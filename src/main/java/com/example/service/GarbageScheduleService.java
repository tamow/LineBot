package com.example.service;

import java.time.DayOfWeek;
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

    public Message getMessage(int dayOfTheWeek) {

        DayOfWeek week = DayOfWeek.of(dayOfTheWeek);
    	String outputText = week.getDisplayName(TextStyle.FULL, Locale.JAPAN) + "は...";
    	outputText += System.getProperty("line.separator");

    	List<String> types = gsDao.selectTypes(dayOfTheWeek);
        outputText += String.join(System.getProperty("line.separator"), types);
        		
        return new TextMessage(outputText);
    }
}
