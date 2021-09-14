package com.insigma.siis.local.pagemodel.cbdHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Cbdstatus;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;

public class EditBackAdvicePageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		String value = this.getRadow_parent_data();
		String cbd_id = value.split("@")[0];
		String cbd_name = value.split("@")[1];
		String filePath = value.split("@")[2];
		this.getPageElement("cbd_id").setValue(cbd_id);
		this.getPageElement("cbd_name").setValue(cbd_name);
		this.getPageElement("filePath").setValue(filePath);
		this.getPageElement("operateFlag").setValue(value.split("@")[3]);
		return 0;
	}
	
	/**
	 * ִ�д�����
	 * @return
	 * @throws Exception
	 */
	@PageEvent("save.onclick")
	public int saveBackAdvice() throws Exception{
		
		String cbd_text = this.getPageElement("cbd_text").getValue();
		String cbd_name = this.getPageElement("cbd_name").getValue();
		String filePath = this.getPageElement("filePath").getValue();
		String cbd_id = this.getPageElement("cbd_id").getValue();
		String linkpsn = this.getPageElement("linkpsn").getValue();
		String linktel = this.getPageElement("linktel").getValue();
		String remark = this.getPageElement("remark").getValue();
		if("gp".equals(this.getPageElement("operateFlag").getValue())){
			//���ϱ��ʱ���id���ϱ��ʱ������ơ������ʱ���id����ǰ̨
			this.getExecuteSG().addExecuteCode("createCBDZip('"+cbd_text+"','"+cbd_name+"','"+cbd_id+"','"+filePath+"','"+linkpsn+"','"+linktel+"','"+remark+"','gpb');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//��·���е�up�滻Ϊdown
		String filename = filePath.replace("up", "down");
		//��ȡ·��
		String path = filename.substring(0,filename.lastIndexOf("Pack"));
		//��װ�������ļ���
		String fileDir = cbd_name+filename.substring(filename.lastIndexOf("_"), filename.lastIndexOf(".")).concat("�ķ���");
		String fileName = cbd_name+filename.substring(filename.lastIndexOf("_"), filename.lastIndexOf(".")).concat("�Ĵ�����.txt");
		File file = new File(path+fileDir);
		File file1 = new File(path+fileDir+"/"+fileName);
		//�������ļ������ڣ��������ļ�
		if(!file.exists() && !file.isDirectory() ){
			file.mkdirs();
		}
		//��ҳ���е��������д���ļ���
		OutputStreamWriter pw = null;//����һ����
		pw = new OutputStreamWriter(new FileOutputStream(file1),"GBK");//ȷ����������ļ��ͱ����ʽ
		pw.write(cbd_text);//��Ҫд���ļ������ݣ����Զ��write
		pw.close();//�ر���
		
		//����ѹ���ļ���
		String zipFile = path+fileDir+".zip";
		//���ļ���ѹ����zipѹ������
		SevenZipUtil.zip7z(path+fileDir, zipFile, null);
		
		//��װxml�ļ�
		HBSession sess = HBUtil.getHBSession();
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
		info.setLinkpsn(linkpsn);
		info.setLinktel(linktel);
		info.setRemark(remark);
		info.setStype("11");
		info.setStypename("�ʱ����ϱ�");
		info.setTime(time);
		info.setTranstype("down");
		info.setErrortype("��");
		info.setErrorinfo("��");
		info.setCbdStatus("2");//���óʱ���״̬  0��δ����
		
		List<SFileDefine> sfile = new ArrayList<SFileDefine>();
		//����xml�ļ���
		String packageFile = "Pack_" +fileDir + ".xml";
		
		SFileDefine sf = new SFileDefine();
		sf.setTime(time);
		File zipFile1 = new File(zipFile);
		sf.setName(zipFile1.getName());
		//����������
		CBDTools ct = new CBDTools();
		sf.setSize(ct.getFileSize(zipFile1));
		sfile.add(sf);
		
		//��¼�ļ���Ϣ
		info.setDatainfo("�ʱ����������ļ�1����");
		
		info.setSfile(sfile);
		
		//����xml�ļ�
		FileUtil.createFile(path + "/" + packageFile,
				JXUtil.Object2Xml(info, true), false, "UTF-8");
		
		//ɾ���������ļ�
		file1.delete();
		//ɾ���������ļ���
		file.delete();
		
		ct.changeStatus(cbd_id+"@"+filePath+"@2");
		
		String uuid = UUID.randomUUID().toString();
		//��ȡʱ��
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String CBD_STATUS_TIME = sdf.format(new Date());
		Cbdstatus cbdstatus = new Cbdstatus();
		cbdstatus.setStatusid(uuid);
		cbdstatus.setCbdstatusstep("2");
		cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
		cbdstatus.setCbdid(cbd_id);
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.save(cbdstatus);
		ts.commit();
		this.getExecuteSG().addExecuteCode("parent.radow.doEvent('reload')");
		
		this.setMainMessage("�ʱ�����سɹ���");
		this.closeCueWindowByYes("backWin");
		this.getExecuteSG().addExecuteCode("parent.parent.radow.doEvent('lfsearch')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("gbreload")
	public int reload(){
		
		return EventRtnType.NORMAL_SUCCESS;
	}

}
