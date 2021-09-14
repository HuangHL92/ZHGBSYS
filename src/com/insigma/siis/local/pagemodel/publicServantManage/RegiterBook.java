package com.insigma.siis.local.pagemodel.publicServantManage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBUtil;

public class RegiterBook {
	public String book(String tpid,String ids,String attribute) throws HibernateException, SQLException{
		String sqlid  = "";
		if(ids != null && ids != ""){
			ids = ids.replace("@", ",").replace("|", "'");
			String[] split = ids.split(",");
	 		int countrenyuan = split.length;
		}else{
			//select  A0000 as a0000,A0101 as a0101,A0184 as a0184,A0192 as a0192,A0104 as a0104,A0107 as a0107,A0117 as a0117,A0111A as a0111,A0114A as a0114,A0134 as a0134,A0140 as a0140,QRZXL as qrzxl,QRZXLXX as qrzxlxx,QRZXW as qrzxw  from A01 a01  where a01.a0000 in (select a0000 from A01SEARCHTEMP where sessionid='F3328D0AE9458EE66F385AAA6FCC5A53') /*ff7a1032-c09c-4b8d-9418-e88c2c7b80a9*/ order by a01.torgid,a01.torder 
 			sqlid =  "select  A0000 ";
 			int indexfrom = attribute.indexOf("from");
 			int indexorder = attribute.indexOf("order");
 			String substring = attribute.substring(indexfrom,indexorder);
 			sqlid = sqlid + substring;
 			ids = sqlid;
		}
		String script = "";
		script += "var cell = document.getElementById('cellweb1');\n";
		script += "var path = getPath();\n";
		script += "cell.WorkbookReadonly=true;\n";//设置不可编辑
		if("47b1011d70f34aefb89365bbfce".equals(tpid)){//名册一
			String [] arr = null;
			int row = 3;//行
			script += "var  aa = cell.openfile(ctpath+'/template/GBMCY.cll','');\n";
			String sql1 = "";
			if(DBUtil.getDBType()==DBType.MYSQL){
				sql1 = "select a01.a0101,                                                                  "+
						"       a0192,                                                                      "+
						"       a01.XB,                                                                     "+
						"       a01.a0117,                                                                  "+
						"       a01.csrq,                                                                   "+
						"       a01.a0111a,                                                                 "+
						"       a01.a0140,                                                                  "+
						"       a01.cjgz,                                                                   "+
						"       a01.xl,                                                                     "+
						"       a01.xw,                                                                     "+
						"       a01.xxjzy,                                                                  "+
						"       a01.xwjzy,                                                                  "+
						"       a01.a0196,                                                                  "+
						"       a01.a0221,                                                                  "+
						"       a01.a0192e,                                                                 "+
						"       GROUP_CONCAT(a0243) a0243,                                                  "+
						"       a01.a0288,                                                                  "+
						"       a01.a0192c                                                                  "+
						"  from (select a01.a0101,                                                          "+
						"               (case                                                               "+
						"                 when a01.a0104 = '1' then                                         "+
						"                  '男'                                                             "+
						"                 when a01.a0104 = '2' then                                         "+
						"                  '女'                                                             "+
						"                 else                                                              "+
						"                  ''                                                               "+
						"               end) as XB,                                                         "+
						"               (select code_name                                                   "+
						"                  from code_value                                                  "+
						"                 where code_type = 'GB3304'                                        "+
						"                   AND CODE_value = a01.a0117) a0117,                              "+
						"               CONCAT_WS('.',substr(a01.a0107, 1, 4),substr(a01.a0107, 5, 2)) as csrq,  "+
						"               a01.a0111a,                                                         "+
						"               a01.a0140,                                                          "+
						"               CONCAT_WS('.',substr(a01.a0134, 1, 4),substr(a01.a0134, 5, 2)) as cjgz,  "+
						"               (select a08.a0801a                                                  "+
						"                  from a08 a08                                                     "+
						"                 where a08.a0000 = a01.a0000                                       "+
						"                   and a08.a0834 = '1'                                             "+
						"                   and a0899 = 'true') as xl,                                      "+
						"               (select CONCAT(a08.a0814,a08.a0824)                                    "+
						"                  from a08 a08                                                     "+
						"                 where a08.a0000 = a01.a0000                                       "+
						"                   and a08.a0834 = '1'                                             "+
						"                   and a0899 = 'true') as xxjzy,                                   "+
						"               (select a08.a0901a                                                  "+
						"                  from a08 a08                                                     "+
						"                 where a08.a0000 = a01.a0000                                       "+
						"                   and a08.a0835 = '1'                                             "+
						"                   and a0899 = 'true') as xw,                                      "+
						"               (select CONCAT(a08.a0814,a08.a0824)                                    "+
						"                  from a08 a08                                                     "+
						"                 where a08.a0000 = a01.a0000                                       "+
						"                   and a08.a0835 = '1'                                             "+
						"                   and a0899 = 'true') as xwjzy,                                   "+
						"               a01.a0192,                                                          "+
						"               (select code_name                                                   "+
						"                  from code_value                                                  "+
						"                 where code_type = 'ZB09'                                          "+
						"                   AND CODE_value = a01.a0221) a0221,                              "+
						"               a01.a0192e,                                                         "+
						"               CONCAT_WS('.',substr(a02.a0243, 1, 4),substr(a02.a0243, 5, 2)) as a0243, "+
						"               CONCAT_WS('.',substr(a01.a0288, 1, 4),substr(a01.a0288, 5, 2)) as a0288, "+
						"               a01.a0196,                                                          "+
						"               a01.a0192c , a01.torgid, a01.torder                                 "+
						"          from a01, a02                                                            "+
						"         where a01.a0000 in ("+ids+")                                              "+
						"           and a02.a0000 = a01.a0000                                               "+
						"           and a02.a0281 = 'true') a01                                             "+
						" group by a01.a0101,                                                               "+
						"          a01.XB,                                                                  "+
						"          a01.a0117,                                                               "+
						"          a01.csrq,                                                                "+
						"          a01.a0111a,                                                              "+
						"          a01.a0140,                                                               "+
						"          a01.cjgz,                                                                "+
						"          a01.xl,                                                                  "+
						"          a01.xxjzy,                                                               "+
						"          a01.xw,                                                                  "+
						"          a01.xwjzy,                                                               "+
						"          a01.a0221,                                                               "+
						"          a01.a0192,                                                               "+
						"          a01.a0192e,                                                              "+
						"          a01.a0196,                                                               "+
						"          a01.a0288,                                                               "+
						"          a01.a0192c                                                               "+
						" order by a0288,a01.torgid,a01.torder                                               ";

				ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sql1).executeQuery();
				if(rs != null){
					arr = mysqlFunc(rs,row);
				}else{
					script += "cell.SetCellString('1','"+row+"', '0','');";
					script += "cell.SetCellString('2','"+row+"', '0','');";
					script += "cell.SetCellString('3','"+row+"', '0','');";
					script += "cell.SetCellString('4','"+row+"', '0','');";
					script += "cell.SetCellString('5','"+row+"', '0','');";
					script += "cell.SetCellString('6','"+row+"', '0','');";
					script += "cell.SetCellString('7','"+row+"', '0','');";
					script += "cell.SetCellString('8','"+row+"', '0','');";
					script += "cell.SetCellString('9','"+row+"', '0','');";
					script += "cell.SetCellString('10','"+row+"', '0','');";
					script += "cell.SetCellString('11','"+row+"', '0','');";
					script += "cell.SetCellString('12','"+row+"', '0','');";
					script += "cell.SetCellString('13','"+row+"', '0','');";
					script += "cell.SetCellString('14','"+row+"', '0','');";
				}
			}else{
				sql1 = "select a01.a0101, a0192, a01.XB, a01.a0117, a01.csrq, a01.a0111a, a01.a0140, a01.cjgz, a01.xl, a01.xw,a01.xxjzy,a01.xwjzy, a01.a0196, a01.a0221, a01.a0192e, wm_concat(a0243) a0243, a01.a0288, a01.a0192c from ( select a01.a0101, (case when a01.a0104 = '1' then '男' when a01.a0104 = '2' then  '女' else '' end) as XB, (select code_name from code_value where code_type='GB3304' AND CODE_value= a01.a0117) a0117, substr(a01.a0107, 1, 4) || '.' || substr(a01.a0107, 5, 2) as csrq, a01.a0111a, a01.a0140, substr(a01.a0134,1,4)||'.'||substr(a01.a0134,5,2) as cjgz, (select a08.a0801a from  a08 a08 where a08.a0000 = a01.a0000 and a08.a0834 = '1' and a0899 = 'true') as xl, (select (a08.a0814 || a08.a0824) from  a08 a08 where a08.a0000 = a01.a0000 and a08.a0834 = '1' and a0899 = 'true') as xxjzy, (select a08.a0901a from  a08 a08 where a08.a0000 = a01.a0000 and a08.a0835 = '1' and a0899 = 'true') as xw, (select (a08.a0814 || a08.a0824) from  a08 a08 where a08.a0000 = a01.a0000 and a08.a0835 = '1' and a0899 = 'true') as xwjzy, a01.a0192, (select code_name from code_value where code_type='ZB09' AND CODE_value=a01.a0221) a0221, a01.a0192e, substr(a02.a0243, 1, 4) || '.' || substr(a02.a0243, 5, 2) as a0243,substr(a01.a0288, 1, 4) || '.' || substr(a01.a0288, 5, 2) as a0288, a01.a0196, a01.a0192c, a01.torgid, a01.torder from a01 ,a02 where a01.a0000 in ( "+ids+" ) and a02.a0000=a01.a0000 and  a02.a0281 = 'true' ) a01  group by a01.a0101, a01.XB, a01.a0117, a01.csrq, a01.a0111a, a01.a0140, a01.cjgz, a01.xl, a01.xxjzy, a01.xw, a01.xwjzy, a01.a0221, a01.a0192, a01.a0192e, a01.a0196, a01.a0288, a01.a0192c, a01.torgid, a01.torder  order by a0288, a01.torgid, a01.torder ";
				List<Object[]> qsmess1 = HBUtil.getHBSession().createSQLQuery(sql1).list();
				arr = nameBook(qsmess1,row);
			}
			script += arr[0];
			row = Integer.parseInt(arr[1]);
			script += "cell.DeleteRow('"+(row)+"','"+(4007-row)+"','0');";
			script += "cell.PrintSetFoot('','第&P页','');";//在第三页加页号
			script += "cell.PrintSetTopTitle('2','2');";//增加表头
			}else if("eebdefc2-4d67-4452-a973".equals(tpid)){/*//名册二
				String [] arr = null;
				Map<String, String> map = new HashMap<String, String>();
				List list = new ArrayList<String>();//机构名称
				script += "var  aa = cell.openfile(ctpath+'/template/GBMCSADWFZ.cll','');\n";
				String sql = "select a0201b from a02 where a0000 in ("+ids+") group by a0201b order by a0201b";
				List<String> jigou = HBUtil.getHBSession().createSQLQuery(sql).list();
				String idall = "";
				String jimc = "";//存机构名称
				if(jigou != null && jigou.size()>0){
					for (String jigoubm : jigou) {
						String aa = "select a0201a,a0000 from a02 where a0000 in("+ids+") and a0201b = '"+jigoubm+"'  group by a0201a,a0000";
						List<Object[]> perid = HBUtil.getHBSession().createSQLQuery("select a0201a,a0000 from a02 where a0000 in("+ids+") and a0201b = '"+jigoubm+"'  group by a0201a,a0000").list();
						if(perid != null && perid.size()>0){
							for (Object[] objects : perid) {
								if("".equals(jimc)){
									jimc = (String)objects[0];
								}
								String personid = (String)objects[1];
								idall += "|"+personid+"|@";
								}
							list.add(jimc);
							map.put(jimc, idall);
							idall = "";
							jimc = "";
							}
					}
				}
				int row = 4;
				if(list != null && list.size()>0){
					for(int b = 0;b<list.size();b++){
						String jgmc = (String)list.get(b);
						String personidj = map.get(jgmc);
						personidj = personidj.substring(0, personidj.length()-1);
						personidj = personidj.replace("@", ",").replace("|", "'");
						if(b==0){
							script += "cell.SetCellString('1','2', '0','单位："+jgmc+"');";
							script += "cell.SetCellFontSize('1','2', '0','14');";//字体大小
							script += "cell.SetCellFontStyle('1','2', '0','2');";
							script += "cell.SetCellAlign('1','2', '0','33');";
							script += " var aa = cell.GetRowBestHeight('2');";
							script += " cell.SetRowHeight('1',aa,'2','0');";
						}else{
							script += "cell.MergeCells('1','"+row+"', '14','"+row+"');";
							script += "cell.SetCellString('1','"+row+"', '0','单位："+jgmc+"');";
							script += "cell.SetCellFontSize('1','"+row+"', '0','14');";//字体大小
							script += "cell.SetCellFontStyle('1','"+row+"', '0','2');";
							script += "cell.SetCellAlign('1','"+row+"', '0','33');";
							script += " var aa = cell.GetRowBestHeight('"+row+"');";
							script += " cell.SetRowHeight('1',aa,'"+row+"','0');";
							row++;
						}
						if(DBUtil.getDBType()==DBType.MYSQL){
							String sql1 = "select a01.a0101,                                                                      "+
									"       a0192,                                                                          "+
									"       a01.XB,                                                                         "+
									"       a01.a0117,                                                                      "+
									"       a01.csrq,                                                                       "+
									"       a01.a0111a,                                                                     "+
									"       a01.a0140,                                                                      "+
									"       a01.cjgz,                                                                       "+
									"       a01.xl,                                                                         "+
									"       a01.xw,                                                                         "+
									"       a01.xxjzy,                                                                      "+
									"       a01.xwjzy,                                                                      "+
									"       a01.a0196,                                                                      "+
									"       a01.a0221,                                                                      "+
									"       a01.a0192e,                                                                     "+
									"       GROUP_CONCAT(a0243) a0243,                                                      "+
									"       a01.a0288,                                                                      "+
									"       a01.a0192c                                                                      "+
									"  from (select a01.a0101,                                                              "+
									"               (case                                                                   "+
									"                 when a01.a0104 = '1' then                                             "+
									"                  '男'                                                                 "+
									"                 when a01.a0104 = '2' then                                             "+
									"                  '女'                                                                 "+
									"                 else                                                                  "+
									"                  ''                                                                   "+
									"               end) as XB,                                                             "+
									"               (select code_name                                                       "+
									"                  from code_value                                                      "+
									"                 where code_type = 'GB3304'                                            "+
									"                   AND CODE_value = a01.a0117) a0117,                                  "+
									"               CONCAT_WS('.',substr(a01.a0107, 1, 4),substr(a01.a0107, 5, 2)) as csrq, "+
									"               a01.a0111a,                                                             "+
									"               a01.a0140,                                                              "+
									"               CONCAT_WS('.',substr(a01.a0134, 1, 4),substr(a01.a0134, 5, 2)) as cjgz, "+
									"               (select a08.a0801a                                                      "+
									"                  from a08 a08                                                         "+
									"                 where a08.a0000 = a01.a0000                                           "+
									"                   and a08.a0834 = '1'                                                 "+
									"                   and a0899 = 'true') as xl,                                          "+
									"               (select CONCAT(a08.a0814,a08.a0824)                                     "+
									"                  from a08 a08                                                         "+
									"                 where a08.a0000 = a01.a0000                                           "+
									"                   and a08.a0834 = '1'                                                 "+
									"                   and a0899 = 'true') as xxjzy,                                       "+
									"               (select a08.a0901a                                                      "+
									"                  from a08 a08                                                         "+
									"                 where a08.a0000 = a01.a0000                                           "+
									"                   and a08.a0835 = '1'                                                 "+
									"                   and a0899 = 'true') as xw,                                          "+
									"               (select CONCAT(a08.a0814,a08.a0824)                                     "+
									"                  from a08 a08                                                         "+
									"                 where a08.a0000 = a01.a0000                                           "+
									"                   and a08.a0835 = '1'                                                 "+
									"                   and a0899 = 'true') as xwjzy,                                       "+
									"               a01.a0192,                                                              "+
									"               (select code_name                                                       "+
									"                  from code_value                                                      "+
									"                 where code_type = 'ZB09'                                              "+
									"                   AND CODE_value = a01.a0221) a0221,                                  "+
									"               a01.a0192e,                                                             "+
									"               CONCAT_WS('.',substr(a02.a0243, 1, 4),substr(a02.a0243, 5, 2)) as a0243,"+
									"               CONCAT_WS('.',substr(a01.a0288, 1, 4),substr(a01.a0288, 5, 2)) as a0288,"+
									"               a01.a0196,                                                              "+
									"               a01.a0192c,a01.torgid,a01.torder                                                              "+
									"          from a01, a02                                                                "+
									"         where a01.a0000 in ("+personidj+")                                            "+
									"           and a02.a0000 = a01.a0000                                                   "+
									"           and a02.a0281 = 'true') a01                                                 "+
									" group by a01.a0101,                                                                   "+
									"          a01.XB,                                                                      "+
									"          a01.a0117,                                                                   "+
									"          a01.csrq,                                                                    "+
									"          a01.a0111a,                                                                  "+
									"          a01.a0140,                                                                   "+
									"          a01.cjgz,                                                                    "+
									"          a01.xl,                                                                      "+
									"          a01.xxjzy,                                                                   "+
									"          a01.xw,                                                                      "+
									"          a01.xwjzy,                                                                   "+
									"          a01.a0221,                                                                   "+
									"          a01.a0192,                                                                   "+
									"          a01.a0192e,                                                                  "+
									"          a01.a0196,                                                                   "+
									"          a01.a0288,                                                                   "+
									"          a01.a0192c                                                                   "+
									" order by a0288,a01.torgid,a01.torder                                                                        ";

							ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sql1).executeQuery();
							if(rs != null){
								arr = mysqlFunc(rs,row);
								script += arr[0];
								row = Integer.parseInt(arr[1]);
							}else{
								script += "cell.SetCellString('1','"+row+"', '0','');";
								script += "cell.SetCellString('2','"+row+"', '0','');";
								script += "cell.SetCellString('3','"+row+"', '0','');";
								script += "cell.SetCellString('4','"+row+"', '0','');";
								script += "cell.SetCellString('5','"+row+"', '0','');";
								script += "cell.SetCellString('6','"+row+"', '0','');";
								script += "cell.SetCellString('7','"+row+"', '0','');";
								script += "cell.SetCellString('8','"+row+"', '0','');";
								script += "cell.SetCellString('9','"+row+"', '0','');";
								script += "cell.SetCellString('10','"+row+"', '0','');";
								script += "cell.SetCellString('11','"+row+"', '0','');";
								script += "cell.SetCellString('12','"+row+"', '0','');";
								script += "cell.SetCellString('13','"+row+"', '0','');";
								script += "cell.SetCellString('14','"+row+"', '0','');";
							}
						}else{
							String sql1 = "select a01.a0101, a0192, a01.XB, a01.a0117, a01.csrq, a01.a0111a, a01.a0140, a01.cjgz, a01.xl, a01.xw,a01.xxjzy,a01.xwjzy, a01.a0196, a01.a0221, a01.a0192e, wm_concat(a0243) a0243, a01.a0288, a01.a0192c from ( select a01.a0101, (case when a01.a0104 = '1' then '男' when a01.a0104 = '2' then  '女' else '' end) as XB, (select code_name from code_value where code_type='GB3304' AND CODE_value= a01.a0117) a0117, substr(a01.a0107, 1, 4) || '.' || substr(a01.a0107, 5, 2) as csrq, a01.a0111a, a01.a0140, substr(a01.a0134,1,4)||'.'||substr(a01.a0134,5,2) as cjgz, (select a08.a0801a from  a08 a08 where a08.a0000 = a01.a0000 and a08.a0834 = '1' and a0899 = 'true') as xl, (select (a08.a0814 || a08.a0824) from  a08 a08 where a08.a0000 = a01.a0000 and a08.a0834 = '1' and a0899 = 'true') as xxjzy, (select a08.a0901a from  a08 a08 where a08.a0000 = a01.a0000 and a08.a0835 = '1' and a0899 = 'true') as xw, (select (a08.a0814 || a08.a0824) from  a08 a08 where a08.a0000 = a01.a0000 and a08.a0835 = '1' and a0899 = 'true') as xwjzy, a01.a0192, (select code_name from code_value where code_type='ZB09' AND CODE_value=a01.a0221) a0221, a01.a0192e, substr(a02.a0243, 1, 4) || '.' || substr(a02.a0243, 5, 2) as a0243,substr(a01.a0288, 1, 4) || '.' || substr(a01.a0288, 5, 2) as a0288, a01.a0196, a01.a0192c from a01 ,a02 where a01.a0000 in ( "+personidj+" ) and a02.a0000=a01.a0000  and  a02.a0281 = 'true' ) a01  group by a01.a0101, a01.XB, a01.a0117, a01.csrq, a01.a0111a, a01.a0140, a01.cjgz, a01.xl, a01.xxjzy, a01.xw, a01.xwjzy, a01.a0221, a01.a0192, a01.a0192e, a01.a0196, a01.a0288, a01.a0192c  order by a0288 ";
							List<Object[]> qsmess1 = HBUtil.getHBSession().createSQLQuery(sql1).list();
							arr = nameBook(qsmess1,row);
							script += arr[0];
							row = Integer.parseInt(arr[1]);
						}
						
					}
					
				}
				script += arr[0];
				row = Integer.parseInt(arr[1]);
				script += "cell.DeleteRow('"+(row)+"','"+(4007-row)+"','0');";
				script += "cell.PrintSetFoot('','第&P页','');";//在第三页加页号
				script += "cell.PrintSetTopTitle('3','3');";//增加表头*/
				//名册二
				String [] arr = null;
				Map<String, String> map = new HashMap<String, String>();
				List<String> list = new ArrayList<String>();//机构名称
				script += "var  aa = cell.openfile(ctpath+'/template/GBMCSADWFZ.cll','');\n";
//				String sql = "select a0201a,a0201b from a02 where a0000 in ("+ids+") group by a0201b,a0201a order by a0201b";
				String sql = "select b0101,b0111 from b01 where b0111 in (select a0201b from a02 where a0000 in ("+ids+")) group by b0111,b0101 order by b0111";
				List<Object[]> jigou = HBUtil.getHBSession().createSQLQuery(sql).list();
				String idall = "";
				String jimc = "";//存机构名称
				if(jigou != null && jigou.size()>0){
					for (Object[] jigoubm : jigou) {
						String jgbm = (String)jigoubm[1];
						if("-1".equals(jgbm)){
							continue;
						}
						String jgname = (String)jigoubm[0];
						list.add(jgname);
						map.put(jgname, jgbm);
					}
				}
				int row = 4;
				if(list != null && list.size()>0){
					for(int b = 0;b<list.size();b++){
						String jgmc = (String)list.get(b);
						String jgid = map.get(jgmc);
						if(b==0){
							script += "cell.SetCellString('1','2', '0','单位："+jgmc+"');";
							script += "cell.SetCellFontSize('1','2', '0','14');";//字体大小
							script += "cell.SetCellFontStyle('1','2', '0','2');";
							script += "cell.SetCellAlign('1','2', '0','33');";
							script += " var aa = cell.GetRowBestHeight('2');";
							script += " cell.SetRowHeight('1',aa,'2','0');";
						}else{
							script += "cell.MergeCells('1','"+row+"', '14','"+row+"');";
							script += "cell.SetCellString('1','"+row+"', '0','单位："+jgmc+"');";
							script += "cell.SetCellFontSize('1','"+row+"', '0','14');";//字体大小
							script += "cell.SetCellFontStyle('1','"+row+"', '0','2');";
							script += "cell.SetCellAlign('1','"+row+"', '0','33');";
							script += " var aa = cell.GetRowBestHeight('"+row+"');";
							script += " cell.SetRowHeight('1',aa,'"+row+"','0');";
							row++;
						}
						if(DBUtil.getDBType()==DBType.MYSQL){
							String sql1 = "select a01.a0101,                                                                      "+
									"       a0192,                                                                          "+
									"       a01.XB,                                                                         "+
									"       a01.a0117,                                                                      "+
									"       a01.csrq,                                                                       "+
									"       a01.a0111a,                                                                     "+
									"       a01.a0140,                                                                      "+
									"       a01.cjgz,                                                                       "+
									"       a01.xl,                                                                         "+
									"       a01.xw,                                                                         "+
									"       a01.xxjzy,                                                                      "+
									"       a01.xwjzy,                                                                      "+
									"       a01.a0196,                                                                      "+
									"       a01.a0221,                                                                      "+
									"       a01.a0192e,                                                                     "+
									"       GROUP_CONCAT(a0243) a0243,                                                      "+
									"       a01.a0288,                                                                      "+
									"       a01.a0192c                                                                      "+
									"  from (select a01.a0101,                                                              "+
									"               (case                                                                   "+
									"                 when a01.a0104 = '1' then                                             "+
									"                  '男'                                                                 "+
									"                 when a01.a0104 = '2' then                                             "+
									"                  '女'                                                                 "+
									"                 else                                                                  "+
									"                  ''                                                                   "+
									"               end) as XB,                                                             "+
									"               (select code_name                                                       "+
									"                  from code_value                                                      "+
									"                 where code_type = 'GB3304'                                            "+
									"                   AND CODE_value = a01.a0117) a0117,                                  "+
									"               CONCAT_WS('.',substr(a01.a0107, 1, 4),substr(a01.a0107, 5, 2)) as csrq, "+
									"               a01.a0111a,                                                             "+
									"               a01.a0140,                                                              "+
									"               CONCAT_WS('.',substr(a01.a0134, 1, 4),substr(a01.a0134, 5, 2)) as cjgz, "+
									"               (select a08.a0801a                                                      "+
									"                  from a08 a08                                                         "+
									"                 where a08.a0000 = a01.a0000                                           "+
									"                   and a08.a0834 = '1'                                                 "+
									"                   and a0899 = 'true') as xl,                                          "+
									"               (select CONCAT(a08.a0814,a08.a0824)                                     "+
									"                  from a08 a08                                                         "+
									"                 where a08.a0000 = a01.a0000                                           "+
									"                   and a08.a0834 = '1'                                                 "+
									"                   and a0899 = 'true') as xxjzy,                                       "+
									"               (select a08.a0901a                                                      "+
									"                  from a08 a08                                                         "+
									"                 where a08.a0000 = a01.a0000                                           "+
									"                   and a08.a0835 = '1'                                                 "+
									"                   and a0899 = 'true') as xw,                                          "+
									"               (select CONCAT(a08.a0814,a08.a0824)                                     "+
									"                  from a08 a08                                                         "+
									"                 where a08.a0000 = a01.a0000                                           "+
									"                   and a08.a0835 = '1'                                                 "+
									"                   and a0899 = 'true') as xwjzy,                                       "+
									"               a01.a0192,                                                              "+
									"               (select code_name                                                       "+
									"                  from code_value                                                      "+
									"                 where code_type = 'ZB09'                                              "+
									"                   AND CODE_value = a01.a0221) a0221,                                  "+
									"               a01.a0192e,                                                             "+
									"               CONCAT_WS('.',substr(a02.a0243, 1, 4),substr(a02.a0243, 5, 2)) as a0243,"+
									"               CONCAT_WS('.',substr(a01.a0288, 1, 4),substr(a01.a0288, 5, 2)) as a0288,"+
									"               a01.a0196,                                                              "+
									"               a01.a0192c,a01.torgid,a01.torder                                                              "+
									"          from a01, a02                                                                "+
									"         where a01.a0000 in ("+ids+")                                            "+
									"           and a02.a0000 = a01.a0000                                                   "+
									"           and a02.a0201b = '"+jgid+"'                                                   "+
									"           and a02.a0281 = 'true') a01                                                 "+
									" group by a01.a0101,                                                                   "+
									"          a01.XB,                                                                      "+
									"          a01.a0117,                                                                   "+
									"          a01.csrq,                                                                    "+
									"          a01.a0111a,                                                                  "+
									"          a01.a0140,                                                                   "+
									"          a01.cjgz,                                                                    "+
									"          a01.xl,                                                                      "+
									"          a01.xxjzy,                                                                   "+
									"          a01.xw,                                                                      "+
									"          a01.xwjzy,                                                                   "+
									"          a01.a0221,                                                                   "+
									"          a01.a0192,                                                                   "+
									"          a01.a0192e,                                                                  "+
									"          a01.a0196,                                                                   "+
									"          a01.a0288,                                                                   "+
									"          a01.a0192c                                                                   "+
									" order by a0288,a01.torgid,a01.torder                                                                        ";

							ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sql1).executeQuery();
							if(rs != null){
								arr = mysqlFunc(rs,row);
								script += arr[0];
								row = Integer.parseInt(arr[1]);
							}else{
								script += "cell.SetCellString('1','"+row+"', '0','');";
								script += "cell.SetCellString('2','"+row+"', '0','');";
								script += "cell.SetCellString('3','"+row+"', '0','');";
								script += "cell.SetCellString('4','"+row+"', '0','');";
								script += "cell.SetCellString('5','"+row+"', '0','');";
								script += "cell.SetCellString('6','"+row+"', '0','');";
								script += "cell.SetCellString('7','"+row+"', '0','');";
								script += "cell.SetCellString('8','"+row+"', '0','');";
								script += "cell.SetCellString('9','"+row+"', '0','');";
								script += "cell.SetCellString('10','"+row+"', '0','');";
								script += "cell.SetCellString('11','"+row+"', '0','');";
								script += "cell.SetCellString('12','"+row+"', '0','');";
								script += "cell.SetCellString('13','"+row+"', '0','');";
								script += "cell.SetCellString('14','"+row+"', '0','');";
							}
						}else{
							String sql1 = "select a01.a0101, a0192, a01.XB, a01.a0117, a01.csrq, a01.a0111a, a01.a0140, a01.cjgz, a01.xl, a01.xw,a01.xxjzy,a01.xwjzy, a01.a0196, a01.a0221, a01.a0192e, wm_concat(a0243) a0243, a01.a0288, a01.a0192c from ( select a01.a0101, (case when a01.a0104 = '1' then '男' when a01.a0104 = '2' then  '女' else '' end) as XB, (select code_name from code_value where code_type='GB3304' AND CODE_value= a01.a0117) a0117, substr(a01.a0107, 1, 4) || '.' || substr(a01.a0107, 5, 2) as csrq, a01.a0111a, a01.a0140, substr(a01.a0134,1,4)||'.'||substr(a01.a0134,5,2) as cjgz, (select a08.a0801a from  a08 a08 where a08.a0000 = a01.a0000 and a08.a0834 = '1' and a0899 = 'true') as xl, (select (a08.a0814 || a08.a0824) from  a08 a08 where a08.a0000 = a01.a0000 and a08.a0834 = '1' and a0899 = 'true') as xxjzy, (select a08.a0901a from  a08 a08 where a08.a0000 = a01.a0000 and a08.a0835 = '1' and a0899 = 'true') as xw, (select (a08.a0814 || a08.a0824) from  a08 a08 where a08.a0000 = a01.a0000 and a08.a0835 = '1' and a0899 = 'true') as xwjzy, a01.a0192, (select code_name from code_value where code_type='ZB09' AND CODE_value=a01.a0221) a0221, a01.a0192e, substr(a02.a0243, 1, 4) || '.' || substr(a02.a0243, 5, 2) as a0243,substr(a01.a0288, 1, 4) || '.' || substr(a01.a0288, 5, 2) as a0288, a01.a0196, a01.a0192c,a01.torgid,a01.torder from a01 ,a02 where a01.a0000 in ( "+ids+" ) and a02.a0201b = '"+jgid+"' and a02.a0000=a01.a0000  and  a02.a0281 = 'true' ) a01  group by a01.a0101, a01.XB, a01.a0117, a01.csrq, a01.a0111a, a01.a0140, a01.cjgz, a01.xl, a01.xxjzy, a01.xw, a01.xwjzy, a01.a0221, a01.a0192, a01.a0192e, a01.a0196, a01.a0288, a01.a0192c,a01.torgid,a01.torder  order by a0288 ";
							List<Object[]> qsmess1 = HBUtil.getHBSession().createSQLQuery(sql1).list();
							arr = nameBook(qsmess1,row);
							script += arr[0];
							row = Integer.parseInt(arr[1]);
						}
					}
				}
				script += arr[0];
				row = Integer.parseInt(arr[1]);
				script += "cell.DeleteRow('"+(row)+"','"+(4007-row)+"','0');";
				script += "cell.PrintSetFoot('','第&P页','');";//在第三页加页号
				script += "cell.PrintSetTopTitle('3','3');";//增加表头
			}
		return script;
	}
	private String[] mysqlFunc(ResultSet rs, int row) {
		String [] arr = new String[2];
		String script = "";
		try {
			while(rs.next()){
				String xmmess = rs.getString(1);//姓名
				if(xmmess != null && !"null".equals(xmmess)){
					xmmess = xmmess.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('1','"+row+"', '0','"+xmmess+"');";
				}else{
					script += "cell.SetCellString('1','"+row+"', '0','');";
				}
				String xrzw = rs.getString(2);//现任职务
				if(xrzw != null && !"null".equals(xrzw) && !"null".equals(xrzw)){
					xrzw = xrzw.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('2','"+row+"', '0','"+xrzw+"');";
				}else{
					script += "cell.SetCellString('2','"+row+"', '0','');";
				}
				String xb = rs.getString(3);//性别
				if(xb != null && !"null".equals(xb)){
					script += "cell.SetCellString('3','"+row+"', '0','"+xb+"');";
				}else{
					script += "cell.SetCellString('3','"+row+"', '0','');";
				}
				String mz = rs.getString(4);//民族
				if(mz != null && !"null".equals(mz)){
					mz = mz.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('4','"+row+"', '0','"+mz+"');";
				}else{
					script += "cell.SetCellString('4','"+row+"', '0','');";
				}
				String csny = rs.getString(5);//出生年月
				if(csny != null && !"null".equals(csny)){
					csny = csny.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('5','"+row+"', '0','"+csny+"');";
				}else{
					script += "cell.SetCellString('5','"+row+"', '0','');";
				}
				String jg = rs.getString(6);//籍贯
				if(jg != null && !"null".equals(jg)){
					jg = jg.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('6','"+row+"', '0','"+jg+"');";
				}else{
					script += "cell.SetCellString('6','"+row+"', '0','');";
				}
				String rdsj = rs.getString(7);//入党时间
				if(rdsj != null && !"null".equals(rdsj)){
					rdsj = rdsj.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('7','"+row+"', '0','"+rdsj+"');";
				}else{
					script += "cell.SetCellString('7','"+row+"', '0','');";
				}
				String cjgzsj = rs.getString(8);//参加工作时间
				if(cjgzsj != null && !"null".equals(cjgzsj)){
					cjgzsj = cjgzsj.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('8','"+row+"', '0','"+cjgzsj+"');";
				}else{
					script += "cell.SetCellString('8','"+row+"', '0','');";
				}
				String xl = rs.getString(9);//学历
				String xw = rs.getString(10);//学位
				if((xl != null && !"null".equals(xl)&&!"".equals(xl)) && (xw != null && !"null".equals(xw)&&!"".equals(xw))){
					xl = xl.replace("\r\n", "").replace("\r", "").replace("\n", "");
					xw = xw.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('9','"+row+"', '0','"+xl+"\\\\r\\\\n"+xw+"');";
				}else if((xl != null && !"null".equals(xl)&&!"".equals(xl)) && (xw == null || "null".equals(xw) || "".equals(xw))){
					xl = xl.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('9','"+row+"', '0','"+xl+"');";
				}else if((xl == null || ("null".equals(xl) || "".equals(xl))) && (xw != null && !"null".equals(xw)&&!"".equals(xw))){
					xw = xw.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('9','"+row+"', '0','"+xw+"');";
				}
				String xlxjjzy = rs.getString(11);//学历校及专业
				String xwxjjzy = rs.getString(12);//学位学校及专业
				if((xlxjjzy != null && !"null".equals(xlxjjzy)&&!"".equals(xlxjjzy)) && (xwxjjzy != null && !"null".equals(xwxjjzy)&&!"".equals(xwxjjzy) )&& xlxjjzy.equals(xwxjjzy)){
					xlxjjzy = xlxjjzy.replace("\r\n", "").replace("\r", "").replace("\n", "");
					xwxjjzy = xwxjjzy.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('10','"+row+"', '0','"+xlxjjzy+"');";
				}else if((xlxjjzy != null && !"null".equals(xlxjjzy)&&!"".equals(xlxjjzy)) && (xwxjjzy != null && !"null".equals(xwxjjzy)&&!"".equals(xwxjjzy) )&& !xlxjjzy.equals(xwxjjzy)){
					xlxjjzy = xlxjjzy.replace("\r\n", "").replace("\r", "").replace("\n", "");
					xwxjjzy = xwxjjzy.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('10','"+row+"', '0','"+xlxjjzy+"\\\\r\\\\n"+xwxjjzy+"');";
				}else if((xlxjjzy != null && !"null".equals(xlxjjzy)&&!"".equals(xlxjjzy)) && (xwxjjzy == null || "null".equals(xwxjjzy)|| "".equals(xwxjjzy) )){
					xlxjjzy = xlxjjzy.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('10','"+row+"', '0','"+xlxjjzy+"');";
				}else if((xlxjjzy == null || "null".equals(xlxjjzy) || "".equals(xlxjjzy)) && (xwxjjzy != null && !"null".equals(xwxjjzy)&&!"".equals(xwxjjzy) )){
					xwxjjzy = xwxjjzy.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('10','"+row+"', '0','"+xwxjjzy+"');";
				}
				String zyjs = rs.getString(13);//专业技术职称
				if(zyjs != null && !"null".equals(zyjs)){
					zyjs = zyjs.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('11','"+row+"', '0','"+zyjs+"');";
				}else{
					script += "cell.SetCellString('11','"+row+"', '0','');";
				}
				
				String zwcc = rs.getString(14);//职务层次
				String zj = rs.getString(15);//职级
				if((zwcc != null && !"null".equals(zwcc)&&!"".equals(zwcc) )&& (zj != null && !"null".equals(zj)&&!"".equals(zj))){
					zwcc = zwcc.replace("\r\n", "").replace("\r", "").replace("\n", "");
					zj = zj.replace("\r\n", "");
					script += "cell.SetCellString('12','"+row+"', '0','"+zwcc+"\\\\r\\\\n"+zj+"');";
				}else if((zwcc != null && !"null".equals(zwcc)&&!"".equals(zwcc) )&& (zj == null || "null".equals(zj) || "".equals(zj))){
					zwcc = zwcc.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('12','"+row+"', '0','"+zwcc+"');";
				}else if((zwcc == null || "null".equals(zwcc) || "".equals(zwcc) )&& (zj != null && !"null".equals(zj)&&!"".equals(zj))){
					zj = zj.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('12','"+row+"', '0','"+zj+"');";

				}
				String rzny = rs.getString(16);//任职年月
				if(rzny != null && !"null".equals(rzny)&& !"".equals(rzny)){
					rzny = rzny.replace(",", "\\\\r\\\\n").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('13','"+row+"', '0','"+rzny+"');";
				}else{
					script += "cell.SetCellString('13','"+row+"', '0','');";
				}
				
				String ccsj = rs.getString(17);//任职务层次时间
				String rzsj = rs.getString(18);//任职级时间
				if((ccsj != null && !"null".equals(ccsj)&&!"".equals(ccsj)) && ((rzsj != null && !"null".equals(rzsj)&&!"".equals(rzsj)))){
					ccsj = ccsj.replace("\r\n", "").replace("\r", "").replace("\n", "");
					rzsj = rzsj.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('14','"+row+"', '0','"+ccsj+"\\\\r\\\\n"+rzsj+"');";
				}else if((ccsj != null && !"null".equals(ccsj)&&!"".equals(ccsj)) && ((rzsj == null || "null".equals(rzsj) || "".equals(rzsj)))){
					script += "cell.SetCellString('14','"+row+"', '0','"+ccsj+"');";
				}else if((ccsj == null || "null".equals(ccsj) || "".equals(ccsj)) && ((rzsj != null && !"null".equals(rzsj)&&!"".equals(rzsj)))){
					script += "cell.SetCellString('14','"+row+"', '0','"+rzsj+"');";
				}
				script += "var hei = cell.GetRowBestHeight('"+row+"');";
				script += "cell.SetRowHeight('1',hei,'"+row+"','0');";
				row++;
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		arr[0] = script;
		arr[1] = String.valueOf(row);
		return arr;
	}
	public String[] nameBook(List<Object[]> qsmess1,int row){
		String [] arr = new String[2];
		String script = "";
		if(qsmess1 != null && qsmess1.size()>0){
			for (Object[] objects : qsmess1) {
				String xmmess = (String)objects[0];//姓名
				if(xmmess != null && !"null".equals(xmmess)){
					xmmess = xmmess.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('1','"+row+"', '0','"+xmmess+"');";
				}else{
					script += "cell.SetCellString('1','"+row+"', '0','');";
				}
				String xrzw = (String)objects[1];//现任职务
				if(xrzw != null && !"null".equals(xrzw) && !"null".equals(xrzw)){
					xrzw = xrzw.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('2','"+row+"', '0','"+xrzw+"');";
				}else{
					script += "cell.SetCellString('2','"+row+"', '0','');";
				}
				String xb = (String)objects[2];//性别
				if(xb != null && !"null".equals(xb)){
					script += "cell.SetCellString('3','"+row+"', '0','"+xb+"');";
				}else{
					script += "cell.SetCellString('3','"+row+"', '0','');";
				}
				String mz = (String)objects[3];//民族
				if(mz != null && !"null".equals(mz)){
					mz = mz.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('4','"+row+"', '0','"+mz+"');";
				}else{
					script += "cell.SetCellString('4','"+row+"', '0','');";
				}
				String csny = (String)objects[4];//出生年月
				if(csny != null && !"null".equals(csny)){
					csny = csny.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('5','"+row+"', '0','"+csny+"');";
				}else{
					script += "cell.SetCellString('5','"+row+"', '0','');";
				}
				String jg = (String)objects[5];//籍贯
				if(jg != null && !"null".equals(jg)){
					jg = jg.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('6','"+row+"', '0','"+jg+"');";
				}else{
					script += "cell.SetCellString('6','"+row+"', '0','');";
				}
				String rdsj = (String)objects[6];//入党时间
				if(rdsj != null && !"null".equals(rdsj)){
					rdsj = rdsj.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('7','"+row+"', '0','"+rdsj+"');";
				}else{
					script += "cell.SetCellString('7','"+row+"', '0','');";
				}
				String cjgzsj = (String)objects[7];//参加工作时间
				if(cjgzsj != null && !"null".equals(cjgzsj)){
					cjgzsj = cjgzsj.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('8','"+row+"', '0','"+cjgzsj+"');";
				}else{
					script += "cell.SetCellString('8','"+row+"', '0','');";
				}
				String xl = (String)objects[8];//学历
				String xw = (String)objects[9];//学位
				if((xl != null && !"null".equals(xl)&&!"".equals(xl)) && (xw != null && !"null".equals(xw)&&!"".equals(xw))){
					xl = xl.replace("\r\n", "").replace("\r", "").replace("\n", "");
					xw = xw.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('9','"+row+"', '0','"+xl+"\\\\r\\\\n"+xw+"');";
				}else if((xl != null && !"null".equals(xl)&&!"".equals(xl)) && (xw == null || "null".equals(xw) || "".equals(xw))){
					xl = xl.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('9','"+row+"', '0','"+xl+"');";
				}else if((xl == null || ("null".equals(xl) || "".equals(xl))) && (xw != null && !"null".equals(xw)&&!"".equals(xw))){
					xw = xw.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('9','"+row+"', '0','"+xw+"');";
				}
				String xlxjjzy = (String)objects[10];//学历校及专业
				String xwxjjzy = (String)objects[11];//学位学校及专业
				if((xlxjjzy != null && !"null".equals(xlxjjzy)&&!"".equals(xlxjjzy)) && (xwxjjzy != null && !"null".equals(xwxjjzy)&&!"".equals(xwxjjzy) )&& xlxjjzy.equals(xwxjjzy)){
					xlxjjzy = xlxjjzy.replace("\r\n", "").replace("\r", "").replace("\n", "");
					xwxjjzy = xwxjjzy.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('10','"+row+"', '0','"+xlxjjzy+"');";
				}else if((xlxjjzy != null && !"null".equals(xlxjjzy)&&!"".equals(xlxjjzy)) && (xwxjjzy != null && !"null".equals(xwxjjzy)&&!"".equals(xwxjjzy) )&& !xlxjjzy.equals(xwxjjzy)){
					xlxjjzy = xlxjjzy.replace("\r\n", "").replace("\r", "").replace("\n", "");
					xwxjjzy = xwxjjzy.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('10','"+row+"', '0','"+xlxjjzy+"\\\\r\\\\n"+xwxjjzy+"');";
				}else if((xlxjjzy != null && !"null".equals(xlxjjzy)&&!"".equals(xlxjjzy)) && (xwxjjzy == null || "null".equals(xwxjjzy)|| "".equals(xwxjjzy) )){
					xlxjjzy = xlxjjzy.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('10','"+row+"', '0','"+xlxjjzy+"');";
				}else if((xlxjjzy == null || "null".equals(xlxjjzy) || "".equals(xlxjjzy)) && (xwxjjzy != null && !"null".equals(xwxjjzy)&&!"".equals(xwxjjzy) )){
					xwxjjzy = xwxjjzy.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('10','"+row+"', '0','"+xwxjjzy+"');";
				}
				String zyjs = (String)objects[12];//专业技术职称
				if(zyjs != null && !"null".equals(zyjs)){
					zyjs = zyjs.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('11','"+row+"', '0','"+zyjs+"');";
				}else{
					script += "cell.SetCellString('11','"+row+"', '0','');";
				}
				
				String zwcc = (String)objects[13];//职务层次
				String zj = (String)objects[14];//职级
				if((zwcc != null && !"null".equals(zwcc)&&!"".equals(zwcc) )&& (zj != null && !"null".equals(zj)&&!"".equals(zj))){
					zwcc = zwcc.replace("\r\n", "").replace("\r", "").replace("\n", "");
					zj = zj.replace("\r\n", "");
					script += "cell.SetCellString('12','"+row+"', '0','"+zwcc+"\\\\r\\\\n"+zj+"');";
				}else if((zwcc != null && !"null".equals(zwcc)&&!"".equals(zwcc) )&& (zj == null || "null".equals(zj) || "".equals(zj))){
					zwcc = zwcc.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('12','"+row+"', '0','"+zwcc+"');";
				}else if((zwcc == null || "null".equals(zwcc) || "".equals(zwcc) )&& (zj != null && !"null".equals(zj)&&!"".equals(zj))){
					zj = zj.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('12','"+row+"', '0','"+zj+"');";

				}
				String rzny = (String)objects[15];//任职年月
				if(rzny != null && !"null".equals(rzny)&& !"".equals(rzny)){
					rzny = rzny.replace(",", "\\\\r\\\\n").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('13','"+row+"', '0','"+rzny+"');";
				}else{
					script += "cell.SetCellString('13','"+row+"', '0','');";
				}
				
				String ccsj = (String)objects[16];//任职务层次时间
				String rzsj = (String)objects[17];//任职级时间
				if((ccsj != null && !"null".equals(ccsj)&&!"".equals(ccsj)) && ((rzsj != null && !"null".equals(rzsj)&&!"".equals(rzsj)))){
					ccsj = ccsj.replace("\r\n", "").replace("\r", "").replace("\n", "");
					rzsj = rzsj.replace("\r\n", "").replace("\r", "").replace("\n", "");
					script += "cell.SetCellString('14','"+row+"', '0','"+ccsj+"\\\\r\\\\n"+rzsj+"');";
				}else if((ccsj != null && !"null".equals(ccsj)&&!"".equals(ccsj)) && ((rzsj == null || "null".equals(rzsj) || "".equals(rzsj)))){
					script += "cell.SetCellString('14','"+row+"', '0','"+ccsj+"');";
				}else if((ccsj == null || "null".equals(ccsj) || "".equals(ccsj)) && ((rzsj != null && !"null".equals(rzsj)&&!"".equals(rzsj)))){
					script += "cell.SetCellString('14','"+row+"', '0','"+rzsj+"');";
				}
				script += "var hei = cell.GetRowBestHeight('"+row+"');";
				script += "cell.SetRowHeight('1',hei,'"+row+"','0');";
				row++;
			}
			}
		arr[0] = script;
		arr[1] = String.valueOf(row);
		return arr;
	}

}
