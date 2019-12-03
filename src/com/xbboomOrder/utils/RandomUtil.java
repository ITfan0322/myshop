package com.xbboomOrder.utils;

import java.util.Random;

/**
 * 鍒涘缓鏃堕棿锛�2016骞�5鏈�13鏃� 涓嬪崍2:48:48
 * 
 * 闅忔満瀛楃涓�
 * 
 * @author andy
 * @version 2.2
 */

public class RandomUtil {

	public static final String ALL_CHAR = "-_#&$@+-*/%()[]0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String NUMBER_CHAR = "0123456789";

	public static final String SPECIAL_CHAR = "-_#&$@+-*/%()[]";
	
	public static final String LETTER_NUMBER_CHAR = LETTER_CHAR + NUMBER_CHAR;

	/**
	 * 杩斿洖涓�涓畾闀跨殑闅忔満瀛楃涓�
	 * 
	 * @param chars
	 *            妯″瀷涓�
	 * @param length
	 *            闅忔満闀垮害
	 * @return
	 */
	public static String randomString(String chars, int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}

	/**
	 * 杩斿洖涓�涓畾闀跨殑闅忔満瀛楃涓插瓧姣嶅叏閮ㄥぇ鍐�
	 * 
	 * @param chars
	 *            妯″瀷涓�
	 * @param length
	 *            闅忔満瀛楃涓查暱搴�
	 * @return 闅忔満瀛楃涓�
	 */
	public static String randomLowerString(String chars, int length) {
		return randomString(chars, length).toLowerCase();
	}

	/**
	 * 杩斿洖涓�涓畾闀跨殑闅忔満瀛楃涓插瓧姣嶅叏閮ㄥ皬鍐�
	 * 
	 * @param chars
	 *            妯″瀷涓�
	 * @param length
	 *            闅忔満瀛楃涓查暱搴�
	 * @return 闅忔満瀛楃涓�
	 */
	public static String randomUpperString(String chars, int length) {
		return randomString(chars, length).toLowerCase();
	}

	/**
	 * 鐢熸垚涓�涓畾闀跨殑绾�0瀛楃涓�
	 * 
	 * @param length
	 *            瀛楃涓查暱搴�
	 * @return 绾�0瀛楃涓�
	 */
	public static String randomZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append("0");
		}
		return sb.toString();
	}

}
