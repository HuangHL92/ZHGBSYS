package com.insigma.siis.local.business.entity;

import java.io.Serializable;

public class DBWY implements Serializable {
	private String id;
	private String a0000;
	private String rank;
	private String dbwy;
	private String xqjb;
	private String rzsj;
	private String mzsj;
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
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getDbwy() {
		return dbwy;
	}
	public void setDbwy(String dbwy) {
		this.dbwy = dbwy;
	}
	public String getXqjb() {
		return xqjb;
	}
	public void setXqjb(String xqjb) {
		this.xqjb = xqjb;
	}
	public String getRzsj() {
		return rzsj;
	}
	public void setRzsj(String rzsj) {
		this.rzsj = rzsj;
	}
	public String getMzsj() {
		return mzsj;
	}
	public void setMzsj(String mzsj) {
		this.mzsj = mzsj;
	}
	@Override
	public String toString() {
		String dbwystr="";
		String rank=this.rank;
		String db=this.dbwy;
		if("1".equals(rank)) {
			dbwystr=dbwystr+"����";
		}else if("2".equals(rank)) {
			dbwystr=dbwystr+"ʡ";
		}else if("3".equals(rank)) {
			dbwystr=dbwystr+"��";
		}else if("4".equals(rank)) {
			dbwystr=dbwystr+"������";
		}
		if("1".equals(db)) {
			dbwystr=dbwystr+"�˴����";
		}else if("2".equals(db)) {
			dbwystr=dbwystr+"������";
		}else if("3".equals(db)) {
			dbwystr=dbwystr+"��ЭίԱ";
		}else if("4".equals(db)) {
			dbwystr=dbwystr+"��ίίԱ";
		}else if("5".equals(db)) {
			dbwystr=dbwystr+"�˴�ί";
		}
		return dbwystr;
	}
//	@Override
//	public String toString() {
//		return "DBWY [id=" + id + ", a0000=" + a0000 + ", rank=" + rank + ", dbwy=" + dbwy + ", xqjb=" + xqjb
//				+ ", rzsj=" + rzsj + ", mzsj=" + mzsj + "]";
//	}
	
	
}
