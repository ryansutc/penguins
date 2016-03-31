package penguins;

//animal class has no methods, just properties
public class Animal {
	private String species;
	private char sex;
	private float weight;
	protected Gps gps;
	
	public Animal(String species, char sex, float weight, String coords)
	{
		this.gps = new Gps();
		this.species = species;
		this.sex = sex;
		this.weight = weight;
		System.out.println(coords);
		this.gps.addCoords(coords);
	}
	
	//overloaded: Animal no coords
	public Animal(String species, char sex, float weight){
		this.gps = new Gps();
		this.species = species;
		this.sex = sex;
		this.weight = weight;
		
	}
	//overloaded: Animal with a full gps
	public Animal(String species, char sex, float weight, Gps gps){
		this.species = species;
		this.sex = sex;
		this.weight = weight;
		this.gps = new Gps();
		for (String a : gps.coordList){
			this.gps.addCoords(a);
		}
		
	}
	
	public String makeString(){
		String s = this.getSpecies().toString() + "   " + this.getSex() + "   " + 
				this.getWeight();
		System.out.println(s);
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
	
}
