package edu.cpp.cs.cs141.final_prog_assignment;
import java.util.Random;

public class GameEngine {
	
	private boolean wall;
	Random random = new Random();
	
	private Characters player;
	private Board board;
	private PowerUp radar = null;
	private PowerUp invincible = null;
	private PowerUp ammo = null;
	
	public void play() {
		wall = false;
		while(!wall) {
			yourTurn();
		}
	}
	
	public void yourTurn() {
		
	}
	
	public boolean wallExist() {
		return true;
	}
	
	public void createBoard() {
		player = new Characters(0,8);
		board = new Board();
		createPowerUp();
		
	}
	
	private void createPowerUp() {
		int x, y;
		while(radar == null) {
			x = getRandom();
			y = getRandom();
			if(board.at(x, y) != "O") {}
			else {
				radar = new PowerUp("radar", x, y);
				board.overWrite(x,y,"r");
			}
		}
		while(invincible == null) {
			x = getRandom();
			y = getRandom();
			if(board.at(x,y) != "O") {}
			else {
				invincible = new PowerUp("invincible", x, y);
				board.overWrite(x,y,"i");;
			}
		}
		while(ammo == null) {
			x = getRandom();
			y = getRandom();
			if(board.at(x,y) != "O") {}
			else {
				ammo = new PowerUp("ammo", x, y);
				board.overWrite(x,y,"a");
			}
		}	
	}
	
	public int getRandom(){
		return random.nextInt(9);
	}
	
}


