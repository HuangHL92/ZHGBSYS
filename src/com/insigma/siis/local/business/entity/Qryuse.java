package com.insigma.siis.local.business.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @Oracle��������ʵ��
 * @author ������(xuyatao@126.com)
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
     * �ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
     */
    private java.lang.String labletype;

    public void setLabletype(final java.lang.String labletype) {
        this.labletype = labletype;
    }

    public java.lang.String getLabletype() {
        return this.labletype;
    }


    /**
     * ����
     */
    private java.lang.String fldcode;

    public void setFldcode(final java.lang.String fldcode) {
        this.fldcode = fldcode;
    }

    public java.lang.String getFldcode() {
        return this.fldcode;
    }


    /**
     * �ֶ���
     */
    private java.lang.String fldname;

    public void setFldname(final java.lang.String fldname) {
        this.fldname = fldname;
    }

    public java.lang.String getFldname() {
        return this.fldname;
    }


    /**
     * ����
     */
    private java.lang.String quid;

    public void setQuid(final java.lang.String quid) {
        this.quid = quid;
    }

    public java.lang.String getQuid() {
        return this.quid;
    }


    /**
     * ��ͼid
     */
    private java.lang.String qvid;

    public void setQvid(final java.lang.String qvid) {
        this.qvid = qvid;
    }

    public java.lang.String getQvid() {
        return this.qvid;
    }


    /**
     * ����
     */
    private java.lang.String sign;

    public void setSign(final java.lang.String sign) {
        this.sign = sign;
    }

    public java.lang.String getSign() {
        return this.sign;
    }


    /**
     * ����˳��
     */
    private java.lang.String sort;

    public void setSort(final java.lang.String sort) {
        this.sort = sort;
    }

    public java.lang.String getSort() {
        return this.sort;
    }


    /**
     * ����
     */
    private java.lang.String tblname;

    public void setTblname(final java.lang.String tblname) {
        this.tblname = tblname;
    }

    public java.lang.String getTblname() {
        return this.tblname;
    }


    /**
     * ֵһ����
     */
    private java.lang.String valuecode1;

    public void setValuecode1(final java.lang.String valuecode1) {
        this.valuecode1 = valuecode1;
    }

    public java.lang.String getValuecode1() {
        return this.valuecode1;
    }


    /**
     * ֵ������
     */
    private java.lang.String valuecode2;

    public void setValuecode2(final java.lang.String valuecode2) {
        this.valuecode2 = valuecode2;
    }

    public java.lang.String getValuecode2() {
        return this.valuecode2;
    }


    /**
     * ֵһ������
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
