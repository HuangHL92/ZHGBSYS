package com.insigma.siis.local.business.helperUtil;

import com.insigma.odin.framework.comm.CodeManager;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.commform.ObjectUtil;
import com.insigma.odin.taglib.util.TagConst;
import com.insigma.odin.taglib.util.TagUtil;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.struts.taglib.html.TextTag;
import org.apache.struts.util.ResponseUtils;

public class ComboBoxTag2 extends TextTag {
	private String codename;
	private String _$31;
	private String _$30;
	private String _$29;
	private String _$28;
	private String _$27;
	private String _$26;
	private String _$25;
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
	private String _$2;
	private String _$1;

	public String getCanOutSelectList() {
		return this._$1;
	}

	public void setCanOutSelectList(String paramString) {
		this._$1 = paramString;
	}

	public String getIsPageSelect() {
		return this._$2;
	}

	public void setIsPageSelect(String paramString) {
		this._$2 = paramString;
	}

	public String getListWidth() {
		return this._$7;
	}

	public void setListWidth(String paramString) {
		this._$7 = paramString;
	}

	public String getValueNotFoundText() {
		return this._$8;
	}

	public void setValueNotFoundText(String paramString) {
		this._$8 = paramString;
	}

	public String getAllAsItem() {
		return this._$9;
	}

	public void setAllAsItem(String paramString) {
		this._$9 = paramString;
	}

	public String getMaxHeight() {
		return this._$10;
	}

	public void setMaxHeight(String paramString) {
		this._$10 = paramString;
	}

	public String getPageSize() {
		return this._$15;
	}

	public void setPageSize(String paramString) {
		this._$15 = paramString;
	}

	public String getUrl() {
		return this._$14;
	}

	public void setUrl(String paramString) {
		this._$14 = paramString;
	}

	public String getHideTrigger() {
		return this._$13;
	}

	public void setHideTrigger(String paramString) {
		this._$13 = paramString;
	}

	public String getMinChars() {
		return this._$12;
	}

	public void setMinChars(String paramString) {
		this._$12 = paramString;
	}

	public String getTpl() {
		return this._$17;
	}

	public void setTpl(String paramString) {
		this._$17 = paramString;
	}

	public String getValidator() {
		return this._$19;
	}

	public void setValidator(String paramString) {
		this._$19 = paramString;
	}

	public String getInvalidText() {
		return this._$18;
	}

	public void setInvalidText(String paramString) {
		this._$18 = paramString;
	}

	public String getCodeType() {
		return this._$23;
	}

	public void setCodeType(String paramString) {
		this._$23 = paramString;
	}

	public String getFilter() {
		return this._$22;
	}

	public void setFilter(String paramString) {
		this._$22 = paramString;
	}

	public String getDefaultValue() {
		return this._$24;
	}

	public void setDefaultValue(String paramString) {
		this._$24 = paramString;
	}

	public String getWidth() {
		return this._$25;
	}

	public void setWidth(String paramString) {
		this._$25 = paramString;
	}

	public String getEditor() {
		return this._$27;
	}

	public void setEditor(String paramString) {
		this._$27 = paramString;
	}

	public String getColspan() {
		return this._$28;
	}

	public void setColspan(String paramString) {
		this._$28 = paramString;
	}

	public String getLabel() {
		return this._$31;
	}

	public void setLabel(String paramString) {
		this._$31 = paramString;
	}

	public String getRequired() {
		return this._$29;
	}

	public void setRequired(String paramString) {
		this._$29 = paramString;
	}

