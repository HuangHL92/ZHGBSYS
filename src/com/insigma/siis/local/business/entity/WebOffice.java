package com.insigma.siis.local.business.entity;

import java.util.Date;

public class WebOffice {
	
	private String id;//����
	private Date creattime;//����ʱ��
	private Date updatetime;//�޸�ʱ��
	private String filename;//�ļ�����
	private String realpath;//�ļ�·��
	private String type;//�ļ���ʶ 1����λ���ᡢ2���������ᡢ3��word 4��Excel���
	private String nametype;//ģ����Ϣ��
	private String selty;//�Ƿ�ͨ��1��ͨ��2����ͨ��
	
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
