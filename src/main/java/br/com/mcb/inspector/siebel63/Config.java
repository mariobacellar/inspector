package br.com.mcb.inspector.siebel63;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Config {

	private final static Logger log = Logger.getLogger(Config.class);

	private static Properties config=null;
	public static  Properties getConfig() { return config;}
	public static  void       setConfig(Properties config){Config.config = config;}

	
	public static String getParam(String key) {
		if (config==null) init();
		String val=config.getProperty(key);
		log.info("-> getParam(String key=["+key+"]/val=["+val+"])");
		return val;
	}
	
	public static void init() {
		log.info("-> init()");
		if (config!=null) 
			return;
		else 
			config= new Properties();
		
		InputStream input = null;
		try {
			String filename = "inspector.siebel63.properties";
			input  =  Inspector.class.getClassLoader().getResourceAsStream(filename);
			if(input==null){
				log.error("Sorry, unable to find [" + filename+"]");
	            System.out.println();
	            return;
			}
			
			config.load(input);
			
			Enumeration<?> e = config.propertyNames();
			while (e.hasMoreElements()) {
				String key   = (String) e.nextElement();
				String value = config.getProperty(key);
				log.info("- Key=["+key+"] -  Value=["+value+"]");
			}
			log.info("<- init()");
		}catch (IOException io) {
			io.printStackTrace();
		}finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
