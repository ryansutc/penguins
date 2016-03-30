package penguins;

public class SeaLion extends Animal{
	private int spots;
	public SeaLion(String species, char sex, float weight, double[][] gps) {
		super(species, sex, weight, gps);
		// TODO Auto-generated constructor stub
	}
	
	public SeaLion(String species, char sex, float weight, double[][] gps, int spots) {
		super(species, sex, weight, gps);
		this.setSpots(spots);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String makeString(){
		String s = super.makeString() + "\t" + this.getSpots();
		return s;
	}
	
	//getters and setters
	public int getSpots() {
		return spots;
	}

	public void setSpots(int spots) {
		this.spots = spots;
	}
	

}
