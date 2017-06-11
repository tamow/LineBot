package com.example.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.GarbageScheduleDao;

@Service
public class GarbageScheduleService {

    @Autowired
    private GarbageScheduleDao garbageScheduleDao;

    public String getTypesToday() {
    	Integer dayOfTheWeek = LocalDateTime.now().getDayOfWeek().getValue();
        List<String> types = garbageScheduleDao.selectTypes(dayOfTheWeek);
        return String.join(System.getProperty("line.separator"), types);
    }

    public String getTypes(Integer dayOfTheWeek) {
        List<String> types = garbageScheduleDao.selectTypes(dayOfTheWeek);
        return String.join(System.getProperty("line.separator"), types);
    }
}
