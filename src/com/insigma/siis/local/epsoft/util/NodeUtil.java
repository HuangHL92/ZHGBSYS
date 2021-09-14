package com.insigma.siis.local.epsoft.util;
import java.util.ArrayList; 
import java.util.Iterator; 
import java.util.List; 

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class NodeUtil {
	private List<String> returnList = new ArrayList<String>(); 

	 /** 
	 * 根据父节点的ID获取所有子节点 
	* @param list 分类表 
	* @param typeId 传入的父节点ID 
	 * @return String 
	 */ 
	 public String getChildNodes(List<Node> list, String typeId) { 
	 if(list == null && typeId == null) return ""; 
	 for (Iterator<Node> iterator = list.iterator(); iterator.hasNext();) { 
	 Node node = (Node) iterator.next(); 
	 // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点 
	if (typeId.equals(node.getId())) { 
	 recursionFn(list, node); 
	 } 
	 // 二、遍历所有的父节点下的所有子节点 
	/*if (node.getParentId()==0) { 
	 recursionFn(list, node); 
	 }*/ 
	 } 
	 return returnList.toString(); 
	 } 

	 private void recursionFn(List<Node> list, Node node) { 
	 List<Node> childList = getChildList(list, node);// 得到子节点列表 
	if (hasChild(list, node)) {// 判断是否有子节点 
	returnList.add(node.getId()); 
	 Iterator<Node> it = childList.iterator(); 
	 while (it.hasNext()) { 
	 Node n = (Node) it.next(); 
	 recursionFn(list, n); 
	 } 
	 } else { 
	 returnList.add(node.getId()); 
	 } 
	 } 

	 // 得到子节点列表 
	private List<Node> getChildList(List<Node> list, Node node) { 
	 List<Node> nodeList = new ArrayList<Node>(); 
	 Iterator<Node> it = list.iterator(); 
	 while (it.hasNext()) { 
	 Node n = (Node) it.next(); 
	 if(n.getParentId()!=null){
	 if (n.getParentId().equals(node.getId())) { 
	 nodeList.add(n); 
	 } 
	 }
	 } 
	 return nodeList; 
	 } 
	 // 判断是否有子节点 
	private boolean hasChild(List<Node> list, Node node) { 
	 return getChildList(list, node).size() > 0 ? true : false; 
	 } 


	 // 本地模拟数据测试 
	public static void main(String[] args) { 
	 long start = System.currentTimeMillis(); 
	 /*List<Node> nodeList = new ArrayList<Node>(); 
	 Node node1 = new Node("1", "蔬菜", "0"); 
	 Node node2 = new Node("2", "水产", "0"); 
	 Node node3 = new Node("3", "畜牧", "0"); 
	 Node node4 = new Node("4", "瓜类", "1"); 
	 Node node5 = new Node("5", "叶类", "1"); 
	 Node node6 = new Node("6", "丝瓜", "4"); 
	 Node node7 = new Node("7", "黄瓜", "4"); 
	 Node node8 = new Node("8", "白菜", "1"); 
	 Node node9 = new Node("9", "虾", "2"); 
	 Node node10 = new Node("10", "鱼", "2"); 
	 Node node11 = new Node("11", "牛", "3"); 

	 nodeList.add(node1); 
	 nodeList.add(node2); 
	 nodeList.add(node3); 
	 nodeList.add(node4); 
	 nodeList.add(node5); 
	 nodeList.add(node6); 
	 nodeList.add(node7); 
	 nodeList.add(node8); 
	 nodeList.add(node9); 
	 nodeList.add(node10); 
	 nodeList.add(node11); */
	 List<Node> nodeList = new ArrayList<Node>();
	 HBSession sess = HBUtil.getHBSession();
	 String hql = "From FunctionVO";
	 List list = sess.createQuery(hql).list();
	 for(int i=0;i<list.size();i++){
		 FunctionVO sf = (FunctionVO)list.get(i);
		 Node node = new Node(sf.getFunctionid(),sf.getTitle(),sf.getParent());
		 nodeList.add(node); 
	 }
	 NodeUtil mt = new NodeUtil(); 
	 CommonQueryBS.systemOut(mt.getChildNodes(nodeList, "402881e5539db7a201539dea384b0002")); 
	 long end = System.currentTimeMillis(); 
	 CommonQueryBS.systemOut("用时:" + (end - start) + "ms"); 
	 } 

	} 

	
