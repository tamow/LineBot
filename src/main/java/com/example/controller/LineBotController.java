package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.service.ConvertDayOfTheWeekService;
import com.example.service.GarbageScheduleService;
import com.example.service.StickMessageService;
import com.linecorp.bot.model.message.Message;

@Controller
public class LineBotController {

	@Autowired
    private ConvertDayOfTheWeekService cdService;

	@Autowired
    private GarbageScheduleService gsService;

	@Autowired
    private StickMessageService smService;

    public Message reply(String text) {
		int dayOfTheWeek = cdService.convert(text);
		if (dayOfTheWeek == -1) {
			smService.getRandomMessage();
		}
		return gsService.getMessage(dayOfTheWeek);
    }
    
    public Message replyStickerMessage() {
    	return smService.getRandomMessage();
    }
}