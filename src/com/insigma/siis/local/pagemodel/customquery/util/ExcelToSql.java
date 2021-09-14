package com.insigma.siis.local.pagemodel.customquery.util;

//import cn.hutool.poi.excel.ExcelReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.insigma.siis.local.business.helperUtil.DateUtil;


/**
 * @author genggaopeng
 * @date 2019/10/24 14:07
 */
public class ExcelToSql {
    public ExcelToSql(String excelPath, File mdataPath) throws Exception{
        // 获取excel路径
        // String excelPath = getPath() + "\\oldCarde.xls";
        // 获取c盘中mdata路径
        File excelfile = new File(excelPath);
        ReadExcel excelReader = new ReadExcel(excelfile, "省级老同志");
        List<List<Object>> lists = excelReader.readXls(0);
        //excelReader.close();
        //行计数器，也相当于id
        int count = 1;
        //分类计数器
        int orgCount = 1;
        List<String> sqls = new ArrayList();
        sqls.add("drop table if exists oldCarde;");
        sqls.add("CREATE TABLE oldCarde ( id TEXT, name TEXT, 'position' TEXT, gender TEXT, nation TEXT, birthPlace TEXT, graduate TEXT, birthday TEXT, workTime TEXT, joinTime TEXT, note TEXT, orgId TEXT, CONSTRAINT oldCarde_PK PRIMARY KEY (id) );");
        for (int i = 0; i < lists.size(); i++) {
            //取一行
            List<Object> list = lists.get(i);
            StringBuilder str = new StringBuilder();

            for (int j = 0; j < list.size(); j++) {
            	Object o =0;
            	try {
            		o = list.get(j);
				} catch (Exception e) {
					continue;
				}
                if(o==null)continue;
                if ("已退出领导班子、担任过省级领导职务的在职在编领导同志名册".equals(o.toString())) {
                    orgCount = 1;
                    break;
                } else if ("担任过正省级领导职务的离退休老同志名册".equals(o.toString())) {
                    orgCount = 2;
                    break;
                } else if ("关系不在我省，已退出领导班子、担任过省级领导职务的老同志名册".equals(o.toString())) {
                    orgCount = 3;
                    break;
                } else if ("担任过副省级领导职务的离退休老同志名册".equals(o.toString())) {
                    orgCount = 4;
                    break;
                } else if ("上次换届离开工作岗位的省级干部".equals(o.toString())) {
                    orgCount = 5;
                    break;
                } else if (o.toString().startsWith("姓")) {
                    break;
                } else {
                    if (o != null) {
                        str.append("'" + o.toString().replace("\n", "<br>") + "',");
                    }
                }
            }

            if (str.toString().equals("")) {
                continue;
            } else {
                StringBuilder str2 = new StringBuilder("insert into oldCarde values ('" + count + "',");
                count++;
                str2.append(str);
                str2.append("'" + orgCount + "');");
                System.out.println(str2.toString());
                sqls.add(str2.toString());
            }
        }

        for (String sql : sqls) {
            executeSql("jdbc:sqlite:" + mdataPath, sql);
        }
    }


