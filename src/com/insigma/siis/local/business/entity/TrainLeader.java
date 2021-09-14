 package com.insigma.siis.local.business.entity;

 import java.io.Serializable;
import java.math.BigDecimal;

 /**
 * @Title 数据库表名  TRAIN_LEADER
 * @author lvyq 97354625@qq.com
 * @Description: 本类由HibernateTool工具自动生成
 * @date 2018年12月28日
 * @version 1.0
 */
 public class TrainLeader implements Serializable {

	 /**
	 * 领导干部上讲台信息ID
	 */
	 private String leacerid;


	 public String getLeacerid() {
	    return this.leacerid;
	 }
	 public void setLeacerid(final String leacerid) {
	    this.leacerid = leacerid;
	 }

	 /**
	 * 培训班次信息ID
	 */
	 private String trainid;


	 public String getTrainid() {
	    return this.trainid;
	 }
	 public void setTrainid(final String trainid) {
	    this.trainid = trainid;
	 }

	 /**
	 * 领导姓名
	 */
	 private String a0101;


	 public String getA0101() {
	    return this.a0101;
	 }
	 public void setA0101(final String a0101) {
	    this.a0101 = a0101;
	 }

	 /**
	 * 领导性别
	 */
	 private String a0104;


	 public String getA0104() {
	    return this.a0104;
	 }
	 public void setA0104(final String a0104) {
	    this.a0104 = a0104;
	 }

	 /**
	 * 领导公民身份号码
	 */
	 private String a0184;


	 public String getA0184() {
	    return this.a0184;
	 }
	 public void setA0184(final String a0184) {
	    this.a0184 = a0184;
	 }

	 /**
	 * 领导现工作单位及职务全称 
	 */
	 private String a0192a;


	 public String getA0192a() {
	    return this.a0192a;
	 }
	 public void setA0192a(final String a0192a) {
	    this.a0192a = a0192a;
	 }

	 /**
	 * 培训年度
	 */
	 private BigDecimal g11020;


	 public BigDecimal getG11020() {
	    return this.g11020;
	 }
	 public void setG11020(final BigDecimal g11020) {
	    this.g11020 = g11020;
	 }

	 /**
	 * 上讲台时间
	 */
	 private String g11037;


	 public String getG11037() {
	    return this.g11037;
	 }
	 public void setG11037(final String g11037) {
	    this.g11037 = g11037;
	 }

	 /**
	 * 讲课题目
	 */
	 private String g11038;


	 public String getG11038() {
	    return this.g11038;
	 }
	 public void setG11038(final String g11038) {
	    this.g11038 = g11038;
	 }

	 /**
	 * 培训时长
	 */
	 private BigDecimal a1107c;


	 public BigDecimal getA1107c() {
	    return this.a1107c;
	 }
	 public void setA1107c(final BigDecimal a1107c) {
	    this.a1107c = a1107c;
	 }

	 /**
	 * 获得学时数
	 */
	 private BigDecimal a1108;


	 public BigDecimal getA1108() {
	    return this.a1108;
	 }
	 public void setA1108(final BigDecimal a1108) {
	    this.a1108 = a1108;
	 }

	 /**
	 * 操作人ID
	 */
	 private String userid;


	 public String getUserid() {
	    return this.userid;
	 }
	 public void setUserid(final String userid) {
	    this.userid = userid;
	 }

	 /**
	 * 操作人名称
	 */
	 private String username;


	 public String getUsername() {
	    return this.username;
	 }
	 public void setUsername(final String username) {
	    this.username = username;
	 }

	 /**
	 * 更新时间
	 */
	 private String updatetime;


	 public String getUpdatetime() {
	    return this.updatetime;
	 }
	 public void setUpdatetime(final String updatetime) {
	    this.updatetime = updatetime;
	 }

	 /**
	 * 现任职务层次
	 */
	 private String g11027;


	 public String getG11027() {
	    return this.g11027;
	 }
	 public void setG11027(final String g11027) {
	    this.g11027 = g11027;
	 }
	 
	 /**
	 * 现任单位编码
	 */
	 private String a0177;


	 public String getA0177() {
	    return this.a0177;
	 }
	 public void setA0177(final String a0177) {
	    this.a0177 = a0177;
	 }
	 
	 /**
	 * 是否市管干部(是1，否0)
	 */
	 private String a0199;


	 public String getA0199() {
	    return this.a0199;
	 }
	 public void setA0199(final String a0199) {
	    this.a0199 = a0199;
	 }
	 public void setA1107c(Long a1107c) {
			// TODO Auto-generated method stub
			this.a1107c = new BigDecimal(a1107c);
		}
	public void setA1108(Long a1108) {
		// TODO Auto-generated method stub
		this.a1108 = new BigDecimal(a1108);
		
	}
 }

