 package com.insigma.odin.framework.privilege.entity;

 import java.io.Serializable;

 /**
 * @Title 数据库表名  SMT_OAUSER
 * @author lvyq 97354625@qq.com
 * @Description: 本类由HibernateTool工具自动生成
 * @date 2020年1月6日
 * @version 1.0
 */
 public class SmtOauser implements Serializable {

	 /**
	 * 部门唯一标识
	 */
	 private String id;
	 /**
	 * 用户名
	 */
	 private String user_id;


	 /**
	 * 用户排序号
	 */
	 private String order_no;

	 /**
	 * 用户姓名
	 */
	 private String user_name;
	 /**
	 * 用户姓名索引
	 */
	 private String user_name_index;

	 /**
	 * 用户别名
	 */
	 private String byname;

	 /**
	 * 绑定ip
	 */
	 private String bind_ip;

	 /**
	 * 是否禁止登录（1禁止0正常）
	 */
	 private String not_login;

	 /**
	 * 个性签名
	 */
	 private String signature;

	 /**
	 * 性别（1男0女）
	 */
	 private String gender;
	 /**
	 * 出生日期yyyy-MM-dd
	 */
	 private String birthday;

	 /**
	 * 部门电话
	 */
	 private String tel_dept;
	 /**
	 * 部门传真
	 */
	 private String fax_dept;
	 /**
	 * 家庭地址
	 */
	 private String address_home;

	 /**
	 * 家庭邮编
	 */
	 private String postcode_home;

	 /**
	 * 家庭电话
	 */
	 private String tel_home;

	 /**
	 * 移动电话
	 */
	 private String mobile;

	 /**
	 * 邮箱地址
	 */
	 private String email;

	 /**
	 * QQ账号
	 */
	 private String qq;
	 /**
	 * 部门id
	 */
	 private String org_id;

	 /**
	 * 部门名称
	 */
	 private String org_name;

	 /**
	 * 职位信息，多个职位通过,隔开
	 */
	 private String position_name;

	 /**
	 * 关联干部信息系统userid
	 */
	 private String self_userid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_name_index() {
		return user_name_index;
	}

	public void setUser_name_index(String user_name_index) {
		this.user_name_index = user_name_index;
	}

	public String getByname() {
		return byname;
	}

	public void setByname(String byname) {
		this.byname = byname;
	}

	public String getBind_ip() {
		return bind_ip;
	}

	public void setBind_ip(String bind_ip) {
		this.bind_ip = bind_ip;
	}

	public String getNot_login() {
		return not_login;
	}

	public void setNot_login(String not_login) {
		this.not_login = not_login;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getTel_dept() {
		return tel_dept;
	}

	public void setTel_dept(String tel_dept) {
		this.tel_dept = tel_dept;
	}

	public String getFax_dept() {
		return fax_dept;
	}

	public void setFax_dept(String fax_dept) {
		this.fax_dept = fax_dept;
	}

	public String getAddress_home() {
		return address_home;
	}

	public void setAddress_home(String address_home) {
		this.address_home = address_home;
	}

	public String getPostcode_home() {
		return postcode_home;
	}

	public void setPostcode_home(String postcode_home) {
		this.postcode_home = postcode_home;
	}

	public String getTel_home() {
		return tel_home;
	}

	public void setTel_home(String tel_home) {
		this.tel_home = tel_home;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getPosition_name() {
		return position_name;
	}

	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}

	public String getSelf_userid() {
		return self_userid;
	}

	public void setSelf_userid(String self_userid) {
		this.self_userid = self_userid;
	}

	 
	 
 }
