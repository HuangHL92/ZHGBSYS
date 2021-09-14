package com.insigma.odin.taglib.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.taglib.grid.EditorGridTag;
import com.insigma.odin.taglib.grid.GridTag;
import com.insigma.odin.taglib.grid.TreeEditorGridTag;

/**
 * 2007-11-24
 * @author jinwei
 *
 */
public class TagUtil {

	/**
	 * 获取label信息
	 * @param label
	 * @param isRequired
	 * @return
	 */
    public static String  getLabelString(String label,String isRequired)
    {
    	String labelInfo ="";
    	if(label!=null&&!label.trim().equals(""))
    	{
    		labelInfo += "";
    		if(isRequired!=null&&isRequired.equals("true"))
    		{
    			labelInfo += "<font color=red>*</font>";
    		}
    		labelInfo += label + "";
    	}
    	return labelInfo;
    }
    /**
     * 处理当需要自动设置表格等面板宽度时使用此方法（自动设置宽度为100%，且能随着窗口大小变化而自适应）
     * @date 2011-7-11  add   2013.3.21更新，原来的bodyStyle有缺陷，该采用此方式
     * @author jinwei
     * @param buffer
     * @param property 当前标签的ID
     * @return  true 表示宽度是自动适应的，不需要再设置，否则需要再设置
     */
    public static boolean autoWidthSetting(StringBuffer buffer,String property){
    	String autoWidth = GlobalNames.sysConfig.get("AUTO_WIDTH");
    	if(autoWidth!=null && autoWidth.equals("1")){
    		//buffer.append("bodyStyle:'width:100%',");
    		buffer.append("monitorResize:true,doLayout:function(){var el=Ext.get('forView_" + property + "').dom;if(Ext.isIE){this.setWidth(0)};var width=el.offsetWidth;while(width<=0){if(el.parentElement){el=el.parentElement;width=el.offsetWidth-2}else{width=787}};this.setWidth(width);Ext.grid.EditorGridPanel.prototype.doLayout.call(this);},");
    		return true;
    	}
    	return false;
    }
    
    /**
     * 生成属性表格的特殊编辑模型（像select等）
     * @param type
     * @param map
     * @return
     */
    public static String getPropertyEditorInfo(String type,HashMap map)
    {
    	String rtn = "";
    	String format = (String)map.get("format");
    	String id = (String)map.get("id");
    	type = (type==null?"string":type);
    	if(type.equals("string"))
    	{
    		rtn = "new Ext.grid.GridEditor(new Ext.form.TextField({selectOnFocus:true}))"; 
    	}else if(type.equals("date"))
    	{
    		rtn = "new Ext.grid.GridEditor(new Ext.form.DateField({format:'"+(format==null?TagConst.DATE_DEFAULT_FORMAT:format)+"',selectOnFocus:true}))";
    	}else if(type.equals("bool"))
    	{
    		rtn = " new Ext.grid.GridEditor("+(id==null?"date_bfield":("date_"+id))+")";
    	}else if(type.equals("number"))
    	{
    		rtn = "new Ext.grid.GridEditor(new Ext.form.NumberField({selectOnFocus:true, style:'text-align:left;'}))";
    	}else if(type.equals("select"))
    	{
    		rtn = "new Ext.grid.GridEditor("+(id==null?"select_bfield":("select_"+id))+")";
    	}
    	return rtn;
    }
    
