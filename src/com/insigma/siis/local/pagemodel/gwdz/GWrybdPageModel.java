package com.insigma.siis.local.pagemodel.gwdz;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONObject;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryPageModel;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.insigma.siis.local.pagemodel.xbrm2.YNTPFileExportBS;

import net.sf.json.JSONArray;

public class GWrybdPageModel extends PageModel {

    @Override
    public int doInit() throws RadowException {
    	this.setNextEventName("ryGrid.dogridquery");
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    
    @PageEvent("ryGrid.dogridquery")
	@NoRequiredValidate
	public int ryGridQuery(int start, int limit) throws RadowException, AppException, PrivilegeException {
    	String exa0000list=this.getPageElement("exa0000").getValue();
    	String[] arr = exa0000list.split(",");
		String mntp00=this.getPageElement("mntp00").getValue();
		String sql="select distinct a0101,a0192a,a0000,a0107" + 
				"          from (select " + 
				"                       GET_tpbXingming(a01.a0101," + 
				"                                       a01.a0104," + 
				"                                       a01.a0117," + 
				"                                       a01.a0141) a0101," + 
				"                       a01.a0192a a0192a,  substr(a01.a0107, 0, 4) || '.' ||" + 
				"                       substr(a01.a0107, 5, 2) a0107," + 
				"                       a01.a0000" + 
				"                  from v_mntp_gw_ry t," + 
				"                       a01," + 
				"                       (select *" + 
				"                          from HZ_MNTP_BZ" + 
				"                         where mntp00 = '"+mntp00+"'" + 
				"                           and a01bztype = '2') b" + 
				"                 where t.a0000 = a01.a0000(+)" + 
				"                   and t.a0000 is not null" + 
				"                   and t.fxyp07 = 1" + 
				"                   and t.mntp00 = '"+mntp00+"'" + 
				"                   and b.a01bzid(+) = t.tp0100                   "+
				"                       and  (select count(1)" + 
				"                          from v_mntp_gw_ry s" + 
				"                         where s.zwqc00 = t.zwqc00" + 
				"                           and s.fxyp00 = t.fxyp00" + 
				"                         group by s.zwqc00)>1" + 
				"                 order by t.fxyp00, t.sortnum)";
		if(arr.length>0) {
			for(int i=0;i<arr.length;i++) {
				if(!"".equals(arr[i]) && arr[i]!=null){
					sql+="   union all " + 
							"select distinct a0101, a0192a, a0000,substr(a01.a0107, 0, 4) || '.' ||   " + 
							"		substr(a01.a0107, 5, 2) a0107  from a01 where a0000='"+arr[i]+"'  ";
				}			
			}
		}
		
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
    /**
     * 获取显示行
     */
    @PageEvent("getDisplayRow")
    public int getDisplayRow() {
        try {
            List<Object[]> list = CommSQL.getA01ConfigTpbjChecked(SysManagerUtils.getUserId());
            Map res = new HashMap();
            res.put("data", list);
            this.setSelfDefResData(res);
            return EventRtnType.SPE_SUCCESS;
        } catch (AppException e) {
            e.printStackTrace();
            this.setMainMessage("操作失败：" + e.getDetailMessage());
            return EventRtnType.FAILD;
        }
    }

    /**
     * 获取显示列
     */
    @PageEvent("getDisplayCol")
    public int getDisplayCol() throws AppException {
        String idsJson = this.getParameter("ids");
        List<String> idsList = JSONObject.parseArray(idsJson, String.class);
        String fieldsJson = this.getParameter("fields");
        List<Object[]> fieldList = JSONObject.parseArray(fieldsJson, Object[].class);

        // 拼接查询SQL语句
        StringBuffer sb = new StringBuffer("select ");
        for (int i = 0; i < fieldList.size(); i++) {
            if (i == fieldList.size() - 1) {
                sb.append(fieldList.get(i)[1]+" " +fieldList.get(i)[0]);
            } else {
                sb.append(fieldList.get(i)[1]+" " +fieldList.get(i)[0]);
                sb.append(",");
            }
        }
        sb.append(" from A01 a  where a.A0000 in ('");
        for (int i = 0; i < idsList.size(); i++) {
            if (i == idsList.size() - 1) {
                sb.append(idsList.get(i));
            } else {
                sb.append(idsList.get(i));
                sb.append("','");
            }
        }
        sb.append("')");
        HBSession session = HBUtil.getHBSession();
        PreparedStatement ps = null;
        List<List<Map<String, String>>> result = new ArrayList<List<Map<String, String>>>();
        try {
            ps = session.connection().prepareStatement(sb.toString());
            ResultSet rs = ps.executeQuery();

            List<Map<String, String>> item;
            Map map;
            
            while (rs.next()) {
                item = new ArrayList<Map<String, String>>();
                Map map2 = new HashMap();
                for (Object[] field : fieldList) {
                    map = new HashMap(16);
                    if ("SELECT".equals(field[6])) {
                        // 数据为类型编码，需转码
                        String value = rs.getString(String.valueOf(field[0]));
                        String codeValue = HBUtil.getCodeName(String.valueOf(field[7]), value);
                        map.put("key", String.valueOf(field[0]));
                        map.put("desc", codeValue);
                        map2.put(String.valueOf(field[0]), codeValue);
                    } else {
                        String key = String.valueOf(field[0]);
                        
                        String value = formatTpbjResult(rs.getObject(String.valueOf(field[0])));
                        if("getTime".equals(field[9])){
                        	value = formatDate(value);
                        }
                        
                        
                        map2.put(key, value);
                        if("BZ".equals(key)||"A0196C".equals(key)||"A0101".equals(key)||"false".equals(field[5])){//姓名调过 改成描述
                        	continue;
                        }
                        
                        map.put("key", key);
                        map.put("desc", value);
                        
                    }
                    
                    item.add(map);
                    
                }
                String desc = getDesc(map2);
                map = new HashMap(16);
                map.put("key", "A0101");
                map.put("desc", desc);
                item.add(map);
                
                String BZ = getDesc2(map2);
                map = new HashMap(16);
                map.put("key", "BZ");
                map.put("desc", BZ);
                item.add(map);
                result.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("查询失败");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        Map res = new HashMap();
        res.put("data", result);
        this.setSelfDefResData(res);
        return EventRtnType.SPE_SUCCESS;
    }

    private String getDesc2(Map map2) {
    	String data = "";
		
		Object BZ=map2.get("BZ")==null?"":map2.get("BZ");//年轻干部
		if(!StringUtils.isEmpty(BZ.toString())){
			data = data + BZ + ",";
		}
		Object A0196C=map2.get("A0196C")==null?"":map2.get("A0196C");//是否可提拔
		if(!StringUtils.isEmpty(A0196C.toString())){
			data = data + A0196C + ",";
		}
		if(data.length()>0){
			data = data.substring(0, data.length()-1);
		}
		return data;
	}

	private String getDesc(Map map2) {
		String data = "";
		
		Object a0101=map2.get("A0101")==null?"":map2.get("A0101");//姓名
		if(!StringUtils.isEmpty(a0101.toString())){
			data = data + a0101 + ",";
		}
		Object a0104=map2.get("A0104")==null?"":map2.get("A0104");//性别
		if(!StringUtils.isEmpty(a0104.toString())){
			//data = data + a0104 + ",";
		}
		Object a0107=map2.get("A0107")==null?"":map2.get("A0107");//出生年月
		if(!StringUtils.isEmpty(a0107.toString())){
			String reg = "^[0-9]{6}$";
			String reg2 = "^[0-9]{8}$";
			if(a0107.toString().matches(reg) || a0107.toString().matches(reg2)){
				String msg = CustomQueryPageModel.getAgeNew(a0107.toString());
				data = data + msg + ",";
			}
		}
		
		Object a0117=map2.get("A0117")==null?"":map2.get("A0117");//民族
		if(!StringUtils.isEmpty(a0117.toString())){
			data = data + a0117 + ",";
		}
		Object a0111=map2.get("A0111A")==null?"":map2.get("A0111A");//籍贯
		if(!StringUtils.isEmpty(a0111.toString())){
			data = data + a0111.toString() + "人,";
		}
		Object a0140=map2.get("A0140")==null?"":map2.get("A0140");//入党时间
		if(a0140!=null){
			String reg = "[0-9]{4}\\.[0-9]{2}";
			Pattern p1 = Pattern.compile(reg);
		    Matcher matcher = p1.matcher(a0140.toString());
		    if (matcher.find()) {
		    	String s = matcher.group();
		    	s = s.replace(".", "年");
		    	data = data + s + "月入党,";
			}else if(!"".equals(a0140)){
				data = data + a0140 + ",";
			}
		}
		
		//专业技术职务
		Object zyjs = map2.get("A0196")==null?"":map2.get("A0196");
		zyjs = zyjs.toString().replaceAll("\\r|\\n", "");
		//String zyjsStr = zyjs.toString();

		String zyjsStr = "";
		if(zyjs != null && !zyjs.equals("")){
			zyjsStr = zyjs.toString();
		}


		zyjsStr = zyjsStr.replace(" ", "");
		if(zyjsStr != null && !zyjsStr.equals("")){
			data = data + zyjsStr + ",";
		}

		//String s = "select LISTAGG(A0201A || A0216A,'、') WITHIN GROUP( ORDER BY A0200) from A02 where a0000 = '"+a0000+"' and A0255 = '1' and A0281 = 'true'";
		Object zwOb = map2.get("A0192a")==null?"":map2.get("A0192a");		//工作单位及职务

		String zw = "";
		if(zwOb != null && !zwOb.equals("")){
			zw = zwOb.toString();
		}


		zw = zw.replace(" ", "");

		if(zw!=null && !zw.equals("")){
			data = data + "现任"+zw + ",";
		}
		data = data.substring(0, data.length()-1) + "。";
		return data;
	}

	private String formatTpbjResult(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    /**
     * 格式化日期
     */
    private String formatDate(String date) {
        String str = date.replace(".", "");
        // 判断是否为纯数字，如果为纯数字且长度为6或8，则格式化日期
        Pattern pattern = Pattern.compile("[0-9]{6,8}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            if (str.length() >= 6) {
                return str.substring(0, 4) + "." + str.substring(4, 6);
            }
        }
        return date;
    }

    @PageEvent("a01ConfigTpbjTree")
    public int a01ConfigTpbjTree() {
        try {
            StringBuilder sb_tree = new StringBuilder("[");
            List<Object[]> list = CommSQL.getA01ConfigTpbjTree(SysManagerUtils.getUserId());
            for (Object[] o : list) {
                String name = o[0].toString();
                String desc = o[3].toString();
                String checked = o[5].toString();
                sb_tree.append(" {text: '" + desc + "',id:'" + name + "',leaf:true,checked:" + checked + "},");
            }
            sb_tree.append("]");
            this.setSelfDefResData(sb_tree.toString());
            return EventRtnType.XML_SUCCESS;
        } catch (AppException e) {
            e.printStackTrace();
            this.setMainMessage("操作失败：" + e.getDetailMessage());
            return EventRtnType.FAILD;
        }
    }

    @PageEvent("saveA01ConfigTpbj")
    public int saveA01ConfigTpbj() {
        String jsonp = this.request.getParameter("jsonp");
        if (jsonp != null && !"".equals(jsonp)) {
            try {
                CommSQL.updateA01ConfigTpbj(jsonp, SysManagerUtils.getUserId());
            } catch (AppException e) {
                e.printStackTrace();
                this.setMainMessage("操作失败：" + e.getDetailMessage());
                return EventRtnType.FAILD;
            }
        }
        this.setMainMessage("保存成功");
        return EventRtnType.NORMAL_SUCCESS;
    }
    @PageEvent("expExcel")
    public int expExcel() {
    	try {
			
    		HBSession sess = HBUtil.getHBSession();
    		String dataArray = this.getPageElement("dataArray").getValue();
    		JSONArray  arrayObject = JSONArray.fromObject(dataArray);
    		List<List<String>> dataList = (List<List<String>>)arrayObject;
			String outputpath = (YNTPFileExportBS.HZBPATH+"zhgboutputfiles/tpbj/");
			File f = new File(outputpath);
			
			if(f.isDirectory()){//先清空输出目录
				JSGLBS.deleteDirectory(outputpath);
			}
			if(!f.isDirectory()){
				f.mkdirs();
			}
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("同屏比较");
			for(int rowi=0;rowi<dataList.size();rowi++){
				List<String> cellList = dataList.get(rowi);
				Row row = sheet.createRow(rowi);
				if(rowi==0){
					row.setHeightInPoints((int)(180/1.5));//288  360
					//sheet.createFreezePane(0, 2);
					sheet.createFreezePane(1, 1, 1, 1);
				}
				for(int celli=0;celli<cellList.size();celli++){
					String cellV = cellList.get(celli);
					Cell cell = row.createCell(celli);
					if(celli==0){
						cell.setCellStyle(getCellStyleCL(workbook, "楷体_GB2312"));
					}else{
						cell.setCellStyle(getCellStyleCL(workbook, "仿宋_GB2312"));
					}
					
					if(rowi==0&&celli>0){
						sheet.setColumnWidth(celli, (int)(6698/1.5));
						//照片
						if(cellV!=null){
							int a0000i = cellV.indexOf("a0000=");
							if(a0000i!=-1){
								String a0000 = cellV.substring(a0000i+6,cellV.length());
								A57 a57 = (A57)sess.get(A57.class, a0000);
								if(a57!=null){
									//Blob img = a57.getPhotodata();
									//InputStream inStream = img.getBinaryStream();
									String photourl = a57.getPhotopath();
									File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
									if(fileF.isFile()){
										
										ByteArrayOutputStream bao = new ByteArrayOutputStream();
										BufferedImage bfimg = ImageIO.read(fileF);
										ImageIO.write(bfimg, "png", bao);
										Drawing pch = sheet.createDrawingPatriarch();
										ClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, celli, 0, celli+1, 1);
										pch.createPicture(anchor, workbook.addPicture(bao.toByteArray(), Workbook.PICTURE_TYPE_JPEG));
										
									
									}
								}
							}
						}
					}else{
						sheet.setColumnWidth(celli, 5200);
						if(cellV!=null){
							cell.setCellValue(cellV);
						}
					}
					
				}
			}
			
			String downloadName = "同屏比较.xlsx";
			String downloadPath = outputpath+downloadName;
			OutputStream fos = new  FileOutputStream(new File(downloadPath));
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        workbook.write(bos);
	        bos.flush();
	        fos.close();
	        bos.close();
	        workbook.close();
			String downloadUUID = UUID.randomUUID().toString();
			request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败"+e.getMessage());
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
    /**
	 * 带上下左右边框 正文正文居中 上下居中
	 * 仿宋_GB2312 楷体_GB2312
	 * @param workbook
	 * @return
	 */
	public static CellStyle getCellStyleCL(Workbook workbook,String fontName){
		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		style.setBorderLeft(BorderStyle.THIN);//左边框   
		style.setBorderRight(BorderStyle.THIN);//右边框  		
		style.setBorderTop(BorderStyle.THIN);//上边框  		
		style.setBorderBottom(BorderStyle.THIN);//下边框  		
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER); 
		Font font =workbook.createFont();  
		font.setFontName(fontName);  
		font.setFontHeightInPoints((short) 14);//字体大小
		style.setFont(font);
		style.setWrapText(true);
		return style;
	}
	
	
	
//	@PageEvent("dorybd")
//	@GridDataRange
//	public int dorybd() throws RadowException, AppException { // 打开窗口的实例
//		Grid grid = (Grid) this.getPageElement("ryGrid");
//		List<HashMap<String, Object>> list = grid.getValueList();
//		List<String> selected = new ArrayList<String>();
//		try {
//			for (int i = 0; i < list.size(); i++) {
//				HashMap<String, Object> item = list.get(i);
//				if (item.get("checked").equals(true)) {
//					String a0000 = item.get("a0000") == null ? "" : item.get("a0000").toString();
//					selected.add(a0000);
//				}
//			}
//			if (selected.size() == 0) {
//				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
//				return EventRtnType.FAILD;
//			}else {
//				(Combo)this.getPageElement("b0111s").setValueList(selected);
//				this.getExecuteSG().addExecuteCode("freshReady()");
//			}
//		}catch (Exception e) {
//			this.setMainMessage("查询失败！");
//			e.printStackTrace();
//		}		
//		return EventRtnType.NORMAL_SUCCESS;	
//	}
	@PageEvent("addintolist")
	public int addintolist(String addlist) throws RadowException, AppException {
		String mntp00=this.getPageElement("mntp00").getValue();
		String a0000s=addlist;
		HBSession sess = HBUtil.getHBSession();
		Connection con = null;
		try {
			HBUtil.executeUpdate("insert into HZ_MNTP_PGRIDBUFFER(mntp00,a0000) "
			          + "select * from (SELECT '"+mntp00+"', REGEXP_SUBSTR('"+a0000s+"', '[^,]+', 1, LEVEL) a0000 FROM DUAL CONNECT BY REGEXP_SUBSTR('"+a0000s+"', '[^,]+', 1, LEVEL) IS NOT NULL"
			          + ") x where not exists (select 1 from HZ_MNTP_PGRIDBUFFER t where t.a0000=x.a0000 and mntp00='"+mntp00+"') "); 
        
		}catch (Exception e) {
			this.setMainMessage("添加失败！");
			e.printStackTrace();
		}		
		this.toastmessage("添加成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
