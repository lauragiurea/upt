package com.licenta.session;

import com.mysql.cj.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordValidator {

    public static boolean validate(final String password, final String hashedPassword) {
        try {
            return hashedPassword.equals(hashWithMD5(password));
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }

    private static String hashWithMD5(String password) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes());
            byte[] bytes = m.digest();

            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return s.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
