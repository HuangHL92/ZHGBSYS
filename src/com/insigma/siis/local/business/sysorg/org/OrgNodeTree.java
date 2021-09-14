package com.insigma.siis.local.business.sysorg.org;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.sysorg.org.dto.B01DTO;

public class OrgNodeTree {

	protected ArrayList treeNodeList;

	protected static final String NODETYPE_LEAF = "0";

	protected static final String NODETYPE_NODE = "1";
	
	protected static final String LEFTTREE = "1";//机构信息维护树
	protected static final String OPENTREE = "2";//弹出树
	protected static final String THREETREE = "3";//邹磊用树
	protected static final String FOURTREE = "4";//名册导出用树
	protected static final String FIVETREE = "5";//人员信息维护树
	protected static final String SIXTREE = "6";//人员信息维护树

	protected static int seq = 0;


	public OrgNodeTree() {
		treeNodeList = new ArrayList();
	}

	public static List<TreeNode> getB01s() {
		return getB01s(null);
	}
	public static List<TreeNode> getB01s(String nsjg) {
		List<B01DTO> b01dtolist  = SysOrgBS.selectB01s(nsjg);
			if (b01dtolist != null) {
				return buildTreeList(b01dtolist);
			}
		
		return null;
	}
	
	public static List<TreeNode> getB01sByPeople(String type, String nsjg) {
		List<B01DTO> b01dtolist = null;
		if(nsjg!=null&& nsjg.equals("0")){
			b01dtolist  = SysOrgBS.selectB01sByPeopleNsjg();
		} else {
			b01dtolist  = SysOrgBS.selectB01sByPeople();
		}
		
			if (b01dtolist != null) {
				return buildTreeList(b01dtolist);
			}
		
		return null;
	}
	
	/**
	 * 建多颗树
	 * 
	 * @param functionlist
	 * @return
	 */
	public static List<TreeNode> buildTreeList(List<B01DTO> functionlist) {
		if (functionlist == null) {
			return null;
		}
		List<TreeNode> rootList = new ArrayList<TreeNode>();
		ArrayList<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		TreeNode root = null;
		List<String> list = new ArrayList();
		for(int i = 0; i < functionlist.size(); i++){
			list.add(functionlist.get(i).getB0111());
		}
//		// Load All Nodes
//		for (int i = 0; i < functionlist.size(); i++) {
//			TreeNode node = getTreeNode(functionlist.get(i),list);
//			if (node != null) {
//				treeNodeList.add(node);
//				if (node.getParentid().equals("")) {
//					root = node;
//					rootList.add(root);
//				}
//			}
//		}
//		// 遍历所有节点建树
//		for (int i = 0; i < treeNodeList.size(); i++) {
//			TreeNode node = treeNodeList.get(i);
//			for (int j = 0; j < treeNodeList.size(); j++) {
//				TreeNode childnode = treeNodeList.get(j);
//				if (childnode.getParentid().equals(node.getId())) {
//					node.addChild(childnode);
//					childnode.setParent(node);
//				}
//			}
//		}		
		SysOrgBS.getTime();
		Map<String,TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
		// Load All Nodes
		for (int i = 0; i < functionlist.size(); i++) {
			TreeNode node = getTreeNode(functionlist.get(i),list);
			if (node != null) {
				nodemap.put(node.getId(), node);
				if (node.getParentid().equals("")) {
					root = node;
					rootList.add(root);
				}
			}
		}
		for(String key : nodemap.keySet()){
			TreeNode node = nodemap.get(key);
			if(!"".equals(node.getParentid())){
				TreeNode node2 =  nodemap.get(node.getParentid());
				if(node2!=null){
					node2.addChild(node);
				}
			}
		}
		return rootList;
	}
	
	private static TreeNode getTreeNode(B01DTO dto,List<String> list) {
		TreeNode node = null;
		String functype = getisLeaf(dto.getB0111(),list);
		// 叶子节点

		if (functype.equals(NODETYPE_LEAF)) {
			node = new TreeNode();
			node.setId(dto.getB0111());
			node.setText(dto.getB0101());
			node.setLink("");
			node.setLeaf(true);
			node.setParentid(dto.getB0121() == null ? "" : dto.getB0121());
			node.setOrderno(dto.getSortid().shortValue());
			node.setJsid("n" + new Integer(seq++).toString());
			node.setCode(dto.getB0101());
			node.setFuncpic("1");
//			node.setUptype(dto.getUptype());
			node.setUptype(dto.getB0194()+","+dto.getType());
		}
		// 节点
		else if (functype.equals(NODETYPE_NODE)) {
			node = new TreeNode();
			node.setId(dto.getB0111());
			node.setText(dto.getB0101());
			node.setLink("");
			node.setLeaf(false);
			node.setParentid(dto.getB0121() == null ? "" : dto.getB0121());
			node.setOrderno(dto.getSortid().shortValue());
			node.setJsid("n" + new Integer(seq++).toString());
			node.setCode(dto.getB0101());
//			node.setFuncpic(dto.getFuncpic());
			node.setFuncpic("1");
			node.setUptype(dto.getB0194()+","+dto.getType());
		}
		return node;
	}
	
