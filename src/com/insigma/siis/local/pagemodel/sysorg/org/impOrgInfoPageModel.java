package com.insigma.siis.local.pagemodel.sysorg.org;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class impOrgInfoPageModel extends PageModel{
	
	private CellStyle styleThick=null;

	public impOrgInfoPageModel() {
	}

	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 导出
	 * @return
	 * @throws RadowException
	 * @throws IOException 
	 * @throws AppException 
	 */
	@PageEvent("exportExcel")
	public int exportExcel() throws RadowException, IOException, AppException{
		String sql="";
		String idcheckedarr=request.getParameter("idcheckedarr");
		OutputStream os = null;
		Workbook workbook = null;
		try {
			//获取构类型机，所在政区，隶属关系，机构类别，机构级别，数据集合
			sql=" select code_value , code_name,code_type from code_value where code_type in ('B0194',"//构类型机
					+ " 'ZB01',"//所在政区
					+ " 'ZB87',"//隶属关系
					+ "'ZB04',"//机构类别
					+ "'ZB03')";//机构级别
			List<HashMap<String, Object>> listData=new CommQuery().getListBySQL(sql);
			Map<String,String> mapB0194=new HashMap<String, String>();
			Map<String,String> mapZB01=new HashMap<String, String>();
			Map<String,String> mapZB87=new HashMap<String, String>();
			Map<String,String> mapZB04=new HashMap<String, String>();
			Map<String,String> mapZB03=new HashMap<String, String>();
			Map<String,Map<String,String>> mapmap=new HashMap<String, Map<String,String>>();
			for(int i=0;i<listData.size();i++){
				if("B0194".equals((String)listData.get(i).get("code_type"))){
					mapB0194.put((String)listData.get(i).get("code_value"), (String)listData.get(i).get("code_name"));
				}else if("ZB01".equals((String)listData.get(i).get("code_type"))){
					mapZB01.put((String)listData.get(i).get("code_value"), (String)listData.get(i).get("code_name"));
				}else if("ZB87".equals((String)listData.get(i).get("code_type"))){
					mapZB87.put((String)listData.get(i).get("code_value"), (String)listData.get(i).get("code_name"));
				}else if("ZB04".equals((String)listData.get(i).get("code_type"))){
					mapZB04.put((String)listData.get(i).get("code_value"), (String)listData.get(i).get("code_name"));
				}else if("ZB03".equals((String)listData.get(i).get("code_type"))){
					mapZB03.put((String)listData.get(i).get("code_value"), (String)listData.get(i).get("code_name"));
				}
			}
			mapmap.put("B0194", mapB0194);
			mapmap.put("ZB01", mapZB01);
			mapmap.put("ZB87", mapZB87);
			mapmap.put("ZB04", mapZB04);
			mapmap.put("ZB03", mapZB03);
			workbook = new HSSFWorkbook();

			List<HashMap<String, Object>>  list=null;
			Map<String, String> map = null;
			if(DBType.ORACLE==DBUtil.getDBType()){
				if(idcheckedarr.length()>0){//有选择
					map = this.operateCheckedData(idcheckedarr);
					String cueUserid=SysUtil.getCacheCurrentUser().getId();
					String [] arr=idcheckedarr.split(",");
					Set<String> set = map.keySet();
					int i = 0;
					Sheet sheet =workbook.createSheet();
					 workbook.setSheetName(i, "机构信息");
					 Map<String, Integer> maprow=new HashMap<String, Integer>();
					 maprow.put("rownum", 0);
					 this.exportSelectTm(workbook, sheet, mapmap, maprow);
					for(String sv : set){
						if(map.containsValue(sv)){
							sql=" select b.b0101 b0101,"//机构名称
							  		+ " b.b0114,"//机构编码
							  		+ " b.b0194,"//机构类型
							  		+ " b.b0117,"//所在政区
							  		+ " b.b0124,"//隶属关系
							  		+ " b.b0131,"//机构类别
							  		+ " b.b0127"//机构级别 
									+" from b01 b,competence_userdept t where b.b0111=t.b0111 and b.b0111='"+sv+"' and t.userid='"+cueUserid+"'  ORDER BY b.SORTID ASC ";
							 list=new CommQuery().getListBySQL(sql);//根据条件查询，所有数据
							 exportSelectData(list,workbook,sheet,mapmap,maprow);
						}else{
							/*sql=" select rpad('    ',(level-1)*3,'    ')||b.b0101 b0101,"//机构名称
							  		+ " b.b0114,"//机构编码
							  		+ " b.b0194,"//机构类型
							  		+ " b.b0117,"//所在政区
							  		+ " b.b0124,"//隶属关系
							  		+ " b.b0131,"//机构类别
							  		+ " b.b0127"//机构级别 
									+" from b01 b,competence_userdept t where b.b0111=t.b0111 and t.userid='"+cueUserid+"' start with b.b0111='"+sv+"' connect by prior b.b0111 =  b.b0121 ORDER SIBLINGS BY b.SORTID ASC ";*/
							 sql="select rpad('    ', (level - 1) * 3, '    ') || b.b0101 b0101," + 
									"        b.b0114," + 
									"        b.b0194," + 
									"        b.b0117," + 
									"        b.b0124," + 
									"        b.b0131," + 
									"        b.b0127" + 
									"   from b01 b" + 
									"   where b.b0111 in  (select t.b0111 from competence_userdept t where t.userid='"+cueUserid+"')" + 
									"   start with b.b0111 = '"+sv+"'" + 
									" connect by  prior b.b0111 =  b.b0121";
							 list=new CommQuery().getListBySQL(sql);//根据条件查询，所有数据
							 exportSelectData(list,workbook,sheet,mapmap,maprow);
						}
						i++;
					}
//					for(int i=0;i<arr.length;i++){
//						 sql=" select rpad('    ',(level-1)*3,'    ')||b.b0101 b0101,"//机构名称
//						  		+ " b.b0114,"//机构编码
//						  		+ " b.b0194,"//机构类型
//						  		+ " b.b0117,"//所在政区
//						  		+ " b.b0124,"//隶属关系
//						  		+ " b.b0131,"//机构类别
//						  		+ " b.b0127"//机构级别 
//								+" from b01 b,competence_userdept t where b.b0111=t.b0111 and t.userid='"+cueUserid+"' start with b.b0111='"+arr[i].split("@")[0]+"' connect by prior b.b0111 =  b.b0121 ORDER SIBLINGS BY b.SORTID ASC ";
//						 list=new CommQuery().getListBySQL(sql);//根据条件查询，所有数据
//						 Sheet sheet =workbook.createSheet();
//						 workbook.setSheetName(i, arr[i].split("@")[1]+"("+i+")");
//						 exportSelect(list,workbook,sheet,mapmap);
//					}
				}else{//未选择
					
					String node=request.getParameter("root_id");
					String cueUserid=SysUtil.getCacheCurrentUser().getId();
					if(node.equals("-1")){//没有选中
						//if("0".equals(type)||"1".equals(type)||"7".equals(type)||"2".equals(type)){//是否机构信息，人员批量转移,机构排序界面的请求，成立，则是
							//获取用户权限范围内的最高级别单位的id
						 	List<HashMap> list1 =null;
							try {
								if(DBType.ORACLE==DBUtil.getDBType()){
									sql="select b0111 from ( select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) asc) where ROWNUM =1";
									list1=CommonQueryBS.getQueryInfoByManulSQL(sql);
								}else if(DBType.MYSQL==DBUtil.getDBType()){
									sql="select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) asc limit 1";
									list1=CommonQueryBS.getQueryInfoByManulSQL(sql);
								}
							}catch(AppException e){
								e.printStackTrace();
							}
							if(list1!=null&&list1.size()>0){
								node=(String)list1.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
							}else{
								this.setSelfDefResData("1@@@"+"程序出错!");
							}
					}
					sql=" select rpad('    ',(level-1)*3,'    ')||b.b0101 b0101,"//机构名称
					  		+ " b.b0114,"//机构编码
					  		+ " b.b0194,"//机构类型
					  		+ " b.b0117,"//所在政区
					  		+ " b.b0124,"//隶属关系
					  		+ " b.b0131,"//机构类别
					  		+ " b.b0127"//机构级别 
							+" from b01 b,competence_userdept t where b.b0111=t.b0111 and t.userid='"+cueUserid+"' start with b.b0111='"+node+"' connect by prior b.b0111 =  b.b0121 ORDER SIBLINGS BY b.SORTID ASC ";
					 list=new CommQuery().getListBySQL(sql);//根据条件查询，所有数据
					 Sheet sheet =workbook.createSheet();
					 String name=HBUtil.getValueFromTab("b0101", "b01", "b0111='"+node+"' ");
					 workbook.setSheetName(0, "机构信息");
					 exportSelect(list,workbook,sheet,mapmap);
				}
			}else if(DBType.MYSQL==DBUtil.getDBType()){
				String cueUserid=SysUtil.getCacheCurrentUser().getId();
				//获取用户权限范围内的最低级别单位的id
			 	List<HashMap> listh =null;
				try {
					if(DBType.ORACLE==DBUtil.getDBType()){
						sql="select b0111 from ( select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) desc) where ROWNUM =1";
						listh=CommonQueryBS.getQueryInfoByManulSQL(sql);
					}else if(DBType.MYSQL==DBUtil.getDBType()){
						sql="select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) desc limit 1";
						listh=CommonQueryBS.getQueryInfoByManulSQL(sql);
					}
				}catch(AppException e){
					e.printStackTrace();
				}
				String nodeh="";
				if(listh!=null&&listh.size()>0){
					nodeh=(String)listh.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
				}else{
					this.setSelfDefResData("1@@@"+"程序出错!");
				}
				
				//sql 不会写，使用java递归循环实现
				if(idcheckedarr.length()>0){//有选择
					map = this.operateCheckedData(idcheckedarr);
					Set<String> set = map.keySet();
					Sheet sheet =workbook.createSheet();
					 workbook.setSheetName(0, "机构信息");
					 Map<String, Integer> maprow=new HashMap<String, Integer>();
					 maprow.put("rownum", 0);
					 this.exportSelectMysqlSheet(workbook, sheet);
					for(String sv : set){
						if(map.containsValue(sv)){
							exportSelectMysql2(sv, sheet, mapmap, workbook, 0, maprow, nodeh, cueUserid, false);
						}else{
							exportSelectMysql2(sv, sheet, mapmap, workbook, 0, maprow, nodeh, cueUserid, true);
						}
					}
				}else{
					String node=request.getParameter("root_id");
					
					if(node.equals("-1")){//没有选中
						//if("0".equals(type)||"1".equals(type)||"7".equals(type)||"2".equals(type)){//是否机构信息，人员批量转移,机构排序界面的请求，成立，则是
							//获取用户权限范围内的最高级别单位的id
						 	List<HashMap> list1 =null;
							try {
								if(DBType.ORACLE==DBUtil.getDBType()){
									sql="select b0111 from ( select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) asc) where ROWNUM =1";
									list1=CommonQueryBS.getQueryInfoByManulSQL(sql);
								}else if(DBType.MYSQL==DBUtil.getDBType()){
									sql="select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) asc limit 1";
									list1=CommonQueryBS.getQueryInfoByManulSQL(sql);
								}
							}catch(AppException e){
								e.printStackTrace();
							}
							if(list1!=null&&list1.size()>0){
								node=(String)list1.get(0).get("b0111");//获取用户权限范围内的最高级别单位的id
							}else{
								this.setSelfDefResData("1@@@"+"程序出错!");
							}
					}
					 Sheet sheet =workbook.createSheet();
					 String name=HBUtil.getValueFromTab("b0101", "b01", "b0111='"+node+"' ");
					 workbook.setSheetName(0, "机构信息");
					 int j=0;//级别
					 Map<String, Integer> maprow=new HashMap<String, Integer>();
					 maprow.put("rownum", 0);//行数
					 exportSelectMysql(node,workbook,sheet,mapmap,j,maprow,nodeh);
				}
			}
			
			//导出excel
			
			Date now = new Date();
			DateFormat d1 = DateFormat.getDateInstance();
			String timeNmae = d1.format(now);
			String userid=SysUtil.getCacheCurrentUser().getId();
			String sourcePath = AppConfig.HZB_PATH + "/temp/excel/jjxxdc_"+userid;//与用户一一对应的文件夹
			String ss=File.separatorChar+"";
			sourcePath=sourcePath.replace("/", ss); 
			sourcePath=sourcePath.replace("\\", ss);
			File file = new File(sourcePath);
			if(file.exists()){//如果存在，则删除文件夹下的老数据
				deleteDirectory(sourcePath);
			}
			if(!file.exists()){//如果不存在则是第一次导出，新建文件夹
				file.mkdirs();
			}
			file = new File((sourcePath+"/export_"+timeNmae+".xls").replace("/", ss).replace("\\", ss));
			os = new FileOutputStream(file);
			os.flush();
			workbook.write(os);
			os.close();
			workbook.close();
			sourcePath=(sourcePath+"/export_"+timeNmae+".xls").replace("/", ss).replace("\\", ss);
			sourcePath=sourcePath.replace("@@", "");//路径中不能含有@@
			sourcePath=sourcePath.replace(ss, "@@");//替换分隔符
			workbook.close();
			this.setSelfDefResData("2@@@"+sourcePath);
		} catch (Exception e) {
			if(os!=null){
				try{
					os.close();
				}catch(Exception e1){
				}
			}
			if(workbook!=null){
				try{
					workbook.close();
				}catch(Exception e2){
					
				}
			}
			this.setSelfDefResData("1@@@导出失败!");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void exportSelectMysql(String id,Workbook workbook,Sheet sheet,Map<String,Map<String,String>> mapmap,int j, Map<String, Integer> maprow,String nodeh) throws AppException{
		try{
			//设置标题的宽
			sheet.setColumnWidth(0, 6 * 256);
			sheet.setColumnWidth(1, 25 * 256);
			sheet.setColumnWidth(2, 50 * 256);
			sheet.setColumnWidth(3, 50 * 256);
			sheet.setColumnWidth(4, 20 * 256);
			
			//标题样式
			CellStyle styleThin = workbook.createCellStyle();
//			styleThin.setBorderLeft(CellStyle.BORDER_THIN);
//			styleThin.setBorderRight(CellStyle.BORDER_THIN);
//			styleThin.setBorderBottom(CellStyle.BORDER_THIN);
//			styleThin.setBorderTop(CellStyle.BORDER_THIN);
			styleThin.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
			//标题字体
			//Font headFont = workbook.createFont();
//			headFont.setFontName("黑体");
//			headFont.setFontHeightInPoints((short) 13);
//			styleThin.setFont(headFont);
			//内容样式
			styleThick = workbook.createCellStyle();
//			styleThick.setBorderLeft(CellStyle.BORDER_THIN);
//			styleThick.setBorderRight(CellStyle.BORDER_THIN);
//			styleThick.setBorderBottom(CellStyle.BORDER_THIN);
//			styleThick.setBorderTop(CellStyle.BORDER_THIN);
			styleThick.setAlignment(HSSFCellStyle.ALIGN_LEFT);//居中
			//标题行
			Row row = sheet.createRow(0);
			//设置标题的高度
			row.setHeightInPoints(19);
			Cell cell = row.createCell(0);
			cell.setCellValue("序号");
			cell.setCellStyle(styleThin);
			cell = row.createCell(1);
			cell.setCellValue("机构名称");
			cell.setCellStyle(styleThin);
			cell = row.createCell(2);
			cell.setCellValue("机构编码");
			cell.setCellStyle(styleThin);
			cell = row.createCell(3);
			cell.setCellValue("机构类型");
			cell.setCellStyle(styleThin);
			cell = row.createCell(4);
			cell.setCellValue("所在政区");
			cell.setCellStyle(styleThin);
			cell = row.createCell(5);
			cell.setCellValue("隶属关系");
			cell.setCellStyle(styleThin);
			cell = row.createCell(6);
			cell.setCellValue("机构类别");
			cell.setCellStyle(styleThin);
			cell = row.createCell(7);
			cell.setCellValue("机构级别 ");
			cell.setCellStyle(styleThin);
			
			String ss="";
			for(int i=0;i<j;i++){
				ss=ss+"    ";
			}
			String cueUserid=SysUtil.getCacheCurrentUser().getId();
			String sql=" select CONCAT('"+ss+"',b.b0101) b0101,"//机构名称
						  		+ " b.b0114,"//机构编码
						  		+ " b.b0194,"//机构类型
						  		+ " b.b0117,"//所在政区
						  		+ " b.b0124,"//隶属关系
						  		+ " b.b0131,"//机构类别
						  		+ " b.b0127"//机构级别  
						  		+ " from b01 b,competence_userdept t where b.b0111=t.b0111 and t.userid='"+cueUserid+"' and b.b0111='"+id+"' order by b.SORTID asc";
			CommQuery cq=new CommQuery();
			List<HashMap<String, Object>> list=cq.getListBySQL(sql);
			j++;
			for(int i=0;i<list.size();i++){
				//本级第一个单位 写收入
				exportMysqlRealize(list.get(i),sheet,mapmap,workbook,j,maprow,nodeh,cueUserid);
				//写入下级，调用递归方法
				exportSelectMysql1(id,sheet,mapmap,workbook,j,maprow,nodeh,cueUserid);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void exportSelectMysqlSheet(Workbook workbook,Sheet sheet) throws AppException{
		try{
			//设置标题的宽
			sheet.setColumnWidth(0, 6 * 256);
			sheet.setColumnWidth(1, 25 * 256);
			sheet.setColumnWidth(2, 50 * 256);
			sheet.setColumnWidth(3, 50 * 256);
			sheet.setColumnWidth(4, 20 * 256);
			
			//标题样式
			CellStyle styleThin = workbook.createCellStyle();
			styleThin.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
			//标题字体
			//内容样式
			styleThick = workbook.createCellStyle();
			styleThick.setAlignment(HSSFCellStyle.ALIGN_LEFT);//居中
			//标题行
			Row row = sheet.createRow(0);
			//设置标题的高度
			row.setHeightInPoints(19);
			Cell cell = row.createCell(0);
			cell.setCellValue("序号");
			cell.setCellStyle(styleThin);
			cell = row.createCell(1);
			cell.setCellValue("机构名称");
			cell.setCellStyle(styleThin);
			cell = row.createCell(2);
			cell.setCellValue("机构编码");
			cell.setCellStyle(styleThin);
			cell = row.createCell(3);
			cell.setCellValue("机构类型");
			cell.setCellStyle(styleThin);
			cell = row.createCell(4);
			cell.setCellValue("所在政区");
			cell.setCellStyle(styleThin);
			cell = row.createCell(5);
			cell.setCellValue("隶属关系");
			cell.setCellStyle(styleThin);
			cell = row.createCell(6);
			cell.setCellValue("机构类别");
			cell.setCellStyle(styleThin);
			cell = row.createCell(7);
			cell.setCellValue("机构级别 ");
			cell.setCellStyle(styleThin);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void exportSelectMysql1(String id,Sheet sheet,Map<String,Map<String,String>> mapmap,Workbook workbook,int j,Map<String, Integer> maprow,String nodeh,String cueUserid) throws AppException{
		
		try{
			String ss="";
			for(int i=0;i<j;i++){
				ss=ss+"    ";
			}
			String sql=" select CONCAT('"+ss+"',b.b0101) b0101,"//机构名称
						  		+ " b.b0114,"//机构编码
						  		+ " b.b0194,"//机构类型
						  		+ " b.b0117,"//所在政区
						  		+ " b.b0124,"//隶属关系
						  		+ " b.b0131,"//机构类别
						  		+ " b.b0127,"//机构级别 
						  		+ " b.b0111 "
						  		+ " from b01 b,competence_userdept t where b.b0111=t.b0111 and t.userid='"+cueUserid+"' and b.b0121='"+id+"' order by b.SORTID asc";
			CommQuery cq=new CommQuery();
			List<HashMap<String, Object>> list=cq.getListBySQL(sql);
			j++;
			for(int i=0;i<list.size();i++){
				//本级第一个单位 写收入
				exportMysqlRealize(list.get(i),sheet,mapmap,workbook,j,maprow,nodeh,cueUserid);
				//写入下级，调用递归方法
				if(id.length()>=nodeh.length()){//id<nodeh 则已经到达最底层，（id当前节点，nodeh最低级别id中的一个）
					continue;
				}else{
					exportSelectMysql1(((String)list.get(i).get("b0111")),sheet,mapmap,workbook,j,maprow,nodeh,cueUserid);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void exportSelectMysql2(String id,Sheet sheet,Map<String,Map<String,String>> mapmap,Workbook workbook,int j,Map<String, Integer> maprow,String nodeh, String cueUserid, boolean flag) throws AppException{
		
		try{
			String ss="";
			for(int i=0;i<j;i++){
				ss=ss+"    ";
			}
			String sql=" select CONCAT('"+ss+"',b.b0101) b0101,"//机构名称
						  		+ " b.b0114,"//机构编码
						  		+ " b.b0194,"//机构类型
						  		+ " b.b0117,"//所在政区
						  		+ " b.b0124,"//隶属关系
						  		+ " b.b0131,"//机构类别
						  		+ " b.b0127,"//机构级别  
						  		+ " b.b0111 "
						  		+ " from b01 b,competence_userdept t where b.b0111=t.b0111 and t.userid='"+cueUserid+"' and b.b0111='"+id+"' order by b.SORTID asc";
			CommQuery cq=new CommQuery();
			List<HashMap<String, Object>> list=cq.getListBySQL(sql);
			j++;
			for(int i=0;i<list.size();i++){
				//本级第一个单位 写收入
				exportMysqlRealize(list.get(i),sheet,mapmap,workbook,j,maprow,nodeh,cueUserid);
				if(flag)
					exportSelectMysql1(((String)list.get(i).get("b0111")),sheet,mapmap,workbook,j,maprow,nodeh,cueUserid);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void exportMysqlRealize(HashMap<String, Object> listmap,Sheet sheet,Map<String,Map<String,String>> map,Workbook workbook,int j,Map<String, Integer> maprow,String nodeh,String cueUserid){
		try{
			Row row=null;
			Cell cell=null;
			row = sheet.createRow((maprow.get("rownum") ));//行
			//row = sheet.createRow((maprow.get("rownum")));//行----------可能要改序列，还没测到2017版
			cell = row.createCell(0);
			cell.setCellValue((maprow.get("rownum") ));
			//cell.setCellValue((maprow.get("rownum")));//行----------可能要改序列，还没测到2017版
			styleThick.setAlignment(HSSFCellStyle.ALIGN_LEFT);//
			cell.setCellStyle(styleThick);
			cell = row.createCell(1);
			cell.setCellValue((String)listmap.get("b0101"));//机构名称
			cell.setCellStyle(styleThick);
			cell = row.createCell(2);
			cell.setCellValue((String)listmap.get("b0114"));//机构编码
			cell.setCellStyle(styleThick);
			cell = row.createCell(3);
			
			cell.setCellValue(map.get("B0194").get((String)listmap.get("b0194")));//机构类型
			cell.setCellStyle(styleThick);
			cell = row.createCell(4);
			cell.setCellValue(map.get("ZB01").get((String)listmap.get("b0117")));//所在政区
			cell.setCellStyle(styleThick);
			
			cell = row.createCell(5);
			cell.setCellValue(map.get("ZB87").get((String)listmap.get("b0124")));//隶属关系
			cell.setCellStyle(styleThick);
			cell = row.createCell(6);
			cell.setCellValue(map.get("ZB04").get((String)listmap.get("b0131")));//机构类别
			cell.setCellStyle(styleThick);
			cell = row.createCell(7);
			cell.setCellValue(map.get("ZB03").get((String)listmap.get("b0127")));//机构级别 
			cell.setCellStyle(styleThick);
			maprow.put("rownum", (maprow.get("rownum")+1));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private void exportSelect(List<HashMap<String, Object>> list,Workbook workbook,Sheet sheet,Map<String,Map<String,String>> map) throws IOException {
		try{
			//设置标题的宽
			sheet.setColumnWidth(0, 6 * 256);
			sheet.setColumnWidth(1, 25 * 256);
			sheet.setColumnWidth(2, 50 * 256);
			sheet.setColumnWidth(3, 50 * 256);
			sheet.setColumnWidth(4, 20 * 256);
			
			//标题样式
			CellStyle styleThin = workbook.createCellStyle();
//			styleThin.setBorderLeft(CellStyle.BORDER_THIN);
//			styleThin.setBorderRight(CellStyle.BORDER_THIN);
//			styleThin.setBorderBottom(CellStyle.BORDER_THIN);
//			styleThin.setBorderTop(CellStyle.BORDER_THIN);
			styleThin.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
			//标题字体
			//Font headFont = workbook.createFont();
//			headFont.setFontName("黑体");
//			headFont.setFontHeightInPoints((short) 13);
//			styleThin.setFont(headFont);
			//内容样式
			styleThick = workbook.createCellStyle();
//			styleThick.setBorderLeft(CellStyle.BORDER_THIN);
//			styleThick.setBorderRight(CellStyle.BORDER_THIN);
//			styleThick.setBorderBottom(CellStyle.BORDER_THIN);
//			styleThick.setBorderTop(CellStyle.BORDER_THIN);
			styleThick.setAlignment(HSSFCellStyle.ALIGN_LEFT);//居中
			//标题行
			Row row = sheet.createRow(0);
			//设置标题的高度
			row.setHeightInPoints(19);
			Cell cell = row.createCell(0);
			cell.setCellValue("序号");
			cell.setCellStyle(styleThin);
			cell = row.createCell(1);
			cell.setCellValue("机构名称");
			cell.setCellStyle(styleThin);
			cell = row.createCell(2);
			cell.setCellValue("机构编码");
			cell.setCellStyle(styleThin);
			cell = row.createCell(3);
			cell.setCellValue("机构类型");
			cell.setCellStyle(styleThin);
			cell = row.createCell(4);
			cell.setCellValue("所在政区");
			cell.setCellStyle(styleThin);
			cell = row.createCell(5);
			cell.setCellValue("隶属关系");
			cell.setCellStyle(styleThin);
			cell = row.createCell(6);
			cell.setCellValue("机构类别");
			cell.setCellStyle(styleThin);
			cell = row.createCell(7);
			cell.setCellValue("机构级别 ");
			cell.setCellStyle(styleThin);
			for(int i=0;i<list.size();i++){
				row = sheet.createRow(i+1);
				cell = row.createCell(0);
				cell.setCellValue(i+1);
				cell.setCellStyle(styleThick);
				cell = row.createCell(1);
				cell.setCellValue((String)list.get(i).get("b0101"));//机构名称
				cell.setCellStyle(styleThick);
				cell = row.createCell(2);
				cell.setCellValue((String)list.get(i).get("b0114"));//机构编码
				cell.setCellStyle(styleThick);
				cell = row.createCell(3);
				
				cell.setCellValue(map.get("B0194").get((String)list.get(i).get("b0194")));//机构类型
				cell.setCellStyle(styleThick);
				cell = row.createCell(4);
				cell.setCellValue(map.get("ZB01").get((String)list.get(i).get("b0117")));//所在政区
				cell.setCellStyle(styleThick);
				
				cell = row.createCell(5);
				cell.setCellValue(map.get("ZB87").get((String)list.get(i).get("b0124")));//隶属关系
				cell.setCellStyle(styleThick);
				cell = row.createCell(6);
				cell.setCellValue(map.get("ZB04").get((String)list.get(i).get("b0131")));//机构类别
				cell.setCellStyle(styleThick);
				cell = row.createCell(7);
				cell.setCellValue(map.get("ZB03").get((String)list.get(i).get("b0127")));//机构级别 
				cell.setCellStyle(styleThick);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
		
	}
	
	//导出excel
	private void exportSelectTm(Workbook workbook,Sheet sheet,Map<String,Map<String,String>> map, Map<String,Integer> maprow){
		try{
			//设置标题的宽
			sheet.setColumnWidth(0, 6 * 256);
			sheet.setColumnWidth(1, 25 * 256);
			sheet.setColumnWidth(2, 50 * 256);
			sheet.setColumnWidth(3, 50 * 256);
			sheet.setColumnWidth(4, 20 * 256);
			
			//标题样式
			CellStyle styleThin = workbook.createCellStyle();
			styleThin.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
			//标题字体
			//内容样式
			styleThick = workbook.createCellStyle();
			styleThick.setAlignment(HSSFCellStyle.ALIGN_LEFT);//居中
			//标题行
			Row row = sheet.createRow(0);
			//设置标题的高度
			row.setHeightInPoints(19);
			Cell cell = row.createCell(0);
			cell.setCellValue("序号");
			cell.setCellStyle(styleThin);
			cell = row.createCell(1);
			cell.setCellValue("机构名称");
			cell.setCellStyle(styleThin);
			cell = row.createCell(2);
			cell.setCellValue("机构编码");
			cell.setCellStyle(styleThin);
			cell = row.createCell(3);
			cell.setCellValue("机构类型");
			cell.setCellStyle(styleThin);
			cell = row.createCell(4);
			cell.setCellValue("所在政区");
			cell.setCellStyle(styleThin);
			cell = row.createCell(5);
			cell.setCellValue("隶属关系");
			cell.setCellStyle(styleThin);
			cell = row.createCell(6);
			cell.setCellValue("机构类别");
			cell.setCellStyle(styleThin);
			cell = row.createCell(7);
			cell.setCellValue("机构级别 ");
			cell.setCellStyle(styleThin);
			maprow.put("rownum", 1);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void exportSelectData(List<HashMap<String, Object>> list,Workbook workbook,Sheet sheet,Map<String,Map<String,String>> map, Map<String,Integer> maprow) throws IOException {
		try{
			for(int i=0;i<list.size();i++){
				exportMysqlRealize(list.get(i), sheet, map, workbook, i, maprow, null, null);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
		
	}
	
	/**
	 * 删除文件夹下的文件及其子目录
	 * @param sourcePath
	 * @throws FileNotFoundException 
	 */
	public void deleteDirectory(String sourcePath) throws FileNotFoundException{
		try{
			//如果sPath不以文件分隔符结尾，自动添加文件分隔符  
			if (!sourcePath.endsWith(File.separator)) {  
				sourcePath = sourcePath + File.separator;  
			 } 
			File dirFile = new File(sourcePath);
			//如果dir对应是一个目录  
			 if (dirFile.isDirectory()) {  
					//删除文件夹下的所有文件(包括子目录)  
					File[] files = dirFile.listFiles();  
					for (int i = 0; i < files.length; i++) {  
					    //删除子文件  
					    if (files[i].isFile()) {  
					    	File file = new File(files[i].getAbsolutePath());  
					        // 路径为文件
					        if (file.isFile() && file.exists()) {  
					            file.delete();  
					        } 

					    } //删除子目录  
					    else {  
					    	//删除当前文件及其子目录
					    	deleteDirectorall(files[i].getAbsolutePath());
					       //deleteDirectory(files[i].getAbsolutePath());  
					    }  
					}  
			 }
		}catch(Exception e){
			 throw new FileNotFoundException(e.getMessage());
		}
	}
	
	/**
	 * 删除此文件夹，及其子文件与子目录
	 * @param sourcePath
	 */
	public void deleteDirectorall(String sourcePath){
		try{
			//如果sPath不以文件分隔符结尾，自动添加文件分隔符  
			if (!sourcePath.endsWith(File.separator)) {  
				sourcePath = sourcePath + File.separator;  
			 } 
			File dirFile = new File(sourcePath);
			//如果dir对应是一个目录  
			 if (dirFile.isDirectory()) {  
					//删除文件夹下的所有文件(包括子目录)  
					File[] files = dirFile.listFiles();  
					for (int i = 0; i < files.length; i++) {  
					    //删除子文件  
					    if (files[i].isFile()) {  
					    	File file = new File(files[i].getAbsolutePath());  
					        // 路径为文件且不为空则进行删除  
					        if (file.isFile() && file.exists()) {  
					            file.delete();  
					        } 

					    } //删除子目录  
					    else {  
					    	deleteDirectorall(files[i].getAbsolutePath());  
					    }  
					}  
					//删除当前目录  
					dirFile.delete();
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private Map<String, String> operateCheckedData(String idcheckedarr){
		Map<String, String> map = new LinkedHashMap<String, String>();
		String [] arr=idcheckedarr.split(",");
		for(String str : arr){
			String[] s = str.split("@");
			map.put(s[0], s[0].contains(".") ? s[0].substring(0,s[0].lastIndexOf(".")) : "1");
		}
		return map;
	}
}
