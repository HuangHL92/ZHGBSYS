package com.insigma.siis.local.pagemodel.repandrec.plat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
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
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Reportftp;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzFtpClient;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class ModelRepPageModel extends PageModel {
	
	@PageEvent("reppackagebtn.onclick")
	public int reppackagebtn(String name) throws RadowException{
//		String tabimp = this.getPageElement("tabimp").getValue();
		Map<String, String> map = new HashMap<String, String>();
		String localf = AppConfig.LOCAL_FILE_BASEURL;
		HBSession sess = HBUtil.getHBSession();
		String infile = "";
		int packcount = 15000;
		try {
			List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 where b0121 ='-1' ").list();
			B01 b01 =  null;
			if(b01s != null && b01s.size()>0){
				b01 = b01s.get(0);
			} else {
				this.setMainMessage("ϵͳ��������ϵ����Ա��");
				return EventRtnType.NORMAL_SUCCESS;
			} 
			String searchDeptid = b01.getB0111();
			String ftpid = this.getPageElement("ftpid").getValue();
			String linkpsn = this.getPageElement("linkpsn").getValue();
			String linktel = this.getPageElement("linktel").getValue();
			String remark = this.getPageElement("remark").getValue();
			
			String gjgs = this.getPageElement("gjgs").getValue();
			if (gjgs!=null && gjgs.equals("1")) {
				
			} else {
				this.setMainMessage("ֻ֧��HZB��ʽ�ϱ���");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//------------      -------------//
			StringBuilder b = new StringBuilder();
			b.append("select a0000,rownum rn from(select distinct a1.a0000 a0000 from a01 a1,a02 a2 where a1.a0000=a2.a0000(+)");
			
			
//			b.append(" and a2.a0201b in (select b.b0111 from b01 b start with b0111='" + searchDeptid +"' connect by prior b0111=b0121)");
			b.append(")");
			Object obj = HBUtil.getHBSession().createSQLQuery("select count(a0000) from (" + b.toString() + ")").uniqueResult();
			int count = 1;
			
			String path = getPath();
			java.sql.Timestamp now = DateUtil.getTimestamp();
			String time = DateUtil.timeToString(now);
			String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
			ZwhzPackDefine info = new ZwhzPackDefine();
			String sid = UUID.randomUUID().toString().replace("-", "");
			info.setId(sid);
			info.setB0101(b01.getB0101());
			info.setB0111(b01.getB0111());
			info.setB0114(b01.getB0114());
			info.setB0194(b01.getB0194());
			info.setDatainfo("������λ��Ϣ������3������Ա������Ϣ��2����");
			info.setDataversion(DateUtil.dateToString(DateUtil.getSysDate(), "yyyyMMdd"));
			info.setLinkpsn(linkpsn);
			info.setLinktel(linktel);
			if(obj!=null){
				count = ((BigDecimal)obj).intValue()/packcount + (((BigDecimal)obj).intValue()%packcount!=0? 1 : 0);
				info.setPersoncount(Long.valueOf(((BigDecimal)obj).intValue()));
			}
			info.setRemark(remark);
			info.setStype("1");
			info.setStypename("��ģ�͵���");
			info.setTime(time);
			info.setTranstype("up");
			info.setErrortype("��");
			info.setErrorinfo("��");
			Map downf = new HashMap();
			List<SFileDefine> sfile = new ArrayList<SFileDefine>();
			String packageFile = "Pack_�����������ļ�_" +b01.getB0114()+"_" +b01.getB0101()+"_" + time1 +".xml";
			for (int i = 1; i <= count; i++) {
				SFileDefine sf = new SFileDefine();
				StringBuilder a01sql = new StringBuilder();
				a01sql.append(" select a0000 from(");
				a01sql.append(b);
				a01sql.append(") where rn >=" + ((i-1)*packcount + 1));
				a01sql.append(" and rn <=" + (i*packcount));
				List<B01> list16 = null;
				if(i==1){
					list16 = HBUtil.getHBSession().createSQLQuery("select * from b01 start with b0111='" + searchDeptid +"' connect by prior b0111=b0121").addEntity(B01.class).list();
					info.setOrgcount(Long.valueOf(list16.size()));
				}
				
				List<A01> list = HBUtil.getHBSession().createSQLQuery("select * from a01 where a0000 in(" + a01sql.toString() + ")").addEntity(A01.class).list();
				List<A02> list2 = HBUtil.getHBSession().createSQLQuery("select * from A02 where a0000 in(" + a01sql.toString() + ")").addEntity(A02.class).list();
				List<A06> list3 = HBUtil.getHBSession().createSQLQuery("select * from A06 where a0000 in(" + a01sql.toString() + ")").addEntity(A06.class).list();
				List<A08> list4 = HBUtil.getHBSession().createSQLQuery("select * from A08 where a0000 in(" + a01sql.toString() + ")").addEntity(A08.class).list();
				List<A11> list5 = HBUtil.getHBSession().createSQLQuery("select * from A11 where a0000 in(" + a01sql.toString() + ")").addEntity(A11.class).list();
				
				List<A14> list6 = HBUtil.getHBSession().createSQLQuery("select * from A14 where a0000 in(" + a01sql.toString() + ")").addEntity(A14.class).list();
				List<A15> list7 = HBUtil.getHBSession().createSQLQuery("select * from A15 where a0000 in(" + a01sql.toString() + ")").addEntity(A15.class).list();
				List<A29> list8 = HBUtil.getHBSession().createSQLQuery("select * from A29 where a0000 in(" + a01sql.toString() + ")").addEntity(A29.class).list();
				List<A30> list9 = HBUtil.getHBSession().createSQLQuery("select * from A30 where a0000 in(" + a01sql.toString() + ")").addEntity(A30.class).list();
				List<A31> list10 = HBUtil.getHBSession().createSQLQuery("select * from A31 where a0000 in(" + a01sql.toString() + ")").addEntity(A31.class).list();
				
				List<A36> list11 = HBUtil.getHBSession().createSQLQuery("select * from A36 where a0000 in(" + a01sql.toString() + ")").addEntity(A36.class).list();
				List<A37> list12 = HBUtil.getHBSession().createSQLQuery("select * from A37 where a0000 in(" + a01sql.toString() + ")").addEntity(A37.class).list();
				List<A41> list13 = HBUtil.getHBSession().createSQLQuery("select * from A41 where a0000 in(" + a01sql.toString() + ")").addEntity(A41.class).list();
				List<A53> list14 = HBUtil.getHBSession().createSQLQuery("select * from A53 where a0000 in(" + a01sql.toString() + ")").addEntity(A53.class).list();
				List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000 in(" + a01sql.toString() + ")").addEntity(A57.class).list();
				List<Map> list17 = new ArrayList<Map>();
				map.put("type", "2");
				map.put("time", time);
				//     ??
				map.put("dataversion", "20121221");
				map.put("psncount", (list!=null&&list.size()>0)? (list.size()+"") :"" );
				//
				map.put("photodir", "Photos");
				map.put("B0101", b01.getB0101());
				map.put("B0111", b01.getB0111());
				map.put("B0114", b01.getB0114());
				map.put("B0194", b01.getB0194());
				map.put("linkpsn", linkpsn);
				map.put("linktel", linktel);
				map.put("remark", remark);
				sf.setTime(time);
				sf.setOrgrows((list16 != null && list16.size()>0)?(list16.size()) :0L);
				sf.setPersonrows((list != null && list.size()>0)?(list.size()) :0L);
				
				list17.add(map);
//				String path = getPath();
				String number = ("000" +i).substring(("000" +i).length()-3);
				String zippath = path + "�����������ļ�_" +b01.getB0111()+"_" +b01.getB0101()+"_" + time1 +"/" + number+"/";
				File file =new File(zippath);    
				//����ļ��в������򴴽�    
				if  (!file .exists()  && !file .isDirectory())      
				{       
				    file .mkdirs();    
				}
				String zippathtable = path + "�����������ļ�_" +b01.getB0111()+"_" +b01.getB0101()+"_" + time1 +"/" + number +"/Table/";
				File file1 =new File(zippathtable);    
				//����ļ��в������򴴽�    
				if  (!file1 .exists()  && !file1 .isDirectory())      
				{       
				    file1 .mkdirs();    
				}
				String zipfile = localf + "/" + "�����������ļ�_" +b01.getB0114()+"_" +b01.getB0101()+"_" + time1+"_" + number +".7z";
				if (gjgs!=null && gjgs.equals("1")) {
					zipfile = localf + "/" + "�����������ļ�_" +b01.getB0114()+"_" +b01.getB0101()+"_" + time1+"_" + number +".hzb";
					Xml4HZBUtil.List2Xml(list, "A01", zippath);
					Xml4HZBUtil.List2Xml(list2, "A02", zippath);
					Xml4HZBUtil.List2Xml(list3, "A06", zippath);
					Xml4HZBUtil.List2Xml(list4, "A08", zippath);
					Xml4HZBUtil.List2Xml(list5, "A11", zippath);
					
					Xml4HZBUtil.List2Xml(list6, "A14", zippath);
					Xml4HZBUtil.List2Xml(list7, "A15", zippath);
					Xml4HZBUtil.List2Xml(list8, "A29", zippath);
					Xml4HZBUtil.List2Xml(list9, "A30", zippath);
					Xml4HZBUtil.List2Xml(list10, "A31", zippath);
					
					Xml4HZBUtil.List2Xml(list11, "A36", zippath);
					Xml4HZBUtil.List2Xml(list12, "A37", zippath);
					Xml4HZBUtil.List2Xml(list13, "A41", zippath);
					Xml4HZBUtil.List2Xml(list14, "A53", zippath);
					Xml4HZBUtil.List2Xml(list15, "A57", zippath);
					
					Xml4HZBUtil.List2Xml(list16, "B01", zippath);
					Xml4HZBUtil.List2Xml(list17, "info", zippath);
				}
				if(list15 != null && list15.size()>0){
					String photopath = zippath + "Photos/";
					File file2 =new File(photopath);    
					//����ļ��в������򴴽�    
					if  (!file2 .exists()  && !file2 .isDirectory())      
					{       
						file2 .mkdirs();    
					}
					for (int j = 0; j < list15.size(); j++) {
						A57 a57 = list15.get(j);
						if(a57.getPhotoname()!=null && !a57.getPhotoname().equals("")){
							File f = new File(photopath + a57.getA0000() +".jpg");
							FileOutputStream fos = new FileOutputStream(f);
							InputStream is = a57.getPhotodata().getBinaryStream();// �������ݺ�ת��Ϊ��������
							byte[] data = new byte[1024];
							while (is.read(data) != -1) {
								fos.write(data);
							}
							fos.close();
							is.close();
						}
					}
				}
				infile = zipfile;
				SevenZipUtil.zip7z(zippath, zipfile, "1234");
//				String cmd="cmd /c \"c:\\Program Files (x86)\\7-Zip\\7z.exe\" a  "+zipfile+" -p1234 -mhe  "+zippath+"\\*";
//				Process p = Runtime.getRuntime().exec(cmd);
//				p.waitFor();
				File file0 = new File(zipfile);
				sf.setName(file0.getName());
//				InputStream f1 = new FileInputStream(zipfile);
//		        int size = f1.available();
				sf.setSize(DataOrgRepPageModel.getFileSize(file0));
				sfile.add(sf);
				this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
				downf.put("file" + i, infile.replace("\\", "/"));
			}
//			this.getExecuteSG().addExecuteCode("window.reloadTree('"+(net.sf.json.JSONArray.fromObject(downf).toString())+"')");
			info.setSfile(sfile);
			CommonQueryBS.systemOut(JXUtil.Object2Xml(info,true));
			FileUtil.createFile(localf + "/"+ packageFile, JXUtil.Object2Xml(info,true), false, "UTF-8");
			TransConfig jfcc = (TransConfig) HBUtil.getHBSession().get(TransConfig.class, ftpid);
//			ZwhzFtpClient.uploadHzb(jfcc, localf + "/"+  packageFile);
//			UploadHelpFileServlet.delFolder(path);
			Reportftp rfpt = new Reportftp();
			rfpt.setFilename("");
			rfpt.setPackageindex(sid);
			rfpt.setPackagename(packageFile);
//			rfpt.setPackageret("");
			rfpt.setRecieveftpuserid(jfcc.getId());
			rfpt.setRecieveftpusername(jfcc.getName());
//			rfpt.setRecieveorg(recieveorg);
//			rfpt.setRecieveorgname(recieveorgname);
			rfpt.setReporttime(DateUtil.getTimestamp());
			rfpt.setB0111(b01.getB0111());
			rfpt.setReporttype("2");
//			String userid = SysUtil.getCacheCurrentUser().getId();
			rfpt.setReportuser(SysUtil.getCacheCurrentUser().getId());
			rfpt.setReportusername(SysUtil.getCacheCurrentUser().getName());
			sess.beginTransaction();
			sess.save(rfpt);
			sess.getTransaction().commit();
			ZwhzFtpClient.uploadHzb(jfcc, localf + "/"+  packageFile);
//			UploadHelpFileServlet.delFolder(path);
//			Xml4HZBUtil.List2Xml(files, packageFile, path, dataMap);
			this.createPageElement("MGrid", "grid", true).reload();
			this.closeCueWindow("dataorgwin");
		} catch (Exception e) {
			sess.getTransaction().rollback();
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	private String getPath() {
		// TODO Auto-generated method stub
		String classPath = getClass().getClassLoader().getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath  = ""; 
		
		//windows�� 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		//linux�� 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		//�ϴ�·��
		String upload_file = rootPath + "zipload/";
		try {
			File file =new File(upload_file);    
			//����ļ��в������򴴽�    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		//��ѹ·��
		String zip = upload_file + uuid + "/";
		return zip;
	}
	@PageEvent("reset.onclick")
	@NoRequiredValidate
	public int resetbtn(String name) throws RadowException{
		this.closeCueWindow("modelwin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("searchModelBtn.ontriggerclick")
	@NoRequiredValidate
	public int searchModel(String name) throws RadowException{
		this.openWindow("simpleExpWin", "pages.repandrec.plat.SearchModel");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("searchDeptBtn.ontriggerclick")
	@NoRequiredValidate
	public int searchDept(String name) throws RadowException{
		this.openWindow("deptWin", "pages.repandrec.plat.SearchFtpUp");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return EventRtnType.NORMAL_SUCCESS;
	}

}
