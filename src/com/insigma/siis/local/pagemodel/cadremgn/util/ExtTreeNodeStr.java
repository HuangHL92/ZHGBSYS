package com.insigma.siis.local.pagemodel.cadremgn.util;

import java.io.Serializable;
import java.util.List;

/**  
 * <p>Title: ExtTNResult</p>  
 * <p>Description:ExtTreeNode���ؽ�� 
 * 	[{id: 1,text: 'A leaf Node',leaf: true},
 * {id: 2,text: 'A folder Node',children: [{id: 3,text: 'A child Node',leaf: true}]]
 * </p>  
 * @author Desire  
 * @date 2018��3��25��  
 */
public class ExtTreeNodeStr implements Serializable {
	
	private String id;
	private String text;
	private Boolean leaf;
	//private List<ExtTreeNode> childrens;
	/**
	 * ��ȡ #{bare_field_comment} 
	 * @return id
	 */
	public String getId() {
		return id;
	}
	/**
	 * ���� #{bare_field_comment} 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * ��ȡ #{bare_field_comment} 
	 * @return text
	 */
	public String getText() {
		return text;
	}
	/**
	 * ���� #{bare_field_comment} 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * ��ȡ #{bare_field_comment} 
	 * @return leaf
	 */
	public Boolean getLeaf() {
		return leaf;
	}
	/**
	 * ���� #{bare_field_comment} 
	 * @param leaf
	 */
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	/**
	 * ��ȡ #{bare_field_comment} 
	 * @return children
	 */
	/*public List<ExtTreeNode> getChildren() {
		return childrens;
	}*/
	/**
	 * ���� #{bare_field_comment} 
	 * @param children
	 */
	/*public void setChildren(List<ExtTreeNode> childrens) {
		this.childrens = childrens;
	}*/
	
	
}
