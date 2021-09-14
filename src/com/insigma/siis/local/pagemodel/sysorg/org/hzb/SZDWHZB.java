package com.insigma.siis.local.pagemodel.sysorg.org.hzb;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;

public class SZDWHZB {
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
}
