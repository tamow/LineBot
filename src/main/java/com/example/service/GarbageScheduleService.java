package com.example.service;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.GarbageScheduleDao;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

@Service
public class GarbageScheduleService {

    @Autowired
    private GarbageScheduleDao gsDao;

    public Message getMessage(int dayOfWeek) {
        DayOfWeek week = DayOfWeek.of(dayOfWeek);
    	String outputText = week.getDisplayName(TextStyle.FULL, Locale.JAPAN) + "は😮";
    	outputText += System.getProperty("line.separator");
		outputText += getItems(dayOfWeek);		
        return new TextMessage(outputText);
    }

    public Message getTodayMessage(ZonedDateTime today) {
    	String outputText = "今日は😧";
    	outputText += System.getProperty("line.separator") + "・";
    	outputText += System.getProperty("line.separator");
		outputText += getItems(today.getDayOfWeek().getValue());		
        return new TextMessage(outputText);
    }

    private String getItems(int dayOfWeek){
    	List<String> items = gsDao.selectItems(dayOfWeek);
    	if (items == null) {
    		return "休みだよ(´･Д･)」";
    	} else {
    		return "・" + String.join(System.getProperty("line.separator") + "・", items);
    	}

    }
}
