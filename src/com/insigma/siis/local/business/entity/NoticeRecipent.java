package com.insigma.siis.local.business.entity;


/**
 * Folder entity. @author MyEclipse Persistence Tools
 */
//֪ͨ���������Ϣ
public class NoticeRecipent implements java.io.Serializable {

	// Fields
	private String id;				//����id
	private String noticeId;		//֪ͨ����ID
	private String recipientId;		//������id
	private String recipientName;	//����������
	private String see;				//�Ƿ��Ѳ鿴 0 ��δ�鿴��1���Ѳ鿴

	
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