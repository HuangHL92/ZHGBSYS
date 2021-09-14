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
	 * У��A01(��Ա������Ϣ)
	 *
	 * 1.����
	 *  A0101	����					Ϊ��
	 *	A0104	�Ա�					Ϊ��
	 *	A0107	��������				Ϊ��
	 *	A0111A	��������				С���������ֻ�Ǻ��ֵ�
	 *	A0114A	����������				С���������ֻ�Ǻ��ֵ�
	 *	A0117	����					Ϊ��
	 *	A0128	����״��				Ϊ��
	 *	A0134	�μӹ���ʱ��			Ϊ��
	 *	A0158	��Ա���/����Ա����		Ϊ��
	 *	A0165	�������				Ϊ��
	 *	A0192B	�ֹ�����λ��ְ����	            Ϊ��
	 *	A0192A	�ֹ�����λ��ְ��ȫ��		Ϊ��
	 *	A1701	����					Ϊ��
	 *	A15Z101	��ȿ��˽������			Ϊ��(��������Ա����)���������С�ڱ���ȣ��ұ�������һ��ȿ������
	 *	A3921	�ڶ�����				��һ����Ϊ���޵��ɡ���Ⱥ�ڡ����ڶ����������ɲ�Ϊ�յ�
	 * 2.���棺
	 *	A0107	��������				�������65�����С��18�����
	 *	A0111A	��������				���ĸ�ֱϽ���⣬С���ĸ����ֵ�
	 *	A0114A	����������				���ĸ�ֱϽ���⣬С���ĸ����ֵ�
	 * 3.��ʾ��
	 *	A0134	�μӹ���ʱ��	 		С��16����μӹ�����
	 *	A0144	�뵳ʱ������			С��18��������й���������
	 *	A0184	���֤��				 ���֤��Ϊ15λ�����һλ�����֤��Ϊ18�ŵĵ����ڶ�λ����1��Ϊ���С�����2��Ϊ��Ů���������Ա�У��
	 *	A1701	����					����������������������μӹ���ʱ�䡱
	 *	A0144	�μ���֯����			С��18��������й���������
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
		StringBuffer errorInfo = new StringBuffer();	//������Ϣ
		StringBuffer warningInfo = new StringBuffer();	//������Ϣ
		StringBuffer promptInfo = new StringBuffer();	//��ʾ��Ϣ
		try {
			a0107 = sdf.parse(a01temp.getA0107().length()==8?a01temp.getA0107():a01temp.getA0107()+"01");
			a0134 = sdf.parse(a01temp.getA0134().length()==8?a01temp.getA0134():a01temp.getA0134()+"01");
			a0144 = sdf.parse(a01temp.getA0144().length()==8?a01temp.getA0144():a01temp.getA0144()+"01");
		} catch (ParseException e) {
			e.printStackTrace();
			errorInfo.append("�μӹ���ʱ��/�μ���֯���ڶ�ӦΪ��yyyyMMdd����ʽ;");
		}
		
		
		
		//1.������Ϣ����
		if(StringUtil.isEmpty(a01temp.getA0101()	)){
			errorInfo.append("����Ϊ��;");
		}
		if(StringUtil.isEmpty(a01temp.getA0104()	)){
			errorInfo.append("�Ա�Ϊ��;");
		}
		if(a0107==null){
			errorInfo.append("��������Ϊ��;");
		}
		if(!StringUtil.isEmpty(a01temp.getA0111a())){
			if(a01temp.getA0111a().length()<2){
				errorInfo.append("��������С���������ֻ�Ǻ��ֵ�;");
			}
		}
		if(StringUtil.isEmpty(a01temp.getA0114a())){
			if(a01temp.getA0114a()!=null && a01temp.getA0114a().length()<2){
				errorInfo.append("����������С���������ֻ�Ǻ��ֵ�;");
			}
		}
		if(StringUtil.isEmpty(a01temp.getA0117()	)){
			errorInfo.append("����Ϊ��;");
		}
		if(StringUtil.isEmpty(a01temp.getA0128()	)){
			errorInfo.append("����״��Ϊ��;");
		}
		if(a0134==null){
			errorInfo.append("�μӹ���ʱ��Ϊ��;");
		}
		if(StringUtil.isEmpty(a01temp.getA0158()	)){
			errorInfo.append("��Ա���/����Ա����Ϊ��;");
		}
		if(StringUtil.isEmpty(a01temp.getA0165()	)){
			errorInfo.append("�������Ϊ��;");
		}
		if(StringUtil.isEmpty(a01temp.getA0192b())){
			errorInfo.append("�ֹ�����λ��ְ����Ϊ��;");
		}
		if(StringUtil.isEmpty(a01temp.getA0192a())){
			errorInfo.append("�ֹ�����λ��ְ��ȫ��Ϊ��;");
		}
		if(StringUtil.isEmpty(a01temp.getA1701()	)){
			errorInfo.append("����Ϊ��;");
		}
		if(StringUtil.isEmpty(a01temp.getA15z101())){
			if(!StringUtil.isEmpty(a01temp.getA0158()) && !a01temp.getA0158().equals("27") && !a01temp.getA0158().equals("198") ){//����Ա���� ZB09
				errorInfo.append("��ȿ��˽������ Ϊ��(��������Ա����);");
			}
			//TODO�����޷��ж�    errorInfo.append("��ȿ��˽������	   �������С�ڱ���ȣ��ұ�������һ��ȿ������");
		}
		if(!StringUtil.isEmpty(a01temp.getA3921()) || !StringUtil.isEmpty(a01temp.getA3927())){
			if(StringUtil.isEmpty(a01temp.getA0141()) || a01temp.getA0141().equals("12") || a01temp.getA0141().equals("13")){//GB4762
				errorInfo.append("��һ����Ϊ���޵��ɡ���Ⱥ�ڡ����ڶ����������ɲ�Ϊ�յ�;");
			}
		}
		
		//2.������Ϣ����
		if(a0107!=null){
			if(DateUtil.monthsBetween( a0107,new Date()) >65*12 || DateUtil.monthsBetween( a0107,new Date()) <18*12 ){
				warningInfo.append("�������� �������65�����С��18�����;");
			}
		}
		if(!StringUtil.isEmpty(a01temp.getA0111a())){
			if(!a01temp.getA0111a().contains("����") && !a01temp.getA0111a().contains("���") && !a01temp.getA0111a().contains("�Ϻ�") && !a01temp.getA0111a().contains("����") && a01temp.getA0111a().length() <4 ){
				warningInfo.append("�������� ���ĸ�ֱϽ���⣬С���ĸ����ֵ�;");
			}
		}
		if(!StringUtil.isEmpty(a01temp.getA0114a())){
			
			if(!a01temp.getA0114a().contains("����") && !a01temp.getA0114a().contains("���") && !a01temp.getA0114a().contains("�Ϻ�") && !a01temp.getA0114a().contains("����") && a01temp.getA0114a().length() <4 ){
				warningInfo.append("���������Ƴ��ĸ�ֱϽ���⣬С���ĸ�����;");
			}
		}
		
		//3.��ʾ��Ϣ
		if(a0134!=null){
			if(DateUtil.monthsBetween( a0134,new Date()) <16*12 ){
				promptInfo.append("�μӹ���ʱ�� С��16����μӹ�����;");
			}
		}	
		if(a0144!=null){
			if(DateUtil.monthsBetween( a0144,new Date()) <18*12 ){
				promptInfo.append("�뵳ʱ������ С��18��������й���������;");
			}
		}	
		if(!StringUtil.isEmpty(a01temp.getA0184())){
			if(a01temp.getA0184().length()==18 ){
				int idFlag = Integer.parseInt(a01temp.getA0184().substring(16, 17))%2;
				if((idFlag==1 && a01temp.getA0104().equals("2")) || (idFlag==0 && a01temp.getA0104().equals("1")) ){
					promptInfo.append("���֤��Ϊ15λ�����һλ�����֤��Ϊ18�ŵĵ����ڶ�λ����1��Ϊ���С�����2��Ϊ��Ů���������Ա�У��;");
				}
				
			}else if(a01temp.getA0184().length()==15){
				int idFlag = Integer.parseInt(a01temp.getA0184().substring(14, 15))%2;
				if((idFlag==1 && a01temp.getA0104().equals("2")) || (idFlag==0 && a01temp.getA0104().equals("1")) ){
					promptInfo.append("���֤��Ϊ15λ�����һλ�����֤��Ϊ18�ŵĵ����ڶ�λ����1��Ϊ���С�����2��Ϊ��Ů���������Ա�У��;");
				}
				
			}else{
				//TODO ���֤�Ų���18λ  Ҳ����15λ��Ӧ��д�������Ϣ
			}
			
		}	
		if(!StringUtil.isEmpty(a01temp.getA1701()) && a01temp.getA1701().contains("�μӹ���ʱ��") ){
			promptInfo.append("����������������������μӹ���ʱ�䡱;");
		}	
		
		/*if(a0144!=null){
			if(DateUtil.monthsBetween( a0144,new Date()) <18*12 ){
			promptInfo.append("�μ���֯����			С��18��������й���������;");
		}*/	
		
		
		//TODO δ��������Ϣ����ʾ��Ϣ
		a01temp.setErrorinfo(errorInfo.toString());
		if(errorInfo.toString().equals("")){
			a01temp.setIsqualified("0");
		}else{
			a01temp.setIsqualified("1");
		}
		
		return a01temp;
	}
	
	
	/**
	 * У��A02��ְ����Ϣ��������ְ���顿��
	 * 
	 * 1.������Ϣ��
	 *  A0201A	��ְ��������			Ϊ��
	 *	A0215A	ְ������				Ϊ��
	 *	A0219	�Ƿ��쵼ְ��/ְ�����		Ϊ��
	 *	A0221	ְ����				Ϊ��
	 *	A0277	��λ���				Ϊ��
	 *	A0243	��������׼��ְ��ʱ��		�����ڡ��μӹ���ʱ�䡱�롰��ǰʱ�䡱֮���
	 *	A0243	��ְ��ʽ				Ϊ��
	 *	A0265	��������׼��ְ��ʱ��		����ְ״̬��Ϊ�����⡱ʱ������������׼��ְ��ʱ�䡱Ϊ�յ�
	 *	A0288	����ְ����ʱ��			Ϊ�գ�������ְ����ʱ�䡱���ڡ���������׼��ְ��ʱ�䡱��
	 * 2.������Ϣ ����
	 * 3.��ʾ��Ϣ��
	 *	A0201	��ְ��������			��������λ����
	 *	A0201D	�Ƿ���ӳ�Ա			Ϊ��
	 *	A0221	ְ����	  			ְ���θ�����ְ���������
	 *	A0243	��������׼��ְ��ʱ��		�����������������������������׼��ְ��ʱ�䡱
	 *	A0259	�Ƿ��Ƹ����			ְ������Ϊ���𼶽�������
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
		StringBuffer errorInfo = new StringBuffer();	//������Ϣ
		StringBuffer warningInfo = new StringBuffer();	//������Ϣ
		StringBuffer promptInfo = new StringBuffer();	//��ʾ��Ϣ
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a02temp.getA0000());
		try {
			a0107 = sdf.parse(a01.getA0107().length()==8?a01.getA0107():a01.getA0107()+"01");
			a0134 = sdf.parse(a01.getA0134().length()==8?a01.getA0134():a01.getA0134()+"01");
			a0144 = sdf.parse(a01.getA0144().length()==8?a01.getA0144():a01.getA0144()+"01");
		} catch (ParseException e) {
			e.printStackTrace();
			errorInfo.append("�μӹ���ʱ��/�μ���֯���ڶ�ӦΪ��yyyyMMdd����ʽ;");
		}
		
		//1.������Ϣ����
		if(StringUtil.isEmpty(a02temp.getA0201a())){
			errorInfo.append("��ְ��������Ϊ��;");
		}	
		if(StringUtil.isEmpty(a02temp.getA0215a())){
			errorInfo.append("ְ������Ϊ��;");
		}	
		if(StringUtil.isEmpty(a02temp.getA0219())){
			errorInfo.append("�Ƿ��쵼ְ��/ְ�����Ϊ��;");
		}	
		if(StringUtil.isEmpty(a02temp.getA0221())){
			errorInfo.append("ְ����Ϊ��;");
		}	
		if(StringUtil.isEmpty(a02temp.getA0277())){
			errorInfo.append("��λ���Ϊ��;");
		}	
		if(!StringUtil.isEmpty(a02temp.getA0243())){ 
			Date a0243 = null;
			//TODO  ����ʵ���ݣ��ݸ�����������varchar(8)�����ݾ�ȷ���� yyyyMMdd
			try {
				a0243 = DateUtil.toDate(a02temp.getA0243(), "yyyyMMdd");
			} catch (AppException e) {
				errorInfo.append("��������׼��ְ��ʱ�����ݾ�ȷδ���� yyyyMMdd;");
			}
			
			if(a0243!=null && DateUtil.monthsBetween( a0243,new Date()) <0 ){
				errorInfo.append("��������׼��ְ��ʱ�䲻���ڡ��μӹ���ʱ�䡱�롰��ǰʱ�䡱֮���;");
			}
			if(a0243!=null && DateUtil.monthsBetween( a0134,a0243) <0 ){
				errorInfo.append("��������׼��ְ��ʱ�䲻���ڡ��μӹ���ʱ�䡱�롰��ǰʱ�䡱֮���;");
			}
			
			
		}	
		if(StringUtil.isEmpty(a02temp.getA0243())){
			errorInfo.append("��ְ��ʽΪ��;");
		}	
		if(StringUtil.isEmpty(a02temp.getA0265())){
			if(!StringUtil.isEmpty(a02temp.getA0255()) && a02temp.getA0255().equals("0")){
				errorInfo.append("��������׼��ְ��ʱ��	����ְ״̬��Ϊ�����⡱ʱ������������׼��ְ��ʱ�䡱Ϊ�յ�;");
			}
		}	
		if(StringUtil.isEmpty(a02temp.getA0288())){
			errorInfo.append("����ְ����ʱ��Ϊ��;");
		//TODO  ����ʵ���ݣ��ݸ�����������varchar(8)�����ݾ�ȷ���� yyyyMMdd
		}else if(!StringUtil.isEmpty(a02temp.getA0265()) && a02temp.getA0265().length()==a02temp.getA0288().length() && a02temp.getA0288().compareTo(a02temp.getA0265())>0){
			errorInfo.append("������ְ����ʱ�䡱���ڡ���������׼��ְ��ʱ�䡱��;");
		}	

		//2.������Ϣ����:��
		
		//3.��ʾ��Ϣ����
		B01 b01 = null;
		try {
			b01 = (B01)HBUtil.getHBSession().get(B01.class, a02temp.getA0201b());
		} catch (Exception e) {
			promptInfo.append("��ְ��������	�Ҳ�����Ӧ��λ��Ϣ;"); //TODO ������ӵ���֤��Ϣ
		}
		
		if(!StringUtil.isEmpty(a02temp.getA0201b())){
			
			if(b01!=null && b01.getB0101().contains("������λ")){
				promptInfo.append("��ְ��������	��������λ����;");
			}
			
		}
		if(StringUtil.isEmpty(a02temp.getA0201d())){
			promptInfo.append("�Ƿ���ӳ�ԱΪ��;");
		}
		if(!StringUtil.isEmpty(a02temp.getA0221())){
			//TODO ְ���� A0221 -'ZB09'  �� ��ְ��������  B0127- 'ZB03' �����ֲ����ȽϹ���Ƚ����ԱȽ�
			//promptInfo.append("ְ����	ְ���θ�����ְ���������;");
		}
		if(!StringUtil.isEmpty(a02temp.getA0243())){
			if(StringUtil.isEmpty(a01.getA1701()) || (!StringUtil.isEmpty(a01.getA1701()) && !a01.getA1701().contains("��������׼��ְ��ʱ��"))  ){
				promptInfo.append("��������׼��ְ��ʱ�� �����������������������������׼��ְ��ʱ�䡱;");
			}
		}
		if(!StringUtil.isEmpty(a02temp.getA0259())){
			if(a02temp.getA0259().contains("�𼶽���")){
				promptInfo.append("�Ƿ��Ƹ����	ְ������Ϊ���𼶽�������;");
			}
			
		}
		//TODO δ��������Ϣ����ʾ��Ϣ
		a02temp.setErrorinfo(errorInfo.toString());
		if(errorInfo.toString().equals("")){
			a02temp.setIsqualified("0");
		}else{
			a02temp.setIsqualified("1");
		}
		return a02temp;
	}
	
	
	
	
	/**
	 * У��A08ѧ��ѧλ��Ϣ��
	 * 1.����
	 *  A0801A	ѧ������			Ϊ��
	 *  A0804	��ѧ����			ѧ������Ϊ����ר���м���������ʱ������ѧ���ڡ�Ϊ�յģ��粻Ϊ�գ�����ѧ���ڡ�С�ڵ��ڡ��������¡���
	 *  A0807	��ҵ����			�粻Ϊ�գ�����ҵ���ڡ�С�ڵ��ڡ���ѧ���ڡ���
	 *  A0904	ѧλ��������		��ѧλ���ơ���Ϊ��ʱ����ѧλ�������ڡ�Ϊ�յģ���ѧλ�������ڡ���Ϊ��ʱ�����ڡ���ѧ���ڡ���
	 *  A0814	ѧУ����λ������	ѧ������Ϊ����ר���м���������ʱ����ѧУ����λ�����ơ�Ϊ�յ�
	 * 2.���棺
	 *  A0807	��ҵ����	    	ѧ������Ϊ����ר���м���������ʱ������ҵ���ڡ�Ϊ�յ�
	 *  A0824	��ѧרҵ����		ѧ������Ϊ����ר���м���������ʱ������ѧרҵ���ơ�Ϊ�յ�
	 * 3.��ʾ�� ��
	 * 
	 * @author mengl
	 * @param a08temp
	 * @return
	 */
	public static A08temp verifyA08(A08temp a08temp) {
		StringBuffer errorInfo = new StringBuffer();
		StringBuffer warningInfo = new StringBuffer();
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a08temp.getA0000());
		//1.������Ϣ����
		if(StringUtils.isEmpty(a08temp.getA0801a())){
			errorInfo.append("ѧ������Ϊ��;");
		}
		
		if(StringUtils.isEmpty(a08temp.getA0804())){
			//ѧ������ĸС�ڵ���4 
			if(!StringUtils.isEmpty(a08temp.getA0801b()) && a08temp.getA0801b().substring(0, 1).compareTo("4")<=0){
				errorInfo.append("ѧ������Ϊ����ר���м���������ʱ������ѧ���ڡ�Ϊ�յ�;");
			}
		}else{
			if(a01.getA0107().compareTo(a08temp.getA0804())<0){
				errorInfo.append("����ѧ���ڡ�С�ڵ��ڡ��������¡�;");
			}
		}
		
		if(!StringUtils.isEmpty(a08temp.getA0807())){
			if(a08temp.getA0804().compareTo(a08temp.getA0807())>=0){
				errorInfo.append("����ҵ���ڡ�С�ڵ��ڡ���ѧ���ڡ�;");
			}
		}
		
		if(StringUtils.isEmpty(a08temp.getA0904())){
			if(!StringUtils.isEmpty(a08temp.getA0901a())){
				errorInfo.append("��ѧλ���ơ���Ϊ��ʱ����ѧλ�������ڡ�Ϊ�յ�;");
			}
		}else{
			if(!StringUtils.isEmpty(a08temp.getA0804()) && a08temp.getA0804().compareTo(a08temp.getA0904())<0){
				errorInfo.append("��ѧλ�������ڡ���Ϊ��ʱ�����ڡ���ѧ���ڡ�;");
			}
		}
		
		if(StringUtil.isEmpty(a08temp.getA0814())){
			//ѧ������ĸС�ڵ���4 
			if(!StringUtils.isEmpty(a08temp.getA0801b()) && a08temp.getA0801b().substring(0, 1).compareTo("4")<=0){
				errorInfo.append("ѧ������Ϊ����ר���м���������ʱ����ѧУ����λ�����ơ�Ϊ�յ�;");
			}
		}
		
		
		//2.������Ϣ����
		if(StringUtils.isEmpty(a08temp.getA0807())){
			if(!StringUtils.isEmpty(a08temp.getA0801b()) && a08temp.getA0801b().substring(0, 1).compareTo("4")<=0){
				warningInfo.append("ѧ������Ϊ����ר���м���������ʱ������ҵ���ڡ�Ϊ�յ�;");
			}
		}
		
		if(StringUtils.isEmpty(a08temp.getA0824())){
			if(!StringUtils.isEmpty(a08temp.getA0801b()) && a08temp.getA0801b().substring(0, 1).compareTo("4")<=0){
				warningInfo.append("ѧ������Ϊ����ר���м���������ʱ������ѧרҵ���ơ�Ϊ�յ�;");
			}
		}
		
		//TODO δ��������Ϣ����ʾ��Ϣ
		a08temp.setErrorinfo(errorInfo.toString());
		if(errorInfo.toString().equals("")){
			a08temp.setIsqualified("0");
		}else{
			a08temp.setIsqualified("1");
		}
		return a08temp;
	}
	
	/**
	 * У��A29(���������Ϣ)
	 * 
	 * 1.������Ϣ
	 * A2947	���빫��Ա����ʱ��	��Ա���Ϊ������Ա����������Ⱥ����Ա���򡰲�����ҵ��Ա��ʱ�������빫��Ա����ʱ�䡱Ϊ�յģ������빫��Ա����ʱ�䡱С�ڡ��μӹ���ʱ�䡱��
	 * A2949	����Ա�Ǽ�ʱ��		��Ϊ��ʱ��������Ա�Ǽ�ʱ�䡱С�ڡ����빫��Ա����ʱ�䡱�ģ���Ϊ��ʱ��������Ա�Ǽ�ʱ�䡱����20060701��
	 * 2.������Ϣ
	 * A2949	����Ա�Ǽ�ʱ��		Ϊ�գ���������Ա����ת�ɲ����⣩
	 * 3.��ʾ��Ϣ����
	 * 
	 * @author mengl
	 * @param a29temp
	 * @return
	 */
	public static A29temp verifyA29(A29temp a29temp) {
		StringBuffer errorInfo = new StringBuffer();	//������Ϣ
		StringBuffer warningInfo = new StringBuffer();	//������Ϣ
		StringBuffer promptInfo = new StringBuffer();	//��ʾ��Ϣ
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a29temp.getA0000());
		Date a0134=null;
		try {
			a0134 = sdf.parse(a01.getA0134().length()==8?a01.getA0134():a01.getA0134()+"01");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		//1.������Ϣ����
		if(StringUtil.isEmpty(a29temp.getA2947())){
			if(!StringUtil.isEmpty(a01.getA0158()) && a01.getA0158().substring(0, 1).equals("1") ){//A0158 - ZB09
				errorInfo.append("���빫��Ա����ʱ�� ��Ա���Ϊ������Ա����������Ⱥ����Ա���򡰲�����ҵ��Ա��ʱ�������빫��Ա����ʱ�䡱Ϊ�յ�;");
			}
		}else if(a0134!=null){
			//TODO  ������ӣ�����ʵ���ݣ��ݸ�����������varchar(8)�����ݾ�ȷ���� yyyyMMdd
			
			if(a29temp.getA2947().compareTo(a01.getA0134())<0){
				errorInfo.append("�����빫��Ա����ʱ�䡱С�ڡ��μӹ���ʱ�䡱��;");
			}
		}
		if(!StringUtil.isEmpty(a29temp.getA2949())){
			Date a2949 = null;
			//TODO  ������ӣ�����ʵ���ݣ��ݸ�����������varchar(8)�����ݾ�ȷ���� yyyyMMdd
			try{
				a2949 = DateUtil.toDate(a29temp.getA2949(), "yyyyMMdd");
			}catch(Exception e){
				errorInfo.append("�����빫��Ա����ʱ�䡱��ʽӦΪyyyyMMdd;");
			}
			
			if(a2949!=null && a29temp.getA2949().compareTo("20060701")<0){
				errorInfo.append("������Ա�Ǽ�ʱ�䡱����20060701��;");
			}
			
			if(StringUtil.isEmpty(a29temp.getA2941()) && a29temp.getA2941().length()==8  && a2949!=null && a29temp.getA2949().compareTo(a29temp.getA2941())<0 ){
				errorInfo.append("������Ա�Ǽ�ʱ�䡱С�ڡ����빫��Ա����ʱ�䡱��;");
			}
			
		}
		//2.������Ϣ����
		if(StringUtil.isEmpty(a29temp.getA2949())){
			if(a01!=null && a01.getA0158()!=null && !a01.getA0158().equals("27") && !a01.getA0158().equals("198")){//TODO �޷��ж���ת���
				warningInfo.append("������Ա�Ǽ�ʱ��Ϊ�գ���������Ա����ת�ɲ����⣩;");
			}
		}	
		//3.��ʾ��Ϣ������
		
		//TODO δ��������Ϣ����ʾ��Ϣ
		a29temp.setErrorinfo(errorInfo.toString());
		if(errorInfo.toString().equals("")){
			a29temp.setIsqualified("0");
		}else{
			a29temp.setIsqualified("1");
		}
		return a29temp;
	}
	
	/**
	 * У��A36(��ͥ��Ա������ϵ��Ϣ)
	 * 
	 * 1.������Ϣ
	 *  A3601	��Ա����			Ϊ��
	 *  A3604A	��Ա����˹�ϵ/��ν	Ϊ��
	 *  A3611	��Ա������λ��ְ��	Ϊ��
	 * 2.������Ϣ
	 *  A3607	��Ա��������		Ϊ�գ���ȥ����Ա���⣩
	 *  A3627	��Ա������ò		Ϊ�գ�18��������Ա���⣩��Ϊ���й���Ա�����������ɡ��ģ�������С��18�����
	 * 3.��ʾ��Ϣ����
	 * 
	 * @author mengl
	 * @param a36temp
	 * @return
	 */
	public static A36temp verifyA36(A36temp a36temp) {
		StringBuffer errorInfo = new StringBuffer();	//������Ϣ
		StringBuffer warningInfo = new StringBuffer();	//������Ϣ
		StringBuffer promptInfo = new StringBuffer();	//��ʾ��Ϣ
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a36temp.getA0000());
		//1.������Ϣ����
		if(StringUtil.isEmpty(a36temp.getA3601())){
			errorInfo.append("��Ա����Ϊ��;");
		}	
		if(StringUtil.isEmpty(a36temp.getA3604a())){
			errorInfo.append("��Ա����˹�ϵ/��ν Ϊ��;");
		}	
		if(StringUtil.isEmpty(a36temp.getA3611())){
			errorInfo.append("��Ա������λ��ְ�� Ϊ��;");
		}	
		
		Date a3607 = null;//TODO ���������֤�������ڸ�ʽ
		if(!StringUtil.isEmpty(a36temp.getA3607())  ){
			try {
				a3607 = DateUtil.toDate(a36temp.getA3607(), "yyyyMMdd");
			} catch (AppException e) {
				errorInfo.append("��Ա�������ڸ�ʽ��ΪyyyyMMdd;");
			}
		}
		
		
		//2.������Ϣ����
		if(StringUtil.isEmpty(a36temp.getA3607())){
			//TODO �ݲ�֪�ĸ��ֶ��ж��Ƿ�ȥ��
			if(StringUtil.isEmpty(a01.getA0128()) && ( a01.getA0128().contains("ȥ��") ||  a01.getA0128().contains("��") ) ){
			}else{
				warningInfo.append("��Ա��������Ϊ�գ���ȥ����Ա���⣩;");
			}
		}	
		if(StringUtil.isEmpty(a36temp.getA3627())){
			if(a3607!=null && DateUtil.monthsBetween(a3607, new Date()) > 18*12 ){
				errorInfo.append("��Ա������ò	Ϊ�գ�18��������Ա���⣩;");
			}
		//TODO  ������ò�Ƿ���  GB4762������ֱ���Ǻ��� 
		}else if(!StringUtil.isEmpty(a36temp.getA3627()) && !a36temp.getA3627().equals("12") && !a36temp.getA3627().equals("13") && a3607!=null && DateUtil.monthsBetween(a3607, new Date()) < 18*12){
			errorInfo.append("��Ա������ò Ϊ���й���Ա�����������ɡ��ģ�������С��18�����;");
		}	
		//3.��ʾ��Ϣ������
		
		//TODO δ��������Ϣ����ʾ��Ϣ
		a36temp.setErrorinfo(errorInfo.toString());
		if(errorInfo.toString().equals("")){
			a36temp.setIsqualified("0");
		}else{
			a36temp.setIsqualified("1");
		}
		return a36temp;
	}
	
	/**
	 * У��A57(��ý����Ϣ)
	 * 1.������Ϣ
	 * A5714	��Ƭ		Ϊ��
	 * 2.������Ϣ����
	 * 3.��ʾ��Ϣ����
	 * 
	 * @author mengl
	 * @param a57temp
	 * @return
	 */
	public static A57temp verifyA57(A57temp a57temp) {
		StringBuffer errorInfo = new StringBuffer();	//������Ϣ
		StringBuffer warningInfo = new StringBuffer();	//������Ϣ
		StringBuffer promptInfo = new StringBuffer();	//��ʾ��Ϣ
		//1.������Ϣ����
		if(StringUtil.isEmpty(a57temp.getA5714())){
			errorInfo.append("��ƬΪ��;");
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
	 * У�� B01(��λ������Ϣ)
	 * 1.������Ϣ
	 *  B0101	��λ����					Ϊ��
	 *  B0104	��λ���					Ϊ��
	 *  B0127	��λ����					Ϊ��
	 *  B0194	���˵�λ��ʶ				��Ϊ�����˵�λ�������˵�λ����Ϊ�գ��������Ӣ����ĸ���Ǵ�д��
	 *  
	 *  ���������ֶβ���ͬʱΪ��
	 *  B0227	����������    
	 *  B0232	���չ���Ա��������ҵ��λ������
	 *  B0233	������ҵ������
	 *  
	 * 2.������Ϣ
	 *  B0183	��ְ�쵼ְ��				Ϊ�ջ����2��
	 *  B0185	��ְ�쵼ְ��				Ϊ��
	 * 
	 * 3.��ʾ��Ϣ����
	 * @author mengl
	 * @param B01temp
	 * @return
	 */
	public static B01temp verifyB01(B01temp b01temp) {
		StringBuffer errorInfo = new StringBuffer();	//������Ϣ
		StringBuffer warningInfo = new StringBuffer();	//������Ϣ
		StringBuffer promptInfo = new StringBuffer();	//��ʾ��Ϣ
		//1.������Ϣ����
		if(StringUtil.isEmpty(b01temp.getB0101())){
			errorInfo.append("��λ����Ϊ��;");
		}	
		if(StringUtil.isEmpty(b01temp.getB0104())){
			errorInfo.append("��λ���Ϊ��;");
		}	
		if(StringUtil.isEmpty(b01temp.getB0127())){
			errorInfo.append("��λ����Ϊ��;");
		}	
		if(!StringUtil.isEmpty(b01temp.getB0194()) && b01temp.getB0194().equals("1") ){
			if(StringUtil.isEmpty(b01temp.getB0111()) ||  b01temp.getB0111().matches("\\w*[a-z]+\\w*")){//TODO ������ʽ��ȷ��
				errorInfo.append("���˵�λ��ʶ ��Ϊ�����˵�λ�������˵�λ����Ϊ�գ��������Ӣ����ĸ���Ǵ�д��;");
			}
		}
		if(b01temp.getB0227()==null && b01temp.getB0232()==null && b01temp.getB0233()==null ){
			errorInfo.append("���������������չ���Ա��������ҵ��λ��������������ҵ������ ���߲���ͬʱΪ��;");
		}
		//2.������Ϣ����
		
		if(b01temp.getB0183()==null ||b01temp.getB0183() >2 ){
			warningInfo.append("��ְ�쵼ְ��Ϊ�ջ����2��;");
		}	
		if(b01temp.getB0185()==null){
			warningInfo.append("��ְ�쵼ְ��Ϊ��;");
		}	
		//3.��ʾ��Ϣ����
		if(b01temp.getB0188()==null){
			promptInfo.append("ͬ����ְ���쵼ְ��Ϊ��;");
		}	
		if(b01temp.getB0189()==null){
			promptInfo.append("ͬ����ְ���쵼ְ��Ϊ��;");
		}	
		if(b01temp.getB0190()==null){
			promptInfo.append("���������ְ�쵼ְ��Ϊ��;");
		}	
		if(b01temp.getB0191a()==null){
			promptInfo.append("���������ְ�쵼ְ��Ϊ��;");
		}	
		if(b01temp.getB0192()==null){
			promptInfo.append("�������ͬ����ְ���쵼ְ��Ϊ��;");
		}	
		if(b01temp.getB0193()==null){
			promptInfo.append("�������ͬ����ְ���쵼ְ��Ϊ��;");
		}	

		//TODO δ��������Ϣ����ʾ��Ϣ
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
