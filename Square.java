package edu.cpp.cs.cs141.final_prog_assignment;
import java.io.Serializable;
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

	private String display;
	private String displayHidden = " * ";

	public Square() { //default constructor
		isEmpty = true;
		isPlayer = false;
		isNinja = false;
		isRoom = false;
		isItem = false;
		reveal = false;
		hide = true;
		display = "   ";
	}
	
	public void setEmpty(){
		isEmpty = true;
		isPlayer = false;
		isRoom = false;
		isItem = false;
		player = null;
		hide = true;
		display = "   ";
	}
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
	public void setItemnull() {
		if(itemPresent!=null) 
			itemPresent = null;
		isItem = false;
	}

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


	public void setItem(Item item) {
		itemPresent = item;
		hide = true;
		display = itemPresent.getType();
		isItem = true;
	}
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
	public void setRoom() {
		display = "[_]";
		hide = false;
		isRoom = true;
		isBrief = false;
	}
	public void toggleHide() {
		hide = !hide;
	}
	public void briefExist(boolean isDebugOn) {
		if (!isDebugOn) {//if debug mode is not on
			display = "[_]";
			hide = false;
			isRoom = true;
			isBrief = true;
		}
		else { //if debug mode is on
			display = "[#]";
			hide = true;
			isRoom = true;
			isBrief = true;
		}

	}


	public boolean getNinja() {
		return isNinja;
			}
	public boolean getPlayer() {
		return isPlayer;
	}
	public boolean getItem() {
		return isItem;
	}
	
	public boolean getEmpty() {
		return isEmpty;
	}
	public boolean getRoom() {
		return isRoom;
	}

	public String getItemtype() {
		return itemPresent.name();
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
		if(!hide) //if something visible
			return display;
		else if(reveal) {
			reveal();
			return display;
		}
		else 
			return displayHidden;
	}

	public void revealBrief() {
		display = "[#]";
	}
	public void reveal() {
		reveal = !reveal;
	}
	
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
	public void makeVisible() {
		hide = false; 
	}
	
	public void killNinja() {
		ninja.die();
		removeNinja();
	}
	public String getDisplay() {
		return display;
	}
	
}
