package com.miaosha.mq.rocket;


import org.apache.log4j.Logger;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * consumer
 */
public class RocketConsumer {

	private static final Logger log = Logger.getLogger(RocketConsumer.class);
	private static final Object startAndShutDownlock = new Object();
	private static boolean active = false;
	private String topic;
	private String groupName;
	private String url;
	private String instanceName;
	private String tags;
	BusinessInterface businessInterface;

	public void setBusinessInterface(BusinessInterface businessInterface) {
		this.businessInterface = businessInterface;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	private  void start(String topic, String tags, MessageListenerConcurrently listener) throws MQClientException {
		synchronized (startAndShutDownlock) {
			if (active) {
				log.error("........");
				return;
			}
			active = true;
		}
		DefaultMQPushConsumer consumer = RocketMQUtil.createConsumer(groupName, url, instanceName);
		consumer.subscribe(this.topic, this.tags);
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		consumer.registerMessageListener(listener);
		consumer.start();
		RocketMQUtil.shutdownHook(consumer);
		log.info("topic:" + this.topic);
		log.info("Tags:" + this.tags);
		log.info("consumer started .");

	}
	
	public  void start() throws MQClientException {
		MessageListenerImpl listener = new MessageListenerImpl();
		listener.setBusinessInterface(businessInterface);
		start(topic, tags, listener);
	}
}



