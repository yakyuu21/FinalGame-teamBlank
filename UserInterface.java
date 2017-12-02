/**
 * CS 141: Intro to Programming and Problem Solving
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #N
 *
 * <description-of-assignment>
 * This is a team project in which we implement a 2D game containing
 * various objects: rooms, power ups, player, and ninjas.
 * The goal of this game is to have the user interact with different 
 * entities in the program and to complete the objective of finding
 * the briefcase.
 * 
 * Team Blank
 * 	
 * Justen Minamitani
 * Saroj Poudel
 * Steve Marrero
 * Aaron Lim
 * Koshi Huynh
 * Jon Camarillo
 */
package edu.cpp.cs.cs141.final_prog_assignment;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class handles all the interactions between the game and the user.
 */
public class UserInterface {

	private enum status{WON, LOST, CONTINUE};
	private GameEngine game;
	private Scanner scan = null;
	/**
	 * The game status is set as CONTINUE until player wins or loses.
	 */
	private status gameStatus = status.CONTINUE;
	
	/**
	 * This field represents difficulty of the game
	 */
	private int level;

	/**
	 * Default constructor that sets the game in motion.
	 * @param game GameEngine object
	 */
	public UserInterface(GameEngine game) {
		this.game = game;
		scan = new Scanner(System.in);	
	}

	public void printTitle() {
		System.out.println("**********************\n"
				+ "*   Get the Cheese   *\n"
				+ "**********************");
	}

	/**
	 * This method will display the main menu at the start of the game or when the
	 * player quits the game.  Game will end when the player exits from this menu.
	 * @return userInput an integer from 1 to 4
	 */
	public int mainMenu() {
		int userInput = 0;
		boolean valid = false;
		System.out.println(	"Main Menu: \n"
				+ "------------------\n"
				+ "\t[1] How to Play\n"  
				+ "\t[2] Start New Game\n"  
				+ "\t[3] Load Game\n" 
				+ "\t[4] Exit Game\n");
		while(!valid) {
			try {
				userInput = scan.nextInt();
				
				if(userInput > 0 && userInput < 5) {
					valid = true;
				}
				else
					System.out.println("Error: Enter a number from 1 to 4"
							+ "\nTry again: ");
			}
			catch(InputMismatchException e) {
				System.out.println("Error: You must enter an integer "
						+ "\nTry again: ");
				scan.nextLine();
			}
		}
		return userInput;
	}
	
	/**
	 * This method represents an extentions of the main menu.  It will
	 * direct the user depending on what they chose in main menu.
	 */
	public void startGame() {
		boolean valid;
		do {
			valid = false;
			int option = mainMenu();
			switch(option) {
			case 1:
				howToPlay();
				break;
			case 2:
				showLine();
				System.out.println("New Game");
				showLine();
				chooseDifficulty();
				loop(level);
				break;
			case 3:
				showLine();
				if(loadGame())
					playGame(getLevel());
				else 
					startGame();
				break;
			case 4:
				showLine();
				System.out.println("Game will close.\n" + "GOODBYE" ); ///closes/exits game
				showLine();
				System.exit(0);
				valid = true;
				break;
			}
		}while(!valid);
	}

	/**
	 * This method will create the current difficulty of the game.
	 * @return difficulty level
	 */
	public void chooseDifficulty() {
		System.out.println("Select Difficulty. \n"
				+ "\t(1) lol\n"
				+ "\t(2) Yo. Dis hard. \n"
				+ "\t(3) DAFUQ?!\n");
		boolean isCorrectInput = false;
		while(!isCorrectInput) {
			try {
				level = scan.nextInt();
				switch(level) {
				case 1:
					game.setDifficulty(1);
					isCorrectInput = true;
					break;
				case 2:
					game.setDifficulty(2);
					isCorrectInput = true;
					break;
				case 3:
					game.setDifficulty(3);
					isCorrectInput = true;
					break;
				default:
					System.out.println("Error: Must enter a number from 1 to 3."
							+ "\nTry again: ");
				}
			}catch(InputMismatchException e) {

				System.out.println("Error: Must input an integer"
						+ "\nTry again:");
				scan.nextLine();
			}
		}
	}

	/**
	 * This method represents the game loop.  It will start by creating
	 * the board and directing the user to the game.
	 */
	public void loop(int level) {
		game.createBoard();
		playGame(level);
	}

	/**
	 * This method will check to see if the spy and the ninja are adjacent to
	 * each other and display and kill the player if they are.
	 */
	public void checkSpy() {
		if(game.checkSpy()) {  //ninja check method
			showLine();
			System.out.println("A Ninja destroyed you!");
		}
	}

