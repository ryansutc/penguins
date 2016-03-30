package penguins;

//animal class has no methods, just properties
public class Animal {
	private String species;
	private char sex;
	private float weight;
	private double gps[][];
	
	public Animal(String species, char sex, float weight, double gps[][])
	{
		this.species = species;
		this.sex = sex;
		this.weight = weight;
		this.gps = gps;
	}
	
	//overloaded: Animal no coords
	public Animal(String species, char sex, float weight){
		
	}
	
	public String makeString(){
		String s = this.getSpecies() + "\t" + this.getSex() + "\t" + 
				this.getWeight() + "\t" + this.getGps()[0][0] + "\t" + 
				this.getGps()[0][1];
		return s;
	}
	
	//getters and setters
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public double[][] getGps() {
		return gps;
	}
	public void setGps(double gps[][]) {
		this.gps = gps;
	}
	
}
