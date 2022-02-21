package com.liam.kafkaintro.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

//ALL PRODUCER STUFF
@Configuration
public class KafkaProducerConfig {
	

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	// configuration to pass to =====> ProducerFactory
	public Map<String, Object> producerConfig() { // must be a Map data structure - <String,Object>?
		Map<String, Object> property = new HashMap<>();
		property.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); // bootstrapServers - from application.properties(localhost:9092)
		property.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // key will be a string
		property.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // value will be a string
		return property;
	}
	
	// create Kafka Producers
	@Bean// Let you dependency inject inside below method
	public ProducerFactory<String, String> producerFactory() { // can also be ProducerFactorty<String, someObject>
		return new DefaultKafkaProducerFactory<>(producerConfig());		
	}
	
	// send messages with KafkaTemplate
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) { // can also be ProducerFactorty<String, someObject>
		return new KafkaTemplate<>(producerFactory);
	}
}
