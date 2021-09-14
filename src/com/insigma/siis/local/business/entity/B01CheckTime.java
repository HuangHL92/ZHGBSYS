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
public class B01CheckTime implements Serializable {


    public B01CheckTime() {
    }


    /**
     * 机构编码
     */
    private java.lang.String b0111;

    public void setB0111(final java.lang.String b0111) {
        this.b0111 = b0111;
    }

    public java.lang.String getB0111() {
        return this.b0111;
    }


    /**
     * 上次检测时间
     */
    private java.lang.String checktime;
    

    public java.lang.String getChecktime() {
		return checktime;
	}

	public void setChecktime(java.lang.String checktime) {
		this.checktime = checktime;
	}




}
