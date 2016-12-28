package com.devon.demo.config;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by Devon on 12/26/2016.
 */
@Configuration
@Conditional(MessageConfiguration.class)

public class MessageConfiguration implements Condition {

	private static final String	DEFAULT_BROKER_URL	= "tcp://192.168.0.28:61616";

	private static final String	ORDER_QUEUE			= "Test1";
	private static final String	RESPONSE_QUEUE		= "Test2";

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
		connectionFactory.setTrustAllPackages(true);
		return connectionFactory;
	}

	@Bean(name = "jmsTemplate1")
	// @Conditional(MessageConfiguration.class)
	public JmsTemplate jmsTemplate1() {
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		template.setDefaultDestinationName(ORDER_QUEUE);
		return template;
	}

	@Bean(name = "jmsTemplate2")
	// @Conditional(MessageConfiguration.class)
	public JmsTemplate jmsTemplate2() {
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		template.setDefaultDestinationName(RESPONSE_QUEUE);
		return template;
	}

	@Override
	public boolean matches(ConditionContext arg0, AnnotatedTypeMetadata arg1) {
		// set to true if want to use active mq
		return false;
	}

}
