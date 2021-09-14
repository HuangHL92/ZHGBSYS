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
public class A63 implements Serializable {


    public A63() {
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
     * 公开选调
     */
    private java.lang.String a2951;

    public void setA2951(final java.lang.String a2951) {
        this.a2951 = a2951;
    }

    public java.lang.String getA2951() {
        return this.a2951;
    }


    /**
     * 选调类别                    ZB142《遴选或选调类别》
     */
    private java.lang.String a6302;

    public void setA6302(final java.lang.String a6302) {
        this.a6302 = a6302;
    }

    public java.lang.String getA6302() {
        return this.a6302;
    }


    /**
     * 原单位类别               ZB144《原单位类别》
     */
    private java.lang.String a6303;

    public void setA6303(final java.lang.String a6303) {
        this.a6303 = a6303;
    }

    public java.lang.String getA6303() {
        return this.a6303;
    }


    /**
     * 原单位层级               ZB141《工作单位层次代码》
     */
    private java.lang.String a6304;

    public void setA6304(final java.lang.String a6304) {
        this.a6304 = a6304;
    }

    public java.lang.String getA6304() {
        return this.a6304;
    }


    /**
     * 原单位职称或职务ZB145《职称代码》
     */
    private java.lang.String a6305;

    public void setA6305(final java.lang.String a6305) {
        this.a6305 = a6305;
    }

    public java.lang.String getA6305() {
        return this.a6305;
    }


    /**
     * 公开选调时间
     */
    private java.lang.String a6306;

    public void setA6306(final java.lang.String a6306) {
        this.a6306 = a6306;
    }

    public java.lang.String getA6306() {
        return this.a6306;
    }


    /**
     * 是否有海外留学经历
     */
    private java.lang.String a6307;

    public void setA6307(final java.lang.String a6307) {
        this.a6307 = a6307;
    }

    public java.lang.String getA6307() {
        return this.a6307;
    }


    /**
     * 留学年限
     */
    private java.lang.String a6308;

    public void setA6308(final java.lang.String a6308) {
        this.a6308 = a6308;
    }

    public java.lang.String getA6308() {
        return this.a6308;
    }


    /**
     * 是否有海外工作经历
     */
    private java.lang.String a6309;

    public void setA6309(final java.lang.String a6309) {
        this.a6309 = a6309;
    }

    public java.lang.String getA6309() {
        return this.a6309;
    }


    /**
     * 海外工作年限
     */
    private java.lang.String a6310;

    public void setA6310(final java.lang.String a6310) {
        this.a6310 = a6310;
    }

    public java.lang.String getA6310() {
        return this.a6310;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("A0000", getA0000()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A63)) {
            return false;
        }
        A63 castOther = (A63) other;
        return new EqualsBuilder().append(this.getA0000(),castOther.getA0000()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA0000()).toHashCode();
    }



}
