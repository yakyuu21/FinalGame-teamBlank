package edu.cpp.cs.cs141.final_prog_assignment;
import java.util.Random;

public class Board {
	private final int horizontal = 9;
	private final int vertical = 9;
	private Square[][] room = new Square[vertical][horizontal];
	private Random random = new Random();
	private int[] briefcaseX = {1,4,7};
	private int[] briefcaseY = {1,4,7};
	
	/**
	 * Creates board with room and briefcase.
	 */
	Board() {
		createBoard();
	}

	/**
	 * Creates board with room and briefcase.
	 */
	public void createBoard() {
		int i = briefcaseX[random.nextInt(3)];
		int j = briefcaseY[random.nextInt(3)];

		for(int x=0;x<horizontal;x++) {
			for(int y=0;y<vertical;y++) {
				if((y==1 || y==4 || y==7) && (x==1 || x==4 || x==7)) {
					if(x == i && y == j) {
						room[x][y] = new Square(); //with briefcase
						room[x][y].setRoom();
						room[x][y].briefExist();
					}
					else {
						room[x][y] = new Square();//without briefcase
						room[x][y].setRoom();
					}
				}
				else
					room[x][y] = new Square();
			}
		}
	}

	/**
	 * 
	 */
	public String displayBoard(int x, int y) {
		return room[x][y].display();	
	}	
	public String show(int x, int y) {
		return room[x][y].reveal();

	}
	
	
	
	public Square at(int x, int y) {
		return room[x][y];
	}
	
	
	public void set(Character player, int x, int y) {	
		room[x][y].setCharacter(player);
		player.setX(x);
		player.setY(y);
	}
	public void move(int x, int y) {	
		room[x][y].playerMoved();
	}
	
	public void setNinja(Character player, int x, int y) {
		room[x][y].setNinja(player);
		player.setX(x);
		player.setY(y);
		
	}

	public void set(Item radar, int x, int y) {
		room[x][y].setItem(radar);
	}

	
	
	


}
