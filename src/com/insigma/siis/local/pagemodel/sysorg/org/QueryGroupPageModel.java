package com.insigma.siis.local.pagemodel.sysorg.org;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fr.third.org.apache.poi.hssf.usermodel.HSSFCellStyle;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class QueryGroupPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		//this.setNextEventName("queryGroupByInfo.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �б�
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("queryGroupByInfo.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		try {
			String sql = "select b.* from b01 b,competence_userdept c where 1=1 "
					+ "and b.b0111 !='001.001' ";//����㲻��ʾ
			this.pageQuery(returnSBSql(sql),"SQL", start, limit); 
			//this.pageQuery(sql, "SQL", start, 20);
        	return EventRtnType.SPE_SUCCESS;
			
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.SPE_SUCCESS;
		}
	}
	
	public String returnSBSql(String sql) throws RadowException{
		
		B01 b01 = new B01();
		this.copyElementsValueToObj(b01, this);
		//System.out.println(b01.toString());
		
		if(b01.getB0101()!=null && !b01.getB0101().trim().equals("")){
			sql+="and b.b0101 like '%"+StringEscapeUtils.escapeSql(b01.getB0101().trim())+"%' ";
		}
		if(b01.getB0104()!=null && !b01.getB0104().trim().equals("")){
			sql+="and b.b0104 like '%"+StringEscapeUtils.escapeSql(b01.getB0104().trim())+"%' ";
		}
		if(b01.getB0107()!=null && !b01.getB0107().trim().equals("")){
			sql+="and b.b0107='"+StringEscapeUtils.escapeSql(b01.getB0107().trim())+"' ";
		}
		if(b01.getB0114()!=null && !b01.getB0114().trim().equals("")){
			sql+="and b.b0114 like'%"+StringEscapeUtils.escapeSql(b01.getB0114().trim())+"%' ";
		}
		if(b01.getB0117()!=null && !b01.getB0117().trim().equals("")){
			sql+="and b.b0117='"+StringEscapeUtils.escapeSql(b01.getB0117().trim())+"' ";
		}
		if(b01.getB0124()!=null && !b01.getB0124().trim().equals("")){
			sql+="and b.b0124='"+StringEscapeUtils.escapeSql(b01.getB0124().trim())+"' ";
		}
		if(b01.getB0127()!=null && !b01.getB0127().trim().equals("")){
			sql+="and b.b0127='"+StringEscapeUtils.escapeSql(b01.getB0127().trim())+"' ";
		}
		if(b01.getB0131()!=null && !b01.getB0131().trim().equals("")){
			sql+="and b.b0131='"+StringEscapeUtils.escapeSql(b01.getB0131().trim())+"' ";
		}
		if(b01.getB0194()!=null && !b01.getB0194().trim().equals("")){
			sql+="and b.b0194='"+StringEscapeUtils.escapeSql(b01.getB0194().trim())+"' ";
		}
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		sql +="and c.b0111=b.b0111 and c.userid='"+cueUserid+"'";
		return sql;
	}
	
	/**
	 * ����
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("exportExcel")
	public int exportExcel() throws RadowException{
		List<HashMap<String, Object>> list=new LinkedList<HashMap<String, Object>>();
		//List<String> list2=new LinkedList<String>();
		try {
			PageElement pe = this.getPageElement("queryGroupByInfo");
			List<HashMap<String, Object>> selectList = pe.getValueList();//��ѯ��Ա�б�
			for (HashMap<String, Object> hm : selectList) {
				if(hm.get("personcheck")!=null&&!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
					list.add(hm);
				}/*else{
					//list2.add((hm.get("b0111")+"").trim());
				}*/
			}
			if(list==null||list.size()<1){//С��0˵��ǰ̨û��ѡ���κμ�¼
				String sql = "select b.b0111 "//b01 ����
						+ " from b01 b,competence_userdept c where 1=1 "
						+ "and b.b0111 !='001.001' ";//����㲻��ʾ
				sql= returnSBSql(sql);
				list=new CommQuery().getListBySQL(sql);//����������ѯ����������
				//list = list2;
			}
			//����excel
			String path = exportSelect(list);
			this.getExecuteSG().addExecuteCode("getDown('"+path+"');");
			return EventRtnType.NORMAL_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("�ļ�����ʧ��");
			return EventRtnType.FAILD;
		}
		
	}
	//����excel
	private String exportSelect(List<HashMap<String, Object>> list) throws IOException {
		OutputStream os = null;
		Workbook workbook = null;
		try{
			workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("��λ�ļ�");
			//���ñ���Ŀ�
			sheet.setColumnWidth(0, 6 * 256);
			sheet.setColumnWidth(1, 25 * 256);
			sheet.setColumnWidth(2, 50 * 256);
			sheet.setColumnWidth(3, 50 * 256);
			sheet.setColumnWidth(4, 20 * 256);
			
			//������ʽ
			CellStyle styleThin = workbook.createCellStyle();
			styleThin.setBorderLeft(CellStyle.BORDER_THIN);
			styleThin.setBorderRight(CellStyle.BORDER_THIN);
			styleThin.setBorderBottom(CellStyle.BORDER_THIN);
			styleThin.setBorderTop(CellStyle.BORDER_THIN);
			styleThin.setAlignment(HSSFCellStyle.ALIGN_CENTER);//����
			//��������
			Font headFont = workbook.createFont();
			headFont.setFontName("����");
			headFont.setFontHeightInPoints((short) 13);
			styleThin.setFont(headFont);
			//������ʽ
			CellStyle styleThick = workbook.createCellStyle();
			styleThick.setBorderLeft(CellStyle.BORDER_THIN);
			styleThick.setBorderRight(CellStyle.BORDER_THIN);
			styleThick.setBorderBottom(CellStyle.BORDER_THIN);
			styleThick.setBorderTop(CellStyle.BORDER_THIN);
			styleThick.setAlignment(HSSFCellStyle.ALIGN_CENTER);//����
			//������
			Row row = sheet.createRow(0);
			//���ñ���ĸ߶�
			row.setHeightInPoints(19);
			
			Cell cell = row.createCell(0);
			cell.setCellValue("���");
			cell.setCellStyle(styleThin);
			cell = row.createCell(1);
			cell.setCellValue("��������");
			cell.setCellStyle(styleThin);
			cell = row.createCell(2);
			cell.setCellValue("��������");
			cell.setCellStyle(styleThin);
			cell = row.createCell(3);
			cell.setCellValue("�ϼ���������");
			cell.setCellStyle(styleThin);
			cell = row.createCell(4);
			cell.setCellValue("��������");
			cell.setCellStyle(styleThin);
			String[] type = {"","���˵�λ","�������","��������"};
			String groupID = "";
			B01 b01 = new B01();
			for(int i=0;i<list.size();i++){
				groupID = (String)list.get(i).get("b0111");
				row = sheet.createRow(i+1);
				b01 = (B01) HBUtil.getHBSession().get(B01.class, groupID);
				if(b01==null){
					throw new IOException("�����쳣!");
				}
				cell = row.createCell(0);
				cell.setCellValue(i+1);
				cell.setCellStyle(styleThick);
				cell = row.createCell(1);
				cell.setCellValue(b01.getB0114()==null?"":b01.getB0114());
				cell.setCellStyle(styleThick);
				cell = row.createCell(2);
				cell.setCellValue(b01.getB0101());
				cell.setCellStyle(styleThick);
				cell = row.createCell(3);
				cell.setCellValue(((B01) HBUtil.getHBSession().get(B01.class, b01.getB0121())).getB0101());
				cell.setCellStyle(styleThick);
				cell = row.createCell(4);
				cell.setCellValue(type[Integer.parseInt(b01.getB0194())]);
				cell.setCellStyle(styleThick);
			}
			Date now = new Date();
			DateFormat d1 = DateFormat.getDateInstance();
			String timeNmae = d1.format(now);
			String sourcePath = AppConfig.HZB_PATH + "/temp/excel/"+UUID.randomUUID().toString();
			File file = new File(sourcePath);
			if(!file.exists()){
				file.mkdirs();
			}
			file = new File(sourcePath+"/export_"+timeNmae+".xlsx");
			os = new FileOutputStream(file);
			os.flush();
			workbook.write(os);
			os.close();
			workbook.close();
			return sourcePath+"/export_"+timeNmae+".xlsx";
		}catch(Exception e){
			if(os!=null){
				os.close();
			}
			if(workbook!=null){
				workbook.close();
			}
			throw new IOException("����ʧ��!");
		}
		
		
	}
	
	//˫���б�鿴�����ø�ҳ���޸Ĺ��ܣ��鿴������Ϣ
	@PageEvent("rowDoubleWin")
	public int rowDoubleWin(String groupid) throws RadowException {
		this.getExecuteSG().addExecuteCode("window.realParent.radow.doEvent('rowDoubleWin','"+groupid+"')");

		/*String pardata="2,"+groupid;
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('rowDoubleWin','pages.sysorg.org.UpdateSysOrg','������Ϣ�޸�ҳ��',860,510,'"+pardata+"','"+ctxPath+"');");*/
		return EventRtnType.NORMAL_SUCCESS;
	}
}


