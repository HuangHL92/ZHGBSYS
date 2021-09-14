package com.insigma.odin.framework.privilege;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.privilege.entity.SmtAct;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.entity.SmtUsergroupref;
import com.insigma.odin.framework.privilege.helper.CommomPermissionHelper;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.helper.UserHelper;
import com.insigma.odin.framework.privilege.util.AbstractPrivilegeBase;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.util.IPermission;
import com.insigma.odin.framework.privilege.util.PageQueryData;
import com.insigma.odin.framework.privilege.util.PrivilegeUtil;
import com.insigma.odin.framework.privilege.util.ResourcesPermissionConst;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.util.HQuery;


/**
 * �û��鴦����
 * @author �ƶ���
 * @created 18-����-2009 11:48:07
 */
public class GroupRealControl extends AbstractPrivilegeBase  implements IGroupControl{
	
	public GroupRealControl(){
		
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	
	
	/**
	 * У����û��������봫������Ƿ���ͬһ��Ͻ���� Ͻ�������ϵĲ�����У��
	 * @param groupId	��Ҫ�����û������ID
	 * @param userIds	����û�ID�м��ö��Ÿ���
	 * @throws PrivilegeException 
	 */
	public void checkGroup(String groupId, String userIds) throws PrivilegeException{
		GroupVO newvo=findXQById(groupId);//�����û����Ͻ��
		String newrate=newvo.getRate();
		
		if(newrate!=null&&!newrate.equals("")){//����Ϊ�յ�У��  �յĲ�У��

			if(Long.parseLong(newrate)>=2){//�������û��������������������� ��У��  ����У��
				String[] ids = userIds.split(",");
				for(int i = 0;i<ids.length;i++){
					String userid=ids[i];
					List<GroupVO> list=findGroupByUserId(userid);
					Boolean boo=false;//false ��ʾ��Ա�¼���������ǰ���鲻��ͬһ��Ͻ��   true  ��ʾ��Ա�¼���������ǰ������ͬһ��Ͻ��
					if(list.size()==0){
						boo=true;
					}
					for(int j = 0;j<list.size();j++){
						GroupVO vo=list.get(j);
						GroupVO oldvo =findXQById(vo.getId());//�鵽���û��������ĸ�Ͻ��
						if(newvo.getId().equals(oldvo.getId())){//����Ͻ����ͬ 
							boo=true;
							break;
							//throw new PrivilegeException("�벻Ҫ��Ͻ�������û���");
						}
					}
					
					if(boo==false){
						throw new PrivilegeException("�벻Ҫ��Ͻ�������û�");
					}
					
				}
			}
			
		}
		
		
	}
	
	/**
	 * �鵽���û��������ĸ�Ͻ��
	 * @param groupId	������û���id
	 * @throws PrivilegeException 
	 */
	public GroupVO findXQById(String groupId) throws PrivilegeException{
		GroupVO vo =findById(groupId);
		String rate =vo.getRate();
		if(rate!=null&&!rate.equals("")){
			if(Long.parseLong(rate)>3){//��������
				return findXQById_extends(vo.getParentid());
			}else if(Long.parseLong(rate)==3){//����
				return vo;
			}else{//��������
				//throw new PrivilegeException("����Ա����Ⱥ��ΪϽ�����ϼ���,����Ϊ"+vo.getName()+",id��"+vo.getId());
				return vo;
			}
		}
		return vo;
	}
	
	
	/**
	 * �����û���ID��ѯ�û�����Ϣ
	 * @param id �û���ID
	 * @return GroupVO
	 * @throws PrivilegeException
	 */
	private GroupVO findXQById_extends(String id) throws PrivilegeException{
//		List list = getPrivilegeManager().getHbSession().createQuery("from SmtGroup where id=:id").setString("id", id).list();
//		if(list == null || list.isEmpty()){
//			throw new PrivilegeException("δ�ҵ�Ⱥ�飬idΪ��"+id);
//		}
//		
//		SmtGroup smtGroup = (SmtGroup)list.get(0);
//		return findXQById(smtGroup.getId());
		GroupVO vo =findById(id);
		if(vo.getRate().equals("3")){
			return vo;
		}else{
			return findXQById(id);
		}
		
	}
	
	

	/**
	 * �����û����顣���Ӷ���û�ʱ,�����������˳���������,��ĳ���û����������쳣ʱ��ֹͣ����.��Ҫͨ�������������乲ͬ�ɹ���ʧ��.
	 * @param groupId	��Ҫ�����û������ID
	 * @param userIds	����û�ID�м��ö��Ÿ���
	 * @return boolean	true��ʾ��ӳɹ�;ʧ��������Ӧ�쳣��Ϣ
	 * @throws PrivilegeException 
	 */
	public boolean addUserToGroup(String groupId, String userIds) throws PrivilegeException{
		GroupUseful((SmtGroup)super.get(SmtGroup.class, groupId),GroupHelper.GROUP_USEFUL,null);//�ж��û�������Ժ���Ч��
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, CommomPermissionHelper.MANAGER_CHECK, getGroupOwnerId(groupId), groupId);
		if(permission == false)//Ȩ���ж�
			throw new PrivilegeException("�޲���Ȩ��");
		String[] ids = userIds.split(",");
		int temp = 0;
		for(int i = 0;i<ids.length;i++){
			getUserUseful(ids[i],"�û�");//�ж��û�������Ч��
			if(!(GroupHelper.isGroupMember(super.getPrivilegeManager(),groupId,ids[i])==GroupHelper.UN_GROUP_MEMGBER))
				throw new PrivilegeException("���û���Ϊ�����Ա");
			try {
				SmtUsergroupref ug = new SmtUsergroupref();
				ug.setGroupid(groupId);
				ug.setUserid(ids[i]);
				ug.setIsleader("0");
				super.save(ug);  
				temp++;
			} catch (PrivilegeException e) {
				throw new PrivilegeException("�û�����ӳ�Աʧ��",e);
			}
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("���û����û����ϵ���в���"+temp+"������", "����IGroupControl�ӿڵ�addUserToGroup������ָ�������ӳ�Ա", null);
		return true;
	}

	/**
	 * ȡ�������Ա�ʸ���ȡ�������Աʱ�����մ����˳�����ȡ��.��ĳ���û�����ȡ���쳣ʱ��ֹͣȡ��,��Ҫͨ�������������乲ͬ�ɹ���ʧ��.
	 * @param groupId	��ȡ������Ա�����ID
	 * @param userIds	��ȡ������Ա����ԱID������û�ID�ö��Ÿ���
	 * @return boolean true��ʾȡ���ɹ�;ʧ��������Ӧ�쳣��Ϣ
	 * @throws PrivilegeException 
	 */
	public boolean cancelGroupManager(String groupId, String userIds) throws PrivilegeException{
		GroupUseful((SmtGroup)super.get(SmtGroup.class, groupId),GroupHelper.GROUP_USEFUL,null);//�ж��û�������Ժ���Ч��
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, CommomPermissionHelper.OWNER_CHECK, getGroupOwnerId(groupId), null);
		if(permission == false)
			throw new PrivilegeException("�޲���Ȩ��");//Ȩ���ж�
		String[] ids = userIds.split(",");
		int temp = 0;
		for(int i = 0;i<ids.length;i++){
			getUserUseful(ids[i],"����Ա");//�ж���ȡ������Ա�����Ƿ������Ч
			//�ж���ȡ������Ա����Ա�Ƿ��Ѿ�ȡ������Ա���
			if(!(GroupHelper.isGroupMember(super.getPrivilegeManager(),groupId,ids[i]) == GroupHelper.GROUP_LEADER))
				throw new PrivilegeException("����Ա�Ǹ��û������Ա,����ȡ�������Ա���...");
			try {
//				session.createQuery("update SmtUsergroupref set ISLEADER = 0 where GROUPID=:groupId and USERID=:userId")
//					   .setString("groupId", groupId)
//					   .setString("userId", ids[i])
//					   .executeUpdate();
				String	hql = "from SmtUsergroupref sug where sug.groupid=:groupId and sug.userid=:userId";
				Session session = getPrivilegeManager().getHbSession();
				List<?> list = session.createQuery(hql).setString("groupId", groupId)
										.setString("userId", ids[i]).list();
				if(list.size()!=0){
					SmtUsergroupref sug = (SmtUsergroupref) list.get(0);
					sug.setIsleader("0");
					session.saveOrUpdate(sug);
					session.flush();
				}	
				temp++;
			} catch (HibernateException e) {
				throw new PrivilegeException("ȡ������Աʧ��",e);
			}
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("ȡ��"+temp+"����Ա�Ĺ���Ա���", "����IGroupControl�ӿڵ�cancelGroupManager����", null);
		return true;
	}

	/**
	 * ɾ���顣��֧��ͬʱɾ������û������
	 * @param groupId �豻ɾ�������ID
	 * @return boolean true��ʾɾ���ɹ�;ʧ��������Ӧ�쳣��Ϣ
	 * @throws PrivilegeException 
	 */
	public boolean deleteGroup(String groupId) throws PrivilegeException{
		SmtGroup smtGroup = (SmtGroup)super.get(SmtGroup.class, groupId);
		//GroupUseful(smtGroup,GroupHelper.GROUP_EXIST,null);//�ж��������
		if(smtGroup == null) throw new PrivilegeException("����ɾ�����û��鲻����");//�ж��������
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_DELETE, CommomPermissionHelper.OWNER_CHECK, getGroupOwnerId(groupId), null);
		if(permission == false)//Ȩ���ж�
			throw new PrivilegeException("�޲���Ȩ��");
		isContainResources(groupId,"ɾ��");//�жϸ��û����Ƿ��г�Ա����Դ
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("groupId", groupId);
		List<?> list = super.query("from SmtAct where OBJECTID=:groupId",params);
		if(!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				SmtAct act = (SmtAct)list.get(i);
				super.delete(act);
			}
		}
		super.delete(smtGroup);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("ɾ���û���"+smtGroup.getName(), "����IGroupControl�ӿڵ�deleteGroup����", null);
		return true;
	}

	/**
	 * ��ѯ��ǰ��¼�û��ɼ��������û���:����start��limit��ֵ��ѯĳһҳ��ֵ;��start��limitΪ-1ʱ,��ʾ��ѯ��¼�����пɼ���.
	 * @param cueUser  UserVO��:��ǰ�û���Ϣ
	 * @param start  ��ѯ��������ʼ
	 * @param limit  ��ѯ������ 
	 * @return ���GroupVO��List�û����б�
	 */
	public List<Object> queryByUser(UserVO cueUser, int start, int limit,HashMap params) throws PrivilegeException{
		String hql = "from SmtGroup sg";
		String hql_n = " from SmtGroup sg where id in" +
					" (select groupid from SmtUsergroupref where userid ='"+cueUser.getId()+"')" +
					" or sg.ownerId = '"+cueUser.getId()+"'";
		if(params != null){
			if(params.get("groupname") != null){
				hql += " where name = '"+params.get("groupname")+"'";
				hql_n += " and name = '"+params.get("groupname")+"'";
			}else if(params.get("groupnames") != null){
				hql += " where name like '%"+params.get("groupnames")+"%' ";
				hql_n += " and name like '%"+params.get("groupnames")+"%' ";
			}else{
				Set<String> keys = params.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = it.next();
					hql += " where sg." + key + " like '%" + params.get(key) + "%'";
					hql_n += " and sg." + key + " like '%" + params.get(key) + "%'";
				}
			}
		}
		Session session = getPrivilegeManager().getHbSession();
		List list = new ArrayList();
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL,CommomPermissionHelper.NORMAL_CHECK,null, null);
		if(permission == true){//����ǳ�������Ա�ͳ������ݼ����û������ѯ�����û���
			if(limit == -1 || start == -1){
				list = session.createQuery(hql).list();
			}else{
				Query q = session.createQuery(hql);
				q.setFirstResult(start); 
				q.setMaxResults(limit); 
				list = q.list();
			}
		}else{//�����û�
			if(limit == -1 || start == -1){
				list = session.createQuery(hql_n).list();
			}else{
				Query q = session.createQuery(hql_n);
				q.setFirstResult(start); 
				q.setMaxResults(limit);
				list = q.list();
			}
		}
		return PrivilegeUtil.getVOList(list,GroupVO.class);
	}

	/**
	 * ��ҳ��ѯ�����ݵ�ǰ��¼�û���ѯ�ɼ��������û���
	 * @param cueUser  �û�����
	 * @param start  ��ʼλ��
	 * @param limit  ��ҳʱÿҳ��ʾ��
	 * @param params  HashMap����:��ѯRoleVO����������
	 * @return PageQueryData ���ط�ҳ��ѯ���ݶ���
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	public PageQueryData pageQueryByUser(UserVO cueUser, int start,
			int limit, HashMap params) throws PrivilegeException {
		PageQueryData pd = getPageQueryDataByUser(cueUser, start, limit, params);
		List groups = queryByUser(cueUser, start, limit, params);
		try {
			pd.setData(HQuery.fromList(groups));
		} catch (AppException e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**
	 * ��ҳ��ѯ�������������ơ����ݵ�ǰ��¼�û���ѯ�ɼ��������û���,����GroupVO�е�parentid�ֶ�Ϊ�����������
	 * @param cueUser  �û�����
	 * @param start  ��ʼλ��
	 * @param limit  ��ҳʱÿҳ��ʾ��
	 * @param params  HashMap����:��ѯRoleVO����������
	 * @return PageQueryData ���ط�ҳ��ѯ���ݶ���
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	public PageQueryData pageQueryContantParentNameByUser(UserVO cueUser, int start,
			int limit, HashMap params) throws PrivilegeException {
		PageQueryData pd = getPageQueryDataByUser(cueUser, start, limit, params);
		List groups = queryByUser(cueUser, start, limit, params);
		for(int i=0;i<groups.size();i++){
			GroupVO group = (GroupVO)groups.get(i);
			GroupVO parent = this.findById(group.getParentid());
			if(parent != null){
				group.setParentid(parent.getName());
			}
		}
		try {
			pd.setData(HQuery.fromList(groups));
		} catch (AppException e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**
	 * ��ҳ��ѯ���������ߵ�¼�������ݵ�ǰ��¼�û���ѯ�ɼ��������û���,����GroupVO��ownerid�ֶ�Ϊ�����ߵĵ�¼��
	 * @param cueUser  �û�����
	 * @param start  ��ʼλ��
	 * @param limit  ��ҳʱÿҳ��ʾ��
	 * @param params  HashMap����:��ѯRoleVO����������
	 * @return PageQueryData ���ط�ҳ��ѯ���ݶ���
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	public PageQueryData pageQueryContantOwnerNameByUser(UserVO cueUser, int start,
			int limit, HashMap params) throws PrivilegeException {
		PageQueryData pd = getPageQueryDataByUser(cueUser, start, limit, params);
		List groups = queryByUser(cueUser, start, limit, params);
		for(int i=0;i<groups.size();i++){
			GroupVO group = (GroupVO)groups.get(i);
			UserVO owner = this.getPrivilegeManager().getIUserControl().findUserByUserId(group.getOwnerId());
			if(owner == null){
				GroupVO groupOwner = findById(group.getOwnerId());
				group.setOwnerId(groupOwner.getName());
			}else{
				group.setOwnerId(owner.getLoginname());
			}
		}
		try {
			pd.setData(HQuery.fromList(groups));
		} catch (AppException e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**
	 * ���û����Ƴ��û����Ƴ�����û�ʱ���ݴ����˳������Ƴ�,��ĳ���û��Ƴ��쳣ʱֹͣ�Ƴ�.��Ҫͨ�������������乲ͬ�ɹ���ʧ��.
	 * @param groupId	���Ƴ��û������ID
	 * @param userIds	���Ƴ����û�ID,����û�ID�м��ö��Ÿ���
	 * @return boolean  true��ʾ�Ƴ��ɹ�;ʧ��������Ӧ�쳣��Ϣ
	 * @throws PrivilegeException
	 */
	public boolean removeUserFromGroup(String groupId, String userIds) throws PrivilegeException{
		SmtGroup smtGroup = (SmtGroup)super.get(SmtGroup.class, groupId);
		GroupUseful(smtGroup,GroupHelper.GROUP_USEFUL,null);//�ж�������Ժ���Ч��
		UserVO user = super.getPrivilegeManager().getCueLoginUser();
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, CommomPermissionHelper.OWNER_CHECK, smtGroup.getOwnerId(), null);
		if(permission == false){//�ж��Ƿ��ǳ�������Ա�����ݼ�����Ȩ���û��������
			//����ǹ���Ա
			if(GroupHelper.isGroupMember(super.getPrivilegeManager(),groupId,user.getId()) == GroupHelper.GROUP_LEADER){
				String[] ids = userIds.split(",");
				for(int i = 0;i<ids.length;i++){
					if(GroupHelper.isGroupMember(super.getPrivilegeManager(),groupId,ids[i]) == GroupHelper.GROUP_LEADER)
						throw new PrivilegeException("����ԱΪ�����Ա,����Ȩɾ��");
					doRemoveUserFromGroup(groupId,ids[i]);
				}
				return true;
			}
			else{
				throw new PrivilegeException("�޲���Ȩ��");
			}
		}
		String[] ids = userIds.split(",");
		int temp = 0;
		for(int i = 0;i<ids.length;i++){
			doRemoveUserFromGroup(groupId,ids[i]);
			temp++;
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("���û����û����ϵ����ɾ��"+temp+"������", "����IGroupControl�ӿڵ�removeUserFromGroup����", null);
		return true;
	}
	
	/**
	 * ע���顣��֧��ͬʱע������û���
	 * @param groupId	�豻ע�������ID
	 * @return boolean true��ʾע���ɹ�;ʧ��������Ӧ�쳣��Ϣ
	 * @throws PrivilegeException 
	 */
	public boolean revokeGroup(String groupId) throws PrivilegeException{
		SmtGroup smtGroup = (SmtGroup)super.get(SmtGroup.class, groupId);
		GroupUseful(smtGroup,GroupHelper.GROUP_USEFUL,null);//�ж�������Ժ���Ч��
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_REVOKE, CommomPermissionHelper.OWNER_CHECK, smtGroup.getOwnerId(), null);
		if(permission == false)//Ȩ���ж�
			throw new PrivilegeException("�޲���Ȩ��");
		isContainResources(groupId,"ע��");//�жϸ��û����Ƿ��г�Ա����Դ
		try {
			smtGroup.setStatus(Integer.toString(GroupHelper.GROUP_UN_USEFUL));
			super.update(smtGroup);
		} catch (PrivilegeException e) {
			throw new PrivilegeException("ע���û���ʧ��",e);
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("ע����"+smtGroup.getName(), "����IGroupControl�ӿڵ�revokeGroup����", null);
		return true;
	}

	/**
	 * ��������Ч����֧��ͬʱ���ö���û�����Ч
	 * @param groupId	����Ϊ��Ч�����ID
	 * @return boolean true��ʾ���óɹ�;ʧ��������Ӧ�쳣��Ϣ
	 * @throws PrivilegeException 
	 */
	public boolean reuseGroup(String groupId) throws PrivilegeException{
		SmtGroup smtGroup = (SmtGroup)super.get(SmtGroup.class, groupId);
		GroupUseful(smtGroup,GroupHelper.GROUP_UN_USEFUL,null);//�ж�������Ժ���Ч��
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, CommomPermissionHelper.OWNER_CHECK, smtGroup.getOwnerId(), null);
		if(permission == false)//Ȩ���ж�
			throw new PrivilegeException("�޲���Ȩ��");
		try {
			smtGroup.setStatus(Integer.toString(GroupHelper.GROUP_USEFUL));
			super.update(smtGroup);
		} catch (PrivilegeException e) {
			throw new PrivilegeException("�����û�����Чʧ��",e);
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("������"+smtGroup.getName(), "����IGroupControl�ӿڵ�reuseGroup����", null);
		return true;
	}
	
	/**
	 * �����顣δָ��������ʱ,Ĭ��Ϊϵͳ�û���;δָ��������ʱ,Ĭ��Ϊ������
	 * @param group GroupVO��:��Ҫ���ӵ������Ϣ
	 * @return boolean true��ʾ���óɹ�;ʧ��������Ӧ�쳣��Ϣ
	 * @throws PrivilegeException 
	 */
	public boolean saveGroup(GroupVO group) throws PrivilegeException{
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT,CommomPermissionHelper.NORMAL_CHECK, null, null);
		if(permission == false)//Ȩ���ж�
			throw new PrivilegeException("�޲���Ȩ��");
		List<?> groupList = queryByName(group.getName(),true);
		if(!groupList.isEmpty())
			throw new PrivilegeException("�������Ѿ�����");
		
		if(group.getParentid() == null || group.getParentid().equals("")){
			group.setParentid("G001");
		}else{//�ж�ָ���ĸ������Ƿ������Ч
			GroupUseful((SmtGroup)super.get(SmtGroup.class, group.getParentid()),GroupHelper.GROUP_USEFUL,"ָ���ĸ�����");
		}
		UserVO cueuser = getPrivilegeManager().getCueLoginUser();
		if(group.getOwnerId() == null){//�ж��Ƿ�ָ��������
			group.setOwnerId(cueuser.getId());
		}else{
			getUserUseful(group.getOwnerId(),"�û���ָ���ĳ�����");//�ж��û�������ߴ��ں���Ч��
		}
		SmtGroup smtGroup = new SmtGroup();
		try {
			BeanUtil.propertyCopy(group, smtGroup);
			if(smtGroup.getStatus() == null || smtGroup.getStatus().equals(""))//���û�����������Ч�ԣ���Ĭ��Ϊ��Ч
				smtGroup.setStatus("1");
			super.save(smtGroup);
		} catch (PrivilegeException e) {
			throw new PrivilegeException("�����û���ʧ��",e);
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("�����û���"+smtGroup.getName(), "����IGroupControl�ӿڵ�saveGroup����", null);
		return true;
	}

	/**
	 * ���������Ա�������ö���û�Ϊָ�������Աʱ,���ݴ���˳������,��ĳ�����ù���Ա�����쳣ʱ,��ֹͣ����.��Ҫͨ�������������乲ͬ�ɹ���ʧ��.
	 * @param groupId	�����ù���Ա�����ID
	 * @param userIds   ������Ϊ����Ա����ԱID������û�ID�ö��Ÿ���
	 * @return boolean true��ʾ���óɹ�;ʧ��������Ӧ�쳣��Ϣ
	 * @throws PrivilegeException 
	 */
	public boolean setGroupManager(String groupId, String userIds) throws PrivilegeException{
		GroupUseful((SmtGroup)super.get(SmtGroup.class, groupId),GroupHelper.GROUP_USEFUL,null);//�ж��û�������Ժ���Ч��
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, CommomPermissionHelper.OWNER_CHECK, getGroupOwnerId(groupId), null);
		if(permission == false)//Ȩ���ж�
			throw new PrivilegeException("�޲���Ȩ��");
		String[] ids = userIds.split(",");
		int temp = 0;
		for(int i = 0;i<ids.length;i++){
			getUserUseful(ids[i],"�û�");//�ж��û�������Ч��
			if(GroupHelper.isGroupMember(super.getPrivilegeManager(),groupId,ids[i]) == GroupHelper.UN_GROUP_MEMGBER){
				throw new PrivilegeException("����Ա�Ǹ����Ա����������Ϊ����Ա");
			}
			if(GroupHelper.isGroupMember(super.getPrivilegeManager(),groupId,ids[i]) == GroupHelper.GROUP_LEADER){
				throw new PrivilegeException("�豻����Ϊ����Ա����Ա�Ѿ�Ϊ�������Ա");
			}
			try {
//				session.createQuery("update SmtUsergroupref set ISLEADER = 1 where GROUPID=:groupId and USERID=:userId")
//						.setString("groupId", groupId)
//						.setString("userId", ids[i])
//						.executeUpdate();
				String	hql = "from SmtUsergroupref sug where sug.groupid=:groupId and sug.userid=:userId";
				Session session = getPrivilegeManager().getHbSession();
				List<?> list = session.createQuery(hql).setString("groupId", groupId)
										.setString("userId", ids[i]).list();
				if(list.size()!=0){
					SmtUsergroupref sug = (SmtUsergroupref) list.get(0);
					sug.setIsleader("1");
					session.saveOrUpdate(sug);
					session.flush();
				}	
				temp++;
			} catch (HibernateException e) {
				throw new PrivilegeException("�����û������Աʧ��",e);
			}
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("���û����û����ϵ��������"+temp+"����ԱΪ����Ա", "����IGroupControl�ӿڵ�setGroupManager����", null);
		return true;
	}

	/**
	 * ������
	 * @param group	GroupVO��
	 * @return boolean true��ʾ���³ɹ�;ʧ��������Ӧ�쳣��Ϣ
	 * @throws PrivilegeException 
	 */
	public boolean updateGroup(GroupVO group) throws PrivilegeException{
		SmtGroup oldGroup = (SmtGroup)super.get(SmtGroup.class, group.getId());
		GroupUseful(oldGroup,GroupHelper.GROUP_EXIST,null);//�ж��û��������
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, 
				CommomPermissionHelper.OWNER_CHECK, oldGroup.getOwnerId(), null);
		if(permission == false)
			throw new PrivilegeException("�޲���Ȩ��");
		if(group.getParentid() != null && !group.getParentid().equals(""))//�ж�ָ���ĸ������Ƿ������Ч
			GroupUseful((SmtGroup)super.get(SmtGroup.class, group.getParentid()),GroupHelper.GROUP_USEFUL,"ָ���ĸ�����");
		if(group.getOwnerId() != null && !(group.getOwnerId().equals(oldGroup.getOwnerId())))//�ж��û�������ߴ��ں���Ч��
			getUserUseful(group.getOwnerId(),"�û��������");
		if(group.getStatus().equals(Integer.toString(GroupHelper.GROUP_UN_USEFUL)))//���ִ��ע����ĸ��£��ж��Ƿ��г�Ա�������Դ
			isContainResources(group.getId(), "ע����ĸ���");
		if(group.getStatus().equals(""))
			throw new PrivilegeException("�û���״̬��������Ϊ��");
		SmtGroup smtGroup_new = new SmtGroup();
		try {
			BeanUtil.propertyCopy(group, smtGroup_new);
//			boolean state = validateHashCode(oldGroup.getId(), oldGroup.getHashcode(),SmtGroup.class);
//			if(state) {
			getPrivilegeManager().getHbSession().clear();//���session
			super.update(smtGroup_new);
//			}
		} catch (PrivilegeException e) {
			throw new PrivilegeException("����ʧ��",e);
		}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("�����û���", "����IGroupControl�ӿڵ�updateGroup����", null);
		return true;
	}

	/**
	 * ����������ѯ����Ϣ:��Ȩ�޿���,��Ϊһ����ѯ����
	 * @param name	��Ҫ��ѯ���������
	 * @param isEqual true:���õ�ʽ���ң�false������ģ������
	 * @return ���GroupVO��List�û����б�
	 * @throws PrivilegeException
	 */
	public List<Object> queryByName(String name, boolean isEqual)
			throws PrivilegeException {
		List<Object> list = null;
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		String hql = "";
		if (isEqual) {
			hql = "from SmtGroup where name=:name";
			list = query(hql, params);
		}else {
			hql = "from SmtGroup where name like '%"+name+"%'";
			list = query(hql, null);
		}
		return PrivilegeUtil.getVOList(list,GroupVO.class);
	}

	/**
	 * �����û���ID����ѯ���û����������ĳ�Ա�����Դ����modelֵ��ѯ�û�������г�Ա����Ч��Ա����Ч��Ա�͹���Ա����.
	 * @param groupId ��Ҫ��ѯ���û����ID
	 * @param model ֵΪGroupHelper�е��ĸ�int�ͳ�������.
	 * 		        ��modolֵΪGROUP_ALL_MENBERʱ,��ѯ�������е����г�Ա;
	 * 		  				ΪGROUP_USEFUL_MENBERʱ,��ѯ�������е���Ч��Ա;
	 *        				ΪGROUP_UN_USEFUL_MENBERʱ,��ѯ�������е���Ч��Ա;
	 *        				ΪGROUP_LEADERʱ,��ѯ�����û���Ĺ���Ա.
	 * @return ���UserVO��List�û��б�
	 * @throws PrivilegeException 
	 */
	public List<UserVO> getGroupMember(String groupId,int model) throws PrivilegeException{
		GroupUseful((SmtGroup)super.get(SmtGroup.class, groupId),GroupHelper.GROUP_EXIST,null);//�ж��û��������
		String ownerid = getGroupOwnerId(groupId);
		boolean permission = super.hasPermission(null,ResourcesPermissionConst.GROUP_ALL_EDIT, CommomPermissionHelper.MANAGER_CHECK, ownerid, groupId);
		if(permission == false){//Ȩ���ж�
			HashMap<String,String> params = new HashMap<String,String>();
			params.put("groupId", groupId);
			params.put("userId", getPrivilegeManager().getCueLoginUser().getId());
			String hql = "from SmtUsergroupref where groupid=:groupId and userid=:userId";
			List<?> list = super.query(hql, params);
			if(list.isEmpty())//�޹���������Ȩ��ʱ���Ƿ�Ϊ�����Ա���ǵĻ��ɲ�ѯ���û��飬�������׳��޲���Ȩ���쳣
				throw new PrivilegeException("�޲���Ȩ��");
		}
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("groupId", groupId);
		String hql = "select userid from SmtUsergroupref where groupid=:groupId";
		if(model == GroupHelper.GROUP_LEADER){
			hql = "select userid from SmtUsergroupref where groupid=:groupId and isleader='1'";
		}
		List<?> idList = super.query(hql,params);
		if(idList.isEmpty()) return null;
		List<UserVO> list = new ArrayList<UserVO>();
		for(int i=0;i<idList.size();i++){
			String userId = (String)idList.get(i);
			SmtUser smtUser = UserHelper.findUserByUserId(super.getPrivilegeManager(), userId);
			if(smtUser==null) continue;
			UserVO user = new UserVO();
			BeanUtil.propertyCopy(smtUser, user);
			if(model == GroupHelper.GROUP_ALL_MEMBER){
				list.add(user);
			}
			if(model == GroupHelper.GROUP_USEFUL_MEMBER){
				if(user.getStatus().equals(Integer.toString(GroupHelper.GROUP_USEFUL_MEMBER))){
					list.add(user);
				}
			}
			if(model == GroupHelper.GROUP_UN_USEFUL_MEMBER){
				if(user.getStatus().equals(Integer.toString(GroupHelper.GROUP_UN_USEFUL_MEMBER))){
					list.add(user);
				}
			}
			if(model == GroupHelper.GROUP_LEADER){
				list.add(user);
			}
		}
		return list;
	}
	
	/**
	 * �����û�ID���������ڵ��û���,���û����ж��ʱ,�򷵻����е��û���
	 * @param id  �û�ID
	 * @return  ���GroupVO��List�û��鼯��
	 * @throws PrivilegeException
	 */
	public List findGroupByUserId(String userid) throws PrivilegeException{
		StringBuffer hql =new StringBuffer( "from SmtGroup where id in (select groupid from SmtUsergroupref where userid=:userid)");
		if(DBType.ORACLE==DBUtil.getDBType()){
			hql.append(" order by to_number(sortid)");
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			hql.append(" order by (sortid+0)");
		}
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("userid", userid);
		return PrivilegeUtil.getVOList(query(hql.toString(), params),GroupVO.class);
	}
	
	/**
	 * �����û����ID��ѯ���ӽڵ���û���
	 * @param id �û���ID
	 * @return �ӽڵ��û��鼯��List
	 * @throws PrivilegeException
	 */
	public List findByParentId(String id) throws PrivilegeException {
		StringBuffer hql =new StringBuffer( "from SmtGroup where parentid=:parentId ");
		if(DBType.ORACLE==DBUtil.getDBType()){
			hql.append(" order by to_number(sortid)");
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			hql.append(" order by (sortid+0)");
		}
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("parentId", id);
		return PrivilegeUtil.getVOList(query(hql.toString(), params),GroupVO.class);
	}
	
	/**
	 * �����û���ID��ѯ�û�����Ϣ
	 * @param id �û���ID
	 * @return GroupVO
	 * @throws PrivilegeException
	 */
	public GroupVO findById(String id) throws PrivilegeException{
		List list = getPrivilegeManager().getHbSession().createQuery("from SmtGroup where id=:id").setString("id", id).list();
		if(list == null || list.isEmpty()){
			return null;
		}
		SmtGroup smtGroup = (SmtGroup)list.get(0);
		GroupVO group = new GroupVO();
		BeanUtil.propertyCopy(smtGroup, group);
		return group;
	}
	
	/**
	 * ��ѯ���û��������û��б�
	 * @param userId �û�ID
	 * @return ���GroupVO��List�û��������û��б�
	 * @throws PrivilegeException
	 */
	public List<GroupVO> getGroupsByUserId(String userId)
			throws PrivilegeException {
		List list = new ArrayList();
		String hql = "from SmtGroup where id in (select groupid from SmtUsergroupref where userid=:userid)";
		list = getPrivilegeManager().getHbSession().createQuery(hql).setString("userid", userId).list();
		List listvo = PrivilegeUtil.getVOList(list,GroupVO.class);
		return listvo;
	}
	
	/**
	 * hashCode��֤
	 */
	public boolean validateHashCode(Object obj) throws PrivilegeException {
		GroupVO group = (GroupVO)obj;
		return super.validateHashCode(group.getId(), group.getHashcode(), SmtGroup.class);
	}

	/*
	 * ��ȡ�������
	 */
	private String getGroupOwnerId(String groupId) throws PrivilegeException{
		SmtGroup smtGroup = (SmtGroup)super.get(SmtGroup.class, groupId);
		if(smtGroup == null) throw new PrivilegeException("���û��鲻����");
		return smtGroup.getOwnerId();
	}
	
	/*
	 * ���ݵ�½��Ա��ȡ�����ɼ���Ŀ��PageQueryData
	 */
	private PageQueryData getPageQueryDataByUser(UserVO cueUser, int start,
			int limit, HashMap params) throws PrivilegeException{
		if (cueUser == null) {
			cueUser = getPrivilegeManager().getCueLoginUser();
		}
		Session sess = getPrivilegeManager().getHbSession();
		StringBuffer hql = new StringBuffer();
		PageQueryData pd = new PageQueryData();
		if (hasPermission(null, ResourcesPermissionConst.GROUP_ALL, CommomPermissionHelper.NORMAL_CHECK, null, null)) {// ����Ƿ��ǳ�������Ա��ɼ����е���Դ��Ȩ��
			hql.append("select count(*) from SmtGroup sg where 1=1 ");
		} else {
			hql.append(" select count(*) from SmtGroup sg where id in" +
					" (select groupid from SmtUsergroupref where userid ='"+cueUser.getId()+"')" +
					" or sg.ownerId = '"+cueUser.getId()+"'");
		}
		if (params != null) {
			Set<String> keys = params.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				hql.append(" and sg." + key + " like '%" + params.get(key) + "%'");
			}
		}
		Query query = sess.createQuery(hql.toString());
		// ȡ��¼����
		int totalcount = Integer.parseInt(query.list().get(0).toString());
		pd.setTotalCount(totalcount);
		return pd;
	}
	
	/*
	 * ��ȡ�û��Ĵ��ں���Ч��
	 */
	private void getUserUseful(String userId,String message) throws PrivilegeException{
		if(UserHelper.userIsExistOrEffective(super.getPrivilegeManager(),userId) == UserHelper.USER_UN_EXSIT){
			throw new PrivilegeException("��"+message+"������");
		}
		if(UserHelper.userIsExistOrEffective(super.getPrivilegeManager(),userId) == UserHelper.USER_EXSIT_UN_EFFECTIVE){
			throw new PrivilegeException("��"+message+"��Ч");
		}
	}
	
	/*
	 * ��ȡ��Ĵ�����Ч�����
	 * modelֵΪGROUP_EXISTʱ��ֻ�ж����Ƿ����
	 * 		  ΪGROUP_USEFULʱ���жϴ����Ժ���Ч��
	 *        ΪGROUP_UN_USEFULʱ���жϴ����Ժ���Ч��
	 */
	private void GroupUseful(SmtGroup group,int model,String message) throws PrivilegeException{
		if(message == null) message = "�û���";
		if(model == GroupHelper.GROUP_EXIST){
			if(GroupHelper.isUsefulGroup(group) == GroupHelper.GROUP_UN_EXIST)
				throw new PrivilegeException("��"+message+"������");
		}
		if(model == GroupHelper.GROUP_USEFUL){
			if(GroupHelper.isUsefulGroup(group) == GroupHelper.GROUP_UN_USEFUL){
				throw new PrivilegeException("��"+message+"��Ч");
			}
		}
		if(model == GroupHelper.GROUP_UN_USEFUL){
			if(GroupHelper.isUsefulGroup(group) == GroupHelper.GROUP_USEFUL){
				throw new PrivilegeException("��"+message+"��Ч");
			}
		}
	}
	
	/*
	 * �ж��û����Ƿ��г�Ա����������Դ
	 */
	private void isContainResources(String groupId,String message) throws PrivilegeException{
		List list_users=getGroupMember(groupId,GroupHelper.GROUP_ALL_MEMBER);
		if(list_users != null){
			throw new PrivilegeException("���û�����"+list_users.size()+"����Ч��Ա���ڣ�����ִ��"+message+"����");
		}//�жϸ��û����Ƿ��г�Ա
		//�жϸ��û����Ƿ������û���
		List<?> list = getPrivilegeManager().getHbSession().createQuery("from SmtGroup where parentid=:id").setString("id", groupId).list();
		if(!list.isEmpty()){
			throw new PrivilegeException("���û��麬�����û���,����ִ��"+message+"����");
		}
		IPermission ipermission = super.getPrivilegeManager().getIPermission();
		boolean temp = ipermission.isOwnEntityPermission(groupId);
		if(temp){
			throw new PrivilegeException("���û������������Դ,����ִ��"+message+"����");
		}//�жϸ��û����Ƿ���������Դ
	}
	
	/*
	 * ִ�д��û����Ƴ��û�����
	 */
	private void doRemoveUserFromGroup(String groupId,String userId) throws PrivilegeException{
		if(UserHelper.userIsExistOrEffective(super.getPrivilegeManager(),userId) == UserHelper.USER_UN_EXSIT){
			throw new PrivilegeException("���û�������");
		}
		if(GroupHelper.isGroupMember(super.getPrivilegeManager(), groupId, userId) == GroupHelper.UN_GROUP_MEMGBER){
			throw new PrivilegeException("����Ա�Ѿ��Ǹ����Ա,�����Ƴ�");
		}
		getPrivilegeManager().getHbSession().createQuery("delete from SmtUsergroupref where USERID=:userId and GROUPID=:groupId")
				.setString("groupId", groupId)
				.setString("userId", userId)
				.executeUpdate();
	}
	
}