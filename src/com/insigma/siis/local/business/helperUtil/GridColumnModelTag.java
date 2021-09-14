package com.insigma.siis.local.business.helperUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.util.ResponseUtils;

public class GridColumnModelTag
extends BodyTagSupport
{
public int doEndTag()
  throws JspException
{
  StringBuffer localStringBuffer = new StringBuffer();
  localStringBuffer.append("]);");
  ResponseUtils.write(this.pageContext, localStringBuffer.toString());
  return super.doEndTag();
}

public int doStartTag()
  throws JspException
{
  StringBuffer localStringBuffer = new StringBuffer();
  localStringBuffer.append("var colModel = new Ext.ux.grid.LockingColumnModel([");
  localStringBuffer.append("");
  localStringBuffer.append("");
  localStringBuffer.append("");
  localStringBuffer.append("");
  localStringBuffer.append("");
  localStringBuffer.append("");
  ResponseUtils.write(this.pageContext, localStringBuffer.toString());
  return 1;
}
}