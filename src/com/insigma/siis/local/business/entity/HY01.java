package com.insigma.siis.local.business.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author lvyq 97354625@qq.com
 * @version 1.0
 * @Title ���ݿ����  HY01
 * @Description: ������HibernateTool�����Զ�����
 * @date 2019��12��10��
 */
public class HY01 implements Serializable {

    private List<HY04> hy04List;

    public List<HY04> getHy04List() {
        return hy04List;
    }

    public void setHy04List(List<HY04> hy04List) {
        this.hy04List = hy04List;
    }

    private String hy0107;

    public String getHy0107() {
        return hy0107;
    }

    public void setHy0107(String hy0107) {
        this.hy0107 = hy0107;
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
     * ��������
     */
    private String hy0101;


    public String getHy0101() {
        return this.hy0101;
    }

    public void setHy0101(final String hy0101) {
        this.hy0101 = hy0101;
    }

    /**
     * ����ʱ��
     */
    private String hy0102;


    public String getHy0102() {
        return this.hy0102;
    }

    public void setHy0102(final String hy0102) {
        this.hy0102 = hy0102;
    }

    /**
     * �Զ��ر�����
     */
    private String hy0103;


    public String getHy0103() {
        return this.hy0103;
    }

    public void setHy0103(final String hy0103) {
        this.hy0103 = hy0103;
    }

    /**
     * �Ƿ񵼳�
     */
    private String hy0104;


    public String getHy0104() {
        return this.hy0104;
    }

    public void setHy0104(final String hy0104) {
        this.hy0104 = hy0104;
    }

    /**
     * ���鴴��ʱ��
     */
    private java.util.Date hy0105;


    public java.util.Date getHy0105() {
        return this.hy0105;
    }

    public void setHy0105(final java.util.Date hy0105) {
        this.hy0105 = hy0105;
    }

    /**
     * ���鴴����Ա����
     */
    private String hy0106;


    public String getHy0106() {
        return this.hy0106;
    }

    public void setHy0106(final String hy0106) {
        this.hy0106 = hy0106;
    }

}
