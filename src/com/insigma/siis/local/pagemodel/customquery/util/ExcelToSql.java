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
        // ��ȡexcel·��
        // String excelPath = getPath() + "\\oldCarde.xls";
        // ��ȡc����mdata·��
        File excelfile = new File(excelPath);
        ReadExcel excelReader = new ReadExcel(excelfile, "ʡ����ͬ־");
        List<List<Object>> lists = excelReader.readXls(0);
        //excelReader.close();
        //�м�������Ҳ�൱��id
        int count = 1;
        //���������
        int orgCount = 1;
        List<String> sqls = new ArrayList();
        sqls.add("drop table if exists oldCarde;");
        sqls.add("CREATE TABLE oldCarde ( id TEXT, name TEXT, 'position' TEXT, gender TEXT, nation TEXT, birthPlace TEXT, graduate TEXT, birthday TEXT, workTime TEXT, joinTime TEXT, note TEXT, orgId TEXT, CONSTRAINT oldCarde_PK PRIMARY KEY (id) );");
        for (int i = 0; i < lists.size(); i++) {
            //ȡһ��
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
                if ("���˳��쵼���ӡ����ι�ʡ���쵼ְ�����ְ�ڱ��쵼ͬ־����".equals(o.toString())) {
                    orgCount = 1;
                    break;
                } else if ("���ι���ʡ���쵼ְ�����������ͬ־����".equals(o.toString())) {
                    orgCount = 2;
                    break;
                } else if ("��ϵ������ʡ�����˳��쵼���ӡ����ι�ʡ���쵼ְ�����ͬ־����".equals(o.toString())) {
                    orgCount = 3;
                    break;
                } else if ("���ι���ʡ���쵼ְ�����������ͬ־����".equals(o.toString())) {
                    orgCount = 4;
                    break;
                } else if ("�ϴλ����뿪������λ��ʡ���ɲ�".equals(o.toString())) {
                    orgCount = 5;
                    break;
                } else if (o.toString().startsWith("��")) {
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
            // ת��Ϊutf-8����
            filePath = URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ��ִ��jar�����еĽ�������".jar"
        if (filePath.endsWith(".jar")) {
            // ��ȡ·���е�jar����
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }

        File file = new File(filePath);

        //�õ�windows�µ���ȷ·��
        filePath = file.getAbsolutePath();
        return filePath;
    }
    
    public String getZipPath() {
		//"D:/HZB//temp/zipload/"+uuid+"/";
		String path=getPath();
		String zipPath=path+"��PAD�����ļ�_"+DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+"/";
		File file = new File(zipPath);
		if(!file.exists()){
			file.mkdirs();
		}
		System.out.println("zipPath:"+zipPath);
		return zipPath;
	}
    

	/** ������� */
	private Map<String, String> headerAlias = new HashMap();

    /**
     * @param @param  path
     * @param @return
     * @param @throws Exception    �趨�ļ�
     * @return List<List<String>>    ��������
     * @throws
     * @Title: readXlsx
     * @Description: ����Xlsx�ļ�
     */
    public List<List<Object>> readXls(File file) throws Exception {
        InputStream is = new FileInputStream(file);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

        List<List<Object>> result = new ArrayList<List<Object>>();
        // ѭ����ǰҳ����������ǰѭ��ҳ
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        // ѭ��ÿһҳ����������ǰѭ��ҳ
//        for (HSSFSheet HSSFSheet : HSSFWorkbook) {
//            String a = HSSFSheet.getSheetName();
//            if (HSSFSheet.gets(0) != "�¶Ȼ�??") {
//                continue;
//            }
        // ������ǰҳ��ѭ����ȡÿһ? rowNum:�ӵڼ���?ʼ��?
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

    // ���ڵ���?
    /*
     * ��ʵ��ʱ������ϣ���õ������ݾ���excel�е����ݣ���������ֽ��������
     * ������excel�е����������֣���ᷢ��Java�ж�Ӧ�ı���˿�ѧ������?
     * ?���ڻ�ȡֵ��ʱ??��Ҫ��?Щ���⴦������֤�õ��Լ���Ҫ�Ľ�?
     * ���ϵ������Ƕ�����??�͵����ݸ�ʽ������ȡ�Լ���Ҫ�Ľ��?
     * �����ṩ����?�ַ������ڴ�֮ǰ�������ȿ�һ��poi�ж���toString()����:
     *
     * �÷�����poi�ķ�������Դ�������ǿ��Է��֣��ô��������ǣ�
     * 1.��ȡ��Ԫ�������
     * 2.�������͸�ʽ�����ݲ�����������Ͳ����˺ܶ಻��������Ҫ��
     * �ʶ����������һ������??
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
     * ��??poiĬ�ϵ�toString������������
     *
     * @param @param  cell
     * @param @return �趨�ļ�
     * @return String    ��������
     * @throws
     * @Title: getStringVal
     * @Description: 1.���ڲ���Ϥ�����ͣ�����Ϊ���򷵻�""����?
     * 2.��������֣����޸ĵ�Ԫ������ΪString��Ȼ�󷵻�String�������ͱ�֤���ֲ�����ʽ����
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


//    //��ȡexcel
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
//            //�ж�cell����
//            switch(cell.getCellType()){
//                case Cell.CELL_TYPE_NUMERIC:{
//                    cellValue = String.valueOf(cell.getNumericCellValue());
//                    break;
//                }
//                case Cell.CELL_TYPE_FORMULA:{
//                    //�ж�cell�Ƿ�Ϊ���ڸ�?
//                    if(DateUtil.isCellDateFormatted(cell)){
//                        //ת��Ϊ���ڸ�ʽYYYY-mm-dd
//                        cellValue = cell.getDateCellValue();
//                    }else{
//                        //����
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


    // ȥ��Excel�ķ���readExcel���÷�������ڲ���Ϊ?��File����
//    public static List readExcel1(File file) {
//        try {
//            // ��������������ȡExcel
//            InputStream is = new FileInputStream(file.getPath());
//            // jxl�ṩ��Workbook?
//            Workbook wb = Workbook.getWorkbook(is);
//            // Excel��ҳǩ��?
//            int sheet_size = wb.getNumberOfSheets();
//            for (int index = 0; index < sheet_size; index++) {
//                List<List> outerList=new ArrayList<List>();
//                // ÿ��ҳǩ����?��Sheet����
//                Sheet sheet = wb.getSheet(index);
//                // sheet.getRows()���ظ�ҳ��??��?
//                for (int i = 0; i < sheet.getRows(); i++) {
//                    List innerList=new ArrayList();
//                    // sheet.getColumns()���ظ�ҳ��??��?
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
//            Sheet rs=rwb.getSheet(0);//��??rwb.getSheet(0)
//            int clos=rs.getColumns();//�õ�?�е�?
//            HSSFRow rows = null;
//            int row=rs.getRows();//�õ�?�е�?
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
////                    //��һ�����������ڶ�������?
////                    Report titles=new Report();
////                    String title=rs.getCell(j++, i).getContents();//Ĭ��?��߱��Ҳ��?? ?�������j++
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
//            //�ж�cell����
//            switch(cell.getCellType()){
//                case Cell.CELL_TYPE_NUMERIC:{
//                    cellValue = String.valueOf(cell.getNumericCellValue());
//                    break;
//                }
//                case Cell.CELL_TYPE_FORMULA:{
//                    //�ж�cell�Ƿ�Ϊ���ڸ�?
//                    if(DateUtil.isCellDateFormatted(cell)){
//                        //ת��Ϊ���ڸ�ʽYYYY-mm-dd
//                        cellValue = cell.getDateCellValue();
//                    }else{
//                        //����
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
//            throw new IOException("��֧�ֵ��ļ�����");
//        }
//    }
//
//    /**
//     * ��ȡ office 2003 excel
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
//                //������һ ? ����
//                continue;
//            }
//            row = sheet.getRow(i);
//            List<Object> linked = new LinkedList<Object>();
//            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
//                cell = row.getCell(j);
//                if (cell == null) {
//                    value = "?";//���벻��Ϊ��
//                    linked.add(value);
//                    //System.out.println(value);
//                    continue;
//                }
//                DecimalFormat df = new DecimalFormat("0");// ��ʽ? number String
//                // �ַ�
//                SimpleDateFormat sdf = new SimpleDateFormat(
//                        "yyyy-MM-dd");// ��ʽ�������ַ���
//                DecimalFormat nf = new DecimalFormat("0");// ��ʽ����?
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
//                    value = "?";//���벻��Ϊ��
//                }
//                //System.out.println(value);
//                linked.add(value);
//            }
//            list.add(linked);
//        }
//        return list;
//    }

    /**
     * ��ȡOffice 2007 excel
     */
//    private static List<List<Object>> read2007Excel(File file)
//            throws IOException {
//        List<List<Object>> list = new LinkedList<List<Object>>();
//        // ��?? HSSFWorkbook ����strPath �����ļ�·��
//        HSSFWorkbook xwb = new HSSFWorkbook(new FileInputStream(file));
//        // ��ȡ��һ�±�����?
//        HSSFSheet sheet = xwb.getSheetAt(0);
//        Object value = null;
//        HSSFRow row = null;
//        HSSFCell cell = null;
//        int counter = 0;
//        for (int i = sheet.getFirstRowNum(); counter < sheet
//                .getPhysicalNumberOfRows(); i++) {
//            if (i == 0 && i == 1 && i == 2) {
//                //������һ ? ����
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
//                    value = "?";//���벻��Ϊ��
//                    linked.add(value);
//                    //System.out.println(value);
//                    continue;
//                }
//                //System.out.println(value);
//                DecimalFormat df = new DecimalFormat("0");// ��ʽ? number String
//                // �ַ�
//                SimpleDateFormat sdf = new SimpleDateFormat(
//                        "yyyy-MM-dd");// ��ʽ�������ַ���
//                DecimalFormat nf = new DecimalFormat("0");// ��ʽ����?
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
//                    case HSSFCell.CELL_TYPE_BLANK://�ո񣬿�?
//                        value = "";
//                        break;
//                    default:
//                        value = cell.toString();
//                }
//                if (value == null || "".equals(value)) {
//                    value = "?";//���벻��Ϊ��
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
//        //����洢��ȡ�������ݼ�¼�ļ�?
//        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//        try {
//
//            String encoding = "UTF-8";
//            File file = new File(filePath);
//            //�ж��ļ��Ƿ����
//            if (file.isFile() && file.exists()) {
//                //���ǵ������?
//                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
//                BufferedReader bufferedReader = new BufferedReader(read);
//                String lineTxt = null;
//                //��¼��ȡ�������ļ�������
//                int count = 0;
//                //�����ֶε���?
//                String[] fields = null;
//                //����ÿ����¼ȡ�����ֶ�??��?
//                String[] fieldValue = null;
//                //����Map����
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
//                        //����ȡ��ÿһ�еļ�¼���뵽list����?
//                        list.add(map);
//                    }
//
//                    count++;
//                }
//                read.close();
//            } else {
//                System.out.println("�Ҳ���ָ�����ļ�");
//            }
//        } catch (Exception e) {
//            System.out.println("��ȡ�ļ����ݳ���");
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public static List<Map<String, Object>> loadExcel(String filepath, int count) {
//        //����Excel�������ļ�������
//        HSSFWorkbook wookbook = null;
//        try {
//            wookbook = new HSSFWorkbook(new FileInputStream(filepath));//����·����������
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        //��excel�ĵ��У���һ����������ȱʡ������0
//        HSSFSheet sheet = wookbook.getSheetAt(count);
//        //��ȡ��excel�ļ��е�?����?
//        int rows = sheet.getPhysicalNumberOfRows();
//        int shengchanstart = 0;
//        List<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
//        boolean boo = false;
//        for (int i = 1; i < rows; i++) {
//            HSSFRow row = sheet.getRow(i);
//
//            if (row != null) {
//                //��ȡ�ļ��е�?����
//                int cells = row.getPhysicalNumberOfCells();
//                String value = "";
//                //����?
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
//                                    if ("����".equals(cell.getStringCellValue())) {
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
//     * ��ȡ�ϲ���Ԫ���?
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
//     * ��ȡ��Ԫ���?
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
	 * ת��������������û�б�����ʹ��ԭ���⣬������Ϊ��ʱ���кŶ�Ӧ����ĸ����header
	 * 
	 * @param headerList ԭ�����б�
	 * @return ת�������б�
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
	 * ת��������������û�б�����ʹ��ԭ���⣬������Ϊ��ʱ���кŶ�Ӧ����ĸ����header
	 * 
	 * @param headerObj ԭ����
	 * @param index ���������кţ�������Ϊ��ʱ���кŶ�Ӧ����ĸ����header
	 * @return ת�������б�
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
	 * ��Sheet�кű�Ϊ����
	 * 
	 * @param index �к�, ��0��ʼ
	 * @return 0-��A; 1-��B...26-��AA
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