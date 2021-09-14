// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.insigma.siis.local.business.helperUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.util.ResponseUtils;

/**
 * 
 * @author mengl
 *
 */
public class GridSelectColTag2 extends BodyTagSupport
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _$4;
	private String _$3;
	private String _$2;
	private String _$1;

	public GridSelectColTag2()
	{
	}

	public int doEndTag()
		throws JspException
	{
		return super.doEndTag();
	}

	public int doStartTag()
		throws JspException
	{
		if (_$2 != null && !_$2.equals(""))
//			_$3 = CodeManager.getArrayCode(_$2, _$1);
			_$3 = CodeType2js.getCodeTypeJS(_$2, _$1,null);
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append("<script>")
		.append("var ")
		.append(_$4)
		.append("_select = ");
		if (_$3 == null || _$3.trim().equals(""))
		{
			stringbuffer.append("odin.cueSelectArrayData;");
		}
		stringbuffer
		.append("[")
		.append(_$3)
		.append("];")
		.append("function doGrid")
		.append(_$4)
		.append("ColSelect(value, params, record,rowIndex,colIndex,ds){")
		.append("var sel_data = eval(\"")
		.append(_$4)
		.append("_select\");");
		stringbuffer.append("for(i=0;i<sel_data.length;i++){");
		stringbuffer.append("if(sel_data[i][0] == value){");
		stringbuffer.append("value = sel_data[i][1];");
		stringbuffer.append("break;");
		stringbuffer.append("}");
		stringbuffer.append("}");
		stringbuffer.append("return value;");
		stringbuffer.append("}");
		stringbuffer.append("</script>");
		ResponseUtils.write(pageContext, stringbuffer.toString());
		return super.doStartTag();
	}

	public String getName()
	{
		return _$4;
	}

	public void setName(String s)
	{
		_$4 = s;
	}

	public String getSelectData()
	{
		return _$3;
	}

	public void setSelectData(String s)
	{
		_$3 = s;
	}

	public String getCodeType()
	{
		return _$2;
	}

	public void setCodeType(String s)
	{
		_$2 = s;
	}

	public String getFilter()
	{
		return _$1;
	}

	public void setFilter(String s)
	{
		_$1 = s;
	}
}
