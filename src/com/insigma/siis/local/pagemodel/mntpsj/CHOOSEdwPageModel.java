package com.insigma.siis.local.pagemodel.mntpsj;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.mntpsj.base.FABDUtil;

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
		//String famx01 = this.getPageElement("famx01").getValue();
		//String famx00 = this.getPageElement("famx00").getValue();
		//Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			
//			if("2".equals(famx01)) {
//				map.put("1","取消关联");
//				@SuppressWarnings("unchecked")
//				List<String> famxlist= HBUtil.getHBSession().createSQLQuery("select famx00 from HZ_MNTP_SJFA_famx x,HZ_MNTP_SJFA a where a.fabd00=x.fabd00 and a.fabd04='"+SysManagerUtils.getUserId()+"' and x.famx01='2' and x.famx00<>'"+famx00+"'").list();
//				if(famxlist.size()>0) {
//					for(int i=0;i<famxlist.size();i++) {
//						@SuppressWarnings("unchecked")
//						List<String> addname= HBUtil.getHBSession().createSQLQuery("select a.fabd02 || '_' ||x.famx03 from  HZ_MNTP_SJFA_famx x,HZ_MNTP_SJFA a " + 
//								"where a.fabd00=x.fabd00 and x.famx00='"+famxlist.get(0)+"'" + 
//								"").list();
//						map.put(famxlist.get(i), addname.get(0));
//					}
//				}
//				
//			}
//			((Combo)this.getPageElement("famxlist")).setValueListForSelect(map); 
			//this.getPageElement("famxlist").setValue("");
			this.getExecuteSG().addExecuteCode("updateTree('');");	
