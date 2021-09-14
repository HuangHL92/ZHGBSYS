package com.insigma.siis.local.pagemodel.cbdHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class PersonAttachPageModel extends PageModel{
    public static String personid = "";
	@Override
	public int doInit() throws RadowException {
		String value = this.getRadow_parent_data();
		personid = value.split("@")[0];
		String flag = value.split("@")[1];
		if("1".equals(flag)){
			this.getExecuteSG().addExecuteCode("Ext.getCmp('getSheet').setVisible(false);");
			this.getExecuteSG().addExecuteCode("Ext.getCmp('uploadFileForPerson').setVisible(false);");
		}else if("2".equals(flag)){
			this.getExecuteSG().addExecuteCode("Ext.getCmp('DJBBtn').setVisible(false);");
			this.getExecuteSG().addExecuteCode("Ext.getCmp('uploadFileForPerson').setVisible(false);");
		}else if("3".equals(flag)){
			this.getExecuteSG().addExecuteCode("Ext.getCmp('DJBBtn').setVisible(false);");
			this.getExecuteSG().addExecuteCode("Ext.getCmp('getSheet').setVisible(false);");
		}
		this.request.getSession().setAttribute("personid", personid);
		this.setNextEventName("peopleInfoGrid.dogridquery");
		// TODO Auto-generated method stub
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("peopleInfoGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		personid = (String)this.request.getSession().getAttribute("personid");
		String [] personids = personid.split(",");
		StringBuffer ids = new StringBuffer();
		for(int i=0;i<personids.length;i++){
			ids = ids.append("'"+personids[i]+"',");
		}
		String id = ids.substring(0, ids.length()-1);
		String sql = "select a0000,a0101,a0104,a0107,a0117,a0141,a0192,a0184 from a01 where a0000 in ("+id+")";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	
	/**
	 * 生成登记表
	 */
	@PageEvent("checkPer")
	public int checkPer(String value) throws RadowException, AppException{
		String [] values = value.split("@");
		String a0000 = values[0];
		String a0101 = values[1];
		HBSession sess = HBUtil.getHBSession();
		StringBuffer sql2 = new StringBuffer();
		String [] a0000s = a0000.split(",");
		for(int i=0;i<a0000s.length;i++){
			sql2=sql2.append("'"+a0000s[i]+"',");
		}
		String sql3 = sql2.substring(0, sql2.length()-1);
		String sql = "select a0101 from Rydjb where a0000 in ("+sql3+")";
		List list = sess.createQuery(sql).list();
		CommonQueryBS.systemOut(a0000);
		CommonQueryBS.systemOut(a0101);
		if(list.size()>0){
			StringBuffer name = new StringBuffer();
			for(int i=0;i<list.size();i++){
				name=name.append(""+list.get(i)+",");
			}
			String allName = name.substring(0, name.length()-1);
			this.getExecuteSG().addExecuteCode("$h.confirm3btn('系统提示','"+allName+",已生成登记表，是否重新生成？注：该人员已生成登记表，如再次生成得覆盖原内容且无法恢复。',200,function(id){" +
					"if(id=='yes'){" +
					"	ml('"+a0000+"','"+allName+"');		" +
						"}else if(id=='no'){" +
						"	" +
						"}else if(id=='cancel'){" +
						"	" +
						"}" +
					"});");
		}else{
			this.getExecuteSG().addExecuteCode("ml('"+a0000+"','"+a0101+"');");
		}		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("getCheckListForPerson")
	public int getCheckListForPerson() throws RadowException, AppException{
		String listString=null;
		int cnt=0;
		List<HashMap<String, Object>> gdlist=new ArrayList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("peopleInfoGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			
			if(!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				listString=listString+"@|"+hm.get("a0000")+"|";
				++cnt;
			}
		}
		if(!"".equals(listString)&&listString!=null)
		    listString=listString.substring(listString.indexOf("@")+1,listString.length());
 		this.getPageElement("checkListForPerson").setValue(listString
 				);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/* 生成被登记人员的字符串
	 * @author lxy
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getSheet")
	public int getSheet(String value) throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		String djString=null;
		String [] values = value.split("@");
		String a0000 = values[0];
		String a0101 = values[1];
		String [] a0000s = a0000.split(",");
		String sql = "select a0201a from a02 where a0000 = '"+a0000s[0]+"'";
		String dw = sess.createSQLQuery(sql).list().get(0).toString();
		String djs = ""+a0000s.length;
		if(a0000s.length<1){
			this.setMainMessage("请选择需要登记的人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//循环登记列表重新生成登记字符串
		for (int i=0;i<a0000s.length;i++) {
			djString=djString+"@|"+a0000s[i]+"|";
		}
		
		if(djString!=null){
			djString=djString.substring(djString.indexOf("@")+1,djString.length());
		}
 		this.getPageElement("djgridString").setValue(djString);
 		this.getPageElement("djs").setValue(djs);
 		this.getPageElement("dw").setValue(dw);
		this.getExecuteSG().addExecuteCode("downLoadTmp()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查看/删除附件
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("modifyAttach")
	public int modifyAttach(String value) throws RadowException{
		CommonQueryBS.systemOut(value);
		this.setRadow_parent_data(value);
		this.openWindow("modifyFileWindow", "pages.search.ModifyAttach");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
