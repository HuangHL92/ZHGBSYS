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
public class Datarecrejlog implements Serializable {


    public Datarecrejlog() {
    }


    /**
     * id
     */
    private java.lang.String datarecrejlogid;

    public void setDatarecrejlogid(final java.lang.String datarecrejlogid) {
        this.datarecrejlogid = datarecrejlogid;
    }

    public java.lang.String getDatarecrejlogid() {
        return this.datarecrejlogid;
    }


    /**
     * 业务表名
     */
    private java.lang.String datatable;

    public void setDatatable(final java.lang.String datatable) {
        this.datatable = datatable;
    }

    public java.lang.String getDatatable() {
        return this.datatable;
    }


    /**
     * 描述
     */
    private java.lang.String detail;

    public void setDetail(final java.lang.String detail) {
        this.detail = detail;
    }

    public java.lang.String getDetail() {
        return this.detail;
    }


    /**
     * 结束时间
     */
    private java.sql.Timestamp endtime;

    public void setEndtime(final java.sql.Timestamp endtime) {
        this.endtime = endtime;
    }

    public java.sql.Timestamp getEndtime() {
        return this.endtime;
    }


    /**
     * 导入记录id
     */
    private java.lang.String imprecordid;

    public void setImprecordid(final java.lang.String imprecordid) {
        this.imprecordid = imprecordid;
    }

    public java.lang.String getImprecordid() {
        return this.imprecordid;
    }


    /**
     * 数据状态
     */
    private java.lang.String opstatus;

    public void setOpstatus(final java.lang.String opstatus) {
        this.opstatus = opstatus;
    }

    public java.lang.String getOpstatus() {
        return this.opstatus;
    }


    /**
     * 操作类型
     */
    private java.lang.String optype;

    public void setOptype(final java.lang.String optype) {
        this.optype = optype;
    }

    public java.lang.String getOptype() {
        return this.optype;
    }


    /**
     * 排序
     */
    private java.lang.Long sortid;

    public void setSortid(final java.lang.Long sortid) {
        this.sortid = sortid;
    }

    public java.lang.Long getSortid() {
        return this.sortid;
    }


    /**
     * 开始时间
     */
    private java.sql.Timestamp starttime;

    public void setStarttime(final java.sql.Timestamp starttime) {
        this.starttime = starttime;
    }

    public java.sql.Timestamp getStarttime() {
        return this.starttime;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Datarecrejlogid", getDatarecrejlogid()).toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Datarecrejlog)) {
            return false;
        }
        Datarecrejlog castOther = (Datarecrejlog) other;
        return new EqualsBuilder().append(this.getDatarecrejlogid(),castOther.getDatarecrejlogid()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getDatarecrejlogid()).toHashCode();
    }



}