    /**
     * 用来生成属性表格的常用编辑模型
     * @param type
     * @param value
     * @return
     */
    public static String getPropertyColInfo(String type,String value)
    {
    	String rtn = "";
    	type = (type==null?"string":type);
    	if(type.equals("string"))
    	{
    		rtn = "\""+value+"\""; 
    	}else if(type.equals("date"))
    	{
    		rtn = "new Date(Date.parseDate('"+value+"','"+TagConst.DATE_DEFAULT_FORMAT+"'))";
    	}else if(type.equals("bool"))
    	{
    		rtn = value;
    	}else if(type.equals("number"))
    	{
    		rtn = value;
    	}else if(type.equals("select"))
    	{
    		rtn = value;
    	}
    	return rtn;
    }
    /**
     * 生成编辑表格的编辑列模型
     * @param editor
     * @param map
     * @return
     */
    public static String getEditColInfo(String editor,HashMap<String,String> map)
    {
    	StringBuffer buffer = new StringBuffer();
    	String required = (String)map.get("required");
    	String mask = (String)map.get("mask");
    	String decimalPrecision = (String)map.get("decimalPrecision");
    	String format = (String)map.get("format");
		String maxLength = (String)map.get("maxLength");
		String maxValue = (String)map.get("maxValue");
		String minLength = (String)map.get("minLength");
		String minValue = (String)map.get("minValue");
		String id = (String)map.get("editorId");
		String upperOrLower = map.get("upperOrLower");
		String onKeyDown = map.get("onKeyDown");
		String edited = map.get("edited");
    	buffer.append(" editor: new Ext.form.");
    	if(editor.equals("text"))
    	{
    		buffer.append("TextField({");
    		buffer.append(maxLength==null?"":("maxLength:"+maxLength+","));
    		buffer.append(minLength==null?"":("minLength:"+minLength+","));
    		buffer.append(getVtype(mask));
    		buffer.append(getRequired(required));
    	}else if(editor.equals("date"))
    	{
    		buffer.append("DateField({");
    		buffer.append(maxLength==null?"":("maxLength:"+maxLength+","));
    		buffer.append(minLength==null?"":("minLength:"+minLength+","));
    		buffer.append(maxValue==null?"":("maxValue:"+maxValue+","));
    		buffer.append(minValue==null?"":("minValue:"+minValue+","));
    		buffer.append(getDateFormat(format));
    		buffer.append(getRequired(required));
    	}else if(editor.equals("number"))
    	{
    		buffer.append("NumberField({");
    		buffer.append(maxLength==null?"":("maxLength:"+maxLength+","));
    		buffer.append(minLength==null?"":("minLength:"+minLength+","));
    		buffer.append(maxValue==null?"":("maxValue:"+maxValue+","));
    		buffer.append(minValue==null?"":("minValue:"+minValue+","));
    		buffer.append(getDecimalPrecision(decimalPrecision));
    		buffer.append(getRequired(required));
    	}else if(editor.equals("select"))
    	{
    		String pageSize = map.get("pageSize");
			String url = map.get("url");
			String hideTrigger = map.get("hideTrigger");
			String minChars = map.get("minChars");
			String displayField = map.get("displayField");
			String tpl = map.get("tpl");
			String onSelect =  map.get("onSelect");
			String store = map.get("store");
			String maxHeight = map.get("maxHeight");
			String queryDelay = map.get("queryDelay");
    		buffer.append("ComboBox({");
    		if(store!=null&&!store.equals("")){
    			buffer.append("store:"+store+",");
    		}else{
	    		buffer.append("store: new Ext.data.");
	    		if(url!=null&&!url.trim().equals("")){
	    	        buffer.append("Store({");
	    			buffer.append("url:'"+url+"',");
	    			buffer.append("reader:new Ext.data.JsonReader({root: 'data',totalProperty: 'totalCount',id: 'key'},[{name: 'key'},{name: 'value'}])");
	    		}else{
	    			buffer.append("SimpleStore({");
	    			buffer.append("fields: ['key', 'value'],data:");	
	    			String data = (String)map.get("selectData");
	        		data = (data==null?"":data);
	        		buffer.append("["+data+"]");
	        		buffer.append(",createFilterFn:odin.createFilterFn");
	    		}
	    		buffer.append("}),");
    		}
    		buffer.append("displayField:'"+(displayField==null?"value":displayField)+"',");
    		buffer.append((tpl==null?"":("tpl: "+tpl+",")));
    		buffer.append("typeAhead: false,");
    		buffer.append("mode: '"+((url==null&&store==null)?"local":"remote")+"',");
    		buffer.append("triggerAction: 'all',");
    		
    		if(id!=null&&!id.equals("")){
    			//String dataIndex = (String)map.get("dataIndex");
        		buffer.append("  id:'"+id+"_grid_combo', ");
        		buffer.append("valueField:'key',");
                buffer.append("hiddenName :'"+id+"_grid',");
                buffer.append("hiddenId :'"+id+"_grid',");
        	}
    		
        	buffer.append("editable:true,");
        	buffer.append((queryDelay==null||queryDelay.equals("")?"":("queryDelay:"+queryDelay+",")));
        	buffer.append("selectOnFocus:true,"); 
        	buffer.append((maxHeight==null||maxHeight.equals(""))?"":("maxHeight:"+maxHeight+","));
        	if(upperOrLower!=null&&upperOrLower.equals("0")){
        		buffer.append("cls:'lower',");
        	}else if(upperOrLower!=null&&upperOrLower.equals("1")){
        		buffer.append("cls:'upper',");
        	}
        	buffer.append("hideTrigger:"+(hideTrigger==null?"false":hideTrigger)+",");
    		if(pageSize==null&&url!=null){
    			buffer.append("pageSize:"+GlobalNames.PAGE_SIZE+",");
    			buffer.append(minChars==null?"":("minChars:"+minChars+","));
    			buffer.append("itemSelector: 'div.search-item',");
    		}else if(url!=null&&pageSize!=null){
    			buffer.append("pageSize:"+pageSize+",");
    			buffer.append(minChars==null?"":("minChars:"+minChars+","));
    			buffer.append("itemSelector: 'div.search-item',");
    		}else if(store!=null&&!store.equals("")){
    			buffer.append("pageSize:"+(pageSize==null?GlobalNames.PAGE_SIZE:pageSize)+",");
    			buffer.append(minChars==null?"":("minChars:"+minChars+","));
    			buffer.append("itemSelector: 'div.search-item',");
    		}
    		//buffer.append(onSelect==null?"":("onSelect:"+onSelect+","));
    		if(onSelect!=null&&!onSelect.equals("")){
    			buffer.append(" onSelect : function(record, index){");
    			buffer.append("if(this.fireEvent('beforeselect', this, record, index) !== false){");
				buffer.append(" this.setValue(record.data[this.valueField || this.displayField]); ");
				buffer.append(" this.collapse();");  
				buffer.append(" this.fireEvent('select', this, record, index);");    				
				buffer.append(onSelect+"(record,index);");  
    			buffer.append(" }");
    			buffer.append("},");
    		}
    		buffer.append("expand:function(){odin.setListWidth(this);Ext.form.ComboBox.prototype.expand.call(this);},");
        	buffer.append(maxLength==null?"":("maxLength:"+maxLength+","));
    		buffer.append(minLength==null?"":("minLength:"+minLength+","));
        	buffer.append(getRequired(required));
    	}else if (editor.equals("selectTree"))
        {
    		buffer = new StringBuffer();
    		buffer.append(" editor: new Ext.ux.form.");
    		String pageSize = map.get("pageSize");
			String url = map.get("url");
			String hideTrigger = map.get("hideTrigger");
			String minChars = map.get("minChars");
			String displayField = map.get("displayField");
			String tpl = map.get("tpl");
			String onSelect =  map.get("onSelect");
			String store = map.get("store");
			String maxHeight = map.get("maxHeight");
			String queryDelay = map.get("queryDelay");
			String GridId = map.get("GridId");
			String ischecked = map.get("ischecked");
			
	        String selectData = (String)map.get("selectData");
	        
	        
	        
	        
    		buffer.append("ComboBoxWidthTree({");
    		
    		if(selectData!=null&&!"".equals(selectData)) {
	        	buffer.append("store: new Ext.data.");
	        	buffer.append("SimpleStore({");
    			buffer.append("fields: ['key', 'value'],data:");	
        		buffer.append("["+selectData+"]");
        		//buffer.append(",createFilterFn:odin.createFilterFn");
	    		buffer.append("}),");
	        }
    		/*
    		if(store!=null&&!store.equals("")){
    			buffer.append("store:"+store+",");
    		}else{
	    		buffer.append("store: new Ext.data.");
	    		if(url!=null&&!url.trim().equals("")){
	    	        buffer.append("Store({");
	    			buffer.append("url:'"+url+"',");
	    			buffer.append("reader:new Ext.data.JsonReader({root: 'data',totalProperty: 'totalCount',id: 'key'},[{name: 'key'},{name: 'value'}])");
	    		}else{
	    			buffer.append("SimpleStore({");
	    			buffer.append("fields: ['key', 'value'],data:");	
	    			String data = (String)map.get("selectData");
	        		data = (data==null?"":data);
	        		buffer.append("["+data+"]");
	        		buffer.append(",createFilterFn:odin.createFilterFn");
	    		}
	    		buffer.append("}),");
    		}*/
    		//buffer.append("displayField:'"+(displayField==null?"value":displayField)+"',");
    		//buffer.append((tpl==null?"":("tpl: "+tpl+",")));
    		buffer.append("displayField:'value',");
    		buffer.append("valueField:'key',");
    		buffer.append("selectStore:'"+(String)map.get("selectStore")+"',");
            String dataIndex = (String)map.get("dataIndex");//+"_id";
            buffer.append("property: '"+id+"',");
            buffer.append("id:'"+id+"_combotree',");
            buffer.append("tpl: '<div style=\"height:200px;\"><div id=\""+id+"_treePanel\"></div></div>',");
            buffer.append("codetype:'"+(String)map.get("codetype")+"',");
            buffer.append("codename:'',");
            buffer.append("gridId:'"+GridId+"',");
            buffer.append("valueField:'key',");
            buffer.append("hiddenName :'"+id+"',");
            buffer.append("hiddenId :'"+id+"',");
            if(!"false".equals(ischecked)){
            	buffer.append("ischecked :"+ischecked+",");
            }
            
            
    		//id = dataIndex+"_combotree";
    		
    		buffer.append("typeAhead: false,");
    		buffer.append("mode: '"+((url==null&&store==null)?"local":"remote")+"',");
    		buffer.append("triggerAction: 'all',");
        	buffer.append("editable:true,");
        	buffer.append((queryDelay==null||queryDelay.equals("")?"":("queryDelay:"+queryDelay+",")));
        	buffer.append("selectOnFocus:true,"); 
        	buffer.append((maxHeight==null||maxHeight.equals(""))?"":("maxHeight:"+maxHeight+","));
        	if(upperOrLower!=null&&upperOrLower.equals("0")){
        		buffer.append("cls:'lower',");
        	}else if(upperOrLower!=null&&upperOrLower.equals("1")){
        		buffer.append("cls:'upper',");
        	}
        	buffer.append("hideTrigger:"+(hideTrigger==null?"false":hideTrigger)+",");
    		if(pageSize==null&&url!=null){
    			buffer.append("pageSize:"+GlobalNames.PAGE_SIZE+",");
    			buffer.append(minChars==null?"":("minChars:"+minChars+","));
    			buffer.append("itemSelector: 'div.search-item',");
    		}else if(url!=null&&pageSize!=null){
    			buffer.append("pageSize:"+pageSize+",");
    			buffer.append(minChars==null?"":("minChars:"+minChars+","));
    			buffer.append("itemSelector: 'div.search-item',");
    		}else if(store!=null&&!store.equals("")){
    			buffer.append("pageSize:"+(pageSize==null?GlobalNames.PAGE_SIZE:pageSize)+",");
    			buffer.append(minChars==null?"":("minChars:"+minChars+","));
    			buffer.append("itemSelector: 'div.search-item',");
    		}
    		//buffer.append(onSelect==null?"":("onSelect:"+onSelect+","));
    		if(onSelect!=null&&!onSelect.equals("")){
    			buffer.append(" onSelect : function(record, index){");
    			buffer.append("if(this.fireEvent('beforeselect', this, record, index) !== false){");
				buffer.append(" this.setValue(record.data[this.valueField || this.displayField]); ");
				buffer.append(" this.collapse();");  
				buffer.append(" this.fireEvent('select', this, record, index);");    				
				buffer.append(onSelect+"(record,index);");  
    			buffer.append(" }");
    			buffer.append("},");
    		}
    		buffer.append("expand:function(){odin.setListWidth(this);Ext.form.ComboBox.prototype.expand.call(this);},");
        	buffer.append(maxLength==null?"":("maxLength:"+maxLength+","));
    		buffer.append(minLength==null?"":("minLength:"+minLength+","));
        	buffer.append(getRequired(required));
          }
    	else if(editor.equals("checkbox"))
    	{
    		buffer.append("Checkbox({");  
    		buffer.append("inputType:''");
    	}
    	String selectOnFocus = map.get("selectOnFocus");
		buffer.append((selectOnFocus==null||selectOnFocus.equals(""))?"":(",selectOnFocus:"+selectOnFocus));
		/*
		 * if(id!=null&&!id.equals("")&&!editor.equals("select")){
		 * buffer.append(" , id:'"+id+"' "); }
		 */
    	if(onKeyDown==null||onKeyDown.equals("")){
    		buffer.append(",fireKey:odin.doAccSpecialkey");
    	}else{
    		buffer.append(",fireKey:"+onKeyDown);
    	}
    	buffer.append("})");
    	buffer.append("");
    	return buffer.toString();
    }

