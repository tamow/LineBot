package com.example.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;

import org.springframework.stereotype.Service;

@Service
public class TranslationService {

	public String translate(String text) {
		  return createTranslateService().translate(text, TranslateOption.sourceLanguage("en"), TranslateOption.targetLanguage("ja")).getTranslatedText();
	}

	private Translate createTranslateService() {
		return TranslateOptions.newBuilder().build().getService();
	}
}
