package com.insigma.siis.local.business.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @Oracle工具生成实体
 * @author 徐亚涛(xuyatao@126.com)
 * @copytright xuyatao 2010-2015
 */
public class Cbdstatus implements Serializable {


    public Cbdstatus() {
    }

    private java.lang.String cbdid;
    private java.lang.String cbdstatusstep;
    private java.lang.String cbdstatustime;
    private java.lang.String statusid;

    public void setCbdid(final java.lang.String cbdid) {
        this.cbdid = cbdid;
    }

    public java.lang.String getCbdid() {
        return this.cbdid;
    }
   

    public void setCbdstatustime(final java.lang.String cbdstatustime) {
        this.cbdstatustime = cbdstatustime;
    }

    public java.lang.String getCbdstatustime() {
        return this.cbdstatustime;
    }

    public void setStatusid(final java.lang.String statusid) {
        this.statusid = statusid;
    }

    public java.lang.String getStatusid() {
        return this.statusid;
    }
    

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Statusid", getStatusid()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Cbdstatus)) {
            return false;
        }
        Cbdstatus castOther = (Cbdstatus) other;
        return new EqualsBuilder().append(this.getStatusid(),castOther.getStatusid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getStatusid()).toHashCode();
    }

	public java.lang.String getCbdstatusstep() {
		return cbdstatusstep;
	}

	public void setCbdstatusstep(java.lang.String cbdstatusstep) {
		this.cbdstatusstep = cbdstatusstep;
	}



}
