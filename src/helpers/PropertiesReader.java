package helpers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	InputStream input;
	Properties prop;
	String fileName = "config.properties";
	
	public PropertiesReader() {
		try {
			prop = new Properties();
			input = getClass().getClassLoader().getResourceAsStream(fileName);
			if(input != null) {
				prop.load(input);
			} else {
				throw new FileNotFoundException("File not found");
			}
		} catch(Exception e) {
			System.out.println("Exception: "+e);
		}
	}
	
	public String getProperty(String name) {
		return prop.getProperty(name);
	}
	
	public void endRead() {
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
