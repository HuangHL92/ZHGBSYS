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
public class A29 implements Serializable,Cloneable {


    public A29() {
    }
    @Override
	public A29 clone() throws CloneNotSupportedException
	{
		Object object = super.clone();
		return (A29)object;
	}

    private java.lang.String a2921c;
    private java.lang.String a2921d;
    
    private java.lang.String a2950;
    private java.lang.String a2951;
    private java.lang.String a2921b;
    private java.lang.Long a2947b;
    private java.lang.String a2970;
    private java.lang.String a2970a;
    private java.lang.String a2970b;
    private java.lang.Long a2970c;
    private java.lang.Long a2947a;

    /**
     * 外键
     */
    private java.lang.String a0000;

    public void setA0000(final java.lang.String a0000) {
        this.a0000 = a0000;
    }

    public java.lang.String getA0000() {
        return this.a0000;
    }


    /**
     * 进入本单位日期
     */
    private java.lang.String a2907;

    public void setA2907(final java.lang.String a2907) {
        this.a2907 = a2907;
    }

    public java.lang.String getA2907() {
        return this.a2907;
    }


    /**
     * 进入本单位方式
     */
    private java.lang.String a2911;

    public void setA2911(final java.lang.String a2911) {
        this.a2911 = a2911;
    }

    public java.lang.String getA2911() {
        return this.a2911;
    }


    /**
     * 原单位名称
     */
    private java.lang.String a2921a;

    public void setA2921a(final java.lang.String a2921a) {
        this.a2921a = a2921a;
    }

    public java.lang.String getA2921a() {
        return this.a2921a;
    }


    /**
     * 在原单位职务
     */
    private java.lang.String a2941;

    public void setA2941(final java.lang.String a2941) {
        this.a2941 = a2941;
    }

    public java.lang.String getA2941() {
        return this.a2941;
    }


    /**
     * 在原单位级别
     */
    private java.lang.String a2944;

    public void setA2944(final java.lang.String a2944) {
        this.a2944 = a2944;
    }

    public java.lang.String getA2944() {
        return this.a2944;
    }


    /**
     * 进入公务员队伍时间
     */
    private java.lang.String a2947;

    public void setA2947(final java.lang.String a2947) {
        this.a2947 = a2947;
    }

    public java.lang.String getA2947() {
        return this.a2947;
    }


    /**
     * 公务员登记时间
     */
    private java.lang.String a2949;

    public void setA2949(final java.lang.String a2949) {
        this.a2949 = a2949;
    }

    public java.lang.String getA2949() {
        return this.a2949;
    }


    /**
     * 标识
     */
    private java.lang.String updated;

    public void setUpdated(final java.lang.String updated) {
        this.updated = updated;
    }

    public java.lang.String getUpdated() {
        return this.updated;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("A0000", getA0000()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof A29)) {
            return false;
        }
        A29 castOther = (A29) other;
        return new EqualsBuilder().append(this.getA0000(),castOther.getA0000()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getA0000()).toHashCode();
    }

	public java.lang.String getA2970() {
		return a2970;
	}

	public void setA2970(java.lang.String a2970) {
		this.a2970 = a2970;
	}

	public java.lang.String getA2970a() {
		return a2970a;
	}

	public void setA2970a(java.lang.String a2970a) {
		this.a2970a = a2970a;
	}

	public java.lang.String getA2970b() {
		return a2970b;
	}

	public void setA2970b(java.lang.String a2970b) {
		this.a2970b = a2970b;
	}

	public java.lang.Long getA2970c() {
		return a2970c;
	}

	public void setA2970c(java.lang.Long a2970c) {
		this.a2970c = a2970c;
	}

	public java.lang.Long getA2947a() {
		return a2947a;
	}

	public void setA2947a(java.lang.Long a2947a) {
		this.a2947a = a2947a;
	}

	public java.lang.String getA2950() {
		return a2950;
	}

	public void setA2950(java.lang.String a2950) {
		this.a2950 = a2950;
	}

	public java.lang.String getA2951() {
		return a2951;
	}

	public void setA2951(java.lang.String a2951) {
		this.a2951 = a2951;
	}

	public java.lang.String getA2921b() {
		return a2921b;
	}

	public void setA2921b(java.lang.String a2921b) {
		this.a2921b = a2921b;
	}

	public java.lang.Long getA2947b() {
		return a2947b;
	}

	public void setA2947b(java.lang.Long a2947b) {
		this.a2947b = a2947b;
	}

	public java.lang.String getA2921c() {
		return a2921c;
	}

	public void setA2921c(java.lang.String a2921c) {
		this.a2921c = a2921c;
	}

	public java.lang.String getA2921d() {
		return a2921d;
	}

	public void setA2921d(java.lang.String a2921d) {
		this.a2921d = a2921d;
	}

	



}
