package br.com.mcb.inspector.siebel63;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import br.com.mcb.inspector.util.FileUtil;

public class Siebel63HttpConnection {

	public final static Logger log = Logger.getLogger(Siebel63HttpConnection.class);

	public final static String CONTENT_TYPE_KEY 	= "Content-Type";
	public final static String CONTENT_TYPE_VAL_APP	= "application/x-www-form-urlencoded;charset=utf-8";
	public final static String CONTENT_TYPE_VAL_UTF	= "text/xml; charset=UTF-8";

	public final static String CONTENT_LENGHT_KEY 	= "Content-Lenght";

	public final static String CONTENT_LANG_KEY		= "Content-Language";
	public final static String CONTENT_LANG_VAL_PTBR= "pt-BR";

	
	public static String post(String p_url, String p_content) throws Exception{
		log.info("-> post(p_url=["+p_url+"], content=["+p_content+"]");
		
		URL url = new URL(p_url);
		String lenContent = "" + Integer.toString(p_content.getBytes().length);
		int    lenght = p_content.getBytes().length;
		log.info(CONTENT_LENGHT_KEY+"=["+lenContent+"]");
		
		HttpURLConnection 
		httpcom = null;
		httpcom = (HttpURLConnection) url.openConnection();
		httpcom.setRequestMethod("POST");
		httpcom.setUseCaches(false);
		httpcom.setDoInput(true);
		httpcom.setDoOutput(true);
		httpcom.setFixedLengthStreamingMode(lenght);
		httpcom.setRequestProperty(CONTENT_TYPE_KEY  	, CONTENT_TYPE_VAL_UTF);
		httpcom.setRequestProperty(CONTENT_LENGHT_KEY	, lenContent);
//		connection.setRequestProperty(CONTENT_LANG_KEY  	, CONTENT_LANG_VAL_PTBR);
//		connection.setRequestProperty("Accept-Encoding"		, "gzip,deflate");
//		connection.setRequestProperty("Host"				, "crmoihml03.oi.corp.net");
//		connection.setRequestProperty("Connection"			, "Keep-Alive");
//		connection.setRequestProperty("User-Agent"			, "Apache-HttpClient/4.1.1 (java 1.5)");
		httpcom.connect();

		//Envio
		OutputStreamWriter 
		out = new OutputStreamWriter(httpcom.getOutputStream(), "UTF-8");
		out.write(p_content);
		out.flush();
		out.close();

		//Recepção
		InputStream		inputStream		= httpcom.getInputStream();
		BufferedReader	bufferedReader	= new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

		String linha;
		StringBuffer resposta = new StringBuffer();
		while((linha = bufferedReader.readLine()) != null) {
			resposta.append(linha);
			resposta.append('\r');
		}
		bufferedReader.close();

		if(httpcom != null) {
			httpcom.disconnect();
		}

		return resposta.toString();
	}


	@Deprecated
	public static String httpPost(String p_url, String content) throws Exception{
		log.info("-> httpPost()");
		
		int length = content.length();
		URL url = new URL(p_url);
		URLConnection con = url.openConnection();
		log.info("SIEBEL63_URL=["+p_url+"]");
		HttpURLConnection	
		http= (HttpURLConnection)con;
		http.setRequestMethod("POST");
		http.setDoOutput(true);
		http.setFixedLengthStreamingMode(length);
		http.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
		http.connect();

		OutputStream 
		os = http.getOutputStream();
	    os.write(content.getBytes());
	    os.flush();
        os.close();

	    InputStream is		= http.getInputStream();
	    char   		aChar	= 0;
	    StringBuffer sbResponse = new StringBuffer();
	    int data = is.read();
	    while(data != -1) {
	    	aChar = (char) data;
	    	sbResponse.append(aChar);
	    	data = is.read();
	    }
	    is.close();
	    
	    String response = sbResponse.toString();
	    log.info("Response = ["+response+"]");
		log.info("<- httpPost()");
		return response;

	}
	
	public static void main(String[] args) {
		try {
			log.info("-> main");
			String kind			= "tmp";
			String fileNameReq	= "FRMCASI.xml";
			String fileNameRsp	= "FRMCASI-rsp.xml";
			String url			= Config.getParam("siebel63.server.ti8")+ Config.getParam("siebel63.api") + Config.getParam("siebel63.api.params");
			String content		= FileUtil.readFile(fileNameReq, kind);
			String response     = Siebel63HttpConnection.post(url, content);
			
			FileUtil.writeFile(fileNameRsp, response, "tmp");
			
			log.info("url........=["+url+"]");
			log.info("content....=["+content+"]");
			log.info("response...=["+response+"]");
			log.info("<- main");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	
}