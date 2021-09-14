package com.insigma.siis.local.business.helperUtil;

import com.insigma.odin.framework.comm.CodeManager;
import com.insigma.odin.taglib.grid.GridTag;
import com.insigma.odin.taglib.grid.TreeEditorGridTag;
import com.insigma.odin.taglib.ss.html.ModuleControlTag;
import com.insigma.odin.taglib.util.TagConst;
import com.insigma.odin.taglib.util.TagUtil;
import com.insigma.siis.local.pagemodel.yngwyUtil.CodeValueUtilPageModel;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.struts.util.ResponseUtils;
import org.freehep.graphicsio.swf.SWFAction.SetProperty;

import javax.servlet.jsp.tagext.Tag;
public class GridEditColumnTag extends BodyTagSupport
{
  private String _$43;
  private String hideable;
  private Boolean locked;
  private Boolean ischecked;
  public Boolean getIschecked() {
	return ischecked;
}

public void setIschecked(Boolean ischecked) {
	this.ischecked = ischecked;
}

private Boolean menuDisabled;
  public Boolean getMenuDisabled() {
	return menuDisabled;
}

public void setMenuDisabled(Boolean menuDisabled) {
	this.menuDisabled = menuDisabled;
}

public Boolean getLocked() {
	return locked;
}

public void setLocked(Boolean locked) {
	this.locked = locked;
}

private String _$42;
  private String _$41;
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
  private String _$25 = "true";
  private String _$24;
  private String _$23;
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
  private String _$4;
  private String _$3;
  private String _$2 = "";
  private String _$1 = "false";

  public String getRendererAlt()
  {
    return this._$30;
  }

  public void setRendererAlt(String paramString)
  {
    this._$30 = paramString;
  }

  public String getEnterAutoAddRow()
  {
    return this._$1;
  }

  public void setEnterAutoAddRow(String paramString)
  {
    this._$1 = paramString;
  }

  public String getCheckBoxClick()
  {
    return this._$4;
  }

  public void setCheckBoxClick(String paramString)
  {
    this._$4 = paramString;
  }

  public String getCheckBoxSelectAllClick()
  {
    return this._$3;
  }

  public void setCheckBoxSelectAllClick(String paramString)
  {
    this._$3 = paramString;
  }

  public String getColorExp()
  {
    return this._$2;
  }

  public void setColorExp(String paramString)
  {
    this._$2 = paramString;
  }

  public String getOnKeyDown()
  {
    return this._$5;
  }

  public void setOnKeyDown(String paramString)
  {
    this._$5 = paramString;
  }

  public String getMaxHeight()
  {
    return this._$6;
  }

  public void setMaxHeight(String paramString)
  {
    this._$6 = paramString;
  }

  public String getSelectOnFocus()
  {
    return this._$8;
  }

  public void setSelectOnFocus(String paramString)
  {
    this._$8 = paramString;
  }

  public String getOnSelect()
  {
    return this._$11;
  }

  public void setOnSelect(String paramString)
  {
    this._$11 = paramString;
  }

  public String getPageSize()
  {
    return this._$17;
  }

  public void setPageSize(String paramString)
  {
    this._$17 = paramString;
  }

  public String getUrl()
  {
    return this._$16;
  }

  public void setUrl(String paramString)
  {
    this._$16 = paramString;
  }

  public String getHideTrigger()
  {
    return this._$15;
  }

  public void setHideTrigger(String paramString)
  {
    this._$15 = paramString;
  }

  public String getMinChars()
  {
    return this._$14;
  }

  public void setMinChars(String paramString)
  {
    this._$14 = paramString;
  }

  public String getFormat()
  {
    return this._$18;
  }

  public void setFormat(String paramString)
  {
    this._$18 = paramString;
  }

  public String getDecimalPrecision()
  {
    return this._$23;
  }

  public void setDecimalPrecision(String paramString)
  {
    this._$23 = paramString;
  }

  public String getMaxLength()
  {
    return this._$22;
  }

  public void setMaxLength(String paramString)
  {
    this._$22 = paramString;
  }

