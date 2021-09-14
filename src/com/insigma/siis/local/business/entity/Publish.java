package com.insigma.siis.local.business.entity;

public class Publish implements java.io.Serializable {
	private String publishid;
	private String meetingid;
	private String agendatype;
	private String agendaname;
	private int sort;
	private String status;
	private String userid;
	/**
	 * @return the publishid
	 */
	public String getPublishid() {
		return publishid;
	}
	/**
	 * @param publishid the publishid to set
	 */
	public void setPublishid(String publishid) {
		this.publishid = publishid;
	}
	/**
	 * @return the meetingid
	 */
	public String getMeetingid() {
		return meetingid;
	}
	/**
	 * @param meetingid the meetingid to set
	 */
	public void setMeetingid(String meetingid) {
		this.meetingid = meetingid;
	}
	/**
	 * @return the agendatype
	 */
	public String getAgendatype() {
		return agendatype;
	}
	/**
	 * @param agendatype the agendatype to set
	 */
	public void setAgendatype(String agendatype) {
		this.agendatype = agendatype;
	}
	/**
	 * @return the agendaname
	 */
	public String getAgendaname() {
		return agendaname;
	}
	/**
	 * @param agendaname the agendaname to set
	 */
	public void setAgendaname(String agendaname) {
		this.agendaname = agendaname;
	}
	/**
	 * @return the sort
	 */
	public int getSort() {
		return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Publish [publishid=" + publishid + ", meetingid=" + meetingid + ", agendatype=" + agendatype
				+ ", agendaname=" + agendaname + ", sort=" + sort + ", status=" + status + ", userid=" + userid + "]";
	}
	
	
	
}