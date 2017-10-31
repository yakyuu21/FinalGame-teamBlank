package edu.coo.cs.cs141.final_prog_assignment;

public class PowerUp {
	
	private enum powerType{ RADAR, INVINCIBLE, AMMO};

	private powerType type;
	private int X;
	private int Y;
	
	PowerUp(String S, int x, int y){
		
		switch(S)
		{
		case "radar":
			type = powerType.RADAR;
			this.X = x;
			this.Y = y;
			break;
			
		case "invincible":
			type = powerType.INVINCIBLE;
			this.X = x;
			this.Y = y;
			break;
			
		case "ammo":
			type = powerType.AMMO;
			this.X = x;
			this.Y = y;
			break;
		default:
			System.out.print("Error: PowerUp - switch");
		}
	}
	
}
