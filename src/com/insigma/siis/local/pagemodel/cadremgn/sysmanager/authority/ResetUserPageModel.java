package com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.RSACoder;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtAcl;
import com.insigma.odin.framework.privilege.entity.SmtFunction;
import com.insigma.odin.framework.privilege.entity.SmtRole;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.util.PrivilegeUtil;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.tree.TreeNode;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.MD5;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.InfoGroup;
import com.insigma.siis.local.business.entity.NoticeRecipent;
import com.insigma.siis.local.business.entity.UserInfoGroup;
import com.insigma.siis.local.business.entity.UserPerson;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.epsoft.util.Node;
import com.insigma.siis.local.epsoft.util.NodeUtil;
import com.insigma.siis.local.pagemodel.cadremgn.util.CheckUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.sysmanager.user.PsWindowPageModel;

import net.sf.json.JSONArray;

public class ResetUserPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
//		this.getPageElement("useful").setValue("1");
		this.setNextEventName("initX");
		return 0;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		String userid = this.getPageElement("subWinIdBussessId").getValue();
		String sql="select id,usergroupname  from smt_usergroup";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
		HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
		for(int i=0;i<listCode.size();i++){
			String id = listCode.get(i).get("id").toString();
			mapCode.put(id, listCode.get(i).get("usergroupname"));
		}
		((Combo)this.getPageElement("dept")).setValueListForSelect(mapCode);
		if(userid==null||"".equals(userid)){
			this.setMainMessage("请先选择要更改的用户");
		}
		UserVO user = new UserVO();
		try {
			user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		} catch (PrivilegeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getPageElement("loginname").setValue(user.getLoginname());
		this.getPageElement("username").setValue(user.getName());
		this.getPageElement("work").setValue(user.getWork());
		this.getPageElement("mobile").setValue(user.getMobile());
		this.getPageElement("tel").setValue(user.getTel());
		this.getPageElement("email").setValue(user.getEmail());
		this.getPageElement("dept").setValue(user.getDept());
		this.getPageElement("validity").setValue(user.getValidity());
		//this.getPageElement("usertype").setValue(user.getUsertype());
		if(user.getValidity()==null||"".equals(user.getValidity())){
			//this.getPageElement("kg").setValue("OFF");
		}else{
			this.getPageElement("kg").setValue("ON");
		}
		//this.getPageElement("useful").setValue();
		return 0;
	}
	
	@PageEvent("resetUser")
	public int resetUserPsw(String id) throws RadowException{
		String isleader = PrivilegeManager.getInstance().getCueLoginUser().getIsleader();
		if("0".equals(isleader)){
			this.setMainMessage("您好，您非管理员用户，没有操作此模块的权限");
			return EventRtnType.NORMAL_SUCCESS;
		}
			this.addNextEvent(NextEventValue.YES, "reset",id);
			this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
			this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
			this.setMainMessage("您确定要重置改用户的登录密码么？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("reset")
	public int reset(String users) throws RadowException{
//		String users = choosePersonIds("memberGrid");
		String[] userids = users.split(",");
		for(int i=0;i<userids.length;i++){
			UserVO user = new UserVO();
			try {
				user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userids[i]);
			} catch (PrivilegeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String newPwd = user.getLoginname();
			String newPassword = MD5.MD5(newPwd);
			user.setPasswd(newPassword);
			try {
				PrivilegeManager.getInstance().getIUserControl().updateUser(user);
			} catch (PrivilegeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List list = new ArrayList();
		try {
			new LogUtil().createLog("68", "SMT_USER","","", "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMainMessage("重置密码成功，重置后的密码和用户登录名相同。");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
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
	
	@PageEvent("savebut.onclick")
	@NoRequiredValidate
	public int savebutOnclick() throws RadowException, SQLException{
		String loginname = this.getPageElement("loginname").getValue();
		String username = this.getPageElement("username").getValue();
		String password = this.getPageElement("password").getValue();
		String surepassword = this.getPageElement("surepassword").getValue();
		
		String mobile = this.getPageElement("mobile").getValue();
		String tel = this.getPageElement("tel").getValue();
		String email = this.getPageElement("email").getValue();
//		String useful = this.getPageElement("useful").getValue();
//		String ssjg = this.getPageElement("ssjg").getValue();
		if(loginname.equals("")) {
			this.setMainMessage("请输入用户登录名");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(username.equals("")){
			this.setMainMessage("请输入姓名");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(loginname.indexOf(" ")!=-1){
			this.setMainMessage("用户登录名中不能包含空格");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(username.indexOf(" ")!=-1){
			this.setMainMessage("姓名中不能包含空格");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*if(password.equals("")){
			this.setMainMessage("请输入密码");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		if((password!=null&&!"".equals(password))&&surepassword.equals("")){
			this.setMainMessage("请输入确认密码");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(CheckUtil.isContainsChinese(password)){
			this.setMainMessage("密码不能包含中文");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!PsWindowPageModel.checkPasswd(password)){
			this.setMainMessage("密码长度必须大于10位，由数字，字母，特殊字符组成");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if((password!=null&&!"".equals(password))&&!surepassword.equals(password)){
			this.setMainMessage("密码与确认密码不一致");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*if(useful.equals("")){
			this.setMainMessage("请选择用户状态");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		if(UserGroupPageModel.isSpecialChar(loginname)){
			this.getExecuteSG().addExecuteCode("Ext.getBody().unmask();");
			this.setMainMessage("用户名只支持中文或者英文");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(UserGroupPageModel.isSpecialChar(username)){
			this.getExecuteSG().addExecuteCode("Ext.getBody().unmask();");
			this.setMainMessage("姓名只支持中文或者英文");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!"".equals(mobile)&&!CheckUtil.isMobile(mobile)){
			this.setMainMessage("请输入正确的手机号码");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!"".equals(tel)&&!CheckUtil.isPhone(tel)){
			this.setMainMessage("请输入正确的办公电话");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!"".equals(email)&&!CheckUtil.checkEmaile(email)){
			this.setMainMessage("请输入正确的邮箱号");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.saveUser();
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("saveUser")
	public int saveUser() throws RadowException, SQLException{
		this.getExecuteSG().addExecuteCode("Ext.get(document.body).mask('请稍等...',odin.msgCls)");
		//String cueUser = PrivilegeManager.getInstance().getCueLoginUser().getName();
		String cueUserID = PrivilegeManager.getInstance().getCueLoginUser().getId();
		
		String userid = this.getPageElement("subWinIdBussessId").getValue();
		UserVO user = new UserVO();
		UserVO user_o = new UserVO();
		try {
			user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
		} catch (PrivilegeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			user_o = user.clone();
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		Date createdate = new Date();
		
		user.setCreatedate(createdate);
		user.setLoginname(this.getPageElement("loginname").getValue());
//		user.setStatus(this.getPageElement("useful").getValue());
		user.setName(this.getPageElement("username").getValue());
		
		String password = this.getPageElement("password").getValue();
		String surepassword = this.getPageElement("surepassword").getValue();
		
		if((password!=null&&!"".equals(password))&&(surepassword!=null&&!"".equals(surepassword))&&password.equals(surepassword)){
			String newPassword = MD5.MD5(password);
			user.setPasswd(newPassword);
			String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJTrQ2uKF1wb6SQmvMqCjhsd0WRvWRK/nrhYj5mZ3D0ZHJ2mqEypXh0Xac8GrgTwbEF1NKKFJyKS7Wl5jHIkoTUCAwEAAQ==";
			
			try {
				byte[] code2 = RSACoder.encryptByPublicKey(password.getBytes(), Base64.decodeBase64(publicKey.getBytes()));
				user.setSec(new String(Base64.encodeBase64(code2)));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		String validity = this.getPageElement("validity").getValue();
		String work = this.getPageElement("work").getValue();
		String mobile = this.getPageElement("mobile").getValue();
		String tel = this.getPageElement("tel").getValue();
		String email = this.getPageElement("email").getValue();
		String dept = this.getPageElement("dept").getValue();
		user.setValidity(validity);
		user.setWork(work);
		user.setMobile(mobile);
		user.setTel(tel);
		user.setEmail(email);
		user.setOwnerId(cueUserID);
		user.setDept(dept);
		try {
			new LogUtil().createLogNew(user.getId(),"用户修改","smt_user",user.getId(),user.getLoginname(), new Map2Temp().getLogInfo(user_o,user));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			PrivilegeManager.getInstance().getIUserControl().updateUser(user);
		} catch (PrivilegeException e2) {
			e2.printStackTrace();
		}
		
		
		
		//更新干部教育、干部监督的用户
		//updateForJDJY(user.getId(),this.getPageElement("loginname").getValue(),this.getPageElement("username").getValue(),this.getPageElement("password").getValue());

		this.getExecuteSG().addExecuteCode("Ext.getBody().unmask();");
//		this.setMainMessage("保存成功");
//		this.closeCueWindowByYes("createAdminWin");
		this.getExecuteSG().addExecuteCode("Ext.MessageBox.alert('消息提示', '保存成功', function(e){ if ('ok' == e){parent.Ext.getCmp('resetUser').close();realParent.reloadTree();}});");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void updateForJDJY(String cueUserID, String loginname, String username,
			String pwd) throws RadowException, SQLException {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		CallableStatement cs = null;
		//干部教育
		String proc = "call UpdateUser(?,?,?,?,?,?,?)";
		cs = conn.prepareCall(proc);
		cs.setString(1, "gbjy");
		cs.setString(2, "updateuser");
		cs.setString(3, cueUserID);
		cs.setString(4, username);
		cs.setString(5, pwd);
		cs.setString(6, "0");
		cs.setString(7, "dwglyzz");//默认业务员
		cs.execute();
		cs.clearBatch();
		
		//干部监督
		cs.setString(1, "gbjd");
		cs.setString(2, "updateuser");
		cs.setString(3, cueUserID);
		cs.setString(4, username);
		cs.setString(5, pwd);
		cs.setString(6, "0");
		cs.setString(7, "secAdmin");//默认业务员
		cs.execute();
		cs.clearBatch();
		
		cs.close();
		
	}
	
	/**
	 *  获取用户的排序编号
	 * @param parentid
	 * @return
	 */
	public Long getSortid(String dept){
		
		/*String sql = "select max(case when t.sortid is null then 0 else t.sortid end)+1 sortid from smt_role t";*/
		String sql = "select max(case when t.sortid is null then 0 else t.sortid end)+1 sortid from smt_user t where t.dept = '"+dept+"'";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		Long sortid = 1L;
		if (list.get(0) == null) {
			sortid = 1L;
		} else {
			sortid = Long.parseLong(list.get(0).toString());
		}
		
		return sortid;
	}
	/**
	 *  获取用户的排序编号
	 * @param parentid
	 * @return
	 */
	public Long getSortid(String parentid,String groupid){
		
		String sql = "select max(case when t.sortid is null then 0 else t.sortid end)+1 sortid from smt_user t where t.owner='"+ parentid.trim() + "'" +
				" and exists(select 1 from smt_usergroupref su where t.userid = su.userid and su.groupid = '"+groupid+"')";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		Long sortid = 1L;
		if (list.get(0) == null) {
			sortid = 1L;
		} else {
			sortid = Long.parseLong(list.get(0).toString());
		}
		
		return sortid;
	}
	
	public void doOrgGrant(String value,String userid,Transaction ts) throws RadowException{
		
		StopWatch w = new StopWatch();
		w.start();
		StopWatch w1 = new StopWatch();
		w1.start();
		//获取当前登录用户的编号
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String cueLoginname = PrivilegeManager.getInstance().getCueLoginUser().getLoginname();

		//获取被授权用户编号（对其授权的用户编号，不是操作用户编号）
//		String userid = this.getRadow_parent_data();
		HBSession sess = HBUtil.getHBSession();
		//查询用户信息
		String hql = "From SmtUser S where S.id = '"+userid+"'";
		SmtUser user =(SmtUser) sess.createQuery(hql).list().get(0);
		String id = "";//定义变量：机构编号
		String type = "";//定义变量：权限类型
		String sChecked = "";//定义变量：是否被勾选
		String sValue = "";//定义变量：checkbox的值
		String delSql = "";//删除语句的变量
		List alreadyExeB0111In = new ArrayList();//定义list变量，用以存储已经执行过批量插入的机构编号
		List alreadyExeB0111Out = new ArrayList();//定义list变量，用以存储已经执行过批量删除的机构编号
		List alreadyExeB0111Up = new ArrayList();//定义list变量，用以存储已经执行过批量更新的机构编号
		//先将对应的数据全部删除，后面再进行插入
		
		//定义变量，记录操作的用户是否已经进行授权
		boolean isAuthed = true;
		//查询操作的用户是否已经机构授权
		List userdep_list = sess.createQuery("from UserDept where userid = '"+userid+"'").list();
		if(userdep_list.size() == 0){
			isAuthed = false;
		}
		//截取机构编号
		String[] ids = value.split(",");
		w1.stop();
		CommonQueryBS.systemOut("机构授权前准备："+w1.elapsedTime());
//		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		//循环处理机构授权
		for(int i=0;i<ids.length;i++){
			String[] values = ids[i].split(":");
			id = values[0].substring(0,values[0].length()-1);
			type = values[0].substring(values[0].length()-1);
			sChecked = values[1];
			sValue = values[2];
			
			String[] values1 = ids[i+1].split(":");
			String id_1 = values1[0].substring(0,values[0].length()-1);
			String type_1 = values1[0].substring(values[0].length()-1);
			String sChecked_1 = values1[1];
			String sValue_1 = values1[2];
			//当该节点为在“勾选下级”被选中后才被勾选，将节点机构及其下级机构查询并放入到hashSet中
			if("1".equals(sValue)){
				if(!alreadyExeB0111In.contains(id.substring(0, id.length()-4))){
					//当登陆用户是system和admin时，包含下级时对机构的所有下级机构进行授权，不涉及用户的机构权限问题。
					if("system".equals(cueLoginname) || "admin".equals(cueLoginname)){
						delSql = " AND B0111 LIKE '"+id+"%' ";
						hql = " AND B1.B0111 LIKE '"+id+"%' ";
						
					}else{//当登陆用户非system和admin时，需要关联登陆用户的机构权限表，仅授权自己有权限的机构
						if(DBType.ORACLE==DBUtil.getDBType()){
							delSql = " AND B0111 IN(SELECT B0111 FROM COMPETENCE_USERDEPT B1 WHERE B1.B0111 LIKE '"+id+"%' AND B1.USERID = '"+cueUserid+"') ";
						}else{
							delSql = "AND B0111 in(SELECT a.B0111 FROM " +
									"(SELECT B0111 FROM COMPETENCE_USERDEPT B1 WHERE B1.B0111 LIKE '"+id+"%' AND B1.USERID = '"+cueUserid+"'  ) a)";
						}
						
						hql = "  FROM COMPETENCE_USERDEPT B1 WHERE B1.b0111 LIKE '"+id+"%' AND B1.USERID = '"+cueUserid+"'   ";
					}
					if("true".equals(sChecked_1)){//当维权权限被选中时，机构授权记录的type值为1
						if(isAuthed){//当操作的用户还没有授权时，不做删除操作
							deleteUserDept(delSql,userid);
						}
						if("system".equals(cueLoginname) || "admin".equals(cueLoginname)){
							saveUpdate(hql,userid,"1","SQL");
						}else{
							saveUpdate(hql,userid,"1","SQLNS");
						}
					}else{//当浏览权限被选中时，机构授权记录的type值为0
						if(isAuthed){//当操作的用户还没有授权时，不做删除操作
							deleteUserDept(delSql,userid);
						}
						if("system".equals(cueLoginname) || "admin".equals(cueLoginname)){
							saveUpdate(hql,userid,"0","SQL");
						}else{
							saveUpdate(hql,userid,"0","SQLNS");
						}
					}
				}
				alreadyExeB0111In.add(id);
			}
			//当该节点为在“勾选下级”被选中前已被勾选，则仅保存节点机构
			if("0".equals(sValue)){
				if("true".equals(sChecked_1)){
					if(isAuthed){//当操作的用户还没有授权时，不做删除操作
						deleteUserDept(" AND B0111 = '"+id+"'",userid);
					}
					saveUpdate(id,userid,"1","value");
				}else{
					if(isAuthed){//当操作的用户还没有授权时，不做删除操作
						deleteUserDept(" AND B0111 = '"+id+"'",userid);
					}
					saveUpdate(id,userid,"0","value");
				}
			}
			//当该节点为在“勾选下级”被选中后才被取消，将该节点及其下级节点移出hashSet
			//包含下级情况下，当机构节点的浏览权限取消时，取消选中机构下的所有机构授权
			if("2".equals(sValue)){
				if(!alreadyExeB0111Out.contains(id.substring(0, id.length()-4))){
					
					if("system".equals(cueLoginname) || "admin".equals(cueLoginname)){
						delSql = " AND B0111 LIKE '"+id+"%'";
					}else{
						delSql = " AND B0111 IN(SELECT a.B0111 FROM (SELECT B0111 FROM COMPETENCE_USERDEPT " +
								" WHERE B0111 LIKE '"+id+"%' AND USERID = '"+cueUserid+"')a )";
					}
					deleteUserDept(delSql,userid);
				}
				alreadyExeB0111Out.add(id);
			}else if("2".equals(sValue_1)){//包含下级情况下，当取消机构的维护权限，将机构权限更新为浏览权限
				if(!alreadyExeB0111Up.contains(id.substring(0, id.length()-4))){
					
					if("system".equals(cueLoginname) || "admin".equals(cueLoginname)){
						hql = " AND B0111 LIKE '"+id+"%'";
					}else{
						hql = " AND B0111 IN(SELECT a.B0111 FROM (SELECT B0111 FROM COMPETENCE_USERDEPT " +
								"WHERE B0111 LIKE '"+id+"%' AND USERID = '"+cueUserid+"') a )";
					}
					saveUpdate(hql,userid,"0","update");
				}
				alreadyExeB0111Up.add(id);
			}
			//当该节点为在“勾选下级”被选中前已被取消，仅将该节点移出hashSet
			if("3".equals(sValue)){
				deleteUserDept(" AND B0111 = '"+id+"'",userid);
			}else if("3".equals(sValue_1)){
				saveUpdate(" AND B0111 = '"+id+"'",userid,"0","update");
			}
			i++;
		}
		if(DBType.ORACLE==DBUtil.getDBType()){
			//机构授权表索引重建，索引名需要与数据库的索引名一致
			sess.createSQLQuery("alter index IDX_B0111 rebuild ").executeUpdate();
			sess.createSQLQuery("alter index IDX_USERID rebuild ").executeUpdate();
		}else{
			
		}
		
//		ts.commit();
		List list = new ArrayList();
		try {
			new LogUtil().createLog("69", "COMPETENCE_USERDEPT",user.getId(), user.getLoginname(), "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//机构授权结束
		w.stop();
	}
	
	//删除授权信息
	public void deleteUserDept(String b0111s,String userid){
		StopWatch w3 = new StopWatch();
		w3.start();
		HBSession sess = HBUtil.getHBSession();
		String sql = "";
		if("null".equals(b0111s)){
			sql="DELETE FROM COMPETENCE_USERDEPT WHERE USERID='"+userid+"'";
		}else{
			sql = "DELETE FROM COMPETENCE_USERDEPT WHERE USERID='"+userid+"' "+b0111s;
		}
		sess.createSQLQuery(sql).executeUpdate();
		w3.stop();
		CommonQueryBS.systemOut("删除结束："+w3.elapsedTime());
	}
	
	//保存授权信息
	public void saveUpdate(String id,String userid,String type,String execMode){
		StopWatch w = new StopWatch();
		w.start();
		HBSession sess = HBUtil.getHBSession();
		String sql = "";
		if("SQL".equals(execMode)){
			if(DBType.ORACLE==DBUtil.getDBType()){
				sql = "INSERT INTO COMPETENCE_USERDEPT SELECT sys_guid(),'"+userid+"',B1.B0111,'"+type+"' FROM b01 B1 WHERE 1=1 "+id;
			}else{
				sql = "INSERT INTO COMPETENCE_USERDEPT SELECT uuid(),'"+userid+"',B1.B0111,'"+type+"' FROM b01 B1 WHERE 1=1 "+id;
			}
		}else if("value".equals(execMode)){
			if(DBType.ORACLE==DBUtil.getDBType()){
				sql = "INSERT INTO COMPETENCE_USERDEPT VALUES( sys_guid(),'"+userid+"','"+id+"','"+type+"') ";
			}else{
				sql = "INSERT INTO COMPETENCE_USERDEPT VALUES( uuid(),'"+userid+"','"+id+"','"+type+"') ";
			}
		}else if("update".equals(execMode)){
			sql = "UPDATE COMPETENCE_USERDEPT SET TYPE = '"+type+"' WHERE USERID = '"+userid+"' "+id;
		}else if("SQLNS".equals(execMode)){
			if("1".equals(type)){
				if(DBType.ORACLE==DBUtil.getDBType()){
					sql = "INSERT INTO COMPETENCE_USERDEPT SELECT sys_guid(),'"+userid+"',B1.B0111,TYPE "+id;
				}else{
					sql = "INSERT INTO COMPETENCE_USERDEPT SELECT uuid(),'"+userid+"',B1.B0111,TYPE "+id;
				}
			}else{
				if(DBType.ORACLE==DBUtil.getDBType()){
					sql = "INSERT INTO COMPETENCE_USERDEPT SELECT sys_guid(),'"+userid+"',B1.B0111,'"+type+"' "+id;
				}else{
					sql = "INSERT INTO COMPETENCE_USERDEPT SELECT uuid(),'"+userid+"',B1.B0111,'"+type+"' "+id;
				}
			}
		}
		sess.createSQLQuery(sql).executeUpdate();
		w.stop();
		CommonQueryBS.systemOut("插入或更新语句执行时间："+w.elapsedTime());
	}
	
	//根据机构树中选择的机构，查询机构下的人员信息
	@PageEvent("orgTreeGridJsonData")
	public int getOrgTreeGridJsonData() throws PrivilegeException, AppException, RadowException {
		//登录用户的用户编码
		String userid =this.getParameter("userid");
		//选中的机构编号
		String b0111 = this.getParameter("b0111");
//		HBSession sess = HBUtil.getHBSession();
		//定义返回信息的json串
		StringBuffer jsonStr = new StringBuffer();
		//sql语句
		String sql = "";
		//管理类别
		
//			if(b0111!=null){
				
/*				if(DBType.ORACLE==DBUtil.getDBType()){
					
					sql="select cv.code_value,cv.code_name,cu.userpersonid from code_value cv,COMPETENCE_USERPERSON cu "+
					" where cv.code_value = cu.a0000(+) and cv.code_type = 'ZB130' and cu.userid(+)='"+userid+"' and cu.B0111(+)='"+b0111+"' order by cv.code_value";
				}else if(DBType.MYSQL==DBUtil.getDBType()) {
					sql="select cv.code_value,cv.code_name,cu.userpersonid from code_value cv LEFT JOIN COMPETENCE_USERPERSON cu"+
					" on cv.CODE_VALUE = cu.A0000 and cu.USERID='"+userid+"' and cu.B0111='"+b0111+"' where cv.code_type = 'ZB130' order by cv.code_value";
				}*/
				String[] ll = {};
				String[] wh = {};
				String lls = "";
				String whs = "";
				String sql2 = "";
				if(DBType.ORACLE==DBUtil.getDBType()){
					sql2 = "select nvl(rate,'0') ll,nvl(empid,'0') wh from smt_user su where  su.userid = '"+userid+"'";
				}else{
					sql2 = "select ifnull(rate,'0') ll,ifnull(empid,'0') wh from smt_user su where  su.userid = '"+userid+"'";
				}
				 
				List<Object[]> l = HBUtil.getHBSession().createSQLQuery(sql2).list();
				for (Object[] objects : l) {
					 ll = objects[0].toString().replace("'", ",").split(",");
					 wh = objects[1].toString().replace("'", ",").split(",");
				}
				for (int i = 0; i < ll.length; i++) {
					lls += " when '"+ll[i]+"' then 'true'";
				}
				for (int i = 0; i < wh.length; i++) {
					whs += " when '"+wh[i]+"' then 'true'";
				}
				sql = "select cv.code_value, cv.code_name,  "
						+ ("".equals(lls) ? "'false'" : "case cv.code_value " + lls + " else 'false' end")+"   llchecked,  "
						+ ("".equals(whs) ? "'false'" : "case cv.code_value " + whs + " else 'false' end")+"  whchecked  "
						+ "from code_value cv where cv.code_type ='ZB130'";
				CommonQueryBS query_person = new CommonQueryBS();
				query_person.setConnection(HBUtil.getHBSession().connection());
				query_person.setQuerySQL(sql);
				Vector<?> vector_person = query_person.query();
				Iterator<?> iterator_person = vector_person.iterator();
				int i = 0;
				//开始组装json字符串
				jsonStr.append("[");
				
				while(iterator_person.hasNext()){
					HashMap person_hashmap = (HashMap) iterator_person.next();
					//定义变量，用以判断是否已被选中
					String check="";
					String read = "";
					String write = "";
					if(person_hashmap.get("llchecked") != null && !"".equals(person_hashmap.get("llchecked")) && !"false".equals(person_hashmap.get("llchecked"))){
						read="checked";
					}else{
						read="indeterminate";
					}
					if(person_hashmap.get("llchecked") != null && !"".equals(person_hashmap.get("llchecked")) && !"false".equals(person_hashmap.get("whchecked"))){
						write="checked";
					}else{
						write="indeterminate";
					}
					if(i==0){
						jsonStr.append("{task:'"+person_hashmap.get("code_name")+"',duration:'<input type=\"checkbox\" onclick=\"lbClick(this)\" "+read+" id=\""+person_hashmap.get("code_value")+"0\"  name=\"KD\"/>',user:'<input type=\"checkbox\" onclick=\"lbClick(this)\" "+write+" id=\""+person_hashmap.get("code_value")+"1\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					}else{
						jsonStr.append(",{task:'"+person_hashmap.get("code_name")+"',duration:'<input type=\"checkbox\" onclick=\"lbClick(this)\" "+read+" id=\""+person_hashmap.get("code_value")+"0\"  name=\"KD\"/>',user:'<input type=\"checkbox\" onclick=\"lbClick(this)\" "+write+" id=\""+person_hashmap.get("code_value")+"1\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					}
					i++;
					
				}
				//组装结尾
				jsonStr.append("]");
//			}
		
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	
	public void save(String value,String roleid) throws RadowException {
		String[] nodes = null;
		HashMap<String,String> nodemap = new HashMap<String,String>();
		if(value !=null) {
			nodes = value.split(",");
			for(int i=0;i<nodes.length;i++) {
				nodemap.put(nodes[i].split(":")[0], nodes[i].split(":")[1]);
			}
		}
		StringBuffer addresourceIds = new StringBuffer();
		StringBuffer removeresourceIds = new StringBuffer();
		for(String node :nodemap.keySet()) {
			if(nodemap.get(node).equals("true")) {
				addresourceIds.append(node+",");
			}else if(nodemap.get(node).equals("false")) {
				removeresourceIds.append(node+",");
			}
		}
/*		List<HashMap<String,Object>> functionlist = this.getPageElement("resourcegrid").getValueList();
		for(int i=0;i<functionlist.size();i++) {
			if(functionlist.get(i).get("logchecked").equals(true)) {
				addresourceIds.append(functionlist.get(i).get("functionid")+",");
			}else {
				removeresourceIds.append(functionlist.get(i).get("functionid")+",");
			}
		}*/
//		String roleid = this.getPageElement("id").getValue();
		
		addresourceIds.append("S000000");
		String add = addresourceIds.toString();
		String remove = removeresourceIds.toString();
		//System.out.println("-------------"+add);
		//System.out.println("-------------"+remove);
		//System.out.println(remove);
		addResource(roleid,add);
		removeResource(roleid,remove);
		/*try {
			PrivilegeManager.getInstance().getIRoleControl().addResousesToRole(roleid, addresourceIds.toString());
			PrivilegeManager.getInstance().getIRoleControl().removeResourcesFromRole(roleid, removeresourceIds.toString());
		} catch (PrivilegeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		HBSession sess = HBUtil.getHBSession();
		String hql = "From SmtRole S where S.id = '"+roleid+"'";
		SmtRole user =(SmtRole) sess.createQuery(hql).list().get(0);
		List list = new ArrayList();
		try {
			new LogUtil().createLog("621", "SMT_ACL",roleid,user.getName(), "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		this.setMainMessage("授权成功！");
//		this.closeCueWindowByYes("grantWindow");
	}
	
	
	public void addResource(String roleid,String addids){
		List<Node> nodeList = new ArrayList<Node>();
		HBSession sess = HBUtil.getHBSession();
		 String hql1 = "From SmtFunction";
		 List list2 = sess.createQuery(hql1).list();
		 for(int i=0;i<list2.size();i++){
			 SmtFunction sf = (SmtFunction)list2.get(i);
			 Node node = new Node(sf.getFunctionid(),sf.getTitle(),sf.getParent());
			 nodeList.add(node); 
		 }
		String[] ids = addids.split(",");
		List<SmtFunction> list = new ArrayList<SmtFunction>();
		for(int i=0;i<ids.length;i++){
			String hql="from SmtFunction t where t.functionid = '"+ids[i]+"'";
			List list1 = sess.createQuery(hql).list();
			if(list1.size()==1){
			SmtFunction s = (SmtFunction)list1.get(0);
			list.add(s);
			}
		}
		//List<SmtFunction> parent = new ArrayList<SmtFunction>();
		//List<SmtFunction> child = new ArrayList<SmtFunction>();
		for(int i=0;i<list.size();i++){
			for(int j=0;j<list.size();j++){
				if(list.get(j).getFunctionid().equals("S000000")){
					continue;
				}
				if(list.get(i).getFunctionid().equals(list.get(j).getParent())){
					//parent.add(list.get(i));
					list.remove(i);
					i--;
					break;
				}
			}
		}
		 
		//System.out.println(mt.getChildNodes(nodeList, "402881fe538436cc0153843a10eb0002")); 
		for(int i=0;i<list.size();i++){
			if(list.get(i).getFunctionid().equals("S000000")){
				continue;
			}
			NodeUtil mt = new NodeUtil();
			String str = mt.getChildNodes(nodeList,list.get(i).getFunctionid());
			if(str.contains(",")){
			str = str.substring(1+list.get(i).getFunctionid().length()+1,str.length()-1);
			addids+=","+str;
			}else{
				continue;
			}
			
		}
		addids = addids.replaceAll(" ","");
		String[] resourceids = addids.split(",");
		for(int i=0;i<resourceids.length;i++){
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			String hql3 = "from SmtAcl t where t.resourceid='"+resourceids[i]+"' and t.roleid='"+roleid+"'";
			List list3 = sess.createQuery(hql3).list();
			if(list3.size()==0){
				SmtAcl acl = new SmtAcl();
				acl.setResourceid(resourceids[i]);
				acl.setRoleid(roleid);
				sess.save(acl);
				sess.flush();
//				ts.commit();
			}
		}
		//System.out.println(addids);
	}
	public void removeResource(String roleid,String removeids){
		List<Node> nodeList = new ArrayList<Node>();
		HBSession sess = HBUtil.getHBSession();
		 String hql1 = "From SmtFunction";
		 List list2 = sess.createQuery(hql1).list();
		 for(int i=0;i<list2.size();i++){
			 SmtFunction sf = (SmtFunction)list2.get(i);
			 Node node = new Node(sf.getFunctionid(),sf.getTitle(),sf.getParent());
			 nodeList.add(node); 
		 }
		 String[] ids = removeids.split(",");
			List<SmtFunction> list = new ArrayList<SmtFunction>();
			for(int i=0;i<ids.length;i++){
				String hql="from SmtFunction t where t.functionid = '"+ids[i]+"'";
				List list1 = sess.createQuery(hql).list();
				if(list1.size()==1){
				SmtFunction s = (SmtFunction)list1.get(0);
				list.add(s);
				}
			}
			for(int i=0;i<list.size();i++){
				if(list.get(i).getFunctionid().equals("S000000")){
					continue;
				}
				for(int j=0;j<list.size();j++){
					if(list.get(i).getParent().equals(list.get(j).getFunctionid())){
						//parent.add(list.get(i));
						list.remove(i);
						i--;
						break;
					}
				}
			}
			 
			for(int i=0;i<list.size();i++){
				NodeUtil mt = new NodeUtil();
				String str = mt.getChildNodes(nodeList,list.get(i).getFunctionid());
				if(str.contains(",")){
				str = str.substring(1+list.get(i).getFunctionid().length()+1,str.length()-1);
				removeids+=str+",";
				}else{
					continue;
				}
				
			}
			removeids = removeids.replaceAll(" ","");
			String[] removeresourceids = removeids.split(",");
			for(int i=0;i<removeresourceids.length;i++){
				Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
				String hql3 = "from SmtAcl t where t.resourceid='"+removeresourceids[i]+"' and t.roleid='"+roleid+"'";
				List list3 = sess.createQuery(hql3).list();
				if(list3.size()==1){
					SmtAcl acl = (SmtAcl)list3.get(0);
					sess.delete(acl);
					sess.flush();
//					ts.commit();
				}
			}
			//System.out.println(removeids);
			
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("orgTreeJsonData")
	@EventDataCustomized("fid,id")
	public int getOrgTreeJsonData() throws RadowException, PrivilegeException {
		String node = this.getParameter("node");
		String roleid =this.getParameter("roleid");
		//List functionlist = PrivilegeManager.getInstance().getIResourceControl().getUserFunctions(SysUtil.getCacheCurrentUser().getUserVO(), SysUtil.getCacheCurrentUser().getSceneVO().getSceneid(), false);//SysUtil.getCacheCurrentUser().getFunctionList();//PrivilegeManager.getInstance().getIResourceControl().findByParentId(node);
		List functionlist = new ArrayList();
		functionlist = getUserFunctions(SysUtil.getCacheCurrentUser().getUserVO(), SysUtil.getCacheCurrentUser().getSceneVO().getSceneid(), false);
		for(int i=0;i<functionlist.size();i++){
			FunctionVO function =(FunctionVO) functionlist.get(i);
			String parent = function.getParent();
			String name = function.getTitle();
			if(function.getType().equals("2")){
				functionlist.remove(i);
				i--;
			}else{
				//系统管理菜单不可见，不可授权
				if(parent!=null){
				}
				if(name!=null){

				}
			}
		}

		List<TreeNode> root = PrivilegeManager.getInstance().getIResourceControl().getUserFunctionsTree(functionlist,roleid, node, true);
		this.setSelfDefResData(JSONArray.fromObject(root));
		return EventRtnType.XML_SUCCESS;
	}

	/**
	 * 	查询用户可以操作的授权
	 * @param paramUserVO
	 * @param paramString
	 * @param paramBoolean
	 * @return
	 * @throws PrivilegeException
	 */
	public List<Object> getUserFunctions(UserVO paramUserVO, String paramString, boolean paramBoolean) throws PrivilegeException{
		
		HBSession sess = HBUtil.getHBSession();
		PrivilegeManager localPrivilegeManager = PrivilegeManager.getInstance();
	    SmtUser localSmtUser = (SmtUser)sess.get(SmtUser.class, paramUserVO.getId());
	    UserVO localUserVO = new UserVO();
	    BeanUtil.propertyCopy(localSmtUser, localUserVO);
	    String sql = "";
	    HashMap<String, Object> hashmap = new HashMap();
	    
	    if (localUserVO.getId().equals(GlobalNames.sysConfig.get("SUPER_ID")))
	    {
	      localUserVO.setLoginname(paramUserVO.getLoginname());
	      localUserVO.setPasswd(paramUserVO.getPasswd());
	    }
	    Object localObject = new ArrayList();
	    String str = "select t from SmtAct t,SmtAcl l where (t.objectid='" + localUserVO.getId() + "' or t.objectid in (select ug.groupid from SmtUsergroupref ug where ug.userid='" + localUserVO.getId() + "')) and  t.roleid=l.roleid and l.resourceid='RESOURCE_ALL'";
	    int i = 0;
	    List list_smtact = sess.createQuery(str).list();
	    if (list_smtact.size() > 0)
	      i = 1;
	    if ((!(localPrivilegeManager.getIPermission().isSuperManager(localUserVO))) && (!(localPrivilegeManager.getIPermission().isSysPermission(localUserVO, "RESOURCE_ALL"))) && (i == 0))
	    {
	      List localList = localPrivilegeManager.getIGroupControl().getGroupsByUserId(paramUserVO.getId());
	      if ((localList == null) || (localList.size() == 0))
	    	  sql = "select distinct f from SmtFunction f,SmtResource r,SmtAct t where f.resourceid = r.id and r.status='1' and t.objectid=:userID order by f.parent,f.orderno";
	      else
	    	  sql = "select distinct f from SmtFunction f,SmtResource r,SmtAct t,SmtUsergroupref ug where f.resourceid = r.id and r.status='1'  and (t.objectid=:userID or (ug.userid=:userID and t.objectid = ug.groupid) ) order by f.parent,f.orderno";
	      hashmap.put("userID", localUserVO.getId());
	      localObject = sess.createQuery(sql).setParameter("userID", localUserVO.getId()).list();
	    }
	    else
	    {
	    	sql = "select f from SmtFunction f,SmtResource r where f.resourceid = r.id and r.status='1' order by f.parent,f.orderno";
	      localObject = sess.createQuery(sql).list();
	    }
	    return ((List<Object>)PrivilegeUtil.getVOList((List)localObject, FunctionVO.class));
	}
	

	public void doGant(String value,String userid) throws RadowException, AppException{
		
//		String b0111 = this.getRadow_parent_data();
		String b0111 = this.getPageElement("subWinIdBussessId").getValue();
		HBSession sess = HBUtil.getHBSession();
		String hql = "From SmtUser S where S.id = '"+userid+"'";
		SmtUser user =(SmtUser) sess.createQuery(hql).list().get(0);

		if(value==null){
			deleteInfoGroupInfo("null",userid);
			return;
		}
		//先删除用户对应的信息记录
		String[] a0000s = value.split(",");
		
		//当操作类型为管理类别时，执行以下操作
			deleteInfoGroupInfo("null",userid);
			for(int i=0;i<a0000s.length;i++){
				String sql="select a1.a0000,a2.a0201b,count(distinct a2.a0201b) from A01 a1,A02 a2 where a1.a0000=a2.a0000 and a1.a0165 = '"+a0000s[i]+"' and a2.a0201b like '"+b0111+"%' group by a1.a0000, a2.a0201b";
/*				CommonQueryBS query_person = new CommonQueryBS();
				query_person.setConnection(HBUtil.getHBSession().connection());
				query_person.setQuerySQL(sql);
				Vector<?> vector_person = query_person.query();
				Iterator<?> iterator_person = vector_person.iterator();
				while(iterator_person.hasNext()){
					HashMap hashmap = (HashMap) iterator_person.next();
					RoleSaveUpdate(hashmap.get("a0000").toString(),b0111,userid);
				}*/
				List<Object[]> l = HBUtil.getHBSession().createSQLQuery(sql).list();
				for (Object[] objects : l) {
					RoleSaveUpdate(objects[0].toString(),objects[1].toString(),userid);
					RoleSaveUpdate(a0000s[i],objects[1].toString(),userid);
				}
				
			}
		
		try {
			new LogUtil().createLog("612", "COMPETENCE_USERPERSON",user.getId(),user.getLoginname(), "", new ArrayList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void deleteInfoGroupInfo(String a0000s,String userid){
		HBSession sess = HBUtil.getHBSession();
		String hql = "from UserPerson t where t.userid='"+userid+"' and t.a0000 not in("+a0000s+") " +
				" and exists (select 1 from A01 a where a.a0000 = t.a0000)";
		if(a0000s.equals("null")){
			hql = "from UserPerson t where t.userid='"+userid+"' and operateType = '1'";
		}
		List list = sess.createQuery(hql).list();
		for(int i=0;i<list.size();i++){
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			UserPerson igi = (UserPerson)list.get(i);
			sess.delete(igi);
			ts.commit();
		}
	}
	
	public void RoleSaveUpdate(String a0000,String b0101,String userid){
		HBSession sess = HBUtil.getHBSession();
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		String hql = "from UserPerson t where t.a0000='"+a0000+"' and t.userid='"+userid+"'  and operateType = '1'";
		List list = sess.createQuery(hql).list();
		if(list.size()==0){
			UserPerson ud = new UserPerson();
			ud.setA0000(a0000);
			ud.setUserid(userid);
			ud.setB0111(b0101);
			ud.setOPERATETYPE("1");
			sess.save(ud);
			sess.flush();
//			ts.commit();
		}
	}
	
	public int saveOnClick(String userid, String roleid) throws  RadowException{
		SceneVO	scene;
		try {
				scene = (SceneVO) PrivilegeManager.getInstance().getISceneControl().queryByName("sce", true).get(0);
		} catch (PrivilegeException e1) {
			this.setMainMessage(e1.getMessage());
			return EventRtnType.FAILD;
		}


						try {
							PrivilegeManager.getInstance().getIRoleControl().grant(userid, scene.getSceneid(), roleid, false);
						} catch (PrivilegeException e) {
							this.setMainMessage(e.getMessage());
							return EventRtnType.FAILD;
						}


		

		HBSession sess = HBUtil.getHBSession();
		String hql = "From SmtUser S where S.id = '"+userid+"'";
		SmtUser user =(SmtUser) sess.createQuery(hql).list().get(0);
		List list = new ArrayList();
		try {
			new LogUtil().createLog("611", "SMT_ACT",user.getId(),user.getLoginname(), "", list);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
//		this.setMainMessage("授权成功");
//		this.closeCueWindowByYes("win_pup");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("jy")
	@NoRequiredValidate
	public int jy() throws RadowException{		
		this.createPageElement("validity", ElementType.SELECT, false).setDisabled(true);		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("qy")
	@NoRequiredValidate
	public int qy() throws RadowException{		
		this.createPageElement("validity", ElementType.SELECT, false).setDisabled(false);		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
