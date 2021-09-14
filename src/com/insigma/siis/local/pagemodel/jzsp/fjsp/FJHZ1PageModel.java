package com.insigma.siis.local.pagemodel.jzsp.fjsp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.insigma.siis.local.business.entity.Sp_Att;
import com.insigma.siis.local.business.entity.Sp_Bus;
import com.insigma.siis.local.business.entity.Sp_Bus_Log;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.jzsp.SP2Util;

public class FJHZ1PageModel extends PageModel{
	
	
	/**
	 * ������Ϣ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
	
		
		String spp00 = this.getPageElement("spp00").getValue();
		String sql="select * from SP01 t where spp00 ='"+spp00+"' "
				+ " order by t.sp0116 asc";
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		String groupid = SysManagerUtils.getUserGroupid();
		HBSession sess = HBUtil.getHBSession();
		
		try {
			sess.getTransaction().begin();
			
			
			
			//����
			if("ss2".equals(ss)){
				String[] spInfo = SP2Util.getSPInfo(spp00);
				Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, spp00);
				int hj = Integer.valueOf(sb.getSpb06());
				if(hj>=4){
					throw new AppException("��������ᣡ");
				}
				
				String curUser = SysManagerUtils.getUserId();
				String curGroup = SysManagerUtils.getUserGroupid();
				if("user".equals(spInfo[0])){//�����û�Ϊ��ǰ�û�
					if(!curUser.equals(spInfo[1])){
						throw new AppException("��ǰ�û�������Ȩ�ޣ�");
					}
					
				}
				if("group".equals(spInfo[0])){
					if(!curGroup.equals(spInfo[1])){
						throw new AppException("��ǰ�û�������Ȩ�ޣ�");
					}
				}
				
				String nexthj = "3";//��һ������
				String spjg = "3";//��ǰ�������
				String spzt = "1";//����״̬
				String desc = spp04;//����
				
				
				spc = (Sp01_Pc)sess.get(Sp01_Pc.class, spp00);
				spc.setSpp08(spzt);//������
				spc.setSpp04(spp04);//�������
				spc.setSpp06(sdf.format(new Date()));
				sess.update(spc);
		
				if(spb04==null||"".equals(spb04)){
					throw new AppException("��ѡ������λ�����ˣ�");
				}
			
					
				
				if(spp04==null||"".equals(spp04)){
					throw new AppException("�����������Ϊ�գ�");
				}
				sb.setSpb02(spzt);//������
				sb.setSpb06(nexthj);//�ڶ�����
				if("user".equals(usertype)){
					sb.setSpb03(spb04);//������
					sb.setSpb04(null);
				}else if("group".equals(usertype)){
					sb.setSpb03(null);//������
					sb.setSpb04(spb04);
				}
				sess.save(sb);
				//����������־
				Sp_Bus_Log sbl = new Sp_Bus_Log();
				sbl.setSpbl00((long)Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004")));
				sbl.setSpb00(spp00);//��������
				sbl.setSpbl01(SysManagerUtils.getUserId());//������id
				sbl.setSpbl02(SysManagerUtils.getUserName());//����������
				sbl.setSpbl03(SysManagerUtils.getUserGroupid());//��������
				sbl.setSpbl04(spjg);//��������1�Ǽ� 2���� 3����ͨ�� 4������ͨ�� 5���
				sbl.setSpbl05(new Date());//����ʱ��
				sbl.setSpbl06(desc);//����
				sbl.setSpbl07(hj+"");//��ǰ����
				sbl.setSpbl08(spp04);
				sess.save(sbl);
				
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
			this.setMainMessage("��ѯʧ��");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			Sp01_Pc spc = (Sp01_Pc)sess.get(Sp01_Pc.class, spp00);
			if(spc==null){
				this.setMainMessage("��ѯʧ��");
				return EventRtnType.NORMAL_SUCCESS;
			}

			/*Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, spp00);
			String spb03 = sb.getSpb03();
			String spb04 = sb.getSpb04();
			if("2".equals(sb.getSpb06())){//����״̬�»���
				if(spb04!=null&&!"".equals(spb04)){
					this.getPageElement("usertype").setValue("group");
					this.getPageElement("spb04").setValue(spb04);
					this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getGroupName(spb04));
				}else if(spb03!=null&&!"".equals(spb03)){
					this.getPageElement("usertype").setValue("user");
					this.getPageElement("spb04").setValue(spb03);
					this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getUserName(spb03));
				}
			}*/
			String spp08 = spc.getSpp08();
			PMPropertyCopyUtil.copyObjValueToElement(spc, this);
			if("1".equals(spp08)){//������
				this.getExecuteSG().addExecuteCode("setDisabled2();");
			}
			
			
			//�����ļ���Ϣ
			List<Sp_Att> spalist = sess.createQuery("from Sp_Att where spb00='"+spp00+"'").list();
			
			if(spalist!=null){
				List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
				for(Sp_Att spa : spalist){
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", spa.getSpa00());
					map.put("name", spa.getSpa02());
					map.put("fileSize", spa.getSpa06());
					//map2.put("readOnly", "true");
					
					listmap.add(map);
					this.setFilesInfo("file03",listmap,false);
				}
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
		String spp00 = this.getPageElement("spp00").getValue();
		String field = this.getPageElement("field").getValue();//�ֶ���
		String name = this.getPageElement("name").getValue();//����
		String value = this.getPageElement("value").getValue();//�޸ĺ��ֵ
		String sp01id = this.getPageElement("sp01id").getValue();//����
		String fieldTitle = this.getPageElement("fieldTitle").getValue();//�ֶ���ע��
		try {
			Sp01 sp01 = (Sp01)HBUtil.getHBSession().get(Sp01.class, sp01id);
			if(sp01!=null){//����
				String sql1 ="update sp01 set "+field+" = ? where  sp0100 = ?";
				HBUtil.executeUpdate(sql1,new Object[]{value,sp01id});
			}else{//����
				String sql1 ="insert into sp01(sp0100,"+field+",spp00,sp0116) values(?,?,?,sysdate)";
				HBUtil.executeUpdate(sql1,new Object[]{sp01id,value,spp00});
			}
			
			this.toastmessage("��"+fieldTitle+"����Ϣ�ѱ��档");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ��!");
		}
		return 0;
			
	}
	
}
