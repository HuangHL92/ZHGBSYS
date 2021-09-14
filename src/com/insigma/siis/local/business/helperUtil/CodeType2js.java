package com.insigma.siis.local.business.helperUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.Inf;
import com.insigma.siis.local.business.menu.MenuBuilder;
import com.insigma.siis.local.business.menu.TreeNodeC;
import com.insigma.siis.local.business.sysorg.org.dto.B01DTO;

public class CodeType2js {
	
	
	
	public static String getCodeTypeJS(HttpServletRequest request) {
		List<TreeNodeC> n = new MenuBuilder().getMenu2(request);
		if(n.size()==0){
			return "";
		}
		//树 
		StringBuffer sb_tree = new StringBuffer(" var CodeTypeTree={");
		//String sqltree="select distinct code_type from code_value t where code_type!='CODE_TYPE' and code_type!='9999'";
		//List<String> treeTypeList = sess.createSQLQuery(sqltree).list();
		
		List<TreeNodeC> tns = n.get(0).getChildren();
		//String sql_treeRoot_entity = "select code_type,code_value,code_name from code_value t where code_type='"+codetype+"' and sub_code_value='-1' order by code_value";
		//根节点
		//List<Object[]> treeRoot_entity_List = sess.createSQLQuery(sql_treeRoot_entity).list();
		sb_tree.append("tree").append(" : {text: 'Root',expanded: true,children: [");
		String icon ="";
		if(tns!=null&&tns.size()>0){
			for(TreeNodeC treeNode : tns){
				//循环最顶端节点
				if(treeNode.getId().equals("402881e6535a0d9a01535a12048f0002")){
					icon="./main/images/icons/group_key.png";
				}else if(treeNode.getId().equals("40287d815451830d0154519c343e0007")){
					icon="fa-comments";
				}else if(treeNode.getId().equals("S010000")){
					icon="fa-gear";
				}else if(treeNode.getId().equals("4028b8815382758d0153828d591b0002")){
					icon="./main/images/icons/group_key.png";
				}else if(treeNode.getId().equals("402881ef533533800153354ae1c00006")){
					icon="fa-th-large";
				}else if(treeNode.getId().equals("402881e95524e7020155258404ee001c")){
					icon="fa-road";
				}else if(treeNode.getId().equals("402881e95524e702015525cc27d20095")){
					icon="fa-signal";
				}else if(treeNode.getId().equals("4028810b5e32a019015e32e1cb810007")){
					icon="fa-tag";
				}else if(treeNode.getId().equals("402888e9651c274201651c87f0fb0008")||treeNode.getId().equals("297e9b33682b642a01682b67d4670004")){//监督管理
					icon="fa-legal";
				}else if(treeNode.getId().equals("402888e9651c274201651c8606d50003")){//实绩纪实
					icon="fa-file";
				}else if(treeNode.getId().equals("402888e9651c274201651c92bb8a000f")){//教育培训
					icon="fa-book";
				}else if(treeNode.getId().equals("402888e9651c274201651c93fc2a0013")){//考核评价
					icon="fa-edit";
				}
				
				else if(treeNode.getId().equals("402881ea56c0dada0156c0e185080008")){//参数配置
					icon="fa-file";
				}else if(treeNode.getId().equals("402882e565f50fb00165f52099ab0005")||
						treeNode.getId().equals("402864816ce04d42016ce082a81f000b")){//权限管理  干部名册
					icon="fa-tasks";
				}else if(treeNode.getId().equals("402881f95446af54015446b14de70003")){//日志管理
					icon="fa-book";
				}else if(treeNode.getId().equals("402882e6650df09801650e2699ef0009")){//系统构建
					icon="fa-edit";
				}else if(treeNode.getId().equals("402881ee5dd63ea4015dd650c92e0002")){//信息发布
					icon="fa-tasks";
				}else if(treeNode.getId().equals("402888045c960861015c9661add50003")){//自定义表册
					icon="fa-gear";
				}
				
				else if(treeNode.getId().equals("402881ec6ac3690f016ac37bb37f0015")){//青干预审管理
					icon="fa-file";
				}else if(treeNode.getId().equals("402888e9651c274201651c86b5f30005")){//亮灯报警
					icon="fa-comments";
				}else if(treeNode.getId().equals("402881ef69fba83f0169fbbedb370003")){//考核与听取意见
					icon="fa-book";
				}else if(treeNode.getId().equals("402881e66b4aef3e016b4af6ef1b0003")){//监督核查
					icon="fa-edit";
				}else if(treeNode.getId().equals("297e9b33654165f4016541fe37f7000a")){//干部选拔任用
					icon="fa-tasks";
				}else if(treeNode.getId().equals("402881ea6c02c912016c02e226340003")){//分析研判
					icon="fa-gear";
				}else if(treeNode.getId().equals("8290ab2269beee1b0169bef0a2450003")){//职数审核
					icon="fa-th-large";
				}
				
				else if(treeNode.getId().equals("402881e66778117501677815a4030004")){
					icon="fa-file";
				}else if(treeNode.getId().equals("4028812464d9c7690164d9fb103b000c")){
					icon="fa-edit";
				}else if(treeNode.getId().equals("4028812464d9c7690164daa5c3310026")){
					icon="fa-signal";
				}else if(treeNode.getId().equals("4028812464d9c7690164daa02cc5001b")){
					icon="fa-tag";
				}else if(treeNode.getId().equals("3e2081e472ac5dc30172ac6287c10003")){//上会
					icon="fa-folder-open";
				}else if(treeNode.getId().equals("4028b8816ca75021016ca7b8bd8b0005")){//家庭成员
					icon="fa-child";
				}else if(treeNode.getId().equals("3e20818b734098f2017340a880c90003")){//援外
					icon="fa-external-link";
				}else if(treeNode.getId().equals("297e08017341bccc01734292bf770033")){//班子评价
					icon="fa-tags";
				}else if(treeNode.getId().equals("40284681725e0ddc01725f120b990029")){//干部任免
					icon="fa-random";
				}else if(treeNode.getId().equals("3e2081e4728392a5017283bb71330003")){//干部调配
					icon="fa-retweet";
				}
				else{
					icon="fa-search";
				}
				sb_tree.append(" {text: '"+treeNode.getText()+"','title':'"+treeNode.getTitle()+"',id:'"+treeNode.getId()+"',icon:'"+icon+"'");
				hasChildren(treeNode,sb_tree);
				
			}
			//一个指标结束
			sb_tree.deleteCharAt(sb_tree.length()-1);
			sb_tree.append(" ]},");
		}
		
		sb_tree.deleteCharAt(sb_tree.length()-1);
		sb_tree.append(" };");
		
		
		return sb_tree.toString();
	}
	
