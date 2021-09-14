package com.insigma.siis.local.pagemodel.publicServantManage;

import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Supervision;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class JdInformationPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
				
		this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("grid1.dogridquery")
	public int dogridquery(int start,int limit) throws Exception{
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();				
		
		System.out.println("传入参数"+a0000);
		String sql = "select id,a0000,position,informationtype,matter,startdate,dealorg,result,influencetime,filenumber from supervision where a0000='"+a0000+"' order by startdate desc";
		
		this.pageQuery(sql, "SQL", start, limit);
        System.out.println("查询sql"+sql);
    	return EventRtnType.SPE_SUCCESS;
		
	}
	/**
	 * 新建考核
	 * 允许多选，校验选择单位个数，多个给予提醒，不能新建
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
	 * 修改
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
		String groupid="";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map=list.get(i);
			String checked=map.get("personcheck")+"";
			if("true".equals(checked)){
				num=num+1;
				groupid=(String)map.get("id");
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
		System.out.println("+++++++++++++="+groupid);
		this.getPageElement("id").setValue(groupid);
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();				
		this.getPageElement("a0000").setValue(a0000);
		//this.setRadow_parent_data(groupid);
		//this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin','pages.sysorg.org.CreateSysOrg','新建下级机构页面',800,500,'"+pardata+"','"+ctxPath+"');");
		//String ctxPath = request.getContextPath();
		//this.getExecuteSG().addExecuteCode("$h.openWin('Information','pages.publicServantManage.Information','监督信息',550,300,'"+groupid+"','"+ctxPath+"')");
		this.getExecuteSG().addExecuteCode("open()");		
		//$h.openPageModeWin('DeployClass','pages.xbrm.DeployClass','调配类别维护页面',720,320,{rb_id:rbid},g_contextpath);
		
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 新建考核
	 * 允许多选，校验选择单位个数，多个给予提醒，不能新建
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("insert")
	public int insert() throws RadowException, Exception {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();				
		this.getPageElement("a0000").setValue(a0000);
		this.getExecuteSG().addExecuteCode("openinsert()");	
		
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	/**
	 * 删除
	 * 允许多选，校验选择单位个数，多个给予提醒，不能新建
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("delete")
	public int delete() throws RadowException, Exception {
		Grid grid = (Grid)this.getPageElement("grid1");

		List<HashMap<String, Object>> list=grid.getValueList();
				
		int num=0;
		StringBuffer groupid = new StringBuffer();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map=list.get(i);
			String checked=map.get("personcheck")+"";
			if("true".equals(checked)){
				num=num+1;
				//groupid=(String)map.get("id");
				groupid.append("'").append(map.get("id") == null ? "" : map.get("id").toString()).append("',");// 被勾选的人员编号组装，用“，”分隔
			}
		}				
		if(num<1){
			this.setMainMessage("请选择一条纪录!!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("ids").setValue(groupid.toString().substring(0, groupid.length()-1));	
		this.getExecuteSG().addExecuteCode("Ext.Msg.confirm('系统提示','您确定要删除吗？'," 
				+ "function(id) { if('yes'==id){radow.doEvent('deletesure','');}else{return;}});");	
		//this.addNextEvent(NextEventValue.YES, "doDelete", groupid);
		//this.addNextEvent(NextEventValue.CANNEL, "cannelEvent");
		
		
		//this.setMessageType(EventMessageType.CONFIRM); // 消息框类型(confirm类型窗口)
		//this.setMainMessage("确定要删除吗？");
		//this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("deletesure")
	public int deleteconfirm() throws AppException, RadowException {
		String ids = this.getPageElement("ids").getValue();				
		HBUtil.executeUpdate("delete from supervision where id in (" +ids+ ")");
		//HBUtil.executeUpdate("delete from admonishing where id in (" + abroadids + ")");
		//HBUtil.executeUpdate("update  admonishing set deleteflag = '1'  where id in (" + abroadids + ")");
		//HBUtil.executeUpdate("update  Cy_Punish set deleteflag = '1'  where oid in (" + abroadids + ")");
		
		this.setMainMessage("删除成功！");
		this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 删除操作
	 * @param id
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 *//*
	@PageEvent("doDelete")
	@com.insigma.odin.framework.radow.annotation.Transaction
	public int doDeleteUser(String groupid) throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		String sql = "delete from supervision where id = '"+groupid+"'";
		HBUtil.executeUpdate(sql);
		this.setMainMessage("删除成功！！！");
		return EventRtnType.NORMAL_SUCCESS;
	}*/
}
