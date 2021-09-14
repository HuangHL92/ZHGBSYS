package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.Param;

public class CodeTypeUtil {
	private static Map<String, String> codetypeMaping = new HashMap<String, String>();
	
	static{
		put("A0104", "GB2261");  //�Ա� 
		put("A0117", "GB3304");  //����
		put("A0160", "ZB125");   //��Ա���
		put("A0163", "ZB126");	//��Ա����״̬
		put("A0165", "ZB130");	//�������
		put("A0192D", "ZB133");	//ְ��
		put("A0120", "ZB134");	//����
		put("A0121", "ZB135");	//��������
		put("A0122", "ZB139");	//רҵ�����๫��Ա��ְ�ʸ�
		put("A0201E", "ZB129");	//���ӳ�Ա���
		put("A0215A", "ZB08");	//ְ������
		put("A0219", "ZB42");	//�Ƿ��쵼ְ��
		put("A0221", "ZB09");	//ְ����
		put("A0222", "ZB127");	//��λ���
		put("A0247", "ZB122");	//��ְ��ʽ
		put("A0251", "ZB13");	//��ְ�䶯���
		put("A0255", "ZB14");	//��ְ״̬
		put("A0271", "ZB16");	//��ְԭ�����
		put("A4901", "ZB72");	//������ʽ
		put("A4904", "ZB73");	//����ԭ��
		put("A4907", "ZB74");	//����ȥ��
		put("A0221A", "ZB136");	//ְ��ȼ�
		put("A0601", "GB8561");	//רҵ������ְ�ʸ�
		put("A0607", "ZB24");	//ȡ���ʸ�;��
		put("A0801B", "ZB64");	//ѧ������
		put("A0901B", "GB6864");	//ѧλ����
		put("A0827", "GB16835");	//��ѧרҵ���
		put("A0837", "ZB123");	//�������
		put("A1101", "ZB29");	//��ѵ���
		put("A1104", "ZB30");	//��ѵ���״̬
		put("A1127", "ZB27");	//��ѵ�������
		put("A1404B", "ZB65");	//�������ƴ���
		put("A1414", "ZB03");	//��׼���ͻ��ؼ���
		put("A1415", "ZB09");	//����ʱְ����
		put("A1428", "ZB128");	//��׼��������
		put("A1517", "ZB18");	//���˽������
		put("A2911", "ZB77");	//���뱾��λ�䶯���
//		put("A2921B", "ZB74");	//���뱾��λǰ������λ���ڵ�
//		put("A2921C", "ZB140");	//���빤����λǰ������λ����
//		put("A2921D", "ZB141");	//���빤����λǰ������λ���
//		put("A2970", "ZB137");	//ѡ����
//		put("A2970A", "ZB138");	//ѡ������Դ
		put("A3001", "ZB78");	//�˳�����λ�䶯���
		put("A3101", "ZB132");	//�������
		put("A3107", "ZB09");	//����ǰְ����
		put("A3108", "ZB133");	//����ǰְ��
		put("A3109", "ZB136");	//����ǰְ��ȼ�
		put("A3110", "ZB134");	//����ǰ����
		put("A0141", "GB4762");	//������ò
		put("A3921", "GB4762");	//�ڶ�����
		put("A3927", "GB4762");	//��������
		//put("B0111", "ZB02");	//��λ����
		put("B0194", "B0194");	//��λ�������
		put("B0117", "ZB01");	//��λ��������
		put("B0124", "ZB87");	//��λ������ϵ
		put("B0127", "ZB03");	//��λ����
		put("B0131", "ZB04");	//��λ�������
		put("A0281", "BOOLEAN");//�����ʶ
/*		put("A3140","ZB09");	//����ǰְ����
		put("A3142","ZB133");	//����ǰְ��
		put("A3141","ZB136");	//����ǰְ��ȼ�
*/		put("A0111A","ZB01");
		put("A0114A", "ZB01");
		
		/*ҵ����ϢȺ��Ϣ���*/
		put("A6005", "ZB64");
		put("A6007", "GB6864");
		put("A6009", "ZB146");
		put("A6010", "ZB147");
		put("A6005", "ZB64");
		put("A2970", "ZB137");	//ѡ����
		put("A2970A", "ZB138");	//ѡ������Դ
		put("A6109", "ZB64");
		put("A6111", "GB6864");
		put("A6202", "ZB142");
		put("A6203", "ZB143");
		put("A6204", "ZB141");
		put("A6302", "ZB142");
		put("A6303", "ZB144");
		put("A6304", "ZB141");
		put("A6305", "ZB145");
	}
	
	public static String get(String key){
		return codetypeMaping.get(key);
	}
	
	private static void put(String key,String value){
		codetypeMaping.put(key , value);
	}
	
	
	/**
	 * ���ݲ�����ȡCodeValueֵ  �����Ĵ��뵥���ж�
	 * @param param
	 * @return ����null ���ڷ��ش�����Ϣ
	 */
	public static Param getCodeValue(Param param){
		/*if(1==1){//��������ʱ��ͨ��
			return param;
		}*/
		if("".equals(param.getValue())){
			return param;
		}
		String codetype = CodeTypeUtil.get(param.getName().toUpperCase());
		if(codetype==null){
			return param;
		}
		List<String> list = new ArrayList<String>();
		if(param.getName().equals("A0111A") || param.getName().equals("A0114A")){
			 list = HBUtil.getHBSession().createSQLQuery(
					"select code_value from Code_Value where code_type='"+codetype+"'" +
							" and code_status='1' and code_name3='"+param.getValue()+"'").list();
		}else{
			 list = HBUtil.getHBSession().createSQLQuery(
					"select code_value from Code_Value where code_type='"+codetype+"'" +
							" and code_status='1' and code_name='"+param.getValue()+"'").list();
		}
		
		if (list==null||list.size()==0) {
			return null;
		}
		param.setValue(list.get(0).toString());
		return param;
	}
	
	
	/**
	 * 
	 * @param b
	 * @return
	 * @throws Exception 
	 */
	public static byte[]  getPhotoBase64(String b) throws Exception{
		if(b==null||b.length()==0){
			throw new Exception("ͼƬ���ݲ���Ϊ�գ�");
		}
		//���ֽ�����Base64����  
		BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(b);//����Base64��������ֽ������ַ���
	}
	
	
}
