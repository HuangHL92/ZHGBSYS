package com.insigma.siis.local.util;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.DateUtil;



/**
 * 横纵表值的互相拷贝(横表即基本表，纵表即事件表)
 * @author 周兆巍
 */

public class CopyEventBean 
{

	/**
	 * @$comment 判断是否是基础类型
	 * @param cls 
	 * @return true 基础类型 false 不是基础类型
	 */
	@SuppressWarnings("unchecked")
	public static boolean isBasicClass(Class cls) {
		if (cls == String.class)
			return true;
		else if (cls == Integer.class || cls == int.class)
			return true;
		else if (cls == Short.class || cls == short.class)
			return true;
		else if (cls == Float.class || cls == float.class)
			return true;
		else if (cls == Double.class || cls == double.class)
			return true;
		else if (cls == Long.class || cls == long.class)
			return true;
		else if (cls == java.util.Date.class)
			return true;
		else if (cls == Boolean.class || cls ==boolean.class)
			return true;
		return false;
	}
//	
//	
//	/**
//	 * @$comment 实现了事件表拷贝到DTO或者到其它的实体(适合所有对像的最终版本)
//	 * @param basicTable 目标对象
//	 * @param property 属性名
//	 * @param value 属性值
//	 * @throws AppException
//	 * @code
//	 *  Ab01 ab01=new Ab01();
//	    for (int i = 0; i < ae53Lst.size(); i++) {
//			try {
//				CopyEventBean.eventCopyToBasicFinally(ab01,
//						((Ae53) ae53Lst.get(i)).getAaz312(),(Ae53) ae53Lst.get(i).getAaz313());
//			} catch (Exception e) {
//				throw new AppException("事件表保存到单位表出错!", e);
//			}
//		}
//	 */
//	public static void eventCopyToBasicFinally(Object basicTable,String property,String value) throws AppException 
//	{
//		BeanInfo toBeanInfo = null;	
//		try {
//			toBeanInfo = Introspector.getBeanInfo(basicTable.getClass(),Object.class);
//			PropertyDescriptor[] toProperties = toBeanInfo.getPropertyDescriptors();
//
//					for (PropertyDescriptor toPropertie:toProperties)
//					{
//						String newProperty= toPropertie.getName();
//						String oldProperty=property;
//						
//						if(oldProperty.equalsIgnoreCase(newProperty))
//						{
//							if(value!=null){
//								if(value.equals("")){
//									value="331023413400";//数字标记证明它在事件表中以""形式保存
//								}
//								if(isBasicClass(toPropertie.getPropertyType())&& toPropertie.getPropertyType()==value.getClass())
//									{
//										toPropertie.getWriteMethod().invoke(basicTable,new Object[] { value });
//									    return;
//									}
//									
//									if(toPropertie.getPropertyType()== Long.class||toPropertie.getPropertyType()== long.class)
//								    {
//								    	
//									    if(value.indexOf("-")>0)
//									    {
//									    	toPropertie.getWriteMethod().invoke(basicTable,new Object[] { DateUtil.strDateToNum(value) });
//		                                    return;
//									    }
//									    toPropertie.getWriteMethod().invoke(basicTable,new Object[] { Long.valueOf(value) });
//								    }
//								    if(toPropertie.getPropertyType()== Integer.class||toPropertie.getPropertyType()== int.class)
//							     	{
//								    	toPropertie.getWriteMethod().invoke(basicTable,new Object[] { Integer.valueOf(value) });
//								    	return;
//							     	}
//								    if(toPropertie.getPropertyType()== Float.class||toPropertie.getPropertyType()== float.class)
//								    {
//								    	toPropertie.getWriteMethod().invoke(basicTable,new Object[] { Float.valueOf(value) });
//								    	return;
//								    }
//								    if(toPropertie.getPropertyType()== Date.class)
//								    {
//								    	toPropertie.getWriteMethod().invoke(basicTable,new Object[] { DateUtil.strDateToNum(value) });
//									    return;
//								    }
//								    if(toPropertie.getPropertyType()== Short.class||toPropertie.getPropertyType()== short.class)
//								    {
//								    	toPropertie.getWriteMethod().invoke(basicTable,new Object[] { Short.valueOf(value) });
//									    return;
//								    }
//								    if(toPropertie.getPropertyType()== Double.class||toPropertie.getPropertyType()== double.class)
//								    {
//								    	toPropertie.getWriteMethod().invoke(basicTable,new Object[] { Double.valueOf(value) });
//									    return;
//								    }	
//							  }
//							
//					}
//				  }
//			   
//		}
//		catch(Exception e)
//		{
//			 throw new AppException("复制数据出错(" + e.getMessage() + ")");
//		}
//	}
//	/**
//	 * @deprecated
//	 * @$comment 把eventTable(事件表)中存的数据对应的保存到basicTable(基本表)
//	 * @param eventTable 事件表
//	 * @param basicTable 基本表
//	 * @param property   字段名
//	 * @throws AppException
//	 */
//	public static void eventCopyToBasic(Object eventTable,Object basicTable,String property) throws AppException 
//	{
//		
//		BeanInfo fromBeanInfo = null;
//		BeanInfo toBeanInfo = null;	
//		try {
//			fromBeanInfo = Introspector.getBeanInfo(eventTable.getClass(),Object.class);
//			toBeanInfo = Introspector.getBeanInfo(basicTable.getClass(),Object.class);
//			
//			PropertyDescriptor[] fromProperties = fromBeanInfo.getPropertyDescriptors();
//			PropertyDescriptor[] toProperties = toBeanInfo.getPropertyDescriptors();
//			for (int i = 0; i < fromProperties.length; i++)
//			{
//				if(i==fromProperties.length-2){//获得相应属性的值 避免多次赋值
//					for (int j = 0; j < toProperties.length; j++)
//					{
//						String newProperty= toProperties[j].getName();
//						String oldProperty=property;
//						
//						if(oldProperty.equalsIgnoreCase(newProperty))
//						{
//							if(isBasicClass(fromProperties[i].getPropertyType())&& isBasicClass(toProperties[j].getPropertyType())&& fromProperties[i].getPropertyType() == toProperties[j].getPropertyType())
//							{
//							    toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {}) });
//							    return;
//							}
//								
//							if(toProperties[j].getPropertyType()== Long.class)
//						    {
//						    	
//							    if((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {}).toString()).indexOf("-")>0)
//							    {
//							    	toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { DateUtil.strDateToNum((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString() ) });
//                                    return;
//							    }
//							    toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { Long.valueOf((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString()) });
//						    }
//						    if(toProperties[j].getPropertyType()== Integer.class)
//					     	{
//						    	toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { Integer.valueOf((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString()) });
//						    	return;
//					     	}
//						    if(toProperties[j].getPropertyType()== Float.class)
//						    {
//						    	toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { Float.valueOf((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString()) });
//						    	return;
//						    }
//						    if(toProperties[j].getPropertyType()== Date.class)
//						    {
//							    toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { DateUtil.strDateToNum((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString()) });
//							    return;
//						    }
//						    if(toProperties[j].getPropertyType()== Short.class)
//						    {
//							    toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { Short.valueOf((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString()) });
//							    return;
//						    }
//						    if(toProperties[j].getPropertyType()== Double.class)
//						    {
//							    toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { Double.valueOf((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString()) });
//							    return;
//						    }					    
//
//					}
//				  }
//			   }
//			}
//		}
//		catch(Exception ex)
//		{
//			 throw new AppException("复制数据出错",ex);
//		}
//	}
//
//	/**
//	 * @$comment 实现从基本表到事件表值的拷贝，
//	 *           此方法只适用于单位模块，原因是事件表的字段被定死了。
//	 *           由于是对AE53表的操作，所以对其他表的操作就要作相应的修改，有待完善中。
//	 * @param basicTable 基本表
//	 * @param eventId 事件ID
//	 * @param keyId 事件表的主键
//	 * @param orgId 组织ID
//	 * @return 
//	 * @throws AppException
//	 */
//    public static List<Ae53> basicCopyToEvent(Object basicTable,Long eventId,String keyId,String orgId) throws AppException 
//    {
//    	Ae53 eventTable=new Ae53();
//		BeanInfo fromBeanInfo = null;
//		BeanInfo toBeanInfo = null;	
//		try {			
//			fromBeanInfo = Introspector.getBeanInfo(basicTable.getClass(),Object.class);
//			toBeanInfo = Introspector.getBeanInfo(eventTable.getClass(),Object.class);
//			List<Ae53> eventLst=new java.util.ArrayList<Ae53>();
//			PropertyDescriptor[] fromProperties = fromBeanInfo.getPropertyDescriptors();
//			PropertyDescriptor[] toProperties = toBeanInfo.getPropertyDescriptors();
//			Object newProValue=null;
//			for (int i = 0; i < fromProperties.length; i++)
//			{
//                    if(fromProperties[i].getName().equalsIgnoreCase("aaz001")){newProValue=orgId;}
//                    else{newProValue=fromProperties[i].getReadMethod().invoke(basicTable,new Object[] {});}            
//                    if(newProValue==null||newProValue.equals("")||newProValue.equals("null")){continue;}	
//				    Ae53 ae53=new Ae53();				    
//					for (int j = 0; j < toProperties.length; j++)
//					{
//                        if(fromProperties[i].getName().equalsIgnoreCase("ab02Data")||fromProperties[i].getName().equalsIgnoreCase("ab31Set")||fromProperties[i].getName().equalsIgnoreCase("ae52Data")||fromProperties[i].getName().equals("list_ae52"))
//                        {continue;}			
//						String oldProperty=fromProperties[i].getName();
//						String fromProperty=toProperties[j].getName();
//						if(fromProperty.equalsIgnoreCase("eaz069")){//组织登记明细id
//							String seqcode=HBUtil.getSequence(keyId);
//							ae53.setEaz069(Long.parseLong(seqcode));//事件表ae53的主键
//						}
//						if(fromProperty.equalsIgnoreCase("aaz308")){//事件ID
//							ae53.setAaz308(eventId);
//						}
//						if(fromProperty.equalsIgnoreCase("aaz312")){//属性名
//							ae53.setAaz312(oldProperty.toUpperCase());
//						}
//						if(fromProperty.equalsIgnoreCase("aaz313")){//属性值
//							ae53.setAaz313(newProValue.toString());
//						}
//						if(j==toProperties.length-1){
//							eventLst.add(ae53);
//						}
//																
//					}
//			}
//			return eventLst;
//		}
//		catch(Exception e)
//		{
//			 throw new AppException("复制数据出错(" + e.getMessage() + ")");
//		}    	
//    }
//	
//    /**
//	 * @$comment 实现从基本表到纵表值的拷贝
//	 *           此方法现在只适用于单位模块，原因是事件表的字段被定死了。
//	 *           由于是对AE19表的操作，所以对其他表的操作就要作相应的修改，有待完善中。
//     * @param basicTable_Old 从数据库里查出的修改前的对象
//     * @param basicTable_New 从界面或者人员处理后的对象
//     * @param eventId 事件ID
//     * @param tableName 基本表的表名
//     * @return List<Ae19> 
//     * @throws AppException
//     */
//	@SuppressWarnings("unchecked")
//	public static List<Ae19> basicCopyToEventForUpdate(Object basicTable_Old,Object basicTable_New,Long eventId,String tableName) throws AppException
//	{
//		Ae19 eventTable=new Ae19();
//		BeanInfo oldBeanInfo = null;
//		BeanInfo newBeanInfo = null;
//		BeanInfo toBeanInfo = null;	
//		
//		try {
//			oldBeanInfo = Introspector.getBeanInfo(basicTable_Old.getClass(),
//					Object.class);
//			newBeanInfo = Introspector.getBeanInfo(basicTable_New.getClass(),
//					Object.class);
//			toBeanInfo  = Introspector.getBeanInfo(eventTable.getClass(),
//					Object.class);
//			List<Ae19> eventLst=new java.util.ArrayList<Ae19>();
//			PropertyDescriptor[] oleProperties = oldBeanInfo
//					.getPropertyDescriptors();
//			PropertyDescriptor[] newProperties = newBeanInfo
//					.getPropertyDescriptors();
//			PropertyDescriptor[] toProperties  = toBeanInfo
//			        .getPropertyDescriptors();
//
//			for (int i = 0; i < oleProperties.length; i++)
//				for (int j = 0; j < newProperties.length; j++)
//					if (oleProperties[i].getName().equals(newProperties[j].getName())) 
//					{
//						if(oleProperties[i].getName().equalsIgnoreCase("aaz308"))
//						{
//							break;
//						}
//						if (isBasicClass(oleProperties[i].getPropertyType())&& isBasicClass(newProperties[j].getPropertyType()))
//						{
//							Object oldValue=oleProperties[i].getReadMethod().invoke(basicTable_Old, new Object[]{});
//							Object newValue=newProperties[j].getReadMethod().invoke(basicTable_New, new Object[]{});
//							if(oldValue==null){
//								oldValue="";
//							}
//							if(newValue!=null&&newValue.toString().equalsIgnoreCase("1000057863456")){
//								newValue="";							
//							}
//							if(newValue!=null&&!newValue.toString().equalsIgnoreCase("null")&&!newValue.toString().equalsIgnoreCase(oldValue.toString())){
//								CommonQueryBS query=new CommonQueryBS();
//								query.setConnection(HBUtil.getHBSession().connection());
//								String column_name=newProperties[j].getName().toUpperCase();
//								query.setQuerySQL("select s.comments from user_col_comments s where s.table_name ='"+tableName+"' and s.column_name ='"+column_name+"'");
//								Vector<?> vector=query.query();
//								String comments=null;
//								Iterator<?> iterator = vector.iterator();
//								if (iterator.hasNext())
//					            {
//									HashMap tmp= (HashMap)iterator.next();
//									comments=(String)tmp.get("comments");
//								}							
//							    Ae19 ae19=new Ae19();
//							    String aaz019=HBUtil.getSequence("SQ_AAZ019");
//								for (int k = 0; k < toProperties.length; k++)
//								{				
//									String fromProperty=toProperties[k].getName();
//									if(fromProperty.equalsIgnoreCase("aaz019")){//组织信息变更明细ID
//										ae19.setAaz019(Long.parseLong(aaz019));
//									}
//									if(fromProperty.equalsIgnoreCase("eae001")){//表名
//										ae19.setEae001(tableName);
//									}
//									if(fromProperty.equalsIgnoreCase("aae122")){//变更项目
//										ae19.setAae122(oleProperties[i].getName().toUpperCase());
//									}
//									if(fromProperty.equalsIgnoreCase("aae123")){//变更前信息
//										ae19.setAae123(oldValue.toString());
//									}
//									if(fromProperty.equalsIgnoreCase("aae124")){//变更后信息
//										ae19.setAae124(newValue.toString());
//									}
//									if(fromProperty.equalsIgnoreCase("aaz308")){//组织信息变更事件ID
//										ae19.setAaz308(eventId);
//									}
//									if(fromProperty.equalsIgnoreCase("aae121")){//变更项中文含义
//										ae19.setAae121(comments);
//									}
//									if(k==toProperties.length-1){
//									    eventLst.add(ae19);	
//									}
//								}	
//								
//							}								
//							break;
//						}
//					}	
//						
//			return eventLst;
//		} catch (Exception e) {
//			throw new AppException("属性复制失败(" + e.getMessage() + ")");
//		}
//		
//	}
//	
//	/**
//	 * @deprecated
//	 * @$comment 把eventTable(事件表)中存的数据对应的保存到basicTable(基本表)
//	 * @param eventTable 事件表
//	 * @param basicTable 基本表
//	 * @param property 事件表中存横表属性的字段值
//	 * @throws AppException
//	 */
//	public static void eventCopyToBasicForUpdate(Object eventTable,Object basicTable,String property) throws AppException 
//	{
//		
//		BeanInfo fromBeanInfo = null;
//		BeanInfo toBeanInfo = null;	
//		try {
//			fromBeanInfo = Introspector.getBeanInfo(eventTable.getClass(),Object.class);
//			toBeanInfo = Introspector.getBeanInfo(basicTable.getClass(),Object.class);
//			
//			PropertyDescriptor[] fromProperties = fromBeanInfo.getPropertyDescriptors();
//			PropertyDescriptor[] toProperties = toBeanInfo.getPropertyDescriptors();
//			for (int i = 0; i < fromProperties.length; i++)
//			{
//				if(i==fromProperties.length-4){//获得相应属性的值 避免多次赋值
//					for (int j = 0; j < toProperties.length; j++)
//					{
//						String newProperty= toProperties[j].getName();
//						String oldProperty=property;
//						
//						if(oldProperty.equalsIgnoreCase(newProperty))
//						{
//							if(isBasicClass(fromProperties[i].getPropertyType())&& isBasicClass(toProperties[j].getPropertyType())&& fromProperties[i].getPropertyType() == toProperties[j].getPropertyType())
//							{
//							    toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {}) });
//							    return;
//							}
//								
//							if(toProperties[j].getPropertyType()== Long.class)
//						    {
//						    	
//							    if((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {}).toString()).indexOf("-")>0)
//							    {
//							    	toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { DateUtil.strDateToNum((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString() ) });
//                                    return;
//							    }
//							    toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { Long.valueOf((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString()) });
//						    }
//						    if(toProperties[j].getPropertyType()== Integer.class)
//					     	{
//						    	toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { Integer.valueOf((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString()) });
//						    	return;
//					     	}
//						    if(toProperties[j].getPropertyType()== Float.class)
//						    {
//						    	toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { Float.valueOf((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString()) });
//						    	return;
//						    }
//						    if(toProperties[j].getPropertyType()== Date.class)
//						    {
//							    toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { DateUtil.strDateToNum((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString()) });
//							    return;
//						    }
//						    if(toProperties[j].getPropertyType()== Short.class)
//						    {
//							    toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { Short.valueOf((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString()) });
//							    return;
//						    }
//						    if(toProperties[j].getPropertyType()== Double.class)
//						    {
//							    toProperties[j].getWriteMethod().invoke(basicTable,new Object[] { Double.valueOf((fromProperties[i].getReadMethod().invoke(eventTable,new Object[] {})).toString()) });
//							    return;
//						    }					    
//
//					}
//				  }
//			   }
//			}
//		}
//		catch(Exception ex)
//		{
//			 throw new AppException("复制数据出错(" + ex.getMessage() + ")");
//		}
//	}
//	
//
//	/**
//	 * @$comment 实现从基本表到纵表值的拷贝。
//	 *           此方法现在只适用于单位模块，原因是事件表的字段被定死了。
//	 *           由于是对AC54表的操作，所以对其他表的操作就要作相应的修改，有待完善中。
//	 * @param basicTable 要保存到事件表里的对象
//	 * @param eventId 事件ID
//	 * @param keyId 事件表的主键
//	 * @param personId 人员ID
//	 * @return List<Ac54>
//	 * @throws AppException
//	 */
//    /*public static List<Ac54> basicCopyToEvent_Ac54(Object basicTable,Long eventId,String keyId,String personId) throws AppException 
//    {
//    	Ac54 eventTable=new Ac54();
//		BeanInfo fromBeanInfo = null;
//		BeanInfo toBeanInfo = null;	
//		try {			
//			fromBeanInfo = Introspector.getBeanInfo(basicTable.getClass(),Object.class);
//			toBeanInfo = Introspector.getBeanInfo(eventTable.getClass(),Object.class);
//			List<Ac54> eventLst=new java.util.ArrayList<Ac54>();
//			PropertyDescriptor[] fromProperties = fromBeanInfo.getPropertyDescriptors();
//			PropertyDescriptor[] toProperties = toBeanInfo.getPropertyDescriptors();
//			Object newProValue=null;
//			for (int i = 0; i < fromProperties.length; i++)
//			{
//	                if(fromProperties[i].getName().equalsIgnoreCase("ab02Data")||fromProperties[i].getName().equalsIgnoreCase("ab31Set"))
//	                {continue;}
//	                if(fromProperties[i].getName().equalsIgnoreCase("aac001")){newProValue=personId;}
//	                else{newProValue=fromProperties[i].getReadMethod().invoke(basicTable,new Object[] {});}            
//	                if(newProValue==null||newProValue.equals("")){continue;}	
//				    Ac54 ac54=new Ac54();				    
//					for (int j = 0; j < toProperties.length; j++)
//					{
//						String oldProperty=fromProperties[i].getName();
//						String newProperty= toProperties[j].getName();
//						if(newProperty.equalsIgnoreCase("aaz308")){
//                            ac54.setAaz308(eventId);
//						}
//						if(newProperty.equalsIgnoreCase("eaz075")){
//							String seqcode=HBUtil.getSequence(keyId);
//							ac54.setEaz075(Long.parseLong(seqcode));
//						}
//						if(newProperty.equalsIgnoreCase("aaz312")){
//							ac54.setAaz312(oldProperty.toUpperCase());
//						}
//						if(newProperty.equalsIgnoreCase("aaz313")){
//							ac54.setAaz313(newProValue.toString());
//						}
//						if(j==toProperties.length-1){
//							eventLst.add(ac54);
//						}	
//					}
//					
//			}
//			return eventLst;
//		}
//		catch(Exception ex)
//		{
//			 throw new AppException("复制数据出错(" + ex.getMessage() + ")");
//		}    	
//    }*/
//	
//    /**
//	 * @$comment 实现从基本表到纵表值的拷贝。
//	 *           此方法现在只适用于单位模块，原因是事件表的字段被定死了。
//	 *           由于是对AB34表的操作，所以对其他表的操作就要作相应的修改，有待完善中。
//     * @param basicTable_Old 从数据库里查出的修改前的对象
//     * @param basicTable_New 从界面或者人员处理后的对象
//     * @param eventId 事件ID
//     * @param tableName 基本表的表名
//     * @return List<Ab34>
//     * @throws AppException
//     */ 
//    @SuppressWarnings("unchecked")
//	public static List<Ab34> basicCopyToEventForUpdate_Ab34(Object basicTable_Old,Object basicTable_New,Long eventId,String tableName) throws AppException{
//		Ab34 eventTable=new Ab34();
//		BeanInfo oldBeanInfo = null;
//		BeanInfo newBeanInfo = null;
//		BeanInfo toBeanInfo = null;	
//		
//		try {
//			oldBeanInfo = Introspector.getBeanInfo(basicTable_Old.getClass(),
//					Object.class);
//			newBeanInfo = Introspector.getBeanInfo(basicTable_New.getClass(),
//					Object.class);
//			toBeanInfo  = Introspector.getBeanInfo(eventTable.getClass(),
//					Object.class);
//			List<Ab34> eventLst=new java.util.ArrayList<Ab34>();
//			PropertyDescriptor[] oleProperties = oldBeanInfo
//					.getPropertyDescriptors();
//			PropertyDescriptor[] newProperties = newBeanInfo
//					.getPropertyDescriptors();
//			PropertyDescriptor[] toProperties  = toBeanInfo
//			        .getPropertyDescriptors();
//
//			for (int i = 0; i < oleProperties.length; i++)
//				for (int j = 0; j < newProperties.length; j++)
//					if (oleProperties[i].getName().equals(newProperties[j].getName())) 
//					{
//						if(oleProperties[i].getName().equalsIgnoreCase("aaz308"))
//						{
//							break;
//						}
//						if (isBasicClass(oleProperties[i].getPropertyType())&& isBasicClass(newProperties[j].getPropertyType()))
//						{
//							Object oldValue=oleProperties[i].getReadMethod().invoke(basicTable_Old, new Object[]{});
//							Object newValue=newProperties[j].getReadMethod().invoke(basicTable_New, new Object[]{});
//							if(oldValue==null){
//								oldValue="";
//							}
//							if(newValue!=null&&!newValue.toString().equalsIgnoreCase("null")&&!newValue.toString().equalsIgnoreCase("")&&!newValue.toString().equalsIgnoreCase(oldValue.toString())){
//								CommonQueryBS query=new CommonQueryBS();
//								query.setConnection(HBUtil.getHBSession().connection());
//								String column_name=newProperties[j].getName().toUpperCase();
//								query.setQuerySQL("select s.comments from user_col_comments s where s.table_name ='"+tableName+"' and s.column_name ='"+column_name+"'");
//								Vector<?> vector=query.query();
//								String comments=null;
//								Iterator<?> iterator = vector.iterator();
//								if (iterator.hasNext())
//					            {
//									HashMap tmp= (HashMap)iterator.next();
//									comments=(String)tmp.get("comments");
//								}							
//							    Ab34 ab34=new Ab34();
//							    String aaz045=HBUtil.getSequence("SQ_AAZ045");
//							    
//								for (int k = 0; k < toProperties.length; k++)
//								{		
//									String toProperty= toProperties[k].getName();		
//									if(toProperty.equalsIgnoreCase("aaz045")){//单位参保情况变更明细ID
//										ab34.setAaz045(Long.parseLong(aaz045));
//									}
//									if(toProperty.equalsIgnoreCase("eae001")){//表名
//										ab34.setEae001(tableName);
//									}
//									if(toProperty.equalsIgnoreCase("aae122")){//变更项目
//										ab34.setAae122(oleProperties[i].getName().toUpperCase());
//									}
//									if(toProperty.equalsIgnoreCase("aae123")){//变更前信息
//										ab34.setAae123(oldValue.toString());
//									}
//									if(toProperty.equalsIgnoreCase("aae124")){//变更后信息
//										ab34.setAae124(newValue.toString());
//									}
//									if(toProperty.equalsIgnoreCase("aaz308")){//单位险种参保情况变更事件ID
//										ab34.setAaz308(eventId);
//									}
//									if(toProperty.equalsIgnoreCase("aae121")){//变更中文含义
//										ab34.setAae121(comments);
//									}
//					                if(k==toProperties.length-1){
//					                	eventLst.add(ab34);
//					                }	
//								}																		
//							}								
//							break;
//						}
//					}	
//						
//			return eventLst;
//		} catch (Exception e) {
//			throw new AppException("属性复制失败(" + e.getMessage() + ")");
//		}   	
//    }
//
//    /**
//	 * @$comment 实现从基本表到纵表值的拷贝。
//	 *           此方法现在只适用于单位模块，原因是事件表的字段被定死了。
//	 *           由于是对AC24表的操作，所以对其他表的操作就要作相应的修改，有待完善中。
//     * @param basicTable_Old 从数据库里查出的修改前的对象
//     * @param basicTable_New 从界面或者人员处理后的对象
//     * @param eventId 事件ID
//     * @param tableName 基本表的表名
//     * @return List<Ac24>
//     * @throws AppException
//     */ 
//	@SuppressWarnings("unchecked")
//	public static List<Ac24> basicCopyToEventForUpdate_Ac24(Object basicTable_Old,Object basicTable_New,Long eventId,String tableName) throws AppException{
//		Ac24 eventTable=new Ac24();
//		BeanInfo oldBeanInfo = null;
//		BeanInfo newBeanInfo = null;
//		BeanInfo toBeanInfo = null;	
//		
//		try {
//			oldBeanInfo = Introspector.getBeanInfo(basicTable_Old.getClass(),
//					Object.class);
//			newBeanInfo = Introspector.getBeanInfo(basicTable_New.getClass(),
//					Object.class);
//			toBeanInfo  = Introspector.getBeanInfo(eventTable.getClass(),
//					Object.class);
//			List<Ac24> eventLst=new java.util.ArrayList<Ac24>();
//			PropertyDescriptor[] oleProperties = oldBeanInfo
//					.getPropertyDescriptors();
//			PropertyDescriptor[] newProperties = newBeanInfo
//					.getPropertyDescriptors();
//			PropertyDescriptor[] toProperties  = toBeanInfo
//			        .getPropertyDescriptors();
//
//			for (int i = 0; i < oleProperties.length; i++)
//				for (int j = 0; j < newProperties.length; j++)
//					if (oleProperties[i].getName().equals(newProperties[j].getName())) 
//					{
//						if(oleProperties[i].getName().equalsIgnoreCase("aaz308"))
//						{
//							break;
//						}
//						if (isBasicClass(oleProperties[i].getPropertyType())&& isBasicClass(newProperties[j].getPropertyType()))
//						{
//							Object oldValue=oleProperties[i].getReadMethod().invoke(basicTable_Old, new Object[]{});
//							Object newValue=newProperties[j].getReadMethod().invoke(basicTable_New, new Object[]{});
//							if(oldValue==null){
//								oldValue="";
//							}
//							if(newValue!=null&&!newValue.toString().equalsIgnoreCase("null")&&!newValue.toString().equalsIgnoreCase("")&&!newValue.toString().equalsIgnoreCase(oldValue.toString())){
//								CommonQueryBS query=new CommonQueryBS();
//								query.setConnection(HBUtil.getHBSession().connection());
//								String column_name=newProperties[j].getName().toUpperCase();
//								query.setQuerySQL("select s.comments from user_col_comments s where s.table_name ='"+tableName+"' and s.column_name ='"+column_name+"'");
//								Vector<?> vector=query.query();
//								String comments=null;
//								Iterator<?> iterator = vector.iterator();
//								if (iterator.hasNext())
//					            {
//									HashMap tmp= (HashMap)iterator.next();
//									comments=(String)tmp.get("comments");
//								}							
//							    Ac24 ac24=new Ac24();
//							    String aaz062=HBUtil.getSequence("SQ_AAZ162");
//								for (int k = 0; k < toProperties.length; k++)
//								{		
//									String toProperty= toProperties[k].getName();
//									if(toProperty.equalsIgnoreCase("aaz162")){//人员参保记录变更明细ID
//										ac24.setAaz162(Long.parseLong(aaz062));	
//									}
//									if(toProperty.equalsIgnoreCase("eae001")){//表名
//										ac24.setEae001(tableName);
//									}
//									if(toProperty.equalsIgnoreCase("aae122")){//变更项目
//										ac24.setAae122(oleProperties[i].getName().toUpperCase());
//									}
//									if(toProperty.equalsIgnoreCase("aae123")){//变更前信息
//										ac24.setAae123(oldValue.toString());
//									}
//									if(toProperty.equalsIgnoreCase("aae124")){//变更后信息
//										ac24.setAae124(newValue.toString());
//									}
//									if(toProperty.equalsIgnoreCase("aaz308")){//人员险种参保记录变更事件ID
//										ac24.setAaz308(eventId);
//									}
//									if(toProperty.equalsIgnoreCase("aae121")){//中文含义
//										ac24.setAae121(comments);
//									}
//									if(k==toProperties.length-1){
//										eventLst.add(ac24);
//									}																			
//								}	
//								
//							}								
//							break;
//						}
//					}	
//						
//			return eventLst;
//		} catch (Exception e) {
//			throw new AppException("属性复制失败(" + e.getMessage() + ")");
//		}   	
//    }
    
