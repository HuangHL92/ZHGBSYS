package com.insigma.siis.local.pagemodel.gwdz;

import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.cadremgn.sysbuilder.CreateDefinePageModel;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONObject;
    
public class CHOOSEdwPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String famx01 = this.getPageElement("famx01").getValue();
		String mntp00 = this.getPageElement("mntp00").getValue();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			if("2".equals(famx01)) {
				@SuppressWarnings("unchecked")
				List<String> mntplist= HBUtil.getHBSession().createSQLQuery("select mntp00 from hz_mntp y where y.mntp03='"+SysManagerUtils.getUserId()+"' or "
						+ " (exists (select 1 from hz_mntpfa_userref u where mnur01='"+SysManagerUtils.getUserId()+"' and u.mntp00=y.mntp00)) order by mntp02 desc").list();
				if(mntplist.size()>0) {
					for(int i=0;i<mntplist.size();i++) {
						@SuppressWarnings("unchecked")
						List<String> addname= HBUtil.getHBSession().createSQLQuery("select mntp04 from hz_mntp where mntp00='"+mntplist.get(i)+"'").list();
						map.put(mntplist.get(i), addname.get(0));
					}
				}
			}
			((Combo)this.getPageElement("mntplist")).setValueListForSelect(map); 
			this.getPageElement("mntplist").setValue("");
			if( "1".equals(famx01) ) {
				this.getExecuteSG().addExecuteCode("updateTree('');");	
			}else if((mntp00!=null && !"".equals(mntp00)) && !"null".equals(mntp00)){
				this.getExecuteSG().addExecuteCode("updateTree('"+mntp00+"');");
				this.getPageElement("mntplist").setValue(mntp00);
			}
						
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 保存
	 */         
	@PageEvent("save")
	@Transaction
	public int save(String json) throws RadowException {
		String famx01 = this.getPageElement("famx01").getValue();
		String famx00 = this.getPageElement("famx00").getValue();
		String mntp00 = this.getPageElement("mntplist").getValue();
		try{
			JSONObject o=JSONObject.fromObject(json);
			Iterator<?> i=o.keys();
			CreateDefinePageModel cdpm=new CreateDefinePageModel();
			String key="";
			String value="";
			String sql="";
			String table_code="";
			HBSession hbsess = HBUtil.getHBSession();
			Statement  stmt = hbsess.connection().createStatement();
			sql="delete from HZ_MNTP_FABD_ORG where famx00='"+famx00+"'";
			stmt.executeUpdate(sql);
			while(i.hasNext()){
				key=i.next().toString();
				value=o.getString(key);
				if(value.contains("@")) {
					String b0111=value.split("@")[0];
					String b0131=value.split("@")[1];
				    table_code=key.substring(0,key.length()-value.length());
					if("-1".equals(table_code)){
						continue;
					}else{
						sql="insert into HZ_MNTP_FABD_ORG (famx00,b0111,b0131) values ('"+famx00+"','"+b0111+"','"+b0131+"')";
						stmt.executeUpdate(sql);
					}
				}				
			}
			if("2".equals(famx01)) {
				sql="update HZ_MNTP_FABD_FAMX set mntp00='"+mntp00+"' where famx00='"+famx00+"'";
				stmt.executeUpdate(sql);
			}else if("1".equals(famx01)) {
				sql="update HZ_MNTP_FABD_FAMX set mntp00='' where famx00='"+famx00+"'";
				stmt.executeUpdate(sql);
			}
			stmt.close();
			this.getExecuteSG().addExecuteCode("alert('保存成功');window.close();");
			this.getExecuteSG().addExecuteCode("realParent.reload()");
		}catch(Exception e){
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		return 1;
	}
	
	@PageEvent("clean")
	@Transaction
	public int clean() throws RadowException {
		String famx00 = this.getPageElement("famx00").getValue();
		try {
			HBSession hbsess = HBUtil.getHBSession();
			Statement  stmt = hbsess.connection().createStatement();
			String sql="delete from HZ_MNTP_FABD_ORG where famx00='"+famx00+"'";
			stmt.executeUpdate(sql);
			stmt.close();
		}catch(Exception e){
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		return 1;
	}
	
	
	
	@PageEvent("definedList")
	public int definedList() throws Exception {
		String famx01 = this.getParameter("famx01");
		String famx00 = this.getParameter("famx00");
		String mntp00=this.getParameter("mntp00");
		StringBuffer jsonStr = new StringBuffer();
		CommQuery cqbs=new CommQuery();
		String userid = SysManagerUtils.getUserId();
		try {
			String sql4="";
			if("1".equals(famx01)) {
				String sql="select b0111 from b01 where   b0111 like '001.001.004%'  and length(b0111)=15 order by b0269";
				@SuppressWarnings("unchecked")
				List<String> b0111list= HBUtil.getHBSession().createSQLQuery(sql).list();
				/*
				 * List<HashMap<String, Object>> b0111; listuser4 = cqbs.getListBySQL(sql4);
				 */

				if (b0111list != null && b0111list.size() > 0) {	
					boolean flag=false;
					jsonStr.append("[");
					jsonStr.append("{\"text\" :\"区县市\" ,\"id\" :\"7961321DB1D84E80BBAD5CC723B75A4D\" ,\"cls\" :\"folder\" ,\"checked\" :"+flag+"");
					jsonStr.append(",\"children\" :[");
					
					for (int i = 0; i < b0111list.size(); i++) {
						sql4="select (select b0101 from b01 where b0111='"+b0111list.get(i)+"') b0101, '"+b0111list.get(i)+"' b0111,b0131," + 
							" (select count(*) from hz_mntp_fabd_org h where h.famx00='"+famx00+"' and  h.b0111='"+b0111list.get(i)+"' and b01.b0131=h.b0131) num " + 
							" from b01 where b0111 like '"+b0111list.get(i)+"%' and  b0131 in ('1001','1003','1004','1005') " + 
							" order by b0269";
						List<HashMap<String, Object>> listuser;
						listuser = cqbs.getListBySQL(sql4);
						if (listuser != null && listuser.size() > 0) {	
							for (int j = 0; j < listuser.size(); j++) {
								String b0111 =listuser.get(j).get("b0111").toString();
								String b0131 =listuser.get(j).get("b0131").toString();
								String b0101 = listuser.get(j).get("b0101").toString();
								String num = listuser.get(j).get("num").toString();
								boolean str=false;
								if("1".equals(num)){
									str= true;
								}
								String zrrxname="";
								if("1001".equals(b0131)) {
									zrrxname="党委";
								}else if("1004".equals(b0131)) {
									zrrxname="政府";
								}else if("1003".equals(b0131)) {
									zrrxname="人大";
								}else if("1005".equals(b0131)) {
									zrrxname="政协";
								}
								jsonStr.append("{\"text\" :\""
										+ b0101+zrrxname
										+ "\" ,\"id\" :\""
										+ b0111+'@'+b0131
										+ "\" ,\"checked\" :"
										+ str
										+ " ,\"leaf\" :\"true\" ,\"cls\" :\"folder\"  }");
				
								if (i != b0111list.size() - 1 || j!=listuser.size()-1) {
									jsonStr.append(",");
								}
								
							}
						}
					}
					jsonStr.append("]}]");
				}	
			}else if("2".equals(famx01)) {
				String sql="select b0111 from( select distinct  b0111,c.b0269 from b01 c,fxyp " + 
						"where c.b01id=fxyp.b01id and fxyp.fxyp07 is not null and fxyp.mntp00='"+mntp00+"'" + 
						" and b0111 like '001.001.004%' and length(b0111)=15 " + 
						"  order by c.b0269)";
				@SuppressWarnings("unchecked")
				List<String> b0111list= HBUtil.getHBSession().createSQLQuery(sql).list();
				if (b0111list != null && b0111list.size() > 0) {	
					boolean flag=false;
					jsonStr.append("[");
					jsonStr.append("{\"text\" :\"区县市\" ,\"id\" :\"7961321DB1D84E80BBAD5CC723B75A4D\" ,\"cls\" :\"folder\" ,\"checked\" :"+flag+"");
					jsonStr.append(",\"children\" :[");
					for (int i = 0; i < b0111list.size(); i++) {
						sql4="select distinct * from (" + 
								"select   (select b0101 from b01 where b0111='"+b0111list.get(i)+"') b0101, '"+b0111list.get(i)+"' b0111," + 
								"        zrrx b0131," + 
								"        (select count(*) from hz_mntp_fabd_org h where h.famx00='"+famx00+"' and  h.b0111='"+b0111list.get(i)+"' and zrrx=h.b0131) num " + 
								"      from( select *" + 
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
								"                               ('1001', '1003', '1004', '1005')" + 
								"                           and b.b0111 like '"+b0111list.get(i)+".%')" + 
								"                   and (select gw.fxyp07" + 
								"                          from hz_mntp_gw gw" + 
								"                         where gw.a0200 = a02.a0200" + 
								"                           and gw.mntp00 =" + 
								"                               '"+mntp00+"') is not null" + 
								"                   and not exists" + 
								"                 (select t.a0200" + 
								"                          from hz_mntp_hhmd t" + 
								"                         where a02.a0200 = t.a0200" + 
								"                           and t.mdtype = '1'" + 
								"                        minus" + 
								"                        select a01bzid" + 
								"                          from hz_mntp_bz" + 
								"                         where a01bztype = '1'" + 
								"                           and bmd = '1'" + 
								"                           and a01bzid = a02.a0200" + 
								"                           and mntp00 = '"+mntp00+"')" + 
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
								"                                       and a0201b like '"+b0111list.get(i)+"%') t" + 
								"                             where rn = 1" + 
								"                               and t.a0000 = a01.a0000))) a01" + 
								"        union all" + 
								"        select *" + 
								"          from (select decode(length(t.fxyp06)," + 
								"                              4," + 
								"                              t.fxyp06," + 
								"                              3," + 
								"                              t.fxyp06," + 
								"                              t.a0201e) zrrx" + 
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
								"                       '"+b0111list.get(i)+"'" + 
								"                   and b.a01bzid(+) = t.tp0100" + 
								"                 order by t.fxyp00, t.sortnum)))" + 
								"order by b0131";
						List<HashMap<String, Object>> listuser;
						listuser = cqbs.getListBySQL(sql4);
						if (listuser != null && listuser.size() > 0) {	
							for (int j = 0; j < listuser.size(); j++) {
								String b0111 =listuser.get(j).get("b0111").toString();
								String b0131 =listuser.get(j).get("b0131").toString();
								String b0101 = listuser.get(j).get("b0101").toString();
								String num = listuser.get(j).get("num").toString();
								boolean str=false;
								if("1".equals(num)){
									str= true;
								}
								String zrrxname="";
								if("1001".equals(b0131)) {
									zrrxname="党委";
								}else if("1004".equals(b0131)) {
									zrrxname="政府";
								}else if("1003".equals(b0131)) {
									zrrxname="人大";
								}else if("1005".equals(b0131)) {
									zrrxname="政协";
								}else {
									continue;
								}
								jsonStr.append("{\"text\" :\""
										+ b0101+zrrxname
										+ "\" ,\"id\" :\""
										+ b0111+'@'+b0131
										+ "\" ,\"checked\" :"
										+ str
										+ " ,\"leaf\" :\"true\" ,\"cls\" :\"folder\"  }");
				
								if (i != b0111list.size() - 1 || j!=listuser.size()-1) {
									jsonStr.append(",");
								}
								
							}
						}
					}
					jsonStr.append("]}]");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
}
