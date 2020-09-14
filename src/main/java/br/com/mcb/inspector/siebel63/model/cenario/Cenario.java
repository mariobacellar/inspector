package br.com.mcb.inspector.siebel63.model.cenario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

// https://www.callicoder.com/java-read-write-csv-file-opencsv/
import com.opencsv.bean.CsvBindByName;

import br.com.mcb.inspector.siebel63.Config;
import br.com.mcb.inspector.util.FileUtil;

public class Cenario {

	private final static Logger log = Logger.getLogger(Cenario.class);
	
	@CsvBindByName(column = "integrationObject")
	public String integrationObject;
	
	@CsvBindByName(column = "cenario")
	public String cenario;
	
	@CsvBindByName(column = "condicao")
	public String condicao;
	
	@CsvBindByName(column = "retorno")
	public String   retorno;

	public String[] wildcard;
		
	public static List<Cenario> ltCenarios;
	public static List<Cenario> getLtCenarios() {
		if (ltCenarios==null) loadAllCenarios();
		return ltCenarios;
	}

	public String id;
	public String xmlFileNameReq;
	public String xmlFileNameRsp;
	public String xmlFileNameTemplate;
	

	
	
	public static void loadAllCenarios() {
		try {
			
			log.info("-> loadAllCenarios()"); 
			if (ltCenarios !=null) {
				log.info("<- loadAllCenarios() ... previous uploaded ("+ltCenarios.size()+")");
				return;
			}

			ltCenarios = new ArrayList<Cenario>();
	        List<String[]> ltLinhasCenarios = FileUtil.getAllCsvRecords();
	        for (Iterator<String[]> cenInter = ltLinhasCenarios.iterator(); cenInter.hasNext();) {
				String[] linha = (String[]) cenInter.next();
	        	Cenario cenario = Cenario.newCenario(linha);
	        	ltCenarios.add(cenario);
			}
			log.info("<- loadAllCenarios() ... loaded ("+ltCenarios.size()+")");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	

	private static Cenario newCenario(String[] linha) {
		log.info("-> build("+linha+")");

		String   record = linha[0];
		String[] field  = record.split(";");
		
		List<String> ltWildcard = new ArrayList<String>();
		
		Cenario 
		ret = new Cenario();
		ret.integrationObject = field[0];
		ret.cenario  = field[1];
		ret.condicao = field[2];
		ret.retorno  = field[3];
		int i=4;
		while ( (i<field.length) && (field[i]!=null) ) {
			ltWildcard.add( field[i] );
			i++;
		}
		ret.wildcard = ltWildcard.toArray(new String[0]);
		ret.setup();

		log.info("-> build("+linha+")");
		return ret;
	}
	
	private void setup() {
		this.id = this.integrationObject + "-" + this.cenario + "-" + this.condicao;
		this.xmlFileNameReq = this.integrationObject + "-" + this.cenario + "-" + this.condicao + ".xml";
		this.xmlFileNameRsp = this.integrationObject + "-" + this.cenario + "-" + this.condicao + "-rsp"+".xml";
		this.xmlFileNameTemplate = this.integrationObject + ".tmpl";
	}


	public String toString() {
		return "id=["+id+"]-integrationObject=["+integrationObject+"]-cenario=["+cenario+"]-condicao=["+condicao+"]-retorno=["+retorno+"]-wildcard=["+wildcard+"]-fileReq=["+xmlFileNameReq+"]-fileRsp=["+xmlFileNameRsp+"]-tmpl=["+xmlFileNameTemplate+"]";
	}
	
	
	
	public static void main(String[] args) {
		Cenario.loadAllCenarios();
		Cenario.loadAllCenarios();
		for (Cenario cenario : Cenario.ltCenarios) {
        	log.info(" >>>cenario<<< " + cenario);
        	for (String wild : cenario.wildcard) {
        		log.info("***wild***" + wild);
			}
		}		
	}	
	
	
}
