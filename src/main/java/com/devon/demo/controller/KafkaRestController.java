package com.devon.demo.controller;

import com.devon.demo.kafka.config.KafkaConfig;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Conditional(KafkaConfig.class)
public class KafkaRestController {
	@Autowired
	private KafkaTemplate<Integer, String> kt;
	static final Logger        LOG   = LoggerFactory.getLogger(KafkaRestController.class);
	private      AtomicInteger count = new AtomicInteger();
private String tempStr ="{\"mem\":2146596,\"mem.free\":721702,\"processors\":16,\"instance.uptime\":85339,\"uptime\":93719,\"systemload.average\":-1.0,\"heap.committed\":2048512,\"heap.init\":524288,\"heap.used\":1326809,\"heap\":7431168,\"nonheap.committed\":99392,\"nonheap.init\":2496,\"nonheap.used\":98097,\"nonheap\":0,\"threads.peak\":40,\"threads.daemon\":18,\"threads.totalStarted\":48,\"threads\":40,\"classes\":11296,\"classes.loaded\":11296,\"classes.unloaded\":0,\"gc.ps_scavenge.count\":15,\"gc.ps_scavenge.time\":202,\"gc.ps_marksweep.count\":3,\"gc.ps_marksweep.time\":243,\"jvm.memory-usage.pools.Code-Cache.max\":251658240,\"com.devon.demo.kafka.config.Listener.listener_call.fiveMinuteRate\":44628.487552979816,\"jvm.memory-usage.pools.PS-Survivor-Space.max\":79167488,\"jvm.memory-usage.pools.PS-Old-Gen.init\":358088704,\"jvm.threads.new.count\":0,\"jvm.gc.PS-Scavenge.time\":202,\"jvm.threads.daemon.count\":18,\"jvm.memory-usage.heap.max\":7609516032,\"jvm.memory-usage.pools.PS-Eden-Space.committed\":1558183936,\"jvm.memory-usage.pools.Metaspace.max\":-1,\"jvm.threads.blocked.count\":0,\"jvm.memory-usage.pools.PS-Old-Gen.max\":5707399168}";

	@PostMapping("/produce/{str}")
	public void produce(@PathVariable("str") String str) {
		kt.send("devontest2", str);
	}

	@PostMapping("/producebrust/{str}/{load}")
	public void producebrust(@PathVariable("str") String str, @PathVariable("load") String load) {
		int temp = Integer.parseInt(load);
		for (int x = 0; x < temp; x++) {
			kt.send("devontest2", count.incrementAndGet() + " - "+ str);
		}
	}

	// @Async
	// private void sendBrust(@PathVariable("str") String str) {
	// for (int x = 0; x < 1000000; x++) {
	// kt.send("devontest", str);
	//
	// }
	// }



}
