package com.insigma.siis.local.pagemodel.customquery;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.codec.binary.Base64;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.License;
import com.aspose.words.MailMergeSettings;
import com.aspose.words.SaveFormat;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.DataToDB;
import com.insigma.siis.local.business.utils.SQLiteUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ItemXml;
import com.insigma.siis.local.lrmx.JiaTingChengYuanXml;
import com.insigma.siis.local.lrmx.PersonXml;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;

/**
 * �����ϻ�Pad���ݣ��������飨������⣩
 * @author 15084
 *
 */
public class DataSHPadImpDBThread implements Runnable {
	
	private String uuid;
	private UserVO userVo;
	private String meetingid;
	private String userid;
	
	public DataSHPadImpDBThread(String uuid,UserVO userVo) {
        this.uuid = uuid;
        this.userVo = userVo;
    }
	
	public DataSHPadImpDBThread(String uuid,UserVO userVo, String meetingid, String userid) {
		this.uuid = uuid;
        this.userVo = userVo;
        this.meetingid = meetingid;
        this.userid = userid;
	}
	
//	public DataSHPadImpDBThread(String uuid,UserVO userVo, String padCons, String excelPath) {
//		this.uuid = uuid;
//		this.userVo = userVo;
//		this.padCons = padCons;
//		this.excelPath = excelPath;
//	}
//	
//	public DataSHPadImpDBThread(String uuid,UserVO userVo, String padCons, String excelPath, String ids) {
//		this.uuid = uuid;
//		this.userVo = userVo;
//		this.padCons = padCons;
//		this.excelPath = excelPath;
//		this.ids = ids;
//	}
	public void updateStatus(String sql) throws AppException {
		HBSession sess = HBUtil.getHBSession();
		HBTransaction trans = sess.beginTransaction();
		Connection conn = sess.connection();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		trans.commit();
	}
	@SuppressWarnings({ "rawtypes", "static-access" })
	@Override
	public void run() {
		HBSession sess = HBUtil.getHBSession();
		String zipPath=getZipPath();
		Connection conn = sess.connection();
		File mdatafile = new File(this.getClass().getClassLoader().getResource("./shdata.db").getPath());
		File file = new File(zipPath+"shdata.db");
		
		//String userid=userVo.getId();
		
		try{
			SQLiteUtil.copyFile(mdatafile, file);
			Connection sqlitconn=new SQLiteUtil(zipPath+"shdata.db").getConnection();
			KingbsconfigBS.saveImpDetail("1","1","���ڽ�������У��",uuid);
			KingbsconfigBS.saveImpDetail("1","2","���",uuid);
			
			recreateSourceINX(sess);
			
			int number1 = 1;
			
			KingbsconfigBS.saveImpDetail("2","1","�ļ�"+(number1++)+"��A01�������ɴ���",uuid);
			System.out.println("A01��ʼ");
			//String a01="select b.a0000,b.a0101,b.a0192a,'��'||decode(a0104,'1','��','2','Ů')||'��'||substr(a0107,1,4)||'.'||substr(a0107,5,2)||'��'||zgxl||zgxw||decode(a0141,'','','��'||(select c.code_name from code_value c where code_type='GB4762' and c.code_value=b.a0141))||'��' a0102 from hz_sh_a01 a,A01 b where a.a0000=b.a0000 and a.publishid in (select publishid from publish where meetingid ='"+meetingid+"')";
			String a01="select sh000,a0000,a0101,a0192a||tp0125 as a0192a,"
					+ " '��'||decode(a0104,'1','��','2','Ů')||'��'||substr(a0107,1,4)||'.'||substr(a0107,5,2)||'��'||zgxl||"
					+ "case when zgxldj>zgxwdj then '��'||zgxw else '' end"
					+ "||decode(a0141,'','','��'||(select c.code_name from code_value c where code_type='GB4762' and c.code_value=t.a0141))||'��' a0102,"
					+ " (select '��'||decode(t.a0104,'1','��','2','Ů')||'��'||substr(t.a0107,1,4)||'.'||substr(t.a0107,5,2)||'��'||decode(t.a0141,'','','��'||(select c.code_name from code_value c where code_type='GB4762' and c.code_value=b.a0141))||'��'||t.zgxl||"
					+ "case when zgxldj>zgxwdj then '��'||zgxw else '' end ||'��'"
					+ " ||(case when t.a0192a like 'ԭ%' then t.a0192a else '����'||t.a0192a end )||decode(a0221,'','','��'||substr(a0288,1,4)||'.'||substr(a0288,5,2)||'��'||(select c.code_name from code_value c where code_type='ZB09' and c.code_value=b.a0221)||'��')||decode(tp0125,'','','��'||tp0125) from a01 b where b.a0000=t.a0000) a0103 "
					+ " from "
					+ "(select sh000,a.a0000,a.tp0111,a.tp0112,a.tp0114,a.a0101,a.a0192a,a.tp0125,a0104,a0107,zgxl,a0141,sh001,"
					+ "case when zgxw like '%��ʿ%'  then '��ʿ' when zgxw like '%˶ʿ%'  then '˶ʿ' when zgxw like '%ѧʿ%'  then 'ѧʿ' else '' end  zgxw, "
					+ "case when zgxl like '%�о���%' or zgxl like '%˶ʿ%' then '2' when zgxl like '%��ѧ%' or zgxl like '%ѧʿ%' then '3' else '4' end  zgxldj, "
					+ "case when zgxw like '%��ʿ%'  or zgxw like '%˶ʿ%'  then '2' when zgxw like '%ѧʿ%'  then '3' else '4' end  zgxwdj"
					+ "    from (select * from hz_sh_a01 where publishid in (select publishid from publish where meetingid in ("+meetingid+")"
					+ "  and (userid='"+userid+"' or publishid in (select publishid from publishuser where (islook='1' or ischange='1') and userid='"+userid+"')"
					+ ")) or sh000 in (select sh000 from personcite where publishid_new in (select publishid from publish where  meetingid in ("+meetingid+") "
					+ " and (userid='"+userid+"' or publishid in (select publishid from publishuser where (islook='1' or ischange='1') and userid='"+userid+"'))))) a ) t  order by sh001,a0101";
			
			ResultSet a01rs=execQuery(conn,a01);
			new DataToDB().insert(sqlitconn, a01rs,"a01");
			System.out.println("A01����");
			
			KingbsconfigBS.saveImpDetail("2","1","�ļ�"+(number1++)+"��MEETINGTHEME�������ɴ���",uuid);
			System.out.println("MEETINGTHEME��ʼ");
			String meetingtheme="select * from meetingtheme where  meetingid in ("+meetingid+") ";
			ResultSet meetingthemers=execQuery(conn,meetingtheme);
			new DataToDB().insert(sqlitconn, meetingthemers,"meetingtheme");
			System.out.println("MEETINGTHEME����");
			
			KingbsconfigBS.saveImpDetail("2","1","�ļ�"+(number1++)+"��PUBLISH�������ɴ���",uuid);
			System.out.println("PUBLISH��ʼ");
			String publish="select * from publish where  meetingid in ("+meetingid+")  and (userid='"+userid+"' or publishid in (select publishid from publishuser where (islook='1' or ischange='1') and userid='"+userid+"'))";
			ResultSet publishrs=execQuery(conn,publish);
			new DataToDB().insert(sqlitconn, publishrs,"publish");
			System.out.println("PUBLISH����");
			
			KingbsconfigBS.saveImpDetail("2","1","�ļ�"+(number1++)+"��HZ_SH_TITLE�������ɴ���",uuid);
			System.out.println("HZ_SH_TITLE��ʼ");
			String title="select titleid,title01,title02,title03,title04,userid,"
					+ "case when title02='1' then a.sortid+1000||'10001000' "
					+ " when title02='2' then  nvl((select b.sortid from hz_sh_title b where b.titleid=a.title04 ),0)+1000||''||lpad(a.sortid,4,0)+1000||'1000'"
					+ " when title02='3' then  nvl((select c.sortid from hz_sh_title b,hz_sh_title c where b.titleid=a.title04 and c.titleid=b.title04 ),0)+1000||''"
					+ "		||lpad(nvl((select b.sortid from hz_sh_title b where b.titleid=a.title04 ),0),4,0)+1000||lpad(a.sortid,4,0)+1000||'' end sortid"
					+ ",meetingid,meetingid,publishid,title05,title06,title07 from hz_sh_title a where  meetingid in ("+meetingid+")  and (publishid in (select publishid from publishuser where (islook='1' or ischange='1')) or publishid in (select publishid from publish where userid='"+userid+"'))  ";
			ResultSet titlers=execQuery(conn,title);
			new DataToDB().insert(sqlitconn, titlers,"hz_sh_title");
			System.out.println("HZ_SH_TITLE����");
			
			KingbsconfigBS.saveImpDetail("2","1","�ļ�"+(number1++)+"��HZ_SH_A01�������ɴ���",uuid);
			System.out.println("HZ_SH_A01��ʼ");
			String sh_a01="select sh000,a0000,publishid,sh001,titleid,tp0111||tp0123 as tp0111,decode(tp0122,'1',tp0112||tp0124,'',tp0112||tp0124,tp0124) tp0112"
					+ "		,tp0113,tp0114,tp0115,tp0116,tp0117,tp0118,tp0119,a0101,a0104,a0107,decode(a0141,'','13',a0141) a0141"
					+ "		,zgxl||case when zgxldj>zgxwdj then '��'||zgxw else '' end zgxl,'' zgxw,"
					+ "		case when (select agendatype  from publish x where x.publishid=a.publishid)='1' then a0192a||decode(tp0125,'','',tp0125) "
					+ "		 when (select agendatype  from publish x where x.publishid=a.publishid)='3' then a0192a||decode(tp0125,'','','��'||tp0125) end  as a0192a,tp0121,tp0122 "
					+ " from (select sh000,a0000,publishid,sh001,titleid,tp0111,tp0112"
					+ "		,tp0113,tp0114,tp0115,tp0116,tp0117,tp0118,tp0119,a0101,a0104,a0107,a0141"
					+ "		,zgxl,case when zgxw like '%��ʿ%'  then '��ʿ' when zgxw like '%˶ʿ%'  then '˶ʿ' when zgxw like '%ѧʿ%'  then 'ѧʿ' else '' end  zgxw, "
					+ "case when (zgxl like '%�о���%' or zgxl like '%˶ʿ%') and zgxl not like '%��У�о���%' then '2' when zgxl like '%��У�о���%' or zgxl like '%��ѧ%' or zgxl like '%ѧʿ%' then '3' else '4' end  zgxldj, "
					+ "case when zgxw like '%��ʿ%'  or zgxw like '%˶ʿ%'  then '2' when zgxw like '%ѧʿ%'  then '3' else '4' end  zgxwdj,a0192a,tp0121,tp0122,tp0123,tp0124,tp0125 from hz_sh_a01 where publishid in (select publishid from publish where meetingid in ("+meetingid+")  and (userid='"+userid+"' or publishid in (select publishid from publishuser where (islook='1' or ischange='1') and userid='"+userid+"'))) "
					+ " union select b.sh000,a.a0000,b.publishid_new,b.sh001,b.titleid_new,tp0111,tp0112"
					+ "		,tp0113,tp0114,tp0115,tp0116,tp0117,tp0118,tp0119,a0101,a0104,a0107,a0141"
					+ "		,zgxl,case when zgxw like '%��ʿ%'  then '��ʿ' when zgxw like '%˶ʿ%'  then '˶ʿ' when zgxw like '%ѧʿ%'  then 'ѧʿ' else '' end  zgxw, "
					+ "case when (zgxl like '%�о���%' or zgxl like '%˶ʿ%') and zgxl not like '%��У�о���%' then '2' when zgxl like '%��У�о���%' or zgxl like '%��ѧ%' or zgxl like '%ѧʿ%' then '3' else '4' end  zgxldj, "
					+ "case when zgxw like '%��ʿ%'  or zgxw like '%˶ʿ%'  then '2' when zgxw like '%ѧʿ%'  then '3' else '4' end  zgxwdj,a0192a,tp0121,tp0122,tp0123,tp0124,tp0125  "
					+ "	from hz_sh_a01 a,personcite b where a.sh000=b.sh000 and b.publishid_new in (select publishid from publish where meetingid in ("+meetingid+")  and (userid='"+userid+"' or publishid in (select publishid from publishuser where (islook='1' or ischange='1') and userid='"+userid+"'))) "
					+ "	) a  order by sh001,a0101";
			ResultSet sh_a01rs=execQuery(conn,sh_a01);
			new DataToDB().insert(sqlitconn, sh_a01rs,"hz_sh_a01");
			System.out.println("HZ_SH_A01����");
			
			/*
			KingbsconfigBS.saveImpDetail("2","1","�ļ�"+(number1++)+"��PERSONCITE�������ɴ���",uuid);
			System.out.println("PERSONCITE��ʼ");
			String cite="select * from personcite where publishid_new in (select publishid from publish where meetingid ='"+meetingid+"')";
			ResultSet citers=execQuery(conn,cite);
			new DataToDB().insert(sqlitconn, citers,"personcite");
			System.out.println("PERSONCITE����");
			*/
			
			sqlitconn.commit();
			sqlitconn.close();
			
			KingbsconfigBS.saveImpDetail("2","1","�ļ�"+(number1++)+"�������Lrmx���ɴ���",uuid);
			exportLrmx(meetingid,zipPath,userid);
			System.out.println("�����Lrmx����");
			
			
			
			KingbsconfigBS.saveImpDetail("2","1","�ļ�"+(number1++)+"���������ת������",uuid);
			CommQuery cqbs=new CommQuery();
			String kccl="select sh000,tp0115 from hz_sh_a01 where publishid in (select publishid from publish where meetingid in ("+meetingid+")  and (userid='"+userid+"' or publishid in (select publishid from publishuser where (islook='1' or ischange='1') and userid='"+userid+"'))) and tp0115 is not null "
					+ " union select a.sh000,a.tp0115 from hz_sh_a01 a,personcite b where a.sh000=b.sh000 and a.tp0118 is not null and b.publishid_new in (select publishid from publish where meetingid in ("+meetingid+") and (userid='"+userid+"' or publishid in (select publishid from publishuser where (islook='1' or ischange='1') and userid='"+userid+"')))";
			List<HashMap<String, Object>> listcl=cqbs.getListBySQL(kccl);
			if(listcl!=null && listcl.size()>0) {
				String zippath =zipPath+"sh_kccl/";
				File f = new File(zippath);
				if(!f.isDirectory()){
					f.mkdirs();
				}
				String disk = getCtxPath();
				StopWatch w = new StopWatch();
				StopWatch w2 = new StopWatch();
				w2.start();
				for(HashMap<String, Object> map:listcl) {
					File file2 = new File(disk+map.get("tp0115"));
					InputStream inStream = new FileInputStream(file2);
					
					
					/*w.start();
					List<BufferedImage> wordToImg = wordToImg(inStream,10,zippath+map.get("a0000")+".png");
					w.stop();
					System.out.println("ת��ͼƬ��"+w.elapsedTime());
					w.start();
					BufferedImage mergeImage = mergeImage(false, wordToImg);
					w.stop();
					System.out.println("�ϲ�ͼƬ��"+w.elapsedTime());
					w.start();
					ImageIO.write(mergeImage, "jpeg", new File(zippath+map.get("a0000")+".png"));
					w.stop();
					System.out.println("д��ͼƬ��"+w.elapsedTime());*/
					wordToImg3(inStream,20,zippath+map.get("sh000")+".svg");
				}
				w2.stop();
				System.out.println("�ܺ�ʱ��"+w2.elapsedTime());
			}
			System.out.println("�������ת������");
			
			KingbsconfigBS.saveImpDetail("2","1","�ļ�"+(number1++)+"�����⸽��ת������",uuid);
			String btfj="select titleid,title05 from hz_sh_title where publishid in (select publishid from publish where meetingid in ("+meetingid+") and (userid='"+userid+"' or publishid in (select publishid from publishuser where (islook='1' or ischange='1') and userid='"+userid+"'))) and title05 is not null ";
			List<HashMap<String, Object>> listbt=cqbs.getListBySQL(btfj);
			if(listbt!=null && listbt.size()>0) {
				String zippath =zipPath+"sh_btfj/";
				File f = new File(zippath);
				if(!f.isDirectory()){
					f.mkdirs();
				}
				String disk = getCtxPath();
				for(HashMap<String, Object> map:listbt) {
					File file2 = new File(disk+map.get("title05"));
					InputStream inStream = new FileInputStream(file2);
					List<BufferedImage> wordToImg = wordToImg2(inStream,100,zippath+map.get("titleid")+"_");
//					BufferedImage mergeImage = mergeImage(false, wordToImg);
//					ImageIO.write(mergeImage, "jpg", new File(zippath+map.get("titleid")+".png"));
					/*for(int i=1;i<=wordToImg.size();i++) {
						BufferedImage mergeImage = updateImage(false, wordToImg.get(i-1));
						ImageIO.write(mergeImage, "jpg", new File(zippath+map.get("titleid")+"_"+i+".png"));
					}*/
				}
			}
			System.out.println("���⸽��ת������");
			
			
			File updatefile=new File(zipPath+"time"+DateUtil.timeToString(DateUtil.getTimestamp(), "yyyy-MM-dd"));
			if(!updatefile.exists()){
				updatefile.createNewFile();
			}
			
			KingbsconfigBS.saveImpDetail("2","2","���",uuid);
			KingbsconfigBS.saveImpDetail("3","1","ѹ����",uuid);	
			
			//KingbsconfigBS.saveImpDetail("3","2","���",uuid);
			String zipfile=zipPath.substring(0, zipPath.length()-1)+".zip";
			//��¼ҳ������
			String sql3 = "update EXPINFO set STATUS = '�ļ�ѹ�������Ժ�...' where id = '"+uuid+"'";
			sess.createSQLQuery(sql3).executeUpdate();
			
				
	
			
			//---------------------------------------------------------------
			Zip7z.zip7Z(zipPath.substring(0, zipPath.length()-1), zipPath.substring(0, zipPath.length()-1)+".zip", null);
			KingbsconfigBS.saveImpDetail("3","2","���",uuid,zipfile.replace("\\", "/"));	
			//------------------------------------------------------------
			//��¼ҳ������
			String time2 = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
			String sql2 = "update EXPINFO set endtime = '"+time2+"',STATUS = '�������',zipfile = '"+zipfile+"' where id = '"+uuid+"'";
			sess.createSQLQuery(sql2).executeUpdate();
			
			try {
				//new LogUtil("421", "IMP_RECORD", "", "", "���ݵ���", new ArrayList(),userVo).start();
				UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
				new LogUtil(user).createLogNew(user.getId(),"������Ա","personcite",user.getId(),"", new ArrayList());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			this.delFolder(zipPath);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				
			}
		}
		
	}

	

