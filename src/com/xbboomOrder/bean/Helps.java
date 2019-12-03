package com.xbboomOrder.bean;

public class Helps {
	int id;
	String myopenid;
	String otheropenid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMyopenid() {
		return myopenid;
	}
	public void setMyopenid(String myopenid) {
		this.myopenid = myopenid;
	}
	public String getOtheropenid() {
		return otheropenid;
	}
	public void setOtheropenid(String otheropenid) {
		this.otheropenid = otheropenid;
	}
	@Override
	public String toString() {
		return "Helps [id=" + id + ", myopenid=" + myopenid + ", otheropenid=" + otheropenid + "]";
	}
	
}
