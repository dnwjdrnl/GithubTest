package model;

public class HobbyVO {
	private String hid;
	private String name;
	
	
	public HobbyVO(String hid) {
		super();
		this.hid = hid;
	}
	public HobbyVO(String hid, String name) {
		super();
		this.hid = hid;
		this.name = name;
	}
	public String getHid() {
		return hid;
	}
	public void setHid(String hid) {
		this.hid = hid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
