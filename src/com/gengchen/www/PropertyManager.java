package com.gengchen.www;

import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
	private  Properties pro = new Properties();
	private static PropertyManager instance = null;
	private PropertyManager(){
		try {
			pro.load(PropertyManager.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  static PropertyManager getInstance(){
		if(instance == null){
			instance = new PropertyManager();
		}
		
		return instance;
	}
	
	public String getProperty(String initialTankCount){
		return pro.getProperty(initialTankCount);
	}
}
 