package com.jeesite.modules.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author zhenghuan (zeghaun@163.com)
 * @version Created by zhenghuan on 2016/8/27
 */
public class PasswordUtil {

    /**
     * 生成密码
     * @param pwdSrc 密码明文
     * @param phone 手机号
     * @return
     */
    public static String getPassword(String pwdSrc, Long phone) {
        String salt = String.valueOf(phone % 1000000);
        return DigestUtils.md5Hex(pwdSrc + salt);
    }
    
    public static String getMd5PasswordOnce(String pwdMd5, Long phone) {
        String salt = String.valueOf(phone % 1000000);
        return DigestUtils.md5Hex(pwdMd5 + salt);
    }
    
    /**
     * 校验密码
     * @param pwdMd5 前端输入的密码一次md5后的值
     * @param phone 手机号
     * @param pwdDB 数据库存的密码
     * @return
     */
    public static boolean valid(String pwdMd5, Long phone, String pwdDB) {
        String salt = String.valueOf(phone % 1000000);
        String userPass = DigestUtils.md5Hex(pwdMd5 + salt);
        return userPass.equals(pwdDB);
    }

}
