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
public class A60 implements Serializable {


    public A60() {
    }


    /**
     * ��Աͳһ��ʶ��
     */
    private java.lang.String a0000;

    public void setA0000(final java.lang.String a0000) {
        this.a0000 = a0000;
    }

    public java.lang.String getA0000() {
        return this.a0000;
    }


    /**
     * �Ƿ���¼����Ա
     */
    private java.lang.String a6001;

    public void setA6001(final java.lang.String a6001) {
        this.a6001 = a6001;
    }

    public java.lang.String getA6001() {
        return this.a6001;
    }


    /**
     * ¼��ʱ��
     */
    private java.lang.String a6002;

    public void setA6002(final java.lang.String a6002) {
        this.a6002 = a6002;
    }

    public java.lang.String getA6002() {
        return this.a6002;
    }


    /**
     * ¼��ʱ������ò
     */
    private java.lang.String a6003;

    public void setA6003(final java.lang.String a6003) {
        this.a6003 = a6003;
    }

    public java.lang.String getA6003() {
        return this.a6003;
    }


    /**
     * ¼��ʱѧ������
     */
    private java.lang.String a6004;

    public void setA6004(final java.lang.String a6004) {
        this.a6004 = a6004;
    }

    public java.lang.String getA6004() {
        return this.a6004;
    }


    /**
     * ¼��ʱѧ������      ZB64��ѧ�����롷
     */
    private java.lang.String a6005;

    public void setA6005(final java.lang.String a6005) {
        this.a6005 = a6005;
    }

    public java.lang.String getA6005() {
        return this.a6005;
    }


    /**
     * ¼��ʱѧλ����
     */
    private java.lang.String a6006;

    public void setA6006(final java.lang.String a6006) {
        this.a6006 = a6006;
    }

    public java.lang.String getA6006() {
        return this.a6006;
    }


    /**
     * ¼��ʱѧλ����      GB/T6864���л����񹲺͹�ѧλ���롷
     */
    private java.lang.String a6007;

    public void setA6007(final java.lang.String a6007) {
        this.a6007 = a6007;
    }

    public java.lang.String getA6007() {
        return this.a6007;
    }


    /**
     * ¼��ʱ���㹤��ʱ��
     */
    private java.lang.String a6008;

    public void setA6008(final java.lang.String a6008) {
        this.a6008 = a6008;
    }

    public java.lang.String getA6008() {
        return this.a6008;
    }


    /**
     * ��Ա��Դ���           ZB146����Ա��Դ������롷
     */
    private java.lang.String a6009;

    public void setA6009(final java.lang.String a6009) {
        this.a6009 = a6009;
    }

    public java.lang.String getA6009() {
        return this.a6009;
    }


    /**
     * �Ƿ���������Ŀ��Ա   ZB147��������Ŀ��Ա���롷
     */
    private java.lang.String a6010;

    public void setA6010(final java.lang.String a6010) {
        this.a6010 = a6010;
    }

    public java.lang.String getA6010() {
        return this.a6010;
    }


    /**
     * �Ƿ�����ʿ��
     */
    private java.lang.String a6011;

    public void setA6011(final java.lang.String a6011) {
        this.a6011 = a6011;
    }

    public java.lang.String getA6011() {
        return this.a6011;
    }


    /**
     * �Ƿ����۴�ѧ��ʿ��
     */
    private java.lang.String a6012;

    public void setA6012(final java.lang.String a6012) {
        this.a6012 = a6012;
    }

    public java.lang.String getA6012() {
        return this.a6012;
    }


    /**
     * �Ƿ�м���
     */
    private java.lang.String a6013;

    public void setA6013(final java.lang.String a6013) {
        this.a6013 = a6013;
    }

    public java.lang.String getA6013() {
        return this.a6013;
    }


    /**
     * �Ƿ��к�����ѧ����
     */
    private java.lang.String a6014;

    public void setA6014(final java.lang.String a6014) {
        this.a6014 = a6014;
    }

    public java.lang.String getA6014() {
        return this.a6014;
    }


    /**
     * ��ѧ����
     */
    private java.lang.String a6015;

    public void setA6015(final java.lang.String a6015) {
        this.a6015 = a6015;
    }

    public java.lang.String getA6015() {
        return this.a6015;
    }


    /**
     * �Ƿ��к��⹤������
     */
    private java.lang.String a6016;

    public void setA6016(final java.lang.String a6016) {
        this.a6016 = a6016;
    }

    public java.lang.String getA6016() {
        return this.a6016;
    }


    /**
     * ���⹤������
     */
    private java.lang.String a6017;

    public void setA6017(final java.lang.String a6017) {
        this.a6017 = a6017;
    }

    public java.lang.String getA6017() {
        return this.a6017;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("A0000", getA0000()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A60)) {
            return false;
        }
        A60 castOther = (A60) other;
        return new EqualsBuilder().append(this.getA0000(),castOther.getA0000()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA0000()).toHashCode();
    }



}
