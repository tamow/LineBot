package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.GarbageScheduleService;

@RestController
public class GarbageScheduleController {

	@Autowired
    private GarbageScheduleService garbageScheduleService;

	@RequestMapping("/garbage")
    public String index() {
		return garbageScheduleService.getTypesToday();
    }
	
	@RequestMapping(value="/garbage/{dayOfTheWeek}")
    public String getMethod(@PathVariable int dayOfTheWeek) {
		return garbageScheduleService.getTypes(dayOfTheWeek);
    }
}