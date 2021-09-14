 package com.insigma.siis.local.business.entity;

 import java.io.Serializable;
 public class TabDatacontrastresult implements Serializable {

     /**
      * 主键
      */
      private java.lang.String storeid;


      public java.lang.String getStoreid() {
         return this.storeid;
      }
      public void setStoreid(final java.lang.String storeid) {
         this.storeid = storeid;
      }

      /**
      * 机构主键（内部）
      */
      private java.lang.String b0111n;


      public java.lang.String getB0111n() {
         return this.b0111n;
      }
      public void setB0111n(final java.lang.String b0111n) {
         this.b0111n = b0111n;
      }

      /**
      * 机构主键（外部）
      */
      private java.lang.String b0111w;


      public java.lang.String getB0111w() {
         return this.b0111w;
      }
      public void setB0111w(final java.lang.String b0111w) {
         this.b0111w = b0111w;
      }

      /**
      * 上级机构主键（内部）
      */
      private java.lang.String b0111parentn;


      public java.lang.String getB0111parentn() {
         return this.b0111parentn;
      }
      public void setB0111parentn(final java.lang.String b0111parentn) {
         this.b0111parentn = b0111parentn;
      }

      /**
      * 上级机构主键（外部）
      */
      private java.lang.String b0111parentw;


      public java.lang.String getB0111parentw() {
         return this.b0111parentw;
      }
      public void setB0111parentw(final java.lang.String b0111parentw) {
         this.b0111parentw = b0111parentw;
      }

      /**
      * 机构名称（内部）
      */
      private java.lang.String b0101n;


      public java.lang.String getB0101n() {
         return this.b0101n;
      }
      public void setB0101n(final java.lang.String b0101n) {
         this.b0101n = b0101n;
      }

      /**
      * 机构名称（外部）
      */
      private java.lang.String b0101w;


      public java.lang.String getb0101w() {
         return this.b0101w;
      }
      public void setb0101w(final java.lang.String b0101w) {
         this.b0101w = b0101w;
      }

      /**
      * 法人单位编码(机构分组编码)（内部）
      */
      private java.lang.String b0114n;


      public java.lang.String getb0114n() {
         return this.b0114n;
      }
      public void setb0114n(final java.lang.String b0114n) {
         this.b0114n = b0114n;
      }

      /**
      * 法人单位编码(机构分组编码)（外部）
      */
      private java.lang.String b0114w;


      public java.lang.String getb0114w() {
         return this.b0114w;
      }
      public void setb0114w(final java.lang.String b0114w) {
         this.b0114w = b0114w;
      }

      /**
      * [0102]导入记录信息表imp_record主键
      */
      private java.lang.String imprecordid;


      public java.lang.String getImprecordid() {
         return this.imprecordid;
      }
      public void setImprecordid(final java.lang.String imprecordid) {
         this.imprecordid = imprecordid;
      }

      /**
      * 结果描述
      */
      private java.lang.String comments;


      public java.lang.String getComments() {
         return this.comments;
      }
      public void setComments(final java.lang.String comments) {
         this.comments = comments;
      }

      /**
      * 对比结果类型   0：已删除 1：一致 2：新增 3：已修改
      */
      private java.lang.String opptimetype;


      public java.lang.String getOpptimetype() {
         return this.opptimetype;
      }
      public void setOpptimetype(final java.lang.String opptimetype) {
         this.opptimetype = opptimetype;
      }

      /**
      * 操作时间
      */
      private java.util.Date opptime;


      public java.util.Date getOpptime() {
         return this.opptime;
      }
      public void setOpptime(final java.util.Date opptime) {
         this.opptime = opptime;
      }

  }

