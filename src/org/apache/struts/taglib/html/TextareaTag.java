package org.apache.struts.taglib.html;

import javax.servlet.jsp.JspException;
import org.apache.struts.util.ResponseUtils;

public class TextareaTag
  extends BaseInputTag
{
  protected String name = "org.apache.struts.taglib.html.BEAN";
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public int doStartTag()
    throws JspException
  {
    ResponseUtils.write(this.pageContext, renderTextareaElement());
    
    return 2;
  }
  
  protected String renderTextareaElement()
    throws JspException
  {
    StringBuffer results = new StringBuffer("<textarea");
    
    results.append(" name=\"");
    if (this.indexed) {
      prepareIndex(results, this.name);
    }
    results.append(this.property);
    results.append("\"");
    if (this.accesskey != null)
    {
      results.append(" accesskey=\"");
      results.append(this.accesskey);
      results.append("\"");
    }
    if (this.tabindex != null)
    {
      results.append(" tabindex=\"");
      results.append(this.tabindex);
      results.append("\"");
    }
    if (this.cols != null)
    {
      results.append(" cols=\"");
      results.append(this.cols);
      results.append("\"");
    }
  //add by zoul 2018Äê8ÔÂ25ÈÕ10:39:58
    if (this.maxlength != null)
    {
      results.append(" maxlength=\"");
      results.append(this.maxlength);
      results.append("\"");
    }
    if (this.rows != null)
    {
      results.append(" rows=\"");
      results.append(this.rows);
      results.append("\"");
    }
    results.append(prepareEventHandlers());
    results.append(prepareStyles());
    results.append(">");
    
    results.append(renderData());
    
    results.append("</textarea>");
    return results.toString();
  }
  
  protected String renderData()
    throws JspException
  {
    String data = this.value;
    if (data == null) {
      data = lookupProperty(this.name, this.property);
    }
    return data == null ? "" : ResponseUtils.filter(data);
  }
  
  public void release()
  {
    super.release();
    this.name = "org.apache.struts.taglib.html.BEAN";
  }
}

/* Location:           C:\workspace2\zhgb\WebContent\WEB-INF\lib\struts.jar
 * Qualified Name:     org.apache.struts.taglib.html.TextareaTag
 * Java Class Version: 1.2 (46.0)
 * JD-Core Version:    0.7.1
 */