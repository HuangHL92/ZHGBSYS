 package com.insigma.siis.local.business.entity;

 import java.io.Serializable;

 /**
 * @Title ���ݿ����  TRAIN_ATT
 * @author lvyq 97354625@qq.com
 * @Description: ������HibernateTool�����Զ�����
 * @date 2019��1��14��
 * @version 1.0
 */
 public class TrainAtt implements Serializable {

	 /**
	 * ��Ƶ����Ƭ�ļ���ϢID
	 */
	 private String attid;


	 public String getAttid() {
	    return this.attid;
	 }
	 public void setAttid(final String attid) {
	    this.attid = attid;
	 }

	 /**
	 * ��ѵ�����ϢID
	 */
	 private String trainid;


	 public String getTrainid() {
	    return this.trainid;
	 }
	 public void setTrainid(final String trainid) {
	    this.trainid = trainid;
	 }

	 /**
	 * ��������
	 */
	 private String g11031;


	 public String getG11031() {
	    return this.g11031;
	 }
	 public void setG11031(final String g11031) {
	    this.g11031 = g11031;
	 }

	 /**
	 * ������ַ
	 */
	 private String g11052;


	 public String getG11052() {
	    return this.g11052;
	 }
	 public void setG11052(final String g11052) {
	    this.g11052 = g11052;
	 }

	 /**
	 * �ļ���С
	 */
	 private String g11053;


	 public String getG11053() {
	    return this.g11053;
	 }
	 public void setG11053(final String g11053) {
	    this.g11053 = g11053;
	 }

	 /**
	 * ������ID
	 */
	 private String userid;


	 public String getUserid() {
	    return this.userid;
	 }
	 public void setUserid(final String userid) {
	    this.userid = userid;
	 }

	 /**
	 * ����������
	 */
	 private String username;


	 public String getUsername() {
	    return this.username;
	 }
	 public void setUsername(final String username) {
	    this.username = username;
	 }

	 /**
	 * ����ʱ��
	 */
	 private String updatetime;


	 public String getUpdatetime() {
	    return this.updatetime;
	 }
	 public void setUpdatetime(final String updatetime) {
	    this.updatetime = updatetime;
	 }

 }
