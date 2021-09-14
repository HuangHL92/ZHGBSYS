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
public class A64 implements Serializable {


    public A64() {
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
     * 报考准考证号
     */
    private java.lang.String a6401;

    public void setA6401(final java.lang.String a6401) {
        this.a6401 = a6401;
    }

    public java.lang.String getA6401() {
        return this.a6401;
    }


    /**
     * 行政职业能力分数
     */
    private java.lang.String a6402;

    public void setA6402(final java.lang.String a6402) {
        this.a6402 = a6402;
    }

    public java.lang.String getA6402() {
        return this.a6402;
    }


    /**
     * 申论分数
     */
    private java.lang.String a6403;

    public void setA6403(final java.lang.String a6403) {
        this.a6403 = a6403;
    }

    public java.lang.String getA6403() {
        return this.a6403;
    }


    /**
     * 其他科目分数
     */
    private java.lang.String a6404;

    public void setA6404(final java.lang.String a6404) {
        this.a6404 = a6404;
    }

    public java.lang.String getA6404() {
        return this.a6404;
    }


    /**
     * 专业能力测试分数
     */
    private java.lang.String a6405;

    public void setA6405(final java.lang.String a6405) {
        this.a6405 = a6405;
    }

    public java.lang.String getA6405() {
        return this.a6405;
    }


    /**
     * 公共科目笔试成绩总分
     */
    private java.lang.String a6406;

    public void setA6406(final java.lang.String a6406) {
        this.a6406 = a6406;
    }

    public java.lang.String getA6406() {
        return this.a6406;
    }


    /**
     * 专业考试成绩
     */
    private java.lang.String a6407;

    public void setA6407(final java.lang.String a6407) {
        this.a6407 = a6407;
    }

    public java.lang.String getA6407() {
        return this.a6407;
    }


    /**
     * 面试成绩
     */
    private java.lang.String a6408;

    public void setA6408(final java.lang.String a6408) {
        this.a6408 = a6408;
    }

    public java.lang.String getA6408() {
        return this.a6408;
    }

    /**
     * 考试标识符
     */
    private java.lang.String a6400;

    public void setA6400(final java.lang.String a6400) {
        this.a6400 = a6400;
    }

    public java.lang.String getA6400() {
        return this.a6400;
    }

    /**
     * 考试人员类别
     */
    private java.lang.String a64type;

    public void seta64type(final java.lang.String a64type) {
        this.a64type = a64type;
    }

    public java.lang.String geta64type() {
        return this.a64type;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("A6400", getA6400()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A64)) {
            return false;
        }
        A64 castOther = (A64) other;
        return new EqualsBuilder().append(this.getA6400(),castOther.getA6400()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA6400()).toHashCode();
    }



}
