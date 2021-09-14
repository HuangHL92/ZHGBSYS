package com.insigma.siis.local.business.entity;

public class A36_FILE {
	private String id;
	private String a0000;
	private String t_ime;
	private String filename;
	private String filepath;
	private String filetext;
	
	public A36_FILE(String id, String a0000, String t_ime, String filename, String filepath, String filetext) {
		super();
		this.id = id;
		this.a0000 = a0000;
		this.t_ime = t_ime;
		this.filename = filename;
		this.filepath = filepath;
		this.filetext = filetext;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getA0000() {
		return a0000;
	}

	public void setA0000(String a0000) {
		this.a0000 = a0000;
	}

	public String getT_ime() {
		return t_ime;
	}

	public void setT_ime(String t_ime) {
		this.t_ime = t_ime;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFiletext() {
		return filetext;
	}

	public void setFiletext(String filetext) {
		this.filetext = filetext;
	}

	public A36_FILE() {
		super();
	}

	public A36_FILE(String a0000, String t_ime, String filetext) {
		setA0000(a0000);
		setT_ime(t_ime);
		setFiletext(filetext);
	}

	@Override
	public String toString() {
		return "A36_FILE [id=" + id + ", a0000=" + a0000 + ", t_ime=" + t_ime + ", filename=" + filename
				+ ", filepath=" + filepath + ", filetext=" + filetext + "]";
	}
	
	
}
