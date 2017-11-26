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

	public void printTitle() {
		System.out.println("\tGet the Cheese\n"
				+ "===============================");
	}
	public void mainMenu() {
		printTitle();
		System.out.println(	"Main Menu: \n"+
				"------------------\n"
				+ "\t1) How to Play\n" + 
				"\t2) Start New Game\n" + 
				"\t3) Load Game\n" + 
				"\t4) Exit Game\n");
		String input = scan.next();
		switch(input) {
			case "1":
				howToPlay();
				break;
			case "2":
				showLine();
				System.out.println("You chose 2.");
				showLine();
				startGame(chooseDifficulty());
				break;
			case "3":
				showLine();
				System.out.println("You chose 3.");
				showLine();
				if(loadGame())
					playGame(getLevel());
				else 
					mainMenu();
				break;
			case "4":
				showLine();
				System.out.println("You chose 4.\n" + "Game will close.\n" + "GOODBYE" ); ///closes/exits game
				showLine();
				System.exit(0);
				break;
			default: //when anything besides 1,2,3,4 is pressed in menu
				showLine();
				System.out.println("Invalid Input");
				mainMenu();
				break;
		}

	}
	public String chooseDifficulty()
	{
		System.out.println("Select Difficulty. \n"
				+ "\t(1) lol\n"
				+ "\t(2) Yo. Dis hard. \n"
				+ "\t(3) DAFUQ?!\n");
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
			{
				showLine();
				System.out.println("All Clear!");
			}
			else {
				showLine();
				System.out.println("Ninja Ahead!");
			}

			boolean valid = false;
			while(!valid) { //repeat until user input a valid key -- take action(move or shoot)
				showLine();
				printStatus();
				displayBoard();
				System.out.print("\tMake a wise choice: \nMove: \n\t  W(UP)\n A(LEFT)  S(DOWN)  D(RIGHT) \nShoot: F \nDebug: R \n");
				direction = scan.next().toLowerCase();
				if(direction.equals("r"))
					game.debugMode();
				else if(direction.equals("f")) {
					showLine();
					System.out.print("Choose direction to fire: \n\t  W(UP)\n A(LEFT)  S(DOWN)  D(RIGHT) \n");
					direction = scan.next();
					showLine();
					System.out.print(game.shoot(direction));
					valid = true;
				}
				else if(direction.equals("w")|| direction.equals("a") || direction.equals("s") || direction.equals("d")){
					valid = game.move(direction);
				}
				else {
					showLine();
					System.out.println("Invalid Input!");
				}
			}

			if (game.checkPlayerIsBriefcase() == true){
				displayBoard();
				showLine();
				System.out.println("YOU FOUND THE BRIEFCASE!");
				showLine();
				System.out.println("\n\n\n\n");
				gameStatus = status.WON;
				mainMenu(); //added

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
			//add check item method here
			if(game.checkSpy()) {  //ninja check method
				showLine();
				System.out.println("A Ninja destroyed you!");
			}

			//***********************************************
			game.ninjaDecision();
			//***********************************************
			game.decInvincibility();

			if(game.playerAlive() == false) {
				gameStatus = status.LOST;
				showLine();
				System.out.println("YOU LOST THE GAME! LOSER!\n");
				mainMenu();
			}
		}
	}




	private String look() {
		boolean valid = false;
		String direction = "";
		while(!valid) {
			showLine();
			printStatus();
			displayBoard();
			System.out.print("\nChoose direction to look:　\n\t  W(UP)\n A(LEFT)  S(DOWN)  D(RIGHT) \nDebug: R \nType \"save\" to save, \"quit\" to exit game.\n");
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

	public void saveGame() {
		String userInput;
		showLine();
		System.out.println("Save: Name your save file:");
		userInput = scan.next();
		scan.nextLine();
		game.save(userInput);
	}

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
				showLine();
				System.out.println("Invalid Input!");
				valid = false;
			}
		}while(!valid);
	}

	public String getLevel(){
		return level;
	}

	public void showLine() {
		System.out.println("\n----------------------------------\n");
	}


}

