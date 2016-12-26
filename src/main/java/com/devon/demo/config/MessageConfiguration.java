package com.devon.demo.config;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by Devon on 12/26/2016.
 */
@Configuration
public class MessageConfiguration {

  private static final String DEFAULT_BROKER_URL = "tcp://192.168.0.28:61616";

  private static final String ORDER_QUEUE = "Test1";

  @Bean
  public ActiveMQConnectionFactory connectionFactory(){
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
    connectionFactory.setTrustAllPackages(true);
    return connectionFactory;
  }

  @Bean
  public JmsTemplate jmsTemplate(){
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory());
    template.setDefaultDestinationName(ORDER_QUEUE);
    return template;
  }

}
