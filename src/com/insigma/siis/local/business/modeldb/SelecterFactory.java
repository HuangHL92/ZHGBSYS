package com.insigma.siis.local.business.modeldb;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
/******************'
 * @category ��ѯ��䴴����������  ���ݲ�ͬ�����ݿ����ʹ�����ͬ������������
 * @author ��С��
 */
public class SelecterFactory {
	public static ISelecter createSelecter(DBUtil.DBType which){
		if(which==DBType.ORACLE)
			return new OracleSelecter();
		if(which==DBType.MYSQL)
			return new MysqlSelecter();
		return new OracleSelecter();
	} 
}
