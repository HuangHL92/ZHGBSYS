package com.insigma.siis.local.pagemodel.cbdHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Transaction;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.AttachmentInfo;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CBDInfo;
import com.insigma.siis.local.business.entity.Cbdstatus;
import com.insigma.siis.local.business.entity.Ftpuser;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;

public class SBprocessSystemPageModel extends PageModel{
	public static String cbd_name = "";
	public static String cbd_id = "";
	public static String personname = "";
	public static String filePath = "";
	public static String status = "";

	@Override
	public int doInit() throws RadowException {
		String value = this.getRadow_parent_data();
		cbd_name = value.split("@")[0];
		cbd_id = value.split("@")[1];
		personname = value.split("@")[2];
		filePath = value.split("@")[3];
		status = value.split("@")[4];
		if(status.equals("3")){
			this.getExecuteSG().addExecuteCode("setButton('createCBD');");
			this.getExecuteSG().addExecuteCode("setButton('modifyCBD');");
			this.getExecuteSG().addExecuteCode("setButton('getCBD');");
			this.getExecuteSG().addExecuteCode("setButton('sureBtn');");
			this.getExecuteSG().addExecuteCode("setButton('sureBtn2');");
		}
		HBSession sess = HBUtil.getHBSession();
		StringBuffer stepValue=new StringBuffer();
		List list = sess.createSQLQuery("select cbd_status_step from cbd_status where cbd_id = '"+cbd_id+"' order by cbd_status_step").list();
		if(list.size()>0){
			//�����еĲ�����װ�����ŵ�ҳ����
			for(int i=0;i<list.size();i++){
				stepValue.append(list.get(i)).append(",");
			}
			stepValue.substring(0, stepValue.length()-1);
			this.getPageElement("step").setValue(stepValue.toString());
			this.getExecuteSG().addExecuteCode("controlStep('"+list.get(list.size()-1)+"');");
		}else{
			this.getExecuteSG().addExecuteCode("controlStep('0');");
			this.getPageElement("step").setValue("0");
		}
		this.getPageElement("cbd_id").setValue(cbd_id);
		this.getPageElement("cbd_name").setValue(cbd_name);
		this.getPageElement("filePath").setValue(filePath);
		this.getPageElement("status").setValue(status);
		this.getExecuteSG().addExecuteCode("setcbdname('"+cbd_name+"');");
		return 0;
	}
	
