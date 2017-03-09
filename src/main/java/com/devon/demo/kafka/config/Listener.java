package com.devon.demo.kafka.config;

<<<<<<< Updated upstream
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

=======
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.devon.demo.metrics.MetricsReporterConfig;
>>>>>>> Stashed changes
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.annotation.KafkaListener;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.devon.demo.metrics.MetricsReporterConfig;

@Conditional(KafkaConfig.class)
public class Listener {
	
	
	private final CountDownLatch	latch1	= new CountDownLatch(1);
	static final Logger				LOG		= LoggerFactory.getLogger(Listener.class);
	private AtomicInteger count = new AtomicInteger();

<<<<<<< Updated upstream
	@KafkaListener(id = "kafkaconsumerlistener", topics = "devontest2")
=======
	@KafkaListener(id = "kafkaconsumerlistener", topics = "devontest")
>>>>>>> Stashed changes
	public void listen1(String foo) {
		this.latch1.countDown();
		Timer.Context context = MetricsReporterConfig.metricRegistry()
				.timer(MetricRegistry.name(Listener.class, "listener_call")).time();
		LOG.info("Count: {} ",count.incrementAndGet());
		context.stop();
	}
}
