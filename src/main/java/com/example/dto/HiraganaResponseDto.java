package com.example.dto;

import lombok.Data;

@Data
public class HiraganaResponseDto {
	private String request_id;
	private String output_type;
	private String converted;
}
