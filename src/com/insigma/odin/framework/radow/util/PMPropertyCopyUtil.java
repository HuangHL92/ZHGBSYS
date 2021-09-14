package com.insigma.odin.framework.radow.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.util.DateUtil;

/**
 * ���Կ�������������
 * @author Administrator
 *
 */
public class PMPropertyCopyUtil {
	
	/**
	 * ����ҳ��Ԫ��ֵ��Object������ͬ��������,���parentElementNameΪ�գ��򿽱�PM�ĵ�һ��Ԫ�أ�����
	 * ����parentElementName�µ�������Ԫ�ص�Objectͬ��������
	 * @param obj Ҫ����Ԫ��ֵ����Java Bean����
	 * @param pm PageModelҳ��
	 * @param parentElementName ������Ԫ�������������Ԫ��ֵ��obj��ͬ��������
	 * @return  �����ɹ�����true�����򷵻�false
	 * @throws RadowException
	 */
	public static boolean copyElementsValueToObj(Object obj, PageModel pm,String parentElementName)
			throws RadowException {
		String pre = (parentElementName==null)?"":(parentElementName+".");
		BeanInfo beanInfo = null;
		PropertyDescriptor[] properties = null;
		try {
			beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
		} catch (IntrospectionException e1) {
			throw new RadowException("ֵ����ʱ�����쳣",e1);
		}
		properties = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < properties.length; i++) {
			String propertieName = properties[i].getName();
			propertieName = pre + propertieName;
			String value = "";
			if (pm.getPageElement(propertieName) != null) {
				value = pm.getPageElement(propertieName).getValue();
			}else {
				continue;
			}
//			if (value.trim().length() == 0) {
//				continue;
//			}
			Class<?> className = properties[i].getPropertyType();
			try {
				if (className.equals(Date.class)) {
					if("null".equalsIgnoreCase(value)){
						value = "";
					}
					if (value.length() <= 10) {
						properties[i].getWriteMethod().invoke(obj,
								new Object[] { DateUtil.parseDate(value) });
					} else {
						properties[i].getWriteMethod().invoke(obj,
								new Object[] { DateUtil.parseDateTime(value) });
					}
				} else if (className.equals(String.class)) {
					properties[i].getWriteMethod().invoke(obj,
							new Object[] { value });
				} else if (className == Integer.class || className == int.class) {
					if("null".equalsIgnoreCase(value)){
						value = "";
					}
					properties[i].getWriteMethod().invoke(obj,
							new Object[] { value.trim().equals("")?null:Integer.parseInt(value) });
				} else if (className == Short.class || className == short.class) {
					if("null".equalsIgnoreCase(value)){
						value = "";
					}
					properties[i].getWriteMethod().invoke(obj,
							new Object[] { value.trim().equals("")?null:Short.parseShort(value) });
				} else if (className == Long.class || className == long.class) {
					if("null".equalsIgnoreCase(value)){
						value = "";
					}
					properties[i].getWriteMethod().invoke(obj,
							new Object[] { value.trim().equals("")?null:Long.parseLong(value) });
				} else if (className == Float.class || className == float.class) {
					properties[i].getWriteMethod().invoke(obj,
							new Object[] { value.trim().equals("")?null:Float.parseFloat(value) });
				} else if (className == Double.class || className == double.class) {
					properties[i].getWriteMethod().invoke(obj,
							new Object[] { value.trim().equals("")?null:Double.parseDouble(value) });
				}
			} catch (Exception e) {  
				throw new RadowException("ֵ����ʱ�����쳣",e);
			}
		}
		return true;
	}

	
	/**
	 * ����Ԫ��ֵ��Object������ͬ��������
	 * @param obj    Ҫ����Ԫ��ֵ����Java Bean����
	 * @return		�����ɹ�����true�����򷵻�false
	 * @throws RadowException
	 * @throws AppException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static boolean copyElementsValueToObj(Object obj, PageModel pm)
			throws RadowException {		
		return copyElementsValueToObj(obj,pm,null);
	}

	/**
	 * ��obj���Ե�ֵ�赽��Ӧ��PageElement��,���parentElementName��Ϊ�գ����赽���ӦԪ�ص���Ԫ����
	 * �������赽PageModel�ĵ�һ��Ԫ����
	 * @param obj    Ҫ���п���Ԫ��ֵ��Java Bean����
	 * @param pm	 ҳ��ģ��
	 * @param parentElementName	��obj�������п�������Ԫ�������������Ԫ��ֵ��ͬ��������
	 * @return   ���óɹ�����true�����򷵻�false
	 * @throws RadowException 
	 * @throws Exception
	 */
	public static boolean copyObjValueToElement(Object obj, PageModel pm,String parentElementName) throws RadowException{		
		try {
			String pre = (parentElementName==null)?"":(parentElementName+".");
			BeanInfo beanInfo = null;
			PropertyDescriptor[] properties = null;
			beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
			properties = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < properties.length; i++) {
				String propertieName = properties[i].getName();
				propertieName = pre + propertieName;
				Object value = properties[i].getReadMethod().invoke(obj,new Object[]{});
				PageElement pe = pm.getPageElement(propertieName);
				if (pe != null) {
					if(value == null || value.equals("null") || value.equals(null)) {
						pe.setValue("");
						
					}else {
						pe.setValue(value.toString());
						
					}
				}else {
					continue;
				}
			}
		} catch (Exception e) {
			throw new RadowException("����ʱ�����쳣��",e);
		}
		
		return true;
	}
	/**
	 * ��obj���Ե�ֵ�赽��Ӧ��PageElement��
	 * @param obj   Ҫ���п���Ԫ��ֵ��Java Bean����
	 * @param pm    ҳ��ģ��
	 * @return		���óɹ�����true�����򷵻�false
	 * @throws RadowException 
	 */
	public static boolean copyObjValueToElement(Object obj, PageModel pm) throws RadowException{
		return copyObjValueToElement(obj,pm,null);
	}
}
