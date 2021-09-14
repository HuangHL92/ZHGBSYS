package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003;




import org.hibernate.Query;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.entity.SmtUserselfcolumn;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.MD5;
import com.insigma.siis.local.business.entity.Inf;
import com.insigma.siis.local.business.entity.InfoGroup;
import com.insigma.siis.local.business.entity.InfoGroupInfo;
import com.insigma.siis.local.business.entity.UserDept;
import com.insigma.siis.local.business.entity.UserPerson;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel;

public class SetInfoGroupWindowPageModel extends PageModel {
	
	/**
	 * 系统区域信息
	 */
	public  Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static int flag = 0;//用于分辨是点击的查询按钮，还是点击的用户组树
	private static int index = 0;
	public SetInfoGroupWindowPageModel(){
		try {
			HBSession sess = HBUtil.getHBSession();
			if("Smt_Group".equals(GlobalNames.sysConfig.get("GROUP"))){
				String areaname = (String) sess.createSQLQuery("SELECT a.NAME FROM SMT_GROUP a WHERE a.GROUPID='G001'").uniqueResult();
				areaInfo.put("areaname", areaname);
				areaInfo.put("areaid", "G001");
			}else{
				Object[] area = (Object[]) sess.createSQLQuery("SELECT b.infogroupname,a.AAA005 FROM AA01 a,COMPETENCE_INFOGROUP b WHERE a.AAA001='AREA_ID' and a.AAA005=b.infogroupid").uniqueResult();
				if(area==null){
					String areaname = (String) sess.createSQLQuery("SELECT a.NAME FROM SMT_GROUP a WHERE a.GROUPID='G001'").uniqueResult();
					areaInfo.put("areaname", areaname);
					areaInfo.put("areaid", "G001");
				}else{
					areaInfo.put("areaname", area[0]);
					areaInfo.put("areaid", area[1]);
				}
			}
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname=user.getLoginname();
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);
			if(groups.isEmpty() || issupermanager ||loginnname.equals("admin")){
				areaInfo.put("manager", "true");
			}else{
				areaInfo.put("manager", "false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PageEvent("optionGroup.onchange")
	public void showmessage(){
		this.setMainMessage("sdfasdfa");
	}

	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException {
		this.getPageElement("groupid").setValue(id);
		HBSession session = HBUtil.getHBSession();
		String hql = "from InfoGroupInfo t where t.infogroupid='"+id+"'";
		List list = session.createQuery(hql).list();
		String infoids = "";
		if(list.size()!=0){
			for(int i=0;i<list.size();i++){
				InfoGroupInfo igi = (InfoGroupInfo)list.get(i);
				infoids+=igi.getInfoid()+",";
			}
		}
		if(infoids.length()>0){
			infoids = infoids.substring(0,infoids.length()-1);
		}
		this.getExecuteSG().addExecuteCode("checked('"+infoids+"')");
		this.getPageElement("checkedgroupid").setValue(id);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		List<InfoGroup> list  = new ArrayList<InfoGroup>();
		String node = this.getParameter("node");
		HBSession sess = HBUtil.getHBSession();
		String hql = "FROM InfoGroup T where T.ordernum='"+node+"'  ORDER BY T.infogroupid";
		list = sess.createQuery(hql).list();
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (InfoGroup group : list) {
				if(i==0 && last==1) {
					jsonStr.append("[{\"text\" :\"" + group.getInfogroupname()
							+ "\" ,\"id\" :\"" + group.getInfogroupid()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getInfogroupid() + "')\"");
					jsonStr.append("}]");
				}else if (i == 0) {
					jsonStr.append("[{\"text\" :\"" + group.getInfogroupname()
							+ "\" ,\"id\" :\"" + group.getInfogroupid()
							+ "\" ,\"leaf\" :\"true\",\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getInfogroupid() + "')\"");
					jsonStr.append("}");
				}else if (i == (last - 1)) {
					jsonStr.append(",{\"text\" :\"" + group.getInfogroupname()
							+ "\" ,\"id\" :\"" + group.getInfogroupid()
							+ "\" ,\"leaf\" :\"true\",\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getInfogroupid() + "')\"");
					jsonStr.append("}]");
				} else {
					jsonStr.append(",{\"text\" :\"" + group.getInfogroupname()
							+ "\" ,\"id\" :\"" + group.getInfogroupid()
							+ "\",\"leaf\" :\"true\",\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getInfogroupid() + "')\"");
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
	@PageEvent("orgTreeGridJsonData")
	public int getOrgTreeGridJsonData() throws PrivilegeException {
		StringBuffer jsonStr = new StringBuffer();
		jsonStr.append("[{id:'1000001A',task:'人员基本信息集',duration:'',user:'',uiProvider:'col',cls:'master-task',iconCls:'task-folder',children:[");
		HBSession sess = HBUtil.getHBSession();
		String hql = "from Inf t";
		List list = sess.createQuery(hql).list();
		int count =0;
		if(list.size()!=0){
			for(int i=0;i<list.size();i++){
				Inf inf = (Inf)list.get(i);
				if("1".equals(inf.getInfotype())){
				String type="人员基本信息集";
				if(count==0){
					jsonStr.append("{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					count=1;
				}else{
					jsonStr.append(",{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
				}
				}
			}
			jsonStr.append("]}");
			count=0;
		}
		if(list.size()!=0){
			jsonStr.append(",{id:'1000002A',task:'职务信息集',duration:'',user:'',uiProvider:'col',cls:'master-task',iconCls:'task-folder',children:[");
			for(int i=0;i<list.size();i++){
				Inf inf = (Inf)list.get(i);
				if("2".equals(inf.getInfotype())){
				String type="职务信息集";
				if(count==0){
					jsonStr.append("{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					count=1;
				}else{
					jsonStr.append(",{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
				}
			}
			}
			jsonStr.append("]}");
			count=0;
		}
		if(list.size()!=0){
			jsonStr.append(",{id:'1000003A',task:'专业技术职务信息集',duration:'',user:'',uiProvider:'col',cls:'master-task',iconCls:'task-folder',children:[");
			for(int i=0;i<list.size();i++){
				Inf inf = (Inf)list.get(i);
				if("3".equals(inf.getInfotype())){
				String type="专业技术职务信息集";
				if(count==0){
					jsonStr.append("{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					count=1;
				}else{
					jsonStr.append(",{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
				}
			}
			}
			jsonStr.append("]}");
			count=0;
		}
		if(list.size()!=0){
			jsonStr.append(",{id:'1000004A',task:'学历学位信息集',duration:'',user:'',uiProvider:'col',cls:'master-task',iconCls:'task-folder',children:[");
			for(int i=0;i<list.size();i++){
				Inf inf = (Inf)list.get(i);
				if("4".equals(inf.getInfotype())){
				String type="学历学位信息集";
				if(count==0){
					jsonStr.append("{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					count=1;
				}else{
					jsonStr.append(",{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
				}
				}
			}
			jsonStr.append("]}");
			count=0;
		}

		if(list.size()!=0){
			jsonStr.append(",{id:'330701A',task:'年度考核信息集',duration:'',user:'',uiProvider:'col',cls:'master-task',iconCls:'task-folder',children:[");
			for(int i=0;i<list.size();i++){
				Inf inf = (Inf)list.get(i);
				if("6".equals(inf.getInfotype())){
				String type="年度考核信息集";
				if(count==0){
					jsonStr.append("{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					count=1;
				}else{
					jsonStr.append(",{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
				}
				}
			}
			jsonStr.append("]}");
			count=0;
		}
		if(list.size()!=0){
			jsonStr.append(",{id:'330702A',task:'家庭成员信息集',duration:'',user:'',uiProvider:'col',cls:'master-task',iconCls:'task-folder',children:[");
			for(int i=0;i<list.size();i++){
				Inf inf = (Inf)list.get(i);
				if("7".equals(inf.getInfotype())){
				String type="家庭成员信息集";
				if(count==0){
					jsonStr.append("{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					count=1;
				}else{
					jsonStr.append(",{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
				}
				}
			}
			jsonStr.append("]}");
			count=0;
		}
		if(list.size()!=0){
			jsonStr.append(",{id:'330703A',task:'单位信息集',duration:'',user:'',uiProvider:'col',cls:'master-task',iconCls:'task-folder',children:[");
			for(int i=0;i<list.size();i++){
				Inf inf = (Inf)list.get(i);
				if("8".equals(inf.getInfotype())){
				String type="单位信息集";
				if(count==0){
					jsonStr.append("{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					count=1;
				}else{
					jsonStr.append(",{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
				}
				}
			}
			jsonStr.append("]}");
			count=0;
		}
		if(list.size()!=0){
			jsonStr.append(",{id:'330704A',task:'奖惩信息集',duration:'',user:'',uiProvider:'col',cls:'master-task',iconCls:'task-folder',children:[");
			for(int i=0;i<list.size();i++){
				Inf inf = (Inf)list.get(i);
				if("5".equals(inf.getInfotype())){
				String type="奖惩信息集";
				if(count==0){
					jsonStr.append("{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					count=1;
				}else{
					jsonStr.append(",{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\" name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
				}
				}
			}
			jsonStr.append("]}");
			count=0;
		}
		if(list.size()!=0){
			jsonStr.append(",{id:'330705A',task:'补充信息集',duration:'',user:'',uiProvider:'col',cls:'master-task',iconCls:'task-folder',children:[");
			for(int i=0;i<list.size();i++){
				Inf inf = (Inf)list.get(i);
				if("9".equals(inf.getInfotype())){
				String type="补充信息集";
				if(count==0){
					jsonStr.append("{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\"  name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
					count=1;
				}else{
					jsonStr.append(",{task:'"+type+"',duration:'"+inf.getInfoname()+"',user:'<input type=\"checkbox\" onclick=\"clickCheck(this,1)\" id=\""+inf.getInfoid()+"\" name=\"KD\"/>',uiProvider:'col',leaf:true,iconCls:'task'}");
				}
				}
			}
			jsonStr.append("]}]");
			count=0;
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
	@PageEvent("CreateInfoGroupBtn.onclick")
	public int CreateInfoGroupBtn() throws RadowException{
		this.openWindow("CreateIGWin", "pages.sysmanager.ZWHZYQ_001_003.CreateInfoGroupWindow");
		this.setRadow_parent_data("1");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("DeleteInfoGroupBtn.onclick")
	public int DeleteInfoGroupBtn() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid.equals("")){
			this.setMainMessage("请选择需要删除的用户组");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//判断用户组是否为默认的用户组，默认用户组不能删除
		HBSession sess = HBUtil.getHBSession();
		String hql = "From InfoGroup S where S.infogroupid = '"+groupid+"' and S.infogroupname in ('三龄一历','其他信息','基本信息','拟任免')";
		List<InfoGroup> ig1 =sess.createQuery(hql).list();
		if(ig1.size()>0){
			this.setMainMessage("该信息组是系统默认，不能删除！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		this.addNextEvent(NextEventValue.YES, "doDeleteGroup",groupid);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
		this.setMainMessage("您确实要执行删除操作吗？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("doDeleteGroup")
	public int doDeleteGroup(String groupid) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String hql = "From InfoGroup S where S.infogroupid = '"+groupid+"'";
		InfoGroup ig1 =(InfoGroup) sess.createQuery(hql).list().get(0);
		/*Connection conn = sess.connection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("delete from InfoGroupInfo where InfoGroupID=?");
			pstmt.setString(1,groupid); 
			pstmt.executeUpdate();
			conn.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		InfoGroup ig = (InfoGroup)sess.createQuery("From InfoGroup t where t.infogroupid='"+groupid+"'").list().get(0);
		sess.delete(ig);
		ts.commit();
		List list = new ArrayList();
		try {
			new LogUtil().createLog("616", "COMPETENCE_INFOGROUP",ig1.getInfogroupid(),ig1.getInfogroupname(), "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMainMessage("删除信息项权限组组成功");
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("ModifyInfoGroupBtn.onclick")
	public int ModifyInfoGroupBtn() throws RadowException{
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid==null || "".equals(groupid)) {
			this.setMainMessage("请选择一个信息项组。");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//判断用户组是否为默认的用户组，默认用户组不能修改
		HBSession sess = HBUtil.getHBSession();
		String hql = "From InfoGroup S where S.infogroupid = '"+groupid+"' and S.infogroupname in ('三龄一历','其他信息','基本信息','拟任免')";
		List<InfoGroup> ig1 =sess.createQuery(hql).list();
		if(ig1.size()>0){
			this.setMainMessage("该信息组是系统默认，不能修改！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.openWindow("CreateIGWin", "pages.sysmanager.ZWHZYQ_001_003.CreateInfoGroupWindow");
		this.setRadow_parent_data(groupid);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("dogrant")
	public void save(String value) throws RadowException{
		String id = this.getPageElement("checkedgroupid").getValue();
		HBSession sess = HBUtil.getHBSession();
		if("".equals(id)||id==null){
			this.setMainMessage("您好，请选择需要操作的信息项权限组");
			return;
		}
		String hql = "From InfoGroup S where S.infogroupid = '"+id+"'";
		InfoGroup ig1 =(InfoGroup) sess.createQuery(hql).list().get(0);
		if(value==null){
			deleteInfoGroupInfo("null",id);
			List list = new ArrayList();
			try {
				new LogUtil().createLog("617", "COMPETENCE_INFOGROUPINFO",ig1.getInfogroupid(),ig1.getInfogroupname(), "", list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.setMainMessage("授权成功！");
			this.closeCueWindowByYes("setInfoGroupWin");
			return;
		}
		String[] infoids = value.split(",");
		String infoids2 = "";
		for(int i=0;i<infoids.length;i++){
			saveUpdate(infoids[i],id);
			infoids2+="'"+infoids[i]+"'"+",";
		}
		deleteInfoGroupInfo(infoids2,id);
		List list = new ArrayList();
		try {
			new LogUtil().createLog("617", "COMPETENCE_INFOGROUPINFO",ig1.getInfogroupid(),ig1.getInfogroupname(), "", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMainMessage("授权成功！");
		this.closeCueWindowByYes("setInfoGroupWin");
	}
	
	public void saveUpdate(String infoid,String infogroupid){
		HBSession sess = HBUtil.getHBSession();
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		String hql = "from InfoGroupInfo t where t.infoid='"+infoid+"' and t.infogroupid='"+infogroupid+"'";
		List list = sess.createQuery(hql).list();
		if(list.size()==0){
			InfoGroupInfo ud = new InfoGroupInfo();
			ud.setInfoid(infoid);
			ud.setInfogroupid(infogroupid);
			sess.save(ud);
			ts.commit();
		}
	}
	
	public void deleteInfoGroupInfo(String infoid,String infogroupid){
		infoid=infoid.substring(0,infoid.length()-1);
		HBSession sess = HBUtil.getHBSession();
		String hql = "from InfoGroupInfo t where t.infogroupid='"+infogroupid+"' and t.infoid not in("+infoid+")";
		if(infoid.equals("nul")){
			hql = "from InfoGroupInfo t where t.infogroupid='"+infogroupid+"'";
		}
		List list = sess.createQuery(hql).list();
		for(int i=0;i<list.size();i++){
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			InfoGroupInfo igi = (InfoGroupInfo)list.get(i);
			sess.delete(igi);
			ts.commit();
		}
	}
	
	
	public boolean findInfoGroupId(String infoid,String id){
		HBSession sess = HBUtil.getHBSession();
		Object infogroupid = sess.createSQLQuery("SELECT a.infogroupid FROM COMPETENCE_INFOGROUPINFO a WHERE a.infoid='"+infoid+"' and a.infogroupid='"+id+"'").uniqueResult();
		if(infogroupid==null){
			return false;
		}
		return true;
	}
	
	private int choosePerson(String grid, boolean isRowNum) throws RadowException{
		int result = 0;
		int number = 0;
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(true)){
				number = i;
				result++;
			}
		}
		if(isRowNum){
			return number;//选中的第几个
		}
		return result;//选中用户个数
	}
	
	/*private String chooseInfoIds(String grid) throws RadowException{
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		String infoids = "";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("logchecked");
			if(logchecked.equals(true)){
				String infoid = (String) this.getPageElement(grid).getValue("infoid", i);
				if(infoids.equals("")){
					infoids += infoid;
				}
				else{
					infoids += ","+infoid;
				}
			}
		}
		return infoids;
	}*/
	public boolean isLeader(){
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String isleader = user.getIsleader();
		return "0".equals(isleader);	
	}
	
	
	
	@Override
	public int doInit() {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
