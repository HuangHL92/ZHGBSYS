 package com.insigma.siis.local.business.entity;

 import java.io.Serializable;

 /**
 * @Title ���ݿ����  TRAIN_UNIT
 * @author lvyq 97354625@qq.com
 * @Description: ������HibernateTool�����Զ�����
 * @date 2019��1��8��
 * @version 1.0
 */
 public class TrainUnit implements Serializable {

	 /**
	 * ����
	 */
	 private String id;


	 public String getId() {
	    return this.id;
	 }
	 public void setId(final String id) {
	    this.id = id;
	 }
	 /**
	 * ��λ��������
	 */
	 private String unitid;


	 public String getUnitid() {
	    return this.unitid;
	 }
	 public void setUnitid(final String unitid) {
	    this.unitid = unitid;
	 }

	 /**
	 * ��λ����
	 */
	 private String unitname;


	 public String getUnitname() {
	    return this.unitname;
	 }
	 public void setUnitname(final String unitname) {
	    this.unitname = unitname;
	 }

	 /**
	 * ��λ����(�йܸɲ�(��ְ))
	 */
	 private java.math.BigDecimal pnum1;


	 public java.math.BigDecimal getPnum1() {
	    return this.pnum1;
	 }
	 public void setPnum1(final java.math.BigDecimal pnum1) {
	    this.pnum1 = pnum1;
	 }

	 /**
	 * ��λ����(�йܸɲ�(��ְ))
	 */
	 private java.math.BigDecimal pnum2;


	 public java.math.BigDecimal getPnum2() {
	    return this.pnum2;
	 }
	 public void setPnum2(final java.math.BigDecimal pnum2) {
	    this.pnum2 = pnum2;
	 }
	 
	 /**
	 * ��λ����(�����ɲ�(��ְ))
	 */
	 private java.math.BigDecimal pnum3;


	 public java.math.BigDecimal getPnum3() {
	    return this.pnum3;
	 }
	 public void setPnum3(final java.math.BigDecimal pnum3) {
	    this.pnum3 = pnum3;
	 }
	 
	 /**
	 * ��λ����(�����ɲ�(��ְ))
	 */
	 private java.math.BigDecimal pnum4;


	 public java.math.BigDecimal getPnum4() {
	    return this.pnum4;
	 }
	 public void setPnum4(final java.math.BigDecimal pnum4) {
	    this.pnum4 = pnum4;
	 }
	 
	 /**
	 * ��λ����(�Ƽ�������)
	 */
	 private java.math.BigDecimal pnum5;


	 public java.math.BigDecimal getPnum5() {
	    return this.pnum5;
	 }
	 public void setPnum5(final java.math.BigDecimal pnum5) {
	    this.pnum5 = pnum5;
	 }
	 
	 /**
	 * ���
	 */
	 private java.math.BigDecimal g11020;


	 public java.math.BigDecimal getG11020() {
	    return this.g11020;
	 }
	 public void setG11020(final java.math.BigDecimal g11020) {
	    this.g11020 = g11020;
	 }

 }
