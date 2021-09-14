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
		"1A",//5�� С�� �ۺϹ����๫��Ա
		"1A01",//���Ҽ���ְ
		"1A02",//���Ҽ���ְ
		"1A11",//ʡ������ְ
		"1A12",//ʡ������ְ
		"1A21",//���ּ���ְ
		"1A22",//���ּ���ְ
		"1A31",//�ش�����ְ 11��
		"1A32",//�ش�����ְ
		"1A41",//��Ƽ���ְ
		"1A42",//��Ƽ���ְ
		"1A50",//��Ա 
		"1A60",//����Ա 
		"1A98",//��������Ա
		"1A99",//���� 
		
		"1B",//С�� 01B רҵ�����๫��Ա 20��
		 "1B01",//һ���ܼ�     
		 "1B02",//�����ܼ�     
		 "1B03",//һ���߼�����
		 "1B04",//�����߼�����
		 "1B05",//�����߼�����
		 "1B06",//�ļ��߼�����
		 "1B07",//һ������
		 "1B08",//��������
		 "1B09",//��������
		 "1B10",//�ļ�����
		 "1B11",//רҵ����Ա
		 "1B98",//��������Ա
		 "1B99",//����
		 
		 "1C",//����ִ���๫��Ա С�� 34��
		 "1C01",//����  
		 "1C02",//һ���߼�����
		 "1C03",//�����߼�����
		 "1C04",//�����߼�����
		 "1C05",//�ļ��߼�����
		 "1C06",//һ������
		 "1C07",//��������
		 "1C08",//��������
		 "1C09",//�ļ�����
		 "1C10",//һ������ִ��Ա
		 "1C11",//��������ִ��Ա
		 "1C98",//��������Ա
		 "1C99",//���� 
		 
		 "2",//���񾯲�ְ������ С�� 48��
		 "20",//һ������
		 "21",//��������
		 "22",//��������
		 "23",//�ļ�����
		 "24",//һ����Ա
		 "25",//������Ա
		 "26",//������Ա
		 "27",//��������Ա
		 "28",//���� 
		 
		 "3",// 57 ���ٵȼ� С��
		 "301", //��ϯ�󷨹�
		 "302", //һ���󷨹�
		 "303", //�����󷨹�
		 "304", //һ���߼�����
		 "305", //�����߼�����
		 "306", //�����߼�����"
		 "307", //�ļ��߼�����
		 "308", //һ������
		 "309", //��������
		 "310", //��������
		 "311", //�ļ�����
		 "312", //�弶����
		 
		 "4",//���ٵȼ� С�� 71 ��A
		 "401", //��ϯ�����
		 "402", //һ�������
		 "403", //���������
		 "404", //һ���߼�����
		 "405", //�����߼�����
		 "406", //�����߼�����
		 "407", //�ļ��߼�����
		 "408", //һ������
		 "409", //��������
		 "410", //��������
		 "411", //�ļ�����
		 "412", //�弶����
		 
		 "5",//�������ȼ� 84�� С��
		 "501", //������һ���ܼ�
		 "502", //�����������ܼ�
		 "503", //������һ������
		 "504", //��������������
		 "505", //��������������
		 "506", //�������ļ�����
		 "507", //������һ������
		 "508", //��������������
		 "509", //��������������
		 "510", //�������ļ�����
		 "511", //������Ա
		 
		 "6",//ִ������Աְ��ȼ� С�� 96
		 "601", //һ������רԱ
		 "602", //��������רԱ
		 "603", //һ���߼�����
		 "604", //�����߼�����
		 "605", //�����߼�����
		 "606", //�ļ��߼�����
		 "607", //һ������
		 "608", //��������
		 "609", //��������
		 "610", //�ļ�����
		 "611", //һ����Ա
		 "612", //������Ա
		 
		 "71",//������ִ��Ա 108 С�� 
		 "7101",//�߼�ִ��Ա
		 "7102",//һ��ִ��Ա
		 "7103",//����ִ��Ա
		 "7104",//����ִ��Ա
		 "7105",//�ļ�ִ��Ա
		 "7106",//�弶ִ��Ա
		 "7107",//����ִ��Ա
		 "7108",//�߼�ִ��Ա
		 "7109",//����ִ��Ա
		 "7110",//��ϰִ��Ա
		 
		 "72",//�����о�Ա 120 С��
		 "7201",//һ���߼�����
		 "7202",//�����߼�����
		 "7203",//һ������
		 "7204",//��������
		 "7205",//��������
		 "7206",//�ļ�����
		 "7207",//һ����Ա
		 "7208",//������Ա
		 "7209",//������Ա
		 "7210",//�ļ���Ա
		 "7211",//������Ա
		 "7212",//��ϰ��Ա
		 
		 "74",
		"7401",//����Ԥ��������
		"7402",//����Ԥ���߼�����
		"7403",//����Ԥ������
		"7404",//����Ԥ��һ������
		"7405",//����Ԥ����������
		"7406",//����Ԥ����������
		"7407",//����Ԥ������
		
		"75",
		"7501",//������Ϣ�߼�����
		"7502",//������Ϣ����
		"7503",//������Ϣһ������
		"7504",//������Ϣ��������
		"7505",//������Ϣ��������
		"7506",//������Ϣ����
		
		 "73",//���ھ�����ְ�� 141 С��
		 "7301",//һ����������
		 "7302",//������������
		 "7303",//������������
		 "7304",//�ļ���������
		 "7305",//�弶��������
		 "7306",//������������
		 "7307",//�߼���������
		 "7308",//�˼���������
		 "7309",//�ż���������
		 "7310",//ʮ����������
		 
		 "9",//��ҵ��λ����ȼ� 152 С��
		 "901",//һ��ְԱ
		 "902",//����ְԱ
		 "903",//����ְԱ
		 "904",//�ļ�ְԱ
		 "905",//�弶ְԱ
		 "906",//����ְԱ
		 "907",//�߼�ְԱ
		 "908",//�˼�ְԱ
		 "909",//�ż�ְԱ
		 "910",//ʮ��ְԱ
		 "912"//����
	};
	public final static String col[]={" 1=0 "," 1=0 "," 1=0 ","",
								" nvl(a01.a0104,0)='2' ",         //4Ů
								" 1=0 ",//
								" nvl(a01.a0117,0)!='01' ",        //6��������
								" 1=0 ",//
								" (nvl(a01.a0141,0)='01' or nvl(a01.a0141,0)='02') ",         //8�й���Ա
								" 1=0 ",//
								" nvl(a01.a0141,0) not in ('01','02') ",         //���й���Ա10
								" 1=0 ",
								" nvl(a08.a0801b,0) like '1%' ",          //�о���12
								" 1=0 ",//
								" nvl(a08.a0801b,0) like '2%' ",          //��ѧ����14
								" 1=0 ",//
								" nvl(a08.a0801b,0) like '3%' ",          //��ѧר��16
								" 1=0 ",//
								" nvl(a08.a0801b,0) like '4%' ",          //��ר18
								" 1=0 ",//
								" nvl(a08.a0801b,61) in ('61','71','81','91') ",//���м���20
								" 1=0 ",//
								" (nvl(a08.a0901b,0)=1 or nvl(a08.a0901b,0) like '2%') ",        //��ʿ22
								" 1=0 ",//
								" nvl(a08.a0901b,0) like '3%' ",          //˶ʿ24
								" 1=0 ",//
								" nvl(a08.a0901b,4) like '4%' ",          //ѧʿ26
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(add_months(sysdate,-455),'yyyy-mm')),'yyyy-mm'))/12) < 35 ",          //35�꼰����28
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=40 and floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=36 ",       //36����40
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=45 and floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=41 ",       //41����45
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=50 and floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=46",       //46����50
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=55 and floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=51",       //51����55
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)<=59 and floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=56",       //56����59
								" 1=0 ",//
								" floor(months_between(SYSDATE,to_date(nvl(substr(a01.a0107,1,6),to_char(SYSDATE,'yyyy-mm')),'yyyy-mm'))/12)>=60 ",          //60�꼰����
								" 1=0 ",//
								" 1=0 ",                //ƽ������
								" add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-13),'yyyy-mm-dd')),'yyyy-mm-dd'),12)>trunc(sysdate) ",                  //����1��39
								" 1=0 ",//
								" add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-37),'yyyy-mm-dd') "
							           +" ),'yyyy-mm-dd'),36)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-11),'yyyy-mm-dd') "
							           +" ),'yyyy-mm-dd'),12)<=trunc(sysdate) ",                  //1��������3��
							           " 1=0 ",//
								" add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-61),'yyyy-mm-dd') "
							           +" ),'yyyy-mm-dd'),60)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-35),'yyyy-mm-dd') "
							           +" ),'yyyy-mm-dd'),36)<=trunc(sysdate) ",                  //3��������5��
							           " 1=0 ",//
								" add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-121),'yyyy-mm-dd') "
							           +" ),'yyyy-mm-dd'),120)>trunc(sysdate) and  add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-59),'yyyy-mm-dd') "
							           +" ),'yyyy-mm-dd'),60)<=trunc(sysdate) ",                  //5��������10��
							           " 1=0 ",//
								" add_months(to_date(nvl(decode(length(a01.A0288),6,a0288||'01',a0288),to_char(add_months(sysdate,-119),'yyyy-mm-dd') "
										+" ),'yyyy-mm-dd'),120)<=trunc(sysdate) ",                  //10�꼰����
										" 1=0 ",//
								" nvl(a01.A0197,0) = 1 "};                 //1 �� 0 �����2�����ϻ��㹤����������27
	public final static String col_mysql[]={" 1=0 "," 1=0 "," 1=0 ","",
		" a01.a0104='2' ",         //4Ů
		" 1=0 ",//
		" a01.a0117!='01' ",        //6��������
		" 1=0 ",//
		" a01.a0141 in ('01','02') ",         //8�й���Ա
		" 1=0 ",//
		" if(a01.a0141 is null,0,a01.a0141) not in ('01','02') ",         //���й���Ա10 Ϊ�������й���Ա��ͳ�Ʒ��������߼���Ҫ��
		" 1=0 ",
		" a08.a0801b like '1%' ",          //�о���12
		" 1=0 ",//
		" a08.a0801b like '2%' ",          //��ѧ����14
		" 1=0 ",//
		" a08.a0801b like '3%' ",          //��ѧר��16
		" 1=0 ",//
		" a08.a0801b like '4%' ",          //��ר18
		" 1=0 ",//
		" a08.a0801b in ('61','71','81','91') ",//���м���20
		" 1=0 ",//
		" (a08.a0901b=1 or a08.a0901b like '2%') ",        //��ʿ22
		" 1=0 ",//
		" a08.a0901b like '3%' ",          //˶ʿ24
		" 1=0 ",//
		" a08.a0901b like '4%' ",          //ѧʿ26
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
			+ "		) <= 35 ",          //35�꼰����28
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
			+ "		)>= 36 ",       //36����40 30
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
			+ "		) >= 41 ",       //41����45 32
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
			+ "		) >= 46 ",       //46����50 34
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
			+ "		) >= 51 ",       //51����55
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
			+ "		) >= 55 ",       //56����59
		" 1=0 ",//
		" floor(   "
			+ "			timestampdiff (month,    "
			//+ "				CURDATE(),	 "
			+ "					if (     "
			+ "						a01.a0107 is null,  "
			+ "						DATE_FORMAT(CURDATE(), '%y%m%d'),if(length(a01.a0107)=6,CONCAT(a01.a0107,\"01\"),a01.a0107)   "
			+ "					),DATE_FORMAT(CURDATE(),'%y%m%d') "
			+ "			) / 12    "
			+ "		) >= 60 ",          //61�꼰����36
		" 1=0 ",//
		" 1=0 ",                //ƽ������
		" DATE_ADD(STR_TO_DATE(if ( "
			+ "						A0288 is null,      "
			+ "						DATE_FORMAT(   "
			+ "							DATE_SUB(CURDATE(),INTERVAL 13 month) ,  "
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 12 month) > CURDATE() ",                  //����1��39
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
			+ "					),'%Y%m%d'),INTERVAL 12 month) <= CURDATE() ",//1��������3��
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
			+ "					),'%Y%m%d'),INTERVAL 36 month) <= CURDATE() ",                  //3��������5��
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
			+ "					),'%Y%m%d'),INTERVAL 60 month) <= CURDATE() ", //5��������10��
	           " 1=0 ",//
		" DATE_ADD(STR_TO_DATE(if (  "
			+ "						A0288 is null,       "
			+ "						DATE_FORMAT(    "
			+ "							DATE_SUB(CURDATE(),INTERVAL 119 month) ,"
			+ "							'%Y%m%d' "
			+ "						),           "
			+ "						if(length(a0288)=6,CONCAT(A0288,'01'),a0288)        "
			+ "					),'%Y%m%d'),INTERVAL 120 month) <= CURDATE() ",  //10�꼰����
				" 1=0 ",//
		" a01.A0197 = 1 "};                 //1 �� 0 �����2�����ϻ��㹤����������27
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
		if(param_arr.length==6){//1 ��һ�η���
		}else{//����һ�η���
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
		query_tj=query_tj.replace("����ѡ��...", "");
		query_tj=query_tj.replace("ȫ��", "");
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
			this.setMainMessage("�в�������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(param_arr_row.length<=0){
			this.setMainMessage("�в�������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		GbjbqkSqlZs gbjbqksqlzs=new GbjbqkSqlZs();
		StringBuffer sb=new StringBuffer();
		GbjbqkSqlPj gbjbqksqlpj=new GbjbqkSqlPj();
		//ƴ��sql select
		gbjbqksqlpj.returnPjGridSql(sb);
		//ƴ��sql from 
		sb.append(" "
	          +" FROM A01 a01 left join "//��Ա������Ϣ��
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
	    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0835='1' and A0834='1' ) a08 ");//ѧ��ѧλ�� A0801B,A0901B,a0000
	     }else if(xueli==true){
	    	 sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0834='1' ) a08_1 where  a08_1.rank=1) a08 ");
	    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0834='1' ) a08 ");//ѧ��ѧλ�� A0801B,A0901B,a0000
	     }else{
	    	 sb.append( " (select * from  ( select A0801B,a0000,A0901B,row_number() over(partition by a0000 order by a0000 ) rank from a08 where A0835='1' ) a08_1 where  a08_1.rank=1) a08 ");
	    	// sb.append( " (select A0801B,A0901B,a0000 from a08 where A0835='1' ) a08 ");//ѧ��ѧλ�� A0801B,A0901B,a0000
	     }
        
          sb.append(" on a01.a0000=a08.a0000 "//
         + "  "//��Աͳһ��ʶ�� ����

	         );
        gbjbqksqlpj.sqlPjExists(sb, groupid);
        //���� a0221 ����
  	    gbjbqksqlpj.sqlPjA01(sb);
  	   
        for(int i=0;i<param_arr_row.length;i++){//this.row[Integer.valueOf(row)]
        	  new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb);
        }
        if(DBType.ORACLE==DBUtil.getDBType()){
        	  for(int i=0;i<param_arr_col.length;i++){//this.row[Integer.valueOf(row)]
            	  if("3".equals(param_arr_col[i])){//�ϼ�
     	        	 
     	         }else{
     	        	 sb.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(param_arr_col[i])]);
     	         }
              }
        }else if(DBType.MYSQL==DBUtil.getDBType()){
        	  for(int i=0;i<param_arr_col.length;i++){//this.row[Integer.valueOf(row)]
            	  if("3".equals(param_arr_col[i])){//�ϼ�
     	        	 
     	         }else{
     	        	 sb.append(" and " +GbjbqkSubPageModel.col_mysql[Integer.valueOf(param_arr_col[i])]);
     	         }
              }
        }else{
				throw new RadowException("����δ֪����Դ������ϵϵͳ����Ա!");
        }
        gbjbqksqlzs.query_tj(sb,query_tj);//ְ��ȼ�  ��ѯ����
		this.pageQuery(sb.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * ͳ�ƣ�������
	 * @return
	 * @throws RadowException 
	 * @throws UnsupportedEncodingException 
	 * @throws AppException 
	 */
	@PageEvent("initTj")
	public int init() throws RadowException, UnsupportedEncodingException, AppException{
		CommQuery cq=new CommQuery();
		//���ø�ѡ��ѡ��
		this.getPageElement("xianyin").setValue("1");
		//����ռ��ѡ��
		this.getPageElement("yczb").setValue("1");
		String groupid = this.getPageElement("dwid_h").getValue();
		String col=this.getPageElement("col_num_h").getValue();
		String row=this.getPageElement("row_num_h").getValue();
		String[] param_arr_col = col.split("\\,");
		String[] param_arr_row = row.split("\\,");
		String query_tj=this.getPageElement("query_tj_h").getValue();
		query_tj="'"+query_tj.replace(",", "','")+"'";
		query_tj=query_tj.replace("����ѡ��...", "");
		query_tj=query_tj.replace(" ", "");
		query_tj=query_tj.replace("ȫ��", "");
		//query_tj=query_tj.replace("'',", "");
		if(param_arr_col.length>6){
			this.setMainMessage("�벻Ҫѭ��������ķ���!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(param_arr_col.length<=0){
			this.setMainMessage("�в�������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(param_arr_row.length<=0){
			this.setMainMessage("�в�������!");
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
		//ƴ��sql select
		gbjbqksqlzs.returnGbjbqkSqlZs1(sb,"");
		//ƴ��sql from
		gbjbqksqlzs.returnGbjbqkSqlZs11_sub(sb,xueli,xuewei);
		//gbjbqksqlzs.returnGbjbqkSqlZs11(sb);
		//ƴ��sql where ����
		//���� a0221 ����
		gbjbqksqlpj.sqlPjExists(sb, groupid);
  	    gbjbqksqlpj.sqlPjA01(sb);
		for(int i=0;i<param_arr_row.length;i++){//������
			 new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb);
		}
		for(int i=0;i<param_arr_col.length;i++){//������
			 if("3".equals(param_arr_col[i])){//�ϼ�
	        	 
		      }else{
		        	 sb.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(param_arr_col[i])]);
		      }
		}
		gbjbqksqlzs.query_tj(sb,query_tj);//ְ��ȼ�  ��ѯ����
        sb.append(" ) a group by A0221 "//��ǰְ���� ����
          );
         //�����ѯ(����ѧ��)()
		List<HashMap<String, Object>> list=cq.getListBySQL(sb.toString());
		
		////����ͳ��ѧλ��Ϣ
		StringBuffer sb1=new StringBuffer();
		//ƴ��sql select
		gbjbqksqlzs.returnGbjbqkSqlZs2(sb1);
		//ƴ��sqlfrom a08
		gbjbqksqlzs.returnGbjbqkSqlZs21_sub(sb1,xueli,xuewei);
		//ƴ��sqlfrom a02 ��Ȩ�ޱ�
		gbjbqksqlpj.sqlPjExists(sb1, groupid);
		
		//���� a0221 ����
  	    gbjbqksqlpj.sqlPjA01(sb1);
       
		for(int i=0;i<param_arr_row.length;i++){//������
			new GbjbqkSqlPj().sqlPj(param_arr_row[i], sb1);
		 }
		 for(int i=0;i<param_arr_col.length;i++){//������
			 if("3".equals(param_arr_col[i])){//�ϼ�
	        	 
		      }else{
		        	 sb1.append(" and " +GbjbqkSubPageModel.col[Integer.valueOf(param_arr_col[i])]);
		      }
		}
		gbjbqksqlzs.query_tj(sb1,query_tj);//ְ��ȼ�  ��ѯ����
	    sb1.append(" ) a group by A0221 "//��ǰְ���� ����
	     );
	    //�����ѯ ѧλ ����ͳ��
		List<HashMap<String, Object>> list1=cq.getListBySQL(sb1.toString());
		list=combine(list,list1);
		//list map���� ת���� jsonString
		StringBuffer ss=new GbjbqkComm().toJson(list);
		this.getPageElement("jsonString_str").setValue(ss.toString());
		
		this.getExecuteSG().addExecuteCode("json_func()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �ϲ����� List<HashMap<String, Object>> list1�е����ݺϲ���list��
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
					list.get(i).put("bs", list1.get(i).get("bs"));//��ʿ
					list.get(i).put("ss", list1.get(i).get("ss"));//˶ʿ
					list.get(i).put("xs", list1.get(i).get("xs"));//ѧʿ
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
					list.get(i).put("bs", list1.get(i).get("bs"));//��ʿ
					list.get(i).put("ss", list1.get(i).get("ss"));//˶ʿ
					list.get(i).put("xs", list1.get(i).get("xs"));//ѧʿ
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
				list.get(j).put("bs", list1.get(i).get("bs"));//��ʿ
				list.get(j).put("ss", list1.get(i).get("ss"));//˶ʿ
				list.get(j).put("xs", list1.get(i).get("xs"));//ѧʿ
			}
		}
	}
	
	public void combine_jy(List<HashMap<String, Object>> list,List<HashMap<String, Object>> list1,String temp,int i){
		for(int j=0;j<list1.size();j++){
			if(temp.equals((String)list1.get(j).get("a0221"))){
				list.get(i).put("bs", list1.get(j).get("bs"));//��ʿ
				list.get(i).put("ss", list1.get(j).get("ss"));//˶ʿ
				list.get(i).put("xs", list1.get(j).get("xs"));//ѧʿ
			}
		}
	}
	
	/**
	 * ����ȫ����
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("xianyin.onclick")
	public int xianyin() throws RadowException{
		
		String xy=this.getPageElement("xianyin").getValue();
		this.getPageElement("xy_zwlb").setValue(xy);
		if("1".equals(xy)){//����
			this.getExecuteSG().addExecuteCode("displayzero('"+xy+"')");
			
		}else{//��ʾ
			this.getExecuteSG().addExecuteCode("xs_zwlb_zero()");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ְ���������ѡ ��ѡ
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
	 * ����ռ��
	 * @throws RadowException 
	 */
	@PageEvent("yczb.onclick")
	public int yczb() throws RadowException{
		String xy=this.getPageElement("yczb").getValue();
		if("1".equals(xy)){//����
			this.getExecuteSG().addExecuteCode("yincangzb()");
		}else{//��ʾ
			this.getExecuteSG().addExecuteCode("xszhanbi()");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
