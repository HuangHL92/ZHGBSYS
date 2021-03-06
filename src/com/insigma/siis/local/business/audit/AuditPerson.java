package com.insigma.siis.local.business.audit;
// Generated 2021-4-1 15:43:02 by Hibernate Tools 5.4.14.Final

import java.math.BigInteger;

/**
 * AuditPerson generated by hbm2java
 */
public class AuditPerson implements java.io.Serializable {

	private String oid;
	private String auditBatch;
	private Integer sortid;
	private String a0000;
	private String a0101;
	private String a0184;
	private String a0192a;

	public AuditPerson() {
	}

	public AuditPerson(String oid) {
		this.oid = oid;
	}

	public AuditPerson(String oid, String auditBatch, Integer sortid, String a0000, String a0101, String a0184,
			String a0192a) {
		this.oid = oid;
		this.auditBatch = auditBatch;
		this.sortid = sortid;
		this.a0000 = a0000;
		this.a0101 = a0101;
		this.a0184 = a0184;
		this.a0192a = a0192a;
	}

	public String getOid() {
		return this.oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getAuditBatch() {
		return this.auditBatch;
	}

	public void setAuditBatch(String auditBatch) {
		this.auditBatch = auditBatch;
	}

	public Integer getSortid() {
		return this.sortid;
	}

	public void setSortid(Integer sortid) {
		this.sortid = sortid;
	}

	public String getA0000() {
		return this.a0000;
	}

	public void setA0000(String a0000) {
		this.a0000 = a0000;
	}

	public String getA0101() {
		return this.a0101;
	}

	public void setA0101(String a0101) {
		this.a0101 = a0101;
	}

	public String getA0184() {
		return this.a0184;
	}

	public void setA0184(String a0184) {
		this.a0184 = a0184;
	}

	public String getA0192a() {
		return this.a0192a;
	}

	public void setA0192a(String a0192a) {
		this.a0192a = a0192a;
	}

}
