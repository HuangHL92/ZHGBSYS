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
	 * 系统区域信息
	 */
	public Hashtable<String, Object> areaInfo = new Hashtable<String, Object>();
	public static String tag = "0";// 0未执行1执行
	public static String b0101stauts = "0";// 0未改变1改变
	public static String b0104stauts = "0";// 0未改变1改变
	public static String b0194Type = "0";// 单位类型1单位 2内设机构 3分组
	public long st_num = 0;// 自动编码，批量更新记录数
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

	// 页面初始化
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
	 * 补全机构编码，校验出法人单位编码为空的机构信息，弹窗点击确认，执行此方法 列表显示不合法的机构信息
	 * 
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("scanLegalPerson")
	public int scanLegalPerson() throws RadowException, AppException {
		String userid = SysUtil.getCacheCurrentUser().getId();
		String sql = "select " + " case when t.b0194='1' then '法人单位' " + " when t.b0194='2' then '内设机构' "
				+ " when t.b0194='3' then '机构分组' else '' end b0194,"// 机构类型(法人单位标识 等(1法人单位 2内设单位 3机构分组))
				+ " t.b0114,"// 机构编码
				+ " t.b0101,"// 机构名称
				+ " t2.b0101 b0101parent,"// 父节点名称
				+ " t.b0104,"// 简称
				+ " t.b0111,"// 机构主键
				+ " t.b0117,"// 机构政区
				+ " t.b0124,"// 隶属关系
				+ " t.b0131,"// 机构类别
				+ " t.b0127,"// 机构级别
				+ " s.type"// 权限类型(0：浏览，1：维护)
				+ "  from b01 t,competence_userdept s,b01 t2 " + " where " + " t.b0111=s.b0111 "
				+ " and t.b0121=t2.b0111 " + " and s.userid='" + userid + "' "// 用户id
				+ " and (t.b0114 is null or length(trim(t.b0114))=0) "// 为空
				+ " and t.b0194='1' "// 法人单位
		;
		CommQuery cq = new CommQuery();

		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		String jgjh_sql = "select t.b0111 " + sql.substring(sql.indexOf("from"), sql.length());
		request.getSession().setAttribute("jgjh_sql", jgjh_sql);
		this.getPageElement("orgInfoGrid").setValueList(list);
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 补全机构编码，校验出不合法的编码信息，弹窗点击确认，查询不合法的校验信息时，执行此方法 列表显示不合法的机构信息
	 * 
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("scanWrongfulUnid")
	public int scanWrongfulUnid() throws RadowException, AppException {
		String userid = SysUtil.getCacheCurrentUser().getId();
		String sql = "select " + " case when t.b0194='1' then '法人单位' " + " when t.b0194='2' then '内设机构' "
				+ " when t.b0194='3' then '机构分组' else '' end b0194,"// 机构类型(法人单位标识 等(1法人单位 2内设单位 3机构分组))
				+ " t.b0114,"// 机构编码
				+ " t.b0101,"// 机构名称
				+ " t2.b0101 b0101parent,"// 父节点名称
				+ " t.b0104,"// 简称
				+ " t.b0111,"// 机构主键
				+ " t.b0117,"// 机构政区
				+ " t.b0124,"// 隶属关系
				+ " t.b0131,"// 机构类别
				+ " t.b0127,"// 机构级别
				+ " s.type"// 权限类型(0：浏览，1：维护)
				+ "  from b01 t,competence_userdept s ,b01 t2 " + " where " + " t.b0111=s.b0111 "
				+ " and t.b0121=t2.b0111 " + " and s.userid='" + userid + "' "// 用户id
				+ " and t.b0114 is not null "// 不为空
				+ " and length(t.b0114)!=0 "// 不为空（适应mysql）
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
	 * 双击机构树，或点击查询按钮，执行此方法 根据机构id，查询当前机构与下一层级机构信息，列表显示
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("orgInfoGrid.dogridquery")
	public int doOrgQuery(int start, int limit) throws RadowException {
		String sort = request.getParameter("sort");// 要排序的列名--无需定义，ext自动后传
		String dir = request.getParameter("dir");// 要排序的方式--无需定义，ext自动后传
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
		if ("tab1".equals(tab_flag)) {// 显示机构tab页
			String orgid = this.getPageElement("checkedgroupid").getValue();
			String bhxa_check = this.getPageElement("bhxa_check").getValue();// 1 选中（包含下级） 0未选中（不包含下级）
			String sql = "select " + " case when t.b0194='1' then '法人单位' " + " when t.b0194='2' then '内设机构' "
					+ " when t.b0194='3' then '机构分组' else '' end b0194,"// 机构类型(法人单位标识 等(1法人单位 2内设单位 3机构分组))
					+ " t.b0114,"// 机构编码
					+ " t.b0101,"// 机构名称
					+ " t2.b0101 b0101parent,"// 父节点名称
					+ " t.b0104,"// 简称
					+ " t.b0111,"// 机构主键
					+ " t.b0117,"// 机构政区
					+ " t.b0124,"// 隶属关系
					+ " t.b0131,"// 机构类别
					+ "t.sign_code SignCode, " // 机构编制证管理类型/参公、事业
					+ " t.unify_code  unifyCode," // 统一社会信用代码
					+ " t.b0127,"// 机构级别
					+ " s.type"// 权限类型(0：浏览，1：维护)
					+ " ,p.grouptype" + "  from b01 t,competence_userdept s,b01 t2,REFB01GROUP p  " + " where "
					+ " t.b0111=s.b0111 " + " and t.b0121=t2.b0111 " + " and t.b0111=p.b0111(+) " + " and s.userid='"
					+ userid + "' ";// 用户id
			if ("1".equals(bhxa_check)) {// 成立，包含下级
				sql = sql + " and t.b0111  like '" + orgid + "%' ";// 机构主键
			} else {
				sql = sql + " and ( t.b0121='" + orgid + "' ";// 上级单位编码//下一级
				sql = sql + " or t.b0111='" + orgid + "' )";// 机构主键//本级
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
		} else if ("tab2".equals(tab_flag)) {// 切换到查询tab页
			String sql = "select " + " case when t.b0194='1' then '法人单位' " + " when t.b0194='2' then '内设机构' "
					+ " when t.b0194='3' then '机构分组' else '' end b0194,"// 机构类型(法人单位标识 等(1法人单位 2内设单位 3机构分组))
					+ " t.b0114,"// 机构编码
					+ " t.b0101,"// 机构名称
					+ " t2.b0101 b0101parent,"// 父节点名称
					+ " t.b0104,"// 简称
					+ " t.b0111,"// 机构主键
					+ " t.b0117,"// 机构政区
					+ " t.b0124,"// 隶属关系
					+ " t.b0131,"// 机构类别
					+ " t.b0127,"// 机构级别
					+ " s.type"// 权限类型(0：浏览，1：维护)
					+ " ,p.grouptype" + "  from b01 t, competence_userdept s,b01 t2,REFB01GROUP p " + " where "
					+ " t.b0111=s.b0111 " + " and t.b0121=t2.b0111 " + " and t.b0111=p.b0111(+) " + " and s.userid='"
					+ userid + "' ";// 用户id

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
	 * tab页面的查询条件
	 * 
	 * @param sql
	 * @return
	 * @throws RadowException
	 */
	public String returnSBSql(String sql) throws RadowException {
		String b0101 = this.getPageElement("b0101_h").getValue();// 机构名称
		String b0114 = this.getPageElement("b0114_h").getValue();// 机构编码
		String b0194 = this.getPageElement("b0194_h").getValue();// 单位类型
		String b0104 = this.getPageElement("b0104_h").getValue();// 简称

		String b0117 = this.getPageElement("b0117").getValue();// 机构政区
		String b0124 = this.getPageElement("b0124").getValue();// 隶属关系
		String b0131 = this.getPageElement("b0131").getValue();// 机构类别
		String b0127 = this.getPageElement("b0127").getValue();// 机构级别

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
	 * 双击机构树 设置被选择的机构id,到页面,并且根据单位查询，列表显示
	 * 
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("querybyid")
	public int query(String id) throws RadowException {
		this.getExecuteSG().addExecuteCode("funcAfterLoad();");// 设置查询列表标志,不合法编码，法人单位为空编码 标志为false(tab tab1
																// 不合法编码，法人单位为空编码 4中显示列表)
		// this.setRadow_parent_data(id.trim());
		String userID = SysManagerUtils.getUserId();
		Map<String, String> map = PublicWindowPageModel.isHasRule(id, userID);
		if (!map.isEmpty() || map == null) {
			if ("2".equals(map.get("type"))) {
				this.setMainMessage("您没有该机构的权限");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		this.getPageElement("checkedgroupid").setValue(id);// 设置被选择的机构id,到页面
		// String userid=SysUtil.getCacheCurrentUser().getId();
//		String sql=  " select t.b0111 from b01 t,competence_userdept s "
//		+ " where "
//			+ " t.b0111=s.b0111 "
//			+ " and s.userid='"+userid+"' "//用户id
//			+ " and ( t.b0121='"+id+"' "//上级单位编码   少
//			+ " or t.b0111='"+id+"' )"//机构主键
//			+ " order by t.b0111,t.b0121 ";
		request.setAttribute("sort", "");
		request.setAttribute("dir", "");
		this.setNextEventName("orgInfoGrid.dogridquery");// 根据机构id查询机构信息
		// this.getExecuteSG().addExecuteCode("selectNode('"+id+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 初始化组织机构树
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		String sql = "from B01 where B0121='" + node + "' order by sortid";
		@SuppressWarnings("unchecked")
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();// 得到当前组织信息
		// 只显示所在的组织及下级组织 不在组织中 则显示全部
		List<B01> choose = new ArrayList<B01>();
		String sql2 = "from B01 where B0111='" + node + "'";
		List<B01> groups = HBUtil.getHBSession().createQuery(sql2).list();// 得到当前组织信息
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

	// 新增按钮
	@PageEvent("queryGroupBtn.onclick")
	public int queryGroupBtn() throws RadowException, Exception {
		this.openWindow("queryGroupWin", "pages.sysorg.org.QueryGroup");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 监督信息 不允许多选，多个给予提醒，不能新建
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("YearCheckBtn.onclick")
	public int YearCheck() throws RadowException, Exception {
		// 获取前台grid数据
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
			this.setMainMessage("仅能选择一个机构!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (num < 1) {
			this.setMainMessage("请选择一个机构!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		System.out.println("+++++++++++++=" + groupid);
		String ctxPath = request.getContextPath();
		// this.setRadow_parent_data(groupid);
		this.getExecuteSG().addExecuteCode("$h.openWin('YearCheck','pages.sysorg.org.YearCheck','年度考核页面',760,460,'"
				+ groupid + "','" + ctxPath + "');");
		// this.openWindow("YearCheck", "pages.sysorg.org.YearCheck");
		this.request.getSession().setAttribute("tag", "0");

		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 新建下级机构 允许多选，校验选择单位个数，多个给予提醒，不能新建
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("addOrgWinBtn.onclick")
	public int addOrgWin() throws RadowException, Exception {

		String loginName = SysUtil.getCacheCurrentUser().getLoginname();
		// 获取前台grid数据
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
			this.setMainMessage("新建下级机构，仅能选择一个上级机构!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		// String pardata = "";
		// String ctxPath = request.getContextPath();
		if (!SysOrgBS.selectB01Count().equals("1")) {// 数据库中，是否仅有唯一根机构，成立，则不是
			if (num == 0) {
				groupid = this.getPageElement("checkedgroupid").getValue();
				if (groupid == null || groupid.length() == 0) {
					this.setMainMessage("请选择机构!");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			// pardata = "1,"+groupid;
			// this.setRadow_parent_data("1,"+this.getPageElement("checkedgroupid").getValue());

			if (!SysRuleBS.havaRule(groupid)) {// 无权限，则进入if

				// 判断是否在公务员系统，一级目录下新建，是则继续
				String[] str_arr = groupid.split("\\.");
				if (str_arr.length == 2) {// 在一级目录下新建
					String str = "";
					String sql = "";
					try {
						// 判断用户是否具有目录维护权限，是则跳过权限，允许新增机构，否则无此权限
						CurrentUser user = SysUtil.getCacheCurrentUser();
						sql = "select Count(1) from COMPETENCE_USERDEPT t where t.userid='" + user.getId()
								+ "' and t.type='1' ";
						str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();

					} catch (Exception e) {
						e.printStackTrace();
					}
					if (str.equals("0") && !"system".equals(loginName)) {// 不具有目录维护权限
						this.setMainMessage("您无此权限!");
						return EventRtnType.NORMAL_SUCCESS;
						// throw new RadowException("您无此权限!");
					} else {
						// 具有目录权限
					}
				} else {// 无权限
					this.setMainMessage("您无此权限!");
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
				this.setMainMessage("您无此权限!");
				return EventRtnType.NORMAL_SUCCESS;

			}
		}
		if (!SysOrgBS.selectB01Count().equals("1")) {// 数据库中，是否仅有唯一根机构，成立，则不是
			List<B01> listB01 = HBUtil.getHBSession().createSQLQuery("select * from b01 where b0111='" + groupid + "'")
					.addEntity(B01.class).list();
			if (listB01.size() == 0) {// 数据库中不存在了
				this.setMainMessage("请重新双击机构树！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}

		this.setRadow_parent_data(groupid);
		// this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin','pages.sysorg.org.CreateSysOrg','新建下级机构页面',800,500,'"+pardata+"','"+ctxPath+"');");
		this.openWindow("addOrgWin", "pages.sysorg.org.CreateSysOrg");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 机构画像 允许多选，校验选择单位个数，多个给予提醒，不能查看
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("orgPortrait")
	public int orgPortrait() throws RadowException, Exception {

		String loginName = SysUtil.getCacheCurrentUser().getLoginname();
		// 获取前台grid数据
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
			this.setMainMessage("查看机构画像，仅能选择一个机构!");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String isContain = this.getPageElement("bhxa_check").getValue();

		HttpSession session = request.getSession();
		session.setAttribute("orgid", groupid);
		session.setAttribute("isContain", isContain);
		// this.setRadow_parent_data(groupid);
		// System.out.println("传出参数"+groupid);
		this.getExecuteSG().addExecuteCode("insertInfo('" + groupid + "')");
		return 0;
	}

	/**
	 * 右键新建下级机构 允许多选，校验选择单位个数，多个给予提醒，不能新建
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("addOrgWinBtnFunc")
	public int addOrgWinFunc(String groupid) throws RadowException, Exception {

		if (groupid == null || groupid.length() == 0) {
			this.setMainMessage("参数异常!");
			return EventRtnType.NORMAL_SUCCESS;
		}

		// String pardata = "";
		// String ctxPath = request.getContextPath();
		if (!SysOrgBS.selectB01Count().equals("1")) {
			// pardata = "1,"+groupid;
			// this.setRadow_parent_data("1,"+this.getPageElement("checkedgroupid").getValue());

			if (!SysRuleBS.havaRule(groupid)) {// 无修改权限，则进入if

				// 判断是否在公务员系统，一级目录下新建，是则继续
				String[] str_arr = groupid.split("\\.");
				if (str_arr.length == 2) {// 在一级目录下新建
					String str = "";
					String sql = "";
					try {
						// 判断用户是否具有目录维护权限，是则跳过权限，允许新增机构，否则无此权限
						CurrentUser user = SysUtil.getCacheCurrentUser();
						sql = "select Count(1) from COMPETENCE_USERDEPT t where t.userid='" + user.getId()
								+ "' and t.type='1' ";
						str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();

					} catch (Exception e) {
						e.printStackTrace();
					}
					if (str.equals("0")) {// 不具有目录维护权限
						this.setMainMessage("您无此权限!");
						return EventRtnType.NORMAL_SUCCESS;
						// throw new RadowException("您无此权限!");
					} else {
						// 具有目录权限
					}
				} else {// 无权限
					this.setMainMessage("您无此权限!");
					return EventRtnType.NORMAL_SUCCESS;
					// throw new RadowException("您无此权限!");
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
				this.setMainMessage("您无此权限!");
				return EventRtnType.NORMAL_SUCCESS;
				// throw new RadowException("您无此权限!");
			}
		}
		this.setRadow_parent_data(groupid);
		// this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin','pages.sysorg.org.CreateSysOrg','新建下级机构页面',800,500,'"+pardata+"','"+ctxPath+"');");
		this.openWindow("addOrgWin", "pages.sysorg.org.CreateSysOrg");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 编制职数
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
			this.setMainMessage("请双击机构树查询!");
			return EventRtnType.NORMAL_SUCCESS;
		} else if (index != 1) {
			this.setMainMessage("请选择一个机构进行查询!");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String b0111 = (String) list2.get(0).get("b0111");// 机构编码
		String b0194 = (String) list2.get(0).get("b0194");// 机构类型
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
				"$h.openWin('SydwOrgWin','pages.sysorg.org.SydwSysOrg','事业单位编制核定表',1250,510,'','" + ctxPath + "');");

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
			this.setMainMessage("请双击机构树查询!");
			return EventRtnType.NORMAL_SUCCESS;
		} else if (index != 1) {
			this.setMainMessage("请选择一个机构进行查询!");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String b0111 = (String) list2.get(0).get("b0111");// 机构编码
		String b0194 = (String) list2.get(0).get("b0194");// 机构类型
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
				"$h.openWin('CgsyOrgWin','pages.sysorg.org.CgsySysOrg','参公事业编制核定表',1250,510,'','" + ctxPath + "');");

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
			this.setMainMessage("请双击机构树查询!");
			return EventRtnType.NORMAL_SUCCESS;
		} else if (index != 1) {
			this.setMainMessage("请选择一个机构进行查询!");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String b0111 = (String) list2.get(0).get("b0111");// 机构编码
		String b0194 = (String) list2.get(0).get("b0194");// 机构类型
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
				"$h.openWin('XzjgOrgWin','pages.sysorg.org.XzjgSysOrg','行政机关编制核定表',1250,510,'','" + ctxPath + "');");

		return EventRtnType.NORMAL_SUCCESS;
	}

	// 修改按钮
	@PageEvent("updateWinBtn.onclick")
	public int updateWinBtn() throws RadowException, AppException {

		// 获取前台grid数据
		Grid grid = (Grid) this.getPageElement("orgInfoGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		if (list == null || list.size() == 0) {
			this.setMainMessage("请双击机构树查询!");
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

		if (num == 0) {// 等于零，有查询结果，但没有选择，默认为全选
			String b0101 = "";
			for (int i = 0; i < list.size(); i++) {
				if (!"1".equals((String) list.get(i).get("type"))) {// 权限类型 不等于 1，没有修改权限
					b0101 = b0101 + (String) list.get(i).get("b0101") + "/";
				}
			}
			if (b0101.length() > 0) {
				this.setMainMessage("您没有权限修改机构： " + b0101.substring(0, b0101.length() - 1));
				return EventRtnType.NORMAL_SUCCESS;
			}
			for (int i = 0; i < list.size(); i++) {
				groupid = groupid + (String) list.get(i).get("b0111") + ",";
			}
		} else {// 有选择
			String b0101 = "";
			for (int i = 0; i < list.size(); i++) {
				if ("true".equals(list.get(i).get("personcheck") + "")) {
					if (!"1".equals((String) list.get(i).get("type"))) {// 权限类型 不等于 1，没有修改权限
						b0101 = b0101 + (String) list.get(i).get("b0101") + "/";
					}
				}

			}
			if (b0101.length() > 0) {
				b0101 = b0101.substring(0, b0101.length() - 1);
				this.setMainMessage("您没有权限修改机构： " + b0101);
				return EventRtnType.NORMAL_SUCCESS;
			}
			for (int i = 0; i < list.size(); i++) {
				if ("true".equals(list.get(i).get("personcheck") + "")) {
					groupid = groupid + (String) list.get(i).get("b0111") + ",";
				}
			}
		}

		groupid = groupid.substring(0, groupid.length() - 1);// 取出，号

		String pardata = "";
		String ctxPath = request.getContextPath();
		// String groupid = this.getPageElement("checkedgroupid").getValue();

		pardata = "2," + groupid;
		// this.getPageElement("unitidDbclAlter").setValue(pardata);
		request.getSession().setAttribute("unitidDbclAlter", pardata);//
		// this.request.getSession().setAttribute("tag", "0");
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('updateOrgWin','pages.sysorg.org.UpdateSysOrg','机构信息修改页面',1250,550,'','" + ctxPath + "');");
		// this.openWindow("updateOrgWin", "pages.sysorg.org.UpdateSysOrg");
		// this.setRadow_parent_data("2,"+this.getPageElement("checkedgroupid").getValue());
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 右键修改按钮
	@PageEvent("updateWinBtnFunc")
	public int updateWinBtnFunc(String id) throws RadowException, AppException {
//		if(groupid==null||groupid.length()==0){
//			this.setMainMessage("参数异常!");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
//		String arr[]=groupid.split(",");
//		if(!"1".equals(arr[1])){
//			this.setMainMessage("你没有权限修改!");
//			return EventRtnType.FAILD;
//		}
//		String ctxPath = request.getContextPath();
//		String pardata="2,"+arr[0];
//		this.request.getSession().setAttribute("tag", "0");
//		this.getExecuteSG().addExecuteCode("$h.openWin('updateOrgWin','pages.sysorg.org.UpdateSysOrg','机构信息修改页面',860,510,'"+pardata+"','"+ctxPath+"');");

		// 获取前台grid数据
		Grid grid = (Grid) this.getPageElement("orgInfoGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		if (list == null || list.size() == 0) {
			this.setMainMessage("请双击机构树查询!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String groupid = "";

		for (int i = 0; i < list.size(); i++) {
			groupid = groupid + (String) list.get(i).get("b0111") + ",";
		}

		groupid = groupid.substring(0, groupid.length() - 1);// 取出，号

		String pardata = "";
		String ctxPath = request.getContextPath();

		String arr[] = id.split(",");
		pardata = arr[0] + "," + groupid;
		request.getSession().setAttribute("unitidDbclAlter", pardata);//
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('updateOrgWin','pages.sysorg.org.UpdateSysOrg','机构信息修改页面',1250,550,'','" + ctxPath + "');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 机构信息导出
	@PageEvent("impOrgInfo.onclick")
	public int impOrgInfo() throws RadowException {
		String id = this.getPageElement("checkedgroupid").getValue();
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('orgimpWin','pages.sysorg.org.impOrgInfo','机构信息导出',480,520,'"
				+ id + "','" + ctxPath + "');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 排序按钮
	@PageEvent("sortSysOrgBtn.onclick")
	public int sortSysOrg() throws RadowException {
		String id = this.getPageElement("checkedgroupid").getValue();
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('orgSortWin','pages.sysorg.org.OrgSort','机构排序',505,520,'" + id + "','" + ctxPath + "');");
		// this.openWindow("orgSortWin", "pages.sysorg.org.OrgSort");
		this.request.getSession().setAttribute("transferType", "orgSort");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 删除按钮点击事件
	@PageEvent("deleteOrgBtn")
	public int deleteOrg() throws RadowException, AppException {

		// 获取前台grid数据
		Grid grid = (Grid) this.getPageElement("orgInfoGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		if (list == null || list.size() == 0) {
			this.setMainMessage("请双击机构树查询!");
			return EventRtnType.NORMAL_SUCCESS;
		}

		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if ("true".equals(list.get(i).get("personcheck") + "")) {
				count = count + 1;
			}
		}
		String unitname = "";// 机构名称
		String groupid = "";
		if (count == 0) {// 有查询结果，但是没有选择，默认全选

			String b0101 = "";
			for (int i = 0; i < list.size(); i++) {
				if (!"1".equals((String) list.get(i).get("type"))) {// 权限类型 不等于 1，没有修改权限
					b0101 = b0101 + (String) list.get(i).get("b0101") + "/";
				}
			}
			if (b0101.length() > 0) {
				this.setMainMessage("你没有权限删除机构: " + b0101.substring(0, b0101.length() - 1));
				return EventRtnType.NORMAL_SUCCESS;
			}
//			for(int i=0;i<list.size();i++){
//				//判断机构下是否有人员
//				if(SysOrgBS.whetherPersonOn1((String)list.get(i).get("b0111"))>0){//在职人员
//					unitname=unitname+(String)list.get(i).get("b0101")+"/";
//					groupid =groupid+(String)list.get(i).get("b0111")+",";
//				}
//			}
//			if(groupid.length()>0){//有人员
//				unitname=unitname.substring(0, unitname.length()-1);
//				groupid=groupid.substring(0, groupid.length()-1);
//				this.getExecuteSG().addExecuteCode("queryPersonByGroupId('"+groupid+"','"+unitname+"');");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			for(int i=0;i<list.size();i++){
//				if(SysOrgBS.whetherPersonOn2((String)list.get(i).get("b0111"))>0){//离退或历史人员
//					unitname=unitname+(String)list.get(i).get("b0101")+"/";
//					groupid =groupid+(String)list.get(i).get("b0111")+",";
//				}
//			}
//			if(groupid.length()>0){//有人员
//				unitname=unitname.substring(0, unitname.length()-1);
//				groupid=groupid.substring(0, groupid.length()-1);
//				this.getExecuteSG().addExecuteCode("queryPersonByGroupId1('"+groupid+"','"+unitname+"');");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			
//			for(int i=0;i<list.size();i++){
//				//查看是否有下级机构
//				if(SysOrgBS.whetherHasChile((String)list.get(i).get("b0111"))>0){
//					unitname=unitname+(String)list.get(i).get("b0101")+"/";
//					groupid=groupid+(String)list.get(i).get("b0111")+",";
//				}
//			}
//			if(groupid.length()>0){
//				this.setMainMessage(unitname.substring(0, unitname.length()-1)+"包含下级机构，不能删除!");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			for (int i = 0; i < list.size(); i++) {
				// groupid=groupid+(String)list.get(i).get("b0111")+",";
				unitname = unitname + (String) list.get(i).get("b0101") + "/";
			}
			if (((String) list.get(0).get("b0111")).equals("001.001")) {
				this.setMainMessage("不能删除根单位！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			groupid = (String) list.get(0).get("b0111") + ",";
		} else {// 有选择
			String b0101 = "";
			for (int i = 0; i < list.size(); i++) {
				if (!"1".equals((String) list.get(i).get("type"))) {// 权限类型 不等于 1，没有修改权限
					if ("true".equals(list.get(i).get("personcheck") + "")) {
						b0101 = b0101 + (String) list.get(i).get("b0101") + "/";
					}
				}
			}
			if (b0101.length() > 0) {
				this.setMainMessage("你没有权限删除机构: " + b0101.substring(0, b0101.length() - 1));
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*
			 * for(int i=0;i<list.size();i++){
			 * if("true".equals(list.get(i).get("personcheck")+"")){ //判断机构下是否有人员
			 * if(SysOrgBS.whetherPersonOn1((String)list.get(i).get("b0111"))>0){//在职人员
			 * unitname=unitname+(String)list.get(i).get("b0101")+"/"; groupid
			 * =groupid+(String)list.get(i).get("b0111")+","; } } }
			 * if(groupid.length()>0){//有人员 unitname=unitname.substring(0,
			 * unitname.length()-1); groupid=groupid.substring(0, groupid.length()-1);
			 * this.getExecuteSG().addExecuteCode("queryPersonByGroupId('"+groupid+"','"+
			 * unitname+"');"); return EventRtnType.NORMAL_SUCCESS; } for(int
			 * i=0;i<list.size();i++){ if("true".equals(list.get(i).get("personcheck")+"")){
			 * if(SysOrgBS.whetherPersonOn2((String)list.get(i).get("b0111"))>0){//离退或历史人员
			 * unitname=unitname+(String)list.get(i).get("b0101")+"/"; groupid
			 * =groupid+(String)list.get(i).get("b0111")+","; } } }
			 * if(groupid.length()>0){//有人员 unitname=unitname.substring(0,
			 * unitname.length()-1); groupid=groupid.substring(0, groupid.length()-1);
			 * this.getExecuteSG().addExecuteCode("queryPersonByGroupId1('"+groupid+"','"+
			 * unitname+"');"); return EventRtnType.NORMAL_SUCCESS; } for(int
			 * i=0;i<list.size();i++){ if("true".equals(list.get(i).get("personcheck")+"")){
			 * //查看是否有下级机构 if(SysOrgBS.whetherHasChile((String)list.get(i).get("b0111"))>0){
			 * unitname=unitname+(String)list.get(i).get("b0101")+"/";
			 * groupid=groupid+(String)list.get(i).get("b0111")+","; } } }
			 * if(groupid.length()>0){ this.setMainMessage(unitname.substring(0,
			 * unitname.length()-1)+"包含下级机构，不能删除!"); return EventRtnType.NORMAL_SUCCESS; }
			 */
			if ("true".equals(list.get(0).get("personcheck") + "")) {
				groupid = groupid + (String) list.get(0).get("b0111") + ",";
				unitname = (String) list.get(0).get("b0101") + "及其下级机构";
			} else {
				for (int i = 0; i < list.size(); i++) {
					if ("true".equals(list.get(i).get("personcheck") + "")) {
						if (((String) list.get(0).get("b0111")).equals("001.001")) {
							groupid = "";
							unitname = "";
							this.setMainMessage("不能删除根单位！");
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
				this.setMainMessage("该单位及下级单位有人员任职，无法删除");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		// B01 b01 = SysOrgBS.LoadB01(groupid);
		this.getPageElement("delete_groupid").setValue(groupid);// 要删除的机构id隐藏到页面
		/* dialog_set("deleteExec","确定删除机构\""+unitname+"\"吗？"); */
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
		if (groupid.contains(",")) {// 用户勾选了box
			groupid = groupid.substring(0, groupid.length() - 1);
		}
		if (groupid == null || groupid.length() == 0) {
			this.setMainMessage("参数异常!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] groupids = groupid.split(",");
		for (String g : groupids) {
			List<B01> listb01 = HBUtil.getHBSession().createSQLQuery("select * from b01 where b0111='" + g + "'")
					.addEntity(B01.class).list();
			List<B01> listb01p = HBUtil.getHBSession()
					.createSQLQuery("select * from b01 where b0111='" + listb01.get(0).getB0121() + "'")
					.addEntity(B01.class).list();// 上一级
			List<B01> listb01g = HBUtil.getHBSession()
					.createSQLQuery("select * from b01 where b0111='" + listb01p.get(0).getB0121() + "'")
					.addEntity(B01.class).list();// 上二级
			// 拼filename
			String filename = "";
			if (listb01g.get(0).getB0111().equals("-1")) {
				filename = listb01p.get(0).getB0101() + ">>>" + listb01.get(0).getB0101();
			} else {
				filename = listb01g.get(0).getB0101() + ">>>" + listb01p.get(0).getB0101() + ">>>"
						+ listb01.get(0).getB0101();
			}

			// 备份数据
			try {
				record(g, filename,
						"select a0000 from a01 where exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0201b like '"
								+ g + "%')",
						UUID.randomUUID().toString().replace("-", "").substring(0, 10), userid);
			} catch (Exception e) {
				e.printStackTrace();
				/*
				 * this.setMainMessage("备份失败！删除不能进行！"); return EventRtnType.FAILD;
				 */
				throw new RadowException("备份失败！删除不能进行！");
			}

			// 删除机构下的用户
			try {
				deleteUser(g);
			} catch (Exception e) {
				// this.setMainMessage("删除机构下的用户失败！删除不能进行！");
				e.printStackTrace();
				throw new RadowException("删除机构下的用户失败！删除不能进行！");
			}

			// 删除人员
			try {
				String personsql = "select a0000 from a01 where exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0201b like '"
						+ g + "%')";
				deletePerson(personsql, g);
			} catch (Exception e) {
				// this.setMainMessage("机构下的人员删除失败！删除不能进行！");
				e.printStackTrace();
				throw new RadowException("人员删除失败！删除不能进行！");
				// return EventRtnType.FAILD;
			}
			// 删除机构
			try {
				SysOrgBS.delB01(g);
			} catch (Exception e) {
				// this.setMainMessage("机构删除失败！");
				e.printStackTrace();
				// return EventRtnType.FAILD;
				throw new RadowException("机构删除失败！");
			}
		}

		// 刷新列表
		this.setMainMessage("删除成功");
		this.setNextEventName("orgInfoGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		this.getExecuteSG().addExecuteCode("Ext.WindowMgr.getActive().close();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 右键删除按钮点击事件
	@PageEvent("deleteOrgBtnFunc")
	public int deleteOrgFunc(String groupid) throws RadowException, AppException {
		if (groupid == null || groupid.length() == 0) {
			this.setMainMessage("参数异常!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String arr[] = groupid.split(",");
		if (!"1".equals(arr[1])) {
			this.setMainMessage("你没有权限删除!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*
		 * if(SysOrgBS.whetherHasChile(arr[0])>0){ this.setMainMessage("包含下级机构，不能删除!");
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
		this.getPageElement("delete_groupid").setValue(arr[0]);// 要删除的机构id隐藏到页面
		List<String> listb0101 = HBUtil.getHBSession()
				.createSQLQuery("select b0101 from b01 where b0111='" + arr[0] + "'").list();
		this.getExecuteSG().addExecuteCode("againReq('" + listb0101 + "');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 删除事件
//	@PageEvent("deleteconfirm")
//	public int deleteconfirm() throws RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
//		String groupid = this.getPageElement("checkedgroupid").getValue();
//		if(SysOrgBS.selectCountBySubId(groupid).equals("0")){
//			
//			//-------------------机构回收用的代码------------------------------------
//			CurrentUser user = SysUtil.getCacheCurrentUser();
//			request.getSession().setAttribute("userid", user);
//			SysOrgBS.selectOut(groupid,user);
//			//------------------------------------------------------------------------
//			SysOrgBS.delB01(groupid);
//			this.getPageElement("checkedgroupid").setValue("");
//			this.getExecuteSG().addExecuteCode("window.reloadTree()");
//		}else{
//			dialog_set("deleteAll","确认删除选中的机构及其下属机构吗？");
//		}
//		return EventRtnType.NORMAL_SUCCESS;
//	}

	/**
	 * 删除机构和人员，并将数据保存到历史表
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
		// 删除机构
		String groupid = this.getPageElement("delete_groupid").getValue();
		if (groupid == null || groupid.length() == 0) {
			this.setMainMessage("参数异常!");
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
			 * //-------------------机构回收用的代码 request.getSession().setAttribute("userid",
			 * user); //------------加入回收站 SysOrgBS.selectOut(groupid,user);
			 * //-------------删除本级和下级 删除权限 删除机构校验的数据 SysOrgBS.delB01(groupid);
			 * 
			 * //-------维护字段，在该方法中加入需要运行的SQL boolean flag =
			 * SysOrgBS.maintenanceB01(groupid); if(flag){
			 * this.getPageElement("checkedgroupid").setValue("");
			 * this.getExecuteSG().addExecuteCode("window.reloadTree()"); //
			 * this.getExecuteSG().addExecuteCode("$('#hideDiv').hide();"); }else{ String
			 * unitname=HBUtil.getValueFromTab("b0101", "b01", "b0111='"+groupid+"' ");
			 * if(arr.length==1){//删除一个机构 this.setMainMessage(unitname+"部分数据未清理!");
			 * }else{//删除多个机构 if(i<arr.length-1){ String name_arr=""; for(int
			 * j=i+1;j<arr.length;j++){//循环未删除的机构 name_arr=name_arr+arr[j]+"/"; }
			 * this.setMainMessage(unitname+"部分数据未清理,"+name_arr.substring(0,
			 * name_arr.length()-1)+"未删除!"); }else{
			 * this.setMainMessage(unitname+"部分数据未清理!"); } }
			 * this.getExecuteSG().addExecuteCode("window.reloadTree()");
			 * //this.getExecuteSG().addExecuteCode("$('#hideDiv').hide();"); return
			 * EventRtnType.FAILD; }
			 */
		}

		// 删除在这个机构下面的人员（把人员status改成4）

		// 刷新列表
		this.setMainMessage("删除成功！");
		this.setNextEventName("orgInfoGrid.dogridquery");

		return EventRtnType.NORMAL_SUCCESS;
	}

	// 整建制转移
	@PageEvent("transferSysOrg.onclick")
	public int transferSysOrg() throws RadowException {
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('transferSysOrgWin','pages.sysorg.org.Zjzzy','隶属关系变更',766,500,'zjzzy','" + ctxPath + "');");
		// this.openWindow("transferSysOrgWin", "pages.sysorg.org.Zjzzy");
		this.request.getSession().setAttribute("transferType", "transferSysOrg");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("outFile.onclick")
	public int outFile() throws RadowException {
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('outFilegWin','pages.sysorg.org.OutFile','机构回收',520,400,'zjzzy','" + ctxPath + "');");
		// this.openWindow("transferSysOrgWin", "pages.sysorg.org.Zjzzy");
		this.request.getSession().setAttribute("transferType", "transferSysOrg");
		this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 批量转移人员
	@PageEvent("batchTransferPersonnel.onclick")
	public int batchTransferPersonnel() throws RadowException {
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('batchTransferPersonnelWin','pages.sysorg.org.ZjzzyPerson','人员批量转移',806,520,'plzy','"
						+ ctxPath + "');");
		// this.openWindow("batchTransferPersonnelWin", "pages.sysorg.org.Zjzzy");
		this.request.getSession().setAttribute("tag", "0");
		this.request.getSession().setAttribute("transferType", "batchTransferPersonnel");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 刷新本页面
	@PageEvent("reloadWin")
	public int reloadWin(String id) throws RadowException {
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 重置按钮
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

	// 编辑页面
	@PageEvent("openSelectOrgWinBtn.onclick")
	public int openUpdateOrgWin() throws RadowException, AppException {
		this.getExecuteSG().addExecuteCode("window.location.href='" + request.getContextPath()
				+ "/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrg'");
		return EventRtnType.NORMAL_SUCCESS;
	}

	private void dialog_set(String fnDelte, String strHint) {
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

	@PageEvent("saveBtn.onclick")
	public int LegalEntitySave() throws AppException, RadowException {
		String b0111 = this.getPageElement("b0111").getValue();// 机构编码
		if (b0111.equals("") || null == b0111) {
			this.setMainMessage("请选择机构");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (!SysRuleBS.havaRule(b0111)) {
			throw new RadowException("您无此权限!");
		}
		this.setNextEventName("validation.result");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 效验
	@PageEvent("validation.result")
	public int validation() throws RadowException {
		// String b0121 =this.getPageElement("b0121").getValue();//上级单位编码
		String b0114 = this.getPageElement("b0114").getValue();
		String b0111 = this.getPageElement("b0111").getValue();// 机构编码
		String radio = this.getPageElement("b0194").getValue();
		String b0101 = this.getPageElement("b0101").getValue();
		String b0104 = this.getPageElement("b0104").getValue();
		if (b0114 == null || b0114.trim().equals("")) {
			this.setMainMessage("请输入机构编码！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (b0101 == null || b0101.trim().equals("")) {
			this.setMainMessage("请输入机构名称！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (b0104 == null || b0104.trim().equals("")) {
			this.setMainMessage("请输入机构简称！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (radio == null || radio.trim().equals("")) {
			this.setMainMessage("请选择机构类型！");
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
			this.setMainMessage("请输入机构名称！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (b01.getB0104().equals("")) {
			this.setMainMessage("请输入简称！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (b0194Type.equals("1")) {
			if (b01.getB0114().trim().equals("")) {
				this.setMainMessage("请输入机构编码！");
				return EventRtnType.NORMAL_SUCCESS;
			}
//			if(b01.getB0117().equals("")){
//				this.setMainMessage("请输入机构所在政区！");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			if(b01.getB0127().equals("")){
//				this.setMainMessage("请输入机构级别！");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			if(b01.getB0131().equals("")){
//				this.setMainMessage("请输入机构类别！");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			if(b01.getB0124().equals("")){
//				this.setMainMessage("请输入隶属关系！");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			if (b01.getB0183() == null) {
				/*
				 * this.setMainMessage("请输入应配正职领导职数！"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0183((long) 0);
			}
			if (b01.getB0185() == null) {
				/*
				 * this.setMainMessage("请输入应配副职领导职数！"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0185((long) 0);
			}
			/*
			 * if(b01.getB0188()==null){ this.setMainMessage("请输入应配同级正职非领导职数！"); return
			 * EventRtnType.NORMAL_SUCCESS; } if(b01.getB0189()==null){
			 * this.setMainMessage("请输入应配同级副职非领导职数！"); return EventRtnType.NORMAL_SUCCESS; }
			 */
			if (b01.getB0227() == null) {
				/*
				 * this.setMainMessage("请输入行政编制数！"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0227((long) 0);
			}
			if (b01.getB0232() == null) {
				/*
				 * this.setMainMessage("请输入事业编制数(参公)！"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0232((long) 0);
			}
			if (b01.getB0233() == null) {
				/*
				 * this.setMainMessage("请输入事业编制数(其他)！"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0233((long) 0);
			}
			/*
			 * if (b01.getB0234() == null) {
			 * 
			 * this.setMainMessage("其他！"); return EventRtnType.NORMAL_SUCCESS;
			 * 
			 * b01.setB0234((long) 0); } if (b01.getB0235() == null) {
			 * 
			 * this.setMainMessage("请输入政法专项编制数！"); return EventRtnType.NORMAL_SUCCESS;
			 * 
			 * b01.setB0235((long) 0); } if (b01.getB0236() == null) {
			 * 
			 * this.setMainMessage("请输入工勤编制数！"); return EventRtnType.NORMAL_SUCCESS;
			 * 
			 * b01.setB0236((long) 0); }
			 */
			if (b01.getB0150() == null) {
				/*
				 * this.setMainMessage("请输入内设机构领导职数！"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0150((long) 0);
			}
			if (b01.getB0190() == null) {
				/*
				 * this.setMainMessage("请输入内设机构正职职数！"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0190((long) 0);
			}
			if (b01.getB0150() < b01.getB0190()) {
				this.setMainMessage("领导职数不应少于正职职数！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if (b01.getB0191a() == null) {
				/*
				 * this.setMainMessage("请输入内设机构副职职数！"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0191a((long) 0);
			}
			if (b01.getB0192() == null) {
				/*
				 * this.setMainMessage("请输入内设机构正职非领导职数！"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0192((long) 0);
			}
			if (b01.getB0193() == null) {
				/*
				 * this.setMainMessage("请输入内设机构副职非领导职数！"); return EventRtnType.NORMAL_SUCCESS;
				 */
				b01.setB0193((long) 0);
			}
		} else if (b0194Type.equals("2")) {
//			if(b01.getB0127().equals("")){
//				this.setMainMessage("请输入机构级别！");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			if(b01.getB0114().trim().equals("")){
//				this.setMainMessage("请输入内设机构编码！");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			// if(b01.getB0183()==null){
			/*
			 * this.setMainMessage("请输入应配正职领导职数！"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0183(null);
			// }
			// if(b01.getB0185()==null){
			/*
			 * this.setMainMessage("请输入应配副职领导职数！"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0185(null);
			// }
			/*
			 * if(b01.getB0188()==null){ this.setMainMessage("请输入应配同级正职非领导职数！"); return
			 * EventRtnType.NORMAL_SUCCESS; } if(b01.getB0189()==null){
			 * this.setMainMessage("请输入应配同级副职非领导职数！"); return EventRtnType.NORMAL_SUCCESS; }
			 */
			// if(b01.getB0227()==null){
			/*
			 * this.setMainMessage("请输入行政编制数！"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0227(null);
			// }
			// if(b01.getB0232()==null){
			/*
			 * this.setMainMessage("请输入事业编制数(参公)！"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0232(null);
			// }
			// if(b01.getB0233()==null){
			/*
			 * this.setMainMessage("请输入事业编制数(其他)！"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0233(null);
			// }
			// if(b01.getB0234()==null){
			/*
			 * this.setMainMessage("其他！"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0234(null);
			// }
			// if(b01.getB0235()==null){
			/*
			 * this.setMainMessage("请输入政法专项编制数！"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0235(null);
			// }
			// if(b01.getB0236()==null){
			/*
			 * this.setMainMessage("请输入工勤编制数！"); return EventRtnType.NORMAL_SUCCESS;
			 */
			b01.setB0236(null);
			// }
		} else {
			if (this.getPageElement("b0114").getValue().equals("") && b0111.length() <= 7) {
				this.setMainMessage("请输入机构编码！");
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
			// 如果不包含自己重名提示不允许重名
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
				this.setMainMessage("保存成功");
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

	// 登陆人权限
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

		if (field.size() > 0) {// 有扩展字段
			if (Info_Extends == null || Info_Extends.size() == 0) {// 新增
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
			} else {// 修改
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
	 * 对选中机构进行数据校验
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
				this.setMainMessage("没有设置机构权限");
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
				this.setMainMessage("无机构信息");
				return EventRtnType.FAILD;
			}
		}
		/*
		 * if(!SysRuleBS.havaRule(groupid)){ throw new RadowException("您无此权限!"); }
		 */
		String ctxPath = request.getContextPath();
		String paradata = "1@" + checkedgroupid + "@" + groupid;
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('dataVerifyWin','pages.sysorg.org.orgdataverify.OrgDataVerify','机构信息校验',700,599,'','"
						+ ctxPath + "',null,{ids:'" + paradata + "',maximizable:false,resizable:false});");
		// this.setRadow_parent_data("1@"+groupid);
		// this.openWindow("dataVerifyWin",
		// "pages.sysorg.org.orgdataverify.OrgDataVerify");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 照片检测
	 * 
	 * @return
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("imgVerify.onclick")
	public int imgVerify() throws RadowException {
		this.getExecuteSG().addExecuteCode("addTab('照片检测','','" + request.getContextPath()
				+ "/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.orgdataverify.OrgPersonImgVerify',false,false)");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 打开人员修改界面
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
	 * 下级机构领导职数配置
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("lowOrgLeaderBtn.onclick")
	public int lowOrgLeaderYuLan() throws RadowException {
		String num = "1";
		String groupid = getPageElement("checkedgroupid").getValue();
		if ((groupid.trim().equals("")) || (groupid == null)) {
			throw new RadowException("请选择机构!");
		}
		if (!SysRuleBS.havaRule(groupid).booleanValue()) {
			throw new RadowException("您无此权限!");
		}
		String param = num + "|" + groupid;
		getExecuteSG().addExecuteCode("lowOrgLeaderYuLan('" + param + "')");
		return EventRtnType.NORMAL_SUCCESS;
	}

//	@NoRequiredValidate
//	@PageEvent("lowOrgLeaderBtn.onclick")
	public int lowOrgLeader() throws RadowException, AppException {
		String fileName = "各单位领导职数配备表";
		String laststr = ".xls";
		String loadFile = ExpRar.getExeclPath() + "\\" + fileName + laststr;
		String expFile = ExpRar.expFile() + fileName + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")
				+ laststr;
		CommonQueryBS.systemOut(loadFile);
		CommonQueryBS.systemOut(expFile);
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if (groupid.trim().equals("") || groupid == null) {
			throw new RadowException("请选择机构!");
		}
		if (!SysRuleBS.havaRule(groupid)) {
			throw new RadowException("您无此权限!");
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
	 * 下级机构编制人员对比
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("lowOrgPeopleBtn.onclick")
	public int lowOrgPeopleYuLan() throws RadowException {
		String num = "2";
		String groupid = getPageElement("checkedgroupid").getValue();
		if ((groupid.trim().equals("")) || (groupid == null)) {
			throw new RadowException("请选择机构!");
		}
		if (!SysRuleBS.havaRule(groupid).booleanValue()) {
			throw new RadowException("您无此权限!");
		}
		String param = num + "|" + groupid;
		getExecuteSG().addExecuteCode("lowOrgLeaderYuLan('" + param + "')");
		return EventRtnType.NORMAL_SUCCESS;
	}

//	@NoRequiredValidate
//	@PageEvent("lowOrgPeopleBtn.onclick")
	public int lowOrgPeople() throws RadowException {
		String fileName = "各单位编制与人员配备表";
		String laststr = ".xls";
		String loadFile = ExpRar.getExeclPath() + "\\" + fileName + laststr;
		String expFile = ExpRar.expFile() + fileName + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")
				+ laststr;
		CommonQueryBS.systemOut(loadFile);
		CommonQueryBS.systemOut(expFile);
		String groupid = this.getPageElement("checkedgroupid").getValue();
		if (groupid.trim().equals("") || groupid == null) {
			throw new RadowException("请选择机构!");
		}
		if (!SysRuleBS.havaRule(groupid)) {
			throw new RadowException("您无此权限!");
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
	 * 多机构领导职数配置
	 * 
	 * @return
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("manyOrgLeaderBtn.onclick")
	public int manyOrgLeader() throws RadowException {
		this.setRadow_parent_data("leader");
//		String ctxPath = request.getContextPath();
//		this.getExecuteSG().addExecuteCode("$h.openWin('orgStatisticsWin','pages.sysorg.org.OrgStatistics','选择机构范围',397,490,'leader','"+ctxPath+"');");
		this.openWindow("orgStatisticsWin", "pages.sysorg.org.OrgStatistics");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 多机构编制人员对比
	 * 
	 * @return
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("manyOrgPeopleBtn.onclick")
	public int manyOrgPeople() throws RadowException {
		this.setRadow_parent_data("people");
//		String ctxPath = request.getContextPath();
//		this.getExecuteSG().addExecuteCode("$h.openWin('orgStatisticsWin','pages.sysorg.org.OrgStatistics','选择机构范围',397,490,'people','"+ctxPath+"');");
		this.openWindow("orgStatisticsWin", "pages.sysorg.org.OrgStatistics");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 列表双击事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("DbClick_grid")
	public int DbClick_grid(String type) throws RadowException {

		// 获取前台grid数据
		Grid grid = (Grid) this.getPageElement("orgInfoGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		if (list == null || list.size() == 0) {
			this.setMainMessage("请双击机构树查询!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String groupid = "";

		for (int i = 0; i < list.size(); i++) {
			groupid = groupid + (String) list.get(i).get("b0111") + ",";
		}

		groupid = groupid.substring(0, groupid.length() - 1);// 取出，号

		String pardata = "";
		String ctxPath = request.getContextPath();

		String arr[] = type.split(",");
		pardata = arr[1] + "," + groupid;
		request.getSession().setAttribute("unitidDbclAlter", pardata);//
		this.getExecuteSG().addExecuteCode(
				"$h.openWin('updateOrgWin','pages.sysorg.org.UpdateSysOrg','机构信息修改页面',1250,550,'','" + ctxPath + "');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 自动编码
	 * 
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("aotumaticCode_onclick")
	public int aotumaticCode() throws AppException, RadowException {
		String checkedgroupid = request.getParameter("checkedgroupid");// 前端选中的机构id;
		if (checkedgroupid != null && checkedgroupid.length() > 0) {// 存在被选中的机构，则仅仅补充被选中机构与其下级机构的编码为空机构分组与内设机构
			completionSubordinateCode(checkedgroupid);
		} else {// 为空，补全权限范围内的，所有编码为空的机构分组与内设机构
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
						+ " where c.b0111=t.b0111" + " and b0194 in ('2','3') "// 2内设机构，3机构分组
						+ " and (t.b0114 is null or length(t.b0114)=0) "
						+ " order by length(t.b0111) asc,t.sortid asc ";// 根据sortid的排序补充编码
			} else {
				sql = " select t.b0111,t.b0114,t.b0194,t.b0121 from " + " competence_userdept c,b01 t "
						+ " where c.b0111=t.b0111" + " and b0194 in ('2','3') "// 2内设机构，3机构分组
						+ " and (t.b0114 is null or length(t.b0114)=0) " + " and t.b0111 like '" + id + "%' "
						+ " order by length(t.b0111) asc,t.sortid asc";// 根据sortid的排序补充编码
			}
			List<HashMap<String, Object>> list = cq.getListBySQL(sql);
			for (int i = 0; i < list.size(); i++) {
				String b0111 = (String) list.get(i).get("b0111");// 机构id
				String b0114 = (String) list.get(i).get("b0114");// 机构编码
				String b0194 = (String) list.get(i).get("b0194");// 机构类型
				String b0121 = (String) list.get(i).get("b0121");// 上级机构id
				if ("2".equals(b0194)) {// 内设机构
					// 获取上级编码
					String b0114up = getB0114up(b0121, cq, b0114);
					// 生成不重复的3位编码
					String tempb0114 = getTempB0114(b0114up, b0121, cq);
					if ("".equals(b0114up) || "null".equals(b0114up) || b0114up == null) {
						continue;
					}
					if (tempb0114.length() >= 4) {
						flag_ns = true;// 内设机构过多标志
						continue;
					} else {
						b0114 = b0114up + "." + tempb0114;
						stmt1 = conn.createStatement();
						stmt1.execute("update b01 set b0114='" + b0114 + "' where b0111='" + b0111 + "' ");
						stmt1.close();
					}
				} else if ("3".equals(b0194)) {// 机构分组
					// 获取上级编码
					String b0114up = getB0114upFz(b0121, cq, b0194);
					// 生成不重复的3位编码
					String tempb0114 = getTempB0114Fz(b0114up, b0121, cq);
					if ("".equals(b0114up) || "null".equals(b0114up) || b0114up == null) {
						continue;
					}
					if (tempb0114.length() >= 4) {
						flag_fz = true;// 分组机构过多标志
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
			throw new RadowException("编码失败!" + e.getMessage());
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
				if ("-1".equals((String) list.get(0).get("b0111"))) {// 根机构
					return b0114;// 返回根机构下级编码
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
			throw new RadowException("获取3位随机编码失败!" + e.getMessage());
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
			throw new RadowException("获取3位随机编码失败!" + e.getMessage());
		}
		return tempb0114;
	}

	public String returnB0114Fz(String superStr, String str, String str1) throws RadowException {
		try {
			str = (Integer.parseInt(str) + 1) + "";
			if (str.length() == 1) {
				str = "0" + str;
			}
			// -1不存在
			if (str1.indexOf("$" + superStr + ".V" + str + "$") == -1) {
				// return str;
			} else {// 编码已经存在，则递归，重新生成
				str = returnB0114Fz(superStr, str, str1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("创建3位机构分组随机编码失败!" + e.getMessage());
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
				return "0000";// 同级别单位过多
			}
			// -1不存在
			if (str1.indexOf("$" + superStr + "." + str + "$") == -1) {
				// return str;
			} else {// 编码已经存在，则递归，重新生成
				str = returnB0114(superStr, str, str1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("创建3位内设机构随机编码失败!" + e.getMessage());
		}
		return str;
	}

	/**
	 * 补全机构代码
	 * 
	 * @param checkedgroupid
	 * @return
	 * @throws AppException
	 */
	public int completionSubordinateCode(String checkedgroupid) throws AppException {
		String checkedname = HBUtil.getValueFromTab("b0101", "b01", " b0111='" + checkedgroupid + "' ");
		if (checkedname == null) {
			this.setSelfDefResData("1@当前机构已经不存在，请刷新机构树重新执行！");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		Connection con=null;
//		Statement st= null;
		try {
//			con=HBUtil.getHBSession().connection();
//			//关闭事务自动提交
//			con.setAutoCommit(false);
//			st= con.createStatement();
			CommQuery cq = new CommQuery();
			String userid = SysUtil.getCacheCurrentUser().getId();
			String sql = "select count(*) num from b01 t,competence_userdept c " + " where t.b0111=c.b0111 "
					+ " and c.userid='" + userid + "' "// 用户id
					+ " and t.b0111 like '" + checkedgroupid + "%' "
					+ " and (length(trim(t.b0114))=0 or t.b0114 is null) "// 机构编码
					+ " and t.b0194 in ('2','3') "// 2 内设机构 3机构分组
			;

			List<HashMap<String, Object>> list = cq.getListBySQL(sql);
			// 判断，被选中的机构及其下级机构，是否存在编码为空的机构
			if (list == null || list.size() == 0 || "0".equals(list.get(0).get("num"))) {// 不存在，编码为空的机构
				if (checkedname == null) {
					checkedname = "";
				}
				this.setSelfDefResData("1@" + checkedname + "机构下不存在编码为空的内设机构与机构分组!");
				return EventRtnType.NORMAL_SUCCESS;
			} else {//
					// 判断，被选中的机构及其下级是否存在编码为空的法人机构
				sql = "select count(*) num from b01 t,competence_userdept c " + " where t.b0111=c.b0111 "
						+ " and c.userid='" + userid + "' "// 用户id
						+ " and t.b0111 like '" + checkedgroupid + "%' "
						+ " and (length(trim(t.b0114))=0 or t.b0114 is null) "// 机构编码
						+ " and t.b0194='1' "// 1法人单位
				;
				list = cq.getListBySQL(sql);
				if (list != null && Double.parseDouble((String) list.get(0).get("num")) > 0) {
					String title = "4@" + checkedname + "机构下存在编码为空的法人单位，请点击确认并修改为空的法人单位编码!";
					this.setSelfDefResData(title);
					return EventRtnType.NORMAL_SUCCESS;
				}
				/* 判断权限范围内是否存在，机构编码重复的机构（机构编码重复，会导致其下级自动编码也跟着重复） */
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
							"1@" + checkedname + "机构下存在重复的机构编码，请点击机构查重或信息校核（机构编码校核->编码重复校核），根据机构信息，手动修改重复的编码!");
					return EventRtnType.NORMAL_SUCCESS;
				}
				/* 判断机构编码是否合法 */
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
					String title = "3@" + checkedname + "机构下存在不为空且不合法的机构编码，请点击确认查看不为空且不合法的机构信息，手动修改机构编码!";
					this.setSelfDefResData(title);
					return EventRtnType.NORMAL_SUCCESS;
				}
				// 开始编码
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
//				String b0111_super="";//获取被选中的机构及其下级机构中,编码为空最高级别机构的上级机构id
//				if(list_only!=null&&list_only.size()>0){
//					b0111_super=(String)list_only.get(0).get("b0121");//获取用户权限范围内,编码为空最高级别机构的上级机构id
//				}else{
//					this.setSelfDefResData("1@数据异常!");
//					return EventRtnType.NORMAL_SUCCESS;
//				}
//				String b0114_super="";//获取用户权限范围内,编码为空最高级别机构的上级机构编码
//				List<HashMap<String, Object>> list_super=cq.getListBySQL("select t.b0114 from b01 t where t.b0111='"+b0111_super+"' ");
//				if(list_super==null||list_super.size()<1){
//					this.setSelfDefResData("1@数据异常!");
//					return EventRtnType.NORMAL_SUCCESS;
//				}else{
//					b0114_super=(String)list_super.get(0).get("b0114");//获取用户权限范围内,编码为空最高级别机构的上级机构编码
//				}
//				
//				String cueUserid=SysUtil.getCacheCurrentUser().getId();
//				//获取用户权限范围内的最低级别单位的id
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
//					nodeh=(String)listh.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
//				}else{
//					this.setSelfDefResData("1@@@"+"程序出错!");
//				}
//				
//				sql="select t.b0111,t.b0114,t.b0194 from competence_userdept c,b01 t "
//						+ " where  t.b0111=c.b0111 "
//						+ " and c.userid='"+userid+"' "
//						+ " and t.b0111 like '"+checkedgroupid+"%' "
//						+ " and t.b0121='"+b0111_super+"' "
//						+ " order by length(t.b0111),t.SORTID asc ";
//				List<HashMap<String, Object>> list_all =cq.getListBySQL(sql);//根据上级id获取下级所有下级机构
//				
//				//将机构编码不为空的机构编码,拼接成字符串
//				String str1=returnStr(list_all);
//				//初始化第一个编码
//				String b0114_ns="0";
//				String b0114_fz="0";
//				for(int i=0;i<list_all.size();i++){//遍历所有下级机构
//					if(list_all.get(i).get("b0114")==null
//							||((String)list_all.get(i).get("b0114")).trim().length()==0
//							){//编码为空,且不为法人单位
//						//自动编码本级别机构
//						if("2".equals((String)list_all.get(i).get("b0194"))){//内设
//							b0114_ns=returnNum(b0114_super,b0114_ns,str1);//生成内设机构编码
//							
//							if(b0114_ns.length()>=4){
//								flag_ns=true;//内设机构过多标志
//								continue;
//							}
//							if(b0114_super==null||b0114_super.length()==0){//考虑到一级机构是机构分组且编码为空，隐藏根节点编码也为空时的情况
//								st.addBatch(" update b01 set b0114='"+b0114_ns+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}else{
//								st.addBatch(" update b01 set b0114='"+b0114_super+"."+b0114_ns+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}
//							st_num=st_num+1;
//							if(st_num%300==0){
//								st.executeBatch();
//								st.close();//补全编码时间很长，st长时间不是释放，会报报错，连接不能释放
//								st=con.createStatement();
//							}
//							//自动编码下级机构
//							if(b0114_super==null||b0114_super.length()==0){
//								aotumaticCode1((String)list_all.get(i).get("b0111"),b0114_ns,st,nodeh);
//							}else{
//								aotumaticCode1((String)list_all.get(i).get("b0111"),b0114_super+"."+b0114_ns,st,nodeh);
//							}
//						}else if("3".equals((String)list_all.get(i).get("b0194"))){//分组
//							b0114_fz=returnNumFz(b0114_super,b0114_fz,str1);//生成分组机构编码
//							
//							if(b0114_fz.length()>=3){
//								flag_fz=true;//分组机构过多标志
//								continue;
//							}
//							if(b0114_super==null||b0114_super.length()==0){//成立则上级机构编码为空 (考虑到一级机构是机构分组且编码为空，隐藏根节点编码也为空时的情况)
//								st.addBatch(" update b01 set b0114='V"+b0114_fz+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}else{
//								st.addBatch(" update b01 set b0114='"+b0114_super+".V"+b0114_fz+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}
//							st_num=st_num+1;
//							if(st_num%300==0){
//								st.executeBatch();
//								st.close();//补全编码时间很长，st长时间不是释放，会报报错，连接不能释放
//								st=con.createStatement();
//							}
//							//自动编码下级机构
//							if(b0114_super==null||b0114_super.length()==0){
//								aotumaticCode1((String)list_all.get(i).get("b0111"),"V"+b0114_fz,st,nodeh);
//							}else{
//								aotumaticCode1((String)list_all.get(i).get("b0111"),b0114_super+".V"+b0114_fz,st,nodeh);
//							}
//						}
//					}else{
//						//自动编码下级机构
//						aotumaticCode1((String)list_all.get(i).get("b0111"),((String)list_all.get(i).get("b0114")).trim(),st,nodeh);
//					}
//				}
//				st.executeBatch();
//				con.commit();
//				st.close();
//				con.close();
				if (flag_fz == true && flag_ns == true) {
					this.setSelfDefResData("2@下级直属内设机构与机构分组过多，仅自动编码部分机构，其余的请手动编码!");
				} else if (flag_fz == true) {
					this.setSelfDefResData("2@下级直属机构分组过多，仅自动编码部分机构，其余的请手动编码!");
				} else if (flag_ns == true) {
					this.setSelfDefResData("2@下级直属内设机构过多，仅自动编码部分机构，其余的请手动编码!");
				} else if (flag_fz == false && flag_ns == false) {
					this.setSelfDefResData("2@编码成功!");
				} else {
					this.setSelfDefResData("2@编码成功!");
				}
				st_num = 0;// 恢复初始化
				flag_ns = false;// 恢复初始化
				flag_fz = false;// 恢复初始化
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
			this.setSelfDefResData("1@自动编码失败!");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 补全机构编码
	 * 
	 * @return
	 */
	public int completionAllCode() {
//		Connection con=null;
//		Statement st= null;
		try {
//			con=HBUtil.getHBSession().connection();
//			//关闭事务自动提交
//			con.setAutoCommit(false);
//			st= con.createStatement();
			CommQuery cq = new CommQuery();
			String userid = SysUtil.getCacheCurrentUser().getId();
			String sql = "select count(*) num from b01 t,competence_userdept c " + " where t.b0111=c.b0111 "
					+ " and c.userid='" + userid + "' "// 用户id
					+ " and (length(trim(t.b0114))=0 or t.b0114 is null) "// 机构编码
					+ " and t.b0194 in ('2','3') "// 2 内设机构 3机构分组
			;

			List<HashMap<String, Object>> list = cq.getListBySQL(sql);
			// 判断，用户权限范围内的内设机构与机构分组，是否存在编码为空的机构
			if (list == null || list.size() == 0 || "0".equals(list.get(0).get("num"))) {// 不存在，编码为空的机构
				this.setSelfDefResData("1@不存在编码为空的内设机构与机构分组!");
				return EventRtnType.NORMAL_SUCCESS;
			} else {// 存在，获取权限范围内，机构级别最高（机构id最短）的机构信息
				/* 判断是否存在，机构编码重复的机构（机构编码重复，会导致其下级自动编码也跟着重复） */
				sql = "select count(*) num from b01 t,competence_userdept c " + " where t.b0111=c.b0111 "
						+ " and c.userid='" + userid + "' "// 用户id
						+ " and (length(trim(t.b0114))=0 or t.b0114 is null) "// 机构编码
						+ " and t.b0194='1' "// 1法人单位
				;
				list = cq.getListBySQL(sql);
				if (list != null && Double.parseDouble((String) list.get(0).get("num")) > 0) {
					String title = "4@存在编码为空的法人单位，请点击确认并修改为空的法人单位编码!";
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
					this.setSelfDefResData("1@存在重复的机构编码，请点击机构查重或信息校核（机构编码校核->编码重复校核），根据机构信息，手动修改重复的编码!");
					return EventRtnType.NORMAL_SUCCESS;
				}
				/* 判断机构编码是否合法 */
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
					String title = "3@存在不为空且不合法的机构编码，请点击确认查看不为空且不合法的机构信息，手动修改机构编码!";
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
//				String b0111_super="";//获取用户权限范围内,编码为空最高级别机构的上级机构id
//				if(list_only!=null&&list_only.size()>0){
//					b0111_super=(String)list_only.get(0).get("b0121");//获取用户权限范围内,编码为空最高级别机构的上级机构id
//				}else{
//					this.setSelfDefResData("1@数据异常!");
//					return EventRtnType.NORMAL_SUCCESS;
//				}
//				
//				String cueUserid=SysUtil.getCacheCurrentUser().getId();
//				//获取用户权限范围内的最低级别单位的id
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
//					nodeh=(String)listh.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
//				}else{
//					this.setSelfDefResData("1@@@"+"程序出错!");
//				}
//				
//				String b0114_super="";//获取用户权限范围内,编码为空最高级别机构的上级机构编码
//				List<HashMap<String, Object>> list_super=cq.getListBySQL("select t.b0114 from b01 t where t.b0111='"+b0111_super+"' ");
//				if(list_super==null||list_super.size()<1){
//					this.setSelfDefResData("1@数据异常!");
//					return EventRtnType.NORMAL_SUCCESS;
//				}else{
//					b0114_super=(String)list_super.get(0).get("b0114");//获取用户权限范围内,编码为空最高级别机构的上级机构编码
//				}
//				
//				sql="select t.b0111,t.b0114,t.b0194 from competence_userdept c,b01 t "
//						+ " where  t.b0111=c.b0111 "
//						+ " and c.userid='"+userid+"' "
//						+ " and t.b0121='"+b0111_super+"' "
//						+ " order by length(t.b0111),t.SORTID asc ";
//				List<HashMap<String, Object>> list_all =cq.getListBySQL(sql);//根据上级id获取下级所有下级机构
//				
//				//将机构编码不为空的机构编码,拼接成字符串
//				String str1=returnStr(list_all);
//				//初始化第一个编码
//				String b0114_ns="0";
//				String b0114_fz="0";
//				for(int i=0;i<list_all.size();i++){//遍历所有下级机构
//					if(list_all.get(i).get("b0114")==null
//							||((String)list_all.get(i).get("b0114")).trim().length()==0
//							){//编码为空,且不为法人单位
//						//自动编码本级别机构
//						if("2".equals((String)list_all.get(i).get("b0194"))){//内设
//							b0114_ns=returnNum(b0114_super,b0114_ns,str1);//生成内设机构编码
//							
//							if(b0114_ns.length()>=4){
//								flag_ns=true;//内设机构过多标志
//								continue;
//							}
//							if(b0114_super==null||b0114_super.length()==0){//成立则上级机构编码为空 (考虑到一级机构是机构分组且编码为空，隐藏根节点编码也为空时的情况)
//								st.addBatch(" update b01 set b0114='"+b0114_ns+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}else{
//								st.addBatch(" update b01 set b0114='"+b0114_super+"."+b0114_ns+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}
//							st_num=st_num+1;
//							if(st_num%300==0){
//								st.executeBatch();
//								st.close();//补全编码时间很长，st长时间不是释放，会报报错，连接不能释放
//								st=con.createStatement();
//							}
//							//自动编码下级机构
//							if(b0114_super==null||b0114_super.length()==0){
//								aotumaticCode1((String)list_all.get(i).get("b0111"),b0114_ns,st,nodeh);
//							}else{
//								aotumaticCode1((String)list_all.get(i).get("b0111"),b0114_super+"."+b0114_ns,st,nodeh);
//							}
//						}else if("3".equals((String)list_all.get(i).get("b0194"))){//分组
//							b0114_fz=returnNumFz(b0114_super,b0114_fz,str1);//生成分组机构编码
//							
//							if(b0114_fz.length()>=3){
//								flag_fz=true;//分组机构过多标志
//								continue;
//							}
//							if(b0114_super==null||b0114_super.length()==0){//成立则上级机构编码为空 (考虑到一级机构是机构分组且编码为空，隐藏根节点编码也为空时的情况)
//								st.addBatch(" update b01 set b0114='V"+b0114_fz+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}else{
//								st.addBatch(" update b01 set b0114='"+b0114_super+".V"+b0114_fz+"' where b0111= '"+list_all.get(i).get("b0111")+"' ");
//							}
//							st_num=st_num+1;
//							if(st_num%300==0){
//								st.executeBatch();
//								st.close();//补全编码时间很长，st长时间不是释放，会报报错，连接不能释放
//								st=con.createStatement();
//							}
//							//自动编码下级机构
//							if(b0114_super==null||b0114_super.length()==0){
//								aotumaticCode1((String)list_all.get(i).get("b0111"),"V"+b0114_fz,st,nodeh);
//							}else{
//								aotumaticCode1((String)list_all.get(i).get("b0111"),b0114_super+".V"+b0114_fz,st,nodeh);
//							}
//						}
//					}else{
//						//自动编码下级机构
//						aotumaticCode1((String)list_all.get(i).get("b0111"),((String)list_all.get(i).get("b0114")).trim(),st,nodeh);
//					}
//				}
//				st.executeBatch();
//				con.commit();
//				st.close();
//				con.close();
				if (flag_fz == true && flag_ns == true) {
					this.setSelfDefResData("2@下级直属内设机构与机构分组过多，仅自动编码部分机构，其余的请手动编码!");
				} else if (flag_fz == true) {
					this.setSelfDefResData("2@下级直属机构分组过多，仅自动编码部分机构，其余的请手动编码!");
				} else if (flag_ns == true) {
					this.setSelfDefResData("2@下级直属内设机构过多，仅自动编码部分机构，其余的请手动编码!");
				} else if (flag_fz == false && flag_ns == false) {
					this.setSelfDefResData("2@编码成功!");
				} else {
					this.setSelfDefResData("2@编码成功!");
				}
				st_num = 0;// 恢复初始化
				flag_ns = false;// 恢复初始化
				flag_fz = false;// 恢复初始化
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
			this.setSelfDefResData("1@自动编码失败!");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 根据传来的数字字符串，实现自增 superStr 上级机构编码 str //10进制 自增字符串 str1 本级别 中，分组机构，机构编码不为空的编码拼接
	 * 
	 * @return
	 */
	public String returnNumFz(String superStr, String str, String str1) {
		str = (Integer.parseInt(str) + 1) + "";
		if (str.length() == 1) {
			str = "0" + str;
		}
		// -1不存在
		if (str1.indexOf("$" + superStr + ".V" + str + "$") == -1) {
			// return str;
		} else {// 编码已经存在，则递归，重新生成
			str = returnNum(superStr, str, str1);
		}
		return str;
	}

	/**
	 * 根据传来的数字字符串，实现自增 superStr 上级机构编码 str //10进制 自增字符串 str1 本级别 中，内设机构，机构编码不为空的编码拼接
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
		// -1不存在
		if (str1.indexOf("$" + superStr + "." + str + "$") == -1) {
			// return str;
		} else {// 编码已经存在，则递归，重新生成
			str = returnNum(superStr, str, str1);
		}
		return str;
	}

	/**
	 * 返回list拼接成的字符串
	 * 
	 * @param list_notnull
	 * @return
	 */
	public String returnStr(List<HashMap<String, Object>> list) {
		String str = "";
		for (int i = 0; i < list.size(); i++) {// 遍历 list
			if (list.get(i).get("b0114") != null && ((String) list.get(i).get("b0114")).trim().length() > 0) {// 编码不为空
				str = str + list.get(i).get("b0114") + "$";
			}
		}
		str = "$" + str;
		return str;
	}

	/**
	 * 自动编码 b0111 上级机构的id b0114 上级机构的编码
	 * 
	 * @throws AppException
	 * @throws SQLException
	 */
	public int aotumaticCode1(String b0111, String b0114, Statement st, String nodeh)
			throws AppException, SQLException {
		try {
			if (b0111 == null || b0111.length() >= nodeh.length()) {// 成立则，此机构id没有下级
				return EventRtnType.NORMAL_SUCCESS;
			}
			String userid = SysUtil.getCacheCurrentUser().getId();

			String sql = "select t.b0111,t.b0114,t.b0194 from competence_userdept c,b01 t " + " where  t.b0111=c.b0111 "
					+ " and c.userid='" + userid + "' " + " and t.b0121='" + b0111 + "' "
					+ " order by length(t.b0111),t.SORTID asc ";
			CommQuery cq = new CommQuery();
			List<HashMap<String, Object>> list_all = cq.getListBySQL(sql);// 根据上级id获取所有下级机构机构

			// 将机构编码不为空的编码,拼接成字符串
			String str1 = returnStr(list_all);
			// 初始化第一个编码
			// 初始化第一个编码
			String b0114_ns = "0";
			String b0114_fz = "0";

			for (int i = 0; i < list_all.size(); i++) {

				if (list_all.get(i).get("b0114") == null
						|| ((String) list_all.get(i).get("b0114")).trim().length() == 0) {// 编码为空,且不为法人单位
					if ("2".equals((String) list_all.get(i).get("b0194"))) {// 内设
						// 自动编码本级别机构
						b0114_ns = returnNum(b0114, b0114_ns, str1);// 生成编码
						if (b0114_ns.length() >= 4) {
							flag_ns = true;// 机构过多标志
							continue;
						}
						if (b0114 == null || b0114.length() == 0 || "null".equals(b0114)) {// 成立则上级机构编码为空
																							// (考虑到一级机构是机构分组且编码为空，隐藏根节点编码也为空时的情况)
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
						// 自动编码下级机构
						aotumaticCode1((String) list_all.get(i).get("b0111"), b0114 + "." + b0114_ns, st, nodeh);
					} else if ("3".equals((String) list_all.get(i).get("b0194"))) {// 分组
						// 自动编码本级别机构
						b0114_fz = returnNumFz(b0114, b0114_fz, str1);// 生成编码
						if (b0114_fz.length() >= 4) {
							flag_fz = true;// 机构过多标志
							continue;
						}
						if (b0114 == null || b0114.length() == 0 || "null".equals(b0114)) {// 成立则上级机构编码为空
																							// (考虑到一级机构是机构分组且编码为空，隐藏根节点编码也为空时的情况)
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
						// 自动编码下级机构
						aotumaticCode1((String) list_all.get(i).get("b0111"), b0114 + ".V" + b0114_fz, st, nodeh);
					}
				} else {
					// 自动编码下级机构
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
			throw new AppException(e.getMessage() + "---------自动编码失败！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("recoverBtn.onclick")
	public int recover() throws RadowException, AppException {
		this.getExecuteSG().addExecuteCode("$h.openWin('recover','pages.sysorg.org.recover','机构回收站页面',860,510,'','"
				+ request.getContextPath() + "');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 复制数据
	public void record(String organization, String filename, String sql, String tablename, String userid)
			throws Exception {
		CommonQueryBS.systemOut("备份数据到数据库（开始）");
		HBSession sess = HBUtil.getHBSession();
		List<String> a0000s = sess.createSQLQuery(sql).list();
		StringBuilder sba01 = new StringBuilder();
		StringBuilder sba02 = new StringBuilder();
		for (String a0000 : a0000s) {
			List<A02> listA02 = sess.createSQLQuery("select * from a02 where a0000='" + a0000 + "'")
					.addEntity(A02.class).list();
			if (listA02.size() == 1) {// 一个人任多机构的情况，a01不用备份
				sba01.append("'" + a0000 + "'" + ",");// 直接删除就行
			} else {
				StringBuilder sba0200s = new StringBuilder();
				int falg = 0;
				for (A02 a02 : listA02) {
					if (a02.getA0201b() == null || a02.getA0201b().length() < organization.length()) {
						continue;
					}
					String a0201b = a02.getA0201b().substring(0, organization.length());
					if (a0201b.equals(organization)) {// 删除a02中信息
						falg++;
						sba0200s.append("'" + a02.getA0200() + "'" + ",");
					}
				}
				if (falg == listA02.size()) {// 这个人的任职机构全部在这个机构下 就直接删人就行
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

		CommonQueryBS.systemOut("备份数据到数据库（结束）");

	}

	// 删除用户
	public void deleteUser(String groupid) throws SQLException {
		CommonQueryBS.systemOut("删除该机构下的用户");
		HBSession sess = HBUtil.getHBSession();
		sess.createSQLQuery("UPDATE SMT_USER SET USEFUL = '0' WHERE OTHERINFO like '" + groupid + "%'").executeUpdate();
		CommonQueryBS.systemOut("删除用户完成");

	}

	// 删除人员
	public void deletePerson(String sql, String groupid) throws SQLException {
		CommonQueryBS.systemOut("删除人员");
		HBSession sess = HBUtil.getHBSession();
		List<String> a01s = sess.createSQLQuery(sql).list();
		if (a01s.size() == 0 || a01s == null) {
			CommonQueryBS.systemOut("删除成功");
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (String a0000 : a01s) {
			List<A02> listA02 = sess.createSQLQuery("select * from a02 where a0000='" + a0000 + "'")
					.addEntity(A02.class).list();
			if (listA02.size() == 1) {
				sb.append("'" + a0000 + "'" + ",");
			} else {// 一个人任多机构的情况
				StringBuilder sba0200s = new StringBuilder();
				int falg = 0;
				for (A02 a02 : listA02) {
					if (a02.getA0201b().length() < groupid.length()) {
						continue;
					}
					String a0201b = a02.getA0201b().substring(0, groupid.length());
					if (a0201b.equals(groupid)) {// 删除
						falg++;
						String a0200 = a02.getA0200();
						sba0200s.append("'" + a0200 + "',");
					}
				}
				if (listA02.size() == falg) {// 表明这个人全部在这个机构下任职，就全部删除这个人的所有信息
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
		CommonQueryBS.systemOut("删除人员成功");
	}

	public static void main(String[] args) throws Exception {
		String s = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
		System.out.println(s.length() + "==================" + s);
	}

	/**
	 * 职位信息 不允许多选，多个给予提醒，不能新建
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("gwConfBtn.onclick")
	public int gwConfBtn() throws RadowException, Exception {
		// 获取前台grid数据
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
			this.setMainMessage("仅能选择一个机构!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (num < 1) {
			this.setMainMessage("请选择一个机构!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		System.out.println("+++++++++++++=" + groupid);
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('gwConfWin','pages.sysorg.org.GwConf','职位管理配置页面',760,460,'"
				+ groupid + "','" + ctxPath + "');");
		// this.request.getSession().setAttribute("tag", "0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 职位信息 不允许多选，多个给予提醒，不能新建
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
				"$h.openWin('orgimpWin','pages.sysorg.org.OrgRefSet','设置',480,520,'" + id + "','" + ctxPath + "');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/* 同步oa机构 */
	@SuppressWarnings("static-access")
	@PageEvent("synchroOA")
	public void synchroOA() throws ParseException, AppException {

		try {
			caClient2 ca = new caClient2();
			HashMap<String, String>[] user1 = ca.getAllDept(); // 从接口获取数据
			for (HashMap<String, String> map : user1) {
				// System.out.println("result is " + map);
				ca.addDept(map); // 循环插入数据
			}
			this.setMainMessage("同步成功");
		} catch (Exception e) {
			// TODO: handle exception
			this.setMainMessage("同步失败：" + e);
		}
	}

	/* 同步oa用户數據 */
	@SuppressWarnings("static-access")
	@PageEvent("synchroOAUser")
	public void synchroOAUser() throws ParseException {
		try {
			String OWNER = PrivilegeManager.getInstance().getCueLoginUser().getId(); // 获取当前用户id 用于插入创建者的值
			caClient2 ca = new caClient2();
			HashMap<String, String>[] users = ca.getAllUser(); // 从接口获取数据
			for (HashMap<String, String> map : users) {
				// System.out.println("result is " + map);
				ca.addUser(map, OWNER);// 循环插入数据
			}
			this.setMainMessage("同步成功");
		} catch (Exception e) {
			// TODO: handle exception
			this.setMainMessage("同步失败：" + e);
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
		// 获取前台grid数据
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
			this.setMainMessage("仅能选择一个机构!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (num < 1) {
			this.setMainMessage("请选择一个机构!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//'001.001.004.003'
		if(groupid.length()<15){
			this.setMainMessage("请选下级单位!");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			groupid = groupid.substring(0,15);
		}
		
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode(" $h.openWin('zzsAdd', 'pages.sysorg.zzs.ZZS', '组织史 ', 1200, 850, null, '"+ctxPath+"', null, { maximizable: false,resizable: false,closeAction: 'close',jgId:'"+groupid+"'})" );

		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	

}
