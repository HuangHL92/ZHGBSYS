package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class QXSBZTP implements Serializable  {
	private String tp00;
	private String b0111;
	private String tpgw;
	private String tptj;
	private String tpry;
	public String getTp00() {
		return tp00;
	}
	public void setTp00(String tp00) {
		this.tp00 = tp00;
	}
	public String getB0111() {
		return b0111;
	}
	public void setB0111(String b0111) {
		this.b0111 = b0111;
	}
	public String getTpgw() {
		return tpgw;
	}
	public void setTpgw(String tpgw) {
		this.tpgw = tpgw;
	}
	public String getTptj() {
		return tptj;
	}
	public void setTptj(String tptj) {
		this.tptj = tptj;
	}
	public String getTpry() {
		return tpry;
	}
	public void setTpry(String tpry) {
		this.tpry = tpry;
	}
	@Override
	public String toString() {
		return "QXSBZTP [tp00=" + tp00 + ", b0111=" + b0111 + ", tpgw=" + tpgw + ", tptj=" + tptj + ", tpry=" + tpry
				+ "]";
	}
	public QXSBZTP(String tp00, String b0111, String tpgw, String tptj, String tpry) {
		super();
		this.tp00 = tp00;
		this.b0111 = b0111;
		this.tpgw = tpgw;
		this.tptj = tptj;
		this.tpry = tpry;
	}
	public QXSBZTP() {
		super();
	}
	
}
