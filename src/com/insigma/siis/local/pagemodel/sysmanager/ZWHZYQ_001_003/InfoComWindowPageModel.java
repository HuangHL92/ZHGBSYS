package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import net.sf.json.JSONArray;

import com.fr.fs.base.entity.User;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.util.ResourcesPermissionConst;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataCustomized;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.tree.TreeNode;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.InfoGroup;
import com.insigma.siis.local.business.entity.UserDept;
import com.insigma.siis.local.business.entity.UserInfoGroup;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class InfoComWindowPageModel extends PageModel {
	private List aclList = null;
	private static int index = 0;
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	@Override
	public int doInit() throws RadowException {
		
		//this.setNextEventName("deptGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String userid =this.getParameter("userid");
		StringBuffer jsonStr = new StringBuffer();
		String cueUsername = PrivilegeManager.getInstance().getCueLoginUser().getLoginname();
		jsonStr.append("[");//{task:'',duration:'',user:'',uiProvider:'col',cls:'master-task',iconCls:'task-folder',children:[
		HBSession sess = HBUtil.getHBSession();
		String hql = "From InfoGroup t where t.infogroupname<>'信息项组'";
		List list = sess.createQuery(hql).list();
		if(list.size()!=0){
			for(int i=0;i<list.size();i++){
				UserInfoGroup ud = new UserInfoGroup();
				InfoGroup uf = (InfoGroup)list.get(i);
				String hql2 = "from UserInfoGroup t where t.infogroupid='"+uf.getInfogroupid()+"' and t.userid='"+userid+"'";
				List list1 = sess.createQuery(hql2).list();
				String read = "";
				String write = "";
				String name =uf.getInfogroupname();
				 if(list1.size()==1){
					  ud = (UserInfoGroup)list1.get(0);
					  if(ud.getType().equals("1")){
						  read="checked";
						  write="checked";
					  }else if(ud.getType().equals("0")){
						  read="checked";
						  write="indeterminate";
					  }else{
						  read="indeterminate";
						  write="indeterminate";
					  }
				  }
				if(i==0){
					jsonStr.append("{task:'"+name+"',duration:'<input type=\"checkbox\" onclick=\"checkhorizon(this)\" "+read+" id=\""+uf.getInfogroupid()+"0"+"\"  name=\"KD\"/>',user:'<input type=\"checkbox\" onclick=\"checkhorizon(this)\" "+write+" id=\""+uf.getInfogroupid()+"1"+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
				}else{
					jsonStr.append(",{task:'"+name+"',duration:'<input type=\"checkbox\" onclick=\"checkhorizon(this)\" "+read+" id=\""+uf.getInfogroupid()+"0"+"\"  name=\"KD\"/>',user:'<input type=\"checkbox\" onclick=\"checkhorizon(this)\" "+write+" id=\""+uf.getInfogroupid()+"1"+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
				}
					
			}
		}
		jsonStr.append("]");//}]
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	/**
	 *  信息项权限组授权保存
	 * @param value
	 * @throws RadowException
	 */
	@PageEvent("dogrant")
	public void save(String value) throws RadowException {
		String userid = this.getRadow_parent_data();
		HBSession sess = HBUtil.getHBSession();
		String hql = "From SmtUser S where S.id = '"+userid+"'";
		SmtUser user =(SmtUser) sess.createQuery(hql).list().get(0);
		if(value==null){
			deleteUserInfoGroup("null",userid);
			List list = new ArrayList();
			try {
				new LogUtil().createLog("613", "COMPETENCE_USERINFOGROUP",user.getId(),user.getLoginname(), "", list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.setMainMessage("授权成功！");
			this.closeCueWindowByYes("win_pup");
			return;
		}
		String[] ids = value.split(",");
		String infogroupids = "";
		for(int i=0;i<ids.length;i++){
			String id = ids[i].substring(0,ids[i].length()-1);
			String type = ids[i].substring(ids[i].length()-1);
			saveUpdate(id,userid,type);
			infogroupids+="'"+id+"'"+",";
		}
		deleteUserInfoGroup(infogroupids,userid);
		List list = new ArrayList();
		try {
			new LogUtil().createLog("613", "COMPETENCE_USERINFOGROUP",user.getId(),user.getLoginname(), "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMainMessage("授权成功！");
		this.closeCueWindowByYes("win_pup");
	}
	/**
	 *  保存授权信息
	 * @param id
	 * @param userid
	 * @param type
	 */
	public void saveUpdate(String id,String userid,String type){
		HBSession sess = HBUtil.getHBSession();
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		String hql = "from UserInfoGroup t where t.infogroupid='"+id+"' and t.userid='"+userid+"'";
		List list = sess.createQuery(hql).list();
		if(list.size()==0){
			UserInfoGroup ud = new UserInfoGroup();
			ud.setInfogroupid(id);
			ud.setUserid(userid);
			ud.setType(type);
			sess.save(ud);
			ts.commit();
		}
		if(list.size()==1){
			UserInfoGroup ud = (UserInfoGroup)list.get(0);
			String tp = ud.getType();
//			if(type.equals("1")){
//				if(tp.equals("0")){
					ud.setType(type);
//				}
//			}
			sess.saveOrUpdate(ud);
			ts.commit();
		}
	}
	public void deleteUserInfoGroup(String infogroupids,String userid){
		infogroupids=infogroupids.substring(0,infogroupids.length()-1);
		HBSession sess = HBUtil.getHBSession();
		String hql = "from UserInfoGroup t where t.userid='"+userid+"' and t.infogroupid not in("+infogroupids+")";
		if(infogroupids.equals("nul")){
			hql = "from UserInfoGroup t where t.userid='"+userid+"'";
		}
		List list = sess.createQuery(hql).list();
		for(int i=0;i<list.size();i++){
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			UserInfoGroup ud = (UserInfoGroup)list.get(i);
			sess.delete(ud);
			ts.commit();
		}
	}
	
	private String chooseInfoGroupIds(String grid) throws RadowException{
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		String infogroupids = "";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(true)){
				String infogroupid = (String) this.getPageElement(grid).getValue("infogroupid", i);
				if(infogroupids.equals("")){
					infogroupids += infogroupid;
				}
				else{
					infogroupids += ","+infogroupid;
				}
			}
		}
		return infogroupids;
	}
	/*private String removeDeptIds(String grid) throws RadowException{
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		String b0111s = "";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(false)){
				String b0111 = (String) this.getPageElement(grid).getValue("b0111", i);
				if(b0111s.equals("")){
					b0111s += b0111;
				}
				else{
					b0111s += ","+b0111;
				}
			}
		}
		return b0111s;
	}
	*/
	
	public static String getInfoData(){
		String userid = SysManagerUtils.getUserId();
		//system 没有权限管理的问题   跳过
		if(userid.equals("40288103556cc97701556d629135000f")) {
			return "[]";
		}
		String sql = "SELECT d.infoid FROM competence_inf d WHERE d.INFOID NOT IN ( SELECT DISTINCT b.infoid FROM COMPETENCE_INFOGROUP a, COMPETENCE_INFOGROUPINFO b, COMPETENCE_INF c WHERE a.infogroupid = b.infogroupid AND c.infoid = b.infoid AND b.infogroupid IN ( SELECT t.infogroupid FROM COMPETENCE_USERINFOGROUP t WHERE t.type = '1' AND t.userid = '"+userid+"'))";
		/*String sql = "select distinct b.infoid " +
				"from COMPETENCE_INFOGROUP a,COMPETENCE_INFOGROUPINFO b ,COMPETENCE_INF c " +
				"where a.infogroupid=b.infogroupid and c.infoid=b.infoid " +
				"and a.infogroupid in (select t.infogroupid from COMPETENCE_USERINFOGROUP t where t.type='0' and t.userid=?) ";*/
				/*"union " +
				"select distinct b.infoid " +
				"from COMPETENCE_INFOGROUP a,COMPETENCE_INFOGROUPINFO b ,COMPETENCE_INF c " +
				"where a.infogroupid=b.infogroupid and c.infoid=b.infoid " +
				"and a.infogroupid not in (select t.infogroupid from COMPETENCE_USERINFOGROUP t where t.userid=?)";
				//"minus " +
		String sqlminus = "select distinct b.infoid from " +
				"COMPETENCE_INFOGROUP a,COMPETENCE_INFOGROUPINFO b ,COMPETENCE_INF c " +
				"where a.infogroupid=b.infogroupid and c.infoid=b.infoid and a.infogroupid " +
				"in (select t.infogroupid from COMPETENCE_USERINFOGROUP t where t.type='1' and " +
				"t.userid=?)"
				;*/
		
		/*//根据“权限项信息组控制”的设置，将所有信息组下面的信息项查询出来，查询出来的为  无权限  的信息项
		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).setString(0, userid).		
														setString(1, userid).list();
		
		//根据“权限项信息组设置”查询出所有当前用户  有权限  的信息项
		List<String> listminus = HBUtil.getHBSession().createSQLQuery(sqlminus).setString(0, userid).list();	
		
		
		//正常情况下，list中不会包含 listminus 中的信息项，程序还是需要处理一下，利用removeAll将list中包含的listminus中的元素去除
		list.removeAll(listminus);
		StringBuffer sb = new StringBuffer("[");
		
		//将 无权限  的信息项拼接为 string字符串，返回前台
		if(list!=null && list.size()>0){
			for(String o : list){
				sb.append("'"+o.toString().toLowerCase()+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			return sb.toString();
		}
		return "[]";*/
		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		StringBuffer sb = new StringBuffer("[");
		if(list!=null && list.size()>0){
			for(String o : list){
				sb.append("'"+o.toString().toLowerCase()+"',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			return sb.toString();
		}
		return "[]";
	}
	
	
}