	public static String getCodeTypeJS2(HttpServletRequest request) {
		
		//树 
		StringBuffer sb_tree = new StringBuffer(" var CodeTypeTree={");
		//根节点
		sb_tree.append("tree").append(" : {text: 'Root',expanded: true,children: [");
		sb_tree.append(" {text: '系统管理',id:'S010000',icon:'./main/images/icons/icon06.png'");
		sb_tree.append(", children:[");
		sb_tree.append(" {text: '系统用户管理',id:'402881e7579269fe0157927829d10004',icon:''");
		sb_tree.append(",location:'/radowAction.do?method=doEvent&pageModel=pages.sysmanager.sysuser.UserManage',leaf: true},");
		sb_tree.append(" {text: '系统角色管理',id:'402881fa57a88cd70157a93e51b2000b',icon:''");
		sb_tree.append(",location:'/radowAction.do?method=doEvent&pageModel=pages.sysmanager.role.OutSideRole',leaf: true}");
		sb_tree.append(" ]}");
		sb_tree.append(" ]}");
		sb_tree.append(" };");
		return sb_tree.toString();
	}
	
	private static void hasChildren(TreeNode treeRoot_entity,StringBuffer sb_tree) {
		//String sql = "select code_type,code_value,code_name from code_value t where code_type='"+codetype+"' and sub_code_value='"+treeRoot_entity[1].toString()+"' order by code_value";
		//List<Object[]> treechrild_entity_List = sess.createSQLQuery(sql).list();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		List<TreeNodeC> treechrild_entity_List = treeRoot_entity.getChildren();
		if(treechrild_entity_List!=null&&treechrild_entity_List.size()>0){
			sb_tree.append(", children:[");
			for(TreeNodeC treechrild_entity : treechrild_entity_List){
				//日志显示的控制
				//对system用户放行
				if(user.getId().equals("40288103556cc97701556d629135000f")){
					sb_tree.append(" \r\n{text: '"+treechrild_entity.getText()+"','title':'"+treechrild_entity.getTitle()+"',id:'"+treechrild_entity.getId()+"',icon:''");
					hasChildren(treechrild_entity,sb_tree);
					continue;
				}
				if(user.getUsertype().equals("2")||user.getUsertype().equals("-1")){
					if(treechrild_entity.getText().equals("日志管理")){
						continue;
					}
				}
				sb_tree.append(" \r\n{text: '"+treechrild_entity.getText()+"','title':'"+treechrild_entity.getTitle()+"',id:'"+treechrild_entity.getId()+"',icon:''");
				hasChildren(treechrild_entity,sb_tree);
				
			}
			//闭合
			//一个叶子节点结束
			sb_tree.deleteCharAt(sb_tree.length()-1);
			sb_tree.append(" ]},");
		}else{
			sb_tree.append(",location:'"+treeRoot_entity.getLink()+"',leaf: true},");
		}
	}
	
