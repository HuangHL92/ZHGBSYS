package com.insigma.siis.local.business.entity;


/**
 * VerifyRule entity. @author MyEclipse Persistence Tools
 */

public class VerifyRule implements java.io.Serializable {

	// Fields

	private String vru001;// VSC001	У�鷽������ʶ��һ��У�飻���������verify_scheme��verify_scheme��verify_ruleһ�Զࣩ
	private String vsc001;// VRU001	У�������������
	private String vru002;// VRU002	У��������Ĭ�ϣ�(����)_(�ֶ���)��У�����ͣ������޸�
	private String vru003;// VRU003	���0-�ޣ�1-����2-���棬3-��ʾ
	private String vru004;// VRU004	��У������
	private String vru005;// VRU005	��У���ֶδ���
	private String vru006;// VRU006	ƴ����ɵ�SQL�е�����
	private String vru007;// VRU007	��Ч�����ã����::Ĭ�ϣ�0��0-��Ч(δ����)��1-��Ч�������ã�
	private String vru008;// VRU008	������Ϣ��У����𣩣�δͨ����У�����ͣ�_��У�������ƣ�
	private String vru009;// VRU009	ƴ����ɵ�sql���

	// Constructors

	/** default constructor */
	public VerifyRule() {
	}

	/** minimal constructor */
	public VerifyRule(String vru001) {
		this.vru001 = vru001;
	}

	/** full constructor */
	public VerifyRule(String vru001, String vsc001, String vru002,
			String vru003, String vru004, String vru005, String vru006,
			String vru007, String vru008, String vru009) {
		this.vru001 = vru001;
		this.vsc001 = vsc001;
		this.vru002 = vru002;
		this.vru003 = vru003;
		this.vru004 = vru004;
		this.vru005 = vru005;
		this.vru006 = vru006;
		this.vru007 = vru007;
		this.vru008 = vru008;
		this.vru009 = vru009;
	}

	// Property accessors

	public String getVru001() {
		return this.vru001;
	}

	public void setVru001(String vru001) {
		this.vru001 = vru001;
	}

	public String getVsc001() {
		return this.vsc001;
	}

	public void setVsc001(String vsc001) {
		this.vsc001 = vsc001;
	}

	public String getVru002() {
		return this.vru002;
	}

	public void setVru002(String vru002) {
		this.vru002 = vru002;
	}

	public String getVru003() {
		return this.vru003;
	}

	public void setVru003(String vru003) {
		this.vru003 = vru003;
	}

	public String getVru004() {
		return this.vru004;
	}

	public void setVru004(String vru004) {
		this.vru004 = vru004;
	}

	public String getVru005() {
		return this.vru005;
	}

	public void setVru005(String vru005) {
		this.vru005 = vru005;
	}

	public String getVru006() {
		return this.vru006;
	}

	public void setVru006(String vru006) {
		this.vru006 = vru006;
	}

	public String getVru007() {
		return this.vru007;
	}

	public void setVru007(String vru007) {
		this.vru007 = vru007;
	}

	public String getVru008() {
		return this.vru008;
	}

	public void setVru008(String vru008) {
		this.vru008 = vru008;
	}

	public String getVru009() {
		return this.vru009;
	}

	public void setVru009(String vru009) {
		this.vru009 = vru009;
	}

}