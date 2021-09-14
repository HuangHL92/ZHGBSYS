package com.insigma.siis.local.business.entity;

import java.util.Date;

public class WebOffice {
	
	private String id;//主键
	private Date creattime;//创建时间
	private Date updatetime;//修改时间
	private String filename;//文件名称
	private String realpath;//文件路径
	private String type;//文件标识 1：单位名册、2：基本名册、3、word 4、Excel表格
	private String nametype;//模板信息集
	private String selty;//是否通用1、通用2、不通用
	
	public String getNametype() {
		return nametype;
	}
	public void setNametype(String nametype) {
		this.nametype = nametype;
	}
	public String getSelty() {
		return selty;
	}
	public void setSelty(String selty) {
		this.selty = selty;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreattime() {
		return creattime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getRealpath() {
		return realpath;
	}
	
	public void setRealpath(String realpath) {
		this.realpath = realpath;
	}
	public WebOffice(String id, Date creattime, Date updatetime, String filename, String realpath,String type) {
		this.id = id;
		this.creattime = creattime;
		this.updatetime = updatetime;
		this.filename = filename;
		this.realpath = realpath;
		this.type = type;
	}
	public WebOffice() {
		
	}
	
}
