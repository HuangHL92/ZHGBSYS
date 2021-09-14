package com.insigma.siis.local.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class Excel2TableUtil {


    public static void getEcel2Sql(String tableName, InputStream is, Integer startNumber, Integer rowNumber) throws RadowException, IOException, SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/orcl", "ZHGBSYS", "ZHGBSYS");
//        Connection connection = HBUtil.getHBSession().connection();
        connection.setAutoCommit(false);
        PreparedStatement statement = null;
        String columns = "(" + getSql(tableName) + ")";
        StringBuilder sql = new StringBuilder("");
        sql.append("insert all ");
        for (int i = 1; i <= rowNumber; i++) {
            sql.append(" into " + tableName + columns + " values " + exceloClumns(is, startNumber) + " ");
            startNumber++;

            if (i == rowNumber) {
                statement = connection.prepareStatement(sql.toString() + " select 1 from dual");
                statement.addBatch();
                statement.executeBatch();
                statement.clearBatch();
            }
            if (i % 500 == 0) {
                statement = connection.prepareStatement(sql.toString() + " select 1 from dual");
                statement.addBatch();
                statement.executeBatch();
                statement.clearBatch();
                sql.setLength(0);
                sql.append("insert all ");
            }
        }
        connection.commit();

        statement.close();
        connection.close();
    }

    public static String exceloClumns(InputStream is, Integer starRowNumber) throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File("D:\\测试.xls")));
        HSSFSheet sheet = null;
        HSSFRow row = null;
        String str = "(";
//        try {
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        //由于第0行和第一行已经合并了  在这里索引从2开始
        row = sheet.getRow(starRowNumber);
        int colNum = row.getPhysicalNumberOfCells();
        int j = 0;
        while (j < colNum - 1) {
            str = str + "'" + getCellFormatValue(row.getCell((short) j)).trim() + "'" + ",";
            j++;
        }
        str = str + "'" + getCellFormatValue(row.getCell((short) j)).trim() + "'" + ")";


        return str;
    }

    public static String getSql(String tableName) throws RadowException, ClassNotFoundException, SQLException {
//        List colomns = new ArrayList();

        if ("".equals(tableName) || tableName == null) {

        }
        String sql = "select COLUMN_NAME From User_Col_Comments a Where Table_Name = upper('" + tableName + "')";
//        HBSession hbSession = HBUtil.getHBSession();
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/orcl", "ZHGBSYS", "ZHGBSYS");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        StringBuffer colomn_sql = new StringBuffer();
        while (resultSet.next()) {
            String column = resultSet.getString("COLUMN_NAME") + ",";
            colomn_sql.append(column);
        }
//        colomns = hbSession.createSQLQuery(sql).list();
//        hbSession.flush();

//        if (colomns != null) {
//            for (int j = 0; j < colomns.size(); j++) {
//                String column = (String) colomns.get(j);
//                colomn_sql.append(column);
//                if (j != colomns.size() - 1) {
//                    colomn_sql.append(",");
//                }
//
//            }
//        } else {
//            throw new RadowException("数据库异常，请联系管理员！");
//        }
        resultSet.close();
        statement.close();
        connection.close();
        return colomn_sql.toString().substring(0, colomn_sql.toString().lastIndexOf(","));
    }

    /**
     * 数据格式化
     *
     * @param cell
     * @return
     */
    public static String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    public static void main(String[] args) throws IOException, SQLException, RadowException, ClassNotFoundException {
        InputStream is = new FileInputStream("d:\\测试.xls");
        getEcel2Sql("testh", is, 2, 11);
    }
}