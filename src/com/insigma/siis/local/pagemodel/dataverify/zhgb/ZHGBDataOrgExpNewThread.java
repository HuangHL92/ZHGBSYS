package com.insigma.siis.local.pagemodel.dataverify.zhgb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.hibernate.Query;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBNewUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil2;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.DataOrgExpControl;
import com.insigma.siis.local.pagemodel.dataverify.DataOrgExpPartThread;
import com.insigma.siis.local.pagemodel.dataverify.DataOrgExpPartThreadDUJG;
import com.insigma.siis.local.util.SqlUtil;

public class ZHGBDataOrgExpNewThread implements Runnable {
	
	private String uuid;
	private String gjgs;
	private CurrentUser user;
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
	private String sign;
//	private String lsry;
	
	private String fxzry;
    public ZHGBDataOrgExpNewThread(String uuid, CurrentUser user,String gjgs,String fxzry,String gzlbry
    		,String gllbry,String searchDeptid,String linkpsn,String linktel,
    		String remark,String gz_lb,String gl_lb,String tabimp,String zdcjg,UserVO userVo,String sign) {
//        this.lsry = lsry;
        this.uuid = uuid;
        this.gjgs = gjgs;
        this.user = user;
        this.zdcjg = zdcjg;
//        this.ltry = ltry;
        this.gzlbry = gzlbry;
        this.gllbry = gllbry;
        this.searchDeptid = searchDeptid;
        this.linkpsn = linkpsn;
        this.linktel = linktel;
        this.remark = remark;
        this.gz_lb = gz_lb;
        this.gl_lb = gl_lb;
        this.tabimp = tabimp;
        this.userVo = userVo;
        this.fxzry = fxzry;
        this.sign = sign;
    }

