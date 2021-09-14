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
public class A57history implements Serializable {


    public A57history() {
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
     * 主键
     */
    private java.lang.String a57h00;

    public void setA57h00(final java.lang.String a57h00) {
        this.a57h00 = a57h00;
    }

    public java.lang.String getA57h00() {
        return this.a57h00;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this).append("A0000", getA0000()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A57history)) {
            return false;
        }
        A57history castOther = (A57history) other;
        return new EqualsBuilder().append(this.getA0000(),castOther.getA0000()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA0000()).toHashCode();
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
    private java.lang.String photostype;

    public void setPhotostype(final java.lang.String photostype) {
        this.photostype = photostype;
    }

    public java.lang.String getPhotostype() {
        return this.photostype;
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
	
	/**
	 * 照片的替换时间
	 */
	private java.lang.String photodate;

	public java.lang.String getPhotodate() {
		return photodate;
	}

	public void setPhotodate(java.lang.String photodate) {
		this.photodate = photodate;
	}
	
	/**
	 * 照片的使用时间
	 */
	private java.lang.String photousedate;
	
	public java.lang.String getPhotousedate() {
		return photousedate;
	}
	
	public void setPhotousedate(java.lang.String photousedate){
		this.photousedate=photousedate;
	}
}
