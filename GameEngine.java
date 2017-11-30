/**
 * CS 141: Intro to Programming and Problem Solving
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #N
 *
 * <description-of-assignment>
 * This is a team project in which we implement a 2D game containing
 * various objects: rooms, power ups, player, and ninjas.
 * The goal of this game is to have the user interact with different 
 * entities in the program and to complete the objective of finding
 * the briefcase.
 * 
 * Team Blank
 * 	
 * Justen Minamitani
 * Saroj Poudel
 * Steve Marrero
 * Aaron Lim
 * Koshi Huynh
 * Jon Camarillo
 */
package edu.cpp.cs.cs141.final_prog_assignment;

import java.util.Random;


import java.util.Random;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This class determines all the actions that takes place within the game
 * from player action to random ninja movments and random items spawns.
 */
public class GameEngine implements Serializable{

	private Random random = new Random();

	/**
	 * This field represents the player the user controls. Only 1 player
	 * may be active in the board at all times.
	 */
	private Character player;
	
	/**
	 * This field represents the board of the game
	 */
	private Board board;
	
	/**
	 * This field creates the 6 ninjas that are in play
	 */
	private Character[] ninjas = new Character[6];
	
	private Item radar = null;
	private Item invincible = null;
	private Item ammo = null;
	
	/**
	 * This field represents the number of ninjas that are left on the board
	 */
	private int numNinja;
	
	/**
	 * This field represents the difficulty of the game
	 */
	private int difficulty;
	
	/**
	 * This field represents if the player picked up the radar or not
	 */
	private boolean hasRadar;

	/**
	 * This method will create the board, player, items, and ninjas.
	 * It will set the player and rooms in the initial position while
	 * setting the items and ninjas in random spaces.
	 * The default setting of the board does not have the radar active
	 * making the briefcase hidden.
	 */
	public void createBoard() {
		player = new Character(false,0,8);
		board = new Board();
		board.set(player, 8, 0);
		createPowerUp();	
		createNinjas();
		hasRadar = false;
	}


	/**
	 * This method will create new ninjas {@link #ninjas} and set the on the board at
	 * random coordinates which the {@Square} is empty
	 */
	public void createNinjas() {
		int x,y;
		int count = 0;

		while( count <= 5 ) {
			do {
				x = getRandom();
				y = getRandom();
			}while(x >= 5 && y <= 3);

			if(!board.at(x, y).getNinja() 
					&& board.at(x, y).getEmpty() 
					&& !board.at(x, y).getRoom() 
					&& !board.at(x, y).getItem()){ 
				ninjas[count] = new Character(x,y);	
				board.setNinja(ninjas[count],x,y);

				ninjas[count].setX(x);
				ninjas[count].setY(y);
				count ++;
			}
		}
	}

	/**
	 * This method will create {@link Item} radar, invincible, and ammo
	 * and set them on the board at random locations in the board
	 */
	private void createPowerUp() {
		int x, y;
		/**
		 * Items weren't populated when the player chose to start another new game.  
		 * Items are initialized as null to act as a reset.
		 */
		radar = null;
		invincible = null;
		ammo = null;

		/**
		 * will loop until one of each item is created
		 */
		while(radar == null) { 
			x = getRandom();
			y = getRandom();
			if(!board.at(x, y).getItem() && board.at(x, y).getEmpty() && !board.at(x, y).getRoom() && !board.at(x, y).getPlayer()) { //check if there is no item, not a room, and is empty
				radar = new Item("radar");
				board.set(radar, x, y);
			}
		}
		while(invincible == null) {
			x = getRandom();
			y = getRandom();
			if(!board.at(x, y).getNinja() && board.at(x, y).getEmpty() && !board.at(x, y).getRoom() && !board.at(x, y).getPlayer()) {//check if there is no item, not a room, and is empty
				invincible = new Item("invincible");
				board.set(invincible, x, y);
			}
		}
		while(ammo == null) {
			x = getRandom();
			y = getRandom();
			if(!board.at(x, y).getNinja() && board.at(x, y).getEmpty() && !board.at(x, y).getRoom() && !board.at(x, y).getPlayer()) {//check if there is no item, not a room, and is empty
				ammo = new Item("ammo");
				board.set(ammo, x, y);
			}
		}	
	}

