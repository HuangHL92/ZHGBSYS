package com.insigma.siis.local.pagemodel.sysmanager.sysuser;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Transaction;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtAct;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class UserModifyWindowPageModel extends PageModel {
	@Override
	public int doInit() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String data= this.getRadow_parent_data();
		CommonQueryBS.systemOut(data);
		String userid=data;
		UserVO user = new UserVO();
		try {
			user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		if(user.getName() != null){
			this.getPageElement("username").setValue(user.getName());
		}
		this.getPageElement("usertype").setValue(user.getUsertype());
		this.getPageElement("useful").setValue(user.getStatus());
		this.getPageElement("loginname").setValue(user.getLoginname());
		this.getPageElement("work").setValue(user.getWork());
		this.getPageElement("mobile").setValue(user.getMobile());
		this.getPageElement("tel").setValue(user.getTel());
		this.getPageElement("email").setValue(user.getEmail());
		if(user.getOtherinfo()!=null){
			String hql = "from B01 t where t.b0111='"+user.getOtherinfo()+"'";
			List list = sess.createQuery(hql).list();
			if(list.size()!=0){
				B01 b = (B01)list.get(0);
				this.getPageElement("ssjg").setValue(b.getB0101());
			}
		}
		this.setNextEventName("rolegrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("savebut.onclick")
	@NoRequiredValidate
	public int savebutOnclick() throws RadowException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		String username = this.getPageElement("username").getValue();
		String ssjg = this.getPageElement("ssjg").getValue();
		String useful = this.getPageElement("useful").getValue();
		String loginname = this.getPageElement("loginname").getValue();
		UserVO cueuser=PrivilegeManager.getInstance().getCueLoginUser();
		String loginnname=cueuser.getLoginname();
		String data= this.getRadow_parent_data();
		String userid=data;
		UserVO user = new UserVO();
		try {
			user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		} catch (PrivilegeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(username.equals("")){
			this.setMainMessage("请输入姓名");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(ssjg.equals("")){
			this.setMainMessage("请选择所属机构");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(loginnname.equals(user.getLoginname())){
			if(!(useful.equals(user.getStatus()))){
				this.setMainMessage("您好，用户无法修改用户的状态。");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		if(username.indexOf(" ")!=-1){
			this.setMainMessage("姓名中不能包含空格");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("".equals(loginname)){
			this.setMainMessage("请输入用户登录名");
			return EventRtnType.NORMAL_SUCCESS;
		}
			this.saveUser("yes");
			return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveUser")
	public int saveUser(String acredit) throws RadowException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		HBSession sess = HBUtil.getHBSession();
		String data= this.getRadow_parent_data();
		String userid=data;
		UserVO user = new UserVO();
		try {
			user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage());
		}
		
		//创建存放旧数据的对象
		UserVO user1 = new UserVO();
		//拷备数据
		PropertyUtils.copyProperties(user1, user);
		
		user.setWork(this.getPageElement("work").getValue());
		user.setMobile(this.getPageElement("mobile").getValue());
		user.setTel(this.getPageElement("tel").getValue());
		user.setEmail(this.getPageElement("email").getValue());
		String useful = this.getPageElement("useful").getValue();
		if(useful.equals("")){
			useful=user.getStatus();
		}
		String loginname = this.getPageElement("loginname").getValue();
		user.setLoginname(loginname);
		user.setStatus(useful);
		user.setName(this.getPageElement("username").getValue());
		String ssjg = this.getPageElement("ssjg").getValue();
			String hql = "from B01 t where t.b0101='"+ssjg+"'";
			List list1 = sess.createQuery(hql).list();
			if(list1.size()!=0){
				B01 b = (B01)list1.get(0);
				ssjg=b.getB0111();
			}
		String usertype = this.getPageElement("usertype").getValue();
		if("2".equals(usertype)){
			user.setIsleader("0");
		}else{
			user.setIsleader("1");
		}
		user.setUsertype(usertype);
		user.setOtherinfo(ssjg);
		user.setRegionid(ssjg);
		String groupid = this.getRadow_parent_data();
		try {
			PrivilegeManager.getInstance().getIUserControl().updateUser(user);
		} catch (PrivilegeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			new LogUtil().createLog("65", "SMT_USER",user.getId(), user.getLoginname(), "", new Map2Temp().getLogInfo(user1,user));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.createPageElement("memberGrid", ElementType.GRID, true).reload();
		this.setMainMessage("保存成功");
		this.closeCueWindowByYes("modifyUserWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 判断用户登录名是否重复
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("loginname.onchange")
	@NoRequiredValidate
	public int LoginNameOnChonge() throws RadowException{
		String loginname = this.getPageElement("loginname").getValue();
		try {
			List list = PrivilegeManager.getInstance().getIUserControl().queryByName(loginname, true);
			if(list.size()==0){
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.setMainMessage("该登录名已经存在");
		} catch (PrivilegeException e) {
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("rolegrid.dogridquery")
	public int doGroupGridQuery(int start, int limit) throws RadowException {
		String data= this.getRadow_parent_data();
		String sql = "select sa.dispatchauth,sr.roleid,sr.rolename,sr.status,sr.roledesc from Smt_Act sa,Smt_Role sr where sa.roleid=sr.roleid and objectid='"+data+"'";
		this.pageQuery(sql,"SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
}
