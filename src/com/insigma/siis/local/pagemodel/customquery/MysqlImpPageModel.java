package com.insigma.siis.local.pagemodel.customquery;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.utils.CommandUtil;

public class MysqlImpPageModel extends PageModel{

	public String uuid="";
	public MysqlImpPageModel() {
		if(DBType.ORACLE==DBUtil.getDBType()){
			uuid="sys_guid()";
		}else if(DBType.MYSQL==DBUtil.getDBType()){
			uuid="uuid()";
		}
	}

	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String getNo() {
		String no = "";
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int r = random.nextInt(35);
			no = no + key[r];
		}
		return no;
	}
	
	public int createTableTmp(Map<String,String> map){
		try{
			if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
				HBSession sess=HBUtil.getHBSession();
				String tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
				map.put("tableExt",tableExt);
				System.out.println("-----------创建临时表A0192"+tableExt+new Date());//a02 表
				String A0192TableSql = "create table A0192"+tableExt+"(A0000 VARCHAR2(120),newA0223 VARCHAR2(200))";
				sess.createSQLQuery(A0192TableSql.toUpperCase()).executeUpdate();
				
			}else if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 开始抽取mysql库数据
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("impMysqlStart")
	public int impMysqlStart() throws RadowException{
		Map<String,String> map=new HashMap<String, String>();
		System.out.println("-------------开始抽取mysql库数据");
		try{
			String userid=SysUtil.getCacheCurrentUser().getId();
			String username=SysUtil.getCacheCurrentUser().getLoginname();
			map.put("userid",userid);
			map.put("username",username);
			String sysorgid=request.getParameter("sysorgid");
			String filepathv=request.getParameter("filepathv");
			if(sysorgid==null||sysorgid.length()==0){
				this.setSelfDefResData("1@@@"+"请选择接收数据的机构！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			map.put("sysorgid",sysorgid);
			if(filepathv==null||filepathv.length()==0){
				this.setSelfDefResData("1@@@"+"请输入照片路径！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			/*String fileLx=filepathv.substring(filepathv.lastIndexOf("."), filepathv.length());
			if(".zip".equalsIgnoreCase(fileLx)){
			}else{
				this.setSelfDefResData("1@@@"+"文件格式错误！");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			File file=new File(filepathv);
			/*if(!file.exists()){
				this.setSelfDefResData("1@@@"+"路径输入错误，文件不存在！");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			if(!file.isDirectory()){
				this.setSelfDefResData("1@@@"+"路径输入错误，文件夹不存在！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			map.put("filepathv", filepathv);
			String ss=File.separatorChar+"";
			filepathv.replace("/", ss);
			filepathv.replace("\\", ss);
			
			String port=request.getParameter("port");
			if(port==null||port.length()==0){
				this.setSelfDefResData("1@@@"+"请输入mysql端口号！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			map.put(port, port);
			String sid=request.getParameter("sid");
			if(sid==null||sid.length()==0){
				this.setSelfDefResData("1@@@"+"请输入mysql实体名！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			map.put(sid,sid);
			String user=request.getParameter("user");
			if(user==null||user.length()==0){
				this.setSelfDefResData("1@@@"+"请输入mysql用户名！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			map.put(user,user);
			String password=request.getParameter("password");
			if(password==null||password.length()==0){
				this.setSelfDefResData("1@@@"+"请输入mysql密码！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			map.put("password", password);
			map.put("closeMysql", "");//初始化为空
			//建立临时表
			//createTableTmp(map);
			/**
			 * 手工录入压缩包路径，点击确认，进度条提示数据抽取中
				1.解压
				2.读取解压文件服务名，生成mysql注册启动 关闭删除文件，并拷贝到解压文件mysql下
				3.执行.bat文件，注册并启动mysql服务
				4.建立连接
				5.数据抽取
				6.执行.bat文件，关闭并删除mysql服务
				7.提示完成
			 */
			
		/*	//1.开始解压
			System.out.println("-------------开始解压 "+new Date());
			startUnzip(filepathv, map);
			//2.生成mysql注册启动 关闭删除文件
			createBatFile(map);
			System.out.println("-------------生成mysql注册启动 关闭删除文件 "+new Date());
			//3.执行.bat文件，注册并启动mysql服务
			executeBatFile(map);
			System.out.println("-------------注册并启动mysql服务 "+new Date());
			Thread.currentThread();
			Thread.sleep(15000);//毫秒
*/
			//4.建立连接
			int num=0;
			Connection conn=createConnection(port,sid,user,password,map,num);
			System.out.println("-------------建立连接 "+new Date());
			if(conn==null){
				throw new RadowException("mysql服务启动时间过长，程序已经终止！ "+new Date());
			}
			//5.数据抽取
			//建立getCodeValues()
			Map<String, String> mapCode = getCodeValues();
			
			System.out.println("-------------数据抽取开始 "+new Date());
			impData(conn,map,mapCode);
			//6.删除建立的临时表
			//System.out.println("-------------删除建立的临时表 "+new Date());
			//deleteTempTable(map);
			//更新排序字段
			System.out.println("-------------更新排序字段 "+new Date());
			updatesort(sysorgid);
			System.out.println("-------------数据抽取结束 "+new Date());
			System.out.println("--------------接收图片开始 "+new Date().toString());
			copyPhotos(map);
			System.out.println("--------------接收图片结束 "+new Date().toString());
			//7.执行.bat文件，关闭并删除mysql服务
		/*	System.out.println("------------关闭并删除mysql服务 "+new Date());
			CloseMysql(map);
			num=0;
			Thread.currentThread();
			Thread.sleep(10000);//毫秒
			conn=mysqlFw(map,num);
			if(conn==null){
				System.out.println("------------确认关闭mysql服务 "+new Date());
				//8.删除解压的mysql库\
				System.out.println("-------------删除解压的mysql库"+new Date());
				deleteMysqlUn(map);
			}else{
				//throw new RadowException("");
			}*/
			//9.提示完成
			this.setSelfDefResData("2@@@"+"数据接收成功！"+map.get("closeMysql"));
		}catch(Exception e){
			/*try{
				CloseMysql(map);
			}catch(Exception e1){
				e1.printStackTrace();
			}*/
			try{
				//删除表
				//deleteTempTable(map);
			}catch(Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
			this.setSelfDefResData("1@@@"+"数据接收失败！（"+e.getMessage()+"）");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	private void updatesort(String sysorgid) throws AppException {
		if(DBUtil.getDBType().equals(DBType.ORACLE)){
			HBSession sess=HBUtil.getHBSession();
			String tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
			String torgidSql = "create table torgid"+tableExt+"(A0000 VARCHAR2(120),TORGID VARCHAR2(200))";
			sess.createSQLQuery(torgidSql.toUpperCase()).executeUpdate();
			sess.createSQLQuery("insert into torgid"+tableExt+" SELECT W.A0000,W.A0201B FROM (SELECT ROW_NUMBER () OVER (PARTITION BY V.a0000 ORDER BY B01.SORTID DESC) rn,A02.A0000,"
					+ "A02.A0201B FROM (SELECT A01.A0000,MIN (LENGTH(A0201B)) minlength FROM a02,a01 WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0201B LIKE '"+sysorgid+"%'"
					+ "GROUP BY A01.A0000) V,A02,B01 WHERE V.A0000 = A02.A0000 AND B01.B0111 = A02.A0201B AND A02.A0281 = 'true' AND LENGTH (A02.A0201B) "
					+ "= V.minlength) W WHERE W.RN = 1").executeUpdate();
			sess.createSQLQuery("Create index tid" + tableExt + " on torgid" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("UPDATE a01 SET A01.TORGID = (SELECT t.TORGID FROM torgid"+tableExt+" t WHERE t.a0000 = A01.A0000 ) WHERE EXISTS (SELECT 1 FROM torgid"+tableExt+" t WHERE t.a0000 = A01.A0000 )").executeUpdate();
			sess.createSQLQuery("drop table torgid"+tableExt).executeUpdate();
		
			String torderSql = "create table torder"+tableExt+"(A0000 VARCHAR2(120),TORDER VARCHAR2(8))";
			sess.createSQLQuery(torderSql.toUpperCase()).executeUpdate();
			sess.createSQLQuery("insert into torder"+tableExt+" SELECT a01.a0000,LPAD (MAX(a02.a0225), 5, 0) FROM a02,a01 WHERE a01.a0000 = a02.a0000 "
					+ "AND a02.a0281 = 'true' AND a01.torgid = a02.a0201b AND a01.torgid LIKE '"+sysorgid+"%' GROUP BY a01.a0000").executeUpdate();
			sess.createSQLQuery("Create index tder" + tableExt + " on torder" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("UPDATE a01 SET A01.torder = (SELECT t.torder FROM torder"+tableExt+" t WHERE t.a0000 = A01.A0000 ) WHERE EXISTS (SELECT 1 FROM torder"+tableExt+" t WHERE t.a0000 = A01.A0000 )").executeUpdate();
			sess.createSQLQuery("drop table torder"+tableExt).executeUpdate();
		}else{
			HBUtil.executeUpdate("UPDATE a01 SET A01.TORGID = GET_TORGID (A01.A0000) WHERE EXISTS (SELECT 1 FROM a02 WHERE A02.a0000 = A01.A0000 AND A02.A0201B LIKE '"+sysorgid+"%')");
			HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') WHERE A01.TORGID LIKE '"+sysorgid+"%'");
		}
		
	}

	public int deleteTempTable(Map<String,String> map){
		try{
			if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
				HBSession sess=HBUtil.getHBSession();
				String tableExt=map.get("tableExt");
				sess.createSQLQuery("drop table A0192"+tableExt).executeUpdate();
				System.out.println("-----------删除临时表A0192"+tableExt+new Date());
			}else if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int deleteMysqlUn(Map<String,String> map) throws RadowException{
		try{
			String sourcePath=map.get("zippath");
			//如果sPath不以文件分隔符结尾，自动添加文件分隔符  
			if (!sourcePath.endsWith(File.separator)) {  
				sourcePath = sourcePath + File.separator;  
			} 
			File dirFile = new File(sourcePath);
			//如果dir对应是一个目录  
			if (dirFile.isDirectory()) {  
				//删除文件夹下的所有文件(包括子目录)  
				File[] files = dirFile.listFiles();  
				for (int i = 0; i < files.length; i++) {  
				    //删除子文件  
				    if (files[i].isFile()) {  
				    	File file = new File(files[i].getAbsolutePath());  
				        // 路径为文件且不为空则进行删除  
				        if (file.isFile() && file.exists()) {  
				            file.delete();  
				        } 

				    } //删除子目录  
				    else {  
				    	deleteDirectorall(files[i].getAbsolutePath());  
				    }  
				}  
				//删除当前目录  
				dirFile.delete();
			 }
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	 /**
     * 删除此文件夹，及其子文件与子目录
     * @param sourcePath
	 * @throws RadowException 
     */
    public int deleteDirectorall(String sourcePath) throws RadowException{
		try{
			//如果sPath不以文件分隔符结尾，自动添加文件分隔符  
			if (!sourcePath.endsWith(File.separator)) {  
				sourcePath = sourcePath + File.separator;  
			 } 
			File dirFile = new File(sourcePath);
			//如果dir对应是一个目录  
			 if (dirFile.isDirectory()) {  
					//删除文件夹下的所有文件(包括子目录)  
					File[] files = dirFile.listFiles();  
					for (int i = 0; i < files.length; i++) {  
					    //删除子文件  
					    if (files[i].isFile()) {  
					    	File file = new File(files[i].getAbsolutePath());  
					        // 路径为文件且不为空则进行删除  
					        if (file.isFile() && file.exists()) {  
					            file.delete();  
					        } 

					    } //删除子目录  
					    else {  
					    	deleteDirectorall(files[i].getAbsolutePath());  
					    }  
					}  
					//删除当前目录  
					dirFile.delete();
			 }
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	public Connection mysqlFw(Map<String,String> map,int num){
		Connection conn=null;
		try{
			String url = "jdbc:mysql://localhost:"+map.get("port")+"/"+map.get("sid");
			// 加载驱动
			try{
				Thread.currentThread();
				Thread.sleep(3000);//毫秒
				num++;
				Class.forName("com.mysql.jdbc.Driver");
				// 获取数据库连接
				if(num>20){
				}else{
					conn = DriverManager.getConnection(url, map.get("user"), map.get("password"));
					if(conn!=null){
						conn=mysqlFw( map, num);
					}
				}
				
			}catch(Exception e){
			
		    }
		}catch(Exception e){
		}
		return conn;
	}
	
	/**
	 * 执行.bat文件，关闭并删除mysql服务
	 * @return
	 * @throws RadowException 
	 */
	public int CloseMysql(Map<String,String> map) throws RadowException{
		try{
			String ss=File.separatorChar+"";
			//获取文件目录路径
			String strcmd = "cmd /c start  "+map.get("mysqlpath")+ss+"uninstall.bat";  //调用我们在项目目录下准备好的bat文件，如果不是在项目目录下，则把“你的文件名.bat”改成文件所在路径。
			run_cmd(strcmd);  //调用上面的run_cmd方法执行操作
		}catch(Exception e){
			map.put("closeMysql", "关闭mysql服务器失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int deleteOldData(Connection connb,Map<String,String> map) throws RadowException{
		try{
			String strsql="";
			if("system".equals(map.get("username"))){
				strsql="(select t.a0000 from a02 t"
						+ " where t.a0201b like '"+map.get("sysorgid")+"%'  )";
			}else{
				strsql="(select t.a0000 from a02 t,competence_userdept s"
						+ " where t.a0201b=s.b0111 "
						+ " and s.userid='"+map.get("userid")+"' "
						+ " and t.a0201b like '"+map.get("sysorgid")+"%'  )";
			}
			String sql="delete from competence_userdept "
					+ " where userid='"+map.get("userid")+"' "
					+ " and b0111 like '"+map.get("sysorgid")+"%' ";
			System.out.println("-------删除权限数据"+new Date().toString());
			System.out.println(sql);
			deletedata(connb,map,sql);//删除权限数据
			//1.单位信息
			//在插入单位信息时删除老机构
			//3.职务层次与职级信息表
			sql="delete from A05 where a0000 in "+strsql;
			System.out.println("--------delete from A05 职务层次与职级信息表"+new Date().toString());
			System.out.println(sql);
			deletedata(connb,map,sql);
			//4.专业技术任职资格表
			sql="delete from a06 where a0000 in "+strsql;
			System.out.println("--------delete from a06  专业技术任职资格表"+new Date().toString());
			System.out.println(sql);
			deletedata(connb,map,sql);
			
			//6.补充信息表
			sql="delete from a99z1 where a0000 in "+strsql;
			System.out.println("--------delete from a99z1  补充信息表"+new Date().toString());
			System.out.println(sql);
			deletedata(connb,map,sql);
			//7.学历学位信息表
			sql="delete from a08 where a0000 in "+strsql;
			System.out.println("--------delete from a08 a99z1 学历学位信息表"+new Date().toString());
			System.out.println(sql);
			deletedata(connb,map,sql);
			//8.多媒体信息表
			sql="delete from a57 where a0000 in "+strsql;
			System.out.println("--------delete from a57 多媒体信息表"+new Date().toString());
			System.out.println(sql);
			deletedata(connb,map,sql);
			//9.奖惩信息表
			sql="delete from a14 where a0000 in "+strsql;
			System.out.println("--------delete from a14 奖惩信息表"+new Date().toString());
			System.out.println(sql);
			deletedata(connb,map,sql);
			//10.考核信息表
			sql="delete from a15 where a0000 in "+strsql;
			System.out.println("-------delete from a15 考核信息表"+new Date().toString());
			System.out.println(sql);
			deletedata(connb,map,sql);
			//11.家庭成员及社会关系信息表
			sql="delete from a36 where a0000 in "+strsql;
			System.out.println("-------delete from a36 家庭成员及社会关系信息表"+new Date().toString());
			System.out.println(sql);
			deletedata(connb,map,sql);
			//5.人员基本信息表
			sql="delete from a01 where a0000 in "+strsql;
			System.out.println("-------delete from a01 人员基本信息表"+new Date().toString());
			System.out.println(sql);
			deletedata(connb,map,sql);
			//2.职务信息 
			System.out.println("-------delete from A02  职务信息 "+new Date().toString());
			sql="delete from A02 where a0201b like '"+map.get("sysorgid")+"%' ";
			System.out.println(sql);
			deletedata(connb,map,sql);
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException("删除老数据失败"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 数据抽取
	 * @return
	 * @throws RadowException 
	 */
	public int impData(Connection conn,Map<String,String> map,Map<String,String> mapCode) throws RadowException{
		Connection connb=null;
		try{
			//获取session 
			connb=HBUtil.getHBSession().connection();
			//关闭事务自动提交
			connb.setAutoCommit(false);
			//0.删除老数据
			System.out.println("--------------开始删除老数据"+new Date().toString());
			deleteOldData(connb,map);
			System.out.println("--------------删除老数据完成"+new Date().toString());
			//1.单位信息
			System.out.println("--------------开始导入机构数据"+new Date().toString());
			impUnitInfo(conn,connb,map);
			//2.职务信息 
			System.out.println("--------------开始导入职务信息  "+new Date().toString());
			impA02Zw(conn,connb,map);
			//3.职务层次与职级信息表
			System.out.println("--------------开始导入职务层次与职级信息表信息  "+new Date().toString());
			impA05Zwcc(conn,connb,map);
			//4.专业技术任职资格表
			System.out.println("--------------开始导入专业技术任职资格表信息  "+new Date().toString());
			impA06Zyjs(conn,connb,map);
			//5.人员基本信息表
			System.out.println("--------------开始导入人员基本信息表信息  "+new Date().toString());
			impA01Ryxx(conn,connb,map,mapCode);
			//6.补充信息表
			System.out.println("--------------开始导入补充信息表信息  "+new Date().toString());
			ImpA99z1bcxx(conn,connb,map);
			//7.学历学位信息表
			System.out.println("--------------开始导入学历学位信息表信息  "+new Date().toString());
			impA08Xlxw(conn,connb,map);
			//8.多媒体信息表
			System.out.println("--------------开始导入多媒体信息表信息  "+new Date().toString());
			impA57(conn,connb,map);
			//9.奖惩信息表
			System.out.println("--------------开始导入奖惩信息表信息  "+new Date().toString());
			impA14(conn,connb,map);
			//10.考核信息表
			System.out.println("--------------开始导入考核信息表信息  "+new Date().toString());
			impA15(conn,connb,map);
			//11.家庭成员及社会关系信息表
			System.out.println("--------------开始导入家庭成员及社会关系信息表信息  "+new Date().toString());
			impA36(conn,connb,map,mapCode);
			//权限表插入
			System.out.println("--------------开始导入权限表信息  "+new Date().toString());
			impCompetence_userdept(conn,connb,map);
			//更新a01中的学位学历
			connb.commit();
			System.out.println("--------------开始更新a01表  "+new Date().toString());
			//updateXwXl(connb,map);
			updateXwXl2(connb,map);
			System.out.println("--------------更新a01表 完成 "+new Date().toString());
			//connb.commit();
			//connb.rollback();
			connb.close();
			
		}catch(Exception e){
			try{
				connb.rollback();
				System.out.println("--------导入失败数据回滚完成");
				if(connb!=null){
					connb.close();
				}
			}catch(Exception e1){
				e.printStackTrace();
			}
			e.printStackTrace();
			throw new RadowException("数据导入出错!"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void updateXwXl2(Connection connb, Map<String, String> map) {
		String tableExt= getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
		
		try {
			HBSession sess = HBUtil.getHBSession();
			
			if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
				//创建A0000表
				String A0000TableSql = "create table A0000"+tableExt+"(A0000 VARCHAR(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				sess.createSQLQuery(A0000TableSql.toUpperCase()).executeUpdate();
				sess.createSQLQuery("insert into A0000"+tableExt+" SELECT A01.A0000 FROM A01,A02 WHERE A02.A0201B LIKE '"+map.get("sysorgid")+"%' "
						+ "AND A01.A0000 = A02.A0000").executeUpdate();
				sess.createSQLQuery("ALTER TABLE A0000" + tableExt + " add index AA0000" + tableExt + "(A0000)").executeUpdate();
				
				sess.createSQLQuery("UPDATE A01 b,( SELECT T.A0000,group_concat(T.a0243 ORDER BY T.a0223) newa0243 FROM(SELECT a.A0000,"
						+ "CONCAT(Substring(a.A0243, 1, 4),'.',Substring(a.A0243, 5, 2)) a0243,a.A0223 "
						+ "FROM A02 a WHERE a.A0201b like '"+map.get("sysorgid")+"%' AND a.A0255 = '1' AND a.A0281 = 'true' AND (LENGTH (a.A0243) = 6 OR LENGTH (a.A0243) = 8)) T "
						+ "GROUP BY T.A0000 ) X SET b.A0192F = X.newa0243 WHERE b.A0000 = X.A0000").executeUpdate();
				
				//更新 全日制 输出学历
				Statement smtA0801A = sess.connection().createStatement();
				String A0801TableSql = "create table A0801A"+tableExt+"(A0000 varchar(120),A0800 varchar(120),A0801A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				smtA0801A.execute(A0801TableSql.toUpperCase());
				smtA0801A.executeUpdate("insert into A0801A"+tableExt+" SELECT T.A0000,T.A0800,T.A0801A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 "
						+ "THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0801A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXLXX,(@pre_parent_code := V.A0000) GRN "
						+ "FROM a08 V,A0000"+tableExt+" A,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 = '1' AND V.A0801B IS NOT NULL AND A.A0000 = V.A0000 "
						+ " ORDER BY V.A0000,V.A0801B) T WHERE T.RN = 1");
				sess.createSQLQuery("ALTER TABLE A0801A" + tableExt + " add index A0000A0801A" + tableExt + "(A0000)").executeUpdate();
				sess.createSQLQuery("ALTER TABLE A0801A" + tableExt + " add index A0000A0831A" + tableExt + "(A0800)").executeUpdate();
				Statement smt = sess.connection().createStatement();
				//smt.executeUpdate("UPDATE A08 A08 SET A08.A0831 = '0' ");
				smt.executeUpdate("UPDATE A08 A08,A0801A"+tableExt+" Y SET A08.A0831  = '1' WHERE Y.A0800 = A08.A0800");
				smt.executeUpdate("UPDATE A01 A01,A0801A"+tableExt+" Y SET A01.QRZXL = Y.A0801A,A01.QRZXLXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
				smt.execute("drop table A0801A"+tableExt);
				smt.close();
				
				//更新 全日制 输出学位
				Statement smtA0901A = sess.connection().createStatement();
				String A0901TableSql = "create table A0901A"+tableExt+"(A0000 varchar(120),A0800 varchar(120),A0901A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				smtA0901A.execute(A0901TableSql.toUpperCase());
				smtA0901A.executeUpdate("insert into A0901A"+tableExt+" SELECT T.A0000,T.A0800,T.A0901A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = "
						+ "V.A0000 THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0901A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXLXX,(@pre_parent_code := V.A0000) GRN FROM "
						+ "a08 V,A0000"+tableExt+" A,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 = '1' AND "
						+ "V.A0901B IS NOT NULL AND A.A0000 = V.A0000 ORDER BY V.A0000,V.A0901B) T WHERE T.RN = 1");
				sess.createSQLQuery("ALTER TABLE A0901A" + tableExt + " add index A0000A0901A" + tableExt + "(A0000)").executeUpdate();
				sess.createSQLQuery("ALTER TABLE A0901A" + tableExt + " add index A0000A0832A" + tableExt + "(A0800)").executeUpdate();
				Statement smt2 = sess.connection().createStatement();
				//smt2.executeUpdate("UPDATE A08 A08 SET A08.A0832 = '0' ");
				smt2.executeUpdate("UPDATE A08 A08,A0901A"+tableExt+" Y SET A08.A0832  = '1' WHERE Y.A0800 = A08.A0800");
				smt2.executeUpdate("UPDATE A01 A01,A0901A"+tableExt+" Y SET A01.QRZXW = Y.A0901A,A01.QRZXWXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
				smt2.execute("drop table A0901A"+tableExt);
				smt2.close();
				
				//更新 在职 输出学历
				Statement smtA0802A = sess.connection().createStatement();
				String A0802TableSql = "create table A0802A"+tableExt+"(A0000 varchar(120),A0800 varchar(120),A0801A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				smtA0802A.execute(A0802TableSql.toUpperCase());
				smtA0802A.executeUpdate("insert into A0802A"+tableExt+" SELECT T.A0000,T.A0800,T.A0801A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 "
						+ "THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0801A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXLXX,(@pre_parent_code := V.A0000) GRN "
						+ "FROM a08 V,A0000"+tableExt+" A,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 = '2' AND V.A0801B IS NOT NULL AND A.A0000 = V.A0000 "
						+ " ORDER BY V.A0000,V.A0801B) T WHERE T.RN = 1");
				sess.createSQLQuery("ALTER TABLE A0802A" + tableExt + " add index A0000A0802A" + tableExt + "(A0000)").executeUpdate();
				sess.createSQLQuery("ALTER TABLE A0802A" + tableExt + " add index A0000A0838A" + tableExt + "(A0800)").executeUpdate();
				Statement smt3 = sess.connection().createStatement();
				//smt3.executeUpdate("UPDATE A08 A08 SET A08.A0838 = '0' ");
				smt3.executeUpdate("UPDATE A08 A08,A0802A"+tableExt+" Y SET A08.A0838  = '1' WHERE Y.A0800 = A08.A0800");
				smt3.executeUpdate("UPDATE A01 A01,A0802A"+tableExt+" Y SET A01.ZZXL = Y.A0801A,A01.ZZXLXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
				smt3.execute("drop table A0802A"+tableExt);
				smt3.close();
				
				//更新 在职 输出学位
				Statement smtA0902A = sess.connection().createStatement();
				String A0902TableSql = "create table A0902A"+tableExt+"(A0000 varchar(120),A0800 varchar(120),A0901A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				smtA0902A.execute(A0902TableSql.toUpperCase());
				smtA0902A.executeUpdate("insert into A0902A"+tableExt+" SELECT T.A0000,T.A0800,T.A0901A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = "
						+ "V.A0000 THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0901A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXLXX,(@pre_parent_code := V.A0000) GRN FROM "
						+ "a08 V,A0000"+tableExt+" A,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 = '2' AND "
						+ "V.A0901B IS NOT NULL AND A.A0000 = V.A0000 ORDER BY V.A0000,V.A0901B) T WHERE T.RN = 1");
				sess.createSQLQuery("ALTER TABLE A0902A" + tableExt + " add index A0000A0902A" + tableExt + "(A0000)").executeUpdate();
				sess.createSQLQuery("ALTER TABLE A0902A" + tableExt + " add index A0000A0839A" + tableExt + "(A0800)").executeUpdate();
				Statement smt4 = sess.connection().createStatement();
				//smt4.executeUpdate("UPDATE A08 A08 SET A08.A0839 = '0' ");
				smt4.executeUpdate("UPDATE A08 A08,A0902A"+tableExt+" Y SET A08.A0839  = '1' WHERE Y.A0800 = A08.A0800");
				smt4.executeUpdate("UPDATE A01 A01,A0902A"+tableExt+" Y SET A01.ZZXW = Y.A0901A,A01.ZZXWXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
				smt4.execute("drop table A0902A"+tableExt);
				smt4.close();
				
				//更新 最高 学历
				Statement smtA0801G = sess.connection().createStatement();
				String A0801GTableSql = "create table A0801G"+tableExt+"(A0000 varchar(120),A0800 varchar(120),A0801A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				smtA0801G.execute(A0801GTableSql.toUpperCase());
				smtA0801G.executeUpdate("insert into A0801G"+tableExt+" SELECT T.A0000,T.A0800,T.A0801A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 "
						+ "THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0801A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXLXX,(@pre_parent_code := V.A0000) GRN "
						+ "FROM a08 V,A0000"+tableExt+" A,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 IN('1','2') AND V.A0801B IS NOT NULL AND A.A0000 = V.A0000 "
						+ " ORDER BY V.A0000,V.A0801B) T WHERE T.RN = 1");
				sess.createSQLQuery("ALTER TABLE A0801G" + tableExt + " add index A0000A0834G" + tableExt + "(A0800)").executeUpdate();
				sess.createSQLQuery("ALTER TABLE A0801G" + tableExt + " add index A0000A0801G" + tableExt + "(A0000)").executeUpdate();
				//先维护最高学历标记A0834
				Statement smt5 = sess.connection().createStatement();
				//smt5.executeUpdate("UPDATE A08 A08 SET A08.A0834 = '0' ");
				smt5.executeUpdate("UPDATE A08 A08,A0801G"+tableExt+" Y SET A08.A0834  = '1' WHERE Y.A0800 = A08.A0800");
				//再更新最高学历
				smt5.executeUpdate("UPDATE A01 A01,A0801G"+tableExt+" Y SET A01.ZGXL = Y.A0801A,A01.ZGXLXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
				smt5.execute("drop table A0801G"+tableExt);
				smt5.close();
				
				//更新 最高 学位
				Statement smtA0901G = sess.connection().createStatement();
				String A0901GTableSql = "create table A0901G"+tableExt+"(A0000 varchar(120),A0800 varchar(120),A0901A varchar(60),ZGXLXX varchar(120)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
				smtA0901G.execute(A0901GTableSql.toUpperCase());
				smtA0901G.executeUpdate("insert into A0901G"+tableExt+" SELECT T.A0000,T.A0800,T.A0901A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = "
						+ "V.A0000 THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0901A,CONCAT(IFNULL(V.A0814,''), IFNULL(V.A0824,'')) ZGXLXX,(@pre_parent_code := V.A0000) GRN FROM "
						+ "a08 V,A0000"+tableExt+" A,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 IN('1','2') AND "
						+ "V.A0901B IS NOT NULL AND A.A0000 = V.A0000 ORDER BY V.A0000,V.A0901B) T WHERE T.RN = 1");
				sess.createSQLQuery("ALTER TABLE A0901G" + tableExt + " add index A0000A0901G" + tableExt + "(A0000)").executeUpdate();
				sess.createSQLQuery("ALTER TABLE A0901G" + tableExt + " add index A0000A0835G" + tableExt + "(A0800)").executeUpdate();
				//先维护最高学位标记A0835
				Statement smt6 = sess.connection().createStatement();
				//smt6.executeUpdate("UPDATE A08 A08 SET A08.A0835 = '0' ");
				smt6.executeUpdate("UPDATE A08 A08,A0901G"+tableExt+" Y SET A08.A0835  = '1' WHERE Y.A0800 = A08.A0800");
				//再更新最高学历
				smt6.executeUpdate("UPDATE A01 A01,A0901G"+tableExt+" Y SET A01.ZGXW = Y.A0901A,A01.ZGXWXX = Y.ZGXLXX WHERE Y.A0000 = A01.A0000");
				smt6.execute("drop table A0901G"+tableExt);
				smt6.close();
				
				sess.createSQLQuery("drop table A0000"+tableExt).executeUpdate();
			}
			if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
				//创建A0000表
				String A0000TableSql = "create table A0000"+tableExt+"(A0000 VARCHAR2(120))";
				sess.createSQLQuery(A0000TableSql.toUpperCase()).executeUpdate();
				sess.createSQLQuery("insert into A0000"+tableExt+" SELECT A01.A0000 FROM A01,A02 WHERE A02.A0201B LIKE '"+map.get("sysorgid")+"%' "
						+ "AND A01.A0000 = A02.A0000").executeUpdate();
				sess.createSQLQuery("Create index AA0000" + tableExt + " on A0000" + tableExt + "(A0000)").executeUpdate();
				
				String A0192TableSql = "create table A0192"+tableExt+"(A0000 VARCHAR2(120),newA0223 VARCHAR2(200))";
				sess.createSQLQuery(A0192TableSql.toUpperCase()).executeUpdate();
				sess.createSQLQuery("insert into A0192"+tableExt+" SELECT W.A0000,W.newA0223 FROM "
						+ "(SELECT ROW_NUMBER () OVER ( PARTITION BY V.a0000 ORDER BY	V.a0223 DESC) a0223rn,V.* FROM "
						+ "(SELECT T .A0000,T .A0243,T .A0223,WM_CONCAT (T .A0243) OVER (PARTITION BY T .A0000 ORDER BY T .A0223) newA0223 "
						+ "FROM(SELECT A .A0000,Substr(A.A0243, 1, 4)||'.'||Substr(A.A0243, 5, 2) A0243,A .A0223 FROM A02 A WHERE A.A0201b "
						+ " like '"+map.get("sysorgid")+"%' AND A .A0255 = '1' AND A .A0281 = 'true' AND (LENGTH (A.A0243) = 6 OR LENGTH (A.A0243) = 8)) T ) V ) W "
						+ "WHERE W.A0223RN = 1 ").executeUpdate();
				sess.createSQLQuery("Create index AA0192" + tableExt + " on A0192" + tableExt + "(A0000)").executeUpdate();
				sess.createSQLQuery("UPDATE A01 SET A01.A0192F = (SELECT X.newA0223 FROM A0192" + tableExt + " X WHERE X.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0192" + tableExt + " X WHERE X.A0000 = A01.A0000)").executeUpdate();
				sess.createSQLQuery("drop table A0192"+tableExt).executeUpdate();
				
				//更新 全日制 输出学历
				String A0801TableSql = "create table A0801"+tableExt+"(A0000 varchar2(120),A0800 varchar2(120),A0801A varchar2(60),ZGXLXX varchar2(120))";
				sess.createSQLQuery(A0801TableSql.toUpperCase()).executeUpdate();
				sess.createSQLQuery("insert into A0801"+tableExt+" SELECT T .A0000,T .A0800,T .A0801A,T .ZGXLXX FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
						+ "ORDER BY v.A0801B) rn,v.A0000,v.A0800,v.A0801A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08 v,A0000"+tableExt+" A WHERE v.A0899 = 'true' AND v.A0837 = '1' AND "
						+ "v.A0801B IS NOT NULL AND A.A0000 = v.A0000) T WHERE T .rn = 1 ").executeUpdate();
				sess.createSQLQuery("Create index AA0801" + tableExt + " on A0801" + tableExt + "(A0000)").executeUpdate();
				sess.createSQLQuery("Create index AA0831" + tableExt + " on A0801" + tableExt + "(A0800)").executeUpdate();
				//sess.createSQLQuery("UPDATE A08" A08 SET A08.A0831 = '0' ").executeUpdate();
				sess.createSQLQuery("UPDATE A08 A08 SET A08.A0831 = '1' WHERE EXISTS (SELECT 1 FROM A0801"+tableExt+" Y WHERE Y.A0800 = A08.A0800)").executeUpdate();
				sess.createSQLQuery("UPDATE A01 A01 SET (A01.QRZXL, A01.QRZXLXX) = (SELECT T .A0801A,T .ZGXLXX FROM A0801"+tableExt+" T "
						+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0801"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
				sess.createSQLQuery("drop table A0801"+tableExt).executeUpdate();
				
				//更新 全日制 输出学位
				String A0901TableSql = "create table A0901"+tableExt+"(A0000 varchar2(120),A0800 varchar2(120),A0901A varchar2(60),ZGXLXX varchar2(120))";
				sess.createSQLQuery(A0901TableSql.toUpperCase()).executeUpdate();
				sess.createSQLQuery("insert into A0901"+tableExt+" SELECT T .A0000,T .A0800,T .A0901A,T .ZGXLXX FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
						+ "ORDER BY v.A0901B) rn,v.A0000,v.A0800,v.A0901A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08 v,A0000"+tableExt+" A WHERE v.A0899 = 'true' AND v.A0837 = '1' AND "
						+ "v.A0901B IS NOT NULL AND A.A0000 = v.A0000) T WHERE T .rn = 1 ").executeUpdate();
				sess.createSQLQuery("Create index AA0901" + tableExt + " on A0901" + tableExt + "(A0000)").executeUpdate();
				sess.createSQLQuery("Create index AA0832" + tableExt + " on A0901" + tableExt + "(A0800)").executeUpdate();
				//sess.createSQLQuery("UPDATE A08 A08 SET A08.A0832 = '0' ").executeUpdate();
				sess.createSQLQuery("UPDATE A08 A08 SET A08.A0832 = '1' WHERE EXISTS (SELECT 1 FROM A0901"+tableExt+" Y WHERE Y.A0800 = A08.A0800)").executeUpdate();
				sess.createSQLQuery("UPDATE A01 A01 SET (A01.QRZXW, A01.QRZXWXX) = (SELECT T .A0901A,T .ZGXLXX FROM A0901"+tableExt+" T "
						+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0901"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
				sess.createSQLQuery("drop table A0901"+tableExt).executeUpdate();
				
				//更新 在职 输出学历
				String A0802TableSql = "create table A0802"+tableExt+"(A0000 varchar2(120),A0800 varchar2(120),A0801A varchar2(60),ZGXLXX varchar2(120))";
				sess.createSQLQuery(A0802TableSql.toUpperCase()).executeUpdate();
				sess.createSQLQuery("insert into A0802"+tableExt+" SELECT T .A0000,T .A0800,T .A0801A,T .ZGXLXX FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
						+ "ORDER BY v.A0801B) rn,v.A0000,v.A0800,v.A0801A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08 v,A0000"+tableExt+" A WHERE v.A0899 = 'true' AND v.A0837 = '2' AND "
						+ "v.A0801B IS NOT NULL AND A.A0000 = v.A0000) T WHERE T .rn = 1 ").executeUpdate();
				sess.createSQLQuery("Create index AA0802" + tableExt + " on A0802" + tableExt + "(A0000)").executeUpdate();
				sess.createSQLQuery("Create index AA0838" + tableExt + " on A0802" + tableExt + "(A0800)").executeUpdate();
				//sess.createSQLQuery("UPDATE A08 A08 SET A08.A0838 = '0' ").executeUpdate();
				sess.createSQLQuery("UPDATE A08 A08 SET A08.A0838 = '1' WHERE EXISTS (SELECT 1 FROM A0802"+tableExt+" Y WHERE Y.A0800 = A08.A0800)").executeUpdate();
				sess.createSQLQuery("UPDATE A01 A01 SET (A01.ZZXL, A01.ZZXLXX) = (SELECT T .A0801A,T .ZGXLXX FROM A0802"+tableExt+" T "
						+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0802"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
				sess.createSQLQuery("drop table A0802"+tableExt).executeUpdate();
				
				//更新 在职 输出学位
				String A0902TableSql = "create table A0902"+tableExt+"(A0000 varchar2(120),A0800 varchar2(120),A0901A varchar2(60),ZGXLXX varchar2(120))";
				sess.createSQLQuery(A0902TableSql.toUpperCase()).executeUpdate();
				sess.createSQLQuery("insert into A0902"+tableExt+" SELECT T .A0000,T .A0800,T .A0901A,T .ZGXLXX FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
						+ "ORDER BY v.A0901B) rn,v.A0000,v.A0800,v.A0901A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08 v,A0000"+tableExt+" A WHERE v.A0899 = 'true' AND v.A0837 = '2' AND "
						+ "v.A0901B IS NOT NULL AND A.A0000 = v.A0000) T WHERE T .rn = 1 ").executeUpdate();
				sess.createSQLQuery("Create index AA0902" + tableExt + " on A0902" + tableExt + "(A0000)").executeUpdate();
				sess.createSQLQuery("Create index AA0839" + tableExt + " on A0902" + tableExt + "(A0800)").executeUpdate();
				//sess.createSQLQuery("UPDATE A08 A08 SET A08.A0839 = '0' ").executeUpdate();
				sess.createSQLQuery("UPDATE A08 A08 SET A08.A0839 = '1' WHERE EXISTS (SELECT 1 FROM A0902"+tableExt+" Y WHERE Y.A0800 = A08.A0800)").executeUpdate();
				sess.createSQLQuery("UPDATE A01 A01 SET (A01.ZZXW, A01.ZZXWXX) = (SELECT T .A0901A,T .ZGXLXX FROM A0902"+tableExt+" T "
						+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0902"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
				sess.createSQLQuery("drop table A0902"+tableExt).executeUpdate();
				
				//更新 最高 学历
				String A0801GTableSql = "create table A0801G"+tableExt+"(A0000 varchar2(120),A0800 varchar2(120),A0801A varchar2(60),ZGXLXX varchar2(120))";
				sess.createSQLQuery(A0801GTableSql.toUpperCase()).executeUpdate();
				sess.createSQLQuery("insert into A0801G"+tableExt+" SELECT T .A0000,T .A0800,T .A0801A,T .ZGXLXX FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
						+ "ORDER BY v.A0801B) rn,v.A0000,v.A0800,v.A0801A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08 v,A0000"+tableExt+" A WHERE v.A0899 = 'true' AND v.A0837 IN('1','2') AND "
						+ "v.A0801B IS NOT NULL AND A.A0000 = v.A0000) T WHERE T .rn = 1 ").executeUpdate();
				sess.createSQLQuery("Create index AG0801" + tableExt + " on A0801G" + tableExt + "(A0000)").executeUpdate();
				sess.createSQLQuery("Create index AG0834" + tableExt + " on A0801G" + tableExt + "(A0800)").executeUpdate();
				//sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0834 = '0'").executeUpdate();
				sess.createSQLQuery("UPDATE A08 A08 SET A08.A0834 = '1' WHERE EXISTS (SELECT 1 FROM A0801G"+tableExt+" Y WHERE Y.A0800 = A08.A0800)");
				
				sess.createSQLQuery("UPDATE A01 A01 SET (A01.ZGXL, A01.ZGXLXX) = (SELECT T .A0801A,T .ZGXLXX FROM A0801G"+tableExt+" T "
						+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0801G"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
				sess.createSQLQuery("drop table A0801G"+tableExt).executeUpdate();
				
				//更新 最高 学位
				String A0901GTableSql = "create table A0901G"+tableExt+"(A0000 varchar2(120),A0800 varchar2(120),A0901A varchar2(60),ZGXLXX varchar2(120))";
				sess.createSQLQuery(A0901GTableSql.toUpperCase()).executeUpdate();
				sess.createSQLQuery("insert into A0901G"+tableExt+" SELECT T .A0000,T .A0800,T .A0901A,T .ZGXLXX FROM (SELECT ROW_NUMBER () OVER (PARTITION BY v.A0000 "
						+ "ORDER BY v.A0901B) rn,v.A0000,v.A0800,v.A0901A,CONCAT (v.A0814, v.A0824) ZGXLXX FROM A08 v,A0000"+tableExt+" A WHERE v.A0899 = 'true' AND v.A0837 IN('1','2') AND "
						+ "v.A0901B IS NOT NULL AND A.A0000 = v.A0000) T WHERE T .rn = 1 ").executeUpdate();
				sess.createSQLQuery("Create index AG0901" + tableExt + " on A0901G" + tableExt + "(A0000)").executeUpdate();
				sess.createSQLQuery("Create index AG0835" + tableExt + " on A0901G" + tableExt + "(A0800)").executeUpdate();
				//sess.createSQLQuery("UPDATE A08"+tableExt+" A08 SET A08.A0835 = '0' ").executeUpdate();
				sess.createSQLQuery("UPDATE A08 A08 SET A08.A0835 = '1' WHERE EXISTS (SELECT 1 FROM A0901G"+tableExt+" Y WHERE Y.A0800 = A08.A0800)").executeUpdate();
				
				sess.createSQLQuery("UPDATE A01 A01 SET (A01.ZGXW, A01.ZGXWXX) = (SELECT T .A0901A,T .ZGXLXX FROM A0901G"+tableExt+" T "
						+ "WHERE T.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0901G"+tableExt+" T WHERE T.A0000 = A01.A0000)").executeUpdate();
				sess.createSQLQuery("drop table A0901G"+tableExt).executeUpdate();
				
				sess.createSQLQuery("drop table A0000"+tableExt).executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public int updateXwXl(Connection connb,Map<String,String> map) throws RadowException{
		try{
			String tableExt=map.get("tableExt");
		
			if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
				System.out.println("更新a01表中，任职时间字段"+new Date().toString());
				String insertSql="insert into A0192"+tableExt+" "
						+ " SELECT W.A0000,W.newA0223 FROM "
						+ "(SELECT ROW_NUMBER () OVER ( PARTITION BY V.a0000 ORDER BY	V.a0223 DESC) a0223rn,V.* FROM "
							+ "(SELECT T .A0000,T .A0243,T .A0223,WM_CONCAT (T .A0243) OVER (PARTITION BY T .A0000 ORDER BY T .A0223) newA0223  FROM "
							+ " (SELECT A .A0000,Substr(A.A0243, 1, 4)||'.'||Substr(A.A0243, 5, 2) A0243,A .A0223 "
							+ " FROM A02 A "
							+ " WHERE A .A0255 = '1' "
							+ " AND A .A0281 = 'true' "
							+ " and A.a0201b like '"+map.get("sysorgid")+"%' "
							+ " AND (LENGTH (A.A0243) = 6 OR LENGTH (A.A0243) = 8)) T ) V ) W "
					+ "WHERE W.A0223RN = 1 ";
				updatedata(connb,map,insertSql);
				String updateSql="UPDATE A01 A01 SET A01.A0192F = (SELECT X.newA0223 FROM A0192" + tableExt + " X WHERE X.A0000 = A01.A0000) WHERE 1 = 1 AND EXISTS (SELECT 1 FROM A0192" + tableExt + " X WHERE X.A0000 = A01.A0000)";
				updatedata(connb,map,updateSql);
			}else if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
				System.out.println("更新a01表中，任职时间字段"+new Date().toString());
				String updateSql="UPDATE A01 b,( SELECT T.A0000,group_concat(T.a0243 ORDER BY T.a0223) newa0243 FROM(SELECT a.A0000,"
						+ "CONCAT(Substring(a.A0243, 1, 4),'.',Substring(a.A0243, 5, 2)) a0243,a.A0223 "
						+ "FROM A02 a WHERE a.A0255 = '1' AND a.A0281 = 'true' AND (LENGTH (a.A0243) = 6 OR LENGTH (a.A0243) = 8)) T "
						+ "GROUP BY T.A0000 ) X SET b.A0192F = X.newa0243 WHERE b.A0000 = X.A0000";
				updatedata(connb,map,updateSql);
				
			}
			
			String sql="";
			if(DBType.ORACLE==DBUtil.getDBType()){
				//更新
				// 全日制 输出学历
				sql="update a01 a01 "
				          + " set(QRZXL,QRZXLXX)=(select ss.A0801A,ss.ZGXLXX from  (SELECT t.a0000, T.A0801A, T.ZGXLXX " 
				           + " FROM (SELECT ROW_NUMBER() OVER(PARTITION BY v.A0000 ORDER BY v.A0801B) rn, " 
				                         + " v.A0000, " 
				                        + " v.A0801A,  "
				                         + " CONCAT(v.A0814, v.A0824) ZGXLXX  "
				                   + " FROM A08 v  "
				                   + " WHERE v.A0899 = 'true'  "
				                    + " AND v.A0837 = '1'  "
				                     + " AND v.A0801B IS NOT NULL) T  "
				            + " WHERE T.rn = 1) ss  where ss.a0000=a01.a0000) "
				        + " where a01.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' ) ";
				System.out.println("//更新 全日制 输出学历"+new Date().toString());
				//System.out.println(sql);
				updatedata(connb,map,sql);
				//更新 全日制 输出学位
				sql="update a01 a01 "
					  + " set (A01.QRZXW, A01.QRZXWXX) = "
					      + " (select ss.A0901A, ss.ZGXLXX "
					         + " from (SELECT T.A0000, T.A0901A, T.ZGXLXX "
					 + " FROM (SELECT ROW_NUMBER() OVER(PARTITION BY v.A0000 ORDER BY v.A0901B) rn, "
					              + " v.A0000, "
					              + " v.A0901A, "
					              + " CONCAT(v.A0814, v.A0824) ZGXLXX "
					         + " FROM A08 v "
					        + " WHERE v.A0899 = 'true' "
					          + " AND v.A0837 = '1' "
					          + " AND v.A0901B IS NOT NULL) T "
					+ " WHERE T.rn = 1) ss "
					        + " where ss.a0000 = a01.a0000) "
					+ " where a01.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' ) ";
				System.out.println("//更新 全日制 输出学位"+new Date().toString());
				//System.out.println(sql);
				updatedata(connb,map,sql);
				//更新 在职 输出学历
				sql="update a01 a01 "
				  + " set (A01.ZZXL, A01.ZZXLXX) = "
				      + " (select ss.A0801A, ss.ZGXLXX "
				         + " from (SELECT T.A0000, T.A0801A, T.ZGXLXX "
				 + " FROM (SELECT ROW_NUMBER() OVER(PARTITION BY v.A0000 ORDER BY v.A0801B) rn, "
				              + " v.A0000, "
				              + " v.A0801A, "
				              + " CONCAT(v.A0814, v.A0824) ZGXLXX "
				         + " FROM A08 v "
				        + " WHERE v.A0899 = 'true' "
				          + " AND v.A0837 = '2' "
				          + " AND v.A0801B IS NOT NULL) T "
				+ " WHERE T.rn = 1) ss "
				        + " where ss.a0000 = a01.a0000) "
				+ " where a01.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' ) ";
				System.out.println("//更新 在职 输出学历"+new Date().toString());
				//System.out.println(sql);
				updatedata(connb,map,sql);
				//更新 在职 输出学位
				sql="update a01 a01 "
					  + " set (A01.ZZXW, A01.ZZXWXX) = "
					      + "  (select ss.A0901A, ss.ZGXLXX "
					         + "  from (SELECT T.A0000, T.A0901A, T.ZGXLXX "
					 + "  FROM (SELECT ROW_NUMBER() OVER(PARTITION BY v.A0000 ORDER BY v.A0901B) rn, "
					              + "  v.A0000, "
					              + "  v.A0901A, "
					              + "  CONCAT(v.A0814, v.A0824) ZGXLXX "
					         + "  FROM A08 v "
					        + "  WHERE v.A0899 = 'true' "
					          + "  AND v.A0837 = '2' "
					          + "  AND v.A0901B IS NOT NULL) T "
					+ "  WHERE T.rn = 1) ss "
					        + "  where ss.a0000 = a01.a0000) "
					+ "  where a01.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' )";
				System.out.println("//更新 在职 输出学位"+new Date().toString());
				//System.out.println(sql);
				updatedata(connb,map,sql);
				//更新 最高 学历
				sql="update a01 a01 "
						+ "  set (A01.ZGXL, A01.ZGXLXX) = "
				      + "  (select ss.A0801A, ss.ZGXLXX "
				         + "  from (SELECT T.A0000,  T.A0801A, T.ZGXLXX "
				  + "   FROM (SELECT ROW_NUMBER() OVER(PARTITION BY v.A0000 ORDER BY v.A0801B) rn, "
				               + "   v.A0000, "
				               + "   v.A0800, "
				               + "   v.A0801A, "
				               + "   CONCAT(v.A0814, v.A0824) ZGXLXX "
				           + "  FROM A08 v "
				          + "  WHERE v.A0899 = 'true' "
				           + "   AND v.A0837 IN ('1', '2') "
				            + "  AND v.A0801B IS NOT NULL) T "
				  + "  WHERE T.rn = 1) ss "
				        + "  where ss.a0000 = a01.a0000) "
				+ "  where a01.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' )";
				System.out.println("//更新 最高 学历"+new Date().toString());
				//System.out.println(sql);
				updatedata(connb,map,sql);
				//更新 最高 学历 标志
				sql=" update a08 SET A08.A0834 = '1'  where a08.A0800 in (SELECT T.A0800 "
						+ "  FROM (SELECT ROW_NUMBER() OVER(PARTITION BY v.A0000 ORDER BY v.A0801B) rn, "
				                + "  v.A0000, "
				                + "  v.A0800, "
				                + "  v.A0801A, "
				                + "  CONCAT(v.A0814, v.A0824) ZGXLXX "
				           + "  FROM A08 v "
				          + "  WHERE v.A0899 = 'true' "
				            + "  AND v.A0837 IN ('1', '2') "
				            + "  AND v.A0801B IS NOT NULL) T "
				  + "  WHERE T.rn = 1)"
				  + " and a08.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' )";
				System.out.println("//更新 最高 学历 标志"+new Date().toString());
				//System.out.println(sql);
				updatedata(connb,map,sql);
				//更新 最高 学位
				System.out.println("//更新 最高 学位"+new Date().toString());
				sql="update a01 a01 "
						 + " set (A01.ZGXW, A01.ZGXWXX) = "
					        + " (select ss.A0901A, ss.ZGXLXX "
					           + " from (SELECT T.A0000,  T.A0901A, T.ZGXLXX "
					  + "  FROM (SELECT ROW_NUMBER() OVER(PARTITION BY v.A0000 ORDER BY v.A0901B) rn, "
					               + "  v.A0000, "
					               + "  v.A0800, "
					                + " v.A0901A, "
					               + "  CONCAT(v.A0814, v.A0824) ZGXLXX "
					           + " FROM A08 v "
					         + "  WHERE v.A0899 = 'true' "
					            + " AND v.A0837 IN ('1', '2') "
					           + "  AND v.A0901B IS NOT NULL) T "
					 + "  WHERE T.rn = 1) ss "
					         + "  where ss.a0000 = a01.a0000) "
					  + " where a01.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' )";
				updatedata(connb,map,sql);
				//更新 最高 学位 标志
				System.out.println("//更新 最高 学位 标志"+new Date().toString());
				sql="update A08 SET A08.A0835 = '1' where a08.A0800 in  (SELECT T.A0800 "
						+ "  FROM (SELECT ROW_NUMBER() OVER(PARTITION BY v.A0000 ORDER BY v.A0901B) rn, "
					             + "   v.A0000, "
					             + "   v.A0800, "
					             + "   v.A0901A, "
					             + "   CONCAT(v.A0814, v.A0824) ZGXLXX "
					          + " FROM A08 v "
					        + "  WHERE v.A0899 = 'true' "
					           + " AND v.A0837 IN ('1', '2') "
					           + " AND v.A0901B IS NOT NULL) T "
					 + " WHERE T.rn = 1 )"
					 + " and a08.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' )";
				updatedata(connb,map,sql);
			}else if(DBType.MYSQL==DBUtil.getDBType()){
				//更新 全日制 输出学历
				System.out.println("//更新 全日制 输出学历"+new Date().toString());
				sql="UPDATE A01 A01,(  "
						+ " SELECT T.A0000,T.A0801A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000  "
						+ " THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0801A,CONCAT(V.A0814, V.A0824) ZGXLXX,(@pre_parent_code := V.A0000) GRN  "
						+ " FROM a08 V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 = '1' AND V.A0801B IS NOT NULL "
						+ "  ORDER BY V.A0000,V.A0801B) T WHERE T.RN = 1 "
						+ " ) Y "
					+ " SET A01.QRZXL = Y.A0801A,A01.QRZXLXX = Y.ZGXLXX "
					+ " WHERE Y.A0000 = A01.A0000 "
					+ " and a01.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' ) ";
				updatedata(connb,map,sql);
				//更新 全日制 输出学位
				sql="UPDATE A01 A01,(  "
						+ " SELECT T.A0000,T.A0901A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = "
								+ " V.A0000 THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0901A,CONCAT(V.A0814, V.A0824) ZGXLXX,(@pre_parent_code := V.A0000) GRN FROM "
								+ " a08 V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 = '1' AND "
								+ " V.A0901B IS NOT NULL ORDER BY V.A0000,V.A0901B) T WHERE T.RN = 1 "
						+ " ) Y "
					+ " SET A01.QRZXW = Y.A0901A,A01.QRZXWXX = Y.ZGXLXX "
					+ " WHERE Y.A0000 = A01.A0000 "
					+ " and a01.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' ) ";
				updatedata(connb,map,sql);
				//更新 在职 输出学历
				System.out.println("更新 在职 输出学历"+new Date().toString());
				sql="UPDATE A01 A01,(  "
						+ " SELECT T.A0000,T.A0801A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 "
						+ " THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0801A,CONCAT(V.A0814, V.A0824) ZGXLXX,(@pre_parent_code := V.A0000) GRN "
						+ " FROM a08 V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 = '2' AND V.A0801B IS NOT NULL"
						+ " ORDER BY V.A0000,V.A0801B) T WHERE T.RN = 1 "
						+ " ) Y "
					+ " SET A01.ZZXL = Y.A0801A,A01.ZZXLXX = Y.ZGXLXX "
					+ " WHERE Y.A0000 = A01.A0000 "
					+ " and a01.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' ) ";
				updatedata(connb,map,sql);
				//更新 在职 输出学位
				System.out.println("更新 在职 输出学位"+new Date().toString());
				sql="UPDATE A01 A01,(  "
						+" SELECT T.A0000,T.A0901A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = "
						+ "V.A0000 THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0901A,CONCAT(V.A0814, V.A0824) ZGXLXX,(@pre_parent_code := V.A0000) GRN FROM "
						+ "a08 V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 = '2' AND "
						+ "V.A0901B IS NOT NULL ORDER BY V.A0000,V.A0901B) T WHERE T.RN = 1"
						+ " ) Y "
					+ " SET A01.ZZXW = Y.A0901A,A01.ZZXWXX = Y.ZGXLXX "
					+ " WHERE Y.A0000 = A01.A0000 "
					+ " and a01.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' ) ";
				updatedata(connb,map,sql);
				//更新 最高 学历
				System.out.println("更新 最高 学历"+new Date().toString());
				sql="UPDATE A01 A01,(  "
						+" SELECT T.A0000,T.A0801A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 "
						+ "THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0801A,CONCAT(V.A0814, V.A0824) ZGXLXX,(@pre_parent_code := V.A0000) GRN "
						+ "FROM a08 V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 IN('1','2') AND V.A0801B IS NOT NULL"
						+ " ORDER BY V.A0000,V.A0801B) T WHERE T.RN = 1"
						+ " ) Y "
					+ " SET A01.ZGXL = Y.A0801A,A01.ZGXLXX = Y.ZGXLXX "
					+ " WHERE Y.A0000 = A01.A0000 "
					+ " and a01.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' ) ";
				updatedata(connb,map,sql);
				//更新 最高 学历 标志
				System.out.println("更新 最高 学历 标志"+new Date().toString());
				sql="UPDATE A08 A08,( "
						+" SELECT T.A0000,T.A0800 FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = V.A0000 "
						+ "THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0801A,CONCAT(V.A0814, V.A0824) ZGXLXX,(@pre_parent_code := V.A0000) GRN "
						+ "FROM a08 V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 IN('1','2') AND V.A0801B IS NOT NULL"
						+ " ORDER BY V.A0000,V.A0801B) T WHERE T.RN = 1"
						+ " ) Y "
						+ " SET A08.A0834  = '1' "
						+ " WHERE Y.A0800 = A08.A0800"
						+ " and a08.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' )";
				updatedata(connb,map,sql);
				//更新 最高 学位
				System.out.println("更新 最高 学位"+new Date().toString());
				sql="UPDATE A01 A01,(  "
						+" SELECT T.A0000,T.A0901A,T.ZGXLXX FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = "
						+ "V.A0000 THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0901A,CONCAT(V.A0814, V.A0824) ZGXLXX,(@pre_parent_code := V.A0000) GRN FROM "
						+ "a08 V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 IN('1','2') AND "
						+ "V.A0901B IS NOT NULL ORDER BY V.A0000,V.A0901B) T WHERE T.RN = 1"
						+ " ) Y "
					+ " SET A01.ZGXW = Y.A0901A,A01.ZGXWXX = Y.ZGXLXX "
					+ " WHERE Y.A0000 = A01.A0000 "
					+ " and a01.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' ) ";
				updatedata(connb,map,sql);
				//更新 最高 学位标志
				System.out.println("更新 最高 学位标志"+new Date().toString());
				sql="UPDATE A08 A08,( "
						+" SELECT T.A0000,T.A0800 FROM (SELECT (@rowNO := CASE WHEN @pre_parent_code = "
						+ "V.A0000 THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A0800,V.A0901A,CONCAT(V.A0814, V.A0824) ZGXLXX,(@pre_parent_code := V.A0000) GRN FROM "
						+ "a08 V,(SELECT @rowNO := 0 ,@pre_parent_code := '') XXX WHERE V.A0899 = 'true' AND V.A0837 IN('1','2') AND "
						+ "V.A0901B IS NOT NULL ORDER BY V.A0000,V.A0901B) T WHERE T.RN = 1"
						+ " ) Y "
						+ "  SET A08.A0835  = '1' "
						+ " WHERE Y.A0800 = A08.A0800"
						+ " and a08.a0000 in (select a0000 from a02 where a0201b like '"+map.get("sysorgid")+"%' )";
				updatedata(connb,map,sql);
			}
			
		}catch(Exception e1){
			e1.printStackTrace();
			throw new RadowException(e1.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int updatedata(Connection connb,Map<String,String> map,String sql) throws RadowException{
		Statement stmt =null;
		try{
			stmt = connb.createStatement();
			stmt.execute(sql);
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException("更新字段失败!"+e.getMessage());
		}
		return EventRtnType.FAILD;
	}
	public int deletedata(Connection connb,Map<String,String> map,String sql) throws RadowException{
		Statement stmt =null;
		try{
			stmt = connb.createStatement();
			stmt.execute(sql);
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException("删除失败!"+e.getMessage());
		}
		return EventRtnType.FAILD;
	}
	public int impCompetence_userdept(Connection conn,Connection connb,Map<String,String> map) throws RadowException{
		//添加新机构权限
		String selectSql="select "
				+ " concat('"+map.get("sysorgid")+"',substring(id,"+(map.get("idend").length()+1)+")) id "  //主键
				+ " from b01 where id!='-1' ";
		String insertSql="insert into competence_userdept ( userdeptid,userid,b0111,type ) "       
									+ " values("+uuid+",'"+map.get("userid")+"',?,'1')";
		impMysqlToBj( conn, connb, map, selectSql, insertSql);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int copyPhotos(Map<String,String> map) throws RadowException{
		try{
			String photo_path = map.get("filepathv");
			File photos = new File(photo_path);
			if(photos.exists() && photos.isDirectory()){
				//PhotosUtil.moveIMPOtherCmd("",photo_path);
				moveIMPOtherCmd(photo_path);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	/**
	 * 使用命令移动文件到指定路径
	 * @param imprecordid
	 */
	public int moveIMPOtherCmd (String photo_path){
		CommandUtil util = new CommandUtil();
		int isfalse = 0;
		List<String> list = initPath();								//初始化路径 包括 0/1/,0/2/,A/B/...Z/Z
		String osname = System.getProperties().getProperty("os.name");
		String LINUX_remove_ALL="mv -f ";
		String WINDOWS_remove_ALL="cmd /c move /y ";
		String photo_path_temp = photo_path.replace("/", "\\");
		try {
			File file = new File(photo_path_temp);
			String[] filelist = file.list();
			if(osname.equals("Linux") && filelist!=null && filelist.length > 0){ //需确认
				for (String photoname : filelist) {
					String source = photo_path_temp + "/" + photoname +" ";
					String dir = GlobalNames.sysConfig.get("PHOTO_PATH") +"/" + photoname.charAt(0) + "/";
					File file2 = new File(dir);
					if(!file2.exists()){
						file2.mkdirs();
					}
					util.executeCommand(LINUX_remove_ALL + source + dir);
				}
				
			} if(osname.contains("Windows") && filelist!=null && filelist.length > 0){
				String ss=File.separatorChar+"";
				String PHOTO_PATH = GlobalNames.sysConfig.get("PHOTO_PATH");
				
				String fileFirst = filelist[0];
				//如果是文件夹，说明是分文件存储，递归分发照片
				if(new File(photo_path_temp+ss+fileFirst).isDirectory()){
					for (String photoname : filelist) {
						traverse(photo_path_temp+ss+photoname,ss,PHOTO_PATH,WINDOWS_remove_ALL,util,isfalse);
					}
				}else{
					//否则不是文件夹，直接是照片路径
					for (String url : list) {
						String source = "\""+photo_path_temp + "\\" + (url.replace("\\", "")) +"*.*\" ";
						String dir = ("\""+PHOTO_PATH + url + "" +"\"").replace("\\\\", "\\");
						int i = util.executeCommand(WINDOWS_remove_ALL + source + (dir.replace("/", "\\")));
						if(i==1){
							isfalse=1;
						}
					}
					
					//检查是否有未分发的照片，如果有继续分发照片
					file = new File(photo_path_temp);
					filelist = file.list();
					if(filelist!=null && filelist.length > 0){
						for (String photoname : filelist) {
							String source = "\""+photo_path_temp + ss+photoname+"\"" +" ";
							String dir = "\""+PHOTO_PATH +"" + photoname.charAt(0) + ss+photoname.charAt(1)+""+"\"" ;
							File file2 = new File(PHOTO_PATH +"" + photoname.charAt(0)+ss+photoname.charAt(1));
							if(!file2.exists()){
								file2.mkdirs();
							}
							int i = util.executeCommand(WINDOWS_remove_ALL + source + (dir.replace("/", "\\")));
							if(i==1){
								isfalse=1;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isfalse;
	}
	
	public void traverse(String photoname,String ss,String PHOTO_PATH,String WINDOWS_remove_ALL,CommandUtil util,int isfalse) {
        File file = new File(photoname);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                return;
            } else {
                for (File file2 : files) {
                	String fileName = file2.getAbsolutePath();
                    if (file2.isDirectory()) {
                    	traverse(fileName,ss,PHOTO_PATH,WINDOWS_remove_ALL,util,isfalse);
                    } else {//移动照片
                    	String source = "\""+fileName.replaceAll("/", "\\")+"\"" +" ";;
                    	String fName = fileName.substring(fileName.lastIndexOf("\\")+1);
						String dir = "\""+PHOTO_PATH +"" + fName.charAt(0) + ss+fName.charAt(1)+""+"\"" ;
						File file3 = new File(PHOTO_PATH +"" + fName.charAt(0)+ss+fName.charAt(1));
						if(!file3.exists()){
							file3.mkdirs();
						}
						int i = util.executeCommand(WINDOWS_remove_ALL + source + (dir.replace("/", "\\")));
						if(i==1){
							isfalse=1;
						}
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }
	
	/**
	 * 使用命令移动文件到初始化路径中 
	 * @param imprecordid
	 */
	public int moveIMPCmd (String photo_path){
		int isfalse = 0;
		CommandUtil util = new CommandUtil();
		List<String> list = initPath();								//初始化路径 包括 0/1/,0/2/,A/B/...Z/Z
		String osname = System.getProperties().getProperty("os.name");
		String LINUX_remove_ALL="mv -f ";
		String WINDOWS_remove_ALL="cmd /c move /y ";
		//String photo_path_temp = (AppConfig.HZB_PATH + "/temp/upload\\unzip\\"+imprecordid +"\\Photos\\").replace("/", "\\");
		String photo_path_temp = photo_path.replace("/", "\\");
		try {
			if(osname.equals("Linux")){ //需确认
				for (String url : list) {
					String source = (photo_path_temp + "/"  + (url.replace("/", "")) +"*.* ").replace("/", "\\");
					String dir = GlobalNames.sysConfig.get("PHOTO_PATH") + url ;
					util.executeCommand(LINUX_remove_ALL + source + dir);
				}
				
			} if(osname.contains("Windows")){
				for (String url : list) {
					String source = "\""+photo_path_temp + (url.replace("\\", "")) +"*.*\" ";
					String dir = ("\""+GlobalNames.sysConfig.get("PHOTO_PATH") + url + "" +"\"").replace("\\\\", "\\");
					int i = util.executeCommand(WINDOWS_remove_ALL + source + (dir.replace("/", "\\")));
					if(i==1){
						isfalse=1;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isfalse;
	}
	
	public  List<String> initPath() {
		String[] keys= {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G"
				,"H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X"
				,"Y","Z"};
		//String[] keys= {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};	
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < keys.length; i++) {
			for (int j = 0; j < keys.length; j++) {
				String osname = System.getProperties().getProperty("os.name");
				if(osname.equals("Linux")){ //需确认
					String url = "/" + keys[i] + "/" +keys[j] +"/";
					list.add(url);
				} if(osname.contains("Windows")){
					String url = "\\" + keys[i] + "\\" +keys[j] +"\\";
					list.add(url);
				}
			}
		}
		return list;
	}
	
	public int impMysqlToBjA01OrA36(Connection conn,Connection connb,Map<String,String> map,String selectSql,String insertSql,Map<String, String> mapCode) throws RadowException{
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		Statement stmt =null;
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectSql );
			String sql = insertSql;
			pstmt = connb.prepareStatement(sql);
			long l=0l;
			int num = 0;
			if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
				num = 10000;
			}else if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
				num = 5000;
			}
			while(rs.next()){
				++l;
				ResultSetMetaData rsmd=rs.getMetaData();
				int cols=rsmd.getColumnCount();
				for(int i=1;i<=cols;i++){
					if("A0195".equals(rsmd.getColumnName(i))){
						if(rs.getString(i)!=null){
							String A0195 = rs.getString(i).trim();
							if("-1".equals(A0195)||"XXX".equals(A0195)){
								pstmt.setObject(i, "-1");
							}else{
								if(A0195.matches("^([a-zA-Z0-9]{3}[.])*([a-zA-Z0-9]{3})$")){
									pstmt.setObject(i, map.get("sysorgid")+A0195.substring((map.get("idend").length())));
								}else{
									pstmt.setObject(i, "-1");
								}
							}
						}else{
							pstmt.setObject(i, "");
						}
					}else if("A0111A".equals(rsmd.getColumnName(i))||"A0114A".equals(rsmd.getColumnName(i))||"A3627".equals(rsmd.getColumnName(i))){
						if(rs.getString(i)!=null){
							String value = rs.getString(i).trim();
							if(value.matches("^[a-zA-Z0-9]+$")){
								pstmt.setObject(i,mapCode.get(value));
							}else{
								pstmt.setObject(i, value);
							}
						}else{
							pstmt.setObject(i, "");
						}
					}else{
						pstmt.setObject(i, rs.getString(i));
					}
					//String ss=rs.getString(i);
				}
				pstmt.addBatch();
				if(l%num==0){
					pstmt.executeBatch();
					pstmt.clearParameters();
				}
			}
			if(l%num!=0){
				pstmt.executeBatch();
				pstmt.clearParameters();
			}
			pstmt.close();
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			try{
				if(pstmt!=null){
					pstmt.close();
				}
				if(rs!=null){
					rs.close();
				}
			}catch(Exception e1){
			}
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int impMysqlToBj(Connection conn,Connection connb,Map<String,String> map,String selectSql,String insertSql) throws RadowException{
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		Statement stmt =null;
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectSql );
			String sql = insertSql;
			pstmt = connb.prepareStatement(sql);
			long l=0l;
			int num = 0;
			if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
				num = 10000;
			}else if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
				num = 5000;
			}
			while(rs.next()){
				++l;
				ResultSetMetaData rsmd=rs.getMetaData();
				int cols=rsmd.getColumnCount();
				for(int i=1;i<=cols;i++){
					if("A0201B".equals(rsmd.getColumnName(i))){
						if(rs.getString(i)!=null){
							String A0201B = rs.getString(i).trim();
							if("-1".equals(A0201B)||"XXX".equals(A0201B)){
								pstmt.setObject(i, "-1");
							}else{
								if(A0201B.matches("^([a-zA-Z0-9]{3}[.])*([a-zA-Z0-9]{3})$")){
									pstmt.setObject(i, map.get("sysorgid")+A0201B.substring((map.get("idend").length())));
								}else{
									pstmt.setObject(i, "-1");
								}
							}
						}else{
							pstmt.setObject(i, "");
						}
					}else{
						pstmt.setObject(i, rs.getString(i));
					}
					//String ss=rs.getString(i);
				}
				pstmt.addBatch();
				if(l%num==0){
					pstmt.executeBatch();
					pstmt.clearParameters();
				}
			}
			if(l%num!=0){
				pstmt.executeBatch();
				pstmt.clearParameters();
			}
			pstmt.close();
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			try{
				if(pstmt!=null){
					pstmt.close();
				}
				if(rs!=null){
					rs.close();
				}
			}catch(Exception e1){
			}
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int impA05Zwcc(Connection conn,Connection connb,Map<String,String> map) throws RadowException{
		String selectSql="select "
						//+ " id,"  //主键
						+ " sid,"   //人员主键(外键)
						+ " if(a0531='1','0','1') a0531," //类别（职务层次、职级标志）
						+ " a0501b,"//职务层次（职级）
						+ " a0504," //批准日期
						
						+ " a0511," //批准文号
						+ " a0517," //终止日期
						+ " a0524"  //状态
						+ " from a05";
			String insertSql="insert into a05 ( a0500,a0000,a0531,a0501b,a0504,"
										+ "a0511,a0517,a0524 "         
										+ " ) "       
										+ " values("+uuid+",?,?,?,?,?,?,?)";
			impMysqlToBj( conn, connb, map, selectSql, insertSql);
			return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int impA36(Connection conn,Connection connb,Map<String,String> map,Map<String,String> mapCode) throws RadowException{
		String selectSql="select "
				//+ " id,    "//主键
				+ " sid,   "//人员主键(外键)
				+ " a3601, "//姓名
				+ " a3604a,"//称谓
				+ " a3607, "//出生日期
				
				+ " a3627, "//政治面貌
				+ " a3611, "//工作单位及职务
				+ " a3699 "//序号
			+ " from a36";
		String insertSql="insert into a36 ( "
				+ " a3600,    "//主键
				+ " a0000,   "//人员主键(外键)
				+ " a3601, "//姓名
				+ " a3604a,"//称谓
				+ " a3607, "//出生日期
				
				+ " a3627, "//政治面貌
				+ " a3611, "//工作单位及职务
				+ " sortid "//序号
				+ " ) "       
				+ " values("+uuid+",?,?,?,? "
						+ ",? ,? ,?  )";
		impMysqlToBjA01OrA36( conn, connb, map, selectSql, insertSql, mapCode);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int impA15(Connection conn,Connection connb,Map<String,String> map) throws RadowException{
		String selectSql="select "
				//+ " id,   "//主键
				+ " sid,  "//人员主键(外键)
				+ " a1517,"//考核结论
				+ " a1521 " //考核年度
			+ " from a15 ";
		String insertSql="insert into a15 ( "
				+ " a1500,   " //主键
				+ " a0000,  " //人员主键(外键)
				+ " a1517," //考核结论
				+ " a1521 "  //考核年度
				+ " ) "       
				+ " values("+uuid+",?,?,? )";
		impMysqlToBj( conn, connb, map, selectSql, insertSql);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int impA14(Connection conn,Connection connb,Map<String,String> map) throws RadowException{
		String selectSql="select "
				//+ " id, "   //主键
				+ " sid, "  //人员主键(外键)
				+ " a1404a,"//奖惩名称
				+ " a1404b,"//奖惩名称代码
				+ " a1407, "//批准日期
				
				+ " a1411a,"//批准机关
				+ " a1414, " //批准机关级别
				+ " a1415, " //受奖惩时职务层次
				+ " a1424, " //撤销日期
				+ " a1428 "  //批准机关性质
			+ " from a14";
		String insertSql="insert into a14 ( "
				+ " a1400, "   //主键
				+ " a0000, "  //人员主键(外键)
				+ " a1404a,"//奖惩名称
				+ " a1404b,"//奖惩名称代码
				+ " a1407, "//批准日期
				
				+ " a1411a,"//批准机关
				+ " a1414, " //批准机关级别
				+ " a1415, " //受奖惩时职务层次
				+ " a1424, " //撤销日期
				+ " a1428 "  //批准机关性质
				+ " ) "       
				+ " values("+uuid+",?,?,?,?,"
				+ "?,?,?,?,?)";
		impMysqlToBj( conn, connb, map, selectSql, insertSql);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int impA57(Connection conn,Connection connb,Map<String,String> map) throws RadowException{
		String selectSql="";
		ResultSet rs =null;
		Statement stmt =null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select LOCATE('\\\\',A5714),LOCATE('/',A5714) from a57 limit 0,1");
			while(rs.next()){
				String n1 = rs.getString(1);
				String n2 = rs.getString(2);
				if(Integer.parseInt(n1)>0||Integer.parseInt(n2)>0){//说明A5714中有分目录存放
					selectSql="select "
							+ " id, "
							+ " CONCAT(id,'.jpg') a5714,"
							+ " CONCAT(id,'.jpg') photoname,"
							+ " if(length(id)=0,'',concat(concat(left(id, 1),'/'),concat(substring(id,2,1),'/'))) photopath"
						+ " from a57";
				}else{
					selectSql="select "
							+ " id, "
							+ " a5714,"
							+ " left(A5714,if( LOCATE('\\|',A5714)>0,LOCATE('\\|',A5714)-1,length(a5714) ) ) photoname,"
							+ " if(length(a5714)=0,'',concat(concat(left(A5714, 1),'/'),concat(substring(A5714,2,1),'/'))) photopath"
						+ " from a57";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null){
					rs.close();
				}
				if(stmt!=null){
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
//		if(DBType.ORACLE==DBUtil.getDBType()){
//			selectSql=" select "
//					+ " id, "
//					+ " a5714,"
//					+ " SUBSTR(a5714,0,instr(a5714,'|')-1) photodata, "
//					+ " decode(length(a5714),0,'',null,'',SUBSTR(a5714,0,1)||'/'||SUBSTR(a5714,2,1)||'/') photopath "
//				+ " from a57";
//		}else if(DBType.MYSQL==DBUtil.getDBType()){
//			selectSql="select "
//					+ " id, "
//					+ " a5714,"
//					+ " left(A5714, LOCATE('\\|',A5714)-1) photodata,"
//					+ " if(length(a5714)=0,'',concat(concat(left(A5714, 1),'/'),concat(substring(A5714,2,1),'/'))) photopath"
//				+ " from a57";
//		}
		String insertSql="insert into a57 ( "
				+ " a0000, "
				+ " a5714,"
				+ " updated,"
				+ " photodata,"
				+ " photoname,"
				
				+ " photstype,"
				+ " photopath,"
				+ " picstatus "
				+ " ) "       
				+ " values(?,?,'1','',?,'jpg',?,'1')";
		impMysqlToBj( conn, connb, map, selectSql, insertSql);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int impA08Xlxw(Connection conn,Connection connb,Map<String,String> map) throws RadowException{
		String selectSql="select "
					//+ " id,"    //主键
					+ " sid,"   //人员主键(外键)
					+ " a0801a,"//学历名称
					+ " a0801b,"//学历代码
					+ " a0901a,"//学位名称
					
					+ " a0901b,"//学位代码
					+ " a0804," //入学时间
					+ " a0807," //毕（肄）业时间
					+ " a0904," //学位授予时间
					+ " a0814," //学校（单位）名称
					
					+ " a0824," //所学专业名称
					+ " a0827," //所学专业类别
					+ " a0837," //教育类别
					+ " a0811," //学制年限
					+ " a0898, "  //输出标识
					+ " if(a0898='1','true','false') a0899 "//输出标识
				+ " from a08";
		String insertSql="insert into a08 ( "
				+ " a0800,"//主键
				+ " a0000,"//人员主键(外键)
				+ " a0801a,"//学历名称
				+ " a0801b,"//学历代码
				+ " a0901a,"//学位名称
				
				+ " a0901b,"//学位代码
				+ " a0804," //入学时间
				+ " a0807," //毕（肄）业时间
				+ " a0904," //学位授予时间
				+ " a0814," //学校（单位）名称
				
				+ " a0824," //所学专业名称
				+ " a0827," //所学专业类别
				+ " a0837," //教育类别
				+ " a0811," //学制年限
				+ " a0898, "  //输出标识
				+ " a0899 "//任免表输出.最高学历的标识。true—是；false—否。
				+ " ) "       
				+ " values("+uuid+",?,?,?,?,"
				+ "?,?,?,?,?,"
				+ "?,?,?,?,?,?)";
		impMysqlToBj( conn, connb, map, selectSql, insertSql);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int ImpA99z1bcxx(Connection conn,Connection connb,Map<String,String> map) throws RadowException{
		String selectSql="select "
				+ " id,       "//人员主键(外键)
				+ " a99z101,  "//是否考录
				+ " a99z102,  "//录用时间
				+ " a99z103,  "//是否选调生
				+ " a99z104 "//进入选调生时间

			+ " from a99z1";
		String insertSql="insert into a99z1 ( "
				+ " a0000,       "//人员主键(外键)
				+ " a99z100,  "//主键
				+ " a99z101,  "//是否考录
				+ " a99z102,  "//录用时间
				+ " a99z103,  "//是否选调生
				+ " a99z104  "//进入选调生时间
				+ " ) "       
				+ " values(?,"+uuid+",?,?,?,?)";
		impMysqlToBj( conn, connb, map, selectSql, insertSql);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int impA01Ryxx(Connection conn,Connection connb,Map<String,String> map,Map<String,String> mapCode) throws RadowException{
		String selectSql="select "
					+ " id," //人员主键
					+ " a0101," //姓名
					+ " a0104," //性别 
					+ " a0107," //出生年月
					+ " a0111a," //籍贯
					+ " a0114a," //出生地
					+ " a0115a," //成长地
					+ " a0117," //民族               
					+ " a0128," //健康状况
					+ " a0134," //参加工作时间
					+ " a0140," //入党时间文字
					+ " a0141," //政治面貌
					+ " a0144," //入党时间
					+ " a3921," //第二党派
					+ " a3927," //第三党派
					+ " a0160," //人员类别
					+ " a0163," //人员管理状态
					+ " a0165," //管理类别
					+ " a0184," //身份证号
					+ " a0187a," //熟悉专业有何特长
					+ " a0192," //现工作单位及职务简称
					+ " a0192a," //现工作单位及职务全称
					+ " a0221," //现职务层次
					+ " a0288," //任现职务层次时间
					+ " a0192e," //现职级
				
					+ " a0192c," //任现职级时间
					+ " a0196," //专业技术职务
					+ " a0197," //是否具有两年以上基层工作经历
					+ " a0195," //统计关系所在单位
					+ " a1701," //简历
					+ " a14z101," //奖惩综述
					+ " a15z101," //年度考核结果综述
					+ " a0120," //级别
					+ " a0121," //编制类型
					+ " a0122," //专业技术类公务员任职资格
					+ " a2949," //公务员登记时间
					+ " a0180" //备注
				+ " from a01";
			String insertSql="insert into a01 ( "
					+ " a0000," //人员主键
					+ " a0101," //姓名
					+ " a0104," //性别 
					+ " a0107," //出生年月
					+ " a0111a," //籍贯
					
					+ " a0114a," //出生地
					+ " a0115a," //成长地
					+ " a0117," //民族               
					+ " a0128," //健康状况
					+ " a0134," //参加工作时间
					
					+ " a0140," //入党时间文字
					+ " a0141," //政治面貌
					+ " a0144," //入党时间
					+ " a3921," //第二党派
					+ " a3927," //第三党派
					
					+ " a0160," //人员类别
					+ " a0163," //人员管理状态
					+ " a0165," //管理类别
					+ " a0184," //身份证号
					+ " a0187a," //熟悉专业有何特长
					
					+ " a0192," //现工作单位及职务简称
					+ " a0192a," //现工作单位及职务全称
					+ " a0221," //现职务层次
					+ " a0288," //任现职务层次时间
					+ " A0192e," //现职级
				
					+ " a0192c," //任现职级时间
					+ " a0196," //专业技术职务
					+ " a0197," //是否具有两年以上基层工作经历
					+ " a0195," //统计关系所在单位
					+ " a1701," //简历
					
					+ " a14z101," //奖惩综述
					+ " a15z101," //年度考核结果综述
					+ " a0120," //级别
					+ " a0121," //编制类型
					+ " a0122," //专业技术类公务员任职资格
					
					+ " a2949," //公务员登记时间
					+ " a0180, " //备注
					+ " status "
					+ " ) "       
					+ " values(?,?,?,?,?,"
							+ "?,?,?,?,?"
							+ ",?,?,?,?,?"
							+ ",?,?,?,?,?"
							+ ",?,?,?,?,?"
							+ ",?,?,?,?,?"
							+ ",?,?,?,?,?"
							+ ",?,?,'1')";
			impMysqlToBjA01OrA36( conn, connb, map, selectSql, insertSql, mapCode);
			return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int impA06Zyjs(Connection conn,Connection connb,Map<String,String> map) throws RadowException{
		String selectSql="select "
							//+ " id "  //主键
							+ " sid, "  //人员主键(外键)
							+ " a0601, "//专业技术资格代码
							+ " a0602, "//专业技术资格名称
							+ " a0604, "//获得资格日期
							
							+ " a0607, "//取得资格途径
							+ " a0611, "//评委会或考试名称
							+ " if(a0699='1','true','false') a0699 "//输出标识
							+ " from a06";
		String insertSql="insert into a06 ( a0600,a0000,a0601,a0602,a0604,"
										+ "a0607,a0611,a0699 "         
										+ " ) "       
										+ " values("+uuid+",?,?,?,?,?,?,?)";
		impMysqlToBj( conn, connb, map, selectSql, insertSql);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int impA02Zw(Connection conn,Connection connb,Map<String,String> map) throws RadowException{
		String selectSql="";
		String insertSql="";
		selectSql=" select "
				//+ " id,"     //主键            
				+ " sid,   "  //人员主键(外键)      
				+ " a0201a,"  //任职机构名称        
				+ " a0201b,"  //任职机构代码        
				+ " a0201d,"  //是否领导成员
		
				+ " a0201e,"  //成员类别          
				+ " a0215a,"  //职务名称          
				+ " if(a0219='1','1','2') a0219, "  //是否领导职务        
				+ " a0223, "  //职务排序          
				+ " a0225, "  //集体内排序 
				
				+ " a0243, "  //任职时间          
				+ " a0245, "  //任职文号          
				+ " a0247, "  //选拔任用方式        
				+ " a0251b,"  //是否破格提拔        
				+ " a0255, "  //任职状态
				
				+ " a0265, "  //免职时间          
				+ " a0267, "  //免职文号          
				+ " a0272, "  //职务变动原因综述      
				+ " if(a0281='1','true','false') a0281, "  //职务输出标识        
				+ " a0279 "  //主职务
				+ " from a02 ";
		insertSql="insert into a02 ( a0200,"   //主键              
				+ " a0000,   "//人员主键(外键)       
				+ " a0201a,"  //任职机构名称        
				+ " a0201b,"  //任职机构代码        
				+ " a0201d,"   //是否领导成员        
				                              
				+ " a0201e,"  //成员类别          
				+ " a0215a,"  //职务名称          
				+ " a0219, "  //是否领导职务        
				+ " a0223, "  //职务排序          
				+ " a0225, "  //集体内排序         
				                              
				+ " a0243, "  //任职时间          
				+ " a0245, "  //任职文号          
				+ " a0247, "  //选拔任用方式        
				+ " a0251b,"  //是否破格提拔        
				+ " a0255, "  //任职状态   
		
				+ " a0265, "  //免职时间         
				+ " a0267, "  //免职文号          
				+ " a0272, "  //职务变动原因综述      
				+ " a0281, "  //职务输出标识            
				+ " a0279 "   //主职务              
				+ " ) "       
				+ " values("+uuid+",?,?,?,?"
				+ ",?,?,?,?,?"
				+ ",?,?,?,?,?"
				+ ",?,?,?,?,?)";
		impMysqlToBj( conn, connb, map, selectSql, insertSql);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 获取被覆盖机构的上级id
	 * @return
	 * @throws RadowException 
	 */
	public String  getUpId(Map<String,String> map,Connection connb) throws RadowException{
		String upid="";
		ResultSet rs =null;
		Statement stmt =null;
		try{
			stmt=connb.createStatement();
			rs=stmt.executeQuery("select b0121 from b01 where b0111='"+map.get("sysorgid")+"' ");
			long l=0l;
			while(rs.next()){
				++l;
				ResultSetMetaData rsmd=rs.getMetaData();
				int cols=rsmd.getColumnCount();
				for(int i=1;i<=cols;i++){
					upid=rs.getString(i);
				}
			}
			if(l==0){
				stmt.close();
				rs.close();
				throw new RadowException("没有根机构！");
			}
			stmt.close();
			rs.close();
		}catch(Exception e){
			try{
				if(stmt!=null){
					stmt.close();
				}
				if(rs!=null){
					rs.close();
				}
			}catch(Exception e1){
				
			}
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return upid;
	}
	
	/**
	 * 获取导出的根机构
	 * @return
	 * @throws RadowException 
	 */
	public String  getGjdid(Connection conn) throws RadowException{
		String gjdid="";
		ResultSet rs =null;
		Statement stmt =null;
		try{
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select b0111 from gwyinfo ");
			long l=0l;
			while(rs.next()){
				++l;
				ResultSetMetaData rsmd=rs.getMetaData();
				int cols=rsmd.getColumnCount();
				for(int i=1;i<=cols;i++){
					gjdid=rs.getString(i);
				}
			}
			if(l==0){
				stmt.close();
				rs.close();
				throw new RadowException("没有根机构！");
			}
			stmt.close();
			rs.close();
		}catch(Exception e){
			try{
				if(stmt!=null){
					stmt.close();
				}
				if(rs!=null){
					rs.close();
				}
			}catch(Exception e1){
				
			}
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return gjdid;
	}
	
	/**
	 * 删除被选择的机构与其下级机构
	 * @param connb
	 * @param map
	 * @return
	 * @throws RadowException
	 */
	public int deleteGjg(Connection connb,Map<String,String> map) throws RadowException{
		Statement stmt =null;
		try{
			stmt=connb.createStatement();
			String sqltmp="";
			if("system".equals(map.get("username"))){
				sqltmp="delete from b01  "
						+ " where b0111 like '"+map.get("sysorgid")+"%' " ;
			}else{
				sqltmp="delete from b01  "
						+ " where b0111 like '"+map.get("sysorgid")+"%' "
						+ " and  b0111 in  (select s.b0111 from competence_userdept s "
									+ " where s.userid='"+map.get("userid")+"'  )";
			}
			
			stmt.execute(sqltmp);
			stmt.close();
		}catch(Exception e){
			try{
				if(stmt!=null){
					stmt.close();
				}
			}catch(Exception e1){
				
			}
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int insertGjg(String idend,String upid,Connection connb,Connection conn,Map<String,String> map) throws RadowException{
		ResultSet rs =null;
		Statement stmt =null;
		PreparedStatement pstmt = null;
		try{
			stmt=conn.createStatement();
			rs = stmt.executeQuery("select id, "    //单位（系统）编码
					+ " sid,    " //父级单位（系统）编码
					+ " b0101,  " //机构名称
					+ " b0104,  " //机构简称
					+ " b0114,  " //机构编码
					
					+ " b0117,  " //所在政区
					+ " b0124,  " //隶属关系
					+ " b0127,  " //机构级别
					+ " b0131,  " //机构类别
					+ " b0194,  " //法人单位标识
					
					+ " b0227,  " //行政编制数
					+ " b0232,  " //事业编制数（参照）
					+ " b0233,  " //事业编制数（非参照）
					+ " b0236,  " //工勤编制数
					+ " b0234,  " //其他编制数
					
					+ " b0238,  " //参照公务员法管理审批时间
					+ " b0239,  " //参照公务员法管理审批文号
					+ " b0150,  " //内设机构领导职数
					+ " b0183,  " //正职领导职数
					+ " b0185,  " //副职领导职数
					
					+ " b0180,  " //备注
					+ " sortid "  //同级排序
					+ " from b01 where id='"+idend+"' "
					);
			String sql = "insert into b01 (b0111, b0121, b0101, b0107, b0114,"
					+ " b0117,b0124,b0127,b0131,b0194, "
					+ " b0227,b0232,b0233,b0236,b0234,"
					+ " b0238,b0239,b0150,b0183,b0185,"
					+ " b0180,sortid"
					+ " ) "
			+ " values(?,?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,?,?,?)";
			
			pstmt = connb.prepareStatement(sql);
			long l=0l;
			while(rs.next()){
				++l;
				ResultSetMetaData rsmd=rs.getMetaData();
				int cols=rsmd.getColumnCount();
				for(int i=1;i<=cols;i++){
					if(i==1){
						pstmt.setObject(i,map.get("sysorgid") );
					}else if(i==2){
						pstmt.setObject(i,upid );
					}else{
						pstmt.setObject(i, rs.getString(i));
					}
				}
				pstmt.addBatch();
			}
			if(l==0){
				stmt.close();
				rs.close();
				throw new RadowException("没有根机构！");
			}
			pstmt.executeBatch();
			pstmt.clearParameters();
			pstmt.close();
			stmt.close();
			rs.close();
		}catch(Exception e){
			try{
				if(stmt!=null){
					stmt.close();
				}
				if(rs!=null){
					rs.close();
				}
				if(pstmt!=null){
					pstmt.close();
				}
			}catch(Exception e1){
				
			}
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 导入机构信息
	 * @return
	 * @throws SQLException 
	 */
	public int impUnitInfo(Connection conn,Connection connb,Map<String,String> map) throws RadowException{
		try{
			//获取导出数据的根结点id
			String idend=getGjdid(conn);
			map.put("idend", idend);
			//被覆盖的机构的上级id
			String upid=getUpId(map,connb);
			//删除被覆盖的机构与其下级机构
			deleteGjg(connb,map);
			//插入根机构 
			insertGjg(idend,upid,connb,conn,map);
			int idendlength=idend.length()+1;
			String selectSql="select concat('"+map.get("sysorgid")+"',substring(id,"+idendlength+")) id, "    //单位（系统）编码
					+ " concat('"+map.get("sysorgid")+"',substring(sid,"+idendlength+")) sid,    " //父级单位（系统）编码
					+ " b0101,  " //机构名称
					+ " b0104,  " //机构简称
					+ " b0114,  " //机构编码
					
					+ " b0117,  " //所在政区
					+ " b0124,  " //隶属关系
					+ " b0127,  " //机构级别
					+ " b0131,  " //机构类别
					+ " b0194,  " //法人单位标识
					
					+ " b0227,  " //行政编制数
					+ " b0232,  " //事业编制数（参照）
					+ " b0233,  " //事业编制数（非参照）
					+ " b0236,  " //工勤编制数
					+ " b0234,  " //其他编制数
					
					+ " b0238,  " //参照公务员法管理审批时间
					+ " b0239,  " //参照公务员法管理审批文号
					+ " b0150,  " //内设机构领导职数
					+ " b0183,  " //正职领导职数
					+ " b0185,  " //副职领导职数
					
					+ " b0180,  " //备注
					+ " sortid "  //同级排序
					+ " from b01 where id!='"+idend+"' ";
			String insertSql="insert into b01 (b0111, b0121, b0101, b0104, b0114,"
							+ " b0117,b0124,b0127,b0131,b0194, "
							+ " b0227,b0232,b0233,b0236,b0234,"
							+ " b0238,b0239,b0150,b0183,b0185,"
							+ " b0180,sortid"
							+ " ) "
							+ " values(?,?,?,?,?,"
							+ "?,?,?,?,?,"
							+ "?,?,?,?,?,"
							+ "?,?,?,?,?,?,?)";
			impMysqlToBj( conn, connb, map, selectSql, insertSql);
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 建立连接
	 * @return
	 * @throws RadowException 
	 */
	public Connection createConnection(String port,String sid,String user,String password,Map<String,String> map,int num) throws RadowException{
		Connection conn=null;
		//String str="?useUnicode=true&amp;characterEncoding=utf8&amp;useOldAliasMetadataBehavior=true&amp;zeroDateTimeBehavior=convertToNull&amp;rewriteBatchedStatements=true";
		String url = "jdbc:mysql://localhost:"+port+"/"+sid;
		// 加载驱动
		try{
			num++;
			Class.forName("com.mysql.jdbc.Driver");
			// 获取数据库连接
			conn = DriverManager.getConnection(url, user, password);
		}catch(Exception e){
			try{
				num++;
				Thread.currentThread();
				Thread.sleep(3000);//毫秒
				if(num>20){
				}else{
					conn=createConnection( port, sid, user, password, map, num);
				}
			}catch(Exception e1){
			}
	    }
		return conn;
	}

	
	/**
	 *执行.bat文件，注册并启动mysql服务 
	 * @return
	 * @throws RadowException 
	*/
	public int executeBatFile(Map<String,String> map) throws RadowException{
		String ss=File.separatorChar+"";
		//获取文件目录路径
		String strcmd = "cmd /c start  "+map.get("mysqlpath")+ss+"install.bat";  //调用我们在项目目录下准备好的bat文件，如果不是在项目目录下，则把“你的文件名.bat”改成文件所在路径。
		run_cmd(strcmd);  //调用上面的run_cmd方法执行操作
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int run_cmd(String strcmd) throws RadowException{
		Process ps = null;  //Process可以控制该子进程的执行或获取该子进程的信息。
		try{
			Runtime rt = Runtime.getRuntime(); //Runtime.getRuntime()返回当前应用程序的Runtime对象
		    ps = rt.exec(strcmd);   //该对象的exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
		    ps.waitFor();  //等待子进程完成再往下执行。

			int i = ps.exitValue();  //接收执行完毕的返回值
			if (i == 0) {
			    //System.out.println("执行完成.");
			} else {
				ps.destroy();  //销毁子进程
				ps = null; 
				throw new RadowException("mysql服务启动失败!");
			   // System.out.println("执行失败.");
			}
			ps.destroy();  //销毁子进程
			ps = null; 
		}catch(Exception e){
			try{
				if(ps!=null){
					ps.destroy();  //销毁子进程
					ps = null; 
				}
			}catch(Exception e1){
			}
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 生成mysql注册启动 关闭删除文件,并拷贝到mysql文件夹下
	 * @return
	 */
	public int createBatFile(Map<String,String> map) throws RadowException{
		try{
			String ss=File.separatorChar+"";
			
			String mbpath1=this.request.getSession().getServletContext().getRealPath("/")+"template\\mysql\\install.bat";
			mbpath1=mbpath1.replace("\\", ss);
			mbpath1=mbpath1.replace("/", ss);
			copyFile(mbpath1, map.get("mysqlpath")+ss+"install.bat");
			
			mbpath1=this.request.getSession().getServletContext().getRealPath("/")+"template\\mysql\\uninstall.bat";
			mbpath1=mbpath1.replace("\\", ss);
			mbpath1=mbpath1.replace("/", ss);
			
			copyFile(mbpath1, map.get("mysqlpath")+ss+"uninstall.bat");
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 拷贝一个文件中的内容到另一个文件中
	 * @param sourcefile
	 * @param targetFile
	 * @return
	 * @throws RadowException
	 */
	public int copyFile(String sourcefile, String targetFile) throws RadowException {
		FileOutputStream out = null;
		FileInputStream input = null;
		File filesource=null;
		File filetarget= null;
		try{
			filesource= new File(sourcefile);
			filetarget= new File(targetFile);
			if(!filetarget.exists()){
				filetarget.createNewFile();
			}
			// 新建文件输入流并对它进行缓冲
			input = new FileInputStream(filesource);
			BufferedInputStream inbuff = new BufferedInputStream(input);
			// 新建文件输出流并对它进行缓冲
			out = new FileOutputStream(filetarget);
			BufferedOutputStream outbuff = new BufferedOutputStream(out);
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len = 0;
			while ((len = inbuff.read(b)) != -1) {
				outbuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outbuff.flush();
			// 关闭流
			inbuff.close();
			outbuff.close();
			out.close();
			input.close();
		}catch(IOException e){
			try{
				if(out!=null){
					out.close();
				}
				if(input!=null){
					input.close();
				}
			}catch(Exception e1){
			}
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}catch(Exception e){
			try{
				if(out!=null){
					out.close();
				}
				if(input!=null){
					input.close();
				}
			}catch(Exception e1){
			}
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * 获取datas photos 路径
	 * @param path
	 * @param map
	 * @return
	 * @throws RadowException
	 */
	public int traverseFolder2(String path,Map<String,String> map) throws RadowException {
		try{
			File file = new File(path);
	        if (file.exists()) {
	            File[] files = file.listFiles();
	            if (files.length == 0) {
	            } else {
	                for (File file2 : files) {
	                    if (file2.isDirectory()) {
	                    	String filename=file2.getName();
	        				if(filename.equalsIgnoreCase("Datas")){
	        					String tmppth=file2.getAbsolutePath();
	        					map.put("dataspath",tmppth );
	        				}
	        				if(filename.equalsIgnoreCase("Photos")){
	        					String tmppth=file2.getAbsolutePath();
	        					map.put("Photospath",tmppth );
	        				}
	                        traverseFolder2(file2.getAbsolutePath(),map);
	                    } else {
	                        
	                    }
	                }
	            }
	        } else {
	        }
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
        return EventRtnType.NORMAL_SUCCESS;
    }
	
	/**
	 * 解压mysql库
	 * @param filepathv
	 * @return
	 */
	public int startUnzip(String filepathv,Map<String,String> map) throws RadowException{
		try{
			String ss=File.separatorChar+"";
			filepathv.replace("/", ss);
			filepathv.replace("\\", ss);
			//获取文件目录路径
			String path=filepathv.substring(0, filepathv.lastIndexOf(ss));
			String filename=filepathv.substring(filepathv.lastIndexOf(ss)+1, filepathv.lastIndexOf("."));
			map.put("zippath", filepathv.substring(0, filepathv.lastIndexOf(".")));
			map.put("zipname", filename);
			unZip(filepathv, path+ss+filename);
			traverseFolder2(path+ss+filename, map); 
			//解压data文件夹
			File file = new File(map.get("dataspath"));
			File[] files = file.listFiles();
			for (File file2 : files) {
				String fielname=file2.getName();
				if(fielname.lastIndexOf(".")>1){
					map.put("mysqlname", fielname.substring(0,  fielname.lastIndexOf(".")));
					map.put("mysqlpath",map.get("dataspath")+ss+map.get("mysqlname") );
					break;
				}
			}
			File filezip=new File(map.get("mysqlpath")+".zip");
			if(filezip.exists()){
				unZip(map.get("mysqlpath")+".zip", map.get("dataspath"));
			}else{
				for (File file2 : files) {
					String fielname=file2.getName();
					map.put("mysqlname", fielname);
					map.put("mysqlpath",map.get("dataspath")+ss+map.get("mysqlname") );
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//解压文件到指定目录
		/**
		 * 
		 * @param source 压缩包所在路径
		 * @param outputDir 解压后所在目录
		 * @return
		 * @throws IOException
		 */
			public  boolean unZip(String source,String outputDir) throws IOException {     
		        File file = new File(source);
				ZipFile zipFile = null;  
				String ss=File.separatorChar+""; 
				InputStream in = null;    
                OutputStream out = null; 
                ZipEntry entry=null;
                File tmpFile=null;
		        try { 
		            zipFile =  new ZipFile(file, "gbk"); 
		            createDirectory(outputDir,null);//创建输出目录 
		            Enumeration<?> enums = zipFile.getEntries(); 
		            while(enums.hasMoreElements()){ 
		                entry = (ZipEntry) enums.nextElement(); 
		                if(entry.isDirectory()){//是目录
		                    createDirectory(outputDir,entry.getName());//创建空目录                
		                }else{//是文件      
		                    tmpFile = new File((outputDir + "/" + entry.getName()).replace("/", ss).replace("\\", ss));   
		                    createDirectory((tmpFile.getParent() + "/").replace("/", ss).replace("\\", ss),null);//创建输出目录  
		                        in = zipFile.getInputStream(entry);;    
		                        out = new FileOutputStream(tmpFile);  
		                        int length = 0;  
		                        byte[] b = new byte[2048];   
		                        while((length = in.read(b)) != -1){  
		                            out.write(b, 0, length);  
		                        }  
		                        in.close(); 
		                        out.close();
		                }  
		            }  
		            zipFile.close(); 
		            return true;
		        } catch (Exception e) {
		        	try{  
		                if(zipFile != null){  
		                    zipFile.close();  
		                } 
		                if(in != null){  
		                	in.close();  
		                } 
		                if(out != null){  
		                	out.close();  
		                } 
			          }catch(Exception e1){  
			             e1.printStackTrace();
			          } 
		        	e.printStackTrace();
		            throw new IOException("解压缩文件出现异常",e);
		        }      
		    } 
			
			//解压构建目录
			/**
			 * 
			 * @param outputDir 路径
			 * @param subDir 子目录
			 * @throws FileNotFoundException 
			 */
		    public static void createDirectory(String outputDir,String subDir) throws FileNotFoundException{
		    	String ss=File.separatorChar+"";
		    	try{
		    		File file = new File(outputDir);  
			        if(!(subDir == null || subDir.trim().equals(""))){//子目录不为空  
			            file = new File((outputDir + "/" + subDir).replace("/", ss).replace("\\", ss));  
			        }  
			        if(!file.exists()){  
			              if(!file.getParentFile().exists())
			                  file.getParentFile().mkdirs();
			            file.mkdirs();  
			        } 
		    	}catch(Exception e){
		    		e.printStackTrace();
		    		throw new FileNotFoundException(e.getMessage());
		    	}
		         
		    }
		    
		    private Map<String, String> getCodeValues() {
				Map<String, String> map = new HashMap<String, String>();
				HBSession sess = HBUtil.getHBSession();
				List<Object[]> list = sess.createSQLQuery("SELECT CODE_NAME3,code_value FROM code_value WHERE CODE_TYPE IN('ZB01','GB4762')").list();
				for(Object[] obj : list){
					map.put(obj[1]+"", obj[0]+"");
				}
				return map;
			}

}
