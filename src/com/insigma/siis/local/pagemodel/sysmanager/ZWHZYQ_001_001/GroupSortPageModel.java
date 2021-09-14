package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.B01sort;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.business.sysorg.org.dto.B01DTO;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class GroupSortPageModel extends PageModel{

	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String tag= "0";//修改动作0未执行1已执行
	public static String updateTag= "0";//修改动作0未执行1已执行
	public GroupSortPageModel(){
		try {
			HBSession sess = HBUtil.getHBSession();
			if("Smt_Group".equals(GlobalNames.sysConfig.get("GROUP"))){
				String areaname = (String) sess.createSQLQuery("SELECT a.NAME FROM SMT_GROUP a WHERE a.GROUPID='G001'").uniqueResult();
				areaInfo.put("areaname", areaname);
				areaInfo.put("areaid", "G001");
			}else{
				Object[] area = (Object[]) sess.createSQLQuery("SELECT b.name,a.AAA005 FROM AA01 a,SMT_GROUP b WHERE a.AAA001='AREA_ID' and a.AAA005=b.groupid").uniqueResult();
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
	
	@Override
	public int doInit() throws RadowException {
		this.updateTag="0";
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("orgTreeJsonDataOpenTree")
	public int orgTreeJsonDataLeftTree() throws PrivilegeException {
		String jsonStr = getJson("");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	public String getJson(String nodeother){
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String node="";
		String sql1 ="";
		if("".equals(nodeother)){
			node = this.getParameter("node");
		}else{
			node = nodeother;
		}
		int nodelength = 0;
		if(node.equals("-1")){
			sql1 = " and sg.GROUPID in (select sug.GROUPID from SMT_USERGROUPREF sug where sug.USERID = '"+cueUserid+"')";
		}else{
			sql1 = " and sg.groupid in (select distinct groupid from smt_group where parentid = '"+node+"')";
		}
		String sql ="select sg.GROUPID,sg.PARENTID,sg.NAME,sg.OWNER,sg.STATUS,sg.HASHCODE,sg.Rate from SMT_GROUP sg where 1=1 "+sql1 +" order by sg.Rate";
		
		CommonQueryBS.systemOut(sql);
		List<HashMap> list =null;
		try {
			list = CommonQueryBS.getQueryInfoByManulSQL(sql);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//得到当前组织信息
//		List<B01> list = null;//得到当前组织信息
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (HashMap group : list) {
				if(i==0 && last==1) {
					jsonStr.append("[");
					appendjson(group, jsonStr);
					jsonStr.append("]");
				}else if (i == 0) {
					jsonStr.append("[");
					appendjson(group, jsonStr);
				}else if (i == (last - 1)) {
					jsonStr.append(",");
					appendjson(group, jsonStr);
					jsonStr.append("]");
				} else {
					jsonStr.append(",");
					appendjson(group, jsonStr);
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		return jsonStr.toString();
		
	}
	
	private String appendjson(HashMap map,StringBuffer sb_tree){
		String icon ="";
		sb_tree.append(" {text: '"+map.get("name")+"',id:'"+map.get("groupid")+"',leaf:"+hasChildren((String)map.get("groupid"))+",tag:'"+""+"'}");
		return sb_tree.toString();
	}
	
	private String hasChildren(String id){
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String sql="select groupid from smt_group where parentid = '"+id+"'";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if(list!=null && list.size()>0){
			return "false";
		}else{
			return "true";
		}
	}
	
	@PageEvent("closeBtn.onclick")
	public int close() throws AppException, RadowException {
		if(this.request.getSession().getAttribute("tag").equals("1")){
			this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
			this.closeCueWindow("groupSortWin");
		}else{
			this.closeCueWindow("groupSortWin");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("YesNew")
	@OpLog
	@Transaction
	public int YesNew(String ids) throws AppException, RadowException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SQLException, IntrospectionException {
		Session sess = HBUtil.getHBSession().getSession();
		CurrentUser user = SysUtil.getCacheCurrentUser();
		ids =ids.substring(0,ids.length()-1);
		String[] arr = ids.split(",");
		B01DTO b01DTOb01ChooseOrgCod =new B01DTO();
		for(int i=0;i<arr.length;i++){
			SmtGroup b01 = (SmtGroup) sess.createQuery("From SmtGroup S where S.id = '"+arr[i]+"'").list().get(0);
			PropertyUtils.copyProperties(b01DTOb01ChooseOrgCod, b01);
			b01.setRate(String.valueOf(i+1));
			sess.update(b01);
			//new LogUtil().createLog("24", "B01", b01.getB0111(), b01.getB0101(), "机构排序", new Map2Temp().getLogInfo(b01DTOb01ChooseOrgCod, b01));
		}
		sess.flush();
		this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
		this.closeCueWindow("groupSortWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
