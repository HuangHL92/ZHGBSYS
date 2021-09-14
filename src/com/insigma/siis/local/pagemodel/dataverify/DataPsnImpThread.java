package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
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
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil2;
import com.insigma.siis.local.business.utils.Xml4Zb3Util;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;
import com.insigma.siis.local.util.TYGSsqlUtil;

public class DataPsnImpThread implements Runnable {
	
	private String uuid;
	private String gjgs;
	private CurrentUser user;
	private String ltry;
	private String gzlbry;
	private String gllbry;
	private String searchDeptid;
	private String linkpsn;
	private String linktel;
	private String remark;
	private String gz_lb;
	private String gl_lb;
	private String tabimp;
	private String zdcjg;
	private UserVO userVo;
	private String ids;
    public DataPsnImpThread(String uuid, CurrentUser user,String gjgs,String ltry,String gzlbry
    		,String gllbry,String searchDeptid,String linkpsn,String linktel,
    		String remark,String gz_lb,String gl_lb,String tabimp,String zdcjg,UserVO userVo,String ids) {
        this.uuid = uuid;
        this.gjgs = gjgs;
        this.user = user;
        this.zdcjg = zdcjg;
        this.ltry = ltry;
        this.gzlbry = gzlbry;
        this.gllbry = gllbry;
        this.searchDeptid = searchDeptid;
        this.linkpsn = linkpsn;
        this.linktel = linktel;
        this.remark = remark;//δ�õ���������hzb��7z���
        this.gz_lb = gz_lb;
        this.gl_lb = gl_lb;
        this.tabimp = tabimp;
        this.userVo = userVo;
        this.ids = ids;
    }

