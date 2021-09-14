package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Vector;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A99Z1;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.DateUtil;

public class ImpA15PageModel extends PageModel {
	private LogUtil applog = new LogUtil();
	
	// 页面初始化
	@Override
	public int doInit() throws RadowException {
		this.controlButton();
		this.getPageElement("batchid").setValue(UUID.randomUUID().toString());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start, int limit) throws RadowException {
		String batchid=this.getPageElement("batchid").getValue();
		String sql = "/*"+Math.random()+"*/ select * from a15report where batchid='"+batchid+"' order by ORDERNUM";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("deleteReport")
	public int deleteReport(String id) throws AppException {
		String sql="delete from a15report where id='"+id+"' ";
		HBUtil.executeUpdate(sql);
		this.setMainMessage("删除成功");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("cover")
	public int cover(String str) throws AppException, RadowException {
		HBSession sess = HBUtil.getHBSession();
		try {
		String[] strs=str.split(":");
		String id=strs[0];
		String a0000=strs[1];
		String a1517=strs[2];
		String a1521=strs[3];
		String name=strs[4];
		List<String>  list2= sess.createSQLQuery("select a1500 from a15 where a0000='"+a0000+"' and a1521='"+a1521+"'").list();
		String a1500=list2.get(0);
		A15 a15=(A15) sess.get(A15.class, a1500);
		A15 a15_old = new A15();
		PropertyUtils.copyProperties(a15_old, a15);
		a15.setA1517(a1517);
		//修改日志记录
		applog.createLog("3152", "A15", a0000, name, "修改记录", new Map2Temp().getLogInfo(a15_old, a15));
		sess.update(a15);
		
		String sql="delete from a15report where id='"+id+"' ";
		HBUtil.executeUpdate(sql);
		} catch (Exception e) {
			sess.getTransaction().rollback();
			e.printStackTrace();
			this.setMainMessage("覆盖失败:"+e);
		}
		this.setMainMessage("覆盖成功");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("impAgain.onclick")
	public int impAgain() throws RadowException, AppException {
		String batchid=this.getPageElement("batchid").getValue();
		HBSession sess=HBUtil.getHBSession();
		List<Object[]> list=sess.createSQLQuery("select a0000,a1517,a1521 from a15report where batchid='"+batchid+"'").list();
		for(Object[] a15report:list) {
			String a0000=a15report[0].toString();
			String a1517=a15report[1].toString();
			String a1521=a15report[2].toString();
			List<String>  list2= sess.createSQLQuery("select a1500 from a15 where a0000='"+a0000+"' and a1521='"+a1521+"'").list();
			if(list2.size()<1) {
				//没有记录  直接 插入
				A15 a15=new A15();
				a15.setA0000(a0000);
				a15.setA1500(UUID.randomUUID().toString());
				a15.setA1521(a1521);
				a15.setA1517(a1517);
				a15.setA1527("3");
				sess.save(a15);
			}else {
				String a1500=list2.get(0);
				//有记录 用更新
				A15 a15=(A15) sess.get(A15.class, a1500);
				a15.setA1517(a1517);
				sess.update(a15);
			}
		}
		sess.flush();
		String sql="delete from a15report where batchid='"+batchid+"' ";
		HBUtil.executeUpdate(sql);
		this.setMainMessage("导入成功");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("expExcel.onclick")
	public int expExcel() throws RadowException, AppException {
		this.setNextEventName("expExcelFromGrid");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("expModelExcel.onclick")
	public int expModelExcel() throws RadowException, AppException {
		this.setNextEventName("expModelExcelFromGrid");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/*
	 * poi导出excel
	 * */
	@PageEvent("expModelExcelFromGrid")
	public int expModelExcelFromGrid() throws RadowException, AppException {
		String batchid=this.getPageElement("batchid").getValue();
		String sql = " select NUM,FAILURE,A0192,A1517 from a15report where batchid='"+batchid+"'";
		
		System.out.println(sql);
		List<Map<String, Object>> list = this.exportPoi(sql);
		if (list == null) {
			this.setMainMessage("没有需要导出的数据");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String infile = this.insertModelList(list);
		infile = infile + "导入失败人员模板.xls";
		this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/*
	 * poi导出excel
	 * */
	@PageEvent("expExcelFromGrid")
	public int expExcelFromGrid() throws RadowException, AppException {
		String batchid=this.getPageElement("batchid").getValue();
		String sql = " select NUM,FAILURE,A0192 from a15report where batchid='"+batchid+"'";
		
		List<Map<String, Object>> list = this.exportPoi(sql);
		if (list == null) {
			this.setMainMessage("没有需要导出的数据");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String infile = this.insertList(list);
		infile = infile + "导入失败信息.xls";
		this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	public List<Map<String, Object>> exportPoi(String sql) throws RadowException, AppException {
		List<Map<String, Object>> list = null;
		HBSession session = HBUtil.getHBSession();
		CommonQueryBS query = new CommonQueryBS();
		query.setConnection(session.connection());
		query.setQuerySQL(sql);
		Vector<?> vector = query.query();
		Iterator<?> iterator = vector.iterator();
		if (iterator.hasNext()) {
			list = new ArrayList<Map<String, Object>>();
			while (iterator.hasNext()) {
				Map<String, Object> tmp = (Map<String, Object>) iterator.next();
				list.add(tmp);
			}
		}

	return list;
	}
	
	public  String insertList(List<Map<String, Object>> list) {
		ExportAsposeBS exp = new ExportAsposeBS();
		//poi导出excel
		String rootPath = ExportAsposeBS.getRootPath();
		String tempPath = rootPath + "FAILURE.xls";
		String path = ExpRar.expFile();
		// 读取excel模板，并复制到新文件中供写入和下载
		File newFile = exp.createNewFile2(tempPath, path);
		InputStream is = null;

		try {
			// 将excel文件转为输入流
			is = new FileInputStream(newFile);
			// 创建个workbook，
			/* XSSFWorkbook workbook = new XSSFWorkbook(is); */
			Workbook workbook = new HSSFWorkbook(is); //
			// 获取第一个sheet
			Sheet sheet = workbook.getSheetAt(0);
			OutputStream fos = new FileOutputStream(newFile);
			Row row = sheet.getRow(0);
	           for (int i = 0; i < list.size(); i++) {
	        		Map<String, Object> map = list.get(i);
	        	   row = sheet.createRow(i+1);
	        	   	Cell cell = row.createCell(0);
	                cell.setCellValue((String) map.get("num"));//序号
	        	   
	                Cell cell1 = row.createCell(1);
	                cell1.setCellValue((String) map.get("failure"));  //失败原因
	                
	                Cell cell2 = row.createCell(2);
	                cell2.setCellValue((String) map.get("a0192"));  //职务名称
	                
	                
	            }
			
			workbook.write(fos);
			fos.flush();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
				

	}
	
	public  String insertModelList(List<Map<String, Object>> list) {
		ExportAsposeBS exp = new ExportAsposeBS();
		//poi导出excel
		String rootPath = ExportAsposeBS.getRootPath();
		String tempPath = rootPath + "年度考核导入模板.xls";
		String path = ExpRar.expFile();
		// 读取excel模板，并复制到新文件中供写入和下载
		File newFile = exp.createNewFile3(tempPath, path);
		InputStream is = null;

		try {
			// 将excel文件转为输入流
			is = new FileInputStream(newFile);
			// 创建个workbook，
			/* XSSFWorkbook workbook = new XSSFWorkbook(is); */
			Workbook workbook = new HSSFWorkbook(is); //
			// 获取第一个sheet
			Sheet sheet = workbook.getSheetAt(0);
			OutputStream fos = new FileOutputStream(newFile);
			Row row = sheet.getRow(0);
	           for (int i = 0; i < list.size(); i++) {
	        		Map<String, Object> map = list.get(i);
	        	   row = sheet.createRow(i+1);
	        	   	Cell cell = row.createCell(0);
	                cell.setCellValue((String) map.get("num"));//序号
	        	   
	                Cell cell1 = row.createCell(1);
	                String failure=(String) map.get("failure");
	                String name=failure.substring(failure.indexOf('(')+1,failure.indexOf(')'));
	                cell1.setCellValue(name);  //姓名
	                
	                Cell cell2 = row.createCell(2);
	                cell2.setCellValue((String) map.get("a0192"));  //职务名称
	                
	                String a1517=(String) map.get("a1517");
	              //考核等次判断
					if("1".equals(a1517)) {
						a1517="优秀";
					}
					else if("2".equals(a1517)) {
						a1517="称职";
					}
					else if("3".equals(a1517)) {
						a1517="基本称职";
					}
					else if("4".equals(a1517)) {
						a1517="不称职";
					}
					else if("5".equals(a1517)) {
						a1517="不定等次";
					}
	                Cell cell3 = row.createCell(3);
	                cell3.setCellValue(a1517);  //考核等次
	            }
			
			workbook.write(fos);
			fos.flush();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
				

	}
	
	
	/*
	 * 确认导入
	 * */
	@PageEvent("confImp")
	public int confImp() throws RadowException, AppException {
		//获取Map
		HashMap<String,A15> sessMap=(HashMap<String,A15>) this.request.getSession().getAttribute("sessMap");
		//文件名
		String fileName=(String) this.request.getSession().getAttribute("fileName");
		//导入年份
		String drnf=(String) this.request.getSession().getAttribute("drnf");
		String batchid=this.getPageElement("batchid").getValue();
		HBSession sess = HBUtil.getHBSession();
		try {
		sess.beginTransaction();
		if(sessMap.size()>0) {
			Set<Entry<String,A15>> entrySet = sessMap.entrySet();
			for (Entry<String,A15> entry:entrySet) {
				A15 a15=(A15) entry.getValue();
				String name=entry.getKey();
				sess.save(a15);
				applog.createLog("3151", "A15", a15.getA0000(), name, "新增记录", new Map2Temp().getLogInfo(new A15(), a15));
			}
			
			//放在判断里面的原因是，如果没有一条数据能导入成功的话，相当于没有导入数据，只是显示一些导入失败的原因，不存在系统内数据的变动，所以不记录这种情况。
		}
		//获取当前用户
		String userID=SysManagerUtils.getUserId();
		//获取当前日期及时间
		String time=DateUtil.getCurrentDateHMS();
		sess.createSQLQuery("insert into A15FileRecords values(sys_guid(),'"+fileName+"','"+userID+"','"+time+"','"+drnf+"')").executeUpdate();
		//确认导入后，让界面可操作
		sess.createSQLQuery("update a15report set status='1' where batchid='"+batchid+"'").executeUpdate();
		sess.getTransaction().commit();
		this.request.getSession().removeAttribute("sessMap");
		this.request.getSession().removeAttribute("fileName");
		this.request.getSession().removeAttribute("drnf");
		this.setNextEventName("memberGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("result();");
		this.setMainMessage("导入成功");
		} catch (Exception e) {
			sess.getTransaction().rollback();
			e.printStackTrace();
			this.setMainMessage("导入出现异常，请联系管理员");
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/*
	 * 取消导入
	 * */
	@PageEvent("callImp")
	public int callImp() throws RadowException, AppException {
		this.request.getSession().removeAttribute("sessMap");
		this.request.getSession().removeAttribute("fileName");
		this.request.getSession().removeAttribute("drnf");
		String batchid=this.getPageElement("batchid").getValue();
		HBSession sess = HBUtil.getHBSession();
		try {
		sess.beginTransaction();
		sess.createSQLQuery("delete a15report where batchid='"+batchid+"'").executeUpdate();
		sess.getTransaction().commit();
		this.setNextEventName("memberGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("result();");
		} catch (Exception e) {
			sess.getTransaction().rollback();
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
}