	 /**
     * @$comment 实现了所有属性名相同的属性值的拷贝而不用考虑它的类型
     * 前提是属性名相同，基于属性的字段名来拷值：
     * 对所有类型的注释:这里的所有类型指的是在CopyEventBean.isBasicClass方法中校验的类型,以后会加上其它需要的类型。
     * 功能：
     * 1、所有类型相同的属性的值的互拷。
     * 2、所有类型到String型的值的互拷。
     * 3、String型到其他所有类型的值的互拷。
     * 4、Date型到Long型的的属性的值的互拷。
     * 5、String中特殊的比如日期的字符格式（"2008-04-01"）到Long型的属性的值的拷贝，单向的，不能实现Long到String这种类型。
     * 6、String中特殊的比如日期的字符格式（"2008-04-01"）到Date型的属性的值的拷贝，单向的，不能实现Date到String这种类型。
     * 7、等等。
     * 缺憾：
     * 1、无法实现比如像Double到Long的转换。
     * 2、以及还无法实现的类型互拷，只能在使用出现时再加上取。
     * @param fromObj 被复制对象
     * @param toObj 复制对象
     * @throws AppException
	 * @code
	 * public class Ab01 {
          private String eab001;
          private Long eab002;
          private Double eab003;
          ......
        }
        public class Test {
          private String eab001;
          private String eab002;
          private String eab003;
          ......
        }
	    Ab01 ab01=new Ab01();
        Test test=new Test();
		test.setEab001("1");
		test.setEab002(Long.valueOf("3333333333"));
		test.setEab003(Double.valueOf("22.0"));
        copyIgnoreProperty.Copy(test,ab01);
     */
	@SuppressWarnings("deprecation")
	public static  void copy(Object fromObj, Object toObj) throws AppException {
		BeanInfo fromBeanInfo = null;
		BeanInfo toBeanInfo = null;
		
		try {
			fromBeanInfo = Introspector.getBeanInfo(fromObj.getClass(),
					Object.class);
			toBeanInfo = Introspector.getBeanInfo(toObj.getClass(),
					Object.class);

			PropertyDescriptor[] fromProperties = fromBeanInfo
					.getPropertyDescriptors();
			PropertyDescriptor[] toProperties = toBeanInfo
					.getPropertyDescriptors();
			Map<String,PropertyDescriptor> map = new HashMap<String,PropertyDescriptor>(fromProperties.length);
			for (int i = 0; i < fromProperties.length; i++){
				map.put(fromProperties[i].getDisplayName().toLowerCase(),fromProperties[i]);
			}
			for(int i=0;i< toProperties.length; i++){
				PropertyDescriptor toPropertie = toProperties[i];
				if(!map.containsKey(toPropertie.getDisplayName().toLowerCase()))
					continue;
				PropertyDescriptor fromPropertie = map.get(toPropertie.getDisplayName().toLowerCase());
				Object fromValue=fromPropertie.getReadMethod().invoke(fromObj,new Object[] {});
				//if(fromValue==null||fromValue.toString().equalsIgnoreCase("")) //郑文勇 2010-04-09 注释掉
				if(fromValue==null)
					continue;
				if (isBasicClass(fromPropertie.getPropertyType())&& isBasicClass(toPropertie.getPropertyType())&& fromPropertie.getPropertyType() == toPropertie.getPropertyType())
					toPropertie.getWriteMethod().invoke(toObj,new Object[] { fromValue });
				else if(isBasicClass(fromPropertie.getPropertyType())&&
						isBasicClass(toPropertie.getPropertyType())&&
						fromPropertie.getPropertyType()!=toPropertie.getPropertyType()){
					
						if(toPropertie.getPropertyType()== Long.class||toPropertie.getPropertyType()== long.class)
					    {	
						    if(fromValue.toString().indexOf("-")>0)
						    {
						    	if(fromValue.toString().length()>7)
						    		toPropertie.getWriteMethod().invoke(toObj,new Object[] { DateUtil.strDateToNum(fromValue.toString().substring(0, 10)) });
						    	else
						    		toPropertie.getWriteMethod().invoke(toObj,new Object[] { DateUtil.strDateToNum(fromValue.toString().substring(0, 7)) });
						    }
						    else if(fromValue.getClass()==Date.class)
						    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { DateUtil.strDateToNum(DateUtil.formatDate((Date)fromValue)) });
						    else if(!"".equalsIgnoreCase(fromValue.toString()))
						    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Long.valueOf(fromValue.toString()) });
					    }
						if(toPropertie.getPropertyType()== Integer.class||toPropertie.getPropertyType()== int.class)
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Integer.valueOf(fromValue.toString()) });
					    if(toPropertie.getPropertyType()== Float.class||toPropertie.getPropertyType()== float.class)
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Float.valueOf(fromValue.toString()) });
					    if(toPropertie.getPropertyType()== java.util.Date.class){
					    	if(fromValue.toString().length()<=10&&fromValue.toString().length()>=8)
					    		toPropertie.getWriteMethod().invoke(toObj,new Object[]{DateUtil.stringToDate(fromValue.toString().replaceAll("/","").replaceAll("-",""),DateUtil.COMPACT_DATE_FORMAT)});
					    	else if(fromValue.toString().length()<8&&fromValue.toString().length()>=6)
					    		toPropertie.getWriteMethod().invoke(toObj,new Object[]{DateUtil.stringToDate(fromValue.toString().replaceAll("/","").replaceAll("-",""),"yyyyMM")});
					    	else if(fromValue.toString().length()>10)
					    		toPropertie.getWriteMethod().invoke(toObj,new Object[]{DateUtil.stringToDate(fromValue.toString(),DateUtil.NORMAL_DATE_FORMAT_NEW)});
					    }
					    if(toPropertie.getPropertyType()== Short.class||toPropertie.getPropertyType()== short.class)
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Short.valueOf(fromValue.toString()) });
					    if(toPropertie.getPropertyType()== Double.class||toPropertie.getPropertyType()== double.class)
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Double.valueOf(fromValue.toString()) });
					    if(toPropertie.getPropertyType()== String.class)
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { fromValue.toString() });
					
					
				}
			}
		} catch (Exception e) {
			throw new AppException("属性复制失败(" + e.getMessage() + ")");
		}
	}

}

