package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity.CodeTypeUtil;

public class SaveA01 {
	private static String idCard = "A0184"; //���֤�� 
	private static String required = "A0184,A0101"; //����� ���֤�ţ�����
	private static Map<String, String> defaultField = new HashMap<String, String>();//Ĭ����
	{
		defaultField.put("A0104", "1");//�Ա�
		defaultField.put("A0128", "����");//����״̬
		defaultField.put("A0163", "1");//Ĭ����ְ��Ա
		defaultField.put("A14Z101", "��");//��������
		defaultField.put("STATUS", "1");//ɾ��״̬
	}
	private static Map<String, String> generatorField = new HashMap<String, String>();//������
	{
		//generatorField.put("A0102", "");//������ƴ
		//generatorField.put("AGE","");
	}
	public static String save(List<Param> params,Connection conn) {
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		String a0000 = null;
		//2����ƴ���
		ChineseSpelling chineseSpelling = new ChineseSpelling();
		sql.append("insert into a01 (");
		//�������
		for(Param param : params) {
			Param p2 = CodeTypeUtil.getCodeValue(param);
			if(p2==null){
				return "����"+param.getName()+" "+param.getDesc()+" �����ڶ�Ӧ�Ĵ���ֵ��"+param.getValue()+"��";
			}
			//��Ա��Ϣ����У��
			if(idCard.equals(param.getName())){
				/*if(IdCardManageUtil.trueOrFalseIdCard(param.getValue())){
					generatorField.put("AGE", IdCardManageUtil.getAge(param.getValue())+"");
				}else{
					return "�������֤�Ŵ���:"+param.getValue();
				}*/
				List<A01> list = HBUtil.getHBSession().createQuery(
						"from A01 where a0184='"+param.getValue()+"'  ").list();
				if(list!=null&&list.size()==1){
					a0000 = list.get(0).getA0000();
					PreparedStatement pst = null;
					try {
						conn.setAutoCommit(false);
						pst = conn.prepareStatement("delete from a01 where a0000=?");
						pst.setString(1, a0000);
						pst.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}finally{
						try {
							if(pst!=null)pst.close();
						} catch (SQLException e1) {
						}
					}	
				}else if(list!=null&&list.size()>0){
					return "����ϵͳ���Ѵ��ڸ���Ա:"+param.getValue();
				}
			}
			
			/*if("A0101".equals(param.getName())){//������ƴ
				generatorField.put("A0102", chineseSpelling.getPYString(param.getValue()));
			}*/
			
			//�ǿ�У��
			if(required.indexOf(param.getName())!=-1 && "".equals(param.getValue())){
				return "����"+param.getName()+" "+param.getDesc()+"����Ϊ��";
			}
			//Ĭ����
			if(defaultField.get(param.getName())!=null){//Ĭ���� Ϊ���������
				if(!"".equals(param.getValue())){
					defaultField.put(param.getName(), param.getValue());
				}
			}else{//��Ĭ����
				column.append(param.getName()+",");
				value.append("'"+param.getValue()+"',");
			}
			
			
		}
		//Ĭ����
		for(String key : defaultField.keySet()){
			column.append(key+",");
			value.append("'"+defaultField.get(key)+"',");
		}
		/*//������
		for(String key : generatorField.keySet()){
			column.append(key+",");
			if(key.equals("AGE")){
				value.append(" "+generatorField.get(key)+" ,");
			}else{
				value.append("'"+generatorField.get(key)+"',");
			}
		}*/
		//����
		column.append("a0000,");
		value.append("'"+(a0000==null?UUID.randomUUID():a0000)+"',");
		
		
		column.deleteCharAt(column.length()-1);
		value.deleteCharAt(value.length()-1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		init();
		return sql.toString();
	}
	private static void init(){
		defaultField.put("A0104", "1");//�Ա�
		defaultField.put("A0128", "����");//����״̬
		defaultField.put("A0163", "1");//Ĭ����ְ��Ա
		defaultField.put("A14Z101", "��");//��������
		defaultField.put("STATUS", "1");//ɾ��״̬
		/*generatorField.put("A0102", "");//������ƴ
		generatorField.put("AGE","");*/
	}
}
