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
import java.io.Serializable;


/**
 * This class represents the board that contains {@link Square} 
 */
public class Board implements Serializable{
	private final int horizontal = 9;
	private final int vertical = 9;
	
	/**
	 * This field represents the board and the coordinates of the different
	 * type of {@link Square}
	 */
	private Square[][] room = new Square[vertical][horizontal];
	
	/**
	 * random object is used in conjunction with nextInt()
	 */
	private Random random = new Random();

	/**
	 * this field represents the possible "x" coordinate of the briefcase
	 */
	private int[] briefcaseArrayX = {1,4,7};
	
	/**
	 * This field represents the possible "y" coordinates of the briefcase
	 */
	private int[] briefcaseArrayY = {1,4,7};
	
	/**
	 * This field represents the "x" coordinates of the briefcase.
	 */
	private int briefcaseX;
	
	/**
	 * This field represents the "y" coordinates of the briefcase.
	 */
	private int briefcaseY;
	
	/**
	 * This field represents the state of the board visibility
	 */
	
	private boolean debugMode = false;
	
	/**
	 * This field represents if the room has the radar
	 */
	private boolean hasRadar;
	
	/**
	 * This is the default constructor, when called it will create the board
	 */
	public Board() {
		createBoard();
	}

	/**
	 * Creates board with room and briefcase.
	 */
	public void createBoard() {
		
		briefcaseX = briefcaseArrayX[random.nextInt(3)];
		briefcaseY = briefcaseArrayY[random.nextInt(3)];

		for(int x=0;x<horizontal;x++) {
			for(int y=0;y<vertical;y++) {
				if((y==1 || y==4 || y==7) && (x==1 || x==4 || x==7)) {
					if(x == briefcaseX && y == briefcaseY) {
						room[x][y] = new Square(); //with briefcase
						
						room[x][y].briefExist(debugMode, hasRadar);
					}
					else {
						room[x][y] = new Square();//without briefcase
						room[x][y].setRoom();
					}
				}
				else
					room[x][y] = new Square();
			}
		}
	}

	/**
	 * This method returns the {@link Square} string at location x and y
	 * @param x the x component of the {@link Square}
	 * @param y the y component of the {@link Square}
	 * @return the String display of the Square @ location
	 */
	public String displayBoard(int x, int y) {
		if(debugMode) {
			return room[x][y].getDisplay();
		}
		else
			return room[x][y].display();
	}	

	/**
	 * This method returns the {@link Square} object at location x and y of {@link #room}
	 * @param x an integer that represents the the row
	 * @param y an integer that represents the column
	 * @return Square at the coordinates
	 */
	public Square at(int x, int y) {
		try
		{
			return room[x][y];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return room[briefcaseX][briefcaseY]; // look at the briefcase room (used in senseSpy() in GameEngine)
		}
	}
	
	/**
	 * This method will set the player on the board
	 * @param player the character object representing the player
	 * @param x an integer value that indicates the row
	 * @param y an integer value that indicates the column
	 */
	public void set(Character player, int x, int y) {
		room[x][y].setCharacter(player, x, y);
		player.setX(x);
		player.setY(y);
	}
	
	/**
	 * This method is used to move player into another location.
	 * @param x refers to the new x location
	 * @param y refers to the new y location
	 */

	public void move(int x, int y) {	
		room[x][y].playerMoved(x, y);
	}
	
	/**
	 * Places the ninja at designated coordinates on the grid.
	 * @param ninjas refers to the ninja objects
	 * @param x refers to the row location
	 * @param y refers to the column location
	 */
	public void setNinja(Character ninjas, int x, int y) {
			room[x][y].setNinja(ninjas, x,y);
	}

	
	/**
	 * Places radar at designated row and column
	 * @param radar object
	 * @param x refers to the row
	 * @param y refers to the column
	 */
	public void set(Item radar, int x, int y) {
		room[x][y].setItem(radar);
	}

	/**
	 * used in GameEngine's checkPlayerIsBriefcase function
	 * @return x-coordinate of briefcase
	 */
	public int getBriefcaseX(){
		return briefcaseX;
	}
	
	/**
	 * used in GameEngine's checkPlayerIsBriefcase function
	 * @return y-coordinate of briefcase
	 */
	public int getBriefcaseY(){
		return briefcaseY;
	}
	
	/**
	 * Used to reveal all the elements on the game board or hide them.
	 * @param hasRadar determine whether or not the spy obtained the radar
	 */
	public void debugMode(boolean hasRadar) {
		debugMode = !debugMode;
		room[briefcaseX][briefcaseY].briefExist(debugMode, hasRadar);
		
	}
	
	/**
	 * This function sets {@link #room} to empty state
	 * @param x integer that is the x-coordinate
	 * @param y integer that is the y-coordinate
	 */
	public void setEmpty( int x, int y) {
		room[x][y].setEmpty();
	}
	
	/**
	 * This method removes the ninja from the board
	 * @param x integer that is the x-coordinate of ninja
	 * @param y integer that is the y-coordinate of ninja
	 */
	public void removeNinja(int x, int y) {
		room[x][y].removeNinja();
	}


}
