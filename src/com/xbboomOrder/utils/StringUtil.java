package com.xbboomOrder.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String瀹搞儱鍙跨猾锟�
 * 
 * @author andy
 * @date 2015-5-16 娑撳宕�4:04:22
 * 
 */
public class StringUtil {

	private StringUtil() {
		super();
	}

	/**
	 * 閸戝搫骞搉ull閸滐拷""
	 * @param src
	 * @return
	 */
	public static String formatNull(String src) {
		return (src == null || "null".equals(src)) ? "" : src;
	}

	/**
	 * 閸掋倖鏌囩�涙顑佹稉鍙夋Ц閸氾缚璐熺粚铏规畱濮濓絽鍨悰銊ㄦ彧瀵骏绱濈粚铏规鐎涙顑佺�电懓绨查惃鍓坣icode缂傛牜鐖�
	 */
	private static final String EMPTY_REGEX = "[\\s\\u00a0\\u2007\\u202f\\u0009-\\u000d\\u001c-\\u001f]+";

	/**
	 * 妤犲矁鐦夌�涙顑佹稉鍙夋Ц閸氾缚璐熺粚锟�
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isEmpty(String input) {
		return input == null || input.equals("") || input.matches(EMPTY_REGEX);
	}
	
	public static boolean isNotEmpty(String input){
		return !isEmpty(input);
	}

	private static final String NUM_REG = "(\\+|\\-)?\\s*\\d+(\\.\\d+)?";

	/**
	 * 閸掋倖鏌囬弰顖氭儊閺佹澘鐡�
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (isEmpty(str)) {
			return false;
		}

		if (str.trim().matches(NUM_REG)) {
			return true;
		}

		return false;
	}

	/**
	 * 閸掋倖鏌囬弰顖氭儊閸栧懎鎯堥張澶夎础閻胶娈戦弫鐗堝祦,婵″倹鐏夌�涙顑佹稉韫厬閸栧懎鎯堥張澶嬫禌閹广垹鐡х粭锕�姘ㄧ拋銈勮礋閺勵垯璐￠惍锟�
	 * 
	 * @param str
	 * @return
	 */
	public static boolean containUnreadableCode(String str) {
		return contain(str, "\\ufffd");
	}

	/**
	 * 閸掋倛顕伴弰顖氭儊閸栧懎鎯堥弫鏉跨摟
	 * 
	 * @param str
	 * @return
	 */
	public static boolean containNumber(String str) {
		return contain(str, "\\d");
	}

	/**
	 * 閸掋倖鏌囬弰顖氭儊閸栧懎鎯坅-zA-Z_0-9
	 * 
	 * @param str
	 * @return
	 */
	public static boolean containWord(String str) {
		return contain(str, "\\w");
	}

	/**
	 * 閺勵垰鎯侀崠鍛儓閺堝鐖ｉ悙鍦儊閸欙拷
	 * 
	 * @param str
	 * @return
	 */
	public static boolean containPunct(String str) {
		return contain(str, PUNCT_REG);
	}

	public static boolean contain(String str, String regex) {
		if (isEmpty(str) || isEmpty(regex)) {
			return false;
		}

		if (str.trim().matches(regex)) {
			return true;
		}

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			return true;
		}

