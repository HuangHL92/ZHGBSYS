 package com.insigma.siis.local.business.entity;

 import java.io.Serializable;

 /**
 * @Title 数据库表名  PERCENTAGE
 * @author lvyq 97354625@qq.com
 * @Description: 本类由HibernateTool工具自动生成
 * @date 2020年2月19日
 * @version 1.0
 */
 public class Percentage implements Serializable {

	 /**
	 * 
	 */
	 private java.lang.String ids;


	 public java.lang.String getIds() {
	    return this.ids;
	 }
	 public void setIds(final java.lang.String ids) {
	    this.ids = ids;
	 }

	 /**
	 * 
	 */
	 private java.lang.String a0000;


	 public java.lang.String getA0000() {
	    return this.a0000;
	 }
	 public void setA0000(final java.lang.String a0000) {
	    this.a0000 = a0000;
	 }

	 /**
	 * 百分比
	 */
	 private java.lang.String percentage_number;
	 
	 

	public java.lang.String getPercentage_number() {
		return percentage_number;
	}
	public void setPercentage_number(java.lang.String percentage_number) {
		this.percentage_number = percentage_number;
	}

	private java.lang.String sessionID;


	public java.lang.String getSessionID() {
		return sessionID;
	}
	public void setSessionID(java.lang.String sessionID) {
		this.sessionID = sessionID;
	}
	

 }
