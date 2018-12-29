//Daniel Gallant
//260861232
import java.util.*;
import java.text.*;

//this class is the blueprint for a spell
public class Spell {
	//attributes of the spell
	private String name;
	private double minDamage;
	private double maxDamage;
	private double chanceOfSuccess=Math.random();
	
	//constructor for an object of type Spell
	//n = spell name, minD = minimum damage, maxD = maximum damage, cOS = chance of success
	public Spell(String n, double minD, double maxD, double cOS) {
		name = n;
		minDamage=minD;
		maxDamage=maxD;
		chanceOfSuccess=cOS;
		//if minimum damage is less than zero or greater than the maximum damage,
		//or if the chance of success is less than zero or greater than one
		//then an exception is thrown
		if(minD<0||minD>maxD||cOS<0||cOS>1) {
			throw new IllegalArgumentException();
		}
	}
	
	//gets the name of the spell
	public String getName() {
		return this.name;
	}
	
	//gets the value of the spells magic damage
	public double getMagicDamage(int seed) {
		double damage=0.0;
		Random r=new Random(seed);
		double rD= r.nextDouble();
		if(rD>this.chanceOfSuccess) {
			damage = 0.0;
			return damage;
		}else {
			damage = (r.nextDouble())*(this.maxDamage-this.minDamage)+this.minDamage;
			return damage;
		}
		
	}
	
	//method that prints a message displaying the name of the spell,its damage range,
	//and it's chance of success in the form of a percentage with 1 decimal place.
	public String toString() {
		//a new object DecimalFormat imported from java.text.* allows the value
		//of chance of success to only have 1 decimal place
		DecimalFormat df1 = new DecimalFormat(".#");
		String str = "Name: "+getName()+" Damage: " +this.minDamage+"-"+this.maxDamage+" Chance: "+df1.format(this.chanceOfSuccess*100)+"%";
		return str;
	}
}
