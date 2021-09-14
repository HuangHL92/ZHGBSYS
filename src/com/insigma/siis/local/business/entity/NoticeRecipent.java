package com.insigma.siis.local.business.entity;


/**
 * Folder entity. @author MyEclipse Persistence Tools
 */
//通知公告接收信息
public class NoticeRecipent implements java.io.Serializable {

	// Fields
	private String id;				//主键id
	private String noticeId;		//通知公告ID
	private String recipientId;		//接收人id
	private String recipientName;	//接收人名称
	private String see;				//是否已查看 0 ：未查看，1：已查看

	
	/** default constructor */
	public NoticeRecipent() {
	}

	/** full constructor */
	public NoticeRecipent(String id, String noticeId, String recipientId, String recipientName, String see
			) {
		super();
		this.id = id;
		this.noticeId = noticeId;
		this.recipientId = recipientId;
		this.recipientName = recipientName;
		this.see = see;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getSee() {
		return see;
	}

	public void setSee(String see) {
		this.see = see;
	}

	

}