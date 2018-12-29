//Daniel Gallant
//260861232
import java.util.*;
import java.io.*;
import java.text.*;

/*
 * NOTE: I used many private helper methods in this class to clean up the code
 * and a description of each helper method is provided to explain its purpose.
 * Multiple empty print statements are used to create new lines for organized spacing
 * and readability for the user.
 */
public class BattleGame {
	//attribute that is a object of type Random
	private static Random random = new Random();
	
	//main method to declare the playGame() method
	public static void main(String[] args) throws IOException{
		playGame("player.txt", "monster.txt", "spells.txt");
	}
	
	//play game method takes the name of three files as input, 
	//p = player.txt, monster = monster.txt, spells = spells.txt
	public static void playGame(String p, String monster, String spells) throws IOException{
		//reads spells from txt file and creates an ArrayList
		ArrayList<Spell> spell = FileIO.readSpells(spells);
		//if there are no spells the txt file then the game will display a message
		if (spell.isEmpty()) {
			System.out.println("The game will be played without spells.");
		}
		//helper method makes sure the two character files, when read, are able to create a Character
		instanceOfCharacter(p, monster);
		//creates a character with var name player
		Character player = FileIO.readCharacter(p);
		//player receives spells from the spell ArrayList
		player.setSpells(spell);
		//creates a character with var name enemy
		Character enemy = FileIO.readCharacter(monster);
		//helper method that displays the name, attack value, current health, and #wins for each character
		getInfo(player, enemy);
		System.out.println();
		System.out.println("Available spells: ");
		if (spell.isEmpty()) {
			System.out.println("None");
		}
		//displays the spells available for the player to use
		player.displaySpells();
		System.out.println();
		//creates Scanner object for keyboard input
		Scanner read = new Scanner(System.in);
		//this loop will enable the user to input again if they do not input "attack" or "quit" until they do.
		for(int i=0; i==0;) {
			System.out.println("Enter a command: ");
			String userInput = read.nextLine();
			System.out.println();
			//conditions depending on the user input
				if (userInput.equalsIgnoreCase("attack")) {
					//while loop checks current health of characters and executes different
					//functions depending on the evaluation of the conditions
					while((player.getCurrHealth()>=0)||(enemy.getCurrHealth()>=0)) {
						//helper methods used
						playerAttacking(player, enemy);
						if(enemy.getCurrHealth()<=0) {
							endGameMessage(player, enemy);
							return;
						}
						enemyAttacking(player,enemy);
						if(player.getCurrHealth()<=0) {
							endGameMessage(player, enemy);
							return;
						}
					}
					return;
				}else if (userInput.equalsIgnoreCase("quit")) {
					System.out.println("Goodbye!");
					return;
				}else if(spell.isEmpty()) {
					System.out.println("Input not recognized. Try: \"attack\" or \"quit\"");
					//this will then proceed to ask for another input due to the initial for loop
				}
				//uses helper method
				else if(userInputEquals(userInput, spell)==true) {
					//random integer = n is generated and becomes seed for the castSpell
					int n = random.nextInt();
					double damage = player.castSpell(userInput, n);
					if(damage<=0) {
						System.out.println(player.getName()+" tried to cast a spell but failed!");
						System.out.println();
						enemyAttacking(player,enemy);
						if(player.getCurrHealth()<=0) {
							endGameMessage(player, enemy);
							return;
						}
					}else {
						playerCasting(player, enemy, userInput, damage);
						if(enemy.getCurrHealth()<=0) {
							endGameMessage(player, enemy);
							return;
						}else {
							enemyAttacking(player,enemy);
							if(player.getCurrHealth()<=0) {
								endGameMessage(player, enemy);
								return;
							}
						}
					}
				}
				else {
					//we assume that if the input is not attack or quit then the user is trying to 
					//cast a spell.
					System.out.println(player.getName()+ " tried to cast "+ userInput+", but the player doesn't know that spell.");
					//this will then proceed to ask for another input due to the initial for loop
				}
		}
	}
	
	//These private helper methods make the playGame method cleaner.
	//NOTE: objects of type DecimalFormat (imported from java.text.*;) are used to
	//make the double values have only 2 decimal places.
	
