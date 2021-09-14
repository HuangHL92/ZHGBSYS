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
 * ���ݵ��빤��action
 *
 *
 *
 */
public class FormImportAction extends ActionSupport {
	private static final String separator = System.getProperty("file.separator");
	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";
	private static final String ZIP = "zip";
	private static POIFSFileSystem fs;// poi�ļ���
	private static Workbook wb;// ���execl
	private static Sheet sheet;// ��ù�����

	/**
	 * �������
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

		Map<String, String> data = new HashMap<String, String>();// ����ǰ̨��MSG
		Map<String, String> tempmap = new HashMap<String, String>();
		String filePath = "";
		String str = "";

		// ���������ļ�����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		// PrintWriter out = response.getWriter();
		String uuid = UUID.randomUUID().toString();
		List<FileItem> fileItems = null;
		Iterator<FileItem> iter;

		String uploadFileName = "";

		try {
			fileItems = uploader.parseRequest(request); // �ļ�����
		} catch (FileUploadException e) {
			str = "�����ļ�����Ϊ�գ������µ��룡";
			data.put("error", str);
			e.printStackTrace();
			this.doSuccess(request, data);
			return this.ajaxResponse(request, response);
		}

		if (!fileItems.isEmpty()) {
			iter = fileItems.iterator();
			String upload_file = AppConfig.HZB_PATH + "/temp/upload/tempFile/";// �ϴ�·��
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
				file.mkdirs();
			}
			FileItem fi = null;
			while (iter.hasNext()) {

				fi = iter.next();

				if (fi == null || fi.getSize() == 0) {
					str = "�����ļ�����Ϊ�գ������µ��룡";
					data.put("error", str);
					this.doSuccess(request, data);
					return this.ajaxResponse(request, response);
				}
				if (!(fi.getName().endsWith(EXCEL_XLS) || (fi.getName().endsWith(EXCEL_XLSX))
						|| fi.getName().endsWith(ZIP))) {
					data.put("error", "�ļ�����Excel��ʽ��zip,���飡");
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

		// ��ȡ�������е��ļ��������д���
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
				data.put("error", "����ʧ�ܣ�");
				data.put("key", "error");
				data.put("type", "zip");
				data.put("file", expFile);
				this.doSuccess(request, data);
				return this.ajaxResponse(request, response);
			} else {
				str = "����ɹ���";
				data.put("success", str);
				data.put("key", "success");
				this.doSuccess(request, data);
				return this.ajaxResponse(request, response);
			}

		} else {
			str = "�ļ���ʽ���ǵ�����ģ���ʽ,�����µ��룡";
			data.put("error", str);
			this.doSuccess(request, data);
			return this.ajaxResponse(request, response);
		}

	}

	/**
	 * ��������: ������Ӧ������
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
	 * ��ʽ����
	 *
	 * @param unid
	 * @param path
	 * @return
	 */
	private Map<String, String> Imp(String fileName, String uuid, String path) {
		HBSession session = HBUtil.getHBSession();
		Map<String, String> data = new HashMap<String, String>();// ����ǰ̨��MSG

		FileInputStream is = null;
		path = path.replaceAll("\\\\", "\\/");

		try {
			// �Ȼ�ȡһ��sheetҳ��������Ϣ
			// ֧��Excel 2003 2007
			File excelFile = new File(path); // �����ļ�����
			// System.out.println("����path+++++++++++++++++++++++++"+path);
			is = new FileInputStream(excelFile);
			Workbook workbook = getWorkbok(is, excelFile);
			// ������ʼ������ Map<String, String> mapA0000
			Sheet sheet = workbook.getSheetAt(0);
			long start = System.currentTimeMillis();
			System.out.println("--------------------------------------------����Sheet��1");

			data = dispose(path, sheet, uuid, session);
			System.out.println(data.get("errorData"));
			long end = System.currentTimeMillis();
			System.out.println(
					"--------------------------------------------Sheet��1�������,��ʱ��" + ((end - start) / 1000) + "��");
			data.put("success", "Excel����ɹ���");
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
	 * Zip ��������
	 *
	 * @throws IOException
	 */
	private List<Map<String, String>> ZipImp(String fileName, String uuid, String path, Map<String, String> tempmap)
			throws IOException {
		// long start = System.currentTimeMillis();
		HBSession session = HBUtil.getHBSession();
		Map<String, String> data = new HashMap<String, String>();// ����ǰ̨��MSG
		List<Map<String, String>> errorExcel = new java.util.ArrayList<Map<String, String>>();
		FileInputStream is = null;
		path = path.replaceAll("\\\\", "\\/");
		String upload_file = AppConfig.HZB_PATH + "/temp/upload/tempFile/" + uuid + "/";
		upload_file = upload_file.replace("\\\\", "\\/");
		try {
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// ����ļ��в������򴴽�
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
				// ������ʼ������ Map<String, String> mapA0000
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
	 * ɾ��Ŀ¼���ļ��У��Լ�Ŀ¼�µ��ļ�
	 *
	 * @param sPath ��ɾ��Ŀ¼���ļ�·��
	 * @return Ŀ¼ɾ���ɹ�����true�����򷵻�false
	 */
	public boolean deleteDirectory(String sPath) {
		// ���sPath�����ļ��ָ�����β���Զ�����ļ��ָ���
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// ���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// ɾ���ļ����µ������ļ�(������Ŀ¼)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// ɾ�����ļ�
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // ɾ����Ŀ¼
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// ɾ����ǰĿ¼
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ɾ�������ļ�
	 *
	 * @param sPath ��ɾ���ļ����ļ���
	 * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File f = new File(sPath);
		// ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��
		if (f.isFile() && f.exists()) {
			f.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * ���������ȡ����
	 *
	 * @param sheet
	 * @param imprecordid
	 * @param session
	 * @return
	 * @throws Exception
	 *
	 *                   �����12380��վ������excel
	 */
	private Map<String, String> dispose(String path, Sheet sheet, String imprecordid, HBSession session)
			throws Exception {
		Map<String, String> data = new HashMap<String, String>();// ����ǰ̨��MSG
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
			data.put("errorData", filename + "@" + "���λ�ô��������ģ��������");
			data.put("key", "error");
			return data;
		}
		String a0184 = "";
		if(c.getCellType()==0){
			data.put("errorData", filename + "@" + "�ȼ���ĵڶ����е����֤��Ϣ����");
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
			data.put("errorData", filename + "@" + "���֤��������ݲ����ڣ�");
			data.put("key", "error");
			return data;
		}
		a01 = list2.get(0);
		List<A36> lista36 = session.createSQLQuery("select * from a36 where a0000='" + a01.getA0000() + "' and A3604A in('����','ĸ��')").addEntity(A36.class)
				.list();
		boolean parentCheck = true;
		String parentNames="";
		for(A36 a36:lista36) {
			parentNames+=a36.getA3601().replace(" ","").replace("��","")+",";
			parentCheck = false;
		}

		if (list2.size() < 1 || a0184 == null && "".equals(a0184)) {
			data.put("errorData", filename + "@" + "���֤��������ݲ����ڣ�");
			data.put("key", "error");
			return data;
		} else {
			// insert data from excel
			try {
				List<Map<String,String>> datas = getList(path);
				if (datas.size()==0) {
					data.put("errorData", filename + "@" + "δ�ҵ���Ч�ļ�ͥ��Ա���ݣ�");
					data.put("key", "error");
					return data;
				}
				for(Map<String,String> d:datas) {
					if(parentNames!=null&&"����ĸ��".contains(d.get("A3604A"))&&parentNames.contains(d.get("A3601").replace(" ","").replace("��",""))) {
						parentCheck=true;
					}
				}
				if(!parentCheck) {
					data.put("errorData", filename + "@" + "��ĸ��Ϣδ����һλƥ�䣬����ɲ���ͥ��Ա��Ϣ�Ƿ���ȷ��");
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
			// ���㶯̬�еĸ���
			int rownum = 0;
			for (int n = 0; n < sheet.getLastRowNum(); n++) {
				c = value(n, 0);
				if (c.contains("����ʡ����")) {
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
						// ���ض�Ӧ�����
						if (typenumber != "") {
							numberValue = typechange(typenumber);
							map.put("type", typechange(typenumber));
						} else {
							map.put("type", numberValue);
						}
						break;
					case 1:
						map.put("A3604A", c.trim()); // ��ν
						break;
					case 2:
						map.put("A3601", c.trim()); // ����
						break;
					case 3:
						map.put("A3607", checkDate(c.trim())); // ��������
						break;
					case 4:
						map.put("A3627", c.trim()); // ������ò
						break;
					case 5:
						map.put("A3611", c.trim()); // ������λ��ְ��
						break;
					case 6:
						map.put("A0184GZ", checkIdCode(c.trim())); // ��ͥ��Ա���֤��
						break;
					case 7:
						map.put("A0111GZ", c.trim()); // ����
						break;
					case 8:
						map.put("A0115GZ", c.trim()); // ��ס��
						break;
					case 9:
						map.put("A0111GZB", c.trim()); // ����
						break;
					case 10:
						map.put("A3621", c.trim()); // ����
						break;
					case 11:
						map.put("A3631", c.trim()); // ��Ա���
						break;
					case 12:
						map.put("A3641", c.trim()); // ��Ա��״
						break;

					}

				}

				c = value(rownum, 3);
				map.put("a36file", c.trim()); // �������λ�����ʡ�����������쵼�ɲ������������ݣ���������

				if (!map.get("A3604A").equals("")) {
					list.add(map);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("����������������������������������������");
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * �Ը�ʽ���淶�����ڽ��и�ʽ������
	 * @param obj
	 * @return
	 * @author zepeng
	 */
	public static String checkDate(String obj) {
		if(obj.indexOf("��")>-1||obj.indexOf("��")>-1||obj.indexOf("��")>-1||obj.indexOf(".")>-1||obj.indexOf("/")>-1||obj.indexOf("\\")>-1||obj.indexOf("-")>-1) {
			obj=obj.replace("��", "@").replace("��", "@").replace("��", "@").replace(".", "@").replace("/", "@").replace("\\", "@").replace("-", "@");
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
	 * ����Cell������������
	 *
	 * @param cell
	 * @return
	 * @author
	 */
	@SuppressWarnings("deprecation")
	private static Object getCellFormatValue(Cell cell) {
		Object cellvalue = "";
		if (cell != null) {
			// �жϵ�ǰCell��Type cell.getCellType()
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:// �����ǰCell��TypeΪNUMERIC
			{
				if (String.valueOf(cell.getNumericCellValue()).indexOf("E") == -1) {
					cellvalue = String.valueOf(cell.getNumericCellValue());
				} else {
					cellvalue = new DecimalFormat("#").format(cell.getNumericCellValue());
				}
				if (HSSFDateUtil.isCellDateFormatted(cell)) { // ���ΪNumber���͵�Date��ת��ΪDate
					@SuppressWarnings("unused")
					Date date = cell.getDateCellValue();
					// cellvalue = DateFormatUtils.format(date,"yyyyMMdd");
				}
				break;
			}
			case Cell.CELL_TYPE_FORMULA: {
				// �жϵ�ǰ��cell�Ƿ�ΪDate
				boolean isdate = false;
				try {
					isdate = DateUtil.isCellDateFormatted(cell);
				}catch(Exception e) {
					//e.printStackTrace();
				}
				if (isdate) {
					// �����Date������ת��ΪData��ʽ
					// data��ʽ�Ǵ�ʱ����ģ�2013-7-10 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();
					// data��ʽ�ǲ�����ʱ����ģ�2013-7-10
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
					Date date = cell.getDateCellValue();
					cellvalue = formatter.format(date);
				} else {// ����Ǵ�����
					cellvalue = cell.getStringCellValue();
					// ȡ�õ�ǰCell����ֵ
					//cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			case Cell.CELL_TYPE_STRING:// �����ǰCell��TypeΪSTRING
				// ȡ�õ�ǰ��Cell�ַ���
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			default:// Ĭ�ϵ�Cellֵ
				cellvalue = "";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	public static String typechange(String type) {
		String result = null;
		if (type.equals("��ĸ")) {
			result = "1";
		} else if (type.equals("��ż���丸ĸ")||type.startsWith("��ż")) {
			result = "2";
		} else if (type.equals("���˵��ֵܽ���")||type.indexOf("����")>-1||type.indexOf("�ֵ�")>-1) {
			result = "3";
		} else if (type.equals("��Ů����Ů����ż���丸ĸ")||type.indexOf("��Ů")>-1) {
			result = "4";
		} else if (type.equals("����ֱϵ������������ϵ���������λ��������ּ�������ְ���Լ��ƾӹ������������Ա")||type.indexOf("����")>-1) {
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
		/* �h�����Д��� */
		String sql8 = "DELETE  from A36New where a0000 =" + "'" + a0000 + "'";
		sess.createSQLQuery(sql8).executeUpdate();
		String deleteA36z1newSql = "DELETE  from A36Z1New where a0000 =" + "'" + a0000 + "'";
		sess.createSQLQuery(deleteA36z1newSql).executeUpdate();

		/* ��ѯ�����ֶ�����û��ֵ���о͸���û�о����� */
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
				a36.setA3601(map.get("A3601")); // ���� ��ѯ�Ƿ���� ���ڼ�����
				a36.setA3604a(map.get("A3604A")); // ��ν
				a36.setA3607(map.get("A3607")); // ��������
				a36.setA3627(map.get("A3627")); // ������ò
				a36.setA3611(map.get("A3611")); // ������λ��ְ��
				a36.setA0184gz(checkIdCode(map.get("A0184GZ"))); // ��ͥ��Ա���֤��
				//System.out.println("1!!!"+map.get("A0184GZ").trim()+"!!!");
				a36.setA0111gz(CODEVALUE(map.get("A0111GZ"), sess, 6)); // ����
				a36.setA0115gz(CODEVALUE(map.get("A0115GZ"), sess, 6)); // ��ס��
				a36.setA0111gzb(CODEVALUE(map.get("A0111GZB"), sess, 7)); // ����
				a36.setA3621(CODEVALUE(map.get("A3621"), sess, 3));// ����
				a36.setA3631(CODEVALUE(map.get("A3631"), sess, 4));// ��Ա���
				a36.setA3641(CODEVALUE(map.get("A3641"), sess, 5));// ��Ա��״
				a36.setA3600(UUID.randomUUID().toString());
				a36.setA0000(a0000);
				a36.setUpdated(map.get("type"));
				a36.setA3645("0");
				//System.out.println("a3600="+a36.getA3600()+"-------updated="+a36.getUpdated());
				String num = (String) sess.save(a36);
				if (num == null) {
					errorData += map.get("A3601") + "����ʧ��";
				}
			} else {
				/* ����A36Z1New */
				A36Z1New a36zl = new A36Z1New();
				a36zl.setA3601(map.get("A3601")); // ���� ��ѯ�Ƿ���� ���ڼ�����
				a36zl.setA3604a(map.get("A3604A")); // ��ν
				a36zl.setA3607(map.get("A3607")); // ��������
				a36zl.setA3627(map.get("A3627")); // ������ò
				a36zl.setA3611(map.get("A3611")); // ������λ��ְ��
				a36zl.setA0184gz(checkIdCode(map.get("A0184GZ"))); // ��ͥ��Ա���֤��
				//System.out.println("2!!!"+map.get("A0184GZ").trim()+"!!!");
				a36zl.setA0111gz(CODEVALUE(map.get("A0111GZ"), sess, 6)); // ����
				a36zl.setA0115gz(CODEVALUE(map.get("A0115GZ"), sess, 6)); // ��ס��
				a36zl.setA0111gzb(CODEVALUE(map.get("A0111GZB"), sess, 7)); // ����
				a36zl.setA3621(CODEVALUE(map.get("A3621"), sess, 3));// ����
				a36zl.setA3631(CODEVALUE(map.get("A3631"), sess, 4));// ��Ա���
				a36zl.setA3641(CODEVALUE(map.get("A3641"), sess, 5));// ��Ա��״
				a36zl.setA3600(UUID.randomUUID().toString());
				a36zl.setA0000(a0000);
				a36zl.setUpdated(map.get("type"));
				a36zl.setA3645("0");
				//System.out.println("a3600="+a36zl.getA3600()+"-------updated="+a36zl.getUpdated());
				String num = (String) sess.save(a36zl);
				if (num == null) {
					errorData += map.get("A3601") + "����ʧ��";
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
		idcode = idcode.trim().replace("��","").replace("��","X").replace("��","X").replace("x","X")
				.replace("��","1").replace("��","2").replace("��","3").
				replace("��","4").replace("��","5").replace("��","6").
				replace("��","7").replace("��","8").replace("��","9").replace("��","0");
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
	 * �ж�Excel�İ汾,��ȡWorkbook
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
		// ��excel�ļ�תΪ������
		InputStream is = null;
		// ��һ��������һ��webbook����Ӧһ��Excel�ļ�
		try {
			is = new FileInputStream(newFile);
			@SuppressWarnings("resource")
			Workbook wb = new HSSFWorkbook(is); //
			// �ڶ�������webbook�����һ��sheet,��ӦExcel�ļ��е�sheet
			Sheet sheet = wb.getSheet("Sheet1");
			// ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������
			Map<String, String> map0 = list.get(0);
			OutputStream fos = new FileOutputStream(newFile);
			// ��ʾ�ɹ���ʧ�ܴ���
			Row row1 = sheet.createRow(0);
			Cell cell1 = row1.createCell(0);
			int dataLenth = Integer.parseInt(map0.get("length")); // �ϴ���������
			int errorLenth = list.size() - 1; // ʧ�ܴ���
			int successLenth = dataLenth - errorLenth; // �ɹ�����
			String information = "�ϴ��ɹ���" + successLenth + "�����ݣ��ϴ�ʧ�ܣ�" + errorLenth + "������";
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
			// ����response��Header
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
