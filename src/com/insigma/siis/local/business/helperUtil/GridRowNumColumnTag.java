package com.insigma.siis.local.business.helperUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.util.ResponseUtils;

public class GridRowNumColumnTag
extends BodyTagSupport
{
private String _$3;
private String _$2;
private String _$1;

public String getHeader()
{
  return this._$3;
}

public void setHeader(String paramString)
{
  this._$3 = paramString;
}

public String getSortable()
{
  return this._$2;
}

public void setSortable(String paramString)
{
  this._$2 = paramString;
}

public String getWidth()
{
  return this._$1;
}

public void setWidth(String paramString)
{
  this._$1 = paramString;
}

public int doStartTag()
  throws JspException
{
  StringBuffer localStringBuffer = new StringBuffer();
  localStringBuffer.append("new Ext.grid.RowNumberer({locked:true,");
  localStringBuffer.append("header:'" + (this._$3==null?"":this._$3) + "',");
  localStringBuffer.append("sortable:" + this._$2 + ",");
  localStringBuffer.append("width:" + ((this._$1 == null) || (this._$1.equals("")) ? "23" : this._$1));
  localStringBuffer.append("}),");
  ResponseUtils.write(this.pageContext, localStringBuffer.toString());
  return 0;
}
}