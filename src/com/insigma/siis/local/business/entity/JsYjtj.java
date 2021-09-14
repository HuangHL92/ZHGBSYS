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
	 * 公式
	 */
	private java.lang.String conditions;
	public java.lang.String getConditions() {
		return conditions;
	}

	public void setConditions(final java.lang.String conditions) {
		this.conditions = conditions;
	}


	private java.lang.String userid;//用户id
	
	private java.lang.String qrysql;//定义sql

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

	private java.lang.String createtime;//生成时间

    public java.lang.String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(final java.lang.String createtime) {
		this.createtime = createtime;
	}

	/**
     * 中文名
     */
    private java.lang.String chinesename;

    public void setChinesename(final java.lang.String chinesename) {
        this.chinesename = chinesename;
    }

    public java.lang.String getChinesename() {
        return this.chinesename;
    }


    /**
     * 功能描述
     */
    private java.lang.String funcdesc;

    public void setFuncdesc(final java.lang.String funcdesc) {
        this.funcdesc = funcdesc;
    }

    public java.lang.String getFuncdesc() {
        return this.funcdesc;
    }


    /**
     * 主键
     */
    private java.lang.String qvid;

    public void setQvid(final java.lang.String qvid) {
        this.qvid = qvid;
    }

    public java.lang.String getQvid() {
        return this.qvid;
    }


    /**
     * 视图类型1 自定义视图2 组合视图 3复杂视图
     */
    private java.lang.String type;

    public void setType(final java.lang.String type) {
        this.type = type;
    }

    public java.lang.String getType() {
        return this.type;
    }


    /**
     * 用途
     */
    private java.lang.String uses;

    public void setUses(final java.lang.String uses) {
        this.uses = uses;
    }
    
    public java.lang.String getUses() {
        return this.uses;
    }


    /**
     * 视图名称
     */
    private java.lang.String viewname;

    public void setViewname(final java.lang.String viewname) {
        this.viewname = viewname;
    }

    public java.lang.String getViewname() {
        return this.viewname;
    }


    /**
     * 库中视图名称
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
    
    private java.lang.String qrysql2;//定义sql

	public java.lang.String getQrysql2() {
		return qrysql2;
	}

	public void setQrysql2(java.lang.String qrysql2) {
		this.qrysql2 = qrysql2;
	}
    

}
