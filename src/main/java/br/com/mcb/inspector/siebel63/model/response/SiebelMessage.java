package br.com.mcb.inspector.siebel63.model.response;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

import br.com.mcb.inspector.util.FileUtil;


@XmlRootElement(name="SiebelMessage")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class SiebelMessage {
	
	private final static Logger log = Logger.getLogger(SiebelMessage.class);
	
	@XmlElement(name="ListaRetorno") 
	public ListaRetorno ListaRetorno;

	public ListaRetorno getListaRetorno() { 
		return ListaRetorno;
	}

	@XmlAttribute(name="MessageId")		
	public String MessageId;
	
	@XmlAttribute(name="MessageType")	
	public String MessageType;
	
	@XmlAttribute(name="IntObjectName") 
	public String IntObjectName;
	
	public String getMessageId()			{ return MessageId;}
	public String getMessageType()			{ return MessageType;}
	public String getIntObjectName()		{ return IntObjectName;}


	public static SiebelMessage createSiebelMessageBean(String xmlFileName) throws Exception {
		log.info("-> createSiebelMessageBean("+xmlFileName+")");
		File xmlFile = new File( FileUtil.getPathXml() + xmlFileName );
		SiebelMessage ret = createSiebelMessageBean(xmlFile);
		log.info("<- createSiebelMessageBean("+xmlFileName+")");
		return ret;
	}

	public static SiebelMessage createSiebelMessageBean(File xmlFile) throws Exception {
		log.info("-> createSiebelMessageBean("+xmlFile.getName()+")");
		JAXBContext		jaxbContext		 = JAXBContext.newInstance(SiebelMessage.class);
		Unmarshaller	jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		SiebelMessage   ret              = (SiebelMessage) jaxbUnmarshaller.unmarshal(xmlFile);
		log.info("<- createSiebelMessageBean("+ret.toString()+")");
		return ret;
	}
	
	public String toXML() throws Exception {
		log.info("-> toXML()");
		JAXBContext jaxbContext	    = JAXBContext.newInstance(this.getClass());
		Marshaller	jaxbMarshaller	= jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
		jaxbMarshaller.marshal(this,new File("src\\main\\resources\\tmp\\temp.xml"));
		String str = FileUtil.readFile("temp.xml", null);
		log.info("<- toXML()");
		return str;
	}

	
	public static void main(String[] args) {
		
		try {

			String fileName= "FRMCASI-rsp.xml";
			log.info("file=["+fileName+"]");
			SiebelMessage sm = createSiebelMessageBean(fileName);
			log.info("SiebelMessage.Codigo =["+sm.getListaRetorno().getRetorno().getCodigo()+"]");
			log.info("SiebelMessage.Message=["+sm.getListaRetorno().getRetorno().getMensagem()+"]");
			
			String smxml = sm.toXML();
			log.info("smxml=["+smxml+"]");
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}	
}
