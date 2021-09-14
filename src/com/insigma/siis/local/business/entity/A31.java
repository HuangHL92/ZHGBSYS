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
public class A31 implements Serializable {


    public A31() {
    }

    private java.lang.String a3108;
    private java.lang.String a3109;
    private java.lang.String a3110;

    /**
     * 外键
     */
    private java.lang.String a0000;

    public void setA0000(final java.lang.String a0000) {
        this.a0000 = a0000;
    }

    public java.lang.String getA0000() {
        return this.a0000;
    }


    /**
     * 离退类别
     */
    private java.lang.String a3101;

    public void setA3101(final java.lang.String a3101) {
        this.a3101 = a3101;
    }

    public java.lang.String getA3101() {
        return this.a3101;
    }


    /**
     * 离退批准时间
     */
    private java.lang.String a3104;

    public void setA3104(final java.lang.String a3104) {
        this.a3104 = a3104;
    }

    public java.lang.String getA3104() {
        return this.a3104;
    }


    /**
     * 离退前级别
     */
    private java.lang.String a3107;

    public void setA3107(final java.lang.String a3107) {
        this.a3107 = a3107;
    }

    public java.lang.String getA3107() {
        return this.a3107;
    }


    /**
     * 离退后管理单位
     */
    private java.lang.String a3117a;

    public void setA3117a(final java.lang.String a3117a) {
        this.a3117a = a3117a;
    }

    public java.lang.String getA3117a() {
        return this.a3117a;
    }


    /**
     * 曾任最高职务
     */
    private java.lang.String a3118;

    public void setA3118(final java.lang.String a3118) {
        this.a3118 = a3118;
    }

    public java.lang.String getA3118() {
        return this.a3118;
    }


    /**
     * 离退批准文号
     */
    private java.lang.String a3137;

    public void setA3137(final java.lang.String a3137) {
        this.a3137 = a3137;
    }

    public java.lang.String getA3137() {
        return this.a3137;
    }


    /**
     * 离退批准单位
     */
    private java.lang.String a3138;

    public void setA3138(final java.lang.String a3138) {
        this.a3138 = a3138;
    }

    public java.lang.String getA3138() {
        return this.a3138;
    }


    /**
     * 标识
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
        return new ToStringBuilder(this).append("A0000", getA0000()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A31)) {
            return false;
        }
        A31 castOther = (A31) other;
        return new EqualsBuilder().append(this.getA0000(),castOther.getA0000()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA0000()).toHashCode();
    }

	public java.lang.String getA3108() {
		return a3108;
	}

	public void setA3108(java.lang.String a3108) {
		this.a3108 = a3108;
	}

	public java.lang.String getA3109() {
		return a3109;
	}

	public void setA3109(java.lang.String a3109) {
		this.a3109 = a3109;
	}

	public java.lang.String getA3110() {
		return a3110;
	}

	public void setA3110(java.lang.String a3110) {
		this.a3110 = a3110;
	}

	



}
