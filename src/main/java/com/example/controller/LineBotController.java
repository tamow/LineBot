package com.example.controller;

import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.dto.LuisEntity;
import com.example.dto.LuisResponseDto;
import com.example.service.GarbageScheduleService;
import com.example.service.LuisService;
import com.example.service.StickMessageService;
import com.example.service.WordAnalysisService;
import com.linecorp.bot.model.message.Message;

@Controller
public class LineBotController {

	private static final String SCHEDULE_INTENT = "verifySchedule";

	@Autowired
	private WordAnalysisService waService;

	@Autowired
	private GarbageScheduleService gsService;

	@Autowired
	private StickMessageService smService;

	@Autowired
	private LuisService luisService;

	public Message reply(String text, ZonedDateTime dateTime) throws URISyntaxException {

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
}