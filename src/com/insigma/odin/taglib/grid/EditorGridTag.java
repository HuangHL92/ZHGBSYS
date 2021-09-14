package com.insigma.odin.taglib.grid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import com.insigma.odin.taglib.util.*;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.commform.ObjectUtil;

import org.apache.struts.util.ResponseUtils;

public class EditorGridTag extends BodyTagSupport {

	private String width;
	private String height;
	private String title;
	private String applyTo;
	private String autoExpandCol;
    private String forceNoScroll;
	private String property;
	private String autoFill;
	private String collapsible;
	private String collapsed;
	private String clicksToEdit;
	private String isFirstLoadData;
	private String grouping;
	private String groupCol;
	private String groupSort;
	
	//是否可以拖拽
	private String enableDragDrop;
	public String getEnableDragDrop() {
		return enableDragDrop;
	}
	public void setEnableDragDrop(String enableDragDrop) {
		this.enableDragDrop = enableDragDrop;
	}
	
	
	private String counting;  
	private String filterFunc;
	private String sm="row"; //selectModel:可以为row,cell,checkbox
	//表格top工具栏
	private String topBarId;
	//表格底部工具栏
	private String bbarId;
	//编辑表格事件群
	private String cellmousedown;
	private String afteredit;
	private String rowDbClick;
	private String cellDbClick;
	private String keydown;
	//数据也可通过此地址获取
	private String url;   
	//加载数据之后事件
	private String load;
	//一页多少条数据
	private String pageSize;   
	//加载数据之前事件
	private String beforeload;
	//开始编辑之前
	private String beforeedit;
	private String inputName;
	private String aftereditCols; //某行里的此列编辑后方可触发afteredit事件，如果无则每列编辑完都会触发
	//右键菜单ID
	private String rightMenuId;
	//远程排序
	private String remoteSort;
	//是否使用buffer方式显示表格数据
	private String bufferView;
	//使用bufferView时一次视图缓存多少行数据
	private String cacheSize;
	//是否有右键功能
	private String hasRightMenu = "true";
	//是否有全部功能的右键
	private String hasAllRightMenu = "true";
	
	//new add 2013.3.15
	private String isAlertSaveAfterPageChanging; //翻页时是否弹出保存确认框，进行保存确认，确定后默认触发页面的doGridSave(gridId)函数
	private String saveAfterPageChangingMsg; //翻页保存确认提示信息内容
	