    public static void executeSql(String url, String sql) {
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("execute sql finished.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getPath() {
        URL url = ExcelToSql.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        try {
            // 转化为utf-8编码
            filePath = URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 可执行jar包运行的结果里包含".jar"
        if (filePath.endsWith(".jar")) {
            // 截取路径中的jar包名
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }

        File file = new File(filePath);

        //得到windows下的正确路径
        filePath = file.getAbsolutePath();
        return filePath;
    }
    
    public String getZipPath() {
		//"D:/HZB//temp/zipload/"+uuid+"/";
		String path=getPath();
		String zipPath=path+"给PAD导出文件_"+DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+"/";
		File file = new File(zipPath);
		if(!file.exists()){
			file.mkdirs();
		}
		System.out.println("zipPath:"+zipPath);
		return zipPath;
	}
    

	/** 标题别名 */
	private Map<String, String> headerAlias = new HashMap();

    /**
     * @param @param  path
     * @param @return
     * @param @throws Exception    设定文件
     * @return List<List<String>>    返回类型
     * @throws
     * @Title: readXlsx
     * @Description: 处理Xlsx文件
     */
    public List<List<Object>> readXls(File file) throws Exception {
        InputStream is = new FileInputStream(file);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

        List<List<Object>> result = new ArrayList<List<Object>>();
        // 循环当前页，并处理当前循环页
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        // 循环每一页，并处理当前循环页
//        for (HSSFSheet HSSFSheet : HSSFWorkbook) {
//            String a = HSSFSheet.getSheetName();
//            if (HSSFSheet.gets(0) != "月度汇??") {
//                continue;
//            }
        // 处理当前页，循环读取每一? rowNum:从第几行?始读?
        for (int rowNum = 2; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            HSSFRow hSSFRow = hssfSheet.getRow(rowNum);
            int minColIx = hSSFRow.getFirstCellNum();
            int maxColIx = 10;
            boolean isFirstLine = true;
            List<Object> rowList = new ArrayList<Object>();
            for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                HSSFCell cell = hSSFRow.getCell(colIx);
                if (isNotEmptyColl(rowList)) {
                	if (cell == null) {
                    	rowList = new ArrayList(0);
                    }
                    if (isFirstLine) {
    					isFirstLine = false;
    					if (isNotEmpty(this.headerAlias)) {
    						rowList = aliasHeader(rowList);
    					}
    				}
                    rowList.add(cell.toString());
				}
                
            }
            result.add(rowList);
        }
        return result;
    }

    // 存在的问?
    /*
     * 其实有时候我们希望得到的数据就是excel中的数据，可是最后发现结果不理想
     * 如果你的excel中的数据是数字，你会发现Java中对应的变成了科学计数法?
     * ?以在获取值的时??就要做?些特殊处理来保证得到自己想要的结?
     * 网上的做法是对于数??型的数据格式化，获取自己想要的结果?
     * 下面提供另外?种方法，在此之前，我们先看一下poi中对于toString()方法:
     *
     * 该方法是poi的方法，从源码中我们可以发现，该处理流程是：
     * 1.获取单元格的类型
     * 2.根据类型格式化数据并输出。这样就产生了很多不是我们想要的
     * 故对这个方法做一个改造??
     */
    /*public String toString(){
        switch(getCellType()){
            case CELL_TYPE_BLANK:
                return "";
            case CELL_TYPE_BOOLEAN:
                return getBooleanCellValue() ? "TRUE" : "FALSE";
            case CELL_TYPE_ERROR:
                return ErrorEval.getText(getErrorCellValue());
            case CELL_TYPE_FORMULA:
                return getCellFormula();
            case CELL_TYPE_NUMERIC:
                if(DateUtil.isCellDateFormatted(this)){
                    DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy")
                    return sdf.format(getDateCellValue());
                }
                return getNumericCellValue() + "";
            case CELL_TYPE_STRING:
                return getRichStringCellValue().toString();
            default :
                return "Unknown Cell Type:" + getCellType();
        }
    }*/

    /**
     * 改??poi默认的toString（）方法如下
     *
     * @param @param  cell
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: getStringVal
     * @Description: 1.对于不熟悉的类型，或者为空则返回""控制?
     * 2.如果是数字，则修改单元格类型为String，然后返回String，这样就保证数字不被格式化了
     */
    public static String getStringVal(HSSFCell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_NUMERIC:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
                return "";
        }
    }


//    //读取excel
//    public static Workbook readExcel2(String filePath){
//        Workbook wb = null;
//        if(filePath==null){
//            return null;
//        }
//        String extString = filePath.substring(filePath.lastIndexOf("."));
//        InputStream is = null;
//        try {
//            is = new FileInputStream(filePath);
//            if(".xls".equals(extString)){
//                return wb = new HSSFWorkbook(is);
//            }else if(".xlsx".equals(extString)){
//                return wb = new HSSFWorkbook(is);
//            }else{
//                return wb = null;
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return wb;
//    }
//    public static Object getCellFormatValue(Cell cell){
//        Object cellValue = null;
//        if(cell!=null){
//            //判断cell类型
//            switch(cell.getCellType()){
//                case Cell.CELL_TYPE_NUMERIC:{
//                    cellValue = String.valueOf(cell.getNumericCellValue());
//                    break;
//                }
//                case Cell.CELL_TYPE_FORMULA:{
//                    //判断cell是否为日期格?
//                    if(DateUtil.isCellDateFormatted(cell)){
//                        //转换为日期格式YYYY-mm-dd
//                        cellValue = cell.getDateCellValue();
//                    }else{
//                        //数字
//                        cellValue = String.valueOf(cell.getNumericCellValue());
//                    }
//                    break;
//                }
//                case Cell.CELL_TYPE_STRING:{
//                    cellValue = cell.getRichStringCellValue().getString();
//                    break;
//                }
//                default:
//                    cellValue = "";
//            }
//        }else{
//            cellValue = "";
//        }
//        return cellValue;
//    }


