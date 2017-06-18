package com.example.controller;

import java.net.URISyntaxException;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.dto.LuisResponseDto;
import com.example.service.GarbageScheduleService;
import com.example.service.LuisService;
import com.example.service.StickMessageService;
import com.example.service.WordAnalysisService;
import com.linecorp.bot.model.message.Message;

@Controller
public class LineBotController {

	@Autowired
	private WordAnalysisService waService;

	@Autowired
	private GarbageScheduleService gsService;

	@Autowired
	private StickMessageService smService;

	@Autowired
	private LuisService luisService;

	public Message reply(String input, ZonedDateTime dateTime) throws URISyntaxException {

		LuisResponseDto luisRes = luisService.verifyDay(input);
		String word = luisRes.getTopScoringIntent().getIntent();
		
		int dayOfWeek = waService.getDayOfWeek(word, dateTime);
		if (dayOfWeek != -1) {
			return gsService.getMessage(dayOfWeek);
		}

		return smService.getQuestionMessage();
	}

	public Message replyStickerMessage() {
		return smService.getRandomMessage();
	}
}