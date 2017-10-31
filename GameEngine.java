/*
 * 
 */
package edu.coo.cs.cs141.final_prog_assignment;

public class GameEngine {
	private int playerX, playerY;
	private boolean wall;
	public void play() {
		wall = false;
		while(!wall) {
			youTurn();
		}
	}
	
	
	
	public void yourTurn() {
		
	}
	
	public boolean wallExist() {
		return true;
	}
	
	

}


