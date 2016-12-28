package com.devon.demo.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Conditional;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.devon.demo.config.MessageConfiguration;
import com.devon.demo.model.Order;

@Component
@Conditional(MessageConfiguration.class)
public class MessageSender {

	@Autowired
	@Qualifier("jmsTemplate1")
	JmsTemplate jmsTemplate;

	public void sendMessage(final Order order) {

		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage objectMessage = session.createObjectMessage(order);
				return objectMessage;
			}
		});
	}

}
