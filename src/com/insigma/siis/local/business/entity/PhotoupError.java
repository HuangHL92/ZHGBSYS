package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class PhotoupError implements Serializable{
	private String per001;//����
	private String per002;//������Ա���֤
	private String per003;//��������
	private String per004;//������ʱ��
	private String per005;//�û�ID
	public PhotoupError(){
		
	}
	public PhotoupError(String per001, String per002, String per003,
			String per004, String per005) {
		super();
		this.per001 = per001;
		this.per002 = per002;
		this.per003 = per003;
		this.per004 = per004;
		this.per005 = per005;
	}
	public String getPer001() {
		return per001;
	}
	public void setPer001(String per001) {
		this.per001 = per001;
	}
	public String getPer002() {
		return per002;
	}
	public void setPer002(String per002) {
		this.per002 = per002;
	}
	public String getPer003() {
		return per003;
	}
	public void setPer003(String per003) {
		this.per003 = per003;
	}
	public String getPer004() {
		return per004;
	}
	public void setPer004(String per004) {
		this.per004 = per004;
	}
	public String getPer005() {
		return per005;
	}
	public void setPer005(String per005) {
		this.per005 = per005;
	}
	
	
}

