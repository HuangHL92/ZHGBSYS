package com.insigma.siis.local.pagemodel.sysorg.org;

import java.beans.IntrospectionException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.SQLQuery;
import org.hsqldb.lib.StringUtil;

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
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.axis.gzoa.caClient2;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.ReturnDO;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.business.sysorg.org.dto.B01DTO;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryPageModel;
import com.insigma.siis.local.util.FileUtil;
import com.utils.DBUtils;

public class SysOrgOtherPageModel extends PageModel {

	/**
	 * ϵͳ������Ϣ
	 */
	public Hashtable<String, Object> areaInfo = new Hashtable<String, Object>();
	public static String tag = "0";// 0δִ��1ִ��
	public static String b0101stauts = "0";// 0δ�ı�1�ı�
	public static String b0104stauts = "0";// 0δ�ı�1�ı�
	public static String b0194Type = "0";// ��λ����1��λ 2������� 3����
	public long st_num = 0;// �Զ����룬�������¼�¼��
	boolean flag_ns = false;
	boolean flag_fz = false;

	public SysOrgOtherPageModel() {
		try {
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname = user.getLoginname();
			@SuppressWarnings("unchecked")
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager = new DefaultPermission().isSuperManager(vo);

			Object[] area = null;
			if (groups.isEmpty() || issupermanager || loginnname.equals("admin")) {
				area = SysOrgBS.queryInit();
				areaInfo.put("manager", "true");
			} else {
				area = SysOrgBS.queryInit();
				areaInfo.put("manager", "false");
			}
			if (area != null) {
				if (area[2].equals("1")) {
					area[2] = "picOrg";
				} else if (area[2].equals("2")) {
					area[2] = "picInnerOrg";
				} else {
					area[2] = "picGroupOrg";
				}
				areaInfo.put("areaname", area[0]);
				areaInfo.put("areaid", area[1]);
				areaInfo.put("picType", area[2]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ҳ���ʼ��
	@Override
	public int doInit() throws RadowException {
		this.controlButton();
		
		if(!DBUtils.isNoGbmc(SysManagerUtils.getUserId())) {
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('bzzsid').hide();");
		}
		String userid = SysUtil.getCacheCurrentUser().getId();
		if(!"40288103556cc97701556d629135000f".equals(userid)) {
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('deleteOrgBtn').hide();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ��ȫ�������룬У������˵�λ����Ϊ�յĻ�����Ϣ���������ȷ�ϣ�ִ�д˷��� �б���ʾ���Ϸ��Ļ�����Ϣ
	 * 
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("scanLegalPerson")
	public int scanLegalPerson() throws RadowException, AppException {
		String userid = SysUtil.getCacheCurrentUser().getId();
		String sql = "select " + " case when t.b0194='1' then '���˵�λ' " + " when t.b0194='2' then '�������' "
				+ " when t.b0194='3' then '��������' else '' end b0194,"// ��������(���˵�λ��ʶ ��(1���˵�λ 2���赥λ 3��������))
				+ " t.b0114,"// ��������
				+ " t.b0101,"// ��������
				+ " t2.b0101 b0101parent,"// ���ڵ�����
				+ " t.b0104,"// ���
				+ " t.b0111,"// ��������
				+ " t.b0117,"// ��������
				+ " t.b0124,"// ������ϵ
				+ " t.b0131,"// �������
				+ " t.b0127,"// ��������
				+ " s.type"// Ȩ������(0�������1��ά��)
				+ "  from b01 t,competence_userdept s,b01 t2 " + " where " + " t.b0111=s.b0111 "
				+ " and t.b0121=t2.b0111 " + " and s.userid='" + userid + "' "// �û�id
				+ " and (t.b0114 is null or length(trim(t.b0114))=0) "// Ϊ��
				+ " and t.b0194='1' "// ���˵�λ
		;
		CommQuery cq = new CommQuery();

		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		String jgjh_sql = "select t.b0111 " + sql.substring(sql.indexOf("from"), sql.length());
		request.getSession().setAttribute("jgjh_sql", jgjh_sql);
		this.getPageElement("orgInfoGrid").setValueList(list);
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ��ȫ�������룬У������Ϸ��ı�����Ϣ���������ȷ�ϣ���ѯ���Ϸ���У����Ϣʱ��ִ�д˷��� �б���ʾ���Ϸ��Ļ�����Ϣ
	 * 
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("scanWrongfulUnid")
	public int scanWrongfulUnid() throws RadowException, AppException {
		String userid = SysUtil.getCacheCurrentUser().getId();
		String sql = "select " + " case when t.b0194='1' then '���˵�λ' " + " when t.b0194='2' then '�������' "
				+ " when t.b0194='3' then '��������' else '' end b0194,"// ��������(���˵�λ��ʶ ��(1���˵�λ 2���赥λ 3��������))
				+ " t.b0114,"// ��������
				+ " t.b0101,"// ��������
				+ " t2.b0101 b0101parent,"// ���ڵ�����
				+ " t.b0104,"// ���
				+ " t.b0111,"// ��������
				+ " t.b0117,"// ��������
				+ " t.b0124,"// ������ϵ
				+ " t.b0131,"// �������
				+ " t.b0127,"// ��������
				+ " s.type"// Ȩ������(0�������1��ά��)
				+ "  from b01 t,competence_userdept s ,b01 t2 " + " where " + " t.b0111=s.b0111 "
				+ " and t.b0121=t2.b0111 " + " and s.userid='" + userid + "' "// �û�id
				+ " and t.b0114 is not null "// ��Ϊ��
				+ " and length(t.b0114)!=0 "// ��Ϊ�գ���Ӧmysql��
		;
		if (DBType.ORACLE == DBUtil.getDBType()) {
			sql = sql + " and not regexp_like(t.b0114, '^([a-zA-Z0-9]{3}[.])*([a-zA-Z0-9]{3})$') ";
		} else if (DBType.MYSQL == DBUtil.getDBType()) {
			sql = sql + " and t.b0114  not regexp  '^([a-zA-Z0-9]{3}[.])*([a-zA-Z0-9]{3})$'";
		}
		CommQuery cq = new CommQuery();

		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		String jgjh_sql = "select t.b0111 " + sql.substring(sql.indexOf("from"), sql.length());
		request.getSession().setAttribute("jgjh_sql", jgjh_sql);
		this.getPageElement("orgInfoGrid").setValueList(list);
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ˫����������������ѯ��ť��ִ�д˷��� ���ݻ���id����ѯ��ǰ��������һ�㼶������Ϣ���б���ʾ
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("orgInfoGrid.dogridquery")
	public int doOrgQuery(int start, int limit) throws RadowException {
		String sort = request.getParameter("sort");// Ҫ���������--���趨�壬ext�Զ���
		String dir = request.getParameter("dir");// Ҫ����ķ�ʽ--���趨�壬ext�Զ���
		String orderby = "";
		limit = 20;
		String size = (String) request.getSession().getAttribute("SOOPageSize");
		if (size != null && !"".equals(size)) {
			limit = Integer.parseInt(size);
		}
		if (sort != null && !"".equals(sort)) {
			if ("type".equalsIgnoreCase(sort)) {
				orderby = "order by s." + sort + " " + dir;
			} else {
				orderby = "order by t." + sort + " " + dir;
			}
		}
		String tab_flag = this.getPageElement("tab_flag").getValue();
		String userid = SysUtil.getCacheCurrentUser().getId();
		if ("tab1".equals(tab_flag)) {// ��ʾ����tabҳ
			String orgid = this.getPageElement("checkedgroupid").getValue();
			String bhxa_check = this.getPageElement("bhxa_check").getValue();// 1 ѡ�У������¼��� 0δѡ�У��������¼���
			String sql = "select " + " case when t.b0194='1' then '���˵�λ' " + " when t.b0194='2' then '�������' "
					+ " when t.b0194='3' then '��������' else '' end b0194,"// ��������(���˵�λ��ʶ ��(1���˵�λ 2���赥λ 3��������))
					+ " t.b0114,"// ��������
					+ " t.b0101,"// ��������
					+ " t2.b0101 b0101parent,"// ���ڵ�����
					+ " t.b0104,"// ���
					+ " t.b0111,"// ��������
					+ " t.b0117,"// ��������
					+ " t.b0124,"// ������ϵ
					+ " t.b0131,"// �������
					+ "t.sign_code SignCode, " // ��������֤��������/�ι�����ҵ
					+ " t.unify_code  unifyCode," // ͳһ������ô���
					+ " t.b0127,"// ��������
					+ " s.type"// Ȩ������(0�������1��ά��)
					+ " ,p.grouptype" + "  from b01 t,competence_userdept s,b01 t2,REFB01GROUP p  " + " where "
					+ " t.b0111=s.b0111 " + " and t.b0121=t2.b0111 " + " and t.b0111=p.b0111(+) " + " and s.userid='"
					+ userid + "' ";// �û�id
			if ("1".equals(bhxa_check)) {// �����������¼�
				sql = sql + " and t.b0111  like '" + orgid + "%' ";// ��������
			} else {
				sql = sql + " and ( t.b0121='" + orgid + "' ";// �ϼ���λ����//��һ��
				sql = sql + " or t.b0111='" + orgid + "' )";// ��������//����
			}
			if ("".equals(orderby)) {
				sql = sql + " order by length(t.b0111),t.SORTID asc ";
			} else {
				sql = sql + orderby;
			}
			String jgjh_sql = "select t.b0111 " + sql.substring(sql.indexOf("from"), sql.length());
			request.getSession().setAttribute("jgjh_sql", jgjh_sql);
			this.pageQuery(sql, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		} else if ("tab2".equals(tab_flag)) {// �л�����ѯtabҳ
			String sql = "select " + " case when t.b0194='1' then '���˵�λ' " + " when t.b0194='2' then '�������' "
					+ " when t.b0194='3' then '��������' else '' end b0194,"// ��������(���˵�λ��ʶ ��(1���˵�λ 2���赥λ 3��������))
					+ " t.b0114,"// ��������
					+ " t.b0101,"// ��������
					+ " t2.b0101 b0101parent,"// ���ڵ�����
					+ " t.b0104,"// ���
					+ " t.b0111,"// ��������
					+ " t.b0117,"// ��������
					+ " t.b0124,"// ������ϵ
					+ " t.b0131,"// �������
					+ " t.b0127,"// ��������
					+ " s.type"// Ȩ������(0�������1��ά��)
					+ " ,p.grouptype" + "  from b01 t, competence_userdept s,b01 t2,REFB01GROUP p " + " where "
					+ " t.b0111=s.b0111 " + " and t.b0121=t2.b0111 " + " and t.b0111=p.b0111(+) " + " and s.userid='"
					+ userid + "' ";// �û�id

			sql = returnSBSql(sql);
			if (!"".equals(orderby)) {
				sql = sql + orderby;
			}
			String jgjh_sql = "select t.b0111 " + sql.substring(sql.indexOf("from"), sql.length());
			request.getSession().setAttribute("jgjh_sql", jgjh_sql);
			this.pageQuery(sql, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		} else {
			return EventRtnType.NORMAL_SUCCESS;
		}
	}

	/**
	 * tabҳ��Ĳ�ѯ����
	 * 
	 * @param sql
	 * @return
	 * @throws RadowException
	 */
	public String returnSBSql(String sql) throws RadowException {
		String b0101 = this.getPageElement("b0101_h").getValue();// ��������
		String b0114 = this.getPageElement("b0114_h").getValue();// ��������
		String b0194 = this.getPageElement("b0194_h").getValue();// ��λ����
		String b0104 = this.getPageElement("b0104_h").getValue();// ���

		String b0117 = this.getPageElement("b0117").getValue();// ��������
		String b0124 = this.getPageElement("b0124").getValue();// ������ϵ
		String b0131 = this.getPageElement("b0131").getValue();// �������
		String b0127 = this.getPageElement("b0127").getValue();// ��������

		// B01 b01 = new B01();
		// this.copyElementsValueToObj(b01, this);
		// System.out.println(b01.toString());

		if (b0101 != null && !b0101.equals("")) {
			sql += " and t.b0101 like '%" + StringEscapeUtils.escapeSql(b0101.trim()) + "%' ";
		}
		if (b0104 != null && !b0104.trim().equals("")) {
			sql += " and t.b0104 like '%" + StringEscapeUtils.escapeSql(b0104.trim()) + "%' ";
		}

		if (b0114 != null && !b0114.trim().equals("")) {
			sql += " and t.b0114 like'%" + StringEscapeUtils.escapeSql(b0114.trim()) + "%' ";
		}
		if (b0117 != null && !b0117.trim().equals("")) {
			sql += " and t.b0117='" + StringEscapeUtils.escapeSql(b0117.trim()) + "' ";
		}
		if (b0124 != null && !b0124.trim().equals("")) {
			sql += " and t.b0124='" + StringEscapeUtils.escapeSql(b0124.trim()) + "' ";
		}
		if (b0127 != null && !b0127.trim().equals("")) {
			sql += " and t.b0127='" + StringEscapeUtils.escapeSql(b0127.trim()) + "' ";
		}
		if (b0131 != null && !b0131.trim().equals("")) {
			sql += " and t.b0131='" + StringEscapeUtils.escapeSql(b0131.trim()) + "' ";
		}
		if (b0194 != null && !b0194.trim().equals("")) {
			sql += " and t.b0194='" + StringEscapeUtils.escapeSql(b0194.trim()) + "' ";
		}
		// String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		// sql +="and c.b0111=b.b0111 and c.userid='"+cueUserid+"'";
		return sql;
	}

	/**
	 * ˫�������� ���ñ�ѡ��Ļ���id,��ҳ��,���Ҹ��ݵ�λ��ѯ���б���ʾ
	 * 
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("querybyid")
	public int query(String id) throws RadowException {
		this.getExecuteSG().addExecuteCode("funcAfterLoad();");// ���ò�ѯ�б��־,���Ϸ����룬���˵�λΪ�ձ��� ��־Ϊfalse(tab tab1
																// ���Ϸ����룬���˵�λΪ�ձ��� 4����ʾ�б�)
		// this.setRadow_parent_data(id.trim());
		String userID = SysManagerUtils.getUserId();
		Map<String, String> map = PublicWindowPageModel.isHasRule(id, userID);
		if (!map.isEmpty() || map == null) {
			if ("2".equals(map.get("type"))) {
				this.setMainMessage("��û�иû�����Ȩ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		this.getPageElement("checkedgroupid").setValue(id);// ���ñ�ѡ��Ļ���id,��ҳ��
		// String userid=SysUtil.getCacheCurrentUser().getId();
//		String sql=  " select t.b0111 from b01 t,competence_userdept s "
//		+ " where "
//			+ " t.b0111=s.b0111 "
//			+ " and s.userid='"+userid+"' "//�û�id
//			+ " and ( t.b0121='"+id+"' "//�ϼ���λ����   ��
//			+ " or t.b0111='"+id+"' )"//��������
//			+ " order by t.b0111,t.b0121 ";
		request.setAttribute("sort", "");
		request.setAttribute("dir", "");
		this.setNextEventName("orgInfoGrid.dogridquery");// ���ݻ���id��ѯ������Ϣ
		// this.getExecuteSG().addExecuteCode("selectNode('"+id+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// ��ʼ����֯������
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		String sql = "from B01 where B0121='" + node + "' order by sortid";
		@SuppressWarnings("unchecked")
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();// �õ���ǰ��֯��Ϣ
		// ֻ��ʾ���ڵ���֯���¼���֯ ������֯�� ����ʾȫ��
		List<B01> choose = new ArrayList<B01>();
		String sql2 = "from B01 where B0111='" + node + "'";
		List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();// �õ���ǰ��֯��Ϣ
		for (int i = 0; i < groups.size(); i++) {
			for (int j = 0; j < groups.size(); j++) {
				if (groups.get(j).getB0111().equals(groups.get(i).getB0121())) {
					groups.remove(i);
					i--;
				}
			}
		}
		boolean equel = false;
		if (!groups.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < groups.size(); j++) {
					if (groups.get(j).getB0111().equals(list.get(i).getB0111())) {
						choose.add(groups.get(j));
						equel = true;
					}
				}
			}
		}
		if (equel) {
			list = choose;
		}
		StringBuffer jsonStr = new StringBuffer();
		String companyOrgImg = request.getContextPath() + "/pages/sysorg/org/images/companyOrgImg2.png";
		String insideOrgImg = request.getContextPath() + "/pages/sysorg/org/images/insideOrgImg1.png";
		String groupOrgImg = request.getContextPath() + "/pages/sysorg/org/images/groupOrgImg1.png";
		String wrongImg = request.getContextPath() + "/pages/sysorg/org/images/wrong.gif";
		String path = companyOrgImg;

		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		String loginnname = user.getLoginname();
		@SuppressWarnings("unchecked")
		List<GroupVO> groups1 = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
		UserVO vo = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
		boolean issupermanager = new DefaultPermission().isSuperManager(vo);
		List<String> b0111s = new ArrayList();
		boolean isadmin = false;
		if (groups1.isEmpty() || issupermanager || loginnname.equals("admin")) {
			isadmin = true;
		} else {
			b0111s = authority(cueUserid);
		}

		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (B01 group : list) {
				Boolean own = isown(b0111s, group.getB0111(), isadmin);
				if (i == 0 && last == 1) {
					if (group.getB0194().equals("2")) {
						path = insideOrgImg;
					} else if (group.getB0194().equals("3")) {
						path = groupOrgImg;
					} else {
						path = companyOrgImg;
					}
					if (!own) {
						path = wrongImg;
					}
					jsonStr.append("[{\"text\" :\"" + group.getB0101() + "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + SysOrgBS.hasChildren(group.getB0111())
//							+ "\" ,\"cls\" :\"folder\",");
							+ " ,\"cls\" :\"folder\",\"icon\":\"" + path + "\",");
					if (own) {
						jsonStr.append("\"href\":");
						jsonStr.append("\"javascript:radow.doEvent('querybyid','" + group.getB0111() + "')\"");
					} else {
						jsonStr.append("\"name\":");
						jsonStr.append("\"\"");
					}
					jsonStr.append("}]");
				} else if (i == 0) {
					if (group.getB0194().equals("2")) {
						path = insideOrgImg;
					} else if (group.getB0194().equals("3")) {
						path = groupOrgImg;
					} else {
						path = companyOrgImg;
					}
					if (!own) {
						path = wrongImg;
					}
					jsonStr.append("[{\"text\" :\"" + group.getB0101() + "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + SysOrgBS.hasChildren(group.getB0111())
							+ " ,\"cls\" :\"folder\",\"icon\":\"" + path + "\",");
					if (own) {
						jsonStr.append("\"href\":");
						jsonStr.append("\"javascript:radow.doEvent('querybyid','" + group.getB0111() + "')\"");
					} else {
						jsonStr.append("\"name\":");
						jsonStr.append("\"\"");
					}
					jsonStr.append("}");
				} else if (i == (last - 1)) {
					if (group.getB0194().equals("2")) {
						path = insideOrgImg;
					} else if (group.getB0194().equals("3")) {
						path = groupOrgImg;
					} else {
						path = companyOrgImg;
					}
					if (!own) {
						path = wrongImg;
					}
					jsonStr.append(",{\"text\" :\"" + group.getB0101() + "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + SysOrgBS.hasChildren(group.getB0111())
							+ " ,\"cls\" :\"folder\",\"icon\":\"" + path + "\",");
					if (own) {
						jsonStr.append("\"href\":");
						jsonStr.append("\"javascript:radow.doEvent('querybyid','" + group.getB0111() + "')\"");
					} else {
						jsonStr.append("\"name\":");
						jsonStr.append("\"\"");
					}
					jsonStr.append("}]");
				} else {
					if (group.getB0194().equals("2")) {
						path = insideOrgImg;
					} else if (group.getB0194().equals("3")) {
						path = groupOrgImg;
					} else {
						path = companyOrgImg;
					}
					if (!own) {
						path = wrongImg;
					}
					jsonStr.append(",{\"text\" :\"" + group.getB0101() + "\" ,\"id\" :\"" + group.getB0111()
							+ "\" ,\"leaf\" :" + SysOrgBS.hasChildren(group.getB0111())
							+ " ,\"cls\" :\"folder\",\"icon\":\"" + path + "\",");
					if (own) {
						jsonStr.append("\"href\":");
						jsonStr.append("\"javascript:radow.doEvent('querybyid','" + group.getB0111() + "')\"");
					} else {
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

	// ������ť
	@PageEvent("queryGroupBtn.onclick")
	public int queryGroupBtn() throws RadowException, Exception {
		this.openWindow("queryGroupWin", "pages.sysorg.org.QueryGroup");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �ල��Ϣ �������ѡ������������ѣ������½�
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("YearCheckBtn.onclick")
	public int YearCheck() throws RadowException, Exception {
		// ��ȡǰ̨grid����
		Grid grid = (Grid) this.getPageElement("orgInfoGrid");

		List<HashMap<String, Object>> list = grid.getValueList();

		int num = 0;
		String groupid = "";
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			String checked = map.get("personcheck") + "";
			if ("true".equals(checked)) {
				num = num + 1;
				groupid = (String) map.get("b0111");
			}
		}
		if (num > 1) {
			this.setMainMessage("����ѡ��һ������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (num < 1) {
			this.setMainMessage("��ѡ��һ������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		System.out.println("+++++++++++++=" + groupid);
		String ctxPath = request.getContextPath();
		// this.setRadow_parent_data(groupid);
		this.getExecuteSG().addExecuteCode("$h.openWin('YearCheck','pages.sysorg.org.YearCheck','��ȿ���ҳ��',760,460,'"
				+ groupid + "','" + ctxPath + "');");
		// this.openWindow("YearCheck", "pages.sysorg.org.YearCheck");
		this.request.getSession().setAttribute("tag", "0");

		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �½��¼����� �����ѡ��У��ѡ��λ����������������ѣ������½�
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("addOrgWinBtn.onclick")
	public int addOrgWin() throws RadowException, Exception {

		String loginName = SysUtil.getCacheCurrentUser().getLoginname();
		// ��ȡǰ̨grid����
		Grid grid = (Grid) this.getPageElement("orgInfoGrid");

		List<HashMap<String, Object>> list = grid.getValueList();

		int num = 0;
		String groupid = "";
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			String checked = map.get("personcheck") + "";
			if ("true".equals(checked)) {
				num = num + 1;
				groupid = (String) map.get("b0111");
			}
		}

		if (num > 1) {
			this.setMainMessage("�½��¼�����������ѡ��һ���ϼ�����!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		// String pardata = "";
		// String ctxPath = request.getContextPath();
		if (!SysOrgBS.selectB01Count().equals("1")) {// ���ݿ��У��Ƿ����Ψһ������������������
			if (num == 0) {
				groupid = this.getPageElement("checkedgroupid").getValue();
				if (groupid == null || groupid.length() == 0) {
					this.setMainMessage("��ѡ�����!");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			// pardata = "1,"+groupid;
			// this.setRadow_parent_data("1,"+this.getPageElement("checkedgroupid").getValue());

			if (!SysRuleBS.havaRule(groupid)) {// ��Ȩ�ޣ������if

				// �ж��Ƿ��ڹ���Աϵͳ��һ��Ŀ¼���½����������
				String[] str_arr = groupid.split("\\.");
				if (str_arr.length == 2) {// ��һ��Ŀ¼���½�
					String str = "";
					String sql = "";
					try {
						// �ж��û��Ƿ����Ŀ¼ά��Ȩ�ޣ���������Ȩ�ޣ��������������������޴�Ȩ��
						CurrentUser user = SysUtil.getCacheCurrentUser();
						sql = "select Count(1) from COMPETENCE_USERDEPT t where t.userid='" + user.getId()
								+ "' and t.type='1' ";
						str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();

					} catch (Exception e) {
						e.printStackTrace();
					}
					if (str.equals("0") && !"system".equals(loginName)) {// ������Ŀ¼ά��Ȩ��
						this.setMainMessage("���޴�Ȩ��!");
						return EventRtnType.NORMAL_SUCCESS;
						// throw new RadowException("���޴�Ȩ��!");
					} else {
						// ����Ŀ¼Ȩ��
					}
				} else {// ��Ȩ��
					this.setMainMessage("���޴�Ȩ��!");
					return EventRtnType.NORMAL_SUCCESS;

				}
			}
		} else {
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname = user.getLoginname();
			@SuppressWarnings("unchecked")
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager = new DefaultPermission().isSuperManager(vo);
			if (groups.isEmpty() || issupermanager || loginnname.equals("admin") || loginnname.equals("system")) {
				// pardata = "1,"+"-1";
				// this.setRadow_parent_data("1,"+"-1");
			} else {
				this.setMainMessage("���޴�Ȩ��!");
				return EventRtnType.NORMAL_SUCCESS;

			}
		}
		if (!SysOrgBS.selectB01Count().equals("1")) {// ���ݿ��У��Ƿ����Ψһ������������������
			List<B01> listB01 = HBUtil.getHBSession().createSQLQuery("select * from b01 where b0111='" + groupid + "'")
					.addEntity(B01.class).list();
			if (listB01.size() == 0) {// ���ݿ��в�������
				this.setMainMessage("������˫����������");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}

		this.setRadow_parent_data(groupid);
		// this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin','pages.sysorg.org.CreateSysOrg','�½��¼�����ҳ��',800,500,'"+pardata+"','"+ctxPath+"');");
		this.openWindow("addOrgWin", "pages.sysorg.org.CreateSysOrg");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �������� �����ѡ��У��ѡ��λ����������������ѣ����ܲ鿴
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("orgPortrait")
	public int orgPortrait() throws RadowException, Exception {

		String loginName = SysUtil.getCacheCurrentUser().getLoginname();
		// ��ȡǰ̨grid����
		Grid grid = (Grid) this.getPageElement("orgInfoGrid");

		List<HashMap<String, Object>> list = grid.getValueList();

		int num = 0;
		String groupid = "";
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			String checked = map.get("personcheck") + "";
			if ("true".equals(checked)) {
				num = num + 1;
				groupid = (String) map.get("b0111");
			}
		}

		if (num > 1) {
			this.setMainMessage("�鿴�������񣬽���ѡ��һ������!");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String isContain = this.getPageElement("bhxa_check").getValue();

		HttpSession session = request.getSession();
		session.setAttribute("orgid", groupid);
		session.setAttribute("isContain", isContain);
		// this.setRadow_parent_data(groupid);
		// System.out.println("��������"+groupid);
		this.getExecuteSG().addExecuteCode("insertInfo('" + groupid + "')");
		return 0;
	}

	/**
	 * �Ҽ��½��¼����� �����ѡ��У��ѡ��λ����������������ѣ������½�
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("addOrgWinBtnFunc")
	public int addOrgWinFunc(String groupid) throws RadowException, Exception {

		if (groupid == null || groupid.length() == 0) {
			this.setMainMessage("�����쳣!");
			return EventRtnType.NORMAL_SUCCESS;
		}

		// String pardata = "";
		// String ctxPath = request.getContextPath();
		if (!SysOrgBS.selectB01Count().equals("1")) {
			// pardata = "1,"+groupid;
			// this.setRadow_parent_data("1,"+this.getPageElement("checkedgroupid").getValue());

			if (!SysRuleBS.havaRule(groupid)) {// ���޸�Ȩ�ޣ������if

				// �ж��Ƿ��ڹ���Աϵͳ��һ��Ŀ¼���½����������
				String[] str_arr = groupid.split("\\.");
				if (str_arr.length == 2) {// ��һ��Ŀ¼���½�
					String str = "";
					String sql = "";
					try {
						// �ж��û��Ƿ����Ŀ¼ά��Ȩ�ޣ���������Ȩ�ޣ��������������������޴�Ȩ��
						CurrentUser user = SysUtil.getCacheCurrentUser();
						sql = "select Count(1) from COMPETENCE_USERDEPT t where t.userid='" + user.getId()
								+ "' and t.type='1' ";
						str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();

					} catch (Exception e) {
						e.printStackTrace();
					}
					if (str.equals("0")) {// ������Ŀ¼ά��Ȩ��
						this.setMainMessage("���޴�Ȩ��!");
						return EventRtnType.NORMAL_SUCCESS;
						// throw new RadowException("���޴�Ȩ��!");
					} else {
						// ����Ŀ¼Ȩ��
					}
				} else {// ��Ȩ��
					this.setMainMessage("���޴�Ȩ��!");
					return EventRtnType.NORMAL_SUCCESS;
					// throw new RadowException("���޴�Ȩ��!");
				}
			}
		} else {
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname = user.getLoginname();
			@SuppressWarnings("unchecked")
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo = PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager = new DefaultPermission().isSuperManager(vo);
			if (groups.isEmpty() || issupermanager || loginnname.equals("admin") || loginnname.equals("system")) {
				// pardata = "1,"+"-1";
				// this.setRadow_parent_data("1,"+"-1");
			} else {
				this.setMainMessage("���޴�Ȩ��!");
				return EventRtnType.NORMAL_SUCCESS;
				// throw new RadowException("���޴�Ȩ��!");
			}
		}
		this.setRadow_parent_data(groupid);
		// this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin','pages.sysorg.org.CreateSysOrg','�½��¼�����ҳ��',800,500,'"+pardata+"','"+ctxPath+"');");
		this.openWindow("addOrgWin", "pages.sysorg.org.CreateSysOrg");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// ����ְ��
	@PageEvent("Sydw.onclick")
	public int expBtn() throws RadowException, AppException {

		Grid grid = (Grid) this.getPageElement("orgInfoGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		List<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>();
		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			String personcheck = list.get(i).get("personcheck").toString();
			if (personcheck.equals("true")) {
				index += 1;
				list2.add(list.get(i));
			}
		}
		if (list == null || list.size() == 0) {
			this.setMainMessage("��˫����������ѯ!");
			return EventRtnType.NORMAL_SUCCESS;
		} else if (index != 1) {
			this.setMainMessage("��ѡ��һ���������в�ѯ!");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String b0111 = (String) list2.get(0).get("b0111");// ��������
		String b0194 = (String) list2.get(0).get("b0194");// ��������
		HBSession sess = HBUtil.getHBSession();
		List<?> UNITID1 = sess
				.createSQLQuery("select UNITID from b01 where b0111 like '" + b0111 + "%' and sign_code='2'").list();
		String UNITID = "";
		if (UNITID1.size() > 0) {
			UNITID = UNITID1.toString();
			UNITID = UNITID.replaceAll(" ", "");
		}
		request.getSession().setAttribute("UNITID", UNITID);
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('SydwOrgWin','pages.sysorg.org.SydwSysOrg','��ҵ��λ���ƺ˶���',1250,510,'','" + ctxPath + "');");

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("Cgsy.onclick")
	public int expBtnn() throws RadowException, AppException {

		Grid grid = (Grid) this.getPageElement("orgInfoGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		List<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>();
		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			String personcheck = list.get(i).get("personcheck").toString();
			if (personcheck.equals("true")) {
				index += 1;
				list2.add(list.get(i));
			}
		}
		if (list == null || list.size() == 0) {
			this.setMainMessage("��˫����������ѯ!");
			return EventRtnType.NORMAL_SUCCESS;
		} else if (index != 1) {
			this.setMainMessage("��ѡ��һ���������в�ѯ!");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String b0111 = (String) list2.get(0).get("b0111");// ��������
		String b0194 = (String) list2.get(0).get("b0194");// ��������
		HBSession sess = HBUtil.getHBSession();
		List<?> UNITID1 = sess
				.createSQLQuery("select UNITID from b01 where b0111 like '" + b0111 + "%' and sign_code='3'").list();
		String UNITID = "";
		if (UNITID1.size() > 0) {
			UNITID = UNITID1.toString();
			UNITID = UNITID.replaceAll(" ", "");
		}
		request.getSession().setAttribute("UNITID2", UNITID);
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('CgsyOrgWin','pages.sysorg.org.CgsySysOrg','�ι���ҵ���ƺ˶���',1250,510,'','" + ctxPath + "');");

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("Xzjg.onclick")
	public int expBtnnn() throws RadowException, AppException {

		Grid grid = (Grid) this.getPageElement("orgInfoGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		List<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>();
		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			String personcheck = list.get(i).get("personcheck").toString();
			if (personcheck.equals("true")) {
				index += 1;
				list2.add(list.get(i));
			}
		}
		if (list == null || list.size() == 0) {
			this.setMainMessage("��˫����������ѯ!");
			return EventRtnType.NORMAL_SUCCESS;
		} else if (index != 1) {
			this.setMainMessage("��ѡ��һ���������в�ѯ!");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String b0111 = (String) list2.get(0).get("b0111");// ��������
		String b0194 = (String) list2.get(0).get("b0194");// ��������
		HBSession sess = HBUtil.getHBSession();
		List<?> UNITID1 = sess
				.createSQLQuery("select UNITID from b01 where b0111 like '" + b0111 + "%' and sign_code='1'").list();
		String UNITID = "";
		if (UNITID1.size() > 0) {
			UNITID = UNITID1.toString();
			UNITID = UNITID.replaceAll(" ", "");
		}
		request.getSession().setAttribute("UNITID3", UNITID);
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('XzjgOrgWin','pages.sysorg.org.XzjgSysOrg','�������ر��ƺ˶���',1250,510,'','" + ctxPath + "');");

		return EventRtnType.NORMAL_SUCCESS;
	}

	// �޸İ�ť
	@PageEvent("updateWinBtn.onclick")
	public int updateWinBtn() throws RadowException, AppException {

		// ��ȡǰ̨grid����
		Grid grid = (Grid) this.getPageElement("orgInfoGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		if (list == null || list.size() == 0) {
			this.setMainMessage("��˫����������ѯ!");
			return EventRtnType.NORMAL_SUCCESS;
		}

		int num = 0;
		String groupid = "";
		for (int i = 0; i < list.size(); i++) {
			if ("true".equals(list.get(i).get("personcheck") + "")) {
				num = num + 1;
				// groupid=groupid+(String)list.get(i).get("b0111")+",";
			}
		}

		if (num == 0) {// �����㣬�в�ѯ�������û��ѡ��Ĭ��Ϊȫѡ
			String b0101 = "";
			for (int i = 0; i < list.size(); i++) {
				if (!"1".equals((String) list.get(i).get("type"))) {// Ȩ������ ������ 1��û���޸�Ȩ��
					b0101 = b0101 + (String) list.get(i).get("b0101") + "/";
				}
			}
			if (b0101.length() > 0) {
				this.setMainMessage("��û��Ȩ���޸Ļ����� " + b0101.substring(0, b0101.length() - 1));
				return EventRtnType.NORMAL_SUCCESS;
			}
			for (int i = 0; i < list.size(); i++) {
				groupid = groupid + (String) list.get(i).get("b0111") + ",";
			}
		} else {// ��ѡ��
			String b0101 = "";
			for (int i = 0; i < list.size(); i++) {
				if ("true".equals(list.get(i).get("personcheck") + "")) {
					if (!"1".equals((String) list.get(i).get("type"))) {// Ȩ������ ������ 1��û���޸�Ȩ��
						b0101 = b0101 + (String) list.get(i).get("b0101") + "/";
					}
				}

			}
			if (b0101.length() > 0) {
				b0101 = b0101.substring(0, b0101.length() - 1);
				this.setMainMessage("��û��Ȩ���޸Ļ����� " + b0101);
				return EventRtnType.NORMAL_SUCCESS;
			}
			for (int i = 0; i < list.size(); i++) {
				if ("true".equals(list.get(i).get("personcheck") + "")) {
					groupid = groupid + (String) list.get(i).get("b0111") + ",";
				}
			}
		}

		groupid = groupid.substring(0, groupid.length() - 1);// ȡ������

		String pardata = "";
		String ctxPath = request.getContextPath();
		// String groupid = this.getPageElement("checkedgroupid").getValue();

		pardata = "2," + groupid;
		// this.getPageElement("unitidDbclAlter").setValue(pardata);
		request.getSession().setAttribute("unitidDbclAlter", pardata);//
		// this.request.getSession().setAttribute("tag", "0");
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('updateOrgWin','pages.sysorg.org.UpdateSysOrg','������Ϣ�޸�ҳ��',1250,550,'','" + ctxPath + "');");
		// this.openWindow("updateOrgWin", "pages.sysorg.org.UpdateSysOrg");
		// this.setRadow_parent_data("2,"+this.getPageElement("checkedgroupid").getValue());
		return EventRtnType.NORMAL_SUCCESS;
	}

	// �Ҽ��޸İ�ť
	@PageEvent("updateWinBtnFunc")
	public int updateWinBtnFunc(String id) throws RadowException, AppException {
//		if(groupid==null||groupid.length()==0){
//			this.setMainMessage("�����쳣!");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
//		String arr[]=groupid.split(",");
//		if(!"1".equals(arr[1])){
//			this.setMainMessage("��û��Ȩ���޸�!");
//			return EventRtnType.FAILD;
//		}
//		String ctxPath = request.getContextPath();
//		String pardata="2,"+arr[0];
//		this.request.getSession().setAttribute("tag", "0");
//		this.getExecuteSG().addExecuteCode("$h.openWin('updateOrgWin','pages.sysorg.org.UpdateSysOrg','������Ϣ�޸�ҳ��',860,510,'"+pardata+"','"+ctxPath+"');");

		// ��ȡǰ̨grid����
		Grid grid = (Grid) this.getPageElement("orgInfoGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		if (list == null || list.size() == 0) {
			this.setMainMessage("��˫����������ѯ!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String groupid = "";

		for (int i = 0; i < list.size(); i++) {
			groupid = groupid + (String) list.get(i).get("b0111") + ",";
		}

		groupid = groupid.substring(0, groupid.length() - 1);// ȡ������

		String pardata = "";
		String ctxPath = request.getContextPath();

		String arr[] = id.split(",");
		pardata = arr[0] + "," + groupid;
		request.getSession().setAttribute("unitidDbclAlter", pardata);//
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('updateOrgWin','pages.sysorg.org.UpdateSysOrg','������Ϣ�޸�ҳ��',1250,550,'','" + ctxPath + "');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// ������Ϣ����
	@PageEvent("impOrgInfo.onclick")
	public int impOrgInfo() throws RadowException {
		String id = this.getPageElement("checkedgroupid").getValue();
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('orgimpWin','pages.sysorg.org.impOrgInfo','������Ϣ����',480,520,'"
				+ id + "','" + ctxPath + "');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// ����ť
	@PageEvent("sortSysOrgBtn.onclick")
	public int sortSysOrg() throws RadowException {
		String id = this.getPageElement("checkedgroupid").getValue();
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('orgSortWin','pages.sysorg.org.OrgSort','��������',505,520,'" + id + "','" + ctxPath + "');");
		// this.openWindow("orgSortWin", "pages.sysorg.org.OrgSort");
		this.request.getSession().setAttribute("transferType", "orgSort");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// ɾ����ť����¼�
	@PageEvent("deleteOrgBtn")
	public int deleteOrg() throws RadowException, AppException {

		// ��ȡǰ̨grid����
		Grid grid = (Grid) this.getPageElement("orgInfoGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		if (list == null || list.size() == 0) {
			this.setMainMessage("��˫����������ѯ!");
			return EventRtnType.NORMAL_SUCCESS;
		}

		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if ("true".equals(list.get(i).get("personcheck") + "")) {
				count = count + 1;
			}
		}
		String unitname = "";// ��������
		String groupid = "";
		if (count == 0) {// �в�ѯ���������û��ѡ��Ĭ��ȫѡ

			String b0101 = "";
			for (int i = 0; i < list.size(); i++) {
				if (!"1".equals((String) list.get(i).get("type"))) {// Ȩ������ ������ 1��û���޸�Ȩ��
					b0101 = b0101 + (String) list.get(i).get("b0101") + "/";
				}
			}
			if (b0101.length() > 0) {
				this.setMainMessage("��û��Ȩ��ɾ������: " + b0101.substring(0, b0101.length() - 1));
				return EventRtnType.NORMAL_SUCCESS;
			}
//			for(int i=0;i<list.size();i++){
//				//�жϻ������Ƿ�����Ա
//				if(SysOrgBS.whetherPersonOn1((String)list.get(i).get("b0111"))>0){//��ְ��Ա
//					unitname=unitname+(String)list.get(i).get("b0101")+"/";
//					groupid =groupid+(String)list.get(i).get("b0111")+",";
//				}
//			}
//			if(groupid.length()>0){//����Ա
//				unitname=unitname.substring(0, unitname.length()-1);
//				groupid=groupid.substring(0, groupid.length()-1);
//				this.getExecuteSG().addExecuteCode("queryPersonByGroupId('"+groupid+"','"+unitname+"');");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			for(int i=0;i<list.size();i++){
//				if(SysOrgBS.whetherPersonOn2((String)list.get(i).get("b0111"))>0){//���˻���ʷ��Ա
//					unitname=unitname+(String)list.get(i).get("b0101")+"/";
//					groupid =groupid+(String)list.get(i).get("b0111")+",";
//				}
//			}
//			if(groupid.length()>0){//����Ա
//				unitname=unitname.substring(0, unitname.length()-1);
//				groupid=groupid.substring(0, groupid.length()-1);
//				this.getExecuteSG().addExecuteCode("queryPersonByGroupId1('"+groupid+"','"+unitname+"');");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			
//			for(int i=0;i<list.size();i++){
//				//�鿴�Ƿ����¼�����
//				if(SysOrgBS.whetherHasChile((String)list.get(i).get("b0111"))>0){
//					unitname=unitname+(String)list.get(i).get("b0101")+"/";
//					groupid=groupid+(String)list.get(i).get("b0111")+",";
//				}
//			}
//			if(groupid.length()>0){
//				this.setMainMessage(unitname.substring(0, unitname.length()-1)+"�����¼�����������ɾ��!");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			for (int i = 0; i < list.size(); i++) {
				// groupid=groupid+(String)list.get(i).get("b0111")+",";
				unitname = unitname + (String) list.get(i).get("b0101") + "/";
			}
			if (((String) list.get(0).get("b0111")).equals("001.001")) {
				this.setMainMessage("����ɾ������λ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			groupid = (String) list.get(0).get("b0111") + ",";
		} else {// ��ѡ��
			String b0101 = "";
			for (int i = 0; i < list.size(); i++) {
				if (!"1".equals((String) list.get(i).get("type"))) {// Ȩ������ ������ 1��û���޸�Ȩ��
					if ("true".equals(list.get(i).get("personcheck") + "")) {
						b0101 = b0101 + (String) list.get(i).get("b0101") + "/";
					}
				}
			}
			if (b0101.length() > 0) {
				this.setMainMessage("��û��Ȩ��ɾ������: " + b0101.substring(0, b0101.length() - 1));
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*
			 * for(int i=0;i<list.size();i++){
			 * if("true".equals(list.get(i).get("personcheck")+"")){ //�жϻ������Ƿ�����Ա
			 * if(SysOrgBS.whetherPersonOn1((String)list.get(i).get("b0111"))>0){//��ְ��Ա
			 * unitname=unitname+(String)list.get(i).get("b0101")+"/"; groupid
			 * =groupid+(String)list.get(i).get("b0111")+","; } } }
			 * if(groupid.length()>0){//����Ա unitname=unitname.substring(0,
			 * unitname.length()-1); groupid=groupid.substring(0, groupid.length()-1);
			 * this.getExecuteSG().addExecuteCode("queryPersonByGroupId('"+groupid+"','"+
			 * unitname+"');"); return EventRtnType.NORMAL_SUCCESS; } for(int
			 * i=0;i<list.size();i++){ if("true".equals(list.get(i).get("personcheck")+"")){
			 * if(SysOrgBS.whetherPersonOn2((String)list.get(i).get("b0111"))>0){//���˻���ʷ��Ա
			 * unitname=unitname+(String)list.get(i).get("b0101")+"/"; groupid
			 * =groupid+(String)list.get(i).get("b0111")+","; } } }
			 * if(groupid.length()>0){//����Ա unitname=unitname.substring(0,
			 * unitname.length()-1); groupid=groupid.substring(0, groupid.length()-1);
			 * this.getExecuteSG().addExecuteCode("queryPersonByGroupId1('"+groupid+"','"+
			 * unitname+"');"); return EventRtnType.NORMAL_SUCCESS; } for(int
			 * i=0;i<list.size();i++){ if("true".equals(list.get(i).get("personcheck")+"")){
			 * //�鿴�Ƿ����¼����� if(SysOrgBS.whetherHasChile((String)list.get(i).get("b0111"))>0){
			 * unitname=unitname+(String)list.get(i).get("b0101")+"/";
			 * groupid=groupid+(String)list.get(i).get("b0111")+","; } } }
			 * if(groupid.length()>0){ this.setMainMessage(unitname.substring(0,
			 * unitname.length()-1)+"�����¼�����������ɾ��!"); return EventRtnType.NORMAL_SUCCESS; }
			 */
			if ("true".equals(list.get(0).get("personcheck") + "")) {
				groupid = groupid + (String) list.get(0).get("b0111") + ",";
				unitname = (String) list.get(0).get("b0101") + "�����¼�����";
			} else {
				for (int i = 0; i < list.size(); i++) {
					if ("true".equals(list.get(i).get("personcheck") + "")) {
						if (((String) list.get(0).get("b0111")).equals("001.001")) {
							groupid = "";
							unitname = "";
							this.setMainMessage("����ɾ������λ��");
							return EventRtnType.NORMAL_SUCCESS;
						}
						groupid = groupid + (String) list.get(i).get("b0111") + ",";
						unitname = unitname + (String) list.get(i).get("b0101") + "/";
					}
				}
			}

		}

		if (unitname.lastIndexOf("/") == unitname.length() - 1) {
			unitname = unitname.substring(0, unitname.length() - 1);
		}
		for (int i = 0; i < list.size(); i++) {
			@SuppressWarnings("unchecked")
			List<String> person= HBUtil.getHBSession().createSQLQuery("select a0000 from a02  where a0201b like '"+(String) list.get(0).get("b0111")+"%'").list();
			if(person.size()>0) {
				this.setMainMessage("�õ�λ���¼���λ����Ա��ְ���޷�ɾ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		// B01 b01 = SysOrgBS.LoadB01(groupid);
		this.getPageElement("delete_groupid").setValue(groupid);// Ҫɾ���Ļ���id���ص�ҳ��
		/* dialog_set("deleteExec","ȷ��ɾ������\""+unitname+"\"��"); */
		this.getExecuteSG().addExecuteCode("againReq('" + unitname + "');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("deleteExec")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteExec() throws RadowException, SQLException, IntrospectionException, IllegalAccessException,
			InvocationTargetException, AppException {
		String userid = SysUtil.getCacheCurrentUser().getId();
		HBSession sess = HBUtil.getHBSession();
		String groupid = this.getPageElement("delete_groupid").getValue();
		if (groupid.contains(",")) {// �û���ѡ��box
			groupid = groupid.substring(0, groupid.length() - 1);
		}
		if (groupid == null || groupid.length() == 0) {
			this.setMainMessage("�����쳣!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] groupids = groupid.split(",");
		for (String g : groupids) {
			List<B01> listb01 = HBUtil.getHBSession().createSQLQuery("select * from b01 where b0111='" + g + "'")
					.addEntity(B01.class).list();
			List<B01> listb01p = HBUtil.getHBSession()
					.createSQLQuery("select * from b01 where b0111='" + listb01.get(0).getB0121() + "'")
					.addEntity(B01.class).list();// ��һ��
			List<B01> listb01g = HBUtil.getHBSession()
					.createSQLQuery("select * from b01 where b0111='" + listb01p.get(0).getB0121() + "'")
					.addEntity(B01.class).list();// �϶���
			// ƴfilename
			String filename = "";
			if (listb01g.get(0).getB0111().equals("-1")) {
				filename = listb01p.get(0).getB0101() + ">>>" + listb01.get(0).getB0101();
			} else {
				filename = listb01g.get(0).getB0101() + ">>>" + listb01p.get(0).getB0101() + ">>>"
						+ listb01.get(0).getB0101();
			}

			// ��������
			try {
				record(g, filename,
						"select a0000 from a01 where exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0201b like '"
								+ g + "%')",
						UUID.randomUUID().toString().replace("-", "").substring(0, 10), userid);
			} catch (Exception e) {
				e.printStackTrace();
				/*
				 * this.setMainMessage("����ʧ�ܣ�ɾ�����ܽ��У�"); return EventRtnType.FAILD;
				 */
				throw new RadowException("����ʧ�ܣ�ɾ�����ܽ��У�");
			}

			// ɾ�������µ��û�
			try {
				deleteUser(g);
			} catch (Exception e) {
				// this.setMainMessage("ɾ�������µ��û�ʧ�ܣ�ɾ�����ܽ��У�");
				e.printStackTrace();
				throw new RadowException("ɾ�������µ��û�ʧ�ܣ�ɾ�����ܽ��У�");
			}

			// ɾ����Ա
			try {
				String personsql = "select a0000 from a01 where exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0201b like '"
						+ g + "%')";
				deletePerson(personsql, g);
			} catch (Exception e) {
				// this.setMainMessage("�����µ���Աɾ��ʧ�ܣ�ɾ�����ܽ��У�");
				e.printStackTrace();
				throw new RadowException("��Աɾ��ʧ�ܣ�ɾ�����ܽ��У�");
				// return EventRtnType.FAILD;
			}
			// ɾ������
			try {
				SysOrgBS.delB01(g);
			} catch (Exception e) {
				// this.setMainMessage("����ɾ��ʧ�ܣ�");
				e.printStackTrace();
				// return EventRtnType.FAILD;
				throw new RadowException("����ɾ��ʧ�ܣ�");
			}
		}

		// ˢ���б�
		this.setMainMessage("ɾ���ɹ�");
		this.setNextEventName("orgInfoGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		this.getExecuteSG().addExecuteCode("Ext.WindowMgr.getActive().close();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// �Ҽ�ɾ����ť����¼�
	@PageEvent("deleteOrgBtnFunc")
	public int deleteOrgFunc(String groupid) throws RadowException, AppException {
		if (groupid == null || groupid.length() == 0) {
			this.setMainMessage("�����쳣!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String arr[] = groupid.split(",");
		if (!"1".equals(arr[1])) {
			this.setMainMessage("��û��Ȩ��ɾ��!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*
		 * if(SysOrgBS.whetherHasChile(arr[0])>0){ this.setMainMessage("�����¼�����������ɾ��!");
		 * return EventRtnType.NORMAL_SUCCESS; }
		 */
		/*
		 * B01 b01 = SysOrgBS.LoadB01(arr[0]); int s1=SysOrgBS.whetherPersonOn1(arr[0]);
		 * int s2=SysOrgBS.whetherPersonOn2(arr[0]); if(s1>0){
		 * this.getExecuteSG().addExecuteCode("queryPersonByGroupId('"+arr[0]+"','"+b01.
		 * getB0101()+"');"); return EventRtnType.NORMAL_SUCCESS; }else if(s2>0){//
		 * this.getExecuteSG().addExecuteCode("queryPersonByGroupId1('"+arr[0]+"','"+b01
		 * .getB0101()+"');"); return EventRtnType.NORMAL_SUCCESS; }
		 */
		/*
		 * if(SysOrgBS.whetherPersonOn(arr[0])>0){
		 * this.getExecuteSG().addExecuteCode("queryPersonByGroupId('"+arr[0]+"','"+b01.
		 * getB0101()+"');"); return EventRtnType.NORMAL_SUCCESS; }
		 */
		this.getPageElement("delete_groupid").setValue(arr[0]);// Ҫɾ���Ļ���id���ص�ҳ��
		List<String> listb0101 = HBUtil.getHBSession()
				.createSQLQuery("select b0101 from b01 where b0111='" + arr[0] + "'").list();
		this.getExecuteSG().addExecuteCode("againReq('" + listb0101 + "');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// ɾ���¼�
//	@PageEvent("deleteconfirm")
//	public int deleteconfirm() throws RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
//		String groupid = this.getPageElement("checkedgroupid").getValue();
//		if(SysOrgBS.selectCountBySubId(groupid).equals("0")){
//			
//			//-------------------���������õĴ���------------------------------------
//			CurrentUser user = SysUtil.getCacheCurrentUser();
//			request.getSession().setAttribute("userid", user);
//			SysOrgBS.selectOut(groupid,user);
//			//------------------------------------------------------------------------
//			SysOrgBS.delB01(groupid);
//			this.getPageElement("checkedgroupid").setValue("");
//			this.getExecuteSG().addExecuteCode("window.reloadTree()");
//		}else{
//			dialog_set("deleteAll","ȷ��ɾ��ѡ�еĻ�����������������");
//		}
//		return EventRtnType.NORMAL_SUCCESS;
//	}

	/**
	 * ɾ����������Ա���������ݱ��浽��ʷ��
	 * 
	 * @return
	 * @throws RadowException
	 * @throws SQLException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws AppException
	 */
	@PageEvent("deleteAll")
	public int deleteAll() throws RadowException, SQLException, IntrospectionException, IllegalAccessException,
			InvocationTargetException, AppException {
		// ɾ������
		String groupid = this.getPageElement("delete_groupid").getValue();
		if (groupid == null || groupid.length() == 0) {
			this.setMainMessage("�����쳣!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String arr[] = groupid.split(",");
		String b0111 = "";
		for (int i = 0; i < arr.length; i++) {
			b0111 = arr[i];
			String sql = "UPDATE b01 SET b0121 = '-2' WHERE b0111='" + b0111 + "'";
			HBUtil.executeUpdate(sql);
			this.getExecuteSG().addExecuteCode("window.reloadTree()");
			/*
			 * groupid=arr[i]; CurrentUser user = SysUtil.getCacheCurrentUser();
			 * //-------------------���������õĴ��� request.getSession().setAttribute("userid",
			 * user); //------------�������վ SysOrgBS.selectOut(groupid,user);
			 * //-------------ɾ���������¼� ɾ��Ȩ�� ɾ������У������� SysOrgBS.delB01(groupid);
			 * 
			 * //-------ά���ֶΣ��ڸ÷����м�����Ҫ���е�SQL boolean flag =
			 * SysOrgBS.maintenanceB01(groupid); if(flag){
			 * this.getPageElement("checkedgroupid").setValue("");
			 * this.getExecuteSG().addExecuteCode("window.reloadTree()"); //
			 * this.getExecuteSG().addExecuteCode("$('#hideDiv').hide();"); }else{ String
			 * unitname=HBUtil.getValueFromTab("b0101", "b01", "b0111='"+groupid+"' ");
			 * if(arr.length==1){//ɾ��һ������ this.setMainMessage(unitname+"��������δ����!");
			 * }else{//ɾ��������� if(i<arr.length-1){ String name_arr=""; for(int
			 * j=i+1;j<arr.length;j++){//ѭ��δɾ���Ļ��� name_arr=name_arr+arr[j]+"/"; }
			 * this.setMainMessage(unitname+"��������δ����,"+name_arr.substring(0,
			 * name_arr.length()-1)+"δɾ��!"); }else{
			 * this.setMainMessage(unitname+"��������δ����!"); } }
			 * this.getExecuteSG().addExecuteCode("window.reloadTree()");
			 * //this.getExecuteSG().addExecuteCode("$('#hideDiv').hide();"); return
			 * EventRtnType.FAILD; }
			 */
		}

		// ɾ������������������Ա������Աstatus�ĳ�4��

		// ˢ���б�
		this.setMainMessage("ɾ���ɹ���");
		this.setNextEventName("orgInfoGrid.dogridquery");

		return EventRtnType.NORMAL_SUCCESS;
	}

	// ������ת��
	@PageEvent("transferSysOrg.onclick")
	public int transferSysOrg() throws RadowException {
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('transferSysOrgWin','pages.sysorg.org.Zjzzy','������ϵ���',766,500,'zjzzy','" + ctxPath + "');");
		// this.openWindow("transferSysOrgWin", "pages.sysorg.org.Zjzzy");
		this.request.getSession().setAttribute("transferType", "transferSysOrg");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("outFile.onclick")
	public int outFile() throws RadowException {
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('outFilegWin','pages.sysorg.org.OutFile','��������',520,400,'zjzzy','" + ctxPath + "');");
		// this.openWindow("transferSysOrgWin", "pages.sysorg.org.Zjzzy");
		this.request.getSession().setAttribute("transferType", "transferSysOrg");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// ����ת����Ա
	@PageEvent("batchTransferPersonnel.onclick")
	public int batchTransferPersonnel() throws RadowException {
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('batchTransferPersonnelWin','pages.sysorg.org.ZjzzyPerson','��Ա����ת��',806,520,'plzy','"
						+ ctxPath + "');");
		// this.openWindow("batchTransferPersonnelWin", "pages.sysorg.org.Zjzzy");
		this.request.getSession().setAttribute("tag", "0");
		this.request.getSession().setAttribute("transferType", "batchTransferPersonnel");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// ˢ�±�ҳ��
	@PageEvent("reloadWin")
	public int reloadWin(String id) throws RadowException {
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}

	// ���ð�ť
	@PageEvent("reset.onclick")
	@NoRequiredValidate
	@OpLog
	public int resetonclick() throws RadowException, AppException {
		this.getPageElement("SysOrgTree").setValue("");
		this.getPageElement("SysOrgTreeIds").setValue("");
		this.getPageElement("b0101").setValue("");
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

	// �༭ҳ��
	@PageEvent("openSelectOrgWinBtn.onclick")
	public int openUpdateOrgWin() throws RadowException, AppException {
		this.getExecuteSG().addExecuteCode("window.location.href='" + request.getContextPath()
				+ "/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrg'");
		return EventRtnType.NORMAL_SUCCESS;
	}

	private void dialog_set(String fnDelte, String strHint) {
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

	@PageEvent("saveBtn.onclick")
	public int LegalEntitySave() throws AppException, RadowException {
		String b0111 = this.getPageElement("b0111").getValue();// ��������
		if (b0111.equals("") || null == b0111) {
			this.setMainMessage("��ѡ�����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (!SysRuleBS.havaRule(b0111)) {
			throw new RadowException("���޴�Ȩ��!");
		}
		this.setNextEventName("validation.result");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// Ч��
	@PageEvent("validation.result")
	public int validation() throws RadowException {
		// String b0121 =this.getPageElement("b0121").getValue();//�ϼ���λ����
		String b0114 = this.getPageElement("b0114").getValue();
		String b0111 = this.getPageElement("b0111").getValue();// ��������
		String radio = this.getPageElement("b0194").getValue();
		String b0101 = this.getPageElement("b0101").getValue();
		String b0104 = this.getPageElement("b0104").getValue();
		if (b0114 == null || b0114.trim().equals("")) {
			this.setMainMessage("������������룡");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (b0101 == null || b0101.trim().equals("")) {
			this.setMainMessage("������������ƣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (b0104 == null || b0104.trim().equals("")) {
			this.setMainMessage("�����������ƣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (radio == null || radio.trim().equals("")) {
			this.setMainMessage("��ѡ��������ͣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (radio.equals("LegalEntity")) {
			this.b0194Type = "1";
		} else if (radio.equals("InnerOrg")) {
			this.b0194Type = "2";
		} else {
			this.b0194Type = "3";
		}
		B01 b01 = new B01();
		PMPropertyCopyUtil.copyElementsValueToObj(b01, this);
		ReturnDO<String> returnDO = CreateSysOrgBS.groupValidate(b01);
		if (!returnDO.isSuccess()) {
			this.setMainMessage(returnDO.getErrorMsg());
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (b01.getB0101().equals("")) {
			this.setMainMessage("������������ƣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (b01.getB0104().equals("")) {
			this.setMainMessage("�������ƣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (b0194Type.equals("1")) {
			if (b01.getB0114().trim().equals("")) {
				this.setMainMessage("������������룡");
				return EventRtnType.NORMAL_SUCCESS;
			}
//			if(b01.getB0117().equals("")){
//				this.setMainMessage("�������������������");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			if(b01.getB0127().equals("")){
//				this.setMainMessage("�������������");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			if(b01.getB0131().equals("")){
//				this.setMainMessage("������������");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			if(b01.getB0124().equals("")){
//				this.setMainMessage("������������ϵ��");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			if (b01.getB0183() == null) {
				/*
				 * this.setMainMessage("������Ӧ����ְ�쵼ְ����"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0183((long) 0);
			}
			if (b01.getB0185() == null) {
				/*
				 * this.setMainMessage("������Ӧ�丱ְ�쵼ְ����"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0185((long) 0);
			}
			/*
			 * if(b01.getB0188()==null){ this.setMainMessage("������Ӧ��ͬ����ְ���쵼ְ����"); return
			 * EventRtnType.NORMAL_SUCCESS; } if(b01.getB0189()==null){
			 * this.setMainMessage("������Ӧ��ͬ����ְ���쵼ְ����"); return EventRtnType.NORMAL_SUCCESS; }
			 */
			if (b01.getB0227() == null) {
				/*
				 * this.setMainMessage("������������������"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0227((long) 0);
			}
			if (b01.getB0232() == null) {
				/*
				 * this.setMainMessage("��������ҵ������(�ι�)��"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0232((long) 0);
			}
			if (b01.getB0233() == null) {
				/*
				 * this.setMainMessage("��������ҵ������(����)��"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0233((long) 0);
			}
			/*
			 * if (b01.getB0234() == null) {
			 * 
			 * this.setMainMessage("������"); return EventRtnType.NORMAL_SUCCESS;
			 * 
			 * b01.setB0234((long) 0); } if (b01.getB0235() == null) {
			 * 
			 * this.setMainMessage("����������ר���������"); return EventRtnType.NORMAL_SUCCESS;
			 * 
			 * b01.setB0235((long) 0); } if (b01.getB0236() == null) {
			 * 
			 * this.setMainMessage("�����빤�ڱ�������"); return EventRtnType.NORMAL_SUCCESS;
			 * 
			 * b01.setB0236((long) 0); }
			 */
			if (b01.getB0150() == null) {
				/*
				 * this.setMainMessage("��������������쵼ְ����"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0150((long) 0);
			}
			if (b01.getB0190() == null) {
				/*
				 * this.setMainMessage("���������������ְְ����"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0190((long) 0);
			}
			if (b01.getB0150() < b01.getB0190()) {
				this.setMainMessage("�쵼ְ����Ӧ������ְְ����");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if (b01.getB0191a() == null) {
				/*
				 * this.setMainMessage("���������������ְְ����"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0191a((long) 0);
			}
			if (b01.getB0192() == null) {
				/*
				 * this.setMainMessage("���������������ְ���쵼ְ����"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0192((long) 0);
			}
			if (b01.getB0193() == null) {
				/*
				 * this.setMainMessage("���������������ְ���쵼ְ����"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0193((long) 0);
			}
		} else if (b0194Type.equals("2")) {
//			if(b01.getB0127().equals("")){
//				this.setMainMessage("�������������");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			if(b01.getB0114().trim().equals("")){
//				this.setMainMessage("����������������룡");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			// if(b01.getB0183()==null){
			/*
			 * this.setMainMessage("������Ӧ����ְ�쵼ְ����"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0183(null);
			// }
			// if(b01.getB0185()==null){
			/*
			 * this.setMainMessage("������Ӧ�丱ְ�쵼ְ����"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0185(null);
			// }
			/*
			 * if(b01.getB0188()==null){ this.setMainMessage("������Ӧ��ͬ����ְ���쵼ְ����"); return
			 * EventRtnType.NORMAL_SUCCESS; } if(b01.getB0189()==null){
			 * this.setMainMessage("������Ӧ��ͬ����ְ���쵼ְ����"); return EventRtnType.NORMAL_SUCCESS; }
			 */
			// if(b01.getB0227()==null){
			/*
			 * this.setMainMessage("������������������"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0227(null);
			// }
			// if(b01.getB0232()==null){
			/*
			 * this.setMainMessage("��������ҵ������(�ι�)��"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0232(null);
			// }
			// if(b01.getB0233()==null){
			/*
			 * this.setMainMessage("��������ҵ������(����)��"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0233(null);
			// }
			// if(b01.getB0234()==null){
			/*
			 * this.setMainMessage("������"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0234(null);
			// }
			// if(b01.getB0235()==null){
			/*
			 * this.setMainMessage("����������ר���������"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0235(null);
			// }
			// if(b01.getB0236()==null){
			/*
			 * this.setMainMessage("�����빤�ڱ�������"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0236(null);
			// }
		} else {
			if (this.getPageElement("b0114").getValue().equals("") && b0111.length() <= 7) {
				this.setMainMessage("������������룡");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		this.setNextEventName("sysorg.save");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("sysorg.save")
//	@OpLog
//	@Transaction
	public int sysorgsave() throws AppException, RadowException, SQLException, IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		HBSession sess = HBUtil.getHBSession();
		String b0194Type = "3";
		String radio = this.getPageElement("b0194").getValue();
		if (radio.equals("LegalEntity")) {
			b0194Type = "1";
		} else if (radio.equals("InnerOrg")) {
			b0194Type = "2";
		} else {
			b0194Type = "3";
		}
		try {
			sess.beginTransaction();
			CurrentUser user = SysUtil.getCacheCurrentUser();
			String b0101 = this.getPageElement("b0101").getValue().trim();
			String b0104 = this.getPageElement("b0104").getValue().trim();
			String b0111 = this.getPageElement("b0111").getValue().trim();
			// ����������Լ�������ʾ����������
			B01 b01 = CreateSysOrgBS.LoadB01(b0111);
			B01DTO b01dto = new B01DTO();
			CreateSysOrgBS.selectListByNameForUpdate(b0101, b0111);
			PropertyUtils.copyProperties(b01dto, b01);
			PMPropertyCopyUtil.copyElementsValueToObj(b01dto, this);
			String b0101old = this.getPageElement("b0101old").getValue();
			String b0104old = this.getPageElement("b0104old").getValue();
			String isstauts = this.getPageElement("isstauts").getValue();
			if (b0101old.equals(b0101)) {
				this.b0101stauts = "0";
			} else {
				this.b0101stauts = "1";
			}
			if (b0104old.equals(b0104)) {
				this.b0104stauts = "0";
			} else {
				this.b0104stauts = "1";
			}
			if (this.b0101stauts.equals("0") && this.b0104stauts.equals("0") || isstauts.equals("1")
					|| b0194Type.equals("3")) {
				CreateSysOrgBS.saveOrUpdateB01(b01dto, b0194Type);
				saveInfo_Extend(sess, b01.getB0111());
				sess.flush();
				this.getPageElement("isstauts").setValue("0");
				this.getExecuteSG().addExecuteCode("window.reloadTree()");
				this.getPageElement("b0101old").setValue(b01.getB0101());
				this.getPageElement("b0104old").setValue(b01.getB0104());
				B01 b01_tem = (B01) sess.get(B01.class, b0111);
				ReturnDO<B01> returnDO = CreateSysOrgBS.alertB01(b01_tem);
				sess.saveOrUpdate(returnDO.getObj());
				sess.flush();
				sess.connection().commit();
				this.setMainMessage("����ɹ�");
			} else {
				sess.connection().rollback();
				this.openWindow("updateNameWin", "pages.sysorg.org.SysOrgUpdateName");
				String parent_data = b01.getB0111() + "," + b0101stauts + "," + b0104stauts + "," + b01dto.getB0101()
						+ "," + b01dto.getB0104();
//				CommonQueryBS.systemOut("============"+parent_data);
				this.setRadow_parent_data(parent_data);
			}

		} catch (Exception e) {
			sess.connection().rollback();
			e.printStackTrace();
			throw new RadowException(e.toString());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	public boolean isown(List list, String b0111, boolean isadmin) {
		if (isadmin) {
			return true;
		}
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(b0111)) {
					return true;
				}
			}
		}
		return false;
	}

	public List authority(String userid) {
		HBSession sess = HBUtil.getHBSession();
		List<String> b0111s = (List) sess
				.createSQLQuery("select t.b0111 from COMPETENCE_USERDEPT t where t.userid='" + userid + "'").list();
		return b0111s;
	}

	// ��½��Ȩ��
	public String jurisdiction() {
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		Object[] area = (Object[]) HBUtil.getHBSession().createSQLQuery(
				"SELECT b.b0101,a.b0111,b.b0194 FROM COMPETENCE_USERDEPT a,B01 b WHERE  a.b0111=b.b0111 and a.userid='"
						+ cueUserid + "'")
				.uniqueResult();
		String b0111 = (String) area[1];
		return b0111;
	}

	private void saveInfo_Extend(HBSession sess, String b0111) throws Exception {

		List<Object[]> list = CreateSysOrgBS.getB01ExtSQL();
		Map<String, String> field = new LinkedHashMap<String, String>();
		if (list != null && list.size() > 0) {
			for (Object[] os : list) {
				field.put(os[1].toString(), os[2].toString());
			}
		}
		sess.flush();

		@SuppressWarnings("unchecked")
		List<Object> Info_Extends = sess.createSQLQuery("select b0111 from b01_ext where b0111='" + b0111 + "'").list();

		if (field.size() > 0) {// ����չ�ֶ�
			if (Info_Extends == null || Info_Extends.size() == 0) {// ����
				StringBuffer insert_sql = new StringBuffer("insert into b01_ext(b0111,");
				StringBuffer values = new StringBuffer(" values('" + b0111 + "',");
				Object[] args = new Object[field.size()];
				Integer index = 0;
				for (String key : field.keySet()) {
					String comment = field.get(key);
					try {
						insert_sql.append(key + ",");
						values.append("?,");
						args[index++] = this.getPageElement(key.toLowerCase()).getValue();
						HBUtil.executeUpdate("alter table b01_ext add " + key + " varchar(200)");
						HBUtil.executeUpdate("alter table b01_ext_temp add " + key + " varchar(200)");
						try {
							HBUtil.executeUpdate("comment on column b01_ext." + key + " is '" + comment + "'");
						} catch (Exception e) {
							try {
								HBUtil.executeUpdate("ALTER TABLE b01_ext MODIFY COLUMN " + key
										+ " VARCHAR(200) COMMENT '" + comment + "'");// mysql
								HBUtil.executeUpdate("ALTER TABLE b01_ext_temp MODIFY COLUMN " + key
										+ " VARCHAR(200) COMMENT '" + comment + "'");// mysql
							} catch (Exception e1) {
							}
						}
					} catch (Exception e) {
					}
				}
				insert_sql.deleteCharAt(insert_sql.length() - 1).append(")");
				values.deleteCharAt(values.length() - 1).append(")");
				insert_sql.append(values);
				HBUtil.executeUpdate(insert_sql.toString(), args);
			} else {// �޸�
				StringBuffer update_sql = new StringBuffer("update b01_ext set ");
				Object[] args = new Object[field.size()];
				Integer index = 0;
				for (String key : field.keySet()) {
					String comment = field.get(key);
					try {
						update_sql.append(key + "=?,");
						args[index++] = this.getPageElement(key.toLowerCase()).getValue();
						HBUtil.executeUpdate("alter table b01_ext add " + key + " varchar(200)");
						HBUtil.executeUpdate("alter table b01_ext_temp add " + key + " varchar(200)");
						try {
							HBUtil.executeUpdate("comment on column b01_ext." + key + " is '" + comment + "'");
						} catch (Exception e) {
							try {
								HBUtil.executeUpdate("ALTER TABLE b01_ext MODIFY COLUMN " + key
										+ " VARCHAR(200) COMMENT '" + comment + "'");// mysql
								HBUtil.executeUpdate("ALTER TABLE b01_ext_temp MODIFY COLUMN " + key
										+ " VARCHAR(200) COMMENT '" + comment + "'");// mysql
							} catch (Exception e1) {
							}
						}

					} catch (Exception e) {
					}
				}
				update_sql.deleteCharAt(update_sql.length() - 1).append(" where b0111='" + b0111 + "'");
				HBUtil.executeUpdate(update_sql.toString(), args);
			}

		}
	}

	private void readInfo_Extend(String b0111) throws AppException {
		List<Map<String, String>> Info_Extends = HBUtil
				.queryforlist("select * from B01_EXT where b0111='" + b0111 + "'", null);
		if (Info_Extends != null && Info_Extends.size() > 0) {//
			Map<String, String> entity = Info_Extends.get(0);
			for (String key : entity.keySet()) {
				try {
					this.getPageElement(key.toLowerCase()).setValue(entity.get(key));
				} catch (Exception e) {
				}
			}
		} else {
			DBType cueDBType = DBUtil.getDBType();
			List<String> list = null;
			if (cueDBType == DBType.MYSQL) {
				list = HBUtil.getHBSession().createSQLQuery(
						"select COLUMN_NAME from information_schema.COLUMNS where table_name = 'b01_ext' and  column_name!='B0111' and TABLE_SCHEMA = 'ZWHZYQ'")
						.list();
			} else {
				list = HBUtil.getHBSession().createSQLQuery(
						"SELECT column_name FROM all_tab_cols WHERE  table_name = UPPER('b01_ext')  and  column_name!='B0111'")
						.list();
			}
			for (int i = 0; i < list.size(); i++) {
				try {
					this.getPageElement(list.get(i).toLowerCase()).setValue("");
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * ��ѡ�л�����������У��
	 * 
	 * @return
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("dataVerify.onclick")
	public int dataVerify() throws RadowException {
		String checkedgroupid = this.getPageElement("checkedgroupid").getValue();
		HBSession sess = HBUtil.getHBSession();
		String groupid = "";
		if (StringUtil.isEmpty(checkedgroupid)) {
			String cueUserid = SysUtil.getCacheCurrentUser().getId();
			String b0111sql = "";
			if (DBType.ORACLE == DBUtil.getDBType()) {
				b0111sql = "select b0111 from ( select b0111 from competence_userdept t where userid='" + cueUserid
						+ "' order by length(b0111) asc) where ROWNUM =1";
			} else if (DBType.MYSQL == DBUtil.getDBType()) {
				b0111sql = "select b0111 from competence_userdept t where userid='" + cueUserid
						+ "' order by length(b0111) asc limit 1";
			}
			Object b01 = sess.createSQLQuery(b0111sql).uniqueResult();
			if (b01 == null || "".equals(b01)) {
				this.setMainMessage("û�����û���Ȩ��");
				return EventRtnType.FAILD;
			} else {
				checkedgroupid = b01.toString();
			}
			groupid = "all";
		} else {
			PageElement pe = this.getPageElement("orgInfoGrid");
			List<HashMap<String, Object>> grid = pe.getValueList();
			if (grid != null && grid.size() > 0) {
				for (int i = 0; i < grid.size(); i++) {
					HashMap<String, Object> map = grid.get(i);
					Object checked = map.get("personcheck");
					if ("true".equals(checked.toString())) {
						groupid += map.get("b0111") + ",";
					}
				}
				if (StringUtil.isEmpty(groupid)) {
					groupid = "selectall";
				} else {
					groupid = groupid.substring(0, groupid.length() - 1);
				}

			} else {
				this.setMainMessage("�޻�����Ϣ");
				return EventRtnType.FAILD;
			}
		}
		/*
		 * if(!SysRuleBS.havaRule(groupid)){ throw new RadowException("���޴�Ȩ��!"); }
		 */
		String ctxPath = request.getContextPath();
		String paradata = "1@" + checkedgroupid + "@" + groupid;
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('dataVerifyWin','pages.sysorg.org.orgdataverify.OrgDataVerify','������ϢУ��',700,599,'','"
						+ ctxPath + "',null,{ids:'" + paradata + "',maximizable:false,resizable:false});");
		// this.setRadow_parent_data("1@"+groupid);
		// this.openWindow("dataVerifyWin",
		// "pages.sysorg.org.orgdataverify.OrgDataVerify");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ��Ƭ���
	 * 
	 * @return
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("imgVerify.onclick")
	public int imgVerify() throws RadowException {
		this.getExecuteSG().addExecuteCode("addTab('��Ƭ���','','" + request.getContextPath()
				+ "/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.orgdataverify.OrgPersonImgVerify',false,false)");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ����Ա�޸Ľ���
	 * 
	 * @param a0000
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("opendA01Modefy")
	@NoRequiredValidate
	public int opendA01ModefyWin(String a0000) throws RadowException {
		this.getExecuteSG().addExecuteCode("parent.addTab('','" + a0000 + "','" + request.getContextPath()
				+ "/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		this.setRadow_parent_data(a0000);
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �¼������쵼ְ������
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("lowOrgLeaderBtn.onclick")
	public int lowOrgLeaderYuLan() throws RadowException {
		String num = "1";
		String groupid = getPageElement("checkedgroupid").getValue();
		if ((groupid.trim().equals("")) || (groupid == null)) {
			throw new RadowException("��ѡ�����!");
		}
		if (!SysRuleBS.havaRule(groupid).booleanValue()) {
			throw new RadowException("���޴�Ȩ��!");
		}
		String param = num + "|" + groupid;
		getExecuteSG().addExecuteCode("lowOrgLeaderYuLan('" + param + "')");
		return EventRtnType.NORMAL_SUCCESS;
	}

//	@NoRequiredValidate
//	@PageEvent("lowOrgLeaderBtn.onclick")
	public int lowOrgLeader() throws RadowException, AppException {
		String fileName = "����λ�쵼ְ���䱸��";
		String laststr = ".xls";
		String loadFile = ExpRar.getExeclPath() + "\\" + fileName + laststr;
		String expFile = ExpRar.expFile() + fileName + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")
				+ laststr;
		CommonQueryBS.systemOut(loadFile);
		CommonQueryBS.systemOut(expFile);
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if (groupid.trim().equals("") || groupid == null) {
			throw new RadowException("��ѡ�����!");
		}
		if (!SysRuleBS.havaRule(groupid)) {
			throw new RadowException("���޴�Ȩ��!");
		}
		InputStream is;
		try {
			FileUtil.createFile(expFile);
			is = new FileInputStream(new File(loadFile));
			List<B01> list = SysOrgBS.selectListByExecl(groupid);
			int counts = Integer.valueOf(SysOrgBS.selectCountBySubId(groupid));
			byte[] bytes = SysOrgBS.wirteExeclLowOrgLeader(is, list, counts);
			FileOutputStream outf = new FileOutputStream(expFile);
			BufferedOutputStream bufferout = new BufferedOutputStream(outf);
			bufferout.write(bytes);
			bufferout.flush();
			bufferout.close();
			this.getPageElement("downfile").setValue(expFile.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.download()");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �¼�����������Ա�Ա�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("lowOrgPeopleBtn.onclick")
	public int lowOrgPeopleYuLan() throws RadowException {
		String num = "2";
		String groupid = getPageElement("checkedgroupid").getValue();
		if ((groupid.trim().equals("")) || (groupid == null)) {
			throw new RadowException("��ѡ�����!");
		}
		if (!SysRuleBS.havaRule(groupid).booleanValue()) {
			throw new RadowException("���޴�Ȩ��!");
		}
		String param = num + "|" + groupid;
		getExecuteSG().addExecuteCode("lowOrgLeaderYuLan('" + param + "')");
		return EventRtnType.NORMAL_SUCCESS;
	}

//	@NoRequiredValidate
//	@PageEvent("lowOrgPeopleBtn.onclick")
	public int lowOrgPeople() throws RadowException {
		String fileName = "����λ��������Ա�䱸��";
		String laststr = ".xls";
		String loadFile = ExpRar.getExeclPath() + "\\" + fileName + laststr;
		String expFile = ExpRar.expFile() + fileName + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")
				+ laststr;
		CommonQueryBS.systemOut(loadFile);
		CommonQueryBS.systemOut(expFile);
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if (groupid.trim().equals("") || groupid == null) {
			throw new RadowException("��ѡ�����!");
		}
		if (!SysRuleBS.havaRule(groupid)) {
			throw new RadowException("���޴�Ȩ��!");
		}
		InputStream is;
		try {
			FileUtil.createFile(expFile);
			is = new FileInputStream(new File(loadFile));
			int counts = Integer.valueOf(SysOrgBS.selectCountBySubId(groupid));
			List<B01> list = SysOrgBS.selectListByExecl(groupid);
			byte[] bytes = SysOrgBS.wirteExeclLowOrgPeople(is, counts, list);
			FileOutputStream outf = new FileOutputStream(expFile);
			BufferedOutputStream bufferout = new BufferedOutputStream(outf);
			bufferout.write(bytes);
			bufferout.flush();
			bufferout.close();
			this.getPageElement("downfile").setValue(expFile.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.download()");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ������쵼ְ������
	 * 
	 * @return
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("manyOrgLeaderBtn.onclick")
	public int manyOrgLeader() throws RadowException {
		this.setRadow_parent_data("leader");
//		String ctxPath = request.getContextPath();
//		this.getExecuteSG().addExecuteCode("$h.openWin('orgStatisticsWin','pages.sysorg.org.OrgStatistics','ѡ�������Χ',397,490,'leader','"+ctxPath+"');");
		this.openWindow("orgStatisticsWin", "pages.sysorg.org.OrgStatistics");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �����������Ա�Ա�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("manyOrgPeopleBtn.onclick")
	public int manyOrgPeople() throws RadowException {
		this.setRadow_parent_data("people");
//		String ctxPath = request.getContextPath();
//		this.getExecuteSG().addExecuteCode("$h.openWin('orgStatisticsWin','pages.sysorg.org.OrgStatistics','ѡ�������Χ',397,490,'people','"+ctxPath+"');");
		this.openWindow("orgStatisticsWin", "pages.sysorg.org.OrgStatistics");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �б�˫���¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("DbClick_grid")
	public int DbClick_grid(String type) throws RadowException {

		// ��ȡǰ̨grid����
		Grid grid = (Grid) this.getPageElement("orgInfoGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		if (list == null || list.size() == 0) {
			this.setMainMessage("��˫����������ѯ!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String groupid = "";

		for (int i = 0; i < list.size(); i++) {
			groupid = groupid + (String) list.get(i).get("b0111") + ",";
		}

		groupid = groupid.substring(0, groupid.length() - 1);// ȡ������

		String pardata = "";
		String ctxPath = request.getContextPath();

		String arr[] = type.split(",");
		pardata = arr[1] + "," + groupid;
		request.getSession().setAttribute("unitidDbclAlter", pardata);//
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('updateOrgWin','pages.sysorg.org.UpdateSysOrg','������Ϣ�޸�ҳ��',1250,550,'','" + ctxPath + "');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �Զ�����
	 * 
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("aotumaticCode_onclick")
	public int aotumaticCode() throws AppException, RadowException {
		String checkedgroupid = request.getParameter("checkedgroupid");// ǰ��ѡ�еĻ���id;
		if (checkedgroupid != null && checkedgroupid.length() > 0) {// ���ڱ�ѡ�еĻ�������������䱻ѡ�л��������¼������ı���Ϊ�ջ����������������
			completionSubordinateCode(checkedgroupid);
		} else {// Ϊ�գ���ȫȨ�޷�Χ�ڵģ����б���Ϊ�յĻ����������������
			completionAllCode();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws RadowException
	 */
	public String setB0114(String userid, String id) throws RadowException {
		Connection conn = HBUtil.getHBSession().connection();
		String gjdid = "";
		Statement stmt1 = null;
		try {
			CommQuery cq = new CommQuery();
			String sql = "";
			if ("".equals(id) || "-1".equals(id)) {
				sql = " select t.b0111,t.b0114,t.b0194,t.b0121 from " + " competence_userdept c,b01 t "
						+ " where c.b0111=t.b0111" + " and b0194 in ('2','3') "// 2���������3��������
						+ " and (t.b0114 is null or length(t.b0114)=0) "
						+ " order by length(t.b0111) asc,t.sortid asc ";// ����sortid�����򲹳����
			} else {
				sql = " select t.b0111,t.b0114,t.b0194,t.b0121 from " + " competence_userdept c,b01 t "
						+ " where c.b0111=t.b0111" + " and b0194 in ('2','3') "// 2���������3��������
						+ " and (t.b0114 is null or length(t.b0114)=0) " + " and t.b0111 like '" + id + "%' "
						+ " order by length(t.b0111) asc,t.sortid asc";// ����sortid�����򲹳����
			}
			List<HashMap<String, Object>> list = cq.getListBySQL(sql);
			for (int i = 0; i < list.size(); i++) {
				String b0111 = (String) list.get(i).get("b0111");// ����id
				String b0114 = (String) list.get(i).get("b0114");// ��������
				String b0194 = (String) list.get(i).get("b0194");// ��������
				String b0121 = (String) list.get(i).get("b0121");// �ϼ�����id
				if ("2".equals(b0194)) {// �������
					// ��ȡ�ϼ�����
					String b0114up = getB0114up(b0121, cq, b0114);
					// ���ɲ��ظ���3λ����
					String tempb0114 = getTempB0114(b0114up, b0121, cq);
					if ("".equals(b0114up) || "null".equals(b0114up) || b0114up == null) {
						continue;
					}
					if (tempb0114.length() >= 4) {
						flag_ns = true;// ������������־
						continue;
					} else {
						b0114 = b0114up + "." + tempb0114;
						stmt1 = conn.createStatement();
						stmt1.execute("update b01 set b0114='" + b0114 + "' where b0111='" + b0111 + "' ");
						stmt1.close();
					}
				} else if ("3".equals(b0194)) {// ��������
					// ��ȡ�ϼ�����
					String b0114up = getB0114upFz(b0121, cq, b0194);
					// ���ɲ��ظ���3λ����
					String tempb0114 = getTempB0114Fz(b0114up, b0121, cq);
					if ("".equals(b0114up) || "null".equals(b0114up) || b0114up == null) {
						continue;
					}
					if (tempb0114.length() >= 4) {
						flag_fz = true;// ������������־
						continue;
					} else {
						b0114 = b0114up + "." + tempb0114;
						stmt1 = conn.createStatement();
						stmt1.execute("update b01 set b0114='" + b0114 + "' where b0111='" + b0111 + "' ");
						stmt1.close();
					}
				}
			}
		} catch (Exception e) {
			try {
				if (stmt1 != null) {
					stmt1.close();
				}
			} catch (Exception e1) {

			}
			e.printStackTrace();
			throw new RadowException("����ʧ��!" + e.getMessage());
		}
		return gjdid;
	}

	public String getB0114upFz(String b0121, CommQuery cq, String b0194) throws AppException, RadowException {
		List<HashMap<String, Object>> list = cq
				.getListBySQL(" select b0114,b0111,b0194 from b01 where b0111='" + b0121 + "' ");
		if (list == null || list.size() == 0) {
			return "";
		} else {
			if ("-1".equals(list.get(0).get("b0111"))) {
				if ("3".equals(b0194)) {
					return returnB0114Fz("", "0", "");
				} else {
					return returnB0114("", "0", "");
				}
			} else {
				return ((String) list.get(0).get("b0114"));
			}
		}
	}

	public String getB0114up(String b0121, CommQuery cq, String b0114) throws AppException {
		List<HashMap<String, Object>> list = cq
				.getListBySQL(" select b0194,b0114,b0111,b0121 from b01 where b0111='" + b0121 + "' ");
		String b0114up = "";
		if (list == null || list.size() == 0) {
			return "";
		} else {
			String b0194 = (String) list.get(0).get("b0194");
			if ("1".equals(b0194)) {
				return ((String) list.get(0).get("b0114"));
			} else if ("2".equals(b0194)) {
				return ((String) list.get(0).get("b0114"));
			} else if ("3".equals(b0194)) {
				if ("-1".equals((String) list.get(0).get("b0111"))) {// ������
					return b0114;// ���ظ������¼�����
				} else {
					b0114up = getB0114up((String) list.get(0).get("b0121"), cq, (String) list.get(0).get("b0114"));
				}
			} else {
				return ((String) list.get(0).get("b0114"));
			}
		}
		return b0114up;
	}

	public String getTempB0114(String b0114up, String b0121, CommQuery cq) throws AppException, RadowException {
		String tempb0114 = "";
		try {
			List<HashMap<String, Object>> list = cq.getListBySQL(
					" select b0114 from b01 where b0121='" + b0121 + "' and b0114 is not null and length(b0114)!=0 ");
			String str1 = "$";
			for (int i = 0; i < list.size(); i++) {
				str1 = str1 + list.get(i).get("b0114") + "$";
			}
			tempb0114 = returnB0114(b0114up, "0", str1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("��ȡ3λ�������ʧ��!" + e.getMessage());
		}
		return tempb0114;
	}

	public String getTempB0114Fz(String b0114up, String b0121, CommQuery cq) throws AppException, RadowException {
		String tempb0114 = "";
		try {
			List<HashMap<String, Object>> list = cq.getListBySQL(
					" select b0114 from b01 where b0121='" + b0121 + "' and b0114 is not null and length(b0114)!=0 ");
			String str1 = "$";
			for (int i = 0; i < list.size(); i++) {
				str1 = str1 + list.get(i).get("b0114") + "$";
			}
			tempb0114 = returnB0114Fz(b0114up, "0", str1);
			tempb0114 = "V" + tempb0114;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("��ȡ3λ�������ʧ��!" + e.getMessage());
		}
		return tempb0114;
	}

	public String returnB0114Fz(String superStr, String str, String str1) throws RadowException {
		try {
			str = (Integer.parseInt(str) + 1) + "";
			if (str.length() == 1) {
				str = "0" + str;
			}
			// -1������
			if (str1.indexOf("$" + superStr + ".V" + str + "$") == -1) {
				// return str;
			} else {// �����Ѿ����ڣ���ݹ飬��������
				str = returnB0114Fz(superStr, str, str1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("����3λ���������������ʧ��!" + e.getMessage());
		}
		return str;
	}

	public String returnB0114(String superStr, String str, String str1) throws RadowException {
		try {
			str = (Integer.parseInt(str) + 1) + "";
			if (str.length() == 1) {
				str = "00" + str;
			} else if (str.length() == 2) {
				str = "0" + str;
			}
			if (str.length() == 4) {
				return "0000";// ͬ����λ����
			}
			// -1������
			if (str1.indexOf("$" + superStr + "." + str + "$") == -1) {
				// return str;
			} else {// �����Ѿ����ڣ���ݹ飬��������
				str = returnB0114(superStr, str, str1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("����3λ��������������ʧ��!" + e.getMessage());
		}
		return str;
	}

	/**
	 * ��ȫ��������
	 * 
	 * @param checkedgroupid
	 * @return
	 * @throws AppException
	 */
	public int completionSubordinateCode(String checkedgroupid) throws AppException {
		String checkedname = HBUtil.getValueFromTab("b0101", "b01", " b0111='" + checkedgroupid + "' ");
		if (checkedname == null) {
			this.setSelfDefResData("1@��ǰ�����Ѿ������ڣ���ˢ�»���������ִ�У�");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		Connection con=null;
//		Statement st= null;
		try {
//			con=HBUtil.getHBSession().connection();
//			//�ر������Զ��ύ
//			con.setAutoCommit(false);
//			st= con.createStatement();
			CommQuery cq = new CommQuery();
			String userid = SysUtil.getCacheCurrentUser().getId();
			String sql = "select count(*) num from b01 t,competence_userdept c " + " where t.b0111=c.b0111 "
					+ " and c.userid='" + userid + "' "// �û�id
					+ " and t.b0111 like '" + checkedgroupid + "%' "
					+ " and (length(trim(t.b0114))=0 or t.b0114 is null) "// ��������
					+ " and t.b0194 in ('2','3') "// 2 ������� 3��������
			;

			List<HashMap<String, Object>> list = cq.getListBySQL(sql);
			// �жϣ���ѡ�еĻ��������¼��������Ƿ���ڱ���Ϊ�յĻ���
			if (list == null || list.size() == 0 || "0".equals(list.get(0).get("num"))) {// �����ڣ�����Ϊ�յĻ���
				if (checkedname == null) {
					checkedname = "";
				}
				this.setSelfDefResData("1@" + checkedname + "�����²����ڱ���Ϊ�յ�����������������!");
				return EventRtnType.NORMAL_SUCCESS;
			} else {//
					// �жϣ���ѡ�еĻ��������¼��Ƿ���ڱ���Ϊ�յķ��˻���
				sql = "select count(*) num from b01 t,competence_userdept c " + " where t.b0111=c.b0111 "
						+ " and c.userid='" + userid + "' "// �û�id
						+ " and t.b0111 like '" + checkedgroupid + "%' "
						+ " and (length(trim(t.b0114))=0 or t.b0114 is null) "// ��������
						+ " and t.b0194='1' "// 1���˵�λ
				;
				list = cq.getListBySQL(sql);
				if (list != null && Double.parseDouble((String) list.get(0).get("num")) > 0) {
					String title = "4@" + checkedname + "�����´��ڱ���Ϊ�յķ��˵�λ������ȷ�ϲ��޸�Ϊ�յķ��˵�λ����!";
					this.setSelfDefResData(title);
					return EventRtnType.NORMAL_SUCCESS;
				}
				/* �ж�Ȩ�޷�Χ���Ƿ���ڣ����������ظ��Ļ��������������ظ����ᵼ�����¼��Զ�����Ҳ�����ظ��� */
				StringBuffer sb = new StringBuffer();
				if (DBType.ORACLE == DBUtil.getDBType()) {
					sb.append(" select " + " t1.b0114 " + " from b01 t1 where t1.b0114 in " + " ( select s1.b0114 from "
							+ " (select t.b0114 from  b01 t, competence_userdept c " + " where c.b0111 = t.b0111 "
							+ " and c.userid = '" + userid + "') s1, " + " (select k.b0114 "
							+ " from b01 k where k.b0114 is not null and length(trim(k.b0114))!=0 ");
					sb.append(" group by k.b0114 " + " having count(K.b0114) > 1) s2 where  s1.b0114=s2.b0114 ) ");
				} else if (DBType.MYSQL == DBUtil.getDBType()) {
					sb.append(" select * from ( select " + " t1.b0114 " + " from b01 t1 where t1.b0114 in "
							+ " ( select s1.b0114 from " + " (select t.b0114 from  b01 t, competence_userdept c "
							+ " where c.b0111 = t.b0111 " + " and c.userid = '" + userid + "') s1, "
							+ " (select k.b0114 "
							+ " from b01 k where k.b0114 is not null and length(trim(k.b0114))!=0  ");
					sb.append(" group by k.b0114 "
							+ " having count(K.b0114) > 1) s2 where  s1.b0114=s2.b0114 ) order by t1.b0114 ) ts  ");
				}
				List<HashMap<String, Object>> listRepeat = cq.getListBySQL(sb.toString());
				if (listRepeat.size() > 0) {
					this.setSelfDefResData(
							"1@" + checkedname + "�����´����ظ��Ļ������룬�����������ػ���ϢУ�ˣ���������У��->�����ظ�У�ˣ������ݻ�����Ϣ���ֶ��޸��ظ��ı���!");
					return EventRtnType.NORMAL_SUCCESS;
				}
				/* �жϻ��������Ƿ�Ϸ� */
				StringBuffer sbLegal = new StringBuffer();
				if (DBType.ORACLE == DBUtil.getDBType()) {
					sbLegal.append(" select t.b0111 from b01 t,competence_userdept s where t.b0111 = s.b0111 "
							+ " and s.userid = '" + userid + "' "
							+ " and not regexp_like(t.b0114, '^([a-zA-Z0-9]{3}[.])*([a-zA-Z0-9]{3})$') "
							+ " and t.b0114 is not null " + " and t.b0111 like '" + checkedgroupid + "%' "
							+ " and length(t.b0114)!=0 ");
				} else if (DBType.MYSQL == DBUtil.getDBType()) {
					sbLegal.append(" select t.b0111 from b01 t,competence_userdept s where t.b0111 = s.b0111 "
							+ " and s.userid = '" + userid + "' "
							+ " and t.b0114 not regexp  '^([a-zA-Z0-9]{3}[.])*([a-zA-Z0-9]{3})$'"
							+ " and t.b0114 is not null " + " and t.b0111 like '" + checkedgroupid + "%' "
							+ "and length(t.b0114)!=0");
				}
				List<HashMap<String, Object>> listLegal = cq.getListBySQL(sbLegal.toString());
				if (listLegal.size() > 0) {
					String title = "3@" + checkedname + "�����´��ڲ�Ϊ���Ҳ��Ϸ��Ļ������룬����ȷ�ϲ鿴��Ϊ���Ҳ��Ϸ��Ļ�����Ϣ���ֶ��޸Ļ�������!";
					this.setSelfDefResData(title);
					return EventRtnType.NORMAL_SUCCESS;
				}
				// ��ʼ����
				setB0114(userid, checkedgroupid);
//				List<HashMap> list_only=null;
//				if(DBType.ORACLE==DBUtil.getDBType()){
//					sql="select b0111,b0121 from ( select t.b0111,t.b0121 from competence_userdept c,b01 t "
//							+ " where  t.b0111=c.b0111 "
//							+ " and c.userid='"+userid+"' "
//							+ " and t.b0111 like '"+checkedgroupid+"%' "
//							+ " order by length(t.b0111),t.SORTID asc) where ROWNUM =1";
//					list_only=CommonQueryBS.getQueryInfoByManulSQL(sql);
//				}else if(DBType.MYSQL==DBUtil.getDBType()){
//					sql="select t.b0111,t.b0121 from competence_userdept c,b01 t "
//							+ " where  t.b0111=c.b0111 "
//							+ " and t.b0111 like '"+checkedgroupid+"%' "
//							+ " and c.userid='"+userid+"' order by length(t.b0111),t.SORTID asc limit 1";
//					list_only=CommonQueryBS.getQueryInfoByManulSQL(sql);
//				}
//				String b0111_super="";//��ȡ��ѡ�еĻ��������¼�������,����Ϊ����߼���������ϼ�����id
//				if(list_only!=null&&list_only.size()>0){
//					b0111_super=(String)list_only.get(0).get("b0121");//��ȡ�û�Ȩ�޷�Χ��,����Ϊ����߼���������ϼ�����id
//				}else{
//					this.setSelfDefResData("1@�����쳣!");
//					return EventRtnType.NORMAL_SUCCESS;
//				}
//				String b0114_super="";//��ȡ�û�Ȩ�޷�Χ��,����Ϊ����߼���������ϼ���������
//				List<HashMap<String, Object>> list_super=cq.getListBySQL("select t.b0114 from b01 t where t.b0111='"+b0111_super+"' ");
//				if(list_super==null||list_super.size()<1){
//					this.setSelfDefResData("1@�����쳣!");
//					return EventRtnType.NORMAL_SUCCESS;
//				}else{
//					b0114_super=(String)list_super.get(0).get("b0114");//��ȡ�û�Ȩ�޷�Χ��,����Ϊ����߼���������ϼ���������
//				}
//				
//				String cueUserid=SysUtil.getCacheCurrentUser().getId();
//				//��ȡ�û�Ȩ�޷�Χ�ڵ���ͼ���λ��id
//			 	List<HashMap> listh =null;
//				try {
//					if(DBType.ORACLE==DBUtil.getDBType()){
//						sql="select b0111 from ( select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) desc) where ROWNUM =1";
//						listh=CommonQueryBS.getQueryInfoByManulSQL(sql);
//					}else if(DBType.MYSQL==DBUtil.getDBType()){
//						sql="select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) desc limit 1";
//						listh=CommonQueryBS.getQueryInfoByManulSQL(sql);
//					}
//				}catch(AppException e){
//					e.printStackTrace();
//				}
//				String nodeh="";
//				if(listh!=null&&listh.size()>0){
//					nodeh=(String)listh.get(0).get("b0111");//��ȡ�û�Ȩ�޷�Χ�ڵ���߼���λ��id
//				}else{
//					this.setSelfDefResData("1@@@"+"�������!");
//				}
//				
//				sql="select t.b0111,t.b0114,t.b0194 from competence_userdept c,b01 t "
//						+ " where  t.b0111=c.b0111 "
//						+ " and c.userid='"+userid+"' "
//						+ " and t.b0111 like '"+checkedgroupid+"%' "
//						+ " and t.b0121='"+b0111_super+"' "
//						+ " order by length(t.b0111),t.SORTID asc ";
//				List<HashMap<String, Object>> list_all =cq.getListBySQL(sql);//�����ϼ�id��ȡ�¼������¼�����
//				
//				//���������벻Ϊ�յĻ�������,ƴ�ӳ��ַ���
//				String str1=returnStr(list_all);
//				//��ʼ����һ������
//				String b0114_ns="0";
//				String b0114_fz="0";
//				for(int i=0;i<list_all.size();i++){//���������¼�����
//					if(list_all.get(i).get("b0114")==null
//							||((String)list_all.get(i).get("b0114")).trim().length()==0
//							){//����Ϊ��,�Ҳ�Ϊ���˵�λ
//						//�Զ����뱾�������
//						if("2".equals((String)list_all.get(i).get("b0194"))){//����
//							b0114_ns=returnNum(b0114_super,b0114_ns,str1);//���������������
//							
//							if(b0114_ns.length()>=4){
//								flag_ns=true;//������������־
//								continue;
//							}
//							if(b0114_super==null||b0114_super.length()==0){//���ǵ�һ�������ǻ��������ұ���Ϊ�գ����ظ��ڵ����ҲΪ��ʱ�����
//								st.addBatch(" update b01 set b0114='"+b0114_ns+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}else{
//								st.addBatch(" update b01 set b0114='"+b0114_super+"."+b0114_ns+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}
//							st_num=st_num+1;
//							if(st_num%300==0){
//								st.executeBatch();
//								st.close();//��ȫ����ʱ��ܳ���st��ʱ�䲻���ͷţ��ᱨ�������Ӳ����ͷ�
//								st=con.createStatement();
//							}
//							//�Զ������¼�����
//							if(b0114_super==null||b0114_super.length()==0){
//								aotumaticCode1((String)list_all.get(i).get("b0111"),b0114_ns,st,nodeh);
//							}else{
//								aotumaticCode1((String)list_all.get(i).get("b0111"),b0114_super+"."+b0114_ns,st,nodeh);
//							}
//						}else if("3".equals((String)list_all.get(i).get("b0194"))){//����
//							b0114_fz=returnNumFz(b0114_super,b0114_fz,str1);//���ɷ����������
//							
//							if(b0114_fz.length()>=3){
//								flag_fz=true;//������������־
//								continue;
//							}
//							if(b0114_super==null||b0114_super.length()==0){//�������ϼ���������Ϊ�� (���ǵ�һ�������ǻ��������ұ���Ϊ�գ����ظ��ڵ����ҲΪ��ʱ�����)
//								st.addBatch(" update b01 set b0114='V"+b0114_fz+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}else{
//								st.addBatch(" update b01 set b0114='"+b0114_super+".V"+b0114_fz+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}
//							st_num=st_num+1;
//							if(st_num%300==0){
//								st.executeBatch();
//								st.close();//��ȫ����ʱ��ܳ���st��ʱ�䲻���ͷţ��ᱨ�������Ӳ����ͷ�
//								st=con.createStatement();
//							}
//							//�Զ������¼�����
//							if(b0114_super==null||b0114_super.length()==0){
//								aotumaticCode1((String)list_all.get(i).get("b0111"),"V"+b0114_fz,st,nodeh);
//							}else{
//								aotumaticCode1((String)list_all.get(i).get("b0111"),b0114_super+".V"+b0114_fz,st,nodeh);
//							}
//						}
//					}else{
//						//�Զ������¼�����
//						aotumaticCode1((String)list_all.get(i).get("b0111"),((String)list_all.get(i).get("b0114")).trim(),st,nodeh);
//					}
//				}
//				st.executeBatch();
//				con.commit();
//				st.close();
//				con.close();
				if (flag_fz == true && flag_ns == true) {
					this.setSelfDefResData("2@�¼�ֱ��������������������࣬���Զ����벿�ֻ�������������ֶ�����!");
				} else if (flag_fz == true) {
					this.setSelfDefResData("2@�¼�ֱ������������࣬���Զ����벿�ֻ�������������ֶ�����!");
				} else if (flag_ns == true) {
					this.setSelfDefResData("2@�¼�ֱ������������࣬���Զ����벿�ֻ�������������ֶ�����!");
				} else if (flag_fz == false && flag_ns == false) {
					this.setSelfDefResData("2@����ɹ�!");
				} else {
					this.setSelfDefResData("2@����ɹ�!");
				}
				st_num = 0;// �ָ���ʼ��
				flag_ns = false;// �ָ���ʼ��
				flag_fz = false;// �ָ���ʼ��
			}
		} catch (Exception e) {
//			try{
//				if(st!=null){
//					st.close();
//				}
//				if(con!=null){
//			    	try{
//			    		con.rollback();
//					    con.close();
//			    	}catch(Exception e1){
//			    		
//			    	}
//			    }
//			}catch(Exception e1){
//				e1.printStackTrace();
//			}
			e.printStackTrace();
			this.setSelfDefResData("1@�Զ�����ʧ��!");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ��ȫ��������
	 * 
	 * @return
	 */
	public int completionAllCode() {
//		Connection con=null;
//		Statement st= null;
		try {
//			con=HBUtil.getHBSession().connection();
//			//�ر������Զ��ύ
//			con.setAutoCommit(false);
//			st= con.createStatement();
			CommQuery cq = new CommQuery();
			String userid = SysUtil.getCacheCurrentUser().getId();
			String sql = "select count(*) num from b01 t,competence_userdept c " + " where t.b0111=c.b0111 "
					+ " and c.userid='" + userid + "' "// �û�id
					+ " and (length(trim(t.b0114))=0 or t.b0114 is null) "// ��������
					+ " and t.b0194 in ('2','3') "// 2 ������� 3��������
			;

			List<HashMap<String, Object>> list = cq.getListBySQL(sql);
			// �жϣ��û�Ȩ�޷�Χ�ڵ����������������飬�Ƿ���ڱ���Ϊ�յĻ���
			if (list == null || list.size() == 0 || "0".equals(list.get(0).get("num"))) {// �����ڣ�����Ϊ�յĻ���
				this.setSelfDefResData("1@�����ڱ���Ϊ�յ�����������������!");
				return EventRtnType.NORMAL_SUCCESS;
			} else {// ���ڣ���ȡȨ�޷�Χ�ڣ�����������ߣ�����id��̣��Ļ�����Ϣ
				/* �ж��Ƿ���ڣ����������ظ��Ļ��������������ظ����ᵼ�����¼��Զ�����Ҳ�����ظ��� */
				sql = "select count(*) num from b01 t,competence_userdept c " + " where t.b0111=c.b0111 "
						+ " and c.userid='" + userid + "' "// �û�id
						+ " and (length(trim(t.b0114))=0 or t.b0114 is null) "// ��������
						+ " and t.b0194='1' "// 1���˵�λ
				;
				list = cq.getListBySQL(sql);
				if (list != null && Double.parseDouble((String) list.get(0).get("num")) > 0) {
					String title = "4@���ڱ���Ϊ�յķ��˵�λ������ȷ�ϲ��޸�Ϊ�յķ��˵�λ����!";
					this.setSelfDefResData(title);
					return EventRtnType.NORMAL_SUCCESS;
				}
				StringBuffer sb = new StringBuffer();
				if (DBType.ORACLE == DBUtil.getDBType()) {
					sb.append(" select " + " t1.b0114 " + " from b01 t1 where t1.b0114 in " + " ( select s1.b0114 from "
							+ " (select t.b0114 from  b01 t, competence_userdept c " + " where c.b0111 = t.b0111 "
							+ " and c.userid = '" + userid + "') s1, " + " (select k.b0114 "
							+ " from b01 k where k.b0114 is not null and length(trim(k.b0114))!=0 ");
					sb.append(" group by k.b0114 " + " having count(K.b0114) > 1) s2 where  s1.b0114=s2.b0114 ) ");
				} else if (DBType.MYSQL == DBUtil.getDBType()) {
					sb.append(" select * from ( select " + " t1.b0114 " + " from b01 t1 where t1.b0114 in "
							+ " ( select s1.b0114 from " + " (select t.b0114 from  b01 t, competence_userdept c "
							+ " where c.b0111 = t.b0111 " + " and c.userid = '" + userid + "') s1, "
							+ " (select k.b0114 "
							+ " from b01 k where k.b0114 is not null and length(trim(k.b0114))!=0  ");
					sb.append(" group by k.b0114 "
							+ " having count(K.b0114) > 1) s2 where  s1.b0114=s2.b0114 ) order by t1.b0114 ) ts  ");
				}
				List<HashMap<String, Object>> listRepeat = cq.getListBySQL(sb.toString());
				if (listRepeat.size() > 0) {
					this.setSelfDefResData("1@�����ظ��Ļ������룬�����������ػ���ϢУ�ˣ���������У��->�����ظ�У�ˣ������ݻ�����Ϣ���ֶ��޸��ظ��ı���!");
					return EventRtnType.NORMAL_SUCCESS;
				}
				/* �жϻ��������Ƿ�Ϸ� */
				StringBuffer sbLegal = new StringBuffer();
				if (DBType.ORACLE == DBUtil.getDBType()) {
					sbLegal.append(" select t.b0111 from b01 t,competence_userdept s where t.b0111 = s.b0111  "
							+ " and s.userid = '" + userid + "' "
							+ " and not regexp_like(t.b0114, '^([a-zA-Z0-9]{3}[.])*([a-zA-Z0-9]{3})$') "
							+ " and t.b0114 is not null " + " and length(trim(t.b0114))!=0 ");
				} else if (DBType.MYSQL == DBUtil.getDBType()) {
					sbLegal.append(" select t.b0111 from b01 t,competence_userdept s where t.b0111 = s.b0111 "
							+ " and s.userid = '" + userid + "' "
							+ " and t.b0114  not regexp  '^([a-zA-Z0-9]{3}[.])*([a-zA-Z0-9]{3})$'"
							+ " and t.b0114 is not null " + "and length(trim(t.b0114))!=0");
				}
				List<HashMap<String, Object>> listLegal = cq.getListBySQL(sbLegal.toString());
				if (listLegal.size() > 0) {
					String title = "3@���ڲ�Ϊ���Ҳ��Ϸ��Ļ������룬����ȷ�ϲ鿴��Ϊ���Ҳ��Ϸ��Ļ�����Ϣ���ֶ��޸Ļ�������!";
					this.setSelfDefResData(title);
					return EventRtnType.NORMAL_SUCCESS;
				}
				setB0114(userid, "-1");
//				List<HashMap> list_only=null;
//				if(DBType.ORACLE==DBUtil.getDBType()){
//					sql="select b0111,b0121 from ( select t.b0111,t.b0121 from competence_userdept c,b01 t "
//							+ " where  t.b0111=c.b0111 "
//							+ " and c.userid='"+userid+"' "
//							+ " order by length(t.b0111),t.SORTID asc) where ROWNUM =1";
//					list_only=CommonQueryBS.getQueryInfoByManulSQL(sql);
//				}else if(DBType.MYSQL==DBUtil.getDBType()){
//					sql="select t.b0111,t.b0121 from competence_userdept c,b01 t "
//							+ " where  t.b0111=c.b0111 "
//							+ " and c.userid='"+userid+"' order by length(t.b0111),t.SORTID asc limit 1";
//					list_only=CommonQueryBS.getQueryInfoByManulSQL(sql);
//				}
//				String b0111_super="";//��ȡ�û�Ȩ�޷�Χ��,����Ϊ����߼���������ϼ�����id
//				if(list_only!=null&&list_only.size()>0){
//					b0111_super=(String)list_only.get(0).get("b0121");//��ȡ�û�Ȩ�޷�Χ��,����Ϊ����߼���������ϼ�����id
//				}else{
//					this.setSelfDefResData("1@�����쳣!");
//					return EventRtnType.NORMAL_SUCCESS;
//				}
//				
//				String cueUserid=SysUtil.getCacheCurrentUser().getId();
//				//��ȡ�û�Ȩ�޷�Χ�ڵ���ͼ���λ��id
//			 	List<HashMap> listh =null;
//				try {
//					if(DBType.ORACLE==DBUtil.getDBType()){
//						sql="select b0111 from ( select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) desc) where ROWNUM =1";
//						listh=CommonQueryBS.getQueryInfoByManulSQL(sql);
//					}else if(DBType.MYSQL==DBUtil.getDBType()){
//						sql="select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) desc limit 1";
//						listh=CommonQueryBS.getQueryInfoByManulSQL(sql);
//					}
//				}catch(AppException e){
//					e.printStackTrace();
//				}
//				String nodeh="";
//				if(listh!=null&&listh.size()>0){
//					nodeh=(String)listh.get(0).get("b0111");//��ȡ�û�Ȩ�޷�Χ�ڵ���߼���λ��id
//				}else{
//					this.setSelfDefResData("1@@@"+"�������!");
//				}
//				
//				String b0114_super="";//��ȡ�û�Ȩ�޷�Χ��,����Ϊ����߼���������ϼ���������
//				List<HashMap<String, Object>> list_super=cq.getListBySQL("select t.b0114 from b01 t where t.b0111='"+b0111_super+"' ");
//				if(list_super==null||list_super.size()<1){
//					this.setSelfDefResData("1@�����쳣!");
//					return EventRtnType.NORMAL_SUCCESS;
//				}else{
//					b0114_super=(String)list_super.get(0).get("b0114");//��ȡ�û�Ȩ�޷�Χ��,����Ϊ����߼���������ϼ���������
//				}
//				
//				sql="select t.b0111,t.b0114,t.b0194 from competence_userdept c,b01 t "
//						+ " where  t.b0111=c.b0111 "
//						+ " and c.userid='"+userid+"' "
//						+ " and t.b0121='"+b0111_super+"' "
//						+ " order by length(t.b0111),t.SORTID asc ";
//				List<HashMap<String, Object>> list_all =cq.getListBySQL(sql);//�����ϼ�id��ȡ�¼������¼�����
//				
//				//���������벻Ϊ�յĻ�������,ƴ�ӳ��ַ���
//				String str1=returnStr(list_all);
//				//��ʼ����һ������
//				String b0114_ns="0";
//				String b0114_fz="0";
//				for(int i=0;i<list_all.size();i++){//���������¼�����
//					if(list_all.get(i).get("b0114")==null
//							||((String)list_all.get(i).get("b0114")).trim().length()==0
//							){//����Ϊ��,�Ҳ�Ϊ���˵�λ
//						//�Զ����뱾�������
//						if("2".equals((String)list_all.get(i).get("b0194"))){//����
//							b0114_ns=returnNum(b0114_super,b0114_ns,str1);//���������������
//							
//							if(b0114_ns.length()>=4){
//								flag_ns=true;//������������־
//								continue;
//							}
//							if(b0114_super==null||b0114_super.length()==0){//�������ϼ���������Ϊ�� (���ǵ�һ�������ǻ��������ұ���Ϊ�գ����ظ��ڵ����ҲΪ��ʱ�����)
//								st.addBatch(" update b01 set b0114='"+b0114_ns+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}else{
//								st.addBatch(" update b01 set b0114='"+b0114_super+"."+b0114_ns+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}
//							st_num=st_num+1;
//							if(st_num%300==0){
//								st.executeBatch();
//								st.close();//��ȫ����ʱ��ܳ���st��ʱ�䲻���ͷţ��ᱨ�������Ӳ����ͷ�
//								st=con.createStatement();
//							}
//							//�Զ������¼�����
//							if(b0114_super==null||b0114_super.length()==0){
//								aotumaticCode1((String)list_all.get(i).get("b0111"),b0114_ns,st,nodeh);
//							}else{
//								aotumaticCode1((String)list_all.get(i).get("b0111"),b0114_super+"."+b0114_ns,st,nodeh);
//							}
//						}else if("3".equals((String)list_all.get(i).get("b0194"))){//����
//							b0114_fz=returnNumFz(b0114_super,b0114_fz,str1);//���ɷ����������
//							
//							if(b0114_fz.length()>=3){
//								flag_fz=true;//������������־
//								continue;
//							}
//							if(b0114_super==null||b0114_super.length()==0){//�������ϼ���������Ϊ�� (���ǵ�һ�������ǻ��������ұ���Ϊ�գ����ظ��ڵ����ҲΪ��ʱ�����)
//								st.addBatch(" update b01 set b0114='V"+b0114_fz+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}else{
//								st.addBatch(" update b01 set b0114='"+b0114_super+".V"+b0114_fz+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}
//							st_num=st_num+1;
//							if(st_num%300==0){
//								st.executeBatch();
//								st.close();//��ȫ����ʱ��ܳ���st��ʱ�䲻���ͷţ��ᱨ�������Ӳ����ͷ�
//								st=con.createStatement();
//							}
//							//�Զ������¼�����
//							if(b0114_super==null||b0114_super.length()==0){
//								aotumaticCode1((String)list_all.get(i).get("b0111"),"V"+b0114_fz,st,nodeh);
//							}else{
//								aotumaticCode1((String)list_all.get(i).get("b0111"),b0114_super+".V"+b0114_fz,st,nodeh);
//							}
//						}
//					}else{
//						//�Զ������¼�����
//						aotumaticCode1((String)list_all.get(i).get("b0111"),((String)list_all.get(i).get("b0114")).trim(),st,nodeh);
//					}
//				}
//				st.executeBatch();
//				con.commit();
//				st.close();
//				con.close();
				if (flag_fz == true && flag_ns == true) {
					this.setSelfDefResData("2@�¼�ֱ��������������������࣬���Զ����벿�ֻ�������������ֶ�����!");
				} else if (flag_fz == true) {
					this.setSelfDefResData("2@�¼�ֱ������������࣬���Զ����벿�ֻ�������������ֶ�����!");
				} else if (flag_ns == true) {
					this.setSelfDefResData("2@�¼�ֱ������������࣬���Զ����벿�ֻ�������������ֶ�����!");
				} else if (flag_fz == false && flag_ns == false) {
					this.setSelfDefResData("2@����ɹ�!");
				} else {
					this.setSelfDefResData("2@����ɹ�!");
				}
				st_num = 0;// �ָ���ʼ��
				flag_ns = false;// �ָ���ʼ��
				flag_fz = false;// �ָ���ʼ��
			}
		} catch (Exception e) {
//			try{
//				if(st!=null){
//					st.close();
//				}
//				if(con!=null){
//			    	try{
//			    		con.rollback();
//					    con.close();
//			    	}catch(Exception e1){
//			    		
//			    	}
//			    }
//			}catch(Exception e1){
//				e1.printStackTrace();
//			}
			e.printStackTrace();
			this.setSelfDefResData("1@�Զ�����ʧ��!");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ���ݴ����������ַ�����ʵ������ superStr �ϼ��������� str //10���� �����ַ��� str1 ������ �У�����������������벻Ϊ�յı���ƴ��
	 * 
	 * @return
	 */
	public String returnNumFz(String superStr, String str, String str1) {
		str = (Integer.parseInt(str) + 1) + "";
		if (str.length() == 1) {
			str = "0" + str;
		}
		// -1������
		if (str1.indexOf("$" + superStr + ".V" + str + "$") == -1) {
			// return str;
		} else {// �����Ѿ����ڣ���ݹ飬��������
			str = returnNum(superStr, str, str1);
		}
		return str;
	}

	/**
	 * ���ݴ����������ַ�����ʵ������ superStr �ϼ��������� str //10���� �����ַ��� str1 ������ �У�����������������벻Ϊ�յı���ƴ��
	 * 
	 * @return
	 */
	public String returnNum(String superStr, String str, String str1) {
		str = (Integer.parseInt(str) + 1) + "";
		if (str.length() == 1) {
			str = "00" + str;
		} else if (str.length() == 2) {
			str = "0" + str;
		}
		// -1������
		if (str1.indexOf("$" + superStr + "." + str + "$") == -1) {
			// return str;
		} else {// �����Ѿ����ڣ���ݹ飬��������
			str = returnNum(superStr, str, str1);
		}
		return str;
	}

	/**
	 * ����listƴ�ӳɵ��ַ���
	 * 
	 * @param list_notnull
	 * @return
	 */
	public String returnStr(List<HashMap<String, Object>> list) {
		String str = "";
		for (int i = 0; i < list.size(); i++) {// ���� list
			if (list.get(i).get("b0114") != null && ((String) list.get(i).get("b0114")).trim().length() > 0) {// ���벻Ϊ��
				str = str + list.get(i).get("b0114") + "$";
			}
		}
		str = "$" + str;
		return str;
	}

	/**
	 * �Զ����� b0111 �ϼ�������id b0114 �ϼ������ı���
	 * 
	 * @throws AppException
	 * @throws SQLException
	 */
	public int aotumaticCode1(String b0111, String b0114, Statement st, String nodeh)
			throws AppException, SQLException {
		try {
			if (b0111 == null || b0111.length() >= nodeh.length()) {// �����򣬴˻���idû���¼�
				return EventRtnType.NORMAL_SUCCESS;
			}
			String userid = SysUtil.getCacheCurrentUser().getId();

			String sql = "select t.b0111,t.b0114,t.b0194 from competence_userdept c,b01 t " + " where  t.b0111=c.b0111 "
					+ " and c.userid='" + userid + "' " + " and t.b0121='" + b0111 + "' "
					+ " order by length(t.b0111),t.SORTID asc ";
			CommQuery cq = new CommQuery();
			List<HashMap<String, Object>> list_all = cq.getListBySQL(sql);// �����ϼ�id��ȡ�����¼���������

			// ���������벻Ϊ�յı���,ƴ�ӳ��ַ���
			String str1 = returnStr(list_all);
			// ��ʼ����һ������
			// ��ʼ����һ������
			String b0114_ns = "0";
			String b0114_fz = "0";

			for (int i = 0; i < list_all.size(); i++) {

				if (list_all.get(i).get("b0114") == null
						|| ((String) list_all.get(i).get("b0114")).trim().length() == 0) {// ����Ϊ��,�Ҳ�Ϊ���˵�λ
					if ("2".equals((String) list_all.get(i).get("b0194"))) {// ����
						// �Զ����뱾�������
						b0114_ns = returnNum(b0114, b0114_ns, str1);// ���ɱ���
						if (b0114_ns.length() >= 4) {
							flag_ns = true;// ���������־
							continue;
						}
						if (b0114 == null || b0114.length() == 0 || "null".equals(b0114)) {// �������ϼ���������Ϊ��
																							// (���ǵ�һ�������ǻ��������ұ���Ϊ�գ����ظ��ڵ����ҲΪ��ʱ�����)
							st.addBatch(" update b01 set b0114='" + b0114_ns + "' where b0111= '"
									+ list_all.get(i).get("b0111") + "' ");
						} else {
							st.addBatch(" update b01 set b0114='" + b0114 + "." + b0114_ns + "' where b0111= '"
									+ list_all.get(i).get("b0111") + "' ");
						}
						st_num = st_num + 1;
						if (st_num % 300 == 0) {
							st.executeBatch();
						}
						// �Զ������¼�����
						aotumaticCode1((String) list_all.get(i).get("b0111"), b0114 + "." + b0114_ns, st, nodeh);
					} else if ("3".equals((String) list_all.get(i).get("b0194"))) {// ����
						// �Զ����뱾�������
						b0114_fz = returnNumFz(b0114, b0114_fz, str1);// ���ɱ���
						if (b0114_fz.length() >= 4) {
							flag_fz = true;// ���������־
							continue;
						}
						if (b0114 == null || b0114.length() == 0 || "null".equals(b0114)) {// �������ϼ���������Ϊ��
																							// (���ǵ�һ�������ǻ��������ұ���Ϊ�գ����ظ��ڵ����ҲΪ��ʱ�����)
							st.addBatch(" update b01 set b0114='V" + b0114_fz + "' where b0111= '"
									+ list_all.get(i).get("b0111") + "' ");
						} else {
							st.addBatch(" update b01 set b0114='" + b0114 + ".V" + b0114_fz + "' where b0111= '"
									+ list_all.get(i).get("b0111") + "' ");
						}
						st_num = st_num + 1;
						if (st_num % 300 == 0) {
							st.executeBatch();
						}
						// �Զ������¼�����
						aotumaticCode1((String) list_all.get(i).get("b0111"), b0114 + ".V" + b0114_fz, st, nodeh);
					}
				} else {
					// �Զ������¼�����
					aotumaticCode1((String) list_all.get(i).get("b0111"),
							((String) list_all.get(i).get("b0114")).trim(), st, nodeh);
				}
			}
		} catch (Exception e) {
			if (st != null) {
				try {
					st.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			throw new AppException(e.getMessage() + "---------�Զ�����ʧ�ܣ�");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("recoverBtn.onclick")
	public int recover() throws RadowException, AppException {
		this.getExecuteSG().addExecuteCode("$h.openWin('recover','pages.sysorg.org.recover','��������վҳ��',860,510,'','"
				+ request.getContextPath() + "');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// ��������
	public void record(String organization, String filename, String sql, String tablename, String userid)
			throws Exception {
		CommonQueryBS.systemOut("�������ݵ����ݿ⣨��ʼ��");
		HBSession sess = HBUtil.getHBSession();
		List<String> a0000s = sess.createSQLQuery(sql).list();
		StringBuilder sba01 = new StringBuilder();
		StringBuilder sba02 = new StringBuilder();
		for (String a0000 : a0000s) {
			List<A02> listA02 = sess.createSQLQuery("select * from a02 where a0000='" + a0000 + "'")
					.addEntity(A02.class).list();
			if (listA02.size() == 1) {// һ�����ζ�����������a01���ñ���
				sba01.append("'" + a0000 + "'" + ",");// ֱ��ɾ������
			} else {
				StringBuilder sba0200s = new StringBuilder();
				int falg = 0;
				for (A02 a02 : listA02) {
					if (a02.getA0201b() == null || a02.getA0201b().length() < organization.length()) {
						continue;
					}
					String a0201b = a02.getA0201b().substring(0, organization.length());
					if (a0201b.equals(organization)) {// ɾ��a02����Ϣ
						falg++;
						sba0200s.append("'" + a02.getA0200() + "'" + ",");
					}
				}
				if (falg == listA02.size()) {// ����˵���ְ����ȫ������������� ��ֱ��ɾ�˾���
					sba01.append("'" + a0000 + "'" + ",");
				} else {
					sba02.append(sba0200s);
				}
			}

		}

		if (sba01 == null || sba01.toString().equals("")) {
			String a01tempsql = "CREATE TABLE a01" + tablename + " as select * from a01 where a0000 in ('" + sba01
					+ "')";
			String a02tempsql;
			if (sba02 == null || sba02.toString().equals("")) {
				a02tempsql = "CREATE TABLE a02" + tablename + " as select * from a02 where a0200 in ('" + sba02
						+ "') or a0000 in ('" + sba01 + "')";
			} else {
				sba02.deleteCharAt(sba02.length() - 1);
				a02tempsql = "CREATE TABLE a02" + tablename + " as select * from a02 where a0200 in (" + sba02
						+ ") or a0000 in ('" + sba01 + "')";
			}
			String a05tempsql = "CREATE TABLE a05" + tablename + " as select * from a05 where a0000 in ('" + sba01
					+ "')";
			String a06tempsql = "CREATE TABLE a06" + tablename + " as select * from a06 where a0000 in ('" + sba01
					+ "')";
			String a08tempsql = "CREATE TABLE a08" + tablename + " as select * from a08 where a0000 in ('" + sba01
					+ "')";
			String a14tempsql = "CREATE TABLE a14" + tablename + " as select * from a14 where a0000 in ('" + sba01
					+ "')";
			String a15tempsql = "CREATE TABLE a15" + tablename + " as select * from a15 where a0000 in ('" + sba01
					+ "')";
			String a36tempsql = "CREATE TABLE a36" + tablename + " as select * from a36 where a0000 in ('" + sba01
					+ "')";
			String a11tempsql = "CREATE TABLE a11" + tablename + " as select * from a11 where a0000 in ('" + sba01
					+ "')";
			String a41tempsql = "CREATE TABLE a41" + tablename + " as select * from a41 where a0000 in ('" + sba01
					+ "')";
			String a29tempsql = "CREATE TABLE a29" + tablename + " as select * from a29 where a0000 in ('" + sba01
					+ "')";
			String a53tempsql = "CREATE TABLE a53" + tablename + " as select * from a53 where a0000 in ('" + sba01
					+ "')";
			String a37tempsql = "CREATE TABLE a37" + tablename + " as select * from a37 where a0000 in ('" + sba01
					+ "')";
			String a31tempsql = "CREATE TABLE a31" + tablename + " as select * from a31 where a0000 in ('" + sba01
					+ "')";
			String a30tempsql = "CREATE TABLE a30" + tablename + " as select * from a30 where a0000 in ('" + sba01
					+ "')";
			String a99Z1tempsql = "CREATE TABLE A99Z1" + tablename + " as select * from A99Z1 where a0000 in ('" + sba01
					+ "')";
			String b01tempsql = "CREATE TABLE b01" + tablename + " as select * from b01 where b0111 like " + "'"
					+ organization + "%'";
			String COMPETENCE_USERtempsql = "CREATE TABLE COMPETENCE_USERDEPT" + tablename
					+ " as select * from COMPETENCE_USERDEPT where b0111 like '" + organization + "%' and userid='"
					+ userid + "'";
			HBUtil.getHBSession().createSQLQuery(a01tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a02tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a05tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a06tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a08tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a14tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a15tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a36tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a11tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a41tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a29tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a53tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a37tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a31tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a30tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a99Z1tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(b01tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(COMPETENCE_USERtempsql).executeUpdate();
			Date day = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sqldatarecord = "INSERT INTO datarecord (organization, temp_table,filename,removedate) VALUES ('"
					+ organization + "', '" + tablename + "','" + filename + "','" + df.format(day) + "')";
			HBUtil.getHBSession().createSQLQuery(sqldatarecord).executeUpdate();
		} else {
			sba01.deleteCharAt(sba01.length() - 1);
			String a01tempsql = "CREATE TABLE a01" + tablename + " as select * from a01 where a0000 in (" + sba01 + ")";
			String a02tempsql;
			if (sba02 == null || sba02.toString().equals("")) {
				a02tempsql = "CREATE TABLE a02" + tablename + " as select * from a02 where a0200 in ('" + sba02
						+ "') or a0000 in (" + sba01 + ")";
			} else {
				sba02.deleteCharAt(sba02.length() - 1);
				a02tempsql = "CREATE TABLE a02" + tablename + " as select * from a02 where a0200 in (" + sba02
						+ ") or a0000 in (" + sba01 + ")";
			}
			String a05tempsql = "CREATE TABLE a05" + tablename + " as select * from a05 where a0000 in (" + sba01 + ")";
			String a06tempsql = "CREATE TABLE a06" + tablename + " as select * from a06 where a0000 in (" + sba01 + ")";
			String a08tempsql = "CREATE TABLE a08" + tablename + " as select * from a08 where a0000 in (" + sba01 + ")";
			String a14tempsql = "CREATE TABLE a14" + tablename + " as select * from a14 where a0000 in (" + sba01 + ")";
			String a15tempsql = "CREATE TABLE a15" + tablename + " as select * from a15 where a0000 in (" + sba01 + ")";
			String a36tempsql = "CREATE TABLE a36" + tablename + " as select * from a36 where a0000 in (" + sba01 + ")";
			String a11tempsql = "CREATE TABLE a11" + tablename + " as select * from a11 where a0000 in (" + sba01 + ")";
			String a41tempsql = "CREATE TABLE a41" + tablename + " as select * from a41 where a0000 in (" + sba01 + ")";
			String a29tempsql = "CREATE TABLE a29" + tablename + " as select * from a29 where a0000 in (" + sba01 + ")";
			String a53tempsql = "CREATE TABLE a53" + tablename + " as select * from a53 where a0000 in (" + sba01 + ")";
			String a37tempsql = "CREATE TABLE a37" + tablename + " as select * from a37 where a0000 in (" + sba01 + ")";
			String a31tempsql = "CREATE TABLE a31" + tablename + " as select * from a31 where a0000 in (" + sba01 + ")";
			String a30tempsql = "CREATE TABLE a30" + tablename + " as select * from a30 where a0000 in (" + sba01 + ")";
			String a99Z1tempsql = "CREATE TABLE A99Z1" + tablename + " as select * from A99Z1 where a0000 in (" + sba01
					+ ")";
			String b01tempsql = "CREATE TABLE b01" + tablename + " as select * from b01 where b0111 like " + "'"
					+ organization + "%'";
			String COMPETENCE_USERtempsql = "CREATE TABLE COMPETENCE_USERDEPT" + tablename
					+ " as select * from COMPETENCE_USERDEPT where b0111 like '" + organization + "%' and userid='"
					+ userid + "'";
			HBUtil.getHBSession().createSQLQuery(a01tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a02tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a05tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a06tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a08tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a14tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a15tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a36tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a11tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a41tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a29tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a53tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a37tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a31tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a30tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(a99Z1tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(b01tempsql).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(COMPETENCE_USERtempsql).executeUpdate();
			Date day = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sqldatarecord = "INSERT INTO datarecord (organization, temp_table,filename,removedate) VALUES ('"
					+ organization + "', '" + tablename + "','" + filename + "','" + df.format(day) + "')";
			HBUtil.getHBSession().createSQLQuery(sqldatarecord).executeUpdate();
		}

		CommonQueryBS.systemOut("�������ݵ����ݿ⣨������");

	}

	// ɾ���û�
	public void deleteUser(String groupid) throws SQLException {
		CommonQueryBS.systemOut("ɾ���û����µ��û�");
		HBSession sess = HBUtil.getHBSession();
		sess.createSQLQuery("UPDATE SMT_USER SET USEFUL = '0' WHERE OTHERINFO like '" + groupid + "%'").executeUpdate();
		CommonQueryBS.systemOut("ɾ���û����");

	}

	// ɾ����Ա
	public void deletePerson(String sql, String groupid) throws SQLException {
		CommonQueryBS.systemOut("ɾ����Ա");
		HBSession sess = HBUtil.getHBSession();
		List<String> a01s = sess.createSQLQuery(sql).list();
		if (a01s.size() == 0 || a01s == null) {
			CommonQueryBS.systemOut("ɾ���ɹ�");
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (String a0000 : a01s) {
			List<A02> listA02 = sess.createSQLQuery("select * from a02 where a0000='" + a0000 + "'")
					.addEntity(A02.class).list();
			if (listA02.size() == 1) {
				sb.append("'" + a0000 + "'" + ",");
			} else {// һ�����ζ���������
				StringBuilder sba0200s = new StringBuilder();
				int falg = 0;
				for (A02 a02 : listA02) {
					if (a02.getA0201b().length() < groupid.length()) {
						continue;
					}
					String a0201b = a02.getA0201b().substring(0, groupid.length());
					if (a0201b.equals(groupid)) {// ɾ��
						falg++;
						String a0200 = a02.getA0200();
						sba0200s.append("'" + a0200 + "',");
					}
				}
				if (listA02.size() == falg) {// ���������ȫ���������������ְ����ȫ��ɾ������˵�������Ϣ
					sb.append("'" + a0000 + "'" + ",");
				} else {
					if (!sba0200s.toString().equals("")) {
						sba0200s.deleteCharAt(sba0200s.length() - 1);
						HBUtil.getHBSession().createSQLQuery("delete from a02 where a0200 in (" + sba0200s + ")")
								.executeUpdate();
					}
				}

			}
		}
		if (sb == null || sb.toString().equals("")) {

		} else {
			sb.deleteCharAt(sb.length() - 1);
			String sqla01 = "delete from a01 where a0000 in (" + sb + ")";
			String sqla02 = "delete from a02 where a0000 in (" + sb + ")";
			String sqla05 = "delete from a05 where a0000 in (" + sb + ")";
			String sqla06 = "delete from a06 where a0000 in (" + sb + ")";
			String sqla08 = "delete from a08 where a0000 in (" + sb + ")";
			String sqla14 = "delete from a14 where a0000 in (" + sb + ")";
			String sqla15 = "delete from a15 where a0000 in (" + sb + ")";
			String sqla36 = "delete from a36 where a0000 in (" + sb + ")";
			String sqla11 = "delete from a11 where a0000 in (" + sb + ")";
			String sqla41 = "delete from a41 where a0000 in (" + sb + ")";
			String sqla29 = "delete from a29 where a0000 in (" + sb + ")";
			String sqla53 = "delete from a53 where a0000 in (" + sb + ")";
			String sqla37 = "delete from a37 where a0000 in (" + sb + ")";
			String sqla31 = "delete from a31 where a0000 in (" + sb + ")";
			String sqla30 = "delete from a30 where a0000 in (" + sb + ")";
			String sqla99Z1 = "delete from A99Z1 where a0000 in (" + sb + ")";
			HBUtil.getHBSession().createSQLQuery(sqla01).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla02).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla05).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla06).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla08).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla14).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla15).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla36).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla11).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla41).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla29).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla53).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla37).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla31).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla30).executeUpdate();
			HBUtil.getHBSession().createSQLQuery(sqla99Z1).executeUpdate();
		}
		CommonQueryBS.systemOut("ɾ����Ա�ɹ�");
	}

	public static void main(String[] args) throws Exception {
		String s = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
		System.out.println(s.length() + "==================" + s);
	}

	/**
	 * ְλ��Ϣ �������ѡ������������ѣ������½�
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("gwConfBtn.onclick")
	public int gwConfBtn() throws RadowException, Exception {
		// ��ȡǰ̨grid����
		Grid grid = (Grid) this.getPageElement("orgInfoGrid");

		List<HashMap<String, Object>> list = grid.getValueList();

		int num = 0;
		String groupid = "";
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			String checked = map.get("personcheck") + "";
			if ("true".equals(checked)) {
				num = num + 1;
				groupid = (String) map.get("b0111");
			}
		}
		if (num > 1) {
			this.setMainMessage("����ѡ��һ������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (num < 1) {
			this.setMainMessage("��ѡ��һ������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		System.out.println("+++++++++++++=" + groupid);
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('gwConfWin','pages.sysorg.org.GwConf','ְλ��������ҳ��',760,460,'"
				+ groupid + "','" + ctxPath + "');");
		// this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ְλ��Ϣ �������ѡ������������ѣ������½�
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("refSetBtn.onclick")
	public int refSetBtn() throws RadowException, Exception {
		String id = this.getPageElement("checkedgroupid").getValue();
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('orgimpWin','pages.sysorg.org.OrgRefSet','����',480,520,'" + id + "','" + ctxPath + "');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/* ͬ��oa���� */
	@SuppressWarnings("static-access")
	@PageEvent("synchroOA")
	public void synchroOA() throws ParseException, AppException {

		try {
			caClient2 ca = new caClient2();
			HashMap<String, String>[] user1 = ca.getAllDept(); // �ӽӿڻ�ȡ����
			for (HashMap<String, String> map : user1) {
				// System.out.println("result is " + map);
				ca.addDept(map); // ѭ����������
			}
			this.setMainMessage("ͬ���ɹ�");
		} catch (Exception e) {
			// TODO: handle exception
			this.setMainMessage("ͬ��ʧ�ܣ�" + e);
		}
	}

	/* ͬ��oa�û����� */
	@SuppressWarnings("static-access")
	@PageEvent("synchroOAUser")
	public void synchroOAUser() throws ParseException {
		try {
			String OWNER = PrivilegeManager.getInstance().getCueLoginUser().getId(); // ��ȡ��ǰ�û�id ���ڲ��봴���ߵ�ֵ
			caClient2 ca = new caClient2();
			HashMap<String, String>[] users = ca.getAllUser(); // �ӽӿڻ�ȡ����
			for (HashMap<String, String> map : users) {
				// System.out.println("result is " + map);
				ca.addUser(map, OWNER);// ѭ����������
			}
			this.setMainMessage("ͬ���ɹ�");
		} catch (Exception e) {
			// TODO: handle exception
			this.setMainMessage("ͬ��ʧ�ܣ�" + e);
		}
	}
	
	
	
	
	/**
	 * 
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("zzsBtn.onclick")
	public int zzsBtn() throws RadowException, Exception {
		// ��ȡǰ̨grid����
		Grid grid = (Grid) this.getPageElement("orgInfoGrid");

		List<HashMap<String, Object>> list = grid.getValueList();

		int num = 0;
		String groupid = "";
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			String checked = map.get("personcheck") + "";
			if ("true".equals(checked)) {
				num = num + 1;
				groupid = (String) map.get("b0111");
			}
		}
		if (num > 1) {
			this.setMainMessage("����ѡ��һ������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (num < 1) {
			this.setMainMessage("��ѡ��һ������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//'001.001.004.003'
		if(groupid.length()<15){
			this.setMainMessage("��ѡ�¼���λ!");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			groupid = groupid.substring(0,15);
		}
		
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(" $h.openWin('zzsAdd', 'pages.sysorg.zzs.ZZS', '��֯ʷ ', 1200, 850, null, '"+ctxPath+"', null, { maximizable: false,resizable: false,closeAction: 'close',jgId:'"+groupid+"'})" );

		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	

}
