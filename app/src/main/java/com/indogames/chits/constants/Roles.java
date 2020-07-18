package com.indogames.chits.constants;

import com.indogames.chits.beans.Role;

public class Roles {
	
	// Role constants
	final static Role RAJA = new Role();
	final static Role RANI = new Role();
	final static Role POLICE = new Role();
	final static Role CHOR = new Role();

	public static Role[] setInitRoles( ) {
		
		RAJA.setId(0);
		RAJA.setRole(GameConstants.TITLE_RAJA);
		RAJA.setPoints(GameConstants.SCORE_1000);
		
		RANI.setId(1);
		RANI.setRole(GameConstants.TITLE_RANI);
		RANI.setPoints(GameConstants.SCORE_800);
		
		POLICE.setId(2);
		POLICE.setRole(GameConstants.TITLE_POLICE);
		POLICE.setPoints(GameConstants.SCORE_500);
		
		CHOR.setId(3);
		CHOR.setRole(GameConstants.TITLE_CHOR);
		CHOR.setPoints(GameConstants.SCORE_0);
		
		Role[] roles = {RAJA, RANI, POLICE, CHOR};
		
		return roles;
		
	}
	
}
