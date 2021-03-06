package com.insigma.siis.local.pagemodel.dataverify;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fr.stable.core.UUID;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class DataExcelExportThread {
	private CurrentUser user;
	private String gzlbry;//????????????????
	private String gllbry;//????????????????
	private String searchDeptid;//????id
	private String linkpsn;//??????
	private String linktel;//????????
	private String gz_lb;//??????????????????
	private String gl_lb;//??????????????????
	private String zdcjg;//??????????????
	private UserVO userVo;
	private String fxzry;//??????????
	private String uuid;//????id
	private String mbpath;
	
	private String exptemplate;
	private String exptunit;
	private String expsy;
	private String exppicture;
	
	private Map<String, Map<String,String>> mapmap=null;
	public static String PHOTO_PATH = GlobalNames.sysConfig.get("PHOTO_PATH");// ??????????

	
	public DataExcelExportThread(String mbpath,String uuid,CurrentUser user,String fxzry,String gzlbry,String gllbry,String searchDeptid,
			String linkpsn,String linktel,String gz_lb,String gl_lb,String zdcjg,UserVO userVo,String exptemplate,String exptunit,String expsy,String exppicture) {
		this.exptemplate=exptemplate;
		this.exptunit=exptunit;
		this.expsy=expsy;
		this.exppicture=exppicture;
		
		this.mbpath=mbpath;
		this.uuid=uuid;
		this.user=user;
		this.fxzry=fxzry;
		this.gzlbry=gzlbry;
		this.gllbry=gllbry;
		this.searchDeptid=searchDeptid;
		this.linkpsn=linkpsn;
		this.linktel=linktel;
		this.gz_lb=gz_lb;
		this.gl_lb=gl_lb;
		this.zdcjg=zdcjg;
		this.userVo=userVo;
	}

	public static void appendFileContent(String fileName, String content) {
		try {
			// ????????????????????????????????????????true????????????????????
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getPath() {
		String upload_file = AppConfig.HZB_PATH + "/temp/excelload/";
		try {
			File file =new File(upload_file);    
			//??????????????????????    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		String excel = upload_file + uuid + "/";
		return excel;
	}
	
	public void copyFile(String sourcefile, String targetDir,String nameIc) throws IOException {
		try{
			File sourceFile =  new File(DataExcelExportThread.PHOTO_PATH + sourcefile);
			if(sourceFile.exists()){
				File targetFile = new File(new File(targetDir)// ????????
						.getAbsolutePath() + File.separator + nameIc+(sourcefile.substring(sourcefile.lastIndexOf("."))));
				copyFile(sourceFile, targetFile);
			}	
		}catch(IOException e){
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
	}
	
	public static void copyFile(File sourcefile, File targetFile)
			throws IOException {
		try{
			// ????????????????????????????
			FileInputStream input = new FileInputStream(sourcefile);
			BufferedInputStream inbuff = new BufferedInputStream(input);
			// ????????????????????????????
			FileOutputStream out = new FileOutputStream(targetFile);
			BufferedOutputStream outbuff = new BufferedOutputStream(out);
			// ????????
			byte[] b = new byte[1024 * 5];
			int len = 0;
			while ((len = inbuff.read(b)) != -1) {
				outbuff.write(b, 0, len);
			}
			// ??????????????????
			outbuff.flush();
			// ??????
			inbuff.close();
			outbuff.close();
			out.close();
			input.close();
		}catch(IOException e){
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
			
	}
	
	public String start() throws RadowException{
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileInputStream is = null;
		String tableExt = (getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS")).toUpperCase();
		//String process_run = "1"; // ????????
		try{
			String logfilename = AppConfig.HZB_PATH + "/temp/upload/exp" +DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".txt";
			File logfile = new File(logfilename);
			if (!logfile.exists()) {
				logfile.createNewFile();
			}
			appendFileContent(logfilename, "ks:excel" + "," + DateUtil.getTime()+ "\n");
			
			HBSession sess = HBUtil.getHBSession();
			B01 b01 = (B01) sess.get(B01.class, searchDeptid);
			//String name = "??????????????_"+ b01.getB0111()+ "_"+ b01.getB0101()+ DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss")+".hzb";
			//String time = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
			//String sql1 = "insert into EXPINFO (ID,NAME,STARTTIME,CREATEUSER,STATUS,B0101) values ('"+uuid+"','"+name+"','"+time+"','"+user.getId()+"','????????????????...','"+b01.getB0101()+"')";
			//--------------------------------------------------------------------
			//sess.createSQLQuery(sql1).executeUpdate();
			
			String path = getPath();
			File file1=new File(path);
			if  (!file1 .exists()  && !file1 .isDirectory())      
			{       
				file1 .mkdirs();    
			}
			//String pathdata=path+"Data.xlsx";
			String pathdata=path+"Data.xls";
			String ss=File.separatorChar+"";
			pathdata=pathdata.replace("\\", ss);
			pathdata=pathdata.replace("/", ss);
			File file2=new File(pathdata);
			if(!file2.exists()||!file2.isFile()){
				file2.createNewFile();
			}
			mbpath=mbpath.replace("\\", ss);
			mbpath=mbpath.replace("/", ss);
			File file =  new File(mbpath);
			fis = new FileInputStream(file);
			String file_copy=pathdata;
			fos = new FileOutputStream(file_copy);
			byte[] buf = new byte[1024*10];
			int len = -1;
			while((len = fis.read(buf)) != -1){
				fos.write(buf, 0, len);
			}
			fis.close();
			fos.close();
			
			is=new FileInputStream(file_copy);
			@SuppressWarnings("resource")
			Workbook workbook = new HSSFWorkbook(is);      //????????2003????Excel
			//XSSFWorkbook xssworkbook = new XSSFWorkbook(is);
			int sheetNums=workbook.getNumberOfSheets();
			
			Font font2 =workbook.createFont();  
			font2.setFontName("????_GB2312");  
			font2.setFontHeightInPoints((short) 11);  
			
			CellStyle cellstyle=workbook.createCellStyle(); 
			cellstyle.setAlignment(HorizontalAlignment.CENTER);        
			cellstyle.setVerticalAlignment(VerticalAlignment.CENTER);  
			cellstyle.setBorderLeft(BorderStyle.THIN);                 
			cellstyle.setBorderRight(BorderStyle.THIN);                
			cellstyle.setBorderLeft(BorderStyle.THIN);                 
			cellstyle.setBorderBottom(BorderStyle.THIN);
			cellstyle.setFont(font2);
			cellstyle.setWrapText(true);
			//????excel
//			this.exptemplate=exptemplate;
//			this.exptunit=exptunit;
//			this.expsy=expsy;
//			this.exppicture=exppicture;
			if("true".equals(exptemplate)){
				
			}else{
				//????????map
				Map<String,String> b0101Map = new HashMap<String, String>();
				String b0101Sql = "select b0111,b0101 from b01 where b0111 like '"+searchDeptid+"%' or b0111 = '-1'";
				List<Object[]> b0101s = sess.createSQLQuery(b0101Sql).list();
				for(Object[] obj : b0101s){
					if(obj!=null && obj[0]!=null && obj[1]!=null){
						b0101Map.put(obj[0].toString(),obj[1].toString());
					}
				}
				System.out.println("---------------------------------------b0101Map");
				//????????map????B0111map
				String newB0101 = "";
				Map<String,String> b0111Map = new HashMap<String, String>();
				String B0111Sql=" select "
						+ " b.b0111 "//????????
						+ " from B01 b,competence_userdept s "
						+ " where b.b0111=s.b0111 "
						+ " and s.userid='"+userVo.getId()+"' "
						+ " and s.b0111 like '"+searchDeptid+"%' "
						+ " order by length(b.b0111) asc ";
				List<Object> b0111s = sess.createSQLQuery(B0111Sql).list();
				for(int i = 0;i<b0111s.size();i++){
					if(b0111s.get(i)!=null){
						String b0111 = b0111s.get(i).toString();
						newB0101 = b0101Map.get(b0111);
						returnB0111(b0111,b0111,searchDeptid,newB0101,b0101Map,i,b0111Map);
					}
				}
				b0111Map.put("-1", "????????");
				System.out.println("---------------------------------------b0111Map");
				//????code_value,????????Sheet
				Map<String, String> codeValues = new HashMap<String, String>();
				String codeValueSql = "SELECT c.CODE_TYPE,c.CODE_VALUE,c.CODE_NAME3 FROM CODE_VALUE c WHERE c.CODE_TYPE IN('ZB01','ZB87',"
						+ "'ZB03','ZB04','GB2261','GB3304','GB4762','ZB125','ZB126','ZB130','ZB09','ZB148','ZB134','ZB135','ZB139',"
						+ "'ZB129','ZB122','ZB14','GB8561','ZB24','ZB64','GB6864','GB16835','ZB123','ZB03','ZB128','ZB18','XZ91')";
				List<Object[]> values = sess.createSQLQuery(codeValueSql).list();
				for(Object[] obj : values){
					if(obj!=null && obj[0]!=null && obj[1]!=null && obj[2]!=null){
						codeValues.put(obj[0].toString()+obj[1].toString(), obj[2].toString());
					}
				}
				codeValues = getNewCodeValues(codeValues);
				System.out.println("---------------------------------------codeValues");
				String A0000Temp = "";
				String a0000SQL = "insert into TEMP"+tableExt+" "
						+ " select a01.a0000,a01.a0101,a01.a0184 "
						+ " from a01 a01 "
						+ " where a01.status!='4' ";
				if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
					a0000SQL = a0000SQL + " and exists (select 1 from a02, competence_userdept s where"
							+ " a02.a0201b=s.b0111 "
							+ " and s.b0111 like '"+searchDeptid+"%' "
							+ " and s.userid='"+userVo.getId()+"' "
							+ " and a01.a0000=a02.a0000 and a0281 = 'true'"
							+ " )";
					A0000Temp = "create table TEMP"+tableExt+"(A0000 varchar2(120),A0101 varchar2(36),A0184 varchar2(18))";
					sess.createSQLQuery(A0000Temp.toUpperCase()).executeUpdate();
					sess.createSQLQuery(a0000SQL).executeUpdate();
					sess.createSQLQuery("Create index A00TEMP" + tableExt + " on TEMP" + tableExt + "(A0000)").executeUpdate();

				}else if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
					a0000SQL = a0000SQL + " and a01.a0000 in (select a02.a0000 from a02, competence_userdept s where"
							+ " a02.a0201b=s.b0111 "
							+ " and s.b0111 like '"+searchDeptid+"%' "
							+ " and s.userid='"+userVo.getId()+"' "
							+ " and a01.a0000=a02.a0000 and a0281 = 'true'"
							+ " )";
					A0000Temp = "create table TEMP"+tableExt+"(A0000 varchar(120),A0101 varchar(36),A0184 varchar(18)) ENGINE=MyISAM DEFAULT CHARSET=utf8";
					sess.createSQLQuery(A0000Temp.toUpperCase()).executeUpdate();
					sess.createSQLQuery(a0000SQL).executeUpdate();
					sess.createSQLQuery("ALTER TABLE TEMP" + tableExt + " add index A00TEMP" + tableExt + "(A0000)").executeUpdate();
				}
				Sheet sheet = null;
				for(int i=0;i<sheetNums;i++){
					sheet=workbook.getSheetAt(i);
					if(i==0){//????????
						exportData0(sheet,linkpsn,linktel);
					}else if(i==1){
						if("true".equals(exptunit)){//????????
							exportData1(sheet,workbook,cellstyle,b0111Map,codeValues);
							System.out.println("1");
						}
					}
					if("true".equals(expsy)){//????????
						if(i==2){
							exportData2(sheet,workbook, cellstyle,b0111Map,tableExt,codeValues);
							System.out.println("2");
						}else if(i==3){
							exportData3(sheet,workbook, cellstyle,b0111Map,tableExt,codeValues);
							System.out.println("3");
						}else if(i==4){
							exportData4(sheet,workbook, cellstyle,tableExt,codeValues);
							System.out.println("4");
						}else if(i==5){
							exportData5(sheet,workbook, cellstyle,tableExt,codeValues);
							System.out.println("5");
						}else if(i==6){
							exportData6(sheet,workbook, cellstyle,tableExt,codeValues);
							System.out.println("6");
						}else if(i==7){
							exportData7(sheet,workbook, cellstyle,tableExt,codeValues);
							System.out.println("7");
						}else if(i==8){
							exportData8(sheet,workbook, cellstyle,tableExt,codeValues);
							System.out.println("8");
						}else if(i==9){
							exportData9(sheet,workbook, cellstyle,tableExt,codeValues);
							System.out.println("9");
						}else if(i==10){
							exportData10(sheet,workbook, cellstyle,tableExt,codeValues);
							System.out.println("10");
						}	
					}
					
				}
			}
			
			fos = new FileOutputStream(file_copy);
			workbook.write(fos);
			
			fos.flush();
			fos.close();
			workbook.close();
			System.gc();
			if("true".equals(exppicture)){
				//????????
				//????????????
				String picpath=path+"Photos";
				File filepic=new File(picpath);
				if(!filepic.exists()&&!filepic .isDirectory()){
					filepic.mkdirs();
				}
				//????????????????
				String sqlry=" select t.A0000, "
						        + " t.A5714, "
						        + " t.UPDATED, "
						        + " t.PHOTODATA, "
						        + " t.PHOTONAME, "
						        + " t.PHOTSTYPE, "
						        + " t.PHOTOPATH, "
						        + " t.PICSTATUS, "
						        + " b.a0101,"
						        + " b.a0184"
						        + " FROM A57 t,TEMP"+tableExt+" b "
						        + " where b.a0000 = t.a0000 ";
						   /*+ " FROM A57 t,a01 a "
						   + " where a.a0000=t.a0000 "
						   + " and a.status!='4' "
						   + " and exists (select 1 from a02, competence_userdept s where"
						   						+ " a02.a0201b=s.b0111 "
						   						+ " and s.b0111 like '"+searchDeptid+"%' "
						   						+ " and s.userid='"+userVo.getId()+"' "
						   						+ " and a02.a0000=a.a0000 )";*/
				PreparedStatement stmt=null;
				HBSession session = HBUtil.getHBSession();
				Connection conn=session.connection();
				stmt = conn.prepareStatement(sqlry, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				int fetchsize = 100;
				stmt.setFetchSize(fetchsize);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					String a0000 = rs.getString("a0000");
					String photoname = rs.getString("photoname");
					String photop = rs.getString("photopath");
					String nameIc=rs.getString("a0101")+rs.getString("a0184");
					String photon = (photoname!=null&&!photoname.equals(""))? photoname:a0000 +"." + "jpg";
					copyFile(photop+photon, picpath, nameIc);
				}	
			}
			
			//????????
			String zippath="";
			if("true".equals(exptemplate)){
				zippath=path+"Data.xls";
			}else{
				zippath=path+b01.getB0101()+".zip";
				SevenZipUtil.zip7z(path, zippath, null);
			}
			return zippath;
		}catch(Exception e){
			/*try{
				//String sql4 = "update expinfo set STATUS = '????????????!' where ID = '"+uuid+"'";
				///HBUtil.getHBSession().createSQLQuery(sql4).executeUpdate();
				//KingbsconfigBS.saveImpDetail(process_run, "4", "????:" + e.getMessage(), uuid);
			}catch(Exception e1){
				e1.printStackTrace();
			}*/
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}finally{
			try{
				if(fis!=null){
					fis.close();
				}
				if(fos!=null){
					fos.close();
				}
				if(is!=null){
					is.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try {
				HBUtil.getHBSession().createSQLQuery("drop table TEMP"+tableExt).executeUpdate();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	
	private Map<String, String> getNewCodeValues(Map<String, String> codeValues) {
		codeValues.put("ZB091A98","????????????????????????");
		codeValues.put("ZB091A99","??????????????????");
		codeValues.put("ZB091B98","????????????????????????");
		codeValues.put("ZB091B99","??????????????????");
		codeValues.put("ZB091C98","????????????????????????");
		codeValues.put("ZB091C99","??????????????????");
		codeValues.put("ZB0927","??????????????????????????????????");
		codeValues.put("ZB0928","????????????????????????????");
		codeValues.put("ZB09911","??????????????????????????????");
		codeValues.put("ZB09912","????????????????????????");
		codeValues.put("ZB09C98","??????????????????????????????????");
		codeValues.put("ZB09C99","????????????????????????????");
		codeValues.put("ZB09E09","????????????????????????");
		codeValues.put("ZB09G09","????????????????????????????");
		
		codeValues.put("ZB0920","????????????????????????????????");
		codeValues.put("ZB0921","????????????????????????????????");
		codeValues.put("ZB0922","????????????????????????????????");
		codeValues.put("ZB0923","????????????????????????????????");
		codeValues.put("ZB0924","????????????????????????????????");
		codeValues.put("ZB0925","????????????????????????????????");
		codeValues.put("ZB0926","????????????????????????????????");
		
		codeValues.put("ZB09601","????????????????????????????????????");
		codeValues.put("ZB09602","????????????????????????????????????");
		codeValues.put("ZB09603","????????????????????????????????????");
		codeValues.put("ZB09604","????????????????????????????????????");
		codeValues.put("ZB09605","????????????????????????????????????");
		codeValues.put("ZB09606","????????????????????????????????????");
		codeValues.put("ZB09607","????????????????????????????????");
		codeValues.put("ZB09608","????????????????????????????????");
		codeValues.put("ZB09609","????????????????????????????????");
		codeValues.put("ZB09610","????????????????????????????????");
		codeValues.put("ZB09611","????????????????????????????????");
		codeValues.put("ZB09612","????????????????????????????????");
		
		codeValues.put("ZB097118","??????????????????????????");
		codeValues.put("ZB097119","????????????????????");

		codeValues.put("ZB097201","??????????????????????????");
		codeValues.put("ZB097202","??????????????????????????");
		codeValues.put("ZB097203","??????????????????????");
		codeValues.put("ZB097204","??????????????????????");
		codeValues.put("ZB097205","??????????????????????");
		codeValues.put("ZB097206","??????????????????????");
		codeValues.put("ZB097207","??????????????????????");
		codeValues.put("ZB097208","??????????????????????");
		codeValues.put("ZB097209","??????????????????????");
		codeValues.put("ZB097210","??????????????????????");
		codeValues.put("ZB097211","??????????????????????");
		codeValues.put("ZB097212","??????????????????????");
		codeValues.put("ZB097218","????????????????????????");
		codeValues.put("ZB097219","??????????????????");

		codeValues.put("ZB097318","??????????????????????????????");
		codeValues.put("ZB097319","????????????????????????");

		codeValues.put("ZB097418","??????????????????????????????");
		codeValues.put("ZB097419","????????????????????????");

		codeValues.put("ZB097518","??????????????????????????????");
		codeValues.put("ZB097519","????????????????????????");
		
		codeValues.put("ZB6501","????");
		codeValues.put("ZB6501111","????????????");
		codeValues.put("ZB6501112","????????");
	    codeValues.put("ZB6501113","????????");
	    codeValues.put("ZB6501114","????????");
	    codeValues.put("ZB6501115","????");
	    codeValues.put("ZB6501119","????");
	    codeValues.put("ZB6502","????");
	    codeValues.put("ZB65021","??????????????");
	    codeValues.put("ZB6502110","??????????????");
	    codeValues.put("ZB6502112","??????????????");
	    codeValues.put("ZB6502113","????????????????");
	    codeValues.put("ZB6502114","??????????????");
	   	codeValues.put("ZB6502117","??????????????");
	    codeValues.put("ZB6502119","??????????????");
	    codeValues.put("ZB65022","????????????????");
	    codeValues.put("ZB6502220","????????????????");
	    codeValues.put("ZB6502222","????????????????");
	    codeValues.put("ZB6502223","??????????????????");
	    codeValues.put("ZB6502224","??????????????????");
	    codeValues.put("ZB6502225","??????????????????");
	    codeValues.put("ZB6502226","????????????????");
		codeValues.put("ZB6502227","????????????????????");
		codeValues.put("ZB6502228","????????????????");
		codeValues.put("ZB65023","??????????????????????????");
		codeValues.put("ZB6502230","????????????");
		codeValues.put("ZB6502231","????????????????");
		codeValues.put("ZB6502232","????????????");
		codeValues.put("ZB6502233","??????????????");
		codeValues.put("ZB6502234","????????????????????");
		codeValues.put("ZB6502237","????????????");
		codeValues.put("ZB6502239","????????????????");
		codeValues.put("ZB65024","??????????????????????????????");
		codeValues.put("ZB6502240","????????????");
		codeValues.put("ZB6502241","????????????????");
		codeValues.put("ZB6502247","????????????????????");
		codeValues.put("ZB6502248","????????????????");
		codeValues.put("ZB6502249","????????????????");
		codeValues.put("ZB65025","??????????????????????");
		codeValues.put("ZB6502250","????????????");
		codeValues.put("ZB6502251","????????????????");
		codeValues.put("ZB6502257","????????????????????");
		codeValues.put("ZB6502258","????????????????");
		codeValues.put("ZB6502259","????????????????");
		codeValues.put("ZB65026","????????????????????");
		codeValues.put("ZB6502610","????????????????????????");
		codeValues.put("ZB6502620","????????????????????????");
		codeValues.put("ZB6502630","????????????????????????????????????????");
		codeValues.put("ZB6502640","????????????????????????");

		return codeValues;
	}

	/**
	 * ????????
	 * @param sheet
	 * @param linkpsn  ??????
	 * @param linktel  ???????? 
	 * @throws Exception
	 */
	public void exportData0(Sheet sheet,String linkpsn,String linktel) throws Exception{
		try{
			// ????????   ????????  ????????????  ?????? ???????? 
			//??????????????????
			CommQuery cq=new CommQuery();
			String sqlname=" select b0101 from b01 b,competence_userdept s "
					+ " where b.b0111=s.b0111 "
					+ " and s.userid='"+userVo.getId()+"' "
					+ " and s.b0111 = '"+searchDeptid+"' ";
			List<HashMap<String, Object>> list =cq.getListBySQL(sqlname);
			String name="";
			if(list!=null&&list.size()>0){
				name=(String)list.get(0).get("b0101");
			}
			//???????? 
			String sqlnum="";
			if(DBType.ORACLE == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.ORACLE)){
				sqlnum=" select count(*) count1 from a01 a01 where exists (select 1 from a02, competence_userdept s where"
						+ " a02.a0201b=s.b0111 "
						+ " and s.b0111 like '"+searchDeptid+"%' "
						+ " and s.userid='"+userVo.getId()+"' "
						+ " and a01.a0000=a02.a0000 and a0281 = 'true' "
						+ "  and a01.status != '4' "
						+ " )" ;
			}else if(DBType.MYSQL == DBUtil.getDBType()||DBUtil.getDBType().equals(DBType.MYSQL)){
				sqlnum=" select count(*) count1 from a01 a01 where a01.a0000 in (select a02.a0000 from a02, competence_userdept s where"
						+ " a02.a0201b=s.b0111 "
						+ " and s.b0111 like '"+searchDeptid+"%' "
						+ " and s.userid='"+userVo.getId()+"' "
						+ " and a01.a0000=a02.a0000 and a0281 = 'true' "
						+ "  and a01.status != '4' "
						+ " )" ;
			}
			
			list =cq.getListBySQL(sqlnum);
			String synum="";
			if(list!=null&&list.size()>0){
				synum=(String)list.get(0).get("count1");
				if(Integer.parseInt(synum)>2000){
					throw new Exception("Excel????????????????2000????");
				}
			}
			//???? ????????????
			String sqlorg="select count(*) count1 from b01, competence_userdept s where"
					+ " b01.b0111=s.b0111 "
					+ " and s.b0111 like '"+searchDeptid+"%' "
					+ " and s.userid='"+userVo.getId()+"' " ;
			list =cq.getListBySQL(sqlorg);
			String orgnum="";
			if(list!=null&&list.size()>0){
				orgnum=(String)list.get(0).get("count1");
			}
			Cell cell=sheet.getRow(3).getCell(5);
			cell.setCellValue(name);
			
			cell=sheet.getRow(4).getCell(5);
			cell.setCellValue(synum);
			
			cell=sheet.getRow(5).getCell(5);
			cell.setCellValue(orgnum);
			
			cell=sheet.getRow(6).getCell(5);
			cell.setCellValue(linkpsn);
			
			cell=sheet.getRow(7).getCell(5);
			cell.setCellValue(linktel);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public void returnOrgName(String tempname,List<HashMap<String,Object>> list,String tempidu,CommQuery cq,int i,String str) throws AppException{
		List<HashMap<String, Object>> listtemp=cq.getListBySQL(" select b0111,b0101,b0121 from b01 where b0111='"+tempidu+"' ");
		
		if(listtemp!=null&&listtemp.size()>0){
			tempname=listtemp.get(0).get("b0101")+tempname;
			tempidu=(String)listtemp.get(0).get("b0121");
			if(tempidu.length()<searchDeptid.length()){//????
				list.get(i).put(str, tempname);
			}else{
				returnOrgName(tempname,list,tempidu,cq,i,str);
			}
		}
	}
	
	private void returnB0101(String b0111,String searchDeptid,String newB0101,Map<String, String> b0101Map,List<HashMap<String, Object>> list,int i,String str){
		if("-1".equals(b0111)){
			list.get(i).put(str, "????????");
		}else if(b0111.length()>searchDeptid.length()){
			b0111 = b0111.substring(0, b0111.length()-4);
			newB0101 = b0101Map.get(b0111) + " " + newB0101;
			returnB0101(b0111,searchDeptid,newB0101,b0101Map,list,i,str);
		}else{
			list.get(i).put(str, newB0101);
		}
	}
	
	private void returnB0111(String b0111,String newB0111,String searchDeptid,String newB0101,Map<String, String> b0101Map,int i,Map<String,String> b0111Map){
		if(newB0111.length()>searchDeptid.length()||newB0101==null){
			newB0111 = b0111.substring(0, newB0111.length()-4);
			newB0101 = b0101Map.get(newB0111) + " " + newB0101;
			returnB0111(b0111,newB0111,searchDeptid,newB0101,b0101Map,i,b0111Map);
		}else{
			b0111Map.put(b0111, newB0101);
		}
	}
	
	/**
	 * B01????
	 * @param sheet
	 * @throws Exception
	 */
	public void exportData1(Sheet sheet,Workbook workbook,CellStyle cellstyle,Map<String,String> b0111Map,Map<String, String> codeValues) throws Exception{
		try{
			CommQuery cq=new CommQuery();
			String sql=" select "
					+ " b.b0101,"//????????
					+ " b.b0104,"//????????
					+ " b.b0114,"//????????
					+ " b.b0117, "//????????
					+ " b.b0124, "//????????
					+ " b.b0127, "//????????
					+ " b.b0131, "//???????? 
					+ " b.b0194, "//????????????
					+ " b.b0227,"//??????????
					+ " b.b0232,"//??????????????????
					+ " b.b0233, "//????????????????????
					+ " b.b0236, "//??????????
					+ " b.b0234,"//"??????????
					+ " b.b0238,"//????????????????????????
					+ " b.b0239, "//????????????????????????
					+ " b.b0150, "//????????????????
					+ " b.b0183,"//????????????
					+ " b.b0185,"//????????????
					+ " b.b0180, "//????
					+ " b.sortid,"//??????
					+ " b.b0111,"//
					+ " b.b0121 "
					+ " from B01 b,competence_userdept s "
					+ " where b.b0111=s.b0111 "
					+ " and s.userid='"+userVo.getId()+"' "
					+ " and s.b0111 like '"+searchDeptid+"%' "
					+ " order by length(b.b0111) asc ";
			/*String sql=" select "
					+ " b.b0101,"//????????
					+ " b.b0104,"//????????
					+ " b.b0114,"//????????
					+ " (select code_name3 from code_value where code_column_name='B0117' and code_value=b.B0117) b0117, "//????????
					+ " (select code_name3 from code_value where code_column_name='B0124' and code_value=b.B0124) b0124, "//????????
					+ " (select code_name3 from code_value where code_column_name='B0127' and code_value=b.B0127) b0127, "//????????
					+ " (select code_name3 from code_value where code_column_name='B0131' and code_value=b.B0131) b0131, "//???????? 
					+ " b.b0194, "//????????????
					+ " b.b0227,"//??????????
					+ " b.b0232,"//??????????????????
					+ " b.b0233, "//????????????????????
					+ " b.b0236, "//??????????
					+ " b.b0234,"//"??????????
					+ " b.b0238,"//????????????????????????
					+ " b.b0239, "//????????????????????????
					+ " b.b0150, "//????????????????
					+ " b.b0183,"//????????????
					+ " b.b0185,"//????????????
					+ " b.b0180, "//????
					+ " b.sortid,"//??????
					+ " b.b0111,"//
					+ " b.b0121 "
					+ " from B01 b,competence_userdept s "
					+ " where b.b0111=s.b0111 "
					+ " and s.userid='"+userVo.getId()+"' "
					+ " and s.b0111 like '"+searchDeptid+"%' "
					+ " order by length(b.b0111) asc ";*/
			
			List<HashMap<String, Object>> list=cq.getListBySQL(sql);
			String b0111 = "";
			String newB0101 = "";
			String str = "b0101";
			String unitname="";
			/*String tempid="";
			String tempname="";
			for(int i=0;i<list.size();i++){//????????????
				tempid=(String)list.get(i).get("b0121");//
				tempname=(String)list.get(i).get("b0101");//
				if(tempid.length()<searchDeptid.length()){//
				}else{
					returnOrgName(tempname,list,tempid,cq,i,"b0101");
				}
			}*/
			
			Cell cell=null;
			HashMap<String, Object> map = null;
			
			cellstyle.setAlignment(HorizontalAlignment.LEFT); 
			for(int i=0;i<list.size();i++){
				//????????????
				unitname=(String)list.get(i).get("b0194");
				if("1".equals(unitname)){
					list.get(i).put("b0194", "????????");	
				}else if("2".equals(unitname)){
					list.get(i).put("b0194", "????????");
				}else if("3".equals(unitname)){
					list.get(i).put("b0194", "????????");
				}
				
				//????????????
				b0111 = (String)list.get(i).get("b0111");//
				newB0101 = b0111Map.get(b0111);
				if(newB0101==null){
					list.get(i).put(str, "????????");
				}else{
					list.get(i).put(str, newB0101);
				}
				
				map=list.get(i);
				Row row=sheet.createRow(i+2);
				row.setHeight((short)(25*20));
				cell=row.createCell(0);cell.setCellStyle(cellstyle); cell.setCellValue((String)map.get("b0101"));  //????????
				//cellstyle.setAlignment(HorizontalAlignment.CENTER); 
				cell=row.createCell(1);cell.setCellStyle(cellstyle); cell.setCellValue((String)map.get("b0104"));  //????????
				cell=row.createCell(2);cell.setCellStyle(cellstyle); cell.setCellValue((String)map.get("b0114"));  //????????
				cell=row.createCell(3);cell.setCellStyle(cellstyle); cell.setCellValue(codeValues.get("ZB01"+(String)map.get("b0117")));  //????????
				cell=row.createCell(4);cell.setCellStyle(cellstyle); cell.setCellValue(codeValues.get("ZB87"+(String)map.get("b0124")));  //????????
				cell=row.createCell(5);cell.setCellStyle(cellstyle); cell.setCellValue(codeValues.get("ZB03"+(String)map.get("b0127")));  //????????
				cell=row.createCell(6);cell.setCellStyle(cellstyle); cell.setCellValue(codeValues.get("ZB04"+(String)map.get("b0131")));  //????????
				cell=row.createCell(7);cell.setCellStyle(cellstyle); cell.setCellValue((String)map.get("b0194"));  //????????????
				cell=row.createCell(8);cell.setCellStyle(cellstyle); cell.setCellValue((String)map.get("b0227"));  //??????????
				cell=row.createCell(9);cell.setCellStyle(cellstyle); cell.setCellValue((String)map.get("b0232"));  //??????????????????
				cell=row.createCell(10);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("b0233")); //????????????????????
				cell=row.createCell(11);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("b0236")); //??????????
				cell=row.createCell(12);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("b0234")); //"??????????
				cell=row.createCell(13);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("b0238")); //????????????????????????
				cell=row.createCell(14);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("b0239")); //????????????????????????
				cell=row.createCell(15);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("b0150")); //????????????????
				cell=row.createCell(16);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("b0183")); //????????????
				cell=row.createCell(17);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("b0185")); //????????????
				cell=row.createCell(18);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("b0180")); //????
				cell=row.createCell(19);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("sortid"));//??????
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * A01????
	 * @param sheet
	 * @throws Exception
	 */
	public void exportData2(Sheet sheet,Workbook workbook,CellStyle cellstyle,Map<String,String> b0111Map,String tableExt,Map<String, String> codeValues) throws Exception{
		try{
			CommQuery cq=new CommQuery();
			String sql="  select"
					+ " a01.a1701 , "//????
					+ " a01.a0121 , "//????????
					+ " a01.a0180, "//????
					+ " a01.a0101 ,  "//????
					+ " a01.a0184, "//????????
					+ " a01.a0104, "//????
					+ " a01.a0107,"//????????
					+ " a01.a0111a  ,"//????
					+ " a01.a0114a ,"//??????
					+ " a01.a0115a, "//??????
					+ " a01.a0117,"//????
					+ " a01.a0128, "//????????
					+ " a01.a0134,"//????????????	
					+ " a01.a0140 , "//????????????
					+ " a01.a0144, "
					+ " a01.a3921, "
					+ " a01.a3927, "
					+ " a01.a0141 , "//???????? 
					+ " a01.a0160,   "//????????
					+ " a01.a0163, "//????????????	
					+ " a01.a0165, "//????????
					+ " a01.a0187a, "//????????????????
					+ " a01.a0192a  ,"//????????????????????
					+ " a01.a0192 , "//????????????????????
					+ " a01.a0221  ,"//??????????
					+ " a01.a0288, "//????????????????
					+ " a01.a0192e, "//??????
					+ " a01.a0192c, "//????????????
					+ " a01.a0196  , "//????????????
					+ " a01.a0197 , "//???????????????????????????? 
					+ " a01.a0195  ,"//????????????????
					+ " a01.a14z101 , "//????????
					+ " a01.a15z101, "//????????????????
					+ " a01.a0120 ,  "//????
					+ " a01.a0122  ,"//????????????????????????
					+ " a01.a2949, "//??????????????
					+ " a01.a0195 a0195id "
					+ " from a01 a01,TEMP"+tableExt+" t "
							+ "where t.a0000 = a01.a0000 ";
			/*String sql="  select"
					+ " a01.a1701 , "//????
					+ " (select code_name3 from code_value where code_column_name='A0121' and code_value=a01.A0121)  a0121 , "//????????
					+ " a01.a0180, "//????
					+ " a01.a0101 ,  "//????
					+ " a01.a0184, "//????????
					+ " (select code_name3 from code_value where code_column_name='A0104' and code_value=a01.A0104)  a0104, "//????
					+ "  a01.a0107,"//????????
					+ "  a01.a0111a  ,"//????
					+ " a01.a0114a ,"//??????
					+ " (select code_name3 from code_value where code_type='ZB01' and code_value=a01.a0115a) a0115a, "//??????
					+ "  (select code_name3 from code_value where code_column_name='A0117' and code_value=a01.A0117) a0117,"//????
					+ "   a01.a0128, "//????????
					+ " a01.a0134,"//????????????	
					+ "  a01.a0140 , "//????????????
					+ " a01.a0144, "
					+ " (select code_name3 from code_value where code_type='GB4762' and code_value=a01.a3921) a3921, "
					+ " (select code_name3 from code_value where code_type='GB4762' and code_value=a01.a3927) a3927, "
					+ "  (select code_name3 from code_value where code_type='GB4762' and code_value=a01.A0141)  a0141 , "//???????? 
					+ "  (select code_name3 from code_value where code_column_name='A0160' and code_value=a01.A0160)  a0160,   "//????????
					+ " (select code_name3 from code_value where code_column_name='A0163' and code_value=a01.A0163)  a0163, "//????????????	
					+ "  (select code_name3 from code_value where code_column_name='A0165' and code_value=a01.A0165) a0165, "//????????
					+ " a01.a0187a, "//????????????????
					+ " a01.a0192a  ,"//????????????????????
					+ " a01.a0192 , "//????????????????????
					+ "  (select code_name3 from code_value where code_type='ZB09' and code_value=a01.A0221)  a0221  ,"//??????????
					+ " a01.a0288, "//????????????????
					+ " (select code_name3 from code_value where code_column_name='A0192D' and code_value=a01.A0192D)   a0192d, "//??????
					+ " a01.a0192c, "//????????????
					+ " a01.a0196  , "//????????????
					+ "  a01.a0197 , "//???????????????????????????? 
					+ "  a01.a0195  ,"//????????????????
					+ " a01.a14z101 , "//????????
					+ " a01.a15z101, "//????????????????
					+ "  (select code_name3 from code_value where code_column_name='A0120' and code_value=a01.A0120)  a0120 ,  "//????
					+ " (select code_name3 from code_value where code_column_name='A0122' and code_value=a01.A0122) a0122  ,"//????????????????????????
					+ " a01.a2949, "//??????????????
					+ " a01.a0195 a0195id "
					+ " from a01 a01 "
					+ " where a01.status!='4' "
					+ " and exists (select 1 from a02, competence_userdept s where"
						+ " a02.a0201b=s.b0111 "
						+ " and s.b0111 like '"+searchDeptid+"%' "
						+ " and s.userid='"+userVo.getId()+"' "
						+ " and a01.a0000=a02.a0000 "
						+ " )" ;*/
							
			
			List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
			/*String tempid="";
			String tempname="";
			for(int i=0;i<list.size();i++){//????????????
				tempid=HBUtil.getValueFromTab("b0121", "b01", "b0111='"+((String)list.get(i).get("a0195id")).trim()+"' ");//
				if(tempid==null){
					continue;
				}
				tempname=(String)list.get(i).get("a0195");//
				if(tempid.length()<searchDeptid.length()){//
				}else{
					returnOrgName(tempname,list,tempid,cq,i,"a0195");
				}
			}*/
			
			String b0111 = "";
			String newB0101 = "";
			String str = "a0195";
			String unitname="";
			
			Cell cell=null;HashMap<String, Object> map = null;
			for(int i=0;i<list.size();i++){
				unitname=(String)list.get(i).get("a0197");//???????????????????????????? 
				if("1".equals(unitname)){
					list.get(i).put("a0197", "??");	
				}else if("0".equals(unitname)){
					list.get(i).put("a0197", "??");	
				}
				
				//????????????
				b0111 = (String)list.get(i).get("a0195");//
				newB0101 = b0111Map.get(b0111);
				if(newB0101==null){
					list.get(i).put(str, "????????");
				}else{
					list.get(i).put(str, newB0101);
				}
				
				map=list.get(i);
				Row row=sheet.createRow(i+2);
				row.setHeight((short)(25*20));
				int m=0;
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0101"));  //????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0184"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("GB2261"+(String)map.get("a0104")));  //????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0107"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0111a")); //????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0114a")); //??????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB01"+(String)map.get("a0115a"))); //??????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("GB3304"+(String)map.get("a0117")));  //????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0128"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0134"));  //????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0140"));  //????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("GB4762"+(String)map.get("a0141")));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0144"));//???????? 12
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("GB4762"+(String)map.get("a3921")));//???????? 13 
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("GB4762"+(String)map.get("a3927")));//???????? 14
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB125"+(String)map.get("a0160")));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB126"+(String)map.get("a0163")));  //????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB130"+(String)map.get("a0165")));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0187a")); //????????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0192a")); //????????????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0192"));  //????????????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB09"+(String)map.get("a0221")));  //??????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0288"));  //????????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB148"+(String)map.get("a0192e"))); //??????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0192c")); //????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0196"));  //????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0197"));  //????????????????????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0195"));  //????????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a1701"));  //????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a14z101"));//???????? 
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a15z101"));//???????????????? 
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB134"+(String)map.get("a0120")));  //????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB135"+(String)map.get("a0121")));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB139"+(String)map.get("a0122")));  //????????????????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a2949"));  //??????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0180"));  //????
				
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * A02????
	 * @param sheet
	 * @throws Exception
	 */
	public void exportData3(Sheet sheet,Workbook workbook,CellStyle cellstyle,Map<String,String> b0111Map,String tableExt,Map<String, String> codeValues) throws Exception{
		try{
			CommQuery cq=new CommQuery();
			String sql="select t.a0101,"
					+ " t.a0184,"
					+ " a02.a0201b, "
					+ " a02.a0201d, "
					+ " a02.a0201e, "
					+ " a02.a0215a, "
					+ " a02.a0219, "
					+ " a02.a0223, "
					+ " a02.a0225, "
					+ " a02.a0243, "
					+ " a02.a0245, "
					+ " a02.a0247, "
					+ " a02.a0251b, "
					+ " a02.a0255, "
					+ " a02.a0265, "
					+ " a02.a0267, "
					+ " a02.a0272, "
					+ " a02.a0281, "
					+ " a02.a0279 "
					+ " from a02 a02,TEMP"+tableExt+" t "
					+ " where t.a0000 = a02.a0000 ";
			/*String sql="select a01.a0101,"
					+ " a01.a0184,"
					+ " a02.a0201b, "
					+ "  a02.a0201d, "
					+ " (select code_name3 from code_value where code_column_name='A0201E' and code_value=a02.A0201E)  a0201e, "
					+ "   a02.a0215a, "
					+ "  a02.a0219, "
					+ "   a02.a0223, "
					+ "   a02.a0225, "
					+ "   a02.a0243, "
					+ "   a02.a0245, "
					+ "  (select code_name3 from code_value where code_column_name='A0247' and code_value=a02.A0247)  a0247, "
					+ "   a02.a0251b, "
					+ "  (select code_name3 from code_value where code_column_name='A0255' and code_value=a02.A0255)  a0255, "
					+ "   a02.a0265, "
					+ "   a02.a0267, "
					+ "   a02.a0272, "
					+ "   a02.a0281, "
					+ "   a02.a0279,"
					+ " b01.b0121,"
					+ " b01.b0101 "
					+ "  from a02 a02 , a01 a01,competence_userdept s,b01 b01 "
					+ " where a01.a0000=a02.a0000 "
					+ " and a02.a0201b=s.b0111 "
					+ " and a02.a0201b=b01.b0111 "
						+ " and a01.status!='4' "
						+ " and s.userid='"+userVo.getId()+"' "
						+ " and s.b0111 like '"+searchDeptid+"%' " ;*/
			List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
			/*String tempid="";
			String tempname="";
			for(int i=0;i<list.size();i++){
				tempid=(String)list.get(i).get("b0121");//
				tempname=(String)list.get(i).get("b0101");//
				if(tempid.length()<searchDeptid.length()){//
				}else{
					returnOrgName(tempname,list,tempid,cq,i,"a0201a");
				}
			}*/
			String b0111 = "";
			String newB0101 = "";
			String str = "a0201b";
			String unitname="";
			
			Cell cell=null;HashMap<String, Object> map=null;
			for(int i=0;i<list.size();i++){
				unitname=(String)list.get(i).get("a0201d");//????????????
				if("1".equals(unitname)){
					list.get(i).put("a0201d", "??");	
				}else if("0".equals(unitname)){
					list.get(i).put("a0201d", "??");	
				}
				unitname=(String)list.get(i).get("a0219");//????????????
				if("1".equals(unitname)){
					list.get(i).put("a0219", "??");	
				}else if("2".equals(unitname)){
					list.get(i).put("a0219", "??");	
				}
				
				unitname=(String)list.get(i).get("a0251b");//????????????
				if("1".equals(unitname)){
					list.get(i).put("a0251b", "??");	
				}else if("0".equals(unitname)){
					list.get(i).put("a0251b", "??");	
				}
				
				unitname=(String)list.get(i).get("a0281");//????????????
				if("true".equals(unitname)||"1".equals(unitname)){
					list.get(i).put("a0281", "????");	
				}else{
					list.get(i).put("a0281", "??????");	
				}
				
				unitname=(String)list.get(i).get("a0279");//??????
				if("1".equals(unitname)){
					list.get(i).put("a0279", "??");	
				}else if("0".equals(unitname)){
					list.get(i).put("a0279", "??");	
				}
				
				//????????????
				b0111 = (String)list.get(i).get("a0201b");//
				newB0101 = b0111Map.get(b0111);
				if(newB0101==null){
					list.get(i).put(str, "????????");
				}else{
					list.get(i).put(str, newB0101);
				}
				
				map=list.get(i);
				Row row=sheet.createRow(i+2);
				row.setHeight((short)(25*20));
				int m=0;
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0101"));  //????
				//cell=row.createCell(m++);cell.setCellValue((String)map.get("a0107"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0184"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0201b")); //????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0201d")); //????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB129"+(String)map.get("a0201e"))); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0215a")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0219"));  //????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0223"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0225"));  //??????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0243"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0245")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB122"+(String)map.get("a0247"))); //????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0251b"));//????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB14"+(String)map.get("a0255"))); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0265")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0267")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0272")); //????????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0281")); //????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0279")); //??????
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * A05????????
	 * @param sheet
	 * @throws Exception
	 */
	public void exportData4(Sheet sheet,Workbook workbook,CellStyle cellstyle,String tableExt,Map<String, String> codeValues) throws Exception{
		try{
			CommQuery cq=new CommQuery();
			String sql=" select t.a0101, "
					+ " t.a0184, "
					+ " a05.a0531, "
					+ " a05.a0501b, "
					+ " a05.a0504, "
					+ " a05.a0511, "
					+ " a05.a0517, "
					+ " a05.a0524 "
					+ "  from a05 a05,TEMP"+tableExt+" t "
					+ " where t.a0000 = a05.a0000 ";
			/*String sql=" select a01.a0101, "
						+ " a01.a0184, "
						+ " a01.a0107, "
						+ " a05.a0531, "
						+ " (select code_name3 from code_value where code_type='ZB09' and code_value=a05.a0501b) a0501b, "
						+ " a05.a0504, "
						+ " a05.a0511, "
						+ " a05.a0517, "
						+ " a05.a0524 "
						+ "  from a05 a05,a01 a01 "
						+ " where a05.a0000=a01.a0000 "
							+ " and a01.status!='4' "
							+ " and exists (select 1 from a02, competence_userdept s where"
							+ " a02.a0201b=s.b0111 "
							+ " and s.b0111 like '"+searchDeptid+"%' "
							+ " and s.userid='"+userVo.getId()+"'"
							+ " and a05.a0000=a02.a0000 "
							+ " )";*/
			List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
			String unitname="";
			
			Cell cell=null;HashMap<String, Object> map=null;
			for(int i=0;i<list.size();i++){
				unitname=(String)list.get(i).get("a0524");//????
				if("1".equals(unitname)){
					list.get(i).put("a0524", "????");	
				}else if("0".equals(unitname)){
					list.get(i).put("a0524", "????");	
				}
				
				unitname=(String)list.get(i).get("a0531");//??????????????????????????
				//??????????????????????(0--??????????1--????)
				if("1".equals(unitname)){
					list.get(i).put("a0531", "??????????");	
				}else if("0".equals(unitname)){
					list.get(i).put("a0531", "????????");	
				}
				
				map=list.get(i);
				Row row=sheet.createRow(i+2);
				row.setHeight((short)(25*20));
				int m=0;
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0101")); //????
				//cell=row.createCell(m++);cell.setCellValue((String)map.get("a0107"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0184")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0531")); //??????????????????????????
				if("????????".equals((String)map.get("a0531"))){
					cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB09"+(String)map.get("a0501b")));//????????????????
				}else{
					cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB148"+(String)map.get("a0501b")));//????????????????
				}
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0504")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0511")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0517")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0524")); //????
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * A06????????
	 * @param sheet
	 * @throws Exception
	 */
	public void exportData5(Sheet sheet,Workbook workbook,CellStyle cellstyle,String tableExt,Map<String,String> codeValues) throws Exception{
		try{
			CommQuery cq=new CommQuery();
			String sql=" select t.a0101, "
					+ " t.a0184, "
					+ " a06.a0602, "
					+ " a06.a0601, "
					+ " a06.A0604, "
					+ " a06.a0607, "
					+ " a06.a0611, "
					+ " a06.a0699 "
					+ " from a06 a06,TEMP"+tableExt+" t "
					+ " where t.a0000 = a06.a0000 ";
			/*String sql=" select a01.a0101, "
						+ " a01.a0107, "
						+ " a01.a0184, "
						+ " a06.a0602, "
						+ "  (select code_name3 from code_value where code_column_name='A0601' and code_value=a06.A0601) a0601, "
						+ " A0604, "
						+ "  (select code_name3 from code_value where code_column_name='A0607' and code_value=a06.A0607) a0607, "
						+ " a06.a0611, "
						+ " a06.a0699 "
						+ " from a06 a06,a01 a01 "
						+ " where a06.a0000=a01.a0000 "
						+ " and a01.status!='4' "
						+ " and exists  (select 1 from a02, competence_userdept s where"
							+ " a02.a0201b=s.b0111 "
							+ " and s.b0111 like '"+searchDeptid+"%' "
							+ " and s.userid='"+userVo.getId()+"' "
							+ " and a06.a0000=a02.a0000 "
							+ " )";*/
			List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
			String unitname="";
			
			Cell cell=null;HashMap<String, Object> map=null;
			for(int i=0;i<list.size();i++){
				unitname=(String)list.get(i).get("a0699");//????????
				if("true".equals(unitname)||"1".equals(unitname)){
					list.get(i).put("a0699", "????");	
				}else{
					list.get(i).put("a0699", "??????");	
				}
				
				map=list.get(i);
				Row row=sheet.createRow(i+2);
				row.setHeight((short)(25*20));
				int m=0;
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0101"));//????
				//cell=row.createCell(m++);cell.setCellValue((String)map.get("a0107"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0184"));//????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("GB8561"+(String)map.get("a0601")));//????????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0602"));//????????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0604"));//????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB24"+(String)map.get("a0607")));//????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0611"));//????????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0699"));//????????
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * A08????????
	 * @param sheet
	 * @throws Exception
	 */
	public void exportData6(Sheet sheet,Workbook workbook,CellStyle cellstyle,String tableExt,Map<String,String> codeValues) throws Exception{
		try{
			CommQuery cq=new CommQuery();
			String sql="select t.a0101, "
				+ " t.a0184, "
				+ " a08.a0801a, "
				+ " a08.a0801b, "
				+ " a08.A0901A, "
				+ " a08.a0901b, "
				+ " a08.a0804, "
				+ " a08.a0807, "
				+ " a08.a0904, "
				+ " a08.a0814, "
				+ " a08.a0824, "
				+ " a08.a0827, "
				+ " a08.a0837, "
				+ " a08.a0811, "
				+ " a08.a0899 "
				+ " from a08 a08,TEMP"+tableExt+" t "
				+ " where t.a0000 = a08.a0000 ";
			/*String sql="select a01.a0101, "
					+ " a01.a0107, "
				+ " a01.a0184, "
				+ " a08.a0801a, "
				+ " (select code_name3 from code_value where code_column_name='A0801B' and code_value=a08.A0801B)  a0801b, "
				+ " a08.A0901A, "
				+ " (select code_name3 from code_value where code_column_name='A0901B' and code_value=a08.A0901B) a0901b, "
				+ " a08.a0804, "
				+ " a08.a0807, "
				+ " a08.a0904, "
				+ " a08.a0814, "
				+ " a08.a0824, "
				+ " (select code_name3 from code_value where code_column_name='A0827' and code_value=a08.A0827)  a0827, "
				+ "  (select code_name3 from code_value where code_column_name='A0837' and code_value=a08.A0837) a0837, "
				+ " a08.a0811, "
				+ " a08.a0898 "
				+ " from a08 a08,a01 a01 where a08.a0000=a01.a0000 "
					+ " and a01.status!='4' "
					+ " and exists  (select 1 from a02, competence_userdept s where"
							+ " a02.a0201b=s.b0111 "
							+ " and s.b0111 like '"+searchDeptid+"%' "
							+ " and s.userid='"+userVo.getId()+"' "
							+ " and a08.a0000=a02.a0000 "
							+ " )";*/
			List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
			String unitname="";
			
			Cell cell=null;
			HashMap<String, Object> map = null;
			for(int i=0;i<list.size();i++){
				unitname=(String)list.get(i).get("a0899");//????????
				if("true".equals(unitname)||"1".equals(unitname)){
					list.get(i).put("a0899", "????");	
				}else{
					list.get(i).put("a0899", "??????");	
				}
				
				map=list.get(i);
				Row row=sheet.createRow(i+2);
				row.setHeight((short)(25*20));
				int m=0;
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0101")); //????
				//cell=row.createCell(m++);cell.setCellValue((String)map.get("a0107"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0184")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0801a"));//????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB64"+(String)map.get("a0801b")));//????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0901a"));//????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("GB6864"+(String)map.get("a0901b")));//????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0804")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0807")); //??????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0904")); //????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0814")); //????????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0824"));//????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("GB16835"+(String)map.get("a0827")));//????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB123"+(String)map.get("a0837")));//????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0811"));//????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0899"));//????????
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * A14????
	 * @param sheet
	 * @throws Exception
	 */
	public void exportData7(Sheet sheet,Workbook workbook,CellStyle cellstyle,String tableExt,Map<String, String> codeValues) throws Exception{
		try{
			CommQuery cq=new CommQuery();
			String sql="select t.a0101, "
				 + " t.a0184, "
				 + "  a14.a1404a, "
				 + "  a14.a1404b, "
				 + "  a14.a1407, "
				 + "  a14.a1411a, "
				 + "  a14.a1414, "
				 + "  a14.a1415, "
				 + "  a14.a1424, "
				 + "  a14.a1428 "
				 + " from a14 a14,TEMP"+tableExt+" t "
				 + " where t.a0000 = a14.a0000 ";
			/*String sql="select a01.a0101, "
					 + " a01.a0107, "
				 + " a01.a0184, "
				 + "  a14.a1404a, "
				 + "  (select code_name3 from code_value where code_type='ZB67' and code_value=a14.A1404B) a1404b, "
				 + "  a14.a1407, "
				 + "  a14.a1411a, "
				 + "  (select code_name3 from code_value where code_type='ZB03' and code_value=a14.a1414) a1414, "
				 + "  (select code_name3 from code_value where code_column_name like 'A1415%' and code_value =a14.A1415)  a1415, "
				 + "  a14.a1424, "
				 + " (select code_name3 from code_value where code_column_name='A1428' and code_value=a14.A1428)  a1428 "
				 + " from a14 ,a01 where a01.a0000=a14.a0000 "
					+ " and a01.status!='4' "
					+ " and exists (select 1 from a02, competence_userdept s where"
							+ "  a02.a0201b=s.b0111 "
							+ " and s.b0111 like '"+searchDeptid+"%' "
							+ " and s.userid='"+userVo.getId()+"' "
									+ " and a14.a0000=a02.a0000 "
							+ " )";*/
			List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
			Cell cell=null;HashMap<String, Object> map=null;
			for(int i=0;i<list.size();i++){
				map=list.get(i);
				Row row=sheet.createRow(i+2);
				row.setHeight((short)(25*20));
				int m=0;
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0101")); //????
				//cell=row.createCell(m++);cell.setCellValue((String)map.get("a0107"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0184")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a1404a"));//????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB65"+(String)map.get("a1404b")));//????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a1407")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a1411a"));//????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB03"+(String)map.get("a1414"))); //????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB09"+(String)map.get("a1415"))); //????????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a1424")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB128"+(String)map.get("a1428"))); //????????????
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * A15????
	 * @param sheet
	 * @throws Exception
	 */
	public void exportData8(Sheet sheet,Workbook workbook,CellStyle cellstyle,String tableExt,Map<String,String> codeValues) throws Exception{
		try{
			CommQuery cq=new CommQuery();
			String sql=" select t.a0101, "
						+ " t.a0184, "
						+ " a15.a1517, "
						+ " a15.a1521 "
						+ "  from a15,TEMP"+tableExt+" t "
						+ " where t.a0000 = a15.a0000 ";
			/*String sql=" select a01.a0101, "
					+ " a01.a0107, "
						+ " a01.a0184, "
						+ "  (select code_name3 from code_value where code_column_name = 'A1517' and code_value =a15.A1517)  a1517, "
						+ " a15.a1521 "
						+ "  from a15,a01 where a01.a0000=a15.a0000 "
						+ " and a01.status!='4' "
						+ " and exists  (select 1 from a02, competence_userdept s where"
							+ " a02.a0201b=s.b0111 "
							+ " and s.b0111 like '"+searchDeptid+"%' "
							+ " and s.userid='"+userVo.getId()+"' "
									+ " and a15.a0000=a02.a0000 "
							+ " )";*/
			List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
			Cell cell=null;HashMap<String, Object> map=null;
			for(int i=0;i<list.size();i++){
				map=list.get(i);
				Row row=sheet.createRow(i+2);
				row.setHeight((short)(25*20));
				int m=0;
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0101"));//????
				//cell=row.createCell(m++);cell.setCellValue((String)map.get("a0107"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0184"));//????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue(codeValues.get("ZB18"+(String)map.get("a1517")));//????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a1521"));//????????
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * A36??????????????
	 * @param sheet
	 * @throws Exception
	 */
	public void exportData9(Sheet sheet,Workbook workbook,CellStyle cellstyle,String tableExt,Map<String, String> codeValues) throws Exception{
		try{
			CommQuery cq=new CommQuery();
			String sql=" select t.a0101, "
						+ " t.a0184, "
						+ " a36.a3601, "
						+ " a36.a3604a, "
						+ " a36.a3607, "
						+ " a36.a3627, "
						+ " a36.a3611, "
						+ " a36.sortid "
						+ " from a36,TEMP"+tableExt+" t "
						+ " where t.a0000 = a36.a0000 ";
			/*String sql=" select a01.a0101, "
					+ " a01.a0107, "
						+ " a01.a0184, "
						+ " a36.a3601, "
						+ " a36.a3604a, "
						+ " a36.a3607, "
						+ " a36.a3627, "
						+ " a36.a3611, "
						+ " a36.sortid "
						+ " from a36,a01 where a36.a0000=a01.a0000 "
						+ " and a01.status!='4' "
						+ " and exists (select 1 from a02, competence_userdept s where"
							+ " a02.a0201b=s.b0111 "
							+ " and s.b0111 like '"+searchDeptid+"%' "
							+ " and s.userid='"+userVo.getId()+"' "
									+ " and a36.a0000=a02.a0000 "
							+ " )";*/
			List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
			Cell cell=null;HashMap<String, Object> map=null;
			for(int i=0;i<list.size();i++){
				map=list.get(i);
				Row row=sheet.createRow(i+2);
				row.setHeight((short)(25*20));
				int m=0;
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0101")); //????
				//cell=row.createCell(m++);cell.setCellValue((String)map.get("a0107"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0184")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a3601")); //????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a3604a"));//????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a3607")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a3627")); //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a3611")); //??????????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("sortid"));//????
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	public void exportData10(Sheet sheet,Workbook workbook,CellStyle cellstyle,String tableExt,Map<String, String> codeValues) throws Exception{
		try{
			CommQuery cq=new CommQuery();
			String sql="select t.A0101, "
					 + " t.A0184, "
					 + "  a99z1.A99Z101, "
					 + "  a99z1.A99Z102, "
					 + "  a99z1.A99Z103, "
					 + " a99z1.A99Z104 "
					 + " from a99z1 a99z1,TEMP"+tableExt+" t "
					 + " where t.a0000 = a99z1.a0000 ";
			/*String sql="select a01.A0101, "
					+ " a01.a0107, "
					 + " a01.A0184, "
					 + "  a99z1.A99Z101, "
					 + "  a99z1.A99Z102, "
					 + "  a99z1.A99Z103, "
					 + " a99z1.A99Z104 "
					 + " from a99z1 a99z1 ,a01 a01 where a01.a0000=a99z1.a0000 "
					 + " and a01.status!='4' "
						+ " and exists  (select 1 from a02, competence_userdept s where"
							+ " a02.a0201b=s.b0111 "
							+ " and s.b0111 like '"+searchDeptid+"%' "
							+ " and s.userid='"+userVo.getId()+"' "
									+ " and a99z1.a0000=a02.a0000 "
							+ " )";*/
			List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
			String unitname="";
			
			Cell cell=null;HashMap<String, Object> map=null;
			for(int i=0;i<list.size();i++){
				unitname=(String)list.get(i).get("a99z101");//????????
				if("1".equals(unitname)){
					list.get(i).put("a99z101", "??");	
				}else if("0".equals(unitname)){
					list.get(i).put("a99z101", "??");	
				}
				
				unitname=(String)list.get(i).get("a99z103");//??????????
				if("1".equals(unitname)){
					list.get(i).put("a99z103", "??");	
				}else if("0".equals(unitname)){
					list.get(i).put("a99z103", "??");	
				}
				
				map=list.get(i);
				Row row=sheet.createRow(i+2);
				row.setHeight((short)(25*20));
				int m=0;
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0101"));  //????
				//cell=row.createCell(m++);cell.setCellValue((String)map.get("a0107"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a0184"));  //????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a99z101"));//????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a99z102"));//????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a99z103"));//??????????
				cell=row.createCell(m++);cell.setCellStyle(cellstyle);cell.setCellValue((String)map.get("a99z104"));//??????????????
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
}
