package com.xbboomOrder.bean;

public class GhShop {
	int id;
	String name;
	String phone;
	String pwd;
	String dates;
	int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "GhShop [id=" + id + ", name=" + name + ", phone=" + phone + ", pwd=" + pwd + ", dates=" + dates
				+ ", status=" + status + "]";
	}
	
}
