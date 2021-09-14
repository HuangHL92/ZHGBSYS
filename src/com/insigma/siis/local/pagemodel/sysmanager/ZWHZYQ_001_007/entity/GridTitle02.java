package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity;


public class GridTitle02{

	private String header;
	private String dataIndex;
	private String width = "100";
	private String editType = "Text";
	private boolean sortable = true;
	private boolean hidden = false;
	private String align = "center";
	private String renderer;
	private String headerTitle;
	private String disabled;
	
	public String getHeader(){
		return header;
	}
	public void setHeader(String header){
		this.header = header;
	}
	
	public String getDataIndex(){
		return dataIndex;
	}
	public void setDataIndex(String dataIndex){
		this.dataIndex = dataIndex;
	}
	
	public String getWidth(){
		return width;
	}
	public void setWidth(String width){
		this.width = width;
	}
	
	public String getEditType(){
		return editType;
	}
	public void setEditType(String editType){
		this.editType = editType;
	}
	
	public boolean isSortable(){
		return sortable;
	}
	public void setSortable(boolean sortable){
		this.sortable = sortable;
	}
	
	public boolean isHidden(){
		return hidden;
	}
	public void setHidden(boolean hidden){
		this.hidden = hidden;
	}
	
	public String getAlign(){
		return align;
	}
	public void setAlign(String align){
		this.align = align;
	}
	
	public String getRenderer(){
		return renderer;
	}
	public void setRenderer(String renderer){
		this.renderer = renderer;
	}
	
	
	public String getHeaderTitle() {
		return headerTitle;
	}
	public void setHeaderTitle(String headerTitle) {
		this.headerTitle = headerTitle;
	}
	
	
	
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public GridTitle02(){
		super();
	}
	public GridTitle02(String header, String dataIndex, String width, 
			String editType, boolean sortable, boolean hidden, String align, String renderer,String headerTitle){
		super();
		this.header = header;
		this.dataIndex = dataIndex;
		this.width = width;
		this.editType = editType;
		this.sortable = sortable;
		this.hidden = hidden;
		this.align = align;
		this.renderer = renderer;
		this.headerTitle = headerTitle;
		this.disabled = disabled;
	}
	
}
