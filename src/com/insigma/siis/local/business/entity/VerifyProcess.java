package com.insigma.siis.local.business.entity;

import java.lang.Long;

/**
 * VerifyProcess entity. @author MyEclipse Persistence Tools
 */

public class VerifyProcess implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 9046809949900476866L;
	private String processId;
	private Long totalNum;
	private Long currentNum;
	private Long resultFlag;
	private String batchNum;
	private String bsType;
	private String processMsg;
	private String userId;




	/** default constructor */
	public VerifyProcess() {
	}

	/** minimal constructor */
	public VerifyProcess(String processId, String batchNum, String bsType) {
		this.processId = processId;
		this.batchNum = batchNum;
		this.bsType = bsType;
	}

	/** full constructor */
	public VerifyProcess(String processId, Long totalNum,
			Long currentNum, Long resultFlag, String batchNum,
			String bsType,String userid) {
		this.processId = processId;
		this.totalNum = totalNum;
		this.currentNum = currentNum;
		this.resultFlag = resultFlag;
		this.batchNum = batchNum;
		this.bsType = bsType;
		this.userId=userid;
	}

	// Property accessors

	public String getProcessId() {
		return this.processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public Long getTotalNum() {
		return this.totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public Long getCurrentNum() {
		return this.currentNum;
	}

	public void setCurrentNum(Long currentNum) {
		this.currentNum = currentNum;
	}

	public Long getResultFlag() {
		return this.resultFlag;
	}

	public void setResultFlag(Long resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getBatchNum() {
		return this.batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getBsType() {
		return this.bsType;
	}

	public void setBsType(String bsType) {
		this.bsType = bsType;
	}

	public String getProcessMsg() {
		return processMsg;
	}

	public void setProcessMsg(String processMsg) {
		this.processMsg = processMsg;
	}
	
	public String getuserId(){
		return this.userId;
	}
	
	public void setuserId(String userid){
		this.userId=userid;
	}
	
	@Override
	public String toString() {
		return "VerifyProcess [processId=" + processId + ", totalNum="
				+ totalNum + ", currentNum=" + currentNum + ", resultFlag="
				+ resultFlag + ", batchNum=" + batchNum + ", bsType=" + bsType
				+ ", processMsg=" + processMsg + "]";
	}

}