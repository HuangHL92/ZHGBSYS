package com.insigma.odin.framework.privilege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.helper.CommomPermissionHelper;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.helper.UserHelper;
import com.insigma.odin.framework.privilege.util.AbstractPrivilegeBase;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.util.PageQueryData;
import com.insigma.odin.framework.privilege.util.PrivilegeUtil;
import com.insigma.odin.framework.privilege.util.ResourcesPermissionConst;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.util.HQuery;
import com.insigma.odin.framework.sys.cap.businesssys.util.HttpConnection;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.MD5;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;


/**
 * @author �·���
 * @created 18-����-2009 11:48:15
 */
public class UserRealControl extends AbstractPrivilegeBase implements
		IUserControl {

	public UserRealControl() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * ɾ���û�
	 * 
	 * @param user
	 *            ����Ҫɾ�����û�
	 * @return    true �Դ��û���ɾ�������ɹ���false �Դ��û���ɾ������ʧ��
	 * @throws PrivilegeException �����׳����쳣���û�������,�û��ڶ��Լ����зǷ�����
	 */
	public boolean deleteUser(UserVO user) throws PrivilegeException {
		if(user==null){
			throw new PrivilegeException("�����Ҫɾ�����û�Ϊnull");
		}
		String userId = user.getId();
		SmtUser smtUser = (SmtUser) super.get(SmtUser.class, userId);
		if(smtUser==null){
			throw new PrivilegeException("ɾ�����û�������");
		}
		if (super.getPrivilegeManager().getCueLoginUser().getLoginname()
				.equals(smtUser.getLoginname())) {
			throw new PrivilegeException("�û��ڶ��Լ����зǷ�����");
		}
		if (!super.hasPermission(null, ResourcesPermissionConst.USER_ALL_DELETE,
				CommomPermissionHelper.OWNER_CHECK, smtUser.getOwnerId(), null)) {// �Ƿ���Ȩ�޲���
			throw new PrivilegeException("�޲���Ȩ�ޣ�����ϵ����Ա");
		}
		if (userParamsValidata(userId,false)) {
			super.delete("from SmtUsergroupref sug where sug.userid='"
					+ userId + "'");
			super.delete("from SmtAct sa where sa.objectid='" + userId
					+ "'");
			super.delete("from SmtUser su where su.id='" + userId + "'");
			PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
			privilegeLog.log("ɾ���û�"+smtUser.getLoginname(), "����IUserControl�ӿڵ�deleteUser����", null);
			return true;
		}
		return false;	
	}

	
	
	/**
	 * Ϊ��ҳ��ѯ�ṩ�Ĳ�ѯ����
	 * 
	 * @param cueUser
	 *            �û�����
	 * @param start
	 *            ��ʼλ��
	 * @param limit
	 *            ��ҳʱÿҳ��ʾ��
	 * @param params
	 *            HashMap ��ѯRoleVO����������
	 * @param isAll �Ƿ�����ѯ�������г�Ա
	 * @return PageQueryData ���ط�ҳ��ѯ���ݶ���
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	public PageQueryData pageQueryByUserVO(UserVO cueUser, int start,
			int limit, HashMap params,boolean isAll) throws PrivilegeException {
		if (cueUser == null) {
			cueUser = getPrivilegeManager().getCueLoginUser();
		}
		Session sess = getPrivilegeManager().getHbSession();
		StringBuffer hql = new StringBuffer();
		PageQueryData pd = new PageQueryData();
		if (hasPermission(null, ResourcesPermissionConst.USER_ALL,
				CommomPermissionHelper.NORMAL_CHECK, null, null)) {// ����Ƿ��ǳ�������Ա��ɼ����е���Դ��Ȩ��
			hql.append("select count(distinct su) from SmtUser su,SmtUsergroupref g,Aaa1 a where su.id=g.userid and g.groupid=a.id.aab301 and su.status='1' and a.id.aaa148 in ("+params.get("gsearch")+") ");
		} else {
			hql
					.append("select count(distinct su) from SmtUser su,SmtUsergroupref g,Aaa1 a where su.id=g.userid and g.groupid=a.id.aab301 and su.status='1' and a.id.aaa148 in ("+params.get("gsearch")+") and (su.ownerId='"
							+ cueUser.getId()
							+ "' or "
							+ " su.id in ( select sug.userid from SmtUsergroupref sug where sug.groupid in"
							+ " (select sg.id from SmtGroup sg where sg.ownerId='"
							+ cueUser.getId()
							+ "') or sug.groupid in"
							+ " (select sug.groupid from SmtUsergroupref sug where sug.userid='"
							+ cueUser.getId() + "')))");
		}
		if (params != null) {
			Set<String> keys = params.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				if("querysql".equals(key)){
					continue;
				}
				if(!"gsearch".equals(key)) {
					hql.append(" and su." + key + " like '%" + params.get(key)
						+ "%'");
				}
			}
		}
		Query query = sess.createQuery(hql.toString());
		List list = query.list();
		// ȡ��¼����
		int totalCount = Integer.parseInt(query.list().get(0).toString());
		List roles = queryByUser(cueUser, start, limit, params,true);
		pd.setTotalCount(totalCount);
		try {
			pd.setData(HQuery.fromList(roles));
		} catch (AppException e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**
	 * ���ݵ�ǰ��¼���û��������Ŀɼ����û�//
	 * 
	 * @param cueUser
	 *            ��ǰ��¼���û�
	 * @param start
	 *            ��ʼ��
	 * @param limit
	 *            ������
	 * @param params
	 *            HashMap ��ѯRoleVO����������
	 * @param isAll �Ƿ�����ѯ�������г�Ա
	 * @return  list   ���ص�ǰ�û��Ŀɼ��û�
	 */
	public List queryByUser(UserVO cueUser, int start, int limit, HashMap params,boolean isAll)
			throws PrivilegeException {
		Session sess = super.getPrivilegeManager().getHbSession();
		SmtUser smtUser = (SmtUser) super.get(SmtUser.class, cueUser.getId());
		List list = new ArrayList();
		boolean querysql = false;
		if (super.hasPermission(null, ResourcesPermissionConst.USER_ALL,
				CommomPermissionHelper.NORMAL_CHECK, null,null)) {
				StringBuffer hql3 = new StringBuffer("select distinct su from SmtUser su,SmtUsergroupref g,Aaa1 a where su.id=g.userid and g.groupid=a.id.aab301 and su.status='1' and a.id.aaa148 in ("+params.get("gsearch")+") ");		
				if (params != null) {
					Set<String> keys = params.keySet();
					Iterator<String> it = keys.iterator();
					while (it.hasNext()) {
						String key = it.next();
						if("querysql".equals(key)){
							querysql = true;
							continue;
						}else{
							if(!"gsearch".equals(key)) {
								hql3.append(" and su." + key + " like '%" + params.get(key)
									+ "%'");
							}
						}
					}
				}
				if(querysql){
					hql3 = addSql(params,hql3);
				}
				Query q3 = sess.createQuery(hql3.toString());
				if (limit != -1) {
					q3.setFirstResult(start);
					q3.setMaxResults(limit);
				}
				return PrivilegeUtil.getVOList(q3.list(), UserVO.class);
		 }
		 
		StringBuffer sql = new StringBuffer("select distinct su from SmtUser su,SmtUsergroupref g,Aaa1 a where su.id=g.userid and g.groupid=a.id.aab301 and su.status='1' and a.id.aaa148 in ("+params.get("gsearch")+") and (su.ownerId='"
				+ cueUser.getId()
				+ "' or "
				+ " su.id in ( select sug.userid from SmtUsergroupref sug where sug.groupid in"
				+ " (select sg.id from SmtGroup sg where sg.ownerId='"
				+ cueUser.getId()
				+ "') or sug.groupid in"
				+ " (select sug.groupid from SmtUsergroupref sug where sug.userid='"
				+ cueUser.getId() + "')))");
		 if (params != null) {
				Set<String> keys = params.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = it.next();
					if("querysql".equals(key)){
						querysql = true;
						continue;
					}
					if(!"gsearch".equals(key)) {
						sql.append("and  su." + key + " like '%" + params.get(key)+ "%' ");
					}
				}
		 }		
		 if(querysql){
			sql = addSql(params,sql);
		 }
		Query query = sess.createQuery(sql.toString());
		if (limit != -1) {
			query.setFirstResult(start);
			query.setMaxResults(limit);
		}	
		return PrivilegeUtil.getVOList(query.list(),UserVO.class);
	}

	/**
	 * �����û��ģɣ�ע���û�
	 * 
	 * @param userId
	 *            Ҫע�����û��ģɣ�
	 * @return    true �Դ��û���ע�������ɹ���false �Դ��û���ע������ʧ��
	 * 
	 * @throws  PrivilegeException  �����׳����쳣���û��ڶ��Լ����зǷ��������û�״̬�Ѿ�Ϊ��Ч
	 */
	public boolean revokeUser(String userId) throws PrivilegeException {
		SmtUser smtUser = (SmtUser) get(SmtUser.class, userId);
		if (super.getPrivilegeManager().getCueLoginUser().getId().equals(userId)) {
			throw new PrivilegeException("�û��ڶ��Լ����зǷ�����");
		}
		if(smtUser==null){
			throw new PrivilegeException("���û�������");
		}
		if (!super.hasPermission(null, ResourcesPermissionConst.USER_ALL_REVOKE,
			CommomPermissionHelper.OWNER_CHECK, smtUser.getOwnerId(),null)) {
			throw new PrivilegeException("�޲���Ȩ��,����ϵ����Ա");
		}
		if (userParamsValidata(userId, false)) {
			if (smtUser.getStatus().equals("0")) {
				throw new PrivilegeException("���û�״̬�Ѿ�Ϊ��Ч");
			}
			smtUser.setStatus("0");
			super.update(smtUser);
			return true;
			}
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("ע���û�"+smtUser.getLoginname(), "����IUserControl�ӿڵ�revokeUser����", null);
		return false;	
	}

	/**
	 * �������û�
	 * 
	 * @param user
	 *            �豣����û�
	 * 
	 * @return    true �Դ��û��Ĵ��������ɹ�  false ��ʾû����Ӧ�Ĳ���Ȩ��
	 * @throws    PrivilegeException  �����׳����쳣���봫��Ҫ�������û����û��Ѵ��ڣ�ָ���ĳ�������Ч
	 */
	public boolean saveUser(UserVO user) throws PrivilegeException {
		if(user==null){
			throw new PrivilegeException("�봫��Ҫ�������û�");
		}
		if(super.hasPermission(null, null, CommomPermissionHelper.NORMAL_CHECK, null, null)){
			List list = (List) queryByName(user.getLoginname(), true);
			if (!list.isEmpty()){
				throw new PrivilegeException("�õ�¼���Ѵ��ڣ��뻻һ����¼��");
			}
			SmtUser smtUser = new SmtUser();
			SmtUser ownerUser = null;
			SmtGroup ownerGroup = null;
			BeanUtil.propertyCopy(user, smtUser);
			String ownerId = smtUser.getOwnerId();
			if(smtUser.getStatus()== null){
				smtUser.setStatus("1");
			}
			if (ownerId == null) {// �Ƿ�ָ��������
				smtUser.setOwnerId(super.getPrivilegeManager().getCueLoginUser().getId());// Ĭ��Ϊ��ǰ�û�
			} else {
				boolean userboolean =  ((ownerUser = (SmtUser) get(SmtUser.class, ownerId)) == null || ownerUser.getStatus().equals("0"));
				boolean groupboolean = ((ownerGroup = (SmtGroup) get(SmtGroup.class, ownerId))==null|| ownerGroup.getStatus().equals("0"));
				if(userboolean&&groupboolean){
					throw new PrivilegeException("ָ���ĳ�������Ч");
				}
			}
			String password = MD5.MD5(user.getPasswd());
			smtUser.setPasswd(password);
			CommonQueryBS.systemOut("password"+password);
			smtUser.setCreatedate(new java.sql.Date(System.currentTimeMillis()));
			super.save(smtUser);
			user.setId(smtUser.getId());
			
			PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
			privilegeLog.log("�����û�"+smtUser.getLoginname(), "����IUserControl�ӿڵ�saveUser����", null);
			return true;
		}
		return false;
	}

	/**
	 * �����û�
	 * 
	 * @param user
	 *            ����µ��û�
	 * @throws PrivilegeException �����׳����쳣���û��ڶ��Լ����зǷ��������û������ڣ��봫��Ҫ���µ��û�
	 * 
	 * @return    true �Դ��û��ĸ��²����ɹ���false �Դ��û��ĸ��²���ʧ�� 
	 * 
	 */
	public boolean updateUser(UserVO user) throws PrivilegeException {
		if(user==null){
			throw new PrivilegeException("�봫��Ҫ���µ��û�");
		}
		if(!super.getPrivilegeManager().getIPermission().isSuperManager(super.getPrivilegeManager().getCueLoginUser())){
			if (super.getPrivilegeManager().getCueLoginUser().getId().equals(
				user.getId())) {
			throw new PrivilegeException("�û��ڶ��Լ����зǷ�����");
			}
		}
		SmtUser SoldUser = (SmtUser) super.get(SmtUser.class, user.getId());
		UserVO UoldUser = new UserVO();
		BeanUtil.propertyCopy(SoldUser, UoldUser);
		if (SoldUser == null) {
			throw new PrivilegeException("���û�������");
		}
		//�����û�ʱ���ݲ����κ�Ȩ�޵��ж�  modify by lizs  20161013  ����ΰ���������ȷ���޸�
//		if (!super.hasPermission(null, ResourcesPermissionConst.USER_ALL_EDIT,
//				CommomPermissionHelper.OWNER_CHECK, SoldUser.getOwnerId(), null)) {
//			throw new PrivilegeException("�޲���Ȩ��,����ϵ����Ա");
//		}
		SmtUser smtUser = new SmtUser();
		BeanUtil.propertyCopy(user, smtUser);
/*		if(!validateHashCode(UoldUser)){
			throw new PrivilegeException("��ϣֵУ�鲻��ȷ");
		}*/
		if (SoldUser.getOwnerId()!=null && !SoldUser.getOwnerId().equals(user.getOwnerId())) {// �жϳ������Ƿ����ı�
			SmtUser newOwner = (SmtUser) super.get(SmtUser.class, user.getOwnerId());
			if (newOwner == null) {
				throw new PrivilegeException("���ĵ��³����߲�����");
			} else if (newOwner.getStatus().equals("0")) {
				throw new PrivilegeException("���ĵ��³�������Ч");
			}
		}
		if (!SoldUser.getStatus().equals(user.getStatus())) {	//�ж�״̬�Ƿ����ı�
			if(SoldUser.getStatus().equals("1")){
				if(someUserParamsValidata(SoldUser)){
					boolean sg = this.revokeUser(user.getId());
				}	
			}
		}
		if(!SoldUser.getLoginname().equals(user.getLoginname())){
			if(!hasSameLoginNameExsit(user.getLoginname())){
				throw new PrivilegeException("�õ�¼�����Ѿ�����");
			}
		}
		getPrivilegeManager().getHbSession().clear();
		super.update(smtUser);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("�����û�", "����IUserControl�ӿڵ�updateUser����", null);
		return true;	
	}

	/**
	 * ���ݴ���ĵ�¼�������û�����ģ��ƥ��;�ȷƥ��
	 * 
	 * @param name
	 *            ��¼��
	 * @param isEqual
	 *            ��ģ��ƥ�仹�Ǿ�ȷƥ��
	 * @return  list ���ظ��ݵ�¼�����ҵ����û�
	 * 
	 */
	public List<Object> queryByName(String name, boolean isEqual)
			throws PrivilegeException {
		HashMap<String, String> params = new HashMap<String, String>();
		StringBuffer hql = new StringBuffer("from SmtUser where loginname");
		if (isEqual) {
			params.put("name", name);
			hql.append("=:name");
		} else {
			hql.append(" like '%"+name+"%'");
		}
		String sql = hql.toString();
		return PrivilegeUtil.getVOList(query(sql, params), UserVO.class);
	}

	/**
	 * �鿴�û��ǲ���ӵ�пɷ���Ȩ�޵Ľ�ɫ
	 * 
	 * @param userId
	 *            �û�Id
	 * @return boolean ����û�ӵ�пɷ���Ȩ�޵Ľ�ɫ���� true�����򷵻�false
	 * 
	 */
	public boolean hasPrivilegeRole(String userId) {
		Session session = super.getPrivilegeManager().getHbSession();
		String hql = "from SmtAct sa where sa.objectid=:userId and dispatchauth='1'";
		Query query = session.createQuery(hql).setParameter("userId", userId);
		List list = query.list();
		if (list.size() != 0) {
			return true;
		}
		return false;
	}

	/**
	 * @param obj  �����Object
	 * @return  true��ʾͨ��hashCode����֤��false��ʾû��ͨ��hashCode����֤
	 */
	public boolean validateHashCode(Object obj) throws PrivilegeException {
		UserVO userVO = (UserVO) obj;
		return super.validateHashCode(userVO.getId(), userVO.getHashcode(),
				SmtUser.class);
	}

	/**
	 * ���������û�
	 * 
	 * @param userId
	 *            �������õ��û���Id
	 * @return boolean ����������óɹ�����true�����򷵻�false
	 * @throws PrivilegeException  �û���״̬�Ѿ�Ϊ��Ч
	 */
	public boolean reuseUser(String userId) throws PrivilegeException {
		SmtUser smtUser = (SmtUser) get(SmtUser.class, userId);
		if(smtUser==null){
			throw new PrivilegeException("���û�������");
		}
		if (!super.hasPermission(null, ResourcesPermissionConst.USER_ALL_REUSE,
				CommomPermissionHelper.OWNER_CHECK, smtUser.getOwnerId(),null)) {
			throw new PrivilegeException("�޲���Ȩ��,����ϵ����Ա");
		}
		if (smtUser.getStatus().equals("1")) {
			throw new PrivilegeException("���û���״̬�Ѿ�Ϊ��Ч");
		}
		smtUser.setStatus("1");
		super.update(smtUser);
		PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
		privilegeLog.log("���������û�"+smtUser.getLoginname(), "����IUserControl�ӿڵ�reuseUser����", null);
		return true;
	}

	/*
	 * ������֤���������
	 * 
	 * @param userId
	 *            �û���ID
	 * @param onlyExsitOrEffective
	 *            �ǲ���ֻ�Դ��ں���Ч�����жϣ�true��ʾ�ǣ�false��ʾ��Ҫ�����������ж�
	 * @rturn boolean true��ʾ��֤ͨ����false��ʾ��֤ʧ��
	 * @throws PrivilegeException �����׳����쳣�У��û�״̬Ϊ��Ч,�û����ڿɷ���Ȩ�޵Ľ�ɫ���û��������Ա���û�����������Դ���û�������
	 */
	private boolean userParamsValidata(String userId,
			boolean onlyExsitOrEffective) throws PrivilegeException {
		boolean reslut = true;
		SmtUser smtUser = (SmtUser) super.get(SmtUser.class, userId);
		if (smtUser != null) {
			if (onlyExsitOrEffective == false) {
				if (hasPrivilegeRole(smtUser.getId())) {
					throw new PrivilegeException("���û����ڿɷ���Ȩ�޵Ľ�ɫ");
				} else if (GroupHelper.isGroupLeader(super
						.getPrivilegeManager(), smtUser.getId())) {
					throw new PrivilegeException("���û��������Ա");
				} else if (this.isOwnEntityPermission(smtUser.getId())) {
					throw new PrivilegeException("���û�����������Դ");
				}
			}
			reslut = true;
		}
		if(smtUser==null){
			reslut= false;
			throw new PrivilegeException("���û�������");
		}
		return reslut;
	}

	/*
	 * ���û���һЩ��Ϣ������֤��Ҫ�ǣ��û��Ƿ���ڿɷ���Ȩ�޵Ľ�ɫ���Ƿ��������Ա���Ƿ����������Դ����
	 * 
	 * @param smtUser Ҫ������֤���û�
	 * @return boolean	��֤�ɹ�����true�����򷵻�false
	 * @throws PrivilegeException �û����ڿɷ���Ȩ�޵Ľ�ɫ���û��������Ա���û�����������Դ
	 */
	private boolean someUserParamsValidata(SmtUser smtUser)
			throws PrivilegeException {
		if (hasPrivilegeRole(smtUser.getId())) {
			throw new PrivilegeException("�û����ڿɷ���Ȩ�޵Ľ�ɫ");
		} else if (GroupHelper.isGroupLeader(super.getPrivilegeManager(),
				smtUser.getId())) {
			throw new PrivilegeException("�û��������Ա");
		} else if (this.isOwnEntityPermission(smtUser.getId())) {
			throw new PrivilegeException("�û�����������Դ");
		}
		return true;
	}

	/*
	 * �ж��û����Ƿ��Ѿ�����
	 * 
	 * @param userId
	 * @return true ��ʾ�û���������
	 * @throws PrivilegeException  ��ʾ�û����Ѿ�����
	 *             
	 */
	private boolean hasSameLoginNameExsit(String loginName)
			throws PrivilegeException {
		String hql = "from SmtUser su where su.loginname=:loginName";
		Session session = super.getPrivilegeManager().getHbSession();
		List list = session.createQuery(hql).setString("loginName", loginName)
				.list();
		if (list.size() != 0) {
			throw new PrivilegeException("�õ�¼���Ѿ����ڣ��뻻һ����¼��");
		}
		return true;
	}

	/**
	 *  �û��޸��Լ�����ķ���
	 *  @param  userId			Ҫ����������û���ID
	 *  @param  oldPassword		�û��ľ�����
	 *  @param  newPassword		�û���������
	 *  @return  boolean  true ��ʾ��������ɹ� false��ʾ��������ʧ��
	 *  @throws  PrivilegeException   �û������ڻ����û���Ч
	 */
	public boolean updatePassword(String userId, String oldPassword,
			String newPassword) throws PrivilegeException {
		UserVO cueUser = super.getPrivilegeManager().getCueLoginUser();
		SmtUser smtUser = (SmtUser) get(SmtUser.class, userId);
		if (UserHelper.userIsExistOrEffective(super.getPrivilegeManager(),
				userId) == UserHelper.USER_EXSIT_AND_EFFECTIVE) {
			if (cueUser.getId().equals(smtUser.getId())) {
				if (smtUser.getPasswd().equals(oldPassword)) {
					smtUser.setPasswd(MD5.MD5(newPassword));
					getPrivilegeManager().getHbSession().clear();
					super.update(smtUser);
					PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
					privilegeLog.log("�û�"+smtUser.getLoginname()+"�޸�����", "����IUserControl�ӿڵ�updatePassword����", null);
					return true;
				}
				return false;
			}
		}
		throw new PrivilegeException("�û������ڻ���ڵ��û���Ч");
	}
	
	
	/**
	 *  �û��޸��Լ�����ķ���
	 *  @param  userId			Ҫ����������û���ID
	 *  @param  oldPassword		�û��ľ�����
	 *  @param  newPassword		�û���������
	 *  @return  boolean  true ��ʾ��������ɹ� false��ʾ��������ʧ��
	 *  @throws  PrivilegeException   �û������ڻ����û���Ч
	 */
	public boolean updatePassword_uc(String userId, String oldPassword,
			String newPassword) throws PrivilegeException {
		UserVO cueUser = super.getPrivilegeManager().getCueLoginUser();
		SmtUser smtUser = (SmtUser) get(SmtUser.class, userId);
		if (UserHelper.userIsExistOrEffective(super.getPrivilegeManager(),
				userId) == UserHelper.USER_EXSIT_AND_EFFECTIVE) {
			if (cueUser.getId().equals(smtUser.getId())) {
				if (smtUser.getPasswd().equals(oldPassword)) {
					smtUser.setPasswd(newPassword);
					getPrivilegeManager().getHbSession().clear();
					super.update(smtUser);
					PrivilegeLogger privilegeLog = PrivilegeLogger.getLogger();
					privilegeLog.log("�û�"+smtUser.getLoginname()+"�޸�����", "����IUserControl�ӿڵ�updatePassword����", null);
					return true;
				}
				return false;
			}
		}
		throw new PrivilegeException("�û������ڻ���ڵ��û���Ч");
	}
	
	
	
	/**
	 * �����û���ID�����û�
	 * @param principalId
	 * @return
	 * @throws PrivilegeException 
	 */
	public UserVO findUserByUserId(String userID) throws PrivilegeException{
		UserVO userVO = new UserVO();
		SmtUser smtUser = (SmtUser) super.get(SmtUser.class, userID);
		BeanUtil.propertyCopy(smtUser, userVO);
		return userVO;
	}
	
	/**
	 * �鿴�û������е���Դ
	 * @param principalId
	 * @return true ��ʾ���и���Դ false��ʾ�����и���Դ
	 */
	public boolean isOwnEntityPermission(String principalId){
		Session session = super.getPrivilegeManager().getHbSession();
		String hql = "from SmtUser su where su.ownerId=:principalId";
		Query query = session.createQuery(hql).setString("principalId", principalId);
		List list = query.list();
		if(list.size()!=0){
			return true;
		}
		String hql2 = "from SmtGroup sg where sg.ownerId=:principalId";
		Query query2 = session.createQuery(hql2).setString("principalId", principalId);
		List list2 = query2.list();
		if(list.size()!=0){
			return true;
		}
		String hql3 = "from SmtRole sr where sr.owner=:principalId";
		Query query3 = session.createQuery(hql3).setString("principalId", principalId);//
		List list3 = query3.list();
		if(list.size()!=0){
			return true;
		}
		String hql4 = "from SmtScene ss where ss.owner=:principalId";
		Query query4 = session.createQuery(hql4).setString("principalId", principalId);
		List list4 = query4.list();
		if(list.size()!=0){
			return true;
		}
		String hql5 = "from SmtResource sr where sr.owner=:principalId";
		Query query5 = session.createQuery(hql5).setString("principalId", principalId);
		List list5 = query5.list();
		if(list.size()!=0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * Ϊ��ҳ��ѯ�ṩ�Ĳ�ѯ����:���ݵ�ǰ��¼�û���ѯ�ɼ��������û�
	 * @param cueUser  �û�����
	 * @param start  ��ʼλ��
	 * @param limit  ��ҳʱÿҳ��ʾ��
	 * @param params  HashMap,��ѯRoleVO����������
	 * @return PageQueryData ���ط�ҳ��ѯ���ݶ���
	 * @throws PrivilegeException
	 */
	@SuppressWarnings("unchecked")
	public PageQueryData pageQueryByUserVO(UserVO cueUser, int start,
			int limit, HashMap params) throws PrivilegeException {
		if (cueUser == null) {
			cueUser = getPrivilegeManager().getCueLoginUser();
		}
		Session sess = getPrivilegeManager().getHbSession();
		StringBuffer hql = new StringBuffer();
		PageQueryData pd = new PageQueryData();
		if (hasPermission(null, ResourcesPermissionConst.USER_ALL,
				CommomPermissionHelper.NORMAL_CHECK, null, null)) {// ����Ƿ��ǳ�������Ա��ɼ����е���Դ��Ȩ��
			hql.append("select count(*) from SmtUser su where su.status='1' and 1=1 ");
		} else {
			hql
					.append("select count(*) from SmtUser su where su.status='1' and (su.ownerId='"
							+ cueUser.getId()
							+ "' or "
							+ " su.id in ( select sug.userid from SmtUsergroupref sug where sug.groupid in"
							+ " (select sg.id from SmtGroup sg where sg.ownerId='"
							+ cueUser.getId()
							+ "') or sug.groupid in"
							+ " (select sug.groupid from SmtUsergroupref sug where sug.userid='"
							+ cueUser.getId() + "')))");
		}
		if (params != null) {
			Set<String> keys = params.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				if("querysql".equals(key)){
					continue;
				}else{
					hql.append(" and su." + key + " like '%" + params.get(key)
							+ "%'");
				}
			}
		}

		Query query = sess.createQuery(hql.toString());
		List list = query.list();
		// ȡ��¼����
		int totalCount = Integer.parseInt(query.list().get(0).toString());
		List roles = queryByUser(cueUser, start, limit, params);
		pd.setTotalCount(totalCount);
		try {
			pd.setData(HQuery.fromList(roles));
		} catch (AppException e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**
	 * ��ѯ��ǰ��¼���û��Ŀɼ����û�������start��limit��ֵ��ѯĳһҳ��ֵ;��limitΪ-1ʱ,��ʾ��ѯ��¼�����пɼ���.
	 * @param cueUser  ��ǰ��¼���û�
	 * @param start  ��ʼ��
	 * @param limit  ������
	 * @return  ���ص�ǰ�û��Ŀɼ��û��б�list
	 */
	public List queryByUser(UserVO cueUser, int start, int limit, HashMap params)
			throws PrivilegeException {
		Session sess = super.getPrivilegeManager().getHbSession();
		SmtUser smtUser = (SmtUser) super.get(SmtUser.class, cueUser.getId());
		List list = new ArrayList();
		boolean querysql =false;
		if (super.hasPermission(null, ResourcesPermissionConst.USER_ALL,
				CommomPermissionHelper.NORMAL_CHECK, null,null)) {
				StringBuffer hql3 = new StringBuffer("from SmtUser su where su.status='1' and 1=1 ");		
				if (params != null) {
					Set<String> keys = params.keySet();
					Iterator<String> it = keys.iterator();
					while (it.hasNext()) {
						String key = it.next();
						if("querysql".equals(key)){
							querysql=true;
							continue;
						}else{
							hql3.append(" and su." + key + " like '%" + params.get(key)
									+ "%'");
							}
						}
					}
				if(querysql){
					hql3 = addSql(params,hql3);
				}
				Query q3 = sess.createQuery(hql3.toString());
				if (limit != -1) {
					q3.setFirstResult(start);
					q3.setMaxResults(limit);
				}
				return PrivilegeUtil.getVOList(q3.list(), UserVO.class);
		 }
		 
		StringBuffer sql = new StringBuffer("select su from SmtUser su where su.status='1' and (su.ownerId='"
				+ cueUser.getId()
				+ "' or "
				+ " su.id in ( select sug.userid from SmtUsergroupref sug where sug.groupid in"
				+ " (select sg.id from SmtGroup sg where sg.ownerId='"
				+ cueUser.getId()
				+ "') or sug.groupid in"
				+ " (select sug.groupid from SmtUsergroupref sug where sug.userid='"
				+ cueUser.getId() + "')))");
		if (params != null) {
				Set<String> keys = params.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = it.next();
					if("querysql".equals(key)){
						querysql=true;
						continue;
					}else{
						sql.append("and  su." + key + " like '%" + params.get(key)+ "%' ");
					}
				}
		 }	
		if(querysql){
			sql = addSql(params,sql);
		}
		Query query = sess.createQuery(sql.toString());
		if (limit != -1) {
			query.setFirstResult(start);
			query.setMaxResults(limit);
		}	
		return PrivilegeUtil.getVOList(query.list(),UserVO.class);
	}
	
	public StringBuffer addSql(HashMap params,StringBuffer sql){
		List querylist = (List)params.get("querysql");
		for(int i=0;i<querylist.size();i++){
			if(i==0){
				sql.append(" order by su."+querylist.get(i).toString()+"");
			}else{
				sql.append(",su."+querylist.get(i).toString()+"");
			}
		}
		return sql;
	}
	
}