	/**
	 * This method is an extension of the look funciion.  The user input in
	 * the look function will direct the game to either look in a direction,
	 * activate debug, save, or load a game.
	 */
	public void lookCommand() {
		boolean valid = false;
		while(!valid) {
			String direction = look();
			switch(direction) {
			case "w":
			case "a":
			case "s":
			case "d":
				if(game.look(direction)) {
					showLine();
					System.out.println("All Clear!");
				}
				else {
					showLine();
					System.out.println("Ninja Ahead!");
				}
				valid = true;
				break;
			case "r":
				game.debugMode();
				showLine();
				displayBoard();
				break;
			case "1":
				saveGame();
				displayBoard();
				break;
			case "2":
				showLine();
				if(loadGame())
					playGame(getLevel());
				else
					System.out.println("Error: Unable to load");
				break;
			case "3":
				showLine();
				quitGame();
				break;
			}
		}
		displayBoard();
	}
	
	/**
	 * This method is called upon when the player wants to shoot their gun.
	 * The player will shoot at a direction the user chooses. 
	 */
	public void shootCommand() {
		String userInput = "";
		System.out.println("Choose direction to shoot");
		commandMenu();
		boolean valid = false;
		while(!valid) {
			userInput = scan.next().toLowerCase();
			switch(userInput) {
			case "w":
			case "a":
			case "s":
			case "d":
				showLine();
				System.out.println(game.shoot(userInput));
				showLine();
				valid = true;
				break;
			default:
				System.out.println("Error: Invalid input. Try Again.");
				break;
			}
		}
	}
	
	/**
	 * This method directs the action of the player after the look phase.
	 * The user input commands the player to move 1 space, shoot, or activate
	 * debug mode.
	 */
	public void moveCommand() {
		String direction = "";
		boolean valid = false;
		showLine();
		System.out.print("Make a wise choice: \nMove: ");
		commandMenu();
		System.out.println("\nF(Shoot) \nR(Debug)");
		while(!valid) {
			direction = scan.next().toLowerCase();
			switch(direction) {
				case "w":
				case "a":
				case "s":
				case "d":
					valid = game.move(direction);
					break;
				case "r":
					game.debugMode();
					displayBoard();
					break;
				case "f":
					shootCommand();
					valid = true;
					break;
			}
		}
	}
	
