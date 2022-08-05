package com.capstone.hyperledgerfabrictransferserver.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Sha256Util {

    public static String digest(String message) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update("test".getBytes(StandardCharsets.UTF_8));
            byte[] digest = messageDigest.digest();

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : digest) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (Exception e) {

        }
        return null;
    }

}
