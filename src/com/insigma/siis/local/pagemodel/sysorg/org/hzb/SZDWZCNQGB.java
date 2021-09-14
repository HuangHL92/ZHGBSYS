package com.insigma.siis.local.pagemodel.sysorg.org.hzb;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;

public class SZDWZCNQGB {
	public static String expData(String gqgx){
		try {
			String sql = "";
			if("gqgx".equals(gqgx)){//国企高校
				sql = HBUtil.getValueFromTab("comments", "sourcetable", "table_name='GQGXHZB'");
			}else{
				sql = HBUtil.getValueFromTab("comments", "sourcetable", "table_name='SZDWHZB'");
			}
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
			JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);

			return updateunDataStoreObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "[]";
	}
	public static String getColData(String egl00){
	    try {
	      String sql = "select b0101,b0111, \r\n" + 
    		  "(select count(distinct a.a0000) from a01 a,a02 b where a.a0000=b.a0000 and a0163='1' and (a.a0165 like '%12%' or a.a0165 like '%50%' or\r\n" + 
	      		"               a.a0165 like '%07%' or a.a0165 like '%08%' or\r\n" + 
	      		"               a.a0165 like '%09%' or a.a0165 like '%13%' or\r\n" + 
	      		"               a.a0165 like '%14%' or a.a0165 like '%15%' or\r\n" + 
	      		"               a.a0165 like '%51%') \r\n and a0281='true' and a0201b like b01.b0111||'%') cjzs,\r\n" + 
	      		"(select count(distinct a.a0000) from a01 a,a02 b where a.a0000=b.a0000 and a0163='1' and (a.a0165 like '%12%' or a.a0165 like '%07%' or\r\n" + 
	      		"               a.a0165 like '%09%' or a.a0165 like '%14%' or  a.a0165 like '%51%') \r\n and a0281='true' and a0201b like b01.b0111||'%') cjzz,\r\n" + 
	      		"(select count(distinct a.a0000) from a01 a,a02 b where a.a0000=b.a0000 and a0163='1' and (a.a0165 like '%12%' or a.a0165 like '%50%' or\r\n" + 
	      		"               a.a0165 like '%07%' or a.a0165 like '%08%' or\r\n" + 
	      		"               a.a0165 like '%09%' or a.a0165 like '%13%' or\r\n" + 
	      		"               a.a0165 like '%14%' or a.a0165 like '%15%' or\r\n" + 
	      		"               a.a0165 like '%51%') \r\n" + 
	      		"   and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)<41 and a0281='true' and a0201b like b01.b0111||'%')ssxq,\r\n" + 
				"    case when (select count(distinct a.a0000) from a01 a,a02 b where a.a0000=b.a0000 and a0163='1' and (a.a0165 like '%12%' or a.a0165 like '%50%' or "+
				"               a.a0165 like '%07%' or a.a0165 like '%08%' or                                                                                          "+
				"               a.a0165 like '%09%' or a.a0165 like '%13%' or                                                                                          "+
				"               a.a0165 like '%14%' or a.a0165 like '%15%' or                                                                                          "+
				"               a.a0165 like '%51%')                                                                                                                   "+
				" and a0281='true' and a0201b like b01.b0111||'%')>0 then                                                                                              "+
				"   to_char((select count(distinct a.a0000) from a01 a,a02 b where a.a0000=b.a0000 and a0163='1' and (a.a0165 like '%12%' or a.a0165 like '%50%' or    "+
				"               a.a0165 like '%07%' or a.a0165 like '%08%' or                                                                                          "+
				"               a.a0165 like '%09%' or a.a0165 like '%13%' or                                                                                          "+
				"               a.a0165 like '%14%' or a.a0165 like '%15%' or                                                                                          "+
				"               a.a0165 like '%51%')                                                                                                                   "+
				"   and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)<41 and a0281='true' and a0201b like b01.b0111||'%') *100/                              "+
				"   (select count(distinct a.a0000) from a01 a,a02 b where a.a0000=b.a0000 and a0163='1' and (a.a0165 like '%12%' or a.a0165 like '%50%' or            "+
				"               a.a0165 like '%07%' or a.a0165 like '%08%' or                                                                                          "+
				"               a.a0165 like '%09%' or a.a0165 like '%13%' or                                                                                          "+
				"               a.a0165 like '%14%' or a.a0165 like '%15%' or                                                                                          "+
				"               a.a0165 like '%51%')                                                                                                                   "+
				"         and a0281='true' and a0201b like b01.b0111||'%'),'fm990.0')||'%' else '0' end cj40zb,                                                        "+
	      		"   (select count(distinct a.a0000) from a01 a,a02 b where a.a0000=b.a0000 and a0163='1' and (a.a0165 like '%12%' or a.a0165 like '%50%' or\r\n" + 
	      		"               a.a0165 like '%07%' or a.a0165 like '%08%' or\r\n" + 
	      		"               a.a0165 like '%09%' or a.a0165 like '%13%' or\r\n" + 
	      		"               a.a0165 like '%14%' or a.a0165 like '%15%' or\r\n" + 
	      		"               a.a0165 like '%51%') \r\n" + 
	      		"   and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)<37 and a0281='true' and a0201b like b01.b0111||'%')a37zz,\r\n" + 
				"	    case when (select count(distinct a.a0000) from a01 a,a02 b where a.a0000=b.a0000 and a0163='1' and (a.a0165 like '%12%' or a.a0165 like '%50%' or "+
				"	               a.a0165 like '%07%' or a.a0165 like '%08%' or                                                                                          "+
				"	               a.a0165 like '%09%' or a.a0165 like '%13%' or                                                                                          "+
				"	               a.a0165 like '%14%' or a.a0165 like '%15%' or                                                                                          "+
				"	               a.a0165 like '%51%')                                                                                                                   "+
				"	 and a0281='true' and a0201b like b01.b0111||'%')>0 then                                                                                              "+
				"	   to_char((select count(distinct a.a0000) from a01 a,a02 b where a.a0000=b.a0000 and a0163='1' and (a.a0165 like '%12%' or a.a0165 like '%50%' or    "+
				"	               a.a0165 like '%07%' or a.a0165 like '%08%' or                                                                                          "+
				"	               a.a0165 like '%09%' or a.a0165 like '%13%' or                                                                                          "+
				"	               a.a0165 like '%14%' or a.a0165 like '%15%' or                                                                                          "+
				"	               a.a0165 like '%51%')                                                                                                                   "+
				"	   and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)<37 and a0281='true' and a0201b like b01.b0111||'%') *100/                              "+
				"	   (select count(distinct a.a0000) from a01 a,a02 b where a.a0000=b.a0000 and a0163='1' and (a.a0165 like '%12%' or a.a0165 like '%50%' or            "+
				"	               a.a0165 like '%07%' or a.a0165 like '%08%' or                                                                                          "+
				"	               a.a0165 like '%09%' or a.a0165 like '%13%' or                                                                                          "+
				"	               a.a0165 like '%14%' or a.a0165 like '%15%' or                                                                                          "+
				"	               a.a0165 like '%51%')                                                                                                                   "+
				"	         and a0281='true' and a0201b like b01.b0111||'%'),'fm990.0')||'%' else '0' end cj37zb,                                                        "+
	      		"   (select count(distinct a.a0000) from a01 a,a02 b where a.a0000=b.a0000 and a0163='1' and (a.a0165 like '%12%' or a.a0165 like '%07%' or\r\n" + 
	      		"               a.a0165 like '%09%' or a.a0165 like '%14%' or  a.a0165 like '%51%')\r\n" + 
	      		"   and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)<37 and a0281='true' and a0201b like b01.b0111||'%')a37,\r\n" + 
	      		"     (select count(distinct a.a0000) from a01 a,a02 b where a.a0000=b.a0000 and a0163='1' and (a.a0165 like '%50%' or a.a0165 like '%08%' or\r\n" + 
	      		"               a.a0165 like '%13%' or a.a0165 like '%15%')\r\n" + 
	      		"   and substr(to_char(sysdate,'yyyymm')-substr(a0107,1,6),1,2)<32 and a0281='true' and a0201b like b01.b0111||'%') a32fz\r\n" + 
	      		" from b01 where (b0121='001.001.002' or b0121='001.001.003') order by b0269";
	      
	      CommQuery cqbs=new CommQuery();
	      List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
	      JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);

	      return updateunDataStoreObject.toString();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return "[]";
	  }
}
