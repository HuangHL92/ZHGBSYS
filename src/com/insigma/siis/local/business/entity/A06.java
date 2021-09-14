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
public class A06 implements Serializable {


    public A06() {
    }
    private java.lang.String a0699;

    public java.lang.String getA0699() {
		return a0699;
	}

	public void setA0699(java.lang.String a0699) {
		this.a0699 = a0699;
	}
	/**
     * 外键（人员统一标识符）
     */
    private java.lang.String a0000;

    public void setA0000(final java.lang.String a0000) {
        this.a0000 = a0000;
    }

    public java.lang.String getA0000() {
        return this.a0000;
    }


    /**
     * 主键
     */
    private java.lang.String a0600;

    public void setA0600(final java.lang.String a0600) {
        this.a0600 = a0600;
    }

    public java.lang.String getA0600() {
        return this.a0600;
    }


    /**
     * 专业技术职务资格代码
     */
    private java.lang.String a0601;

    public void setA0601(final java.lang.String a0601) {
        this.a0601 = a0601;
    }

    public java.lang.String getA0601() {
        return this.a0601;
    }


    /**
     * 专业技术职务资格汉字
     */
    private java.lang.String a0602;

    public void setA0602(final java.lang.String a0602) {
        this.a0602 = a0602;
    }

    public java.lang.String getA0602() {
        return this.a0602;
    }


    /**
     * 获得资格日期
     */
    private java.lang.String a0604;

    public void setA0604(final java.lang.String a0604) {
        this.a0604 = a0604;
    }

    public java.lang.String getA0604() {
        return this.a0604;
    }


    /**
     * 取得资格途径
     */
    private java.lang.String a0607;

    public void setA0607(final java.lang.String a0607) {
        this.a0607 = a0607;
    }

    public java.lang.String getA0607() {
        return this.a0607;
    }


    /**
     * 评委会或考试名称
     */
    private java.lang.String a0611;

    public void setA0611(final java.lang.String a0611) {
        this.a0611 = a0611;
    }

    public java.lang.String getA0611() {
        return this.a0611;
    }


    /**
     * 当前专业技术任职资格标识
     */
    private java.lang.String a0614;

    public void setA0614(final java.lang.String a0614) {
        this.a0614 = a0614;
    }

    public java.lang.String getA0614() {
        return this.a0614;
    }


    /**
     * 排序字段
     */
    private java.lang.Long sortid;

    public void setSortid(final java.lang.Long sortid) {
        this.sortid = sortid;
    }

    public java.lang.Long getSortid() {
        return this.sortid;
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
        return new ToStringBuilder(this).append("A0600", getA0600()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A06)) {
            return false;
        }
        A06 castOther = (A06) other;
        return new EqualsBuilder().append(this.getA0600(),castOther.getA0600()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA0600()).toHashCode();
    }



}
