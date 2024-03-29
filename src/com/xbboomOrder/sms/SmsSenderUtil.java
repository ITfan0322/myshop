package com.xbboomOrder.sms;

import java.lang.StringBuffer;
import java.util.Random;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;


public class SmsSenderUtil {

    public static boolean isNotEmpty(String s) {
        if (s == null || s.isEmpty())
            return false;
        return true;
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }

    public static long getRandom() {
        return (new Random(SmsSenderUtil.getCurrentTime())).nextInt(900000) + 100000;
    }

    public static String calculateSignature(String appkey, long random, long time,
            String phoneNumber) {

        StringBuffer buffer = new StringBuffer("appkey=")
            .append(appkey)
            .append("&random=")
            .append(random)
            .append("&time=")
            .append(time)
            .append("&mobile=")
            .append(phoneNumber);

        return sha256(buffer.toString());
    }

    public static String calculateSignature(String appkey, long random, long time,
            String[] phoneNumbers) {

        StringBuffer buffer = new StringBuffer("appkey=")
            .append(appkey)
            .append("&random=")
            .append(random)
            .append("&time=")
            .append(time)
            .append("&mobile=");

        if (phoneNumbers.length > 0) {
            buffer.append(phoneNumbers[0]);
            for (int i = 1; i < phoneNumbers.length; i++) {
                buffer.append(",");
                buffer.append(phoneNumbers[i]);
            }
        }

        return sha256(buffer.toString());
    }

    public static String calculateSignature(String appkey, long random, long time,
            ArrayList<String> phoneNumbers) {
        return calculateSignature(appkey, random, time, phoneNumbers.toArray(new String[0]));
    }

    public static String calculateSignature(String appkey, long random, long time) {

        StringBuffer buffer = new StringBuffer("appkey=")
            .append(appkey)
            .append("&random=")
            .append(random)
            .append("&time=")
            .append(time);

        return sha256(buffer.toString());
    }

    public static String calculateFStatusSignature(String appkey, long random,
            long time, String fid) {

        StringBuffer buffer = new StringBuffer("appkey=")
            .append(appkey)
            .append("&random=")
            .append(random)
            .append("&time=")
            .append(time)
            .append("&fid=")
            .append(fid);

        return sha256(buffer.toString());
    }

    public static String calculateAuth(String appkey, long random, long time, String fileSha1Sum) {
        StringBuffer buffer = new StringBuffer("appkey=")
            .append(appkey)
            .append("&random=")
            .append(random)
            .append("&time=")
            .append(time)
            .append("&content-sha1=")
            .append(fileSha1Sum);

        return sha256(buffer.toString());
    }

    public static String sha1sum(String rawString) {
        return DigestUtils.sha1Hex(rawString);
    }

    public static String sha1sum(byte[] bytes) {
        return DigestUtils.sha1Hex(bytes);
    }

    public static String sha256(String rawString) {
        return DigestUtils.sha256Hex(rawString);
    }
}
