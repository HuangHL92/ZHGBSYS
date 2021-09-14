package com.insigma.siis.local.pagemodel.jzsp.jgld;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Sp01;
import com.insigma.siis.local.business.entity.Sp01_Pc;
import com.insigma.siis.local.business.entity.Sp_Bus;
import com.insigma.siis.local.business.entity.Sp_Bus_Log;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.jzsp.SP2Util;

public class STJZHZPageModel extends PageModel{
	
	
	/**
	 * ������Ϣ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String sp0100s = this.getPageElement("sp0100s").getValue();
		if(sp0100s==null||"".equals(sp0100s)){
			String spp00 = this.getPageElement("spp00").getValue();
			String sql="select * from SP01 t where spp00 ='"+spp00+"' "
					+ " order by t.sp0116 desc";
			this.pageQuery(sql, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		}
		sp0100s = sp0100s.substring(0,sp0100s.length()-1).replace(",", "','");
		String sql="select * from SP01 t where sp0100 in('"+sp0100s+"') "
				+ " order by t.sp0116 desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * ������Ϣ�޸ı���
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("save.onclick")
	public int save(String ss) throws RadowException, AppException{
		String spp00 = this.getPageElement("spp00").getValue();
		Sp01_Pc spc = new Sp01_Pc();
		String spb04 = this.getPageElement("spb04").getValue();//����λ ���쵼
		String usertype = this.getPageElement("usertype").getValue();//����λ ���쵼
		String spp04 = this.getPageElement("spp04").getValue();//�������
		String spp05 = this.getPageElement("spp05").getValue();//���쵼���
		List<HashMap<String,String>> list = this.getPageElement("memberGrid").getStringValueList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		String groupid = SysManagerUtils.getUserGroupid();
		Map<String, String> map = new HashMap<String, String>();
		HBSession sess = HBUtil.getHBSession();
		
		try {
			sess.getTransaction().begin();
			
			if(spp00==null||"".equals(spp00)){//����
				//1�������μ�¼
				spc.setSpp00(UUID.randomUUID().toString());
				spc.setSpp02("�쵼�ɲ����ż�ְ������");//��������
				spc.setSpp04(spp04);
				spc.setSpp05(spp05);
				spc.setSpp08("0");//����״̬
				spc.setSpp03(new Date());//�Ǽ�ʱ��
				spc.setSpp11(SysManagerUtils.getUserId());//������
				if(spb04!=null&&!"".equals(spb04)){
					if("group".equals(usertype)){
						spc.setSpp10(spb04);//��������
					}else if("user".equals(usertype)){
						spc.setSpp09(spb04);//������
					}
				}
				sess.save(spc);
				spp00 = spc.getSpp00();
				//2���������־ ��������
				
				for(HashMap<String,String> m : list){
					//����������־
					Sp_Bus_Log sbl = new Sp_Bus_Log();
					sbl.setSpbl00((long)Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004")));
					sbl.setSpb00(m.get("sp0100"));//��������
					sbl.setSpbl01(SysManagerUtils.getUserId());//������id
					sbl.setSpbl02(SysManagerUtils.getUserName());//����������
					sbl.setSpbl03(groupid);//��������
					sbl.setSpbl04("1");//��������1�Ǽ� 2���� 3����ͨ�� 4������ͨ�� 5����
					sbl.setSpbl05(new Date());//����ʱ��
					sbl.setSpbl06("��������");//����
					sbl.setSpbl07("2");//�����ڵ�
					sbl.setSpbl08("���һ���");
					sess.save(sbl);
					Sp01 sp01 = (Sp01)sess.get(Sp01.class, m.get("sp0100"));
					sp01.setSpp00(spp00);
					sess.save(sp01);
				}
				sess.flush();
			}else{//�޸�
				spc = (Sp01_Pc)sess.get(Sp01_Pc.class, spp00);
				if(spc==null){
					this.setMainMessage("���ܱ���Ϣ�����ڣ�");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if("0".equals(spc.getSpp08())){//�Ǽ�״̬
					if(spb04!=null&&!"".equals(spb04)){
						if("group".equals(usertype)){
							spc.setSpp10(spb04);//��������
						}else if("user".equals(usertype)){
							spc.setSpp09(spb04);//������
						}
						
					}
					spc.setSpp04(spp04);
					spc.setSpp05(spp05);
				}else{
					spc.setSpp04(spp04);
					spc.setSpp05(spp05);
				}
				sess.update(spc);
				sess.flush();
			}
			
			
			
			//����
			if("ss2".equals(ss)){
				if("0".equals(spc.getSpp08())){//�Ǽ�״̬
					String[] spInfo = SP2Util.getSPInfoPC1(spc.getSpp00());
					if(spInfo!=null){
						map.put(spInfo[0], spInfo[1]);
					}
					spc.setSpp08("1");
					map.put("spbl08", spp04);
					map.put("spp04", spp04);
					if(spb04==null||"".equals(spb04)){
						throw new AppException("��ѡ������λ�����ˣ�");
					}
					if(spp04==null||"".equals(spp04)){
						throw new AppException("����д���������");
					}
					spc.setSpp06(sdf.format(new Date()));
					sess.update(spc);
					
					//���������־ ��������
					for(HashMap<String,String> m : list){
						//����������־
						SP2Util.apply02(m.get("sp0100"), sess, map, "1");
					}
					sess.flush();
				}else if("1".equals(spc.getSpp08())){//������  ��������
					String[] spInfo = SP2Util.getSPInfoPC2(spc.getSpp00());
					if(spInfo!=null){
						map.put(spInfo[0], spInfo[1]);
					}
					spc.setSpp08("2");//ͨ��
					map.put("spbl08", spp05);
					map.put("spp05", spp05);
					
					if(spp05==null||"".equals(spp05)){
						throw new AppException("����д���쵼�����");
					}
					spc.setSpp07(sdf.format(new Date()));
					//���������־ ��������
					for(HashMap<String,String> m : list){
						//����������־
						SP2Util.apply02(m.get("sp0100"), sess, map, "1");
					}
					sess.flush();
				}
			}else if("ss3".equals(ss)){//δͨ��
				spc.setSpp08("3");//ͨ��
				map.put("spbl08", spp05);
				map.put("spp05", spp05);
				
				if(spp05==null||"".equals(spp05)){
					throw new AppException("����д���쵼�����");
				}
				spc.setSpp07(sdf.format(new Date()));
				//���������־ ��������
				for(HashMap<String,String> m : list){
					//����������־
					SP2Util.apply02(m.get("sp0100"), sess, map, "0");
				}
				sess.flush();
			}
			sess.getTransaction().commit();
			this.getExecuteSG().addExecuteCode("saveCallBack('����ɹ�','1');");
		} catch (AppException e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			sess.getTransaction().rollback();
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ��");
			sess.getTransaction().rollback();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String spp00 = this.getPageElement("spp00").getValue();
		HBSession sess = HBUtil.getHBSession();
		if(spp00==null||"".equals(spp00)){
			this.getExecuteSG().addExecuteCode("setDisabled1();");
		}else{
			Sp01_Pc spc = (Sp01_Pc)sess.get(Sp01_Pc.class, spp00);
			if(spc==null){
				this.setMainMessage("��ѯʧ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String spp09 = spc.getSpp09();
			String spp10 = spc.getSpp10();
			if("0".equals(spc.getSpp08())){//����״̬�»���
				if(spp10!=null&&!"".equals(spp10)){
					this.getPageElement("usertype").setValue("group");
					this.getPageElement("spb04").setValue(spp10);
					this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getGroupName(spp10));
				}else if(spp09!=null&&!"".equals(spp09)){
					this.getPageElement("usertype").setValue("user");
					this.getPageElement("spb04").setValue(spp09);
					this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getUserName(spp09));
				}
			}
			String spp08 = spc.getSpp08();
			PMPropertyCopyUtil.copyObjValueToElement(spc, this);
			if("0".equals(spp08)){//����
				this.getExecuteSG().addExecuteCode("setDisabled1();");
			}else if("1".equals(spp08)){//������
				this.getExecuteSG().addExecuteCode("setDisabled2();");
			}else{
				this.getExecuteSG().addExecuteCode("setDisabled3();");
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * grid���ʵʱ����
	 * @param str
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveChange")
	public int saveChange(String str) throws RadowException, AppException{
		String field = this.getPageElement("field").getValue();//�ֶ���
		String name = this.getPageElement("name").getValue();//����
		String value = this.getPageElement("value").getValue();//�޸ĺ��ֵ
		String sp01id = this.getPageElement("sp01id").getValue();//����
		String fieldTitle = this.getPageElement("fieldTitle").getValue();//�ֶ���ע��
		try {
			String sql1 ="update sp01 set "+field+" = ? where  sp0100 = ?";
			HBUtil.executeUpdate(sql1,new Object[]{value,sp01id});
			this.toastmessage("����Ϊ��" + name + "�ġ�"+fieldTitle+"����Ϣ�ѱ��档");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ��!");
		}
		return 0;
			
	}
	
}