	@Override
	public void run() {
		Map<String, String> map = new HashMap<String, String>();
		String infile = "";										// �ļ�
		String process_run = "1";								// �������
		String path = "";
		try {
			CommonQueryBS.systemOut(DateUtil.getTime());
			if(tabimp == null || tabimp.equals("") || tabimp.equals("1")){
				HBSession sess = HBUtil.getHBSession();
				
				if(ids.startsWith("select")){
					
				}else{
					String[] s = ids.split(",");
					if(s.length>1000){
						throw new RuntimeException("��ѡ����Ա���ó���1000�ˣ�������δ���");
					}
				}
				
				//����Ա����ǰ��У�鹤��
				KingbsconfigBS.saveImpDetail(process_run,"1","���ڽ�������У��",uuid);						//��¼У�����
				//1���������벻��Ϊ��
				String sqla = "";
				if(DBType.ORACLE == DBUtil.getDBType()){
					sqla = "select count(1) from b01 where 1 = 1 and exists (select 1 from A02 where a0000 in(" + ids + ") and A02.a0201b = b01.b0111) and b0114 is null and b0111 != '-1'";
				}else{
					sqla = "select count(1) from b01 where b0111 in (select a0201b from A02 where a0000 in(" + ids + ")) and b0111 != '-1' and (b0114 is null or b0114 = '')";
				}
				Object obj1 = sess.createSQLQuery(sqla).uniqueResult();
				if(obj1!=null){
					Integer one = Integer.parseInt(""+obj1);
					if(one > 0){
						throw new RuntimeException("������Ա��ְ��������Ϊ��");
					}
				}
				//2���������벻���ظ�
				
				//3����������Ա���֤����Ϊ��
				String sqlc = "";
				if(DBType.ORACLE == DBUtil.getDBType()){
					sqlc = "SELECT COUNT(1) FROM a01 WHERE a0000 IN(" +ids + ") AND a01.A0184 IS NULL";
				}else{
					sqlc = "SELECT COUNT(1) FROM a01 WHERE a0000 IN(" +ids + ") AND (a01.A0184 IS NULL OR a01.A0184 = '')";
				}
				Object obj3 = sess.createSQLQuery(sqlc).uniqueResult();
				if(obj3!=null){
					Integer one = Integer.parseInt(""+obj3);
					if(one > 0){
						throw new RuntimeException("������Ա���֤Ϊ��");
					}
				}
				
				//4����������Ա���֤�����ظ� 
				String sqld = "SELECT SUM(N) FROM (SELECT COUNT(1) AS N FROM a01 where a01.A0000 IN(" +ids + ") GROUP BY a01.A0184 HAVING count(1) >= 2) TMP";
				/*if(DBType.ORACLE == DBUtil.getDBType()){
					sqld = "SELECT COUNT(1) FROM A01 WHERE 1 = 1 AND (EXISTS (SELECT 1 FROM A01 A WHERE A.A0184 = A01.A0184 AND A.A0000 IN(" +ids + ") AND A.a0000 != A01.a0000))";
				}else{
					sqld = "SELECT COUNT(1) FROM A01, A01 AS A WHERE A.A0184 = A01.A0184 AND A.A0000 IN (" +ids + ") AND A.a0000 != A01.a0000";
				}*/
				Object obj4 = sess.createSQLQuery(sqld).uniqueResult();
				if(obj4!=null){
					Integer one = Integer.parseInt(""+obj4);
					if(one > 0){
						throw new RuntimeException("������Ա���֤�ظ�");
					}
				}
				
				//------------      -------------//
				Object psn = sess.createSQLQuery("select count(*) from a01 where a0000 in(" + ids + ")").uniqueResult();
				List<Map> list17 = new ArrayList<Map>();
				map.put("type", "31");		//����Ա��������
				map.put("time", DateUtil.timeToString(DateUtil.getTimestamp()));
				//     ??
				map.put("dataversion", "20171020");
				map.put("psncount", (psn!=null)? psn.toString() : "");
				map.put("photodir", "Photos");
				map.put("B0101", "");
				map.put("B0111", "");
				map.put("B0114", "");
				map.put("B0194", "");
				map.put("linkpsn", "");
				map.put("linktel", "");
				map.put("remark", "������ԱΪ" +((psn!=null)? psn.toString() : "") + "��");
				list17.add(map);
				path = getPath();
				String zippath = path + "����Ա�����ļ�_" + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +"/";
				String name = "����Ա�����ļ�_" + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss");
				if("hzb".equals(remark)){
					name = name + ".hzb";
				}else if("7z".equals(remark)){
					name = name + ".7z";
				}
				//--------------------------------------------------------------------
				//����ҳ���ļ�����
				String time = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
				String sql1 = "insert into EXPINFO (ID,NAME,STARTTIME,CREATEUSER,STATUS,PSNCOUNT) values ('"+uuid+"','"+name+"','"+time+"','"+user.getId()+"','�ļ����������Ժ�...','"+""+psn+"')";
				sess.createSQLQuery(sql1).executeUpdate();
				//------------------------------------------------------------------------
				File file =new File(zippath);    
				//����ļ��в������򴴽�    
				if (!file.exists()){       
				    file.mkdirs();    
				}
				String zippathtable = zippath +"Table/";
				File file1 =new File(zippathtable);    
				//����ļ��в������򴴽�    
				if(!file1.exists()){       
				    file1 .mkdirs();    
				}
				String zipfile = path + "����Ա�����ļ�_" + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss");
				if("hzb".equals(remark)){
					zipfile = zipfile + ".hzb";
				}else if("7z".equals(remark)){
					zipfile = zipfile + ".7z";
				}
				KingbsconfigBS.saveImpDetail("1","2","���",uuid);						//��¼�������
				process_run = "2";
				int number1 = 1;														//�ѽ��������ľ
				//int number2 = 27;														//δ���������ľ
				//int number2 = 13;	
				int number2 = 19;	
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A01.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				
				Connection conn = sess.connection();
				Statement stmt = null;
				stmt = conn.createStatement();
				ResultSet rs_a01 = null;
				if("hzb".equals(remark)){
					rs_a01 = stmt.executeQuery("select * from a01 where a0000 in(" +ids + ")");
				}else if("7z".equals(remark)){
					rs_a01 = stmt.executeQuery("select "+TYGSsqlUtil.A01+" from a01 where a0000 in(" +ids + ")");
				}
				Xml4HZBUtil2.List2Xml(rs_a01, "A01", zippath, remark);
				rs_a01.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A02.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_a02 = null;
				if("hzb".equals(remark)){
					rs_a02 = stmt.executeQuery("select * from A02 where a0000 in(" +ids + ")");
				}else if("7z".equals(remark)){
					rs_a02 = stmt.executeQuery("select "+TYGSsqlUtil.A02+" from A02 where a0000 in(" +ids + ")");
				}
				Xml4HZBUtil2.List2Xml(rs_a02, "A02", zippath, remark);
				rs_a02.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A06.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_a06 = null;
				if("hzb".equals(remark)){
					rs_a06 = stmt.executeQuery("select * from A06 where a0000 in(" +ids + ")");
				}else if("7z".equals(remark)){
					rs_a06 = stmt.executeQuery("select "+TYGSsqlUtil.A06+" from A06 where a0000 in(" +ids + ")");
				}
				Xml4HZBUtil2.List2Xml(rs_a06, "A06", zippath, remark);
				rs_a06.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A08.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_a08 = null;
				if("hzb".equals(remark)){
					rs_a08 = stmt.executeQuery("select * from A08 where a0000 in(" +ids + ")");
				}else if("7z".equals(remark)){
					rs_a08 = stmt.executeQuery("select "+TYGSsqlUtil.A08+" from A08 where a0000 in(" +ids + ")");
				}
				Xml4HZBUtil2.List2Xml(rs_a08, "A08", zippath, remark);
				rs_a08.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A11.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_a11 = stmt.executeQuery("select * from A11 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_a11, "A11", zippath, remark);
				rs_a11.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A14.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_a14 = null;
				if("hzb".equals(remark)){
					rs_a14 = stmt.executeQuery("select * from A14 where a0000 in(" +ids + ")");
				}else if("7z".equals(remark)){
					rs_a14 = stmt.executeQuery("select "+TYGSsqlUtil.A14+" from A14 where a0000 in(" +ids + ")");
				}
				Xml4HZBUtil2.List2Xml(rs_a14, "A14", zippath, remark);
				rs_a14.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A15.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_a15 = null;
				if("hzb".equals(remark)){
					rs_a15 = stmt.executeQuery("select * from A15 where a0000 in(" +ids + ")");
				}else if("7z".equals(remark)){
					rs_a15 = stmt.executeQuery("select "+TYGSsqlUtil.A15+" from A15 where a0000 in(" +ids + ")");
				}
				Xml4HZBUtil2.List2Xml(rs_a15, "A15", zippath,remark);
				rs_a15.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A29.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A29 = stmt.executeQuery("select * from A29 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_A29, "A29", zippath, remark);
				rs_A29.close();
				stmt.close();
				
				
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A31.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A31 = stmt.executeQuery("select * from A31 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_A31, "A31", zippath, remark);
				rs_A31.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A30.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A30 =null;
				
				if("hzb".equals(remark)){
					rs_A30 = stmt.executeQuery("select * from A30 where a0000 in(" +ids + ")");
				}else if("7z".equals(remark)){
					rs_A30 = stmt.executeQuery("select "+TYGSsqlUtil.A30+" from A30 where a0000 in(" +ids + ")");
				}
				Xml4HZBUtil2.List2Xml(rs_A30, "A30", zippath, remark);
				rs_A30.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A36.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A36 = null;
				if("hzb".equals(remark)){
					rs_A36 = stmt.executeQuery("select * from A36 where a0000 in(" +ids + ")");
				}else if("7z".equals(remark)){
					rs_A36 = stmt.executeQuery("select "+TYGSsqlUtil.A36+" from A36 where a0000 in(" +ids + ")");
				}
				Xml4HZBUtil2.List2Xml(rs_A36, "A36", zippath, remark);
				rs_A36.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A37.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A37 = stmt.executeQuery("select * from A37 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_A37, "A37", zippath, remark);
				rs_A37.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A41.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A41 = stmt.executeQuery("select * from A41 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_A41, "A41", zippath, remark);
				rs_A41.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A53.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A53 = stmt.executeQuery("select * from A53 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_A53, "A53", zippath, remark);
				rs_A53.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A57.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A57 = null;
				if("hzb".equals(remark)){
					rs_A57 = stmt.executeQuery("select * from A57 where a0000 in(" +ids + ")");
				}else if("7z".equals(remark)){
					rs_A57 = stmt.executeQuery("select "+TYGSsqlUtil.A57+" from A57 where a0000 in(" +ids + ")");
				}
				Xml4HZBUtil2.List2Xml(rs_A57, "A57", zippath, remark);
				rs_A57.close();
				stmt.close();
				
				/*KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A60.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A60 = stmt.executeQuery("select * from A60 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_A60, "A60", zippath);
				rs_A60.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A61.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A61 = stmt.executeQuery("select * from A61 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_A61, "A61", zippath);
				rs_A61.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A62.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A62 = stmt.executeQuery("select * from A62 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_A62, "A62", zippath);
				rs_A62.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A63.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A63 = stmt.executeQuery("select * from A63 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_A63, "A63", zippath);
				rs_A63.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A64.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A64 = stmt.executeQuery("select * from A64 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_A64, "A64", zippath);
				rs_A64.close();
				stmt.close();*/
				
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A05.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A05 = null;
				if("hzb".equals(remark)){
					rs_A05 = stmt.executeQuery("select * from A05 where a0000 in(" +ids + ")");
				}else if("7z".equals(remark)){
					rs_A05 = stmt.executeQuery("select "+TYGSsqlUtil.A05+" from A05 where a0000 in(" +ids + ")");
				}
				Xml4HZBUtil2.List2Xml(rs_A05, "A05", zippath, remark);
				rs_A05.close();
				stmt.close();
				
				/*KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A68.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A68 = stmt.executeQuery("select * from A68 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_A68, "A68", zippath);
				rs_A68.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A69.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A69 = stmt.executeQuery("select * from A69 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_A69, "A69", zippath);
				rs_A69.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A71.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A71 = stmt.executeQuery("select * from A71 where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_A71, "A71", zippath);
				rs_A71.close();
				stmt.close();*/
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A99Z1.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				ResultSet rs_A99Z1 = null;
				if("hzb".equals(remark)){
					rs_A99Z1 = stmt.executeQuery("select * from A99Z1 where a0000 in(" +ids + ")");
				}else if("7z".equals(remark)){
					rs_A99Z1 = stmt.executeQuery("select "+TYGSsqlUtil.A99Z1+" from A99Z1 where a0000 in(" +ids + ")");
				}
				Xml4HZBUtil2.List2Xml(rs_A99Z1, "A99Z1", zippath, remark);
				rs_A99Z1.close();
				stmt.close();
				
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��B01.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				stmt = conn.createStatement();
				String b01sql = "";
				if(DBUtil.getDBType().equals(DBType.ORACLE)){
					if("hzb".equals(remark)){
						b01sql = "select * from b01 where b0111 in (select a0201b from A02 where a0000 in(" +ids + ") UNION SELECT a0195 FROM a01 WHERE A0000 IN ("+ids+")) and b0114 is not null";
					}else if("7z".equals(remark)){
						b01sql = "select "+TYGSsqlUtil.B01+" from b01 where b0111 in (select a0201b from A02 where a0000 in(" +ids + ") UNION SELECT a0195 FROM a01 WHERE A0000 IN ("+ids+")) and b0114 is not null";
					}
				} else {
					if("hzb".equals(remark)){
						b01sql = "select * from b01 where b0111 in (select a0201b from A02 where a0000 in(" +ids + ") UNION SELECT a0195 FROM a01 WHERE A0000 IN ("+ids+")) and b0114 is not null and b0114<>''";
					}else if("7z".equals(remark)){
						b01sql = "select "+TYGSsqlUtil.B01+" from b01 where b0111 in (select a0201b from A02 where a0000 in(" +ids + ") UNION SELECT a0195 FROM a01 WHERE A0000 IN ("+ids+")) and b0114 is not null and b0114<>''";
					}
				}
				ResultSet rs_b01 = stmt.executeQuery(b01sql);
				
				Xml4HZBUtil2.List2Xml(rs_b01, "B01", zippath, remark);
				rs_b01.close();
				stmt.close();
				
				/*KingbsconfigBS.saveImpDetail(process_run,"1","�����������ɴ���",uuid);		//��¼�������
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_info = stmt.executeQuery("select * from INFO_EXTEND where a0000 in(" +ids + ")");
				Xml4HZBUtil2.List2Xml(rs_info, "INFO_EXTEND", zippath);
				rs_info.close();
				stmt.close();*/
				
				KingbsconfigBS.saveImpDetail(process_run,"1","����˵���ļ����ɴ�����",uuid);	
				Xml4HZBUtil2.List2Xml(list17, "info", zippath);
				
				//------------------------------------------------------------
				//��¼ҳ������
				String sql4 = "update EXPINFO set STATUS = '��Ƭ���������Ժ�...' where id = '"+uuid+"'";
				sess.createSQLQuery(sql4).executeUpdate();
				//---------------------------------------------------------------
				KingbsconfigBS.saveImpDetail(process_run,"1","��Ա��Ƭͷ�����ɴ�����",uuid);	
				stmt = conn.createStatement();
				ResultSet rs_A57t = stmt.executeQuery("select * from A57 where a0000 in(" +ids + ")");
				if(rs_A57t!=null){
					String photopath = zippath + "Photos/";				//����ͼƬ·��      
					File file2 =new File(photopath);    
					if  (!file2 .exists()  && !file2 .isDirectory()){   //����ļ��в������򴴽�       
						file2 .mkdirs();    
					}
//					byte[] Buffer = new byte[4096];  
					List<String> photolist = new ArrayList<String>();
					while (rs_A57t.next()) {
						String a0000 = rs_A57t.getString("a0000");
						String photoname = rs_A57t.getString("photoname");
						String photop = rs_A57t.getString("photopath");
//						Blob Photodata = rs_A57t.getBlob("photodata");
//						if(a0000!=null && !a0000.equals("") && Photodata!=null){
//							File f = new File(photopath + a0000 +"." + "jpg");
//							FileOutputStream fos = new FileOutputStream(f);  
//							InputStream is = rs_A57t.getBinaryStream("photodata");  
//							int size = 0;  
//							while((size = is.read(Buffer)) != -1){  
//								fos.write(Buffer,0,size);  
//							}
//							fos.close();
//							is.close();
//						}
//						String photon = (photoname!=null&&!photoname.equals(""))? photoname:a0000 +"." + "jpg";
//						photolist.add(photop+photon);
						String photon = (photoname!=null&&!photoname.equals(""))? photoname:a0000 +"." + "jpg";
						PhotosUtil.copyFile(photop+photon, photopath);
					}
//					PhotosUtil.copyCmd(photolist, photopath);
				}
				rs_A57t.close();
				stmt.close();
				KingbsconfigBS.saveImpDetail(process_run,"2","���",uuid);			//��¼�������
				process_run = "3";
				KingbsconfigBS.saveImpDetail(process_run,"1","ѹ����",uuid);			//��¼�������
				infile = zipfile;
				//------------------------------------------------------------
				//��¼ҳ������
				String sql3 = "update EXPINFO set STATUS = '�ļ�ѹ�������Ժ�...' where id = '"+uuid+"'";
				sess.createSQLQuery(sql3).executeUpdate();
				//---------------------------------------------------------------
				if("hzb".equals(remark)){
					NewSevenZipUtil.zip7zNew(zippath, zipfile, "1");
				}else if("7z".equals(remark)){
					Zip7z.zip7Z(zippath, zipfile, "20171020");
				}
				
				KingbsconfigBS.saveImpDetail(process_run,"2","���",uuid,infile.replace("\\", "/"));	
				//------------------------------------------------------------
				//��¼ҳ������
				String time2 = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
				String sql2 = "update EXPINFO set endtime = '"+time2+"',STATUS = '�������',zipfile = '"+zipfile+"' where id = '"+uuid+"'";
				sess.createSQLQuery(sql2).executeUpdate();
				//---------------------------------------------------------------
				try {
					new LogUtil("421", "IMP_RECORD", "", "", "���ݵ���", new ArrayList(),userVo).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.delFolder(zippath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(!path.equals("")){
				this.delFolder(path);
			}
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","ʧ��:"+e.getMessage(),uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//������ʾ��Ϣ�� 
			
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
	
	private String getPath() {
		String upload_file = AppConfig.HZB_PATH + "/temp/zipload/";
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
}