	/**
	 * This method will allow player to play the game on the desired difficulty. It will 
	 * get its action from {@link GameEngine} and display messages with each command.
	 * This method will control the flow of the game.
	 * @param lvl String input from user that indicates the difficulty of the game.
	 */
	public void playGame(int lvl) {
		String direction;
		gameStatus = status.CONTINUE;
		
		while(gameStatus == status.CONTINUE) {	
			displayBoard();
			printStatus();
			lookCommand();
			moveCommand();
			
			if (game.checkPlayerIsBriefcase() == true){
				displayBoard();
				showLine();
				System.out.println("YOU FOUND THE BRIEFCASE!"
						+"\nCONGRATS YOU WIN!");
				showLine();
				System.out.println("\n\n\n\n");
				gameStatus = status.WON;
				startGame(); //added
			}

			if(game.checkItem()) {
				int itemPickUp = game.applyItem();
				printStatus();
				displayBoard();
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
			checkSpy();
			game.ninjaDecision();
			game.decInvincibility();

			if(game.playerAlive() == false) {
				gameStatus = status.LOST;
				showLine();
				System.out.println("YOU LOST THE GAME! LOSER!\n");
				startGame();
			}
		}
	}

	/**
	 * This method will only display the directions.  No input is necessary.
	 */
	public void commandMenu() {
		System.out.println("\t W(UP)\n A(LEFT)  S(DOWN)  D(RIGHT)");
	}
	
	/**
	 * This method will get an input from the user for which direction the user wants to look,
	 * if they want to save, if they want to load, or if they want to reload. 
	 * @return direction a String for instructions.
	 */
	private String look() {
		boolean valid = false;
		String direction = "";
		showLine();
		System.out.println("Choose a direction to look");
		commandMenu();
		System.out.print("R(Debug)\t 1(Save)\t 2(Load) \t3(Quit)");
		while(!valid) {
			direction = scan.next().toLowerCase();
			if(direction.equals("w") || direction.equals("a") || direction.equals("s") || direction.equals("d")
					|| direction.equals("r")|| direction.equals("1")|| direction.equals("2") || direction.equals("3"))
				valid = true;
			else
				System.out.println("Invalid Entry.  Try Again");
		}
		return direction;
	}

	/**
	 * This method displays the current status of the player and the number of ninjas left
	 */
	public void printStatus() {
		System.out.println("STATUS: \n"
				+ "-----------"
				+ "\nInvincibility: "+ game.invCount() 
				+ "\nAmmo: "+ game.getAmmoCount()
				+ "\nLives: " + game.getPlayer().getLives()
				+ "\nNinjas Left: " + game.getNumNinja() + "\n");
	}


	/**
	 * This method will display the board onto the screen
	 */
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

	/**
	 * This method displays the background information of the game, and instructions on how to play.
	 * It will describe the controls, and what each item does.
	 */
	public void howToPlay() {
		System.out.println("Background:\n"
				+ "--------------\n"
				+ "You are a SPY that infiltrated the enemy's base of operation in order to steal a briefcase\n"
				+ "containing classified information.  The building you have infiltrated has a 9x9 layout, and\n"
				+ "is pitch black.  Luckily, you brought along your nightvision goggle that allows your to see\n"
				+ "two spaces ahead of you.  The briefcase is hidden in one of nine rooms, and for added security\n"
				+ "there are six ninjas patrolling the building.  Your objective is to safely navigate the building\n"
				+ "while doing your best to avoid the ninjas.  If the ninjas is next to you, the ninja will stab you\n"
				+ "sending you back to the beginning of the game.  When a ninja kills you three times you have failed\n"
				+ "your objective.  You do have a gun with a single bullet, so use it with caution.  While navigating\n"
				+ "the building, you may find items laying on the floor: radar, invincible potion, or one more ammo.\n"
				+ "The items will grant you a power up that will help you on your quest to steal the briefcase.\n"
				+ "GOOD LUCK!\n");

		System.out.println("Items:\n"
				+ "(R) - RADAR - When picked up it will show your which room contains the briefcase\n"
				+ "(A) - AMMO - An additional bullet, can only be picked up if you have no ammo\n"
				+ "(I) - INVINCIBILITY - For 5 turns you are impervious to death\n");

		System.out.println("Controls: \n"
				+ "\t  W(UP)\n A(LEFT)  S(DOWN)  D(RIGHT) \n"
				+ "- You can only move one space per turn.  If you run into a wall or room, your turn does not expire\n"
				+ "- Rooms can only be accessed from the NORTH\n"
				+ "- Look ahead(once per turn) 2 spaces in the direction of your choice\n"
				+ "[r] - debug mode\n"
				+ "[f] - shoot, use wasd for which direction.  The bullet will travel in a straight trajectory until\n"
				+ "\tit hits a Ninja, Wall, or a Room\n"
				+ "-At any time type [save] to save your progress or type [quit] to return to the main menu\n");
	}

	/**
	 * This method will ask the user to create a new save file or to overwrite an
	 * existing one.
	 */
	public void saveGame() {
		String userInput;
		showLine();
		System.out.println("Save: Name your save file:");
		userInput = scan.next();
		scan.nextLine();
		game.save(userInput);
	}

	/**
	 * This method will ask the user to load an existing file that they have
	 * previously created
	 * @return true if the file exist, false if it doesn't
	 */
	public boolean loadGame() {
		String userInput;
		System.out.println("Load: Enter file name");
		userInput = scan.next();
		scan.nextLine();
		if(game.load(userInput))
			return true;
		else {
			System.out.println("Unable to Load\n");
			return false;
		}
	}

	/**
	 * This method will be called when the player quits the game. It will check
	 * to see if the player wanted to save their progress before quitting and
	 * send them back to the main menu.
	 */
	public void quitGame() {
		String userInput;
		boolean valid = false;
		do {		
			showLine();
			System.out.println("Would you like to save? [Y]es / [N]o");
			userInput = scan.next();
			if(userInput.toLowerCase().equals("y")) {
				saveGame();
				valid = true;
				startGame();
			}
			else if(userInput.toLowerCase().equals("n")) {
				showLine();
				System.out.println("The game will now return to main menu.\n");
				valid = true;
				startGame();
			}
			else {
				showLine();
				System.out.println("Invalid Input!");
				valid = false;
			}
		}while(!valid);
	}

	/**
	 * This method will return the current difficulty of the game
	 * @return String value of the level difficulty.
	 */
	public int getLevel(){
		return level;
	}

	public void showLine() {
		System.out.println("\n----------------------------------\n");
	}


}

