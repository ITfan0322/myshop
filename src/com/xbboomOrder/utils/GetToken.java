package com.xbboomOrder.utils;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.codehaus.xfire.util.Base64;

import com.google.gson.JsonObject;

public class GetToken {
	/**
	 * 
	 * @author silen 获取token
	 */
	
		String appid = "wxd3270e7769759ce7";
		String secret = "20ae66705a29fe927544a1dfcd02b96b";

		// 获取token
		public String getToken() throws Exception {
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
			String token = new HttpRequestor().doGet(requestUrl);
			JSONObject oppidObj = JSONObject.fromObject(token);
			 String to = (String) oppidObj.get("access_token");
			// String session_key = (String) oppidObj.get("session_key");
			//System.out.println(oppidObj);
			
			return to;
		}
		// 获取token
		public String getTicket(String token) throws Exception {
					String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";
					String ticket = new HttpRequestor().doGet(requestUrl);
					JSONObject oppidObj = JSONObject.fromObject(ticket);
					 String tic = (String) oppidObj.get("ticket");
					// String session_key = (String) oppidObj.get("session_key");
					System.out.println(oppidObj);
					
					return tic;
				}
		
	
}
