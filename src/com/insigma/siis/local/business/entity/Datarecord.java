package com.insigma.siis.local.business.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 机构回收站实体
 * @author 苏城锋
 * 2018-4-16
 */
public class Datarecord implements Serializable {


    public Datarecord() {
    }


    public int DATARECORDID;
    public String ORGANIZZTION;
    public String TEMP_TABLE;
    public String FILENAME;
    public String REMOVEDATE;
	public int getDATARECORDID() {
		return DATARECORDID;
	}
	public void setDATARECORDID(int dATARECORDID) {
		DATARECORDID = dATARECORDID;
	}
	public String getORGANIZZTION() {
		return ORGANIZZTION;
	}
	public void setORGANIZZTION(String oRGANIZZTION) {
		ORGANIZZTION = oRGANIZZTION;
	}
	public String getTEMP_TABLE() {
		return TEMP_TABLE;
	}
	public void setTEMP_TABLE(String tEMP_TABLE) {
		TEMP_TABLE = tEMP_TABLE;
	}
	public String getFILENAME() {
		return FILENAME;
	}
	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}
	public String getREMOVEDATE() {
		return REMOVEDATE;
	}
	public void setREMOVEDATE(String rEMOVEDATE) {
		REMOVEDATE = rEMOVEDATE;
	}
    

}
