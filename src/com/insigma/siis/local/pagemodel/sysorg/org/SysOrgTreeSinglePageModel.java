/**
 * 
 */
package com.insigma.siis.local.pagemodel.sysorg.org;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

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
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

/**
 * @author Administrator
 *
 */
public class SysOrgTreeSinglePageModel extends PageModel {

	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String tag= "0";//修改动作0未执行1已执行
	public SysOrgTreeSinglePageModel(){
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
				area = (Object[]) sess.createSQLQuery("select b0101,b0111,b0194 from B01 where b0111='-1'").uniqueResult();
				areaInfo.put("manager", "true");
			}else{
				area = (Object[]) sess.createSQLQuery("select b0101,b0111,b0194 from B01 where b0111='-1'").uniqueResult();
				areaInfo.put("manager", "false");
			}
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//初始化组织机构树
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		String sql="from B01 where B0121='"+node+"' order by sortid";
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();//得到当前组织信息
		//只显示所在的组织及下级组织 不在组织中 则显示全部
		List<B01> choose = new ArrayList<B01>();
		String sql2="from B01 where B0111='"+node+"'";
		List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();//得到当前组织信息
		for(int i=0;i<groups.size();i++){
			for(int j=0;j<groups.size();j++){
				if(groups.get(j).getB0111().equals(groups.get(i).getB0121())){
					groups.remove(i);
					i--;
				}
			}
		}
		boolean equel = false;
		if(!groups.isEmpty()){
			for(int i = 0;i<list.size();i++){
				for(int j = 0;j<groups.size();j++){
					if(groups.get(j).getB0111().equals(list.get(i).getB0111())){
						choose.add(groups.get(j));
						equel = true;
					}
				}
			}
		}
		if(equel){
			list = choose;
		}
		StringBuffer jsonStr = new StringBuffer();
		String companyOrgImg = request.getContextPath()+"/pages/sysorg/org/images/companyOrgImg2.png";
		String insideOrgImg = request.getContextPath()+"/pages/sysorg/org/images/insideOrgImg1.png";
		String groupOrgImg = request.getContextPath()+"/pages/sysorg/org/images/groupOrgImg1.png";
		String path = companyOrgImg;
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (B01 group : list) {
				if(i==0 && last==1) {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
					}else if (group.getB0194().equals("3")){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					jsonStr.append("[{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111())
//							+ "\" ,\"cls\" :\"folder\",");
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getB0111() + "')\"");
					jsonStr.append("}]");
				}else if (i == 0) {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
					}else if (group.getB0194().equals("3")){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					jsonStr.append("[{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111())
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
							jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getB0111() + "')\"");
					jsonStr.append("}");
				}else if (i == (last - 1)) {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
					}else if (group.getB0194().equals("3")){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					jsonStr.append(",{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111())
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getB0111() + "')\"");
					jsonStr.append("}]");
				} else {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
					}else if (group.getB0194().equals("3")){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					jsonStr.append(",{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111())
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ group.getB0111() + "')\"");
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
	
	//点击树事件
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException {
		try {
			String sql2="from B01 where B0111='"+id+"'";
			List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();//得到当前组织信息
		} catch (Exception e) {
			throw new RadowException(e.getMessage());
		}
		this.getPageElement("checkedgroupid").setValue(id);
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("dogrant")
	public  int  gettree(String value) throws RadowException {
		String ret=null;
		String[] nodes = null;
		HashMap<String,String> nodemap = new HashMap<String,String>();
		if(value !=null) {
			nodes = value.split(",");
			for(int i=0;i<nodes.length;i++) {
				nodemap.put(nodes[i].split(":")[0], nodes[i].split(":")[1]);
			}
		}else{
			this.setMainMessage("请选择机构！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		StringBuffer addresourceIds = new StringBuffer();
		StringBuffer removeresourceIds = new StringBuffer();
		for(String node :nodemap.keySet()) {
			if(nodemap.get(node).equals("true")) {
				addresourceIds.append("'"+node+"',");
			}else if(nodemap.get(node).equals("false")) {
				removeresourceIds.append(node+",");
			}
		}
		ret=addresourceIds.toString();
		String b01String="";
		if(ret!=null&&!"".equals(ret)){
			b01String=ret.substring(0,ret.lastIndexOf(","));
			List<B01> list = selectListByIds(b01String);
			String orgNames ="";
			for(int i=0;i<list.size();i++){
				String b0101 = list.get(i).getB0101();
				orgNames+=b0101+"\n";
//				orgNames+=b0101;
//				this.createPageElement("SysOrgTree", ElementType.TEXTAREA, true).setValue(orgNames);
//				this.createPageElement("SysOrgTreeIds", ElementType.HIDDEN, true).setValue(b01String);
			}
//			System.out.println("returnWin ("+orgNames+","+b01String.replace("'", "|").replace(",", ":")+")");
			String a =b01String.replace(",", "@");
//			System.out.println(a.replace("'", "|"));
			String b = orgNames+","+a.replace("'", "|");
			CommonQueryBS.systemOut(b);
			this.getExecuteSG().addExecuteCode("returnWin ('"+b+"')");
		}else{
//			this.createPageElement("SysOrgTree", ElementType.TEXTAREA, true).setValue("");
//			this.createPageElement("SysOrgTreeIds", ElementType.HIDDEN, true).setValue("");
			this.getExecuteSG().addExecuteCode("returnWin ('"+""+","+""+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
//	@PageEvent("selectOrgsBtn.onclick")
//	public int selectOrgs() throws AppException, RadowException {
//		this.getExecuteSG().addExecuteCode("doQuery();");
//		return EventRtnType.NORMAL_SUCCESS;
//	}

	@PageEvent("cancelBtn.onclick")
	public int updateCancel() throws AppException, RadowException {
		this.closeCueWindow();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public List<B01> selectListByIds(String ids){
		List<B01> list = new ArrayList<B01>();
		if(ids.length()>0){
			String sql="from B01 where B0111 in ("+ids+") order by sortid";
			list = HBUtil.getHBSession().createQuery(sql).list();
		}
		return list;
	}
	
	private String hasChildren(String id){
		String sql="from B01 b where B0121='"+id+"' order by sortid";// -1其它现职人员
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();
		if(list!=null && list.size()>0){
			return "false";
		}else{
			return "true";
		}
	}
}

