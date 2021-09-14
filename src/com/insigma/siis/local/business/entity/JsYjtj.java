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
public class JsYjtj implements Serializable {
	private java.lang.String yjtype;

    public java.lang.String getYjtype() {
		return yjtype;
	}

	public void setYjtype(java.lang.String yjtype) {
		this.yjtype = yjtype;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsYjtj() {
    }
	
	/**
	 * ��ʽ
	 */
	private java.lang.String conditions;
	public java.lang.String getConditions() {
		return conditions;
	}

	public void setConditions(final java.lang.String conditions) {
		this.conditions = conditions;
	}


	private java.lang.String userid;//�û�id
	
	private java.lang.String qrysql;//����sql

	public java.lang.String getUserid() {
		return userid;
	}

	public void setUserid(final java.lang.String userid) {
		this.userid = userid;
	}

	public java.lang.String getQrysql() {
		return qrysql;
	}

	public void setQrysql(final java.lang.String qrysql) {
		this.qrysql = qrysql;
	}

	private java.lang.String createtime;//����ʱ��

    public java.lang.String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(final java.lang.String createtime) {
		this.createtime = createtime;
	}

	/**
     * ������
     */
    private java.lang.String chinesename;

    public void setChinesename(final java.lang.String chinesename) {
        this.chinesename = chinesename;
    }

    public java.lang.String getChinesename() {
        return this.chinesename;
    }


    /**
     * ��������
     */
    private java.lang.String funcdesc;

    public void setFuncdesc(final java.lang.String funcdesc) {
        this.funcdesc = funcdesc;
    }

    public java.lang.String getFuncdesc() {
        return this.funcdesc;
    }


    /**
     * ����
     */
    private java.lang.String qvid;

    public void setQvid(final java.lang.String qvid) {
        this.qvid = qvid;
    }

    public java.lang.String getQvid() {
        return this.qvid;
    }


    /**
     * ��ͼ����1 �Զ�����ͼ2 �����ͼ 3������ͼ
     */
    private java.lang.String type;

    public void setType(final java.lang.String type) {
        this.type = type;
    }

    public java.lang.String getType() {
        return this.type;
    }


    /**
     * ��;
     */
    private java.lang.String uses;

    public void setUses(final java.lang.String uses) {
        this.uses = uses;
    }
    
    public java.lang.String getUses() {
        return this.uses;
    }


    /**
     * ��ͼ����
     */
    private java.lang.String viewname;

    public void setViewname(final java.lang.String viewname) {
        this.viewname = viewname;
    }

    public java.lang.String getViewname() {
        return this.viewname;
    }


    /**
     * ������ͼ����
     */
    private java.lang.String viewnametb;

    public void setViewnametb(final java.lang.String viewnametb) {
        this.viewnametb = viewnametb;
    }

    public java.lang.String getViewnametb() {
        return this.viewnametb;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Qvid", getQvid()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof JsYjtj)) {
            return false;
        }
        JsYjtj castOther = (JsYjtj) other;
        return new EqualsBuilder().append(this.getQvid(),castOther.getQvid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getQvid()).toHashCode();
    }
    
    private java.lang.String qrysql2;//����sql

	public java.lang.String getQrysql2() {
		return qrysql2;
	}

	public void setQrysql2(java.lang.String qrysql2) {
		this.qrysql2 = qrysql2;
	}
    

}
