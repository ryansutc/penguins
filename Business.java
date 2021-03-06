package penguins;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Business {
	private ArrayList<Animal> animals; //dynamic arraylist of animals 
	private String outputPath;
	private String inputPath;

	public Business(){
		this.outputPath = "";
		this.inputPath = "";
		animals = new ArrayList<Animal>(); 
	}
	
	public void addAnimal(String type, char sex, float weight, Gps gps, String theme){
		//create an animal and add to a dynamic array of animals

		type = type.toLowerCase().replace(" ", "");
		System.out.println(type);
		System.out.println("coordlist size: " + gps.coordList.size());
		if (type.equals("penguin")){
			
			Animal newAnimal = new Penguin(type, sex, weight, gps, Double.parseDouble(theme));
			animals.add(newAnimal);  //upcast
		}
		else if (type.equals("walrus")){
			Animal newAnimal = new Walrus(type, sex, weight, gps, theme.toLowerCase().replace(" ", ""));
			animals.add(newAnimal);  //upcast
		}
		else if (type.equals("sealion")){
			Animal newAnimal = new SeaLion(type, sex, weight, gps, Integer.parseInt(theme));
			animals.add(newAnimal);  //upcast
		}
		
	}
	
	public String listAnimals(){
		String s = "";
		System.out.println(""+animals.size());
		for (Animal a : animals) {
			//polymorphism in action
			System.out.println(a.makeString());
			
		}
		return s;
	}
	
	public void writeFile(String filepath) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
		try{
			
			bw.write("\nid   species   sex   weight   theme   lat & long   ");
			bw.newLine();
			int x = 1;
			for (Animal a : animals) {
				if (a.gps.coordList.isEmpty()){
					bw.write("\n" + x + "   " + a.makeString() + "   ");
					bw.newLine();
				}
				else{
					for (String s: a.gps.coordList){
						bw.write("\n" + x + "   " + a.makeString() + "   " + s);
						bw.newLine();
	
					}
				}
				x += 1;
			}
		
		}
		catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
				
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		finally {
			bw.close();
		}
		
	}
	public String readFile(String filepath) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		String data = "";
		try{
			
			//br.read("species\t sex\t weight\t lat\t long\t theme");
			
			String cline; //current line in file
			cline = br.readLine();
			while(cline !=null){
				System.out.println(cline);
				data = data + "\n" + cline;
				cline = br.readLine();
				
			}

		}
		catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
				
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		finally {
			br.close();
		}
		return data;
	}
	
	//getters and setters
	public String getoutputPath() {
		return outputPath;
	}

	public void setoutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	

	public String getinputPath() {
		return inputPath;
	}

	public void setinputPath(String inputPath) {
		this.inputPath = inputPath;
	}
	public ArrayList<Animal> getAnimals(){
		return this.animals;
	}

}
