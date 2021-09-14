package com.insigma.siis.local.pagemodel.yntp.tpl;

public class ExcelReturnParam {
	public ExcelReturnParam(boolean setRowHeight,int rowHeightLength) {
		super();
		this.setRowHeight = setRowHeight;
		this.rowHeightLength = rowHeightLength;
	}

	private boolean setRowHeight;
	private  int rowHeightLength;
	public ExcelReturnParam setRowHeightLength(int rowHeightLength) {
		this.rowHeightLength = rowHeightLength;
		return this;
	}

	public int getRowHeightLength() {
		return rowHeightLength;
	}

	public boolean isSetRowHeight() {
		return setRowHeight;
	}

	public void setSetRowHeight(boolean setRowHeight) {
		this.setRowHeight = setRowHeight;
	}
}
