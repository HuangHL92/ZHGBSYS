package com.insigma.siis.local.jtrans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sfile")
public class SFileDefine {

	private String name;
	
	private Long size;
	
	private String time;
	
	private Long orgrows;
	
	private Long personrows;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getOrgrows() {
		return orgrows;
	}

	public void setOrgrows(Long orgrows) {
		this.orgrows = orgrows;
	}

	public Long getPersonrows() {
		return personrows;
	}

	public void setPersonrows(Long personrows) {
		this.personrows = personrows;
	}

	public SFileDefine(String name, Long size, String time, Long orgrows,
			Long personrows) {
		super();
		this.name = name;
		this.size = size;
		this.time = time;
		this.orgrows = orgrows;
		this.personrows = personrows;
	}

	public SFileDefine() {
		super();
	}
	
}
