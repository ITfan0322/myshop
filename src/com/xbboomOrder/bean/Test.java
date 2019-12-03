package com.xbboomOrder.bean;

import com.xbboomOrder.utils.HttpRequestor;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String addr=new HttpRequestor().doGet("https://apis.map.qq.com/ws/location/v1/ip?key=T5UBZ-CJOLD-IYH4K-HH2EP-IL7YK-77BAF");
			System.out.println(addr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
