package com.insigma.siis.local.business.helperUtil;

import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.commform.ObjectUtil;
import com.insigma.odin.taglib.util.TagConst;
import com.insigma.odin.taglib.util.TagUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.struts.util.ResponseUtils;

public class EditorGridTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8787093442545839457L;
	private String _$40;
	private String _$39;
	private String _$38;
	private String _$37;
	private String _$36;
	private String _$35;
	private String _$34;
	private String _$33;
	private String _$32;
	private String _$31;
	private String _$30;
	private String _$29;
	private String _$28;
	private String _$27;
	private String _$26;
	private String _$25;
	private String _$24;
	private String _$23 = "row";
	private String _$22;
	private String _$21;
	private String _$20;
	private String _$19;
	private String _$18;
	private String _$17;
	private String _$16;
	private String _$15;
	private String _$14;
	private String _$13;
	private String _$12;
	private String _$11;
	private String _$10;
	private String _$9;
	private String _$8;
	private String _$7;
	private String _$6;
	private String _$5;
	private String _$4 = "true";
	private String _$3 = "true";
	private String _$2;
	private String _$1;
	private Boolean locked;
	private String ddGroup;
	public String getDdGroup() {
		return ddGroup;
	}

	public void setDdGroup(String ddGroup) {
		this.ddGroup = ddGroup;
	}

	private Boolean enableColumnHide;
	private String groupTextTpl;
	private String colHGroupRows;
	private String plugins;

	

	public String getPlugins() {
		return plugins;
	}

	public void setPlugins(String plugins) {
		this.plugins = plugins;
	}

	public String getColHGroupRows() {
		return colHGroupRows;
	}

	public void setColHGroupRows(String colHGroupRows) {
		this.colHGroupRows = colHGroupRows;
	}

	public String getGroupTextTpl() {
		return groupTextTpl;
	}

	public void setGroupTextTpl(String groupTextTpl) {
		this.groupTextTpl = groupTextTpl;
	}

	public Boolean getEnableColumnHide() {
		return enableColumnHide;
	}

	public void setEnableColumnHide(Boolean enableColumnHide) {
		this.enableColumnHide = enableColumnHide;
	}

	public String getIsAlertSaveAfterPageChanging() {
		return this._$2;
	}

	public void setIsAlertSaveAfterPageChanging(String paramString) {
		this._$2 = paramString;
	}

	public String getSaveAfterPageChangingMsg() {
		return this._$1;
	}

	public void setSaveAfterPageChangingMsg(String paramString) {
		this._$1 = paramString;
	}

	public String getBufferView() {
		return this._$6;
	}

	public void setBufferView(String paramString) {
		this._$6 = paramString;
	}

	public String getCacheSize() {
		return this._$5;
	}

	public void setCacheSize(String paramString) {
		this._$5 = paramString;
	}

	public String getRemoteSort() {
		return this._$7;
	}

	public void setRemoteSort(String paramString) {
		this._$7 = paramString;
	}

	public String getRightMenuId() {
		return this._$8;
	}

	public void setRightMenuId(String paramString) {
		this._$8 = paramString;
	}

	public String getInputName() {
		return this._$10;
	}

	public void setInputName(String paramString) {
		this._$10 = paramString;
	}

	public String getGrouping() {
		return this._$28;
	}

	public void setGrouping(String paramString) {
		this._$28 = paramString;
	}

	public String getGroupCol() {
		return this._$27;
	}

	public void setGroupCol(String paramString) {
		this._$27 = paramString;
	}

	public String getCounting() {
		return this._$25;
	}

	public void setCounting(String paramString) {
		this._$25 = paramString;
	}

	public String getAutoFill() {
		return this._$33;
	}

	public void setAutoFill(String paramString) {
		this._$33 = paramString;
	}

	public String getHeight() {
		return this._$39;
	}

	public void setHeight(String paramString) {
		this._$39 = paramString;
	}

	public String getTitle() {
		return this._$38;
	}

	public void setTitle(String paramString) {
		this._$38 = paramString;
	}

	public String getWidth() {
		return this._$40;
	}

	public void setWidth(String paramString) {
		this._$40 = paramString;
	}

	public int doEndTag() throws JspException {
		StringBuffer localStringBuffer = new StringBuffer();
		if (this._$28 == null) {
			localStringBuffer.append("var ds = new Ext.data.Store({");
			localStringBuffer.append("reader: reader,");
			localStringBuffer.append("baseParams: {cueGridId:'" + this._$34
					+ "'},");
		} else {
			localStringBuffer.append("var ds = new Ext.data.GroupingStore({");
			localStringBuffer.append("reader: reader,");
			localStringBuffer.append("baseParams: {cueGridId:'" + this._$34
					+ "'},");
		    localStringBuffer.append("sortInfo:{field: '" + this._$27 + "', direction: \"" + (this._$26 == null ? "ASC" : this._$26) + "\"},");
			localStringBuffer.append("groupField:'" + this._$27 + "',");
		}
		if ((this._$15 != null) && (!this._$15.equals(""))) {
			HttpServletRequest localHttpServletRequest = (HttpServletRequest) this.pageContext
					.getRequest();
			this._$15 = (localHttpServletRequest.getContextPath() + this._$15);
			localStringBuffer.append("url: '" + this._$15 + "'");
		} else {
			localStringBuffer.append("data: gridData,");
			localStringBuffer
					.append("proxy:new Ext.data.MemoryProxy(gridData)");
		}
		if ((this._$7 != null) && (!this._$7.equals("")))
			localStringBuffer.append(",remoteSort:" + this._$7);
		localStringBuffer.append("});");
		if(this._$12!=null)
			localStringBuffer.append("ds.on('beforeload'," + this._$12 + ");");
		if(this._$14!=null)
			localStringBuffer
					.append("ds.on('load',function(a,b,c){odin.doCommLoad(a,b,c);"
							+ this._$14 + "(a,b,c);});");
		localStringBuffer.append(TagUtil.getFilterFuncInfo("Ext.getCmp('"
				+ this._$34 + "')", this._$24));
		localStringBuffer.append("var grid_" + this._$34
				+ " = new Ext.grid.EditorGridPanel({");
		if(this.enableColumnHide!=null&&this.enableColumnHide==false){
			localStringBuffer.append("enableColumnHide:"+this.enableColumnHide+",");
		}
		if(this.ddGroup!=null){
			localStringBuffer.append("ddGroup:'"+this.ddGroup+"',");
		}
		localStringBuffer.append("ds:ds,");
		localStringBuffer.append("cm:colModel,");
		localStringBuffer
				.append((this._$23 == null) || (this._$23.equals("")) ? ""
						: "selModel:sm,");
		this._$30 = (this._$30 == null ? TagConst.GRID_CLICKSTOEDIT : this._$30);
		if (this._$30.equals("true"))
			localStringBuffer.append("clicksToEdit: 1,");
		localStringBuffer.append("id:'" + this._$34 + "',");
		localStringBuffer.append("enableDragDrop: true" + ",");//行拖动
		localStringBuffer.append("");
		localStringBuffer
				.append("viewConfig: {forceFit:"
						+ (this._$35 == null ? TagConst.GRID_FORCE_NOSCROLL : this._$35)
						+ ",autoFill:" + (this._$33 == null ? TagConst.GRID_AUTOFILL : this._$33)
						+ ",getRowClass:function(record, rowIndex, rowParams, store){if(typeof changeRowClass=='function'){return changeRowClass(record, rowIndex, rowParams, store)}}},");
		if (!TagUtil.autoWidthSetting(localStringBuffer, this._$34))
			localStringBuffer.append("width: "
					+ (this._$40 == null ? Integer
							.valueOf(TagConst.GRID_DEFAULT_WIDTH) : this._$40)
					+ ",");
		localStringBuffer.append("height: "
				+ (this._$39 == null ? Integer
						.valueOf(TagConst.GRID_DEFAULT_HEIGHT) : this._$39)
				+ ",");
		localStringBuffer.append("title:'"
				+ (this._$38 == null ? "" : this._$38) + "',");
		localStringBuffer.append("loadMask: true,");
		if (this._$28 != null) {
			localStringBuffer.append("view: new Ext.grid.GroupingView({");
			localStringBuffer.append("forceFit:false,autoFill:"
					+ (this._$33 == null ? TagConst.GRID_AUTOFILL : this._$33)
					+ ",");
			
			if (this.groupTextTpl != null) {
				localStringBuffer
				.append("groupTextTpl: '"+this.groupTextTpl+"',groupByText:'按类别分组',showGroupsText:'类别'");
			}else{
				localStringBuffer
				.append("groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"项\" : \"项\"]})'"
						+ ",emptypGroupText:'其他'");
			}
			
			localStringBuffer.append("}),");
		} else if ((this._$6 != null) && (this._$6.equals("true"))) {
			localStringBuffer.append("view: new Ext.ux.grid.BufferView({");
			localStringBuffer.append("cacheSize:"
					+ (this._$5 != null ? this._$5 : "50") + ",");
			localStringBuffer.append("forceFit:"
					+ (this._$35 == null ? TagConst.GRID_FORCE_NOSCROLL
							: this._$35) + ",autoFill:"
					+ (this._$33 == null ? TagConst.GRID_AUTOFILL : this._$33)
					+ ",");
			localStringBuffer.append("scrollDelay: true");
			localStringBuffer.append("}),");
		}else if(this.locked!=null&&this.locked==true){//后期维护 zoul
			localStringBuffer.append("view: new Ext.ux.grid.LockingGridView({forceFit : false , getRowClass:function(record, rowIndex, rowParams, store){if(typeof changeRowClass=='function'){return changeRowClass(record, rowIndex, rowParams, store)}} }),");
		}
		if (this._$25 != null)
			localStringBuffer.append("plugins: new Ext.grid.GroupSummary(),");
		else if(this.colHGroupRows!=null){
			localStringBuffer.append("plugins: new Ext.ux.grid.ColumnHeaderGroup({rows: ["+this.colHGroupRows+"]}),");
		}else if(this.plugins!=null){
			localStringBuffer.append("plugins: "+this.plugins+",");
		}
		localStringBuffer.append("collapsible:"
				+ (this._$32 == null ? TagConst.COLLAPSIBLE : this._$32) + ",");
		localStringBuffer.append("collapsed:"
				+ (this._$31 == null ? TagConst.COLLAPSED : this._$31) + ",");
		if (this._$22 != null)
			localStringBuffer.append("tbar:"
					+ TagUtil.getToolBarInfo(this._$22, this._$13) + ",");
		if (this._$21 != null)
			localStringBuffer.append("bbar:"
					+ TagUtil.getToolBarInfo(this._$21, this._$13) + ",");
		if (this._$36 != null)
			localStringBuffer.append("autoExpandColumn:'" + this._$36 + "',");
		if ((this._$37 != null) && (!this._$37.equals("")))
			localStringBuffer.append("renderTo:" + this._$37);
		else
			localStringBuffer.append("renderTo:gridDiv_" + this._$34);
		localStringBuffer.append("});");
		localStringBuffer.append(TagUtil.getGridEventStr(this._$34,
				"cellmousedown", this._$20));
		this._$19 = (this._$19 == null ? "odin.afterEditForEditGrid"
				: this._$19);
		if (this._$9 != null)
			this._$19 = ("function(e){if( (','+'" + this._$9
					+ "'+',').indexOf(','+e.field+',') == -1 ){ return true; }"
					+ this._$19 + "(e);}");
		localStringBuffer.append(TagUtil.getGridEventStr(this._$34,
				"afteredit", this._$19));
		localStringBuffer.append(TagUtil.getGridEventStr(this._$34,
				"beforeedit", this._$11));
		localStringBuffer.append(TagUtil.getGridEventStr(this._$34,
				"rowdblclick", this._$18));
		localStringBuffer.append(TagUtil.getGridEventStr(this._$34,
				"celldblclick", this._$17));
		localStringBuffer.append(TagUtil.getGridEventStr(this._$34, "keydown",
				this._$16));
		if ((this._$8 != null) && (!this._$8.trim().equals("")))
			localStringBuffer
					.append(TagUtil
							.getGridEventStr(
									this._$34,
									"rowcontextmenu",
									"function(grid,rowIndex,e){e.preventDefault();"
											+ ((this._$23 != null)
													&& (this._$23.equals("row")) ? "grid.getSelectionModel().selectRow(rowIndex);"
													: "")
											+ "odin.ext.menu.MenuMgr.get('"
											+ this._$8
											+ "').showAt(e.getXY());" + "}"));
		this._$29 = (this._$29 == null ? TagConst.GRID_ISFIRSTLOADDATA
				: this._$29);
		if (this._$29.equals("true"))
			if (((this._$22 != null) && (this._$22.toLowerCase()
					.equals("pagetoolbar")))
					|| ((this._$21 != null) && (this._$21.toLowerCase()
							.equals("pagetoolbar"))))
				localStringBuffer.append("ds.load({params:{start:0, limit:"
						+ (this._$13 == null ? Integer
								.valueOf(GlobalNames.PAGE_SIZE) : this._$13)
						+ "}});");
			else
				localStringBuffer.append("ds.load();");
		if (ObjectUtil.equals(this._$2, "true")) {
			if (this._$1 == null)
				this._$1 = "是否保存当页数据？";
			localStringBuffer
					.append("ds.on('beforeload', function(theds){if(theds.getModifiedRecords().length>0){if(confirm('"
							+ this._$1
							+ "')){doGridSave('"
							+ this._$34
							+ "');};theds.commitChanges();}});");
		} else {
			localStringBuffer
					.append("ds.on('beforeload', function(theds){theds.commitChanges();});");
		}
		localStringBuffer.append("odin.setGridJsonData('"
				+ this._$34
				+ "','"
				+ ((this._$10 == null) || (this._$10.equals("")) ? this._$34
						+ "Data" : this._$10) + "');");
		localStringBuffer.append("});");
		localStringBuffer.append("Ext.onReady(function(){");
		if (ObjectUtil.equals(this._$4, "true"))
			localStringBuffer
					.append("Ext.getCmp('"
							+ this._$34
							+ "').on('contextmenu', function(e){odin.grid.menu.gridContextmenu(e,this,'"
							+ this._$3 + "')});");
		localStringBuffer.append("})");
		localStringBuffer.append("</script>");
		ResponseUtils.write(this.pageContext, localStringBuffer.toString());
		return super.doEndTag();
	}

	public Boolean isLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public int doStartTag() throws JspException {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("<div id='forView_" + this._$34
				+ "' style='width:100%;'>");
		if ((this._$37 == null) || (this._$37.equals("")))
			localStringBuffer.append("<div id='gridDiv_" + this._$34
					+ "' ></div>");
		localStringBuffer.append("</div>");
		String str = (this._$10 == null) || (this._$10.equals("")) ? this._$34
				+ "Data" : this._$10;
		localStringBuffer
				.append("<input type='hidden' style='display:none' name='"
						+ str + "' id='" + str + "' ");
		localStringBuffer.append(" value='"
				+ TagUtil.getPropertyValue(this.pageContext, str) + "' ");
		localStringBuffer.append(">");
		localStringBuffer.append("<script>");
		localStringBuffer.append("Ext.onReady(function(){");
		localStringBuffer.append(TagUtil.getGridSmInfo(this._$23));
		ResponseUtils.write(this.pageContext, localStringBuffer.toString());
		return 1;
	}

	public String getProperty() {
		return this._$34;
	}

	public void setProperty(String paramString) {
		this._$34 = paramString;
	}

	public String getApplyTo() {
		return this._$37;
	}

	public void setApplyTo(String paramString) {
		this._$37 = paramString;
	}

	public String getAutoExpandCol() {
		return this._$36;
	}

	public void setAutoExpandCol(String paramString) {
		this._$36 = paramString;
	}

	public String getForceNoScroll() {
		return this._$35;
	}

	public void setForceNoScroll(String paramString) {
		this._$35 = paramString;
	}

	public String getCollapsible() {
		return this._$32;
	}

	public void setCollapsible(String paramString) {
		this._$32 = paramString;
	}

	public String getCellmousedown() {
		return this._$20;
	}

	public void setCellmousedown(String paramString) {
		this._$20 = paramString;
	}

	public String getClicksToEdit() {
		return this._$30;
	}

	public void setClicksToEdit(String paramString) {
		this._$30 = paramString;
	}

	public String getAfteredit() {
		return this._$19;
	}

	public void setAfteredit(String paramString) {
		this._$19 = paramString;
	}

	public String getTopBarId() {
		return this._$22;
	}

	public void setTopBarId(String paramString) {
		this._$22 = paramString;
	}

	public String getBbarId() {
		return this._$21;
	}

	public void setBbarId(String paramString) {
		this._$21 = paramString;
	}

	public String getSm() {
		return this._$23;
	}

	public void setSm(String paramString) {
		this._$23 = paramString;
	}

	public String getFilterFunc() {
		return this._$24;
	}

	public void setFilterFunc(String paramString) {
		this._$24 = paramString;
	}

	public String getUrl() {
		return this._$15;
	}

	public void setUrl(String paramString) {
		this._$15 = paramString;
	}

	public String getIsFirstLoadData() {
		return this._$29;
	}

	public void setIsFirstLoadData(String paramString) {
		this._$29 = paramString;
	}

	public String getCollapsed() {
		return this._$31;
	}

	public void setCollapsed(String paramString) {
		this._$31 = paramString;
	}

	public String getPageSize() {
		return this._$13;
	}

	public void setPageSize(String paramString) {
		this._$13 = paramString;
	}

	public String getBeforeload() {
		return this._$12;
	}

	public void setBeforeload(String paramString) {
		this._$12 = paramString;
	}

	public String getBeforeedit() {
		return this._$11;
	}

	public void setBeforeedit(String paramString) {
		this._$11 = paramString;
	}

	public String getRowDbClick() {
		return this._$18;
	}

	public void setRowDbClick(String paramString) {
		this._$18 = paramString;
	}

	public String getCellDbClick() {
		return this._$17;
	}

	public void setCellDbClick(String paramString) {
		this._$17 = paramString;
	}

	public String getKeydown() {
		return this._$16;
	}

	public void setKeydown(String paramString) {
		this._$16 = paramString;
	}

	public String getLoad() {
		return this._$14;
	}

	public void setLoad(String paramString) {
		this._$14 = paramString;
	}

	public String getAftereditCols() {
		return this._$9;
	}

	public void setAftereditCols(String paramString) {
		this._$9 = paramString;
	}

	public String getGroupSort() {
		return this._$26;
	}

	public void setGroupSort(String paramString) {
		this._$26 = paramString;
	}

	public String getHasRightMenu() {
		return this._$4;
	}

	public void setHasRightMenu(String paramString) {
		this._$4 = paramString;
	}

	public String getHasAllRightMenu() {
		return this._$3;
	}

	public void setHasAllRightMenu(String paramString) {
		this._$3 = paramString;
	}
}
