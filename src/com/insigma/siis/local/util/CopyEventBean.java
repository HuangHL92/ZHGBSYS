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
 * ���ݱ�ֵ�Ļ��࿽��(����������ݱ��¼���)
 * @author ����Ρ
 */

public class CopyEventBean 
{

	/**
	 * @$comment �ж��Ƿ��ǻ�������
	 * @param cls 
	 * @return true �������� false ���ǻ�������
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
//	 * @$comment ʵ�����¼�������DTO���ߵ�������ʵ��(�ʺ����ж�������հ汾)
//	 * @param basicTable Ŀ�����
//	 * @param property ������
//	 * @param value ����ֵ
//	 * @throws AppException
//	 * @code
//	 *  Ab01 ab01=new Ab01();
//	    for (int i = 0; i < ae53Lst.size(); i++) {
//			try {
//				CopyEventBean.eventCopyToBasicFinally(ab01,
//						((Ae53) ae53Lst.get(i)).getAaz312(),(Ae53) ae53Lst.get(i).getAaz313());
//			} catch (Exception e) {
//				throw new AppException("�¼����浽��λ�����!", e);
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
//									value="331023413400";//���ֱ��֤�������¼�������""��ʽ����
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
//			 throw new AppException("�������ݳ���(" + e.getMessage() + ")");
//		}
//	}
//	/**
//	 * @deprecated
//	 * @$comment ��eventTable(�¼���)�д�����ݶ�Ӧ�ı��浽basicTable(������)
//	 * @param eventTable �¼���
//	 * @param basicTable ������
//	 * @param property   �ֶ���
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
//				if(i==fromProperties.length-2){//�����Ӧ���Ե�ֵ �����θ�ֵ
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
//			 throw new AppException("�������ݳ���",ex);
//		}
//	}
//
//	/**
//	 * @$comment ʵ�ִӻ������¼���ֵ�Ŀ�����
//	 *           �˷���ֻ�����ڵ�λģ�飬ԭ�����¼�����ֶα������ˡ�
//	 *           �����Ƕ�AE53��Ĳ��������Զ�������Ĳ�����Ҫ����Ӧ���޸ģ��д������С�
//	 * @param basicTable ������
//	 * @param eventId �¼�ID
//	 * @param keyId �¼��������
//	 * @param orgId ��֯ID
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
//						if(fromProperty.equalsIgnoreCase("eaz069")){//��֯�Ǽ���ϸid
//							String seqcode=HBUtil.getSequence(keyId);
//							ae53.setEaz069(Long.parseLong(seqcode));//�¼���ae53������
//						}
//						if(fromProperty.equalsIgnoreCase("aaz308")){//�¼�ID
//							ae53.setAaz308(eventId);
//						}
//						if(fromProperty.equalsIgnoreCase("aaz312")){//������
//							ae53.setAaz312(oldProperty.toUpperCase());
//						}
//						if(fromProperty.equalsIgnoreCase("aaz313")){//����ֵ
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
//			 throw new AppException("�������ݳ���(" + e.getMessage() + ")");
//		}    	
//    }
//	
//    /**
//	 * @$comment ʵ�ִӻ������ݱ�ֵ�Ŀ���
//	 *           �˷�������ֻ�����ڵ�λģ�飬ԭ�����¼�����ֶα������ˡ�
//	 *           �����Ƕ�AE19��Ĳ��������Զ�������Ĳ�����Ҫ����Ӧ���޸ģ��д������С�
//     * @param basicTable_Old �����ݿ��������޸�ǰ�Ķ���
//     * @param basicTable_New �ӽ��������Ա�����Ķ���
//     * @param eventId �¼�ID
//     * @param tableName ������ı���
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
//									if(fromProperty.equalsIgnoreCase("aaz019")){//��֯��Ϣ�����ϸID
//										ae19.setAaz019(Long.parseLong(aaz019));
//									}
//									if(fromProperty.equalsIgnoreCase("eae001")){//����
//										ae19.setEae001(tableName);
//									}
//									if(fromProperty.equalsIgnoreCase("aae122")){//�����Ŀ
//										ae19.setAae122(oleProperties[i].getName().toUpperCase());
//									}
//									if(fromProperty.equalsIgnoreCase("aae123")){//���ǰ��Ϣ
//										ae19.setAae123(oldValue.toString());
//									}
//									if(fromProperty.equalsIgnoreCase("aae124")){//�������Ϣ
//										ae19.setAae124(newValue.toString());
//									}
//									if(fromProperty.equalsIgnoreCase("aaz308")){//��֯��Ϣ����¼�ID
//										ae19.setAaz308(eventId);
//									}
//									if(fromProperty.equalsIgnoreCase("aae121")){//��������ĺ���
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
//			throw new AppException("���Ը���ʧ��(" + e.getMessage() + ")");
//		}
//		
//	}
//	
//	/**
//	 * @deprecated
//	 * @$comment ��eventTable(�¼���)�д�����ݶ�Ӧ�ı��浽basicTable(������)
//	 * @param eventTable �¼���
//	 * @param basicTable ������
//	 * @param property �¼����д������Ե��ֶ�ֵ
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
//				if(i==fromProperties.length-4){//�����Ӧ���Ե�ֵ �����θ�ֵ
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
//			 throw new AppException("�������ݳ���(" + ex.getMessage() + ")");
//		}
//	}
//	
//
//	/**
//	 * @$comment ʵ�ִӻ������ݱ�ֵ�Ŀ�����
//	 *           �˷�������ֻ�����ڵ�λģ�飬ԭ�����¼�����ֶα������ˡ�
//	 *           �����Ƕ�AC54��Ĳ��������Զ�������Ĳ�����Ҫ����Ӧ���޸ģ��д������С�
//	 * @param basicTable Ҫ���浽�¼�����Ķ���
//	 * @param eventId �¼�ID
//	 * @param keyId �¼��������
//	 * @param personId ��ԱID
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
//			 throw new AppException("�������ݳ���(" + ex.getMessage() + ")");
//		}    	
//    }*/
//	
//    /**
//	 * @$comment ʵ�ִӻ������ݱ�ֵ�Ŀ�����
//	 *           �˷�������ֻ�����ڵ�λģ�飬ԭ�����¼�����ֶα������ˡ�
//	 *           �����Ƕ�AB34��Ĳ��������Զ�������Ĳ�����Ҫ����Ӧ���޸ģ��д������С�
//     * @param basicTable_Old �����ݿ��������޸�ǰ�Ķ���
//     * @param basicTable_New �ӽ��������Ա�����Ķ���
//     * @param eventId �¼�ID
//     * @param tableName ������ı���
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
//									if(toProperty.equalsIgnoreCase("aaz045")){//��λ�α���������ϸID
//										ab34.setAaz045(Long.parseLong(aaz045));
//									}
//									if(toProperty.equalsIgnoreCase("eae001")){//����
//										ab34.setEae001(tableName);
//									}
//									if(toProperty.equalsIgnoreCase("aae122")){//�����Ŀ
//										ab34.setAae122(oleProperties[i].getName().toUpperCase());
//									}
//									if(toProperty.equalsIgnoreCase("aae123")){//���ǰ��Ϣ
//										ab34.setAae123(oldValue.toString());
//									}
//									if(toProperty.equalsIgnoreCase("aae124")){//�������Ϣ
//										ab34.setAae124(newValue.toString());
//									}
//									if(toProperty.equalsIgnoreCase("aaz308")){//��λ���ֲα��������¼�ID
//										ab34.setAaz308(eventId);
//									}
//									if(toProperty.equalsIgnoreCase("aae121")){//������ĺ���
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
//			throw new AppException("���Ը���ʧ��(" + e.getMessage() + ")");
//		}   	
//    }
//
//    /**
//	 * @$comment ʵ�ִӻ������ݱ�ֵ�Ŀ�����
//	 *           �˷�������ֻ�����ڵ�λģ�飬ԭ�����¼�����ֶα������ˡ�
//	 *           �����Ƕ�AC24��Ĳ��������Զ�������Ĳ�����Ҫ����Ӧ���޸ģ��д������С�
//     * @param basicTable_Old �����ݿ��������޸�ǰ�Ķ���
//     * @param basicTable_New �ӽ��������Ա�����Ķ���
//     * @param eventId �¼�ID
//     * @param tableName ������ı���
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
//									if(toProperty.equalsIgnoreCase("aaz162")){//��Ա�α���¼�����ϸID
//										ac24.setAaz162(Long.parseLong(aaz062));	
//									}
//									if(toProperty.equalsIgnoreCase("eae001")){//����
//										ac24.setEae001(tableName);
//									}
//									if(toProperty.equalsIgnoreCase("aae122")){//�����Ŀ
//										ac24.setAae122(oleProperties[i].getName().toUpperCase());
//									}
//									if(toProperty.equalsIgnoreCase("aae123")){//���ǰ��Ϣ
//										ac24.setAae123(oldValue.toString());
//									}
//									if(toProperty.equalsIgnoreCase("aae124")){//�������Ϣ
//										ac24.setAae124(newValue.toString());
//									}
//									if(toProperty.equalsIgnoreCase("aaz308")){//��Ա���ֲα���¼����¼�ID
//										ac24.setAaz308(eventId);
//									}
//									if(toProperty.equalsIgnoreCase("aae121")){//���ĺ���
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
//			throw new AppException("���Ը���ʧ��(" + e.getMessage() + ")");
//		}   	
//    }
    
