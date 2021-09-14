package com.insigma.siis.local.business.entity;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;


/**
 * @Oracle工具生成实体
 * @author 徐亚涛(xuyatao@126.com)
 * @copytright xuyatao 2010-2015
 */
public class A01 implements Serializable, Cloneable {

	@Override
	public A01 clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (A01)super.clone();
	}
    public A01() {
    }

    private java.lang.String a0197;
    private java.lang.String orgid;
    private java.lang.String a0122;
    private java.lang.String tbrjg;
    private java.lang.String a0120;
    private java.lang.String a0121;
    private java.lang.String a0194u;

    
    /**
     * 人员统一标识符
     */
    private java.lang.String a0000;

    public void setA0000(final java.lang.String a0000) {
        this.a0000 = a0000;
    }

    public java.lang.String getA0000() {
        return this.a0000;
    }
    
    private java.lang.String a0192e;

    public void setA0192e(final java.lang.String a0192e) {
        this.a0192e = a0192e;
    }

    public java.lang.String getA0192e() {
        return this.a0192e;
    }

    /**
     * 姓名
     */
    private java.lang.String a0101;

    public void setA0101(final java.lang.String a0101) {
        this.a0101 = a0101;
    }

    public java.lang.String getA0101() {
        return this.a0101;
    }


    /**
     * 姓名简拼
     */
    private java.lang.String a0102;

    public void setA0102(final java.lang.String a0102) {
        this.a0102 = a0102;
    }

    public java.lang.String getA0102() {
        return this.a0102;
    }
    
    /**
     * 职务层次
     */
    private java.lang.String a0221;

    public void setA0221(final java.lang.String a0221) {
        this.a0221 = a0221;
    }

    public java.lang.String getA0221() {
        return this.a0221;
    }
    /**
     * 任职务层次时间
     */
    private java.lang.String a0288;

    public void setA0288(final java.lang.String a0288) {
        this.a0288 = a0288;
    }

    public java.lang.String getA0288() {
        return this.a0288;
    }
    /**
     * 任该职级时间 
     */
    private java.lang.String a0192c;

    public void setA0192c(final java.lang.String a0192c) {
        this.a0192c = a0192c;
    }

    public java.lang.String getA0192c() {
        return this.a0192c;
    }
    
    /**
     * 正处时间
     */
    private java.lang.String zcsj;

    public void setZcsj(final java.lang.String zcsj) {
        this.zcsj = zcsj;
    }

    public java.lang.String getZcsj() {
        return this.zcsj;
    }
    
    /**
     * 正处时间
     */
    private java.lang.String fcsj;

    public void setFcsj(final java.lang.String fcsj) {
        this.fcsj = fcsj;
    }

    public java.lang.String getFcsj() {
        return this.fcsj;
    }
    
    /**
     * 公务员登记时间
     */
    private java.lang.String a2949;

    public void setA2949(final java.lang.String a2949) {
        this.a2949 = a2949;
    }

    public java.lang.String getA2949() {
        return this.a2949;
    }
    

    /**
     * 性别
     */
    private java.lang.String a0104;

    public void setA0104(final java.lang.String a0104) {
        this.a0104 = a0104;
    }

    public java.lang.String getA0104() {
        return this.a0104;
    }


    /**
     * 性别(汉字)
     */
    private java.lang.String a0104a;

    public void setA0104a(final java.lang.String a0104a) {
        this.a0104a = a0104a;
    }

    public java.lang.String getA0104a() {
        return this.a0104a;
    }


    /**
     * 出生日期(该人在公安户籍管理部门登记注册的、在人事档案中记载的并经组织、干部、人事部门确认的出生年月日)
     */
    private java.lang.String a0107;

    public void setA0107(final java.lang.String a0107) {
        this.a0107 = a0107;
    }

    public java.lang.String getA0107() {
        return this.a0107;
    }
    
    /**
     * 成长地(添加  tongzj2017/5/29)
     */
    private java.lang.String a0115a;

    public void setA0115a(final java.lang.String a0115a) {
        this.a0115a = a0115a;
    }

    public java.lang.String getA0115a() {
        return this.a0115a;
    }

    /**
     * 籍贯
     */
    private java.lang.String a0111;

    public void setA0111(final java.lang.String a0111) {
        this.a0111 = a0111;
    }

    public java.lang.String getA0111() {
        return this.a0111;
    }


    /**
     * 籍贯
     */
    private java.lang.String comboxArea_a0111;

    public java.lang.String getComboxArea_a0111() {
		return comboxArea_a0111;
	}

	public void setComboxArea_a0111(java.lang.String comboxArea_a0111) {
		this.comboxArea_a0111 = comboxArea_a0111;
	}


    /**
     * 出生地
     */
    private java.lang.String a0114;

    public void setA0114(final java.lang.String a0114) {
        this.a0114 = a0114;
    }

    public java.lang.String getA0114() {
        return this.a0114;
    }


    /**
     * 出生地
     */
    private java.lang.String comboxArea_a0114;

    public java.lang.String getComboxArea_a0114() {
		return comboxArea_a0114;
	}

	public void setComboxArea_a0114(java.lang.String comboxArea_a0114) {
		this.comboxArea_a0114 = comboxArea_a0114;
	}

	 /**
     * 职级
     */
    private java.lang.String a0192d;

    /**
     * 民族
     */
    private java.lang.String a0117;

    public void setA0117(final java.lang.String a0117) {
        this.a0117 = a0117;
    }

    public java.lang.String getA0117() {
        return this.a0117;
    }


    /**
     * 民族(汉字)
     */
    private java.lang.String a0117a;

    public void setA0117a(final java.lang.String a0117a) {
        this.a0117a = a0117a;
    }

    public java.lang.String getA0117a() {
        return this.a0117a;
    }


    /**
     * 健康状况
     */
    private java.lang.String a0128;

    public void setA0128(final java.lang.String a0128) {
        this.a0128 = a0128;
    }

    public java.lang.String getA0128() {
        return this.a0128;
    }


    /**
     * 健康状况
     */
    private java.lang.String a0128b;

    public void setA0128b(final java.lang.String a0128b) {
        this.a0128b = a0128b;
    }

    public java.lang.String getA0128b() {
        return this.a0128b;
    }


    /**
     * 参加工作时间(经组织、干部、人事、劳动部门审定的该人参加工作起始日期)
     */
    private java.lang.String a0134;

    public void setA0134(final java.lang.String a0134) {
        this.a0134 = a0134;
    }

    public java.lang.String getA0134() {
        return this.a0134;
    }


    /**
     * 入党时间
     */
    private java.lang.String a0140;

    public void setA0140(final java.lang.String a0140) {
        this.a0140 = a0140;
    }

    public java.lang.String getA0140() {
        return this.a0140;
    }


    /**
     * 政治面貌
     */
    private java.lang.String a0141;

    public void setA0141(final java.lang.String a0141) {
        this.a0141 = a0141;
    }

    public java.lang.String getA0141() {
        return this.a0141;
    }


    /**
     * 记录本人三个政治面貌
     */
    private java.lang.String a0141d;

    public void setA0141d(final java.lang.String a0141d) {
        this.a0141d = a0141d;
    }

    public java.lang.String getA0141d() {
        return this.a0141d;
    }


    /**
     * 入党时间
     */
    private java.lang.String a0144;

    public void setA0144(final java.lang.String a0144) {
        this.a0144 = a0144;
    }

    public java.lang.String getA0144() {
        return this.a0144;
    }


    /**
     * 参加组织时间二
     */
    private java.lang.String a0144b;

    public void setA0144b(final java.lang.String a0144b) {
        this.a0144b = a0144b;
    }

    public java.lang.String getA0144b() {
        return this.a0144b;
    }


    /**
     * 参加组织时间三
     */
    private java.lang.String a0144c;

    public void setA0144c(final java.lang.String a0144c) {
        this.a0144c = a0144c;
    }

    public java.lang.String getA0144c() {
        return this.a0144c;
    }


    /**
     * 职务层次
     */
    private java.lang.String a0148;

    public void setA0148(final java.lang.String a0148) {
        this.a0148 = a0148;
    }

    public java.lang.String getA0148() {
        return this.a0148;
    }


    /**
     * 职务层次批准日期
     */
    private java.lang.String a0148c;

    public void setA0148c(final java.lang.String a0148c) {
        this.a0148c = a0148c;
    }

    public java.lang.String getA0148c() {
        return this.a0148c;
    }


    /**
     * 职务层次
     */
    private java.lang.String a0149;

    public void setA0149(final java.lang.String a0149) {
        this.a0149 = a0149;
    }

    public java.lang.String getA0149() {
        return this.a0149;
    }


    /**
     * 该人参加公务员登记的标识。1—登记；2—暂缓登记；3—不予登记。
     */
    private java.lang.String a0151;

    public void setA0151(final java.lang.String a0151) {
        this.a0151 = a0151;
    }

    public java.lang.String getA0151() {
        return this.a0151;
    }


    /**
     * 公务员登记类别1—公务员；02—参照管理人员；3—其他
     */
    private java.lang.String a0153;

    public void setA0153(final java.lang.String a0153) {
        this.a0153 = a0153;
    }

    public java.lang.String getA0153() {
        return this.a0153;
    }


    /**
     * 公务员登记时间
     */
    private String a0155;

    public void setA0155(final String a0155) {
        this.a0155 = a0155;
    }

    public String getA0155() {
        return this.a0155;
    }


    /**
     * 人事关系所在单位名称
     */
    private java.lang.String a0157;

    public void setA0157(final java.lang.String a0157) {
        this.a0157 = a0157;
    }

    public java.lang.String getA0157() {
        return this.a0157;
    }


    /**
     * 公务员级别
     */
    private java.lang.String a0158;

    public void setA0158(final java.lang.String a0158) {
        this.a0158 = a0158;
    }

    public java.lang.String getA0158() {
        return this.a0158;
    }


    /**
     * 公务员登记后所定职务
     */
    private java.lang.String a0159;

    public void setA0159(final java.lang.String a0159) {
        this.a0159 = a0159;
    }

    public java.lang.String getA0159() {
        return this.a0159;
    }


    /**
     * 职务拼接方式
     */
    private java.lang.String a015a;

    public void setA015a(final java.lang.String a015a) {
        this.a015a = a015a;
    }

    public java.lang.String getA015a() {
        return this.a015a;
    }


    /**
     * 标识该人调出的方式类别
     */
    private java.lang.String a0160;

    public void setA0160(final java.lang.String a0160) {
        this.a0160 = a0160;
    }

    public java.lang.String getA0160() {
        return this.a0160;
    }


    /**
     * 公务员登记信息集备注
     */
    private java.lang.String a0161;

    public void setA0161(final java.lang.String a0161) {
        this.a0161 = a0161;
    }

    public java.lang.String getA0161() {
        return this.a0161;
    }


    /**
     * 公务员级别批准日期
     */
    private java.lang.String a0162;

    public void setA0162(final java.lang.String a0162) {
        this.a0162 = a0162;
    }

    public java.lang.String getA0162() {
        return this.a0162;
    }


    /**
     * 人员状态
     */
    private java.lang.String a0163;

    public void setA0163(final java.lang.String a0163) {
        this.a0163 = a0163;
    }

    public java.lang.String getA0163() {
        return this.a0163;
    }


    /**
     * 管理类别
     */
    private java.lang.String a0165;

    public void setA0165(final java.lang.String a0165) {
        this.a0165 = a0165;
    }

    public java.lang.String getA0165() {
        return this.a0165;
    }


    /**
     * 备注
     */
    private java.lang.String a0180;

    public void setA0180(final java.lang.String a0180) {
        this.a0180 = a0180;
    }

    public java.lang.String getA0180() {
        return this.a0180;
    }


    /**
     * 身份证号
     */
    private java.lang.String a0184;

    public void setA0184(final java.lang.String a0184) {
        this.a0184 = a0184;
    }

    public java.lang.String getA0184() {
        return this.a0184;
    }


    /**
     * 该人的业务专长
     */
    private java.lang.String a0187a;

    public void setA0187a(final java.lang.String a0187a) {
        this.a0187a = a0187a;
    }

    public java.lang.String getA0187a() {
        return this.a0187a;
    }


    /**
     * 与列表关联
     */
    private java.lang.String a0191;

    public void setA0191(final java.lang.String a0191) {
        this.a0191 = a0191;
    }

    public java.lang.String getA0191() {
        return this.a0191;
    }


    /**
     * 工作单位及职务
     */
    private java.lang.String a0192;

    public void setA0192(final java.lang.String a0192) {
        this.a0192 = a0192;
    }

    public java.lang.String getA0192() {
        return this.a0192;
    }


    /**
     * 工作单位及职务全称
     */
    private java.lang.String a0192a;

    public void setA0192a(final java.lang.String a0192a) {
        this.a0192a = a0192a;
    }

    public java.lang.String getA0192a() {
        return this.a0192a;
    }


    /**
     * 工作单位及职务的内部简称
     */
    private java.lang.String a0192b;

    public void setA0192b(final java.lang.String a0192b) {
        this.a0192b = a0192b;
    }

    public java.lang.String getA0192b() {
        return this.a0192b;
    }


    /**
     * 职务层次
     */
    private java.lang.String a0193;

    public void setA0193(final java.lang.String a0193) {
        this.a0193 = a0193;
    }

    public java.lang.String getA0193() {
        return this.a0193;
    }


    /**
     * 基层工作经历年限
     */
    private java.lang.Long a0194;

    public void setA0194(final java.lang.Long a0194) {
        this.a0194 = a0194;
    }

    public java.lang.Long getA0194() {
        return this.a0194;
    }


    /**
     * 统计关系所在单位
     */
    private java.lang.String a0195;

    public void setA0195(final java.lang.String a0195) {
        this.a0195 = a0195;
    }

    public java.lang.String getA0195() {
        return this.a0195;
    }


    /**
     * 专业技术职务
     */
    private java.lang.String a0196;

    public void setA0196(final java.lang.String a0196) {
        this.a0196 = a0196;
    }

    public java.lang.String getA0196() {
        return this.a0196;
    }


    /**
     * 照片路径
     */
    private java.lang.String a0198;

    public void setA0198(final java.lang.String a0198) {
        this.a0198 = a0198;
    }

    public java.lang.String getA0198() {
        return this.a0198;
    }


    /**
     * 用于区分是否是正在增加的人员，1为任免表导入人员；0为正常人员
     */
    private java.lang.String a0199;

    public void setA0199(final java.lang.String a0199) {
        this.a0199 = a0199;
    }

    public java.lang.String getA0199() {
        return this.a0199;
    }


    /**
     * 最高职务层次类别
     */
    private java.lang.String a01k01;

    public void setA01k01(final java.lang.String a01k01) {
        this.a01k01 = a01k01;
    }

    public java.lang.String getA01k01() {
        return this.a01k01;
    }


    /**
     * 该人是否有基层工作经验的标志。
     */
    private java.lang.String a01k02;

    public void setA01k02(final java.lang.String a01k02) {
        this.a01k02 = a01k02;
    }

    public java.lang.String getA01k02() {
        return this.a01k02;
    }


    /**
     * 奖惩情况
     */
    private java.lang.String a14z101;

    public void setA14z101(final java.lang.String a14z101) {
    
    		this.a14z101 = a14z101;
    	
        
    }

    public java.lang.String getA14z101() {
        return this.a14z101;
    }


    /**
     * 年度考核结果
     */
    private java.lang.String a15z101;

    public void setA15z101(final java.lang.String a15z101) {
    	
    		this.a15z101 = a15z101;
    	
        
    }

    public java.lang.String getA15z101() {
        return this.a15z101;
    }


    /**
     * 简历
     */
    private java.lang.String a1701;

    public void setA1701(final java.lang.String a1701) {
    	
    		this.a1701 = a1701;
    	
       
    }

    public java.lang.String getA1701() {
    	if(StringUtils.isEmpty(this.a1701)){
    		return this.a1701;
    	}else{
    		return this.a1701.replace("\u2002", " ");
    	}
    }


    /**
     * 第二党派
     */
    private java.lang.String a3921;

    public void setA3921(final java.lang.String a3921) {
        this.a3921 = a3921;
    }

    public java.lang.String getA3921() {
        return this.a3921;
    }


    /**
     * 第三党派
     */
    private java.lang.String a3927;

    public void setA3927(final java.lang.String a3927) {
        this.a3927 = a3927;
    }

    public java.lang.String getA3927() {
        return this.a3927;
    }


    /**
     * 年龄
     */
    private java.lang.Long age;

    public void setAge(final java.lang.Long age) {
        this.age = age;
    }

    public java.lang.Long getAge() {
        return this.age;
    }


    /**
     * 呈报单位
     */
    private java.lang.String cbdw;

    public void setCbdw(final java.lang.String cbdw) {
        this.cbdw = cbdw;
    }

    public java.lang.String getCbdw() {
        return this.cbdw;
    }


    /**
     * 多次保存结果
     */
    private java.lang.String isvalid;

    public void setIsvalid(final java.lang.String isvalid) {
        this.isvalid = isvalid;
    }

    public java.lang.String getIsvalid() {
        return this.isvalid;
    }


    /**
     * 计算年龄时间
     */
    private java.sql.Date jsnlsj;

    public void setJsnlsj(final java.sql.Date jsnlsj) {
        this.jsnlsj = jsnlsj;
    }

    public java.sql.Date getJsnlsj() {
        return this.jsnlsj;
    }


    /**
     * 年龄
     */
    private java.lang.String nl;

    public void setNl(final java.lang.String nl) {
        this.nl = nl;
    }

    public java.lang.String getNl() {
        return this.nl;
    }


    /**
     * 拟免职务
     */
    private java.lang.String nmzw;

    public void setNmzw(final java.lang.String nmzw) {
        this.nmzw = nmzw;
    }

    public java.lang.String getNmzw() {
        return this.nmzw;
    }


    /**
     * 拟任职务
     */
    private java.lang.String nrzw;

    public void setNrzw(final java.lang.String nrzw) {
        this.nrzw = nrzw;
    }

    public java.lang.String getNrzw() {
        return this.nrzw;
    }


    /**
     * 最高全日制学历
     */
    private java.lang.String qrzxl;

    public void setQrzxl(final java.lang.String qrzxl) {
        this.qrzxl = qrzxl;
    }

    public java.lang.String getQrzxl() {
        return this.qrzxl;
    }


    /**
     * 院校系专业（最高全日制学历）
     */
    private java.lang.String qrzxlxx;

    public void setQrzxlxx(final java.lang.String qrzxlxx) {
        this.qrzxlxx = qrzxlxx;
    }

    public java.lang.String getQrzxlxx() {
        return this.qrzxlxx;
    }


    /**
     * 最高全日制学位
     */
    private java.lang.String qrzxw;

    public void setQrzxw(final java.lang.String qrzxw) {
        this.qrzxw = qrzxw;
    }

    public java.lang.String getQrzxw() {
        return this.qrzxw;
    }


    /**
     * 院校系专业（最高全日制学位）
     */
    private java.lang.String qrzxwxx;

    public void setQrzxwxx(final java.lang.String qrzxwxx) {
        this.qrzxwxx = qrzxwxx;
    }

    public java.lang.String getQrzxwxx() {
        return this.qrzxwxx;
    }


    /**
     * 排序号
     */
    private java.lang.Long resultsortid;

    public void setResultsortid(final java.lang.Long resultsortid) {
        this.resultsortid = resultsortid;
    }

    public java.lang.Long getResultsortid() {
        return this.resultsortid;
    }


    /**
     * 任免理由
     */
    private java.lang.String rmly;

    public void setRmly(final java.lang.String rmly) {
        this.rmly = rmly;
    }

    public java.lang.String getRmly() {
        return this.rmly;
    }


    /**
     * 排序字段
     */
    private java.lang.Long sortid;

    public void setSortid(final java.lang.Long sortid) {
        this.sortid = sortid;
    }

    public java.lang.Long getSortid() {
        return this.sortid;
    }


    /**
     * 填报人
     */
    private java.lang.String tbr;

    public void setTbr(final java.lang.String tbr) {
        this.tbr = tbr;
    }

    public java.lang.String getTbr() {
        return this.tbr;
    }


    /**
     * 填表时间
     */
    private Long tbsj;

    public void setTbsj(final Long tbsj) {
        this.tbsj = tbsj;
    }

    public Long getTbsj() {
        return this.tbsj;
    }


    /**
     * 结果集的用户名id号
     */
    private java.lang.String userlog;

    public void setUserlog(final java.lang.String userlog) {
        this.userlog = userlog;
    }

    public java.lang.String getUserlog() {
        return this.userlog;
    }


    /**
     * 修改人
     */
    private java.lang.String xgr;

    public void setXgr(final java.lang.String xgr) {
        this.xgr = xgr;
    }

    public java.lang.String getXgr() {
        return this.xgr;
    }


    /**
     * 修改时间
     */
    private Long xgsj;

    public void setXgsj(final Long xgsj) {
        this.xgsj = xgsj;
    }

    public Long getXgsj() {
        return this.xgsj;
    }


    /**
     * 最高在职学历
     */
    private java.lang.String zzxl;

    public void setZzxl(final java.lang.String zzxl) {
        this.zzxl = zzxl;
    }

    public java.lang.String getZzxl() {
        return this.zzxl;
    }


    /**
     * 院校系专业（最高在职学历）
     */
    private java.lang.String zzxlxx;

    public void setZzxlxx(final java.lang.String zzxlxx) {
        this.zzxlxx = zzxlxx;
    }

    public java.lang.String getZzxlxx() {
        return this.zzxlxx;
    }


    /**
     * 最高在职学位
     */
    private java.lang.String zzxw;

    public void setZzxw(final java.lang.String zzxw) {
        this.zzxw = zzxw;
    }

    public java.lang.String getZzxw() {
        return this.zzxw;
    }


    /**
     * 院校系专业（最高在职学位）
     */
    private java.lang.String zzxwxx;

    public void setZzxwxx(final java.lang.String zzxwxx) {
        this.zzxwxx = zzxwxx;
    }

    public java.lang.String getZzxwxx() {
        return this.zzxwxx;
    }

	public java.lang.String getA0192d() {
		return a0192d;
	}

	public void setA0192d(java.lang.String a0192d) {
		this.a0192d = a0192d;
	}

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public java.lang.String getTbrjg() {
		return tbrjg;
	}

	public void setTbrjg(java.lang.String tbrjg) {
		this.tbrjg = tbrjg;
	}

	public java.lang.String getA0120() {
		return a0120;
	}

	public void setA0120(java.lang.String a0120) {
		this.a0120 = a0120;
	}

	public java.lang.String getA0121() {
		return a0121;
	}

	public void setA0121(java.lang.String a0121) {
		this.a0121 = a0121;
	}

	public java.lang.String getA0122() {
		return a0122;
	}

	public void setA0122(java.lang.String a0122) {
		this.a0122 = a0122;
	}

	public java.lang.String getA0197() {
		return a0197;
	}

	public void setA0197(java.lang.String a0197) {
		this.a0197 = a0197;
	}
	
	public java.lang.String getA0194u() {
		return a0194u;
	}

	public void setA0194u(java.lang.String a0194u) {
		this.a0194u = a0194u;
	}
	

	private java.lang.String cbdresult;

	public java.lang.String getCbdresult() {
		return cbdresult;
	}

	public void setCbdresult(java.lang.String cbdresult) {
		this.cbdresult = cbdresult;
	}

	public java.lang.String getOrgid() {
		return orgid;
	}

	public void setOrgid(java.lang.String orgid) {
		this.orgid = orgid;
	}
	
	public java.lang.String getA0192f() {
		return a0192f;
	}

	public void setA0192f(java.lang.String a0192f) {
		this.a0192f = a0192f;
	}

	//工作单位及职务全称对应的，任职时间
	private java.lang.String a0192f;

	//最高学历
	private java.lang.String zgxl;
	
	public java.lang.String getZgxl() {
		return zgxl;
	}
	public void setZgxl(java.lang.String zgxl) {
		this.zgxl = zgxl;
	}
	
	//最高学历学校专业
	private java.lang.String zgxlxx;
	
	public java.lang.String getZgxlxx() {
		return zgxlxx;
	}
	public void setZgxlxx(java.lang.String zgxlxx) {
		this.zgxlxx = zgxlxx;
	}
	
	
	//最高学位
	private java.lang.String zgxw;
	
	public java.lang.String getZgxw() {
		return zgxw;
	}
	public void setZgxw(java.lang.String zgxw) {
		this.zgxw = zgxw;
	}
	

	//最高学历学校专业
	private java.lang.String zgxwxx;
	
	public java.lang.String getZgxwxx() {
		return zgxwxx;
	}
	public void setZgxwxx(java.lang.String zgxwxx) {
		this.zgxwxx = zgxwxx;
	}
	
	private String n0150;
	private String a0131;
	private String n0152;

	public String getN0150() {
		return n0150;
	}
	public void setN0150(String n0150) {
		this.n0150 = n0150;
	}
	public String getA0131() {
		return a0131;
	}
	public void setA0131(String a0131) {
		this.a0131 = a0131;
	}
	public String getN0152() {
		return n0152;
	}
	public void setN0152(String n0152) {
		this.n0152 = n0152;
	}
	
	/* 无锡加 */
	
	private String a0190; //干部处审核（0：未审  1：已审）
	private String a0189; //干部一处审核（0：未审  1：已审）

	public String getA0190() {
		return a0190;
	}
	public void setA0190(String a0190) {
		this.a0190 = a0190;
	}
	public String getA0189() {
		return a0189;
	}
	public void setA0189(String a0189) {
		this.a0189 = a0189;
	}
	
	private String a6506;//套改后职级

	public String getA6506() {
		return a6506;
	}
	public void setA6506(String a6506) {
		this.a6506 = a6506;
	}
	
	private String a0188;//乡镇党政正职经历

	public String getA0188() {
		return a0188;
	}
	public void setA0188(String a0188) {
		this.a0188 = a0188;
	}
	
	private String fkbs;//分库标识 1优秀年轻干部，
	private String fkly;//分库其他来源 2科级干部库

	public String getFkbs() {
		return fkbs;
	}
	public String getFkly() {
		return fkly;
	}
	public void setFkbs(String fkbs) {
		this.fkbs = fkbs;
	}
	public void setFkly(String fkly) {
		this.fkly = fkly;
	}
	
	private String a0132;//是否乡镇（街道）党（工）委书记
	private String a0133;//是否乡镇（街道）镇长（主任）

	public String getA0132() {
		return a0132;
	}
	public String getA0133() {
		return a0133;
	}
	public void setA0132(String a0132) {
		this.a0132 = a0132;
	}
	public void setA0133(String a0133) {
		this.a0133 = a0133;
	}
	
	//任相当层次职务职级时间
	private String A0192x;
	
	public String getA0192x() {
		return A0192x;
	}
	
	public void setA0192x(String A0192x) {
		this.A0192x = A0192x;
	}
	
	//担任社团兼职
	private String STJZ;

	public String getSTJZ() {
		return STJZ;
	}
	public void setSTJZ(String sTJZ) {
		STJZ = sTJZ;
	}
	
	//代表委员
	private String Dbwy;

	public String getDbwy() {
		return Dbwy;
	}
	public void setDbwy(String dbwy) {
		Dbwy = dbwy;
	}
	
	
	
}
