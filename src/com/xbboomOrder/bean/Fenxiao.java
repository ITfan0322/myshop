package com.xbboomOrder.bean;

public class Fenxiao {
	int id;
	int grade;
	double one;
	double two;
	String ppname;
	int ghid;
	
	public String getPpname() {
		return ppname;
	}
	public void setPpname(String ppname) {
		this.ppname = ppname;
	}
	public int getGhid() {
		return ghid;
	}
	public void setGhid(int ghid) {
		this.ghid = ghid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public double getOne() {
		return one;
	}
	public void setOne(double one) {
		this.one = one;
	}
	public double getTwo() {
		return two;
	}
	public void setTwo(double two) {
		this.two = two;
	}
	@Override
	public String toString() {
		return "Fenxiao [id=" + id + ", grade=" + grade + ", one=" + one + ", two=" + two + ", ppname=" + ppname
				+ ", ghid=" + ghid + "]";
	}
	
	
}
