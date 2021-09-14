package com.insigma.siis.local.business.entity;



import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Sublibrariescondition implements Serializable {
	private String sub_libraries_condition_id;//查询条件ID
	private String sub_libraries_model_id;//分库主题ID，外键
	private String information_set;//条件表
	private String information_set_field;//条件列
	private String condition_operator;//运算符
	private String field_value;//条件列值
	private String condition_connector;//和下一条件连接符(and，or)
	private String condition_no;//条件序号
	private String brackets;//括弧（(或者)）
	//冗余

	private String field_value_text;
	private String field_value_select;
	private String field_value_date;
	private String field_value_number;

	public Sublibrariescondition() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Sublibrariescondition(String sub_libraries_condition_id,
			String sub_libraries_model_id, String information_set,
			String information_set_field, String condition_operator,
			String field_value, String condition_connector,
			String condition_no, String brackets) {
		super();
		this.sub_libraries_condition_id = sub_libraries_condition_id;
		this.sub_libraries_model_id = sub_libraries_model_id;
		this.information_set = information_set;
		this.information_set_field = information_set_field;
		this.condition_operator = condition_operator;
		this.field_value = field_value;
		this.condition_connector = condition_connector;
		this.condition_no = condition_no;
		this.brackets = brackets;
	}

	public String getSub_libraries_condition_id() {
		return sub_libraries_condition_id;
	}

	public void setSub_libraries_condition_id(String sub_libraries_condition_id) {
		this.sub_libraries_condition_id = sub_libraries_condition_id;
	}

	public String getSub_libraries_model_id() {
		return sub_libraries_model_id;
	}

	public void setSub_libraries_model_id(String sub_libraries_model_id) {
		this.sub_libraries_model_id = sub_libraries_model_id;
	}

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

	public String getCondition_operator() {
		return condition_operator;
	}

	public void setCondition_operator(String condition_operator) {
		this.condition_operator = condition_operator;
	}

	public String getField_value() {
		return field_value;
	}

	public void setField_value(String field_value) {
		this.field_value = field_value;
	}

	public String getCondition_connector() {
		return condition_connector;
	}

	public void setCondition_connector(String condition_connector) {
		this.condition_connector = condition_connector;
	}

	public String getCondition_no() {
		return condition_no;
	}

	public void setCondition_no(String condition_no) {
		this.condition_no = condition_no;
	}

	public String getBrackets() {
		return brackets;
	}

	public void setBrackets(String brackets) {
		this.brackets = brackets;
	}

	

	public String getField_value_text() {
		return field_value_text;
	}

	public void setField_value_text(String field_value_text) {
		this.field_value_text = field_value_text;
	}

	public String getField_value_select() {
		return field_value_select;
	}

	public void setField_value_select(String field_value_select) {
		this.field_value_select = field_value_select;
	}

	public String getField_value_date() {
		return field_value_date;
	}

	public void setField_value_date(String field_value_date) {
		this.field_value_date = field_value_date;
	}

	public String getField_value_number() {
		return field_value_number;
	}

	public void setField_value_number(String field_value_number) {
		this.field_value_number = field_value_number;
	}

	

	

	

}
