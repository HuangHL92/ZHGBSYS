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
public class A61 implements Serializable {


    public A61() {
    }


    /**
     * 人员唯一标志符
     */
    private java.lang.String a0000;

    public void setA0000(final java.lang.String a0000) {
        this.a0000 = a0000;
    }

    public java.lang.String getA0000() {
        return this.a0000;
    }


    /**
     * 选调生      ZB137《选调生类别代码》
     */
    private java.lang.String a2970;

    public void setA2970(final java.lang.String a2970) {
        this.a2970 = a2970;
    }

    public java.lang.String getA2970() {
        return this.a2970;
    }


    /**
     * 来源                    ZB138《选调生来源类别代码》
     */
    private java.lang.String a2970a;

    public void setA2970a(final java.lang.String a2970a) {
        this.a2970a = a2970a;
    }

    public java.lang.String getA2970a() {
        return this.a2970a;
    }


    /**
     * 初始工作单位
     */
    private java.lang.String a2970b;

    public void setA2970b(final java.lang.String a2970b) {
        this.a2970b = a2970b;
    }

    public java.lang.String getA2970b() {
        return this.a2970b;
    }


    /**
     * 在基层乡镇机关工作时间
     */
    private java.lang.String a2970c;

    public void setA2970c(final java.lang.String a2970c) {
        this.a2970c = a2970c;
    }

    public java.lang.String getA2970c() {
        return this.a2970c;
    }


    /**
     * 进入选调生时间
     */
    private java.lang.String a6104;

    public void setA6104(final java.lang.String a6104) {
        this.a6104 = a6104;
    }

    public java.lang.String getA6104() {
        return this.a6104;
    }


    /**
     * 选调生政治面貌
     */
    private java.lang.String a6107;

    public void setA6107(final java.lang.String a6107) {
        this.a6107 = a6107;
    }

    public java.lang.String getA6107() {
        return this.a6107;
    }


    /**
     * 选调时学历名称
     */
    private java.lang.String a6108;

    public void setA6108(final java.lang.String a6108) {
        this.a6108 = a6108;
    }

    public java.lang.String getA6108() {
        return this.a6108;
    }


    /**
     * 选调时学历代码
     */
    private java.lang.String a6109;

    public void setA6109(final java.lang.String a6109) {
        this.a6109 = a6109;
    }

    public java.lang.String getA6109() {
        return this.a6109;
    }


    /**
     * 选调时学位名称     ZB64《学历代码》
     */
    private java.lang.String a6110;

    public void setA6110(final java.lang.String a6110) {
        this.a6110 = a6110;
    }

    public java.lang.String getA6110() {
        return this.a6110;
    }


    /**
     * 选调时学位代码
     */
    private java.lang.String a6111;

    public void setA6111(final java.lang.String a6111) {
        this.a6111 = a6111;
    }

    public java.lang.String getA6111() {
        return this.a6111;
    }


    /**
     * 是否退役大学生士兵      GB/T6864《中华人民公共和国学位代码》
     */
    private java.lang.String a6112;

    public void setA6112(final java.lang.String a6112) {
        this.a6112 = a6112;
    }

    public java.lang.String getA6112() {
        return this.a6112;
    }


    /**
     * 是否有海外留学经历
     */
    private java.lang.String a6113;

    public void setA6113(final java.lang.String a6113) {
        this.a6113 = a6113;
    }

    public java.lang.String getA6113() {
        return this.a6113;
    }


    /**
     * 留学年限
     */
    private java.lang.String a6114;

    public void setA6114(final java.lang.String a6114) {
        this.a6114 = a6114;
    }

    public java.lang.String getA6114() {
        return this.a6114;
    }


    /**
     * 是否有海外工作经历
     */
    private java.lang.String a6115;

    public void setA6115(final java.lang.String a6115) {
        this.a6115 = a6115;
    }

    public java.lang.String getA6115() {
        return this.a6115;
    }


    /**
     * 海外工作年限
     */
    private java.lang.String a6116;

    public void setA6116(final java.lang.String a6116) {
        this.a6116 = a6116;
    }

    public java.lang.String getA6116() {
        return this.a6116;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("A0000", getA0000()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A61)) {
            return false;
        }
        A61 castOther = (A61) other;
        return new EqualsBuilder().append(this.getA0000(),castOther.getA0000()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA0000()).toHashCode();
    }



}
