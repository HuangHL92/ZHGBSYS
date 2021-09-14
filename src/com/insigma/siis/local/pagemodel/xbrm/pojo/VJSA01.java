package com.insigma.siis.local.pagemodel.xbrm.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.insigma.siis.local.pagemodel.publicServantManage.Data2Long;


/**
 * @Oracle工具生成实体
 * @author 徐亚涛(xuyatao@126.com)
 * @copytright xuyatao 2010-2015
 */
public class VJSA01 implements Serializable, Cloneable {

	@Override
	public VJSA01 clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (VJSA01)super.clone();
	}
    public VJSA01() {
    }

    private String A0000  ;
    private String A0101  ;
    private String A0104  ;
    private String A0117  ;
    private String A0107  ;
    private String A0111A ;
    private String A0140  ;
    private String A0134  ;
    private String A0192A ;
    private Character V_XT   ;
    private String ZGXL   ;
    private BigDecimal JS_SORT;
    
    
	public String getA0104() {
		return A0104;
	}
	public void setA0104(String a0104) {
		A0104 = a0104;
	}
	public String getA0000() {
		return A0000;
	}
	public String getA0101() {
		return A0101;
	}
	public String getA0117() {
		return A0117;
	}
	public String getA0107() {
		return A0107;
	}
	public String getA0111A() {
		return A0111A;
	}
	public String getA0140() {
		return A0140;
	}
	public String getA0134() {
		return A0134;
	}
	public String getA0192A() {
		return A0192A;
	}
	public Character getV_XT() {
		return V_XT;
	}
	public String getZGXL() {
		return ZGXL;
	}
	public BigDecimal getJS_SORT() {
		return JS_SORT;
	}
	public void setA0000(String a0000) {
		A0000 = a0000;
	}
	public void setA0101(String a0101) {
		A0101 = a0101;
	}
	public void setA0117(String a0117) {
		A0117 = a0117;
	}
	public void setA0107(String a0107) {
		A0107 = a0107;
	}
	public void setA0111A(String a0111a) {
		A0111A = a0111a;
	}
	public void setA0140(String a0140) {
		A0140 = a0140;
	}
	public void setA0134(String a0134) {
		A0134 = a0134;
	}
	public void setA0192A(String a0192a) {
		A0192A = a0192a;
	}
	public void setV_XT(Character v_XT) {
		V_XT = v_XT;
	}
	public void setZGXL(String zGXL) {
		ZGXL = zGXL;
	}
	public void setJS_SORT(BigDecimal jS_SORT) {
		JS_SORT = jS_SORT;
	}

}
