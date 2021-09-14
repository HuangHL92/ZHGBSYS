package com.insigma.siis.local.pagemodel.cadremgn.dataproofread.sysorg;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryPageModel;
import com.insigma.siis.local.pagemodel.zj.Utils;

public class SingleVerifyPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX()");
		return EventRtnType.NORMAL_SUCCESS;
	}	
	
	@PageEvent("initX()")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException{
		
		HBSession sess=HBUtil.getHBSession();
		String userid=SysUtil.getCacheCurrentUser().getId();
		//String vscsql="select vsc001,vsc002 from verify_scheme where vsc003='1' and (vsc007='1' or vsc005='"+userid+"') order by vsc007";

		String sql="select TABLE_CODE,TABLE_NAME from CODE_TABLE order by TABLE_CODE";
		List<Object[]> list=sess.createSQLQuery(sql).list();
		//HashMap<String,String> map= new HashMap<String,String>();
		HashMap<String,String> map= new LinkedHashMap<String,String>();
		if(list!=null&&list.size()>0){
			for(Object[] obj:list){
				String TABLE_CODE=obj[0].toString();
				String TABLE_NAME=obj[0].toString()+"."+obj[1].toString();
				map.put(TABLE_CODE, TABLE_NAME);
			}
		}
	((Combo)this.getPageElement("TABLE_CODE")).setValueListForSelect(map);
	if(list!=null&&list.size()>0){
		this.getPageElement("TABLE_CODE").setValue(list.get(0)[0].toString());
	}
	
	
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("executeCheck")
	public int executeCheck(String table){

		
			try {
				this.getPageElement("table").setValue(table);
			} catch (RadowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		this.setNextEventName("editgrids.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("editgrids.dogridquery")
	public int executeChecks(int start,int limit) throws AppException, RadowException, SQLException{

		String table = this.getPageElement("table").getValue();
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String userid = user.getId();
		CommQuery cqbs=new CommQuery();
		String condition="";//条件
		List<HashMap<String, Object>> list=new ArrayList<HashMap<String, Object>>();
		String deleteSql="delete from proofread where p_userid='"+userid+"'";
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		stmt.executeQuery(deleteSql);
		stmt.close();
		String sql="";
		if("40288103556cc97701556d629135000f".equals(userid)){
			if(table == null ||"".equals(table)){
			 sql="select * from code_table_col where code_type is null and cdtval1 is not null and cdttyp is not null";

			}else{
			 sql="select * from code_table_col where code_type is null and cdtval1 is not null and cdttyp is not null and TABLE_CODE = '"+table+"'";
			}
		}else{
		//String sql="select * from code_table_col where code_type is null and cdtval1 is not null and cdttyp is not null";
			if(table == null ||"".equals(table)){
				sql="select a.* from code_table_col a,COMPETENCE_USERTABLECOL b "
						+ "where a.code_type is null and a.cdtval1 is not null and a.cdttyp is not null "
						+ "  and a.table_code=b.table_code and a.col_code=b.col_code and b.userid in ('"+Utils.getRoleId(userid)+"')"
								+ " and b.islook='1' "
								+ " and b.ischeckout='1' ";
				
			}else{
				sql="select a.* from code_table_col a,COMPETENCE_USERTABLECOL b "
						+ "where a.code_type is null and a.cdtval1 is not null and a.cdttyp is not null and TABLE_CODE = '"+table+"'"
						+ "  and a.table_code=b.table_code and a.col_code=b.col_code and b.userid in ('"+Utils.getRoleId(userid)+"')"
								+ " and b.islook='1' "
								+ " and b.ischeckout='1' ";
			}
			
			
		}
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
		int n=1;
		for(HashMap<String, Object> a:listCode){
			n++;
			String str=a.get("cdttyp").toString();
			String yesmst="";
			String cortable="";
			String importID="";
			String usercond="";
			if("B".equals(a.get("table_code").toString().substring(0,1))){
				importID="B0111";
				cortable="B01";
				usercond=" and a."+importID+" in (select cu.b0111 from competence_userdept cu where cu.userid = '"+userid+"')";
			}else{
				importID="A0000";
				cortable="A01";
				if("40288103556cc97701556d629135000f".equals(userid)){
					
				}else{
					String instr=CustomQueryPageModel.getGllb2();
					if(!"".equals(instr)){
						usercond=" and a."+importID+" in "+instr;
					}
//					usercond=" and a."+importID+" in (select t.a0000 from Competence_Subperson t where t.userid = '"+userid+"' and t.type = '1')";
				}
			}
			if("1".equals(a.get("yesmst"))){
				
			}else{
				yesmst=" and a."+a.get("col_code")+" is not null";
			}
			if("<".equals(str)){
				condition=">=";
				addList(list,a,condition,yesmst,userid,usercond);
			}else if(">".equals(str)){
				condition="<=";
				addList(list,a,condition,yesmst,userid,usercond);
			}else if("=".equals(str)){
				condition="<>";
				addList(list,a,condition,yesmst,userid,usercond);
			}else if("I".equals(str)){
				sql="select * from "+a.get("table_code")+" a,"+cortable+" b  where a."+importID+"=b."+importID+" and a."+a.get("col_code")+" not in ('"+(a.get("cdtval1")==null?"":a.get("cdtval1")).toString().replace(",", "','")+"') "+yesmst ;
				mapList(a,list,sql,userid,usercond);
			}else if("!<".equals(str)){
				condition="<";
				addList(list,a,condition,yesmst,userid,usercond);
			}else if("!>".equals(str)){
				condition=">";
				addList(list,a,condition,yesmst,userid,usercond);
			}else if("!=".equals(str)){
				condition="=";
				addList(list,a,condition,yesmst,userid,usercond);
			}else if("!I".equals(str)){
				sql="select * from "+a.get("table_code")+" a,"+cortable+" b  where a."+importID+"=b."+importID+" and a."+a.get("col_code")+" in ('"+(a.get("cdtval1")==null?"":a.get("cdtval1")).toString().replace(",", "','")+"')"+yesmst ;
				mapList(a,list,sql,userid,usercond);
			}else if("L<".equals(str)){
				condition=">=";
				addLenList(list,a,condition,yesmst,userid,usercond);
			}else if("L>".equals(str)){
				condition="<=";
				addLenList(list,a,condition,yesmst,userid,usercond);
			}else if("L=".equals(str)){
				condition="<>";
				addLenList(list,a,condition,yesmst,userid,usercond);
			}else if("LI".equals(str)){
				sql="select * from "+a.get("table_code")+" a,"+cortable+" b  where  a."+importID+"=b."+importID+" and Length(a."+a.get("col_code")+") in ('"+(a.get("cdtval1")==null?"":a.get("cdtval1")).toString().replace(",", "','")+"') "+yesmst ;
				mapList(a,list,sql,userid,usercond);
			}else if("L!<".equals(str)){
				condition="<";
				addLenList(list,a,condition,yesmst,userid,usercond);
			}else if("L!>".equals(str)){
				condition=">";
				addLenList(list,a,condition,yesmst,userid,usercond);
			}else if("L!=".equals(str)){
				condition="=";
				addLenList(list,a,condition,yesmst,userid,usercond);
			}else if("L!I".equals(str)){
				sql="select * from "+a.get("table_code")+" a,"+cortable+" b  where a."+importID+"=b."+importID+" and Length(a."+a.get("col_code")+") in ('"+(a.get("cdtval1")==null?"":a.get("cdtval1")).toString().replace(",", "','")+"') "+yesmst;
				mapList(a,list,sql,userid,usercond);
			}else{
				if(a.get("cdttyp")!=null&&"".equals(a.get("cdttyp"))){
					if("B".equals(str)){
						sql="select * from "+a.get("table_code")+" where a."+importID+"=b."+importID+" and a."+a.get("col_code")+"<"+a.get("cdtval1")+" or  a."+a.get("col_code")+">"+a.get("cdtval2")+yesmst;
					}else if("!B".equals(str)){
						sql="select * from "+a.get("table_code")+" where a."+importID+"=b."+importID+" and a."+a.get("col_code")+">="+a.get("cdtval1")+" and a."+a.get("col_code")+"<="+a.get("cdtval2")+yesmst;
					}else if("LB".equals(str)){
						sql="select * from "+a.get("table_code")+" where a."+importID+"=b."+importID+" and Length(a."+a.get("col_code")+") <"+a.get("cdtval1")+" or Length(a."+a.get("col_code")+") >"+a.get("cdtval2")+yesmst;
					}else if("L!B".equals(str)){
						sql="select * from "+a.get("table_code")+" where a."+importID+"=b."+importID+" and Length(a."+a.get("col_code")+") >="+a.get("cdtval1")+" and Length(a."+a.get("col_code")+") <="+a.get("cdtval2")+yesmst;
					}
					mapList(a,list,sql,userid,usercond);
				}
			}
		}
		
		if("40288103556cc97701556d629135000f".equals(userid)){
			
			if(table == null ||"".equals(table)){
				sql="select * from code_table_col where code_type is not null ";

			}else{
				sql="select * from code_table_col where code_type is not null and TABLE_CODE = '"+table+"'";
			}
		}else{
			if(table == null ||"".equals(table)){
				sql="select a.* from code_table_col a,COMPETENCE_USERTABLECOL b where a.code_type is not null "
						+ "  and a.table_code=b.table_code and a.col_code=b.col_code and b.userid in ('"+Utils.getRoleId(userid)+"') and b.ischeckout='1'";
			}else{
				sql="select a.* from code_table_col a,COMPETENCE_USERTABLECOL b where a.code_type is not null and a.TABLE_CODE = '"+table+"'"
						+ "  and a.table_code=b.table_code and a.col_code=b.col_code and b.userid in ('"+Utils.getRoleId(userid)+"') and b.ischeckout='1'";
			}
			
		}
		List<HashMap<String, Object>> listDM=cqbs.getListBySQL(sql);
		if(listDM!=null&&listDM.size()>0){
			for(HashMap<String, Object> a:listDM){
				String cortable="";
				String importID="";
				String usercond="";
				if("B".equals(a.get("table_code").toString().substring(0,1))){
					importID="B0111";
					cortable="B01";
					usercond=" and a."+importID+" in (select cu.b0111 from competence_userdept cu where cu.userid = '"+userid+"')";
				}else{
					importID="A0000";
					cortable="A01";
					if("40288103556cc97701556d629135000f".equals(userid)){
						
					}else{
						String instr=CustomQueryPageModel.getGllb2();
						if(!"".equals(instr)){
							usercond=" and a."+importID+" in "+instr;
						}
//						usercond=" and a."+importID+" in (select t.a0000 from Competence_Subperson t where t.userid = '"+userid+"' and t.type = '1')";
					}
				}
				if("B01".equals(a.get("code_type"))){
					if("1".equals(a.get("yesmst"))){
						sql="select * from "+a.get("table_code")+" a,"+cortable+" b  where a."+importID+"=b."+importID+" and a."+a.get("col_code")+" not in (select B0111 from B01)" ;
						mapList2(a,list,sql,userid,usercond);
					}else{
						sql="select * from "+a.get("table_code")+" a,"+cortable+" b  where a."+importID+"=b."+importID+" and a."+a.get("col_code")+" is not null and a."+a.get("col_code")+" not in (select B0111 from B01)" ;
						mapList2(a,list,sql,userid,usercond);
					}
				}else{
					if("1".equals(a.get("yesmst"))){
						sql="select * from "+a.get("table_code")+" a,"+cortable+" b  where a."+importID+"=b."+importID+" and a."+a.get("col_code")+" not in (select code_value from code_value where code_type='"+a.get("code_type")+"')" ;
						mapList2(a,list,sql,userid,usercond);
					}else{
						sql="select * from "+a.get("table_code")+" a,"+cortable+" b  where a."+importID+"=b."+importID+" and a."+a.get("col_code")+" is not null and a."+a.get("col_code")+" not in (select code_value from code_value where code_type='"+a.get("code_type")+"')" ;
						mapList2(a,list,sql,userid,usercond);
					}
				}
			}
		}
		sql="select rownum ,t.* from (select p_a0000b0111,p_tablename,p_codename,p_hingename,p_hingevalue,p_codevalue,p_texterror from proofread where p_userid='"+userid+"' order by p_tablename,p_codename,p_hingename) t";
		this.pageQuery(sql, "SQL", start,limit);
		//this.getPageElement("editgrids").setValueList(list);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 筛选时间类型，并将1900-01-01转为19000101或者'19000101'
	 */
	public static String matcherRegularExpression(String str) {
        Pattern pattern = Pattern.compile("\\d{4}-(0[1-9]|1[1-2])-(0[1-9]|[1-2]\\d|3[0-1])");
        Matcher matcher = pattern.matcher(str);
        boolean found = false;
        while (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
	/**
	 * 对map组装赋值给list(有code_type的字段)
	 * @throws AppException 
	 * @throws SQLException 
	 */
	public void mapList2(HashMap<String, Object> a,List<HashMap<String, Object>> list,String sql,String userid,String usercond) throws AppException, SQLException{
		String importID="";
		String name="";
		String card="";
		if("t".equalsIgnoreCase(a.get("col_data_type")==null?"":a.get("col_data_type").toString())){
			String string = matcherRegularExpression(sql);
			if("".equals(string)){
				sql = sql + " and a."+a.get("col_code")+" like  '____-__-__'";
			}else{
				sql = sql.replace(string, string.trim().replace("-", "")) + " and a."+a.get("col_code")+" like  '____-__-__'";
			}
		}
		if("B".equals(a.get("table_code").toString().substring(0,1))){
			importID="B0111";
			name="B0101";
			card="B0111";
		}else{
			importID="A0000";
			name="A0101";
			card="A0184";
		}
		String str="b."+importID+"," + "'"+userid+"','"+a.get("table_code")+a.get("col_lection_name")+"' a0833,'"+a.get("col_code")+a.get("col_name")+"' a0834,b."+name+" a0835"
				+ ",b."+card+" a0836,a."+a.get("col_code")+" a0800,nvl('"+entryStr(a.get("cdterrdscp"))+"','"+a.get("col_name")+":代码输入错误!"+"') a0184";
		sql=sql.replace("*", str);
		sql=sql+" "+usercond;
//		System.out.println(sql+"333");
		String insertSql="insert into proofread(p_a0000b0111,p_userid,p_tablename,p_codename,p_hingename,p_hingevalue,p_codevalue,p_texterror) "+sql;
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		try{
			stmt.executeQuery(insertSql);
		}catch(Exception e){
			System.out.println("执行单项校核时出错1："+insertSql);
		}finally{
			stmt.close();
		}
	}
	
	/**
	 * 对map组装赋值给list
	 * @throws AppException 
	 * @throws SQLException 
	 */
	public void mapList(HashMap<String, Object> a,List<HashMap<String, Object>> list,String sql,String userid,String usercond) throws AppException, SQLException{
		String importID="";
		String name="";
		String card="";
		if("t".equalsIgnoreCase(a.get("col_data_type")==null?"":a.get("col_data_type").toString())){
			String string = matcherRegularExpression(sql);
			if("".equals(string)){
				sql = sql + " and a."+a.get("col_code")+" like  '____-__-__'";
			}else{
				sql = sql.replace(string, string.trim().replace("-", "")) + " and a."+a.get("col_code")+" like  '____-__-__'";
			}
		}
		if("B".equals(a.get("table_code").toString().substring(0,1))){
			importID="B0111";
			name="B0101";
			card="B0111";
		}else{
			importID="A0000";
			name="A0101";
			card="A0184";
		}
		String str="b."+importID+"," + "'"+userid+"','"+a.get("table_code")+a.get("col_lection_name")+"' a0833,'"+a.get("col_code")+a.get("col_name")+"' a0834,b."+name+" a0835"
				+ ",b."+card+" a0836,a."+a.get("col_code")+" a0800,'"+entryStr(a.get("cdterrdscp"))+"' a0184";
		sql=sql.replace("*", str);
		sql=sql+" "+usercond;
//		System.out.println(sql+"000");
		String insertSql="insert into proofread(p_a0000b0111,p_userid,p_tablename,p_codename,p_hingename,p_hingevalue,p_codevalue,p_texterror) "+sql;
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		try{
			stmt.executeQuery(insertSql);
		}catch(Exception e){
			System.out.println("执行单项校核时出错2："+insertSql);
		}finally{
			stmt.close();
		}
	}
	
	/**
	 * 对grid的list进行赋值
	 * @param list
	 * @param sql
	 * @param condition
	 * @throws AppException
	 * @throws SQLException 
	 */
	public void addList (List<HashMap<String, Object>> list,HashMap<String, Object> a,String condition,String yesmst,String userid,String usercond) throws AppException, SQLException{
		String importID="";
		String cortable="";
		String name="";
		String card="";
		if("B".equals(a.get("table_code").toString().substring(0,1))){
			importID="B0111";
			cortable="B01";
			name="B0101";
			card="B0111";
		}else{
			importID="A0000";
			cortable="A01";
			name="A0101";
			card="A0184";
		}
		String str="";
		if("t".equalsIgnoreCase(a.get("col_data_type")==null?"":a.get("col_data_type").toString())){
			str = " and a."+a.get("col_code")+" like  '____-__-__'";
		}
		String sql="select "
				+ "b."+importID+"," + "'"+userid+"','"+a.get("table_code")+a.get("col_lection_name")+"' a0833,'"+a.get("col_code")+a.get("col_name")+"' a0834,b."+name+" a0835"
				+ ",b."+card+" a0836,a."+a.get("col_code")+" a0800,'"+entryStr(a.get("cdterrdscp"))+"' a0184"
				+ "  from "+a.get("table_code")+" a,"+cortable+" b where  a."+importID+"=b."+importID+" and a."+a.get("col_code")+" "+condition+" "+a.get("cdtval1")+" "+str
						+ " "+yesmst+" "+usercond;
		String string = matcherRegularExpression(sql);
		if("".equals(string)){
			sql = sql;
		}else{
			sql = sql.replace(string, string.trim().replace("-", ""));
		}
//		System.out.println(sql+"111");
		String insertSql="insert into proofread(p_a0000b0111,p_userid,p_tablename,p_codename,p_hingename,p_hingevalue,p_codevalue,p_texterror) "+sql;
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		try{
			stmt.executeQuery(insertSql);
		}catch(Exception e){
			System.out.println("执行单项校核时出错3："+insertSql);
		}finally{
			stmt.close();
		}
//		List<HashMap<String, Object>> listResult=cqbs.getListBySQL(sql);
//		list.addAll(listResult);
		
	}
	
	/**
	 * 对grid的list进行赋值（位数）
	 * @param list
	 * @param sql
	 * @param condition
	 * @throws AppException
	 * @throws SQLException 
	 */
	public void addLenList (List<HashMap<String, Object>> list,HashMap<String, Object> a,String condition,String yesmst,String userid,String usercond) throws AppException, SQLException{
		String importID="";
		String cortable="";
		String name="";
		String card="";
		if("B".equals(a.get("table_code").toString().substring(0,1))){
			importID="B0111";
			cortable="B01";
			name="B0101";
			card="B0111";
		}else{
			importID="A0000";
			cortable="A01";
			name="A0101";
			card="A0184";
		}
		String str="";
		if("t".equalsIgnoreCase(a.get("col_data_type")==null?"":a.get("col_data_type").toString())){
			str = " and a."+a.get("col_code")+" like  '____-__-__'";
		}
		String sql="select "
				+ "b."+importID+"," + "'"+userid+"','"+a.get("table_code")+a.get("col_lection_name")+"' a0833,'"+a.get("col_code")+a.get("col_name")+"' a0834,b."+name+" a0835"
				+ ",b."+card+" a0836,a."+a.get("col_code")+" a0800,'"+entryStr(a.get("cdterrdscp"))+"' a0184"
				+ " from "+a.get("table_code")+" a,"+cortable+" b  where  a."+importID+"=b."+importID+" and Length(a."+a.get("col_code")+") "+condition+" "+a.get("cdtval1")+" "+str
					+ " "+yesmst+" "+usercond;
		String string = matcherRegularExpression(sql);
		if("".equals(string)){
			sql = sql;
		}else{
			sql = sql.replace(string, string.trim().replace("-", ""));
		}
//		System.out.println(sql+"222");
		String insertSql="insert into proofread(p_a0000b0111,p_userid,p_tablename,p_codename,p_hingename,p_hingevalue,p_codevalue,p_texterror) "+sql;
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		try{
			stmt.executeQuery(insertSql);
		}catch(Exception e){
			System.out.println("执行单项校核时出错4："+insertSql);
		}finally{
			stmt.close();
		}
	}
	
	
	
	/**
	 * 为null转换为空字符串
	 * @param obj
	 * @return
	 */
	public String entryStr(Object obj){
		String result="";
		if(obj!=null){
			result=obj.toString();
		}
		return result;
	}
	/* 青岛项目需求 
	*//**
	 * 双击错误详细信息，跳转到任免表
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 *//*
	@PageEvent("editgrids.rowdbclick")
	@GridDataRange(GridData.allrow)
	@AutoNoMask
	public int dbClickRowErrorDetailGrid2() throws RadowException, AppException{
		Grid grid = (Grid)this.getPageElement("editgrids");
		int cueRowIndex = grid.getCueRowIndex();
		List<HashMap<String,Object>> gridList = grid.getValueList();
		HashMap<String,Object> map = gridList.get(cueRowIndex);
		String a0000=map.get("p_a0000b0111").toString();
		if(StringUtil.isEmpty(a0000)){
			throw new AppException("无法获取错误主体的类型！");
		}
		HashMap<String,Object> map_x =  null;
		if (".".equals(a0000.substring(3,4))) {//视为机构信息
			String groupid="";
			for(int i=0;i<gridList.size();i++){
				map_x = gridList.get(i);
				if (".".equals(map_x.get("p_a0000b0111").toString().substring(3,4))) {
					groupid+=map_x.get("p_a0000b0111")+",";
				}
			}
			if(!StringUtil.isEmpty(groupid)){
				groupid=groupid.substring(0,groupid.length()-1);
			}
			String pardata=a0000+","+groupid;
			request.getSession().setAttribute("unitidDbclAlter", pardata);
			this.getExecuteSG().addExecuteCode("$h.openPageModeWin('updateOrgWin','pages.sysorg.org.UpdateSysOrg','机构信息修改页面',860,510,'','"+request.getContextPath()+"');");
		} else {//视为人员信息
//			this.getExecuteSG().addExecuteCode("$h.openPageModeWin('RmbkzWin','pages.cadremgn.infmtionentry.custom.Rmbkz', '任免表', 1360, 620,'"+a0000+"','"+request.getContextPath()+"');");
//			this.getExecuteSG().addExecuteCode("$h.openWin('RmbkzWin','pages.cadremgn.infmtionentry.custom.Rmbkz', '任免表', 1360, 620,'"+a0000+"','"+request.getContextPath()+"');");
			//this.getExecuteSG().addExecuteCode("$h.openPageModeWin('RmbkzWin','pages.cadremgn.infmtionentry.custom.Rmbkz', '任免表', 1360, 665,'editgrids','"+request.getContextPath()+"');");
		     //this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/rmb.jsp?a0000="+a0000+"','人员信息修改',851,630,null,"
			//		+ "{a0000:'"+a0000+"',gridName:'errorDetailGrid2',maximizable:false,resizable:false,draggable:false},true);");
			
			
			this.request.getSession().setAttribute("a0000", a0000);
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	 青岛项目需求 */
	
	
	/**
	 * 双击错误详细信息，跳转到任免表
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("editgrids.rowdbclick")
	@GridDataRange(GridData.allrow)
	@AutoNoMask
	public int dbClickRowErrorDetailGrid2() throws RadowException, AppException{
		Grid grid = (Grid)this.getPageElement("editgrids");
		int cueRowIndex = grid.getCueRowIndex();
		List<HashMap<String,Object>> gridList = grid.getValueList();
		HashMap<String,Object> map = gridList.get(cueRowIndex);
		String a0000=map.get("p_a0000b0111").toString();
		if(StringUtil.isEmpty(a0000)){
			throw new AppException("无法获取错误主体的类型！");
		}
		this.request.getSession().setAttribute("personIdSet", null);
		this.request.getSession().setAttribute("vsql", null);
		/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,630,null,"
				+ "{a0000:'"+a0000+"',gridName:'editgrids',maximizable:false,resizable:false,draggable:false},true);");*/
		this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
		 return EventRtnType.NORMAL_SUCCESS;
	}
	 
}