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
		
		System.out.println("�������"+a0000);
		String sql = "select id,a0000,position,informationtype,matter,startdate,dealorg,result,influencetime,filenumber from supervision where a0000='"+a0000+"' order by startdate desc";
		
		this.pageQuery(sql, "SQL", start, limit);
        System.out.println("��ѯsql"+sql);
    	return EventRtnType.SPE_SUCCESS;
		
	}
	/**
	 * �½�����
	 * �����ѡ��У��ѡ��λ����������������ѣ������½�
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
	 * �޸�
	 * �����ѡ��У��ѡ��λ����������������ѣ������½�
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
			this.setMainMessage("����ѡ��һ����¼!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(num<1){
			this.setMainMessage("��ѡ��һ����¼!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		System.out.println("+++++++++++++="+groupid);
		this.getPageElement("id").setValue(groupid);
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();				
		this.getPageElement("a0000").setValue(a0000);
		//this.setRadow_parent_data(groupid);
		//this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin','pages.sysorg.org.CreateSysOrg','�½��¼�����ҳ��',800,500,'"+pardata+"','"+ctxPath+"');");
		//String ctxPath = request.getContextPath();
		//this.getExecuteSG().addExecuteCode("$h.openWin('Information','pages.publicServantManage.Information','�ල��Ϣ',550,300,'"+groupid+"','"+ctxPath+"')");
		this.getExecuteSG().addExecuteCode("open()");		
		//$h.openPageModeWin('DeployClass','pages.xbrm.DeployClass','�������ά��ҳ��',720,320,{rb_id:rbid},g_contextpath);
		
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * �½�����
	 * �����ѡ��У��ѡ��λ����������������ѣ������½�
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
	 * ɾ��
	 * �����ѡ��У��ѡ��λ����������������ѣ������½�
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
				groupid.append("'").append(map.get("id") == null ? "" : map.get("id").toString()).append("',");// ����ѡ����Ա�����װ���á������ָ�
			}
		}				
		if(num<1){
			this.setMainMessage("��ѡ��һ����¼!!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("ids").setValue(groupid.toString().substring(0, groupid.length()-1));	
		this.getExecuteSG().addExecuteCode("Ext.Msg.confirm('ϵͳ��ʾ','��ȷ��Ҫɾ����'," 
				+ "function(id) { if('yes'==id){radow.doEvent('deletesure','');}else{return;}});");	
		//this.addNextEvent(NextEventValue.YES, "doDelete", groupid);
		//this.addNextEvent(NextEventValue.CANNEL, "cannelEvent");
		
		
		//this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ������(confirm���ʹ���)
		//this.setMainMessage("ȷ��Ҫɾ����");
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
		
		this.setMainMessage("ɾ���ɹ���");
		this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ɾ������
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
		this.setMainMessage("ɾ���ɹ�������");
		return EventRtnType.NORMAL_SUCCESS;
	}*/
}
