package com.eleme.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomUtils {
    private static int[] prefix = {
            // 联通
            130,
            131,
            132,
            145,
            155,
            156,
            166,
            175,
            176,
            185,
            186,
            // 移动
            134,
            135,
            136,
            137,
            138,
            139,
            147,
            150,
            151,
            152,
            157,
            158,
            159,
            178,
            182,
            183,
            184,
            187,
            188,
            198
    };

    public static String getRandomPhoneNum() {
        SecureRandom secureRandom = new SecureRandom();
        int i = secureRandom.nextInt(prefix.length-1);
        return prefix[i]+ "" + (int) ((Math.random() * 90000000 + 9999999));
    }

    public static void main(String[] args) {
        String sn = "2a029315fa2b0c02";
//        Integer integer = Integer.valueOf(str, 16);
        String oldeosid = new BigInteger(sn, 16).toString(10);
        String s = oldeosid.substring(0, oldeosid.length() - 3) + "00";
        System.out.println(s);
    }
}
