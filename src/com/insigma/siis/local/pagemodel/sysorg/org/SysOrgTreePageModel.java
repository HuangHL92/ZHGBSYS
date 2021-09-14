package com.insigma.siis.local.pagemodel.sysorg.org;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.B01;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

public class SysOrgTreePageModel extends PageModel{

	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String tag= "0";//修改动作0未执行1已执行
	public SysOrgTreePageModel(){
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
	
	@PageEvent("dogrant")
	public  int  gettree(String value) throws RadowException {
		StringBuffer sb = new StringBuffer("");
		JSONObject jsonObject = JSONObject.fromObject(value);
		Iterator<String> it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext()) {
			String nodeid = it.next(); 
			String operators = (String) jsonObject.get(nodeid);
			String[] types = operators.split(":");
			sb.append(types[0]+"\r\n");
		}
		sb.append("{,}").append(value);
		/*StringBuffer sb_and = new StringBuffer("");
		StringBuffer sb_or = new StringBuffer("");
		JSONObject jsonObject = JSONObject.fromObject(value);
		Iterator<String> it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		//单个节点的操作按最后一次包含下级的操作为准，否则就按最后一次为准。
		while (it.hasNext()) {
			String nodeid = it.next(); 
			String operators = (String) jsonObject.get(nodeid);
			String[] operatorArrary = operators.split(",");
			boolean hasContain = false;
			for(int i = operatorArrary.length-1;i>=0;i--){
				String operator = operatorArrary[i];
				String[] types = operator.split(":");
				if("1".equals(types[1])&&"true".equals(types[0])){
					sb_or.append(" or b01.b0111 like '"+nodeid + "%'");
					hasContain = true;
					break;
				}else if("2".equals(types[1])&&"false".equals(types[0])){
					sb_and.append(" and b01.b0111 not like '"+nodeid + "%'");
					hasContain = true;
					break;
				}
			}
			String lastnode = operatorArrary[operatorArrary.length-1];
			String[] types = lastnode.split(":");
			if(!hasContain||"0".equals(types[1])){
				if("true".equals(types[0])){
					sb_or.append(" or b01.b0111 = '"+nodeid+"'");
				}else{
					sb_and.append(" and b01.b0111 != '"+nodeid+"'");
				}
			}
			
			
			
		} 
		String sql = " select b0101 from b01 where 1=1 "+sb_and.toString()+"and (1=2 "+sb_or.toString()+")";
		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		StringBuffer orgNames=new StringBuffer("");
		String property = this.getPageElement("property").getValue();
		String closewin = this.getPageElement("closewin").getValue();
		if(list!=null && list.size()>0){
			for(String s : list){
				orgNames.append(s+"\r\n");
			}
			orgNames.append(",1");
			//this.getExecuteSG().addExecuteCode("parent.returnwin"+property+"('"+orgNames+"')");
		}else{
			this.getExecuteSG().addExecuteCode("parent.returnwin"+property+"('"+""+","+""+"')");
		}*/
		String property = this.getPageElement("property").getValue();
		String closewin = this.getPageElement("closewin").getValue();
		this.closeCueWindow(closewin);
		this.getExecuteSG().addExecuteCode("parent.returnwin"+property+"('"+sb+"')");
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("cancelBtn.onclick")
	public int updateCancel() throws AppException, RadowException {
		String closewin = this.getPageElement("closewin").getValue();
		this.closeCueWindow(closewin);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public List<B01> selectListByIds(String ids){
		List<B01> list = new ArrayList<B01>();
		String[] a =ids.split(",");
		String sql="";
		if(a.length>999){
			StringBuffer sqlString = new StringBuffer();
			for (int i = 0; i < a.length; i++) {  
                if (i == (a.length - 1)) {  
                    sqlString.append(a[i]); //SQL拼装，最后一条不加“,”。  
                }else if((i%999)==0 && i>0){  
                    sqlString.append(a[i]).append(") or B0111 in ("); //解决ORA-01795问题  
                }else{  
                    sqlString.append(a[i]).append(",");  
                }  
            } 
			sql="from B01 where B0111 in ("+sqlString+") order by sortid";
		}else{
			if(ids.length()>0){
				sql="from B01 where B0111 in ("+ids+") order by sortid";
			}
		}
		list = HBUtil.getHBSession().createQuery(sql).list();
		return list;
	}
	
	public List<B01> selectListBySubId(String id){
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		List<B01> list = new ArrayList<B01>();
		List<Object> l = new ArrayList<Object>();
		String sql="select *"
				+ " from b01 a,"
				+ " (select *"
				+ "		from competence_userdept"
				+ "		where userid = '"+cueUserid+"') b"
				+ " where a.b0111 like '"+id+"%'"
				+ " and a.b0111 = b.b0111";
		list = HBUtil.getHBSession().createSQLQuery(sql).addEntity(B01.class).list();
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
	
	//初始化组织机构树
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		String sign = this.getParameter("sign");//浏览  编辑  机构树编辑
		
		PublicWindowPageModel publicWindowPageModel =new PublicWindowPageModel();
		String jsonStr ="";
		if(sign==null||"".equals(sign))
		{
			jsonStr = publicWindowPageModel.getJson("2",node);
		}else {
			jsonStr = publicWindowPageModel.getJson("2",node,sign);
		}
		
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	//获取机构数
	public int getCount(String id){
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		String sql="select count(1)"
				+ " from b01 a,"
				+ " (select b0111"
				+ "		from competence_userdept"
				+ "		where userid = '"+cueUserid+"') b"
				+ " where a.b0111 like '"+id+"%'"
				+ " and a.b0111 = b.b0111";	
		Object ob = HBUtil.getHBSession().createSQLQuery(sql).list().get(0);
		Integer a  = Integer.valueOf(ob.toString());
		return a;
	}
}
