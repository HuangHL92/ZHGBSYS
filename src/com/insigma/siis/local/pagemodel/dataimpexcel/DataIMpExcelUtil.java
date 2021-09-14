package com.insigma.siis.local.pagemodel.dataimpexcel;

import java.io.FileOutputStream;
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
import java.util.Random;
import java.util.UUID;

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

import org.apache.commons.collections.list.TreeList;
import org.apache.commons.fileupload.FileItem;
import org.hibernate.Hibernate;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.util.DateUtil;
import com.insigma.siis.local.business.datavaerify.DataOIDTO;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A01temp;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A02temp;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A06temp;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A08temp;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A11temp;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A14temp;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A15temp;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A29temp;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A30temp;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A31temp;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A36temp;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A37temp;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A41temp;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A53temp;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.B01temp;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

/**
 * Excel导出工具类
 * 应用API地址：http://jexcelapi.sourceforge.net/resources/javadocs/2_6_10/docs/
 * 
 * @author mengl
 * 
 */
public class DataIMpExcelUtil {

	@SuppressWarnings("unused")
	private static String pattern = "yyyyMMdd HH:mm:ss"; // 统一导出导入日期解析格式

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
	 *            保护密码
	 * @return
	 */

	public final static String exportExcel(HttpServletResponse response,
			List<List<Object>> listContent, String fileName, String passwd) {
		String result = "系统提示：Excel文件导出成功！";
		// 处理文件名
		if (StringUtil.isEmpty(fileName)) {
			fileName = "导出文件";
		}
		fileName = fileName.replace(".xls", "") + "_"
				+ DateUtil.toString(new Date(), "yyyyMMddHHmmss") + ".xls";

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		try {
			// 定义输出流，以便打开保存对话框______________________begin
			// HttpServletResponse response=ServletActionContext.getResponse();
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			// 定义输出流，以便打开保存对话框_______________________end

			/** **********1.创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			workbook.setProtected(false);

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
			sheetsetInfo.setProtected(false);// 设定保护状态
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
				sheetset.setProtected(false);// 设定保护状态
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

		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			CommonQueryBS.systemOut(result);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Excel导入方法
	 * 
	 * @author mengl
	 * @param item 上传的文件
	 * @return
	 */
	public final static List<List> importExcel(FileItem item) {
		List<List> list = new ArrayList<List>();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Map a01Map = new HashMap();
			Map b01Map = new HashMap();
			Map a11Map = new HashMap();
			InputStream is = item.getInputStream();
			Workbook rwb = Workbook.getWorkbook(is);
			//人员基本信息
			Sheet a01Sheet = rwb.getSheet("人员基本信息");
			List a01s = new ArrayList();
			for (int i = 0; i < a01Sheet.getRows(); i++) {
				A01temp a01t = new A01temp();
				String uuid = UUID.randomUUID().toString();
				a01Map.put(a01Sheet.getCell(0, i).getContents()+a01Sheet.getCell(1, i).getContents()
						+a01Sheet.getCell(2, i).getContents()+a01Sheet.getCell(3, i).getContents(), uuid);
				a01t.setA0000(uuid);
				a01t.setA0101(a01Sheet.getCell(0, i).getContents());//姓名
				a01t.setA0104(getCode(a01Sheet.getCell(1, i).getContents(),"GB2261", ""));//性别代码
				a01t.setA0104a(a01Sheet.getCell(1, i).getContents());//性别代码
				a01t.setA0107(a01Sheet.getCell(2, i).getContents());//出生年月
				
				a01t.setA0184(a01Sheet.getCell(3, i).getContents());//身份证号
				a01t.setA0111(getCode(a01Sheet.getCell(4, i).getContents(),"ZB01", ""));//籍贯号
				a01t.setA0111a(a01Sheet.getCell(4, i).getContents());//籍贯
				a01t.setA0114(getCode(a01Sheet.getCell(5, i).getContents(),"ZB01", ""));//出生地
				a01t.setA0114a(a01Sheet.getCell(5, i).getContents());//出生地
				a01t.setA0117(getCode(a01Sheet.getCell(6, i).getContents(),"GB3304", ""));//民族
				a01t.setA0117a(a01Sheet.getCell(6, i).getContents());//民族
				
				a01t.setA0144(a01Sheet.getCell(7, i).getContents());//入党时间
				a01t.setA0141(getCode(a01Sheet.getCell(8, i).getContents(),"GB4762", ""));//政治面貌
				a01t.setA3921(getCode(a01Sheet.getCell(9, i).getContents(),"GB4762", ""));//第二党派
				a01t.setA3927(getCode(a01Sheet.getCell(10, i).getContents(),"GB4762", ""));//第三党派
				
				a01t.setA0134(a01Sheet.getCell(11, i).getContents());//参加工作时间
				a01t.setA0160(getCode(a01Sheet.getCell(12, i).getContents(),"GB3304", ""));//人员类别
				a01t.setA0187a(a01Sheet.getCell(13, i).getContents());//熟悉专业有何专长
				a01t.setA0128(getCode(a01Sheet.getCell(14, i).getContents(),"GB2261C", ""));//健康状况
				a01t.setA0128b(a01Sheet.getCell(14, i).getContents());//健康状况
				a01t.setA0163(getCode(a01Sheet.getCell(15, i).getContents(),"ZB126", ""));//人员状态
				a01t.setA0165(getCode(a01Sheet.getCell(16, i).getContents(),"ZB130", ""));//管理类别

				a01t.setA0194((a01Sheet.getCell(17, i).getContents()!=null && 
						!a01Sheet.getCell(17, i).getContents().equals(""))?Long.parseLong(a01Sheet.getCell(17, i).getContents()):null );//基层工作经历年限
				a01t.setA1701(a01Sheet.getCell(18, i).getContents());//管理类别
				dealA01(a01t);//处理党派 处理年龄
				a01s.add(a01t);
			}
			
			//机构信息
			Sheet b01Sheet = rwb.getSheet("机构信息");
			List b01s = new ArrayList();
			for (int i = 0; i < b01Sheet.getRows(); i++) {
				B01temp b01t = new B01temp();
				b01t.setB0101(b01Sheet.getCell(0, i).getContents());//机构名称
				b01t.setB0104(b01Sheet.getCell(1, i).getContents());//机构简称
				b01t.setB0111(b01Sheet.getCell(2, i).getContents());//机构重名识别码 ？？？
//				b01t.setA0184(b01Sheet.getCell(3, i).getContents());//上级机构名称
				b01t.setB0121(b01Sheet.getCell(4, i).getContents());//上级机构重名识别码 ？？？
				
				b01t.setB0131(getCode(b01Sheet.getCell(5, i).getContents(),"ZB04", ""));//机构lei别
				b01t.setB0127(getCode(b01Sheet.getCell(6, i).getContents(),"ZB03", ""));//机构级别
				b01t.setB0117(getCode(b01Sheet.getCell(7, i).getContents(),"ZB01", ""));//机构所在政区
				b01t.setB0194(getCode(b01Sheet.getCell(8, i).getContents(),"XZ20", ""));//法人单位标识
				b01t.setB0114(b01Sheet.getCell(9, i).getContents());//单位(分组)编码

				b01t.setB0124(getCode(b01Sheet.getCell(10, i).getContents(),"ZB87", ""));//隶属关系
				String b0183 = b01Sheet.getCell(11, i).getContents();
				b01t.setB0183(b0183!=null&&!b0183.equals("")?Long.parseLong(b0183):null);//正职领导职数
				String b0185 = b01Sheet.getCell(12, i).getContents();
				b01t.setB0185(b0185!=null&&!b0185.equals("")?Long.parseLong(b0185):null);//副职领导职数
				String B0188 = b01Sheet.getCell(13, i).getContents();
				b01t.setB0188(B0188!=null&&!B0188.equals("")?Long.parseLong(B0188):null);//正职非领导职数
				String B0189 = b01Sheet.getCell(14, i).getContents();
				b01t.setB0189(B0189!=null&&!B0189.equals("")?Long.parseLong(B0189):null);//副职非领导职数
				
				String B0150 = b01Sheet.getCell(15, i).getContents();
				b01t.setB0150(B0150!=null&&!B0150.equals("")?Long.parseLong(B0150):null);//内设领导职数
				String B0190 = b01Sheet.getCell(16, i).getContents();
				b01t.setB0190(B0190!=null&&!B0190.equals("")?Long.parseLong(B0190):null);//内设正职领导职数
				String B0191A = b01Sheet.getCell(17, i).getContents();
				b01t.setB0191a(B0191A!=null&&!B0191A.equals("")?Long.parseLong(B0191A):null);//内设副职领导职数
				String B0192 = b01Sheet.getCell(18, i).getContents();
				b01t.setB0192(B0192!=null&&!B0192.equals("")?Long.parseLong(B0192):null);//内设正职非领导职数
				String B0193 = b01Sheet.getCell(19, i).getContents();
				b01t.setB0193(B0193!=null&&!B0193.equals("")?Long.parseLong(B0193):null);//内设副职非领导职数
				
				String B0227 = b01Sheet.getCell(20, i).getContents();
				b01t.setB0227(B0227!=null&&!B0227.equals("")?Long.parseLong(B0227):null);//行政编制数
				String B0232 = b01Sheet.getCell(21, i).getContents();
				b01t.setB0232(B0232!=null&&!B0232.equals("")?Long.parseLong(B0232):null);//参照公务员法管理事业编制数
				String B0233 = b01Sheet.getCell(22, i).getContents();
				b01t.setB0233(B0233!=null&&!B0233.equals("")?Long.parseLong(B0233):null);//其他事业编制数
				
				b01s.add(b01t);
			}
			
			//职务信息
			Sheet A02Sheet = rwb.getSheet("职务信息");
			List a02s = new ArrayList();
			for (int i = 0; i < A02Sheet.getRows(); i++) {
				A02temp a02t = new A02temp();
				String key = A02Sheet.getCell(0, i).getContents() + A02Sheet.getCell(1, i).getContents() +
					A02Sheet.getCell(2, i).getContents() + A02Sheet.getCell(3, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a02t.setA0000(a01Map.get(key).toString());
				}
				a02t.setA0201a(A02Sheet.getCell(4, i).getContents());//机构名称
				
				a02t.setA0201b(A02Sheet.getCell(5, i).getContents());//机构重名识别码 ？？？
				a02t.setA0215a(getCode(A02Sheet.getCell(6, i).getContents(),"ZB08", ""));//职务名称 ？？？ A0215b
				a02t.setA0219(getCode(A02Sheet.getCell(7, i).getContents(),"ZB42", ""));//职务类别
				a02t.setA0221(getCode(A02Sheet.getCell(8, i).getContents(),"ZB09", ""));//职务层次
				a02t.setA0243(A02Sheet.getCell(9, i).getContents());//任职时间
				
				a02t.setA0247(getCode(A02Sheet.getCell(10, i).getContents(),"ZB122", ""));//选拔任用方式
				a02t.setA0251(getCode(A02Sheet.getCell(11, i).getContents(),"ZB13", ""));//职动类型
				a02t.setA0295(A02Sheet.getCell(12, i).getContents());//最高职务 ？？？？
				a02t.setA0255(getCode(A02Sheet.getCell(13, i).getContents(),"ZB14", ""));//任职状态
				a02t.setA0281(A02Sheet.getCell(14, i).getContents());//输出到任免表   1  ？？？
				
				a02t.setA0245(A02Sheet.getCell(15, i).getContents());//任职文号
				a02t.setA0288(A02Sheet.getCell(16, i).getContents());//任职务层次时间
				a02t.setA0201e(getCode(A02Sheet.getCell(17, i).getContents(),"ZB129", ""));//成员类别
				a02t.setA0201d(getCode(A02Sheet.getCell(18, i).getContents(),"XZ09", ""));//是否班子成员
				a02t.setA0229(A02Sheet.getCell(19, i).getContents());//主管工作   ?????   分管（从事）工作(该人分管工作的范围或从事工作的领域及岗位名称)
				
				a02t.setA0271(getCode(A02Sheet.getCell(20, i).getContents(),"ZB16", ""));//免职类型
				a02t.setA0265(A02Sheet.getCell(21, i).getContents());//免职时间
				a02t.setA0267(A02Sheet.getCell(22, i).getContents());//免职文号
				a02t.setA0284(getCode(A02Sheet.getCell(23, i).getContents(),"XZ09", ""));//是否交流
				a02t.setA4904(getCode(A02Sheet.getCell(24, i).getContents(),"ZB73", ""));//交流原因
				
				a02t.setA4901(getCode(A02Sheet.getCell(25, i).getContents(),"ZB72", ""));//交流方式
				a02t.setA4907(getCode(A02Sheet.getCell(26, i).getContents(),"ZB74", ""));//交流去向
				a02t.setA0259(getCode(A02Sheet.getCell(27, i).getContents(),"", ""));//破格提拔 ??? 
				String NO = A02Sheet.getCell(28, i).getContents();
				a02t.setA0225(NO!=null&&!NO.equals("")?Long.parseLong(NO):null);//集体内排序号
				
				a02s.add(a02t);
			}
			//学历学位信息
			Sheet A08Sheet = rwb.getSheet("学历学位信息");
			List a08s = new ArrayList();
			for (int i = 0; i < A08Sheet.getRows(); i++) {
				A08temp a08t = new A08temp();
				String key = A08Sheet.getCell(0, i).getContents() + A08Sheet.getCell(1, i).getContents() +
					A08Sheet.getCell(2, i).getContents() + A08Sheet.getCell(3, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a08t.setA0000(a01Map.get(key).toString());
				}
				a08t.setA0801a(A08Sheet.getCell(4, i).getContents());//学历
				
				a08t.setA0901a(A08Sheet.getCell(5, i).getContents());//学位？
				a08t.setA0824(A08Sheet.getCell(6, i).getContents());//专业
				a08t.setA0904(A08Sheet.getCell(7, i).getContents());//授予时间
				a08t.setA0804(A08Sheet.getCell(8, i).getContents());//入学时间
				a08t.setA0807(A08Sheet.getCell(9, i).getContents());//毕业时间
				
				a08t.setA0814(A08Sheet.getCell(10, i).getContents());//学校及院系
				a08t.setA0837(getCode(A08Sheet.getCell(11, i).getContents(),"ZB123", ""));//教育类别
				a08t.setA0811(A08Sheet.getCell(12, i).getContents());//学制年限
				a08s.add(a08t);
				
			}
			//离退信息
			Sheet A31Sheet = rwb.getSheet("离退信息");
			List a31s = new ArrayList();
			for (int i = 0; i < A31Sheet.getRows(); i++) {
				A31temp a31t = new A31temp();
				String key = A31Sheet.getCell(0, i).getContents() + A31Sheet.getCell(1, i).getContents() +
					A31Sheet.getCell(2, i).getContents() + A31Sheet.getCell(3, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a31t.setA0000(a01Map.get(key).toString());
				}
				a31t.setA3101(getCode(A31Sheet.getCell(4, i).getContents(),"ZB123", ""));//离退类别
				
				a31t.setA3104(A31Sheet.getCell(5, i).getContents());//离退批准时间
				a31t.setA3107(getCode(A31Sheet.getCell(6, i).getContents(),"ZB09", ""));//离退前级别
				a31t.setA3117a(A31Sheet.getCell(7, i).getContents());//离退后管理单位
				a31t.setA3118(A31Sheet.getCell(8, i).getContents());//曾任最高职务
				a31t.setA3137(A31Sheet.getCell(9, i).getContents());//离退批准文号
				a31s.add(a31t);
			}
			//退出管理信息
			Sheet A30Sheet = rwb.getSheet("退出管理信息");
			List a30s = new ArrayList();
			for (int i = 0; i < A30Sheet.getRows(); i++) {
				A30temp a30t = new A30temp();
				String key = A30Sheet.getCell(0, i).getContents() + A30Sheet.getCell(1, i).getContents() +
					A30Sheet.getCell(2, i).getContents() + A30Sheet.getCell(3, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a30t.setA0000(a01Map.get(key).toString());
				}
				a30t.setA3001(getCode(A30Sheet.getCell(4, i).getContents(),"ZB87", ""));//退出管理方式
				
				a30t.setA3004(A30Sheet.getCell(5, i).getContents());//退出日期
				a30t.setA3007a(A30Sheet.getCell(6, i).getContents());//调往单位
				a30t.setA3034(A30Sheet.getCell(7, i).getContents());//备注
				a30s.add(a30t);
			}
			//进入管理信息
			Sheet A29Sheet = rwb.getSheet("进入管理信息");
			List a29s = new ArrayList();
			for (int i = 0; i < A29Sheet.getRows(); i++) {
				A29temp a29t = new A29temp();
				String key = A29Sheet.getCell(0, i).getContents() + A29Sheet.getCell(1, i).getContents() +
					A29Sheet.getCell(2, i).getContents() + A29Sheet.getCell(3, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a29t.setA0000(a01Map.get(key).toString());
				}
				a29t.setA2911(getCode(A29Sheet.getCell(4, i).getContents(),"ZB77", ""));//进入本单位方式
				
				a29t.setA2907(A29Sheet.getCell(5, i).getContents());//进入日期
				a29t.setA2921a(A29Sheet.getCell(6, i).getContents());//原单位名称
				a29t.setA2941(A29Sheet.getCell(7, i).getContents());//原单位职务
				a29t.setA2944(A29Sheet.getCell(8, i).getContents());//原单位级别
				a29t.setA2947(A29Sheet.getCell(9, i).getContents());//进入公务员队伍时间
				a29t.setA2949(A29Sheet.getCell(10, i).getContents());//公务员登记时间
				a29s.add(a29t);
			}
			//考核信息
			Sheet A15Sheet = rwb.getSheet("考核信息");
			List a15s = new ArrayList();
			for (int i = 0; i < A15Sheet.getRows(); i++) {
				A15temp a15t = new A15temp();
				String key = A15Sheet.getCell(0, i).getContents() + A15Sheet.getCell(1, i).getContents() +
					A15Sheet.getCell(2, i).getContents() + A15Sheet.getCell(3, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a15t.setA0000(a01Map.get(key).toString());
				}
				a15t.setA1521(A15Sheet.getCell(4, i).getContents());//考核年度
				
				a15t.setA1517(getCode(A15Sheet.getCell(5, i).getContents(),"ZB18", ""));//考核结果
				a15s.add(a15t);
			}
			//奖惩信息
			Sheet A14Sheet = rwb.getSheet("奖惩信息");
			List a14s = new ArrayList();
			for (int i = 0; i < A14Sheet.getRows(); i++) {
				A14temp a14t = new A14temp();
				String key = A14Sheet.getCell(0, i).getContents() + A14Sheet.getCell(1, i).getContents() +
					A14Sheet.getCell(2, i).getContents() + A14Sheet.getCell(3, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a14t.setA0000(a01Map.get(key).toString());
				}
				a14t.setA1404b(A14Sheet.getCell(4, i).getContents());//进入本单位方式
				
				a14t.setA1404a(A14Sheet.getCell(5, i).getContents());//进入日期
				a14t.setA1407(A14Sheet.getCell(6, i).getContents());//原单位名称
				a14t.setA1414(getCode(A14Sheet.getCell(7, i).getContents(),"ZB18", ""));//原单位职务
				a14t.setA1411a(A14Sheet.getCell(8, i).getContents());//原单位级别
				a14t.setA1424(A14Sheet.getCell(9, i).getContents());//进入公务员队伍时间
				a14t.setA1428(getCode(A14Sheet.getCell(10, i).getContents(),"ZB128", ""));//公务员登记时间
				a14t.setA1415(getCode(A14Sheet.getCell(11, i).getContents(),"ZB09", ""));//公务员登记时间
				a14s.add(a14t);
			}
			//住址通讯信息
			Sheet A37Sheet = rwb.getSheet("住址通讯信息");
			List a37s = new ArrayList();
			for (int i = 0; i < A37Sheet.getRows(); i++) {
				A37temp a37t = new A37temp();
				String key = A37Sheet.getCell(0, i).getContents() + A37Sheet.getCell(1, i).getContents() +
					A37Sheet.getCell(2, i).getContents() + A37Sheet.getCell(3, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a37t.setA0000(a01Map.get(key).toString());
				}
				a37t.setA3701(A37Sheet.getCell(4, i).getContents());//办公地址
				
				a37t.setA3711(A37Sheet.getCell(5, i).getContents());//家庭住址
				a37t.setA3707c(A37Sheet.getCell(6, i).getContents());//移动电话
				a37t.setA3707a(A37Sheet.getCell(7, i).getContents());//办公电话
				a37t.setA3707e(A37Sheet.getCell(8, i).getContents());//秘书电话
				a37t.setA3707b(A37Sheet.getCell(9, i).getContents());//住宅电话
				a37t.setA3708(A37Sheet.getCell(10, i).getContents());//电子邮箱
				a37t.setA3714(A37Sheet.getCell(11, i).getContents());//住址邮编
				a37s.add(a37t);
			}
			//家庭成员信息
			Sheet A36Sheet = rwb.getSheet("家庭成员信息");
			List a36s = new ArrayList();
			for (int i = 0; i < A36Sheet.getRows(); i++) {
				A36temp a36t = new A36temp();
				String key = A36Sheet.getCell(0, i).getContents() + A36Sheet.getCell(1, i).getContents() +
					A36Sheet.getCell(2, i).getContents() + A36Sheet.getCell(3, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a36t.setA0000(a01Map.get(key).toString());
				}
				a36t.setA3604a(A36Sheet.getCell(4, i).getContents());//称谓
				
				a36t.setA3601(A36Sheet.getCell(5, i).getContents());//家庭成员姓名
				a36t.setA3607(A36Sheet.getCell(6, i).getContents());//出生日期
				a36t.setA3627(getCode(A36Sheet.getCell(7, i).getContents(),"ZB18", ""));//政治面貌
				a36t.setA3611(A36Sheet.getCell(8, i).getContents());//工作单位及职务
				a36s.add(a36t);
			}
			
			//培训人员信息
			Sheet A41Sheet = rwb.getSheet("培训人员信息");
			List a41s = new ArrayList();
			for (int i = 0; i < A41Sheet.getRows(); i++) {
				A41temp a41t = new A41temp();
				String key = A41Sheet.getCell(0, i).getContents() + A41Sheet.getCell(1, i).getContents() +
				A41Sheet.getCell(2, i).getContents() + A41Sheet.getCell(3, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a41t.setA0000(a01Map.get(key).toString());
				}
				a41t.setA1100(A41Sheet.getCell(5, i).getContents());//培训重名识别码
				a11Map.put(A41Sheet.getCell(5, i).getContents(), key);
			}
			//专业技术信息
			Sheet A06Sheet = rwb.getSheet("专业技术信息");
			List a06s = new ArrayList();
			for (int i = 0; i < A06Sheet.getRows(); i++) {
				A06temp a06t = new A06temp();
				String key = A06Sheet.getCell(0, i).getContents() + A06Sheet.getCell(1, i).getContents() +
				A06Sheet.getCell(2, i).getContents() + A06Sheet.getCell(3, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a06t.setA0000(a01Map.get(key).toString());
				}
				a06t.setA0602(A06Sheet.getCell(4, i).getContents());//专业技术资格
				
				a06t.setA0604(A06Sheet.getCell(5, i).getContents());//获取资格日期
				a06t.setA0607(getCode(A06Sheet.getCell(6, i).getContents(),"ZB24", ""));//取得资格途径
				a06t.setA0611(A06Sheet.getCell(7, i).getContents());//评委会或考试名称
				a06s.add(a06t);
			}
			
			//任免信息
			Sheet A53Sheet = rwb.getSheet("任免信息");
			List a53s = new ArrayList();
			for (int i = 0; i < A53Sheet.getRows(); i++) {
				A53temp a53t = new A53temp();
				String key = A53Sheet.getCell(0, i).getContents() + A53Sheet.getCell(1, i).getContents() +
				A53Sheet.getCell(2, i).getContents() + A53Sheet.getCell(3, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a53t.setA0000(a01Map.get(key).toString());
				}
				a53t.setA5304(A53Sheet.getCell(4, i).getContents());//拟任职务
				
				a53t.setA5315(A53Sheet.getCell(5, i).getContents());//拟免职务
				a53t.setA5317(A53Sheet.getCell(6, i).getContents());//任免理由
				a53t.setA5319(A53Sheet.getCell(7, i).getContents());//呈报单位
				a53t.setA5321(A53Sheet.getCell(8, i).getContents());//时间
				a53s.add(a53t);
			}
			
			//培训班信息
			Sheet A11Sheet = rwb.getSheet("培训班信息");
			List a11s = new ArrayList();
			for (int i = 0; i < A11Sheet.getRows(); i++) {
				A11temp a11t = new A11temp();
				String key = A11Sheet.getCell(1, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a11t.setA0000(a01Map.get(key).toString());
				}
				a11t.setA1131(A11Sheet.getCell(0, i).getContents());//培训名称
				a11t.setA1100(A11Sheet.getCell(1, i).getContents());//培训重名识别码
				a11t.setA1101(getCode(A11Sheet.getCell(2, i).getContents(),"ZB29", ""));//培训类别
				a11t.setA1107(A11Sheet.getCell(3, i).getContents());//开始时间
				a11t.setA1111(A11Sheet.getCell(4, i).getContents());//结束时间
				
				a11t.setA1127(getCode(A11Sheet.getCell(5, i).getContents(),"ZB27", ""));//培训机构类别
				a11t.setA1104(getCode(A11Sheet.getCell(6, i).getContents(),"ZB30", ""));//培训离岗状态
				a11t.setA1114(A11Sheet.getCell(7, i).getContents());//主办单位
				a11t.setA1121a(A11Sheet.getCell(8, i).getContents());//培训机构名称
				String a1151 = A11Sheet.getCell(9, i).getContents();
				a11t.setA1151((a1151!=null && !a1151.equals(""))?(a1151.equals("是")?"1":"0"):"");//出国（出境）培训标识
				a11s.add(a11t);
			}
			//数据信息
			Sheet ImpSheet = rwb.getSheet("数据信息");
			List imps = new ArrayList();
			Map impt = new HashMap();
			impt.put("b0101", ImpSheet.getCell(1, 0).getContents());
			impt.put("b0111", ImpSheet.getCell(1, 1).getContents());
			impt.put("b0114", ImpSheet.getCell(1, 2).getContents());
			impt.put("linkpsn", ImpSheet.getCell(1, 3).getContents());
			impt.put("linktel", ImpSheet.getCell(1, 4).getContents());
			impt.put("remark", ImpSheet.getCell(1, 5).getContents());
			impt.put("no", ImpSheet.getCell(1, 6).getContents());
			impt.put("count", ImpSheet.getCell(1, 7).getContents());
			imps.add(impt);
			
			list.add(a01s);
			list.add(a02s);
			list.add(a06s);
			list.add(a08s);
			list.add(a11s);
			list.add(a14s);
			list.add(a15s);
			list.add(a29s);
			list.add(a30s);
			list.add(a31s);
			list.add(a36s);
			list.add(a37s);
			list.add(a41s);
			list.add(a53s);
			list.add(b01s);
			list.add(imps);
			// 关闭
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	/**
	 * 预留补全基本
	 * @param a01t
	 */
	private static void dealA01(A01temp a01t) {
		
		
	}

	/**
	 * 
	 * @param codename 代码内容
	 * @param codetype 代码类别
	 * @param no 代码区第几个值,分别为 "", "2" ,"3"
	 * @return
	 */
	private static String getCode(String codename, String codetype, String no) {
		Object obj = HBUtil.getHBSession().createSQLQuery("select code_value from code_value t where t.code_type ='" +
				codetype + "' and code_name" + no + "='" + codename + "'").uniqueResult();
		return obj!=null ? obj.toString() : "";
	}
	
	/**
	 * 
	 * @param codename 代码内容
	 * @param codetype 代码类别
	 * @param no 代码区第几个值,分别为 "", "2" ,"3"
	 * @return
	 */
	private static String getName(String codevalue, String codetype, String no) {
		Object obj = HBUtil.getHBSession().createSQLQuery("select code_name" + no + " from code_value t where t.code_type ='" +
				codetype + "' and code_value='" + codevalue + "'").uniqueResult();
		return obj!=null ? obj.toString() : "";
	}

	public static void exportExcel(DataOIDTO dto, List<List> dataList_a, B01 b01, int no, String fileName,
			String passwd) {
		String result = "系统提示：Excel文件导出成功！";
		// 处理文件名
		if (StringUtil.isEmpty(fileName)) {
			fileName = "导出文件";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Map a01Map = new HashMap();
		Map b01Map = new HashMap();
		Map a11Map = new HashMap();
		try {
			// 定义输出流，以便打开保存对话框______________________begin
			// HttpServletResponse response=ServletActionContext.getResponse();
			OutputStream os = new FileOutputStream(fileName);// 取得输出流
			/*response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			// 定义输出流，以便打开保存对话框_______________________end
*/
			/** **********1.创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			workbook.setProtected(false);

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
			WritableSheet sheetInfo = workbook.createSheet("数据信息", 0);// parm1:工作表名称;parm2:工作表在Excel的位置从0开始
			jxl.SheetSettings sheetsetInfo = sheetInfo.getSettings();
			sheetsetInfo.setProtected(true);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetsetInfo.setPassword(passwd);// 设定密码
			}
			if(b01 != null){
				sheetInfo.addCell(new Label(0, 0, "机构名称", wcf_center));
				sheetInfo.addCell(new Label(0, 1, "机构重名识别码", wcf_center));
				sheetInfo.addCell(new Label(0, 2, "单位(分组)编码", wcf_center));
				sheetInfo.addCell(new Label(0, 3, "联系人", wcf_center));
				sheetInfo.addCell(new Label(0, 4, "联系电话", wcf_center));
				sheetInfo.addCell(new Label(0, 5, "备注", wcf_center));
				sheetInfo.addCell(new Label(0, 6, "文件", wcf_center));
				sheetInfo.addCell(new Label(0, 7, "文件总数", wcf_center));
				
				sheetInfo.addCell(new Label(1, 0, b01.getB0101(), wcf_left));
				sheetInfo.addCell(new Label(1, 1, b01.getB0111(), wcf_left));
				sheetInfo.addCell(new Label(1, 2, b01.getB0114(), wcf_left));
				sheetInfo.addCell(new Label(1, 3, dto.getLinkpsn(), wcf_left));
				sheetInfo.addCell(new Label(1, 4, dto.getLinktel(), wcf_left));
				sheetInfo.addCell(new Label(1, 5, dto.getRemark(), wcf_left));
				sheetInfo.addCell(new Label(1, 6, no+"", wcf_left));
				sheetInfo.addCell(new Label(1, 7, dto.getCount()+"", wcf_left));
			}
			// 创建人员基本信息
			WritableSheet sheet1= workbook.createSheet("人员基本信息", 1);// parm1:工作表名称;parm2:工作表在Excel的位置从0开始
			jxl.SheetSettings sheetset1= sheet1.getSettings();
			sheetset1.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset1.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet1.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet1.addCell(new Label(1, 0, "性别", wcf_center));
			sheet1.addCell(new Label(2, 0, "出生年月", wcf_center));
			sheet1.addCell(new Label(3, 0, "身份证号", wcf_center));
			sheet1.addCell(new Label(4, 0, "籍贯", wcf_center));
			sheet1.addCell(new Label(5, 0, "出生地", wcf_center));
			sheet1.addCell(new Label(6, 0, "民族", wcf_center));
			sheet1.addCell(new Label(7, 0, "入党时间", wcf_center));
			sheet1.addCell(new Label(8, 0, "政治面貌", wcf_center));
			sheet1.addCell(new Label(9, 0, "第二党派", wcf_center));
			sheet1.addCell(new Label(10, 0, "第三党派", wcf_center));
			sheet1.addCell(new Label(11, 0, "参加工作时间", wcf_center));
			sheet1.addCell(new Label(12, 0, "人员类别", wcf_center));
			sheet1.addCell(new Label(13, 0, "熟悉专业有何专长", wcf_center));
			sheet1.addCell(new Label(14, 0, "健康状况", wcf_center));
			sheet1.addCell(new Label(15, 0, "人员状态", wcf_center));
			sheet1.addCell(new Label(16, 0, "管理类别", wcf_center));
			sheet1.addCell(new Label(17, 0, "基层工作经历年限", wcf_center));
			sheet1.addCell(new Label(18, 0, "简历", wcf_center));
			//设置内容
			List list1 = dataList_a.get(0);
			for (int i = 1; list1!=null && list1.size() > 0 && i <= list1.size(); i++) {
				A01 a01 = (A01) list1.get(i-1);
				sheet1.addCell(new Label(0, i, a01.getA0101(), wcf_left));
				sheet1.addCell(new Label(1, i, getName(a01.getA0104(), "GB2261", ""), wcf_left));
				sheet1.addCell(new Label(2, i, a01.getA0107(), wcf_left));
				sheet1.addCell(new Label(3, i, a01.getA0184(), wcf_left));
				sheet1.addCell(new Label(4, i, a01.getComboxArea_a0111(), wcf_left));
				sheet1.addCell(new Label(5, i, a01.getComboxArea_a0114(), wcf_left));
				sheet1.addCell(new Label(6, i, getName(a01.getA0117(), "GB3304", ""), wcf_left));
				sheet1.addCell(new Label(7, i, a01.getA0114(), wcf_left));
				sheet1.addCell(new Label(8, i, getName(a01.getA0141(), "GB4762", ""), wcf_left));
				sheet1.addCell(new Label(9, i, getName(a01.getA3921(), "GB4762", ""), wcf_left));
				sheet1.addCell(new Label(10, i, getName(a01.getA3927(), "GB4762", ""), wcf_left));
				sheet1.addCell(new Label(11, i, a01.getA0134(), wcf_left));
				sheet1.addCell(new Label(12, i, getName(a01.getA0160(), "ZB125", ""), wcf_left));
				sheet1.addCell(new Label(13, i, a01.getA0187a(), wcf_left));
				sheet1.addCell(new Label(14, i, a01.getA0128(), wcf_left));
				sheet1.addCell(new Label(15, i, getName(a01.getA0163(), "ZB126", ""), wcf_left));
				sheet1.addCell(new Label(16, i, getName(a01.getA0165(), "ZB130", ""), wcf_left));
				sheet1.addCell(new Label(17, i, a01.getA0194() != null ? a01.getA0194().toString() : "" , wcf_left));
				sheet1.addCell(new Label(18, i, a01.getA1701(), wcf_left));
				// 人员id与 姓名 性别 出生日期 身份证号
				a01Map.put(a01.getA0000(), new String[]{a01.getA0101(), getName(a01.getA0104(), "GB2261", ""), a01.getA0107(), a01.getA0184()});
			}
			
			// 创建机构基本信息
			WritableSheet sheet2= workbook.createSheet("机构信息", 2);// parm1:工作表名称;parm2:工作表在Excel的位置从0开始
			jxl.SheetSettings sheetset2= sheet2.getSettings();
			sheetset2.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset2.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet2.addCell(new Label(0, 0, "机构名称", wcf_center));
			sheet2.addCell(new Label(1, 0, "机构简称", wcf_center));
			sheet2.addCell(new Label(2, 0, "机构重名识别码", wcf_center));
			sheet2.addCell(new Label(3, 0, "上级机构名称", wcf_center));
			sheet2.addCell(new Label(4, 0, "上级机构重名识别码", wcf_center));
			sheet2.addCell(new Label(5, 0, "机构类别", wcf_center));
			sheet2.addCell(new Label(6, 0, "机构级别", wcf_center));
			sheet2.addCell(new Label(7, 0, "机构所在政区", wcf_center));
			sheet2.addCell(new Label(8, 0, "法人单位标识", wcf_center));
			sheet2.addCell(new Label(9, 0, "单位(分组)编码", wcf_center));
			
			sheet2.addCell(new Label(10, 0, "隶属关系", wcf_center));
			sheet2.addCell(new Label(11, 0, "正职领导职数", wcf_center));
			sheet2.addCell(new Label(12, 0, "副职领导职数", wcf_center));
			sheet2.addCell(new Label(13, 0, "正职非领导职数", wcf_center));
			sheet2.addCell(new Label(14, 0, "副职非领导职数", wcf_center));
			sheet2.addCell(new Label(15, 0, "内设领导职数", wcf_center));
			sheet2.addCell(new Label(16, 0, "内设正职领导职数", wcf_center));
			sheet2.addCell(new Label(17, 0, "内设副职领导职数", wcf_center));
			sheet2.addCell(new Label(18, 0, "内设正职非领导职数", wcf_center));
			sheet2.addCell(new Label(19, 0, "内设副职非领导职数", wcf_center));
			
			sheet2.addCell(new Label(20, 0, "行政编制数", wcf_center));
			sheet2.addCell(new Label(21, 0, "参照公务员法管理事业编制数", wcf_center));
			sheet2.addCell(new Label(22, 0, "其他事业编制数", wcf_center));
			//设置内容
			List list2 = dataList_a.get(15);
			for (int i = 1; list2!=null && list2.size() > 0 && i <= list2.size(); i++) {
				B01 b01t = (B01) list2.get(i-1);
				sheet2.addCell(new Label(0, i, b01t.getB0101(), wcf_left));
				sheet2.addCell(new Label(1, i, b01t.getB0104(), wcf_left));
				sheet2.addCell(new Label(2, i, b01t.getB0111(), wcf_left));
				if(b01t.getB0121()!=null && !b01t.getB0121().equals("")){
					B01 par = (B01) HBUtil.getHBSession().get(B01.class, b01t.getB0121());
					if(par != null)
						sheet2.addCell(new Label(3, i, par.getB0101(), wcf_left));
				} else {
					sheet2.addCell(new Label(3, i, "", wcf_left));
				}
				sheet2.addCell(new Label(4, i, b01t.getB0121(), wcf_left));
				sheet2.addCell(new Label(5, i, getName(b01t.getB0131(), "ZB04", ""), wcf_left));
				sheet2.addCell(new Label(6, i, getName(b01t.getB0127(), "ZB03", ""), wcf_left));
				sheet2.addCell(new Label(7, i, getName(b01t.getB0117(), "ZB01", ""), wcf_left));
				sheet2.addCell(new Label(8, i, getName(b01t.getB0194(), "XZ20", ""), wcf_left));
				sheet2.addCell(new Label(9, i, b01t.getB0114(), wcf_left));
				sheet2.addCell(new Label(10, i, getName(b01t.getB0124(), "ZB87", ""), wcf_left));
				sheet2.addCell(new Label(11, i, b01t.getB0183()!=null ? b01t.getB0183().toString() : "", wcf_left));
				sheet2.addCell(new Label(12, i, b01t.getB0185()!=null ? b01t.getB0185().toString() : "", wcf_left));
				sheet2.addCell(new Label(13, i, b01t.getB0188()!=null ? b01t.getB0188().toString() : "", wcf_left));
				sheet2.addCell(new Label(14, i, b01t.getB0189()!=null ? b01t.getB0189().toString() : "", wcf_left));
				sheet2.addCell(new Label(15, i, b01t.getB0150()!=null ? b01t.getB0150().toString() : "", wcf_left));
				sheet2.addCell(new Label(16, i, b01t.getB0190()!=null ? b01t.getB0190().toString() : "", wcf_left));
				sheet2.addCell(new Label(17, i, b01t.getB0191a()!=null ? b01t.getB0191a().toString() : "", wcf_left));
				sheet2.addCell(new Label(18, i, b01t.getB0192()!=null ? b01t.getB0192().toString() : "", wcf_left));
				sheet2.addCell(new Label(19, i, b01t.getB0193()!=null ? b01t.getB0193().toString() : "", wcf_left));
				sheet2.addCell(new Label(20, i, b01t.getB0227()!=null ? b01t.getB0227().toString() : "", wcf_left));
				sheet2.addCell(new Label(21, i, b01t.getB0232()!=null ? b01t.getB0232().toString() : "", wcf_left));
				sheet2.addCell(new Label(22, i, b01t.getB0233()!=null ? b01t.getB0233().toString() : "", wcf_left));
				// 机构id与 名称 分组编码
				a01Map.put(b01t.getB0111(), new String[]{b01t.getB0101(), b01t.getB0114()});
			}
			
			// 创建职务信息
			WritableSheet sheet3= workbook.createSheet("职务信息", 3);// parm1:工作表名称;parm2:工作表在Excel的位置从0开始
			jxl.SheetSettings sheetset3= sheet3.getSettings();
			sheetset3.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset3.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet3.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet3.addCell(new Label(1, 0, "性别", wcf_center));
			sheet3.addCell(new Label(2, 0, "出生年月", wcf_center));
			sheet3.addCell(new Label(3, 0, "身份证号", wcf_center));
			sheet3.addCell(new Label(4, 0, "机构名称", wcf_center));
			sheet3.addCell(new Label(5, 0, "机构重名识别码", wcf_center));
			sheet3.addCell(new Label(6, 0, "职务名称", wcf_center));
			sheet3.addCell(new Label(7, 0, "职务类别", wcf_center));
			sheet3.addCell(new Label(8, 0, "职务层次", wcf_center));
			sheet3.addCell(new Label(9, 0, "任职时间", wcf_center));
			
			sheet3.addCell(new Label(10, 0, "选拔任用方式", wcf_center));
			sheet3.addCell(new Label(11, 0, "职动类型", wcf_center));
			sheet3.addCell(new Label(12, 0, "最高职务", wcf_center));
			sheet3.addCell(new Label(13, 0, "任职状态", wcf_center));
			sheet3.addCell(new Label(14, 0, "输出到任免表", wcf_center));
			sheet3.addCell(new Label(15, 0, "任职文号", wcf_center));
			sheet3.addCell(new Label(16, 0, "任职务层次时间", wcf_center));
			sheet3.addCell(new Label(17, 0, "成员类别", wcf_center));
			sheet3.addCell(new Label(18, 0, "是否班子成员", wcf_center));
			sheet3.addCell(new Label(19, 0, "主管工作", wcf_center));
			
			sheet3.addCell(new Label(20, 0, "免职类型", wcf_center));
			sheet3.addCell(new Label(21, 0, "免职时间", wcf_center));
			sheet3.addCell(new Label(22, 0, "免职文号", wcf_center));
			sheet3.addCell(new Label(23, 0, "是否交流", wcf_center));
			sheet3.addCell(new Label(24, 0, "交流原因", wcf_center));
			sheet3.addCell(new Label(25, 0, "交流方式", wcf_center));
			sheet3.addCell(new Label(26, 0, "交流去向", wcf_center));
			sheet3.addCell(new Label(27, 0, "破格提拔", wcf_center));
			sheet3.addCell(new Label(28, 0, "集体内排序号", wcf_center));
			//设置内容
			List list3 = dataList_a.get(1);
			for (int i = 1; list3!=null && list3.size() > 0 && i <= list3.size(); i++) {
				A02 a02 = (A02) list3.get(i-1);
				String[] a01arr = (String[]) a01Map.get(a02.getA0000());
				sheet3.addCell(new Label(0, i, a01arr[0], wcf_left));
				sheet3.addCell(new Label(1, i, a01arr[1], wcf_left));
				sheet3.addCell(new Label(2, i, a01arr[2], wcf_left));
				sheet3.addCell(new Label(3, i, a01arr[3], wcf_left));
				sheet3.addCell(new Label(4, i, a02.getA0201a(), wcf_left));
				sheet3.addCell(new Label(5, i, a02.getA0201b(), wcf_left));
				sheet3.addCell(new Label(6, i, getName(a02.getA0215a(), "ZB08", ""), wcf_left));
				sheet3.addCell(new Label(7, i, getName(a02.getA0219(), "ZB42", ""), wcf_left));
				sheet3.addCell(new Label(8, i, getName(a02.getA0221(), "ZB09", ""), wcf_left));
				sheet3.addCell(new Label(9, i, a02.getA0243(), wcf_left));
				sheet3.addCell(new Label(10, i, getName(a02.getA0247(), "ZB122", ""), wcf_left));
				sheet3.addCell(new Label(11, i, getName(a02.getA0251(), "ZB13", ""), wcf_left));
				sheet3.addCell(new Label(12, i, a02.getA0295(), wcf_left));
				sheet3.addCell(new Label(13, i, getName(a02.getA0255(), "ZB14", ""), wcf_left));
				sheet3.addCell(new Label(14, i, a02.getA0281()!=null ? (a02.getA0281().equals("true")? "1" : "") : "", wcf_left));
				sheet3.addCell(new Label(15, i, a02.getA0245(), wcf_left));
				sheet3.addCell(new Label(16, i, a02.getA0288(), wcf_left));
				sheet3.addCell(new Label(17, i, getName(a02.getA0201e(), "ZB129", ""), wcf_left));
				sheet3.addCell(new Label(18, i, getName(a02.getA0201d(), "XZ09", ""), wcf_left));
				sheet3.addCell(new Label(19, i, a02.getA0229(), wcf_left));
				sheet3.addCell(new Label(20, i, getName(a02.getA0271(), "ZB16", ""), wcf_left));
				sheet3.addCell(new Label(21, i, a02.getA0265(), wcf_left));
				sheet3.addCell(new Label(22, i, a02.getA0267(), wcf_left));
				sheet3.addCell(new Label(23, i, getName(a02.getA0284(), "XZ09", ""), wcf_center));
				sheet3.addCell(new Label(24, i, getName(a02.getA4904(), "ZB73", ""), wcf_center));
				sheet3.addCell(new Label(25, i, getName(a02.getA4901(), "ZB72", ""), wcf_center));
				sheet3.addCell(new Label(26, i, getName(a02.getA4907(), "ZB74", ""), wcf_center));
				sheet3.addCell(new Label(27, i, getName(a02.getA0251b(), "XZ09", ""), wcf_center));
				sheet3.addCell(new Label(28, i, a02.getA0225()!=null ? a02.getA0225() +"" :"", wcf_center));
			}
			
			// 创建学历学位信息
			WritableSheet sheet4= workbook.createSheet("学历学位信息", 4);
			jxl.SheetSettings sheetset4= sheet4.getSettings();
			sheetset4.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset4.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet4.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet4.addCell(new Label(1, 0, "性别", wcf_center));
			sheet4.addCell(new Label(2, 0, "出生年月", wcf_center));
			sheet4.addCell(new Label(3, 0, "身份证号", wcf_center));
			sheet4.addCell(new Label(4, 0, "学历", wcf_center));
			sheet4.addCell(new Label(5, 0, "学位", wcf_center));
			sheet4.addCell(new Label(6, 0, "专业", wcf_center));
			sheet4.addCell(new Label(7, 0, "授予时间", wcf_center));
			sheet4.addCell(new Label(8, 0, "入学时间", wcf_center));
			sheet4.addCell(new Label(9, 0, "毕业时间", wcf_center));
			sheet4.addCell(new Label(10, 0, "学校级院系", wcf_center));
			sheet4.addCell(new Label(11, 0, "教育类别", wcf_center));
			sheet4.addCell(new Label(12, 0, "学制年限", wcf_center));
			
			//设置内容
			List list4 = dataList_a.get(3);
			for (int i = 1; list4!=null && list4.size() > 0 && i <= list4.size(); i++) {
				A08 a08 = (A08) list4.get(i-1);
				String[] a01arr = (String[]) a01Map.get(a08.getA0000());
				sheet4.addCell(new Label(0, i, a01arr[0], wcf_left));
				sheet4.addCell(new Label(1, i, a01arr[1], wcf_left));
				sheet4.addCell(new Label(2, i, a01arr[2], wcf_left));
				sheet4.addCell(new Label(3, i, a01arr[3], wcf_left));
				sheet4.addCell(new Label(4, i, a08.getA0801a(), wcf_left));
				sheet4.addCell(new Label(5, i, a08.getA0901a(), wcf_left));
				sheet4.addCell(new Label(6, i, a08.getA0824(), wcf_left));
				sheet4.addCell(new Label(7, i, a08.getA0904(), wcf_left));
				sheet4.addCell(new Label(8, i, a08.getA0804(), wcf_left));
				sheet4.addCell(new Label(9, i, a08.getA0807(), wcf_left));
				sheet4.addCell(new Label(10, i, a08.getA0814(), wcf_left));
				sheet4.addCell(new Label(11, i, getName(a08.getA0837(), "ZB123", ""), wcf_left));
				sheet4.addCell(new Label(12, i, a08.getA0811(), wcf_left));
				
			}
			// 离退信息
			WritableSheet sheet5= workbook.createSheet("离退信息", 5);// parm1:工作表名称;parm2:工作表在Excel的位置从0开始
			jxl.SheetSettings sheetset5= sheet5.getSettings();
			sheetset5.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset5.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet5.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet5.addCell(new Label(1, 0, "性别", wcf_center));
			sheet5.addCell(new Label(2, 0, "出生年月", wcf_center));
			sheet5.addCell(new Label(3, 0, "身份证号", wcf_center));
			sheet5.addCell(new Label(4, 0, "离退类别", wcf_center));
			sheet5.addCell(new Label(5, 0, "离退批准时间", wcf_center));
			sheet5.addCell(new Label(6, 0, "离退前级别", wcf_center));
			sheet5.addCell(new Label(7, 0, "离退后管理单位", wcf_center));
			sheet5.addCell(new Label(8, 0, "曾任最高职务", wcf_center));
			sheet5.addCell(new Label(9, 0, "离退批准文号", wcf_center));
			//设置内容
			List list5 = dataList_a.get(9);
			for (int i = 1; list5!=null && list5.size() > 0 && i <= list5.size(); i++) {
				A31 a31 = (A31) list5.get(i-1);
				String[] a01arr = (String[]) a01Map.get(a31.getA0000());
				sheet5.addCell(new Label(0, i, a01arr[0], wcf_left));
				sheet5.addCell(new Label(1, i, a01arr[1], wcf_left));
				sheet5.addCell(new Label(2, i, a01arr[2], wcf_left));
				sheet5.addCell(new Label(3, i, a01arr[3], wcf_left));
				sheet5.addCell(new Label(4, i, getName(a31.getA3101(), "ZB132", ""), wcf_left));
				sheet5.addCell(new Label(5, i, a31.getA3104(), wcf_left));
				sheet5.addCell(new Label(6, i, getName(a31.getA3107(), "ZB09", ""), wcf_left));
				sheet5.addCell(new Label(7, i, a31.getA3117a(), wcf_left));
				sheet5.addCell(new Label(8, i, a31.getA3118(), wcf_left));
				sheet5.addCell(new Label(9, i, a31.getA3137(), wcf_left));
			}
			// 退出管理信息
			WritableSheet sheet6= workbook.createSheet("退出管理信息", 6);// parm1:工作表名称;parm2:工作表在Excel的位置从0开始
			jxl.SheetSettings sheetset6= sheet6.getSettings();
			sheetset6.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset6.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet6.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet6.addCell(new Label(1, 0, "性别", wcf_center));
			sheet6.addCell(new Label(2, 0, "出生年月", wcf_center));
			sheet6.addCell(new Label(3, 0, "身份证号", wcf_center));
			sheet6.addCell(new Label(4, 0, "退出管理方式", wcf_center));
			sheet6.addCell(new Label(5, 0, "退出日期", wcf_center));
			sheet6.addCell(new Label(6, 0, "调往单位", wcf_center));
			sheet6.addCell(new Label(7, 0, "备注", wcf_center));
			//设置内容
			List list6 = dataList_a.get(8);
			for (int i = 1; list6!=null && list6.size() > 0 && i <= list6.size(); i++) {
				A30 a30 = (A30) list6.get(i-1);
				String[] a01arr = (String[]) a01Map.get(a30.getA0000());
				sheet6.addCell(new Label(0, i, a01arr[0], wcf_left));
				sheet6.addCell(new Label(1, i, a01arr[1], wcf_left));
				sheet6.addCell(new Label(2, i, a01arr[2], wcf_left));
				sheet6.addCell(new Label(3, i, a01arr[3], wcf_left));
				sheet6.addCell(new Label(4, i, getName(a30.getA3001(), "ZB87", ""), wcf_left));
				sheet6.addCell(new Label(5, i, a30.getA3004(), wcf_left));
				sheet6.addCell(new Label(6, i, a30.getA3007a(), wcf_left));
				sheet6.addCell(new Label(7, i, a30.getA3034(), wcf_left));
			}
			//进入管理信息
			WritableSheet sheet7= workbook.createSheet("进入管理信息", 7);
			jxl.SheetSettings sheetset7= sheet7.getSettings();
			sheetset7.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset7.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet7.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet7.addCell(new Label(1, 0, "性别", wcf_center));
			sheet7.addCell(new Label(2, 0, "出生年月", wcf_center));
			sheet7.addCell(new Label(3, 0, "身份证号", wcf_center));
			sheet7.addCell(new Label(4, 0, "进入本单位方式", wcf_center));
			sheet7.addCell(new Label(5, 0, "进入日期", wcf_center));
			sheet7.addCell(new Label(6, 0, "原单位名称", wcf_center));
			sheet7.addCell(new Label(7, 0, "原单位职务", wcf_center));
			sheet7.addCell(new Label(8, 0, "原单位级别", wcf_center));
			sheet7.addCell(new Label(9, 0, "进入公务员队伍时间", wcf_center));
			sheet7.addCell(new Label(10, 0, "公务员登记时间", wcf_center));
			
			//设置内容
			List list7 = dataList_a.get(7);
			for (int i = 1; list7!=null && list7.size() > 0 && i <= list7.size(); i++) {
				A29 a29 = (A29) list7.get(i-1);
				String[] a01arr = (String[]) a01Map.get(a29.getA0000());
				sheet7.addCell(new Label(0, i, a01arr[0], wcf_left));
				sheet7.addCell(new Label(1, i, a01arr[1], wcf_left));
				sheet7.addCell(new Label(2, i, a01arr[2], wcf_left));
				sheet7.addCell(new Label(3, i, a01arr[3], wcf_left));
				sheet7.addCell(new Label(4, i, getName(a29.getA2911(), "ZB77", ""), wcf_left));
				sheet7.addCell(new Label(5, i, a29.getA2907(), wcf_left));
				sheet7.addCell(new Label(6, i, a29.getA2921a(), wcf_left));
				sheet7.addCell(new Label(7, i, a29.getA2941(), wcf_left));
				sheet7.addCell(new Label(8, i, a29.getA2944(), wcf_left));
				sheet7.addCell(new Label(9, i, a29.getA2947(), wcf_left));
				sheet7.addCell(new Label(10, i, a29.getA2949(), wcf_left));
			}
			//考核信息
			WritableSheet sheet8= workbook.createSheet("考核信息", 8);
			jxl.SheetSettings sheetset8= sheet8.getSettings();
			sheetset8.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset8.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet8.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet8.addCell(new Label(1, 0, "性别", wcf_center));
			sheet8.addCell(new Label(2, 0, "出生年月", wcf_center));
			sheet8.addCell(new Label(3, 0, "身份证号", wcf_center));
			sheet8.addCell(new Label(4, 0, "考核年度", wcf_center));
			sheet8.addCell(new Label(5, 0, "考核结果", wcf_center));
			
			//设置内容
			List list8 = dataList_a.get(6);
			for (int i = 1; list8!=null && list8.size() > 0 && i <= list8.size(); i++) {
				A15 a15 = (A15) list8.get(i-1);
				String[] a01arr = (String[]) a01Map.get(a15.getA0000());
				sheet8.addCell(new Label(0, i, a01arr[0], wcf_left));
				sheet8.addCell(new Label(1, i, a01arr[1], wcf_left));
				sheet8.addCell(new Label(2, i, a01arr[2], wcf_left));
				sheet8.addCell(new Label(3, i, a01arr[3], wcf_left));
				sheet8.addCell(new Label(4, i, a15.getA1521(), wcf_left));
				sheet8.addCell(new Label(5, i, getName(a15.getA1517(), "ZB18", ""), wcf_left));
			}
			
			// 创建奖惩信息
			WritableSheet sheet9= workbook.createSheet("奖惩信息", 9);
			jxl.SheetSettings sheetset9= sheet9.getSettings();
			sheetset9.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset9.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet9.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet9.addCell(new Label(1, 0, "性别", wcf_center));
			sheet9.addCell(new Label(2, 0, "出生年月", wcf_center));
			sheet9.addCell(new Label(3, 0, "身份证号", wcf_center));
			sheet9.addCell(new Label(4, 0, "奖惩名称代码", wcf_center));
			sheet9.addCell(new Label(5, 0, "奖惩名称", wcf_center));
			sheet9.addCell(new Label(6, 0, "批准时间", wcf_center));
			sheet9.addCell(new Label(7, 0, "批准机关级别", wcf_center));
			sheet9.addCell(new Label(8, 0, "批准机关", wcf_center));
			sheet9.addCell(new Label(9, 0, "撤销时间", wcf_center));
			sheet9.addCell(new Label(10, 0, "批准机关性质", wcf_center));
			sheet9.addCell(new Label(11, 0, "奖惩时职务层次", wcf_center));
			
			//设置内容
			List list9 = dataList_a.get(5);
			for (int i = 1; list9!=null && list9.size() > 0 && i <= list9.size(); i++) {
				A14 a14 = (A14) list9.get(i-1);
				String[] a01arr = (String[]) a01Map.get(a14.getA0000());
				sheet9.addCell(new Label(0, i, a01arr[0], wcf_left));
				sheet9.addCell(new Label(1, i, a01arr[1], wcf_left));
				sheet9.addCell(new Label(2, i, a01arr[2], wcf_left));
				sheet9.addCell(new Label(3, i, a01arr[3], wcf_left));
				sheet9.addCell(new Label(4, i, a14.getA1404b(), wcf_left));
				sheet9.addCell(new Label(5, i, a14.getA1404a(), wcf_left));
				sheet9.addCell(new Label(6, i, a14.getA1407(), wcf_left));
				sheet9.addCell(new Label(7, i, getName(a14.getA1414(), "ZB03", ""), wcf_left));
				sheet9.addCell(new Label(8, i, a14.getA1411a(), wcf_left));
				sheet9.addCell(new Label(9, i, a14.getA1424(), wcf_left));
				sheet9.addCell(new Label(10, i, getName(a14.getA1428(), "ZB128", ""), wcf_left));
				sheet9.addCell(new Label(11, i, getName(a14.getA1415(), "ZB09", ""), wcf_left));
			}
			//住址通讯信息
			WritableSheet sheet10= workbook.createSheet("住址通讯信息", 10);
			jxl.SheetSettings sheetset910= sheet10.getSettings();
			sheetset910.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset910.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet10.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet10.addCell(new Label(1, 0, "性别", wcf_center));
			sheet10.addCell(new Label(2, 0, "出生年月", wcf_center));
			sheet10.addCell(new Label(3, 0, "身份证号", wcf_center));
			sheet10.addCell(new Label(4, 0, "办公地址", wcf_center));
			sheet10.addCell(new Label(5, 0, "家庭住址", wcf_center));
			sheet10.addCell(new Label(6, 0, "移动电话", wcf_center));
			sheet10.addCell(new Label(7, 0, "办公电话", wcf_center));
			sheet10.addCell(new Label(8, 0, "秘书电话", wcf_center));
			sheet10.addCell(new Label(9, 0, "住宅电话", wcf_center));
			sheet10.addCell(new Label(10, 0, "电子邮箱", wcf_center));
			sheet10.addCell(new Label(11, 0, "住址邮编", wcf_center));
			
			//设置内容
			List list10 = dataList_a.get(11);
			for (int i = 1; list10!=null && list10.size() > 0 && i <= list10.size(); i++) {
				A37 a37 = (A37) list10.get(i-1);
				String[] a01arr = (String[]) a01Map.get(a37.getA0000());
				sheet10.addCell(new Label(0, i, a01arr[0], wcf_left));
				sheet10.addCell(new Label(1, i, a01arr[1], wcf_left));
				sheet10.addCell(new Label(2, i, a01arr[2], wcf_left));
				sheet10.addCell(new Label(3, i, a01arr[3], wcf_left));
				sheet10.addCell(new Label(4, i, a37.getA3701(), wcf_left));
				sheet10.addCell(new Label(5, i, a37.getA3711(), wcf_left));
				sheet10.addCell(new Label(6, i, a37.getA3707c(), wcf_left));
				sheet10.addCell(new Label(7, i, a37.getA3707a(), wcf_left));
				sheet10.addCell(new Label(8, i, a37.getA3707e(), wcf_left));
				sheet10.addCell(new Label(9, i, a37.getA3707b(), wcf_left));
				sheet10.addCell(new Label(10, i, a37.getA3708(), wcf_left));
				sheet10.addCell(new Label(11, i, a37.getA3714(), wcf_left));
			}
			//家庭成员信息
			WritableSheet sheet11= workbook.createSheet("家庭成员信息", 11);
			jxl.SheetSettings sheetset11= sheet11.getSettings();
			sheetset11.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset11.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet11.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet11.addCell(new Label(1, 0, "性别", wcf_center));
			sheet11.addCell(new Label(2, 0, "出生年月", wcf_center));
			sheet11.addCell(new Label(3, 0, "身份证号", wcf_center));
			sheet11.addCell(new Label(4, 0, "称谓", wcf_center));
			sheet11.addCell(new Label(5, 0, "家庭成员姓名", wcf_center));
			sheet11.addCell(new Label(6, 0, "出生日期", wcf_center));
			sheet11.addCell(new Label(7, 0, "政治面貌", wcf_center));
			sheet11.addCell(new Label(8, 0, "工作单位及职务", wcf_center));
			
			//设置内容
			List list11 = dataList_a.get(10);
			for (int i = 1; list11!=null && list11.size() > 0 && i <= list11.size(); i++) {
				A36 a36 = (A36) list11.get(i-1);
				String[] a01arr = (String[]) a01Map.get(a36.getA0000());
				sheet11.addCell(new Label(0, i, a01arr[0], wcf_left));
				sheet11.addCell(new Label(1, i, a01arr[1], wcf_left));
				sheet11.addCell(new Label(2, i, a01arr[2], wcf_left));
				sheet11.addCell(new Label(3, i, a01arr[3], wcf_left));
				sheet11.addCell(new Label(4, i, getName(a36.getA3604a(), "GB4761", ""), wcf_left));
				sheet11.addCell(new Label(5, i, a36.getA3601(), wcf_left));
				sheet11.addCell(new Label(6, i, a36.getA3607(), wcf_left));
				sheet11.addCell(new Label(7, i, getName(a36.getA3627(), "GB4762", ""), wcf_left));
				sheet11.addCell(new Label(8, i, a36.getA3611(), wcf_left));
			}
			//培训班信息
			WritableSheet sheet12= workbook.createSheet("培训班信息", 12);
			jxl.SheetSettings sheetset12= sheet12.getSettings();
			sheetset12.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset12.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet12.addCell(new Label(0, 0, "培训名称", wcf_center));
			sheet12.addCell(new Label(1, 0, "培训重名识别码", wcf_center));
			sheet12.addCell(new Label(2, 0, "培训类别", wcf_center));
			sheet12.addCell(new Label(3, 0, "开始时间", wcf_center));
			sheet12.addCell(new Label(4, 0, "结束时间", wcf_center));
			sheet12.addCell(new Label(5, 0, "培训机构类别", wcf_center));
			sheet12.addCell(new Label(6, 0, "培训离岗状态", wcf_center));
			sheet12.addCell(new Label(7, 0, "主办单位", wcf_center));
			sheet12.addCell(new Label(8, 0, "培训机构名称", wcf_center));
			sheet12.addCell(new Label(9, 0, "出国（出境）培训标识", wcf_center));
			//设置内容
			List list12 = dataList_a.get(4);
			for (int i = 1; list12!=null && list12.size() > 0 && i <= list12.size(); i++) {
				A11 a11 = (A11) list12.get(i-1);
				sheet12.addCell(new Label(0, i, a11.getA1131(), wcf_left));
				sheet12.addCell(new Label(1, i, a11.getA1100(), wcf_left));
				sheet12.addCell(new Label(2, i, getName(a11.getA1101(), "ZB29", ""), wcf_left));
				sheet12.addCell(new Label(3, i, a11.getA1107(), wcf_left));
				sheet12.addCell(new Label(4, i, a11.getA1111(), wcf_left));
				sheet12.addCell(new Label(5, i, getName(a11.getA1127(), "ZB27", ""), wcf_left));
				sheet12.addCell(new Label(6, i, getName(a11.getA1104(), "ZB30", ""), wcf_left));
				sheet12.addCell(new Label(7, i, a11.getA1114(), wcf_left));
				sheet12.addCell(new Label(8, i, a11.getA1121a(), wcf_left));
				sheet12.addCell(new Label(9, i, (a11.getA1151()!=null&&!a11.getA1151().equals(""))?
						(a11.getA1151().equals("1")?"是":"否"):"" , wcf_left));
				a11Map.put(a11.getA1100(), a11.getA1131());
			}
			//培训人员信息
			WritableSheet sheet13= workbook.createSheet("培训人员信息", 13);
			jxl.SheetSettings sheetset13= sheet13.getSettings();
			sheetset13.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset13.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet13.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet13.addCell(new Label(1, 0, "性别", wcf_center));
			sheet13.addCell(new Label(2, 0, "出生年月", wcf_center));
			sheet13.addCell(new Label(3, 0, "身份证号", wcf_center));
			sheet13.addCell(new Label(4, 0, "培训名称", wcf_center));
			sheet13.addCell(new Label(5, 0, "培训重名识别码", wcf_center));
			//设置内容
			List list13 = dataList_a.get(12);
			for (int i = 1; list13!=null && list13.size() > 0 && i <= list13.size(); i++) {
				A41 a41 = (A41) list13.get(i-1);
				String[] a01arr = (String[]) a01Map.get(a41.getA0000());
				sheet13.addCell(new Label(0, i, a01arr[0], wcf_left));
				sheet13.addCell(new Label(1, i, a01arr[1], wcf_left));
				sheet13.addCell(new Label(2, i, a01arr[2], wcf_left));
				sheet13.addCell(new Label(3, i, a01arr[3], wcf_left));
				sheet13.addCell(new Label(4, i, a11Map.get(a41.getA1100())!=null ?
						a11Map.get(a41.getA1100()).toString():"" , wcf_left));
				sheet13.addCell(new Label(5, i, a41.getA1100(), wcf_left));
			}
			//专业技术信息
			WritableSheet sheet14= workbook.createSheet("专业技术信息", 14);
			jxl.SheetSettings sheetset14= sheet14.getSettings();
			sheetset14.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset14.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet14.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet14.addCell(new Label(1, 0, "性别", wcf_center));
			sheet14.addCell(new Label(2, 0, "出生年月", wcf_center));
			sheet14.addCell(new Label(3, 0, "身份证号", wcf_center));
			sheet14.addCell(new Label(4, 0, "专业技术资格", wcf_center));
			sheet14.addCell(new Label(5, 0, "获取资格日期", wcf_center));
			sheet14.addCell(new Label(6, 0, "取得资格途径", wcf_center));
			sheet14.addCell(new Label(7, 0, "评委会或考试名称", wcf_center));
			//设置内容
			List list14 = dataList_a.get(2);
			for (int i = 1; list14!=null && list14.size() > 0 && i <= list14.size(); i++) {
				A06 a06 = (A06) list14.get(i-1);
				String[] a01arr = (String[]) a01Map.get(a06.getA0000());
				sheet14.addCell(new Label(0, i, a01arr[0], wcf_left));
				sheet14.addCell(new Label(1, i, a01arr[1], wcf_left));
				sheet14.addCell(new Label(2, i, a01arr[2], wcf_left));
				sheet14.addCell(new Label(3, i, a01arr[3], wcf_left));
				sheet14.addCell(new Label(4, i, a06.getA0602(), wcf_left));
				sheet14.addCell(new Label(5, i, a06.getA0604(), wcf_left));
				sheet14.addCell(new Label(6, i, getName( a06.getA0607(), "ZB24", ""), wcf_left));
				sheet14.addCell(new Label(7, i, a06.getA0611(), wcf_left));
			}
			//任免信息
			WritableSheet sheet15= workbook.createSheet("任免信息", 15);
			jxl.SheetSettings sheetset15= sheet15.getSettings();
			sheetset15.setProtected(false);// 设定保护状态
			if (!StringUtil.isEmpty(passwd)) {
				sheetset15.setPassword(passwd);// 设定密码
			}
			//设置标题
			sheet15.addCell(new Label(0, 0, "姓名", wcf_center));
			sheet15.addCell(new Label(1, 0, "性别", wcf_center));
			sheet15.addCell(new Label(2, 0, "出生年月", wcf_center));
			sheet15.addCell(new Label(3, 0, "身份证号", wcf_center));
			sheet15.addCell(new Label(4, 0, "拟任职务", wcf_center));
			sheet15.addCell(new Label(5, 0, "拟免职务", wcf_center));
			sheet15.addCell(new Label(6, 0, "任免理由", wcf_center));
			sheet15.addCell(new Label(7, 0, "呈报单位", wcf_center));
			sheet15.addCell(new Label(8, 0, "时间", wcf_center));
			
			//设置内容
			List list15 = dataList_a.get(13);
			for (int i = 1; list15!=null && list15.size() > 0 && i <= list15.size(); i++) {
				A53 a53 = (A53) list15.get(i-1);
				String[] a01arr = (String[]) a01Map.get(a53.getA0000());
				sheet15.addCell(new Label(0, i, a01arr[0], wcf_left));
				sheet15.addCell(new Label(1, i, a01arr[1], wcf_left));
				sheet15.addCell(new Label(2, i, a01arr[2], wcf_left));
				sheet15.addCell(new Label(3, i, a01arr[3], wcf_left));
				sheet15.addCell(new Label(4, i, a53.getA5304(), wcf_left));
				sheet15.addCell(new Label(5, i, a53.getA5315(), wcf_left));
				sheet15.addCell(new Label(6, i, a53.getA5317(), wcf_left));
				sheet15.addCell(new Label(7, i, a53.getA5319(), wcf_left));
				sheet15.addCell(new Label(8, i, a53.getA5321(), wcf_left));
			}			
			/** **********7.将以上缓存中的内容写到EXCEL文件中******** */
			workbook.write();
			/** *********8.关闭文件************* */
			workbook.close();

		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			CommonQueryBS.systemOut(result);
			e.printStackTrace();
		}
		
	}

}