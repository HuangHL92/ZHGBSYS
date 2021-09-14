package com.insigma.siis.local.business.helperUtil;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.TextTag;
import org.apache.struts.util.ResponseUtils;

import com.insigma.odin.taglib.util.*;

/**
 * 2016-07-20
 * 
 * @author zhujq
 * 
 */
public class NewDateEditTag extends TextTag {

	private String label;
	private String labelSpanId;
	private String required;
	private String mask;
	private String colspan;
	private String width;
	private String validator; // 校验的方法
	private String invalidText;// 不通过时的提示信息
	private String inputType; // 可以为text, password，和html的type有着相同功能
	private String selectOnFocus;
	private String isCheck;
	private String hid;

	public String getSelectOnFocus() {
		return selectOnFocus;
	}

	public void setSelectOnFocus(String selectOnFocus) {
		this.selectOnFocus = selectOnFocus;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public String getInvalidText() {
		return invalidText;
	}

	public void setInvalidText(String invalidText) {
		this.invalidText = invalidText;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getColspan() {
		return colspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	/*
	 * @Override public int doStartTag() throws JspException {
	 * if(this.getSize()==null) { this.setStyle("width:88%"); } StringBuffer
	 * buffer = new StringBuffer();
	 * if(this.getLabel()==null&&this.colspan!=null) { this.colspan = "1"; }
	 * buffer .append("<td nowrap
	 * colspan=\""+(this.getColspan()==null?"2":this.getColspan ())+"\">");
	 * buffer.append("\r\n<div class=\"x-form-item\">"); buffer.append
	 * (TagUtil.getLabelString(this.getLabel(),this.getRequired()));
	 * buffer.append("<div class=\"x-form-element\">");
	 * ResponseUtils.write(this.pageContext,buffer.toString()); /*
	 * if(this.value==null) { value=""; }
	 * 
	 * try{ super.doStartTag(); }catch(Exception e) { this.value = "";
	 * super.doStartTag(); }
	 * 
	 * return 1; }
	 */

	public int doStartTag() throws JspException {
		setHid(this.property);
		setProperty(this.property + "_1");
		this.setStyleId(this.property);
		this.type = (this.inputType == null ? "text" : this.inputType);
		if ((this.getSize() == null || this.getSize().equals(""))
				&& this.colspan != null) {
			this.setStyle("width:" + TagConst.TEXT_STYLE_WIDTH);
		}
		StringBuffer buffer = new StringBuffer();
		if (this.getLabel() == null && this.colspan == null) {
			this.colspan = "2";
		}
		if (this.getLabel() != null) {
			this.labelSpanId = (this.labelSpanId == null) ? (this.hid + "SpanId")
					: this.labelSpanId;
			buffer
					.append("<td nowrap align='right'><span style='font-size:12px' id='"
							+ this.labelSpanId + "' >");
			this.labelSpanId = null; // 使用后清空，否则有可能会被下一个标签使用
			buffer.append(TagUtil.getLabelString(this.getLabel(), this
					.getRequired()));
			buffer.append("</span>&nbsp;</td>");
			buffer.append("<td nowrap colspan=\""
					+ (this.getColspan() == null ? "1" : (Integer.parseInt(this
							.getColspan()) - 1)) + "\">");
		} else {
			buffer.append("<td nowrap colspan=\"" + this.getColspan() + "\">");
		}

		buffer.append("\r\n<div class=\"x-form-item\">");
		// buffer.append(TagUtil.getLabelString(this.getLabel(),this.getRequired()));
		buffer.append("<div class=\"x-form-element\">");
		buffer.append("<input type=\"hidden\" name=\"" + this.hid + "\" id=\""
				+ this.hid + "\" />");
		ResponseUtils.write(this.pageContext, buffer.toString());

		try {
			this.setMaxlength((this.maxlength == null ? "" : this.maxlength)
					+ TagUtil.getNewPropertysStr(label, required));
			super.doStartTag();
		} catch (Exception e) {
			this.value = "";
			super.doStartTag();
		}
		return 1;
	}

	@Override
	public int doEndTag() throws JspException {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("</div></div>\r\n");
		buffer.append("<script type=\"text/javascript\">");
		buffer.append("Ext.onReady(function(){");
		buffer.append("var " + this.property + " = new Ext.form.");

		buffer.append("TextField");

		buffer.append("({ ");
		if (this.getRequired() != null && "true".equals(this.getRequired())) {
			buffer.append("allowBlank:false ,");
		}
		//if (this.getMask() != null) {
			buffer.append("vtype:'num2',");
		//}
		if (this.getSize() == null) {
			buffer.append("width:"
					+ (this.width == null ? TagConst.TEXT_WIDTH : this.width)
					+ ",");
		}
		buffer.append("id:'" + this.property + "',enableKeyEvents:true,");
		buffer.append((this.validator == null ? "" : ("validator:"
				+ this.validator + ",")));
		buffer.append((this.invalidText == null ? "" : ("invalidText:'"
				+ this.invalidText + "',")));
		buffer.append("inputType:'"
				+ (this.inputType == null ? "text" : this.inputType) + "',");
		buffer.append((selectOnFocus == null || selectOnFocus.equals("")) ? ""
				: ("selectOnFocus:" + selectOnFocus + ","));
		buffer.append("applyTo:'" + this.property + "',");
		// String message = "日期格式不正确，只能输入6位或8位的有效日期，正确格式为：200808 或 20080804";
		// buffer.append("minLength:6,");
		// buffer.append("minLengthText:\""+message+"\",");
		// buffer.append("maxLength:8,");
		// buffer.append("maxLengthText:\""+message+"\"");
		// 输入是否可以超过当前日期
		
		buffer.append("listeners:{blur:function(){blurDate_bj('"
				+ this.hid + "',"+"true".equals(this.isCheck)+",true);}," +
						"keyup:function(){blurDate_bj('"
				+ this.hid + "',"+"true".equals(this.isCheck)+",false);}," +
						"focus:function(){restoreDate('"
				+ this.hid + "');}}");
		
		buffer.append("});");
		buffer.append("}); </script>");
		buffer.append("</td>");
		ResponseUtils.write(this.pageContext, buffer.toString());
		super.release();
		TagUtil.flush(pageContext);
		return 1;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getLabelSpanId() {
		return labelSpanId;
	}

	public void setLabelSpanId(String labelSpanId) {
		this.labelSpanId = labelSpanId;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

}
