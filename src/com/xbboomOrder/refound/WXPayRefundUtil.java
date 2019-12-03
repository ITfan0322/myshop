package com.xbboomOrder.refound;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.xbboomOrder.utils.XMLUtil;




/** 
 * @author: py
 * @version:2017年1月10日 上午11:17:59 
 * qwy.wx.wxpay.util.WXPayRefundUtil.java
 * @Desc  微信退款 申请以及退款
 * //api地址：http://mch.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
 */
public class WXPayRefundUtil {

	String appid = "wxd3270e7769759ce7"; //微信公众号apid
	String appsecret = "20ae66705a29fe927544a1dfcd02b96b"; //微信公众号appsecret
	public static String mch_id = "1541631801";  //微信商户id
	String op_user_id = "1541631801";//操作员帐号, 默认为商户号
	String partnerkey = "08dc51acad3502c6777c36962a8483a1";//商户平台上的那个KEY
	/**
	 * @date 2017年1月10日上午10:36:50 void
	 * @Des:退款
	 */
	public Map wechatRefund(String out_refund_no,String out_trade_no,String total_fee,String refund_fee,String nonce_str) {
		// 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
		/*String out_refund_no = "4200000054201804158496114407";
		String out_trade_no = "1523763907852";// 商户侧传给微信的订单号
		String total_fee = "1";// 总金额
		String refund_fee = "1";// 退款金额
		String nonce_str = "20bdx4b25826bfdsf";// 随机字符串
		 */		
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("op_user_id", op_user_id);
		RequestHandler reqHandler = new RequestHandler(
				null, null);
		reqHandler.init(appid, appsecret, partnerkey);
		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + 
				 "<appid>" + appid + "</appid>" +
				 "<mch_id>"+ mch_id + "</mch_id>" + 
				 "<nonce_str>" + nonce_str+ "</nonce_str>" + 
				 "<sign><![CDATA[" + sign + "]]></sign>"+
				 "<out_trade_no>" + out_trade_no + "</out_trade_no>"+
				 "<out_refund_no>" + out_refund_no + "</out_refund_no>"+
				 "<total_fee>" + total_fee + "</total_fee>"+ 
				 "<refund_fee>" + refund_fee + "</refund_fee>"+
				 "<op_user_id>" + op_user_id + "</op_user_id>" + 
				 "</xml>";
		
		/*<xml>
		   <appid>wx2421b1c4370ec43b</appid>
		   <mch_id>10000100</mch_id>
		   <nonce_str>6cefdb308e1e2e8aabd48cf79e546a02</nonce_str>
		   <op_user_id>10000100</op_user_id>
		   <out_refund_no>1415701182</out_refund_no>
		   <out_trade_no>1415757673</out_trade_no>
		   <refund_fee>1</refund_fee>
		   <total_fee>1</total_fee>
		   <transaction_id></transaction_id>
		   <sign>FE56DD4AA85C0EECA82C35595A69E153</sign>
		</xml>*/
		Map map = new HashMap<>();
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		try {
			String s= ClientCustomSSL.doRefund(createOrderURL, xml);
			System.out.println("s:"+s);
			
			map = XMLUtil.doXMLParse(s);
			
			//改变支付数据库中的是否退款
			//新增退款数据库数据
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * @date 2017年1月10日上午10:37:47 void
	 * @Des:退款查询
	 */
	public void querywechatRefund() {
		// 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
		String transaction_id = "656565";
		String nonce_str = "5616556165";// 随机字符串
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("transaction_id", transaction_id);
		RequestHandler reqHandler = new RequestHandler(
				null, null);
		reqHandler.init(appid, appsecret, partnerkey);
		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + 
				"<appid>" + appid + "</appid>" + 
				"<mch_id>"+ mch_id + "</mch_id>" + 
				"<nonce_str>" + nonce_str+ "</nonce_str>" + 
				"<sign><![CDATA[" + sign + "]]></sign>"+
				"<transaction_id>" + transaction_id + "</transaction_id>"+ 
				 "</xml>";
		
		/*<xml>
		   <appid>wx2421b1c4370ec43b</appid>
		   <mch_id>10000100</mch_id>
		   <nonce_str>0b9f35f484df17a732e537c37708d1d0</nonce_str>
		   <out_refund_no></out_refund_no>
		   <out_trade_no>1415757673</out_trade_no>
		   <refund_id></refund_id>
		   <transaction_id></transaction_id>
		   <sign>66FFB727015F450D167EF38CCC549521</sign>
		</xml>*/
		
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/refundquery";
		try {
			String s= ClientCustomSSL.doRefund(createOrderURL, xml);
			System.out.println("s:"+s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void main(String[] args) {
		WXPayRefundUtil refund=new WXPayRefundUtil();
		System.out.println(refund.wechatRefund(System.currentTimeMillis()+"","NiMO1561714636","1","1",System.currentTimeMillis()/1000+""));//申请退款
		//refund.querywechatRefund();//查询退款
	}
	
	

}