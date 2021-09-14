package com.insigma.siis.local.business.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.commform.hibernate.HList;
import com.insigma.odin.framework.commform.local.sys.LoginManager;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.odin.framework.util.commform.ObjectUtil;
import com.insigma.odin.framework.util.commform.StringUtil;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.sysmanager.resource.resourcePageModel;
import com.lbs.cp.sysmanager.entity.SysUser;
import com.lbs.cp.sysmanager.user.bp.UserBSImp;

public class MenuBuilder {

	protected ArrayList treeNodeList;

	protected static final String NODETYPE_LEAF = "0";

	protected static final String NODETYPE_NODE = "1";

	protected int seq = 0;

	// protected String contextPath;

	public MenuBuilder() {
		treeNodeList = new ArrayList();
	}

	public String getMenu(HttpServletRequest request) {
		String userid = SysManagerUtils.getUserId();
		UserBSImp userbsimp = new UserBSImp();
		//SysUser sysuser = null;
		//sysuser = userbsimp.findByUsername(username);
	
			List list = getMenuList(userid);
			if (list != null) {
				return buildMenu(list);
			}
		
		return "";
	}
	
	public List<TreeNodeC> getMenu2(HttpServletRequest request) {
		String userid = SysManagerUtils.getUserId();
		UserBSImp userbsimp = new UserBSImp();
		//SysUser sysuser = null;
		//sysuser = userbsimp.findByUsername(username);
	
			List list = getMenuList(userid);
			if (list != null) {
				return buildTreeList(list);
			}
		
		return null;
	}
	

	public String getMenuAll() {
		List list = getMenuListAll();
		if (list != null) {
			return buildMenuAll(list);
		}
		return "";
	}

	/**
	 * 用户组
	 */
	public String getMenuGroupAll() {
		List list = getMenuGroupListAll();
		if (list != null) {
			return buildMenuGroupAll(list);
		}
		return "";
	}

	/**
	 * 用户组
	 * 
	 * @throws AppException
	 */
	public String getMenuGroupCurrent() throws AppException {
		List list = getMenuGroupListCurrent();
		if (list != null) {
			return buildMenuGroupCurrent(list);
		}
		return "";
	}

	public List getMenuList(HttpServletRequest request) {
		String username = (String) request.getParameter(GlobalNames.USERNAME);
		UserBSImp userbsimp = new UserBSImp();
		SysUser sysuser = null;
		sysuser = userbsimp.findByUsername(username);
		if (sysuser != null) {
			// List list =
			// userbsimp.findFunctionListByUserid(sysuser.getUserid());
			List list = getMenuList(sysuser.getUserid());
			if (list != null) {
				return list;
			}
		}
		return null;
	}

