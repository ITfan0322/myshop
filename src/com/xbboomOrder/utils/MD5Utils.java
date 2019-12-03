package com.xbboomOrder.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * 濮濄倗琚弰鐤5閸旂姴鐦戠粻妤佺《閻ㄥ嫬鐤勯悳甯礉 闁插洨鏁ゆ禍鍞坅va閸愬懐鐤嗙粻妤佺《閿涘矂娓剁憰浣哥穿閻⑩暒ava.security.MessageDigest
 *
 * @author airland.congs
 * @version $Revision: 1.1 $
 *
 */
public class MD5Utils {
	// 鐏忓繐鍟撻惃鍕摟缁楋缚瑕�
	private static char[] DigitLower = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	// 婢堆冨晸閻ㄥ嫬鐡х粭锔胯
	private static char[] DigitUpper = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 姒涙顓婚弸鍕拷鐘插毐閺侊拷
	 *
	 */
	public MD5Utils() {
	}

	/**
	 * 閸旂姴鐦戞稊瀣倵閻ㄥ嫬鐡х粭锔胯閸忋劋璐熺亸蹇撳晸
	 *
	 * @param srcStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NullPointerException
	 */
	public static String getMD5Lower(String srcStr)
			throws NoSuchAlgorithmException {
		String sign = "lower";
		return processStr(srcStr, sign);
	}

	/**
	 * 閸旂姴鐦戞稊瀣倵閻ㄥ嫬鐡х粭锔胯閸忋劋璐熸径褍鍟�
	 *
	 * @param srcStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NullPointerException
	 */
	public static String getMD5Upper(String srcStr)
			throws NoSuchAlgorithmException {
		String sign = "upper";
		return processStr(srcStr, sign);
	}

	private static String processStr(String srcStr, String sign)
			throws NoSuchAlgorithmException, NullPointerException {
		MessageDigest digest;
		// 鐎规矮绠熺拫鍐暏閻ㄥ嫭鏌熷▔锟�
		String algorithm = "MD5";
		// 缂佹挻鐏夌�涙顑佹稉锟�
		String result = "";
		// 閸掓繂顫愰崠鏍ц嫙瀵拷婵绻樼悰宀冾吀缁狅拷
		digest = MessageDigest.getInstance(algorithm);
		digest.update(srcStr.getBytes());
		byte[] byteRes = digest.digest();

		// 鐠侊紕鐣籦yte閺佹壆绮嶉惃鍕毐鎼达拷
		int length = byteRes.length;

		// 鐏忓摴yte閺佹壆绮嶆潪顒佸床閹存劕鐡х粭锔胯
		for (int i = 0; i < length; i++) {
			result = result + byteHEX(byteRes[i], sign);
		}

		return result;
	}

	/**
	 * 鐏忓摴tye閺佹壆绮嶆潪顒佸床閹存劕鐡х粭锔胯
	 *
	 * @param bt
	 * @return
	 */
	private static String byteHEX(byte bt, String sign) {

		char[] temp = null;
		if (sign.equalsIgnoreCase("lower")) {
			temp = DigitLower;
		} else if (sign.equalsIgnoreCase("upper")) {
			temp = DigitUpper;
		} else {
			throw new java.lang.RuntimeException("閸旂姴鐦戠紓鍝勭毌韫囧懓顩﹂惃鍕蒋娴狅拷");
		}
		char[] ob = new char[2];

		ob[0] = temp[(bt >>> 4) & 0X0F];

		ob[1] = temp[bt & 0X0F];

		return new String(ob);
	}

	 public static String getMD5(String content) {
	        MessageDigest messageDigest = null;
	        try {
	            messageDigest = MessageDigest.getInstance("MD5");
	            messageDigest.reset();
	            messageDigest.update(content.getBytes("UTF-8"));
	        } catch (Exception e) {
	        	e.printStackTrace();
	        } 

	        byte[] byteArray = messageDigest.digest();

	        StringBuffer md5StrBuff = new StringBuffer();

	        for (int i = 0; i < byteArray.length; i++) {
	            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
	                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
	            else
	                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
	        }

	        return md5StrBuff.toString();
	    }

	public static void main(String[] args) {
		String content = getMD5("24358");
		System.out.println(content);
	}

}
