package org.apache.struts.taglib.html;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.logic.IterateTag;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;

public abstract class BaseHandlerTag
  extends BodyTagSupport
{
  private static Log log = LogFactory.getLog(BaseHandlerTag.class);
  protected static final Locale defaultLocale = Locale.getDefault();
  protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.html.LocalStrings");
  protected String accesskey = null;
  protected String tabindex = null;
  protected boolean indexed = false;
  private String onclick = null;
  private String ondblclick = null;
  private String onmouseover = null;
  private String onmouseout = null;
  private String onmousemove = null;
  private String onmousedown = null;
  private String onmouseup = null;
  private String onkeydown = null;
  private String onkeyup = null;
  private String onkeypress = null;
  private String onselect = null;
  private String onchange = null;
  private String onblur = null;
  private String onfocus = null;
  private boolean disabled = false;
  private boolean readonly = false;
  private String style = null;
  private String styleClass = null;
  private String styleId = null;
  private String alt = null;
  private String altKey = null;
  private String bundle = null;
  private String locale = "org.apache.struts.action.LOCALE";
  private String title = null;
  private String titleKey = null;
  
  public void setAccesskey(String accessKey)
  {
    this.accesskey = accessKey;
  }
  
  public String getAccesskey()
  {
    return this.accesskey;
  }
  
  public void setTabindex(String tabIndex)
  {
    this.tabindex = tabIndex;
  }
  
  public String getTabindex()
  {
    return this.tabindex;
  }
  
  public void setIndexed(boolean indexed)
  {
    this.indexed = indexed;
  }
  
  public boolean getIndexed()
  {
    return this.indexed;
  }
  
  public void setOnclick(String onClick)
  {
    this.onclick = onClick;
  }
  
  public String getOnclick()
  {
    return this.onclick;
  }
  
  public void setOndblclick(String onDblClick)
  {
    this.ondblclick = onDblClick;
  }
  
  public String getOndblclick()
  {
    return this.ondblclick;
  }
  
  public void setOnmousedown(String onMouseDown)
  {
    this.onmousedown = onMouseDown;
  }
  
  public String getOnmousedown()
  {
    return this.onmousedown;
  }
  
  public void setOnmouseup(String onMouseUp)
  {
    this.onmouseup = onMouseUp;
  }
  
  public String getOnmouseup()
  {
    return this.onmouseup;
  }
  
  public void setOnmousemove(String onMouseMove)
  {
    this.onmousemove = onMouseMove;
  }
  
  public String getOnmousemove()
  {
    return this.onmousemove;
  }
  
  public void setOnmouseover(String onMouseOver)
  {
    this.onmouseover = onMouseOver;
  }
  
  public String getOnmouseover()
  {
    return this.onmouseover;
  }
  
  public void setOnmouseout(String onMouseOut)
  {
    this.onmouseout = onMouseOut;
  }
  
  public String getOnmouseout()
  {
    return this.onmouseout;
  }
  
  public void setOnkeydown(String onKeyDown)
  {
    this.onkeydown = onKeyDown;
  }
  
  public String getOnkeydown()
  {
    return this.onkeydown;
  }
  
  public void setOnkeyup(String onKeyUp)
  {
    this.onkeyup = onKeyUp;
  }
  
  public String getOnkeyup()
  {
    return this.onkeyup;
  }
  
  public void setOnkeypress(String onKeyPress)
  {
    this.onkeypress = onKeyPress;
  }
  
  public String getOnkeypress()
  {
    return this.onkeypress;
  }
  
  public void setOnchange(String onChange)
  {
    this.onchange = onChange;
  }
  
  public String getOnchange()
  {
    return this.onchange;
  }
  
  public void setOnselect(String onSelect)
  {
    this.onselect = onSelect;
  }
  
  public String getOnselect()
  {
    return this.onselect;
  }
  
  public void setOnblur(String onBlur)
  {
    this.onblur = onBlur;
  }
  
  public String getOnblur()
  {
    return this.onblur;
  }
  
  public void setOnfocus(String onFocus)
  {
    this.onfocus = onFocus;
  }
  
  public String getOnfocus()
  {
    return this.onfocus;
  }
  
  public void setDisabled(boolean disabled)
  {
    this.disabled = disabled;
  }
  
  public boolean getDisabled()
  {
    return this.disabled;
  }
  
  public void setReadonly(boolean readonly)
  {
    this.readonly = readonly;
  }
  
  public boolean getReadonly()
  {
    return this.readonly;
  }
  
  public void setStyle(String style)
  {
    this.style = style;
  }
  
  public String getStyle()
  {
    return this.style;
  }
  
  public void setStyleClass(String styleClass)
  {
    this.styleClass = styleClass;
  }
  
  public String getStyleClass()
  {
    return this.styleClass;
  }
  
  public void setStyleId(String styleId)
  {
    this.styleId = styleId;
  }
  
  public String getStyleId()
  {
    return this.styleId;
  }
  
  public String getAlt()
  {
    return this.alt;
  }
  
  public void setAlt(String alt)
  {
    this.alt = alt;
  }
  
  public String getAltKey()
  {
    return this.altKey;
  }
  
  public void setAltKey(String altKey)
  {
    this.altKey = altKey;
  }
  
  public String getBundle()
  {
    return this.bundle;
  }
  
  public void setBundle(String bundle)
  {
    this.bundle = bundle;
  }
  
  public String getLocale()
  {
    return this.locale;
  }
  
  public void setLocale(String locale)
  {
    this.locale = locale;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public String getTitleKey()
  {
    return this.titleKey;
  }
  
  public void setTitleKey(String titleKey)
  {
    this.titleKey = titleKey;
  }
  
  public void release()
  {
    super.release();
    this.accesskey = null;
    this.alt = null;
    this.altKey = null;
    this.bundle = null;
    this.indexed = false;
    this.locale = "org.apache.struts.action.LOCALE";
    this.onclick = null;
    this.ondblclick = null;
    this.onmouseover = null;
    this.onmouseout = null;
    this.onmousemove = null;
    this.onmousedown = null;
    this.onmouseup = null;
    this.onkeydown = null;
    this.onkeyup = null;
    this.onkeypress = null;
    this.onselect = null;
    this.onchange = null;
    this.onblur = null;
    this.onfocus = null;
    this.disabled = false;
    this.readonly = false;
    this.style = null;
    this.styleClass = null;
    this.styleId = null;
    this.tabindex = null;
    this.title = null;
    this.titleKey = null;
  }
  
  protected String message(String literal, String key)
    throws JspException
  {
    if (literal != null)
    {
      if (key != null)
      {
        JspException e = new JspException(messages.getMessage("common.both"));
        RequestUtils.saveException(this.pageContext, e);
        throw e;
      }
      return literal;
    }
    if (key != null) {
      return RequestUtils.message(this.pageContext, getBundle(), getLocale(), key);
    }
    return null;
  }
  
  private Class loopTagSupportClass = null;
  private Method loopTagSupportGetStatus = null;
  private Class loopTagStatusClass = null;
  private Method loopTagStatusGetIndex = null;
  private boolean triedJstlInit = false;
  private boolean triedJstlSuccess = false;
  
  private Integer getJstlLoopIndex()
  {
    if (!this.triedJstlInit)
    {
      this.triedJstlInit = true;
      try
      {
        this.loopTagSupportClass = RequestUtils.applicationClass("javax.servlet.jsp.jstl.core.LoopTagSupport");
        
        this.loopTagSupportGetStatus = this.loopTagSupportClass.getDeclaredMethod("getLoopStatus", null);
        
        this.loopTagStatusClass = RequestUtils.applicationClass("javax.servlet.jsp.jstl.core.LoopTagStatus");
        
        this.loopTagStatusGetIndex = this.loopTagStatusClass.getDeclaredMethod("getIndex", null);
        
        this.triedJstlSuccess = true;
      }
      catch (ClassNotFoundException ex) {}catch (NoSuchMethodException ex) {}
    }
    if (this.triedJstlSuccess) {
      try
      {
        Object loopTag = TagSupport.findAncestorWithClass(this, this.loopTagSupportClass);
        if (loopTag == null) {
          return null;
        }
        Object status = this.loopTagSupportGetStatus.invoke(loopTag, null);
        return (Integer)this.loopTagStatusGetIndex.invoke(status, null);
      }
      catch (IllegalAccessException ex)
      {
        log.error(ex.getMessage(), ex);
      }
      catch (IllegalArgumentException ex)
      {
        log.error(ex.getMessage(), ex);
      }
      catch (InvocationTargetException ex)
      {
        log.error(ex.getMessage(), ex);
      }
      catch (NullPointerException ex)
      {
        log.error(ex.getMessage(), ex);
      }
      catch (ExceptionInInitializerError ex)
      {
        log.error(ex.getMessage(), ex);
      }
    }
    return null;
  }
  
  protected void prepareIndex(StringBuffer handlers, String name)
    throws JspException
  {
    int index = 0;
    boolean found = false;
    

    IterateTag iterateTag = (IterateTag)TagSupport.findAncestorWithClass(this, IterateTag.class);
    if (iterateTag == null)
    {
      Integer i = getJstlLoopIndex();
      if (i != null)
      {
        index = i.intValue();
        found = true;
      }
    }
    else
    {
      index = iterateTag.getIndex();
      found = true;
    }
    if (!found)
    {
      JspException e = new JspException(messages.getMessage("indexed.noEnclosingIterate"));
      RequestUtils.saveException(this.pageContext, e);
      throw e;
    }
    if (name != null) {
      handlers.append(name);
    }
    handlers.append("[");
    handlers.append(index);
    handlers.append("]");
    if (name != null) {
      handlers.append(".");
    }
  }
  
  protected String prepareStyles()
    throws JspException
  {
    String value = null;
    StringBuffer styles = new StringBuffer();
    if (this.style != null)
    {
      styles.append(" style=\"");
      styles.append(getStyle());
      styles.append("\"");
    }
    if (this.styleClass != null)
    {
      styles.append(" class=\"");
      styles.append(getStyleClass());
      styles.append("\"");
    }
    if (this.styleId != null)
    {
      styles.append(" id=\"");
      styles.append(getStyleId());
      styles.append("\"");
    }
    value = message(this.title, this.titleKey);
    if (value != null)
    {
      styles.append(" title=\"");
      styles.append(value);
      styles.append("\"");
      styles.append(" titleLabel=\"");
      styles.append(value);
      styles.append("\"");
    }
    value = message(this.alt, this.altKey);
    if (value != null)
    {
      styles.append(" alt=\"");
      styles.append(value);
      styles.append("\"");
    }
    return styles.toString();
  }
  
  protected String prepareEventHandlers()
  {
    StringBuffer handlers = new StringBuffer();
    prepareMouseEvents(handlers);
    prepareKeyEvents(handlers);
    prepareTextEvents(handlers);
    prepareFocusEvents(handlers);
    return handlers.toString();
  }
  
  protected void prepareMouseEvents(StringBuffer handlers)
  {
    if (this.onclick != null)
    {
      handlers.append(" onclick=\"");
      handlers.append(getOnclick());
      handlers.append("\"");
    }
    if (this.ondblclick != null)
    {
      handlers.append(" ondblclick=\"");
      handlers.append(getOndblclick());
      handlers.append("\"");
    }
    if (this.onmouseover != null)
    {
      handlers.append(" onmouseover=\"");
      handlers.append(getOnmouseover());
      handlers.append("\"");
    }
    if (this.onmouseout != null)
    {
      handlers.append(" onmouseout=\"");
      handlers.append(getOnmouseout());
      handlers.append("\"");
    }
    if (this.onmousemove != null)
    {
      handlers.append(" onmousemove=\"");
      handlers.append(getOnmousemove());
      handlers.append("\"");
    }
    if (this.onmousedown != null)
    {
      handlers.append(" onmousedown=\"");
      handlers.append(getOnmousedown());
      handlers.append("\"");
    }
    if (this.onmouseup != null)
    {
      handlers.append(" onmouseup=\"");
      handlers.append(getOnmouseup());
      handlers.append("\"");
    }
  }
  
  protected void prepareKeyEvents(StringBuffer handlers)
  {
    if (this.onkeydown != null)
    {
      handlers.append(" onkeydown=\"");
      handlers.append(getOnkeydown());
      handlers.append("\"");
    }
    if (this.onkeyup != null)
    {
      handlers.append(" onkeyup=\"");
      handlers.append(getOnkeyup());
      handlers.append("\"");
    }
    if (this.onkeypress != null)
    {
      handlers.append(" onkeypress=\"");
      handlers.append(getOnkeypress());
      handlers.append("\"");
    }
  }
  
  protected void prepareTextEvents(StringBuffer handlers)
  {
    if (this.onselect != null)
    {
      handlers.append(" onselect=\"");
      handlers.append(getOnselect());
      handlers.append("\"");
    }
    if (this.onchange != null)
    {
      handlers.append(" onchange=\"");
      handlers.append(getOnchange());
      handlers.append("\"");
    }
  }
  
  protected void prepareFocusEvents(StringBuffer handlers)
  {
    if (this.onblur != null)
    {
      handlers.append(" onblur=\"");
      handlers.append(getOnblur());
      handlers.append("\"");
    }
    if (this.onfocus != null)
    {
      handlers.append(" onfocus=\"");
      handlers.append(getOnfocus());
      handlers.append("\"");
    }
    if (this.disabled) {
      handlers.append(" disabled=\"disabled\"");
    }
    if (this.readonly) {
      handlers.append(" readonly=\"readonly\"");
    }
  }
  
  protected boolean isXhtml()
  {
    String xhtml = (String)this.pageContext.getAttribute("org.apache.struts.globals.XHTML", 1);
    if ("true".equalsIgnoreCase(xhtml)) {
      return true;
    }
    return false;
  }
  
  protected String getElementClose()
  {
    if (isXhtml()) {
      return " />";
    }
    return ">";
  }
  
  protected String lookupProperty(String beanName, String property)
    throws JspException
  {
    Object bean = RequestUtils.lookup(this.pageContext, beanName, null);
    if (bean == null) {
      throw new JspException(messages.getMessage("getter.bean", beanName));
    }
    try
    {
      return BeanUtils.getProperty(bean, property);
    }
    catch (IllegalAccessException e)
    {
      throw new JspException(messages.getMessage("getter.access", property, beanName));
    }
    catch (InvocationTargetException e)
    {
      Throwable t = e.getTargetException();
      throw new JspException(messages.getMessage("getter.result", property, t.toString()));
    }
    catch (NoSuchMethodException e)
    {
      throw new JspException(messages.getMessage("getter.method", property, beanName));
    }
  }
}

/* Location:           C:\workspace2\zhgb\WebContent\WEB-INF\lib\struts.jar
 * Qualified Name:     org.apache.struts.taglib.html.BaseHandlerTag
 * Java Class Version: 1.2 (46.0)
 * JD-Core Version:    0.7.1
 */