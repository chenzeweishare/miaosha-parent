package com.miaosha.mq.rocket;


import java.util.List;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageListenerImpl implements MessageListenerConcurrently {

	@Autowired
    BusinessInterface businessInterface;

	public void setBusinessInterface(BusinessInterface businessInterface) {
		this.businessInterface = businessInterface;
	}

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		for (MessageExt me : msgs) {
				if(null!=me.getTags()){
					//BusinessInterface<MessageExt> bus=ConsumerFactory.getInterface(me.getTopic(),me.getTags());
					businessInterface.doBusiness(me);
				}
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

	}


}