	public int doStartTag() throws JspException {
		if (("true".equals(this._$2)) && (this._$14 == null))
			this._$14 = "/common/sysCodeAction.do?method=querySelectCodeValuesByPage";
		if (((getCodeType() == null) || (getCodeType().equals("")))
				&& (getData() == null))
			setCodeType(this.property.toUpperCase());
		StringBuffer localStringBuffer = new StringBuffer();
		if (((getSize() == null) || (getSize().equals("")))
				&& (this._$28 != null))
			setStyle("width:" + TagConst.TEXT_STYLE_WIDTH);
		if ((getLabel() == null) && (this._$28 == null))
			this._$28 = "2";
		if (getLabel() != null) {
			this._$30 = (this._$30 == null ? this.property + "SpanId"
					: this._$30);
			localStringBuffer
					.append("<td nowrap align='right'><span style='font-size:12px' id='"
							+ this._$30 + "' >");
			this._$30 = null;
			localStringBuffer.append(TagUtil.getLabelString(getLabel(),
					getRequired()));
			localStringBuffer.append("</span>&nbsp;</td>");
			localStringBuffer.append("<td nowrap colspan=\""
					+ (getColspan() == null ? "1" : Integer.valueOf(Integer
							.parseInt(getColspan()) - 1)) + "\">");
		} else {
			localStringBuffer.append("<td nowrap align='left' id='tdid_"+getProperty()+"' class='tdclass_"+getProperty()+" tdclass' colspan=\"" + getColspan()
					+ "\">");
		}
		localStringBuffer.append("\r\n<div class=\"x-form-item\">");
		localStringBuffer.append("<div class=\"x-form-element\">");
		ResponseUtils.write(this.pageContext, localStringBuffer.toString());
		setStyle("display:none");
		this.type = "hidden";
		setStyleId(this.property);
		try {
			setMaxlength((this.maxlength == null ? "" : this.maxlength)
					+ TagUtil.getNewPropertysStr(this._$31, this._$29));
			super.doStartTag();
		} catch (Exception localException) {
			this.value = "";
			super.doStartTag();
		}
		return 2;
	}

