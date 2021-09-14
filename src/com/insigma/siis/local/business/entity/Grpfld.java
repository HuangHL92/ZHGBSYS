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
public class Grpfld implements Serializable {


    public Grpfld() {
    }

    private java.lang.String ordernum1;
    
    public java.lang.String getOrdernum1() {
		return ordernum1;
	}

	public void setOrdernum1(java.lang.String ordernum1) {
		this.ordernum1 = ordernum1;
	}

	/**
     * �����ֶ�ת��Ϊ������־
     */
    private java.lang.String codetocomm;

    public void setCodetocomm(final java.lang.String codetocomm) {
        this.codetocomm = codetocomm;
    }

    public java.lang.String getCodetocomm() {
        return this.codetocomm;
    }

    private java.lang.String  codetype;
    public java.lang.String getCodetype() {
		return codetype;
	}

	public void setCodetype(java.lang.String codetype) {
		this.codetype = codetype;
	}


	/**
     * �ֶκ�������ַ�
     */
    private java.lang.String connsymbol;

    public void setConnsymbol(final java.lang.String connsymbol) {
        this.connsymbol = connsymbol;
    }

    public java.lang.String getConnsymbol() {
        return this.connsymbol;
    }


    /**
     * code_table_col�������
     */
    private java.lang.String ctci;

    public void setCtci(final java.lang.String ctci) {
        this.ctci = ctci;
    }

    public java.lang.String getCtci() {
        return this.ctci;
    }


    /**
     * ���ڸ�ʽ��
     */
    private java.lang.String datefmt;

    public void setDatefmt(final java.lang.String datefmt) {
        this.datefmt = datefmt;
    }

    public java.lang.String getDatefmt() {
        return this.datefmt;
    }


    /**
     * �ֶκϼƺ���
     */
    private java.lang.String fldfunc;

    public void setFldfunc(final java.lang.String fldfunc) {
        this.fldfunc = fldfunc;
    }

    public java.lang.String getFldfunc() {
        return this.fldfunc;
    }


    /**
     * ָ���� �ֶ���
     */
    private java.lang.String fldname;

    public void setFldname(final java.lang.String fldname) {
        this.fldname = fldname;
    }

    public java.lang.String getFldname() {
        return this.fldname;
    }


    /**
     * ָ����ע�� �����ֶ���
     */
    private java.lang.String fldnamenote;

    public void setFldnamenote(final java.lang.String fldnamenote) {
        this.fldnamenote = fldnamenote;
    }

    public java.lang.String getFldnamenote() {
        return this.fldnamenote;
    }


    /**
     * �ֶ�Ϊ�յĴ����
     */
    private java.lang.String fldnull;

    public void setFldnull(final java.lang.String fldnull) {
        this.fldnull = fldnull;
    }

    public java.lang.String getFldnull() {
        return this.fldnull;
    }


    /**
     * ����
     */
    private java.lang.String gfid;

    public void setGfid(final java.lang.String gfid) {
        this.gfid = gfid;
    }

    public java.lang.String getGfid() {
        return this.gfid;
    }


    /**
     * ֵΪ��ʱ�����ӷ�����ʽ
     */
    private java.lang.String handnull;

    public void setHandnull(final java.lang.String handnull) {
        this.handnull = handnull;
    }

    public java.lang.String getHandnull() {
        return this.handnull;
    }


    /**
     * �ϼ�ͬ�����ֶα�־
     */
    private java.lang.String meregfld;

    public void setMeregfld(final java.lang.String meregfld) {
        this.meregfld = meregfld;
    }

    public java.lang.String getMeregfld() {
        return this.meregfld;
    }


    /**
     * ���
     */
    private java.lang.String ordernum;

    public void setOrdernum(final java.lang.String ordernum) {
        this.ordernum = ordernum;
    }

    public java.lang.String getOrdernum() {
        return this.ordernum;
    }


    /**
     * ͬ����������ӷ�
     */
    private java.lang.String replacefld;

    public void setReplacefld(final java.lang.String replacefld) {
        this.replacefld = replacefld;
    }

    public java.lang.String getReplacefld() {
        return this.replacefld;
    }


    /**
     * ��Ϣ�� ����
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
        return new ToStringBuilder(this).append("Gfid", getGfid()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Grpfld)) {
            return false;
        }
        Grpfld castOther = (Grpfld) other;
        return new EqualsBuilder().append(this.getGfid(),castOther.getGfid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getGfid()).toHashCode();
    }



}
