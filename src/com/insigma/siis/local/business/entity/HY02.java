 package com.insigma.siis.local.business.entity;

 import java.io.Serializable;
 import java.util.List;

 /**
 * @Title ���ݿ����  HY02
 * @author lvyq 97354625@qq.com
 * @Description: ������HibernateTool�����Զ�����
 * @date 2019��12��10��
 * @version 1.0
 */
 public class HY02 implements Serializable {
 	private List<HY03> hy03List;

	 public List<HY03> getHy03List() {
		 return hy03List;
	 }

	 public void setHy03List(List<HY03> hy03List) {
		 this.hy03List = hy03List;
	 }

	 /**
	 * ����׶�Ψһ��ʶ��
	 */
	 private String hy0200;


	 public String getHy0200() {
	    return this.hy0200;
	 }
	 public void setHy0200(final String hy0200) {
	    this.hy0200 = hy0200;
	 }

	 /**
	 * ����Ψһ��ʶ��
	 */
	 private String hy0100;


	 public String getHy0100() {
	    return this.hy0100;
	 }
	 public void setHy0100(final String hy0100) {
	    this.hy0100 = hy0100;
	 }

	 /**
	 * ����׶�����
	 */
	 private String hy0201;


	 public String getHy0201() {
	    return this.hy0201;
	 }
	 public void setHy0201(final String hy0201) {
	    this.hy0201 = hy0201;
	 }

	 /**
	 * ����׶��ص�
	 */
	 private String hy0202;


	 public String getHy0202() {
	    return this.hy0202;
	 }
	 public void setHy0202(final String hy0202) {
	    this.hy0202 = hy0202;
	 }

	 /**
	 * ����׶�����
	 */
	 private Integer hy0203;


	 public Integer getHy0203() {
	    return this.hy0203;
	 }
	 public void setHy0203(final Integer hy0203) {
	    this.hy0203 = hy0203;
	 }

 }
