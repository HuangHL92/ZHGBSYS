package com.insigma.siis.local.util;

import java.sql.Connection;
import java.util.List;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;

public class SqlUtil {
    
    /*
     * ���ݴ���ı��������ɵ����ѯ��sql
     * 
     * 
     * 
     * */
    public static String getSqlByTableName(String tableName,String alias) throws Exception {
        return getSqlByTableName(tableName, alias, null);
    }
    /*
     * ���ݴ���ı��������ɵ����ѯ��sql
     * 
     * 
     * 
     * */
    public static String getSqlByTableName(String tableName,String alias,String sign) throws Exception {
        String sql = "";
        if(tableName !=null && !"".equals(tableName)){
            String sql2 = null;//��ѯ���ֶ�sql
            List colomns = null;//���ֶμ�
            if(sign!=null&&sign.indexOf(".ZDY")!=-1){
            	String inx_id = sign.split("\\.ZDY")[0];
            	
            	colomns = HBUtil.getHBSession().createSQLQuery("select c.col_code from INTERCHANGE_FORMAT_DETAIL t,code_table_col c where "
        		+ "t.ctci=c.ctci and t.inx_id='"+inx_id+"' and c.table_code='"+tableName.toUpperCase()+"'").list();
            	
            	sql2 = "select c.col_code from INTERCHANGE_FORMAT_DETAIL t,code_table_col c where "
        		+ "t.ctci=c.ctci and t.inx_id='"+inx_id+"' and c.table_code='"+tableName.toUpperCase()+"' "
        		+ "union  "
        		+ "select t.table_col from Inx_Config t "
        		+ "where table_name = '"+tableName.toUpperCase()+"'";
            	
            	if(colomns.size()==0){
            		tableName = "(select * from "+tableName+" where 1=2)";
            	}
            }else if (DBUtil.getDBType().equals(DBType.MYSQL)) {          
                sql2 = "select column_name from information_schema.columns a where table_name = upper('"+tableName+"') and a.TABLE_SCHEMA = 'ZWHZYQ'";
            } else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
                sql2 = "select COLUMN_NAME From User_Col_Comments a Where Table_Name = upper('"+tableName+"')";
            }
            colomns = HBUtil.getHBSession().createSQLQuery(sql2).list();
            StringBuffer colomn_sql = new StringBuffer();            //b01�ֶ����� ��(a0000,A0200......)
            if(colomns != null){
                for (int j = 0; j < colomns.size(); j++) {
                    String column = (String) colomns.get(j);
                    if(alias != null && !"".equals(alias)){
                        if(j == 0){
                            colomn_sql.append(alias+"."+column);
                        }else{
                            colomn_sql.append(","+alias+"."+column);
                        }
                    }else{
                        colomn_sql.append(column);
                        if(j != colomns.size()-1){
                            colomn_sql.append(",");
                        }
                    }
                }
            } else {
                throw new RadowException("���ݿ��쳣������ϵ����Ա��");
            }
            
            if(alias != null && !"".equals(alias)){
                sql = " select "+colomn_sql+" "+ " FROM "+tableName +" "+alias + " ";
            }else{
                sql = " select "+colomn_sql+" "+ " FROM "+tableName +" ";
            }
        }
        return sql;
    }
    /*
     * ���ݴ���ı��������ر��ֶ��ַ���
     * tableName    ����
     * alias    �����
     * ���أ�
     * colomn1,colomns2,colomns........
     * t.colomn1,t.colomns2,t.colomns........
     * 
     * */
    public static String getColomnsByTableName(String tableName,String alias) throws Exception {
        return getColomnsByTableName(tableName, alias,null);
    }
    
    
    /*
     * ���ݴ���ı��������ر��ֶ��ַ���
     * tableName    ����
     * alias    �����
     * ���أ�
     * colomn1,colomns2,colomns........
     * t.colomn1,t.colomns2,t.colomns........
     * 
     * */
    public static String getColomnsByTableName(String tableName,String alias,String sign) throws Exception {
        String colomnStr = "";
        if(tableName !=null && !"".equals(tableName)){
            String sql2 = null;//��ѯ���ֶ�sql
            List colomns = null;//���ֶμ�
            if(sign!=null&&sign.indexOf(".ZDY")!=-1){
            	String inx_id = sign.split("\\.ZDY")[0];
            	sql2 = "select c.col_code from INTERCHANGE_FORMAT_DETAIL t,code_table_col c where "
                		+ "t.ctci=c.ctci and t.inx_id='"+inx_id+"' and c.table_code='"+tableName.toUpperCase()+"' "
                		+ "union  "
                		+ "select t.table_col from Inx_Config t "
                		+ "where table_name = '"+tableName.toUpperCase()+"'";
            }else if (DBUtil.getDBType().equals(DBType.MYSQL)) {          
                sql2 = "select column_name from information_schema.columns a where table_name = upper('"+tableName+"') and a.TABLE_SCHEMA = 'ZWHZYQ'";
            } else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
                sql2 = "select COLUMN_NAME From User_Col_Comments a Where Table_Name = upper('"+tableName+"')";
            }
            
            
            colomns = HBUtil.getHBSession().createSQLQuery(sql2).list();
            StringBuffer colomn_sql = new StringBuffer();            //b01�ֶ����� ��(a0000,A0200......)
            if(colomns != null){
                for (int j = 0; j < colomns.size(); j++) {
                    String column = (String) colomns.get(j);
                    if(alias != null && !"".equals(alias)){
                        if(j == 0){
                            colomn_sql.append(alias+"."+column);
                        }else{
                            colomn_sql.append(","+alias+"."+column);
                        }
                    }else{
                        colomn_sql.append(column);
                        if(j != colomns.size()-1){
                            colomn_sql.append(",");
                        }
                    }
                }
            } else {
                throw new RadowException("���ݿ��쳣������ϵ����Ա��");
            }
            
            colomnStr = " "+colomn_sql+" ";
        }
        return colomnStr;
    }
    
    private static Connection getConnection(Connection conn){
        if(conn == null){
            HBSession sess = HBUtil.getHBSession();
            conn = sess.connection();
        }
        return conn;
    }
    
    

}
