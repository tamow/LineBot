package com.example.entity;

import org.seasar.doma.Entity;

import lombok.Data;

@Data
@Entity
public class GarbageSchedule {

	private Integer dayOfTheWeek;
	private String  type;
}