  public String getMinLength()
  {
    return this._$21;
  }

  public void setMinLength(String paramString)
  {
    this._$21 = paramString;
  }

  public String getMaxValue()
  {
    return this._$20;
  }

  public void setMaxValue(String paramString)
  {
    this._$20 = paramString;
  }

  public String getMinValue()
  {
    return this._$19;
  }

  public void setMinValue(String paramString)
  {
    this._$19 = paramString;
  }

  public String getAlign()
  {
    return this._$24;
  }

  public void setAlign(String paramString)
  {
    this._$24 = paramString;
  }

  public String getEdited()
  {
    return this._$25;
  }

  public void setEdited(String paramString)
  {
    this._$25 = paramString;
  }

  public String getCodeType()
  {
    return this._$27;
  }

  public void setCodeType(String paramString)
  {
    this._$27 = paramString;
  }

  public String getFilter()
  {
    return this._$26;
  }

  public void setFilter(String paramString)
  {
    this._$26 = paramString;
  }

  public String getRenderer()
  {
    return this._$31;
  }

  public void setRenderer(String paramString)
  {
    this._$31 = paramString;
  }

  public String getEditor()
  {
    return this._$34;
  }

  public void setEditor(String paramString)
  {
    this._$34 = paramString;
  }

  public int doEndTag()
    throws JspException
  {
    this._$1 = "false";
    return super.doEndTag();
  }
  public String getColTagGridName(String paramString, Tag paramTag) {
		Tag localTag1 = paramTag.getParent();
		if (localTag1 != null) {
			Tag localTag2 = localTag1.getParent();
			if (localTag2 != null)
				if ((localTag2 instanceof GridTag))
					paramString = ((GridTag) localTag2).getProperty();
				else if ((localTag2 instanceof com.insigma.odin.taglib.grid.EditorGridTag))
					paramString = ((com.insigma.odin.taglib.grid.EditorGridTag) localTag2)
							.getProperty();
				else if ((localTag2 instanceof com.insigma.siis.local.business.helperUtil.EditorGridTag))
					paramString = ((com.insigma.siis.local.business.helperUtil.EditorGridTag) localTag2)
							.getProperty();
				else if ((localTag2 instanceof com.insigma.odin.taglib.ss.html.grid.EditorGridTag))
					paramString = ((com.insigma.odin.taglib.ss.html.grid.EditorGridTag) localTag2)
							.getProperty();
				else if ((localTag2 instanceof ModuleControlTag))
					paramString = getColTagGridName(paramString, localTag2);
				else if ((localTag2 instanceof TreeEditorGridTag))
					paramString = ((TreeEditorGridTag) localTag2).getProperty();
		}
		return paramString;
	}
  
