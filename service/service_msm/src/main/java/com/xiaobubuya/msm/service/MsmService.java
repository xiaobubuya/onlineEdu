package com.xiaobubuya.msm.service;

import java.util.Map;

/**
 * @Author: xiaobubuya
 * @Description:
 * @Date Created in 2021-06-22 15:10
 * @Modified By:
 */
public interface MsmService {
    boolean send(String phone, String sms_180051135, Map<String, Object> param);

    boolean sendEmailCode(String email, Map<String, Object> param);
}
