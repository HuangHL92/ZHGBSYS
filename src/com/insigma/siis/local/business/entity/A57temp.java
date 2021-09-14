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
public class A57temp implements Serializable {


    public A57temp() {
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
     * 
     */
    private java.lang.String a0000;

    public void setA0000(final java.lang.String a0000) {
        this.a0000 = a0000;
    }

    public java.lang.String getA0000() {
        return this.a0000;
    }


    /**
     * 
     */
    private java.lang.String a5714;

    public void setA5714(final java.lang.String a5714) {
        this.a5714 = a5714;
    }

    public java.lang.String getA5714() {
        return this.a5714;
    }


    /**
     * 错误信息
     */
    private java.lang.String errorinfo;

    public void setErrorinfo(final java.lang.String errorinfo) {
        this.errorinfo = errorinfo;
    }

    public java.lang.String getErrorinfo() {
        return this.errorinfo;
    }


    /**
     * 是否合格；0合格、1不合格
     */
    private java.lang.String isqualified;

    public void setIsqualified(final java.lang.String isqualified) {
        this.isqualified = isqualified;
    }

    public java.lang.String getIsqualified() {
        return this.isqualified;
    }


    /**
     * 
     */
    private java.lang.String updated;

    public void setUpdated(final java.lang.String updated) {
        this.updated = updated;
    }

    public java.lang.String getUpdated() {
        return this.updated;
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

    public java.lang.String getPhotopath() {
		return photopath;
	}

	public void setPhotopath(java.lang.String photopath) {
		this.photopath = photopath;
	}

	public java.lang.String getPhotstype() {
        return this.photstype;
    }
    /**
     * 照片名称
     */
    private java.lang.String photopath;

}
