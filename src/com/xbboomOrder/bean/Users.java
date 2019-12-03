package com.xbboomOrder.bean;

public class Users {
	int id;
	String openid;
	String phone;
	String dates;
	String address;
	String name;
	int news;
	String money;
	String scan;
	String mytjm;
	String toptjm;
	String grade;
	String img;
	
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getScan() {
		return scan;
	}
	public void setScan(String scan) {
		this.scan = scan;
	}
	public String getMytjm() {
		return mytjm;
	}
	public void setMytjm(String mytjm) {
		this.mytjm = mytjm;
	}
	public String getToptjm() {
		return toptjm;
	}
	public void setToptjm(String toptjm) {
		this.toptjm = toptjm;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getNews() {
		return news;
	}
	public void setNews(int news) {
		this.news = news;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", openid=" + openid + ", phone=" + phone + ", dates=" + dates + ", address="
				+ address + ", name=" + name + "]";
	}
	
	
	
}
