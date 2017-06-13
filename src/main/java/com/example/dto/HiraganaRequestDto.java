package com.example.dto;

import lombok.Data;

@Data
public class HiraganaRequestDto {

	private String app_id;
	private String request_id;
	private String output_type;
	private String sentence;
}