		return false;
	}

	/**
	 * 閺囨寧宕查幍锟介張澶屾畱閿涘牅绗夐崠鍝勫瀻婢堆冪毈閸愭瑱绱�
	 * 
	 * @param input
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replaceAll(String input, String regex,
			String replacement) {
		return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(input)
				.replaceAll(replacement);
	}

	/**
	 * 缁夊娅庨幍锟介張澶屾畱缁岀儤鐗�
	 * 
	 * @param text
	 * @return
	 */
	public static String removeAllSpace(String text) {
		if (isEmpty(text)) {
			return text;
		}

		return text.replaceAll("[ ]+", "");
	}

	private static final String PUNCT_REG = "[^a-zA-Z0-9\\u4e00-\\u9fa5]";

	/**
	 * 缁夊娅庣�涙顑佹稉韫厬閻ㄥ嫭澧嶉張澶屾畱娑擃叀瀚抽弬鍥ㄧ垼閻愬湱顑侀崣锟�
	 * 
	 * @param str
	 * @return
	 */
	public static String removeAllPunct(String str) {
		if (isEmpty(str)) {
			return str;
		}

		return str.replaceAll(PUNCT_REG, "");
	}

	/**
	 * 鐠侊紕鐣籹tr娑擃厼瀵橀崥顐㈩樋鐏忔垳閲滅�涙劕鐡х粭锔胯sub
	 * 
	 * @param str
	 * @param sub
	 * @return
	 */
	public static int countMatches(String str, String sub) {
		if (isEmpty(str) || isEmpty(sub)) {
			return 0;
		}

		int count = 0;
		int idx = 0;
		while ((idx = str.indexOf(sub, idx)) != -1) {
			count++;
			idx += sub.length();
		}

		return count;
	}

	/**
	 * 閼惧嘲绶卞┃鎰摟缁楋缚瑕嗛惃鍕娑擃亜鐡欑�涙顑佹稉锟�
	 * 
	 * @param str
	 *            閿涙碍绨�涙顑佹稉锟�
	 * @param beginIndex
	 *            閿涙艾绱戞慨瀣偍瀵洩绱欓崠鍛閿涳拷
	 * @param endIndex
	 *            閿涙氨绮ㄩ弶鐔哄偍瀵洩绱欐稉宥呭瘶閹奉剨绱�
	 * @return
	 */
	public static String substring(String str, int beginIndex, int endIndex) {
		if (isEmpty(str)) {
			return str;
		}

		int length = str.length();

		if (beginIndex >= length || endIndex <= 0 || beginIndex >= endIndex) {
			return null;
		}

		if (beginIndex < 0) {
			beginIndex = 0;
		}
		if (endIndex > length) {
			endIndex = length;
		}

		return str.substring(beginIndex, endIndex);
	}

	/**
	 * 鐠侊紕鐣籹tr娑擃厼瀵橀崥顐㈢摍鐎涙顑佹稉鐬玼b閹碉拷閸︺劋缍呯純顔炬畱閸撳秳绔存稉顏勭摟缁楋附鍨ㄩ懓鍛倵娑擄拷娑擃亜鐡х粭锕�鎷皊ub閹碉拷缂佸嫭鍨氶惃鍕煀鐎涙顑佹稉锟�
	 * 
	 * @param str
	 * @param sub
	 * @return
	 */
	public static Set<String> substring(String str, String sub) {
		if (isEmpty(str) || isEmpty(sub)) {
			return null;
		}

		Set<String> result = new HashSet<String>();
		int idx = 0;
		while ((idx = str.indexOf(sub, idx)) != -1) {
			String temp = substring(str, idx - 1, idx + sub.length());
			if (!isEmpty(temp)) {
				temp = removeAllPunct(temp);
				if (!sub.equalsIgnoreCase(temp) && !containWord(temp)) {
					result.add(temp);
				}

			}

			temp = substring(str, idx, idx + sub.length() + 1);
			if (!isEmpty(temp)) {
				temp = removeAllPunct(temp);
				if (!sub.equalsIgnoreCase(temp) && !containWord(temp)) {
					result.add(temp);
				}
			}

			idx += sub.length();
		}

		return result;
	}

	/**
	 * 鏉╁洦鎶ら幒濉淢L娑擃厽妫ゅ▔鏇⌒掗弸鎰畱闂堢偞纭剁�涙顑�
	 * 
	 * @param content
	 * @return
	 */
	public static String wrapXmlContent(String content) {
		if (isEmpty(content)) {
			return "";
		}

		StringBuilder result = new StringBuilder();

		for (int i = 0; i < content.length(); i++) {
			char ch = content.charAt(i);
			if ((ch == '\t') || (ch == '\n') || (ch == '\r')
					|| ((ch >= ' ') && (ch <= 55295))
					|| ((ch >= 57344) && (ch <= 65533))
					|| ((ch >= 65536) && (ch <= 1114111))) {
				result.append(ch);
			}
		}

		return result.toString();
	}

	/**
	 * 閸掋倖鏌囩�涙顑佹稉鑼畱闂�鍨
	 * 
	 * @param str
	 * @return
	 */
	public static boolean overLength(String str) {
		if (isEmpty(str)) {
			return false;
		}

		return str.length() > 1 ? true : false;
	}

	/**
	 * 鐎涙顑佹稉韫厬閸氼偅婀侀悧瑙勭暕鐎涙顑侀惃鍕槱閻烇拷
	 * 
	 * @param str
	 * @return
	 */
	public static String specialStr(String str) {
		str = str.replaceAll("[^\\u4e00-\\u9fa5 | 0-9| a-zA-Z | \\.]+", " ")
				.replaceAll("[\\.]{2,}", " ").trim();
		return str;
	}

	/**
	 * 鐏忓棛澹掑▓濠勵儊閸欏嘲骞撻幒澶涚礉娴ｅ棙妲告穱婵堟殌缁岀儤鐗�
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceInValidateChar(String str) {
		return str.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5\\s+]", " ");
	}

	/**
	 * 鏉╂柨娲栫�涙顑佹稉鎻掝嚠鎼存梻娈憉nicode缂傛牜鐖�
	 * 
	 * @param str
	 * @return
	 */
	public static String[] toHexString(String str) {
		char[] chars = str.toCharArray();

		String[] result = new String[chars.length];

		for (int i = 0; i < chars.length; i++) {
			result[i] = Integer.toHexString((int) chars[i]);
		}

		return result;
	}

	public static String getUuid() {
		return UUID.randomUUID().toString();
	}
	
	public static boolean isUrl(String src) {
		String regex = "http[s]?:\\/\\/([\\w-]+\\.[\\w-]+)(\\.[\\w-])+(:\\d{2,10})?.*";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(src);
		return matcher.matches();
	}
	
	/**
	 * sql 閺屻儴顕楁潪顑跨疅
	 * @param str
	 * @return
	 */
	public static String escapeSql(String str){
		if (StringUtil.isNotEmpty(str)) {
			StringBuffer strbuff = new StringBuffer();
			for (String s : str.split("")) {
				if (s.equals("%") || s.equals("_") || s.equals("\\")) {
					strbuff.append("\\");
				}
				strbuff.append(s);
			}
			return strbuff.toString();
		}
		return str;
	}
}
