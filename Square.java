package edu.cpp.cs.cs141.final_prog_assignment;

public class Square{
	
	private boolean isNinja;
	private boolean isPlayer;
	private boolean isRoom;
	private boolean isItem;
	private boolean isEmpty;
	
	private boolean isBrief;
	private boolean hide;
	private Character player;
	private Character ninja;
	private Item itemPresent;
	
	private String display;
	
	public Square() { //default constructor
		isEmpty = true;
		isPlayer = false;
		isNinja = false;
		isRoom = false;
		isItem = false;
		display = " * ";
	}
	public void setCharacter(Character charac) { //set character in location
		player = charac;
		isPlayer = true;
		display = " P ";
		hide = false;
	}
	
	
	public void playerMoved() {
		player = null;
		isPlayer = false;
		display = " * ";
	}
	
	
	public void setItem(Item item) {
		itemPresent = item;
		hide = true;
		display = " " + itemPresent.getType() + " ";
		isItem = true;
	}
	public void setNinja(Character charac) {
		ninja = charac;
		isNinja = true;
		hide = true;
		display = " N ";
	}
	public void setRoom() {
		display = "[_]";
		hide = false;
		isRoom = true;
	}
	public void briefExist() {
		display = "[_]";
		hide = false;
		isRoom = true;
		isBrief = true;
	}
	
	
	public boolean getNinja() {
		if(isNinja) //ninja exists
			return true; 
		else
			return false;
	}
	public boolean getItem() {
		if(isItem)
			return true;
		else
			return false;
	}
	public boolean getEmpty() {
		return isEmpty;
	}
	public boolean getRoom() {
		return isRoom;
	}

	
	public Character getPlayerObj() {
		return player;
	}
	public Character getNinjaObj() {
		return ninja;
	}


	public boolean getBrief() {
		return isBrief;
	}
	
	
	
	
	
	public String display() {
		/*
		 * if(isPlayer)
			return " P ";
		else if(isRoom)
			return "[_]";
		else // isEmpty
			return " * ";
			*/
		if(!hide) //if something visible
			return display;
		else 
			return " * ";
	}
	
	public String reveal() {
		return display;
	}


}
