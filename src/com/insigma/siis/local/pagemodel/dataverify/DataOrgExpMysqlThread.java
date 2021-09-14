package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;



import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.worktable.openapi.util;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.CommandUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBNewUtil;
import com.insigma.siis.local.business.utils.kingbs.CommandImpUtil;
import com.insigma.siis.local.business.utils.kingbs.EncryptUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.util.SqlUtil;
import com.insigma.siis.local.util.TYGSsqlUtil;
import com.insigma.siis.local.util.ZipCompressing;

public class DataOrgExpMysqlThread implements Runnable {
	
	private String uuid;
	private UserVO userVo;
	private DataOrgExpMysql control;
	private String no;
	private Map<String, String> map;
	private String contact;
	private String tel;
	private String notes;
	private Map<String, String> mapCodeValues;
	
	public DataOrgExpMysqlThread(String uuid,UserVO userVo,DataOrgExpMysql control, Map<String, String> map, String no,
			String contact,String tel,String notes) {
		super();
		this.uuid = uuid;
		this.userVo = userVo;
		this.control = control;
		this.map = map;
		this.no = no;
		this.contact = contact;
		this.tel = tel;
		this.notes = notes;
		this.mapCodeValues = mapCodeValues;
	}

	@Override
	public void run() {
		StopWatch w = new StopWatch();
		String infile = "";					 					// 文件
		String process_run = "2";								// 过程序号
		String path = control.getPath();
		String zippath = control.getZippath();
		String tables[] = {"gwyinfo", "A01", "A02", "A05", "A06", "A08", "A14", "A15", "A36", "A57", "A99Z1", "B01", "PHOTO"};
		
		try {
			w.start();
			
			HBSession sess = HBUtil.getHBSession();
			Connection conn = sess.connection();;
			String zipfile = zippath + "/MYSQL数据库文件_" + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".zip";
			
			int fetchsize = 100;
			/*if(DBUtil.getDBType().equals(DBType.MYSQL)){
				Context	env = (Context) new InitialContext();
		        BasicDataSource datasourceRef = (BasicDataSource) env.lookup("java:comp/env/jdbc/insiis");
		        String url= datasourceRef.getUrl();
		        Class.forName("com.mysql.jdbc.Driver");
		        conn=DriverManager.getConnection(url,datasourceRef.getUsername(),datasourceRef.getPassword()); 
		        fetchsize = Integer.MIN_VALUE;
			} else {
				conn = sess.connection();
			}*/
			int number = 0;
			w.stop();
			//appendFileContent(logfilename, "线程"+no+"_1:"+"\n"+w.elapsedTime()+"\n"+ DateUtil.getTime()+"\n");	
			while (true) {
				w.start();
				number = control.getNewNumber();
				if(number>12){
					break;
				}
				String table = tables[number];
				CommonQueryBS.systemOut("thd---"+no +"----"+number+"---"+table);
				if(table.equals("PHOTO")){
					KingbsconfigBS.saveImpDetail(process_run,"1","人员照片头像生成处理中，剩余"+(control.getNumber2()),uuid);	
					//页面详情信息照片文件生成中-----------------------------------
					//------------------------------------------------
					String photopath = zippath + "/Photos/";				//生成图片路径      
					File file2 =new File(photopath);    
					if  (!file2 .exists()  && !file2.isDirectory()){
						file2.mkdirs();    
					}
					
					//直接批量复制照片
					CommandUtil util = new CommandUtil();
					List<String> list = initPath();								//初始化路径 包括 0/1/,0/2/,A/B/...Z/Z
					String osname = System.getProperties().getProperty("os.name");
					String LINUX_COPY_ALL="cp -f ";
					String WINDOWS_COPY_ALL="cmd /c xcopy /r /y ";
					String PHOTO_PATH = AppConfig.PHOTO_PATH;
					//String photo_path_temp = (AppConfig.HZB_PATH + "/temp/upload\\unzip\\"+imprecordid +"\\Photos\\").replace("/", "\\");
					String photo_path_temp = PHOTO_PATH.replace("/", "\\");
					try {
						if(osname.equals("Linux")){ //需确认
							for (String url : list) {
								String source = (photo_path_temp + "/"  + (url.replace("/", "")) +"*.* ").replace("/", "\\");
								String dir = photopath ;
								util.executeCommand(LINUX_COPY_ALL + source + dir);
							}
							
						} if(osname.contains("Windows")){
							for (String url : list) {
								String source = "\""+photo_path_temp + url +"*.*\" ";
								String dir = ("\""+photopath + "" +"\"").replace("/", "\\");
								util.executeCommand(WINDOWS_COPY_ALL + source + dir);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					//pywu 20170608  A57表所有字段
					//查询人员图片数据
		            /*String createsql = SqlUtil.getSqlByTableName("A57", "t") + " where EXISTS (SELECT 1 from A01 v where v.A0000=t.A0000)" ;
		            PreparedStatement stmt = null;
		            try {
						stmt = conn.prepareStatement(createsql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
						stmt.setFetchSize(fetchsize);
						stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
						ResultSet rs = stmt.executeQuery();
						while (rs.next()) {
							String a0000 = rs.getString("a0000");
							String photoname = rs.getString("photoname");
							String photop = rs.getString("photopath");
							String photon = (photoname!=null&&!photoname.equals(""))? photoname:a0000 +"." + "jpg";
							PhotosUtil.copyFile(photop+photon, photopath);
						}
						w.stop();
						//appendFileContent(logfilename, "thd---"+no +"----"+number+"---"+table+":"+" "+w.elapsedTime()+"    "+ DateUtil.getTime()+"\n");	
					} catch (Exception e) {
						e.printStackTrace();
						throw new RadowException(table+"表导入出错："+e);
					} finally{
						if(stmt!=null){
							stmt.close();
						}
						if(conn!=null){
							conn.close();
						}
					}*/
				
				}else if(table.equals("gwyinfo")){
					 Connection connection = null;
					 Statement smt = null;
					 String url = "jdbc:mysql://localhost:7953/gwybase?user=gwydb&password=gwydbpwd&useUnicode=true&characterEncoding=UTF8";
					 
					 KingbsconfigBS.saveImpDetail(process_run,"1",table+"表数据生成处理，剩余"+(control.getNumber2())+"",uuid);		//记录导入过程
					 try {
						 Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
						 connection = DriverManager.getConnection(url);
						 smt = connection.createStatement();
						 
						 ResultSet rs = conn.createStatement().executeQuery("select B0111,B0101,B0114,B0194 from B01 where B0111 = '001.001'");
						 String B0111 = "";
						 String B0101 = "";
						 String B0114 = "";
						 String B0194 = "";
						 while(rs.next()){
							 B0111 = rs.getString(1);
							 B0101 = rs.getString(2);
							 B0114 = rs.getString(3);
							 B0194 = rs.getString(4);
						 }
						 rs = conn.createStatement().executeQuery("select count(1) from A01");
						 Integer PSNCOUNT = 0;
						 while(rs.next()){
							 PSNCOUNT = rs.getInt(1);
						 }
						 String gwyinfo = "insert into gwyinfo values('23',now(),'2017.01',"+PSNCOUNT+",'"+B0101+"','"+B0111+"','"+B0114+"','"+B0194+"','"+contact+"','"+tel+"','"+notes+"')";
						 smt.executeUpdate(gwyinfo);
					 }catch (Exception e){
						 e.printStackTrace();
						 throw new RadowException(table+"表导入出错："+e);
					 }finally{
						 if(smt!=null){
							 smt.close();
						 }
						 if(connection!=null){
							 connection.close();
						 }
						 conn.close();
					 }
			         
				}else {
					//appendFileContent(logfilename, "thd---"+no +"----"+number+"---"+table+":"+" "+w.elapsedTime()+"    "+ DateUtil.getTime()+"\n");	
					KingbsconfigBS.saveImpDetail(process_run,"1",table+"表数据生成处理，剩余"+(control.getNumber2())+"",uuid);		//记录导入过程
					
					Connection connection = null;
					PreparedStatement ps = null;
			        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
			        // 避免中文乱码要指定useUnicode和characterEncoding
			        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
			        // 下面语句之前就要先创建javademo数据库
					String url = "jdbc:mysql://localhost:7953/gwybase?user=gwydb&password=gwydbpwd&useUnicode=true&characterEncoding=UTF8";
			 
			        try {
			            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
			            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
			            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			            // 一个Connection代表一个数据库连接
			            connection = DriverManager.getConnection(url);
			            Statement smt = connection.createStatement();
			            String colomn_sql = "";           //b01字段连接 如(a0000,A0200......)
			    		String value_sql = "";            //b01字段插入值连接(?,?,?)
						String sqlMysql = "select column_name from information_schema.columns a where table_name = upper('"+table+"')  and a.TABLE_SCHEMA = 'gwybase'";

						ResultSet rs = smt.executeQuery(sqlMysql);
						List<Object> colomns = new ArrayList<Object>();
						while(rs.next()){
							colomns.add(rs.getObject(1));
							String column = ""+rs.getObject(1);
							colomn_sql = colomn_sql + column+",";
							value_sql = value_sql + "?,";
						}
						colomn_sql = colomn_sql.substring(0, colomn_sql.length()-1);
						value_sql = value_sql.substring(0, value_sql.length()-1);
						
						connection.setAutoCommit(false);
			            ps = connection.prepareStatement("insert into "+tables[number]+"("+colomn_sql+") values("+value_sql+")");
			            
			            //读取数据库数据存入MYSQL中
			            Statement smtLocal = conn.createStatement();
			            if("B01".equals(table)){
			            	rs = smtLocal.executeQuery("select "+map.get(table)+" from " + table + " where B0111 != '-1'");
			            } else if("A06".equals(table)){
			            	if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
			            		rs = smtLocal.executeQuery("SELECT x.A0600,x.A0000,x.A0601,x.A0602,x.A0604,x.A0607,x.A0611,x.A0699 FROM(SELECT T.A0600,"
			            				+ "T.A0000,T.A0601,T.A0602,T.A0604,T.A0607,T.A0611,T.A0699,ROW_NUMBER () OVER (PARTITION BY T.A0600 ORDER BY "
			            				+ "T.A0600 ASC ) rn FROM A06 T ) x WHERE x.rn = 1");
			            	}else if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
			            		rs = smtLocal.executeQuery("SELECT T.A0600,T.A0000,T.A0601,T.A0602,T.A0604,T.A0607,T.A0611,T.A0699 FROM ( SELECT "
			            				+ "( @rowNO := CASE WHEN @pre_parent_code = V.A0600 THEN @rowNO + 1 ELSE 1 END ) RN,V.A0600,V.A0000,V.A0601,"
			            				+ "V.A0602,V.A0604,V.A0607,V.A0611,V.A0699,(@pre_parent_code := V.A0600) GRN FROM a06 V, ( SELECT @rowNO := 0 "
			            				+ ",@pre_parent_code := '' ) XXX ORDER BY V.A0600 ) T WHERE T.RN = 1");
			            	}
			            } else if("A99Z1".equals(table)){
			            	if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
			            		rs = smtLocal.executeQuery("SELECT x.A0000,x.A99Z101,x.A99Z102,x.A99Z103,x.A99Z104 FROM(SELECT T.A0000,T.A99Z100,"
			            				+ "T.A99Z101,T.A99Z102,T.A99Z103,T.A99Z104,ROW_NUMBER () OVER (PARTITION BY T .a0000 ORDER BY T .a0000 ASC "
			            				+ ") rn FROM A99Z1 T ) x WHERE x.rn = 1");
			            	}else if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
			            		rs = smtLocal.executeQuery("SELECT T.A0000,T.A99Z101,T.A99Z102,T.A99Z103,T.A99Z104 FROM (SELECT(@rowNO := CASE WHEN @pre_parent_code = V.A0000 "
			            				+ "THEN @rowNO + 1 ELSE 1 END) RN,V.A0000,V.A99Z100,V.A99Z101,V.A99Z102,V.A99Z103,V.A99Z104, "
			            				+ "(@pre_parent_code := V.A0000) GRN FROM a99z1 V,(SELECT @rowNO := 0 ,@pre_parent_code := '') "
			            				+ "XXX ORDER BY V.A0000) T WHERE T.RN = 1");
			            	}
			            }else{
			            	rs = smtLocal.executeQuery("select "+map.get(table)+" from "+table);
			            }
			            
			            if(rs!=null){
			            	ResultSetMetaData md = rs.getMetaData();                 //得到结果集(rs)的结构信息，比如字段数、字段名等   
			    		    int columnCount = md.getColumnCount();                   //返回此 ResultSet 对象中的列数   
			    		    CommonQueryBS.systemOut("   rs 开始"+table+"开始前占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			    			//----------------------------------------------------------------------------------
			    		    int count = 0;
			    		    while (rs.next()) {
			    		    	Map rowData = new HashMap(columnCount); 
			    		    	for (int j = 1; j <= columnCount; j++) {   
			    		    		if(md.getColumnName(j)!=null&&(((table.equals("A01")||table.equals("A99Z1")||table.equals("A57"))&&("A0000".equals(md.getColumnName(j))))
			    		    				||"A0200".equals(md.getColumnName(j))||"A0500".equals(md.getColumnName(j))
			    		    				||"A0600".equals(md.getColumnName(j))||"A0800".equals(md.getColumnName(j))||"A1400".equals(md.getColumnName(j))
			    		    				||"A1500".equals(md.getColumnName(j))||"A3600".equals(md.getColumnName(j))||"B0111".equals(md.getColumnName(j)))){
			    		    			rowData.put("ID", rs.getObject(j)); 
			    		    		}else if(md.getColumnName(j)!=null&&(((table.equals("A02")||table.equals("A05")||table.equals("A06")||table.equals("A08")||table.equals("A14")
			    		    				||table.equals("A15")||table.equals("A36"))&&"A0000".equals(md.getColumnName(j)))||"B0121".equals(md.getColumnName(j)))){
			    		    			rowData.put("SID", rs.getObject(j)); 
			    		    		}else if(md.getColumnName(j)!=null&&table.equals("A36")&&"SORTID".equals(md.getColumnName(j))){
			    		    			String A3699 = rs.getObject(j)!=null?rs.getObject(j).toString():"";
			    		    			String reg = "^\\d+$"; 
										if(A3699.matches(reg)){
											rowData.put("A3699", subStringByByte(""+rs.getObject(j),6)); 
										}else{
											rowData.put("A3699", "");
										}
			    		    			
			    		    		}else if(md.getColumnName(j)!=null&&table.equals("B01")&&"SORTID".equals(md.getColumnName(j))){
			    		    			String SORTID = rs.getObject(j)!=null?rs.getObject(j).toString():"";
			    		    			String reg = "^\\d+$"; 
										if(SORTID.matches(reg)){
											rowData.put("SORTID", subStringByByte(""+rs.getObject(j),9)); 
										}else{
											rowData.put("SORTID", ""); 
										}
			    		    		}else if((md.getColumnName(j)!=null&&table.equals("A08"))&&(("A0899".equals(md.getColumnName(j)))||"A0811".equals(md.getColumnName(j)))){
			    		    			if("A0899".equals(md.getColumnName(j))){
			    		    				String A0899 = rs.getObject(j)!=null?rs.getObject(j).toString():"";
				    		    			rowData.put("A0898", A0899.equals("true")||A0899.equals("1")?"1":"0");
			    		    			}else if("A0811".equals(md.getColumnName(j))){
			    		    				String A0811 = rs.getObject(j)!=null?rs.getObject(j).toString():"";
			    		    				String reg = "^\\d+$";
			    		    				String reg2 = "^\\d+(\\.\\d+)?$";
											if((A0811.matches(reg))||(A0811.matches(reg2))){
												if(Float.parseFloat(A0811)>=0.1&&Float.parseFloat(A0811)<=99.9){
													rowData.put("A0811", rs.getObject(j));
												}else{
													rowData.put("A0811", "");
												}
											}else{
												rowData.put("A0811", "");
											}
			    		    			}
			    		    		}else if(md.getColumnName(j)!=null&&table.equals("A05")&&"A0531".equals(md.getColumnName(j))){
			    		    			String A0531 = rs.getObject(j)!=null?rs.getObject(j).toString():"";
			    		    			if("1".equals(A0531)){
			    		    				rowData.put("A0531", "0");
			    		    			}
			    		    			if("0".equals(A0531)){
			    		    				rowData.put("A0531", "1");
			    		    			}
			    		    		}else if(md.getColumnName(j)!=null&&table.equals("A02")&&"A0219".equals(md.getColumnName(j))){
			    		    			String A0219 = rs.getObject(j)!=null?rs.getObject(j).toString():"";
			    		    			rowData.put("A0219", A0219.equals("1")?"1":"0");
			    		    		}else if(md.getColumnName(j)!=null&&table.equals("A02")&&"A0281".equals(md.getColumnName(j))){
			    		    			String A0281 = rs.getObject(j)!=null?rs.getObject(j).toString():"";
			    		    			rowData.put("A0281", A0281.equals("true")||A0281.equals("1")?"1":"0");
			    		    		}else if(md.getColumnName(j)!=null&&table.equals("A06")&&"A0699".equals(md.getColumnName(j))){
			    		    			String A0699 = rs.getObject(j)!=null?rs.getObject(j).toString():"";
			    		    			rowData.put("A0699", A0699.equals("true")||A0699.equals("1")?"1":"0");
			    		    		}else if(md.getColumnName(j)!=null&&table.equals("B01")&&"B0227".equals(md.getColumnName(j))){
			    		    			String B0227 = rs.getObject(j)!=null?rs.getObject(j).toString():"";
			    		    			String reg = "^\\d+$"; 
										if(B0227.matches(reg)){
											rowData.put("B0227", subStringByByte(""+rs.getObject(j),8));
										}else{
											rowData.put("B0227", "");
										}
			    		    		}/*else if((md.getColumnName(j)!=null&&table.equals("A01"))&&("A0111A".equals(md.getColumnName(j))||"A0114A".equals(md.getColumnName(j)))){
			    		    			String value = rs.getObject(j)!=null?rs.getObject(j).toString().trim().replaceAll(" ", ""):"";
			    		    			rowData.put(md.getColumnName(j), mapCodeValues.get(value)!=null?mapCodeValues.get(value):"");
			    		    		}else if(md.getColumnName(j)!=null&&table.equals("A36")&&"A3627".equals(md.getColumnName(j))){
			    		    			String A3627 = rs.getObject(j)!=null?rs.getObject(j).toString().trim().replaceAll(" ", ""):"";
			    		    			rowData.put("A3627", mapCodeValues.get(A3627)!=null?mapCodeValues.get(A3627):"");
			    		    		}*/else if(md.getColumnName(j)!=null&&table.equals("A57")&&"A5714".equals(md.getColumnName(j))){
			    		    			String A5714 = rs.getObject(j)!=null?rs.getObject(j).toString().trim().replaceAll(" ", ""):"";
		    		    				//System.out.println(A5714.indexOf(".jpg"));
			    		    			if(A5714.indexOf(".jpg")!=-1){
			    		    				rowData.put("A5714", A5714.substring(0, A5714.indexOf(".jpg")+4));
			    		    			}else{
			    		    				if(rs.getString("A0000")!=null){
			    		    					rowData.put("A5714", rs.getString("A0000") + ".jpg");
			    		    				}else{
			    		    					rowData.put("A5714", "");
			    		    				}
			    		    			}
			    		    		}
			    		    		else{
			    		    			rowData.put(md.getColumnName(j), rs.getObject(j)); 
			    		    		}
			    		        }
			    		    	for (int j = 0; j < colomns.size(); j++) {
			    		    		ps.setObject(j+1, rowData.get(colomns.get(j).toString().toUpperCase())==null||
						    					rowData.get(colomns.get(j).toString().toUpperCase()).equals("")?null:rowData.get(colomns.get(j).toString().toUpperCase()));
			    				}
			    		    	ps.addBatch();
			    				rowData.clear();
			    				rowData=null;
			    				count ++;
			    				if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
			    					if(count%10000 == 0){
				    					ps.executeBatch();
				    					ps.clearBatch();
				    					//connection.commit();
				    			    	CommonQueryBS.systemOut("   "+DateUtil.getTime()+"---"+table + "---10000一次" + count/10000 +",内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				    			    	System.gc();
				    				}
			    				}else if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
			    					if(count%5000 == 0){
				    					ps.executeBatch();
				    					ps.clearBatch();
				    					//connection.commit();
				    			    	CommonQueryBS.systemOut("   "+DateUtil.getTime()+"---"+table + "---5000一次" + count/5000 +",内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				    			    	System.gc();
				    				}
			    				}
			    		    }
			    			
			    		    md = null;
			    			System.gc();      //释放内存
			    			CommonQueryBS.systemOut("   bzb-->"+DateUtil.getTime()+"--->关闭内存"+table+"结束占用内存："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			            }
			            
			            ps.executeBatch();
			            ps.clearBatch();
			            connection.commit();
			            
			        } catch (SQLException e) {
			            e.printStackTrace();
			            throw new RadowException(table+"表导入出错："+e);
			        } catch (Exception e) {
			            e.printStackTrace();
			            throw new RadowException(table+"表导入出错："+e);
			        } finally {
			        	ps.close();
			        	connection.close();
			        	conn.close();
			        }
					
					w.stop();
					//appendFileContent(logfilename, "thd---"+no +"----"+number+"---"+table+":"+" "+w.elapsedTime()+"    "+ DateUtil.getTime()+"\n");	
				}
				
			}	
			
			if(control.getStatus()==2){
				KingbsconfigBS.saveImpDetail(process_run,"1","正在关闭MYSQL数据库",uuid);			//记录导入过程
				
				/*File my_install = new File(path+"/mysql/my_uninstall.bat");
				if(!my_install.exists()){
					throw new Exception("停止并服务失败，请检查配置！");
				}
				new CommandUtil().executeCommand("cmd /c start "+(path+"/mysql/my_uninstall.bat ").replace("/", "\\"));*/
				System.out.println("cmd /c start /b "+(path+"/mysql/bin/mysqladmin -u root --password=123456 shutdown ").replace("/", "\\"));
				new CommandUtil().executeCommand("cmd /c start /b "+(path+"/mysql/bin/mysqladmin -u root --password=123456 shutdown ").replace("/", "\\"));
				//start c:\hzb\mysql\bin\mysqladmin -u root --password=admin shutdown
				
				closeService(0);
				
				KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid);			//记录导入过程
					
				process_run = "3";
				KingbsconfigBS.saveImpDetail(process_run,"1","压缩中",uuid);			//记录导入过程
					
				Zip7z.zip7Z(zippath, zipfile, null);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","正在删除缓存",uuid);			//记录导入过程
				this.delFolder(zippath+"/Datas");
				this.delFolder(zippath+"/Photos");
				CommonQueryBS.systemOut("删除缓存成功");
				KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid,infile.replace("\\", "/"));
				
				new LogUtil("412", "IMP_RECORD", "", "", "数据导出", new ArrayList(),userVo).start();
			}
			
		} catch (Exception e) {
			if("16".equals(e.getMessage())){
				try {
					KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid);			//记录导入过程
					
					process_run = "3";
					KingbsconfigBS.saveImpDetail(process_run ,"4","自动关闭服务失败，请手动关闭并压缩",uuid);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else{
				e.printStackTrace();
				try {
					KingbsconfigBS.saveImpDetail(process_run ,"4","失败:"+e.getMessage(),uuid);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if(no.equals("1")){
					control.errStatus("1");
				}else {
					control.errStatus("2");
				}
				
				if(control.getStatus()==0){
					//new CommandUtil().executeCommand("cmd /c start "+(path+"/mysql/my_uninstall.bat ").replace("/", "\\"));
					System.out.println("cmd /c start /b "+(path+"/mysql/bin/mysqladmin -u root --password=123456 shutdown ").replace("/", "\\"));
					new CommandUtil().executeCommand("cmd /c start /b "+(path+"/mysql/bin/mysqladmin -u root --password=123456 shutdown ").replace("/", "\\"));
					try {
						closeService(0);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					this.delFolder(zippath+"/Datas");
					this.delFolder(zippath+"/Photos");
					CommonQueryBS.systemOut("删除缓存成功");
				}
			}
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
	private static String getNo(){
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
	
	private String subStringByByte(String str, int len) {
		 String result = "";
	     if (str != "") {
	    	 byte[] a = str.getBytes();
	    	 if (a.length <= len) {
	    		 result = str;
	    	 } else if (len > 0) {
	    		 result = new String(a, 0, len);
	    		 int length = result.length();
	    		 if (str.charAt(length - 1) != result.charAt(length - 1)) {
	    			 if (length < 2) {
	    				 result = "";
	    			 } else {
	    				 result = result.substring(0, length - 1);
	    			 }
	    		 }
	    	 }
	     }
	     return result;
	}
	
	public void closeService(int num) throws Exception{
		Thread.currentThread();
		Thread.sleep(30000);//毫秒
		//建立连接
		Connection conn=createConnection(num);
	};
	
	public Connection createConnection(int num) throws Exception{
		Connection conn=null;
		String url = "jdbc:mysql://localhost:7953/gwybase?user=gwydb&password=gwydbpwd&useUnicode=true&characterEncoding=UTF8";
		// 加载驱动
		try{
			num++;
			Class.forName("com.mysql.jdbc.Driver");
			// 获取数据库连接
			try {
				conn = DriverManager.getConnection(url);
			} catch (Exception e) {
			}
			if(conn==null){
			}else if(conn!=null){
				throw new Exception("数据库未断开!");
			}
		}catch(Exception e){
			num++;
			Thread.currentThread();
			Thread.sleep(4000);//毫秒
			if(num>15){
				throw new Exception(""+num);
			}
			conn=createConnection(num);
		}
		return conn;
	}
	
	private static List<String> initPath() { 
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
}
