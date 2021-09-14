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
public class Qryviewfld implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Qryviewfld() {
    }

	
	private java.lang.String fldnamenote;
    /**
     * 指标项 字段名
     */
    private java.lang.String fldname;

    public java.lang.String getFldnamenote() {
		return fldnamenote;
	}

	public void setFldnamenote(final java.lang.String fldnamenote) {
		this.fldnamenote = fldnamenote;
	}

	public void setFldname(final java.lang.String fldname) {
        this.fldname = fldname;
    }

    public java.lang.String getFldname() {
        return this.fldname;
    }


    /**
     * 主键
     */
    private java.lang.String qvfid;

    public void setQvfid(final java.lang.String qvfid) {
        this.qvfid = qvfid;
    }

    public java.lang.String getQvfid() {
        return this.qvfid;
    }
    
    /**
     * 编号
     */
    private java.lang.String fldnum;
    
    public void setFldnum(final java.lang.String fldnum) {
    	this.fldnum = fldnum;
    }
    
    public java.lang.String getFldnum() {
    	return this.fldnum;
    }


    /**
     * qryview表的主键
     */
    private java.lang.String qvid;

    public void setQvid(final java.lang.String qvid) {
        this.qvid = qvid;
    }

    public java.lang.String getQvid() {
        return this.qvid;
    }


    /**
     * 信息集 表名
     */
    private java.lang.String tblname;

    public void setTblname(final java.lang.String tblname) {
        this.tblname = tblname;
    }

    public java.lang.String getTblname() {
        return this.tblname;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Qvfid", getQvfid()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Qryviewfld)) {
            return false;
        }
        Qryviewfld castOther = (Qryviewfld) other;
        return new EqualsBuilder().append(this.getQvfid(),castOther.getQvfid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getQvfid()).toHashCode();
    }



}
