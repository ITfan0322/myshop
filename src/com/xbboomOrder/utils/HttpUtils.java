package com.xbboomOrder.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 閸掓稑缂撻弮鍫曟？閿涳拷2016楠烇拷11閺堬拷9閺冿拷 娑撳宕�4:16:32
 * 
 * @author andy
 * @version 2.2
 */
public class HttpUtils {

	private static final String DEFAULT_CHARSET = "UTF-8";
	
	private static final int CONNECT_TIME_OUT = 5000; //闁剧偓甯寸搾鍛閺冨爼妫�3缁夛拷
	
	private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT).build();
	
	private static SSLContext wx_ssl_context = null; //瀵邦喕淇婇弨顖欑帛ssl鐠囦椒鍔�
	
	static{
		Resource resource = new ClassPathResource("apiclient_cert.p12");
		try {
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			char[] keyPassword = ConfigUtil.getProperty("wx.mchid").toCharArray(); //鐠囦椒鍔熺�靛棛鐖�
			keystore.load(resource.getInputStream(), keyPassword);
			wx_ssl_context = SSLContexts.custom().loadKeyMaterial(keystore, keyPassword).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description 閸旂喕鍏橀幓蹇氬牚: get 鐠囬攱鐪�
	 * @param url 鐠囬攱鐪伴崷鏉挎絻
	 * @param params 閸欏倹鏆�
	 * @param headers headers閸欏倹鏆�
	 * @return 鐠囬攱鐪版径杈Е鏉╂柨娲杗ull
	 */
	public static String get(String url, Map<String, String> params, Map<String, String> headers) {

		CloseableHttpClient httpClient = null;
		if (params != null && !params.isEmpty()) {
			StringBuffer param = new StringBuffer();
			boolean flag = true; // 閺勵垰鎯佸锟芥慨锟�
			for (Entry<String, String> entry : params.entrySet()) {
				if (flag) {
					param.append("?");
					flag = false;
				} else {
					param.append("&");
				}
				param.append(entry.getKey()).append("=");
				
				try {
					param.append(URLEncoder.encode(entry.getValue(), DEFAULT_CHARSET));
				} catch (UnsupportedEncodingException e) {
					//缂傛牜鐖滄径杈Е
				}
			}
			url += param.toString();
		}

		String body = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.custom()
					.setDefaultRequestConfig(REQUEST_CONFIG)
					.build();
			HttpGet httpGet = new HttpGet(url);
			response = httpClient.execute(httpGet);
			body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return body;
	}

	/**
	 * @description 閸旂喕鍏橀幓蹇氬牚: get 鐠囬攱鐪�
	 * @param url 鐠囬攱鐪伴崷鏉挎絻
	 * @return 鐠囬攱鐪版径杈Е鏉╂柨娲杗ull
	 */
	public static String get(String url) {
		return get(url, null);
	}

	/**
	 * @description 閸旂喕鍏橀幓蹇氬牚: get 鐠囬攱鐪�
	 * @param url 鐠囬攱鐪伴崷鏉挎絻
	 * @param params 閸欏倹鏆�
	 * @return 鐠囬攱鐪版径杈Е鏉╂柨娲杗ull
	 */
	public static String get(String url, Map<String, String> params) {
		return get(url, params, null);
	}

	/**
	 * @description 閸旂喕鍏橀幓蹇氬牚: post 鐠囬攱鐪�
	 * @param url 鐠囬攱鐪伴崷鏉挎絻
	 * @param params 閸欏倹鏆�
	 * @return 鐠囬攱鐪版径杈Е鏉╂柨娲杗ull
	 */
	public static String post(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<>();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, String> entry : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		String body = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.custom()
					.setDefaultRequestConfig(REQUEST_CONFIG)
					.build();
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, DEFAULT_CHARSET));
			response = httpClient.execute(httpPost);
			body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return body;
	}

	/**
	 * @description 閸旂喕鍏橀幓蹇氬牚: post 鐠囬攱鐪�
	 * @param url 鐠囬攱鐪伴崷鏉挎絻
	 * @param s 閸欏倹鏆焫ml
	 * @return 鐠囬攱鐪版径杈Е鏉╂柨娲杗ull
	 */
	public static String post(String url, String s) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = new HttpPost(url);
		String body = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.custom()
					.setDefaultRequestConfig(REQUEST_CONFIG)
					.build();
			httpPost.setEntity(new StringEntity(s, DEFAULT_CHARSET));
			response = httpClient.execute(httpPost);
			body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return body;
	}

	/**
	 * @description 閸旂喕鍏橀幓蹇氬牚: post https鐠囬攱鐪伴敍灞炬箛閸斺�虫珤閸欏苯鎮滅拠浣峰姛妤犲矁鐦�
	 * @param url 鐠囬攱鐪伴崷鏉挎絻
	 * @param params 閸欏倹鏆�
	 * @return 鐠囬攱鐪版径杈Е鏉╂柨娲杗ull
	 */
	 public static String posts(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<>();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, String> entry : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		String body = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.custom()
					.setDefaultRequestConfig(REQUEST_CONFIG)
					.setSSLSocketFactory(getSSLConnectionSocket())
					.build();
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, DEFAULT_CHARSET));
			response = httpClient.execute(httpPost);
			body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return body;
	}
	
	/**
	 * @description 閸旂喕鍏橀幓蹇氬牚: post https鐠囬攱鐪伴敍灞炬箛閸斺�虫珤閸欏苯鎮滅拠浣峰姛妤犲矁鐦�
	 * @param url 鐠囬攱鐪伴崷鏉挎絻
	 * @param s 閸欏倹鏆焫ml
	 * @return 鐠囬攱鐪版径杈Е鏉╂柨娲杗ull
	 */
	public static String posts(String url, String s) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = new HttpPost(url);
		String body = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.custom()
					.setDefaultRequestConfig(REQUEST_CONFIG)
					.setSSLSocketFactory(getSSLConnectionSocket())
					.build();
			httpPost.setEntity(new StringEntity(s, DEFAULT_CHARSET)); 
			response = httpClient.execute(httpPost);
			body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return body;
	}

	//鑾峰彇ssl connection閾炬帴
		private static SSLConnectionSocketFactory getSSLConnectionSocket() {
			return new SSLConnectionSocketFactory(wx_ssl_context, new String[] {"TLSv1", "TLSv1.1", "TLSv1.2"}, null,
					null);
		}
}