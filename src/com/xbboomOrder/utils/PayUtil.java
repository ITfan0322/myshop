package com.xbboomOrder.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 閸掓稑缂撻弮鍫曟？閿涳拷2016楠烇拷11閺堬拷2閺冿拷 娑撳宕�7:12:44
 * 
 * @author andy
 * @version 2.2
 */

public class PayUtil {

	/**
	 * 閻㈢喐鍨氱拋銏犲礋閸欙拷
	 * 
	 * @return
	 */
	public static String getTradeNo() {
		// 閼奉亜顤�8娴ｅ秵鏆� 00000001
		return "TNO" + DatetimeUtil.formatDate(new Date(), DatetimeUtil.TIME_STAMP_PATTERN) + "00000001";
	}

	/**
	 * 闁拷濞嗘儳宕熼崣锟�
	 * 
	 * @return
	 */
	public static String getRefundNo() {
		// 閼奉亜顤�8娴ｅ秵鏆� 00000001
		return "RNO" + DatetimeUtil.formatDate(new Date(), DatetimeUtil.TIME_STAMP_PATTERN) + "00000001";
	}

	/**
	 * 闁拷濞嗘儳宕熼崣锟�
	 * 
	 * @return
	 */
	public static String getTransferNo() {
		// 閼奉亜顤�8娴ｅ秵鏆� 00000001
		return "TNO" + DatetimeUtil.formatDate(new Date(), DatetimeUtil.TIME_STAMP_PATTERN) + "00000001";
	}

	/**
	 * 鏉╂柨娲栫�广垺鍩涚粩鐥爌
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteAddrIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 婢舵碍顐奸崣宥呮倻娴狅絿鎮婇崥搴濈窗閺堝顦挎稉鐚閸婄》绱濈粭顑跨娑撶導p閹靛秵妲搁惇鐔风杽ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	/**
	 * 閼惧嘲褰囬張宥呭閸ｃ劎娈慽p閸︽澘娼�
	 * 
	 * @param request
	 * @return
	 */
	public static String getLocalIp(HttpServletRequest request) {
		return request.getLocalAddr();
	}

	public static String getSign(Map<String, String> params, String paternerKey) throws UnsupportedEncodingException {
		return MD5Utils.getMD5(createSign(params, false) + "&key=" + paternerKey).toUpperCase();
	}

	/**
	 * 閺嬪嫰锟界姷顒烽崥锟�
	 * 
	 * @param params
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createSign(Map<String, String> params, boolean encode) throws UnsupportedEncodingException {
		Set<String> keysSet = params.keySet();
		Object[] keys = keysSet.toArray();
		Arrays.sort(keys);
		StringBuffer temp = new StringBuffer();
		boolean first = true;
		for (Object key : keys) {
			if (key == null || StringUtil.isEmpty(params.get(key))) // 閸欏倹鏆熸稉铏光敄娑撳秴寮稉搴ｎ劮閸氾拷
				continue;
			if (first) {
				first = false;
			} else {
				temp.append("&");
			}
			temp.append(key).append("=");
			Object value = params.get(key);
			String valueStr = "";
			if (null != value) {
				valueStr = value.toString();
			}
			if (encode) {
				temp.append(URLEncoder.encode(valueStr, "UTF-8"));
			} else {
				temp.append(valueStr);
			}
		}
		return temp.toString();
	}

	/**
	 * 閸掓稑缂撻弨顖欑帛闂呭繑婧�鐎涙顑佹稉锟�
	 * @return
	 */
	public static String getNonceStr(){
		return RandomUtil.randomString(RandomUtil.LETTER_NUMBER_CHAR, 32);
	}
	
	/**
	 * 閺�顖欑帛閺冨爼妫块幋锟�
	 * @return
	 */
	public static String payTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
