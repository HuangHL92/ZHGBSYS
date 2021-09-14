package com.insigma.siis.local.business.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.collections.list.TreeList;
import org.apache.commons.fileupload.FileItem;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.util.DateUtil;
import com.insigma.siis.local.business.entity.VerifyRule;
import com.insigma.siis.local.business.entity.VerifyScheme;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

/**
 * Excel导出工具类
 * 应用API地址：http://jexcelapi.sourceforge.net/resources/javadocs/2_6_10/docs/
 * 
 * @author mengl
 * 
 */
public class CustomExcelUtil {

	private static String pattern = "yyyyMMdd HH:mm:ss"; // 统一导出导入日期解析格式
	
	/**
	 * 把校验方案信息放到Map中
	 * @param vsc001Exp
	 * @param fileName
	 * @return
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,List<List<Object>>> getExcelMap(String vsc001Exp, String fileName) throws AppException{
		HBSession sess = HBUtil.getHBSession();
		List<List<Object>> list = null;
		String[] vsc001s = vsc001Exp.split(",");
		Map<String,List<List<Object>>> listContentMap = new HashMap<String,List<List<Object>>>();
		for(String vsc001Str : vsc001s){
			list = new ArrayList<List<Object>>();
			List<String> vru001s = new ArrayList<String>();
			List<Object> vsList = sess.createQuery("from  VerifyScheme a where a.vsc001 = :vsc001").setString("vsc001", vsc001Str).list();
			if (vsList == null || vsList.size() < 1 || vsList.get(0) == null) {
				throw new AppException("导出出错，错误信息：未找到要导出的方案信息，导出方案ID-" + vsc001Exp);
			}
			if(StringUtil.isEmpty(fileName)){
				fileName =  ((VerifyScheme) vsList.get(0)).getVsc002();
			}
			
			List<Object> vrList = sess.createQuery("from  VerifyRule a where a.vsc001 = :vsc001").setString("vsc001", vsc001Str).list();
			for (Object obj : vrList) {
				if (obj instanceof VerifyRule) {
					VerifyRule vr = (VerifyRule) obj;
					vru001s.add(vr.getVru001());
				}
			}
			List<Object> vslList = null;
			if(vru001s.size()>0){
				vslList = sess .createQuery( "from  VerifySqlList a where a.vru001 in  (:vru001s)").setParameterList("vru001s", vru001s).list();
			}
			list.add(vsList);
			if(vrList!=null && vrList.size()>0){
				list.add(vrList);
			}
			if(vslList!=null && vslList.size()>0){
				list.add(vslList);
			}
			
			
			listContentMap.put(fileName, list);
			fileName = "";
		}
		return listContentMap;
	}
	
	
	/**
	 * Excel导出方法
	 * 
	 * 应用API地址：http://jexcelapi.sourceforge.net/resources/javadocs/2_6_10/docs/
	 * 
	 * @author mengl
	 * @param response
	 *            HttpServletResponse对象
	 * @param listContent
	 *            生成Excel使用的对象 注：要对应List<List<Object>> listContent
	 *            中Object类型一致，不然列标题会对应不上
	 * @param fileName
	 *            导出文件名（会自动拼接当前日期）
	 * @param passwd
	 *            Excel保护密码
	 * @return
	 */

