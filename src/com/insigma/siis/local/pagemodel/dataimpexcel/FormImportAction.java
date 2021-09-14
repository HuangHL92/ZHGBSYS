package com.insigma.siis.local.pagemodel.dataimpexcel;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.insigma.odin.framework.ActionSupport;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A36New;
import com.insigma.siis.local.business.entity.A36Z1New;
import com.insigma.siis.local.business.entity.A36_FILE;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.utils.ZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.util.StringUtil;

/**
 * 数据导入工具action
 *
 *
 *
 */
public class FormImportAction extends ActionSupport {
	private static final String separator = System.getProperty("file.separator");
	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";
	private static final String ZIP = "zip";
	private static POIFSFileSystem fs;// poi文件流
	private static Workbook wb;// 获得execl
	private static Sheet sheet;// 获得工作簿

	/**
	 * 点击导入
	 *
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward startImp(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> data = new HashMap<String, String>();// 返回前台的MSG
		Map<String, String> tempmap = new HashMap<String, String>();
		String filePath = "";
		String str = "";

		// 创建接收文件对象
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		// PrintWriter out = response.getWriter();
		String uuid = UUID.randomUUID().toString();
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;

		String uploadFileName = "";

		try {
			fileItems = uploader.parseRequest(request); // 文件本身
		} catch (FileUploadException e) {
			str = "导入文件不能为空，请重新导入！";
			data.put("error", str);
			e.printStackTrace();
			this.doSuccess(request, data);
			return this.ajaxResponse(request, response);
		}

		if (!fileItems.isEmpty()) {
			iter = fileItems.iterator();
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/tempFile/";// 上传路径
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
				file.mkdirs();
			}
			FileItem fi = null;
			while (iter.hasNext()) {

				fi = iter.next();

				if (fi == null || fi.getSize() == 0) {
					str = "导入文件不能为空，请重新导入！";
					data.put("error", str);
					this.doSuccess(request, data);
					return this.ajaxResponse(request, response);
				}
				if (!(fi.getName().endsWith(EXCEL_XLS) || (fi.getName().endsWith(EXCEL_XLSX))
						|| fi.getName().endsWith(ZIP))) {
					data.put("error", "文件不是Excel格式或zip,请检查！");
					this.doSuccess(request, data);
					return this.ajaxResponse(request, response);
				}
				String filerealname = "";
				String filename = "";
				if (fi.getName().endsWith(EXCEL_XLS)) {
					filePath = upload_file + uuid + ".xls";
					filename = fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
					filerealname = filename.substring(0, filename.lastIndexOf("."));
					tempmap.put("FILE_TYPE", EXCEL_XLS);
				}
				if (fi.getName().endsWith(EXCEL_XLSX)) {
					filePath = upload_file + uuid + ".xlsx";
					filename = fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
					filerealname = filename.substring(0, filename.lastIndexOf("."));

					tempmap.put("FILE_TYPE", EXCEL_XLSX);
				}
				if (fi.getName().endsWith(ZIP)) {
					filePath = upload_file + uuid + ".zip";
					filename = fi.getName().substring(fi.getName().lastIndexOf(separator) + 1);
					filerealname = filename.substring(0, filename.lastIndexOf("."));
					tempmap.put("FILE_TYPE", ZIP);
				}
				tempmap.put("FILE_NAME", filerealname);
				try {
					fi.write(new File(filePath));
				} catch (Exception e) {
					e.printStackTrace();
				}
				uploadFileName = filerealname;

			}
			fi.getOutputStream().close();
		}

		// 读取服务器中的文件，并进行处理
		File f = new File(filePath);
		if (f.getName().endsWith(EXCEL_XLS) || f.getName().endsWith(EXCEL_XLSX)) {
			data = Imp(uploadFileName, uuid, filePath);
			data.put("file", filePath);
			this.doSuccess(request, data);
			f.delete();
			return this.ajaxResponse(request, response);
		} else if (f.getName().endsWith(ZIP)) {
			List<Map<String, String>> errordata = ZipImp(uploadFileName, uuid, filePath, tempmap);
			int count = 0;
			for (int i = 0; i < errordata.size(); i++) {
				Map<String, String> map = errordata.get(i);
				String a = map.get("key");
				if (a != null && a.equals("error")) {
					count++;
				}
			}
			if (count > 0) {
				String expFile = ExpRar.expFile();
				errorExcel(errordata, expFile);
				data.put("error", "导入失败！");
				data.put("key", "error");
				data.put("type", "zip");
				data.put("file", expFile);
				this.doSuccess(request, data);
				return this.ajaxResponse(request, response);
			} else {
				str = "导入成功！";
				data.put("success", str);
				data.put("key", "success");
				this.doSuccess(request, data);
				return this.ajaxResponse(request, response);
			}

		} else {
			str = "文件格式不是导出的模板格式,请重新导入！";
			data.put("error", str);
			this.doSuccess(request, data);
			return this.ajaxResponse(request, response);
		}

	}

	/**
	 * 功能描述: 发送响应流方法
	 */
	public void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	/**
	 * 正式导入
	 *
	 * @param unid
	 * @param path
	 * @return
	 */
	private Map<String, String> Imp(String fileName, String uuid, String path) {
		HBSession session = HBUtil.getHBSession();
		Map<String, String> data = new HashMap<String, String>();// 返回前台的MSG

		FileInputStream is = null;
		path = path.replaceAll("\\\\", "\\/");

		try {
			// 先获取一张sheet页的所有信息
			// 支持Excel 2003 2007
			File excelFile = new File(path); // 创建文件对象
			// System.out.println("我是path+++++++++++++++++++++++++"+path);
			is = new FileInputStream(excelFile);
			Workbook workbook = getWorkbok(is, excelFile);
			// 继续初始化数据 Map<String, String> mapA0000
			Sheet sheet = workbook.getSheetAt(0);
			long start = System.currentTimeMillis();
			System.out.println("--------------------------------------------进入Sheet表1");

			data = dispose(path, sheet, uuid, session);
			System.out.println(data.get("errorData"));
			long end = System.currentTimeMillis();
			System.out.println(
					"--------------------------------------------Sheet表1处理完成,耗时：" + ((end - start) / 1000) + "秒");
			data.put("success", "Excel导入成功！");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				this.deleteFile(path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return data;
	}

	/***
	 * Zip 批量导入
	 *
	 * @throws IOException
	 */
	private List<Map<String, String>> ZipImp(String fileName, String uuid, String path, Map<String, String> tempmap)
			throws IOException {
		// long start = System.currentTimeMillis();
		HBSession session = HBUtil.getHBSession();
		Map<String, String> data = new HashMap<String, String>();// 返回前台的MSG
		List<Map<String, String>> errorExcel = new java.util.ArrayList<Map<String, String>>();
		FileInputStream is = null;
		path = path.replaceAll("\\\\", "\\/");
		String upload_file = AppConfig.HZB_PATH + "/temp/upload/tempFile/" + uuid + "/";
		upload_file = upload_file.replace("\\\\", "\\/");
		try {
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
				file.mkdirs();
			}
			ZipUtil.decompress(path, upload_file, null);
			File[] files = file.listFiles();
			Map<String, String> datalength = new HashMap<String, String>();
			datalength.put("length", String.valueOf(files.length));
			errorExcel.add(datalength);
			for (File p_file : files) {
				// String name=p_file.getName();
				is = new FileInputStream(p_file);
				Workbook workbook = getWorkbok(is, p_file);
				// 继续初始化数据 Map<String, String> mapA0000
				Sheet sheet = workbook.getSheetAt(0);
				try {
					data = dispose(p_file.toString(), sheet, uuid, session);
					if (!StringUtil.isEmpty(data.get("errorData"))) {
						errorExcel.add(data);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			is.close();
			this.deleteDirectory(upload_file);

		}

		return errorExcel;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 *
	 * @param sPath 被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除单个文件
	 *
	 * @param sPath 被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File f = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (f.isFile() && f.exists()) {
			f.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 具体表格导入读取方法
	 *
	 * @param sheet
	 * @param imprecordid
	 * @param session
	 * @return
	 * @throws Exception
	 *
	 *                   导入从12380网站过来的excel
	 */
	private Map<String, String> dispose(String path, Sheet sheet, String imprecordid, HBSession session)
			throws Exception {
		Map<String, String> data = new HashMap<String, String>();// 返回前台的MSG
		Row row = null;
		Cell c = null;
		row = sheet.getRow(0);
		c = row.getCell(1);
		int num = path.lastIndexOf("\\");
		String filename = "";
		if (num > 0) {
			filename = path.substring(path.lastIndexOf("\\"), path.length());
		}
		if(c==null) {
			data.put("errorData", filename + "@" + "表格位置错误，请参照模板调整表格！");
			data.put("key", "error");
			return data;
		}
		String a0184 = "";
		if(c.getCellType()==0){
			data.put("errorData", filename + "@" + "等级后的第二格中的身份证信息错误！");
			data.put("key", "error");
			return data;
		}else{
			a0184 = c.getStringCellValue();
		}
		A01 a01 = null;
		@SuppressWarnings("unchecked")
		List<A01> list2 = session.createSQLQuery("select * from a01 where a0184='" + a0184 + "'").addEntity(A01.class)
				.list();
		if (list2.size() < 1 || a0184 == null && "".equals(a0184)) {
			data.put("errorData", filename + "@" + "身份证错误或数据不存在！");
			data.put("key", "error");
			return data;
		}
		a01 = list2.get(0);
		List<A36> lista36 = session.createSQLQuery("select * from a36 where a0000='" + a01.getA0000() + "' and A3604A in('父亲','母亲')").addEntity(A36.class)
				.list();
		boolean parentCheck = true;
		String parentNames="";
		for(A36 a36:lista36) {
			parentNames+=a36.getA3601().replace(" ","").replace("　","")+",";
			parentCheck = false;
		}

		if (list2.size() < 1 || a0184 == null && "".equals(a0184)) {
			data.put("errorData", filename + "@" + "身份证错误或数据不存在！");
			data.put("key", "error");
			return data;
		} else {
			// insert data from excel
			try {
				List<Map<String,String>> datas = getList(path);
				if (datas.size()==0) {
					data.put("errorData", filename + "@" + "未找到有效的家庭成员数据！");
					data.put("key", "error");
					return data;
				}
				for(Map<String,String> d:datas) {
					if(parentNames!=null&&"父亲母亲".contains(d.get("A3604A"))&&parentNames.contains(d.get("A3601").replace(" ","").replace("　",""))) {
						parentCheck=true;
					}
				}
				if(!parentCheck) {
					data.put("errorData", filename + "@" + "父母信息未能有一位匹配，请检查干部家庭成员信息是否正确！");
					data.put("key", "error");
					return data;
				}
				String map = insertDate(datas , a01.getA0000());
				if (map != null) {
					data.put("key", "success");
				} else {
					data.put("errorData", map);
				}

			} catch (Exception e) {
				// TODO: handle exception
				data.put("key", "error");
				System.out.println(e);
			}
		}
		return data;

	}

	public static List<Map<String, String>> getList(String path) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		try {
			//fs = new POIFSFileSystem(in);
			try {
				InputStream in = new FileInputStream(path);
				wb = new HSSFWorkbook(in);
			}catch(Exception e) {
				try {
					InputStream in = new FileInputStream(path);
					wb = new XSSFWorkbook(in);
				}catch(Exception ex) {
					ex.printStackTrace();;
				}
			}
			try {
				HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			}catch(Exception e) {
				//e.printStackTrace();
			}
			sheet = wb.getSheetAt(0);

			String c = "";
			String numberValue = null;
			// 计算动态行的个数
			int rownum = 0;
			for (int n = 0; n < sheet.getLastRowNum(); n++) {
				c = value(n, 0);
				if (c.contains("曾任省部级")) {
					rownum = n;
					break;
				}
			}

			for (int i = 3; i <= rownum; i++) {
				Map<String, String> map = new HashMap<String, String>();
				for (int j = 0; j < 13; j++) {
					c = value(i, j);
					switch (j) {
					case 0:
						String typenumber = c.trim();
						// 返回对应的类别
						if (typenumber != "") {
							numberValue = typechange(typenumber);
							map.put("type", typechange(typenumber));
						} else {
							map.put("type", numberValue);
						}
						break;
					case 1:
						map.put("A3604A", c.trim()); // 称谓
						break;
					case 2:
						map.put("A3601", c.trim()); // 姓名
						break;
					case 3:
						map.put("A3607", checkDate(c.trim())); // 出生年月
						break;
					case 4:
						map.put("A3627", c.trim()); // 政治面貌
						break;
					case 5:
						map.put("A3611", c.trim()); // 工作单位及职务
						break;
					case 6:
						map.put("A0184GZ", checkIdCode(c.trim())); // 家庭成员身份证号
						break;
					case 7:
						map.put("A0111GZ", c.trim()); // 籍贯
						break;
					case 8:
						map.put("A0115GZ", c.trim()); // 居住地
						break;
					case 9:
						map.put("A0111GZB", c.trim()); // 国籍
						break;
					case 10:
						map.put("A3621", c.trim()); // 民族
						break;
					case 11:
						map.put("A3631", c.trim()); // 人员身份
						break;
					case 12:
						map.put("A3641", c.trim()); // 人员现状
						break;

					}

				}

				c = value(rownum, 3);
				map.put("a36file", c.trim()); // 本人现任或曾任省部级及以上领导干部（含已离退休）秘书的情况

				if (!map.get("A3604A").equals("")) {
					list.add(map);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("！！！！！！！！！！！！！！！！！！！！");
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 对格式不规范的日期进行格式化处理
	 * @param obj
	 * @return
	 * @author zepeng
	 */
	public static String checkDate(String obj) {
		if(obj.indexOf("年")>-1||obj.indexOf("月")>-1||obj.indexOf("日")>-1||obj.indexOf(".")>-1||obj.indexOf("/")>-1||obj.indexOf("\\")>-1||obj.indexOf("-")>-1) {
			obj=obj.replace("年", "@").replace("月", "@").replace("日", "@").replace(".", "@").replace("/", "@").replace("\\", "@").replace("-", "@");
			String[] objs = obj.split("@");
			String year = objs[0];
			String month = objs[1].length()==1?"0"+objs[1]:objs[1];
			String day = "";
			if(objs.length>2)day=objs[2].length()==1?"0"+objs[2]:objs[2];
			obj = year+month+day;
		}
		return obj;
	}
	public static String value(int row, int cell) {
		String data = getCellFormatValue(sheet.getRow(row).getCell(cell)).toString();
		return data;
	}

	/**
	 * 根据Cell类型设置数据
	 *
	 * @param cell
	 * @return
	 * @author
	 */
	@SuppressWarnings("deprecation")
	private static Object getCellFormatValue(Cell cell) {
		Object cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type cell.getCellType()
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
			{
				if (String.valueOf(cell.getNumericCellValue()).indexOf("E") == -1) {
					cellvalue = String.valueOf(cell.getNumericCellValue());
				} else {
					cellvalue = new DecimalFormat("#").format(cell.getNumericCellValue());
				}
				if (HSSFDateUtil.isCellDateFormatted(cell)) { // 如果为Number类型的Date，转换为Date
					@SuppressWarnings("unused")
					Date date = cell.getDateCellValue();
					// cellvalue = DateFormatUtils.format(date,"yyyyMMdd");
				}
				break;
			}
			case Cell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				boolean isdate = false;
				try {
					isdate = DateUtil.isCellDateFormatted(cell);
				}catch(Exception e) {
					//e.printStackTrace();
				}
				if (isdate) {
					// 如果是Date类型则，转化为Data格式
					// data格式是带时分秒的：2013-7-10 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();
					// data格式是不带带时分秒的：2013-7-10
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
					Date date = cell.getDateCellValue();
					cellvalue = formatter.format(date);
				} else {// 如果是纯数字
					cellvalue = cell.getStringCellValue();
					// 取得当前Cell的数值
					//cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			default:// 默认的Cell值
				cellvalue = "";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	public static String typechange(String type) {
		String result = null;
		if (type.equals("父母")) {
			result = "1";
		} else if (type.equals("配偶及其父母")||type.startsWith("配偶")) {
			result = "2";
		} else if (type.equals("本人的兄弟姐妹")||type.indexOf("姐妹")>-1||type.indexOf("兄弟")>-1) {
			result = "3";
		} else if (type.equals("子女、子女的配偶及其父母")||type.indexOf("子女")>-1) {
			result = "4";
		} else if (type.equals("其他直系和三代以内旁系亲属中现任或曾任厅局级及以上职务，以及移居国（境）外的人员")||type.indexOf("三代")>-1) {
			result = "5";
		} else {
			result = type;
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	@Transaction
	public String insertDate(List<Map<String, String>> list, String a0000) {
		HBSession sess = HBUtil.getHBSession();
		String errorData = "";
		/* 刪除已有數據 */
		String sql8 = "DELETE  from A36New where a0000 =" + "'" + a0000 + "'";
		sess.createSQLQuery(sql8).executeUpdate();
		String deleteA36z1newSql = "DELETE  from A36Z1New where a0000 =" + "'" + a0000 + "'";
		sess.createSQLQuery(deleteA36z1newSql).executeUpdate();

		/* 查询秘书字段中有没有值，有就覆盖没有就新增 */
		String sql = "select * from a36_file where a0000='" + a0000 + "' order by to_number(t_ime) desc";
		List<A36_FILE> A36_FILEList = sess.createSQLQuery(sql).addEntity(A36_FILE.class).list();
		A36_FILE a36file = null;
		A36_FILE a30file1 = null;
		Map<String, String> map1 = list.get(0);
		if (A36_FILEList.size() <= 0) {
			a36file = new A36_FILE();
			a36file.setA0000(a0000);
			a36file.setFiletext(map1.get("a36file"));
			sess.save(a36file);
		} else {
			a30file1 = A36_FILEList.get(0);
			a30file1.setFiletext(map1.get("a36file"));
		}

		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			String t = map.get("type");
			if (t.equals("1") || t.equals("2")) {
				A36New a36 = new A36New();
				a36.setA3601(map.get("A3601")); // 姓名 查询是否存在 存在即覆盖
				a36.setA3604a(map.get("A3604A")); // 称谓
				a36.setA3607(map.get("A3607")); // 出生年月
				a36.setA3627(map.get("A3627")); // 政治面貌
				a36.setA3611(map.get("A3611")); // 工作单位及职务
				a36.setA0184gz(checkIdCode(map.get("A0184GZ"))); // 家庭成员身份证号
				//System.out.println("1!!!"+map.get("A0184GZ").trim()+"!!!");
				a36.setA0111gz(CODEVALUE(map.get("A0111GZ"), sess, 6)); // 籍贯
				a36.setA0115gz(CODEVALUE(map.get("A0115GZ"), sess, 6)); // 居住地
				a36.setA0111gzb(CODEVALUE(map.get("A0111GZB"), sess, 7)); // 国籍
				a36.setA3621(CODEVALUE(map.get("A3621"), sess, 3));// 民族
				a36.setA3631(CODEVALUE(map.get("A3631"), sess, 4));// 人员身份
				a36.setA3641(CODEVALUE(map.get("A3641"), sess, 5));// 人员现状
				a36.setA3600(UUID.randomUUID().toString());
				a36.setA0000(a0000);
				a36.setUpdated(map.get("type"));
				a36.setA3645("0");
				//System.out.println("a3600="+a36.getA3600()+"-------updated="+a36.getUpdated());
				String num = (String) sess.save(a36);
				if (num == null) {
					errorData += map.get("A3601") + "插入失败";
				}
			} else {
				/* 插入A36Z1New */
				A36Z1New a36zl = new A36Z1New();
				a36zl.setA3601(map.get("A3601")); // 姓名 查询是否存在 存在即覆盖
				a36zl.setA3604a(map.get("A3604A")); // 称谓
				a36zl.setA3607(map.get("A3607")); // 出生年月
				a36zl.setA3627(map.get("A3627")); // 政治面貌
				a36zl.setA3611(map.get("A3611")); // 工作单位及职务
				a36zl.setA0184gz(checkIdCode(map.get("A0184GZ"))); // 家庭成员身份证号
				//System.out.println("2!!!"+map.get("A0184GZ").trim()+"!!!");
				a36zl.setA0111gz(CODEVALUE(map.get("A0111GZ"), sess, 6)); // 籍贯
				a36zl.setA0115gz(CODEVALUE(map.get("A0115GZ"), sess, 6)); // 居住地
				a36zl.setA0111gzb(CODEVALUE(map.get("A0111GZB"), sess, 7)); // 国籍
				a36zl.setA3621(CODEVALUE(map.get("A3621"), sess, 3));// 民族
				a36zl.setA3631(CODEVALUE(map.get("A3631"), sess, 4));// 人员身份
				a36zl.setA3641(CODEVALUE(map.get("A3641"), sess, 5));// 人员现状
				a36zl.setA3600(UUID.randomUUID().toString());
				a36zl.setA0000(a0000);
				a36zl.setUpdated(map.get("type"));
				a36zl.setA3645("0");
				//System.out.println("a3600="+a36zl.getA3600()+"-------updated="+a36zl.getUpdated());
				String num = (String) sess.save(a36zl);
				if (num == null) {
					errorData += map.get("A3601") + "插入失败";
				}
			}

		}
		// String a = null;
		// a.toString();
		sess.flush();
		if (errorData != "") {
			String name = (String) HBUtil.getHBSession()
					.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult();
			errorData = name + ":" + errorData;
		}
		return errorData;

	}
	public static String checkIdCode(String idcode) {
		idcode = idcode.trim().replace("　","").replace("Ｘ","X").replace("ｘ","X").replace("x","X")
				.replace("１","1").replace("２","2").replace("３","3").
				replace("４","4").replace("５","5").replace("６","6").
				replace("７","7").replace("８","8").replace("９","9").replace("０","0");
		String temp = "";
		for(char s:idcode.toCharArray()) {
			if("1234567890X".indexOf(s)>-1) {
				temp+=s;
			}
		}
		idcode=temp;
		if(idcode.length()>18) {
				idcode=idcode.substring(0,18);
		}
		return idcode;
	}

	private String CODEVALUE(String code, HBSession sess, int num) {
		if (!"".equals(code)) {
			String sql = "select  CODE_VALUE   from CODE_VALUE where code_name =" + "'" + code + "' and rownum =1 ";
			switch (num) {
			case 1:
				break;
			case 2:
				sql = sql + " and  CODE_TYPE='GB4762'";
				break;
			case 3:
				sql = sql + " and  CODE_TYPE='GB3304'";
				break;
			case 4:
				sql = sql + " and  CODE_TYPE='ZB06'";
				break;
			case 5:
				sql = sql + " and  CODE_TYPE='ZB56'";
				break;
			case 6:
				sql = "select  CODE_VALUE   from CODE_VALUE where code_name2 =" + "'" + code + "'";
				sql = sql + " and  CODE_TYPE='ZB01' and rownum =1";
				break;
			case 7:
				sql = sql + " and  CODE_TYPE='GB2659'";
				break;
			}

			String result = (String) sess.createSQLQuery(sql).uniqueResult();
			return result;

		}
		return null;
	}

	/**
	 * 判断Excel的版本,获取Workbook
	 *
	 * @param in
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWorkbok(InputStream in, File file) throws IOException {
		Workbook wb = null;
		if (file.getName().endsWith(EXCEL_XLS)) { // Excel 2003
			wb = new HSSFWorkbook(in);
		} else if (file.getName().endsWith(EXCEL_XLSX)) { // Excel 2007/2010
			wb = new XSSFWorkbook(in);
		}
		in.close();
		return wb;
	}

	public void errorExcel(List<Map<String, String>> list, String expFile) {
		ExportAsposeBS export = new ExportAsposeBS();
		@SuppressWarnings("static-access")
		String rootPath = export.getRootPath();
		String tempPath = rootPath + "errordata.xls";
		String newFileName = "error.xls";
		File newFile = export.createNewFile(tempPath, expFile, newFileName);
		// 将excel文件转为输入流
		InputStream is = null;
		// 第一步，创建一个webbook，对应一个Excel文件
		try {
			is = new FileInputStream(newFile);
			@SuppressWarnings("resource")
			Workbook wb = new HSSFWorkbook(is); //
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			Sheet sheet = wb.getSheet("Sheet1");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
			Map<String, String> map0 = list.get(0);
			OutputStream fos = new FileOutputStream(newFile);
			// 显示成功和失败次数
			Row row1 = sheet.createRow(0);
			Cell cell1 = row1.createCell(0);
			int dataLenth = Integer.parseInt(map0.get("length")); // 上传数据数量
			int errorLenth = list.size() - 1; // 失败次数
			int successLenth = dataLenth - errorLenth; // 成功次数
			String information = "上传成功：" + successLenth + "条数据，上传失败：" + errorLenth + "条数据";
			cell1.setCellValue(information);
			for (int i = 1; i < list.size(); i++) {
				Row row = sheet.createRow(i + 1);
				Map<String, String> map = list.get(i);
				if(!map.containsKey("errorData")) {
					continue;
				}
				String message = map.get("errorData").replace("\\", "");
				String messageArray[] = message.split("@");
				Cell filename = row.createCell(0);
				filename.setCellValue(messageArray[0]);

				Cell errorMessage = row.createCell(1);
				errorMessage.setCellValue(messageArray[1]);
			}

			wb.write(fos);
			fos.flush();
			fos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void downloadExcel(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		File file = null;
		DataInputStream in = null;
		OutputStream out = null;
		OutputStream toClient = null;
		try {

			String url = request.getParameter("url");
			String fileName = request.getParameter("fileName");
			file = new File(url + fileName);
			response.reset();
			// 设置response的Header
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			response.setContentType("application/msexcel");
			in = new DataInputStream(new FileInputStream(file));
			out = response.getOutputStream();
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(bufferOut);
			toClient.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != toClient) {
					toClient.close();
				}
				if (null != toClient) {
					toClient.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
