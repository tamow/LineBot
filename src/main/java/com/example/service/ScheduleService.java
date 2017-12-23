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
public class ScheduleService {

	@Autowired
	private GarbageScheduleDao gsDao;

	public Message getMessage(int dayOfWeek) {
		String text = getItems(dayOfWeek);
		return new TextMessage(text);
	}

	private String getItems(int dayOfWeek) {
		List<String> items = gsDao.selectItems(dayOfWeek);
		if (items.isEmpty()) {
			return DayOfWeek.of(dayOfWeek).getDisplayName(TextStyle.FULL, Locale.JAPAN) + "は休みだよ(´･Д･)」";
		} else {
			return String.join(System.getProperty("line.separator"), items);
		}
	}

	public Message getSchedule() {
		String schedule = "";
		for (int dayOfWeek = DayOfWeek.MONDAY.getValue(); dayOfWeek <= DayOfWeek.SUNDAY.getValue(); dayOfWeek++) {
			DayOfWeek week = DayOfWeek.of(dayOfWeek);
			schedule += "[" + week.getDisplayName(TextStyle.FULL, Locale.JAPAN) + "]" + System.getProperty("line.separator");
			List<String> items = gsDao.selectItems(dayOfWeek);
			if (items.isEmpty()) {
				schedule += "休み" + System.getProperty("line.separator");
			} else {
				schedule += String.join(", ", items) + System.getProperty("line.separator");
			}
		}
		return new TextMessage(schedule.trim());
	}
}
