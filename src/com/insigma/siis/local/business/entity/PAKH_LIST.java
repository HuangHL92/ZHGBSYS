package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class PAKH_LIST implements Serializable {
	private String pl000;
	private String pl001;
	private String pl002;
	private String pts00;
	private String pl004;
	private String pl005;
	public String getPl000() {
		return pl000;
	}
	public void setPl000(String pl000) {
		this.pl000 = pl000;
	}
	public String getPl001() {
		return pl001;
	}
	public void setPl001(String pl001) {
		this.pl001 = pl001;
	}
	public String getPl002() {
		return pl002;
	}
	public void setPl002(String pl002) {
		this.pl002 = pl002;
	}
	public String getPts00() {
		return pts00;
	}
	public void setPts00(String pts00) {
		this.pts00 = pts00;
	}
	public String getPl004() {
		return pl004;
	}
	public void setPl004(String pl004) {
		this.pl004 = pl004;
	}
	public String getPl005() {
		return pl005;
	}
	public void setPl005(String pl005) {
		this.pl005 = pl005;
	}
	@Override
	public String toString() {
		return "PAKH_LIST [pl000=" + pl000 + ", pl001=" + pl001 + ", pl002=" + pl002 + ", pts00=" + pts00 + ", pl004="
				+ pl004 + ", pl005=" + pl005 + "]";
	}
	

}
