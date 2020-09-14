package br.com.mcb.inspector.util;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

public class Common {
	
	final static Logger log	= Logger.getLogger(Common.class);
	
	private static String text;

	public static void main (String[] args) {
	    text = "Hello >$1< >$2< >$3<";
	    
	    String xml= "SiebelMessage 1 2 $";
	    log.info("xml=["+xml+"]");

	    List<String> toRemove = Arrays.asList("1", "2", "$");
	    String newxmls = null;
	    for (String removeString : toRemove) {
		    log.info("removeString=["+removeString+"]");
    	
		    newxmls = replaceTextWithEmptyString(removeString, xml);
			xml = newxmls; 
		}
	    log.info("newxmls=["+newxmls+"]");
	    
	}

	private static String replaceTextWithEmptyString(String whatToReplace, String xml) {
	    return xml.replaceAll(whatToReplace, "");
	}
	
	
	
	
}
