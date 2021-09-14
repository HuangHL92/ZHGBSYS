package com.insigma.siis.local.pagemodel.gbmc.expexcel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.pagemodel.gbmc.pojo.Gbmc;
import com.insigma.siis.local.pagemodel.gbmc.pojo.Gbmc1;
import com.insigma.siis.local.pagemodel.gbmc.pojo.Gbmc2;

public class ExpTPB {
	/**
	 *  ����sql������Ա�����
	 * @param sql
	 * @return
	 * @throws AppException 
	 */
	public static List<Gbmc1>  querybyid_gbmc1(String sql) throws AppException{
		 HBSession session = HBUtil.getHBSession();
		    SQLQuery sqlQuery = session.createSQLQuery(sql);
		    sqlQuery.setResultTransformer(Transformers.aliasToBean(Gbmc1.class));
		    List<Gbmc1> list = sqlQuery.list();
		    return list;
	}
	/**
	 *  ����sql���� ����������Ա�����
	 * @param sql
	 * @return
	 * @throws AppException 
	 */
	public static List<Gbmc2>  querybyid_gbmc2(String sql) throws AppException{
		 HBSession session = HBUtil.getHBSession();
		    SQLQuery sqlQuery = session.createSQLQuery(sql);
		    sqlQuery.setResultTransformer(Transformers.aliasToBean(Gbmc2.class));
		    List<Gbmc2> list = sqlQuery.list();
		    return list;
	}
	/**
	 *  ����sql���� ���ݸɲ�������Ա�����
	 * @param sql
	 * @return
	 * @throws AppException 
	 */
	public static List<Gbmc>  querybyid_gbmc3(String sql) throws AppException{
		 HBSession session = HBUtil.getHBSession();
		    SQLQuery sqlQuery = session.createSQLQuery(sql);
		    sqlQuery.setResultTransformer(Transformers.aliasToBean(Gbmc.class));
		    List<Gbmc> list = sqlQuery.list();
		    return list;
	}
	/**
	 * ����sql���ɲ�������Ա�����
	 * @param sql
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	public static Map<String, List<Gbmc1>> querybyid_gbmc(String sql) throws RadowException, AppException{
	  
	  //���ݻ�������������Ա��Ϣ����
		LinkedHashMap<String, List<Gbmc1>> gruopMap = null;
	 
	  if(!StringUtils.isEmpty(sql))
	  {
		  List<Gbmc1> list = querybyid_gbmc1(sql);
	  if(list.size()!=0)
	  {
		  gruopMap = new LinkedHashMap<String, List<Gbmc1>>();
	  }
      for (Gbmc1 gbmc : list) {

    	  String group = gbmc.getA0201b()+"&"+gbmc.getB0101();
  	    //����Ǹû�������ְ��Ա�����з�������Ӹ÷���
        if(gruopMap.get(group)==null)
        {
        	gruopMap.put(group, new ArrayList<Gbmc1>());
        }
        //����и÷�����ֱ�������Ա��Ϣ
        gruopMap.get(group).add(gbmc);
	}
	  }

    return gruopMap;
 	}
	
}
