package com.xbboomOrder.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Randem {
	public static String getRandomChar(int length) {            //生成随机字符串   
        char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
        buffer.append(chr[random.nextInt(36)]);
        }
        return buffer.toString();
        }
	//获取n个月后的日期
	public String getDate(String inDate,int num) {
		Calendar c = Calendar.getInstance();//获得一个日历的实例   
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
        Date date = null;   
        try{   
            date = sdf.parse(inDate);//初始日期   
        }catch(Exception e){  

        }   
        c.setTime(date);//设置日历时间   
        c.add(Calendar.MONTH,num);//在日历的月份上增加n个月
        String strDate = sdf.format(c.getTime());//的到你想要得n个月后的日期   
        System.out.println(strDate);
        return strDate;
       
	}
	//比较两个日期大小
	public int compareDate(String date1,String date2) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date1).compareTo(sdf.parse(date2));
	}
}
