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
				if(loadGame())
					playGame(getLevel());
				else 
					mainMenu();
				break;
			case "4":
				System.out.println("you chose 4\n" + "Game will close.\n" + "GOODBYE" ); ///closes/exits game
				System.exit(0);
				break;
			default: //when anything besides 1,2,3,4 is pressed in menu
				System.out.println("Invalid Input");
				mainMenu();
				break;
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
			if (level.equals("1") || level.equals("2") || level.equals("3")) {
				switch(level){
					case"1":
						game.setDifficulty("1");
						isCorrectInput = true;
						break;
					case"2":
						game.setDifficulty("2");
						isCorrectInput = true;
						break;
					case"3":
						game.setDifficulty("3");
						 isCorrectInput = true;
						 break;
				}
			}
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
				printStatus();
				System.out.print("Move(WASD) or shoot(F) or Debug(r): \n");
				direction = scan.next().toLowerCase();
				if(direction.equals("r"))
					game.debugMode();
				else if(direction.equals("f")) {
					System.out.print("Choose direction to fire: \n");
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
				displayBoard();
				printStatus();
				showLine();
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
					case 0:
						System.out.println("Cannot pick up Ammo!");
						break;
					default:
						break;
				}
			}
			//add check item method here
			if(game.checkSpy())  //ninja check method
				System.out.println("A Ninja destroyed you!");

			//***********************************************
			game.ninjaDecision();
			//***********************************************
			game.decInvincibility();

			if(game.playerAlive() == false) {
				gameStatus = status.LOST;
				System.out.println("YOU LOST THE GAME! LOSER!");
				mainMenu();
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
			printStatus();
			System.out.print("\nChoose direction to look(WASD): \nType \"save\" to save, \"quit\" to exit game.\n");
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

	public void printStatus() {
		System.out.println("Invincibility: "+ game.invCount());
		System.out.println("Ammo: "+ game.getAmmoCount());
		System.out.println("Lives: " + game.getPlayer().getLives());
		System.out.println("Ninjas Left: " + game.getNumNinja() + "\n");
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
	
	public void saveGame() {
		String userInput;
		System.out.println("Name a save file:");
		userInput = scan.next();
		scan.nextLine();
		game.save(userInput);
	}
	
	public boolean loadGame() {
		String userInput;
		System.out.println("Load: enter file name");
		userInput = scan.next();
		scan.nextLine();
		if(game.load(userInput))
			return true;
		else {
			System.out.println("Unable to Load\n");
			return false;
		}
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
				mainMenu();
			}
			else if(userInput.toLowerCase().equals("n")) {
				System.out.println("The game will now return to main menu.\n");
				valid = true;
				mainMenu();
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
		System.out.println("\n\n\n----------------------------------\n\n\n");
	}
	
	
}

