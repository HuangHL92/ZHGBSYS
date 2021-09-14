package com.insigma.siis.local.pagemodel.sysorg.org.hzb;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;

public class QSXZDZZZPBB {
	public static String expData(){
		try {
			String sql  = HBUtil.getValueFromTab("comments", "sourcetable", "table_name='QSXZDZZZPBB'");
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
