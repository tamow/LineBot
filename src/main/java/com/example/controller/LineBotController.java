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

	@Autowired
	private WordAnalysisService waService;

	@Autowired
	private GarbageScheduleService gsService;

	@Autowired
	private StickMessageService smService;

	@Autowired
	private LuisService luisService;

	public Message reply(String text, ZonedDateTime dateTime) throws URISyntaxException {

		LuisResponseDto luisRes = luisService.verifyDay(text);
		Optional<LuisEntity> luisEntity = luisRes.getEntities().stream().sorted(Comparator.comparingDouble(LuisEntity::getScore)).findFirst();
        if(!luisEntity.isPresent()) {
    		return smService.getQuestionMessage();
        }
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