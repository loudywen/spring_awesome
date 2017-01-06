package com.devon.demo.controller;

import com.devon.demo.kafka.config.KafkaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Conditional(KafkaConfig.class)
public class KafkaRestController {
	@Autowired
	private KafkaTemplate<Integer, String> kt;


	@PostMapping("/produce/{str}")
	public void produce(@PathVariable("str") String str) {
        sendBrust(str);
    }

	@PostMapping("/producebrust/{str}")
	public void producebrust(@PathVariable("str") String str) {
        sendBrust(str);
    }

    @Async
    private void sendBrust(@PathVariable("str") String str) {
	    for(int x = 0; x<1000000; x++){
            kt.send("devontest", str);

        }
    }



}
