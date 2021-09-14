package com.insigma.siis.local.business.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @Oracle��������ʵ��
 * @author ������(xuyatao@126.com)
 * @copytright xuyatao 2010-2015
 */
public class Reportftp implements Serializable {


    public Reportftp() {
    }
    private java.lang.String b0111;

    /**
     * �ļ���
     */
    private java.lang.String filename;

    public void setFilename(final java.lang.String filename) {
        this.filename = filename;
    }

    public java.lang.String getFilename() {
        return this.filename;
    }


    /**
     * ��ˮ��
     */
    private java.lang.String packageindex;

    public void setPackageindex(final java.lang.String packageindex) {
        this.packageindex = packageindex;
    }

    public java.lang.String getPackageindex() {
        return this.packageindex;
    }


    /**
     * ����
     */
    private java.lang.String packagename;

    public void setPackagename(final java.lang.String packagename) {
        this.packagename = packagename;
    }

    public java.lang.String getPackagename() {
        return this.packagename;
    }


    /**
     * �����ļ���
     */
    private java.lang.String packageret;

    public void setPackageret(final java.lang.String packageret) {
        this.packageret = packageret;
    }

    public java.lang.String getPackageret() {
        return this.packageret;
    }


    /**
     * ����ftp�û�id
     */
    private java.lang.String recieveftpuserid;

    public void setRecieveftpuserid(final java.lang.String recieveftpuserid) {
        this.recieveftpuserid = recieveftpuserid;
    }

    public java.lang.String getRecieveftpuserid() {
        return this.recieveftpuserid;
    }


    /**
     * ����ftp�û�״̬
     */
    private java.lang.String recieveftpusername;

    public void setRecieveftpusername(final java.lang.String recieveftpusername) {
        this.recieveftpusername = recieveftpusername;
    }

    public java.lang.String getRecieveftpusername() {
        return this.recieveftpusername;
    }


    /**
     * ���ջ���id
     */
    private java.lang.String recieveorg;

    public void setRecieveorg(final java.lang.String recieveorg) {
        this.recieveorg = recieveorg;
    }

    public java.lang.String getRecieveorg() {
        return this.recieveorg;
    }


    /**
     * ���ջ�������
     */
    private java.lang.String recieveorgname;

    public void setRecieveorgname(final java.lang.String recieveorgname) {
        this.recieveorgname = recieveorgname;
    }

    public java.lang.String getRecieveorgname() {
        return this.recieveorgname;
    }


    /**
     * �ϱ�id
     */
    private java.lang.String reportftpid;

    public void setReportftpid(final java.lang.String reportftpid) {
        this.reportftpid = reportftpid;
    }

    public java.lang.String getReportftpid() {
        return this.reportftpid;
    }

    /**
     * ״̬
     */
    private java.lang.String reportstatue;

    public void setReportstatue(final java.lang.String reportstatue) {
        this.reportstatue = reportstatue;
    }

    public java.lang.String getReportstatue() {
        return this.reportstatue;
    }
    /**
     * �ϱ�ʱ��
     */
    private java.sql.Timestamp reporttime;

    public void setReporttime(final java.sql.Timestamp reporttime) {
        this.reporttime = reporttime;
    }

    public java.sql.Timestamp getReporttime() {
        return this.reporttime;
    }


    /**
     * �ϱ�����
     */
    private java.lang.String reporttype;

    public void setReporttype(final java.lang.String reporttype) {
        this.reporttype = reporttype;
    }

    public java.lang.String getReporttype() {
        return this.reporttype;
    }


    /**
     * �ϱ��û�id
     */
    private java.lang.String reportuser;

    public void setReportuser(final java.lang.String reportuser) {
        this.reportuser = reportuser;
    }

    public java.lang.String getReportuser() {
        return this.reportuser;
    }


    /**
     * �û�����
     */
    private java.lang.String reportusername;

    public void setReportusername(final java.lang.String reportusername) {
        this.reportusername = reportusername;
    }

    public java.lang.String getReportusername() {
        return this.reportusername;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Reportftpid", getReportftpid()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Reportftp)) {
            return false;
        }
        Reportftp castOther = (Reportftp) other;
        return new EqualsBuilder().append(this.getReportftpid(),castOther.getReportftpid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getReportftpid()).toHashCode();
    }

	public java.lang.String getB0111() {
		return b0111;
	}

	public void setB0111(java.lang.String b0111) {
		this.b0111 = b0111;
	}



}
