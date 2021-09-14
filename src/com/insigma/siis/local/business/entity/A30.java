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
public class A30 implements Serializable {


    public A30() {
    }
    private String a3954a;
    private String a3954b;
    private String a3038;
    

    public String getA3038() {
		return a3038;
	}

	public void setA3038(String a3038) {
		this.a3038 = a3038;
	}

	public String getA3954a() {
		return a3954a;
	}

	public void setA3954a(String a3954a) {
		this.a3954a = a3954a;
	}

	public String getA3954b() {
		return a3954b;
	}

	public void setA3954b(String a3954b) {
		this.a3954b = a3954b;
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
     * 退出管理方式
     */
    private java.lang.String a3001;

    public void setA3001(final java.lang.String a3001) {
        this.a3001 = a3001;
    }

    public java.lang.String getA3001() {
        return this.a3001;
    }


    /**
     * 日期
     */
    private java.lang.String a3004;

    public void setA3004(final java.lang.String a3004) {
        this.a3004 = a3004;
    }

    public java.lang.String getA3004() {
        return this.a3004;
    }


    /**
     * 调往单位
     */
    private java.lang.String a3007a;

    public void setA3007a(final java.lang.String a3007a) {
        this.a3007a = a3007a;
    }

    public java.lang.String getA3007a() {
        return this.a3007a;
    }


    /**
     * 备注
     */
    private java.lang.String a3034;

    public void setA3034(final java.lang.String a3034) {
        this.a3034 = a3034;
    }

    public java.lang.String getA3034() {
        return this.a3034;
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
        if (!(other instanceof A30)) {
            return false;
        }
        A30 castOther = (A30) other;
        return new EqualsBuilder().append(this.getA0000(),castOther.getA0000()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA0000()).toHashCode();
    }



}
