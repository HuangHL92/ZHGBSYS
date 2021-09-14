package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity;

public enum DataType {
	
	/**
	 * �ַ���
	 */
	STRING("VARCHAR2",1),
	/**
	 * ��ֵ
	 */
	NUMBER("NUMBER",2),
	/**
	 * ����
	 */
	DATE("DATE",3),
	/**
	 * ��������
	 */
	BINARY("BLOB",4),
	/**
	 * ���ı�
	 */
	LARGETEXT("CLOB",4);
	
	/**
	 * ����
	 */
	private String name;
	
	/**
	 * ����
	 */
	private int index;

	/**
	 * ��ȡ����
	 * @return ����
	 */
	public String getName() {
		return name;
	}

	/**
	 * ��������
	 * @param name ����
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ��ȡ����
	 * @return ����
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * ��������
	 * @param name ����
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * ���캯��
	 * @param name ����
	 * @param index ����
	 */
	private DataType(String name, int index) {
		this.name = name;
		this.index = index;
	}
}
