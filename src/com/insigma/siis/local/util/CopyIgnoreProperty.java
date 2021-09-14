package com.insigma.siis.local.util;

import java.beans.*;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;



import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.DateUtil;

/**
 * ʵ����������������ͬ������ֵ�Ŀ��������ÿ�����������
 * @author ����Ρ
 * @version 1.0
 * @updated 2008-04-15
 */

public class CopyIgnoreProperty extends CopyEventBean{

   
	
	/**
     * @$comment ʵ���˰Ѵ����HashMap���������ݿ���������ʵ����
     * ���ܣ�
     * 1������ʱ���뿼��toObj�����Ե����͡�
     * 2�����͵�ת����
     *    2.1������ת����Long,Integer,Float,Date,Short,Double,String,Boolean��
     *    2.2��String������ı������ڵ��ַ���ʽ��"2008-04-01"����Long�͵����Ե�ֵ��ת����
     *    2.3��String������ı������ڵ��ַ���ʽ��"2008-04-01"����Date�͵����Ե�ֵ��ת����
     *    2.4���ȵȡ�
	 * @param fromHashMap �����Ƶ�HashMap
	 * @param toObj ���ƵĶ���
	 * @throws AppException
	 * @code
	 * 	CommonQueryBS query_ac58_money=new CommonQueryBS();
		query_ac58_money.setConnection(sess.connection());	
	    query_ac58_money.setQuerySQL("select sum(akc087) as akc087,sum(ekc001) as ekc001,sum(eac005) as eac005 from ac58  where aac001="+aac001+"");
		Vector<?> vector_medical=query_ac58_money.query();
		Iterator<?> iterator_medical = vector_medical.iterator();
		ArchiveQueryMedicalPara medicalPara=dto.new ArchiveQueryMedicalPara();
		while (iterator_medical.hasNext()){
			HashMap tmp= (HashMap)iterator_medical.next();
			CopyIgnoreProperty.copyHashMap(tmp, medicalPara);
		}       
     */
	@SuppressWarnings("unchecked")
	public static  void copyHashMap(HashMap fromHashMap, Object toObj) throws AppException{
		BeanInfo toBeanInfo = null;
		Integer hashMapSize=fromHashMap.keySet().size();
		String field="";
		try {
			toBeanInfo = Introspector.getBeanInfo(toObj.getClass(),Object.class);
			PropertyDescriptor[] toProperties = toBeanInfo.getPropertyDescriptors();
			for (PropertyDescriptor toPropertie:toProperties)
			{
				field = toPropertie.getName();
				Integer toPropertieSize=0;
				String Property= toPropertie.getName();
		    	Object fromValue=fromHashMap.get(Property);
				if(fromValue!=null&&!fromValue.toString().equalsIgnoreCase("")){
					    if(hashMapSize.toString().equals(toPropertieSize.toString())){
					    	return;
					    }
					    toPropertieSize++;
						if(toPropertie.getPropertyType()== Long.class||toPropertie.getPropertyType()== long.class)
					    {
					    	
						    if(fromValue.toString().indexOf("-")>0)
						    {
						    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { DateUtil.strDateToNum(fromValue.toString().substring(0, 10)) });
						    	continue;
						    }
						    if(fromValue.getClass()==Date.class)
						    {
						    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { DateUtil.strDateToNum(DateUtil.formatDate((Date)fromValue)) });
						    	continue;
						    }
						    toPropertie.getWriteMethod().invoke(toObj,new Object[] { Long.valueOf(fromValue.toString()) });
						    continue;
					    }
					    if(toPropertie.getPropertyType()== Integer.class||toPropertie.getPropertyType()== int.class)
				     	{
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Integer.valueOf(fromValue.toString()) });
					    	continue;
				     	}
					    if(toPropertie.getPropertyType()== Float.class||toPropertie.getPropertyType()== float.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Float.valueOf(fromValue.toString()) });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== java.util.Date.class){
					    	if(fromValue.toString().length()<=10&&fromValue.toString().length()>=8)
					    		toPropertie.getWriteMethod().invoke(toObj,new Object[]{DateUtil.stringToDate(fromValue.toString().replaceAll("/","").replaceAll("-",""),DateUtil.COMPACT_DATE_FORMAT)});
					    	else if(fromValue.toString().length()<8&&fromValue.toString().length()>=6)
					    		toPropertie.getWriteMethod().invoke(toObj,new Object[]{DateUtil.stringToDate(fromValue.toString().replaceAll("/","").replaceAll("-",""),"yyyyMM")});
					    	else if(fromValue.toString().length()>10)
					    		toPropertie.getWriteMethod().invoke(toObj,new Object[]{DateUtil.stringToDate(fromValue.toString(),DateUtil.NORMAL_DATE_FORMAT_NEW) });
					    }
					    if(toPropertie.getPropertyType()== Short.class||toPropertie.getPropertyType()== short.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Short.valueOf(fromValue.toString()) });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== Double.class||toPropertie.getPropertyType()== double.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Double.valueOf(fromValue.toString()) });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== String.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { fromValue.toString() });
					    	continue;
					    }
					    if(toPropertie.getPropertyType()== Boolean.class||toPropertie.getPropertyType()== boolean.class)
					    {
					    	toPropertie.getWriteMethod().invoke(toObj,new Object[] { Boolean.parseBoolean(fromValue.toString()) });
					    	continue;
					    }
					}
			}
			   
		}catch (Exception e) {
		   throw new AppException("���Ը���ʧ��["+field+"](" + e.getMessage() + ")"); 
		 }
	}
		
    /**
     * @$comment ʵ���˰Ѵ�����������������ݿ���������ʵ���У����ض����б�
     * ʹ��˵����������һ��Ҫ��һ��ʵ����󣬲���ֱ�Ӹ�ֵ������
     * 1������ĸ�ֵ������Object[] obj=new Object[10];obj[0]="��";��
     * 2����ȷ�ĸ�ֵ������Ac01 ac01=new Ac01();Object[] obj=new Object[10];ac01.setAac003("��");obj[0]=ac01;��
     * ���ܣ�
     * 1������ʱ���뿼��toObj�����Ե����͡�
     * 2�����͵�ת����
     *    2.1������ת����Long,Integer,Float,Date,Short,Double,String,Boolean��
     *    2.2��String������ı������ڵ��ַ���ʽ��"2008-04-01"����Long�͵����Ե�ֵ��ת����
     *    2.3��String������ı������ڵ��ַ���ʽ��"2008-04-01"����Date�͵����Ե�ֵ��ת����
     *    2.4���ȵȡ�
     * @param <typeName> �Զ�������
     * @param fromObj ��Ŷ��������
     * @param toObj ��
     * @return List<typeName> �Զ�������б�
     * @throws AppException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @code
     * public List GetSysCodeType() {
			Iterator lists = session
					.createQuery(
							"select new Kc05(aaz265,eac071,aaz308,aaz002) from Kc05 k where k.aaz001="+aaz001+"")
					.iterate();
			List kc05lst=new java.util.ArrayList(); 
			while (lists.hasNext())
            {
				Object[] tmp = (Object[])lists.next();
			    List<Kc05> lst=CopyIgnoreProperty.copyObjectArray(tmp, Kc05.class);
			    for(int i=0;i<lst.size();i++){
			      kc05lst.add((Kc05)lst.get(i));
			    }		
			}
			return kc05lst;
	    }     
     */
	public static <typeName> List<typeName> copyObjectArray(Object[] fromObj, Class<typeName> toObj) throws AppException{	
		
		LinkedList<typeName> list = new LinkedList<typeName>();
		try {
			for(int i=0;i<fromObj.length;i++){
				typeName obj =  toObj.newInstance();
				CopyIgnoreProperty.copy(fromObj[i], obj);
				list.add(obj);
			}	

		}catch(Exception e){
			throw new AppException("���Ը���ʧ��(" + e.getMessage() + ")"); 
		}
		return list;		
	}

	/**
	 * @deprecated
	 * @$comment ʵ���˰Ѵ����Object[]�����ݿ���������ʵ����
	 * ���ܣ�
     * 1������ʱ���뿼��toObj�����Ե����͡�
     * 2�����͵�ת����
     *    2.1������ת����Long,Integer,Float,Date,Short,Double,String,Boolean��
     *    2.2��String������ı������ڵ��ַ���ʽ��"2008-04-01"����Long�͵����Ե�ֵ��ת����
     *    2.3��String������ı������ڵ��ַ���ʽ��"2008-04-01"����Date�͵����Ե�ֵ��ת����
     *    2.4���ȵȡ�
     * ȱ����
     * 1���޷�ʵ�ֱ�����Double��Long��ת����
     * 2��HQL�������Ե�˳������toObj�����ƵĶ����������Ҫһ��,�����Ƶ����ݾ��ǲ���ȷ��,��Ϊ���󴴽�ʱ�����ǰ��ַ����е�,���磺
     *    2.1��aab001��eab001ǰ
     *    2.2��aab002��aab003ǰ
     * 3���������ֶ�˳�������ȷ�������ᡣ
	 * @param fromObjs �����Ƶ�����
	 * @param toObj ���ƵĶ���
	 * @throws AppException
	 * @code
	 * public class Ab01 {
          private String eab001;
          private Long aaz001;
          private Double aab019;
          private String aaa148;
          ......
        }
       public List GetSysCodeType(Long aaz001) {
			Iterator lists = session
					.createQuery(
							"select a.aaa148,a.aaz001,a.aab019,a.eab001 from Ab01 a where a.aaz001="+aaz001+"")
					.iterate();
			List ab01lst=new java.util.ArrayList(); 
			while (lists.hasNext())
            {
				Object[] tmp = (Object[])lists.next();
			    copyIgnoreProperty.copyObjectArrayForHQL(tmp,ab01);
			    for(int i=0;i<lst.size();i++){
			      ab01lst.add((Ab01)lst.get(i));
			    }		
			}
			return ab01lst;
	    }        
     */
	public static void copyObjectArrayForHQL(Object[] fromObjs, Object toObj) throws AppException{	
		BeanInfo toBeanInfo = null;	
		Integer i = 0;
		try {
			toBeanInfo = Introspector.getBeanInfo(toObj.getClass(),
					Object.class);
			PropertyDescriptor[] toProperties = toBeanInfo
					.getPropertyDescriptors();
			for (Object fromObj:fromObjs)
			{
				for ( ; i < toProperties.length; )
				{
					Object fromValue=fromObj;
					if(fromValue!=null&&!fromValue.toString().equalsIgnoreCase("")){
						if (isBasicClass(fromValue.getClass())&& isBasicClass(toProperties[i].getPropertyType())&& fromValue.getClass() == toProperties[i].getPropertyType()){
							toProperties[i].getWriteMethod().invoke(toObj,new Object[] { fromValue });
							break;
						}
						else if (isBasicClass(fromValue.getClass())&& isBasicClass(toProperties[i].getPropertyType())&&fromValue.getClass()!=toProperties[i].getPropertyType()) {
							if(toProperties[i].getPropertyType()== Long.class||toProperties[i].getPropertyType()== long.class)
						    {
						    	
							    if(fromValue.toString().indexOf("-")>0)
							    {
							    	toProperties[i].getWriteMethod().invoke(toObj,new Object[] { DateUtil.strDateToNum(fromValue.toString().substring(0, 10)) });
							    	break;
							    }
							    if(fromValue.getClass()==Date.class)
							    {
							    	toProperties[i].getWriteMethod().invoke(toObj,new Object[] { DateUtil.strDateToNum(DateUtil.formatDate((Date)fromValue)) });
							    	break;
							    }
							    toProperties[i].getWriteMethod().invoke(toObj,new Object[] { Long.valueOf(fromValue.toString()) });
							    break;
						    }
						    if(toProperties[i].getPropertyType()== Integer.class||toProperties[i].getPropertyType()== int.class)
					     	{
						    	toProperties[i].getWriteMethod().invoke(toObj,new Object[] { Integer.valueOf(fromValue.toString()) });
						    	break;
					     	}
						    if(toProperties[i].getPropertyType()== Float.class||toProperties[i].getPropertyType()== float.class)
						    {
						    	toProperties[i].getWriteMethod().invoke(toObj,new Object[] { Float.valueOf(fromValue.toString()) });
						    	break;
						    }
						    if(toProperties[i].getPropertyType()== Date.class)
						    {
						    	toProperties[i].getWriteMethod().invoke(toObj,new Object[] { DateUtil.stringToDate(fromValue.toString(),DateUtil.NORMAL_DATE_FORMAT) });
						    	break;
						    }
						    if(toProperties[i].getPropertyType()== Short.class||toProperties[i].getPropertyType()== short.class)
						    {
						    	toProperties[i].getWriteMethod().invoke(toObj,new Object[] { Short.valueOf(fromValue.toString()) });
						    	break;
						    }
						    if(toProperties[i].getPropertyType()== Double.class||toProperties[i].getPropertyType()== double.class)
						    {
						    	toProperties[i].getWriteMethod().invoke(toObj,new Object[] { Double.valueOf(fromValue.toString()) });
						    	break;
						    }
						    if(toProperties[i].getPropertyType()== String.class)
						    {
						    	toProperties[i].getWriteMethod().invoke(toObj,new Object[] { fromValue.toString() });
						    	break;
						    }
						}
					}else{
						break;
					}
					
				}
				i++;
			}
		}catch(Exception e){
			throw new AppException("���Ը���ʧ��(" + e.getMessage() + ")"); 
		}
	}
	/**
	 * @$comment ʵ���˰Ѵ����ʵ���е����ݿ�����HashMap������
	 * @param fromObj
	 * @param toHashMap
	 * @throws RadowException
	 */
	@SuppressWarnings("unchecked")
	public  static  void copyObjToHashMap(Object fromObj,HashMap toHashMap) throws RadowException{
		BeanInfo fromBeanInfo = null;
		try {
			fromBeanInfo = Introspector.getBeanInfo(fromObj.getClass(),Object.class);
			PropertyDescriptor[] fromProperties = fromBeanInfo.getPropertyDescriptors();
			for (PropertyDescriptor fromPropertie:fromProperties)
			{
				String Property= fromPropertie.getName();
		    	Object fromValue=fromPropertie.getReadMethod().invoke(fromObj,new Object[] {});
		    	
				if(fromValue!=null&&!fromValue.toString().equalsIgnoreCase("")){
					toHashMap.put(Property, fromValue);
				}
			}
			   
		}catch (Exception e) {
		   throw new RadowException("���Ը���ʧ��(" + e.getMessage() + ")"); 
		 }		
	}
}
