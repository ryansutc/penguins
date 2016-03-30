package penguins;

public class Walrus extends Animal {
	private String dentalhealth;
	public Walrus(String species, char sex, float weight, double[][] gps) {
		super(species, sex, weight, gps);
		// TODO Auto-generated constructor stub
	}
	
	public Walrus(String species, char sex, float weight, double[][] gps, String dentalhealth) {
		super(species, sex, weight, gps);
		// TODO Auto-generated constructor stub
		this.setDentalhealth(dentalhealth);
	}
	
	@Override
	public String makeString(){
		String s = super.makeString() + "\t" + this.getDentalhealth();
		return s;
	}
	

	public String getDentalhealth() {
		//should be poor, good, average 
		return dentalhealth;
	}

	public void setDentalhealth(String dentalhealth) {
		//should be poor, good, average (validation done in controller class
		this.dentalhealth = dentalhealth;
	}

}
