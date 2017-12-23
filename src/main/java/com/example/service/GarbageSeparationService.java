package com.example.service;

import java.io.IOException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class GarbageSeparationService {

	public String search(String text, int max) throws IOException {
		// 家具は曖昧すぎるため対象外にする
		if ("家具".equals(text)) {
			return null;
		}

		String url = "http://cgi.city.yokohama.lg.jp/shigen/bunbetsu/search2.html?txt=" + URLEncoder.encode(text, "windows-31j") + "&lang=ja";
		Document document = Jsoup.connect(url).get();
		Elements itemNames = document.getElementsByClass("item_name");
		int itemCount = itemNames.size() - 1;
		if (itemCount == 0) {
			return null;
		}

		String res = "";
		Elements itemDescs = document.getElementsByClass("item_desc");
		for (int i = 1; i <= max && i <= itemCount; i++) {
			String itemName = itemNames.get(i).text();
			String itemDesc = (itemDescs.get(i).select("a").text().isEmpty() ? itemDescs.get(i).text(): itemDescs.get(i).select("a").text());
			String itemUrl = itemDescs.get(i).select("a").attr("href");
			res += "[" + itemName + "]" + System.getProperty("line.separator") + itemDesc + System.getProperty("line.separator") + itemUrl + System.getProperty("line.separator");
		}
		if (max != 1 && itemCount > max) {
			res += "全て表示" + System.getProperty("line.separator") + url;
		}
		return res;
	}

}