    // 去读Excel的方法readExcel，该方法的入口参数为?个File对象
//    public static List readExcel1(File file) {
//        try {
//            // 创建输入流，读取Excel
//            InputStream is = new FileInputStream(file.getPath());
//            // jxl提供的Workbook?
//            Workbook wb = Workbook.getWorkbook(is);
//            // Excel的页签数?
//            int sheet_size = wb.getNumberOfSheets();
//            for (int index = 0; index < sheet_size; index++) {
//                List<List> outerList=new ArrayList<List>();
//                // 每个页签创建?个Sheet对象
//                Sheet sheet = wb.getSheet(index);
//                // sheet.getRows()返回该页的??行?
//                for (int i = 0; i < sheet.getRows(); i++) {
//                    List innerList=new ArrayList();
//                    // sheet.getColumns()返回该页的??列?
//                    for (int j = 0; j < sheet.getColumns(); j++) {
//                        String cellinfo = sheet.getCell(j, i).getContents();
//                        if(cellinfo.isEmpty()){
//                            continue;
//                        }
//                        innerList.add(cellinfo);
//                        System.out.print(cellinfo);
//                    }
//                    outerList.add(i, innerList);
//                    System.out.println();
//                }
//                return outerList;
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (BiffException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


//    public static List<Map<String,String>> getAllByExcel(String file){
//        List<Map<String,String>> list=new ArrayList<Map<String,String>>();
//        try {
//            Workbook rwb=Workbook.getWorkbook(new File(file));
//            Sheet rs=rwb.getSheet(0);//或??rwb.getSheet(0)
//            int clos=rs.getColumns();//得到?有的?
//            HSSFRow rows = null;
//            int row=rs.getRows();//得到?有的?
//            String cellData = null;
//            String[] fields = null;
//            System.out.println(clos+" rows:"+rows);
//            for (int i = 0; i < row; i++) {
//                Map<String,String> map = new LinkedHashMap<String,String>();
//                for (int j=0;j<clos;j++){
//                     cellData = (String) getCellFormatValue(rows.getCell(j));
//                     map.put(fields[j], cellData);
//                 }
//                list.add(map);
//
////                for (int j = 0; j < clos; j++) {
////
////                    //第一个是列数，第二个是行?
////                    Report titles=new Report();
////                    String title=rs.getCell(j++, i).getContents();//默认?左边编号也算?? ?以这里得j++
////                    String option_a=rs.getCell(j++, i).getContents();
////                    String option_b=rs.getCell(j++, i).getContents();
////                    String option_c=rs.getCell(j++, i).getContents();
////                    String option_d=rs.getCell(j++, i).getContents();
////                    String answer=rs.getCell(j++, i).getContents();
////
////                    if(title!=null&&!title.equals(""))
////                        titles.set("title",title);
////                    else
////                        continue;
////                    if(option_a!=null&&!option_a.equals(""))
////                        titles.set("option_a",option_a);
////                    else
////                        continue;
////                    if(option_b!=null&&!option_b.equals(""))
////                        titles.set("option_b",option_b);
////                    else
////                        continue;
////                    if(option_c!=null&&!option_c.equals(""))
////                        titles.set("option_c",option_c);
////                    else
////                        continue;
////                    if(option_d!=null&&!option_d.equals(""))
////                        titles.set("option_d",option_d);
////                    else
////                        continue;
////                    if(answer.equals("A")||answer.equals("B")||answer.equals("C")||answer.equals("D")||answer.equals("a")||answer.equals("b")||answer.equals("c")||answer.equals("d")){
////                        titles.set("answer",answer);
////                    }
////                    else
////                        continue;
////
////                    System.out.println("title:"+title+"option_a:"+option_a+"option_b:"+option_b+"option_c:"+option_c+"option_d:"+option_d+"answer:"+answer);
////                    list.add(titles);
////                }
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return list;
//    }

//    public static Object getCellFormatValue1(Cell cell){
//        Object cellValue = null;
//        if(cell!=null){
//            //判断cell类型
//            switch(cell.getCellType()){
//                case Cell.CELL_TYPE_NUMERIC:{
//                    cellValue = String.valueOf(cell.getNumericCellValue());
//                    break;
//                }
//                case Cell.CELL_TYPE_FORMULA:{
//                    //判断cell是否为日期格?
//                    if(DateUtil.isCellDateFormatted(cell)){
//                        //转换为日期格式YYYY-mm-dd
//                        cellValue = cell.getDateCellValue();
//                    }else{
//                        //数字
//                        cellValue = String.valueOf(cell.getNumericCellValue());
//                    }
//                    break;
//                }
//                case Cell.CELL_TYPE_STRING:{
//                    cellValue = cell.getRichStringCellValue().getString();
//                    break;
//                }
//                default:
//                    cellValue = "";
//            }
//        }else{
//            cellValue = "";
//        }
//        return cellValue;
//    }


//    public static List<List<Object>> readExcel(File file) throws IOException {
//        String fileName = file.getName();
//        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
//                .substring(fileName.lastIndexOf(".") + 1);
//        if ("xls".equals(extension)) {
//            return read2003Excel(file);
//        } else if ("xlsx".equals(extension)) {
//            return read2007Excel(file);
//        } else {
//            throw new IOException("不支持的文件类型");
//        }
//    }
//
//    /**
//     * 读取 office 2003 excel
//     *
//     * @throws IOException
//     * @throws FileNotFoundException
//     */
//    private static List<List<Object>> read2003Excel(File file)
//            throws IOException {
//        List<List<Object>> list = new LinkedList<List<Object>>();
//        HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
//        HSSFSheet sheet = hwb.getSheetAt(0);
//        Object value = null;
//        HSSFRow row = null;
//        HSSFCell cell = null;
//        int counter = 0;
//        for (int i = sheet.getFirstRowNum(); counter < sheet.getPhysicalNumberOfRows(); i++) {
//            if (i == 0 && i == 1 && i == 2) {
//                //跳过第一 ? 三行
//                continue;
//            }
//            row = sheet.getRow(i);
//            List<Object> linked = new LinkedList<Object>();
//            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
//                cell = row.getCell(j);
//                if (cell == null) {
//                    value = "?";//导入不能为空
//                    linked.add(value);
//                    //System.out.println(value);
//                    continue;
//                }
//                DecimalFormat df = new DecimalFormat("0");// 格式? number String
//                // 字符
//                SimpleDateFormat sdf = new SimpleDateFormat(
//                        "yyyy-MM-dd");// 格式化日期字符串
//                DecimalFormat nf = new DecimalFormat("0");// 格式化数?
//                switch (cell.getCellType()) {
//                    case HSSFCell.CELL_TYPE_STRING:
//                        value = cell.getStringCellValue();
//                        //System.out.println(i + "?" + j + " ? is String type" +"  "+value);
//                        break;
//                    case HSSFCell.CELL_TYPE_NUMERIC:
////                  System.out.println(i + "?" + j
////                          + " ? is Number type ; DateFormt:"
////                          + cell.getCellStyle().getDataFormatString());
//                        if ("@".equals(cell.getCellStyle().getDataFormatString())) {
//                            value = df.format(cell.getNumericCellValue());
//                        } else if ("General".equals(cell.getCellStyle()
//                                .getDataFormatString())) {
//                            value = nf.format(cell.getNumericCellValue());
//                        } else {
//                            value = sdf.format(HSSFDateUtil.getJavaDate(cell
//                                    .getNumericCellValue()));
//                        }
//                        break;
//                    case HSSFCell.CELL_TYPE_BOOLEAN:
//                        value = cell.getBooleanCellValue();
//                        break;
//                    case HSSFCell.CELL_TYPE_BLANK:
//                        value = "";
//                        break;
//                    default:
//                        value = cell.toString();
//                }
//                if (value == null || "".equals(value)) {
//                    value = "?";//导入不能为空
//                }
//                //System.out.println(value);
//                linked.add(value);
//            }
//            list.add(linked);
//        }
//        return list;
//    }

    /**
     * 读取Office 2007 excel
     */
//    private static List<List<Object>> read2007Excel(File file)
//            throws IOException {
//        List<List<Object>> list = new LinkedList<List<Object>>();
//        // 构?? HSSFWorkbook 对象，strPath 传入文件路径
//        HSSFWorkbook xwb = new HSSFWorkbook(new FileInputStream(file));
//        // 读取第一章表格内?
//        HSSFSheet sheet = xwb.getSheetAt(0);
//        Object value = null;
//        HSSFRow row = null;
//        HSSFCell cell = null;
//        int counter = 0;
//        for (int i = sheet.getFirstRowNum(); counter < sheet
//                .getPhysicalNumberOfRows(); i++) {
//            if (i == 0 && i == 1 && i == 2) {
//                //跳过第一 ? 三行
//                continue;
//            }
//            row = sheet.getRow(i);
//            if (row == null) {
//                break;
//            }
//            List<Object> linked = new LinkedList<Object>();
//            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
//                cell = row.getCell(j);
//                if (cell == null) {
//                    value = "?";//导入不能为空
//                    linked.add(value);
//                    //System.out.println(value);
//                    continue;
//                }
//                //System.out.println(value);
//                DecimalFormat df = new DecimalFormat("0");// 格式? number String
//                // 字符
//                SimpleDateFormat sdf = new SimpleDateFormat(
//                        "yyyy-MM-dd");// 格式化日期字符串
//                DecimalFormat nf = new DecimalFormat("0");// 格式化数?
//                switch (cell.getCellType()) {
//                    case HSSFCell.CELL_TYPE_STRING:
//                        //System.out.println(i + "?" + j + " ? is String type");
//                        value = cell.getStringCellValue();
//                        break;
//                    case HSSFCell.CELL_TYPE_NUMERIC:
//                        //  System.out.println(i + "?" + j
//                        //  + " ? is Number type ; DateFormt:"
//                        //  + cell.getCellStyle().getDataFormatString());
//                        if ("@".equals(cell.getCellStyle().getDataFormatString())) {
//                            value = df.format(cell.getNumericCellValue());
//                        } else if ("General".equals(cell.getCellStyle()
//                                .getDataFormatString())) {
//                            value = nf.format(cell.getNumericCellValue());
//                        } else {
//                            value = sdf.format(HSSFDateUtil.getJavaDate(cell
//                                    .getNumericCellValue()));
//                        }
//                        break;
//                    case HSSFCell.CELL_TYPE_BOOLEAN:
//                        value = cell.getBooleanCellValue();
//                        break;
//                    case HSSFCell.CELL_TYPE_BLANK://空格，空?
//                        value = "";
//                        break;
//                    default:
//                        value = cell.toString();
//                }
//                if (value == null || "".equals(value)) {
//                    value = "?";//导入不能为空
//                }
//                //System.out.println(value);
//                linked.add(value);
//            }
//            list.add(linked);
//        }
//        return list;
//    }


//    public static List<HashMap<String, String>> readExcelFile(String filePath) {
//
//        //定义存储读取到的数据记录的集?
//        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//        try {
//
//            String encoding = "UTF-8";
//            File file = new File(filePath);
//            //判断文件是否存在
//            if (file.isFile() && file.exists()) {
//                //考虑到编码格?
//                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTxt = null;
//                //记录读取的数据文件的行数
//                int count = 0;
//                //定义字段的数?
//                String[] fields = null;
//                //定义每条记录取出的字段??数?
//                String[] fieldValue = null;
//                //定义Map集合
//                HashMap<String, String> map = new HashMap<String, String>();
//                while ((lineTxt = bufferedReader.readLine()) != null) {
//
//                    map = new HashMap<String, String>();
//                    if (count == 0) {
//                        fields = lineTxt.split("\\|");
//                        System.out.println("fields:" + fields);
//                    } else {
//                        fieldValue = lineTxt.split("\\|");
//                        //System.out.println("fieldValue:"+fieldValue);
//                        for (int i = 0; i < fields.length; i++) {
//                            for (int j = 0; j < fieldValue.length; j++) {
//                                if (i == j) {
//                                    map.put(fields[i], fieldValue[j]);
//                                }
//                            }
//                        }
//                        //将读取的每一行的记录存入到list集合?
//                        list.add(map);
//                    }
//
//                    count++;
//                }
//                read.close();
//            } else {
//                System.out.println("找不到指定的文件");
//            }
//        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public static List<Map<String, Object>> loadExcel(String filepath, int count) {
//        //创建Excel工作簿文件的引用
//        HSSFWorkbook wookbook = null;
//        try {
//            wookbook = new HSSFWorkbook(new FileInputStream(filepath));//根据路劲创建引用
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        //在excel文档中，第一个工作表的缺省索引是0
//        HSSFSheet sheet = wookbook.getSheetAt(count);
//        //获取到excel文件中的?有行?
//        int rows = sheet.getPhysicalNumberOfRows();
//        int shengchanstart = 0;
//        List<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
//        boolean boo = false;
//        for (int i = 1; i < rows; i++) {
//            HSSFRow row = sheet.getRow(i);
//
//            if (row != null) {
//                //获取文件中的?有列
//                int cells = row.getPhysicalNumberOfCells();
//                String value = "";
//                //遍历?
//                if (!boo) {
//                    aa:
//                    for (int j = 0; j < cells; j++) {
//
//                        HSSFCell cell = row.getCell((short) j);
//
//                        if (cell != null) {
//                            switch (cell.getCellType()) {
//                                case HSSFCell.CELL_TYPE_FORMULA:
//                                    System.out.println(cell.getCellFormula() + "=====================");
//                                    ;
//                                    break;
//                                case HSSFCell.CELL_TYPE_NUMERIC:
//                                    value += cell.getNumericCellValue() + ",";
//                                    break;
//                                case HSSFCell.CELL_TYPE_STRING:
//                                    if ("表名".equals(cell.getStringCellValue())) {
//                                        boo = true;
//                                        break aa;
//                                    }
//                                    value += cell.getStringCellValue() + ",";
//                                default:
//                                    value += "";
//                                    break;
//                            }
//                        }
//                    }
//                } else {
//                    HSSFCell cell = row.getCell((short) 1);
//                    Map<String, Object> map = new HashMap();
//                    if (!"".equals(cell.getStringCellValue())) {
//                        String tablename = getMergedRegionValue(sheet, i);
//                        map.put("table", tablename);
//                        map.put("fieldname", row.getCell((short) (shengchanstart + 2)).getStringCellValue());
//                        map.put("desc", row.getCell((short) (shengchanstart + 1)).getStringCellValue());
//                        System.out.println(tablename + "**********");
//                        li.add(map);
//                    }
//                    System.out.println(i + "**********");
//                }
//            }
//        }
//
//        System.out.println(li);
//        return li;
//    }
//
//    /**
//     * 获取合并单元格的?
//     *
//     * @param sheet
//     * @param row
//     * @return
//     */
//    public static String getMergedRegionValue(Sheet sheet, int row) {
//        int sheetMergeCount = sheet.getNumMergedRegions();
//
//        for (int i = 0; i < sheetMergeCount; i++) {
//            org.apache.poi.ss.util.CellRangeAddress ca = sheet.getMergedRegion(i);
//            int firstColumn = ca.getFirstColumn();
//            int lastColumn = ca.getLastColumn();
//            int firstRow = ca.getFirstRow();
//            int lastRow = ca.getLastRow();
//            if (row >= firstRow && row <= lastRow) {
//                Row fRow = sheet.getRow(firstRow);
//                Cell fCell = fRow.getCell(firstColumn);
//                return getCellValue(fCell);
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 获取单元格的?
//     *
//     * @param cell
//     * @return
//     */
//    public static String getCellValue(Cell cell) {
//
//        if (cell == null) return "";
//
//        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
//
//            return cell.getStringCellValue();
//
//        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
//
//            return String.valueOf(cell.getBooleanCellValue());
//
//        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
//
//            return cell.getCellFormula();
//
//        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//
//            return String.valueOf(cell.getNumericCellValue());
//
//        }
//        return "";
//    }
    public static boolean isNotEmpty(Map<?, ?> map) {
		return null != map && false == map.isEmpty();
	}
    
    /**
	 * 转换标题别名，如果没有别名则使用原标题，当标题为空时，列号对应的字母便是header
	 * 
	 * @param headerList 原标题列表
	 * @return 转换别名列表
	 */
	private List<Object> aliasHeader(List<Object> headerList) {
		final int size = headerList.size();
		final List<Object> result = new ArrayList(size);
		if (isEmpty(headerList)) {
			return result;
		}

		for(int i = 0; i < size; i++) {
			result.add(aliasHeader(headerList.get(i), i));
		}
		return result;
	}
	
	/**
	 * 转换标题别名，如果没有别名则使用原标题，当标题为空时，列号对应的字母便是header
	 * 
	 * @param headerObj 原标题
	 * @param index 标题所在列号，当标题为空时，列号对应的字母便是header
	 * @return 转换别名列表
	 * @since 4.3.2
	 */
	private String aliasHeader(Object headerObj, int index) {
		if(null == headerObj) {
			return indexToColName(index);
		}
		
		final String header = headerObj.toString();
		return defaultIfNull(this.headerAlias.get(header), header);
	}
	
	/**
	 * 将Sheet列号变为列名
	 * 
	 * @param index 列号, 从0开始
	 * @return 0-》A; 1-》B...26-》AA
	 * @since 4.1.20
	 */
	public static String indexToColName(int index) {
		if (index < 0) {
			return null;
		}
		final StringBuilder colName = new StringBuilder();
		do {
			if (colName.length() > 0) {
				index--;
			}
			int remainder = index % 26;
			colName.append((char) (remainder + 'A'));
			index = (int) ((index - remainder) / 26);
		} while (index > 0);
		return colName.reverse().toString();
	}
	
	public static <T> T defaultIfNull(final T object, final T defaultValue) {
		return (null != object) ? object : defaultValue;
	}
	
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
	
	public static Timestamp getTimestamp() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		return now;
	}
	
	public static boolean isNotEmptyColl(Collection<?> collection) {
		return false == isEmpty(collection);
	}
}
