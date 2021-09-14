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
	 * 系统区域信息
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
	 * 日志主信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		//List alist = new ArrayList();
		HBSession sess = HBUtil.getHBSession();
		//获取页面中的查询条件
		String loginname = this.getPageElement("searchUserNameBtn").getValue();	//操作用户
		String info = this.getPageElement("searchInfoBtn").getValue();			//信息集
		String user = this.getPageElement("searchObjectBtn").getValue();		//操作对象
		String type = this.getPageElement("searchTypeBtn").getValue();			//操作类型
		String starttime = this.getPageElement("start").getValue();				//开始时间
		String endtime = this.getPageElement("end").getValue();					//结束时间
		//定义用来组装sql的变量
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
		
		//系统管理员
		String userid = SysManagerUtils.getUserId();
		if("297e9b3367154ab2016716125d430231".equals(userid)){
			str.append(" AND logmain.userlog not in ('297e9b3367154ab2016716125d430231',"
					+ "'297e9b3367154ab2016716a77abe0ca2',"
					+ "'402882846f6a49c9016f6a7d6e7b000a',"
					+ "'40288103556cc97701556d629135000f')");
			str.append(" and 1=2 ");
		}
		//安全管理员
		if("297e9b3367154ab2016716a77abe0ca2".equals(userid)){
			//str.append(" AND logmain.userlog in ('297e9b3367154ab2016716125d430231')");//只看系统管理员
			
			str.append(" AND logmain.userlog not in ('297e9b3367154ab2016716125d430231',"
					+ "'297e9b3367154ab2016716a77abe0ca2',"
					//+ "'402882846f6a49c9016f6a7d6e7b000a',"
					+ "'40288103556cc97701556d629135000f')");
		}
		
		//安全审计员
		if("402882846f6a49c9016f6a7d6e7b000a".equals(userid)){
			str.append(" AND logmain.userlog in ('297e9b3367154ab2016716125d430231','297e9b3367154ab2016716a77abe0ca2')");
		}
		//根据页面中的参数组装sql
		if(!loginname.equals("")){
			//将操作用户名转为用户ID在拼接入sql
			//String sql = "select userid from smt_user where loginname = '"+loginname+"' ";
			//String loginID = sess.createSQLQuery(sql).uniqueResult().toString();
			String loginID = loginname;
			if("U001".equals(loginID)){
				loginID = "系统管理员";
			}
			str.append(" AND logmain.userlog = '"+loginID+"'");
		}
		
		//判断用户是否为system，不是则不可查看system日志
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
		//连接排序
		str.append(" order by logmain.system_operate_date desc");
		String sql = str.toString();
		//System.out.println(sql);
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 日志明细信息
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
				+ " and dataname is not null "//添加为空 ，过滤，信息不全，则不显示；需要修改数据字典配置信息，使信息完整
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
	 * 刷新子日志
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.rowclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException{  //打开窗口的实例
		//loadPage(n8,'/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.QueryPersonList')
		//String id= (String)this.getPageElement("memberGrid").getValue("id",this.getPageElement("memberGrid").getCueRowIndex()).toString();
		mainid = (String)this.getPageElement("memberGrid").getValueList().get(this.getPageElement("memberGrid").getCueRowIndex()).get("system_log_id");
		this.setNextEventName("logGrid.dogridquery");
		//this.openWindow("addwin","pages.publicServantManage.PersonAddTab");//事件处理完后的打开窗口事件
		//this.setRadow_parent_data(this.getPageElement("memberGrid").getValue("id",this.getPageElement("memberGrid").getCueRowIndex()).toString());
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@Override
	public int doInit() throws RadowException {
		
		//页面操作类型代码设置 add by lizs
		List<CodeValue> list = HBUtil.getHBSession().createQuery("from CodeValue where codeType ='OPERATE_TYPE'").list();
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		for(int i=0;i<list.size();i++){
			map.put(list.get(i).getCodeName(), list.get(i).getCodeName());
		}
		Combo table = (Combo)this.getPageElement("searchTypeBtn");
		table.setValueListForSelect(map);
		
		//页面中信息集代码设置add by lizs
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
	 * 查询日志
	 */
	@PageEvent("findLogBtn.onclick")
	public int findLogBtn() throws RadowException{ 
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	/**
	 * 用于日志载入
	 * 
	 */
	@PageEvent("logImpBtn.onclick")
	public int logImpBtn() throws RadowException{ 
		this.openWindow("fileImpWin", "pages.sysmanager.ZWHZYQ_001_004.FileImpWindow");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	/**
	 * 用于日志迁移
	 */
	@PageEvent("logMoveBtn.onclick")
	public int logMoveBtn() throws RadowException{ 
		this.openWindow("logMoveWin", "pages.sysmanager.ZWHZYQ_001_004.LogMoveWindow");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	/**
	 * 日志迁移
	 */
	@PageEvent("logSaveBtn.onclick")
	public int logSaveBtn() throws RadowException{ 
		this.addNextEvent(NextEventValue.YES, "delTTab","yes");							/*confirm类型窗口,点击确定时触发事件*/
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");							/*confirm类型窗口,点击取消时触发事件*/
		this.setMessageType(EventMessageType.CONFIRM); 									/*消息框类型(confirm类型窗口)*/
		this.setMainMessage("迁移大任务日志到备份表的同时，将会迁移相关明细日志，您确定迁移吗？");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	/**
	 *操作数据库迁移数据
	 * @param confirm confirm返回值
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
		this.setMainMessage("日志迁移成功。");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