	/**
	 * ���ɱ����ʱ���
	 */
	@PageEvent("createCBD.onclick")
	public int createCBD() throws RadowException{
		String value = cbd_id+"@GU@"+personname;
		this.setRadow_parent_data(value);
		this.openWindow("editCBD", "pages.cbdHandler.AddCBDInfo");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �޸ı����ʱ���
	 */
	@PageEvent("modifyCBD.onclick")
	public int modifyCBD() throws RadowException{
		String value = cbd_id+"@GU@"+personname;
		this.setRadow_parent_data(value);
		this.openWindow("editCBD", "pages.cbdHandler.AddCBDInfo");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �鿴�ʱ���
	 */
	@PageEvent("getCBD.onclick")
	public int getCBD() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		List list = sess.createSQLQuery("select cbd_id from cbd_info where objectno ='"+cbd_id+"' and cbd_path='3'").list();
		String cbd_id2 = "";
		if(list.size()>0){
			cbd_id2 = list.get(0).toString();
		}else{
			this.setMainMessage("�������ɳʱ�����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//this.getPageElement("cbd_id").setValue(cbd_id2);
		this.getExecuteSG().addExecuteCode("expExcelTemp('"+cbd_id2+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �������
	 */
	@PageEvent("nextStep")
	public int startWork(String value) throws RadowException{
		
		HBSession sess = HBUtil.getHBSession();
		cbd_id = this.getPageElement("cbd_id").getValue();
		String uuid = UUID.randomUUID().toString();
		//��ȡʱ��
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String CBD_STATUS_TIME = sdf.format(new Date());
		Cbdstatus cbdstatus = new Cbdstatus();
		cbdstatus.setStatusid(uuid);
		cbdstatus.setCbdstatusstep(value);
		cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
		cbdstatus.setCbdid(cbd_id);
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.save(cbdstatus);
		ts.commit();
		
		this.setMainMessage("�������ɹ���");
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ˢ��
	 */
	@PageEvent("reload")
	public int reload() throws RadowException {
	    this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��������
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	@PageEvent("reply")
	public int reply(String value) throws Exception{
		
		//��ȡ����
		String[] values = value.split("@");
		String cbd_id = values[0];
		String cbd_name = values[1];
		String filePath = values[2];
		//�������ݿ����
		HBSession sess = HBUtil.getHBSession();
		//�����ʱ�������
		CBDInfo cbdinfo = null;
		//������������
		AttachmentInfo atta = null;
		//��ѯ�ʱ�����Ϣ�͸�����Ϣ
		List list= sess.createQuery("from CBDInfo where objectno = '"+cbd_id+"'").list();
		if(list.size()>0){
			cbdinfo = (CBDInfo) list.get(0);
		}else {
			this.setMainMessage("�óʱ�����û��¼�뱾���ʱ���������������");
			return EventRtnType.FAILD;
		}
		if(cbdinfo != null){
			
			List list_atta = sess.createQuery("from AttachmentInfo where objectid = '"+cbdinfo.getCbd_id()+"'").list();
			if(list_atta.size()>0 ){
				atta = (AttachmentInfo) list_atta.get(0);
			}else{
				this.setMainMessage("�óʱ�����û���ϴ������ʱ�������������������");
				return EventRtnType.FAILD;
			}
		}
		//��ȡftp·��
		StringBuffer ftpPath = new StringBuffer();
		List<Ftpuser> ftp_list = sess.createQuery("from Ftpuser t").list();
		if(ftp_list.size()>0){
			ftpPath.append(ftp_list.get(0).getHomedirectory().substring(1,ftp_list.get(0).getHomedirectory().length()));
			ftpPath.append("/cbd/down/");
		}else{
			//��װѹ�������·��
			//��·���е�up�滻Ϊdown
			//��ȡ·��
			ftpPath.append(filePath.substring(0,filePath.lastIndexOf("Pack")));
			
		}
		
		//����������
		CBDTools ct = new CBDTools();
		//��ȡϵͳ��Ŀ¼
		String rootpath = ct.getPath();
		//����ԭ·��
		String filepath = rootpath + atta.getFilepath();
		
		//��װ�����ļ�����ļ���
		String fileDir = cbd_name+filePath.substring(filePath.lastIndexOf("_"), filePath.lastIndexOf(".")).concat("����������");
		File file = new File(ftpPath.toString()+fileDir);
		//�������ļ������ڣ��������ļ�
		if(!file.exists() && !file.isDirectory() ){
			file.mkdirs();
		}
		
		File atta_file = new File(filepath);
		String atta_fileName = atta_file.getName();
		File targetFile = new File(ftpPath.toString()+fileDir+"/"+atta_fileName);
		ReportCBDFilePageModel.copyFile(atta_file, targetFile);
		
		//����ѹ���ļ���
		String zipFile = ftpPath.toString()+fileDir+".zip";
		//���ļ���ѹ����zipѹ������
		SevenZipUtil.zip7z(ftpPath.toString()+fileDir, zipFile, null);
		
		//��װxml�ļ�
		List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 where b0121 ='-1' ").list();
		B01 b01 = null;
		if (b01s != null && b01s.size() > 0) {
			b01 = b01s.get(0);
		} else {
			this.setMainMessage("ϵͳ��������ϵ����Ա��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//����ʱ�����
		java.sql.Timestamp now = DateUtil.getTimestamp();
		String time = DateUtil.timeToString(now);
		String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
		//����ftp�ϱ���Ϣxml����
		ZwhzPackDefine info = new ZwhzPackDefine();
		info.setId(cbd_id);
		info.setB0101(b01.getB0101());
		info.setB0111(b01.getB0111());
		info.setB0114(b01.getB0114());
		info.setB0194(b01.getB0194());

		info.setDataversion(DateUtil.dateToString(DateUtil.getSysDate(),
				"yyyyMMdd"));
		info.setLinkpsn("");
		info.setLinktel("");
		info.setRemark("");
		info.setStype("11");
		info.setStypename("�ʱ����ϱ�");
		info.setTime(time);
		info.setTranstype("down");
		info.setErrortype("��");
		info.setErrorinfo("��");
		info.setCbdStatus("3");//���óʱ���״̬  0��δ����
		
		List<SFileDefine> sfile = new ArrayList<SFileDefine>();
		//����xml�ļ���
		String packageFile = "Pack_" +fileDir + ".xml";
		
		SFileDefine sf = new SFileDefine();
		sf.setTime(time);
		File zipFile1 = new File(zipFile);
		sf.setName(zipFile1.getName());
		sf.setSize(ct.getFileSize(zipFile1));
		sfile.add(sf);
		
		//��¼�ļ���Ϣ
		info.setDatainfo("�����ʱ���ɨ����ļ�1����");
		
		info.setSfile(sfile);
		
		//����xml�ļ�
		FileUtil.createFile(ftpPath.toString() + "/" + packageFile,
				JXUtil.Object2Xml(info, true), false, "UTF-8");
		
		//ɾ���ļ�
		targetFile.delete();
		File xmlfile = new File(ftpPath.toString() + "/" + packageFile);
		File zipfile = new File(zipFile);
		File txmlfile = new File(ftpPath.toString() + "/" +fileDir+"/"+ packageFile);
		File tzipfile = new File(ftpPath.toString()+fileDir+"/"+fileDir+".zip");
		ReportCBDFilePageModel.copyFile(xmlfile, txmlfile);
		ReportCBDFilePageModel.copyFile(zipfile, tzipfile);
		String zipFile2 = ftpPath.toString()+fileDir+"�������ݰ�"+".zip";
		SevenZipUtil.zip7z(ftpPath.toString()+fileDir, zipFile2, null);
		txmlfile.delete();
		tzipfile.delete();
		//ɾ���ļ���
		file.delete();
		
		
		ct.changeStatus(cbd_id+"@"+filePath+"@3");
		
		String uuid = UUID.randomUUID().toString();
		//��ȡʱ��
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String CBD_STATUS_TIME = sdf.format(new Date());
		Cbdstatus cbdstatus = new Cbdstatus();
		cbdstatus.setStatusid(uuid);
		cbdstatus.setCbdstatusstep("3");
		cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
		cbdstatus.setCbdid(cbd_id);
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.save(cbdstatus);
		ts.commit();
		this.reloadPage();
		this.setMainMessage("������ɣ�");
		this.getExecuteSG().addExecuteCode("parent.radow.doEvent('btnsx.onclick');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 *  ��ת��������ҳ��
	 * @param value
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getBackWin")
	public int getBackWin(String value) throws RadowException{
		
		this.setRadow_parent_data(value);
		this.openWindow("backWin", "pages.cbdHandler.EditBackAdvice");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	/**
	 * �鿴/ɾ������
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("modifyAttach")
	public int editFile(String value) throws RadowException{
		this.setRadow_parent_data(value);
		this.openWindow("modifyFileWindow", "pages.search.ModifyAttach");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ������سʱ������ݰ��ж��Ƿ��и���
	 * @param value
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getCBDZip")
	public int getCBDZip(String value) throws RadowException {
		String [] values = value.split("@");
		String cbd_id = values[0];
		String cbd_name = values[1];
		//ִ�����ݿ����
		HBSession sess = HBUtil.getHBSession();
		//ͨ��objectno��ѯ�ϱ��ʱ���id
		String sb_cbd_id = sess.createSQLQuery("select cbd_id from cbd_info where objectno = '"+cbd_id+"'").uniqueResult().toString();
		if(sb_cbd_id.isEmpty()){
			this.setMainMessage("����¼���ϱ��ʱ�����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//��ѯ�ʱ�����Ӧ�ĸ�����Ϣ
		String sql = "select * from Attachment_Info ai where ai.objectid = '"+sb_cbd_id+"'";
		List attach_list =  sess.createSQLQuery(sql).list();
		if(attach_list.size()<=0){
			this.setMainMessage("��û���ϴ��ʱ����������������سʱ������ݰ���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//��ȡ�ʱ�����Ӧ��Ա���
		sql = "select ci.CBD_PERSONID from CBD_Info ci where ci.cbd_id = '"+sb_cbd_id+"'";
		List list = sess.createSQLQuery(sql).list();
		String personid = list.get(0).toString();
		//��ȡ��Ա���
		String[] personids = personid.split(",");
						
		//ѭ����ѯ��Ա�ĸ�����Ϣ
		for(int i =0;i<personids.length;i++){
			sql = "select * from Attachment_Info ai where ai.personid = '"+personids[i]+"'";
			List atta = sess.createSQLQuery(sql).list();
			//�ж��Ƿ�����Ա�ĸ�����Ϣ���������Աû���ϴ��������������ϱ�
			if(atta.size()<=0){
				this.setMainMessage("��û���ϴ���Ա�������������سʱ������ݰ���");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//��ȡ�ʱ�����Ӧ��Ա����
		sql = "select CBD_PERSONNAME from cbd_info where cbd_id = '"+sb_cbd_id+"'";
		List list2 = sess.createSQLQuery(sql).list();
		String personname = list2.get(0).toString();
		//���ϱ��ʱ���id���ϱ��ʱ������ơ������ʱ���id����ǰ̨
		this.getExecuteSG().addExecuteCode("createCBDZip('"+sb_cbd_id+"','"+cbd_name+"','"+cbd_id+"','"+personname+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//ͨ�����̷�ʽ����
	@PageEvent("reply1")
	public int reply1(String value){
		
		HBSession sess = HBUtil.getHBSession();
		
		String hql = "from CBDInfo where objectno = '"+value.split("@")[0]+"' and cbd_path = '3'";
		List<CBDInfo> cbdlist = sess.createQuery(hql).list();
		if(cbdlist.size()==0){
			this.setMainMessage("����¼�뱾���ʱ�����");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			hql = "from AttachmentInfo where objectid = '"+cbdlist.get(0).getCbd_id()+"'";
			List attalist = sess.createQuery(hql).list();
			if(attalist.size() == 0){
				this.setMainMessage("û���ϴ������ʱ���������");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		this.getExecuteSG().addExecuteCode("createCBDZip1('"+cbdlist.get(0).getCbd_id()+"','"+cbdlist.get(0).getCbd_name()+"','"+value+"','"+value.split("@")[1]+"');");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
