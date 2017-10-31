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
	
	public void decAmmo() {
		ammo--;
	}
	public int getAmmo(){
		return ammo;
	}
	
	
	//health
	public void dead(){
		isAlive = false;
	}
	public boolean getLife(){
		return isAlive;
	}
	public void damaged() {
		if(invincibleCount != 0)
			invincibleCount--;
		this.dead();
	}
	
	
	
	public void item(String item) {
		switch(item) {
		case "ammo":
			if(ammo == 1)
				ammo = 1;
			ammo++;
			break;
		case "invincible":
			invincibleCount = 5;
			break;
		case "radar":
			
			break;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	//lookahead
}