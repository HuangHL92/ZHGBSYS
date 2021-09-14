package com.insigma.siis.local.business.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @author 徐亚涛(xuyatao @ 126.com)
 * @Oracle工具生成实体
 * @copytright xuyatao 2010-2015
 */
public class A53 implements Serializable {

    private BigDecimal rbSysno;

    public BigDecimal getRbSysno() {
        return rbSysno;
    }

    public void setRbSysno(BigDecimal rbSysno) {
        this.rbSysno = rbSysno;
    }

    public A53() {
    }

    private String rbId;

    public String getRbId() {
        return rbId;
    }

    public void setRbId(String rbId) {
        this.rbId = rbId;
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
     * 主键
     */
    private java.lang.String a5300;

    public void setA5300(final java.lang.String a5300) {
        this.a5300 = a5300;
    }

    public java.lang.String getA5300() {
        return this.a5300;
    }


    /**
     * 拟任职务
     */
    private java.lang.String a5304;

    public void setA5304(final java.lang.String a5304) {
        this.a5304 = a5304;
    }

    public java.lang.String getA5304() {
        return this.a5304;
    }


    /**
     * 拟免职务
     */
    private java.lang.String a5315;

    public void setA5315(final java.lang.String a5315) {
        this.a5315 = a5315;
    }

    public java.lang.String getA5315() {
        return this.a5315;
    }


    /**
     * 任免理由
     */
    private java.lang.String a5317;

    public void setA5317(final java.lang.String a5317) {
        this.a5317 = a5317;
    }

    public java.lang.String getA5317() {
        return this.a5317;
    }


    /**
     * 呈报单位
     */
    private java.lang.String a5319;

    public void setA5319(final java.lang.String a5319) {
        this.a5319 = a5319;
    }

    public java.lang.String getA5319() {
        return this.a5319;
    }


    /**
     * 计算年龄时间
     */
    private java.lang.String a5321;

    public void setA5321(final java.lang.String a5321) {
        this.a5321 = a5321;
    }

    public java.lang.String getA5321() {
        return this.a5321;
    }


    /**
     * 填表时间
     */
    private java.lang.String a5323;

    public void setA5323(final java.lang.String a5323) {
        this.a5323 = a5323;
    }

    public java.lang.String getA5323() {
        return this.a5323;
    }


    /**
     * 填表人
     */
    private java.lang.String a5327;

    public void setA5327(final java.lang.String a5327) {
        this.a5327 = a5327;
    }

    public java.lang.String getA5327() {
        return this.a5327;
    }


    /**
     * 用户ID
     */
    private java.lang.String a5399;

    public void setA5399(final java.lang.String a5399) {
        this.a5399 = a5399;
    }

    public java.lang.String getA5399() {
        return this.a5399;
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
        return new ToStringBuilder(this).append("A5300", getA5300()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A53)) {
            return false;
        }
        A53 castOther = (A53) other;
        return new EqualsBuilder().append(this.getA5300(), castOther.getA5300()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA5300()).toHashCode();
    }

    /**
     * 拟任职务层次
     */
    private String a5366;
    /**
     * 是否领导职务
     */
    private String a5367;
    /**
     * 拟任单位
     */
    private String a5368;
    /**
     * 拟免职务id
     */
    private String a5369;
    /**
     * 拟任状态
     */
    private String a5365;

    //-------------------------------------- set and get --------------------------------------//

    public String getA5366() {
        return a5366;
    }

    public void setA5366(String a5366) {
        this.a5366 = a5366;
    }

    public String getA5367() {
        return a5367;
    }

    public void setA5367(String a5367) {
        this.a5367 = a5367;
    }

    public String getA5368() {
        return a5368;
    }

    public void setA5368(String a5368) {
        this.a5368 = a5368;
    }

    public String getA5369() {
        return a5369;
    }

    public void setA5369(String a5369) {
        this.a5369 = a5369;
    }

    public String getA5365() {
        return a5365;
    }

    public void setA5365(String a5365) {
        this.a5365 = a5365;
    }
}