package com.indogames.chits.action;

import com.indogames.chits.beans.Player;
import com.indogames.chits.beans.Role;
import com.indogames.chits.constants.GameConstants;
import com.indogames.chits.constants.Roles;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//import com.game.rrcp.bean.Player;
//import com.game.rrcp.bean.Role;
//import com.game.rrcp.constants.GameConstants;
//import com.game.rrcp.constants.Roles;

public class ThrowChits {

	/**
	 * @description Allocates a role to evey player randomly
	 * @return Array of players with roles assigned randomly
	 * 
	 * RAJA
	 * RANI
	 * POLICE
	 * CHOR
	 * 
	 *  */
	public Player[] throwchits(Player[] players) {

		ThrowChits rn = new ThrowChits();
		int[] chits = rn.chitIds();
		Role[] roles = Roles.setInitRoles();

		if (chits.length == players.length) {
			for (int i = 0; i < players.length; i++) {
				players[i].setRole(roles[chits[i]]);
				players[i].setScore(players[i].getRole().getPoints()); // Initialize score with assigned role's points
				if(players[i].getRole().getId() == GameConstants.ID_POLICE) {
					System.out.println(players[i].getPlayerName() + " is " + players[i].getRole().getRole());
				}
			}
		}
		return players;
	}
	
	/** 
	 * @param players Array of Player object
	 * @param clickedButton Id of the button clicked, to be considered as guessed chor
	 * @param players Array of Player - updated with the Roles assinged randomly
	 * */
	public boolean isChor(int clickedButton, Player[] players) {

		int chorIndex = -1;
		int chorViewId = -1;
		for (int in = 0; in < players.length; in++) {
			if (players[in].getRole().getId() == GameConstants.ID_CHOR) {
				chorIndex = in;
				chorViewId = players[in].getViewId();
			}
		}
		if(clickedButton == chorViewId) {
			return true; // identified the chor correct
		} else {
			return false; // guess was wrong fella!
		}
	}

	// Generates a random number
	private int getRandomNumber() {
		Random random = new Random();
		return random.nextInt(4);
	}

	// Provides a random sequence of numbers(0-3)
	private int[] chitIds() {
		int chitIds[] = { -1, -1, -1, -1 };
		int i = 0;
		while (i < chitIds.length) {

			int random = getRandomNumber();
			if (!isNumberExists(chitIds, random)) {
				chitIds[i] = random;
				i++;
			}
		}
		return chitIds;
	}

	/**
	 * @param num- random generated number
	 * @param arr- Array of length 4, to get a random sequence of numbers
	 * 
	 * @implNote- Checks if value of "num" already exists in "arr"
	 */
	private boolean isNumberExists(int[] arr, int num) {
		for (int i=0; i<arr.length; i++) {
			if (arr[i] == num) {
				return true;
			}
		}
		return false;
	}
}
