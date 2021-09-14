package com.insigma.siis.local.business.entity;

public class DHXJ implements java.io.Serializable{
	private String dhxjid;
	private String xjqy;
	private String hylx;
	private String hymc;
	private String filesize;
	private String filename;
	private String fileurl;
	private String hfilesize;
	private String hfilename;
	private String hfileurl;
	private String jfilesize;
	private String jfilename;
	private String jfileurl;
	private java.util.Date createdon;
	private String wcbz;
	private String sj;
	public DHXJ() {
		super();
		this.dhxjid = dhxjid;
		this.xjqy = xjqy;
		this.hylx = hylx;
		this.hymc = hymc;
		this.filesize = filesize;
		this.filename = filename;
		this.fileurl = fileurl;
		this.hfilesize = hfilesize;
		this.hfilename = hfilename;
		this.hfileurl = hfileurl;
		this.jfilesize = jfilesize;
		this.jfilename = jfilename;
		this.jfileurl = jfileurl;
		this.createdon = createdon;
		this.wcbz = wcbz;
		this.sj = sj;
	}
	
	public String getJfilesize() {
		return jfilesize;
	}

	public void setJfilesize(String jfilesize) {
		this.jfilesize = jfilesize;
	}

	public String getJfilename() {
		return jfilename;
	}

	public void setJfilename(String jfilename) {
		this.jfilename = jfilename;
	}

	public String getJfileurl() {
		return jfileurl;
	}

	public void setJfileurl(String jfileurl) {
		this.jfileurl = jfileurl;
	}

	public String getHfilesize() {
		return hfilesize;
	}

	public void setHfilesize(String hfilesize) {
		this.hfilesize = hfilesize;
	}

	public String getHfilename() {
		return hfilename;
	}

	public void setHfilename(String hfilename) {
		this.hfilename = hfilename;
	}

	public String getHfileurl() {
		return hfileurl;
	}

	public void setHfileurl(String hfileurl) {
		this.hfileurl = hfileurl;
	}
	

	public java.util.Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(java.util.Date createdon) {
		this.createdon = createdon;
	}

	public String getDhxjid() {
		return dhxjid;
	}
	public void setDhxjid(String dhxjid) {
		this.dhxjid = dhxjid;
	}
	public String getXjqy() {
		return xjqy;
	}
	public void setXjqy(String xjqy) {
		this.xjqy = xjqy;
	}
	public String getHylx() {
		return hylx;
	}
	public void setHylx(String hylx) {
		this.hylx = hylx;
	}
	public String getHymc() {
		return hymc;
	}
	public void setHymc(String hymc) {
		this.hymc = hymc;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileurl() {
		return fileurl;
	}
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	public String getWcbz() {
		return wcbz;
	}
	public void setWcbz(String wcbz) {
		this.wcbz = wcbz;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	
}
