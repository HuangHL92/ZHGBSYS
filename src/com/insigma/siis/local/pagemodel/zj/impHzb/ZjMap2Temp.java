package com.insigma.siis.local.pagemodel.zj.impHzb;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01temp;
import com.insigma.siis.local.business.entity.A02temp;
import com.insigma.siis.local.business.entity.A06temp;
import com.insigma.siis.local.business.entity.A08temp;
import com.insigma.siis.local.business.entity.A11temp;
import com.insigma.siis.local.business.entity.A14temp;
import com.insigma.siis.local.business.entity.A15temp;
import com.insigma.siis.local.business.entity.A29temp;
import com.insigma.siis.local.business.entity.A30temp;
import com.insigma.siis.local.business.entity.A31temp;
import com.insigma.siis.local.business.entity.A36temp;
import com.insigma.siis.local.business.entity.A37temp;
import com.insigma.siis.local.business.entity.A41temp;
import com.insigma.siis.local.business.entity.A53temp;
import com.insigma.siis.local.business.entity.A57temp;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.B01temp;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

import net.sf.json.JSONArray;

public class ZjMap2Temp {

	public static List toTemp(String table,
			List<Map<String, String>> list) throws AppException {
		// TODO Auto-generated method stub
		if(list == null || list.size()==0){
			return null;
		}
		if(table.equals("A01")){
			return A01toTemp(list);
		} else if(table.equals("A02")){
			return A02toTemp(list);
		} else if(table.equals("A06")){
			return A06toTemp(list);
		} else if(table.equals("A08")){
			return A08toTemp(list);
		} else if(table.equals("A11")){
			return A11toTemp(list);
		} else if(table.equals("A14")){
			return A14toTemp(list);
		} else if(table.equals("A15")){
			return A15toTemp(list);
		} else if(table.equals("A29")){
			return A29toTemp(list);
		} else if(table.equals("A30")){
			return A30toTemp(list);
		} else if(table.equals("A31")){
			return A31toTemp(list);
		} else if(table.equals("A36")){
			return A36toTemp(list);
		} else if(table.equals("A37")){
			return A37toTemp(list);
		} else if(table.equals("A41")){
			return A41toTemp(list);
		} else if(table.equals("A53")){
			return A53toTemp(list);
		} else if(table.equals("A57")){
			return A57toTemp(list);
		} else if(table.equals("B01")){
			return B01toTemp(list);
		} else if(table.equals("Imprecord")){
			return ImprecordtoTemp(list);
		}
		return null;
	}
	//��ȡlist�����map ǰ���ȡ����map������
	private static List ImprecordtoTemp(List<Map<String, String>> list) throws AppException {
		List<Imprecord> rlist = new ArrayList<Imprecord>();
		if(list.size() == 2){
			Map<String, String> map = list.get(0);
			Imprecord temp = (Imprecord) mapToObject(Imprecord.class, map);
			rlist.add(temp);
		}else{
			throw new AppException("���ݲ�ȫ");
		}
		return rlist;
	}

