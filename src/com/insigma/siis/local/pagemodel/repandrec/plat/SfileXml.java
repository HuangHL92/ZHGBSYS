package com.insigma.siis.local.pagemodel.repandrec.plat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="sfile")
public class SfileXml {

	private String name;
	
	private String size;
	
	private String createtime;
	
	private String orgrows;
	
	private String personrows;

	public SfileXml(String name, String size, String createtime,
			String orgrows, String personrows) {
		super();
		this.name = name;
		this.size = size;
		this.createtime = createtime;
		this.orgrows = orgrows;
		this.personrows = personrows;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getOrgrows() {
		return orgrows;
	}

	public void setOrgrows(String orgrows) {
		this.orgrows = orgrows;
	}

	public String getPersonrows() {
		return personrows;
	}

	public void setPersonrows(String personrows) {
		this.personrows = personrows;
	}

	public SfileXml() {
		super();
	}
	
}
