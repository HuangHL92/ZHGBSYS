package com.insigma.siis.local.pagemodel.sysorg.org;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

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
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysorg.org.OrgNodeTree;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.util.FileUtil;

import net.sf.json.JSONObject;

public class OrgStatisticsPageModel extends PageModel {
	
	/**
	 * 系统区域信息
	 */
	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	
	public OrgStatisticsPageModel(){
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
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("query2")
	public int query2(String value) throws RadowException{
		String userID = SysManagerUtils.getUserId();
		String b01String = value;
		StringBuffer a02_a0201b_sb = new StringBuffer("");
        StringBuffer cu_b0111_sb = new StringBuffer("");
        if(b01String!=null && !"".equals(b01String)){
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" and (1=2 ");
				cu_b0111_sb.append(" and (1=2 ");
			}
			Iterator<String> it = jsonObject.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {
				String nodeid = it.next(); 
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");//[机构名称，是否包含下级，是否本级选中]
				if("true".equals(types[1])&&"true".equals(types[2])){
					//a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+"%' ");
					a02_a0201b_sb.append(" or "+CommSQL.subString("a02.a0201b", 1, nodeid.length(), nodeid));
					//cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+"%' ");
					cu_b0111_sb.append(" or "+CommSQL.subString("cu.b0111", 1, nodeid.length(), nodeid));
				}else if("true".equals(types[1])&&"false".equals(types[2])){
					//a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+".%' ");
					a02_a0201b_sb.append(" or "+CommSQL.subString2("a02.a0201b", 1, nodeid.length(), nodeid));
					//cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+".%' ");
					cu_b0111_sb.append(" or "+CommSQL.subString2("cu.b0111", 1, nodeid.length(), nodeid));
				}else if("false".equals(types[1])&&"true".equals(types[2])){
					a02_a0201b_sb.append(" or a02.a0201b = '"+nodeid+"' ");
					cu_b0111_sb.append(" or cu.b0111 = '"+nodeid+"' ");
				}
			}
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" ) ");
				cu_b0111_sb.append(" ) ");
			}
        }
			
	       // String finalsql = CommSQL.getCondiQuerySQL(userID,a01sb,a02sb,a02_a0201b_sb,cu_b0111_sb,orther_sb,a0163);
	        
	        //this.getPageElement("sql").setValue(cu_b0111_sb.toString());	

			if("{}".equals(value)){
				this.setMainMessage("请先选择机构！");
				return EventRtnType.NORMAL_SUCCESS;
			}

			String num = "";
			String execlName = this.getRadow_parent_data();
			if("leader".equals(this.getRadow_parent_data())){
				num = "3";
			}
			if("people".equals(this.getRadow_parent_data())){
				num = "4";
			}
			String param = num+"|"+b01String+"|"+cu_b0111_sb+"|"+execlName;
			String n = "";
			/*try {
				System.out.println(param.indexOf("\""));
				n = ByteToStringUtil.byteToBase64Str(param.getBytes("gbk"));
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			String p = param.replace("'", "\\'");
			//p = p.replace("\"", "\\\\\"");
			//System.out.println(p);
			this.getExecuteSG().addExecuteCode("lowOrgLeaderYuLan('"+p+"')");
			this.closeCueWindow("orgStatisticsWin");
 			return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	/**
	 * @return 
	 * @throws RadowException
	 */
	
	@NoRequiredValidate
