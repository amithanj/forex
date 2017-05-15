package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class ConfigReader {

	public static Properties readForex(final String path){
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(path)));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return properties;
		
	}
	
	public static List<String> readCurrencies(final String path){
		java.util.List<String> invertableCurrencies = null;
		try {
			invertableCurrencies = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return invertableCurrencies;
	}
}
