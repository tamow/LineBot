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

	public Message getMessage(int dayOfWeek) {
		DayOfWeek week = DayOfWeek.of(dayOfWeek);
		String text = week.getDisplayName(TextStyle.FULL, Locale.JAPAN);
		text += getItems(dayOfWeek);
		return new TextMessage(text);
	}

	private String getItems(int dayOfWeek) {
		List<String> items = gsDao.selectItems(dayOfWeek);
		if (items.isEmpty()) {
			return "は休みだよ(´･Д･)」";
		} else {
			return System.getProperty("line.separator") + "・"
					+ String.join(System.getProperty("line.separator") + "・", items);
		}
	}

	public Message getSchedule() {
		String text = null;
		for (int dayOfWeek = DayOfWeek.MONDAY.getValue(); dayOfWeek <= DayOfWeek.SUNDAY.getValue(); dayOfWeek++) {
			text += ((TextMessage) getMessage(dayOfWeek)).getText();
		}
		return new TextMessage(text);
	}
}
