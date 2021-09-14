package com.insigma.siis.local.pagemodel.sysorg.org;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
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
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.B01sort;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.business.sysorg.org.dto.B01DTO;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class OrgSortPageModel extends PageModel{

	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String tag= "0";//修改动作0未执行1已执行
	public static String updateTag= "0";//修改动作0未执行1已执行
	public OrgSortPageModel(){
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
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		this.updateTag="0";
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//初始化组织机构树
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		if(this.updateTag.equals("0")){
			String delB01Sort = "delete from b01_sort where update_user ='"+user.getId()+"'";
			String insertB01Sort ="insert into b01_sort(b0111,b0121,b0101,b0194,sortid,update_user) SELECT b0111,b0121,b0101,b0194,sortid,'"+user.getId()+"' FROM B01 ";
			HBUtil.getHBSession().createSQLQuery(delB01Sort).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(insertB01Sort).executeUpdate();
			HBUtil.getHBSession().flush();
			this.updateTag="1";
		}
		String node = this.getParameter("node");
		String sql="from B01sort where B0121='"+node+"' and update_user ='"+user.getId()+"' order by sortid";
		List<B01sort> list = HBUtil.getHBSession().createQuery(sql).list();//得到当前组织信息
		//只显示所在的组织及下级组织 不在组织中 则显示全部
		List<B01sort> choose = new ArrayList<B01sort>();
		String sql2="from B01sort where B0111='"+node+"' and update_user ='"+user.getId()+"'";
		List<B01sort> groups = HBUtil.getHBSession().createQuery(sql2).list();//得到当前组织信息
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
		String wrongImg = request.getContextPath()+"/pages/sysorg/org/images/wrong.gif";
		String path = companyOrgImg;
		
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

		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (B01sort group : list) {
				Boolean own = isown(b0111s,group.getB0111(),isadmin);
				if(i==0 && last==1) {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
					}else if (group.getB0194().equals("3")){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					if(!own){
						path=wrongImg;
					}
					jsonStr.append("[{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + SysOrgBS.hasChildren(group.getB0111())
//							+ "\" ,\"cls\" :\"folder\",");
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					if(own){
						jsonStr.append("\"href\":");
						jsonStr.append("\"javascript:radow.doEvent('querybyid','"
								+ group.getB0111() + "')\"");
					}else{
						jsonStr.append("\"name\":");
						jsonStr.append("\"\"");
					}
					jsonStr.append("}]");
				}else if (i == 0) {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
					}else if (group.getB0194().equals("3")){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					if(!own){
						path=wrongImg;
					}
					jsonStr.append("[{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + SysOrgBS.hasChildren(group.getB0111())
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					if(own){
						jsonStr.append("\"href\":");
						jsonStr.append("\"javascript:radow.doEvent('querybyid','"
								+ group.getB0111() + "')\"");
					}else{
						jsonStr.append("\"name\":");
						jsonStr.append("\"\"");
					}
					jsonStr.append("}");
				}else if (i == (last - 1)) {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
					}else if (group.getB0194().equals("3")){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					if(!own){
						path=wrongImg;
					}
					jsonStr.append(",{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + SysOrgBS.hasChildren(group.getB0111())
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					if(own){
						jsonStr.append("\"href\":");
						jsonStr.append("\"javascript:radow.doEvent('querybyid','"
								+ group.getB0111() + "')\"");
					}else{
						jsonStr.append("\"name\":");
						jsonStr.append("\"\"");
					}
					jsonStr.append("}]");
				} else {
					if(group.getB0194().equals("2")){
						path=insideOrgImg;
					}else if (group.getB0194().equals("3")){
						path=groupOrgImg;
					}else{
						path=companyOrgImg;
					}
					if(!own){
						path=wrongImg;
					}
					jsonStr.append(",{\"text\" :\"" + group.getB0101()
							+ "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + SysOrgBS.hasChildren(group.getB0111())
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					if(own){
						jsonStr.append("\"href\":");
						jsonStr.append("\"javascript:radow.doEvent('querybyid','"
								+ group.getB0111() + "')\"");
					}else{
						jsonStr.append("\"name\":");
						jsonStr.append("\"\"");
					}
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
		this.getPageElement("checkedgroupid").setValue(id);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("UpBtn.onclick")
	public int Up() throws RadowException, AppException{
		CurrentUser user = SysUtil.getCacheCurrentUser();
		Long Choose; //选中 sortid
		Long RePlace ;//替换 sortid
		Long Middle ;//中间sortid
		//查询出同级机构排序字段
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if(groupid==null||groupid.equals("")){
			this.setMainMessage("请选择要移动的机构！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String Sql1="from B01sort where update_user ='"+user.getId()+"' and b0121 in ( select t.b0121 from B01 t where t.b0111='"+groupid+"') order by SORTID";
		String Sql2="from B01sort where update_user ='"+user.getId()+"' and b0111='"+groupid+"'";
		//当点击上移按钮，判断结果集比他小的一位
		//取得上级组织为groupid的list
		List<B01sort> list = HBUtil.getHBSession().createQuery(Sql1).list();
		//循环
		String tag="1";
		for(int i=0;i<list.size();i++){
			//取出的数据的机构编码等于选中的机构编码
			if(list.get(i).getB0111().equals(groupid)){
				//判断如果是第一条数据 那么什么都不做 TODO
				if(i==0){
					throw new RadowException("机构已到最顶端!");
				}else{
				Choose =list.get(i).getSortid();
				RePlace =list.get(i-1).getSortid();
				Middle = Choose;
				Choose = RePlace ;
				RePlace = Middle;
				String ChooseOrgCod = list.get(i).getB0111();
				String RePlaceCod = list.get(i-1).getB0111();
				DBType cueDBType =DBUtil.getDBType();
				String Sql3 ="";
				String Sql4 ="";
				if(cueDBType==DBType.MYSQL){
					Sql3 = "update B01_sort t set t.updated='1' , t.SORTID ='"+Choose+"' ,t.update_user ='"+user.getId()+"',t.update_date = now() where update_user ='"+user.getId()+"' and t.B0111='"+ChooseOrgCod+"'";
					Sql4 = "update B01_sort t set t.updated='1' , t.SORTID ='"+RePlace+"' ,t.update_user ='"+user.getId()+"',t.update_date = now() where update_user ='"+user.getId()+"' and t.B0111='"+RePlaceCod+"'";
				}else{
					Sql3 = "update B01_sort t set t.updated='1' , t.SORTID ='"+Choose+"' ,t.update_user ='"+user.getId()+"',t.update_date = sysdate where update_user ='"+user.getId()+"' and t.B0111='"+ChooseOrgCod+"'";
					Sql4 = "update B01_sort t set t.updated='1' , t.SORTID ='"+RePlace+"' ,t.update_user ='"+user.getId()+"',t.update_date = sysdate where update_user ='"+user.getId()+"' and t.B0111='"+RePlaceCod+"'";
				}
				B01 b01ChooseOrgCod = SysOrgBS.LoadB01(ChooseOrgCod);
				B01 b01RePlaceCod = SysOrgBS.LoadB01(RePlaceCod);
				B01DTO b01DTOb01ChooseOrgCod =new B01DTO();
				B01DTO b01DTOb01RePlaceCod =new B01DTO();
				HBTransaction trans=null;
				HBSession session = HBUtil.getHBSession();
				try {
					PropertyUtils.copyProperties(b01DTOb01ChooseOrgCod, b01ChooseOrgCod);
					b01DTOb01ChooseOrgCod.setSortid(Choose);
					b01DTOb01ChooseOrgCod.setUpdateuser(user.getId());
					b01DTOb01ChooseOrgCod.setUpdatedate(DateUtil.getTimestamp().getTime());
					PropertyUtils.copyProperties(b01DTOb01RePlaceCod, b01RePlaceCod);
					b01DTOb01RePlaceCod.setSortid(RePlace);
					b01DTOb01RePlaceCod.setUpdateuser(user.getId());
					b01DTOb01RePlaceCod.setUpdatedate(DateUtil.getTimestamp().getTime());
					trans=session.beginTransaction();
					new LogUtil().createLog("24", "B01", list.get(i).getB0111(), list.get(i).getB0101(), "机构排序", new Map2Temp().getLogInfo(b01ChooseOrgCod, b01DTOb01ChooseOrgCod));
					new LogUtil().createLog("24", "B01", list.get(i-1).getB0111(), list.get(i-1).getB0101(), "机构排序", new Map2Temp().getLogInfo(b01RePlaceCod, b01DTOb01RePlaceCod));
					session.createSQLQuery(Sql3).executeUpdate();
					session.createSQLQuery(Sql4).executeUpdate();
				} catch (Exception e) {
					trans.rollback();
					e.printStackTrace();
				} 
				session.flush();
				session.getTransaction().commit();
				this.request.getSession().setAttribute("tag", "1");
				break;
			}
			}
			
		}
//		reloadPage();
		this.getExecuteSG().addExecuteCode("window.realParent.reloadTree()");
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;
	
	}
	
	@PageEvent("DownBtn.onclick")
	public int Down() throws RadowException, AppException{
		CurrentUser user = SysUtil.getCacheCurrentUser();
		Long Choose; //选中 sortid
		Long RePlace ;//替换 sortid
		Long Middle ;//中间sortid
		//查询出同级机构排序字段
		String groupid = this.getPageElement("checkedgroupid").getValue();
		String Sql1="from B01sort where update_user ='"+user.getId()+"' and b0121 in ( select t.b0121 from B01 t where t.b0111='"+groupid+"') order by SORTID";
		String Sql2="from B01sort where update_user ='"+user.getId()+"' and b0111='"+groupid+"'";
		if(groupid==null||groupid.equals("")){
			this.setMainMessage("请选择要移动的机构！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//当点击上移按钮，判断结果集比他大的一位
		//取得上级组织为groupid的list
		List<B01sort> list = HBUtil.getHBSession().createQuery(Sql1).list();
		//循环
		for(int i=0;i<list.size();i++){
			//取出的数据的机构编码等于选中的机构编码
			if(list.get(i).getB0111().equals(groupid)){
				//判断如果是第一条数据 那么什么都不做 TODO
				if(list.size()-1==i){
					throw new RadowException("机构已到最底端!");	
				}else{
				Choose =list.get(i).getSortid();
				RePlace =list.get(i+1).getSortid();
				Middle = Choose;
				Choose = RePlace ;
				RePlace = Middle;
				String ChooseOrgCod = list.get(i).getB0111();
				String RePlaceCod = list.get(i+1).getB0111();
				DBType cueDBType =DBUtil.getDBType();
				String Sql3 ="";
				String Sql4 ="";
				if(cueDBType==DBType.MYSQL){
					Sql3 = "update B01_sort t set t.updated='1' , t.SORTID ='"+Choose+"' ,t.update_user ='"+user.getId()+"',t.update_date = now() where update_user ='"+user.getId()+"' and t.B0111='"+ChooseOrgCod+"'";
					Sql4 = "update B01_sort t set t.updated='1' , t.SORTID ='"+RePlace+"' ,t.update_user ='"+user.getId()+"',t.update_date = now() where update_user ='"+user.getId()+"' and t.B0111='"+RePlaceCod+"'";
				}else{
					Sql3 = "update B01_sort t set t.updated='1' , t.SORTID ='"+Choose+"' ,t.update_user ='"+user.getId()+"',t.update_date = sysdate where update_user ='"+user.getId()+"' and t.B0111='"+ChooseOrgCod+"'";
					Sql4 = "update B01_sort t set t.updated='1' , t.SORTID ='"+RePlace+"' ,t.update_user ='"+user.getId()+"',t.update_date = sysdate where update_user ='"+user.getId()+"' and t.B0111='"+RePlaceCod+"'";
				}
				B01 b01ChooseOrgCod = SysOrgBS.LoadB01(ChooseOrgCod);
				B01 b01RePlaceCod = SysOrgBS.LoadB01(RePlaceCod);
				B01DTO b01DTOb01ChooseOrgCod =new B01DTO();
				B01DTO b01DTOb01RePlaceCod =new B01DTO();
				HBTransaction trans=null;
				HBSession session = HBUtil.getHBSession();
				try {
					PropertyUtils.copyProperties(b01DTOb01ChooseOrgCod, b01ChooseOrgCod);
					b01DTOb01ChooseOrgCod.setSortid(Choose);
					b01DTOb01ChooseOrgCod.setUpdateuser(user.getId());
					b01DTOb01ChooseOrgCod.setUpdatedate(DateUtil.getTimestamp().getTime());
					PropertyUtils.copyProperties(b01DTOb01RePlaceCod, b01RePlaceCod);
					b01DTOb01RePlaceCod.setSortid(RePlace);
					b01DTOb01RePlaceCod.setUpdateuser(user.getId());
					b01DTOb01RePlaceCod.setUpdatedate(DateUtil.getTimestamp().getTime());
					trans=session.beginTransaction();
					new LogUtil().createLog("24", "B01", list.get(i).getB0111(), list.get(i).getB0101(), "机构排序", new Map2Temp().getLogInfo(b01ChooseOrgCod, b01DTOb01ChooseOrgCod));
					new LogUtil().createLog("24", "B01", list.get(i+1).getB0111(), list.get(i+1).getB0101(), "机构排序", new Map2Temp().getLogInfo(b01RePlaceCod, b01DTOb01RePlaceCod));
					session.createSQLQuery(Sql3).executeUpdate();
					session.createSQLQuery(Sql4).executeUpdate();
				} catch (Exception e) {
					trans.rollback();
					e.printStackTrace();
				}
				session.flush();
				session.getTransaction().commit();
				this.request.getSession().setAttribute("tag", "1");
				break;
			}
			}
		}
		this.getExecuteSG().addExecuteCode("window.realParent.reloadTree()");
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("closeBtn.onclick")
	public int close() throws AppException, RadowException {
		if(this.request.getSession().getAttribute("tag").equals("1")){
			this.getExecuteSG().addExecuteCode("window.realParent.reloadTree()");
			this.closeCueWindow("orgSortWin");
		}else{
			this.closeCueWindow("orgSortWin");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("YesBtn.onclick")
	public int Yes() throws AppException, RadowException {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		String sql ="update b01 t set t.sortid=(select b.sortid from b01_sort b where b.b0111=t.b0111 and b.update_user='"+user.getId()+"' and b.updated='1') where exists (select b.sortid from b01_sort b where b.b0111=t.b0111 and b.update_user='"+user.getId()+"' and b.updated='1' )";
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		HBUtil.getHBSession().flush();
		if(this.request.getSession().getAttribute("tag").equals("1")){
			this.getExecuteSG().addExecuteCode("window.realParent.reloadTree()");
			this.closeCueWindow("orgSortWin");
		}else{
			this.closeCueWindow("orgSortWin");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public static String getNextUpEn(String en){  
		if(StringUtils.isEmpty(en)){
			return "A";
		}
		en = en.toUpperCase();
        char lastE = 'a';  
        char st = en.toCharArray()[0];
        if(st<65 || st>90){
        	return "A";
        }
        if(Character.isUpperCase(st)){
            if(en.equals("Z")){
                return "A";
            }
            if(en==null || en.equals("")){ 
                return "A";  
            }
            lastE = 'Z';  
        }else{
            if(en.equals("z")){
                return "a";
            }
            if(en==null || en.equals("")){ 
                return "a";  
            }
            lastE = 'z';  
        }
        int lastEnglish = (int)lastE;      
        char[] c = en.toCharArray();  
        if(c.length>1){  
            return "A";  
        }else{  
            int now = (int)c[0];  
            if(now >= lastEnglish)  
                return null;  
            char uppercase = (char)(now+1);  
            return String.valueOf(uppercase);  
        }  
    }
	
	@PageEvent("YesNew")
	@OpLog
	@Transaction
	public int YesNew(String ids) throws AppException, RadowException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SQLException, IntrospectionException {
		Session sess = HBUtil.getHBSession().getSession();
		CurrentUser user = SysUtil.getCacheCurrentUser();
		ids =ids.substring(0,ids.length()-1);
		String[] arr = ids.split(",");
		//排序时同时删除被转移机构的宏观分析数据--lzy update
		String sql11 = "delete from statistics_age where b0111 like '"+arr[0]+"%'";
		String sql22 = "delete from statistics_educationlevel where b0111 like '"+arr[0]+"%'";
		String sql33 = "delete from statistics_highestpostlevel where b0111 like '"+arr[0]+"%'";
		String sql44 = "delete from statistics_sex where b0111 like '"+arr[0]+"%'";
		HBUtil.getHBSession().createSQLQuery(sql11).executeUpdate();
		HBUtil.getHBSession().createSQLQuery(sql22).executeUpdate();
		HBUtil.getHBSession().createSQLQuery(sql33).executeUpdate();
		HBUtil.getHBSession().createSQLQuery(sql44).executeUpdate();
		B01DTO b01DTOb01ChooseOrgCod =new B01DTO();
		
		//机构排序，新字段 2020年6月4日10:18:28 zoul
		
		for(int i=0;i<arr.length;i++){
			B01 b01 = (B01) sess.createQuery(" from B01 where b0111='"+arr[i]+"'").list().get(0);
			PropertyUtils.copyProperties(b01DTOb01ChooseOrgCod, b01);
			b01.setSortid(Long.valueOf(i+1));
			b01.setUpdateuser(user.getId());
			b01.setUpdatedate(DateUtil.getTimestamp().getTime());
			
			
			//机构排序，新字段 2020年6月4日10:18:28 zoul
			String parentSortPre = "";//排序前缀
			int maxsortNUM = 0;
			if(arr[0].length()==3){//一级机构
				parentSortPre = "";
			}else{
				String parentId = b01.getB0121();
				B01 b01sort = (B01) sess.createQuery(" from B01 where b0111='"+parentId+"'").list().get(0);
				/*String sql = "select max(B0269) from B01 where b0121='"+parentId+"'";
				Object maxsort = sess.createSQLQuery(sql).uniqueResult();
				if(maxsort!=null&&!"".equals(maxsort.toString())){
					String num = maxsort.toString().substring(maxsort.toString().length()-3,(maxsort.toString().length()));
					if(Integer.valueOf(num)<800){
						maxsortNUM = Integer.valueOf(num)+1;
					}
				}*/
				parentSortPre = b01sort.getB0269();
			}
			String before = b01.getB0269();
			String nextLetter = "";
			if(before==null){
				nextLetter = getNextUpEn(null);
			}else{
				String letter = before.substring(before.length()-1, before.length());
				nextLetter = getNextUpEn(letter);
			}
			
			b01.setB0269((parentSortPre+"."+StringUtils.leftPad((i)+"", 3, "0"))+nextLetter);
			if(before!=null){
				moveOrgSort(before, b01.getB0269());
			}
			
			/***********机构排序，新字段 2020年6月4日10:18:28 zoul***************************************************************************/
			
			
			
			sess.update(b01);
			
		}
		new LogUtil("24", "B01", "jgpx", "机构排序", "机构排序", new ArrayList()).start();
		sess.flush();
		this.getExecuteSG().addExecuteCode("window.realParent.reloadTree()");
		//this.closeCueWindow("orgSortWin");
		this.setMainMessage("操作完成！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Transaction
	public static void moveOrgSort(String before ,String after) throws AppException, RadowException{
		//取出本级以及所有下级
		String sql = "update b01 t set t.B0269=replace(t.B0269,'"+before+"','"+after+"') where t.B0269 like '"+before+"%'";
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		
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

	@Override
	public void closeCueWindow(String arg0) {
		// TODO Auto-generated method stub
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('" + arg0 + "').close();");;
	}
}
