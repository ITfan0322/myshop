package com.xbboomOrder.bean;

public class Types {
	int id;
	String one;
	String two;
	String img;
	String jj;
	
	public String getJj() {
		return jj;
	}
	public void setJj(String jj) {
		this.jj = jj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOne() {
		return one;
	}
	public void setOne(String one) {
		this.one = one;
	}
	public String getTwo() {
		return two;
	}
	public void setTwo(String two) {
		this.two = two;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return "Types [id=" + id + ", one=" + one + ", two=" + two + ", img=" + img + "]";
	}
	
}
