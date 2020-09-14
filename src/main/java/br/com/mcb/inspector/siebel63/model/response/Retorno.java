package br.com.mcb.inspector.siebel63.model.response;

import javax.xml.bind.annotation.XmlElement;

public class Retorno {

	@XmlElement(name="Codigo")   
	int Codigo;
	
	@XmlElement(name="Mensagem") 
	String Mensagem;
	
	public int		getCodigo()		{return Codigo;}
	public String	getMensagem()	{return Mensagem;}
	
	
}
