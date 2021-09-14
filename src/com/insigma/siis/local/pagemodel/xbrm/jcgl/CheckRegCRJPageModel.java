package com.insigma.siis.local.pagemodel.xbrm.jcgl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.JUpload.JUpload;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;

public class CheckRegCRJPageModel extends PageModel implements JUpload {
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		
		String checkregid = this.getPageElement("checkregid").getValue();
		String sql="select * from CHECKREGCRJZJ t where CHECKREGID='"+checkregid
				+"' order by to_number(crcrjzj001),SORTID ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid2.dogridquery")
	public int doMemberQuery2(int start,int limit) throws RadowException{
		String zjid = this.getPageElement("checkregcrjzjid").getValue();
		String sql="select * from CHECKREGCRJJL t where checkregcrjzjid='"+zjid
				+"' order by SORTID desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("doInitX")
	public int doInitX() throws RadowException, AppException{
		String checkregid = this.getPageElement("checkregid").getValue();
		try {
			CommQuery cqbs=new CommQuery();
			String spasql = "select ckfileid,filesize,filename from CHECKREGFILE where "
					+ " checkregid='" +checkregid+"' and filetype='crcrj'";
			List<HashMap<String, Object>> spalist=cqbs.getListBySQL(spasql.toString());
			if (spalist != null) {
				List<Map<String, String>> list_temp=null;
				for (HashMap<String, Object> ta : spalist) {
					
					Map<String, String> map_temp = new HashMap<String, String>();
					list_temp = new ArrayList<Map<String, String>>();
					map_temp.put("id", ta.get("ckfileid")+"");
					map_temp.put("name", ta.get("filename")+"");
					map_temp.put("fileSize", ta.get("filesize")+"");
					list_temp.add(map_temp);
				}
				//遍历map
				this.setFilesInfo("crcrj", list_temp, false);
			}
			
			this.setNextEventName("memberGrid.dogridquery");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException {
		
		this.setNextEventName("doInitX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveBtn.onclick")
	public int saveBtn() throws RadowException{
		this.upLoadFile("crcrj");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 获得文件名称
		String isfile = formDataMap.get("Filename");
		// 判断是否上传了附件，没有上传则不进行文件处理
		if (isfile != null && !isfile.equals("")) {
			try {
				// 获取表单信息
				FileItem fi = fileItem.get(0);
				DecimalFormat df = new DecimalFormat("#.00");
				String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
				// 如果文件大于1M则显示M，小于则显示kb
				if (fi.getSize() < 1048576) {
					fileSize = (int) fi.getSize() / 1024 + "KB";
				}
				if (fi.getSize() < 1024) {
					fileSize = (int) fi.getSize() / 1024 + "B";
				}
				String id = saveFile(formDataMap, fi,fileSize);
				map.put("file_pk", id);
				map.put("file_name", isfile);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static String  disk = AppConfig.HZB_PATH + "/";
	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// 获得人员信息id
		String filename = formDataMap.get("Filename");
		String classAtt = formDataMap.get("fileid");
		
		String checkregid = formDataMap.get("checkregid");
		String id = UUID.randomUUID().toString();
		String directory = "checkregfiles" + File.separator +checkregid+ File.separator;
		String filePath = directory  + id;
		File f = new File(disk + directory);
		if(!f.isDirectory()){
			f.mkdirs();
		}
		HBSession sess = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		Connection conn = null;
		InputStream in = null;
		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			
			String sql = "insert into CHECKREGFILE values(?,?,?,?,?, ?,?,?)";
			System.out.println(sql);
			ps = conn.prepareStatement(sql.toString());
			int fidex = 1;
			ps.setString(fidex++, id);
			ps.setString(fidex++, checkregid);
			
			ps.setString(fidex++, filename);
			ps.setString(fidex++, SysManagerUtils.getUserId());
			ps.setDate(fidex++, DateUtil.date2sqlDate(new Date()));
			ps.setString(fidex++, directory);
			ps.setString(fidex++, fileSize);
			ps.setString(fidex++, classAtt);
			
			fi.write(new File(disk + filePath));
			
			
			String sql2 = "insert into CHECKREGCRJZJ values (?,?,?,?,?, ?,?,?,?,?, ?,?)";
			ps2 = conn.prepareStatement(sql2.toString());
			String sql3 = "insert into CHECKREGCRJJL values (?,?,?,?,?, ?,?,?,?,?, ?,?)";
			ps3 = conn.prepareStatement(sql3.toString());
			in = new FileInputStream(new File(disk + filePath));
			Workbook wb = null;
			if (filename.toLowerCase().endsWith("xls")) { // Excel 2003
				wb = new HSSFWorkbook(in);
			} else if (filename.toLowerCase().endsWith("xlsx")) { // Excel 2007/2010
				wb = new XSSFWorkbook(in);
			}
			Sheet sheet = wb.getSheetAt(0);
			List<CellRangeAddress> CombineCells  = getCombineCell(sheet);
			int rowNum =sheet.getLastRowNum();//总行数
			
			int row1firstC = 0;
	        int row1lastC = 0;
	        int row1firstR = 0;
	        int row1lastR = 0;
	        
	        int row3firstC = 0;
	        int row3lastC = 0;
	        int row3firstR = 0;
	        int row3lastR = 0;
			String values[] = {"","","",""};
	        
			for (int i = 2; i <= rowNum; i++) {
				String cellValue = null;
				if(i <= row1lastR) {
					if(i <= row3lastR) {
						for(int x=9;x<16;x++){
							if(x == 9) {
								String cv = getCellValue(sheet.getRow(i).getCell(x));
								if(cv.equals("未查到出入境记录")) {
									break;
								} else {
									ps3.setString(x - 5, cv);
								}
							} else if(x >9 && x < 15) {
								String cv = getCellValue(sheet.getRow(i).getCell(x));
								ps3.setString(x - 5, cv);
							} else if(x == 15) {
								String cv = getCellValue(sheet.getRow(i).getCell(x));
								ps3.setString(x - 5, cv);
								ps3.setString(1, UUID.randomUUID().toString());
								ps3.setString(2, values[3]);
								ps3.setString(3, checkregid);
								ps3.setString(11, values[1]);
								ps3.setInt(12, i);
								ps3.addBatch();
							}
						
						}
					} else {
						for(int x=2;x<16;x++){
							if(x == 2) {
								boolean boo = false;
								for (CellRangeAddress ca : CombineCells) {
									//获得合并单元格的起始行, 结束行, 起始列, 结束列
									row3firstC = ca.getFirstColumn();
									row3lastC = ca.getLastColumn();
									row3firstR = ca.getFirstRow();
									row3lastR = ca.getLastRow();
						            if(i >= row3firstR && i <= row3lastR) {
						                if(x >= row3firstC && x <= row3lastC) {
						                    Row fRow = sheet.getRow(row3firstR);
						                    Cell fCell = fRow.getCell(row3firstC);
						                    cellValue = getCellValue(fCell);
						                    boo = true;
						                    break;
						                }
						            } else {
						                cellValue = "";
						            }
								}
								if(boo) {
									values[2] =cellValue; 
								} else {
									row3firstC = x;
							        row3lastC = x;
							        row3firstR = i;
							        row3lastR = i;
							        cellValue = getCellValue(sheet.getRow(i).getCell(x));
							        values[2] = cellValue;
								}
								if(cellValue==null || "".equals(cellValue) ||
										"未查到出入境证件".equals(cellValue)) {
									String newid = UUID.randomUUID().toString();
									values[3] = newid;
									ps2.setString(1, newid);
									ps2.setString(2, checkregid);
									ps2.setString(3, values[0]);
									ps2.setString(4, values[1]);
									ps2.setString(5, "");
									ps2.setString(6, "");
									ps2.setString(7, "");
									ps2.setString(8, "");
									ps2.setString(9, "");
									ps2.setString(10, "");
									ps2.setString(11, "");
									ps2.setInt(12, i);
									ps2.addBatch();
									break;
								}
							} else if(x >2 && x < 8) {
								ps2.setString(x + 3, getCellValue(sheet.getRow(i).getCell(x)));
							} else if(x == 8) {
								ps2.setString(x + 3, getCellValue(sheet.getRow(i).getCell(x)));
								String newid = UUID.randomUUID().toString();
								values[3] = newid;
								ps2.setString(1, newid);
								ps2.setString(2, checkregid);
								ps2.setString(3, values[0]);
								ps2.setString(4, values[1]);
								ps2.setString(5, values[2]);
								ps2.setInt(12, i);
								ps2.addBatch();
							} else if(x == 9) {
								String cv = getCellValue(sheet.getRow(i).getCell(x));
								if(cv.equals("未查到出入境记录")) {
									break;
								} else {
									ps3.setString(x - 5, cv);
								}
							} else if(x >9 && x < 15) {
								String cv = getCellValue(sheet.getRow(i).getCell(x));
								/*if((x - 5) == 9) {
									if(cv.length()>30) {
										System.out.println(i +"----------" +cv);
									}
								}*/
								ps3.setString(x - 5, cv);
							} else if(x == 15) {
								String cv = getCellValue(sheet.getRow(i).getCell(x));
								ps3.setString(x - 5, cv);
								ps3.setString(1, UUID.randomUUID().toString());
								ps3.setString(2, values[3]);
								ps3.setString(3, checkregid);
								ps3.setString(11, values[1]);
								ps3.setInt(12, i);
								ps3.addBatch();
							}
						
						}
					}
				} else {
					for(int x=0;x<16;x++){
						if(x == 0) {
							boolean boo = false;
							for (CellRangeAddress ca : CombineCells) {
								//获得合并单元格的起始行, 结束行, 起始列, 结束列
								row1firstC = ca.getFirstColumn();
								row1lastC = ca.getLastColumn();
								row1firstR = ca.getFirstRow();
								row1lastR = ca.getLastRow();
					            if(i >= row1firstR && i <= row1lastR) {
					                if(x >= row1firstC && x <= row1lastC) {
					                    Row fRow = sheet.getRow(row1firstR);
					                    Cell fCell = fRow.getCell(row1firstC);
					                    cellValue = getCellValue(fCell);
					                    boo = true;
					                    break;
					                }
					            } else {
					                cellValue = "";
					            }
							}
							if(boo) {
								if(row1firstC != row1lastC) {
									break;
								}
								values[0] =cellValue; 
							} else {
								row1firstC = x;
						        row1lastC = x;
						        row1firstR = i;
						        row1lastR = i;
						        values[0] = getCellValue(sheet.getRow(i).getCell(x));
						        if(values[0] == null || values[0].equals("")) {
									break;
								}
							}
						} else if(x == 1) {
							values[1] = getCellValue(sheet.getRow(i).getCell(16));
						} else if(x == 2) {
							boolean boo = false;
							for (CellRangeAddress ca : CombineCells) {
								//获得合并单元格的起始行, 结束行, 起始列, 结束列
								row3firstC = ca.getFirstColumn();
								row3lastC = ca.getLastColumn();
								row3firstR = ca.getFirstRow();
								row3lastR = ca.getLastRow();
					            if(i >= row3firstR && i <= row3lastR) {
					                if(x >= row3firstC && x <= row3lastC) {
					                    Row fRow = sheet.getRow(row3firstR);
					                    Cell fCell = fRow.getCell(row3firstC);
					                    cellValue = getCellValue(fCell);
					                    boo = true;
					                    break;
					                }
					            } else {
					                cellValue = "";
					            }
							}
							if(boo) {
								values[2] =cellValue; 
							} else {
								row3firstC = x;
						        row3lastC = x;
						        row3firstR = i;
						        row3lastR = i;
						        cellValue = getCellValue(sheet.getRow(i).getCell(x));
						        values[2] = cellValue;
							}
							if(cellValue==null || "".equals(cellValue) ||
									"未查到出入境证件".equals(cellValue)) {
								String newid = UUID.randomUUID().toString();
								values[3] = newid;
								ps2.setString(1, newid);
								ps2.setString(2, checkregid);
								ps2.setString(3, values[0]);
								ps2.setString(4, values[1]);
								ps2.setString(5, "");
								ps2.setString(6, "");
								ps2.setString(7, "");
								ps2.setString(8, "");
								ps2.setString(9, "");
								ps2.setString(10, "");
								ps2.setString(11, "");
								ps2.setInt(12, i);
								ps2.addBatch();
								break;
							}
						} else if(x >2 && x < 8) {
							ps2.setString(x + 3, getCellValue(sheet.getRow(i).getCell(x)));
						} else if(x == 8) {
							ps2.setString(x + 3, getCellValue(sheet.getRow(i).getCell(x)));
							String newid = UUID.randomUUID().toString();
							values[3] = newid;
							ps2.setString(1, newid);
							ps2.setString(2, checkregid);
							ps2.setString(3, values[0]);
							ps2.setString(4, values[1]);
							ps2.setString(5, values[2]);
							ps2.setInt(12, i);
							ps2.addBatch();
						} else if(x == 9) {
							String cv = getCellValue(sheet.getRow(i).getCell(x));
							if(cv.equals("未查到出入境记录")) {
								break;
							} else {
								ps3.setString(x - 5, cv);
							}
						} else if(x >9 && x < 15) {
							String cv = getCellValue(sheet.getRow(i).getCell(x));
							ps3.setString(x - 5, cv);
						} else if(x == 15) {
							String cv = getCellValue(sheet.getRow(i).getCell(x));
							ps3.setString(x - 5, cv);
							ps3.setString(1, UUID.randomUUID().toString());
							ps3.setString(2, values[3]);
							ps3.setString(3, checkregid);
							ps3.setString(11, values[1]);
							ps3.setInt(12, i);
							ps3.addBatch();
						}
					
					}
				}
				if(i% 5000 ==0) {
					ps2.executeBatch();
					ps2.clearBatch();
					ps2.clearParameters();
					ps3.executeBatch();
					ps3.clearBatch();
					ps3.clearParameters();
				}
			}
			ps2.executeBatch();
			ps2.clearBatch();
			ps2.clearParameters();
			ps3.executeBatch();
			ps3.clearBatch();
			ps3.clearParameters();
			ps.executeUpdate();
			
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			try{
				if(conn!=null)
					conn.rollback();
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new RadowException("上传附件失败！");
		} finally {
			try{
				if(ps2!=null)
					ps.close();
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
				if(in!=null)
					in.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		return id;
	}
	
	/**
     * 获取单元格的值
     * @param cell
     * @return
     */
    public  String getCellValue(Cell cell){
    	//判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
        case HSSFCell.CELL_TYPE_NUMERIC:  
            DecimalFormat df = new DecimalFormat("0");  
            cellValue =  df.format(cell.getNumericCellValue());
            break;  
        case HSSFCell.CELL_TYPE_STRING:  
        	cellValue= cell.getStringCellValue().trim();
            cellValue=StringUtils.isEmpty(cellValue) ? "" : cellValue; 
            break;  
        case HSSFCell.CELL_TYPE_BOOLEAN:  
        	cellValue = String.valueOf(cell.getBooleanCellValue());  
            break;  
        case HSSFCell.CELL_TYPE_FORMULA: 
            cellValue = cell.getCellFormula();
            break;  
        default:  
            System.out.println("unsuported sell type");  
        break;  
        }
        return cellValue;
       
    }


    /**
     * 合并单元格处理,获取合并行
     * @param sheet
     * @return List<CellRangeAddress>
     */
    public  List<CellRangeAddress> getCombineCell(Sheet sheet) {
        List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
        //获得一个 sheet 中合并单元格的数量
        int sheetmergerCount = sheet.getNumMergedRegions();
        //遍历所有的合并单元格
        for(int i = 0; i<sheetmergerCount;i++) {
            //获得合并单元格保存进list中
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }
        return list;
    }

	@Override
	public String deleteFile(String id) {
		
		try {
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> list = sess.createSQLQuery("select CKFILEID,FDIRECTORY,FILENAME,checkregid from CHECKREGFILE "
					+ " where CKFILEID = '" +id + "'").list();
			if(list==null || list.size()==0){
				return null;//删除失败
			}
			Object[] arr = list.get(0);
			String checkregid = arr[3]+"";
			String directory = disk+arr[1];
			File f = new File(directory+id);
			if(f.isFile()){
				f.delete();
			}
			sess.createSQLQuery("delete from CHECKREGFILE "
					+ " where CKFILEID = '" +id + "'").executeUpdate();
			sess.createSQLQuery("delete from CHECKREGCRJZJ "
					+ " where checkregid = '" +checkregid + "'").executeUpdate();
			sess.createSQLQuery("delete from CHECKREGCRJJL "
					+ " where checkregid = '" +checkregid + "'").executeUpdate();
			sess.flush();
			
			return id;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
	
}
