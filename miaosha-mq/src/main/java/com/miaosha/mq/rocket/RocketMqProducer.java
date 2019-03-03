package com.miaosha.mq.rocket;/*
 * ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * www.jiagouedu.com
 * 悟空老师QQ：245553999
 */


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.util.SerializationUtils;

public class RocketMqProducer {
    private static final Logger log = Logger.getLogger(RocketMqProducer.class);
    private static volatile boolean isStart = false;
    private static boolean initOpen = false;
    private String topic;
    private String groupName;
    private String url;
    private String instanceName;
    private String tags;
    DefaultMQProducer producer;

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


    /**
     * 初始化静态启动单例producer
     */
    public synchronized void start() {
        try {
            if (isStart) {
                throw new MQClientException(-1, "MQProducer是已经启动的，请不要重复启动");
            }
            producer = RocketMQUtil.createProducer(groupName, url, instanceName);
            producer.start();
            isStart = true;
            log.info("producer started");
            RocketMQUtil.shutdownHook(producer);
        } catch (Exception e) {
            log.fatal("RockeMq Service start Unable!please check!", e);
        }

    }

    /**
     * 发送消息处理以后新增所有的发送统一走此接口
     *
     * @param topic 默认走 cn.com.gome.mfb.rocketMq.MQProducerUtil.getTopic();
     * @param tag   新增tag
     * @param obj   要序列化对象既 消息对象
     * @return
     * @throws Exception
     */
    public SendResult sendMessageForOther(String topic, String tag, Object obj) throws Exception {
        if (StringUtils.isEmpty(tag)) {
            log.error(" 消息订阅topic:" + topic + "的tag不能为空");
            return null;
        }
        return retrySendMessage(new Message(topic, tag, SerializationUtils.serialize(obj)), 3);
    }


    /**
     * 发送消息处理以后新增所有的发送统一走此接口
     *
     * @param topic
     *            默认走 cn.com.gome.mfb.rocketMq.MQProducerUtil.getTopic();
     * @param tag
     *            新增tag
     * @param obj
     *            要序列化对象既 消息对象
     * @return
     * @throws MQCannotSendException

    public static SendResult sendMessageForOther(String topic,  Object obj) throws Exception {

    return retrySendMessage(new Message(topic == null ? getTopic() : topic, tag, SerializationUtils.serialize(obj)), 3);
    } */

    /****
     * 非java语言同意走此接口 需用用thrift序列化
     * @Description :
     * @author zhangyinhu
     * @param topic
     * @param data
     * @return
     * @throws Exception
     */
    public SendResult sendMessageForOther(String topic, String tag, byte[] data) throws Exception {

        if (StringUtils.isEmpty(tag)) {
            log.error(" 消息订阅topic:" + topic + "的tag不能为空");
            return null;
        }
        return retrySendMessage(new Message(topic, tag, data), 3);
    }

    /**
     * 指定发送数据
     *
     * @param msg        消息
     * @param retryCount 重试次数
     * @return
     * @throws Exception 当操作重试次数还不能发送成功就抛出此异常
     */
    public SendResult retrySendMessage(Message msg, int retryCount) throws Exception {
        int iniCount = 1;
        SendResult result = null;
        while (true) {
            try {
                result = producer.send(msg);
                break;
            } catch (Exception e) {
                log.error("发送消息失败:", e);
                if (iniCount++ >= retryCount) {
                    throw new Exception(e);
                }
            }
        }
        return result;
    }

}
