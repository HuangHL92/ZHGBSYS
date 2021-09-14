package com.insigma.siis.local.pagemodel.sysorg.org;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkComm;
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkSqlPj;
import com.insigma.siis.local.pagemodel.sysorg.org.gbjbqk.GbjbqkSqlZs;

public class GbjbqkSubPageModel extends PageModel{
	public final static String row[]={" 1=0 ",
		" 1=0 ",//1
		" 1=0 ",//2
		"",//3
		"",
		"1A",//5行 小计 综合管理类公务员
		"1A01",//国家级正职
		"1A02",//国家级副职
		"1A11",//省部级正职
		"1A12",//省部级副职
		"1A21",//厅局级正职
		"1A22",//厅局级副职
		"1A31",//县处级正职 11行
		"1A32",//县处级副职
		"1A41",//乡科级正职
		"1A42",//乡科级副职
		"1A50",//科员 
		"1A60",//办事员 
		"1A98",//试用期人员
		"1A99",//其他 
		
		"1B",//小计 01B 专业技术类公务员 20行
		 "1B01",//一级总监     
		 "1B02",//二级总监     
		 "1B03",//一级高级主管
		 "1B04",//二级高级主管
		 "1B05",//三级高级主管
		 "1B06",//四级高级主管
		 "1B07",//一级主管
		 "1B08",//二级主管
		 "1B09",//三级主管
		 "1B10",//四级主管
		 "1B11",//专业技术员
		 "1B98",//试用期人员
		 "1B99",//其他
		 
		 "1C",//行政执法类公务员 小计 34行
		 "1C01",//督办  
		 "1C02",//一级高级主办
		 "1C03",//二级高级主办
		 "1C04",//三级高级主办
		 "1C05",//四级高级主办
		 "1C06",//一级主办
		 "1C07",//二级主办
		 "1C08",//三级主办
		 "1C09",//四级主办
		 "1C10",//一级行政执法员
		 "1C11",//二级行政执法员
		 "1C98",//试用期人员
		 "1C99",//其他 
		 
		 "2",//人民警察职务序列 小计 48行
		 "20",//一级警长
		 "21",//二级警长
		 "22",//三级警长
		 "23",//四级警长
		 "24",//一级警员
		 "25",//二级警员
		 "26",//三级警员
		 "27",//试用期人员
		 "28",//其他 
		 
		 "3",// 57 法官等级 小计
		 "301", //首席大法官
		 "302", //一级大法官
		 "303", //二级大法官
		 "304", //一级高级法官
		 "305", //二级高级法官
		 "306", //三级高级法官"
		 "307", //四级高级法官
		 "308", //一级法官
		 "309", //二级法官
		 "310", //三级法官
		 "311", //四级法官
		 "312", //五级法官
		 
		 "4",//检察官等级 小计 71 行A
		 "401", //首席大检察官
		 "402", //一级大检察官
		 "403", //二级大检察官
		 "404", //一级高级检察官
		 "405", //二级高级检察官
		 "406", //三级高级检察官
		 "407", //四级高级检察官
		 "408", //一级检察官
		 "409", //二级检察官
		 "410", //三级检察官
		 "411", //四级检察官
		 "412", //五级检察官
		 
		 "5",//警务技术等级 84行 小计
		 "501", //警务技术一级总监
		 "502", //警务技术二级总监
		 "503", //警务技术一级主任
		 "504", //警务技术二级主任
		 "505", //警务技术三级主任
		 "506", //警务技术四级主任
		 "507", //警务技术一级主管
		 "508", //警务技术二级主管
		 "509", //警务技术三级主管
		 "510", //警务技术四级主管
		 "511", //警务技术员
		 
		 "6",//执法勤务警员职务等级 小计 96
		 "601", //一级警务专员
		 "602", //二级警务专员
		 "603", //一级高级警长
		 "604", //二级高级警长
		 "605", //三级高级警长
		 "606", //四级高级警长
		 "607", //一级警长
		 "608", //二级警长
		 "609", //三级警长
		 "610", //四级警长
		 "611", //一级警员
		 "612", //二级警员
		 
		 "71",//深圳市执法员 108 小计 
		 "7101",//高级执法员
		 "7102",//一级执法员
		 "7103",//二级执法员
		 "7104",//三级执法员
		 "7105",//四级执法员
		 "7106",//五级执法员
		 "7107",//六级执法员
		 "7108",//七级执法员
		 "7109",//助理执法员
		 "7110",//见习执法员
		 
		 "72",//深圳市警员 120 小计
		 "7201",//一级高级警长
		 "7202",//二级高级警长
		 "7203",//一级警长
		 "7204",//二级警长
		 "7205",//三级警长
		 "7206",//四级警长
		 "7207",//一级警员
		 "7208",//二级警员
		 "7209",//三级警员
		 "7210",//四级警员
		 "7211",//初级警员
		 "7212",//见习警员
		 
		 "74",
		"7401",//气象预报总主任
		"7402",//气象预报高级主任
		"7403",//气象预报主任
		"7404",//气象预报一级主管
		"7405",//气象预报二级主管
		"7406",//气象预报三级主管
		"7407",//气象预报助理
		
		"75",
		"7501",//气象信息高级主任
		"7502",//气象信息主任
		"7503",//气象信息一级主管
		"7504",//气象信息二级主管
		"7505",//气象信息三级主管
		"7506",//气象信息助理
		
		 "73",//深圳警务技术职务 141 小计
		 "7301",//一级技术警察
		 "7302",//二级技术警察
		 "7303",//三级技术警察
		 "7304",//四级技术警察
		 "7305",//五级技术警察
		 "7306",//六级技术警察
		 "7307",//七级技术警察
		 "7308",//八级技术警察
		 "7309",//九级技术警察
		 "7310",//十级技术警察
		 
		 "9",//事业单位管理等级 152 小计
		 "901",//一级职员
		 "902",//二级职员
		 "903",//三级职员
		 "904",//四级职员
		 "905",//五级职员
		 "906",//六级职员
		 "907",//七级职员
		 "908",//八级职员
		 "909",//九级职员
		 "910",//十级职员
		 "912"//其他
	};
	public final static String col[]={" 1=0 "," 1=0 "," 1=0 ","",
								" nvl(a01.a0104,0)='2' ",         //4女
								" 1=0 ",//
								" nvl(a01.a0117,0)!='01' ",        //6少数民族
								" 1=0 ",//
								" (nvl(a01.a0141,0)='01' or nvl(a01.a0141,0)='02') ",         //8中共党员
								" 1=0 ",//
								" nvl(a01.a0141,0) not in ('01','02') ",         //非中共党员10
								" 1=0 ",
								" nvl(a08.a0801b,0) like '1%' ",          //研究生12
								" 1=0 ",//
								" nvl(a08.a0801b,0) like '2%' ",          //大学本科14
								" 1=0 ",//
								" nvl(a08.a0801b,0) like '3%' ",          //大学专科16
								" 1=0 ",//
								" nvl(a08.a0801b,0) like '4%' ",          //中专18
								" 1=0 ",//
								" nvl(a08.a0801b,61) in ('61','71','81','91') ",//高中及以20
								" 1=0 ",//
								" (nvl(a08.a0901b,0)=1 or nvl(a08.a0901b,0) like '2%') ",        //博士22
								" 1=0 ",//
								" nvl(a08.a0901b,0) like '3%' ",          //硕士24
								" 1=0 ",//
								" nvl(a08.a0901b,4) like '4%' ",          //学士26
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(add_months(sysdate,-455),'yyyy-mm')),'yyyy-mm'))/12) < 35 ",          //35岁及以下28
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=40 and floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=36 ",       //36岁至40
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=45 and floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=41 ",       //41岁至45
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=50 and floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=46",       //46岁至50
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=55 and floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=51",       //51岁至55
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=59 and floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=56",       //56岁至59
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=60 ",          //60岁及以上
								" 1=0 ",//
								" 1=0 ",                //平均年龄
								" add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-13),'yyyy-mm-dd')),'yyyy-mm-dd'),12)>trunc(sysdate) ",                  //不满1年39
								" 1=0 ",//
								" add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-37),'yyyy-mm-dd') "
							           +" ),'yyyy-mm-dd'),36)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-11),'yyyy-mm-dd') "
							           +" ),'yyyy-mm-dd'),12)<=trunc(sysdate) ",                  //1年至不满3年
							           " 1=0 ",//
								" add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-61),'yyyy-mm-dd') "
							           +" ),'yyyy-mm-dd'),60)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-35),'yyyy-mm-dd') "
							           +" ),'yyyy-mm-dd'),36)<=trunc(sysdate) ",                  //3年至不满5年
							           " 1=0 ",//
								" add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-121),'yyyy-mm-dd') "
							           +" ),'yyyy-mm-dd'),120)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-59),'yyyy-mm-dd') "
							           +" ),'yyyy-mm-dd'),60)<=trunc(sysdate) ",                  //5年至不满10年
							           " 1=0 ",//
								" add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-119),'yyyy-mm-dd') "
										+" ),'yyyy-mm-dd'),120)<=trunc(sysdate) ",                  //10年及以上
										" 1=0 ",//
								" nvl(a01.A0197,0) = 1 "};                 //1 是 0 否具有2年以上基层工作经历人数27
	public final static String col_mysql[]={" 1=0 "," 1=0 "," 1=0 ","",
		" a01.a0104='2' ",         //4女
		" 1=0 ",//
		" a01.a0117!='01' ",        //6少数民族
		" 1=0 ",//
		" a01.a0141 in ('01','02') ",         //8中共党员
		" 1=0 ",//
		" if(a01.a0141 is null,0,a01.a0141) not in ('01','02') ",         //非中共党员10 为空则不是中共党员（统计分析计算逻辑需要）
		" 1=0 ",
		" a08.a0801b like '1%' ",          //研究生12
		" 1=0 ",//
		" a08.a0801b like '2%' ",          //大学本科14
		" 1=0 ",//
		" a08.a0801b like '3%' ",          //大学专科16
		" 1=0 ",//
		" a08.a0801b like '4%' ",          //中专18
		" 1=0 ",//
		" a08.a0801b in ('61','71','81','91') ",//高中及以20
		" 1=0 ",//
		" (a08.a0901b=1 or a08.a0901b like '2%') ",        //博士22
		" 1=0 ",//
		" a08.a0901b like '3%' ",          //硕士24
		" 1=0 ",//
		" a08.a0901b like '4%' ",          //学士26
		" 1=0 ",//
		" floor(  "
			+ "			timestampdiff (month,     "
			//+ "				CURDATE(), "
			+ "					if (   "
			+ "						a01.a0107 is null,   "
			+ "						DATE_FORMAT(  "
			+ "							DATE_SUB(	now(),INTERVAL 455 MONTH),  "
			+ "							'yyyy-mm' "
			+ "						),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107)  "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) <= 35 ",          //35岁及以下28
		" 1=0 ",//
		" floor(   "
			+ "			timestampdiff (month, "
			//+ "				CURDATE(), "
			+ "					if (     "
			+ "						a01.a0107 is null,   "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107) "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d')  "
			+ "			) / 12     "
			+ "		) <= 40        "
			+ "		AND floor(     "
			+ "			timestampdiff (month,     "
			//+ "				CURDATE(), "
			+ "					if (    "
			+ "						a01.a0107 is null,   "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107) "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d')  "
			+ "			) / 12     "
			+ "		)>= 36 ",       //36岁至40 30
		" 1=0 ",//
		" floor(    "
			+ "			timestampdiff (month,   "
			//+ "				CURDATE(),	 "
			+ "					if (     "
			+ "						a01.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107) "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) <= 45       "
			+ "		AND floor(    "
			+ "			timestampdiff (month,     "
			//+ "				CURDATE(),	 "
			+ "					if (     "
			+ "						a01.a0107 is null,   "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107) "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) >= 41 ",       //41岁至45 32
		" 1=0 ",//
		" floor(   "
			+ "			timestampdiff (month,   "
			//+ "				CURDATE(), "
			+ "					if (   "
			+ "						a01.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107)  "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) <= 50       "
			+ "		AND floor(    "
			+ "			timestampdiff (month,   "
			//+ "				CURDATE(), "
			+ "					if (  "
			+ "						a01.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107)   "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) >= 46 ",       //46岁至50 34
		" 1=0 ",//
		" floor(   "
			+ "			timestampdiff (month,   "
			//+ "				CURDATE(), "
			+ "					if (   "
			+ "						a01.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107)  "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) <= 55       "
			+ "		AND floor(    "
			+ "			timestampdiff (month,   "
			//+ "				CURDATE(), "
			+ "					if (  "
			+ "						a01.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107)   "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) >= 51 ",       //51岁至55
		" 1=0 ",//
		" floor(   "
			+ "			timestampdiff (month,   "
			//+ "				CURDATE(), "
			+ "					if (   "
			+ "						a01.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107)  "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) <= 59       "
			+ "		AND floor(    "
			+ "			timestampdiff (month,   "
			//+ "				CURDATE(), "
			+ "					if (  "
			+ "						a01.a0107 is null, "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107)   "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) >= 55 ",       //56岁至59
		" 1=0 ",//
		" floor(   "
			+ "			timestampdiff (month,    "
			//+ "				CURDATE(),	 "
			+ "					if (     "
			+ "						a01.a0107 is null,  "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107)   "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) >= 60 ",          //61岁及以上36
		" 1=0 ",//
		" 1=0 ",                //平均年龄
		" DATE_ADD(STR_TO_DATE(if ( "
			+ "						A0288 is null,      "
			+ "						DATE_FORMAT(   "
			+ "							DATE_SUB(CURDATE(),INTERVAL 13 month) ,  "
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 12 month) > CURDATE() ",                  //不满1年39
		" 1=0 ",//
		" DATE_ADD(STR_TO_DATE(if (  "
			+ "						A0288 is null,       "
			+ "						DATE_FORMAT(    "
			+ "							DATE_SUB(CURDATE(),INTERVAL 37 month) , "
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 36 month) > CURDATE()  "
			+ "		AND DATE_ADD(STR_TO_DATE(if (  "
			+ "						A0288 is null,      "
			+ "						DATE_FORMAT(   "
			+ "							DATE_SUB(CURDATE(),INTERVAL 11 month) ,  "
			+ "							'%Y%m%d'  "
			+ "						),            "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)         "
			+ "					),'%Y%m%d'),INTERVAL 12 month) <= CURDATE() ",//1年至不满3年
	           " 1=0 ",//
		" DATE_ADD(STR_TO_DATE(if (  "
			+ "						A0288 is null,       "
			+ "						DATE_FORMAT(    "
			+ "							DATE_SUB(CURDATE(),INTERVAL 61 month) ,  "
			+ "							'%Y%m%d'  "
			+ "						),            "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)         "
			+ "					),'%Y%m%d'),INTERVAL 60 month) > CURDATE() "
			+ "		AND DATE_ADD(STR_TO_DATE(if ( "
			+ "						A0288 is null,     "
			+ "						DATE_FORMAT(  "
			+ "							DATE_SUB(CURDATE(),INTERVAL 35 month) ,  "
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 36 month) <= CURDATE() ",                  //3年至不满5年
	           " 1=0 ",//
		" DATE_ADD(STR_TO_DATE(if ( "
			+ "						A0288 is null,      "
			+ "						DATE_FORMAT(   "
			+ "							DATE_SUB(CURDATE(),INTERVAL 121 month) , "
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 120 month) > CURDATE()  "
			+ "		AND DATE_ADD(STR_TO_DATE(if ( "
			+ "						A0288 is null,     "
			+ "						DATE_FORMAT(  "
			+ "							DATE_SUB(CURDATE(),INTERVAL 59 month) , "
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 60 month) <= CURDATE() ", //5年至不满10年
	           " 1=0 ",//
		" DATE_ADD(STR_TO_DATE(if (  "
			+ "						A0288 is null,       "
			+ "						DATE_FORMAT(    "
			+ "							DATE_SUB(CURDATE(),INTERVAL 119 month) ,"
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 120 month) <= CURDATE() ",  //10年及以上
				" 1=0 ",//
		" a01.A0197 = 1 "};                 //1 是 0 否具有2年以上基层工作经历人数27
	public final static String xioaji="5,20,34,48,58,71,84,96,109,120,133,141,148,159";

	public GbjbqkSubPageModel() {
	}

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("initX")
	public int initX() throws RadowException{
		this.getPageElement("xy_zwlb").setValue("1");
		String subWinIdBussessId2 = this.getPageElement("subWinIdBussessId").getValue();
		
		if(subWinIdBussessId2==null||subWinIdBussessId2.length()==0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] param_arr = subWinIdBussessId2.split("\\$");
		String groupid = param_arr[0].trim().replace("|", "'").split("group")[0];

		String title=param_arr[param_arr.length-1];
		
		
		String col="";
		String row="";
		String query_tj="";
		col=param_arr[param_arr.length-3];
		row=param_arr[param_arr.length-2];
		query_tj=param_arr[param_arr.length-4];
		if(param_arr.length==6){//1 第一次反查
		}else{//大于一次反查
			int m=(param_arr.length-6)/4;
			for(int i=1;i<=m;i++){
				col=col+","+param_arr[param_arr.length-3-4*i];
				row=row+","+param_arr[param_arr.length-2-4*i];
				title=title+"-"+param_arr[param_arr.length-1-4*i];
				query_tj=query_tj+","+param_arr[param_arr.length-4-4*i];
			}
			
		}
		this.getPageElement("title_h").setValue(title);
		this.getPageElement("dwid_h").setValue(groupid);
		this.getPageElement("col_num_h").setValue(col);
		this.getPageElement("row_num_h").setValue(row);
		this.getPageElement("query_tj_h").setValue(query_tj);
		String userid=SysUtil.getCacheCurrentUser().getId();
		this.getPageElement("userid_h").setValue(userid);
		this.setNextEventName("gridfc.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("gridfc.dogridquery")
	public int zwxxQuery(int start, int limit) throws RadowException {
		String groupid = this.getPageElement("dwid_h").getValue();
		String col=this.getPageElement("col_num_h").getValue();
		String row=this.getPageElement("row_num_h").getValue();
		String query_tj=this.getPageElement("query_tj_h").getValue();
		query_tj="'"+query_tj.replace(",", "','")+"'";
		query_tj=query_tj.replace("请您选择...", "");
		query_tj=query_tj.replace("全部", "");
		query_tj=query_tj.replace(" ", "");
		//query_tj=query_tj.replace("'',", "");
		//query_tj=query_tj.replace(",''", "");
		if(col==null){
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(row==null){
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] param_arr_col = col.split("\\,");
		String[] param_arr_row = row.split("\\,");
		if(param_arr_col.length<=0){
			this.setMainMessage("列参数错误!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(param_arr_row.length<=0){
			this.setMainMessage("行参数错误!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		GbjbqkSqlZs gbjbqksqlzs=new GbjbqkSqlZs();
		StringBuffer sb=new StringBuffer();
		GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		//拼接sql select
		gbjbqksqlpj.returnPjGridSql(sb);
		//拼接sql from 
		sb.append(" "
	          +" FROM A01 a01 left join "//人员基本信息表
				);
		boolean xueli=false; 
		boolean xuewei=false;
	    for(int i=0;i<param_arr_col.length;i++){
	    	 if(Integer.parseInt( param_arr_col[i])<=21||Integer.parseInt( param_arr_col[i])>=28){
	    		 xueli=true;
	    	 }else{
	    		 xuewei=true;
	    	 }
	     }
	     if(xueli==true&&xuewei==true){
	    	 sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0835='1' and A0834='1' ) a08_1 where  a08_1.rank=1) a08 ");
	    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0835='1' and A0834='1' ) a08 ");//学历学位表 A0801B,A0901B,a0000
	     }else if(xueli==true){
	    	 sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0834='1' ) a08_1 where  a08_1.rank=1) a08 ");
	    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0834='1' ) a08 ");//学历学位表 A0801B,A0901B,a0000
	     }else{
	    	 sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08 ");
	    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0835='1' ) a08 ");//学历学位表 A0801B,A0901B,a0000
	     }
        
          sb.append(" on a01.a0000=a08.a0000 "//
         + "  "//人员统一标识符 关联

	         );
        gbjbqksqlpj.sqlPjExists(sb, groupid);
        //条件 a0221 过滤
  	    gbjbqksqlpj.sqlPjA01(sb);
  	   
        for(int i=0;i<param_arr_row.length;i++){//this.row[Integer.valueOf(row)]
        	  new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb);
        }
        if(DBType.ORACLE==DBUtil.getDBType()){
        	  for(int i=0;i<param_arr_col.length;i++){//this.row[Integer.valueOf(row)]
            	  if("3".equals(param_arr_col[i])){//合计
     	        	 
     	         }else{
     	        	 sb.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(param_arr_col[i])]);
     	         }
              }
        }else if(DBType.MYSQL==DBUtil.getDBType()){
        	  for(int i=0;i<param_arr_col.length;i++){//this.row[Integer.valueOf(row)]
            	  if("3".equals(param_arr_col[i])){//合计
     	        	 
     	         }else{
     	        	 sb.append(" and " +GbjbqkSubPageModel.col_mysql[Integer.valueOf(param_arr_col[i])]);
     	         }
              }
        }else{
				throw new RadowException("发现未知数据源，请联系系统管理员!");
        }
        gbjbqksqlzs.query_tj(sb,query_tj);//职务等级  查询条件
		this.pageQuery(sb.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 统计，反查结果
	 * @return
	 * @throws RadowException 
	 * @throws UnsupportedEncodingException 
	 * @throws AppException 
	 */
	@PageEvent("initTj")
	public int init() throws RadowException, UnsupportedEncodingException, AppException{
		CommQuery cq=new CommQuery();
		//设置复选框选中
		this.getPageElement("xianyin").setValue("1");
		//隐藏占比选中
		this.getPageElement("yczb").setValue("1");
		String groupid = this.getPageElement("dwid_h").getValue();
		String col=this.getPageElement("col_num_h").getValue();
		String row=this.getPageElement("row_num_h").getValue();
		String[] param_arr_col = col.split("\\,");
		String[] param_arr_row = row.split("\\,");
		String query_tj=this.getPageElement("query_tj_h").getValue();
		query_tj="'"+query_tj.replace(",", "','")+"'";
		query_tj=query_tj.replace("请您选择...", "");
		query_tj=query_tj.replace(" ", "");
		query_tj=query_tj.replace("全部", "");
		//query_tj=query_tj.replace("'',", "");
		if(param_arr_col.length>6){
			this.setMainMessage("请不要循环做过多的反查!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(param_arr_col.length<=0){
			this.setMainMessage("列参数错误!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(param_arr_row.length<=0){
			this.setMainMessage("行参数错误!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		boolean xueli=false; 
		boolean xuewei=false;
	     for(int i=0;i<param_arr_col.length;i++){
	    	 if(Integer.parseInt( param_arr_col[i])<=21||Integer.parseInt( param_arr_col[i])>=28){
	    		 xueli=true;
	    	 }else{
	    		 xuewei=true;
	    	 }
	     }
		StringBuffer sb=new StringBuffer();
		GbjbqkSqlZs gbjbqksqlzs=new GbjbqkSqlZs();
		GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		//拼接sql select
		gbjbqksqlzs.returnGbjbqkSqlZs1(sb,"");
		//拼接sql from
		gbjbqksqlzs.returnGbjbqkSqlZs11_sub(sb,xueli,xuewei);
		//gbjbqksqlzs.returnGbjbqkSqlZs11(sb);
		//拼接sql where 条件
		//条件 a0221 过滤
		gbjbqksqlpj.sqlPjExists(sb, groupid);
  	    gbjbqksqlpj.sqlPjA01(sb);
		for(int i=0;i<param_arr_row.length;i++){//行条件
			 new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb);
		}
		for(int i=0;i<param_arr_col.length;i++){//列条件
			 if("3".equals(param_arr_col[i])){//合计
	        	 
		      }else{
		        	 sb.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(param_arr_col[i])]);
		      }
		}
		gbjbqksqlzs.query_tj(sb,query_tj);//职务等级  查询条件
        sb.append(" ) a group by A0221 "//当前职务层次 分组
          );
         //分组查询(包含学历)()
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		
		////仅仅统计学位信息
		StringBuffer sb1=new StringBuffer();
		//拼接sql select
		gbjbqksqlzs.returnGbjbqkSqlZs2(sb1);
		//拼接sqlfrom a08
		gbjbqksqlzs.returnGbjbqkSqlZs21_sub(sb1,xueli,xuewei);
		//拼接sqlfrom a02 与权限表
		gbjbqksqlpj.sqlPjExists(sb1, groupid);
		
		//条件 a0221 过滤
  	    gbjbqksqlpj.sqlPjA01(sb1);
       
		for(int i=0;i<param_arr_row.length;i++){//行条件
			new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb1);
		 }
		 for(int i=0;i<param_arr_col.length;i++){//列条件
			 if("3".equals(param_arr_col[i])){//合计
	        	 
		      }else{
		        	 sb1.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(param_arr_col[i])]);
		      }
		}
		gbjbqksqlzs.query_tj(sb1,query_tj);//职务等级  查询条件
	    sb1.append(" ) a group by A0221 "//当前职务层次 分组
	     );
	    //分组查询 学位 单独统计
		List<HashMap<String, Object>> list1=cq.getListBySQL(sb1.toString());
		list=combine(list,list1);
		//list map数据 转换成 jsonString
		StringBuffer ss=new GbjbqkComm().toJson(list);
		this.getPageElement("jsonString_str").setValue(ss.toString());
		
		this.getExecuteSG().addExecuteCode("json_func()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 合并两个 List<HashMap<String, Object>> list1中的数据合并到list中
	 * @param list
	 * @param list1
	 * @return
	 */
	public List<HashMap<String, Object>> combine(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1){
		if(list==null||list1==null||list1.size()==0||list.size()==0){
			return list;
		}
		String temp="";
		
		//list.size()<=list1.size();
		if(list.size()<=list1.size()){
			for(int i=0;i<list.size();i++){
				if(list.get(i).get("a0221")==null||"".equals(list.get(i).get("a0221"))){
					continue;
				}
				
				temp=(String)list.get(i).get("a0221");
				if(temp.equals((String)list1.get(i).get("a0221"))){
					list.get(i).put("bs", list1.get(i).get("bs"));//博士
					list.get(i).put("ss", list1.get(i).get("ss"));//硕士
					list.get(i).put("xs", list1.get(i).get("xs"));//学士
				}else{
					combine_jy(list,list1,temp,i);
				}
			}
		}else{//list.size()>list1.size();
			for(int i=0;i<list1.size();i++){
				if(list1.get(i).get("a0221")==null||"".equals(list1.get(i).get("a0221"))){
					continue;
				}
				
				temp=(String)list1.get(i).get("a0221");
				if(temp.equals((String)list.get(i).get("a0221"))){
					list.get(i).put("bs", list1.get(i).get("bs"));//博士
					list.get(i).put("ss", list1.get(i).get("ss"));//硕士
					list.get(i).put("xs", list1.get(i).get("xs"));//学士
				}else{
					combine_jy_f(list,list1,temp,i);
				}
			}
		}
		
		return list;
	}
	
	public void combine_jy_f(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list.size();j++){
			if(temp.equals((String)list.get(j).get("a0221"))){
				list.get(j).put("bs", list1.get(i).get("bs"));//博士
				list.get(j).put("ss", list1.get(i).get("ss"));//硕士
				list.get(j).put("xs", list1.get(i).get("xs"));//学士
			}
		}
	}
	
	public void combine_jy(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("bs", list1.get(j).get("bs"));//博士
				list.get(i).put("ss", list1.get(j).get("ss"));//硕士
				list.get(i).put("xs", list1.get(j).get("xs"));//学士
			}
		}
	}
	
	/**
	 * 隐藏全零行
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("xianyin.onclick")
	public int xianyin() throws RadowException{
		
		String xy=this.getPageElement("xianyin").getValue();
		this.getPageElement("xy_zwlb").setValue(xy);
		if("1".equals(xy)){//隐藏
			this.getExecuteSG().addExecuteCode("displayzero('"+xy+"')");
			
		}else{//显示
			this.getExecuteSG().addExecuteCode("xs_zwlb_zero()");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 职务类别下拉选 复选
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("zwlb.onchange")
	public int zwlb() throws RadowException{
		
		String zwlb_l=this.getPageElement("zwlb").getValue();
		if(zwlb_l==null||"".equals(zwlb_l)||zwlb_l.length()==0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("zwlb_l").setValue(zwlb_l);
		this.getExecuteSG().addExecuteCode("zwlb_xy()");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 隐藏占比
	 * @throws RadowException 
	 */
	@PageEvent("yczb.onclick")
	public int yczb() throws RadowException{
		String xy=this.getPageElement("yczb").getValue();
		if("1".equals(xy)){//隐藏
			this.getExecuteSG().addExecuteCode("yincangzb()");
		}else{//显示
			this.getExecuteSG().addExecuteCode("xszhanbi()");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
