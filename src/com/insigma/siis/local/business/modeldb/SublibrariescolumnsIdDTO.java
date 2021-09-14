package com.insigma.siis.local.business.modeldb;
import java.io.Serializable;
public class SublibrariescolumnsIdDTO implements Serializable {
	private String sub_libraries_model_id;
	private String information_set;
	private String information_set_field;
	
	
	public SublibrariescolumnsIdDTO() {
		super();
	}
	
	
	public SublibrariescolumnsIdDTO(String sub_libraries_model_id,
			String information_set, String information_set_field) {
		super();
		this.sub_libraries_model_id = sub_libraries_model_id;
		this.information_set = information_set;
		this.information_set_field = information_set_field;
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
	
	
	
	
}
