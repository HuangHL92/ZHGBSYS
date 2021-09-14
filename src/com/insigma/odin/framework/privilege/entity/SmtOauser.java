 package com.insigma.odin.framework.privilege.entity;

 import java.io.Serializable;

 /**
 * @Title ���ݿ����  SMT_OAUSER
 * @author lvyq 97354625@qq.com
 * @Description: ������HibernateTool�����Զ�����
 * @date 2020��1��6��
 * @version 1.0
 */
 public class SmtOauser implements Serializable {

	 /**
	 * ����Ψһ��ʶ
	 */
	 private String id;
	 /**
	 * �û���
	 */
	 private String user_id;


	 /**
	 * �û������
	 */
	 private String order_no;

	 /**
	 * �û�����
	 */
	 private String user_name;
	 /**
	 * �û���������
	 */
	 private String user_name_index;

	 /**
	 * �û�����
	 */
	 private String byname;

	 /**
	 * ��ip
	 */
	 private String bind_ip;

	 /**
	 * �Ƿ��ֹ��¼��1��ֹ0������
	 */
	 private String not_login;

	 /**
	 * ����ǩ��
	 */
	 private String signature;

	 /**
	 * �Ա�1��0Ů��
	 */
	 private String gender;
	 /**
	 * ��������yyyy-MM-dd
	 */
	 private String birthday;

	 /**
	 * ���ŵ绰
	 */
	 private String tel_dept;
	 /**
	 * ���Ŵ���
	 */
	 private String fax_dept;
	 /**
	 * ��ͥ��ַ
	 */
	 private String address_home;

	 /**
	 * ��ͥ�ʱ�
	 */
	 private String postcode_home;

	 /**
	 * ��ͥ�绰
	 */
	 private String tel_home;

	 /**
	 * �ƶ��绰
	 */
	 private String mobile;

	 /**
	 * �����ַ
	 */
	 private String email;

	 /**
	 * QQ�˺�
	 */
	 private String qq;
	 /**
	 * ����id
	 */
	 private String org_id;

	 /**
	 * ��������
	 */
	 private String org_name;

	 /**
	 * ְλ��Ϣ�����ְλͨ��,����
	 */
	 private String position_name;

	 /**
	 * �����ɲ���Ϣϵͳuserid
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
