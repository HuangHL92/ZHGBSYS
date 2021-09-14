package com.insigma.siis.local.pagemodel.sysorg.org;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

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
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.publicServantManage.WorkUnitsAddPagePageModel;

public class OutFilePageModel extends PageModel {
	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String tag= "0";//�޸Ķ���0δִ��1��ִ��
	public static String openwin = "";
	public OutFilePageModel(){
		
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
		this.getExecuteSG().addExecuteCode("odin.ext.getCmp('memberGrid').view.refresh();");
//		this.getExecuteSG().addExecuteCode("odin.ext.getCmp('memberGrid').store.reload();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//��ʼ����֯������
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		String sql="from B01 where B0121='"+node+"' order by sortid";
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();//�õ���ǰ��֯��Ϣ
		//ֻ��ʾ���ڵ���֯���¼���֯ ������֯�� ����ʾȫ��
		List<B01> choose = new ArrayList<B01>();
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String sql2="from B01 where B0111='"+node+"'";
		List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();//�õ���ǰ��֯��Ϣ
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
	
	//�����ұ���
	@PageEvent("orgTreeJsonDataright")
	public int getOrgTreeJsonDataright() throws PrivilegeException {
		String node = this.getParameter("node");
		String sql="from B01 where B0121='"+node+"' order by sortid";
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();//�õ���ǰ��֯��Ϣ
		//ֻ��ʾ���ڵ���֯���¼���֯ ������֯�� ����ʾȫ��
		List<B01> choose = new ArrayList<B01>();
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String sql2="from B01 where B0111='"+node+"'";
		List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();//�õ���ǰ��֯��Ϣ
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
							+ " ,\"cls\" :\"folder\",\"icon\":\""+path+"\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyidright','"
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
					jsonStr.append("\"javascript:radow.doEvent('querybyidright','"
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
					jsonStr.append("\"javascript:radow.doEvent('querybyidright','"
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
					jsonStr.append("\"javascript:radow.doEvent('querybyidright','"
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
	
	//���������¼�
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException {
		try {
			String sql2 = "select b0101,b0111 from cpb01 where B0111='"+id+"'";
//			List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();//�õ���ǰ��֯��Ϣ
			List<Object[]> groups = HBUtil.getHBSession().createSQLQuery(sql2).list();
			System.out.println(groups.get(0)[0].toString());
			System.out.println(groups.get(0)[1].toString());
			this.getPageElement("turnOut").setValue(groups.get(0)[0].toString());
			this.getPageElement("turnOutId").setValue(groups.get(0)[1].toString());
			
			/*String sql2="from CPB01 where B0111='"+id+"'";
			List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();//�õ���ǰ��֯��Ϣ
			System.out.println(groups.get(0).getB0101());
			System.out.println(groups.get(0).getB0111());
			this.getPageElement("turnOut").setValue(groups.get(0).getB0101());
			this.getPageElement("turnOutId").setValue(groups.get(0).getB0111());*/
		} catch (Exception e) {
			throw new RadowException(e.getMessage());
		}
		this.getPageElement("checkedgroupid").setValue(id);
		//this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//����Ҳ����¼�
	@PageEvent("querybyidright")
	@NoRequiredValidate
	public int querybyidright(String id) throws RadowException {
		try {
			String sql2="from B01 where B0111='"+id+"'";
			List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();//�õ���ǰ��֯��Ϣ
			this.getPageElement("changeInto").setValue(groups.get(0).getB0101());
			this.getPageElement("changeIntoId").setValue(groups.get(0).getB0111());
		} catch (Exception e) {
			throw new RadowException(e.getMessage());
		}
		this.getPageElement("checkedgroupid").setValue(id);
		//this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ɾ��������ť
	@PageEvent("Btndelete.onclick")
	public int Btndelete() throws RadowException, PrivilegeException {
		String turnOutSysOrgId = this.getPageElement("turnOutId").getValue();
		if(turnOutSysOrgId ==null ||"".equals(turnOutSysOrgId)){
			throw new RadowException("��ѡɾ������!");
		}
		request.getSession().setAttribute("turnOutSysOrgId", turnOutSysOrgId);
		String transferType=(String) this.request.getSession().getAttribute("transferType");
		String turnOutSysOrg = this.getPageElement("turnOut").getValue();
		if(transferType.equals("transferSysOrg")){
			dialog_set("buttondelete","ȷ�Ͻ�"+turnOutSysOrg+"����ɾ����");
		}

		return EventRtnType.NORMAL_SUCCESS;
	}
	//ɾ����ť
	@PageEvent("buttondelete")
	public int ButtonDelete() throws RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		String turnOutSysOrgId = (String) request.getSession().getAttribute("turnOutSysOrgId");
		//��cpb01 ����ɾ���û���
		String sql="delete from cpb01 where b0111 like '"+turnOutSysOrgId+"'";
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		//��Ȩ�ޱ���ɾ���û���
		String sql1="delete from COMPETENCE_USERDEPT where b0111 like '"+turnOutSysOrgId+"'";
		HBUtil.getHBSession().createSQLQuery(sql1).executeUpdate();
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}	
	
	
	//ִ�а�ť
	@PageEvent("transferSysOrgBtn.onclick")
	public int transferSysOrg() throws RadowException, PrivilegeException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		String b11b114 = "";
		String cpb11b114 = "";
		String turnOutSysOrgId = this.getPageElement("turnOutId").getValue();
		if(turnOutSysOrgId ==null ||"".equals(turnOutSysOrgId)){
			throw new RadowException("��ѡ��ָ�����!");
		}
		String sql = "select b0101,b0114 from b01 where b0111 = '"+turnOutSysOrgId+"'";
		String sql1 = "select b0101,b0114 from cpb01 where b0111 = '"+turnOutSysOrgId+"'";
		String sql2 = "select b0121 from cpb01 where b0111 = '"+turnOutSysOrgId+"'";
		List<Object[]> b0101b0114 = HBUtil.getHBSession().createSQLQuery(sql).list();
		List<Object[]> cpb0101b0114 = HBUtil.getHBSession().createSQLQuery(sql1).list();
		List cpb0121 = HBUtil.getHBSession().createSQLQuery(sql2).list();
		String  b0121cp = (String) cpb0121.get(0);
		/*String upb0121 = b0121cp.substring(0, b0121cp.lastIndexOf("."));
		System.out.println(b0121cp.substring(0, b0121cp.lastIndexOf(".")));*/
		if(b0101b0114.size()>0){
			String b0101 = (String)b0101b0114.get(0)[0];
			String b0114 = (String)b0101b0114.get(0)[0];
			String cpb0101 = (String)cpb0101b0114.get(0)[0];
			String cpb0114 = (String)cpb0101b0114.get(0)[0];
			b11b114 = b0101+b0114;
			cpb11b114 = cpb0101+cpb0114;
			if(cpb11b114.equals(b11b114)){
				throw new RadowException("�û����Ѵ��ڲ��ָܻ���");
			}
		}
		String changeIntoSysOrg = "";
		changeIntoSysOrg = this.getPageElement("changeInto").getValue();
		if(changeIntoSysOrg == null || "".equals(changeIntoSysOrg)){
			
			String sql3 = "select b0111 from b01 where b0111 = '"+b0121cp+"'";
			int upb0111 = HBUtil.getHBSession().createSQLQuery(sql3).executeUpdate();
			if(upb0111>0){
				String sql4 = "insert into B01 ( b0101,b0111,b0114,B0117,B0121,B0124,B0127,B0131,b0107,b0194,sortid) select  b.b0101,b.b0111,b.b0114,b.B0117,b.B0121,b.B0124,b.B0127,b.B0131,b.b0107,b.b0194,b.sortid from cpb01 b where b.b0111 like  '"+turnOutSysOrgId+"%'";
				int mess = HBUtil.getHBSession().createSQLQuery(sql4).executeUpdate();
				CurrentUser user = SysUtil.getCacheCurrentUser();
//				String sql21 = "insert  into COMPETENCE_USERDEPT (userdeptid,userid,b0111, type)values('"+uuid+"','"+user.getId()+"','"+turnOutSysOrgId+".%','1' )";
//				HBUtil.getHBSession().createSQLQuery(sql21).executeUpdate();
				
				String sql21 =  "select b0111  from cpb01 where b0111 like '"+turnOutSysOrgId+"%'";
				List<Object> sqllist1 = HBUtil.getHBSession().createSQLQuery(sql21).list();
				for(Object o:sqllist1){
					String uuid = UUID.randomUUID().toString().replace("-", "");
					String sql212 = "insert  into COMPETENCE_USERDEPT (userdeptid,userid,b0111, type)values('"+uuid+"','"+user.getId()+"','"+o+"','1' )";
					HBUtil.getHBSession().createSQLQuery(sql212).executeUpdate();
				}
				String sql313="delete from cpb01 where b0111 like '"+turnOutSysOrgId+"%'";
				HBUtil.getHBSession().createSQLQuery(sql313).executeUpdate();
				this.reloadPage();
				this.getExecuteSG().addExecuteCode("window.realParent.reloadTree()");
				this.setMainMessage("����ɹ�");
			}else{
				request.getSession().setAttribute("turnOutSysOrgId", turnOutSysOrgId);
				String transferType=(String) this.request.getSession().getAttribute("transferType");
				String turnOutSysOrg = this.getPageElement("turnOut").getValue();
				
				if(turnOutSysOrg.trim().equals("")||turnOutSysOrg==null){
					throw new RadowException("��ѡ��ת������!");
				}
				if(changeIntoSysOrg.trim().equals("")||changeIntoSysOrg==null){
					throw new RadowException("�ϼ��������������ֶ�ѡ���ϼ�����!");
				}
				if(turnOutSysOrg.equals(changeIntoSysOrg)){
					throw new RadowException("ת��������ת�����������ͬһ����!");
				}
			/*	if(!SysRuleBS.havaRule(turnOutSysOrgId)){
					throw new RadowException("���޴�Ȩ��!");
				}*/
				if(transferType.equals("transferSysOrg")){
					String conutsql = "select count(t.a0000) from (select a0000 from a02 where a0201b='"+turnOutSysOrgId+"%' and a0255='1' group by a0000) t";
					Object c = HBUtil.getHBSession().createSQLQuery(conutsql).uniqueResult();
//					dialog_set("transferSysOrg.move","ȷ�Ͻ�"+turnOutSysOrg+"�����Լ������µ�"+c.toString()+"λ����Աת��"+changeIntoSysOrg+"��������");
					dialog_set("transferSysOrg.move","ȷ�Ͻ�"+turnOutSysOrg+"����ת�뵽"+changeIntoSysOrg+"��������");
				}else{
//					dialog_set("transferSysOrg.move","ȷ�Ͻ�"+turnOutSysOrg+"��������Աת��"+changeIntoSysOrg+"��������");
				}
//				String turnOutSysOrgId = this.getPageElement("turnOutId").getValue();
				//this.getExecuteSG().addExecuteCode("odin.ext.getCmp('memberGrid').view.refresh();");
				//this.getExecuteSG().addExecuteCode("odin.ext.getCmp('memberGrid').store.reload();");
				
			}
		}else{

			request.getSession().setAttribute("turnOutSysOrgId", turnOutSysOrgId);
			String transferType=(String) this.request.getSession().getAttribute("transferType");
			String turnOutSysOrg = this.getPageElement("turnOut").getValue();
			
			if(turnOutSysOrg.trim().equals("")||turnOutSysOrg==null){
				throw new RadowException("��ѡ��ת������!");
			}
			if(changeIntoSysOrg.trim().equals("")||changeIntoSysOrg==null){
				throw new RadowException("�ϼ��������������ֶ�ѡ���ϼ�����!");
			}
			if(turnOutSysOrg.equals(changeIntoSysOrg)){
				throw new RadowException("ת��������ת�����������ͬһ����!");
			}
		/*	if(!SysRuleBS.havaRule(turnOutSysOrgId)){
				throw new RadowException("���޴�Ȩ��!");
			}*/
			if(transferType.equals("transferSysOrg")){
				String conutsql = "select count(t.a0000) from (select a0000 from a02 where a0201b='"+turnOutSysOrgId+"%' and a0255='1' group by a0000) t";
				Object c = HBUtil.getHBSession().createSQLQuery(conutsql).uniqueResult();
//				dialog_set("transferSysOrg.move","ȷ�Ͻ�"+turnOutSysOrg+"�����Լ������µ�"+c.toString()+"λ����Աת��"+changeIntoSysOrg+"��������");
				dialog_set("transferSysOrg.move","ȷ�Ͻ�"+turnOutSysOrg+"����ת�뵽"+changeIntoSysOrg+"��������");
			}else{
//				dialog_set("transferSysOrg.move","ȷ�Ͻ�"+turnOutSysOrg+"��������Աת��"+changeIntoSysOrg+"��������");
			}
//			String turnOutSysOrgId = this.getPageElement("turnOutId").getValue();
			//this.getExecuteSG().addExecuteCode("odin.ext.getCmp('memberGrid').view.refresh();");
			//this.getExecuteSG().addExecuteCode("odin.ext.getCmp('memberGrid').store.reload();");
			
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//ִ�а�ť
	@PageEvent("transferSysOrg.move")
	public int transferSysOrgMove() throws RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException {
		//����ת����Ա
		//1.�ж�ת�������ת�������Ƿ�Ϊ�ա�
		//2.���id=targetId��ʾ�����ϲ�������Ŀ�����������ͬһ������
		//3.ѭ�� �����Ŀ�����������ϼ����ϼ����ϼ�---=��ת�ƻ����� ��ʾ��Ŀ����������Ǳ��ϲ��������¼�������
		//4.�����ϲ�����ѡ��Ϊ�������飬��ʾ�����ϲ����������ǡ��������顯���� 
		//5.��Ŀ�����ѡ��Ϊ�������飬��ʾ��Ŀ����������ǡ��������顯���� 
		//update A02 t set t.A0201B = 'targetId' where  t.A0201B ='id' ;
		
		//������ת��
		//id�ĸ�������޸�targetId
		//1.�ж�ת�������ת�������Ƿ�Ϊ�ա�
		//2.���id=targetId��ʾ�����ϲ�������Ŀ�����������ͬһ������
		//3.ѭ�� �����Ŀ�����������ϼ����ϼ����ϼ�---=��ת�ƻ����� ��ʾ��Ŀ����������Ǳ��ϲ��������¼�������
		//4.Ŀ����������������������ϲ���������Ƿ��˵�λ���߷��������ʾ�����ܽ������˵�λ���򡰻������顱������ת�Ƶ���������������� 
		//update b01 t  t.b0121='targetId' where t.b0111='id' ;
		String turnOutSysOrgId = (String) request.getSession().getAttribute("turnOutSysOrgId");
		CurrentUser user = SysUtil.getCacheCurrentUser();
		
		/*String sql21 = "insert  into COMPETENCE_USERDEPT (userdeptid,userid,b0111, type)values('"+uuid+"','"+user.getId()+"','"+turnOutSysOrgId+"%','1' )";
		HBUtil.getHBSession().createSQLQuery(sql21).executeUpdate();*/
		String sql21 =  "select b0111  from cpb01 where b0111 like '"+turnOutSysOrgId+"%'";
		List<Object> sqllist1 = HBUtil.getHBSession().createSQLQuery(sql21).list();
		for(Object o:sqllist1){
			String uuid = UUID.randomUUID().toString().replace("-", "");

			String sql212 = "insert  into COMPETENCE_USERDEPT (userdeptid,userid,b0111, type)values('"+uuid+"','"+user.getId()+"','"+o+"','1' )";
			HBUtil.getHBSession().createSQLQuery(sql212).executeUpdate();
		}
		
		String sql223 = "insert into b01(b0101,b0107,b0111,b0121,b0117,b0114,b0194) select b0101,b0107,b0111,b0121,b0117,b0114,b0194 from cpb01 where b0111 like '"+turnOutSysOrgId+"%' ";
		HBUtil.getHBSession().createSQLQuery(sql223).executeUpdate();
		
		String sql313="delete from cpb01 where b0111 like '"+turnOutSysOrgId+"%'";
		HBUtil.getHBSession().createSQLQuery(sql313).executeUpdate();
		
		String turnOutSysOrg = this.getPageElement("turnOutId").getValue();
		String changeIntoSysOrg = this.getPageElement("changeIntoId").getValue();
		String sql="from B01 where B0111='"+turnOutSysOrg+"'";
		String sql2="from B01 where B0111='"+changeIntoSysOrg+"'";
		List<B01> list =HBUtil.getHBSession().createQuery(sql).list();
		List<B01> list2 =HBUtil.getHBSession().createQuery(sql2).list();
		//ת��ʱͬʱɾ����ת�ƻ����ĺ�۷�������--lzy update
		String sql11 = "delete from statistics_age where b0111 like '"+turnOutSysOrg+"%'";
		String sql22 = "delete from statistics_educationlevel where b0111 like '"+turnOutSysOrg+"%'";
		String sql33 = "delete from statistics_highestpostlevel where b0111 like '"+turnOutSysOrg+"%'";
		String sql44 = "delete from statistics_sex where b0111 like '"+turnOutSysOrg+"%'";
		HBUtil.getHBSession().createSQLQuery(sql11).executeUpdate();
		HBUtil.getHBSession().createSQLQuery(sql22).executeUpdate();
		HBUtil.getHBSession().createSQLQuery(sql33).executeUpdate();
		HBUtil.getHBSession().createSQLQuery(sql44).executeUpdate();
		String transferType=(String) this.request.getSession().getAttribute("transferType");
		if(transferType.equals("transferSysOrg")){
			String sql4="select b0111 from B01 where  b0111 like '"+turnOutSysOrg+"%'";
			List list4 =HBUtil.getHBSession().createSQLQuery(sql4).list();
			for(int i=0;i<list4.size();i++){
				if(changeIntoSysOrg.equals(list4.get(i).toString())){
					throw new RadowException("ת����������Ϊת��������ϼ���λ!");
				}
			}
			if(list2.get(0).getB0194().equals("2")){
				if(list.get(0).getB0194().equals("1")||list.get(0).getB0194().equals("3")){
					throw new RadowException("���ܽ�'���˵�λ'��'��������'ת�Ƶ�'�������'��");
				}
			} 

			String sql5="select max(t.sortid)+1 sortid from b01 t where t.b0121='"+list2.get(0).getB0111()+"'";
			List list5 =HBUtil.getHBSession().createSQLQuery(sql5).list();
			String sortid="1";
			if(list5.get(0)==null){
				sortid="1";
			}else{
				sortid=list5.get(0).toString();
			}
			String b0111 =CreateSysOrgBS.selectB0111BySubId(changeIntoSysOrg);
			B01 b01temp =CreateSysOrgBS.LoadB01(turnOutSysOrg);
			String sql6="update b01 t set t.b0121='"+changeIntoSysOrg+"',t.sortid='"+sortid+"',t.b0111='"+b0111+"' where t.b0111='"+turnOutSysOrg+"'";
			HBUtil.getHBSession().createSQLQuery(sql6).executeUpdate();
			SysRuleBS.updateUserDept(turnOutSysOrg,b0111);
			batchTransferPersonnel(turnOutSysOrg,b0111);
			String hasChildren =SysOrgBS.hasChildren(b0111);
			if(hasChildren.equals("true")){
				//�¼������¼��������������Ͷ�Ӧ����Աת��
				moveOrgAndPeople(turnOutSysOrg,b0111);
				SysRuleBS.batchUpdateUserDept(turnOutSysOrg,b0111);
			}
			B01 b01 =new B01();
			B01 b02 =new B01();
			b01.setB0111(turnOutSysOrg);
			b01.setB0121(b01temp.getB0121());
			b02.setB0111(b0111);
			b02.setB0121(changeIntoSysOrg);
			try {
				new LogUtil().createLog("25", "B01", b0111, list.get(0).getB0101(), "������ת��", new Map2Temp().getLogInfo(b01, b02));
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.request.getSession().setAttribute("tag", "1");
			this.reloadPage();
			this.getExecuteSG().addExecuteCode("window.realParent.reloadTree()");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			if(list.get(0).getB0194().equals("3")){
				throw new RadowException("ת�����������ǡ��������顯��");
			}
			if(list2.get(0).getB0194().equals("3")){
				throw new RadowException("ת����������ǡ��������顯��");
			}
			//�޸���Ա��֯���
			/*int count =batchTransferPersonnelBypeople(turnOutSysOrg,changeIntoSysOrg);
			this.setMainMessage("��"+list.get(0).getB0101()+"ת��"+list2.get(0).getB0101()+count+"λ����Ա");
			B01 b01 =new B01();
			B01 b02 =new B01();
			b01.setB0111(turnOutSysOrg);
			b02.setB0111(changeIntoSysOrg);
			try {
//				new LogUtil().createLog("26", "B01", list.get(0).getB0111(), list.get(0).getB0101(), "��Աת��", new Map2Temp().getLogInfo(b01, b02));
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

		}
		return EventRtnType.NORMAL_SUCCESS;
	}	
	
	//�رհ�ť
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
		ne.setNextEventValue(NextEventValue.YES); // �������Ϣ�����ȷ��ʱ�������´��¼�
		ne.setNextEventName(fnDelte);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// �������Ϣ�����ȡ��ʱ�������´��¼�
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage(strHint); // ������ʾ��Ϣ
	}
	
	public int batchTransferPersonnel(String outId,String inId){
		String sql="update a02 set a0201b = '"+inId+"' where a0201b='"+outId+"'";
		String conutsql = "select count(t.a0000) from (select a0000 from a02 where a0201b='"+outId+"' group by a0000) t";
		Object c = HBUtil.getHBSession().createSQLQuery(conutsql).uniqueResult();
		int count = HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		return Integer.valueOf(c.toString());
	}
	
	//�����޸���Ա����ְ��Ϣ���޸�ԭ��¼�������¼�¼��
	public int batchTransferPersonnelBypeople(String outId,String inId) throws RadowException{
		B01 b01 = CreateSysOrgBS.LoadB01(inId);
		String insertsql = "";
		if(DBType.ORACLE==DBUtil.getDBType()){
			insertsql = "insert into a02(A0000,A0200,A0201,A0201A,A0201B,A0201C,A0201D,A0201E,A0204,A0207,A0209,A0215A," +
			"A0215B,A0216A,A0219,A0219W,A0221,A0221W,A0222,A0223,A0225,A0229,A0243,A0245,A0247,A0251,A0251B,A0255,A0256," +
			"A0256A,A0256B,A0256C,A0259,A0265,A0267,A0271,A0277,A0281,A0284,A0288,A0289,A0295,A0299,A4901,A4904,A4907," +
			"UPDATED,WAGE_USED,A0221A,B0239,B0238) select A0000,sys_guid(),'','"+b01.getB0101()+"','"+inId+"','"+b01.getB0104()+"','','','','','','',"+
			"'','','','','','','99','','','','','','','','','1','','','','','','','','','',A0281,'','','','','','','','',"+
			"'','','','','' from a02 where a0201b='"+outId+"' and a0255='1'";
		}else{
			insertsql = "insert into a02(A0000,A0200,A0201,A0201A,A0201B,A0201C,A0201D,A0201E,A0204,A0207,A0209,A0215A," +
			"A0215B,A0216A,A0219,A0219W,A0221,A0221W,A0222,A0223,A0225,A0229,A0243,A0245,A0247,A0251,A0251B,A0255,A0256," +
			"A0256A,A0256B,A0256C,A0259,A0265,A0267,A0271,A0277,A0281,A0284,A0288,A0289,A0295,A0299,A4901,A4904,A4907," +
			"UPDATED,WAGE_USED,A0221A,B0239,B0238) select A0000,uuid(),NULL,'"+b01.getB0101()+"','"+inId+"','"+b01.getB0104()+"'," +
			"NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'99',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1'," +
			"NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,A0281,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,"+
			"NULL,NULL,NULL,NULL,NULL from a02 where a0201b='"+outId+"' and a0255='1'";
		}
		HBUtil.getHBSession().createSQLQuery(insertsql).executeUpdate();//�����»�������ְ��Ϣ��¼
		//String sql="update a02 set a0201a='"+b01.getB0101()+"', a0201c='"+b01.getB0104()+"', a0201b = '"+inId+"' where a0201b='"+outId+"' and a0255='1'";
		String sql="update a02 set a0255 = '0' where a0201b='"+outId+"' and a0255='1'";//��ԭ��ְ��Ϣ��¼�Ļ���״̬����Ϊ����
		String conutsql = "select count(t.a0000) from (select a0000 from a02 where a0201b='"+outId+"' and a0255='1' group by a0000) t";
		Object c = HBUtil.getHBSession().createSQLQuery(conutsql).uniqueResult();
		int count = HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		WorkUnitsAddPagePageModel workUnitsAddPagePageModel =new WorkUnitsAddPagePageModel();
		String a0000s="select t.a0000 from a01 t,a02 b where t.a0000=b.a0000 and  b.a0201b = '"+inId+"'";
		List<String> list = HBUtil.getHBSession().createSQLQuery(a0000s).list();
		for(int i=0;i<list.size();i++){
			workUnitsAddPagePageModel.UpdateTitleBtn(list.get(i));
		}
		//������Ա�鿴���Ƽ�¼
		HBUtil.getHBSession().createSQLQuery("update competence_userperson set b0111 = '"+inId+"' where b0111 = '"+outId+"'").executeUpdate();
		return Integer.valueOf(c.toString());
	}
	
	private String hasChildren(String id){
		String sql="from B01 b where B0121='"+id+"' order by sortid";// -1������ְ��Ա
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();
		if(list!=null && list.size()>0){
			return "false";
		}else{
			return "true";
		}
	}
	
	public void moveOrgAndPeople(String before ,String after){
		//ȡ�������Լ������¼�
		String sql = "update b01 t set t.b0111=replace(t.b0111,'"+before+"','"+after+"'),t.b0121 =replace(t.b0121,'"+before+"','"+after+"') where t.b0111 like '"+before+"%'";
		String sqlA02 = "update a02 t set t.a0201b=replace(t.a0201b,'"+before+"','"+after+"')  where t.a0201b like '"+before+"%'";
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		HBUtil.getHBSession().createSQLQuery(sqlA02).executeUpdate();
		//����a0195
		String sqlA01 = "update a01 t set t.a0195=replace(t.a0195,'"+before+"','"+after+"')  where t.a0195 like '"+before+"%'";
		HBUtil.getHBSession().createSQLQuery(sqlA01).executeUpdate();
		//��ʷ��������Ա���ڻ���
		String sqlOrgId = "update a01 t set t.orgid=replace(t.orgid,'"+before+"','"+after+"')  where t.orgid like '"+before+"%'";
		HBUtil.getHBSession().createSQLQuery(sqlOrgId).executeUpdate();
	}
	
	@Override
	public void closeCueWindow(String arg0) {
		// TODO Auto-generated method stub
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('" + arg0 + "').close();");;
	}

}

