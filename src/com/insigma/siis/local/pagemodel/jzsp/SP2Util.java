package com.insigma.siis.local.pagemodel.jzsp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.Sp01;
import com.insigma.siis.local.business.entity.Sp01_Pc;
import com.insigma.siis.local.business.entity.Sp_Bus;
import com.insigma.siis.local.business.entity.Sp_Bus_Log;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class SP2Util {
	/**
	 * 0���� : 1��ǰ������� ��2����״̬ : 3���� ��4���������Ƿ��Ϊ��  : 5��ע�Ƿ��Ϊ�� : 
	 */
	public static String[][] nextJGLDconfig = new String[4][];
	/**
	 * 0���� : 1��ǰ������� ��2����״̬ : 3���� ��4���������Ƿ��Ϊ��  : 5��ע�Ƿ��Ϊ�� : 
	 */
	public static String[][] prevJGLDconfig = new String[4][];
	static{
		//0���� : 1��ǰ������� ��2����״̬ : 3���� ��4���������Ƿ��Ϊ��  : 5��ע�Ƿ��Ϊ�� : 6ҵ������Ƿ��Ϊ�� ��7ҵ������ֶ�
		nextJGLDconfig[1] = new String[]{"2","2","1","�ϱ�","0","1","1",""};//�Ǽǽ���
		nextJGLDconfig[2] = new String[]{"3","6","1","����","0","1","0","spp04"};//һ����������
		nextJGLDconfig[3] = new String[]{"4","3","2","����ͨ��(���)","1","1","0","spp05"};//������������
		
		//0���� : 1��ǰ������� ��2����״̬ : 3���� ��4���������Ƿ��Ϊ��  : 5��ע�Ƿ��Ϊ�� : 6ҵ������Ƿ��Ϊ��
		prevJGLDconfig[2] = new String[]{"1","4","0","�˻�","1","0","1","spp04"};//һ����������
		prevJGLDconfig[3] = new String[]{"4","4","3","����δͨ��(���)","1","1","0","spp05"};//������������
	}
	/**
	 * ͨ��01
	 * @param sp0100
	 * @param sess
	 * @param map 
	 * @throws NumberFormatException
	 * @throws AppException
	 */
	public static void apply02(String sp0100, HBSession sess, Map<String, String> map,String type) throws NumberFormatException, AppException {
		String[][] JGLDconfig;
		if("1".equals(type)){//ͨ��
			JGLDconfig = nextJGLDconfig;
		}else{//��ͨ��
			JGLDconfig = prevJGLDconfig;
		}
		Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, sp0100);
		int hj = Integer.valueOf(sb.getSpb06());
		if(hj>=JGLDconfig.length){
			throw new AppException("��������ᣡ");
		}
		if(1!=hj){
			String curUser = SysManagerUtils.getUserId();
			String curGroup = SysManagerUtils.getUserGroupid();
			if(map.get("user")!=null){//�����û�Ϊ��ǰ�û�
				if(!curUser.equals(map.get("user"))){
					throw new AppException("��ǰ�û�������Ȩ�ޣ�");
				}
				
			}
			if(map.get("group")!=null){
				if(!curGroup.equals(map.get("group"))){
					throw new AppException("��ǰ�û�������Ȩ�ޣ�");
				}
			}
		}
		String conf[] = JGLDconfig[hj];
		String nexthj = conf[0];//��һ������
		String spjg = conf[1];//��ǰ�������
		String spzt = conf[2];//����״̬
		String desc = conf[3];//����
		
		
		Sp01 sp01 = (Sp01)sess.get(Sp01.class, sp0100);
		sp01.setSp0114(spzt);//������
		sess.update(sp01);
		
		String spb03 = sb.getSpb03();//��������
		String spb04 = sb.getSpb04();//������
		if((spb04==null||"".equals(spb04))&&(spb03==null||"".equals(spb03))&&"0".equals(conf[4])){
			throw new AppException("��ѡ������λ�����ˣ�");
		}
		if(hj!=1){
			String temp = map.get(conf[7]);
			if("0".equals(conf[6])&&(temp==null||"".equals(temp))){
				throw new AppException("���ڲ����������Ϊ�գ�");
			}
		}
		String spbl08 = map.get("spbl08");
		if((spbl08==null||"".equals(spbl08))&&"0".equals(conf[5])){
			throw new AppException("��ע����Ϊ�գ�");
		}
		sb.setSpb02(spzt);//������
		sb.setSpb06(nexthj);//�ڶ�����
		sess.save(sb);
		//����������־
		Sp_Bus_Log sbl = new Sp_Bus_Log();
		sbl.setSpbl00((long)Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004")));
		sbl.setSpb00(sp0100);//��������
		sbl.setSpbl01(SysManagerUtils.getUserId());//������id
		sbl.setSpbl02(SysManagerUtils.getUserName());//����������
		sbl.setSpbl03(SysManagerUtils.getUserGroupid());//��������
		sbl.setSpbl04(spjg);//��������1�Ǽ� 2���� 3����ͨ�� 4������ͨ�� 5���
		sbl.setSpbl05(new Date());//����ʱ��
		sbl.setSpbl06(desc);//����
		sbl.setSpbl07(hj+"");//��ǰ����
		sbl.setSpbl08(spbl08);
		sess.save(sbl);
		if("0".equals(type)&&!nexthj.equals("4")){
			//�������̱��˻���һ��
			String sql = "select t.spbl01 from SP_BUS_LOG t where spb00='"+sp0100+"' and t.spbl04 in('2') order by t.spbl00 desc";
			List<String> list = sess.createSQLQuery(sql).list();
			if(list.size()==0){
				throw new AppException("�޷��˻أ���������һ�����ڣ�");
			}
			String userid = list.get(0);
			sb.setSpb03(userid);//������
			sb.setSpb04(null);//��������
			sess.save(sb);
			
		}
		
		
		
		if(nexthj.equals("4")){//���
			//ɾ��������Ϣ�� ��������Ϣ��ӵ���ʷ��¼��
			sess.createSQLQuery("insert into SP_BUS_his (select * from SP_BUS where spb00='"+sp0100+"')").executeUpdate();
			sess.delete(sb);
		}
		
		
		
		sess.flush();
	}
	
	

	/**
	 * ��ȡ��ǰ������ �������û� ������
	 * @param id
	 * @return
	 */
	public static String[] getSPInfo(String id){
		String[] ret = new String[2];
		HBSession sess = HBUtil.getHBSession();
		Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, id);
		if(sb!=null){
			String sb03 = sb.getSpb03();
			if(sb03!=null&&!"".endsWith(sb03)){
				ret[0] = "user";
				ret[1] = sb03;
				return ret;
			}
			String sb04 = sb.getSpb04();
			if(sb04!=null&&!"".endsWith(sb04)){
				ret[0] = "group";
				ret[1] = sb04;
				return ret;
			}
		}
		return ret;
	}
	/**
	 * ��ȡ��ǰ������ �������û�  �ύ�� ������ת
	 * @param id
	 * @return
	 */
	public static String[] getSPInfoPC1(String id){
		String[] ret = new String[2];
		HBSession sess = HBUtil.getHBSession();
		Sp01_Pc sb = (Sp01_Pc)sess.get(Sp01_Pc.class, id);
		if(sb!=null){
			String sb03 = sb.getSpp11();
			if(sb03!=null&&!"".endsWith(sb03)){
				ret[0] = "user";
				ret[1] = sb03;
				return ret;
			}
		}
		return null;
	}
	
	/**
	 * ��ȡ��ǰ������ �������û�  ����
	 * @param id
	 * @return
	 */
	public static String[] getSPInfoPC2(String id){
		String[] ret = new String[2];
		HBSession sess = HBUtil.getHBSession();
		Sp01_Pc sb = (Sp01_Pc)sess.get(Sp01_Pc.class, id);
		if(sb!=null){
			String sb03 = sb.getSpp09();
			if(sb03!=null&&!"".endsWith(sb03)){
				ret[0] = "user";
				ret[1] = sb03;
				return ret;
			}
			String sb04 = sb.getSpp10();
			if(sb04!=null&&!"".endsWith(sb04)){
				ret[0] = "group";
				ret[1] = sb04;
				return ret;
			}
		}
		return null;
	}
	
}
