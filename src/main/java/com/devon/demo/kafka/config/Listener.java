package com.devon.demo.kafka.config;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;


public class Listener {
	private final CountDownLatch	latch1	= new CountDownLatch(1);
	static final Logger				LOG		= LoggerFactory.getLogger(Listener.class);

	@KafkaListener(id = "foo", topics = "test")
	public void listen1(String foo) {
		this.latch1.countDown();
		LOG.info(foo);
	}
}
