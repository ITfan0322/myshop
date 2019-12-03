package com.xbboomOrder.bean;

public class ShopMenu {
	int id;
	int mid;
	int num;
	String size;
	String colors;
	String kucun;
	String dates;
	String number;
	String status;
	String types;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
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
	public String getKucun() {
		return kucun;
	}
	public void setKucun(String kucun) {
		this.kucun = kucun;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "ShopMenu [id=" + id + ", mid=" + mid + ", num=" + num + ", size=" + size + ", colors=" + colors
				+ ", kucun=" + kucun + ", dates=" + dates + ", number=" + number + "]";
	}
	
	
}
