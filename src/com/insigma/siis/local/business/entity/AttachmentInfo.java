package com.insigma.siis.local.business.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.insigma.siis.local.pagemodel.publicServantManage.Data2Long;


/**
 * @Oracle工具生成实体
 * @author 徐亚涛(xuyatao@126.com)
 * @copytright xuyatao 2010-2015
 */
public class AttachmentInfo implements Serializable {


    public AttachmentInfo() {
    }

    private java.lang.String id;
    
    private java.lang.String personid;
    private java.lang.String personname;
    private java.lang.String uploaddate;
    private java.lang.String filepath;
    
    private java.lang.String filetype;

    private java.lang.String filename;

    private java.lang.String userid;

    private java.lang.String username;

    private java.lang.String attribute1;

    private java.lang.String attribute2;

    private java.lang.String attribute3;

    private java.lang.String attribute4;

    private java.lang.String objectid;
    
    private java.lang.String idcard;
    private java.lang.String beizhu;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getPersonid() {
		return personid;
	}

	public void setPersonid(java.lang.String personid) {
		this.personid = personid;
	}

	public java.lang.String getPersonname() {
		return personname;
	}

	public void setPersonname(java.lang.String personname) {
		this.personname = personname;
	}

	public java.lang.String getUploaddate() {
		return uploaddate;
	}

	public void setUploaddate(java.lang.String uploaddate) {
		this.uploaddate = uploaddate;
	}

	public java.lang.String getFilepath() {
		return filepath;
	}

	public void setFilepath(java.lang.String filepath) {
		this.filepath = filepath;
	}

	public java.lang.String getFiletype() {
		return filetype;
	}

	public void setFiletype(java.lang.String filetype) {
		this.filetype = filetype;
	}

	public java.lang.String getFilename() {
		return filename;
	}

	public void setFilename(java.lang.String filename) {
		this.filename = filename;
	}

	public java.lang.String getUserid() {
		return userid;
	}

	public void setUserid(java.lang.String userid) {
		this.userid = userid;
	}

	public java.lang.String getUsername() {
		return username;
	}

	public void setUsername(java.lang.String username) {
		this.username = username;
	}

	public java.lang.String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(java.lang.String attribute1) {
		this.attribute1 = attribute1;
	}

	public java.lang.String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(java.lang.String attribute2) {
		this.attribute2 = attribute2;
	}

	public java.lang.String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(java.lang.String attribute3) {
		this.attribute3 = attribute3;
	}

	public java.lang.String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(java.lang.String attribute4) {
		this.attribute4 = attribute4;
	}

	public java.lang.String getObjectid() {
		return objectid;
	}

	public void setObjectid(java.lang.String objectid) {
		this.objectid = objectid;
	}

	public java.lang.String getIdcard() {
		return idcard;
	}

	public void setIdcard(java.lang.String idcard) {
		this.idcard = idcard;
	}

	public java.lang.String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(java.lang.String beizhu) {
		this.beizhu = beizhu;
	}

}
