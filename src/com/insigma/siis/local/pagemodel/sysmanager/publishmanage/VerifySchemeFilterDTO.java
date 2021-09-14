package com.insigma.siis.local.pagemodel.sysmanager.publishmanage;

/**
 * 信息校验方案查询条件DTO
 * 
 * @author mengl
 *
 */
public class VerifySchemeFilterDTO {
	private String vsc002 ;			//方案名称
	private String vsc006Start ;	//创建时间开始
	private String vsc006End ;		//创建时间结束
	private String vsc005Name ;		//创建人
	private String vsc004 ;			//创建机构ID
	private String comboxArea_vsc004 ;			//创建机构显示内容
	

	private String filePublishType = "1";

	public VerifySchemeFilterDTO() {
		super();
	}

	public VerifySchemeFilterDTO(String vsc002, String vsc006Start,
			String vsc006End, String vsc005Name, String vsc004) {
		super();
		this.vsc002 = vsc002;
		this.vsc006Start = vsc006Start;
		this.vsc006End = vsc006End;
		this.vsc005Name = vsc005Name;
		this.vsc004 = vsc004;
	}

	public String getVsc002() {
		return vsc002;
	}

	public void setVsc002(String vsc002) {
		this.vsc002 = vsc002;
	}

	public String getVsc006Start() {
		return vsc006Start;
	}

	public void setVsc006Start(String vsc006Start) {
		this.vsc006Start = vsc006Start;
	}

	public String getVsc006End() {
		return vsc006End;
	}

	public void setVsc006End(String vsc006End) {
		this.vsc006End = vsc006End;
	}

	public String getVsc005Name() {
		return vsc005Name;
	}

	public void setVsc005Name(String vsc005Name) {
		this.vsc005Name = vsc005Name;
	}

	public String getVsc004() {
		return vsc004;
	}

	public void setVsc004(String vsc004) {
		this.vsc004 = vsc004;
	}

	public String getFilePublishType() {
		return filePublishType;
	}

	public void setFilePublishType(String filePublishType) {
		this.filePublishType = filePublishType;
	}

	public String getComboxArea_vsc004() {
		return comboxArea_vsc004;
	}

	public void setComboxArea_vsc004(String comboxArea_vsc004) {
		this.comboxArea_vsc004 = comboxArea_vsc004;
	}
	

}