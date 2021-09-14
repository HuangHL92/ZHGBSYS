package com.insigma.siis.local.pagemodel.fxyp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;
    
public class BDHZPageModel extends PageModel{
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		//String mntp00=request.getParameter("mntp00");
		String mntp00=this.getPageElement("mntp00").getValue();
		try {
			List<HashMap<String, Object>> list =new ArrayList<HashMap<String,Object>>();
			Map<String, Object> mapx = new LinkedHashMap<String, Object>();
			String condition="";
			condition="select b0101,b0111,b0183,b0185,decode(nvl(b0183,0),0,decode(nvl(b0185,0),0,'',b0185||'副'),b0183||'正'||decode(nvl(b0185,0),0,'',b0185||'副')) b0188,b01id,b0131"
					+"  from b01 where  (case when substr(b0111,1,11)='001.001.004' and b0131 in ('1001','1003','1004','1005','1006','1007') then substr(b0111,1,15)||'#'||b0131 else b0111 end) in "
					+"   (select (case when substr(b0111,1,11)='001.001.004' and a.fxyp06 in ('1001','1003','1004','1005','1006','1007') then b0111||'#'||a.fxyp06 else b0111 end)"
					+"	          from fxyp a,b01 b,hz_mntp_zwqc c,rxfxyp d "
					+"	         where a.b01id=b.b01id and a.zwqc00=c.zwqc00 and c.zwqc00=d.zwqc00 and c.mntp00 = '"+mntp00+"') and substr(b0111,1,11) in ('001.001.002','001.001.003','001.001.004')  order by b0269";

			List<HashMap<String, Object>> list_b01=queryB01(condition);
			if(list_b01!=null&&list_b01.size()>0){
				String b0111="";
				String condititon="";
				for(HashMap<String, Object> map_b01:list_b01){
					b0111=map_b01.get("b0111").toString();
					if("001.001.004".equals(b0111.substring(0,11))&&("1001".equals(map_b01.get("b0131"))||
							"1003".equals(map_b01.get("b0131"))||"1004".equals(map_b01.get("b0131"))||
							"1005".equals(map_b01.get("b0131"))||"1006".equals(map_b01.get("b0131"))||
							"1007".equals(map_b01.get("b0131")))){
						condititon="b01id=(select b01id from b01 where b0111='"+b0111.substring(0, 15)+"') and fxyp06='"+map_b01.get("b0131")+"'";
					}else{
						condititon="b01id='"+map_b01.get("b01id").toString()+"' ";
					}
					String sjzs=querySPZS(b0111,condititon,mntp00);
					String tpry=queryTPRY(b0111, condititon, mntp00);
					HashMap<String, Object> map=new HashMap<String, Object>();
					map.put("sjzs", sjzs);
					map.put("tpry", tpry);
					map.put("b0101", map_b01.get("b0101"));
					map.put("b0188", map_b01.get("b0188"));
					map.put("b0111", map_b01.get("b0111"));
					String b0111a=map_b01.get("b0111").toString();
					mapx.put(b0111a,map_b01.get("b0101"));
					list.add(map);
					}
				}
			((Combo)this.getPageElement("b0101")).setValueListForSelect(mapx); 
			if(list.size()>0) {
				JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(list);
				this.getExecuteSG().addExecuteCode("Add("+updateunDataStoreObject1+");");
			}
		}catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("queryB01")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryB01(String condition) throws RadowException {
		List<HashMap<String, Object>> list=null;
		try{
			CommQuery commQuery =new CommQuery();
			list = commQuery.getListBySQL(condition);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@PageEvent("querySPZS")
	@NoRequiredValidate
	public String querySPZS(String b0111,String condititon,String mntp00) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String str="";
//		String sql6 = "select count(*) ZZ from (SELECT A01.A0000 FROM a02, a01 "
//				+ "WHERE a01.A0000 = a02.a0000 AND a01.a0163='1' and a01.status='1' and a02.a0281 = 'true' AND a02.a0255 = '1' "
//				+ "and a02.a0201e='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b='"+b0111+"') tt ";
		String sql6 = "select cnt+nvl((select sum(t.zwqc01) from  (select zwqc00,zwqc01 from hz_mntp_gw where mntp00='"+mntp00+"' and a0201e='1' and "+condititon+" group by zwqc00,zwqc01) t),0) ZZ "
				+ "  from (select count(*) cnt from "
				+ " (SELECT A02.A0200 FROM a02, a01 "
				+ "WHERE a01.A0000 = a02.a0000 AND a01.a0163='1' and a01.status='1' and a02.a0281 = 'true' AND a02.a0255 = '1' "
				+ "and a02.a0201e='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b='"+b0111+"' ) tt) ";		
		Object o6 = sess.createSQLQuery(sql6).uniqueResult();
		int zz = Integer.valueOf(o6.toString());
//		String sql7 = "select count(*) FZ from (SELECT A01.A0000 FROM a02, a01 "
//				+ "WHERE a01.A0000 = a02.a0000 AND a01.a0163='1' and a01.status='1' and a02.a0281 = 'true' AND a02.a0255 = '1' "
//				+ "and a02.a0201e = '3' and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b='"+b0111+"') tt ";
		String sql7 = "select cnt+nvl((select sum(t.zwqc01) from  (select zwqc00,zwqc01 from hz_mntp_gw where mntp00='"+mntp00+"' and a0201e='3'  and "+condititon+" group by zwqc00,zwqc01) t),0) FZ "
				+ "  from (select count(*) cnt from "
				+ " (SELECT A01.A0000 FROM a02, a01 "
				+ "WHERE a01.A0000 = a02.a0000 AND a01.a0163='1' and a01.status='1' and a02.a0281 = 'true' AND a02.a0255 = '1' "
				+ "and a02.a0201e = '3' and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b='"+b0111+"') tt)";	
		Object o7 = sess.createSQLQuery(sql7).uniqueResult();
		int fz = Integer.valueOf(o7.toString());
		if(zz>0) {
			str=str+zz+"正";
		}
		if(fz>0) {
			str=str+fz+"副";
		}
		return str;
	}
	
	@PageEvent("queryTPRY")
	@NoRequiredValidate
	public String queryTPRY(String b0111,String condititon,String mntp00) throws RadowException {
		String str="";
		String str2="";
		int row=1;
		List<HashMap<String, Object>> list=null;
		//待定职务全称
		String sql_t= "select replace(wm_concat(a.zwqc00),',',''',''') zwqc00s from "
				+ " (select distinct zwqc00,zwqc01 from hz_mntp_gw where mntp00='"+mntp00+"' and "+condititon+") a,"
				+ " (select zwqc00,count(*) cnt from rxfxyp where zwqc00 in (select zwqc00 from hz_mntp_gw where mntp00='"+mntp00+"' and "+condititon+") group by zwqc00 ) b "
				+ " where a.zwqc00=b.zwqc00 and abs(a.zwqc01)<b.cnt";
		try{
			CommQuery commQuery =new CommQuery();
			list = commQuery.getListBySQL(sql_t);
			String cond1="";
			if(list!=null&&list.size()>0) {
				if(list.get(0).get("zwqc00s")==null||"".equals(list.get(0).get("zwqc00s"))) {
					cond1="###########";
				}else {
					cond1=list.get(0).get("zwqc00s").toString();
				}
				String sql_c="select a0000 from a02 where a0281='true' and a0201b='"+b0111+"'"
						+ " minus "
						+ "  (select a0000 from a02 where a0281='true' and a0201b='"+b0111+"' and a0200 not in ("
						+ "    select a0200 from hz_mntp_gw a where fxyp07=-1  and  a.zwqc00 not in ('"+cond1+"') and a.mntp00='"+mntp00+"' and "+condititon+")"
						+ "  union "
						+ "   select a0000 from v_mntp_gw_ry a where fxyp07=1  and  a.zwqc00 not in ('"+cond1+"') and a.mntp00='"+mntp00+"' and "+condititon+")";
				List<HashMap<String, Object>> list_c=commQuery.getListBySQL(sql_c);
				
				String sql_j="  select a0000 from hz_mntp_gw a,rxfxyp b where a.zwqc00=b.zwqc00 and a.mntp00='"+mntp00+"' and "+condititon+" and  a.zwqc00 not in ('"+cond1+"')"
						+ "	and a0000 not in (select a0000 from a02 where a0281='true' and a0201b='"+b0111+"' )";
				List<HashMap<String, Object>> list_j=commQuery.getListBySQL(sql_j);
				
				String sql_all="  select distinct a0000 from hz_mntp_gw a,rxfxyp b where a.zwqc00=b.zwqc00 and a.mntp00='"+mntp00+"' and "+condititon+" and  a.zwqc00 not in ('"+cond1+"')";
				List<HashMap<String, Object>> list_all=commQuery.getListBySQL(sql_all);
				
				//交流进
				int num_j=0;
				if(list_j!=null && list_j.size()>0) {
					num_j=list_j.size();
					str=str+"外部交流进"+num_j+"名，";
				}
				//交流出
				int num_c=0;
				if(list_c!=null && list_c.size()>0) {
					num_c=list_c.size();
					str=str+"向外交流"+num_c+"名，";
				}
				//内部转任
				int num_n=list_all.size()-num_c-num_j;
				if(num_n>0) {
					str=str+"内部转任"+num_n+"名，";
				}
				if(str.length()>0) {
					str=str.substring(0, str.length()-1);
				}
				
				//待定
				String sql_dd_qc="  select distinct zwqc00 from hz_mntp_gw a where a.zwqc00 in ('"+cond1+"') and a.mntp00='"+mntp00+"' and "+condititon;
				List<HashMap<String, Object>> list_dd_qc=commQuery.getListBySQL(sql_dd_qc);
				if(list_dd_qc!=null && list_dd_qc.size()>0) {
					String sql_dd_ry="  select distinct a0000 from hz_mntp_gw a,rxfxyp b where a.zwqc00=b.zwqc00  and  a.zwqc00 in ('"+cond1+"') and a.mntp00='"+mntp00+"' and "+condititon;
					List<HashMap<String, Object>> list_dd_ry=commQuery.getListBySQL(sql_dd_ry);
					str=str+"（待定"+list_dd_ry.size()+"选"+list_dd_qc.size()+"）";
				}
				
				//拟任人员
				if(list_all.size()+-num_c>0||(list_dd_qc!=null && list_dd_qc.size()>0)) {
					String sql_nr="select * from (select distinct d.a0192a||'<span style=''font-weight:bold;''>'||d.a0101||'</span>'||'，建议转任'||a.a0192a||case when a.zwqc00 in ('"+cond1+"') then '（待定）' else '' end||';' a01zs,b.sortnum "
							+ "  from hz_mntp_zwqc a,(select b.b01id,b.zwqc00,b.fxyp06,e.sortnum sortnum from fxyp b,gwsort e where b.fxyp00= e.sortid(+) ) b"
							+ " ,rxfxyp c, a01 d where a.zwqc00=b.zwqc00 and a.zwqc00=c.zwqc00 and c.a0000=d.a0000 "
							+ "   and a.fxyp07=1 and a.mntp00='"+mntp00+"' and "+condititon+") order by sortnum";
					List<HashMap<String, Object>> list_nr=commQuery.getListBySQL(sql_nr);
					for(int i=0;i<list_nr.size();i++) {
						str2=str2+row+"、"+list_nr.get(i).get("a01zs")+"<br>";
						row++;
					}
				}
				
				if(list_c!=null && list_c.size()>0) {
					//交流出人员具体信息
					String sql_c_1="select a0192a||'<span style=''font-weight:bold;''>'||a0101||'</span>'||'向外交流;' a01zs from a01 where a0000 in "
							+ "(select a0000 from a02 where a0281='true' and a0201b='"+b0111+"' "
							+ " minus "
							+ "  (select a0000 from a02 where a0281='true' and a0201b='"+b0111+"' and a0200 not in ("
							+ "    select a0200 from hz_mntp_gw a where fxyp07=-1  and  a.zwqc00 not in ('"+cond1+"') and a.mntp00='"+mntp00+"' and "+condititon+")"
							+ "  union "
							+ "   select a0000 from v_mntp_gw_ry a where fxyp07=1  and  a.zwqc00 not in ('"+cond1+"') and a.mntp00='"+mntp00+"' and "+condititon+")) "
							+ "	order by (select lpad(max(a0225), 25, '0') from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a02.A0201B = '"+b0111+"')";
					List<HashMap<String, Object>> list_c_1=commQuery.getListBySQL(sql_c_1);
					for(int i=0;i<list_c_1.size();i++) {
						str2=str2+row+"、"+list_c_1.get(i).get("a01zs")+"<br>";
						row++;
					}
				}
				
			}else {
				String sql_c="select a0000 from a02 where a0281='true' and a0201b='"+b0111+"' "
						+ " minus "
						+ "  (select a0000 from a02 where a0281='true' and a0201b='"+b0111+"' and a0200 not in ("
						+ "    select a0200 from hz_mntp_gw a where fxyp07=-1 and a.mntp00='"+mntp00+"' and "+condititon+" )"
						+ "  union "
						+ "   select a0000 from v_mntp_gw_ry a where fxyp07=1  and  a.zwqc00 not in ('"+cond1+"') and a.mntp00='"+mntp00+"' and "+condititon+")";
				List<HashMap<String, Object>> list_c=commQuery.getListBySQL(sql_c);
				
				String sql_j="  select a0000 from hz_mntp_gw a,rxfxyp b where a.zwqc00=b.zwqc00 and a.mntp00='"+mntp00+"' and "+condititon
						+ "	and a0000 not in (select a0000 from a02 where a0281='true' and a0201b='"+b0111+"' )";
				List<HashMap<String, Object>> list_j=commQuery.getListBySQL(sql_j);
				
				String sql_all="  select distinct a0000 from hz_mntp_gw a,rxfxyp b where a.zwqc00=b.zwqc00 and a.mntp00='"+mntp00+"' and "+condititon;
				List<HashMap<String, Object>> list_all=commQuery.getListBySQL(sql_all);
				

				int num_j=0;
				if(list_j!=null && list_j.size()>0) {
					num_j=list_j.size();
					str=str+"外部交流进"+num_j+"名，";
				}
				int num_c=0;
				if(list_c!=null && list_c.size()>0) {
					num_c=list_c.size();
					str=str+"向外交流"+num_c+"名，";
				}
				int num_n=list_all.size()-num_c-num_j;
				if(num_n>0) {
					str=str+"内部转任"+num_n+"名，";
				}
				if(str.length()>0) {
					str=str.substring(0, str.length()-1);
				}
				
				//拟任人员
				if(list_all.size()-num_c>0) {
					String sql_nr="select * from (select distinct d.a0192a||'<span style=''font-weight:bold;''>'||d.a0101||'</span>'||'，建议转任'||a.a0192a||case when a.zwqc00 in ('"+cond1+"') then '（待定）' else '' end||';' a01zs,b.sortnum "
							+ "  from hz_mntp_zwqc a,(select b.b01id,b.zwqc00,b.fxyp06,e.sortnum sortnum from fxyp b,gwsort e where b.fxyp00= e.sortid(+) ) b"
							+ " ,rxfxyp c, a01 d where a.zwqc00=b.zwqc00 and a.zwqc00=c.zwqc00 and c.a0000=d.a0000 "
							+ "   and a.fxyp07=1 and a.mntp00='"+mntp00+"' and "+condititon+") order by sortnum";
					List<HashMap<String, Object>> list_nr=commQuery.getListBySQL(sql_nr);
					for(int i=0;i<list_nr.size();i++) {
						str2=str2+row+"、"+list_nr.get(i).get("a01zs")+"<br>";
						row++;
					}
				}
				
				if(list_c!=null && list_c.size()>0) {
					//交流出人员具体信息
					String sql_c_1="select a0192a||'<span style=''font-weight:bold;''>'||a0101||'</span>'||'向外交流;' a01zs from a01 where a0000 in "
							+ "(select a0000 from a02 where a0281='true' and a0201b='"+b0111+"' "
							+ " minus "
							+ "  (select a0000 from a02 where a0281='true' and a0201b='"+b0111+"' and a0200 not in ("
							+ "    select a0200 from hz_mntp_gw a where fxyp07=-1   and a.mntp00='"+mntp00+"' and "+condititon+")"
							+ "  union "
							+ "   select a0000 from v_mntp_gw_ry a where fxyp07=1  and  a.zwqc00 not in ('"+cond1+"') and a.mntp00='"+mntp00+"' and "+condititon+")) "
							+ "	order by (select lpad(max(a0225), 25, '0') from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a02.A0201B = '"+b0111+"')";
				
					List<HashMap<String, Object>> list_c_1=commQuery.getListBySQL(sql_c_1);
					for(int i=0;i<list_c_1.size();i++) {
						str2=str2+row+"、"+list_c_1.get(i).get("a01zs")+"<br>";
						row++;
					}
				}
			}
			if(str2.length()>4) {
				str2=str2.substring(0,str2.length()-4);
				str=str+"<br>"+str2;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	
	
	@PageEvent("query")
	public int query() throws RadowException, AppException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		String b0101= this.getPageElement("b0101").getValue();
		String[] b0111s=b0101.split(",");
		String addsql="";	
	try {
		if(b0101.length()>0) {
			List<HashMap<String, Object>> list =new ArrayList<HashMap<String,Object>>();
			for(int i=0;i<b0111s.length;i++) {
				String condition="";
				condition="select b0101,b0111,b0183,b0185,decode(nvl(b0183,0),0,decode(nvl(b0185,0),0,'',b0185||'副'),b0183||'正'||decode(nvl(b0185,0),0,'',b0185||'副')) b0188,b01id,b0131"
						+"  from b01 where  (case when substr(b0111,1,11)='001.001.004' and b0131 in ('1001','1003','1004','1005','1006','1007') then substr(b0111,1,15)||'#'||b0131 else b0111 end) in "
						+"   (select (case when substr(b0111,1,11)='001.001.004' and a.fxyp06 in ('1001','1003','1004','1005','1006','1007') then b0111||'#'||a.fxyp06 else b0111 end)"
						+"	          from fxyp a,b01 b,hz_mntp_zwqc c,rxfxyp d "
						+"	         where a.b01id=b.b01id and a.zwqc00=c.zwqc00 and c.zwqc00=d.zwqc00 and c.mntp00 = '"+mntp00+"') and substr(b0111,1,11) in ('001.001.002','001.001.003','001.001.004') and b0111='"+b0111s[i]+"'  order by b0269";
				List<HashMap<String, Object>> list_b01=queryB01(condition);
				if(list_b01!=null&&list_b01.size()>0){
					String b0111="";
					String condititon="";
					for(HashMap<String, Object> map_b01:list_b01){
						b0111=map_b01.get("b0111").toString();
						if("001.001.004".equals(b0111.substring(0,11))&&("1001".equals(map_b01.get("b0131"))||
								"1003".equals(map_b01.get("b0131"))||"1004".equals(map_b01.get("b0131"))||
								"1005".equals(map_b01.get("b0131"))||"1006".equals(map_b01.get("b0131"))||
								"1007".equals(map_b01.get("b0131")))){
							condititon="b01id=(select b01id from b01 where b0111='"+b0111.substring(0, 15)+"') and fxyp06='"+map_b01.get("b0131")+"'";
						}else{
							condititon="b01id='"+map_b01.get("b01id").toString()+"' ";
						}
						String sjzs=querySPZS(b0111,condititon,mntp00);
						String tpry=queryTPRY(b0111, condititon, mntp00);
						HashMap<String, Object> map=new HashMap<String, Object>();
						map.put("sjzs", sjzs);
						map.put("tpry", tpry);
						map.put("b0101", map_b01.get("b0101"));
						map.put("b0188", map_b01.get("b0188"));
						map.put("b0111", map_b01.get("b0111"));
						list.add(map);
						}
					}
			}
			if(list.size()>0) {
				JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(list);
				this.getExecuteSG().addExecuteCode("Add("+updateunDataStoreObject1+");");
			}
		}else {
			this.setNextEventName("initX");
		}
		}catch (Exception e) {
		this.setMainMessage("查询失败！");
		e.printStackTrace();
		}	
	return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	public String isnull(Object obj) {
		String str="";
		if(obj==null||"".equals(obj)) {
			
		}else {
			str=obj.toString();
		}
		return str;
	}
	
	
}
