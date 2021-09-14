package com.insigma.siis.local.pagemodel.mntpsj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

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
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.mntpsj.base.FABDUtil;

public class QXDWPageModel  extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.getExecuteSG().addExecuteCode("init();");
		return 0;
	}
	
	@PageEvent("updateGrid")
	public int updateGrid() {
		try {
			String fabd00 = this.getPageElement("fabd00").getValue();
			String famx00sel = this.getPageElement("famx00sel").getValue();
			CommQuery cqbs=new CommQuery();
			String sql="select * from HZ_MNTP_SJFA where fabd00='"+fabd00+"'";
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			this.getPageElement("mntpname").setValue(list.get(0).get("fabd02").toString());
			
			
			list = cqbs.getListBySQL(
					"select famx00,famx03 from HZ_MNTP_SJFA_famx where fabd00='"+fabd00+"' "
					+ "  and famx01='2' order by famx02 ");
			Map<String, String> map = new LinkedHashMap<String, String>();
			if (list != null && list.size() > 0) {
				for (HashMap<String, Object> map1 : list) {
					map.put(map1.get("famx00").toString(), map1.get("famx03").toString());
				}
				((Combo)this.getPageElement("famx00")).setValueListForSelect(map);
				
			}
			this.getPageElement("famx00").setValue(famx00sel);
			
			
			
			String b0111=this.getPageElement("b0111").getValue();
			
			String a0000=this.getPageElement("a0000").getValue();
			@SuppressWarnings("unchecked")
			List<String> a0101s = HBUtil.getHBSession().createSQLQuery("select a0101 from a01 where a0000='"+a0000+"'").list();
			if(a0101s.size()>0) {
				this.getPageElement("tp0101").setValue(a0101s.get(0));
			}
			

			@SuppressWarnings("unchecked")
			List<String> b0101s = HBUtil.getHBSession().createSQLQuery("select b0101 from b01 where b0111='"+b0111+"'").list();
			if(b0101s.size()>0) {
				this.getPageElement("b0101").setValue(b0101s.get(0));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("allGrid.dogridquery")
	@Transaction
	@Synchronous(true)
	public int allSelect(int start, int limit) throws RadowException, AppException, PrivilegeException {
		String b0111 = this.getPageElement("mntp_b01").getValue();
		String qxwhere = " and a02.a0201e in ('1','3') and a01.a0221 is not null ";
		try {
			
			String sql = "select *" + 
					"  from (select a02.a0200 fxyp00," + 
					"               a01.a0000," + 
					"               a01.a0101," + 
					"               a02.a0215a," + 
					"               a0165," + 
					"               a0201b," + 
					"               '"+b0111+"' b0111," + 
					"               '' b0131," + 
					"               a02.a0225 ,a02.a0201e,"
					+ "				decode(a01.a0288,null,null,substr(a01.a0288, 0, 4) || '.' || substr(a01.a0288, 5, 2)) a0288,"
					+ "				GET_TPBXUELI2(a01.qrzxl,a01.zzxl,a01.qrzxw,a01.zzxw) tp0105,a0192a" + 
					"          from a02, a01" +
					"         WHERE a01.A0000 = a02.a0000  " + 
					"           AND a01.a0163 = '1'" + 
					"           and a01.status = '1'" + 
					"           and a02.a0281 = 'true'" + 
					"           AND a02.a0255 = '1'" + 
					"           and a02.a0201b = '"+b0111+"'" +  qxwhere+
					"        union all" + 
					"        select qpid fxyp00," + 
					"               '' a0000," + 
					"               '' a0101," + 
					"               gwname a0215a," + 
					"               '' a0165," + 
					"               '"+b0111+"' a0201b," + 
					"               '"+b0111+"' b0111," + 
					"               '' b0131," + 
					"               '' a0225,gwzf a0201e,'' a0288,'' tp0105,'' a0192a" + 
					"          from BGWQP" + 
					"         where b01id =" + 
					"               (select b01id from b01 where b0111 = '"+b0111+"') " 
							+ ") t  " +
					"  order by nvl((select sortid" + 
					"                from gwpxall b" + 
					"               where b0111 = '"+b0111+"'" + 
					"                 and t.fxyp00 = b.id)," + 
					"              99999)";
			this.pageQuery(sql, "SQL", start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("save")
	@NoRequiredValidate
	public int save()throws RadowException{
		try {
			String a0000 = this.getPageElement("a0000").getValue();
			//去向的a0200
			String a0200y = this.getPageElement("a0200").getValue();
			//这是现状的a0200 新增岗位的时候管理 fxyp表
			String a0200 = this.getPageElement("fxyp00").getValue();
			String b0111 = this.getPageElement("mntp_b01").getValue();
			String famx00 = this.getPageElement("famx00").getValue();
			String b0131 = "";
			String sql = "";
			String sjdw00=UUID.randomUUID().toString();
			
			if(b0111.startsWith("001.001.004")&&b0111.length()==19){
				b0131 = HBUtil.getValueFromTab("b0131", "b01", "b0111='"+b0111+"'");
			}
			
			if(!"".equals(b0131)) {

				@SuppressWarnings("unchecked")
				List<String> exists= HBUtil.getHBSession().createSQLQuery("select sjdw00 from HZ_MNTP_SJFA_org a where a.famx00='"+famx00+"' and b0111='"+b0111+"' and b0131='"+b0131+"'").list();
				if(exists.size()>0) {
					sjdw00 = exists.get(0);
					String sql1="update HZ_MNTP_SJFA_ORG set status='1' where famx00='"+famx00+"' and b0111='"+b0111+"' and b0131='"+b0131+"'";
					HBUtil.executeUpdate(sql1);
				}else {
					sql="insert into HZ_MNTP_SJFA_ORG (famx00,b0111,mntp05,b0131,sjdw00,status,famx01,orgsort) values ('"+famx00+"','"+b0111+"','2','"+b0131+"','"+sjdw00+"','1','2','9999999999999999999999999')";
					HBUtil.executeUpdate(sql);
				}
			}else {
				@SuppressWarnings("unchecked")
				List<String> exists= HBUtil.getHBSession().createSQLQuery("select sjdw00 from HZ_MNTP_SJFA_org a where a.famx00='"+famx00+"' and b0111='"+b0111+"' ").list();
				if(exists.size()>0) {
					sjdw00 = exists.get(0);
					String sql1="update HZ_MNTP_SJFA_ORG set status='1' where famx00='"+famx00+"' and b0111='"+b0111+"' ";
					HBUtil.executeUpdate(sql1);
				}else {
					if("001.001.003".equals(b0111.substring(0, 11))) {
						sql="insert into HZ_MNTP_SJFA_ORG (famx00,b0111,mntp05,sjdw00,status,famx01,orgsort) values ('"+famx00+"','"+b0111+"','4','"+sjdw00+"','1','2','9999999999999999999999999')";
						HBUtil.executeUpdate(sql);
					}else {
						sql="insert into HZ_MNTP_SJFA_ORG (famx00,b0111,mntp05,sjdw00,status,famx01,orgsort) values ('"+famx00+"','"+b0111+"','1','"+sjdw00+"','1','2','9999999999999999999999999')";
						HBUtil.executeUpdate(sql);
					}
				}
			}
			
			//是否已经有岗位 如果有，则直接新增人员
			String selectSql = FABDUtil.getXZXXSZSQL(b0111);
			String fxyp00 = HBUtil.getValueFromTab("fxyp00", "v_mntp_sj_gw_ry", "gwa0200 in ('"+a0200+"') and famx00='"+famx00+"'");
			if(StringUtils.isEmpty(fxyp00)){
				fxyp00 = UUID.randomUUID().toString();
				sql="insert into HZ_FXYP_SJFA(fxyp00,fxyp01,fxyp02,fxyp05,a0201e,status,b0111,sjdw00,famx00,a0200) "  
						+ " select '"+fxyp00+"' fxyp00,sortid fxyp01,a0215a fxyp02,'"+SysManagerUtils.getUserId()+"' fxyp05,"
						+ " a0201e,'1' status,'"+b0111+"' b0111,'"+sjdw00+"' sjdw00,'"+famx00+"' famx00,a0200"
						+ " from ("+selectSql+") x"
						+ " where a0200 in ('"+a0200+"')"
						+ " and not exists (select 1 from v_mntp_sj_gw_ry t where gwa0200 in ('"+a0200+"') and t.famx00='"+famx00+"'  and t.gwa0200=x.a0200)";
				HBUtil.executeUpdate(sql);
			}
			
			HBUtil.getHBSession().flush();
			
			this.getExecuteSG().addExecuteCode("realParent.doAddPerson.queryByNameAndIDS('"+a0000+"','"+fxyp00+"','"+a0200y+"');");
			this.setMainMessage("保存成功");
		} catch (Exception e) {
			this.setMainMessage("保存失败");
			e.printStackTrace();
		}
		//this.setNextEventName("allGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("realParent.infoSearch();parent.odin.ext.getCmp(subWinId).close();");
		return EventRtnType.NORMAL_SUCCESS;
	}


	

	/**
	 * 获取最大的排序号
	 * @return
	 */
	public String getMax_sort(String sjdw00){
		HBSession session = HBUtil.getHBSession();
		String sort = session.createSQLQuery("select nvl(max(fxyp01),0)+1 from hz_fxyp_SJFA where sjdw00='"+sjdw00+"'" ).uniqueResult().toString();
		return sort;
	}
}
