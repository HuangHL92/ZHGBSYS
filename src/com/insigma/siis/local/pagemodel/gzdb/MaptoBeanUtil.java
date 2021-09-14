/**
 * Copyright (C), 2019-2019, INSIGMACompany
 * FileName: JinQian
 * Author:   Qian_INSIGMA
 * Date:     2019/8/7 19:53
 * Description: MapToBean
 */
package com.insigma.siis.local.pagemodel.gzdb;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.comm.search.CommonMethodBS;

/**
 * @author Qian_INSIGMA CratedBy  2019/8/715:53
 * Description :  MapToBean
 */
public class MaptoBeanUtil {
    /**
     * ��Map����ͨ���������ת����Bean����
     *
     * @param mapResult ������ݵ�map����
     * @param clazz ��ת����class
     * @return ת�����Bean����
     * @throws Exception �쳣
     */
    public static Object mapToBean(HashMap<String, Object> mapResult, Class<?> clazz) throws Exception {
        Object obj = clazz.newInstance();
        if(mapResult != null && mapResult.size() > 0) {
            for(Map.Entry<String, Object> entry : mapResult.entrySet()) {

                String propertyName = entry.getKey();       //������
                Object value = entry.getValue();
                String setMethodName = "set"
                        + propertyName.substring(0, 1).toUpperCase()
                        + propertyName.substring(1);
                Field field = getClassField(clazz, propertyName);
                if(field==null)
                    continue;
                Class<?> fieldTypeClass = field.getType();
                value = convertValType(value, fieldTypeClass);
                try{
                    clazz.getMethod(setMethodName, field.getType()).invoke(obj, value);
                }catch(NoSuchMethodException e){
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    /**
     * ��Object���͵�ֵ��ת����bean�����������Ӧ������ֵ
     *
     * @param value Object����ֵ
     * @param fieldTypeClass ���Ե�����
     * @return ת�����ֵ
     */
    private static Object convertValType(Object value, Class<?> fieldTypeClass) {
        Object retVal = null;
        if(Long.class.getName().equals(fieldTypeClass.getName())
                || long.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Long.parseLong(value.toString());
        } else if(Integer.class.getName().equals(fieldTypeClass.getName())
                || int.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Integer.parseInt(value.toString());
        } else if(Float.class.getName().equals(fieldTypeClass.getName())
                || float.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Float.parseFloat(value.toString());
        } else if(Double.class.getName().equals(fieldTypeClass.getName())
                || double.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Double.parseDouble(value.toString());
        } else {
            retVal = value;
        }
        return retVal;
    }

    /**
     * ��ȡָ���ֶ����Ʋ�����class�еĶ�Ӧ��Field����(�������Ҹ���)
     *
     * @param clazz ָ����class
     * @param fieldName �ֶ�����
     * @return Field����
     */
    private static Field getClassField(Class<?> clazz, String fieldName) {
        if( Object.class.getName().equals(clazz.getName())) {
            return null;
        }
        Field []declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        Class<?> superClass = clazz.getSuperclass();
        if(superClass != null) {// �򵥵ĵݹ�һ��
            return getClassField(superClass, fieldName);
        }
        return null;
    }
  //�ж��Ƿ��Ǹɲ��ۺϴ��쵼
    public boolean isLeader() throws AppException{
    	boolean flag = false;
    	UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
    	String userid = user.getId();
        String sql = "select userid from SMT_USER t where userid ='"+userid+"'" +
        		" and 1=1"; 
        List<HashMap<String, Object>> list=CommonMethodBS.getListBySQL( sql );
        if (list.size() > 0) {
       	   flag = true;
       }
       return flag;
   } 
    
}

