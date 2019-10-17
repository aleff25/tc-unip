package com.health.lifeway.domain.commons.crypt;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class CryptUtils {

    private static String ALGORITHM = "MD5";

    private MessageDigest md = null;

    public CryptUtils() {
        try {
            this.md =  MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public boolean compare(String pwd, String pwdHashed) {
        String newPwd = crypt(pwd);
        return pwdHashed.equals(newPwd);
    }

    public String crypt(String pwd) {
        if (md != null) {
            return new String(hexCodes(md.digest(pwd.getBytes())));
        }
        return null;
    }

    private static char[] hexCodes(byte[] text) {
        char[] hexOutput = new char[text.length * 2];
        String hexString;

        for (int i = 0; i < text.length; i++) {
            hexString = "00" + Integer.toHexString(text[i]);
            hexString.toUpperCase().getChars(hexString.length() - 2,
                    hexString.length(), hexOutput, i * 2);
        }
        return hexOutput;
    }
}
