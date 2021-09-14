 package com.insigma.siis.local.business.entity;

 import java.io.Serializable;

 /**
 * @Title 数据库表名  TRAIN_ATT
 * @author lvyq 97354625@qq.com
 * @Description: 本类由HibernateTool工具自动生成
 * @date 2019年1月14日
 * @version 1.0
 */
 public class TrainAtt implements Serializable {

	 /**
	 * 视频、照片文件信息ID
	 */
	 private String attid;


	 public String getAttid() {
	    return this.attid;
	 }
	 public void setAttid(final String attid) {
	    this.attid = attid;
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
	 * 附件名称
	 */
	 private String g11031;


	 public String getG11031() {
	    return this.g11031;
	 }
	 public void setG11031(final String g11031) {
	    this.g11031 = g11031;
	 }

	 /**
	 * 附件地址
	 */
	 private String g11052;


	 public String getG11052() {
	    return this.g11052;
	 }
	 public void setG11052(final String g11052) {
	    this.g11052 = g11052;
	 }

	 /**
	 * 文件大小
	 */
	 private String g11053;


	 public String getG11053() {
	    return this.g11053;
	 }
	 public void setG11053(final String g11053) {
	    this.g11053 = g11053;
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

 }
