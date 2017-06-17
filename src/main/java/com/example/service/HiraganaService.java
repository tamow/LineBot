package com.example.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dto.HiraganaRequestDto;
import com.example.dto.HiraganaResponseDto;

@Service
public class HiraganaService {

	public String convertToHiragana(String word) throws URISyntaxException {

		URI uri =  new URI("https://labs.goo.ne.jp/api/hiragana");

		HiraganaRequestDto body = new HiraganaRequestDto();
		body.setApp_id("5d6a00e3c13dd4cadde7e775c6440848ef32a696fa6e13b5c1ad23ce10db1b2e");
		body.setRequest_id("record003");
		body.setOutput_type("hiragana");
		body.setSentence(word);

		RequestEntity<HiraganaRequestDto> req = RequestEntity.post(uri).contentType(MediaType.APPLICATION_JSON)
				.body(body);

		ResponseEntity<HiraganaResponseDto> res = new RestTemplate().exchange(req, HiraganaResponseDto.class);

		return res.getBody().getConverted();
	}
}
