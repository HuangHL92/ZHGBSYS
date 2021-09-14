package com.insigma.siis.local.pagemodel.DataAnalysis;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class YoungCadreDataPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("grid1.dogridquery")
	public int dogridquery(int start,int limit) throws Exception{
		String gwcode = this.getPageElement("gwcode").getValue();	
		String seriesName = this.getPageElement("seriesName").getValue();	
		String ereaid = this.getPageElement("ereaid").getValue();	
		String warndate = this.getPageElement("warndate").getValue();	
		String sql = "";
		String sql1 = "select *" + 
				"  from A01 a" + 
				"  left join (select *" + 
				"               from A02" + 
				"              where A0279 = '1'" + 
				"                and A0255 = '1') b" + 
				"    on a.a0000 = b.a0000" + 
				" where substr(a.a0163, 0, 1) = '1'" + 
				"   and a.status != '4'" + 
				"   and a.a0221 like '1A%'" + 
				"   and a.A0160 in ('1', '5', '6')" + 
				"   and substr(a0107, 3, 1) = 8 " + 
				"   and b.a0201b like '"+ereaid+"%' " + 
				"   and a.a0221 = '"+gwcode+"'";
		String sql2 = "select *" + 
				"  from A01 a" + 
				"  left join (select *" + 
				"               from A02" + 
				"              where A0279 = '1'" + 
				"                and A0255 = '1') b" + 
				"    on a.a0000 = b.a0000" + 
				" where substr(a.a0163, 0, 1) = '1'" + 
				"   and a.status != '4'" + 
				"   and a.a0221 like '1A%'" + 
				"   and a.A0160 in ('1', '5', '6')" + 
				"   and substr(a0107, 3, 1) = 9 " + 
				"   and b.a0201b like '"+ereaid+"%' " + 
				"   and a.a0221 = '"+gwcode+"'";
		String sql3 = "select *" + 
				"  from A01 a" + 
				"  left join (select *" + 
				"               from A02" + 
				"              where A0279 = '1'" + 
				"                and A0255 = '1') b" + 
				"    on a.a0000 = b.a0000" + 
				" where substr(a.a0163, 0, 1) = '1'" + 
				"   and a.status != '4'" + 
				"   and a.a0221 like '1A%'" + 
				"   and a.A0160 in ('1', '5', '6')" + 
				"   and substr(a0107, 3, 1) != 8 " + 
				"   and substr(a0107, 3, 1) != 9 " + 
				"   and b.a0201b like '"+ereaid+"%' " + 
				"   and a.a0221 = '"+gwcode+"'";
		String sql4 = "select*" + 
				"  from A01 a" + 
				"  left join (select *" + 
				"               from A02" + 
				"              where A0279 = '1'" + 
				"                and A0255 = '1') b" + 
				"    on a.a0000 = b.a0000" + 
				" where substr(a.a0163, 0, 1) = '1'" + 
				"   and a.status != '4'" + 
				"   and a.a0221 like '1A%'" + 
				"   and a.A0160 in ('1', '5', '6')" + 
				"   and TRUNC(months_between(to_date('"+warndate+"','yyyy-mm-dd'), to_date(substr(a.a0107,0,6)||'01' ,'yyyymmdd'))/12)<=35 " + 
				"   and b.a0201b like '"+ereaid+"%' " + 
				"   and a.a0221 = '"+gwcode+"'";
		String sql5 = "select*" + 
				"  from A01 a" + 
				"  left join (select *" + 
				"               from A02" + 
				"              where A0279 = '1'" + 
				"                and A0255 = '1') b" + 
				"    on a.a0000 = b.a0000" + 
				" where substr(a.a0163, 0, 1) = '1'" + 
				"   and a.status != '4'" + 
				"   and a.a0221 like '1A%'" + 
				"   and a.A0160 in ('1', '5', '6')" + 
				"   and TRUNC(months_between(to_date('"+warndate+"','yyyy-mm-dd'), to_date(substr(a.a0107,0,6)||'01' ,'yyyymmdd'))/12)<=40 " + 
				"   and TRUNC(months_between(to_date('"+warndate+"','yyyy-mm-dd'), to_date(substr(a.a0107,0,6)||'01' ,'yyyymmdd'))/12)>=36 " + 
				"   and b.a0201b like '"+ereaid+"%' " + 
				"   and a.a0221 = '"+gwcode+"'";
		String sql6 = "select*" + 
				"  from A01 a" + 
				"  left join (select *" + 
				"               from A02" + 
				"              where A0279 = '1'" + 
				"                and A0255 = '1') b" + 
				"    on a.a0000 = b.a0000" + 
				" where substr(a.a0163, 0, 1) = '1'" + 
				"   and a.status != '4'" + 
				"   and a.a0221 like '1A%'" + 
				"   and a.A0160 in ('1', '5', '6')" + 
				"   and TRUNC(months_between(to_date('"+warndate+"','yyyy-mm-dd'), to_date(substr(a.a0107,0,6)||'01' ,'yyyymmdd'))/12)<=45 " + 
				"   and TRUNC(months_between(to_date('"+warndate+"','yyyy-mm-dd'), to_date(substr(a.a0107,0,6)||'01' ,'yyyymmdd'))/12)>=41 " + 
				"   and b.a0201b like '"+ereaid+"%' " + 
				"   and a.a0221 = '"+gwcode+"'";
		String sql7 = "select*" + 
				"  from A01 a" + 
				"  left join (select *" + 
				"               from A02" + 
				"              where A0279 = '1'" + 
				"                and A0255 = '1') b" + 
				"    on a.a0000 = b.a0000" + 
				" where substr(a.a0163, 0, 1) = '1'" + 
				"   and a.status != '4'" + 
				"   and a.a0221 like '1A%'" + 
				"   and a.A0160 in ('1', '5', '6')" + 
				"   and TRUNC(months_between(to_date('"+warndate+"','yyyy-mm-dd'), to_date(substr(a.a0107,0,6)||'01' ,'yyyymmdd'))/12)>=46 " + 
				"   and b.a0201b like '"+ereaid+"%' " + 
				"   and a.a0221 = '"+gwcode+"'";
		
		if("80后".equals(seriesName)) {
        	sql = sql1;
        }else if("90后".equals(seriesName)){
        	sql = sql2;
        }else if("其他1".equals(seriesName)){
        	sql = sql3;
        }else if("35岁以下".equals(seriesName)){
        	sql = sql4;
        }else if("35岁-40岁".equals(seriesName)){
        	sql = sql5;
        }else if("40岁-45岁".equals(seriesName)){
        	sql = sql6;
        }else if("其他2".equals(seriesName)){
        	sql = sql7;
        }
		this.pageQuery(sql, "SQL", start, limit);
        System.out.println("查询sql"+sql);
    	return EventRtnType.SPE_SUCCESS;
			
	}
	/**
	 * 刷新
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("refresh")
	public int refresh() throws RadowException, Exception {
		this.setNextEventName("grid1.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	/**
	 *修改考核
	 * 允许多选，校验选择单位个数，多个给予提醒，不能新建
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("insert")
	public int insert() throws RadowException, Exception {
		String orgId = this.getPageElement("subWinIdBussessId").getValue();									
		String ctxPath = request.getContextPath();
		
		this.getExecuteSG().addExecuteCode("$h.openWin('gwcfginfo','pages.sysorg.org.GwCfgInfo','新增页面',550,250,'"+orgId+"','"+ctxPath+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("grid1.rowdbclick")
	@GridDataRange(GridData.allrow)
	@AutoNoMask
	public int rowdbclick() throws RadowException, AppException{
		/*Grid grid = (Grid)this.getPageElement("grid1");
		int cueRowIndex = grid.getCueRowIndex();
		List<HashMap<String,Object>> gridList = grid.getValueList();
		HashMap<String,Object> map = gridList.get(cueRowIndex);
		String id=map.get("jggwconfid").toString();
		String orgId = this.getPageElement("subWinIdBussessId").getValue();	
		String groupid = orgId + "," +id;
		System.out.println("+++++++++++++="+groupid);
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('gwcfginfo','pages.sysorg.org.GwCfgInfo','编辑页面',550,250,'"+groupid+"','"+ctxPath+"');");*/
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 *修改考核
	 * 允许多选，校验选择单位个数，多个给予提醒，不能新建
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("update")
	public int update() throws RadowException, Exception {
		Grid grid = (Grid)this.getPageElement("grid1");

		List<HashMap<String, Object>> list=grid.getValueList();
				
		int num=0;
		String id="";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map=list.get(i);
			String checked=map.get("colcheck")+"";
			if("true".equals(checked)){
				num=num+1;
				id=(String)map.get("jggwconfid");
			}
		}				
		if(num>1){
			this.setMainMessage("仅能选择一条纪录!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(num<1){
			this.setMainMessage("请选择一条纪录!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String orgId = this.getPageElement("subWinIdBussessId").getValue();	
		String groupid = orgId + "," +id;
		System.out.println("+++++++++++++="+groupid);
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('gwcfginfo','pages.sysorg.org.GwCfgInfo','编辑页面',550,250,'"+groupid+"','"+ctxPath+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 删除
	 * 允许多选，校验选择单位个数，多个给予提醒，不能新建
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("deleteBtn")
	public int delete() throws RadowException, Exception {
		HBSession sess = HBUtil.getHBSession();
		String orgId = this.getPageElement("subWinIdBussessId").getValue();
		Grid grid = (Grid)this.getPageElement("grid1");
		List<HashMap<String, Object>> list=grid.getValueList();
		int num=0;
		int num2=0;
		//String groupid="";
		StringBuffer groupid = new StringBuffer();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map=list.get(i);
			String checked=map.get("colcheck")+"";
			if("true".equals(checked)){
				String jggwconfid = map.get("jggwconfid") == null ? "" : map.get("jggwconfid").toString();
				String gwcode = map.get("gwcode") == null ? "" : map.get("gwcode").toString();
				
				BigDecimal o1 = (BigDecimal) sess.createSQLQuery("select count(1) from a02 where a0215a_c='"+gwcode+"' and get_B0111NS(a0201b)='"+orgId+"'").uniqueResult();
				BigDecimal o2 = (BigDecimal) sess.createSQLQuery("select count(1) from js22 where js2204='"+gwcode+"' and get_B0111NS(js2202)='"+orgId+"'").uniqueResult();
				if(o1.intValue()>0 || o2.intValue()>0) {
					num2 ++ ;
				} else {
					num=num+1;
					groupid.append("'").append(map.get("jggwconfid") == null ? "" : map.get("jggwconfid").toString()).append("',");// 被勾选的人员编号组装，用“，”分隔
				}
			}
		}				
		if(num<1){
			if(num2 > 0) {
				this.setMainMessage("选择岗位在系统中正在被使用，不能删除！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.setMainMessage("请选择一条纪录!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("ids").setValue(groupid.toString().substring(0, groupid.length()-1));	
		this.getExecuteSG().addExecuteCode("Ext.Msg.confirm('系统提示','您确定要删除吗？'," 
				+ "function(id) { if('yes'==id){radow.doEvent('deletesure','');}else{return;}});");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deletesure")
	public int deleteconfirm() throws AppException, RadowException {
		String ids = this.getPageElement("ids").getValue();	
		System.out.println("delete from Jggwconf where jggwconfid in (" +ids+ ")");
		HBUtil.executeUpdate("delete from Jggwconf where jggwconfid in (" +ids+ ")");
		this.setMainMessage("删除成功！");
		this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

}
