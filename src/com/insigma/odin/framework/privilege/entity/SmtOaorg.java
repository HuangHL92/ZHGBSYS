 package com.insigma.odin.framework.privilege.entity;

 import java.io.Serializable;

 /**
 * @Title ���ݿ����  SMT_OAORG
 * @author lvyq 97354625@qq.com
 * @Description: ������HibernateTool�����Զ�����
 * @date 2020��1��7��
 * @version 1.0
 */
 public class SmtOaorg implements Serializable {

	 /**
	 * ID
	 */
	 private String id;
	 /**
	 * ��������
	 */
	 private String org_name;
	 /**
	 * ���ű��
	 */
	 private String org_no;
	 /**
	 * ���ŵ绰
	 */
	 private String tel_no;
	 /**
	 * ���Ŵ���
	 */
	 private String fax_no;
	 /**
	 * ����ְ��
	 */
	 private String org_func;
	 /**
	 * ���ŵ�ַ
	 */
	 private String org_address;
	 /**
	 * �����ϼ�
	 */
	 private String org_parent;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getOrg_no() {
		return org_no;
	}
	public void setOrg_no(String org_no) {
		this.org_no = org_no;
	}
	public String getTel_no() {
		return tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}
	public String getFax_no() {
		return fax_no;
	}
	public void setFax_no(String fax_no) {
		this.fax_no = fax_no;
	}
	public String getOrg_func() {
		return org_func;
	}
	public void setOrg_func(String org_func) {
		this.org_func = org_func;
	}
	public String getOrg_address() {
		return org_address;
	}
	public void setOrg_address(String org_address) {
		this.org_address = org_address;
	}
	public String getOrg_parent() {
		return org_parent;
	}
	public void setOrg_parent(String org_parent) {
		this.org_parent = org_parent;
	}
	 
	 
	 

 }
