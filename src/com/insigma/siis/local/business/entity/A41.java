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
public class A41 implements Serializable {


    public A41() {
    }


    /**
     * ��ѵ��Ա��ʶ
     */
    private java.lang.String a0000;

    public void setA0000(final java.lang.String a0000) {
        this.a0000 = a0000;
    }

    public java.lang.String getA0000() {
        return this.a0000;
    }


    /**
     * ��ѵ���ʶ
     */
    private java.lang.String a1100;

    public void setA1100(final java.lang.String a1100) {
        this.a1100 = a1100;
    }

    public java.lang.String getA1100() {
        return this.a1100;
    }


    /**
     * ��ѵ��Ψһ��ʶ
     */
    private java.lang.String a4100;

    public void setA4100(final java.lang.String a4100) {
        this.a4100 = a4100;
    }

    public java.lang.String getA4100() {
        return this.a4100;
    }


    /**
     * ��ٱ�ʶ
     */
    private java.lang.String a4101;

    public void setA4101(final java.lang.String a4101) {
        this.a4101 = a4101;
    }

    public java.lang.String getA4101() {
        return this.a4101;
    }


    /**
     * ȱϯ��ʶ
     */
    private java.lang.String a4102;

    public void setA4102(final java.lang.String a4102) {
        this.a4102 = a4102;
    }

    public java.lang.String getA4102() {
        return this.a4102;
    }


    /**
     * ���ĳɼ���ʶ
     */
    private java.lang.String a4103;

    public void setA4103(final java.lang.String a4103) {
        this.a4103 = a4103;
    }

    public java.lang.String getA4103() {
        return this.a4103;
    }


    /**
     * ѧ�ֱ�ʶ
     */
    private java.lang.String a4104;

    public void setA4104(final java.lang.String a4104) {
        this.a4104 = a4104;
    }

    public java.lang.String getA4104() {
        return this.a4104;
    }


    /**
     * ��ѵ�����ʶ
     */
    private java.lang.String a4105;

    public void setA4105(final java.lang.String a4105) {
        this.a4105 = a4105;
    }

    public java.lang.String getA4105() {
        return this.a4105;
    }


    /**
     * ��ע��ʶ
     */
    private java.lang.String a4199;

    public void setA4199(final java.lang.String a4199) {
        this.a4199 = a4199;
    }

    public java.lang.String getA4199() {
        return this.a4199;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("A4100", getA4100()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A41)) {
            return false;
        }
        A41 castOther = (A41) other;
        return new EqualsBuilder().append(this.getA4100(),castOther.getA4100()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA4100()).toHashCode();
    }



}
