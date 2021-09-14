package com.insigma.siis.local.pagemodel.xbrm;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.sys.comm.CommQueryBS;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.Aa10;
import com.insigma.siis.local.business.entity.Js19;
import com.insigma.siis.local.business.entity.Js33;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;
import com.insigma.siis.local.pagemodel.xbrm.pojo.ExcelA57Pojo;
import com.insigma.siis.local.pagemodel.xbrm.pojo.VJSA01;
import com.insigma.siis.local.pagemodel.xbrm.tpl.ExcelReturnParam;
import com.insigma.siis.local.pagemodel.xbrm.tpl.ExcelStyleUtil;
import com.insigma.siis.local.pagemodel.xbrm.tpl.NumberFormatUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.utils.AA10Util;
import com.utils.CommonQueryBS;
















import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import sun.misc.BASE64Encoder;

public class QCJSFileExportBS {
	
	private PageModel pm;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	/**
	 * 文件输出路径
	 */
	private String outputpath = JSGLBS.HZBPATH+"zhgboutputfiles/";
	/**
	 * 文件名
	 */
	final private List<String> outputFileNameList = new ArrayList<String>();
	/**
	 * 表样名
	 */
	private String sheetName ;
	
	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<String> getOutputFileNameList() {
		return outputFileNameList;
	}
	
	public String getOutputpath() {
		return outputpath;
	}
	public void setOutputpath(String outputpath) {
		this.outputpath = outputpath;
	}
	public PageModel getPm() {
		return pm;
	}
	public void setPm(PageModel pm) {
		this.pm = pm;
	}
	public QCJSFileExportBS() {
		
	}
	public QCJSFileExportBS(PageModel pm) {
		super();
		this.pm = pm;
	}
	
	
	public String matePersonInfo(String rbId,String dc005,String cur_hj,String cur_hj_4,String js0100WhereSql){
		//ExcelStyleUtil.js0100WhereSql = "";
		String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005,"");
		if(!StringUtil.isEmpty(js0100WhereSql)){
			sql = "select a36.a3601,a36.a3604a,a36.a3611,t.a0000,t.a0101,t.a0104a,t.a0184,t.a0192 from A36 a36,( select a01.a0000,a01.a0101,(case when a01.a0104='1' then '男' when a01.a0104='2' then '女' else '' end) a0104a,a01.a0184,a01.a0192 "
					+ " from a01,("+sql+") m,js02 where a01.a0000 = m.a0000 and m.js0100 = js02.js0100 and js02.js0100 in('"+js0100WhereSql+"')) t where a36.a0000=t.a0000 and  a36.A3604A in('妻子','丈夫')";
		}else{
			sql = "select a36.a3601,a36.a3604a,a36.a3611,t.a0000,t.a0101,t.a0104a,t.a0184,t.a0192 from A36 a36,( select a01.a0000,a01.a0101,(case when a01.a0104='1' then '男' when a01.a0104='2' then '女' else '' end) a0104a,a01.a0184,a01.a0192 "
					+ " from a01,("+sql+") m,js02 where a01.a0000 = m.a0000 and m.js0100 = js02.js0100) t where a36.a0000=t.a0000 and  a36.A3604A in('妻子','丈夫')";
		}
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		StringBuffer strB=new StringBuffer();
		try {
			stmt = session.connection().createStatement();
			rs = stmt.executeQuery(sql);
			int num=0;
			while(rs.next()){
				num=num+1;
				if(num==1){
					strB.append(num);
				}else{
					strB.append(","+num);
				}
				strB.append(","+rs.getString("a0101"));
				strB.append(","+rs.getString("a0104a"));
				strB.append(","+rs.getString("a0184"));
				strB.append(","+rs.getString("a0192"));
				String cw=rs.getString("a3604a");
				strB.append(","+cw);
				if(cw.equals("丈夫")){
					strB.append(","+"男");
				}else{
					strB.append(","+"女");
				}
				strB.append(","+" ");//配偶身份证号A36中无
				strB.append(","+rs.getString("a3611"));
				if(num==8){
					break;//最多循环8个人
				}
			}
			//没到8个人 添加空字符串
//			for(int i=1;i<9;i++){
//				if(i==1){
//					matepersonInfoS.append("n1");
//				}else{
//					matepersonInfoS.append(","+"n"+i);
//				}
//				matepersonInfoS.append(","+"na"+i);
//				matepersonInfoS.append(","+"s"+i);
//				matepersonInfoS.append(","+"i"+i);
//				matepersonInfoS.append(","+"z"+i);
//				matepersonInfoS.append(","+"p"+i);
//				matepersonInfoS.append(","+"ps"+i);
//				matepersonInfoS.append(","+"pi"+i);
//				matepersonInfoS.append(","+"pz"+i);
//			}
			
			if(num<8){
				for(int i=num+1;i<9;i++){
					if(num==0 && i==1){
						strB.append(i);
					}else{
						strB.append(","+i);
					}
					strB.append(","+" ");
					strB.append(","+" ");
					strB.append(","+" ");
					strB.append(","+" ");
					strB.append(","+" ");
					strB.append(","+" ");
					strB.append(","+" ");
					strB.append(","+" ");
					
				}
			}
			return strB.toString();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void createTqyjrymd1(String rbId,String dc005,String cur_hj,String cur_hj_4,String js0100WhereSql, String path) {
		//ExcelStyleUtil.js0100WhereSql = "";
		/*String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005,"");
		if(!StringUtil.isEmpty(js0100WhereSql)){
			sql = "select a01.a0101,a01.a0184,a01.a0192,m.js0111,m.js0117,m.js0109,m.js0100,a01.a0104a,a01.a0104,"
					+ "a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,"
					+ "a01.fcsj,js02.js0206 from a01,("+sql+") m,js02 where a01.a0000 = m.a0000 and m.js0100 = js02.js0100 and js02.js0100 in('"+js0100WhereSql+"')";
		}else{
			sql = "select a01.a0101,a01.a0184,a01.a0192,m.js0111,m.js0117,m.js0109,m.js0100,a01.a0104a,a01.a0104,"
					+ "a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,"
					+ "a01.fcsj,js02.js0206 from a01,("+sql+") m,js02 where a01.a0000 = m.a0000 and m.js0100 = js02.js0100";
		}*/
		
		String sql = "select * from (select a01.a0101,a01.a0184,a01.a0192,js01.js0111,js01.js0117,js01.js0109,js01.js0100,a01.a0104a,a01.a0104,"
				+ " a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,a01.fcsj,js02.js0206 "
				+ " from a01,js01,js_hj,js02 where js01.js0122 = '1' and js01.js0100 = js02.js0100 and "
				+ " a01.a0000=js01.a0000 and nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and js01.rb_id in ('"+rbId+"')  "/*+hj4sql  */
				+" union select a01.a0101,a01.a0184,a01.a0192,js01.js0111,js01.js0117,js01.js0109,js01.js0100,a01.a0104a,a01.a0104,"
				+ " a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,a01.fcsj,js02.js0206 "
				+ " from v_js_a01 a01,js01,js_hj,js02 where js01.js0100 = js02.js0100 and a01.a0000=js01.a0000 and "
				+ " js01.js0122 = a01.v_xt and js0122 in ('2','3','4') and "
				+ " nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and js01.rb_id in ('"+rbId+"')  " /*+hj4sql */ +")";
		
		HBSession session = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Workbook wb = null;
		InputStream in = null;
		FileOutputStream fileOut = null;
		session = HBUtil.getHBSession();
		try {
			String p =  QCJSFileExportBS.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/tqyjrymdjgjf.xlsx").getPath();
			in = new FileInputStream(new File(p));
			wb = new XSSFWorkbook(in);
			Sheet sheet = wb.getSheetAt(0);
			conn = session.connection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			int num=1;
			short height = 0;
			CellStyle cs[] = new CellStyle[5] ;
			while(rs.next()){
				if(num==1) {
					Row row = sheet.getRow(num+1);
					Cell cell0 = row.getCell(0);
					Cell cell1 = row.getCell(1);
					Cell cell2 = row.getCell(2);
					Cell cell3 = row.getCell(3);
					Cell cell4 = row.getCell(4);
					cell0.setCellValue(num);
					cell1.setCellValue(rs.getString("a0101"));
					String s=rs.getString("a0104");
					if("1".equals(s)){
						cell2.setCellValue("男");
					}else if("2".equals(s)){
						cell2.setCellValue("女");
					}
					String a0184 = rs.getString("a0184");
					String a0192 = rs.getString("a0192");
					cell3.setCellValue(a0184!=null ? a0184 :"");
					cell4.setCellValue(a0192!=null ? a0192 :"");
					cs[0] = cell0.getCellStyle();
					cs[1] = cell1.getCellStyle();
					cs[2] = cell2.getCellStyle();
					cs[3] = cell3.getCellStyle();
					cs[4] = cell4.getCellStyle();
					height = row.getHeight();
				} else {
					Row row = sheet.createRow(num+1);
					Cell cell0 = row.createCell(0);
					Cell cell1 = row.createCell(1);
					Cell cell2 = row.createCell(2);
					Cell cell3 = row.createCell(3);
					Cell cell4 = row.createCell(4);
					cell0.setCellValue(num);
					cell1.setCellValue(rs.getString("a0101"));
					String s=rs.getString("a0104");
					if("1".equals(s)){
						cell2.setCellValue("男");
					}else if("2".equals(s)){
						cell2.setCellValue("女");
					}
					String a0184 = rs.getString("a0184");
					String a0192 = rs.getString("a0192");
					cell3.setCellValue(a0184!=null ? a0184 :"");
					cell4.setCellValue(a0192!=null ? a0192 :"");
					cell0.setCellStyle(cs[0]);
					cell1.setCellStyle(cs[1]);
					cell2.setCellStyle(cs[2]);
					cell3.setCellStyle(cs[3]);
					cell4.setCellStyle(cs[4]);
					row.setHeight(height);
				}
				num ++;
			}
			fileOut = new FileOutputStream(path+"/听取意见人员名单（纪委、公、检、法）.xlsx"); 
			wb.write(fileOut);
			wb.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void createTqyjrymd2(String rbId,String dc005,String cur_hj,String cur_hj_4,String js0100WhereSql, String path) {
		//ExcelStyleUtil.js0100WhereSql = "";
		/*String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005,"");
		if(!StringUtil.isEmpty(js0100WhereSql)){
			sql = "select a01.a0101,a01.a0184,a01.a0192,m.js0111,m.js0117,m.js0109,m.js0100,a01.a0104a,a01.a0104,"
					+ "a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,"
					+ "a01.fcsj,js02.js0206 from a01,("+sql+") m,js02 where a01.a0000 = m.a0000 and m.js0100 = js02.js0100 and js02.js0100 in('"+js0100WhereSql+"')";
		}else{
			sql = "select a01.a0101,a01.a0184,a01.a0192,m.js0111,m.js0117,m.js0109,m.js0100,a01.a0104a,a01.a0104,"
					+ "a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,"
					+ "a01.fcsj,js02.js0206 from a01,("+sql+") m,js02 where a01.a0000 = m.a0000 and m.js0100 = js02.js0100";
		}*/
		String sql = "select * from (select a01.a0101,a01.a0184,a01.a0192,js01.js0111,js01.js0117,js01.js0109,js01.js0100,a01.a0104a,a01.a0104,"
				+ " a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,a01.fcsj,js02.js0206,a3601,a3607,a3611,a3684,a3604a "
				+ " from a01,js01,js_hj,js02,(select * from (select a3601,a3607,a3611,a3684,a3604a,a0000,rank() over(partition by a0000 order by a3600) rk from a36 where a3604a in ('丈夫','妻子')) where rk='1') a36 where js01.js0122 = '1' and js01.js0100 = js02.js0100 and "
				+ " a01.a0000=js01.a0000 and nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and a36.a0000=a01.a0000 and js01.rb_id in ('"+rbId+"')  "/*+hj4sql  */
				+" union select a01.a0101,a01.a0184,a01.a0192,js01.js0111,js01.js0117,js01.js0109,js01.js0100,a01.a0104a,a01.a0104,"
				+ " a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,a01.fcsj,js02.js0206,a3601,a3607,a3611,a3684,a3604a "
				+ " from v_js_a01 a01,js01,js_hj,js02,(select * from (select a3601,a3607,a3611,a3684,a3604a,a0000,v_xt,rank() over(partition by a0000 order by a3600) rk from v_js_a36 where a3604a in ('丈夫','妻子')) where rk='1') a36 where js01.js0100 = js02.js0100 and a01.a0000=js01.a0000 and "
				+ " js01.js0122 = a01.v_xt and js0122 in ('2','3','4') and "
				+ " nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 and a36.a0000 = a01.a0000 and a36.v_xt = a01.v_xt"
				+ " and js01.rb_id in ('"+rbId+"')  " /*+hj4sql */ +")";
		HBSession session = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Workbook wb = null;
		InputStream in = null;
		FileOutputStream fileOut = null;
		session = HBUtil.getHBSession();
		try {
			String p =  QCJSFileExportBS.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/tqyjrymdwjw.xlsx").getPath();
			in = new FileInputStream(new File(p));
			wb = new XSSFWorkbook(in);
			Sheet sheet = wb.getSheetAt(0);
			conn = session.connection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			int num=1;
			short height = 0;
			CellStyle cs[] = new CellStyle[6] ;
			while(rs.next()){
				System.out.println(num);
				if(num==1) {
					Row row = sheet.getRow(num+1);
					Cell cell0 = row.getCell(0);
					Cell cell1 = row.getCell(1);
					Cell cell2 = row.getCell(2);
					Cell cell3 = row.getCell(3);
					Cell cell4 = row.getCell(4);
					cell0.setCellValue(num);
					cell1.setCellValue(rs.getString("a0101"));
					String s=rs.getString("a0104");
					if("1".equals(s)){
						cell2.setCellValue("男");
					}else if("2".equals(s)){
						cell2.setCellValue("女");
					}
					String a0184 = rs.getString("a0184");
					String a0192 = rs.getString("a0192");
					cell3.setCellValue(a0184!=null ? a0184 :"");
					cell4.setCellValue(a0192!=null ? a0192 :"");
					cs[0] = cell0.getCellStyle();
					cs[1] = cell1.getCellStyle();
					cs[2] = cell2.getCellStyle();
					cs[3] = cell3.getCellStyle();
					cs[4] = cell4.getCellStyle();
					height = row.getHeight();
					
					//a3601,a3607,a3611,a3684,a3604a
					Row row2 = sheet.getRow(num+2);
					Cell cell21 = row2.getCell(1);
					Cell cell22 = row2.getCell(2);
					Cell cell23 = row2.getCell(3);
					Cell cell24 = row2.getCell(4);
					cs[5] = cell21.getCellStyle();
					//cell21.setCellValue("（配偶）");
					String a3604a=rs.getString("a3604a");
					if(a3604a!=null && "丈夫".equals(a3604a)){
						cell22.setCellValue("男");
					}else if(a3604a!=null && "妻子".equals(a3604a)){
						cell22.setCellValue("女");
					}
					String a3684 = rs.getString("a3684");
					String a3611 = rs.getString("a3611");
					cell23.setCellValue(a3684!=null ? a3684 :"");
					cell24.setCellValue(a3611!=null ? a3611 :"");
				} else {
					Row row = sheet.createRow(num*2);
					Cell cell0 = row.createCell(0);
					Cell cell1 = row.createCell(1);
					Cell cell2 = row.createCell(2);
					Cell cell3 = row.createCell(3);
					Cell cell4 = row.createCell(4);
					cell0.setCellValue(num);
					cell1.setCellValue(rs.getString("a0101"));
					String s=rs.getString("a0104");
					if("1".equals(s)){
						cell2.setCellValue("男");
					}else if("2".equals(s)){
						cell2.setCellValue("女");
					}
					String a0184 = rs.getString("a0184");
					String a0192 = rs.getString("a0192");
					cell3.setCellValue(a0184!=null ? a0184 :"");
					cell4.setCellValue(a0192!=null ? a0192 :"");
					cell0.setCellStyle(cs[0]);
					cell1.setCellStyle(cs[1]);
					cell2.setCellStyle(cs[2]);
					cell3.setCellStyle(cs[3]);
					cell4.setCellStyle(cs[4]);
					row.setHeight(height);
					
					//a3601,a3607,a3611,a3684,a3604a
					Row row2 = sheet.createRow(num*2+1);
					Cell cell20 = row2.createCell(0);
					Cell cell21 = row2.createCell(1);
					Cell cell22 = row2.createCell(2);
					Cell cell23 = row2.createCell(3);
					Cell cell24 = row2.createCell(4);
					cell21.setCellValue("（配偶）");
					
					String a3604a=rs.getString("a3604a");
					if(a3604a!=null && "丈夫".equals(a3604a)){
						cell22.setCellValue("男");
					}else if(a3604a!=null && "妻子".equals(a3604a)){
						cell22.setCellValue("女");
					}
					String a3684 = rs.getString("a3684");
					String a3611 = rs.getString("a3611");
					cell23.setCellValue(a3684!=null ? a3684 :"");
					cell24.setCellValue(a3611!=null ? a3611 :"");
					cell20.setCellStyle(cs[0]);
					cell21.setCellStyle(cs[5]);
					cell22.setCellStyle(cs[2]);
					cell23.setCellStyle(cs[3]);
					cell24.setCellStyle(cs[4]);
					row2.setHeight(height);
					sheet.addMergedRegion(new CellRangeAddress(num*2,num*2+1 , 0, 0));
				}
				num ++;
			}
			fileOut = new FileOutputStream(path+"/听取意见人员名单（卫健委）.xlsx"); 
			wb.write(fileOut);
			wb.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getPersonsInfo(String rbId,String dc005,String cur_hj,String cur_hj_4,String js0100WhereSql){
		//ExcelStyleUtil.js0100WhereSql = "";
		String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005,"");
		if(!StringUtil.isEmpty(js0100WhereSql)){
			sql = "select a01.a0101,a01.a0184,a01.a0192,m.js0111,m.js0117,m.js0109,m.js0100,a01.a0104a,a01.a0104,"
					+ "a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,"
					+ "a01.fcsj,js02.js0206 from a01,("+sql+") m,js02 where a01.a0000 = m.a0000 and m.js0100 = js02.js0100 and js02.js0100 in('"+js0100WhereSql+"')";
		}else{
			sql = "select a01.a0101,a01.a0184,a01.a0192,m.js0111,m.js0117,m.js0109,m.js0100,a01.a0104a,a01.a0104,"
					+ "a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,"
					+ "a01.fcsj,js02.js0206 from a01,("+sql+") m,js02 where a01.a0000 = m.a0000 and m.js0100 = js02.js0100";
		}
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		StringBuffer strB=new StringBuffer();
		try {
			stmt = session.connection().createStatement();
			rs = stmt.executeQuery(sql);
			int num=0;
			while(rs.next()){
				num=num+1;
				if(num==1){
					strB.append(num);
				}else{
					strB.append(","+num);
				}
				strB.append(","+rs.getString("a0101"));
				String s=rs.getString("a0104");
				if("1".equals(s)){
					strB.append(","+"男");
				}else if("2".equals(s)){
					strB.append(","+"女");
				}else{
					strB.append(","+" ");
				}
				strB.append(","+rs.getString("a0184"));
				strB.append(","+rs.getString("a0192"));
				
				if(num==8){
					break;//最多循环8个人
				}
			}
			//没到8个人 添加空字符串
			if(num<8){
				for(int i=num+1;i<9;i++){
					strB.append(","+i);
					strB.append(","+" ");
					strB.append(","+" ");
					strB.append(","+" ");
					strB.append(","+" ");
				}
			}
			return strB.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public int getPersonsNum(String rbId,String dc005,String cur_hj,String cur_hj_4,String js0100WhereSql){
		//ExcelStyleUtil.js0100WhereSql = "";
		/*String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005);
		
		if(!StringUtil.isEmpty(js0100WhereSql)){
			sql = "select a01.a0101,a01.a0192,m.js0111,m.js0117,m.js0109,m.js0100,a01.a0104a,"
					+ "a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,"
					+ "a01.fcsj,js02.js0206 from a01,("+sql+") m,js02 where a01.a0000 = m.a0000 and m.js0100 = js02.js0100 and js02.js0100 in('"+js0100WhereSql+"')";
		}else{
			sql = "select a01.a0101,a01.a0192,m.js0111,m.js0117,m.js0109,m.js0100,a01.a0104a,"
					+ "a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,"
					+ "a01.fcsj,js02.js0206 from a01,("+sql+") m,js02 where a01.a0000 = m.a0000 and m.js0100 = js02.js0100";
		}*/
		String sql = getSql_1(cur_hj, cur_hj_4, rbId,dc005,"");
		if(!StringUtil.isEmpty(js0100WhereSql)){
			sql = "select m.a0101,m.a0192,m.js0111,m.js0117,m.js0109,m.js0100,m.a0104a,"
					+ "m.a0107,m.qrzxl,m.a0111a,m.a0134,m.a0140,m.zcsj,"
					+ "m.fcsj,js02.js0206 from ("+sql+") m,js02 where m.js0100 = js02.js0100 and js02.js0100 in('"+js0100WhereSql+"')";
		}else{
			sql = "select m.a0101,m.a0192,m.js0111,m.js0117,m.js0109,m.js0100,m.a0104a,"
					+ "m.a0107,m.qrzxl,m.a0111a,m.a0134,m.a0140,m.zcsj,"
					+ "m.fcsj,js02.js0206 from ("+sql+") m,js02 where m.js0100 = js02.js0100";
		}
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		int num=0;
		try {
			stmt = session.connection().createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				num=num+1;
			}
			return num;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 通用sql
	 * @param cur_hj
	 * @param cur_hj_4
	 * @param rbId
	 * @param dc005
	 * @return
	 */
	public static String getSql_1(String cur_hj,String cur_hj_4,String rbId,String dc005, String js0100WhereSql){
		String ref_dc001 = "js01.dc001";
		String orderbyfield = " JS_SORT";
		//String dc001_alias = "dc001";
		//String f
		if(RMHJ.TAO_LUN_JUE_DING.equals(cur_hj)){
			cur_hj = cur_hj_4;
			if(RMHJ.TAO_LUN_JUE_DING.equals(cur_hj)){
				orderbyfield = " JS_SORT4";
			}
		}
		if(RMHJ.JI_BEN_QING_KUANG.equals(cur_hj)){
			cur_hj = RMHJ.DONG_YI;
			
		}
		String hj4sql = " and js_type like '"+cur_hj+"%' ";
		if(RMHJ.DONG_YI.equals(cur_hj)){
			hj4sql = "";
			orderbyfield = " js0113";
		}
		
		if(RMHJ.REN_MIAN_ZHI.equals(cur_hj)&&RMHJ.TAN_HUA_AN_PAI.equals(dc005)){
			orderbyfield = " JS_SORT_dc005_2";
			ref_dc001 = "js_hj.JS_CLASS_DC001_2";
			//dc001_alias = "dc001_2";
		}
		/*a01.a0101,a01.a0192,m.js0111,m.js0117,m.js0109,m.js0100,a01.a0104a,"
				+ "a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,"
				+ "a01.fcsj,js02.js0206*/
		
		String sql = "select * from (select distinct js01.js0109,a01.a0000,js_hj.js0100,js01.js0108,js01.js0111,js01.js0117,a01.a0107,a01.zgxl,a01.a0196,"
				+ " nvl((select dc003 from deploy_classify t where t.dc001="+ref_dc001+"),'其他') dc003,"
				+ " (select dc006 from deploy_classify t where t.dc001="+ref_dc001+") dc006,"
				+ " a0101,(select dc004 from deploy_classify t where t.dc001="+ref_dc001+") dc004,"+orderbyfield
				+ " ,a01.a0141,a01.a0104,js01.js0122,a01.a0192,a01.a0104a,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,a01.fcsj "
				+ " from a01,js01,js_hj,js02 where js01.js0122 = '1' and js01.js0100 = js02.js0100(+) and "
				+ " a01.a0000=js01.a0000 and nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and js01.rb_id='"+rbId+"'  "+hj4sql +js0100WhereSql 
				+" union select distinct js01.js0109,a01.a0000,js_hj.js0100,js01.js0108,js01.js0111,js01.js0117,a01.a0107,a01.zgxl,a01.a0196,"
				+ " nvl((select dc003 from deploy_classify t where t.dc001="+ref_dc001+"),'其他') dc003,"
				+ " (select dc006 from deploy_classify t where t.dc001="+ref_dc001+") dc006,"
				+ " a0101,(select dc004 from deploy_classify t where t.dc001="+ref_dc001+") dc004,"+orderbyfield
				+ " ,a01.a0141,a01.a0104,js01.js0122,a01.a0192,a01.a0104a,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,a01.fcsj "
				+ " from v_js_a01 a01,js01,js_hj,js02 where js01.js0100 = js02.js0100(+) and a01.a0000=js01.a0000 and "
				+ " js01.js0122 = a01.v_xt and js0122 in ('2','3','4') and "
				+ " nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and js01.rb_id='"+rbId+"'  "+hj4sql +js0100WhereSql +""
				+ ")order by dc004,"+orderbyfield;
		return sql;
		//distinct a01.a0101, js01.js0108,js01.js0111,"+orderbyfield
	}
	
	public String getSheetPersons(String rbId,String dc005,String cur_hj,String cur_hj_4,int returnnum,String js0100WhereSql) throws RadowException{
		
		//ExcelStyleUtil.js0100WhereSql = "";
		String sql = getSql_1(cur_hj, cur_hj_4, rbId,dc005,"");
		if(!StringUtil.isEmpty(js0100WhereSql)){
			sql = "select m.a0101,m.a0192,m.js0111,m.js0117,m.js0109,m.js0100,m.a0104a,"
					+ "m.a0107,m.qrzxl,m.a0111a,m.a0134,m.a0140,m.zcsj,"
					+ "m.fcsj,js02.js0206 from ("+sql+") m,js02 where m.js0100 = js02.js0100 and js02.js0100 in('"+js0100WhereSql+"')";
		}else{
			sql = "select m.a0101,m.a0192,m.js0111,m.js0117,m.js0109,m.js0100,m.a0104a,"
					+ "m.a0107,m.qrzxl,m.a0111a,m.a0134,m.a0140,m.zcsj,"
					+ "m.fcsj,js02.js0206 from ("+sql+") m,js02 where m.js0100 = js02.js0100";
		}
		
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		String personsStr="";
		int num=0;
		String personOne="";
		try {
			stmt = session.connection().createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				num=num+1;
				if(num==1){
					personsStr=rs.getString("a0101");
					personOne=rs.getString("a0101");
				}else if(num==2){
					personsStr=personsStr+"、"+rs.getString("a0101");	
				}
			}
			
			if(num==1){
				//返回一个人的格式进行区分
				if(returnnum==1){
					return personsStr+"同志";
				}else{
					return personsStr+"同志（名单附后）";
				}
			}else if(num==0){
				return "0名同志";
			}else{
				//returnnum=1表示返回”XX等几名同志“格式
				if(returnnum==1){
					return personOne+"等"+num+"名同志（名单附后）";
				}else{
					//returnnum=1表示返回2个人的格式格式
					return personsStr+"等"+num+"名同志（名单附后）";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	public boolean impHistoryExcel(List<ExcelA57Pojo> list){
		FileOutputStream fileOut = null;   
	    BufferedImage bufferImg = null;   
	    //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray  
	    try { 
	       
	      HSSFWorkbook wb = new HSSFWorkbook();   
	      HSSFSheet sheet1 = wb.createSheet("test picture");  
	      //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点） 
	      HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();   
	      
	      //初始值    short col1, int row1, short col2, int row2
    	  int col1=1;
    	  int row1=2;
    	  int col2=3;
    	  int row2=8;
    	  
    	  //循环生成4个位置参数
    	  //1 2 3 8
    	  //4 2 6 8
    	  //7 2 9 8
    	  
    	  int namerow=1;
    	  int namecol=1;
    	  Row row = sheet1.createRow(namerow);
    	  
    	  int i=0;
    	  for(ExcelA57Pojo pojo:list){
    		//换行
	    	  if(i%10==0 && i!=0){
	    		  col1=1;
		    	  row1=row1+8;
		    	  col2=3;
		    	  row2=row2+8;
		    	  namerow=namerow+8;
		    	  namecol=1;
		    	  row = sheet1.createRow(namerow);
	    	  }
	    	  Cell cell = row.createCell(namecol);
		      cell.setCellValue(pojo.getA0101()); 
		      Cell cell1 = row.createCell(namecol+1);
	 		  HSSFCellStyle cellStyle2 = wb.createCellStyle();
	 		  HSSFDataFormat format = wb.createDataFormat();
	 		  cellStyle2.setDataFormat(format.getFormat("@"));
	 		  cell1.setCellStyle(cellStyle2);
		      cell1.setCellValue(pojo.getA0184());  
	    	  
	    	  //anchor主要用于设置图片的属性 
		      HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 100, 100,(short) col1, row1, (short) col2, row2);   
		      anchor.setAnchorType(3);   
		      //插入图片 
		      String photo=AppConfig.PHOTO_PATH+File.separator+pojo.getPHOTOPATH()+pojo.getPHOTONAME();
			  //System.out.println(photo);
			  File photofile=new File(photo);
			  if(photofile.exists()){
				  //photofile=new File("C:/Users/15084/Desktop/keji.jpeg");
				  try{
					  ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();   
				      bufferImg = ImageIO.read(photofile);
				      ImageIO.write(bufferImg, "jpg", byteArrayOut);
				      patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
				  }catch(Exception e){
					  //continue;
				  }
			  }
		        
		      fileOut = new FileOutputStream("C:/Users/Administrator/Desktop/未上传历史照片Excel.xls");  
		      //更新值
		      col1=col1+5;
	    	  //row1=2;
	    	  col2=col2+5;
	    	  //row2=8;
	    	  namecol=namecol+5;
	    	  
	    	  i++;
    	  }
    	  
	    /*  //anchor主要用于设置图片的属性 
	      HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 100, 100,(short) 1, 1, (short) 3, 6);   
	      anchor.setAnchorType(3);   
	      //插入图片  
	      patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));  
	      fileOut = new FileOutputStream("C:/Users/15084/Desktop/测试2Excel.xls");   */
	      
	      // 写入excel文件   
	      wb.write(fileOut);   
	      System.out.println("----Excle文件已生成------"); 
	      return true;
	    } catch (Exception e) { 
	      e.printStackTrace(); 
	      return false;
	    }finally{ 
	      if(fileOut != null){ 
	         try { 
	          fileOut.close(); 
	        } catch (IOException e) { 
	          e.printStackTrace(); 
	        } 
	      } 
	    }
	}
	
	/**
	 * 导出带人员照片的excel
	 */
	public static void main(String[] args) {
		
	    FileOutputStream fileOut = null;   
	    BufferedImage bufferImg = null;   
	    //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray  
	    try { 
	      ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();   
	      bufferImg = ImageIO.read(new File("C:/Users/15084/Desktop/keji.jpeg"));   
	      ImageIO.write(bufferImg, "jpg", byteArrayOut); 
	       
	      HSSFWorkbook wb = new HSSFWorkbook();   
	      HSSFSheet sheet1 = wb.createSheet("test picture");  
	      //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点） 
	      HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();   
	      
	      //初始值    short col1, int row1, short col2, int row2
    	  int col1=1;
    	  int row1=2;
    	  int col2=3;
    	  int row2=8;
    	  
    	  //循环生成4个位置参数
    	  //1 2 3 8
    	  //4 2 6 8
    	  //7 2 9 8
    	  
    	  int namerow=1;
    	  int namecol=1;
    	  Row row = sheet1.createRow(namerow);
    	  
	      for(int i=0;i<40;i++){
	    	  //换行
	    	  if(i%10==0 && i!=0){
	    		  col1=1;
		    	  row1=row1+8;
		    	  col2=3;
		    	  row2=row2+8;
		    	  namerow=namerow+8;
		    	  namecol=1;
		    	  row = sheet1.createRow(namerow);
	    	  }
	    	  Cell cell = row.createCell(namecol);
		      cell.setCellValue("名字"); 
		      Cell cell1 = row.createCell(namecol+1);
	 		  HSSFCellStyle cellStyle2 = wb.createCellStyle();
	 		  HSSFDataFormat format = wb.createDataFormat();
	 		  cellStyle2.setDataFormat(format.getFormat("@"));
	 		  cell1.setCellStyle(cellStyle2);
		      cell1.setCellValue("220881199110254110");  
	    	  
	    	  //anchor主要用于设置图片的属性 
		      HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 100, 100,(short) col1, row1, (short) col2, row2);   
		      anchor.setAnchorType(3);   
		      //插入图片  
		      patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));  
		      fileOut = new FileOutputStream("C:/Users/15084/Desktop/测试3Excel.xls");  
		      //更新值
		      col1=col1+3;
	    	  //row1=2;
	    	  col2=col2+3;
	    	  //row2=8;
	    	  namecol=namecol+3;
	    	  
	      }
	    /*  //anchor主要用于设置图片的属性 
	      HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 100, 100,(short) 1, 1, (short) 3, 6);   
	      anchor.setAnchorType(3);   
	      //插入图片  
	      patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));  
	      fileOut = new FileOutputStream("C:/Users/15084/Desktop/测试2Excel.xls");   */
	      
	      // 写入excel文件   
	      wb.write(fileOut);   
	      System.out.println("----Excle文件已生成------"); 
	    } catch (Exception e) { 
	      e.printStackTrace(); 
	    }finally{ 
	      if(fileOut != null){ 
	         try { 
	          fileOut.close(); 
	        } catch (IOException e) { 
	          e.printStackTrace(); 
	        } 
	      } 
	    } 
	} 
	
	/**
	 * 领导干部个人有关事项报告抽查核实对象基本信息表
	 */
	public void exportJBXXB(List<List<Map<String,String>>> datalistAll)  throws RadowException,Exception{
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		sheet.getPrintSetup().setLandscape(true);
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		//sheet.setDisplayGridlines(false);
		Row row = sheet.createRow(0);
		row.setHeightInPoints((short) 57);
		sheet.setColumnWidth(0, 6 * 256+184);
		sheet.setColumnWidth(1, 15 * 256+184);
		sheet.setColumnWidth(2, 14 * 256+184);
		sheet.setColumnWidth(3, 7 * 256+184);
		sheet.setColumnWidth(4, 25 * 256+184);
		sheet.setColumnWidth(5, 23 * 256+184);
		sheet.setColumnWidth(6, 18 * 256+184);
		sheet.setColumnWidth(7, 15 * 256+184);
		
		//第一行
		Cell cell = row.createCell(0);
		// 生成一个样式
 		CellStyle style = workbook.createCellStyle();
 		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
 		style.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	cell.setCellStyle(style);
     	// 生成一个字体
 		Font font = workbook.createFont();
 		font.setFontHeightInPoints((short) 22);
 		font.setFontName("Times New Roman");
 		style.setFont(font);
        cell.setCellValue("领导干部个人有关事项报告抽查核实对象基本信息表");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
     	
        //第二行
        row = sheet.createRow(1);
        row.setHeightInPoints((short) 30);
        cell = row.createCell(0);
        CellStyle style2 = workbook.createCellStyle();
     	Font font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 14);
		font2.setFontName("Times New Roman");
		style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style2.setFont(font2);
		cell.setCellStyle(style2);
        cell.setCellValue("单位（盖章）： 无锡市委组织部");
        cell = row.createCell(6);
        cell.setCellStyle(style2);
        cell.setCellValue("填写日期："+com.insigma.siis.local.business.helperUtil.DateUtil.timeToString(com.insigma.siis.local.business.helperUtil.DateUtil.getTimestamp(), "yyyy年MM月dd日"));
        
        //第三行
        row = sheet.createRow(2);
        CellStyle style3 = workbook.createCellStyle();
     	Font font3 = workbook.createFont();
     	font3.setFontHeightInPoints((short) 14);
     	font3.setFontName("Times New Roman");
     	style3.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	style3.setFont(font3);
     	
        row.setHeightInPoints((short) 26); 
        String[] headers = { "序号", "对    象", "姓名", "性别", "现任职务","身份证号","房产查询地","填报日期"};
        List<String> list  = Arrays.asList(headers);
        for(int i=0;i<list.size();i++){
        	cell = row.createCell(i);
        	cell.setCellStyle(style3);
        	cell.setCellValue(list.get(i));
        }
        
        //第四行
        CellStyle style4 = workbook.createCellStyle();
     	Font font4 = workbook.createFont();
     	font4.setFontHeightInPoints((short) 14);
     	font4.setFontName("Times New Roman");
     	style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
     	style4.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	style4.setFont(font4);
        
        int num=2;
        for(int i=0;i<datalistAll.size();i++){
        	List<Map<String,String>> subList=datalistAll.get(i);
        	int startrow=0;
        	int endrow=0;
        	for(int j=0;j<subList.size();j++){
        		Map<String,String> map=subList.get(j);
        		String target=map.get("target");
        		String name=map.get("name");
        		String sex=map.get("sex");
        		String zw=map.get("zw");
        		String idcard=map.get("idcard");
        		String house=map.get("house");
        		String date=map.get("date");
        		
        		row = sheet.createRow(num++);//第几行
        		if(j==0){
        			startrow=num;
            		cell = row.createCell(0);
            		cell.setCellStyle(style4);
            		cell.setCellValue(i+1);
        		}
        		cell = row.createCell(1);
        		cell.setCellStyle(style4);
        		cell.setCellValue(target);
        		
        		cell = row.createCell(2);
        		cell.setCellStyle(style4);
        		cell.setCellValue(name);
        		
        		cell = row.createCell(3);
        		cell.setCellStyle(style4);
        		cell.setCellValue(sex);
        		
        		cell = row.createCell(4);
        		cell.setCellStyle(style4);
        		cell.setCellValue(zw);
        		
        		cell = row.createCell(5);
        		cell.setCellStyle(style4);
        		cell.setCellValue(idcard);
        		
        		cell = row.createCell(6);
        		cell.setCellStyle(style4);
        		cell.setCellValue(house);
        		
        		cell = row.createCell(7);
        		cell.setCellStyle(style4);
        		cell.setCellValue(date);
        	}
        	if(startrow!=0){
        		sheet.addMergedRegion(new CellRangeAddress(startrow-1,startrow+subList.size()-2 , 0, 0));
        		System.out.println((startrow-1)+"----"+(startrow+subList.size()-2));
        	}
        }
        
        
        List<List<Map<String,String>>> datelist1=new ArrayList<List<Map<String,String>>>();
        
        
     	File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "查询对象基本信息表（报省查核用）.xls";
		if(this.outputFileNameList.size()==0){
			this.outputFileNameList.add(fn);
		}
		
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
     	
	}
	
	
	/**
	 * 干部动议建议表
	 * @throws RadowException 
	 * @throws Exception 
	 */
	public void exportGBDYJY(String type) throws RadowException, Exception {
		  //声明一个工作簿  
		Workbook workbook = new HSSFWorkbook();  
		//生成一个表格  
		Sheet sheet = workbook.createSheet(); 
		//设置横向打印 
		sheet.getPrintSetup().setLandscape(true);
		//设置A4打印
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		// 隐藏网格线
		sheet.setDisplayGridlines(false);
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String cur_hj = pm.request.getAttribute("cur_hj").toString();//环节
		String cur_hj_4 = pm.request.getAttribute("cur_hj_4").toString();//讨论决定分环节
		String dc005 = pm.request.getAttribute("dc005").toString();
		String js0100WhereSql = pm.request.getAttribute("js0100WhereSql").toString();
		String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005,js0100WhereSql);
		sql = "select * from js02 WHERE js02.js0100 in (select js0100 from ("+sql+"))";
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		// 产生表格标题行
        Row row = sheet.createRow(0);
        // 设置创建单元格高度
		row.setHeightInPoints((short) 49);
		// 设置指定列宽度
		sheet.setColumnWidth(0, 3 * 256);
		sheet.setColumnWidth(1, 17 * 256);
		sheet.setColumnWidth(2, 29 * 256);
		sheet.setColumnWidth(3, 23 * 256);
		sheet.setColumnWidth(4, 42 * 256);
		sheet.setColumnWidth(5, 10 * 256);
		// 生成一个样式
		CellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		// 生成一个字体
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 20);
		font.setFontName("宋体");
		style.setFont(font);
        Cell cell = row.createCell(0);
        if(type.endsWith("干部")){
        	cell.setCellValue(type+"工作动议表");
        }else{
        	cell.setCellValue(type+"干部工作动议表");        	
        }
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
     	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
     	cell.setCellStyle(style);
     	
     	CellStyle style2 = workbook.createCellStyle();
     	style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
     	style2.setAlignment(CellStyle.ALIGN_LEFT);
        // 生成一个字体
     	Font font2 = workbook.createFont();
     	font2.setFontHeightInPoints((short) 12);
     	font2.setFontName("黑体");
     	style2.setFont(font2);
        row = sheet.createRow(1);
        row.setHeightInPoints((short) 30);
        cell = row.createCell(0);
        //if(type.equals("省级机关")||type.equals("市（县）区")||type.equals("国有企业")||type.equals("省管干部")){
        	cell.setCellValue("省委书记签字：_____________");
		//}else{
			//cell.setCellValue("省委组织部部长签字：_____________");
		//}
        
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
        cell.setCellStyle(style2);
        
        CellStyle style3 = workbook.createCellStyle();
     	style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
     	style3.setAlignment(CellStyle.ALIGN_RIGHT);
     	style3.setFont(font2);
        cell = row.createCell(4);
        Calendar cal = Calendar.getInstance();
        int month =cal.get(Calendar.MONTH)  + 1;
        cell.setCellValue(cal.get(Calendar.YEAR)+" 年 "+month+" 月 "+cal.get(Calendar.DATE)+" 日 ");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));
        cell.setCellStyle(style3);
        
        row = sheet.createRow(2);
        row.setHeightInPoints((short) 36);
        CellStyle style4 = workbook.createCellStyle();
     	style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
     	style4.setAlignment(CellStyle.ALIGN_CENTER);
     	style4.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
     	style4.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
     	style4.setBorderRight(CellStyle.BORDER_THIN);//右边框       
     	style4.setBorderTop(CellStyle.BORDER_THIN);//上边框 
     	style4.setFont(font2);
     	style4.setWrapText(true);// 自动换行
        String[] headers = { "序号", "空缺职位", "岗位要求", "配备方向", "配备建议","备注" };
        List<String> list  = Arrays.asList(headers);
        for(int i=0;i<list.size();i++){
        	cell = row.createCell(i);
        	cell.setCellValue(list.get(i));
            cell.setCellStyle(style4);
        }
        CellStyle style5 = workbook.createCellStyle();
        style5.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style5.setAlignment(CellStyle.ALIGN_LEFT);
        style5.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
        style5.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
        style5.setBorderRight(CellStyle.BORDER_THIN);//右边框       
        style5.setBorderTop(CellStyle.BORDER_THIN);//上边框 
        style5.setWrapText(true);// 自动换行
     	 // 生成一个字体
     	Font font3 = workbook.createFont();
     	font3.setFontHeightInPoints((short) 12);
     	font3.setFontName("仿宋_GB2312");
     	style5.setFont(font3);
     	
     	Font font4 = workbook.createFont();
     	font4.setFontHeightInPoints((short) 12);
     	font4.setFontName("仿宋_GB2312");
     	font4.setBoldweight(Font.BOLDWEIGHT_BOLD); //加粗
        int i=2;
        int rowNum = 1;
        while(rs.next()){
        	i++;
        	row = sheet.createRow(i);
        	row.setHeightInPoints((short) 141);
        	cell = row.createCell(0);
        	cell.setCellValue(rowNum);//输入序号
        	cell.setCellStyle(style5);
        	cell = row.createCell(1);
        	cell.setCellValue(rs.getString("js0202"));//输入空缺职位
        	cell.setCellStyle(style5);
        	cell = row.createCell(2);
        	cell.setCellValue(rs.getString("js0203"));//输入岗位要求
        	cell.setCellStyle(style5);
        	cell = row.createCell(3);
        	cell.setCellValue(rs.getString("js0204"));//输入配备方向
        	cell.setCellStyle(style5);
        	cell = row.createCell(4);
        	//在同一单元格将名字加粗
        	String name = Arrays.asList(rs.getString("js0205").split("\\(")).get(0);
        	HSSFRichTextString richString = new HSSFRichTextString(rs.getString("js0205"));
        	richString.applyFont(0, name.length(), font4);
        	richString.applyFont(name.length(), rs.getString("js0205").length(), font3);
        	cell.setCellValue(richString);//输入配备建议
        	cell.setCellStyle(style5);
        	cell = row.createCell(5);
        	cell.setCellValue(rs.getString("js0206"));//输入备注
        	cell.setCellStyle(style5);
        	rowNum++;
        }
        
        CellStyle style6 = workbook.createCellStyle();
		style6.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);//垂直居底
		style6.setAlignment(CellStyle.ALIGN_LEFT);//水平居左
		style6.setFont(font2);
        row = sheet.createRow(i+1);
        row.setHeightInPoints((short) 64);
        cell = row.createCell(0);
        //if(type.equals("省级机关")||type.equals("市（县）区")||type.equals("国有企业")||type.equals("省管干部")){
        	cell.setCellValue("省委组织部分管副部长签字：_____________");
		//}else{
			//cell.setCellValue("分管处室负责人签字：_____________");
		//}
        sheet.addMergedRegion(new CellRangeAddress(i+1, i+1, 0, 3));
        cell.setCellStyle(style6);
        
        cell = row.createCell(4);
        //if(type.equals("省级机关")||type.equals("市（县）区")||type.equals("国有企业")||type.equals("省管干部")){
        	cell.setCellValue("省委组织部部长签字：_____________");
		//}else{
			//cell.setCellValue("分管副部长签字：_____________");
		//}
        sheet.addMergedRegion(new CellRangeAddress(i+1, i+1, 4, 5));
        cell.setCellStyle(style6);
        
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "干部工作动议表.xls";
		if(this.outputFileNameList.size()==0){
			this.outputFileNameList.add(fn);
		}
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();


	}
			