	private static List A57toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A57temp> rlist = new ArrayList<A57temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A57temp temp = (A57temp) mapToObject(A57temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
				
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A53toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A53temp> rlist = new ArrayList<A53temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A53temp temp = (A53temp) mapToObject(A53temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A41toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A41temp> rlist = new ArrayList<A41temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A41temp temp = (A41temp) mapToObject(A41temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List B01toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<B01temp> rlist = new ArrayList<B01temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				B01temp temp = (B01temp) mapToObject(B01temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A37toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A37temp> rlist = new ArrayList<A37temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A37temp temp = (A37temp) mapToObject(A37temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A36toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A36temp> rlist = new ArrayList<A36temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A36temp temp = (A36temp) mapToObject(A36temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A31toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A31temp> rlist = new ArrayList<A31temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A31temp temp = (A31temp) mapToObject(A31temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A30toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A30temp> rlist = new ArrayList<A30temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A30temp temp = (A30temp) mapToObject(A30temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A29toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A29temp> rlist = new ArrayList<A29temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A29temp temp = (A29temp) mapToObject(A29temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A15toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A15temp> rlist = new ArrayList<A15temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A15temp temp = (A15temp) mapToObject(A15temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A14toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A14temp> rlist = new ArrayList<A14temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A14temp temp = (A14temp) mapToObject(A14temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A08toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A08temp> rlist = new ArrayList<A08temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A08temp temp = (A08temp) mapToObject(A08temp.class, map);
				/**�˴���У�����
				 */
				if(temp.getA0899()!=null && temp.getA0899().equals("1")){
					temp.setA0899("true");
				} else {
					temp.setA0899("false");
				}
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A11toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A11temp> rlist = new ArrayList<A11temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A11temp temp = (A11temp) mapToObject(A11temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A06toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A06temp> rlist = new ArrayList<A06temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A06temp temp = (A06temp) mapToObject(A06temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A02toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A02temp> rlist = new ArrayList<A02temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A02temp temp = (A02temp) mapToObject(A02temp.class, map);
				/**�˴���У�����
				 */
				if(temp.getA0281()!=null && temp.getA0281().equals("1")){
					temp.setA0281("true");
				} else {
					temp.setA0281("false");
				}
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}

	private static List A01toTemp(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		List<A01temp> rlist = new ArrayList<A01temp>();
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				A01temp temp = (A01temp) mapToObject(A01temp.class, map);
				/**�˴���У�����
				 */
				temp.setIsqualified("0");
				rlist.add(temp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlist;
	}
	
	 /**
     * ��һ�� Map ����ת��Ϊһ�� JavaBean
     * @param type Ҫת��������
     * @param map ��������ֵ�� map
     * @return ת�������� JavaBean ����
     * @throws IntrospectionException
     *             �������������ʧ��
     * @throws IllegalAccessException
     *             ���ʵ���� JavaBean ʧ��
     * @throws InstantiationException
     *             ���ʵ���� JavaBean ʧ��
     * @throws InvocationTargetException
     *             ����������Ե� setter ����ʧ��
     */
    public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        java.beans.BeanInfo beanInfo = Introspector.getBeanInfo(type); // ��ȡ������
        Object obj = type.newInstance(); // ���� JavaBean ����

        // �� JavaBean ��������Ը�ֵ
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            String stringLetter=propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);    
            if (map.containsKey(stringLetter)) {
                // ����һ����� try ������������һ�����Ը�ֵʧ�ܵ�ʱ��Ͳ���Ӱ���������Ը�ֵ��
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }
    /**
     * ��һ�� JavaBean ����ת��Ϊһ��  Map
     * @param bean Ҫת����JavaBean ����
     * @return ת��������  Map ����
     * @throws IntrospectionException �������������ʧ��
     * @throws IllegalAccessException ���ʵ���� JavaBean ʧ��
     * @throws InvocationTargetException ����������Ե� setter ����ʧ��
     */
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        java.beans.BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName1 = descriptor.getName();
            String propertyName = propertyName1.toUpperCase();
            if (!propertyName.equals("CLASS")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                	
                	if(result.getClass() == java.sql.Date.class){
                		 returnMap.put(propertyName, DateUtil.dateToString((java.sql.Date)result, "yyyyMMdd"));//Ϊ�丳ֵ  date
					}else if(result.getClass() == java.sql.Time.class){
						 returnMap.put(propertyName, DateUtil.timeToString((java.sql.Time)result));//Ϊ�丳ֵ  time
					}else if(result.getClass() == java.sql.Timestamp.class){
						 returnMap.put(propertyName, DateUtil.timeToString((java.sql.Timestamp)result));//Ϊ�丳ֵ timestamp
					}else if(result.getClass() == String.class){
						returnMap.put(propertyName, result);                                            //Ϊ�丳ֵ string
					}else if(result.getClass() == Integer.class || result.getClass() == int.class ||
							result.getClass() == Boolean.class || result.getClass() == boolean.class||
							result.getClass() == Short.class || result.getClass() == short.class||
							result.getClass() == Long.class || result.getClass() == long.class||
							result.getClass() == Double.class || result.getClass() == double.class||
							result.getClass() == Float.class || result.getClass() == float.class||
							result.getClass() == java.math.BigInteger.class ||
							result.getClass() == BigDecimal.class){
						returnMap.put(propertyName, (String.valueOf(result)));     
					} else {
						returnMap.put(propertyName, (String.valueOf(result)));     
					}
                
                } else {
                	returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
    
    /**
     * ��һ�� JavaBean ����ת��Ϊһ��  Map
     * @param bean Ҫת����JavaBean ����
     * @return ת��������  Map ����
     * @throws IntrospectionException �������������ʧ��
     * @throws IllegalAccessException ���ʵ���� JavaBean ʧ��
     * @throws InvocationTargetException ����������Ե� setter ����ʧ��
     */
    public static Map convertBean2(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    
    /**
	 * ��Map<String,String>�����ʵ����
	 * @param clazz		��Ҫ��ʵ����
	 * @param map		������Ϣ��Map����
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object mapToObject(Class clazz, Map<String,String> map){
		
		if(null == map){
			return null;
		}
		
		java.lang.reflect.Field[] fields = clazz.getDeclaredFields();	//ȡ���������е����ԣ�Ҳ���Ǳ�����
		java.lang.reflect.Field field;
		Object o = null;
		try {
			o = clazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		for(int i=0; i<fields.length; i++){
			field = fields[i];
			String fieldName = field.getName();
			//�����Եĵ�һ����ĸ����ɴ�д
			String stringLetter=fieldName.substring(0, 1).toUpperCase();    
			//ȡ��set������������setBbzt
			String setterName="set"+stringLetter+fieldName.substring(1);    
			//����ȡ��set������
			Method setMethod = null;
			Class fieldClass = field.getType();
			// Map�� A0000
			String mapName = fieldName.toUpperCase();
			
			try {
				if(map.get(mapName) != null && !map.get(mapName).equals("")){
					if(isHaveSuchMethod(clazz, setterName)){
						if(fieldClass == String.class){
							setMethod = clazz.getMethod(setterName, fieldClass);
							setMethod.invoke(o, String.valueOf(map.get(mapName)));//Ϊ�丳ֵ 
						}else if(fieldClass == Integer.class || fieldClass == int.class){
							setMethod = clazz.getMethod(setterName, fieldClass);
							setMethod.invoke(o, Integer.parseInt(String.valueOf(map.get(mapName))));//Ϊ�丳ֵ 
						}else if(fieldClass == Boolean.class || fieldClass == boolean.class){
							setMethod = clazz.getMethod(setterName, fieldClass);
							setMethod.invoke(o, Boolean.getBoolean(String.valueOf(map.get(mapName))));//Ϊ�丳ֵ 
						}else if(fieldClass == Short.class || fieldClass == short.class){
							setMethod = clazz.getMethod(setterName, fieldClass);
							setMethod.invoke(o, Short.parseShort(String.valueOf(map.get(mapName))));//Ϊ�丳ֵ 
						}else if(fieldClass == Long.class || fieldClass == long.class){
							setMethod = clazz.getMethod(setterName, fieldClass);
							setMethod.invoke(o, Long.parseLong(String.valueOf(map.get(mapName))));//Ϊ�丳ֵ 
						}else if(fieldClass == Double.class || fieldClass == double.class){
							setMethod = clazz.getMethod(setterName, fieldClass);
							setMethod.invoke(o, Double.parseDouble(String.valueOf(map.get(mapName))));//Ϊ�丳ֵ 
						}else if(fieldClass == Float.class || fieldClass == float.class){
							setMethod = clazz.getMethod(setterName, fieldClass);
							setMethod.invoke(o, Float.parseFloat(String.valueOf(map.get(mapName))));//Ϊ�丳ֵ 
						}else if(fieldClass == java.math.BigInteger.class ){
							setMethod = clazz.getMethod(setterName, fieldClass);
							setMethod.invoke(o, BigInteger.valueOf(Long.parseLong(String.valueOf(map.get(mapName)))));//Ϊ�丳ֵ 
						}else if(fieldClass == BigDecimal.class){
							setMethod = clazz.getMethod(setterName, fieldClass);
							setMethod.invoke(o, BigDecimal.valueOf(Long.parseLong(String.valueOf(map.get(mapName)))));//Ϊ�丳ֵ 
						}else if(fieldClass == Date.class){
							setMethod = clazz.getMethod(setterName, fieldClass);
							String dstr = map.get(mapName);
							Date date = DateUtil.utilDate2SqlDate(DateUtil.stringToDate(dstr, "yyyyMMdd"));
							setMethod.invoke(o, date);//Ϊ�丳ֵ 
						}else if(fieldClass == Time.class){
							setMethod = clazz.getMethod(setterName, fieldClass);
							String dstr = map.get(mapName);
							Time date = Time.valueOf(dstr);
							setMethod.invoke(o, new Date(date.getTime()));//Ϊ�丳ֵ 
//							setMethod.invoke(o, new Date(((java.sql.Time)map.get(mapName)).getTime()));//Ϊ�丳ֵ 
						}else if(fieldClass == Timestamp.class){
							setMethod = clazz.getMethod(setterName, fieldClass);
							String dstr = map.get(mapName);
							Timestamp date = DateUtil.stringToTime(dstr);
							setMethod.invoke(o, new Date(date.getTime()));//Ϊ�丳ֵ 
						}
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}   catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}	
			
		}
		return o;
	}
	
	/**
	 * ��Map<String,Object>�����ʵ����
	 * @param clazz		��Ҫ��ʵ����
	 * @param map		������Ϣ��Map����
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object mapToObject2(Class clazz, Map<String,Object> map){
		
		if(null == map){
			return null;
		}
		
		Field[] fields = clazz.getDeclaredFields();	//ȡ���������е����ԣ�Ҳ���Ǳ�����
		Field field;
		Object o = null;
		try {
			o = clazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		for(int i=0; i<fields.length; i++){
			field = fields[i];
			String fieldName = field.getName();
			//�����Եĵ�һ����ĸ����ɴ�д
			String stringLetter=fieldName.substring(0, 1).toUpperCase();    
			//ȡ��set������������setBbzt
			String setterName="set"+stringLetter+fieldName.substring(1);    
			//����ȡ��set������
			Method setMethod = null;
			Class fieldClass = field.getType();
			try {
				if(isHaveSuchMethod(clazz, setterName)){
					if(fieldClass == String.class){
						setMethod = clazz.getMethod(setterName, fieldClass);
						setMethod.invoke(o, String.valueOf(map.get(fieldName)));//Ϊ�丳ֵ 
					}else if(fieldClass == Integer.class || fieldClass == int.class){
						setMethod = clazz.getMethod(setterName, fieldClass);
						setMethod.invoke(o, Integer.parseInt(String.valueOf(map.get(fieldName))));//Ϊ�丳ֵ 
					}else if(fieldClass == Boolean.class || fieldClass == boolean.class){
						setMethod = clazz.getMethod(setterName, fieldClass);
						setMethod.invoke(o, Boolean.getBoolean(String.valueOf(map.get(fieldName))));//Ϊ�丳ֵ 
					}else if(fieldClass == Short.class || fieldClass == short.class){
						setMethod = clazz.getMethod(setterName, fieldClass);
						setMethod.invoke(o, Short.parseShort(String.valueOf(map.get(fieldName))));//Ϊ�丳ֵ 
					}else if(fieldClass == Long.class || fieldClass == long.class){
						setMethod = clazz.getMethod(setterName, fieldClass);
						setMethod.invoke(o, Long.parseLong(String.valueOf(map.get(fieldName))));//Ϊ�丳ֵ 
					}else if(fieldClass == Double.class || fieldClass == double.class){
						setMethod = clazz.getMethod(setterName, fieldClass);
						setMethod.invoke(o, Double.parseDouble(String.valueOf(map.get(fieldName))));//Ϊ�丳ֵ 
					}else if(fieldClass == Float.class || fieldClass == float.class){
						setMethod = clazz.getMethod(setterName, fieldClass);
						setMethod.invoke(o, Float.parseFloat(String.valueOf(map.get(fieldName))));//Ϊ�丳ֵ 
					}else if(fieldClass == BigInteger.class ){
						setMethod = clazz.getMethod(setterName, fieldClass);
						setMethod.invoke(o, BigInteger.valueOf(Long.parseLong(String.valueOf(map.get(fieldName)))));//Ϊ�丳ֵ 
					}else if(fieldClass == BigDecimal.class){
						setMethod = clazz.getMethod(setterName, fieldClass);
						setMethod.invoke(o, BigDecimal.valueOf(Long.parseLong(String.valueOf(map.get(fieldName)))));//Ϊ�丳ֵ 
					}else if(fieldClass == Date.class){
						setMethod = clazz.getMethod(setterName, fieldClass);
						if(map.get(fieldName).getClass() == java.sql.Date.class){
							setMethod.invoke(o, new Date(((java.sql.Date)map.get(fieldName)).getTime()));//Ϊ�丳ֵ 
						}else if(map.get(fieldName).getClass() == java.sql.Time.class){
							setMethod.invoke(o, new Date(((java.sql.Time)map.get(fieldName)).getTime()));//Ϊ�丳ֵ 
						}else if(map.get(fieldName).getClass() == java.sql.Timestamp.class){
							setMethod.invoke(o, new Date(((java.sql.Timestamp)map.get(fieldName)).getTime()));//Ϊ�丳ֵ 
						}
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}   catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}	
			
		}
		return o;
	}


/**
	 * �ж�ĳ�������Ƿ���ĳ������
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public static boolean isHaveSuchMethod(Class<?> clazz, String methodName){
		Method[] methodArray = clazz.getMethods();
		boolean result = false;
		if(null != methodArray){
			for(int i=0; i<methodArray.length; i++){
				if(methodArray[i].getName().equals(methodName)){
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	private static HashMap<String, String[]> getLogConfig(){
		HashMap<String, String[]> codetypeMapping = new HashMap<String, String[]>();
		String sql = "select infoid,infoname,code_type from COMPETENCE_INF";
		HBSession sess = HBUtil.getHBSession();
    	try {
    		List<Object[]> list = sess.createSQLQuery(sql).list();
    		if(list!=null&&list.size()>0){
    			String feld_name="";String code_type = "";
    			for(Object[] o : list){
    				if(o[1]!=null){
    					feld_name = o[1].toString();
    				}else{
    					feld_name = "";
    				}
    				if(o[2]!=null){
    					code_type = o[2].toString();
    				}else{
    					code_type = "";
    				}
    				codetypeMapping.put(o[0].toString(), new String[]{feld_name,code_type});
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codetypeMapping;
	}
	private static HashMap<String, String[]> codetypeMapping = null;
	static{
		codetypeMapping = getLogConfig();
	}
	/**
     * ��һ�� JavaBean ����ת��Ϊһ��  Map
     * @param bean Ҫת����JavaBean ����
     * @return ת��������  Map ����
     * @throws IntrospectionException �������������ʧ��
     * @throws IllegalAccessException ���ʵ���� JavaBean ʧ��
     * @throws InvocationTargetException ����������Ե� setter ����ʧ��
	 * @throws AppException 
     */
	@SuppressWarnings("unchecked")
    public static List<String[]> getLogInfo(Object oldObj, Object newObj)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException, AppException {
        Map<String, String> oldMap = convertBean(oldObj);
        Map<String, String> newMap = convertBean(newObj);
        
       
		String size = HBUtil.getValueFromTab("count(1)", "COMPETENCE_INF", "");
		if(codetypeMapping==null||codetypeMapping.size()==0||codetypeMapping.size()!=Integer.valueOf(size)){
    		codetypeMapping = getLogConfig();
		}
	    	
		
        
        List<String[]> list = new ArrayList<String[]>();
        for (String key : newMap.keySet()) {
        	String val1 = oldMap.get(key);
        	String val2 = newMap.get(key);
        	
        	if(key.equals("A0501B")){
	    		String a0531 = oldMap.get("A0531");
	    	        
		        if(a0531 != null && a0531.equals("0")){
		        	key = "A0501A";
		        }
        	}
        	
        	//ͨ��key�ҵ���Ӧ��code_type�����code_type�����ڣ���val1��val2ֵ���ñ䣬��������Ҫ���ɶ�Ӧ��code_name����������쳣������ԭֵ
        	//ת������
        	String[] config = codetypeMapping.get(key);
        	String codeType = null;
        	String fieldName = null;
        	//codetypeMapping �������ñ���Ĳ�����־��¼
        	if(config!=null){
        		codeType = config[1];
        		fieldName = config[0];
        		//����ת��������
        		if(codeType!=null && !"".equals(codeType)) {
            		val1 = getCodeName(codeType,val1);
            		val2 = getCodeName(codeType,val2);
            	}
        	}
            	//val1��val2 ͬʱΪnull��Ϊ""ʱ�ж���ͬ������¼��־  mengl 20160622 
            	if(!val2.equals(val1) && !(StringUtil.isEmpty(val2) && StringUtil.isEmpty(val1)) ){
            		String[] arr = {key, val1, val2,fieldName==null?"":fieldName};
            		list.add(arr);
            	}
        	
        	
        }
        return list;
    }
	
    public static String getCodeTypeByKey(String key){
    	String sql = "select code_type from code_table_col where col_code=?";
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
			ps = HBUtil.getHBSession().connection().prepareStatement(sql);
			ps.setString(1, key);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getString("code_type");
			} else {
				return "";
			}
		} catch (SQLException e) {
			return "";
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
    }
    
    public static String getCodeName(String codeType, String codeValue) {
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	if("ORGID".equals(codeType)){
    		String sql = "select b0101 from b01 where b0111=?";
    		try {
    			ps = HBUtil.getHBSession().connection().prepareStatement(sql);
    			ps.setString(1, codeValue);
    			rs = ps.executeQuery();
    			if(rs.next()) {
    				return rs.getString("b0101");
    			} else {
    				return codeValue;
    			}
    		} catch (SQLException e) {
    			return codeValue;
    		}finally{
    			if(rs != null){
    				try {
    					rs.close();
    				} catch (SQLException e) {
    					e.printStackTrace();
    				}
    			}
    			if(ps != null){
    				try {
    					ps.close();
    				} catch (SQLException e) {
    					e.printStackTrace();
    				}
    			}
    		}
    	}else{
    		String sql = "select code_name from code_value where code_type=? and code_value=?";
        	try {
    			ps = HBUtil.getHBSession().connection().prepareStatement(sql);
    			ps.setString(1, codeType);
    			ps.setString(2, codeValue);
    			rs = ps.executeQuery();
    			if(rs.next()) {
    				return rs.getString("code_name");
    			} else {
    				return codeValue;
    			}
    		} catch (SQLException e) {
    			return codeValue;
    		}finally{
    			if(rs != null){
    				try {
    					rs.close();
    				} catch (SQLException e) {
    					e.printStackTrace();
    				}
    			}
    			if(ps != null){
    				try {
    					ps.close();
    				} catch (SQLException e) {
    					e.printStackTrace();
    				}
    			}
    		}
    	}
    	
    }
    
	public static void main(String[] args) {
//		Map map = new HashMap();
//		map.put("A0000", "7552733A-9111-46AC-B517-79720A06EC3B");
//		map.put("A0101", "�¾�");
//		map.put("XGSJ", "20160224");
//		Object obj = mapToObject(A01temp.class ,map);
//		A01temp t = (A01temp) obj;
//		System.out.println(t.getXgsj());
		B01 b1 = new B01();
		b1.setB0101("2");
		b1.setB0104("ddd11");
		b1.setB0107("22223");
		b1.setB0111("wera");
		B01 b2 = new B01();
		b2.setB0101("1");
		b2.setB0104("ddd11");
		b2.setB0111("wera2");
		try {
//			Map m = convertBean(obj);
//			System.out.println(m.get("XGSJ").toString() + m.get("A0101").toString());
			List list = getLogInfo(b2, b1);
			CommonQueryBS.systemOut(JSONArray.fromObject(list).toString());
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
