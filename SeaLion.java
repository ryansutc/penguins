package penguins;

public class SeaLion extends Animal{
	private int spots;
	public SeaLion(String species, char sex, float weight, String coords) {
		super(species, sex, weight, coords);
		// TODO Auto-generated constructor stub
	}
	
	public SeaLion(String species, char sex, float weight, String coords, int spots) {
		super(species, sex, weight, coords);
		this.setSpots(spots);
		// TODO Auto-generated constructor stub
	}
	public SeaLion(String species, char sex, float weight, Gps gps, int spots) {
		super(species, sex, weight, gps);
		this.setSpots(spots);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String makeString(){
		String s = super.makeString() + "   " + this.getSpots();
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
