package br.com.mcb.inspector.siebel63;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.mcb.inspector.siebel63.model.cenario.Cenario;
import br.com.mcb.inspector.util.FileUtil;

public class Inspector {

	public Config config;
	
	public static final String XML  = "xml";
	public static final String TMPL = "tmpl";

	public static String SIEBEL63_URL;
	public static String SIEBEL63_ENVIRONMENT;
	
	final static Logger log		= Logger.getLogger(Inspector.class);
	final static Logger repolog = Logger.getLogger("repo");

	public static Cenario cenario;
	
	
	public static void perform() {
		try {
			log.info("-> perform()");

			loadConfigProperties(); 		
			
			loadCenarioTesteSiebel63();
			
			buildSiebelMessageRequestFiles();
			
			
			

//			
//			writeReport(mapReqRes);
			
			log.info("<- perform()");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void loadConfigProperties() {
		Config.init();
		log.info("-> loadConfigProperties()");
		SIEBEL63_ENVIRONMENT=Config.getParam("siebel63.api.environment");
		SIEBEL63_URL=Config.getParam("siebel63.server.ti8"); 
		if ("TRG".equals(SIEBEL63_ENVIRONMENT))
			SIEBEL63_URL=Config.getParam("siebel63.server.trg");
		SIEBEL63_URL=SIEBEL63_URL + Config.getParam("siebel63.api") + Config.getParam("siebel63.api.params");
		log.info("- Sending transactions to ["+SIEBEL63_ENVIRONMENT+"] = ["+SIEBEL63_URL+"]" );
		log.info("<- loadConfigProperties()");

	}


	private static void loadCenarioTesteSiebel63() {
		log.info("-> loadCenarioTesteSiebel63()");
		Cenario.loadAllCenarios();
		for (Cenario one : Cenario.getLtCenarios()) {
			log.info("*** cenario: ***" + one);
        	for (String wild : one.wildcard) {
	        	log.info("*** wild: ***" + wild);
			}
		}		
		log.info("<- loadCenarioTesteSiebel63()");
	}
	

	private static void buildSiebelMessageRequestFiles() throws Exception {
		log.info("-> buildSiebelMessageXMLFiles()");
		List<Cenario> cenarios= Cenario.getLtCenarios();
		String template = null;
		for (Cenario cenario : cenarios) {
			if ("integrationObject.tmpl".equals(cenario.xmlFileNameTemplate))continue; 
			template = FileUtil.readFile(cenario.xmlFileNameTemplate, TMPL);
			FileUtil.writeFile(cenario.xmlFileNameReq, template, XML);
		}
		log.info("<- buildSiebelMessageXMLFiles()");
	}



	/**
	 * 
	 * @return
	 */

	public static void main(String[] args) {
		try {

			perform();
			
/*
			loadConfigProperties();
			String xml = "<SiebelMessage MessageId=\"\" MessageType=\"Integration Object\" IntObjectName=\"FRMCASI\"><ListaConsultaMeioDeAcesso><Dados><OrigemRequisicao></OrigemRequisicao><ListaMeioDeAcesso><MeioDeAcesso><NumeroContaFatura>2626273094</NumeroContaFatura><DDD>21</DDD><Designacao>Varejo</Designacao><Classificacao>Normal</Classificacao></MeioDeAcesso></ListaMeioDeAcesso></Dados></ListaConsultaMeioDeAcesso></SiebelMessage>"; 
			String rsp = Siebel63HttpConnection.httpPost(xml);
			log.info("xml=["+xml+"]");
			log.info("rsp=["+rsp+"]");
*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
