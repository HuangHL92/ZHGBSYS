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
public class Grprcdsplmd implements Serializable {


    public Grprcdsplmd() {
    }

    
    
    private java.lang.String qrysql;
    public java.lang.String getCondition() {
		return condition;
	}

	public void setCondition(java.lang.String condition) {
		this.condition = condition;
	}

	public java.lang.String getQrysql() {
		return qrysql;
	}

	public void setQrysql(java.lang.String qrysql) {
		this.qrysql = qrysql;
	}



	private java.lang.String condition;
    private java.lang.String slfcfg;
    public java.lang.String getSlfcfg() {
		return slfcfg;
	}

	public void setSlfcfg(final java.lang.String slfcfg) {
		this.slfcfg = slfcfg;
	}


	/**
     * ��¼����о�������c��־ 1�� 0��
     */
    private java.lang.String centerbranch;

    public void setCenterbranch(final java.lang.String centerbranch) {
        this.centerbranch = centerbranch;
    }

    public java.lang.String getCenterbranch() {
        return this.centerbranch;
    }


    /**
     * ��¼�����ӷ�
     */
    private java.lang.String connector;

    public void setConnector(final java.lang.String connector) {
        this.connector = connector;
    }

    public java.lang.String getConnector() {
        return this.connector;
    }


    /**
     * code_table_col����
     */
    private java.lang.String ctci;

    public void setCtci(final java.lang.String ctci) {
        this.ctci = ctci;
    }

    public java.lang.String getCtci() {
        return this.ctci;
    }


    /**
     * ��ѯ��¼������
     */
    private java.lang.String endrow;

    public void setEndrow(final java.lang.String endrow) {
        this.endrow = endrow;
    }

    public java.lang.String getEndrow() {
        return this.endrow;
    }


    /**
     * �����п�ʼ������־ 1�� 0��
     */
    private java.lang.String firstindex;

    public void setFirstindex(final java.lang.String firstindex) {
        this.firstindex = firstindex;
    }

    public java.lang.String getFirstindex() {
        return this.firstindex;
    }


    /**
     * ��������ÿ���ַ���
     */
    private java.lang.String indentrow;

    public void setIndentrow(final java.lang.String indentrow) {
        this.indentrow = indentrow;
    }

    public java.lang.String getIndentrow() {
        return this.indentrow;
    }


    /**
     * �����ַ�λ����
     */
    private java.lang.String indextnum;

    public void setIndextnum(final java.lang.String indextnum) {
        this.indextnum = indextnum;
    }

    public java.lang.String getIndextnum() {
        return this.indextnum;
    }


    /**
     * ��ѯ���Ϊsql�������ʽ��־ 1�� 0��
     */
    private java.lang.String issqlfunc;

    public void setIssqlfunc(final java.lang.String issqlfunc) {
        this.issqlfunc = issqlfunc;
    }

    public java.lang.String getIssqlfunc() {
        return this.issqlfunc;
    }


    /**
     * ����
     */
    private java.lang.String rsm001;

    public void setRsm001(final java.lang.String rsm001) {
        this.rsm001 = rsm001;
    }

    public java.lang.String getRsm001() {
        return this.rsm001;
    }


    /**
     * ��ѯ��¼��ʼ��
     */
    private java.lang.String startrow;

    public void setStartrow(final java.lang.String startrow) {
        this.startrow = startrow;
    }

    public java.lang.String getStartrow() {
        return this.startrow;
    }





    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Rsm001", getRsm001()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Grprcdsplmd)) {
            return false;
        }
        Grprcdsplmd castOther = (Grprcdsplmd) other;
        return new EqualsBuilder().append(this.getRsm001(),castOther.getRsm001()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getRsm001()).toHashCode();
    }



}
