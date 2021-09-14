package com.insigma.siis.local.business.entity;

import java.util.Date;

public class JwBatch {
	private String id;
	private String creator;
	private String createtime;
	private String creatordept;
	private String batchno;
	private String deleteflag;
	private String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreatordept() {
		return creatordept;
	}
	public void setCreatordept(String creatordept) {
		this.creatordept = creatordept;
	}

	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

		
	
}
