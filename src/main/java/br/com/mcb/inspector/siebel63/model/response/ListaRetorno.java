package br.com.mcb.inspector.siebel63.model.response;

import javax.xml.bind.annotation.XmlElement;

public class ListaRetorno {

	@XmlElement(name="Retorno") public Retorno Retorno;
	
	public Retorno	getRetorno(){ 
		return	Retorno;
	}

	
}
