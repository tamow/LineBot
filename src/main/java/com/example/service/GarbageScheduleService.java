package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.GarbageScheduleDao;

@Service
public class GarbageScheduleService {

    @Autowired
    private GarbageScheduleDao garbageScheduleDao;

    public String getType() {
        return garbageScheduleDao.selectAll().get(0).getType();
    }
}
