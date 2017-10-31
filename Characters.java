package edu.coo.cs.cs141.final_prog_assignment;

public class Characters {
	private int ammo;
	private boolean isAlive;
	private int invincibleCount;
	public int locationX, locationY;

	Characters(int x, int y){
		ammo = 1;
		isAlive = true;
		invincibleCount = 0;
		locationX = x;//spawns on left bottom most block
		locationY = y; 
	}

	public void move(String direction) {
		switch(direction.toLowerCase()) {
			case"w":
				if(locationY > 0)
					locationY--;
				break;
			case"a":
				if(locationX > 0)
					locationX--;
				break;
			case"s":
				if(locationY < 8)
					locationY++;
				break;
			case"d":
				if(locationY < 8)
					locationX++;
				break;
			default:
				break;
		}
	}
	
	//location
	public int getX() {
		return locationX;
	}
	public int getY() {
		return locationY;
	}
	//gun
	public void shoot() {
		ammo--;
		this.decLife();
	}
	public int getAmmo(){
		return ammo;
	}
	//health
	public void decLife(){
		hp--;
	}
	public int getLife(){
		return hp;
	}
<<<<<<< HEAD:characters.java
	public int getAmmo(){
		return ammo;
	}
	
	
	//lookahead
	
	
=======



>>>>>>> origin/master:Characters.java
}
