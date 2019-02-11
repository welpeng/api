package com.eleme.utils;


public class Synchronizes {

    public static String buildReceivingLock(String urlKey) {
        return joinIntern("RECEIVING", urlKey);
    }

    public static String buildUserReceiveLock( String userId) {
        return joinIntern("USER_RECEIVE", userId);
    }

    private static String joinIntern(String... elements) {
        return String.join("::", elements).intern();
    }

}
