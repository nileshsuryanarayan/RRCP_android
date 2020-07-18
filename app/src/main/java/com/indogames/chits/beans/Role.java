package com.indogames.chits.beans;

import java.io.Serializable;

public class Role implements Serializable {

	private int id;
	private String role;
	private int points;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
}
