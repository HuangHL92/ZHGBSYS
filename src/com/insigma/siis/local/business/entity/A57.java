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
public class A57 implements Serializable {


    public A57() {
    }
    private java.lang.String photopath;

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
     * 照片
     */
    private java.lang.String a5714;

    public void setA5714(final java.lang.String a5714) {
        this.a5714 = a5714;
    }

    public java.lang.String getA5714() {
        return this.a5714;
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
        return new ToStringBuilder(this).append("A0000", getA0000()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A57)) {
            return false;
        }
        A57 castOther = (A57) other;
        return new EqualsBuilder().append(this.getA0000(),castOther.getA0000()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA0000()).toHashCode();
    }

    /**
     * 照片数据
     */
    private java.sql.Blob photodata;

    public void setPhotodata(final java.sql.Blob photodata) {
        this.photodata = photodata;
    }

    public java.sql.Blob getPhotodata() {
        return this.photodata;
    }


    /**
     * 照片名称
     */
    private java.lang.String photoname;

    public void setPhotoname(final java.lang.String photoname) {
        this.photoname = photoname;
    }

    public java.lang.String getPhotoname() {
        return this.photoname;
    }


    /**
     * 照片类型
     */
    private java.lang.String photstype;

    public void setPhotstype(final java.lang.String photstype) {
        this.photstype = photstype;
    }

    public java.lang.String getPhotstype() {
        return this.photstype;
    }

	public java.lang.String getPhotopath() {
		return photopath;
	}

	public void setPhotopath(java.lang.String photopath) {
		this.photopath = photopath;
	}


	/**
	 * 照片状态
	 */
	private java.lang.String picstatus;

	public java.lang.String getPicstatus() {
		return picstatus;
	}

	public void setPicstatus(java.lang.String picstatus) {
		this.picstatus = picstatus;
	}
	
}
