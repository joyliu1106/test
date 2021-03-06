package com.joy.test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

public class DESUtils {
    private Key key;// 密钥的key值
    private byte[] DESkey;
    private byte[] DESIV = { 0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB,(byte) 0xCD, (byte) 0xEF };
    private AlgorithmParameterSpec iv = null;// 加密算法的参数接口

    public DESUtils() {
        try {
            this.DESkey = "GRb3s0d5".getBytes("UTF-8");// 设置密钥
            DESKeySpec keySpec = new DESKeySpec(DESkey);// 设置密钥参数
            iv = new IvParameterSpec(DESIV);// 设置向量
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
            key = keyFactory.generateSecret(keySpec);// 得到密钥对象
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密String 明文输入密文输出
     * @param inputString 待加密的明文
     * @return 加密后的字符串
     */

    public String getEnc(String inputString) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String outputString = "";
        try {
            byteMing = inputString.getBytes("UTF-8");
            byteMi = this.getEncCode(byteMing);
            byte[] temp = Base64.getEncoder().encode(byteMi);
            outputString = new String(temp);
        } catch (Exception e) {

        } finally {
            byteMing = null;
            byteMi = null;
        }
        return outputString;
    }

    /**
     * 解密String 以密文输入明文输出
     * @param inputString 需要解密的字符串
     * @return 解密后的字符串
     */

    public String getDec(String inputString) {
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = Base64.getDecoder().decode(inputString.getBytes());
            byteMing = this.getDesCode(byteMi);
            strMing = new String(byteMing, "UTF8");
        } catch (Exception e) {
        } finally {
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }


    /**
     * 加密以byte[]明文输入,byte[]密文输出
     * @param bt 待加密的字节码
     * @return 加密后的字节码
     */
    private byte[] getEncCode(byte[] bt) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            // 得到Cipher实例
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byteFina = cipher.doFinal(bt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }


    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     * @param bt 待解密的字节码
     * @return 解密后的字节码
     */

    private byte[] getDesCode(byte[] bt) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            // 得到Cipher实例
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byteFina = cipher.doFinal(bt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

}
