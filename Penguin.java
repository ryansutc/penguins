package penguins;

public class Penguin extends Animal {

	private double bloodpressure;	
	public Penguin(String species, char sex, float weight, String coords) {
		super(species, sex, weight, coords);
		// TODO Auto-generated constructor stub
	}
	
	public Penguin(String species, char sex, float weight, String coords, double bloodpressure) {
		super(species, sex, weight, coords);
		this.setBloodpressure(bloodpressure);
		// TODO Auto-generated constructor stub
	}
	public Penguin(String species, char sex, float weight, Gps gps, double bloodpressure) {
		super(species, sex, weight, gps);
		this.setBloodpressure(bloodpressure);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String makeString(){
		String s = super.makeString() + "   " +  String.valueOf(getBloodpressure());
		return s;
	}


	public double getBloodpressure() {
		return bloodpressure;
	}

	public void setBloodpressure(double bloodpressure) {
		this.bloodpressure = bloodpressure;
	}

}
