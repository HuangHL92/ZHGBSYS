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
	 *  根据sql生成人员结果集
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
	 *  根据sql生成 会议名册人员结果集
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
	 *  根据sql生成 杭州干部名册人员结果集
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
	 * 根据sql生成并分组人员结果集
	 * @param sql
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	public static Map<String, List<Gbmc1>> querybyid_gbmc(String sql) throws RadowException, AppException{
	  
	  //根据机构主键进行人员信息分组
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
  	    //如果是该机构的任职人员还进行分组则添加该分组
        if(gruopMap.get(group)==null)
        {
        	gruopMap.put(group, new ArrayList<Gbmc1>());
        }
        //如果有该分组则直接添加人员信息
        gruopMap.get(group).add(gbmc);
	}
	  }

    return gruopMap;
 	}
	
}
