 package com.insigma.siis.local.business.entity;

 import java.io.Serializable;
import java.math.BigDecimal;

 /**
 * @Title ���ݿ����  TRAIN_SCORE
 * @author lvyq 97354625@qq.com
 * @Description: ������HibernateTool�����Զ�����
 * @date 2018��12��29��
 * @version 1.0
 */
 public class TrainScore implements Serializable {

	 /**
	 * ѧԱ���Գɼ���Ϣ
	 */
	 private String scoreid;


	 public String getScoreid() {
	    return this.scoreid;
	 }
	 public void setScoreid(final String scoreid) {
	    this.scoreid = scoreid;
	 }

	 /**
	 * ��ѵѧԱ������ϢID
	 */
	 private String personnelid;


	 public String getPersonnelid() {
	    return this.personnelid;
	 }
	 public void setPersonnelid(final String personnelid) {
	    this.personnelid = personnelid;
	 }

	 /**
	 * ��ѵ���
	 */
	 private BigDecimal g11020;


	 public BigDecimal getG11020() {
	    return this.g11020;
	 }
	 public void setG11020(final BigDecimal g11020) {
	    this.g11020 = g11020;
	 }

	 /**
	 * ������𣨵��ڷ��濼��1��ѧ���÷�����2�����ۿ���3�������й��쵼�ɲ�����֪ʶ����4��
	 */
	 private String g11039;


	 public String getG11039() {
	    return this.g11039;
	 }
	 public void setG11039(final String g11039) {
	    this.g11039 = g11039;
	 }

	 /**
	 * ����ʱ��
	 */
	 private String g11040;


	 public String getG11040() {
	    return this.g11040;
	 }
	 public void setG11040(final String g11040) {
	    this.g11040 = g11040;
	 }

	 /**
	 * ���Գɼ�
	 */
	 private BigDecimal g11041;


	 public BigDecimal getG11041() {
	    return this.g11041;
	 }
	 public void setG11041(final BigDecimal g11041) {
	    this.g11041 = g11041;
	 }

	 /**
	 * ���ѧʱ��
	 */
	 private BigDecimal a1108;


	 public BigDecimal getA1108() {
	    return this.a1108;
	 }
	 public void setA1108(final BigDecimal a1108) {
	    this.a1108 = a1108;
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

	 /**
	 * ����
	 */
	 private String a0101;


	 public String getA0101() {
	    return this.a0101;
	 }
	 public void setA0101(final String a0101) {
	    this.a0101 = a0101;
	 }

	 /**
	 * �Ա�
	 */
	 private String a0104;


	 public String getA0104() {
	    return this.a0104;
	 }
	 public void setA0104(final String a0104) {
	    this.a0104 = a0104;
	 }

	 /**
	 * ���֤��
	 */
	 private String a0184;


	 public String getA0184() {
	    return this.a0184;
	 }
	 public void setA0184(final String a0184) {
	    this.a0184 = a0184;
	 }
	 /**
	 * ����ְ����
	 */
	 private String g11027;


	 public String getG11027() {
	    return this.g11027;
	 }
	 public void setG11027(final String g11027) {
	    this.g11027 = g11027;
	 }
	 
	 /**
	 * �ֹ�����λ��ְ��ȫ��
	 */
	 private String a0192a;


	 public String getA0192a() {
	    return this.a0192a;
	 }
	 public void setA0192a(final String a0192a) {
	    this.a0192a = a0192a;
	 }
	 /**
	 * ���ε�λ����
	 */
	 private String a0177;


	 public String getA0177() {
	    return this.a0177;
	 }
	 public void setA0177(final String a0177) {
	    this.a0177 = a0177;
	 }
	 /**
	 * �Ƿ��йܸɲ�(��1����0)
	 */
	 private String a0199;


	 public String getA0199() {
	    return this.a0199;
	 }
	 public void setA0199(final String a0199) {
	    this.a0199 = a0199;
	 }
	public void setG11020(Long g11020) {
		// TODO Auto-generated method stub
		this.g11020 = new BigDecimal(g11020);
		
		
	}
	public void setG11041(Long g11041) {
		// TODO Auto-generated method stub
		this.g11041 = new BigDecimal(g11041);
		
	}
	public void setA1108(Long a1108) {
		this.a1108 = new BigDecimal(a1108);
		// TODO Auto-generated method stub
		
	}
 }
