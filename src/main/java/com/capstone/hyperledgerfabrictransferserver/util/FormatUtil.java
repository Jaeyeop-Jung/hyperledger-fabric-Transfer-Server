package com.capstone.hyperledgerfabrictransferserver.util;

public class FormatUtil {

    public static boolean isStudentNumber(String uniqueNumber){
        if (uniqueNumber.startsWith("20") && uniqueNumber.length() == 8) {
            return true;
        }
        return false;
    }

    public static boolean isPhoneNumber(String uniqueNumber) {
        if (uniqueNumber.startsWith("010") && (uniqueNumber.length() == 11 || uniqueNumber.length() == 10)) {
            return true;
        }
        return false;
    }

}
