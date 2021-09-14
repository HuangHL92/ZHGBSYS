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
public class Qryuse implements Serializable {


    public Qryuse() {
    }
    
    private java.lang.String valuename2;
	public java.lang.String getValuename2() {
		return valuename2;
	}

	public void setValuename2(java.lang.String valuename2) {
		this.valuename2 = valuename2;
	}

	/**
     * 字段类型 T时间C字符串(文本)N数字S下拉选
     */
    private java.lang.String labletype;

    public void setLabletype(final java.lang.String labletype) {
        this.labletype = labletype;
    }

    public java.lang.String getLabletype() {
        return this.labletype;
    }


    /**
     * 代码
     */
    private java.lang.String fldcode;

    public void setFldcode(final java.lang.String fldcode) {
        this.fldcode = fldcode;
    }

    public java.lang.String getFldcode() {
        return this.fldcode;
    }


    /**
     * 字段名
     */
    private java.lang.String fldname;

    public void setFldname(final java.lang.String fldname) {
        this.fldname = fldname;
    }

    public java.lang.String getFldname() {
        return this.fldname;
    }


    /**
     * 主键
     */
    private java.lang.String quid;

    public void setQuid(final java.lang.String quid) {
        this.quid = quid;
    }

    public java.lang.String getQuid() {
        return this.quid;
    }


    /**
     * 视图id
     */
    private java.lang.String qvid;

    public void setQvid(final java.lang.String qvid) {
        this.qvid = qvid;
    }

    public java.lang.String getQvid() {
        return this.qvid;
    }


    /**
     * 符号
     */
    private java.lang.String sign;

    public void setSign(final java.lang.String sign) {
        this.sign = sign;
    }

    public java.lang.String getSign() {
        return this.sign;
    }


    /**
     * 条件顺序
     */
    private java.lang.String sort;

    public void setSort(final java.lang.String sort) {
        this.sort = sort;
    }

    public java.lang.String getSort() {
        return this.sort;
    }


    /**
     * 表名
     */
    private java.lang.String tblname;

    public void setTblname(final java.lang.String tblname) {
        this.tblname = tblname;
    }

    public java.lang.String getTblname() {
        return this.tblname;
    }


    /**
     * 值一代码
     */
    private java.lang.String valuecode1;

    public void setValuecode1(final java.lang.String valuecode1) {
        this.valuecode1 = valuecode1;
    }

    public java.lang.String getValuecode1() {
        return this.valuecode1;
    }


    /**
     * 值二代码
     */
    private java.lang.String valuecode2;

    public void setValuecode2(final java.lang.String valuecode2) {
        this.valuecode2 = valuecode2;
    }

    public java.lang.String getValuecode2() {
        return this.valuecode2;
    }


    /**
     * 值一中文名
     */
    private java.lang.String valuename1;

    public void setValuename1(final java.lang.String valuename1) {
        this.valuename1 = valuename1;
    }

    public java.lang.String getValuename1() {
        return this.valuename1;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Quid", getQuid()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Qryuse)) {
            return false;
        }
        Qryuse castOther = (Qryuse) other;
        return new EqualsBuilder().append(this.getQuid(),castOther.getQuid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getQuid()).toHashCode();
    }



}
