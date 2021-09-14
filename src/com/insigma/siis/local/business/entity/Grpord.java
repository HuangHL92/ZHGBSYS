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
public class Grpord implements Serializable {


    public Grpord() {
    }
    
    private java.lang.String ordernum1;
    public java.lang.String getOrdernum1() {
		return ordernum1;
	}

	public void setOrdernum1(java.lang.String ordernum1) {
		this.ordernum1 = ordernum1;
	}

	private java.lang.String orderby;
    
    public java.lang.String getOrderby() {
		return orderby;
	}

	public void setOrderby(java.lang.String orderby) {
		this.orderby = orderby;
	}
	/**
     * col_table_col主键
     */
    private java.lang.String ctci;

    public void setCtci(final java.lang.String ctci) {
        this.ctci = ctci;
    }

    public java.lang.String getCtci() {
        return this.ctci;
    }


    /**
     * 字段
     */
    private java.lang.String fldcode;

    public void setFldcode(final java.lang.String fldcode) {
        this.fldcode = fldcode;
    }

    public java.lang.String getFldcode() {
        return this.fldcode;
    }


    /**
     * 主键
     */
    private java.lang.String go001;

    public void setGo001(final java.lang.String go001) {
        this.go001 = go001;
    }

    public java.lang.String getGo001() {
        return this.go001;
    }


    /**
     * 排序
     */
    private java.lang.String ordernum;

    public void setOrdernum(final java.lang.String ordernum) {
        this.ordernum = ordernum;
    }

    public java.lang.String getOrdernum() {
        return this.ordernum;
    }


    /**
     * 表
     */
    private java.lang.String tblcode;

    public void setTblcode(final java.lang.String tblcode) {
        this.tblcode = tblcode;
    }

    public java.lang.String getTblcode() {
        return this.tblcode;
    }





    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Go001", getGo001()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Grpord)) {
            return false;
        }
        Grpord castOther = (Grpord) other;
        return new EqualsBuilder().append(this.getGo001(),castOther.getGo001()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getGo001()).toHashCode();
    }



}
