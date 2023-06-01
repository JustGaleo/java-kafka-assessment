package com.example.interview.service;

import com.example.interview.entity.Scan;
import com.example.interview.repository.ScanRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Service
public class KafkaConsumerService {

	@Autowired
	private ScanRepository scanRepository;

	@KafkaListener(id = "scan-listener-spring-v1", topics = "scan_topic_v1", groupId = "scan_group", concurrency = "1")
	public void consumeScanv1(ConsumerRecord<String, String> record) {

		System.out.println("Received message for scan_topic_v1. Key: " + record.key() + ", Value: " + record.value());

		ObjectMapper mapper = new ObjectMapper();
		Scan scan;
		try {
			JsonNode node = mapper.readTree(record.value());

			scan = new Scan();
			scan.setProductId(node.get("product_id").asText());
			scan.setTimestamp(LocalDateTime.parse(node.get("timestamp").asText(), DateTimeFormatter.ISO_DATE_TIME));
			scan.setType(node.get("type").asText());
			scan.setRobotId(node.get("robot_id").asText());
			scan.setBarcode(node.get("barcode").asText());
			scan.setShelfLocation(node.get("shelf_location").asText());

			scanRepository.save(scan);
		} catch (JsonMappingException e) {
			System.out.println("JsonMappingException: " + e.getMessage());
		} catch (JsonProcessingException e) {
			System.out.println("JsonProcessingException: " + e.getMessage());
		}
	}

	@KafkaListener(id = "scan-listener-spring-v2", topics = "scan_topic_v2", groupId = "scan_group", concurrency = "1")
	public void consumeScanv2(ConsumerRecord<String, String> record) {

		System.out.println("Received message for scan_topic_v2. Key: " + record.key() + ", Value: " + record.value());
		HashMap<String, String> scanMap = new HashMap<String, String>();
		String[] parts = record.value().split("\\|");
		for (String part : parts) {
			String[] element = part.split(":",2);
			scanMap.put(element[0].trim(), element[1].trim());
		}
		Scan scan = new Scan();
		scan.setProductId(scanMap.get("product_id"));
		scan.setTimestamp(LocalDateTime.parse(scanMap.get("timestamp"), DateTimeFormatter.ISO_DATE_TIME));
		scan.setType(scanMap.get("type"));
		scan.setRobotId(scanMap.get("robot_id"));
		scan.setBarcode(scanMap.get("barcode"));
		scan.setShelfLocation(scanMap.get("shelf_location"));
		scanRepository.save(scan);

	}

}
