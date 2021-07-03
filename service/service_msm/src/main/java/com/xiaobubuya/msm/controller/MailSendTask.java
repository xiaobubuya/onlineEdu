package com.xiaobubuya.msm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailSendTask {
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void mailResendTask() {
        System.out.println("针对失败消息重新投递");
    }

}