//			if( "1".equals(famx01) ) {
//				this.getExecuteSG().addExecuteCode("updateTree('');");	
//			}else{
//				@SuppressWarnings("unchecked")
//				List<String> famxreflist= HBUtil.getHBSession().createSQLQuery("select famx00ref from HZ_MNTP_SJFA_famx x where x.famx00='"+famx00+"'").list();
//				if(famxreflist.size()>0) {
//					if(famxreflist.get(0)!=null && !"".equals(famxreflist.get(0))) {
//						@SuppressWarnings("unchecked")
//						List<String> refaddname= HBUtil.getHBSession().createSQLQuery("select a.fabd02 || '_' ||x.famx03 from  HZ_MNTP_SJFA_famx x,HZ_MNTP_SJFA a " + 
//								"where a.fabd00=x.fabd00 and x.famx00='"+famxreflist.get(0)+"'" + 
//								"").list();
//						this.getPageElement("famxlist").setValue(refaddname.get(0));
//						this.getExecuteSG().addExecuteCode("updateTree('"+refaddname.get(0)+"');");	
//					}else {
//						this.getExecuteSG().addExecuteCode("updateTree('');");	
//					}		
//				}else {
//					this.getExecuteSG().addExecuteCode("updateTree('');");	
//				}
//				
//			}
						
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 保存
	 * @throws AppException 
	 */         
	@PageEvent("save")
	@Transaction
	public int save(String json) throws RadowException, AppException {
		if(!StringUtils.isEmpty(json)){
			saveFA(json, null, null, "1");
			String famx00 = this.getPageElement("famx00").getValue();
			String famx01 = this.getPageElement("famx01").getValue();
			String fabd00 = HBUtil.getValueFromTab("fabd00", "HZ_MNTP_SJFA_famx", "famx00='"+famx00+"'");
			//模拟方案选择单位默认现状也选择
			if("2".equals(famx01)){
				try {
					
					String sql="select famx00,fabd00,famx01,famx02,famx03 "
							+ " from HZ_MNTP_SJFA_famx t where t.fabd00='"+fabd00+"' and famx01='1' order by t.famx02";
					CommQuery cqbs=new CommQuery();
					
					List<HashMap<String, String>> list = cqbs.getListBySQL2(sql);
					for(HashMap<String, String> m : list){
						saveFA(json, m.get("famx00"), "1", "1");
					}
				} catch (AppException e) {
					e.printStackTrace();
				}
			}
			try {
				String famx03 = HBUtil.getValueFromTab("famx03", "HZ_MNTP_SJFA_famx", "famx00='"+famx00+"'");
				String fabd02 = HBUtil.getValueFromTab("fabd02", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
				new LogUtil().createLogNew("方案明细单位选择","方案明细单位选择","模拟调配方案明细单位表",famx00,fabd02+"("+famx03+")", new ArrayList());
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			
			this.getExecuteSG().addExecuteCode("window.close();");
			this.getExecuteSG().addExecuteCode("realParent.reload()");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	
	/**
	 * 
	 * @param json
	 * @param famx00
	 * @param famx01
	 * @param type  区县市 1 为新增空白方案 2为新增方案，模拟情况的人默认为现状
	 * @return
	 * @throws RadowException
	 */
	public int saveFA(String json,String famx00,String famx01,String addType) throws RadowException {
		if(StringUtils.isEmpty(famx00)){
			famx00 = this.getPageElement("famx00").getValue();
			famx01 = this.getPageElement("famx01").getValue();
		}
		try{
			JSONObject o=JSONObject.fromObject(json);
			Iterator<?> i=o.keys();
			String key="";
			String value="";
			String sql="";
			String table_code="";

			HBSession hbsess = HBUtil.getHBSession();
			Statement  stmt = hbsess.connection().createStatement();
			sql="update HZ_MNTP_SJFA_famx set famx00ref='' where famx00='"+famx00+"'";
			stmt.executeUpdate(sql);
			sql="update HZ_MNTP_SJFA_ORG set status='2',famx01='"+famx01+"' where famx00='"+famx00+"'";
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
						@SuppressWarnings("unchecked")
						List<String> exists= HBUtil.getHBSession().createSQLQuery("select b0111 from HZ_MNTP_SJFA_org a where a.famx00='"+famx00+"' and b0111='"+b0111+"' and b0131='"+b0131+"'").list();
						if(exists.size()>0) {
							String sql1="update HZ_MNTP_SJFA_ORG set status='1',famx01='"+famx01+"' where famx00='"+famx00+"' and b0111='"+b0111+"' and b0131='"+b0131+"'";
							stmt.executeUpdate(sql1);
						}else {
							String sjdw00=UUID.randomUUID().toString();
							sql="insert into HZ_MNTP_SJFA_ORG (famx00,b0111,mntp05,b0131,sjdw00,status,famx01,orgsort) values ('"+famx00+"','"+b0111+"','2','"+b0131+"','"+sjdw00+"','1','"+famx01+"',(select b0269 from b01 where b0111='"+b0111+"'))";
							stmt.executeUpdate(sql);
							/*sql="insert into HZ_FXYP_SJFA(fxyp00,fxyp01,fxyp02,fxyp05,a0201e,status,b0111,b0131,sjdw00,famx00,zwqc00,zwcode) " + 
									"select sys_guid() fxyp00,sortid fxyp01,gwname fxyp02,'"+SysManagerUtils.getUserId()+"' fxyp05,gwtype a0201e,'1' status" + 
									",'"+b0111+"' b0111,'"+b0131+"'b0131,'"+sjdw00+"' sjdw00,'"+famx00+"' famx00,zwqc00,gwcode from HZ_TPGDGWB " + 
									"where b01type='2' and b0131='"+b0131+"'";
							stmt.executeUpdate(sql);*/
							String selectSql = FABDUtil.getXZXXSZSQL(b0111);
							//1区县市不加人员  2政府联动的岗位 不直接添加人，   没有设置班子岗位代码的
							sql = "insert all"
								+ " when 1 = 1 then into HZ_FXYP_SJFA(fxyp00,fxyp01,fxyp02,fxyp05,a0201e,status,b0111,sjdw00,famx00,a0200,bzgw) values(fxyp00,fxyp01,fxyp02,fxyp05,a0201e,status,b0111,sjdw00,famx00,a0200,bzgw)"
								+ " when decode(a0000,null,0,'',0,1)=1 and (substr(b0111,0,11)!='001.001.004' or '2'='"+addType+"') and bzgw not in ('201','202','209') then into hz_rxfxyp_SJFA(tp0100,a0000,type,tp0101,tp0102,tp0103,tp0104,tp0105,tp0106,sortnum,fxyp00,a0200) values(tp0100,a0000,type,tp0101,tp0102,tp0103,tp0104,tp0105,tp0106,sortnum,fxyp00,a0200)"
								+ " select sys_guid() fxyp00,rownum fxyp01,a0215a fxyp02,'"+SysManagerUtils.getUserId()+"' fxyp05,"
								+ " a0201e,'1' status,'"+b0111+"' b0111,'"+sjdw00+"' sjdw00,'"+famx00+"' famx00, "
								+ " sys_guid() tp0100,a0000,'3' type,a0101 tp0101,a0107 tp0102,a0192f tp0103,a0288 tp0104,"
								+ " tp0105,a0192a tp0106,1 sortnum,a0200,bzgw"
								+ " from ("+selectSql+")";
						
							stmt.executeUpdate(sql);
						}
												
					}
				}else {
					 String b0111=value;
					 table_code=key.substring(0,key.length()-value.length());
						if("-1".equals(table_code)){
							continue;
						}else{
							@SuppressWarnings("unchecked")
							List<String> exists= HBUtil.getHBSession().createSQLQuery("select b0111 from HZ_MNTP_SJFA_org a where a.famx00='"+famx00+"' and b0111='"+b0111+"' ").list();
							if(exists.size()>0) {
								String sql1="update HZ_MNTP_SJFA_ORG set status='1',famx01='"+famx01+"' where famx00='"+famx00+"' and b0111='"+b0111+"' ";
								stmt.executeUpdate(sql1);
							}else {
								String sjdw00=UUID.randomUUID().toString();
								if("001.001.003".equals(b0111.substring(0, 11))) {
									sql="insert into HZ_MNTP_SJFA_ORG (famx00,b0111,mntp05,sjdw00,status,famx01,orgsort) values ('"+famx00+"','"+b0111+"','4','"+sjdw00+"','1','"+famx01+"',(select b0269 from b01 where b0111='"+b0111+"'))";
									stmt.executeUpdate(sql);
								
								}else {
									sql="insert into HZ_MNTP_SJFA_ORG (famx00,b0111,mntp05,sjdw00,status,famx01,orgsort) values ('"+famx00+"','"+b0111+"','1','"+sjdw00+"','1','"+famx01+"',(select b0269 from b01 where b0111='"+b0111+"'))";
									stmt.executeUpdate(sql);
								}
								
								/*sql="insert into HZ_FXYP_SJFA(fxyp00,fxyp01,fxyp02,fxyp05,a0201e,status,b0111,sjdw00,famx00,zwqc00,zwcode) " + 
										"select sys_guid() fxyp00,sortid fxyp01,gwname fxyp02,'"+SysManagerUtils.getUserId()+"' fxyp05,gwtype a0201e,'1' status" + 
										",'"+b0111+"' b0111,'"+sjdw00+"' sjdw00,'"+famx00+"' famx00,zwqc00,gwcode from HZ_TPGDGWB " + 
										"where b01type='1'";
								stmt.executeUpdate(sql);*/
								String selectSql = FABDUtil.getXZXXSZSQL(b0111);
								sql = "insert all"
									+ " when 1 = 1 then into HZ_FXYP_SJFA(fxyp00,fxyp01,fxyp02,fxyp05,a0201e,status,b0111,sjdw00,famx00,a0200) values(fxyp00,fxyp01,fxyp02,fxyp05,a0201e,status,b0111,sjdw00,famx00,a0200)"
									+ " when decode(a0000,null,0,'',0,1)=1 then into hz_rxfxyp_SJFA(tp0100,a0000,type,tp0101,tp0102,tp0103,tp0104,tp0105,tp0106,sortnum,fxyp00,a0200) values(tp0100,a0000,type,tp0101,tp0102,tp0103,tp0104,tp0105,tp0106,sortnum,fxyp00,a0200)"
									+ " select sys_guid() fxyp00,rownum fxyp01,a0215a fxyp02,'"+SysManagerUtils.getUserId()+"' fxyp05,"
									+ " a0201e,'1' status,'"+b0111+"' b0111,'"+sjdw00+"' sjdw00,'"+famx00+"' famx00, "
									+ " sys_guid() tp0100,a0000,'3' type,a0101 tp0101,a0107 tp0102,a0192f tp0103,a0288 tp0104,"
									+ " tp0105,a0192a tp0106,1 sortnum,a0200"
									+ " from ("+selectSql+")";
							
								stmt.executeUpdate(sql);
							}
							
							
						}
				}
			}
			@SuppressWarnings("unchecked")
			List<Object[]> b0111list= HBUtil.getHBSession().createSQLQuery("select distinct b0111,orgsort,b0131 from HZ_MNTP_SJFA_org a where a.famx00='"+famx00+"' and b0131 in ('1003','1004')").list();
			Map<String,String> updateParm = new HashMap<String, String>();
			
			//区长副书记绑定
			sql="update HZ_FXYP_SJFA a set zwqc00=famx00||substr(b0111,0,15)||'102201' " 
					+ " where famx00='"+famx00+"' and length(b0111)=19 and substr(b0111,0,11)='001.001.004'"
					+ " and bzgw in('102','201') ";
			stmt.executeUpdate(sql);
			//常委副区长
			sql="update HZ_FXYP_SJFA a set zwqc00=famx00||substr(b0111,0,15)||'108202' " 
					+ " where famx00='"+famx00+"' and length(b0111)=19 and substr(b0111,0,11)='001.001.004'"
					+ " and bzgw in('108','202') ";
			stmt.executeUpdate(sql);
			//常委副区长
			sql="update HZ_FXYP_SJFA a set zwqc00=famx00||substr(b0111,0,15)||'115209' " 
					+ " where famx00='"+famx00+"' and length(b0111)=19 and substr(b0111,0,11)='001.001.004'"
					+ " and bzgw in('115','209') ";
			stmt.executeUpdate(sql);
			
			
			
			if(b0111list.size()>0) {
				for(int k=0;k<b0111list.size();k++) {
					Object[] b0111s= b0111list.get(k);
					String b0111 = b0111s[0]==null?"":b0111s[0].toString();
					String zwqc00=UUID.randomUUID().toString().replace("-", "");
					/*sql="update HZ_FXYP_SJFA a set zwqc00='"+zwqc00+"' " + 
							"where famx00='"+famx00+"' and b0111='"+b0111s+"'  and a.zwcode in (select gwcode from HZ_TPGDGWB t where zwqc00='1' and t.gwcode=a.zwcode)";
					stmt.executeUpdate(sql);*/
					
					//人大政府换位子
					if(b0111.length()==19&&b0111.startsWith("001.001.004")){
						String qx = b0111.substring(0,15);
						String b0131 = b0111s[2]==null?"":b0111s[2].toString();
						
						//人大政府换位子
						if("1003,1004".contains(b0131)){
							updateParm.put(qx+"@"+b0131, b0111);
						}
					}
				}
				for(String k : updateParm.keySet()){
					String[] keys = k.split("@");
					String values = updateParm.get(k);
					if("1003".equals(keys[1])){
						stmt.executeUpdate("update HZ_MNTP_SJFA_org set orgsort=(select b0269 from b01 where length(b0111)=19 and substr(b0111,0,15)='"+keys[0]+"' and b0131 ='1004') where b0111='"+values+"' and famx00='"+famx00+"'");
					}else{
						stmt.executeUpdate("update HZ_MNTP_SJFA_org set orgsort=(select b0269 from b01 where length(b0111)=19 and substr(b0111,0,15)='"+keys[0]+"' and b0131 ='1003') where b0111='"+values+"' and famx00='"+famx00+"'");
					}
				}
			}
			stmt.close();
			
		}catch(Exception e){
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	
	
	@PageEvent("definedList")
	public int definedList() throws Exception {
		String famx01 = this.getParameter("famx01");
		String famx00 = this.getParameter("famx00");
		String famx00ref=this.getParameter("famx00ref");
		StringBuffer jsonStr = new StringBuffer();
		CommQuery cqbs=new CommQuery();
		String userid = SysManagerUtils.getUserId();
		try {
			if(famx00ref==null || "".equals(famx00ref) ||"1".equals(famx00ref) ) {
				boolean flag=false;
				jsonStr.append("[");
				//区县
				String sql4="";
				String sql="select b0111 from b01 where   b0111 like '001.001.004%'  and length(b0111)=15 order by b0269";
				@SuppressWarnings("unchecked")
				List<String> b0111list= HBUtil.getHBSession().createSQLQuery(sql).list();
				jsonStr.append("{\"text\" :\"区县市\" ,\"id\" :\"7961321DB1D84E80BBAD5CC723B75A4D\" ,\"cls\" :\"folder\" ,\"checked\" :"+flag+"");
				jsonStr.append(",\"children\" :[");
				
				for (int i = 0; i < b0111list.size(); i++) {
					sql4="select (select b0101 from b01 where b0111='"+b0111list.get(i)+"') b0101, b0101 b0101a,'"+b0111list.get(i)+"' b0111,b0131,b0111 b01b0111," + 
						" (select count(*) from HZ_MNTP_SJFA_ORG h where h.famx00='"+famx00+"' and  h.b0111=b01.b0111  and status='1') num " + 
						" from b01 where b0111 like '"+b0111list.get(i)+"%' and  length(b0111)=19 and b0194='1' " + 
						" order by b0269";
					List<HashMap<String, Object>> listuser4;
					listuser4 = cqbs.getListBySQL(sql4);
					if (listuser4 != null && listuser4.size() > 0) {	
						for (int j = 0; j < listuser4.size(); j++) {
							String b0111 =listuser4.get(j).get("b01b0111").toString();
							String b0131 =listuser4.get(j).get("b0131").toString();
							String b0101 = listuser4.get(j).get("b0101").toString();
							String b0101a = listuser4.get(j).get("b0101a").toString();
							String num = listuser4.get(j).get("num").toString();
							boolean str=false;
							if("1".equals(num)){
								str= true;
							}
							String zrrxname="";
							if("1001".equals(b0131)) {
								zrrxname=b0101+"党委";
							}else if("1004".equals(b0131)) {
								zrrxname=b0101+"政府";
							}else if("1003".equals(b0131)) {
								zrrxname=b0101+"人大";
							}else if("1005".equals(b0131)) {
								zrrxname=b0101+"政协";
							}else if("1006".equals(b0131)) {
								zrrxname=b0101+"法院";
							}else if("1007".equals(b0131)) {
								zrrxname=b0101+"检察院";
							}else {
								zrrxname=b0101a;
							}
							jsonStr.append("{\"text\" :\""
									+ zrrxname
									+ "\" ,\"id\" :\""
									+ b0111+'@'+b0131
									+ "\" ,\"checked\" :"
									+ str
									+ " ,\"leaf\" :\"true\" ,\"cls\" :\"folder\"  }");
			
							if (i != b0111list.size() - 1 || j!=listuser4.size()-1) {
								jsonStr.append(",");
							}
							
						}
					}
				}
				jsonStr.append("]},");
				//国企高校
				String sql3="select b0111,b0101,(select count(*) from HZ_MNTP_SJFA_ORG h  where h.b0111=a.b0111 and h.famx00='"+famx00+"'  and status='1') num from b01 a where b0111 like '001.001.003%' and length(b0111)=15 order by b0269";
				List<HashMap<String, Object>> listuser3;
				listuser3 = cqbs.getListBySQL(sql3);
				if (listuser3 != null && listuser3.size() > 0) {
					jsonStr.append("{\"text\" :\"国企高校\" ,\"id\" :\"11D6D8E4C53E4AAEA68B95CA5417FE53\" ,\"cls\" :\"folder\" ,\"checked\" :"+flag+"");
					jsonStr.append(",\"children\" :[");
					
					for (int j = 0; j < listuser3.size(); j++) {
						String b0111 =listuser3.get(j).get("b0111").toString();// 用户组ID
						String b0101 = listuser3.get(j).get("b0101").toString();// 用户组名称
						String num = listuser3.get(j).get("num").toString();// 
						boolean str=false;
						if("1".equals(num)){
							str= true;
						}
						
						jsonStr.append("{\"text\" :\""
								+ b0101
								+ "\" ,\"id\" :\""
								+ b0111
								+ "\" ,\"checked\" :"
								+ str
								+ " ,\"cls\" :\"folder\"  ");

						jsonStr.append(",\"leaf\" :\"true\" ");

		
						if (j != listuser3.size() - 1) {
							jsonStr.append("},");
						}else {
							jsonStr.append("}");
						}
		
					}
					jsonStr.append("]},");
				}
				//市直
				String sql2="select b0111,b0101,(select count(*) from HZ_MNTP_SJFA_ORG h  where h.b0111=a.b0111 and h.famx00='"+famx00+"'  and status='1') num from b01 a where b0111 like '001.001.002%' and length(b0111)=15 order by b0269";
				List<HashMap<String, Object>> listuser2;
				listuser2 = cqbs.getListBySQL(sql2);
				if (listuser2 != null && listuser2.size() > 0) {
					jsonStr.append("{\"text\" :\"市直单位\" ,\"id\" :\"68EBAC3D5C24449AAE6CAEA289A331F4\" ,\"cls\" :\"folder\" ,\"checked\" :"+flag+"");
					jsonStr.append(",\"children\" :[");
					
					for (int j = 0; j < listuser2.size(); j++) {
						String b0111 =listuser2.get(j).get("b0111").toString();// 用户组ID
						String b0101 = listuser2.get(j).get("b0101").toString();// 用户组名称
						String num = listuser2.get(j).get("num").toString();// 
						boolean str=false;
						if("1".equals(num)){
							str= true;
						}
						
						jsonStr.append("{\"text\" :\""
								+ b0101
								+ "\" ,\"id\" :\""
								+ b0111
								+ "\" ,\"checked\" :"
								+ str
								+ " ,\"cls\" :\"folder\"  ");
						if("001.001.002.01O".equals(b0111) || "001.001.002.01Q".equals(b0111) || "001.001.002.02O".equals(b0111) ) {
							jsonStr.append(",\"children\" :[");
							String sqladd="select b0111,b0101,(select count(*) from HZ_MNTP_SJFA_ORG h  where h.b0111=a.b0111 and h.famx00='"+famx00+"'  and status='1') num from b01 a where b0111 like '"+b0111+".%' and instr(b0101,'处级')<=0 order by b0269";
							List<HashMap<String, Object>> listuseradd;
							listuseradd = cqbs.getListBySQL(sqladd);
							if (listuseradd != null && listuseradd.size() > 0) {
								for(int u=0;u<listuseradd.size();u++) {
									String b0111add=listuseradd.get(u).get("b0111").toString();
									String b0101add=listuseradd.get(u).get("b0101").toString();
									String numadd=listuseradd.get(u).get("num").toString();
									boolean stradd=false;
									if("1".equals(numadd)){
										stradd= true;
									}
									jsonStr.append("{\"text\" :\""
											+ b0101add
											+ "\" ,\"id\" :\""
											+ b0111add
											+ "\" ,\"checked\" :"
											+ stradd
											+ " ,\"leaf\" :\"true\" ,\"cls\" :\"folder\" } ");
									if (u != listuseradd.size() - 1) {
										jsonStr.append(",");
									}
								}				
							}
							jsonStr.append("]");
						}else {
							jsonStr.append(",\"leaf\" :\"true\" ");
						}
		
						if (j != listuser2.size() - 1) {
							jsonStr.append("},");
						}else {
							jsonStr.append("}");
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
