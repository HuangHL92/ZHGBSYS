package com.insigma.siis.local.business.helperUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
  
/**
 * ���֤����
 * @author
 * ��֤���֤,��ȡ�Ա����������
 */

public class IdCardManageUtil {
	final static int[] Weight = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};			//��Ȩ����
	final static String[] Verifycode = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};	//У����
	
	/**
	 * �ж����֤������Ч��
	 * @param idCard ���֤����
	 * @return	У���� 0��ʾ��Ч,1��ʾ��Ч
	 */
	public ErrorMsgDTO checkIdCard(String idCard){
		String newIdCard = idCard;
		ErrorMsgDTO errdto = new ErrorMsgDTO();
		if(idCard.length() != 15&&idCard.length() != 18){
			errdto.setReturn_ret(0);
			errdto.setReturn_msg("���֤λ��������15λ����18λ��");
		}else{
			if(idCard.length() == 15){	//�����15λ,ת��Ϊ18λ
				Long m=Long.parseLong(idCard.substring(8, 10));			
				Long d=Long.parseLong(idCard.substring(10, 12));
				if(m>12||d>31){
					errdto.setReturn_ret(0);
					errdto.setReturn_msg("���֤��Ч��");
				}else{
					newIdCard = proIdCard15to18(idCard);
					if(newIdCard.length() != 18){ 				//λ������,����0
						errdto.setReturn_ret(0);
						errdto.setReturn_msg("���֤��Ч��");
					}else{
						String verify = newIdCard.substring(17, 18); 
						if(verify.equals(getVerify(newIdCard))){ 	//�����Ч��,�����ؼ����
							errdto.setReturn_ret(1);				//��Ч,����1
						}
						else{
							errdto.setReturn_ret(0);
							errdto.setReturn_msg("���֤��Ч��");						//��Ч,����0
						}
					}
				}
			}else if(idCard.length() == 18){
				    String verify = newIdCard.substring(17, 18); 
					if(verify.equals(getVerify(newIdCard))){ 	//�����Ч��,�����ؼ����
						errdto.setReturn_ret(1);	//��Ч,����1
					}
					else{
						errdto.setReturn_ret(0);
						errdto.setReturn_msg("���֤��Ч��");						//��Ч,����0
					}	
			}
		}
		return errdto;
	}
    /**
     * ��ȡ��ȷ�����֤
     * @param idCard
     * @return
     */
	public String getAae135(String idCard){
		String newIdCard = null;
		if(idCard.length() == 15){
			newIdCard = proIdCard15to18(idCard);
		}
		if(idCard.length() == 18){
			newIdCard = idCard;
		}
		return newIdCard;
	}
	/**
	 * �����֤�����л�ȡ�Ա�
	 * @param idCard ���֤����
	 * @return	�Ա�,'2'ΪŮ��,'1'Ϊ����
	 */
	public static String getSexFromIdCard(String idCard){
		if(idCard.length() == 15){					//�����15λ,ת��Ϊ18λ
			idCard = proIdCard15to18(idCard);
		}
		int a = Integer.parseInt(idCard.substring(16, 17));		//ȡ������2λ
		if(a % 2 == 0){								//ż��ΪŮ��,����Ϊ����
			return "2";
		}
		else{
			return "1";
		}
	}
	
	/**
	 * �����֤�����л�ȡ��������
	 * @param idCard ���֤����
	 * @return	��������
	 */
	public static String getBirthdayFromIdCard(String idCard){
		if(idCard.length() == 15){					//�����15λ,ת��Ϊ18λ
			idCard = proIdCard15to18(idCard);
		}
		String birthday = idCard.substring(6, 14);	//��ȡ��ʾ�������ڵĵ�7-14λ
		return birthday.substring(0,4) + birthday.substring(4,6) + birthday.substring(6,8);			
	}
	
	/**
	 * 15λ���֤����ת��Ϊ18λ
	 * @param idCard 15λ���֤����
	 * @return newidCard ������18λ���֤����
	 */
	public static String proIdCard15to18(String idCard){
		int i, j, s = 0;
		String newidCard;
		newidCard = idCard;
		newidCard = newidCard.substring(0, 6) + "19" + newidCard.substring(6, idCard.length());
		for( i = 0; i<newidCard.length() ;i++ ){
			j = Integer.parseInt(newidCard.substring(i, i+1)) * Weight[i];
			s = s + j;
		}
		s = s % 11;
		newidCard = newidCard + Verifycode[s];
		return newidCard;
	}
	
	/**
	 * ��ȡ���֤У����
	 * @param idCard ���֤����
	 * @return ���֤�����У����
	 */
	private String getVerify(String idCard){
		int[] ai = new int[18];
		int remaining = 0; 
		if(idCard.length() == 18){ 
			idCard = idCard.substring(0, 17); 
		} 
		if(idCard.length() == 17){ 
			int sum = 0; 
			for(int i = 0; i < 17; i++){ 
				String k = idCard.substring(i, i+1); 
				ai[i] = Integer.parseInt(k); 
			} 
			for(int i = 0; i < 17; i++){ 
				sum = sum + Weight[i] * ai[i]; 
			} 
			remaining = sum % 11; 
		} 
		return remaining == 2 ? "X" : String.valueOf(Verifycode[remaining]); 
	} 
	
	/**
	 * �������֤��ţ��������֤�Ƿ�Ϸ������Ϊtrue��Ϸ������Ϊfalse����֤δͨ��
	 * @param idCard
	 * @return
	 * {
	 * @code
	 * String aac002 = this.getPageElement("aac002").getValue();//��ȡҳ����������֤���
	 * boolean flag = IdCardManageUtil.trueOrFalseIdCard(aac002);//�ж����֤����Ƿ��ܹ���֤ͨ��
	 * 	if(flag){
	 * 		...
	 * 	}else{
	 * 		throw new RadowException("���֤���Ϸ�!");
	 * 	}
	 * }
	 */
	public static boolean trueOrFalseIdCard(String idCard){
		//���Ϊ�� ��֤��ͨ��
		if(idCard==null || idCard.equals("")){
			return false;
		}
		idCard = idCard.toUpperCase();
		//�����Ϊ�� ����֤ͨ���򷵻�true ���򷵻�false
		ErrorMsgDTO dto = new ErrorMsgDTO(); 
		try{
			dto = new IdCardManageUtil().checkIdCard(idCard); 
		}catch(Exception e){
			//e.printStackTrace();
			return false;
		}
		if(dto.getReturn_ret() == 1){
			return true;
		}else{
			return false;
		}
	} 

	/**
	 * ��ȡ����
	 * @param aac002
	 * @return
	 */
	public static int  getAge(String aac002){
		if(aac002.length() == 15){					//�����15λ,ת��Ϊ18λ
			aac002 = proIdCard15to18(aac002);
		}
		String birth = aac002.substring(6,14);
		 //dayOfBirth������һ�յ�ʱ��
		java.util.Date dayOfBirth=null;
		try {
			dayOfBirth = new SimpleDateFormat("yyyyMMdd").parse(birth);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		 java.util.Calendar cal = new GregorianCalendar();//��ǰʱ��
		int year = cal.get(Calendar.YEAR);//year�������
		cal.setTime(dayOfBirth);//��ǰcalΪdayOfBirth������һ�յ�Calendarʱ��
		int birthYear = cal.get(Calendar.YEAR);//�������
		int age = year - birthYear;
		cal.set(Calendar.YEAR, year);//�����������
		Date birthday = cal.getTime();//birthday ÿ�������
		Date today = new Date();//today ����
		 if(today.compareTo(birthday)<0){//��ǰʱ��С�ڽ��������
			 //����û�й�����, ��һ��
			 age--;
		 }
		 	return age;
		 }
	
	/**
	 * ��ȡ����
	 * @param aac002
	 * @return
	 */
	public static int getAgefrombirth(String birth) {
		if(birth == null||(birth.length()!=6&&birth.length()!=8)){
			return -1;
		}
		if(birth.length()==6){
			birth = birth+"01";
		}
		java.util.Date dayOfBirth = null;
		try {
			dayOfBirth = new SimpleDateFormat("yyyyMMdd").parse(birth);
		} catch (Exception e) {
			return -1;
		}

		java.util.Calendar cal = new GregorianCalendar();// ��ǰʱ��
		int year = cal.get(Calendar.YEAR);// year�������
		cal.setTime(dayOfBirth);// ��ǰcalΪdayOfBirth������һ�յ�Calendarʱ��
		int birthYear = cal.get(Calendar.YEAR);// �������
		int age = year - birthYear;
		cal.set(Calendar.YEAR, year);// �����������
		Date birthday = cal.getTime();// birthday ÿ�������
		Date today = new Date();// today ����
		if (today.compareTo(birthday) < 0) {// ��ǰʱ��С�ڽ��������
			// ����û�й�����, ��һ��
			age--;
		}
		return age;
	}
	
	
}
	
	
	
