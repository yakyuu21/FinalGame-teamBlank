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
 * This class represents any active character that is in the game.
 * Shows if the character is a player or ninja, and has the x and
 * y coordinates of the character.
 */
public class Character implements Serializable{
	private int lives;
	private int bullet;
	
	/**
	 * determines if the character has the briefcase or not
	 */
	private boolean briefcase;
	
	/**
	 * This field determines if the character is a ninja or the player.
	 */
	private boolean isNinja;
	
	/**
	 * this field represents the x and y coordinates of the character.
	 */
	private int locationX, locationY;
	
	/**
	 * This field represents if the character is still on the board
	 */
	private boolean isAlive;
	
	/**
	 * This field represents the counter for how many turns the player is
	 * invincible.
	 */
	private int isInvincible = 0;
	
	/**
	 * This default constructor creates the a ninja character
	 * @param x the x coordinate of the ninja
	 * @param y the y coordinate of the ninja
	 */
	public Character(int x, int y) {
		lives = 1;
		bullet = 0;
		briefcase = false;
		isNinja = true;
		isAlive = true;
		locationX = x;
		locationY = y;
	}
	
	/**
	 * This default constructor creates the player on the board
	 * @param brief represents if the player has the briefcase or not
	 * @param x the x coordinate of the player
	 * @param y the y coordinate of the player
	 */
	public Character(boolean brief,int x, int y) {
		lives = 3;
		bullet = 1;
		briefcase = brief;
		isNinja = false;
		isAlive = true;
		locationX = x;
		locationY = y;
	}
	
	/**
	 * This method decrements the lives of the character by 1. If the lives
	 * is zero the character is no longer on the board
	 */
	public void decLives() {
		if(lives > 0)
			lives--;
		if(lives == 0)
			isAlive = false;
	}
	
	/**
	 * This method returns the state of the player
	 * @return true if the player is invincible, false otherwise
	 */
	public boolean isInvincible() {
		if(isInvincible == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * This method returns how many turns the player is invincible
	 * @return isInvincible represents the number of turns the player is invincible for.
	 */
	public int getInvCount() {
		return isInvincible;
	}
	
	/**
	 * This method decrements the {@link #isInvincible} by 1 as long as it is greater than 0.
	 */
	public void decInvincibility() {
		if(isInvincible > 0)
			isInvincible--;
	}

	/**
	 * This method returns the x-coordinate of the character
	 * @return locationX the row of the character
	 */
	public int getX() {
		return locationX;
	}
	
	/**
	 * This method returns the y-coordinate of the character
	 * @return locationY the column of the character
	 */
	public int getY() {
		return locationY;
	}
	
	/**
	 * This method returns the number of lives remaining
	 * @return lives an integer value from 0 to 3
	 */
	public int getLives() {
		return lives;
	}
	
	
	/**
	 * This method sets isInvincible when item is picked up.
	 */
	public void setInvincible() {
		isInvincible = 5;
	}
	
	/**
	 * This method sets the x coordinate of the character
	 * @param x an integer from 0 to 8 that represents the x-coordinate of the character
	 */
	public void setX(int x) {
		locationX = x;
	}
	
	/**
	 * This method sets the y coordinate of the character
	 * @param y an integer from 0 to 8 that represents the y value of the character
	 */
	public void setY(int y) {
		locationY = y;
	}

	/**
	 * This method changes when the player picks up the briefcase
	 */
	public void setBriefcase() {
		briefcase = true;
	}
	
	/**
	 * This method returns the state of the character
	 * @return isAlive represents whether the character is still on the board.
	 */
	public boolean getAlive() {
		return isAlive;
	}
	
	/**
	 * This method determines if the player has the briefcase or not
	 * @return briefcase true if player has briefcase, false otherwise.
	 */
	public boolean hasBriefcase() {
		return briefcase;
	}
	
	/**
	 * This method checks if the character is a ninja or player
	 * @return isNinja true if character is ninja, false if the character
	 * is player.
	 */
	public boolean checkNinja() {
		return isNinja;
	}
	
	/**
	 * This method returns how much ammo player has remaining
	 * @return bullet an integer from 0 to 1 
	 */
	public int getAmmo() {
		return bullet;
	}
	
	/**
	 * This method represents when the player picks up additional ammo
	 * will only increment by 1 if the player does not have a bullet
	 */
	public void incAmmo() {
		if(bullet == 0) //add ammo if no ammo left
			bullet++;
		//does nothing is already one ammo
	}
	
	/**
	 * This method decrements by 1 when the player shoots
	 */
	public void fire() {
		bullet--;
	}
	
	/**
	 * This method sets the character status to dead
	 */
	public void die() {
		isAlive = false;
	}
}
