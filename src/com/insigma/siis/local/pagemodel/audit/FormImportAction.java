package com.insigma.siis.local.pagemodel.audit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fr.function.SQL;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFCell;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFDateUtil;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.ActionSupport;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;

/**
 * 编办数据导入工具action
 * 
 * @author huyl
 *
 */
public class FormImportAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final String separator = System.getProperty("file.separator");
	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";
	public static Map<String,String> auditMap=new HashMap<String,String>(){{
		put("省纪委省监委", "JW_AUDIT_INFO_TEMP");put("干部综合处", "ZZB_GB_AUDIT_INFO_TEMP");put("干部监督室", "ZZB_JD_AUDIT_INFO_TEMP");
		put("省信访局", "XF_AUDIT_INFO_TEMP");put("省法院", "FY_AUDIT_INFO_TEMP");put("省检察院", "JCY_AUDIT_INFO_TEMP");
		put("省发改委", "FGW_AUDIT_INFO_TEMP");put("省公安厅", "GA_AUDIT_INFO_TEMP");put("省人力社保厅", "RLSB_AUDIT_INFO_TEMP");
		put("省自然资源厅", "ZRZY_AUDIT_INFO_TEMP");put("省生态环境厅", "STHJ_AUDIT_INFO_TEMP");put("省卫健委", "WJW_AUDIT_INFO_TEMP");
		put("省应急管理厅", "YJGL_AUDIT_INFO_TEMP");put("省审计厅", "SJ_AUDIT_INFO_TEMP");put("省市场监管局", "SCJG_AUDIT_INFO_TEMP");
		put("省统计局", "TJ_AUDIT_INFO_TEMP");put("省总工会", "GH_AUDIT_INFO_TEMP");put("国家税务总局浙江省税务局", "SW_AUDIT_INFO_TEMP");
		
	}};
	public static Map<String,Integer> colMap=new HashMap<String,Integer>(){{
		put("省纪委省监委", 10);put("干部综合处", 7);put("干部监督室", 9);
		put("省信访局", 8);put("省法院", 9);put("省检察院", 8);
		put("省发改委", 8);put("省公安厅", 12);put("省人力社保厅", 7);
		put("省自然资源厅", 7);put("省生态环境厅", 7);put("省卫健委", 7);
		put("省应急管理厅", 7);put("省审计厅", 8);put("省市场监管局", 8);
		put("省统计局", 7);put("省总工会", 7);put("国家税务总局浙江省税务局", 7);
		
	}};
	
	
	
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
	public ActionForward startImp(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long s = System.currentTimeMillis();
		Map<String, String> data = new HashMap<String, String>();// 返回前台的MSG
		Map<String, String> tempmap = new HashMap<String, String>();
		String imptype=request.getParameter("imptype");
		String batchid=request.getParameter("batchid");
		tempmap.put("IMP_TYPE", imptype);
		tempmap.put("batchid", batchid);
		String filePath = "";
		String str = "";
		
		// 创建接收文件对象
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		PrintWriter out = response.getWriter();
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

			while (iter.hasNext()) {

				FileItem fi = iter.next();

				if (fi == null || fi.getSize() == 0) {
					str = "导入文件不能为空，请重新导入！";
					data.put("error", str);
					this.doSuccess(request, data);
					return this.ajaxResponse(request, response);
				}
				if (!(fi.getName().endsWith(EXCEL_XLS) || (fi.getName().endsWith(EXCEL_XLSX)))) {
					data.put("error", "文件不是Excel格式,请检查！");
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
				tempmap.put("FILE_NAME", filerealname);
				try {
					fi.write(new File(filePath));
				} catch (Exception e) {
					e.printStackTrace();
				}
				uploadFileName = filerealname;
				fi.getOutputStream().close();
			}
		}

		// 读取服务器中的文件，并进行处理
		File f = new File(filePath);
		if (f.getName().endsWith(EXCEL_XLS) || f.getName().endsWith(EXCEL_XLSX)) {

			data = Imp(uploadFileName, uuid, filePath, tempmap);
		} else {
			str = "文件格式不是导出的模板格式,请重新导入！";
			data.put("error", str);
			this.doSuccess(request, data);
			return this.ajaxResponse(request, response);
		}
		f.delete();

		long s2 = System.currentTimeMillis();
		System.out.println("Excel导入，共计耗时：" + ((s2 - s) / 1000) + "秒");

		this.doSuccess(request, data);
		return this.ajaxResponse(request, response);
	}

	/**
	 * 正式导入
	 * 
	 * @param uuid
	 * @param path
	 * @return
	 */
	private Map<String, String> Imp(String fileName, String uuid, String path, Map<String, String> tempmap) {
		HBSession session = HBUtil.getHBSession();
		Map<String, String> data = new HashMap<String, String>();// 返回前台的MSG

		FileInputStream is = null;
		path = path.replaceAll("\\\\", "\\/");

		try {
			// 先获取一张sheet页的所有信息
			// 支持Excel 2003 2007
			File excelFile = new File(path); // 创建文件对象
			is = new FileInputStream(excelFile);
			Workbook workbook = getWorkbok(is, excelFile);
			// 继续初始化数据 Map<String, String> mapA0000
			Sheet sheet = workbook.getSheetAt(0);
			long start = System.currentTimeMillis();
			System.out.println("--------------------------------------------进入Sheet表1");
			String sql = "select FILE_ID.NEXTVAL from dual";
			if("AuditFeedback".equals(tempmap.get("IMP_TYPE"))||"AuditPerson".equals(tempmap.get("IMP_TYPE"))) {
				sql = "select AUDIT_IMPID.NEXTVAL from dual";
			}
			
			String fileId = session.createSQLQuery(sql).uniqueResult().toString();
			tempmap = dispose(fileName, fileId, sheet, uuid, session, tempmap);
			long end = System.currentTimeMillis();
			System.out.println(
					"--------------------------------------------Sheet表1处理完成,耗时：" + ((end - start) / 1000) + "秒");
			data.put("success", "Excel导入成功！");

			if ("Excel导入成功！".equals(data.get("success"))) {
				// excel导入成功后生成导入详情
				setUpPower(fileName, fileId, session, tempmap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			data.put("error", e.getMessage());
			return data;
		} finally {
			try {
				is.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return data;
	}

	/**
	 * 具体表格导入读取方法
	 * 
	 * @param sheet
	 * @param imprecordid
	 * @param session
	 * @throws Exception
	 * 
	 *             导入从12380网站过来的excel
	 */
	@SuppressWarnings("deprecation")
	private Map<String, String> dispose(String fileName, String fileId, Sheet sheet, String imprecordid,
			HBSession session, Map<String, String> tempmap) throws Exception {

		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		List<String> a0184list= new ArrayList<String>();
		String tempTable = "";
		// 获得总行数和总列数
		int rowNum = sheet.getLastRowNum();
		// int colNum=sheet.getRow(1).getPhysicalNumberOfCells();
		int colNum = 0;

		StringBuffer sqlApp = new StringBuffer();
		System.out.println("fileName  " + fileName);

		if (fileName.equals("中间库导入数据")) {
			tempTable = "Xchg_12380XFList";
			colNum = 16;

			sqlApp.append(tempTable
					+ "(file_id, xfdocno,	status,	zhuanbantype,	zhuanbanstate,	upreceivetime,	downreceivetime,	jubaotype,	accepttime,	breporter,	brunit,	brduty,	checkresult,	isaggr,	creator,	importtime,	importer "
					+ ") values(");
			sqlApp.append("'" + fileId + "' ,");
		} else if (fileName.equals("12380网站举报")) {
			tempTable = "WEB_FILE_TEMP";
			colNum = 32;
			sqlApp.append(tempTable
					+ "(file_id, record_id ,record_no,	reporter_name,	reporter_sex,	locality_code,	reporter_unit,	reporter_zhiwu,	reporter_code_id,	reporter_addree,	report_time,	reporter_tel_no,	reporter_email,	breporter_name,	breporter_sex,	breporter_zzmm,	breporter_unit,	breporter_zhiwu,	breporter_level,	breporter_dep,	breporter_city,	breporter_area,	report_info,	isNo1,	quest_type,	quest_type_1,	quest_type_2,	process_status,	check_type,	check_result,	context_suggest,	keep_drop,	person_unit "
					+ ") values(");
			sqlApp.append("'" + fileId + "' ,");

		} else if (fileName.equals("干部监督信访件录入")) {
			tempTable = "WEB_FILE_TEMP";
			colNum = 45;
			sqlApp.append(tempTable + "(web_file_temp.file_id," + "        web_file_temp.id,"
					+ "        web_file_temp.record_no," + "        web_file_temp.reporter_name,"
					+ "        web_file_temp.reporter_sex," + "        web_file_temp.locality_code,"
					+ "        web_file_temp.reporter_unit," + "        web_file_temp.reporter_duty,"
					+ "        web_file_temp.reporter_code_id," + "        web_file_temp.reporter_addree,"
					+ "        web_file_temp.report_time," + "        web_file_temp.reporter_tel_no,"
					+ "        web_file_temp.reporter_email," + "        web_file_temp.breporter_name,"
					+ "        web_file_temp.breporter_sex," + "        web_file_temp.breporter_zzmm,"
					+ "        web_file_temp.breporter_unit," + "        web_file_temp.breporter_duty,"
					+ "        web_file_temp.breporter_level," + "        web_file_temp.breporter_dep,"
					+ "        web_file_temp.breporter_city," + "        web_file_temp.breporter_area,"
					+ "        web_file_temp.report_info," + "        web_file_temp.isno1,"
					+ "        web_file_temp.quest_type," + "        web_file_temp.quest_type_1,"
					+ "        web_file_temp.quest_type_2," + "        web_file_temp.process_status,"
					+ "        web_file_temp.check_type," + "        web_file_temp.check_result,"
					+ "        web_file_temp.jb_style," + "        web_file_temp.jb_source," +
					// " web_file_temp.person_unit," +
					"        web_file_temp.APPROVE_TIME," + "        web_file_temp.APPROVER_NAME," +

					"        web_file_temp.leader_approve_1," + "        web_file_temp.leader_approve_2,"
					+ "        web_file_temp.ZB_TIME_1,"  
					+ "        web_file_temp.zb_unit_1," 
					+ "        web_file_temp.ZB_RECEIVER_1,"  
					+ "        web_file_temp.ZB_TIME_2,"  
					+ "        web_file_temp.zb_unit_2,"
					+ "        web_file_temp.ZB_RECEIVER_2," 
					+ "        web_file_temp.ZB_TIME_3," 
					+ "        web_file_temp.zb_unit_3 ,"
					+ "        web_file_temp.ZB_RECEIVER_3," 
					+ "        web_file_temp.ISGS" + ") values(");
			sqlApp.append("'" + fileId + "' ,");

		}

		else if (fileName.contains("重大事项")) {
			tempTable = "ZDSX_PERSONALRPT_TEMP";
			colNum = 8;
			// insert into

			sqlApp.append(tempTable
					+ "(oid,file_id,deleteflag,docno,acceptdate,reportername,cardid,reporterduty,unittype,itemtype,content "
					+ ") values(");
			sqlApp.append("sys_guid() ,'" + fileId + "',0, ");

		} else if (fileName.equals("年度经济责任审计导出数据模板")) {
			tempTable = "NDJJZRSJ_TEMP";
			colNum = 12;
			// insert into
			sqlApp.append(tempTable + "(file_id,id,nd,xm,sfz,srzw,bdcdw,sjdw,sjsj,sjlb,sjjl,zywtzy,bz,sjbg " + ") values(");
			sqlApp.append("'" + fileId + "' ,sys_guid(),");

		} else if (fileName.equals("离任经济事项交接导出数据模板")) {
			tempTable = "LRJJSXJJ_TEMP";
			colNum = 12;
			// insert into
			sqlApp.append(
					tempTable + "(file_id,id,lrldxm,dwmc,lrldzw,lrldsfz,rzqssj,rzjzsj,jrldxm,jrldzw,jrldsfz,jjsj,bz,jjfjcl "
							+ ") values(");
			sqlApp.append("'" + fileId + "' ,sys_guid(),");

		} else if (fileName.equals("12380网站个人举报信息")) {
			tempTable = "WEB_XF_PERSON";
			colNum = 15;
			sqlApp.append(tempTable);
			sqlApp.append(" (  web_xf_person.file_id," + "     web_xf_person.oid,"
					+ "              web_xf_person.record_id," + "              web_xf_person.reporter_name,"
					+ "              web_xf_person.reporter_connect," + "              web_xf_person.reporter_duty,"
					+ "              web_xf_person.reporter_unit," + "              web_xf_person.breporter_name,"
					+ "              web_xf_person.breporter_card_id," + "              web_xf_person.breporter_unit,"
					+ "              web_xf_person.breporter_duty," + "              web_xf_person.breporter_province,"
					+ "              web_xf_person.breporter_city," + "              web_xf_person.brepoerer_district,"
					+ "              web_xf_person.breporter_duty_level," + "              web_xf_person.xf_problem,"
					+ "              web_xf_person.appact_date ) values(");
			sqlApp.append("'" + fileId + "' ,sys_guid(),");

		} else if (fileName.equals("12380单位举报信息")) {

			tempTable = "WEB_XF_UNIT";
			colNum = 12;
			sqlApp.append(tempTable);
			sqlApp.append(" (web_xf_unit.file_id," + "       web_xf_unit.oid," + "       web_xf_unit.record_id,"
					+ "       web_xf_unit.reporter_name," + "       web_xf_unit.reporter_connect,"
					+ "       web_xf_unit.reporter_duty," + "       web_xf_unit.reporter_unit,"
					+ "       web_xf_unit.breporter_unit," + "       web_xf_unit.breporter_unit_level,"
					+ "       web_xf_unit.breporter_province," + "       web_xf_unit.breporter_city,"
					+ "       web_xf_unit.brepoerer_district," + "       web_xf_unit.xf_problem,"
					+ "       web_xf_unit.appact_date) values (");
			sqlApp.append("'" + fileId + "' ,sys_guid(),");
		}else if(fileName.equals("提醒批量新增模版")){
			tempTable="WEB_REMID";
			colNum=34;
			sqlApp.append(tempTable);
			sqlApp.append(" ( file_id,remidid,a0101,a0192a,ranks,queorgin,quenoone,quetypeone,queinfoone,quenotwo,quetypetwo,queinfotwo,");
			sqlApp.append(" quenothree,quetypethree,queinfothree,quenofour,quetypefour,queinfofour,quenofive,quetypefive,queinfofive,");
			sqlApp.append(" quenosix,quetypesix,queinfosix,chater,chattype,recorder,chattime,chatadd,chatinfo,remark,remind,remindtime,copyunit,status,proresult ) values ( ");
			sqlApp.append("'"+fileId+"',sys_guid(),");
		}else if(fileName.equals("函询批量新增模版")){
			tempTable="WEB_ENVELOPE";
			colNum=30;
			sqlApp.append(tempTable);
			sqlApp.append("( file_id,id,a0101,a0192a,grade,source,quenoone,quetypeone,queinfoone,quenotwo,quetypetwo,queinfotwo,");
			sqlApp.append(" quenothree,quetypethree,queinfothree,quenofour,quetypefour,queinfofour,quenofive,quetypefive,queinfofive,");
			sqlApp.append(" quenosix,quetypesix,queinfosix,applytime,backlimit,copy,conclusion,confirm,suggestion,isdeal,proresult ) values ( ");
			sqlApp.append("'"+fileId+"',sys_guid(),");
		}else if(fileName.equals("诫勉批量新增模版")){
			tempTable="WEB_ADMONISH";
			colNum=36;
			sqlApp.append(tempTable);
			sqlApp.append("( file_id,id,a0101,a0192a,grade,source,quenoone,quetypeone,queinfoone,quenotwo,quetypetwo,queinfotwo,");
			sqlApp.append(" quenothree,quetypethree,queinfothree,quenofour,quetypefour,queinfofour,quenofive,quetypefive,queinfofive,");
			sqlApp.append(" quenosix,quetypesix,queinfosix,applytime,handletime,backlimit,speakers,talksort,recorder,talktime,talkplace,conversation,");
			sqlApp.append(" remark,referencenumber,copy,way,proresult) values (");
			sqlApp.append("'"+fileId+"',sys_guid(),");
		}else if ("AuditFeedback".equals(tempmap.get("IMP_TYPE"))||"AuditFeedback_Batch".equals(tempmap.get("IMP_TYPE"))) {
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			String deptname=(String) session.createSQLQuery("select rolename from  smt_role where roleid='"+user.getDept()+"'").uniqueResult();
			//deptname="省纪委省监委";
			tempTable=auditMap.get(fileName);
			colNum=colMap.get(fileName);
			sqlApp.append(tempTable);
			sqlApp.append(" values( ");
			sqlApp.append("sys_guid(),");
			if("AuditFeedback_Batch".equals(tempmap.get("IMP_TYPE"))) {
				a0184list=session.createSQLQuery("select a0184 from audit_person where audit_batch='"+tempmap.get("batchid")+"'").list();
			}
			
		}else if ("AuditPerson".equals(tempmap.get("IMP_TYPE"))) {
			tempTable="AUDIT_PERSON_TEMP";
			colNum=7;
			sqlApp.append(tempTable);
			sqlApp.append(" values( ");
			sqlApp.append("sys_guid(),'"+fileId+"',");
			
		}else if ("TriColor".equals(tempmap.get("IMP_TYPE"))) {

			tempTable="A01_ROSTER_TEMP";
			colNum=6;
			sqlApp.append(tempTable);
			sqlApp.append(" values( ");
			sqlApp.append("sys_guid(),'"+fileId+"',");
		}

		tempmap.put("ORGCOUNT", rowNum + "");
		Cell c = null;
		try {
			// 创建临时表
			// tempTable = "BBB01"+getNo() + DateUtil.timeToString(DateUtil.getTimestamp(),
			// "yyyyMMddHHmmssS");

			// String BBB01_temp = "create table "+tempTable+" as select * from BBB01 where
			// 1=2";
			// session.createSQLQuery(BBB01_temp).executeUpdate();

			tempmap.put("TABLE_NAME", tempTable);

			int i = 1;

			if (tempTable.equals("WEB_XF_UNIT") || tempTable.equals("WEB_XF_PERSON")
					|| tempTable.equals("ZDSX_PERSONALRPT_TEMP")||"AuditFeedback".equals(tempmap.get("IMP_TYPE"))) {
				i = 2;

			}
			
			a:for (int j = i; j <= rowNum; j++) {
				
				
				StringBuffer sb = new StringBuffer("insert into ");
				sb.append(sqlApp);
				// sb.append("'"+UUID.randomUUID().toString()+"',");
				// sb.append("'0',");
				for (int k = 0; k < colNum; k++) {// 列
					
					String cellValue = "";
					c = sheet.getRow(j).getCell(k);
					if("AuditFeedback".equals(tempmap.get("IMP_TYPE"))||"AuditFeedback_Batch".equals(tempmap.get("IMP_TYPE"))) {
						if(k==2) {
							continue;
						}
						if(k==1&&(c == null ||"".equals(c.toString())|| (c != null && c.toString().equals("null ")))) {
							continue a;
						}
						if("AuditFeedback_Batch".equals(tempmap.get("IMP_TYPE"))&&k==1&&!a0184list.contains(c.toString())) {
							continue a;
						}
						
						
					}
					
					if("AuditPerson".equals(tempmap.get("IMP_TYPE"))) {
						if(k==2&&(c == null ||"".equals(c.toString())|| (c != null && c.toString().equals("null ")))) {
							continue a;
						}
						if(k==0) {
							continue;
						}
					}
					
					// System.out.println("C " + c+ " ctype " + c.getCellStyle());

					if (c == null || (c != null && c.toString().equals("null "))) {
						sb.append("'',");
					} else {

						if (c.getCellType() == 0 && c.toString().length() == 10) {
							cellValue = formater.format(c.getDateCellValue());

						} else {

							cellValue = c.toString();
						}
						
						sb.append("'" + cellValue + "',");
					}
				}
				if("AuditFeedback".equals(tempmap.get("IMP_TYPE"))||"AuditFeedback_Batch".equals(tempmap.get("IMP_TYPE"))) {
					sb.append("'"+fileId+"',");
				}
				
				sb.deleteCharAt(sb.lastIndexOf(","));
				sb.append(")");

				System.out.println("sql: " + sb);
				session.createSQLQuery(sb.toString()).executeUpdate();
			}
		} catch (Exception e) { // 先catch错误，处理错误后再抛出
			e.printStackTrace();
			throw new Exception("导入失败！请检查Excel，Sheet表1！");
		}
		return tempmap;
	}

	private void setUpPower(String fileName, String fileId, HBSession session, Map<String, String> map)
			throws Exception {
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		System.out.println("user.getId():"+user.getId());
		CallableStatement c = null;

		if (fileName.equals("12380网站举报")) {

			c = HBUtil.getHBSession().connection().prepareCall("{call IMP_WEB_XF(?,?,?)}");
			c.setString(1, fileId);
			c.setString(2, user.getId());
			c.registerOutParameter(3, Types.VARCHAR);

			c.execute();

		} else if (fileName.equals("中间库导入数据")) {

			c = HBUtil.getHBSession().connection().prepareCall("{call IMP_12380NAMELIST(?,?,?)}");
			c.setString(1, fileId);
			c.setString(2, user.getId());
			c.registerOutParameter(3, Types.VARCHAR);
			c.execute();
		} else if ("ZDSX_PERSONALRPT_TEMP".equals(map.get("TABLE_NAME"))) {
			c = HBUtil.getHBSession().connection().prepareCall("{call PRO_ZDSX_PERSONALRPT(?,?)}");
			c.setString(1, fileId);
			c.setString(2, user.getId());
			c.execute();

		} else if ("NDJJZRSJ_TEMP".equals(map.get("TABLE_NAME"))) {
			c = HBUtil.getHBSession().connection().prepareCall("{call PRO_NDJJZRSJ(?,?)}");
			c.setString(1, fileId);
			c.setString(2, user.getId());
			c.execute();

		} else if ("LRJJSXJJ_TEMP".equals(map.get("TABLE_NAME"))) {
			c = HBUtil.getHBSession().connection().prepareCall("{call PRO_LRJJSXJJ(?,?)}");
			c.setString(1, fileId);
			c.setString(2, user.getId());
			c.execute();

		} else if ("WEB_XF_PERSON".equals(map.get("TABLE_NAME"))) {
			c = HBUtil.getHBSession().connection().prepareCall("{call IMP_WEB_PERSON(?,?,?)}");
			c.setString(1, fileId);
			c.setString(2, user.getId());
			c.registerOutParameter(3, Types.VARCHAR);
			c.execute();
		} else if ("WEB_XF_UNIT".equals(map.get("TABLE_NAME"))) {
			c = HBUtil.getHBSession().connection().prepareCall("{call IMP_WEB_UNIT(?,?,?)}");
			c.setString(1, fileId);
			c.setString(2, user.getId());
			c.registerOutParameter(3, Types.VARCHAR);
			c.execute();
			
		}else if("WEB_REMID".equals(map.get("TABLE_NAME"))){
			c = HBUtil.getHBSession().connection().prepareCall("{call PRO_REMID(?,?,?)}");
			c.setString(1, fileId);
			c.setString(2, user.getId());
			c.registerOutParameter(3, Types.VARCHAR);
			c.execute();
		}else if("WEB_ENVELOPE".equals(map.get("TABLE_NAME"))){
			c=HBUtil.getHBSession().connection().prepareCall("{call PRO_APPLYBYLETTER(?,?,?)}");
			c.setString(1, fileId);
			c.setString(2,user.getId());
			c.registerOutParameter(3, Types.VARCHAR);
			c.execute();
		
		}else if("WEB_ADMONISH".equals(map.get("TABLE_NAME"))){
			c=HBUtil.getHBSession().connection().prepareCall("{call PRO_ADMONISHING(?,?,?)}");
			c.setString(1, fileId);
			c.setString(2,user.getId());
			c.registerOutParameter(3, Types.VARCHAR);
			c.execute();	
		}else if("AuditFeedback".equals(map.get("IMP_TYPE"))||"AuditFeedback_Batch".equals(map.get("IMP_TYPE"))) {
			c=HBUtil.getHBSession().connection().prepareCall("{call PRO_AUDIT_IMP(?,?)}");
			c.setString(1, fileId);
			c.setString(2, map.get("TABLE_NAME"));
	
			c.execute();
		}else if("AuditPerson".equals(map.get("IMP_TYPE"))) {
			c=HBUtil.getHBSession().connection().prepareCall("{call PRO_AUDITPERSON_IMP(?,?,?,?)}");
			c.setString(1, fileId);
			c.setString(2, map.get("batchid"));
			c.setString(3, user.getId());
			c.registerOutParameter(4, Types.VARCHAR);
			c.execute();
		}else if("TriColor".equals(map.get("IMP_TYPE"))) {
			c=HBUtil.getHBSession().connection().prepareCall("{call PRO_TRICOLOR_IMP(?)}");
			c.setString(1, fileId);
			c.execute();
		}else {
			c = HBUtil.getHBSession().connection().prepareCall("{call IMP_WEB_XF(?,?,?)}");
			c.setString(1, fileId);
			c.setString(2, user.getId());
			c.registerOutParameter(3, Types.VARCHAR);
			c.execute();

		}

	}

	// private void setUpPower(String unid, HBSession session, Map<String, String>
	// map) throws Exception {
	// UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
	// String b0101 = "";
	// try {
	// b0101 = HBUtil.getValueFromTab("b0101", "B01", "b0111='" +
	// user.getOtherinfo() + "'");
	// } catch (AppException e) {
	// e.printStackTrace();
	// }
	// StringBuffer sb = new StringBuffer("insert into NEW_IMP_RECORD ");
	// sb.append(
	// "(IMP_RECORD_ID,IMP_USER_ID,IMP_TIME,IMP_DEPTID,IMP_DEPT_NAME,FILE_NAME,FILE_TYPE,IMP_TYPE,ORGCOUNT,COMPARE_STATUS,TABLE_NAME)
	// values(");
	// sb.append("'" + unid + "',");
	// sb.append("'" + user.getId() + "',");
	// sb.append("'" + DateUtil.dateToString(new Date(), "yyyyMMdd HH24:mi:ss") +
	// "',");
	// sb.append("'" + user.getOtherinfo() + "',");
	// sb.append("'" + b0101 + "',");
	// sb.append("'" + map.get("FILE_NAME") + "',");
	// sb.append("'" + map.get("FILE_TYPE") + "',");
	// sb.append("'1',");
	// sb.append("'" + map.get("ORGCOUNT") + "',");
	// sb.append("'1',");
	// sb.append("'" + map.get("TABLE_NAME") + "')");
	// session.createSQLQuery(sb.toString()).executeUpdate();
	// }

	private static String getNo() {
		String no = "";
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int r = random.nextInt(35);
			no = no + key[r];
		}
		return no;
	}

	/**
	 * 判断Excel的版本,获取Workbook
	 * 
	 * @param in
	 * @param file
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

}
