package com.xiaobubuya.msm.controller;

import com.xiaobubuya.msm.service.MsmService;
import lombok.SneakyThrows;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "group",
        topic = "TopicEmail",
        selectorExpression="*")
public class MailServer implements RocketMQListener<MessageExt> {
    @Autowired
    RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();
    @Autowired
    private MsmService msmService;


    @SneakyThrows
    @Override
    public void onMessage(MessageExt message) {
        byte[] body = message.getBody();
        String msg = new String(body);
        System.out.println(msg);
        String[] strs = msg.split("\\|");
        //检查当前消息是否被消费，保证消息幂等性
/*        if(!redisTemplate.hasKey(strs[1])){
            System.out.println(strs[1] + " 消息已被消费,无法消费");
            return;
        }*/
        //接收到消息发送邮件
        msmService.sendEmailCode(strs[1],strs[0]);
    }
}