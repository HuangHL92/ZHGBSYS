package com.insigma.siis.local.pagemodel.statisticalanalysis.simpleanalysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import net.sf.json.JSONArray;

import org.hibernate.Query;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.CodeManager;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.CodeTableCol;
import com.insigma.siis.local.business.entity.Customquery;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;

public class GeneralStatisticsPageModel extends PageModel {
	private CustomQueryBS ctcBs=new CustomQueryBS();
	public static List<Map<String, Object>> query_condition = new ArrayList<Map<String, Object>>();
	private static String ID = null;

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		query_condition.clear();
  		// String groupid = this.getPageElement("subWinIdBussessId").getValue();
		// 初始化，设定信息集可选项 mengl 20160629
		Combo collection = (Combo) this.createPageElement("collection",
				ElementType.SELECT, false);
		Map<String, String> map = RuleSqlListBS.getAllCodeTablesMap2();
		collection.setValueListForSelect(map);
		// 如果有父窗体有传递可定条件ID则加载相关条件信息到网格
		String queryId = this.getPageElement("subWinIdBussessId2").getValue().replace("|", "'");
		if (queryId.contains("@")) {
			HBSession sess = HBUtil.getHBSession();
			String groupid = queryId.split("@")[0];
			String SQ001 = queryId.split("@")[1];
			String sql = "select QC001,QC002,QC003,QC004 from query_condition where SQ001 = '"+SQ001+"'";
			List<Object[]> list = sess.createSQLQuery(sql).list();
			if(DBUtil.getDBType()==DBType.ORACLE){
				for(int i=0;i<list.size();i++){
					Map<String, Object> um = new HashMap<String, Object>();
					Object[] a=list.get(i);
					String QC001 = a[0].toString();
					String QC002 = a[1].toString();
					String QC003 = a[2].toString().replace("|", "'");
					Clob clob = (Clob)a[3];
					String QC004 = "";
					try {
						QC004 = ClobToString(clob);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					um.put("uuid", QC001);
					um.put("Queryname", QC002);
					um.put("Querysql", QC003);
					um.put("Gridstring", QC004);
					this.getExecuteSG().addExecuteCode("CreateLi('"+QC002+"','"+QC001+"');");
					query_condition.add(um);
				}
			}else{
				for(int i=0;i<list.size();i++){
					Map<String, Object> um = new HashMap<String, Object>();
					Object[] a=list.get(i);
					String QC001 = a[0].toString();
					String QC002 = a[1].toString();
					String QC003 = a[2].toString().replace("|", "'");
					String QC004 = a[3].toString();
					um.put("uuid", QC001);
					um.put("Queryname", QC002);
					um.put("Querysql", QC003);
					um.put("Gridstring", QC004);
					this.getExecuteSG().addExecuteCode("CreateLi('"+QC002+"','"+QC001+"');");
					query_condition.add(um);
				}
			}
		}
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String ClobToString(Clob clob) throws SQLException, IOException {

		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
		sb.append(s);
		s = br.readLine();
		}
		reString = sb.toString();
		return reString;
		}

	
	/**
	 * 设定选中行号到页面上
	 * @author mengl
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("grid.rowclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@NoRequiredValidate
	public int setCueRow() throws RadowException, AppException{
		int cueRowIndex = this.getPageElement("grid").getCueRowIndex();
		this.getPageElement("cueRowIndex").setValue(cueRowIndex+"");
		PageElement pe = this.getPageElement("grid");
		PageElement colName = this.getPageElement("colName");
		
		
		//信息集
		this.getPageElement("collection").setValue(pe.getValue("tableName").toString());
		
		//信息集onchange
		collectionChange();
		
		//信息项
		colName.setValue((String)(pe.getValue("colNamesValue")));
		
		//信息项onchange
		colNameChane();
		
		
		this.getPageElement("opeartor").setValue((String)(pe.getValue("opeartors")));
		this.getPageElement("colValue").setValue((String)(pe.getValue("colValues")));
		this.getPageElement("logicSymbol").setValue((String)(pe.getValue("logicSymbols")));
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 修改
	 * @return
	 * @author mengl
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("editcond.onclick")
	@AutoNoMask
	public int modify() throws RadowException, AppException{
		Grid pe = (Grid)this.getPageElement("grid");//获取grid对象
		String cueRowIdexStr = this.getPageElement("cueRowIndex").getValue();
		if(StringUtil.isEmpty(cueRowIdexStr)){
			throw new AppException("请先选中一行修改！");
		}
		int cueRowIndex = Integer.valueOf(cueRowIdexStr);
		//获取选中行中各列值
		String collection = this.getPageElement("collection").getValue();
		String colNameValue = this.getPageElement("colName").getValue();
		String opeartor = this.getPageElement("opeartor").getValue();
		String colValue = this.getPageElement("colValue").getValue();
		String logicSymbol = this.getPageElement("logicSymbol").getValue();
		/*String colName = ctcBs.getCtc(colNameValue,ctcBs.ctcList).getColName();
		String tableName=ctcBs.getCtc(colNameValue,ctcBs.ctcList).getTableCode();
		String colValueView=ctcBs.getAaa103(colNameValue, colValue,ctcBs.ctcList);*/
		CodeTableCol codeTableCol = RuleSqlListBS.getCodeTableCol(collection, colNameValue);
		String colName = codeTableCol.getColName();
//		String tableName=RuleSqlListBS.getTableName(collection);  //不在保存汉字，保存代码
		String colValueView = CodeManager.getValueByCode(!StringUtil.isEmpty(codeTableCol.getCodeType())?codeTableCol.getCodeType():colNameValue,colValue);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", collection);  //不再保存汉字，保存代码
		map.put("colNames", colName);
		map.put("colNamesValue", colNameValue);
		map.put("opeartors", opeartor);
		map.put("colValues", colValue);
		if(colValueView!=null){
		    map.put("colValuesView", colValueView);
		}else{
		    map.put("colValuesView", colValue);
		}
		map.put("logicSymbols", logicSymbol);
		
