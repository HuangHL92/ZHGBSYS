package com.insigma.odin.framework.util;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.lbs.commons.DOMUtil;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GlobalNames
  extends com.lbs.commons.GlobalNames
{
  public static Logger log = Logger.getLogger(GlobalNames.class);
  public static String SYS_OPLOG_CLASS = "";
  public static String SYS_PLANAREA_CLASS = "";
  public static String SYS_CODE_CLASS = "";
  public static String FUNC_TREE_ISONCELOAD = "";
  public static Hashtable<String, String> udSysConfig = new Hashtable();
  public static Hashtable<String, String> sysConfig = new Hashtable();
  
  public static void readConfiguration(InputStream paramInputStream)
  {
    Document localDocument = DOMUtil.loadDocumentFromInputStream(paramInputStream);
    NodeList localNodeList = DOMUtil.getMultiNodes(localDocument, "config");
    String str2;
    Object localObject;
    for (int i = 0; i < localNodeList.getLength(); i++)
    {
      str2 = DOMUtil.getAttributeValue((Element)localNodeList.item(i), "name");
      localObject = DOMUtil.getAttributeValue((Element)localNodeList.item(i), "value");
      sysConfig.put(str2, localObject==null?"":localObject.toString());
      if (str2.indexOf("UD_") >= 0) {
        udSysConfig.put(str2, localObject==null?"":localObject.toString());
      }
      log.info(str2 + " = " + (String)localObject);
    }
    try
    {
      String str1 = null;
      str2 = null;
      localObject = HBUtil.getHBSession().createSQLQuery("select aaa005 from aa01 a where a.aaa001 ='AREA_ID' ").list();
      if ((localObject != null) && (((List)localObject).size() > 0))
      {
        str1 = (String)((List)localObject).iterator().next();
        //str2 = (String)HBUtil.getHBSession().createSQLQuery("select aaa146 from aa26 b where b.aab301='" + str1 + "'").list().iterator().next();
        udSysConfig.put("UD_AREA_ID", str1);
        //udSysConfig.put("UD_AREA_NAME", str2);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    if (sysConfig.get("SYS_OPLOG_CLASS") != null) {
      SYS_OPLOG_CLASS = (String)sysConfig.get("SYS_OPLOG_CLASS");
    }
    if (sysConfig.get("FUNC_TREE_ISONCELOAD") != null) {
      FUNC_TREE_ISONCELOAD = (String)sysConfig.get("FUNC_TREE_ISONCELOAD");
    }
    if (sysConfig.get("SYS_PLANAREA_CLASS") != null) {
      SYS_PLANAREA_CLASS = (String)sysConfig.get("SYS_PLANAREA_CLASS");
    }
    if (sysConfig.get("SYS_CODE_CLASS") != null) {
      SYS_CODE_CLASS = (String)sysConfig.get("SYS_CODE_CLASS");
    }
    if (sysConfig.get("LOGON_PAGE") != null) {
      LOGON_DIALOG_PAGE = (String)sysConfig.get("LOGON_PAGE");
    }
  }
}

/* Location:           D:\git\hzb\WebContent\WEB-INF\lib\odin-util.jar
 * Qualified Name:     com.insigma.odin.framework.util.GlobalNames
 * Java Class Version: 5 (49.0)
 * JD-Core Version:    0.7.1
 */