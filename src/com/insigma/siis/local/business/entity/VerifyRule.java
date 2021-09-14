package com.insigma.siis.local.business.entity;


/**
 * VerifyRule entity. @author MyEclipse Persistence Tools
 */

public class VerifyRule implements java.io.Serializable {

	// Fields

	private String vru001;// VSC001	校验方案编码识别一组校验；外键（关联verify_scheme，verify_scheme与verify_rule一对多）
	private String vsc001;// VRU001	校验项编码主键；
	private String vru002;// VRU002	校验项名称默认：(表名)_(字段名)（校验类型）；可修改
	private String vru003;// VRU003	类别0-无，1-错误，2-警告，3-提示
	private String vru004;// VRU004	主校验表代码
	private String vru005;// VRU005	主校验字段代码
	private String vru006;// VRU006	拼接完成的SQL中的主表
	private String vru007;// VRU007	有效（启用）标记::默认：0；0-无效(未启用)，1-有效（已启用）
	private String vru008;// VRU008	返回信息（校验类别）：未通过（校验类型）_（校验项名称）
	private String vru009;// VRU009	拼接完成的sql语句

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