    public static String getVtype(String mask)
    {
    	String rtn = "";
    	if(mask!=null)
    	{
    		rtn="vtype:'"+mask+"',";
    	}
    	return rtn;
    }

    public static String getRequired(String required)
    {
    	String rtn = "";
    	if(required!=null && "true".equals(required))
    	{
    		rtn = "allowBlank:false ";
    	}else{
    		rtn = "allowBlank:true ";
    	}
    	return rtn;
    }

    public static String getDateFormat(String format)
    {
    	String rtn = "";
    	if(format!=null)
    	{
    		rtn = "format:'"+format+"', ";
    	}else{
    		rtn = "format:'"+TagConst.DATE_DEFAULT_FORMAT+"', ";
    	}
    	return rtn;
    }

    public static String getDecimalPrecision(String decimalPrecision)
    {
    	String rtn = "";
    	if(decimalPrecision!=null)
    	{
    		rtn = "decimalPrecision:"+decimalPrecision+",";
    	}
    	return rtn;
    }

    /**
     * 产生一个唯一的值
     * @return
     */
    public static String getId(int length) {

		Random random = new Random();
		StringBuffer buffer = new StringBuffer(length);
		int iRandom1;
		int iRandom2;
		for (int i = 0; i < length; i++) {
			iRandom1 = random.nextInt(4);
			if (iRandom1 != 3) {
				iRandom2 = random.nextInt(25);
				buffer.append((char) ('a' + iRandom2));
			} else {
				iRandom2 = random.nextInt(9);
				buffer.append((char) ('0' + iRandom2));
			}
		}
		return buffer.toString();

	}
    
