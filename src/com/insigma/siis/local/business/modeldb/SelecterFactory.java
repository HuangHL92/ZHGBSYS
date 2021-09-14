package com.insigma.siis.local.business.modeldb;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
/******************'
 * @category 查询语句创建器工厂类  根据不同的数据库类型创建不同的生成器对象
 * @author 李小宁
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
