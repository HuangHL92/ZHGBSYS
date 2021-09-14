package com.insigma.siis.local.pagemodel.repandrec.local;

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

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class KingbsZZBFilePageModel extends PageModel {
	
//	@PageEvent("imp3btn")
//	public int imp3btn(String name) throws RadowException{
//		System.out.println("�������ʼ��-----"+ DateUtil.getTime());
//		Map<String, String> map = new HashMap<String, String>();
//		HBSession sess = HBUtil.getHBSession();
//		String path = "D:/KingbsData/unzip";   //�����ļ�·��
//		File sfile = new File(name);   //�����ļ�λ��
//		String filePath = "";
//		String filename = "";
//		int count = 0;//zzb3 �ļ���Ŀ
//		String imprecordid = "";
//		try {
//			String logfilename = getRootPath() + "upload/" +DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".txt";
//			File logfile = new File(logfilename);
//			if(!logfile.exists()){
//				logfile.createNewFile();
//			}
//			appendFileContent(logfilename, "��ʼ����:"+ DateUtil.getTime()+"\n");
////			sess.beginTransaction();
//			if(sfile.exists()){
//				filePath = sfile.getAbsolutePath();
//				filename = sfile.getName();
//				count ++;
//			}
//			if(count==0){
//				this.setMainMessage("ָ��Ŀ¼�²����ڱ����ļ���");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			String zippath = path + "/";
//			//Photos ·��
//			String photo_file = path + "/Photos/";
//			File file =new File(zippath);    
//			//����ļ��в������򴴽�    
//			if  (!file .exists()  && !file .isDirectory()) {       
//			    file .mkdirs();    
//			}
//			System.out.println("zzb3��ѹ����ʼ��ʼ��-----"+ DateUtil.getTime());
//			appendFileContent(logfilename, "��ѹ����ʼ:"+ DateUtil.getTime()+"\n");
//			SevenZipUtil.extractile(filePath, path+"/", "1234");
//			appendFileContent(logfilename, "��ѹ������:"+ DateUtil.getTime()+"\n");
//			System.out.println("zzb3��ѹ��������-----"+ DateUtil.getTime());
//			File datfile = new File(path + "/KDataTmp/GWYDB.dat");   //�����ļ�λ��
//			if(datfile.exists() && datfile.isFile()){
//				appendFileContent(logfilename, "���������ݿ�:"+ DateUtil.getTime()+"\n");
//				KingbsGainBS.cmdImpKingBs(datfile.getAbsoluteFile());
//				appendFileContent(logfilename, "���������ݿ����:"+ DateUtil.getTime()+"\n");
//				System.out.println("���������ݿ�ɹ���---"+ DateUtil.getTime());
//			} else {
//				UploadHelpFileServlet.delFolder(path);
//				this.setMainMessage("�����ļ�����");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			//==========================================================================================================
//			//NO.003 ��ȡ��Ӧ���к͵�������ڵ������Ӧ�Ļ�����Ϣ����Ҫ��impdeptid����Ӧ�� ����Ψһ���룩deptid����Ӧ�� �ϼ�������Ψһ���룩
//			//2016��06��15��   ���� �����룬���ע�� ��
//			//��ȡ���� �˴������ݿ��л�ȡ��������Ϣ(b01) 
//			B01 b01 = KingbsGainBS.getB01byParentid("");
//			if(b01 == null){
//				UploadHelpFileServlet.delFolder(path);          //ɾ�� �˴���zzb3ѹ���� ��Ӧ�÷������� ��ѹ����ļ�Ŀ¼�� 
//				this.setMainMessage("ϵͳ��������ϵ����Ա��");    //������ʾ��Ϣ�� 
//				return EventRtnType.NORMAL_SUCCESS;            //��������
//			}
//			//��ȡ���� ��Ӧ�����ݿ��л�ȡ��������Ϣ(b01) 
//			B01 detp = null;
//			String deptid = "";// ���ڵ��ϼ�����id
//			String impdeptid = "";//���ڵ����id
//			List<B01> detps = HBUtil.getHBSession().createQuery(
//					"from B01 t where t.b0101='" + b01.getB0101()  //�������
//							+ "' and t.b0114='" + b01.getB0114()   //��֯��������
//							+ "'").list();
//			if (detps != null && detps.size() > 0) {
//				detp = detps.get(0);                   //��ȡ��Ӧ���к��˴������ݵĸ�����ͬ��֯�������롢ͬ���ƵĻ�����Ϣ
//				impdeptid = detps.get(0).getB0111();   //��Ӧ�� ����Ψһ����
//				deptid = detps.get(0).getB0121();      //��Ӧ�� �ϼ�������Ψһ���� 
//				detps.removeAll(detps);                //�ͷ� ��Ӧ�û�����Ϣ�б�
//				detps.clear();                         
//				detps = null;
//			} else {
//				UploadHelpFileServlet.delFolder(path);     //ɾ�� �˴���zzb3ѹ���� ��Ӧ�÷������� ��ѹ����ļ�Ŀ¼��
//				this.setMainMessage("δƥ�䵽���������⣡"); //������ʾ��Ϣ��
//				return EventRtnType.NORMAL_SUCCESS;        //��������
//			}
//			detp = null;
//			System.out.println("NO.003 ��⵽��Ӧ���к͵�������ڵ������Ӧ�Ļ�����Ϣimpdeptid����Ӧ�� ����Ψһ���룩��"+impdeptid+"deptid����Ӧ�� �ϼ�������Ψһ���룩:"+deptid+" ���ʱ��"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss"));
//			//==========================================================================================================
//			//NO.004 ��ȡ��ǰִ�е���Ĳ�����Ա����������Ϣ gr��������Ϣ��
//			CurrentUser user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
//			List<B01> grps = HBUtil.getHBSession().createQuery(
//					"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
//							+ user.getId() + "')").list();
//			B01 gr = null;
//			if (grps != null && grps.size() > 0) {
//				gr = grps.get(0);     //��ȡ ��ǰִ�е���Ĳ�����Ա����������Ϣ
//				grps.removeAll(grps); //�ͷ� ��ǰִ�е���Ĳ�����Ա����������Ϣ�б�
//				grps.clear();
//				grps = null;
//			}
//			System.out.println("NO.004 ��ȡ��ǰִ�е���Ĳ�����Ա����������Ϣ gr��������Ϣ������Ψһ���룺"+gr.getB0111()+"��������:"+gr.getB0101()+" ���ʱ��"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss"));
//			//==========================================================================================================
//			//NO.005 ���ε�����̼�¼�뵼���ѯ��¼��imp_record������ȡ imprecordid�������ѯ��¼��Ψһ������
//			Imprecord imprecord = new Imprecord();           //�½� �����ѯ��¼�� ��¼
//			imprecord.setImptime(DateUtil.getTimestamp());     //���� ���뿪ʼʱ�� 
//			imprecord.setImpuserid(user.getId());            //���� ���������Ա���� 
//			if (gr != null) {   
//				imprecord.setImpgroupid(gr.getB0111());      //���� �������Ա������������
//				imprecord.setImpgroupname(gr.getB0101());    //���� �������Ա������������
//			}
//			imprecord.setIsvirety("0");                      //���� ����У���־ ��ʼ��Ϊ0��ʾ û��У�� 
//			imprecord.setFilename(filename);                 //���� ���ε�����ļ����ļ�����
//			imprecord.setFiletype("ZZB3");                   //���� ���ε�����ļ����� 
//			imprecord.setImptype("3");                       //���� �������� 3 ��ʾ ZZB3
//			imprecord.setEmpdeptid(b01.getB0111());          //���� �����ļ��и�������Ψһ����
//			imprecord.setEmpdeptname(b01.getB0101());        //���� �����ļ��и�����������
//			imprecord.setImpdeptid(impdeptid);               //���� ��Ӧ�� ����Ψһ����
//			imprecord.setImpstutas("1");                     //���� ����״̬Ϊ1 ������״̬ 1��δ���롢2���ѵ��롢3����أ�
//			imprecord.setB0114(b01.getB0114());              //���� �����ļ��и��������ڲ�����
//			imprecord.setB0194(b01.getB0194());              //���� �����ļ��и������ķ��˵�λ��ʶ
//			imprecord.setWrongnumber("0");                   //���� У�����ļ�¼����(δʹ��)
//			imprecord.setTotalnumber("0");                   //���� ������ܼ�¼����
//			sess.save(imprecord);                            //���浼���ѯ��¼�� 
//			imprecordid = imprecord.getImprecordid();
//			b01  = null;
//			System.out.println("NO.005 ���ε�����̼�¼�뵼���ѯ��¼��imprecordid�������ѯ��¼��Ψһ��������"+imprecordid+" ���ʱ��"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss"));
//			//==========================================================================================================
//			//NO.006 ���˴������ݿ�ֱ������ε��뱾Ӧ�����ݿ⣬�������ݸ���personsize�趨
//			System.out.println("NO.006 �ܵ������ݿ�ʼ��---"+ DateUtil.getTime());
//			int personcount = 0;                                                     //������������
//			int personsize = 20000;                                                   //ÿ��2000����¼
//			int time = 0;
//			int t_n = 0;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//��������������� 			
//			//NO.006.002 ����A02��
//			personcount = KingbsGainBS.getAllA02Size();                         //���˴������ݿ��ȡA02��¼��
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0); //���㵼�������
//			for (int i = 0; i < time; i++) {
//				appendFileContent(logfilename, "A02���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("NO.006.002 A02���ݵ���"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A02");
//				System.out.println("NO.006.002 A02���ݵ���"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A02���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllB01Size();                          //���˴������ݿ��ȡb01��¼��
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);  //���㵼�������
//			System.out.println("NO.006.001 B01���ݵ��� ����Ҫ����"+personcount+"����¼");
//			for (int i = 0; i < time; i++) {//b01�����������ε��뱾Ӧ�����ݿ����ʱ����
//				appendFileContent(logfilename, "B02���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("NO.006.001 B01���ݵ���"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "B01");
//				System.out.println("NO.006.001 B01���ݵ���"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "B02���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllA06Size();//��ȡa06�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a06ѭ������
//				appendFileContent(logfilename, "A06���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a06����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A06");
//				System.out.println("a06����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A06���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllA08Size();//��ȡa08�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a06ѭ������
//				appendFileContent(logfilename, "A08���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a08����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A08");
//				System.out.println("a08����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A08���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllA11Size();//��ȡa011�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a11ѭ������
//				appendFileContent(logfilename, "A11���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a11����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A11");
//				System.out.println("a11����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A11���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllA14Size();//��ȡa14�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a14ѭ������
//				appendFileContent(logfilename, "A14���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a14����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A14");
//				System.out.println("a14����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A14���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllA15Size();//��ȡa15�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a14ѭ������
//				appendFileContent(logfilename, "A15���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a15����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A15");
//				System.out.println("a145����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A15���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllA29Size();//��ȡa29�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a14ѭ������
//				appendFileContent(logfilename, "A29���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a29����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A29");
//				System.out.println("a29����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A29���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllA30Size();//��ȡa30�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a30ѭ������
//				appendFileContent(logfilename, "A30���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a30����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A30");
//				System.out.println("a30����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A30���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllA31Size();//��ȡa31�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a31ѭ������
//				appendFileContent(logfilename, "A31���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a31����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A31");
//				System.out.println("a31����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A31���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllA36Size();//��ȡa36�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a36ѭ������
//				appendFileContent(logfilename, "A36���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a36����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A36");
//				System.out.println("a36����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A36���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllA37Size();//��ȡa37�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a37ѭ������
//				appendFileContent(logfilename, "A37���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a37����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A37");
//				System.out.println("a37����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A37���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllA41Size();//��ȡa41�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a14ѭ������
//				appendFileContent(logfilename, "A41���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a41����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A41");
//				System.out.println("a41����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A41���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllA53Size();//��ȡa53�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a53ѭ������
//				appendFileContent(logfilename, "A53���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a53����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A53");
//				System.out.println("aa53����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A53���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getAllA57Size();//��ȡa57�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a57ѭ������
//				appendFileContent(logfilename, "A57���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a57����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_A57(i, personsize, imprecord, photo_file, deptid, "A57");
//				System.out.println("a57����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A57���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 ����B01��
//			personcount = KingbsGainBS.getA01Size();//��ȡa01�������
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a01ѭ������
//				appendFileContent(logfilename, "A01���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ"+"\n");
//				System.out.println("a01����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ο�ʼ");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A01");
//				System.out.println("a01����"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���");
//				appendFileContent(logfilename, "A01���ݵ���"+ DateUtil.getTime()+"===>�������ݹ���"+(time)+"�Σ���"+(i+1)+"�ν���"+"\n");
//			}
//			t_n = t_n + personcount;
//			
//			imprecord.setTotalnumber(t_n+"");
//			sess.update(imprecord);
//			appendFileContent(logfilename, "��ȡ���"+"\n");
//			//ϵͳ��־
//			//new LogUtil().createLog("451", "IMP_RECORD", "", "", "������ʱ��", new ArrayList());
//			System.out.println(DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>ɾ�������ļ� ��ʼ");
//			UploadHelpFileServlet.delFolder(path);
//			System.out.println(DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>ɾ�������ļ� ����");
////			sess.getTransaction().commit();
//			appendFileContent(logfilename, "ɾ�������ļ�"+ DateUtil.getTime()+"\n");
//		} catch (Exception e) {
//			UploadHelpFileServlet.delFolder(path);
//			e.printStackTrace();
//			KingbsGainBS.dealRollback(imprecordid);
//			if(sess != null)
//				sess.getTransaction().rollback();
//			this.setMainMessage(e.getMessage());
//			e.printStackTrace();
//		}finally{
//			System.gc();
//		}
//		this.createPageElement("MGrid", ElementType.GRID, true).reload();
//		this.closeCueWindow("win1");
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("zzbsearch");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��ѯ�ļ�
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("zzbsearch")
	public int lfsearch() throws RadowException{
		String ZZB3_FILE = "D:/KingbsData";
		File file = new File(ZZB3_FILE);
		List<HashMap<String, Object>> gridList = new ArrayList<HashMap<String,Object>>();
		Grid grid = (Grid)this.createPageElement("Fgrid", ElementType.GRID, false);
		try {
			
			if(file.exists()){
				File[] subFiles = file.listFiles();
				if(subFiles!=null && subFiles.length>0){
					for (int i = 0; i < subFiles.length; i++) {
						File file0= subFiles[i];
						String name = file0.getName();
						if(name.substring(name.lastIndexOf(".") + 1).equalsIgnoreCase("zzb3")){
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("fname", name);
							map.put("fabsolutepath", file0.getAbsolutePath().replace("\\", "/"));
							gridList.add(map);
						}
					}
				}
			}
			if(gridList.size()>0){
				grid.setValueList(gridList);
			}else{
				grid.setValueList(new ArrayList<HashMap<String,Object>>());
			}
		} catch (Exception e) {
			throw new RadowException("����Ŀ¼ʧ��:"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("btnsx.onclick")
	public int btnsxOnClick()throws RadowException{
		this.setNextEventName("zzbsearch");
		return EventRtnType.NORMAL_SUCCESS;
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
}
