package com.insigma.siis.local.business.modeldb;

public class ColFuture {
	private String information_set;//表
	private String information_set_field;//字段
	private String data_type;//数据类型
	private String code_type;//代码类型 为空表明非代码类型
	public String getInformation_set() {
		return information_set;
	}
	public void setInformation_set(String information_set) {
		this.information_set = information_set;
	}
	public String getInformation_set_field() {
		return information_set_field;
	}
	public void setInformation_set_field(String information_set_field) {
		this.information_set_field = information_set_field;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getCode_type() {
		return code_type;
	}
	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}
	

}
