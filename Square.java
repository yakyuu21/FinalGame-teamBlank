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
import java.io.Serializable;

/**
 * This class represents the individual squares on {@link Board} and the type
 * of object it represents.  The square can be empty, a room, a player, a ninja,
 * or an item.  It will have string values depending on its type, and whether the
 * square is hidden or not.
 */
public class Square implements Serializable{
	private boolean isNinja;
	private boolean isPlayer;
	private boolean isRoom;
	private boolean isItem;
	private boolean isEmpty;

	private boolean isBrief;
	private boolean hide;
	private boolean reveal;
	private Character player;
	private Character ninja;
	private Item itemPresent;

	/**
	 * This field represents the string of the square type
	 */
	
	private String display;
	/**
	 * This field represents a square that isn't visible to the user
	 */
	private String displayHidden = " * ";

	/**
	 * default Constructor, the square is set as empty and hidden
	 */
	public Square() { 
		isEmpty = true;
		isPlayer = false;
		isNinja = false;
		isRoom = false;
		isItem = false;
		reveal = false;
		hide = true;
		display = "   ";
	}
	
	
	/**
	 * This method will set the square to empty
	 */
	public void setEmpty(){
		isEmpty = true;
		isPlayer = false;
		isRoom = false;
		isItem = false;
		player = null;
		hide = true;
		display = "   ";
	}
	
	/**
	 * This method will set the square as a character
	 * @param charac the player or the ninja
	 * @param x x coordinate integer
	 * @param y y coordinate integer 
	 */
	public void setCharacter(Character charac, int x, int y) { //set character in location
		if ((x == 1 || x == 4 || x == 7)&&
		(y == 1 || y == 4 || y == 7)){ //when inside room
			player = null;
			isPlayer = false;
			hide = false;
			isRoom = true;
			display = "[P]";
		}
		else{ //when not inside room
			player = charac;
			isPlayer = true;
			display = " P ";
			hide = false;
		}
	}
	
	/**
	 * This method will remove the item from the square
	 */
	public void setItemnull() {
		if(itemPresent!=null) 
			itemPresent = null;
		isItem = false;
	}

	/**
	 * This method represents the square after the player has moved from it.
	 * It shows when the player moves out of the room, moves on top of an object, or
	 * moves away from an empty square.
	 * @param x x coordinate of the player
	 * @param y y coordinate of the player
	 */
	public void playerMoved(int x, int y) { //when player moves out of room
		if ((x == 1 || x == 4 || x == 7)&&(y == 1 || y == 4 ||y == 7)){
				player = null;
				isPlayer = false;
				hide = false;
				isRoom = true;
				display = "[_]";
		}
		else{
			if(isItem) {
				player = null;
				isPlayer = false;
				hide = true;
				isEmpty = false;
				display = itemPresent.getType();
			}
			else { 
				player = null;
				isPlayer = false;
				hide = true;
				isEmpty = true;
				display = "   ";
			}	
		}	
	}


	/**
	 * This method sets the square as a power up
	 * @param item ammo, radar, or invincible
	 */
	public void setItem(Item item) {
		itemPresent = item;
		hide = true;
		display = itemPresent.getType();
		isItem = true;
	}
	
	/**
	 * This method sets the square to a ninja
	 * @param charac the ninja object
	 * @param x x location of the ninja
	 * @param y y location of the ninja
	 */
	public void setNinja(Character charac,int x, int y) {
		ninja = charac;
		isNinja = true;
		hide = true;
		if(isPlayer) 
			display = " P ";
		else
			display = " N "; 
		isEmpty = false;
		ninja.setX(x);
		ninja.setY(y);		
	}
	
	/**
	 * This method will set the square to a room
	 */
	public void setRoom() {
		display = "[_]";
		hide = false;
		isRoom = true;
		isBrief = false;
	}
	
	public void toggleHide() {
		hide = !hide;
	}
	
	/**
	 * This method will will reveal the briefcase in the room depending on whether
	 * debug mode is on, or if the player picked up a radar if not, it will show the
	 * rooms as hidden
	 * @param isDebugOn boolean value that represents the game mode
	 * @param hasRadar boolean value that represents if the player picked up the ammo or not
	 */
	public void briefExist(boolean isDebugOn, boolean hasRadar) {
		if (!isDebugOn && !hasRadar) {//if debug mode is not on and spy does not have radar
			display = "[_]";
			hide = false;
			isRoom = true;
			isBrief = true;
		}
		else { //if debug mode is on or if spy has radar
			display = "[#]";
			hide = false;
			isRoom = true;
			isBrief = true;
		}

	}


	/**
	 * This method will return if the square is a ninja or not
	 * @return true if ninja, false if not
	 */
	public boolean getNinja() {
		return isNinja;
	}
	
	/**
	 * This method will return if the square is a player or not
	 * @return true if player, false if not
	 */
	public boolean getPlayer() {
		return isPlayer;
	}
	
	/**
	 * This method will return if the square is an item or not
	 * @return true if item, false if not
	 */
	public boolean getItem() {
		return isItem;
	}
	
	/**
	 * This method will return if the square is empty or not
	 * @return true if square is empty false otherwise
	 */
	public boolean getEmpty() {
		return isEmpty;
	}
	
	/**
	 * This method will return if the square is a room or not
	 * @return true if square is a room false otherwise
	 */
	public boolean getRoom() {
		return isRoom;
	}

	/**
	 * This method will return the string representation of the item
	 * @return String representation of the item
	 */
	public String getItemtype() {
		return itemPresent.name();
	}
	
	/**
	 * This method will return the player object
	 * @return player object
	 */
	public Character getPlayerObj() {
		return player;
	}
	
	/**
	 * This method will return the ninja object
	 * @return ninja object
	 */
	public Character getNinjaObj() {
		return ninja;
	}
	
	/**
	 * This method will return if a room has a briefcase
	 * @return true if room has the briefcase, other wise return false
	 */
	public boolean getBrief() {
		return isBrief;
	}

	/**
	 * This method will return the string representation of the square
	 * @return display if hide is off, if hide is on it will return displayHidden
	 */
	public String display() {
		if(!hide) //if something visible
			return display;
		else if(reveal) {
			reveal();
			return display;
		}
		else 
			return displayHidden;
	}

	/**
	 * This method will reveal the briefcase in a room
	 */
	public void revealBrief() {
		display = "[#]";
	}
	
	/**
	 * This method will toggle reveal
	 */
	public void reveal() {
		reveal = !reveal;
	}
	
	/**
	 * This method will change the square from a ninja to empty
	 */
	public void removeNinja() {
		isNinja = false;
		if(itemPresent != null)
			display = itemPresent.getType();
		else if(isPlayer){
			display = " P ";
		}
		else {
			display = "   ";
			isEmpty = true;
		}
	}
	
	/**
	 * This method will set hide to false making it that square visible to the player
	 */
	public void makeVisible() {
		hide = false; 
	}
	
	/**
	 * This method will remove the ninja from the square
	 */
	public void killNinja() {
		ninja.die();
		removeNinja();
	}
	
	/**
	 * This method will return the string representation of the square
	 * @return display String representation of Square
	 */
	public String getDisplay() {
		return display;
	}
	
}
