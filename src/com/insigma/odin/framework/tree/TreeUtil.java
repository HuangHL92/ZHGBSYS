package com.insigma.odin.framework.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeUtil {
	
	private TreeUtil(){}
	
	public static List<TreeNode> buildTree(List<TreeNode> nodeList){
		List<TreeNode> rootList = new ArrayList<TreeNode>();
		for(Iterator<TreeNode> it = nodeList.iterator();it.hasNext();){
			TreeNode cueNode = it.next();
			for(Iterator<TreeNode> it2 = nodeList.iterator();it2.hasNext();){
				TreeNode node = it2.next();
				if(cueNode.getId().equals(node.getParent())){
					cueNode.getChildren().add(node);
				}
			}
			if(cueNode.getChildren().size()>0){
				cueNode.setLeaf(false);
			}
			if(cueNode.getParent()==null || cueNode.getParent().equals("")){
				rootList.add(cueNode);
			}
		}
		return rootList;
	}
}
