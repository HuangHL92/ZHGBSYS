package com.insigma.siis.local.jtrans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "root")
public class ZwhzPackDefine {
	
	private String transtype;// up down
	
	private String stype;// 123,  ,7 ；   10：呈报单上报
	
	private String stypename; 
	
	private String time;
	
	private String dataversion;
	
	private String b0101;
	
	private String b0111;
	
	private String b0114;
	
	private String b0194;
	
	private Long orgcount;
	
	private Long personcount;
	
	private String linktel;
	
	private String linkpsn;
	
	private String remark;
	
	private String datainfo;
	
	private String id;
	
	private String errortype;
	
	private String errorinfo;
	
	private List<SFileDefine> sfile;
	
	private String cbdStatus;
	
	private String personname;
	
	private String personid;

	public String getTranstype() {
		return transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getStypename() {
		return stypename;
	}

	public void setStypename(String stypename) {
		this.stypename = stypename;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDataversion() {
		return dataversion;
	}

	public void setDataversion(String dataversion) {
		this.dataversion = dataversion;
	}

	public String getB0101() {
		return b0101;
	}

	public void setB0101(String b0101) {
		this.b0101 = b0101;
	}

	public String getB0111() {
		return b0111;
	}

	public void setB0111(String b0111) {
		this.b0111 = b0111;
	}

	public String getB0114() {
		return b0114;
	}

	public void setB0114(String b0114) {
		this.b0114 = b0114;
	}

	public String getB0194() {
		return b0194;
	}

	public void setB0194(String b0194) {
		this.b0194 = b0194;
	}

	public Long getOrgcount() {
		return orgcount;
	}

	public void setOrgcount(Long orgcount) {
		this.orgcount = orgcount;
	}

	public Long getPersoncount() {
		return personcount;
	}

	public void setPersoncount(Long personcount) {
		this.personcount = personcount;
	}

	public String getLinktel() {
		return linktel;
	}

	public void setLinktel(String linktel) {
		this.linktel = linktel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDatainfo() {
		return datainfo;
	}

	public void setDatainfo(String datainfo) {
		this.datainfo = datainfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getErrortype() {
		return errortype;
	}

	public void setErrortype(String errortype) {
		this.errortype = errortype;
	}

	public String getErrorinfo() {
		return errorinfo;
	}

	public void setErrorinfo(String errorinfo) {
		this.errorinfo = errorinfo;
	}

	public List<SFileDefine> getSfile() {
		return sfile;
	}

	public void setSfile(List<SFileDefine> sfile) {
		this.sfile = sfile;
	}

	public ZwhzPackDefine(String transtype, String stype, String stypename,
			String time, String dataversion, String b0101, String b0111,
			String b0114, String b0194, Long orgcount, Long personcount,
			String linktel, String remark, String datainfo, String id,
			String errortype, String errorinfo, List<SFileDefine> sfile,String cbdStatus,
			String personname,String personid) {
		super();
		this.transtype = transtype;
		this.stype = stype;
		this.stypename = stypename;
		this.time = time;
		this.dataversion = dataversion;
		this.b0101 = b0101;
		this.b0111 = b0111;
		this.b0114 = b0114;
		this.b0194 = b0194;
		this.orgcount = orgcount;
		this.personcount = personcount;
		this.linktel = linktel;
		this.remark = remark;
		this.datainfo = datainfo;
		this.id = id;
		this.errortype = errortype;
		this.errorinfo = errorinfo;
		this.sfile = sfile;
		this.cbdStatus = cbdStatus;
		this.personid = personid;
		this.personname = personname;
	}

	public ZwhzPackDefine() {
		super();
	}

	public String getLinkpsn() {
		return linkpsn;
	}

	public void setLinkpsn(String linkpsn) {
		this.linkpsn = linkpsn;
	}

	public String getCbdStatus() {
		return cbdStatus;
	}

	public void setCbdStatus(String cbdStatus) {
		this.cbdStatus = cbdStatus;
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
