package com.zte.shopping.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Eytins
 */


public class MD5Util {
    public static String md5(String str) {
        try {
            // 编译期异常 java.security.NoSuchAlgorithmException
            MessageDigest md = MessageDigest.getInstance("MD5");

            // str.getBytes("utf-8") 编译期异常
            // java.io.UnsupportedEncodingException
            byte[] byteArr = md.digest(str.getBytes("utf-8"));

            // 虽然加密了,但是看上去像乱码(堄{溽觰驛?鮽堲)
            // return new String(byteArr);
            // 定义一个BASE64Encoder实例 BASE64Encoder sun公司的
            BASE64Encoder encoder = new BASE64Encoder();

            // HIjTe+Th03XzQdkG9YKI9A==
            return encoder.encode(byteArr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 以后用单元测试
     * 不要用main()方法
     */
    public static void main(String[] args) {
        System.out.println(md5("wangwu"));
    }

}
