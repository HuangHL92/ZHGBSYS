package com.insigma.siis.local.pagemodel.fxyp;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BZRYBDGWPageModel extends PageModel{

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
			List<String> tpdw= HBUtil.getHBSession().createSQLQuery("select b0111 from (select distinct  b0111��c.b0269 from b01 c,fxyp " + 
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
						
						String addsql=" and (select   gw.fxyp07  from hz_mntp_gw gw  where gw.a0200 = a02.a0200"
								+ "  and gw.mntp00 = '"+mntp00+"') is not null ";
						
						String listsql=" and not exists(select t.a0200 from hz_mntp_hhmd t where a02.a0200=t.a0200 and t.mdtype='1'  " + 
								" minus " + 
								" select a01bzid from hz_mntp_bz where a01bztype ='1' and bmd='1' and a01bzid=a02.a0200 and mntp00='"+mntp00+"') ";
						
						
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
								"                           and b.b0111 like '"+b0111+".%') " +addsql+listsql+
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
								"          from (select decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx" + 
								"      " + 
								"                  from v_mntp_gw_ry t," + 
								"                       a01," + 
								"                       (select *" + 
								"                          from HZ_MNTP_BZ" + 
								"                         where mntp00 = '"+mntp00+"'" + 
								"                           and a01bztype = '2') b" + 
								"                 where t.a0000 = a01.a0000(+)" + 
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
									zrrxname="��ί";
								}else if("1004".equals(zrrx)) {
									zrrxname="����";
								}else if("1003".equals(zrrx)) {
									zrrxname="�˴�";
								}else if("1005".equals(zrrx)) {
									zrrxname="��Э";
								}else if("1006".equals(zrrx)) {
									zrrxname="��Ժ";
								}else if("1007".equals(zrrx)) {
									zrrxname="���Ժ";
								}else if("Z01".equals(zrrx)) {
									zrrxname="����";
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
			//������ֱ
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
			
			//��������
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
							"                 where t.a0000 = a01.a0000(+)" + 
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
								zrrxname="��ί";
							}else if("1004".equals(zrrx)) {
								zrrxname="����";
							}else if("1003".equals(zrrx)) {
								zrrxname="�˴�";
							}else if("1005".equals(zrrx)) {
								zrrxname="��Э";
							}else if("1006".equals(zrrx)) {
								zrrxname="��Ժ";
							}else if("1007".equals(zrrx)) {
								zrrxname="���Ժ";
							}else if("Z01".equals(zrrx)) {
								zrrxname="����";
							}
							map.put(b01id+"#2#"+zrrx, addname1.get(0)+zrrxname);
						}
					}
				}
			}
			((Combo)this.getPageElement("b0101")).setValueListForSelect(map); 
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
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
		String checked1=this.getPageElement("showgrey").getValue();
		String addsql="";
		String listsql=" and not exists(select t.a0200 from hz_mntp_hhmd t where a02.a0200=t.a0200 " + 
				" minus " + 
				" select a01bzid from hz_mntp_bz where a01bztype ='1' and bmd='1' and a01bzid=a02.a0200 and mntp00='"+mntp00+"') ";
		try {
			if("1".equals(checked)) {
				addsql=" and (select   gw.fxyp07  from hz_mntp_gw gw  where gw.a0200 = a02.a0200"
						+ "  and gw.mntp00 = '"+mntp00+"') is not null ";
			}
			if("1".equals(checked1)) {
				listsql=" and not exists(select t.a0200 from hz_mntp_hhmd t where a02.a0200=t.a0200 and t.mdtype='1' " + 
						" minus " + 
						" select a01bzid from hz_mntp_bz where a01bztype ='1' and a01bzid=a02.a0200 and bmd='1'  and mntp00='"+mntp00+"') ";
			}
			//�����������ֱ��λ�������У������ƽ̨
			@SuppressWarnings("unchecked")
			List<String> tpsz= HBUtil.getHBSession().createSQLQuery("select b0111 from (select distinct  b0111,c.b0269 from b01 c,fxyp " + 
					"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null and b0111 <> '001.002' " + 
					"and substr(b0111,0,11)<>  '001.001.001' " +
					"and (substr(b0111,0,11)<>  '001.001.004' or length(b0111)<>15) " + 
					"order by c.b0269)").list();
			
			if(tpsz.size()>0) {
				for(int i=0;i<tpsz.size();i++) {
					String b0111=tpsz.get(i);

					String sql="select * from(" + 
							"select *" + 
							"  from (select a02.a0201e zrrx," + 
							"               (select gw.fxyp07" + 
							"                  from hz_mntp_gw gw" + 
							"                 where gw.a0200 = a02.a0200" + 
							"                   and gw.mntp00 = '"+mntp00+"'" + 
							"                   and fxyp07 = -1) fxyp07," + 
							"               (select b0101 from b01 where b01.b0111 = a02.a0201b) b0101," + 
							"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
							"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
							"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107,              " + 
							"               a01.qrzxl qrzxl," + 
							"               a01.zgxl  zgxl,               " + 
							"               a02.a0215a," + 
							"               a01.a0192a,a01.dbwy,'"+b0111+"' b0111p," + 
							"               (select code_name" + 
							"                  from code_value" + 
							"                 where code_value.code_value = a01.a0141" + 
							"                   and code_type = 'GB4762') a0141," + 
							"               (select bz.rybz" + 
							"                  from HZ_MNTP_BZ bz" + 
							"                 where bz.a01bzid = a02.a0200" + 
							"                   and bz.mntp00 = '"+mntp00+"'" + 
							"                   and a01bztype = '1') rybz,                   " + 
							"               (select sortnum from gwsort g where g.sortid=a02.a0200 and mntp00 = '"+mntp00+"') sortnum,null peoplesort" + 
							"               , null peoplenum,'' bdfxyp00,null zwqc01             " + 
							"          from a01, a02" + 
							"         where a01.A0000 = a02.a0000" + 
							"           AND a02.a0281 = 'true'" + 
							"           AND a02.a0255 = '1'" + 
							"           and a01.a0163='1'" + 
							"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
							"           and a02.a0201b = '"+b0111+"'" + addsql+ listsql+
							"           and not exists" + 
							"                 (select 1" + 
							"                          from GWZWREF gw" + 
							"                         where gw.a0200 = a02.a0200" + 
							"                           and gw.mntp00 =" + 
							"                               '"+mntp00+"') )                 " + 
							" union all " + 
							"select *" + 
							"  from (select decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx," + 
							"               t.fxyp07 fxyp07," + 
							"               (select b0101 from b01 where b0111 = '"+b0111+"') b0101," + 
							"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
							"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
							"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107,       " + 
							"               a01.qrzxl qrzxl," + 
							"               a01.zgxl  zgxl," + 
							"               t.fxyp02 a0215a," + 
							"               a01.a0192a,a01.dbwy,'"+b0111+"' b0111p," + 
							"               (select code_name" + 
							"                  from code_value" + 
							"                 where code_value.code_value = a01.a0141" + 
							"                   and code_type = 'GB4762') a0141," + 
							"               b.rybz,  " + 
							"               (select sortnum from gwsort g  where g.sortid=t.fxyp00 and mntp00 = '"+mntp00+"') sortnum, t.sortnum peoplesort," + 
							"                 (select count(1)" + 
							"                          from  v_mntp_gw_ry s" + 
							"                         where s.zwqc00=t.zwqc00" + 
							"                          and s.fxyp00=t.fxyp00" + 
							"                         group by s.zwqc00) peoplenum,"+
							"         (select fxyp00 from gwzwref f where f.fxyp00=t.fxyp00 ) bdfxyp00,t.zwqc01" + 
							"          from v_mntp_gw_ry t," + 
							"               a01," + 
							"               (select *" + 
							"                  from HZ_MNTP_BZ" + 
							"                 where mntp00 = '"+mntp00+"'" + 
							"                   and a01bztype = '2') b" + 
							"         where t.a0000 = a01.a0000(+)" + 
							"           and t.fxyp07 = 1" + 
							"           and t.mntp00 = '"+mntp00+"'" + 
							"           and (select b0111 from b01 where t.b01id = b01.b01id) =" + 
							"               '"+b0111+"'" + 
							"           and b.a01bzid(+) = t.tp0100" + 
							"         order by t.fxyp00, t.sortnum)" + 
							")order by sortnum,peoplesort";
					
					String formersql="select a01.a0101,a01.a0000," + 
							"       decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
							"       decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
							"       a01.qrzxl qrzxl," + 
							"       a01.zgxl zgxl,a01.dbwy,'"+b0111+"' b0111p," + 
							"       (select code_name" + 
							"          from code_value" + 
							"         where code_value.code_value = a01.a0141" + 
							"           and code_type = 'GB4762') a0141," + 
							"       (select bz.rybz" + 
							"          from HZ_MNTP_BZ bz" + 
							"         where bz.a01bzid = f.a0200" + 
							"           and bz.mntp00 = '"+mntp00+"'" + 
							"           and a01bztype = '1') rybz," + 
							"       f.fxyp00,"+
						    "(select fxyp07 from fxyp x where x.a0200=f.a0200 and x.mntp00 = '"+mntp00+"' ) fxyp07" + 
							"   from GWZWREF f, a01" + 
							"  where a01.a0000 = f.a0000" + 
							"   and f.mntp00 = '"+mntp00+"'" + 
							"   and f.a0200 in" + 
							"       (select a0200 from a02 where a02.a0201b = '"+b0111+"')" ; 
					CommQuery cqbs=new CommQuery();
					List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
					JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
				    String length=String.valueOf(listCode.size());
				    
				    List<HashMap<String, Object>> listCode1=cqbs.getListBySQL(formersql.toString());
				    JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);
					this.getExecuteSG().addExecuteCode("AddSz("+updateunDataStoreObject.toString()+","+updateunDataStoreObject1.toString()+","+length.toString()+");");
					
				}
			}
			
			String sql1="select distinct c.b01id from  HZ_MNTP_ORG_QT c,fxyp " + 
					"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null ";
			@SuppressWarnings("unchecked")
			List<String> addsz= HBUtil.getHBSession().createSQLQuery(sql1).list();
			if(addsz.size()>0) {
				for(int i=0;i<addsz.size();i++) {
					String b01id=addsz.get(i);
					String sql="select *" + 
							"  from (select decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx," + 
							"               t.fxyp07 fxyp07," + 
							"               (select jgmc from  HZ_MNTP_ORG_QT  where b01id = '"+b01id+"') b0101," + 
							"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
							"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
							"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107,       " + 
							"               a01.qrzxl qrzxl," + 
							"               a01.zgxl  zgxl," + 
							"               t.fxyp02 a0215a," + 
							"               a01.a0192a,a01.dbwy,(select b0111 from b01 where b01id='"+b01id+"') b0111p," + 
							"               (select code_name" + 
							"                  from code_value" + 
							"                 where code_value.code_value = a01.a0141" + 
							"                   and code_type = 'GB4762') a0141," + 
							"               b.rybz,  " + 
							"               (select sortnum from gwsort g  where g.sortid=t.fxyp00 and mntp00 = '"+mntp00+"') sortnum, t.sortnum peoplesort," + 
							"                 (select count(1)" + 
							"                          from  v_mntp_gw_ry s" + 
							"                         where s.zwqc00=t.zwqc00" + 
							"                          and s.fxyp00=t.fxyp00" + 
							"                         group by s.zwqc00) peoplenum,"+
							"         (select fxyp00 from gwzwref f where f.fxyp00=t.fxyp00 ) bdfxyp00,t.zwqc01" + 
							"          from v_mntp_gw_ry t," + 
							"               a01," + 
							"               (select *" + 
							"                  from HZ_MNTP_BZ" + 
							"                 where mntp00 = '"+mntp00+"'" + 
							"                   and a01bztype = '2') b" + 
							"         where t.a0000 = a01.a0000(+)" + 
							"           and t.fxyp07 = 1" + 
							"           and t.mntp00 = '"+mntp00+"'" + 
							"           and t.b01id =" + 
							"               '"+b01id+"'" + 
							"           and b.a01bzid(+) = t.tp0100" + 
							"         order by t.fxyp00, t.sortnum)";
					
					String formersql="select a01.a0101,a01.a0000," + 
							"       decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
							"       decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
							"       a01.qrzxl qrzxl," + 
							"       a01.zgxl zgxl,a01.dbwy,(select b0111 from b01 where b01id='"+b01id+"') b0111p," + 
							"       (select code_name" + 
							"          from code_value" + 
							"         where code_value.code_value = a01.a0141" + 
							"           and code_type = 'GB4762') a0141," + 
							"       (select bz.rybz" + 
							"          from HZ_MNTP_BZ bz" + 
							"         where bz.a01bzid = f.a0200" + 
							"           and bz.mntp00 = '"+mntp00+"'" + 
							"           and a01bztype = '1') rybz," + 
							"       f.fxyp00,"+
						    "(select fxyp07 from fxyp x where x.a0200=f.a0200 and x.mntp00 = '"+mntp00+"' ) fxyp07" + 
							"   from GWZWREF f, a01" + 
							"  where a01.a0000 = f.a0000" + 
							"   and f.mntp00 = '"+mntp00+"'" + 
							"   and f.a0200 in" + 
							"       (select a0200 from a02 where a02.a0201b = '"+b01id+"')" ; 
					CommQuery cqbs=new CommQuery();
					List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
					JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
				    String length=String.valueOf(listCode.size());
				    List<HashMap<String, Object>> listCode1=cqbs.getListBySQL(formersql.toString());
				    JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);
					this.getExecuteSG().addExecuteCode("AddSz("+updateunDataStoreObject.toString()+","+updateunDataStoreObject1.toString()+","+length.toString()+");");
				}
			}
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			e.printStackTrace();
		}
		this.setNextEventName("init1");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("init1")
	public int init1() throws RadowException, AppException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		String checked=this.getPageElement("showAll").getValue();
		String checked1=this.getPageElement("showgrey").getValue();
		String addsql="";
		String listsql=" and not exists(select t.a0200 from hz_mntp_hhmd t where a02.a0200=t.a0200 " + 
				" minus " + 
				" select a01bzid from hz_mntp_bz where a01bztype ='1' and a01bzid=a02.a0200 and bmd='1'  and mntp00='"+mntp00+"') ";
		try {
			if("1".equals(checked)) {
				addsql=" and (select   gw.fxyp07  from hz_mntp_gw gw  where gw.a0200 = a02.a0200"
						+ "  and gw.mntp00 = '"+mntp00+"') is not null ";
			}
			if("1".equals(checked1)) {
				listsql=" and not exists(select t.a0200 from hz_mntp_hhmd t where a02.a0200=t.a0200 and t.mdtype='1' " + 
						" minus " + 
						" select a01bzid from hz_mntp_bz where a01bztype ='1' and a01bzid=a02.a0200 and bmd='1'  and mntp00='"+mntp00+"') ";
			}
			//���������������
			@SuppressWarnings("unchecked")
			List<String> tpqx= HBUtil.getHBSession().createSQLQuery("select b0111 from( select distinct  b0111,c.b0269 from b01 c,fxyp " + 
					" where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null " + 
					" and b0111 like '001.001.004%' and length(b0111)=15 " + 
					" order by c.b0269)").list();
			
			if(tpqx.size()>0) {
				for(int i=0;i<tpqx.size();i++) {
					String b0111=tpqx.get(i);
					
					
					String zrrxaddsql=" and (select   gw.fxyp07  from hz_mntp_gw gw  where gw.a0200 = a02.a0200"
							+ "  and gw.mntp00 = '"+mntp00+"') is not null ";
					
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
							"                   and a01.a0163 = '1'" + 
							"                   and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
							"                   and a02.a0201b in" + 
							"                       (select b0111" + 
							"                          from b01 b" + 
							"                         where b.b0131 in" + 
							"                               ('1001', '1003', '1004', '1005', '1006', '1007')" + 
							"                           and b.b0111 like '"+b0111+".%')" + zrrxaddsql+listsql+
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
							"          from (select   decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx" + 
							"      " + 
							"                  from v_mntp_gw_ry t," + 
							"                       a01," + 
							"                       (select *" + 
							"                          from HZ_MNTP_BZ" + 
							"                         where mntp00 = '"+mntp00+"'" + 
							"                           and a01bztype = '2') b" + 
							"                 where t.a0000 = a01.a0000(+)" + 
							"                   and t.fxyp07 = 1" + 
							"                   and t.mntp00 = '"+mntp00+"'" + 
							"                   and (select b0111 from b01 where t.b01id = b01.b01id) =" + 
							"                       '"+b0111+"'" + 
							"                   and b.a01bzid(+) = t.tp0100" + 
							"                 order by t.fxyp00, t.sortnum))" + 
							" order by zrrx ";
					List<String> zrrxList=HBUtil.getHBSession().createSQLQuery(zrrx1sql).list();
//					if(zrrx1.size()>=zrrx2.size()) {
//						zrrxList=zrrx1;
//					}else {
//						zrrxList=zrrx2;
//					}
					int lengthQX=0;
					//ѭ�������ϳ��Ļ�������
					if(zrrxList.size()>0) {
						for(int j=0;j<zrrxList.size();j++) {
							String zrrx=zrrxList.get(j);	
							String sql="select * from(" + 
									"select *" + 
									"  from (SELECT (select b0101 from b01 where b0111 = '"+b0111+"') b0101," + 
									"               decode((select b0131 from b01 where b0111 = a02.a0201b)," + 
									"                      '1001'," + 
									"                      '��ί'," + 
									"                      '1004'," + 
									"                      '����'," + 
									"                      '1003'," + 
									"                      '�˴�'," + 
									"                      '1005'," + 
									"                      '��Э'," + 
									"                      '1006'," + 
									"                      '��Ժ'," + 
									"                      '1007'," + 
									"                      '���Ժ','Z01','����') zrrx," + 
									"               (select gw.fxyp07" + 
									"                  from hz_mntp_gw gw" + 
									"                 where gw.a0200 = a02.a0200" + 
									"                   and gw.mntp00 = '"+mntp00+"'" + 
									"                   and fxyp07 = -1) fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
									"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
									"               a01.qrzxl qrzxl," + 
									"               a01.zgxl  zgxl, " + 
									"               a02.a0215a," + 
									"               a01.a0192a,a01.dbwy,'"+b0111+"' b0111p," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               b.rybz," + 
									"                (select sortnum from gwsort g where g.sortid=a02.a0200 and mntp00 = '"+mntp00+"') sortnum," + 
									"                null peoplesort," + 
									"                null peoplenum " + 
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
									"           and a01.a0163 = '1'" + 
									"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
									"           and a02.a0201b in" + 
									"               (select b0111" + 
									"                  from b01 b" + 
									"                 where b.b0131 in" + 
									"                       ('1001', '1003', '1004', '1005', '1006', '1007')" + 
									"                   and b.b0111 like '"+b0111+".%')" + addsql+listsql+
									"                 and not exists" + 
									"                 (select 1" + 
									"                          from GWZWREF gw" + 
									"                         where gw.a0200 = a02.a0200" + 
									"                           and gw.mntp00 =" + 
									"                               '"+mntp00+"')   " + 
									"           and (select b0131 from b01 where b0111 = a02.a0201b) = '"+zrrx+"'" + 
									"         ) " + 
									" union all " + 
									"select *" + 
									"  from (select (select b0101 from b01 where b0111 = '"+b0111+"') b0101," + 
									"               decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)," + 
									"                      '1001'," + 
									"                      '��ί'," + 
									"                      '1004'," + 
									"                      '����'," + 
									"                      '1003'," + 
									"                      '�˴�'," + 
									"                      '1005'," + 
									"                      '��Э'," + 
									"                      '1006'," + 
									"                      '��Ժ'," + 
									"                      '1007'," + 
									"                      '���Ժ','Z01','����') zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
									"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
									"               a01.qrzxl qrzxl," + 
									"               a01.zgxl  zgxl, " + 
									"               t.fxyp02 a0215a," + 
									"               a01.a0192a a0192a,a01.dbwy,'"+b0111+"' b0111p," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               b.rybz," + 
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
									"         where t.a0000 = a01.a0000(+)" + 
									"           and t.fxyp07 = 1" + 
									"           and t.mntp00 = '"+mntp00+"'" + 
									"           and (select b0111 from b01 where t.b01id = b01.b01id) =" + 
									"               '"+b0111+"'" + 
									"           and b.a01bzid(+) = t.tp0100" + 
									"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) = '"+zrrx+"'" + 
									"         order by t.fxyp00, t.sortnum)" + 
									")order by sortnum,peoplesort" ;
							CommQuery cqbs=new CommQuery();
							List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
							lengthQX+=listCode.size();	
						}
					}
					
					if(zrrxList.size()>0) {
						for(int j=0;j<zrrxList.size();j++) {
							String zrrx=zrrxList.get(j);
							String sql="select * from(" + 
									"select *" + 
									"  from (SELECT (select b0101 from b01 where b0111 = '"+b0111+"') b0101," + 
									"               decode((select b0131 from b01 where b0111 = a02.a0201b)," + 
									"                      '1001'," + 
									"                      '��ί'," + 
									"                      '1004'," + 
									"                      '����'," + 
									"                      '1003'," + 
									"                      '�˴�'," + 
									"                      '1005'," + 
									"                      '��Э'," + 
									"                      '1006'," + 
									"                      '��Ժ'," + 
									"                      '1007'," + 
									"                      '���Ժ','Z01','����') zrrx," + 
									"               (select gw.fxyp07" + 
									"                  from hz_mntp_gw gw" + 
									"                 where gw.a0200 = a02.a0200" + 
									"                   and gw.mntp00 = '"+mntp00+"'" + 
									"                   and fxyp07 = -1) fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
									"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
									"               a01.qrzxl qrzxl," + 
									"               a01.zgxl  zgxl, " + 
									"               a02.a0215a," + 
									"               a01.a0192a,a01.dbwy,'"+b0111+"' b0111p," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               b.rybz," + 
									"                (select sortnum from gwsort g where g.sortid=a02.a0200 and mntp00 = '"+mntp00+"') sortnum," + 
									"                null peoplesort," + 
									"                null peoplenum,'' bdfxyp00,null zwqc01 " + 
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
									"           and a01.a0163 = '1'" + 
									"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
									"           and a02.a0201b in" + 
									"               (select b0111" + 
									"                  from b01 b" + 
									"                 where b.b0131 in" + 
									"                       ('1001', '1003', '1004', '1005', '1006', '1007')" + 
									"                   and b.b0111 like '"+b0111+".%')" + addsql+listsql+
									"                 and not exists" + 
									"                 (select 1" + 
									"                          from GWZWREF gw" + 
									"                         where gw.a0200 = a02.a0200" + 
									"                           and gw.mntp00 =" + 
									"                               '"+mntp00+"')   " + 
									"           and (select b0131 from b01 where b0111 = a02.a0201b) = '"+zrrx+"'" + 
									"         ) " + 
									" union all " + 
									"select *" + 
									"  from (select (select b0101 from b01 where b0111 = '"+b0111+"') b0101," + 
									"               decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)," + 
									"                      '1001'," + 
									"                      '��ί'," + 
									"                      '1004'," + 
									"                      '����'," + 
									"                      '1003'," + 
									"                      '�˴�'," + 
									"                      '1005'," + 
									"                      '��Э'," + 
									"                      '1006'," + 
									"                      '��Ժ'," + 
									"                      '1007'," + 
									"                      '���Ժ','Z01','����') zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
									"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
									"               a01.qrzxl qrzxl," + 
									"               a01.zgxl  zgxl, " + 
									"               t.fxyp02 a0215a," + 
									"               a01.a0192a a0192a,a01.dbwy,'"+b0111+"' b0111p," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               b.rybz," + 
									"                (select sortnum from gwsort g  where g.sortid=t.fxyp00 and mntp00 = '"+mntp00+"') sortnum," + 
									"                t.sortnum peoplesort," + 
									"                 (select count(1)" + 
									"                          from  v_mntp_gw_ry s" + 
									"                         where s.zwqc00=t.zwqc00" + 
									"                          and s.fxyp00=t.fxyp00" + 
									"                         group by s.zwqc00) peoplenum,"+
									"         (select fxyp00 from gwzwref f where f.fxyp00=t.fxyp00 ) bdfxyp00,t.zwqc01" + 
									"          from v_mntp_gw_ry t," + 
									"               a01," + 
									"               (select *" + 
									"                  from HZ_MNTP_BZ" + 
									"                 where mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '2') b" + 
									"         where t.a0000 = a01.a0000(+)" + 
									"           and t.fxyp07 = 1" + 
									"           and t.mntp00 = '"+mntp00+"'" + 
									"           and (select b0111 from b01 where t.b01id = b01.b01id) =" + 
									"               '"+b0111+"'" + 
									"           and b.a01bzid(+) = t.tp0100" + 
									"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) = '"+zrrx+"'" + 
									"         order by t.fxyp00, t.sortnum)" + 
									")order by sortnum,peoplesort" ;
							
							String a0201b="";
							if("1001".equals(zrrx)) {
								a0201b=b0111+".001";
							}else if("1003".equals(zrrx)) {
								a0201b=b0111+".002";
							}else if("1004".equals(zrrx)) {
								a0201b=b0111+".003";
							}else if("1005".equals(zrrx)) {
								a0201b=b0111+".004";
							}else if("1006".equals(zrrx)) {
								a0201b=b0111+".005";
							}else if("1007".equals(zrrx)) {
								a0201b=b0111+".006";
							}
							
							String formersql="select a01.a0101,a01.a0000," + 
									"       decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"       decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
									"       a01.qrzxl qrzxl," + 
									"       a01.zgxl zgxl,a01.dbwy,'"+b0111+"' b0111p," + 
									"       (select code_name" + 
									"          from code_value" + 
									"         where code_value.code_value = a01.a0141" + 
									"           and code_type = 'GB4762') a0141," + 
									"       (select bz.rybz" + 
									"          from HZ_MNTP_BZ bz" + 
									"         where bz.a01bzid = f.a0200" + 
									"           and bz.mntp00 = '"+mntp00+"'" + 
									"           and a01bztype = '1') rybz," + 
									"       f.fxyp00,"+
								    "(select fxyp07 from fxyp x where x.a0200=f.a0200 and x.mntp00 = '"+mntp00+"' ) fxyp07" + 
									"   from GWZWREF f, a01" + 
									"  where a01.a0000 = f.a0000" + 
									"   and f.mntp00 = '"+mntp00+"'" + 
									"   and f.a0200 in" + 
									"       (select a0200 from a02 where a02.a0201b = '"+a0201b+"')" ; 
							
							CommQuery cqbs1=new CommQuery();
							List<HashMap<String, Object>> listCode1=cqbs1.getListBySQL(sql.toString());
							JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);	
							String totallength=String.valueOf(lengthQX);
							String line=String.valueOf(j);
							String length=String.valueOf(listCode1.size());
							
							List<HashMap<String, Object>> listCode2=cqbs1.getListBySQL(formersql.toString());
							JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(listCode2);	
							this.getExecuteSG().addExecuteCode("AddQx("+updateunDataStoreObject1.toString()+","+updateunDataStoreObject2.toString()+","+totallength+","+length+","+line+");");
						}
					}	
				}
			}
			
			//������������
			@SuppressWarnings("unchecked")
			List<String> addqx= HBUtil.getHBSession().createSQLQuery("select distinct c.b01id from  HZ_MNTP_ORG_QXS c,fxyp " + 
					"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null " ).list();
			
			
			if(addqx.size()>0) {
				for(int i=0;i<addqx.size();i++) {
					String b01id=addqx.get(i);
					String zrrxsql="       select distinct zrrx from ( select *" + 
							"          from (select   decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx" + 
							"                  from v_mntp_gw_ry t," + 
							"                       a01," + 
							"                       (select *" + 
							"                          from HZ_MNTP_BZ" + 
							"                         where mntp00 = '"+mntp00+"'" + 
							"                           and a01bztype = '2') b" + 
							"                 where t.a0000 = a01.a0000(+)" + 
							"                   and t.fxyp07 = 1" + 
							"                   and t.mntp00 = '"+mntp00+"'" + 
							"                   and t.b01id =" + 
							"                       '"+b01id+"'" + 
							"                   and b.a01bzid(+) = t.tp0100" + 
							"                 order by t.fxyp00, t.sortnum)) order by zrrx" ;
					
					
					List<String> zrrxList=HBUtil.getHBSession().createSQLQuery(zrrxsql).list();
					int lengthQX=0;
					if(zrrxList.size()>0) {
						for(int j=0;j<zrrxList.size();j++) {
							String zrrx=zrrxList.get(j);	
							String sql="select * from(" + 
									"select *" + 
									"  from (select (select b0101 from HZ_MNTP_ORG_QXS where b01id = '"+b01id+"') b0101," + 
									"               decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)," + 
									"                      '1001'," + 
									"                      '��ί'," + 
									"                      '1004'," + 
									"                      '����'," + 
									"                      '1003'," + 
									"                      '�˴�'," + 
									"                      '1005'," + 
									"                      '��Э'," + 
									"                      '1006'," + 
									"                      '��Ժ'," + 
									"                      '1007'," + 
									"                      '���Ժ','Z01','����') zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
									"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
									"               a01.qrzxl qrzxl," + 
									"               a01.zgxl  zgxl, " + 
									"               t.fxyp02 a0215a," + 
									"               a01.a0192a a0192a,a01.dbwy,(select b0111 from b01 where b01id='"+b01id+"') b0111p," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               b.rybz," + 
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
									"         where t.a0000 = a01.a0000(+)" + 
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
						
						for(int j=0;j<zrrxList.size();j++) {
							String zrrx=zrrxList.get(j);
							String sql="select * from(" + 
									"select *" + 
									"  from (select (select b0101 from HZ_MNTP_ORG_QXS where b01id = '"+b01id+"') b0101," + 
									"               decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)," + 
									"                      '1001'," + 
									"                      '��ί'," + 
									"                      '1004'," + 
									"                      '����'," + 
									"                      '1003'," + 
									"                      '�˴�'," + 
									"                      '1005'," + 
									"                      '��Э'," + 
									"                      '1006'," + 
									"                      '��Ժ'," + 
									"                      '1007'," + 
									"                      '���Ժ','Z01','����') zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
									"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
									"               a01.qrzxl qrzxl," + 
									"               a01.zgxl  zgxl, " + 
									"               t.fxyp02 a0215a," + 
									"               a01.a0192a a0192a,a01.dbwy,(select b0111 from b01 where b01id='"+b01id+"') b0111p," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               b.rybz," + 
									"                (select sortnum from gwsort g  where g.sortid=t.fxyp00 and mntp00 = '"+mntp00+"') sortnum," + 
									"                t.sortnum peoplesort," + 
									"                 (select count(1)" + 
									"                          from  v_mntp_gw_ry s" + 
									"                         where s.zwqc00=t.zwqc00" + 
									"                          and s.fxyp00=t.fxyp00" + 
									"                         group by s.zwqc00) peoplenum,"+
									"         (select fxyp00 from gwzwref f where f.fxyp00=t.fxyp00 ) bdfxyp00,t.zwqc01" + 
									"          from v_mntp_gw_ry t," + 
									"               a01," + 
									"               (select *" + 
									"                  from HZ_MNTP_BZ" + 
									"                 where mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '2') b" + 
									"         where t.a0000 = a01.a0000(+)" + 
									"           and t.fxyp07 = 1" + 
									"           and t.mntp00 = '"+mntp00+"'" + 
									"           and t.b01id  =" + 
									"               '"+b01id+"'" + 
									"           and b.a01bzid(+) = t.tp0100" + 
									"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) = '"+zrrx+"'" + 
									"         order by t.fxyp00, t.sortnum)" + 
									")order by sortnum,peoplesort" ;

							
							String formersql="select a01.a0101,a01.a0000," + 
									"       decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"       decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
									"       a01.qrzxl qrzxl," + 
									"       a01.zgxl zgxl,a01.dbwy,(select b0111 from b01 where b01id='"+b01id+"') b0111p," + 
									"       (select code_name" + 
									"          from code_value" + 
									"         where code_value.code_value = a01.a0141" + 
									"           and code_type = 'GB4762') a0141," + 
									"       (select bz.rybz" + 
									"          from HZ_MNTP_BZ bz" + 
									"         where bz.a01bzid = f.a0200" + 
									"           and bz.mntp00 = '"+mntp00+"'" + 
									"           and a01bztype = '1') rybz," + 
									"       f.fxyp00,"+
								    "(select fxyp07 from fxyp x where x.a0200=f.a0200 and x.mntp00 = '"+mntp00+"' ) fxyp07" + 
									"   from GWZWREF f, a01" + 
									"  where a01.a0000 = f.a0000" + 
									"   and f.mntp00 = '"+mntp00+"'" + 
									"   and f.a0200 in" + 
									"       (select a0200 from a02 where a02.a0201b = '"+b01id+"')" ; 
							
							CommQuery cqbs1=new CommQuery();
							List<HashMap<String, Object>> listCode1=cqbs1.getListBySQL(sql.toString());
							JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);	
							String totallength=String.valueOf(lengthQX);
							String line=String.valueOf(j);
							String length=String.valueOf(listCode1.size());
							
							List<HashMap<String, Object>> listCode2=cqbs1.getListBySQL(formersql.toString());
							JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(listCode2);	
							this.getExecuteSG().addExecuteCode("AddQx("+updateunDataStoreObject1.toString()+","+updateunDataStoreObject2.toString()+","+totallength+","+length+","+line+");");
						}
						
					}
				}
			}
			this.getExecuteSG().addExecuteCode("bindRMB()");
			this.setNextEventName("dwxxadd");
			
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
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
		String checked1=this.getPageElement("showgrey").getValue();
		String addsql="";	
		String listsql=" and not exists(select t.a0200 from hz_mntp_hhmd t where a02.a0200=t.a0200 " + 
				" minus " + 
				" select a01bzid from hz_mntp_bz where a01bztype ='1' and a01bzid=a02.a0200 and bmd='1'  and mntp00='"+mntp00+"') ";
		try {
			if("1".equals(checked)) {
				addsql=" and (select   gw.fxyp07  from hz_mntp_gw gw  where gw.a0200 = a02.a0200"
						+ "  and gw.mntp00 = '"+mntp00+"') is not null ";
			}
			if("1".equals(checked1)) {
				listsql=" and not exists(select t.a0200 from hz_mntp_hhmd t where a02.a0200=t.a0200 and t.mdtype='1' " + 
						" minus " + 
						" select a01bzid from hz_mntp_bz where a01bztype ='1' and a01bzid=a02.a0200 and bmd='1'  and mntp00='"+mntp00+"') ";
			}
			if(b0101.length()>0) {
				//����ѡ�еĻ����Լ��������ʣ�����������
				ArrayList<String> b01info= new ArrayList<String>();
				for(String string:b0111s) {
					if(string.indexOf("#")==-1){
						b01info.add(string);
					}			
				}
				//����ѡ�еĻ���������������
				ArrayList<String> b0111all= new ArrayList<String>();
				for(String string:b0111s) {
					if(string.indexOf("@")==-1 && string.indexOf("#")==-1) {
						b0111all.add(string);
					}else if(string.indexOf("#")==-1){
						b0111all.add(string.split("@")[0]);
					}
				}
				//����ѡ�еĻ���ȥ�أ�����������
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
	//						if(zrrx1.size()>=zrrx2.size()) {
	//							zrrxList=zrrx1;
	//						}else {
	//							zrrxList=zrrx2;
	//						}
							int lengthQX=0;
							//ѭ�������ϳ��Ļ�������
							if(zrrxList.size()>0) {
								for(int j=0;j<zrrxList.size();j++) {
									String zrrx=zrrxList.get(j);
									String sql="select * from(" + 
											"select *" + 
											"  from (SELECT (select b0101 from b01 where b0111 = '"+b0111+"') b0101," + 
											"               decode((select b0131 from b01 where b0111 = a02.a0201b)," + 
											"                      '1001'," + 
											"                      '��ί'," + 
											"                      '1004'," + 
											"                      '����'," + 
											"                      '1003'," + 
											"                      '�˴�'," + 
											"                      '1005'," + 
											"                      '��Э'," + 
											"                      '1006'," + 
											"                      '��Ժ'," + 
											"                      '1007'," + 
											"                      '���Ժ','Z01','����') zrrx," + 
											"               (select gw.fxyp07" + 
											"                  from hz_mntp_gw gw" + 
											"                 where gw.a0200 = a02.a0200" + 
											"                   and gw.mntp00 = '"+mntp00+"'" + 
											"                   and fxyp07 = -1) fxyp07," + 
											"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
											"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
											"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
											"               a01.qrzxl qrzxl," + 
											"               a01.zgxl  zgxl, " + 
											"               a02.a0215a," + 
											"               a01.a0192a,a01.dbwy,'"+b0111+"' b0111p," + 
											"               (select code_name" + 
											"                  from code_value" + 
											"                 where code_value.code_value = a01.a0141" + 
											"                   and code_type = 'GB4762') a0141," + 
											"               b.rybz," + 
											"                (select sortnum from gwsort g where g.sortid=a02.a0200 and mntp00 = '"+mntp00+"') sortnum," + 
											"                null peoplesort," + 
											"                null peoplenum " + 
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
											"           and a01.a0163 = '1'" + 
											"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
											"           and a02.a0201b in" + 
											"               (select b0111" + 
											"                  from b01 b" + 
											"                 where b.b0131 in" + 
											"                       ('1001', '1003', '1004', '1005', '1006', '1007')" + 
											"                   and b.b0111 like '"+b0111+".%')"+ addsql+ listsql+
											"                 and not exists" + 
											"                 (select 1" + 
											"                          from GWZWREF gw" + 
											"                         where gw.a0200 = a02.a0200" + 
											"                           and gw.mntp00 =" + 
											"                               '"+mntp00+"')   " + 
											"           and (select b0131 from b01 where b0111 = a02.a0201b) = '"+zrrx+"'" + 
											"         ) " + 
											" union all " + 
											"select *" + 
											"  from (select (select b0101 from b01 where b0111 = '"+b0111+"') b0101," + 
											"               decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)," + 
											"                      '1001'," + 
											"                      '��ί'," + 
											"                      '1004'," + 
											"                      '����'," + 
											"                      '1003'," + 
											"                      '�˴�'," + 
											"                      '1005'," + 
											"                      '��Э'," + 
											"                      '1006'," + 
											"                      '��Ժ'," + 
											"                      '1007'," + 
											"                      '���Ժ','Z01','����') zrrx," + 
											"               t.fxyp07 fxyp07," + 
											"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
											"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
											"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
											"               a01.qrzxl qrzxl," + 
											"               a01.zgxl  zgxl, " + 
											"               t.fxyp02 a0215a," + 
											"               a01.a0192a a0192a,a01.dbwy,'"+b0111+"' b0111p," + 
											"               (select code_name" + 
											"                  from code_value" + 
											"                 where code_value.code_value = a01.a0141" + 
											"                   and code_type = 'GB4762') a0141," + 
											"               b.rybz," + 
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
											"         where t.a0000 = a01.a0000(+)" + 
											"           and t.fxyp07 = 1" + 
											"           and t.mntp00 = '"+mntp00+"'" + 
											"           and (select b0111 from b01 where t.b01id = b01.b01id) =" + 
											"               '"+b0111+"'" + 
											"           and b.a01bzid(+) = t.tp0100" + 
											"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) = '"+zrrx+"'" + 
											"         order by t.fxyp00, t.sortnum)" + 
											")order by sortnum,peoplesort" ;
									CommQuery cqbs=new CommQuery();
									List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
									lengthQX+=listCode.size();	
								}
							}
							
							if(zrrxList.size()>0) {
								for(int j=0;j<zrrxList.size();j++) {
									String zrrx=zrrxList.get(j);
									String sql="select * from(" + 
											"select *" + 
											"  from (SELECT (select b0101 from b01 where b0111 = '"+b0111+"') b0101," + 
											"               decode((select b0131 from b01 where b0111 = a02.a0201b)," + 
											"                      '1001'," + 
											"                      '��ί'," + 
											"                      '1004'," + 
											"                      '����'," + 
											"                      '1003'," + 
											"                      '�˴�'," + 
											"                      '1005'," + 
											"                      '��Э'," + 
											"                      '1006'," + 
											"                      '��Ժ'," + 
											"                      '1007'," + 
											"                      '���Ժ','Z01','����') zrrx," + 
											"               (select gw.fxyp07" + 
											"                  from hz_mntp_gw gw" + 
											"                 where gw.a0200 = a02.a0200" + 
											"                   and gw.mntp00 = '"+mntp00+"'" + 
											"                   and fxyp07 = -1) fxyp07," + 
											"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
											"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
											"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
											"               a01.qrzxl qrzxl," + 
											"               a01.zgxl  zgxl, " + 
											"               a02.a0215a," + 
											"               a01.a0192a,a01.dbwy,'"+b0111+"' b0111p," + 
											"               (select code_name" + 
											"                  from code_value" + 
											"                 where code_value.code_value = a01.a0141" + 
											"                   and code_type = 'GB4762') a0141," + 
											"               b.rybz," + 
											"                (select sortnum from gwsort g where g.sortid=a02.a0200 and mntp00 = '"+mntp00+"') sortnum," + 
											"                null peoplesort," + 
											"                null peoplenum, '' bdfxyp00,null zwqc01 " + 
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
											"           and a01.a0163 = '1'" + 
											"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
											"           and a02.a0201b in" + 
											"               (select b0111" + 
											"                  from b01 b" + 
											"                 where b.b0131 in" + 
											"                       ('1001', '1003', '1004', '1005', '1006', '1007')" + 
											"                   and b.b0111 like '"+b0111+".%')" + addsql+ listsql+
											"                 and not exists" + 
											"                 (select 1" + 
											"                          from GWZWREF gw" + 
											"                         where gw.a0200 = a02.a0200" + 
											"                           and gw.mntp00 =" + 
											"                               '"+mntp00+"')   " + 
											"           and (select b0131 from b01 where b0111 = a02.a0201b) = '"+zrrx+"'" + 
											"         ) " + 
											" union all " + 
											"select *" + 
											"  from (select (select b0101 from b01 where b0111 = '"+b0111+"') b0101," + 
											"               decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)," + 
											"                      '1001'," + 
											"                      '��ί'," + 
											"                      '1004'," + 
											"                      '����'," + 
											"                      '1003'," + 
											"                      '�˴�'," + 
											"                      '1005'," + 
											"                      '��Э'," + 
											"                      '1006'," + 
											"                      '��Ժ'," + 
											"                      '1007'," + 
											"                      '���Ժ','Z01','����') zrrx," + 
											"               t.fxyp07 fxyp07," + 
											"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
											"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
											"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
											"               a01.qrzxl qrzxl," + 
											"               a01.zgxl  zgxl, " + 
											"               t.fxyp02 a0215a," + 
											"               a01.a0192a a0192a,a01.dbwy,'"+b0111+"' b0111p," + 
											"               (select code_name" + 
											"                  from code_value" + 
											"                 where code_value.code_value = a01.a0141" + 
											"                   and code_type = 'GB4762') a0141," + 
											"               b.rybz," + 
											"                (select sortnum from gwsort g  where g.sortid=t.fxyp00 and mntp00 = '"+mntp00+"') sortnum," + 
											"                t.sortnum peoplesort," + 
											"                 (select count(1)" + 
											"                          from  v_mntp_gw_ry s" + 
											"                         where s.zwqc00=t.zwqc00" + 
											"                          and s.fxyp00=t.fxyp00" + 
											"                         group by s.zwqc00) peoplenum,"+
											"         (select fxyp00 from gwzwref f where f.fxyp00=t.fxyp00 ) bdfxyp00,t.zwqc01" + 
											"          from v_mntp_gw_ry t," + 
											"               a01," + 
											"               (select *" + 
											"                  from HZ_MNTP_BZ" + 
											"                 where mntp00 = '"+mntp00+"'" + 
											"                   and a01bztype = '2') b" + 
											"         where t.a0000 = a01.a0000(+)" + 
											"           and t.fxyp07 = 1" + 
											"           and t.mntp00 = '"+mntp00+"'" + 
											"           and (select b0111 from b01 where t.b01id = b01.b01id) =" + 
											"               '"+b0111+"'" + 
											"           and b.a01bzid(+) = t.tp0100" + 
											"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) = '"+zrrx+"'" + 
											"         order by t.fxyp00, t.sortnum)" + 
											")order by sortnum,peoplesort" ;
									
									String a0201b="";
									if("1001".equals(zrrx)) {
										a0201b=b0111+".001";
									}else if("1003".equals(zrrx)) {
										a0201b=b0111+".002";
									}else if("1004".equals(zrrx)) {
										a0201b=b0111+".003";
									}else if("1005".equals(zrrx)) {
										a0201b=b0111+".004";
									}else if("1006".equals(zrrx)) {
										a0201b=b0111+".005";
									}else if("1007".equals(zrrx)) {
										a0201b=b0111+".006";
									}
									
									String formersql="select a01.a0101,a01.a0000," + 
											"       decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
											"       decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
											"       a01.qrzxl qrzxl," + 
											"       a01.zgxl zgxl,a01.dbwy,'"+b0111+"' b0111p," + 
											"       (select code_name" + 
											"          from code_value" + 
											"         where code_value.code_value = a01.a0141" + 
											"           and code_type = 'GB4762') a0141," + 
											"       (select bz.rybz" + 
											"          from HZ_MNTP_BZ bz" + 
											"         where bz.a01bzid = f.a0200" + 
											"           and bz.mntp00 = '"+mntp00+"'" + 
											"           and a01bztype = '1') rybz," + 
											"       f.fxyp00,"+
										    "(select fxyp07 from fxyp x where x.a0200=f.a0200 and x.mntp00 = '"+mntp00+"' ) fxyp07" + 
											"   from GWZWREF f, a01" + 
											"  where a01.a0000 = f.a0000" + 
											"   and f.mntp00 = '"+mntp00+"'" + 
											"   and f.a0200 in" + 
											"       (select a0200 from a02 where a02.a0201b = '"+a0201b+"')" ; 
									
									
									CommQuery cqbs1=new CommQuery();
									List<HashMap<String, Object>> listCode1=cqbs1.getListBySQL(sql.toString());
									JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);	
									String totallength=String.valueOf(lengthQX);
									String line=String.valueOf(j);
									String length=String.valueOf(listCode1.size());
									
									List<HashMap<String, Object>> listCode2=cqbs1.getListBySQL(formersql.toString());
									JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(listCode2);	
									this.getExecuteSG().addExecuteCode("AddQx("+updateunDataStoreObject1.toString()+","+updateunDataStoreObject2.toString()+","+totallength+","+length+","+line+");");
	
								}
							}
						}else{
							String sql="select * from(" + 
									"select *" + 
									"  from (select a02.a0201e zrrx," + 
									"               (select gw.fxyp07" + 
									"                  from hz_mntp_gw gw" + 
									"                 where gw.a0200 = a02.a0200" + 
									"                   and gw.mntp00 = '"+mntp00+"'" + 
									"                   and fxyp07 = -1) fxyp07," + 
									"               (select b0101 from b01 where b01.b0111 = a02.a0201b) b0101," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
									"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107,              " + 
									"               a01.qrzxl qrzxl," + 
									"               a01.zgxl  zgxl,               " + 
									"               a02.a0215a," + 
									"               a01.a0192a,a01.dbwy,'"+b0111+"' b0111p," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               (select bz.rybz" + 
									"                  from HZ_MNTP_BZ bz" + 
									"                 where bz.a01bzid = a02.a0200" + 
									"                   and bz.mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '1') rybz,                   " + 
									"               (select sortnum from gwsort g where g.sortid=a02.a0200 and mntp00 = '"+mntp00+"') sortnum,null peoplesort," + 
									"                null peoplenum,'' bdfxyp00,null zwqc01               " + 
									"          from a01, a02" + 
									"         where a01.A0000 = a02.a0000" + 
									"           AND a02.a0281 = 'true'" + 
									"           AND a02.a0255 = '1'" + 
									"           and a01.a0163='1'" + 
									"           and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
									"           and a02.a0201b = '"+b0111+"'" + addsql+listsql+
									"           and not exists" + 
									"                 (select 1" + 
									"                          from GWZWREF gw" + 
									"                         where gw.a0200 = a02.a0200" + 
									"                           and gw.mntp00 =" + 
									"                               '"+mntp00+"') )                 " + 
									" union all " + 
									"select *" + 
									"  from (select decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"               (select b0101 from b01 where b0111 = '"+b0111+"') b0101," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
									"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107,       " + 
									"               a01.qrzxl qrzxl," + 
									"               a01.zgxl  zgxl," + 
									"               t.fxyp02 a0215a," + 
									"               a01.a0192a,a01.dbwy,'"+b0111+"' b0111p," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               b.rybz,  " + 
									"               (select sortnum from gwsort g  where g.sortid=t.fxyp00 and mntp00 = '"+mntp00+"') sortnum, t.sortnum peoplesort," + 
									"                 (select count(1)" + 
									"                          from  v_mntp_gw_ry s" + 
									"                         where s.zwqc00=t.zwqc00" + 
									"                          and s.fxyp00=t.fxyp00" + 
									"                         group by s.zwqc00) peoplenum,"+
									"         (select fxyp00 from gwzwref f where f.fxyp00=t.fxyp00 ) bdfxyp00,t.zwqc01" + 
									"          from v_mntp_gw_ry t," + 
									"               a01," + 
									"               (select *" + 
									"                  from HZ_MNTP_BZ" + 
									"                 where mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '2') b" + 
									"         where t.a0000 = a01.a0000(+)" + 
									"           and t.fxyp07 = 1" + 
									"           and t.mntp00 = '"+mntp00+"'" + 
									"           and (select b0111 from b01 where t.b01id = b01.b01id) =" + 
									"               '"+b0111+"'" + 
									"           and b.a01bzid(+) = t.tp0100" + 
									"         order by t.fxyp00, t.sortnum)" + 
									")order by sortnum,peoplesort";
							
							String formersql="select a01.a0101,a01.a0000," + 
									"       decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"       decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
									"       a01.qrzxl qrzxl," + 
									"       a01.zgxl zgxl,a01.dbwy,'"+b0111+"' b0111p," + 
									"       (select code_name" + 
									"          from code_value" + 
									"         where code_value.code_value = a01.a0141" + 
									"           and code_type = 'GB4762') a0141," + 
									"       (select bz.rybz" + 
									"          from HZ_MNTP_BZ bz" + 
									"         where bz.a01bzid = f.a0200" + 
									"           and bz.mntp00 = '"+mntp00+"'" + 
									"           and a01bztype = '1') rybz," + 
									"       f.fxyp00,"+
								    "(select fxyp07 from fxyp x where x.a0200=f.a0200 and x.mntp00 = '"+mntp00+"' ) fxyp07" + 
									"   from GWZWREF f, a01" + 
									"  where a01.a0000 = f.a0000" + 
									"   and f.mntp00 = '"+mntp00+"'" + 
									"   and f.a0200 in" + 
									"       (select a0200 from a02 where a02.a0201b = '"+b0111+"')" ; 
							
							CommQuery cqbs=new CommQuery();
							List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
							JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
						    String length=String.valueOf(listCode.size());
						    
						    List<HashMap<String, Object>> listCode1=cqbs.getListBySQL(formersql.toString());
						    JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);
							this.getExecuteSG().addExecuteCode("AddSz("+updateunDataStoreObject.toString()+","+updateunDataStoreObject1.toString()+","+length.toString()+");");
						}
					}
				}
				
				//������������
				this.setNextEventName("queryadd");
				
			}else {
				this.setNextEventName("initX");
			}
			
		}catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
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
			//�������������Լ��������ʵ���Ϣ
			ArrayList<String> b01info= new ArrayList<String>();
			for(String string:b0111s) {
				if(string.indexOf("#")!=-1){
					b01info.add(string);
				}			
			}
			
			//����ѡ�е���������
			ArrayList<String> b01idsz= new ArrayList<String>();
			for(String string:b0111s) {
				if(string.indexOf("#")!=-1 ) {
					if("1".equals(string.split("#")[1])) {
						b01idsz.add(string.split("#")[0]);
					}		
				}
			}
			
			//��ֱѭ��
			if(b01idsz.size()>0) {
				for(int i=0;i<b01idsz.size();i++) {
					String b01id=b01idsz.get(i);
					String sql="select *" + 
							"  from (select decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) zrrx," + 
							"               t.fxyp07 fxyp07," + 
							"               (select jgmc from  HZ_MNTP_ORG_QT  where b01id = '"+b01id+"') b0101," + 
							"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
							"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
							"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107,       " + 
							"               a01.qrzxl qrzxl," + 
							"               a01.zgxl  zgxl," + 
							"               t.fxyp02 a0215a," + 
							"               a01.a0192a,a01.dbwy,(select b0111 from b01 where b01id='"+b01id+"') b0111p," + 
							"               (select code_name" + 
							"                  from code_value" + 
							"                 where code_value.code_value = a01.a0141" + 
							"                   and code_type = 'GB4762') a0141," + 
							"               b.rybz,  " + 
							"               (select sortnum from gwsort g  where g.sortid=t.fxyp00 and mntp00 = '"+mntp00+"') sortnum, t.sortnum peoplesort," + 
							"                 (select count(1)" + 
							"                          from  v_mntp_gw_ry s" + 
							"                         where s.zwqc00=t.zwqc00" + 
							"                          and s.fxyp00=t.fxyp00" + 
							"                         group by s.zwqc00) peoplenum,"+
							"         (select fxyp00 from gwzwref f where f.fxyp00=t.fxyp00 ) bdfxyp00,t.zwqc01" + 
							"          from v_mntp_gw_ry t," + 
							"               a01," + 
							"               (select *" + 
							"                  from HZ_MNTP_BZ" + 
							"                 where mntp00 = '"+mntp00+"'" + 
							"                   and a01bztype = '2') b" + 
							"         where t.a0000 = a01.a0000(+)" + 
							"           and t.fxyp07 = 1" + 
							"           and t.mntp00 = '"+mntp00+"'" + 
							"           and t.b01id =" + 
							"               '"+b01id+"'" + 
							"           and b.a01bzid(+) = t.tp0100" + 
							"         order by t.fxyp00, t.sortnum)";
					
					String formersql="select a01.a0101,a01.a0000," + 
							"       decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
							"       decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
							"       a01.qrzxl qrzxl," + 
							"       a01.zgxl zgxl,a01.dbwy,(select b0111 from b01 where b01id='"+b01id+"') b0111p," + 
							"       (select code_name" + 
							"          from code_value" + 
							"         where code_value.code_value = a01.a0141" + 
							"           and code_type = 'GB4762') a0141," + 
							"       (select bz.rybz" + 
							"          from HZ_MNTP_BZ bz" + 
							"         where bz.a01bzid = f.a0200" + 
							"           and bz.mntp00 = '"+mntp00+"'" + 
							"           and a01bztype = '1') rybz," + 
							"       f.fxyp00,"+
						    "(select fxyp07 from fxyp x where x.a0200=f.a0200 and x.mntp00 = '"+mntp00+"' ) fxyp07" + 
							"   from GWZWREF f, a01" + 
							"  where a01.a0000 = f.a0000" + 
							"   and f.mntp00 = '"+mntp00+"'" + 
							"   and f.a0200 in" + 
							"       (select a0200 from a02 where a02.a0201b = '"+b01id+"')" ; 
					CommQuery cqbs=new CommQuery();
					List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
					JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
				    String length=String.valueOf(listCode.size());
				    List<HashMap<String, Object>> listCode1=cqbs.getListBySQL(formersql.toString());
				    JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);
					this.getExecuteSG().addExecuteCode("AddSz("+updateunDataStoreObject.toString()+","+updateunDataStoreObject1.toString()+","+length.toString()+");");
				}
			}
			
			
			//����ѡ�е���������
			ArrayList<String> b01idqx= new ArrayList<String>();
			for(String string:b0111s) {
				if(string.indexOf("#")!=-1 ) {
					if("2".equals(string.split("#")[1]) && !b01idqx.contains(string.split("#")[0])) {
						b01idqx.add(string.split("#")[0]);
					}		
				}
			}
			//����ѭ��
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
									"                      '��ί'," + 
									"                      '1004'," + 
									"                      '����'," + 
									"                      '1003'," + 
									"                      '�˴�'," + 
									"                      '1005'," + 
									"                      '��Э'," + 
									"                      '1006'," + 
									"                      '��Ժ'," + 
									"                      '1007'," + 
									"                      '���Ժ','Z01','����') zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
									"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
									"               a01.qrzxl qrzxl," + 
									"               a01.zgxl  zgxl, " + 
									"               t.fxyp02 a0215a," + 
									"               a01.a0192a a0192a,a01.dbwy,(select b0111 from b01 where b01id='"+b01id+"') b0111p," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               b.rybz," + 
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
									"         where t.a0000 = a01.a0000(+)" + 
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
						
						for(int j=0;j<zrrxList.size();j++) {
							String zrrx=zrrxList.get(j);
							String sql="select * from(" + 
									"select *" + 
									"  from (select (select b0101 from HZ_MNTP_ORG_QXS where b01id = '"+b01id+"') b0101," + 
									"               decode(decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e)," + 
									"                      '1001'," + 
									"                      '��ί'," + 
									"                      '1004'," + 
									"                      '����'," + 
									"                      '1003'," + 
									"                      '�˴�'," + 
									"                      '1005'," + 
									"                      '��Э'," + 
									"                      '1006'," + 
									"                      '��Ժ'," + 
									"                      '1007'," + 
									"                      '���Ժ','Z01','����') zrrx," + 
									"               t.fxyp07 fxyp07," + 
									"               GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a01.a0000," + 
									"               decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"               decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
									"               a01.qrzxl qrzxl," + 
									"               a01.zgxl  zgxl, " + 
									"               t.fxyp02 a0215a," + 
									"               a01.a0192a a0192a,a01.dbwy,(select b0111 from b01 where b01id='"+b01id+"') b0111p," + 
									"               (select code_name" + 
									"                  from code_value" + 
									"                 where code_value.code_value = a01.a0141" + 
									"                   and code_type = 'GB4762') a0141," + 
									"               b.rybz," + 
									"                (select sortnum from gwsort g  where g.sortid=t.fxyp00 and mntp00 = '"+mntp00+"') sortnum," + 
									"                t.sortnum peoplesort," + 
									"                 (select count(1)" + 
									"                          from  v_mntp_gw_ry s" + 
									"                         where s.zwqc00=t.zwqc00" + 
									"                          and s.fxyp00=t.fxyp00" + 
									"                         group by s.zwqc00) peoplenum,"+
									"         (select fxyp00 from gwzwref f where f.fxyp00=t.fxyp00 ) bdfxyp00,t.zwqc01" + 
									"          from v_mntp_gw_ry t," + 
									"               a01," + 
									"               (select *" + 
									"                  from HZ_MNTP_BZ" + 
									"                 where mntp00 = '"+mntp00+"'" + 
									"                   and a01bztype = '2') b" + 
									"         where t.a0000 = a01.a0000(+)" + 
									"           and t.fxyp07 = 1" + 
									"           and t.mntp00 = '"+mntp00+"'" + 
									"           and t.b01id  =" + 
									"               '"+b01id+"'" + 
									"           and b.a01bzid(+) = t.tp0100" + 
									"           and decode(length(t.fxyp06), 4 , t.fxyp06,3,t.fxyp06, t.a0201e) = '"+zrrx+"'" + 
									"         order by t.fxyp00, t.sortnum)" + 
									")order by sortnum,peoplesort" ;

							
							String formersql="select a01.a0101,a01.a0000," + 
									"       decode(a01.a0104, 1, '��', 2, 'Ů') a0104," + 
									"       decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
									"       a01.qrzxl qrzxl," + 
									"       a01.zgxl zgxl,a01.dbwy,(select b0111 from b01 where b01id='"+b01id+"') b0111p," + 
									"       (select code_name" + 
									"          from code_value" + 
									"         where code_value.code_value = a01.a0141" + 
									"           and code_type = 'GB4762') a0141," + 
									"       (select bz.rybz" + 
									"          from HZ_MNTP_BZ bz" + 
									"         where bz.a01bzid = f.a0200" + 
									"           and bz.mntp00 = '"+mntp00+"'" + 
									"           and a01bztype = '1') rybz," + 
									"       f.fxyp00,"+
								    "(select fxyp07 from fxyp x where x.a0200=f.a0200 and x.mntp00 = '"+mntp00+"' ) fxyp07" + 
									"   from GWZWREF f, a01" + 
									"  where a01.a0000 = f.a0000" + 
									"   and f.mntp00 = '"+mntp00+"'" + 
									"   and f.a0200 in" + 
									"       (select a0200 from a02 where a02.a0201b = '"+b01id+"')" ; 
							
							CommQuery cqbs1=new CommQuery();
							List<HashMap<String, Object>> listCode1=cqbs1.getListBySQL(sql.toString());
							JSONArray  updateunDataStoreObject1 = JSONArray.fromObject(listCode1);	
							String totallength=String.valueOf(lengthQX);
							String line=String.valueOf(j);
							String length=String.valueOf(listCode1.size());
							
							List<HashMap<String, Object>> listCode2=cqbs1.getListBySQL(formersql.toString());
							JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(listCode2);	
							this.getExecuteSG().addExecuteCode("AddQx("+updateunDataStoreObject1.toString()+","+updateunDataStoreObject2.toString()+","+totallength+","+length+","+line+");");
						}
						
					}
				}
			}
			this.getExecuteSG().addExecuteCode("bindRMB()");
			this.setNextEventName("dwxxadd");
		}catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
		
	
	@PageEvent("dwxxadd")
	public int dwxxadd() throws RadowException, AppException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		@SuppressWarnings("unchecked")
		List<String> tpdw= HBUtil.getHBSession().createSQLQuery("select b0111 from (select distinct  b0111��c.b0269 from b01 c,fxyp " + 
				"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null and b0111 <> '001.002' " + 
				"order by c.b0269)").list();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			if(tpdw.size()>0) {
				for(int i=0;i<tpdw.size();i++) {					
					String b0111=tpdw.get(i);
					@SuppressWarnings("unchecked")
					List<String> b01ids= HBUtil.getHBSession().createSQLQuery("select b01id from b01 where b0111='"+b0111+"'").list();
					String b01id=b01ids.get(0);
					String sb=this.showOrgInfo(mntp00,"2",b0111,b01id);
					map.put(b0111, sb);
					this.getExecuteSG().addExecuteCode("dwxxadd('"+b0111+"','"+b01id+"','"+sb+"')");
							
				}
			}
		
			
		}catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public String showOrgInfo(String mntp00,String mntp05,String b0111, String b01id) throws RadowException, AppException{
		//�ж��ظ�
		Map<String,Object> a0000sMap = new HashMap<String, Object>();
		//ͳ��
		Map<String,Object> mapCount = new HashMap<String, Object>();
		//����
		Map<String,List<String>> reverseSearchMap = new HashMap<String, List<String>>();
	
		this.getJData(mntp00,mntp05,b01id,b0111,a0000sMap,mapCount,reverseSearchMap,false);
  
		String html = "";
		StringBuilder sb = new StringBuilder();
		sb.append("<p class=\"p1\">����ṹ��");
		String fontcolor = "style=\"color:#FF4500\"";
		boolean hasComma = false;
		if(mapCount.get("ageCount")!=null){
			float avgAge = (float)((Integer)mapCount.get("ageCount"))/a0000sMap.size();
	        DecimalFormat df = new DecimalFormat("0.0");
	        String result = df.format(avgAge);
			sb.append("ƽ������<span "+fontcolor+">"+result+"</span>��");
			hasComma = true;
		}
		if(mapCount.get("ageLT45")!=null){
			if(hasComma){
				sb.append("��");
			}
			hasComma = true;
			sb.append("<span dataType=\"ageLT45\" >45�꼰����<span "+fontcolor+">"+mapCount.get("ageLT45")+"</span>��</span>");
		}
		if(mapCount.get("ageGT55")!=null){
			if(hasComma){
				sb.append("��");
			}
			hasComma = true;
			sb.append("<span dataType=\"ageGT55\" >55�꼰����<span "+fontcolor+">"+mapCount.get("ageGT55")+"</span>��</span>");
		}
		sb.append("</p><p class=\"p1\">ѧ���ṹ��");
		hasComma = false;
		if(mapCount.get("xlxwLev1")!=null){
			hasComma = true;
			sb.append("<span dataType=\"xlxwLev1\" >�о���������<span "+fontcolor+">"+mapCount.get("xlxwLev1")+"</span>��</span>");
		}
		if(mapCount.get("xlxwLev2")!=null){
			hasComma = true;
			sb.append("<span dataType=\"xlxwLev2\" >�����в�ʿ<span "+fontcolor+">"+mapCount.get("xlxwLev2")+"</span>����</span>");
		}
		if(mapCount.get("xlxwLev3")!=null){
			if(hasComma){
				sb.append("��");
			}
			hasComma = true;
			sb.append("<span dataType=\"xlxwLev3\" >��ѧ<span "+fontcolor+">"+mapCount.get("xlxwLev3")+"</span>��</span>");
		}
		if(mapCount.get("xlxwLev4")!=null){
			if(hasComma){
				sb.append("��");
			}
			hasComma = true;
			sb.append("<span dataType=\"xlxwLev4\" >��ר������<span "+fontcolor+">"+mapCount.get("xlxwLev4")+"</span>��</span>");
		}
		sb.append("</p><p class=\"p1\">�ṹ�Ըɲ���");
		hasComma = false;
		if(mapCount.get("female")!=null){
			hasComma = true;
			sb.append("<span dataType=\"female\" >Ů�ɲ�<span "+fontcolor+">"+mapCount.get("female")+"</span>��</span>");
		}
		//Ů�ɲ�ȱ�����
		if("2".equals(mntp05)){
			if(mapCount.get("female1001")==null||mapCount.get("female1003")==null||mapCount.get("female1004")==null||mapCount.get("female1005")==null){
				hasComma = false;
				sb.append("��");
				if(mapCount.get("female1001")==null){
					hasComma = true;
					sb.append("��ίȱ��Ů�ɲ�<span "+fontcolor+">1</span>��");
				}
				if(mapCount.get("female1003")==null){
					if(hasComma){
						sb.append("��");
					}
					hasComma = true;
					sb.append("�˴�ȱ��Ů�ɲ�<span "+fontcolor+">1</span>��");
				}
				if(mapCount.get("female1004")==null){
					if(hasComma){
						sb.append("��");
					}
					hasComma = true;
					sb.append("����ȱ��Ů�ɲ�<span "+fontcolor+">1</span>��");
				}
				
				if(mapCount.get("female1005")==null){
					if(hasComma){
						sb.append("��");
					}
					hasComma = true;
					sb.append("��Эȱ��Ů�ɲ�<span "+fontcolor+">1</span>��");
				}
				sb.append("��");
			}
		}
		if(mapCount.get("noZGParty")!=null){
			if(hasComma){
				sb.append("��");
			}
			hasComma = true;
			sb.append("<span dataType=\"noZGParty\" >����ɲ�<span "+fontcolor+">"+mapCount.get("noZGParty")+"</span>��</span>");
		}
		//�ĸ����ӵ���ɲ����˴�����Ӧ��1������Э3����ȱ�伸��
		if("2".equals(mntp05)){
			//�ĸ����ӵ���ɲ����˴�����Ӧ��1������Э3����ȱ�伸��
			if(mapCount.get("noZGParty1003")==null||mapCount.get("noZGParty1004")==null||(mapCount.get("noZGParty1005")==null||(mapCount.get("noZGParty1005")!=null&&(Integer)mapCount.get("noZGParty1005")<3))){
				hasComma = false;
				sb.append("��");
				if(mapCount.get("noZGParty1003")==null){
					hasComma = true;
					sb.append("�˴�ȱ�䵳��ɲ�<span "+fontcolor+">1</span>��");
				}
				if(mapCount.get("noZGParty1004")==null){
					if(hasComma){
						sb.append("��");
					}
					hasComma = true;
					sb.append("����ȱ�䵳��ɲ�<span "+fontcolor+">1</span>��");
				}
				
				if(mapCount.get("noZGParty1005")==null||(mapCount.get("noZGParty1005")!=null&&(Integer)mapCount.get("noZGParty1005")<3)){
					if(hasComma){
						sb.append("��");
					}
					hasComma = true;
					sb.append("��Эȱ�䵳��ɲ�<span "+fontcolor+">"+(3-(Integer)(mapCount.get("noZGParty1005")==null?0:mapCount.get("noZGParty1005")))+"</span>��");
				}
				sb.append("��");
			}
			
			sb.append("</p><p class=\"p1\">����ɲ���");
			hasComma = false;
			Integer dz10011004Count = Integer.valueOf(mapCount.get("dz10011004")==null?"0":mapCount.get("dz10011004").toString());
			if(mapCount.get("age40")!=null){//dz10011004
				
				hasComma = true;
				float avgAge40 = (float)((Integer)mapCount.get("age40"))/dz10011004Count*100;
		        DecimalFormat df = new DecimalFormat("0.00");
		        String result = df.format(avgAge40);
				sb.append("<span dataType=\"age40\" >40�����ҵ����쵼�ɲ�<span "+fontcolor+">"+mapCount.get("age40")+"</span>����ռ��<span "+fontcolor+">"+result+"%</span></span>");
			}
			if(mapCount.get("age35")!=null){
				if(hasComma){
					sb.append("��");
				}
				hasComma = true;
				sb.append("<span dataType=\"age35\" >����35�����ҵ����쵼�ɲ�<span "+fontcolor+">"+mapCount.get("age35")+"</span>��</span>");
			}	
			sb.append("</p>");
		}
		
		
		
		//40�����ҵ����쵼�ɲ� ���� ռ�ȣ�����35�����ҵ����쵼�ɲ��������ĸ�����ȱ��Ů�ɲ���Ӧ��1����������
//				if("2".equals(mntp05)){
//					sb.append("</p><p class=\"p1\">����������");
//					hasComma = false;
//					if(mapCount.get("el02_1")!=null){
//						hasComma = true;
//						sb.append("<span dataType=\"el02_1\" >�������ӳ�Ա�����조��������ͬһְλ��ְ��10����뽻��<span "+fontcolor+">"+mapCount.get("el02_1")+"</span>��</span>");
//					}
//					if(mapCount.get("el02_2")!=null){
//						if(hasComma){
//							sb.append("��</br>");
//						}
//						hasComma = true;
//						sb.append("<span dataType=\"el02_2\" >�������ӳ�Ա�����조��������ͬһְλ��������ְ�ﵽ�������ڣ������Ƽ�����������ͬһְ��<span "+fontcolor+">"+mapCount.get("el02_2")+"</span>��</span>");
//					}
//					
//					if(mapCount.get("el02_3")!=null){
//						if(hasComma){
//							sb.append("��</br>");
//						}
//						hasComma = true;
//						sb.append("<span dataType=\"el02_3\" >ͬһ�����أ��У����������е���ͬһ����쵼ְ����10��Ӧ������<span "+fontcolor+">"+mapCount.get("el02_3")+"</span>��</span>");
//					}
//					if(mapCount.get("el02_4")!=null){
//						if(hasComma){
//							sb.append("��</br>");
//						}
//						hasComma = true;
//						sb.append("<span dataType=\"el02_4\" >�����أ��У���ί��Ǹ���һ��ԭ����Ҫ����<span "+fontcolor+">"+mapCount.get("el02_4")+"</span>��</span>");
//					}
//					if(mapCount.get("el02_5")!=null){
//						if(hasComma){
//							sb.append("��</br>");
//						}
//						hasComma = true;
//						sb.append("<span dataType=\"el02_5\" >�����쵼�ɲ�����ͬһ����쵼ְ���ۼƴ�15�����ϵģ����ټ�������<span "+fontcolor+">"+mapCount.get("el02_5")+"</span>��</span>");
//					}
//					html = sb.toString();
//				}else{
//					html = sb.toString().replaceAll("(?!)", "")
//							.replaceAll("class=\"p1\"", "class=\"p2\"");
//				}
	html = sb.toString();
	JSONObject  pgridRecordsRS = JSONObject.fromObject(reverseSearchMap);
	
//				this.pm.getExecuteSG().addExecuteCode("$('.OrgInfo').html('"+html+"')");
//				this.pm.getExecuteSG().addExecuteCode("PgridInfo.pgridRecordsRS="+pgridRecordsRS+";reverseSearchClick()");
	return html;
}
	
	
	public void getJData(String mntp00, String mntp05, String b01id, String b0111, Map<String, Object> a0000sMap, Map<String, Object> mapCount, Map<String, List<String>> reverseSearchMap,boolean istpq) throws AppException{
		String sql = "";
		
		sql = this.getSQLTPH(mntp00, mntp05, b01id, b0111);
		
		
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
		
		for(HashMap<String, Object> dataMap : listCode){
			String a0000 = dataMap.get("a0000")==null?"":dataMap.get("a0000").toString();
			if(StringUtils.isEmpty(a0000)){
				continue;
			}
			if(a0000sMap.get(a0000)==null&&!"-1".equals(dataMap.get("fxyp07"))){//��ְ�Ĳ���
				a0000sMap.put(a0000, "");
				String age = dataMap.get("age")==null?"0":dataMap.get("age").toString();
				
				String dataKey = "ageCount";
				//������
				this.addCount(mapCount,dataKey,Integer.valueOf(age),a0000,a0000,reverseSearchMap);
				
				//��������
				dataKey = "el02_1";
				if(dataMap.get("el02")!=null&&dataMap.get("el02").toString().contains("1")){
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(dataMap.get("el02")!=null&&dataMap.get("el02").toString().contains("2")){
					dataKey = "el02_2";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(dataMap.get("el02")!=null&&dataMap.get("el02").toString().contains("3")){
					dataKey = "el02_3";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(dataMap.get("el02")!=null&&dataMap.get("el02").toString().contains("4")){
					dataKey = "el02_4";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(dataMap.get("el02")!=null&&dataMap.get("el02").toString().contains("5")){
					dataKey = "el02_5";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}
				
				
				//40�����ҵ����쵼�ɲ�
				dataKey = "age40";
				if( "1001".equals(dataMap.get("fxyp06")) || "1004".equals(dataMap.get("fxyp06") ) ){
					if(Integer.valueOf(age)<43 ){
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
						//����35�����ҵ����쵼�ɲ�����
						dataKey = "age35";
						if(Integer.valueOf(age)<38){
							this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
						}
					}
					dataKey = "dz10011004";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}
				
				
				//55�꼰���ϼ���
				dataKey = "ageGT55";
				if(Integer.valueOf(age)>=55){
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					//60�꼰���ϼ���
					dataKey = "ageGT60";
					if(Integer.valueOf(age)>=60){
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}
				}
				
				//56-59��
				dataKey = "ageGT56LT59";
				if(Integer.valueOf(age)>=56&&Integer.valueOf(age)<=59){
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else  if(Integer.valueOf(age)>=51&&Integer.valueOf(age)<=55){
					dataKey = "ageGT51LT55";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else  if(Integer.valueOf(age)>=46&&Integer.valueOf(age)<=50){
					dataKey = "ageGT46LT50";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(Integer.valueOf(age)>=41&&Integer.valueOf(age)<=45){
					//41-45��
					dataKey = "ageGT41LT45";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(Integer.valueOf(age)<=40){
					//40�꼰����
					dataKey = "ageLT40";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}
				
				
				
				
				
				
				//45�꼰����
				dataKey = "ageLT45";
				if(Integer.valueOf(age)<=45){
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}
				
				
				//Ů�ɲ�����
				dataKey = "female";
				String a0104 = dataMap.get("a0104")==null?"0":dataMap.get("a0104").toString();
				if("2".equals(a0104)){
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					//�ĸ�����ȱ��Ů�ɲ���Ӧ��1��������
					if("1001".equals(dataMap.get("fxyp06"))){
						dataKey = "female1001";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}else if("1004".equals(dataMap.get("fxyp06"))){
						dataKey = "female1004";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}else if("1003".equals(dataMap.get("fxyp06"))){
						dataKey = "female1003";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}else if("1005".equals(dataMap.get("fxyp06"))){
						dataKey = "female1005";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}
				}
				//����ɲ�����
				dataKey = "noZGParty";
				String a0141 = dataMap.get("a0141")==null?"0":dataMap.get("a0141").toString();
				if(!"01".equals(a0141)){
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					
					//�ĸ����ӵ���ɲ����˴�����Ӧ��1������Э3����ȱ�伸��
					if("1001".equals(dataMap.get("fxyp06"))){
						dataKey = "noZGParty1001";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}else if("1004".equals(dataMap.get("fxyp06"))){
						dataKey = "noZGParty1004";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}else if("1003".equals(dataMap.get("fxyp06"))){
						dataKey = "noZGParty1003";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}else if("1005".equals(dataMap.get("fxyp06"))){
						dataKey = "noZGParty1005";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}
				}
				
				//�о��������ϼ��������в�ʿ��������  ��ѧ��������ר�����¼���
				String zzxl = dataMap.get("zzxl")==null?"":dataMap.get("zzxl").toString();
				String zzxw = dataMap.get("zzxw")==null?"":dataMap.get("zzxw").toString();
				String qrzxl = dataMap.get("qrzxl")==null?"":dataMap.get("qrzxl").toString();
				String qrzxw = dataMap.get("qrzxw")==null?"":dataMap.get("qrzxw").toString();
				String regx = "[\\s\\S]*(��ʿ|˶ʿ|�о���)[\\s\\S]*";
				if(zzxl.matches(regx)||zzxw.matches(regx)||qrzxl.matches(regx)||qrzxw.matches(regx)){
					dataKey = "xlxwLev1";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(zzxl.matches("[\\s\\S]*(��ѧ|����)[\\s\\S]*")||zzxw.matches("[\\s\\S]*(��ѧ|����)[\\s\\S]*")||qrzxl.matches("[\\s\\S]*(��ѧ|����)[\\s\\S]*")||qrzxw.matches("[\\s\\S]*(��ѧ|����)[\\s\\S]*")){
					dataKey = "xlxwLev3";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(zzxl.matches("[\\s\\S]*(��ר|ר��|����|�м�|��ר|Сѧ|����)[\\s\\S]*")||zzxw.matches("[\\s\\S]*(��ר|ר��|����|�м�|��ר|Сѧ|����)[\\s\\S]*")||qrzxl.matches("[\\s\\S]*(��ר|ר��|����|�м�|��ר|Сѧ|����)[\\s\\S]*")||qrzxw.matches("[\\s\\S]*(��ר|ר��|����|�м�|��ר|Сѧ|����)[\\s\\S]*")){
					dataKey = "xlxwLev4";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}
				if(zzxl.matches("[\\s\\S]*(��ʿ)[\\s\\S]*")||zzxw.matches("[\\s\\S]*(��ʿ)[\\s\\S]*")||qrzxl.matches("[\\s\\S]*(��ʿ)[\\s\\S]*")||qrzxw.matches("[\\s\\S]*(��ʿ)[\\s\\S]*")){
					dataKey = "xlxwLev2";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}
			
			}
			
			//����
			mapCount.put("totalCount", a0000sMap.size());
			
		}
	}
	
	
	public void addCount(Map<String, Object> mapCount, String dataKey, Integer count, String a0000, String a00002, Map<String, List<String>> reverseSearchMap){
		if(mapCount.get(dataKey)!=null){
			mapCount.put(dataKey, (Integer)mapCount.get(dataKey)+count);
			reverseSearchMap.get(dataKey).add(a0000);
		}else{
			List<String> l = new ArrayList<String>();
			reverseSearchMap.put(dataKey, l);
			l.add(a0000);
			mapCount.put(dataKey, count);
		}
	}
	
	
	public String getSQLTPH(String mntp00, String mntp05, String b01id, String b0111){
		String sql = "";
		
		  
		if("2".equals(mntp05)){//�����쵼����
  
			sql = "select a02.a0200,"
					+ "(select b0131 from b01 where b0111=a02.a0201b) fxyp06,"
				+ " (select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp07,"
				+ " a01.a0000,a01.a0104,a01.a0141,qrzxl,qrzxw,zzxl,zzxw,a01.a0101,"
				+ " substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age "
				+ " FROM a02, a01 "
		  		+ " WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
		  		//+ " and a02.a0201e in('1','3') "
		  		+ " and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
		  		+ " and a02.a0201b in "
		  		+ " (select b0111 from b01 b where b.b0131 in('1001','1003','1004','1005','1006','1007') "
		  		+ " and b.b0111 like '"+b0111+".%')";
	  
	  
		}else if("3".equals(mntp05)||"1".equals(mntp05)||"4".equals(mntp05)){//����ƽ̨ ��ֱ �����У
			  sql = "select  a02.a0200,(nvl(a02.a0201e,'Z')||'01') fxyp06,"
				+ " (select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp07,"
				+ "a01.a0000,a01.a0104,a01.a0141,qrzxl,qrzxw,zzxl,zzxw,a01.a0101,"
				+ "substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age from a01, a02 where a01.A0000 = a02.a0000 AND a02.a0281 = 'true'"
		   +"    AND a02.a0255 = '1' "
		  // + " and a02.a0201e in ('1','3')"
		   +"    and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')"
		   +"    and a02.a0201b = '"+b0111+"'";
		}
  
  
		sql = "select * from ("+sql+") a01 ";
		
		String allSql = "and t.b01id='"+b01id+"'";
		if("".equals(b01id)){//������
			allSql = "and t.b01id in(select b0111 from b01 b where b.b0131 in('1001','1003','1004','1005','1006','1007') "
		  		+ " and b.b0111 like '"+b0111+".%')";
		}
		String unionSql = "select t.a0200,t.fxyp06,t.fxyp07,a01.a0000,"
				+ "a01.a0104,a01.a0141,qrzxl,qrzxw,zzxl,zzxw,a01.a0101,"
				+ "substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age "
				+ " from v_mntp_gw_ry t, a01 "
		  		+ " where t.sortnum=1 and t.a0000=a01.a0000 and t.fxyp07=1 and t.mntp00='"+mntp00+"' "+allSql;
		  
		sql = "select a01.*,"
				+ "(select to_char(wm_concat(el02)) el02 from EXCHANGE_LIST el where el01='2020' and el.a0000=a01.a0000) el02"
				+ " from ("+sql+" union all "+unionSql+") a01 ";	
		return sql;
	}
	/**
	 * ��ȡ����
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
