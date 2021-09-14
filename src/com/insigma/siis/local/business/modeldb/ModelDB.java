package com.insigma.siis.local.business.modeldb;

import java.util.List;

import com.insigma.siis.local.business.entity.Sublibrariescolumns;
import com.insigma.siis.local.business.entity.Sublibrariescondition;
import com.insigma.siis.local.business.entity.Sublibrariesmodel;

public class ModelDB {
	private String id;
	private Sublibrariesmodel model;
	private List<Sublibrariescondition> conditions;
	private List<Sublibrariescolumns> columns;
	public Sublibrariesmodel getModel() {
		return model;
	}
	public void setModel(Sublibrariesmodel model) {
		this.model = model;
	}
	public List<Sublibrariescondition> getConditions() {
		return conditions;
	}
	public void setConditions(List<Sublibrariescondition> conditions) {
		this.conditions = conditions;
	}
	public List<Sublibrariescolumns> getColumns() {
		return columns;
	}
	public void setColumns(List<Sublibrariescolumns> columns) {
		this.columns = columns;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}
