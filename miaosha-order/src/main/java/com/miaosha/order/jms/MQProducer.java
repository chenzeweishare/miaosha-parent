package com.miaosha.order.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.alibaba.fastjson.JSONObject;
import com.miaosha.order.OrderLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * activeMq
 */
@Component
public class MQProducer {
	
	private JmsTemplate jmsTemplate;
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	
	@Autowired
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendMessage(final OrderLog order) {
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(JSONObject.toJSONString(order));
			}
		});
	}

}