//	@PageEvent("dogrant")
	public int expExecl(String value) throws RadowException {
		String execlName = this.getRadow_parent_data();
		//String execlName=this.getPageElement("subWinIdBussessId").getValue();
		if(execlName==null){
			this.setMainMessage("参数错误!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		CommonQueryBS.systemOut(execlName);
		String expFile ="";
		if(value ==null){
			this.setMainMessage("请选择机构！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<B01> list = getTreeList(value);
		if(execlName.equals("people")){
			expFile = peopleExp(list);
		}else{
			expFile = leaderExp(list);
		}
	    this.getPageElement("downfile").setValue(expFile.replace("\\", "/"));
		this.getExecuteSG().addExecuteCode("window.download()");
		
		this.closeCueWindow("orgStatisticsWin");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * @return 
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("cancelBtn.onclick")
	public int cancel() throws RadowException {
		this.closeCueWindow("orgStatisticsWin");
		return EventRtnType.NORMAL_SUCCESS;
	}

	public List<B01> getTreeList(String value){
		String ret=null;
		String[] nodes = null;
		HashMap<String,String> nodemap = new HashMap<String,String>();
		nodes = value.split(",");
		for(int i=0;i<nodes.length;i++) {
			nodemap.put(nodes[i].split(":")[0], nodes[i].split(":")[1]);
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
		List<B01> list= null;
		if(ret!=null&&!"".equals(ret)){
			b01String=ret.substring(0,ret.lastIndexOf(","));
			list = selectListByIds(b01String);
		}
		return list;
	}
	
	public List<B01> selectListByIds(String ids){
		List<B01> list = new ArrayList<B01>();
		String userId = SysUtil.getCacheCurrentUser().getId();
		if(ids.length()>0){
			String sql="Select b0111 from B01 b where b.b0194 in ('1','2') and exists (select * from COMPETENCE_USERDEPT t where t.userid='"+userId+"' and t.b0111 in  ("+ids+") and t.type in ('0','1') and t.b0111=b.b0111)";
			List liststr = HBUtil.getHBSession().createSQLQuery(sql).list();
			String str="";
			if(liststr.size()>0){
				for(int i=0;i<liststr.size();i++){
					str = str+"'"+liststr.get(i)+"',";
				}
				str=str.substring(0,str.length()-1);
				String sql2="from B01 where b0111 in ("+str+") order by b0111";
				list = HBUtil.getHBSession().createQuery(sql2).list();	
			}
		}
		return list;
	}
	
	public String leaderExp(List<B01> list) throws RadowException  {
		String fileName ="各单位领导职数配备表";
		String laststr=".xls";
		String loadFile= ExpRar.getExeclPath()+"\\"+fileName+laststr;
		String expFile=ExpRar.expFile()+fileName + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+laststr;
		CommonQueryBS.systemOut(loadFile);
		CommonQueryBS.systemOut(expFile);
		InputStream is;
		try {
			FileUtil.createFile(expFile);
			is = new FileInputStream(new File(loadFile));
			int counts=0;
			if(list !=null){
				counts = list.size();
			}
			byte[] bytes =SysOrgBS.wirteExeclLowOrgLeader(is,list,counts);
		    FileOutputStream outf=new FileOutputStream(expFile);
		    BufferedOutputStream bufferout= new BufferedOutputStream(outf);
		    bufferout.write(bytes);
		    bufferout.flush();
		    bufferout.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return expFile;
	}
	
	public String peopleExp(List<B01> list) throws RadowException  {
		String fileName ="各单位编制与人员配备表";
		String laststr=".xls";
		String loadFile= ExpRar.getExeclPath()+"\\"+fileName+laststr;
		String expFile=ExpRar.expFile()+fileName + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+laststr;
		CommonQueryBS.systemOut(loadFile);
		CommonQueryBS.systemOut(expFile);
		InputStream is;
		try {
			FileUtil.createFile(expFile);
			is = new FileInputStream(new File(loadFile));
			int counts=0;
			if(list !=null){
				counts = list.size();
			}
			byte[] bytes =SysOrgBS.wirteExeclLowOrgPeople(is,counts,list);
		    FileOutputStream outf=new FileOutputStream(expFile);
		    BufferedOutputStream bufferout= new BufferedOutputStream(outf);
		    bufferout.write(bytes);
		    bufferout.flush();
		    bufferout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return expFile;
	}
	
	//初始化组织机构树
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String jsonStr =OrgNodeTree.getCodeTypeJS("2");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
}