	/**
	 * This method will get a random number from 0 to 8
	 * @return random integer from 0 to 8
	 */
	public int getRandom(){ 
		return random.nextInt(9);
	}

	/**
	 * This method will display the String representation of the item at x and y
	 * @param x integer representing the x coordinate
	 * @param y integer representing the y coordinate
	 * @return String representation of the object at the square
	 */
	public String displayBoard(int x, int y){
		return board.displayBoard(x, y);
	}

	/**
	 * This method will allow the user to see 2 spaces ahead of them. 
	 * First the method checks if the player is in a room or not.
	 * If in a room, isRoom = true, and player can only see north.
	 * Otherwise, two squares ahead are stored and checked for ninjas.
	 * @param direction String input from user which indicates which direction to see wasd
	 * @return true if spaces ahead isn't a room or wall, otherwise false
	 */
	public boolean look(String direction) {
		Square[] s = new Square[3];
		boolean clear = true;
		boolean isRoom = false;
		if((board.at(player.getX(), player.getY()).getRoom()))
				isRoom = true;
		switch(direction.toLowerCase()) {
			case "w":
				for(int i = 1;i < 3; i++) {
					if(player.getX()-i >= 0) {
						s[i] = board.at(player.getX()-i, player.getY());
					}			
				}
				break;
			case "a":
				if(isRoom)
					break;
				for(int i = 1;i < 3; i++) {
					if(player.getY()-i >= 0) {
						s[i] = board.at(player.getX(), player.getY()-i);
					}			
				}
				break;
			case "s":
				if(isRoom)
					break;
				for(int i = 1;i < 3; i++) {
					if(player.getX()+i <= 8) {
						s[i] = board.at(player.getX()+i, player.getY());
					}			
				}
				break;

			case "d":
				if(isRoom)
					break;
				for(int i = 1;i < 3; i++) {
					if(player.getY()+i <= 8) {
						s[i] = board.at(player.getX(), player.getY()+i);
					}			
				}
				break;
			default:
				System.out.print("Error: GameEngine-look-switch");
		}	
		/*
		 * This will now check whether or not the squares are Rooms, Ninjas,
		 * or all clear! This also reveals those squares to the user
		 * next time the board is printed.
		 */
		for(int i = 1; i < 3; i++) {
			if(s[i] != null) {
				if(s[i].getRoom()) {
					break;
				}
				else if(s[i].getNinja()) {
					s[i].reveal();
					clear = false;
				}
				else {
					s[i].reveal();
				}
			}
		}
		return clear;
	}
	
