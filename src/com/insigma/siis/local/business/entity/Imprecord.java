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
public class Imprecord implements Serializable {


    public Imprecord() {
    }


    /**
     * ��λ(����)����
     */
    private java.lang.String b0114;

    public void setB0114(final java.lang.String b0114) {
        this.b0114 = b0114;
    }

    public java.lang.String getB0114() {
        return this.b0114;
    }


    /**
     * ���˵�λ��ʶ
     */
    private java.lang.String b0194;

    public void setB0194(final java.lang.String b0194) {
        this.b0194 = b0194;
    }

    public java.lang.String getB0194() {
        return this.b0194;
    }


    /**
     * ���ݰ汾
     */
    private java.lang.String dataverion;

    public void setDataverion(final java.lang.String dataverion) {
        this.dataverion = dataverion;
    }

    public java.lang.String getDataverion() {
        return this.dataverion;
    }


    /**
     * ��������id
     */
    private java.lang.String empdeptid;

    public void setEmpdeptid(final java.lang.String empdeptid) {
        this.empdeptid = empdeptid;
    }

    public java.lang.String getEmpdeptid() {
        return this.empdeptid;
    }


    /**
     * ������������
     */
    private java.lang.String empdeptname;

    public void setEmpdeptname(final java.lang.String empdeptname) {
        this.empdeptname = empdeptname;
    }

    public java.lang.String getEmpdeptname() {
        return this.empdeptname;
    }


    /**
     * �ļ�����
     */
    private java.lang.String filename;

    public void setFilename(final java.lang.String filename) {
        this.filename = filename;
    }

    public java.lang.String getFilename() {
        return this.filename;
    }


    /**
     * �ļ�����
     */
    private java.lang.String filetype;

    public void setFiletype(final java.lang.String filetype) {
        this.filetype = filetype;
    }

    public java.lang.String getFiletype() {
        return this.filetype;
    }


    /**
     * �������id
     */
    private java.lang.String impdeptid;

    public void setImpdeptid(final java.lang.String impdeptid) {
        this.impdeptid = impdeptid;
    }

    public java.lang.String getImpdeptid() {
        return this.impdeptid;
    }


    /**
     * ���������
     */
    private java.lang.String impgroupid;

    public void setImpgroupid(final java.lang.String impgroupid) {
        this.impgroupid = impgroupid;
    }

    public java.lang.String getImpgroupid() {
        return this.impgroupid;
    }


    /**
     * �������������
     */
    private java.lang.String impgroupname;

    public void setImpgroupname(final java.lang.String impgroupname) {
        this.impgroupname = impgroupname;
    }

    public java.lang.String getImpgroupname() {
        return this.impgroupname;
    }


    /**
     * ������ϢΨһ��ʶ��
     */
    private java.lang.String imprecordid;

    public void setImprecordid(final java.lang.String imprecordid) {
        this.imprecordid = imprecordid;
    }

    public java.lang.String getImprecordid() {
        return this.imprecordid;
    }


    /**
     * ����״̬1��δ���룬2�ѵ���,3���
     */
    private java.lang.String impstutas;

    public void setImpstutas(final java.lang.String impstutas) {
        this.impstutas = impstutas;
    }

    public java.lang.String getImpstutas() {
        return this.impstutas;
    }


    /**
     * ����ʱ��
     */
    private java.sql.Timestamp imptime;

    public void setImptime(final java.sql.Timestamp imptime) {
        this.imptime = imptime;
    }

    public java.sql.Timestamp getImptime() {
        return this.imptime;
    }


    /**
     * �������� 1 hzb, 2 zb3, 3 zzb3,
     */
    private java.lang.String imptype;

    public void setImptype(final java.lang.String imptype) {
        this.imptype = imptype;
    }

    public java.lang.String getImptype() {
        return this.imptype;
    }


    /**
     * �����û�����
     */
    private java.lang.String impuserid;

