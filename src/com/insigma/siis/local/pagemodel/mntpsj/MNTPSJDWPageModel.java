package com.insigma.siis.local.pagemodel.mntpsj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class MNTPSJDWPageModel extends PageModel {
	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		String fabd00 = this.getPageElement("fabd00").getValue();
		String userid = this.getCurUserid();
		try{
			if(StringUtils.isEmpty(fabd00)){
				this.setMainMessage("请选择方案！");
			}else{
				String fabd02 = HBUtil.getValueFromTab("fabd02", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
				String fabd06 = HBUtil.getValueFromTab("fabd06", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
				String mntp05 = HBUtil.getValueFromTab("mntp05", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
				this.getPageElement("fabd02").setValue(fabd02);
				this.getPageElement("mntp05").setValue(mntp05);
				this.getExecuteSG().addExecuteCode("$('#mntp05').val('"+mntp05+"');changeBTN();");
				if("1".equals(fabd06)){
					//乡镇
					this.getExecuteSG().addExecuteCode("Ext.getCmp('mntp05_combo').disable()");
				}
				
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String fabd00 = this.getPageElement("fabd00").getValue();
		String sql="select t.famx00,t.fabd00,t.famx01,t.famx02,t.famx03,f.fabd06 "
				+ " from HZ_MNTP_SJFA_famx t,HZ_MNTP_SJFA f where t.fabd00=f.fabd00 and t.fabd00='"+fabd00+"' order by t.famx02";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/*@PageEvent("memberGrid.rowclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws Exception{  //打开窗口的实例
		String famx00 = (String)this.getPageElement("memberGrid").getValueList().get(this.getPageElement("memberGrid").getCueRowIndex()).get("famx00");
		this.getPageElement("famx00").setValue(famx00);
//		ReturnFA("fa4ce239-cab9-43af-9c7c-3d90904cf722");
		return EventRtnType.NORMAL_SUCCESS;		
	}*/
	
	
	@PageEvent("infoDelete")
	@Transaction
	public int infoDelete() throws RadowException, AppException{
		String famx00 = this.getPageElement("famx00").getValue();
		
		
		HBSession sess = HBUtil.getHBSession();
		try {
			String fabd02 = this.getPageElement("fabd02").getValue();
			String famx03 = HBUtil.getValueFromTab("famx03", "HZ_MNTP_SJFA_famx", "famx00='"+famx00+"'");
			
			HBUtil.executeUpdate("delete from  HZ_MNTP_SJFA_ORG where famx00='"+famx00+"'");
			HBUtil.executeUpdate("delete from  HZ_MNTP_SJFA_famx where famx00='"+famx00+"'");
			HBUtil.executeUpdate("delete from  HZ_RXFXYP_SJFA where fxyp00 in (select fxyp00 from HZ_FXYP_SJFA where famx00='"+famx00+"')");
			HBUtil.executeUpdate("delete from  HZ_FXYP_SJFA where famx00='"+famx00+"'");
			HBUtil.executeUpdate("delete from  HZ_MNTP_SJFA_INFO where famx00f='"+famx00+"'");
			
			//删除调配数据
			
			sess.flush();
			this.getPageElement("famx00").setValue("");
			
			
			new LogUtil().createLogNew("方案明细删除","单位调配方案明细删除","模拟调配方案明细表",famx00,fabd02+"("+famx03+")", new ArrayList());
			
		}catch (Exception e) {
			this.setMainMessage("删除失败！"+e.getMessage());
			e.printStackTrace();
		}
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("copyFAMX")
	@Transaction
	public int copyFAMX(String addfamx01) throws RadowException, AppException{
		Map<String, String> returnMAP = new HashMap<String, String>();
		//区县市  班子
		String json = "{\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.001.001@1001\":\"001.001.004.001.001@1001\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.001.002@1003\":\"001.001.004.001.002@1003\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.001.003@1004\":\"001.001.004.001.003@1004\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.001.004@1005\":\"001.001.004.001.004@1005\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.004.001@1001\":\"001.001.004.004.001@1001\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.004.002@1003\":\"001.001.004.004.002@1003\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.004.003@1004\":\"001.001.004.004.003@1004\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.004.004@1005\":\"001.001.004.004.004@1005\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.005.001@1001\":\"001.001.004.005.001@1001\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.005.002@1003\":\"001.001.004.005.002@1003\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.005.003@1004\":\"001.001.004.005.003@1004\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.005.004@1005\":\"001.001.004.005.004@1005\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.006.001@1001\":\"001.001.004.006.001@1001\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.006.002@1003\":\"001.001.004.006.002@1003\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.006.003@1004\":\"001.001.004.006.003@1004\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.006.004@1005\":\"001.001.004.006.004@1005\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.007.001@1001\":\"001.001.004.007.001@1001\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.007.002@1003\":\"001.001.004.007.002@1003\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.007.004@1004\":\"001.001.004.007.004@1004\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.007.003@1005\":\"001.001.004.007.003@1005\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.008.001@1001\":\"001.001.004.008.001@1001\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.008.002@1003\":\"001.001.004.008.002@1003\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.008.003@1004\":\"001.001.004.008.003@1004\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.008.004@1005\":\"001.001.004.008.004@1005\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00E.001@1001\":\"001.001.004.00E.001@1001\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00E.002@1003\":\"001.001.004.00E.002@1003\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00E.003@1004\":\"001.001.004.00E.003@1004\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00E.004@1005\":\"001.001.004.00E.004@1005\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00F.001@1001\":\"001.001.004.00F.001@1001\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00F.002@1003\":\"001.001.004.00F.002@1003\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00F.003@1004\":\"001.001.004.00F.003@1004\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00F.004@1005\":\"001.001.004.00F.004@1005\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.009.001@1001\":\"001.001.004.009.001@1001\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.009.002@1003\":\"001.001.004.009.002@1003\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.009.003@1004\":\"001.001.004.009.003@1004\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.009.004@1005\":\"001.001.004.009.004@1005\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00A.001@1001\":\"001.001.004.00A.001@1001\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00A.002@1003\":\"001.001.004.00A.002@1003\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00A.003@1004\":\"001.001.004.00A.003@1004\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00A.004@1005\":\"001.001.004.00A.004@1005\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00B.001@1001\":\"001.001.004.00B.001@1001\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00B.002@1003\":\"001.001.004.00B.002@1003\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00B.003@1004\":\"001.001.004.00B.003@1004\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00B.004@1005\":\"001.001.004.00B.004@1005\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00C.001@1001\":\"001.001.004.00C.001@1001\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00C.002@1003\":\"001.001.004.00C.002@1003\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00C.003@1004\":\"001.001.004.00C.003@1004\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00C.004@1005\":\"001.001.004.00C.004@1005\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00D.007@1001\":\"001.001.004.00D.007@1001\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00D.001@1003\":\"001.001.004.00D.001@1003\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00D.002@1004\":\"001.001.004.00D.002@1004\",\"7961321DB1D84E80BBAD5CC723B75A4D001.001.004.00D.003@1005\":\"001.001.004.00D.003@1005\"}";
		
		String famx00 = this.getPageElement("famx00").getValue();
		String fabd00 = this.getPageElement("fabd00").getValue();
		String famx01 = "2";
		if(!StringUtils.isEmpty(addfamx01)){
			famx01 = addfamx01;
		}
		HBSession sess = HBUtil.getHBSession();
		@SuppressWarnings("unchecked")
		List<String> famx02s = HBUtil.getHBSession().createSQLQuery("select famx02 from HZ_MNTP_SJFA_famx " + 
				"  where fabd00='"+fabd00+"'  order by famx02 desc").list();
		@SuppressWarnings("unchecked")
		List<String> famx02rks = HBUtil.getHBSession().createSQLQuery("select rank() over(partition by famx01 order by famx02)  rk from HZ_MNTP_SJFA_famx " + 
				"  where fabd00='"+fabd00+"' and famx01='"+famx01+"' order by famx02 desc").list();
		
		int famx02=1;
		int famx02rk=1;
		if(famx02s.size()>0) {
			if(famx02s.get(0)!=null && !"".equals(famx02s.get(0))) {
				famx02=Integer.valueOf(String.valueOf(famx02s.get(0)))+1;
			}
		}
		if(famx02rks.size()>0) {
			if(famx02rks.get(0)!=null && !"".equals(famx02rks.get(0))) {
				famx02rk=Integer.valueOf(String.valueOf(famx02rks.get(0)))+1;
			}
		}
		//区县市 1 为新增空白方案 2为新增方案，模拟情况的人默认为现状  3为复制
		String addType = this.getPageElement("addType").getValue();
		if("1".equals(addType)||"2".equals(addType)){
			if(StringUtils.isEmpty(fabd00)){
				//returnMAP.put("result", "0");
				//returnMAP.put("msg", "参数famx00类型为空！");
				this.setMainMessage("参数famx00类型为空！");
			}else{
				famx00 = UUID.randomUUID().toString();
				
				
				
				//新增方案明细
				try {
					String fabd02 = this.getPageElement("fabd02").getValue();
					new LogUtil().createLogNew("方案明细新增","单位调配方案明细新增(区县市)","模拟调配方案明细表",famx00,fabd02+"("+("1".equals(famx01)?("现班子情况"+famx02rk):("调配建议方案"+famx02rk)+":"+("1".equals(addType)?"空方案":"现状方案"))+")", new ArrayList());

					
					sess.createSQLQuery("insert into HZ_MNTP_SJFA_famx(famx00,fabd00,famx01,famx02,famx03) values(?,?,?,?,?)")
					.setString(0, famx00).setString(1, fabd00).setString(2, famx01)
					.setInteger(3, famx02).setString(4, "1".equals(famx01)?("现班子情况"+famx02rk):("调配建议方案"+famx02rk)).executeUpdate();
					sess.flush();
					
				}catch (Exception e) {
					//returnMAP.put("result", "0");
					//returnMAP.put("msg", "添加失败！");
					this.setMainMessage("添加失败！");
					e.printStackTrace();
					return EventRtnType.NORMAL_SUCCESS;
				}
				new CHOOSEdwPageModel().saveFA(json, famx00, famx01, addType);
				/*returnMAP.put("result", "1");
				returnMAP.put("famx00", famx00);
				returnMAP.put("fabd00", fabd00);
				returnMAP.put("addType", addType);
				returnMAP.put("msg", "新增成功！");*/
				

				
				this.setNextEventName("memberGrid.dogridquery");
			}
		}else if("3".equals(addType)){
			if(StringUtils.isEmpty(famx00)){
				//returnMAP.put("result", "0");
				//returnMAP.put("msg", "参数famx00类型为空！");
				this.setMainMessage("参数famx00类型为空！");
			}else{
				String famx00_copy = UUID.randomUUID().toString();
				String sjdw00_copy_sub = UUID.randomUUID().toString().substring(0,18);
				String fxyp00_copy_sub = UUID.randomUUID().toString().substring(0,18);
				
				String famx03 = HBUtil.getValueFromTab("famx03", "HZ_MNTP_SJFA_famx", "famx00='"+famx00+"'");
				String fabd02 = this.getPageElement("fabd02").getValue();
				new LogUtil().createLogNew("方案明细复制","单位调配方案明细复制(区县市)","模拟调配方案明细表",famx00,fabd02+"("+famx03+"-->"+famx03+"-复制)", new ArrayList());

				
				//复制
				//HZ_MNTP_SJFA_famx famx00
				HBUtil.executeUpdate("insert into HZ_MNTP_SJFA_famx(famx00,fabd00,famx01,famx02,famx03) " + 
						"select '"+famx00_copy+"',fabd00,famx01,"+famx02+",famx03 || '-复制' from HZ_MNTP_SJFA_famx " + 
						"where famx00='"+famx00+"'");
				//HZ_MNTP_SJFA_ORG famx00 sjdw00
				HBUtil.executeUpdate("insert into HZ_MNTP_SJFA_ORG (famx00,b0111,mntp05,sjdw00,status,famx01,orgsort) " + 
						"select '"+famx00_copy+"',b0111,mntp05,'"+sjdw00_copy_sub+"' ||substr(sjdw00,18),status,famx01,orgsort from HZ_MNTP_SJFA_ORG " + 
						"where famx00 = '"+famx00+"'");
				//HZ_FXYP_SJFA fxyp00  famx00 sjdw00
				HBUtil.executeUpdate("insert into HZ_FXYP_SJFA(fxyp00,fxyp01,fxyp02,fxyp05,a0201e,status,b0111,sjdw00,famx00,a0200,zwqc00) " + 
						"select '"+fxyp00_copy_sub+"'||substr(fxyp00,18),fxyp01,fxyp02,fxyp05,a0201e,status,b0111,'"+sjdw00_copy_sub+"' ||substr(sjdw00,18),'"+famx00_copy+"',a0200,replace(zwqc00,famx00,'"+famx00_copy+"') from HZ_FXYP_SJFA " + 
						"where famx00 = '"+famx00+"'");
				//hz_rxfxyp_SJFA fxyp00 tp0100
				HBUtil.executeUpdate("insert into hz_rxfxyp_SJFA(tp0100,a0000,type,tp0101,tp0102,tp0103,tp0104,tp0105,tp0106,sortnum,fxyp00,a0200) " + 
						"select sys_guid(),a0000,type,tp0101,tp0102,tp0103,tp0104,tp0105,tp0106,sortnum,'"+fxyp00_copy_sub+"'||substr(fxyp00,18),a0200 from hz_rxfxyp_SJFA " + 
						"where fxyp00 in (select fxyp00 from HZ_FXYP_SJFA where famx00 = '"+famx00+"') ");
				//HZ_MNTP_SJFA_INFO fxyp00f infoid
				HBUtil.executeUpdate("insert into HZ_MNTP_SJFA_INFO(famx00f,fabd00,a0200,infoid,tpdesc,tpdesc2) " + 
						"select '"+famx00_copy+"',fabd00,'"+fxyp00_copy_sub+"'||substr(a0200,18),sys_guid(),tpdesc,tpdesc2 from HZ_MNTP_SJFA_INFO " + 
						"where famx00f = '"+famx00+"'");
				this.setNextEventName("memberGrid.dogridquery");
			}
		}else{
			//returnMAP.put("result", "0");
			//returnMAP.put("msg", "参数type类型不对！");
			this.setMainMessage("参数addtype类型为空！");
		}
		
		
		
		
		
		//this.setSelfDefResData(returnMAP);
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	public static List returnFA(String fabd00) throws Exception {
		return returnFA(fabd00, null,null);
	}
	
	public static List returnFA(String fabd00, String filter, String selfamx00) throws Exception {
		return returnFA(fabd00, filter, selfamx00, false);
	}
	
	public static List returnFA(String fabd00, String filter, String selfamx00,boolean isTP) throws Exception {
		
		if(StringUtils.isEmpty(filter)||"全部".equals(filter)){
			filter = "";
		}else{
			filter = " and b0111 like '"+filter+"%' ";
		}
		if(StringUtils.isEmpty(selfamx00)||"全部".equals(selfamx00)){
			selfamx00 = "";
		}else{
			selfamx00 = " and (famx00 = '"+selfamx00+"' or famx01='1') ";
		}
		
		String famx01 = "";
		if(isTP){
			famx01 = " and x.famx01='2' ";
		}
		
		
		ArrayList<List> fabdList= new ArrayList<List>();
		CommQuery cqbs=new CommQuery();
//		List<HashMap<String,String>> famxList;
		try {
			@SuppressWarnings("unchecked")
			List<String> famx00s= HBUtil.getHBSession().createSQLQuery(
					"select famx00 from HZ_MNTP_SJFA_famx where fabd00='"+fabd00+"' "+selfamx00+" order by  famx02").list();
			if(famx00s.size()>0) {
				for(int i=0;i<famx00s.size();i++) {
					String famx00=famx00s.get(i);
					String sql="select x.famx00,x.famx01,g.b0111,g.b0131,(select b0101 from b01 x where x.b0111=g.b0111) b0101 " + 
							" from HZ_MNTP_SJFA_famx x ,HZ_MNTP_SJFA_ORG g " + 
							" where g.famx00='"+famx00+"' and g.status='1' " + famx01 +
							" and x.famx00=g.famx00 "+filter+" order by  g.orgsort,(select b0269 from b01 where b01.b0111=g.b0111)";
					List<HashMap<String, Object>> famxList;
					famxList = cqbs.getListBySQL(sql);
					if(famxList.size()>0){
						fabdList.add(famxList);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return fabdList;
	}
	
	@PageEvent("memberGrid.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogrid6AfterEdit() throws Exception {
		//获取页面数据
		Grid grid6 = (Grid) this.getPageElement("memberGrid");
		String famx00 = grid6.getValue("famx00") +"";
		String famx01 = grid6.getValue("famx01") +"";
		String famx03 = grid6.getValue("famx03") +"";
		HBSession sess = HBUtil.getHBSession();
		
		String famx01_o = HBUtil.getValueFromTab("famx01", "HZ_MNTP_SJFA_famx", "famx00='"+famx00+"'");
		String famx03_o = HBUtil.getValueFromTab("famx03", "HZ_MNTP_SJFA_famx", "famx00='"+famx00+"'");
		
		sess.createSQLQuery("update HZ_MNTP_SJFA_famx set famx01=?,famx03=? where famx00=?")
		.setString(0, famx01).setString(1, famx03).setString(2, famx00).executeUpdate();
		sess.createSQLQuery("update HZ_MNTP_SJFA_ORG set famx01=? where famx00=?")
		.setString(0, famx01).setString(1, famx00).executeUpdate();
		//切换类型 清掉单位信息
		//HBUtil.executeUpdate("delete HZ_MNTP_SJFA_ORG where famx00=?",new Object[]{famx00});
		/*
		HBUtil.executeUpdate("update HZ_MNTP_FABD_famx set mntp00='' where famx00=?",new Object[]{famx00});*/
		
		
		
		List<String[]> list = new ArrayList<String[]>();
		String[] arr1 = {"famx01", famx01_o, famx01, "famx01"};
		String[] arr2 = {"famx03", famx03_o, famx03, "famx03"};
		list.add(arr1);
		list.add(arr2);
		String fabd02 = this.getPageElement("fabd02").getValue();
		
		new LogUtil().createLogNew("方案明细修改","单位调配方案明细修改","模拟调配方案明细表",famx00,fabd02+"("+famx03+")", list);

		
		sess.flush();
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveFABD")
	@Transaction
	public int saveFABD(String addfamx01) throws RadowException, AppException{
		//String userid = this.getCurUserid();
		//famx00 fabd00 famx01 mntp00
		String fabd00 = this.getPageElement("fabd00").getValue();
		String famx00 = UUID.randomUUID().toString();
		String famx01 = "2";
		if(!StringUtils.isEmpty(addfamx01)){
			famx01 = addfamx01;
		}
		try {
			HBSession sess = HBUtil.getHBSession();
			@SuppressWarnings("unchecked")
			List<String> famx02s = HBUtil.getHBSession().createSQLQuery("select famx02 from HZ_MNTP_SJFA_famx " + 
					"  where fabd00='"+fabd00+"'  order by famx02 desc").list();
			List<String> famx02rks = HBUtil.getHBSession().createSQLQuery("select rank() over(partition by famx01 order by famx02)  rk from HZ_MNTP_SJFA_famx " + 
					"  where fabd00='"+fabd00+"' and famx01='"+famx01+"' order by famx02 desc").list();
			
			int famx02=1;
			int famx02rk=1;
			if(famx02s.size()>0) {
				if(famx02s.get(0)!=null && !"".equals(famx02s.get(0))) {
					famx02=Integer.valueOf(String.valueOf(famx02s.get(0)))+1;
				}
			}
			if(famx02rks.size()>0) {
				if(famx02rks.get(0)!=null && !"".equals(famx02rks.get(0))) {
					famx02rk=Integer.valueOf(String.valueOf(famx02rks.get(0)))+1;
				}
			}
			
			String fabd02 = this.getPageElement("fabd02").getValue();
			new LogUtil().createLogNew("方案明细新增","单位调配方案明细新增","模拟调配方案明细表",famx00,fabd02+"("+("1".equals(famx01)?("现班子情况"+famx02rk):("调配建议方案"+famx02rk))+")", new ArrayList());

			
			
			sess.createSQLQuery("insert into HZ_MNTP_SJFA_famx(famx00,fabd00,famx01,famx02,famx03) values(?,?,?,?,?)")
			.setString(0, famx00).setString(1, fabd00).setString(2, famx01)
			.setInteger(3, famx02).setString(4, "1".equals(famx01)?("现班子情况"+famx02rk):("调配建议方案"+famx02rk)).executeUpdate();
			sess.flush();
			this.setNextEventName("rolesort");
			
		}catch (Exception e) {
			this.setMainMessage("保存失败！"+e.getMessage());
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveFabd02")
	@Transaction
	public int saveFabd02() throws Exception{
		String fabd00 = this.getPageElement("fabd00").getValue();
		String fabd02 = this.getPageElement("fabd02").getValue();
		String mntp05 = this.getPageElement("mntp05").getValue();
		HBUtil.executeUpdate("update HZ_MNTP_SJFA set fabd02=?,mntp05=?"
				+ " where fabd00=?",new Object[]{fabd02,mntp05,fabd00});
		
		new LogUtil().createLogNew("修改方案信息","单位调配修改方案信息","模拟调配方案表",fabd00,fabd02, new ArrayList());

		
		this.getExecuteSG().addExecuteCode("realParent.odin.setSelectValue('mntp05','"+mntp05+"');realParent.$('#fabd02').val('"+fabd02+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("rolesort")
	@Transaction
	public int publishsort() throws RadowException {
		
		String fabd00 = this.getPageElement("fabd00").getValue();
		String fabd02 = this.getPageElement("fabd02").getValue();
		new LogUtil().createLogNew("方案明细排序","单位调配方案明细排序","模拟调配方案明细表",fabd00,fabd02, new ArrayList());
		List<HashMap<String, String>> list = this.getPageElement("memberGrid").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		Connection con = null;
		try {
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "update HZ_MNTP_SJFA_famx set famx02 = ? where famx00=?";
			PreparedStatement ps = con.prepareStatement(sql);
			int i = 1;
			for (HashMap<String, String> m : list) {
				String zwzc00 = m.get("famx00");
				ps.setInt(1, i);
				ps.setString(2, zwzc00);
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
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

}
