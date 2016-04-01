package penguins;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
	private Business business = new Business(); //static final because there can only be one business class
	
	public boolean validateTheme(String text, String animal){
		
		//animal = Penguin, Sea Lion, Walrus
		text = text.trim().toLowerCase();
		//System.out.println("Triggered " + animal);
		Pattern p = null;
		if (animal == "Penguin"){
			//use a pattern for a floating or integer number (empty string will not be accepted)
			p = Pattern.compile("^[0-9]{1,}[.]{0,1}[0-9]{0,}$|^[0-9]{0,}[.]{0,1}[0-9]{1,}$");
		}
		else if (animal == "Sea Lion" ){
			//use a pattern for a floating or integer number
			p = Pattern.compile("^[0-9]{0,}$");
		}
		else if (animal == "Walrus") {
			//use a pattern to capture optional set of values
			
			p = Pattern.compile("^good$|^average$|^poor$");	
		}
		else {
			System.out.println("Throw error");
			return false;
		}
		Matcher m = p.matcher(text);
		return m.find();
	}

	public boolean validateLatLong(String type, String coord){
		//type is either lat or lon
		//lat is between -90/+90, lon is between -180/+180
		
		Pattern p = null;
		coord = coord.trim();
		
		//bless ye: https://regex101.com/
		if (type == "lat"){
			p = Pattern.compile("(^[-+]{0,1}[0-8]{0,1}[0-9]$|^[-+]{0,1}[0-8]{0,1}[0-9][.][0-9]{0,}$)");
		}
		else if (type == "lon"){
			p = Pattern.compile("(^[-+]?[0-1]?[0-7]?[0-9][.][0-9]{0,}$|^[-+]{0,1}[0-1]{0,1}[0-7]{0,1}[0-9]$|^[-+]{0,1}[0-9]{0,1}[0-9]$|^[-+]{0,1}[0-9]{0,1}[0-9][.][0-9]{0,}$)");
		}
		else {
			System.out.println("Throw error");
			return false;
		}
		
		Matcher m = p.matcher(coord);
		return m.find();

	}

	//Check that file is valid path to a new or existing text file to export to:
	public int validateFilePath(String filepath){
		if (!filepath.toLowerCase().endsWith(".txt")){
			return 1;
		}
		File file = new File(filepath);
		
		if (!file.getParentFile().isDirectory()) {
			System.out.println("This is not a valid directory");
			return 2;
		}
			
		if (file.exists()){
			//System.out.println("This file already exists. You sure you want to overwrite it?");
			return 3; //file already exists for write//all good for read
		}
		return 0; //all good for write/ error for read
	}
	public Business getBusiness() {
		return business;
	}

}
