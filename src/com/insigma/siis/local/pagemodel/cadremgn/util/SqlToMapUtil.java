package com.insigma.siis.local.pagemodel.cadremgn.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.commform.hibernate.HList;
import com.insigma.odin.framework.commform.hibernate.HSession;
import com.insigma.odin.framework.commform.hibernate.HUtil;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.util.BeanUtil;

public class SqlToMapUtil {
	
	public static Map<String,String> HListTomap(String sql,String key, String val) throws AppException{
		
		HList list=new HList(); 
		list.retrieve(sql);
		
		LinkedHashMap map= new LinkedHashMap<String, String>();
		for(int i=0; i<list.getRowCount();i++){
			map.put(list.getString(key, i), list.getString(val, i));				
		}
		
		return map;
		
	}
	
	
	/**
	 * @$Comment 将查询出来的额List <HashMap>转换为List <Class>
	 * @param vect
	 * @param classbean
	 * @return
	 * @throws AppException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static List disassembleHashMap(Vector vect, Class classbean) throws AppException, InstantiationException, IllegalAccessException {
		
		Iterator<?> iterator = vect.iterator();
		
		List list = new LinkedList();
		while (iterator.hasNext()) {
			Object dto = classbean.newInstance();
			HashMap tmp = (HashMap) iterator.next();
			BeanUtil.copyHashMap(tmp,dto);
			list.add(dto);
		}
		
		return list;
		
	}
	

	/**
	 * 给前台页面select标签动态赋值
	 * 
	 */
	public static void setValueListForSelect(PageModel pageModel, String property, Map map) {
		if (map != null) {
			Set<Object> key = map.keySet();
			StringBuffer data = new StringBuffer();
			data.append("odin.reSetSelectData('" + property
					+ "',odin.ext.decode(\"[");
			StringBuffer buf = new StringBuffer();
			for (Iterator<Object> it = key.iterator(); it.hasNext();) {
				Object k = it.next();
				String keyStr = k.toString();
				String valueStr = map.get(k).toString();
//				String keyStr = replaceSpecial(k.toString());
//				String valueStr =  replaceSpecial(map.get(k).toString());
				if (!buf.toString().equals("")) {
					buf.append(",");
				}
				buf.append("{");
				buf.append("'key':'" + keyStr + "',");
				buf.append("'value':'" + valueStr + "'");
				buf.append("}");
			}
			data.append(buf.toString());
			data.append("]\"));");
			pageModel.getExecuteSG().addExecuteCode(data.toString());
		}
	}

	public static String replaceSpecial(String str){
		String temp=str
		.replace("\"", "")  
		.replace("\'", "")  
		.replace("\\", "")
		.replace("/", "")
		.replace("\n", "")  
		.replace("\r", "")
		;
		return temp;
	}

}