	public final static void exportExcel(HttpServletResponse response,
			Map<String,List<List<Object>>> listContentMap, String passwd) {
		String fileName = "";
		String result = "系统提示：Excel文件导出成功！";
		int count = 0 ;
		
		// 定义输出流，以便打开保存对话框______________________begin
		OutputStream os ;

		try {
			Set<String> keySet = listContentMap.keySet();
			
			os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			
			//1.单个文件直接输出下载
			if(listContentMap.size()==1){
				String key = keySet.iterator().next();
				if (!StringUtil.isEmpty(key)) {
					fileName = key;
				}else{
					fileName = "导出文件";
				}
				fileName = fileName.replace(".xls", "") + "_"
						+ DateUtil.toString(new Date(), "yyyyMMddHHmmss") + ".xls";	
				
				response.setHeader("Content-disposition", "attachment; filename="
						+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
				
				// 定义输出流，以便打开保存对话框
				// 设定输出文件头
				response.setContentType("application/msexcel");// 定义输出类型
				WritableWorkbook workbook = Workbook.createWorkbook(os);
				writeWorkBook(workbook,listContentMap.get(key),passwd);
			}else{
			//2.多个文件，生成到服务器端压缩到同一个文件夹后输出下载
				String zipTempDir = ExpRar.expFile();												//被压缩文件所在临时文件夹路径
				String zipFileName = "导出文件"+DateUtil.toString(new Date(), "yyyyMMddHHmmss")+".zip";//压缩文件名称
				String zipFileDir = ExpRar.expFile();												//压缩文件目录
				response.setHeader("Content-disposition", "attachment; filename="
						+ new String(zipFileName.getBytes("GB2312"), "ISO8859-1"));					// 设定输出文件头
				response.setContentType("application/msexcel");										// 定义输出类型
				
				File zipDirFile = new File(zipTempDir); 
				zipDirFile.mkdirs();
				
				CommonQueryBS.systemOut(zipTempDir);
				//2.1 把文件循环写入zipDir目录
				for(String key :keySet){
					count++;
					if (!StringUtil.isEmpty(key)) {
						fileName = key;
					}else{
						fileName = "导出文件";
					}
					fileName = fileName.replace(".xls", "") + "_"
							+ DateUtil.toString(new Date(), "yyyyMMddHHmmss") +"_No"+count+ ".xls";	
					
					File file = new File(zipTempDir+fileName);
					if(!file.exists()){
						file.createNewFile();
					}
					WritableWorkbook workbook = Workbook.createWorkbook(file);
					writeWorkBook(workbook,listContentMap.get(key),passwd);
				}
				//2.2 压缩zipTempDir文件夹
				ZipUtil.compress(zipTempDir, zipFileDir+zipFileName, "", "");
				//2.3 下载
				File zifFile = new File(zipFileDir+zipFileName);
				InputStream is = new FileInputStream(zifFile);
				BufferedInputStream bis = new BufferedInputStream(is);
				byte[] buffer = new byte[1024];
				int read ;
				while ((read = bis.read(buffer)) > 0) {  
			          os.write(buffer, 0, read);  
			    } 
				
				try {
					if(bis!=null){
						bis.close();
					}
					if(is!=null){
						is.close();
					}
				} catch (Exception e) {
					//异常不处理
				}
			}

		} catch (Exception e) {
			CommonQueryBS.systemOut(result);
			e.printStackTrace();
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
		}
	}

	/**
	 * 写入一个Excel文件
	 * @param os
	 * @param listContent
	 * @param passwd
	 * @throws IOException
	 * @throws WriteException
	 * @throws AppException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void writeWorkBook(WritableWorkbook workbook,List<List<Object>> listContent,String passwd) throws IOException, WriteException, AppException, IllegalArgumentException, IllegalAccessException{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		
		/** **********1.创建工作簿************ */
//		WritableWorkbook workbook = Workbook.createWorkbook(os);
		if(workbook==null){
			throw new AppException("传入Excel工作簿对象为空！");
		}
		workbook.setProtected(true);

		/** ************2.设置单元格字体************** */
		WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
		WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD);

		/** ************3.以下设置三种单元格样式，灵活备用************ */
		// 用于标题居中
		WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
		wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
		wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
		wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
		wcf_center.setWrap(false); // 文字是否换行

		// 用于正文居左
		WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
		wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
		wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
		wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
		wcf_left.setWrap(false); // 文字是否换行

		// 创建第一项工作表Info,存放其他信息表信息
		WritableSheet sheetInfo = workbook.createSheet("Info", 0);// parm1:工作表名称;parm2:工作表在Excel的位置从0开始
		jxl.SheetSettings sheetsetInfo = sheetInfo.getSettings();
		sheetsetInfo.setProtected(true);// 设定保护状态
		if (!StringUtil.isEmpty(passwd)) {
			sheetsetInfo.setPassword(passwd);// 设定密码
		}

		for (int i = 0; listContent != null && i < listContent.size(); i++) {
			// 设定sheet的名称为实体名（非全限定名），否则根据序号排名 【Sheet+序号】
			Object sheetMappingObj = listContent.get(i).get(0);
			String sheetName = "";
			if (sheetMappingObj != null) {
				sheetName = sheetMappingObj.getClass().getSimpleName();// 简单名
				// sheetName = sheetMappingObj.getClass().getName();//全限定名
				// 第一项工作表Info:第一行开始，每行依次为sheet对应实体的全限定名
				sheetInfo.addCell(new Label(0, i, sheetMappingObj
						.getClass().getName(), wcf_left));
			}

			/** **********4.创建工作表************ */
			// 每个List<Object>创建一个工作表
			WritableSheet sheet = workbook.createSheet(StringUtil
					.isEmpty(sheetName) ? "Sheet" + (i + 2) : sheetName,
					(i + 1));// parm1:工作表名称;parm2:工作表在Excel的位置从0开始
			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(true);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset.setPassword(passwd);// 设定密码
			}

