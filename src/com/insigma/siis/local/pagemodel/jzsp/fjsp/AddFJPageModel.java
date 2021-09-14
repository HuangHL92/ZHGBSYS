package com.insigma.siis.local.pagemodel.jzsp.fjsp;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;

import com.JUpload.JUpload;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.Sp01;
import com.insigma.siis.local.business.entity.Sp01_Pc;
import com.insigma.siis.local.business.entity.Sp_Att;
import com.insigma.siis.local.business.entity.Sp_Bus;
import com.insigma.siis.local.business.entity.Sp_Bus_Log;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.jzsp.SP2Util;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;

public class AddFJPageModel extends PageModel  implements JUpload{
	
	
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
		String spp13 = this.getPageElement("spp13").getValue();
		String spp02 = this.getPageElement("spp02").getValue();
		if(spp13==null||"".equals(spp13)){
			this.setMainMessage("���ⲻ��Ϊ�գ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(spp02==null||"".equals(spp02)){
			this.setMainMessage("���Ͳ���Ϊ�գ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		Sp01_Pc sp01_pc = new Sp01_Pc();
		String spb04 = this.getPageElement("spb04").getValue();//����λ ���쵼
		String usertype = this.getPageElement("usertype").getValue();//����λ ���쵼
		String groupid = SysManagerUtils.getUserGroupid();
		Map<String, String> map = new HashMap<String, String>();
		HBSession sess = HBUtil.getHBSession();
		boolean isUpload = false;
		boolean isAdd = false;
		try {
			sess.getTransaction().begin();
			
			if(spp00==null||"".equals(spp00)){//����
				isAdd=true;
				sp01_pc.setSpp00(UUID.randomUUID().toString());
				sp01_pc.setSpp08("0");//����״̬ 0δ���� 1������ 2����ͨ�� 3������ͨ��
				sp01_pc.setSpp11(SysManagerUtils.getUserId());//������
				sp01_pc.setSpp03(new Date());//�Ǽ�ʱ��
				sp01_pc.setSpp02(spp02);//�������
				sp01_pc.setSpp13(spp13);//����
				sess.save(sp01_pc);
				spp00 = sp01_pc.getSpp00();
				
				
				//��������
				Sp_Bus sb = new Sp_Bus();
				sb.setSpb00(sp01_pc.getSpp00());//��������
				sb.setSpb01(sp01_pc.getSpp00());//ҵ������
				sb.setSpb02("0");//����״̬ 0δ���� 1������ 2����ͨ�� 3������ͨ��
				if(spb04!=null&&!"".equals(spb04)){
					if("group".equals(usertype)){
						sb.setSpb04(spb04);//��������
					}else if("user".equals(usertype)){
						sb.setSpb03(spb04);//������
					}
				}
				
				sb.setSpb05(spp02);//����1�����쵼
				sb.setSpb06("1");//��ǰ�ڵ�
				sess.save(sb);
				//����������־
				Sp_Bus_Log sbl = new Sp_Bus_Log();
				sbl.setSpbl00((long)Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004")));
				sbl.setSpb00(sp01_pc.getSpp00());//��������
				sbl.setSpbl01(SysManagerUtils.getUserId());//������id
				sbl.setSpbl02(SysManagerUtils.getUserName());//����������
				sbl.setSpbl03(groupid);//��������
				sbl.setSpbl04("1");//��������1�Ǽ� 2���� 3����ͨ�� 4������ͨ�� 5����
				sbl.setSpbl05(new Date());//����ʱ��
				sbl.setSpbl06("�����ǼǱ�");//����
				sbl.setSpbl07("1");//�����ڵ�
				sess.save(sbl);
				sess.flush();
				
				isUpload = true;
				//this.setMainMessage("�����ɹ�");
			}else{
				
				sp01_pc = (Sp01_Pc)sess.get(Sp01_Pc.class, spp00);
				if(sp01_pc==null){
					this.setMainMessage("��������Ϣ�����ڣ�");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if("0".equals(sp01_pc.getSpp08())){//�Ǽ�״̬
					sp01_pc.setSpp02(spp02);//�������
					sp01_pc.setSpp13(spp13);//����
					isUpload = true;
				}
				
				Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, spp00);
				String[] spInfo = SP2Util.getSPInfo(spp00);
				if(spInfo!=null){
					map.put(spInfo[0], spInfo[1]);
				}
				if(spb04!=null&&!"".equals(spb04)){
					if("group".equals(usertype)){
						sb.setSpb04(spb04);//��������
						sb.setSpb03(null);//������
					}else if("user".equals(usertype)){
						sb.setSpb04(null);//��������
						sb.setSpb03(spb04);//������
					}
				}else{
					sb.setSpb04(null);//��������
					sb.setSpb03(null);//������
				}
				
				sess.update(sb);
				sess.flush();
			}
			//����
			if("ss2".equals(ss)){
				
				String spbl08 = this.getPageElement("spbl08").getValue();
				Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, spp00);
				int hj = Integer.valueOf(sb.getSpb06());
				if(hj==4){
					throw new AppException("��������ᣡ");
				}
				
				String nexthj = "2";//��һ������
				String spjg = "2";//��ǰ�������//��������1�Ǽ� 2���� 3����ͨ�� 4������ͨ�� 5����
				String spzt = "1";//����״̬
				String desc = "����";//����
				
				
				
				sp01_pc.setSpp08(spzt);//������
				sess.update(sp01_pc);
				
				String spb03 = sb.getSpb03();//��������
				spb04 = sb.getSpb04();//������
				if((spb04==null||"".equals(spb04))&&(spb03==null||"".equals(spb03))){
					throw new AppException("��ѡ������λ�����ˣ�");
				}
				sb.setSpb02(spzt);//������
				sb.setSpb06(nexthj);//�ڶ�����
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
				sbl.setSpbl08(spbl08);
				sess.save(sbl);			
				
				sess.flush();
				this.getExecuteSG().addExecuteCode("saveCallBack('����ɹ�','1');");
			}
			sess.getTransaction().commit();
			if(isUpload){
				this.upLoadFile("file03");
				this.getPageElement("spp00").setValue(sp01_pc.getSpp00());
				if(isAdd){
					this.getExecuteSG().addExecuteCode("setFileLength('�����ɹ�');");
				}else{
					this.getExecuteSG().addExecuteCode("setFileLength('����ɹ�');");
				}
			}
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
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String spp00 = this.getPageElement("spp00").getValue();
		try {
			HBSession sess = HBUtil.getHBSession();
			if(spp00!=null&&!"".equals(spp00)){
				Sp01_Pc sp01_pc = (Sp01_Pc)sess.get(Sp01_Pc.class, spp00);
				if(sp01_pc==null){
					this.setMainMessage("��ѯʧ��");
					return EventRtnType.NORMAL_SUCCESS;
				}
				PMPropertyCopyUtil.copyObjValueToElement(sp01_pc, this);
				Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, spp00);
				String spb04 = sb.getSpb04();//��������
				String spb03 = sb.getSpb03();//������
				if("0".equals(sb.getSpb02())){//����״̬�»���
					if(spb04!=null&&!"".equals(spb04)){
						this.getPageElement("usertype").setValue("group");
						this.getPageElement("spb04").setValue(spb04);
						this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getGroupName(spb04));
					}else if(spb03!=null&&!"".equals(spb03)){
						this.getPageElement("usertype").setValue("user");
						this.getPageElement("spb04").setValue(spb03);
						this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getUserName(spb03));
					}
				}
				
				String spb06 = sb.getSpb06();//��ǰ�ڵ�
				if("1".equals(spb06)){//�Ǽǽڵ�
					this.getExecuteSG().addExecuteCode("setDisabled1();");
				}else if("2".equals(spb06)){//һ������
					this.getExecuteSG().addExecuteCode("setDisabled2();");
				}else if("3".equals(spb06)){//��������
					this.getExecuteSG().addExecuteCode("setDisabled3();");
				}else if("4".equals(spb06)){//��������
					this.getExecuteSG().addExecuteCode("setDisabled4();");
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
			}else{
				this.getExecuteSG().addExecuteCode("setDisabled1();");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ��");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("searchA01")
	public int searchA01(String name) throws RadowException, AppException{
		
		try {
			String sql = "select count(1) from a01 where status!='4' and a0101=?";
			HBSession sess = HBUtil.getHBSession();
			Object size = sess.createSQLQuery(sql).setString(0, name).uniqueResult();
			int s = Integer.valueOf(size.toString());
			if(s==1){
				List<A01> a01list = sess.createQuery("from A01 where status!='4' and a0101=?").setString(0, name).list();
				A01 a01 = a01list.get(0);
				this.getPageElement("a0000").setValue(a01.getA0000());
				this.getPageElement("sp0103").setValue(a01.getA0104());
				this.getPageElement("sp0104").setValue(a01.getA0107());
				this.getPageElement("sp0106").setValue(a01.getA0192a());
			}else if(s==0){
				this.setMainMessage("�޸���Ա��Ϣ��");
			}else{
				this.getExecuteSG().addExecuteCode("openPwin();");
			}
		}catch (Exception e) {
			this.setMainMessage("��ѯʧ��");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("searchA01ByA0000")
	public int searchA01ByA0000(String a0000) throws RadowException, AppException{
		
		try {
			if(a0000==null||"".equals(a0000)){
				this.setMainMessage("��ѡ����Ա��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			HBSession sess = HBUtil.getHBSession();
				A01 a01 = (A01)sess.get(A01.class, a0000);
				if(a01==null){
					this.setMainMessage("�޸���Ա��Ϣ��");
					return EventRtnType.NORMAL_SUCCESS;
				}
				this.getPageElement("a0000").setValue(a01.getA0000());
				this.getPageElement("sp0103").setValue(a01.getA0104());
				this.getPageElement("sp0104").setValue(a01.getA0107());
				this.getPageElement("sp0106").setValue(a01.getA0192a());
				this.getPageElement("sp0102").focus();
		}catch (Exception e) {
			this.setMainMessage("��ѯʧ��");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// ����ļ�����
		String isfile = formDataMap.get("Filename");
		// �ж��Ƿ��ϴ��˸�����û���ϴ��򲻽����ļ�����
		if (isfile != null && !isfile.equals("")) {
			try {
				// ��ȡ����Ϣ
				FileItem fi = fileItem.get(0);
				DecimalFormat df = new DecimalFormat("#.00");
				String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
				// ����ļ�����1M����ʾM��С������ʾkb
				if (fi.getSize() < 1048576) {
					fileSize = (int) fi.getSize() / 1024 + "KB";
				}
				if (fi.getSize() < 1024) {
					fileSize = (int) fi.getSize() / 1024 + "B";
				}
				String id = saveFile(formDataMap, fi,fileSize);
				map.put("file_pk", id);
				map.put("file_name", isfile);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return map;
	}
	public static String  disk = JSGLBS.HZBPATH ;
	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// �����Ա��Ϣid
		String spp00 = formDataMap.get("spp00");
		String filename = formDataMap.get("Filename");
		
		Sp_Att spa = new Sp_Att();
		spa.setSpb00(spp00);;//ҵ������
		spa.setSpa00(UUID.randomUUID().toString());//����
		spa.setSpa03(SysManagerUtils.getUserId());//�û�id
		spa.setSpa04(new Date());//�ϴ�ʱ��
		spa.setSpa06(fileSize);
		spa.setSpa02(filename);
		
		String directory = "zhgbuploadjzspfiles" + File.separator +spp00+ File.separator;
		String filePath = directory  + spa.getSpa00();
		File f = new File(disk + directory);
		
		if(!f.isDirectory()){
			f.mkdirs();
		}
		fi.write(new File(disk + filePath));
		spa.setSpa05(directory);
		HBUtil.getHBSession().save(spa);
		HBUtil.getHBSession().flush();
		
		return spa.getSpa00();
	}
	@Override
	public String deleteFile(String id) {
		try {
			HBSession sess = HBUtil.getHBSession();
			Sp_Att spa = (Sp_Att)sess.get(Sp_Att.class, id);
			if(spa==null){
				return null;//ɾ��ʧ��
			}
			String directory = disk+spa.getSpa05();
			File f = new File(directory+id);
			if(f.isFile()){
				f.delete();
			}
			sess.delete(spa);
			sess.flush();
			
			return id;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
