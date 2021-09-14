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
public class Codevaluesort implements Serializable {


    public Codevaluesort() {
    }


    /**
     * 
     */
    private java.lang.String codetype;

    public void setCodetype(final java.lang.String codetype) {
        this.codetype = codetype;
    }

    public java.lang.String getCodetype() {
        return this.codetype;
    }


    /**
     * 
     */
    private java.lang.String codevalue;

    public void setCodevalue(final java.lang.String codevalue) {
        this.codevalue = codevalue;
    }

    public java.lang.String getCodevalue() {
        return this.codevalue;
    }


    /**
     * 
     */
    private java.lang.Long id;

    public void setId(final java.lang.Long id) {
        this.id = id;
    }

    public java.lang.Long getId() {
        return this.id;
    }


    /**
     * 
     */
    private java.lang.String sortvalue;

    public void setSortvalue(final java.lang.String sortvalue) {
        this.sortvalue = sortvalue;
    }

    public java.lang.String getSortvalue() {
        return this.sortvalue;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Id", getId()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Codevaluesort)) {
            return false;
        }
        Codevaluesort castOther = (Codevaluesort) other;
        return new EqualsBuilder().append(this.getId(),castOther.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getId()).toHashCode();
    }



}
