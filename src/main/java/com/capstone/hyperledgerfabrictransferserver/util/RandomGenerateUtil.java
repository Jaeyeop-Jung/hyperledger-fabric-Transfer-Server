package com.capstone.hyperledgerfabrictransferserver.util;

import java.security.SecureRandom;

public class RandomGenerateUtil {

    private static final SecureRandom random = new SecureRandom();

    public static String generate(Integer length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }
}
