package com.example.dto;

import lombok.Data;

@Data
public class HiraganaRequestDto {

	private String appId;
	private String requestId;
	private String outputType;
	private String sentence;
}
