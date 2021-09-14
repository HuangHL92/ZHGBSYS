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
 * 属性拷贝辅助工具类
 * @author Administrator
 *
 */
public class PMPropertyCopyUtil {
	
	/**
	 * 拷贝页面元素值到Object对象中同名属性里,如果parentElementName为空，则拷贝PM的第一级元素，否则
	 * 拷贝parentElementName下的所有子元素到Object同名属性里
	 * @param obj 要拷贝元素值到的Java Bean对象
	 * @param pm PageModel页面
	 * @param parentElementName 拷贝该元素下面的所有子元素值到obj的同名属性里
	 * @return  拷贝成功返回true，否则返回false
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
			throw new RadowException("值拷贝时发生异常",e1);
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
				throw new RadowException("值拷贝时发生异常",e);
			}
		}
		return true;
	}

	
	/**
	 * 拷贝元素值到Object对象中同名属性里
	 * @param obj    要拷贝元素值到的Java Bean对象
	 * @return		拷贝成功返回true，否则返回false
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
	 * 把obj属性的值设到对应的PageElement中,如果parentElementName不为空，则设到其对应元素的子元素中
	 * 否则，则设到PageModel的第一级元素中
	 * @param obj    要从中拷贝元素值的Java Bean对象
	 * @param pm	 页面模型
	 * @param parentElementName	从obj的属性中拷贝到该元素下面的所有子元素值的同名属性里
	 * @return   设置成功返回true，否则返回false
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
			throw new RadowException("拷贝时发生异常！",e);
		}
		
		return true;
	}
	/**
	 * 把obj属性的值设到对应的PageElement中
	 * @param obj   要从中拷贝元素值的Java Bean对象
	 * @param pm    页面模型
	 * @return		设置成功返回true，否则返会false
	 * @throws RadowException 
	 */
	public static boolean copyObjValueToElement(Object obj, PageModel pm) throws RadowException{
		return copyObjValueToElement(obj,pm,null);
	}
}
