package com.insigma.siis.local.business.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.commform.DateUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A01temp;
import com.insigma.siis.local.business.entity.A02temp;
import com.insigma.siis.local.business.entity.A08temp;
import com.insigma.siis.local.business.entity.A29temp;
import com.insigma.siis.local.business.entity.A36temp;
import com.insigma.siis.local.business.entity.A57temp;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.B01temp;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

@Deprecated
public class DataVerifyUtil {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	/**
	 * 校验A01(人员基本信息)
	 *
	 * 1.错误：
	 *  A0101	姓名					为空
	 *	A0104	性别					为空
	 *	A0107	出生日期				为空
	 *	A0111A	籍贯名称				小于两个汉字或非汉字的
	 *	A0114A	出生地名称				小于两个汉字或非汉字的
	 *	A0117	民族					为空
	 *	A0128	健康状况				为空
	 *	A0134	参加工作时间			为空
	 *	A0158	人员类别/公务员级别		为空
	 *	A0165	管理类别				为空
	 *	A0192B	现工作单位及职务简称	            为空
	 *	A0192A	现工作单位及职务全称		为空
	 *	A1701	简历					为空
	 *	A15Z101	年度考核结果综述			为空(试用期人员除外)；考核年度小于本年度，且必须有上一年度考核情况
	 *	A3921	第二党派				第一党派为“无党派”或“群众”，第二、第三党派不为空的
	 * 2.警告：
	 *	A0107	出生日期				年龄大于65周岁或小于18周岁的
	 *	A0111A	籍贯名称				除四个直辖市外，小于四个汉字的
	 *	A0114A	出生地名称				除四个直辖市外，小于四个汉字的
	 * 3.提示：
	 *	A0134	参加工作时间	 		小于16周岁参加工作的
	 *	A0144	入党时间文字			小于18周岁加入中国共产党的
	 *	A0184	身份证号				 身份证号为15位的最后一位，身份证号为18号的倒数第二位，“1”为“男”，“2”为“女”，须与性别校核
	 *	A1701	简历					“简历”文字中须包括“参加工作时间”
	 *	A0144	参加组织日期			小于18周岁加入中国共产党的
	 *	
	 * @author mengl
	 * @param a01temp
	 * @return
	 * @throws ParseException 
	 */
	public static A01temp verifyA01(A01temp a01temp)  {
		Date a0107 = null;
		Date a0134 = null;
		Date  a0144 = null;
		StringBuffer errorInfo = new StringBuffer();	//错误信息
		StringBuffer warningInfo = new StringBuffer();	//警告信息
		StringBuffer promptInfo = new StringBuffer();	//提示信息
		try {
			a0107 = sdf.parse(a01temp.getA0107().length()==8?a01temp.getA0107():a01temp.getA0107()+"01");
			a0134 = sdf.parse(a01temp.getA0134().length()==8?a01temp.getA0134():a01temp.getA0134()+"01");
			a0144 = sdf.parse(a01temp.getA0144().length()==8?a01temp.getA0144():a01temp.getA0144()+"01");
		} catch (ParseException e) {
			e.printStackTrace();
			errorInfo.append("参加工作时间/参加组织日期都应为‘yyyyMMdd’格式;");
		}
		
		
		
		//1.错误信息处理
		if(StringUtil.isEmpty(a01temp.getA0101()	)){
			errorInfo.append("姓名为空;");
		}
		if(StringUtil.isEmpty(a01temp.getA0104()	)){
			errorInfo.append("性别为空;");
		}
		if(a0107==null){
			errorInfo.append("出生日期为空;");
		}
		if(!StringUtil.isEmpty(a01temp.getA0111a())){
			if(a01temp.getA0111a().length()<2){
				errorInfo.append("籍贯名称小于两个汉字或非汉字的;");
			}
		}
		if(StringUtil.isEmpty(a01temp.getA0114a())){
			if(a01temp.getA0114a()!=null && a01temp.getA0114a().length()<2){
				errorInfo.append("出生地名称小于两个汉字或非汉字的;");
			}
		}
		if(StringUtil.isEmpty(a01temp.getA0117()	)){
			errorInfo.append("民族为空;");
		}
		if(StringUtil.isEmpty(a01temp.getA0128()	)){
			errorInfo.append("健康状况为空;");
		}
		if(a0134==null){
			errorInfo.append("参加工作时间为空;");
		}
		if(StringUtil.isEmpty(a01temp.getA0158()	)){
			errorInfo.append("人员类别/公务员级别为空;");
		}
		if(StringUtil.isEmpty(a01temp.getA0165()	)){
			errorInfo.append("管理类别为空;");
		}
		if(StringUtil.isEmpty(a01temp.getA0192b())){
			errorInfo.append("现工作单位及职务简称为空;");
		}
		if(StringUtil.isEmpty(a01temp.getA0192a())){
			errorInfo.append("现工作单位及职务全称为空;");
		}
		if(StringUtil.isEmpty(a01temp.getA1701()	)){
			errorInfo.append("简历为空;");
		}
		if(StringUtil.isEmpty(a01temp.getA15z101())){
			if(!StringUtil.isEmpty(a01temp.getA0158()) && !a01temp.getA0158().equals("27") && !a01temp.getA0158().equals("198") ){//公务员级别 ZB09
				errorInfo.append("年度考核结果综述 为空(试用期人员除外);");
			}
			//TODO　暂无法判断    errorInfo.append("年度考核结果综述	   考核年度小于本年度，且必须有上一年度考核情况");
		}
		if(!StringUtil.isEmpty(a01temp.getA3921()) || !StringUtil.isEmpty(a01temp.getA3927())){
			if(StringUtil.isEmpty(a01temp.getA0141()) || a01temp.getA0141().equals("12") || a01temp.getA0141().equals("13")){//GB4762
				errorInfo.append("第一党派为“无党派”或“群众”，第二、第三党派不为空的;");
			}
		}
		
		//2.警告信息处理
		if(a0107!=null){
			if(DateUtil.monthsBetween( a0107,new Date()) >65*12 || DateUtil.monthsBetween( a0107,new Date()) <18*12 ){
				warningInfo.append("出生日期 年龄大于65周岁或小于18周岁的;");
			}
		}
		if(!StringUtil.isEmpty(a01temp.getA0111a())){
			if(!a01temp.getA0111a().contains("北京") && !a01temp.getA0111a().contains("天津") && !a01temp.getA0111a().contains("上海") && !a01temp.getA0111a().contains("重庆") && a01temp.getA0111a().length() <4 ){
				warningInfo.append("籍贯名称 除四个直辖市外，小于四个汉字的;");
			}
		}
		if(!StringUtil.isEmpty(a01temp.getA0114a())){
			
			if(!a01temp.getA0114a().contains("北京") && !a01temp.getA0114a().contains("天津") && !a01temp.getA0114a().contains("上海") && !a01temp.getA0114a().contains("重庆") && a01temp.getA0114a().length() <4 ){
				warningInfo.append("出生地名称除四个直辖市外，小于四个汉字;");
			}
		}
		
		//3.提示信息
		if(a0134!=null){
			if(DateUtil.monthsBetween( a0134,new Date()) <16*12 ){
				promptInfo.append("参加工作时间 小于16周岁参加工作的;");
			}
		}	
		if(a0144!=null){
			if(DateUtil.monthsBetween( a0144,new Date()) <18*12 ){
				promptInfo.append("入党时间文字 小于18周岁加入中国共产党的;");
			}
		}	
		if(!StringUtil.isEmpty(a01temp.getA0184())){
			if(a01temp.getA0184().length()==18 ){
				int idFlag = Integer.parseInt(a01temp.getA0184().substring(16, 17))%2;
				if((idFlag==1 && a01temp.getA0104().equals("2")) || (idFlag==0 && a01temp.getA0104().equals("1")) ){
					promptInfo.append("身份证号为15位的最后一位，身份证号为18号的倒数第二位，“1”为“男”，“2”为“女”，须与性别校核;");
				}
				
			}else if(a01temp.getA0184().length()==15){
				int idFlag = Integer.parseInt(a01temp.getA0184().substring(14, 15))%2;
				if((idFlag==1 && a01temp.getA0104().equals("2")) || (idFlag==0 && a01temp.getA0104().equals("1")) ){
					promptInfo.append("身份证号为15位的最后一位，身份证号为18号的倒数第二位，“1”为“男”，“2”为“女”，须与性别校核;");
				}
				
			}else{
				//TODO 身份证号不是18位  也不是15位，应该写入错误信息
			}
			
		}	
		if(!StringUtil.isEmpty(a01temp.getA1701()) && a01temp.getA1701().contains("参加工作时间") ){
			promptInfo.append("“简历”文字中须包括“参加工作时间”;");
		}	
		
		/*if(a0144!=null){
			if(DateUtil.monthsBetween( a0144,new Date()) <18*12 ){
			promptInfo.append("参加组织日期			小于18周岁加入中国共产党的;");
		}*/	
		
		
		//TODO 未处理警告信息，提示信息
		a01temp.setErrorinfo(errorInfo.toString());
		if(errorInfo.toString().equals("")){
			a01temp.setIsqualified("0");
		}else{
			a01temp.setIsqualified("1");
		}
		
		return a01temp;
	}
	
	
	/**
	 * 校验A02（职务信息【仅对现职务检查】）
	 * 
	 * 1.错误信息：
	 *  A0201A	任职机构名称			为空
	 *	A0215A	职务名称				为空
	 *	A0219	是否领导职务/职务类别		为空
	 *	A0221	职务层次				为空
	 *	A0277	岗位类别				为空
	 *	A0243	决定或批准任职的时间		不介于“参加工作时间”与“当前时间”之间的
	 *	A0243	任职方式				为空
	 *	A0265	决定或批准免职的时间		“任职状态”为“已免”时，“决定或批准免职的时间”为空的
	 *	A0288	任现职务层次时间			为空；“任现职务层次时间”大于“决定或批准任职的时间”的
	 * 2.警告信息 ：无
	 * 3.提示信息：
	 *	A0201	任职机构代码			“其他单位”的
	 *	A0201D	是否班子成员			为空
	 *	A0221	职务层次	  			职务层次高于任职机构级别的
	 *	A0243	决定或批准任职的时间		“简历”文字中须包括“决定或批准任职的时间”
	 *	A0259	是否破格提拔			职动类型为“逐级晋升”的
	 *	
	 * @author mengl
	 * @param a02temp
	 * @return
	 * @throws ParseException 
	 */
	public static A02temp verifyA02(A02temp a02temp)  {
		Date a0107 = null;
		Date a0134 = null;
		Date  a0144 = null;
		StringBuffer errorInfo = new StringBuffer();	//错误信息
		StringBuffer warningInfo = new StringBuffer();	//警告信息
		StringBuffer promptInfo = new StringBuffer();	//提示信息
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a02temp.getA0000());
		try {
			a0107 = sdf.parse(a01.getA0107().length()==8?a01.getA0107():a01.getA0107()+"01");
			a0134 = sdf.parse(a01.getA0134().length()==8?a01.getA0134():a01.getA0134()+"01");
			a0144 = sdf.parse(a01.getA0144().length()==8?a01.getA0144():a01.getA0144()+"01");
		} catch (ParseException e) {
			e.printStackTrace();
			errorInfo.append("参加工作时间/参加组织日期都应为‘yyyyMMdd’格式;");
		}
		
