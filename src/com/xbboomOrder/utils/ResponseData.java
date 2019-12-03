package com.xbboomOrder.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 闁告帗绋戠紓鎾诲籍閸洘锛熼柨娑虫嫹2015妤犵儑鎷�10闁哄牞鎷�13闁哄喛鎷� 濞戞挸顑呭畷锟�11:28:08
 * 
 * @author andy
 * @version 2.2
 */

public class ResponseData  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6478017818699106018L;
	private List<?> list = null;
	private Object cont = null;

	public ResponseData(List<?> list, Object cont) {
		super();
		this.list = list;
		this.cont = cont;
	}

	public ResponseData() {
		super();
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Object getCont() {
		return cont;
	}

	public void setCont(Object cont) {
		this.cont = cont;
	}
}
