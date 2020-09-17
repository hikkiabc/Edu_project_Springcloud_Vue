package com.gg.utils;

import org.springframework.util.DigestUtils;

public class Md5Util {
    private static String salt = "salt";

    /**
     * 生成md5
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        String base = str + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
