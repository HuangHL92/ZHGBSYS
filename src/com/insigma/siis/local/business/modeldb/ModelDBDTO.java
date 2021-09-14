package com.insigma.siis.local.business.modeldb;

import java.util.List;

import com.insigma.siis.local.business.entity.SublibrariesconditionDTO;

public class ModelDBDTO {
	private String id;
	private SublibrariesmodelDTO model;
	private List<SublibrariesconditionDTO> conditions;
	private List<SublibrariescolumnsDTO> columns;
	public SublibrariesmodelDTO getModel() {
		return model;
	}
	public void setModel(SublibrariesmodelDTO model) {
		this.model = model;
	}
	public List<SublibrariesconditionDTO> getConditions() {
		return conditions;
	}
	public void setConditions(List<SublibrariesconditionDTO> conditions) {
		this.conditions = conditions;
	}
	public List<SublibrariescolumnsDTO> getColumns() {
		return columns;
	}
	public void setColumns(List<SublibrariescolumnsDTO> columns) {
		this.columns = columns;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}
