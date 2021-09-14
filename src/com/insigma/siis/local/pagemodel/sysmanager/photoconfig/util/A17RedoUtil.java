package com.insigma.siis.local.pagemodel.sysmanager.photoconfig.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.fr.report.core.A.K;
import com.fr.report.core.A.V;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A17;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class A17RedoUtil {

	public static void redoA17All() throws AppException, SQLException{
		CommQuery cqbs=new CommQuery();
		HBSession sess = HBUtil.getHBSession();
		try {
			String updateSql = "DROP TABLE hz_a17_redo";
			sess.createSQLQuery(updateSql).executeUpdate();
		} catch (Exception e) {
		}
		String updateSql = "create table hz_a17_redo "
				+ "( a0000 varchar2(40), a1700 varchar2(40), complete varchar2(2000))";
		sess.createSQLQuery(updateSql).executeUpdate();
		
		List<String> a0000List = sess.createSQLQuery("select a0000 from a01").list();
		
		String a17sql = "select a0000,a1700,a1701,a1702,a1703 from hz_a17 where  a1709 = '2' order by a0000, to_number(a1799)";
		
		List<HashMap<String, String>> a17MainList= cqbs.getListBySQL2(a17sql);
		//每个人对应的主简历
		Map<String,List<HashMap<String, String>>> a17Map = new HashMap<String,List<HashMap<String, String>>>(20000);
		for(HashMap<String, String> a17 : a17MainList){
			List<HashMap<String, String>> a17List = a17Map.get(a17.get("a0000"));
			if(a17List!=null){
				a17List.add(a17);
			}else{
				a17List = new ArrayList<HashMap<String, String>>();
				a17List.add(a17);
				a17Map.put(a17.get("a0000"), a17List);
			}
		}
		
		
		String a17DetailSql = "select a1700,a1701,a1702,a1703,a1709,belong_to_a1700 from hz_a17 where"
				+ "   a1709 in ('3','4') order by belong_to_a1700,a1709, to_number(a1701)";
		List<HashMap<String, String>> a17DetailList= cqbs.getListBySQL2(a17DetailSql);
		
		//每条简历对应的有括号的简历
		Map<String,List<HashMap<String, String>>> a17DetailMap = new HashMap<String,List<HashMap<String, String>>>(20000);
		for(HashMap<String, String> a17m : a17DetailList){
			List<HashMap<String, String>> a17Listm = a17DetailMap.get(a17m.get("belong_to_a1700"));
			if(a17Listm!=null){
				a17Listm.add(a17m);
			}else{
				a17Listm = new ArrayList<HashMap<String, String>>();
				a17Listm.add(a17m);
				a17DetailMap.put(a17m.get("belong_to_a1700"), a17Listm);
			}
		}
		
		int i=0;
		Connection conn = sess.connection();
		conn.setAutoCommit(false);
		PreparedStatement pst = conn.prepareStatement("insert into hz_a17_redo(a0000,a1700,complete) "
				+ " values(?,?,?)");
		for(String a0000 : a0000List){
			/*String a17sql = "from A17 where a0000 = '" + a0000 + "' and a1709 = '2' order by to_number(a1799)";
			
			List<A17> a17MainList = sess.createQuery(a17sql).list();*/
			List<HashMap<String, String>> a17MainListOne = a17Map.get(a0000);
			if(a17MainListOne==null){
				continue;
			}
			for(HashMap<String, String> a17 : a17MainListOne){
				String a1700 = a17.get("a1700");
				/*String a17DetailSql = "select a1700,a1701,a1702,a1703 from hz_a17 where"
						+ " belong_to_a1700 = '" + a1700 + "' and a1709 in ('3','4') order by to_number(a1701)";
				List<HashMap<String, String>> a17DetailList= cqbs.getListBySQL2(a17DetailSql);*/
				List<HashMap<String, String>> a17DetailListone = a17DetailMap.get(a1700);
				if(a17DetailListone==null){
					continue;
				}
				String complete=saveZZA17(a17DetailListone,a0000,a17);
				pst.setString(1, a0000);
				pst.setString(2, a1700);
				pst.setString(3, complete);
				pst.addBatch();
				//System.out.print(i+":"+a1700+":"+a0000+":");
				//System.out.println(complete);
				if(i==100){
					break;
				}
			}
			i++;
		}
		pst.executeBatch();
		conn.commit();
		System.out.println(i);
	}
	
	public static String saveZZA17(List<HashMap<String, String>> list,String a0000,HashMap<String, String> belongToA17) {
		String buffer=belongToA17.get("a1703");
		if(buffer==null){
			buffer = "";
		}
		
		//String belong_to_a1700=belongToA17.getA1700();
		//String belongToA1701=belongToA17.getA1701();
		//String belongToA1702=belongToA17.getA1702();
		String a1700,a1701,a1702,a1703,a1709;
		/**
		 * 将数据存储库中
		 */
		int n = 0;
		if(list.size()==0||list==null) {
			return buffer;
		}
		for (HashMap<String, String> map : list) {
			a1700 = map.get("a1700");
			if("".equals(a1700)||a1700==null) {
				a1700=UUID.randomUUID().toString().replaceAll("-", "");
			}
			a1701 = map.get("a1701");
			a1702 = map.get("a1702");
			a1703 = map.get("a1703");
			a1709 = map.get("a1709");
			if (a1701 == null){
				continue;
			}
				
			if("3".equals(a1709)) {
				if(buffer.indexOf("\n（")!=-1||buffer.indexOf("其间")!=-1) {
					if(StringUtils.isEmpty(a1701)){
						buffer=buffer.substring(0, buffer.length()-1)+"；"+DateUtil.dataStrFormart(a1702, ".", "", "") +a1703+"）";
					}else{
						if(StringUtils.isEmpty(a1702)){
							buffer=buffer.substring(0, buffer.length()-1)+"；"+DateUtil.dataStrFormart(a1701, ".", "", "")+"起" +a1703+"）";
						}else{
							buffer=buffer.substring(0, buffer.length()-1)+"；"+DateUtil.dataStrFormart(a1701, ".", "", "")+"--"+DateUtil.dataStrFormart(a1702, ".", "", "")+""+a1703+"）";
						}
					}
				}else {
					if(StringUtils.isEmpty(a1701)){
						buffer=buffer+"（其间："+DateUtil.dataStrFormart(a1702, ".", "", "")+a1703+"）";

					}else{
						if(StringUtils.isEmpty(a1702)){
							buffer=buffer+"（其间："+DateUtil.dataStrFormart(a1701, ".", "", "")+"起"+a1703+"）";
						}else{
							buffer=buffer+"（其间："+DateUtil.dataStrFormart(a1701, ".", "", "")+"--"+DateUtil.dataStrFormart(a1702, ".", "", "")+""+a1703+"）";

						}
					}
				}
			}else if ("4".equals(a1709)) {
				//buffer=buffer.replace("（其间：", "\n（");
				if(buffer.indexOf("\n（")!=-1) {
					if(StringUtils.isEmpty(a1702)){
						buffer=buffer.substring(0, buffer.length()-1)+"；"+DateUtil.dataStrFormart(a1701, ".", "", "")+"起" +a1703+"）";
					}else if(StringUtils.isEmpty(a1701)){
						buffer=buffer.substring(0, buffer.length()-1)+"；"+DateUtil.dataStrFormart(a1702, ".", "", "") +a1703+"）";
					}else{
						
						buffer=buffer.substring(0, buffer.length()-1)+"；"+DateUtil.dataStrFormart(a1701, ".", "", "")+"--"+DateUtil.dataStrFormart(a1702, ".", "", "")+""+a1703+"）";
					}
				}else {
					if(StringUtils.isEmpty(a1702)){
						buffer=buffer+"\n（"+DateUtil.dataStrFormart(a1701, ".", "", "")+"起"+a1703+"）";
					}else if(StringUtils.isEmpty(a1701)){
						buffer=buffer+"\n（"+DateUtil.dataStrFormart(a1702, ".", "", "") +a1703+"）";
					}else{
						buffer=buffer+"\n（"+DateUtil.dataStrFormart(a1701, ".", "", "")+"--"+DateUtil.dataStrFormart(a1702, ".", "", "")+""+a1703+"）";
					}
				}
			}
		}
		return buffer;
	}
}
