package edu.cpp.cs.cs141.final_prog_assignment;
import java.io.Serializable;

public class Character implements Serializable{
	private int lives;
	private int bullet;
	private boolean briefcase;
	private boolean isNinja;
	private int locationX, locationY;
	private boolean isAlive;
	private int isInvincible = 0;
	
	public Character(int x, int y) {
		lives = 1;
		bullet = 0;
		briefcase = false;
		isNinja = true;
		isAlive = true;
		locationX = x;
		locationY = y;
	}
	public Character(boolean brief,int x, int y) {
		lives = 3;
		bullet = 1;
		briefcase = brief;
		isNinja = false;
		isAlive = true;
		locationX = x;
		locationY = y;
	}
	
	public void decLives() {
		if(lives > 0)
			lives--;
		if(lives == 0)
			isAlive = false;
	}
	
	public boolean isInvincible() {
		if(isInvincible == 0)
			return false;
		else
			return true;
	}
	public int getInvCount() {
		return isInvincible;
	}
	
	public void decInvincibility() {
		if(isInvincible > 0)
			isInvincible--;
	}

	public int getX() {
		return locationX;
	}
	public int getY() {
		return locationY;
	}
	public int getLives() {
		return lives;
	}
	
	
	
	public void setInvincible() {
		isInvincible = 5;
	}
	public void setX(int x) {
		locationX = x;
	}
	public void setY(int y) {
		locationY = y;
	}

	public void setBriefcase() {
		briefcase = true;
	}
	public boolean getAlive() {
		return isAlive;
	}
	
	public boolean hasBriefcase() {
		return briefcase;
	}
	
	public boolean checkNinja() {
		return isNinja;
	}
	public int getAmmo() {
		return bullet;
	}
	public void incAmmo() {
		if(bullet == 0) //add ammo if no ammo left
			bullet++;
		//does nothing is already one ammo
	}
	
	public void fire() {
		bullet--;
	}
	public void die() {
		isAlive = false;
	}
}
