package com.insigma.siis.local.pagemodel.customquery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.codec.binary.Base64;
import org.freehep.graphicsio.swf.SWFAction.TargetPath;
import org.hibernate.jdbc.ResultSetWrapper;

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
import com.insigma.siis.local.business.utils.DataToDB;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SQLiteUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil2;
import com.insigma.siis.local.business.utils.Xml4Zb3Util;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.util.TYGSsqlUtil;

/**
 *
 * @author 15084
 *
 */

public class DataPsnImpDBThread implements Runnable {
	
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
	private String allSelect;
	private String sessionid;
	 
	
    public DataPsnImpDBThread(String uuid, CurrentUser user,String gjgs,String ltry,String gzlbry
    		,String gllbry,String searchDeptid,String linkpsn,String linktel,
    		String remark,String gz_lb,String gl_lb,String tabimp,String zdcjg,UserVO userVo,String ids,String allSelect,String sessionid) {
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
        this.remark = remark;//未用到，暂用于zip标记
        this.gz_lb = gz_lb;
        this.gl_lb = gl_lb;
        this.tabimp = tabimp;
        this.userVo = userVo;
        this.ids = ids;
        this.allSelect=allSelect;
        this.sessionid=sessionid;
    }
    
	@Override
	public void run() {
		Map<String, String> map = new HashMap<String, String>();
		String infile = "";										// 文件
		String process_run = "1";								// 过程序号
		String path = "";
		//创建临时表exportzip_idstemp存ids
		String idsTempTableName="idstemp"+System.currentTimeMillis();
		String tempTable="b0111_temp_"+System.currentTimeMillis();
		
		//拼接机构编码
		StringBuffer A0195S=new StringBuffer();
		//C79.E19.P59.015.020
		//机构编码，给不同地区打补丁需要更换
		String INSTITUTIONID="C79.E19.P59.015.020";
		A0195S.append("\'"+INSTITUTIONID+"\'");
		while(INSTITUTIONID.length()>4){
			INSTITUTIONID=INSTITUTIONID.substring(0, INSTITUTIONID.length()-4);
			A0195S.append(",");
			A0195S.append("\'"+INSTITUTIONID+"\'");
		}
		INSTITUTIONID="C79.E19.P59.015.020";//必须，后面程序使用
		
		
		try {
			CommonQueryBS.systemOut(DateUtil.getTime());
			
			//创建索引
			if(tabimp == null || tabimp.equals("") || tabimp.equals("1")){
				HBSession sess = HBUtil.getHBSession();
				
				String INSTITUTIONID_b0111=(String) sess.createSQLQuery("SELECT B0111 FROM `b01` WHERE b0114='"+INSTITUTIONID+"'").uniqueResult();
				
				try{
					sess.createSQLQuery("DROP INDEX IN_A01_A0195").executeUpdate();
					sess.createSQLQuery("CREATE INDEX IN_A01_A0195 ON A01(A0195)").executeUpdate();
					
					sess.createSQLQuery("DROP INDEX IN_A02_A0000").executeUpdate();
					sess.createSQLQuery("CREATE INDEX IN_A02_A0000 ON A02(A0000)").executeUpdate();
					
					sess.createSQLQuery("DROP INDEX IN_A05_A0000").executeUpdate();
					sess.createSQLQuery("CREATE INDEX IN_A05_A0000 ON A05(A0000)").executeUpdate();
					
					sess.createSQLQuery("DROP INDEX IN_A08_A0000").executeUpdate();
					sess.createSQLQuery("CREATE INDEX IN_A08_A0000 ON A08(A0000)").executeUpdate();
					
					sess.createSQLQuery("DROP INDEX IN_A11_A0000").executeUpdate();
					sess.createSQLQuery("CREATE INDEX IN_A11_A0000 ON A11(A0000)").executeUpdate();
					
					sess.createSQLQuery("DROP INDEX IN_A14_A0000").executeUpdate();
					sess.createSQLQuery("CREATE INDEX IN_A14_A0000 ON A14(A0000)").executeUpdate();
					
					sess.createSQLQuery("DROP INDEX IN_A15_A0000").executeUpdate();
					sess.createSQLQuery("CREATE INDEX IN_A15_A0000 ON A15(A0000)").executeUpdate();
					
					sess.createSQLQuery("DROP INDEX IN_A36_A0000").executeUpdate();
					sess.createSQLQuery("CREATE INDEX IN_A36_A0000 ON A36(A0000)").executeUpdate();
					
					sess.createSQLQuery("DROP INDEX IN_A57_A0000").executeUpdate();
					sess.createSQLQuery("CREATE INDEX IN_A57_A0000 ON A57(A0000)").executeUpdate();
				}catch(Exception e){
					
				}
				
				if(!ids.startsWith("select")){//选择的时候
					Connection idsConn=sess.connection();
					Statement state=idsConn.createStatement();
					String createIdsSql="create table "+idsTempTableName+" "
							+ "(A0000 varchar(120) NOT NULL,"
							+ "PRIMARY KEY (A0000))";
					System.out.println(createIdsSql);
					state.execute(createIdsSql);
					state.close();
					String[] s = ids.split(",");
					for(int i=0;i<s.length;i++){
						String idStr=s[i];
						System.out.println("insert into "+idsTempTableName+"(A0000) values("+idStr+")");
						HBUtil.executeUpdate("insert into "+idsTempTableName+"(A0000) values("+idStr+")");
					}
					ids="select A0000 from "+idsTempTableName;
				}
				String countSql="select count(1) from ";
				Object obj=sess.createSQLQuery(countSql+"("+ids+") tmp1").uniqueResult();
				System.out.println(countSql+"("+ids+") as tmp1");
				int idsCount=Integer.parseInt((sess.createSQLQuery(countSql+"("+ids+") tmp1").uniqueResult()+""));
				
				
				//按人员导出前的校验工作
				KingbsconfigBS.saveImpDetail(process_run,"1","正在进行数据校验",uuid);						//记录校验过程
				if(idsCount==0){
					ids=allSelect;
					//throw new RuntimeException("超过"+CommSQL.MAXROW+"不能导出！");
				}
				
				
				//1、机构编码不能为空
				/*String sqla = "";
				if(DBType.ORACLE == DBUtil.getDBType()){
					sqla = "select count(1) from b01 where 1 = 1 and exists (select 1 from A02 where a0000 in(" + ids + ") and A02.a0201b = b01.b0111) and b0114 is null and b0111 != '-1'";
				}else{
					sqla = "select count(1) from b01 where b0111 in (select a0201b from A02 where a0000 in(" + ids + ")) and b0111 != '-1' and (b0114 is null or b0114 = '')";
				}
				Object obj1 = sess.createSQLQuery(sqla).uniqueResult();
				if(obj1!=null){
					Integer one = Integer.parseInt(""+obj1);
					if(one > 0){
						throw new RuntimeException("发现人员任职机构编码为空");
					}
				}*/
				//2、机构编码不能重复
//				String sqlb = "";
//				if(DBType.ORACLE == DBUtil.getDBType()){
//					sqlb = "select count(1) from b01 where 1 = 1 and exists (select 1 from A02 where a0000 in(" + ids + ") and A02.a0201b = b01.b0111) and b0114 is null and b0111 != '-1'";
//				}else{
//					sqlb = "select count(1) from b01 where b0111 in (select a0201b from A02 where a0000 in(" + ids + ")) and b0111 != '-1' and (b0114 is null or b0114 = '')";
//				}
				
				//3、机构内人员身份证不能为空
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
						throw new RuntimeException("发现人员身份证为空");
					}
				}
				//------------      -------------//
				Object psn = sess.createSQLQuery("select count(*) from a01 where a0000 in(" + ids + ")").uniqueResult();
				List<Map> list17 = new ArrayList<Map>();
				map.put("type", "31");		//按人员导入数据
				map.put("time", DateUtil.timeToString(DateUtil.getTimestamp()));
				//??
				map.put("dataversion", "20171020");
				map.put("psncount", (psn!=null)? psn.toString() : "");
				map.put("photodir", "Photos");
				map.put("B0101", "");
				map.put("B0111", "");
				map.put("B0114", "");
				map.put("B0194", "");
				map.put("linkpsn", "");
				map.put("linktel", "");
				map.put("remark", "包含人员为" +((psn!=null)? psn.toString() : "") + "。");
				list17.add(map);
				path = getPath();
				String zippath = path + "按人员导出文件_" + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +"/";
				String name = "按人员导出文件_" + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss");
				name = name + ".zip";
				//--------------------------------------------------------------------
				//详情页面文件名称
				String time = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
				String sql1 = "insert into EXPINFO (ID,NAME,STARTTIME,CREATEUSER,STATUS,PSNCOUNT) values ('"+uuid+"','"+name+"','"+time+"','"+user.getId()+"','文件生成中请稍候...','"+""+psn+"')";
				sess.createSQLQuery(sql1).executeUpdate();
				//------------------------------------------------------------------------
				File file =new File(zippath);    
				//如果文件夹不存在则创建    
				if (!file.exists()){       
				    file.mkdirs();    
				}
				String zippathtable = zippath +"DB/";
				File file1 =new File(zippathtable);    
				//如果文件夹不存在则创建    
				if(!file1.exists()){       
				    file1 .mkdirs();    
				}
				String zipfile = path + "按人员导出文件_" + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss");
				zipfile = zipfile + ".zip";
				KingbsconfigBS.saveImpDetail("1","2","完成",uuid);						//记录导入过程
				process_run = "2";
				int number1 = 1;														//已解析表的树木
				//int number2 = 27;														//未解析标的树木
				int number2 = 12;
				
				System.out.println("A01开始："+DateUtil.timeToString(DateUtil.getTimestamp(), "HH:mm:ss"));
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A01数据生成处理",uuid);		//记录导入过程
				File sourceFile=new File(this.getClass().getClassLoader().getResource("./mdata.db").getPath());
				String filename=zippathtable+"mdata.db";
				File targetFile=new File(filename);
				SQLiteUtil.copyFile(sourceFile, targetFile);
				Connection sqliteConn=new SQLiteUtil(filename).getConnection();
				
				Connection conn = sess.connection();
				Statement stmt = null;
				stmt = conn.createStatement();
				ResultSet rs_a01 = null;
				rs_a01 = stmt.executeQuery("select * from a01 where a0000 in(" +ids + ") and a0195 in(SELECT b0111 FROM b01 WHERE b0114 in ("+A0195S+") OR B0111 LIKE '"+INSTITUTIONID_b0111+"%')");
				System.out.println("rs_a01查询到结果："+DateUtil.timeToString(DateUtil.getTimestamp(), "HH:mm:ss"));
				new DataToDB().insert(sqliteConn, rs_a01,"a01");
				rs_a01.close();
				stmt.close();
				System.out.println("A01结束："+DateUtil.timeToString(DateUtil.getTimestamp(), "HH:mm:ss"));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A02数据生成处理",uuid);		//记录导入过程
				Connection sqliteConnA02=new SQLiteUtil(filename).getConnection();
				stmt = conn.createStatement();
				ResultSet rs_a02 = null;
				rs_a02 = stmt.executeQuery("select * from A02 where a0000 in(" +ids + ") and A0201B in(SELECT b0111 FROM b01 WHERE b0114 in ("+A0195S+") OR b0111 LIKE '"+INSTITUTIONID_b0111+"%')");
				
				new DataToDB().insert(sqliteConnA02, rs_a02,"a02");
				rs_a02.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A05数据生成处理",uuid);		//记录导入过程
				Connection sqliteConnA05=new SQLiteUtil(filename).getConnection();
				stmt = conn.createStatement();
				ResultSet rs_a05 = null;
				rs_a05 = stmt.executeQuery("select * from A05 where a0000 in(" +ids + ")");
				new DataToDB().insert(sqliteConnA05, rs_a05,"a05");
				rs_a05.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A08数据生成处理",uuid);		//记录导入过程
				Connection sqliteConnA08=new SQLiteUtil(filename).getConnection();
				stmt = conn.createStatement();
				ResultSet rs_a08 = null;
				rs_a08 = stmt.executeQuery("select * from A08 where a0000 in(" +ids + ")");
				new DataToDB().insert(sqliteConnA08, rs_a08,"a08");
				rs_a08.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A11数据生成处",uuid);		//记录导入过程
				Connection sqliteConnA11=new SQLiteUtil(filename).getConnection();
				stmt = conn.createStatement();
				ResultSet rs_a11 = null;
				rs_a11 = stmt.executeQuery("select * from A11 where a0000 in(" +ids + ")");
				new DataToDB().insert(sqliteConnA11, rs_a11,"a11");
				rs_a11.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A14数据生成处理",uuid);		//记录导入过程
				Connection sqliteConnA14=new SQLiteUtil(filename).getConnection();
				stmt = conn.createStatement();
				ResultSet rs_a14 = null;
				rs_a14 = stmt.executeQuery("select * from A14 where a0000 in(" +ids + ")");
				new DataToDB().insert(sqliteConnA14, rs_a14,"a14");
				rs_a14.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A15数据生成处理",uuid);		//记录导入过程
				Connection sqliteConnA15=new SQLiteUtil(filename).getConnection();
				stmt = conn.createStatement();
				ResultSet rs_a15 = null;
				rs_a15 = stmt.executeQuery("select * from A15 where a0000 in(" +ids + ")");
				new DataToDB().insert(sqliteConnA15, rs_a15,"a15");
				rs_a15.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A36数据生成处理",uuid);		//记录导入过程
				Connection sqliteConnA36=new SQLiteUtil(filename).getConnection();
				stmt = conn.createStatement();
				ResultSet rs_A36 = null;
				rs_A36 = stmt.executeQuery("select * from a36 where a0000 in(" +ids + ")");
				new DataToDB().insert(sqliteConnA36, rs_A36,"a36");
				rs_A36.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A57数据生成处理",uuid);		//记录导入过程
				Connection sqliteConnA57=new SQLiteUtil(filename).getConnection();
				stmt = conn.createStatement();
				ResultSet rs_A57 = null;
				rs_A57 = stmt.executeQuery("select * from a57 where a0000 in(" +ids + ")");
				new DataToDB().insert(sqliteConnA57, rs_A57,"a57");
				rs_A57.close();
				stmt.close();
					
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：B01数据生成处理",uuid);		//记录导入过程
				
				Connection sqliteConnB01=new SQLiteUtil(filename).getConnection();
				stmt = conn.createStatement();
				String a0195sql="select a0195 from A01 WHERE a0000 IN("+ids+")  and a0195 in(SELECT b0111 FROM b01 WHERE b0114 in ("+A0195S+") OR b0111 LIKE '"+INSTITUTIONID_b0111+"%')";
				System.out.println(a0195sql);
				ResultSet rs_a0195 = stmt.executeQuery(a0195sql);
				//将机构编码个数小于1000存入字符串中，大于1000存入临时表中
				Set<String> b0111set = new HashSet<String>();//不重复
				while(rs_a0195.next()){
					String a0195=rs_a0195.getString("a0195");
					b0111set.add(a0195);
					if(a0195.length()<7){
						continue;
					}
					do{
						if(a0195.length()<4){
							break;
						}
						a0195=a0195.substring(0, a0195.length()-4);
						System.out.println(a0195);
						b0111set.add(a0195);
					}while(a0195.length()!=7);
				}
				rs_a0195.close();
				stmt.close();
				String selectb0111s="";
				if(b0111set.size()<1000){
					StringBuffer b0111s=new StringBuffer();
					int count_b0111s=1;
					for(String b0111:b0111set){
						b0111s.append("'"+b0111+"'"+",");
						count_b0111s=count_b0111s+1;
					}
					b0111s.append("'-1'");
					selectb0111s=b0111s.toString();
				}else{
					String createIdsSql="create table "+tempTable+" "
							+ "(b0111 varchar(120) NOT NULL,"
							+ "PRIMARY KEY (b0111))";
					stmt = conn.createStatement();
					stmt.execute(createIdsSql);
					stmt.close();
					for(String b0111:b0111set){
						HBUtil.executeUpdate("insert into "+tempTable+"(b0111) values("+b0111+")");
					}
					HBUtil.executeUpdate("insert into "+tempTable+"(b0111) values('-1')");
					selectb0111s="select b0111 from "+tempTable;
				}
				//通过b0111查询机构
				stmt = conn.createStatement();
				String b01sql="select * from b01 WHERE b0111 IN("+selectb0111s+")";
				ResultSet rs_b01 = stmt.executeQuery(b01sql);
				new DataToDB().insert(sqliteConnB01, rs_b01,"b01");
				rs_b01.close();
				stmt.close();
				
//				Connection sqliteConnB01=new SQLiteUtil(filename).getConnection();
//				stmt = conn.createStatement();
//				String b01sql = "";
//				if(DBType.ORACLE == DBUtil.getDBType()){
//					b01sql="SELECT * FROM b01 d WHERE EXISTS ( SELECT b.b0111 FROM b01 b INNER JOIN (SELECT * FROM A01 WHERE a0000 IN ("+ids+")) a ON a.a0195 LIKE b.b0111||'%' where d.b0111 = b.b0111) OR b0111 = '-1'";
//					//b01sql="select s.* from b01 s , (SELECT a0195 from A01 a inner join A01SEARCHTEMP c  on a.a0000=c.a0000 WHERE sessionid = '"+sessionid+"' group by a0195) x where x.a0195 LIKE s.b0111||'%' OR s.b0111 = '-1'";
//				}else{
//					//select * from b01 s , (SELECT a0195 from A01 a inner join A01SEARCHTEMP  on a.a0000=c.a0000 WHERE sessionid = '8D28840A86E4DCC1A430BF2EB4D703C2' group by a0195) x where x.a0195 LIKE CONCAT(s.b0111, '%')OR s.b0111 = '-1'
//					//b01sql = "SELECT * FROM b01 d WHERE EXISTS ( SELECT b.b0111 FROM b01 b INNER JOIN (SELECT * FROM A01 WHERE a0000 IN ("+ids+")) a ON a.a0195 LIKE CONCAT(b.b0111,'%') where d.b0111 = b.b0111) OR b0111 = '-1'";
//					//b01sql="select * from b01 s , (SELECT a0195 from A01 a inner join A01SEARCHTEMP c on a.a0000=c.a0000 WHERE sessionid = '"+uuid+"' group by a0195) x where x.a0195 LIKE CONCAT(s.b0111, '%')OR s.b0111 = '-1'";
//					b01sql="select s.* from b01 s , (SELECT a0195 from A01 a inner join A01SEARCHTEMP c on a.a0000=c.a0000 WHERE sessionid = '"+sessionid+"' group by a0195) x where x.a0195 LIKE CONCAT(s.b0111, '%')	OR s.b0111 = '-1'";
//				
//				}
//				ResultSet rs_b01 = stmt.executeQuery(b01sql);
//				new DataToDB().insert(sqliteConnB01, rs_b01,"b01");
//				rs_b01.close();
//				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","数据说明文件生成处理中",uuid);	
				
				//------------------------------------------------------------
				//记录页面详情
				String sql4 = "update EXPINFO set STATUS = '照片生成中请稍候...' where id = '"+uuid+"'";
				sess.createSQLQuery(sql4).executeUpdate();
				//---------------------------------------------------------------
				KingbsconfigBS.saveImpDetail(process_run,"1","人员照片头像生成处理中",uuid);	
				final String realPhotoPath=sess.createSQLQuery("select AAA005 from AA01 WHERE AAA001='PHOTO_PATH'").uniqueResult().toString()+"/";
				//int a57length=Integer.parseInt((sess.createSQLQuery("select count(1) from A57 where a0000 in(" +ids + ")").uniqueResult().toString()));
				int a57length=Integer.parseInt((sess.createSQLQuery("select count(1) from A57").uniqueResult().toString()));
				stmt = conn.createStatement();
//				final ResultSet rs_A57t = stmt.executeQuery("select * from A57 where a0000 in(" +ids + ")");
				final ResultSet rs_A57t = stmt.executeQuery("select * from A57");
				if(rs_A57t!=null){
					String photopath = zippath +"Photos/";				//生成图片路径      
					File file2 =new File(photopath);    
					if  (!file2 .exists()  && !file2 .isDirectory()){   //如果文件夹不存在则创建       
						file2 .mkdirs();    
					}
					
					File photoSourceFile=new File(this.getClass().getClassLoader().getResource("./photo.db").getPath());
					String photoFilename=photopath+"/photo.db";
					File photoTargetFile=new File(photoFilename);
					SQLiteUtil.copyFile(photoSourceFile, photoTargetFile);
					Connection photoSqliteConn=new SQLiteUtil(photoFilename).getConnection();
//					ResultSet rs_a01 = null;
//					rs_a01 = stmt.executeQuery("select * from a01 where a0000 in(" +ids + ")");
//					new DataToDB().insert(photoSqliteConn, rs_a01,"a01");
					photoSqliteConn.setAutoCommit(false);
					String photoSql="insert into A57_BASE64S(A0000,'PHOTONAME','PHOTOBASE64') values(?,?,?)";
					PreparedStatement prestate=photoSqliteConn.prepareStatement(photoSql);
					List<String> photolist = new ArrayList<String>();
					int commitNum=20000;
					int count=0;
					//这里改成先多线程转base64放到一个集合里，然后循环插入
					final CountDownLatch latch = new CountDownLatch(a57length);
					final Map<String,A57_my> a57map=new Hashtable<String,A57_my>();
					final Map<String,String> base64map=new Hashtable<String,String>();
					ExecutorService taskPool=Executors.newScheduledThreadPool(2);
					while(rs_A57t.next()){
						String a0000 = rs_A57t.getString("a0000");
						String photoname = rs_A57t.getString("photoname");
						String photop = rs_A57t.getString("photopath");
						A57_my a571=new A57_my();
						a571.setA0000(a0000);
						a571.setPhotoname(photoname);
						a571.setPhotop(photop);
						a57map.put(a0000, a571);
					}
					
					
						for(final Map.Entry<String, A57_my> entry:a57map.entrySet()){
							
							Runnable base64run=new Runnable(){
								@Override
								public void run() {
									try{
										A57_my a57_my=entry.getValue();
										String a0000 = entry.getKey();
										String photoname = a57_my.getPhotoname();
										String photop = a57_my.getPhotop();
										String photon = (photoname!=null&&!photoname.equals(""))? photoname:a0000 +"." + "jpg";
										File photoFile=new File(realPhotoPath+photop+photon);
										if(photoFile.exists()){
											InputStream photoInputStream=new FileInputStream(photoFile);
											byte[] photodata=new byte[photoInputStream.available()];
											photoInputStream.read(photodata);
											photoInputStream.close();
											String photoBase64=new String(Base64.encodeBase64(photodata));
											base64map.put(a0000, photoBase64);
										}
									}catch(Exception e){
										e.printStackTrace();
									}finally{
						                latch.countDown();  
									}
								}
							};
							taskPool.execute(base64run);
						}
					try{
						latch.await();
						System.gc();
					}catch (Exception e) {
						e.printStackTrace();
					}
					if(base64map.size()>0){
						for(Map.Entry<String, String> entry:base64map.entrySet()){
							count=count+1;
							String a0000key=entry.getKey();
							String base64value=entry.getValue();
							prestate.setObject(1, a0000key);
							prestate.setObject(2, "");
							prestate.setObject(3, base64value);
							prestate.addBatch();
							if(count%commitNum==0){
								prestate.executeBatch();
								photoSqliteConn.commit();
								prestate.clearBatch();
							}
						}
						prestate.executeBatch();
						photoSqliteConn.commit();
						prestate.clearBatch();
						
						
					}
					//关闭照片
					prestate.close();
					photoSqliteConn.close();
					
//					while (rs_A57t.next()) {
//						count=count+1;
//						String a0000 = rs_A57t.getString("a0000");
//						String photoname = rs_A57t.getString("photoname");
//						String photop = rs_A57t.getString("photopath");
//						String photon = (photoname!=null&&!photoname.equals(""))? photoname:a0000 +"." + "jpg";
//						
//						
//						
//						File photoFile=new File(realPhotoPath+photop+photon);
//						if(photoFile.exists()){
//							InputStream photoInputStream=new FileInputStream(photoFile);
//							byte[] photodata=new byte[photoInputStream.available()];
//							photoInputStream.read(photodata);
//							photoInputStream.close();
//							String photoBase64=new String(Base64.encodeBase64(photodata));
//							
//							prestate.setObject(1, a0000);
//							prestate.setObject(2, photop+photon);
//							prestate.setObject(3, photoBase64);
//							prestate.addBatch();
//						}
//						if(count%commitNum==0){
//							prestate.executeBatch();
//							photoSqliteConn.commit();
//							prestate.clearBatch();
//						}
//					}
					
//					prestate.executeBatch();
//					photoSqliteConn.commit();
//					prestate.clearBatch();
//					
//					prestate.close();
//					photoSqliteConn.close();
					
				}
				
				rs_A57t.close();
				stmt.close();
				KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid);			//记录导入过程
				process_run = "3";
				KingbsconfigBS.saveImpDetail(process_run,"1","压缩中",uuid);			//记录导入过程
				infile = zipfile;
				//------------------------------------------------------------
				//记录页面详情
				String sql3 = "update EXPINFO set STATUS = '文件压缩中请稍候...' where id = '"+uuid+"'";
				sess.createSQLQuery(sql3).executeUpdate();
				//---------------------------------------------------------------
				Zip7z.zip7Z(zippath, zipfile, null);
				KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid,infile.replace("\\", "/"));	
				//------------------------------------------------------------
				//记录页面详情
				String time2 = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
				String sql2 = "update EXPINFO set endtime = '"+time2+"',STATUS = '导出完成',zipfile = '"+zipfile+"' where id = '"+uuid+"'";
				sess.createSQLQuery(sql2).executeUpdate();
				//---------------------------------------------------------------
				try {
					new LogUtil("421", "IMP_RECORD", "", "", "数据导出", new ArrayList(),userVo).start();
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
				KingbsconfigBS.saveImpDetail(process_run ,"4","失败:"+e.getMessage(),uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//结束提示信息。 
			
		}finally{
			try {
				HBUtil.executeUpdate("drop table "+idsTempTableName);//删除临时表
				HBUtil.executeUpdate("drop table "+tempTable);
			} catch (AppException e) {
				
			}
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
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
           FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
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
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	
	private String getPath() {
		String upload_file = AppConfig.HZB_PATH + "/temp/zipload/";
		try {
			File file =new File(upload_file);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		//解压路径
		String zip = upload_file + uuid + "/";
		return zip;
	}
	
	
}

class ToBase64 extends Thread{
	//
	
	
}

class A57_my{
	private String a0000;
	private String photoname;
	private String photop;
	private String base64;
	public String getA0000() {
		return a0000;
	}
	public void setA0000(String a0000) {
		this.a0000 = a0000;
	}
	public String getPhotoname() {
		return photoname;
	}
	public void setPhotoname(String photoname) {
		this.photoname = photoname;
	}
	public String getPhotop() {
		return photop;
	}
	public void setPhotop(String photop) {
		this.photop = photop;
	}
	public String getBase64() {
		return base64;
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}	
	
	
}
