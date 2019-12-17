package com.y3tu.yao.gateway.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * ClassName: SignUtil
 * Description:
 * date: 2019/11/20 15:30
 *
 * @author zht
 */
public class SignUtil {
    private final static String secretKey = "ndE2jdZNFixH9G6Aidsfyf7lYT3PxW";

    /**
     * @Author : zht
     * @Description :根据签名算法得出签名---参数按照参数名ASCII码从小到大排序（字典序）
     * @Date : 10:55 2018/6/5
     **/
    public static void main(String[] args) {
        String timestamp = "20191217102050";
        String nonce = "123123";
        String bodyString = "{\"page_num\":1,\"page_size\":10,\"root\":{\"child\":{\"test\":1}}}";
        //指定字符集UTF-8
        String characterEncoding = "UTF-8";
        String mySign = createSign(characterEncoding, timestamp, nonce, bodyString);
        System.out.println(mySign);
    }


    public static String createSign(String characterEncoding, String timestamp, String nonce, String bodyString) {
        //把参数做MD5加密
        String signValue = encryptWithMD5(timestamp + nonce + bodyString, characterEncoding).toUpperCase();
        //使用私钥经SHA256签名算法生成签名值
        signValue = sha256_HMAC(signValue, secretKey);
        //做BASE64编码后的sign
        return Base64.getEncoder().encodeToString(signValue.getBytes());
    }


    private static String encryptWithMD5(String target, String charset) {
        String md5Str = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            byte[] bytes = md5.digest(charset == null ? target.getBytes() : target.getBytes(charset));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(Integer.toHexString(bt));
            }
            md5Str = stringBuilder.toString();
        } catch (Exception ignored) {
        }
        return md5Str;
    }

    /**
     * sha256_HMAC加密
     *
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    private static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String sTemp;
        for (int n = 0; b != null && n < b.length; n++) {
            sTemp = Integer.toHexString(b[n] & 0XFF);
            if (sTemp.length() == 1) {
                hs.append('0');
            }
            hs.append(sTemp);
        }
        return hs.toString().toLowerCase();
    }
}
