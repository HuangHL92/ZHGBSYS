package com.insigma.siis.local.pagemodel.sysorg.org;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
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
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.publicServantManage.WorkUnitsAddPagePageModel;

public class ZjzzyPersonPageModel extends PageModel {
	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String tag= "0";//修改动作0未执行1已执行
	public static String openwin = "";
	public ZjzzyPersonPageModel(){
		
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
		
		openwin = this.getPageElement("subWinIdBussessId").getValue();
		/*this.getExecuteSG().addExecuteCode("odin.ext.getCmp('memberGrid').view.refresh();");*/
		
		
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
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
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
//							+ "\" ,\"cls\" :\"folder\",");
							+ "\" ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
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
							+ "\" ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
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
							+ "\" ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
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
							+ "\" ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
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
	
	//加载右边树
//	@PageEvent("orgTreeJsonDataright")
//	public int getOrgTreeJsonDataright() throws PrivilegeException {
//		String node = this.getParameter("node");
//		String sql="from B01 where B0121='"+node+"' order by sortid";
//		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();//得到当前组织信息
//		//只显示所在的组织及下级组织 不在组织中 则显示全部
//		List<B01> choose = new ArrayList<B01>();
//		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
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
//		String companyOrgImg = request.getContextPath()+"/main/images/icons/companyOrgImg2.png";
//		String insideOrgImg = request.getContextPath()+"/main/images/tree/leaf.gif";
//		String groupOrgImg = request.getContextPath()+"/main/images/tree/folder.gif";
//		String path = companyOrgImg;
//		if (list != null && !list.isEmpty()) {
//			int i = 0;
//			int last = list.size();
//			for (B01 group : list) {
//				if(i==0 && last==1) {
//					if(group.getB0194().equals("2")){
//						path=insideOrgImg;
//					}else if (group.getB0194().equals("3")){
//						path=groupOrgImg;
//					}else{
//						path=companyOrgImg;
//					}
//					jsonStr.append("[{\"text\" :\"" + group.getB0101()
//							+ "\" ,\"id\" :\"" + group.getB0111()
//							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111())
//							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
//					jsonStr.append("\"href\":");
//					jsonStr.append("\"javascript:radow.doEvent('querybyidright','"
//							+ group.getB0111() + "')\"");
//					jsonStr.append("}]");
//				}else if (i == 0) {
//					if(group.getB0194().equals("2")){
//						path=insideOrgImg;
//					}else if (group.getB0194().equals("3")){
//						path=groupOrgImg;
//					}else{
//						path=companyOrgImg;
//					}
//					jsonStr.append("[{\"text\" :\"" + group.getB0101()
//							+ "\" ,\"id\" :\"" + group.getB0111()
//							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111())
//							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
//							jsonStr.append("\"href\":");
//					jsonStr.append("\"javascript:radow.doEvent('querybyidright','"
//							+ group.getB0111() + "')\"");
//					jsonStr.append("}");
//				}else if (i == (last - 1)) {
//					if(group.getB0194().equals("2")){
//						path=insideOrgImg;
//					}else if (group.getB0194().equals("3")){
//						path=groupOrgImg;
//					}else{
//						path=companyOrgImg;
//					} 
//					jsonStr.append(",{\"text\" :\"" + group.getB0101()
//							+ "\" ,\"id\" :\"" + group.getB0111()
//							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111())
//							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
//					jsonStr.append("\"href\":");
//					jsonStr.append("\"javascript:radow.doEvent('querybyidright','"
//							+ group.getB0111() + "')\"");
//					jsonStr.append("}]");
//				} else {
//					if(group.getB0194().equals("2")){
//						path=insideOrgImg;
//					}else if (group.getB0194().equals("3")){
//						path=groupOrgImg;
//					}else{
//						path=companyOrgImg;
//					}
//					jsonStr.append(",{\"text\" :\"" + group.getB0101()
//							+ "\" ,\"id\" :\"" + group.getB0111()
//							+ "\" ,\"leaf\" :" + hasChildren(group.getB0111())
//							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
//					jsonStr.append("\"href\":");
//					jsonStr.append("\"javascript:radow.doEvent('querybyidright','"
//							+ group.getB0111() + "')\"");
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
	
	//点击左侧树事件
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException {
		try {
			String sql2="from B01 where B0111='"+id+"'";
			List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();//得到当前组织信息
			this.getPageElement("turnOut").setValue(groups.get(0).getB0101());
			this.getPageElement("turnOutId").setValue(groups.get(0).getB0111());
		} catch (Exception e) {
			throw new RadowException(e.getMessage());
		}
		this.getPageElement("checkedgroupid").setValue(id);
		//this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//点击右侧树事件
	@PageEvent("querybyidright")
	@NoRequiredValidate
	public int querybyidright(String id) throws RadowException {
		try {
			String sql2="from B01 where B0111='"+id+"'";
			List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();//得到当前组织信息
			this.getPageElement("changeInto").setValue(groups.get(0).getB0101());
			this.getPageElement("changeIntoId").setValue(groups.get(0).getB0111());
		} catch (Exception e) {
			throw new RadowException(e.getMessage());
		}
		this.getPageElement("checkedgroupid").setValue(id);
		//this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//执行按钮
	@Transaction
	@PageEvent("transferSysOrgBtn.onclick")
	public int transferSysOrg() throws RadowException, AppException {
		
		
		String transferType=(String) this.request.getSession().getAttribute("transferType");
		
		
		
		if(transferType.equals("batchTransferPersonnel")){
			
			String type = this.getPageElement("type").getValue();  
			
			if(type == null || type.equals("")){
				this.setMainMessage("请选择人员转移类型！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}
		
		String turnOutSysOrg = this.getPageElement("turnOut").getValue();//转出
		String changeIntoSysOrg = this.getPageElement("changeInto").getValue();//转入
		String turnOutSysOrgId = this.getPageElement("turnOutId").getValue();//转出id
		String changeIntoSysOrgId=this.getPageElement("changeIntoId").getValue();//转入id
		if(turnOutSysOrg.trim().equals("")||turnOutSysOrg==null){
			throw new RadowException("请选择转出机构!");
		}
		if(changeIntoSysOrg.trim().equals("")||changeIntoSysOrg==null){
			throw new RadowException("请选择转入机构!");
		}
		String outsys[]=turnOutSysOrg.split("/");
		String outId[]=turnOutSysOrgId.split(",");
		for(int i=0;i<outId.length;i++){
			if(outId[i].equals(changeIntoSysOrgId)){
				throw new RadowException("转出机构("+outsys[i]+")和转入机构("+changeIntoSysOrg+")不能是同一机构!");
			}
		}//如果转出的机构id和转入的机构id一致，则不能转移；
		
		
		for(int i=0;i<outId.length;i++){
			if(!SysRuleBS.havaRule(outId[i])){
				throw new RadowException("您没有对机构："+outsys[i]+"操作的权限!");
			}
		}//转移机构需要权限
		
		if(transferType.equals("transferSysOrg")){//transferSysOrg隶属关系变更
			String temp="(";
			for(int i=0;i<outId.length;i++){
				temp=temp+" a0201b like '"+outId[i]+"%' or ";
			}
			temp=temp.substring(0, temp.length()-3);
			temp=temp+" )  ";
			String conutsql = "select count(t.a0000) from (select a0000 from a02 where "+temp+"  group by a0000) t";//and a0255='1'
			Object c = HBUtil.getHBSession().createSQLQuery(conutsql).uniqueResult();
			dialog_set("transferSysOrg.move","确认将"+turnOutSysOrg+"机构以及机构下的"+c.toString()+"位公务员转入"+changeIntoSysOrg+"机构下吗？");
		}else{
			
			dialog_set("transferSysOrg.move","确认将"+turnOutSysOrg+"机构下人员转入"+changeIntoSysOrg+"机构下吗？");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//执行按钮
	@Transaction
	@PageEvent("transferSysOrg.move")
	public int transferSysOrgMove() throws RadowException {
		try{
			
			String transferType=(String) this.request.getSession().getAttribute("transferType");
			
			if(transferType.equals("transferSysOrg")){//transferSysOrg隶属关系变更
				
				//整建制转移
				//id的父类编码修改targetId
				//1.判断转入机构，转出机构是否为空。
				//2.如果id=targetId提示“被合并机构和目标机构不能是同一机构”
				//3.循环 （如果目标机构编码的上级，上级，上级---=被转移机构） 提示“目标机构不能是被合并机构的下级！”；
				//4.目标机构如果是内设机构，被合并机构如果是法人单位或者分组机构提示“不能将“法人单位”或“机构分组”整建制转移到“内设机构”！” 
				//update b01 t  t.b0121='targetId' where t.b0111='id' ;
				String turnOutSysOrg = this.getPageElement("turnOutId").getValue();
				System.out.println(turnOutSysOrg +"++++++++++++++++++++++++");
				String outId[]=turnOutSysOrg.split(",");
				String sql="from B01 where ( ";
				String sql11 = "delete from statistics_age where ( ";
				String sql22 = "delete from statistics_educationlevel where ( ";
				String sql33 = "delete from statistics_highestpostlevel where ( ";
				String sql44 = "delete from statistics_sex where ( ";
				for(int i=0;i<outId.length;i++){
					sql=sql+" B0111='"+outId[i]+"' or ";
					sql11=sql11+" b0111 like '"+outId[i]+"%' or ";
					sql22=sql22+" b0111 like '"+outId[i]+"%' or ";
					sql33=sql33+" b0111 like '"+outId[i]+"%' or ";
					sql44=sql44+" b0111 like '"+outId[i]+"%' or ";
				}
				sql=sql.substring(0, sql.length()-3)+" ) ";
				sql11=sql11.substring(0, sql11.length()-3)+" ) ";
				sql22=sql22.substring(0, sql22.length()-3)+" ) ";
				sql33=sql33.substring(0, sql33.length()-3)+" ) ";
				sql44=sql44.substring(0, sql44.length()-3)+" ) ";
				
				String changeIntoSysOrg = this.getPageElement("changeIntoId").getValue();
				
				String sql2="from B01 where B0111='"+changeIntoSysOrg+"'";
				List<B01> list =HBUtil.getHBSession().createQuery(sql).list();
				List<B01> list2 =HBUtil.getHBSession().createQuery(sql2).list();
				//转移时同时删除被转移机构的宏观分析数据--lzy update
			
				HBUtil.getHBSession().createSQLQuery(sql11).executeUpdate();
				HBUtil.getHBSession().createSQLQuery(sql22).executeUpdate();
				HBUtil.getHBSession().createSQLQuery(sql33).executeUpdate();
				HBUtil.getHBSession().createSQLQuery(sql44).executeUpdate();
				String sql4="from B01 where ( ";
				for(int i=0;i<outId.length;i++){
					sql4=sql4+" b0111 like '"+turnOutSysOrg+"%' or ";
				}
				sql4=sql4.substring(0, sql4.length()-3)+" ) ";
				List<B01> list4 =HBUtil.getHBSession().createQuery(sql4).list();
				int changetmp=changeIntoSysOrg.length();
				for(int i=0;i<list4.size();i++){
					int outtemp=list4.get(i).getB0111().length();
					/*if(changeIntoSysOrg.equals(list4.get(i).getB0111())){
						throw new RadowException("转出机构("+list4.get(i).getB0101()+")不能为转入机构("+list2.get(i).getB0101()+")!");
					}*/
					if(outtemp<changetmp&&changeIntoSysOrg.substring(0, outtemp).equals(list4.get(i).getB0111())){
						throw new RadowException("转出机构("+list4.get(i).getB0101()+")不能为转入机构("+list2.get(i).getB0101()+")的上级单位!");
					}
				}
				if(list2.get(0).getB0194().equals("2")){
					for(int i=0;i<list.size();i++){
						if(list.get(i).getB0194().equals("1")){
							throw new RadowException("不能将法人单位("+list.get(i).getB0101()+")整建制转移到内设机构("+list2.get(0).getB0101()+")！");
						}
						if(list.get(i).getB0194().equals("3")){
							throw new RadowException("不能将机构分组("+list.get(i).getB0101()+")整建制转移到内设机构("+list2.get(0).getB0101()+")！");
						}
					}
					
				} 
	//			if(list2.get(0).getB0194().equals("3")){
	//				for(int i=0;i<list.size();i++){
	//					if(list.get(i).getB0194().equals("2")){
	//						throw new RadowException("不能将内设机构("+list.get(i).getB0101()+")整建制转移到机构分组("+list2.get(i).getB0101()+")下！");
	//					}
	//				}
	//			} 
	
				String sql5="select max(t.sortid)+1 sortid from b01 t where t.b0121='"+list2.get(0).getB0111()+"'";
				List list5 =HBUtil.getHBSession().createSQLQuery(sql5).list();
				String sortid="1";
				if(list5.get(0)==null){
					sortid="1";
				}else{
					sortid=list5.get(0).toString();
				}
					System.out.println("循环上层");
				for(int i=0;i<outId.length;i++){
					
					String b0111 =CreateSysOrgBS.selectB0111BySubId(changeIntoSysOrg);
					System.out.println(b0111);
					B01 b01temp =CreateSysOrgBS.LoadB01(outId[i]);
					String sql6="update b01 t set t.b0121='"+changeIntoSysOrg+"',t.sortid='"+sortid+"',t.b0111='"+b0111+"' where t.b0111='"+outId[i]+"'";
					HBUtil.getHBSession().createSQLQuery(sql6).executeUpdate();
					SysRuleBS.updateUserDept(outId[i],b0111);
					batchTransferPersonnel(outId[i],b0111);
					String hasChildren =SysOrgBS.hasChildren(b0111);
					
					if(hasChildren.equals("true")){
						//下级及其下级机构编码变更，和对应的人员转移
						moveOrgAndPeople(outId[i],b0111);
						SysRuleBS.batchUpdateUserDept(outId[i],b0111);
					}
					B01 b01 =new B01();
					B01 b02 =new B01();
					b01.setB0111(outId[i]);
					b01.setB0121(b01temp.getB0121());
					b02.setB0111(b0111);
					b02.setB0121(changeIntoSysOrg);
					try {
						new LogUtil().createLog("25", "B01", b0111, list.get(0).getB0101(), "整建制转移", new Map2Temp().getLogInfo(b01, b02));
					} catch (AppException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				this.request.getSession().setAttribute("tag", "1");
				this.reloadPage();
				this.getExecuteSG().addExecuteCode("window.realParent.reloadTree()");
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				//批量转移人员
				//1.判断转入机构，转出机构是否为空。
				//2.如果id=targetId提示“被合并机构和目标机构不能是同一机构”
				//3.循环 （如果目标机构编码的上级，上级，上级---=被转移机构） 提示“目标机构不能是被合并机构的下级！”；
				//4.当被合并机构选择为机构分组，提示“被合并机构不能是‘机构分组’！” 
				//5.当目标机构选择为机构分组，提示“目标机构不能是‘机构分组’！” 
				//update A02 t set t.A0201B = 'targetId' where  t.A0201B ='id' ;
				String turnOutSysOrg = this.getPageElement("turnOutId").getValue();
				String changeIntoSysOrg = this.getPageElement("changeIntoId").getValue();
				String sql="from B01 where B0111='"+turnOutSysOrg+"'";
				String sql2="from B01 where B0111='"+changeIntoSysOrg+"'";
				List<B01> list =HBUtil.getHBSession().createQuery(sql).list();
				List<B01> list2 =HBUtil.getHBSession().createQuery(sql2).list();
				//转移时同时删除被转移机构的宏观分析数据--lzy update
				String sql11 = "delete from statistics_age where b0111 like '"+turnOutSysOrg+"%'";
				String sql22 = "delete from statistics_educationlevel where b0111 like '"+turnOutSysOrg+"%'";
				String sql33 = "delete from statistics_highestpostlevel where b0111 like '"+turnOutSysOrg+"%'";
				String sql44 = "delete from statistics_sex where b0111 like '"+turnOutSysOrg+"%'";
				HBUtil.getHBSession().createSQLQuery(sql11).executeUpdate();
				HBUtil.getHBSession().createSQLQuery(sql22).executeUpdate();
				HBUtil.getHBSession().createSQLQuery(sql33).executeUpdate();
				HBUtil.getHBSession().createSQLQuery(sql44).executeUpdate();
				if(list.get(0).getB0194().equals("3")){
					throw new RadowException("转出机构不能是‘机构分组’！");
				}
				if(list2.get(0).getB0194().equals("3")){
					throw new RadowException("转入机构不能是‘机构分组’！");
				}
				//修改人员组织语句
				int count;
				String type = this.getPageElement("type").getValue();
				if("1".equals(type)){
					count =batchTransferPersonnelBypeople(turnOutSysOrg,changeIntoSysOrg);
				}else{
					count =batchTransferPersonnelBypeople1(turnOutSysOrg,changeIntoSysOrg);
				}
				
				
				
				this.setMainMessage("从"+list.get(0).getB0101()+"转入"+list2.get(0).getB0101()+count+"位公务员");
				B01 b01 =new B01();
				B01 b02 =new B01();
				b01.setB0111(turnOutSysOrg);
				b02.setB0111(changeIntoSysOrg);
				try {
					new LogUtil().createLog("26", "B01", list.get(0).getB0111(), list.get(0).getB0101(), "人员转移", new Map2Temp().getLogInfo(b01, b02));
				} catch (AppException e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			System.out.println("失败");
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}	
	
	//关闭按钮
	@PageEvent("closeBtn.onclick")
	public int close() throws RadowException {
		this.getExecuteSG().addExecuteCode("window.realParent.reloadTree();");
		if(openwin.equals("zjzzy")){
			this.closeCueWindow("transferSysOrgWin");
		}else{
			this.closeCueWindow("batchTransferPersonnelWin");
		}
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
	
	@Transaction
	public int batchTransferPersonnel(String outId,String inId){
		String sql="update a02 set a0201b = '"+inId+"' where a0201b='"+outId+"'";
		String conutsql = "select count(t.a0000) from (select a0000 from a02 where a0201b='"+outId+"' group by a0000) t";
		Object c = HBUtil.getHBSession().createSQLQuery(conutsql).uniqueResult();
		int count = HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		return Integer.valueOf(c.toString());
	}
	
	//批量修改人员的任职信息（修改原记录，增加新记录）
	public int batchTransferPersonnelBypeople(String outId,String inId) throws RadowException{
		B01 b01 = CreateSysOrgBS.LoadB01(inId);
		String insertsql = "";
		if(DBType.ORACLE==DBUtil.getDBType()){
			insertsql = "insert into a02(A0000,A0200,A0201,A0201A,A0201B,A0201C,A0201D,A0201E,A0204,A0207,A0209,A0215A," +
			"A0215B,A0216A,A0219,A0219W,A0221,A0221W,A0222,A0223,A0225,A0229,A0243,A0245,A0247,A0251,A0251B,A0255,A0256," +
			"A0256A,A0256B,A0256C,A0259,A0265,A0267,A0271,A0277,A0281,A0284,A0288,A0289,A0295,A0299,A4901,A4904,A4907," +
			"UPDATED,WAGE_USED,A0221A,B0239,B0238) select A0000,sys_guid(),'','"+b01.getB0101()+"','"+inId+"','"+b01.getB0104()+"','','','','','','',"+
			"'','','','','','','99','','','','','','','','','1','','','','','','','','','',A0281,'','','','','','','','',"+
			"'','','','','' from a02 where a0201b='"+outId+"' ";//and a0255='1'
		}else{
			insertsql = "insert into a02(A0000,A0200,A0201,A0201A,A0201B,A0201C,A0201D,A0201E,A0204,A0207,A0209,A0215A," +
			"A0215B,A0216A,A0219,A0219W,A0221,A0221W,A0222,A0223,A0225,A0229,A0243,A0245,A0247,A0251,A0251B,A0255,A0256," +
			"A0256A,A0256B,A0256C,A0259,A0265,A0267,A0271,A0277,A0281,A0284,A0288,A0289,A0295,A0299,A4901,A4904,A4907," +
			"UPDATED,WAGE_USED,A0221A,B0239,B0238) select A0000,uuid(),NULL,'"+b01.getB0101()+"','"+inId+"','"+b01.getB0104()+"'," +
			"NULL,NULL,NULL,NULL,NULL,a0215a,NULL,NULL,NULL,NULL,NULL,NULL,'99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1'," +
			"NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,A0281,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,"+
			"NULL,NULL,NULL,NULL,NULL from a02 where a0201b='"+outId+"' ";//and a0255='1'
		}
		
//		if(DBType.ORACLE==DBUtil.getDBType()){
//			insertsql = "insert into a02(A0000,A0200,A0201,A0201A,A0201B,"
//					+ "A0201C,A0201D,A0201E,A0204,A0207,"
//					+ "A0209,A0215A,A0215B,A0216A,A0219,"
//					+ "A0219W,A0221,A0221W,A0222,A0223,"
//					+ "A0225,A0229,A0243,A0245,A0247,"
//					+ "A0251,A0251B,A0255,A0256,A0256A,"
//					+ "A0256B,A0256C,A0259,A0265,A0267,"
//					+ "A0271,A0277,A0281,A0284,A0288,"
//					+ "A0289,A0295,A0299,A4901,A4904,"
//					+ "A4907,UPDATED,WAGE_USED,A0221A,B0239,"
//					+ "B0238) "
//					+ "select A0000,sys_guid(),A0201,'"+b01.getB0101()+"','"+inId+"',"
//							+ "'"+b01.getB0104()+"',A0201D,A0201E,A0204,A0207,"
//							+ "A0209,A0215A,A0215B,A0216A,A0219,"
//							+ "A0219W,A0221,A0221W,A0222,A0223,"
//							+ "A0225,A0229,A0243,A0245,A0247,"
//							+ "A0251,A0251B,A0255,A0256,A0256A,"
//							+ "A0256B,A0256C,A0259,A0265,A0267,"
//							+ "A0271,A0277,A0281,A0284,A0288,"
//							+ "A0289,A0295,A0299,A4901,A4904,"
//							+ "A4907,UPDATED,WAGE_USED,A0221A,B0239,"
//							+ "B0238 from a02 where a0201b='"+outId+"' and a0255='1'";
//		}else{
//			insertsql = "insert into a02(A0000,A0200,A0201,A0201A,A0201B,"
//					+ "A0201C,A0201D,A0201E,A0204,A0207,"
//					+ "A0209,A0215A,A0215B,A0216A,A0219,"
//					+ "A0219W,A0221,A0221W,A0222,A0223,"
//					+ "A0225,A0229,A0243,A0245,A0247,"
//					+ "A0251,A0251B,A0255,A0256,A0256A,"
//					+ "A0256B,A0256C,A0259,A0265,A0267,"
//					+ "A0271,A0277,A0281,A0284,A0288,"
//					+ "A0289,A0295,A0299,A4901,A4904,"
//					+ "A4907,UPDATED,WAGE_USED,A0221A,B0239,"
//					+ "B0238) select A0000,uuid(),A0201,'"+b01.getB0101()+"','"+inId+"',"
//							+ "'"+b01.getB0104()+"',A0201D,A0201E,A0204,A0207,"
//							+ "A0209,A0215A,A0215B,A0216A,A0219,"
//							+ "A0219W,A0221,A0221W,A0222,A0223,"
//							+ "A0225,A0229,A0243,A0245,A0247,"
//							+ "A0251,A0251B,A0255,A0256,A0256A,"
//							+ "A0256B,A0256C,A0259,A0265,A0267,"
//							+ "A0271,A0277,A0281,A0284,A0288,"
//							+ "A0289,A0295,A0299,A4901,A4904,"
//							+ "A4907,UPDATED,WAGE_USED,A0221A,B0239,"
//							+ "B0238 from a02 where a0201b='"+outId+"' and a0255='1'";
//		}
		HBUtil.getHBSession().createSQLQuery(insertsql).executeUpdate();//插入新机构的任职信息记录
		//String sql="update a02 set a0201a='"+b01.getB0101()+"', a0201c='"+b01.getB0104()+"', a0201b = '"+inId+"' where a0201b='"+outId+"' and a0255='1'";
		String sql="update a02 set a0255 = '0' , a0281='false' "//人员任职状态以，a0281 为准，a0255可有可无
				+ " where a0201b='"+outId+"' and a0255='1'";//将原任职信息记录的机构状态更新为已免
		String conutsql = "select count(t.a0000) from (select a0000 from a02 where a0201b='"+outId+"'  group by a0000) t";//and a0255='1'
		Object c = HBUtil.getHBSession().createSQLQuery(conutsql).uniqueResult();
		int count = HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		WorkUnitsAddPagePageModel workUnitsAddPagePageModel =new WorkUnitsAddPagePageModel();
		String a0000s="select t.a0000 from a01 t,a02 b where t.a0000=b.a0000 and  b.a0201b = '"+inId+"'";
		List<String> list = HBUtil.getHBSession().createSQLQuery(a0000s).list();
		for(int i=0;i<list.size();i++){
			workUnitsAddPagePageModel.UpdateTitleBtn(list.get(i));
		}
		//更新人员查看控制记录
		HBUtil.getHBSession().createSQLQuery("update competence_userperson set b0111 = '"+inId+"' where b0111 = '"+outId+"'").executeUpdate();
		return Integer.valueOf(c.toString());
	}
	
	
	
	
	//批量修改人员的任职信息（更新原记录）
	public int batchTransferPersonnelBypeople1(String outId,String inId) throws RadowException{
		B01 b01 = CreateSysOrgBS.LoadB01(inId);
	   //String sql="update a02 set a0201a='"+b01.getB0101()+"', a0201c='"+b01.getB0104()+"', a0201b = '"+inId+"' where a0201b='"+outId+"' and a0255='1'";
		String sql="update a02 set a0201a = (select b0101 from b01 where b0111='"+inId+"') , a0201b='"+inId+"' "//更新任职信息
				+ " where a0201b='"+outId+"' and a0255='1'";//将在任的人员更新任职信息和任职代码
		String conutsql = "select count(t.a0000) from (select a0000 from a02 where a0201b='"+outId+"'  group by a0000) t";//and a0255='1'
		Object c = HBUtil.getHBSession().createSQLQuery(conutsql).uniqueResult();
		int count = HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		WorkUnitsAddPagePageModel workUnitsAddPagePageModel =new WorkUnitsAddPagePageModel();
		String a0000s="select t.a0000 from a01 t,a02 b where t.a0000=b.a0000 and  b.a0201b = '"+inId+"'";
		List<String> list = HBUtil.getHBSession().createSQLQuery(a0000s).list();
		for(int i=0;i<list.size();i++){
			workUnitsAddPagePageModel.UpdateTitleBtn(list.get(i));
		}
		//更新人员查看控制记录
		HBUtil.getHBSession().createSQLQuery("update competence_userperson set b0111 = '"+inId+"' where b0111 = '"+outId+"'").executeUpdate();
		return Integer.valueOf(c.toString());
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
	
	@Transaction
	public void moveOrgAndPeople(String before ,String after) throws AppException, RadowException{
		//取出本级以及所有下级
		String sql = "update b01 t set t.b0111=replace(t.b0111,'"+before+"','"+after+"'),t.b0121 =replace(t.b0121,'"+before+"','"+after+"') where t.b0111 like '"+before+"%'";
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		System.out.println(sql);
		String sqlA02 = "update a02 t set t.a0201b=replace(t.a0201b,'"+before+"','"+after+"')  where t.a0201b like '"+before+"%'";
		HBUtil.getHBSession().createSQLQuery(sqlA02).executeUpdate();
		System.out.println(sqlA02);
		//更新a0195  id应该是选中机构的上级单位需要变更
		String sqlA01 = "update a01 t set t.a0195=replace(t.a0195,'"+before+"','"+after+"')  where t.a0195 like '"+before+"%'";
	    HBUtil.getHBSession().createSQLQuery(sqlA01).executeUpdate();
		System.out.println(sqlA01);
		System.out.println(before+"------------------------"+after);
		
		//历史和离退人员所在机构
		String sqlOrgId = "update a01 t set t.orgid=replace(t.orgid,'"+before+"','"+after+"')  where t.orgid like '"+before+"%'";
		HBUtil.getHBSession().createSQLQuery(sqlOrgId).executeUpdate();
	}
	
	@Override
	public void closeCueWindow(String arg0) {
		// TODO Auto-generated method stub
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('" + arg0 + "').close();");;
	}

}

