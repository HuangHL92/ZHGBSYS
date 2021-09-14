package com.insigma.odin.framework.radow.util;

import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.util.GlobalNames;
import java.util.Hashtable;

public class PubJSUtil
{
  public static String getDisableScript(PageElement paramPageElement, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramPageElement.isParentPageElement()) {
      localStringBuffer.append("parent.");
    }
    if ((paramPageElement.getType().equals(ElementType.SELECT)) || (paramPageElement.getType().equals(ElementType.DATE)))
    {
      String str = paramPageElement.getId();
      if (paramPageElement.getType().equals(ElementType.SELECT)) {
        str = str + "_combo";
      }
      if (paramBoolean) {
        localStringBuffer.append("odin.ext.getCmp('" + str + "').disable();");
      } else {
        localStringBuffer.append("odin.ext.getCmp('" + str + "').enable();");
      }
    }
    else if (paramBoolean)
    {
      localStringBuffer.append("odin.ext.getCmp('" + paramPageElement.getId() + "').disable();");
    }
    else
    {
      localStringBuffer.append("odin.ext.getCmp('" + paramPageElement.getId() + "').enable();");
    }
    return localStringBuffer.toString();
  }
  
  public static String getDisplayScript(PageElement paramPageElement, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramPageElement.isParentPageElement()) {
      localStringBuffer.append("parent.");
    }
    if (paramPageElement.getType().equals(ElementType.NORMAL)) {
      _$1(localStringBuffer, (paramPageElement.getId() == null) || (paramPageElement.getId().equals("")) ? paramPageElement.getName() : paramPageElement.getId(), "style.display", paramBoolean ? "" : "none");
    } else if (paramPageElement.getType().equals(ElementType.TAB)) {
      localStringBuffer.append("odin.ext.getCmp('" + paramPageElement.getId() + "')." + (paramBoolean ? "show" : "hide") + "();");
    }
    return localStringBuffer.toString();
  }
  
  public static String getReadOnlyScript(PageElement paramPageElement, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramPageElement.isParentPageElement()) {
      localStringBuffer.append("parent.");
    }
    if (paramPageElement.getType().equals(ElementType.DATE))
    {
      localStringBuffer.append("odin.setDateReadOnly('");
      localStringBuffer.append(paramPageElement.getId());
      localStringBuffer.append("',");
      localStringBuffer.append(paramBoolean);
      localStringBuffer.append(");");
    }
    else if (paramPageElement.getType().equals(ElementType.SELECT))
    {
      localStringBuffer.append("odin.setComboReadOnly('");
      localStringBuffer.append(paramPageElement.getId());
      localStringBuffer.append("',");
      localStringBuffer.append(paramBoolean);
      localStringBuffer.append(");");
    }
    else if (paramPageElement.getType().equals(ElementType.CHECKBOX))
    {
      _$1(localStringBuffer, (paramPageElement.getId() == null) || (paramPageElement.getId().equals("")) ? paramPageElement.getName() : paramPageElement.getId(), "disabled", !paramBoolean);
    }
    else
    {
      _$1(localStringBuffer, (paramPageElement.getId() == null) || (paramPageElement.getId().equals("")) ? paramPageElement.getName() : paramPageElement.getId(), "readOnly", paramBoolean);
    }
    String str = (String)GlobalNames.sysConfig.get("READONLY_COLOR");
    if ((str != null) && (!str.trim().equals("")))
    {
      localStringBuffer.append("document.getElementById('");
      localStringBuffer.append(paramPageElement.getId());
      if (paramPageElement.getType().equals(ElementType.SELECT)) {
        localStringBuffer.append("_combo");
      }
      if (paramBoolean) {
        localStringBuffer.append("').style.background = '" + str + "';");
      } else {
        localStringBuffer.append("').style.background = '';");
      }
    }
    return localStringBuffer.toString();
  }
  
  private static void _$1(StringBuffer paramStringBuffer, String paramString1, String paramString2, boolean paramBoolean)
  {
    paramStringBuffer.append("document.getElementById('");
    paramStringBuffer.append(paramString1);
    paramStringBuffer.append("').");
    paramStringBuffer.append(paramString2);
    paramStringBuffer.append("=");
    paramStringBuffer.append(paramBoolean);
    paramStringBuffer.append(";");
  }
  
  private static void _$1(StringBuffer paramStringBuffer, String paramString1, String paramString2, Object paramObject)
  {
    paramStringBuffer.append("document.getElementById('");
    paramStringBuffer.append(paramString1);
    paramStringBuffer.append("').");
    paramStringBuffer.append(paramString2);
    paramStringBuffer.append("='");
    paramStringBuffer.append(paramObject);
    paramStringBuffer.append("';");
  }
  
  public static String getValueScript(PageElement paramPageElement, String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    paramString = paramString.replaceAll("'", "\\\\'");
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramPageElement.isParentPageElement()) {
      localStringBuffer.append("parent.");
    }
    if (paramPageElement.getType().equals(ElementType.CHECKBOX))
    {
      _$1(localStringBuffer, (paramPageElement.getId() == null) || (paramPageElement.getId().equals("")) ? paramPageElement.getName() : paramPageElement.getId(), "checked", paramString.equals("1"));
      return localStringBuffer.toString();
    }
    if (paramPageElement.getType().equals(ElementType.RADIO))
    {
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("var ans=document.getElementsByName('");
      localStringBuffer.append((paramPageElement.getId() == null) || (paramPageElement.getId().equals("")) ? paramPageElement.getName() : paramPageElement.getId());
      localStringBuffer.append("');");
      localStringBuffer.append("for(var i=0;i<ans.length;i++){if(ans[i].value=='" + paramString + "'){ans[i].checked=true;}};");
      return localStringBuffer.toString();
    }
    if (paramPageElement.getType().equals(ElementType.SELECT))
    {
      localStringBuffer.append("odin.setSelectValue('");
      localStringBuffer.append(paramPageElement.getId());
      localStringBuffer.append("','");
      localStringBuffer.append(paramString);
      localStringBuffer.append("');");
      return localStringBuffer.toString();
    }
    if (paramPageElement.getType().equals(ElementType.NUMBER)) {
      localStringBuffer.append("document.getElementById('" + ((paramPageElement.getId() == null) || (paramPageElement.getId().equals("")) ? paramPageElement.getName() : paramPageElement.getId()) + "').value=parseFloat('" + paramString + "',10);");
    }
	
    _$1(localStringBuffer, (paramPageElement.getId() == null) || (paramPageElement.getId().equals("")) ? paramPageElement.getName() : paramPageElement.getId(), "value", paramString);
   
    try {
	    if(paramPageElement.getId()!=null&&!"".equals(paramPageElement.getId())){
//日期格式化
			if(paramPageElement.getPm().getPageElement(paramPageElement.getId()+"_1")!=null){
				if(paramString!=null&&paramString.length()>=6){
					localStringBuffer.append("document.getElementById('" + paramPageElement.getId()+"_1').value='"+paramString.substring(0,4)+"."+paramString.substring(4,6)+"';");
				}else{
					localStringBuffer.append("document.getElementById('" + paramPageElement.getId()+"_1').value='';");
				}
				
				//localStringBuffer.append("document.getElementById('" + paramPageElement.getId()+"_1').focus();");
				//localStringBuffer.append("document.getElementById('" + paramPageElement.getId()+"_1').blur();");

			}
	    }
	} catch (RadowException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
    }
    
    return localStringBuffer.toString();
  }
}

/* Location:           C:\workspace2\zhgb\WebContent\WEB-INF\lib\odin-radow.jar
 * Qualified Name:     com.insigma.odin.framework.radow.util.PubJSUtil
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.7.1
 */