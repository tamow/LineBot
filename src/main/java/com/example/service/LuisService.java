package com.example.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dto.LuisResponseDto;

@Service
public class LuisService {

	@Autowired
	private RestTemplate restTemplate;

	public LuisResponseDto verifyDay(String query) throws URISyntaxException {

		URI uri = new URI(
				"https://westus.api.cognitive.microsoft.com/luis/v2.0/apps/cbd1c897-28c1-4f9c-97b4-b3217b3c9198?subscription-key=5e61e3ba9a68483c9b92bdc58bfadb19&timezoneOffset=540&verbose=true&q="
						+ query);

		ResponseEntity<LuisResponseDto> res = restTemplate.getForEntity(uri, LuisResponseDto.class);
		return res.getBody();
	}
}