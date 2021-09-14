package com.insigma.siis.local.business.modeldb;

/****************
 * @category 查询语句生成器接口定义
 * @author 李小宁
 */
public interface ISelecter {
	public abstract String getSelecter(ModelDB db) throws Exception;

}
