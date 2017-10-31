/*
 * 
 */

package edu.coo.cs.cs141.final_prog_assignment;
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
		displayBoard();//Don't have to show.
	}
	
}