	public List getMenuList(String userid) {
		FunctionVO func;
		List list = new ArrayList();
		HList hl = null;
		try {
			UserVO userVO = SysUtil.getCacheCurrentUser().getUserVO();
			PrivilegeManager pri = PrivilegeManager.getInstance();
			String sectype = "";
			try {
				sectype = HBUtil.getValueFromTab("sectype", "smt_user", "userid='"+userVO.getId()+"'");
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (pri.getIPermission().isSuperManager(userVO) || pri.getIPermission().isSysPermission(userVO, "RESOURCE_ALL")) {
				hl = new HList().setSql("select f.* from Smt_Function f,Smt_Resource r where f.resourceid = r.resourceid and r.status='1' order by f.parent,f.orderno").retrieve();
			} else {
				if("40288103556cc97701556d629135000f".equals(userVO.getId())) {
					hl = new HList().setSql("select f.* from Smt_Function f,Smt_Resource r where f.resourceid = r.resourceid and r.status='1' order by f.parent,f.orderno").retrieve();
				}else {
					hl = new HList().setSql("select distinct f.* from Smt_Function f,Smt_Resource r,Smt_Act t,Smt_Acl l,Smt_Usergroupref ug "
							+ " where  f.resourceid = r.resourceid and r.status='1' "
							+ " and l.resourceid = r.resourceid and t.roleid = l.roleid "
							//密级标识
				            +" and (f.param3 is null or f.param3 like '"+sectype+"'||'%') "
				            
							+ " and (t.objectid=:userid  or (ug.userid=:userid and t.objectid = ug.groupid)) order by f.parent,f.orderno").setSqlParam("userid", userid).retrieve();
				}
			}
			for (int i = 0; i < hl.getRowCount(); i++) {
				func = new FunctionVO();
				func.setDescription(hl.getString("description", i));
				func.setFunctionid(hl.getString("functionid", i));
				func.setLocation(hl.getString("location", i));
				func.setOrderno(new Long(hl.getInt("orderno", i).shortValue()));
				func.setOwner(hl.getString("owner", i));
				func.setParent(hl.getString("parent", i));
				// func.setTitle(hl.getString("title", i));
				String code = hl.getString("functioncode", i);
				if (code.length() == 5) {
					code = code.substring(code.length() - 2);
				} else if (code.length() == 9) {
					if (ObjectUtil.equals(code, "000000000")) {
						code = "";
					} else {
						code = code.substring(3, 5) + "-" + code.substring(5);
					}
				} else if (code.length() == 3) { // 3位的子系统也不显示编码（南20130820修改）
					code = "";
				}
				String title = getTitle(code, hl.getString("title", i));
				func.setDescription(code);
				func.setTitle(title);
				
				if("01".equals(resourcePageModel.mmbs.get(hl.getString("functionid", i)))){
					func.setParam4(title+"（<font color=red>内部</font>）");
				}else if("0".equals(resourcePageModel.mmbs.get(hl.getString("functionid", i)))){
					func.setParam4(title+"（<font color=red>秘密</font>）");
				}else if("011".equals(resourcePageModel.mmbs.get(hl.getString("functionid", i)))){
					func.setParam4(title+"（<font color=red>非密</font>）");
				}else{
					func.setParam4(title+"（<font color=red>非密</font>）");
				}
				
				
				func.setType(hl.getString("type", i));
				func.setFuncpic(hl.getString("funcpic", i));
				func.setUptype(hl.getString("uptype",i));
				list.add(func);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		String xx = "";
		for(int i=0;i<((ArrayList) list).size();i++){
			FunctionVO functionVO = (FunctionVO) list.get(i);
			if(functionVO.getTitle().equals("干部信息数据导入")){
				System.out.println(i);
				xx = String.valueOf(i);
				 break;
			}else{
				xx = "没有";
				System.out.println("没有");
			}
		}
		return list;
	}

	private String getTitle(String code,String title){
		String  isShowFuncCode = GlobalNames.sysConfig.get("IS_SHOW_FUNCCODE");
		if(isShowFuncCode==null || "1".equals(isShowFuncCode.trim())){
			title = code + title;
		}
		return title;
	}
	
	public List getMenuListAll() {
		FunctionVO func;
		List list = new ArrayList();
		try {
			HList hl = new HList().setSql("select * from smt_function order by parent,orderno,functionid").retrieve();
			for (int i = 0; i < hl.getRowCount(); i++) {
				func = new FunctionVO();
				func.setDescription(hl.getString("description", i));
				func.setFunctionid(hl.getString("functionid", i));
				func.setLocation(hl.getString("location", i));
				func.setOrderno(new Long(hl.getInt("orderno", i).shortValue()));
				func.setOwner(hl.getString("owner", i));
				func.setParent(hl.getString("parent", i));
				String code = hl.getString("functioncode", i);
				if (code.length() == 4) {
					code = code.substring(code.length() - 2);
				} else if (code.length() == 8) {
					if (ObjectUtil.equals(code, "00000000")) {
						code = "";
					} else {
						code = code.substring(2, 4) + "-" + code.substring(4);
					}
				}
				String title = getTitle(code, hl.getString("title", i));
				func.setDescription(code);
				func.setTitle(title);
				func.setType(NODETYPE_NODE);// hl.getString("type",i));
				list.add(func);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List getMenuGroupListAll() {
		SmtGroup group;
		List list = new ArrayList();
		try {
			HList hl = new HList().setSql("select * from smt_group  order by parentid,groupid").retrieve();
			for (int i = 0; i < hl.getRowCount(); i++) {
				group = new SmtGroup();
				group.setDesc(hl.getString("description", i));
				group.setId(hl.getString("groupid", i));
				group.setParentid(hl.getString("parentid", i));
				group.setDistrictcode(hl.getString("districtcode", i));
				group.setName(hl.getString("name", i));
				group.setShortname(hl.getString("shortname", i));
				group.setType(NODETYPE_NODE);// hl.getString("type",i));
				list.add(group);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List getMenuGroupListCurrent() {
		SmtGroup group;
		List list = new ArrayList();
		try {
			HList hl = new HList().setSql("select * from smt_group t  start with t.districtcode in(select a.districtcode from smt_group a,smt_usergroupref b where a.groupid=b.groupid and b.isleader='1' and status='1' and b.userid='" + LoginManager.getCurrentUserId() + "') connect by prior t.districtcode = t.parentid").retrieve();
			for (int i = 0; i < hl.getRowCount(); i++) {
				group = new SmtGroup();
				group.setDesc(hl.getString("description", i));
				group.setId(hl.getString("groupid", i));
				group.setParentid(hl.getString("parentid", i));
				group.setDistrictcode(hl.getString("districtcode", i));
				group.setName(hl.getString("name", i));
				group.setShortname(hl.getString("shortname", i));
				group.setType(NODETYPE_NODE);// hl.getString("type",i));
				list.add(group);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return list;
	}

	public String buildMenu(List functionlist) {
		if (functionlist == null) {
			return "";
		}
		// int i = functionlist.size();
		// String
		List<TreeNodeC> rootList = buildTreeList(functionlist);
		JsonConfig jc = new JsonConfig();
		jc.setExcludes(new String[]{"children"});
		String rootListStr = JSONArray.fromObject(rootList, jc).toString();
		StringBuffer sb = new StringBuffer();
		sb.append("var menuroot_size = "+ rootList.size()+";\n");
		sb.append("var menuroot_list = " + rootListStr + ";\n");
		for (int i = 0; i < rootList.size(); i++) {
			TreeNodeC root = rootList.get(i);
			String id = "menuroot" + (i == 0 ? "" : i);
			root.setJsid(id);
			if (i > 0) {
				sb.append("\n");
			}
			sb.append("var " + id + "=new Ext.tree.TreeNode({text: '" + root.getText() + "',draggable:false, id:'" + id + "'});\n");
			getSubMenu(root, sb);
		}
		return sb.toString();

	}

	private String buildMenuAll(List functionlist) {
		if (functionlist == null) {
			return "";
		}
		// int i = functionlist.size();
		// String
		List<TreeNodeC> rootList = buildTreeList(functionlist);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rootList.size(); i++) {
			TreeNode root = rootList.get(i);
			String id = "menuroot" + (i == 0 ? "" : i);
			root.setJsid(id);
			if (i == 0) {
				sb.append("var allmenuroot=new Ext.tree.TreeNode({text: '功能菜单',draggable:false, id:'allmenuroot'});\n");
			} else {
				sb.append("\n");
			}
			sb.append("var " + id + "=allmenuroot.appendChild(new Ext.tree.TreeNode({text: '" + root.getText() + "',id:'" + id + "',href:'javascript:loadPage(" + id + ",\\'" + root.getId() + "\\')',leaf:false}));\n");
			getSubMenuAll(root, sb);
		}
		return sb.toString();

	}

	/**
	 * 构建用户组
	 * 
	 * @param grouplist
	 * @return
	 */
	private String buildMenuGroupAll(List grouplist) {
		if (grouplist == null) {
			return "";
		}
		// int i = functionlist.size();
		// String
		TreeNode root = buildGroupTree(grouplist);
		StringBuffer sb = new StringBuffer();
		if (root != null) {
			root.setJsid("menuroot");
			getMenuAll(root, sb);
			return sb.toString();
		} else {
			return "";
		}

	}

	/**
	 * 构建用户组
	 * 
	 * @param grouplist
	 * @return
	 * @throws AppException
	 */
	private String buildMenuGroupCurrent(List grouplist) throws AppException {
		if (grouplist == null) {
			return "";
		}
		// int i = functionlist.size();
		// String
		List<TreeNode> root = buildGroupTreeCurrent(grouplist);
		StringBuffer sb = new StringBuffer();
		if (root != null) {
			for (int i = 0; i < root.size(); i++) {
				root.get(i).setJsid("menuroot");
				getMenuAll(root.get(i), sb, i);
			}
			return sb.toString();
		} else {
			return "";
		}

	}

	public TreeNode buildTree(List functionlist) {
		if (functionlist == null) {
			return null;
		}
		ArrayList<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		TreeNode root = null;

		// Load All Nodes
		for (int i = 0; i < functionlist.size(); i++) {
			TreeNode node = getTreeNode((FunctionVO) functionlist.get(i));
			if (node != null) {
				treeNodeList.add(node);
				// if parentid is empty, conisdered to be a root node;
				if (node.getParentid().equals("")) {
					root = node;
				}
			}
		}
		// 遍历所有节点建树
		for (int i = 0; i < treeNodeList.size(); i++) {
			TreeNode node = treeNodeList.get(i);
			for (int j = 0; j < treeNodeList.size(); j++) {
				TreeNode childnode = treeNodeList.get(j);
				if (childnode.getParentid().equals(node.getId())) {
					node.addChild(childnode);
					childnode.setParent(node);
				}
			}
		}
		return root;
	}

	/**
	 * 建多颗树
	 * 
	 * @param functionlist
	 * @return
	 */
	public List<TreeNodeC> buildTreeList(List functionlist) {
		if (functionlist == null) {
			return null;
		}
		List<TreeNodeC> rootList = new ArrayList<TreeNodeC>();
		ArrayList<TreeNodeC> treeNodeList = new ArrayList<TreeNodeC>();
		TreeNodeC root = null;

		// Load All Nodes
		for (int i = 0; i < functionlist.size(); i++) {
			TreeNodeC node = getTreeNode((FunctionVO) functionlist.get(i));
			if (node != null) {
				treeNodeList.add(node);
				// if parentid is empty, conisdered to be a root node;
				if (node.getParentid().equals("")) {
					root = node;
					rootList.add(root);
				}
			}
		}
		// 遍历所有节点建树
		for (int i = 0; i < treeNodeList.size(); i++) {
			TreeNode node = treeNodeList.get(i);
			for (int j = 0; j < treeNodeList.size(); j++) {
				TreeNode childnode = treeNodeList.get(j);
				if (childnode.getParentid().equals(node.getId())) {
					node.addChild(childnode);
					childnode.setParent(node);
				}
			}
		}
		// PrintUtil.myprint(rootList.size());
		return rootList;
	}

	/**
	 * 遍历用户组
	 * 
	 * @param grouplist
	 * @return
	 */
	public TreeNode buildGroupTree(List grouplist) {
		if (grouplist == null) {
			return null;
		}
		ArrayList<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		TreeNode root = null;
		// Load All Nodes
		for (int i = 0; i < grouplist.size(); i++) {
			TreeNode node = getTreeGroupNode((SmtGroup) grouplist.get(i));
			if (node != null) {
				treeNodeList.add(node);
				// if parentid is empty, conisdered to be a root node;
				if (node.getParentid().equals("")) {
					root = node;
				}
			}
		}
		// 遍历所有节点建树
		for (int i = 0; i < treeNodeList.size(); i++) {
			TreeNode node = treeNodeList.get(i);
			for (int j = 0; j < treeNodeList.size(); j++) {
				TreeNode childnode = treeNodeList.get(j);
				if (childnode.getParentid().equals(node.getId())) {
					node.addChild(childnode);
					childnode.setParent(node);
				}
			}
		}
		return root;
	}

	/**
	 * 遍历当前用户组
	 * 
	 * @param grouplist
	 * @return
	 * @throws AppException
	 */
	public List<TreeNode> buildGroupTreeCurrent(List grouplist) throws AppException {
		if (grouplist == null) {
			return null;
		}
		ArrayList<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		List<TreeNode> root = new ArrayList();
		TreeNode maxNode = null;
		HList tmpLst = new HList("select groupid from smt_usergroupref where isleader='1' and userid='" + LoginManager.getCurrentUserId() + "'");
		String grouidLst = "";
		for (int i = 0; i < tmpLst.getRowCount(); i++) {
			grouidLst = grouidLst + "," + tmpLst.getString("groupid", i);
		}
		grouidLst = grouidLst.substring(1);
		// Load All Nodes
		for (int i = 0; i < grouplist.size(); i++) {
			TreeNode node = getTreeGroupNode((SmtGroup) grouplist.get(i));
			if (node != null) {
				treeNodeList.add(node);
				// if parentid is empty, conisdered to be a root node;
				if (node.getParentid().equals("")) {
					maxNode = node;
					root.add(node);
				} else if (StringUtil.in(node.getId(), grouidLst)) {
					root.add(node);
				}
			}
		}
		// 遍历所有节点建树
		for (int i = 0; i < treeNodeList.size(); i++) {
			TreeNode node = treeNodeList.get(i);
			for (int j = 0; j < treeNodeList.size(); j++) {
				TreeNode childnode = treeNodeList.get(j);
				if (childnode.getParentid().equals(node.getId())) {
					node.addChild(childnode);
					childnode.setParent(node);
				}
			}
		}
		if (!ObjectUtil.equals(maxNode, "")) {
			root.clear();
			root.add(maxNode);
		}
		return root;
	}

	private TreeNodeC getTreeNode(FunctionVO dto) {
		// TreeNode node = new TreeNode();
		TreeNodeC node = null;
		String functype = dto.getType() == null ? "" : dto.getType();
		// 叶子节点
		if (functype.equals(NODETYPE_LEAF)) {
			node = new TreeNodeC();
			node.setId(dto.getFunctionid());
			node.setText(dto.getTitle());
			node.setLink(dto.getLocation() == null ? "" : dto.getLocation());
			node.setLeaf(true);
			node.setParentid(dto.getParent() == null ? "" : dto.getParent());
			node.setOrderno(dto.getOrderno().shortValue());
			node.setJsid("n" + new Integer(seq++).toString());
			node.setCode(dto.getDescription());
			node.setFuncpic(dto.getFuncpic());
			node.setUptype(dto.getUptype());
			node.setTitle(dto.getParam4());
		}
		// 节点
		else if (functype.equals(NODETYPE_NODE)) {
			node = new TreeNodeC();
			node.setId(dto.getFunctionid());
			node.setText(dto.getTitle());
			node.setLink("");
			node.setLeaf(false);
			node.setParentid(dto.getParent() == null ? "" : dto.getParent());
			node.setOrderno(dto.getOrderno().shortValue());
			node.setJsid("n" + new Integer(seq++).toString());
			node.setCode(dto.getDescription());
			node.setFuncpic(dto.getFuncpic());
			node.setTitle(dto.getParam4());
		}
		return node;
	}

	private TreeNode getTreeGroupNode(SmtGroup dto) {
		// TreeNode node = new TreeNode();
		TreeNode node = null;
		String functype = dto.getType() == null ? "" : dto.getType();
		// 叶子节点
		if (functype.equals(NODETYPE_LEAF)) {
			node = new TreeNode();
			node.setId(dto.getId());
			node.setText(dto.getName());
			node.setLink("");
			node.setLeaf(true);
			node.setParentid(dto.getParentid() == null ? "" : dto.getParentid());
			node.setJsid("n" + new Integer(seq++).toString());
		}
		// 节点
		else if (functype.equals(NODETYPE_NODE)) {
			node = new TreeNode();
			node.setId(dto.getId());
			node.setText(dto.getName());
			node.setLink("");
			node.setLeaf(false);
			node.setParentid(dto.getParentid() == null ? "" : dto.getParentid());
			node.setJsid("n" + new Integer(seq++).toString());
		}
		return node;
	}

	private void getSubMenu(TreeNode node, StringBuffer sb) {
		List<TreeNode> children = node.getChildren();
		if (children.size() <= 0)
			return;
		else {
			Collections.sort(children);
			for (int i = 0; i < children.size(); i++) {
				TreeNode n = (TreeNode) children.get(i);
				sb.append("var " + n.getJsid());
				sb.append("=");
				sb.append(node.getJsid());
				sb.append(".appendChild(new Ext.tree.TreeNode({");
				sb.append("text:");
				sb.append("'" + n.getText() + "',");
				// sb.append("draggable:false,");
				sb.append("id:");
				sb.append("'" + n.getId() + "',");
				if (!n.getLink().equals("")) {
					sb.append("href:");
					sb.append("'javascript:loadPage(" + n.getJsid() + ",\\'" + n.getLink() + "\\')',");
				} else {
					sb.append("href:");
					sb.append("'javascript:" + n.getJsid() + ".toggle()',");
				}
				if (n.isLeaf()) {
					sb.append("leaf:true");
				} else {
					sb.append("leaf:false");
				}
				sb.append("}));");
				// 递归继续生成下面的子菜单
				getSubMenu(n, sb);
			}
		}

	}

	private void getSubMenuAll(TreeNode node, StringBuffer sb) {
		List<TreeNode> children = node.getChildren();
		if (children.size() <= 0)
			return;
		else {
			Collections.sort(children);
			for (int i = 0; i < children.size(); i++) {
				TreeNode n = (TreeNode) children.get(i);
				sb.append("var " + n.getJsid());
				sb.append("=");
				sb.append(node.getJsid());
				sb.append(".appendChild(new Ext.tree.TreeNode({");
				sb.append("text:");
				sb.append("'" + n.getText() + "',");
				// sb.append("draggable:false,");
				sb.append("id:");
				sb.append("'" + n.getId() + "',");
				sb.append("href:");
				sb.append("'javascript:loadPage(" + n.getJsid() + ",\\'" + n.getId() + "\\')',");
				if (n.isLeaf()) {
					sb.append("leaf:true");
				} else {
					sb.append("leaf:false");
				}
				sb.append("}));");
				// 递归继续生成下面的子菜单
				getSubMenuAll(n, sb);
			}
		}

	}

	private void getSubMenuAll(TreeNode node, StringBuffer sb, String tmp) {
		List<TreeNode> children = node.getChildren();
		if (children.size() <= 0)
			return;
		else {
			Collections.sort(children);
			for (int i = 0; i < children.size(); i++) {
				TreeNode n = (TreeNode) children.get(i);
				sb.append("var " + n.getJsid());
				sb.append("=");
				sb.append(tmp);
				sb.append(".appendChild(new Ext.tree.TreeNode({");
				sb.append("text:");
				sb.append("'" + n.getText() + "',");
				// sb.append("draggable:false,");
				sb.append("id:");
				sb.append("'" + n.getId() + "',");
				sb.append("href:");
				sb.append("'javascript:loadPage(" + n.getJsid() + ",\\'" + n.getId() + "\\')',");
				if (n.isLeaf()) {
					sb.append("leaf:true");
				} else {
					sb.append("leaf:false");
				}
				sb.append("}));");
				// 递归继续生成下面的子菜单
				getSubMenuAll(n, sb);
			}
		}

	}

	private void getMenuAll(TreeNode node, StringBuffer sb) {
		TreeNode n = node;
		sb.append("var " + n.getJsid());
		sb.append("=");
		sb.append(node.getJsid());
		sb.append(".appendChild(new Ext.tree.TreeNode({");
		sb.append("text:");
		sb.append("'" + n.getText() + "',");
		// sb.append("draggable:false,");
		sb.append("id:");
		sb.append("'" + n.getId() + "',");
		sb.append("href:");
		sb.append("'javascript:loadPage(" + n.getJsid() + ",\\'" + n.getId() + "\\')',");
		if (n.isLeaf()) {
			sb.append("leaf:true");
		} else {
			sb.append("leaf:false");
		}
		sb.append("}));");
		getSubMenuAll(n, sb);

	}

	private void getMenuAll(TreeNode node, StringBuffer sb, int i) {
		TreeNode n = node;
		String tmp = n.getJsid();
		// if(i>0){
		tmp = n.getJsid() + i;
		// }
		sb.append("var " + tmp);
		sb.append("=");
		sb.append(node.getJsid());
		sb.append(".appendChild(new Ext.tree.TreeNode({");
		sb.append("text:");
		sb.append("'" + n.getText() + "',");
		// sb.append("draggable:false,");
		sb.append("id:");
		sb.append("'" + n.getId() + "',");
		sb.append("href:");
		sb.append("'javascript:loadPage(" + n.getJsid() + ",\\'" + n.getId() + "\\')',");
		if (n.isLeaf()) {
			sb.append("leaf:true");
		} else {
			sb.append("leaf:false");
		}
		sb.append("}));");
		getSubMenuAll(n, sb, tmp);

	}
}