	@Override
	public void run() {
		Map<String, String> map = new HashMap<String, String>();
		String infile = ""; // 文件
		String process_run = "1"; // 过程序号
		String newTable = "";
	    	
		try {
			// 记录日志文件
			StopWatch w = new StopWatch();
			String logfilename = AppConfig.HZB_PATH + "/temp/upload/exp" +DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".txt";
			File logfile = new File(logfilename);
			if (!logfile.exists()) {
				logfile.createNewFile();
			}
			appendFileContent(logfilename, "ks:" + "," + DateUtil.getTime()+ "\n");
			if (tabimp == null || tabimp.equals("") || tabimp.equals("1")) {
				HBSession sess = HBUtil.getHBSession();
				
				if(!("h".equalsIgnoreCase(linkpsn) && "z".equalsIgnoreCase(linktel) && "b".equalsIgnoreCase(remark))){ //可以跳过检验，主要用于大数据测试时使用
					//按人员导出前的校验工作
					KingbsconfigBS.saveImpDetail("1","1","正在进行数据校验",uuid);						//记录校验过程
					
					//1、机构编码不能为空
					String sqla = "";
					if(DBType.ORACLE == DBUtil.getDBType()){
						//sqla = "SELECT COUNT(1) FROM B01 WHERE 1 = 1 AND B01.B0111 LIKE '"+searchDeptid+"%' AND B01.B0111 != '-1' AND B01.B0114 IS NULL";
						sqla="select count(*) count1 from b01, competence_userdept s where"
								+ " b01.b0111=s.b0111 "
								+ " and s.b0111 like '"+searchDeptid+"%' "
								+ " and s.userid='"+userVo.getId()+"' and b01.b0114 is null";
					}else{
						//sqla = "SELECT COUNT(1) FROM B01 WHERE 1 = 1 AND B01.B0111 LIKE '"+searchDeptid+"%' AND B01.B0111 != '-1' AND (B01.B0114 IS NULL OR B01.B0114 = '')";
						sqla="select count(*) count1 from b01, competence_userdept s where"
								+ " b01.b0111=s.b0111 "
								+ " and s.b0111 like '"+searchDeptid+"%' "
								+ " and s.userid='"+userVo.getId()+"' and (b01.b0114 is null or b01.b0114 = '')";
					}
					Object obj1 = sess.createSQLQuery(sqla).uniqueResult();
					if(obj1!=null){
						Integer one = Integer.parseInt(""+obj1);
						if(one > 0){
							throw new RuntimeException("发现"+one+"个机构存在空机构编码");
						}
					}
					//2、机构编码不能重复
					String sqlb = "SELECT SUM(N) FROM (select count(1) AS N from b01,"
								+ " competence_userdept s where"
								+ " b01.b0111=s.b0111 "
								+ " and s.b0111 like '"+searchDeptid+"%' and "
								+ "s.userid='"+userVo.getId()+"' group by b0114 having count(b0114)>1) TMP";
					/*if(DBType.ORACLE == DBUtil.getDBType()){
						sqlb = "SELECT COUNT(1) FROM B01 WHERE 1 = 1 AND (EXISTS (SELECT 1 FROM B01 B WHERE B.B0114 = B01.B0114 AND B.B0111 != B01.B0111 AND B0111 LIKE '"+searchDeptid+"%'))";
					}else{
						sqlb = "SELECT COUNT(1) FROM B01,B01 AS B WHERE B.B0114 = B01.B0114 AND B.B0111 != B01.B0111 AND B01.B0111 LIKE '"+searchDeptid+"%'";
					}*/
					Object obj2 = sess.createSQLQuery(sqlb).uniqueResult();
					if(obj2!=null){
						Integer one = Integer.parseInt(""+obj2);
						if(one > 0){
							throw new RuntimeException("发现"+one+"个机构编码重复");
						}
					}
					
					if (!zdcjg.equals("1")) {
						//3、机构内人员身份证不能为空
						String sqlc = "";
						if(DBType.ORACLE == DBUtil.getDBType()){
							//sqlc = "SELECT COUNT(1) FROM A01,A02 WHERE 1 = 1 AND A01.A0184 IS NULL AND A01.STATUS != '4' AND A01.A0000 = A02.A0000 AND A02.A0201B LIKE '"+searchDeptid+"%'";
							sqlc=" select count(*) count1 from a01 a01 where exists (select 1 from a02, competence_userdept s where"
									+ " a02.a0201b=s.b0111 "
									+ " and s.b0111 like '"+searchDeptid+"%' "
									+ " and s.userid='"+userVo.getId()+"' "
									+ " and a01.a0000=a02.a0000 "
									+ "  and a01.status != '4' "
									+ " ) and a01.a0184 is null";
						}else{
							//sqlc = "SELECT COUNT(1) FROM A01,A02 WHERE 1 = 1 AND (A01.A0184 IS NULL OR A01.A0184 = '') AND A01.STATUS != '4' AND A01.A0000 = A02.A0000 AND A02.A0201B LIKE '"+searchDeptid+"%'";
							sqlc=" select count(*) count1 from a01 a01 where a01.a0000 in (select a02.a0000 from a02, competence_userdept s where"
									+ " a02.a0201b=s.b0111 "
									+ " and s.b0111 like '"+searchDeptid+"%' "
									+ " and s.userid='"+userVo.getId()+"' "
									+ " and a01.a0000=a02.a0000 "
									+ "  and a01.status != '4' "
									+ " ) and (a01.a0184 is null or a01.a0184 = '')";
						}
						Object obj3 = sess.createSQLQuery(sqlc).uniqueResult();
						if(obj3!=null){
							Integer one = Integer.parseInt(""+obj3);
							if(one > 0){
								throw new RuntimeException("发现机构内"+one+"个人员身份证为空");
							}
						}
						
						//4、机构内人员身份证不能重复  
						//String 	sqld = "SELECT SUM(N) FROM (SELECT COUNT(1) AS N FROM a01 where exists (select 1 from a02 where a01.a0000 = a02.a0000 and a02.a0201b like '"+searchDeptid+"%') and status<>'4' GROUP BY a0184 HAVING count(1) >= 2) TMP";
						String 	sqld = "SELECT count(*) FROM a01 WHERE a01.a0184 IN ( "
								+ " SELECT a0184 FROM a01 WHERE a0000 IN (select a0000 from a02, competence_userdept s "
								+ " where a02.a0201b=s.b0111 and s.b0111 like '"+searchDeptid+"%' and s.userid='"+userVo.getId()+"') "
								+ " AND STATUS <> '4' GROUP BY a0184 HAVING count(a0184) > 1)";
						
						/*if(DBType.ORACLE == DBUtil.getDBType()){
							sqld = "SELECT COUNT(1) FROM A01 WHERE 1 = 1 AND (EXISTS (SELECT 1 FROM A01 A,A02 WHERE A.A0184 = A01.A0184 AND A .a0000 != A01.a0000 AND A02.A0201B LIKE '"+searchDeptid+"%' AND A.A0000 = A02.A0000))";
						}else{
							sqld = "SELECT COUNT(1) FROM A01,A01 AS A,A02 WHERE A.A0184 = A01.A0184 AND A.a0000 != A01.a0000 AND A02.A0201B LIKE '"+searchDeptid+"%' AND A.A0000 = A02.A0000";
						}*/
						Object obj4 = sess.createSQLQuery(sqld).uniqueResult();
						if(obj4!=null){
							Integer one = Integer.parseInt(""+obj4);
							if(one > 0){
								throw new RuntimeException("发现机构内"+one+"个人员身份证重复");
							}
						}
					}
				}
				
				B01 b01 = (B01) sess.get(B01.class, searchDeptid);
				newTable = "A01" + getNo() + System.currentTimeMillis();

				List<Map> list17 = new ArrayList<Map>();
				map.put("type", "21"); // 导出机构数据
				map.put("time", DateUtil.timeToString(DateUtil.getTimestamp()));
				map.put("dataversion", "20171020");
				map.put("photodir", "Photos");
				map.put("B0101", b01.getB0101());
				map.put("B0111", b01.getB0111());
				map.put("B0114", b01.getB0114());
				map.put("B0194", b01.getB0194());
				map.put("linkpsn", linkpsn);
				map.put("linktel", linktel);
				map.put("remark", remark);

				DataOrgExpControl control = new DataOrgExpControl();
				String path = getPath();
				//String zippath = path+ "按机构导出文件_"+ b01.getB0111()+ "_"+ b01.getB0101()+ DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss") + "/";
				String zippath = "";
				String name = "";
				if("7z".equals(sign)){
					map.put("type", "23");//通用格式
					map.put("dataversion", "20171020");
					zippath = path + DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss") + "/";
					name = "按机构导出文件_"+ b01.getB0111()+ "_"+ b01.getB0101()+ DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss")+".7z";
				}else if("zip".equals(sign)){
					map.put("type", "23");//通用格式
					map.put("dataversion", "2017.01");
					zippath = path + DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss") + "/";
					name = "按机构导出文件_"+ b01.getB0111()+ "_"+ b01.getB0101()+ DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss")+".zip";
				}else{
					zippath = path+ "按机构导出文件_"+ b01.getB0111()+ "_"+ b01.getB0101()+ DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss") + "/";
					name = "按机构导出文件_"+ b01.getB0111()+ "_"+ b01.getB0101()+ DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss")+".hzb";
				}
				
				String time = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
				//详情页面文件名称
				String sql1 = "insert into EXPINFO (ID,NAME,STARTTIME,CREATEUSER,STATUS,B0101) values ('"+uuid+"','"+name+"','"+time+"','"+user.getId()+"','文件生成中请稍候...','"+b01.getB0101()+"')";
				//--------------------------------------------------------------------
				sess.createSQLQuery(sql1).executeUpdate();
				//----------------------------------------------------------------------
//				String zippath1 = "按机构导出文件_"+ b01.getB0111()+ "_"+ b01.getB0101()+ DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss") + "/";'"+time+"','%Y-%m-%d %H:%i:%s'
				File file = new File(zippath);
				// 如果文件夹不存在则创建
				if (!file.exists() && !file.isDirectory()) {
					file.mkdirs();
				}
				String zippathtable = zippath + "/Table/";
				File file1 = new File(zippathtable);
				// 如果文件夹不存在则创建
				if (!file1.exists() && !file1.isDirectory()) {
					file1.mkdirs();
				}
				if (zdcjg.equals("1")) {
					map.put("psncount", "0");
					String sql4 = "update expinfo set psncount = '0' where ID = '"+uuid+"'";
					sess.createSQLQuery(sql4).executeUpdate();
					
					list17.add(map);
					Xml4HZBNewUtil.List2Xml(list17, "info", zippath, sign);
					KingbsconfigBS.saveImpDetail("1", "2", "完成", uuid); // 记录导入过程
					KingbsconfigBS.saveImpDetail("2", "1", "数据说明文件生成中", uuid);
					DataOrgExpPartThreadDUJG t1 = new DataOrgExpPartThreadDUJG(
							uuid, gjgs, searchDeptid, zdcjg, userVo, newTable,
							b01, control, logfilename, "1", sign);
					control.setPath(path);
					control.setZippath(zippath);
					control.setThd1(new Thread(t1, "DataOrgExpPart_1_" + uuid));
					control.start();
				} else {
					StringBuilder b = new StringBuilder();
					b.append("create table " + newTable + " as  ");
					if (fxzry.equals("1")) {
					    b.append(SqlUtil.getSqlByTableName("A01","") + "where 1=1 ");//pywu  20170608
					    b.append(" AND a0163 IN ('2', '21', '22', '23', '29') AND status != '4' "  );
						b.append(" AND a01.a0000 IN (SELECT a02.a0000 FROM a02 WHERE a02.A0201B IN (SELECT cu.b0111 FROM "
								+ "competence_userdept cu WHERE	cu.userid = '"+userVo.getId()+"')	AND a0281 = 'true' "
								+ "AND a02.a0201b LIKE '"+searchDeptid+"%')");
						
						if (!gz_lb.equals("")) {
							if (gzlbry.equals("0")) {
								b.append(" and a0160 in ("+ gz_lb.substring(0,gz_lb.length() - 1)+ ")");
							}
						}
						if (!gl_lb.equals("")) {
							if (gllbry.equals("0")) {
								b.append(" and a0165 in ("+ gl_lb.substring(0,gl_lb.length() - 1)+ ")");
							}
						}
						
						b.append(" UNION All ");
					}
					
                    b.append(SqlUtil.getSqlByTableName("A01",""));//pywu  20170608

                    b.append(" where 1=1 AND a0163 = '1' AND status != '4' ");
                    b.append(" AND a01.a0000 IN (SELECT a02.a0000 FROM a02 WHERE a02.A0201B IN (SELECT cu.b0111 FROM "
							+ "competence_userdept cu WHERE	cu.userid = '"+userVo.getId()+"')	AND a0281 = 'true' "
							+ "AND a02.a0201b LIKE '"+searchDeptid+"%')");
					if (!gz_lb.equals("")) {
						if (gzlbry.equals("0")) {
							b.append(" and a0160 in ("
									+ gz_lb.substring(0, gz_lb.length() - 1)
									+ ")");
						}
					}
					if (!gl_lb.equals("")) {
						if (gllbry.equals("0")) {
							b.append(" and a0165 in ("
									+ gl_lb.substring(0, gl_lb.length() - 1)
									+ ")");
						}
					}
					// ----------------------------------------------------------------------------------------------------
					CommonQueryBS.systemOut(b.toString());
					w.start();
					Connection conn = sess.connection();
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(b.toString());
					stmt.executeUpdate("create index idx_" + newTable + " on " + newTable + " (a0000)");
					stmt.close();
					conn.close();
					Object spn = sess.createSQLQuery("select count(1) from " + newTable).uniqueResult();
					//map.put("type", "21"); // 导出全数据
					map.put("psncount", (spn != null) ? spn.toString() : "");
					//更新人数
					String num = spn.toString();
					String sql4 = "update expinfo set psncount = '"+num+"' where ID = '"+uuid+"'";
					sess.createSQLQuery(sql4).executeUpdate();
					
					list17.add(map);
					ZHGBXml4HZBNewUtil.List2Xml(list17, "info", zippath, sign);
					KingbsconfigBS.saveImpDetail("1", "2", "完成", uuid); // 记录导入过程
					KingbsconfigBS.saveImpDetail("2", "1", "数据说明文件生成处理中", uuid);
					if( spn != null && spn.toString().equals("0")){
//						//页面详情信息XML文件生成中-----------------------------------
//						String sql2 = "update expinfo set STATUS = '文件生成中请稍候...' where ID = '"+uuid+"'";
//						sess.createSQLQuery(sql2).executeUpdate();
//						//------------------------------------------------
						CommonQueryBS.systemOut(spn.toString());
						/*Xml4HZBNewUtil.List2Xml(list17, "info", zippath, sign);
						KingbsconfigBS.saveImpDetail("1", "2", "完成", uuid); // 记录导入过程
						KingbsconfigBS.saveImpDetail("2", "1", "数据说明文件生成处理中", uuid);*/
						DataOrgExpPartThreadDUJG t1 = new DataOrgExpPartThreadDUJG(
								uuid, gjgs, searchDeptid, zdcjg, userVo, newTable,
								b01, control, logfilename, "1", sign);
						control.setPath(path);
						control.setZippath(zippath);
						control.setThd1(new Thread(t1, "DataOrgExpPart_1_" + uuid));
						control.start();
					}else{
						ZHGBDataOrgExpPartThread t1 = new ZHGBDataOrgExpPartThread(uuid, gjgs, searchDeptid, zdcjg, userVo, newTable, b01, control, logfilename, "1",sign);
						ZHGBDataOrgExpPartThread t2 = new ZHGBDataOrgExpPartThread(uuid, gjgs, searchDeptid, zdcjg, userVo, newTable, b01, control,logfilename, "2",sign);
						control.setPath(path);
						control.setZippath(zippath);
						control.setThd1(new Thread(t1, "DataOrgExpPart_1_" + uuid));
						control.setThd2(new Thread(t2, "DataOrgExpPart_2_" + uuid));
						control.start();
					}
				}
			}
			
		} catch (Exception e) {
			try {
				try {
					if(!newTable.equals("")){
						HBUtil.getHBSession().createSQLQuery("drop table " + newTable).executeUpdate();
					}
				} catch (Exception e2) {
					e.printStackTrace();
				}
				String sql4 = "update expinfo set STATUS = '文件导出异常!' where ID = '"+uuid+"'";
				HBUtil.getHBSession().createSQLQuery(sql4).executeUpdate();
				KingbsconfigBS.saveImpDetail(process_run, "4", "失败:" + e.getMessage(), uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}// 结束提示信息。
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
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0, classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}

	public static void appendFileContent(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getNo() {
		String no = "";
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int r = random.nextInt(35);
			no = no + key[r];
		}
		CommonQueryBS.systemOut(no);
		return no;
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
