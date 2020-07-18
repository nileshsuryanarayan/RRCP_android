package com.indogames.chits.beans;

import java.io.Serializable;

public class Player implements Serializable {

	private int id;
	private int viewId;
	private String playerName;
	private String macId;
	private Role role;
	private int score;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getMacId() {
		return macId;
	}
	public void setMacId(String macId) {
		this.macId = macId;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getViewId() { return viewId; }
	public void setViewId(int viewId) { this.viewId = viewId; }
	
}
