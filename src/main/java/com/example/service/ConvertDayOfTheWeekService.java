package com.example.service;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

@Service
public class ConvertDayOfTheWeekService {

    public int convert(String inputText) {

		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));

    	switch(inputText.toUpperCase()) {
    	case "一昨日":
    	case "おととい":
    	case "DAY BEFORE YESTERDAY":
        	return now.minusDays(2).getDayOfWeek().getValue();
    	case "昨日":
    	case "きのう":
    	case "YESTERDAY":
        	return now.minusDays(1).getDayOfWeek().getValue();
 	   	case "今日":
    	case "きょう":
    	case "本日":
    	case "ほんじつ":
    	case "TODAY":
        	return now.getDayOfWeek().getValue();
    	case "明日":
    	case "あした":
    	case "TOMORROW":
        	return now.plusDays(1).getDayOfWeek().getValue();
    	case "明後日":
    	case "あさって":
    	case "DAY AFTER TOMORROW":
        	return now.plusDays(2).getDayOfWeek().getValue();
    	case "月":
    	case "月曜":
    	case "月曜日":
    	case "MON":
    	case "MONDAY":
    		return DayOfWeek.MONDAY.getValue();
    	case "火":
    	case "火曜":
    	case "火曜日":
    	case "TUE":
    	case "TUESDAY":
    		return DayOfWeek.TUESDAY.getValue();
    	case "水":
    	case "水曜":
    	case "水曜日":
    	case "WED":
    	case "WEDNESDAY":
    		return DayOfWeek.WEDNESDAY.getValue();
    	case "木":
    	case "木曜":
    	case "木曜日":
    	case "THU":
    	case "THURSDAY":
    		return DayOfWeek.THURSDAY.getValue();
    	case "金":
    	case "金曜":
    	case "金曜日":
    	case "FRI":
    	case "FRIDAY":
    		return DayOfWeek.FRIDAY.getValue();
    	case "土":
    	case "土曜":
    	case "土曜日":
    	case "SAT":
    	case "SATURDAY":
    		return DayOfWeek.SATURDAY.getValue();
    	case "日":
    	case "日曜":
    	case "日曜日":
    	case "SUN":
    	case "SUNDAY":
    		return DayOfWeek.SUNDAY.getValue();
    	}
    	return -1;
    }
}