		//1.错误信息处理
		if(StringUtil.isEmpty(a02temp.getA0201a())){
			errorInfo.append("任职机构名称为空;");
		}	
		if(StringUtil.isEmpty(a02temp.getA0215a())){
			errorInfo.append("职务名称为空;");
		}	
		if(StringUtil.isEmpty(a02temp.getA0219())){
			errorInfo.append("是否领导职务/职务类别为空;");
		}	
		if(StringUtil.isEmpty(a02temp.getA0221())){
			errorInfo.append("职务层次为空;");
		}	
		if(StringUtil.isEmpty(a02temp.getA0277())){
			errorInfo.append("岗位类别为空;");
		}	
		if(!StringUtil.isEmpty(a02temp.getA0243())){ 
			Date a0243 = null;
			//TODO  无真实数据，暂根据数据类型varchar(8)，数据精确到天 yyyyMMdd
			try {
				a0243 = DateUtil.toDate(a02temp.getA0243(), "yyyyMMdd");
			} catch (AppException e) {
				errorInfo.append("决定或批准任职的时间数据精确未到天 yyyyMMdd;");
			}
			
			if(a0243!=null && DateUtil.monthsBetween( a0243,new Date()) <0 ){
				errorInfo.append("决定或批准任职的时间不介于“参加工作时间”与“当前时间”之间的;");
			}
			if(a0243!=null && DateUtil.monthsBetween( a0134,a0243) <0 ){
				errorInfo.append("决定或批准任职的时间不介于“参加工作时间”与“当前时间”之间的;");
			}
			
			
		}	
		if(StringUtil.isEmpty(a02temp.getA0243())){
			errorInfo.append("任职方式为空;");
		}	
		if(StringUtil.isEmpty(a02temp.getA0265())){
			if(!StringUtil.isEmpty(a02temp.getA0255()) && a02temp.getA0255().equals("0")){
				errorInfo.append("决定或批准免职的时间	“任职状态”为“已免”时，“决定或批准免职的时间”为空的;");
			}
		}	
		if(StringUtil.isEmpty(a02temp.getA0288())){
			errorInfo.append("任现职务层次时间为空;");
		//TODO  无真实数据，暂根据数据类型varchar(8)，数据精确到天 yyyyMMdd
		}else if(!StringUtil.isEmpty(a02temp.getA0265()) && a02temp.getA0265().length()==a02temp.getA0288().length() && a02temp.getA0288().compareTo(a02temp.getA0265())>0){
			errorInfo.append("“任现职务层次时间”大于“决定或批准任职的时间”的;");
		}	

