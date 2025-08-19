package com.aaseya.ITSM.dto;

public class LocationDetailsDTO {
	private String blockRoomNo;
	private String floor;
	private String branch;
	private String department;
	private String city;
	private String state;
	
	public String getFloor() {
		return floor;
	}
	public String getBlockRoomNo() {
		return blockRoomNo;
	}
	public void setBlockRoomNo(String blockRoomNo) {
		this.blockRoomNo = blockRoomNo;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "LocationDetailsDTO [blockRoomNo=" + blockRoomNo + ", floor=" + floor + ", branch=" + branch
				+ ", department=" + department + ", city=" + city + ", state=" + state + "]";
	}
	
	

}