	//checks if the character .txt files are valid for creating a Character
	private static void instanceOfCharacter(String p, String monster) {
		if(!(FileIO.readCharacter(p) instanceof Character) || !(FileIO.readCharacter(monster) instanceof Character)) {
			System.out.println("The game cannot be played");
			return;
		}
	}
	
	//displays the info of each character
	private static void getInfo(Character player, Character enemy) {
		System.out.println("Name: " +player.getName());
		System.out.println("Current Health: "+player.getCurrHealth());
		System.out.println("Attack Value: "+player.getAttackValue());
		System.out.println("Number of Wins: "+player.getNumWins());
		System.out.println();
		System.out.println("Name: " +enemy.getName());
		System.out.println("Current Health: "+enemy.getCurrHealth());
		System.out.println("Attack Value: "+enemy.getAttackValue());
		System.out.println("Number of Wins: "+enemy.getNumWins());
	}
	
	//method that allows the player to attack the enemy
	private static void playerAttacking(Character player, Character enemy) {
		int playerRandomSeed = random.nextInt();
		double playerAD = player.getAttackDamage(playerRandomSeed);
		DecimalFormat pdf2 = new DecimalFormat(".##");
		System.out.println(player.getName()+" attacks for "+ pdf2.format(playerAD) + " damage!");
		enemy.takeDamage(playerAD);
		if(enemy.getCurrHealth()>0) {
			System.out.println(enemy.toString());
			System.out.println();
		}
	}
	
	//method that allows the enemy to attack the player
	private static void enemyAttacking(Character player, Character enemy) {
		int enemyRandomSeed = random.nextInt();
		double enemyAD = enemy.getAttackDamage(enemyRandomSeed);
		DecimalFormat edf2 = new DecimalFormat(".##");
		System.out.println(enemy.getName()+" attacks for "+ edf2.format(enemyAD) + " damage!");
		player.takeDamage(enemyAD);
		if(player.getCurrHealth()>0) {
			System.out.println(player.toString());
			System.out.println();
		}
	}
	
	//method that determines what happens when one character is defeated
	//includes updating the .txt files of the characters
	private static void endGameMessage(Character player, Character enemy) throws IOException{
		if(enemy.getCurrHealth()<=0) {
			System.out.println(enemy.getName() +" was knocked out!");
			System.out.println();
			System.out.println("Congratulations! You defeated the monster");
			player.increaseWins();
			
			System.out.println();
			System.out.println(player.getName()+" has won: "+player.getNumWins()+" times");
			FileIO.writeCharacter(player, "player.txt");
			System.out.println();
			System.out.println("Successfully wrote to file: player.txt");
			return;
		}else if(player.getCurrHealth()<=0) {
			System.out.println(player.getName() +" was knocked out!");
			System.out.println();
			System.out.println("Game over. You were defeated by the monster!");
			enemy.increaseWins();
			
			System.out.println();
			System.out.println(enemy.getName()+" has won: "+enemy.getNumWins()+" times");
			FileIO.writeCharacter(enemy, "monster.txt");
			System.out.println();
			System.out.println("Successfully wrote to file: monster.txt");
			return;
		}
	}
	
	//method that returns true if the user input is equal to the name of a spell
	//and false otherwise
	private static boolean userInputEquals(String input, ArrayList<Spell> spell) {
		if((input.equalsIgnoreCase(spell.get(0).getName()))
				||(input.equalsIgnoreCase(spell.get(1).getName()))
				||(input.equalsIgnoreCase(spell.get(2).getName()))
				||(input.equalsIgnoreCase(spell.get(3).getName()))) {
			return true;
		}else {
			return false;
		}
	}
	
	//method that allows the player to cast a spell and do magic damage to the monster
	private static void playerCasting(Character player, Character enemy, String s, double damage) {
		DecimalFormat df2 = new DecimalFormat(".##");
		System.out.println(player.getName()+" casted "+s+" and dealt "+df2.format(damage)+" damage!");
		enemy.takeDamage(damage);
		System.out.println(enemy.toString());
		System.out.println();
	}
}

