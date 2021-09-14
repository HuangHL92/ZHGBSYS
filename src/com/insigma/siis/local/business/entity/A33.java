package com.insigma.siis.local.business.entity;

import java.io.Serializable;

import com.insigma.siis.local.epsoft.config.SysCodeUtil;


/**
 * @Oracle工具生成实体
 * @author wangs2
 */
@SuppressWarnings("serial")
public class A33 implements Serializable, Cloneable {
	@Override
	public A33 clone() throws CloneNotSupportedException {
		return (A33)super.clone();
	}
    public A33() {
    }
    private String a0000 ;//人员统一标识符
    private String a3300 ;//主键
    private Integer a3310 ;//基本工资
    private Integer a3321 ;//国家统一的津贴补贴
    private Integer a3321c;//乡镇工作补贴
    private Integer a3321d;//高海拔地区折算工龄补贴
    private Integer a3322 ;//规范津贴补贴
    private Integer a3323 ;// 改革性补贴
    private Integer a3331 ;//年终一次性奖金
    private Integer a3332 ;//立功受奖一次性奖金
    private Integer a3333 ;//奖励性补贴
    private Integer a3350 ;//其他
    private Integer a3361 ;//工资级别
    private String a3362 ;//工资档次
    private String a3371 ;//享受艰苦边远地区津贴级别
    private Integer a3372 ;//所属艰苦边远地区分类区
    private Integer a3381 ;//工作年限增加
    private Integer a3382 ;//工作年限扣减
    private String a3360 ;//工资待遇
    private String a3385 ;//年内领取工资月数
    private String gznx  ;//工作年限
	
    
	
	public String getA0000() {
		return a0000;
	}
	public void setA0000(String a0000) {
		this.a0000 = a0000;
	}
	public String getA3300() {
		return a3300;
	}
	public void setA3300(String a3300) {
		this.a3300 = a3300;
	}
	public Integer getA3310() {
		return a3310;
	}
	public void setA3310(Integer a3310) {
		this.a3310 = a3310;
	}
	public Integer getA3321() {
		return a3321;
	}
	public void setA3321(Integer a3321) {
		this.a3321 = a3321;
	}
	public Integer getA3321c() {
		return a3321c;
	}
	public void setA3321c(Integer a3321c) {
		this.a3321c = a3321c;
	}
	public Integer getA3321d() {
		return a3321d;
	}
	public void setA3321d(Integer a3321d) {
		this.a3321d = a3321d;
	}
	public Integer getA3322() {
		return a3322;
	}
	public void setA3322(Integer a3322) {
		this.a3322 = a3322;
	}
	public Integer getA3323() {
		return a3323;
	}
	public void setA3323(Integer a3323) {
		this.a3323 = a3323;
	}
	public Integer getA3331() {
		return a3331;
	}
	public void setA3331(Integer a3331) {
		this.a3331 = a3331;
	}
	public Integer getA3332() {
		return a3332;
	}
	public void setA3332(Integer a3332) {
		this.a3332 = a3332;
	}
	public Integer getA3333() {
		return a3333;
	}
	public void setA3333(Integer a3333) {
		this.a3333 = a3333;
	}
	public Integer getA3350() {
		return a3350;
	}
	public void setA3350(Integer a3350) {
		this.a3350 = a3350;
	}
	public Integer getA3361() {
		return a3361;
	}
	public void setA3361(Integer a3361) {
		this.a3361 = a3361;
	}
	public String getComboxArea_a3361() {
		return SysCodeUtil.getCodeName("XZ96", this.a3361);
	}
	public String getA3362() {
		return a3362;
	}
	public void setA3362(String a3362) {
		this.a3362 = a3362;
	}
	public String getA3371() {
		return a3371;
	}
	public void setA3371(String a3371) {
		this.a3371 = a3371;
	}
	public Integer getA3372() {
		return a3372;
	}
	public void setA3372(Integer a3372) {
		this.a3372 = a3372;
	}
	public Integer getA3381() {
		return a3381;
	}
	public void setA3381(Integer a3381) {
		this.a3381 = a3381;
	}
	public Integer getA3382() {
		return a3382;
	}
	public void setA3382(Integer a3382) {
		this.a3382 = a3382;
	}
	public String getGznx() {
		return gznx;
	}
	public void setGznx(String gznx) {
		this.gznx = gznx;
	}
	public String getComboxArea_a3360() {
		return SysCodeUtil.getCodeName("XZ95", this.a3360);
	}
	
	public String getA3360() {
		return a3360;
	}
	public void setA3360(String a3360) {
		this.a3360 = a3360;
	}
	public String getA3385() {
		return a3385;
	}
	public void setA3385(String a3385) {
		this.a3385 = a3385;
	}
	@Override
	public String toString() {
		return "A33 [a0000=" + a0000 + ", a3300=" + a3300 + ", a3310=" + a3310 + ", a3321=" + a3321 + ", a3321c="
				+ a3321c + ", a3321d=" + a3321d + ", a3322=" + a3322 + ", a3323=" + a3323 + ", a3331=" + a3331
				+ ", a3332=" + a3332 + ", a3333=" + a3333 + ", a3350=" + a3350 + ", a3361=" + a3361 + ", a3362=" + a3362
				+ ", a3371=" + a3371 + ", a3372=" + a3372 + ", a3381=" + a3381 + ", a3382=" + a3382 + ", a3360=" + a3360
				+ ", a3385=" + a3385 + ", gznx=" + gznx + "]";
	}
	
}
