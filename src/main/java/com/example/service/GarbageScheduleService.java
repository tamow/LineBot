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
    	String outputText = week.getDisplayName(TextStyle.FULL, Locale.JAPAN) + "は￼￼￼￼￼￼￼￼￼￼￼🙂";
    	outputText += System.getProperty("line.separator");

    	List<String> types = gsDao.selectItems(dayOfWeek);
        outputText += String.join(System.getProperty("line.separator"), types);
        		
        return new TextMessage(outputText);
    }

    public Message getTodayMessage(ZonedDateTime today) {

    	String outputText = "今日は😧";
    	outputText += System.getProperty("line.separator");

    	List<String> types = gsDao.selectItems(today.getDayOfWeek().getValue());
        outputText += String.join(System.getProperty("line.separator"), types);
        		
        return new TextMessage(outputText);
    }

}
