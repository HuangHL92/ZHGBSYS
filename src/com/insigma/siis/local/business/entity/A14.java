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
public class A14 implements Serializable {


    public A14() {
    }


    /**
     * 人员唯一标识符
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
    private java.lang.String a1400;

    public void setA1400(final java.lang.String a1400) {
        this.a1400 = a1400;
    }

    public java.lang.String getA1400() {
        return this.a1400;
    }


    /**
     * 奖惩名称
     */
    private java.lang.String a1404a;

    public void setA1404a(final java.lang.String a1404a) {
        this.a1404a = a1404a;
    }

    public java.lang.String getA1404a() {
        return this.a1404a;
    }


    /**
     * 奖惩代码
     */
    private java.lang.String a1404b;

    public void setA1404b(final java.lang.String a1404b) {
        this.a1404b = a1404b;
    }

    public java.lang.String getA1404b() {
        return this.a1404b;
    }


    /**
     * 奖惩批准时间
     */
    private java.lang.String a1407;

    public void setA1407(final java.lang.String a1407) {
        this.a1407 = a1407;
    }

    public java.lang.String getA1407() {
        return this.a1407;
    }


    /**
     * 批准机关
     */
    private java.lang.String a1411a;

    public void setA1411a(final java.lang.String a1411a) {
        this.a1411a = a1411a;
    }

    public java.lang.String getA1411a() {
        return this.a1411a;
    }


    /**
     * 批准机关级别
     */
    private java.lang.String a1414;

    public void setA1414(final java.lang.String a1414) {
        this.a1414 = a1414;
    }

    public java.lang.String getA1414() {
        return this.a1414;
    }


    /**
     * 奖惩时的职务层次
     */
    private java.lang.String a1415;

    public void setA1415(final java.lang.String a1415) {
        this.a1415 = a1415;
    }

    public java.lang.String getA1415() {
        return this.a1415;
    }


    /**
     * 奖惩撤销时间
     */
    private java.lang.String a1424;

    public void setA1424(final java.lang.String a1424) {
        this.a1424 = a1424;
    }

    public java.lang.String getA1424() {
        return this.a1424;
    }


    /**
     * 批准机关性质
     */
    private java.lang.String a1428;

    public void setA1428(final java.lang.String a1428) {
        this.a1428 = a1428;
    }

    public java.lang.String getA1428() {
        return this.a1428;
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
    
    /*
     * 影响期
     */
    private java.lang.String a1423;

    public void setA1423(final java.lang.String a1423) {
        this.a1423 = a1423;
    }

    public java.lang.String getA1423() {
        return this.a1423;
    }
    
    /*
     * 备注
     */
    private java.lang.String a1499;
    
    public java.lang.String getA1499() {
		return a1499;
	}

	public void setA1499(java.lang.String a1499) {
		this.a1499 = a1499;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this).append("A1400", getA1400()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A14)) {
            return false;
        }
        A14 castOther = (A14) other;
        return new EqualsBuilder().append(this.getA1400(),castOther.getA1400()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA1400()).toHashCode();
    }



}
