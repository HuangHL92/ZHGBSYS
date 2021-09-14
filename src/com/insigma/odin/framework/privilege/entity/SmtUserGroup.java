package com.insigma.odin.framework.privilege.entity;

public class SmtUserGroup extends SmtPrincipal implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;    //唯一主键
	private String usergroupname;   //用户组名称
	private String sid;     //上级用户组ID
	private String sortid;  //排序字段
	private String userid;   //用户表关联id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsergroupname() {
		return usergroupname;
	}
	public void setUsergroupname(String usergroupname) {
		this.usergroupname = usergroupname;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSortid() {
		return sortid;
	}
	public void setSortid(String sortid) {
		this.sortid = sortid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}