	 /**
     * @$comment ʵ����������������ͬ������ֵ�Ŀ��������ÿ�����������
     * ǰ������������ͬ���������Ե��ֶ�������ֵ��
     * ���������͵�ע��:�������������ָ������CopyEventBean.isBasicClass������У�������,�Ժ�����������Ҫ�����͡�
     * ���ܣ�
     * 1������������ͬ�����Ե�ֵ�Ļ�����
     * 2���������͵�String�͵�ֵ�Ļ�����
     * 3��String�͵������������͵�ֵ�Ļ�����
     * 4��Date�͵�Long�͵ĵ����Ե�ֵ�Ļ�����
     * 5��String������ı������ڵ��ַ���ʽ��"2008-04-01"����Long�͵����Ե�ֵ�Ŀ���������ģ�����ʵ��Long��String�������͡�
     * 6��String������ı������ڵ��ַ���ʽ��"2008-04-01"����Date�͵����Ե�ֵ�Ŀ���������ģ�����ʵ��Date��String�������͡�
     * 7���ȵȡ�
     * ȱ����
     * 1���޷�ʵ�ֱ�����Double��Long��ת����
     * 2���Լ����޷�ʵ�ֵ����ͻ�����ֻ����ʹ�ó���ʱ�ټ���ȡ��
     * @param fromObj �����ƶ���
     * @param toObj ���ƶ���
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
				//if(fromValue==null||fromValue.toString().equalsIgnoreCase("")) //֣���� 2010-04-09 ע�͵�
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
			throw new AppException("���Ը���ʧ��(" + e.getMessage() + ")");
		}
	}

}