			/** ***************5.以下是EXCEL开头大标题，暂时省略********************* */
			// sheet.mergeCells(0, 0, colWidth, 0);
			// sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
			/**
			 * ***************6.以下是EXCEL第一行列标题，暂时省略（可以通过传值 或 反射方法获取列名
			 * 设定）*********************
			 */
			// 注：要对应List中Object类型一致，不然列标题会对应不上
			Field[] titles = sheetMappingObj.getClass().getDeclaredFields();
			for (int index = 0; titles != null && index < titles.length; index++) {
				sheet.addCell(new Label(index, 0, titles[index].getName(),
						wcf_center));
			}

			/** ***************6.以下是EXCEL正文数据********************* */
			Field[] fields = null;
			int z = 1;// 行号,0行为标题行，没有标题则从0开始写数据
			for (Object obj : listContent.get(i)) {
				if (!sheetMappingObj.getClass().getName()
						.equals(obj.getClass().getName())) {
					throw new AppException(
							"导出Excel异常!异常信息：List<List<Object>> listContent 参数中List<Object>同一个List存放不是相同类！");
				}

				fields = obj.getClass().getDeclaredFields();
				int j = 0;// 列号
				for (Field v : fields) {
					v.setAccessible(true);
					Object va = v.get(obj);
					if (va == null) {
						va = "";
					}
					if ("Date".equals(v.getType().getSimpleName())) {
						va = sdf.format(va);
					}
					;
					sheet.addCell(new Label(j, z, va.toString(), wcf_left));
					j++;
				}
				z++;
			}
		}

		/** **********7.将以上缓存中的内容写到EXCEL文件中******** */
		workbook.write();
		/** *********8.关闭文件************* */
		workbook.close();
	}
	
	/**
	 * Excel导入方法
	 * 
	 * @author mengl
	 * @param item 上传的文件
	 * @return
	 */
	public final static List<List<Object>> importExcel(InputStream is) {
		List<List<Object>> list = new ArrayList<List<Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Workbook rwb = Workbook.getWorkbook(is);

			Sheet infoSheet = rwb.getSheet("Info");
			Map<String, String> entryMap = new HashMap<String, String>();
			for (int i = 0; i < infoSheet.getRows(); i++) {
				String content = infoSheet.getCell(0, i).getContents();
				
				System.out.println("content:"+content);
				
				entryMap.put(content.substring(content.lastIndexOf(".") + 1),
						content);
			}

			for (String sheetName : entryMap.keySet()) {
				List<Object> listObj = new ArrayList<Object>();
				// 1. 反射获取新实例
				
				Class classSheet = Class.forName(entryMap.get(sheetName));// 通过全限定名获取对象实体的Class对象
				Sheet st = rwb.getSheet(sheetName);
				System.out.println("Sheet:"+st.getName());
				int rs = st.getColumns();
				int rows = st.getRows();

				List<String> listField = new TreeList(); // 标题行，对应实体字段
				// 获取标题行，字段名
				for (int i = 0; i < rs; i++) {// 列
					Cell cell = st.getCell(i, 0);
					listField.add(cell.getContents());
				}
				for (int k = 1; k < rows; k++) {// 行
					Object obj = classSheet.newInstance();
					for (int i = 0; i < rs; i++) {// 列
						// 2. 获取属性
						String fieldName = listField.get(i);
						Field field = classSheet.getDeclaredField(fieldName);
		
						// 3. 设定属性可以访问（即使private修饰）
						field.setAccessible(true);

						Cell c00 = st.getCell(i, k);
						// 通用的获取cell值的方式,返回字符串
						String strc00 = c00.getContents();
						
						// 4. 获取实体字段所属类型
						Class typeClass = field.getType();
						
						// 5. 获取该类型的String参数的构造方法
						Constructor con = typeClass.getConstructor(String.class);

						// 6. 获取属性对象值
						Object fieldObj = null;
						if (!StringUtil.isEmpty(strc00)) {
							if (field
									.getGenericType()
									.toString()
									.substring(
											field.getGenericType().toString()
													.lastIndexOf(".") + 1)
									.equals("Date")) {
								// Date类型需要解析
								fieldObj = sdf.parse(strc00);
							} else {
								// 通过获取对应类型的String类型构造方法生成对应类型的属性值
								fieldObj = con.newInstance(strc00);
							}
						}
						// 7. 为反射得到的实例赋值
						field.set(obj, fieldObj);

						// 获得cell具体类型值的方式
						// if (c00.getType() == CellType.LABEL) {
						// LabelCell labelc00 = (LabelCell) c00;
						// strc00 = labelc00.getString();
						// }
						// excel 类型为时间类型处理;
						// if (c00.getType() == CellType.DATE) {
						// DateCell dc = (DateCell) c00;
						// strc00 = sdf.format(dc.getDate());
						// }

					}
					listObj.add(obj);
				}
				list.add(listObj);
			}

			// 关闭
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
	

}