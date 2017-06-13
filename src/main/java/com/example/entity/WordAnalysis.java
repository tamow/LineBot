package com.example.entity;

import org.seasar.doma.Entity;

import lombok.Data;

@Data
@Entity
public class WordAnalysis {

	private String word;
	private Integer type;
}
