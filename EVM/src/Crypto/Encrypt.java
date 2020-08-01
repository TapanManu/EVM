/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crypto;

/**
 *
 * @author tapan
 */

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Encrypt {
    public Encrypt(String inputFile) throws BadPaddingException,IllegalBlockSizeException,
    InvalidKeyException,NoSuchPaddingException{
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line;
            int count=0;
            String[] s = inputFile.split(".txt");
            while((line=br.readLine())!=null){
                Boolean status;
                if(count==0)
                    status=false;
                else
                    status=true;
                String encryptedString = Base64.getEncoder().encodeToString(encrypt(line, publicKey));
                try{
                FileWriter fw = new FileWriter(s[0]+".encrypted",status);
                fw.write(encryptedString);
                fw.write("\n");
                fw.close();
                count++;
                }
                catch(IOException ie){
                    System.out.println("error");
                }
            }
        } catch (NoSuchAlgorithmException|IOException e ) {
            System.err.println(e.getMessage());
        }
    }

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgFGVfrY4jQSoZQWWygZ83roKXWD4YeT2x2p41dGkPixe73rT2IW04glagN2vgoZoHuOPqa5and6kAmK2ujmCHu6D1auJhE2tXP+yLkpSiYMQucDKmCsWMnW9XlC5K7OSL77TXXcfvTvyZcjObEz6LIBRzs6+FqpFbUO9SJEfh6wIDAQAB";
    
    public static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }
}
