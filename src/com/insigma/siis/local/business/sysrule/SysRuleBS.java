package com.insigma.siis.local.business.sysrule;

import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.BSSupport;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.UserDept;
import com.insigma.siis.local.business.entity.UserDeptLook;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class SysRuleBS extends BSSupport{
	
	//����
	public static void saveUserDept(String b0111){
		CurrentUser user = SysUtil.getCacheCurrentUser();
		//��ȡ�������ϼ����������л���Ȩ��
		String hql = "from UserDept ud where exists (select 1 from B01 b where b.b0121 = ud.b0111 and b.b0111 = '"+b0111+"')";
		List<UserDept> list1 = HBUtil.getHBSession().createQuery(hql).list();
		for(int i=0;i<list1.size();i++){
			UserDept ud = list1.get(i);
			List list2 = HBUtil.getHBSession().createQuery("from SmtUser where userid = '"+ud.getUserid()+"' and isleader = '1'").list();
			if(list2.size()<1){
				continue;
			}
			UserDept userDept=new UserDept();
			userDept.setB0111(b0111);
			userDept.setUserid(ud.getUserid());
			userDept.setType("1");
			
			UserDeptLook UserDeptlook =new UserDeptLook();
			UserDeptlook.setB0111(b0111);
			UserDeptlook.setUserid(ud.getUserid());
			UserDeptlook.setType("1");
			
			HBUtil.getHBSession().save(userDept);
			HBUtil.getHBSession().flush();
			HBUtil.getHBSession().save(UserDeptlook);
			HBUtil.getHBSession().flush();
			
		}
		List<Object[]> list = selectUserDept(b0111);
		if(list.size()<1){
			if(!user.getId().equals("U001")){
				UserDept userDept=new UserDept();
				userDept.setB0111(b0111);
				userDept.setUserid(user.getId());
				userDept.setType("1");
				
				UserDeptLook UserDeptlook=new UserDeptLook();
				UserDeptlook.setB0111(b0111);
				UserDeptlook.setUserid(user.getId());
				UserDeptlook.setType("1");
				
				HBUtil.getHBSession().save(userDept);
				HBUtil.getHBSession().flush();
				HBUtil.getHBSession().save(UserDeptlook);
				HBUtil.getHBSession().flush();
			}	
		}
	}

	//�޸�
	@Transaction
	public static void updateUserDept(String oldB0111,String newB0lll){
		List<Object[]> list = selectUserDept(oldB0111);
		UserDept userDept = new UserDept();
		for(int i=0;i<list.size();i++){
			Object[] obj= list.get(0);
			userDept.setUserdeptid((String)obj[0]);
			userDept.setUserid((String)obj[1]);
			userDept.setB0111(newB0lll);
			userDept.setType((String)obj[3]);
			HBUtil.getHBSession().update(userDept);
			HBUtil.getHBSession().flush();
		}
	}

	//ɾ��
	public static void deleteUserDept(String b0111){
		List<Object[]> list = selectUserDept(b0111);
		for(int i=0;i<list.size();i++){
			Object[] obj= list.get(0);
			String sql = "delete from COMPETENCE_USERDEPT  where userdeptid='"+(String)obj[0]+"'";
			HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		}
	}
	
	//��ѯ
	public static List<Object[]> selectUserDept(String b0111){
		CurrentUser user = SysUtil.getCacheCurrentUser();
		String sql = "select * from COMPETENCE_USERDEPT t where t.userid='"+user.getId()+"' and t.b0111='"+b0111+"'";
		List<Object[]> list  = HBUtil.getHBSession().createSQLQuery(sql).list();
		return list;
	}
	
	//�����޸�
	@Transaction
	public static void batchUpdateUserDept(String before,String after) throws AppException,RadowException{
		CurrentUser user = SysUtil.getCacheCurrentUser();
		String sql = "update COMPETENCE_USERDEPT  set b0111=replace(b0111,'"+before+"','"+after+"') where b0111 like '"+before+"%' and userid='"+user.getId()+"'";
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
	}
	
	//����ɾ��
	public static void batchDeleteUserDept(String b0111){
			String sql = "delete from COMPETENCE_USERDEPT  where  b0111 like '"+b0111+"%' ";
			HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
	}
	
	//�Ƿ����޸�Ȩ��
	public static Boolean havaRule(String b0111) {
		try {
			CurrentUser user = SysUtil.getCacheCurrentUser();
			String sql = "select Count(1) from COMPETENCE_USERDEPT t where t.userid='"+user.getId()+"' and t.b0111='"+b0111+"' and t.type='1'";
			String str  = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
			String cueUserid = user.getId();
			String loginnname=user.getLoginname();
			List<GroupVO> groups;
			groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);
			if(groups.isEmpty() || issupermanager ||loginnname.equals("admin")||"system".equals(loginnname)){
				return true;
			}
			if(str.equals("0")){
				return false;
			}else{
				return true;
			}
		} catch (PrivilegeException e) {
			e.printStackTrace();
			return false;
		}
	}
	

		/**
		 * ������Ϣ�б�˫���¼���Ȩ��У��ר��
		 * @param b0111
		 * @return
		 */
		public static Boolean havaRule_jg(String b0111) {
			try {
				CurrentUser user = SysUtil.getCacheCurrentUser();
				String sql = "select Count(1) from COMPETENCE_USERDEPT t where t.userid='"+user.getId()+"' and t.b0111='"+b0111+"' ";
				String str  = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
				String cueUserid = user.getId();
				String loginnname=user.getLoginname();
				List<GroupVO> groups;
				groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
				UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
				boolean issupermanager=new DefaultPermission().isSuperManager(vo);
				if(groups.isEmpty() || issupermanager ||loginnname.equals("admin")){
					return true;
				}
				if(str.equals("0")){
					return false;
				}else{
					return true;
				}
			} catch (PrivilegeException e) {
				e.printStackTrace();
				return false;
			}
		}
	
	public static Boolean issupermanager() {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		String cueUserid = user.getId();
		String loginnname=user.getLoginname();
		List<GroupVO> groups;
		try {
			groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);
			if(groups.isEmpty() || issupermanager ||loginnname.equals("admin")){
				return true;
			}
		} catch (PrivilegeException e) {
			e.printStackTrace();
			return false;
		}

		return false;
		
	}
	/**
	 * ����δ��SYSTEM�û��µı༭����
	 * zxw ����δ��������ֵ��system�û��Ľڵ�
	 */
	public static void bcUserdept() {
		try{
			CommQuery commQuery = new CommQuery();
			List<java.util.HashMap<String,Object>> list2 = commQuery.getListBySQL("select b0111 from b01 where b0111 <> '-1' and"
					+ " b0111 not in (select b0111 from COMPETENCE_USERDEPT t where t.userid='40288103556cc97701556d629135000f')");
			int num = list2.size();
			if(num<1){
				CommonQueryBS.systemOut("����Ȩ�ޱ��м�¼ȫ���ı༭������Ϣ...");
			}else{
				for (int i = 0; i < num; i++) {
					HBUtil.executeUpdate("insert into COMPETENCE_USERDEPT values ("+DBUtil.UUID()+",'40288103556cc97701556d629135000f','"+list2.get(i).get("b0111")+"','1')");
				}
				
			}
		}catch(Exception e){}
	}
	/**
	 * ����δ��SYSTEM�û��µ��������
	 * zxw ����δ��������ֵ��system�û��Ľڵ�
	 */
	public static void bcUserdeptTwo() {
		try{
			CommQuery commQuery = new CommQuery();
			List<java.util.HashMap<String,Object>> list2 = commQuery.getListBySQL("select b0111 from b01 where b0111 <> '-1' and"
					+ " b0111 not in (select b0111 from competence_userdept t where t.userid='40288103556cc97701556d629135000f')");
			int num = list2.size();
			if(num<1){
				CommonQueryBS.systemOut("����Ȩ�ޱ��м�¼ȫ�������������Ϣ...");
			}else{
				for (int i = 0; i < num; i++) {
					HBUtil.executeUpdate("insert into competence_userdept values ("+DBUtil.UUID()+",'40288103556cc97701556d629135000f','"+list2.get(i).get("b0111")+"','1')");
				}
				
			}
		}catch(Exception e){}
	}
	
	//���������������ѧλѧ���洢����(ִ�ж������)
	public static void calls(List<String> a0000List) {
		// TODO Auto-generated method stub
		HBSession sess = HBUtil.getHBSession();    
		try {
			for(String a0000:a0000List) {
				sess.createSQLQuery("{Call XWXL('"+a0000+"')}").executeUpdate();     
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	////���������������ѧλѧ���洢����(ִ�е�������)
	public static void call(String a0000) {
		// TODO Auto-generated method stub
		HBSession sess = HBUtil.getHBSession();    
		try {
			sess.createSQLQuery("{Call XWXL('"+a0000+"')}").executeUpdate();     
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
} 