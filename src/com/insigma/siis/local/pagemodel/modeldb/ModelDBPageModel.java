package com.insigma.siis.local.pagemodel.modeldb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.business.entity.Sublibrariesmodel;
import com.insigma.siis.local.business.modeldb.ModeldbBS;
import com.insigma.siis.local.business.modeldb.SynTask;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ModelDBPageModel extends PageModel {
	
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("MGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("query.onclick")
	@NoRequiredValidate           
	@OpLog
	public int query()throws RadowException, AppException {
		this.setNextEventName("MGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("MGrid.dogridquery")
	@NoRequiredValidate  
	public int dogridQuery(int start,int limit) throws RadowException{
		StringBuffer sql = new StringBuffer("select sub_libraries_model_id,sub_libraries_model_name,create_time,sub_libraries_model_type,self_create_mark,run_state,sub_libraries_model_key from SUB_LIBRARIES_MODEL where 1=1 ");
		String name = this.getPageElement("sublibrariesmodelname").getValue();
		String type = this.getPageElement("sublibrariesmodeltype").getValue();
		String timea = this.getPageElement("createtimesta").getValue();
		String timeb = this.getPageElement("createtimeend").getValue();
		String group = this.getPageElement("gsearch").getValue();
		String sqlResult = null;
		
		if(!"".equals(name)&&name!=null){
			sql.append(" and SUB_LIBRARIES_MODEL_NAME like '%" +name +"%'");
		}
		if(!"".equals(type)&&type!=null){
			sql.append(" and SUB_LIBRARIES_MODEL_TYPE like '%" +type+ "%'");
		}
		if(!"".equals(timea)&&timea!=null){
			sql.append(" and to_char(CREATE_TIME,'yyyymmdd') >='" +timea+ "'");
		}
		if(!"".equals(timeb)&&timeb!=null){
			sql.append(" and to_char(CREATE_TIME,'yyyymmdd') <='" +timeb+ "'");
		}
		if(!"".equals(group)&&group!=null){
			sql.append(" and CREATE_GROUPID ='" +group+ "'");
		}
		
		//处理MySQL
		if(DBUtil.getDBType() == DBType.MYSQL){
			sqlResult =sql.toString().replace("Nvl(", "ifnull(")
					.replace("to_char(CREATE_TIME,'yyyymmdd')", "DATE_FORMAT(CREATE_TIME,'%Y%m%d')");
		}else{
			sqlResult = sql.toString();
		}
		
		this.pageQuery(sqlResult,"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	public ModelDBPageModel() {
		try {
			HBSession sess = HBUtil.getHBSession();
			if("Smt_Group".equals(GlobalNames.sysConfig.get("GROUP"))){
				String areaname = (String) sess.createSQLQuery("SELECT a.NAME FROM SMT_GROUP a WHERE a.GROUPID='G001'").uniqueResult();
				areaInfo.put("areaname", areaname);
				areaInfo.put("areaid", "G001");
				///////////2017.05.09
				String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
				@SuppressWarnings("unchecked")
				List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
				if(groups.isEmpty() || PrivilegeManager.getInstance().getCueLoginUser().getLoginname().equals("kf00")){
					areaInfo.put("manager", "true");
				}else{
					areaInfo.put("manager", "false");
				}
			}
			//else{
				/**Object[] area = (Object[]) sess.createSQLQuery("SELECT b.AAA146,a.AAA005 FROM AA01 a,AA26 b WHERE a.AAA001='AREA_ID' and a.AAA005=b.AAB301 ").uniqueResult();
				if(area==null){
					String areaname = (String) sess.createSQLQuery("SELECT a.NAME FROM SMT_GROUP a WHERE a.GROUPID='G001'").uniqueResult();
					areaInfo.put("areaname", areaname);
					areaInfo.put("areaid", "G001");
				}else{
					areaInfo.put("areaname", area[0]);
					areaInfo.put("areaid", area[1]);
				}**/
			//}
			/**String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
			@SuppressWarnings("unchecked")
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			if(groups.isEmpty() || PrivilegeManager.getInstance().getCueLoginUser().getLoginname().equals("kf00")){
				areaInfo.put("manager", "true");
			}else{
				areaInfo.put("manager", "false");
			}**/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@PageEvent("create.onclick")
	@NoRequiredValidate
	public int openaddWin()throws RadowException{
		this.setRadow_parent_data("");
		this.openWindow("createWin", "pages.modeldb.ModelLoad");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("dogridgrant")
	@NoRequiredValidate
	public int dogridgrant(String p)throws RadowException{
		this.setRadow_parent_data(p);
		this.openWindow("createWin", "pages.modeldb.ModelLoad");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("dogriddelete")
	public int dogriddelete(String p) throws RadowException{
		this.addNextEvent(NextEventValue.YES, "doDelete",p);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型(confirm类型窗口)
		this.setMainMessage("确定要删除该主题吗？会连同主题定义以及主题库数据一起删除");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("doDelete")
	@Transaction
	public int del(String p) throws RadowException  {
		ModeldbBS.delSublibrariesmodel(p,0);
		this.setMainMessage("删除成功 ");
		this.getPageElement("MGrid").reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("clear.onclick")
	@NoRequiredValidate           
	public int resetonclick()throws RadowException, AppException {
		this.getPageElement("sublibrariesmodelname").setValue("");
		this.createPageElement("sublibrariesmodeltype",ElementType.SELECT,false).setValue("");
		this.getPageElement("createtimesta").setValue("");
		this.getPageElement("createtimeend").setValue("");
		this.getPageElement("gsearch").setValue("");
		this.getPageElement("comboxArea_gsearch").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("imp.onclick")
	@NoRequiredValidate
	public int imp()throws RadowException{
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("expJson.onclick")
	@NoRequiredValidate
	public int expJson()throws Exception{
		 exp(".json");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("expXml.onclick")
	@NoRequiredValidate
	public int expXml()throws Exception{
		 exp(".xml");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public void exp(String extname)throws Exception{
		List<HashMap<String, Object>>  gridlist = this.getPageElement("MGrid").getValueList();
		List<String> list = new ArrayList<String>();
		HBSession sess = HBUtil.getHBSession();
		Session sessReal = sess.getSession();
		Sublibrariesmodel modelNew = new Sublibrariesmodel();
		for(HashMap<String, Object> map : gridlist){
			if("true".equals(map.get("check").toString())){
				list.add(map.get("sub_libraries_model_key").toString()+"-"+map.get("sub_libraries_model_name").toString());
				Sublibrariesmodel model = (Sublibrariesmodel) sess.get(Sublibrariesmodel.class, (String)map.get("sub_libraries_model_id"));
				sessReal.evict(model);
				//记录日志:启动同步主题分库
				try {
					new LogUtil().createLog("659", "SUB_LIBRARIES_MODEL",
							(String)map.get("sub_libraries_model_id"),
							(String)map.get("sub_libraries_model_name"), "导出"+extname+"文件",Map2Temp.getLogInfo(model,modelNew)
							);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		if(list.size()==0){
			throw new RadowException("请勾选要到处定义的分库");
		}
		String filename = ModeldbBS.FileBuilder(list,extname);
		this.getExecuteSG().addExecuteCode("openExp('"+filename+"');");
		//ModeldbBS.FileDistroy(filename);
		
		
	}
	
	@PageEvent("run.onclick")
	@NoRequiredValidate
	public int run()throws RadowException, AppException{
		List<HashMap<String, Object>>  gridlist = this.getPageElement("MGrid").getValueList();
		List<String> list = new ArrayList<String>();
		for(HashMap<String, Object> map : gridlist){
			if("true".equals(map.get("check").toString())){
				list.add(map.get("sub_libraries_model_id").toString());
			}
		}
		if(list.size()==0){
			throw new RadowException("请勾选要执行的分库");
		}
		int res = ModeldbBS.runDB(list);
		this.setMainMessage(res==0?"启动成功":"启动失败");
		this.getPageElement("MGrid").reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("del.onclick")
	@NoRequiredValidate
	public int del() throws RadowException {
		List<HashMap<String, Object>>  gridlist = this.getPageElement("MGrid").getValueList();
		List<String> list = new ArrayList<String>();
		for(HashMap<String, Object> map : gridlist){
			if("true".equals(map.get("check").toString())){
				list.add(map.get("sub_libraries_model_id").toString());
			}
		}
		if(list.size()==0){
			throw new RadowException("请勾选要执行的分库");
		}
		
		this.addNextEvent(NextEventValue.YES, "doDelete",list.toString());
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型(confirm类型窗口)
		this.setMainMessage("确定要删除该主题吗？会连同主题定义以及主题库数据一起删除");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doDelete")
	@NoRequiredValidate
	@Transaction
	public int delete(String s) throws RadowException {
		//[402881ee543b9cab01543ba3b8a90002, 402881f25438ea02015438eb0aa60004]
		String[] a = s.substring(1, s.length()-1).split(","); 
		for(int i=0;i<a.length;i++){
			ModeldbBS. delSublibrariesmodel(a[i].trim(),0);
		}
		this.setMainMessage("删除完成");
		this.getPageElement("MGrid").reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		List<GroupVO> list = PrivilegeManager.getInstance().getIGroupControl()
				.findByParentId(node);
		// 只显示所在的组织及下级组织 不在组织中 则显示全部
		List<GroupVO> choose = new ArrayList<GroupVO>();
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser()
				.getId();
		List<GroupVO> groups = PrivilegeManager.getInstance()
				.getIGroupControl().findGroupByUserId(cueUserid);
		boolean topuser=false; 
		String areaid=areaInfo.get("areaid").toString();
		for (int i = 0; i < groups.size(); i++) {
			GroupVO vo=(GroupVO)groups.get(i);
			String groupid=vo.getId();
			if(groupid.equals(areaid)){
				topuser=true;
			}
			for (int j = 0; j < groups.size(); j++) {
				if (groups.get(j).getId().equals(groups.get(i).getParentid())) {
					groups.remove(i);
					i--;
				}
			}
		}
		boolean equel = false;
		
		
		if (!groups.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				
				for (int j = 0; j < groups.size(); j++) {
					if (groups.get(j).getId().equals(list.get(i).getId())) {
						choose.add(groups.get(j));
						equel = true;
					}
				}
			}
		}
		if (equel) {
			list = choose;
		}
		// 。
		if(topuser==false && node.equals(areaInfo.get("areaid"))){
			list=PrivilegeManager.getInstance()
			.getIGroupControl().findGroupByUserId(cueUserid);
		}
		
		
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (GroupVO group : list) {
				if (i == 0 && last == 1) {
					jsonStr.append("[{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}]");
				} else if (i == 0) {
					jsonStr.append("[{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}");
				} else if (i == (last - 1)) {
					jsonStr.append(",{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}]");
				} else {
					jsonStr.append(",{\"text\" :\"" + group.getName()
							+ "\" ,\"id\" :\"" + group.getId()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getId() + "')\"");
					jsonStr.append("}");
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
		
	}
	
	@PageEvent("grant.onclick")
	@NoRequiredValidate
	public int grant()throws RadowException{
		List<HashMap<String, Object>>  gridlist = this.getPageElement("MGrid").getValueList();
		List<String> list = new ArrayList<String>();
		for(HashMap<String, Object> map : gridlist){
			if("true".equals(map.get("check").toString())){
				list.add(map.get("sub_libraries_model_id").toString());
			}
		}
		if(list.size()==0){
			throw new RadowException("请勾选要授予的分库");
		}
		this.setRadow_parent_data(list.toString());
		this.openWindow("grantWin", "pages.modeldb.LoadGrant");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("start.onclick")
	@NoRequiredValidate
	public int start() throws RadowException{
		List<Sublibrariesmodel> list = ModeldbBS.getTaskList();
		TimerTask task = new SynTask(list);
        Timer timer = new Timer();  
        long delay = 1000; 
        long intevalPeriod = 1000*60;  
        // schedules the task to be run in an interval  
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);  
        
        for(Sublibrariesmodel model :list){
        	//记录日志:启动同步主题分库
    		try {
    			new LogUtil().createLog("657", "SUB_LIBRARIES_MODEL",
    					model.getSub_libraries_model_id(),
    					model.getSub_libraries_model_name(), null,null
    					);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        }
      
        
        
        this.setMainMessage("数据同步开启！");
        return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("stop.onclick")
	@NoRequiredValidate
	public int stop() throws RadowException{
		SynTask.running = false;
		this.setMainMessage("数据同步停止！");
		
		try {
			new LogUtil().createLog("658", "",
					"",
					"", "停止所有分库同步",null
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return EventRtnType.NORMAL_SUCCESS;
	}

}
