package com.jeesite.modules.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import org.bouncycastle.util.encoders.Hex;

public class AesUtil {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/NoPadding";
        static String key = "frp9876543210000";  //16位
    static String iv2 = "1234567890000000";  //16位


    /**
     * 加密
     * @param content 待加密内容
     */
    public static String encryptAES(String key, String content) throws Exception{
        // 获得一个 SecretKeySpec
        // SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKeyStr), "AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        // 获得加密算法实例对象 Cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"算法/模式/补码方式"
        // 获得一个 IvParameterSpec
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv2.getBytes());  // 使用 CBC 模式，需要一个向量 iv, 可增加加密算法的强度
        // 根据参数初始化算法
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        // 执行加密并返回经 BASE64 处助理之后的密文
        return Base64.getEncoder().encodeToString(cipher.doFinal(content.getBytes()));
    }

    /**
     * 解密
     * @param content: 待解密内容，是 BASE64 编码后的字节数组
     */
    public static String decryptAES(String content) throws Exception {
        // 密文进行 BASE64 解密处理
        byte[] contentDecByBase64 = Base64.getDecoder().decode(content);
        // 获得一个 SecretKeySpec
        // SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKeyStr), "AES");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        // 获得加密算法实例对象 Cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"算法/模式/补码方式"
        // 获得一个初始化 IvParameterSpec
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv2.getBytes());
        // 根据参数初始化算法
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        // 解密
        return new String(cipher.doFinal(contentDecByBase64), "utf8");
    }


    public static void main(String[] args) throws Exception {
        System.out.println(encryptAES("frp9876543210000","123456"));
        System.out.println(decryptAES("KD9op7L2GJiej+5uFCMs0w=="));

    }

}