	/**
	 * This method moves the player 1 space in the direction indicated by the user.
	 * It cannot move past its boundaries, and cannot walk through walls.
	 * Walls are only accessible from the north side, and the player cannot walk over
	 * ninja while invincible. If player walks over a power up, they will use the items
	 * effect if valid.
	 * @param direction String input from the user wasd
	 * @return true if move is valid: if location isn't a room or wall
	 * otherwise return false
	 */
	public boolean move(String direction) {
		boolean moved = false;

		switch(direction){
			case"w":
				if((player.getX() == 0) ||
						(player.getX() == 1+1 ||player.getX() == 4+1 ||player.getX() == 7+1) &&
						(player.getY() == 1 ||	player.getY() == 4 ||player.getY() == 7)){
					System.out.println("WALL!");
					moved = false;

				}
				
				else {
					board.set(player, player.getX()-1, player.getY());
					board.move(player.getX()+1, player.getY());
					moved = true;
				}
				break;
			case"a":
				if((player.getY() == 0) ||
						(player.getX() == 1 || player.getX() == 4 ||	player.getX() == 7) &&
						(player.getY() == 1 || player.getY() == 4 ||	player.getY() == 7) || 
						(player.getY() == 1+1 ||player.getY() == 4+1 || player.getY() == 7+1) &&
						(player.getX() == 1 || player.getX() == 4 || player.getX() == 7)){
					System.out.println("WALL!");
					moved = false;

				}
				else{
					board.set(player, player.getX(), player.getY()-1);
					board.move(player.getX(), player.getY()+1);
					moved = true;
				}
				break;
			case"s":
				if((player.getX() == 8) ||
						(player.getX() == 1 ||player.getX() == 4 ||	player.getX() == 7) &&
						(player.getY() == 1 ||player.getY() == 4 ||player.getY() == 7) ||
						(player.getX() == 1 ||player.getX() == 4 ||player.getX() == 7) &&
						(player.getY() == 1 ||player.getY() == 4 ||	player.getY() == 7)){
					System.out.println("WALL!");
					moved = false;
				}

				else{
					board.set(player, player.getX()+1, player.getY());
					board.move(player.getX()-1, player.getY());
					moved = true;
				}
				break;
			case"d":
				if((player.getY() == 8) ||
						(player.getX() == 1 || player.getX() == 4 || player.getX() == 7) &&
						(player.getY() == 1 || player.getY() == 4 || player.getY() == 7) ||
						(player.getY() == 1-1 || player.getY() == 4-1 || player.getY() == 7-1) &&
						(player.getX() == 1 || player.getX() == 4 || player.getX() == 7)){
					System.out.println("WALL!");
					moved = false;

				}
				
				else{
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
	 * This method checks if the player location is the same as the briefcase location
	 * @return boolean is true if they're the same, false if they're not
	 */
	public boolean checkPlayerIsBriefcase(){
		if(player.getX() == board.getBriefcaseX() && player.getY() == board.getBriefcaseY())
			return true;
		else 
			return false;
	}

	/**
	 * This method sets the game state to debug mode, revealing the entire board
	 */
	public void debugMode() {
		board.debugMode(getHasRadar());		
	}
	
	/**
	 * This method returns the amount of ammo the player has 
	 * @return 1 if full ammo, 0 if no ammo
	 */
	public int getAmmoCount() {
		return player.getAmmo();
	}
	
	/**
	 * This method gets a direction indicated by the user wasd and shoots in that direction.
	 * The bullet will travel until a ninja is hit or it hits a wall or a room.
	 * Ammo will be depleted upon firing and will indicate that you cannot fire if you attempt
	 * with no ammo.
	 * @param direction String input from user that indicates which direction wasd
	 * @return String telling user what they hit
	 */
	public String shoot(String direction) {
		int value = 0;
		if(player.getAmmo() == 0)
			return "You are out of ammunition! Idiot! \n";
		if(direction.equals("s") || direction.equals("d"))
			value = 1;
		else
			value = -1;
		int x = player.getX();
		int y = player.getY();
		switch(direction) {
			case "w":
			case "s":
				for(int i = x + value; i >= 0 && i < 9; i+=value) {
					if(board.at(i, y).getNinja()) {
						board.at(i,y).killNinja();
						player.fire();
						return "You killed a Ninja!\n";
					}
					else if(board.at(i, y).getRoom()) {
						break;
					}
				}
				break;
			case "a":
			case "d":
				for(int j = y + value; j >= 0 && j < 9; j+=value) {
					if(board.at(x, j).getNinja()) {
						board.at(x,j).killNinja();
						player.fire();
						return "You killed a Ninja! \n";
					}
					else if(board.at(x,j).getRoom()) {
						break;
					}
				}
				break;
			default:
				System.out.println("Error: GameEninge-shoot-switch");
		}
		player.fire();
		return "No Ninjas were killed!\n";
	}

	/**
	 * This method checks to see if the ninja and players are at adjacent squares
	 * @return true if player is 1 space adjacent to ninja and false if not
	 */
	public boolean checkSpy() {
		int x = player.getX();
		int y = player.getY();
		int a, b;
		boolean value = false;
		
		for(int count = 0; count <= 5; count++) {
			if(!ninjas[count].getAlive())
				continue;
			a = ninjas[count].getX();
			b = ninjas[count].getY();
			
			if(board.at(x, y).getRoom())
				return value;
			
			if(!player.isInvincible()) {
				if(y == b) {
					if(a ==(x+1) || a == (x-1)) {
						reset(x,y);
						value = true;
					}
				}
				else if(x == a) {
					if(b == (y+1) || b == (y-1)) {
						reset(x,y);
						value = true;
					}
				}
				else if(x == 1 && y == b) {
					reset(x,y);
					value = true;
				}
			}
		}
		return value;
	}

	/**
	 * This method represents when the player gets killed by the ninja but still has atleast 1 or more lives.
	 * The player will be placed back into the initial starting position, and the location where the player
	 * was killed will be set to empty.
	 * @param x integer value for x coordinate
	 * @param y integer value for y coordinate
	 */
	public void reset(int x,int y) {
		player.decLives();
		board.set(player,8, 0);
		board.setEmpty(x, y);
	}

	/**
	 * This method represents the random movement of the ninja. The random movements
	 * depends on rng from 0 to 3.  This method will make sure the ninja will not
	 * move out of bounds, or into rooms.  If the ninja walks over item, {@link Square}
	 * will temporarily hold the value and upon moving, the item is returned.
	 */
	public void ninjaMovement() {
		for ( int i = 0; i <= 5; i++) { 
			if(ninjas[i].getAlive() == false)
				continue;

			int a = ninjas[i].getX();
			int b = ninjas[i].getY();
			int direction;
			int x = 0; 
			int y = 0;

			do {
				direction = random.nextInt(4);
				switch(direction) {
					case 0:
						x= a-1;
						y= b;
						break;
					case 1:
						x= a+1;
						y = b;
						break;
					case 2:
						x= a;
						y = b-1;
						break;
					case 3:
						x= a;
						y = b+1;
						break;
					default:
						System.out.println("Error @ ninjamovement default case");

				}
			}while(( x < 0 || x > 8) || (y < 0 || y > 8)|| board.at(x, y).getNinja()|| board.at(x,y).getRoom()); //keep within boundary

			boolean valid = false;
			while(!valid) { //check so ninjas do not go into room
				if(!board.at(x, y).getRoom()) {
					moveNinja(ninjas[i],direction,x,y);
					valid = true;
				}
				else
					valid = false;
			}
		}
	}

	/**
	 * This method will set the difficulty of the randomized ninja movements.  If the player
	 * is in the Ninjas line of sight, it will follow the player until the player is out of 
	 * its line of sight.
	 */
	public void useLineOfSightMovement()
	{
		for ( int i = 0; i <=5; i++) 
		{

			if(ninjas[i].getAlive() == false)
				continue;

			int a = ninjas[i].getX();
			int b = ninjas[i].getY();
			int direction;
			int x = 0; 
			int y = 0;
			boolean isLoop = false;
			if (senseSpyInLineOfSight(a,b) == true)
			{
				isLoop = true;
				int count0 = 0;
				int count1 = 0;
				int count2 = 0;
				int count3 = 0;
				direction = getNinjaDirection(a, b, player.getX(), player.getY());
				while (isLoop == true){

					switch(direction) {
						case 0: // up
							x= a-1;
							y= b;
							count0++;
							break;
						case 1: // down

							x= a+1;
							y = b;
							count1++;
							break;
						case 2: // left
							x= a;
							y = b-1;
							count2++;
							break;
						case 3: // right
							x= a;
							y = b+1;
							count3++;
							break;
						case 4: // added
							x = a;
							y = b;
							break;
						default:
							System.out.println("Error @ uselineofsightmovement default case");
					}

					// if ninja can't move there...
					if ( (x < 0 || x > 8 || y < 0 || y > 8)
							|| board.at(x, y).getNinja()==true
							|| board.at(x, y).getRoom() == true)
							//|| board.at(x, y).getPlayer() == true)
					{
						isLoop = true; // ...the do loop activates...
						direction = random.nextInt(4); //...and a random direction is assigned.
						if (count0 >= 1 && count1 >= 1 && count2 >= 1 && count3 >= 1) { // if ninja can't move at all, case 4 is activated; added count4
							isLoop = false;
							direction = 4;
							x = a;
							y = b;
						}
					} 
					else{
						isLoop = false;
						moveNinja(ninjas[i],direction,x,y);
					}
				}
			}

			else{
				isLoop = true;
				int count0 = 0;
				int count1 = 0;
				int count2 = 0;
				int count3 = 0;


				do
				{
					direction = random.nextInt(4);

					switch(direction) {
						case 0:
							x= a-1;
							y= b;
							count0++;
							break;
						case 1:
							x= a+1;
							y = b;
							count1++;

							break;
						case 2:
							x= a;
							y = b-1;
							count2++;

							break;
						case 3:
							x= a;
							y = b+1;
							count3++;

							break;
						case 4:
							x = a;
							y = b;
							break;
						default:
							System.out.println("Error @ useradialmovement else - default case");
							break;

					}
					if (count0 >= 1 && count1 >= 1 && count2 >= 1 && count3 >= 1) { // if ninja can't move, case 4 is activated.
						isLoop = false;
						direction = 4;
						x = a;
						y = b;
					}				
					else
					{
						isLoop = false;
					}
				} while(( x < 0 || x > 8) || (y < 0 || y > 8)
						|| (board.at(x, y).getNinja() == true && direction != 4)
						|| board.at(x,y).getRoom() == true 
						|| board.at(x, y).getPlayer() == true
						|| isLoop == true); //keep within boundary; added



				moveNinja(ninjas[i],direction,x,y);


			}
		}
	}

	/**
	 * This method sets the ninja movement to the hardest difficulty. It will follow the player
	 * if the player is within a radius of the ninja.
	 */
	public void useRadialMovement() {
		for ( int i = 0; i <=5; i++) 
		{ 
			if(ninjas[i].getAlive() == false)
				continue;

			int a = ninjas[i].getX();
			int b = ninjas[i].getY();
			int direction;
			int x = 0; 
			int y = 0;
			boolean isLoop = false;

			if (senseSpyInRad(a,b) == true) { // senseSpy() checks if spy is within a 3 unit radius of a ninja; return true if spy is nearby, false if not
				direction = getNinjaDirection(a, b, player.getX(), player.getY()); // specifies a direction for a ninja
				int count0 = 0;
				int count1 = 0;
				int count2 = 0;
				int count3 = 0;
				//loop that takes in the specified direction; if the ninja can't move to that location, direction is random
				do{

					switch(direction) {
						case 0: // up
							x= a-1;
							y= b;
							count0++;
							break;
						case 1: // down
							x= a+1;
							y = b;
							count1++;
							break;
						case 2: // left
							x= a;
							y = b-1;
							count2++;
							break;
						case 3: // right
							x= a;
							y = b+1;
							count3++;
							break;
						case 4: // stay (used only if a ninja can't move)
							x = a;
							y = b;
							break;
						default:
							System.out.println("Error @ useradialmovement default case");
							break;
					}

					// if ninja can't move somewhere...
					if (( x < 0 || x > 8) 
							|| (y < 0 || y > 8)
							|| (board.at(x, y).getNinja()==true && direction != 4)
							|| board.at(x, y).getRoom() == true)
							//|| board.at(x, y).getPlayer() == true){ //added
					{
						isLoop = true; // ...the do loop activates...
						direction = random.nextInt(4); //...and a random direction is assigned.
						if (count0 >= 1 && count1 >= 1 && count2 >= 1 && count3 >= 1) { // if ninja can't move, case 4 is activated.
							isLoop = false;
							direction = 4;
							x = a;
							y = b;
						}
					}
					else 
					{
						isLoop = false;
						moveNinja(ninjas[i],direction,x,y);

					}// if for some other reason a ninja can't move, the ninja will keep the same position
				}while (isLoop == true);

			}

			// if the ninja is not within 3 units radius of spy, then direction of ninja movement is random.
			else{
				isLoop = false;
				int count0 = 0;
				int count1 = 0;
				int count2 = 0;
				int count3 = 0;


				do {
					direction = random.nextInt(4);
					switch(direction) {
						case 0:
							x= a-1;
							y= b;
							count0++;
							break;
						case 1:
							x= a+1;
							y = b;
							count1++;

							break;
						case 2:
							x= a;
							y = b-1;
							count2++;

							break;
						case 3:
							x= a;
							y = b+1;
							count3++;

							break;
						case 4:
							x = a;
							y = b;
						default:
							System.out.println("Error @ useradialmovement else - default case");
							break;

					}
					if (count0 >= 1 && count1 >= 1 && count2 >= 1 && count3 >= 1) { // if ninja can't move, case 4 is activated.
						isLoop = false;
						direction = 4;
						x = a;
						y = b;
					}					
				} while(( x < 0 || x > 8) || (y < 0 || y > 8)
						|| (board.at(x, y).getNinja() == true && direction != 4)
						|| board.at(x,y).getRoom() == true 
						|| board.at(x, y).getPlayer() == true); //keep within boundary; added



				moveNinja(ninjas[i],direction,x,y);

			}
		}
	}


	/**
	 * This method will move the ninja in the x and y coordinate of the board.
	 * @param ninjas Character object of the ninja that has moved
	 * @param direction integer value of the random number generated
	 * @param x integer value for the x coordinate
	 * @param y integer value for the y coordinate
	 */
	public void moveNinja( Character ninjas, int direction, int x, int y) {
		switch(direction) {
			case 0:					
				board.removeNinja(x+1, y);
				board.setNinja(ninjas,x,y);
				break;
			case 1:	
				board.removeNinja(x-1, y);
				board.setNinja(ninjas,x,y);
				break;
			case 2:	
				board.removeNinja(x, y+1);
				board.setNinja(ninjas,x,y);
				break;	
			case 3:	
				board.removeNinja(x, y-1);
				board.setNinja(ninjas,x,y);
				break;	
			case 4: // added
				board.removeNinja(x, y);
				board.setNinja(ninjas,x,y);
			default:
				break;

		}

	}



	public int getNinjaDirection(int row, int column, int i, int j) // row column = ninja, i j = spy
	{
		int ninjaDirection = 0;
		int s;

		if (row < i && column < j) { // ninja in quadrant 3: spy = origin
			s = random.nextInt(2);
			if (s == 0)
				ninjaDirection =  1;
			else ninjaDirection = 3;
		}

		else if (row < i && column > j){
			s = random.nextInt(2);
			if (s == 0)
				ninjaDirection =  1;
			else ninjaDirection = 2;
		}
		else if (row > i && column < j){
			s = random.nextInt(2);
			if (s == 0)
				ninjaDirection =  0;
			else ninjaDirection = 3;
		}
		else if (row > i && column > j){
			s = random.nextInt(2);
			if (s == 0)
				ninjaDirection =  0;
			else ninjaDirection = 2;
		}
		else if (row == i && column < j)
			ninjaDirection = 3;
		else if (row == i && column > j)
			ninjaDirection = 2;
		else if (row < i && column == j)
			ninjaDirection = 1;
		else if (row > i && column == j)
			ninjaDirection = 0;
		else if (row == i && column == j)
		{
			ninjaDirection = 4;
		}
		else System.out.println("Error @ getninjadirection");
		return ninjaDirection;
	}


	/**
	 * This method will check to see if the player is within the ninjas line of sight.
	 * @param xNinja x coordinate of the ninja
	 * @param yNinja y coordinate of the ninja
	 * @return true if within xNina or yNinja
	 */
	public boolean senseSpyInLineOfSight(int xNinja, int yNinja)
	{
		boolean isSpyInSight = false;
		if (xNinja == player.getX() || yNinja == player.getY()){ // if ninja and spy are in same row or column
			if (xNinja < player.getX()) // same column and ninja above spy
			{
				for ( int i = xNinja + 1; i < player.getX(); i++) 
				{
					if (board.at(i, player.getY()).getNinja() || board.at(i, player.getY()).getRoom())
					{
						isSpyInSight = false;
						break;
					}
					else isSpyInSight = true;
				}
			}
			else if (xNinja > player.getX()) // same column and ninja below spy
			{
				for ( int i = xNinja - 1; i > player.getX(); i--)
				{
					if (board.at(i, player.getY()).getNinja() || board.at(i, player.getY()).getRoom())
					{
						isSpyInSight = false;
						break;
					}
					else isSpyInSight = true;

				}
			}
			else if (yNinja > player.getY()) // same row and ninja to the right of spy
			{
				for ( int i = yNinja -1 ; i > player.getY(); i--)
				{
					if (board.at(player.getX(), i).getNinja() || board.at(player.getX(), i).getRoom())
					{
						isSpyInSight = false;
						break;
					}
					else isSpyInSight = true;

				}
			}
			else if (yNinja < player.getY()) // same row and ninja to the left of spy
			{
				for ( int i = yNinja + 1; i < player.getY(); i++)
				{
					if (board.at(player.getX(), i).getNinja() || board.at(player.getX(), i).getRoom())
					{
						isSpyInSight = false;
						break;
					}
					else isSpyInSight = true;
				}
			}
			else
				isSpyInSight = false;
		}
		else 				
			isSpyInSight = false;

		return isSpyInSight;
	}



	/**
	 * This method will check to see if the player is within the radius of the ninja
	 * @param row integer x coordinate for ninja
	 * @param column integer y coordinate for ninja
	 * @return return true if player is within radius, otherwise false
	 */
	public boolean senseSpyInRad(int row, int column) // row = x coordinate for ninja, column = y coordinate for ninja
	{
		boolean isSpyNearby = false;
		for(int i = row - 3; i <= row + 3; i++) // changed
			for(int j = column - 3; j <= column + 3; j++){ // changed
				if (board.at(i, j).getPlayer() == true){
					isSpyNearby = true;
				}
			}
		return isSpyNearby;
	}

	/**
	 * This method will reduce the invincible counter by 1
	 */
	public void decInvincibility() {
		player.decInvincibility();
	}
	
	/**
	 * This method will return the current amount of invincible count
	 * @return invincible count
	 */
	public int invCount() {
		return player.getInvCount();
	}

	/**
	 * This method checks to see if player is alive
	 * @return player condition
	 */
	public boolean playerAlive() {
		return player.getAlive();
	}
	
	/**
	 * This method checks if the item is at the player location
	 * @return true if player is on item false if not
	 */
	public boolean checkItem() {
		int x = player.getX();
		int y = player.getY();
		if(board.at(x, y).getItem()) 
			return true;
		return false;
	}
	
	/**
	 * This method applies the item if the player walks over the item
	 * @return integer 0 to 3
	 */
	public int applyItem() {
		int x = player.getX();
		int y = player.getY();
		String type = board.at(x, y).getItemtype();

		switch(type) {
			case "radar" : //radar
				applyRadar();
				board.at(x, y).setItemnull();
				return 1;
			case "invincible": //invincible
				applyInvincible();
				board.at(x, y).setItemnull();
				return 2;
			case "ammo"://ammo
				if(player.getAmmo() == 0){
					applyAmmo();
					board.at(x, y).setItemnull();
					return 3;
				}
				return 0;
			default:
				return 0;
		}
	}

	/**
	 * This method applies the invincible item to the player
	 */
	public void applyInvincible() {
		player.setInvincible();
	}
	
	/**
	 * This method applies the ammo to the player.
	 */
	public void applyAmmo() {
		player.incAmmo();
	}


	/**
	 * This method activates the radar allowing the user to see the location of the briefcase.
	 */
	public void applyRadar() {
		hasRadar = true; // added: to be used in debug, briefcase will remain visible when switching debugmode on and off 
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) { //check every room and reveal the room with the briefcase
				if(board.at(i,j).getBrief())
					board.at(i,j).revealBrief();
			}
		}
	}
	
	/**
	 * This method saves the current conditions of the game onto a .dat file
	 * @param filename String input from user
	 * @return true if save is successful, returns false if IOException
	 */
	public boolean save(String filename) {
		try {
			FileOutputStream ofstream = new FileOutputStream(filename + ".dat") ;
			ObjectOutputStream outfile = new ObjectOutputStream(ofstream);

			outfile.writeObject(board);
			outfile.writeObject(ninjas);
			outfile.writeObject(player);

			outfile.writeObject(radar);
			outfile.writeObject(invincible);
			outfile.writeObject(ammo);
			outfile.writeObject(difficulty);
			outfile.close();
			return true;

		}
		catch(IOException e) {
			return false;
		}
	}
	
	/**
	 * This method will load a save file and allow the player to continue from their last progress
	 * @param filename String user input 
	 * @return true if file exist, false if file doesnt exist, IOException, or ClassNotFoundException
	 */
	public boolean load(String filename) {
		try {
			FileInputStream ifstream = new FileInputStream(filename + ".dat");
			ObjectInputStream infile = new ObjectInputStream(ifstream);

			board = (Board) infile.readObject();
			ninjas = (Character[]) infile.readObject();
			player = (Character) infile.readObject();
			radar = (Item) infile.readObject();
			invincible = (Item) infile.readObject();
			ammo = (Item) infile.readObject();
			difficulty = (int) infile.readObject();
			infile.close();
			return true;
		}catch(IOException | ClassNotFoundException e) {
			return false;
		}
	}

	/**
	 * This method will set the number of ninjas that are currently on the board
	 * by checking if the ninja object is alive or not
	 */
	public void setNumNinja() {
		int counter = 0;
		for(int i = 0; i < 6; i++) {
			if(ninjas[i].getAlive())
				counter++;
		}

		numNinja = counter;
	}

	/**
	 * This method will return the number of ninjas left on the board.  Since the player
	 * can fire a max of two times, the integer values should be from 4 to 6.
	 * @return numNinjas integer value from 4 to 6.
	 */
	public int getNumNinja() {
		setNumNinja();
		return numNinja;
	}

	/**
	 * This method will return the player object
	 * @return player 
	 */
	public Character getPlayer() {
		return player;
	}

	/**
	 * This method will set the difficulty of the game
	 * @param level represents the difficulty of the game
	 */
	public void setDifficulty(int level) {
		difficulty = level;
	}

	/**
	 * This method will set the ninja movements depending on the difficulty of the game
	 */
	public void ninjaDecision(){
		switch(difficulty) {
			case 1:
				ninjaMovement();
				break;
			case 2:
				useLineOfSightMovement();
				break;
			case 3:
				useRadialMovement();
				break;
		}
	}

	public boolean getHasRadar()
	{
		return hasRadar;
	}
}



