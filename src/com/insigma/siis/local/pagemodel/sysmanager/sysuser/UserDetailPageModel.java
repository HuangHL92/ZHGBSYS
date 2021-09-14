package com.insigma.siis.local.pagemodel.sysmanager.sysuser;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
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
import com.insigma.odin.framework.privilege.vo.RoleVO;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.InfoGroup;
import com.insigma.siis.local.business.entity.UserInfoGroup;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.epsoft.util.Node;
import com.insigma.siis.local.epsoft.util.NodeUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class UserDetailPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		/*绿色的分割线---------------------------------------------------------------------------------------------------------*/
		//the first step to load the page,query base information from table smt_user
		/*绿色的分割线---------------------------------------------------------------------------------------------------------*/
		HBSession sess = HBUtil.getHBSession();
		String data= this.getPageElement("subWinIdBussessId").getValue();
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
		//this.getPageElement("usertype").setValue(user.getUsertype());
		
		this.getPageElement("isleader").setValue(user.getIsleader());

//		this.getPageElement("createdate").setValue(DateUtil.dateToString(user.getCreatedate(), "yyyymmdd"));
		
		this.getPageElement("useful").setValue(user.getStatus());
		this.getPageElement("loginname").setValue(user.getLoginname());
		this.getPageElement("work").setValue(user.getWork());
		this.getPageElement("mobile").setValue(user.getMobile());
		this.getPageElement("tel").setValue(user.getTel());
		this.getPageElement("email").setValue(user.getEmail());
		Object owner = HBUtil.getHBSession().createSQLQuery("select username from smt_user where userid='"+user.getOwnerId()+"'").uniqueResult();
		if(null == owner){
			owner = "历史用户";
		}
		this.getPageElement("owner").setValue(owner.toString());
		if(user.getOtherinfo()!=null && !user.getOtherinfo().equals("X001")){
			String hql = "from B01 t where t.b0111='"+user.getOtherinfo()+"'";
			List list = sess.createQuery(hql).list();
			if(list.size()!=0){
				B01 b = (B01)list.get(0);
				this.getPageElement("ssjg").setValue(b.getB0101());
			}
		}else{
			this.getPageElement("ssjg").setValue("无管理单位");
		}
		
		/*绿色的分割线---------------------------------------------------------------------------------------------------------*/	
		//the second step,get the organic chechekd
		/*绿色的分割线---------------------------------------------------------------------------------------------------------*/
		this.setNextEventName("rolegrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	/**
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	
	@PageEvent("rolegrid.dogridquery")
	public int usergridDoQuery(int start, int limit) throws RadowException {
			String userid = this.getPageElement("subWinIdBussessId").getValue();
			String type="";
			try {
				UserVO user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(userid);
				 type = user.getUsertype();
			} catch (PrivilegeException e) {
				e.printStackTrace();
			}
			
			String sql = "select sr.roleid,"+
					 "sr.roledesc,"+
					 "sr.owner,"+
					 "sr.rolename,"+
					 "sr.status status,"
					+ "case (select 1 from Smt_Act sa where sa.roleid=sr.roleid and objectid='"+userid+"') when 1 then 'true' else 'false'  end  rolecheck from smt_role sr where sr.hostsys='"+type+"'";
			this.pageQuery(sql,"SQL", start, limit);

		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("saveUser")
	public int saveUser(String ids) throws RadowException{
		this.getExecuteSG().addExecuteCode("Ext.get(document.body).mask('请稍等...',odin.msgCls)");
		String cueUser = PrivilegeManager.getInstance().getCueLoginUser().getName();
		String cueUserID = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String data= this.getPageElement("subWinIdBussessId").getValue(); //人员id
		Date createdate = new Date();
		UserVO user = new UserVO();
		try {
			user = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(data);
		} catch (PrivilegeException e2) {
			e2.printStackTrace();
		}
//		user.setCreatedate(createdate);
//		user.setLoginname(this.getPageElement("loginname").getValue());
		user.setStatus(this.getPageElement("useful").getValue());
		user.setName(this.getPageElement("username").getValue());
//		user.setPasswd(this.getPageElement("password").getValue());
		String type = "0";

		String ssjg = this.getPageElement("ssjg").getValue().equals("无管理单位用户") ? "X001" : this.getPageElement("ssjg").getValue();
		String work = this.getPageElement("work").getValue();
		String mobile = this.getPageElement("mobile").getValue();
		String tel = this.getPageElement("tel").getValue();
		String email = this.getPageElement("email").getValue();
		user.setIsleader(type);
		if("0".equals(type)){
			type="2";
		}
//		user.setUsertype(type);
		user.setEmpid(cueUser);
		
		user.setWork(work);
		user.setMobile(mobile);
		user.setTel(tel);
		user.setEmail(email);
//		String groupid = this.getRadow_parent_data();
//		String groupid =this.getPageElement("subWinIdBussessId").getValue();
		//保存用户所属机构
		if(user.getOtherinfo().equals("X001")){
			user.setOtherinfo(ssjg);
		}		
		Long sortID = getSortid(cueUserID,"330700");
		user.setSortid(sortID);
		String userid = "";
		
		//解析勾选的管理类别
		String rate = "";
		String empid = "";
//		Set<String> result = new HashSet<String>();//去掉差集只要勾选的全都存
		Set<String> llset = new HashSet<String>();
		Set<String> whset = new HashSet<String>();
		String ids2 = this.getPageElement("ryIds").getValue();
		String[] a0165s = ids2.substring(0,ids2.length()-1).split(",");
		for (int i = 0; i < a0165s.length; i++) {
			String value1 = a0165s[i].split(":")[0];//code type
			String hz = value1.substring(value1.length()-1); //end of value1 0:liulan 1:weihu			
			String value2 = a0165s[i].split(":")[1];//checked
			if("false".equals(value2)){
				if("0".equals(hz)){
//					rate += value1.substring(0, value1.length()-1) + ",";
					llset.add(value1.substring(0, value1.length()-1));
				}else{
//					empid += value1.substring(0, value1.length()-1) + ",";
					whset.add(value1.substring(0, value1.length()-1));
				}
			}
		}
//		result.addAll(whset);
//		result.removeAll(llset);
		rate = StringUtils.join(llset.toArray(),",");
		empid = StringUtils.join(whset.toArray(),",");//差集
		user.setRate(null == rate || "".equals(rate) ? "" : "'"+rate.replace(",", "','")+"'");
		user.setEmpid(null == empid || "".equals(empid) ? "" : "'"+empid.replace(",", "','")+"'");
		//创建存放旧数据的对象
		UserVO user1 = new UserVO();
		try {

		//拷备数据
		PropertyUtils.copyProperties(user1, user);
		PrivilegeManager.getInstance().getIUserControl().updateUser(user);
		List list = PrivilegeManager.getInstance().getIUserControl().queryByName(user.getLoginname(), true);
		userid = ((UserVO)list.get(0)).getId();
		
			//角色模块授权 如果是自定义角色，新建
			String roleType = "1";
			if(roleType.equals("2")){
				//new role name
				String ids1 = this.getPageElement("changeNode").getValue();
				String role_name = this.getPageElement("roleName").getValue();
				if((role_name==null||role_name.equals(""))||ids1==null||ids1.equals("")) {
					this.setMainMessage("请填写角色名称，并勾选功能权限。");
					this.getExecuteSG().addExecuteCode("Ext.get(document.body).unmask();");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}else if("1".equals(roleType)){
				PageElement pe = this.getPageElement("grid6");
				if(pe!=null){
					List<HashMap<String, Object>> rlist = pe.getValueList();
					int k=0;
					for(int j=0;j<rlist.size();j++){
						HashMap<String, Object> map = rlist.get(j);
						Object usercheck = map.get("checked");
						if(usercheck.equals(true)){
							k++;
						}
					}
					if(k==0) {
						this.setMainMessage("您尚未选择角色。");
						this.getExecuteSG().addExecuteCode("Ext.get(document.body).unmask();");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			

			this.getExecuteSG().addExecuteCode("realParent.odin.ext.getCmp('memberGrid').store.reload();");

			
			
			//新增用户时，默认给用户赋所有信息项权限组的权限  
			List<InfoGroup> inflist = HBUtil.getHBSession().createQuery("from InfoGroup where ordernum is not null").list();
			for(int i=0;i<inflist.size();i++){
				UserInfoGroup uig = new UserInfoGroup();
				InfoGroup ig = inflist.get(i);
				uig.setType("1");
				uig.setUserid(userid);
				
				uig.setInfogroupid(ig.getInfogroupid());
				HBUtil.getHBSession().save(uig);
				HBUtil.getHBSession().flush();
			}
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			// 机构授权  保存管理层次条件
			doOrgGrant(ids, userid,ts);
			
			//角色模块授权 如果是自定义角色，新建
//			String roleType = this.getPageElement("roleSelect").getValue();
			if(roleType.equals("2")){
				//new role name
				SmtRole role = new SmtRole();
				String role_name = this.getPageElement("roleName").getValue();
				role.setDesc(role_name);
				role.setName(user.getName());
				String hostsys = "1";
				Long sortID1 = getSortid();
				role.setSortid(sortID1);
				role.setStatus("1");
				role.setHostsys(hostsys);
				role.setOwner(SysUtil.getCacheCurrentUser().getId());
				HBUtil.getHBSession().save(role);	
				HBUtil.getHBSession().flush();
				
				try {
					new LogUtil().createLog("618", "SMT_ROLE",role.getId(),role.getName(), "", new ArrayList());
				} catch (Exception e) {
					e.printStackTrace();
				}
				//授权 1.为角色授权 2.为用户授角色权限				
				String ids1 = this.getPageElement("changeNode").getValue();
				save(ids1,role.getId());
				saveOnClick(userid,role.getId());
			}else if("1".equals(roleType)){
				//roleid 4028c60c25335f86012533767f3c0002 system
//				saveOnClick(userid,"4028c60c25335f86012533767f3c0002");
				SceneVO scene = null;
				try {
					scene = (SceneVO) PrivilegeManager.getInstance().getISceneControl().queryByName("sce", true).get(0);
				} catch (PrivilegeException e1) {
					this.setMainMessage(e1.getMessage());
					return EventRtnType.FAILD;
				}
				PageElement pe = this.getPageElement("grid6");
				if(pe!=null){
					List<HashMap<String, Object>> rlist = pe.getValueList();
					int k=0;
					for(int j=0;j<rlist.size();j++){
						
						HashMap<String, Object> map = rlist.get(j);
						Object usercheck = map.get("checked");
						if(usercheck.equals(true)){
							k++;
							String roleid=	(String) this.getPageElement("grid6").getValue("roleid", j);
							boolean isCanDespacth = true;
//							String id = ids.split(",");
							
								try {
									PrivilegeManager.getInstance().getIRoleControl().grant(userid, scene.getSceneid(), roleid, isCanDespacth);
								} catch (PrivilegeException e) {
									e.printStackTrace();
								}
							
						}
					}
					if(k==0) {
						this.setMainMessage("您尚未选择角色。");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				
				try {
					new LogUtil().createLog("611", "SMT_ACT",user.getId(),user.getLoginname(), "", new ArrayList());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}/*else if("0".equals(roleType)){
				//roleid 402881e456498d9601564a2ccde004c0 normal
				saveOnClick(userid,"402881e456498d9601564a2ccde004c0");
			}*/
//			ts.commit();
			//人员查看权限
/*			String ids2 = this.getPageElement("ryIds").getValue();
			doGant(ids2, userid);*/
			ts.commit();
		} catch (PrivilegeException e) {
//			throw new RadowException(e.getMessage());
			e.printStackTrace();
		} /*catch (AppException e) {
			throw new RadowException(e.getMessage());
		}*/ catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InvocationTargetException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (NoSuchMethodException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		/*if(acredit.equals("yes")) doAcredit(userid);*/
		try {
			new LogUtil().createLog("65", "SMT_USER",user.getId(), user.getLoginname(), "", new Map2Temp().getLogInfo(user1,user));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("Ext.getBody().unmask();");
//		this.setMainMessage("保存成功");
//		this.closeCueWindowByYes("UserDetailWin");
		this.getExecuteSG().addExecuteCode("Ext.MessageBox.alert('消息提示', '保存成功', function(e){ if ('ok' == e){parent.Ext.getCmp('UserDetailWin').close();}});");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 *  获取用户的排序编号
	 * @param parentid
	 * @return
	 */
	public Long getSortid(){
		
		String sql = "select max(case when t.sortid is null then 0 else t.sortid end)+1 sortid from smt_role t";
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
}
