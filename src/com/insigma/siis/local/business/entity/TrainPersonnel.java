 package com.insigma.siis.local.business.entity;

 import java.io.Serializable;
import java.math.BigDecimal;

 /**
 * @Title 数据库表名  TRAIN_PERSONNEL
 * @author lvyq 97354625@qq.com
 * @Description: 本类由HibernateTool工具自动生成
 * @date 2018年12月27日
 * @version 1.0
 */
 public class TrainPersonnel implements Serializable {

	 /**
	 * 培训学员名单信息ID
	 */
	 private String personnelid;


	 public String getPersonnelid() {
	    return this.personnelid;
	 }
	 public void setPersonnelid(final String personnelid) {
	    this.personnelid = personnelid;
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
	 * 姓名
	 */
	 private String a0101;


	 public String getA0101() {
	    return this.a0101;
	 }
	 public void setA0101(final String a0101) {
	    this.a0101 = a0101;
	 }

	 /**
	 * 性别
	 */
	 private String a0104;


	 public String getA0104() {
	    return this.a0104;
	 }
	 public void setA0104(final String a0104) {
	    this.a0104 = a0104;
	 }

	 /**
	 * 公民身份号码
	 */
	 private String a0184;


	 public String getA0184() {
	    return this.a0184;
	 }
	 public void setA0184(final String a0184) {
	    this.a0184 = a0184;
	 }

	 /**
	 * 现工作单位及职务全称 
	 */
	 private String a0192a;


	 public String getA0192a() {
	    return this.a0192a;
	 }
	 public void setA0192a(final String a0192a) {
	    this.a0192a = a0192a;
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
	 * 是否班组长(是1，否0)
	 */
	 private String g11028;


	 public String getG11028() {
	    return this.g11028;
	 }
	 public void setG11028(final String g11028) {
	    this.g11028 = g11028;
	 }

	 /**
	 * 是否优秀学员(是1，否0)
	 */
	 private String g11029;


	 public String getG11029() {
	    return this.g11029;
	 }
	 public void setG11029(final String g11029) {
	    this.g11029 = g11029;
	 }

	 /**
	 * 学员考勤信息
	 */
	 private String g11032;


	 public String getG11032() {
	    return this.g11032;
	 }
	 public void setG11032(final String g11032) {
	    this.g11032 = g11032;
	 }

	 /**
	 * 学员培训小结信息
	 */
	 private String g02003;


	 public String getG02003() {
	    return this.g02003;
	 }
	 public void setG02003(final String g02003) {
	    this.g02003 = g02003;
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
	 * 培训时长(可以不用)
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
	public void setA1108(Long a1108) {
		// TODO Auto-generated method stub
		
		this.a1108 = new BigDecimal(a1108);
		
	}
 }
