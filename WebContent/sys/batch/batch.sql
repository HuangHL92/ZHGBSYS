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
  is '����ҵ����ʱ��';
comment on column NET_BATCHTEMP.ID
  is '����';
comment on column NET_BATCHTEMP.BATCHID
  is '����id';
comment on column NET_BATCHTEMP.UPLOADDATE
  is '�ϴ�������ʱ��';
comment on column NET_BATCHTEMP.RSROWNUM
  is '������������excel���к�';
comment on column NET_BATCHTEMP.ISVALIDATE
  is '�Ƿ�У��ͨ����1 �� 0 ��';
comment on column NET_BATCHTEMP.ISUSEFUL
  is '�Ƿ���Ч�����߼�ɾ���� 1��0��0��Ϊɾ��';
comment on column NET_BATCHTEMP.CONTENT
  is '���е����ݣ���json��ʽ���棬���Լ���ʱҲҪע��';
comment on column NET_BATCHTEMP.DESCRIPTION
  is '��ע';
comment on column NET_BATCHTEMP.PRAM1
  is 'Ԥ���ֶ�1';
comment on column NET_BATCHTEMP.PRAM2
  is 'Ԥ���ֶ�2';
comment on column NET_BATCHTEMP.PRAM3
  is 'Ԥ���ֶ�3';
comment on column NET_BATCHTEMP.AAB001
  is '��λ�����������Ψһ��ʶһ����λ����Ϣ';
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
