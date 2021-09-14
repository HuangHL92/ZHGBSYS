package com.insigma.siis.local.business.helperUtil;

import java.io.File;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.siis.local.business.entity.B01;

public class SysManagerUtils {

	
	
	/**
	 * 获取用户名称
	 * @return
	 */
	public static String getUserPwd(){
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		return user.getPasswd();
	}
	/**
	 * 获取用户名称
	 * @return
	 */
	public static String getUserName(){
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		return user.getName();
	}
	public static String getUserloginName(){
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		return user.getLoginname();
	}
	/**
	 * 获取用户id
	 * @return
	 */
	public static String getUserId(){
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		return user.getId();
	}
	
	/**
	 * 获取机构id
	 * @return
	 */
	public static String getUserOrgName(){
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String userOrgid = user.getOtherinfo();
		String name = "";
		if(userOrgid!=null){
			B01 b01 = (B01)HBUtil.getHBSession().get(B01.class, userOrgid);
			if(b01!=null)
				name = b01.getB0101();
		}
		
		return name;
	}
	/**
	 * 获取机构id
	 * @return
	 */
	public static String getUserOrgid(){
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String userOrgid = user.getOtherinfo();
		return userOrgid;
	}
	
	/**
	 * 获取用户组id
	 * @return
	 */
	public static String getUserGroupid(){
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String userid = user.getId();
		String sql = "select id from "
				+ " (select t.id,t.type,level lv from userinfo t "
				+ " start with t.id='"+userid+"' connect by t.id=prior t.pid ) t"
						+ " where t.type='group' order by lv asc";
		HBSession sess = HBUtil.getHBSession();
		List<Object> list = sess.createSQLQuery(sql).list();
		if(list.size()>0){
			return list.get(0).toString();
		}
		return null;
	}
	
	
	public String getWebrootPath(){
		String rootPath = this.getClass().getResource("/").getPath();
		File f = new File(rootPath);
		File f1 = f.getParentFile();
		String path = f1.getParent();
		return path;
		
	}
	public static String getGroupName(String spB04) {
		String sql = "select t.usergroupname from SMT_USERGROUP t where t.id='"+spB04+"'";
		HBSession sess = HBUtil.getHBSession();
		List<Object> list = sess.createSQLQuery(sql).list();
		if(list.size()>0){
			return list.get(0).toString();
		}
		return null;
	}
	//杭州干部获取用户组id
	public static String getDeptId() {
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(user==null){
			return "";
		}
		return user.getDept();
	}
	public static String getUserName(String spb04) {
		String sql = "select t.username from SMT_USER t where t.userid='"+spb04+"'";
		HBSession sess = HBUtil.getHBSession();
		List<Object> list = sess.createSQLQuery(sql).list();
		if(list.size()>0){
			return list.get(0).toString();
		}
		return null;
	}
}
