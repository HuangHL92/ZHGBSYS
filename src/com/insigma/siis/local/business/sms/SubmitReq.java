package com.insigma.siis.local.business.sms;


/**
 * 短信发结构体
 * @author a
 *
 */
public class SubmitReq {
	private String apid;
	private String ecname;
	private String secreKey;
	private String content;
	private String mobiles;
	private String addSerial;
	private String sign;
	private String mac;
	
	private String templateId;
	private String params;
	
	public String getApid() {
		return apid;
	}
	public void setApid(String apid) {
		this.apid = apid;
	}
	public String getEcname() {
		return ecname;
	}
	public void setEcname(String ecname) {
		this.ecname = ecname;
	}
	public String getSecreKey() {
		return secreKey;
	}
	public void setSecreKey(String secreKey) {
		this.secreKey = secreKey;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMobiles() {
		return mobiles;
	}
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	public String getAddSerial() {
		return addSerial;
	}
	public void setAddSerial(String addSerial) {
		this.addSerial = addSerial;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
}
