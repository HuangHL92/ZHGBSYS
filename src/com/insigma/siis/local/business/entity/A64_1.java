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
public class A64_1 implements Serializable {


    public A64_1() {
    }


    /**
     * 人员唯一标识符
     */
    private java.lang.String a0000_1;

    public void setA0000_1(final java.lang.String a0000_1) {
        this.a0000_1 = a0000_1;
    }

    public java.lang.String getA0000_1() {
        return this.a0000_1;
    }


    /**
     * 报考准考证号
     */
    private java.lang.String a6401_1;

    public void setA6401_1(final java.lang.String a6401_1) {
        this.a6401_1 = a6401_1;
    }

    public java.lang.String getA6401_1() {
        return this.a6401_1;
    }


    /**
     * 行政职业能力分数
     */
    private java.lang.String a6402_1;

    public void setA6402_1(final java.lang.String a6402_1) {
        this.a6402_1 = a6402_1;
    }

    public java.lang.String getA6402_1() {
        return this.a6402_1;
    }


    /**
     * 申论分数
     */
    private java.lang.String a6403_1;

    public void setA6403_1(final java.lang.String a6403_1) {
        this.a6403_1 = a6403_1;
    }

    public java.lang.String getA6403_1() {
        return this.a6403_1;
    }


    /**
     * 其他科目分数
     */
    private java.lang.String a6404_1;

    public void setA6404_1(final java.lang.String a6404_1) {
        this.a6404_1 = a6404_1;
    }

    public java.lang.String getA6404_1() {
        return this.a6404_1;
    }


    /**
     * 专业能力测试分数
     */
    private java.lang.String a6405_1;

    public void setA6405_1(final java.lang.String a6405_1) {
        this.a6405_1 = a6405_1;
    }

    public java.lang.String getA6405_1() {
        return this.a6405_1;
    }


    /**
     * 公共科目笔试成绩总分
     */
    private java.lang.String a6406_1;

    public void setA6406_1(final java.lang.String a6406_1) {
        this.a6406_1 = a6406_1;
    }

    public java.lang.String getA6406_1() {
        return this.a6406_1;
    }


    /**
     * 专业考试成绩
     */
    private java.lang.String a6407_1;

    public void setA6407_1(final java.lang.String a6407_1) {
        this.a6407_1 = a6407_1;
    }

    public java.lang.String getA6407_1() {
        return this.a6407_1;
    }


    /**
     * 面试成绩
     */
    private java.lang.String a6408_1;

    public void setA6408_1(final java.lang.String a6408_1) {
        this.a6408_1 = a6408_1;
    }

    public java.lang.String getA6408_1() {
        return this.a6408_1;
    }

    /**
     * 考试标识符
     */
    private java.lang.String a6400_1;

    public void setA6400_1(final java.lang.String a6400_1) {
        this.a6400_1 = a6400_1;
    }

    public java.lang.String getA6400_1() {
        return this.a6400_1;
    }

    /**
     * 考试人员类别
     */
    private java.lang.String a64type_1;

    public void seta64type_1(final java.lang.String a64type_1) {
        this.a64type_1 = a64type_1;
    }

    public java.lang.String geta64type_1() {
        return this.a64type_1;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("A6400_1", getA6400_1()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A64_1)) {
            return false;
        }
        A64_1 castOther = (A64_1) other;
        return new EqualsBuilder().append(this.getA6400_1(),castOther.getA6400_1()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA6400_1()).toHashCode();
    }



}
