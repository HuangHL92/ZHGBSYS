package com.insigma.siis.local.pagemodel.cbdHandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import org.hibernate.Transaction;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.AttachmentInfo;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CBDInfo;
import com.insigma.siis.local.business.entity.Cbdstatus;
import com.insigma.siis.local.business.entity.Reportftp;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzFtpClient;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class ReportCBDFilePageModel extends PageModel {

	public static String cbdid = "";
	public static String cbdname = "";
	public static String bj_cbdid = "";
	public static String personname = "";
	public static String personid = "";
	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		String cbd_id = this.getRadow_parent_data();
		cbdid = cbd_id.split("@")[0];
		cbdname = cbd_id.split("@")[1];
		bj_cbdid = cbd_id.split("@")[2];
		personname = cbd_id.split("@")[3];
		personid = cbd_id.split("@")[4];
		return 0;
	}

	/**
	 *  ��ftpѡ�񴰿�
	 * @param name
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("searchDeptBtn.ontriggerclick")
	@NoRequiredValidate
	public int searchDept(String name) throws RadowException {
		this.openWindow("deptWin", "pages.repandrec.plat.SearchFtpUp");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 *  �ϱ��ʱ�����ftp���ͣ�
	 * @param name
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("reppackagebtn.onclick")
	public int reppackagebtn() throws RadowException, AppException {
		
		//�Ƚ����ϱ������Ĵ�������/ѹ����
		String zipPath = collectAttachment(cbdid,cbdname);
		//�жϸ������������������㣬�������ϱ��ʱ���
		if("noCBDAtta".equals(zipPath)){
			this.setMainMessage("��û���ϴ��ʱ��������������ϱ���");
			return EventRtnType.FAILD;
		}else if("noPersonAtta".equals(zipPath.split("@")[0])){
			this.setMainMessage(zipPath.split("@")[1]+"��û���ϴ��κθ����������ϱ���");
			return EventRtnType.FAILD;
		}
		String zipPath1 = zipPath.split("@")[2];
		Map<String, String> map = new HashMap<String, String>();
		
		HBSession sess = HBUtil.getHBSession();
		try {
			//��ȡ��¼�û�����Ϣ
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 where b0111 ='"+user.getOtherinfo()+"' ").list();
			B01 b01 = null;
			if (b01s != null && b01s.size() > 0) {
				b01 = b01s.get(0);
			} else {
				this.setMainMessage("ϵͳ��������ϵ����Ա��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String searchDeptid = b01.getB0111();
			//��ȡҳ����ftp��Ϣ
			String ftpid = this.getPageElement("ftpid").getValue();
			//��ȡҳ������ϵ����Ϣ
			String linkpsn = this.getPageElement("linkpsn").getValue();
			//��ȡҳ������ϵ�˵绰��Ϣ
			String linktel = this.getPageElement("linktel").getValue();
			//��ȡҳ���б�ע��Ϣ
			String remark = this.getPageElement("remark").getValue();

			int count = 1;
			
			//����ʱ�����
			java.sql.Timestamp now = DateUtil.getTimestamp();
			String time = DateUtil.timeToString(now);
			String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
			//����ftp�ϱ���Ϣxml����
			ZwhzPackDefine info = new ZwhzPackDefine();
			
			//String sid = UUID.randomUUID().toString().replace("-", "");
			info.setId(cbdid);
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
			info.setTranstype("up");
			info.setErrortype("��");
			info.setErrorinfo("��");
			info.setCbdStatus("0");//���óʱ���״̬  0��δ����
			info.setPersonid(personid);
			info.setPersonname(personname);
			
			List<SFileDefine> sfile = new ArrayList<SFileDefine>();
			//����xml�ļ���
			String packageFile = "Pack_" +zipPath.split("@")[3] + ".xml";
			
			for (int i = 1; i <= count; i++) {
				
				SFileDefine sf = new SFileDefine();

				map.put("type", "11");
				map.put("time", time);
				map.put("dataversion", "20121221");
				map.put("photodir", "Photos");
				map.put("B0101", b01.getB0101());
				map.put("B0111", b01.getB0111());
				map.put("B0114", b01.getB0114());
				map.put("B0194", b01.getB0194());
				map.put("linkpsn", linkpsn);
				map.put("linktel", linktel);
				map.put("remark", remark);
				sf.setTime(time);

				String number = ("000" + i).substring(("000" + i).length() - 3);
				//Ҫ�ϱ����ļ�
				String zipfile =zipPath.split("@")[0];
				
				//SevenZipUtil.zip7z(zippath, zipfile, "1234");
				File file0 = new File(zipfile);
				sf.setName(file0.getName());
				// InputStream f1 = new FileInputStream(zipfile);
				// int size = f1.available();
				sf.setSize(getFileSize(file0));
				sfile.add(sf);
			}
			//��¼�ļ���Ϣ
			info.setDatainfo("�ʱ�������1�����ļ���"+ zipPath.split("@")[1] + "����");
			
			info.setSfile(sfile);
			
			CommonQueryBS.systemOut(JXUtil.Object2Xml(info, true));
			//����xml�ļ�
			FileUtil.createFile(zipPath1 + "/" + packageFile,
					JXUtil.Object2Xml(info, true), false, "UTF-8");
			//�ж��Ƿ���ѡ��ftp��ַ
			if (StringUtil.isEmpty(ftpid)) {
				throw new AppException("�ϱ���λΪ�գ�������ѡ��");
			}
			//����ftp���Ӷ���
			TransConfig jfcc = (TransConfig) HBUtil.getHBSession().get(
					TransConfig.class, ftpid);

			Reportftp rfpt = new Reportftp();
			rfpt.setFilename("");
			rfpt.setPackageindex(cbdid);
			rfpt.setPackagename(packageFile);
			rfpt.setRecieveftpuserid(jfcc.getId());
			rfpt.setRecieveftpusername(jfcc.getName());
			rfpt.setReporttime(DateUtil.getTimestamp());
			rfpt.setB0111(b01.getB0111());
			rfpt.setReporttype("1");
			rfpt.setReportuser(SysUtil.getCacheCurrentUser().getId());
			rfpt.setReportusername(SysUtil.getCacheCurrentUser().getName());
			
			//��ʼ�ϱ�
			try {
				sess.beginTransaction();
				sess.save(rfpt);
				
				//��¼�ʱ�������
				String uuid = UUID.randomUUID().toString();
				//��ȡʱ��
				SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
				String CBD_STATUS_TIME = sdf.format(new Date());
				Cbdstatus cbdstatus = new Cbdstatus();
				cbdstatus.setStatusid(uuid);
				cbdstatus.setCbdstatusstep("5");
				cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
				cbdstatus.setCbdid(bj_cbdid);
				sess.save(cbdstatus);
				
				//�ϱ�����
				ZwhzFtpClient.uploadCBD(jfcc, zipPath1 + "/" + packageFile);
				sess.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				sess.getTransaction().rollback();
				throw new AppException(e.getMessage());

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("�ϱ��쳣���쳣��Ϣ��" + e.getMessage());

		}
		this.setMainMessage("�ʱ����ϱ��ɹ�");
		this.getExecuteSG().addExecuteCode("parent.setNextStep('5')");
		this.closeCueWindow("ReportCBD");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ���߷�������ȡ·��
	 * @return
	 */
	private String getPath() {
		// TODO Auto-generated method stub
		String classPath = getClass().getClassLoader().getResource("/")
				.getPath();
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath = "";

		// windows��
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1,
					classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux��
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0,
					classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}
	
	public static Long getFileSize(File f) {
		FileChannel fc = null;
		try {
			if (f.exists() && f.isFile()) {
				FileInputStream fis = new FileInputStream(f);
				fc = fis.getChannel();
				return fc.size();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fc) {
				try {
					fc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return 0L;
	}
	
	/**
	 *  ����ʱ�����صĸ�������ѹ������ѹ����·����Ϊ����ֵ
	 * @return
	 */
	public String collectAttachment(String cbd_id,String cbd_name){
		
		//��ȡ������·��
		String path = getPath();
		//ѹ��·��
		String zipPath = path +"CBDUpload/CBDZip";
		//����vector��������Ÿ���·����Ϣ
		Vector vector = new Vector();
		//ִ�����ݿ����
		HBSession sess = HBUtil.getHBSession();
		//��ѯ�ʱ�����Ӧ�ĸ�����Ϣ
		String hql = "from AttachmentInfo ai where ai.objectid = '"+cbd_id+"'";
		List<AttachmentInfo> attach_list =  sess.createQuery(hql).list();
		if(attach_list.size()<=0){
			return "noCBDAtta";
		}
		//��ȡ�ʱ�������
		String name = attach_list.get(0).getFilename();
		String cbdAttachPath = attach_list.get(0).getFilepath();
		
		//��ѯ�ʱ�����Ϣ
		hql = "from CBDInfo ci where ci.cbd_id = '"+cbd_id+"'";
		CBDInfo cbdinfo = (CBDInfo) sess.createQuery(hql).list().get(0);
		//��ȡ�ʱ�����Ӧ��Ա���
		String personid = cbdinfo.getCbd_personid();
		//��ȡ��Ա���
		String[] personids = personid.split(",");
		String[] personnames = cbdinfo.getCbd_personname().split(",");
		
		//ѭ����ѯ��Ա�ĸ�����Ϣ
		for(int i =0;i<personids.length;i++){
			hql = "from AttachmentInfo ai where ai.personid = '"+personids[i]+"'";
			List<AttachmentInfo> atta = sess.createQuery(hql).list();
			//�ж��Ƿ�����Ա�ĸ�����Ϣ���������Աû���ϴ��������������ϱ�
			if(atta.size()<=0){
				//return "noPersonAtta@"+personnames[i];
				continue;
			}
			vector.add(atta.get(0).getPersonname()+atta.get(0).getIdcard());
		}
		
		//����ʱ�����
		java.sql.Timestamp now = DateUtil.getTimestamp();
		String time = DateUtil.timeToString(now);
		String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
		
		//ѹ��·����ӳʱ������Ƶ��ļ��У����Դ�ųʱ�����ص����и���
		zipPath = zipPath+"/"+cbd_name+time1;
		//�ж�Ŀ¼�Ƿ���ڣ�����������򴴽�Ŀ¼
		try {
			File file = new File(zipPath);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//��ȡ�ʱ��������ĺ��
		String houzhui = cbdAttachPath.substring(cbdAttachPath.lastIndexOf("."), cbdAttachPath.length());
		//���ʱ�������������ѹ��·����
		try {
			copyFile(new File(path+cbdAttachPath),new File(zipPath+"/"+name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//������Ա���ļ���
		for(int i=0;i<vector.size();i++){
			try {
				copyDirectiory(path+"CBDUpload/DJBOrOtherAttachment/"+vector.get(i),zipPath+"/"+vector.get(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//ѹ���ļ�·��
		String zip = zipPath+"/"+cbd_name+"_"+time1+".zip";
		//���ļ���ѹ����zipѹ������
		SevenZipUtil.zip7z(zipPath, zip, null);
		//ɾ��ԭ�ļ����ļ���
		File file_oldcbd = new File(zipPath+"/"+name);
		file_oldcbd.getAbsoluteFile();
		file_oldcbd.delete();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//������Ա���ļ���
		for(int i=0;i<vector.size();i++){
			try {
				delDirectiory(zipPath+"/"+vector.get(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.gc();
		
		//����ʱ���ѹ���ļ���Ϣ
		String uuid =  UUID.randomUUID().toString();//��ȡ���ݱ������ֵ
		AttachmentInfo atta= new AttachmentInfo();
		atta.setId(uuid);
		atta.setObjectid(cbd_id+"u");//�ʱ�����������ӱ�־����u������ʶ������¼Ϊ�ʱ�������ѹ����
		atta.setUploaddate(time);
		atta.setFilepath("CBDUpload/CBDZip"+"/"+name+time1+"/"+cbd_name+"_"+time1+".zip");
		atta.setFiletype("2");
		atta.setFilename(cbd_name);
		
		//��ȡ��¼�û�����Ϣ
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		atta.setUserid(user.getId());
		atta.setUsername(user.getName());
		sess.saveOrUpdate(atta);
		
		return zip+"@"+personids.length+"@"+zipPath+"@"+cbd_name+"_"+time1;
	}
	
	/**
	 * �����ļ�
	 * @param sourcefile Դ�ļ�·��
	 * @param targetFile  Ŀ���ļ�·��
	 * @throws IOException
	 */
	public static void copyFile(File sourcefile, File targetFile)throws IOException {
		// �½��ļ����������������л���
		FileInputStream input = new FileInputStream(sourcefile);
		BufferedInputStream inbuff = new BufferedInputStream(input);
		// �½��ļ���������������л���
		FileOutputStream out = new FileOutputStream(targetFile);
		BufferedOutputStream outbuff = new BufferedOutputStream(out);
		
		// ��������
		byte[] b = new byte[1024 * 5];
		int len = 0;
		while ((len = inbuff.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}
		// ˢ�´˻���������
		outbuff.flush();
		// �ر���
		inbuff.close();
		outbuff.close();
		out.close();
		input.close();
		
	}
	
	/**
	 * ���������ļ���
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static void copyDirectiory(String sourceDir, String targetDir)
	throws IOException {
		// �½�Ŀ��Ŀ¼
		(new File(targetDir)).mkdirs();
		// ��ȡԴ�ļ��е��µ��ļ���Ŀ¼
		File[] file = (new File(sourceDir)).listFiles();
		
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// Դ�ļ�
				File sourceFile = file[i];
				// Ŀ���ļ�
				File targetFile = new File(new File(targetDir)
						.getAbsolutePath()
						+ File.separator + file[i].getName());
		
				copyFile(sourceFile, targetFile);
		
			}
			if (file[i].isDirectory()) {
				// ׼�����Ƶ�Դ�ļ���
				String dir1 = sourceDir + file[i].getName();
				// ׼�����Ƶ�Ŀ���ļ���
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
		
	}
	
	/**
	 * ɾ�������ļ���
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static void delDirectiory(String sourceDir)
	throws IOException {
		// �½�Ŀ��Ŀ¼
		// ��ȡԴ�ļ��е��µ��ļ���Ŀ¼
		File[] file = (new File(sourceDir)).listFiles();
		
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// Դ�ļ�
				File sourceFile = file[i];
				sourceFile.getAbsoluteFile();
				sourceFile.delete();
		
			}
			if (file[i].isDirectory()) {
				// ׼�����Ƶ�Դ�ļ���
				String dir1 = sourceDir + file[i].getName();
				delDirectiory(dir1);
			}
		}
		File file_d = new File(sourceDir);
		file_d.getAbsoluteFile();
		file_d.delete();
	}
			
	
}