	public static String getisLeaf(String b0111,List<String> list){
		int j=0;
		String str = NODETYPE_NODE;
		for(int i=0;i<list.size();i++){
			if(list.get(0).contains(b0111)){
				j=j+1;
				if(j>1){
					str= NODETYPE_LEAF;
					break;
				}
			}
		}
		
		
		return str;
	}
	public static String getCodeTypeJS(String type) {
		return getCodeTypeJS(type,null);
	}
	public static String getCodeTypeJS(String type,String nsjg) {
		List<TreeNode> n=null;
		if(FIVETREE.equals(type)||SIXTREE.equals(type)||THREETREE.equals(type)){
			n=getB01sByPeople(type,nsjg);
		}else{
			n=getB01s(nsjg);
		}
		
		//树 
		StringBuffer sb_tree = new StringBuffer("[");
		
		List<TreeNode> tns = n.get(0).getChildren();
		//根节点
		String icon ="";
		String[] a = null;
		if(tns!=null&&tns.size()>0){
			for(TreeNode treeNode : tns){
				//循环最顶端节点
				a=treeNode.getUptype().split(",");
				if(type.equals(LEFTTREE)){
					if(a[1].equals("2")){
						icon ="./main/images/icons/wrong.gif";
					}else{
						if(a[0].equals("1")){
							icon="./main/images/icons/companyOrgImg2.png";
						}else if(a[0].equals("2")){
							icon="./main/images/icons/insideOrgImg1.png";
						}else if(a[0].equals("3")){
							icon="./main/images/icons/groupOrgImg1.png";
						}
					}
				}else{
					if(a[0].equals("1")){
						icon="./main/images/icons/companyOrgImg2.png";
					}else if(a[0].equals("2")){
						icon="./main/images/icons/insideOrgImg1.png";
					}else if(a[0].equals("3")){
						icon="./main/images/icons/groupOrgImg1.png";
					}
				}
				if(type.equals(LEFTTREE)){
					if(a[1].equals("2")){
						sb_tree.append(" {text: '"+treeNode.getText()+"',id:'"+treeNode.getId()+"',icon:'"+icon+"',\"href\":\"javascript:alert('您没有权限')\"");
					}else{
						sb_tree.append(" {text: '"+treeNode.getText()+"',id:'"+treeNode.getId()+"',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+treeNode.getId()+"')\"");
					}
				}else if(type.equals(OPENTREE)){
					sb_tree.append(" {text: '"+treeNode.getText()+"',id:'"+treeNode.getId()+"',tag:'"+""+"',icon:'"+icon+"'");
				}else if(type.equals(FOURTREE)){
					sb_tree.append(" {text: '"+treeNode.getText()+"',id:'"+treeNode.getId()+"',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+treeNode.getId()+"')\"");
				}else if(type.equals(FIVETREE)){
					if(a[1].equals("2")){
						sb_tree.append(" {text: '"+treeNode.getText()+"',id:'"+treeNode.getId()+"',icon:'"+icon+"'");
					}else{
						sb_tree.append(" {text: '"+treeNode.getText()+"',id:'"+treeNode.getId()+"',editable:'"+a[1]+"',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+treeNode.getId()+"')\"");
					}
				}else if(type.equals(SIXTREE)){
					if(a[1].equals("2")){
						sb_tree.append(" {text: '"+treeNode.getText()+"',id:'"+treeNode.getId()+"',icon:'"+icon+"',\"href\":\"javascript:alert('您没有权限')\"");
					}else{
						sb_tree.append(" {text: '"+treeNode.getText()+"',id:'"+treeNode.getId()+"',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+treeNode.getId()+"')\"");
					}
				}else if(type.equals(THREETREE)){
					if(a[1].equals("2")||"0".equals(a[1])||"3".equals(a[0])){//机构授权 2无权限 0查看权限 不能在职务中被选择。机构类型为机构分组不能在机构中被选择
						sb_tree.append(" {text: '"+treeNode.getText()+"',id:'"+treeNode.getId()+"|false',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+treeNode.getId()+"|false--"+treeNode.getText()+"')\"");
					}else{
						sb_tree.append(" {text: '"+treeNode.getText()+"',id:'"+treeNode.getId()+"|true',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+treeNode.getId()+"|true--"+treeNode.getText()+"')\"");
					}
				}else{
					if(a[1].equals("2")){
						sb_tree.append(" {text: '"+treeNode.getText()+"',id:'"+treeNode.getId()+"|false',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+treeNode.getId()+"|false--"+treeNode.getText()+"')\"");
					}else{
						sb_tree.append(" {text: '"+treeNode.getText()+"',id:'"+treeNode.getId()+"|true',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+treeNode.getId()+"|true--"+treeNode.getText()+"')\"");
					}
					
				}
				hasChildren(treeNode,sb_tree,type);
				
			}
			//一个指标结束
			sb_tree.deleteCharAt(sb_tree.length()-1);
			sb_tree.append(" ]");
		}
		
		return sb_tree.toString();
	}
	
	private static void hasChildren(TreeNode treeRoot_entity,StringBuffer sb_tree,String type) {
		List<TreeNode> treechrild_entity_List = treeRoot_entity.getChildren();
		if(treechrild_entity_List!=null&&treechrild_entity_List.size()>0){
			sb_tree.append(", children:[");
			String icon="";
			String[] a = null;
			for(TreeNode treechrild_entity : treechrild_entity_List){
				a=treechrild_entity.getUptype().split(",");
				if(type.equals(LEFTTREE)){
					if(a[1].equals("2")){
						icon ="./main/images/icons/wrong.gif";
					}else{
						if(a[0].equals("1")){
							icon="./main/images/icons/companyOrgImg2.png";
						}else if(a[0].equals("2")){
							icon="./main/images/icons/insideOrgImg1.png";
						}else if(a[0].equals("3")){
							icon="./main/images/icons/groupOrgImg1.png";
						}
					}
				}else{
					if(a[0].equals("1")){
						icon="./main/images/icons/companyOrgImg2.png";
					}else if(a[0].equals("2")){
						icon="./main/images/icons/insideOrgImg1.png";
					}else if(a[0].equals("3")){
						icon="./main/images/icons/groupOrgImg1.png";
					}
				}
				if(type.equals(LEFTTREE)){
					if(a[1].equals("2")){
						sb_tree.append(" \r\n{text: '"+treechrild_entity.getText()+"',id:'"+treechrild_entity.getId()+"',icon:'"+icon+"',\"href\":\"javascript:alert('您没有权限')\"");
					}else{
						sb_tree.append(" \r\n{text: '"+treechrild_entity.getText()+"',id:'"+treechrild_entity.getId()+"',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+treechrild_entity.getId()+"')\"");
					}
				}else if(type.equals(OPENTREE)){
					sb_tree.append(" {text: '"+treechrild_entity.getText()+"',id:'"+treechrild_entity.getId()+"',tag:'"+""+"',icon:'"+icon+"'");
				}else if(type.equals(FOURTREE)){
					sb_tree.append(" \r\n{text: '"+treechrild_entity.getText()+"',id:'"+treechrild_entity.getId()+"',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+treechrild_entity.getId()+"')\"");
				}else if(type.equals(FIVETREE)){
					if(a[1].equals("2")){
						sb_tree.append(" \r\n{text: '"+treechrild_entity.getText()+"',id:'"+treechrild_entity.getId()+"',icon:'"+icon+"'");
					}else{
						sb_tree.append(" \r\n{text: '"+treechrild_entity.getText()+"',id:'"+treechrild_entity.getId()+"',editable:'"+a[1]+"',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+treechrild_entity.getId()+"')\"");
					}
				}else if(type.equals(SIXTREE)){
					if(a[1].equals("2")){
						sb_tree.append(" \r\n{text: '"+treechrild_entity.getText()+"',id:'"+treechrild_entity.getId()+"',icon:'"+icon+"',\"href\":\"javascript:alert('您没有权限')\"");
					}else{
						sb_tree.append(" \r\n{text: '"+treechrild_entity.getText()+"',id:'"+treechrild_entity.getId()+"',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+treechrild_entity.getId()+"')\"");
					}
				}else if(type.equals(THREETREE)){
					if(a[1].equals("2")||"3".equals(a[0])||a[1].equals("0")){
						sb_tree.append(" {text: '"+treechrild_entity.getText()+"',id:'"+treechrild_entity.getId()+"|false',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+treechrild_entity.getId()+"|false--"+treechrild_entity.getText()+"')\"");
					}else{
						sb_tree.append(" {text: '"+treechrild_entity.getText()+"',id:'"+treechrild_entity.getId()+"|true',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+treechrild_entity.getId()+"|true--"+treechrild_entity.getText()+"')\"");
					}
				}else{
					if(a[1].equals("2")){
						sb_tree.append(" {text: '"+treechrild_entity.getText()+"',id:'"+treechrild_entity.getId()+"|false',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+treechrild_entity.getId()+"|false--"+treechrild_entity.getText()+"')\"");
					}else{
						sb_tree.append(" {text: '"+treechrild_entity.getText()+"',id:'"+treechrild_entity.getId()+"|true',icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+treechrild_entity.getId()+"|true--"+treechrild_entity.getText()+"')\"");
					}
					
				}
				hasChildren(treechrild_entity,sb_tree,type);
				
			}
			//闭合
			//一个叶子节点结束
			sb_tree.deleteCharAt(sb_tree.length()-1);
			sb_tree.append(" ]},");
		}else{
			sb_tree.append(",location:'"+treeRoot_entity.getLink()+"',leaf: true},");
		}
	}
	
	
}
