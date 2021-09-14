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

public class BZRYBDPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("init0");
//		this.setExecuteSG(executeSG);
//		this.setNextEventName("pgrid.dogridquery");
//		this.setNextEventName("pgrid2.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("init0")
	public int init0() throws RadowException, AppException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		try {
			@SuppressWarnings("unchecked")
			List<String> tpdw= HBUtil.getHBSession().createSQLQuery("select b0111 from( select distinct  b0111,c.b0269 from b01 c,fxyp " + 
					"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null and b0111 <> '001.002' " + 
					"order by c.b0269)").list();
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			if(tpdw.size()>0) {
				for(int i=0;i<tpdw.size();i++) {
					String b0111=tpdw.get(i);
					@SuppressWarnings("unchecked")
					List<String> b0101s= HBUtil.getHBSession().createSQLQuery("select b0101 from b01 where b0111='"+tpdw.get(i)+"'").list();
					String b0101="";
					if(b0101s.size()>0) {
						b0101=b0101s.get(0);
					}
					if(b0111.length()==15 && "001.001.004".equals(b0111.substring(0,11))) {
						String zrrx1sql="select distinct zrrx" + 
								"  from (select *" + 
								"          from (SELECT (select b0131 from b01 where b0111 = a02.a0201b) zrrx" + 
								"                  FROM a02," + 
								"                       a01," + 
								"                       (select *" + 
								"                          from HZ_MNTP_BZ" + 
								"                         where mntp00 = '"+mntp00+"'" + 
								"                           and a01bztype = '1') b" + 
								"                 WHERE a01.A0000 = a02.a0000" + 
								"                   AND a02.a0281 = 'true'" + 
								"                   AND a02.a0255 = '1'" + 
								"                   and b.a01bzid(+) = a02.a0200" + 
								"                   and a01.a0163='1'" + 
								"                   and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
								"                   and a02.a0201b in" + 
								"                       (select b0111" + 
								"                          from b01 b" + 
								"                         where b.b0131 in" + 
								"                               ('1001', '1003', '1004', '1005', '1006', '1007')" + 
								"                           and b.b0111 like '"+b0111+".%')" +
								"                 order by zrrx," + 
								"                          ((select rpad(b0269, 25, '.') ||" + 
								"                                   lpad(a0225, 25, '0')" + 
								"                              from (select a02.a0000," + 
								"                                           b0269," + 
								"                                           a0225," + 
								"                                           row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
								"                                      from a02, b01" + 
								"                                     where a02.a0201b = b01.b0111" + 
								"                                       and a0281 = 'true'" + 
								"                                       and a0201b like '"+b0111+"%') t" + 
								"                             where rn = 1" + 
								"                               and t.a0000 = a01.a0000))) a01" + 
								"        union all" + 
								"        select *" + 
								"          from (select  decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx" + 
								"      " + 
								"                  from v_mntp_gw_ry t," + 
								"                       a01," + 
								"                       (select *" + 
								"                          from HZ_MNTP_BZ" + 
								"                         where mntp00 = '"+mntp00+"'" + 
								"                           and a01bztype = '2') b" + 
								"                 where t.a0000 = a01.a0000" + 
								"                   and t.fxyp07 = 1" + 
								"                   and t.mntp00 = '"+mntp00+"'" + 
								"                   and (select b0111 from b01 where t.b01id = b01.b01id) =" + 
								"                       '"+b0111+"'" + 
								"                   and b.a01bzid(+) = t.tp0100" + 
								"                 order by t.fxyp00, t.sortnum))" + 
								" order by zrrx ";
						List<String> zrrxList=HBUtil.getHBSession().createSQLQuery(zrrx1sql).list();
						if(zrrxList.size()>0) {
							for(int j=0;j<zrrxList.size();j++) {
								String zrrx=zrrxList.get(j);
								String zrrxname="";
								if("1001".equals(zrrx)) {
									zrrxname="党委";
								}else if("1004".equals(zrrx)) {
									zrrxname="政府";
								}else if("1003".equals(zrrx)) {
									zrrxname="人大";
								}else if("1005".equals(zrrx)) {
									zrrxname="政协";
								}else if("1006".equals(zrrx)) {
									zrrxname="法院";
								}else if("1007".equals(zrrx)) {
									zrrxname="检察院";
								}else if("Z01".equals(zrrx)) {
									zrrxname="其他";
								}
								if(zrrx!=null) {
									map.put(b0111+"@"+zrrx, b0101+zrrxname);
								}
								
							}
						}
					}else {
						map.put(b0111, b0101);
					}
					
				}
			}
			//新增市直
			@SuppressWarnings("unchecked")
			List<String> tpdwadd= HBUtil.getHBSession().createSQLQuery("select distinct c.b01id from  HZ_MNTP_ORG_QT c,fxyp " + 
					"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null " ).list();
			if(tpdwadd.size()>0) {
				for(int i=0;i<tpdwadd.size();i++) {
					@SuppressWarnings("unchecked")
					List<String> addname= HBUtil.getHBSession().createSQLQuery("select jgmc from HZ_MNTP_ORG_QT where b01id='"+tpdwadd.get(i)+"'").list();
					map.put(tpdwadd.get(i)+"#1", addname.get(0));
				}
			}
			
			//新增区县
			@SuppressWarnings("unchecked")
			List<String> tpqxadd= HBUtil.getHBSession().createSQLQuery("select distinct c.b01id from  HZ_MNTP_ORG_QXS c,fxyp " + 
					"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null " ).list();
			if(tpqxadd.size()>0) {
				for(int i=0;i<tpqxadd.size();i++) {
					String b01id=tpqxadd.get(i);
					@SuppressWarnings("unchecked")
					List<String> addname1= HBUtil.getHBSession().createSQLQuery("select b0101 from HZ_MNTP_ORG_QXS where b01id='"+tpqxadd.get(i)+"'").list();
					String zrrxsql="       select distinct zrrx from ( select *" + 
							"          from (select   decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx" + 
							"                  from v_mntp_gw_ry t," + 
							"                       a01," + 
							"                       (select *" + 
							"                          from HZ_MNTP_BZ" + 
							"                         where mntp00 = '"+mntp00+"'" + 
							"                           and a01bztype = '2') b" + 
							"                 where t.a0000 = a01.a0000" + 
							"                   and t.fxyp07 = 1" + 
							"                   and t.mntp00 = '"+mntp00+"'" + 
							"                   and t.b01id =" + 
							"                       '"+b01id+"'" + 
							"                   and b.a01bzid(+) = t.tp0100" + 
							"                 order by t.fxyp00, t.sortnum)) order by zrrx" ;
					@SuppressWarnings("unchecked")
					List<String> zrrxlist= HBUtil.getHBSession().createSQLQuery(zrrxsql).list();
					if(zrrxlist.size()>0) {
						for(int j=0;j<zrrxlist.size();j++) {
							String zrrx=zrrxlist.get(j);
							String zrrxname="";
							if("1001".equals(zrrx)) {
								zrrxname="党委";
							}else if("1004".equals(zrrx)) {
								zrrxname="政府";
							}else if("1003".equals(zrrx)) {
								zrrxname="人大";
							}else if("1005".equals(zrrx)) {
								zrrxname="政协";
							}else if("1006".equals(zrrx)) {
								zrrxname="法院";
							}else if("1007".equals(zrrx)) {
								zrrxname="检察院";
							}else if("Z01".equals(zrrx)) {
								zrrxname="其他";
							}
							map.put(b01id+"#2#"+zrrx, addname1.get(0)+zrrxname);
						}
					}
				}
			}
			((Combo)this.getPageElement("b0101")).setValueListForSelect(map); 
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	
	}
	
	
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		String mntp00 = this.getPageElement("mntp00").getValue();
//		String mntp00="0915a081-d034-4ddc-9a53-cea49b79e43c";
		String checked=this.getPageElement("showAll").getValue();
		String addsql="";
		try {
			if("1".equals(checked)) {
				addsql=" and (select   gw.fxyp07  from hz_mntp_gw gw  where gw.a0200 = a02.a0200"
						+ "  and gw.mntp00 = '"+mntp00+"') is not null ";
			}
			//发生调配的市直单位、国企高校、区县平台
			@SuppressWarnings("unchecked")
			List<String> tpsz= HBUtil.getHBSession().createSQLQuery("select b0111 from( select distinct  b0111,c.b0269 from b01 c,fxyp " + 
					"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null and b0111 <> '001.002'  " + 
					"and (substr(b0111,0,11)<>  '001.001.004' or length(b0111)<>15) " + 
					"order by c.b0269)").list();
			
			if(tpsz.size()>0) {
				for(int i=0;i<tpsz.size();i++) {
					String b0111=tpsz.get(i);
					String sql="select * from" + 
							"       (select  (select count(*) from a01,a02   where a01.A0000 = a02.a0000" + 
							"           AND a02.a0281 = 'true'" + 
							"           AND a02.a0255 = '1'" + 
							"           and a01.a0163='1'" + 
							"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
							"           and a02.a0201b = '"+b0111+"') rs," + 
							"   (select b0101 from b01 where b01.b0111=a02.a0201b) b0101,"+
							"                a02.a0201e zrrx," + 
							"               (select gw.fxyp07" + 
							"                  from hz_mntp_gw gw" + 
							"                 where gw.a0200 = a02.a0200" + 
							"                   and gw.mntp00 = '"+mntp00+"'" + 
							"                   and fxyp07 = -1) fxyp07," + 
							"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
							"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
							"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
							"               a01.a0192a," + 
							"               (select code_name" + 
							"                  from code_value" + 
							"                 where code_value.code_value = a01.a0141" + 
							"                   and code_type = 'GB4762') a0141," + 
							"               (select bz.a01bzdesc" + 
							"                  from HZ_MNTP_BZ bz" + 
							"                 where bz.a01bzid = a02.a0200" + 
							"                   and bz.mntp00 = '"+mntp00+"'" + 
							"                   and a01bztype = '1') a01bzdesc" + 
							"          from a01, a02" + 
							"         where a01.A0000 = a02.a0000" + 
							"           AND a02.a0281 = 'true'" + 
							"           AND a02.a0255 = '1'" + 
							"           and a01.a0163='1'" + 
							"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
							"           and a02.a0201b = '"+b0111+"'" + addsql+
							"         order by zrrx," + 
							"                  ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
							"                      from (select a02.a0000," + 
							"                                   b0269," + 
							"                                   a0225," + 
							"                                   row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
							"                              from a02, b01" + 
							"                             where a02.a0201b = b01.b0111" + 
							"                               and a0281 = 'true'" + 
							"                               and a0201b like '"+b0111+"%') t" + 
							"                     where rn = 1" + 
							"                       and t.a0000 = a01.a0000))" + 
							"        ) a01";
					CommQuery cqbs=new CommQuery();
					List<HashMap<String, Object>> listCode1=cqbs.getListBySQL(sql.toString());
					JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);
					
					sql="select *" + 
							"  from (select a02.a0201e zrrx," + 
							"               (select gw.fxyp07" + 
							"                  from hz_mntp_gw gw" + 
							"                 where gw.a0200 = a02.a0200" + 
							"                   and gw.mntp00 = '"+mntp00+"'" + 
							"                   and fxyp07 = -1) fxyp07," + 
							"   (select b0101 from b01 where b01.b0111=a02.a0201b) b0101,"+
							"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
							"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
							"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
							"               a02.a0215a," + 
							"               a01.a0192a," + 
							"               (select code_name" + 
							"                  from code_value" + 
							"                 where code_value.code_value = a01.a0141" + 
							"                   and code_type = 'GB4762') a0141," + 
							"               (select bz.a01bzdesc" + 
							"                  from HZ_MNTP_BZ bz" + 
							"                 where bz.a01bzid = a02.a0200" + 
							"                   and bz.mntp00 = '"+mntp00+"'" + 
							"                   and a01bztype = '1') a01bzdesc" + 
							"          from a01, a02" + 
							"         where a01.A0000 = a02.a0000" + 
							"           AND a02.a0281 = 'true'" + 
							"           AND a02.a0255 = '1'" + 
							"           and a01.a0163='1'" + 
							"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
							"           and a02.a0201b = '"+b0111+"'" + 
							"           and not exists" + 
							"         (select gw.fxyp07" + 
							"                  from hz_mntp_gw gw" + 
							"                 where gw.a0200 = a02.a0200" + 
							"                   and gw.mntp00 = '"+mntp00+"'" + 
							"                   and fxyp07 = -1)" + addsql+
							"         order by zrrx,((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
							"                      from (select a02.a0000," + 
							"                                   b0269," + 
							"                                   a0225," + 
							"                                   row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
							"                              from a02, b01" + 
							"                             where a02.a0201b = b01.b0111" + 
							"                               and a0281 = 'true'" + 
							"                               and a0201b like '"+b0111+"%') t" + 
							"                     where rn = 1" + 
							"                       and t.a0000 = a01.a0000))) a01" + 
							"/*UNION 结果集中的列名总是等于 UNION 中第一个 SELECT 语句中的列名。*/" + 
							"union all  " + 
							"select *" + 
							"  from (select decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx," + 
							"               t.fxyp07 fxyp07," + 
							"             (select b0101 from b01 where b0111 = '"+b0111+"') b0101," + 
							"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
							"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
							"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
							"              t.fxyp02 a0215a," + 
							"               a01.a0192a," + 
							"               (select code_name" + 
							"                  from code_value" + 
							"                 where code_value.code_value = a01.a0141" + 
							"                   and code_type = 'GB4762') a0141," + 
							"                b.a01bzdesc" + 
							"          from v_mntp_gw_ry t, a01, (select *" + 
							"                          from HZ_MNTP_BZ" + 
							"                         where mntp00 = '"+mntp00+"'" + 
							"                           and a01bztype = '2') b" + 
							"         where t.a0000 = a01.a0000" + 
							"           and t.fxyp07 = 1" + 
							"           and t.mntp00 = '"+mntp00+"'" + 
							"           and (select b0111 from b01 where t.b01id=b01.b01id) ='"+b0111+"'" + 
							"             and b.a01bzid(+) = t.tp0100" + 
							"         order by t.fxyp00, t.sortnum)";
					CommQuery cqbs1=new CommQuery();
					String c=sql.toString();
					List<HashMap<String, Object>> listCode2=cqbs1.getListBySQL(sql.toString());
					JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(listCode2);
					if(listCode1.size()>0 || listCode2.size()>0) {
						if(listCode1.size()>=listCode2.size()) {
							String length=String.valueOf(listCode1.size());
							this.getExecuteSG().addExecuteCode("AddSz1("+updateunDataStoreObject1.toString()+","+updateunDataStoreObject2.toString()+","+length+");");
						}else {
							String length=String.valueOf(listCode2.size());
							this.getExecuteSG().addExecuteCode("AddSz2("+updateunDataStoreObject2.toString()+","+updateunDataStoreObject1.toString()+","+length.toString()+");");
						}
					}
					
				}
			}
			
			//新增市直
			String sql1="select distinct c.b01id from  HZ_MNTP_ORG_QT c,fxyp " + 
					"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null ";
			@SuppressWarnings("unchecked")
			List<String> addsz= HBUtil.getHBSession().createSQLQuery(sql1).list();
			if(addsz.size()>0) {
				for(int j=0;j<addsz.size();j++) {
					String b01id=addsz.get(j);
					String sql="select *" + 
							"  from (select decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx," + 
							"               t.fxyp07 fxyp07," + 
							"             (select jgmc from HZ_MNTP_ORG_QT where b01id = '"+b01id+"') b0101," + 
							"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
							"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
							"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
							"              t.fxyp02 a0215a," + 
							"               a01.a0192a," + 
							"               (select code_name" + 
							"                  from code_value" + 
							"                 where code_value.code_value = a01.a0141" + 
							"                   and code_type = 'GB4762') a0141," + 
							"                b.a01bzdesc" + 
							"          from v_mntp_gw_ry t, a01, (select *" + 
							"                          from HZ_MNTP_BZ" + 
							"                         where mntp00 = '"+mntp00+"'" + 
							"                           and a01bztype = '2') b" + 
							"         where t.a0000 = a01.a0000" + 
							"           and t.fxyp07 = 1" + 
							"           and t.mntp00 = '"+mntp00+"'" + 
							"           and t.b01id ='"+b01id+"'" + 
							"             and b.a01bzid(+) = t.tp0100" + 
							"         order by t.fxyp00, t.sortnum)";
					CommQuery cqbs=new CommQuery();
					List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
					JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
					
					List<HashMap<String, Object>> listCode1=cqbs.getListBySQL("select * from v_mntp_gw_ry t where 1<>1");
					JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);
					
					String length=String.valueOf(listCode.size());
					this.getExecuteSG().addExecuteCode("AddSz2("+updateunDataStoreObject.toString()+","+updateunDataStoreObject1.toString()+","+length+");");
				}
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		this.setNextEventName("init1");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("init1")
	public int init1() throws RadowException, AppException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		String checked=this.getPageElement("showAll").getValue();
		String addsql="";
		try {
			if("1".equals(checked)) {
				addsql=" and (select   gw.fxyp07  from hz_mntp_gw gw  where gw.a0200 = a02.a0200"
						+ "  and gw.mntp00 = '"+mntp00+"') is not null ";
			}
			//发生调配的区县市
			@SuppressWarnings("unchecked")
			List<String> tpqx= HBUtil.getHBSession().createSQLQuery("select b0111 from( select distinct  b0111,c.b0269 from b01 c,fxyp " + 
					" where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null " + 
					" and b0111 like '001.001.004%' and length(b0111)=15 " + 
					" order by c.b0269)").list();
			
			if(tpqx.size()>0) {
				for(int i=0;i<tpqx.size();i++) {
					String b0111=tpqx.get(i);
					String zrrx1sql="select distinct zrrx" + 
							"  from (select *" + 
							"          from (SELECT (select b0131 from b01 where b0111 = a02.a0201b) zrrx" + 
							"                  FROM a02," + 
							"                       a01," + 
							"                       (select *" + 
							"                          from HZ_MNTP_BZ" + 
							"                         where mntp00 = '"+mntp00+"'" + 
							"                           and a01bztype = '1') b" + 
							"                 WHERE a01.A0000 = a02.a0000" + 
							"                   AND a02.a0281 = 'true'" + 
							"                   AND a02.a0255 = '1'" + 
							"                   and b.a01bzid(+) = a02.a0200" + 
							"                   and a01.a0163='1'" + 
							"                   and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
							"                   and a02.a0201b in" + 
							"                       (select b0111" + 
							"                          from b01 b" + 
							"                         where b.b0131 in" + 
							"                               ('1001', '1003', '1004', '1005', '1006', '1007')" + 
							"                           and b.b0111 like '"+b0111+".%')" + addsql+
							"                 order by zrrx," + 
							"                          ((select rpad(b0269, 25, '.') ||" + 
							"                                   lpad(a0225, 25, '0')" + 
							"                              from (select a02.a0000," + 
							"                                           b0269," + 
							"                                           a0225," + 
							"                                           row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
							"                                      from a02, b01" + 
							"                                     where a02.a0201b = b01.b0111" + 
							"                                       and a0281 = 'true'" + 
							"                                       and a0201b like '"+b0111+"%') t" + 
							"                             where rn = 1" + 
							"                               and t.a0000 = a01.a0000))) a01" + 
							"        union all" + 
							"        select *" + 
							"          from (select  decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx" + 
							"      " + 
							"                  from v_mntp_gw_ry t," + 
							"                       a01," + 
							"                       (select *" + 
							"                          from HZ_MNTP_BZ" + 
							"                         where mntp00 = '"+mntp00+"'" + 
							"                           and a01bztype = '2') b" + 
							"                 where t.a0000 = a01.a0000" + 
							"                   and t.fxyp07 = 1" + 
							"                   and t.mntp00 = '"+mntp00+"'" + 
							"                   and (select b0111 from b01 where t.b01id = b01.b01id) =" + 
							"                       '"+b0111+"'" + 
							"                   and b.a01bzid(+) = t.tp0100" + 
							"                 order by t.fxyp00, t.sortnum))" + 
							" order by zrrx ";
					@SuppressWarnings("unchecked")
					List<String> zrrxList=HBUtil.getHBSession().createSQLQuery(zrrx1sql).list();

//					if(zrrx1.size()>=zrrx2.size()) {
//						zrrxList=zrrx1;
//					}else {
//						zrrxList=zrrx2;
//					}
					int lengthQX=0;
					//循环遍历机构性质
					if(zrrxList.size()>0) {
						for(int j=0;j<zrrxList.size();j++) {
							String zrrx=zrrxList.get(j);
							String sql="SELECT decode((select b0131 from b01 where b0111 = a02.a0201b),'1001','党委','1004','政府','1003','人大'," + 
									"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
									"       (select gw.fxyp07" + 
									"          from hz_mntp_gw gw" + 
									"         where gw.a0200 = a02.a0200" + 
									"           and gw.mntp00 = '"+mntp00+"'" + 
									"           and fxyp07 = -1) fxyp07," + 
									"       GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
									"       decode(a01.a0104, 1, '男', 2, '女') a0104," + 
									"       substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
									"       a01.a0192a," + 
									"       (select code_name" + 
									"          from code_value" + 
									"         where code_value.code_value = a01.a0141" + 
									"           and code_type = 'GB4762') a0141," + 
									"       (select bz.a01bzdesc" + 
									"          from HZ_MNTP_BZ bz" + 
									"         where bz.a01bzid = a02.a0200" + 
									"           and bz.mntp00 = '"+mntp00+"'" + 
									"           and a01bztype = '1') a01bzdesc" + 
									"  FROM a02, a01" + 
									" WHERE a01.A0000 = a02.a0000" + 
									"   AND a02.a0281 = 'true'" + 
									"   AND a02.a0255 = '1'" + 
									"   and a01.a0163='1'" + 
									"   and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
									"   and (select b0131 from b01 where b0111 = a02.a0201b)='"+zrrx+"'" + 
									"   and a02.a0201b in" + 
									"       (select b0111" + 
									"          from b01 b" + 
									"         where b.b0131 in ('1001', '1003', '1004', '1005', '1006', '1007')" + 
									"           and  b.b0111 like '"+b0111+"%')" + addsql+
									" order by " + 
									"          ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
									"              from (select a02.a0000," + 
									"                           b0269," + 
									"                           a0225," + 
									"                           row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
									"                      from a02, b01" + 
									"                     where a02.a0201b = b01.b0111" + 
									"                       and a0281 = 'true'" + 
									"                       and b01.b0111 like '"+b0111+"%') t" + 
									"             where rn = 1" + 
									"               and t.a0000 = a01.a0000))";
							CommQuery cqbs1=new CommQuery();
							List<HashMap<String, Object>> listCode1=cqbs1.getListBySQL(sql.toString());
							
							
							sql="select *" + 
									"  from (SELECT  decode((select b0131 from b01 where b0111 = a02.a0201b),'1001','党委','1004','政府','1003','人大'," + 
									"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
									"               (select gw.fxyp07" + 
									"                  from hz_mntp_gw gw" + 
									"                 where gw.a0200 = a02.a0200" + 
									"                   and gw.mntp00 = '"+mntp00+"'" + 
									"                   and fxyp07 = -1) fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
									"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
									"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
									"               a02.a0215a," + 
									"               a01.a0192a," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               b.a01bzdesc" + 
									"          FROM a02," + 
									"               a01," + 
									"               (select *" + 
									"                  from HZ_MNTP_BZ" + 
									"                 where mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '1') b" + 
									"         WHERE a01.A0000 = a02.a0000" + 
									"           AND a02.a0281 = 'true'" + 
									"           AND a02.a0255 = '1'" + 
									"           and b.a01bzid(+) = a02.a0200" + 
									"           and a01.a0163='1'" + 
									"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
									"           and a02.a0201b in" + 
									"               (select b0111" + 
									"                  from b01 b" + 
									"                 where b.b0131 in" + 
									"                       ('1001', '1003', '1004', '1005', '1006', '1007')" + 
									"                   and b.b0111 like '"+b0111+".%')" + 
									"           and not exists" + 
									"         (select gw.fxyp07" + 
									"                  from hz_mntp_gw gw" + 
									"                 where gw.a0200 = a02.a0200" + 
									"                   and gw.mntp00 = '"+mntp00+"'" + 
									"                   and fxyp07 = -1)" + 
									"                   and (select b0131 from b01 where b0111 = a02.a0201b)='"+zrrx+"'" + addsql+
									"         order by zrrx," + 
									"                  ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
									"                      from (select a02.a0000," + 
									"                                   b0269," + 
									"                                   a0225," + 
									"                                   row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
									"                              from a02, b01" + 
									"                             where a02.a0201b = b01.b0111" + 
									"                               and a0281 = 'true'" + 
									"                               and a0201b like '"+b0111+"%') t" + 
									"                     where rn = 1" + 
									"                       and t.a0000 = a01.a0000))) a01 " + 
									" union all" + 
									" select *" + 
									"  from (select decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e),'1001','党委','1004','政府','1003','人大'," + 
									"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
									"               " + 
									"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
									"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
									"               t.fxyp02 a0215a," + 
									"               a01.a0192a a0192a," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               " + 
									"               b.a01bzdesc" + 
									"          from v_mntp_gw_ry t," + 
									"               a01," + 
									"               (select *" + 
									"                  from HZ_MNTP_BZ" + 
									"                 where mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '2') b" + 
									"         where t.a0000 = a01.a0000" + 
									"           and t.fxyp07 = 1" + 
									"           and t.mntp00 = '"+mntp00+"'" + 
									"           and  (select b0111 from b01 where t.b01id=b01.b01id) ='"+b0111+"'" + 
									"           and b.a01bzid(+) = t.tp0100" + 
									"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)='"+zrrx+"' " + 
									"         order by t.fxyp00, t.sortnum)";
							CommQuery cqbs2=new CommQuery();
							List<HashMap<String, Object>> listCode2=cqbs2.getListBySQL(sql.toString());
							if(listCode1.size()>=listCode2.size()) {
								lengthQX+=listCode1.size();
							}else {
								lengthQX+=listCode2.size();
							}
						}
					}
					
					if(zrrxList.size()>0) {
						for(int j=0;j<zrrxList.size();j++) {
							String zrrx=zrrxList.get(j);
							String sql="SELECT  (select b0101 from b01 where b0111='"+b0111+"') b0101, "
									+ " decode((select b0131 from b01 where b0111 = a02.a0201b),'1001','党委','1004','政府','1003','人大'," + 
									"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
									"       (select gw.fxyp07" + 
									"          from hz_mntp_gw gw" + 
									"         where gw.a0200 = a02.a0200" + 
									"           and gw.mntp00 = '"+mntp00+"'" + 
									"           and fxyp07 = -1) fxyp07," + 
									"       GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
									"       decode(a01.a0104, 1, '男', 2, '女') a0104," + 
									"       substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
									"       a01.a0192a," + 
									"       (select code_name" + 
									"          from code_value" + 
									"         where code_value.code_value = a01.a0141" + 
									"           and code_type = 'GB4762') a0141," + 
									"       (select bz.a01bzdesc" + 
									"          from HZ_MNTP_BZ bz" + 
									"         where bz.a01bzid = a02.a0200" + 
									"           and bz.mntp00 = '"+mntp00+"'" + 
									"           and a01bztype = '1') a01bzdesc" + 
									"  FROM a02, a01" + 
									" WHERE a01.A0000 = a02.a0000" + 
									"   AND a02.a0281 = 'true'" + 
									"   AND a02.a0255 = '1'" + 
									"   and a01.a0163='1'" + 
									"   and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
									"   and (select b0131 from b01 where b0111 = a02.a0201b)='"+zrrx+"'" + 
									"   and a02.a0201b in" + 
									"       (select b0111" + 
									"          from b01 b" + 
									"         where b.b0131 in ('1001', '1003', '1004', '1005', '1006', '1007')" + 
									"           and  b.b0111 like '"+b0111+"%')" + addsql+
									" order by " + 
									"          ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
									"              from (select a02.a0000," + 
									"                           b0269," + 
									"                           a0225," + 
									"                           row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
									"                      from a02, b01" + 
									"                     where a02.a0201b = b01.b0111" + 
									"                       and a0281 = 'true'" + 
									"                       and b01.b0111 like '"+b0111+"%') t" + 
									"             where rn = 1" + 
									"               and t.a0000 = a01.a0000))";
							CommQuery cqbs1=new CommQuery();
							List<HashMap<String, Object>> listCode1=cqbs1.getListBySQL(sql.toString());
							JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);
							
							
							sql="select *" + 
									"  from (SELECT   (select b0101 from b01 where b0111='"+b0111+"') b0101,"
									+ "decode((select b0131 from b01 where b0111 = a02.a0201b),'1001','党委','1004','政府','1003','人大'," + 
									"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
									"               (select gw.fxyp07" + 
									"                  from hz_mntp_gw gw" + 
									"                 where gw.a0200 = a02.a0200" + 
									"                   and gw.mntp00 = '"+mntp00+"'" + 
									"                   and fxyp07 = -1) fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
									"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
									"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
									"               a02.a0215a," + 
									"               a01.a0192a," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               b.a01bzdesc" + 
									"          FROM a02," + 
									"               a01," + 
									"               (select *" + 
									"                  from HZ_MNTP_BZ" + 
									"                 where mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '1') b" + 
									"         WHERE a01.A0000 = a02.a0000" + 
									"           AND a02.a0281 = 'true'" + 
									"           AND a02.a0255 = '1'" + 
									"           and b.a01bzid(+) = a02.a0200" + 
									"           and a01.a0163='1'" + 
									"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
									"           and a02.a0201b in" + 
									"               (select b0111" + 
									"                  from b01 b" + 
									"                 where b.b0131 in" + 
									"                       ('1001', '1003', '1004', '1005', '1006', '1007')" + 
									"                   and b.b0111 like '"+b0111+".%')" + 
									"           and not exists" + 
									"         (select gw.fxyp07" + 
									"                  from hz_mntp_gw gw" + 
									"                 where gw.a0200 = a02.a0200" + 
									"                   and gw.mntp00 = '"+mntp00+"'" + 
									"                   and fxyp07 = -1)" + 
									"                   and (select b0131 from b01 where b0111 = a02.a0201b)='"+zrrx+"'" + addsql+
									"         order by zrrx," + 
									"                  ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
									"                      from (select a02.a0000," + 
									"                                   b0269," + 
									"                                   a0225," + 
									"                                   row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
									"                              from a02, b01" + 
									"                             where a02.a0201b = b01.b0111" + 
									"                               and a0281 = 'true'" + 
									"                               and a0201b like '"+b0111+"%') t" + 
									"                     where rn = 1" + 
									"                       and t.a0000 = a01.a0000))) a01 " + 
									" union all" + 
									" select *" + 
									"  from (select  (select b0101 from b01 where b0111='"+b0111+"') b0101,"
									+ " decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e),'1001','党委','1004','政府','1003','人大'," + 
									"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
									"               " + 
									"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
									"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
									"               t.fxyp02 a0215a," + 
									"               a01.a0192a a0192a," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               " + 
									"               b.a01bzdesc" + 
									"          from v_mntp_gw_ry t," + 
									"               a01," + 
									"               (select *" + 
									"                  from HZ_MNTP_BZ" + 
									"                 where mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '2') b" + 
									"         where t.a0000 = a01.a0000" + 
									"           and t.fxyp07 = 1" + 
									"           and t.mntp00 = '"+mntp00+"'" + 
									"           and  (select b0111 from b01 where t.b01id=b01.b01id) ='"+b0111+"'" + 
									"           and b.a01bzid(+) = t.tp0100" + 
									"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)='"+zrrx+"' " + 
									"         order by t.fxyp00, t.sortnum)";
							CommQuery cqbs2=new CommQuery();
							List<HashMap<String, Object>> listCode2=cqbs2.getListBySQL(sql.toString());
							JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(listCode2);
							String totallength=String.valueOf(lengthQX);
							String num=String.valueOf(j);
							if(listCode1.size()>=listCode2.size()) {
								String length=String.valueOf(listCode1.size());
								this.getExecuteSG().addExecuteCode("AddQx1("+updateunDataStoreObject1.toString()+","+updateunDataStoreObject2.toString()+","+totallength+","+length+","+num+");");
							}else {
								String length=String.valueOf(listCode2.size());
								this.getExecuteSG().addExecuteCode("AddQx2("+updateunDataStoreObject2.toString()+","+updateunDataStoreObject1.toString()+","+totallength+","+length+","+num+");");
							}

						}
					}
					
					
				}
			}
			
			
			//新增的区县市
			@SuppressWarnings("unchecked")
			List<String> addqx= HBUtil.getHBSession().createSQLQuery("select distinct c.b01id from  HZ_MNTP_ORG_QXS c,fxyp " + 
					"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null " ).list();
			if(addqx.size()>0) {
				for(int j=0;j<addqx.size();j++) {
					String b01id=addqx.get(j);
					String zrrxsql="       select distinct zrrx from ( select *" + 
							"          from (select   decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx" + 
							"                  from v_mntp_gw_ry t," + 
							"                       a01," + 
							"                       (select *" + 
							"                          from HZ_MNTP_BZ" + 
							"                         where mntp00 = '"+mntp00+"'" + 
							"                           and a01bztype = '2') b" + 
							"                 where t.a0000 = a01.a0000" + 
							"                   and t.fxyp07 = 1" + 
							"                   and t.mntp00 = '"+mntp00+"'" + 
							"                   and t.b01id =" + 
							"                       '"+b01id+"'" + 
							"                   and b.a01bzid(+) = t.tp0100" + 
							"                 order by t.fxyp00, t.sortnum)) order by zrrx" ;
					
					
					List<String> zrrxList=HBUtil.getHBSession().createSQLQuery(zrrxsql).list();
					int lengthQX=0;
					
					if(zrrxList.size()>0) {
						//遍历算出总人数
						for(int k=0;k<zrrxList.size();k++) {
							String zrrx=zrrxList.get(k);	
							String sql="select * from(" + 
									"select *" + 
									"  from (select (select b0101 from HZ_MNTP_ORG_QXS where b01id = '"+b01id+"') b0101," + 
									"               decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)," + 
									"                      '1001'," + 
									"                      '党委'," + 
									"                      '1004'," + 
									"                      '政府'," + 
									"                      '1003'," + 
									"                      '人大'," + 
									"                      '1005'," + 
									"                      '政协'," + 
									"                      '1006'," + 
									"                      '院长'," + 
									"                      '1007'," + 
									"                      '检察长','Z01','其他') zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
									"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
									"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
									"               a01.qrzxl qrzxl," + 
									"               a01.zgxl  zgxl, " + 
									"               t.fxyp02 a0215a," + 
									"               a01.a0192a a0192a," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               b.a01bzdesc," + 
									"                (select sortnum from gwsort g  where g.sortid=t.fxyp00 and mntp00 = '"+mntp00+"') sortnum," + 
									"                t.sortnum peoplesort," + 
									"                 (select count(1)" + 
									"                          from  v_mntp_gw_ry s" + 
									"                         where s.zwqc00=t.zwqc00" + 
									"                          and s.fxyp00=t.fxyp00" + 
									"                         group by s.zwqc00) peoplenum"+
									"          from v_mntp_gw_ry t," + 
									"               a01," + 
									"               (select *" + 
									"                  from HZ_MNTP_BZ" + 
									"                 where mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '2') b" + 
									"         where t.a0000 = a01.a0000" + 
									"           and t.fxyp07 = 1" + 
									"           and t.mntp00 = '"+mntp00+"'" + 
									"           and  t.b01id =" + 
									"               '"+b01id+"'" + 
									"           and b.a01bzid(+) = t.tp0100" + 
									"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) = '"+zrrx+"'" + 
									"         order by t.fxyp00, t.sortnum)" + 
									")order by sortnum,peoplesort" ;
							CommQuery cqbs=new CommQuery();
							List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
							lengthQX+=listCode.size();	
						}
						
						//遍历 填入表格
						for(int k=0;k<zrrxList.size();k++) {
							String zrrx=zrrxList.get(k);
							String sql=	" select *" + 
									"  from (select  (select b0101 from HZ_MNTP_ORG_QXS where b01id='"+b01id+"') b0101,"
									+ " decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e),'1001','党委','1004','政府','1003','人大'," + 
									"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
									"               " + 
									"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
									"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
									"               t.fxyp02 a0215a," + 
									"               a01.a0192a a0192a," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               " + 
									"               b.a01bzdesc" + 
									"          from v_mntp_gw_ry t," + 
									"               a01," + 
									"               (select *" + 
									"                  from HZ_MNTP_BZ" + 
									"                 where mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '2') b" + 
									"         where t.a0000 = a01.a0000" + 
									"           and t.fxyp07 = 1" + 
									"           and t.mntp00 = '"+mntp00+"'" + 
									"           and  t.b01id='"+b01id+"'" + 
									"           and b.a01bzid(+) = t.tp0100" + 
									"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)='"+zrrx+"' " + 
									"         order by t.fxyp00, t.sortnum)";
							
							CommQuery cqbs2=new CommQuery();
							List<HashMap<String, Object>> listCode2=cqbs2.getListBySQL(sql.toString());
							JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(listCode2);
							String totallength=String.valueOf(lengthQX);
							String num=String.valueOf(k);
							
							List<HashMap<String, Object>> listCode1=cqbs2.getListBySQL("select * from v_mntp_gw_ry t where 1<>1");
							JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);
							
							String length=String.valueOf(listCode2.size());
							this.getExecuteSG().addExecuteCode("AddQx2("+updateunDataStoreObject2.toString()+","+updateunDataStoreObject1.toString()+","+totallength+","+length+","+num+");");
						}
					}
				}
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	

	@PageEvent("query")
	public int query() throws RadowException, AppException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		String b0101= this.getPageElement("b0101").getValue();
		System.out.println(b0101);
		String[] b0111s=b0101.split(",");
		String checked=this.getPageElement("showAll").getValue();
		String addsql="";	
		try {
			if("1".equals(checked)) {
				addsql=" and (select   gw.fxyp07  from hz_mntp_gw gw  where gw.a0200 = a02.a0200"
						+ "  and gw.mntp00 = '"+mntp00+"') is not null ";
			}
			if(b0101.length()>0) {
				//所有选中的机构以及机构性质（除新增机构
				ArrayList<String> b01info= new ArrayList<String>();
				for(String string:b0111s) {
					if(string.indexOf("#")==-1){
						b01info.add(string);
					}			
				}
				//所有选中的机构（除新增机构
				ArrayList<String> b0111all= new ArrayList<String>();
				for(String string:b0111s) {
					if(string.indexOf("@")==-1 && string.indexOf("#")==-1) {
						b0111all.add(string);
					}else if(string.indexOf("#")==-1){
						b0111all.add(string.split("@")[0]);
					}
				}
				//所有选中的机构去重（除新增机构
				ArrayList<String> b0111list = new ArrayList<String>();
				  for (String str : b0111all) {
				       if (!b0111list.contains(str)) {
				           b0111list.add(str);
				       }
				   }
				if(b0111list.size()>0) {
					for(int z=0;z<b0111list.size();z++) {
						String b0111=b0111list.get(z);
						if(b0111.length()==15 && "001.001.004".equals(b0111.substring(0,11))) {
							ArrayList<String> zrrxList= new ArrayList();
							for(int k=0;k<b01info.size();k++){
								if(b01info.get(k).indexOf("@")!=-1) {
									if(b01info.get(k).split("@")[0].equals(b0111)) {
										zrrxList.add(b01info.get(k).split("@")[1]);
									}
								}
							}
							int lengthQX=0;
							//循环遍历较长的机构性质
							if(zrrxList.size()>0) {
								for(int j=0;j<zrrxList.size();j++) {
									String zrrx=zrrxList.get(j);
									String sql="SELECT decode((select b0131 from b01 where b0111 = a02.a0201b),'1001','党委','1004','政府','1003','人大'," + 
											"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
											"       (select gw.fxyp07" + 
											"          from hz_mntp_gw gw" + 
											"         where gw.a0200 = a02.a0200" + 
											"           and gw.mntp00 = '"+mntp00+"'" + 
											"           and fxyp07 = -1) fxyp07," + 
											"       GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
											"       decode(a01.a0104, 1, '男', 2, '女') a0104," + 
											"       substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
											"       a01.a0192a," + 
											"       (select code_name" + 
											"          from code_value" + 
											"         where code_value.code_value = a01.a0141" + 
											"           and code_type = 'GB4762') a0141," + 
											"       (select bz.a01bzdesc" + 
											"          from HZ_MNTP_BZ bz" + 
											"         where bz.a01bzid = a02.a0200" + 
											"           and bz.mntp00 = '"+mntp00+"'" + 
											"           and a01bztype = '1') a01bzdesc" + 
											"  FROM a02, a01" + 
											" WHERE a01.A0000 = a02.a0000" + 
											"   AND a02.a0281 = 'true'" + 
											"   AND a02.a0255 = '1'" + 
											"   and a01.a0163='1'" + 
											"   and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
											"   and (select b0131 from b01 where b0111 = a02.a0201b)='"+zrrx+"'" + 
											"   and a02.a0201b in" + 
											"       (select b0111" + 
											"          from b01 b" + 
											"         where b.b0131 in ('1001', '1003', '1004', '1005', '1006', '1007')" + 
											"           and  b.b0111 like '"+b0111+"%')" + addsql+
											" order by " + 
											"          ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
											"              from (select a02.a0000," + 
											"                           b0269," + 
											"                           a0225," + 
											"                           row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
											"                      from a02, b01" + 
											"                     where a02.a0201b = b01.b0111" + 
											"                       and a0281 = 'true'" + 
											"                       and b01.b0111 like '"+b0111+"%') t" + 
											"             where rn = 1" + 
											"               and t.a0000 = a01.a0000))";
									CommQuery cqbs1=new CommQuery();
									List<HashMap<String, Object>> listCode1=cqbs1.getListBySQL(sql.toString());
									
									
									sql="select *" + 
											"  from (SELECT  decode((select b0131 from b01 where b0111 = a02.a0201b),'1001','党委','1004','政府','1003','人大'," + 
											"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
											"               (select gw.fxyp07" + 
											"                  from hz_mntp_gw gw" + 
											"                 where gw.a0200 = a02.a0200" + 
											"                   and gw.mntp00 = '"+mntp00+"'" + 
											"                   and fxyp07 = -1) fxyp07," + 
											"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
											"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
											"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
											"               a02.a0215a," + 
											"               a01.a0192a," + 
											"               (select code_name" + 
											"                  from code_value" + 
											"                 where code_value.code_value = a01.a0141" + 
											"                   and code_type = 'GB4762') a0141," + 
											"               b.a01bzdesc" + 
											"          FROM a02," + 
											"               a01," + 
											"               (select *" + 
											"                  from HZ_MNTP_BZ" + 
											"                 where mntp00 = '"+mntp00+"'" + 
											"                   and a01bztype = '1') b" + 
											"         WHERE a01.A0000 = a02.a0000" + 
											"           AND a02.a0281 = 'true'" + 
											"           AND a02.a0255 = '1'" + 
											"           and b.a01bzid(+) = a02.a0200" + 
											"           and a01.a0163='1'" + 
											"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
											"           and a02.a0201b in" + 
											"               (select b0111" + 
											"                  from b01 b" + 
											"                 where b.b0131 in" + 
											"                       ('1001', '1003', '1004', '1005', '1006', '1007')" + 
											"                   and b.b0111 like '"+b0111+".%')" + 
											"           and not exists" + 
											"         (select gw.fxyp07" + 
											"                  from hz_mntp_gw gw" + 
											"                 where gw.a0200 = a02.a0200" + 
											"                   and gw.mntp00 = '"+mntp00+"'" + 
											"                   and fxyp07 = -1)" + 
											"                   and (select b0131 from b01 where b0111 = a02.a0201b)='"+zrrx+"'" + addsql+
											"         order by zrrx," + 
											"                  ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
											"                      from (select a02.a0000," + 
											"                                   b0269," + 
											"                                   a0225," + 
											"                                   row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
											"                              from a02, b01" + 
											"                             where a02.a0201b = b01.b0111" + 
											"                               and a0281 = 'true'" + 
											"                               and a0201b like '"+b0111+"%') t" + 
											"                     where rn = 1" + 
											"                       and t.a0000 = a01.a0000))) a01 " + 
											" union all" + 
											" select *" + 
											"  from (select decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e),'1001','党委','1004','政府','1003','人大'," + 
											"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
											"               t.fxyp07 fxyp07," + 
											"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
											"               " + 
											"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
											"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
											"               t.fxyp02 a0215a," + 
											"               a01.a0192a a0192a," + 
											"               (select code_name" + 
											"                  from code_value" + 
											"                 where code_value.code_value = a01.a0141" + 
											"                   and code_type = 'GB4762') a0141," + 
											"               " + 
											"               b.a01bzdesc" + 
											"          from v_mntp_gw_ry t," + 
											"               a01," + 
											"               (select *" + 
											"                  from HZ_MNTP_BZ" + 
											"                 where mntp00 = '"+mntp00+"'" + 
											"                   and a01bztype = '2') b" + 
											"         where t.a0000 = a01.a0000" + 
											"           and t.fxyp07 = 1" + 
											"           and t.mntp00 = '"+mntp00+"'" + 
											"           and  (select b0111 from b01 where t.b01id=b01.b01id) ='"+b0111+"'" + 
											"           and b.a01bzid(+) = t.tp0100" + 
											"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)='"+zrrx+"' " + 
											"         order by t.fxyp00, t.sortnum)";
									CommQuery cqbs2=new CommQuery();
									List<HashMap<String, Object>> listCode2=cqbs2.getListBySQL(sql.toString());
									if(listCode1.size()>=listCode2.size()) {
										lengthQX+=listCode1.size();
									}else {
										lengthQX+=listCode2.size();
									}
								}
							}
							
							if(zrrxList.size()>0) {
								for(int j=0;j<zrrxList.size();j++) {
									String zrrx=zrrxList.get(j);
									String sql="SELECT  (select b0101 from b01 where b0111='"+b0111+"') b0101, "
											+ " decode((select b0131 from b01 where b0111 = a02.a0201b),'1001','党委','1004','政府','1003','人大'," + 
											"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
											"       (select gw.fxyp07" + 
											"          from hz_mntp_gw gw" + 
											"         where gw.a0200 = a02.a0200" + 
											"           and gw.mntp00 = '"+mntp00+"'" + 
											"           and fxyp07 = -1) fxyp07," + 
											"       GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
											"       decode(a01.a0104, 1, '男', 2, '女') a0104," + 
											"       substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
											"       a01.a0192a," + 
											"       (select code_name" + 
											"          from code_value" + 
											"         where code_value.code_value = a01.a0141" + 
											"           and code_type = 'GB4762') a0141," + 
											"       (select bz.a01bzdesc" + 
											"          from HZ_MNTP_BZ bz" + 
											"         where bz.a01bzid = a02.a0200" + 
											"           and bz.mntp00 = '"+mntp00+"'" + 
											"           and a01bztype = '1') a01bzdesc" + 
											"  FROM a02, a01" + 
											" WHERE a01.A0000 = a02.a0000" + 
											"   AND a02.a0281 = 'true'" + 
											"   AND a02.a0255 = '1'" + 
											"   and a01.a0163='1'" + 
											"   and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
											"   and (select b0131 from b01 where b0111 = a02.a0201b)='"+zrrx+"'" + 
											"   and a02.a0201b in" + 
											"       (select b0111" + 
											"          from b01 b" + 
											"         where b.b0131 in ('1001', '1003', '1004', '1005', '1006', '1007')" + 
											"           and  b.b0111 like '"+b0111+"%')" + addsql+
											" order by " + 
											"          ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
											"              from (select a02.a0000," + 
											"                           b0269," + 
											"                           a0225," + 
											"                           row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
											"                      from a02, b01" + 
											"                     where a02.a0201b = b01.b0111" + 
											"                       and a0281 = 'true'" + 
											"                       and b01.b0111 like '"+b0111+"%') t" + 
											"             where rn = 1" + 
											"               and t.a0000 = a01.a0000))";
									CommQuery cqbs1=new CommQuery();
									List<HashMap<String, Object>> listCode1=cqbs1.getListBySQL(sql.toString());
									JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);
									
									
									sql="select *" + 
											"  from (SELECT   (select b0101 from b01 where b0111='"+b0111+"') b0101,"
											+ "decode((select b0131 from b01 where b0111 = a02.a0201b),'1001','党委','1004','政府','1003','人大'," + 
											"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
											"               (select gw.fxyp07" + 
											"                  from hz_mntp_gw gw" + 
											"                 where gw.a0200 = a02.a0200" + 
											"                   and gw.mntp00 = '"+mntp00+"'" + 
											"                   and fxyp07 = -1) fxyp07," + 
											"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
											"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
											"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
											"               a02.a0215a," + 
											"               a01.a0192a," + 
											"               (select code_name" + 
											"                  from code_value" + 
											"                 where code_value.code_value = a01.a0141" + 
											"                   and code_type = 'GB4762') a0141," + 
											"               b.a01bzdesc" + 
											"          FROM a02," + 
											"               a01," + 
											"               (select *" + 
											"                  from HZ_MNTP_BZ" + 
											"                 where mntp00 = '"+mntp00+"'" + 
											"                   and a01bztype = '1') b" + 
											"         WHERE a01.A0000 = a02.a0000" + 
											"           AND a02.a0281 = 'true'" + 
											"           AND a02.a0255 = '1'" + 
											"           and b.a01bzid(+) = a02.a0200" + 
											"           and a01.a0163='1'" + 
											"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
											"           and a02.a0201b in" + 
											"               (select b0111" + 
											"                  from b01 b" + 
											"                 where b.b0131 in" + 
											"                       ('1001', '1003', '1004', '1005', '1006', '1007')" + 
											"                   and b.b0111 like '"+b0111+".%')" + 
											"           and not exists" + 
											"         (select gw.fxyp07" + 
											"                  from hz_mntp_gw gw" + 
											"                 where gw.a0200 = a02.a0200" + 
											"                   and gw.mntp00 = '"+mntp00+"'" + 
											"                   and fxyp07 = -1)" + 
											"                   and (select b0131 from b01 where b0111 = a02.a0201b)='"+zrrx+"'" + addsql+
											"         order by zrrx," + 
											"                  ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
											"                      from (select a02.a0000," + 
											"                                   b0269," + 
											"                                   a0225," + 
											"                                   row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
											"                              from a02, b01" + 
											"                             where a02.a0201b = b01.b0111" + 
											"                               and a0281 = 'true'" + 
											"                               and a0201b like '"+b0111+"%') t" + 
											"                     where rn = 1" + 
											"                       and t.a0000 = a01.a0000))) a01 " + 
											" union all" + 
											" select *" + 
											"  from (select  (select b0101 from b01 where b0111='"+b0111+"') b0101,"
											+ " decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e),'1001','党委','1004','政府','1003','人大'," + 
											"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
											"               t.fxyp07 fxyp07," + 
											"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
											"               " + 
											"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
											"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
											"               t.fxyp02 a0215a," + 
											"               a01.a0192a a0192a," + 
											"               (select code_name" + 
											"                  from code_value" + 
											"                 where code_value.code_value = a01.a0141" + 
											"                   and code_type = 'GB4762') a0141," + 
											"               " + 
											"               b.a01bzdesc" + 
											"          from v_mntp_gw_ry t," + 
											"               a01," + 
											"               (select *" + 
											"                  from HZ_MNTP_BZ" + 
											"                 where mntp00 = '"+mntp00+"'" + 
											"                   and a01bztype = '2') b" + 
											"         where t.a0000 = a01.a0000" + 
											"           and t.fxyp07 = 1" + 
											"           and t.mntp00 = '"+mntp00+"'" + 
											"           and  (select b0111 from b01 where t.b01id=b01.b01id) ='"+b0111+"'" + 
											"           and b.a01bzid(+) = t.tp0100" + 
											"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)='"+zrrx+"' " + 
											"         order by t.fxyp00, t.sortnum)";
									CommQuery cqbs2=new CommQuery();
									List<HashMap<String, Object>> listCode2=cqbs2.getListBySQL(sql.toString());
									JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(listCode2);
									String totallength=String.valueOf(lengthQX);
									String num=String.valueOf(j);
									if(listCode1.size()>=listCode2.size()) {
										String length=String.valueOf(listCode1.size());
										this.getExecuteSG().addExecuteCode("AddQx1("+updateunDataStoreObject1.toString()+","+updateunDataStoreObject2.toString()+","+totallength+","+length+","+num+");");
									}else {
										String length=String.valueOf(listCode2.size());
										this.getExecuteSG().addExecuteCode("AddQx2("+updateunDataStoreObject2.toString()+","+updateunDataStoreObject1.toString()+","+totallength+","+length+","+num+");");
									}
	
	
								}
							}
						}else{
							String sql="select * from" + 
									"       (select  (select count(*) from a01,a02   where a01.A0000 = a02.a0000" + 
									"           AND a02.a0281 = 'true'" + 
									"           AND a02.a0255 = '1'" + 
									"           and a01.a0163='1'" + 
									"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
									"           and a02.a0201b = '"+b0111+"') rs," + 
									"   (select b0101 from b01 where b01.b0111=a02.a0201b) b0101,"+
									"                a02.a0201e zrrx," + 
									"               (select gw.fxyp07" + 
									"                  from hz_mntp_gw gw" + 
									"                 where gw.a0200 = a02.a0200" + 
									"                   and gw.mntp00 = '"+mntp00+"'" + 
									"                   and fxyp07 = -1) fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
									"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
									"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
									"               a01.a0192a," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               (select bz.a01bzdesc" + 
									"                  from HZ_MNTP_BZ bz" + 
									"                 where bz.a01bzid = a02.a0200" + 
									"                   and bz.mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '1') a01bzdesc" + 
									"          from a01, a02" + 
									"         where a01.A0000 = a02.a0000" + 
									"           AND a02.a0281 = 'true'" + 
									"           AND a02.a0255 = '1'" + 
									"           and a01.a0163='1'" + 
									"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
									"           and a02.a0201b = '"+b0111+"'" +addsql+ 
									"         order by zrrx," + 
									"                  ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
									"                      from (select a02.a0000," + 
									"                                   b0269," + 
									"                                   a0225," + 
									"                                   row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
									"                              from a02, b01" + 
									"                             where a02.a0201b = b01.b0111" + 
									"                               and a0281 = 'true'" + 
									"                               and a0201b like '"+b0111+"%') t" + 
									"                     where rn = 1" + 
									"                       and t.a0000 = a01.a0000))" + 
									"        ) a01";
							CommQuery cqbs=new CommQuery();
							List<HashMap<String, Object>> listCode1=cqbs.getListBySQL(sql.toString());
							JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);
							
							sql="select *" + 
									"  from (select a02.a0201e zrrx," + 
									"               (select gw.fxyp07" + 
									"                  from hz_mntp_gw gw" + 
									"                 where gw.a0200 = a02.a0200" + 
									"                   and gw.mntp00 = '"+mntp00+"'" + 
									"                   and fxyp07 = -1) fxyp07," + 
									"   (select b0101 from b01 where b01.b0111=a02.a0201b) b0101,"+
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
									"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
									"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
									"               a02.a0215a," + 
									"               a01.a0192a," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               (select bz.a01bzdesc" + 
									"                  from HZ_MNTP_BZ bz" + 
									"                 where bz.a01bzid = a02.a0200" + 
									"                   and bz.mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '1') a01bzdesc" + 
									"          from a01, a02" + 
									"         where a01.A0000 = a02.a0000" + 
									"           AND a02.a0281 = 'true'" + 
									"           AND a02.a0255 = '1'" + 
									"           and a01.a0163='1'" + 
									"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
									"           and a02.a0201b = '"+b0111+"'" + 
									"           and not exists" + 
									"         (select gw.fxyp07" + 
									"                  from hz_mntp_gw gw" + 
									"                 where gw.a0200 = a02.a0200" + 
									"                   and gw.mntp00 = '"+mntp00+"'" + 
									"                   and fxyp07 = -1)" + addsql+
									"         order by zrrx,((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
									"                      from (select a02.a0000," + 
									"                                   b0269," + 
									"                                   a0225," + 
									"                                   row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
									"                              from a02, b01" + 
									"                             where a02.a0201b = b01.b0111" + 
									"                               and a0281 = 'true'" + 
									"                               and a0201b like '"+b0111+"%') t" + 
									"                     where rn = 1" + 
									"                       and t.a0000 = a01.a0000))) a01" + 
									"/*UNION 结果集中的列名总是等于 UNION 中第一个 SELECT 语句中的列名。*/" + 
									"union all  " + 
									"select *" + 
									"  from (select decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"             (select b0101 from b01 where b0111 = '"+b0111+"') b0101," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
									"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
									"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
									"              t.fxyp02 a0215a," + 
									"               a01.a0192a," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"                b.a01bzdesc" + 
									"          from v_mntp_gw_ry t, a01, (select *" + 
									"                          from HZ_MNTP_BZ" + 
									"                         where mntp00 = '"+mntp00+"'" + 
									"                           and a01bztype = '2') b" + 
									"         where t.a0000 = a01.a0000" + 
									"           and t.fxyp07 = 1" + 
									"           and t.mntp00 = '"+mntp00+"'" + 
									"           and (select b0111 from b01 where t.b01id=b01.b01id) ='"+b0111+"'" + 
									"             and b.a01bzid(+) = t.tp0100" + 
									"         order by t.fxyp00, t.sortnum)";
							CommQuery cqbs1=new CommQuery();
							String c=sql.toString();
							List<HashMap<String, Object>> listCode2=cqbs1.getListBySQL(sql.toString());
							JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(listCode2);
							if(listCode1.size()>=listCode2.size()) {
								String length=String.valueOf(listCode1.size());
								this.getExecuteSG().addExecuteCode("AddSz1("+updateunDataStoreObject1.toString()+","+updateunDataStoreObject2.toString()+","+length.toString()+");");
							}else {
								String length=String.valueOf(listCode2.size());
								this.getExecuteSG().addExecuteCode("AddSz2("+updateunDataStoreObject2.toString()+","+updateunDataStoreObject1.toString()+","+length.toString()+");");
							}
						}
					}
				}
				this.setNextEventName("queryadd");
			}else {
				this.setNextEventName("initX");
			}
		}catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("queryadd")
	public int queryadd() throws RadowException, AppException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		String b0101= this.getPageElement("b0101").getValue();
		System.out.println(b0101);
		String[] b0111s=b0101.split(",");
		String checked=this.getPageElement("showAll").getValue();
		String addsql="";
		try {
			//所有新增机构以及机构性质的信息
			ArrayList<String> b01info= new ArrayList<String>();
			for(String string:b0111s) {
				if(string.indexOf("#")!=-1){
					b01info.add(string);
				}			
			}
			
			//所有选中的新增机构
			ArrayList<String> b01idsz= new ArrayList<String>();
			for(String string:b0111s) {
				if(string.indexOf("#")!=-1 ) {
					if("1".equals(string.split("#")[1])) {
						b01idsz.add(string.split("#")[0]);
					}		
				}
			}
			
			
			if(b01idsz.size()>0) {
				for(int j=0;j<b01idsz.size();j++) {
					String b01id=b01idsz.get(j);
					String sql="select *" + 
							"  from (select decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx," + 
							"               t.fxyp07 fxyp07," + 
							"             (select jgmc from HZ_MNTP_ORG_QT where b01id = '"+b01id+"') b0101," + 
							"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
							"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
							"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
							"              t.fxyp02 a0215a," + 
							"               a01.a0192a," + 
							"               (select code_name" + 
							"                  from code_value" + 
							"                 where code_value.code_value = a01.a0141" + 
							"                   and code_type = 'GB4762') a0141," + 
							"                b.a01bzdesc" + 
							"          from v_mntp_gw_ry t, a01, (select *" + 
							"                          from HZ_MNTP_BZ" + 
							"                         where mntp00 = '"+mntp00+"'" + 
							"                           and a01bztype = '2') b" + 
							"         where t.a0000 = a01.a0000" + 
							"           and t.fxyp07 = 1" + 
							"           and t.mntp00 = '"+mntp00+"'" + 
							"           and t.b01id ='"+b01id+"'" + 
							"             and b.a01bzid(+) = t.tp0100" + 
							"         order by t.fxyp00, t.sortnum)";
					CommQuery cqbs=new CommQuery();
					List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
					JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
					
					List<HashMap<String, Object>> listCode1=cqbs.getListBySQL("select * from v_mntp_gw_ry t where 1<>1");
					JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);
					
					String length=String.valueOf(listCode.size());
					this.getExecuteSG().addExecuteCode("AddSz2("+updateunDataStoreObject.toString()+","+updateunDataStoreObject1.toString()+","+length+");");
				}
			}
			
			

			//所有选中的新增区县
			ArrayList<String> b01idqx= new ArrayList<String>();
			for(String string:b0111s) {
				if(string.indexOf("#")!=-1 ) {
					if("2".equals(string.split("#")[1]) && !b01idqx.contains(string.split("#")[0])) {
						b01idqx.add(string.split("#")[0]);
					}		
				}
			}
			
			
			if(b01idqx.size()>0) {
				for(int i=0;i<b01idqx.size();i++) {
					String b01id=b01idqx.get(i);
					ArrayList<String> zrrxList= new ArrayList();
					for(int k=0;k<b01info.size();k++){
						if("2".equals(b01info.get(k).split("#")[1])) {
							if(b01info.get(k).split("#")[0].equals(b01id)) {
								zrrxList.add(b01info.get(k).split("#")[2]);
							}
						}
					}
					int lengthQX=0;
					if(zrrxList.size()>0) {
						for(int j=0;j<zrrxList.size();j++) {
							String zrrx=zrrxList.get(j);	
							String sql="select * from(" + 
									"select *" + 
									"  from (select (select b0101 from HZ_MNTP_ORG_QXS where b01id = '"+b01id+"') b0101," + 
									"               decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)," + 
									"                      '1001'," + 
									"                      '党委'," + 
									"                      '1004'," + 
									"                      '政府'," + 
									"                      '1003'," + 
									"                      '人大'," + 
									"                      '1005'," + 
									"                      '政协'," + 
									"                      '1006'," + 
									"                      '院长'," + 
									"                      '1007'," + 
									"                      '检察长','Z01','其他') zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
									"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
									"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
									"               a01.qrzxl qrzxl," + 
									"               a01.zgxl  zgxl, " + 
									"               t.fxyp02 a0215a," + 
									"               a01.a0192a a0192a," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               b.a01bzdesc," + 
									"                (select sortnum from gwsort g  where g.sortid=t.fxyp00 and mntp00 = '"+mntp00+"') sortnum," + 
									"                t.sortnum peoplesort," + 
									"                 (select count(1)" + 
									"                          from  v_mntp_gw_ry s" + 
									"                         where s.zwqc00=t.zwqc00" + 
									"                          and s.fxyp00=t.fxyp00" + 
									"                         group by s.zwqc00) peoplenum"+
									"          from v_mntp_gw_ry t," + 
									"               a01," + 
									"               (select *" + 
									"                  from HZ_MNTP_BZ" + 
									"                 where mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '2') b" + 
									"         where t.a0000 = a01.a0000" + 
									"           and t.fxyp07 = 1" + 
									"           and t.mntp00 = '"+mntp00+"'" + 
									"           and  t.b01id =" + 
									"               '"+b01id+"'" + 
									"           and b.a01bzid(+) = t.tp0100" + 
									"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) = '"+zrrx+"'" + 
									"         order by t.fxyp00, t.sortnum)" + 
									")order by sortnum,peoplesort" ;
							CommQuery cqbs=new CommQuery();
							List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
							lengthQX+=listCode.size();	
						}
						
						//遍历 填入表格
						for(int k=0;k<zrrxList.size();k++) {
							String zrrx=zrrxList.get(k);
							String sql=	" select *" + 
									"  from (select  (select b0101 from HZ_MNTP_ORG_QXS where b01id='"+b01id+"') b0101,"
									+ " decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e),'1001','党委','1004','政府','1003','人大'," + 
									"       '1005','政协','1006','院长','1007','检察长','Z01','其他') zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101," + 
									"               " + 
									"               decode(a01.a0104, 1, '男', 2, '女') a0104," + 
									"               substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107," + 
									"               t.fxyp02 a0215a," + 
									"               a01.a0192a a0192a," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               " + 
									"               b.a01bzdesc" + 
									"          from v_mntp_gw_ry t," + 
									"               a01," + 
									"               (select *" + 
									"                  from HZ_MNTP_BZ" + 
									"                 where mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '2') b" + 
									"         where t.a0000 = a01.a0000" + 
									"           and t.fxyp07 = 1" + 
									"           and t.mntp00 = '"+mntp00+"'" + 
									"           and  t.b01id='"+b01id+"'" + 
									"           and b.a01bzid(+) = t.tp0100" + 
									"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)='"+zrrx+"' " + 
									"         order by t.fxyp00, t.sortnum)";
							
							CommQuery cqbs2=new CommQuery();
							List<HashMap<String, Object>> listCode2=cqbs2.getListBySQL(sql.toString());
							JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(listCode2);
							String totallength=String.valueOf(lengthQX);
							String num=String.valueOf(k);
							
							List<HashMap<String, Object>> listCode1=cqbs2.getListBySQL("select * from v_mntp_gw_ry t where 1<>1");
							JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);
							
							String length=String.valueOf(listCode2.size());
							this.getExecuteSG().addExecuteCode("AddQx2("+updateunDataStoreObject2.toString()+","+updateunDataStoreObject1.toString()+","+totallength+","+length+","+num+");");
						}
					}
				}
			}
			
		}catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	/**
	 * 获取数据
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("expData")
	public  String expData() throws RadowException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		try {
			String sql = "select * from fxyp;";
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
