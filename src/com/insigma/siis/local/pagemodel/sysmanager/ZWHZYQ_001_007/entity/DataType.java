package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity;

public enum DataType {
	
	/**
	 * 字符串
	 */
	STRING("VARCHAR2",1),
	/**
	 * 数值
	 */
	NUMBER("NUMBER",2),
	/**
	 * 日期
	 */
	DATE("DATE",3),
	/**
	 * 二进制流
	 */
	BINARY("BLOB",4),
	/**
	 * 大文本
	 */
	LARGETEXT("CLOB",4);
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 索引
	 */
	private int index;

	/**
	 * 获取名称
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取索引
	 * @return 索引
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 设置索引
	 * @param name 索引
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * 构造函数
	 * @param name 名称
	 * @param index 索引
	 */
	private DataType(String name, int index) {
		this.name = name;
		this.index = index;
	}
}
