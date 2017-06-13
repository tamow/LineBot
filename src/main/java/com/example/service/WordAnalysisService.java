package com.example.service;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.WordAnalysisDao;

@Service
public class WordAnalysisService {

	private final int MONDAY = 1;
	private final int TUESDAY = 2;
	private final int WEDNESDAY = 3;
	private final int THURSDAY = 4;
	private final int FRIDAY = 5;
	private final int SATURDAY = 6;
	private final int SUNDAY = 7;
	private final int DAY_BEFORE_YESTERDAY = 8;
	private final int YESTERDAY = 9;
	private final int TODAY = 10;
	private final int TOMORROW = 11;
	private final int DAY_AFTER_TOMORROW = 12;

	@Autowired
	private WordAnalysisDao waDao;

	public int getDayOfWeek(String word, ZonedDateTime dateTime) {

		switch (waDao.selectType(word.toUpperCase())) {
		case MONDAY:
			return DayOfWeek.MONDAY.getValue();
		case TUESDAY:
			return DayOfWeek.TUESDAY.getValue();
		case WEDNESDAY:
			return DayOfWeek.WEDNESDAY.getValue();
		case THURSDAY:
			return DayOfWeek.THURSDAY.getValue();
		case FRIDAY:
			return DayOfWeek.FRIDAY.getValue();
		case SATURDAY:
			return DayOfWeek.SATURDAY.getValue();
		case SUNDAY:
			return DayOfWeek.SUNDAY.getValue();
		case DAY_BEFORE_YESTERDAY:
			return dateTime.minusDays(2).getDayOfWeek().getValue();
		case YESTERDAY:
			return dateTime.minusDays(1).getDayOfWeek().getValue();
		case TODAY:
			return dateTime.getDayOfWeek().getValue();
		case TOMORROW:
			return dateTime.plusDays(1).getDayOfWeek().getValue();
		case DAY_AFTER_TOMORROW:
			return dateTime.plusDays(2).getDayOfWeek().getValue();
		}
		return -1;
	}
}
