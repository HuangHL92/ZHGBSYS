package com.insigma.siis.local.pagemodel.fxyp;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.GbkhAtt;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;

public class GWSortPageModel extends PageModel{

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
			List<String> tpdw= HBUtil.getHBSession().createSQLQuery("select b0111 from (select distinct  b0111，c.b0269 from b01 c,fxyp " + 
					"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null " + 
					"order by c.b0269)").list();
//			List<String> tpdw= HBUtil.getHBSession().createSQLQuery("select distinct  b0111 from b01 c,fxyp " + 
//					"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null " + 
//					"order by c.b0111").list();
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			if(tpdw.size()>0) {
				for(int i=0;i<tpdw.size();i++) {
					@SuppressWarnings("unchecked")
					List<String> b0101= HBUtil.getHBSession().createSQLQuery("select b0101 from b01 where b0111='"+tpdw.get(i)+"'").list();
					map.put(tpdw.get(i), b0101.get(0));
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
					map.put(tpdwadd.get(i)+"@1", addname.get(0));
				}
			}
			//新增区县
			@SuppressWarnings("unchecked")
			List<String> tpqxadd= HBUtil.getHBSession().createSQLQuery("select distinct c.b01id from  HZ_MNTP_ORG_QXS c,fxyp " + 
					"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null " ).list();
			if(tpqxadd.size()>0) {
				for(int i=0;i<tpqxadd.size();i++) {
					@SuppressWarnings("unchecked")
					List<String> addname1= HBUtil.getHBSession().createSQLQuery("select b0101 from HZ_MNTP_ORG_QXS where b01id='"+tpqxadd.get(i)+"'").list();
					map.put(tpqxadd.get(i)+"@2", addname1.get(0));
				}
			}
			
