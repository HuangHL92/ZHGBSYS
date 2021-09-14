package com.insigma.siis.local.pagemodel.repandrec.plat;

import java.sql.Blob;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="root")
public class PackInfoXml {

	private String transtype;
	private String stype;
	private String stypename;
	private String time;
	private String dataverion;
	private String b0101;
	private String b0111;
	private String b0114;
	private String b0194;
	private String orgcount;
	private String personcount;
	private String linkpsn;
	private String linktel;
	private String remark;
	private String datainfo;
	private String id;
	private String errortype;
	private String errorinfo;
	private List<SfileXml> sfile;//note

	
	public PackInfoXml(String transtype, String stype, String stypename,
			String time, String dataverion, String b0101, String b0111,
			String b0114, String b0194, String orgcount, String personcount,
			String linkpsn, String linktel, String remark, String datainfo,
			String id, String errortype, String errorinfo, List<SfileXml> sfile) {
		super();
		this.transtype = transtype;
		this.stype = stype;
		this.stypename = stypename;
		this.time = time;
		this.dataverion = dataverion;
		this.b0101 = b0101;
		this.b0111 = b0111;
		this.b0114 = b0114;
		this.b0194 = b0194;
		this.orgcount = orgcount;
		this.personcount = personcount;
		this.linkpsn = linkpsn;
		this.linktel = linktel;
		this.remark = remark;
		this.datainfo = datainfo;
		this.id = id;
		this.errortype = errortype;
		this.errorinfo = errorinfo;
		this.sfile = sfile;
	}


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


	public String getDataverion() {
		return dataverion;
	}


	public void setDataverion(String dataverion) {
		this.dataverion = dataverion;
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


	public String getOrgcount() {
		return orgcount;
	}


	public void setOrgcount(String orgcount) {
		this.orgcount = orgcount;
	}


	public String getPersoncount() {
		return personcount;
	}


	public void setPersoncount(String personcount) {
		this.personcount = personcount;
	}


	public String getLinkpsn() {
		return linkpsn;
	}


	public void setLinkpsn(String linkpsn) {
		this.linkpsn = linkpsn;
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


	public List<SfileXml> getSfile() {
		return sfile;
	}


	public void setSfile(List<SfileXml> sfile) {
		this.sfile = sfile;
	}


	public PackInfoXml() {
		super();
	}
	
}
