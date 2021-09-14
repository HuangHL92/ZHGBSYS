package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class DataExpInfoPageModel extends PageModel{
	private static String commStr= "select t.name ,t.b0101,t.psncount,t.id,t.starttime ,t.endtime ,t.downtimes ,t.status ,t.zipfile,v.username createuser,t.createuser userid from expinfo t,smt_user v where t.createuser = v.userid ";
	@Override
	public int doInit() throws RadowException {
		
		//HBSession sess = HBUtil.getHBSession();
		//MYSQL数据库上报按钮，是否显示控制
		/*String mysqlexport_isuseful = sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'MYSQLEXPORT_ISUSEFUL'").uniqueResult().toString();
		
		if(mysqlexport_isuseful != null && mysqlexport_isuseful.equals("ON")){
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('mysqlExp').show();");
			
		}else{
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('mysqlExp').hide();");
		}*/
		
		
		String sql = commStr;
		this.getPageElement("sql").setValue(sql.toString());
		this.setNextEventName("expInfogrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	@PageEvent("expInfogrid.dogridquery")
	public int dogridQuery(int start,int limit) throws RadowException{
		//权限控制 -->只能下载当前用户导出的文件
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String sql = this.getPageElement("sql").getValue().replace("t.downtimes", DBUtil.getDBType() == DBUtil.DBType.ORACLE ? "NVL(t.downtimes,0) downtimes" :"IFNULL(t.downtimes,0) downtimes") + "and v.userid ='" + user.getId() + "' order by t.starttime desc";
		this.pageQuery(sql.toString(),"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("btn1.onclick")
	public int sbQuery() throws RadowException{
		String user = this.getPageElement("userId").getValue();
		String time = this.getPageElement("time").getValue();
		String time2 = this.getPageElement("time2").getValue();
		StringBuffer sql = new StringBuffer();
		sql.append(commStr);
 		if(!"".equals(user.trim().toString()) && user != null){
			sql.append("   and (v.loginname = '"+user+"' or v.username = '"+user+"')");
		}
		if(!"".equals(time.trim().toString()) && time != null){
			sql.append(" and t.starttime >= '"+time+"'");
		}
		if(!"".equals(time2.trim().toString()) && time2 != null){
			sql.append(" and t.starttime <= '"+time2+"'");
		}
		this.getPageElement("sql").setValue(sql.toString());
		this.setNextEventName("expInfogrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	
	@PageEvent("count")
	public int count(String id) throws RadowException{
		@SuppressWarnings("unchecked")
		List<String> list = HBUtil.getHBSession().createSQLQuery("select downtimes from expinfo where id ='"+id+"'").list();
		try {
			if(list != null && list.size()>0){
				String downtimes = String.valueOf(Integer.parseInt(null == list.get(0) ? "0" : list.get(0))+1);
				HBUtil.executeUpdate("update expinfo set downtimes = '"+downtimes+"' where id ='"+id+"'");
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@PageEvent("v")
	public int verifyExits(String filePath) throws RadowException, UnsupportedEncodingException{
		filePath = URLDecoder.decode(filePath, "utf-8");
        File file = new File(filePath);
		if(!file.exists()){
			this.setMainMessage("文件已被删除");
		}else{
			this.getExecuteSG().addExecuteCode("download()");
			String houzhui = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());  //文件后缀
			//日志记录
			if("hzb".equalsIgnoreCase(houzhui)){
				new LogUtil("411", "IMP_RECORD", "", "", "数据导出", new ArrayList(),PrivilegeManager.getInstance().getCueLoginUser()).start();
			}else if("7z".equalsIgnoreCase(houzhui)){
				new LogUtil("412", "IMP_RECORD", "", "", "数据导出", new ArrayList(),PrivilegeManager.getInstance().getCueLoginUser()).start();
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("getCheckList")
	public int getCheckList() throws RadowException, AppException{
		String listString=null;
		int cnt=0;
		List<HashMap<String, Object>> gdlist=new ArrayList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("expInfogrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			
			if(!"".equals(hm.get("check"))&&(Boolean) hm.get("check")){
				listString=listString+"@|"+hm.get("id")+"|";
				++cnt;
			}
		}
		if(!"".equals(listString)&&listString!=null)
		    listString=listString.substring(listString.indexOf("@")+1,listString.length());
		System.out.println("---->"+listString);
 		this.getPageElement("checkList").setValue(listString
 				);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("delimpinfo")
	public int delimpinfo() throws RadowException{
		String impids = this.getPageElement("checkList").getValue();
		if("".equals(impids)){
			this.setMainMessage("请勾选要删除的信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		dialog_set("delimpconfirm","您确定要删除选中的信息么？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void dialog_set(String fnDelte, String strHint){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName(fnDelte);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage(strHint); // 窗口提示信息
	}
	
	@PageEvent("delimpconfirm")
	public int delimpconfirm() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String impids = this.getPageElement("checkList").getValue();
		String ids = impids.replace("|", "'").replace("@", ",");
		if(ids!=null){
			//删除文件
			List<Object> list = sess.createSQLQuery("SELECT ZIPFILE FROM EXPINFO WHERE ID IN ("+ids+")").list();
			if(list!=null && list.size()>0){
				for(int i = 0;i<list.size();i++){
					Object zippath = list.get(i);
					if(zippath!=null){
						File file = new File(zippath.toString());
						if(file.exists()){
							file.delete();
						}
					}
				}
			}
			sess.createSQLQuery("delete from expinfo where id in ("+ids+")").executeUpdate();
		}
		this.setNextEventName("expInfogrid.dogridquery");
		this.getPageElement("checkList").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