	public int doEndTag() throws JspException {
		if (ObjectUtil.equals(this._$3, "true")) {
			this._$5 = "true";
			this._$9 = "true";
			this.value = "all";
		}
		if (ObjectUtil.equals(this._$4, "true")) {
			this._$5 = "true";
			this._$9 = "true";
		}
		if (ObjectUtil.equals(this._$6, "true")) {
			this._$9 = "true";
			this.value = "all";
		}
		String str = "";
		if (this.bodyContent != null)
			str = this.bodyContent.getString();
		StringBuffer localStringBuffer = new StringBuffer();
		super.doEndTag();
		localStringBuffer.append("<input type='text' name='" + this.property
				+ "_combo' id='" + this.property + "_combo' autocomplete=\"off\" ");
		if ((getSize() != null) && (!getSize().equals("")))
			localStringBuffer.append("size='" + getSize() + "' ");
		if (getDisabled() == true)
			localStringBuffer.append(" disabled='true' ");
		localStringBuffer.append(prepareEventHandlers());
		localStringBuffer.append(" >");
		localStringBuffer.append("</div></div><script>");
		if ((getUrl() == null) || (getUrl().equals(""))) {
			String localObject = (String) GlobalNames.sysConfig
					.get("SELECT_LAZYLOAD");
			if (("1".equals(localObject)) && (this._$23 != null)
					&& (!this._$23.equals(""))) {
				localStringBuffer
						.append("if(typeof radow_select_info == 'undefined'){");
				localStringBuffer.append("radow_select_info = [];");
				localStringBuffer.append("}");
				localStringBuffer.append("radow_select_info.push({'id':'"
						+ getProperty() + "_combo','aaa100':'"
						+ (this._$23 == null ? "" : this._$23)
						+ "',aaa105:'',filter:\""
						+ (getFilter() == null ? "" : getFilter())
						+ "\",isRemoveAll:true});");
			} else {
				if ((this._$23 != null) && (!this._$23.equals("")))
					this._$26 = CodeType2js.getCodeTypeJS(this._$23,this._$22,this.codename);//CodeManager.getArrayCode(this._$23, this._$22);
				if ((this._$9 != null) && (!this._$9.equals("")))
					this._$26 = ("['all','È«²¿']," + this._$26);
			}
			
		}
		
		//*************************************************************************//
		//CodeType2js.insertLogConfig(this.property, this._$31, this._$23);
		
		localStringBuffer.append("var " + this.property + "_comboData = ["
				+ (this._$26 == null ? str : this._$26) + "];");
		localStringBuffer.append("var " + this.property
				+ "_store = new Ext.data.");
		if ((this._$14 != null) && (!this._$14.trim().equals(""))) {
			HttpServletRequest localObject = (HttpServletRequest) this.pageContext
					.getRequest();
			this._$14 = (((HttpServletRequest) localObject).getContextPath() + this._$14);
			localStringBuffer.append("Store({");
			localStringBuffer.append("url:'" + this._$14 + "',");
			localStringBuffer.append("baseParams: {aaa100:'" + this._$23
					+ "',filter:'"
					+ ObjectUtil.nvl(this._$22, "").replace("'", "\\'")
					+ "',allAsItem:'" + this._$9 + "'},");
			localStringBuffer
					.append("listeners:{'beforeload':function(theds,pa){theds.baseParams.cueGridId='"
							+ this.property
							+ "_combo';},'load':function(){odin.setListWidth(Ext.getCmp('"
							+ this.property + "_combo'))}},");
			localStringBuffer
					.append("reader:new Ext.data.JsonReader({root: 'data',totalProperty: 'totalCount',id: 'key'},[{name: 'key'},{name: 'value'}])");
		} else {
			localStringBuffer.append("SimpleStore({");
			localStringBuffer.append("data :" + this.property
					+ "_comboData,fields: ['key', 'value']");
			localStringBuffer.append(",createFilterFn:odin.createFilterFn");
		}
		localStringBuffer.append("});");
		localStringBuffer.append("Ext.onReady(function(){");
		localStringBuffer.append("var "
				+ this.property
				+ "_combo = new "
				+ (ObjectUtil.equals(this._$5, "true") ? "Ext.ux.form.LovCombo"
						: "Ext.form.ComboBox") + "({");
		localStringBuffer.append("store: " + this.property + "_store,");
		localStringBuffer.append("displayField:'"
				+ (this._$21 == null ? "value" : this._$21) + "',");
		localStringBuffer.append("canOutSelectList:'"
				+ (this._$1 == null ? "false" : this._$1) + "',");
		if(this._$20!=null&&!"".equals(this._$20)){
			localStringBuffer.append("valueField:'" + this._$20 + "',");
		}
		
		localStringBuffer.append("typeAhead: false,");
		localStringBuffer.append("id:'" + this.property + "_combo',");
		localStringBuffer.append("mode: '"
				+ (this._$14 == null ? "local" : "remote") + "',");
		if ((getTpl() != null) && (!"true".equals(getMultiSelect())))
			if ("keyvalue".equals(getTpl().trim().toLowerCase()))
				localStringBuffer
						.append("tpl:'<tpl for=\".\"><div class=\"x-combo-list-item\">{key} - {value}{params}</div></tpl>',");
			else
				localStringBuffer.append("tpl:" + getTpl() + ",");
		localStringBuffer.append("emptyText:'"
				+ ((this._$24 == null) || (this._$24.equals("")) ? ""//ÇëÄúÑ¡Ôñ...
						: this._$24) + "',");
		localStringBuffer.append("editable:"
				+ (((this._$27 != null) && (this._$27.equals("true")))
						|| ((this._$27 == null) && (!getReadonly())) ? "true,"
						: "false,"));
		if(this._$16!=null&&!"".equals(this._$16)){
			localStringBuffer.append("selectOnFocus:" + this._$16 + ",");
		}
		if(this._$10!=null&&!"".equals(this._$10)){
			localStringBuffer.append("maxHeight:" + this._$10 + ",");
		}
		
		if ((getRequired() != null) && ("true".equals(getRequired())))
			localStringBuffer.append("allowBlank:false ,");
		localStringBuffer.append("triggerAction: 'all',");
		if (getSize() == null)
			localStringBuffer.append("width:"
					+ (this._$25 == null ? Integer.valueOf(TagConst.TEXT_WIDTH)
							: this._$25) + ",");
		if (this._$14 != null)
			this._$7 = "260";
		if ((this._$7 != null) && (!"".equals(this._$7)))
			localStringBuffer.append("listWidth:" + this._$7 + ",");
		if (getReadonly())
			localStringBuffer.append("readOnly:true,");
		if (getDisabled() == true)
			localStringBuffer.append("disabled:true,");
		if ((this._$9 != null) && (!"".equals(this._$9))){
			localStringBuffer.append("allAsItem:" + this._$9 + ",");
		}
		if ((this._$8 != null) && (!"".equals(this._$8))){
			localStringBuffer.append("valueNotFoundText:'" + this._$8 + "',");
		}
		if ((this._$19 != null) && (!"".equals(this._$19))){
			localStringBuffer.append("validator:" + this._$19 + ",");
		}
		if ((this._$18 != null) && (!"".equals(this._$18))){
			localStringBuffer.append("invalidText:'" + this._$18 + "',");
		}
		
		localStringBuffer.append("hideTrigger:"
				+ (this._$13 == null ? "false" : this._$13) + ",");
		if ((this._$11 != null) && (!"".equals(this._$11))){
			localStringBuffer.append("queryDelay:" + this._$11 + ",");
		}
		
		if ((this._$15 == null) && (this._$14 != null)) {
			localStringBuffer.append("pageSize:" + GlobalNames.PAGE_SIZE + ",");
			localStringBuffer.append("minChars:" + this._$12 + ",");
		} else if ((this._$14 != null) && (this._$15 != null)) {
			localStringBuffer.append("pageSize:" + this._$15 + ",");
			localStringBuffer.append("minChars:" + this._$12 + ",");
		}
		if (!ObjectUtil.equals(this._$5, "true")) {
			localStringBuffer.append(" onSelect : function(record, index){");
			localStringBuffer
					.append("if(this.fireEvent('beforeselect', this, record, index) !== false){");
			if (!getReadonly()) {
				localStringBuffer
						.append(" this.setValue(record.data[this.valueField || this.displayField]); ");
				localStringBuffer.append(" this.collapse();");
				localStringBuffer.append(" odin.doAccForSelect(this);");
				localStringBuffer
						.append(" this.fireEvent('select', this, record, index);");
				if ((getOnchange() != null) && (!getOnchange().equals("")))
					localStringBuffer.append(getOnchange() + "(record,index);");
			}
			localStringBuffer.append(" }");
			localStringBuffer.append("},");
		}
		localStringBuffer.append("applyTo:'" + this.property + "_combo'");
		localStringBuffer.append("});");
		if (!ObjectUtil.equals(this._$5, "true")) {
			localStringBuffer.append("Ext.getCmp('" + this.property
					+ "_combo').addListener('blur',odin.doAccForSelect);");
			localStringBuffer
					.append("Ext.getCmp('"
							+ this.property
							+ "_combo').addListener('select',odin.setHiddenTextValue);");
		} else if (ObjectUtil.equals(this._$9, "true")) {
			localStringBuffer
					.append("Ext.getCmp('"
							+ this.property
							+ "_combo').addListener('beforeselect',odin.doMultiSelectWithAll);");
		}
		localStringBuffer.append("Ext.getCmp('" + this.property
				+ "_combo').addListener('focus',odin.comboFocus);");
		localStringBuffer.append("Ext.getCmp('" + this.property
				+ "_combo').addListener('expand',odin.setListWidth);");
		localStringBuffer.append("Ext.getCmp('" + this.property
				+ "_combo').addListener('invalid',odin.trrigerCommInvalid);");
		Object localObject = getSelectValue();
		if ((localObject != null) && (!((String) localObject).equals("")))
			localStringBuffer.append("odin.setSelectValue('" + this.property
					+ "','" + (String) localObject + "')");
		localStringBuffer.append("});");
		localStringBuffer.append("</script>");
		localStringBuffer.append("</td>");
		this._$23 = null;
		this._$26 = null;
		this._$14 = null;
		ResponseUtils.write(this.pageContext, localStringBuffer.toString());
		super.release();
		TagUtil.flush(this.pageContext);
		return 1;
	}