	/**
	 * 谈话安排建议方案
	 * @throws RadowException
	 * @throws Exception
	 */
	public void exportGBTHAN() throws RadowException,Exception{
		//声明一个工作簿  
		Workbook workbook = new HSSFWorkbook();  
		//生成一个表格  
		Sheet sheet = workbook.createSheet(); 
		//设置横向打印 
		sheet.getPrintSetup().setLandscape(true);
		//设置A4打印
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		// 隐藏网格线
		sheet.setDisplayGridlines(false);
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String cur_hj = pm.request.getAttribute("cur_hj").toString();//环节
		String cur_hj_4 = pm.request.getAttribute("cur_hj_4").toString();//讨论决定分环节
		String dc005 = pm.request.getAttribute("dc005").toString();
		String js0100WhereSql = pm.request.getAttribute("js0100WhereSql").toString();
		/*String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005);
		sql = "select a01.a0101,a01.a0192,m.js0111,m.js0117,m.js0109,m.js0100,a01.a0104a,"
				+ "a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,"
				+ "a01.fcsj,js02.js0206 from a01,("+sql+") m,js02 where a01.a0000 = m.a0000 and m.js0100 = js02.js0100";*/
		String sql = getSql_1(cur_hj, cur_hj_4, rbId,dc005, js0100WhereSql);
		sql="select m.a0101,m.a0192,m.js0111,m.js0117,m.js0109,m.js0100,m.a0104a,"
			+ " m.a0107,m.qrzxl,m.a0111a,m.a0134,m.a0140,m.zcsj,"
			+ " m.fcsj,js02.js0206 "
			+ " from ("+sql+") m,js02  where m.js0100 = js02.js0100";;
		
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		// 产生表格标题行
        Row row = sheet.createRow(0);
        // 设置创建单元格高度
		row.setHeightInPoints((short) 57);
		// 设置指定列宽度
		sheet.setColumnWidth(0, 7 * 256+184);
		sheet.setColumnWidth(1, 25 * 256+184);
		sheet.setColumnWidth(2, 25 * 256+184);
		sheet.setColumnWidth(3, 4 * 256+184);
		sheet.setColumnWidth(4, 7 * 256+184);
		sheet.setColumnWidth(5, 9 * 256+184);
		sheet.setColumnWidth(6, 6 * 256+184);
		sheet.setColumnWidth(7, 6 * 256+184);
		sheet.setColumnWidth(8, 6 * 256+184);
		sheet.setColumnWidth(9, 10 * 256+184);
		sheet.setColumnWidth(10, 6 * 256+184);
		sheet.setColumnWidth(11, 12 * 256+184);
		
		// 生成一个样式
		CellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		// 生成一个字体
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 22);
		font.setFontName("Times New Roman");
		style.setFont(font);
        Cell cell = row.createCell(0);
        cell.setCellValue("需  谈  话  人  员  名  单");
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
     	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
     	cell.setCellStyle(style);
     	
