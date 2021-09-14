package com.insigma.siis.local.epsoft.util;

public class Node {
		 /** 
		 * 节点id 
		 */ 
		 private String id; 
		 private String type; 
		 public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		 /** 
		 * 节点名称 
		*/ 
		private String nodeName; 
		 /** 
		 * 父节点id 
		 */ 
		 private String parentId; 
		 public Node() { 
		 } 
		 public Node(String id, String parentId) { 
		 this.id = id; 
		 this.parentId = parentId; 
		 } 
		 public Node(String id, String nodeName, String parentId) { 
		 this.id = id; 
		 this.nodeName = nodeName; 
		 this.parentId = parentId; 
		 } 
		 public String getId() { 
		 return id; 
		 } 
		 public void setId(String id) { 
		 this.id = id; 
		 } 
		 public String getParentId() { 
		 return parentId; 
		 } 
		 public void setParentId(String parentId) { 
		 this.parentId = parentId; 
		 } 
		 public String getNodeName() { 
		 return nodeName; 
		 } 
		 public void setNodeName(String nodeName) { 
		 this.nodeName = nodeName; 
		 } 
		} 
	
