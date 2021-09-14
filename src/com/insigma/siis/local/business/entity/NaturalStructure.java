package com.insigma.siis.local.business.entity;

public class NaturalStructure {
	
	private String id;
	private Integer orderNumber;
	private String unit;
	private String category;
	private String project;
	private String quantity;
	private String oneTicketVeto;
	private String count;
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
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
	private String querysql;
	
	
}
