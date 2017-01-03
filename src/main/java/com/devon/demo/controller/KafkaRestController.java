package com.devon.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devon.demo.kafka.config.KafkaConfig;

@RestController
@Conditional(KafkaConfig.class)
public class KafkaRestController {
	@Autowired
	KafkaTemplate<Integer, String> kt;

	@PostMapping("/produce/{str}")
	public void produce(@PathVariable("str") String str) {
		kt.send("test", str);
	}
}
