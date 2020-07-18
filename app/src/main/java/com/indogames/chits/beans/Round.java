package com.indogames.chits.beans;

import java.io.Serializable;

public class Round implements Serializable {

	private int id;
	private Player[] players;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Player[] getPlayers() {
		return players;
	}
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
}
