package com.insigma.odin.framework.privilege.entity;

public class SmtUserGroup extends SmtPrincipal implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;    //Ψһ����
	private String usergroupname;   //�û�������
	private String sid;     //�ϼ��û���ID
	private String sortid;  //�����ֶ�
	private String userid;   //�û������id
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