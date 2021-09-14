package com.insigma.siis.local.pagemodel.xbrm.jcgl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hsqldb.lib.StringUtil;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.Checkreg;
import com.insigma.siis.local.business.entity.Folder;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.Notice;
import com.insigma.siis.local.business.entity.NoticeFile;
import com.insigma.siis.local.business.entity.NoticeRecipent;
import com.insigma.siis.local.business.entity.Policy;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.entity.Sp_Att;
import com.insigma.siis.local.business.entity.YearCheckFile;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sms.SMSSend;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.meeting.MeetingThemePageModel;
import com.insigma.siis.local.pagemodel.meeting.PublishPageModel;
import com.insigma.siis.local.pagemodel.train.HandleTrainPageModel;
import com.insigma.siis.local.pagemodel.xbrm.JSGLPageModel;
import com.insigma.siis.local.pagemodel.xbrm.QCJSPageModel;
  

/**
 * ��Ϣ����Servlet
 * @author fujun
 *
 */
public class RegCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletConfig config;
	private LogUtil applog = new LogUtil();
	
	final public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}  
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("downloadPFile".equals(method)){
			downloadPFile(request, response);
		} else if("downloadPFile_bs".equals(method)){
			downloadPFile_bs(request, response);
		} else if("downloadFile_chdbsj".equals(method)){
			downloadFile_chdbsj(request, response);
		} else if("downloadFile_chdbzd".equals(method)){
			downloadFile_chdbzd(request, response);
		} else if("impFK".equals(method)){
			impFK(request, response);
		} else if("deleteFile".equals(method)){
			deleteFile(request, response);
		} else if("getXX".equals(method)){
			getXX(request, response);
		} else if("downloadFKFile".equals(method)) {
			downloadFKFile(request, response);
		} else if("impXXK".equals(method)){
			impXXK(request, response);
		} else if("deleteXXKFile".equals(method)){
			deleteXXKFile(request, response);
		} else if("getXXK".equals(method)){
			getXXK(request, response);
		} else if("impPCFile".equals(method)){
			impPCFile(request, response);
		} else if("downloadFile_xxwth".equals(method)){
			downloadFile_xxwth(request, response);
		} else if("downloadFile_PC".equals(method)){
			downloadFile_PC(request, response);
		} else if("impPCXXFile".equals(method)){
			impPCXXFile(request, response);
		} else if("downloadFile_jghz".equals(method)){
			downloadFile_jghz(request, response);
		} else if("impPCXXZIPFile".equals(method)){
			impPCXXZIPFile(request, response);
		} else if("sendMsg".equals(method)){
			sendMsg(request, response);
		} else if("impJggwxx".equals(method)){
			impJggwxx(request, response);
		}
		
	}
	private void impJggwxx(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		
		HBSession sess = null;
		Connection conn = null;
		PreparedStatement ps2 = null;
		Statement stmt = null;
		InputStream in = null;
		try {
			request.setCharacterEncoding("GBK");
			out = response.getWriter();
			
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			

			List<FileItem> fileItems = uploader.parseRequest(request); 
			//CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if(!fi.isFormField()){
					String path = fi.getName();// �ļ�����
					String filename = path.substring(path.lastIndexOf("\\")+1);

					in = fi.getInputStream();
					Workbook wb = getWorkbok(in, null, filename);
					Sheet sheet0 = wb.getSheetAt(0);
					sess = HBUtil.getHBSession();
					conn = sess.connection();
					conn.setAutoCommit(false);
					stmt = conn.createStatement();
					String sql = "insert into JGGWIMP(jggwid,b0101,b0114,gwname,zwcode,gwnum) values(sys_guid(),?,?,?,?, ?)";
					ps2 = conn.prepareStatement(sql.toString());

					Sheet sheet = wb.getSheetAt(0);
					int rowNum =sheet.getLastRowNum();	//������
					String b0101 = "";
					String b0114 = "";
					a:for (int i = 1; i <= rowNum; i++) {
						Row row = sheet.getRow(i);
						if(row==null) {
							break a;
						}
						String c0 = getCellValue(row.getCell(0));
						String c1 = getCellValue(row.getCell(1));
						if(c1!=null && !c1.trim().equals("")) {
							ps2.setString(1, c0);
							ps2.setString(2, c1);
							b0114 = c1;
							b0101 = c0;
						} else {
							ps2.setString(1, b0101);
							ps2.setString(2, b0114);
						}
						String c3 = getCellValue(row.getCell(2));
						ps2.setString(3, c3);
						String zwcode = "";
						String c4 = getCellValue(row.getCell(3));
						if(c4!=null) {
							if(c4.equals("����")) {
								zwcode = "1A31";
							} else if(c4.equals("����")) {
								zwcode = "1A21";
							} else if(c4.equals("����")) {
								zwcode = "1A32";
							} else if(c4.equals("����")) {
								zwcode = "1A22";
							}
						}
						
						ps2.setString(4, zwcode);
						String c5 = row.getCell(4).toString();
						int k = 0;
						if(c5!=null && !c5.equals("")) {
							k = Double.valueOf(c5).intValue();
						}
						ps2.setInt(5, k);
						ps2.addBatch();
						
					}
					ps2.executeBatch();
					ps2.clearBatch();
					ps2.clearParameters();
					String sqlu = "update JGGWIMP j set j.b0111=(select k.b0111 from b01 k where k.b0114=j.b0114 and k.b0101=j.b0101)"
							+ " where exists (select k.b0111 from b01 k where k.b0114=j.b0114 and k.b0101=j.b0101)";
					System.out.println(sqlu); 
					stmt.execute(sqlu);
					String sqlu2 = "update JGGWIMP j set j.gwcode=(select k.code_value from code_value k where k.CODE_NAME=j.GWNAME and k.code_type='GWGLLB')"
							+ " where exists (select k.code_value from code_value k where k.CODE_NAME=j.GWNAME and k.code_type='GWGLLB')";
					stmt.execute(sqlu2);
					String sqlu3 = "insert into Jggwconf(jggwconfid,b0111,b0101,gwcode,gwname,gwnum,zwcode,status) select jggwid,b0111,b0101,gwcode,gwname,gwnum,zwcode,'1' from JGGWIMP ";
					stmt.execute(sqlu3);
					conn.commit();
				}
			}
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.parent.Ext.Msg.hide();"
					+ "parent.parent.odin.ext.getCmp('simpleExpWin2').close();");
			out.println("</script>");
			CommonQueryBS.systemOut("�ϴ�====================");
			
		} catch (Exception e) {
			try {
				if(conn!=null)
					conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('����ʧ�ܣ�');</script>");
			e.printStackTrace();
		}finally{
			try{
				if(conn!=null)
					conn.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			if(out != null){
				out.close();
			}
		}
	}
	
	private void sendMsg(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		PrintWriter out = null;
		//Map<String ,String> formDataMap = new HashMap<String ,String>();
		HBSession sess = HBUtil.getHBSession();
		try {
			request.setCharacterEncoding("GBK");
			out = response.getWriter();
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			
			//�ý�ɫid�����û������ҵ绰����
			String roleids = "";
			String checkregid = request.getParameter("checkregid");
			String xq = request.getParameter("xq");
			Checkreg cr = (Checkreg) sess.get(Checkreg.class, checkregid);
			cr.setRegstatus("1");
			if(xq!=null && xq.contains("1")) {
				cr.setGacltx("1");
				roleids = roleids + "8294f18b6b93e083016bc01b8ed37e46,";
			}
			if(xq!=null && xq.contains("2")) {
				cr.setZjjcltx("1");
				roleids = roleids + "8294f18b6b93e083016bc01efb4a7ecd,";
			}
			if(xq!=null && xq.contains("3")) {
				cr.setGscltx("1");
				roleids = roleids + "8294f18b6b93e083016bc020dc647f47,";
			}
			sess.update(cr);
			sess.flush();
			String name = cr.getRegname();
			String r = roleids.length()>3?roleids.substring(0,roleids.length()-1):"";
			String sql = "select MOBILE from smt_user u,smt_act t where t.objectid=u.userid and t.roleid in ('"+(r.replace(",", "','"))+"')";
			List<Object> list = sess.createSQLQuery(sql).list();
			String phones = "";
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				if(obj!=null && !obj.toString().trim().equals("")) {
					phones = phones + obj +",";
				}
			}
			if(phones.length()>1) {
				String mst = /*DateUtil.timeToString(DateUtil.getTimestamp(),"")*/ DateUtil.getTime() + "������1����Ϣ��ˣ�����ϵͳ�ڲ��գ�����ʱ���������";
				try {
					SMSSend.send(phones, mst);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.parent.Ext.Msg.hide();"
					+ "parent.realParent.infoSearch();parent.window.close();");
			out.println("</script>");
			
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('ϵͳ�쳣��');</script>");
		}finally{
			
			if(out != null){
				out.close();
			}
		}
		
	}
	private void impPCXXZIPFile(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		//Map<String ,String> formDataMap = new HashMap<String ,String>();
		HBSession sess = null;
		Connection conn = null;
		try {
			request.setCharacterEncoding("GBK");
			out = response.getWriter();
			sess = HBUtil.getHBSession();
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String sqlq="select smt_usergroup.usergroupname,smt_usergroup.id,smt_user.loginname,smt_user.userid from smt_user right join smt_usergroup on smt_user.dept=smt_usergroup.id where smt_user.userid='"+cueUserid+"'";
			List<HashMap> mapBySQL = CommonQueryBS.getQueryInfoByManulSQL(sqlq);
			String usergroupname=(String)mapBySQL.get(0).get("usergroupname")+"";
			String loginname=(String)mapBySQL.get(0).get("loginname");
			String groupid=(String)mapBySQL.get(0).get("id");
			String userid=(String)mapBySQL.get(0).get("userid");
			
			String id = UUID.randomUUID().toString();
			String directory = disk + "temp/impzip" + File.separator +id+ File.separator;
			String filePath = directory  + "unzip";
			File f = new File(filePath);
			if(!f.isDirectory()){
				f.mkdirs();
			}
			
			List<FileItem> fileItems = uploader.parseRequest(request); 
			//CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if(!fi.isFormField()){
					String impFile = directory + "imp.zip";
					fi.write(new File(impFile));
					//SevenZipUtil.unzipDir(impFile, filePath);
					NewSevenZipUtil.unzip7zAll(impFile, filePath, null);
					File upzip = new File(filePath);
					String childs[] = upzip.list();
					if(childs.length==0) {
						out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('ѹ�����쳣��');</script>");
						return;
					}
					String ids = "";
					for (int i = 0; i < childs.length; i++) {
						String c = childs[i];
						ids = ids +"'"+c+"',";
					}
					ids= ids.substring(0,ids.length()-1);
					List list = sess.createSQLQuery("select * from checkreg where checkregid in ("+ids+")").list();
					if(list.size()>0) {
						out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('�������������������γ�ͻ�����ܵ��룡');</script>");
						return;
					}
					conn = sess.connection();
					conn.setAutoCommit(false);
					for (int i = 0; i < childs.length; i++) {
						String path = childs[i];
						impDir(conn, filePath, path, usergroupname, loginname, groupid, userid, out);
					}
					conn.commit();
				}
			}
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.parent.Ext.Msg.hide();"
					+ "parent.realParent.infoSearch();parent.parent.odin.ext.getCmp('simpleExpWin3').close();");
			out.println("</script>");
			CommonQueryBS.systemOut("�ϴ�====================");
			
		} catch (Exception e) {
			try {
				if(conn!=null)
					conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('����ʧ�ܣ�');</script>");
			e.printStackTrace();
		}finally{
			try{
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			if(out != null){
				out.close();
			}
		}
		
	}
	private void impDir(Connection conn, String dir, String path, String usergroupname, String loginname, String groupid,
			String userid, PrintWriter out) throws Exception {
		try {
			String base = dir +"/"+ path+"/base.xlsx";
			File baseFile = new File(base);
			if(baseFile.exists() && baseFile.isFile()) {
				impDir_base(conn, baseFile, usergroupname, loginname, groupid, userid);
			} else {
				throw new AppException("û���ҵ�������Ϣ�����ܵ��롣");
			}
			File dirFile = new File(dir +"/"+ path);
			File[] childs = dirFile.listFiles();
			for (int i = 0; i < childs.length; i++) {
				File file = childs[i];
				String filename = file.getName();
				if(file.isDirectory()) {
					impDir_FKfile(conn, filename , path, dir);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	private void impDir_FKfile(Connection conn, String dirname, String path, String dir) throws Exception {
		try {
			File file = new File(dir +"/"+ path+"/"+dirname);
			if(file.isDirectory()) {
				File[] childs = file.listFiles();
				if(childs.length > 0) {
					File impFile = childs[0];
					DecimalFormat df = new DecimalFormat("#.00");
					String fileSize = df.format((double) impFile.getTotalSpace() / 1048576) + "MB";
					// ����ļ�����1M����ʾM��С������ʾkb
					if (impFile.getTotalSpace() < 1048576) {
						fileSize = (int) impFile.getTotalSpace() / 1024 + "KB";
					}
					if (impFile.getTotalSpace() < 1024) {
						fileSize = (int) impFile.getTotalSpace() / 1024 + "B";
					}
					
					if(dirname.equals("1")) {
						String id = impDir_Crj(conn, path, impFile,fileSize);
					} else if(dirname.equals("2")) {
						String id = impDir_Fc(conn, path, impFile,fileSize);
					} else if(dirname.equals("3")) {
						String id = impDir_Bx(conn, path, impFile,fileSize);
					} else if(dirname.equals("4")) {
						String id = impDir_Zq(conn, path, impFile,fileSize);
					} else if(dirname.equals("5")) {
						String id = impDir_Gscg(conn, path, impFile,fileSize);
					} else if(dirname.equals("6")) {
						String id = impDir_Gszw(conn, path, impFile,fileSize);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	private String impDir_Gszw(Connection conn, String checkregid, File impFile, String fileSize) throws RadowException {
		// �����Ա��Ϣid
		String classAtt = "crgszw";
		String filename = impFile.getName();
		String id = UUID.randomUUID().toString();
		String directory = "checkregfiles" + File.separator +checkregid+ File.separator;
		String filePath = directory  + id;
		File f = new File(disk + directory);
		if(!f.isDirectory()){
			f.mkdirs();
		}
		PreparedStatement ps2 = null;
		InputStream in = null;
		try {
			
			impDir_Crfile(conn,id,checkregid,impFile,fileSize,classAtt);
			
			String sql2 = "insert into CHECKREGGSZW values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?)";
			ps2 = conn.prepareStatement(sql2.toString());
			in = new FileInputStream(new File(disk + filePath));
			Workbook wb = null;
			if (filename.toLowerCase().endsWith("xls")) { // Excel 2003
				wb = new HSSFWorkbook(in);
			} else if (filename.toLowerCase().endsWith("xlsx")) { // Excel 2007/2010
				wb = new XSSFWorkbook(in);
			}
			Sheet sheet = wb.getSheetAt(0);
			int rowNum =sheet.getLastRowNum();//������
			for (int i = 1; i <= rowNum; i++) {
				int cidex = 3;
				//��Excel�����п�ʼȡֵ k��
				ps2.setString(1, UUID.randomUUID().toString());
				ps2.setString(2, checkregid);
				
				for(int x=1;x<14;x++){  //˳��ͬ	ǰ�˵Ļ��� x��
					Cell cell = sheet.getRow(i).getCell(x); 
					String value = getCellValue(cell);
					if(cell!=null) {
						ps2.setString(cidex ++, value);
					} else {
						ps2.setString(cidex ++, null);
					}
					
				}
				ps2.setInt(16, i);
				ps2.addBatch();
				if(i% 10000 ==0) {
					ps2.executeBatch();
					ps2.clearBatch();
					ps2.clearParameters();
				}
			}
			ps2.executeBatch();
			ps2.clearBatch();
			ps2.clearParameters();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("�ϴ�����ʧ�ܣ�");
		} finally {
			try{
				if(ps2!=null)
					ps2.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		return id;
	}
	private String impDir_Gscg(Connection conn, String checkregid, File impFile, String fileSize) throws RadowException {
		// �����Ա��Ϣid
		String classAtt = "crgscg";
		String filename = impFile.getName();
		String id = UUID.randomUUID().toString();
		String directory = "checkregfiles" + File.separator +checkregid+ File.separator;
		String filePath = directory  + id;
		File f = new File(disk + directory);
		if(!f.isDirectory()){
			f.mkdirs();
		}
		PreparedStatement ps2 = null;
		InputStream in = null;
		try {
			
			impDir_Crfile(conn,id,checkregid,impFile,fileSize,classAtt);
			
			String sql2 = "insert into CHECKREGGSCG values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?)";
			ps2 = conn.prepareStatement(sql2.toString());
			in = new FileInputStream(new File(disk + filePath));
			Workbook wb = null;
			if (filename.toLowerCase().endsWith("xls")) { // Excel 2003
				wb = new HSSFWorkbook(in);
			} else if (filename.toLowerCase().endsWith("xlsx")) { // Excel 2007/2010
				wb = new XSSFWorkbook(in);
			}
			Sheet sheet = wb.getSheetAt(0);
			int rowNum =sheet.getLastRowNum();//������
			for (int i = 1; i <= rowNum; i++) {
				int cidex = 3;
				//��Excel�����п�ʼȡֵ k��
				ps2.setString(1, UUID.randomUUID().toString());
				ps2.setString(2, checkregid);
				
				for(int x=1;x<16;x++){  //˳��ͬ	ǰ�˵Ļ��� x��
					Cell cell = sheet.getRow(i).getCell(x); 
					String value = getCellValue(cell);
					if(cell!=null) {
						ps2.setString(cidex ++, value);
					} else {
						ps2.setString(cidex ++, null);
					}
					
				}
				ps2.setInt(18, i);
				ps2.addBatch();
				if(i% 10000 ==0) {
					ps2.executeBatch();
					ps2.clearBatch();
					ps2.clearParameters();
				}
			}
			ps2.executeBatch();
			ps2.clearBatch();
			ps2.clearParameters();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("�ϴ�����ʧ�ܣ�");
		} finally {
			try{
				if(ps2!=null)
					ps2.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		return id;
	}
	private String impDir_Zq(Connection conn, String checkregid, File impFile, String fileSize) throws RadowException {
		// �����Ա��Ϣid
		String classAtt = "crzq";
		String filename = impFile.getName();
		String id = UUID.randomUUID().toString();
		String directory = "checkregfiles" + File.separator +checkregid+ File.separator;
		String filePath = directory  + id;
		File f = new File(disk + directory);
		if(!f.isDirectory()){
			f.mkdirs();
		}
		PreparedStatement ps2 = null;
		InputStream in = null;
		try {
			
			impDir_Crfile(conn,id,checkregid,impFile,fileSize,classAtt);
			
			String sql2 = "insert into CHECKREGZQ values (?,?,?,?,?, ?,?,?,?,?, ?,?,?)";
			ps2 = conn.prepareStatement(sql2.toString());
			in = new FileInputStream(new File(disk + filePath));
			Workbook wb = null;
			if (filename.toLowerCase().endsWith("xls")) { // Excel 2003
				wb = new HSSFWorkbook(in);
			} else if (filename.toLowerCase().endsWith("xlsx")) { // Excel 2007/2010
				wb = new XSSFWorkbook(in);
			}
			Sheet sheet = wb.getSheetAt(0);
			int rowNum =sheet.getLastRowNum();//������
			a:for (int i = 1; i <= rowNum; i++) {
				int cidex = 3;
				//��Excel�����п�ʼȡֵ k��
				for(int x=0; x<11; x++){  //˳��ͬ	ǰ�˵Ļ��� x��
					Row row = sheet.getRow(i);
					if(row==null) {
						break a;
					}
					Cell cell = row.getCell(x); 
					String value = getCellValue(cell);
					if(x==0) {
						String reg = "^\\d+$";
						if(value==null || "".equals(value.trim())
								|| !value.matches(reg)) {
							break a;
						}
					}
					if(value!=null) {
						ps2.setString(cidex ++, value);
					} else {
						ps2.setString(cidex ++, null);
					}
					
				}
				ps2.setString(1, UUID.randomUUID().toString());
				ps2.setString(2, checkregid);
				
				ps2.setInt(13, i);
				ps2.addBatch();
				if(i% 10000 ==0) {
					ps2.executeBatch();
					ps2.clearBatch();
					ps2.clearParameters();
				}
			}
			ps2.executeBatch();
			ps2.clearBatch();
			ps2.clearParameters();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("�ϴ�����ʧ�ܣ�");
		} finally {
			try{
				if(ps2!=null)
					ps2.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		return id;
	}
	private String impDir_Bx(Connection conn, String checkregid, File impFile, String fileSize) throws RadowException {
		// �����Ա��Ϣid
		String classAtt = "crbx";
		String filename = impFile.getName();
		String id = UUID.randomUUID().toString();
		String directory = "checkregfiles" + File.separator +checkregid+ File.separator;
		String filePath = directory  + id;
		File f = new File(disk + directory);
		if(!f.isDirectory()){
			f.mkdirs();
		}
		PreparedStatement ps2 = null;
		InputStream in = null;
		try {
			
			impDir_Crfile(conn,id,checkregid,impFile,fileSize,classAtt);
			
			String sql2 = "insert into CHECKREGBX values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)";
			ps2 = conn.prepareStatement(sql2.toString());
			in = new FileInputStream(new File(disk + filePath));
			Workbook wb = null;
			if (filename.toLowerCase().endsWith("xls")) { // Excel 2003
				wb = new HSSFWorkbook(in);
			} else if (filename.toLowerCase().endsWith("xlsx")) { // Excel 2007/2010
				wb = new XSSFWorkbook(in);
			}
			Sheet sheet = wb.getSheetAt(0);
			int rowNum =sheet.getLastRowNum();//������
			a:for (int i = 1; i <= rowNum; i++) {
				int cidex = 3;
				//��Excel�����п�ʼȡֵ k��
				for(int x=0; x<11; x++){  //˳��ͬ	ǰ�˵Ļ��� x��
					Row row = sheet.getRow(i);
					if(row==null) {
						break a;
					}
					Cell cell = row.getCell(x); 
					String value = getCellValue(cell);
					if(x==0) {
						if(value==null || "".equals(value.trim())
								|| value.trim().startsWith("˵��")) {
							break a;
						}
					}
					if(value!=null) {
						ps2.setString(cidex ++, value);
					} else {
						ps2.setString(cidex ++, null);
					}
					
				}
				ps2.setString(1, UUID.randomUUID().toString());
				ps2.setString(2, checkregid);
				
				ps2.setInt(14, i);
				ps2.addBatch();
				if(i% 10000 ==0) {
					ps2.executeBatch();
					ps2.clearBatch();
					ps2.clearParameters();
				}
			}
			ps2.executeBatch();
			ps2.clearBatch();
			ps2.clearParameters();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("�ϴ�����ʧ�ܣ�");
		} finally {
			try{
				if(ps2!=null)
					ps2.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		return id;
	}
	private String impDir_Fc(Connection conn, String checkregid, File impFile, String fileSize) throws RadowException {
		// �����Ա��Ϣid
		String classAtt = "crfc";
		String filename = impFile.getName();
		String id = UUID.randomUUID().toString();
		String directory = "checkregfiles" + File.separator +checkregid+ File.separator;
		String filePath = directory  + id;
		File f = new File(disk + directory);
		if(!f.isDirectory()){
			f.mkdirs();
		}
		PreparedStatement ps2 = null;
		InputStream in = null;
		try {
			
			impDir_Crfile(conn,id,checkregid,impFile,fileSize,classAtt);
			
			String sql2 = "insert into CHECKREGFC values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)";
			ps2 = conn.prepareStatement(sql2.toString());
			in = new FileInputStream(new File(disk + filePath));
			Workbook wb = null;
			if (filename.toLowerCase().endsWith("xls")) { // Excel 2003
				wb = new HSSFWorkbook(in);
			} else if (filename.toLowerCase().endsWith("xlsx")) { // Excel 2007/2010
				wb = new XSSFWorkbook(in);
			}
			Sheet sheet = wb.getSheetAt(0);
			int rowNum =sheet.getLastRowNum();//������
			for (int i = 1; i <= rowNum; i++) {
				int cidex = 3;
				//��Excel�����п�ʼȡֵ k��
				ps2.setString(1, UUID.randomUUID().toString());
				ps2.setString(2, checkregid);
				
				for(int x=0;x<16;x++){  //˳��ͬ	ǰ�˵Ļ��� x��
					Cell cell = sheet.getRow(i).getCell(x); 
					String value = getCellValue(cell);
					if(cell!=null) {
						ps2.setString(cidex ++, value);
					} else {
						ps2.setString(cidex ++, null);
					}
					
				}
				ps2.setInt(19, i);
				ps2.addBatch();
				if(i% 10000 ==0) {
					ps2.executeBatch();
					ps2.clearBatch();
					ps2.clearParameters();
				}
			}
			ps2.executeBatch();
			ps2.clearBatch();
			ps2.clearParameters();
			
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("�ϴ�����ʧ�ܣ�");
		} finally {
			try{
				if(ps2!=null)
					ps2.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		return id;
	}
	private String impDir_Crj(Connection conn, String checkregid, File impFile, String fileSize) throws RadowException {
		// �����Ա��Ϣid
		String classAtt = "crcrj";
		String filename = impFile.getName();
		String id = UUID.randomUUID().toString();
		String directory = "checkregfiles" + File.separator +checkregid+ File.separator;
		String filePath = directory  + id;
		File f = new File(disk + directory);
		if(!f.isDirectory()){
			f.mkdirs();
		}
		
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		InputStream in = null;
		try {
			
			impDir_Crfile(conn,id,checkregid,impFile,fileSize,classAtt);
			
			String sql2 = "insert into CHECKREGCRJZJ values (?,?,?,?,?, ?,?,?,?,?, ?,?)";
			ps2 = conn.prepareStatement(sql2.toString());
			String sql3 = "insert into CHECKREGCRJJL values (?,?,?,?,?, ?,?,?,?,?, ?,?)";
			ps3 = conn.prepareStatement(sql3.toString());
			in = new FileInputStream(new File(disk + filePath));
			Workbook wb = null;
			if (filename.toLowerCase().endsWith("xls")) { // Excel 2003
				wb = new HSSFWorkbook(in);
			} else if (filename.toLowerCase().endsWith("xlsx")) { // Excel 2007/2010
				wb = new XSSFWorkbook(in);
			}
			Sheet sheet = wb.getSheetAt(0);
			List<CellRangeAddress> CombineCells  = getCombineCell(sheet);
			int rowNum =sheet.getLastRowNum();//������
			
			int row1firstC = 0;
	        int row1lastC = 0;
	        int row1firstR = 0;
	        int row1lastR = 0;
	        
	        int row3firstC = 0;
	        int row3lastC = 0;
	        int row3firstR = 0;
	        int row3lastR = 0;
			String values[] = {"","","",""};
	        
			for (int i = 2; i <= rowNum; i++) {
				String cellValue = null;
				if(i <= row1lastR) {
					if(i <= row3lastR) {
						for(int x=9;x<16;x++){
							if(x == 9) {
								String cv = getCellValue(sheet.getRow(i).getCell(x));
								if(cv.equals("δ�鵽���뾳��¼")) {
									break;
								} else {
									ps3.setString(x - 5, cv);
								}
							} else if(x >9 && x < 15) {
								String cv = getCellValue(sheet.getRow(i).getCell(x));
								ps3.setString(x - 5, cv);
							} else if(x == 15) {
								String cv = getCellValue(sheet.getRow(i).getCell(x));
								ps3.setString(x - 5, cv);
								ps3.setString(1, UUID.randomUUID().toString());
								ps3.setString(2, values[3]);
								ps3.setString(3, checkregid);
								ps3.setString(11, values[1]);
								ps3.setInt(12, i);
								ps3.addBatch();
							}
						
						}
					} else {
						for(int x=2;x<16;x++){
							if(x == 2) {
								boolean boo = false;
								for (CellRangeAddress ca : CombineCells) {
									//��úϲ���Ԫ�����ʼ��, ������, ��ʼ��, ������
									row3firstC = ca.getFirstColumn();
									row3lastC = ca.getLastColumn();
									row3firstR = ca.getFirstRow();
									row3lastR = ca.getLastRow();
						            if(i >= row3firstR && i <= row3lastR) {
						                if(x >= row3firstC && x <= row3lastC) {
						                    Row fRow = sheet.getRow(row3firstR);
						                    Cell fCell = fRow.getCell(row3firstC);
						                    cellValue = getCellValue(fCell);
						                    boo = true;
						                    break;
						                }
						            } else {
						                cellValue = "";
						            }
								}
								if(boo) {
									values[2] =cellValue; 
								} else {
									row3firstC = x;
							        row3lastC = x;
							        row3firstR = i;
							        row3lastR = i;
							        cellValue = getCellValue(sheet.getRow(i).getCell(x));
							        values[2] = cellValue;
								}
								if(cellValue==null || "".equals(cellValue) ||
										"δ�鵽���뾳֤��".equals(cellValue)) {
									String newid = UUID.randomUUID().toString();
									values[3] = newid;
									ps2.setString(1, newid);
									ps2.setString(2, checkregid);
									ps2.setString(3, values[0]);
									ps2.setString(4, values[1]);
									ps2.setString(5, "");
									ps2.setString(6, "");
									ps2.setString(7, "");
									ps2.setString(8, "");
									ps2.setString(9, "");
									ps2.setString(10, "");
									ps2.setString(11, "");
									ps2.setInt(12, i);
									ps2.addBatch();
									break;
								}
							} else if(x >2 && x < 8) {
								ps2.setString(x + 3, getCellValue(sheet.getRow(i).getCell(x)));
							} else if(x == 8) {
								ps2.setString(x + 3, getCellValue(sheet.getRow(i).getCell(x)));
								String newid = UUID.randomUUID().toString();
								values[3] = newid;
								ps2.setString(1, newid);
								ps2.setString(2, checkregid);
								ps2.setString(3, values[0]);
								ps2.setString(4, values[1]);
								ps2.setString(5, values[2]);
								ps2.setInt(12, i);
								ps2.addBatch();
							} else if(x == 9) {
								String cv = getCellValue(sheet.getRow(i).getCell(x));
								if(cv.equals("δ�鵽���뾳��¼")) {
									break;
								} else {
									ps3.setString(x - 5, cv);
								}
							} else if(x >9 && x < 15) {
								String cv = getCellValue(sheet.getRow(i).getCell(x));
								ps3.setString(x - 5, cv);
							} else if(x == 15) {
								String cv = getCellValue(sheet.getRow(i).getCell(x));
								ps3.setString(x - 5, cv);
								ps3.setString(1, UUID.randomUUID().toString());
								ps3.setString(2, values[3]);
								ps3.setString(3, checkregid);
								ps3.setString(11, values[1]);
								ps3.setInt(12, i);
								ps3.addBatch();
							}
						
						}
					}
				} else {
					for(int x=0;x<16;x++){
						if(x == 0) {
							boolean boo = false;
							for (CellRangeAddress ca : CombineCells) {
								//��úϲ���Ԫ�����ʼ��, ������, ��ʼ��, ������
								row1firstC = ca.getFirstColumn();
								row1lastC = ca.getLastColumn();
								row1firstR = ca.getFirstRow();
								row1lastR = ca.getLastRow();
					            if(i >= row1firstR && i <= row1lastR) {
					                if(x >= row1firstC && x <= row1lastC) {
					                    Row fRow = sheet.getRow(row1firstR);
					                    Cell fCell = fRow.getCell(row1firstC);
					                    cellValue = getCellValue(fCell);
					                    boo = true;
					                    break;
					                }
					            } else {
					                cellValue = "";
					            }
							}
							if(boo) {
								if(row1firstC != row1lastC) {
									break;
								}
								values[0] =cellValue; 
							} else {
								row1firstC = x;
						        row1lastC = x;
						        row1firstR = i;
						        row1lastR = i;
						        values[0] = getCellValue(sheet.getRow(i).getCell(x));
						        if(values[0] == null || values[0].equals("")) {
									break;
								}
							}
						} else if(x == 1) {
							values[1] = getCellValue(sheet.getRow(i).getCell(16));
						} else if(x == 2) {
							boolean boo = false;
							for (CellRangeAddress ca : CombineCells) {
								//��úϲ���Ԫ�����ʼ��, ������, ��ʼ��, ������
								row3firstC = ca.getFirstColumn();
								row3lastC = ca.getLastColumn();
								row3firstR = ca.getFirstRow();
								row3lastR = ca.getLastRow();
					            if(i >= row3firstR && i <= row3lastR) {
					                if(x >= row3firstC && x <= row3lastC) {
					                    Row fRow = sheet.getRow(row3firstR);
					                    Cell fCell = fRow.getCell(row3firstC);
					                    cellValue = getCellValue(fCell);
					                    boo = true;
					                    break;
					                }
					            } else {
					                cellValue = "";
					            }
							}
							if(boo) {
								values[2] =cellValue; 
							} else {
								row3firstC = x;
						        row3lastC = x;
						        row3firstR = i;
						        row3lastR = i;
						        cellValue = getCellValue(sheet.getRow(i).getCell(x));
						        values[2] = cellValue;
							}
							if(cellValue==null || "".equals(cellValue) ||
									"δ�鵽���뾳֤��".equals(cellValue)) {
								String newid = UUID.randomUUID().toString();
								values[3] = newid;
								ps2.setString(1, newid);
								ps2.setString(2, checkregid);
								ps2.setString(3, values[0]);
								ps2.setString(4, values[1]);
								ps2.setString(5, "");
								ps2.setString(6, "");
								ps2.setString(7, "");
								ps2.setString(8, "");
								ps2.setString(9, "");
								ps2.setString(10, "");
								ps2.setString(11, "");
								ps2.setInt(12, i);
								ps2.addBatch();
								break;
							}
						} else if(x >2 && x < 8) {
							ps2.setString(x + 3, getCellValue(sheet.getRow(i).getCell(x)));
						} else if(x == 8) {
							ps2.setString(x + 3, getCellValue(sheet.getRow(i).getCell(x)));
							String newid = UUID.randomUUID().toString();
							values[3] = newid;
							ps2.setString(1, newid);
							ps2.setString(2, checkregid);
							ps2.setString(3, values[0]);
							ps2.setString(4, values[1]);
							ps2.setString(5, values[2]);
							ps2.setInt(12, i);
							ps2.addBatch();
						} else if(x == 9) {
							String cv = getCellValue(sheet.getRow(i).getCell(x));
							if(cv.equals("δ�鵽���뾳��¼")) {
								break;
							} else {
								ps3.setString(x - 5, cv);
							}
						} else if(x >9 && x < 15) {
							String cv = getCellValue(sheet.getRow(i).getCell(x));
							ps3.setString(x - 5, cv);
						} else if(x == 15) {
							String cv = getCellValue(sheet.getRow(i).getCell(x));
							ps3.setString(x - 5, cv);
							ps3.setString(1, UUID.randomUUID().toString());
							ps3.setString(2, values[3]);
							ps3.setString(3, checkregid);
							ps3.setString(11, values[1]);
							ps3.setInt(12, i);
							ps3.addBatch();
						}
					
					}
				}
				if(i% 5000 ==0) {
					ps2.executeBatch();
					ps2.clearBatch();
					ps2.clearParameters();
					ps3.executeBatch();
					ps3.clearBatch();
					ps3.clearParameters();
				}
			}
			ps2.executeBatch();
			ps2.clearBatch();
			ps2.clearParameters();
			ps3.executeBatch();
			ps3.clearBatch();
			ps3.clearParameters();
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("�ϴ�����ʧ�ܣ�");
		} finally {
			try{
				if(ps3!=null)
					ps3.close();
				if(ps2!=null)
					ps2.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		return id;
	}
	
	private void impDir_Crfile(Connection conn, String id, String checkregid, File impFile, String fileSize,
			String classAtt) throws RadowException {
		PreparedStatement ps = null;
		String directory = "checkregfiles" + File.separator +checkregid+ File.separator;
		String filePath = directory  + id;
		try {
			String sql = "insert into CHECKREGFILE values(?,?,?,?,?, ?,?,?)";
			ps = conn.prepareStatement(sql.toString());
			int fidex = 1;
			ps.setString(fidex++, id);
			ps.setString(fidex++, checkregid);
			
			ps.setString(fidex++, impFile.getName());
			ps.setString(fidex++, SysManagerUtils.getUserId());
			ps.setDate(fidex++, DateUtil.date2sqlDate(new Date()));
			ps.setString(fidex++, directory);
			ps.setString(fidex++, fileSize);
			ps.setString(fidex++, classAtt);
			ps.execute();
			FileUtil.copyFile(impFile, new File(disk + filePath));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("�ϴ�����ʧ�ܣ�");
		} finally {
			try{
				if(ps!=null)
					ps.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
	}
	/**
     * �ϲ���Ԫ����,��ȡ�ϲ���
     * @param sheet
     * @return List<CellRangeAddress>
     */
    public  List<CellRangeAddress> getCombineCell(Sheet sheet) {
        List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
        //���һ�� sheet �кϲ���Ԫ�������
        int sheetmergerCount = sheet.getNumMergedRegions();
        //�������еĺϲ���Ԫ��
        for(int i = 0; i<sheetmergerCount;i++) {
            //��úϲ���Ԫ�񱣴��list��
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }
        return list;
    }
	
	private void impDir_base(Connection conn, File baseFile, String usergroupname, String loginname, String groupid,
			String userid) throws Exception {
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		InputStream in = null;
		try {
			in = new FileInputStream(baseFile);
			Workbook wb = getWorkbok(in, baseFile, baseFile.getName());
			Sheet sheet0 = wb.getSheetAt(0);
			
			String sql = "insert into CHECKREG(checkregid,regno,regname,checkdate,regstatus,publishtime,"
					+ "reguser,userid,groupid,groupname,createtime,regtype,REGOTHERID) values(?,?,?,?,?, ?,?,?,?,?, ?,?,?)";
			ps = conn.prepareStatement(sql.toString());
			Row row0 = sheet0.getRow(1);
			String id = getCellValue(row0.getCell(0));
			ps.setString(1, id);
			ps.setString(2, getCellValue(row0.getCell(1)));
			ps.setString(3, getCellValue(row0.getCell(2)));
			ps.setString(4, getCellValue(row0.getCell(3)));
			ps.setString(5, getCellValue(row0.getCell(4)));
			ps.setString(6, getCellValue(row0.getCell(5)));
			
			ps.setString(7, loginname);
			ps.setString(8, userid);
			ps.setString(9, groupid);
			ps.setString(10, usergroupname);
			ps.setTimestamp(11, DateUtil.getTimestamp());
			ps.setString(12, getCellValue(row0.getCell(6)));
			ps.setString(13, getCellValue(row0.getCell(7)));
			
			String sql2 = "insert into checkregperson(crp000,a0000,a3600,crp001,crp002,crp004,"
					+ "crp005,sortid1,sortid2,crp006,crp007,checkregid,crp008,crp009,crp011,"
					+ "crp012,crp018) values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)";
			ps2 = conn.prepareStatement(sql2.toString());
			//HSSFWorkbook wb = new HSSFWorkbook(in);
			
			Sheet sheet = wb.getSheetAt(1);
			int rowNum =sheet.getLastRowNum();	//������
			a:for (int i = 1; i <= rowNum; i++) {
				Row row = sheet.getRow(i);
				if(row==null) {
					break a;
				}
				ps2.setString(1, getCellValue(row.getCell(0)));
				ps2.setString(2, getCellValue(row.getCell(1)));
				ps2.setString(3, getCellValue(row.getCell(2)));
				ps2.setString(4, getCellValue(row.getCell(3)));
				ps2.setString(5, getCellValue(row.getCell(4)));
				ps2.setString(6, getCellValue(row.getCell(5)));
				ps2.setString(7, getCellValue(row.getCell(6)));
				String c8 = getCellValue(row.getCell(7));
				ps2.setInt(8, c8!=null && !c8.trim().equals("")?Double.valueOf(c8).intValue():0);
				String c9 = getCellValue(row.getCell(8));
				ps2.setInt(9, c9!=null && !c9.trim().equals("")?Double.valueOf(c9).intValue():0);
				ps2.setString(10, getCellValue(row.getCell(9)));
				ps2.setString(11, getCellValue(row.getCell(10)));
				ps2.setString(12, getCellValue(row.getCell(11)));
				ps2.setString(13, getCellValue(row.getCell(12)));
				ps2.setString(14, getCellValue(row.getCell(13)));
				ps2.setString(15, getCellValue(row.getCell(14)));
				ps2.setString(16, getCellValue(row.getCell(15)));
				ps2.setString(17, getCellValue(row.getCell(16)));
				
				ps2.addBatch();
				
			}
			ps2.executeBatch();
			ps2.clearBatch();
			ps2.clearParameters();
			
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if(ps!=null)
					ps.close();
				if(ps2!=null)
					ps2.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
	private void downloadFile_jghz(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		OutputStream out = null;
	 	HBSession session  = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Workbook wb = null;
		FileInputStream in = null;
		try {
			String clientInfo = request.getHeader("User-agent");
			request.setCharacterEncoding("GBK");
			String rootpath = getRootPath();
			String checkregid = request.getParameter("checkregid");
			
			//Checkreg reg = (Checkreg) session.get(Checkreg.class, checkregid);
			conn = session.connection();
			stmt = conn.createStatement();
			in = new FileInputStream(new File(rootpath+"/pages/xbrm/jcgl/hzqkhz.xlsx"));
			wb = new XSSFWorkbook(in);
			Sheet sheet = wb.getSheetAt(0);
			
			String querysql = "select * from CHECKREGPERSON where checkregid='"+checkregid+"' and CRP007='1' order by sortid1,crp008,sortid2";
			rs = stmt.executeQuery(querysql);
			int index = 3;								//��ʼ��������
			int number = 1;								//��Ա���
			String id = "";
			short rowheihgt = 350;					
			CellStyle[] cellstyles = new CellStyle[11];
			while (rs.next()) {
				if(index==3) {
					Row row_old = sheet.getRow(index);
					rowheihgt = row_old.getHeight();
					cellstyles[0] = row_old.getCell(0).getCellStyle();
					cellstyles[1] = row_old.getCell(1).getCellStyle();
					cellstyles[2] = row_old.getCell(2).getCellStyle();
					cellstyles[3] = row_old.getCell(3).getCellStyle();
					cellstyles[4] = row_old.getCell(4).getCellStyle();
					cellstyles[5] = row_old.getCell(5).getCellStyle();
					cellstyles[6] = row_old.getCell(6).getCellStyle();
					cellstyles[7] = row_old.getCell(7).getCellStyle();
					cellstyles[8] = row_old.getCell(8).getCellStyle();
					cellstyles[9] = row_old.getCell(9).getCellStyle();
					cellstyles[10] = row_old.getCell(10).getCellStyle();
				}
				
				Row row = sheet.createRow(index);
				String crp001 = rs.getString("crp001");		//����
				String crp004 = rs.getString("crp004");		//������λְ��
				
				Cell cell0 = row.createCell(0);
				cell0.setCellStyle(cellstyles[0]);
				cell0.setCellValue(number++);
				
				Cell cell1 = row.createCell(1);
				cell1.setCellStyle(cellstyles[1]);
				cell1.setCellValue(crp001);
				
				Cell cell2 = row.createCell(2);
				cell2.setCellStyle(cellstyles[2]);
				cell2.setCellValue(crp004);
				
				Cell cell3 = row.createCell(3);
				cell3.setCellStyle(cellstyles[3]);
				cell3.setCellValue(rs.getString("crp012"));
				
				Cell cell4 = row.createCell(4);
				cell4.setCellStyle(cellstyles[4]);
				cell4.setCellValue(rs.getString("crp011"));
				
				Cell cell5 = row.createCell(5);
				cell5.setCellStyle(cellstyles[5]);
				cell5.setCellValue(rs.getString("crp018"));
				
				Cell cell6 = row.createCell(6);
				cell6.setCellStyle(cellstyles[6]);
				cell6.setCellValue(rs.getString("crp016"));
				
				Cell cell7 = row.createCell(7);
				cell7.setCellStyle(cellstyles[7]);
				cell7.setCellValue(rs.getString("crp015"));
				
				Cell cell8 = row.createCell(8);
				cell8.setCellStyle(cellstyles[8]);
				cell8.setCellValue(rs.getString("crp013"));
				
				Cell cell9 = row.createCell(9);
				cell9.setCellStyle(cellstyles[9]);
				cell9.setCellValue(rs.getString("crp014"));
				
				Cell cell10 = row.createCell(10);
				cell10.setCellStyle(cellstyles[10]);
				cell10.setCellValue(rs.getString("crp017"));
				row.setHeight(rowheihgt);
				
				index++;
			}
			
			in.close();
			rs.close();
			stmt.close();
			conn.close();
			
			String filename = "����������.xlsx";
			/*FileOutputStream fout = new FileOutputStream(new File(path2+filename));
			wb.write(fout);
			fout.close();
		    wb.close();*/
			
			// IE����URLEncoder��ʽ����
			if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {
			    if (clientInfo.indexOf("MSIE 6") > 0 || clientInfo.indexOf("MSIE 5") > 0) {// IE6����GBK���˴�ʵ���ɾ�����
					filename = new String(filename.getBytes("GBK"),  "ISO-8859-1");
				} else {// ie7+��URLEncoder��ʽ
			        filename = URLEncoder.encode(filename, "utf-8");
				}
		    } else {//���������
		       	filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
		    }
			out = response.getOutputStream();
			
			response.reset();
			
			
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			response.setContentType("application/octet-stream;charset=GBK");
			wb.write(out);
		    out.close();
		    wb.close();
		} catch (Exception e) {
			e.printStackTrace();
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
	private void impPCXXFile(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		//Map<String ,String> formDataMap = new HashMap<String ,String>();
		HBSession sess = null;
		PreparedStatement ps = null;
		Connection conn = null;
		PreparedStatement ps2 = null;
		InputStream in = null;
		try {
			request.setCharacterEncoding("GBK");
			out = response.getWriter();
			
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String sqlq="select smt_usergroup.usergroupname,smt_usergroup.id,smt_user.loginname,smt_user.userid from smt_user right join smt_usergroup on smt_user.dept=smt_usergroup.id where smt_user.userid='"+cueUserid+"'";
			List<HashMap> mapBySQL = CommonQueryBS.getQueryInfoByManulSQL(sqlq);
			String usergroupname=(String)mapBySQL.get(0).get("usergroupname")+"";
			String loginname=(String)mapBySQL.get(0).get("loginname");
			String groupid=(String)mapBySQL.get(0).get("id");
			String userid=(String)mapBySQL.get(0).get("userid");
			
			
			List<FileItem> fileItems = uploader.parseRequest(request); 
			//CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if(!fi.isFormField()){
					String path = fi.getName();// �ļ�����
					String filename = path.substring(path.lastIndexOf("\\")+1);

					in = fi.getInputStream();
					Workbook wb = getWorkbok(in, null, filename);
					Sheet sheet0 = wb.getSheetAt(0);
					sess = HBUtil.getHBSession();
					conn = sess.connection();
					conn.setAutoCommit(false);
					
					String sql = "insert into CHECKREG(checkregid,regno,regname,checkdate,regstatus,publishtime,"
							+ "reguser,userid,groupid,groupname,createtime,regtype,REGOTHERID) values(?,?,?,?,?, ?,?,?,?,?, ?,?,?)";
					ps = conn.prepareStatement(sql.toString());
					Row row0 = sheet0.getRow(1);
					String id = getCellValue(row0.getCell(0));
					
					ps.setString(1, id);
					ps.setString(2, getCellValue(row0.getCell(1)));
					ps.setString(3, getCellValue(row0.getCell(2)));
					ps.setString(4, getCellValue(row0.getCell(3)));
					ps.setString(5, getCellValue(row0.getCell(4)));
					ps.setString(6, getCellValue(row0.getCell(5)));
					
					ps.setString(7, loginname);
					ps.setString(8, userid);
					ps.setString(9, groupid);
					ps.setString(10, usergroupname);
					ps.setTimestamp(11, DateUtil.getTimestamp());
					ps.setString(12, getCellValue(row0.getCell(6)));
					ps.setString(13, getCellValue(row0.getCell(7)));
					
					String sql2 = "insert into checkregperson(crp000,a0000,a3600,crp001,crp002,crp004,"
							+ "crp005,sortid1,sortid2,crp006,crp007,checkregid,crp008,crp009,crp011,"
							+ "crp012) values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?)";
					ps2 = conn.prepareStatement(sql2.toString());
					//HSSFWorkbook wb = new HSSFWorkbook(in);
					
					Sheet sheet = wb.getSheetAt(1);
					int rowNum =sheet.getLastRowNum();	//������
					int sortid1 = 0;
					int sortid2 = 0;
					String ref = "";					//����id
					a:for (int i = 1; i <= rowNum; i++) {
						Row row = sheet.getRow(i);
						if(row==null) {
							break a;
						}
						ps2.setString(1, getCellValue(row.getCell(0)));
						ps2.setString(2, getCellValue(row.getCell(1)));
						ps2.setString(3, getCellValue(row.getCell(2)));
						ps2.setString(4, getCellValue(row.getCell(3)));
						ps2.setString(5, getCellValue(row.getCell(4)));
						ps2.setString(6, getCellValue(row.getCell(5)));
						ps2.setString(7, getCellValue(row.getCell(6)));
						String c8 = getCellValue(row.getCell(7));
						ps2.setInt(8, c8!=null && !c8.trim().equals("")?Double.valueOf(c8).intValue():0);
						String c9 = getCellValue(row.getCell(8));
						ps2.setInt(9, c9!=null && !c9.trim().equals("")?Double.valueOf(c9).intValue():0);
						ps2.setString(10, getCellValue(row.getCell(9)));
						ps2.setString(11, getCellValue(row.getCell(10)));
						ps2.setString(12, getCellValue(row.getCell(11)));
						ps2.setString(13, getCellValue(row.getCell(12)));
						ps2.setString(14, getCellValue(row.getCell(13)));
						ps2.setString(15, getCellValue(row.getCell(14)));
						ps2.setString(16, getCellValue(row.getCell(15)));
						
						ps2.addBatch();
						
					}
					ps2.executeBatch();
					ps2.clearBatch();
					ps2.clearParameters();
					
					ps.executeUpdate();
					conn.commit();
				}
			}
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.parent.Ext.Msg.hide();"
					+ "parent.realParent.infoSearch();parent.parent.odin.ext.getCmp('simpleExpWin3').close();");
			out.println("</script>");
			CommonQueryBS.systemOut("�ϴ�====================");
			
		} catch (Exception e) {
			try {
				if(conn!=null)
					conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('����ʧ�ܣ�');</script>");
			e.printStackTrace();
		}finally{
			try{
				if(ps2!=null)
					ps.close();
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			if(out != null){
				out.close();
			}
		}
		
	}
	private void downloadFile_PC(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		OutputStream out = null;
	 	HBSession session  = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Workbook wb = null;
		FileInputStream in = null;
		try {
			String clientInfo = request.getHeader("User-agent");
			request.setCharacterEncoding("GBK");
			String rootpath = getRootPath();
			String checkregids = request.getParameter("checkregid");
			
			String path1 = this.disk +"temp/checkreg/"+UUID.randomUUID().toString();
			String path2 = path1+"/file/";
			File file = new File(path2);
			if(!file.exists()) {
				file.mkdirs();
			}
			String checkregidarr [] = checkregids.split(",");
			conn = session.connection();
			
			for (int i = 0; i < checkregidarr.length; i++) {
				String checkregid = checkregidarr[i];
				RecordBatch rb = (RecordBatch) session.get(RecordBatch.class, checkregid);
				if (rb == null){
					continue;
				}
				//String path3 = path2+"/"+checkregid+"/";
				String path3 = path2+"/"+rb.getRbName()+"/";
				File file2 = new File(path3);
				if(!file2.exists()) {
					file2.mkdirs();
				}
				Checkreg reg = (Checkreg) session.get(Checkreg.class, checkregid);
				String bm = reg.getGroupname();			//����
				String sj = reg.getCheckdate();			//ʱ��
				String nm = reg.getRegname();			//����
				
				in = new FileInputStream(new File(rootpath+"/pages/xbrm/jcgl/checkregexp1.xlsx"));
				wb = new XSSFWorkbook(in);
				CellStyle style = wb.createCellStyle();
				Sheet sheet = wb.getSheetAt(0);
				Row row = sheet.createRow(1);
				row.createCell(0).setCellValue(reg.getCheckregid());
				row.createCell(1).setCellValue(reg.getRegno());
				row.createCell(2).setCellValue(reg.getRegname());
				row.createCell(3).setCellValue(reg.getCheckdate());
				row.createCell(4).setCellValue(reg.getRegstatus());
				row.createCell(5).setCellValue(reg.getPublishtime());
				row.createCell(6).setCellValue(reg.getRegtype());
				row.createCell(7).setCellValue(reg.getRegotherid());
				
				stmt = conn.createStatement();
				Sheet sheet2 = wb.getSheetAt(1);
				String querysql = "select * from CHECKREGPERSON where checkregid='"+checkregid+"' order by sortid1,crp008,sortid2";
				rs = stmt.executeQuery(querysql);
				int index = 1;								//��ʼ��������
				while (rs.next()) {
					Row row_ = sheet2.createRow(index);
					row_.createCell(0).setCellValue(rs.getString("crp000"));	//id
					row_.createCell(1).setCellValue(rs.getString("a0000"));		//id
					row_.createCell(2).setCellValue(rs.getString("a3600"));		//id
					row_.createCell(3).setCellValue(rs.getString("crp001"));	//����
					row_.createCell(4).setCellValue(rs.getString("crp002"));//��ν
					row_.createCell(5).setCellValue(rs.getString("crp004"));//������λ��ְ��   ��
					row_.createCell(6).setCellValue(rs.getString("crp005"));//������ò   ��
					row_.createCell(7).setCellValue(""+rs.getInt("sortid1"));
					row_.createCell(8).setCellValue(""+rs.getInt("sortid2"));
					row_.createCell(9).setCellValue(rs.getString("crp006"));	//���֤
					row_.createCell(10).setCellValue(rs.getString("crp007"));	//��Ա���� 
					row_.createCell(11).setCellValue(checkregid);
					row_.createCell(12).setCellValue(rs.getString("crp008"));	//���id
					row_.createCell(13).setCellValue(rs.getString("crp009"));	//�Ա�
					row_.createCell(14).setCellValue(rs.getString("crp011"));	//ְ��
					row_.createCell(15).setCellValue(rs.getString("crp012"));	//��Ա���
					row_.createCell(16).setCellValue(rs.getString("crp018"));	//�������
					index++;
				}
				
				rs.close();
				
				String filename = "base.xlsx";
				FileOutputStream fout = new FileOutputStream(new File(path3+filename));
				wb.write(fout);
				fout.close();
				in.close();
			    wb.close();
			    
			  //�����ļ�
			    String querysql1 = "select CKFILEID,FDIRECTORY,FILENAME,filetype from CHECKREGFILE where checkregid='"+checkregid+
			    		"' and filetype in ('crcrj1','crfc1','crgscg1','crbx1','crzq1','crgszw1')";
				rs = stmt.executeQuery(querysql1);
				while (rs.next()) {
					String fid = rs.getString(1);
					String fdir = rs.getString(2);
					String fname = rs.getString(3);
					String ftype = rs.getString(4);
					String path = disk + fdir + fid;
					String newfname = "";
					if("crcrj1".equals(ftype)) {
						newfname = "1";
					} else if("crfc1".equals(ftype)) {
						newfname = "2";
					} else if("crbx1".equals(ftype)) {
						newfname = "3";
					} else if("crzq1".equals(ftype)) {
						newfname = "4";
					} else if("crgscg1".equals(ftype)) {
						newfname = "5";
					} else if("crgszw1".equals(ftype)) {
						newfname = "6";
					}
					String newpath = path3 + newfname +"/";
					File file1 = new File(newpath);
					if(!file1.isDirectory()) {
						file1.mkdirs();
					}
					FileUtil.copyFile(path, newpath +fname);
				}
				rs.close();
				stmt.close();
			}
			
			String zip =  path1+"/z.zip";
			SevenZipUtil.zip7z(path2, zip, null);
			
			String filenamezip = "������Ϣ.zip";
			// IE����URLEncoder��ʽ����
			if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {
			    if (clientInfo.indexOf("MSIE 6") > 0 || clientInfo.indexOf("MSIE 5") > 0) {// IE6����GBK���˴�ʵ���ɾ�����
			    	filenamezip = new String(filenamezip.getBytes("GBK"),  "ISO-8859-1");
				} else {// ie7+��URLEncoder��ʽ
					filenamezip = URLEncoder.encode(filenamezip, "utf-8");
				}
		    } else {//���������
		    	filenamezip = new String(filenamezip.getBytes("GBK"), "ISO-8859-1");
		    }
			response.reset();
			
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Content-disposition", "attachment;filename=" + filenamezip);
			response.setContentType("application/octet-stream;charset=GBK");
			
			File dfile = new File(zip);
            response.setContentLength((int)file.length());
            /*����ļ����ȴ���0*/
            if (dfile.isFile()) {
            	// ��������ʽ�����ļ���
	            InputStream fis = new BufferedInputStream(new FileInputStream(zip));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
                /*���������*/
	            out = new BufferedOutputStream(response.getOutputStream());
	            response.addHeader("Content-Length", "" + dfile.length());
		        response.setContentType("application/octet-stream");
		        out.write(buffer);
		        out.flush();
		        out.close();
            }
		} catch (Exception e) {
			e.printStackTrace();
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if(conn!=null && !conn.isClosed()){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
	private void downloadFile_xxwth(HttpServletRequest request, HttpServletResponse response) {
		if (!getLicense()) { // ��֤License ������֤�����ɵ�word�ĵ�����ˮӡ����
			return ;
		}
		response.setContentType("text/html;charset=GBK");
		ServletOutputStream out = null;
	 	HBSession session  = HBUtil.getHBSession();
		
		String doctpl = "";
		Map<String, Object> dataMap = null;
		List<String> paths = new ArrayList<String>();
		String rootpath = getRootPath();
		try {
			String clientInfo = request.getHeader("User-agent");
			request.setCharacterEncoding("GBK");
			String checkregid = request.getParameter("checkregid");
			String p = request.getParameter("p");
			
			dataMap = new HashMap<String, Object>();
			String sj = DateUtil.dateToString(new Date(), "yyyy��MM��dd��");			//ʱ��
			dataMap.put("text2", sj);
			BigDecimal count = (BigDecimal) session.createSQLQuery("select count(1) from checkregperson "
					+ " where checkregid='"+checkregid+"' and crp002='������'").uniqueResult();
			String sql = "select crp001 from (select crp001,rownum rn from checkregperson where "
					+ " checkregid='"+checkregid+"' and  crp002='������' order by sortid1) where rn<=2 ";
			List<Object> list = session.createSQLQuery(sql).list();
			String text1 = "";
			if(count.intValue() > 2) {
				text1 = list.get(0) +"��"+ list.get(1) +"��"+count.intValue()+"��";
			} else if(count.intValue() == 2) {
				text1 = list.get(0) +"��"+ list.get(1) +"2��";
			} else {
				text1 = list.get(0) +"1��";
			}
			dataMap.put("text1", text1);
			if(p.equals("1")) {
				doctpl = rootpath + "/pages/xbrm/jcgl/cxwth.doc";
			} else {
				doctpl = rootpath + "/pages/xbrm/jcgl/cxwth2.doc";
			}
			
			Document doc = new Document(doctpl);
			// ���Ӵ����������Ƭ����
			//doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
			// ��ģ���������Դ
			// doc.getMailMerge().executeWithRegions(new MapMailMergeDataSource(getMapList2(a0000), "Employees"));
			StringBuffer mapkey = new StringBuffer();
			StringBuffer mapvalue = new StringBuffer();
			for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if(StringUtil.isEmpty(value+"")){
                    	value = "@";
				}
				mapkey = mapkey.append(key + "&");
				//ȥ��&�����ַ�
				value = value.toString().replaceAll("&", "");
				mapvalue = mapvalue.append(value + "&");
			}
			// �ı���
			String[] Flds = mapkey.toString().split("&");
			// ֵ
			String[] Vals = mapvalue.toString().split("&");
			for (int j = 0; j < Vals.length; j++) {
				if(Vals[j].equals("@")){
					Vals[j] = "";
				}
			}
			
			// ��䵥������
			doc.getMailMerge().execute(Flds, Vals);
			/*File outFile = new File(expFile + (i + 1) + "_" + a0101 + ".doc");
			// д�뵽Word�ĵ���
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			// ���浽�������
			doc.save(os, SaveFormat.DOC);
			OutputStream out = new FileOutputStream(outFile);
			out.write(os.toByteArray());
			out.close();*/
			
			String filename = "�����й�������ʵ��Ϣ��ѯί�к�.doc";
			filename = filename.replace(" ", "");
			// IE����URLEncoder��ʽ����
			if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {
			    if (clientInfo.indexOf("MSIE 6") > 0 || clientInfo.indexOf("MSIE 5") > 0) {// IE6����GBK���˴�ʵ���ɾ�����
					filename = new String(filename.getBytes("GBK"),  "ISO-8859-1");
				} else {// ie7+��URLEncoder��ʽ
			        filename = URLEncoder.encode(filename, "utf-8");
				}
		    } else {//���������
		       	filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
		    }
			out = response.getOutputStream();
			response.reset();
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			response.setContentType("application/octet-stream;charset=GBK");
			doc.save(out, SaveFormat.DOC);
		} catch (Exception e) {
			e.printStackTrace();
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	
	}
	private void impPCFile(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		//Map<String ,String> formDataMap = new HashMap<String ,String>();
		
		HBSession sess = null;
		PreparedStatement ps = null;
		Connection conn = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		InputStream in = null;
		
		try {
			request.setCharacterEncoding("GBK");
			out = response.getWriter();
			
			String id = UUID.randomUUID().toString();
			String regno = URLDecoder.decode(URLDecoder.decode(request.getParameter("regno"),"utf-8"),"utf-8");
			String regname = URLDecoder.decode(URLDecoder.decode(request.getParameter("regname"),"utf-8"),"utf-8");;
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String sqlq="select smt_usergroup.usergroupname,smt_usergroup.id,smt_user.loginname,smt_user.userid from smt_user right join smt_usergroup on smt_user.dept=smt_usergroup.id where smt_user.userid='"+cueUserid+"'";
			List<HashMap> mapBySQL = CommonQueryBS.getQueryInfoByManulSQL(sqlq);
			String usergroupname=(String)mapBySQL.get(0).get("usergroupname")+"";
			String loginname=(String)mapBySQL.get(0).get("loginname");
			String groupid=(String)mapBySQL.get(0).get("id");
			String userid=(String)mapBySQL.get(0).get("userid");
			
			
			List<FileItem> fileItems = uploader.parseRequest(request); 
			//CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if(!fi.isFormField()){
					String path = fi.getName();// �ļ�����
					String filename = path.substring(path.lastIndexOf("\\")+1);

					sess = HBUtil.getHBSession();
					conn = sess.connection();
					conn.setAutoCommit(false);
					
					String id2 = UUID.randomUUID().toString();
					String directory = "checkregfiles" + File.separator +id+ File.separator;
					String filePath = directory  + id2;
					File f = new File(disk + directory);
					if(!f.isDirectory()){
						f.mkdirs();
					}
					DecimalFormat df = new DecimalFormat("#.00");
					String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
					// ����ļ�����1M����ʾM��С������ʾkb
					if (fi.getSize() < 1048576) {
						fileSize = (int) fi.getSize() / 1024 + "KB";
					}
					if (fi.getSize() < 1024) {
						fileSize = (int) fi.getSize() / 1024 + "B";
					}
					
					String sql3 = "insert into CHECKREGFILE values(?,?,?,?,?, ?,?,?)";
					ps3 = conn.prepareStatement(sql3.toString());
					int fidex = 1;
					ps3.setString(fidex++, id2);
					ps3.setString(fidex++, id);
					
					ps3.setString(fidex++, filename);
					ps3.setString(fidex++, SysManagerUtils.getUserId());
					ps3.setDate(fidex++, DateUtil.date2sqlDate(new Date()));
					ps3.setString(fidex++, directory);
					ps3.setString(fidex++, fileSize);
					ps3.setString(fidex++, "bsgsdr");
					
					fi.write(new File(disk + filePath));
					ps3.executeUpdate();
					
					
					String sql = "insert into CHECKREG(checkregid,regno,regname,checkdate,regstatus,publishtime,"
							+ "reguser,userid,groupid,groupname,createtime,regtype) values(?,?,?,?,'0', '',?,?,?,?, ?,'1')";
					ps = conn.prepareStatement(sql.toString());
					ps.setString(1, id);
					ps.setString(2, regno);
					ps.setString(3, regname);
					ps.setString(4, DateUtil.getcurdate());
					ps.setString(5, loginname);
					ps.setString(6, userid);
					ps.setString(7, groupid);
					ps.setString(8, usergroupname);
					ps.setTimestamp(9, DateUtil.getTimestamp());
					
					
					
					String sql2 = "insert into checkregperson(crp000,crp001,crp002,crp004,sortid1,sortid2,"
							+ "crp006,crp007,checkregid,crp008,crp009,crp018) values (?,?,?,?,?, ?,?,?,'"+id+"',?, ?,?)";
					ps2 = conn.prepareStatement(sql2.toString());
					//HSSFWorkbook wb = new HSSFWorkbook(in);
					//in = fi.getInputStream();
					in = new FileInputStream(new File(disk + filePath));
					Workbook wb = getWorkbok(in, null, filename);
					Sheet sheet = wb.getSheetAt(0);
					int rowNum =sheet.getLastRowNum();	//������
					int sortid1 = 0;
					int sortid2 = 0;
					String ref = "";					//����id
					a:for (int i = 3; i <= rowNum; i++) {
						Row row = sheet.getRow(i);
						if(row==null) {
							break a;
						}
						String value1 = getCellValue(row.getCell(1));
						String value2 = getCellValue(row.getCell(2));
						String value3 = getCellValue(row.getCell(3));
						String value4 = getCellValue(row.getCell(4));
						String value5 = getCellValue(row.getCell(5));
						String value6 = getCellValue(row.getCell(6));
						System.out.println(value6);
						if(value1==null) {
							break a;
						} else {
							if("������".equals(value1.trim())) {
								ref = UUID.randomUUID().toString();
								sortid1++;
								sortid2 = 0;
							}
						}
						
						
						ps2.setString(1, UUID.randomUUID().toString());
						ps2.setString(2, value2);
						
						ps2.setString(3, value1);
						ps2.setString(4, value4);
						ps2.setInt(5, sortid1);
						ps2.setInt(6, sortid2++);
						ps2.setString(7, value5);
						ps2.setString(8, (value1!=null && value1.equals("������")) ?"1":"2");
						ps2.setString(9, ref);
						ps2.setString(10, value3);
						ps2.setString(11, value6);
						ps2.addBatch();
						if(i% 10000 ==0) {
							ps2.executeBatch();
							ps2.clearBatch();
							ps2.clearParameters();
						}
					}
					ps2.executeBatch();
					ps2.clearBatch();
					ps2.clearParameters();
					
					ps.executeUpdate();
					conn.commit();
				}
			}
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.parent.Ext.Msg.hide();"
					+ "parent.realParent.infoSearch();parent.parent.odin.ext.getCmp('simpleExpWin3').close();");
			out.println("</script>");
			CommonQueryBS.systemOut("�ϴ�====================");
			
		} catch (Exception e) {
			try {
				if(conn!=null)
					conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('����ʧ�ܣ�');</script>");
			e.printStackTrace();
		}finally{
			try{
				if(ps3!=null)
					ps3.close();
				if(ps2!=null)
					ps.close();
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			if(out != null){
				out.close();
			}
		}
		
	}
	private void getXXK(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		String fileid  =request.getParameter("fileid");
		PrintWriter out = null;
		try {
			request.setCharacterEncoding("GBK");
			out = response.getWriter();
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> list = sess.createSQLQuery("select CKFILEID,FDIRECTORY,FILENAME,checkregid from CHECKREGFILE "
					+ " where FILETYPE ='"+fileid+"'").list();
			if(list.size()>0) {
				Object[] obj = list.get(0);
				out.println("<script>"
						+ "parent.setXX('"+obj[0]+"','"+obj[2]+"');");
				out.println("</script>");
			}
		}catch (Exception e) {
			e.printStackTrace();
			out.println("<script>parent.alert('�����쳣��');</script>");
		}finally{
			if(out != null){
				out.close();
			}
		}
	}
	private void deleteXXKFile(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/html;charset=GBK");
		String id  =request.getParameter("fid");
		
		PrintWriter out = null;
		HBSession sess = HBUtil.getHBSession();
		try {
			request.setCharacterEncoding("GBK");
			out = response.getWriter();
			List<Object[]> list = sess.createSQLQuery("select CKFILEID,FDIRECTORY,FILENAME,checkregid,FILETYPE from CHECKREGFILE "
					+ " where CKFILEID = '" +id + "' ").list();
			
			Object[] arr = list.get(0);
			String checkregid = arr[3]+"";
			String FILETYPE = arr[4]+"";
			String directory = disk+arr[1];
			File f = new File(directory+id);
			if(f.isFile()){
				f.delete();
			}
			sess.createSQLQuery("delete from CHECKREGFILE "
					+ " where CKFILEID = '" +id + "'").executeUpdate();
			if("jtcyk".equals(FILETYPE)) {
				sess.createSQLQuery("truncate table CHECKREGJTCYK").executeUpdate();
			} else if("ndk_pthz".equals(FILETYPE)) {
				sess.createSQLQuery("truncate table CRKPTHZ").executeUpdate();
			} else if("ndk_yscg".equals(FILETYPE)) {
				sess.createSQLQuery("truncate table CRKYSCG").executeUpdate();
			} else if(FILETYPE.equals("ndk_gattxz")) {
				sess.createSQLQuery("truncate table CRKGWTTXZ").executeUpdate();
			} else if(FILETYPE.equals("ndk_lwgat")) {
				sess.createSQLQuery("truncate table CRKlwgat").executeUpdate();
			} else if(FILETYPE.equals("ndk_yjgw")) {
				sess.createSQLQuery("truncate table CRKYJGW").executeUpdate();
			} else if(FILETYPE.equals("ndk_wyjgw")) {
				sess.createSQLQuery("truncate table CRKWYJGW").executeUpdate();
			} else if(FILETYPE.equals("ndk_fcqk")) {
				sess.createSQLQuery("truncate table CRKFC").executeUpdate();
			} else if(FILETYPE.equals("ndk_gpqk")) {
				sess.createSQLQuery("truncate table CRKGP").executeUpdate();
			} else if(FILETYPE.equals("ndk_jjqk")) {
				sess.createSQLQuery("truncate table CRKJJ").executeUpdate();
			} else if(FILETYPE.equals("ndk_tsxbx")) {
				sess.createSQLQuery("truncate table CRKTZXBX").executeUpdate();
			} else if(FILETYPE.equals("ndk_jsqk")) {
				sess.createSQLQuery("truncate table CRKJS").executeUpdate();
			}
			
			sess.flush();
			out.println("<script>parent.$('#span_id').html('&nbsp;');"
					+ "parent.alert('ɾ���ɹ���');parent.Ext.getCmp('impwBtn').enable();" );
			out.println("</script>");
		}catch (Exception e) {
			e.printStackTrace();
			out.println("<script>parent.alert('ɾ��ʧ�ܣ�');</script>");
		}finally{
			if(out != null){
				out.close();
			}
		}
		
	}
	private void impXXK(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		Map<String ,String> formDataMap = new HashMap<String ,String>();
		try {
			String fileid =request.getParameter("fileid");
			formDataMap.put("fileid", fileid);
			
			request.setCharacterEncoding("GBK");
			out = response.getWriter();
			List<FileItem> fileItems = uploader.parseRequest(request); 
			//CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if(!fi.isFormField()){
					String path = fi.getName();// �ļ�����
					formDataMap.put("Filename", path.substring(path.lastIndexOf("\\")+1));
					try {
						DecimalFormat df = new DecimalFormat("#.00");
						String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
						// ����ļ�����1M����ʾM��С������ʾkb
						if (fi.getSize() < 1048576) {
							fileSize = (int) fi.getSize() / 1024 + "KB";
						}
						if (fi.getSize() < 1024) {
							fileSize = (int) fi.getSize() / 1024 + "B";
						}
						String id = "";
						if("jtcyk".equals(fileid)) {
							id = saveFile2(formDataMap, fi,fileSize);
						} else {
							id = saveFile3(formDataMap, fi,fileSize);
						}
						formDataMap.put("file_id", id);
					} catch (Exception e) {
						throw new AppException("�ϴ�ʧ��");
					}
				}
			}
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();"
					+ "parent.setXX('"+formDataMap.get("file_id")+"','"+formDataMap.get("Filename")+"');");
			out.println("</script>");
			CommonQueryBS.systemOut("�ϴ�====================");
			
		} catch (Exception e) {
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('ʧ�ܣ�');</script>");
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
		}
	}
	private String saveFile3(Map<String, String> formDataMap, FileItem fi, String fileSize) throws RadowException {
		// �����Ա��Ϣid
		String filename = formDataMap.get("Filename");
		String classAtt = formDataMap.get("fileid");
		
		String id = UUID.randomUUID().toString();
		String directory = "checkregfiles" + File.separator +classAtt+ File.separator;
		String filePath = directory  + id;
		File f = new File(disk + directory);
		if(!f.isDirectory()){
			f.mkdirs();
		}
		HBSession sess = null;
		PreparedStatement ps = null;
		Connection conn = null;
		PreparedStatement ps2 = null;
		InputStream in = null;
		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			
			String sql = "insert into CHECKREGFILE values(?,?,?,?,?, ?,?,?)";
			ps = conn.prepareStatement(sql.toString());
			int fidex = 1;
			ps.setString(fidex++, id);
			ps.setString(fidex++, "");
			
			ps.setString(fidex++, filename);
			ps.setString(fidex++, SysManagerUtils.getUserId());
			ps.setDate(fidex++, DateUtil.date2sqlDate(new Date()));
			ps.setString(fidex++, directory);
			ps.setString(fidex++, fileSize);
			ps.setString(fidex++, classAtt);
			
			fi.write(new File(disk + filePath));
			
			String sql2 = "";
			int startrow = 3;
			int cols = 16;
			if(classAtt.equals("ndk_pthz")) {
				sql2 = "insert into CRKPTHZ values (sys_guid(),?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)";
				startrow = 3;
				cols = 16;
			} else if(classAtt.equals("ndk_yscg")) {
				sql2 = "insert into CRKYSCG values (sys_guid(),?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)";
				startrow = 3;
				cols = 16;
			} else if(classAtt.equals("ndk_gattxz")) {
				sql2 = "insert into CRKGWTTXZ values (sys_guid(),?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?)";
				startrow = 3;
				cols = 17;
			} else if(classAtt.equals("ndk_lwgat")) {
				sql2 = "insert into CRKlwgat values (sys_guid(),?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)";
				startrow = 3;
				cols = 16;
			} else if(classAtt.equals("ndk_yjgw")) {
				sql2 = "insert into CRKYJGW values (sys_guid(),?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)";
				startrow = 3;
				cols = 18;
			} else if(classAtt.equals("ndk_wyjgw")) {
				sql2 = "insert into CRKWYJGW values (sys_guid(),?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)";
				startrow = 3;
				cols = 16;
			} else if(classAtt.equals("ndk_fcqk")) {
				sql2 = "insert into CRKFC values (sys_guid(),?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?)";
				startrow = 3;
				cols = 17;
			} else if(classAtt.equals("ndk_gpqk")) {
				sql2 = "insert into CRKGP values (sys_guid(),?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)";
				startrow = 3;
				cols = 16;
			} else if(classAtt.equals("ndk_jjqk")) {
				sql2 = "insert into CRKJJ values (sys_guid(),?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)";
				startrow = 3;
				cols = 16;
			} else if(classAtt.equals("ndk_tsxbx")) {
				sql2 = "insert into CRKTZXBX values (sys_guid(),?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)";
				startrow = 3;
				cols = 16;
			} else if(classAtt.equals("ndk_jsqk")) {
				sql2 = "insert into CRKJS values (sys_guid(),?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?)";
				startrow = 3;
				cols = 25;
			}
			
			ps2 = conn.prepareStatement(sql2.toString());
			in = new FileInputStream(new File(disk + filePath));
			//HSSFWorkbook wb = new HSSFWorkbook(in);
			Workbook wb = getWorkbok(in, new File(disk + filePath), filename);
			
			Sheet sheet = wb.getSheetAt(0);
			int rowNum =sheet.getLastRowNum();	//������
			a:for (int i = startrow; i <= rowNum; i++) {
				int cidex = 1;
				Row row = sheet.getRow(i);
				if(row==null) {
					break a;
				}
				for (int j = 0; j < cols; j++) {
					Cell cell = row.getCell(j); 
					String value = getCellValue(cell);
					ps2.setString(cidex++, value);
				}
				ps2.addBatch();
				if(i% 10000 ==0) {
					ps2.executeBatch();
					ps2.clearBatch();
					ps2.clearParameters();
				}
			}
			ps2.executeBatch();
			ps2.clearBatch();
			ps2.clearParameters();
			
			ps.executeUpdate();
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			try{
				if(conn!=null)
					conn.rollback();
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new RadowException("�ϴ�����ʧ�ܣ�");
		} finally {
			try{
				if(ps2!=null)
					ps.close();
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		return id;
	}
	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";
	public static Workbook getWorkbok(InputStream in, File file, String filename) throws IOException, EncryptedDocumentException, InvalidFormatException {
		Workbook wb = null;
		if (filename.toLowerCase().endsWith(EXCEL_XLS)) { // Excel 2003
			wb = new HSSFWorkbook(in);
		} else if (filename.toLowerCase().endsWith(EXCEL_XLSX)) { // Excel 2007/2010
			wb = new XSSFWorkbook(in);
		}
		in.close();
		return wb;
	}
	private String saveFile2(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// �����Ա��Ϣid
		String filename = formDataMap.get("Filename");
		String classAtt = formDataMap.get("fileid");
		
		String id = UUID.randomUUID().toString();
		String directory = "checkregfiles" + File.separator +classAtt+ File.separator;
		String filePath = directory  + id;
		File f = new File(disk + directory);
		if(!f.isDirectory()){
			f.mkdirs();
		}
		HBSession sess = null;
		PreparedStatement ps = null;
		Connection conn = null;
		PreparedStatement ps2 = null;
		InputStream in = null;
		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			
			String sql = "insert into CHECKREGFILE values(?,?,?,?,?, ?,?,?)";
			System.out.println(sql);
			ps = conn.prepareStatement(sql.toString());
			int fidex = 1;
			ps.setString(fidex++, id);
			ps.setString(fidex++, "");
			
			ps.setString(fidex++, filename);
			ps.setString(fidex++, SysManagerUtils.getUserId());
			ps.setDate(fidex++, DateUtil.date2sqlDate(new Date()));
			ps.setString(fidex++, directory);
			ps.setString(fidex++, fileSize);
			ps.setString(fidex++, classAtt);
			
			fi.write(new File(disk + filePath));
			String sql2 = "insert into CHECKREGJTCYK values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)";
			ps2 = conn.prepareStatement(sql2.toString());
			in = new FileInputStream(new File(disk + filePath));
			//HSSFWorkbook wb = new HSSFWorkbook(in);
			Workbook wb = getWorkbok(in, new File(disk + filePath), filename);
			
			Sheet sheet = wb.getSheetAt(0);
			int rowNum =sheet.getLastRowNum();	//������
			String brsfz = "";					//�������֤
			a:for (int i = 3; i <= rowNum; i++) {
				Row row = sheet.getRow(i);
				if(row==null) {
					break a;
				}
				int cidex = 2;
				for (int j = 0; j < 15; j++) {
					Cell cell = row.getCell(j); 
					String value = getCellValue(cell);
					ps2.setString(cidex++, value);
				}
				/*Cell cell2 = row.getCell(2); 
				Cell cell6 = row.getCell(6); 
				String value2 = getCellValue(cell2);
				String value6 = getCellValue(cell6);
				if(value2==null) {
					break a;
				} else {
					if("����".equals(value2.trim())) {
						brsfz = value6;
					}
				}*/
				ps2.setString(1, UUID.randomUUID().toString());
				ps2.setInt(17, i);
				
				ps2.addBatch();
				if(i% 10000 ==0) {
					ps2.executeBatch();
					ps2.clearBatch();
					ps2.clearParameters();
				}
			}
			ps2.executeBatch();
			ps2.clearBatch();
			ps2.clearParameters();
			
			ps.executeUpdate();
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			try{
				if(conn!=null)
					conn.rollback();
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new RadowException("�ϴ�����ʧ�ܣ�");
		} finally {
			try{
				if(ps2!=null)
					ps.close();
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		return id;
	}
	/**
     * ��ȡ��Ԫ���ֵ
     * @param cell
     * @return
     */
    public  String getCellValue(Cell cell){
    	//�ж��Ƿ�Ϊnull��մ�
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        switch (cell.getCellType()) {   //����cell�е��������������  
        case HSSFCell.CELL_TYPE_NUMERIC:  
            /*DecimalFormat df = new DecimalFormat("#.##");  
            cellValue =  df.format(cell.getNumericCellValue());*/
            cellValue =  cell.getNumericCellValue() +"";
            break;  
        case HSSFCell.CELL_TYPE_STRING:  
        	cellValue= cell.getStringCellValue().trim();
            cellValue=StringUtils.isEmpty(cellValue) ? "" : cellValue; 
            break;  
        case HSSFCell.CELL_TYPE_BOOLEAN:  
        	cellValue = String.valueOf(cell.getBooleanCellValue());  
            break;  
        case HSSFCell.CELL_TYPE_FORMULA: 
            cellValue = cell.getCellFormula();
            break;  
        default:  
            System.out.println("unsuported sell type");  
        break;  
        }
        return cellValue;
       
    }
	
	private void downloadFKFile(HttpServletRequest request, HttpServletResponse response) {
		String checkregid = request.getParameter("checkregid");
		String type = request.getParameter("type");
		String root = AppConfig.HZB_PATH + "/";;
		String path = "";
		String filetype = "";
		try {
			if(type!=null) {
				if(type.equals("crjxx")) {
					filetype = "crcrj1";
				} else if(type.equals("fcxx")){
					filetype = "crfc1";
				} else if(type.equals("sybxxx")){
					filetype = "crbx1";
				} else if(type.equals("gpjjxx")){
					filetype = "crzq1";
				} else if(type.equals("gsxx1")){
					filetype = "crgscg1";
				} else if(type.equals("gsxx2")){
					filetype = "crgszw1";
				}
			}
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> list = sess.createSQLQuery("select CKFILEID,FDIRECTORY,FILENAME from CHECKREGFILE "
					+ " where checkregid = '" +checkregid + "' and filetype='"+filetype+"'").list();
			Object[] fileVO = list.get(0);
			path = root+fileVO[1]+fileVO[0];
			String filename = fileVO[2]+"";
	        /*��ȡ�ļ�*/
	       //File file = new File("C:\\Users\\LENOVO\\Desktop\\436475f1e75cc5266857e85dc1de0063.jpg");
	        /*����ļ�����*/
	        if (filename != null && !filename.equals("")) {
	            response.reset();
	            response.setHeader("pragma", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setContentType("application/octet-stream;charset=GBK");
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("GBK"), "ISO8859-1"));
	            File file = new File(path);
	            response.setContentLength((int)file.length());
	            /*����ļ����ȴ���0*/
	            if (file.isFile()) {
	            	// ��������ʽ�����ļ���
		            InputStream fis = new BufferedInputStream(new FileInputStream(path));
		            byte[] buffer = new byte[fis.available()];
		            fis.read(buffer);
		            fis.close();
	                /*���������*/
	                ServletOutputStream servletOS = response.getOutputStream();
	                servletOS.write(buffer);
	                servletOS.flush();
	                servletOS.close();
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void deleteFile(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		String id  =request.getParameter("fid");
		
		PrintWriter out = null;
		HBSession sess = HBUtil.getHBSession();
		try {
			request.setCharacterEncoding("GBK");
			out = response.getWriter();
			List<Object[]> list = sess.createSQLQuery("select CKFILEID,FDIRECTORY,FILENAME,checkregid,FILETYPE from CHECKREGFILE "
					+ " where CKFILEID = '" +id + "' ").list();
			
			Object[] arr = list.get(0);
			String checkregid = arr[3]+"";
			String FILETYPE = arr[4]+"";
			String directory = disk+arr[1];
			File f = new File(directory+id);
			if(f.isFile()){
				f.delete();
			}
			sess.createSQLQuery("delete from CHECKREGFILE "
					+ " where CKFILEID = '" +id + "'").executeUpdate();
			String updatecol = "";
			if(FILETYPE.equals("crbx1")) {
				updatecol = " sybxxx='0' ";
			} else if(FILETYPE.equals("crcrj1")) {
				updatecol = " crjxx='0' ";
			} else if(FILETYPE.equals("crfc1")) {
				updatecol = " fcxx='0' ";
			} else if(FILETYPE.equals("crgscg1")) {
				updatecol = " gsxx1='0' ";
			} else if(FILETYPE.equals("crgszw1")) {
				updatecol = " gsxx2='0' ";
			} else if(FILETYPE.equals("crzq1")) {
				updatecol = " gpjjxx='0' ";
			}
			sess.createSQLQuery("update CHECKREG set " + updatecol
					+ " where checkregid = '" +checkregid + "'").executeUpdate();
			sess.flush();
			out.println("<script>parent.$('#span_id').html('&nbsp;');"
					+ "parent.alert('ɾ���ɹ���');parent.Ext.getCmp('impwBtn').enable();" );
			out.println("</script>");
		}catch (Exception e) {
			e.printStackTrace();
			out.println("<script>parent.alert('ɾ��ʧ�ܣ�');</script>");
		}finally{
			if(out != null){
				out.close();
			}
		}
		
	}
	private void getXX(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		String checkregid =request.getParameter("checkregid");
		String fileid  =request.getParameter("fileid");
		PrintWriter out = null;
		try {
			request.setCharacterEncoding("GBK");
			out = response.getWriter();
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> list = sess.createSQLQuery("select CKFILEID,FDIRECTORY,FILENAME,checkregid from CHECKREGFILE "
					+ " where checkregid = '" +checkregid + "' and FILETYPE ='"+fileid+"'").list();
			if(list.size()>0) {
				Object[] obj = list.get(0);
				out.println("<script>"
						+ "parent.setXX('"+obj[0]+"','"+obj[2]+"');");
				out.println("</script>");
			}
		}catch (Exception e) {
			e.printStackTrace();
			out.println("<script>parent.alert('�����쳣��');</script>");
		}finally{
			if(out != null){
				out.close();
			}
		}
	
	}

	public static String  disk = AppConfig.HZB_PATH + "/";
	private void impFK(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = null;
		Map<String ,String> formDataMap = new HashMap<String ,String>();
		try {
			String checkregid =request.getParameter("checkregid");
			String fileid =request.getParameter("fileid");
			formDataMap.put("fileid", fileid);
			formDataMap.put("checkregid", checkregid);
			
			request.setCharacterEncoding("GBK");
			out = response.getWriter();
			List<FileItem> fileItems = uploader.parseRequest(request); 
			//CommonQueryBS.systemOut(fileItems.size()+"");
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if(!fi.isFormField()){
					String path = fi.getName();// �ļ�����
					formDataMap.put("Filename", path.substring(path.lastIndexOf("\\")+1));
					System.out.println(path.substring(path.lastIndexOf("\\")+1));
					try {
						DecimalFormat df = new DecimalFormat("#.00");
						String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
						// ����ļ�����1M����ʾM��С������ʾkb
						if (fi.getSize() < 1048576) {
							fileSize = (int) fi.getSize() / 1024 + "KB";
						}
						if (fi.getSize() < 1024) {
							fileSize = (int) fi.getSize() / 1024 + "B";
						}
						String id = saveFile(formDataMap, fi,fileSize);
						formDataMap.put("file_id", id);
					} catch (Exception e) {
						throw new AppException("�ϴ�ʧ��");
					}
				}
			}
			CommonQueryBS.systemOut("INSERT END---------" +DateUtil.getTime());
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();"
					+ "parent.setXX('"+formDataMap.get("file_id")+"','"+formDataMap.get("Filename")+"');");
			out.println("</script>");
			CommonQueryBS.systemOut("�ϴ�====================");
			
		} catch (Exception e) {
			out.println("<script>parent.odin.ext.get(parent.document.body).unmask();parent.odin.alert('ʧ�ܣ�');</script>");
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
		}
	}
	
	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// �����Ա��Ϣid
		String filename = formDataMap.get("Filename");
		String classAtt = formDataMap.get("fileid");
		
		String checkregid = formDataMap.get("checkregid");
		String id = UUID.randomUUID().toString();
		String directory = "checkregfiles" + File.separator +checkregid+ File.separator;
		String filePath = directory  + id;
		File f = new File(disk + directory);
		if(!f.isDirectory()){
			f.mkdirs();
		}
		HBSession sess = null;
		PreparedStatement ps = null;
		Statement stmt = null;
		Connection conn = null;
		//InputStream in = null;
		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			
			String sql = "insert into CHECKREGFILE values(?,?,?,?,?, ?,?,?)";
			System.out.println(sql);
			ps = conn.prepareStatement(sql.toString());
			int fidex = 1;
			ps.setString(fidex++, id);
			ps.setString(fidex++, checkregid);
			
			ps.setString(fidex++, filename);
			ps.setString(fidex++, SysManagerUtils.getUserId());
			ps.setDate(fidex++, DateUtil.date2sqlDate(new Date()));
			ps.setString(fidex++, directory);
			ps.setString(fidex++, fileSize);
			ps.setString(fidex++, classAtt);
			
			fi.write(new File(disk + filePath));
			ps.executeUpdate();
			
			String updatecol = "";
			if(classAtt.equals("crbx1")) {
				updatecol = " sybxxx='1' ";
			} else if(classAtt.equals("crcrj1")) {
				updatecol = " crjxx='1' ";
			} else if(classAtt.equals("crfc1")) {
				updatecol = " fcxx='1' ";
			} else if(classAtt.equals("crgscg1")) {
				updatecol = " gsxx1='1' ";
			} else if(classAtt.equals("crgszw1")) {
				updatecol = " gsxx2='1' ";
			} else if(classAtt.equals("crzq1")) {
				updatecol = " gpjjxx='1' ";
			}
			stmt = conn.createStatement();
			stmt.execute("update CHECKREG set " + updatecol
					+ " where checkregid = '" +checkregid + "'");
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			try{
				if(conn!=null)
					conn.rollback();
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new RadowException("�ϴ�����ʧ�ܣ�");
		} finally {
			try{
				if(ps!=null)
					ps.close();
				if(stmt!=null)
					stmt.close();
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		return id;
	}
	private void downloadFile_chdbzd(HttpServletRequest request, HttpServletResponse response) {
		HBSession sess = HBUtil.getHBSession();
		String clientInfo = request.getHeader("User-agent");
		
		String rootpath = getRootPath();
		ServletOutputStream out = null;
		String checkregid = request.getParameter("checkregid");
		InputStream in = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		//HSSFWorkbook workbook_new = null;
		try {
			request.setCharacterEncoding("GBK");
			
			Map<String, String> fcMap = getFcMap(checkregid,sess);			//����
			Map<String, BigDecimal[]> gjbMap = getGjbMap(checkregid,sess); 	//��Ʊ������
			Map<String, String[]> gsMap = getGsMap(checkregid,sess); 		//����
			Map<String, String[]> crjzjMap = getCrjzjMap(checkregid,sess); 	//crj
			Map<String, String[]> crjjlMap = getCrjjlMap(checkregid,sess); 	//crj��¼
			
			//----------------- ��ȡ��ȿ����� ------------------------------
			Map<String, String[]> k_crjzjMap = getKCrjzjMap(checkregid,sess);
			Map<String, String> k_crjjlMap = getKCrjjlMap(checkregid,sess);
			Map<String, String[]> k_crjjlMap2 = getKCrjjlMap2(checkregid,sess);
			Map<String, String> k_FcMap = getKFcMap(checkregid,sess);
			Map<String, BigDecimal[]> k_gjbMap = getKGjbMap(checkregid,sess);
			Map<String, String[]> k_gsMap = getKGsMap(checkregid,sess);
			Map<String, String> k_yjgwMap = getKYjMap(checkregid,sess);
			Map<String, String> k_wyjgwMap = getKWyjMap(checkregid,sess);
			
			conn = sess.connection();
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			in = new FileInputStream(new File(rootpath+"/pages/xbrm/jcgl/chdbb_zdch.xls"));
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet sheet_old = workbook.getSheetAt(0);
			//workbook_new = new HSSFWorkbook();
			//List<CellRangeAddress> originMerged = sheet_old.getMergedRegions();
			
			String nd = DateUtil.dateToString(new Date(), "yyyy");
			int count = 1;
			
			String querysql = "select * from CHECKREGPERSON where checkregid='"+checkregid+"' and crp007='1' order by sortid1,crp008,sortid2";
			rs = stmt.executeQuery(querysql);
			while (rs.next()) {
				String crp001 = rs.getString("crp001");
				String crp004 = rs.getString("crp004");
				String crp008 = rs.getString("crp008");
				
				//HSSFCellStyle headstyle = workbook.createCellStyle();
				HSSFSheet new_sheet = workbook.createSheet(count +"��" + crp001);
				ExcelCopyOrMerged.copySheet(workbook, sheet_old, new_sheet, true);
				new_sheet.getRow(0).getCell(0).setCellValue(nd+"-" + (count++));
				new_sheet.getRow(3).getCell(1).setCellValue(crp001);
				new_sheet.getRow(3).getCell(3).setCellValue(crp004);
				String crp018 = rs.getString("crp018");
				new_sheet.getRow(5).getCell(1).setCellValue("�ʱ�䣺"+DateUtil.dateToString(stringToDate_Size6_8(crp018), "yyyy��MM��dd��"));
				new_sheet.getRow(2).getCell(0).setCellValue("��������--"+nd+"�꣩ ");
				new_sheet.getRow(12).getCell(3).setCellValue(fcMap.get(crp008)!=null ? fcMap.get(crp008)+"" :"�޴������");
				if(gjbMap.containsKey(crp008)) {
					BigDecimal[] zjs = gjbMap.get(crp008);
					BigDecimal z0 = zjs[0];
					BigDecimal z1 = zjs[1];
					BigDecimal z2 = zjs[2];
					BigDecimal z3 = zjs[3];
					BigDecimal z4 = zjs[4];
					BigDecimal z5 = zjs[5];
					BigDecimal z6 = zjs[6];
					BigDecimal z7 = zjs[7];
					BigDecimal z8 = zjs[8];
					BigDecimal z9 = zjs[9];
					BigDecimal z10 = zjs[10];
					BigDecimal z11 = zjs[11];
					StringBuffer gjbmsg = new StringBuffer();
					if(z0!=null || z1!=null  || z2!=null) {
						gjbmsg.append("����");
						if(z0!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(Math.round(z0.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z1!=null) {
							gjbmsg.append("��������ֵ").append(Math.round(z1.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z2!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(Math.round(z2.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(z3!=null || z4!=null  || z5!=null) {
						gjbmsg.append("��ż����");
						if(z3!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(Math.round(z3.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z4!=null) {
							gjbmsg.append("��������ֵ").append(Math.round(z4.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z5!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(Math.round(z5.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(z6!=null || z7!=null  || z8!=null) {
						gjbmsg.append("��Ů����");
						if(z6!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(Math.round(z6.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z7!=null) {
							gjbmsg.append("��������ֵ").append(Math.round(z7.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z8!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(Math.round(z8.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(z9!=null || z10!=null  || z11!=null) {
						gjbmsg.append("��Ů����ż����");
						if(z9!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(Math.round(z9.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z10!=null) {
							gjbmsg.append("��������ֵ").append(Math.round(z10.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z11!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(Math.round(z11.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(gjbmsg.length()>4) {
						new_sheet.getRow(13).getCell(3).setCellValue(gjbmsg.substring(0,gjbmsg.length()-3) + "");
					} else {
						new_sheet.getRow(13).getCell(3).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(13).getCell(3).setCellValue("�޴������");
				}
				if(gsMap.containsKey(crp008)) {
					String[] arr = gsMap.get(crp008);
					String msg = (arr[0]!=null? arr[0]+"\r\n" :"") + (arr[1]!=null? arr[1]+"\r\n" :"") +
							(arr[2]!=null? arr[2]+"\r\n" :"") + (arr[6]!=null? arr[6]+"\r\n" :"") +
							(arr[3]!=null? arr[3]+"\r\n" :"") + (arr[4]!=null? arr[4]+"\r\n" :"") + 
							(arr[5]!=null? arr[5]+"\r\n" :"") + (arr[7]!=null? arr[7]+"\r\n" :"");
					if(msg.trim().length()>1) {
						new_sheet.getRow(14).getCell(3).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(14).getCell(3).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(14).getCell(3).setCellValue("�޴������");
				}
				if(crjzjMap.containsKey(crp008)) {
					String[] arr = crjzjMap.get(crp008);
					String msg = "����" + (arr[2]!=null? arr[2]+"" :"")  ;
					if(msg.trim().length()>4) {
						new_sheet.getRow(6).getCell(3).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(6).getCell(3).setCellValue("�޴������");
					}
					String msg2 = "����" + 
							(arr[0]!=null? arr[0]+"" :"") +(arr[1]!=null? arr[1]+"" :"")  ;
					if(msg2.trim().length()>4) {
						new_sheet.getRow(8).getCell(3).setCellValue(msg2.substring(0,msg2.length()-3) + "");
					} else {
						new_sheet.getRow(8).getCell(3).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(6).getCell(3).setCellValue("�޴������");
					new_sheet.getRow(8).getCell(3).setCellValue("�޴������");
				}
				if(crjjlMap.containsKey(crp008)) {
					String[] arr = crjjlMap.get(crp008);
					String msg = (arr[0]!=null? arr[0]+"" :"");
					if(msg.trim().length()>1) {
						new_sheet.getRow(7).getCell(3).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(7).getCell(3).setCellValue("�޴������");
					}
					String msg2 = (arr[1]!=null? arr[1]+"" :"")  ;
					if(msg2.trim().length()>1) {
						new_sheet.getRow(9).getCell(3).setCellValue(msg2.substring(0,msg2.length()-3) + "");
					} else {
						new_sheet.getRow(9).getCell(3).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(7).getCell(3).setCellValue("�޴������");
					new_sheet.getRow(9).getCell(3).setCellValue("�޴������");
				}
				
				//-----------  ��ȿ������� ----------------------------------------------------
				new_sheet.getRow(12).getCell(1).setCellValue(k_FcMap.get(crp008)!=null ? k_FcMap.get(crp008)+"" :"�޴������");
				if(k_gjbMap.containsKey(crp008)) {
					BigDecimal[] zjs = k_gjbMap.get(crp008);
					BigDecimal z0 = zjs[0];
					BigDecimal z1 = zjs[1];
					BigDecimal z2 = zjs[2];
					BigDecimal z3 = zjs[3];
					BigDecimal z4 = zjs[4];
					BigDecimal z5 = zjs[5];
					BigDecimal z6 = zjs[6];
					BigDecimal z7 = zjs[7];
					BigDecimal z8 = zjs[8];
					BigDecimal z9 = zjs[9];
					BigDecimal z10 = zjs[10];
					BigDecimal z11 = zjs[11];
					StringBuffer gjbmsg = new StringBuffer();
					if(z0!=null || z1!=null  || z2!=null) {
						gjbmsg.append("����");
						if(z0!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(z0.doubleValue()).append("��Ԫ��");
						}
						if(z1!=null) {
							gjbmsg.append("��������ֵ").append(z1.doubleValue()).append("��Ԫ��");
						}
						if(z2!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(z2.doubleValue()).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(z3!=null || z4!=null  || z5!=null) {
						gjbmsg.append("��ż����");
						if(z3!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(z3.doubleValue()).append("��Ԫ��");
						}
						if(z4!=null) {
							gjbmsg.append("��������ֵ").append(z4.doubleValue()).append("��Ԫ��");
						}
						if(z5!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(z5.doubleValue()).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(z6!=null || z7!=null  || z8!=null) {
						gjbmsg.append("��Ů����");
						if(z6!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(z6.doubleValue()).append("��Ԫ��");
						}
						if(z7!=null) {
							gjbmsg.append("��������ֵ").append(z7.doubleValue()).append("��Ԫ��");
						}
						if(z8!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(z8.doubleValue()).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(z9!=null || z10!=null  || z11!=null) {
						gjbmsg.append("��Ů����ż����");
						if(z9!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(z9.doubleValue()).append("��Ԫ��");
						}
						if(z10!=null) {
							gjbmsg.append("��������ֵ").append(z10.doubleValue()).append("��Ԫ��");
						}
						if(z11!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(z11.doubleValue()).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(gjbmsg.length()>4) {
						new_sheet.getRow(13).getCell(1).setCellValue(gjbmsg.substring(0,gjbmsg.length()-3) + "");
					} else {
						new_sheet.getRow(13).getCell(1).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(13).getCell(1).setCellValue("�޴������");
				}
				if(k_gsMap.containsKey(crp008)) {
					String[] arr = k_gsMap.get(crp008);
					String msg = (arr[0]!=null? arr[0]+"\r\n" :"") + (arr[1]!=null? arr[1]+"\r\n" :"")
							+(arr[2]!=null? arr[2]+"\r\n" :"") + (arr[3]!=null? arr[3]+"\r\n" :"") + 
							(arr[4]!=null? arr[4]+"\r\n" :"") + (arr[5]!=null? arr[5]+"\r\n" :"") +
							(arr[6]!=null? arr[6]+"\r\n" :"") + (arr[7]!=null? arr[7]+"\r\n" :"");
					if(msg.trim().length()>1) {
						new_sheet.getRow(14).getCell(1).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(14).getCell(1).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(14).getCell(1).setCellValue("�޴������");
				}
				if(k_crjzjMap.containsKey(crp008)) {
					String[] arr = k_crjzjMap.get(crp008);
					String msg = "����" + (arr[2]!=null? arr[2]+"" :"") ;
					if(msg.trim().length()>4) {
						new_sheet.getRow(6).getCell(1).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(6).getCell(1).setCellValue("�޴������");
					}
					String msg2 = "����" + 
							(arr[0]!=null? arr[0]+"" :"") +(arr[1]!=null? arr[1]+"" :"")  ;
					if(msg2.trim().length()>4) {
						new_sheet.getRow(8).getCell(1).setCellValue(msg2.substring(0,msg2.length()-3) + "");
					} else {
						new_sheet.getRow(8).getCell(1).setCellValue("�޴������");
						
					}
				} else {
					new_sheet.getRow(6).getCell(1).setCellValue("�޴������");
					new_sheet.getRow(8).getCell(1).setCellValue("�޴������");
				}
				if(k_crjjlMap.containsKey(crp008)) {
					String arr = k_crjjlMap.get(crp008);
					String msg = "" + (arr!=null && !arr.equals("")? arr+"" :"") ;
					if(msg.trim().length()>2) {
						new_sheet.getRow(7).getCell(1).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(7).getCell(1).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(7).getCell(1).setCellValue("�޴������");
				}
				if(k_crjjlMap2.containsKey(crp008)) {
					String arr[] = k_crjjlMap2.get(crp008);
					String msg = "" + (arr[0]!=null && !arr[0].equals("")? arr[0]+"" :"") +
							(arr[1]!=null && !arr[1].equals("")? arr[1]+"" :"");
					if(msg.trim().length()>2) {
						new_sheet.getRow(9).getCell(1).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(9).getCell(1).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(9).getCell(1).setCellValue("�޴������");
				}
				if(k_yjgwMap.containsKey(crp008)) {
					String arr = k_yjgwMap.get(crp008);
					String msg = "" + (arr!=null && !arr.equals("")? arr+"" :"");
					if(msg.trim().length()>2) {
						new_sheet.getRow(10).getCell(1).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(10).getCell(1).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(10).getCell(1).setCellValue("�޴������");
				}
				if(k_wyjgwMap.containsKey(crp008)) {
					String arr = k_wyjgwMap.get(crp008);
					String msg = "" + (arr!=null && !arr.equals("")? arr+"" :"");
					if(msg.trim().length()>2) {
						new_sheet.getRow(11).getCell(1).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(11).getCell(1).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(11).getCell(1).setCellValue("�޴������");
				}
				
				
			}
			workbook.setActiveSheet(1);
			workbook.setSheetHidden(0, true);
			String filename = "��˱ȶԱ�--�ص���.xls";
			filename = filename.replace(" ", "");
			// IE����URLEncoder��ʽ����
			if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {
			    if (clientInfo.indexOf("MSIE 6") > 0 || clientInfo.indexOf("MSIE 5") > 0) {// IE6����GBK���˴�ʵ���ɾ�����
					filename = new String(filename.getBytes("GBK"),  "ISO-8859-1");
				} else {// ie7+��URLEncoder��ʽ
			        filename = URLEncoder.encode(filename, "utf-8");
				}
		    } else {//���������
		       	filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
		    }
			
			out = response.getOutputStream();
			
			response.reset();
			
			
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			response.setContentType("application/octet-stream;charset=GBK");
			workbook.write(out);
		    out.close();
		    workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	private Map<String, String[]> getCrjjlMap(String checkregid, HBSession sess) {
		//Checkreg cr = (Checkreg) sess.get(Checkreg.class, checkregid);
		//String rq = cr.getCheckdate();
		String rq = DateUtil.getcurdate();
		Date date = stringToDate_Size6_8(rq);
		String sql = "select crp008,crp002,CRCRJjl003,CRCRJjl006,CRCRJjl007 from checkregperson p,checkregcrjjl f " + 
				" where p.crp006=f.CRCRJjl008 and crp002='������' and p.checkregid=f.checkregid and p.checkregid='"+checkregid+"' order by sortid";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		Map<String, String[]> map = new HashMap<String, String[]>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			String id = obj[0].toString();
			String yxq = obj[2].toString();
			String gj = obj[3]!=null ?obj[3].toString():"";
			String dd = obj[4]!=null ?obj[4].toString():"";
			if(Integer.parseInt(rq.substring(0, 4))-1<=Integer.parseInt(yxq.substring(0, 4))) {
				String str = yxq+"����"+(gj!=null&&!gj.equals("")?gj:" ")+"����"+(dd!=null&&!dd.equals("")?dd:" ")+"��"+"\r\n";
				String[] arr = null;
				if(map.containsKey(id)) {
					arr = map.get(id);
				} else {
					arr = new String[2];
				}
				
				if(dd.toUpperCase().contains("TW") || dd.toUpperCase().contains("HK")
						|| dd.toUpperCase().contains("MAC")) {
					String old = arr[1];
					arr[1] = (old!=null?old:"")+str;
				} else {
					String old = arr[0];
					arr[0] = (old!=null?old:"")+str;
				}
				map.put(id, arr);
			}
		}
		return map;
	}
	private Map<String, String> getKWyjMap(String checkregid, HBSession sess) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			conn = sess.connection();
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			String sql = "select crp008, CRKwYJGW09,CRKwYJGW10,CRKwYJGW11,CRKwYJGW13 from checkregperson p, crkwyjgw f " + 
					" where p.crp006=f.CRKWYJGW02 and crp002='������' and p.checkregid='"+checkregid+"'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				String xm = rs.getString(2);
				String cw = rs.getString(3);
				String dd = rs.getString(4);
				String sj = rs.getString(5);
				String msg = cw +sj+"����"+dd+"ͣ��һ�����ϣ�"+"\r\n";
				String arr = null;
				if(map.containsKey(id)) {
					arr = map.get(id);
					arr = arr+ msg;
				} else {
					arr = msg;
				}
				map.put(id, arr);
			}
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	private Map<String, String> getKYjMap(String checkregid, HBSession sess) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			conn = sess.connection();
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			String sql = "select crp008, CRKYJGW09,CRKYJGW10,CRKYJGW11,CRKYJGW13,CRKYJGW15 from checkregperson p, crkyjgw f " + 
					" where p.crp006=f.CRKYJGW02 and crp002='������' and p.checkregid='"+checkregid+"'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				String xm = rs.getString(2);
				String cw = rs.getString(3);
				String dd = rs.getString(4);
				String zj = rs.getString(5);
				String sj = rs.getString(6);
				String msg = cw + sj +"���ƾ�"+dd+"������"+zj+"��"+"\r\n";
				String arr = null;
				if(map.containsKey(id)) {
					arr = map.get(id);
					arr = arr!=null&&!arr.equals("")?arr:""+ msg;
				} else {
					arr = msg;
				}
				map.put(id, arr);
			}
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	private Map<String, String[]> getKCrjjlMap2(String checkregid, HBSession sess) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, String[]> map = new HashMap<String, String[]>();
		try {
			conn = sess.connection();
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			String sql = "select crp008, CRKLWGAT09,CRKLWGAT10,CRKLWGAT11,CRKLWGAT13 from checkregperson p, CRKLWGAT f " + 
					" where p.crp006=f.CRKLWGAT02 and crp002='������' and p.checkregid='"+checkregid+"'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				String t1 = rs.getString(2);
				String t2 = rs.getString(3);
				String dd = rs.getString(4);
				String jg = rs.getString(5);
				String[] arr = null;
				if(map.containsKey(id)) {
					arr = map.get(id);
				} else {
					arr = new String[2];
				}
				String msg = ""+t1+"-"+t2+"����"+dd+"";
				msg = msg+ (jg!=null && !jg.equals("")?",��"+jg+"��׼��"+"\r\n" :"��"+"\r\n");
				if(dd.contains("̨��")) {
					arr[0] = arr[0]!=null&&!arr[0].equals("")?arr[0]:"" + msg;
				} else {
					arr[1] = arr[1]!=null&&!arr[1].equals("")?arr[1]:"" + msg;
				}
				map.put(id, arr);
			}
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	private Map<String, String[]> getKGsMap(String checkregid, HBSession sess) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, String[]> map = new HashMap<String, String[]>();
		try {
			conn = sess.connection();
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			/*String sql = "select crp008,CRKJS10,CRKJS12,CRKJS19,CRKJS20,CRKJS21 || '%',CRKJS22,CRKJS25 from checkregperson p, " + 
					"(select CRKJS02,CRKJS12,CRKJS19,CRKJS20,CRKJS21,CRKJS22,CRKJS25,case when CRKJS10 in('����','������') " + 
					" then '������' when CRKJS10 in('�ɷ�','����','��ż') then '��ż' else '��Ů' end CRKJS10 from crkjs) f " + 
					" where p.crp006  =f.CRKJS02 and CRKJS22 in ('����','����') and p.checkregid='"+checkregid+"'";*/
			String sql = "select crp008,CRKJS10,CRKJS12,CRKJS19,CRKJS20,CRKJS21 || '%',CRKJS22,CRKJS25 from checkregperson p, " + 
					"(select CRKJS02,CRKJS12,CRKJS19,CRKJS20,CRKJS21,CRKJS22,CRKJS25,case when CRKJS10 in('����','������') " + 
					" then '������' when CRKJS10 in('�ɷ�','����','��ż') then '��ż' when CRKJS10 in('��ϱ','Ů��') then '��Ů����ż' else '��Ů' end CRKJS10 from crkjs) f " + 
					" where p.crp006  =f.CRKJS02 and CRKJS22 in ('����','����') and p.checkregid='"+checkregid+"'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				String dx = rs.getString(2);
				String dw = rs.getString(3);
				Double zb = rs.getDouble(4);
				String cz = rs.getString(5);
				String zhb = rs.getString(6);
				String zt = rs.getString(7);
				//String sj = rs.getString(8);
				String arr[] = null;
				if(map.containsKey(id)) {
					arr = map.get(id);
				} else {
					arr = new String[8];
				}
				String msg = "����"+dw+cz+"��Ԫ����ҵע���ʱ�"+zb+"��Ԫ�����˳��ʱ���"+zhb+(zt.equals("����")?("(����)"):"")+"��";
				if(dx.equals("������")) {
					String old = arr[0];
					arr[0] = (old!=null?old:"") +"����"+ msg;
				} else if(dx.equals("��ż")) {
					String old = arr[1];
					arr[1] = (old!=null?old:"") +"��ż"+ msg;
				} else if(dx.equals("��Ů")) {
					String old = arr[2];
					arr[2] = (old!=null?old:"") +"��Ů"+ msg;
				} else if(dx.equals("��Ů����ż")) {
					String old = arr[6];
					arr[6] = (old!=null?old:"") +"��Ů����ż"+ msg;
				} 
				map.put(id, arr);
			}
			rs.close();
			
			/*String sql2 = "select crp008,crp002,CRGSZW005,CRGSZW006/10000,CRGSZW013,CRGSZW015"
					+ " from checkregperson p,checkreggszw f  where p.crp006=f.CRGSZW003"
					+ " and CRGSZW013<>'ע��'and p.checkregid='"+checkregid+"'";
			rs = stmt.executeQuery(sql2);
			while (rs.next()) {
				String id = rs.getString(1);
				String dx = rs.getString(2);
				String dw = rs.getString(3);
				Double zb = rs.getDouble(4);
				String zt = rs.getString(5);
				String sj = rs.getString(6);
				String arr[] = null;
				if(map.containsKey(id)) {
					arr = map.get(id);
				} else {
					arr = new String[6];
				}
				String msg = "����"+dw+"�ķ��ˣ������˻�Ӫ�ߣ�����ҵע���ʱ�"+zb+"��Ԫ"+(zt.equals("����")?("(����"+sj+")"):"")+"��";
				if(dx.equals("������")) {
					String old = arr[3];
					arr[3] = (old!=null?old:"") +"����"+ msg;
				} else if(dx.equals("��ż")) {
					String old = arr[4];
					arr[4] = (old!=null?old:"") +"��ż"+ msg;
				} else if(dx.equals("��Ů")) {
					String old = arr[5];
					arr[5] = (old!=null?old:"") +"��Ů"+ msg;
				} else if(dx.equals("��Ů����ż")) {
					String old = arr[7];
					arr[7] = (old!=null?old:"") +"��Ů����ż"+ msg;
				} 
				map.put(id, arr);
			}
			rs.close();*/
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	private Map<String, BigDecimal[]> getKGjbMap(String checkregid, HBSession sess) {
		// ��Ʊ
		/*String sql = "select crp008,CRKGP10,sum(CRKGP14) from checkregperson p,(select CRKGP02,case when CRKGP10 " + 
				" in('����','������') then '������' when CRKGP10 in('�ɷ�','����','��ż') then '��ż' else '��Ů' end CRKGP10,CRKGP14 from crkgp) f " + 
				" where p.crp006=f.CRKGP02 and p.checkregid='"+checkregid+"' group by crp008,CRKGP10";*/
		String sql = "select crp008,CRKGP10,sum(CRKGP14) from checkregperson p,(select CRKGP02,case when CRKGP10 " + 
				" in('����','������') then '������' when CRKGP10 in('�ɷ�','����','��ż') then '��ż' when CRKGP10 in('Ů��','��ϱ') then '��Ů����ż' else '��Ů' end CRKGP10,CRKGP14 from crkgp) f " + 
				" where p.crp006=f.CRKGP02 and p.checkregid='"+checkregid+"' group by crp008,CRKGP10";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		Map<String, BigDecimal[]> map = new HashMap<String, BigDecimal[]>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			String id = obj[0].toString();
			String dx = obj[1].toString().replace(" ", "");
			BigDecimal zj = (BigDecimal) obj[2];
			if(map.containsKey(id)) {
				BigDecimal[] arr = map.get(id);
				if(dx.equals("������")) {
					arr[0] = zj;
				} else if(dx.equals("��ż")) {
					arr[3] = zj;
				} else if(dx.equals("��Ů")) {
					arr[6] = zj;
				} else if(dx.equals("��Ů����ż")) {
					arr[9] = zj;
				}
			} else {
				BigDecimal[] arr = new BigDecimal[12];
				if(dx.equals("������")) {
					arr[0] = zj;
				} else if(dx.equals("��ż")) {
					arr[3] = zj;
				} else if(dx.equals("��Ů")) {
					arr[6] = zj;
				} else if(dx.equals("��Ů����ż")) {
					arr[9] = zj;
				}
				map.put(id, arr);
			}
		}
		//����
		/*String sql3 = "select crp008,CRKJJ10,sum(CRKJJ14) from checkregperson p,(select CRKJJ02,CRKJJ14, case when CRKJJ10 " + 
				"in('����','������') then '������' when CRKJJ10 in('�ɷ�','����','��ż') then '��ż' else '��Ů' end CRKJJ10 from crkjj) f  " + 
				" where p.crp006=f.CRKJJ02 and p.checkregid='"+checkregid+"' group by crp008,CRKJJ10";*/
		String sql3 = "select crp008,CRKJJ10,sum(CRKJJ14) from checkregperson p,(select CRKJJ02,CRKJJ14, case when CRKJJ10 " + 
				"in('����','������') then '������' when CRKJJ10 in('�ɷ�','����','��ż') then '��ż' when CRKJJ10 in('Ů��','��ϱ') then '��Ů����ż' else '��Ů' end CRKJJ10 from crkjj) f  " + 
				" where p.crp006=f.CRKJJ02 and p.checkregid='"+checkregid+"' group by crp008,CRKJJ10";
		List<Object[]> list3 = sess.createSQLQuery(sql3).list();
		for (int i = 0; i < list3.size(); i++) {
			Object[] obj = list3.get(i);
			String id = obj[0].toString();
			String dx = obj[1].toString().replace(" ", "");
			BigDecimal zj = (BigDecimal) obj[2];
			if(map.containsKey(id)) {
				BigDecimal[] arr = map.get(id);
				if(dx.equals("������")) {
					arr[1] = zj;
				} else if(dx.equals("��ż")) {
					arr[4] = zj;
				} else if(dx.equals("��Ů")) {
					arr[7] = zj;
				} else if(dx.equals("��Ů����ż")) {
					arr[10] = zj;
				}
			} else {
				BigDecimal[] arr = new BigDecimal[12];
				if(dx.equals("������")) {
					arr[1] = zj;
				} else if(dx.equals("��ż")) {
					arr[4] = zj;
				} else if(dx.equals("��Ů")) {
					arr[7] = zj;
				} else if(dx.equals("��Ů����ż")) {
					arr[10] = zj;
				}
				map.put(id, arr);
			}
		}
		
		/*String sql2 = "select crp008,CRKTZXBX10,sum(CRKTZXBX14) from checkregperson p,(select CRKTZXBX02,CRKTZXBX14,case when CRKTZXBX10  " + 
				" in('����','������') then '������' when CRKTZXBX10 in('�ɷ�','����','��ż') then '��ż' else '��Ů' end CRKTZXBX10 " + 
				" from crktzxbx) f where p.crp006=f.CRKTZXBX02 and p.checkregid='"+checkregid+"' group by crp008,CRKTZXBX10";*/
		String sql2 = "select crp008,CRKTZXBX10,sum(CRKTZXBX14) from checkregperson p,(select CRKTZXBX02,CRKTZXBX14,case when CRKTZXBX10  " + 
				" in('����','������') then '������' when CRKTZXBX10 in('�ɷ�','����','��ż') then '��ż' when CRKTZXBX10 in('Ů��','��ϱ') then '��Ů����ż' else '��Ů' end CRKTZXBX10 " + 
				" from crktzxbx) f where p.crp006=f.CRKTZXBX02 and p.checkregid='"+checkregid+"' group by crp008,CRKTZXBX10";
		List<Object[]> list2 = sess.createSQLQuery(sql2).list();
		for (int i = 0; i < list2.size(); i++) {
			Object[] obj = list2.get(i);
			String id = obj[0].toString();
			String dx = obj[1].toString().replace(" ", "");
			BigDecimal zj = (BigDecimal) obj[2];
			if(map.containsKey(id)) {
				BigDecimal[] arr = map.get(id);
				if(dx.equals("������")) {
					arr[2] = zj;
				} else if(dx.equals("��ż")) {
					arr[5] = zj;
				} else if(dx.equals("��Ů")) {
					arr[8] = zj;
				} else if(dx.equals("��Ů����ż")) {
					arr[11] = zj;
				}
			} else {
				BigDecimal[] arr = new BigDecimal[12];
				if(dx.equals("������")) {
					arr[2] = zj;
				} else if(dx.equals("��ż")) {
					arr[5] = zj;
				} else if(dx.equals("��Ů")) {
					arr[8] = zj;
				} else if(dx.equals("��Ů����ż")) {
					arr[11] = zj;
				}
				map.put(id, arr);
			}
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	private Map<String, String> getKFcMap(String checkregid, HBSession sess) {
		String sql = "select crp008,to_char(wm_concat(CRKFC11)),to_char(wm_concat(CRKFC12)),to_char(wm_concat(CRKFC10)) from checkregperson p,"
				+ "CRKFC f where p.crp006=f.CRKFC02 and p.checkregid='"+checkregid+
				"' group by crp008";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			String id = obj[0].toString();
			String fcs = obj[1]!=null ? obj[1].toString() :"";	//���ݵ�ַ
			String mjs = obj[2]!=null ? obj[2].toString() :"";	//�������
			String lqs = obj[3]!=null ? obj[3].toString() :"";	//������Դȥ��
			
			String[] fcarr = fcs.split(",");
			String[] mjarr = mjs.split(",");
			String[] lqarr = lqs.split(",");
			String newfc = "";
			String newmj = "";
			int count = 0;
			List<String> zj = new ArrayList<String>();		//��Դ���� + 
			List<String> js = new ArrayList<String>();		//ȥ�򷿲�
			for (int j = 0; j < fcarr.length; j++) {
				String fc = fcarr[j];
				String lq = lqarr[j];
				if(lq.equals("����") || lq.equals("����ȥ��") || lq.equals("��������")) {
					js.add(fc);
				} else {
					zj.add(fc);
				}
			}
			//zj.removeAll(js);
			if(zj.size()>0) {
				for (int j = 0; j < fcarr.length; j++) {
					String fc = fcarr[j];
					if(!js.contains(fc) && !newfc.contains(fc)) {
						newfc= newfc + fc +"��";
						newmj= newmj + mjarr[j] +"ƽ�ס�";
						count ++;
					}
				}
				if(count==1) {
					map.put(id, count+"�׷��������Ϊ"+(newmj.length()>0?newmj.substring(0, newmj.length()-1):newmj));
				} else if(count>1) {
					map.put(id, count+"�׷���������ֱ�Ϊ"+(newmj.length()>0?newmj.substring(0, newmj.length()-1):newmj));
				}
			} else {
				map.put(id, count+"�׷���������ֱ�Ϊ"+(newmj.length()>0?newmj.substring(0, newmj.length()-1):newmj));
			}
		}
		return map;
	}
	private Map<String, String> getKCrjjlMap(String checkregid, HBSession sess) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			conn = sess.connection();
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			String sql = "select crp008, CRKYSCG09,CRKYSCG10,CRKYSCG11,CRKYSCG13 from checkregperson p, Crkyscg f " + 
					" where p.crp006=f.CRKYSCG02 and crp002='������' and p.checkregid='"+checkregid+"'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				String t1 = rs.getString(2);
				String t2 = rs.getString(3);
				String dd = rs.getString(4);
				String jg = rs.getString(5);
				String msg = ""+t1+"-"+t2+"����"+dd+"";
				msg = msg+ (jg!=null && !jg.equals("")?",��"+jg+"��׼��"+"\r\n" :"��"+"\r\n");
				String arr = null;
				if(map.containsKey(id)) {
					arr = map.get(id);
					arr = arr+ msg;
				} else {
					arr = msg;
				}
				map.put(id, arr);
			}
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	@SuppressWarnings("unchecked")
	private Map<String, String[]> getKCrjzjMap(String checkregid, HBSession sess) {
		//Checkreg cr = (Checkreg) sess.get(Checkreg.class, checkregid);
		//String rq = cr.getCheckdate();
		String rq = DateUtil.getcurdate();
		Date date = stringToDate_Size6_8(rq);
		String sql = "select crp008,crp002,CRKPTHZ12,CRKPTHZ09,CRKPTHZ11,CRKPTHZ13 from checkregperson p,crkpthz f " + 
				" where p.crp006=f.CRKPTHZ02 and crp002='������' and p.checkregid='"+checkregid+"' ";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		Map<String, String[]> map = new HashMap<String, String[]>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			String id = obj[0].toString();
			String bg1 = obj[2].toString();		//�Ƿ���֯������
			String zj = obj[3].toString();
			String yxq = obj[4].toString();
			String bg5 = obj[5].toString();		//���ܻ���
			
			if(Integer.parseInt(rq.substring(0, 4))-1<=Integer.parseInt(yxq.substring(0, 4))) {
				Date date2 = stringToDate_Size6_8(yxq);
				String str = "";
				
				if(date2.before(date)) {
					str += "��˽����"+zj.toUpperCase();
					str += bg5!=null && !bg5.equals("")? ",����"+bg5+"����" : "";
					str += "��"+yxq+"ʧЧ��";
				} else {
					str += "��˽����"+zj.toUpperCase();
					str += bg5!=null && !bg5.equals("")? ",����"+bg5+"����" : "";
				}
				str += "��"+"\r\n";
				String[] arr = null;
				if(map.containsKey(id)) {
					arr = map.get(id);
				} else {
					arr = new String[3];
				}
				String old = arr[2];
				arr[2] = (old!=null?old:"")+str;
				map.put(id, arr);
			}
		}
		
		String sql2 = "select crp008,crp002,CRKGWTTXZ09,CRKGWTTXZ10,CRKGWTTXZ12,CRKGWTTXZ13,CRKGWTTXZ14 from checkregperson p,crkgwttxz f " + 
				"where p.crp006=f.CRKGWTTXZ02 and crp002='������'  and p.checkregid='"+checkregid+"' ";
		List<Object[]> list2 = sess.createSQLQuery(sql2).list();
		for (int i = 0; i < list2.size(); i++) {
			Object[] obj = list2.get(i);
			String id = obj[0].toString();
			String lx = obj[2].toString();
			String zj = obj[3].toString();
			String yxq = obj[4].toString();
			String bg1 = obj[5].toString();		//�Ƿ���֯������
			String bg2 = obj[6].toString();		//�Ƿ���֯������
			if(Integer.parseInt(rq.substring(0, 4))-1<=Integer.parseInt(yxq.substring(0, 4))) {
				Date date2 = stringToDate_Size6_8(yxq);
				String str = "";
				if(lx.contains("̨��")) {
					str += "̨��ͨ��֤";
				} else if(lx.contains("��")) {
					str += "�۰�ͨ��֤";
				}
				str += zj.toUpperCase();
				str += bg2!=null && !bg2.equals("")? ",����"+bg2+"����" : "";
				if(date2.before(date)) {
					str += "��"+yxq+"ʧЧ��";
				}
				str += "��"+"\r\n";
				String[] arr = null;
				if(map.containsKey(id)) {
					arr = map.get(id);
				} else {
					arr = new String[3];
				}
				if(lx.contains("̨��")) {
					String old = arr[0];
					arr[0] = (old!=null?old:"")+str;
				} else if(lx.contains("��")) {
					String old = arr[1];
					arr[1] = (old!=null?old:"")+str;
				}
			}
		}
		return map;
	}
	
	private void downloadFile_chdbsj(HttpServletRequest request, HttpServletResponse response) {
		HBSession sess = HBUtil.getHBSession();
		String clientInfo = request.getHeader("User-agent");
		
		String rootpath = getRootPath();
		ServletOutputStream out = null;
		String checkregid = request.getParameter("checkregid");
		InputStream in = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		//HSSFWorkbook workbook_new = null;
		try {
			request.setCharacterEncoding("GBK");
			
			Map<String, String> fcMap = getFcMap(checkregid,sess);			//����
			Map<String, BigDecimal[]> gjbMap = getGjbMap(checkregid,sess); 	//��Ʊ������
			Map<String, String[]> gsMap = getGsMap(checkregid,sess); 		//����
			Map<String, String[]> crjzjMap = getCrjzjMap(checkregid,sess); 	//crj
			Map<String, String[]> crjjlMap = getCrjjlMap(checkregid,sess); 	//crj��¼
			
			//----------------- ��ȡ��ȿ����� ------------------------------
			Map<String, String[]> k_crjzjMap = getKCrjzjMap(checkregid,sess);
			Map<String, String> k_crjjlMap = getKCrjjlMap(checkregid,sess);
			Map<String, String[]> k_crjjlMap2 = getKCrjjlMap2(checkregid,sess);
			Map<String, String> k_FcMap = getKFcMap(checkregid,sess);
			Map<String, BigDecimal[]> k_gjbMap = getKGjbMap(checkregid,sess);
			Map<String, String[]> k_gsMap = getKGsMap(checkregid,sess);
			Map<String, String> k_yjgwMap = getKYjMap(checkregid,sess);
			Map<String, String> k_wyjgwMap = getKWyjMap(checkregid,sess);
			
			conn = sess.connection();
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			in = new FileInputStream(new File(rootpath+"/pages/xbrm/jcgl/chdbb_sjch.xls"));
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet sheet_old = workbook.getSheetAt(0);
			//workbook_new = new HSSFWorkbook();
			//List<CellRangeAddress> originMerged = sheet_old.getMergedRegions();
			
			String nd = DateUtil.dateToString(new Date(), "yyyy");
			int count = 1;
			
			String querysql = "select * from CHECKREGPERSON where checkregid='"+checkregid+"' and crp007='1' order by sortid1,crp008,sortid2";
			rs = stmt.executeQuery(querysql);
			while (rs.next()) {
				String crp001 = rs.getString("crp001");
				String crp004 = rs.getString("crp004");
				String crp008 = rs.getString("crp008");
				
				//HSSFCellStyle headstyle = workbook.createCellStyle();
				HSSFSheet new_sheet = workbook.createSheet(count +"��" + crp001);
				ExcelCopyOrMerged.copySheet(workbook, sheet_old, new_sheet, true);
				new_sheet.getRow(0).getCell(0).setCellValue(nd+"-" + (count++));
				new_sheet.getRow(3).getCell(1).setCellValue(crp001);
				new_sheet.getRow(3).getCell(3).setCellValue(crp004);
				String crp018 = rs.getString("crp018");
				new_sheet.getRow(5).getCell(1).setCellValue("�ʱ�䣺"+DateUtil.dateToString(stringToDate_Size6_8(crp018), "yyyy��MM��dd��"));
				new_sheet.getRow(2).getCell(0).setCellValue("��������--"+nd+"�꣩ ");
				new_sheet.getRow(12).getCell(3).setCellValue(fcMap.get(crp008)!=null ? fcMap.get(crp008)+"" :"�޴������");
				if(gjbMap.containsKey(crp008)) {
					BigDecimal[] zjs = gjbMap.get(crp008);
					BigDecimal z0 = zjs[0];
					BigDecimal z1 = zjs[1];
					BigDecimal z2 = zjs[2];
					BigDecimal z3 = zjs[3];
					BigDecimal z4 = zjs[4];
					BigDecimal z5 = zjs[5];
					BigDecimal z6 = zjs[6];
					BigDecimal z7 = zjs[7];
					BigDecimal z8 = zjs[8];
					BigDecimal z9 = zjs[9];
					BigDecimal z10 = zjs[10];
					BigDecimal z11 = zjs[11];
					StringBuffer gjbmsg = new StringBuffer();
					if(z0!=null || z1!=null  || z2!=null) {
						gjbmsg.append("����");
						if(z0!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(Math.round(z0.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z1!=null) {
							gjbmsg.append("��������ֵ").append(Math.round(z1.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z2!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(Math.round(z2.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(z3!=null || z4!=null  || z5!=null) {
						gjbmsg.append("��ż����");
						if(z3!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(Math.round(z3.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z4!=null) {
							gjbmsg.append("��������ֵ").append(Math.round(z4.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z5!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(Math.round(z5.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(z6!=null || z7!=null  || z8!=null) {
						gjbmsg.append("��Ů����");
						if(z6!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(Math.round(z6.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z7!=null) {
							gjbmsg.append("��������ֵ").append(Math.round(z7.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z8!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(Math.round(z8.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(z9!=null || z10!=null  || z11!=null) {
						gjbmsg.append("��Ů����ż����");
						if(z9!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(Math.round(z9.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z10!=null) {
							gjbmsg.append("��������ֵ").append(Math.round(z10.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						if(z11!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(Math.round(z11.doubleValue()/100d)/100d).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(gjbmsg.length()>4) {
						new_sheet.getRow(13).getCell(3).setCellValue(gjbmsg.substring(0,gjbmsg.length()-3) + "");
					} else {
						new_sheet.getRow(13).getCell(3).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(13).getCell(3).setCellValue("�޴������");
				}
				if(gsMap.containsKey(crp008)) {
					String[] arr = gsMap.get(crp008);
					String msg = (arr[0]!=null? arr[0]+"\r\n" :"") + (arr[1]!=null? arr[1]+"\r\n" :"") +
							(arr[2]!=null? arr[2]+"\r\n" :"") + (arr[6]!=null? arr[6]+"\r\n" :"") +
							(arr[3]!=null? arr[3]+"\r\n" :"") + (arr[4]!=null? arr[4]+"\r\n" :"") + 
							(arr[5]!=null? arr[5]+"\r\n" :"") + (arr[7]!=null? arr[7]+"\r\n" :"");
					if(msg.trim().length()>1) {
						new_sheet.getRow(14).getCell(3).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(14).getCell(3).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(14).getCell(3).setCellValue("�޴������");
				}
				if(crjzjMap.containsKey(crp008)) {
					String[] arr = crjzjMap.get(crp008);
					String msg = "����" + (arr[2]!=null? arr[2]+"" :"")  ;
					if(msg.trim().length()>4) {
						new_sheet.getRow(6).getCell(3).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(6).getCell(3).setCellValue("�޴������");
					}
					String msg2 = "����" + 
							(arr[0]!=null? arr[0]+"" :"") +(arr[1]!=null? arr[1]+"" :"")  ;
					if(msg2.trim().length()>4) {
						new_sheet.getRow(8).getCell(3).setCellValue(msg2.substring(0,msg2.length()-3) + "");
					} else {
						new_sheet.getRow(8).getCell(3).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(6).getCell(3).setCellValue("�޴������");
					new_sheet.getRow(8).getCell(3).setCellValue("�޴������");
				}
				if(crjjlMap.containsKey(crp008)) {
					String[] arr = crjjlMap.get(crp008);
					String msg = (arr[0]!=null? arr[0]+"" :"");
					if(msg.trim().length()>1) {
						new_sheet.getRow(7).getCell(3).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(7).getCell(3).setCellValue("�޴������");
					}
					String msg2 = (arr[1]!=null? arr[1]+"" :"")  ;
					if(msg2.trim().length()>1) {
						new_sheet.getRow(9).getCell(3).setCellValue(msg2.substring(0,msg2.length()-3) + "");
					} else {
						new_sheet.getRow(9).getCell(3).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(7).getCell(3).setCellValue("�޴������");
					new_sheet.getRow(9).getCell(3).setCellValue("�޴������");
				}
				
				//-----------  ��ȿ������� ----------------------------------------------------
				new_sheet.getRow(12).getCell(1).setCellValue(k_FcMap.get(crp008)!=null ? k_FcMap.get(crp008)+"" :"�޴������");
				if(k_gjbMap.containsKey(crp008)) {
					BigDecimal[] zjs = k_gjbMap.get(crp008);
					BigDecimal z0 = zjs[0];
					BigDecimal z1 = zjs[1];
					BigDecimal z2 = zjs[2];
					BigDecimal z3 = zjs[3];
					BigDecimal z4 = zjs[4];
					BigDecimal z5 = zjs[5];
					BigDecimal z6 = zjs[6];
					BigDecimal z7 = zjs[7];
					BigDecimal z8 = zjs[8];
					BigDecimal z9 = zjs[9];
					BigDecimal z10 = zjs[10];
					BigDecimal z11 = zjs[11];
					StringBuffer gjbmsg = new StringBuffer();
					if(z0!=null || z1!=null  || z2!=null) {
						gjbmsg.append("����");
						if(z0!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(z0.doubleValue()).append("��Ԫ��");
						}
						if(z1!=null) {
							gjbmsg.append("��������ֵ").append(z1.doubleValue()).append("��Ԫ��");
						}
						if(z2!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(z2.doubleValue()).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(z3!=null || z4!=null  || z5!=null) {
						gjbmsg.append("��ż����");
						if(z3!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(z3.doubleValue()).append("��Ԫ��");
						}
						if(z4!=null) {
							gjbmsg.append("��������ֵ").append(z4.doubleValue()).append("��Ԫ��");
						}
						if(z5!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(z5.doubleValue()).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(z6!=null || z7!=null  || z8!=null) {
						gjbmsg.append("��Ů����");
						if(z6!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(z6.doubleValue()).append("��Ԫ��");
						}
						if(z7!=null) {
							gjbmsg.append("��������ֵ").append(z7.doubleValue()).append("��Ԫ��");
						}
						if(z8!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(z8.doubleValue()).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(z9!=null || z10!=null  || z11!=null) {
						gjbmsg.append("��Ů����ż����");
						if(z9!=null) {
							gjbmsg.append("��Ʊ����ֵ").append(z9.doubleValue()).append("��Ԫ��");
						}
						if(z10!=null) {
							gjbmsg.append("��������ֵ").append(z10.doubleValue()).append("��Ԫ��");
						}
						if(z11!=null) {
							gjbmsg.append("Ͷ���ͱ���").append(z11.doubleValue()).append("��Ԫ��");
						}
						gjbmsg.deleteCharAt(gjbmsg.length()-1);
						gjbmsg.append("��\r\n");
					}
					if(gjbmsg.length()>4) {
						new_sheet.getRow(13).getCell(1).setCellValue(gjbmsg.substring(0,gjbmsg.length()-3) + "");
					} else {
						new_sheet.getRow(13).getCell(1).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(13).getCell(1).setCellValue("�޴������");
				}
				if(k_gsMap.containsKey(crp008)) {
					String[] arr = k_gsMap.get(crp008);
					String msg = (arr[0]!=null? arr[0]+"\r\n" :"") + (arr[1]!=null? arr[1]+"\r\n" :"")
							+(arr[2]!=null? arr[2]+"\r\n" :"") + (arr[3]!=null? arr[3]+"\r\n" :"") + 
							(arr[4]!=null? arr[4]+"\r\n" :"") + (arr[5]!=null? arr[5]+"\r\n" :"") +
							(arr[6]!=null? arr[6]+"\r\n" :"") + (arr[7]!=null? arr[7]+"\r\n" :"");
					if(msg.trim().length()>1) {
						new_sheet.getRow(14).getCell(1).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(14).getCell(1).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(14).getCell(1).setCellValue("�޴������");
				}
				if(k_crjzjMap.containsKey(crp008)) {
					String[] arr = k_crjzjMap.get(crp008);
					String msg = "����" + (arr[2]!=null? arr[2]+"" :"") ;
					if(msg.trim().length()>4) {
						new_sheet.getRow(6).getCell(1).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(6).getCell(1).setCellValue("�޴������");
					}
					String msg2 = "����" + 
							(arr[0]!=null? arr[0]+"" :"") +(arr[1]!=null? arr[1]+"" :"")  ;
					if(msg2.trim().length()>4) {
						new_sheet.getRow(8).getCell(1).setCellValue(msg2.substring(0,msg2.length()-3) + "");
					} else {
						new_sheet.getRow(8).getCell(1).setCellValue("�޴������");
						
					}
				} else {
					new_sheet.getRow(6).getCell(1).setCellValue("�޴������");
					new_sheet.getRow(8).getCell(1).setCellValue("�޴������");
				}
				if(k_crjjlMap.containsKey(crp008)) {
					String arr = k_crjjlMap.get(crp008);
					String msg = "" + (arr!=null && !arr.equals("")? arr+"" :"") ;
					if(msg.trim().length()>2) {
						new_sheet.getRow(7).getCell(1).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(7).getCell(1).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(7).getCell(1).setCellValue("�޴������");
				}
				if(k_crjjlMap2.containsKey(crp008)) {
					String arr[] = k_crjjlMap2.get(crp008);
					String msg = "" + (arr[0]!=null && !arr[0].equals("")? arr[0]+"" :"") +
							(arr[1]!=null && !arr[1].equals("")? arr[1]+"" :"");
					if(msg.trim().length()>2) {
						new_sheet.getRow(9).getCell(1).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(9).getCell(1).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(9).getCell(1).setCellValue("�޴������");
				}
				if(k_yjgwMap.containsKey(crp008)) {
					String arr = k_yjgwMap.get(crp008);
					String msg = "" + (arr!=null && !arr.equals("")? arr+"" :"");
					if(msg.trim().length()>2) {
						new_sheet.getRow(10).getCell(1).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(10).getCell(1).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(10).getCell(1).setCellValue("�޴������");
				}
				if(k_wyjgwMap.containsKey(crp008)) {
					String arr = k_wyjgwMap.get(crp008);
					String msg = "" + (arr!=null && !arr.equals("")? arr+"" :"");
					if(msg.trim().length()>2) {
						new_sheet.getRow(11).getCell(1).setCellValue(msg.substring(0,msg.length()-3) + "");
					} else {
						new_sheet.getRow(11).getCell(1).setCellValue("�޴������");
					}
				} else {
					new_sheet.getRow(11).getCell(1).setCellValue("�޴������");
				}
				
				
			}
			workbook.setActiveSheet(1);
			workbook.setSheetHidden(0, true);
			String filename = "��˱ȶԱ�--������.xls";
			filename = filename.replace(" ", "");
			// IE����URLEncoder��ʽ����
			if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {
			    if (clientInfo.indexOf("MSIE 6") > 0 || clientInfo.indexOf("MSIE 5") > 0) {// IE6����GBK���˴�ʵ���ɾ�����
					filename = new String(filename.getBytes("GBK"),  "ISO-8859-1");
				} else {// ie7+��URLEncoder��ʽ
			        filename = URLEncoder.encode(filename, "utf-8");
				}
		    } else {//���������
		       	filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
		    }
			
			out = response.getOutputStream();
			
			response.reset();
			
			
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			response.setContentType("application/octet-stream;charset=GBK");
			workbook.write(out);
		    out.close();
		    workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	@SuppressWarnings("unchecked")
	private Map<String, String[]> getCrjzjMap(String checkregid, HBSession sess) {
		//Checkreg cr = (Checkreg) sess.get(Checkreg.class, checkregid);
		//String rq = cr.getCheckdate();
		String rq = DateUtil.getcurdate();
		Date date = stringToDate_Size6_8(rq);
		String sql = "select crp008,crp002,CRCRJZJ006,CRCRJZJ007,CRCRJZJ009 from checkregperson p,checkregcrjzj f " + 
				"where p.crp006=f.CRCRJZJ002 and CRCRJZJ007 is not null and crp002='������' and p.checkregid=f.checkregid and p.checkregid='"+checkregid+"' ";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		Map<String, String[]> map = new HashMap<String, String[]>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			String id = obj[0].toString();
			String lx = obj[2].toString();
			String zj = obj[3].toString();
			String yxq = obj[4].toString();
			if(Integer.parseInt(rq.substring(0, 4))-1<=Integer.parseInt(yxq.substring(0, 4))) {
				Date date2 = stringToDate_Size6_8(yxq);
				String str = "";
				if(lx.contains("̨��")) {
					if(date2.before(date)) {
						str += "̨��ͨ��֤";
						str += zj.toUpperCase();
						str += "��"+yxq+"ʧЧ��";
					} else {
						str += "��Ч������˽����̨��ͨ��֤";
						str += zj.toUpperCase();
					}
				} else if(lx.contains("��")) {
					if(date2.before(date)) {
						str += "�۰�ͨ��֤";
						str += zj.toUpperCase();
						str += "��"+yxq+"ʧЧ��";
					} else {
						str += "��Ч������˽���ո۰�ͨ��֤";
						str += zj.toUpperCase();
					}
				} else {
					if(date2.before(date)) {
						str += "��˽����";
						str += zj.toUpperCase();
						str += "��"+yxq+"ʧЧ��";
					} else {
						str += "��Ч������˽����";
						str += zj.toUpperCase();
					}
				}
				
				str += "��"+"\r\n";
				String[] arr = null;
				if(map.containsKey(id)) {
					arr = map.get(id);
				} else {
					arr = new String[3];
				}
				if(lx.contains("̨��")) {
					String old = arr[0];
					arr[0] = (old!=null?old:"")+str;
				} else if(lx.contains("��")) {
					String old = arr[1];
					arr[1] = (old!=null?old:"")+str;
				} else {
					String old = arr[2];
					arr[2] = (old!=null?old:"")+str;
				}
				map.put(id, arr);
			}
		}
		return map;
	}
	
	private Map<String, String[]> getGsMap(String checkregid, HBSession sess) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, String[]> map = new HashMap<String, String[]>();
		try {
			conn = sess.connection();
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			/*String sql = "select crp008,crp002,CRGSCG005,CRGSCG006/10000,CRGSCG010,round(CRGSCG010*10000/"
					+ " CRGSCG006*100,2) || '%',CRGSCG013,CRGSCG015 from (select case when crp002='��Ů����ż' "
					+ "then '��Ů' else crp002 end crp002,crp006,crp008,checkregid from checkregperson) p,checkreggscg f where p.crp006"
					+ " =f.CRGSCG003 and p.checkregid=f.checkregid and CRGSCG013<>'ע��' and p.checkregid='"+checkregid+"'";*/
			String sql = "select crp008,crp002,CRGSCG005,CRGSCG006/10000,CRGSCG010,round(CRGSCG010*10000/"
					+ " CRGSCG006*100,2) || '%',CRGSCG013,CRGSCG015 from (select crp002,crp006,"
					+ "crp008,checkregid from checkregperson) p,checkreggscg f where p.crp006"
					+ " =f.CRGSCG003 and p.checkregid=f.checkregid and CRGSCG013<>'ע��' and p.checkregid='"+checkregid+"'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				String dx = rs.getString(2).replaceAll(" ", "");
				String dw = rs.getString(3);
				Double zb = rs.getDouble(4);
				String cz = rs.getString(5);
				String zhb = rs.getString(6);
				String zt = rs.getString(7);
				String sj = rs.getString(8);
				String arr[] = null;
				if(map.containsKey(id)) {
					arr = map.get(id);
				} else {
					arr = new String[8];
				}
				String msg = "����"+dw+cz+"��Ԫ����ҵע���ʱ�"+zb+"��Ԫ�����˳��ʱ���"+zhb+(zt.equals("����")?("(����"+sj+")"):"")+"��";
				if(dx.equals("������")) {
					String old = arr[0];
					arr[0] = (old!=null?old:"") +"����"+ msg;
				} else if(dx.equals("��ż")) {
					String old = arr[1];
					arr[1] = (old!=null?old:"") +"��ż"+ msg;
				} else if(dx.equals("��Ů")) {
					String old = arr[2];
					arr[2] = (old!=null?old:"") +"��Ů"+ msg;
				} else if(dx.equals("��Ů����ż")) {
					String old = arr[6];
					arr[6] = (old!=null?old:"") +"��Ů����ż"+ msg;
				} 
				map.put(id, arr);
			}
			rs.close();
			
			/*String sql2 = "select crp008,crp002,CRGSZW005,CRGSZW006/10000,CRGSZW013,CRGSZW015"
					+ " from (select case when crp002='��Ů����ż' then '��Ů' else crp002 end crp002,"
					+ "crp006,crp008,checkregid from checkregperson) p,checkreggszw f  where p.crp006=f.CRGSZW003"
					+ " and p.checkregid=f.checkregid and CRGSZW013<>'ע��'and p.checkregid='"+checkregid+"'";*/
			String sql2 = "select crp008,crp002,CRGSZW005,CRGSZW006/10000,CRGSZW013,CRGSZW015"
					+ " from (select  crp002,"
					+ "crp006,crp008,checkregid from checkregperson) p,checkreggszw f  where p.crp006=f.CRGSZW003"
					+ " and p.checkregid=f.checkregid and CRGSZW013<>'ע��'and p.checkregid='"+checkregid+"'";
			rs = stmt.executeQuery(sql2);
			while (rs.next()) {
				String id = rs.getString(1);
				String dx = rs.getString(2).replaceAll(" ", "");;
				String dw = rs.getString(3);
				Double zb = rs.getDouble(4);
				String zt = rs.getString(5);
				String sj = rs.getString(6);
				String arr[] = null;
				if(map.containsKey(id)) {
					arr = map.get(id);
				} else {
					arr = new String[8];
				}
				String msg = "����"+dw+"�ķ��ˣ������˻�Ӫ�ߣ�����ҵע���ʱ�"+zb+"��Ԫ"+(zt.equals("����")?("(����"+sj+")"):"")+"��";
				if(dx.equals("������")) {
					String old = arr[3];
					arr[3] = (old!=null?old:"") +"����"+ msg;
				} else if(dx.equals("��ż")) {
					String old = arr[4];
					arr[4] = (old!=null?old:"") +"��ż"+ msg;
				} else if(dx.equals("��Ů")) {
					String old = arr[5];
					arr[5] = (old!=null?old:"") +"��Ů"+ msg;
				} else if(dx.equals("��Ů����ż")) {
					String old = arr[7];
					arr[7] = (old!=null?old:"") +"��Ů����ż"+ msg;
				} 
				map.put(id, arr);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return map;
	}
	private Map<String, BigDecimal[]> getGjbMap(String checkregid, HBSession sess) {
		/*String sql = "select crp008,crp002,CRZQ005,sum(CRZQ010) from (select case when crp002='��Ů����ż' then '��Ů' "
				+ "else crp002 end crp002,crp006,crp008,checkregid from checkregperson) p,checkregzq f"
				+ "  where p.crp006=f.CRZQ002 and p.checkregid=f.checkregid and p.checkregid='"+checkregid+"' group by crp008,crp002,CRZQ005";*/
		String sql = "select crp008,crp002,CRZQ005,sum(CRZQ010) from (select crp002,crp006,crp008,checkregid from checkregperson) p,checkregzq f"
				+ "  where p.crp006=f.CRZQ002 and p.checkregid=f.checkregid and p.checkregid='"+checkregid+"' group by crp008,crp002,CRZQ005";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		Map<String, BigDecimal[]> map = new HashMap<String, BigDecimal[]>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			String id = obj[0].toString();
			String dx = obj[1].toString().replaceAll(" ", "");
			String lx = obj[2].toString().replaceAll(" ", "");
			/*if("f20c3270-df88-498a-bdb1-7b1d393dc2cf".equals(id)) {
				System.out.println(dx+"---------"+lx);
			}*/
			BigDecimal zj = (BigDecimal) obj[3];
			if(map.containsKey(id)) {
				BigDecimal[] arr = map.get(id);
				if(dx.equals("������")) {
					if(lx.equals("��Ʊ")) {
						arr[0] = zj;
					} else {
						arr[1] = zj;
					}
				} else if(dx.equals("��ż")) {
					if(lx.equals("��Ʊ")) {
						arr[3] = zj;
					} else {
						arr[4] = zj;
					}
				} else if(dx.equals("��Ů")) {
					if(lx.equals("��Ʊ")) {
						arr[6] = zj;
					} else {
						arr[7] = zj;
					}
				} else if(dx.equals("��Ů����ż")) {
					if(lx.equals("��Ʊ")) {
						arr[9] = zj;
					} else {
						arr[10] = zj;
					}
				}
			} else {
				BigDecimal[] arr = new BigDecimal[12];
				if(dx.equals("������")) {
					if(lx.equals("��Ʊ")) {
						arr[0] = zj;
					} else {
						arr[1] = zj;
					}
				} else if(dx.equals("��ż")) {
					if(lx.equals("��Ʊ")) {
						arr[3] = zj;
					} else {
						arr[4] = zj;
					}
				} else if(dx.equals("��Ů")) {
					if(lx.equals("��Ʊ")) {
						arr[6] = zj;
					} else {
						arr[7] = zj;
					}
				} else if(dx.equals("��Ů����ż")) {
					if(lx.equals("��Ʊ")) {
						arr[9] = zj;
					} else {
						arr[10] = zj;
					}
				}
				map.put(id, arr);
			}
		}
		//System.out.println(map.get("f20c3270-df88-498a-bdb1-7b1d393dc2cf"));
		/*String sql2 = "select crp008,crp002,sum(CRBX009) from (select case when crp002='��Ů����ż' then '��Ů' else"
				+ " crp002 end crp002,crp006,crp008,checkregid from checkregperson) p,checkregbx f where "
				+ " p.crp006=f.CRBX004 and p.checkregid=f.checkregid and p.checkregid='"+checkregid+"' group by crp008,crp002";*/
		String sql2 = "select crp008,crp002,sum(CRBX009) from (select crp002,crp006,crp008,checkregid from checkregperson) p,checkregbx f where "
				+ " p.crp006=f.CRBX004 and p.checkregid=f.checkregid and p.checkregid='"+checkregid+"' group by crp008,crp002";
		List<Object[]> list2 = sess.createSQLQuery(sql2).list();
		for (int i = 0; i < list2.size(); i++) {
			Object[] obj = list2.get(i);
			String id = obj[0].toString();
			String dx = obj[1].toString().replaceAll(" ", "");
			BigDecimal zj = (BigDecimal) obj[2];
			if(map.containsKey(id)) {
				BigDecimal[] arr = map.get(id);
				if(dx.equals("������")) {
					arr[2] = zj;
				} else if(dx.equals("��ż")) {
					arr[5] = zj;
				} else if(dx.equals("��Ů")) {
					arr[8] = zj;
				} else if(dx.equals("��Ů����ż")) {
					arr[11] = zj;
				}
			} else {
				BigDecimal[] arr = new BigDecimal[12];
				if(dx.equals("������")) {
					arr[2] = zj;
				} else if(dx.equals("��ż")) {
					arr[5] = zj;
				} else if(dx.equals("��Ů")) {
					arr[8] = zj;
				} else if(dx.equals("��Ů����ż")) {
					arr[11] = zj;
				}
				map.put(id, arr);
			}
		}
		return map;
	}
	private Map<String, String> getFcMap(String checkregid, HBSession sess) {
		String sql = "select crp008,to_char(wm_concat(CRFC010)),to_char(wm_concat(CRFC011)) from checkregperson p,"
				+ "checkregfc f where p.crp006=f.CRFC004 and p.checkregid=f.checkregid and p.checkregid='"+checkregid+
				"' and crfc006 in ('�ѵǼ�','�ѱ���δ�Ǽ�') group by crp008";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			String id = obj[0].toString();
			String fcs = obj[1]!=null ? obj[1].toString() :"";
			String mjs = obj[2]!=null ? obj[2].toString() :"";
			String[] fcarr = fcs.split(",");
			String[] mjarr = mjs.split(",");
			String newfc = "";
			String newmj = "";
			int count = 0;
			for (int j = 0; j < fcarr.length; j++) {
				String fc = fcarr[j];
				if(!newfc.contains(fc+"��")) {
					newfc= newfc + fc +"��";
					newmj= newmj + mjarr[j] +"ƽ�ס�";
					count ++;
				}
			}
			if(count==1) {
				map.put(id, count+"�׷��������Ϊ"+(newmj.length()>0?newmj.substring(0, newmj.length()-1):newmj));
			} else if(count > 1) {
				map.put(id, count+"�׷���������ֱ�Ϊ"+(newmj.length()>0?newmj.substring(0, newmj.length()-1):newmj));
			}
		}
		return map;
	}
	private void downloadPFile_bs(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		OutputStream out = null;
	 	HBSession session  = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		HSSFWorkbook wb = null;
		FileInputStream in = null;
		try {
			String clientInfo = request.getHeader("User-agent");
			request.setCharacterEncoding("GBK");
			String rootpath = getRootPath();
			String checkregids = request.getParameter("checkregid");
			

			String path1 = this.disk +"temp/checkreg/"+UUID.randomUUID().toString();
			String path2 = path1+"/file/";
			File file = new File(path2);
			if(!file.exists()) {
				file.mkdirs();
			}
			
			String ids[] = checkregids.substring(0,checkregids.length()-1).split(",");
			for (int i = 0; i < ids.length; i++) {
				String checkregid = ids[i];
				Checkreg reg = (Checkreg) session.get(Checkreg.class, checkregid);
				//String bm = reg.getGroupname();	
				String bm = "������ί��֯��";			//����
				String sj = reg.getCheckdate();			//ʱ��
				String nm = reg.getRegname();			//ʱ��
				
				conn = session.connection();
				stmt = conn.createStatement();
				in = new FileInputStream(new File(rootpath+"/pages/xbrm/jcgl/sxchjbxxb_bs.xls"));
				wb = new HSSFWorkbook(in);
				Sheet sheet = wb.getSheetAt(0);
				sheet.getRow(1).getCell(0).setCellValue("���λ��"+bm);
				Date d = stringToDate_Size6_8(sj);
				sheet.getRow(1).getCell(6).setCellValue("����ڣ�" +DateUtil.dateToString(d, "yyyy��MM��dd��"));
				
				String querysql = "select * from CHECKREGPERSON where checkregid='"+checkregid+"' order by sortid1,crp008,sortid2";
				rs = stmt.executeQuery(querysql);
				int index = 3;								//��ʼ��������
				int number = 0;								//��Ա���
				String id = "";
				short rowheihgt = 240;					
				CellStyle[] cellstyles = new CellStyle[7];
				while (rs.next()) {
					if(index==3) {
						Row row_old = sheet.getRow(index);
						rowheihgt = row_old.getHeight();
						cellstyles[0] = row_old.getCell(0).getCellStyle();
						cellstyles[1] = row_old.getCell(1).getCellStyle();
						cellstyles[2] = row_old.getCell(2).getCellStyle();
						cellstyles[3] = row_old.getCell(3).getCellStyle();
						cellstyles[4] = row_old.getCell(4).getCellStyle();
						cellstyles[5] = row_old.getCell(5).getCellStyle();
						cellstyles[6] = row_old.getCell(6).getCellStyle();
					}
					
					Row row = sheet.createRow(index);
					String crp001 = rs.getString("crp001");		//����
					String crp002 = rs.getString("crp002");		//��ν
					String crp009 = rs.getString("crp009");		//�Ա�
					String crp006 = rs.getString("crp006");		//���֤
					String crp004 = rs.getString("crp004");		//������λְ��
					String crp008 = rs.getString("crp008");		//���id
					String crp018 = rs.getString("crp018");		//�˲�����
					
					if(!id.equals(crp008)) {
						number ++;
						id = crp008;
					}
					
					Cell cell0 = row.createCell(0);
					cell0.setCellStyle(cellstyles[0]);
					String no = "000"+(index-2);
					cell0.setCellValue("0510"+no.substring(no.length()-3));
					
					Cell cell1 = row.createCell(1);
					cell1.setCellStyle(cellstyles[1]);
					cell1.setCellValue(crp002);
					
					Cell cell2 = row.createCell(2);
					cell2.setCellStyle(cellstyles[2]);
					cell2.setCellValue(crp001);
					
					Cell cell3 = row.createCell(3);
					cell3.setCellStyle(cellstyles[3]);
					cell3.setCellValue(crp009);
					
					Cell cell4 = row.createCell(4);
					cell4.setCellStyle(cellstyles[4]);
					cell4.setCellValue(crp004);
					
					Cell cell5 = row.createCell(5);
					cell5.setCellStyle(cellstyles[5]);
					cell5.setCellValue(crp006);
					
					Cell cell6 = row.createCell(6);
					cell6.setCellStyle(cellstyles[6]);
					cell6.setCellValue(crp018);
					row.setHeight(rowheihgt);
					//row.setRowStyle(rowstyle);
					index++;
				}
				
				in.close();
				rs.close();
				stmt.close();
				conn.close();
				
				String filename = (i+1)+"��ѯ���������Ϣ��"+number+"��-����ѯ��λ��("+nm+").xls";
				FileOutputStream fout = new FileOutputStream(new File(path2+filename));
				wb.write(fout);
				fout.close();
			    wb.close();
				
			}
			
			String zip =  path1+"/z.zip";
			SevenZipUtil.zip7z(path2, zip, null);
			
			String filename = "��ѯ���������Ϣ��.zip";
			filename = filename.replace(" ", "");
			// IE����URLEncoder��ʽ����
			if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {
			    if (clientInfo.indexOf("MSIE 6") > 0 || clientInfo.indexOf("MSIE 5") > 0) {// IE6����GBK���˴�ʵ���ɾ�����
					filename = new String(filename.getBytes("GBK"),  "ISO-8859-1");
				} else {// ie7+��URLEncoder��ʽ
			        filename = URLEncoder.encode(filename, "utf-8");
				}
		    } else {//���������
		       	filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
		    }
			response.reset();
			
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			response.setContentType("application/octet-stream;charset=GBK");
			
			File dfile = new File(zip);
            response.setContentLength((int)file.length());
            /*����ļ����ȴ���0*/
            if (dfile.isFile()) {
            	// ��������ʽ�����ļ���
	            InputStream fis = new BufferedInputStream(new FileInputStream(zip));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
                /*���������*/
	            out = new BufferedOutputStream(response.getOutputStream());
	            response.addHeader("Content-Length", "" + dfile.length());
		        response.setContentType("application/octet-stream");
		        out.write(buffer);
		        out.flush();
		        out.close();
            }
		} catch (Exception e) {
			e.printStackTrace();
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private void downloadPFile(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		OutputStream out = null;
	 	HBSession session  = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		HSSFWorkbook wb = null;
		FileInputStream in = null;
		try {
			String clientInfo = request.getHeader("User-agent");
			request.setCharacterEncoding("GBK");
			String rootpath = getRootPath();
			String checkregids = request.getParameter("checkregid");
			
			String path1 = this.disk +"temp/checkreg/"+UUID.randomUUID().toString();
			String path2 = path1+"/file/";
			File file = new File(path2);
			if(!file.exists()) {
				file.mkdirs();
			}
			
			String ids[] = checkregids.contains(",") ? checkregids.substring(0,checkregids.length()-1).split(","):checkregids.split(",");
			for (int i = 0; i < ids.length; i++) {
				String checkregid = ids[i];
				Checkreg reg = (Checkreg) session.get(Checkreg.class, checkregid);
				//String bm = reg.getGroupname();	 
				String bm = "ʡί��֯��";			//����
				String sj = reg.getCheckdate();			//ʱ��
				String nm = reg.getRegname();			//����
				
				conn = session.connection();
				stmt = conn.createStatement();
				in = new FileInputStream(new File(rootpath+"/pages/xbrm/jcgl/sxchjbxxb_1.xls"));
				wb = new HSSFWorkbook(in);
				Sheet sheet = wb.getSheetAt(0);
				sheet.getRow(1).getCell(0).setCellValue("���λ��"+bm);
				Date d = stringToDate_Size6_8(sj);
				sheet.getRow(1).getCell(5).setCellValue("����ڣ�" +DateUtil.dateToString(d, "yyyy��MM��dd��"));
				
				String querysql = "select * from CHECKREGPERSON where checkregid='"+checkregid+"' order by sortid1,crp008,sortid2";
				rs = stmt.executeQuery(querysql);
				int index = 3;								//��ʼ��������
				int number = 0;								//��Ա���
				String id = "";								//��id�仯����Ա��� +1
				int prerow = 3;								//�ϲ���ʼ�к�
				short rowheihgt = 240;					
				CellStyle[] cellstyles = new CellStyle[6];
				while (rs.next()) {
					if(index==3) {
						Row row_old = sheet.getRow(index);
						rowheihgt = row_old.getHeight();
						cellstyles[0] = row_old.getCell(0).getCellStyle();
						cellstyles[1] = row_old.getCell(1).getCellStyle();
						cellstyles[2] = row_old.getCell(2).getCellStyle();
						cellstyles[3] = row_old.getCell(3).getCellStyle();
						cellstyles[4] = row_old.getCell(4).getCellStyle();
						cellstyles[5] = row_old.getCell(5).getCellStyle();
					}
					
					Row row = sheet.createRow(index);
					//row.setRowStyle(rowstyle);
					String crp001 = rs.getString("crp001");		//����
					String crp002 = rs.getString("crp002");		//��ν
					String crp009 = rs.getString("crp009");		//�Ա�
					String crp006 = rs.getString("crp006");		//���֤
					String crp008 = rs.getString("crp008");		//���id
					String crp018 = rs.getString("crp018");		//�˲�����
					
					if(!id.equals(crp008)) {
						if(prerow != index && prerow != (index-1)) {
							//System.out.println(prerow+","+(index-1));
							sheet.addMergedRegion(new CellRangeAddress(prerow, index-1, 0, 0));
						}
						prerow = index;
						number ++;
						id = crp008;
					}
					
					Cell cell0 = row.createCell(0);
					cell0.setCellStyle(cellstyles[0]);
					cell0.setCellValue(number);
					
					Cell cell1 = row.createCell(1);
					cell1.setCellStyle(cellstyles[1]);
					cell1.setCellValue(crp002);
					
					Cell cell2 = row.createCell(2);
					cell2.setCellStyle(cellstyles[2]);
					cell2.setCellValue(crp001);
					
					Cell cell3 = row.createCell(3);
					cell3.setCellStyle(cellstyles[3]);
					cell3.setCellValue(crp009);
					
					Cell cell4 = row.createCell(4);
					cell4.setCellStyle(cellstyles[4]);
					cell4.setCellValue(crp006);
					
					Cell cell5 = row.createCell(5);
					cell5.setCellStyle(cellstyles[5]);
					cell5.setCellValue(crp018);
					row.setHeight(rowheihgt);
					//row.setRowStyle(rowstyle);
					index++;
				}
				if(prerow != index && prerow != (index-1)) {
					//System.out.println(prerow+","+(index-1));
					sheet.addMergedRegion(new CellRangeAddress(prerow, index-1, 0, 0));
				}
				in.close();
				rs.close();
				stmt.close();
				conn.close();
				
				String filename = (i+1)+"��ѯ���������Ϣ��"+number+"��-����ѯ��λ��("+nm+").xls";
				
				FileOutputStream fout = new FileOutputStream(new File(path2+filename));
				wb.write(fout);
				fout.close();
			    wb.close();
			}
			
			String zip =  path1+"/z.zip";
			SevenZipUtil.zip7z(path2, zip, null);
			
			String filename = "��ѯ���������Ϣ��.zip";
			filename = filename.replace(" ", "");
			// IE����URLEncoder��ʽ����
			if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {
			    if (clientInfo.indexOf("MSIE 6") > 0 || clientInfo.indexOf("MSIE 5") > 0) {// IE6����GBK���˴�ʵ���ɾ�����
					filename = new String(filename.getBytes("GBK"),  "ISO-8859-1");
				} else {// ie7+��URLEncoder��ʽ
			        filename = URLEncoder.encode(filename, "utf-8");
				}
		    } else {//���������
		       	filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
		    }
			response.reset();
			
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			response.setContentType("application/octet-stream;charset=GBK");
			
			File dfile = new File(zip);
            response.setContentLength((int)file.length());
            /*����ļ����ȴ���0*/
            if (dfile.isFile()) {
            	// ��������ʽ�����ļ���
	            InputStream fis = new BufferedInputStream(new FileInputStream(zip));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
                /*���������*/
	            out = new BufferedOutputStream(response.getOutputStream());
	            response.addHeader("Content-Length", "" + dfile.length());
		        response.setContentType("application/octet-stream");
		        out.write(buffer);
		        out.flush();
		        out.close();
            }
		} catch (Exception e) {
			e.printStackTrace();
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public static Date stringToDate_Size6_8(String strDate) {
		if (strDate == null|| strDate.equals("")) {
			return null;
		}
		if(strDate.length()==6){
			strDate = strDate +"01";
		} 
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date myDate = new Date();
		try {
			myDate = df.parse(strDate);
			return myDate;
		} catch (Exception e) {
			return null;
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
	public boolean deleteFile(String filename) throws AppException {
		java.io.File dir = new java.io.File(filename);
		boolean b = true;
		if (dir.exists()) {
			b = dir.delete();
		}
		return b;
	}

	// ɾ���ļ���
	// param folderPath �ļ�����������·��
	public void delFolder(String folderPath) {
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
	public boolean delAllFile(String path) {
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
