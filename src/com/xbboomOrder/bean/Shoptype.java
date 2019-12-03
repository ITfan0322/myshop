package com.xbboomOrder.bean;

public class Shoptype {
	int id;
	String name;
	String number;
	int px;
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getPx() {
		return px;
	}
	public void setPx(int px) {
		this.px = px;
	}
	@Override
	public String toString() {
		return "Shoptype [id=" + id + ", name=" + name + ", number=" + number + ", px=" + px + "]";
	}
	
}
