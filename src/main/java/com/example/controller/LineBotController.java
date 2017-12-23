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
import com.example.service.ScheduleService;
import com.example.service.GarbageSeparationService;
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
	private WordAnalysisService wordAnalysisService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private GarbageSeparationService separationService;

	@Autowired
	private StickMessageService stickMessageService;

	@Autowired
	private LuisService luisService;
	
	@Autowired
	private RekognitionService rekognitionService;

	@Autowired
	private TranslationService translationService;

	/**
	 * テキストに対する返信
	 * @param text
	 * @param dateTime
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public Message replyTextMessage(String text, ZonedDateTime dateTime) throws URISyntaxException, IOException {

		String word = "";
		// Luisの形態素解析では、"今日は"が「キョウ ハ」ではなく「コンニチハ」と認識されるため個別対応する
		if ("今日は？".equals(text)) {
			word = "今日";
		} else {
			LuisResponseDto luisRes = luisService.luis(text);
			// 予定に関する単語があれば、予定表を返す
			if (SCHEDULE_INTENT.equals(luisRes.getTopScoringIntent().getIntent()) && luisRes.getTopScoringIntent().getScore() > 0.9) {
				return scheduleService.getSchedule();
			}
			// 曜日または日にち(一昨年/昨日/今日/明日/明後日)に関する単語があれば取得する
			Optional<LuisEntity> luisEntity = luisRes.getEntities().stream().filter(p -> "day".equals(p.getType()))
					.sorted(Comparator.comparingDouble(LuisEntity::getScore).reversed()).findFirst();
			if (luisEntity.isPresent()) {
				word = luisEntity.get().getEntity();
			}
		}
		// wordから列挙型DayOfWeekに変換し、予定を返す
		int dayOfWeek = wordAnalysisService.getDayOfWeek(word, dateTime);
		if (dayOfWeek != -1) {
			return scheduleService.getMessage(dayOfWeek);
		}
		
		// textの分別情報を検索して返す
		String res = separationService.search(text, 3);
		if (res != null) {
			return new TextMessage(res);
		}

		return stickMessageService.getQuestionMessage();
	}

	/**
	 * スタンプに対する返信
	 * @return
	 */
	public Message replyStickerMessage() {
		return stickMessageService.getRandomMessage();
	}
	
	/**
	 * 画像に対する返信
	 * @param messageId
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException
	 */
	public Message replyImageMessage(String messageId) throws InterruptedException, ExecutionException, IOException {
		InputStream is = lineMessagingClient.getMessageContent(messageId).get().getStream();
		List<String> items = rekognitionService.analyze(is);
		
		String text = "";
		int count = 0;
		for (String item : items) {
			String ja = translationService.translate(item);
			String res = separationService.search(ja, 1);
			if (res != null) {
				text += res;
				if (++count >= 3) break;
			}
		}
		return new TextMessage(text);
	}
}