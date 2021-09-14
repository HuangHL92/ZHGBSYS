package com.insigma.siis.local.pagemodel.publicServantManage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBUtil;

/**
 * 备案表展示
 * 
 * @author Administrator
 *
 */
public class BeiAnBiaoKeep {
	public String keep(String tpid,String ids){
		ids = ids.replace("@", ",").replace("|", "'");
		String[] split = ids.split(",");
		int countrenyuan = split.length;
		String script = "";
		int xuhao = 1;//序号
		int line = 1;//列
		int row = 8;//行
		script += "var cell = document.getElementById('cellweb1');\n";
		script += "var path = getPath();\n";
		script += "cell.WorkbookReadonly=true;\n";//设置不可编辑
		if("3de1c725-d71b-476a-b87c-6c8d2184".equals(tpid)){
			script += "var  aa = cell.openfile(ctpath+'/template/GWYDJBAB.cll','');\n";
		}else{
			script += "var  aa = cell.openfile(ctpath+'/template/CZDJBAB.cll','');\n";
		}
		int aaa = countrenyuan - 15;
		if(aaa>0){
			script += "cell.InsertRow('23','"+(countrenyuan-15)+"','0');";//插入行
		}
		String sql = "";
		if(DBUtil.getDBType()==DBType.MYSQL){
			sql = "select a01.a0101,                                                           "+
					"       (case                                                                "+
					"         when a01.a0104 = '1' then                                          "+
					"          '男'                                                              "+
					"         else                                                               "+
					"          '女'                                                              "+
					"       end) as XB,                                                          "+
					"       CONCAT_WS('.',substr(a01.a0107, 1, 4),substr(a01.a0107, 5, 2)) as cs,"+
					"       (case                                                                "+
					"         when (select a08.a0801a                                            "+
					"                 from a08 a08                                               "+
					"                where a08.A0000 = a01.a0000                                 "+
					"                  and a08.a0834 = '1'                                       "+
					"                  and a08.a0899 = 'true') is null then                      "+
					"          null                                                              "+
					"         else                                                               "+
					"          (select a08.a0801a                                                "+
					"             from a08 a08                                                   "+
					"            where a08.A0000 = a01.a0000                                     "+
					"              and a08.a0834 = '1'                                           "+
					"              and a08.a0899 = 'true')                                       "+
					"       end) as xl,                                                          "+
					"       a01.a0192a,                                                          "+
					"       a01.a0120                                                            "+
					"  from a01 a01                                                              "+
					" where a01.a0000 in ("+ids+")                                               ";
		}else{
			sql = "select a01.a0101,                                                      "+
					"       (case                                                           "+
					"         when a01.a0104 = '1' then                                     "+
					"          '男'                                                         "+
					"         else                                                          "+
					"          '女'                                                         "+
					"       end) as XB,                                                     "+
					"       substr(a01.a0107, 1, 4) || '.' || substr(a01.a0107, 5, 2) as cs,"+
					"       (case                                                           "+
					"         when (select a08.a0801a                                       "+
					"                 from a08 a08                                          "+
					"                where a08.A0000 = a01.a0000                            "+
					"                  and a08.a0834 = '1'                                  "+
					"                  and a08.a0899 = 'true') is null then                 "+
					"          null                                                         "+
					"         else                                                          "+
					"          (select a08.a0801a                                           "+
					"             from a08 a08                                              "+
					"            where a08.A0000 = a01.a0000                                "+
					"              and a08.a0834 = '1'                                      "+
					"              and a08.a0899 = 'true')                                  "+
					"       end) as xl,                                                     "+
					"       a01.a0192a,                                                     "+
					"       a01.a0120                                                       "+
					"  from a01 a01                                                         "+
					" where a01.a0000 in ("+ids+")                                          ";
		}
		try {
			ResultSet rs = HBUtil.getHBSession().getSession().connection().prepareStatement(sql).executeQuery();
			if(rs != null){
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				while(rs.next()){
					script += "cell.SetCellString('"+line+"','"+row+"','0','"+xuhao+"');";
					script += "cell.SetRowHeight('1','48','"+row+"','0');";
					script += "cell.SetCellAlign('"+line+"','"+row+"','0','36');";//居中对齐
					for(int a = 1;a<=columnCount;a++){
						line++;
						String string = rs.getString(a);
						if(line == 7){
							string = tochen(string);
						}
						if(string != null ||"".equals(string)||"null".equals(string)){
							script += "cell.SetCellString('"+line+"','"+row+"','0','"+string+"');";
							script += "cell.SetCellFontSize('"+line+"','"+row+"','0','11');";//字体大小
							script += "cell.SetCellTextStyle('"+line+"','"+row+"','0','2');";//自动折行
							if(line == 1 || line == 2 || line == 3 || line == 4 ||line == 5 || line == 7){
								script += "cell.SetCellAlign('"+line+"','"+row+"','0','36');";//居中对齐
							}else if(line == 6){
								script += "cell.SetCellAlign('"+line+"','"+row+"','0','33');";//居中对齐
							}
						} else {
							script += "cell.SetCellString('"+line+"','"+row+"','0','');";
						}
						
					}
					line=1;
					row++;
					xuhao++;
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String id = split[0];
		List list = HBUtil.getHBSession().createSQLQuery("select A0201A  from a02 where a0000 = "+id+"").list();
		if(list != null && list.size()>0){
			Object object = list.get(0);
			script += "cell.SetCellString('4','3','0','"+object+"');";
			script += "cell.SetCellFontSize('4','3','0','14');";//字体大小
			script += "cell.SetCellAlign('4','3','0','36');";//居中对齐
		}
		script += "cell.PrintSetFoot('','','中共中央组织部 人力资源和社会保障部制');";
		script += "cell.SetCellString('4','5','0','"+(xuhao-1)+"');";
		script += "cell.SetCellFontSize('4','5','0','14');";//字体大小
		script += "cell.SetCellAlign('4','5','0','36');";//居中对齐
		script += "cell.PrintSetFoot('','第&P页','');";//在第三页加页号
		return script;
	}
	private String tochen(String mes){
		String mess= "";
		if("1".equals(mes)){
			mess = "一级";
		}else if("2".equals(mes)){
			mess = "二级";
		}else if("3".equals(mes)){
			mess = "三级";
		}else if("4".equals(mes)){
			mess = "四级";
		}else if("5".equals(mes)){
			mess = "五级";
		}else if("6".equals(mes)){
			mess = "六级";
		}else if("7".equals(mes)){
			mess = "七级";
		}else if("8".equals(mes)){
			mess = "八级";
		}else if("9".equals(mes)){
			mess = "九级";
		}else if("10".equals(mes)){
			mess = "十级";
		}else if("11".equals(mes)){
			mess = "十一级";
		}else if("12".equals(mes)){
			mess = "十二级";
		}else if("13".equals(mes)){
			mess = "十三级";
		}else if("14".equals(mes)){
			mess = "十四级";
		}else if("15".equals(mes)){
			mess = "十五级";
		}else if("16".equals(mes)){
			mess = "十六级";
		}else if("17".equals(mes)){
			mess = "十七级";
		}else if("18".equals(mes)){
			mess = "十八级";
		}else if("19".equals(mes)){
			mess = "十九级";
		}else if("20".equals(mes)){
			mess = "二十级";
		}else if("21".equals(mes)){
			mess = "二十一级";
		}else if("22".equals(mes)){
			mess = "二十二级";
		}else if("23".equals(mes)){
			mess = "二十三级";
		}else if("24".equals(mes)){
			mess = "二十四级";
		}else if("25".equals(mes)){
			mess = "二十五级";
		}else if("26".equals(mes)){
			mess = "二十六级";
		}else if("27".equals(mes)){
			mess = "二十七级";
		}else{
			mess = "";
		}
		
		return mess;
	} 

}
