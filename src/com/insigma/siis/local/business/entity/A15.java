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
public class A15 implements Serializable {


    public A15() {
    }


    /**
     * ��ԱΨһ��ʶ��
     */
    private java.lang.String a0000;

    public void setA0000(final java.lang.String a0000) {
        this.a0000 = a0000;
    }

    public java.lang.String getA0000() {
        return this.a0000;
    }


    /**
     * ����
     */
    private java.lang.String a1500;

    public void setA1500(final java.lang.String a1500) {
        this.a1500 = a1500;
    }

    public java.lang.String getA1500() {
        return this.a1500;
    }


    /**
     * ���˽��
     */
    private java.lang.String a1517;

    public void setA1517(final java.lang.String a1517) {
        this.a1517 = a1517;
    }

    public java.lang.String getA1517() {
        return this.a1517;
    }


    /**
     * �������
     */
    private java.lang.String a1521;

    public void setA1521(final java.lang.String a1521) {
        this.a1521 = a1521;
    }

    public java.lang.String getA1521() {
        return this.a1521;
    }


    /**
     * ���������
     */
    private java.lang.String a1527;

    public void setA1527(final java.lang.String a1527) {
        this.a1527 = a1527;
    }

    public java.lang.String getA1527() {
        return this.a1527;
    }


    /**
     * ��ʶ
     */
    private java.lang.String updated;

    public void setUpdated(final java.lang.String updated) {
        this.updated = updated;
    }

    public java.lang.String getUpdated() {
        return this.updated;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("A1500", getA1500()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A15)) {
            return false;
        }
        A15 castOther = (A15) other;
        return new EqualsBuilder().append(this.getA1500(),castOther.getA1500()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA1500()).toHashCode();
    }



}
