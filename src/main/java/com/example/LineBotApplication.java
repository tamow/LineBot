package com.example;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.util.IOUtils;
import com.example.controller.LineBotController;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@SpringBootApplication
@LineMessageHandler
public class LineBotApplication {

	@Autowired
    private LineMessagingClient lineMessagingClient;
	
	@Autowired
	private LineBotController controller;

	@Value("${aws.accessKey}")
	String accessKey;
	
	@Value("${aws.secretKey}")
	String secretKey;

	public static void main(String[] args) {
		SpringApplication.run(LineBotApplication.class, args);
	}

	@EventMapping
	public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws URISyntaxException {
		return controller.reply(event.getMessage().getText(),
				event.getTimestamp().atZone(ZoneId.of("Asia/Tokyo")));
	}

	@EventMapping
	public Message handleStickerMessage(MessageEvent<StickerMessageContent> event) {
		return controller.replyStickerMessage();
	}

	@EventMapping
    public Message handleImageMessageEvent(MessageEvent<ImageMessageContent> event) throws Exception {
		try {
			InputStream is = lineMessagingClient.getMessageContent(event.getMessage().getId()).get().getStream();
	        DetectLabelsRequest request = new DetectLabelsRequest();
			request.withImage(new Image().withBytes(ByteBuffer.wrap(IOUtils.toByteArray(is))));

			AWSCredentials credential = new BasicAWSCredentials(accessKey, secretKey);
			
	        AmazonRekognitionClient client = new AmazonRekognitionClient(credential);
	        DetectLabelsResult result = client.detectLabels(request);
	        List<Label> labels = result.getLabels();
	        String res = "";
	        for (Label label: labels) {
	            res += label.getName() + ": " + Math.round(label.getConfidence()) + "%" + System.getProperty("line.separator");
	        }
	        return new TextMessage(res);

		} catch (Exception e1) {
			throw e1;
		}
    }

	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
	}
}
