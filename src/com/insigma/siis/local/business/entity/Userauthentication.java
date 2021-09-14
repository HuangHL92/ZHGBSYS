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
public class Userauthentication implements Serializable {


    public Userauthentication() {
    }


    /**
     * 
     */
    private java.lang.String token;

    public void setToken(final java.lang.String token) {
        this.token = token;
    }

    public java.lang.String getToken() {
        return this.token;
    }


    /**
     * 
     */
    private java.lang.String username;

    public void setUsername(final java.lang.String username) {
        this.username = username;
    }

    public java.lang.String getUsername() {
        return this.username;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Token", getToken()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Userauthentication)) {
            return false;
        }
        Userauthentication castOther = (Userauthentication) other;
        return new EqualsBuilder().append(this.getToken(),castOther.getToken()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getToken()).toHashCode();
    }



}
