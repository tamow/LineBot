package com.example.controller;


import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.service.GarbageScheduleService;
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

    public Message reply(String word, ZonedDateTime dateTime) {
		int dayOfWeek = waService.getDayOfWeek(word, dateTime);
		if (dayOfWeek == -1) {
			return gsService.getTodayMessage(dateTime);
		}
		return gsService.getMessage(dayOfWeek);
    }
    
    public Message replyStickerMessage() {
    	return smService.getRandomMessage();
    }
}