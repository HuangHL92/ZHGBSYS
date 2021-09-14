 package com.insigma.siis.local.business.entity;

 import java.io.Serializable;

 /**
 * @Title 数据库表名  SP_BUS_LOG
 * @author lvyq 97354625@qq.com
 * @Description: 本类由HibernateTool工具自动生成
 * @date 2018年10月24日
 * @version 1.0
 */
 public class Sp_Bus_Log implements Serializable {
	 private String spbl07;
	 private String spbl08;
	 public String getSpbl08() {
		return spbl08;
	}
	public void setSpbl08(String spbl08) {
		this.spbl08 = spbl08;
	}
	public String getSpbl07() {
		return spbl07;
	}
	public void setSpbl07(String spbl07) {
		this.spbl07 = spbl07;
	}

	/**
	 * 主键
	 */
	 private java.lang.Long spbl00;


	 public java.lang.Long getSpbl00() {
	    return this.spbl00;
	 }
	 public void setSpbl00(final java.lang.Long spbl00) {
	    this.spbl00 = spbl00;
	 }

	 /**
	 * 流程主键
	 */
	 private java.lang.String spb00;


	 public java.lang.String getSpb00() {
	    return this.spb00;
	 }
	 public void setSpb00(final java.lang.String spb00) {
	    this.spb00 = spb00;
	 }

	 /**
	 * 操作人id
	 */
	 private java.lang.String spbl01;


	 public java.lang.String getSpbl01() {
	    return this.spbl01;
	 }
	 public void setSpbl01(final java.lang.String spbl01) {
	    this.spbl01 = spbl01;
	 }

	 /**
	 * 操作人姓名
	 */
	 private java.lang.String spbl02;


	 public java.lang.String getSpbl02() {
	    return this.spbl02;
	 }
	 public void setSpbl02(final java.lang.String spbl02) {
	    this.spbl02 = spbl02;
	 }

	 /**
	 * 操作机构
	 */
	 private java.lang.String spbl03;


	 public java.lang.String getSpbl03() {
	    return this.spbl03;
	 }
	 public void setSpbl03(final java.lang.String spbl03) {
	    this.spbl03 = spbl03;
	 }

	 /**
	 * 操作类型1登记 2送审 3审批通过 4审批不通过 5结束
	 */
	 private java.lang.String spbl04;


	 public java.lang.String getSpbl04() {
	    return this.spbl04;
	 }
	 public void setSpbl04(final java.lang.String spbl04) {
	    this.spbl04 = spbl04;
	 }

	 /**
	 * 操作时间
	 */
	 private java.util.Date spbl05;


	 public java.util.Date getSpbl05() {
	    return this.spbl05;
	 }
	 public void setSpbl05(final java.util.Date spbl05) {
	    this.spbl05 = spbl05;
	 }

	 /**
	 * 描述
	 */
	 private java.lang.String spbl06;


	 public java.lang.String getSpbl06() {
	    return this.spbl06;
	 }
	 public void setSpbl06(final java.lang.String spbl06) {
	    this.spbl06 = spbl06;
	 }

 }
