package com.utils;

import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.Aa10;

public class AA10Util {
	
	/**
	 * 根据代码类别和代码值获取代码名称
	 * @param AAA100  代码类别
	 * @param AAA102 代码值
	 * @return  String 代码名称
	 */
	@SuppressWarnings("unchecked")
	public String getAA103ByAAA100AndAAA102(String AAA100,String AAA102){
		String AAA103 = "";
		StringBuffer sql = new StringBuffer();
		sql.append("select aaa103");
		sql.append("  from aa10");
		sql.append(" where aaa100 = '"+AAA100+"' ");
		sql.append("   and aaa102 = '"+AAA102+"' ");
		
		HBSession session = HBUtil.getHBSession();
		List<Object> queryInfoList = session.createSQLQuery(sql.toString()).list();
		if(queryInfoList!=null && queryInfoList.size()>0){
			AAA103 = queryInfoList.get(0).toString();
		}
		return AAA103;
	}
	
	@SuppressWarnings("unchecked")
	public List<Aa10> queryAA10ByAAA100(String aaa100){
		HBSession session = HBUtil.getHBSession();
		List<Aa10> Aa10List = (ArrayList<Aa10>)session.createQuery("from Aa10 where aaa100='"+aaa100+"'").list();
		return Aa10List;
	}
	
	@SuppressWarnings("unchecked")
	public String getAA105ByAAA100AndAAA102(String AAA100,String AAA102){
		String AAA105 = "";
		StringBuffer sql = new StringBuffer();
		sql.append("select aaa105");
		sql.append("  from aa10");
		sql.append(" where aaa100 = '"+AAA100+"' ");
		sql.append(" and   aaa102 = '"+AAA102+"' ");
		
		HBSession session = HBUtil.getHBSession();
		List<Object> queryInfoList = session.createSQLQuery(sql.toString()).list();
		if(queryInfoList!=null && queryInfoList.size()>0){
			AAA105 = queryInfoList.get(0).toString();
		}
		return AAA105;
	}
	
	@SuppressWarnings("unchecked")
	public String getAA103ByAAA100AndAAA105(String AAA100,String AAA105){
		String AAA103 = "";
		StringBuffer sql = new StringBuffer();
		sql.append("select aaa103");
		sql.append("  from aa10");
		sql.append(" where aaa100 = '"+AAA100+"' ");
		sql.append("   and aaa105 = '"+AAA105+"' ");
		
		HBSession session = HBUtil.getHBSession();
		List<Object> queryInfoList = session.createSQLQuery(sql.toString()).list();
		if(queryInfoList!=null && queryInfoList.size()>0){
			AAA103 = queryInfoList.get(0).toString();
		}
		return AAA103;
	}
	
	@SuppressWarnings("unchecked")
	public String getAAA102ByAAA100AndAAA103(String AAA100,String AAA103){
		String AAA102="";
		StringBuffer sql=new StringBuffer();
		sql.append("select aaa102");
		sql.append("  from aa10");
		sql.append(" where aaa100 = '"+AAA100+"' ");
		sql.append("   and aaa103 = '"+AAA103+"' ");
		
		HBSession session = HBUtil.getHBSession();
		List<Object> queryInfoList = session.createSQLQuery(sql.toString()).list();
		if(queryInfoList!=null && queryInfoList.size()>0){
			AAA102 = queryInfoList.get(0).toString();
		}
		return AAA102;
		
	}
}
