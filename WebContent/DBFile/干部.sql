
alter table A01 add A0190 varchar2(2);
alter table A01 add A0189 varchar2(2);

insert into code_value (CODE_VALUE_SEQ, CODE_TYPE, CODE_COLUMN_NAME, CODE_LEVEL, CODE_VALUE, ININO, SUB_CODE_VALUE, CODE_NAME, CODE_NAME2, CODE_NAME3, CODE_REMARK, CODE_SPELLING, ISCUSTOMIZE, CODE_ASSIST, CODE_STATUS, CODE_LEAF, START_DATE, STOP_DATE)
values (3800077, 'OPERATE_TYPE', null, null, '3801', null, '-1', '�ɲ������', '�ɲ������', '�ɲ������', null, 'GBCSH', '0', null, '1', '1', null, null);
insert into code_value (CODE_VALUE_SEQ, CODE_TYPE, CODE_COLUMN_NAME, CODE_LEVEL, CODE_VALUE, ININO, SUB_CODE_VALUE, CODE_NAME, CODE_NAME2, CODE_NAME3, CODE_REMARK, CODE_SPELLING, ISCUSTOMIZE, CODE_ASSIST, CODE_STATUS, CODE_LEAF, START_DATE, STOP_DATE)
values (3800078, 'OPERATE_TYPE', null, null, '3802', null, '-1', '�ɲ�һ�����', '�ɲ�һ�����', '�ɲ�һ�����', null, 'GBYCSH', '0', null, '1', '1', null, null);
insert into code_value (CODE_VALUE_SEQ, CODE_TYPE, CODE_COLUMN_NAME, CODE_LEVEL, CODE_VALUE, ININO, SUB_CODE_VALUE, CODE_NAME, CODE_NAME2, CODE_NAME3, CODE_REMARK, CODE_SPELLING, ISCUSTOMIZE, CODE_ASSIST, CODE_STATUS, CODE_LEAF, START_DATE, STOP_DATE)
values (3800079, 'OPERATE_TYPE', null, null, '3803', null, '-1', '�ɲ����������', '�ɲ����������', '�ɲ����������', null, 'GBCJSSH', '0', null, '1', '1', null, null);

insert into COMPETENCE_INF values('A0190','�ɲ������','1',null);
insert into COMPETENCE_INF values('A0189','�ɲ�һ�����','1',null);

insert into a01_config (DICID, CODE, NAME, TDESC, ORDERID, ISVALI, FORMTYPE, ABOUTCODE, GRIDWIDTH, RENDERER, ALIGN, USERID)
values ('a0189', '(case when a0189=''1'' then ''����'' else ''δ��'' end )', '�ɲ�һ�����״̬', '�ɲ�һ�����״̬', 41, 'true', 'TEXT', null, 100, null, 'center', '40288103556cc97701556d629135000f');
insert into a01_config (DICID, CODE, NAME, TDESC, ORDERID, ISVALI, FORMTYPE, ABOUTCODE, GRIDWIDTH, RENDERER, ALIGN, USERID)
values ('a0190', '(case when a0190=''1'' then ''����'' else ''δ��'' end )', '�ɲ������״̬', '�ɲ������״̬', 40, 'true', 'TEXT', null, 100, null, 'center', '40288103556cc97701556d629135000f');

insert into A01_CONFIG_INIT values('a0189', '(case when a0189=''1'' then ''����'' else ''δ��'' end )', '�ɲ�һ�����״̬', '�ɲ�һ�����״̬','296','true','TEXT',null,'100',null,'center');
insert into A01_CONFIG_INIT values('a0190', '(case when a0190=''1'' then ''����'' else ''δ��'' end )', '�ɲ������״̬', '�ɲ������״̬','297','true','TEXT',null,'100',null,'center');
