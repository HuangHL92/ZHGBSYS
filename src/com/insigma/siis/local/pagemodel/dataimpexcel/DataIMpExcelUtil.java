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
 * Excel??????????
 * ????API??????http://jexcelapi.sourceforge.net/resources/javadocs/2_6_10/docs/
 * 
 * @author mengl
 * 
 */
public class DataIMpExcelUtil {

	@SuppressWarnings("unused")
	private static String pattern = "yyyyMMdd HH:mm:ss"; // ????????????????????????

	/**
	 * Excel????????
	 * 
	 * ????API??????http://jexcelapi.sourceforge.net/resources/javadocs/2_6_10/docs/
	 * 
	 * @author mengl
	 * @param response
	 *            HttpServletResponse????
	 * @param listContent
	 *            ????Excel?????????? ??????????List<List<Object>> listContent
	 *            ??Object??????????????????????????????
	 * @param fileName
	 *            ????????????????????????????????
	 * @param passwd
	 *            ????????
	 * @return
	 */

	public final static String exportExcel(HttpServletResponse response,
			List<List<Object>> listContent, String fileName, String passwd) {
		String result = "??????????Excel??????????????";
		// ??????????
		if (StringUtil.isEmpty(fileName)) {
			fileName = "????????";
		}
		fileName = fileName.replace(".xls", "") + "_"
				+ DateUtil.toString(new Date(), "yyyyMMddHHmmss") + ".xls";

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		try {
			// ??????????????????????????????______________________begin
			// HttpServletResponse response=ServletActionContext.getResponse();
			OutputStream os = response.getOutputStream();// ??????????
			response.reset();// ??????????
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			// ??????????????
			response.setContentType("application/msexcel");// ????????????
			// ??????????????????????????????_______________________end

			/** **********1.??????????************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			workbook.setProtected(false);

			/** ************2.??????????????************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);

			/** ************3.????????????????????????????????************ */
			// ????????????
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // ????
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // ????????????
			wcf_center.setAlignment(Alignment.CENTRE); // ????????????
			wcf_center.setWrap(false); // ????????????

			// ????????????
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // ????
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // ????????????
			wcf_left.setAlignment(Alignment.LEFT); // ????????????
			wcf_left.setWrap(false); // ????????????

			// ????????????????Info,??????????????????
			WritableSheet sheetInfo = workbook.createSheet("Info", 0);// parm1:??????????;parm2:????????Excel????????0????
			jxl.SheetSettings sheetsetInfo = sheetInfo.getSettings();
			sheetsetInfo.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetsetInfo.setPassword(passwd);// ????????
			}

			for (int i = 0; listContent != null && i < listContent.size(); i++) {
				// ????sheet?????????????????????????????????????????????? ??Sheet+??????
				Object sheetMappingObj = listContent.get(i).get(0);
				String sheetName = "";
				if (sheetMappingObj != null) {
					sheetName = sheetMappingObj.getClass().getSimpleName();// ??????
					// sheetName = sheetMappingObj.getClass().getName();//????????
					// ????????????Info:??????????????????????sheet??????????????????
					sheetInfo.addCell(new Label(0, i, sheetMappingObj
							.getClass().getName(), wcf_left));
				}

				/** **********4.??????????************ */
				// ????List<Object>??????????????
				WritableSheet sheet = workbook.createSheet(StringUtil
						.isEmpty(sheetName) ? "Sheet" + (i + 2) : sheetName,
						(i + 1));// parm1:??????????;parm2:????????Excel????????0????
				/** **********??????????????????????????????????***************** */
				jxl.SheetSettings sheetset = sheet.getSettings();
				sheetset.setProtected(false);// ????????????
				if (!StringUtil.isEmpty(passwd)) {
					sheetset.setPassword(passwd);// ????????
				}

				/** ***************5.??????EXCEL????????????????????********************* */
				// sheet.mergeCells(0, 0, colWidth, 0);
				// sheet.addCell(new Label(0, 0, "XX????", wcf_center));
				/**
				 * ***************6.??????EXCEL???????????????????????????????????? ?? ????????????????
				 * ??????*********************
				 */
				// ??????????List??Object??????????????????????????????
				Field[] titles = sheetMappingObj.getClass().getDeclaredFields();
				for (int index = 0; titles != null && index < titles.length; index++) {
					sheet.addCell(new Label(index, 0, titles[index].getName(),
							wcf_center));
				}

				/** ***************6.??????EXCEL????????********************* */
				Field[] fields = null;
				int z = 1;// ????,0????????????????????????0??????????
				for (Object obj : listContent.get(i)) {
					if (!sheetMappingObj.getClass().getName()
							.equals(obj.getClass().getName())) {
						throw new AppException(
								"????Excel????!??????????List<List<Object>> listContent ??????List<Object>??????List????????????????");
					}

					fields = obj.getClass().getDeclaredFields();
					int j = 0;// ????
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

			/** **********7.??????????????????????EXCEL??????******** */
			workbook.write();
			/** *********8.????????************* */
			workbook.close();

		} catch (Exception e) {
			result = "??????????Excel????????????????????" + e.toString();
			CommonQueryBS.systemOut(result);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Excel????????
	 * 
	 * @author mengl
	 * @param item ??????????
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
			//????????????
			Sheet a01Sheet = rwb.getSheet("????????????");
			List a01s = new ArrayList();
			for (int i = 0; i < a01Sheet.getRows(); i++) {
				A01temp a01t = new A01temp();
				String uuid = UUID.randomUUID().toString();
				a01Map.put(a01Sheet.getCell(0, i).getContents()+a01Sheet.getCell(1, i).getContents()
						+a01Sheet.getCell(2, i).getContents()+a01Sheet.getCell(3, i).getContents(), uuid);
				a01t.setA0000(uuid);
				a01t.setA0101(a01Sheet.getCell(0, i).getContents());//????
				a01t.setA0104(getCode(a01Sheet.getCell(1, i).getContents(),"GB2261", ""));//????????
				a01t.setA0104a(a01Sheet.getCell(1, i).getContents());//????????
				a01t.setA0107(a01Sheet.getCell(2, i).getContents());//????????
				
				a01t.setA0184(a01Sheet.getCell(3, i).getContents());//????????
				a01t.setA0111(getCode(a01Sheet.getCell(4, i).getContents(),"ZB01", ""));//??????
				a01t.setA0111a(a01Sheet.getCell(4, i).getContents());//????
				a01t.setA0114(getCode(a01Sheet.getCell(5, i).getContents(),"ZB01", ""));//??????
				a01t.setA0114a(a01Sheet.getCell(5, i).getContents());//??????
				a01t.setA0117(getCode(a01Sheet.getCell(6, i).getContents(),"GB3304", ""));//????
				a01t.setA0117a(a01Sheet.getCell(6, i).getContents());//????
				
				a01t.setA0144(a01Sheet.getCell(7, i).getContents());//????????
				a01t.setA0141(getCode(a01Sheet.getCell(8, i).getContents(),"GB4762", ""));//????????
				a01t.setA3921(getCode(a01Sheet.getCell(9, i).getContents(),"GB4762", ""));//????????
				a01t.setA3927(getCode(a01Sheet.getCell(10, i).getContents(),"GB4762", ""));//????????
				
				a01t.setA0134(a01Sheet.getCell(11, i).getContents());//????????????
				a01t.setA0160(getCode(a01Sheet.getCell(12, i).getContents(),"GB3304", ""));//????????
				a01t.setA0187a(a01Sheet.getCell(13, i).getContents());//????????????????
				a01t.setA0128(getCode(a01Sheet.getCell(14, i).getContents(),"GB2261C", ""));//????????
				a01t.setA0128b(a01Sheet.getCell(14, i).getContents());//????????
				a01t.setA0163(getCode(a01Sheet.getCell(15, i).getContents(),"ZB126", ""));//????????
				a01t.setA0165(getCode(a01Sheet.getCell(16, i).getContents(),"ZB130", ""));//????????

				a01t.setA0194((a01Sheet.getCell(17, i).getContents()!=null && 
						!a01Sheet.getCell(17, i).getContents().equals(""))?Long.parseLong(a01Sheet.getCell(17, i).getContents()):null );//????????????????
				a01t.setA1701(a01Sheet.getCell(18, i).getContents());//????????
				dealA01(a01t);//???????? ????????
				a01s.add(a01t);
			}
			
			//????????
			Sheet b01Sheet = rwb.getSheet("????????");
			List b01s = new ArrayList();
			for (int i = 0; i < b01Sheet.getRows(); i++) {
				B01temp b01t = new B01temp();
				b01t.setB0101(b01Sheet.getCell(0, i).getContents());//????????
				b01t.setB0104(b01Sheet.getCell(1, i).getContents());//????????
				b01t.setB0111(b01Sheet.getCell(2, i).getContents());//?????????????? ??????
//				b01t.setA0184(b01Sheet.getCell(3, i).getContents());//????????????
				b01t.setB0121(b01Sheet.getCell(4, i).getContents());//?????????????????? ??????
				
				b01t.setB0131(getCode(b01Sheet.getCell(5, i).getContents(),"ZB04", ""));//????lei??
				b01t.setB0127(getCode(b01Sheet.getCell(6, i).getContents(),"ZB03", ""));//????????
				b01t.setB0117(getCode(b01Sheet.getCell(7, i).getContents(),"ZB01", ""));//????????????
				b01t.setB0194(getCode(b01Sheet.getCell(8, i).getContents(),"XZ20", ""));//????????????
				b01t.setB0114(b01Sheet.getCell(9, i).getContents());//????(????)????

				b01t.setB0124(getCode(b01Sheet.getCell(10, i).getContents(),"ZB87", ""));//????????
				String b0183 = b01Sheet.getCell(11, i).getContents();
				b01t.setB0183(b0183!=null&&!b0183.equals("")?Long.parseLong(b0183):null);//????????????
				String b0185 = b01Sheet.getCell(12, i).getContents();
				b01t.setB0185(b0185!=null&&!b0185.equals("")?Long.parseLong(b0185):null);//????????????
				String B0188 = b01Sheet.getCell(13, i).getContents();
				b01t.setB0188(B0188!=null&&!B0188.equals("")?Long.parseLong(B0188):null);//??????????????
				String B0189 = b01Sheet.getCell(14, i).getContents();
				b01t.setB0189(B0189!=null&&!B0189.equals("")?Long.parseLong(B0189):null);//??????????????
				
				String B0150 = b01Sheet.getCell(15, i).getContents();
				b01t.setB0150(B0150!=null&&!B0150.equals("")?Long.parseLong(B0150):null);//????????????
				String B0190 = b01Sheet.getCell(16, i).getContents();
				b01t.setB0190(B0190!=null&&!B0190.equals("")?Long.parseLong(B0190):null);//????????????????
				String B0191A = b01Sheet.getCell(17, i).getContents();
				b01t.setB0191a(B0191A!=null&&!B0191A.equals("")?Long.parseLong(B0191A):null);//????????????????
				String B0192 = b01Sheet.getCell(18, i).getContents();
				b01t.setB0192(B0192!=null&&!B0192.equals("")?Long.parseLong(B0192):null);//??????????????????
				String B0193 = b01Sheet.getCell(19, i).getContents();
				b01t.setB0193(B0193!=null&&!B0193.equals("")?Long.parseLong(B0193):null);//??????????????????
				
				String B0227 = b01Sheet.getCell(20, i).getContents();
				b01t.setB0227(B0227!=null&&!B0227.equals("")?Long.parseLong(B0227):null);//??????????
				String B0232 = b01Sheet.getCell(21, i).getContents();
				b01t.setB0232(B0232!=null&&!B0232.equals("")?Long.parseLong(B0232):null);//??????????????????????????
				String B0233 = b01Sheet.getCell(22, i).getContents();
				b01t.setB0233(B0233!=null&&!B0233.equals("")?Long.parseLong(B0233):null);//??????????????
				
				b01s.add(b01t);
			}
			
			//????????
			Sheet A02Sheet = rwb.getSheet("????????");
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
				a02t.setA0201a(A02Sheet.getCell(4, i).getContents());//????????
				
				a02t.setA0201b(A02Sheet.getCell(5, i).getContents());//?????????????? ??????
				a02t.setA0215a(getCode(A02Sheet.getCell(6, i).getContents(),"ZB08", ""));//???????? ?????? A0215b
				a02t.setA0219(getCode(A02Sheet.getCell(7, i).getContents(),"ZB42", ""));//????????
				a02t.setA0221(getCode(A02Sheet.getCell(8, i).getContents(),"ZB09", ""));//????????
				a02t.setA0243(A02Sheet.getCell(9, i).getContents());//????????
				
				a02t.setA0247(getCode(A02Sheet.getCell(10, i).getContents(),"ZB122", ""));//????????????
				a02t.setA0251(getCode(A02Sheet.getCell(11, i).getContents(),"ZB13", ""));//????????
				a02t.setA0295(A02Sheet.getCell(12, i).getContents());//???????? ????????
				a02t.setA0255(getCode(A02Sheet.getCell(13, i).getContents(),"ZB14", ""));//????????
				a02t.setA0281(A02Sheet.getCell(14, i).getContents());//????????????   1  ??????
				
				a02t.setA0245(A02Sheet.getCell(15, i).getContents());//????????
				a02t.setA0288(A02Sheet.getCell(16, i).getContents());//??????????????
				a02t.setA0201e(getCode(A02Sheet.getCell(17, i).getContents(),"ZB129", ""));//????????
				a02t.setA0201d(getCode(A02Sheet.getCell(18, i).getContents(),"XZ09", ""));//????????????
				a02t.setA0229(A02Sheet.getCell(19, i).getContents());//????????   ?????   ????????????????(????????????????????????????????????????????)
				
				a02t.setA0271(getCode(A02Sheet.getCell(20, i).getContents(),"ZB16", ""));//????????
				a02t.setA0265(A02Sheet.getCell(21, i).getContents());//????????
				a02t.setA0267(A02Sheet.getCell(22, i).getContents());//????????
				a02t.setA0284(getCode(A02Sheet.getCell(23, i).getContents(),"XZ09", ""));//????????
				a02t.setA4904(getCode(A02Sheet.getCell(24, i).getContents(),"ZB73", ""));//????????
				
				a02t.setA4901(getCode(A02Sheet.getCell(25, i).getContents(),"ZB72", ""));//????????
				a02t.setA4907(getCode(A02Sheet.getCell(26, i).getContents(),"ZB74", ""));//????????
				a02t.setA0259(getCode(A02Sheet.getCell(27, i).getContents(),"", ""));//???????? ??? 
				String NO = A02Sheet.getCell(28, i).getContents();
				a02t.setA0225(NO!=null&&!NO.equals("")?Long.parseLong(NO):null);//????????????
				
				a02s.add(a02t);
			}
			//????????????
			Sheet A08Sheet = rwb.getSheet("????????????");
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
				a08t.setA0801a(A08Sheet.getCell(4, i).getContents());//????
				
				a08t.setA0901a(A08Sheet.getCell(5, i).getContents());//??????
				a08t.setA0824(A08Sheet.getCell(6, i).getContents());//????
				a08t.setA0904(A08Sheet.getCell(7, i).getContents());//????????
				a08t.setA0804(A08Sheet.getCell(8, i).getContents());//????????
				a08t.setA0807(A08Sheet.getCell(9, i).getContents());//????????
				
				a08t.setA0814(A08Sheet.getCell(10, i).getContents());//??????????
				a08t.setA0837(getCode(A08Sheet.getCell(11, i).getContents(),"ZB123", ""));//????????
				a08t.setA0811(A08Sheet.getCell(12, i).getContents());//????????
				a08s.add(a08t);
				
			}
			//????????
			Sheet A31Sheet = rwb.getSheet("????????");
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
				a31t.setA3101(getCode(A31Sheet.getCell(4, i).getContents(),"ZB123", ""));//????????
				
				a31t.setA3104(A31Sheet.getCell(5, i).getContents());//????????????
				a31t.setA3107(getCode(A31Sheet.getCell(6, i).getContents(),"ZB09", ""));//??????????
				a31t.setA3117a(A31Sheet.getCell(7, i).getContents());//??????????????
				a31t.setA3118(A31Sheet.getCell(8, i).getContents());//????????????
				a31t.setA3137(A31Sheet.getCell(9, i).getContents());//????????????
				a31s.add(a31t);
			}
			//????????????
			Sheet A30Sheet = rwb.getSheet("????????????");
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
				a30t.setA3001(getCode(A30Sheet.getCell(4, i).getContents(),"ZB87", ""));//????????????
				
