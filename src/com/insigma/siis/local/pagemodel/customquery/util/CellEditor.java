package com.insigma.siis.local.pagemodel.customquery.util;

import org.apache.poi.ss.usermodel.Cell;

/**
 * ��Ԫ��༭���ӿ�
 * @author Looly
 *
 */
public interface CellEditor {
	/**
	 * �༭
	 * @param cell ��Ԫ����󣬿��Ի�ȡ��Ԫ���С�����ʽ����Ϣ
	 * @param value ��Ԫ��ֵ
	 * @return �༭��Ķ���
	 */
	Object edit(Cell cell, Object value);
}
