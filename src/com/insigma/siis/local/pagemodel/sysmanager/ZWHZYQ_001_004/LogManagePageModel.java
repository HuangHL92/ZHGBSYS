package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Query;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class LogManagePageModel extends PageModel {
	
	/**
	 * ϵͳ������Ϣ
	 */
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String mainid = "";
	public String users = "";
	public LogManagePageModel(){
		HBSession sess = HBUtil.getHBSession();
		String hql = "From SmtUser where status='1'";
		List list = sess.createQuery(hql).list();
		if(list.size()!=0){
			for(int i=0;i<list.size();i++){
				SmtUser su = (SmtUser)list.get(i);
				if(i==0){
					users+="['"+su.getId()+"','"+su.getLoginname()+"']";
				}else{
					users+=",['"+su.getId()+"','"+su.getLoginname()+"']";
				}
				
			}
		}
		
	}
	
	@PageEvent("optionGroup.onchange")
	public void showmessage(){
		this.setMainMessage("sdfasdfa");
	}
	/**
	 * ��־����Ϣ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		//List alist = new ArrayList();
		HBSession sess = HBUtil.getHBSession();
		//��ȡҳ���еĲ�ѯ����
		String loginname = this.getPageElement("searchUserNameBtn").getValue();	//�����û�
		String info = this.getPageElement("searchInfoBtn").getValue();			//��Ϣ��
		String user = this.getPageElement("searchObjectBtn").getValue();		//��������
		String type = this.getPageElement("searchTypeBtn").getValue();			//��������
		String starttime = this.getPageElement("start").getValue();				//��ʼʱ��
		String endtime = this.getPageElement("end").getValue();					//����ʱ��
		//����������װsql�ı���
		StringBuffer str = new StringBuffer();
		str.append("select logmain.system_log_id,"+
					"logmain.userlog,"+
					"logmain.system_operate_date,"+
					"logmain.eventtype,"+
					"logmain.eventobject,"+
					"logmain.objectid,"+
					"logmain.objectname,"+
					"logmain.operate_comments "+ 
					" from Log_Main logmain " +
					" where 1=1 ");
		
		//ϵͳ����Ա
		String userid = SysManagerUtils.getUserId();
		if("297e9b3367154ab2016716125d430231".equals(userid)){
			str.append(" AND logmain.userlog not in ('297e9b3367154ab2016716125d430231',"
					+ "'297e9b3367154ab2016716a77abe0ca2',"
					+ "'402882846f6a49c9016f6a7d6e7b000a',"
					+ "'40288103556cc97701556d629135000f')");
			str.append(" and 1=2 ");
		}
		//��ȫ����Ա
		if("297e9b3367154ab2016716a77abe0ca2".equals(userid)){
			//str.append(" AND logmain.userlog in ('297e9b3367154ab2016716125d430231')");//ֻ��ϵͳ����Ա
			
			str.append(" AND logmain.userlog not in ('297e9b3367154ab2016716125d430231',"
					+ "'297e9b3367154ab2016716a77abe0ca2',"
					//+ "'402882846f6a49c9016f6a7d6e7b000a',"
					+ "'40288103556cc97701556d629135000f')");
		}
		
		//��ȫ���Ա
		if("402882846f6a49c9016f6a7d6e7b000a".equals(userid)){
			str.append(" AND logmain.userlog in ('297e9b3367154ab2016716125d430231','297e9b3367154ab2016716a77abe0ca2')");
		}
		//����ҳ���еĲ�����װsql
		if(!loginname.equals("")){
			//�������û���תΪ�û�ID��ƴ����sql
			//String sql = "select userid from smt_user where loginname = '"+loginname+"' ";
			//String loginID = sess.createSQLQuery(sql).uniqueResult().toString();
			String loginID = loginname;
			if("U001".equals(loginID)){
				loginID = "ϵͳ����Ա";
			}
			str.append(" AND logmain.userlog = '"+loginID+"'");
		}
		
		//�ж��û��Ƿ�Ϊsystem�������򲻿ɲ鿴system��־
		UserVO userF = PrivilegeManager.getInstance().getCueLoginUser();
		if(!userF.getId().equals("40288103556cc97701556d629135000f")){
			str.append(" AND logmain.USERLOG != '40288103556cc97701556d629135000f'");
		}
		
		
		if(!info.equals("")){
			str.append(" AND logmain.eventobject like '%"+info+"%'");
		}
		if(!user.equals("")){
			str.append(" AND logmain.objectname like '%"+user+"%'");
		}
		if(!type.equals("")){
			str.append(" AND logmain.eventtype like '%"+type+"%'");
		}
		//STR_TO_DATE('2016-10-21','%Y-%m-%d')
		//DATE_ADD(STR_TO_DATE('2016-10-21','%Y-%m-%d'), INTERVAL 1 DAY)
		if(!starttime.equals("")){
			CommonQueryBS.systemOut(starttime);
			if(DBUtil.getDBType()==DBType.ORACLE)
				str.append(" AND logmain.system_operate_date >= to_date('"+starttime+"','YYYY-MM-DD')");
			else
				str.append(" AND logmain.system_operate_date >= STR_TO_DATE('"+starttime+"','%Y-%m-%d')");
		}
		if(!endtime.equals("")){
			CommonQueryBS.systemOut(endtime);
			if(DBUtil.getDBType()==DBType.ORACLE)
				str.append(" AND logmain.system_operate_date < (to_date('"+endtime+"','YYYY-MM-DD')+1)");
			else
				str.append(" AND logmain.system_operate_date < DATE_ADD(STR_TO_DATE('"+endtime+"','%Y-%m-%d'), INTERVAL 1 DAY)");
		}
		//��������
		str.append(" order by logmain.system_operate_date desc");
		String sql = str.toString();
		//System.out.println(sql);
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * ��־��ϸ��Ϣ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("logGrid.dogridquery")
	public int doLogQuery(int start,int limit) throws RadowException{
		String id = this.mainid;
		List alist = new ArrayList();
		String hql = "From LogDetail t where t.systemlogid='"+id+"' "
				+ " and dataname is not null "//���Ϊ�� �����ˣ���Ϣ��ȫ������ʾ����Ҫ�޸������ֵ�������Ϣ��ʹ��Ϣ����
				+ " and length(dataname)!=0 ";
		HBSession session = HBUtil.getHBSession();
		Query query = session.createQuery(hql);
		alist = query.list();
		if(alist == null || alist.isEmpty()){
			this.setSelfDefResData(this.getPageQueryData(new ArrayList(), start, limit));
			return EventRtnType.SPE_SUCCESS;
		}
		this.setSelfDefResData(this.getPageQueryData(alist, start, limit));
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * ˢ������־
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.rowclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException{  //�򿪴��ڵ�ʵ��
		//loadPage(n8,'/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.QueryPersonList')
		//String id= (String)this.getPageElement("memberGrid").getValue("id",this.getPageElement("memberGrid").getCueRowIndex()).toString();
		mainid = (String)this.getPageElement("memberGrid").getValueList().get(this.getPageElement("memberGrid").getCueRowIndex()).get("system_log_id");
		this.setNextEventName("logGrid.dogridquery");
		//this.openWindow("addwin","pages.publicServantManage.PersonAddTab");//�¼��������Ĵ򿪴����¼�
		//this.setRadow_parent_data(this.getPageElement("memberGrid").getValue("id",this.getPageElement("memberGrid").getCueRowIndex()).toString());
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@Override
	public int doInit() throws RadowException {
		
		//ҳ��������ʹ������� add by lizs
		List<CodeValue> list = HBUtil.getHBSession().createQuery("from CodeValue where codeType ='OPERATE_TYPE'").list();
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		for(int i=0;i<list.size();i++){
			map.put(list.get(i).getCodeName(), list.get(i).getCodeName());
		}
		Combo table = (Combo)this.getPageElement("searchTypeBtn");
		table.setValueListForSelect(map);
		
		//ҳ������Ϣ����������add by lizs
		List<CodeValue> list_Info = HBUtil.getHBSession().createQuery("from CodeValue where codeType ='TABLE_NAME'").list();
		LinkedHashMap<String,String> map_Info = new LinkedHashMap<String,String>();
		for(int i=0;i<list_Info.size();i++){
			map_Info.put(list_Info.get(i).getCodeName(), list_Info.get(i).getCodeName());
		}
		Combo table_Info = (Combo)this.getPageElement("searchInfoBtn");
		table_Info.setValueListForSelect(map_Info);
		
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ��ѯ��־
	 */
	@PageEvent("findLogBtn.onclick")
	public int findLogBtn() throws RadowException{ 
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	/**
	 * ������־����
	 * 
	 */
	@PageEvent("logImpBtn.onclick")
	public int logImpBtn() throws RadowException{ 
		this.openWindow("fileImpWin", "pages.sysmanager.ZWHZYQ_001_004.FileImpWindow");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	/**
	 * ������־Ǩ��
	 */
	@PageEvent("logMoveBtn.onclick")
	public int logMoveBtn() throws RadowException{ 
		this.openWindow("logMoveWin", "pages.sysmanager.ZWHZYQ_001_004.LogMoveWindow");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	/**
	 * ��־Ǩ��
	 */
	@PageEvent("logSaveBtn.onclick")
	public int logSaveBtn() throws RadowException{ 
		this.addNextEvent(NextEventValue.YES, "delTTab","yes");							/*confirm���ʹ���,���ȷ��ʱ�����¼�*/
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");							/*confirm���ʹ���,���ȡ��ʱ�����¼�*/
		this.setMessageType(EventMessageType.CONFIRM); 									/*��Ϣ������(confirm���ʹ���)*/
		this.setMainMessage("Ǩ�ƴ�������־�����ݱ��ͬʱ������Ǩ�������ϸ��־����ȷ��Ǩ����");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	/**
	 *�������ݿ�Ǩ������
	 * @param confirm confirm����ֵ
	 * @return
	 * @throws Exception 
	 */
	@PageEvent("delTTab")
	public int delTTB(String confirm) throws Exception{
		String sql="insert into Log_Main_B select * from Log_Main";
		String sql1="insert into Log_Detail_B select * from Log_Detail";
		String sql2="truncate table Log_Main";
		String sql3="truncate table Log_Detail";
		HBSession sess = HBUtil.getHBSession();
		Statement stat = null;
		Connection conn = null;
		conn = sess.connection();
		stat = conn.createStatement();
		stat.execute(sql);
		stat.execute(sql1);
		stat.execute(sql2);
		stat.execute(sql3);
		sess.flush();
		stat.close();
		conn.close();
		this.setNextEventName("memberGrid.dogridquery");
		this.setMainMessage("��־Ǩ�Ƴɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