				a30t.setA3004(A30Sheet.getCell(5, i).getContents());//????????
				a30t.setA3007a(A30Sheet.getCell(6, i).getContents());//????????
				a30t.setA3034(A30Sheet.getCell(7, i).getContents());//????
				a30s.add(a30t);
			}
			//????????????
			Sheet A29Sheet = rwb.getSheet("????????????");
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
				a29t.setA2911(getCode(A29Sheet.getCell(4, i).getContents(),"ZB77", ""));//??????????????
				
				a29t.setA2907(A29Sheet.getCell(5, i).getContents());//????????
				a29t.setA2921a(A29Sheet.getCell(6, i).getContents());//??????????
				a29t.setA2941(A29Sheet.getCell(7, i).getContents());//??????????
				a29t.setA2944(A29Sheet.getCell(8, i).getContents());//??????????
				a29t.setA2947(A29Sheet.getCell(9, i).getContents());//??????????????????
				a29t.setA2949(A29Sheet.getCell(10, i).getContents());//??????????????
				a29s.add(a29t);
			}
			//????????
			Sheet A15Sheet = rwb.getSheet("????????");
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
				a15t.setA1521(A15Sheet.getCell(4, i).getContents());//????????
				
				a15t.setA1517(getCode(A15Sheet.getCell(5, i).getContents(),"ZB18", ""));//????????
				a15s.add(a15t);
			}
			//????????
			Sheet A14Sheet = rwb.getSheet("????????");
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
				a14t.setA1404b(A14Sheet.getCell(4, i).getContents());//??????????????
				
				a14t.setA1404a(A14Sheet.getCell(5, i).getContents());//????????
				a14t.setA1407(A14Sheet.getCell(6, i).getContents());//??????????
				a14t.setA1414(getCode(A14Sheet.getCell(7, i).getContents(),"ZB18", ""));//??????????
				a14t.setA1411a(A14Sheet.getCell(8, i).getContents());//??????????
				a14t.setA1424(A14Sheet.getCell(9, i).getContents());//??????????????????
				a14t.setA1428(getCode(A14Sheet.getCell(10, i).getContents(),"ZB128", ""));//??????????????
				a14t.setA1415(getCode(A14Sheet.getCell(11, i).getContents(),"ZB09", ""));//??????????????
				a14s.add(a14t);
			}
			//????????????
			Sheet A37Sheet = rwb.getSheet("????????????");
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
				a37t.setA3701(A37Sheet.getCell(4, i).getContents());//????????
				
				a37t.setA3711(A37Sheet.getCell(5, i).getContents());//????????
				a37t.setA3707c(A37Sheet.getCell(6, i).getContents());//????????
				a37t.setA3707a(A37Sheet.getCell(7, i).getContents());//????????
				a37t.setA3707e(A37Sheet.getCell(8, i).getContents());//????????
				a37t.setA3707b(A37Sheet.getCell(9, i).getContents());//????????
				a37t.setA3708(A37Sheet.getCell(10, i).getContents());//????????
				a37t.setA3714(A37Sheet.getCell(11, i).getContents());//????????
				a37s.add(a37t);
			}
			//????????????
			Sheet A36Sheet = rwb.getSheet("????????????");
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
				a36t.setA3604a(A36Sheet.getCell(4, i).getContents());//????
				
				a36t.setA3601(A36Sheet.getCell(5, i).getContents());//????????????
				a36t.setA3607(A36Sheet.getCell(6, i).getContents());//????????
				a36t.setA3627(getCode(A36Sheet.getCell(7, i).getContents(),"ZB18", ""));//????????
				a36t.setA3611(A36Sheet.getCell(8, i).getContents());//??????????????
				a36s.add(a36t);
			}
			
			//????????????
			Sheet A41Sheet = rwb.getSheet("????????????");
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
				a41t.setA1100(A41Sheet.getCell(5, i).getContents());//??????????????
				a11Map.put(A41Sheet.getCell(5, i).getContents(), key);
			}
			//????????????
			Sheet A06Sheet = rwb.getSheet("????????????");
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
				a06t.setA0602(A06Sheet.getCell(4, i).getContents());//????????????
				
				a06t.setA0604(A06Sheet.getCell(5, i).getContents());//????????????
				a06t.setA0607(getCode(A06Sheet.getCell(6, i).getContents(),"ZB24", ""));//????????????
				a06t.setA0611(A06Sheet.getCell(7, i).getContents());//????????????????
				a06s.add(a06t);
			}
			
			//????????
			Sheet A53Sheet = rwb.getSheet("????????");
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
				a53t.setA5304(A53Sheet.getCell(4, i).getContents());//????????
				
				a53t.setA5315(A53Sheet.getCell(5, i).getContents());//????????
				a53t.setA5317(A53Sheet.getCell(6, i).getContents());//????????
				a53t.setA5319(A53Sheet.getCell(7, i).getContents());//????????
				a53t.setA5321(A53Sheet.getCell(8, i).getContents());//????
				a53s.add(a53t);
			}
			
			//??????????
			Sheet A11Sheet = rwb.getSheet("??????????");
			List a11s = new ArrayList();
			for (int i = 0; i < A11Sheet.getRows(); i++) {
				A11temp a11t = new A11temp();
				String key = A11Sheet.getCell(1, i).getContents();
				if(a01Map.get(key)==null){
					continue;
				} else {
					a11t.setA0000(a01Map.get(key).toString());
				}
				a11t.setA1131(A11Sheet.getCell(0, i).getContents());//????????
				a11t.setA1100(A11Sheet.getCell(1, i).getContents());//??????????????
				a11t.setA1101(getCode(A11Sheet.getCell(2, i).getContents(),"ZB29", ""));//????????
				a11t.setA1107(A11Sheet.getCell(3, i).getContents());//????????
				a11t.setA1111(A11Sheet.getCell(4, i).getContents());//????????
				
				a11t.setA1127(getCode(A11Sheet.getCell(5, i).getContents(),"ZB27", ""));//????????????
				a11t.setA1104(getCode(A11Sheet.getCell(6, i).getContents(),"ZB30", ""));//????????????
				a11t.setA1114(A11Sheet.getCell(7, i).getContents());//????????
				a11t.setA1121a(A11Sheet.getCell(8, i).getContents());//????????????
				String a1151 = A11Sheet.getCell(9, i).getContents();
				a11t.setA1151((a1151!=null && !a1151.equals(""))?(a1151.equals("??")?"1":"0"):"");//????????????????????
				a11s.add(a11t);
			}
			//????????
			Sheet ImpSheet = rwb.getSheet("????????");
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
			// ????
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	/**
	 * ????????????
	 * @param a01t
	 */
	private static void dealA01(A01temp a01t) {
		
		
	}

	/**
	 * 
	 * @param codename ????????
	 * @param codetype ????????
	 * @param no ??????????????,?????? "", "2" ,"3"
	 * @return
	 */
	private static String getCode(String codename, String codetype, String no) {
		Object obj = HBUtil.getHBSession().createSQLQuery("select code_value from code_value t where t.code_type ='" +
				codetype + "' and code_name" + no + "='" + codename + "'").uniqueResult();
		return obj!=null ? obj.toString() : "";
	}
	
	/**
	 * 
	 * @param codename ????????
	 * @param codetype ????????
	 * @param no ??????????????,?????? "", "2" ,"3"
	 * @return
	 */
	private static String getName(String codevalue, String codetype, String no) {
		Object obj = HBUtil.getHBSession().createSQLQuery("select code_name" + no + " from code_value t where t.code_type ='" +
				codetype + "' and code_value='" + codevalue + "'").uniqueResult();
		return obj!=null ? obj.toString() : "";
	}

	public static void exportExcel(DataOIDTO dto, List<List> dataList_a, B01 b01, int no, String fileName,
			String passwd) {
		String result = "??????????Excel??????????????";
		// ??????????
		if (StringUtil.isEmpty(fileName)) {
			fileName = "????????";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Map a01Map = new HashMap();
		Map b01Map = new HashMap();
		Map a11Map = new HashMap();
		try {
			// ??????????????????????????????______________________begin
			// HttpServletResponse response=ServletActionContext.getResponse();
			OutputStream os = new FileOutputStream(fileName);// ??????????
			/*response.reset();// ??????????
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			// ??????????????
			response.setContentType("application/msexcel");// ????????????
			// ??????????????????????????????_______________________end
*/
			/** **********1.??????????************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			workbook.setProtected(false);

			/** ************2.??????????????************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);

			/** ************3.????????????????????????????????************ */
			// ????????????
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // ????
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // ????????????
			wcf_center.setAlignment(Alignment.CENTRE); // ????????????
			wcf_center.setWrap(false); // ????????????

			// ????????????
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // ????
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // ????????????
			wcf_left.setAlignment(Alignment.LEFT); // ????????????
			wcf_left.setWrap(false); // ????????????

			// ????????????????Info,??????????????????
			WritableSheet sheetInfo = workbook.createSheet("????????", 0);// parm1:??????????;parm2:????????Excel????????0????
			jxl.SheetSettings sheetsetInfo = sheetInfo.getSettings();
			sheetsetInfo.setProtected(true);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetsetInfo.setPassword(passwd);// ????????
			}
			if(b01 != null){
				sheetInfo.addCell(new Label(0, 0, "????????", wcf_center));
				sheetInfo.addCell(new Label(0, 1, "??????????????", wcf_center));
				sheetInfo.addCell(new Label(0, 2, "????(????)????", wcf_center));
				sheetInfo.addCell(new Label(0, 3, "??????", wcf_center));
				sheetInfo.addCell(new Label(0, 4, "????????", wcf_center));
				sheetInfo.addCell(new Label(0, 5, "????", wcf_center));
				sheetInfo.addCell(new Label(0, 6, "????", wcf_center));
				sheetInfo.addCell(new Label(0, 7, "????????", wcf_center));
				
				sheetInfo.addCell(new Label(1, 0, b01.getB0101(), wcf_left));
				sheetInfo.addCell(new Label(1, 1, b01.getB0111(), wcf_left));
				sheetInfo.addCell(new Label(1, 2, b01.getB0114(), wcf_left));
				sheetInfo.addCell(new Label(1, 3, dto.getLinkpsn(), wcf_left));
				sheetInfo.addCell(new Label(1, 4, dto.getLinktel(), wcf_left));
				sheetInfo.addCell(new Label(1, 5, dto.getRemark(), wcf_left));
				sheetInfo.addCell(new Label(1, 6, no+"", wcf_left));
				sheetInfo.addCell(new Label(1, 7, dto.getCount()+"", wcf_left));
			}
			// ????????????????
			WritableSheet sheet1= workbook.createSheet("????????????", 1);// parm1:??????????;parm2:????????Excel????????0????
			jxl.SheetSettings sheetset1= sheet1.getSettings();
			sheetset1.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset1.setPassword(passwd);// ????????
			}
			//????????
			sheet1.addCell(new Label(0, 0, "????", wcf_center));
			sheet1.addCell(new Label(1, 0, "????", wcf_center));
			sheet1.addCell(new Label(2, 0, "????????", wcf_center));
			sheet1.addCell(new Label(3, 0, "????????", wcf_center));
			sheet1.addCell(new Label(4, 0, "????", wcf_center));
			sheet1.addCell(new Label(5, 0, "??????", wcf_center));
			sheet1.addCell(new Label(6, 0, "????", wcf_center));
			sheet1.addCell(new Label(7, 0, "????????", wcf_center));
			sheet1.addCell(new Label(8, 0, "????????", wcf_center));
			sheet1.addCell(new Label(9, 0, "????????", wcf_center));
			sheet1.addCell(new Label(10, 0, "????????", wcf_center));
			sheet1.addCell(new Label(11, 0, "????????????", wcf_center));
			sheet1.addCell(new Label(12, 0, "????????", wcf_center));
			sheet1.addCell(new Label(13, 0, "????????????????", wcf_center));
			sheet1.addCell(new Label(14, 0, "????????", wcf_center));
			sheet1.addCell(new Label(15, 0, "????????", wcf_center));
			sheet1.addCell(new Label(16, 0, "????????", wcf_center));
			sheet1.addCell(new Label(17, 0, "????????????????", wcf_center));
			sheet1.addCell(new Label(18, 0, "????", wcf_center));
			//????????
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
				// ????id?? ???? ???? ???????? ????????
				a01Map.put(a01.getA0000(), new String[]{a01.getA0101(), getName(a01.getA0104(), "GB2261", ""), a01.getA0107(), a01.getA0184()});
			}
			
			// ????????????????
			WritableSheet sheet2= workbook.createSheet("????????", 2);// parm1:??????????;parm2:????????Excel????????0????
			jxl.SheetSettings sheetset2= sheet2.getSettings();
			sheetset2.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset2.setPassword(passwd);// ????????
			}
			//????????
			sheet2.addCell(new Label(0, 0, "????????", wcf_center));
			sheet2.addCell(new Label(1, 0, "????????", wcf_center));
			sheet2.addCell(new Label(2, 0, "??????????????", wcf_center));
			sheet2.addCell(new Label(3, 0, "????????????", wcf_center));
			sheet2.addCell(new Label(4, 0, "??????????????????", wcf_center));
			sheet2.addCell(new Label(5, 0, "????????", wcf_center));
			sheet2.addCell(new Label(6, 0, "????????", wcf_center));
			sheet2.addCell(new Label(7, 0, "????????????", wcf_center));
			sheet2.addCell(new Label(8, 0, "????????????", wcf_center));
			sheet2.addCell(new Label(9, 0, "????(????)????", wcf_center));
			
			sheet2.addCell(new Label(10, 0, "????????", wcf_center));
			sheet2.addCell(new Label(11, 0, "????????????", wcf_center));
			sheet2.addCell(new Label(12, 0, "????????????", wcf_center));
			sheet2.addCell(new Label(13, 0, "??????????????", wcf_center));
			sheet2.addCell(new Label(14, 0, "??????????????", wcf_center));
			sheet2.addCell(new Label(15, 0, "????????????", wcf_center));
			sheet2.addCell(new Label(16, 0, "????????????????", wcf_center));
			sheet2.addCell(new Label(17, 0, "????????????????", wcf_center));
			sheet2.addCell(new Label(18, 0, "??????????????????", wcf_center));
			sheet2.addCell(new Label(19, 0, "??????????????????", wcf_center));
			
			sheet2.addCell(new Label(20, 0, "??????????", wcf_center));
			sheet2.addCell(new Label(21, 0, "??????????????????????????", wcf_center));
			sheet2.addCell(new Label(22, 0, "??????????????", wcf_center));
			//????????
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
				// ????id?? ???? ????????
				a01Map.put(b01t.getB0111(), new String[]{b01t.getB0101(), b01t.getB0114()});
			}
			
			// ????????????
			WritableSheet sheet3= workbook.createSheet("????????", 3);// parm1:??????????;parm2:????????Excel????????0????
			jxl.SheetSettings sheetset3= sheet3.getSettings();
			sheetset3.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset3.setPassword(passwd);// ????????
			}
			//????????
			sheet3.addCell(new Label(0, 0, "????", wcf_center));
			sheet3.addCell(new Label(1, 0, "????", wcf_center));
			sheet3.addCell(new Label(2, 0, "????????", wcf_center));
			sheet3.addCell(new Label(3, 0, "????????", wcf_center));
			sheet3.addCell(new Label(4, 0, "????????", wcf_center));
			sheet3.addCell(new Label(5, 0, "??????????????", wcf_center));
			sheet3.addCell(new Label(6, 0, "????????", wcf_center));
			sheet3.addCell(new Label(7, 0, "????????", wcf_center));
			sheet3.addCell(new Label(8, 0, "????????", wcf_center));
			sheet3.addCell(new Label(9, 0, "????????", wcf_center));
			
			sheet3.addCell(new Label(10, 0, "????????????", wcf_center));
			sheet3.addCell(new Label(11, 0, "????????", wcf_center));
			sheet3.addCell(new Label(12, 0, "????????", wcf_center));
			sheet3.addCell(new Label(13, 0, "????????", wcf_center));
			sheet3.addCell(new Label(14, 0, "????????????", wcf_center));
			sheet3.addCell(new Label(15, 0, "????????", wcf_center));
			sheet3.addCell(new Label(16, 0, "??????????????", wcf_center));
			sheet3.addCell(new Label(17, 0, "????????", wcf_center));
			sheet3.addCell(new Label(18, 0, "????????????", wcf_center));
			sheet3.addCell(new Label(19, 0, "????????", wcf_center));
			
			sheet3.addCell(new Label(20, 0, "????????", wcf_center));
			sheet3.addCell(new Label(21, 0, "????????", wcf_center));
			sheet3.addCell(new Label(22, 0, "????????", wcf_center));
			sheet3.addCell(new Label(23, 0, "????????", wcf_center));
			sheet3.addCell(new Label(24, 0, "????????", wcf_center));
			sheet3.addCell(new Label(25, 0, "????????", wcf_center));
			sheet3.addCell(new Label(26, 0, "????????", wcf_center));
			sheet3.addCell(new Label(27, 0, "????????", wcf_center));
			sheet3.addCell(new Label(28, 0, "????????????", wcf_center));
			//????????
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
			
			// ????????????????
			WritableSheet sheet4= workbook.createSheet("????????????", 4);
			jxl.SheetSettings sheetset4= sheet4.getSettings();
			sheetset4.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset4.setPassword(passwd);// ????????
			}
			//????????
			sheet4.addCell(new Label(0, 0, "????", wcf_center));
			sheet4.addCell(new Label(1, 0, "????", wcf_center));
			sheet4.addCell(new Label(2, 0, "????????", wcf_center));
			sheet4.addCell(new Label(3, 0, "????????", wcf_center));
			sheet4.addCell(new Label(4, 0, "????", wcf_center));
			sheet4.addCell(new Label(5, 0, "????", wcf_center));
			sheet4.addCell(new Label(6, 0, "????", wcf_center));
			sheet4.addCell(new Label(7, 0, "????????", wcf_center));
			sheet4.addCell(new Label(8, 0, "????????", wcf_center));
			sheet4.addCell(new Label(9, 0, "????????", wcf_center));
			sheet4.addCell(new Label(10, 0, "??????????", wcf_center));
			sheet4.addCell(new Label(11, 0, "????????", wcf_center));
			sheet4.addCell(new Label(12, 0, "????????", wcf_center));
			
			//????????
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
			// ????????
			WritableSheet sheet5= workbook.createSheet("????????", 5);// parm1:??????????;parm2:????????Excel????????0????
			jxl.SheetSettings sheetset5= sheet5.getSettings();
			sheetset5.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset5.setPassword(passwd);// ????????
			}
			//????????
			sheet5.addCell(new Label(0, 0, "????", wcf_center));
			sheet5.addCell(new Label(1, 0, "????", wcf_center));
			sheet5.addCell(new Label(2, 0, "????????", wcf_center));
			sheet5.addCell(new Label(3, 0, "????????", wcf_center));
			sheet5.addCell(new Label(4, 0, "????????", wcf_center));
			sheet5.addCell(new Label(5, 0, "????????????", wcf_center));
			sheet5.addCell(new Label(6, 0, "??????????", wcf_center));
			sheet5.addCell(new Label(7, 0, "??????????????", wcf_center));
			sheet5.addCell(new Label(8, 0, "????????????", wcf_center));
			sheet5.addCell(new Label(9, 0, "????????????", wcf_center));
			//????????
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
			// ????????????
			WritableSheet sheet6= workbook.createSheet("????????????", 6);// parm1:??????????;parm2:????????Excel????????0????
			jxl.SheetSettings sheetset6= sheet6.getSettings();
			sheetset6.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset6.setPassword(passwd);// ????????
			}
			//????????
			sheet6.addCell(new Label(0, 0, "????", wcf_center));
			sheet6.addCell(new Label(1, 0, "????", wcf_center));
			sheet6.addCell(new Label(2, 0, "????????", wcf_center));
			sheet6.addCell(new Label(3, 0, "????????", wcf_center));
			sheet6.addCell(new Label(4, 0, "????????????", wcf_center));
			sheet6.addCell(new Label(5, 0, "????????", wcf_center));
			sheet6.addCell(new Label(6, 0, "????????", wcf_center));
			sheet6.addCell(new Label(7, 0, "????", wcf_center));
			//????????
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
			//????????????
			WritableSheet sheet7= workbook.createSheet("????????????", 7);
			jxl.SheetSettings sheetset7= sheet7.getSettings();
			sheetset7.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset7.setPassword(passwd);// ????????
			}
			//????????
			sheet7.addCell(new Label(0, 0, "????", wcf_center));
			sheet7.addCell(new Label(1, 0, "????", wcf_center));
			sheet7.addCell(new Label(2, 0, "????????", wcf_center));
			sheet7.addCell(new Label(3, 0, "????????", wcf_center));
			sheet7.addCell(new Label(4, 0, "??????????????", wcf_center));
			sheet7.addCell(new Label(5, 0, "????????", wcf_center));
			sheet7.addCell(new Label(6, 0, "??????????", wcf_center));
			sheet7.addCell(new Label(7, 0, "??????????", wcf_center));
			sheet7.addCell(new Label(8, 0, "??????????", wcf_center));
			sheet7.addCell(new Label(9, 0, "??????????????????", wcf_center));
			sheet7.addCell(new Label(10, 0, "??????????????", wcf_center));
			
			//????????
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
			//????????
			WritableSheet sheet8= workbook.createSheet("????????", 8);
			jxl.SheetSettings sheetset8= sheet8.getSettings();
			sheetset8.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset8.setPassword(passwd);// ????????
			}
			//????????
			sheet8.addCell(new Label(0, 0, "????", wcf_center));
			sheet8.addCell(new Label(1, 0, "????", wcf_center));
			sheet8.addCell(new Label(2, 0, "????????", wcf_center));
			sheet8.addCell(new Label(3, 0, "????????", wcf_center));
			sheet8.addCell(new Label(4, 0, "????????", wcf_center));
			sheet8.addCell(new Label(5, 0, "????????", wcf_center));
			
			//????????
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
			
			// ????????????
			WritableSheet sheet9= workbook.createSheet("????????", 9);
			jxl.SheetSettings sheetset9= sheet9.getSettings();
			sheetset9.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset9.setPassword(passwd);// ????????
			}
			//????????
			sheet9.addCell(new Label(0, 0, "????", wcf_center));
			sheet9.addCell(new Label(1, 0, "????", wcf_center));
			sheet9.addCell(new Label(2, 0, "????????", wcf_center));
			sheet9.addCell(new Label(3, 0, "????????", wcf_center));
			sheet9.addCell(new Label(4, 0, "????????????", wcf_center));
			sheet9.addCell(new Label(5, 0, "????????", wcf_center));
			sheet9.addCell(new Label(6, 0, "????????", wcf_center));
			sheet9.addCell(new Label(7, 0, "????????????", wcf_center));
			sheet9.addCell(new Label(8, 0, "????????", wcf_center));
			sheet9.addCell(new Label(9, 0, "????????", wcf_center));
			sheet9.addCell(new Label(10, 0, "????????????", wcf_center));
			sheet9.addCell(new Label(11, 0, "??????????????", wcf_center));
			
			//????????
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
			//????????????
			WritableSheet sheet10= workbook.createSheet("????????????", 10);
			jxl.SheetSettings sheetset910= sheet10.getSettings();
			sheetset910.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset910.setPassword(passwd);// ????????
			}
			//????????
			sheet10.addCell(new Label(0, 0, "????", wcf_center));
			sheet10.addCell(new Label(1, 0, "????", wcf_center));
			sheet10.addCell(new Label(2, 0, "????????", wcf_center));
			sheet10.addCell(new Label(3, 0, "????????", wcf_center));
			sheet10.addCell(new Label(4, 0, "????????", wcf_center));
			sheet10.addCell(new Label(5, 0, "????????", wcf_center));
			sheet10.addCell(new Label(6, 0, "????????", wcf_center));
			sheet10.addCell(new Label(7, 0, "????????", wcf_center));
			sheet10.addCell(new Label(8, 0, "????????", wcf_center));
			sheet10.addCell(new Label(9, 0, "????????", wcf_center));
			sheet10.addCell(new Label(10, 0, "????????", wcf_center));
			sheet10.addCell(new Label(11, 0, "????????", wcf_center));
			
			//????????
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
			//????????????
			WritableSheet sheet11= workbook.createSheet("????????????", 11);
			jxl.SheetSettings sheetset11= sheet11.getSettings();
			sheetset11.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset11.setPassword(passwd);// ????????
			}
			//????????
			sheet11.addCell(new Label(0, 0, "????", wcf_center));
			sheet11.addCell(new Label(1, 0, "????", wcf_center));
			sheet11.addCell(new Label(2, 0, "????????", wcf_center));
			sheet11.addCell(new Label(3, 0, "????????", wcf_center));
			sheet11.addCell(new Label(4, 0, "????", wcf_center));
			sheet11.addCell(new Label(5, 0, "????????????", wcf_center));
			sheet11.addCell(new Label(6, 0, "????????", wcf_center));
			sheet11.addCell(new Label(7, 0, "????????", wcf_center));
			sheet11.addCell(new Label(8, 0, "??????????????", wcf_center));
			
			//????????
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
			//??????????
			WritableSheet sheet12= workbook.createSheet("??????????", 12);
			jxl.SheetSettings sheetset12= sheet12.getSettings();
			sheetset12.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset12.setPassword(passwd);// ????????
			}
			//????????
			sheet12.addCell(new Label(0, 0, "????????", wcf_center));
			sheet12.addCell(new Label(1, 0, "??????????????", wcf_center));
			sheet12.addCell(new Label(2, 0, "????????", wcf_center));
			sheet12.addCell(new Label(3, 0, "????????", wcf_center));
			sheet12.addCell(new Label(4, 0, "????????", wcf_center));
			sheet12.addCell(new Label(5, 0, "????????????", wcf_center));
			sheet12.addCell(new Label(6, 0, "????????????", wcf_center));
			sheet12.addCell(new Label(7, 0, "????????", wcf_center));
			sheet12.addCell(new Label(8, 0, "????????????", wcf_center));
			sheet12.addCell(new Label(9, 0, "????????????????????", wcf_center));
			//????????
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
						(a11.getA1151().equals("1")?"??":"??"):"" , wcf_left));
				a11Map.put(a11.getA1100(), a11.getA1131());
			}
			//????????????
			WritableSheet sheet13= workbook.createSheet("????????????", 13);
			jxl.SheetSettings sheetset13= sheet13.getSettings();
			sheetset13.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset13.setPassword(passwd);// ????????
			}
			//????????
			sheet13.addCell(new Label(0, 0, "????", wcf_center));
			sheet13.addCell(new Label(1, 0, "????", wcf_center));
			sheet13.addCell(new Label(2, 0, "????????", wcf_center));
			sheet13.addCell(new Label(3, 0, "????????", wcf_center));
			sheet13.addCell(new Label(4, 0, "????????", wcf_center));
			sheet13.addCell(new Label(5, 0, "??????????????", wcf_center));
			//????????
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
			//????????????
			WritableSheet sheet14= workbook.createSheet("????????????", 14);
			jxl.SheetSettings sheetset14= sheet14.getSettings();
			sheetset14.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset14.setPassword(passwd);// ????????
			}
			//????????
			sheet14.addCell(new Label(0, 0, "????", wcf_center));
			sheet14.addCell(new Label(1, 0, "????", wcf_center));
			sheet14.addCell(new Label(2, 0, "????????", wcf_center));
			sheet14.addCell(new Label(3, 0, "????????", wcf_center));
			sheet14.addCell(new Label(4, 0, "????????????", wcf_center));
			sheet14.addCell(new Label(5, 0, "????????????", wcf_center));
			sheet14.addCell(new Label(6, 0, "????????????", wcf_center));
			sheet14.addCell(new Label(7, 0, "????????????????", wcf_center));
			//????????
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
			//????????
			WritableSheet sheet15= workbook.createSheet("????????", 15);
			jxl.SheetSettings sheetset15= sheet15.getSettings();
			sheetset15.setProtected(false);// ????????????
			if (!StringUtil.isEmpty(passwd)) {
				sheetset15.setPassword(passwd);// ????????
			}
			//????????
			sheet15.addCell(new Label(0, 0, "????", wcf_center));
			sheet15.addCell(new Label(1, 0, "????", wcf_center));
			sheet15.addCell(new Label(2, 0, "????????", wcf_center));
			sheet15.addCell(new Label(3, 0, "????????", wcf_center));
			sheet15.addCell(new Label(4, 0, "????????", wcf_center));
			sheet15.addCell(new Label(5, 0, "????????", wcf_center));
			sheet15.addCell(new Label(6, 0, "????????", wcf_center));
			sheet15.addCell(new Label(7, 0, "????????", wcf_center));
			sheet15.addCell(new Label(8, 0, "????", wcf_center));
			
			//????????
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
			/** **********7.??????????????????????EXCEL??????******** */
			workbook.write();
			/** *********8.????????************* */
			workbook.close();

		} catch (Exception e) {
			result = "??????????Excel????????????????????" + e.toString();
			CommonQueryBS.systemOut(result);
			e.printStackTrace();
		}
		
	}

}