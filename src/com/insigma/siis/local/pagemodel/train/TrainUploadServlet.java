package com.insigma.siis.local.pagemodel.train;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Train;
import com.insigma.siis.local.business.entity.TrainAtt;
import com.insigma.siis.local.business.entity.TrainElearning;
import com.insigma.siis.local.business.entity.TrainLeader;
import com.insigma.siis.local.business.entity.TrainPersonnel;
import com.insigma.siis.local.business.entity.TrainScore;
import com.insigma.siis.local.business.entity.TrainUnit;
import com.insigma.siis.local.epsoft.config.AppConfig;
public class TrainUploadServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html;charset=GBK");
		req.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("GBK");
		String upload_file = AppConfig.HZB_PATH + "\\Customtemp\\impdata";
		PrintWriter out = null;
		String filename=null;
		String filePath = null;
		try {
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
				file.mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			List<FileItem> fileItems = uploader.parseRequest(req);
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					
				} else {
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
					try {
						filename = path.substring(path.lastIndexOf("\\") + 1);
						//String md5Name = MD5.MD5(filename);
						String houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//String md5Name = MD5.MD5(filename);
						filePath = upload_file +"\\"+ "培训数据"+System.currentTimeMillis() + "." + houzhui;
						fi.write(new File(filePath));
						fi.getOutputStream().close();
						fi = null;
						} catch (Exception e) {
						e.printStackTrace();
						try {
							throw new AppException("上传失败");
						} catch (AppException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//导入数据库
		impData(readWorkBook(new File(filePath)));
	}
	
	
	public Workbook readWorkBook(File file) {
		Workbook wb = null;
		InputStream is = null;
		try {
			// 判断文件是否存在
			if (null == file || !file.exists()) {
				return null;
			}
			is = new FileInputStream(file);
			/** 使用WorkbookFactory不用再去判断Excel因为版本不同而使用不同的方法 */
			wb = WorkbookFactory.create(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception e) {
					is = null;
					e.printStackTrace();
				}
			}
		}
		return wb;

	}

	@Transaction
	public void impData(Workbook wb) {
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
		String today = s.format(new Date());
		HBSession sess = HBUtil.getHBSession();
		/** 遍历sheet */
		for (int h = 0; h < wb.getNumberOfSheets(); h++) {
			Sheet sheet = wb.getSheetAt(h);
			/** 得到Excel的总行数 */
			int rowCount = sheet.getPhysicalNumberOfRows();
			if("培训班数据".equals(sheet.getSheetName())){
				/** 循环Excel的行 */
				for (int i = 1; i < rowCount; i++) {
					Row row = sheet.getRow(i);
					if (null == row) {
						continue;
					}
					Train t = new Train();
					boolean flag = false;
					/** 循环Excel的列 */
					for (int c = 0; c < row.getLastCellNum(); c++) {
						if(flag){
							break;
						}
						Cell cell = row.getCell(c);
						String cellValue = getValue(cell);
						switch (c) {
						case 0:
							t.setG11020(new BigDecimal(cellValue));
							break;
						case 1:
							if(StringUtils.isEmpty(cellValue)){
								flag = true;
								break;
							}
							t.setTrainid(cellValue);
							break;
						case 2:
							t.setA1131(cellValue);
							break;
						case 3:
							String value ="";
							List<Object[]> list = sess.createSQLQuery("select code_value,code_name from code_value where code_type='ZB29'").list();
							for(Object[] o :list){
								if(o[1].toString().equals(cellValue)){
									value=o[0].toString();
									break;
								}
							}
							t.setA1101(value);
							break;
						case 4:
							t.setA1114(cellValue);
							break;
						case 5:
							t.setA1107(cellValue);
							break;
						case 6:
							t.setA1111(cellValue);
							break;
						case 7:
							t.setG11021(cellValue);
							break;
						case 8:
							t.setG11022(cellValue);
							break;
						case 9:
							t.setG11023(cellValue);
							break;
						case 10:
							t.setG11024(cellValue);
							break;
						case 11:
							if(!StringUtils.isEmpty(cellValue)){
								t.setG11025(today+"/"+cellValue);
							}else{
								t.setG11025(cellValue);
							}
							break;
						case 12:
							if(!StringUtils.isEmpty(cellValue)){
								t.setG11026(today+"/"+cellValue);
							}else{
								t.setG11026(cellValue);
							}
							break;
						case 13:
							if(!StringUtils.isEmpty(cellValue)){
								t.setG11030(today+"/"+cellValue);
							}else{
								t.setG11030(cellValue);
							}
							break;
						default:
							break;
						}
					}
					String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
					String username = SysUtil.getCacheCurrentUser().getUserVO().getName();
					t.setUserid(userid);
					t.setUsername(username);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					t.setUpdatetime(sdf.format(new Date()));
					sess.save(t);
					sess.flush();
				}
			}else if("学员培训信息".equals(sheet.getSheetName())){
				/** 循环Excel的行 */
				for (int i = 1; i < rowCount; i++) {
					Row row = sheet.getRow(i);
					if (null == row) {
						continue;
					}
					TrainPersonnel tp = new TrainPersonnel();
					boolean flag = false;
					/** 循环Excel的列 */
					for (int c = 0; c < row.getLastCellNum(); c++) {
						if(flag){
							break;
						}
						Cell cell = row.getCell(c);
						String cellValue = getValue(cell);
						switch (c) {
						case 0:
							if(StringUtils.isEmpty(cellValue)){
								flag = true;
								break;
							}
							tp.setTrainid(cellValue);
							break;
						case 1:
							if(StringUtils.isEmpty(cellValue)){
								flag = true;
								break;
							}
							tp.setA0101(cellValue);
							break;
						case 2:
							if("男".equals(cellValue)){
								tp.setA0104("1");
							}else{
								tp.setA0104("2");
							}
							break;
						case 3:
							if(StringUtils.isEmpty(cellValue)){
								flag = true;
								break;
							}
							tp.setA0184(cellValue);
							break;
						case 4:
							tp.setA0177(cellValue);
							break;
						case 5:
							tp.setA0192a(cellValue);
							break;
						case 6:
							if("否".equals(cellValue)){
								tp.setA0199("0");
							}else{
								tp.setA0199("1");
							}
							break;
						case 7:
							String value ="";
							List<Object[]> list = sess.createSQLQuery("select code_value,code_name from code_value where code_type='TrainZB09'").list();
							for(Object[] o :list){
								if(o[1].toString().equals(cellValue)){
									value=o[0].toString();
									break;
								}
							}
							tp.setG11027(value);
							break;
						case 8:
							if("否".equals(cellValue)){
								tp.setG11028("0");
							}else{
								tp.setG11028("1");
							}
							break;
						case 9:
							if("否".equals(cellValue)){
								tp.setG11029("0");
							}else{
								tp.setG11029("1");
							}
							break;
						case 10:
							tp.setG11032(cellValue);
							break;
						case 11:
							tp.setG02003(cellValue);
							break;
						case 12:
							tp.setA1108(new BigDecimal(cellValue));
							break;
						default:
							break;
						}
					}
					String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
					String username = SysUtil.getCacheCurrentUser().getUserVO().getName();
					tp.setUserid(userid);
					tp.setUsername(username);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					tp.setUpdatetime(sdf.format(new Date()));
					sess.save(tp);
					sess.flush();
				}
			}else if("领导干部上讲台".equals(sheet.getSheetName())){
				/** 循环Excel的行 */
				for (int i = 1; i < rowCount; i++) {
					Row row = sheet.getRow(i);
					if (null == row) {
						continue;
					}
					TrainLeader tl = new TrainLeader();
					boolean flag = false;
					/** 循环Excel的列 */
					for (int c = 0; c < row.getLastCellNum(); c++) {
						if(flag){
							break;
						}
						Cell cell = row.getCell(c);
						String cellValue = getValue(cell);
						switch (c) {
						case 0:
							if(StringUtils.isEmpty(cellValue)){
								flag = true;
								break;
							}
							tl.setTrainid(cellValue);
							break;
						case 1:
							if(StringUtils.isEmpty(cellValue)){
								flag = true;
								break;
							}
							tl.setA0101(cellValue);
							break;
						case 2:
							if("男".equals(cellValue)){
								tl.setA0104("1");
							}else{
								tl.setA0104("2");
							}
							break;
						case 3:
							if(StringUtils.isEmpty(cellValue)){
								flag = true;
								break;
							}
							tl.setA0184(cellValue);
							break;
						case 4:
							tl.setA0177(cellValue);
							break;
						case 5:
							tl.setA0192a(cellValue);
							break;
						case 6:
							if("否".equals(cellValue)){
								tl.setA0199("0");
							}else{
								tl.setA0199("1");
							}
							break;
						case 7:
							String value ="";
							List<Object[]> list = sess.createSQLQuery("select code_value,code_name from code_value where code_type='TrainZB09'").list();
							for(Object[] o :list){
								if(o[1].toString().equals(cellValue)){
									value=o[0].toString();
									break;
								}
							}
							tl.setG11027(value);
							break;
						case 8:
							tl.setG11037(cellValue);
							break;
						case 9:
							tl.setG11038(cellValue);
							break;
						case 10:
							tl.setA1108(new BigDecimal(cellValue));
							break;
						default:
							break;
						}
					}
					String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
					String username = SysUtil.getCacheCurrentUser().getUserVO().getName();
					tl.setUserid(userid);
					tl.setUsername(username);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					tl.setUpdatetime(sdf.format(new Date()));
					sess.save(tl);
					sess.flush();
				}
			}else if("培训场景资料".equals(sheet.getSheetName())){
				/** 循环Excel的行 */
				for (int i = 1; i < rowCount; i++) {
					Row row = sheet.getRow(i);
					if (null == row) {
						continue;
					}
					TrainAtt ta = new TrainAtt();
					boolean flag = false;
					/** 循环Excel的列 */
					for (int c = 0; c < row.getLastCellNum(); c++) {
						if(flag){
							break;
						}
						Cell cell = row.getCell(c);
						String cellValue = getValue(cell);
						switch (c) {
						case 0:
							if(StringUtils.isEmpty(cellValue)){
								flag = true;
								break;
							}
							ta.setTrainid(cellValue);
							break;
						case 1:
							ta.setG11031(cellValue);
							break;
						case 2:
							ta.setG11052(today+"/"+cellValue);
							break;
						default:
							break;
						}
					}
					String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
					String username = SysUtil.getCacheCurrentUser().getUserVO().getName();
					ta.setUserid(userid);
					ta.setUsername(username);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					ta.setUpdatetime(sdf.format(new Date()));
					sess.save(ta);
					sess.flush();
				}
			}else if("学员考试成绩".equals(sheet.getSheetName())){
				/** 循环Excel的行 */
				for (int i = 1; i < rowCount; i++) {
					Row row = sheet.getRow(i);
					if (null == row) {
						continue;
					}
					TrainScore ts = new TrainScore();
					boolean flag = false;
					/** 循环Excel的列 */
					for (int c = 0; c < row.getLastCellNum(); c++) {
						if(flag){
							break;
						}
						Cell cell = row.getCell(c);
						String cellValue = getValue(cell);
						switch (c) {
						case 0:
							if(StringUtils.isEmpty(cellValue)){
								flag = true;
								break;
							}
							ts.setG11020(new BigDecimal(cellValue));
							break;
						case 1:
							if(StringUtils.isEmpty(cellValue)){
								flag = true;
								break;
							}
							ts.setA0101(cellValue);
							break;
						case 2:
							if("男".equals(cellValue)){
								ts.setA0104("1");
							}else{
								ts.setA0104("2");
							}
							break;
						case 3:
							if(StringUtils.isEmpty(cellValue)){
								flag = true;
								break;
							}
							ts.setA0184(cellValue);
							break;
						case 4:
							ts.setA0177(cellValue);
							break;
						case 5:
							ts.setA0192a(cellValue);
							break;
						case 6:
							if("否".equals(cellValue)){
								ts.setA0199("0");
							}else{
								ts.setA0199("1");
							}
							break;
						case 7:
							String value_g11027 ="";
							List<Object[]> list_g11027 = sess.createSQLQuery("select code_value,code_name from code_value where code_type='TrainZB09'").list();
							for(Object[] o :list_g11027){
								if(o[1].toString().equals(cellValue)){
									value_g11027=o[0].toString();
									break;
								}
							}
							ts.setG11027(value_g11027);
							break;
						case 8:
							String value ="";
							List<Object[]> list = sess.createSQLQuery("select code_value,code_name from code_value where code_type='GB2230'").list();
							for(Object[] o :list){
								if(o[1].toString().equals(cellValue)){
									value=o[0].toString();
									break;
								}
							}
							ts.setG11039(value);
							break;
						case 9:
							ts.setG11040(cellValue);
							break;
						case 10:
							ts.setG11041(new BigDecimal(cellValue));
							break;
						case 11:
							ts.setA1108(new BigDecimal(cellValue));
							break;
						default:
							break;
						}
					}
					String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
					String username = SysUtil.getCacheCurrentUser().getUserVO().getName();
					ts.setUserid(userid);
					ts.setUsername(username);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					ts.setUpdatetime(sdf.format(new Date()));
					sess.save(ts);
					sess.flush();
				}
			}else if("网络课程培训".equals(sheet.getSheetName())){
				/** 循环Excel的行 */
				for (int i = 1; i < rowCount; i++) {
					Row row = sheet.getRow(i);
					if (null == row) {
						continue;
					}
					TrainElearning te = new TrainElearning();
					boolean flag = false;
					/** 循环Excel的列 */
					for (int c = 0; c < row.getLastCellNum(); c++) {
						if(flag){
							break;
						}
						Cell cell = row.getCell(c);
						String cellValue = getValue(cell);
						switch (c) {
						case 0:
							te.setG11020(new BigDecimal(cellValue));
							break;
						case 1:
							if(StringUtils.isEmpty(cellValue)){
								flag = true;
								break;
							}
							te.setA0101(cellValue);
							break;
						case 2:
							if("男".equals(cellValue)){
								te.setA0104("1");
							}else{
								te.setA0104("2");
							}
							break;
						case 3:
							if(StringUtils.isEmpty(cellValue)){
								flag = true;
								break;
							}
							te.setA0184(cellValue);
							break;
						case 4:
							te.setA0177(cellValue);
							break;
						case 5:
							te.setA0192a(cellValue);
							break;
						case 6:
							if("否".equals(cellValue)){
								te.setA0199("0");
							}else{
								te.setA0199("1");
							}
							break;
						case 7:
							String value ="";
							List<Object[]> list = sess.createSQLQuery("select code_value,code_name from code_value where code_type='TrainZB09'").list();
							for(Object[] o :list){
								if(o[1].toString().equals(cellValue)){
									value=o[0].toString();
									break;
								}
							}
							te.setG11027(value);
							break;
						case 8:
							te.setG11042(cellValue);
							break;
						case 9:
							te.setA1107(cellValue);
							break;
						case 10:
							te.setA1111(cellValue);
							break;
						case 11:
							te.setA1108(new BigDecimal(cellValue));
							break;
						default:
							break;
						}
					}
					String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
					String username = SysUtil.getCacheCurrentUser().getUserVO().getName();
					te.setUserid(userid);
					te.setUsername(username);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					te.setUpdatetime(sdf.format(new Date()));
					sess.save(te);
					sess.flush();
				}
			}else if("单位人数信息".equals(sheet.getSheetName())){
				/** 循环Excel的行 */
				for (int i = 1; i < rowCount; i++) {
					Row row = sheet.getRow(i);
					if (null == row) {
						continue;
					}
					TrainUnit tu = new TrainUnit();
					/** 循环Excel的列 */
					for (int c = 0; c < row.getLastCellNum(); c++) {
						Cell cell = row.getCell(c);
						String cellValue = getValue(cell);
						switch (c) {
						case 0:
							tu.setG11020(new BigDecimal(cellValue));
							break;
						case 1:
							tu.setUnitid(cellValue);
							break;
						case 2:
							tu.setUnitname(cellValue);
							break;
						case 3:
							tu.setPnum1(new BigDecimal(cellValue));
							break;
						case 4:
							tu.setPnum2(new BigDecimal(cellValue));
							break;
						case 5:
							tu.setPnum3(new BigDecimal(cellValue));
							break;
						case 6:
							tu.setPnum4(new BigDecimal(cellValue));
							break;
						case 7:
							tu.setPnum5(new BigDecimal(cellValue));
							break;
						default:
							break;
						}
					}
					sess.save(tu);
					sess.flush();
				}
			}
		}
	}
	
	public static String getValue(Cell cell){
		String cellValue="";
		if (cell != null) {

			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
					cellValue = fmt.format(cell.getDateCellValue());
				} else {
					Double d = cell.getNumericCellValue();
					cellValue = d.toString();
					// 解决1234.0 去掉后面的.0
					if (null != cellValue && !"".equals(cellValue.trim())) {
						String[] item = cellValue.split("[.]");
						if (1 < item.length && "0".equals(item[1])) {
							cellValue = item[0];
						}
					}
				}
				break;

			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;

			case Cell.CELL_TYPE_BLANK:

				cellValue = cell.getStringCellValue();
				break;

			case Cell.CELL_TYPE_ERROR:

				cellValue = "";
				break;

			case Cell.CELL_TYPE_FORMULA:
				cellValue = cell.getStringCellValue();
				break;

			default:
				cellValue = cell.getStringCellValue();
			}
		}
		return cellValue;
	}
	public static void main(String[] args) {
	}
}