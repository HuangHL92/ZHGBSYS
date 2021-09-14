package com.insigma.siis.local.business.modeldb;


import java.io.Serializable;
import java.util.Date;

public class SublibrariesmodelDTO implements Serializable {
	private static final long serialVersionUID = -7327867498612781447L;
	private String sub_libraries_model_id; // �ֿ�����ID
	private String sub_libraries_model_name; // �ֿ���������
	private String sub_libraries_model_type; // �ֿ���������(1-�߼��ֿ⣬2-����ֿ�)
	private String sub_libraries_model_info; // �ֿ�����˵��
	private String run_state; // ����״̬��0-δ������1-��������
	private String is_effective; // ��Ч��ʶ��0-��Ч��1-��Ч��
	private String self_create_mark; // �������ͣ�0-�ϼ�������1-�Խ���
	private String create_user_id; // �����û�����
	private String create_username; // �����û�����
	private String create_groupid; // �����û������
	private String create_group_name; // �����û�������
	private Date create_time; // ����ʱ��
	private String change_user_id; // ����޸��û�����
	private String change_user_name; // ����޸��û�����
	private Date change_time; // ����޸�ʱ��
	private String sub_libraries_model_key; // ����ֿ�ģ��ID�̺�
	private String sqltext;
	public SublibrariesmodelDTO() {
		super();
	}
	public SublibrariesmodelDTO(String sub_libraries_model_id,
			String sub_libraries_model_name, String sub_libraries_model_type,
			String sub_libraries_model_info, String run_state,
			String is_effective, String self_create_mark,
			String create_user_id, String create_username,
			String create_groupid, String create_group_name, Date create_time,
			String change_user_id, String change_user_name, Date change_time,
			String sub_libraries_model_key,String sqltext) {
		super();
		this.sub_libraries_model_id = sub_libraries_model_id;
		this.sub_libraries_model_name = sub_libraries_model_name;
		this.sub_libraries_model_type = sub_libraries_model_type;
		this.sub_libraries_model_info = sub_libraries_model_info;
		this.run_state = run_state;
		this.is_effective = is_effective;
		this.self_create_mark = self_create_mark;
		this.create_user_id = create_user_id;
		this.create_username = create_username;
		this.create_groupid = create_groupid;
		this.create_group_name = create_group_name;
		this.create_time = create_time;
		this.change_user_id = change_user_id;
		this.change_user_name = change_user_name;
		this.change_time = change_time;
		this.sub_libraries_model_key = sub_libraries_model_key;
		this.sqltext = sqltext;
	}
	public String getSub_libraries_model_id() {
		return sub_libraries_model_id;
	}
	public void setSub_libraries_model_id(String sub_libraries_model_id) {
		this.sub_libraries_model_id = sub_libraries_model_id;
	}
	public String getSub_libraries_model_name() {
		return sub_libraries_model_name;
	}
	public void setSub_libraries_model_name(String sub_libraries_model_name) {
		this.sub_libraries_model_name = sub_libraries_model_name;
	}
	public String getSub_libraries_model_type() {
		return sub_libraries_model_type;
	}
	public void setSub_libraries_model_type(String sub_libraries_model_type) {
		this.sub_libraries_model_type = sub_libraries_model_type;
	}
	public String getSub_libraries_model_info() {
		return sub_libraries_model_info;
	}
	public void setSub_libraries_model_info(String sub_libraries_model_info) {
		this.sub_libraries_model_info = sub_libraries_model_info;
	}
	public String getRun_state() {
		return run_state;
	}
	public void setRun_state(String run_state) {
		this.run_state = run_state;
	}
	public String getIs_effective() {
		return is_effective;
	}
	public void setIs_effective(String is_effective) {
		this.is_effective = is_effective;
	}
	public String getSelf_create_mark() {
		return self_create_mark;
	}
	public void setSelf_create_mark(String self_create_mark) {
		this.self_create_mark = self_create_mark;
	}
	public String getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getCreate_username() {
		return create_username;
	}
	public void setCreate_username(String create_username) {
		this.create_username = create_username;
	}
	public String getCreate_groupid() {
		return create_groupid;
	}
	public void setCreate_groupid(String create_groupid) {
		this.create_groupid = create_groupid;
	}
	public String getCreate_group_name() {
		return create_group_name;
	}
	public void setCreate_group_name(String create_group_name) {
		this.create_group_name = create_group_name;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getChange_user_id() {
		return change_user_id;
	}
	public void setChange_user_id(String change_user_id) {
		this.change_user_id = change_user_id;
	}
	public String getChange_user_name() {
		return change_user_name;
	}
	public void setChange_user_name(String change_user_name) {
		this.change_user_name = change_user_name;
	}
	public Date getChange_time() {
		return change_time;
	}
	public void setChange_time(Date change_time) {
		this.change_time = change_time;
	}
	public String getSub_libraries_model_key() {
		return sub_libraries_model_key;
	}
	public void setSub_libraries_model_key(String sub_libraries_model_key) {
		this.sub_libraries_model_key = sub_libraries_model_key;
	}
	public String getSqltext() {
		return sqltext;
	}
	public void setSqltext(String sqltext) {
		this.sqltext = sqltext;
	}
	
	
   



}
