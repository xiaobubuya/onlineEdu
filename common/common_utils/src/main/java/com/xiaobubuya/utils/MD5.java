package com.xiaobubuya.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: xiaobubuya
 * @Description:
 * @Date Created in 2021-06-22 20:16
 * @Modified By:
 */
public final class MD5 {
    public static String encrypt (String strSrc){
        try{
            char hexChars[] = { '0','1','2','3','4','5','6','7','8',
                    '9','a','b','c','d','e','f'};
            byte[] bytes = strSrc.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw  new RuntimeException("MD5加密出错！！+" + e);
        }

    }

    public static void main(String[] args) {
        System.out.println(MD5.encrypt("111111"));
    }
}