  public int doStartTag()
    throws JspException
  {
    this._$40 = getColTagGridName(this._$40, this);
    if (getEditor().equals("select"))
    {
      if ((getCodeType() == null) || (getCodeType().equals("")))
        setCodeType(getDataIndex().toUpperCase());
      if ((getSelectData() == null) && (getCodeType() != null) && (!getCodeType().equals("")) && ((getUrl() == null) || (getUrl().equals(""))))
       // setSelectData(CodeManager.getArrayCode(getCodeType(), getFilter()));
    	  setSelectData(CodeValueUtilPageModel.getCodeTypeJS(getCodeType(), getFilter(),null));
    }
    if (getEditor().equals("selectTree"))
    {
      if ((getCodeType() == null) || (getCodeType().equals("")))
        setCodeType(getDataIndex().toUpperCase());
      if ((getSelectData() == null) && (getCodeType() != null) && (!getCodeType().equals("")) && ((getUrl() == null) || (getUrl().equals("")))){
    	// setSelectData(CodeManager.getArrayCode(getCodeType(), getFilter()));
    	  HttpSession session = this.pageContext.getSession();
    	  String selectStore = (String)session.getAttribute(getCodeType());
    	  if(selectStore==null){
    	  	selectStore = CodeValueUtilPageModel.getCodeTypeJS(getCodeType());
    	  	session.setAttribute(getCodeType(),selectStore);
    	  }
    	  setSelectStore(selectStore);
    	  setSelectData(CodeValueUtilPageModel.getCodeTypeJS(getCodeType(), getFilter(),null));
          
      }
       
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("{");
    if (this._$43 != null)
      localStringBuffer.append("id:'" + this._$37 + "',");
    
    if (this.locked != null&&!"".equals(this.locked)){
    	localStringBuffer.append("locked:" + this.locked + ",");
    }
    
    if (this._$41 == null)
    {
      localStringBuffer.append("header: \"" + this._$37 + "\",");
    }
    else if ((this._$34 != null) && (this._$34.equals("checkbox")) && (this._$41.toLowerCase().equals("selectall")))
    {
      localStringBuffer.append("header: \"<div class='x-grid3-check-col-td'><div class='x-grid3-check-col' alowCheck='" + ((getEdited() != null) && (getEdited().equals("false")) ? "false" : "true") + "' id='selectall_" + this._$40 + "_" + this._$37 + "' onclick='odin.selectAllFuncForE3(\\\"" + this._$40 + "\\\",this,\\\"" + this._$37 + "\\\");" + (this._$3 == null ? "" : new StringBuilder().append(this._$3).append("(\\\"").append(this._$40).append("\\\",\\\"").append(this._$37).append("\\\",this);").toString()) + "'></div></div>\",");
      this._$38 = "false";
    }
    else
    {
      localStringBuffer.append("header: \"" + this._$41 + "\",");
    }
    localStringBuffer.append("hidden:" + (this._$33 == null ? TagConst.GRID_COL_HIDDEN : this._$33) + ",");
    localStringBuffer.append("align:'" + this._$24 + "',");
    localStringBuffer.append(" width: " + (this._$39 == null ? "40" : this._$39) + ", ");
    localStringBuffer.append("sortable: " + (this._$38 == null ? "true" : this._$38) + ",");
    localStringBuffer.append("menuDisabled:" + (this.menuDisabled == null ? "false" : this.menuDisabled) + ",");
    localStringBuffer.append(" enterAutoAddRow:" + this._$1 + ",");
    if (this._$35 != null)
      localStringBuffer.append("summaryType: '" + this._$35 + "',");
    if (this._$29 != null)
      localStringBuffer.append("summaryRenderer: " + this._$29 + ",");
    if ((this._$31 == null||"".equals(this._$31)) && (this._$34 != null) && (this._$34.equals("date")))
    {
      localStringBuffer.append("renderer:function(a,b,c,d,e,f){var v=odin.renderDate(a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return " + (this._$30 == null ? "radow.renderAlt" : this._$30) + "(v,b,c,d,e,f,\"" + this._$2.replace("\"", "\\\"") + "\")},");
    }
    else if ((this._$31 == null||"".equals(this._$31)) && (this._$34 != null) && (this._$34.equals("select")))
    {
      localStringBuffer.append("renderer:function(a,b,c,d,e,f){var v=odin.doGridSelectColRender('" + getGridName() + "','" + getDataIndex() + "',a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return " + (this._$30 == null ? "radow.renderAlt" : this._$30) + "(v,b,c,d,e,f,\"" + this._$2.replace("\"", "\\\"") + "\")},");
    }
    else if ((this._$31 == null||"".equals(this._$31)) && (this._$34 != null) && (this._$34.equals("selectTree")))
    {
      localStringBuffer.append("renderer:function(a,b,c,d,e,f){var v=odin.doGridSelectColRender('" + getGridName() + "','" + getDataIndex() + "',a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return " + (this._$30 == null ? "radow.renderAlt" : this._$30) + "(v,b,c,d,e,f,\"" + this._$2.replace("\"", "\\\"") + "\")},");
    }
    else if ((this._$31 == null||"".equals(this._$31)) && (this._$34 != null) && (this._$34.equals("checkbox")))
    {
      localStringBuffer.append("renderer:");
      localStringBuffer.append("function(value, params, record,rowIndex,colIndex,ds){");
      localStringBuffer.append("var rtn = '';");
      localStringBuffer.append("params.css=' x-grid3-check-col-td';");
      localStringBuffer.append("if(value==true || value=='true'){");
      localStringBuffer.append("rtn=\"<div class='x-grid3-check-col-on' alowCheck='" + ((this._$25 != null) && (this._$25.equals("false")) ? "false" : "true") + "' onclick='odin.accCheckedForE3(this,\"+rowIndex+\",\"+colIndex+\",\\\"" + this._$37 + "\\\",\\\"" + (this._$40 == null ? "" : this._$40) + "\\\");" + (this._$4 == null ? "" : new StringBuilder().append(this._$4).append("(\"+rowIndex+\",\"+colIndex+\",\\\"").append(this._$37).append("\\\",\\\"").append(this._$40 == null ? "" : this._$40).append("\\\");").toString()) + "'></div>\";");
      localStringBuffer.append("}else{");
      localStringBuffer.append("rtn=\"<div class='x-grid3-check-col' alowCheck='" + ((this._$25 != null) && (this._$25.equals("false")) ? "false" : "true") + "' onclick='odin.accCheckedForE3(this,\"+rowIndex+\",\"+colIndex+\",\\\"" + this._$37 + "\\\",\\\"" + (this._$40 == null ? "" : this._$40) + "\\\");" + (this._$4 == null ? "" : new StringBuilder().append(this._$4).append("(\"+rowIndex+\",\"+colIndex+\",\\\"").append(this._$37).append("\\\",\\\"").append(this._$40 == null ? "" : this._$40).append("\\\");").toString()) + "'></div>\";");
      localStringBuffer.append("}");
      if ((this._$40 == null) || (this._$40.trim().equals("")))
        localStringBuffer.append("odin.checkboxds = ds;");
      localStringBuffer.append("return odin.renderEdit(rtn,params,record,rowIndex,colIndex,ds);");
      localStringBuffer.append("},");
      this._$25 = "false";
    }
    else if (this._$31 != null&&!"".equals(this._$31))
    {
      localStringBuffer.append("renderer:function(a,b,c,d,e,f){var v=" + this._$31 + "(a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return " + (this._$30 == null ? "radow.renderAlt" : this._$30) + "(v,b,c,d,e,f,\"" + this._$2.replace("\"", "\\\"") + "\")},");
    }
    else
    {
      localStringBuffer.append("renderer:function(v,b,c,d,e,f){odin.renderEdit(v,b,c,d,e,f);return " + (this._$30 == null ? "radow.renderAlt" : this._$30) + "(v,b,c,d,e,f,\"" + this._$2.replace("\"", "\\\"") + "\")},");
    }
    localStringBuffer.append(" editable:" + getEdited() + ", ");
    if ((this.hideable != null) && (!this.hideable.trim().equals(""))){
    	localStringBuffer.append(" hideable: " + this.hideable +",");
    }
    localStringBuffer.append(" dataIndex: '" + this._$37 + "'");
    
    localStringBuffer.append(",");
    HashMap localHashMap = new HashMap();
    localHashMap.put("GridId", this._$40);
    localHashMap.put("required", this._$28);
    localHashMap.put("selectData", this._$32);
    localHashMap.put("dataIndex", this._$37);
    localHashMap.put("decimalPrecision", this._$23);
    localHashMap.put("maxLength", this._$22);
    localHashMap.put("maxValue", this._$20);
    localHashMap.put("minLength", this._$21);
    localHashMap.put("minValue", this._$19);
    localHashMap.put("format", this._$18);
    localHashMap.put("editorId", this._$42);
    localHashMap.put("selectOnFocus", this._$8);
    localHashMap.put("upperOrLower", this._$9);
    if (this._$16 != null)
    {
      HttpServletRequest localHttpServletRequest = (HttpServletRequest)this.pageContext.getRequest();
      this._$16 = (localHttpServletRequest.getContextPath() + this._$16);
    }
    localHashMap.put("pageSize", this._$17);
    localHashMap.put("url", this._$16);
    localHashMap.put("hideTrigger", this._$15);
    localHashMap.put("minChars", this._$14);
    localHashMap.put("displayField", this._$13);
    localHashMap.put("tpl", this._$12);
    localHashMap.put("onSelect", this._$11);
    localHashMap.put("store", this._$10);
    localHashMap.put("queryDelay", this._$7);
    localHashMap.put("maxHeight", this._$6);
    localHashMap.put("onKeyDown", this._$5);
    localHashMap.put("selectStore", this.getSelectStore());
    localHashMap.put("codetype", this._$27);
    localHashMap.put("ischecked", this.getIschecked()==null?"false":(this.getIschecked()+""));
    localStringBuffer.append(TagUtil.getEditColInfo(this._$34, localHashMap));
    localStringBuffer.append("}");
    if (this._$36 == null)
      localStringBuffer.append(",");
    setCodeType(null);
    setSelectData(null);
    setHeader(null);
    setDataIndex(null);
    ResponseUtils.write(this.pageContext, localStringBuffer.toString());
    return super.doStartTag();
  }
  private String selectStore;
  public String getSelectStore() {
	return selectStore;
}

private void setSelectStore(String codeTypeJS) {
	// TODO Auto-generated method stub
	
  }

public String getDataIndex()
  {
    return this._$37;
  }

  public void setDataIndex(String paramString)
  {
    this._$37 = paramString;
  }

  public String getHeader()
  {
    return this._$41;
  }

  public void setHeader(String paramString)
  {
    this._$41 = paramString;
  }

  public String getId()
  {
    return this._$43;
  }

  public void setId(String paramString)
  {
    this._$43 = paramString;
  }

  public String getIsLast()
  {
    return this._$36;
  }

  public void setIsLast(String paramString)
  {
    this._$36 = paramString;
  }

  public String getSortable()
  {
    return this._$38;
  }

  public void setSortable(String paramString)
  {
    this._$38 = paramString;
  }

  public String getWidth()
  {
    return this._$39;
  }

  public void setWidth(String paramString)
  {
    this._$39 = paramString;
  }

  public String getRequired()
  {
    return this._$28;
  }

  public void setRequired(String paramString)
  {
    this._$28 = paramString;
  }

  public String getSelectData()
  {
    return this._$32;
  }

  public void setSelectData(String paramString)
  {
    this._$32 = paramString;
  }

  public String getCountType()
  {
    return this._$35;
  }

  public void setCountType(String paramString)
  {
    this._$35 = paramString;
  }

  public String getHidden()
  {
    return this._$33;
  }

  public void setHidden(String paramString)
  {
    this._$33 = paramString;
  }

  public String getSummaryRenderer()
  {
    return this._$29;
  }

  public void setSummaryRenderer(String paramString)
  {
    this._$29 = paramString;
  }

  public String getEditorId()
  {
    return this._$42;
  }

  public void setEditorId(String paramString)
  {
    this._$42 = paramString;
  }

  public String getDisplayField()
  {
    return this._$13;
  }

  public void setDisplayField(String paramString)
  {
    this._$13 = paramString;
  }

  public String getTpl()
  {
    return this._$12;
  }

  public void setTpl(String paramString)
  {
    this._$12 = paramString;
  }

  public String getStore()
  {
    return this._$10;
  }

  public void setStore(String paramString)
  {
    this._$10 = paramString;
  }

  public String getUpperOrLower()
  {
    return this._$9;
  }

  public void setUpperOrLower(String paramString)
  {
    this._$9 = paramString;
  }

  public String getGridName()
  {
    return this._$40;
  }

  public void setGridName(String paramString)
  {
    this._$40 = paramString;
  }

  public String getQueryDelay()
  {
    return this._$7;
  }

  public void setQueryDelay(String paramString)
  {
    this._$7 = paramString;
  }

public String getHideable() {
	return hideable;
}

public void setHideable(String hideable) {
	this.hideable = hideable;
}
}