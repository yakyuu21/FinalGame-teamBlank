
package edu.cpp.cs.cs141.final_prog_assignment;
import java.util.Random;

public class Board {
	final int horizontal = 9;
	final int vertical = 9;
	String[][] board = new String [horizontal][vertical];
	Random random = new Random();

	private int depth;

	int[] briefcaseX = {1,4,7};
	int[] briefcaseY = {1,4,7};
	
	Board() {
		createBoard();
	}

	public void createBoard() {

		for(int x=0;x<horizontal;x++) {
			for(int y=0;y<vertical;y++) {
				if((y==1 || y==4 || y==7) && (x==1 || x==4 || x==7)) {
					board[x][y] = "?";
				}
				else 
					board[x][y] = "O";
			}
		}
		spawnBriefcase();
	}

	public void overWrite(int x, int y) {
		createBoard();
		board[x][y] = "P";
	}

	public void displayBoard() {
		for(int x=0;x<horizontal;x++) {
			System.out.println("");
			for(int y=0;y<vertical;y++) {
				System.out.print(board[x][y] + " ");
			}
		}

		System.out.println("\n");		
	}	
	
	public void spawnBriefcase()
	{
		board[briefcaseX[random.nextInt(2)]][briefcaseY[random.nextInt(2)]] = "B";
	}
	
	public String at(int x, int y) {
		return board[x][y];
	}

	public void overWrite(int x, int y, String s) {
		board[x][y] = s;
		return;
	}
	
	//This is the "look" option, returning a string to the UserInterface
	//This method needs exception handling for outofbounds array access
	//Also are we suppose to print the board with revealed spaces?
	public String reveal(String direction, int x, int y){
		switch(direction) {
		case "w":
			if(board[x][y-1] == "N" || board[x][y-2] == "N")
				return "Ninja ahead!";
			else
				return "Clear!";
		case "a":
			if(board[x-1][y] == "N" || board[x-2][y] == "N")
				return "Ninja ahead!";
			else
				return "Clear!";
		case "s":
			if(board[x][y+1] == "N" || board[x][y+2] == "N")
				return "Ninja ahead!";
			else
				return "Clear!";
		case "d":
			if(board[x+1][y] == "N" || board[x+2][y] == "N")
				return "Ninja ahead!";
			else
				return "Clear!";
		default:
			System.out.println("Error - Board.reveal()");
		}
}
