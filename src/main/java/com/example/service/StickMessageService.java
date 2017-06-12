package com.example.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;

@Service
public class StickMessageService {

    public Message getRandomMessage() {
        switch(new Random().nextInt(5)) {
        case 0:
        	return new StickerMessage("2", "18");
        case 1:
        	return new StickerMessage("2", "20");
        case 2:
        	return new StickerMessage("2", "145");
        case 3:
        	return new StickerMessage("2", "149");
        default:
        	return new StickerMessage("2", "173");
        }
    }
}
