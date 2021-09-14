package com.insigma.siis.local.business.entity;

public class DutyNum {
	private String id;
	private Integer orderNumber;
	private String unit;
	private String project;
	private String dutyCategory;
	private String dutyRank;
	private String quantity;
	private String remark;
	private String oneTicketVeto;
	private String querysql;
	public String getCreattime() {
		return creattime;
	}
	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}
	private String creattime;
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getUnit() {
		return unit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getDutyCategory() {
		return dutyCategory;
	}
	public void setDutyCategory(String dutyCategory) {
		this.dutyCategory = dutyCategory;
	}
	public String getDutyRank() {
		return dutyRank;
	}
	public void setDutyRank(String dutyRank) {
		this.dutyRank = dutyRank;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOneTicketVeto() {
		return oneTicketVeto;
	}
	public void setOneTicketVeto(String oneTicketVeto) {
		this.oneTicketVeto = oneTicketVeto;
	}
	public String getQuerysql() {
		return querysql;
	}
	public void setQuerysql(String querysql) {
		this.querysql = querysql;
	}
	
	
}
