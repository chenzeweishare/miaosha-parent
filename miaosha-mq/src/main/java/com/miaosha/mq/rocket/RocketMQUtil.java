package com.miaosha.mq.rocket;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;

/**
 * static untilties for RocketMQ 
 * @author zhangyinhu
 *
 */
public class RocketMQUtil {


	/**
	 * create  DefaultMQProducer
	 * @param groupName
	 * @param url
	 * @param instanceName
	 * @return
	 */
	public static DefaultMQProducer createProducer(String groupName, String url, String instanceName){
		DefaultMQProducer producer = new DefaultMQProducer(groupName);
		producer.setNamesrvAddr(url);
		producer.setInstanceName(instanceName);
		return producer;
	}

	/**
	 * create DefaultMQPushConsumer
	 * @param groupName
	 * @param url
	 * @param instanceName
	 * @return
	 */
	public static DefaultMQPushConsumer createConsumer(String groupName, String url, String instanceName) {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
		consumer.setNamesrvAddr(url);
		consumer.setInstanceName(instanceName);
		return consumer;
	}
	
	/**
	 * Thread shutdown hook for close Reference
	 * @param producer current MQProducer
	 */
	public static void shutdownHook(final MQProducer producer) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				if (producer!=null) {
					producer.shutdown();
				}
			}
		}));
	}
	/**
	 * Thread shutdown hook for close Reference
	 * @param consumer current  MQPushConsumer
	 */
	public static void shutdownHook(final MQPushConsumer consumer) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				if (consumer!=null) {
					consumer.shutdown();
				}
			}
		}));
	}
	
	
} 
