package br.com.mcb.inspector.remedy;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Inspector {

    public static void main(String[] args) throws MalformedURLException, IOException {

        BufferedReader br = null;
        
        try {


            while (true){
            	
            	System.out.println("###########################################################");
            	System.out.println("Hora Status =["+getCurrentTimeStamp()+"]");
            	System.out.println("###########################################################");
                
            	StringBuffer writeSB = new StringBuffer();

            	ArrayList<String> 
	        	ltARS = new ArrayList<String>();
	        	ltARS = buildListFromFile();
	
	        	for (String ars : ltARS) {
	
	        		// Pula ARS comentada
	        		if (ars.indexOf("#")>=0) continue;
	        		
	        		URL url = new URL("http://10.32.214.139/statusreport/chamado.php?report=1&ars="+ars);
	                br = new BufferedReader(new InputStreamReader(url.openStream()));
	
	                String line;
	
	                StringBuilder sb = new StringBuilder();
	                
	                String  insterestBlock="<em>Status:&nbsp;</em></td></tr>				<tr><td class=\"campo_txt\"";
	                //System.out.println("insterestBlock = ["+insterestBlock+"]");
	
	                while ((line = br.readLine()) != null)               
	                	sb.append(line);
	                
	                line =sb.toString();
	                //System.out.println("line (ANTES) = ["+line+"]");
	                
	                
	            	int begin1 = line.indexOf(insterestBlock)+1;
	            	int begin2 = begin1+ insterestBlock.length() ;// line.indexOf(";</td></tr>",begin1);
	            	int fim   = line.indexOf("&nbsp;</td></tr>",begin2);
	            
	            	line = line.substring(begin2,fim).trim();
	                System.out.println("===========================================================");
	                System.out.println("ARS = ["+ars+"] - Status = ["+line+"]");
	                writeSB.append    ("ARS = ["+ars+"] - Status = ["+line+"]\n");
	                System.out.println("===========================================================");
	                System.out.println("===========================================================");
				}
	
	            //writeStatus(writeSB);
	            Thread.sleep(10000);
            }
            
        }catch(Exception e){
        	e.printStackTrace();
        } finally {

            if (br != null) {
                br.close();
            }
        }
    }
    
    
    public static ArrayList<String> buildListFromFile() throws Exception {
    	File file = new File( ".\\src\\main\\resources\\ars.txt");
    	
//    	String classpath = System.getProperty("java.class.path");
//    	System.out.println("classpath = ["+classpath+"]");

    	//InputStream file = Inspector.class.getResourceAsStream(".\\src\\main\\resources\\ars.txt") ;
    	
    	Scanner s = new Scanner( file);
    	ArrayList<String> list = new ArrayList<String>();
    	while (s.hasNext()){
    	    list.add(s.next());
    	}
    	s.close();
    	return list;
    }

	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
    
	public static String getCurrentTimeStamp2FileName() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HHmmss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}

	public static void writeStatus(StringBuffer line) throws Exception {
    	String fileName = ".\\status\\ARS_STATUS_"+getCurrentTimeStamp2FileName( );
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		writer.println(line.toString());
		writer.close();
    }
}