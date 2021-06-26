package com.xiaobubuya.edu.client;

import org.springframework.stereotype.Component;

/**
 * @Author: xiaobubuya
 * @Description:
 * @Date Created in 2021-06-26 12:08
 * @Modified By:
 */
@Component
public class OrderClientImpl implements OrderClient{
    @Override
    public boolean isBuyCourse(String memberid, String id) {
        return false;
    }
}
