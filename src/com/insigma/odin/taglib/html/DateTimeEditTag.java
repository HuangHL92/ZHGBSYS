package com.insigma.odin.taglib.html;

import com.insigma.odin.taglib.util.TagConst;
import com.insigma.odin.taglib.util.TagUtil;
import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.html.TextTag;
import org.apache.struts.util.ResponseUtils;

public class DateTimeEditTag
  extends TextTag
{
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
  private String dateFormat;
  private String timeFormat;
  
  public String getDateFormat() {
	return dateFormat;
}

public void setDateFormat(String dateFormat) {
	this.dateFormat = dateFormat;
}

public String getTimeFormat() {
	return timeFormat;
}

public void setTimeFormat(String timeFormat) {
	this.timeFormat = timeFormat;
}

public String getSelectOnFocus()
  {
    return this._$1;
  }
  
  public void setSelectOnFocus(String paramString)
  {
    this._$1 = paramString;
  }
  
  public String getValidator()
  {
    return this._$3;
  }
  
  public void setValidator(String paramString)
  {
    this._$3 = paramString;
  }
  
  public String getInvalidText()
  {
    return this._$2;
  }
  
  public void setInvalidText(String paramString)
  {
    this._$2 = paramString;
  }
  
  public String getFormat()
  {
    return this._$4;
  }
  
  public void setFormat(String paramString)
  {
    this._$4 = paramString;
  }
  
  public String getWidth()
  {
    return this._$5;
  }
  
  public void setWidth(String paramString)
  {
    this._$5 = paramString;
  }
  
  public String getColspan()
  {
    return this._$6;
  }
  
  public void setColspan(String paramString)
  {
    this._$6 = paramString;
  }
  
  public String getLabel()
  {
    return this._$10;
  }
  
  public void setLabel(String paramString)
  {
    this._$10 = paramString;
  }
  
  public String getMask()
  {
    return this._$7;
  }
  
  public void setMask(String paramString)
  {
    this._$7 = paramString;
  }
  
  public String getRequired()
  {
    return this._$8;
  }
  
  public void setRequired(String paramString)
  {
    this._$8 = paramString;
  }
  
  public int doStartTag()
    throws JspException
  {
    setStyleId(this.property);
    if (((getSize() == null) || (getSize().equals(""))) && (this._$6 != null)) {
      setStyle("width:" + TagConst.TEXT_STYLE_WIDTH);
    }
    StringBuffer localStringBuffer = new StringBuffer();
    if ((getLabel() == null) && (this._$6 == null)) {
      this._$6 = "2";
    }
    if (getLabel() != null)
    {
      this._$9 = (this._$9 == null ? this.property + "SpanId" : this._$9);
      localStringBuffer.append("<td nowrap align='right'><span style='font-size:12px' id='" + this._$9 + "' >");
      this._$9 = null;
      localStringBuffer.append(TagUtil.getLabelString(getLabel(), getRequired()));
      localStringBuffer.append("</span>&nbsp;</td>");
      localStringBuffer.append("<td nowrap colspan=\"" + (getColspan() == null ? "1" : Integer.valueOf(Integer.parseInt(getColspan()) - 1)) + "\">");
    }
    else
    {
      localStringBuffer.append("<td nowrap colspan=\"" + getColspan() + "\">");
    }
    localStringBuffer.append("\r\n<div class=\"x-form-item\">");
    localStringBuffer.append("<div class=\"x-form-element\">");
    ResponseUtils.write(this.pageContext, localStringBuffer.toString());
    try
    {
      setMaxlength((this.maxlength == null ? "" : this.maxlength) + TagUtil.getNewPropertysStr(this._$10, this._$8));
      super.doStartTag();
    }
    catch (Exception localException)
    {
      this.value = "";
      super.doStartTag();
    }
    return 1;
  }
  
  public int doEndTag()
    throws JspException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("</div></div>\r\n");
    localStringBuffer.append("<script type=\"text/javascript\">");
    localStringBuffer.append("Ext.onReady(function(){");
    localStringBuffer.append("var " + this.property + " = new Ext.ux.form.");
    localStringBuffer.append("DateTimeField");
    localStringBuffer.append("({ ");
    if ((getRequired() != null) && ("true".equals(getRequired()))) {
      localStringBuffer.append("allowBlank:false ,");
    }
    if (getSize() == null) {
      localStringBuffer.append("width:" + (this._$5 == null ? Integer.valueOf(TagConst.TEXT_WIDTH) : this._$5) + ",");
    }
    localStringBuffer.append("id:'" + this.property + "',");
    localStringBuffer.append("dateFormat:'" + this.dateFormat + "',");
    localStringBuffer.append("timeFormat:'" + this.timeFormat + "',");
    localStringBuffer.append("format:'" + (this._$4 == null ? TagConst.DATE_DEFAULT_FORMAT : this._$4) + "',");
    localStringBuffer.append("maxValue:'2099-12-31',minValue:'1900-01-01',");
    localStringBuffer.append("onSelect: function(m, d){");
    if (!getReadonly()) {
      localStringBuffer.append("this.setValue(d);");
    }
    localStringBuffer.append(getOnchange() + ";");
    localStringBuffer.append("this.fireEvent('select', this, d);");
    localStringBuffer.append("this.menu.hide();");
    localStringBuffer.append("},");
    if (getReadonly() == true) {
      localStringBuffer.append("readOnly:true,");
    }
    if (getDisabled()) {
      localStringBuffer.append("disabled:true,");
    }
    if( this._$3!=null)
    	localStringBuffer.append("validator:" + this._$3==null + ",");
    if( this._$2!=null)
    	localStringBuffer.append("invalidText:'" + this._$2 + "',");
    if( this._$1!=null)
    	localStringBuffer.append("selectOnFocus:" + this._$1 + ",");
    localStringBuffer.append(" applyTo:'" + this.property + "'");
    localStringBuffer.append("});");
    localStringBuffer.append("Ext.getCmp('" + this.property + "').addListener('invalid',odin.trrigerCommInvalid);");
    localStringBuffer.append("});");
    localStringBuffer.append(" </script>");
    localStringBuffer.append("</td>");
    ResponseUtils.write(this.pageContext, localStringBuffer.toString());
    super.release();
    return 1;
  }
  
  public String getLabelSpanId()
  {
    return this._$9;
  }
  
  public void setLabelSpanId(String paramString)
  {
    this._$9 = paramString;
  }
}

/* Location:           C:\workspace2\zhgb\WebContent\WEB-INF\lib\odin-taglib.jar
 * Qualified Name:     com.insigma.odin.taglib.html.DateEditTag
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.7.1
 */