	private void recreateSourceINX(HBSession sess) {
		try{
			sess.createSQLQuery("DROP INDEX IN_A01_A0000").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("CREATE INDEX IN_A01_A0000 ON A01(A0000)").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("DROP INDEX IN_HZ_SH_A01_A0000").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("CREATE INDEX IN_HZ_SH_A01_A0000 ON HZ_SH_A01(A0000)").executeUpdate();
		}catch(Exception e){}
		try{
					
		}catch(Exception e){}
		try{
			sess.createSQLQuery("DROP INDEX IN_HZ_SH_A01_PUBLISHID").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("CREATE INDEX IN_HZ_SH_A01_PUBLISHID ON HZ_SH_A01(PUBLISHID)").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("DROP INDEX IN_HZ_SH_A01_TITLEID").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("CREATE INDEX IN_HZ_SH_A01_TITLEID ON HZ_SH_A01(TITLEID)").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("DROP INDEX IN_HZ_SH_A01_TITLEID").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("CREATE INDEX IN_HZ_SH_A01_TITLEID ON HZ_SH_A01(TITLEID)").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("DROP INDEX IN_HZ_SH_A01_SH000").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("CREATE INDEX IN_HZ_SH_A01_SH000 ON HZ_SH_A01(SH000)").executeUpdate();
		}catch(Exception e){}
		
		try{
			sess.createSQLQuery("DROP INDEX IN_HZ_SH_TITLE_TITLEID").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("CREATE INDEX IN_HZ_SH_TITLE_TITLEID ON HZ_SH_TITLE(TITLEID)").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("DROP INDEX IN_HZ_SH_TITLE_TITLE04").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("CREATE INDEX IN_HZ_SH_TITLE_TITLE04 ON HZ_SH_TITLE(TITLE04)").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("DROP INDEX IN_HZ_SH_TITLE_MEETINGID").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("CREATE INDEX IN_HZ_SH_TITLE_MEETINGID ON HZ_SH_TITLE(MEETINGID)").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("DROP INDEX IN_HZ_SH_TITLE_PUBLISHID").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("CREATE INDEX IN_HZ_SH_TITLE_PUBLISHID ON HZ_SH_TITLE(PUBLISHID)").executeUpdate();
		}catch(Exception e){}
		
		try{
			sess.createSQLQuery("DROP INDEX IN_PUBLISH_PUBLISHID").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("CREATE INDEX IN_PUBLISH_PUBLISHID ON PUBLISH(PUBLISHID)").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("DROP INDEX IN_PUBLISH_MEETINGID").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("CREATE INDEX IN_PUBLISH_MEETINGID ON PUBLISH(MEETINGID)").executeUpdate();
		}catch(Exception e){}
		
		try{
			sess.createSQLQuery("DROP INDEX IN_MEETINGTHEME_MEETINGID").executeUpdate();
		}catch(Exception e){}
		try{
			sess.createSQLQuery("CREATE INDEX IN_MEETINGTHEME_MEETINGID ON MEETINGTHEME(MEETINGID)").executeUpdate();
		}catch(Exception e){}
		
		
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
	
	
	public String phototobase64(String phototPath) throws Exception {
		File photoFile=new File(phototPath);
		if(photoFile.exists()){
			InputStream photoInputStream=new FileInputStream(new File(phototPath));
			byte[] photodata=new byte[photoInputStream.available()];
			photoInputStream.read(photodata);
			photoInputStream.close();
			String photoBase64=new String(Base64.encodeBase64(photodata));
			return photoBase64;
		}else{
			return "";
		}
	}



	public ResultSet execQuery(Connection conn,String sql) throws SQLException {
		Statement s =null;
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getZipPath() {
		//"D:/HZB//temp/zipload/"+uuid+"/";
		String path=getPath();
		String zipPath=path+"�ϻ�PAD�ļ�_"+DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+"/";
		File file = new File(zipPath);
		if(!file.exists()){
			file.mkdirs();
		}
		System.out.println("zipPath:"+zipPath);
		return zipPath;
	}
	
	private static String getCtxPath() {
        String upload_file = AppConfig.HZB_PATH + "/";
        try {
            File file = new File(upload_file);
            //����ļ��в������򴴽�
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //��ѹ·��
        return upload_file;
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
	private static BufferedImage makeThumbnail(Image img, int width, int height) {
		BufferedImage tag = new BufferedImage(width, height, 1);
		Graphics g = tag.getGraphics();
		//ԭͼ������img.getScaledInstance(width, height, 4)
		g.drawImage(img.getScaledInstance(width, height, 4), 0, 0, null);
		g.dispose();
		return tag;
	}

	private static  String saveSubImage(BufferedImage image,
			Rectangle subImageBounds, String filename,String path,String isupdate) throws IOException {
		
		BufferedImage subImage = new BufferedImage(subImageBounds.width,
				subImageBounds.height, 1);
		Graphics g = subImage.getGraphics();
		
		//������ֱ߽�����  ��4�����
		if (subImageBounds.x < 0||
				subImageBounds.y<0||
				(image.getHeight()-subImageBounds.y)<subImageBounds.height||
				(image.getWidth()-subImageBounds.x)<subImageBounds.width
				) {
			int left=0,//��ͼ��ƫ��λ��
				top=0,//��ͼ��ƫ��λ��	
				x=subImageBounds.x,	//	ԭͼ��ƫ��λ
				y=subImageBounds.y, //	ԭͼ��ƫ��λ
				width=subImageBounds.width,		//	ԭͼ����ȡ�Ŀ��
				height=subImageBounds.height;   //	ԭͼ����ȡ�ĸ߶�
			
			if(subImageBounds.x < 0){//ͼƬ����߽� δ�ﵽ ��ͼ�����߽�
				left = -subImageBounds.x;////��ͼ����ߵ�ƫ��λ�ã��Ӹ�ƫ��λ�ÿ�ʼ��ͼ
				if(left>subImageBounds.width){//��� ��ͼ����ߵ�ƫ��λ�� �����ұ߽�
					left=subImageBounds.width;//��ͼ����ߵ�ƫ��λ�õ��ڽ�ͼ��Ŀ�
				}
				x=0;//ͼƬ������߿�ʼ��ͼ
				width=subImageBounds.width+subImageBounds.x;//ͼƬ���صĿ�ȵ��� ��ͼ��ĳ��ȼ�ȥƫ�Ƶĳ���
				if(width>image.getWidth()){//�����������صĿ�ȴ���ͼƬ����Ŀ��
					width = image.getWidth();//ͼƬ���صĿ�ȵ���ͼƬ�Ŀ��
				}else if(width<=0){//�����������صĿ��С��0
					width=1;//��ȵ���1. ���Ϊ0Ҫ����
				}
			}else if((image.getWidth()-subImageBounds.x)<subImageBounds.width){//ͼƬ���ұ߽� δ�ﵽ��ͼ����ұ߽�
				left=0;//��ͼ�������ʼλ�ÿ�ʼ��ͼ
				x=subImageBounds.x;//ͼƬ�����ƫ��ȿ�ʼ��ͼ
				if(x-image.getWidth()>0){//���ͼƬ��߿�ʼ��ƫ��ȱ�ͼƬ��Ȼ�Ҫ��
					x = image.getWidth();//ƫ��ȵ���ͼƬ���
				}
				width=image.getWidth()-subImageBounds.x;//ͼƬ��ͼ�Ŀ�ȵ���ͼƬ�Ŀ�ȼ�ȥƫ���
				if(width<=0){//������ص�ͼƬ�Ŀ��С��0
					width=1;//���Ϊ1
					x=x-1;//ƫ������ؼ�һ
				}
			}
			if(subImageBounds.y<0){//ͼƬ���ϱ߽� δ�ﵽ ��ͼ����ϱ߽�
				top = -subImageBounds.y;
				if(top>subImageBounds.height){
					top=subImageBounds.height;
				}
				y=0;
				height=subImageBounds.height+subImageBounds.y;
				if(height>image.getHeight()){
					height = image.getHeight();
				}else if(height<=0){
					height=1;
				}
			}else if((image.getHeight()-subImageBounds.y)<subImageBounds.height){//ͼƬ���±߽� δ�ﵽ ��ͼ����±߽�
				top=0;
				y=subImageBounds.y;
				if(y-image.getHeight()>0){
					y=image.getHeight();
				}
				height=image.getHeight()-subImageBounds.y;
				if(height<=0){
					height=1;
					y=y-1;
				}
			}
			
			g.setColor(Color.white);
			g.fillRect(0, 0, subImageBounds.width, subImageBounds.height);
			g.drawImage(image.getSubimage(x, y, width, height), left, top, null);
		} else {
			g.drawImage(image.getSubimage(subImageBounds.x, subImageBounds.y,
					subImageBounds.width, subImageBounds.height), 0, 0, null);
		}
		g.dispose();
		//������Աͷ��
		if("update".equals(isupdate)){
			saveImg(subImage,filename,"jpg",path,isupdate);
			return "";
		}
		String base64 = saveImg(subImage,filename,"jpg",path,isupdate);
		//ImageIO.write(subImage, formatName, subImageFile);
		return base64;
	}

	private static String saveImg(BufferedImage image, String fileName,String formatName,String path,
			String isupdate) {
		String photourl = path + File.separator;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if(formatName==null||"".equals(formatName)){
			formatName = "jpg";
		}
		String base64 = "";
		try {
			fileName = fileName+"."+formatName;
			photourl = photourl+fileName;
			
			
			File fileD = new File(photourl);
			if(!fileD.isDirectory()){
				fileD.mkdirs();
			}
			File fileF = new File(photourl+fileName);
			if("update".equals(isupdate)){
				ImageIO.write(image, formatName, fileF);
				return "";
			}
			ImageIO.write(image, formatName, out);
			//base64 = phototobase64(out.toByteArray());
			
			
			
		}  catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return base64;
	}

	
	public static String cut(String srcImageFile,  String filename,String path,String isupdate) throws IOException {
		Image image = ImageIO.read(new File(srcImageFile));
		Rectangle rect = new Rectangle(0, 0, 272, 340);
		int width = 272;
		int height = 340;
		
		BufferedImage bImage = makeThumbnail(image, width, height);
		if("update".equals(isupdate)){
			saveSubImage(bImage, rect, filename, path,isupdate);
			return "";
		}
		String bas64 = saveSubImage(bImage, rect,filename,path,isupdate);
		return bas64;
	}
	
	public String ClobToString(Clob clob) throws SQLException, IOException { 
    	
        String reString = ""; 
        java.io.Reader is = clob.getCharacterStream();// �õ��� 
        BufferedReader br = new BufferedReader(is); 
        String s = br.readLine(); 
        StringBuffer sb = new StringBuffer(); 
        while (s != null) {// ִ��ѭ�����ַ���ȫ��ȡ����ֵ��StringBuffer��StringBufferת��STRING 
            sb.append(s); 
            s = br.readLine(); 
        } 
        reString = sb.toString(); 
        return reString; 
    }
	
	@SuppressWarnings({ "unchecked", "static-access" })
	@PageEvent("export")
	public int exportLrmx(String meetingid,String path,String userid) throws RadowException{
		
		//û��ѡ�в���ȫ��
		HBSession sess = HBUtil.getHBSession();
		String sql="select a0000,sh000 from hz_sh_a01 where a0000 is not null and a0000 in (select a0000 from a01) and publishid in (select publishid from publish where meetingid in ("+meetingid+")  and (userid='"+userid+"' or publishid in (select publishid from publishuser where (islook='1' or ischange='1') and userid='"+userid+"')))"
				+ " union select a.a0000,a.sh000 from hz_sh_a01 a,personcite b where a.sh000=b.sh000 and a.a0000 is not null and a.a0000 in (select a0000 from a01) and b.publishid_new in (select publishid from publish where meetingid in ("+meetingid+")  and (userid='"+userid+"' or publishid in (select publishid from publishuser where (islook='1' or ischange='1') and userid='"+userid+"')))";
		CommQuery commQuery =new CommQuery();
		List<HashMap<String, Object>> list;
		try {
			list = commQuery.getListBySQL(sql);
			String a0000 = "";
			String sh000 = "";
			if(list !=null && list.size() > 0){
				String zippath =path+"sh_lrmx/";
				File f = new File(zippath);
				if(!f.isDirectory()){
					f.mkdirs();
				}
				for(int j = 0;j < list.size(); j++){
					a0000 = list.get(j).get("a0000").toString();
					sh000 = list.get(j).get("sh000").toString();
					PersonXml per = Parenthesisprocessing(a0000,"",sh000);
					try {
						FileUtil.createFile(zippath+sh000+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
						A01 a01log = new A01();
						new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX����", new Map2Temp().getLogInfo(new A01(), a01log));
					} catch (Exception e) {
						
					}
				}
			}

		} catch (AppException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	// ת���������
	public PersonXml Parenthesisprocessing(String a0000, String time,String sh000) {
		PersonXml per = createLrmxStr(a0000, time,sh000);
		if (per.getJianLi() != null && per.getJianLi() != "") {
			per.setJianLi(per.getJianLi().replaceAll("&lt;", "&1lt;"));
			per.setJianLi(per.getJianLi().replaceAll("&gt;", "&1gt;"));
			per.setJianLi(per.getJianLi().replaceAll("&amp;", "&1amp;"));
		}
		if (per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() != null
				&& per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() != "") {
			per.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll("&", "&1amp;"));
			per.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll("<", "&1lt;"));
			per.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll(">", "&1gt;"));
		}
		if (per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi() != null
				&& per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi() != "") {
			per.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi().replaceAll("&", "&1amp;"));
			per.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi().replaceAll("<", "&1lt;"));
			per.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi().replaceAll(">", "&1gt;"));
		}
		if (per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() != null && per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() != "") {
			per.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll("&", "&1amp;"));
			per.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll("<", "&1lt;"));
			per.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll(">", "&1gt;"));
		}
		return per;
	}
	
	public PersonXml createLrmxStr(String ids,String time,String sh000){
		String a0000 = ids;
		PersonXml a = new PersonXml();
		JiaTingChengYuanXml jiaTingChengYuanXml=new JiaTingChengYuanXml();
		List<JiaTingChengYuanXml> jtcyList = new ArrayList<JiaTingChengYuanXml>();
		List<ItemXml> itemlist = new ArrayList<ItemXml>();
		HBSession sess = HBUtil.getHBSession();
		String sqla36 = "from A36 where a0000='"+a0000+"' order by sortid,"
				  + "case " +
					"        when A3604A='�ɷ�' or A3604A='����' then 1 " +
					"        when A3604A='����' or A3604A='Ů��'or A3604A='��Ů'or A3604A='����' or A3604A='��Ů' or A3604A='����' or A3604A='��Ů' or A3604A='����' or A3604A='��Ů' or A3604A='����' or A3604A='��Ů' or A3604A='����' or A3604A='����Ů��' or A3604A='������' then 2 " +
					"        when A3604A='����'  then 3 " +
					"        when A3604A='ĸ��'  then 4 " +
					"        when A3604A='�̸�'  then 5 " +
					"        when A3604A='��ĸ'  then 6 " +
					"      end   , " +
		 			" case "+
		            "    when "+
		            "      A3604A='����' or A3604A='Ů��' or A3604A='��Ů'  or "+
		            "      A3604A='����' or A3604A='��Ů' or A3604A='����'  or "+
		            "      A3604A='��Ů' or A3604A='����' or A3604A='��Ů'  or "+
		            "      A3604A='����' or A3604A='��Ů' or A3604A='����'  or "+
		            "      A3604A='����Ů��' or A3604A='������' "+
			        "    then   "+
			        "      to_number(GETAGE(A3607)) "+
		            "    end"+
		            "        desc";
		List lista36 = sess.createQuery(sqla36).list();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		a01.setQrzxlxx(a01.getQrzxlxx()!= null ? a01.getQrzxlxx() : "");
		if(a01.getQrzxlxx().equals(a01.getQrzxwxx())) {
			a01.setQrzxwxx(null);
		}
		a01.setZzxlxx(a01.getZzxlxx()!= null ? a01.getZzxlxx() : "");
		if(a01.getZzxlxx().equals(a01.getZzxwxx())) {
			a01.setZzxwxx(null);
		}
		
		A57 a57 = (A57)sess.get(A57.class, a0000);
		String sqla53 = "from A53 where a0000='"+a0000+"' ";
		List<A53> list = sess.createQuery(sqla53).list();
		Object xb = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB2261' and t.code_value = '"+a01.getA0104()+"'").uniqueResult();
		Object mz = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB3304' and t.code_value = '"+a01.getA0117()+"'").uniqueResult();
		if(a57!=null){
	    	byte[] data = PhotosUtil.getPhotoData(a57);
			if(data!=null){
				a.setZhaoPian(data);
			}
	    }
		a.setXingMing(a01.getA0101());
		a.setXingBie(xb!=null?xb.toString():"");
		a.setChuShengNianYue(a01.getA0107().length()>6?a01.getA0107().substring(0, 6):a01.getA0107());
		a.setMinZu(mz!=null?mz.toString():"");
		a.setJiGuan(a01.getComboxArea_a0111());
		a.setChuShengDi(a01.getComboxArea_a0114()!= null ? a01.getComboxArea_a0114() : "");
		a.setRuDangShiJian(a01.getA0140()!= null ? a01.getA0140() : "");
		a.setCanJiaGongZuoShiJian(a01.getA0134().length()>6?a01.getA0134().substring(0, 6):a01.getA0134());
		a.setJianKangZhuangKuang(a01.getA0128()!= null ? a01.getA0128() : "");
		a.setZhuanYeJiShuZhiWu(a01.getA0196()!= null ? a01.getA0196() : "");
		a.setShuXiZhuanYeYouHeZhuanChang(a01.getA0187a()!= null ? a01.getA0187a() : "");
		a.setQuanRiZhiJiaoYu_XueLi(a01.getQrzxl()!= null ? a01.getQrzxl() : "");
		a.setQuanRiZhiJiaoYu_XueWei(a01.getQrzxw()!= null ? a01.getQrzxw() : "");
		a.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getQrzxlxx()!= null ? a01.getQrzxlxx() : "");
		a.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getQrzxwxx()!= null ? a01.getQrzxwxx() : "");
		a.setZaiZhiJiaoYu_XueLi(a01.getZzxl()!= null ? a01.getZzxl() : "");
		a.setZaiZhiJiaoYu_XueWei(a01.getZzxw()!= null ? a01.getZzxw() : "");
		a.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getZzxlxx()!= null ? a01.getZzxlxx() : "");
		a.setZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getZzxwxx()!= null ? a01.getZzxwxx() : "");
