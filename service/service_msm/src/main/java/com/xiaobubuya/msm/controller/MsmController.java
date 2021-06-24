package com.xiaobubuya.msm.controller;

import com.xiaobubuya.msm.service.MsmService;
import com.xiaobubuya.msm.utils.RandomUtil;
import com.xiaobubuya.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

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
    public Result emailCode(@PathVariable String email){
        String code = redisTemplate.opsForValue().get(email);
        if(!StringUtils.isEmpty(code)) return Result.ok();

        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msmService.sendEmailCode(email, param);
        if(isSend) {
            redisTemplate.opsForValue().set(email, code,5, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error().message("发送邮件失败");
        }
    }
}
