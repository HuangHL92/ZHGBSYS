package com.insigma.siis.local.pagemodel.gbmc.pojo;

import com.insigma.siis.local.util.StringUtil;



public class Gbmc {

	//任职机构主键
	/*
	 * private String a0201b; //机构名称 private String b0101;
	 */
	//姓名
	private String a0101;
	//工作单位及职务
    private String a0192a;
    //性别
    private String a0104;
    //民族
    private String a0117;
    //出生年月
    private String a0107;
    //籍贯
    private String a0111a;
    //参加工作时间
    private String a0134;
    //入党时间
    private String a0140;
    //全日制最高学历
    private String qrzxl;
    //全日制最高学历学校
    private String qrzxlxx;
    //在职最高学历
    private String zzxl;
    //在职最高学历 学校
    private String zzxlxx;
    //专业技术职务
    private String a0196;
    //任职时间
    private String a0192f;
    //任职务层次时间
    private String a0288;
    //任职级时间
	private String a0192c;
    //备注  
    private String comments;
	public String getA0101() {
		
		return a0101==null?"":a0101;
	}
	public void setA0101(String a0101) {
		this.a0101 = a0101;
	}
	public String getA0192a() {
		
		return a0192a==null?"":a0192a;
	}
	public void setA0192A(String a0192a) {
		this.a0192a = a0192a;
	}
	public String getA0104() {
		String string = Gbmc2.xbMap.get(a0104);
		if(StringUtil.isEmpty(string))
		return "";
		else
		return string;
	}
	public void setA0104(String a0104) {
		this.a0104 = a0104;
	}
	public String getA0117() {
		String string = Gbmc2.mzMap.get(a0117);
		if(StringUtil.isEmpty(string))
		return "";
		else
		return string;
	}
	public void setA0117(String a0117) {
		this.a0117 = a0117;
	}
	public String getA0111a() {
		
		return a0111a==null?"":a0111a;
	}
	
	public void setA0111A(String a0111a) {
		this.a0111a = a0111a;
	}
	public String getQrzxl() {
	
		return qrzxl==null?"":qrzxl;
	}
	public void setQRZXL(String qrzxl) {
		this.qrzxl = qrzxl;
	}
	public String getQrzxlxx() {
		
		return qrzxlxx==null?"":qrzxlxx;
	}
	public void setQRZXLXX(String qrzxlxx) {
		this.qrzxlxx = qrzxlxx;
	}
	public String getZzxl() {
		
		return zzxl==null?"":zzxl;
	}
	public void setZZXL(String zzxl) {
		this.zzxl = zzxl;
	}
	public String getZzxlxx() {
		
		return zzxlxx==null?"":zzxlxx;
	}
	public void setZZXLXX(String zzxlxx) {
		this.zzxlxx = zzxlxx;
		
	}
	public String getA0107() {
		
		return a0107==null?"":a0107;
	}
	public void setA0107(String a0107) {
		this.a0107 = a0107;
	}
	public String getA0134() {
		
		return a0134==null?"":a0134;
	}
	public void setA0134(String a0134) {
		this.a0134 = a0134;
	}
	public String getA0140() {
		
		return a0140==null?"":a0140;
	}
	public void setA0140(String a0140) {
		this.a0140 = a0140;
	}
	public String getA0192f() {
		
		return a0192f==null?"":a0192f;
	}
	public void setA0192F(String a0192f) {
		this.a0192f = a0192f;
	}
	
	public String getA0192c() {
		return a0192c==null?"":a0192c;
	}
	public void setA0192c(String a0192c) {
		this.a0192c = a0192c;
	}
	public void setA0192C(String a0192c) {
		this.a0192c = a0192c;
	}
	public String getComments() {
		return comments==null?"":comments;
	}
	public void setCOMMENTS(String comments) {
		this.comments = comments;
	}/*
		 * public String getB0101() { return b0101; } public void setB0101(String b0101)
		 * { this.b0101 = b0101; } public String getA0201b() { return a0201b; } public
		 * void setA0201B(String a0201b) { this.a0201b = a0201b; }
		 */
	public String getA0196() {
		return a0196;
	}
	public void setA0196(String a0196) {
		this.a0196 = a0196;
	}
	public void setA0192a(String a0192a) {
		this.a0192a = a0192a;
	}
	public void setA0111a(String a0111a) {
		this.a0111a = a0111a;
	}
	public void setQrzxl(String qrzxl) {
		this.qrzxl = qrzxl;
	}
	public void setQrzxlxx(String qrzxlxx) {
		this.qrzxlxx = qrzxlxx;
	}
	public void setZzxl(String zzxl) {
		this.zzxl = zzxl;
	}
	public void setZzxlxx(String zzxlxx) {
		this.zzxlxx = zzxlxx;
	}
	public void setA0192f(String a0192f) {
		this.a0192f = a0192f;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getA0288() {
		return a0288;
	}
	public void setA0288(String a0288) {
		this.a0288 = a0288;
	}
    
    
 
}