//		a.setXianRenZhiWu(a01.getA0192a()!= null ? a01.getA0192a() : "");
		a.setJianLi(a01.getA1701()!= null ? a01.getA1701() : "");
		a.setTianBiaoRen(a01.getCbdw()!= null ? a01.getCbdw() : "");
		/*
		 * hy
		 * ��Ҫ���޹�Ϊ�գ���Ϊ(��)
		 */
		if(a01.getA14z101()==null||a01.getA14z101().equals("��")||a01.getA14z101().isEmpty())
			a.setJiangChengQingKuang("(��)");
		else
			a.setJiangChengQingKuang(a01.getA14z101());
		if(a01.getA15z101()==null||a01.getA15z101().isEmpty())
			a.setNianDuKaoHeJieGuo("(��)");
		else
			a.setNianDuKaoHeJieGuo(a01.getA15z101());
		a.setShenFenZheng(a01.getA0184());
//		if(list==null||list.size()==0){
//			a.setNiRenZhiWu("");
//			a.setNiMianZhiWu("");
//			a.setRenMianLiYou("");
//			a.setChengBaoDanWei("");
//			a.setJiSuanNianLingShiJian("");
//			a.setTianBiaoShiJian(time);
//			a.setTianBiaoRen("");
//		}else{
//			List lista53 = sess.createQuery(sqla53).list();
//			A53 a53 = (A53)lista53.get(0);
//			a.setNiRenZhiWu(a53.getA5304());
//			a.setNiMianZhiWu(a53.getA5315());
//			a.setRenMianLiYou(a53.getA5317());
//			a.setChengBaoDanWei(a53.getA5319());
//			a.setJiSuanNianLingShiJian("");
//			a.setTianBiaoShiJian(time);
//			a.setTianBiaoRen("");
//		}
		if(lista36!=null&&lista36.size()>0){
			for(int i=1;i<=lista36.size();i++){
				ItemXml b = new ItemXml();
				A36 a36 = (A36) lista36.get(i - 1);
				//Object cw = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4761' and t.code_value = '"+a36.getA3604a()+"'").uniqueResult();
				b.setChengWei(a36.getA3604a() != null ? a36.getA3604a() : "");
				b.setChuShengRiQi(lengthToSix(a36.getA3607()));
				b.setGongZuoDanWeiJiZhiWu(a36.getA3611());
				b.setXingMing(a36.getA3601());
				//Object zzmm = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4762' and t.code_value = '"+a36.getA3627()+"'").uniqueResult();
				b.setZhengZhiMianMao(a36.getA3627() != null ? a36.getA3627() : "");
				itemlist.add(b);
			}
		}
		String sql="select * from hz_sh_a01 where sh000='"+sh000+"' ";
		CommQuery commQuery =new CommQuery();
		try {
			List<HashMap<String, Object>> list_sh = commQuery.getListBySQL(sql);
			if(list_sh!=null&&list_sh.size()>0) {
				HashMap<String, Object> map_sh=list_sh.get(0);
				String tp0111=map_sh.get("tp0111")==null?"":map_sh.get("tp0111").toString();
				String tp0112=map_sh.get("tp0112")==null?"":map_sh.get("tp0112").toString();
				String tp0121=map_sh.get("tp0121")==null?"":map_sh.get("tp0121").toString();
				String tp0122=map_sh.get("tp0122")==null?"":map_sh.get("tp0122").toString();
				String tp0113=map_sh.get("tp0113")==null?"":map_sh.get("tp0113").toString();
				String a0192a=map_sh.get("a0192a")==null?"":map_sh.get("a0192a").toString();
				a.setXianRenZhiWu(a0192a);
				if(!"".equals(tp0111)) {
					if("2".equals(tp0121)) {
						tp0111=tp0111+"����ְ��";
					}else if("3".equals(tp0121)) {
						tp0111="����"+tp0111;
					}else if("4".equals(tp0121)) {
						tp0111="����ת��";
					}
				}else if("".equals(tp0111)&&("3".equals(tp0122)||"4".equals(tp0122))) {
					tp0111="��������";
				}else if("".equals(tp0111)&&"4".equals(tp0121)) {
					tp0111="����ת��";
				}else {
					tp0111="";
				}
				if("2".equals(tp0122)||"3".equals(tp0122)) {
					if("".equals(tp0112)) {
						tp0112= map_sh.get("a0192a").toString();
					}
				}
				a.setNiRenZhiWu(tp0111);
				a.setNiMianZhiWu(tp0112);
				a.setRenMianLiYou(tp0113);
			}else {
				a.setNiRenZhiWu("");
				a.setNiMianZhiWu("");
				a.setRenMianLiYou("");
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jiaTingChengYuanXml.setItem(itemlist);
		jtcyList.add(jiaTingChengYuanXml);
		a.setJiaTingChengYuan(jtcyList);
		return a;
	}
	
	private String lengthToSix(String str) {
		if(str!=null&&!"".equals(str)) {
			if(str.length()==8) {
				String dateStr = str.substring(0, 6);
				return dateStr;
			}else {
				return str;
			}
		}
		return "";
	}
	
	           
	public static boolean getLicense() {
		boolean result = false;
		try {
			InputStream is = TestAspose2Pdf.class.getClassLoader()
					.getResourceAsStream("Aspose.Words.lic"); // license.xmlӦ����..\WebRoot\WEB-INF\classes·����
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result = true;
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}             
	/**    
	* @Description: word��txt�ļ�ת��ͼƬ
	*/ 
	private static List<BufferedImage> wordToImg(InputStream inputStream, int pageNum, String imgPath) throws Exception {
		if (!getLicense()) {
			return null;
		} 
		try {
			long old = System.currentTimeMillis();
			Document doc = new Document(inputStream); 
			ImageSaveOptions options = new ImageSaveOptions(SaveFormat.JPEG); 
			options.setPrettyFormat(true);
			options.setUseAntiAliasing(true);
			options.setUseHighQualityRendering(true); 
			int pageCount = doc.getPageCount();  
			if (pageCount > pageNum) {
				//����ǰpageCount�� 
				pageCount = pageNum; 
			}           
			List<BufferedImage> imageList = new ArrayList<BufferedImage>(); 
			for (int i = 0; i < pageCount; i++) {
				OutputStream output = new ByteArrayOutputStream();
				options.setPageIndex(i); 
				//options.setResolution(132);
				options.setScale(4);
				StopWatch w = new StopWatch();
				w.start();
				doc.save(output, options); 
				w.stop();
				System.out.println("ת��ͼƬ�����"+i+"��"+w.elapsedTime());
				//ImageInputStream imageInputStream = javax.imageio.ImageIO.createImageInputStream(parse(output)); 
				w.start();
				imageList.add(javax.imageio.ImageIO.read(parse(output)));
				w.stop();
				System.out.println("ת��ͼƬ������"+i+"��"+w.elapsedTime());
			}       
			return imageList;
		} catch (Exception e) {
			e.printStackTrace(); 
			throw e;  
		}   
	} 
	/**    
	* @Description: word��txt�ļ�ת��ͼƬ
	*/ 
	private static List<BufferedImage> wordToImg3(InputStream inputStream, int pageNum, String imgPath) throws Exception {
		if (!getLicense()) {
			return null;
		} 
		try {
			Document doc = new Document(inputStream); 
			//int pageCount = doc.getPageCount();  
			ImageSaveOptions options = new ImageSaveOptions(SaveFormat.SVG); 
			options.setPrettyFormat(true);
			options.setUseAntiAliasing(true);
			options.setUseHighQualityRendering(true); 
			
			/*if (pageCount > pageNum) {
				//����ǰpageCount�� 
				pageCount = pageNum; 
			}         
			options.setPageCount(pageCount);*/
			List<BufferedImage> imageList = new ArrayList<BufferedImage>(); 
			OutputStream output = new FileOutputStream(imgPath);
			
			options.setScale(1);
			doc.save(output, options); 
			output.close();         
			return imageList;
		} catch (Exception e) {
			e.printStackTrace(); 
			throw e;  
		}   
	} 
	/**    
	* @Description: word��txt�ļ�ת��ͼƬ
	*/ 
	private static List<BufferedImage> wordToImg2(InputStream inputStream, int pageNum, String imgPath) throws Exception {
		if (!getLicense()) {
			return null;
		} 
		try {
			long old = System.currentTimeMillis();
			Document doc = new Document(inputStream); 
			ImageSaveOptions options = new ImageSaveOptions(SaveFormat.PNG); 
			options.setPrettyFormat(true);
			options.setUseAntiAliasing(true);
			options.setUseHighQualityRendering(true); 
			int pageCount = doc.getPageCount();  
			if (pageCount > pageNum) {
				//����ǰpageCount�� 
				//pageCount = pageNum; 
			}           
			List<BufferedImage> imageList = new ArrayList<BufferedImage>(); 
			OutputStream output;
			for (int i = 0; i < pageCount; i++) {
				output = new FileOutputStream(imgPath+(i+1)+".png");
				options.setPageIndex(i); 
				//options.setResolution(132);
				options.setScale(4);
				doc.save(output, options); 
				output.close();
				//ImageInputStream imageInputStream = javax.imageio.ImageIO.createImageInputStream(parse(output)); 
				//imageList.add(javax.imageio.ImageIO.read(imageInputStream));
			}           
			return imageList;
		} catch (Exception e) {
			e.printStackTrace(); 
			throw e;  
		}   
	} 
	//outputStreamתinputStream    
	public static ByteArrayInputStream parse(OutputStream out) throws Exception { 
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		baos = (ByteArrayOutputStream) out; 
		ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray()); 
		return swapStream;    
	}   
	
	
	/**   
	* ����ͼƬ����
	*  
	* @param isHorizontal true����ˮƽ�ϲ���fasle����ֱ�ϲ�
	* @param imgs         ���ϲ���ͼƬ����    
	* @return    
	* @throws IOException 
	*/    
	public static BufferedImage updateImage(boolean isHorizontal, BufferedImage img) throws IOException { 
		// ������ͼƬ   
		BufferedImage destImage = null; 
		// ������ͼƬ�ĳ��͸� 
		int allw = 0, allh = 0, allwMax = 0, allhMax = 0; 
		// ��ȡ�ܳ����ܿ�������     
		allw += img.getWidth();        
		allh += img.getHeight();      
		if (img.getWidth() > allwMax) {  
			allwMax = img.getWidth();     
		}   
		if (img.getHeight() > allhMax) { 
			allhMax = img.getHeight();  
		}     
		// ������ͼƬ 
		if (isHorizontal) {  
			destImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);  
		} else {  
			destImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB); 
		}     
		Graphics2D g2 = (Graphics2D) destImage.getGraphics(); 
		g2.setBackground(Color.LIGHT_GRAY);  
		g2.clearRect(0, 0, allw, allh);   
		g2.setPaint(Color.RED);  
		// �ϲ�������ͼƬ����ͼƬ  
		int wx = 0, wy = 0;    
		int w1 = img.getWidth();    
		int h1 = img.getHeight();
		// ��ͼƬ�ж�ȡRGB   
		int[] ImageArrayOne = new int[w1 * h1];  
		ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // ����ɨ��ͼ���и������ص�RGB�������� 
		if (isHorizontal) { // ˮƽ����ϲ� 
			destImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1); // �����ϰ벿�ֻ���벿�ֵ�RGB 
		} else { // ��ֱ����ϲ�  
			destImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1); // �����ϰ벿�ֻ���벿�ֵ�RGB 
		}    
		wx += w1;    
		wy += h1 + 5;   
		return destImage; 
	} 
	
	
	/**   
	* �ϲ���������ͼƬ��һ��ͼƬ
	*  
	* @param isHorizontal true����ˮƽ�ϲ���fasle����ֱ�ϲ�
	* @param imgs         ���ϲ���ͼƬ����    
	* @return    
	* @throws IOException 
	*/    
	public static BufferedImage mergeImage(boolean isHorizontal, List<BufferedImage> imgs) throws IOException { 
		// ������ͼƬ   
		BufferedImage destImage = null; 
		// ������ͼƬ�ĳ��͸� 
		int allw = 0, allh = 0, allwMax = 0, allhMax = 0; 
		// ��ȡ�ܳ����ܿ������� 
		for (int i = 0; i < imgs.size(); i++) { 
			BufferedImage img = imgs.get(i);    
			allw += img.getWidth();    
			if (imgs.size() != i + 1) {  
				allh += img.getHeight() + 5; 
			} else {     
				allh += img.getHeight(); 
			}        
			if (img.getWidth() > allwMax) {  
				allwMax = img.getWidth();     
			}   
			if (img.getHeight() > allhMax) { 
				allhMax = img.getHeight();  
			}    
		}     
		// ������ͼƬ 
		if (isHorizontal) {  
			destImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);  
		} else {  
			destImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB); 
		}     
		Graphics2D g2 = (Graphics2D) destImage.getGraphics(); 
		g2.setBackground(Color.LIGHT_GRAY);  
		g2.clearRect(0, 0, allw, allh);   
		g2.setPaint(Color.RED);  
		// �ϲ�������ͼƬ����ͼƬ  
		int wx = 0, wy = 0;   
		for (int i = 0; i < imgs.size(); i++) { 
			BufferedImage img = imgs.get(i);  
			int w1 = img.getWidth();    
			int h1 = img.getHeight();
			// ��ͼƬ�ж�ȡRGB   
			//int[] ImageArrayOne = new int[w1 * h1];  
			//ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // ����ɨ��ͼ���и������ص�RGB�������� 
			if (isHorizontal) { // ˮƽ����ϲ� 
				//destImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1); // �����ϰ벿�ֻ���벿�ֵ�RGB 
			} else { // ��ֱ����ϲ�  
				//destImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1); // �����ϰ벿�ֻ���벿�ֵ�RGB 
				g2.drawImage(img, 0, wy, null);
			}    
			wx += w1;    
			wy += h1 + 5;    
		}     
		return destImage; 
	}       
	
	
	public int pdfView2(String docPath,String savePath) throws RadowException, AppException{		
		TestAspose2Pdf.doc2pdf(docPath,savePath);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
//	public static void pdf2ImageDemo(String PdfFilePath, String dstImgFolder, int dpi) {	
//		File file = new File(PdfFilePath);
//		PDDocument pdDocument;	
//		try {		
//			String imgPDFPath = file.getParent();
//			int dot = file.getName().lastIndexOf('.');
//			// ��ȡͼƬ�ļ���		
//			String imagePDFName = file.getName().substring(0, dot); 
//			String imgFolderPath = null;		
//			if (dstImgFolder.equals("")) {	
//				// ��ȡͼƬ��ŵ��ļ���·��	
//				imgFolderPath = imgPDFPath + File.separator + imagePDFName;	
//			} else {		
//				imgFolderPath = dstImgFolder + File.separator + imagePDFName;
//			} 		
//			if (createDirectory(imgFolderPath)) {	
//				pdDocument = PDDocument.load(file);	
//				PDFRenderer renderer = new PDFRenderer(pdDocument);
//				PdfReader reader = new PdfReader(PdfFilePath);		
//				int pages = reader.getNumberOfPages();// ��ȡPDFҳ��	
//				System.out.println("PDF page number is:" + pages);		
//				StringBuffer imgFilePath = null;			
//				for (int i = 0; i < pages; i++) {			
//					String imgFilePathPrefix = imgFolderPath			
//					+ File.separator + imagePDFName;				
//					imgFilePath = new StringBuffer();			
//					imgFilePath.append(imgFilePathPrefix);				
//					imgFilePath.append("_");				
//					imgFilePath.append(String.valueOf(i + 1));			
//					imgFilePath.append(".png");// PNG			
//					File dstFile = new File(imgFilePath.toString());	
//					BufferedImage image = renderer.drawImage(image)
//					ImageIO.write(image, "png", dstFile);// PNG		
//				}			
//				System.out.println("PDF�ĵ�תPNGͼƬ�ɹ���");	
//			} else {	
//				System.out.println("PDF�ĵ�תPNGͼƬʧ�ܣ�"+ "����" + imgFolderPath + "ʧ��");	
//			}	
//		} catch (IOException e) {	
//			e.printStackTrace();	
//		}	
//	}
//
//	private static boolean createDirectory(String folder) {	
//		File dir = new File(folder);
//		if (dir.exists()) {	
//			return true;	
//		} else {	
//			return dir.mkdirs();		
//		}	
//	}
	
//	public   static   void  setup()  throws  IOException {   
//		   
//        // load a pdf from a byte buffer    
//        File file = new  File(   
//                "C:/Users/86183/Desktop/222.pdf" );   
//        RandomAccessFile raf = new  RandomAccessFile(file,  "r" );   
//        FileChannel channel = raf.getChannel();   
//        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0 , channel   
//                .size());   
//        PDFFile pdffile = new  PDFFile(buf);   
//   
//        System.out.println("ҳ���� "  + pdffile.getNumPages());   
//   
//        for  ( int  i =  1 ; i <= pdffile.getNumPages(); i++) {   
//            // draw the first page to an image    
//            PDFPage page = pdffile.getPage(i);   
//   
//            // get the width and height for the doc at the default zoom    
//            Rectangle rect = new  Rectangle( 0 ,  0 , ( int ) page.getBBox()   
//                    .getWidth(), (int ) page.getBBox().getHeight());   
//   
//            // generate the image    
//            Image img = page.getImage(rect.width, rect.height, // width &    
//                                                                // height    
//                    rect, // clip rect    
//                    null ,  // null for the ImageObserver    
//                    true ,  // fill background with white    
//                    true   // block until drawing is done    
//                    );   
//   
//            BufferedImage tag = new  BufferedImage(rect.width, rect.height,   
//                    BufferedImage.TYPE_INT_RGB);   
//            tag.getGraphics().drawImage(img, 0 ,  0 , rect.width, rect.height,   
//                    null );   
//            FileOutputStream out = new  FileOutputStream(   
//                    "C:/Users/86183/Desktop/ceshi/"    
//                            + i + ".png" );  // ������ļ���    
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
//            encoder.encode(tag); // JPEG����    
//   
//            out.close();   
//        }     
//    }  
	
}
