package com.example.service;

import java.io.IOException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class GarbageSeparationService {

	public String search(String text) throws IOException {
		String url = "http://cgi.city.yokohama.lg.jp/shigen/bunbetsu/search2.html?txt=" + URLEncoder.encode(text, "windows-31j") + "&lang=ja";
		Document document = Jsoup.connect(url).get();
		Elements elements = document.getElementsByClass("item_name");
		if (elements.size() >= 2) {
			String item = elements.get(1).text();
			elements = document.getElementsByClass("item_desc");
			return "[" + item + "]" + System.getProperty("line.separator") + elements.get(1).select("a").text() + System.getProperty("line.separator");
		}
		return null;
	}

}
