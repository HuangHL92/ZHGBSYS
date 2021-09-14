package com.insigma.siis.local.pagemodel.sysorg.org;


import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.sysorg.org.OrgNodeTree;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

import net.sf.json.JSONObject;

public class SysOrgPageModel extends PageModel {
	
	/**
	 * 系统区域信息
	 */
	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String queryType="0";//1点击机构树查询2点击按钮查询
	public static String tag="0";//0未执行1执行
	protected static final String exportFirst="1";
	protected static final String exportSencond="2";
	protected static final String exportThree="3";
	protected static final String exportFour="4";
	protected static final String exportFive="5";
	
	public SysOrgPageModel(){
		try {
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname=user.getLoginname();
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);
			
			HBSession sess = HBUtil.getHBSession();
			Object[] area = null;
			if(groups.isEmpty() || issupermanager ||loginnname.equals("admin")){
				area = SysOrgBS.queryInit();
				areaInfo.put("manager", "true");
			}else{
				area =  SysOrgBS.queryInit();
				areaInfo.put("manager", "false");
			}
			if(area!=null ) { 
				if(area[2].equals("1")){
					area[2]="picOrg";
				}else if(area[2].equals("2")){
					area[2]="picInnerOrg";
				}else{
					area[2]="picGroupOrg";
				}
				areaInfo.put("areaname", area[0]);
				areaInfo.put("areaid", area[1]);
				areaInfo.put("picType", area[2]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//页面初始化
	@Override
	public int doInit() {
		this.getExecuteSG().addExecuteCode("odin.ext.getCmp('memberGrid').view.refresh();");
		this.getExecuteSG().addExecuteCode("styleInit();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("getCheckList")
	public int getCheckList() throws RadowException, AppException{
		String listString=null;
		int cnt=0;
		List<HashMap<String, Object>> gdlist=new ArrayList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("memberGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			
			if(!"".equals(hm.get("orgcheck"))&&(Boolean) hm.get("orgcheck")){
				listString=listString+"@|"+hm.get("b0111x")+"|";
				++cnt;
			}
		}
		if(!"".equals(listString)&&listString!=null)
		    listString=listString.substring(listString.indexOf("@")+1,listString.length());
 		this.getPageElement("checkList").setValue(listString
 				);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("exportAll2.onclick")
	public int exportAll2(){
		this.setSelfResponseFunc("xianyin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("betchModifyBtn.onclick")
	@GridDataRange
	public int betchModifyBtn() throws RadowException{
		String orgids = this.getPageElement("checkList").getValue();
		if("".equals(orgids)){
			this.setMainMessage("请勾选要修改的机构");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.request.getSession().setAttribute("orgids", orgids);
		this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.SysbetchModify','批量修改',850,450,document.getElementById('checkList').value,ctxPath)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//点击树查询事件
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException {
//		try {
//			selectCountById(id);
//			String sql="from B01 where B0111='"+id+"'";
//			List list = HBUtil.getHBSession().createQuery(sql).list();
//			B01 b01 =(B01) list.get(0);
//			if(b01 == null){
//				throw new RadowException("查询错误");
//			}
//			this.getPageElement("optionGroup").setValue(b01.getB0101());
//		} catch (Exception e) {
//			throw new RadowException(e.getMessage());
//		}
		this.getPageElement("checkedgroupid").setValue(id);
		//设置查询方式为左边树
		this.request.getSession().setAttribute("queryType", "1");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("conditionPanel_panel.onclick")
	public int conditionPanel_panel(){
		this.setMainMessage("22");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//刷新列表
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		List alist = new ArrayList();
		String sql="";
		queryType=(String) this.request.getSession().getAttribute("queryType");
		if(queryType.equals("1")){
			String groupid = this.getPageElement("checkedgroupid").getValue();
			String sb="select B01.*,B01.b0111 b0111x from B01 where B0121='"+groupid+"' order by sortid";
			this.pageQuery(sb, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		}else if(queryType.equals("2")){
			//String b0111= "-1";
			String SysOrgTreeIds = this.getPageElement("SysOrgTreeIds").getValue();
			String b0101 = this.getPageElement("b0101name").getValue();
			String b0127 = this.getPageElement("b0127").getValue();
			String b0131 = this.getPageElement("b0131").getValue();
			String b0124 = this.getPageElement("b0124").getValue();
			String b0117 = this.getPageElement("b0117").getValue();
			String b0194 = this.getPageElement("b0194_combo").getValue();
			if(b0194.contains("法人单位")&&b0194.contains("内设机构")&&!b0194.contains("机构分组")){
				b0194="'1','2'";
			}else if(b0194.contains("法人单位")&&!b0194.contains("内设机构")&&b0194.contains("机构分组")){
				b0194="'1','3'";
			}else if(!b0194.contains("法人单位")&&b0194.contains("内设机构")&&b0194.contains("机构分组")){
				b0194="'2','3'";
			}else if(b0194.contains("法人单位")){
				b0194="'1'";
			}else if(b0194.contains("内设机构")){
				b0194="'2'";
			}else if(b0194.contains("机构分组")){
				b0194="'3'";
			}
			StringBuffer sb = new StringBuffer("");
			String strcon="";
			sb.append("select B01.*,B01.b0111 b0111x from B01 where 1=1 and b0111!='-1'");
			if(SysOrgTreeIds!=null&&!SysOrgTreeIds.equals("")&&!"{}".equals(SysOrgTreeIds)){
				//选择机构
				JSONObject jsonObject = JSONObject.fromObject(SysOrgTreeIds);
				sb.append(" and (1=2 ");
				Iterator<String> it = jsonObject.keys();
				// 遍历jsonObject数据，添加到Map对象
				while (it.hasNext()) {
					String nodeid = it.next(); 
					String operators = (String) jsonObject.get(nodeid);
					String[] types = operators.split(":");
					if("true".equals(types[1])&&"true".equals(types[2])){
						sb.append(" or b0111 like '"+nodeid+"%' ");
					}else if("true".equals(types[1])&&"false".equals(types[2])){
						sb.append(" or b0111 like '"+nodeid+".%' ");
					}else if("false".equals(types[1])&&"true".equals(types[2])){
						sb.append(" or b0111 = '"+nodeid+"' ");
					}
				}
				sb.append(" ) ");
				/*sb.append(" and ");
				String reSysOrgTreeIds =SysOrgTreeIds.replace("|", "'");
				reSysOrgTreeIds= reSysOrgTreeIds.replace("@", ",");
				String[] a =reSysOrgTreeIds.split(",");
				if(a.length>999){
					StringBuffer sqlString = new StringBuffer();
					for (int i = 0; i < a.length; i++) {  
		                if (i == (a.length - 1)) {  
		                    sqlString.append(a[i]); //SQL拼装，最后一条不加“,”。  
		                }else if((i%999)==0 && i>0){  
		                    sqlString.append(a[i]).append(") or b0111 in ("); //解决ORA-01795问题  
		                }else{  
		                    sqlString.append(a[i]).append(",");  
		                }  
		            } 
					sb.append("b0111 in ("+sqlString+")");
				}else{
					sb.append("b0111 in ("+reSysOrgTreeIds+")");
				}*/
			}
			if (!b0101.equals("")){
				strcon = "b0101 like '%" + b0101 +"%'";
				sb.append(" and ");
				sb.append(strcon);
			}
			if (!b0127.equals("")){
				sb.append(" and ");
				sb.append("b0127='"+b0127+"'");
			}
			if (!b0131.equals("")){
				sb.append(" and ");
				sb.append("b0131='"+b0131+"'");
			}
			if (!b0124.equals("")){
				sb.append(" and ");
				sb.append("b0124='"+b0124+"'");
			}
			if (!b0117.equals("")){
				sb.append(" and ");
				sb.append("b0117='"+b0117+"'");
			}
			if(!b0194.equals("")){
				if(b0194.contains("法人单位")&&b0194.contains("内设机构")&&b0194.contains("机构分组")){
				}else if(b0194.contains("请")){
				}else{
					sb.append(" and ");
					sb.append("b0194 in ("+b0194+")");	
				}
			}
			sb.append(" order by b0111");
			//sb.append(" start with b0111 = '"+b0111+"' connect by b0121 = prior b0111");
			this.pageQuery(sb.toString(), "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		}else if(queryType.equals("3")){
			String sb="select B01.*,B01.b0111 b0111x from B01 where b0111 <>'-1' order by b0111";
			this.pageQuery(sb, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		}else if(queryType.equals("4")){
			String groupid = this.getPageElement("checkedgroupid").getValue();
			String sb="select B01.*,B01.b0111 b0111x from B01 where B0111  like '%"+groupid+"%' order by b0111";
			this.pageQuery(sb, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		}else if(queryType.equals("5")){
			String ids =(String) this.request.getSession().getAttribute("queryids");
			String sb="select B01.*,B01.b0111 b0111x from B01 where B0111  in ("+ids+") order by b0111";
			this.pageQuery(sb, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		}
		this.createPageElement("memberGrid", ElementType.GRID, false).setValueList(null);
		if(alist == null || alist.isEmpty()){
			this.setSelfDefResData(this.getPageQueryData(new ArrayList(), start, limit));
			return EventRtnType.SPE_SUCCESS;
		}
		this.setSelfDefResData(this.getPageQueryData(alist, start, limit));
		return EventRtnType.SPE_SUCCESS;
	}

//	//初始化组织机构树
//	@PageEvent("orgTreeJsonData")
//	public int getOrgTreeJsonData() throws PrivilegeException {
//		String node = this.getParameter("node");
//		String sql="from B01 where B0121='"+node+"' order by sortid";
//		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();//得到当前组织信息
//		//只显示所在的组织及下级组织 不在组织中 则显示全部
//		List<B01> choose = new ArrayList<B01>();
//		String sql2="from B01 where B0111='"+node+"'";
//		List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();//得到当前组织信息
//		for(int i=0;i<groups.size();i++){
//			for(int j=0;j<groups.size();j++){
//				if(groups.get(j).getB0111().equals(groups.get(i).getB0121())){
//					groups.remove(i);
//					i--;
//				}
//			}
//		}
//		boolean equel = false;
//		if(!groups.isEmpty()){
//			for(int i = 0;i<list.size();i++){
//				for(int j = 0;j<groups.size();j++){
//					if(groups.get(j).getB0111().equals(list.get(i).getB0111())){
//						choose.add(groups.get(j));
//						equel = true;
//					}
//				}
//			}
//		}
//		if(equel){
//			list = choose;
//		}
//		StringBuffer jsonStr = new StringBuffer();
//		String companyOrgImg = "/insiis6/pages/sysorg/org/images/companyOrgImg2.png";
//		String insideOrgImg = "/insiis6/pages/sysorg/org/images/insideOrgImg1.png";
//		String groupOrgImg = "/insiis6/pages/sysorg/org/images/groupOrgImg1.png";
//		String wrongImg = "/insiis6/pages/sysorg/org/images/wrong.gif";
//		String path = companyOrgImg;
//		
//		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
//		String cueUserid = user.getId();
//		String loginnname=user.getLoginname();
//		List<GroupVO> groups1 = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
//		UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
//		boolean issupermanager=new DefaultPermission().isSuperManager(vo);
//		List<String> b0111s =new ArrayList();
//		boolean isadmin =false;
//		if(groups1.isEmpty() || issupermanager ||loginnname.equals("admin")){
//			isadmin =true;
//		}else{
//			b0111s=authority(cueUserid);
//		}
//
//		if (list != null && !list.isEmpty()) {
//			int i = 0;
//			int last = list.size();
//			for (B01 group : list) {
//				Boolean own = isown(b0111s,group.getB0111(),isadmin);
//				if(i==0 && last==1) {
//					if(group.getB0194().equals("2")){
//						path=insideOrgImg;
//					}else if (group.getB0194().equals("3")){
//						path=groupOrgImg;
//					}else{
//						path=companyOrgImg;
//					}
//					if(!own){
//						path=wrongImg;
//					}
//					jsonStr.append("[{\"text\" :\"" + group.getB0101()
//							+ "\" ,\"id\" :\"" + group.getB0111()
//							+ "\" ,\"leaf\" :" + SysOrgBS.hasChildren(group.getB0111())
////							+ "\" ,\"cls\" :\"folder\",");
//							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
//					if(own){
//						jsonStr.append("\"href\":");
//						jsonStr.append("\"javascript:radow.doEvent('querybyid','"
//								+ group.getB0111() + "')\"");
//					}else{
//						jsonStr.append("\"href\":");
//						jsonStr.append("\"javascript:alert('您没有权限')\"");
//					}
//					jsonStr.append("}]");
//				}else if (i == 0) {
//					if(group.getB0194().equals("2")){
//						path=insideOrgImg;
//					}else if (group.getB0194().equals("3")){
//						path=groupOrgImg;
//					}else{
//						path=companyOrgImg;
//					}
//					if(!own){
//						path=wrongImg;
//					}
//					jsonStr.append("[{\"text\" :\"" + group.getB0101()
//							+ "\" ,\"id\" :\"" + group.getB0111()
//							+ "\" ,\"leaf\" :" + SysOrgBS.hasChildren(group.getB0111())
//							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
//					if(own){
//						jsonStr.append("\"href\":");
//						jsonStr.append("\"javascript:radow.doEvent('querybyid','"
//								+ group.getB0111() + "')\"");
//					}else{
//						jsonStr.append("\"href\":");
//						jsonStr.append("\"javascript:alert('您没有权限')\"");
//					}
//					jsonStr.append("}");
//				}else if (i == (last - 1)) {
//					if(group.getB0194().equals("2")){
//						path=insideOrgImg;
//					}else if (group.getB0194().equals("3")){
//						path=groupOrgImg;
//					}else{
//						path=companyOrgImg;
//					}
//					if(!own){
//						path=wrongImg;
//					}
//					jsonStr.append(",{\"text\" :\"" + group.getB0101()
//							+ "\" ,\"id\" :\"" + group.getB0111()
//							+ "\" ,\"leaf\" :" + SysOrgBS.hasChildren(group.getB0111())
//							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
//					if(own){
//						jsonStr.append("\"href\":");
//						jsonStr.append("\"javascript:radow.doEvent('querybyid','"
//								+ group.getB0111() + "')\"");
//					}else{
//						jsonStr.append("\"href\":");
//						jsonStr.append("\"javascript:alert('您没有权限')\"");
//					}
//					jsonStr.append("}]");
//				} else {
//					if(group.getB0194().equals("2")){
//						path=insideOrgImg;
//					}else if (group.getB0194().equals("3")){
//						path=groupOrgImg;
//					}else{
//						path=companyOrgImg;
//					}
//					if(!own){
//						path=wrongImg;
//					}
//					jsonStr.append(",{\"text\" :\"" + group.getB0101()
//							+ "\" ,\"id\" :\"" + group.getB0111()
//							+ "\" ,\"leaf\" :" + SysOrgBS.hasChildren(group.getB0111())
//							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
//					if(own){
//						jsonStr.append("\"href\":");
//						jsonStr.append("\"javascript:radow.doEvent('querybyid','"
//								+ group.getB0111() + "')\"");
//					}else{
//						jsonStr.append("\"href\":");
//						jsonStr.append("\"javascript:alert('您没有权限')\"");
//					}
//					jsonStr.append("}");
//				}
//				i++;
//			}
//		} else {
//			jsonStr.append("{}");
//		}
//		this.setSelfDefResData(jsonStr.toString());
//		return EventRtnType.XML_SUCCESS;
//	}
	
	//新增按钮
	@PageEvent("addOrgWinBtn.onclick")
	public int addOrgWin() throws RadowException, Exception {
		if(!SysOrgBS.selectB01Count().equals("1")){
			String groupid = this.getPageElement("checkedgroupid").getValue();
			this.setRadow_parent_data("1,"+this.getPageElement("checkedgroupid").getValue());
			if(groupid.trim().equals("")||groupid==null){
				throw new RadowException("请选择机构!");
			}
			if(!SysRuleBS.havaRule(groupid)){
				throw new RadowException("您无此权限!");
			}
		}else{
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname=user.getLoginname();
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);
			if(groups.isEmpty() || issupermanager ||loginnname.equals("admin")){
				this.setRadow_parent_data("1,"+"-1");
			}else{
				throw new RadowException("您无此权限!");
			}
		}
		this.openWindow("addOrgWin", "pages.sysorg.org.CreateSysOrg");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//修改按钮
	@PageEvent("updateOrgWinBtn.onclick")
	public int updateOrgWin() throws RadowException {
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid.trim().equals("")||groupid==null){
			throw new RadowException("请选择机构!");
		}
		if(!SysRuleBS.havaRule(groupid)){
			throw new RadowException("您无此权限!");
		}
		this.request.getSession().setAttribute("tag", "0");
		this.openWindow("updateOrgWin", "pages.sysorg.org.CreateSysOrg");
		this.setRadow_parent_data("2,"+this.getPageElement("checkedgroupid").getValue());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//排序按钮
	@PageEvent("sortSysOrgBtn.onclick")
	public int sortSysOrg() throws RadowException {
	    this.openWindow("orgSortWin", "pages.sysorg.org.OrgSort");
		this.request.getSession().setAttribute("transferType", "orgSort");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//删除按钮点击事件
	@PageEvent("deleteOrgBtn.onclick")
	public int deleteOrg() throws RadowException {
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid.trim().equals("")||groupid==null){
			throw new RadowException("请选择要删除的机构!");
		}
		if(!SysRuleBS.havaRule(groupid)){
			throw new RadowException("您无此权限!");
		}
		B01 b01 = SysOrgBS.LoadB01(groupid);
		dialog_set("deleteconfirm","确定删除"+b01.getB0101()+"吗？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//删除事件
	@PageEvent("deleteconfirm")
	public int deleteconfirm() throws RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(SysOrgBS.selectCountBySubId(groupid).equals("0")){
			SysOrgBS.delB01(groupid);
			SysRuleBS.deleteUserDept(groupid);
			this.getPageElement("checkedgroupid").setValue("");
			this.getExecuteSG().addExecuteCode("window.reloadTree()");
		}else{
			B01 b01 = SysOrgBS.LoadB01(groupid);
			dialog_set("deleteAll","确认删除"+b01.getB0101()+"及其下属机构吗？");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//删除机构以及下级机构事件
	@PageEvent("deleteAll")
	public int deleteAll() throws RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		String groupid = this.getPageElement("checkedgroupid").getValue();
		SysOrgBS.delB01(groupid);
		SysRuleBS.batchDeleteUserDept(groupid);
		this.getPageElement("checkedgroupid").setValue("");
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//查询按钮点击事件
	@PageEvent("selectAllBtn.onclick")
	public int selectAll() throws RadowException, PrivilegeException{
		this.request.getSession().setAttribute("queryType", "2");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//整建制转移
	@PageEvent("transferSysOrg.onclick")
	public int transferSysOrg() throws RadowException {
	    this.openWindow("transferSysOrgWin", "pages.sysorg.org.Zjzzy");
		this.request.getSession().setAttribute("transferType", "transferSysOrg");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//批量转移人员
	@PageEvent("batchTransferPersonnel.onclick")
	public int batchTransferPersonnel() throws RadowException {
	    this.openWindow("batchTransferPersonnelWin", "pages.sysorg.org.Zjzzy");
		this.request.getSession().setAttribute("tag", "0");
		this.request.getSession().setAttribute("transferType", "batchTransferPersonnel");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//刷新本页面
	@PageEvent("reloadWin")
	public int reloadWin(String id) throws RadowException{
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//重置按钮
	@PageEvent("reset.onclick")
	@NoRequiredValidate           
	@OpLog
	public int resetonclick()throws RadowException, AppException {
		this.getPageElement("SysOrgTree").setValue("");
		this.getPageElement("SysOrgTreeIds").setValue("");
		this.getPageElement("b0101name").setValue("");
		this.getPageElement("b0127").setValue("");
		this.getPageElement("b0127_combo").setValue("");
		this.getPageElement("b0131").setValue("");
		this.getPageElement("b0131_combo").setValue("");
		this.getPageElement("b0124").setValue("");
		this.getPageElement("b0124_combo").setValue("");
		this.getPageElement("b0117").setValue("");
		this.getPageElement("b0117_combo").setValue("");
		this.getPageElement("b0194").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//编辑页面
	@PageEvent("openUpdateOrgWinBtn.onclick")
	public int openUpdateOrgWin()throws RadowException, AppException {
		this.getExecuteSG().addExecuteCode("window.location.href='" +request.getContextPath() +  
				"/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgOther'");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void dialog_set(String fnDelte, String strHint){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName(fnDelte);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage(strHint); // 窗口提示信息
	}
	
	/**
	 * 修改人员信息的双击事件
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception 
	 */
	@PageEvent("memberGrid.rowdbclick")
	@GridDataRange
	public int memberGridOnRowDbClick() throws RadowException, Exception{  //打开窗口的实例
		String groupid = this.getPageElement("memberGrid").getValue("b0111",this.getPageElement("memberGrid").getCueRowIndex()).toString();
		if(groupid.trim().equals("")||groupid==null){
			throw new RadowException("请选择机构!");
		}
		if(!SysRuleBS.havaRule(groupid)){
			throw new RadowException("您无此权限!");
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		String loginnname=user.getLoginname();
		List<GroupVO> groups1 = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
		UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
		boolean issupermanager=new DefaultPermission().isSuperManager(vo);
		List<String> b0111s =new ArrayList();
		boolean isadmin =false;
		if(groups1.isEmpty() || issupermanager ||loginnname.equals("admin")){
			isadmin =true;
		}else{
			b0111s=authority(cueUserid);
		}
		Boolean own = isown(b0111s,groupid,isadmin);
		if(own){
			this.request.getSession().setAttribute("tag", "0");
			this.openWindow("updateOrgWin", "pages.sysorg.org.CreateSysOrg");
			this.setRadow_parent_data("2,"+groupid);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 修改人员信息的双击事件
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception 
	 */
	@PageEvent("treeDblclick")
	@GridDataRange
	public int treeDblclick(String node) throws RadowException, Exception{  //打开窗口的实例
		String groupid = node;
		CommonQueryBS.systemOut(node);
		if(groupid.trim().equals("")||groupid==null){
			throw new RadowException("请选择机构!");
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		String loginnname=user.getLoginname();
		List<GroupVO> groups1 = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
		UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
		boolean issupermanager=new DefaultPermission().isSuperManager(vo);
		List<String> b0111s =new ArrayList();
		boolean isadmin =false;
		if(groups1.isEmpty() || issupermanager ||loginnname.equals("admin")){
			isadmin =true;
		}else{
			b0111s=authority(cueUserid);
		}
		Boolean own = isown(b0111s,groupid,isadmin);
		if(own){
			this.request.getSession().setAttribute("tag", "0");
			this.openWindow("updateOrgWin", "pages.sysorg.org.CreateSysOrg");
			this.setRadow_parent_data("2,"+groupid);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public boolean isown(List list,String b0111,boolean isadmin){
		if(isadmin){
			return true;
		}
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				if(list.get(i).equals(b0111)){
					return true;
				}
			}
		}
		return false;
	}
	
	public List authority(String userid){
		HBSession sess = HBUtil.getHBSession();
		List<String> b0111s = (List) sess.createSQLQuery("select t.b0111 from COMPETENCE_USERDEPT t where t.userid='"+userid+"'").list();
		return b0111s;
	}
	
	//登陆人权限
	public String jurisdiction(){
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		Object[] area = (Object[]) HBUtil.getHBSession().createSQLQuery("SELECT b.b0101,a.b0111,b.b0194 FROM COMPETENCE_USERDEPT a,B01 b WHERE  a.b0111=b.b0111 and a.userid='"+cueUserid+"'").uniqueResult();
		String b0111 =(String) area[1];
		return b0111;
	}
	
	/**
	 * 对选中机构进行数据校验
	 * @return
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("dataVerify.onclick")
	public int dataVerify() throws RadowException , AppException{
		List<HashMap<String, Object>> list = this.getPageElement("memberGrid").getValueList();
		int countNum = 0;
		String groupid = null;
		for (int j = 0; j < list.size(); j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("check");
			if (check1 != null && check1.equals(true)) {
				groupid = map.get("b0111x") != null ? map.get("b0111x").toString() : "";
				countNum++;
			}
		}
		if (countNum == 0) {
			groupid = this.getPageElement("checkedgroupid").getValue();
			if (groupid.trim().equals("") || groupid == null) {
				throw new RadowException("请选择要校验的机构!");
			} else if (!SysRuleBS.havaRule(groupid)) {
				throw new RadowException("您无此权限!");
			}
		} else if (groupid != "") {
			if (countNum > 1) {
				throw new AppException("请勾选一个机构！");
			}
			if (!SysRuleBS.havaRule(groupid)) {
				throw new RadowException("您无此权限!");
			}
		}
		this.setRadow_parent_data("1@" + groupid);
		this.openWindow("dataVerifyWin", "pages.sysorg.org.orgdataverify.OrgDataVerify");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * @return
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("exportAlLOrg.onclick")
	public int exportAlLOrg() throws RadowException {
		this.setRadow_parent_data("1");
		this.openWindow("exportOrgColumn", "pages.sysorg.org.ExportOrgColumn");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询所有机构
	 */
	@NoRequiredValidate
	@PageEvent("selectAllOrgBtn.onclick")
	public int selectAllOrg() throws RadowException {
		this.request.getSession().setAttribute("queryType", "3");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 查询选中单位下所有机构
	 */
	@NoRequiredValidate
	@PageEvent("selectAllDownBtn.onclick")
	public int selectAllDown() throws RadowException {
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid.trim().equals("")||groupid==null){
			throw new RadowException("请选择机构!");
		}
		this.request.getSession().setAttribute("queryType", "4");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 查询所有可见机构和查询选中单位下所有可见机构
	 */
	@NoRequiredValidate
	@PageEvent("selectSee")
	public int selectSee(String ids) throws RadowException {
		this.request.getSession().setAttribute("queryids", ids.substring(0, ids.length()-1));
		this.request.getSession().setAttribute("queryType", "5");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public static void main(String[] args) {
		List<B01> list =new ArrayList();
		B01 b01 =new B01();
		b01.setB0111("001.001");
		b01.setSortid(1L);
		B01 b02 =new B01();
		b02.setB0111("001.001.001");
		b02.setSortid(3L);
		B01 b03 =new B01();
		b03.setB0111("001.001.002");
		b03.setSortid(2L);
		B01 b04 =new B01();
		b04.setB0111("001.001.003");
		b04.setSortid(1L);
		B01 b05 =new B01();
		b05.setB0111("001.002");
		b05.setSortid(2L);
		B01 b06 =new B01();
		b06.setB0111("001.002.001");
		b06.setSortid(3L);
		list.add(b01);
		list.add(b02);
		list.add(b03);
		list.add(b04);
		list.add(b05);
		list.add(b06);
		List<B01> tempList = new ArrayList();
		List<B01> sortList = new ArrayList();
		int upB01 = 0;
		String tag="1";
		for(int i=0;i<list.size();i++){
			B01 b01temp = list.get(i);
			if(b01temp.getB0111().length()==upB01){
				if(tag.equals("1")){
					tempList.add(list.get(i-1));
					tag="0";
					sortList.remove(sortList.size()-1);
				}
				tempList.add(list.get(i));
			}else{
				tag="1";
				upB01=b01temp.getB0111().length();
				if(tempList.size()>0){
					Collections.sort(tempList, new Comparator<B01>() {
						@Override
						public int compare(B01 arg0, B01 arg1) {
							// TODO Auto-generated method stub
							return arg0.getSortid().compareTo(arg1.getSortid());
						}
			        });
					for (B01 p : tempList) {
						sortList.add(p);
				    }
					tempList=new ArrayList();
				}
				sortList.add(b01temp);
			}
			
		}
		for (B01 p : sortList) {
			CommonQueryBS.systemOut(p.getB0111()+":"+p.getSortid());
		}
	}
}
