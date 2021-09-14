package com.insigma.siis.local.jtrans;

import java.util.List;

public class UDPackFile {

	public String fileName;
	
	public String filePath;
	
	public String transType;
	
	public String createTime;
	
	public String orgId;
	
	public String orgName;
	
	public String dataInfo;
	
	public String filePublishType;
	
	public String isOther;
	
	private String status;//呈报单状态  added by lizs
	
	private String cbd_id;//呈报单主键  added by lizs
	
	private String personname;
	
	private String personid;
	
	private List<SFileDefine> sfile; //文件信息  added by lizs

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDataInfo() {
		return dataInfo;
	}

	public void setDataInfo(String dataInfo) {
		this.dataInfo = dataInfo;
	}

	public String getIsOther() {
		return isOther;
	}
	
	public void setIsOther(String isOther) {
		this.isOther = isOther;
	}


	public UDPackFile(String fileName, String filePath, String transType,
			String createTime, String orgId, String orgName, String dataInfo,
			String filePublishType, String isOther,String status,String personname,String personid) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.transType = transType;
		this.createTime = createTime;
		this.orgId = orgId;
		this.orgName = orgName;
		this.dataInfo = dataInfo;
		this.filePublishType = filePublishType;
		this.isOther = isOther;
		this.status = status;
		this.personid = personid;
		this.personname = personname;
	}

	public UDPackFile() {
		super();
	}

	public String getFilePublishType() {
		return filePublishType;
	}

	public void setFilePublishType(String filePublishType) {
		this.filePublishType = filePublishType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCbd_id() {
		return cbd_id;
	}

	public void setCbd_id(String cbd_id) {
		this.cbd_id = cbd_id;
	}

	public List<SFileDefine> getSfile() {
		return sfile;
	}

	public void setSfile(List<SFileDefine> sfile) {
		this.sfile = sfile;
	}
	
	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}
	
	
}
