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
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Decrypt {
     public Decrypt(String inputFile,String privateKey)throws IllegalBlockSizeException, InvalidKeyException, 
                                NoSuchPaddingException, BadPaddingException{
        String s[] = inputFile.split(".encrypted");
         try {
            BufferedReader br = new BufferedReader(new FileReader(s[0]+".encrypted"));
            String line;
            int count=0;
            Boolean status;
            while((line=br.readLine())!=null){
                String decryptedString = Decrypt.decrypt(line, privateKey);
                try{
                    status = count != 0;
                FileWriter fw = new FileWriter(s[0]+".decrypted",status);
                fw.write(decryptedString);
                fw.write("\n");
                fw.close();
                count++;
                }
                catch(IOException ie){
                    System.out.println("error");
                }
            }
            

            //String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
            //System.out.println(decryptedString);
        } catch (NoSuchAlgorithmException|IOException e ) {
            System.err.println(e.getMessage());
        }

    }
   
    

    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }
}
