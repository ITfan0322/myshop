package com.xbboomOrder.bean;

public class Menus {
	int id;
	String name;
	int num;//已售
	String price;//价格
	String colors;//颜色
	String size;//尺码
	int status;//出售状态
	String img;//封面图
	String types;//品牌
	String two;
	String imgs;//详情
	String shops;
	String ghid;
	
	public String getGhid() {
		return ghid;
	}
	public void setGhid(String ghid) {
		this.ghid = ghid;
	}
	public String getShops() {
		return shops;
	}
	public void setShops(String shops) {
		this.shops = shops;
	}
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getColors() {
		return colors;
	}
	public void setColors(String colors) {
		this.colors = colors;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getTwo() {
		return two;
	}
	public void setTwo(String two) {
		this.two = two;
	}
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	@Override
	public String toString() {
		return "Menus [id=" + id + ", name=" + name + ", num=" + num + ", price=" + price + ", colors=" + colors
				+ ", size=" + size + ", status=" + status + ", img=" + img + ", types=" + types + ", two=" + two
				+ ", imgs=" + imgs + "]";
	}
	
	
	
}
