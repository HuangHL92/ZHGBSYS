package com.insigma.siis.local.pagemodel.mntpsj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.mntpsj.base.FABDUtil;

public class MNTPSJXZGWPageModel  extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.getExecuteSG().addExecuteCode("init();");
		return 0;
	}
	
	@PageEvent("updateGrid")
	public int updateGrid() {
		try {
			CommQuery cqbs=new CommQuery();
			String fabd00=this.getPageElement("fabd00").getValue();
			
			String famx00 = this.getPageElement("famx00").getValue();
			if(StringUtils.isEmpty(famx00)){
				famx00 = HBUtil.getValueFromTab("famx00", "HZ_MNTP_SJFA_famx", "fabd00='"+fabd00+"'");
				this.getPageElement("famx00").setValue(famx00);
			}
					
			
			
			String sql="select * from HZ_MNTP_SJFA where fabd00='"+fabd00+"'";
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			this.getPageElement("mntpname").setValue(list.get(0).get("fabd02").toString());
			//String famx00=this.getPageElement("famx00").getValue();
			
			
			/*sql="select nvl(b0104,b0101) b0104,b0111,b01id,b0269 from b01 where substr(b0111,1,11)='001.001.004' and length(b0111)=19 and b0131 in('1001','1003','1004','1005','1006','1007') "
					+ " union all "
					+ " select nvl(b0104,b0101) b0104,b0111,b01id,b0269 from b01 where b0121 like '001.001.004.%' and length(b0111)=19 and b0131='3480' "
					+ " union all "
					+ " select nvl(b0104,b0101) b0104,b0111,b01id,b0269 from b01 where (b0121 = '001.001.002'"
					+"  and b01id not in ('6C759252379B4E01BFA614D2B06D31FA','B9E46D6110134E77B7273E01EACF21A3','6B48873119494B34A80F0E314549813D')  )"
					+"    or b01id in (  'F0E0EA716C0442328DE41B549BC73C9C','0B3931450F264D36895440B7EAB46B81','24A2EF2597174AE8BF1E65D7EE34DD24',"
					+"   '70BF51BDAD28458DA98B7092B049AECF','BC923D3D6F034B8584C4DDF60071C895','96D6674BC36C428B84166728D0A21455',"
					+"   '72C8D307601E4D15B4DED1B82B41E8DE','A6F4AE51884D4231B7AF3C8623A56884')  "
					+ "  union all  "
					+ " select nvl(b0104,b0101) b0104,b0111,b01id,b0269  from b01 where b0111 like '001.001.003.%'"
					 +"  and b01id not in ( '36E7A2F90629493AA4FCAB4345AF6F77','6C5E5C831D8443D48EBF42E89CDE7055','DBAD9B3F2C2E4BAE935FC8F4C245555D','3601E75C9C6F4EF4AD7274C9D6CA09B5','B9E46D6110134E77B7273E01EACF21A3','6C759252379B4E01BFA614D2B06D31FA','D563DF546904420B8D042038B927549A','7D174AA620804C608774111467F3F111')"
					 +"  and b0111 not like '001.001.002.02N%'  and b0114 not like '%X09'"
					 + " order by b0269 ";*/
			//List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
			/*HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(int i=0;i<listCode.size();i++){
				HashMap<String, Object> m = listCode.get(i);
				String b0104 = m.get("b0104")==null?"":m.get("b0104").toString();
				String b0111sel = m.get("b0111")==null?"":m.get("b0111").toString();
				mapCode.put(b0111sel, b0104);
			}
			((Combo)this.getPageElement("mntp_b01")).setValueListForSelect(mapCode);*/
			
			String b0111=this.getPageElement("b0111").getValue();
			
			if(!StringUtils.isEmpty(b0111)&&!StringUtils.isEmpty(famx00)){
				String b0101 = HBUtil.getValueFromTab("b0101", "b01", "b0111='"+b0111+"'");
				this.getPageElement("mntp_b01").setValue(b0111);
				this.getPageElement("mntp_b01_combotree").setValue(b0101);
				this.setNextEventName("allGrid.dogridquery");
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
					"  from (select a02.a0200," + 
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
					"        select qpid a0200," + 
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
					"                 and t.a0200 = b.id)," + 
					"              99999)";
			this.pageQuery(sql, "SQL", start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("save")
	public int save(String id)throws RadowException{
		try {
			String famx00 = this.getPageElement("famx00").getValue();
			String a0200 = this.getPageElement(id).getValue();
			String b0111 = this.getPageElement("mntp_b01").getValue();
			String b0131 = "";
			String sql = "";
			
			a0200 = a0200.replace(",", "','");
			String sjdw00=UUID.randomUUID().toString();
			
			
			String fabd00=this.getPageElement("fabd00").getValue();
			String mntpname = HBUtil.getValueFromTab("fabd02", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
			String b0101 = HBUtil.getValueFromTab("b0101", "b01", "b0111='"+b0111+"'");
			new LogUtil().createLogNew("选择现有调配岗位","选择现有调配岗位新增","模拟调配岗位表",sjdw00,mntpname+"("+b0101+")", new ArrayList());

			
			
			if(b0111.startsWith("001.001.004")&&b0111.length()==19){
				b0131 = HBUtil.getValueFromTab("b0131", "b01", "b0111='"+b0111+"'");
			}
			
			if(!"".equals(b0131)) {

				@SuppressWarnings("unchecked")
				List<String> exists= HBUtil.getHBSession().createSQLQuery("select sjdw00 from HZ_MNTP_SJFA_org a where a.famx00='"+famx00+"' and b0111='"+b0111+"' and b0131='"+b0131+"'").list();
				if(exists.size()>0) {
					sjdw00 = exists.get(0);
					//String sql1="update HZ_MNTP_SJFA_ORG set status='1' where famx00='"+famx00+"' and b0111='"+b0111+"' and b0131='"+b0131+"'";
					//HBUtil.executeUpdate(sql1);
				}else {
					sql="insert into HZ_MNTP_SJFA_ORG (famx00,b0111,mntp05,b0131,sjdw00,status,famx01,orgsort) values ('"+famx00+"','"+b0111+"','2','"+b0131+"','"+sjdw00+"','1','2',(select b0269 from b01 where b0111='"+b0111+"'))";
					HBUtil.executeUpdate(sql);
				}
			}else {
				@SuppressWarnings("unchecked")
				List<String> exists= HBUtil.getHBSession().createSQLQuery("select sjdw00 from HZ_MNTP_SJFA_org a where a.famx00='"+famx00+"' and b0111='"+b0111+"' ").list();
				if(exists.size()>0) {
					sjdw00 = exists.get(0);
					//String sql1="update HZ_MNTP_SJFA_ORG set status='1' where famx00='"+famx00+"' and b0111='"+b0111+"' ";
					//HBUtil.executeUpdate(sql1);
				}else {
					if("001.001.003".equals(b0111.substring(0, 11))) {
						sql="insert into HZ_MNTP_SJFA_ORG (famx00,b0111,mntp05,sjdw00,status,famx01,orgsort) values ('"+famx00+"','"+b0111+"','4','"+sjdw00+"','1','2',(select b0269 from b01 where b0111='"+b0111+"'))";
						HBUtil.executeUpdate(sql);
					}else {
						sql="insert into HZ_MNTP_SJFA_ORG (famx00,b0111,mntp05,sjdw00,status,famx01,orgsort) values ('"+famx00+"','"+b0111+"','1','"+sjdw00+"','1','2',(select b0269 from b01 where b0111='"+b0111+"'))";
						HBUtil.executeUpdate(sql);
					}
				}
			}
			
			String selectSql = FABDUtil.getXZXXSZSQL(b0111);
			sql="insert into HZ_FXYP_SJFA(fxyp00,fxyp01,fxyp02,fxyp05,a0201e,status,b0111,sjdw00,famx00,a0200,zwqc00,bzgw) "  
					+ " select sys_guid() fxyp00,sortid fxyp01,a0215a fxyp02,'"+SysManagerUtils.getUserId()+"' fxyp05,"
					+ " a0201e,'1' status,'"+b0111+"' b0111,'"+sjdw00+"' sjdw00,'"+famx00+"' famx00,a0200,"
					+ " (case when bzgw='102' or bzgw='201' then '"+(famx00+(b0111.substring(0, 15))+"102201")+"' "
					+ " when bzgw='115' or bzgw='209' then '"+(famx00+(b0111.substring(0, 15))+"115209")+"' "
					+ " when bzgw='108' or bzgw='202' then '"+(famx00+(b0111.substring(0, 15))+"108202")+"' "
					+ " else null end) zwqc00,bzgw "
					+ " from ("+selectSql+") x"
					+ " where a0200 in ('"+a0200+"')"
					+ " and not exists (select 1 from v_mntp_sj_gw_ry t where gwa0200 in ('"+a0200+"') and t.famx00='"+famx00+"'  and t.gwa0200=x.a0200)";
			HBUtil.executeUpdate(sql);
			HBUtil.getHBSession().flush();
			this.toastmessage("保存成功");
			//this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp(subWinId).close();realParent.infoSearch();");
		} catch (Exception e) {
			this.setMainMessage("保存失败");
			e.printStackTrace();
		}
		//this.setNextEventName("allGrid.dogridquery");
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