		pe.updateRowData(cueRowIndex, map);//更新该行数据
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**对选中行提升一行
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("upRow.onclick")
	@AutoNoMask
	@NoRequiredValidate
	public int upRow() throws RadowException, AppException{
		Grid pe = (Grid) this.getPageElement("grid");
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("请先选中一行！");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==0){
				throw new AppException("已经是第一行，不能上移！");
			}
		}
		List<HashMap<String, Object>> list = pe.getValueList();
		List<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
		for(int i =0;i<list.size();i++){
			if(i==(cueRowIndex-1)){
				newList.add(list.get(cueRowIndex));
			}else if(i==cueRowIndex){
				newList.add(list.get(cueRowIndex-1));
			}else{
				newList.add(list.get(i));
			}
		}
		pe.setValueList(newList);
		pe.selectRow(cueRowIndex-1);
		this.getPageElement("cueRowIndex").setValue(cueRowIndex-1+"");
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**对选中行降低一行
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("downRow.onclick")
	@AutoNoMask
	@NoRequiredValidate
	public int downRow() throws RadowException, AppException{
		
		Grid pe = (Grid) this.getPageElement("grid");
		List<HashMap<String, Object>> list = pe.getValueList();
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("请先选中一行！");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==(list.size()-1)){
				throw new AppException("已经是最后行，不能下移！");
			}
		}
		
		List<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
		for(int i =0;i<list.size();i++){
			if(i==(cueRowIndex+1)){
				newList.add(list.get(cueRowIndex));
			}else if(i==cueRowIndex){
				newList.add(list.get(cueRowIndex+1));
			}else{
				newList.add(list.get(i));
			}
		}
		pe.setValueList(newList);
		pe.selectRow(cueRowIndex+1);
		this.getPageElement("cueRowIndex").setValue(cueRowIndex+1+"");
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 当选择信息集时，触发查询指标项
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("collection.onchange")
	@NoRequiredValidate
	public int collectionChange() throws RadowException, AppException {
		//根据选择的信息集加载对应的查询条件数据
		PageElement pe = this.getPageElement("colName");
		String code = this.getPageElement("collection").getValue();
		/*TreeMap<String, String> treeMap=ctcBs.getCtcListByCollectionCode(code,ctcBs.ctcList);
		( (Combo) this.getPageElement("colName")).setValueListForSelect(treeMap);*/
		
		TreeMap<String, String> treeMap = null;
		if(!"A01".equalsIgnoreCase(code)){
			treeMap = RuleSqlListBS.getVru005byVru004_tj(code,false,"isZbx='1' and zbxTj='1' ");//仅查询指标项，且忽略vsl006是否配置 mengl 20160629
		}else{
			treeMap = RuleSqlListBS.getVru005byVru004_tj(code,false,"zbxTj='1' and isZbx='1' or id.columnName = 'AGE' or id.columnName ='A0148' ");//仅查询指标项，且忽略vsl006是否配置 mengl 20160629
		}
		
		( (Combo) this.getPageElement("colName")).setValueListForSelect(treeMap);
		
		pe.setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("colName.onchange")
	@NoRequiredValidate
	public int colNameChane() throws RadowException, AppException {
		//根据所选的查询条件加载对应的值数据
		Combo pe = (Combo)this.getPageElement("colValue");
		String collection = this.getPageElement("collection").getValue();
		String colNameValue = this.getPageElement("colName").getValue();
		
		if(StringUtil.isEmpty(colNameValue)){
			this.getPageElement("collection").setValue("");
			this.getPageElement("colName").setValue("");
			this.setMainMessage("请先选择信息集！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		
		// 设定可选值
		// mengl 20160629
		CodeTableCol cr = RuleSqlListBS.getCodeTableCol(collection, colNameValue);
		Map<String,String> map = new TreeMap<String,String>();
		if(cr!=null && !StringUtil.isEmpty(cr.getCodeType())){
			map = RuleSqlListBS.getAllMapByCodeType(cr.getCodeType().toUpperCase());
		}else if(!StringUtil.isEmpty(colNameValue)){
			map = RuleSqlListBS.getAllMapByCodeType(colNameValue.toUpperCase());
		}
		pe.setValueListForSelect(map);
		
		//设定运算符
		 Combo opeartorCombo = (Combo)this.getPageElement("opeartor");
		 Map<String,String> mapOperator = RuleSqlListBS.getCodeValuesMapByCodeType("OPERATOR");
		 //可选代码为空，设定操作符，否则去掉
		if(map.isEmpty()){
			//当判断代码值为空的时候，如果是number形式的字段，则剔除相应的运算符
			if(cr.getColDataTypeShould().equals("NUMBER")){
				mapOperator.remove("like {v%}");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("like {%v}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("like {%v%}");
				mapOperator.remove("not like {%v%}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
			//如果是DATE形式的字段，提出不适合date类型使用的运算符
			}else if (cr.getColDataTypeShould().equals("DATE")){
				mapOperator.remove("like {v%}");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("like {%v}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("like {%v%}");
				mapOperator.remove("not like {%v%}");
			//如果是VARCHAR2形式的字段，提出不适合VARCHAR2类型使用的运算符
			}else if (cr.getColDataTypeShould().equals("VARCHAR2")){
				mapOperator.remove(">{v}");
				mapOperator.remove(">={v}");
				mapOperator.remove("<{v}");
				mapOperator.remove("<={v}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
			}else{
				mapOperator.remove(">{v}");
				mapOperator.remove(">={v}");
				mapOperator.remove("<{v}");
				mapOperator.remove("<={v}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
			}
			
		}else{
			mapOperator.remove(">{v}");
			mapOperator.remove(">={v}");
			mapOperator.remove("<{v}");
			mapOperator.remove("<={v}");
			mapOperator.remove("like {v%}");
			mapOperator.remove("not like {v%}");
			mapOperator.remove("like {%v}");
			mapOperator.remove("not like {%v}");
			mapOperator.remove("like {%v%}");
			mapOperator.remove("not like {%v%}");
			mapOperator.remove("!= trunc(sysdate + {v})");
			mapOperator.remove("< trunc(sysdate + {v})");
			mapOperator.remove("<= trunc(sysdate + {v})");
			mapOperator.remove("= trunc(sysdate + {v})");
			mapOperator.remove("> trunc(sysdate + {v}) ");
			mapOperator.remove(">= trunc(sysdate + {v})");
		}
		if(cr.getColCode().equals("AGE")){
			mapOperator.remove("!={v}");
		}
		opeartorCombo.setValueListForSelect(mapOperator);
		pe.setValue("");
		opeartorCombo.setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("closeWin.onclick")
	@NoRequiredValidate
	public int closeWin() {
		this.closeCueWindow("win1");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * @$comment 添加统计条件
	 * @param 
	 * @return 
	 * @throws 
	 * @author wuh
	 */
	//@SuppressWarnings({ "static-access", "unchecked" })
	@PageEvent("btnSave.onclick")
	@NoRequiredValidate
	@Transaction
	public int btnSaveOnclick() throws RadowException, AppException {
		PageElement pe = this.getPageElement("grid");
		String queryName=this.getPageElement("conditionName").getValue();
		queryName = StringFilter(queryName);//过滤特殊字符串
		List<HashMap<String, Object>> list= pe.getValueList();
		String querySql=ctcBs.createSQLToTj(list);
		String data=JSONArray.fromObject(list).toString();
		String uuid = UUID.randomUUID().toString();
		if(queryName==null||"".equals(queryName)){
			throw new AppException("查询条件名称不能为空！");
		}
		for(int i=0 ; i < query_condition.size() ; i++){
			Map<String, Object> map = query_condition.get(i);
			String Queryname = (String)map.get("Queryname");
			String oldUuid = (String)map.get("uuid");
			if(Queryname.equals(queryName)){
				this.getExecuteSG().addExecuteCode("replace('"+oldUuid+"');");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		Map<String, Object> um = new HashMap<String, Object>();
		um.put("Queryname", queryName);
		um.put("Querysql", querySql);
		um.put("Gridstring", data);
		um.put("uuid", uuid);
		query_condition.add(um);
		
		this.getPageElement("collection").setValue("");
		this.getPageElement("colName").setValue("");
		this.getPageElement("opeartor").setValue("");
		this.getPageElement("colValue").setValue("");
		this.getPageElement("aa").setValue("");
		this.getPageElement("conditionName").setValue("");
		this.getPageElement("grid").reload();

		this.getExecuteSG().addExecuteCode("CreateLi('"+queryName+"','"+uuid+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("replaceEvent")
	public int saveEvent(String oldUuid) throws RadowException, AppException {
		PageElement pe = this.getPageElement("grid");
		String queryName=this.getPageElement("conditionName").getValue();
		queryName = StringFilter(queryName);//过滤特殊字符串
		List<HashMap<String, Object>> list= pe.getValueList();
		String querySql=ctcBs.createSQLToTj(list);
		String data=JSONArray.fromObject(list).toString();
		Map<String, Object> um = new HashMap<String, Object>();
		um.put("Queryname", queryName);
		um.put("Querysql", querySql);
		um.put("Gridstring", data);
		um.put("uuid", oldUuid);
		if(oldUuid!=null){
			for(int i=0 ; i < query_condition.size() ; i++){
				Map<String, Object> map = query_condition.get(i);
				String uuid = (String)map.get("uuid");
				if(uuid.equals(oldUuid)){
					query_condition.set(i,um);
				}
			}
		}
		this.getPageElement("collection").setValue("");
		this.getPageElement("colName").setValue("");
		this.getPageElement("opeartor").setValue("");
		this.getPageElement("colValue").setValue("");
		this.getPageElement("aa").setValue("");
		this.getPageElement("conditionName").setValue("");
		this.getPageElement("grid").reload();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * @$comment 根据生成查询条件查询
	 * @param 
	 * @return 
	 * @throws 
	 * @author lixy
	 */
	
	@SuppressWarnings("static-access")
	@PageEvent("doQuery.onclick")
	@NoRequiredValidate
	@Transaction
	public int doQuery() throws RadowException, AppException {
		PageElement pe = this.getPageElement("grid");
		String queryName="上次查询";//保存为上次查询
		List<HashMap<String, Object>> list= pe.getValueList();
		String querySql=ctcBs.createSQL(list);
		String queryDes=ctcBs.createSQLView(list);
		String loginName=SysUtil.getCacheCurrentUser() .getLoginname();
//		String queryId=this.getRadow_parent_data();
		ctcBs.delLastTimeCq();//删除上次查询条件
		String data=JSONArray.fromObject(list).toString();
		ctcBs.saveOrUodateCq("", queryName, querySql, queryDes, loginName,data);
		this.createPageElement("sql",ElementType.HIDDEN, true).setValue(querySql);//设置父页面sql值为本次生成sql
		this.request.getSession().setAttribute("groupBy", "1");
		this.closeCueWindow("win1");
		this.getExecuteSG().addExecuteCode("parent.radow.doEvent('gridcq');");	//刷新父窗体固定条件网格
		this.getExecuteSG().addExecuteCode("parent.radow.doEvent('peopleInfoGrid.dogridquery');");//父窗体执行查询			
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 新增条件到列表
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@SuppressWarnings("static-access")
	@PageEvent("savecond")
	@GridDataRange
	public int savecondonClick() throws RadowException, AppException{
		
        
		PageElement pe = this.getPageElement("grid");
		String collectionValue = this.getPageElement("collection").getValue();
		String colNameValue = this.getPageElement("colName").getValue();
		String opeartor = this.getPageElement("opeartor").getValue();
		String colValue = this.getPageElement("colValue").getValue();
		colValue = StringFilter(colValue);//过滤特殊字符串
		if(StringUtil.isEmpty(collectionValue)){
			this.setMainMessage("信息集为空！");
			return EventRtnType.NORMAL_SUCCESS; 
		}
		if(StringUtil.isEmpty(colNameValue)){
			this.setMainMessage("信息项为空！");
			return EventRtnType.NORMAL_SUCCESS; 
		}
		if(StringUtil.isEmpty(opeartor)){
			this.setMainMessage("运算符为空！");
			return EventRtnType.NORMAL_SUCCESS; 
		}
		if(StringUtil.isEmpty(colValue)){
			this.setMainMessage("取值为空！");
			return EventRtnType.NORMAL_SUCCESS; 
		}
		//当查询的是身份证时，将查询值中的小写字母转换为大写字母
		if("A0184".equals(colNameValue.toUpperCase())){
			colValue = colValue.toUpperCase();
		}
		String logicSymbol = this.getPageElement("logicSymbol").getValue();
		String colName = ctcBs.getCtc(colNameValue,ctcBs.ctcList).getColName();
		String tableName=ctcBs.getCtc(colNameValue,ctcBs.ctcList).getTableCode();
		String colValueView=ctcBs.getAaa103(colNameValue, colValue,ctcBs.ctcList);
		
		List<HashMap<String, Object>> list = pe.getValueList();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("colNames", colName);
		map.put("colNamesValue", colNameValue);
		map.put("opeartors", opeartor);
		map.put("colValues", colValue);
		if(colValueView!=null)
		    map.put("colValuesView", colValueView);
		else
		    map.put("colValuesView", colValue);
		map.put("logicSymbols", logicSymbol);
		list.add(map);
	
		pe.setValueList(list);
		this.getPageElement("cueRowIndex").setValue("");
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	/**
	 * 当grid中的关系列变更时触发
	 * @author caiy
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("grid.afteredit")
	@GridDataRange(GridData.cuerow)
	@NoRequiredValidate
	public int gridonchange() throws RadowException, AppException{
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 统计
	 * @author wuh
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("showPic.onclick")
	public int showPic() throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		String SQ002 = null;
		String sql = this.getPageElement("subWinIdBussessId2").getValue().replace("|", "'");
		if(query_condition.size()==0){
			this.setMainMessage("请添加统计条件");
			return EventRtnType.NORMAL_SUCCESS;
		}
        if(sql.contains("@")){
			String groupid = sql.split("@")[0];
			SQ002 = sql.split("@")[2];
			String num = "";//将统计项的数据计算出来
			for(int i=0;i<query_condition.size();i++){
				String Querysql = (String) query_condition.get(i).get("Querysql");//数据库中存的是“|”，记得转换为“‘”
				String tj = Querysql+groupid;
				String number = "";
				if(DBUtil.getDBType()==DBType.ORACLE){
					number = sess.createSQLQuery("select count(1) from ( "+tj+" )").uniqueResult().toString();
				}else{
					number = sess.createSQLQuery("select count(1) from ( "+tj+" ) as t").uniqueResult().toString();
				}
				num = num+number+",";//将算出的数字拼接传递
			}
			if(SQ002 != null && SQ002.length() > 0){
				String pass = num.substring(0, num.length()-1)+"@"+"&"+SQ002;//拼接@符号区分常用统计和通用统计
				this.getExecuteSG().addExecuteCode("tjfx2('"+pass+"')");
			}else{
				String pass = num.substring(0, num.length()-1)+"@";//拼接@符号区分常用统计和通用统计
				this.getExecuteSG().addExecuteCode("tjfx('"+pass+"')");
			}
		}else{
			String num = "";//将统计项的数据计算出来
			for(int i=0;i<query_condition.size();i++){
				String Querysql = (String) query_condition.get(i).get("Querysql");
				String tj = Querysql+sql;
				String number = "";
				if(DBUtil.getDBType()==DBType.ORACLE){
					number = sess.createSQLQuery("select count(1) from ( "+tj+" )").uniqueResult().toString();
				}else{
					number = sess.createSQLQuery("select count(1) from ( "+tj+" ) as t").uniqueResult().toString();
				}
				num = num+number+",";//将算出的数字拼接传递
			}
			if(SQ002 != null && SQ002.length() > 0){
				String pass = num.substring(0, num.length()-1)+"@"+"&"+SQ002;//拼接@符号区分常用统计和通用统计
				this.getExecuteSG().addExecuteCode("tjfx2('"+pass+"')");
			}else{
				String pass = num.substring(0, num.length()-1)+"@";//拼接@符号区分常用统计和通用统计
				this.getExecuteSG().addExecuteCode("tjfx('"+pass+"')");
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 删除页面统计条件
	 * @author wuh
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("delright")
	public int delright(String value) throws RadowException, AppException{
		String id = value;
		for(int i=0 ; i < query_condition.size() ; i++){
			Map<String, Object> map = query_condition.get(i);
			String uuid = (String)map.get("uuid");
			if(uuid.equals(id)){
				query_condition.remove(i);
			}
		}
		this.getPageElement("aa").setValue("");
		this.getPageElement("conditionName").setValue("");
		this.getPageElement("uuid").setValue("");
		this.getPageElement("grid").reload();
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * 清空按钮
	 * @author wuh
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("delAll.onclick")
	public int delAll() throws RadowException, AppException{
		this.getPageElement("collection").setValue("");
		this.getPageElement("colName").setValue("");
		this.getPageElement("opeartor").setValue("");
		this.getPageElement("colValue").setValue("");
		this.getPageElement("aa").setValue("");
		this.getPageElement("conditionName").setValue("");
		this.getPageElement("grid").reload();
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * 页面查询统计条件
	 * @author wuh
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("Print")
	public int print(String pass) throws RadowException, AppException{
		String id = pass;
		this.getPageElement("uuid").setValue(id);
		this.getPageElement("collection").setValue("");
		this.getPageElement("colName").setValue("");
		this.getPageElement("opeartor").setValue("");
		this.getPageElement("colValue").setValue("");
		for(int i=0 ; i < query_condition.size() ; i++){
			Map<String, Object> map = query_condition.get(i);
			String uuid = (String)map.get("uuid");
			if(uuid.equals(id)){
				String Queryname = (String)map.get("Queryname");
				String Gridstring = (String)map.get("Gridstring");
				this.getPageElement("grid").setValue(Gridstring);
	    	    this.getPageElement("conditionName").setValue(Queryname);
			}
		}
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	/**
	 * 组装打印sql
	 * @author caiy
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	//@PageEvent("grid.afteredit")
	@PageEvent("PrintSQL")
	@GridDataRange(GridData.cuerow)
	@NoRequiredValidate
	public int printSql() throws RadowException, AppException{
		String sqlForm = "";
		List<HashMap<String, Object>> gridList = this.getPageElement("grid").getValueList();
		for(int i=0 ; i < gridList.size() ; i++){
			HashMap<String, Object> map = gridList.get(i);
			sqlForm = sqlForm+map.get("leftBracket");
			sqlForm = sqlForm+map.get("colNames");
			sqlForm = sqlForm+" "+map.get("opeartors");
			//sqlForm = sqlForm+map.get("colValuesView");
			sqlForm = sqlForm+map.get("rightBracket");
			sqlForm = sqlForm+map.get("logicSymbols");
			String value = map.get("colValuesView").toString();
			sqlForm = sqlForm.replace("trunc(sysdate + {v}) ", "当前时间+"+value+"天").replace("not like {%v%}", "不包含'"+value+"'")
					.replace("not like {%v}", "不以'"+value+"'结尾").replace("not like {v%}", "不以'"+value+"'开头")
					.replace("{v}", value).replace("or", "或者").replace("and", "并且").replace("like {%v%}", "包含("+value+")")
					.replace("like {%v}", "以("+value+")结尾").replace("like {v%}", "以("+value+")开头").replace("并且 1=1", "");

		}
		this.getPageElement("aa").setValue(sqlForm);
		//this.getExecuteSG().addExecuteCode("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//点击保存按钮
	@PageEvent("save.onclick")
	@Transaction
	public int ewtj() throws RadowException{
		ID = UUID.randomUUID().toString();
		if(query_condition.size()==0){
			this.setMainMessage("请添加统计条件!");
			return EventRtnType.NORMAL_SUCCESS; 
		}

		String queryId = this.getPageElement("subWinIdBussessId2").getValue().replace("|", "'");
		if(queryId.contains("@")){
			String SQ001 = queryId.split("@")[1];
			HBSession sess = HBUtil.getHBSession();
			Connection conn = sess.connection();
			sess.createSQLQuery("delete from query_condition where SQ001 = '"+SQ001+"'").executeUpdate();
			for(int i=0;i<query_condition.size();i++){
				String QC001 = query_condition.get(i).get("uuid").toString();
				String QC002 = query_condition.get(i).get("Queryname").toString();
				String QC003 = query_condition.get(i).get("Querysql").toString().replace("'", "|");
				String QC004 = query_condition.get(i).get("Gridstring").toString();
				String sql2 = "insert into query_condition(QC001,QC002,QC003,SQ001,QC004) values (?,?,?,?,?)";
				//sess.createSQLQuery(sql2).executeUpdate();
				try {
					PreparedStatement ps = conn.prepareStatement("insert into query_condition(QC001,QC002,QC003,SQ001,QC004) values (?,?,?,?,?)");
					ps.setString(1, QC001);
					ps.setString(2, QC002);
					ps.setString(3, QC003);
					ps.setString(4, SQ001);
					ps.setString(5, QC004);
					ps.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.setMainMessage("保存成功");
			this.closeCueWindowByYes("GeneralStatistics");
		}else{
			this.getExecuteSG().addExecuteCode("save('1@"+ID+"')");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//点击关闭按钮
	@PageEvent("close.onclick")
	public int close() throws RadowException{
		this.closeCueWindow("GeneralStatistics");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public void closeCueWindowByYes(String arg0) {
		// TODO Auto-generated method stub
		this.setShowMsg(true);
		this.addNextBackFunc(NextEventValue.YES, "parent.odin.ext.getCmp('"+arg0+"').close();");
	}
	
	@Override
	public void closeCueWindow(String arg0) {
		// TODO Auto-generated method stub
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('" + arg0 + "').close();");
	}

    // 过滤特殊字符  
    public   static   String StringFilter(String str) throws PatternSyntaxException {     
          // 只允许字母和数字       
          // String   regEx  =  "[^a-zA-Z0-9]";                     
          // 清除掉所有特殊字符  
          String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
          Pattern p = Pattern.compile(regEx);     
          Matcher m = p.matcher(str);     
          return m.replaceAll("").trim();     
    } 

	
}
