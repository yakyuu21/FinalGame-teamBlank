package edu.cpp.cs.cs141.final_prog_assignment;

import java.util.Scanner;

public class UserInterface {
	private enum status{WON, LOST, CONTINUE};
	private GameEngine game;
	private Scanner scan = null;
	private status gameStatus = status.CONTINUE;
	String level;
	
	public UserInterface(GameEngine game) {
		this.game = game;
		scan = new Scanner(System.in);	
	}
	
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
					startGame(chooseDifficulty());
					break;
				case "3":
					System.out.println("you chose 3");
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
	
	public String chooseDifficulty()
	{
		System.out.println("Select Difficulty. \n"
				+ "(1) lol\n"
				+ "(2) Yo. Dis hard. \n"
				+ "(3) DAFUQ?!");
		boolean isCorrectInput = false;

		do{ 
			Scanner input = new Scanner(System.in);
			level = input.next();
			if (level.equals("1") || level.equals("2") || level.equals("3"))
			 isCorrectInput = true;
			else
				System.out.println("Nah.  Enter 1, 2, or 3.");
		} while(isCorrectInput == false);
		return level;
	}
	// Will probably need to catch exception when a String is entered

	
	/**
	 * Creates board
	 */
	public void startGame(String level) {
		game.createBoard();
		playGame(level);
	}
	
	public void playGame(String lvl){
		String direction;
		gameStatus = status.CONTINUE;

		while(gameStatus == status.CONTINUE) {
			direction = look();
			if(game.look(direction))
				System.out.println("All Clear!");
			else
				System.out.println("Ninja Ahead!");

			boolean valid = false;
			while(!valid) { //repeat until user input a valid key -- take action(move or shoot)
				showLine();
				displayBoard();
				System.out.println("Invincibility: "+ game.invCount());
				System.out.println("Ammo: "+ game.getAmmoCount());

				System.out.print("Move(WASD) or shoot(F): ");
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
			}
			if(game.checkItem()) {
				int itemPickUp = game.applyItem();

				switch(itemPickUp) {
					case 1:
						System.out.println("You have picked up a Radar!");
						break;
					case 2:
						System.out.println("You have picked up an Invincibility!");
						break;
					case 3:
						System.out.println("You have picked up an Ammo!");
						break;
					default:
						break;
				}
			}
			//add check item method here
			if(game.checkSpy())  //ninja check method
				System.out.println("A Ninja destroyed you!");

			//***********************************************
			if (lvl.equals("1"))
				game.ninjaMovement();
			else if (lvl.equals("2"))
				game.useLineOfSightMovement();
			else
				game.useRadialMovement();
			//game.ninjaMovement();

			//game.ninjaMovementRAD();
			//***********************************************

			if(game.checkSpy())  //ninja check method
				System.out.println("A Ninja destroyed you!");

			game.decInvincibility();

			if(game.playerAlive() == false) {
				gameStatus = status.LOST;
				System.out.println("YOU LOST THE GAME! LOSER!");
			}
			else if (game.checkPlayerIsBriefcase() == true){
				System.out.println("YOU FOUND THE BRIEFCASE!");
				gameStatus = status.WON;
			}

		}
	}


		
	

	private String look() {
		boolean valid = false;
		String direction = "";
		while(!valid) {
			showLine();
			displayBoard();
			System.out.println("Invincibility: "+ game.invCount());
			System.out.println("Ammo: "+ game.getAmmoCount());
			
			System.out.print("\nChoose direction to look(WASD): \nType \"save\" to save, \"quit\" to exit game.");
			direction = scan.next().toLowerCase();
			if(direction.equals("r"))
				game.debugMode();
			if(direction.toLowerCase().equals("w")||direction.toLowerCase().equals("a")||direction.toLowerCase().equals("s")||direction.toLowerCase().equals("d"))
				valid = true;
			else if(direction.toLowerCase().equals("save"))
				saveGame();
			else if(direction.toLowerCase().equals("quit"))
				quitGame();
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

	public void mainMenu() {
		System.out.println(	"1) How to Play\n" + 
							"2) Start New Game\n" + 
							"3) Load Game\n" + 
							"4) Exit Game\n");
		String input = scan.next();
		switch(input) {
			case "1":
				howToPlay();
				break;
			case "2":
				System.out.println("you chose 2");
				startGame(chooseDifficulty());
				break;
			case "3":
				System.out.println("you chose 3");
				loadGame();
				playGame(getLevel());
				
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
	
	public void saveGame() {
		String userInput;
		System.out.println("Name a save file:");
		userInput = scan.next();
		scan.nextLine();
		game.save(userInput);
	}
	
	public void loadGame() {
		String userInput;
		System.out.println("Load: enter file name");
		userInput = scan.next();
		scan.nextLine();
		game.load(userInput);
	}
	
	public void quitGame() {
		String userInput;
		boolean valid = false;
		do {		
			System.out.println("Would you like to save? [Y]es / [N]o");
			userInput = scan.next();
			if(userInput.toLowerCase().equals("y")) {
				saveGame();
				valid = true;
			}
			else if(userInput.toLowerCase().equals("n")) {
				System.out.println("The game will now return to main menu.\n");
				mainMenu();
				valid = true;
			}
			else {
				System.out.println("Invalid Input!");
				valid = false;
			}
		}while(!valid);
	}

	public String getLevel(){
		return level;
	}

	public void showLine() {
		System.out.println("----------------------------------");
	}
	
	
}

