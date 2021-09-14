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
 * Excel����������
 * Ӧ��API��ַ��http://jexcelapi.sourceforge.net/resources/javadocs/2_6_10/docs/
 * 
 * @author mengl
 * 
 */
public class CustomExcelUtil {

	private static String pattern = "yyyyMMdd HH:mm:ss"; // ͳһ�����������ڽ�����ʽ
	
	/**
	 * ��У�鷽����Ϣ�ŵ�Map��
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
				throw new AppException("��������������Ϣ��δ�ҵ�Ҫ�����ķ�����Ϣ����������ID-" + vsc001Exp);
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
	 * Excel��������
	 * 
	 * Ӧ��API��ַ��http://jexcelapi.sourceforge.net/resources/javadocs/2_6_10/docs/
	 * 
	 * @author mengl
	 * @param response
	 *            HttpServletResponse����
	 * @param listContent
	 *            ����Excelʹ�õĶ��� ע��Ҫ��ӦList<List<Object>> listContent
	 *            ��Object����һ�£���Ȼ�б�����Ӧ����
	 * @param fileName
	 *            �����ļ��������Զ�ƴ�ӵ�ǰ���ڣ�
	 * @param passwd
	 *            Excel��������
	 * @return
	 */

	public final static void exportExcel(HttpServletResponse response,
			Map<String,List<List<Object>>> listContentMap, String passwd) {
		String fileName = "";
		String result = "ϵͳ��ʾ��Excel�ļ������ɹ���";
		int count = 0 ;
		
		// ������������Ա�򿪱���Ի���______________________begin
		OutputStream os ;

		try {
			Set<String> keySet = listContentMap.keySet();
			
			os = response.getOutputStream();// ȡ�������
			response.reset();// ��������
			
			//1.�����ļ�ֱ���������
			if(listContentMap.size()==1){
				String key = keySet.iterator().next();
				if (!StringUtil.isEmpty(key)) {
					fileName = key;
				}else{
					fileName = "�����ļ�";
				}
				fileName = fileName.replace(".xls", "") + "_"
						+ DateUtil.toString(new Date(), "yyyyMMddHHmmss") + ".xls";	
				
				response.setHeader("Content-disposition", "attachment; filename="
						+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
				
				// ������������Ա�򿪱���Ի���
				// �趨����ļ�ͷ
				response.setContentType("application/msexcel");// �����������
				WritableWorkbook workbook = Workbook.createWorkbook(os);
				writeWorkBook(workbook,listContentMap.get(key),passwd);
			}else{
			//2.����ļ������ɵ���������ѹ����ͬһ���ļ��к��������
				String zipTempDir = ExpRar.expFile();												//��ѹ���ļ�������ʱ�ļ���·��
				String zipFileName = "�����ļ�"+DateUtil.toString(new Date(), "yyyyMMddHHmmss")+".zip";//ѹ���ļ�����
				String zipFileDir = ExpRar.expFile();												//ѹ���ļ�Ŀ¼
				response.setHeader("Content-disposition", "attachment; filename="
						+ new String(zipFileName.getBytes("GB2312"), "ISO8859-1"));					// �趨����ļ�ͷ
				response.setContentType("application/msexcel");										// �����������
				
				File zipDirFile = new File(zipTempDir); 
				zipDirFile.mkdirs();
				
				CommonQueryBS.systemOut(zipTempDir);
				//2.1 ���ļ�ѭ��д��zipDirĿ¼
				for(String key :keySet){
					count++;
					if (!StringUtil.isEmpty(key)) {
						fileName = key;
					}else{
						fileName = "�����ļ�";
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
				//2.2 ѹ��zipTempDir�ļ���
				ZipUtil.compress(zipTempDir, zipFileDir+zipFileName, "", "");
				//2.3 ����
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
					//�쳣������
				}
			}

		} catch (Exception e) {
			CommonQueryBS.systemOut(result);
			e.printStackTrace();
			result = "ϵͳ��ʾ��Excel�ļ�����ʧ�ܣ�ԭ��" + e.toString();
		}
	}

	/**
	 * д��һ��Excel�ļ�
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
		
		/** **********1.����������************ */
//		WritableWorkbook workbook = Workbook.createWorkbook(os);
		if(workbook==null){
			throw new AppException("����Excel����������Ϊ�գ�");
		}
		workbook.setProtected(true);

		/** ************2.���õ�Ԫ������************** */
		WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
		WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD);

		/** ************3.�����������ֵ�Ԫ����ʽ������************ */
		// ���ڱ������
		WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
		wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // ����
		wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // ���ִ�ֱ����
		wcf_center.setAlignment(Alignment.CENTRE); // ����ˮƽ����
		wcf_center.setWrap(false); // �����Ƿ���

		// �������ľ���
		WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
		wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // ����
		wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // ���ִ�ֱ����
		wcf_left.setAlignment(Alignment.LEFT); // ����ˮƽ����
		wcf_left.setWrap(false); // �����Ƿ���

		// ������һ�����Info,���������Ϣ����Ϣ
		WritableSheet sheetInfo = workbook.createSheet("Info", 0);// parm1:����������;parm2:��������Excel��λ�ô�0��ʼ
		jxl.SheetSettings sheetsetInfo = sheetInfo.getSettings();
		sheetsetInfo.setProtected(true);// �趨����״̬
		if (!StringUtil.isEmpty(passwd)) {
			sheetsetInfo.setPassword(passwd);// �趨����
		}

		for (int i = 0; listContent != null && i < listContent.size(); i++) {
			// �趨sheet������Ϊʵ��������ȫ�޶��������������������� ��Sheet+��š�
			Object sheetMappingObj = listContent.get(i).get(0);
			String sheetName = "";
			if (sheetMappingObj != null) {
				sheetName = sheetMappingObj.getClass().getSimpleName();// ����
				// sheetName = sheetMappingObj.getClass().getName();//ȫ�޶���
				// ��һ�����Info:��һ�п�ʼ��ÿ������Ϊsheet��Ӧʵ���ȫ�޶���
				sheetInfo.addCell(new Label(0, i, sheetMappingObj
						.getClass().getName(), wcf_left));
			}

			/** **********4.����������************ */
			// ÿ��List<Object>����һ��������
			WritableSheet sheet = workbook.createSheet(StringUtil
					.isEmpty(sheetName) ? "Sheet" + (i + 2) : sheetName,
					(i + 1));// parm1:����������;parm2:��������Excel��λ�ô�0��ʼ
			/** **********�����ݺ��ӡ��Ĭ��Ϊ�ݴ򣩡���ӡֽ***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(true);// �趨����״̬
			if (!StringUtil.isEmpty(passwd)) {
				sheetset.setPassword(passwd);// �趨����
			}

			/** ***************5.������EXCEL��ͷ����⣬��ʱʡ��********************* */
			// sheet.mergeCells(0, 0, colWidth, 0);
			// sheet.addCell(new Label(0, 0, "XX����", wcf_center));
			/**
			 * ***************6.������EXCEL��һ���б��⣬��ʱʡ�ԣ�����ͨ����ֵ �� ���䷽����ȡ����
			 * �趨��*********************
			 */
			// ע��Ҫ��ӦList��Object����һ�£���Ȼ�б�����Ӧ����
			Field[] titles = sheetMappingObj.getClass().getDeclaredFields();
			for (int index = 0; titles != null && index < titles.length; index++) {
				sheet.addCell(new Label(index, 0, titles[index].getName(),
						wcf_center));
			}

			/** ***************6.������EXCEL��������********************* */
			Field[] fields = null;
			int z = 1;// �к�,0��Ϊ�����У�û�б������0��ʼд����
			for (Object obj : listContent.get(i)) {
				if (!sheetMappingObj.getClass().getName()
						.equals(obj.getClass().getName())) {
					throw new AppException(
							"����Excel�쳣!�쳣��Ϣ��List<List<Object>> listContent ������List<Object>ͬһ��List��Ų�����ͬ�࣡");
				}

				fields = obj.getClass().getDeclaredFields();
				int j = 0;// �к�
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

		/** **********7.�����ϻ����е�����д��EXCEL�ļ���******** */
		workbook.write();
		/** *********8.�ر��ļ�************* */
		workbook.close();
	}
	
	/**
	 * Excel���뷽��
	 * 
	 * @author mengl
	 * @param item �ϴ����ļ�
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
				// 1. �����ȡ��ʵ��
				
				Class classSheet = Class.forName(entryMap.get(sheetName));// ͨ��ȫ�޶�����ȡ����ʵ���Class����
				Sheet st = rwb.getSheet(sheetName);
				System.out.println("Sheet:"+st.getName());
				int rs = st.getColumns();
				int rows = st.getRows();

				List<String> listField = new TreeList(); // �����У���Ӧʵ���ֶ�
				// ��ȡ�����У��ֶ���
				for (int i = 0; i < rs; i++) {// ��
					Cell cell = st.getCell(i, 0);
					listField.add(cell.getContents());
				}
				for (int k = 1; k < rows; k++) {// ��
					Object obj = classSheet.newInstance();
					for (int i = 0; i < rs; i++) {// ��
						// 2. ��ȡ����
						String fieldName = listField.get(i);
						Field field = classSheet.getDeclaredField(fieldName);
		
						// 3. �趨���Կ��Է��ʣ���ʹprivate���Σ�
						field.setAccessible(true);

						Cell c00 = st.getCell(i, k);
						// ͨ�õĻ�ȡcellֵ�ķ�ʽ,�����ַ���
						String strc00 = c00.getContents();
						
						// 4. ��ȡʵ���ֶ���������
						Class typeClass = field.getType();
						
						// 5. ��ȡ�����͵�String�����Ĺ��췽��
						Constructor con = typeClass.getConstructor(String.class);

						// 6. ��ȡ���Զ���ֵ
						Object fieldObj = null;
						if (!StringUtil.isEmpty(strc00)) {
							if (field
									.getGenericType()
									.toString()
									.substring(
											field.getGenericType().toString()
													.lastIndexOf(".") + 1)
									.equals("Date")) {
								// Date������Ҫ����
								fieldObj = sdf.parse(strc00);
							} else {
								// ͨ����ȡ��Ӧ���͵�String���͹��췽�����ɶ�Ӧ���͵�����ֵ
								fieldObj = con.newInstance(strc00);
							}
						}
						// 7. Ϊ����õ���ʵ����ֵ
						field.set(obj, fieldObj);

						// ���cell��������ֵ�ķ�ʽ
						// if (c00.getType() == CellType.LABEL) {
						// LabelCell labelc00 = (LabelCell) c00;
						// strc00 = labelc00.getString();
						// }
						// excel ����Ϊʱ�����ʹ���;
						// if (c00.getType() == CellType.DATE) {
						// DateCell dc = (DateCell) c00;
						// strc00 = sdf.format(dc.getDate());
						// }

					}
					listObj.add(obj);
				}
				list.add(listObj);
			}

			// �ر�
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
	

}