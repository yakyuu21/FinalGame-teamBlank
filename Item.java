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
 * This class represents the items that are available to the player
 * Radar, Ammo, and Invincible.  They will have a string representation
 * of the type of item and will be placed randomly on the board.
 */
public class Item implements Serializable{
	private String type;
	
	/**
	 * This method will set the type of item.
	 * @param s type of item
	 */
	public Item(String s) {
		type = s;
	}
	
	/**
	 * This method will return the String representation of the item
	 * depending on the type
	 * @return " A" for ammo, " R " for radar, and " I " for invincible
	 */
	public String getType() {
		switch(type) {
		case "radar":
			return " R ";
		case "invincible":
			return " I ";
		case "ammo":
			return " A ";
			default:
				return "Error - PowerUp.display - Switch";
		}
	}
	
	/**
	 * This method will return the type of item
	 * @return item type
	 */
	public String name() {
		return type;
	}

}
