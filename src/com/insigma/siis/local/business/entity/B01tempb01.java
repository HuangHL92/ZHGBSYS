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
public class B01tempb01 implements Serializable {


    public B01tempb01() {
    }


    /**
     * 导入记录id
     */
    private java.lang.String imprecordid;

    public void setImprecordid(final java.lang.String imprecordid) {
        this.imprecordid = imprecordid;
    }

    public java.lang.String getImprecordid() {
        return this.imprecordid;
    }


    /**
     * 新生成对照id或temp.b0114（临时表b0114不为空）
     */
    private java.lang.String newb0111;

    public void setNewb0111(final java.lang.String newb0111) {
        this.newb0111 = newb0111;
    }

    public java.lang.String getNewb0111() {
        return this.newb0111;
    }


    /**
     * 倒数数据id
     */
    private java.lang.String tempb0111;

    public void setTempb0111(final java.lang.String tempb0111) {
        this.tempb0111 = tempb0111;
    }

    public java.lang.String getTempb0111() {
        return this.tempb0111;
    }


    /**
     * id
     */
    private java.lang.String temprid;

    public void setTemprid(final java.lang.String temprid) {
        this.temprid = temprid;
    }

    public java.lang.String getTemprid() {
        return this.temprid;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Temprid", getTemprid()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof B01tempb01)) {
            return false;
        }
        B01tempb01 castOther = (B01tempb01) other;
        return new EqualsBuilder().append(this.getTemprid(),castOther.getTemprid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getTemprid()).toHashCode();
    }



}