     	CellStyle style5 = workbook.createCellStyle();
		style5.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style5.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	Font font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 12);
		font2.setFontName("Times New Roman");
		style5.setFont(font2);
		row = sheet.createRow(1);
		//  主谈人： 
		cell = row.createCell(0);
		cell.setCellValue("  主谈人：");
		
		CellStyle style9 = workbook.createCellStyle();
     	Font font9 = workbook.createFont();
     	font9.setFontHeightInPoints((short) 12);
     	font9.setFontName("Times New Roman");
     	style9.setFont(font9);
		
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
		cell.setCellStyle(style9);
		
		cell = row.createCell(10);
		Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        cell.setCellValue(cal.get(Calendar.YEAR)+" 年 "+month+" 月");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 10, 11));
     	cell.setCellStyle(style5);
     	
     	row = sheet.createRow(2);
        row.setHeightInPoints((float) 42.75);
        CellStyle style2 = workbook.createCellStyle();
     	style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
     	style2.setAlignment(CellStyle.ALIGN_CENTER);
     	style2.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
     	style2.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
     	style2.setBorderRight(CellStyle.BORDER_THIN);//右边框       
     	style2.setBorderTop(CellStyle.BORDER_THIN);//上边框 
     	Font font3 = workbook.createFont();
     	font3.setFontHeightInPoints((short) 14);
     	font3.setFontName("黑体");
     	style2.setFont(font3);
     	style2.setWrapText(true);// 自动换行
     	
     	CellStyle style6 = workbook.createCellStyle();
     	style6.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
     	style6.setAlignment(CellStyle.ALIGN_CENTER);
     	style6.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
     	style6.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
     	style6.setBorderRight(CellStyle.BORDER_THIN);//右边框       
     	style6.setBorderTop(CellStyle.BORDER_THIN);//上边框 
     	Font font6 = workbook.createFont();
     	font6.setFontHeightInPoints((short) 11);
     	font6.setFontName("黑体");
     	style6.setFont(font6);
     	style6.setWrapText(true);// 自动换行
        String[] headers = { "姓名", "职务", "拟任免职务", "性别", "出生年月","全日制学历","籍贯","工作年月","入党时间","副处职（正处）时间","现职时间","备注"};
        List<String> list  = Arrays.asList(headers);
        for(int i=0;i<list.size();i++){
        	cell = row.createCell(i);
        	cell.setCellValue(list.get(i));
        	if(list.get(i).equals("副处职（正处）时间")){
        		cell.setCellStyle(style6);
        	}else{
        		cell.setCellStyle(style2);
        	}
        }
        CellStyle style3 = workbook.createCellStyle();
        style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style3.setAlignment(CellStyle.ALIGN_LEFT);
        style3.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
        style3.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
        style3.setBorderRight(CellStyle.BORDER_THIN);//右边框       
        style3.setBorderTop(CellStyle.BORDER_THIN);//上边框 
        style3.setWrapText(true);// 自动换行
     	 // 生成一个字体
     	Font font4 = workbook.createFont();
     	font4.setFontHeightInPoints((short) 12);
     	font4.setFontName("仿宋");
     	style3.setFont(font4);
     	
     	CellStyle style7 = workbook.createCellStyle();
     	style7.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
     	style7.setAlignment(CellStyle.ALIGN_CENTER);
     	style7.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
     	style7.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
     	style7.setBorderRight(CellStyle.BORDER_THIN);//右边框       
     	style7.setBorderTop(CellStyle.BORDER_THIN);//上边框 
     	style7.setWrapText(true);// 自动换行
     	style7.setFont(font4);
     	
     	CellStyle style4 = workbook.createCellStyle();
     	style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
     	style4.setAlignment(CellStyle.ALIGN_LEFT);
     	style4.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
     	style4.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
     	style4.setBorderRight(CellStyle.BORDER_THIN);//右边框       
     	style4.setBorderTop(CellStyle.BORDER_THIN);//上边框 
     	style4.setWrapText(true);// 自动换行
     	 // 生成一个字体
     	Font font5 = workbook.createFont();
     	font5.setFontHeightInPoints((short) 10);
     	font5.setFontName("仿宋");
     	style4.setFont(font5);
        int i=2;
        while(rs.next()){
        	i++;
        	//字数大于39个字不设置行高
        	ExcelReturnParam setRowHeight = new ExcelReturnParam(true,39);
        	row = sheet.createRow(i);
        	cell = row.createCell(0);
        	cell.setCellValue(rs.getString("a0101"));//姓名
        	cell.setCellStyle(style7);
        	
        	cell = row.createCell(1);
        	String value = rs.getString("a0192");//职务
        	ExcelStyleUtil.setCellValue(cell, style3, value,setRowHeight);
        	
        	cell = row.createCell(2);
        	//value = rs.getString("js0111")==null?"":"任"+rs.getString("js0111")+","+"免"+rs.getString("js0117");//拟任免职务
        	String value2 = rs.getString("js0111");
        	String value3 = rs.getString("js0117");
        	
        	if(value2!=null && !value2.trim().equals("") && !value2.equals("null")) {
        		value = "任"+value2;//拟任免职务
        		if(value3!=null && !value3.equals("") && !value3.equals("null")) {
        			value = value +"，"+ "免"+value3;//拟任免职务
        		}
        	} else {
        		if(value3!=null && !value3.trim().equals("") && !value3.equals("null")) {
        			value = "免"+value3;//拟任免职务
        		}
        	}
        	
        	ExcelStyleUtil.setCellValue(cell, style3, value,setRowHeight);
        	
        	cell = row.createCell(3);
        	value = rs.getString("a0104a");//性别
        	cell.setCellValue(value);
        	cell.setCellStyle(style7);
        	
        	cell = row.createCell(4);
        	value = rs.getString("a0107")==null?"":rs.getString("a0107").substring(2, 4)+"."+rs.getString("a0107").substring(4, 6);//出生年月
        	cell.setCellValue(value);
        	cell.setCellStyle(style7);
        	
        	cell = row.createCell(5);
        	value = rs.getString("qrzxl");//全日制学历
        	ExcelStyleUtil.setCellValue(cell, style7, value,setRowHeight.setRowHeightLength(12));
        	
        	cell = row.createCell(6);
        	value = rs.getString("a0111a");//籍贯
        	ExcelStyleUtil.setCellValue(cell, style7, value,setRowHeight.setRowHeightLength(6));
        	
        	cell = row.createCell(7);
        	value = rs.getString("a0134")==null?"":rs.getString("a0134").substring(2, 4)+"."+rs.getString("a0134").substring(4, 6);//工作年月
        	cell.setCellValue(value);
        	cell.setCellStyle(style7);
        	
        	cell = row.createCell(8);
        	value = rs.getString("a0140");//入党时间
        	ExcelStyleUtil.setCellValue(cell, style7, value,setRowHeight.setRowHeightLength(6));
        	
        	cell = row.createCell(9);
        	if(!StringUtil.isEmpty(rs.getString("fcsj"))){
        		value = rs.getString("fcsj").substring(2, 4)+"."+rs.getString("fcsj").substring(4, 6);//副处职（正处）时间
        	}else if(!StringUtil.isEmpty(rs.getString("zcsj"))){
        		value = rs.getString("zcsj").substring(2, 4)+"."+rs.getString("zcsj").substring(4, 6);//副处职（正处）时间
        	}else{
        		value = "";
        	}
        	cell.setCellValue(value);
        	cell.setCellStyle(style7);
        	
        	cell = row.createCell(10);
        	value = rs.getString("js0109")==null?"":rs.getString("js0109").substring(2, 4)+"."+rs.getString("a0134").substring(4, 6);//现职时间
        	cell.setCellValue(value);
        	cell.setCellStyle(style7);
        	
        	cell = row.createCell(11);
        	value = rs.getString("js0206");//备注
        	ExcelStyleUtil.setCellValue(cell, style4, value,setRowHeight.setRowHeightLength(18));
        	
        	if(setRowHeight.isSetRowHeight()){
				row.setHeightInPoints((short)43);//行高
			}
        	
        }
        
        File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "谈话安排建议方案.xls";
		if(this.outputFileNameList.size()==0){
			this.outputFileNameList.add(fn);
		}
		
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
	}
	
	/**
	 * 动议干部名单
	 */
	public void exportDYMD() throws RadowException,Exception {
		 //声明一个工作簿  
		Workbook workbook = new HSSFWorkbook();  
		//生成一个表格  
		Sheet sheet = workbook.createSheet(); 
		//设置横向打印 
		sheet.getPrintSetup().setLandscape(true);
		//设置A4打印
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		// 隐藏网格线
		sheet.setDisplayGridlines(false);
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String cur_hj = pm.request.getAttribute("cur_hj").toString();//环节
		String cur_hj_4 = pm.request.getAttribute("cur_hj_4").toString();//讨论决定分环节
		String dc005 = pm.request.getAttribute("dc005").toString();
		String js0100WhereSql = pm.request.getAttribute("js0100WhereSql").toString();
		String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005, js0100WhereSql);
		sql = "select a01.a0101,a01.a0192,m.js0111,m.js0117,m.js0109,m.js0100,a01.a0104a,"
				+ "a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,"
				+ "a01.fcsj,js02.js0206 from a01,("+sql+") m,js02 where a01.a0000 = m.a0000 and m.js0100 = js02.js0100";
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		// 产生表格标题行
        Row row = sheet.createRow(0);
        // 设置创建单元格高度
		row.setHeightInPoints((short) 57);
		// 设置指定列宽度
		sheet.setColumnWidth(0, 7 * 256+184);
		sheet.setColumnWidth(1, 25 * 256+184);
		sheet.setColumnWidth(2, 25 * 256+184);
		sheet.setColumnWidth(3, 4 * 256+184);
		sheet.setColumnWidth(4, 7 * 256+184);
		sheet.setColumnWidth(5, 9 * 256+184);
		sheet.setColumnWidth(6, 6 * 256+184);
		sheet.setColumnWidth(7, 6 * 256+184);
		sheet.setColumnWidth(8, 6 * 256+184);
		sheet.setColumnWidth(9, 10 * 256+184);
		sheet.setColumnWidth(10, 6 * 256+184);
		sheet.setColumnWidth(11, 12 * 256+184);
		
		// 生成一个样式
		CellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		// 生成一个字体
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 22);
		font.setFontName("Times New Roman");
		style.setFont(font);
        Cell cell = row.createCell(0);
        cell.setCellValue("动  议  干  部  名  单");
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
     	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
     	cell.setCellStyle(style);
     	
     	CellStyle style5 = workbook.createCellStyle();
		style5.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style5.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	Font font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 12);
		font2.setFontName("Times New Roman");
		style5.setFont(font2);
		row = sheet.createRow(1);
		cell = row.createCell(10);
		Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        cell.setCellValue(cal.get(Calendar.YEAR)+" 年 "+month+" 月");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 10, 11));
     	cell.setCellStyle(style5);
     	
     	row = sheet.createRow(2);
        row.setHeightInPoints((float) 42.75);
        CellStyle style2 = workbook.createCellStyle();
     	style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
     	style2.setAlignment(CellStyle.ALIGN_CENTER);
     	style2.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
     	style2.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
     	style2.setBorderRight(CellStyle.BORDER_THIN);//右边框       
     	style2.setBorderTop(CellStyle.BORDER_THIN);//上边框 
     	Font font3 = workbook.createFont();
     	font3.setFontHeightInPoints((short) 14);
     	font3.setFontName("黑体");
     	style2.setFont(font3);
     	style2.setWrapText(true);// 自动换行
     	
     	CellStyle style6 = workbook.createCellStyle();
     	style6.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
     	style6.setAlignment(CellStyle.ALIGN_CENTER);
     	style6.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
     	style6.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
     	style6.setBorderRight(CellStyle.BORDER_THIN);//右边框       
     	style6.setBorderTop(CellStyle.BORDER_THIN);//上边框 
     	Font font6 = workbook.createFont();
     	font6.setFontHeightInPoints((short) 11);
     	font6.setFontName("黑体");
     	style6.setFont(font6);
     	style6.setWrapText(true);// 自动换行
        String[] headers = { "姓名", "职务", "拟任免职务", "性别", "出生年月","全日制学历","籍贯","工作年月","入党时间","副处职（正处）时间","现职时间","备注"};
        List<String> list  = Arrays.asList(headers);
        for(int i=0;i<list.size();i++){
        	cell = row.createCell(i);
        	cell.setCellValue(list.get(i));
        	if(list.get(i).equals("副处职（正处）时间")){
        		cell.setCellStyle(style6);
        	}else{
        		cell.setCellStyle(style2);
        	}
        }
        CellStyle style3 = workbook.createCellStyle();
        style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style3.setAlignment(CellStyle.ALIGN_LEFT);
        style3.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
        style3.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
        style3.setBorderRight(CellStyle.BORDER_THIN);//右边框       
        style3.setBorderTop(CellStyle.BORDER_THIN);//上边框 
        style3.setWrapText(true);// 自动换行
     	 // 生成一个字体
     	Font font4 = workbook.createFont();
     	font4.setFontHeightInPoints((short) 12);
     	font4.setFontName("仿宋");
     	style3.setFont(font4);
     	
     	CellStyle style7 = workbook.createCellStyle();
     	style7.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
     	style7.setAlignment(CellStyle.ALIGN_CENTER);
     	style7.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
     	style7.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
     	style7.setBorderRight(CellStyle.BORDER_THIN);//右边框       
     	style7.setBorderTop(CellStyle.BORDER_THIN);//上边框 
     	style7.setWrapText(true);// 自动换行
     	style7.setFont(font4);
     	
     	CellStyle style4 = workbook.createCellStyle();
     	style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
     	style4.setAlignment(CellStyle.ALIGN_LEFT);
     	style4.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
     	style4.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
     	style4.setBorderRight(CellStyle.BORDER_THIN);//右边框       
     	style4.setBorderTop(CellStyle.BORDER_THIN);//上边框 
     	style4.setWrapText(true);// 自动换行
     	 // 生成一个字体
     	Font font5 = workbook.createFont();
     	font5.setFontHeightInPoints((short) 10);
     	font5.setFontName("仿宋");
     	style4.setFont(font5);
        int i=2;
        while(rs.next()){
        	i++;
        	//字数大于39个字不设置行高
        	ExcelReturnParam setRowHeight = new ExcelReturnParam(true,39);
        	row = sheet.createRow(i);
        	cell = row.createCell(0);
        	cell.setCellValue(rs.getString("a0101"));//姓名
        	cell.setCellStyle(style7);
        	
        	cell = row.createCell(1);
        	String value = rs.getString("a0192");//职务
        	ExcelStyleUtil.setCellValue(cell, style3, value,setRowHeight);
        	
        	cell = row.createCell(2);
        	value = rs.getString("js0111")==null?"":"任"+rs.getString("js0111")+","+"免"+rs.getString("js0117");//拟任免职务
        	ExcelStyleUtil.setCellValue(cell, style3, value,setRowHeight);
        	
        	cell = row.createCell(3);
        	value = rs.getString("a0104a");//性别
        	cell.setCellValue(value);
        	cell.setCellStyle(style7);
        	
        	cell = row.createCell(4);
        	value = rs.getString("a0107")==null?"":rs.getString("a0107").substring(2, 4)+"."+rs.getString("a0107").substring(4, 6);//出生年月
        	cell.setCellValue(value);
        	cell.setCellStyle(style7);
        	
        	cell = row.createCell(5);
        	value = rs.getString("qrzxl");//全日制学历
        	ExcelStyleUtil.setCellValue(cell, style7, value,setRowHeight.setRowHeightLength(12));
        	
        	cell = row.createCell(6);
        	value = rs.getString("a0111a");//籍贯
        	ExcelStyleUtil.setCellValue(cell, style7, value,setRowHeight.setRowHeightLength(6));
        	
        	cell = row.createCell(7);
        	value = rs.getString("a0134")==null?"":rs.getString("a0134").substring(2, 4)+"."+rs.getString("a0134").substring(4, 6);//工作年月
        	cell.setCellValue(value);
        	cell.setCellStyle(style7);
        	
        	cell = row.createCell(8);
        	value = rs.getString("a0140");//入党时间
        	ExcelStyleUtil.setCellValue(cell, style7, value,setRowHeight.setRowHeightLength(6));
        	
        	cell = row.createCell(9);
        	if(!StringUtil.isEmpty(rs.getString("fcsj"))){
        		value = rs.getString("fcsj").substring(2, 4)+"."+rs.getString("fcsj").substring(4, 6);//副处职（正处）时间
        	}else if(!StringUtil.isEmpty(rs.getString("zcsj"))){
        		value = rs.getString("zcsj").substring(2, 4)+"."+rs.getString("zcsj").substring(4, 6);//副处职（正处）时间
        	}else{
        		value = "";
        	}
        	cell.setCellValue(value);
        	cell.setCellStyle(style7);
        	
        	cell = row.createCell(10);
        	value = rs.getString("js0109")==null?"":rs.getString("js0109").substring(2, 4)+"."+rs.getString("a0134").substring(4, 6);//现职时间
        	cell.setCellValue(value);
        	cell.setCellStyle(style7);
        	
        	cell = row.createCell(11);
        	value = rs.getString("js0206");//备注
        	ExcelStyleUtil.setCellValue(cell, style4, value,setRowHeight.setRowHeightLength(18));
        	
        	if(setRowHeight.isSetRowHeight()){
				row.setHeightInPoints((short)43);//行高
			}
        	
        }
        
        File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "动议干部名单.xls";
		if(this.outputFileNameList.size()==0){
			this.outputFileNameList.add(fn);
		}
		
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();   
	}
	
	/**
	 * 职数预审
	 */
	public void exportZSYS() throws RadowException,Exception {
		//声明一个工作簿  
		Workbook workbook = new HSSFWorkbook();  
		//生成一个表格  
		Sheet sheet = workbook.createSheet(); 
		PrintSetup ps = sheet.getPrintSetup();

		//ps.setLandscape(true); // 打印方向，true：横向，false：纵向
		ps.setPaperSize(PrintSetup.A4_PAPERSIZE); //纸张
		sheet.setMargin(Sheet.BottomMargin,( double ) 0.6 );// 页边距（下）
		sheet.setMargin(Sheet.LeftMargin,( double ) 0.2 );// 页边距（左）
		sheet.setMargin(Sheet.RightMargin,( double ) 0.2 );// 页边距（右）
		sheet.setMargin(Sheet.TopMargin,( double ) 0.6 );// 页边距（上）
		ps.setHeaderMargin((double) 0.3); // 页眉
		ps.setFooterMargin((double) 0.3);//页脚

		sheet.setHorizontallyCenter(true);//设置打印页面为水平居中
		//sheet.setVerticallyCenter(true);;//设置打印页面为垂直居中


		// 隐藏网格线
		sheet.setDisplayGridlines(false);
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String sql = "select j.*,b.b0101 from js12 j, b01 b where j.js1201=b.b0111 and rb_id = '"+rbId+"'";
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		// 产生表格标题行
        Row row = sheet.createRow(0);
        // 设置创建单元格高度
		row.setHeightInPoints((short) 35);
		// 设置指定列宽度
		sheet.setColumnWidth(0, 5 * 256+184);
		sheet.setColumnWidth(1, 13 * 256+184);
		sheet.setColumnWidth(2, 5 * 256+184);
		sheet.setColumnWidth(3, 5 * 256+184);
		sheet.setColumnWidth(4, 5 * 256+184);
		sheet.setColumnWidth(5, 5 * 256+184);
		sheet.setColumnWidth(6, 5 * 256+184);
		sheet.setColumnWidth(7, 5 * 256+184);
		sheet.setColumnWidth(8, 5 * 256+184);
		sheet.setColumnWidth(9, 5 * 256+184);
		sheet.setColumnWidth(10, 5 * 256+184);
		sheet.setColumnWidth(11, 5 * 256+184);
		sheet.setColumnWidth(12, 5 * 256+184);
		sheet.setColumnWidth(13, 5 * 256+184);
		sheet.setColumnWidth(14, 12 * 256+184);
		
		// 生成一个样式
		CellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		// 生成一个字体
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 22);
		font.setFontName("宋体");
		style.setFont(font);
		Cell cell = row.createCell(0);
		cell.setCellValue("干部职数配备预审单");
		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
		cell.setCellStyle(style);
		
		CellStyle style2 = workbook.createCellStyle();
		style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style2.setAlignment(CellStyle.ALIGN_LEFT);//水平居左
		// 生成一个字体
		Font font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 12);
		font2.setFontName("宋体");
		style2.setFont(font2);
		row = sheet.createRow(1);
		// 设置创建单元格高度
		row.setHeightInPoints((short) 27);
		cell = row.createCell(0);
		cell.setCellValue("填报单位（盖章）：省委组织部");
		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
		cell.setCellStyle(style2);
		cell = row.createCell(6);
		cell.setCellValue("负责人（签字）：");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 10));
		cell.setCellStyle(style2);
		cell = row.createCell(11);
		cell.setCellValue("填报时间："+new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 11, 14));
		cell.setCellStyle(style2);
		
		CellStyle style3 = workbook.createCellStyle();
		style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style3.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		style3.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
        style3.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
        style3.setBorderRight(CellStyle.BORDER_THIN);//右边框       
        style3.setBorderTop(CellStyle.BORDER_THIN);//上边框 
        style3.setWrapText(true);// 自动换行
		// 生成一个字体
		Font font3 = workbook.createFont();
		font3.setFontHeightInPoints((short) 12);
		font3.setFontName("宋体");
		style3.setFont(font3);
		row = sheet.createRow(2);
		// 设置创建单元格高度
		row.setHeightInPoints((short) 28);
		cell = row.createCell(0);//这里晚点要根据行数合并单元格
		cell.setCellValue("职数配备");
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellValue("拟配备干部的地区或单位（含内设机构）");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));
		cell.setCellStyle(style3);
		String[] headers = { "职数层级", "核定职数", "现配职数", "拟免职数", "拟配职数","是否\r\n超职数"};
        List<String> list  = Arrays.asList(headers);
        int cellnum=2;
        for(int i=0;i<list.size();i++){
        	cell = row.createCell(cellnum);
        	cell.setCellValue(list.get(i));
        	cell.setCellStyle(style3);
        	cell = row.createCell(cellnum+1);
        	cell.setCellStyle(style3);
        	sheet.addMergedRegion(new CellRangeAddress(2, 2, cellnum, cellnum+1));
        	cellnum = cellnum+2;
        }
        cell = row.createCell(14);
		cell.setCellValue("备注");
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 14, 14));
		cell.setCellStyle(style3);
		
		row = sheet.createRow(3);
		// 设置创建单元格高度
		row.setHeightInPoints((short) 102);
		cell = row.createCell(0);
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellStyle(style3);
		cell = row.createCell(2);
		cell.setCellValue("处级");
		cell.setCellStyle(style3);
		cell = row.createCell(3);
		cell.setCellValue("科级");
		cell.setCellStyle(style3);
		for(int m=0;m<10;m++){
			cell = row.createCell(4+m);
			if((m&1)!=1){
				cell.setCellValue("领导职数");
			}else{
				cell.setCellValue("非领导职数");
			}
			cell.setCellStyle(style3);
		}
		cell = row.createCell(14);
		cell.setCellStyle(style3);
		int i=3;
		while(rs.next()){
			i++;
        	//字数大于18个字不设置行高
        	ExcelReturnParam setRowHeight = new ExcelReturnParam(true,18);
        	row = sheet.createRow(i);
        	cell = row.createCell(0);
        	cell.setCellStyle(style3);
        	cell = row.createCell(1);
        	String value = rs.getString("b0101");//单位名称
        	ExcelStyleUtil.setCellValue(cell, style3, value,setRowHeight);
        	
        	cell = row.createCell(2);
        	cell.setCellValue(rs.getString("js1202").equals("1")?"√":"");//职数层级处级
        	cell.setCellStyle(style3);
        	
        	cell = row.createCell(3);
        	cell.setCellValue(rs.getString("js1202").equals("0")?"√":"");//职数层级科级
        	cell.setCellStyle(style3);
        	
        	cell = row.createCell(4);
        	cell.setCellValue(rs.getString("js1203"));//核定职数领导职数
        	cell.setCellStyle(style3);
        	
        	cell = row.createCell(5);
        	cell.setCellValue(rs.getString("js1204"));//核定职数非领导职数
        	cell.setCellStyle(style3);
        	
        	cell = row.createCell(6);
        	cell.setCellValue(rs.getString("js1205"));//现配职数领导职数
        	cell.setCellStyle(style3);
        	
        	cell = row.createCell(7);
        	cell.setCellValue(rs.getString("js1206"));//现配职数非领导职数
        	cell.setCellStyle(style3);
        	
        	cell = row.createCell(8);
        	cell.setCellValue(rs.getString("js1207"));//拟免职数领导职数
        	cell.setCellStyle(style3);
        	
        	cell = row.createCell(9);
        	cell.setCellValue(rs.getString("js1208"));//拟免职数非领导职数
        	cell.setCellStyle(style3);
        	
        	cell = row.createCell(10);
        	cell.setCellValue(rs.getString("js1209"));//拟配职数领导职数
        	cell.setCellStyle(style3);
        	
        	cell = row.createCell(11);
        	cell.setCellValue(rs.getString("js1210"));//拟配职数非领导职数
        	cell.setCellStyle(style3);
        	
        	cell = row.createCell(12);
        	cell.setCellValue(rs.getString("js1211").equals("0")?"否":"是");//超值数领导职数
        	cell.setCellStyle(style3);
        	
        	cell = row.createCell(13);
        	cell.setCellValue(rs.getString("js1212").equals("0")?"否":"是");//超值数非领导职数
        	cell.setCellStyle(style3);
        	
        	cell = row.createCell(14);
        	cell.setCellValue(rs.getString("js1213"));//备注
        	cell.setCellStyle(style3);
        	
        	if(setRowHeight.isSetRowHeight()){
				row.setHeightInPoints((short)41);//行高
			}
        }
		
		if(i==3){
			i++;//如果没有数据加一行空行
			row = sheet.createRow(i);
			row.setHeightInPoints((short)40);//行高
			for(int z=0;z<15;z++){
				cell = row.createCell(z);
	        	cell.setCellStyle(style3);
			}
        	
		}
		
		sheet.addMergedRegion(new CellRangeAddress(2, i, 0, 0));//将之前的单元格合并
		
		row = sheet.createRow(i+1);
		row.setHeightInPoints((short)120);//行高
		cell=row.createCell(0);
		cell.setCellValue("拟配职数的职务名称");
		cell.setCellStyle(style3);
		for(int z=1;z<15;z++){
			cell = row.createCell(z);
        	cell.setCellStyle(style3);
		}
		sheet.addMergedRegion(new CellRangeAddress(i+1, i+1, 1, 14));
		
		row = sheet.createRow(i+2);
		row.setHeightInPoints((short)50);//行高
		cell=row.createCell(0);
		cell.setCellValue("干部监督机构审核意见");
		cell.setCellStyle(style3);
		CellStyle style4 = workbook.createCellStyle();
		style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style4.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		//style4.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
		style4.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
		style4.setBorderRight(CellStyle.BORDER_THIN);//右边框       
		style4.setBorderTop(CellStyle.BORDER_THIN);//上边框 
		style4.setWrapText(true);// 自动换行
		for(int z=1;z<15;z++){
			cell = row.createCell(z);
        	cell.setCellStyle(style4);
		}
		sheet.addMergedRegion(new CellRangeAddress(i+2, i+3, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(i+2, i+2, 1, 14));
		
		row = sheet.createRow(i+3);
		row.setHeightInPoints((short)37);//行高
		cell=row.createCell(0);
		cell.setCellStyle(style3);
		CellStyle style5 = workbook.createCellStyle();
		style5.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style5.setAlignment(CellStyle.ALIGN_LEFT);//水平居左
		style5.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
		//style5.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
		//style5.setBorderRight(CellStyle.BORDER_THIN);//右边框       
		//style5.setBorderTop(CellStyle.BORDER_THIN);//上边框 
		style5.setWrapText(true);// 自动换行
		style5.setFont(font3);
		
		CellStyle style6 = workbook.createCellStyle();
		style6.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);//垂直居下
		style6.setAlignment(CellStyle.ALIGN_RIGHT);//水平居右
		style6.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
		//style6.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
		style6.setBorderRight(CellStyle.BORDER_THIN);//右边框       
		//style6.setBorderTop(CellStyle.BORDER_THIN);//上边框 
		style6.setWrapText(true);// 自动换行
		style6.setFont(font3);
		for(int z=1;z<15;z++){
			cell = row.createCell(z);
			if(z==1){
				cell.setCellValue("初核人：");
			}
			if(z==6){
				cell.setCellValue("审核人：");
			}
			if(z==14){
				cell.setCellValue("年  月  日");
				cell.setCellStyle(style6);
				break;
			}
        	cell.setCellStyle(style5);
		}
		sheet.addMergedRegion(new CellRangeAddress(i+3, i+3, 6, 7));
		
		row = sheet.createRow(i+4);
		row.setHeightInPoints((short)66);//行高
		cell=row.createCell(0);
		cell.setCellValue("领导审批意见");
		cell.setCellStyle(style3);
		CellStyle style7 = workbook.createCellStyle();
		style7.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);//垂直居下
		style7.setAlignment(CellStyle.ALIGN_RIGHT);//水平居右
		style7.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
		style7.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
		style7.setBorderRight(CellStyle.BORDER_THIN);//右边框       
		style7.setBorderTop(CellStyle.BORDER_THIN);//上边框 
		style7.setWrapText(true);// 自动换行
		style7.setFont(font3);
		for(int z=1;z<15;z++){
			cell = row.createCell(z);
            if(z==1){
				cell.setCellValue("年  月  日");
			}
        	cell.setCellStyle(style7);
		}
		sheet.addMergedRegion(new CellRangeAddress(i+4, i+4, 1, 14));
		
		row = sheet.createRow(i+5);
		row.setHeightInPoints((short)69);//行高
		cell=row.createCell(0);
		cell.setCellValue("审核意见落实情况");
		cell.setCellStyle(style3);
		for(int z=1;z<15;z++){
			cell = row.createCell(z);
        	cell.setCellStyle(style3);
		}
		sheet.addMergedRegion(new CellRangeAddress(i+5, i+5, 1, 14));
		
		row = sheet.createRow(i+6);
		row.setHeightInPoints((short)40);//行高
		cell=row.createCell(0);
		cell.setCellValue("注：1、填报“核定职数”栏，要以组织人事、编制部门核定的职数为准。其他栏目也要如实填报，否则追究填报单位组织人事部门负责人责任；\r\n    2、此表一式两份，上报单位、审核单位各一份。");
		CellStyle style8 = workbook.createCellStyle();
		style8.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style8.setAlignment(CellStyle.ALIGN_LEFT);//水平居左
		style8.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
		style8.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
		style8.setBorderRight(CellStyle.BORDER_THIN);//右边框       
		style8.setBorderTop(CellStyle.BORDER_THIN);//上边框 
		style8.setWrapText(true);// 自动换行
		// 生成一个字体
		Font font4 = workbook.createFont();
		font4.setFontHeightInPoints((short) 9);
		font4.setFontName("宋体");
		style8.setFont(font4);
		cell.setCellStyle(style8);
		for(int z=1;z<15;z++){
			cell = row.createCell(z);
        	cell.setCellStyle(style8);
		}
		sheet.addMergedRegion(new CellRangeAddress(i+6, i+6, 0, 14));
		
		
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "干部职数配备预审单.xls";
		if(this.outputFileNameList.size()==0){
			this.outputFileNameList.add(fn);
		}
			
	    OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
	    //这个就就是弹出下载对话框的关键代码
	    BufferedOutputStream bos = new BufferedOutputStream(fos);
	    workbook.write(bos);
	        
	    bos.flush();
	    fos.close();
	    bos.close();
	
	}
	
	/**
	 * 青干预审
	 */
	public void exportQGYS() throws RadowException,Exception {
		//声明一个工作簿  
		Workbook workbook = new HSSFWorkbook();  
		//生成一个表格  
		Sheet sheet = workbook.createSheet(); 
		PrintSetup ps = sheet.getPrintSetup();

		//ps.setLandscape(true); // 打印方向，true：横向，false：纵向
		ps.setPaperSize(PrintSetup.A4_PAPERSIZE); //纸张
		sheet.setMargin(Sheet.BottomMargin,( double ) 0.6 );// 页边距（下）
		sheet.setMargin(Sheet.LeftMargin,( double ) 0.2 );// 页边距（左）
		sheet.setMargin(Sheet.RightMargin,( double ) 0.2 );// 页边距（右）
		sheet.setMargin(Sheet.TopMargin,( double ) 0.6 );// 页边距（上）
		ps.setHeaderMargin((double) 0.3); // 页眉
		ps.setFooterMargin((double) 0.3);//页脚

		sheet.setHorizontallyCenter(true);//设置打印页面为水平居中
		//sheet.setVerticallyCenter(true);;//设置打印页面为垂直居中


		// 隐藏网格线
		sheet.setDisplayGridlines(false);
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String sql = "select * from js13 where rb_id = '"+rbId+"'";
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		/*String js1301 = "";
		String js1302 = "";
		String js1303 = "";
		String js1304 = "";
		String js1305 = "";
		String js1306 = "";
		String js1307 = "";
		String js1308 = "";
		String js1309 = "";
		String js1310 = "";
		String js1311 = "";
		String js1312 = "";
		String js1313 = "";
		String js1314 = "";
		String js1315 = "";
		String js1316 = "";
		String js1317 = "";
		String js1318 = "";
		String js1319 = "";
		String js1320 = "";
		String js1321 = "";
		String js1322 = "";
		String js1323 = "";
		String js1324 = "";
		String js1325 = "";
		String js1326 = "";*/
		String dw = "";			//单位
		String js1311 = "";		//现正
		String js1312 = "";		//现副
		String js1316 = "";		//现百分比
		String js1321 = "";		//现40正
		String js1322 = "";		//现40副
		String js1326 = "";		//现40百分比
		String js1331 = "";		//现80正
		String js1332 = "";		//现80副
		String js1336 = "";		//现80百分比
		String js1341 = "";		//现正
		String js1342 = "";		//现副
		String js1346 = "";		//现百分比
		String js1351 = "";		//现40正
		String js1352 = "";		//现40副
		String js1356 = "";		//现40百分比
		String js1361 = "";		//现80正
		String js1362 = "";		//现80副
		String js1366 = "";		//现80百分比
		
		String js1395 = "";		//备注
		while(rs.next()){
			js1311 = rs.getString("js1311");
			js1312 = rs.getString("js1312");
			js1316 = rs.getString("js1316");
			js1321 = rs.getString("js1321");
			js1322 = rs.getString("js1322");
			js1326 = rs.getString("js1326");
			js1331 = rs.getString("js1331");
			js1332 = rs.getString("js1332");
			js1336 = rs.getString("js1336");
			js1341 = rs.getString("js1341");
			js1342 = rs.getString("js1342");
			js1346 = rs.getString("js1346");
			js1351 = rs.getString("js1351");
			js1352 = rs.getString("js1352");
			js1356 = rs.getString("js1356");
			js1361 = rs.getString("js1361");
			js1362 = rs.getString("js1362");

			js1395 = rs.getString("js1395");
			/*js1301 = rs.getString("js1301");
			js1302 = rs.getString("js1302");
			js1303 = rs.getString("js1303");
			js1304 = rs.getString("js1304");
			js1305 = rs.getString("js1305");
			js1306 = rs.getString("js1306");
			js1307 = rs.getString("js1307");
			js1308 = rs.getString("js1308");
			js1309 = rs.getString("js1309");
			js1310 = rs.getString("js1310");
			js1311 = rs.getString("js1311");
			js1312 = rs.getString("js1312");
			js1313 = rs.getString("js1313");
			js1314 = rs.getString("js1314");
			js1315 = rs.getString("js1315");
			js1316 = rs.getString("js1316");
			js1317 = rs.getString("js1317");
			js1318 = rs.getString("js1318");
			js1319 = rs.getString("js1319");
			js1320 = rs.getString("js1320");
			js1321 = rs.getString("js1321");
			js1322 = rs.getString("js1322");
			js1323 = rs.getString("js1323");
			js1324 = rs.getString("js1324");
			js1325 = rs.getString("js1325");
			js1326 = rs.getString("js1326");*/
		}
		// 产生表格标题行
		Row row = sheet.createRow(0);
		// 设置创建单元格高度
		row.setHeightInPoints((short) 35);
		// 设置指定列宽度
		sheet.setColumnWidth(0, 11 * 256+184);
		sheet.setColumnWidth(1, 15 * 256+184);
		sheet.setColumnWidth(2, 15 * 256+184);
		sheet.setColumnWidth(3, 15 * 256+184);
		sheet.setColumnWidth(4, 15 * 256+184);
		sheet.setColumnWidth(5, 11 * 256+184);
		
		// 生成一个样式
		CellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		// 生成一个字体
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 22);
		font.setFontName("宋体");
		style.setFont(font);
		Cell cell = row.createCell(0);
		cell.setCellValue("市级机关、市属企事业单位");
		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		cell.setCellStyle(style);
		
		row = sheet.createRow(1);
		row.setHeightInPoints((short) 40);
		cell = row.createCell(0);
		cell.setCellValue("乡科级青年干部选配专项预审表");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
		cell.setCellStyle(style);
		
		row = sheet.createRow(2);
		row.setHeightInPoints((short) 30);
		cell = row.createCell(0);
		cell.setCellValue("填报单位（盖章）："+dw);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));
		// 生成一个样式
		CellStyle style2 = workbook.createCellStyle();
		style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style2.setAlignment(CellStyle.ALIGN_LEFT);//水平居左
		// 生成一个字体
		Font font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 12);
		font2.setFontName("宋体");
		style2.setFont(font2);
		cell.setCellStyle(style2);
		
		cell = row.createCell(4);
		cell.setCellValue("填报时间："+new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 5));
		cell.setCellStyle(style2);
		
		// 生成一个样式
		CellStyle style3 = workbook.createCellStyle();
		style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style3.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		style3.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
		style3.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
		style3.setBorderRight(CellStyle.BORDER_THIN);//右边框       
		style3.setBorderTop(CellStyle.BORDER_THIN);//上边框 
		style3.setWrapText(true);// 自动换行
		style3.setFont(font2);
		row = sheet.createRow(3);
		row.setHeightInPoints((short) 33);
		cell = row.createCell(0);
		cell.setCellValue("项    目");
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellStyle(style3);
		sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 1));
		
		cell = row.createCell(2);
		cell.setCellValue("职    级");
		cell.setCellStyle(style3);
		cell = row.createCell(3);
		cell.setCellStyle(style3);
		cell = row.createCell(4);
		cell.setCellStyle(style3);
		cell = row.createCell(5);
		cell.setCellStyle(style3);
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 5));
		
		row = sheet.createRow(4);
		row.setHeightInPoints((short) 33);
		cell = row.createCell(0);
		cell.setCellStyle(style3);
		cell = row.createCell(2);
		cell.setCellValue("正科职");
		cell.setCellStyle(style3);
		
		cell = row.createCell(3);
		cell.setCellValue("副科职");
		cell.setCellStyle(style3);
		
		cell = row.createCell(4);
		cell.setCellValue("青年干部占比");
		cell.setCellStyle(style3);
		
		cell = row.createCell(5);
		cell.setCellValue("备注");
		cell.setCellStyle(style3);
		
		row = sheet.createRow(5);
		row.setHeightInPoints((short) 33);
		cell = row.createCell(0);
		HSSFRichTextString content = new HSSFRichTextString("现配备\r\n情况");
		cell.setCellValue(content);
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellValue("人数");
		cell.setCellStyle(style3);
		cell = row.createCell(2);
		cell.setCellValue(js1311);
		cell.setCellStyle(style3);
		cell = row.createCell(3);
		cell.setCellValue(js1312);
		cell.setCellStyle(style3);
		cell = row.createCell(4);
		cell.setCellValue(js1316+"%");
		cell.setCellStyle(style3);
		cell = row.createCell(5);
		cell.setCellValue(js1395);
		sheet.addMergedRegion(new CellRangeAddress(5, 12, 5, 5));
		cell.setCellStyle(style3);
		
		row = sheet.createRow(6);
		row.setHeightInPoints((short) 33);
		cell = row.createCell(0);
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellValue("40岁以下");
		cell.setCellStyle(style3);
		cell = row.createCell(2);
		cell.setCellValue(js1321);
		cell.setCellStyle(style3);
		cell = row.createCell(3);
		cell.setCellValue(js1322);
		cell.setCellStyle(style3);
		cell = row.createCell(4);
		cell.setCellValue(js1326+"%");
		cell.setCellStyle(style3);
		cell = row.createCell(5);
		cell.setCellStyle(style3);
		
		/*row = sheet.createRow(7);
		row.setHeightInPoints((short) 33);
		cell = row.createCell(0);
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellValue("35岁以下");
		cell.setCellStyle(style3);
		cell = row.createCell(2);
		cell.setCellValue(js1307);
		cell.setCellStyle(style3);
		cell = row.createCell(3);
		cell.setCellValue(js1308);
		cell.setCellStyle(style3);
		cell = row.createCell(4);
		cell.setCellValue(js1309+"%");
		cell.setCellStyle(style3);
		cell = row.createCell(5);
		cell.setCellStyle(style3);*/
		
		row = sheet.createRow(7);
		row.setHeightInPoints((short) 33);
		cell = row.createCell(0);
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellValue("80后");
		cell.setCellStyle(style3);
		cell = row.createCell(2);
		cell.setCellValue(js1331);
		cell.setCellStyle(style3);
		cell = row.createCell(3);
		cell.setCellValue(js1332);
		cell.setCellStyle(style3);
		cell = row.createCell(4);
		cell.setCellValue(js1336+"%");
		cell.setCellStyle(style3);
		cell = row.createCell(5);
		cell.setCellStyle(style3);
		//合并5-8行第一列单元格
		sheet.addMergedRegion(new CellRangeAddress(5, 7, 0, 0));
		
		row = sheet.createRow(8);
		row.setHeightInPoints((short) 33);
		cell = row.createCell(0);
		cell.setCellValue("拟提拔\r\n情况");
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellValue("人数");
		cell.setCellStyle(style3);
		cell = row.createCell(2);
		cell.setCellValue(js1341);
		cell.setCellStyle(style3);
		cell = row.createCell(3);
		cell.setCellValue(js1342);
		cell.setCellStyle(style3);
		cell = row.createCell(4);
		cell.setCellValue(js1346+"%");
		cell.setCellStyle(style3);
		cell = row.createCell(5);
		cell.setCellStyle(style3);
		
		row = sheet.createRow(9);
		row.setHeightInPoints((short) 33);
		cell = row.createCell(0);
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellValue("40岁以下");
		cell.setCellStyle(style3);
		cell = row.createCell(2);
		cell.setCellValue(js1351);
		cell.setCellStyle(style3);
		cell = row.createCell(3);
		cell.setCellValue(js1352);
		cell.setCellStyle(style3);
		cell = row.createCell(4);
		cell.setCellValue(js1356+"%");
		cell.setCellStyle(style3);
		cell = row.createCell(5);
		cell.setCellStyle(style3);
		
		/*row = sheet.createRow(11);
		row.setHeightInPoints((short) 33);
		cell = row.createCell(0);
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellValue("35岁以下");
		cell.setCellStyle(style3);
		cell = row.createCell(2);
		cell.setCellValue(js1319);
		cell.setCellStyle(style3);
		cell = row.createCell(3);
		cell.setCellValue(js1320);
		cell.setCellStyle(style3);
		cell = row.createCell(4);
		cell.setCellValue(js1321+"%");
		cell.setCellStyle(style3);
		cell = row.createCell(5);
		cell.setCellStyle(style3);*/
		
		row = sheet.createRow(12);
		row.setHeightInPoints((short) 33);
		cell = row.createCell(0);
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellValue("90后");
		cell.setCellStyle(style3);
		cell = row.createCell(2);
		cell.setCellValue(js1361);
		cell.setCellStyle(style3);
		cell = row.createCell(3);
		cell.setCellValue(js1362);
		cell.setCellStyle(style3);
		cell = row.createCell(4);
		cell.setCellValue(js1366+"%");
		cell.setCellStyle(style3);
		cell = row.createCell(5);
		cell.setCellStyle(style3);
		sheet.addMergedRegion(new CellRangeAddress(9, 11, 0, 0));
		
		// 生成一个样式
		CellStyle style4 = workbook.createCellStyle();
		style4.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);//垂直居下
		style4.setAlignment(CellStyle.ALIGN_LEFT);//水平居左
		style4.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
		style4.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
		style4.setBorderRight(CellStyle.BORDER_THIN);//右边框       
		style4.setBorderTop(CellStyle.BORDER_THIN);//上边框 
		style4.setFont(font2);
		row = sheet.createRow(13);
		row.setHeightInPoints((short) 90);
		cell = row.createCell(0);
		cell.setCellValue("市委组织部\r\n"+"青年干部处\r\n"+"审核意见");
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellStyle(style3);
		sheet.addMergedRegion(new CellRangeAddress(13, 13, 0, 1));
		cell = row.createCell(2);
		cell.setCellValue("初核人：        审核人：               年   月   日");
		cell.setCellStyle(style4);
		cell = row.createCell(3);
		cell.setCellStyle(style4);
		cell = row.createCell(4);
		cell.setCellStyle(style4);
		cell = row.createCell(5);
		cell.setCellStyle(style4);
		sheet.addMergedRegion(new CellRangeAddress(13, 13, 2, 5));
		
		row = sheet.createRow(14);
		row.setHeightInPoints((short) 90);
		cell = row.createCell(0);
		cell.setCellValue("市委组织部\r\n"+"部领导审批意见");
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellStyle(style3);
		sheet.addMergedRegion(new CellRangeAddress(14, 14, 0, 1));
		cell = row.createCell(2);
		cell.setCellStyle(style4);
		cell = row.createCell(3);
		cell.setCellStyle(style4);
		cell = row.createCell(4);
		cell.setCellStyle(style4);
		cell = row.createCell(5);
		cell.setCellStyle(style4);
		sheet.addMergedRegion(new CellRangeAddress(14, 14, 2, 5));
		
		row = sheet.createRow(15);
		row.setHeightInPoints((short) 90);
		cell = row.createCell(0);
		cell.setCellValue("审核意见\r\n"+"落实情况");
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellStyle(style3);
		sheet.addMergedRegion(new CellRangeAddress(15, 15, 0, 1));
		cell = row.createCell(2);
		cell.setCellStyle(style4);
		cell = row.createCell(3);
		cell.setCellStyle(style4);
		cell = row.createCell(4);
		cell.setCellStyle(style4);
		cell = row.createCell(5);
		cell.setCellStyle(style4);
		sheet.addMergedRegion(new CellRangeAddress(15, 15, 2, 5));
		
		// 生成一个样式
		CellStyle style5 = workbook.createCellStyle();
		style5.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);//垂直居下
		style5.setAlignment(CellStyle.ALIGN_LEFT);//水平居左
		style5.setFont(font2);
		row = sheet.createRow(16);
		row.setHeightInPoints((short) 30);
		cell = row.createCell(0);
		cell.setCellValue("负责人（签字）：              经办人：           联系电话：");
		cell.setCellStyle(style5);
		cell = row.createCell(1);
		cell.setCellStyle(style5);
		cell = row.createCell(2);
		cell.setCellStyle(style5);
		cell = row.createCell(3);
		cell.setCellStyle(style5);
		cell = row.createCell(4);
		cell.setCellStyle(style5);
		cell = row.createCell(5);
		cell.setCellStyle(style5);
		sheet.addMergedRegion(new CellRangeAddress(16, 16, 0, 5));
		
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "青干预审表.xls";
		if(this.outputFileNameList.size()==0){
			this.outputFileNameList.add(fn);
		}
			
	    OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
	    //这个就就是弹出下载对话框的关键代码
	    BufferedOutputStream bos = new BufferedOutputStream(fos);
	    workbook.write(bos);
	        
	    bos.flush();
	    fos.close();
	    bos.close();
		
	}
	public static boolean getLicense() {
		boolean result = false;
		try {
			InputStream is = TestAspose2Pdf.class.getClassLoader()
					.getResourceAsStream("Aspose.Words.lic"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result = true;
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public void exportQGYS2() throws RadowException, IOException {
		if (!getLicense()) { // 验证License 若不验证则生成的word文档会有水印产生
			return ;
		}
		HBSession sess=HBUtil.getHBSession();
		Statement stmt = null;
		ResultSet rs = null;
		String doctpl = "";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String rbId = pm.request.getAttribute("rbId").toString();//批次
			RecordBatch rb = (RecordBatch) sess.get(RecordBatch.class, rbId);
			String sql = "select * from js13 where rb_id = '"+rbId+"'";
			stmt = sess.connection().createStatement();
			rs = stmt.executeQuery(sql);
			String dw = rb.getRbOrg();			//单位
			String sj = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());	
			String bz ="";
			String a1 ="",a2="",a3="",a4="",a5="",a6="";
			String b1 ="",b2="",b3="",b4="",b5="",b6="";
			String c1 ="",c2="",c3="",c4="",c5="",c6="";
			String d1 ="",d2="",d3="",d4="",d5="",d6="";
			String e1 ="",e2="",e3="",e4="",e5="",e6="";
			String f1 ="",f2="",f3="",f4="",f5="",f6="";
			while (rs.next()) {
				a1=rs.getString("js1311");a2=rs.getString("js1312");a3=rs.getString("js1313");a4=rs.getString("js1314");a5=rs.getString("js1315");a6=rs.getString("js1316");
				b1=rs.getString("js1321");b2=rs.getString("js1322");b3=rs.getString("js1323");b4=rs.getString("js1324");b5=rs.getString("js1325");b6=rs.getString("js1326");
				c1=rs.getString("js1331");c2=rs.getString("js1332");c3=rs.getString("js1333");c4=rs.getString("js1334");c5=rs.getString("js1335");c6=rs.getString("js1336");
				d1=rs.getString("js1341");d2=rs.getString("js1342");d3=rs.getString("js1343");d4=rs.getString("js1344");d5=rs.getString("js1345");d6=rs.getString("js1346");
				e1=rs.getString("js1351");e2=rs.getString("js1352");e3=rs.getString("js1353");e4=rs.getString("js1354");e5=rs.getString("js1355");e6=rs.getString("js1356");
				f1=rs.getString("js1361");f2=rs.getString("js1362");f3=rs.getString("js1363");f4=rs.getString("js1364");f5=rs.getString("js1365");f6=rs.getString("js1366");
				bz=rs.getString("js1395");
			}
			dataMap.put("dw", dw);dataMap.put("sj", sj);dataMap.put("bz", bz);
			dataMap.put("a1", a1);dataMap.put("a2", a2);dataMap.put("a3", a3);dataMap.put("a4", a4);dataMap.put("a5", a5);dataMap.put("a6", a6);		
			dataMap.put("b1", b1);dataMap.put("b2", b2);dataMap.put("b3", b3);dataMap.put("b4", b4);dataMap.put("b5", b5);dataMap.put("b6", b6);		
			dataMap.put("c1", c1);dataMap.put("c2", c2);dataMap.put("c3", c3);dataMap.put("c4", c4);dataMap.put("c5", c5);dataMap.put("c6", c6);		
			dataMap.put("d1", d1);dataMap.put("d2", d2);dataMap.put("d3", d3);dataMap.put("d4", d4);dataMap.put("d5", d5);dataMap.put("d6", d6);		
			dataMap.put("e1", e1);dataMap.put("e2", e2);dataMap.put("e3", e3);dataMap.put("e4", e4);dataMap.put("e5", e5);dataMap.put("e6", e6);		
			dataMap.put("f1", f1);dataMap.put("f2", f2);dataMap.put("f3", f3);dataMap.put("f4", f4);dataMap.put("f5", f5);dataMap.put("f6", f6);		
			
			String tp = pm.request.getSession().getServletContext().getRealPath("/")+"pages\\xbrm\\";
			String tfile = "";
			System.out.println(tp);
			doctpl = tp + "/qcjsysd1.doc";
			Document doc = new Document(doctpl);
			StringBuffer mapkey = new StringBuffer();
			StringBuffer mapvalue = new StringBuffer();
			for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if(StringUtil.isEmpty(value+"")){
                    	value = "@";
				}
				mapkey = mapkey.append(key + "&");
				//去掉&特殊字符
				value = value!=null ? value.toString().replaceAll("&", ""): "";
				mapvalue = mapvalue.append(value + "&");
			}
			// 文本域
			String[] Flds = mapkey.toString().split("&");
			// 值
			String[] Vals = mapvalue.toString().split("&");
			for (int j = 0; j < Vals.length; j++) {
				if(Vals[j].equals("@")){
					Vals[j] = "";
				}
			}
			// 填充单个数据
			doc.getMailMerge().execute(Flds, Vals);
			
			File opp = new File(outputpath);
			if(!opp.isDirectory()){
				opp.mkdirs();
			}
			String fn = "青干预审表省机关.doc";
			if(this.outputFileNameList.size()==0){
				this.outputFileNameList.add(fn);
			}
		    OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
		    //这个就就是弹出下载对话框的关键代码
		    BufferedOutputStream bos = new BufferedOutputStream(fos);
		    doc.save(bos, SaveFormat.DOC);
		    fos.close();
		    bos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("导出异常！");
		}
		
	}
	
	public void exportQGYS3() throws RadowException, IOException {
		if (!getLicense()) { // 验证License 若不验证则生成的word文档会有水印产生
			return ;
		}
		HBSession sess=HBUtil.getHBSession();
		Statement stmt = null;
		ResultSet rs = null;
		String doctpl = "";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String rbId = pm.request.getAttribute("rbId").toString();//批次
			RecordBatch rb = (RecordBatch) sess.get(RecordBatch.class, rbId);
			String sql = "select * from js99 where rb_id = '"+rbId+"'";
			stmt = sess.connection().createStatement();
			rs = stmt.executeQuery(sql);
			String dw = rb.getRbOrg();			//单位
			String sj = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());	
			String bz ="";
			String a1 ="",a2="",a3="",a4="",a5="",a6="";
			String b1 ="",b2="",b3="",b4="",b5="",b6="";
			String c1 ="",c2="",c3="",c4="",c5="",c6="";
			String d1 ="",d2="",d3="",d4="",d5="",d6="";
			String e1 ="",e2="",e3="",e4="",e5="",e6="";
			String f1 ="",f2="",f3="",f4="",f5="",f6="";
			while (rs.next()) {
				a1=rs.getString("js9911");a2=rs.getString("js9912");a3=rs.getString("js9913");a4=rs.getString("js9914");a5=rs.getString("js9915");a6=rs.getString("js9916");
				b1=rs.getString("js9921");b2=rs.getString("js9922");b3=rs.getString("js9923");b4=rs.getString("js9924");b5=rs.getString("js9925");b6=rs.getString("js9926");
				c1=rs.getString("js9931");c2=rs.getString("js9932");c3=rs.getString("js9933");c4=rs.getString("js9934");c5=rs.getString("js9935");c6=rs.getString("js9936");
				d1=rs.getString("js9941");d2=rs.getString("js9942");d3=rs.getString("js9943");d4=rs.getString("js9944");d5=rs.getString("js9945");d6=rs.getString("js9946");
				e1=rs.getString("js9951");e2=rs.getString("js9952");e3=rs.getString("js9953");e4=rs.getString("js9954");e5=rs.getString("js9955");e6=rs.getString("js9956");
				f1=rs.getString("js9961");f2=rs.getString("js9962");f3=rs.getString("js9963");f4=rs.getString("js9964");f5=rs.getString("js9965");f6=rs.getString("js9966");
				bz=rs.getString("js9995");
			}
			dataMap.put("dw", dw);dataMap.put("sj", sj);dataMap.put("bz", bz);
			dataMap.put("a1", a1);dataMap.put("a2", a2);dataMap.put("a3", a3);dataMap.put("a4", a4);dataMap.put("a5", a5);dataMap.put("a6", a6);		
			dataMap.put("b1", b1);dataMap.put("b2", b2);dataMap.put("b3", b3);dataMap.put("b4", b4);dataMap.put("b5", b5);dataMap.put("b6", b6);		
			dataMap.put("c1", c1);dataMap.put("c2", c2);dataMap.put("c3", c3);dataMap.put("c4", c4);dataMap.put("c5", c5);dataMap.put("c6", c6);		
			dataMap.put("d1", d1);dataMap.put("d2", d2);dataMap.put("d3", d3);dataMap.put("d4", d4);dataMap.put("d5", d5);dataMap.put("d6", d6);		
			dataMap.put("e1", e1);dataMap.put("e2", e2);dataMap.put("e3", e3);dataMap.put("e4", e4);dataMap.put("e5", e5);dataMap.put("e6", e6);		
			dataMap.put("f1", f1);dataMap.put("f2", f2);dataMap.put("f3", f3);dataMap.put("f4", f4);dataMap.put("f5", f5);dataMap.put("f6", f6);		
			
			String tp = pm.request.getSession().getServletContext().getRealPath("/")+"pages\\xbrm\\";
			String tfile = "";
			System.out.println(tp);
			doctpl = tp + "/qcjsysd2.doc";
			Document doc = new Document(doctpl);
			StringBuffer mapkey = new StringBuffer();
			StringBuffer mapvalue = new StringBuffer();
			for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if(StringUtil.isEmpty(value+"")){
                    	value = "@";
				}
				mapkey = mapkey.append(key + "&");
				//去掉&特殊字符
				value = value!=null?value.toString().replaceAll("&", ""):"";
				mapvalue = mapvalue.append(value + "&");
			}
			// 文本域
			String[] Flds = mapkey.toString().split("&");
			// 值
			String[] Vals = mapvalue.toString().split("&");
			for (int j = 0; j < Vals.length; j++) {
				if(Vals[j].equals("@")){
					Vals[j] = "";
				}
			}
			// 填充单个数据
			doc.getMailMerge().execute(Flds, Vals);
			
			File opp = new File(outputpath);
			if(!opp.isDirectory()){
				opp.mkdirs();
			}
			String fn = "青干预审表县区.doc";
			if(this.outputFileNameList.size()==0){
				this.outputFileNameList.add(fn);
			}
		    OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
		    //这个就就是弹出下载对话框的关键代码
		    BufferedOutputStream bos = new BufferedOutputStream(fos);
		    doc.save(bos, SaveFormat.DOC);
		    fos.close();
		    bos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("导出异常！");
		}
		
	}
	
	
	
	/**
	 * 干部调配建议方案
	 * type 1代表动议导出的， 2代表讨论决定导出的
	 * @throws RadowException 
	 * @throws Exception 
	 */
	public void exportGBTP(String type) throws RadowException, Exception {
		File tplfilepath;
		if("1".equals(type)){
			tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/干部调配建议方案（动议）.xls").getPath());
		}else{
			tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/干部调配建议方案.xls").getPath());
		}
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //读取复制好的文件
		
		Workbook workbook = new HSSFWorkbook(is);      //默认读取2003版的Excel
		//获取sheet页总数
		//int sheetNums = workbook.getNumberOfSheets();
		
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String cur_hj = pm.request.getAttribute("cur_hj").toString();//环节
		String cur_hj_4 = pm.request.getAttribute("cur_hj_4").toString();//讨论决定分环节
		String dc005 = pm.request.getAttribute("dc005").toString();
		String js0100WhereSql = pm.request.getAttribute("js0100WhereSql").toString();
		
		String[] cur_hj_4Array;
		
		if("1".equals(type)){//初始动议
			cur_hj_4Array = new String[]{RMHJ.DONG_YI};
		}else{//部会 书记 常委
			cur_hj_4Array = new String[]{"4_1","4_2","4_3"};
		}
		int cur_hj_4ArraySize = cur_hj_4Array.length;
		for(int cur_hj_4ArrayI=0;cur_hj_4ArrayI<cur_hj_4ArraySize;cur_hj_4ArrayI++){
			if(cur_hj_4ArraySize==1){
				cur_hj = cur_hj_4Array[cur_hj_4ArrayI];
			}else{
				cur_hj = RMHJ.TAO_LUN_JUE_DING;
				cur_hj_4 = cur_hj_4Array[cur_hj_4ArrayI];
			}
			Sheet sheet = workbook.getSheetAt(cur_hj_4ArrayI);
			String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005, js0100WhereSql);
			HBSession session = null;
			Statement stmt = null;
			ResultSet rs = null;
			session = HBUtil.getHBSession();
			stmt = session.connection().createStatement();
			rs = stmt.executeQuery(sql);
			//String tmp = null;
			CellStyle style = ExcelStyleUtil.getCellStyle(workbook);
			CellStyle style2 = ExcelStyleUtil.getCellStyle(workbook,(short)10);//姓名
			int rowIndex = 6;
			int i=0;
			Object prevValue = null;
			List<Map<String, String>> typelist = new ArrayList<Map<String,String>>();
			Map<String, String> typemap = new HashMap<String, String>();
			while (rs.next()) {
				i++;
				//字的个数大于30个就不设置行高。
				ExcelReturnParam setRowHeight = new ExcelReturnParam(true,4);
				
				Object value = rs.getObject("dc003")==null?"":rs.getObject("dc003");
				if(prevValue==null||!(prevValue.equals(value))){//类别
					Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
					rowIndex++;
					String dc006 = rs.getObject("dc006")==null?"":("("+rs.getObject("dc006").toString()+")");
					typemap = new HashMap<String, String>();
					typemap.put("rowindex", row.getRowNum()+"");
					typemap.put("rowvalue", value.toString());
					typemap.put("dc006", dc006);
					typemap.put("size", i+"");
					typelist.add(typemap);
					prevValue = rs.getObject("dc003");
					
				}
				
				Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
				Cell cell = row.createCell(0);//序号
				ExcelStyleUtil.setCellValue(cell, style, i+"");
				cell = row.createCell(1);//姓名
				value = rs.getObject("a0101");
				String a0141 = rs.getString("a0141");//第一党派
				String a0104 = rs.getString("a0104");//性别
				String desc = ExcelStyleUtil.formatName(a0141, a0104);
				
				ExcelStyleUtil.setCellValue(cell, style2, value==null?"":(value+desc),setRowHeight);
				
				cell = row.createCell(2);//出生年月
				value = rs.getObject("a0107");
				value = ExcelStyleUtil.formatDate(value==null?"":value.toString());
				ExcelStyleUtil.setCellValue(cell, style, value,setRowHeight.setRowHeightLength(10));
				
				cell = row.createCell(3);//学历职称 //去掉职称
				value = rs.getObject("zgxl")==null?"":rs.getObject("zgxl");
				ExcelStyleUtil.setCellValue(cell, style, value,setRowHeight.setRowHeightLength(6));
				
				
				cell = row.createCell(4);//现任职务
				value = rs.getObject("js0108");
				ExcelStyleUtil.setCellValue(cell, style, value,setRowHeight.setRowHeightLength(10));
				cell = row.createCell(5);//拟任职务
				value = rs.getObject("js0111");
				ExcelStyleUtil.setCellValue(cell, style, value,setRowHeight);
				cell = row.createCell(6);
				value = "";
				ExcelStyleUtil.setCellValue(cell, style, value);
				
				if(setRowHeight.isSetRowHeight()){
					row.setHeightInPoints((short)40);//行高
				}
				rowIndex++;
			}
			for(int ri=0;ri<typelist.size();ri++){
				Map<String, String> m = typelist.get(ri);
				int rownum = Integer.valueOf(m.get("rowindex"));
				String rowvalue = m.get("rowvalue").toString();
				String dc006 = m.get("dc006").toString();
				int size = Integer.valueOf(m.get("size"));
				//这个就是合并单元格
				//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
				
				int sizeNext = 0;
				if(ri==typelist.size()-1){
					sizeNext = i+1;
				}else{
					sizeNext = Integer.valueOf(typelist.get(ri+1).get("size"));
				}
				
				ExcelStyleUtil.mergeRegionAndSetValue(sheet,workbook, 0, 6, rownum,
						NumberFormatUtil.formatInteger(ri+1)+"、"+rowvalue+"（"+(sizeNext-size)+"人）"+dc006);
				
				
			}
			File opp = new File(outputpath);
			if(!opp.isDirectory()){
				opp.mkdirs();
			}
			String hjstr = "1".equals(type)?"（动议）":"（讨论决定）";
			String fn = "干部调配建议方案"+hjstr+".xls";
			if(this.outputFileNameList.size()==0){
				this.outputFileNameList.add(fn);
			}
			
	        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
	        //这个就就是弹出下载对话框的关键代码
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        workbook.write(bos);
	        
	        bos.flush();
	        fos.close();
	        bos.close();
		}
		
		
		
		
	}

	
	
	/**
	 * 初步建议人选征求市纪委名单
	 */
	public void exportJYRX() throws Exception {
		this.exportTJRX("初步建议人选征求省纪委名单.xls");
		
	}
	/**
	 * 分管市领导的通气材料
	 * @throws Exception 
	 */
	public void exportTQCL() throws Exception {
		File tplfilepath=null;
		File newFile = null;
		InputStream is = null;  //读取复制好的文件
		
		Workbook workbook = null;      //默认读取2003版的Excel
		//获取sheet页总数
		//int sheetNums = workbook.getNumberOfSheets();
		
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String cur_hj = pm.request.getAttribute("cur_hj").toString();//批次
		String extxlsName = "（任免职）";
		if(RMHJ.MIN_ZHU_TUI_JIAN.equals(cur_hj)){
			extxlsName = "";
		}
		String sql = ExcelStyleUtil.getSqlTQCL(rbId,cur_hj);
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
	


		Sheet sheet = null;
		
		//String tmp = null;
		CellStyle style = null;
		int rowIndex = 6;
		int i=0;
		Object prevValue = null;
		List<Map<String, String>> typelist = null;
		Map<String, String> typemap = null;
		
		
		String oldLD = "";
		String curLD = "";
		int workbookIndex = 0;
		while (rs.next()) {
			curLD = rs.getString("dc003")==null?"":rs.getString("dc003");
			if(workbookIndex!=0&&!oldLD.equals(curLD)){//上一个领导和下一个领导不一样 这里结束一个excel表格
				for(int ri=0;ri<typelist.size();ri++){
					Map<String, String> m = typelist.get(ri);
					int rownum = Integer.valueOf(m.get("rowindex"));
					String rowvalue = m.get("rowvalue").toString();
					String dc006 = m.get("dc006").toString();
					int size = Integer.valueOf(m.get("size"));
					
					
					int sizeNext = 0;
					if(ri==typelist.size()-1){
						sizeNext = i+1;
					}else{
						sizeNext = Integer.valueOf(typelist.get(ri+1).get("size"));
					}
					//这个就是合并单元格
					//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
					ExcelStyleUtil.mergeRegionAndSetValue(sheet,workbook, 0, 6, rownum,
							NumberFormatUtil.formatInteger(ri+1)+"、"+rowvalue+"（"+(sizeNext-size)+"人）"+dc006);
					
					
				}
				File opp = new File(outputpath);
				if(!opp.isDirectory()){
					opp.mkdirs();
				}
				String fn = workbookIndex+oldLD+"通气材料"+extxlsName+".xls";
				this.outputFileNameList.add(fn);
		        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
		        //这个就就是弹出下载对话框的关键代码
		        BufferedOutputStream bos = new BufferedOutputStream(fos);
		        workbook.write(bos);
		        
		        bos.flush();
		        fos.close();
		        bos.close();
			}
			
			
			
			if(workbookIndex==0||!oldLD.equals(curLD)){//新生成一个workbook
				typelist = new ArrayList<Map<String,String>>();
				typemap = new HashMap<String, String>();
				rowIndex = 6;
				workbookIndex++;
				tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/通气材料.xls").getPath());
				newFile = tplfilepath;
				is = new FileInputStream(newFile);  //读取复制好的文件
				prevValue = null;
				workbook = new HSSFWorkbook(is);      //默认读取2003版的Excel
				sheet = workbook.getSheetAt(0);
				style = ExcelStyleUtil.getCellStyle(workbook);
				i=0;
				
				//日期
				Row rowdate = sheet.getRow(4);
				Cell celldate = rowdate.getCell(5);
				celldate.setCellValue(new SimpleDateFormat("yyyy年M月dd日").format(new Date()) );
				//领导
				celldate = rowdate.getCell(0);
				celldate.setCellValue("通气领导："+curLD);
			}
			
			i++;
			//字的个数大于30个就不设置行高。
			ExcelReturnParam setRowHeight = new ExcelReturnParam(true,6);
			
			Object value = rs.getObject("jsdw002")==null?"":rs.getObject("jsdw002");
			if(prevValue==null||!(prevValue.equals(value))){//类别
				Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
				rowIndex++;
				String dc006 = rs.getObject("dc006")==null?"":("("+rs.getObject("dc006").toString()+")");
				typemap = new HashMap<String, String>();
				typemap.put("rowindex", row.getRowNum()+"");
				typemap.put("rowvalue", value.toString());
				typemap.put("dc006", dc006);
				typemap.put("size", i+"");
				typelist.add(typemap);
				prevValue = rs.getObject("jsdw002");
				
			}
			
			Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
			Cell cell = row.createCell(0);//序号
			ExcelStyleUtil.setCellValue(cell, style, i+"");
			cell = row.createCell(1);//姓名
			value = rs.getObject("a0101");
			String a0141 = rs.getString("a0141");//第一党派
			String a0104 = rs.getString("a0104");//性别
			String desc = ExcelStyleUtil.formatName(a0141, a0104);
			ExcelStyleUtil.setCellValue(cell, style, value==null?"":(value+desc),setRowHeight);
			
			cell = row.createCell(2);//出生年月
			value = rs.getObject("a0107");
			value = ExcelStyleUtil.formatDate(value==null?"":value.toString());
			ExcelStyleUtil.setCellValue(cell, style, value,setRowHeight.setRowHeightLength(12));
			
			cell = row.createCell(3);//学历职称
			value = rs.getObject("zgxl")==null?"":rs.getObject("zgxl");
			ExcelStyleUtil.setCellValue(cell, style, value,setRowHeight.setRowHeightLength(10));
			
			
			cell = row.createCell(4);//现任职务
			value = rs.getObject("js0108");
			ExcelStyleUtil.setCellValue(cell, style, value,setRowHeight.setRowHeightLength(20));
			cell = row.createCell(5);//拟任职务
			value = rs.getObject("js0111");
			ExcelStyleUtil.setCellValue(cell, style, value,setRowHeight);
			cell = row.createCell(6);
			value = "";
			ExcelStyleUtil.setCellValue(cell, style, value);
			
			if(setRowHeight.isSetRowHeight()){
				row.setHeightInPoints((short)40);//行高
			}
			rowIndex++;
			
			oldLD = rs.getString("dc003")==null?"":rs.getString("dc003");//把当前领导赋给上一个领导
		}
		
		
		if(typelist!=null){
			//循环结束
			for(int ri=0;ri<typelist.size();ri++){
				Map<String, String> m = typelist.get(ri);
				int rownum = Integer.valueOf(m.get("rowindex"));
				String rowvalue = m.get("rowvalue").toString();
				String dc006 = m.get("dc006").toString();
				int size = Integer.valueOf(m.get("size"));
				//这个就是合并单元格
				//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
				
				int sizeNext = 0;
				if(ri==typelist.size()-1){
					sizeNext = i+1;
				}else{
					sizeNext = Integer.valueOf(typelist.get(ri+1).get("size"));
				}
				
				ExcelStyleUtil.mergeRegionAndSetValue(sheet,workbook, 0, 6, rownum,
						NumberFormatUtil.formatInteger(ri+1)+"、"+rowvalue+"（"+(sizeNext-size)+"人）"+dc006);
				
				
			}
			File opp = new File(outputpath);
			if(!opp.isDirectory()){
				opp.mkdirs();
			}
			String fn = workbookIndex+curLD+"通气材料"+extxlsName+".xls";
			this.outputFileNameList.add(fn);
	        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
	        //这个就就是弹出下载对话框的关键代码
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        workbook.write(bos);
	        
	        bos.flush();
	        fos.close();
	        bos.close();
		}
		
		
		
		
	}
	/**
	 * 拟推荐人选征求市纪委意见名单
	 * @param expname 
	 * @throws Exception 
	 */
	public void exportTJRX(String expname) throws Exception {
		File tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/"+expname).getPath());
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //读取复制好的文件
		
		Workbook workbook = new HSSFWorkbook(is);      //默认读取2003版的Excel
		//获取sheet页总数
		//int sheetNums = workbook.getNumberOfSheets();
		Sheet sheet = workbook.getSheetAt(0);
		
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String cur_hj = pm.request.getAttribute("cur_hj").toString();//环节
		String cur_hj_4 = pm.request.getAttribute("cur_hj_4").toString();//讨论决定分环节
		String dc005 = pm.request.getAttribute("dc005").toString();
		String js0100WhereSql = pm.request.getAttribute("js0100WhereSql").toString();
		String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005,js0100WhereSql);
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		//String tmp = null;
		CellStyle style = ExcelStyleUtil.getCellStyle(workbook);
		
		int rowIndex = 5;
		int i=0;
		while (rs.next()) {
			i++;
			//字的个数大于30个就不设置行高。
			ExcelReturnParam setRowHeight = new ExcelReturnParam(true,30);
			Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
			
			Cell cell = row.createCell(0);//序号
			ExcelStyleUtil.setCellValue(cell, style, i+"");
			
			cell = row.createCell(1);//姓名
			Object value = rs.getObject("a0101");
			String a0141 = rs.getString("a0141");//第一党派
			String a0104 = rs.getString("a0104");//性别
			String desc = ExcelStyleUtil.formatName(a0141, a0104);
			ExcelStyleUtil.setCellValue(cell, style, value==null?"":(value+desc),setRowHeight);
			cell = row.createCell(2);//现任职务
			value = rs.getObject("js0108");
			ExcelStyleUtil.setCellValue(cell, style, value,setRowHeight);
			
			if(setRowHeight.isSetRowHeight()){
				row.setHeightInPoints((short)40);//行高
			}
			rowIndex++;
		}
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		this.outputFileNameList.add(expname);
        OutputStream fos = new  FileOutputStream(new File(outputpath+expname));

   
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
		
	}
	/**
	 * 考察对象民义情况汇总表
	 */
	public void exportKCDX() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 部会干部调配建议方案
	 * @throws Exception 
	 * @throws RadowException 
	 */
	public void exportBHGBTP() throws RadowException, Exception {
		this.exportGBTP("2");
	}
	/**
	 * 书记专题会干部调配建议方案
	 */
	public void exportSJHGBTP() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 常委会干部调配建议方案
	 */
	public void exportCWHGBTP() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 组织考察名单
	 * @param
	 * @throws RadowException
	 * @throws Exception
	 */
	public void exportZZKCMD() throws RadowException,Exception{
		File tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/zzkcmd.xls").getPath());
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //读取复制好的文件
		
		Workbook workbook = new HSSFWorkbook(is);      //默认读取2003版的Excel
		//获取sheet页总数
		//int sheetNums = workbook.getNumberOfSheets();
		Sheet sheet = workbook.getSheetAt(0);
		
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String cur_hj = pm.request.getAttribute("cur_hj").toString();//环节
		String cur_hj_4 = pm.request.getAttribute("cur_hj_4").toString();//讨论决定分环节
		String dc005 = pm.request.getAttribute("dc005").toString();
		String js0100WhereSql = pm.request.getAttribute("js0100WhereSql").toString();
		String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005,js0100WhereSql);
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		//String tmp = null;
		CellStyle style = ExcelStyleUtil.getCellStyle(workbook);

		//日期
		Row rowdate = sheet.getRow(1);//第二行
		Cell celldate = rowdate.getCell(5);//第六列
		//将当前时间添加到第二行第六列
		celldate.setCellValue(new SimpleDateFormat("yyyy年MM月").format(new Date()) );
		int i=0;
		int rowIndex = 3;
		while (rs.next()) {
			String a0101=rs.getString("a0101");
			String js0100=rs.getString("js0100");
			String sql2=" select js0100,dc001,rb_id,js1401,js1402,js1403,js1404,js1405,js1406 "
					  + " from js14 where js0100='"+js0100+"'";
			HashMap<String, Object> map=CommonQueryBS.getMapBySQL(sql2);
			if(map!=null){
				//字的个数大于30个就不设置行高。
				ExcelReturnParam setRowHeight = new ExcelReturnParam(true,30);
				Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
				
				//Cell cell = row.createCell(0);//序号
				//ExcelStyleUtil.setCellValue(cell, style, i+"");
				//sheet.shiftRows(i,sheet.getLastRowNum()+1, 1, true, false);
				if(map.get("js1401")!=null){
					String js1401=map.get("js1401").toString();
					//Cell kcdw=sheet.getRow(i).getCell(0);//考察单位
					Cell kcdw=row.createCell(0);//考察单位
					ExcelStyleUtil.setCellValue(kcdw, style, js1401);
					//kcdw.setCellValue(js1401);
				}else{
					Cell kcdw=row.createCell(0);//考察单位
					ExcelStyleUtil.setCellValue(kcdw, style, "");
				}
				if(map.get("js1402")!=null){
					String js1402=map.get("js1402").toString();
					//Cell kcsj=sheet.getRow(i).getCell(1);//考察时间
					if(js1402.length() >= 6) {
						String a=js1402.substring(2, 4);
						String b=js1402.substring(4, 6);
						Cell kcsj=row.createCell(1);//考察时间
						ExcelStyleUtil.setCellValue(kcsj, style,a+"."+b);
						//kcsj.setCellValue(DateUtil.numDateToStr1(js1402));
					} else {
						Cell kcsj=row.createCell(1);//考察时间
						ExcelStyleUtil.setCellValue(kcsj, style,"");
					}
					
				}else{
					Cell kcsj=row.createCell(1);//考察时间
					ExcelStyleUtil.setCellValue(kcsj, style,"");
				}
				
				//Cell kcdx=sheet.getRow(i).getCell(2);//考察对象
				Cell kcdx=row.createCell(2);//考察对象
				//kcdx.setCellValue(a0101);
				ExcelStyleUtil.setCellValue(kcdx, style, a0101);
				
				if(map.get("js1403")!=null){
					String js1403=map.get("js1403").toString();
					//Cell mzcp=sheet.getRow(i).getCell(3);//民主测评、评议范围和要求
					Cell mzcp=row.createCell(3);//民主测评、评议范围和要求
					ExcelStyleUtil.setCellValue(mzcp, style, js1403);
					//mzcp.setCellValue(js1403);
				}else{
					Cell mzcp=row.createCell(3);//民主测评、评议范围和要求
					ExcelStyleUtil.setCellValue(mzcp, style, "");
				}
				if(map.get("js1404")!=null){
					String js1404=map.get("js1404").toString();
					//Cell gbthdx=sheet.getRow(i).getCell(4);//个别谈话对象范围
					Cell gbthdx=row.createCell(4);//个别谈话对象范围
					ExcelStyleUtil.setCellValue(gbthdx, style, js1404);
					//gbthdx.setCellValue(js1404);
					
				}else{
					Cell gbthdx=row.createCell(4);//个别谈话对象范围
					ExcelStyleUtil.setCellValue(gbthdx, style, "");
				}
				if(map.get("js1405")!=null){
					String js1405=map.get("js1405").toString();
					//Cell kczcy=sheet.getRow(i).getCell(5);//考察组成员
					Cell kczcy=row.createCell(5);//考察组成员
					ExcelStyleUtil.setCellValue(kczcy, style, js1405);
					//kczcy.setCellValue(js1405);
					
				}else{
					Cell kczcy=row.createCell(5);//考察组成员
					ExcelStyleUtil.setCellValue(kczcy, style, "");
				}
				if(map.get("js1406")!=null){
					String js1406=map.get("js1406").toString();
					//Cell remark=sheet.getRow(i).getCell(6);//备注
					Cell remark=row.createCell(6);//备注
					ExcelStyleUtil.setCellValue(remark, style, js1406);
					//remark.setCellValue(js1406);
				}else{
					Cell remark=row.createCell(6);//备注
					ExcelStyleUtil.setCellValue(remark, style, "");
				}
				
				rowIndex++;
				
			}
			
			
		}
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "考察方案.xls";
		this.outputFileNameList.add(fn);
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
	}
	
	/**
	 * 常委会票决票
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws AppException 
	 */
	public void exportPJP() throws Exception {
		File tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/省委常委会干部任免表决票.xls").getPath());
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //读取复制好的文件
		
		Workbook workbook = new HSSFWorkbook(is);      //默认读取2003版的Excel
		//获取sheet页总数
		//int sheetNums = workbook.getNumberOfSheets();
		Sheet sheet = workbook.getSheetAt(0);
		
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String cur_hj = pm.request.getAttribute("cur_hj").toString();//环节
		String cur_hj_4 = pm.request.getAttribute("cur_hj_4").toString();//讨论决定分环节
		cur_hj_4 = RMHJ.TAO_LUN_JUE_DING+"_3";
		String dc005 = pm.request.getAttribute("dc005").toString();
		String js0100WhereSql = pm.request.getAttribute("js0100WhereSql").toString();
		String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005, js0100WhereSql);
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		//String tmp = null;
		CellStyle style = ExcelStyleUtil.getCellStyle(workbook);
		//日期
		Row rowdate = sheet.getRow(4);
		Cell celldate = rowdate.getCell(3);
		celldate.setCellValue(new SimpleDateFormat("yyyy年M月dd日").format(new Date()) );
		int rowIndex = 7;
		while (rs.next()) {
			//字的个数大于30个就不设置行高。
			ExcelReturnParam setRowHeight = new ExcelReturnParam(true,30);
			Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
			
			Cell cell = row.createCell(0);//姓名
			Object value = rs.getObject("a0101");
			String a0141 = rs.getString("a0141");//第一党派
			String a0104 = rs.getString("a0104");//性别
			String desc = ExcelStyleUtil.formatName(a0141, a0104);
			ExcelStyleUtil.setCellValue(cell, style, value==null?"":(value+desc),setRowHeight.setRowHeightLength(15));
			cell = row.createCell(1);//现任职务
			value = rs.getObject("js0108");
			ExcelStyleUtil.setCellValue(cell, style, value,setRowHeight);
			cell = row.createCell(2);//拟任职务
			value = rs.getObject("js0111");
			ExcelStyleUtil.setCellValue(cell, style, value,setRowHeight);
			cell = row.createCell(3);
			value = "";
			ExcelStyleUtil.setCellValue(cell, style, value);
			cell = row.createCell(4);//
			ExcelStyleUtil.setCellValue(cell, style, value);
			cell = row.createCell(5);//
			ExcelStyleUtil.setCellValue(cell, style, value);
			if(setRowHeight.isSetRowHeight()){
				row.setHeightInPoints((short)40);//行高
			}
			rowIndex++;
		}
		
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "市委常委会干部任免表决表.xls";
		this.outputFileNameList.add(fn);
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
		
	}
	/**
	 * 常委会票决票情况
	 * @throws Exception 
	 */
	public void exportPJQK() throws Exception {
		File tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/省委常委会干部任免表决计票结果.xls").getPath());
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //读取复制好的文件
		
		Workbook workbook = new HSSFWorkbook(is);      //默认读取2003版的Excel
		//获取sheet页总数
		//int sheetNums = workbook.getNumberOfSheets();
		Sheet sheet = workbook.getSheetAt(0);
		
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String cur_hj = pm.request.getAttribute("cur_hj").toString();//环节
		String cur_hj_4 = pm.request.getAttribute("cur_hj_4").toString();//讨论决定分环节
		cur_hj_4 = RMHJ.TAO_LUN_JUE_DING+"_3";
		String dc005 = pm.request.getAttribute("dc005").toString();
		String js0100WhereSql = pm.request.getAttribute("js0100WhereSql").toString();
		String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005, js0100WhereSql);
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		//String tmp = null;
		CellStyle style = ExcelStyleUtil.getCellStyle(workbook);
		//日期
		Row rowdate = sheet.getRow(6);
		Cell celldate = rowdate.getCell(3);
		celldate.setCellValue(new SimpleDateFormat("yyyy年M月dd日").format(new Date()) );
		int rowIndex = 8;
		while (rs.next()) {
			Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
			row.setHeightInPoints((short)40);//行高
			Cell cell = row.createCell(0);//姓名
			Object value = rs.getObject("a0101");
			String a0141 = rs.getString("a0141");//第一党派
			String a0104 = rs.getString("a0104");//性别
			String desc = ExcelStyleUtil.formatName(a0141, a0104);
			ExcelStyleUtil.setCellValue(cell, style, value==null?"":(value+desc));
			cell = row.createCell(1);
			value = "";
			ExcelStyleUtil.setCellValue(cell, style, value);
			cell = row.createCell(2);//
			ExcelStyleUtil.setCellValue(cell, style, value);
			cell = row.createCell(3);//
			ExcelStyleUtil.setCellValue(cell, style, value);
			rowIndex++;
		}
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "省委常委会干部任免表决计票结果.xls";
		this.outputFileNameList.add(fn);
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
		
	}
	/**
	 * 干部谈话安排方案
	 * @throws Exception 
	 */
	public void exportTHAP() throws Exception {
		File tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/谈话安排建议方案.xls").getPath());
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //读取复制好的文件
		
		Workbook workbook = new HSSFWorkbook(is);      //默认读取2003版的Excel
		//获取sheet页总数
		//int sheetNums = workbook.getNumberOfSheets();
		Sheet sheet = workbook.getSheetAt(0);
		
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String cur_hj = pm.request.getAttribute("cur_hj").toString();//环节
		String cur_hj_4 = pm.request.getAttribute("cur_hj_4").toString();//讨论决定分环节
		String dc005 = pm.request.getAttribute("dc005").toString();
		
		String js0100WhereSql = pm.request.getAttribute("js0100WhereSql").toString();
		String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005,js0100WhereSql);
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		//String tmp = null;
		CellStyle style = ExcelStyleUtil.getCellStyle(workbook);
		//日期
		Row rowdate = sheet.getRow(3);
		Cell celldate = rowdate.getCell(0);
		celldate.setCellValue(new SimpleDateFormat("谈话时间：yyyy年M月  日").format(new Date()) );
		int rowIndex = 7;
		int i=0;
		Object prevValue = null;
		List<Map<String, String>> typelist = new ArrayList<Map<String,String>>();
		Map<String, String> typemap = new HashMap<String, String>();
		while (rs.next()) {
			i++;
			//字的个数大于30个就不设置行高。
			ExcelReturnParam setRowHeight = new ExcelReturnParam(true,30);
			
			Object value = rs.getObject("dc003")==null?"":rs.getObject("dc003");
			if(prevValue==null||!(prevValue.equals(value))){//第一次循环
				Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
				rowIndex++;
				String dc006 = rs.getObject("dc006")==null?"":("("+rs.getObject("dc006").toString()+")");
				typemap = new HashMap<String, String>();
				typemap.put("rowindex", row.getRowNum()+"");
				typemap.put("rowvalue", value.toString());
				typemap.put("dc006", dc006);
				typemap.put("size", i+"");
				typelist.add(typemap);
				prevValue = rs.getObject("dc003");
				
			}
			
			Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
			Cell cell = row.createCell(0);//序号
			ExcelStyleUtil.setCellValue(cell, style, i+"");
			cell = row.createCell(1);//姓名
			value = rs.getObject("a0101");
			String a0141 = rs.getString("a0141");//第一党派
			String a0104 = rs.getString("a0104");//性别
			String desc = ExcelStyleUtil.formatName(a0141, a0104);
			ExcelStyleUtil.setCellValue(cell, style, value==null?"":(value+desc),setRowHeight.setRowHeightLength(15));
			cell = row.createCell(2);//现任职务
			value = rs.getObject("js0108");
			ExcelStyleUtil.setCellValue(cell, style, value,setRowHeight);
			cell = row.createCell(3);//拟任职务
			value = rs.getObject("js0111");
			ExcelStyleUtil.setCellValue(cell, style, value,setRowHeight);
			cell = row.createCell(4);
			value = "";
			ExcelStyleUtil.setCellValue(cell, style, value);
			
			if(setRowHeight.isSetRowHeight()){
				row.setHeightInPoints((short)40);//行高
			}
			rowIndex++;
		}
		for(int ri=0;ri<typelist.size();ri++){
			Map<String, String> m = typelist.get(ri);
			int rownum = Integer.valueOf(m.get("rowindex"));
			String rowvalue = m.get("rowvalue").toString();
			String dc006 = m.get("dc006").toString();
			int size = Integer.valueOf(m.get("size"));
			//这个就是合并单元格
			//参数说明：1：开始行 2：结束行  3：开始列 4：结束列
			
			int sizeNext = 0;
			if(ri==typelist.size()-1){
				sizeNext = i+1;
			}else{
				sizeNext = Integer.valueOf(typelist.get(ri+1).get("size"));
			}
			
			ExcelStyleUtil.mergeRegionAndSetValue(sheet,workbook, 0, 4, rownum,
					NumberFormatUtil.formatInteger(ri+1)+"、"+rowvalue+"（"+(sizeNext-size)+"人）"+dc006);
			
			
		}
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "谈话安排建议方案.xls";
		this.outputFileNameList.add(fn);
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
		
	}
	/**
	 * 公示
	 * @throws RadowException 
	 * @throws IOException 
	 */
	public void exportGS() throws RadowException, IOException {
		
		HBSession sess=HBUtil.getHBSession();
		
		String rbid = pm.request.getAttribute("rbId").toString();//批次
		String js0100WhereSql = pm.request.getAttribute("js0100WhereSql").toString();
/*		String sql = "select a01.*,js_hj.js_sort from a01,js01,js_hj where "
				+ " js_hj.js0100=js01.js0100 and js01.a0000=a01.a0000 and "
				+ " js01.rb_id='"+rbid+"' and js_type='5'"
				+ " "+ExcelStyleUtil.js0100WhereSql+"  order by js_hj.js_sort";*/
		String sql = "select a01.*,js_hj.js_sort from a01,js01,js_hj where "
				+ " js_hj.js0100=js01.js0100 and js01.a0000=a01.a0000 and "
				+ " js01.rb_id='"+rbid+"'"
				+ " "+js0100WhereSql+"  order by js_hj.js_sort";
		List<A01> ads = (List<A01>)sess.createSQLQuery(sql).addEntity(A01.class).list();
		
		for(int i=0;i<ads.size();i++){
			A01 ad = ads.get(i);
			String id=ad.getA0000();
			String a0101 = ad.getA0101();
			String a0104 = ad.getA0104();			
			if("2".equals(a0104)){
				a0104="女";
			}else{
				a0104="男";
			}
			String a0117=ad.getA0117();	
			if(a0117!=null){
				String s = "SELECT CODE_NAME FROM CODE_VALUE WHERE CODE_TYPE = 'GB3304' AND CODE_VALUE = '"+a0117+"'";
				a0117 = sess.createSQLQuery(s).uniqueResult().toString();
			}
			String a0107=ad.getA0107();	
			if(a0107!=null){
				String year = a0107.substring(0, 4);
				String month = a0107.substring(4, 6);
				a0107 = year+"年"+month+"月出生";
			}
			//String a0111=ad.getA0111()+"人";	
			String a0111 = ad.getComboxArea_a0111()==null?"":ad.getComboxArea_a0111();
			a0111 = a0111+"人";
						
			String a0140=ad.getA0140();	
			if(a0140!=null){
				String year = a0140.substring(0, 4);
				String month = a0140.substring(5, 7);
				a0140 = year+"年"+month+"月加入中国共产党，";
			}else{
				a0140="";
			}
			String a0134=ad.getA0134();	
			if(a0134!=null){
				String year = a0134.substring(0, 4);
				String month = a0134.substring(4, 6);
				a0134 = year+"年"+month+"月参加工作，";
			}else{
				a0134="";
			}
			
			String xl = null;       //学历
			String zgxl = ad.getZgxl()==null?"":ad.getZgxl();
			
			xl = zgxl+"学历";
			
			String a0192a = ad.getA0192a();
			if(a0192a!=null){
				a0192a="现任"+a0192a;
			}else{
				a0192a="";
			}
			
			//String photo = null;      
			A57 a57=(A57) sess.createQuery("from A57 where a0000 = '"+id+"'").uniqueResult();
			String imgFile = "";
			if(a57!=null&&a57.getPhotopath()!=null){
				imgFile = PhotosUtil.PHOTO_PATH+ a57.getPhotopath()+ a57.getPhotoname();
			}else{
				imgFile = pm.request.getSession().getServletContext().getRealPath("/rmb/images/head_pic.png");
			}							
			InputStream in = null;
			byte[] data = null;
			File file=new File(imgFile);
			if(file.exists()) {
				in = new FileInputStream(imgFile);
			}else{
				in = new FileInputStream(pm.request.getSession().getServletContext().getRealPath("/rmb/images/head_pic.png"));
			}
			
			data = new byte[in.available()];
			in.read(data);
			in.close();
			BASE64Encoder encoder = new BASE64Encoder();  
			String strphotos = encoder.encode(data);
			
			//String nr = null;
			String nrsql = "select js0111 from js01,js_hj where js_hj.js0100=js01.js0100 and "
				+ " js01.rb_id='"+rbid+"' and js_type='5' and a0000='"+id+"'";
			Object nr = sess.createSQLQuery(nrsql).uniqueResult();
			if(nr==null){
				nr="";
			}
			
			
			Date date =new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			String dqtime=sdf.format(date);
			Map<String ,Object> tmpData =new HashMap<String,Object>();
			tmpData.put("a0101", a0101);            //姓名
			tmpData.put("a0104", a0104);             //性别
			tmpData.put("a0117", a0117);              //民族
			tmpData.put("a0107", a0107);             //出生年月
			tmpData.put("a0111", a0111);             //籍贯
			tmpData.put("a0140", a0140);             //入党
			tmpData.put("a0134", a0134);             //
			tmpData.put("xl", xl);             //
			tmpData.put("a0192a", a0192a);     
			tmpData.put("photos", strphotos); 
			tmpData.put("nr", nr); 
			
			String filePath = this.outputpath;
			String docname=(i+1)+a0101+"同志外部公示.doc";
			this.outputFileNameList.add(docname);
			String upfile=filePath+docname;
			
			try {
				Configuration configuration= new Configuration();
				configuration.setDefaultEncoding("UTF-8");
				Template template=null;
				String tempName="file.ftl";
				
				//加载freemarker模版文件
				configuration.setDirectoryForTemplateLoading(new File(pm.request.getSession().getServletContext().getRealPath("/")+"pages\\xbrm\\"));
				//设置对象包装器
				configuration.setObjectWrapper(new DefaultObjectWrapper());
				//设置异常处理器
				configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
				//定义Template对像
				template=configuration.getTemplate(tempName);
				//输出文件
				File outFile=new File(upfile);
				//如果输出文件夹不存在,则创建
				if(!outFile.getParentFile().exists()){
					outFile.getParentFile().mkdirs();
				}
				//将模版和数据模型合并生成文件
				Writer out =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
				//生成文件
				template.process(tmpData, out);
				out.flush();
				out.close();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	
	/**
	 * 公示
	 * @throws RadowException 
	 * @throws IOException 
	 */
	public void exportGS2() throws RadowException, IOException {
		
		HBSession sess=HBUtil.getHBSession();
		
		String rbid = pm.request.getAttribute("rbId").toString();//批次
		String js0100WhereSql = pm.request.getAttribute("js0100WhereSql").toString();
		
		String sql = "select a01.a0000,a01.a0101,a0104,a0117,a0107,a0111a,a0140,a0134,a0192a,js_hj.js_sort,'' as v_xt from a01,js01,js_hj where "
				+ " js_hj.js0100=js01.js0100 and js01.a0000=a01.a0000 and "
				+ " js01.rb_id='"+rbid+"' "
				+ " "+js0100WhereSql+"  order by js_hj.js_sort";
		List<VJSA01> ads = (List<VJSA01>)sess.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(VJSA01.class)).list();
		String sql2 = "select a01.a0000,a01.a0101,a0104,a0117,a0107,a0111a,a0140,a0134,a0192a,to_char(v_xt) as v_xt,zgxl,js_hj.js_sort from v_js_a01 a01,js01,js_hj where "
				+ " js_hj.js0100=js01.js0100 and js01.a0000=a01.a0000 and js0122 in ('2','3','4') and "
				+ " js0122=a01.v_xt and js01.rb_id='"+rbid+"'"
				+ " "+js0100WhereSql+"  order by js_sort";
		List<VJSA01> ads2 = (List<VJSA01>)sess.createSQLQuery(sql2).setResultTransformer(Transformers.aliasToBean(VJSA01.class)).list();
		/*System.out.println(sql);*/
		ads.addAll(ads2);
		for(int i=0;i<ads.size();i++){
			VJSA01 ad = ads.get(i);
			String id=ad.getA0000();
			String a0101 = ad.getA0101();
			String a0104 = ad.getA0104();			
			Character vxt = ad.getV_XT();
			
			if("2".equals(a0104)){
				a0104="女";
			}else{
				a0104="男";
			}
			String a0117=ad.getA0117();	
			if(a0117!=null){
				String s = "SELECT CODE_NAME FROM CODE_VALUE WHERE CODE_TYPE = 'GB3304' AND CODE_VALUE = '"+a0117+"'";
				a0117 = sess.createSQLQuery(s).uniqueResult().toString();
			}
			String a0107=ad.getA0107();	
			if(a0107!=null){
				String year = a0107.substring(0, 4);
				String month = a0107.substring(4, 6);
				a0107 = year+"年"+month+"月出生";
			}
			//String a0111=ad.getA0111()+"人";	
			String a0111 = ad.getA0111A()==null?"":ad.getA0111A();
			a0111 = a0111+"人";
						
			String a0140=ad.getA0140();	
			if(a0140!=null&& a0140.length()>=8){
				String year = a0140.substring(0, 4);
				String month = a0140.substring(5, 7);
				a0140 = year+"年"+month+"月加入中国共产党，";
			}else{
				a0140="";
			}
			String a0134=ad.getA0134();	
			if(a0134!=null){
				String year = a0134.substring(0, 4);
				String month = a0134.substring(4, 6);
				a0134 = year+"年"+month+"月参加工作，";
			}else{
				a0134="";
			}
			
			String xl = null;       //学历
			String zgxl = ad.getZGXL()==null?"":ad.getZGXL();
			
			xl = zgxl+"学历";
			
			String a0192a = ad.getA0192A();
			if(a0192a!=null){
				a0192a="现任"+a0192a;
			}else{
				a0192a="";
			}
			
			//String photo = null;      
			A57 a57 = null;
			if(vxt!=null && (vxt.charValue()=='2' || vxt.charValue()=='3' || vxt.charValue()=='4')) {
				List<A57> lista57 = sess.createSQLQuery("select * from v_js_a57 where a0000='"+id+"' and v_xt='"+vxt.charValue()+"'").addEntity(A57.class).list();
				if(lista57.size() > 0) {
					a57 = lista57.get(0);
				}
			} else {
				a57=(A57) sess.createQuery("from A57 where a0000 = '"+id+"'").uniqueResult();
			}
			
			String imgFile = "";
			if(a57!=null&&a57.getPhotopath()!=null){
				if(vxt!=null && (vxt.charValue()=='2' || vxt.charValue()=='3' || vxt.charValue()=='4')) {
					imgFile = PhotosUtil.PHOTO_PATH +"qcjs/"+vxt.toString()+"/"+ a57.getPhotopath()+ a57.getPhotoname();
				} else {
					imgFile = PhotosUtil.PHOTO_PATH + a57.getPhotopath()+ a57.getPhotoname();
				}
			}else{
				imgFile = pm.request.getSession().getServletContext().getRealPath("/rmb/images/head_pic.png");
			}							
			InputStream in = null;
			byte[] data = null;
			File file=new File(imgFile);
			if(file.exists()) {
				in = new FileInputStream(imgFile);
			}else{
				in = new FileInputStream(pm.request.getSession().getServletContext().getRealPath("/rmb/images/head_pic.png"));
			}
			
			data = new byte[in.available()];
			in.read(data);
			in.close();
			BASE64Encoder encoder = new BASE64Encoder();  
			String strphotos = encoder.encode(data);
			
			//String nr = null;
			/*String nrsql = "select js0111 from js01,js_hj where js_hj.js0100=js01.js0100 and "
				+ " js01.rb_id='"+rbid+"' and js_type='5' and a0000='"+id+"'";*/
			String nrsql = "select js0111 from js01,js_hj where js_hj.js0100=js01.js0100 and "
					+ " js01.rb_id='"+rbid+"' and a0000='"+id+"'";
			Object nr = sess.createSQLQuery(nrsql).uniqueResult();
			if(nr==null){
				nr="";
			}
			
			
			Date date =new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			String dqtime=sdf.format(date);
			Map<String ,Object> tmpData =new HashMap<String,Object>();
			tmpData.put("a0101", a0101);            //姓名
			tmpData.put("a0104", a0104);             //性别
			tmpData.put("a0117", a0117);              //民族
			tmpData.put("a0107", a0107);             //出生年月
			tmpData.put("a0111", a0111);             //籍贯
			tmpData.put("a0140", a0140);             //入党
			tmpData.put("a0134", a0134);             //
			tmpData.put("xl", xl);             //
			tmpData.put("a0192a", a0192a);     
			tmpData.put("photos", strphotos); 
			tmpData.put("nr", nr); 
			
			String filePath = this.outputpath;
			String docname=(i+1)+a0101+"同志外部公示.doc";
			this.outputFileNameList.add(docname);
			String upfile=filePath+docname;
			
			try {
				Configuration configuration= new Configuration();
				configuration.setDefaultEncoding("UTF-8");
				Template template=null;
				String tempName="file.ftl";
				
				//加载freemarker模版文件
				configuration.setDirectoryForTemplateLoading(new File(pm.request.getSession().getServletContext().getRealPath("/")+"pages\\xbrm\\"));
				//设置对象包装器
				configuration.setObjectWrapper(new DefaultObjectWrapper());
				//设置异常处理器
				configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
				//定义Template对像
				template=configuration.getTemplate(tempName);
				//输出文件
				File outFile=new File(upfile);
				//如果输出文件夹不存在,则创建
				if(!outFile.getParentFile().exists()){
					outFile.getParentFile().mkdirs();
				}
				//将模版和数据模型合并生成文件
				Writer out =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
				//生成文件
				template.process(tmpData, out);
				out.flush();
				out.close();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	
	public void exportTQCLRMZ() throws Exception {//任免职
		this.exportTQCL();
	}
	
	/**
	 * 通用sql
	 * @param cur_hj
	 * @param cur_hj_4
	 * @param rbId
	 * @param dc005
	 * @return
	 */
	public static String getSql_2(String cur_hj,String cur_hj_4,String rbId,String dc005, String js0100WhereSql_){
		String ref_dc001 = "js01.dc001";
		String orderbyfield = " JS_SORT";
		//String dc001_alias = "dc001";
		//String f
		if(RMHJ.TAO_LUN_JUE_DING.equals(cur_hj)){
			cur_hj = cur_hj_4;
			if(RMHJ.TAO_LUN_JUE_DING.equals(cur_hj)){
				orderbyfield = " JS_SORT4";
			}
		}
		if(RMHJ.JI_BEN_QING_KUANG.equals(cur_hj)){
			cur_hj = RMHJ.DONG_YI;
			
		}
		String hj4sql = " and js_type like '"+cur_hj+"%' ";
		if(RMHJ.DONG_YI.equals(cur_hj)){
			hj4sql = "";
			orderbyfield = " js0113";
		}
		
		if(RMHJ.REN_MIAN_ZHI.equals(cur_hj)&&RMHJ.TAN_HUA_AN_PAI.equals(dc005)){
			orderbyfield = " JS_SORT_dc005_2";
			ref_dc001 = "js_hj.JS_CLASS_DC001_2";
			//dc001_alias = "dc001_2";
		}
		String js0100WhereSql = "";
        if (!"".equals(js0100WhereSql_)) {
        	if(!js0100WhereSql_.contains("js01.")) {
        		js0100WhereSql = " and js01.js0100 in('" + js0100WhereSql_ + "') ";
        	}
        } else {
            js0100WhereSql = "";
        }
		/*a01.a0101,a01.a0192,m.js0111,m.js0117,m.js0109,m.js0100,a01.a0104a,"
				+ "a01.a0107,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,"
				+ "a01.fcsj,js02.js0206*/
		
		String sql = "select * from (select distinct js01.js0109,a01.a0000,js_hj.js0100,js01.js0108,js01.js0111,js01.js0117,a01.a0107,a01.zgxl,a01.a0196,"
				/*+ " nvl((select dc003 from deploy_classify t where t.dc001="+ref_dc001+"),'其他') dc003,"
				+ " (select dc006 from deploy_classify t where t.dc001="+ref_dc001+") dc006,"
				+ " a0101,(select dc004 from deploy_classify t where t.dc001="+ref_dc001+") dc004,"*/
				+orderbyfield
				+ " ,a01.a0141,a01.a0104,js01.js0122,a01.a0192,a01.a0104a,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,a01.fcsj,js01.js0124 "
				+ " ,a01.a0101, a01.a0221,a01.a0288,a01.a0192e,a01.a0192c, "
				+ " (select min(decode(LENGTH(A0243),6,A0243||'01',A0243)) from a02 a,jggwconf f where get_b0111ns(a.a0201b)=f.b0111 and a.a0215a_c=f.gwcode and zwcode='1A32' and a.a0000=a01.a0000 and a.a0219='1') A0243a,  "
				+ " (select min(decode(LENGTH(A0243),6,A0243||'01',A0243)) from a02 a,jggwconf f where get_b0111ns(a.a0201b)=f.b0111 and a.a0215a_c=f.gwcode and zwcode='1A32' and a.a0000=a01.a0000 and a.a0219='2') A0243b  "
				
				+ " from a01,js01,js_hj where js01.js0122 = '1' and "
				+ " a01.a0000=js01.a0000 and nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and js01.rb_id='"+rbId+"'  "+hj4sql +js0100WhereSql 
				+" union select distinct js01.js0109,a01.a0000,js_hj.js0100,js01.js0108,js01.js0111,js01.js0117,a01.a0107,a01.zgxl,a01.a0196,"
				/* +" nvl((select dc003 from deploy_classify t where t.dc001="+ref_dc001+"),'其他') dc003,"
				+ " (select dc006 from deploy_classify t where t.dc001="+ref_dc001+") dc006,"
				+ " a0101,(select dc004 from deploy_classify t where t.dc001="+ref_dc001+") dc004,"*/
				+orderbyfield
				+ " ,a01.a0141,a01.a0104,js01.js0122,a01.a0192,a01.a0104a,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,a01.fcsj,js01.js0124 "
				+ " ,a01.a0101, a01.a0221,a01.a0288,a01.a0192e,a01.a0192c, "
				+ " (select min(decode(LENGTH(A0243),6,A0243||'01',A0243)) from v_js_a02 a where a.a0000=a01.a0000 and a.v_xt=a01.v_xt and A0255='1') A0243a, "
				+ " '' A0243b "
				
				+ " from v_js_a01 a01,js01,js_hj where a01.a0000=js01.a0000 and "
				+ " js01.js0122 = a01.v_xt and js0122 in ('2','3','4') and "
				+ " nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and js01.rb_id='"+rbId+"'  "+hj4sql +js0100WhereSql +""
				+ ")order by "+orderbyfield;
		return sql;
		//distinct a01.a0101, js01.js0108,js01.js0111,"+orderbyfield
	}

	/**
	 * 导出讨论任免干部名单
	 * @throws IOException 
	 */
	public void exportTLRMGBMD(String rbapprove , String js0100WhereSql, String tljlbxzlx) throws RadowException,Exception{
		String cur_hj_4 = pm.request.getAttribute("cur_hj_4").toString();//讨论决定分环节
		File tplfilepath = null;
		//通过讨论决定环节判断是部务会还是常委会
		if("4_1".equals(tljlbxzlx)){//部务会
			 tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/bwhtlrmmd.xls").getPath());
		}else if("4_3".equals(tljlbxzlx)){//常委会
			 tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/cwhtlrmmd.xls").getPath());
		}
	   
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //读取复制好的文件
		
		Workbook workbook = new HSSFWorkbook(is);      //默认读取2003版的Excel
		//获取sheet页总数
		Sheet sheet = workbook.getSheetAt(0);
		
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String cur_hj = pm.request.getAttribute("cur_hj").toString();//环节
		
		String dc005 = pm.request.getAttribute("dc005").toString();
		/*String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005);
		sql=" select t1.a0101,t1.a0192,t1.a0104,t1.qrzxl,t1.a0111a,t1.a0134,t1.a0140,t1.zcsj,t1.fcsj,"
				  + " m.js0111,m.js0117,m.js0109,m.js0100,m.a0107,"
				  + " t2.dc001,t2.rb_id,t2.js1501,t2.js1502,t2.js1503,t2.js1504,t2.js1505,"
				  + " t2.js1506,t2.js1507,t2.js1508,t2.js1509,t2.js1510 "
				  + " from ("+sql+") m,js15 t2 where t1.a0000=m.a0000 and m.js0100 = t2.js0100";*/
		
		String sql = getSql_2(cur_hj, cur_hj_4, rbId,dc005,js0100WhereSql);
		if(rbapprove.equals("2")) {
			sql=" select m.a0101,m.a0192,m.a0104,m.qrzxl,m.a0111a,m.a0134,m.a0140,m.zcsj,m.fcsj,"
					  + " m.js0111,m.js0117,m.js0109,m.js0100,m.a0107,"
					  + " t2.dc001,t2.rb_id,t2.js1501,t2.js1502,t2.js1503,t2.js1504,t2.js1505,"
					  + " t2.js1506,t2.js1507,t2.js1508,t2.js1509,t2.js1510,m.js0124"
					  + " ,m.a0221,m.a0288,m.a0192e,m.a0192c,m.a0243a,m.a0243b,js0122 "
					  + " from ("+sql+") m,js15 t2,js02 t3 where m.js0100 = t2.js0100 and "
					  + " m.js0100 = t3.js0100 and ((js0208='1' and js0211='1') or js0208='2') ";
		} else {
			sql=" select m.a0101,m.a0192,m.a0104,m.qrzxl,m.a0111a,m.a0134,m.a0140,m.zcsj,m.fcsj,"
					  + " m.js0111,m.js0117,m.js0109,m.js0100,m.a0107,"
					  + " t2.dc001,t2.rb_id,t2.js1501,t2.js1502,t2.js1503,t2.js1504,t2.js1505,"
					  + " t2.js1506,t2.js1507,t2.js1508,t2.js1509,t2.js1510,m.js0124 "
					  + " ,m.a0221,m.a0288,m.a0192e,m.a0192c,m.a0243a,m.a0243b,js0122 "
					  + " from ("+sql+") m,js15 t2 where  m.js0100 = t2.js0100";
		}
		
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		//List<HashMap<String,Object>> list=CommonQueryBS.getListBySQL(sql);
		CellStyle style = ExcelStyleUtil.getCellStyle(workbook);
		
		//日期
		Row rowdate = sheet.getRow(1);//第2行
		Cell celldate = rowdate.getCell(10);//第11列
		//将当前时间添加到第2行第11列
		celldate.setCellValue(new SimpleDateFormat("yyyy年MM月").format(new Date()) );
		int rowIndex = 3;
		String reg = "^([0-9]{4}[.][0-9]{2})$";
		String reg2 = "([0-9]{4}[.][0-9]{2})";
		while(rs.next()){
			
			String a0101=rs.getString("a0101");//人员姓名
			String a0192=rs.getString("a0192");//现任职务
			String js0111=rs.getString("js0111");//拟任职务
			String js0117=rs.getString("js0117");//拟免职务
			
			String a0104=rs.getString("a0104");//性别
			String a0107=rs.getString("a0107");//出生日期
		    a0107=a0107.substring(2, 4)+"."+a0107.substring(4, 6);
			String qrzxl=rs.getString("qrzxl");//全日制学历
			String a0111a=rs.getString("a0111a");//籍贯
			a0111a = a0111a.length()==4 ? a0111a.substring(0, 2) + "\r\n" +a0111a.substring(2): a0111a;
			String a0134=rs.getString("a0134");//工作时间
			a0134=a0134.substring(2, 4)+"."+a0134.substring(4, 6);
			String a0140=rs.getString("a0140");//入党时间
			boolean p = Pattern.matches(reg, a0140);
			if(p) {
				a0140 = a0140.substring(2);
			}
			Pattern pt = Pattern.compile(reg2);
			Matcher m = pt.matcher(a0140);
			while (m.find()) {
	            String mstr = m.group();
	            a0140 = a0140.replace(mstr, mstr.substring(2));
	        } 
			//String zcsj=rs.getString("zcsj");//正处时间
			//String fcsj=rs.getString("fcsj");//副处时间
			String a0221=rs.getString("a0221");//职务层次
			String a0288=rs.getString("a0288");//职务层次时间
			String a0192e=rs.getString("a0192e");//职级
			String a0192c=rs.getString("a0192c");//职级时间
			String a0243a=rs.getString("a0243a");//职务岗位对应县处级副职领导时间
			String a0243b=rs.getString("a0243b");//职务岗位对应县处级副职非领导时间
			String vxt=rs.getString("js0122");//职务岗位对应县处级副职领导时间
			String zcfcsj = "";
			if(vxt.equals("1")) {
				if(a0221.equals("1A41")) {
					if(a0288!=null && !a0288.equals("")) {
						zcfcsj=a0288.substring(2, 4)+"."+a0288.substring(4, 6)+"\r\n" + "科级";
					}
				} else if(a0221.equals("1A32")) {
					if(a0243a!=null && !a0243a.trim().equals("")) {
						zcfcsj=a0243a.substring(2, 4)+"."+a0243a.substring(4, 6) ;
					} else if(a0288!=null && !a0288.equals("")) {
						zcfcsj=a0288.substring(2, 4)+"."+a0288.substring(4, 6);
					}
				} else if(a0221.equals("1A31")) {
					String f = "";
					if(a0243a!=null && !a0243a.trim().equals("")) {
						f = a0243a.substring(2, 4)+"."+a0243a.substring(4, 6) ;
						if(a0288!=null && !a0288.equals("")) {
							String z = a0288.substring(2, 4)+"."+a0288.substring(4, 6);
							if(f.equals(z)) {
								zcfcsj = "("+z+")";
							} else {
								zcfcsj = f+"\r\n("+z+")";
							}
						} else {
							zcfcsj = f;
						}
					} else if(a0243b!=null && !a0243b.trim().equals("")) {
						f = a0243b.substring(2, 4)+"."+a0243b.substring(4, 6) ;
						if(a0288!=null && !a0288.equals("")) {
							String z = a0288.substring(2, 4)+"."+a0288.substring(4, 6);
							if(f.equals(z)) {
								zcfcsj = "("+z+")";
							} else {
								zcfcsj = f+"\r\n("+z+")";
							}
						} else {
							zcfcsj = f;
						}
					} else if(a0288!=null && !a0288.equals("")) {
						zcfcsj = "("+a0288.substring(2, 4)+"."+a0288.substring(4, 6)+")";
					}
				}
			} else {
				if(a0221.equals("1A41")) {
					if(a0288!=null && !a0288.equals("")) {
						zcfcsj=a0288.substring(2, 4)+"."+a0288.substring(4, 6)+"\r\n" + "科级";
					}
				}/* else if(a0221.equals("1A32")) {
					if(a0243a!=null && !a0243a.trim().equals("")) {
						zcfcsj=a0243a.substring(2, 4)+"."+a0243a.substring(4, 6) ;
					} else if(a0288!=null && !a0288.equals("")) {
						zcfcsj=a0288.substring(2, 4)+"."+a0288.substring(4, 6);
					}
				} else if(a0221.equals("1A31")) {
					String f = "";
					if(a0243a!=null && !a0243a.trim().equals("")) {
						f = a0243a.substring(2, 4)+"."+a0243a.substring(4, 6) ;
						if(a0288!=null && !a0288.equals("")) {
							String z = a0288.substring(2, 4)+"."+a0288.substring(4, 6);
							if(f.equals(z)) {
								zcfcsj = "("+z+")";
							} else {
								zcfcsj = f+"\r\n("+z+")";
							}
						} else {
							zcfcsj = f;
						}
					} else if(a0288!=null && !a0288.equals("")) {
						zcfcsj = "("+a0288.substring(2, 4)+"."+a0288.substring(4, 6)+")";
					}
				}*/
			}
			String js0109=rs.getString("js0109");//任现职时间
			js0109=js0109.substring(2, 4)+"."+js0109.substring(4, 6);
			//String js1509=rs.getString("js1509");//备注(部务会)
			//String js1510=rs.getString("js1510");//备注(常委会)
			String js1509=rs.getString("js0124");//备注
			String js1510=js1509;//备注
			
			ExcelReturnParam setRowHeight = new ExcelReturnParam(true,30);
			Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
			
			Cell xm=row.createCell(0);//姓名
			ExcelStyleUtil.setCellValue(xm, style, a0101);
			
			Cell xrzw=row.createCell(1);//现任职务
			ExcelStyleUtil.setCellValue(xrzw, style, a0192);
			
			Cell nrmzw=row.createCell(2);//拟任免职务
			String nrm = "";
			if(js0111!=null && !js0111.equals("")) {
				nrm += "任" + js0111 +",";
			}
			if(js0117!=null && !js0117.equals("")) {
				nrm += "免" + js0117 ;
			}
			ExcelStyleUtil.setCellValue(nrmzw, style, nrm);
			
			Cell xb=row.createCell(3);//性别
			if("1".equals(a0104)){
				ExcelStyleUtil.setCellValue(xb, style,"男");
			}else if("2".equals(a0104)){
				ExcelStyleUtil.setCellValue(xb, style,"女");
			}else{
				ExcelStyleUtil.setCellValue(xb, style,"");
			}
			
			
			Cell csrq=row.createCell(4);//出生年月
			ExcelStyleUtil.setCellValue(csrq, style, a0107);
			
			Cell xl=row.createCell(5);//学历
			ExcelStyleUtil.setCellValue(xl, style, qrzxl);
			
			Cell jg=row.createCell(6);//籍贯
			ExcelStyleUtil.setCellValue(jg, style, a0111a);
			
			Cell gzsj=row.createCell(7);//工作时间
			ExcelStyleUtil.setCellValue(gzsj, style, a0134);
			
			Cell rdsj=row.createCell(8);//入党时间
			ExcelStyleUtil.setCellValue(rdsj, style, a0140);
			
			Cell fzcsj=row.createCell(9);//副处职（正处）时间
			/*if(fcsj!=null&&zcsj!=null){
				ExcelStyleUtil.setCellValue(fzcsj, style, fcsj+","+zcsj);
			}else if(fcsj!=null&&zcsj==null){
				ExcelStyleUtil.setCellValue(fzcsj, style, fcsj);
			}else if(fcsj==null&&zcsj!=null){
				ExcelStyleUtil.setCellValue(fzcsj, style, zcsj);
			}else{
				ExcelStyleUtil.setCellValue(fzcsj, style, "");
			}*/
			ExcelStyleUtil.setCellValue(fzcsj, style, zcfcsj);
			
			
			Cell xzsj=row.createCell(10);//现职时间
			ExcelStyleUtil.setCellValue(xzsj, style, js0109);
			
			Cell remark=row.createCell(11);//备注
			if("4_1".equals(tljlbxzlx)){//部务会
				ExcelStyleUtil.setCellValue(remark, style, js1509);
			}else if("4_3".equals(tljlbxzlx)){//常委会
				ExcelStyleUtil.setCellValue(remark, style, js1510);
			}					
			
			rowIndex++;
			
		}
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn="";
		//通过讨论决定环节判断是部务会还是常委会
		if("4_1".equals(tljlbxzlx)){//部务会
			fn = "部务会讨论任免干部名单.xls";
		}else if("4_3".equals(tljlbxzlx)){//常委会
			fn = "常委会讨论任免干部名单.xls";
		}
			   
		this.outputFileNameList.add(fn);
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
	}
	
	public void exportTLRMGBMD2() throws RadowException,Exception{
		String cur_hj_4 = pm.request.getAttribute("cur_hj_4").toString();//讨论决定分环节
		File tplfilepath = null;
		//通过讨论决定环节判断是部务会还是常委会
		if("4_1".equals(cur_hj_4)){//部务会
			 tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/bwhtlrmmd.xls").getPath());
		}else if("4_3".equals(cur_hj_4)){//常委会
			 tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/cwhtlrmmd.xls").getPath());
		}
	   
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //读取复制好的文件
		
		Workbook workbook = new HSSFWorkbook(is);      //默认读取2003版的Excel
		//获取sheet页总数
		Sheet sheet = workbook.getSheetAt(0);
		
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		/*String cur_hj = pm.request.getAttribute("cur_hj").toString();//环节
		
		String dc005 = pm.request.getAttribute("dc005").toString();*/
		/*String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005);
		sql=" select t1.a0101,t1.a0192,t1.a0104,t1.qrzxl,t1.a0111a,t1.a0134,t1.a0140,t1.zcsj,t1.fcsj,"
				  + " m.js0111,m.js0117,m.js0109,m.js0100,m.a0107,"
				  + " t2.dc001,t2.rb_id,t2.js1501,t2.js1502,t2.js1503,t2.js1504,t2.js1505,"
				  + " t2.js1506,t2.js1507,t2.js1508,t2.js1509,t2.js1510 "
				  + " from ("+sql+") m,js15 t2 where t1.a0000=m.a0000 and m.js0100 = t2.js0100";*/
		String a0200ssql="select rb_id from RECORD_BATCH where rbm_id ='"+rbId+"'";
    	CommonQueryBS cq=new CommonQueryBS();
    	List<HashMap<String, Object>> list = cq.getListBySQL(a0200ssql);
    	String rbids = "";
    	if(list.size()>0){
    		for (int i = 0; i < list.size(); i++) {
    			HashMap<String, Object> map = list.get(i);
    			rbids += "'" + map.get("rb_id") +"',";
			}
    	}
    	rbids = rbids.substring(0, rbids.length()-1);
		String ref_dc001 = "js01.dc001";
		//String orderbyfield = " JS_SORT";
		String sql0 = "select * from (select distinct js01.js0109,a01.a0000,js_hj.js0100,js01.js0108,js01.js0111,js01.js0117,a01.a0107,a01.zgxl,a01.a0196,"
				/*+ " nvl((select dc003 from deploy_classify t where t.dc001="+ref_dc001+"),'其他') dc003,"
				+ " (select dc006 from deploy_classify t where t.dc001="+ref_dc001+") dc006,"
				+ " a0101,(select dc004 from deploy_classify t where t.dc001="+ref_dc001+") dc004"*//*+orderbyfield*/
				+ " a01.a0141,a01.a0104,js01.js0122,a01.a0192,a01.a0104a,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,a01.fcsj "
				+ " ,a01.a0101, a01.a0221,a01.a0288,a01.a0192e,a01.a0192c, "
				+ " (select min(decode(LENGTH(A0243),6,A0243||'01',A0243)) from a02 a,jggwconf f where get_b0111ns(a.a0201b)=f.b0111 and a.a0215a_c=f.gwcode and zwcode='1A32' and a.a0000=a01.a0000 and a.a0219='1') A0243a,  "
				+ " (select min(decode(LENGTH(A0243),6,A0243||'01',A0243)) from a02 a,jggwconf f where get_b0111ns(a.a0201b)=f.b0111 and a.a0215a_c=f.gwcode and zwcode='1A32' and a.a0000=a01.a0000 and a.a0219='2') A0243b  "
				
				+ " from a01,js01,js_hj,js02 where js01.js0122 = '1' and js01.js0100 = js02.js0100 and "
				+ " a01.a0000=js01.a0000 and nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and js01.rb_id in ("+rbids+")  "/*+hj4sql  */
				+" union select distinct js01.js0109,a01.a0000,js_hj.js0100,js01.js0108,js01.js0111,js01.js0117,a01.a0107,a01.zgxl,a01.a0196,"
				/*+ " nvl((select dc003 from deploy_classify t where t.dc001="+ref_dc001+"),'其他') dc003,"
				+ " (select dc006 from deploy_classify t where t.dc001="+ref_dc001+") dc006,"
				+ " a0101,(select dc004 from deploy_classify t where t.dc001="+ref_dc001+") dc004"*//*+orderbyfield*/
				+ " a01.a0141,a01.a0104,js01.js0122,a01.a0192,a01.a0104a,a01.qrzxl,a01.a0111a,a01.a0134,a01.a0140,a01.zcsj,a01.fcsj "
				+ " ,a01.a0101, a01.a0221,a01.a0288,a01.a0192e,a01.a0192c, "
				+ " (select min(decode(LENGTH(A0243),6,A0243||'01',A0243)) from v_js_a02 a where a.a0000=a01.a0000 and a.v_xt=a01.v_xt and A0255='1') A0243a, "
				+ " '' A0243b "
				
				+ " from v_js_a01 a01,js01,js_hj,js02 where js01.js0100 = js02.js0100 and a01.a0000=js01.a0000 and "
				+ " js01.js0122 = a01.v_xt and js0122 in ('2','3','4') and "
				+ " nvl(js01.js0120,'1')!='2' and js_hj.js0100=js01.js0100 "
				+ " and js01.rb_id in ("+rbids+")  " /*+hj4sql */ +")";
				//+ ")order by dc004,"+orderbyfield;
		System.out.println(sql0);
		String sql=" select m.a0101,m.a0192,m.a0104,m.qrzxl,m.a0111a,m.a0134,m.a0140,m.zcsj,m.fcsj,"
				  + " m.js0111,m.js0117,m.js0109,m.js0100,m.a0107,"
				  + " t2.dc001,t2.rb_id,t2.js1501,t2.js1502,t2.js1503,t2.js1504,t2.js1505,"
				  + " t2.js1506,t2.js1507,t2.js1508,t2.js1509,t2.js1510 "
				  + " ,m.a0221,m.a0288,m.a0192e,m.a0192c,m.a0243a,m.a0243b,js0122 "
				  + " from ("+sql0+") m,js15 t2 where  m.js0100 = t2.js0100";
		
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		//List<HashMap<String,Object>> list=CommonQueryBS.getListBySQL(sql);
		CellStyle style = ExcelStyleUtil.getCellStyle(workbook);
		
		//日期
		Row rowdate = sheet.getRow(1);//第2行
		Cell celldate = rowdate.getCell(10);//第11列
		//将当前时间添加到第2行第11列
		celldate.setCellValue(new SimpleDateFormat("yyyy年MM月").format(new Date()) );
		int rowIndex = 3;
		String reg = "^([0-9]{4}[.][0-9]{2})$";
		String reg2 = "([0-9]{4}[.][0-9]{2})";
		while(rs.next()){
			
			String a0101=rs.getString("a0101");//人员姓名
			String a0192=rs.getString("a0192");//现任职务
			String js0111=rs.getString("js0111");//拟任职务
			String js0117=rs.getString("js0117");//拟免职务
			
			String a0104=rs.getString("a0104");//性别
			String a0107=rs.getString("a0107");//出生日期
		    a0107=a0107.substring(2, 4)+"."+a0107.substring(4, 6);
			String qrzxl=rs.getString("qrzxl");//全日制学历
			String a0111a=rs.getString("a0111a");//籍贯
			a0111a = a0111a.length()==4 ? a0111a.substring(0, 2) + "\r\n" +a0111a.substring(2): a0111a;
			String a0134=rs.getString("a0134");//工作时间
			a0134=a0134.substring(2, 4)+"."+a0134.substring(4, 6);
			String a0140=rs.getString("a0140");//入党时间
			boolean p = Pattern.matches(reg, a0140);
			if(p) {
				a0140 = a0140.substring(2);
			}
			Pattern pt = Pattern.compile(reg2);
			Matcher m = pt.matcher(a0140);
			while (m.find()) {
	            String mstr = m.group();
	            a0140 = a0140.replace(mstr, mstr.substring(2));
	        } 
			//String zcsj=rs.getString("zcsj");//正处时间
			//String fcsj=rs.getString("fcsj");//副处时间
			String a0221=rs.getString("a0221");//职务层次
			String a0288=rs.getString("a0288");//职务层次时间
			String a0192e=rs.getString("a0192e");//职级
			String a0192c=rs.getString("a0192c");//职级时间
			String a0243a=rs.getString("a0243a");//职务岗位对应县处级副职领导时间
			String a0243b=rs.getString("a0243b");//职务岗位对应县处级副职非领导时间
			String vxt=rs.getString("js0122");//职务岗位对应县处级副职领导时间
			String zcfcsj = "";
			if(vxt.equals("1")) {
				if(a0221.equals("1A41")) {
					if(a0288!=null && !a0288.equals("")) {
						zcfcsj=a0288.substring(2, 4)+"."+a0288.substring(4, 6)+"\r\n" + "科级";
					}
				} else if(a0221.equals("1A32")) {
					if(a0243a!=null && !a0243a.trim().equals("")) {
						zcfcsj=a0243a.substring(2, 4)+"."+a0243a.substring(4, 6) ;
					} else if(a0288!=null && !a0288.equals("")) {
						zcfcsj=a0288.substring(2, 4)+"."+a0288.substring(4, 6);
					}
				} else if(a0221.equals("1A31")) {
					String f = "";
					if(a0243a!=null && !a0243a.trim().equals("")) {
						f = a0243a.substring(2, 4)+"."+a0243a.substring(4, 6) ;
						if(a0288!=null && !a0288.equals("")) {
							String z = a0288.substring(2, 4)+"."+a0288.substring(4, 6);
							if(f.equals(z)) {
								zcfcsj = "("+z+")";
							} else {
								zcfcsj = f+"\r\n("+z+")";
							}
						} else {
							zcfcsj = f;
						}
					} else if(a0243b!=null && !a0243b.trim().equals("")) {
						f = a0243b.substring(2, 4)+"."+a0243b.substring(4, 6) ;
						if(a0288!=null && !a0288.equals("")) {
							String z = a0288.substring(2, 4)+"."+a0288.substring(4, 6);
							if(f.equals(z)) {
								zcfcsj = "("+z+")";
							} else {
								zcfcsj = f+"\r\n("+z+")";
							}
						} else {
							zcfcsj = f;
						}
					} else if(a0288!=null && !a0288.equals("")) {
						zcfcsj = "("+a0288.substring(2, 4)+"."+a0288.substring(4, 6)+")";
					}
				}
			} else {
				if(a0221.equals("1A41")) {
					if(a0288!=null && !a0288.equals("")) {
						zcfcsj=a0288.substring(2, 4)+"."+a0288.substring(4, 6)+"\r\n" + "科级";
					}
				}/* else if(a0221.equals("1A32")) {
					if(a0243a!=null && !a0243a.trim().equals("")) {
						zcfcsj=a0243a.substring(2, 4)+"."+a0243a.substring(4, 6) ;
					} else if(a0288!=null && !a0288.equals("")) {
						zcfcsj=a0288.substring(2, 4)+"."+a0288.substring(4, 6);
					}
				} else if(a0221.equals("1A31")) {
					String f = "";
					if(a0243a!=null && !a0243a.trim().equals("")) {
						f = a0243a.substring(2, 4)+"."+a0243a.substring(4, 6) ;
						if(a0288!=null && !a0288.equals("")) {
							String z = a0288.substring(2, 4)+"."+a0288.substring(4, 6);
							if(f.equals(z)) {
								zcfcsj = "("+z+")";
							} else {
								zcfcsj = f+"\r\n("+z+")";
							}
						} else {
							zcfcsj = f;
						}
					} else if(a0288!=null && !a0288.equals("")) {
						zcfcsj = "("+a0288.substring(2, 4)+"."+a0288.substring(4, 6)+")";
					}
				}*/
			}
			String js0109=rs.getString("js0109");//任现职时间
			js0109=js0109.substring(2, 4)+"."+js0109.substring(4, 6);
			String js1509=rs.getString("js1509");//备注(部务会)
			String js1510=rs.getString("js1510");//备注(常委会)
			
			
			ExcelReturnParam setRowHeight = new ExcelReturnParam(true,30);
			Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
			
			Cell xm=row.createCell(0);//姓名
			ExcelStyleUtil.setCellValue(xm, style, a0101);
			
			Cell xrzw=row.createCell(1);//现任职务
			ExcelStyleUtil.setCellValue(xrzw, style, a0192);
			
			String nrm = "";
			if(js0111!=null && !js0111.equals("")) {
				nrm += "任" + js0111 ;
				if(js0117!=null && !js0117.equals("")) {
					nrm += "," + "免" + js0117 ;
				}
			} else {
				if(js0117!=null && !js0117.equals("")) {
					nrm += "免" + js0117 ;
				}
			}
			
			Cell nrmzw=row.createCell(2);//拟任免职务
			ExcelStyleUtil.setCellValue(nrmzw, style, nrm);
			
			Cell xb=row.createCell(3);//性别
			if("1".equals(a0104)){
				ExcelStyleUtil.setCellValue(xb, style,"男");
			}else if("2".equals(a0104)){
				ExcelStyleUtil.setCellValue(xb, style,"女");
			}else{
				ExcelStyleUtil.setCellValue(xb, style,"");
			}
			
			
			Cell csrq=row.createCell(4);//出生年月
			ExcelStyleUtil.setCellValue(csrq, style, a0107);
			
			Cell xl=row.createCell(5);//学历
			ExcelStyleUtil.setCellValue(xl, style, qrzxl);
			
			Cell jg=row.createCell(6);//籍贯
			ExcelStyleUtil.setCellValue(jg, style, a0111a);
			
			Cell gzsj=row.createCell(7);//工作时间
			ExcelStyleUtil.setCellValue(gzsj, style, a0134);
			
			Cell rdsj=row.createCell(8);//入党时间
			ExcelStyleUtil.setCellValue(rdsj, style, a0140);
			
			Cell fzcsj=row.createCell(9);//副处职（正处）时间
			/*if(fcsj!=null&&zcsj!=null){
				ExcelStyleUtil.setCellValue(fzcsj, style, fcsj+","+zcsj);
			}else if(fcsj!=null&&zcsj==null){
				ExcelStyleUtil.setCellValue(fzcsj, style, fcsj);
			}else if(fcsj==null&&zcsj!=null){
				ExcelStyleUtil.setCellValue(fzcsj, style, zcsj);
			}else{
				ExcelStyleUtil.setCellValue(fzcsj, style, "");
			}*/
			ExcelStyleUtil.setCellValue(fzcsj, style, zcfcsj);
			
			Cell xzsj=row.createCell(10);//现职时间
			ExcelStyleUtil.setCellValue(xzsj, style, js0109);
			
			Cell remark=row.createCell(11);//备注
			if("4_1".equals(cur_hj_4)){//部务会
				ExcelStyleUtil.setCellValue(remark, style, js1509);
			}else if("4_3".equals(cur_hj_4)){//常委会
				ExcelStyleUtil.setCellValue(remark, style, js1510);
			}					
			
			rowIndex++;
			
		}
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn="";
		//通过讨论决定环节判断是部务会还是常委会
		if("4_1".equals(cur_hj_4)){//部务会
			fn = "部务会讨论任免干部名单.xls";
		}else if("4_3".equals(cur_hj_4)){//常委会
			fn = "常委会讨论任免干部名单.xls";
		}
			   
		this.outputFileNameList.add(fn);
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
	}
	
	/**
	 *导出表决单
	 */
	public void exportBjd() throws RadowException,Exception{
		String cur_hj_4 = pm.request.getAttribute("cur_hj_4").toString();//讨论决定分环节
		File tplfilepath = null;
		//通过讨论决定环节判断是部务会还是常委会
		if("4_1".equals(cur_hj_4)){//部务会
			 tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/bwhbjd.xls").getPath());
		}else if("4_3".equals(cur_hj_4)){//常委会
			 tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/cwhbjd.xls").getPath());
		}
	   
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //读取复制好的文件
		
		Workbook workbook = new HSSFWorkbook(is);      //默认读取2003版的Excel
		//获取sheet页总数
		Sheet sheet = workbook.getSheetAt(0);
		
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String cur_hj = pm.request.getAttribute("cur_hj").toString();//环节
		
		String dc005 = pm.request.getAttribute("dc005").toString();
		String js0100WhereSql = pm.request.getAttribute("js0100WhereSql").toString();
		/*String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005);
		sql=" select t1.a0101,t1.a0192,t1.a0104,t1.qrzxl,t1.a0111a,t1.a0134,t1.a0140,t1.zcsj,t1.fcsj,"
				  + " m.js0111,m.js0117,m.js0109,m.js0100,m.a0107,"
				  + " t2.dc001,t2.rb_id,t2.js1501,t2.js1502,t2.js1503,t2.js1504,t2.js1505,"
				  + " t2.js1506,t2.js1507,t2.js1508,t2.js1509,t2.js1510 "
				  + " from a01 t1,("+sql+") m,js15 t2 where t1.a0000=m.a0000 and m.js0100 = t2.js0100";*/
		
		String sql = getSql_1(cur_hj, cur_hj_4, rbId,dc005, js0100WhereSql);
		sql=" select m.a0101,m.a0192,m.a0104,m.qrzxl,m.a0111a,m.a0134,m.a0140,m.zcsj,m.fcsj,"
				  + " m.js0111,m.js0117,m.js0109,m.js0100,m.a0107,"
				  + " t2.dc001,t2.rb_id,t2.js1501,t2.js1502,t2.js1503,t2.js1504,t2.js1505,"
				  + " t2.js1506,t2.js1507,t2.js1508,t2.js1509,t2.js1510 "
				  + " from ("+sql+") m,js15 t2 where m.js0100 = t2.js0100";;
		
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		//List<HashMap<String,Object>> list=CommonQueryBS.getListBySQL(sql);
		CellStyle style = ExcelStyleUtil.getCellStyle(workbook);
		
		//日期
		Row rowdate = sheet.getRow(1);//第2行
		Cell celldate = rowdate.getCell(6);//第11列
		//将当前时间添加到第2行第7列
		celldate.setCellValue(new SimpleDateFormat("yyyy年MM月").format(new Date()) );
		
		int i=1;
		int rowIndex = 4;
		while(rs.next()){
			String a0101=rs.getString("a0101");//人员姓名
			String a0192=rs.getString("a0192");//现任职务
			String js0111=rs.getString("js0111");//拟任职务
			String js0117=rs.getString("js0117");//拟免职务
			String js1509=rs.getString("js1509");//备注(部务会)
			String js1510=rs.getString("js1510");//备注(常委会)
			
			ExcelReturnParam setRowHeight = new ExcelReturnParam(true,30);
			Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
			
			Cell xh=row.createCell(0);//序号
			ExcelStyleUtil.setCellValue(xh, style, i);
			
			Cell xm=row.createCell(1);//姓名
			ExcelStyleUtil.setCellValue(xm, style, a0101);
			
			Cell xrzw=row.createCell(2);//现任职务
			ExcelStyleUtil.setCellValue(xrzw, style, a0192);
			
			Cell nrmzw=row.createCell(3);//拟任免职务
			if(js0111!=null&&js0117!=null){
				ExcelStyleUtil.setCellValue(nrmzw, style, js0111+","+js0117);
			}else if(js0111!=null&&js0117==null){
				ExcelStyleUtil.setCellValue(nrmzw, style, js0111);
			}else if(js0111==null&&js0117!=null){
				ExcelStyleUtil.setCellValue(nrmzw, style, js0117);
			}else{
				ExcelStyleUtil.setCellValue(nrmzw, style, "");
			}
			
			Cell ty=row.createCell(4);//表决意见-同意
			ExcelStyleUtil.setCellValue(ty, style, "");
			
			Cell bty=row.createCell(5);//表决意见-不同意
			ExcelStyleUtil.setCellValue(bty, style, "");
			
			Cell hy=row.createCell(6);//表决意见-缓议
			ExcelStyleUtil.setCellValue(hy, style, "");
			
			Cell remark=row.createCell(7);//备注
			if("4_1".equals(cur_hj_4)){//部务会
				ExcelStyleUtil.setCellValue(remark, style, js1509);
			}else if("4_3".equals(cur_hj_4)){//常委会
				ExcelStyleUtil.setCellValue(remark, style, js1510);
			}	
			
			i++;
			rowIndex++;
		}
		
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn="";
		//通过讨论决定环节判断是部务会还是常委会
		if("4_1".equals(cur_hj_4)){//部务会
			fn = "部务会表决单.xls";
		}else if("4_3".equals(cur_hj_4)){//常委会
			fn = "常委会表决单.xls";
		}
			   
		this.outputFileNameList.add(fn);
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
	}

	/**
	 * 导出报告单
	 */
	public void exportBgd() throws RadowException,Exception{
		String cur_hj_4 = pm.request.getAttribute("cur_hj_4").toString();//讨论决定分环节
		File tplfilepath = null;
		//通过讨论决定环节判断是部务会还是常委会
		if("4_1".equals(cur_hj_4)){//部务会
			 tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/bwhbjbgd.xls").getPath());
		}else if("4_3".equals(cur_hj_4)){//常委会
			 tplfilepath=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/tpl/cwhbjbgd.xls").getPath());
		}
	   
		File newFile = tplfilepath;
		InputStream is = new FileInputStream(newFile);  //读取复制好的文件
		
		Workbook workbook = new HSSFWorkbook(is);      //默认读取2003版的Excel
		//获取sheet页总数
		Sheet sheet = workbook.getSheetAt(0);
		
		String rbId = pm.request.getAttribute("rbId").toString();//批次
		String cur_hj = pm.request.getAttribute("cur_hj").toString();//环节
		
		String dc005 = pm.request.getAttribute("dc005").toString();
		String js0100WhereSql = pm.request.getAttribute("js0100WhereSql").toString();
		/*String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005);
		sql=" select t1.a0101,t1.a0192,t1.a0104,t1.qrzxl,t1.a0111a,t1.a0134,t1.a0140,t1.zcsj,t1.fcsj,"
				  + " m.js0111,m.js0117,m.js0109,m.js0100,m.a0107,"
				  + " t2.dc001,t2.rb_id,t2.js1501,t2.js1502,t2.js1503,t2.js1504,t2.js1505,"
				  + " t2.js1506,t2.js1507,t2.js1508,t2.js1509,t2.js1510 "
				  + " from a01 t1,("+sql+") m,js15 t2 where t1.a0000=m.a0000 and m.js0100 = t2.js0100";*/
		
		String sql = getSql_1(cur_hj, cur_hj_4, rbId,dc005, js0100WhereSql);
		sql=" select m.a0101,m.a0192,m.a0104,m.qrzxl,m.a0111a,m.a0134,m.a0140,m.zcsj,m.fcsj,"
				  + " m.js0111,m.js0117,m.js0109,m.js0100,m.a0107,"
				  + " t2.dc001,t2.rb_id,t2.js1501,t2.js1502,t2.js1503,t2.js1504,t2.js1505,"
				  + " t2.js1506,t2.js1507,t2.js1508,t2.js1509,t2.js1510 "
				  + " from ("+sql+") m,js15 t2 where m.js0100 = t2.js0100";;
	
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
		rs = stmt.executeQuery(sql);
		List<HashMap<String,Object>> list=CommonQueryBS.getListBySQL(sql);
		CellStyle style = ExcelStyleUtil.getCellStyle(workbook);
		
		//表头
		Row rowdate = sheet.getRow(2);//第3行
		Cell celldate = rowdate.getCell(0);//第1列
		if(list.size()>0){
			HashMap<String,Object> map=list.get(0);
			if(map!=null){
				String name="";
				if(map.get("a0101")!=null){
					name=map.get("a0101").toString();//人员姓名
					
				}
				/*String js1501="";
				if(map.get("js1501")!=null){
					js1501=map.get("js1501").toString();//参加人数（部务会）
				}
				
				String js1505="";
				if(map.get("js1505")!=null){
					js1505=map.get("js1505").toString();//参加人数（常委会）
				}*/
				String str="";
				//通过讨论决定环节判断是部务会还是常委会
				if("4_1".equals(cur_hj_4)){//部务会
					str = "    刚才，部务会对"+name+"等同志的任用方案进行了无记名投票表决。今天到会的部务会成员   名，发出表决票    张，收回    张，其中有效票    张，表决有效。表决结果如下:";
				   }else if("4_3".equals(cur_hj_4)){//常委会
					    str="    刚才，常委会对"+name+"等同志的任用方案进行了无记名投票表决。今天到会的市委常委   名，发出表决票    张，收回    张，其中有效票    张，表决有效。表决结果如下:";
				   }
				
				//将当前时间添加到第3行第1列
				celldate.setCellValue(str) ;
			}
		}
						
		int i=1;
		int rowIndex = 5;
		while(rs.next()){
			String a0101=rs.getString("a0101");//人员姓名
			String a0192=rs.getString("a0192");//现任职务
			String js0111=rs.getString("js0111");//拟任职务
			String js0117=rs.getString("js0117");//拟免职务
			String js1509=rs.getString("js1509");//备注(部务会)
			String js1510=rs.getString("js1510");//备注(常委会)
			
			String js1501=rs.getString("js1501");//参加人数（部务会）
			String js1502=rs.getString("js1502");//同意（部务会）
			String js1503=rs.getString("js1503");//不同意（部务会）
			String js1504=rs.getString("js1504");//缓议（部务会）
			
			String js1505=rs.getString("js1505");//参加人数（常委会）
			String js1506=rs.getString("js1506");//同意（常委会）
			String js1507=rs.getString("js1507");//同意（常委会）
			String js1508=rs.getString("js1508");//同意（常委会）
			
			ExcelReturnParam setRowHeight = new ExcelReturnParam(true,30);
			Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
			
			Cell xh=row.createCell(0);//序号
			ExcelStyleUtil.setCellValue(xh, style, i);
			
			Cell xm=row.createCell(1);//姓名
			ExcelStyleUtil.setCellValue(xm, style, a0101);
			
			Cell xrzw=row.createCell(2);//现任职务
			ExcelStyleUtil.setCellValue(xrzw, style, a0192);
			
			Cell nrmzw=row.createCell(3);//拟任免职务
			if(js0111!=null&&js0117!=null){
				ExcelStyleUtil.setCellValue(nrmzw, style, js0111+","+js0117);
			}else if(js0111!=null&&js0117==null){
				ExcelStyleUtil.setCellValue(nrmzw, style, js0111);
			}else if(js0111==null&&js0117!=null){
				ExcelStyleUtil.setCellValue(nrmzw, style, js0117);
			}else{
				ExcelStyleUtil.setCellValue(nrmzw, style, "");
			}
			
			Cell ty=row.createCell(4);//表决意见-同意
			if("4_1".equals(cur_hj_4)){//部务会
				ExcelStyleUtil.setCellValue(ty, style, js1502);
			}else if("4_3".equals(cur_hj_4)){//常委会
				ExcelStyleUtil.setCellValue(ty, style, js1506);
			}	
			
			Cell bty=row.createCell(5);//表决意见-不同意
			if("4_1".equals(cur_hj_4)){//部务会
				ExcelStyleUtil.setCellValue(bty, style, js1503);
			}else if("4_3".equals(cur_hj_4)){//常委会
				ExcelStyleUtil.setCellValue(bty, style, js1507);
			}	
			
			Cell hy=row.createCell(6);//表决意见-缓议
			if("4_1".equals(cur_hj_4)){//部务会
				ExcelStyleUtil.setCellValue(hy, style, js1504);
			}else if("4_3".equals(cur_hj_4)){//常委会
				ExcelStyleUtil.setCellValue(hy, style, js1508);
			}	
			
			Cell remark=row.createCell(7);//备注
			if("4_1".equals(cur_hj_4)){//部务会
				ExcelStyleUtil.setCellValue(remark, style, js1509);
			}else if("4_3".equals(cur_hj_4)){//常委会
				ExcelStyleUtil.setCellValue(remark, style, js1510);
			}	
			
			i++;
			rowIndex++;
		}
		
		File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn="";
		//通过讨论决定环节判断是部务会还是常委会
		if("4_1".equals(cur_hj_4)){//部务会
			fn = "部务会表决结果报告单.xls";
		}else if("4_3".equals(cur_hj_4)){//常委会
			fn = "常委会表决结果报告单.xls";
		}
			   
		this.outputFileNameList.add(fn);
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
		
	}

	/**
	 * 导出EXCEL民主推荐表
	 * @param tuijianNum
	 */
	public void exportTJB(String tuijianNum) throws RadowException,Exception{
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		sheet.getPrintSetup().setLandscape(true);
		
		sheet.setDisplayGridlines(false);
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		Row row = sheet.createRow(0);
		row.setHeightInPoints((short) 57);
		sheet.setColumnWidth(0, 27 * 256+184);
		sheet.setColumnWidth(1, 28 * 256+184);
		sheet.setColumnWidth(2, 30 * 256+184);
		sheet.setColumnWidth(3, 18 * 256+184);
		
		//第一行
		Cell cell = row.createCell(0);
		// 生成一个样式
 		CellStyle style = workbook.createCellStyle();
 		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
 		style.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
 		style.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
		style.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
		style.setBorderRight(CellStyle.BORDER_THIN);//右边框       
		style.setBorderTop(CellStyle.BORDER_THIN);//上边框 
     	//cell.setCellStyle(style);
     	// 生成一个字体
 		Font font = workbook.createFont();
 		font.setFontHeightInPoints((short) 22);
 		font.setFontName("方正小标宋简体");
 		style.setFont(font);
 		cell.setCellStyle(style);
        cell.setCellValue("民主推荐表");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
     	
        //第二行
        row = sheet.createRow(1);
        row.setHeightInPoints((short) 54);
        cell = row.createCell(0);
        CellStyle style2 = workbook.createCellStyle();
     	Font font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 16);
		font2.setFontName("黑体");
		style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style2.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		style2.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
		style2.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
		style2.setBorderRight(CellStyle.BORDER_THIN);//右边框       
		style2.setBorderTop(CellStyle.BORDER_THIN);//上边框 
 		style2.setFont(font2);
		cell.setCellStyle(style2);
        cell.setCellValue("推荐职位");
        cell = row.createCell(1);
        cell.setCellStyle(style2);
        cell.setCellValue("姓名");
        cell = row.createCell(2);
        cell.setCellStyle(style2);
        cell.setCellValue("现任职务");
        cell = row.createCell(3);
        cell.setCellStyle(style2);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 3));
        
        //第三行
        row = sheet.createRow(2);
        row.setHeightInPoints((short) 108); 
        //sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));
        CellStyle style3 = workbook.createCellStyle();
		style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style3.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		style3.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
		style3.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
		style3.setBorderRight(CellStyle.BORDER_THIN);//右边框       
		style3.setBorderTop(CellStyle.BORDER_THIN);//上边框 
		cell = row.createCell(0);
		cell.setCellStyle(style3);
		cell = row.createCell(1);
		cell.setCellStyle(style3);
		cell = row.createCell(2);
		cell.setCellStyle(style3);
		cell = row.createCell(3);
		cell.setCellStyle(style3);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 3));
        
        //第四行
        row = sheet.createRow(3);
        row.setHeightInPoints((short) 42); 
        cell = row.createCell(0);
        CellStyle style4 = workbook.createCellStyle();
     	Font font4 = workbook.createFont();
     	font4.setFontHeightInPoints((short) 16);
     	font4.setFontName("仿宋_GB2312");
     	style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
     	style4.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	style4.setBorderBottom(CellStyle.BORDER_THIN);//下边框       
		style4.setBorderLeft(CellStyle.BORDER_THIN);//左边框       
		style4.setBorderRight(CellStyle.BORDER_THIN);//右边框       
		style4.setBorderTop(CellStyle.BORDER_THIN);//上边框 
     	//style4.setFont(font4);
		cell.setCellStyle(style4);
     	cell.setCellValue("注：限推"+tuijianNum+"名，多推无效。");
     	cell = row.createCell(1);
     	cell.setCellStyle(style4);
     	cell = row.createCell(2);
     	cell.setCellStyle(style4);
     	cell = row.createCell(3);
     	cell.setCellStyle(style4);
     	sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 3));
     	
     	File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "民主推荐表.xls";
		if(this.outputFileNameList.size()==0){
			this.outputFileNameList.add(fn);
		}
		
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
		
	}

	/**
	 * 导出推荐结果表EXCEL
	 * 
	 */
	public void exportTJJGB(List<Js33> datalist,HashMap<String, Object> mapobj) throws RadowException,Exception{
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		sheet.getPrintSetup().setLandscape(true);
		
		//sheet.setDisplayGridlines(false);
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		Row row = sheet.createRow(0);
		row.setHeightInPoints((short) 86);
		sheet.setColumnWidth(0, 5 * 256+184);
		sheet.setColumnWidth(1, 10 * 256+184);
		sheet.setColumnWidth(2, 38 * 256+184);
		sheet.setColumnWidth(3, 11 * 256+184);
		sheet.setColumnWidth(4, 11 * 256+184);
		sheet.setColumnWidth(5, 11 * 256+184);
		
		//第一行
		Cell cell = row.createCell(0);
 		CellStyle style = workbook.createCellStyle();
 		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
 		style.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	cell.setCellStyle(style);
 		Font font = workbook.createFont();
 		font.setFontHeightInPoints((short) 22);
 		font.setFontName("方正小标宋简体");
 		style.setFont(font);
        cell.setCellValue("民主推荐结果");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
     	
        //第二行
        row = sheet.createRow(1);
        CellStyle style3 = workbook.createCellStyle();
     	Font font3 = workbook.createFont();
     	font3.setFontHeightInPoints((short) 14);
     	font3.setFontName("黑体");
     	style3.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
     	style3.setWrapText(true);
     	style3.setFont(font3);
        row.setHeightInPoints((short) 35); 
        String[] headers = { "序号", "姓名", "现任职务", "会议推荐票数", "谈话推荐票数","排名"};
        List<String> list  = Arrays.asList(headers);
        for(int i=0;i<list.size();i++){
        	cell = row.createCell(i);
        	cell.setCellStyle(style3);
        	cell.setCellValue(list.get(i));
        }
        
        //第三行开始循环数据
        int rownum=2;
        CellStyle style5 = workbook.createCellStyle();
     	Font font5 = workbook.createFont();
     	font5.setFontHeightInPoints((short) 13);
     	font5.setFontName("Times New Roman");
     	style5.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	style5.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
     	style5.setFont(font5);
     	int hytjNum=0;
     	int thtjNum=0;
        for(int i=0;i<datalist.size();i++){
        	rownum=2+i;
        	row = sheet.createRow(rownum);
        	row.setHeightInPoints((short) 30);
        	String[] datas={(i+1)+"",datalist.get(i).getJs3302(),datalist.get(i).getJs3312(),datalist.get(i).getJs3305(),datalist.get(i).getJs3308(),"第"+(i+1)+"名"};
        	
        	if(datalist.get(i).getJs3305()!=null && datalist.get(i).getJs3305()!=""){
        		hytjNum+=Integer.parseInt(datalist.get(i).getJs3305());
    		}
        	
        	if(datalist.get(i).getJs3308()!=null && datalist.get(i).getJs3308()!=""){
        		thtjNum+=Integer.parseInt(datalist.get(i).getJs3308());
    		}
        	
        	List<String> subdatalist = Arrays.asList(datas);
            for(int j=0;j<subdatalist.size();j++){
            	cell = row.createCell(j);
            	cell.setCellStyle(style5);
            	cell.setCellValue(subdatalist.get(j));
            }
        }
        
        //（其他）行
        rownum=rownum+1;
        row = sheet.createRow(rownum);
        row.setHeightInPoints((short) 60);
        cell = row.createCell(0);
        CellStyle style4 = workbook.createCellStyle();
     	Font font4 = workbook.createFont();
     	font4.setFontHeightInPoints((short) 16);
     	font4.setFontName("仿宋_GB2312");
     	style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
     	style4.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	style4.setFont(font4);
     	cell.setCellValue("其他");
     	cell.setCellStyle(style4);
     	sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 1));
     	cell = row.createCell(2);
     	cell.setCellValue(mapobj.get("js1804")==null?"":mapobj.get("js1804").toString());
     	cell.setCellStyle(style4);
     	sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 2, 5));
     	
        //最后一行
     	rownum=rownum+1;
        row = sheet.createRow(rownum);
        row.setHeightInPoints((short) 51); 
        cell = row.createCell(0);
        CellStyle style6 = workbook.createCellStyle();
     	Font font6 = workbook.createFont();
     	font6.setFontHeightInPoints((short) 16);
     	font6.setFontName("Times New Roman");
     	style6.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
     	style6.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	style6.setFont(font6);
     	String chrq1=DateUtil.dateToString(DateUtil.getTimestamp(), "yyyy年MM月dd日");
     	if(mapobj.get("js1802")!=null){
     		String js1802=mapobj.get("js1802").toString();
     		chrq1=js1802.substring(0, 4)+"年"+js1802.substring(4, 6)+"月"+js1802.substring(6, 8)+"日";
     	}
     	
     	cell.setCellValue(chrq1+"，共"+hytjNum+"人参加会议推荐;"+chrq1+"，共"+thtjNum+"人参加个别谈话推荐。");
     	sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 5));
     	
     	File opp = new File(outputpath);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "民主推荐结果.xls";
		if(this.outputFileNameList.size()==0){
			this.outputFileNameList.add(fn);
		}
		
        OutputStream fos = new  FileOutputStream(new File(outputpath+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
	}
	
	
	public void exportTJGZFA(String path,String js2002,String js2003,String js2004,String js2005,String js2006,String js2007,int num)  throws RadowException,Exception{
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		sheet.getPrintSetup().setLandscape(true);
				
		//sheet.setDisplayGridlines(false);
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		Row row = sheet.createRow(0);
		row.setHeightInPoints((short) 86);
		sheet.setColumnWidth(0, 10 * 256+184);
		sheet.setColumnWidth(1, 12 * 256+184);
		sheet.setColumnWidth(2, 40 * 256+184);
		sheet.setColumnWidth(3, 20 * 256+184);
		sheet.setColumnWidth(4, 14 * 256+184);
		sheet.setColumnWidth(5, 15 * 256+184);
					
		//第一行
		Cell cell = row.createCell(0);
 		CellStyle style = workbook.createCellStyle();
 		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
 		style.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	cell.setCellStyle(style);
 		Font font = workbook.createFont();
 		font.setFontHeightInPoints((short) 22);
 		font.setFontName("方正小标宋简体");
 		style.setFont(font);
 		row.setHeightInPoints((short) 42);
        cell.setCellValue("民主推荐工作方案");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
     	
        //第二行
        row = sheet.createRow(1);
        cell = row.createCell(4);
        CellStyle style1 = workbook.createCellStyle();
        style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        style1.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	cell.setCellStyle(style1);
 		Font font1 = workbook.createFont();
 		font1.setFontHeightInPoints((short) 12);
 		font1.setFontName("Times New Roman");
 		style1.setFont(font1);
 		row.setHeightInPoints((short) 21); 
        cell.setCellValue("年   月");
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));
        		
        //第三行
        row = sheet.createRow(2);
        CellStyle style3 = workbook.createCellStyle();
     	Font font3 = workbook.createFont();
     	font3.setFontHeightInPoints((short) 14);
     	font3.setFontName("黑体");
     	style3.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	style3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
     	style3.setWrapText(true);
     	style3.setFont(font3);
        row.setHeightInPoints((short) 45); 
        String[] headers = { "推荐职位", "推荐人选范围", "推荐人选具体要求", "参加推荐对象", "推荐程序","备注"};
        List<String> list  = Arrays.asList(headers);
        for(int i=0;i<list.size();i++){
        	cell = row.createCell(i);
        	cell.setCellStyle(style3);
        	cell.setCellValue(list.get(i));
        }
        
        //String js2002,String js2003,String js2004,String js2005,String js2006,String js2007
        //第四行
        String[] data = {js2002,js2003,js2004,js2005,js2006,js2007};
        List<String> datalist  = Arrays.asList(data);
        row = sheet.createRow(3);
        row.setHeightInPoints((short) 187);
        CellStyle style4 = workbook.createCellStyle();
     	Font font4 = workbook.createFont();
     	font4.setFontHeightInPoints((short) 12);
     	font4.setFontName("Times New Roman");
     	style4.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
     	style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
     	style4.setWrapText(true);
     	style4.setFont(font4);
     	
        for(int i=0;i<datalist.size();i++){
        	cell = row.createCell(i);
        	cell.setCellStyle(style3);
        	cell.setCellValue(datalist.get(i));
        }
        
     	File opp = new File(outputpath+path+File.separator);
		if(!opp.isDirectory()){
			opp.mkdirs();
		}
		String fn = "民主推荐方案"+num+".xls";
		if(this.outputFileNameList.size()==0){
			this.outputFileNameList.add(fn);
		}
		
        OutputStream fos = new  FileOutputStream(new File(outputpath+path+File.separator+fn));
        //这个就就是弹出下载对话框的关键代码
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        workbook.write(bos);
        
        bos.flush();
        fos.close();
        bos.close();
	}
	
}
