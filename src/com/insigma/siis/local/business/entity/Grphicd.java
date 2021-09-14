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
public class Grphicd implements Serializable {


    public Grphicd() {
    }


    /**
     * 条件
     */
    private java.lang.String condition;

    public void setCondition(final java.lang.String condition) {
        this.condition = condition;
    }

    public java.lang.String getCondition() {
        return this.condition;
    }


    /**
     * code_table_col主键
     */
    private java.lang.String ctci;

    public void setCtci(final java.lang.String ctci) {
        this.ctci = ctci;
    }

    public java.lang.String getCtci() {
        return this.ctci;
    }


    /**
     * 主键
     */
    private java.lang.String ghc001;

    public void setGhc001(final java.lang.String ghc001) {
        this.ghc001 = ghc001;
    }

    public java.lang.String getGhc001() {
        return this.ghc001;
    }


    /**
     * 排序
     */
    private java.lang.String ordernum;

    public void setOrdernum(final java.lang.String ordernum) {
        this.ordernum = ordernum;
    }

    public java.lang.String getOrdernum() {
        return this.ordernum;
    }


    /**
     * 说明
     */
    private java.lang.String translation;

    public void setTranslation(final java.lang.String translation) {
        this.translation = translation;
    }

    public java.lang.String getTranslation() {
        return this.translation;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Ghc001", getGhc001()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Grphicd)) {
            return false;
        }
        Grphicd castOther = (Grphicd) other;
        return new EqualsBuilder().append(this.getGhc001(),castOther.getGhc001()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getGhc001()).toHashCode();
    }



}
