package com.liam.kafkaintro.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

// ALL CONSUMER STUFF
@Configuration
public class KafkaConsumerConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	// configuration to pass to =====> ConsumerFactory
	public Map<String, Object> consumerConfig() { // must be a Map data structure - <String,Object>?
		Map<String, Object> property = new HashMap<>();
		property.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); // bootstrapServers - from application.properties(localhost:9092)
		property.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class); // key will be a string
		property.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class); // value will be a string
		return property;
	}

	
	// consumes Kafka Producers?
	@Bean // Lets you dependency inject inside below method
	public ConsumerFactory<String, String> consumerFactory() { // can also be ConsumerFactorty<String, someObject>
		return new DefaultKafkaConsumerFactory<>(consumerConfig());		
	}
	
	// Listener
	// recieves all messages from all topics or partitions on a single thread
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> factory(ConsumerFactory<String, String> consumerFactory) {		// Since the others are <String, String> - I guess
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}
}
