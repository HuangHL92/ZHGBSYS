package com.insigma.siis.local.pagemodel.audit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.audit.AuditBatch;
import com.insigma.siis.local.business.audit.AuditPerson;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.util.DateUtil;

public class AuditFeedbackPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}

	@PageEvent("initX")
	public int initX() {
		this.setNextEventName("unitreGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("unitreGrid.dogridquery")
	public int policySetgrid(int start,int limit) throws RadowException{
		
		StringBuffer sb = new StringBuffer("select a.*,(select username from smt_user where userid=a.CREATE_BY) createbyname from AUDIT_BATCH a" );
		
		System.out.println("batchsql:"+sb.toString());
		this.pageQuery(sb.toString(),"SQL",  start, limit);
	    return EventRtnType.SPE_SUCCESS;
	}
	
	
	/*
	 * @PageEvent("unitreGrid.rowdbclick")
	 * 
	 * @GridDataRange public int unitreGridrowdbclick() throws RadowException{
	 * //打开窗口的实例 Map<String,Object> map =
	 * (Map<String,Object>)this.getPageElement("unitreGrid").getValueList().get(this
	 * .getPageElement("unitreGrid").getCueRowIndex()); String
	 * id=(String)map.get("oid");
	 * 
	 * this.getExecuteSG().addExecuteCode("openInfo('"+id+"')"); return
	 * EventRtnType.NORMAL_SUCCESS; }
	 */
	
	
	@PageEvent("expExcel")
	public int expExcel(String id) throws AppException, RadowException {
		//导出个人excel
		HBSession sess = HBUtil.getHBSession();
		List<AuditPerson> list=sess.createSQLQuery("select * from audit_person where audit_batch='"+id+"'").addEntity(AuditPerson.class).list();
		if(list==null||list.size()==0) {
			this.setMainMessage("没有可导出的人员");
			return EventRtnType.FAILD;
		}
		String expfile=createExcel(id,list);
		expfile = expfile.replace("\\", "/");
		this.getPageElement("path").setValue(expfile);
		this.getExecuteSG().addExecuteCode("exp()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private String createExcel(String id,List<AuditPerson> list ) throws AppException {
		HBSession sess = HBUtil.getHBSession();
		FileInputStream fis = null;
		Workbook workbook = null;
		FileOutputStream fos = null;
		String filepath="";
		String expFile = ExpRar.expFile();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String deptname=(String) sess.createSQLQuery("select rolename from  smt_role where roleid='"+user.getDept()+"'").uniqueResult();
		deptname="省纪委省监委";
		AuditBatch ab=(AuditBatch) sess.createSQLQuery("select * from audit_batch where oid='"+id+"'").addEntity(AuditBatch.class).uniqueResult();
		// 获取模板路径
		String mbpath = this.request.getSession().getServletContext().getRealPath("/") + "pages/audit/导入模板/"+deptname+".xlsx";
		try {
			File file = new File(mbpath);
			fis = new FileInputStream(mbpath);
			workbook = getWorkbok(fis, file);
			Sheet sheet = workbook.getSheetAt(0);
			Row csrow = sheet.getRow(1);
			
			CellStyle cs = workbook.createCellStyle();
			cs.cloneStyleFrom(csrow.getCell(0).getCellStyle());
			for(int i=0;i<list.size();i++) {
				Row row=sheet.createRow(i+2);
				AuditPerson ap=list.get(i);
				row.createCell(0).setCellValue(ap.getA0101());
				row.createCell(1).setCellValue(ap.getA0184());
				row.createCell(2).setCellValue(ap.getA0192a());
				for(int j=0;j<FormImportAction.colMap.get(deptname);j++) {
					if(j<=2) {
						row.getCell(j).setCellStyle(cs);
					}else {
						row.createCell(j).setCellStyle(cs);
					}
					
				}
			}
			String time=DateUtil.getCurrentDate();
			CodeValue cv=RuleSqlListBS.getCodeValue("AUDIT_TYPE", ab.getBatchType());
			filepath=expFile + deptname+ab.getAuditBatchNo()+cv.getCodeName()+time+".xlsx";
			fos = new FileOutputStream(filepath);
			workbook.write(fos);
		}catch (Exception e) {
			e.printStackTrace();
			throw new AppException("生成EXCEL出错");

		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return filepath;
	}

	public static Workbook getWorkbok(InputStream in, File file) throws IOException {
		Workbook wb = null;
		if (file.getName().endsWith("xls")) { // Excel 2003
			wb = new HSSFWorkbook(in);
		} else if (file.getName().endsWith("xlsx")) { // Excel 2007/2010
			wb = new XSSFWorkbook(in);
		}
		in.close();
		return wb;
	}
}
