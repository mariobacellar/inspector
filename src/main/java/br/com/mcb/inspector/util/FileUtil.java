package br.com.mcb.inspector.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.opencsv.CSVReader;

import br.com.mcb.inspector.siebel63.Config;
import br.com.mcb.inspector.siebel63.model.cenario.Cenario;
import br.com.mcb.inspector.siebel63.model.response.SiebelMessage;

public class FileUtil {

	private final static Logger log = Logger.getLogger(Config.class);
	
	public static String getFileNameCsv() { return getPathCsv()+ Config.getParam("siebel63.file.csv.file");}
	public static String getPathCsv()     { return               Config.getParam("siebel63.file.csv.dir");}
	
	public static String getPathXml()     { return               Config.getParam("siebel63.file.xml.dir");}
	public static String getPathTmpl()    { return               Config.getParam("siebel63.file.tmpl.dir");}
	public static String getPathTmp()     { return               Config.getParam("siebel63.file.tmp.dir");}
	

	/**
	 * 
	 * @param fileName
	 * @param content
	 * @param kind Values is: 'xml' ou 'tmpl'
	 */
	public static void writeFile(String fileName, String content, String kind) {
		log.info("-> writeFile(fileNeme=["+fileName+"], content=["+content+"]), kind=["+kind+"]");
		BufferedWriter	bw = null;
		FileWriter		fw = null;
		try{
			String dir = null;
			if ("xml".equals(kind)) 
				dir = getPathXml();
			else
			if ("tmp".equals(kind)) 
				dir = getPathTmp(); 
			else
			if ("tmpl".equals(kind)) 
				dir = getPathTmpl(); 
			else 
				dir="src\\main\\resources\\tmp\\";
			log.info("dir=["+dir+"]");
								
			File file = new File (dir+fileName);
			log.info("file=["+file+"]");
			
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(content);
			log.info("Content wrote=["+content+"]");
		}catch (IOException e){
			e.printStackTrace();
		}catch (Exception ee) {
			ee.printStackTrace();
			log.error(ee);
		}finally{
			try {
				if (bw != null) {
					bw.close();
				}
				if (fw != null) {
					fw.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		log.info("<- writeFile(fileNeme=["+fileName+"], content=["+content+"]), kind=["+kind+"]");
	}
	
	public static String readFile(String fileName, String kind) throws Exception {
		log.info("-> readFile("+fileName+"), kind=["+kind+"]");

		String dir = null;
		if ("xml".equals(kind)) 
			dir = getPathXml();
		else
		if ("tmp".equals(kind)) 
			dir = getPathTmp(); 
		else
		if ("tmpl".equals(kind)) 
			dir = getPathTmpl(); 
		else 
			dir=".\\src\\main\\resources\\tmp\\";
		log.info("dir=["+dir+"]");
							
		File file = new File (dir+fileName);
		log.info("file=["+file+"]");
		
		StringBuffer	content = new StringBuffer();
		BufferedReader	buffer  = new BufferedReader( new FileReader( file ) );
		String xml = "";
		int ln = 0;
		while ( xml != null) {
			ln++;
			xml = buffer.readLine();
			if (xml!=null || "null".equals(xml))
			content.append(xml);
		}
		buffer.close();
		log.info("content=["+content.toString()+")");
		log.info("<-- readFile("+fileName+"), kind=["+kind+"]");
		return content.toString();
	}
	

	
	public static List<String[]> getAllCsvRecords() {
		log.info("-> getAllCsvRecords()");
		CSVReader csvReader	= null;
		try {
			String csvName = getFileNameCsv();
			log.info("Name=["+csvName+"]");
			Reader reader = Files.newBufferedReader( Paths.get(csvName) );
			csvReader = new CSVReader(reader);
			log.info("<- getAllCsvRecords()");
			return csvReader.readAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return null;
		} finally {
			try {
				if (csvReader!=null) csvReader.close();
			} catch (IOException e) {
				log.error(e);
				e.printStackTrace();
			}
		}
	}	
	
	public static void main(String[] args) {
		String fileNeme="mario.txt";
		String content="Writring test";
		String kind="xml";
		
		List<Cenario> cenarios = Cenario.getLtCenarios();
		for (Cenario cenario : cenarios) {
			fileNeme = cenario.xmlFileNameRsp;
			content  = "Writring test  -> "+cenario.cenario;
			writeFile(fileNeme, content, kind);
		}
		
	}
	
}
