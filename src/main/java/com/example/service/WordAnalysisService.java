package com.example.service;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.WordAnalysisDao;

@Service
public class WordAnalysisService {

	private static final int MONDAY = 1;
	private static final int TUESDAY = 2;
	private static final int WEDNESDAY = 3;
	private static final int THURSDAY = 4;
	private static final int FRIDAY = 5;
	private static final int SATURDAY = 6;
	private static final int SUNDAY = 7;
	private static final int DAY_BEFORE_YESTERDAY = 8;
	private static final int YESTERDAY = 9;
	private static final int TODAY = 10;
	private static final int TOMORROW = 11;
	private static final int DAY_AFTER_TOMORROW = 12;

	@Autowired
	private WordAnalysisDao waDao;

	public int getDayOfWeek(String word, ZonedDateTime dateTime) {

		int tpye = waDao.selectType(word.trim().toUpperCase());

		switch (tpye) {
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
		default:
			return -1;
		}
	}
}
