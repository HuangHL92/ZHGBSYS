package com.insigma.siis.local.business.modeldb;


import java.io.Serializable;
import java.util.Date;

public class SublibrariesmodelDTO implements Serializable {
	private static final long serialVersionUID = -7327867498612781447L;
	private String sub_libraries_model_id; // 分库主题ID
	private String sub_libraries_model_name; // 分库主题名称
	private String sub_libraries_model_type; // 分库主题类型(1-逻辑分库，2-物理分库)
	private String sub_libraries_model_info; // 分库主题说明
	private String run_state; // 启动状态（0-未启动，1-已启动）
	private String is_effective; // 有效标识（0-无效，1-有效）
	private String self_create_mark; // 创建类型（0-上级创建，1-自建）
	private String create_user_id; // 创建用户编码
	private String create_username; // 创建用户名称
	private String create_groupid; // 创建用户组编码
	private String create_group_name; // 创建用户组名称
	private Date create_time; // 创建时间
	private String change_user_id; // 最后修改用户编码
	private String change_user_name; // 最后修改用户名称
	private Date change_time; // 最后修改时间
	private String sub_libraries_model_key; // 主题分库模型ID短号
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