	protected String getSelectValue() {
		if ((this.value == null) || ("".equals(this.value.trim()))
				|| ("null".equals(this.value.trim())))
			return TagUtil.getPropertyValue(this.pageContext, this.property);
		return this.value;
	}

	public String getData() {
		return this._$26;
	}

	public void setData(String paramString) {
		this._$26 = paramString;
	}

	public String getDisplayField() {
		return this._$21;
	}

	public void setDisplayField(String paramString) {
		this._$21 = paramString;
	}

	public String getSelectOnFocus() {
		return this._$16;
	}

	public void setSelectOnFocus(String paramString) {
		this._$16 = paramString;
	}

	public String getValueField() {
		return this._$20;
	}

	public void setValueField(String paramString) {
		this._$20 = paramString;
	}

	public String getQueryDelay() {
		return this._$11;
	}

	public void setQueryDelay(String paramString) {
		this._$11 = paramString;
	}

	public String getLabelSpanId() {
		return this._$30;
	}

	public void setLabelSpanId(String paramString) {
		this._$30 = paramString;
	}

	public String getMultiSelect() {
		return this._$5;
	}

	public void setMultiSelect(String paramString) {
		this._$5 = paramString;
	}

	public String getMultiSelectWithAll() {
		return this._$4;
	}

	public void setMultiSelectWithAll(String paramString) {
		this._$4 = paramString;
	}

	public String getMultiSelectWithAllAndSetDefault() {
		return this._$3;
	}

	public void setMultiSelectWithAllAndSetDefault(String paramString) {
		this._$3 = paramString;
	}

	public String getAllAsItemAndSetDefault() {
		return this._$6;
	}

	public void setAllAsItemAndSetDefault(String paramString) {
		this._$6 = paramString;
	}

	public String getCodename() {
		return codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}
}