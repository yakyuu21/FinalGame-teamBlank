package edu.cpp.cs.cs141.final_prog_assignment;

import java.util.Random;

public class GameEngine {

	private Random random = new Random();
	private Square square = new Square();

	private Character player;
	private Board board;
	private Character[] ninjas = new Character[6];
	private Item radar = null;
	private Item invincible = null;
	private Item ammo = null;


	public void createBoard() {
		player = new Character(false,0,8);
		board = new Board();
		board.set(player, 8, 0);
		createPowerUp();	
		createNinjas();
	}

	private void createNinjas() {
		int count = 6;
		int i = 0;
		while(count > 0){
			int x = random.nextInt(5) + 4; //staying 3 units away from player initial position
			int y = random.nextInt(5) + 4; //staying 3 units away from player initial position
			if(!board.at(x, y).getNinja() && board.at(x, y).getEmpty() && !board.at(x, y).getRoom()) {//if ninja does not already exist in room
				ninjas[i] = new Ninja(x,y);
				board.setNinja(ninjas[i],x,y);
				i++;
				count--;//dec count until create 6 ninjas
			}
		}

	}

	private void createPowerUp() {
		int x, y;
		while(radar == null) {
			x = getRandom();
			y = getRandom();
			if(!board.at(x, y).getItem() && board.at(x, y).getEmpty() && !board.at(x, y).getRoom()) {
				radar = new Item("radar");
				board.set(radar, x, y);
			}
		}
		while(invincible == null) {
			x = getRandom();
			y = getRandom();
			if(!board.at(x, y).getNinja() && board.at(x, y).getEmpty() && !board.at(x, y).getRoom()) {
				invincible = new Item("invincible");
				board.set(invincible, x, y);
			}
		}
		while(ammo == null) {
			x = getRandom();
			y = getRandom();
			if(!board.at(x, y).getNinja() && board.at(x, y).getEmpty() && !board.at(x, y).getRoom()) {
				ammo = new Item("ammo");
				board.set(ammo, x, y);
			}
		}	
	}
	public int getRandom(){
		return random.nextInt(9);
	}
	public String displayBoard(int x, int y){
		return board.displayBoard(x, y);
	}

	public boolean look(String direction) {
		Square[] s = new Square[3];
		boolean clear = true;
		switch(direction.toLowerCase()) {
			case "w":
				for(int i = 1;i < 3; i++) {
					if(player.getX()-i >= 0) {
						s[i] = board.at(player.getX()-i, player.getY());
					}			
				}
				break;
			case "a":
				for(int i = 1;i < 3; i++) {
					if(player.getY()-i >= 0) {
						s[i] = board.at(player.getX(), player.getY()-i);
					}			
				}
				break;
			case "s":
				for(int i = 1;i < 3; i++) {
					if(player.getX()+i <= 8) {
						s[i] = board.at(player.getX()+i, player.getY());
					}			
				}
				break;

			case "d":
				for(int i = 1;i < 3; i++) {
					if(player.getY()+i >= 0) {
						s[i] = board.at(player.getX(), player.getY()+i);
					}			
				}
				break;
			default:
				System.out.print("Error: GameEngine-look-switch");
		}	
		for(int i = 1; i < 3; i++) {
			if(s[i] != null) {
				if(s[i].getRoom()) {
					break;
				}
				else if(s[i].getNinja()) {
					s[i].reveal();
					clear = false;
				}
				else
					s[i].reveal();
			}
		}
		return clear;
	}

	public boolean move(String direction) {
		boolean moved = false;

		switch(direction){
			case"w":
				if((player.getX() == 0) ||
				(player.getX() == 1+1 ||
				player.getX() == 4+1 ||
				player.getX() == 7+1) &&
				(player.getY() == 1 ||
				player.getY() == 4 ||
				player.getY() == 7))
				{
					System.out.println("WALL!");
					moved = false;

				}

				else
				{

					board.set(player, player.getX()-1, player.getY());
					board.move(player.getX()+1, player.getY());
					moved = true;
				}

				break;
			case"a":
				if((player.getY() == 0) ||
						(player.getX() == 1 ||
						player.getX() == 4 ||
						player.getX() == 7) &&
						(player.getY() == 1 ||
						player.getY() == 4 ||
						player.getY() == 7) ||
						(player.getY() == 1+1 ||
						player.getY() == 4+1 ||
						player.getY() == 7+1) &&
						(player.getX() == 1 ||
						player.getX() == 4 ||
						player.getX() == 7))
				{
					System.out.println("WALL!");
					moved = false;

				}
				else
				{
					board.set(player, player.getX(), player.getY()-1);
					board.move(player.getX(), player.getY()+1);
					moved = true;
				}
				break;
			case"s":
				if((player.getX() == 8) ||
						(player.getX() == 1 ||
						player.getX() == 4 ||
						player.getX() == 7) &&
						(player.getY() == 1 ||
						player.getY() == 4 ||
						player.getY() == 7) ||
						(player.getX() == 1 ||
						player.getX() == 4 ||
						player.getX() == 7) &&
						(player.getY() == 1 ||
						player.getY() == 4 ||
						player.getY() == 7))
				{
					System.out.println("WALL!");
					moved = false;

				}
				else
				{
					board.set(player, player.getX()+1, player.getY());
					board.move(player.getX()-1, player.getY());
					moved = true;
				}
				break;
			case"d":
				if((player.getY() == 8) ||
						(player.getX() == 1 ||
						player.getX() == 4 ||
						player.getX() == 7) &&
						(player.getY() == 1 ||
						player.getY() == 4 ||
						player.getY() == 7) ||
						(player.getY() == 1-1 ||
						player.getY() == 4-1 ||
						player.getY() == 7-1) &&
						(player.getX() == 1 ||
						player.getX() == 4 ||
						player.getX() == 7))
				{
					System.out.println("WALL!");
					moved = false;

				}
				else
				{
					board.set(player, player.getX(), player.getY()+1);
					board.move(player.getX(), player.getY()-1);
					moved = true;
				}
				break;
			default:
				moved = true;
				break;

		}
		return moved;

	}
	
	/**
	 * checks if the player location is the same as the briefcase location
	 * @return boolean is true if they're the same, false if they're not
	 */
	public boolean checkPlayerIsBriefcase()
	{
		if(player.getX() == board.getBriefcaseX() && player.getY() == board.getBriefcaseY())
		{
			return square.getBrief();;

		}
		else 
			return false;
	}
	
	/*	
	public String shoot(String direction) {
		int value = 0;
		if(player.getAmmo() == 0)
		if(direction.equals("w") || direction.equals("s"))
			value = 1;
		else
			value = -1;
		int x = player.getX();
		int y = player.getY();
		switch(direction) {
		case "w":
		case "d":
			for(int i = x + value; i > 0 && i < 9; i+=value) {
				if(board.at(i, y).getNinja()) {
					killNinja(i, y);
					//player.shoot();
					return "You killed a Ninja!";
				}
			}
			break;
		case "a":
		case "s":
			for(int j = y + value; j > 0 && j < 9; j+=value) {
				if(board.at(x, j).getNinja()) {
					killNinja(x, j);
					//player.shoot();
					return "You killed a Ninja!";
				}
			}
			break;
			default:
				System.out.println("Error: GameEninge-shoot-switch");
		}
		return "No Ninjas were killed!";
	}


	private void killNinja(int x, int y) {
		for(int i = 0; i < ninjas.length; i++)
		{
			if(ninjas[i].getX() == x && ninjas[i].getY() == y)
				ninjas[i].die();
		}
	}
	 */

	public void debugMode() {
		board.debugMode();
		
	}


}



