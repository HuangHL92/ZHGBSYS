package com.insigma.siis.local.business.datavaerify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.comm.BusinessBSSupport;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class DataComparisonBS extends BusinessBSSupport {
	
	public static List getVirtualResult(String node, String imprecordid, int type) throws AppException {
		StopWatch w1 = new StopWatch();
		w1.start();
		HBSession sess = HBUtil.getHBSession();
		List<HashMap> list_map = new ArrayList<HashMap>();;
		try {
			Imprecord impr = (Imprecord) sess.get(Imprecord.class, imprecordid);
			String impdeptid = impr.getImpdeptid();
			if(node.equals("-1")){
				HashMap map = new HashMap();
				String sstr = " select (select count(b.b0111) from b01 b where b.b0121 = a.b0111) as count1,"
						+ "(select count(1) from b01"+impr.getImptemptable()+" b  "+(type == 1 ? "" : " where b.b0194 in (1,3) ")+") as count2,"
						+ "(select count(1) from b01 b where b0111 like '"+impdeptid+"%' "+(type == 1 ? "" : " and b.b0194 in (1,3) ")+") as count3,"
						+ "(select count(1) from b01"+impr.getImptemptable()+" b where a.b0101=b.b0101 and a.b0114=b.b0114) as comcount,"
						+ "a.b0111 b0111,a.b0101 b0101,a.b0121 b0121,a.sortid,a.b0194 b0194,"
						+ "a.b0114 b0114 "
						+ " from b01 a"
						+ " where a.b0111='" + impdeptid + "' order by sortid";
				List<HashMap> list_map1 = CommonQueryBS.getQueryInfoByManulSQL(sstr.toString());
				map = list_map1.get(0);
				map.put("b0101", map.get("b0101")+"(全部："+map.get("count3")+")");
				map.put("comcount", map.get("comcount")+"(全部："+map.get("count2")+")");
				map.put("comcount2","1(全部："+map.get("count3")+")");
				if(type == 3){
					map.put("count1", "0");
				}
				list_map.add(map);
			} else {
				String str = "select (select count(b.b0111) from b01 b where b.b0121 = a.b0111 "+(type == 1 ? "" : " and b.b0194 in (1,3) ")+" ) as count1,"
						+ "(select count(1) from b01"+impr.getImptemptable()+" b where a.b0101=b.b0101 and a.b0114=b.b0114) as comcount,"
						+ "a.b0111 b0111,a.b0101 b0101,a.b0121 b0121,a.sortid,a.b0194 b0194,"
						+ "a.b0114 b0114 "
						+ " from b01 a "
						+ " where a.b0121 = '" + (node.equals("-1")?impdeptid: node) + "' "+(type == 1 ? "" : " and a.b0194 in (1,3) ")+" order by sortid";
				List<HashMap> list_map2 = CommonQueryBS.getQueryInfoByManulSQL(str.toString());
				list_map.addAll(list_map2);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("获取查询数据异常！");
		}
		w1.stop();
		CommonQueryBS.systemOut("机构授权树执行时间：" + w1.elapsedTime());
		return list_map;
	}

	private String hasChildren(String id) {
		String sql = "select b.b0111 from B01 b where b.B0111 like '" + id
				+ "%' order by sortid";// -1其它现职人员
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			return "false";
		} else {
			return "true";
		}
	}

}
