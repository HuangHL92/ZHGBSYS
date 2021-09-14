package com.insigma.siis.local.epsoft.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;

/**
 * ʵ�忽������
 * @author gezh
 *
 */
public class BeanCopyUtil {

	/**
	 * ��������ʵ��
	 * @param fromObj
	 * @param hm
	 * @throws Exception
	 */
	public static void CopytoHashMap(Object fromObj,HashMap hm) throws Exception {
		BeanInfo fromBean=Introspector.getBeanInfo(fromObj.getClass(),Object.class);
		PropertyDescriptor[] fromprops=fromBean.getPropertyDescriptors();
		for (int i = 0; i < fromprops.length; i++){
			Object fromValue=fromprops[i].getReadMethod().invoke(fromObj,new Object[] {});
			hm.put(fromprops[i].getName(),fromValue);
		}
	}
}