    public static String getPropertyValue(PageContext pagecontext, String s)
    {
        Object obj = null;
        try
        {
            obj = RequestUtils.lookup(pagecontext, "org.apache.struts.taglib.html.BEAN", s, null);
        }
        catch(JspException _ex)
        {
            obj = "";
        }
        if(obj == null)
            obj = "";
        return ResponseUtils.filter(obj.toString());
    }
    
    public static String getGridEventStr(String id,String eventName,String func)
    {
    	String rtn = "";
    	if(func!=null){
    		rtn += "Ext.getCmp('"+id+"').addListener('"+eventName+"'," + func + ");";
    	}
    	return rtn;
    }
    /**
     * 获得grid的选择模型
     * @param smType
     * @return
     */
    public static String getGridSmInfo(String smType){
    	String rtn = "";
    	if(smType==null){ 
    		smType = "";
    	}
    	if(smType.equals("row")){
    		rtn = "var sm = new Ext.grid.RowSelectionModel({onEditorKey:odin.doAccOnEditorKey});";
    	}else if(smType.equals("cell")){
    		rtn = "var sm = new Ext.grid.CellSelectionModel({onEditorKey:odin.doAccOnEditorKey});";
    	}else if(smType.equals("checkbox")){
    		rtn = "var sm = new Ext.grid.CheckboxSelectionModel({onEditorKey:odin.doAccOnEditorKey});";
    	}else{
    		rtn = "var sm = new Ext.grid.RowSelectionModel({onEditorKey:odin.doAccOnEditorKey});";
    	}
    	return rtn;
    }
    /**
     * 将一个这样的"['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']"字符转化成list
     * @param array
     * @return
     */
    public static List arrayToList(String array){
		List codeList=new ArrayList();
		List codeGroupList=new ArrayList();
		String temp=array;
		int idx1=temp.indexOf('[');
		int idx2=temp.indexOf(']');
		while(idx1!=-1&&idx2!=-1&&idx2>idx1){
			String codeGroup=temp.substring(idx1+1, idx2);
			codeGroupList.add(codeGroup);
			temp=temp.substring(idx2+1);
			idx1=temp.indexOf('[');
			idx2=temp.indexOf(']');
		}
		
		for(int i=0;i<codeGroupList.size();i++){
			String codeGroup=(String)codeGroupList.get(i);
			String[] codes=codeGroup.split(",");
			idx1=codes[0].indexOf('\'');
			idx2=codes[0].lastIndexOf('\'');
			String code=codes[0].substring(idx1+1,idx2);
			idx1=codes[1].indexOf('\'');
			idx2=codes[1].lastIndexOf('\'');
			String value=codes[1].substring(idx1+1,idx2);
			Map map = new HashMap();
			map.put("name", code);
			map.put("value", value);
			codeList.add(map);
		}
		
		return codeList;   
	}
    public static String getFilterFuncInfo(String grid,String func){
    	String info = "";
    	if(func!=null&&!func.equals("")){    
    		info += "ds.on('load', function(ds){odin.doFilterGridCueData("+grid+","+func+");});";
    	}
    	return info;
    }
    /**
     * 根据id获取toolbar信息
     * @param toolBarId
     * @return
     */
    public static String getToolBarInfo(String toolBarId,String pageSize){
    	String id = toolBarId.toLowerCase();
    	if(id.equals("pagetoolbar")){
    		return "new Ext.PagingToolbar({pageSize: "
    		          +((pageSize==null||pageSize.trim().equals(""))?GlobalNames.PAGE_SIZE:pageSize)
    		          +",store: ds,displayInfo: true })";
    	}else{
    		return "Ext.getCmp('"+toolBarId+"')";
    	}
    }
    /**
     * 人为的给各输入框增加两属性，主要用作统一非空校验
     * @param label
     * @param required
     * @return
     */
    public static String getNewPropertysStr(String label,String required){
    	/***给struts的text标签增加两个属性****/
		String newProps = "\" ";
		newProps += "label=\""+(label==null?"":label)+"\" ";
		newProps += "required=\""+(required==null?"false":required);
		return newProps;
		
    }
    /**
     * 根据input的格式化串，处理其掩码输入控制
     * @param format
     * @return
     */
    public static String getInputMaskStr(String format){
    	StringBuffer buff = new StringBuffer();
    	buff.append("\" ");
    	if("Y-m-d".equals(format)){
    		buff.append("placeholder=\"0000-00-00\" onkeyup=\"odin.commInputMask(this,'"+format+"')");
    	}
    	if("Y-m-d H:i:s".equals(format)){
    		buff.append("placeholder=\"0000-00-00 00:00:00\" onkeyup=\"odin.commInputMask(this,'"+format+"')");
    	}
    	if("Y-m-d H:i".equals(format)){
    		buff.append("placeholder=\"0000-00-00 00:00\" onkeyup=\"odin.commInputMask(this,'"+format+"')");
    	}
    	
    	return buff.toString();
    }
    /**
     * 将目前已经产生好了的局部response刷新到前台去显示
     * 减少前台页面出现空白的时长———— 在延迟加载下拉时方启用
     * @author jinw
     * @date 2012-4-17
     * @param c PageContext
     */
    public static void flush(PageContext c){
    	try{
    		String selectLazyLoad = GlobalNames.sysConfig.get("SELECT_LAZYLOAD");
    		if("1".equals(selectLazyLoad)){
    			c.getResponse().flushBuffer();
    			//c.getOut().flush();
    		}
    	}catch(IOException ioe){
    		ioe.printStackTrace();
    	}
    }
    /**
     * 在表格列标签中后去其所在表格标签的ID信息，如checkbox需要表格的ID信息
     * @param gridName 列标签的表格名称信息
     * @param t 列标签
     * @return
     * <p>时间： 2013-3-22</p>
     * @author jinw
     */
    public static String getColTagGridName(String gridName,Tag t){
    	Tag p  = t.getParent();
		if(p!=null){
			Tag grid = p.getParent();
			if(grid!=null){
				if(grid instanceof GridTag){
					gridName = ((GridTag)grid).getProperty();
				}else if(grid instanceof EditorGridTag){
					gridName = ((EditorGridTag)grid).getProperty();
				}else if(grid instanceof com.insigma.odin.taglib.ss.html.grid.EditorGridTag){
					gridName = ((com.insigma.odin.taglib.ss.html.grid.EditorGridTag)grid).getProperty();
				}else if(grid instanceof com.insigma.odin.taglib.ss.html.ModuleControlTag){
					gridName = getColTagGridName(gridName,grid);
				}else if(grid instanceof com.insigma.odin.taglib.grid.TreeEditorGridTag){
					gridName = ((TreeEditorGridTag)grid).getProperty();
				}
			}
		}
    	return gridName;
    }
}
