package com.adk.kost.util;


import com.adk.kost.exception.ValidateException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AES256Util {


    public static String generateEncrypted(String data){
        try{
            SecretKey secretKey = generateSecretKey(data.toCharArray(), generateSalt());
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
            String encrypt = generateEncrypt(data, keySpec);

            return encrypt;
        }catch (Exception e){
            throw new ValidateException("Anda tidak bisa generate");
        }
    }


    public static String generateDecrypted(String data){
        try{

            SecretKey secretKey = generateSecretKey(data.toCharArray(), generateSalt());
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

            String encrypt = generateEncrypted(data);
            String decrypt = generateDecrypt(encrypt, keySpec);

            return decrypt;
        }catch (Exception e){
            throw new ValidateException("Anda tidak bisa generate");
        }
    }

    private static SecretKey generateSecretKey(char[] password, byte[] salt) throws Exception {
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, 65536, 256);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return keyFactory.generateSecret(keySpec);
    }


    private static String generateEncrypt(String plainText, SecretKey secretKey) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypt = cipher.doFinal(plainText.getBytes("UTF-8"));
        return  base64Encode(encrypt);
    }

    private static String generateDecrypt(String cipherText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] dec64 = base64Decode(cipherText);
        byte[] decByte = cipher.doFinal(dec64);
        return new String(decByte, "UTF-8");
    }


    private static String base64Encode(byte[] input){
        return Base64.getEncoder().encodeToString(input);
    }

    private static byte[] base64Decode(String input){
        return Base64.getDecoder().decode(input);
    }

    private static byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
