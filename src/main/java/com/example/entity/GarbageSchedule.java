package com.example.entity;

import org.seasar.doma.Entity;

import lombok.Data;

@Data
@Entity
public class GarbageSchedule {

	private Integer dayOfWeek;
	private String item;
}
