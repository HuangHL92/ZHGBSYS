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
public class A62 implements Serializable {


    public A62() {
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
     * ������ѡ
     */
    private java.lang.String a2950;

    public void setA2950(final java.lang.String a2950) {
        this.a2950 = a2950;
    }

    public java.lang.String getA2950() {
        return this.a2950;
    }


    /**
     * ��ѡ���              ZB142����ѡ��ѡ�����
     */
    private java.lang.String a6202;

    public void setA6202(final java.lang.String a6202) {
        this.a6202 = a6202;
    }

    public java.lang.String getA6202() {
        return this.a6202;
    }


    /**
     * ��ѡ��ʽ              ZB143����ѡ��ʽ��
     */
    private java.lang.String a6203;

    public void setA6203(final java.lang.String a6203) {
        this.a6203 = a6203;
    }

    public java.lang.String getA6203() {
        return this.a6203;
    }


    /**
     * ԭ��λ����         ZB141��������λ��δ��롷
     */
    private java.lang.String a6204;

    public void setA6204(final java.lang.String a6204) {
        this.a6204 = a6204;
    }

    public java.lang.String getA6204() {
        return this.a6204;
    }


    /**
     * ��ѡʱ��
     */
    private java.lang.String a6205;

    public void setA6205(final java.lang.String a6205) {
        this.a6205 = a6205;
    }

    public java.lang.String getA6205() {
        return this.a6205;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("A0000", getA0000()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A62)) {
            return false;
        }
        A62 castOther = (A62) other;
        return new EqualsBuilder().append(this.getA0000(),castOther.getA0000()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA0000()).toHashCode();
    }



}
