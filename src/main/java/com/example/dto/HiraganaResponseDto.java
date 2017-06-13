package com.example.dto;

import lombok.Data;

@Data
public class HiraganaResponseDto {

	private String requestId;
	private String outputType;
	private String converted;
}
