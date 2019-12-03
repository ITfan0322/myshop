package com.xbboomOrder.bean;

public class Mycar {
	int id;
	int mid;
	String openid;
	int num;
	String menus;
	String size;
	String colors;
	String sid;
	String sname;
	int smid;
	
	public int getSmid() {
		return smid;
	}
	public void setSmid(int smid) {
		this.smid = smid;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getMenus() {
		return menus;
	}
	public void setMenus(String menus) {
		this.menus = menus;
	}
	
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColors() {
		return colors;
	}
	public void setColors(String colors) {
		this.colors = colors;
	}
	@Override
	public String toString() {
		return "Mycar [id=" + id + ", mid=" + mid + ", openid=" + openid + ", num=" + num + ", menus=" + menus + "]";
	}
	
}
