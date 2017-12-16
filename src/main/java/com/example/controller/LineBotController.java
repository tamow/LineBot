package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.dto.LuisEntity;
import com.example.dto.LuisResponseDto;
import com.example.service.GarbageScheduleService;
import com.example.service.LuisService;
import com.example.service.RekognitionService;
import com.example.service.StickMessageService;
import com.example.service.TranslationService;
import com.example.service.WordAnalysisService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

@Controller
public class LineBotController {

	private static final String SCHEDULE_INTENT = "verifySchedule";

	@Autowired
    private LineMessagingClient lineMessagingClient;
	
	@Autowired
	private WordAnalysisService waService;

	@Autowired
	private GarbageScheduleService gsService;

	@Autowired
	private StickMessageService smService;

	@Autowired
	private LuisService luisService;
	
	@Autowired
	private RekognitionService rService;

	@Autowired
	private TranslationService tService;

	public Message replyTextMessage(String text, ZonedDateTime dateTime) throws URISyntaxException {

		LuisResponseDto luisRes = luisService.luis(text);
		// 予定表
		if (SCHEDULE_INTENT.equals(luisRes.getTopScoringIntent().getIntent()) && luisRes.getTopScoringIntent().getScore() > 0.9) {
			return gsService.getSchedule();
		}
		Optional<LuisEntity> luisEntity = luisRes.getEntities().stream().filter(p -> "day".equals(p.getType()))
				.sorted(Comparator.comparingDouble(LuisEntity::getScore).reversed()).findFirst();
		if (!luisEntity.isPresent()) {
			return smService.getQuestionMessage();
		}
		// 曜日
		String word = luisEntity.get().getEntity();
		int dayOfWeek = waService.getDayOfWeek(word, dateTime);
		if (dayOfWeek == -1) {
			return smService.getQuestionMessage();
		}
		return gsService.getMessage(dayOfWeek);
	}

	public Message replyStickerMessage() {
		return smService.getRandomMessage();
	}
	
	public Message replyImageMessage(String messageId) throws InterruptedException, ExecutionException, IOException {
		InputStream is = lineMessagingClient.getMessageContent(messageId).get().getStream();
		List<String> items = rService.analyze(is);
		
		String res = "";
		for (String item : items) {
			res += tService.translate(item) + System.getProperty("line.separator");
		}
		return new TextMessage(items.toString());
	}
}