    public void setImpuserid(final java.lang.String impuserid) {
        this.impuserid = impuserid;
    }

    public java.lang.String getImpuserid() {
        return this.impuserid;
    }


    /**
     * �Ƿ�У��
     */
    private java.lang.String isvirety;

    public void setIsvirety(final java.lang.String isvirety) {
        this.isvirety = isvirety;
    }

    public java.lang.String getIsvirety() {
        return this.isvirety;
    }


    /**
     * ��ϵ��
     */
    private java.lang.String linkpsn;

    public void setLinkpsn(final java.lang.String linkpsn) {
        this.linkpsn = linkpsn;
    }

    public java.lang.String getLinkpsn() {
        return this.linkpsn;
    }


    /**
     * ��ϵ�绰
     */
    private java.lang.String linktel;

    public void setLinktel(final java.lang.String linktel) {
        this.linktel = linktel;
    }

    public java.lang.String getLinktel() {
        return this.linktel;
    }


    /**
     * ��ע
     */
    private java.lang.String remark;

    public void setRemark(final java.lang.String remark) {
        this.remark = remark;
    }

    public java.lang.String getRemark() {
        return this.remark;
    }


    /**
     * ���������
     */
    private java.lang.String storagenumber;

    public void setStoragenumber(final java.lang.String storagenumber) {
        this.storagenumber = storagenumber;
    }

    public java.lang.String getStoragenumber() {
        return this.storagenumber;
    }


    /**
     * ��������
     */
    private java.lang.String totalnumber;

    public void setTotalnumber(final java.lang.String totalnumber) {
        this.totalnumber = totalnumber;
    }

    public java.lang.String getTotalnumber() {
        return this.totalnumber;
    }


    /**
     * У�鷽��ID
     */
    private java.lang.String vsc001;

    public void setVsc001(final java.lang.String vsc001) {
        this.vsc001 = vsc001;
    }

    public java.lang.String getVsc001() {
        return this.vsc001;
    }


    /**
     * ����������
     */
    private java.lang.String wrongnumber;

    public void setWrongnumber(final java.lang.String wrongnumber) {
        this.wrongnumber = wrongnumber;
    }

    public java.lang.String getWrongnumber() {
        return this.wrongnumber;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Imprecordid", getImprecordid()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Imprecord)) {
            return false;
        }
        Imprecord castOther = (Imprecord) other;
        return new EqualsBuilder().append(this.getImprecordid(),castOther.getImprecordid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getImprecordid()).toHashCode();
    }
    private java.lang.Long psncount;
    private java.lang.Long orgcount;
    private java.lang.String processstatus;

	public java.lang.Long getPsncount() {
		return psncount;
	}

	public void setPsncount(java.lang.Long psncount) {
		this.psncount = psncount;
	}

	public java.lang.Long getOrgcount() {
		return orgcount;
	}

	public void setOrgcount(java.lang.Long orgcount) {
		this.orgcount = orgcount;
	}

	public java.lang.String getProcessstatus() {
		return processstatus;
	}

	public void setProcessstatus(java.lang.String processstatus) {
		this.processstatus = processstatus;
	}
	
	private java.lang.String imptemptable;

	public java.lang.String getImptemptable() {
		return imptemptable;
	}

	public void setImptemptable(java.lang.String imptemptable) {
		this.imptemptable = imptemptable;
	}
	
	private java.lang.Long tablenumber;

	public java.lang.Long getTablenumber() {
		return tablenumber;
	}

	public void setTablenumber(java.lang.Long tablenumber) {
		this.tablenumber = tablenumber;
	}

	private java.lang.String a0165;

	public java.lang.String getA0165() {
		return a0165;
	}

	public void setA0165(java.lang.String a0165) {
		this.a0165 = a0165;
	}
	private String iscontain;
	
	public void setIscontain(String iscontain) {
		this.iscontain = iscontain;
	}
	public String getIscontain() {
		return iscontain;
	}
}
