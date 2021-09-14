package com.insigma.siis.local.business.entity;

import java.sql.Timestamp;

public class ImpProcess {
	
	private String impprocessid;
	private String processtype;
	private String processname;
	private Timestamp starttime;
	private Timestamp endtime;
	private String processstatus;
	private String processinfo;
	private String imprecordid;
	private String addfidld1;
	private String addfield2;
	private String addfield3;
	public String getImpprocessid() {
		return impprocessid;
	}
	public void setImpprocessid(String impprocessid) {
		this.impprocessid = impprocessid;
	}
	public String getProcesstype() {
		return processtype;
	}
	public void setProcesstype(String processtype) {
		this.processtype = processtype;
	}
	public String getProcessname() {
		return processname;
	}
	public void setProcessname(String processname) {
		this.processname = processname;
	}
	
	public Timestamp getStarttime() {
		return starttime;
	}
	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	public String getProcessstatus() {
		return processstatus;
	}
	public void setProcessstatus(String processstatus) {
		this.processstatus = processstatus;
	}
	public String getProcessinfo() {
		return processinfo;
	}
	public void setProcessinfo(String processinfo) {
		this.processinfo = processinfo;
	}
	public String getImprecordid() {
		return imprecordid;
	}
	public void setImprecordid(String imprecordid) {
		this.imprecordid = imprecordid;
	}
	public String getAddfidld1() {
		return addfidld1;
	}
	public void setAddfidld1(String addfidld1) {
		this.addfidld1 = addfidld1;
	}
	public String getAddfield2() {
		return addfield2;
	}
	public void setAddfield2(String addfield2) {
		this.addfield2 = addfield2;
	}
	public String getAddfield3() {
		return addfield3;
	}
	public void setAddfield3(String addfield3) {
		this.addfield3 = addfield3;
	}

}
