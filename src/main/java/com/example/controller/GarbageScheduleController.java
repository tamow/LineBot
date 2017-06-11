package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.GarbageScheduleService;

@RestController
public class GarbageScheduleController {

	@Autowired
    private GarbageScheduleService garbageScheduleService;

	@RequestMapping("/")
    public String index() {
		return garbageScheduleService.getType();
    }
}