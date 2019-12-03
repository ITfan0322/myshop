package com.xbboomOrder.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSONObject;

import oracle.net.aso.i;

public class test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		
//		int i=0;
//		int j=100;
//		timer4(i);
//		timer4(j);
		WebSocket ws = new WebSocket();
		JSONObject jo = new JSONObject();
		jo.put("message", "这是后台返回的消息！");
		//jo.put("To",invIO.getIoEmployeeUid());
		ws.onMessage(jo.toString());
	}
	public static void timer4(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 26);
        calendar.set(Calendar.SECOND, 0);

        Date time = calendar.getTime();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	System.out.println("Local Time is " + i);
            }
        }, 0, 2000);// 这里设定将延时每天固定执行
    }

}
