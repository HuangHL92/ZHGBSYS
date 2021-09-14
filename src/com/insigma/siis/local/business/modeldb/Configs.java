package com.insigma.siis.local.business.modeldb;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Configs {
	private List<Config> config;
    
	
	
	public Configs() {
		super();
		// TODO Auto-generated constructor stub
	}
	


	public Configs(List<Config> config) {
		super();
		this.config = config;
	}



	public List<Config> getConfig() {
		return config;
	}



	public void setConfig(List<Config> config) {
		this.config = config;
	}
	
}