		//2.警告信息处理:无
		
		//3.提示信息处理
		B01 b01 = null;
		try {
			b01 = (B01)HBUtil.getHBSession().get(B01.class, a02temp.getA0201b());
		} catch (Exception e) {
			promptInfo.append("任职机构代码	找不到对应单位信息;"); //TODO 自行添加的验证信息
		}
		
		if(!StringUtil.isEmpty(a02temp.getA0201b())){
			
			if(b01!=null && b01.getB0101().contains("其他单位")){
				promptInfo.append("任职机构代码	“其他单位”的;");
			}
			
		}
		if(StringUtil.isEmpty(a02temp.getA0201d())){
			promptInfo.append("是否班子成员为空;");
		}
		if(!StringUtil.isEmpty(a02temp.getA0221())){
			//TODO 职务层次 A0221 -'ZB09'  ； 任职机构级别  B0127- 'ZB03' 。两种参数比较规则比较难以比较
			//promptInfo.append("职务层次	职务层次高于任职机构级别的;");
		}
		if(!StringUtil.isEmpty(a02temp.getA0243())){
			if(StringUtil.isEmpty(a01.getA1701()) || (!StringUtil.isEmpty(a01.getA1701()) && !a01.getA1701().contains("决定或批准任职的时间"))  ){
				promptInfo.append("决定或批准任职的时间 “简历”文字中须包括“决定或批准任职的时间”;");
			}
		}
		if(!StringUtil.isEmpty(a02temp.getA0259())){
			if(a02temp.getA0259().contains("逐级晋升")){
				promptInfo.append("是否破格提拔	职动类型为“逐级晋升”的;");
			}
			
		}
		//TODO 未处理警告信息，提示信息
		a02temp.setErrorinfo(errorInfo.toString());
		if(errorInfo.toString().equals("")){
			a02temp.setIsqualified("0");
		}else{
			a02temp.setIsqualified("1");
		}
		return a02temp;
	}
	
	
	
	
	/**
	 * 校验A08学历学位信息：
	 * 1.错误：
	 *  A0801A	学历名称			为空
	 *  A0804	入学日期			学历名称为“中专或中技”及以上时，“入学日期”为空的；如不为空，“入学日期”小于等于“出生年月”的
	 *  A0807	毕业日期			如不为空，“毕业日期”小于等于“入学日期”的
	 *  A0904	学位授予日期		“学位名称”不为空时，“学位授予日期”为空的；“学位授予日期”不为空时，早于“入学日期”的
	 *  A0814	学校（单位）名称	学历名称为“中专或中技”及以上时，“学校（单位）名称”为空的
	 * 2.警告：
	 *  A0807	毕业日期	    	学历名称为“中专或中技”及以上时，“毕业日期”为空的
	 *  A0824	所学专业名称		学历名称为“中专或中技”及以上时，“所学专业名称”为空的
	 * 3.提示： 无
	 * 
	 * @author mengl
	 * @param a08temp
	 * @return
	 */
	public static A08temp verifyA08(A08temp a08temp) {
		StringBuffer errorInfo = new StringBuffer();
		StringBuffer warningInfo = new StringBuffer();
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a08temp.getA0000());
		//1.错误信息处理
		if(StringUtils.isEmpty(a08temp.getA0801a())){
			errorInfo.append("学历名称为空;");
		}
		
		if(StringUtils.isEmpty(a08temp.getA0804())){
			//学历首字母小于等于4 
			if(!StringUtils.isEmpty(a08temp.getA0801b()) && a08temp.getA0801b().substring(0, 1).compareTo("4")<=0){
				errorInfo.append("学历名称为“中专或中技”及以上时，“入学日期”为空的;");
			}
		}else{
			if(a01.getA0107().compareTo(a08temp.getA0804())<0){
				errorInfo.append("“入学日期”小于等于“出生年月”;");
			}
		}
		
		if(!StringUtils.isEmpty(a08temp.getA0807())){
			if(a08temp.getA0804().compareTo(a08temp.getA0807())>=0){
				errorInfo.append("“毕业日期”小于等于“入学日期”;");
			}
		}
		
		if(StringUtils.isEmpty(a08temp.getA0904())){
			if(!StringUtils.isEmpty(a08temp.getA0901a())){
				errorInfo.append("“学位名称”不为空时，“学位授予日期”为空的;");
			}
		}else{
			if(!StringUtils.isEmpty(a08temp.getA0804()) && a08temp.getA0804().compareTo(a08temp.getA0904())<0){
				errorInfo.append("“学位授予日期”不为空时，早于“入学日期”;");
			}
		}
		
		if(StringUtil.isEmpty(a08temp.getA0814())){
			//学历首字母小于等于4 
			if(!StringUtils.isEmpty(a08temp.getA0801b()) && a08temp.getA0801b().substring(0, 1).compareTo("4")<=0){
				errorInfo.append("学历名称为“中专或中技”及以上时，“学校（单位）名称”为空的;");
			}
		}
		
		
		//2.警告信息处理
		if(StringUtils.isEmpty(a08temp.getA0807())){
			if(!StringUtils.isEmpty(a08temp.getA0801b()) && a08temp.getA0801b().substring(0, 1).compareTo("4")<=0){
				warningInfo.append("学历名称为“中专或中技”及以上时，“毕业日期”为空的;");
			}
		}
		
		if(StringUtils.isEmpty(a08temp.getA0824())){
			if(!StringUtils.isEmpty(a08temp.getA0801b()) && a08temp.getA0801b().substring(0, 1).compareTo("4")<=0){
				warningInfo.append("学历名称为“中专或中技”及以上时，“所学专业名称”为空的;");
			}
		}
		
		//TODO 未处理警告信息，提示信息
		a08temp.setErrorinfo(errorInfo.toString());
		if(errorInfo.toString().equals("")){
			a08temp.setIsqualified("0");
		}else{
			a08temp.setIsqualified("1");
		}
		return a08temp;
	}
	
	/**
	 * 校验A29(进入管理信息)
	 * 
	 * 1.错误信息
	 * A2947	进入公务员队伍时间	人员类别为“公务员”、“参照群团人员”或“参照事业人员”时，“进入公务员队伍时间”为空的；“进入公务员队伍时间”小于“参加工作时间”的
	 * A2949	公务员登记时间		不为空时，“公务员登记时间”小于“进入公务员队伍时间”的；不为空时，“公务员登记时间”早于20060701的
	 * 2.警告信息
	 * A2949	公务员登记时间		为空（试用期人员、军转干部除外）
	 * 3.提示信息：无
	 * 
	 * @author mengl
	 * @param a29temp
	 * @return
	 */
	public static A29temp verifyA29(A29temp a29temp) {
		StringBuffer errorInfo = new StringBuffer();	//错误信息
		StringBuffer warningInfo = new StringBuffer();	//警告信息
		StringBuffer promptInfo = new StringBuffer();	//提示信息
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a29temp.getA0000());
		Date a0134=null;
		try {
			a0134 = sdf.parse(a01.getA0134().length()==8?a01.getA0134():a01.getA0134()+"01");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		//1.错误信息处理
		if(StringUtil.isEmpty(a29temp.getA2947())){
			if(!StringUtil.isEmpty(a01.getA0158()) && a01.getA0158().substring(0, 1).equals("1") ){//A0158 - ZB09
				errorInfo.append("进入公务员队伍时间 人员类别为“公务员”、“参照群团人员”或“参照事业人员”时，“进入公务员队伍时间”为空的;");
			}
		}else if(a0134!=null){
			//TODO  自行添加：无真实数据，暂根据数据类型varchar(8)，数据精确到天 yyyyMMdd
			
			if(a29temp.getA2947().compareTo(a01.getA0134())<0){
				errorInfo.append("“进入公务员队伍时间”小于“参加工作时间”的;");
			}
		}
		if(!StringUtil.isEmpty(a29temp.getA2949())){
			Date a2949 = null;
			//TODO  自行添加：无真实数据，暂根据数据类型varchar(8)，数据精确到天 yyyyMMdd
			try{
				a2949 = DateUtil.toDate(a29temp.getA2949(), "yyyyMMdd");
			}catch(Exception e){
				errorInfo.append("“进入公务员队伍时间”格式应为yyyyMMdd;");
			}
			
			if(a2949!=null && a29temp.getA2949().compareTo("20060701")<0){
				errorInfo.append("“公务员登记时间”早于20060701的;");
			}
			
			if(StringUtil.isEmpty(a29temp.getA2941()) && a29temp.getA2941().length()==8  && a2949!=null && a29temp.getA2949().compareTo(a29temp.getA2941())<0 ){
				errorInfo.append("“公务员登记时间”小于“进入公务员队伍时间”的;");
			}
			
		}
		//2.警告信息处理
		if(StringUtil.isEmpty(a29temp.getA2949())){
			if(a01!=null && a01.getA0158()!=null && !a01.getA0158().equals("27") && !a01.getA0158().equals("198")){//TODO 无法判定军转类别
				warningInfo.append("“公务员登记时间为空（试用期人员、军转干部除外）;");
			}
		}	
		//3.提示信息处理：无
		
		//TODO 未处理警告信息，提示信息
		a29temp.setErrorinfo(errorInfo.toString());
		if(errorInfo.toString().equals("")){
			a29temp.setIsqualified("0");
		}else{
			a29temp.setIsqualified("1");
		}
		return a29temp;
	}
	
	/**
	 * 校验A36(家庭成员及社会关系信息)
	 * 
	 * 1.错误信息
	 *  A3601	人员姓名			为空
	 *  A3604A	人员与该人关系/称谓	为空
	 *  A3611	人员工作单位及职务	为空
	 * 2.警告信息
	 *  A3607	人员出生日期		为空（已去世人员除外）
	 *  A3627	人员政治面貌		为空（18岁以下人员除外）；为“中共党员”或“民主党派”的，且年龄小于18周岁的
	 * 3.提示信息：无
	 * 
	 * @author mengl
	 * @param a36temp
	 * @return
	 */
	public static A36temp verifyA36(A36temp a36temp) {
		StringBuffer errorInfo = new StringBuffer();	//错误信息
		StringBuffer warningInfo = new StringBuffer();	//警告信息
		StringBuffer promptInfo = new StringBuffer();	//提示信息
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a36temp.getA0000());
		//1.错误信息处理
		if(StringUtil.isEmpty(a36temp.getA3601())){
			errorInfo.append("人员姓名为空;");
		}	
		if(StringUtil.isEmpty(a36temp.getA3604a())){
			errorInfo.append("人员与该人关系/称谓 为空;");
		}	
		if(StringUtil.isEmpty(a36temp.getA3611())){
			errorInfo.append("人员工作单位及职务 为空;");
		}	
		
		Date a3607 = null;//TODO 自行添加验证出生日期格式
		if(!StringUtil.isEmpty(a36temp.getA3607())  ){
			try {
				a3607 = DateUtil.toDate(a36temp.getA3607(), "yyyyMMdd");
			} catch (AppException e) {
				errorInfo.append("人员出生日期格式不为yyyyMMdd;");
			}
		}
		
		
		//2.警告信息处理
		if(StringUtil.isEmpty(a36temp.getA3607())){
			//TODO 暂不知哪个字段判断是否去世
			if(StringUtil.isEmpty(a01.getA0128()) && ( a01.getA0128().contains("去世") ||  a01.getA0128().contains("死") ) ){
			}else{
				warningInfo.append("人员出生日期为空（已去世人员除外）;");
			}
		}	
		if(StringUtil.isEmpty(a36temp.getA3627())){
			if(a3607!=null && DateUtil.monthsBetween(a3607, new Date()) > 18*12 ){
				errorInfo.append("人员政治面貌	为空（18岁以下人员除外）;");
			}
		//TODO  政治面貌是否是  GB4762，还是直接是汉字 
		}else if(!StringUtil.isEmpty(a36temp.getA3627()) && !a36temp.getA3627().equals("12") && !a36temp.getA3627().equals("13") && a3607!=null && DateUtil.monthsBetween(a3607, new Date()) < 18*12){
			errorInfo.append("人员政治面貌 为“中共党员”或“民主党派”的，且年龄小于18周岁的;");
		}	
		//3.提示信息处理：无
		
		//TODO 未处理警告信息，提示信息
		a36temp.setErrorinfo(errorInfo.toString());
		if(errorInfo.toString().equals("")){
			a36temp.setIsqualified("0");
		}else{
			a36temp.setIsqualified("1");
		}
		return a36temp;
	}
	
	/**
	 * 校验A57(多媒体信息)
	 * 1.错误信息
	 * A5714	照片		为空
	 * 2.警告信息：无
	 * 3.提示信息：无
	 * 
	 * @author mengl
	 * @param a57temp
	 * @return
	 */
	public static A57temp verifyA57(A57temp a57temp) {
		StringBuffer errorInfo = new StringBuffer();	//错误信息
		StringBuffer warningInfo = new StringBuffer();	//警告信息
		StringBuffer promptInfo = new StringBuffer();	//提示信息
		//1.错误信息处理
		if(StringUtil.isEmpty(a57temp.getA5714())){
			errorInfo.append("照片为空;");
		}
		a57temp.setErrorinfo(errorInfo.toString());
		if(errorInfo.toString().equals("")){
			a57temp.setIsqualified("0");
		}else{
			a57temp.setIsqualified("1");
		}
		return a57temp;
	}
	
	/**
	 * 校验 B01(单位基本信息)
	 * 1.错误信息
	 *  B0101	单位名称					为空
	 *  B0104	单位简称					为空
	 *  B0127	单位级别					为空
	 *  B0194	法人单位标识				如为“法人单位”，法人单位编码为空，或编码内英文字母不是大写的
	 *  
	 *  以下三个字段不能同时为空
	 *  B0227	行政编制数    
	 *  B0232	参照公务员法管理事业单位编制数
	 *  B0233	其他事业编制数
	 *  
	 * 2.警告信息
	 *  B0183	正职领导职数				为空或大于2的
	 *  B0185	副职领导职数				为空
	 * 
	 * 3.提示信息：无
	 * @author mengl
	 * @param B01temp
	 * @return
	 */
	public static B01temp verifyB01(B01temp b01temp) {
		StringBuffer errorInfo = new StringBuffer();	//错误信息
		StringBuffer warningInfo = new StringBuffer();	//警告信息
		StringBuffer promptInfo = new StringBuffer();	//提示信息
		//1.错误信息处理
		if(StringUtil.isEmpty(b01temp.getB0101())){
			errorInfo.append("单位名称为空;");
		}	
		if(StringUtil.isEmpty(b01temp.getB0104())){
			errorInfo.append("单位简称为空;");
		}	
		if(StringUtil.isEmpty(b01temp.getB0127())){
			errorInfo.append("单位级别为空;");
		}	
		if(!StringUtil.isEmpty(b01temp.getB0194()) && b01temp.getB0194().equals("1") ){
			if(StringUtil.isEmpty(b01temp.getB0111()) ||  b01temp.getB0111().matches("\\w*[a-z]+\\w*")){//TODO 正则表达式待确定
				errorInfo.append("法人单位标识 如为“法人单位”，法人单位编码为空，或编码内英文字母不是大写的;");
			}
		}
		if(b01temp.getB0227()==null && b01temp.getB0232()==null && b01temp.getB0233()==null ){
			errorInfo.append("行政编制数、参照公务员法管理事业单位编制数、其他事业编制数 三者不能同时为空;");
		}
		//2.警告信息处理
		
		if(b01temp.getB0183()==null ||b01temp.getB0183() >2 ){
			warningInfo.append("正职领导职数为空或大于2的;");
		}	
		if(b01temp.getB0185()==null){
			warningInfo.append("副职领导职数为空;");
		}	
		//3.提示信息处理
		if(b01temp.getB0188()==null){
			promptInfo.append("同级正职非领导职数为空;");
		}	
		if(b01temp.getB0189()==null){
			promptInfo.append("同级副职非领导职数为空;");
		}	
		if(b01temp.getB0190()==null){
			promptInfo.append("内设机构正职领导职数为空;");
		}	
		if(b01temp.getB0191a()==null){
			promptInfo.append("内设机构副职领导职数为空;");
		}	
		if(b01temp.getB0192()==null){
			promptInfo.append("内设机构同级正职非领导职数为空;");
		}	
		if(b01temp.getB0193()==null){
			promptInfo.append("内设机构同级副职非领导职数为空;");
		}	

		//TODO 未处理警告信息，提示信息
		b01temp.setErrorinfo(errorInfo.toString());
		if(errorInfo.toString().equals("")){
			b01temp.setIsqualified("0");
		}else{
			b01temp.setIsqualified("1");
		}
		return b01temp;
	}
	
	
	public static void main(String[] args) throws Exception {
	/*	String a = "19911005";
		Date s = new Date();
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		Date t = DateUtil.toDate(a, "yyyyMMdd");
		Date b = f.parse(a);*/
		
//		System.out.println(DateUtil.monthsBetween(b,new Date()));
		CommonQueryBS.systemOut("234asd234".matches("\\w*[a-z]+\\w*")+"");
	}
	
	
	
}
