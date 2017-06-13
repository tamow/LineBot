package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dto.HiraganaRequestDto;
import com.example.dto.HiraganaResponseDto;

@Service
public class HiraganaService {

	public String convertToHiragana(String word) {

		String url = "https://labs.goo.ne.jp/api/hiragana";

		HiraganaRequestDto req = new HiraganaRequestDto();
		req.setAppId("5d6a00e3c13dd4cadde7e775c6440848ef32a696fa6e13b5c1ad23ce10db1b2e");
		req.setRequestId("record003");
		req.setOutputType("hiragana");
		req.setSentence(word);

		HiraganaResponseDto res = new RestTemplate().postForObject(url, req, HiraganaResponseDto.class);
		
		return res.getConverted();
	}
}
