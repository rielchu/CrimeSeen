package com.example.sqlite.model;

import java.util.Date;

public class user_entity {
	private int id;
	private String password;
	private String username;
	private String sex;
	private String city_address;
	private String birthdate;
	private String last_login;
	
	public user_entity(int id, String username, String password, String sex, String city_address, String birthdate, String last_login)
	{
		this.id=id;
		this.username=username;
		this.password=password;
		this.sex=sex;
		this.city_address=city_address;
		this.birthdate=birthdate;
		this.last_login=last_login;		
	}
	
	public user_entity(String username, String password, String sex, String city_address, String birthdate, String last_login)
	{
		this.username=username;
		this.password=password;
		this.sex=sex;
		this.city_address=city_address;
		this.birthdate=birthdate;
		this.last_login=last_login;		
	}
	
	public user_entity(){}
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCity_address() {
		return city_address;
	}
	public void setCity_address(String city_address) {
		this.city_address = city_address;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getLast_login() {
		return last_login;
	}

	public void setLast_login(String last_login) {
		this.last_login = last_login;
	}

	
	
	
	

}
