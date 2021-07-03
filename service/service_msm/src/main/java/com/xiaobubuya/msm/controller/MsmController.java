package com.xiaobubuya.msm.controller;

import com.xiaobubuya.msm.service.MsmService;
import com.xiaobubuya.msm.utils.RandomUtil;
import com.xiaobubuya.utils.Result;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaobubuya
 * @Description:
 * @Date Created in 2021-06-22 15:09
 * @Modified By:
 */
@RequestMapping("/msm")
@RestController
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${rocketmq.topic}")
    private String mqTopic;

    @Value("${rocketmq.producer.send-message-timeout}")
    private int timeout;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    // 发送阿里云短信的方法
    @GetMapping(value = "/send/{phone}")
    public Result code(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) return Result.ok();

        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msmService.send(phone, "SMS_180051135", param);
        if(isSend) {
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error().message("发送短信失败");
        }
    }

    // 发送邮箱验证的方法
    @GetMapping(value = "/sendEmail/{email}")
    public Result emailCode(@PathVariable String email) throws Exception {
        String code = redisTemplate.opsForValue().get(email);
        if(!StringUtils.isEmpty(code)) return Result.ok();
        code = RandomUtil.getFourBitRandom();

        asyncSend(mqTopic, "TagA", code+'|'+email);
        redisTemplate.opsForValue().set(email, code,5, TimeUnit.MINUTES);
        return Result.ok();
    }


    /**
     * 异步发送消息
     * 可靠异步发送：发送方发出数据后，不等接收方发回响应，接着发送下个数据包的通讯方式；
     * 特点：速度快；有结果反馈；数据可靠；
     * 应用场景：异步发送一般用于链路耗时较长,对 rt响应时间较为敏感的业务场景,例如用户视频上传后通知启动转码服务,转码完成后通知推送转码结果等；
     */
    public void asyncSend(String topic, String tags, String body) throws Exception {
        //判断Topic和body是否为空
        if (StringUtils.isEmpty(topic)||StringUtils.isEmpty(body)) {
            return ;
        }
        rocketMQTemplate.asyncSend(topic, body, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("消息发送成功"+sendResult.getSendStatus());
                //更新用户状态
                //mailSendLogsService.updateStatus(msgId,1);
            }
            @Override
            public void onException(Throwable e) {
                // TODO 消息发送失败，定义一个定时器定时去发送消息
                e.printStackTrace();
                System.out.println("消息发送失败");
            }
        },timeout);
    }

}
