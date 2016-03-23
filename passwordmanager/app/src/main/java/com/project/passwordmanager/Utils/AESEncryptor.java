package com.project.passwordmanager.Utils;

import android.content.Context;
import android.util.Base64;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


/**
 * AesDemo encryption/Decryption
 * @author yogesh (yogeshdhedhi@gmail.com)
 */
public class AESEncryptor {

   /* public static void main(String args[]) {
        final String strToEncrypt = "This is a text for encryption and decryption";

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            String encryptedStr = AESEncryptor.encrypt(strToEncrypt.trim(), saltKey.getBytes(), cipher);
            System.out.println("String to Encrypt: " + strToEncrypt);
            System.out.println("Encrypted: " + encryptedStr);
            System.out.println("String To Decrypt : " + encryptedStr);
            String decStr = AESEncryptor.decrypt(encryptedStr.trim(), saltKey.getBytes(), cipher);
            System.out.println("Decrypted : " + decStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }*/

    public static void writeDataFile(String data,Context context,final String  masterCode){
        try {
            final String saltKey = AppUtils.getSaltKey(masterCode);
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            String encryptedStr = AESEncryptor.encrypt(data.trim(), saltKey.getBytes(), cipher);
            if(encryptedStr != null) {
                FileOutputStream fileos = context.openFileOutput("test", Context.MODE_PRIVATE);
                fileos.write(encryptedStr.getBytes());
                fileos.close();
            }else {
                AppUtils.makeShortToast(context,"problem while saving Data please try after sometime");
            }
        } catch (FileNotFoundException e) {
            AppUtils.makeShortToast(context,"problem while saving Data please try after sometime");
        } catch (IOException e) {
            AppUtils.makeShortToast(context,"problem while saving Data please try after sometime");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
        e.printStackTrace();
        }
    }


    public static String readDataFile(Context context,String masterCode) {
        String data = "";
        final String saltKey = AppUtils.getSaltKey(masterCode);
        try {
            FileInputStream fin = context.openFileInput("test");
            int c;
            while ((c = fin.read()) != -1) {
                data = data + Character.toString((char) c);
            }
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            String decryptedFileData = AESEncryptor.decrypt(data.trim(), saltKey.getBytes(), cipher);
            return decryptedFileData;
        } catch (FileNotFoundException e) {
            AppUtils.makeShortToast(context,"problem while reading Data please try after sometime");
        } catch (IOException e) {
            AppUtils.makeShortToast(context,"problem while reading Data please try after sometime");
        } catch (NoSuchAlgorithmException e) {
            AppUtils.makeShortToast(context, "problem while reading Data please try after sometime");
        } catch (NoSuchPaddingException e) {
            AppUtils.makeShortToast(context, "problem while reading Data please try after sometime");
        }
        return data;
    }
    public static String encrypt(String strToEncrypt, byte[] key, Cipher cipher) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
            byte[] text = Arrays.copyOf(strToEncrypt.getBytes("UTF-8"), ((strToEncrypt.getBytes("UTF-8").length/16)+1)*16);
            String encryptedString = base64(cipher.doFinal(text));
            return encryptedString;
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, byte[] key, Cipher cipher) {
        try {
            cipher.init(Cipher.DECRYPT_MODE,  new SecretKeySpec(key, "AES"));
            String decryptedString = new String(cipher.doFinal(base64Decry(strToDecrypt)));
            return decryptedString;
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static String base64(byte[] bytes) {
        String encryptedString = "";
        try {
            return new String(Base64.encode(bytes, Base64.NO_WRAP), "UTF-8");
        } catch (UnsupportedEncodingException e){

        }
        return encryptedString;
    }

    public static byte[] base64Decry(String str) {
        return Base64.decode(str, Base64.NO_WRAP);
    }
}
