-----------------------------------------------------
-- Export file for user NETREPDB_NEW               --
-- Created by Administrator on 2009-2-26, 11:12:56 --
-----------------------------------------------------

spool netrepdb_new.log

prompt
prompt Creating table NET_BATCHTEMP
prompt ============================
prompt
create table NET_BATCHTEMP
(
  ID          VARCHAR2(32) not null,
  BATCHID     VARCHAR2(100),
  UPLOADDATE  DATE,
  RSROWNUM    NUMBER,
  ISVALIDATE  VARCHAR2(1),
  ISUSEFUL    VARCHAR2(1),
  CONTENT     VARCHAR2(4000),
  DESCRIPTION VARCHAR2(1000),
  PRAM1       VARCHAR2(500),
  PRAM2       VARCHAR2(500),
  PRAM3       VARCHAR2(500),
  AAB001      VARCHAR2(50)
)
;
comment on table NET_BATCHTEMP
  is '批量业务临时表';
comment on column NET_BATCHTEMP.ID
  is '主键';
comment on column NET_BATCHTEMP.BATCHID
  is '批次id';
comment on column NET_BATCHTEMP.UPLOADDATE
  is '上传并保存时间';
comment on column NET_BATCHTEMP.RSROWNUM
  is '该条数据所在excel的行号';
comment on column NET_BATCHTEMP.ISVALIDATE
  is '是否校验通过：1 是 0 否';
comment on column NET_BATCHTEMP.ISUSEFUL
  is '是否有效（即逻辑删除） 1是0否，0则为删除';
comment on column NET_BATCHTEMP.CONTENT
  is '该行的内容，以json方式保存，所以检索时也要注意';
comment on column NET_BATCHTEMP.DESCRIPTION
  is '备注';
comment on column NET_BATCHTEMP.PRAM1
  is '预留字段1';
comment on column NET_BATCHTEMP.PRAM2
  is '预留字段2';
comment on column NET_BATCHTEMP.PRAM3
  is '预留字段3';
comment on column NET_BATCHTEMP.AAB001
  is '单位内码或者其它唯一标识一个单位的信息';
alter table NET_BATCHTEMP
  add constraint PK_NET_BATCHTEMP primary key (ID);
create index IDX_NET_BATCHTEMP_CONTENT on NET_BATCHTEMP (CONTENT);

prompt
prompt Creating sequence SEQ_BATCHID
prompt =============================
prompt
create sequence SEQ_BATCHID
minvalue 1
maxvalue 999999999999999999
start with 281
increment by 1
cache 20
order;

prompt
prompt Creating procedure CLEAR_NET_BATCHTEMP
prompt ======================================
prompt
create or replace procedure clear_net_batchtemp is
begin
  delete from net_batchtemp b where b.uploaddate<sysdate-2;
end clear_net_batchtemp;
/


spool off
