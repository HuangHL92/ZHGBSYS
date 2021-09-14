package com.insigma.siis.local.pagemodel.sysmanager.sysuser;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Transaction;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.AddType;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class GroupModifyWindowPageModel extends PageModel {

	@PageEvent("uptBtn.onclick")
	public int saveBtnOnClick() throws RadowException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		SmtGroup group = new SmtGroup();
		SmtGroup group1 = new SmtGroup();//存放修改前数据的对象
		String data = this.getRadow_parent_data();
		String[] datas = data.split(",");
		String groupid = datas[0];
		HBSession sess = HBUtil.getHBSession();
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		String hql = "From SmtGroup S where S.id = '"+groupid+"'";
		group =(SmtGroup) sess.createQuery(hql).list().get(0);
		
		//拷备数据
		PropertyUtils.copyProperties(group1, group);
		
		String name = this.getPageElement("name1").getValue();
		if(name.indexOf(" ")!=-1){
			this.setMainMessage("用户组名中不能包含空格");
			return EventRtnType.NORMAL_SUCCESS;
		}
		group.setName(this.getPageElement("name1").getValue().trim());
		sess.saveOrUpdate(group);
		ts.commit();
		//		if(datas[1].equals("groupShowWin")){
//			this.createPageElement("groupgrid", ElementType.GRID, true).reload();
//		}
		/*if(group.getParentid()==null){
			this.getExecuteSG().addExecuteCode("window.parent.a("+group.getId()+","+group.getName()+")");
		}*/
		try {
			new LogUtil().createLog("62", "SMT_GROUP",group.getId(), group.getName(), "", new Map2Temp().getLogInfo(group1,group));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMainMessage("更新成功");
		this.closeCueWindowByYes("modifyGroupWin");
		//this.getExecuteSG().addExecuteCode("alert(1)");
		this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException{
		this.isShowMsg = false;
		String data = this.getRadow_parent_data();
		String[] datas = data.split(",");
		String groupid = datas[0];
		GroupVO group = new GroupVO();
		try {
			group = PrivilegeManager.getInstance().getIGroupControl().findById(groupid);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		if(group.getName() != null)
			this.getPageElement("name1").setValue(group.getName());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*private String getOwnerIdorParentidByName(String name, boolean isOwner) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String hql = "";
		if(isOwner){
			hql = "select id from SmtUser where loginname=:name";
		}else{
			hql = "select id from SmtGroup where name=:name";
		}
		List<String> list = session.createQuery(hql).setString("name", name).list();
		if(list == null || list.isEmpty()){
			return "";
		}
		return list.get(0);
	}
	
	private String getOwnerOrParentName(String id, boolean isOwner) throws RadowException{
		String name = "";
		try {
			if(isOwner){
				UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(id);
				name = user.getLoginname();
			}else{
				GroupVO group = PrivilegeManager.getInstance().getIGroupControl().findById(id);
				name = group.getName();
			}
		} catch (PrivilegeException e) {
			this.setMainMessage("查询持有者或父类组异常");
		}
		return name;
	}*/
}