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
public class Competenceuserdept implements Serializable {


    public Competenceuserdept() {
    }


    /**
     * ��������
     */
    private java.lang.String b0111;

    public void setB0111(final java.lang.String b0111) {
        this.b0111 = b0111;
    }

    public java.lang.String getB0111() {
        return this.b0111;
    }


    /**
     * Ȩ������(0�������1��ά��)
     */
    private java.lang.String type;

    public void setType(final java.lang.String type) {
        this.type = type;
    }

    public java.lang.String getType() {
        return this.type;
    }


    /**
     * �û�������������
     */
    private java.lang.String userdeptid;

    public void setUserdeptid(final java.lang.String userdeptid) {
        this.userdeptid = userdeptid;
    }

    public java.lang.String getUserdeptid() {
        return this.userdeptid;
    }


    /**
     * �û�����
     */
    private java.lang.String userid;

    public void setUserid(final java.lang.String userid) {
        this.userid = userid;
    }

    public java.lang.String getUserid() {
        return this.userid;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Userdeptid", getUserdeptid()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Competenceuserdept)) {
            return false;
        }
        Competenceuserdept castOther = (Competenceuserdept) other;
        return new EqualsBuilder().append(this.getUserdeptid(),castOther.getUserdeptid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getUserdeptid()).toHashCode();
    }



}
