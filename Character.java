//Daniel Gallant
//260861232
import java.util.*;
import java.text.*;

//this class defines a character
public class Character {
	//attributes of the character
	private String name;
	private double attackValue;
	private double maxHealth;
	private double currentHealth;
	private int numberWins;
	private static ArrayList<Spell> spells= new ArrayList<Spell>();
	
	//constructor for an object of type Character
	//n = name, av = attack value, mh = max health, nw = #wins
	public Character(String n, double av, double mh, int nw) {
		name = n;
		attackValue=av;
		maxHealth=mh;
		currentHealth=mh; //current health is initially max health
		numberWins=nw;
	}
	
	//gets the name of the character
	public String getName() {
		return this.name;
	}
	
	//gets the attack value of the character
	public double getAttackValue() {
		return this.attackValue;
	}
	
	//gets the max health of the character
	public double getMaxHealth() {
		return this.maxHealth;
	}
	
	//gets the current health of the character
	public double getCurrHealth() {
		return this.currentHealth;
	}
	
	//gets the number of wins of the character
	public int getNumWins() {
		return this.numberWins;
	}
	
	//prints a string that displays the characters name and current health
	public String toString() {
		DecimalFormat df2 = new DecimalFormat(".##");
		String str = "Character's name: " + this.name + ". Current health: " + df2.format(this.currentHealth) + ".";
		return str;
	}
	
	//gets the attack damage of the character generating a random double and multiplying
	//it by a number between 0.7-1.0
	public double getAttackDamage(int seed) {
		Random randomGenerator = new Random(seed);
		double multiple = 0.7+(1.0-0.7)*randomGenerator.nextDouble();
		double aD = this.attackValue * multiple;
		return aD;
	}
	
	//method that causes the character to take damage
	//d = return value of getAttackDamage
	public double takeDamage(double d) {
		this.currentHealth = this.currentHealth-d;
		return this.currentHealth;
	}
	
	//increases the number of wins of the character
	public void increaseWins() {
		this.numberWins = this.numberWins+1;
	}
	
	//gives the character an ArrayList of spells
	public static void setSpells(ArrayList<Spell> spellsInput) {
		spells = spellsInput;
	}
	
	//displays the spells the character has
	public static void displaySpells() {
		for (int i = 0; i<spells.size(); i++) {
			System.out.println((spells.get(i).toString()));
		}
	}
	
	//method that enables the character to cast a spell
	//s = name of the spell, n = seed
	public double castSpell(String s, int n) {
		for(int i=0; i<spells.size(); i++) {
			if(s.equalsIgnoreCase(spells.get(i).getName())) {
				return spells.get(i).getMagicDamage(n);
			}
		}
		return -1.0;
		
	}
}
