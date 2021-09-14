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
public class A37 implements Serializable {


    public A37() {
    }


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
     * 办公地址
     */
    private java.lang.String a3701;

    public void setA3701(final java.lang.String a3701) {
        this.a3701 = a3701;
    }

    public java.lang.String getA3701() {
        return this.a3701;
    }


    /**
     * 办公电话
     */
    private java.lang.String a3707a;

    public void setA3707a(final java.lang.String a3707a) {
        this.a3707a = a3707a;
    }

    public java.lang.String getA3707a() {
        return this.a3707a;
    }


    /**
     * 住宅电话
     */
    private java.lang.String a3707b;

    public void setA3707b(final java.lang.String a3707b) {
        this.a3707b = a3707b;
    }

    public java.lang.String getA3707b() {
        return this.a3707b;
    }


    /**
     * 移动电话
     */
    private java.lang.String a3707c;

    public void setA3707c(final java.lang.String a3707c) {
        this.a3707c = a3707c;
    }

    public java.lang.String getA3707c() {
        return this.a3707c;
    }


    /**
     * 秘书电话
     */
    private java.lang.String a3707e;

    public void setA3707e(final java.lang.String a3707e) {
        this.a3707e = a3707e;
    }

    public java.lang.String getA3707e() {
        return this.a3707e;
    }


    /**
     * 电子邮箱
     */
    private java.lang.String a3708;

    public void setA3708(final java.lang.String a3708) {
        this.a3708 = a3708;
    }

    public java.lang.String getA3708() {
        return this.a3708;
    }


    /**
     * 家庭住址
     */
    private java.lang.String a3711;

    public void setA3711(final java.lang.String a3711) {
        this.a3711 = a3711;
    }

    public java.lang.String getA3711() {
        return this.a3711;
    }


    /**
     * 住址邮编
     */
    private java.lang.String a3714;

    public void setA3714(final java.lang.String a3714) {
        this.a3714 = a3714;
    }

    public java.lang.String getA3714() {
        return this.a3714;
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
    private String a3721;
    

    public String getA3721() {
		return a3721;
	}

	public void setA3721(String a3721) {
		this.a3721 = a3721;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this).append("A0000", getA0000()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A37)) {
            return false;
        }
        A37 castOther = (A37) other;
        return new EqualsBuilder().append(this.getA0000(),castOther.getA0000()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA0000()).toHashCode();
    }



}
