package com.insigma.siis.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aspose.words.IMailMergeDataSource;

public class MapMailMergeDataSource implements IMailMergeDataSource {
	private List<Map<String, Object>> dataList;

	private int index;

	// wordģ���е�?TableStart:tableName??TableEnd:tableName?��Ӧ
	private String tableName = null;

	/**
	 * @param dataList
	 *            ���ݼ�
	 * @param tableName
	 *            ��ģ���е�Name��Ӧ
	 */
	public MapMailMergeDataSource(List<Map<String, Object>> dataList,
			String tableName) {
		this.dataList = dataList;
		this.tableName = tableName;
		index = -1;
	}

	/**
	 * @param data
	 *            �������ݼ�
	 * @param tableName
	 *            ��ģ���е�Name��Ӧ
	 */
	public MapMailMergeDataSource(Map<String, Object> data, String tableName) {
		if (this.dataList == null) {
			this.dataList = new ArrayList<Map<String, Object>>();
			this.dataList.add(data);
		}
		this.tableName = tableName;
		index = -1;
	}

	/**
	 * ��ȡ���������
	 * 
	 * @return
	 */
	private int getCount() {
		return this.dataList.size();
	}

	@Override
	public IMailMergeDataSource getChildDataSource(String arg0)
			throws Exception {
		return null;
	}

	@Override
	public String getTableName() throws Exception {
		return this.tableName;
	}

	/**
	 * ʵ�ֽӿ� ��ȡ��ǰindexָ�������е����� �����ݴ���args�����м���
	 * 
	 * @return ***����false�򲻰�����***
	 */
	@Override
	public boolean getValue(String key, Object[] args) throws Exception {
		if (index < 0 || index >= this.getCount()) {
			return false;
		}
		if (args != null && args.length > 0) {
			args[0] = this.dataList.get(index).get(key);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ʵ�ֽӿ� �ж��Ƿ�����һ����¼
	 */
	@Override
	public boolean moveNext() throws Exception {
		index += 1;
		if (index >= this.getCount()) {
			return false;
		}
		return true;
	}

}
