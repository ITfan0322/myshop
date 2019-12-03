package com.xbboomOrder.sms;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsStatusPuller;
import com.github.qcloudsms.SmsMobileStatusPuller;
import com.github.qcloudsms.SmsStatusPullCallbackResult;
import com.github.qcloudsms.SmsStatusPullReplyResult;
import com.github.qcloudsms.SmsVoiceVerifyCodeSender;
import com.github.qcloudsms.SmsVoiceVerifyCodeSenderResult;
import com.github.qcloudsms.SmsVoicePromptSender;
import com.github.qcloudsms.SmsVoicePromptSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.xbboomOrder.bean.Shops;

import org.json.JSONException;

import java.io.IOException;


public class QcloudSmsTest {

    public static void main(String[] args) {
        // 短信应用SDK AppID
        int appid = 1400231519; // 1400开头

        // 短信应用SDK AppKey
        String appkey = "af2b1f3add8130eabd7bbd748784e31c";

        // 需要发送短信的手机号码
        String[] phoneNumbers = {"17600196265"};

        // 短信模板ID，需要在短信应用中申请
        // NOTE: 这里的模板ID`7839`只是一个示例，
        // 真实的模板ID需要在短信控制台中申请
        int templateId = 371691;

        // 签名
        // NOTE: 这里的签名"腾讯云"只是一个示例，
        // 真实的签名需要在短信控制台中申请，另外
        // 签名参数使用的是`签名内容`，而不是`签名ID`
        String smsSign = "优趣科技";

        // 单发短信
        try {
      	  SmsMultiSender msender = new SmsMultiSender(appid, appkey);
      	  SmsMultiSenderResult result =  msender.send(0, "86", phoneNumbers,
      	      "【优趣科技】您有新的订单，请及时查看", "", "");
      	  System.out.println(result);
      	} catch (HTTPException e) {
      	  // HTTP 响应码错误
      	  e.printStackTrace();
      	} catch (JSONException e) {
      	  // JSON 解析错误
      	  e.printStackTrace();
      	} catch (IOException e) {
      	  // 网络 IO 错误
      	  e.printStackTrace();
      	}
        
        
        /*try {
        	  SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        	  SmsSingleSenderResult result = ssender.send(0, "86", phoneNumbers[0],
        	      "【腾讯云】您的验证码是: 5678", "", "");
        	  System.out.println(result);
        	} catch (HTTPException e) {
        	  // HTTP 响应码错误
        	  e.printStackTrace();
        	} catch (JSONException e) {
        	  // JSON 解析错误
        	  e.printStackTrace();
        	} catch (IOException e) {
        	  // 网络 IO 错误
        	  e.printStackTrace();
        	}*/
        
        
       /* try {
        	  String[] params = {""};
        	  SmsMultiSender msender = new SmsMultiSender(appid, appkey);
        	  SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers,
        	      templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
        	  System.out.println(result);
        	} catch (HTTPException e) {
        	  // HTTP 响应码错误
        	  e.printStackTrace();
        	} catch (JSONException e) {
        	  // JSON 解析错误
        	  e.printStackTrace();
        	} catch (IOException e) {
        	  // 网络 IO 错误
        	  e.printStackTrace();
        	}*/
       /* String[] phone= new String[5];
        for (int i = 0; i < 5; i++) {
			phone[i]=i+"";
		}
        for (int i = 0; i < 5; i++) {
			System.out.println(phone[i]);
		}
*/
        

       

        // 拉取单个手机短信状态
        
    }
}
