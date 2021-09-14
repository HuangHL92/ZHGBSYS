package com.insigma.siis.local.business.entity;

public class GbkcFile implements java.io.Serializable{
	private String id;
	private String filename;
	private String fileurl;
	private String filesize;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	@Override
	public String toString() {
		return "GbkcFile [id=" + id + ", filename=" + filename + ", fileurl=" + fileurl + ", filesize=" + filesize
				+ "]";
	}
	

}
