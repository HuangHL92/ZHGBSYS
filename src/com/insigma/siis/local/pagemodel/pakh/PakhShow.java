package com.insigma.siis.local.pagemodel.pakh;

import java.util.HashMap;
import java.util.List;

import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;

public class PakhShow {

	public static String getColData(String egl00){
		try {
			String sql = "select l.egl04,c.etc01,t.et01,t.et02,t.et03,t.et04,c.etc00,t.et00 from PAKH_LIST l,"
					+ "PAKH_TARGET_CLASS c,PAKH_TARGET t where l.ets00=c.ets00 and t.etc00=c.etc00 and l.egl00='"+egl00+"' order by c.etc03,t.et05";
			
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
			JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);

			return updateunDataStoreObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "[]";
	}
	
	public static String getB01Data(String egl00){
		try {
			String sql = "select b0101,b01id from b01 where b01id in (select b01id from PAKH_TARGET_ORG where ets00=(select ets00 from PAKH_LIST where egl00='"+egl00+"')) order by b0269";
			
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
			JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);

			return updateunDataStoreObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "[]";
	}
	/**
	 * ∆¿∑÷–≈œ¢
	 * @param egl00
	 * @return
	 */
	public static String getGradeData(String egl00){
		try {
			String sql = "select v.*,g.eg00,g.b01id,g.grade from "
					+ "(select l.egl04,c.etc01,t.et01,t.et02,t.et03,t.et04,c.etc00,t.et00,l.egl00 "
					+ " from PAKH_LIST l,PAKH_TARGET_CLASS c,PAKH_TARGET t "
					+ " where l.ets00=c.ets00 and t.etc00=c.etc00 order by c.etc03,t.et05) v "
					+ " join PAKH_GERDEN g on v.et00=g.et00 and v.egl00=g.egl00  where v.egl00='"+egl00+"'";
			
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
