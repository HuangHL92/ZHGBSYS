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
public class Fptconfig implements Serializable {


    public Fptconfig() {
    }


    /**
     * 外加
     */
    private java.lang.String addtionfield;

    public void setAddtionfield(final java.lang.String addtionfield) {
        this.addtionfield = addtionfield;
    }

    public java.lang.String getAddtionfield() {
        return this.addtionfield;
    }


    /**
     * 本地备份文件根目录
     */
    private java.lang.String backupfile;

    public void setBackupfile(final java.lang.String backupfile) {
        this.backupfile = backupfile;
    }

    public java.lang.String getBackupfile() {
        return this.backupfile;
    }


    /**
     * ID
     */
    private java.lang.String fptconfigid;

    public void setFptconfigid(final java.lang.String fptconfigid) {
        this.fptconfigid = fptconfigid;
    }

    public java.lang.String getFptconfigid() {
        return this.fptconfigid;
    }


    /**
     * 本地数据存放根目录
     */
    private java.lang.String localfile;

    public void setLocalfile(final java.lang.String localfile) {
        this.localfile = localfile;
    }

    public java.lang.String getLocalfile() {
        return this.localfile;
    }


    /**
     * 本系统FTP服务根目录
     */
    private java.lang.String serverbaseurl;

    public void setServerbaseurl(final java.lang.String serverbaseurl) {
        this.serverbaseurl = serverbaseurl;
    }

    public java.lang.String getServerbaseurl() {
        return this.serverbaseurl;
    }


    /**
     * 本系统FTP服务端口
     */
    private java.lang.String serverport;

    public void setServerport(final java.lang.String serverport) {
        this.serverport = serverport;
    }

    public java.lang.String getServerport() {
        return this.serverport;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Fptconfigid", getFptconfigid()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Fptconfig)) {
            return false;
        }
        Fptconfig castOther = (Fptconfig) other;
        return new EqualsBuilder().append(this.getFptconfigid(),castOther.getFptconfigid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getFptconfigid()).toHashCode();
    }



}
