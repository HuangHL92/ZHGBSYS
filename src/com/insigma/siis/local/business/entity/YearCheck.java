package com.insigma.siis.local.business.entity;


/**
 * Folder entity. @author MyEclipse Persistence Tools
 */
//出国附件表
public class YearCheck implements java.io.Serializable {

	// Fields

	private String id;
	private String b0111;		
	private String checkyear;			
	private String checktime;		
	private String checkfile;
	
	
	/** default constructor */
	public YearCheck() {
	}

	/** full constructor */
	public YearCheck(String id, String b0111, String checkyear, String checktime,String checkfile) {
		super();
		this.id = id;
		this.setB0111(b0111);
		this.setCheckyear(checkyear);
		this.setChecktime(checktime);
		this.setCheckfile(checkfile);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getB0111() {
		return b0111;
	}

	public void setB0111(String b0111) {
		this.b0111 = b0111;
	}

	public String getCheckyear() {
		return checkyear;
	}

	public void setCheckyear(String checkyear) {
		this.checkyear = checkyear;
	}

	public String getChecktime() {
		return checktime;
	}

	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}

	public String getCheckfile() {
		return checkfile;
	}

	public void setCheckfile(String checkfile) {
		this.checkfile = checkfile;
	}


	

}