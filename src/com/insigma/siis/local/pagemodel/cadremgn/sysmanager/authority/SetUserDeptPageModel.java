package com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;

public class SetUserDeptPageModel extends PageModel{
	/**
	 * 初始化
	 */
	@Override
	public int doInit() throws RadowException {
		try {
			this.setNextEventName("InitX");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@SuppressWarnings("unchecked")
	@PageEvent("InitX")
	@NoRequiredValidate
	public int InitX()throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		CurrentUser user = SysUtil.getCacheCurrentUser();
		HashMap<String,String> map= new LinkedHashMap<String,String>();
		try {
			if("40288103556cc97701556d629135000f".equals(user.getId())) {
				String sql2 = "select id,usergroupname from SMT_USERGROUP where sid is null  ";
				List<Object[]> list=sess.createSQLQuery(sql2).list();
				if(list!=null&&list.size()>0){
					for(Object[] obj:list){
						String TABLE_CODE = obj[0].toString();
						String TABLE_NAME = obj[1].toString();
						map.put(TABLE_CODE, TABLE_NAME);
					}
				}
			}
			String sql= " select id,usergroupname from SMT_USERGROUP where sid='"+user.getDept()+"' order by sortid";
			List<Object[]> list=sess.createSQLQuery(sql).list();
			if(list!=null&&list.size()>0){
				for(Object[] obj:list){
					String TABLE_CODE = obj[0].toString();
					String TABLE_NAME = obj[1].toString();
					map.put(TABLE_CODE, TABLE_NAME);
				}
			}
			((Combo)this.getPageElement("dept")).setValueListForSelect(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("set.onclick")
	@NoRequiredValidate
	public int set()throws RadowException{
		CurrentUser user = SysUtil.getCacheCurrentUser();
		HBSession sess = HBUtil.getHBSession();
		try {
			String userid = this.getPageElement("userid").getValue();
			String dept = this.getPageElement("dept").getValue();
			if(userid==null || userid.equals("")) {
				this.setMainMessage("人员数据异常， 请重试！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(dept==null || dept.equals("")) {
				this.setMainMessage("请选择部门！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			sess.createSQLQuery("update smt_user set dept='"+dept+"' where userid='"+userid+"'").executeUpdate();
			sess.flush();
			this.getExecuteSG().addExecuteCode("Ext.MessageBox.alert('消息提示', '设置成功！', function(e){window.close();realParent.reloadTree();});");;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