			((Combo)this.getPageElement("b0111")).setValueListForSelect(map); 
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		String b01id = this.getPageElement("b01id").getValue();
		String mntp00 = this.getPageElement("mntp00").getValue();
		try {
			@SuppressWarnings("unchecked")
			List<String> b0111s= HBUtil.getHBSession().createSQLQuery("select b0111 from b01 where b01id='"+b01id+"'").list();
			if(b0111s.size()>0) {
				@SuppressWarnings("unchecked")
				List<String> tpdw= HBUtil.getHBSession().createSQLQuery("select distinct  b0111 from b01 c,fxyp " + 
						"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null " + 
						"order by c.b0111").list();
				if(tpdw.contains(b0111s.get(0))) {
					String b0111=b0111s.get(0);
					this.getPageElement("b0111").setValue(b0111s.get(0));
					if(b0111.length()==15 && "001.001.004".equals(b0111.substring(0,11))) {
						this.getPageElement("zrrx").setValue("1001");
						this.setNextEventName("gwGrid.dogridquery");
					}else {
						this.setNextEventName("gwGrid.dogridquery");
					}
				}
				
			}else {
				String sql1="select distinct c.b01id from  HZ_MNTP_ORG_QT c,fxyp " + 
						"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null ";
				
				String sql2="select distinct c.b01id from  HZ_MNTP_ORG_QXS c,fxyp " + 
						"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null ";
				@SuppressWarnings("unchecked")
				List<String> tpdwadd1= HBUtil.getHBSession().createSQLQuery(sql1).list();
				@SuppressWarnings("unchecked")
				List<String> tpdwadd2= HBUtil.getHBSession().createSQLQuery(sql2).list();
				if(tpdwadd1.contains(b01id)) {
					this.getPageElement("b0111").setValue(tpdwadd1.get(0)+"@1");
					this.setNextEventName("gwGrid.dogridquery");
				}else if(tpdwadd2.contains(b01id)) {
					this.getPageElement("b0111").setValue(tpdwadd2.get(0)+"@2");
					this.getPageElement("zrrx").setValue("1001");
					this.setNextEventName("gwGrid.dogridquery");
				}
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	
	}
	
//	@PageEvent("zrrxQuery")
//	public int zrrxQuery() throws RadowException, AppException{
//		String mntp00 = this.getPageElement("mntp00").getValue();
//		String b0111= this.getPageElement("b0111").getValue();
//		try {
//			if(b0111.length()==15 && "001.001.004".equals(b0111.substring(0,11))) {
//				this.setNextEventName("gwGrid.dogridquery");
//			}else {
//				this.setNextEventName("gwGrid.dogridquery");
//			}
//		} catch (Exception e) {
//			this.setMainMessage("查询失败！");
//			e.printStackTrace();
//		}
//		return EventRtnType.NORMAL_SUCCESS;
//	
//	}
//	
	
	@PageEvent("gwGrid.dogridquery")
	@NoRequiredValidate
	public int gwQuery(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		String b0111= this.getPageElement("b0111").getValue();
		String zrrx=this.getPageElement("zrrx").getValue();
		String sql="";	
		int flag=b0111.indexOf("@");
		try {
			System.out.println(b0111+"and"+zrrx);	
			if(flag==-1) {
				if(b0111.length()==15 && "001.001.004".equals(b0111.substring(0,11))) {
					String sql1="select * from (SELECT (select b0131 from b01 where b0111 = a02.a0201b) zrrx," + 
							" (select gw.fxyp07  from hz_mntp_gw gw  where gw.a0200 = a02.a0200" + 
							"  and gw.mntp00 ='"+mntp00+"'and fxyp07 = -1) fxyp07," + 
							"          a0215a,b.sortnum,a0200, a01.a0000 a0000s," + 
							"                         a01.a0101 a0101s," + 
							"                        decode(a01.a0104, 1, '男', 2, '女') a0104s," + 
							"          (select code_name from code_value where code_value.code_value = a01.a0141 and code_type = 'GB4762') a0141s, " + 
							"                       a01.a0192a a0192as" + 
							"                  FROM a02," + 
							"                       a01," + 
							"                       (select *" + 
							"                          from GWSORT" + 
							"                         where mntp00 = '"+mntp00+"'" + 
							"                           and sorttype = '1') b" + 
							"                 WHERE a01.A0000 = a02.a0000" + 
							"                   and a01.a0163='1'" + 
							"                   AND a02.a0281 = 'true'" + 
							"                   AND a02.a0255 = '1'" + 
							"                   and b.SORTID(+) = a02.a0200" + 
							"       and (select b0131 from b01 where b0111 = a02.a0201b)='"+zrrx+"'"+
							"                   and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
							"                   and exists" + 
							"                       (select b0111" + 
							"                          from b01 b" + 
							"                         where b.b0131 in" + 
							"                               ('1001', '1003', '1004', '1005', '1006', '1007')" + 
							"              and a02.a0201b=b.b0111  and b.b0111 like '"+b0111+".%')" +
							" 				and not exists" + 
							"                 (select 1" + 
							"                          from GWZWREF gw" + 
							"                         where gw.a0200 = a02.a0200" + 
							"                           and gw.mntp00 =" + 
							"                               '"+mntp00+"') "+
							"                 order by zrrx,((select rpad(b0269, 25, '.') ||" + 
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
							"                               and t.a0000 = a01.a0000)))";
					
					
					String sql2="select * from (select fxyp06 zrrx ,fxyp07,fxyp02 a0215a,b.sortnum,fxyp00 a0200"
						  + " , (select listagg(a01.a0000, ',') within group(order by r.sortnum)" + 
						  "                          from rxfxyp r, a01" + 
						  "                         where r.zwqc00 = t.zwqc00" + 
						  "                           and a01.a0000 = r.a0000) a0000s," + 
						  //拟任岗位全部人员
						  "                         (select listagg(a01.a0101, ',') within group(order by r.sortnum)" + 
						  "                          from rxfxyp r, a01" + 
						  "                         where r.zwqc00 = t.zwqc00" + 
						  "                           and a01.a0000 = r.a0000) a0101s," + 
						  "                         (select listagg(decode(a01.a0104, 1, '男', 2, '女'), ',') within group(order by r.sortnum)" + 
						  "                          from rxfxyp r, a01" + 
						  "                         where r.zwqc00 = t.zwqc00" + 
						  "                           and a01.a0000 = r.a0000) a0104s,   " + 
						  "                         (select listagg((select code_name from code_value where code_value.code_value = a01.a0141 and code_type = 'GB4762'), ',') within group(order by r.sortnum)" + 
						  "                          from rxfxyp r, a01" + 
						  "                         where r.zwqc00 = t.zwqc00" + 
						  "                           and a01.a0000 = r.a0000) a0141s,   " + 
						  "                         (select listagg(a01.a0192a, '-') within group(order by r.sortnum)" + 
						  "                          from rxfxyp r, a01" + 
						  "                         where r.zwqc00 = t.zwqc00" + 
						  "                           and a01.a0000 = r.a0000) a0192as" + 
							"                  from hz_mntp_gw t,               " + 
							"                       (select *" + 
							"                          from GWSORT" + 
							"                         where mntp00 = '"+mntp00+"'" + 
							"                           and sorttype = '2') b" + 
							"                 where  t.fxyp07 = 1" + 
							"                   and t.mntp00 ='"+mntp00+"'" + 
							"                   and t.b01id = (select b01id from b01 where b0111='"+b0111+"')" + 
							"                   and  b.SORTID(+) = t.fxyp00 " + 
							"                   and fxyp06='"+zrrx+"'" + 
							"                 order by t.fxyp00)" ;
					
					sql="select * from "+"("+sql1+" union all "+sql2+") order by sortnum";
				}else{
					String sql1="select * from(" + 
							"SELECT a02.a0201e zrrx," + 
							" (select gw.fxyp07  from hz_mntp_gw gw  where gw.a0200 = a02.a0200" + 
							"  and gw.mntp00 ='"+mntp00+"'and fxyp07 = -1) fxyp07," + 
							"          a0215a,b.sortnum,a0200, a01.a0000 a0000s," + 
							"                         a01.a0101 a0101s," + 
							"                        decode(a01.a0104, 1, '男', 2, '女') a0104s," + 
							"          (select code_name from code_value where code_value.code_value = a01.a0141 and code_type = 'GB4762') a0141s, " + 
							"                       a01.a0192a a0192as" + 
							"                  FROM a02," + 
							"                       a01," + 
							"                       (select *" + 
							"                          from GWSORT" + 
							"                         where mntp00 = '"+mntp00+"'" + 
							"                           and sorttype = '1') b" + 
							"                  WHERE a01.A0000 = a02.a0000" + 
							"                   and a01.a0163='1'" + 
							"                   AND a02.a0281 = 'true'" + 
							"                   AND a02.a0255 = '1'" + 
							"                   and b.SORTID(+) = a02.a0200" + 
							"                   and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')" + 
							"                   and a02.a0201b = '"+b0111+"'" +
							" 				and not exists" + 
							"                 (select 1" + 
							"                          from GWZWREF gw" + 
							"                         where gw.a0200 = a02.a0200" + 
							"                           and gw.mntp00 =" + 
							"                               '"+mntp00+"') "+
							"                 order by ((select rpad(b0269, 25, '.') ||" + 
							"                                   lpad(a0225, 25, '0')" + 
							"                              from (select a02.a0000," + 
							"                                           b0269," + 
							"                                           a0225," + 
							"                                           row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
							"                                      from a02, b01" + 
							"                                     where a02.a0201b = b01.b0111" + 
							"                                       and a0281 = 'true'" + 
							"                                       and a0201b = '"+b0111+"') t" + 
							"                             where rn = 1" + 
							"                               and t.a0000 = a01.a0000)))";
					
					
					String sql2="select * from (select fxyp06 zrrx ,fxyp07,fxyp02 a0215a,b.sortnum,fxyp00 a0200"
						  + ", (select listagg(a01.a0000, ',') within group(order by r.sortnum)" + 
						  "                          from rxfxyp r, a01" + 
						  "                         where r.zwqc00 = t.zwqc00" + 
						  "                           and a01.a0000 = r.a0000) a0000s," + 
						  //拟任岗位全部人员
						  "                         (select listagg(a01.a0101, ',') within group(order by r.sortnum)" + 
						  "                          from rxfxyp r, a01" + 
						  "                         where r.zwqc00 = t.zwqc00" + 
						  "                           and a01.a0000 = r.a0000) a0101s," + 
						  "                         (select listagg(decode(a01.a0104, 1, '男', 2, '女'), ',') within group(order by r.sortnum)" + 
						  "                          from rxfxyp r, a01" + 
						  "                         where r.zwqc00 = t.zwqc00" + 
						  "                           and a01.a0000 = r.a0000) a0104s,   " + 
						  "                         (select listagg((select code_name from code_value where code_value.code_value = a01.a0141 and code_type = 'GB4762'), ',') within group(order by r.sortnum)" + 
						  "                          from rxfxyp r, a01" + 
						  "                         where r.zwqc00 = t.zwqc00" + 
						  "                           and a01.a0000 = r.a0000) a0141s,   " + 
						  "                         (select listagg(a01.a0192a, '-') within group(order by r.sortnum)" + 
						  "                          from rxfxyp r, a01" + 
						  "                         where r.zwqc00 = t.zwqc00" + 
						  "                           and a01.a0000 = r.a0000) a0192as " + 
							"                  from hz_mntp_gw t,               " + 
							"                       (select *" + 
							"                          from GWSORT" + 
							"                         where mntp00 = '"+mntp00+"'" + 
							"                           and sorttype = '2') b" + 
							"                 where  t.fxyp07 = 1" + 
							"                   and t.mntp00 ='"+mntp00+"'" + 
							"                   and t.b01id = (select b01id from b01 where b0111='"+b0111+"')" + 
							"                   and  b.SORTID(+) = t.fxyp00 " + 
							"                 order by t.fxyp00)";
					
					sql="select * from "+"("+sql1+" union all "+sql2+") order by sortnum";
				}
			}else {
				if("1".equals(b0111.split("@")[1])) {
					sql="select * from (select fxyp06 zrrx ,fxyp07,fxyp02 a0215a,b.sortnum,fxyp00 a0200"
							  + ", (select listagg(a01.a0000, ',') within group(order by r.sortnum)" + 
							  "                          from rxfxyp r, a01" + 
							  "                         where r.zwqc00 = t.zwqc00" + 
							  "                           and a01.a0000 = r.a0000) a0000s," + 
							  //拟任岗位全部人员
							  "                         (select listagg(a01.a0101, ',') within group(order by r.sortnum)" + 
							  "                          from rxfxyp r, a01" + 
							  "                         where r.zwqc00 = t.zwqc00" + 
							  "                           and a01.a0000 = r.a0000) a0101s," + 
							  "                         (select listagg(decode(a01.a0104, 1, '男', 2, '女'), ',') within group(order by r.sortnum)" + 
							  "                          from rxfxyp r, a01" + 
							  "                         where r.zwqc00 = t.zwqc00" + 
							  "                           and a01.a0000 = r.a0000) a0104s,   " + 
							  "                         (select listagg((select code_name from code_value where code_value.code_value = a01.a0141 and code_type = 'GB4762'), ',') within group(order by r.sortnum)" + 
							  "                          from rxfxyp r, a01" + 
							  "                         where r.zwqc00 = t.zwqc00" + 
							  "                           and a01.a0000 = r.a0000) a0141s,   " + 
							  "                         (select listagg(a01.a0192a, '-') within group(order by r.sortnum)" + 
							  "                          from rxfxyp r, a01" + 
							  "                         where r.zwqc00 = t.zwqc00" + 
							  "                           and a01.a0000 = r.a0000) a0192as " + 
								"                  from hz_mntp_gw t,               " + 
								"                       (select *" + 
								"                          from GWSORT" + 
								"                         where mntp00 = '"+mntp00+"'" + 
								"                           and sorttype = '2') b" + 
								"                 where  t.fxyp07 = 1" + 
								"                   and t.mntp00 ='"+mntp00+"'" + 
								"                   and t.b01id = '"+b0111.split("@")[0]+"'" + 
								"                   and  b.SORTID(+) = t.fxyp00 " + 
								"                 order by t.fxyp00)";
					
					sql="select * from "+"("+sql+") order by sortnum";
				}else if("2".equals(b0111.split("@")[1])) {
					sql="select * from (select fxyp06 zrrx ,fxyp07,fxyp02 a0215a,b.sortnum,fxyp00 a0200"
							  + " , (select listagg(a01.a0000, ',') within group(order by r.sortnum)" + 
							  "                          from rxfxyp r, a01" + 
							  "                         where r.zwqc00 = t.zwqc00" + 
							  "                           and a01.a0000 = r.a0000) a0000s," + 
							  //拟任岗位全部人员
							  "                         (select listagg(a01.a0101, ',') within group(order by r.sortnum)" + 
							  "                          from rxfxyp r, a01" + 
							  "                         where r.zwqc00 = t.zwqc00" + 
							  "                           and a01.a0000 = r.a0000) a0101s," + 
							  "                         (select listagg(decode(a01.a0104, 1, '男', 2, '女'), ',') within group(order by r.sortnum)" + 
							  "                          from rxfxyp r, a01" + 
							  "                         where r.zwqc00 = t.zwqc00" + 
							  "                           and a01.a0000 = r.a0000) a0104s,   " + 
							  "                         (select listagg((select code_name from code_value where code_value.code_value = a01.a0141 and code_type = 'GB4762'), ',') within group(order by r.sortnum)" + 
							  "                          from rxfxyp r, a01" + 
							  "                         where r.zwqc00 = t.zwqc00" + 
							  "                           and a01.a0000 = r.a0000) a0141s,   " + 
							  "                         (select listagg(a01.a0192a, '-') within group(order by r.sortnum)" + 
							  "                          from rxfxyp r, a01" + 
							  "                         where r.zwqc00 = t.zwqc00" + 
							  "                           and a01.a0000 = r.a0000) a0192as" + 
								"                  from hz_mntp_gw t,               " + 
								"                       (select *" + 
								"                          from GWSORT" + 
								"                         where mntp00 = '"+mntp00+"'" + 
								"                           and sorttype = '2') b" + 
								"                 where  t.fxyp07 = 1" + 
								"                   and t.mntp00 ='"+mntp00+"'" + 
								"                   and t.b01id = '"+b0111.split("@")[0]+"'" + 
								"                   and  b.SORTID(+) = t.fxyp00 " + 
								"                   and fxyp06='"+zrrx+"'" + 
								"                 order by t.fxyp00)" ;
					sql="select * from "+"("+sql+") order by sortnum";
				}
			}
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	
	}
	
	
	
//	@PageEvent("gwGrid.rowclick")
//	@GridDataRange
//	public int persongridOnRowDbClick() throws RadowException{  //打开窗口的实例
//		String fxyp07 = (String)this.getPageElement("gwGrid").getValueList().get(this.getPageElement("gwGrid").getCueRowIndex()).get("fxyp07");
//		String a0200 =(String)this.getPageElement("gwGrid").getValueList().get(this.getPageElement("gwGrid").getCueRowIndex()).get("a0200");
//		String zrrx=(String)this.getPageElement("gwGrid").getValueList().get(this.getPageElement("gwGrid").getCueRowIndex()).get("zrrx");
//		String a0215a=(String)this.getPageElement("gwGrid").getValueList().get(this.getPageElement("gwGrid").getCueRowIndex()).get("a0215a");
//		this.getPageElement("fxyp07").setValue(fxyp07);
//		this.getPageElement("a0200").setValue(a0200);
//		this.getPageElement("zrrx").setValue(zrrx);
//		this.getPageElement("a0215a").setValue(a0215a);
//		System.out.println(a0215a);
//		this.setNextEventName("rxGrid.dogridquery");
//		return EventRtnType.NORMAL_SUCCESS;		
//	}
	
//	@PageEvent("rxGrid.dogridquery")
//	public int doLogQuery(int start,int limit) throws RadowException{
//		String fxyp07 = this.getPageElement("fxyp07").getValue();
//		String a0200=this.getPageElement("a0200").getValue();
//		String b0111=this.getPageElement("b0111").getValue();
//		String mntp00=this.getPageElement("mntp00").getValue();
//		String sql = "";
//		if("1".equals(fxyp07)) {
//			String fxyp00=a0200;
//			@SuppressWarnings("unchecked")
//			List<String> zwqc00= HBUtil.getHBSession().createSQLQuery("select zwqc00 from fxyp where  fxyp00='"+fxyp00+"'").list();
//			if(zwqc00.size()>0) {
//				sql="select tp0101 a0101,decode(a01.a0104, 1, '男', 2, '女') a0104,tp0102 a0107,tp0106 a0192a ," + 
//						"  (select code_name from code_value where code_value.code_value = a01.a0141 and code_type = 'GB4762') a0141,t.sortnum,'1' fxyp07" + 
//						" from rxfxyp t,a01" + 
//						"  where a01.a0000=t.a0000 and zwqc00='"+zwqc00.get(0)+"'  order by t.sortnum ";
//			}		
//		}else if("-1".equals(fxyp07)) {
//			sql="select * from (select a01.a0101,decode(a01.a0104, 1, '男', 2, '女') a0104," + 
//					" substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107 ,a01.a0192a," + 
//					" (select code_name from code_value where code_value.code_value = a01.a0141 and code_type = 'GB4762') a0141,null sortnum,'-1' fxyp07 " + 
//					"from a01,a02 where a01.a0000=a02.a0000 and  a0200='"+a0200+"')";
//			@SuppressWarnings("unchecked")
//			List<String> fxyp00bd= HBUtil.getHBSession().createSQLQuery(
//					"select fxyp00 from fxyp where fxyp00ref=(select fxyp00 from fxyp where a0200='"+a0200+"' "
//					+ " and  mntp00 = '"+mntp00+"')").list();
//			String zwqc00="";
//			if(fxyp00bd.size()>0) {
//				@SuppressWarnings("unchecked")
//				List<String> zwqc00List= HBUtil.getHBSession().createSQLQuery("select zwqc00 from fxyp where  fxyp00='"+fxyp00bd.get(0)+"'").list();
//				if(zwqc00List.size()>0) {
//					zwqc00=zwqc00List.get(0);
//				}
//			}
//			String sql2="select * from (select tp0101 a0101,decode(a01.a0104, 1, '男', 2, '女') a0104,tp0102 a0107,tp0106 a0192a ," + 
//					"  (select code_name from code_value where code_value.code_value = a01.a0141 and code_type = 'GB4762') a0141,t.sortnum,'1' fxyp07" + 
//					" from rxfxyp t,a01" + 
//					"  where a01.a0000=t.a0000 and zwqc00='"+zwqc00+"'  order by t.sortnum )";
//			sql="select * from ("+sql+" union all " +sql2+")";
//			
//		}else {
//			sql="select a01.a0101,decode(a01.a0104, 1, '男', 2, '女') a0104," + 
//					" substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2) a0107 ,a01.a0192a," + 
//					" (select code_name from code_value where code_value.code_value = a01.a0141 and code_type = 'GB4762') a0141,null sortnum,'' fxyp07 " + 
//					"from a01,a02 where a01.a0000=a02.a0000 and  a0200='"+a0200+"'";
//		}
//		this.pageQuery(sql, "SQL", start, limit);
//		return EventRtnType.SPE_SUCCESS;
//	}
	
	@PageEvent("rolesort")
	@Transaction
	public int publishsort() throws RadowException, AppException {
		String mntp00 = this.getPageElement("mntp00").getValue();
		String b0111= this.getPageElement("b0111").getValue();
		String zrrx=this.getPageElement("zrrx").getValue();
		int flag=b0111.indexOf("@");
		List<HashMap<String, String>> list = this.getPageElement("gwGrid").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		Connection con = null;
		try {
			if(flag==-1) {
				if(b0111.length()==15 && "001.001.004".equals(b0111.substring(0,11))) {
					HBUtil.executeUpdate("delete from gwsort where mntp00='"+mntp00+"' and  zrrx='"+zrrx+"'  and b01id = (select b01id from b01 where b0111='"+b0111+"')");
				}else {
					HBUtil.executeUpdate("delete from gwsort where mntp00='"+mntp00+"' and b01id = (select b01id from b01 where b0111='"+b0111+"')");
				}
			}else {
				if("2".equals(b0111.split("@")[1])) {
					HBUtil.executeUpdate("delete from gwsort where mntp00='"+mntp00+"' and  zrrx='"+zrrx+"'  and b01id = '"+b0111.split("@")[0]+"'");
				}else if("1".equals(b0111.split("@")[1])){
					HBUtil.executeUpdate("delete from gwsort where mntp00='"+mntp00+"' and b01id = '"+b0111.split("@")[0]+"'");
				}
			}
					
			@SuppressWarnings("unchecked")
			List<String> b01id= HBUtil.getHBSession().createSQLQuery("select b01id from b01 where b0111='"+b0111+"'").list();
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "insert into GWSORT(mntp00,sorttype,sortid,b01id,sortnum,zrrx) values ( ?, ?, ?, ?, ?, ?) ";
			PreparedStatement ps = con.prepareStatement(sql);
			int i = 1;
			for (HashMap<String, String> m : list) {
				String fxyp07 = m.get("fxyp07");
				String a0200 = m.get("a0200");
				String zrrx1 = m.get("zrrx");
				ps.setString(1, mntp00);
				if("1".equals(fxyp07)) {
					ps.setString(2, "2");
				}else {
					ps.setString(2, "1");
				}
				ps.setString(3,a0200);
				if(flag==-1) {
					ps.setString(4,b01id.get(0));
				}else {
					ps.setString(4,b0111.split("@")[0]);
				}
				
				ps.setInt(5, i);
				ps.setString(6, zrrx1);
				ps.addBatch();
				i++;
			}
			ps.executeBatch();
			con.commit();
			ps.close();
			con.close();
		} catch (Exception e) {
			try {
				con.rollback();
				if (con != null) {
					con.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		this.toastmessage("排序已保存！");
		this.setNextEventName("gwGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("sort")
	@Transaction
	public int changeSort(String param) {
		String a0000 = param.split("@")[0];
		String type = param.split("@")[1];
		String fxyp00 = param.split("@")[2];
		HBSession session = HBUtil.getHBSession();
		try {
			@SuppressWarnings("unchecked")
			List<String> zwqc00= HBUtil.getHBSession().createSQLQuery("select zwqc00 from fxyp where  fxyp00='"+fxyp00+"'").list();
			if(zwqc00.size()>0) {
				if (type.equals("1")) {// 上移
					@SuppressWarnings("unchecked")
					List<String> sortnums= HBUtil.getHBSession().createSQLQuery("select sortnum from rxfxyp where zwqc00='"+zwqc00.get(0)+"' " + 
							" and a0000='"+a0000+"'").list();
//					PublishAtt pa = (PublishAtt) session.get(PublishAtt.class, pat00);
					if(sortnums.size()>0) {
						 int sortnum = Integer.parseInt(String.valueOf(sortnums.get(0)));
						if (sortnum == 1) {
							this.setMainMessage("已在最顶端！");
							return EventRtnType.NORMAL_SUCCESS;
						} else {
							int sort_before = sortnum - 1;
							HBUtil.executeUpdate("update rxfxyp set sortnum="+sortnum+" where zwqc00='"+zwqc00.get(0)+"' and sortnum="+sort_before); 
							HBUtil.executeUpdate("update rxfxyp set sortnum="+sort_before+" where zwqc00='"+zwqc00.get(0)+"' and a0000='"+a0000+"'"); 
						}
					}
					
				} else {// 下移
					@SuppressWarnings("unchecked")
					List<String> sortnums= HBUtil.getHBSession().createSQLQuery("select sortnum from rxfxyp where zwqc00='"+zwqc00.get(0)+"' " + 
							" and a0000='"+a0000+"'").list();
					if(sortnums.size()>0) {
						String max_sort = getMax_sort(zwqc00.get(0));
						 int sortnum = Integer.parseInt(String.valueOf(sortnums.get(0)));
						if (sortnum == Integer.valueOf(max_sort)) {
							this.setMainMessage("已在最底端！");
							return EventRtnType.NORMAL_SUCCESS;
						}
						int sort_next = sortnum+ 1;
						HBUtil.executeUpdate("update rxfxyp set sortnum="+sortnum+" where zwqc00='"+zwqc00.get(0)+"' and sortnum="+sort_next); 
						HBUtil.executeUpdate("update rxfxyp set sortnum="+sort_next+" where zwqc00='"+zwqc00.get(0)+"' and a0000='"+a0000+"'"); 
//						session.createSQLQuery("update Gbkh_att set sort=" + pa.getSort() + " where gbkhid='" + gbkhid
//								+ "' and sort=" + sort_next).executeUpdate();
//						pa.setSort(sort_next);
					}			
				}
			}
		}catch (Exception e) {
			this.setMainMessage("排序失败！");
			e.printStackTrace();
			return EventRtnType.FAILD;
		}
		this.setNextEventName("gwGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 获取最大的排序号
	 * 
	 * @return
	 */
	public String getMax_sort(String zwqc00) {
		HBSession session = HBUtil.getHBSession();
		String sort = session
				.createSQLQuery("select nvl(max(sortnum),0) from rxfxyp where zwqc00='"+zwqc00+"'")
				.uniqueResult().toString();
		return sort;
	}
	
//	@PageEvent("rolesort1")
//	@Transaction
//	public int publishsort1() throws RadowException, AppException {
//		String mntp00 = this.getPageElement("mntp00").getValue();
//		String b0111= this.getPageElement("b0111").getValue();
//		String fxyp07 = this.getPageElement("fxyp07").getValue();
//		String a0200=this.getPageElement("a0200").getValue();
//		List<HashMap<String, String>> list = this.getPageElement("rxGrid").getStringValueList();
//		HBSession sess = HBUtil.getHBSession();
//		Connection con = null;
//		if("1".equals(fxyp07)) {
//			try {
//				@SuppressWarnings("unchecked")
//				List<String> zwqc00= HBUtil.getHBSession().createSQLQuery("select zwqc00 from fxyp where  fxyp00='"+a0200+"'").list();
//				con = sess.connection();
//				con.setAutoCommit(false);
//				if(zwqc00.size()>0) {
//					String sql = "update  rxfxyp set sortnum= ? where zwqc00= ? and tp0101 = ?  and tp0102= ?";
//					PreparedStatement ps = con.prepareStatement(sql);
//					int i = 1;
//					for (HashMap<String, String> m : list) {
//						String tp0101=m.get("a0101");
//						String tp0102=m.get("a0107");
//						ps.setInt(1, i);
//						ps.setString(2, zwqc00.get(0));
//						ps.setString(3, tp0101);
//						ps.setString(4, tp0102);
//						ps.addBatch();
//						i++;
//					}
//					ps.executeBatch();
//					con.commit();
//					ps.close();
//					con.close();
//				}	
//			} catch (Exception e) {
//				try {
//					con.rollback();
//					if (con != null) {
//						con.close();
//					}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//				e.printStackTrace();
//				this.setMainMessage("排序失败！");
//				return EventRtnType.FAILD;
//			}
//			this.toastmessage("排序已保存！");
//		}
//		this.setNextEventName("rxGrid.dogridquery");
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	
	@PageEvent("removebind")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int bingGW(String fxyp00) throws RadowException, AppException {
		String mntp00=this.getPageElement("mntp00").getValue();
		String a0200=this.getPageElement("a0200").getValue();
		String zrrx=this.getPageElement("zrrx").getValue();
		String b0111=this.getPageElement("b0111").getValue();
		HBSession sess = HBUtil.getHBSession();
		try {
			@SuppressWarnings("unchecked")
			List<String> fxyp00ref= HBUtil.getHBSession().createSQLQuery("select fxyp00ref from fxyp where a0200='"+a0200+"' and  mntp00 = '"+mntp00+"'").list();
			@SuppressWarnings("unchecked")
			List<String> fxyp00nm= HBUtil.getHBSession().createSQLQuery("select fxyp00 from fxyp where a0200='"+a0200+"' and  mntp00 = '"+mntp00+"'").list();
			if(fxyp00ref.size()>0) {
				if(fxyp00ref.get(0)==null ||"".equals(fxyp00ref.get(0))) {
					this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','该职务未绑定',null,'220')");
					return EventRtnType.FAILD;
				}else {
					HBUtil.executeUpdate("update fxyp set fxyp00ref='' where fxyp00ref='"+fxyp00nm.get(0)+"'");
					HBUtil.executeUpdate("update fxyp set fxyp00ref='' where fxyp00='"+fxyp00nm.get(0)+"'");
				}
			}
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','绑定失败！',null,'220')");
			e.printStackTrace();
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','解除绑定成功！',null,'220')");
		this.setNextEventName("rxGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
}