	public String getIsAlertSaveAfterPageChanging() {
		return isAlertSaveAfterPageChanging;
	}
	public void setIsAlertSaveAfterPageChanging(String isAlertSaveAfterPageChanging) {
		this.isAlertSaveAfterPageChanging = isAlertSaveAfterPageChanging;
	}
	public String getSaveAfterPageChangingMsg() {
		return saveAfterPageChangingMsg;
	}
	public void setSaveAfterPageChangingMsg(String saveAfterPageChangingMsg) {
		this.saveAfterPageChangingMsg = saveAfterPageChangingMsg;
	}
	public String getBufferView() {
		return bufferView;
	}
	public void setBufferView(String bufferView) {
		this.bufferView = bufferView;
	}
	public String getCacheSize() {
		return cacheSize;
	}
	public void setCacheSize(String cacheSize) {
		this.cacheSize = cacheSize;
	}
	public String getRemoteSort() {
		return remoteSort;
	}
	public void setRemoteSort(String remoteSort) {
		this.remoteSort = remoteSort;
	}
	public String getRightMenuId() {
		return rightMenuId;
	}
	public void setRightMenuId(String rightMenuId) {
		this.rightMenuId = rightMenuId;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public String getGrouping() {
		return grouping;
	}
	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}
	public String getGroupCol() {
		return groupCol;
	}
	public void setGroupCol(String groupCol) {
		this.groupCol = groupCol;
	}
	public String getCounting() {
		return counting;
	}
	public void setCounting(String counting) {
		this.counting = counting;
	}
	public String getAutoFill() {
		return autoFill;
	}
	public void setAutoFill(String autoFill) {
		this.autoFill = autoFill;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	@Override
	public int doEndTag() throws JspException {
		StringBuffer buffer = new StringBuffer();
		if(this.grouping==null){
		    buffer.append("var ds = new Ext.data.Store({");
		    buffer.append("reader: reader,");
		    buffer.append("baseParams: {cueGridId:'" + this.property + "'},");
		}else{
			buffer.append("var ds = new Ext.data.GroupingStore({");
			buffer.append("reader: reader,");
			buffer.append("baseParams: {cueGridId:'" + this.property + "'},");
			buffer.append("sortInfo:{field: '"+this.groupCol+"', direction: \""+(this.groupSort==null?"ASC":this.groupSort)+"\"},");
		    buffer.append("groupField:'"+this.groupCol+"',");
		}
		if(this.url!=null&&!this.url.equals("")){
			HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
	        this.url = request.getContextPath() + this.url;
			buffer.append("url: '"+this.url+"'");
		}else{
			buffer.append("data: gridData,");
			buffer.append("proxy:new Ext.data.MemoryProxy(gridData)");
		}
		if(this.remoteSort!=null&&!this.remoteSort.equals("")){
			buffer.append(",remoteSort:"+this.remoteSort);
		}
		//buffer.append(",autoLoad:false");
		buffer.append("});");                                            
		buffer.append(this.beforeload==null?"":("ds.on('beforeload',"+this.beforeload+");"));
		buffer.append(this.load==null?"ds.on('load',odin.doCommLoad);":("ds.on('load',function(a,b,c){odin.doCommLoad(a,b,c);"+this.load+"(a,b,c);});"));
		buffer.append(TagUtil.getFilterFuncInfo("Ext.getCmp('"+this.property+"')",this.filterFunc)); 
		buffer.append("var grid_"+this.property+" = new Ext.grid.EditorGridPanel({");
		buffer.append("ds:ds,");    
		buffer.append("cm:colModel,");
		buffer.append((sm==null||sm.equals(""))?"":"selModel:sm,");  
		this.clicksToEdit = (this.clicksToEdit==null?TagConst.GRID_CLICKSTOEDIT:this.clicksToEdit);
		if(this.clicksToEdit.equals("true"))
		{
			buffer.append("clicksToEdit: 1,");
		}
		buffer.append("id:'"+this.property+"',");
		buffer.append("");
		buffer.append("viewConfig: {forceFit:"+(this.forceNoScroll==null?TagConst.GRID_FORCE_NOSCROLL:this.forceNoScroll)+",autoFill:"+(this.autoFill==null?TagConst.GRID_AUTOFILL:this.autoFill)+"},");
		if(!TagUtil.autoWidthSetting(buffer,this.property)){  // jinwei  add  2011.7.11为自适应宽度100%而加
			buffer.append("width: "+(this.width==null?TagConst.GRID_DEFAULT_WIDTH:this.width)+",");
		}
		buffer.append("height: "+(this.height==null?TagConst.GRID_DEFAULT_HEIGHT:this.height)+",");
		buffer.append("title:'"+(this.title==null?"":this.title)+"',");
		buffer.append("loadMask: true,");
		if(this.grouping!=null)
		{
			buffer.append("view: new Ext.grid.GroupingView({");
			buffer.append("forceFit:false,autoFill:"+(this.autoFill==null?TagConst.GRID_AUTOFILL:this.autoFill)+",");
			//buffer.append("groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"Items\" : \"Item\"]})'");
			buffer.append("groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"项\" : \"项\"]})'");
			buffer.append("}),");
		}else if(this.bufferView!=null && this.bufferView.equals("true")){
			buffer.append("view: new Ext.ux.grid.BufferView({");
			buffer.append("cacheSize:"+(this.cacheSize!=null?this.cacheSize:"50")+",");
			buffer.append("forceFit:"+(this.forceNoScroll==null?TagConst.GRID_FORCE_NOSCROLL:this.forceNoScroll)+",autoFill:"+(this.autoFill==null?TagConst.GRID_AUTOFILL:this.autoFill)+",");
			buffer.append("scrollDelay: true");
			buffer.append("}),");
		}
		if(this.counting!=null)
		{
			buffer.append("plugins: new Ext.grid.GroupSummary(),");
		}
		//是否可以拖拽
		if(this.enableDragDrop!=null && "true".equals(this.enableDragDrop.trim())) {
			buffer.append("enableDragDrop:true, dragDropCallback :function(){},");
		}
		buffer.append("collapsible:"+(this.collapsible==null?TagConst.COLLAPSIBLE:this.collapsible)+",");
		buffer.append("collapsed:"+(this.collapsed==null?TagConst.COLLAPSED:this.collapsed)+",");
		buffer.append((this.topBarId==null?"":("tbar:"+TagUtil.getToolBarInfo(this.topBarId, this.pageSize)+",")));
		buffer.append((this.bbarId==null?"":("bbar:"+TagUtil.getToolBarInfo(this.bbarId, this.pageSize)+",")));
		if(this.autoExpandCol!=null){
			buffer.append("autoExpandColumn:'"+(this.autoExpandCol)+"',");
		}
		if(this.applyTo!=null&&!this.applyTo.equals("")){
		    buffer.append("renderTo:"+this.applyTo); 
		}else{
			buffer.append("renderTo:gridDiv_"+this.property);
		}
		buffer.append("});");
		
		if(this.enableDragDrop!=null && "true".equals(this.enableDragDrop.trim())) {
			buffer.append("var grid_"+this.property+"_ddrow =  new  Ext.dd.DropTarget(grid_"+this.property+".container, {");
			buffer.append("ddGroup : 'GridDD' ,copy    :  false ,");
			buffer.append(" 		notifyDrop :  function (dd, e, data) {");
			buffer.append("            var  rows = data.selections;");
			// 拖动到第几行
			buffer.append("            var  index = dd.getDragData(e).rowIndex;" + 
					"             if  ( typeof (index) ==  \"undefined\" ) {" + 
					"                 return ;" + 
					"            }" + 
							// 修改store
					"            for (i = 0; i < rows.length; i++) {" + 
					"                 var  rowData = rows[i];" + 
					"                 if (! this .copy) ds.remove(rowData);" + 
					"                ds.insert(index, rowData);" + 
					"            }" + 
					"			grid_"+this.property+".dragDropCallback();" + 
					"        }" + 
					"    });");
		}
		
		buffer.append(TagUtil.getGridEventStr(this.property,"cellmousedown" ,this.cellmousedown));
		this.afteredit = (this.afteredit==null?"odin.afterEditForEditGrid":this.afteredit);
		if(this.aftereditCols!=null){
			this.afteredit = "function(e){if( (','+'"+this.aftereditCols+"'+',').indexOf(','+e.field+',') == -1 ){ return true; }"+this.afteredit+"(e);}";
		}
		buffer.append(TagUtil.getGridEventStr(this.property,"afteredit" ,this.afteredit));
		buffer.append(TagUtil.getGridEventStr(this.property, "beforeedit", this.beforeedit));
		buffer.append(TagUtil.getGridEventStr(this.property,"rowdblclick",this.rowDbClick));
		buffer.append(TagUtil.getGridEventStr(this.property,"celldblclick",this.cellDbClick));
		buffer.append(TagUtil.getGridEventStr(this.property,"keydown",this.keydown));
		if(this.rightMenuId!=null && !this.rightMenuId.trim().equals("")){
			buffer.append(TagUtil.getGridEventStr(this.property, "rowcontextmenu", "function(grid,rowIndex,e){"
					+"e.preventDefault();" 
					+((sm!=null && sm.equals("row"))?"grid.getSelectionModel().selectRow(rowIndex);":"")
					+"odin.ext.menu.MenuMgr.get('"+this.rightMenuId+"').showAt(e.getXY());"
					+"}"));
		}
		this.isFirstLoadData = (this.isFirstLoadData==null?TagConst.GRID_ISFIRSTLOADDATA:this.isFirstLoadData);
		if(this.isFirstLoadData.equals("true")){
			if((this.topBarId!=null&&this.topBarId.toLowerCase().equals("pagetoolbar"))
					||(this.bbarId!=null&&this.bbarId.toLowerCase().equals("pagetoolbar"))){
				buffer.append("ds.load({params:{start:0, limit:"+(this.pageSize==null?GlobalNames.PAGE_SIZE:this.pageSize)+"}});");
			}else{
				buffer.append("ds.load();");
			}
		}
		if (ObjectUtil.equals(this.isAlertSaveAfterPageChanging, "true")) {
			if (this.saveAfterPageChangingMsg == null) {
				this.saveAfterPageChangingMsg = "是否保存当页数据？";
			}
			buffer.append("ds.on('beforeload', function(theds){if(theds.getModifiedRecords().length>0){if(confirm('" + this.saveAfterPageChangingMsg + "')){doGridSave('"+this.property+"');};theds.commitChanges();}});");
		} else {
			buffer.append("ds.on('beforeload', function(theds){theds.commitChanges();});");
		}
		buffer.append("odin.setGridJsonData('"+this.property+"','"+((this.inputName==null||this.inputName.equals(""))?(this.property+"Data"):this.inputName)+"');");
		buffer.append("});");
		
		buffer.append("Ext.onReady(function(){");
		if (ObjectUtil.equals(hasRightMenu, "true")) {
			buffer.append("Ext.getCmp('" + this.property + "').on('contextmenu', function(e){odin.grid.menu.gridContextmenu(e,this,'" + hasAllRightMenu + "')});"); // 增加鼠标右键
		}
		buffer.append("})");
		buffer.append("</script>");

		ResponseUtils.write(pageContext, buffer.toString());
		System.out.println(buffer.toString());
		return super.doEndTag();
	}
	@Override
	public int doStartTag() throws JspException {

		StringBuffer buffer = new StringBuffer();
		buffer.append("<div id='forView_" + this.property + "' style='width:100%;'>");
		if (this.applyTo == null || this.applyTo.equals("")) {
			buffer.append("<div id='gridDiv_" + this.property + "' ></div>");
		}
		buffer.append("</div>");
		/*if(this.applyTo==null || this.applyTo.equals("")){
		    buffer.append("<table width='100%' align='center'><tr><td><div id='gridDiv_"+this.property+"' style='width:100%'></div></td><tr/></table>");
		}*/
		String hiddenInputName = ((this.inputName==null||this.inputName.equals(""))?(this.property+"Data"):this.inputName);
		buffer.append("<input type='hidden' style='display:none' name='"+hiddenInputName+"' id='"+hiddenInputName+"' ");
		buffer.append(" value='"+(TagUtil.getPropertyValue(pageContext,hiddenInputName ))+"' ");
		buffer.append(">");
		buffer.append("<script>");
		buffer.append("Ext.onReady(function(){");
		buffer.append(TagUtil.getGridSmInfo(sm));
		//buffer.append("Ext.QuickTips.init();");
		//buffer.append(" var xg = Ext.grid;");
		ResponseUtils.write(pageContext, buffer.toString());

		return this.EVAL_BODY_INCLUDE;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getApplyTo() {
		return applyTo;
	}
	public void setApplyTo(String applyTo) {
		this.applyTo = applyTo;
	}
	public String getAutoExpandCol() {
		return autoExpandCol;
	}
	public void setAutoExpandCol(String autoExpandCol) {
		this.autoExpandCol = autoExpandCol;
	}
	public String getForceNoScroll() {
		return forceNoScroll;
	}
	public void setForceNoScroll(String forceNoScroll) {
		this.forceNoScroll = forceNoScroll;
	}
	public String getCollapsible() {
		return collapsible;
	}
	public void setCollapsible(String collapsible) {
		this.collapsible = collapsible;
	}
	public String getCellmousedown() {
		return cellmousedown;
	}
	public void setCellmousedown(String cellmousedown) {
		this.cellmousedown = cellmousedown;
	}
	public String getClicksToEdit() {
		return clicksToEdit;
	}
	public void setClicksToEdit(String clicksToEdit) {
		this.clicksToEdit = clicksToEdit;
	}
	public String getAfteredit() {
		return afteredit;             
	}
	public void setAfteredit(String afteredit) {
		this.afteredit = afteredit;
	}
	public String getTopBarId() {
		return topBarId;
	}
	public void setTopBarId(String topBarId) {
		this.topBarId = topBarId;
	}
	public String getBbarId() {
		return bbarId;
	}
	public void setBbarId(String bbarId) {
		this.bbarId = bbarId;
	}
	public String getSm() {
		return sm;
	}
	public void setSm(String sm) {
		this.sm = sm;
	}
	public String getFilterFunc() {
		return filterFunc;
	}
	public void setFilterFunc(String filterFunc) {
		this.filterFunc = filterFunc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIsFirstLoadData() {
		return isFirstLoadData;
	}
	public void setIsFirstLoadData(String isFirstLoadData) {
		this.isFirstLoadData = isFirstLoadData;
	}
	public String getCollapsed() {
		return collapsed;
	}
	public void setCollapsed(String collapsed) {
		this.collapsed = collapsed;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getBeforeload() {
		return beforeload;
	}
	public void setBeforeload(String beforeload) {
		this.beforeload = beforeload;
	}
	public String getBeforeedit() {
		return beforeedit;
	}
	public void setBeforeedit(String beforeedit) {
		this.beforeedit = beforeedit;
	}
	public String getRowDbClick() {
		return rowDbClick;
	}
	public void setRowDbClick(String rowDbClick) {
		this.rowDbClick = rowDbClick;
	}
	public String getCellDbClick() {
		return cellDbClick;
	}
	public void setCellDbClick(String cellDbClick) {
		this.cellDbClick = cellDbClick;
	}
	public String getKeydown() {
		return keydown;
	}
	public void setKeydown(String keydown) {
		this.keydown = keydown;
	}
	public String getLoad() {
		return load;
	}
	public void setLoad(String load) {
		this.load = load;
	}
	public String getAftereditCols() {
		return aftereditCols;
	}
	public void setAftereditCols(String aftereditCols) {
		this.aftereditCols = aftereditCols;
	}
	public String getGroupSort() {
		return groupSort;
	}
	public void setGroupSort(String groupSort) {
		this.groupSort = groupSort;
	}
	public String getHasRightMenu() {
		return hasRightMenu;
	}
	public void setHasRightMenu(String hasRightMenu) {
		this.hasRightMenu = hasRightMenu;
	}
	public String getHasAllRightMenu() {
		return hasAllRightMenu;
	}
	public void setHasAllRightMenu(String hasAllRightMenu) {
		this.hasAllRightMenu = hasAllRightMenu;
	}
	
}
