package com.insigma.siis.local.pagemodel.fxyp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;

public class RYMDPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("init0");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("init0")
	public int init0() throws RadowException, AppException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		HashMap<String, Object> map1=new HashMap<String, Object>();
		List<HashMap<String, Object>> listgw =new ArrayList<HashMap<String,Object>>();
		map1.put("nrgw", "一人任职多个岗位");
		listgw.add(map1);
		JSONArray  updateunDataStoreObjectgw = JSONArray.fromObject(listgw);
		this.getExecuteSG().addExecuteCode("AddGW("+updateunDataStoreObjectgw+");");
		try {
			List<HashMap<String, Object>> listCode1 =new ArrayList<HashMap<String,Object>>();
			//一人任职多个岗位
			String sql1zwqc00="select  zwqc00  from  fxyp where mntp00 = '"+mntp00+"' "
					+ "group by  zwqc00 having count (zwqc00) > 1";
			CommQuery cqbs1=new CommQuery();
			List<HashMap<String, Object>> listzwqc00 =cqbs1.getListBySQL(sql1zwqc00.toString());
			if(listzwqc00!=null&&listzwqc00.size()>0) {
				for(int i=0;i<listzwqc00.size();i++) {
					String sql="select (select zwqc00 from hz_mntp_zwqc where hz_mntp_zwqc.zwqc00 = rxfxyp.zwqc00) zwqc00,"
							+ "(select A0192A from hz_mntp_zwqc where hz_mntp_zwqc.zwqc00 = rxfxyp.zwqc00) NRGW,"
							+ "(select a0101 from a01 where a01.a0000 = rxfxyp.a0000) NAME,"
							+ "(select decode(a01.a0104, 1, '男', 2, '女') from a01 where a01.a0000 = rxfxyp.a0000) SEX,"
							+ "(select substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)from a01 where a01.a0000 = rxfxyp.a0000) YEAR,"
							+ "(select a0192a from a01 where a01.a0000 = rxfxyp.a0000) WORK  " + 
							"from rxfxyp where zwqc00 = '"+listzwqc00.get(i).get("zwqc00").toString()+"'   and (select fxyp07 from hz_mntp_zwqc where hz_mntp_zwqc.zwqc00 = rxfxyp.zwqc00) =1";
					List<HashMap<String, Object>> listpeople =cqbs1.getListBySQL(sql.toString());
					if(listpeople!=null&&listpeople.size()>0) {
						for(int j=0;j<listpeople.size();j++) {
							listCode1.add(listpeople.get(j));
						}
					}
				}
			}
			List<HashMap<String, Object>> list1 =new ArrayList<HashMap<String,Object>>();
			if(listCode1.size()>0) {
				for (HashMap<String, Object> hashMap : listCode1) {
					list1.add(hashMap);
				}
				JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(list1);
				this.getExecuteSG().addExecuteCode("AddGWMore("+updateunDataStoreObject1+");");
			}else {
				HashMap<String, Object> mapnull=new HashMap<String, Object>();
				mapnull.put("nrgw", " ");
				mapnull.put("name", " ");
				mapnull.put("sex", " ");
				mapnull.put("year", " ");
				mapnull.put("work", " ");
				list1.add(mapnull);
				JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(list1);
				this.getExecuteSG().addExecuteCode("AddGWMore("+updateunDataStoreObject1+");");
			}
		}
		catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		this.setNextEventName("init1");
		return EventRtnType.NORMAL_SUCCESS;

	}
	@PageEvent("init1")
	public int init1() throws RadowException, AppException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		HashMap<String, Object> map1=new HashMap<String, Object>();
		List<HashMap<String, Object>> listgw =new ArrayList<HashMap<String,Object>>();
		map1.put("nrgw", "一个岗位多人任职");
		listgw.add(map1);
		JSONArray  updateunDataStoreObjectgw = JSONArray.fromObject(listgw);
		this.getExecuteSG().addExecuteCode("AddGW("+updateunDataStoreObjectgw+");");
		try {
			List<HashMap<String, Object>> listCode2 =new ArrayList<HashMap<String,Object>>();
			//一个岗位多人任职
			String sql2zwqc00="select zwqc00 from hz_mntp_zwqc where mntp00='"+mntp00+"'";
			CommQuery cqbs2=new CommQuery();
			List<HashMap<String, Object>> listzwqc00=cqbs2.getListBySQL(sql2zwqc00.toString());
			for(int i=0;i<listzwqc00.size();i++) {
			String zwqc00=listzwqc00.get(i).get("zwqc00").toString();
			String sql2="select (select zwqc00 from hz_mntp_zwqc where hz_mntp_zwqc.zwqc00 = rxfxyp.zwqc00) zwqc00,"
							+ "(select A0192A from hz_mntp_zwqc where hz_mntp_zwqc.zwqc00 = rxfxyp.zwqc00) NRGW,"
							+ "(select a0101 from a01 where a01.a0000 = rxfxyp.a0000) NAME,"
							+ "(select decode(a01.a0104, 1, '男', 2, '女') from a01 where a01.a0000 = rxfxyp.a0000) SEX,"
							+ "(select substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)from a01 where a01.a0000 = rxfxyp.a0000) YEAR,"
							+ "(select a0192a from a01 where a01.a0000 = rxfxyp.a0000) WORK  " + 
							"from rxfxyp where zwqc00 = '"+zwqc00+"'";
			List<HashMap<String, Object>> listpeople=cqbs2.getListBySQL(sql2.toString());
			if(listpeople!=null&&listpeople.size()>1) {
				for(int j=0;j<listpeople.size();j++) {
					listCode2.add(listpeople.get(j));
				}
			}
			}
			List<HashMap<String, Object>> list2 =new ArrayList<HashMap<String,Object>>();
			int a=0;
			if(listCode2.size()>0) {
				list2.add(listCode2.get(0));
				for(int i=1;i<listCode2.size();i++) {
				if(listCode2.get(a).get("zwqc00").equals(listCode2.get(i).get("zwqc00"))) {
					list2.add(listCode2.get(i));
					continue;
				}else {
					String length=String.valueOf(list2.size());
					JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(list2);
					this.getExecuteSG().addExecuteCode("AddPeopleMore("+updateunDataStoreObject2+","+length.toString()+");");
					list2.removeAll(list2);
					list2.add(listCode2.get(i));
					a=i;
				}	
			}
			String length=String.valueOf(list2.size());
			JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(list2);
			this.getExecuteSG().addExecuteCode("AddPeopleMore("+updateunDataStoreObject2+","+length.toString()+");");
			}else {
				HashMap<String, Object> mapnull=new HashMap<String, Object>();
				mapnull.put("nrgw", " ");
				mapnull.put("name", " ");
				mapnull.put("sex", " ");
				mapnull.put("year", " ");
				mapnull.put("work", " ");
				list2.add(mapnull);
				JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(list2);
				this.getExecuteSG().addExecuteCode("AddPeopleMore("+updateunDataStoreObject1+");");
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		this.setNextEventName("init2");
		return EventRtnType.NORMAL_SUCCESS;
	
	}
	@PageEvent("init2")
	public int init2() throws RadowException, AppException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		HashMap<String, Object> map1=new HashMap<String, Object>();
		List<HashMap<String, Object>> listgw =new ArrayList<HashMap<String,Object>>();
		map1.put("nrgw", "仅免现职");
		listgw.add(map1);
		JSONArray  updateunDataStoreObjectgw = JSONArray.fromObject(listgw);
		this.getExecuteSG().addExecuteCode("AddGW("+updateunDataStoreObjectgw+");");
		try {
			//免现职
			String sql3="select  '' NRGW,"
					+ "(select a0101 from a01 where a01.a0000= rxfxyp.a0000) NAME,"
					+ "(select decode(a01.a0104, 1, '男', 2, '女') from a01 where a01.a0000= rxfxyp.a0000) SEX,"
					+ "(select substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) from a01 where a01.a0000= rxfxyp.a0000) YEAR,"
					+ "(select a0192a from a01 where a01.a0000= rxfxyp.a0000) WORK "
					+ "from rxfxyp where zwqc00 in "
					+ "(select zwqc00 from fxyp where mntp00='"+mntp00+"' and fxyp07='-1') "
					+ "and tp0101 not in  "
					+ "(select tp0101 from rxfxyp where zwqc00 in  "
					+ "(select zwqc00 from fxyp where mntp00='"+mntp00+"' and fxyp07='1')"
					+ ")" ;
			CommQuery cqbs3=new CommQuery();
			List<HashMap<String, Object>> listCode3=cqbs3.getListBySQL(sql3.toString());
			List<HashMap<String, Object>> list3 =new ArrayList<HashMap<String,Object>>();
			if(listCode3.size()>0) {
			for (HashMap<String, Object> hashMap : listCode3) {
				list3.add(hashMap);
			}
			JSONArray  updateunDataStoreObject3 = JSONArray.fromObject(list3);
			this.getExecuteSG().addExecuteCode("AddGWnull("+updateunDataStoreObject3+");");
			}else {
				HashMap<String, Object> mapnull=new HashMap<String, Object>();
				mapnull.put("nrgw", " ");
				mapnull.put("name", " ");
				mapnull.put("sex", " ");
				mapnull.put("year", " ");
				mapnull.put("work", " ");
				list3.add(mapnull);
				JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(list3);
				this.getExecuteSG().addExecuteCode("AddGWnull("+updateunDataStoreObject1+");");
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
