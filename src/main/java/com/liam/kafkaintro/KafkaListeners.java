package com.liam.kafkaintro;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
	
	@KafkaListener(topics = "kafkaintro", groupId = "groupId") // for more listeners you want want to change the groupId to be unique
	void listener(String data) { // Data is coming from KafkaApplication
		System.out.println("Listener recieved " + data + " 🤦‍♀️😒");
	}

}
