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
public class Sysmodelrole implements Serializable {


    public Sysmodelrole() {
    }

    
    public Sysmodelrole(String modelroleid, String sublibrariesmodelid,
			String userid) {
		super();
		this.modelroleid = modelroleid;
		this.sublibrariesmodelid = sublibrariesmodelid;
		this.userid = userid;
	}


	/**
     * 模型指标唯一标识符
     */
    private java.lang.String modelroleid;

    public void setModelroleid(final java.lang.String modelroleid) {
        this.modelroleid = modelroleid;
    }

    public java.lang.String getModelroleid() {
        return this.modelroleid;
    }


    /**
     * 模型id
     */
    private java.lang.String sublibrariesmodelid;

    public void setSublibrariesmodelid(final java.lang.String sublibrariesmodelid) {
        this.sublibrariesmodelid = sublibrariesmodelid;
    }

    public java.lang.String getSublibrariesmodelid() {
        return this.sublibrariesmodelid;
    }


    /**
     * 用户id
     */
    private java.lang.String userid;

    public void setUserid(final java.lang.String userid) {
        this.userid = userid;
    }

    public java.lang.String getUserid() {
        return this.userid;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Modelroleid", getModelroleid()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Sysmodelrole)) {
            return false;
        }
        Sysmodelrole castOther = (Sysmodelrole) other;
        return new EqualsBuilder().append(this.getModelroleid(),castOther.getModelroleid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getModelroleid()).toHashCode();
    }



}
