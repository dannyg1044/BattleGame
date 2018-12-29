//Daniel Gallant
//260861232
import java.util.*;
import java.io.*;

//purpose of this class is to create methods that can write and read files to and from a directory
public class FileIO {
	//empty constructor for the class
	public FileIO() {
		
	}
	
	//this method reads a file with the attributes of a character and
	//creates a character according to those attributes
	public static Character readCharacter(String fileName) {
		try {
			ArrayList<String> lines = new ArrayList<String>();
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String currentLine = br.readLine();
			while(currentLine != null){
				lines.add(currentLine);
				currentLine = br.readLine();
			}
			br.close();
			fr.close();
			Character create = new Character(lines.get(0),Double.parseDouble(lines.get(1)),Double.parseDouble(lines.get(2)),Integer.parseInt(lines.get(3)));
			return create;
		}
		catch(FileNotFoundException fe){
			System.out.println("File not found!");
			return null;
		}
		catch(IOException ioe){
			System.out.println("IOException!");
			return null;
		}

	}
	
	//method that reads from a file containing info about spells and 
	//creates an ArrayList of those spells
	public static ArrayList<Spell> readSpells(String fileName){
		try {
			ArrayList<Spell> addSpells = new ArrayList<Spell>();
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String currentLine=br.readLine();
			while(currentLine!=null) {
				String[] split = currentLine.split("\t");
				Spell newSpell = new Spell(split[0], Double.parseDouble(split[1]), Double.parseDouble(split[2]),Double.parseDouble(split[3]));
				addSpells.add(newSpell);
				currentLine = br.readLine();
			}
			br.close();
			fr.close();
			return addSpells;
		}
		catch(FileNotFoundException fnfe){
			System.out.println("File not found!");
			return null;
		}
		catch(IOException ioe){
			System.out.println("IO Exception!");
			return null;
		}
	}
	
	//method that writes a file containing the info of a character in order to
	//update the character at the end of a battle
	//c = character (example: player, enemy, etc...)
	public static void writeCharacter(Character c, String fileName) throws IOException{
		FileWriter fw = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(c.getName());
		bw.newLine();
		bw.write(Double.toString(c.getAttackValue()));
		bw.newLine();
		bw.write(Double.toString(c.getMaxHealth()));
		bw.newLine();
		bw.write(Integer.toString(c.getNumWins()));
		bw.close();
		fw.close();
	}
}
