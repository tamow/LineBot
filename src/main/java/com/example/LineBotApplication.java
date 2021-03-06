package com.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.controller.LineBotController;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@SpringBootApplication
@LineMessageHandler
public class LineBotApplication {

	@Autowired
	private LineBotController controller;

	public static void main(String[] args) {
		SpringApplication.run(LineBotApplication.class, args);
	}

	@EventMapping
	public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws URISyntaxException, IOException {
		return controller.replyTextMessage(event.getMessage().getText(), event.getTimestamp().atZone(ZoneId.of("Asia/Tokyo")));
	}

	@EventMapping
	public Message handleStickerMessage(MessageEvent<StickerMessageContent> event) {
		return controller.replyStickerMessage();
	}

	@EventMapping
    public Message handleImageMessageEvent(MessageEvent<ImageMessageContent> event) throws Exception {
		return controller.replyImageMessage(event.getMessage().getId());
    }

	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
	}
}
