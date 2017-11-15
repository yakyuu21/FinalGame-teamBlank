package edu.cpp.cs.cs141.final_prog_assignment;

import java.util.Scanner;
import java.util.InputMismatchException;

public class UserInterface {
	private enum status{WON, LOST, CONTINUE};
	private GameEngine game;
	private Scanner scan = null;
	private status gameStatus = status.CONTINUE;
	
	public UserInterface(GameEngine game) {
		this.game = game;
		scan = new Scanner(System.in);	
	}
	
	
	public void printTitle() {
		System.out.println("\tSTEAL THE BRIEFCASE\n"
				+ "=================================");
	}
	
	
	public int mainMenu() {
		int userInput = 0;
		boolean valid = false;
		
		System.out.println(	"MAIN MENU: \n"
				+"-----------------\n"
				+ "\t1) How to Play\n" + 
					"\t2) Start New Game\n" + 
					"\t3) Load Game\n" + 
					"\t4) Exit Game\n");
		while(!valid) {
			try {
				userInput = scan.nextInt();
				if(userInput >0 && userInput < 5) 
					valid = true;
				else
					System.out.println("\nInvalid Input, Must be a number 1 to 5");
			}
			catch(InputMismatchException e) {
				System.out.println("\nError: Input must be an integer");
				scan.nextLine();
			}
		}
		return userInput;
	}
	
	public void startGame() {
		printTitle();
		int option = mainMenu();
		boolean quit = false;
		while(!quit){
			switch(option) {
			case 1:
				howToPlay();
				option = mainMenu();
				break;
			case 2:
				System.out.println("GAME START");
				loop();
				break;
			case 3:
				System.out.println("Not Implemented yet");
				option = mainMenu();
				break;
			case 4:
				System.out.println("Thank you for playing. Good Bye");
				quit = true;
				break;
			}
		}
		
	}
	
	/**
	 * Displays menu options.  Player provides input.  If player selects (2), then startGame() executes.
	 */
	/*
	public void openMenu() {
		boolean redo = true;
		while(redo) {
			mainMenu();
			String input = scan.next();
			switch(input) {
				case "1":
					howToPlay();
					break;
				case "2":
					System.out.println("you chose 2");
					startGame();
					/* playGame(); --implement gameplay in gameEngine 
					 * 			to avoid going back to main menu after display game board
					 * 
					 
					
					break;
				case "3":
					System.out.println("you chose 3");
					/* - need to implement: saveGame();
					 * 			-- saves current board situation and make available for next use.
					 * 			--(maybe create an outstream function that creates a file with certain name with board situation.)
					 * 
					 * loadGame() - retrieve board from saved data 
					 
					break;
				case "4":
					System.out.println("you chose 4\n" + "Game will close.\n" + "GOODBYE" ); ///closes/exits game
					System.exit(0);
					break;
				default: //when anything besides 1,2,3,4 is pressed in menu
					System.out.println("Invalid Input");
					break;
			}
		}
	}
*/
	/**
	 * Creates board
	 */
	public void loop() {
		game.createBoard();
		playGame();
	}
	

	public int secondaryMenu() {
		int userInput = 0;
		boolean valid = false;
		System.out.println("SELECT ACTION:\n"
				+ "----------------\n"
				+ "\t1) look"
				+ "\t2) save"
				+ "\t3) quit");
		while(!valid)
			try {
				userInput = scan.nextInt();
				if(userInput > 0 || userInput < 4) 
					valid = true;
				else
					System.out.println("Error: Invalid input...select a number from 1 to 3");
			}
			catch(InputMismatchException e) {
				System.out.println("Error: You must enter an integer");
				scan.nextLine();
			}
		return userInput;
	}
	
	
	public void playGame(){
		String direction;
		gameStatus = status.CONTINUE;
		while(gameStatus == status.CONTINUE) {
			
			int option = secondaryMenu();
			switch(option) {
			case 1:
				direction = look();
				
				if(game.look(direction))
					System.out.println("All Clear!");
				 
				else
					System.out.println("Ninja Ahead!");
				
				boolean valid = false;

				while(!valid) {
					displayBoard();
					System.out.print("Move(WASD) or shoot(F) or debug(r): ");
					direction = scan.next().toLowerCase();
					if(direction.equals("r"))
						game.debugMode();
					else if(direction.equals("f")) {
						System.out.print("Choose direction to fire: ");
						direction = scan.next();
						System.out.print(game.shoot(direction));
						valid = true;
					}
					else if(direction.equals("w")|| direction.equals("a") || direction.equals("s") || direction.equals("d")){
						valid = game.move(direction);
					}
					else {
						System.out.println("Invalid Input!");
					}
					displayBoard();
				}
				
				if(game.checkSpy()) {
					System.out.println("A Ninja destroyed you!");
				}
				
				game.ninjaMovement();
				
				game.decInvincibility();
				if(game.playerAlive() == false) {
					gameStatus = status.LOST;
					System.out.println("YOU LOST THE GAME! LOSER!");
				}
				
				else if (game.checkPlayerIsBriefcase() == true)
				{
					System.out.println("YOU FOUND THE BRIEFCASE!");
					gameStatus = status.WON;
				}
					
				break;
			case 2:
				System.out.println("NOT YET IMPLEMENTED");
				break;
				
			case 3:
				System.out.println("You Quit to Main Menu");
				startGame();
				break;
			}
		}
	
			
			
	}
	
				
			
			  /**if(player.getAlive()){
			  		decLives();
			  }
			  if(player.checkGameOver(){
			  		startGame();
			  }
			  **/
		
		
	

	private String look() {
		boolean valid = false;
		String direction = "";
		while(!valid) {
			displayBoard();
			System.out.print("Choose direction to look(WASD) or debut(R): ");
			direction = scan.next().toLowerCase();
			if(direction.equals("r"))
				game.debugMode();
			if(direction.equals("w")||direction.equals("a")||direction.equals("s")||direction.equals("d"))
				valid = true;
		}
		return direction;
	}

	private void displayBoard() {
		int length = 9;
		for(int x = 0;x < length; x++) {
			System.out.println("");
			for(int y = 0;y < length;y++) {
				System.out.print(game.displayBoard(x, y));
			}
		}
		System.out.println("\n");
	}


	
	public void howToPlay() {
		System.out.println(	"1) HOW TO PLAY:\n" + "You are a SPY that is tasked with retrieving a "
				+ "briefcase containing classified enemy documents \nlocated in one of the NINE rooms."
				+ "These rooms can only be opened from the NORTH side."
				+ "\nHowever, there are SIX ninjas patrolling the building.\n"
				+ "You have a choice of taking an action of: \n"
				+ "- Look ahead(once per turn): \n\tPress L to look TWO squares ahead\n"
				+ "- Move one square: \n\tPress W,A,S,D to face, then move up,left,down,right respectively\n"
				+ ""
				+ ""
				//add stuff about items
				+ ""
				+ ""
				+ "- Shoot in one direction(You only get one ammo at start):\n\tPress SPACE to shoot\n"
				+ "Bring back the briefcase ALIVE!\n"
				+ "GOOD LUCK\n");
	}
	
	

	
	
}

