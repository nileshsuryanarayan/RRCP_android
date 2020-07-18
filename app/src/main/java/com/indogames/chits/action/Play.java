package com.indogames.chits.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.indogames.chits.beans.Player;
import com.indogames.chits.beans.Round;
import com.indogames.chits.constants.GameConstants;

public class Play {
	
//	private Players
	
	public static void main(String[] args) {
		
		Play play = new Play();
		play.letsPlay();
	}

	public void letsPlay() {
		List<Round> rounds = new ArrayList<Round>();
		
		for(int i=0; i<5; i++) {
			Player one = new Player();
			one.setPlayerName("One");
			Player two = new Player();
			two.setPlayerName("Two");
			Player three = new Player();
			three.setPlayerName("Three");
			Player four = new Player();
			four.setPlayerName("Four");
			
			Player[] players = { one, two, three, four };
			
			ThrowChits throe = new ThrowChits();
			
			System.out.println("Round - " + (i+1));
			int policeIndex = -1;
			int chorIndex = -1;
			
			// Allocates a role to evey player randomly- RAJA RANI CHOR POLICE
			players = throe.throwchits(players);
			
			for (int in = 0; in < players.length; in++) {
				if(players[in].getRole().getId() == GameConstants.ID_POLICE) {
					policeIndex = in;
				} else if (players[in].getRole().getId() == GameConstants.ID_CHOR) {
					chorIndex = in;
				}
			}
			
//			players = throe.identifyChor(players, policeIndex, chorIndex);
			
			Round round = new Round();
			round.setId(i);
			round.setPlayers(players);
			rounds.add(round);
		}
		
		System.out.println("-------Final score------");
		Play play = new Play();
		play.calculateFinalScore(rounds);
		
	}
	
	/** 
	 * @description generate final score of each player by adding individual scores of each round
	 * */
	private void calculateFinalScore(List<Round> rounds) {
		int p1TotalScore = 0;
		int p2TotalScore = 0;
		int p3TotalScore = 0;
		int p4TotalScore = 0;
		
		for(int r=0; r<rounds.size(); r++) {
			p1TotalScore = p1TotalScore + rounds.get(r).getPlayers()[0].getRole().getPoints();
			p2TotalScore = p2TotalScore + rounds.get(r).getPlayers()[1].getRole().getPoints();
			p3TotalScore = p3TotalScore + rounds.get(r).getPlayers()[2].getRole().getPoints();
			p4TotalScore = p4TotalScore + rounds.get(r).getPlayers()[3].getRole().getPoints();
		}
		Player[] players = rounds.get(0).getPlayers();
		
		players[0].setScore(p1TotalScore);
		players[1].setScore(p2TotalScore);
		players[2].setScore(p3TotalScore);
		players[3].setScore(p4TotalScore);
				
		Play play = new Play();
		play.whoWonTheGame(players, p1TotalScore, p2TotalScore, p3TotalScore, p4TotalScore);
	}
	
	/**
	 * @description will calculate the RAJA, RANI, POLICE, CHOR of the game on the basis of final scores
	 * */
	public void whoWonTheGame(Player[] players, int... finalScores) {
		
		Arrays.sort(finalScores);
		
		for(int i=0; i<players.length; i++) {
			if(players[i].getScore() == finalScores[0]) {
				players[i].getRole().setRole(GameConstants.TITLE_CHOR);
			} else if (players[i].getScore() == finalScores[1]) {
				players[i].getRole().setRole(GameConstants.TITLE_POLICE);
			} else if (players[i].getScore() == finalScores[2]) {
				players[i].getRole().setRole(GameConstants.TITLE_RANI);
			} else if (players[i].getScore() == finalScores[3]) {
				players[i].getRole().setRole(GameConstants.TITLE_RAJA);
			}
		}
		
		for(int i=0; i<players.length; i++) {
			System.out.println(players[i].getPlayerName() +" "+ players[i].getScore() + " " + players[i].getRole().getRole());
		}
		
	}
	
}
