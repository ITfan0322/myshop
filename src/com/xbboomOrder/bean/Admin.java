package com.xbboomOrder.bean;

public class Admin {
	int id;
	String phone;
	String name;
	String pwd;
	String type;
	String shops;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getShops() {
		return shops;
	}
	public void setShops(String shops) {
		this.shops = shops;
	}
	@Override
	public String toString() {
		return "Admin [id=" + id + ", phone=" + phone + ", name=" + name + ", pwd=" + pwd + "]";
	}
	
}
