package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;

import com.example.service.ConvertDayOfTheWeekService;
import com.example.service.GarbageScheduleService;
import com.example.service.StickMessageService;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@LineMessageHandler
public class LineBotController {

	@Autowired
    private ConvertDayOfTheWeekService cdService;

	@Autowired
    private GarbageScheduleService gsService;

	@Autowired
    private StickMessageService smService;

	@EventMapping
    public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		int dayOfTheWeek = cdService.convert(event.getMessage().getText());
		if (dayOfTheWeek == -1) {
			smService.getRandomMessage();
		}
		return gsService.getMessage(dayOfTheWeek);
    }
    
    @EventMapping
    public Message handleStickerMessage(MessageEvent<StickerMessageContent> event) {
    	return smService.getRandomMessage();
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
}