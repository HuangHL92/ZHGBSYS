package com.insigma.siis.local.module.common.codeparameter;

import com.insigma.odin.framework.ActionFormSupport;

public class CodeParameterForm extends ActionFormSupport{
	
	private static final long serialVersionUID = 1L;
	
	private String list2Data; //������ϸ

	private String deleteList;//ɾ����ϸ

	public String getList2Data() {
		return list2Data;
	}

	public void setList2Data(String list2Data) {
		this.list2Data = list2Data;
	}

	public String getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(String deleteList) {
		this.deleteList = deleteList;
	}
	
}