	public static String getCodeTypeJS(String codetyp) {
		HBSession sess = HBUtil.getHBSession();
		//下拉框
		String sql="select code_type,code_value,code_name from code_value t where code_type='"+codetyp+"' order by ININO,code_value";
		
		List<Object[]> list = sess.createSQLQuery(sql).list();
		
		if(list!=null&&list.size()>0){
			//[['1','男',''],['2','女',''],['9','未说明得性别','']] 下拉框数据语法
			StringBuffer sb = new StringBuffer("");
		
			sb.append("[");
			for(Object[] codevalue : list){
				sb.append("\r\n['"+codevalue[1].toString()+"','"+codevalue[2].toString()+"'],");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("],");
			
			sb.deleteCharAt(sb.length()-1);
			//sb.append("};");
			return sb.toString();
		}
		return	"[]";
	}
	
	/**
	 * 任免表专用
	 */
	public static String getRrmbCodeType() {
		HBSession sess = HBUtil.getHBSession();
		//下拉框
		String sql="select code_type,code_value,code_name from code_value t where code_type "
				+ " in('GB2261','GB3304','GB2261D','ZB130','ZB135','ZB125','ZB126','GB4761','GB4762','ZB09','ZB148','ZB01','ZB139','ZB134','XZ91','XZ93','XZ94','XZ95','XZ96','XZ97','A3385','A0251B','EXTRA_TAGS') order by inino,code_value";
		
		List<Object[]> list = sess.createSQLQuery(sql).list();
		
		Map<String, List<Map<String, String>>> codemap = new LinkedHashMap<String, List<Map<String,String>>>();
		//List<Map<String,String>> mlist = new ArrayList<Map<String,String>>();
		for(Object[] codevalue : list){
			if(codevalue[1]!=null&&!"".equals(codevalue[1].toString())){
				Map<String, String> m = new HashMap<String, String>();
				if("GB4762".equals(codevalue[0].toString())){
					Map<String, String> m2 = new HashMap<String, String>();
					m2.put("code_value", codevalue[2].toString());
					m2.put("code_name", codevalue[2].toString());
					List<Map<String, String>> mlist = codemap.get("GB9999");
					if(mlist==null){
						mlist = new ArrayList<Map<String,String>>();	
						codemap.put("GB9999", mlist);
					}
					mlist.add(m2);
				}else{
					m.put("code_value", codevalue[1].toString());
					m.put("code_name", codevalue[2].toString());
					List<Map<String, String>> mlist = codemap.get(codevalue[0].toString());
					if(mlist==null){
						mlist = new ArrayList<Map<String,String>>();	
						codemap.put(codevalue[0].toString(), mlist);
					}
					mlist.add(m);
				}
				
			}
		}
		//[['1','男',''],['2','女',''],['9','未说明得性别','']] 下拉框数据语法
		StringBuffer sb = new StringBuffer(" var CodeTypeJson={");
		StringBuffer sbr = new StringBuffer(" var CodeTypeJsonr={");
		StringBuffer sbkey = new StringBuffer(" var CodeTypeKey={");
		for(String codetype : codemap.keySet()){
			List<Map<String, String>> codeEnetys = codemap.get(codetype);
			sb.append("\r\n"+codetype).append(": {");
			sbr.append("\r\n"+codetype).append(": {");
			sbkey.append("\r\n"+codetype).append(": [");
			int i=0;
			for(Map<String, String> codeEnety : codeEnetys){
				if(i%5==0){
					sb.append("\r\n");
					sbr.append("\r\n");
					sbkey.append("\r\n");
				}
				sb.append("'"+codeEnety.get("code_value")+"':'"+codeEnety.get("code_name")+"',");
				sbr.append("'"+codeEnety.get("code_name")+"':'"+codeEnety.get("code_value")+"',");
				sbkey.append("'"+codeEnety.get("code_value")+"',");
				i++;
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("},");
			sbr.deleteCharAt(sbr.length()-1);
			sbr.append("},");
			sbkey.deleteCharAt(sbkey.length()-1);
			sbkey.append("],");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("};");
		sbr.deleteCharAt(sbr.length()-1);
		sbr.append("};");
		
		sbkey.deleteCharAt(sbkey.length()-1);
		sbkey.append("};");
		return sb.toString()+sbr.toString()+sbkey.toString();
	}

	/**
	 * @param args
	 */
	public static void getCodeTypeJS() {
		HBSession sess = HBUtil.getHBSession();
		//下拉框
		String sql="select code_type,code_value,code_name from code_value t where code_type!='9999' order by code_value";
		
		List<Object[]> list = sess.createSQLQuery(sql).list();
		
			Map<String, List<Map<String, String>>> codemap = new LinkedHashMap<String, List<Map<String,String>>>();
			//List<Map<String,String>> mlist = new ArrayList<Map<String,String>>();
			for(Object[] codevalue : list){
				
				if(codevalue[1]!=null&&!"".equals(codevalue[1].toString())){
					Map<String, String> m = new HashMap<String, String>();
					m.put("code_value", codevalue[1].toString());
					m.put("code_name", codevalue[2].toString());
					List<Map<String, String>> mlist = codemap.get(codevalue[0].toString());
					if(mlist==null){
						mlist = new ArrayList<Map<String,String>>();	
						codemap.put(codevalue[0].toString(), mlist);
					}
					mlist.add(m);
				}
				
				
				
				
			}
			//[['1','男',''],['2','女',''],['9','未说明得性别','']] 下拉框数据语法
			StringBuffer sb = new StringBuffer(" var CodeType={");
			for(String codetype : codemap.keySet()){
				List<Map<String, String>> codeEnetys = codemap.get(codetype);
				sb.append("\r\n"+codetype).append(": [");
				int i=0;
				for(Map<String, String> codeEnety : codeEnetys){
					if(i%5==0){
						sb.append("\r\n");
					}
					sb.append("['"+codeEnety.get("code_value")+"','"+codeEnety.get("code_name")+"'],");
					i++;
				}
				sb.deleteCharAt(sb.length()-1);
				sb.append("],");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("};");
			String path = new SysManagerUtils().getWebrootPath();
			try {
				FileWriter w=new FileWriter(path+"\\basejs\\codetype.js",false);
				w.write(sb.toString());
				w.flush();
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		/*//树 
		StringBuffer sb_tree = new StringBuffer(" var CodeTypeTree={");
		String sqltree="select distinct code_type from code_value t where code_type!='CODE_TYPE' and code_type!='9999'";
		List<String> treeTypeList = sess.createSQLQuery(sqltree).list();
		for(String codetype : treeTypeList){
			String sql_treeRoot_entity = "select code_type,code_value,code_name from code_value t where code_type='"+codetype+"' and sub_code_value='-1' order by code_value";
			//根节点
			List<Object[]> treeRoot_entity_List = sess.createSQLQuery(sql_treeRoot_entity).list();
			sb_tree.append(codetype).append(" : {text: 'Root',expanded: true,children: [");
			if(treeRoot_entity_List!=null&&treeRoot_entity_List.size()>0){
				for(Object[] treeRoot_entity : treeRoot_entity_List){
					//循环最顶端节点
					sb_tree.append(" {text: '"+treeRoot_entity[2].toString()+"',id:'"+treeRoot_entity[1].toString()+"'");
					hasChildren(treeRoot_entity,codetype,sess,sb_tree);
					
				}
				//一个指标结束
				sb_tree.deleteCharAt(sb_tree.length()-1);
				sb_tree.append(" ]},");
			}
		}
		sb_tree.deleteCharAt(sb_tree.length()-1);
		sb_tree.append(" };");
		
		try {
			FileWriter w=new FileWriter(path+"\\basejs\\codetype.js",true);
			w.write(sb_tree.toString());
			w.flush();
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	private static void hasChildren(Object[] treeRoot_entity,String codetype,HBSession sess,StringBuffer sb_tree) {
		String sql = "select code_type,code_value,code_name from code_value t where code_type='"+codetype+"' and sub_code_value='"+treeRoot_entity[1].toString()+"' order by code_value";
		List<Object[]> treechrild_entity_List = sess.createSQLQuery(sql).list();
		if(treechrild_entity_List!=null&&treechrild_entity_List.size()>0){
			sb_tree.append(", children:[");
			for(Object[] treechrild_entity : treechrild_entity_List){
				sb_tree.append(" \r\n{text: '"+treechrild_entity[2].toString()+"',id:'"+treechrild_entity[1].toString()+"'");
				hasChildren(treechrild_entity,codetype,sess,sb_tree);
				
			}
			//闭合
			//一个叶子节点结束
			sb_tree.deleteCharAt(sb_tree.length()-1);
			sb_tree.append(" ]},");
		}else{
			sb_tree.append(",leaf: true},");
		}
	}

	
	
	
	
	
	/**
	 * 下拉框控件直接查表 不通过V_aa10
	 * @param codetype
	 * @param filter
	 * @return
	 */
	public static String getCodeTypeJS(String codetype,String filter,String codename) {
		if(filter==null){
			filter = "1=1";
		}
		if(codename==null||"".equals(codename)){
			codename = "code_name";
		}
		HBSession sess = HBUtil.getHBSession();
		//下拉框
		String sql="select code_type,code_value,"+codename+" from code_value t where code_type='"+codetype+"'" +
				" and " + filter + " order by inino, code_value";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String, List<Map<String, String>>> codemap = new HashMap<String, List<Map<String,String>>>();
			//List<Map<String,String>> mlist = new ArrayList<Map<String,String>>();
			//[['1','男',''],['2','女',''],['9','未说明得性别','']] 下拉框数据语法
			StringBuffer sb = new StringBuffer("");
			//sb.append("[");
			for(Object[] codevalue : list){
				if(codevalue[2]!=null) {
					sb.append("\r\n['"+codevalue[1].toString()+"','"+codevalue[2].toString()+"',''],");
				}
			}
			sb.deleteCharAt(sb.length()-1);
			//sb.append("]");
			return sb.toString();
		}else{
			return "[]";
		}
	}
	
	
	/**
	 * 下拉框控件直接查表 不通过V_aa10
	 * @param codetype
	 * @param filter
	 * @return
	 */
	public static List<String> getCodeTypeList(String codetype,String filter,String codename) {
		if(filter==null){
			filter = "1=1";
		}
		if(codename==null||"".equals(codename)){
			codename = "code_name";
		}
		HBSession sess = HBUtil.getHBSession();
		//下拉框
		String sql="select code_type,code_value,"+codename+" from code_value t where code_type='"+codetype+"'" +
				" and " + filter + " order by inino, code_value";
		
		List<Object[]> list = sess.createSQLQuery(sql).list();
		List<String> l = new ArrayList<String>();
		if(list!=null&&list.size()>0){
			for(Object[] codevalue : list){
				l.add("{key:'"+codevalue[1].toString()+"',value:'"+codevalue[2].toString()+"'}");//{key:'1',value:'在任'}
			}
		}
		return l;
	}
	
	/**
	 * 通过select标签把 字段 对应的 描述和代码类型 插入 COMPETENCE_INF 表
	 * @return
	 */
	public static void insertLogConfig(String field, String label, String codetype){
		field = field.toUpperCase();
		Inf logconfig = new Inf();
		String selectsql = "from Inf where infoid='"+field+"'";
		HBSession sess = HBUtil.getHBSession();
		List<Inf> list = sess.createQuery(selectsql).list();
		if(list!=null&&list.size()>0){
			Inf lcfg = list.get(0);
			if(lcfg.getCodetype()==null||"".equals(lcfg.getCodetype())){
				lcfg.setCodetype(codetype);
			}
			if(lcfg.getInfoname()==null||"".equals(lcfg.getInfoname())){
				lcfg.setInfoname(label);
			}
			sess.update(lcfg);
		}else{
			logconfig.setCodetype(codetype);
			logconfig.setInfoname(label);
			logconfig.setInfoid(field);
			sess.save(logconfig);
		}
		sess.flush();
	}
	
	public static String getCodeTypeTREEGWGLLB(String codevalueparameter) {
		String sql = "select x.gwcode code_value,trim('-1'),gwname code_name,1 code_leaf " +
				"  From jggwconf x Where  b0111='"+codevalueparameter+"'  order By Code_Value Desc ";
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			List<Object[]> listres = sess.createSQLQuery(sql).list();
			
			List<TreeNode> rootList = new ArrayList<TreeNode>();
			Map<String,TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
			for(Object[] treedata : listres){
					TreeNode rootnode = genNode(treedata);
					nodemap.put(rootnode.getId(), rootnode);
					if("-1".equals(treedata[1])){
						rootList.add(rootnode);
					}
			}
			genTree(nodemap);
			return genTreeStr(rootList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "{}";
	}
	
	public static String getuserGroupTreeData(String node) {
		HBSession sess = null;
		StringBuffer sb = new StringBuffer("");
		try {
			sb.append("[");
			sess = HBUtil.getHBSession();
			String sql = "";
			if(node.equals("-1")) {
				sql = "select x.id code_value,trim('-1'),usergroupname code_name,(select count(1)"
						+ " from smt_usergroup p where p.sid=x.id ) code_leaf " +
						"  From smt_usergroup x Where  sid is null order By sortid Desc ";
			} else {
				sql = "select x.id code_value,sid,usergroupname code_name,(select count(1) " + 
						" from smt_usergroup p where p.sid=x.id ) code_leaf " +
						"  From smt_usergroup x Where  sid ='"+node+"'  order By sortid Desc ";
			}
			List<Object[]> listres = sess.createSQLQuery(sql).list();
			for(Object[] treedata : listres){
				BigDecimal b = (BigDecimal) treedata[3];
				sb.append("{text: '"+treedata[2]+"',id:'"+treedata[0]+"',cls:'folder'");
				if(b.intValue()>0) {
					sb.append(",leaf: false");
				} else {
					sb.append(",leaf: true");
				}
				sb.append(",'href':");
				sb.append("\"javascript:radow.doEvent('querybyid','"
				            							+ treedata[0] + "')\"");
				
				sb.append("},");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			//System.out.println(sb.toString());
			return sb.toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "{}";
	}
	public static String getCodeTypeTREE(String node, String codetype,
			String codename, String changeValue) {
		
		String treesql = "select t.code_value,t.sub_code_value,t."+codename+",t.code_leaf from code_value t " +
				" where t.code_status='1' and t.code_type='"+codetype+"'";
		String filter = "";
		if("ZB08".equals(codetype)){//职务名称
			if("1002".equals(changeValue)||"1001".equals(changeValue)){//党的纪委 党委
				filter = " and t.code_value in('001A','001B','ZZZZ','T01K')";
			}else if("1003".equals(changeValue)){//人大
				filter = " and t.code_value in('004A','004B','ZZZZ')";
			}else if("1004".equals(changeValue)){//政府
				filter = " and t.code_value in('201A','201B','251A','A0215A','252A','A0215A'," +
						"'254A','254B','255A','255B','256A','256B','257A','257B','258A','258B','259A','259B'" +
						",'260A','260B','261A','261B','ZZZZ','251B','252B')";
			}else if("1005".equals(changeValue)){//政协
				filter = " and t.code_value in('201A','201B','ZZZZ')";
			}else if("1006".equals(changeValue)){//法院
				filter = " and t.code_value in('417A','417B','ZZZZ')";
			}else if("1007".equals(changeValue)){//检察院
				filter = " and t.code_value in('105A','105B','ZZZZ')";
			}
		}else if("ZB09".equals(codetype)){
			if("01".equals(changeValue)){//公务员、参照管理人员岗位
				filter = " and (t.code_value like '01%' or t.code_value like '02%' or t.code_value like '09%' )";
			}else if("02".equals(changeValue)){//事业单位管理岗位
				filter = " and (t.code_value like '08%')";
			}else if("03".equals(changeValue)){//事业单位专业技术岗位
				filter = " and (t.code_value like '03%')";
			}else if("04".equals(changeValue)){//机关技术工人岗位
				filter = " and (t.code_value like '04%')";
			}else if("05".equals(changeValue)){//机关普通工人岗位
				filter = " and (t.code_value like '05%')";
			}else if("06".equals(changeValue)){//事业单位技术工人岗位
				filter = " and (t.code_value like '06%')";
			}else if("07".equals(changeValue)){//事业单位普通工人岗位
				filter = " and (t.code_value like '07%')";
			}else{//其它
				
			}
		}
		
		treesql += filter +" order by ININO,t.code_value";
		
		
		
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			List<Object[]> listres = sess.createSQLQuery(treesql).list();
			
			List<TreeNode> rootList = new ArrayList<TreeNode>();
			Map<String,TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
			for(Object[] treedata : listres){
				if(treedata[2]!=null) {
				TreeNode rootnode = genNode(treedata);
				nodemap.put(rootnode.getId(), rootnode);
				if("-1".equals(treedata[1])){
					rootList.add(rootnode);
				}
				}
			}
			genTree(nodemap);
			return genTreeStr(rootList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "{}";
	}
			
	
	private static void genTree(Map<String, TreeNode> nodemap) {
		for(String key : nodemap.keySet()){
			TreeNode node = nodemap.get(key);
			if(!"-1".equals(node.getParentid())){
				String a = node.getParentid();
				TreeNode node2 =  nodemap.get(node.getParentid());
				if(node2!=null){
					node2.addChild(node);
				}
			}
		}
	}
	private static TreeNode genNode(Object[] treedata) {
		TreeNode node = new TreeNode();
		node.setId(treedata[0].toString());
		node.setText(treedata[2].toString());
		node.setLink("");
		node.setLeaf(true);
		node.setParentid(treedata[1].toString());
		node.setOrderno((short)1);
		//node.setJsid("n" + new Integer(seq++).toString());
		//node.setCode(dto.getB0101());
//		node.setFuncpic(dto.getFuncpic());
		//node.setFuncpic("1");
		if("1".equals(treedata[3].toString())){
			node.setUptype("true");
		}else{
			node.setUptype("false");
		}
		
		return node;
	}
	
	public static String genTreeStr(List<TreeNode> rootList) {
		
		//树 
		StringBuffer sb_tree = new StringBuffer("[");
		
		//根节点
		if(rootList!=null&&rootList.size()>0){
			for(TreeNode treeNode : rootList){
				sb_tree.append("{'text' :'" + treeNode.getText()
				            							+ "' ,'id' :'" + treeNode.getId() +"|" +treeNode.getUptype()
				            							+ "' ,'leaf' :" + (treeNode.getChildren().size()>0?"false":"true")
				            							+ " ,'cls' :'folder',");
				sb_tree.append("'href':");
				sb_tree.append("\"javascript:radow.doEvent('querybyid','"
				            							+ treeNode.getId() + "')\"");
				
				genChildren(treeNode,sb_tree);
				
			}
			//一个指标结束
			sb_tree.deleteCharAt(sb_tree.length()-1);
			sb_tree.append(" ]");
		}
		
		return sb_tree.toString();
	}
	
	private static void genChildren(TreeNode treeRoot_entity,StringBuffer sb_tree) {
		List<TreeNode> treechrild_entity_List = treeRoot_entity.getChildren();
		if(treechrild_entity_List!=null&&treechrild_entity_List.size()>0){
			sb_tree.append(", children:[");
			
			for(TreeNode treechrild_entity : treechrild_entity_List){
				sb_tree.append("{'text' :'" + treechrild_entity.getText()
						+ "' ,'id' :'" + treechrild_entity.getId() +"|" +treechrild_entity.getUptype()
						+ "' ,'leaf' :" + (treechrild_entity.getChildren().size()>0?"false":"true")
						+ " ,'cls' :'folder',");
				sb_tree.append("'href':");
				sb_tree.append("\"javascript:radow.doEvent('querybyid','"
										+ treechrild_entity.getId() + "')\"");
				
				genChildren(treechrild_entity,sb_tree);
				
			}
			//闭合
			//一个叶子节点结束
			sb_tree.deleteCharAt(sb_tree.length()-1);
			sb_tree.append(" ]},");
		}else{
			sb_tree.append(" },");
		}
	}
	
	public static String getCusQueryList(String loginname){
		String treesql="SELECT CQLI,LISTNAME,LISTCOUNT,LISTTIME,PARENTID  FROM CUSTOMQUERYLIST WHERE LOGINNAME='"+loginname+"' ORDER BY LISTTIME DESC";
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			List<Object[]> listres = sess.createSQLQuery(treesql).list();
			List<TreeNode> rootList = new ArrayList<TreeNode>();
			Map<String,TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
			for(Object[] treedata : listres){
				TreeNode rootnode = genCusQueryNode(treedata);
				nodemap.put(rootnode.getId(), rootnode);
				if("-1".equals(treedata[4])){
					rootList.add(rootnode);
				}
			}
			genTree(nodemap);
			return genTreeStr(rootList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "{}";
	}
	private static TreeNode genCusQueryNode(Object[] treedata){
		TreeNode node = new TreeNode();
		node.setId(treedata[0].toString());
		node.setText(treedata[1].toString()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人数："+treedata[2].toString()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最后修改时间："+treedata[3].toString().substring(0,treedata[3].toString().length()-2));
		node.setLink("");
		node.setLeaf(true);
		node.setParentid(treedata[4].toString());
		node.setOrderno((short)1);
		String sql="SELECT * FROM customquerylist where PARENTID='"+treedata[0].toString()+"'";
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			List<Object[]> listres = sess.createSQLQuery(sql).list();
			if(listres.size() < 1){
				node.setUptype("true");
			}else{
				node.setUptype("false");
			}
		}catch (Exception e) {
			node.setUptype("true");
			System.out.println(node.getId()+"没有子节点");
		}
		
		
		return node;
	}
	
	@SuppressWarnings("unchecked")
	public static String[] getFpInfo(HttpServletRequest request) {
		String ret[] = new String[2];
		List<Object> list = HBUtil.getHBSession().createSQLQuery("select roleid from smt_act where objectid='"+ SysUtil.getCacheCurrentUser().getId()
				+"' and roleid in ('402881e66b4b9bcb016b4bdc359f0011','402881e66b4b9bcb016b4bdc73cc0013','402881e66b4b9bcb016b4bdc9e6a0015','402881e66b4b25fc016b4b4a4d250011')").list();
		if(list.size()>0) {
			String roleid = list.get(0).toString();
			if(roleid.equals("402881e66b4b9bcb016b4bdc359f0011")) {
				ret[0] = "zjj";
				ret[1] = "402881e66b4b25fc016b4b8e50cb00d5";
			} else if(roleid.equals("402881e66b4b9bcb016b4bdc73cc0013")) {
				ret[0] = "zjh";
				ret[1] = "402881e66b4b25fc016b4b8f245800d6";
			} else if(roleid.equals("402881e66b4b9bcb016b4bdc9e6a0015")) {
				ret[0] = "gs";
				ret[1] = "402881e66b4b25fc016b4b8ff90200d7";
			} else if(roleid.equals("402881e66b4b25fc016b4b4a4d250011")) {
				ret[0] = "ga";
				ret[1] = "402881e66b4aef3e016b4afc69390006";
			}
		} else {
			List<Object> list2 = HBUtil.getHBSession().createSQLQuery("select f.resourceid from smt_act r,smt_acl f where  r.roleid=f.roleid and objectid='"+ SysUtil.getCacheCurrentUser().getId()
					+"' and f.resourceid = '40287d815451830d0154519c343e0007'").list();
			if(list2.size()>0) {
				ret[0] = "ryxx";
			}
			
		}
		return ret;
	}
}
