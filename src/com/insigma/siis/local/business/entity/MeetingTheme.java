package com.insigma.siis.local.business.entity;


public class MeetingTheme implements java.io.Serializable {
	private String meetingid;
	private String time;
	private String meetingname;
	private String status;
	private String sort;
	private String meetingtype;
	private String userid;
	private String meetingjc;
	private String meetingpc;
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
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
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
	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * @return the meetingname
	 */
	public String getMeetingname() {
		return meetingname;
	}
	/**
	 * @param meetingname the meetingname to set
	 */
	public void setMeetingname(String meetingname) {
		this.meetingname = meetingname;
	}

	public String getMeetingtype() {
		return meetingtype;
	}
	public void setMeetingtype(String meetingtype) {
		this.meetingtype = meetingtype;
	}
	

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getMeetingjc() {
		return meetingjc;
	}
	public void setMeetingjc(String meetingjc) {
		this.meetingjc = meetingjc;
	}
	public String getMeetingpc() {
		return meetingpc;
	}
	public void setMeetingpc(String meetingpc) {
		this.meetingpc = meetingpc;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MeetingTheme [meetingid=" + meetingid + ", time=" + time + ", meetingname=" + meetingname + ", status="
				+ status + ", sort=" + sort + ", meetingtype=" + meetingtype + ", userid=" + userid 
				+ ", meetingjc=" + meetingjc +", meetingpc=" + meetingpc +"]";
	}
	
	

}