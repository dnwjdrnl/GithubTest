package model;

import java.util.ArrayList;

public class MemberVO {
	private String id;
	private String password;
	private String name;
	private ArrayList<HobbyVO> hobby;
	
	public MemberVO(String id, String name, ArrayList<HobbyVO> hobby) {
		super();
		this.id = id;
		this.name = name;
		this.hobby = hobby;
	}
	public MemberVO(String id, String password, String name, ArrayList<HobbyVO> hobby) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.hobby = hobby;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<HobbyVO> getHobby() {
		return hobby;
	}
	public void setHobby(ArrayList<HobbyVO> hobby) {
		this.hobby = hobby;
	}
	
	
	

}
