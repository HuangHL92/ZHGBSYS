package com.insigma.siis.local.business.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @Oracle工具生成实体
 * @author 徐亚涛(xuyatao@126.com)
 * @copytright xuyatao 2010-2015
 */
public class Inf implements Serializable {
	private java.lang.String infoid;
	private java.lang.String infoname;
	private java.lang.String infotype;
	private java.lang.String codetype;
   
	public java.lang.String getInfoid() {
		return infoid;
	}


	public void setInfoid(java.lang.String infoid) {
		this.infoid = infoid;
	}


	public java.lang.String getInfoname() {
		return infoname;
	}


	public void setInfoname(java.lang.String infoname) {
		this.infoname = infoname;
	}


	public java.lang.String getInfotype() {
		return infotype;
	}


	public void setInfotype(java.lang.String infotype) {
		this.infotype = infotype;
	}


	public Inf() {
    }


	public java.lang.String getCodetype() {
		return codetype;
	}


	public void setCodetype(java.lang.String codetype) {
		this.codetype = codetype;
	}




}
