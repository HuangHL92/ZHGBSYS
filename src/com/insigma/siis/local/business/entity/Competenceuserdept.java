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
public class Competenceuserdept implements Serializable {


    public Competenceuserdept() {
    }


    /**
     * 机构编码
     */
    private java.lang.String b0111;

    public void setB0111(final java.lang.String b0111) {
        this.b0111 = b0111;
    }

    public java.lang.String getB0111() {
        return this.b0111;
    }


    /**
     * 权限类型(0：浏览，1：维护)
     */
    private java.lang.String type;

    public void setType(final java.lang.String type) {
        this.type = type;
    }

    public java.lang.String getType() {
        return this.type;
    }


    /**
     * 用户机构关联编码
     */
    private java.lang.String userdeptid;

    public void setUserdeptid(final java.lang.String userdeptid) {
        this.userdeptid = userdeptid;
    }

    public java.lang.String getUserdeptid() {
        return this.userdeptid;
    }


    /**
     * 用户编码
     */
    private java.lang.String userid;

    public void setUserid(final java.lang.String userid) {
        this.userid = userid;
    }

    public java.lang.String getUserid() {
        return this.userid;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Userdeptid", getUserdeptid()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Competenceuserdept)) {
            return false;
        }
        Competenceuserdept castOther = (Competenceuserdept) other;
        return new EqualsBuilder().append(this.getUserdeptid(),castOther.getUserdeptid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getUserdeptid()).toHashCode();
    }



}
