package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class ImpZzbHzbThread implements Runnable {
	
	private String uuid;
	private String filename;
	private CurrentUser user;
	private UserVO userVo;
    public ImpZzbHzbThread(String filename, String uuid, CurrentUser user,UserVO userVo) {
        this.uuid = uuid;
        this.filename = filename;
        this.user = user;
        this.userVo = userVo;
    }

	@Override
	public void run() {
		String rootPath = "";									// ��Ŀ·��
		String imprecordid = uuid;								// �����¼id
		String process_run = "1";								// ����������
		UploadHzbFileBS uploadbs = new UploadHzbFileBS();		// ҵ����bs
		HBSession sess = HBUtil.getHBSession();
		String filePath ="";									// �ϴ��ļ�����·��
		String unzip = "";										// ��ѹ·��
		String upload_file = "";
		try {
			// ��¼��־�ļ�
			String logfilename = getRootPath() + "upload/" +DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".txt";
			File logfile = new File(logfilename);
			if(!logfile.exists()){
				logfile.createNewFile();
			}
			appendFileContent(logfilename, "��ʼ����:"+ DateUtil.getTime()+"\n");
			// 001==============�����ļ� ��׺  ��ʽ  �ϴ�·��  ��ѹ·��==============================================================
			String classPath = getClass().getClassLoader().getResource("/").getPath();				// class ·��
			if ("\\".equals(File.separator)) {														// windows��
				rootPath = classPath.substring(1, classPath.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {														// linux��
				rootPath = classPath.substring(0, classPath.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			upload_file = rootPath + "upload/" + uuid + "/";									    // �ϴ�·��
			unzip = rootPath + "upload/unzip/" + uuid + "/";										// ��ѹ·��
			File file = new File(unzip);															// ����ļ��в������򴴽�
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
			String houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());  //�ļ���׺
			filePath = upload_file + "/" + uuid + "." +houzhui;									    //�ϴ��ļ�����·��
			String from_file = unzip + "Photos/";													//��ѹ��ͼƬ���·��
			File f_file = new File(from_file);
			if (!f_file.exists() && !f_file.isDirectory()) {
				file.mkdirs();
			}
			
			// 002================  �ļ���ѹ   =========================================================================
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			appendFileContent(logfilename, "��ѹ����ʼ:"+ DateUtil.getTime()+"\n");					//��¼��־
			if (houzhui.equalsIgnoreCase("hzb")) {
				NewSevenZipUtil.extractilenew(filePath, unzip, "1");
			} else {
				NewSevenZipUtil.extractilenew(filePath, unzip, "2");
			}
			appendFileContent(logfilename, "��ѹ������:"+ DateUtil.getTime()+"\n");					//��¼��־
			KingbsconfigBS.saveImpDetail("1","2","���",imprecordid);								//��¼�������
			process_run = "2";																		//�������
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			
			//002================  ����ͷ�ļ�   =========================================================================
			KingbsconfigBS.saveImpDetail(process_run,"1","������",imprecordid);						//��¼�������
			List<Map<String, String>> headlist = Dom4jUtil
					.gwyinfoF(unzip + "" + "gwyinfo.xml");
			List<B01> grps = HBUtil.getHBSession().createQuery(
					"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
							+ user.getId() + "')").list();
			B01 gr = null;
			if (grps != null && grps.size() > 0) {
				gr = grps.get(0);
			}
			List<B01> detps = null;
			String B0111 = headlist.get(0).get("B0111");							// ���ڵ��ϼ�����id
			String deptid = "";														// ���ڵ��ϼ�����id
			String impdeptid = "";													//���ڵ����id
			detps = HBUtil.getHBSession().createQuery("from B01 t where t.b0101='" + headlist.get(0).get("B0101")
					+ "' and t.b0114='" + headlist.get(0).get("B0114") + "'").list();
			if (detps != null && detps.size() > 0) {
				impdeptid = detps.get(0).getB0111();
				deptid = detps.get(0).getB0121();
			} else {
				throw new Exception("δƥ�䵽��������");
			}
			List<Imprecord> imprecords = Map2Temp.toTemp("Imprecord", headlist);
			if (imprecords != null && imprecords.size() > 0) {
				Imprecord imprecord = imprecords.get(0);
				imprecord.setImptime(DateUtil.getTimestamp());
				imprecord.setImpuserid(user.getId());
				if (gr != null) {
					imprecord.setImpgroupid(gr.getB0111());
					imprecord.setImpgroupname(gr.getB0101());
				}
				imprecord.setIsvirety("0");
				imprecord.setFilename(filename);
				imprecord.setFiletype(houzhui);
				imprecord.setImptype(houzhui.equalsIgnoreCase("hzb")? "1" : "2");
				imprecord.setEmpdeptid(headlist.get(0).get("B0111"));
				imprecord.setEmpdeptname(headlist.get(0).get("B0101"));
				imprecord.setImpdeptid(impdeptid);
				imprecord.setImpstutas("1");
				imprecord.setPsncount((headlist.get(0).get("psncount")!=null&& !headlist.get(0).get("psncount").equals(""))?Long.parseLong(headlist.get(0).get("psncount")):0L);
				imprecord.setLinkpsn(headlist.get(0).get("linkpsn"));
				imprecord.setLinktel(headlist.get(0).get("linktel"));
				imprecord.setImprecordid(uuid);
				imprecord.setProcessstatus("1");
				sess.update(imprecord);
				KingbsconfigBS.saveImpDetail(process_run,"2","���",imprecordid);				//��¼�������
				process_run = "3";																//�������
				int t_n = 0;
				//==========  ���������ļ����������ݿ�   =================================================================================
				int number1 = 1;																//�ѽ��������ľ
				int number2 = 0;
				if(houzhui.equalsIgnoreCase("hzb")){
					number2 = 20;																//δ���������ľ
				}else{
					number2 = 15;
				}
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A02���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				//NO.006.002 ����A02��
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A02���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("NO.006.002 A02���ݵ���"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A02", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("NO.006.002 A02���ݵ���"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
				
				appendFileContent(logfilename, "A02���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A06���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				//NO.006.003 ����A06��
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A06���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a06����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A06", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a06����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				appendFileContent(logfilename, "A06���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A08���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A08���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a08����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A08", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a08����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				appendFileContent(logfilename, "A08���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A11���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A11���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a11����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A11", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a11����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				appendFileContent(logfilename, "A11���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A14���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A14���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a14����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A14", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a14����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				appendFileContent(logfilename, "A14���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A15���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A15���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a15����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A15", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a15����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				appendFileContent(logfilename, "A15���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A29���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A29���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a29����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A29", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a29����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				appendFileContent(logfilename, "A29���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A30���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A30���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a30����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A30", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a30����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				appendFileContent(logfilename, "A30���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A31���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A31���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a31����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A31", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a31����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				appendFileContent(logfilename, "A31���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A36���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A36���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a36����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A36", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a36����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				appendFileContent(logfilename, "A36���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A37���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A37���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a37����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A37", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a37����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				appendFileContent(logfilename, "A37���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A41���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A41���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a41����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A41", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a41����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				appendFileContent(logfilename, "A41���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A53���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A53���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a53����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A53", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("aa53����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			 
				appendFileContent(logfilename, "A53���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A57���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A57���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a57����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A57", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a57����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------				
				appendFileContent(logfilename, "A57���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				if(houzhui.equalsIgnoreCase("hzb")){	//���ܰ�������--��������
					
					appendFileContent(logfilename, "a60���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
					KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A60���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
					CommonQueryBS.systemOut("a60����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A60", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("a60����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
				 
					appendFileContent(logfilename, "A60���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					appendFileContent(logfilename, "==============================================="+"\n");
					appendFileContent(logfilename, "A61���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					
					
					
					KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A61���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
					CommonQueryBS.systemOut("a61����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A61", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("a61����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
				 
					appendFileContent(logfilename, "A61���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					appendFileContent(logfilename, "==============================================="+"\n");
					appendFileContent(logfilename, "A62���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					
					
					KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A62���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
					CommonQueryBS.systemOut("a62����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A62", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("a62����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
				 
					appendFileContent(logfilename, "A62���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					appendFileContent(logfilename, "==============================================="+"\n");
					appendFileContent(logfilename, "A63���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					
					
					KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A63���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
					CommonQueryBS.systemOut("a63����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A63", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("a63����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
				 
					appendFileContent(logfilename, "A63���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					appendFileContent(logfilename, "==============================================="+"\n");
					appendFileContent(logfilename, "A64���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					
					
					KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A64���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
					CommonQueryBS.systemOut("a64����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A64", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("a64����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
					
					appendFileContent(logfilename, "A64���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					appendFileContent(logfilename, "==============================================="+"\n");
				}
				appendFileContent(logfilename, "B01���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------				
				//NO.006.001 ����B01��
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�B01���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("NO.006.001 B01���ݵ���"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "B01", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("NO.006.001 B01���ݵ���"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");

				appendFileContent(logfilename, "B01���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A01���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�A01���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
				CommonQueryBS.systemOut("a01����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A01", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a01����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
			
				appendFileContent(logfilename, "A01���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				if(houzhui.equalsIgnoreCase("hzb")){
					KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ������Ϣ��",imprecordid);
					CommonQueryBS.systemOut("������Ϣ"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "INFO_EXTEND", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("������Ϣ"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "B01_EXT", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("������Ϣ"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
					
				}
				imprecord.setTotalnumber(t_n + "");
				imprecord.setProcessstatus("2");
				sess.update(imprecord);
			}
			CommonQueryBS.systemOut("END INSERT---------" +DateUtil.getTime());
			sess.flush();
			appendFileContent(logfilename, "�������"+"\n");
			/**/
			KingbsconfigBS.saveImpDetail(process_run, "2", "��ȡ���", imprecordid);
			try {
				if (houzhui.equalsIgnoreCase("hzb")) {
					new LogUtil("421", "IMP_RECORD", "", "", "������ʱ��", new ArrayList(),userVo).start();
				} else {
					new LogUtil("422", "IMP_RECORD", "", "", "������ʱ��", new ArrayList(),userVo).start();
				}
			} catch (Exception e) {
				try {
					if (houzhui.equalsIgnoreCase("hzb")) {
						new LogUtil().createLog("421", "IMP_RECORD", "", "", "������ʱ��", new ArrayList());
					} else {
						new LogUtil().createLog("422", "IMP_RECORD", "", "", "������ʱ��", new ArrayList());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			try {
				this.delFolder(unzip+"Table/");
				this.delFolder(unzip+"gwyinfo.xml");
				this.delFolder(filePath);
				this.delFolder(upload_file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			appendFileContent(logfilename, "ɾ�������ļ�"+ DateUtil.getTime()+"\n");
			CommonQueryBS.systemOut("delete file END---------" +DateUtil.getTime());
		} catch (AppException e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","ʧ��:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//������ʾ��Ϣ�� 
			uploadbs.rollbackImp(imprecordid);
			if(sess != null)
				sess.getTransaction().rollback();
			this.delFolder(unzip);
			this.delFolder(filePath);
			this.delFolder(upload_file);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","ʧ��:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//������ʾ��Ϣ�� 
			uploadbs.rollbackImp(imprecordid);
			this.delFolder(unzip);
			this.delFolder(filePath);
			this.delFolder(upload_file);
			if(sess != null)
				sess.getTransaction().rollback();
			e.printStackTrace();
		}

	}
	
	private String getRootPath() {
		String classPath = this.getClass().getClassLoader().getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath  = ""; 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		return rootPath;
	}
	public static void appendFileContent(String fileName, String content) {
        try {
            //��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
           FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	// ɾ���ļ���
	// param folderPath �ļ�����������·��
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ɾ��ָ���ļ����������ļ�
	// param path �ļ�����������·��
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
				flag = true;
			}
		}
		return flag;
	}
}
