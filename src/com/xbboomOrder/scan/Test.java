package com.xbboomOrder.scan;

import com.xbboomOrder.utils.GetToken;

import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) {
		try {
			String token = new GetToken().getToken();
			System.out.println(token);
			new Scans().getminiqrQr("pages/detail/detail","1700","564", token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
