package com.insigma.siis.local.pagemodel.cadremgn.sysbuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.commform.hibernate.HUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Grpfld;
import com.insigma.siis.local.business.entity.Grphicd;
import com.insigma.siis.local.business.entity.Grpord;
import com.insigma.siis.local.business.entity.Grprcdsplmd;
import com.insigma.siis.local.business.entity.Qryuse;
import com.insigma.siis.local.pagemodel.cadremgn.util.JsonUtil;
import com.insigma.siis.local.pagemodel.cadremgn.util.SqlToMapUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryPageModel;
import com.insigma.siis.local.pagemodel.zj.Utils;

public class CreateDefinePageModel extends PageModel {
	/**
	 * 必须可编辑的按钮
	 */
	//选择条件
	public  String btnn1init[]={"btnn1","btnn2","btnn4"};//1.选择条件 2.并且 3.非
	//选择条件
	public  String btnn1[]={"btnn5","btnn6"};//1.并且 2.或者
	//（
	public  String btnn2[]={"btnn4","btnn1","btnn2"};//1.非 2.选择条件 3.（
	//）
	public  String btnn3[]={"btnn5","btnn6"};//1.并且 2.或者
	//非
	public  String btnn4[]={"btnn1","btnn2"};//1.选择条件 2.（
	//并且
	public  String btnn5[]={"btnn1","btnn2"};//1.选择条件  2.（
	//或者
	public  String btnn6[]={"btnn1","btnn2"};//1.选择条件 2.（
	public static HashMap<String, Object>  mapsyb=new HashMap<String, Object>();
	public HashMap<String , String[]> mapBtn=new HashMap<String, String[]>();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		this.getExecuteSG().addExecuteCode("init();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 初始化页面
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("initpage")
	public int initpage() throws RadowException, AppException{
		String ctci=this.getPageElement("ctci").getValue();
		if(ctci==null||ctci.trim().equals("")||ctci.trim().equals("null")){
			this.getExecuteSG().addExecuteCode("confirmAct('未检测到选中的组合字段信息!请选择组合字段信息!');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String showname=HBUtil.getValueFromTab("col_code||'  '||col_name showname", "code_table_col", "ctci='"+ctci+"'");
		this.getPageElement("showname").setValue(showname);//当前操作字段
		//加载已经定义的组合字段列表
		this.setNextEventName("contentListquery");
		
		//加载记录组合格式设置
		this.setNextEventName("loadRowRecordSet");
		
		initFldInfoSet( ctci);//初始化tab3 信息项字段属性设置 隐藏到页面
		String check2=HBUtil.getValueFromTab("slfcfg", "grprcdsplmd", "ctci='"+ctci+"' ");
		if("1".equals(check2)){
			this.getPageElement("check2").setValue(check2);//查询结果为sql函数表达式
		}else{
			this.createPageElement("button1", ElementType.BUTTON, false).setDisabled(true);//编辑表达式
		}
		loadTableInfoSelect( ctci);//初始化信息集选中table信息隐藏到页面
		//加载 2 3 4 5 tab页 listgrid数据
		this.setNextEventName("intloadttff");//
		
		//初始化tab4信息
		loadHighSelect();
		initButtunTab4();//初始化tab4组合条件按钮编辑属性
		initTab3Info(ctci);
		this.getExecuteSG().addExecuteCode("initTab4Button()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public int loadHighSelect() throws AppException{
		String sql="select funcexpress,expressdesc1 from expressfunc where functype='1' ";
		Map<String, String> map = SqlToMapUtil.HListTomap(sql, "funcexpress","expressdesc1");
		SqlToMapUtil.setValueListForSelect(this, "stringfuncid", map);
		sql="select funcexpress,expressdesc1 from expressfunc where functype='2' ";
		map = SqlToMapUtil.HListTomap(sql, "funcexpress","expressdesc1");
		SqlToMapUtil.setValueListForSelect(this, "datefuncid", map);
		sql="select funcexpress,expressdesc1 from expressfunc where functype='3' ";
		map = SqlToMapUtil.HListTomap(sql, "funcexpress","expressdesc1");
		SqlToMapUtil.setValueListForSelect(this, "numberfuncid", map);
		sql="select funcexpress,funcexpress||' '||expressdesc1 expressdesc1 from expressfunc where functype='4' ";
		map = SqlToMapUtil.HListTomap(sql, "funcexpress","expressdesc1");
		SqlToMapUtil.setValueListForSelect(this, "opratesymfuncid", map);
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 点击组合字段，加载历史信息
	 * @param ctci
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("loadAllData")
	public int loadAllData(String ctci) throws RadowException, AppException{
		cleanJGZBXInfo();//清空结果指标项页面信息
		clearValue();//清空条件信息项tab4页面信息
		clearRcdInfo();//清空加载记录组合格式设置
		loadComRcdInfo( ctci);//加载记录组合格式设置
		String result=loadCodeDesc(ctci);//字段生成规则描述
		this.getPageElement("area4").setValue(result);
		
		initFldInfoSet( ctci);//初始化tab3 信息项字段属性设置 隐藏到页面
		String check2=HBUtil.getValueFromTab("slfcfg", "grprcdsplmd", "ctci='"+ctci+"' ");
		if("1".equals(check2)){
			this.getPageElement("check2").setValue(check2);//查询结果为sql函数表达式
			this.createPageElement("button1", ElementType.BUTTON, false).setDisabled(false);//编辑表达式
		}else{
			this.getPageElement("check2").setValue("0");//查询结果为sql函数表达式
			this.createPageElement("button1", ElementType.BUTTON, false).setDisabled(true);//编辑表达式
		}
		loadTableInfoSelect( ctci);//初始化信息集选中table信息隐藏到页面
		
		//加载 2 3 4 5 tab页listgrid数据
		loadTabTTFF( ctci);
		
		//初始化tab4信息
		initButtunTab4();//初始化tab4组合条件按钮编辑属性
		initTab3Info(ctci);//初始化tab4信息到页面
		this.getExecuteSG().addExecuteCode("initTab4Button()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int clearRcdInfo() throws RadowException{
		//清空组合记录格式设置
		//从第？行
		String temp="";
		this.getPageElement("startrow").setValue(temp);
		//到第
		this.getPageElement("endrow").setValue(temp);
		//选择记录连接符
		this.getPageElement("connector").setValue(temp);
		//记录间分行居中排列
		this.getPageElement("centerbranch").setValue("0");
		//每行？个字符
		this.getPageElement("indentrow").setValue(temp);
		//每行？个字符位置
		this.getPageElement("indextnum").setValue(temp);
		//从首行开始缩进
		this.getPageElement("firstindex").setValue("0");
		this.getPageElement("area4").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 根据信息集加载所有指标项
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("loadFieldGridList")
	public int loadFieldGridList() throws RadowException, AppException{
//		String ctci="";
//		Grid gridContentList=(Grid)this.getPageElement("contentList");
//		List<HashMap<String, Object>> listContentList=gridContentList.getValueList();
//		for(int i=0;listContentList!=null&&i<listContentList.size();i++){
//			String checked=listContentList.get(i).get("change")+"";
//			if("true".equals(checked)){
//				ctci=listContentList.get(i).get("ctci")+"";
//			}
//		}
		Grid gridCode = (Grid)this.getPageElement("content2");
		String table_code="";
		List<HashMap<String, Object>> listCode=gridCode.getValueList();
		for(int i=0;i<listCode.size();i++){
			String checked=listCode.get(i).get("change")+"";
			if("true".equals(checked)){
				table_code=table_code+"'"+listCode.get(i).get("table_code")+"',";
			}
		}
		if(!"".equals(table_code)){
			table_code=table_code.substring(0, table_code.length()-1);
		}
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			
		}else{
			instr=" and col_code in (select t.col_code from COMPETENCE_USERTABLECOL t where t.userid in ('"+Utils.getRoleId(userid)+"')) ";
		}
		String sql="";
		if(!"".equals(table_code)){
			sql="select s.*, rownum ordernum from (select "//
					+ " ctci, "//	主键
					+" table_code||'.'||col_code||'  '||col_name showcolname,"
					+ " table_code, "//	信息表编码,用来关联CODE_TABLE
					+ " col_code, "//	指标项对应的表的对应列
					+ " col_name, "//	指标项对应的表的对应列的名称
					+ " code_type, "//	指标项
					+ " col_data_type_should, "//	应该列数据类型(实际数据类型从v_varify_vsl006视图中查询),新增信息项类型(0:文本,1:数字,2:日期)
					+ " col_data_type "//	列数据类型
				+ " from code_table_col "
				+ " where 1=1 "
				//+ " and isuse='1' "
				+ " and islook='1'"
				+ " and table_code in ("+table_code+") "
				+instr
						+ " order by col_code asc ) s"
				;
		}else{
			sql="select * from dual where 1=2 ";
		}
		CommQuery cq=new CommQuery();
		List<HashMap<String,Object>> list=cq.getListBySQL(sql);
		this.getPageElement("contentList2").setValueList(list);
		this.getPageElement("contentList3").setValueList(list);
		this.getPageElement("contentList5").setValueList(list);
		this.getPageElement("contentList6").setValueList(list);
		//清空排序tab5
		list=cq.getListBySQL("select * from dual where 1=2 ");
		this.getPageElement("contentList4").setValueList(list);
		//清空页面条件tab4
		this.setNextEventName("clearValue");
		this.getExecuteSG().addExecuteCode("cleanInfo();");
		copyToTableInfo( table_code);
		//清空查询页面信息项tab3
		cleanJGZBXInfo();
		this.getExecuteSG().addExecuteCode("cleanFldInfo();");
		
//		//初始化tab4信息
//		initButtunTab4();//初始化tab4组合条件按钮编辑属性
//		initTab3Info(ctci);
//		this.getExecuteSG().addExecuteCode("initTab4Button()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("copyToTableInfo")
	public int copyToTableInfo(String table_code) throws AppException{
		if(!"".equals(table_code)){
			String sql="select table_code,table_code||' '||table_name table_name from code_table "
					+ " where table_code in ("+table_code.toUpperCase()+") "
					+ " order by table_code asc ";
			Map<String, String>  map = SqlToMapUtil.HListTomap(sql, "table_code","table_name");
			SqlToMapUtil.setValueListForSelect(this, "infosetfuncid", map);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("tableOnChange")
	public int tableOnChange(String tableinfo) throws AppException{
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			
		}else{
			instr=" and col_code in (select t.col_code from COMPETENCE_USERTABLECOL t where t.userid in ('"+Utils.getRoleId(userid)+"')) ";
		}
		if(!"".equals(tableinfo)){
			String sql="select col_code,col_code||' '||col_name col_name "
					+ " from code_table_col "
					+ " where table_code='"+tableinfo.toUpperCase()+"' "
							+ instr
							+ " ";
			Map<String, String>  map = SqlToMapUtil.HListTomap(sql, "col_code","col_name");
			SqlToMapUtil.setValueListForSelect(this, "databasefuncid", map);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initTab4Button")
	public int initTab4Button(){
		this.createPageElement("editbtnm", ElementType.BUTTON, false).setDisabled(true);//）
		this.createPageElement("btnm3", ElementType.BUTTON, false).setDisabled(true);//）
		this.createPageElement("btnm4", ElementType.BUTTON, false).setDisabled(true);//）
		
		this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(false);
		return EventRtnType.NORMAL_SUCCESS;
	}
	public int initTab3Info(String ctci) throws RadowException, AppException{
		CommQuery cq=new CommQuery();
		//String txtareaarr="";//存储所有添加条件 用于显示条件
		String sql=""
				+ " select * from ("
				+ "select "
				+ " 1 tyle,"//
				+ " a.quid, "//	主键
				+ " a.qvid, "//	视图id or 组合字段 ctci
				+ " a.sort, "//	条件顺序
				+ " a.fldname, "//	字段名
				+ " a.fldcode, "//	代码
				+ " a.tblname, "//	表名
				+ " a.valuename1, "//	值一中文名
				+ " a.valuecode1, "//	值一代码
				+ " a.lable_type code_type, "//	字段类型 T时间C字符串(文本)N数字S下拉选
				+ " a.valuecode2, "//	值二代码
				+ " a.valuename2, "//	值二中文名
				+ " a.sign, "//	符号
				+ " b.code_name signname "
				+ " from qryuse a,(select * from code_value where code_type='OPERATOR') b where a.sign=b.code_value and a.qvid='"+ctci+"' "
						+ " "
						+ " union all "
						+ " select "
							+ " 2 tyle,"//
							+ " ghc001 quid, "//	主键
							+ " ctci qvid, "//	视图id or 组合字段 ctci
							+ " ordernum sort, "//	条件顺序
							+ " translation fldname, "//	字段名
							+ " condition fldcode, "//	代码
							+ " '1' tblname, "//	表名
							+ " '1' valuename1, "//	值一中文名
							+ " '1' valuecode1, "//	值一代码
							+ " '1' code_type, "//	字段类型 T时间C字符串(文本)N数字S下拉选
							+ " '1' valuecode2, "//	值二代码
							+ " '1' valuename2, "//	值二中文名
							+ " '1' sign, "//	符号
							+ " '1' signname "
							+ " from grphicd where ctci='"+ctci+"' "
									+ ")"
									+ " order by sort asc "
						+ "";
		List<HashMap<String, Object>> list=cq.getListBySQL(sql);
		
		String zhtj=HBUtil.getValueFromTab("condition", "grprcdsplmd", "ctci='"+ctci+"' ");
		this.getExecuteSG().addExecuteCode("cleanInfo();");
		this.getPageElement("conditionName9").setValue(zhtj);
		this.getPageElement("zhtj").setValue(zhtj);
		//conditionStr
		
		if(list!=null&&list.size()>0){
			String jsonstr=JSONArray.fromObject(list).toString();
			jsonstr=jsonstr.replace("\'", "$");
			this.getExecuteSG().addExecuteCode("initTab3Info('"+jsonstr+"');");
		}
//		for(int i=0;list!=null&&i<list.size();i++){
//			String code_type=(String)list.get(i).get("code_type");
//			String sign=(String)list.get(i).get("sign");
//			if(sign!=null&&sign.indexOf("between")!=-1){
//				String fldname=(String)list.get(i).get("fldname");
//				sign=HBUtil.getValueFromTab("code_name", "code_value", "code_type='OPERATOR' and code_value='"+sign+"' ");
//				String valuecode1=(String)list.get(i).get("valuecode1");
//				String valuecode2=(String)list.get(i).get("valuecode2");
//			}else{
//				
//			}
//		}
//		var txtareaarrCode=[];//存储所有添加条件 用于拼接sql条件
//		var arrall=[];//存储所有条件  
//		var arrselect=[];//存储选中的条件 保存到条件表中
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public void cleanJGZBXInfo() throws RadowException{
		this.getPageElement("check2").setValue("0");//合并同类项字段
		this.getPageElement("check4").setValue("0");//合并同类项字段
		this.getPageElement("check3").setValue("0");//代码字段转换为描述
		this.getPageElement("connsymbol").setValue("");//字段后的连接字符
		this.getPageElement("connsymbol_combo").setValue("");//字段后的连接字符
		this.getPageElement("select12").setValue("");//值为空时连接符处理方式
		this.getPageElement("select12_combo").setValue("");//值为空时连接符处理方式
		this.getPageElement("select13").setValue("");//字段为空的代替符
		this.getPageElement("select13_combo").setValue("");//字段为空的代替符
		this.getPageElement("select14").setValue("");//字段合计函数
		this.getPageElement("select14_combo").setValue("");//字段合计函数
		this.getPageElement("selectsame").setValue("");//同类项替代连接符
		this.getPageElement("selectsame_combo").setValue("");//同类项替代连接符
		this.getPageElement("select15").setValue("");//日期格式化
		this.getPageElement("select15_combo").setValue("");//日期格式化
	}
	
	public void loadTableInfoSelect(String ctci) throws AppException, RadowException{
		//初始化信息集选中信息
		CommQuery cq=new CommQuery();
		String sql=" select tblname from ("
				+ " select tblname from grpfld where ctci='"+ctci+"'"
				+ "  group by tblname "
				+ " union all  "
				+ " select tblname from qryuse where qvid='"+ctci+"' group by tblname"
						+ ") group by tblname  ";
		List<HashMap<String, Object>> list=cq.getListBySQL(sql);
		String tableInfo="";
		for(int i=0;list!=null&&i<list.size();i++){
			tableInfo=tableInfo+list.get(i).get("tblname")+",";
		}
		if(!"".equals(tableInfo)){
			tableInfo=tableInfo.substring(0, tableInfo.length()-1);
			this.getPageElement("tablesInfo").setValue(tableInfo);
		}
	}
	
	public void initFldInfoSet(String ctci) throws AppException, RadowException{
		String sql="select * from grpfld where ctci='"+ctci+"' ";
		CommQuery cq=new CommQuery();
		List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
		String setfld="";
		for(int i=0;list!=null&&i<list.size();i++){
			Object temp=list.get(i).get("connsymbol");
			if(temp==null)temp="";
			setfld=setfld+temp+"@";
			temp=list.get(i).get("handnull");
			if(temp==null)temp="";
			setfld=setfld+temp+"@";
			temp=list.get(i).get("fldnull");
			if(temp==null)temp="";
			setfld=setfld+temp+"@";
			temp=list.get(i).get("fldfunc");
			if(temp==null)temp="";
			setfld=setfld+temp+"@";
			temp=list.get(i).get("replacefld");
			if(temp==null)temp="";
			setfld=setfld+temp+"@";
			temp=list.get(i).get("datefmt");
			if(temp==null)temp="";
			setfld=setfld+temp+"@";
			temp=list.get(i).get("meregfld");
			if(temp==null)temp="";
			setfld=setfld+temp+"@";
			temp=list.get(i).get("codetocomm");
			if(temp==null)temp="";
			setfld=setfld+temp+"$";
		}
		if(!"".equals(setfld)){
			setfld=setfld.substring(0, setfld.length()-1);
		}
		this.getPageElement("setfld").setValue(setfld);
	}
	
	public void initButtunTab4() throws RadowException{
		//值二
		this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(true);
		this.getExecuteSG().addExecuteCode("document.getElementById('conditionName71_combotree').disabled=true; ");
		//this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(true);
		this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(true);
		//组合条件
		this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//）
		this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);//并且
		this.getExecuteSG().addExecuteCode("document.getElementById('conditionName6_combotree').disabled=true; ");
		//this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);//或者
	}
	
	//字段生成规则描述
	public String loadCodeDesc(String ctci) throws AppException, RadowException{
		String result="";
		CommQuery cq=new CommQuery();
		//结果指标项
		String sql="select s.*, rownum ordernum"
				+ " from (select "
						+" ctc.table_code||'.'||ctc.col_code||'('||b.table_name||'.'||ctc.col_name||')' tblcodesc"
					+ " from code_table_col ctc, grpfld gf,code_table b"
					+ " where ctc.table_code||ctc.col_code=gf.tblname||gf.fldname "
					+ " and gf.ctci='"+ctci+"' and ctc.table_code=b.table_code"
					+ " order by gf.ordernum1 asc) s ";
		List<HashMap<String, Object>> listfld=cq.getListBySQL(sql);
		if(listfld!=null&&listfld.size()>0){
			result=result+"选择结果字段：\n";
			for(int i=0;i<listfld.size();i++){
				result=result+listfld.get(i).get("tblcodesc").toString()+"\n";
			}
			//选择信息集
			sql="select distinct(a.tbldesc) from"
				+ "(select  distinct(ctc.table_code||'('||b.table_name||')')  tbldesc"
				+ " from code_table_col ctc, grpfld gf,code_table b "
				+ "where ctc.table_code||ctc.col_code=gf.tblname||gf.fldname "
						+ " and gf.ctci='"+ctci+"' and ctc.table_code=b.table_code"
						+ " order by gf.ordernum1 asc) a ";
			List<HashMap<String, Object>> listtbl=cq.getListBySQL(sql);
			if(listtbl!=null&&listtbl.size()>0){
				result=result+"\n选择表：\n";
				for(int i=0;i<listtbl.size();i++){
					result=result+listtbl.get(i).get("tbldesc").toString()+"\n";
				}
			}
		}
		//查询条件
		sql="select a.tblname||'.'||a.fldcode codevalue,a.sign,a.fldname,a.valuename1,a.valuecode1,a.valuename2,a.valuecode2,"
			+ "b.code_name from qryuse a,code_value b where a.qvid='"+ctci+"'  and a.sign=b.code_value order by a.sort";
		List<HashMap<String, Object>> listqry=cq.getListBySQL(sql);
		if(listqry!=null&&listqry.size()>0){
			result=result+"\n条件语句：\n";
			sql="select replace(condition,'.','') conditions from grprcdsplmd where ctci='"+ctci+"'";
			Object object=cq.getListBySQL(sql).get(0).get("conditions");
			String conmessage="";
			if(object!=null){
				conmessage=object.toString();
			}
			conmessage=conmessage.replace(" ", "");
			String[] sptcon=conmessage.split("[0-9]{1,}");
			if(conmessage!=null&&!"".equals(conmessage)
				&&sptcon.length==0){
				if("between * and".equals(listqry.get(0).get("sign"))){
					result=result+listqry.get(0).get("codevalue")+" between '"+listqry.get(0).get("valuecode1")+"' and '"+listqry.get(0).get("valuecode2")+"'"
						+ "("+listqry.get(0).get("fldname")+" 在 "+listqry.get(0).get("valuename1")+" 和 "+listqry.get(0).get("valuename2")+" 之间)\n";
				}else{
					String spt=listqry.get(0).get("sign").toString();
					String arr[]=spt.split("\\{");
					result=result+listqry.get(0).get("codevalue")+" "+arr[0]+" '"+listqry.get(0).get("valuecode1")+"'"
							+ "("+listqry.get(0).get("fldname")+" "+listqry.get(0).get("code_name")+" "+listqry.get(0).get("valuename1")+")\n";
				}
			}else{
				result=result+sptcon[0];
				for(int i=0;i<listqry.size();i++){
					String message="";
					if("between * and".equals(listqry.get(i).get("sign"))){
						message=listqry.get(i).get("codevalue")+" between '"+listqry.get(i).get("valuecode1")+"' and '"+listqry.get(i).get("valuecode2")+"'"
							+ "("+listqry.get(i).get("fldname")+" 在 "+listqry.get(i).get("valuename1")+" 和 "+listqry.get(i).get("valuename2")+" 之间)";
					}else{
						String spt=listqry.get(i).get("sign").toString();
						String arr[]=spt.split("\\{");
						message=listqry.get(i).get("codevalue")+" "+arr[0]+" '"+listqry.get(i).get("valuecode1")+"'"
								+ "("+listqry.get(i).get("fldname")+" "+listqry.get(i).get("code_name")+" "+listqry.get(i).get("valuename1")+")";
					}
					if(i==listqry.size()-1&&sptcon.length==listqry.size()){
						result=result+message+"\n";
					}else{
						result=result+message+"   "+sptcon[i+1]+"\n";
					}
				}
			}
		}
		//排序字段
		sql="select "
				+ "  ctc.table_code||'.'||ctc.col_code||'('||b.table_name||'.'||ctc.col_name||')'||'     '||decode(gf.orderby,'1','升序','0','降序')   tblcodesc "
				+ " from code_table_col ctc, grpord gf,code_table b "
				+ " where ctc.table_code||ctc.col_code=gf.tblcode||gf.fldcode "
				+ " and gf.ctci='"+ctci+"' and ctc.table_code=b.table_code "
				+ " order by gf.ordernum1 asc ";
		List<HashMap<String, Object>> listorder=cq.getListBySQL(sql);
		if(listorder!=null&&listorder.size()>0){
			result=result+"\n排序语句：\n";
			for(int i=0;i<listorder.size();i++){
				result=result+listorder.get(i).get("tblcodesc").toString()+"\n";
			}
		}
		return result;

	}
		
		
	/**
	 * 1加载选择信息集tab
	 * 2结果指标项tab
	 * 3加载条件tab
	 * 4加载排序tab
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("intloadttff")
	public int intloadttff() throws RadowException, AppException{
		String ctci=this.getPageElement("ctci").getValue();
		return loadTabTTFF( ctci);
	}
	
	public int loadTabTTFF(String ctci) throws RadowException, AppException{
			CommQuery cq=new CommQuery();
			//加载信息集
			this.setNextEventName("content2query");
			//加载指标项列表
			String userid=SysUtil.getCacheCurrentUser().getId();
			String username=SysUtil.getCacheCurrentUser().getLoginname();
			String instr="";
			if("system".equals(username)){
				
			}else{
				instr=" and col_code in (select t.col_code from COMPETENCE_USERTABLECOL t where t.userid in ('"+Utils.getRoleId(userid)+"')) ";
			}
			String sqlfld="select s.*, rownum ordernum"
					+ " from (select "//
							+ " ctci, "//	主键
							+" table_code||'.'||col_code||'  '||col_name showcolname,"
							+ " table_code, "//	信息表编码,用来关联CODE_TABLE
							+ " col_code, "//	指标项对应的表的对应列
							+ " col_name, "//	指标项对应的表的对应列的名称
							+ " code_type, "//	指标项
							+ " col_data_type_should, "//	应该列数据类型(实际数据类型从v_varify_vsl006视图中查询),新增信息项类型(0:文本,1:数字,2:日期)
							+ " col_data_type "//	列数据类型
						+ " from code_table_col "
						+ " where table_code in ("
						+" select tblname from ("
						+ " select tblname from grpfld where ctci='"+ctci+"'"
						+ "  group by tblname "
						+ " union all  "
						+ " select tblname from qryuse where qvid='"+ctci+"' group by tblname"
								+ ") group by tblname  "
						+ "  )"
						//+ " and isuse='1'"
						+ " and islook='1' "
						+instr
						+ " order by col_code asc)s "
						;
			List<HashMap<String, Object>> listfld=cq.getListBySQL(sqlfld);
			this.getPageElement("contentList2").setValueList(listfld);//tab2
			String sqlfld5=""
					+ " select * from (select s.*, rownum ordernum "
					+ " from (select "//
							+ " ctci, "//	主键
							+" table_code||'.'||col_code||'  '||col_name showcolname,"
							+ " table_code, "//	信息表编码,用来关联CODE_TABLE
							+ " col_code, "//	指标项对应的表的对应列
							+ " col_name, "//	指标项对应的表的对应列的名称
							+ " code_type, "//	指标项
							+ " col_data_type_should, "//	应该列数据类型(实际数据类型从v_varify_vsl006视图中查询),新增信息项类型(0:文本,1:数字,2:日期)
							+ " col_data_type "//	列数据类型
						+ " from code_table_col "
						+ " where table_code in ("
						+" select tblname from ("
						+ " select tblname from grpfld where ctci='"+ctci+"'"
						+ "  group by tblname "
						+ " union all  "
						+ " select tblname from qryuse where qvid='"+ctci+"' group by tblname"
						+ ") group by tblname  "
						+ ")"
					//+ " and isuse='1'"
						+ " and islook='1' "
						+instr
						+ " order by col_code asc)s ) where "
						+ "  col_code not in (select fldcode from grpord where ctci='"+ctci+"') "
								+ " order by ordernum asc "
						;
			List<HashMap<String, Object>> listfld5=cq.getListBySQL(sqlfld5);
			this.getPageElement("contentList3").setValueList(listfld5);//tab5
			String sqlfld3=""
					+ "select * from (select s.*, rownum ordernum"
					+ " from (select "//
							+ " ctci, "//	主键
							+" table_code||'.'||col_code||'  '||col_name showcolname,"
							+ " table_code, "//	信息表编码,用来关联CODE_TABLE
							+ " col_code, "//	指标项对应的表的对应列
							+ " col_name, "//	指标项对应的表的对应列的名称
							+ " code_type, "//	指标项
							+ " col_data_type_should, "//	应该列数据类型(实际数据类型从v_varify_vsl006视图中查询),新增信息项类型(0:文本,1:数字,2:日期)
							+ " col_data_type "//	列数据类型
						+ " from code_table_col "
						+ " where table_code in ("
						+" select tblname from ("
						+ " select tblname from grpfld where ctci='"+ctci+"'"
						+ "  group by tblname "
						+ " union all  "
						+ " select tblname from qryuse where qvid='"+ctci+"' group by tblname"
						+ ") group by tblname  "
						+ ") "
						//+ " and isuse='1'"
						+ " and islook='1' "
						+instr
						+ " order by col_code asc)s ) where "
						+ " col_code not in (select fldname from grpfld where ctci='"+ctci+"' )"
								+ " order by ordernum asc ";
			List<HashMap<String, Object>> listfld3=cq.getListBySQL(sqlfld3);
			this.getPageElement("contentList5").setValueList(listfld3);//tab3 指标项
			this.getPageElement("contentList6").setValueList(listfld);//tab4
			//加载查询结果指标项
			String fldinfosql="select "//
							+ " gf.ctci, "//	主键
							+" table_code||'.'||col_code||'  '||col_name showcolname,"
							+ " table_code, "//	信息表编码,用来关联CODE_TABLE
							+ " col_code, "//	指标项对应的表的对应列
							+ " col_name, "//	指标项对应的表的对应列的名称
							+ " code_type, "//	指标项
							+ " col_data_type_should, "//	应该列数据类型(实际数据类型从v_varify_vsl006视图中查询),新增信息项类型(0:文本,1:数字,2:日期)
							+ " col_data_type, "//	列数据类型
							+ " gf.ordernum ordernum"
							+ " from code_table_col ctc, grpfld gf "
							+ " where ctc.table_code||ctc.col_code=gf.tblname||gf.fldname "
							+instr
							+ " and gf.ctci='"+ctci+"' "
							+ " order by gf.ordernum1 asc "
					+ "  ";
			List<HashMap<String, Object>> listfldinfo=cq.getListBySQL(fldinfosql);
			this.getPageElement("searchList").setValueList(listfldinfo);//tab3
			this.getExecuteSG().addExecuteCode("initHiddenShCL();");
			String fldinfosql4="select "//
					+ " gf.ctci, "//	主键
					+ " case when orderby=1 then '升序@'||table_code||'.'||col_code||'  '||col_name "
						+ " else '降序@'||table_code||'.'||col_code||'  '||col_name end  showcolname,"
					+ " table_code, "//	信息表编码,用来关联CODE_TABLE
					+ " col_code, "//	指标项对应的表的对应列
					+ " col_name, "//	指标项对应的表的对应列的名称
					+ " code_type, "//	指标项
					+ " col_data_type_should, "//	应该列数据类型(实际数据类型从v_varify_vsl006视图中查询),新增信息项类型(0:文本,1:数字,2:日期)
					+ " col_data_type, "//	列数据类型
					+ " gf.ordernum ordernum"
					+ " from code_table_col ctc, grpord gf "
					+ " where ctc.table_code||ctc.col_code=gf.tblcode||gf.fldcode "
					+instr
					+ " and gf.ctci='"+ctci+"' "
					+ " order by gf.ordernum1 asc "
					+ "  ";
			List<HashMap<String, Object>> listfldinfo4=cq.getListBySQL(fldinfosql4);
			this.getPageElement("contentList4").setValueList(listfldinfo4);
			return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 加载记录组合格式设置
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("loadRowRecordSet")
	public int loadRowRecordSet() throws RadowException, AppException{
		String ctci=this.getPageElement("ctci").getValue();
		loadComRcdInfo( ctci);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public void loadComRcdInfo(String ctci) throws RadowException, AppException{
		HBSession hbsession=HBUtil.getHBSession();
		Query query_tzjhf = hbsession.createQuery("from Grprcdsplmd a where a.ctci = :ctci");
		query_tzjhf.setString("ctci", ctci);
		List<Grprcdsplmd> grprcdsplmdlist = query_tzjhf.list();
		if(grprcdsplmdlist!=null&&grprcdsplmdlist.size()>0){
			Grprcdsplmd Grprcdsplmd=grprcdsplmdlist.get(0);
			if(Grprcdsplmd!=null){
				//从第？行
				String temp=Grprcdsplmd.getStartrow();
				if(temp==null||"".equals(temp.trim())||"null".equals(temp.trim())){
					temp="";
				}
				this.getPageElement("startrow").setValue(temp);
				//到第
				temp=Grprcdsplmd.getEndrow();
				if(temp==null||"".equals(temp.trim())||"null".equals(temp.trim())){
					temp="";
				}
				this.getPageElement("endrow").setValue(temp);
				//选择记录连接符
				temp=Grprcdsplmd.getConnector();
				if(temp==null||"".equals(temp.trim())||"null".equals(temp.trim())){
					temp="";
				}
				this.getPageElement("connector").setValue(temp);
				//记录间分行居中排列
				temp=Grprcdsplmd.getCenterbranch();
				if(temp==null||"".equals(temp.trim())||"null".equals(temp.trim())){
					temp="";
				}
				this.getPageElement("centerbranch").setValue(temp);
				//每行？个字符
				temp=Grprcdsplmd.getIndentrow();
				if(temp==null||"".equals(temp.trim())||"null".equals(temp.trim())){
					temp="";
				}
				this.getPageElement("indentrow").setValue(temp);
				//每行？个字符位置
				temp=Grprcdsplmd.getIndextnum();
				if(temp==null||"".equals(temp.trim())||"null".equals(temp.trim())){
					temp="";
				}
				this.getPageElement("indextnum").setValue(temp);
				//从首行开始缩进
				temp=Grprcdsplmd.getFirstindex();
				if(temp==null||"".equals(temp.trim())||"null".equals(temp.trim())){
					temp="";
				}
				this.getPageElement("firstindex").setValue(temp);
			}
		}
		this.getPageElement("area4").setValue(loadCodeDesc( ctci));
	}
	
	
	/**
	 * 
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("clearRowRecordSet")
	public int clearRowRecordSet() throws RadowException, AppException{
		clearRcdInfo();//清空记录组合格式设置
		clearValue();//清空条件信息项tab4页面信息
		cleanJGZBXInfo();////清空tab3页面下拉信息
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 清空添加条件tab4页面的值
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("clearValue")
	public int clearValue() throws RadowException, AppException{
		this.getPageElement("conditionName9").setValue("");//组合条件
		//值二
		this.getPageElement("conditionName71").setValue("");
		this.getPageElement("conditionName71_combotree").setValue("");
		//值二
		this.getPageElement("conditionName7").setValue("");
		//值一
		this.getPageElement("conditionName61").setValue("");
		//值一
		this.getPageElement("conditionName611").setValue("");
		//值一
		this.getPageElement("conditionName6").setValue("");
		this.getPageElement("conditionName6_combotree").setValue("");
		//条件符号
		this.getPageElement("conditionName5").setValue("");
		this.getPageElement("conditionName5_combo").setValue("");
	//	Map<String, Object> map = new LinkedHashMap<String, Object>();
	//	((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
		Map<String, String> map = SqlToMapUtil.HListTomap("select * from dual where 1=2 ", "code_value","code_name");
		SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
		//条件指标项
		this.getPageElement("conditionName4").setValue("");
		
		//"btnn1","btnn2","btnn4"};//1.选择条件 2.并且 3.非
		this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);
		//移除
		this.createPageElement("btnm3", ElementType.BUTTON, false).setDisabled(false);
		//全部删除
		this.createPageElement("btnm4", ElementType.BUTTON, false).setDisabled(false);
		//保存
//		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(false);
//		//预览
//		this.createPageElement("btn5", ElementType.BUTTON, false).setDisabled(false);
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 清空指标项与条件
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("clearAll")
	public int clearAll() throws AppException, RadowException{
		//清除所有条件设置
		//清除指标项
		String sql="select * from dual where 1=2 ";
		CommQuery cq=new CommQuery();
		List<HashMap<String,Object>> list=cq.getListBySQL(sql);
		this.getPageElement("contentList2").setValueList(list);
		this.getPageElement("contentList3").setValueList(list);
		this.getPageElement("contentList5").setValueList(list);
		this.getPageElement("contentList6").setValueList(list);
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 清空条件
	 * @return
	 */
	@PageEvent("clearConditionAll")
	public int clearConditionAll(){
		//清除组合记录设置信息
		//清除字段属性设置
		//清除确认条件设置信息
		//清除组合条件
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("content2query")
	public int content2GridListQuery() throws RadowException, AppException{
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			
		}else{
			instr=" and table_code in (select t.table_code from COMPETENCE_USERTABLE t where t.userid = '"+userid+"')";
		}
		CommQuery cq=new CommQuery();
		String sql="select "
				+ " table_code||'    '||table_name showtablename ,"//
				+ " table_code, "//	信息表编码
				+ " table_name, "//	信息表名称
				+ " code_table_qd, "//	青岛信息表编码
				+ " table_group, "//	信息表群组
				+ " table_name_qd, "//	青岛信息表名称
				+ " cmprlt_qd, "//	
				+ " tblprp_qd, "//	
				+ " iscompfld_qd, "//	
				+ " usecnt_qd "//	
				+ " from code_table "
				+ " where 1=1 "
				+ instr
				+ " order by table_code asc ";
		List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
		this.getPageElement("content2").setValueList(list);
		//this.pageQuery(sql, "SQL",  start, limit);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 加载已经定义的列表
	 * @param startcontentList2
	 * @param limit
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("contentListquery")
	public int initGridList() throws RadowException, AppException{
		String tablejhinfo=this.getPageElement("tablejhinfo").getValue();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			
		}else{
			instr=" and table_code in (select t.table_code from COMPETENCE_USERTABLECOL t "
					+ " where t.userid in ('"+Utils.getRoleId(userid)+"') "
							+ " and  iscompfld='1' )";
		}
		
		CommQuery cq=new CommQuery();
		String sql="select table_code||'.'||col_code||'  '||col_name showname,"
						+ " table_code,"
						+ " col_code,"
						+ " col_name,"
						+ " ctci "
				+ " from code_table_col  "
				+ " where 1=1 "
				+ " and table_code='"+tablejhinfo+"' "
				+ instr
				+ " order by col_code asc ";
		List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
		this.getPageElement("contentList").setValueList(list);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 选择下拉属性字段，给下拉选控件赋值
	 * 有between and  有like 
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("code_type_value1")
	public int code_type_value1(String code_type) throws AppException, RadowException{
		conditionclear();//清除条件
		//运算符号
		//CommQuery commQuery = new CommQuery();
		String sql="select t.code_value,t.code_value||'             '||t.code_name code_name from CODE_VALUE t "
				+ " where  sub_code_value!='11' "
				+ " and t.code_type='OPERATOR' ";
		//List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		for(int i=0;i<list2.size(); i++){
//			map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
//		}
//		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
	
		Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","code_name");
		SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
		
		selectValue1List(code_type);//值一
		this.getExecuteSG().addExecuteCode("setconditionName4();");//给条件指标项赋值
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 清除条件
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("conditionclear")
	public int conditionclear() throws RadowException{
		this.getPageElement("conditionName4").setValue("");//条件指标项
		this.getPageElement("conditionName5").setValue("");//条件符号
		this.getPageElement("conditionName5_combo").setValue("");//条件符号
		this.getPageElement("conditionName6_combotree").setValue("");//值一
		this.getPageElement("conditionName6").setValue("");//值一
		this.getPageElement("conditionName61").setValue("");//值一
		this.getPageElement("conditionName611").setValue("");//值一
		this.getPageElement("conditionName6111").setValue("");//值一
		this.getPageElement("conditionName7").setValue("");//值二
		this.getPageElement("conditionName71").setValue("");//值二
		this.getPageElement("conditionName71_combotree").setValue("");//值二
		this.getPageElement("conditionName711").setValue("");//值二
		this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(true);//或者
		//this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(true);//或者
		this.getExecuteSG().addExecuteCode("document.getElementById('conditionName71_combotree').disabled=true; ");
		this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(true);//或者
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 设置值2 number
	 * @return
	 */
	@PageEvent("setValue211Disable")
	public int setValue211Disable(){
		this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(false);//
		this.createPageElement("conditionName6111", ElementType.BUTTON, false).setDisabled(false);//
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("setValue111Disable")
	public int setValue111Disable(){
		this.createPageElement("conditionName611", ElementType.BUTTON, false).setDisabled(false);//
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 设置值2 date
	 * @return
	 */
	@PageEvent("setValue2Disable")
	public int setValue2Disable(){
		this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(false);//
		this.createPageElement("conditionName61", ElementType.BUTTON, false).setDisabled(false);//
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 值1 下拉选，赋值
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("selectValue1List")
	public int selectValue1List(String code_type) throws RadowException{
		try{
//			String sql="";
//			if(code_type!=null&&"b01".equals(code_type.toLowerCase())){//机构数据取，b01
//				sql="select t.b0114,"
//						+ " b0114||'  '||b0101 showname,"
//						+ " b0101,"
//						+ " b0111 "
//						+ " from b01 t "
//						//+ " where  t.b0111='"+code_type.trim()+"' "
//						+ " order by b0111 asc ";
//			}else{
//				sql="select t.code_value,"
//						+ " code_value||'  '||code_name showname,"
//						+ " code_name "
//						+ " from CODE_VALUE t "
//						+ " where  t.code_type='"+code_type.trim()+"' "
//						+ " order by code_value asc ";
//			}
//			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			//SqlToMapUtil.setValueListForSelect(this, "conditionName6", getMap(code_type));
			this.getExecuteSG().addExecuteCode("setTree('"+code_type+"');");
			//((Combo)this.getPageElement("conditionName6")).setValueListForSelect(map); 
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 设置值2 select
	 * @return
	 * @throws AppException 
	 * @throws RadowException 
	 */
	@PageEvent("setValue21Disable")
	public int setValue21Disable(String code_type) throws RadowException, AppException{
		this.getExecuteSG().addExecuteCode("document.getElementById('conditionName71_combotree').disabled=false; ");
		this.getExecuteSG().addExecuteCode("setTree2('"+code_type+"');");
//		this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(false);//或者
//		SqlToMapUtil.setValueListForSelect(this, "conditionName71", getMap(code_type));
		//((Combo)this.getPageElement("conditionName71")).setValueListForSelect(getMap(code_type)); 
		return EventRtnType.NORMAL_SUCCESS;
	}
	public Map<String, String> getMap(String code_type) throws AppException{
		String sql="";
		if(code_type!=null&&"b01".equals(code_type.toLowerCase())){//机构数据取，b01
			sql="select t.b0114,"
					+ " b0114||'  '||b0101 showname,"
					+ " b0101,"
					+ " b0111 "
					+ " from b01 t "
					+ " order by b0111 asc ";
		}else{
			sql="select t.code_value,"
					+ " code_value||'  '||code_name showname,"
					+ " code_name "
					+ " from CODE_VALUE t "
					+ " where  t.code_type='"+code_type.trim()+"' "
					+ " order by code_value asc ";
		}
		Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
		/*
		String sql="";
		CommQuery commQuery = new CommQuery();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if(code_type!=null&&"b01".equals(code_type.toLowerCase())){//机构数据取，b01
			sql="select t.b0114,b0101,b0111 from b01 t "
					//+ " where  t.b0111='"+code_type.trim()+"' "
					+ " order by b0111 asc ";
			List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
			for(int i=0;i<list2.size(); i++){
				Object b0111=list2.get(i).get("b0111");
				if(b0111==null)b0111="";
				Object b0114=list2.get(i).get("b0114");
				if(b0114==null)b0114="";
				Object b0101=list2.get(i).get("b0101");
				if(b0101==null)b0101="";
				map.put(b0111.toString(),b0114.toString()+" "+b0101);
			}
		}else{
			sql="select t.code_value,code_name from CODE_VALUE t "
					+ " where  t.code_type='"+code_type.trim()+"' "
					+ " order by code_value asc ";
			List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
			for(int i=0;i<list2.size(); i++){
				map.put(replaceSpecial(list2.get(i).get("code_value").toString()),replaceSpecial(list2.get(i).get("code_value").toString())+" "+replaceSpecial(list2.get(i).get("code_name").toString()));
			}
		}*/
		return map;
	}
	/**
	 * 日期，赋值
	 * 有between and  无like 
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("selectValueList")
	public int selectValueList() throws AppException, RadowException{
		try{
			conditionclear();//清除条件
			//CommQuery commQuery = new CommQuery();
			String sql="select t.code_value,t.code_value||'             '||t.code_name code_name from CODE_VALUE t "
					+ " where code_value not like '%like%' "
					+ " and sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			/*List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			for(int i=0;i<list2.size(); i++){
				map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
			}
			((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);*/
			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","code_name");
			SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
			this.getExecuteSG().addExecuteCode("setconditionName4();");//给条件指标项赋值
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 *文本，赋值 
	 * 有like 无between and
	 * @return
	 * @throws AppException 
	 * @throws RadowException 
	 */
	@PageEvent("selectValueListNobt")
	public int selectValueListNobt() throws AppException, RadowException{
		conditionclear();//清除条件
	//	CommQuery commQuery = new CommQuery();
		String sql="select t.code_value,t.code_value||'             '||t.code_name code_name from CODE_VALUE t "
				+ " where code_value not like '%between%' "
				+ " and sub_code_value!='11' "
				+ " and t.code_type='OPERATOR' ";
		/*List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<list2.size(); i++){
			map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
		}
		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);*/
		Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","code_name");
		SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
		this.getExecuteSG().addExecuteCode("setconditionName4();");//给条件指标项赋值
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("select14Method")
	public int select14Method(String tylestr) throws AppException, RadowException{
		String  arr[]=tylestr.split("@");
		String temp=arr.length>=2?arr[1].trim():"";
		String connsymbol=HBUtil.getValueFromTab("code_name", "code_value", "code_type='GRPCNYB' and code_value='"+temp+"' ");
		if(connsymbol==null)connsymbol="";//字段后的连接字符
		this.getExecuteSG().addExecuteCode("document.getElementById('connsymbol').value='"+temp+"';"
				+ "document.getElementById('connsymbol_combo').value='"+connsymbol+"'; ");
		//this.getPageElement("connsymbol").setValue(temp);
		temp=arr.length>=3?arr[2].trim():"";//值为空时连接符处理方式
		String select12=HBUtil.getValueFromTab("code_name", "code_value", "code_type='GRPYBDL' and code_value='"+temp+"' ");
		if(select12==null)select12="";
		this.getExecuteSG().addExecuteCode("document.getElementById('select12').value='"+temp+"';"
				+ "document.getElementById('select12_combo').value='"+select12+"'; ");
		//this.getPageElement("select12").setValue(temp);
		temp=arr.length>=4?arr[3].trim():"";//字段为空的代替符
		String select13=HBUtil.getValueFromTab("code_name", "code_value", "code_type='GRPNLRP' and code_value='"+temp+"' ");
		if(select13==null)select13="";
		this.getExecuteSG().addExecuteCode("document.getElementById('select13').value='"+temp+"';"
				+ "document.getElementById('select13_combo').value='"+select13+"'; ");
		//this.getPageElement("select13").setValue(temp);
		temp=arr.length>=6?arr[5].trim():"";//同类项替代连接符
		String selectsame=HBUtil.getValueFromTab("code_name", "code_value", "code_type='GRPSMRP' and code_value='"+temp+"' ");
		if(selectsame==null)selectsame="";
		this.getExecuteSG().addExecuteCode("document.getElementById('selectsame').value='"+temp+"';"
				+ "document.getElementById('selectsame_combo').value='"+selectsame+"'; ");
		//this.getPageElement("selectsame").setValue(temp);
		temp=arr.length>=7?arr[6].trim():"";//日期格式化
		String select15=HBUtil.getValueFromTab("code_name", "code_value", "code_type='GRPDTFM' and code_value='"+temp+"' ");
		if(select15==null)select15="";
		this.getExecuteSG().addExecuteCode("document.getElementById('select15').value='"+temp+"';"
				+ "document.getElementById('select15_combo').value='"+select15+"'; ");
		//this.getPageElement("select15").setValue(temp);
		if("1".equals(arr.length>=8?arr[7]:"")||"true".equals(arr.length>=8?arr[7].trim():"")){
			this.getPageElement("check4").setValue("1");
		}else{
			this.getPageElement("check4").setValue("0");
		}
		this.createPageElement("check3", ElementType.CHECKBOX, false).setDisabled(false);
		if("1".equals(arr.length>=9?arr[8]:"")||"true".equals(arr.length>=9?arr[8].trim():"")){
			this.getPageElement("check3").setValue("1");
		}else{
			this.getPageElement("check3").setValue("0");
		}
		String tyle=arr[0];
		String sql="";
		if("n".equals(tyle)){
			sql="select t.code_value,t.code_name "
					+ " from CODE_VALUE t "
					+ " where t.code_type='GRPCBFC' " 
					+ " ";
			this.createPageElement("check3", ElementType.CHECKBOX, false).setDisabled(true);
		}else if("t".equals(tyle)){
			sql="select t.code_value,t.code_name "
					+ " from CODE_VALUE t "
					+ " where t.code_type='GRPCBFC' " 
					+ " and code_value not in ('1','5')";
			this.createPageElement("check3", ElementType.CHECKBOX, false).setDisabled(true);
		}else if("c".equals(tyle)){
			sql="select t.code_value,t.code_name "
					+ " from CODE_VALUE t "
					+ " where t.code_type='GRPCBFC' " 
					+ " and code_value ='2'";
			this.createPageElement("check3", ElementType.CHECKBOX, false).setDisabled(true);
		}else if("s".equals(tyle)){
			sql="select t.code_value,t.code_name "
					+ " from CODE_VALUE t "
					+ " where t.code_type='GRPCBFC' " 
					+ " and code_value ='2'";
			this.createPageElement("check3", ElementType.CHECKBOX, false).setDisabled(false);
		}
		Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","code_name");
		SqlToMapUtil.setValueListForSelect(this, "select14", map);
		temp=arr.length>=5?arr[4].trim():"";
		String select14=HBUtil.getValueFromTab("code_name", "code_value", "code_type='GRPCBFC' and code_value='"+temp+"' ");
		if(select14==null)select14="";
		this.getExecuteSG().addExecuteCode("document.getElementById('select14').value='"+temp+"';"
				+ "document.getElementById('select14_combo').value='"+select14+"'; ");
		//this.getPageElement("select14").setValue();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
//	@PageEvent("loadComFldInfo")
//	public int loadComFldInfo(String  arr[]) throws RadowException{
//		
//		this.getPageElement("select14").setValue(arr.length>=5?arr[4].trim():"");
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	/**数字 
	 * 
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("selectValueListLikeBt")
	public int selectValueListLikeBt() throws RadowException, AppException{
		conditionclear();//清除条件
		//运算符号
		//CommQuery commQuery = new CommQuery();
		String sql="select t.code_value,t.code_value||'             '||t.code_name code_name from CODE_VALUE t "
				+ " where  "
				+ " sub_code_value!='11' "
				+ " and t.code_type='OPERATOR' ";
//		List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		for(int i=0;i<list2.size(); i++){
//			map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
//		}
//		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
		Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","code_name");
		SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
		this.getExecuteSG().addExecuteCode("setconditionName4();");//给条件指标项赋值
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 1.添加到列表 显示条件
	 * 2.生成隐藏 条件
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("addtolistfunc")
	public int addtolistfunc() throws RadowException, AppException{

		//指标项代码
		String col_code=this.getPageElement("col_code").getValue();
		//表名
		String table_code=this.getPageElement("table_code").getValue();
		String col_data_type_should=this.getPageElement("col_data_type_should").getValue();//存储的数据类型
		String code_type=this.getPageElement("code_type").getValue();//下拉参数
		String col_data_type=this.getPageElement("col_data_type").getValue();//显示的控件类型
	
		col_data_type_should=col_data_type_should.toLowerCase();
		String conditionName4=this.getPageElement("col_name").getValue();//隐藏的指标项 中文名称 
		//隐藏的指标项 代码
		String conditionName5=this.getPageElement("conditionName5").getValue();//条件符号
		
		//条件符号 中文
		String tabType=this.getPageElement("tabType").getValue();
		if("1".equals(tabType)){
			if(conditionName5==null||conditionName5.trim().equals("")||"null".equals(conditionName5.trim())){
				this.setMainMessage("请选择条件符号!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(col_data_type_should==null||col_data_type_should.trim().length()==0){
				this.setMainMessage("请双击指标项！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String conditionName51=HUtil.getValueFromTab("code_name", "code_value", "code_type='OPERATOR' and code_value='"+conditionName5+"' ");
			//String conditionName51=HUtil.getValueFromTab("code_name", "code_value", "code_type='OPERATOR' and code_value='"+conditionName5+"' ");
			String value1="";
			String value11="";
			String value2="";
			String value21="";
			String qryusestr="";//复查查询条件记录
			if(code_type!=null&&!code_type.trim().equals("")&&!code_type.equals("null")){//值一 下拉
				value1=this.getPageElement("conditionName6").getValue();
				value2=this.getPageElement("conditionName71").getValue();
				if("b01".equals(code_type.toLowerCase())){
					value11=HUtil.getValueFromTab("b0101", "b01", "b0111='"+value1+"'");
					value21=HUtil.getValueFromTab("b0101", "b01", "b0111='"+value2+"'");
				}else{
					value11=HUtil.getValueFromTab("code_name", "code_value", "code_type='"+code_type.toUpperCase()+"' and code_value='"+value1+"'");
					value21=HUtil.getValueFromTab("code_name", "code_value", "code_type='"+code_type.toUpperCase()+"' and code_value='"+value2+"'");
				}
				if(conditionName5.indexOf("between")!=-1){
					qryusestr=tabType
							+","+conditionName4.replace(",", "")//字段名
							+","+col_code//代码
							+","+table_code//表名
							+","+value11//值一中文名
							+","+value1//值一代码
							+","+"S"//字段类型 T时间C字符串(文本)N数字S下拉选
							+","+value2//值二代码
							+","+value21//值二中文名
							+","+conditionName5//符号
							;
				}else if(conditionName5.indexOf("null")!=-1){
					qryusestr=tabType
							+","+conditionName4.replace(",", "")//字段名
							+","+col_code//代码
							+","+table_code//表名
							+","+""//值一中文名
							+","+""//值一代码
							+","+"S"//字段类型 T时间C字符串(文本)N数字S下拉选
							+","+""//值二代码
							+","+""//值二中文名
							+","+conditionName5//符号
							;
				}else{
					qryusestr=tabType
							+","+conditionName4.replace(",", "")//字段名
							+","+col_code//代码
							+","+table_code//表名
							+","+value11//值一中文名
							+","+value1//值一代码
							+","+"S"//字段类型 T时间C字符串(文本)N数字S下拉选
							+","+" "//值二代码
							+","+" "//值二中文名
							+","+conditionName5//符号
							;
				}
			}else if("date".equals(col_data_type_should.toLowerCase())||"t".equals(col_data_type)||"T".equals(col_data_type)){//值一 date
				value2=this.getPageElement("conditionName7").getValue();
				value1=this.getPageElement("conditionName61").getValue();
				if(conditionName5.indexOf("between")!=-1){
					qryusestr=tabType
							+","+conditionName4.replace(",", "")//字段名
							+","+col_code//代码
							+","+table_code//表名
							+","+""//值一中文名
							+","+value1//值一代码
							+","+"T"//字段类型 T时间C字符串(文本)N数字S下拉选
							+","+value2//值二代码
							+","+""//值二中文名
							+","+conditionName5//符号
							;
				}else if(conditionName5.indexOf("null")!=-1){
					qryusestr=tabType
							+","+conditionName4.replace(",", "")//字段名
							+","+col_code//代码
							+","+table_code//表名
							+","+""//值一中文名
							+","+""//值一代码
							+","+"T"//字段类型 T时间C字符串(文本)N数字S下拉选
							+","+""//值二代码
							+","+""//值二中文名
							+","+conditionName5//符号
							;
				}else{
					qryusestr=tabType
							+","+conditionName4.replace(",", "")//字段名
							+","+col_code//代码
							+","+table_code//表名
							+","+""//值一中文名
							+","+value1//值一代码
							+","+"T"//字段类型 T时间C字符串(文本)N数字S下拉选
							+","+""//值二代码
							+","+""//值二中文名
							+","+conditionName5//符号
							;
				}
				
			}else if(col_data_type_should==null
					||col_data_type_should.equals("clob")
					||col_data_type_should.equals("varchar2")
					||col_data_type_should.equals("null")
					||col_data_type_should.trim().equals("")){//值一  文本
				value1=this.getPageElement("conditionName611").getValue();
				if(conditionName5.indexOf("null")!=-1){
					qryusestr=tabType
							+","+conditionName4.replace(",", "")//字段名
							+","+col_code//代码
							+","+table_code//表名
							+","+""//值一中文名
							+","+""
							+","+"C"//字段类型 T时间C字符串(文本)N数字S下拉选
							+","+""//值二代码
							+","+""//值二中文名
							+","+conditionName5//符号
							;
				}else{
					qryusestr=tabType
							+","+conditionName4.replace(",", "")//字段名
							+","+col_code//代码
							+","+table_code//表名
							+","+" "//值一中文名
							+","+value1.replace(",", "").replace("@", "")//值一代码
							+","+"C"//字段类型 T时间C字符串(文本)N数字S下拉选
							+","+" "//值二代码
							+","+" "//值二中文名
							+","+conditionName5//符号
							;
				}
			}else if("number".equals(col_data_type_should)){//number
				value2=this.getPageElement("conditionName711").getValue();
				value1=this.getPageElement("conditionName6111").getValue();
				if(conditionName5.indexOf("between")!=-1){
					qryusestr=tabType
							+","+conditionName4.replace(",", "")//字段名
							+","+col_code.replace(",", "")//代码
							+","+table_code//表名
							+","+""//值一中文名
							+","+value1//值一代码
							+","+"N"//字段类型 T时间C字符串(文本)N数字S下拉选
							+","+value2//值二代码
							+","+""//值二中文名
							+","+conditionName5//符号
							;
				}else if(conditionName5.indexOf("null")!=-1){
					qryusestr=tabType
							+","+conditionName4.replace(",", "")//字段名
							+","+col_code.replace(",", "")//代码
							+","+table_code//表名
							+","+""//值一中文名
							+","+""//值一代码
							+","+"N"//字段类型 T时间C字符串(文本)N数字S下拉选
							+","+""//值二代码
							+","+""//值二中文名
							+","+conditionName5//符号
							;
				}else{
					qryusestr=tabType
							+","+conditionName4.replace(",", "")//字段名
							+","+col_code.replace(",", "")//代码
							+","+table_code//表名
							+","+""//值一中文名
							+","+value1//值一代码
							+","+"N"//字段类型 T时间C字符串(文本)N数字S下拉选
							+","+""//值二代码
							+","+""//值二中文名
							+","+conditionName5//符号
							;
				}
			}
			this.getPageElement("qryusestr").setValue(qryusestr);//拼接条件用于保存
			if(code_type!=null&&!code_type.trim().equals("")&&!code_type.equals("null")){
				String tmp="";
				if(conditionName5.indexOf("between")!=-1){
					tmp=conditionName4+"  "+conditionName51+"  "+value11+" , "+value21;
				}else if(conditionName5.indexOf("null")!=-1){
					tmp=conditionName4+"  "+conditionName51+"  ";
				}else{
					tmp=conditionName4+"  "+conditionName51+"  "+value11;
				}
				tmp=tmp+"@@"+"1";
				this.getPageElement("conditoionlist").setValue(tmp);
			}else{
				String tmp="";
				if(conditionName5.indexOf("between")!=-1){
					 tmp=conditionName4+"  "+conditionName51+"  "+value1+" , "+value2;
				}else if(conditionName5.indexOf("null")!=-1){
					tmp=conditionName4+"  "+conditionName51+"  ";
				}else{
					 tmp=conditionName4+"  "+conditionName51+"  "+value1;
				}
				tmp=tmp+"@@"+"1";
				this.getPageElement("conditoionlist").setValue(tmp);
			}
		}else if("2".equals(tabType)){
			String expressionid=this.getPageElement("expressionid").getValue();
			String expressionexplainid=this.getPageElement("expressionexplainid").getValue();
			this.getPageElement("conditoionlist").setValue(expressionid+"@@"+"2");
			this.getPageElement("qryusestr").setValue("2"+","+expressionid+","+expressionexplainid);
		}
		
		this.getExecuteSG().addExecuteCode("textareadd();");//添加到列表
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int initDate(String code_type){
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 编辑条件
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("arrToContab")
	public int arrToContab(String str) throws RadowException, AppException{
		String arr[]=str.split(",");
		String type=arr[6];
		int num=0;
		String conditionName4=arr[num+3]+'.'+arr[num+2]+' '+arr[num+1];
		this.getPageElement("conditionName4").setValue(conditionName4);
		if("T".equals(type)){
			String sql="select t.code_value,t.code_value||' '||t.code_name showname "
					+ " from code_value t "
					+ " where  "
					+ " sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
			this.getPageElement("conditionName5").setValue(arr[num+9]);
			String name=HBUtil.getValueFromTab("code_value||' '||code_name", "code_value", "code_type = 'OPERATOR' and code_value ='"+arr[num+9]+"' ");
			this.getPageElement("conditionName5_combo").setValue(name);
			if(arr[num+9].indexOf("null")==-1){
				this.getPageElement("conditionName61").setValue(arr[num+5]);
				if(arr.length>=10&&arr[num+9]!=null&&arr[num+9].indexOf("between")!=-1){
					this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(false);
					this.getPageElement("conditionName7").setValue(arr[num+7]);
				}
			}
		}else if("C".equals(type)){
			String sql="select t.code_value,t.code_value||' '||t.code_name showname "
					+ " from CODE_VALUE t "
					+ " where code_value not like '%between%' "
					+ " and sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
			this.getPageElement("conditionName5").setValue(arr[num+9]);
			String name=HBUtil.getValueFromTab("code_value||' '||code_name", "code_value", "code_type = 'OPERATOR' and code_value ='"+arr[num+9]+"' ");
			this.getPageElement("conditionName5_combo").setValue(name);
			this.getPageElement("conditionName611").setValue(arr[num+5]);
		}else if("N".equals(type)){
			String sql="select t.code_value,t.code_value||' '||t.code_name showname "
					+ " from CODE_VALUE t "
					+ " where  "
					+ " sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
			this.getPageElement("conditionName5").setValue(arr[num+9]);
			String name=HBUtil.getValueFromTab("code_value||' '||code_name", "code_value", "code_type = 'OPERATOR' and code_value ='"+arr[num+9]+"' ");
			this.getPageElement("conditionName5_combo").setValue(name);
			if(arr[num+9].indexOf("null")==-1){
				this.getPageElement("conditionName6111").setValue(arr[num+5]);
				if(arr.length>=8&&arr[num+9]!=null&&arr[num+9].indexOf("between")!=-1){
					this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(false);
					this.getPageElement("conditionName711").setValue(arr[num+7]);
				}
			}
		}else if("S".equals(type)){
			String sql="select t.code_value,t.code_value||' '||t.code_name showname "
					+ " from code_value t "
					+ " where sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
			this.getPageElement("conditionName5").setValue(arr[num+9]);
			String name=HBUtil.getValueFromTab("code_value||' '||code_name", "code_value", "code_type = 'OPERATOR' and code_value ='"+arr[num+9]+"' ");
			this.getPageElement("conditionName5_combo").setValue(name);
			String code_type=this.getPageElement("code_type").getValue();
//			if(code_type!=null&&"b01".equals(code_type.toLowerCase())){//机构数据取，b01
//				sql="select t.b0114,"
//						+ " b0114||'  '||b0101 showname,"
//						+ " b0101,"
//						+ " b0111 "
//						+ " from b01 t "
//						+ " order by b0111 asc ";
//			}else{
//				sql="select t.code_value,"
//						+ " code_value||'  '||code_name showname,"
//						+ " code_name "
//						+ " from code_value t "
//						+ " where  t.code_type='"+code_type.trim()+"' "
//						+ " order by code_value asc ";
//			}
//			map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			//SqlToMapUtil.setValueListForSelect(this, "conditionName6", map);
			this.getExecuteSG().addExecuteCode("setTree('"+code_type+"');");
			if(arr[9].indexOf("between")!=-1){
				this.getExecuteSG().addExecuteCode("setTree2('"+code_type+"');");
			}
			if(arr[num+9].indexOf("null")==-1){
				this.getPageElement("conditionName6").setValue(arr[num+5]);
				this.getPageElement("conditionName6_combotree").setValue(arr[num+5]+"  "+arr[num+4]);
				if(arr.length>=9&&arr[num+9]!=null&&arr[num+9].indexOf("between")!=-1){
					this.getExecuteSG().addExecuteCode("document.getElementById('conditionName71_combotree').disabled=false; ");
//				this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(false);
					this.getPageElement("conditionName71").setValue(arr[num+7]);
					this.getPageElement("conditionName71_combotree").setValue(arr[num+7]+"  "+arr[num+8]);
				}
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 点击选中条件，
	 * 隐藏对应按钮
	 * 1.选择条件
	 * 2.（
	 * 3.）
	 * 4.非
	 * 显示对应按钮
	 * 5.并且
	 * 6.或者
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("setDisSelect")
	public int setDisSelect(String arrid) throws RadowException{
		try{
			String id="";
			String arrstr[]=arrid.split(",");
			id=arrstr[0];
			int leftBrakets=Integer.parseInt(arrstr[1]);
			int rightBrakets=Integer.parseInt(arrstr[2]);
			id=arrstr[0];
			//设置可编辑按钮
			if(mapBtn==null||mapBtn.size()==0){
				mapBtn.put("btnn1", btnn1);
				mapBtn.put("btnn2", btnn2);
				mapBtn.put("btnn3", btnn3);
				mapBtn.put("btnn4", btnn4);
				mapBtn.put("btnn5", btnn5);
				mapBtn.put("btnn6", btnn6);
			}
			String arr[]=mapBtn.get(id);
			for(int i=0;i<arr.length;i++){
				this.createPageElement(arr[i], ElementType.BUTTON, false).setDisabled(false);
			}
//			this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(true);//选择条件
//			this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(true);//（
//			this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//）
//			this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);//非
//			this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(false);//并且
//			this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(false);//或者
			//设置不可编辑按钮
			if("btnn1".equals(id)){
				//"btnn5","btnn6"};//1.并且 2.或者
				if(leftBrakets==rightBrakets){
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//）
				}
				if(leftBrakets>rightBrakets){
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(false);//）
				}
				this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);//非
				this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(true);//选择条件
				this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(true);//（
			}else if("btnn2".equals(id)){
				//"btnn4","btnn1","btnn2"};//1.非 2.选择条件 3.（
				this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//）
				this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);//并且
				this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);//或者
			}else if("btnn3".equals(id)){
				//"btnn5","btnn6"};//1.并且 2.或者
				if(leftBrakets>rightBrakets){
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(false);//）
				}
				if(leftBrakets==rightBrakets){
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//）
				}
				this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(true);//选择条件
				this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(true);//（
				this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);//非
			}else if("btnn4".equals(id)){
					//"btnn1","btnn2"};//1.选择条件 2.（
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//）
					this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);//非
					this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);//并且
					this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);//或者
			}else if("btnn5".equals(id)){
				//"btnn1","btnn2"};//1.选择条件  2.（
				this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//）
				this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);//非
				this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);//并且
				this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);//或者
			}else if("btnn6".equals(id)){
				//"btnn1","btnn2"};//1.选择条件 2.（
				this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//）
				this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);//非
				this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);//并且
				this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);//或者
			}
			setAllDis();//设置全部删除、删除、编辑按钮，不可编辑
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 设置不可编辑
	 * @return
	 */
	@PageEvent("setAllDis")
	public int setAllDis(){
		this.createPageElement("btnm4", ElementType.BUTTON, false).setDisabled(true);//
		this.createPageElement("btnm3", ElementType.BUTTON, false).setDisabled(true);//
		this.createPageElement("editbtnm", ElementType.BUTTON, false).setDisabled(true);//
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 重新设置
	 * 隐藏对应按钮
	 * 1.）
	 * 2.并且
	 * 3.或者
	 * 显示对应按钮
	 * 4.选择条件
	 * 5.（
	 * 6.非
	 * @return
	 */
	@PageEvent("refreshDis")
	public int refreshDis(){
		this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//）
		this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);//并且
		this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);//或者
		this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(false);//（
		this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(false);//非
		this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(false);//选择条件
		//设置全部删除按钮，可编辑
		this.createPageElement("btnm4", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnm3", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("editbtnm", ElementType.BUTTON, false).setDisabled(false);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 点击查询结果为sql函数表达式 复选框
	 * @param disable
	 * @return
	 */
	@PageEvent("setDisabled")
	public int setDisabled(String disable){
		if("true".equals(disable)){
			this.createPageElement("button1", ElementType.BUTTON, false).setDisabled(false);//编辑表达式
		}else{
			this.createPageElement("button1", ElementType.BUTTON, false).setDisabled(true);//编辑表达式
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 点击保存
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("saveFunc")
	public int saveFunc() throws RadowException, AppException{
		/*信息校验*/
		//校验已定义的组合字段是否被选中
		Grid gridContentList=(Grid)this.getPageElement("contentList");
		List<HashMap<String, Object>> listContentList=gridContentList.getValueList();
		boolean flagContentList=false;
		for(int i=0;listContentList!=null&&i<listContentList.size();i++){
			String checked=listContentList.get(i).get("change")+"";
			if("true".equals(checked)){
				flagContentList=true;
			}
		}
		if(flagContentList==false){
			this.setMainMessage("请选择已定义的组合字段!");
			this.getExecuteSG().addExecuteCode("swithTabForSave('1')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//校验选择查询结果记录下拉选是否选择
		String startRow=this.getPageElement("startrow").getValue();
		String endRow=this.getPageElement("endrow").getValue();
		if(startRow==null||endRow==null||"".equals(startRow.trim())||"".equals(endRow.trim())){
			this.setMainMessage("请选择查询结果记录!");
			this.getExecuteSG().addExecuteCode("swithTabForSave('1')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//校验选择信息集是否被选中
		Grid gridContent2=(Grid)this.getPageElement("content2");
		List<HashMap<String, Object>> listContent2=gridContent2.getValueList();
		boolean flagContentList2=false;
		for(int i=0;listContent2!=null&&i<listContent2.size();i++){
			String checked=listContent2.get(i).get("change")+"";
			if("true".equals(checked)){
				flagContentList2=true;
			}
		}
		if(flagContentList2==false){
			this.setMainMessage("请选择信息集!");
			this.getExecuteSG().addExecuteCode("swithTabForSave('2')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//校验查询结果指标项是否有选择
		Grid gridSearchList=(Grid)this.getPageElement("searchList");
		List<HashMap<String, Object>> listSearchList=gridSearchList.getValueList();
		if(listSearchList==null||listSearchList.size()<1){
			this.setMainMessage("请选择查询结果指标项!");
			this.getExecuteSG().addExecuteCode("swithTabForSave('3')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String ctci=this.getPageElement("ctci").getValue();
		//校验如果选择结果指标项用sql表达式表示的话，是否输入了表达式
		String slfcfg=this.getPageElement("check2").getValue();
		if(slfcfg!=null&&!slfcfg.equals("1")&&!slfcfg.equals("true")){
			
		}else{
			String sql="select resexpress,expressdesc from RESULTSPELL where ctci='"+ctci+"'";
			CommQuery cq=new CommQuery();
			List<HashMap<String,Object>> listexpress=cq.getListBySQL(sql);
			if(listexpress!=null&&listexpress.size()>0){
				
			}else{
				this.setMainMessage("请输入结果指标项的sql表达式");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		String searchGrpFld=this.getPageElement("searchGrpFld").getValue();//选择的信息项与组合字段设置信息字符串
		searchGrpFld=searchGrpFld.replace("\'", "");
		String searctListArr[]=searchGrpFld.split("\\$");
		Map<String,String[]> map=new HashMap<String, String[]>();
		for(int i=0;i<searctListArr.length;i++){
			String temp=searctListArr[i];
			String arr[]=temp.split("@");
			map.put(arr[0].toLowerCase()+arr[1].toLowerCase(), arr);
		}
		/*2.保存数据*/
		HBSession session=HBUtil.getHBSession();
		String sqldelete = "delete from grprcdsplmd where ctci='"+ctci+"' ";
		session.createSQLQuery(sqldelete).executeUpdate();
		Grprcdsplmd	grprcdsplmd=new Grprcdsplmd();//保存记录组合格式设置
		grprcdsplmd.setCtci(ctci);
		//选择查询结果记录:从第
		String startrow=this.getPageElement("startrow").getValue();
		grprcdsplmd.setStartrow(startrow);
		//到第
		String endrow=this.getPageElement("endrow").getValue();
		grprcdsplmd.setEndrow(endrow);
		//选择记录间连接符
		String connector=this.getPageElement("connector").getValue();
		grprcdsplmd.setConnector(connector);
		//记录缩进排列:每行
		String indentrow=this.getPageElement("indentrow").getValue();
		grprcdsplmd.setIndentrow(indentrow);
		//每行
		String indextnum=this.getPageElement("indextnum").getValue();
		grprcdsplmd.setIndextnum(indextnum);
		//记录间分行居中排列
		String firstindex=this.getPageElement("firstindex").getValue();
		grprcdsplmd.setFirstindex(firstindex);
		//从首行开始缩进
		String centerbranch=this.getPageElement("centerbranch").getValue();
		grprcdsplmd.setCenterbranch(centerbranch);
		//查询结果为sql表达式标志1是0否
		//String slfcfg=this.getPageElement("check2").getValue();
		if(slfcfg!=null&&!slfcfg.equals("1")&&!slfcfg.equals("true")){
			slfcfg="0";
		}
		grprcdsplmd.setSlfcfg(slfcfg);
		//String qrysql=getSql( map);//生成的sql
		String qrysql=getSqlInCondition( );//生成的sql
		
		this.getPageElement("querysql").setValue(qrysql);
		grprcdsplmd.setQrysql(qrysql);
		//组合条件
		String zhtj=this.getPageElement("zhtj").getValue();
		//String condition=this.getPageElement("conditionName9").getValue();
		grprcdsplmd.setCondition(zhtj);
		session.save(grprcdsplmd);
		session.flush();
		
		String sqldeletegrpfld = "delete from grpfld where ctci='"+ctci+"' ";
		session.createSQLQuery(sqldeletegrpfld).executeUpdate();
		if("1".equals(slfcfg)){//查询结果为sql表达式标志1是0否
			for(int i=0;listSearchList!=null&&listSearchList.size()>i;i++){
				Grpfld grpfld=new Grpfld();//保存查询结果指标项
				grpfld.setCtci(ctci);//code_table_col表的主键
				String tblname=(String)listSearchList.get(i).get("table_code");//信息集 表名
				grpfld.setTblname(tblname);
				String fldname=(String)listSearchList.get(i).get("col_code");//指标项名
				grpfld.setFldname(fldname);
				String fldnamenote=(String)listSearchList.get(i).get("col_name");//指标项 字段名
				grpfld.setFldnamenote(fldnamenote);
				String codetype=(String)listSearchList.get(i).get("code_type");
				grpfld.setCodetype(codetype);
				String ordernum=(String)listSearchList.get(i).get("ordernum");
				grpfld.setOrdernum(ordernum);
				grpfld.setOrdernum1((i+1)+"");
//				String arr[]=map.get(tblname.toLowerCase()+fldname.toLowerCase());
//				grpfld.setConnsymbol(arr.length>=3?arr[2]:"");
//				grpfld.setHandnull(arr.length>=4?arr[3]:"");
//				grpfld.setFldnull(arr.length>=5?arr[4]:"");
//				grpfld.setFldfunc(arr.length>=6?arr[5]:"");
//				grpfld.setReplacefld(arr.length>=7?arr[6]:"");
//				grpfld.setDatefmt(arr.length>=8?arr[7]:"");
//				grpfld.setMeregfld(arr.length>=9?("true".equals(arr[8])||"1".equals(arr[8])?"1":"0"):"");
//				grpfld.setCodetocomm(arr.length>=10?("true".equals(arr[9])||"1".equals(arr[9])?"1":"0"):"");
				session.save(grpfld);
			}
			session.flush();
		}else{
			for(int i=0;listSearchList!=null&&listSearchList.size()>i;i++){
				Grpfld grpfld=new Grpfld();//保存查询结果指标项
				grpfld.setCtci(ctci);//code_table_col表的主键
				String tblname=(String)listSearchList.get(i).get("table_code");//信息集 表名
				grpfld.setTblname(tblname);
				String fldname=(String)listSearchList.get(i).get("col_code");//指标项名
				grpfld.setFldname(fldname);
				String fldnamenote=(String)listSearchList.get(i).get("col_name");//指标项 字段名
				grpfld.setFldnamenote(fldnamenote);
				String codetype=(String)listSearchList.get(i).get("code_type");
				grpfld.setCodetype(codetype);
				String ordernum=(String)listSearchList.get(i).get("ordernum");
				grpfld.setOrdernum(ordernum);
				grpfld.setOrdernum1((i+1)+"");
				String arr[]=map.get(tblname.toLowerCase()+fldname.toLowerCase());
				grpfld.setConnsymbol(arr.length>=3?arr[2]:"");
				grpfld.setHandnull(arr.length>=4?arr[3]:"");
				grpfld.setFldnull(arr.length>=5?arr[4]:"");
				grpfld.setFldfunc(arr.length>=6?arr[5]:"");
				grpfld.setReplacefld(arr.length>=7?arr[6]:"");
				grpfld.setDatefmt(arr.length>=8?arr[7]:"");
				grpfld.setMeregfld(arr.length>=9?("true".equals(arr[8])||"1".equals(arr[8])?"1":"0"):"");
				grpfld.setCodetocomm(arr.length>=10?("true".equals(arr[9])||"1".equals(arr[9])?"1":"0"):"");
				session.save(grpfld);
			}
			session.flush();
		}
		
		
	
//		Grpfldsplmd grpfldsplmd=new Grpfldsplmd();//保存组合字段 字段属性设置
//		//String codetocomm	代码字段转换为描述标志
//		String connsymbol=this.getPageElement("connsymbol").getValue();//字段后的连接字符
//		String handnull=this.getPageElement("select12").getValue();	//值为空时的连接符处理方式
//		String fldnull=this.getPageElement("select13").getValue();	//字段为空的代替符
//		String fldfunc=this.getPageElement("select14").getValue();	//字段合计函数
//		//String meregfld=this.getPageElement("selectsame").getValue();	//合计同类项字段标志
//		String replacefld=this.getPageElement("selectsame").getValue();	//同类项代替连接符
//		String datefmt=this.getPageElement("select15").getValue();	//日期格式化
//		//code_table_col 主键
//		grpfldsplmd.setCtci(ctci);
//		grpfldsplmd.setConnsymbol(connsymbol);
//		grpfldsplmd.setHandnull(handnull);
//		grpfldsplmd.setFldnull(fldnull);
//		grpfldsplmd.setFldfunc(fldfunc);
//		grpfldsplmd.setReplacefld(replacefld);
//		grpfldsplmd.setDatefmt(datefmt);
//		session.save(grpfldsplmd);
		
		//保存条件记录
		String sqldeleteqryuse= "delete from qryuse where qvid='"+ctci+"' ";
		session.createSQLQuery(sqldeleteqryuse).executeUpdate();
		String sqlgrphicd= "delete from grphicd where ctci='"+ctci+"' ";
		session.createSQLQuery(sqlgrphicd).executeUpdate();
		String allqryusestr=this.getPageElement("allqryusestr").getValue();
		String arrAt[]=null;//所有记录
		if(allqryusestr!=null&&!allqryusestr.trim().equals("")){
			arrAt=allqryusestr.split("。@。");
		}
		for(int i=0;arrAt!=null&&i<arrAt.length;i++){
			String arrone[]=arrAt[i].split(",");
			String temp=arrone[0];
			if("1".equals(temp)){
				Qryuse qryuse=new Qryuse();
				qryuse.setQvid(ctci);
				qryuse.setSort((i+1)+"");
				qryuse.setFldname(arrone[1]);
				qryuse.setFldcode(arrone[2]);
				qryuse.setTblname(arrone[3]);
				qryuse.setValuename1(arrone[4]);
				qryuse.setValuecode1(arrone[5]);
				qryuse.setLabletype(arrone[6]);
				qryuse.setValuecode2(arrone[7]);
				qryuse.setValuename2(arrone[8]);
				qryuse.setSign(arrone[9]);
				session.save(qryuse);
			}else if("2".equals(temp)){
				Grphicd grphicd=new Grphicd();
				grphicd.setCtci(ctci);
				grphicd.setOrdernum((i+1)+"");
				grphicd.setCondition(arrone[1]);
				if(arrone.length>2){
					grphicd.setTranslation(arrone[2]);
				}
				session.save(grphicd);
				
			}
			
		}
		session.flush();
		String sqldeletegrpord= "delete from grpord where ctci='"+ctci+"' ";
		session.createSQLQuery(sqldeletegrpord).executeUpdate();
		Grid gridContentList4=(Grid)this.getPageElement("contentList4");//排序列表
		List<HashMap<String, Object>> ContentList4=gridContentList4.getValueList();
		for(int i=0;ContentList4!=null&&i<ContentList4.size();i++){
			Grpord grpord=new Grpord();
			//CTCI	col_table_col主键
			grpord.setCtci(ctci);
			//FLDCODE	字段 col_code
			String fldcode=(String)ContentList4.get(i).get("col_code");
			grpord.setFldcode(fldcode);
			//ORDERNUM	排序
			String ordernum=(String)ContentList4.get(i).get("ordernum");
			grpord.setOrdernum(ordernum);
			grpord.setOrdernum1((i+1)+"");
			//TBLCODE	表
			String tblcode=(String)ContentList4.get(i).get("table_code");
			grpord.setTblcode(tblcode);
			String showcolname=(String)ContentList4.get(i).get("showcolname");
			if(showcolname.indexOf("升序")!=-1){
				grpord.setOrderby("1");
			}else{
				grpord.setOrderby("0");
			}
			session.save(grpord);
		}
		session.flush();
		/*执行sql*/
		this.getExecuteSG().addExecuteCode("insertGrpFld('"+grprcdsplmd.getRsm001()+"','"+ctci+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 根据组合条件 生成sql 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
//	public String getSql(Map<String,String[]> map) throws RadowException, AppException{
//		Grid gridCode = (Grid)this.getPageElement("searchList");
//		List<HashMap<String, Object>> listCode=gridCode.getValueList();
////		Grid gridTable = (Grid)this.getPageElement("content2");//
//		List<HashMap<String, Object>> listTable=gridCode.getValueList();
//		
//		//拼接order by 
//		Grid girdorder=(Grid)this.getPageElement("contentList4");
//		List<HashMap<String, Object>> listorder=girdorder.getValueList();
//		/*生成查询sql保存到自定义查询视图表中*/
//		String sqlSelect="select ";
//		for(int i=0;i<listCode.size();i++){
//			String tempTable=(String)listCode.get(i).get("table_code");
//			String tempCode=(String)listCode.get(i).get("col_code");
//			//String code_type=(String)listCode.get(i).get("code_type");
//			String arr[]=map.get(tempTable.toLowerCase()+tempCode.toLowerCase());
//			//String col_data_type_should=(String)listCode.get(i).get("col_data_type_should");
//			//String col_data_type=(String)listCode.get(i).get("col_data_type");
//			if(arr.length>=3){
//				String fld=tempTable+tempCode;
//				String temp=HBUtil.getValueFromTab("code_name", "code_value", "code_type='GRPCNYB' and code_value='"+arr[2]+"' ");
//				if(arr.length>=3&&!arr[2].equals("")){//字段后的连接符
//					//值为空时连接符处理方式
////					if("1".equals(arr[3])){//前后连接符都不要
////						sqlSelect=sqlSelect+fld;
////					}else if("2".equals(arr[3])){//前连接符不要
////						fld="decode("+fld+",'','','*','*','_','_','-','-','&','&','@','@','以后（日期型字段）','以后（日期型字段）','以前（日期型字段）','以前（日期型字段）','至今（日期型字段）','至今（日期型字段）')";
////					}else if("3".equals(arr[3])){//后连接符不要
////						fld=fld+"||"+"'"+temp+"'";
////					}else{//字段后的连接符
////						fld="'"+temp+"'"+"||"+"      "+fld;
////					}
//					sqlSelect=sqlSelect+fld+"||'"+temp+"'||";
//				}
//			}
//		}
//		sqlSelect=sqlSelect.toLowerCase();
//		sqlSelect=sqlSelect.substring(0, sqlSelect.length()-2);
//		sqlSelect=sqlSelect+" from ";
//		String tablestr="";
//		for(int i=0;i<listTable.size();i++){
//			String tempt=(String)listTable.get(i).get("table_code");
//			if(tablestr.indexOf(tempt)==-1){
//				tablestr=tablestr+tempt+",";
//			}
//		}
//		tablestr=tablestr.toLowerCase();
//		String inA0000Str=getSqlInCondition();
//		String tabletemp="";//表名
//		for(int i=0;i<listTable.size();i++){
//			String tempt=(String)listTable.get(i).get("table_code");
//			if(tablestr.indexOf(tempt.toLowerCase())!=-1){
//				String tempTable=(String)listCode.get(i).get("table_code");
//				String tempCode=(String)listCode.get(i).get("col_code");
//				String code_type=(String)listCode.get(i).get("code_type");
//				String arr[]=map.get(tempTable.toLowerCase()+tempCode.toLowerCase());
//				String col_data_type_should=(String)listCode.get(i).get("col_data_type_should");
//				String col_data_type=(String)listCode.get(i).get("col_data_type");
//				String fld=tempTable+"."+tempCode;
//				if(arr.length>=10&&"true".equals(arr[9])&&!"".equals(code_type)){//代码字段转换为描述
//					fld="(select code_name from code_value where code_type='"+code_type.toUpperCase()+"' and code_value='"+fld+"') ";
//				}
//				if(arr.length>=8&&!arr[7].equals("")&&(arr.length<10||!"true".equals(arr[9]))){//日期格式化
//					if("date".equalsIgnoreCase(col_data_type_should)||"t".equalsIgnoreCase(col_data_type)){
//						String temp=HBUtil.getValueFromTab("code_name", "code_value", "code_type='GRPDTFM' and code_value='"+arr[7]+"' ");
//						fld="to_char(to_date("+fld+",'yyyy-mm-dd hh24:mi:ss'),'"+temp+"')";
//					}
//				}
//				if(arr.length>=5&&!arr[4].equals("")){//字段为空的代替符
//					String temp=HBUtil.getValueFromTab("code_name", "code_value", "code_type='GRPNLRP' and code_value='"+arr[4]+"' ");
//					fld="nvl("+fld+",'"+temp+"')";
//				}
//				//合并 - 统计
//				if(arr.length>=9&&"true".equals(arr[8])){//合并同类项字段
//					if(!"".equals(arr[6])){//同类项替代连接符
//						String temp=HBUtil.getValueFromTab("code_name", "code_value", "code_type='GRPSMRP' and code_value='"+arr[6]+"' ");
////						fld="LISTAGG("+fld+",'"+temp+"')";
//						fld="wm_concat("+fld+")";
//					}else{
////						fld="listagg("+fld+",'')";
//						fld="wm_concat("+fld+")";
//					}
//				}
//				if(arr.length>=6&&!arr[5].equals("")){//字段合计函数
//					String temp=HBUtil.getValueFromTab("code_name2", "code_value", "code_type='GRPCBFC' and code_value='"+arr[5]+"' ");
//					fld=temp+"("+fld+")";
//				}
//				if((arr.length>=6&&!arr[5].equals(""))||(arr.length>=9&&"true".equals(arr[8]))){
//					
//					String ordertmp="";
//					if(listorder!=null&&listorder.size()>0){
//						for(int j=0;j<listorder.size();j++){
//							String col_code=(String)listorder.get(j).get("col_code");
//							String table_code=(String)listorder.get(j).get("table_code");
//							String showcolname=(String)listorder.get(i).get("showcolname");
//							if(table_code.equals(tempTable)){
//								if(showcolname!=null&&showcolname.indexOf("升序")!=-1){
//									ordertmp=ordertmp+" order by "+tempCode+"."+col_code+" asc,";
//								}else{
//									ordertmp=ordertmp+" order by "+tempCode+"."+col_code+" desc,";
//								}
//								
//							}
//						}
//						if("".equals(ordertmp)){
//							ordertmp=ordertmp.substring(0,ordertmp.length()-1);
//						}
//					}else{
//					}
//					if("".equals(ordertmp)){
//						if("b".equals(tempCode.substring(0, 1).toLowerCase())){//机构
//							tabletemp=tabletemp+"(select a0000,"+fld+" "+tempTable+tempCode+" from "+tempTable+",(select a0000,min(a0201b) a0201b from a02 where a0000 in ("+inA0000Str+") group by a0000) a02 where a02.a0201b="+tempTable+".b0111 ) "+tempTable+",";
//						}else{
//							tabletemp=tabletemp+"(select a0000,"+fld+" "+tempTable+tempCode+" from "+tempTable+" where a0000 in ("+inA0000Str+") group by a0000 ) "+tempTable+",";
//						}
//					}else{
//						if("b".equals(tempCode.substring(0, 1).toLowerCase())){//机构
//							tabletemp=tabletemp+"(select a0000,"+fld+" "+tempTable+tempCode+" from "+tempTable+",(select a0000,min(a0201b) a0201b from a02 where a0000 in ("+inA0000Str+") group by a0000) a02 where a02.a0201b="+tempTable+".b0111 order by '"+ordertmp+"' ) "+tempTable+",";
//						}else{
//							tabletemp=tabletemp+"(select a0000,"+fld+" "+tempTable+tempCode+" from "+tempTable+" where a0000 in ("+inA0000Str+")  group by a0000  order by '"+ordertmp+"') "+tempTable+",";
//						}
//						
//					}
//				}else{
//					if("b".equals(tempCode.substring(0, 1).toLowerCase())){//机构
//						tabletemp=tabletemp+"(select a0000,max("+fld+") "+tempTable+tempCode+" from "+tempTable+",(select a0000,min(a0201b) a0201b from a02 where a0000 in ("+inA0000Str+") group by a0000) a02 where a02.a0201b="+tempTable+".b0111  ) "+tempTable+",";
//					}else{
//						tabletemp=tabletemp+"(select a0000,max("+fld+") "+tempTable+tempCode+" from "+tempTable+" where in ("+inA0000Str+") group by a0000 ) "+tempTable+",";
//					}
//				}
//				tablestr=tablestr.replace(tempt, "");
//			}
//		}
//		tablestr="";
//		for(int i=0;i<listTable.size();i++){
//			String tempt=(String)listTable.get(i).get("table_code");
//			if(tablestr.indexOf(tempt)==-1){
//				tablestr=tablestr+tempt+",";
//			}
//		}
//		tablestr=tablestr.toLowerCase();
//		tabletemp=tabletemp.substring(0, tabletemp.length()-1);
//		if(tablestr.indexOf("a01")==-1){//a01 必须选择
//			tabletemp=tabletemp+",a01";
//		}
//		if(tablestr!=null&&tablestr.indexOf("b01")!=-1){//选择b01则必须选择a02，前台不选则，则sql自动添加
//			if(tablestr.indexOf("a02")==-1){
//				tabletemp=tabletemp+",(select a0000 from a02 group by a0000) a02";
//			}
//		}
//		sqlSelect=sqlSelect+tabletemp;//拼接表名
//		
//		sqlSelect=sqlSelect+" where 1=1 ";
//		String tableid="";//表关联
//		if(tabletemp!=null&&!tabletemp.equals("null")&&tabletemp.trim().length()>0){
//			for(int i=0;i<listTable.size();i++){
//				String temp=(String)listTable.get(i).get("table_code");
//				if(!"a01".equals(temp.toLowerCase())&&!"b".equals(temp.substring(0, 1).toLowerCase())){
//					tableid=tableid+" and a01.a0000="+temp.toLowerCase()+".a0000 ";
//				}else if("b".equals(temp.substring(0, 1).toLowerCase())){//b01.b0111=a02.b0111 and a02.a0000=a01.a0000
//					tableid=tableid+" and a02.a0201b="+temp.toLowerCase()+".b0111 ";
//				}
//					
//			}
//		
//		}
//		sqlSelect=sqlSelect+tableid;
//		String qrysql=sqlSelect;
//		
//		this.getPageElement("querysql").setValue(qrysql);//隐藏sql到前台
//		return qrysql;
//	}
	
	/**
	 * 根据组合条件 生成sql 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	public String getSqlInCondition() throws RadowException, AppException{
		Grid gridCode = (Grid)this.getPageElement("content2");
		List<HashMap<String, Object>> listTable=gridCode.getValueList();
		
		
		/*生成查询sql保存到自定义查询视图表中*/
		String sqlSelect="select a01.a0000 from  ";
		
		String tabletemp="";//表名
//		for(int i=0;i<listTable.size();i++){
//			String temp=(String)listTable.get(i).get("table_code");
//			if(tabletemp.indexOf(temp.toLowerCase()+",")==-1){
//				tabletemp=tabletemp+" "+temp.toLowerCase()+",";
//			}
//			
//		}
		
		for(int i=0;i<listTable.size();i++){
			String temp=(String)listTable.get(i).get("table_code");
			String change=listTable.get(i).get("change")+"";
			if("true".equals(change)){
				tabletemp=tabletemp+" "+temp.toLowerCase()+",";
			}
			
		}

		tabletemp=tabletemp.substring(0, tabletemp.length()-1);
		tabletemp=tabletemp.toLowerCase();
		if(tabletemp.indexOf("a01")==-1){//a01 必须选择
			tabletemp=tabletemp+",a01";
		}
		if(tabletemp!=null&&tabletemp.indexOf("b01")!=-1){//选择b01则必须选择a02，前台不选则，则sql自动添加
			if(tabletemp.indexOf("a02")==-1){
				tabletemp=tabletemp+",a02";
			}
		}
		sqlSelect=sqlSelect+tabletemp;//拼接表名
		
		sqlSelect=sqlSelect+" where 1=1 ";
		String tableid="";//表关联
		if(tabletemp!=null&&!tabletemp.equals("null")&&tabletemp.trim().length()>0){
			String arr[]=tabletemp.split(",");
			for(int i=0;i<arr.length;i++){
				String temp=arr[i];
				temp=temp.trim();
				if(!"a01".equals(temp.toLowerCase())&&!"b".equals(temp.substring(0, 1).toLowerCase())){
					tableid=tableid+" and a01.a0000="+temp.toLowerCase()+".a0000 ";
				}else if("b".equals(temp.substring(0, 1).toLowerCase())){//b01.b0111=a02.b0111 and a02.a0000=a01.a0000
					tableid=tableid+" and a02.a0201b="+temp.toLowerCase()+".b0111 ";
				}
			}
		}

		sqlSelect=sqlSelect+" and a01.status!='4' "//垃圾数据
				+ " ";
		sqlSelect=sqlSelect+tableid;//拼接表间关联
		sqlSelect=sqlSelect.toLowerCase();
		String whereConditionStr=this.getPageElement("conditionStr").getValue();//组合条件
		String qrysql="";
		if(whereConditionStr!=null
				&&!whereConditionStr.trim().equals("")
				&&!whereConditionStr.trim().equals("null")){
			qrysql=sqlSelect+" and (" + whereConditionStr+") ";//生成sql
		}else{
			qrysql=sqlSelect;
		}
		//替换条件中的运算符
		qrysql=qrysql.replace("{v}", "")
				.replace("{%v}", "")
				.replace("{v%}", "")
				.replace("{%v%}", "");
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			
		}else{
			instr=CustomQueryPageModel.getGllb2();
			if(!"".equals(instr)){
				instr=" and a01.a0000 in "+instr;
			}
//			instr=" and a01.a0000 in (select t.a0000 from Competence_Subperson t where t.userid = '"+userid+"' and t.type = '1' ) ";
		}
		qrysql=qrysql+instr;
		this.getPageElement("querysql").setValue(qrysql);//隐藏sql到前台
		return qrysql;
	}
	/**
	 * 更新组合字段数据
	 * @return
	 * @throws AppException 
	 */
	public String [] createGrpData(String ctci) throws AppException{
	//	String updatesql="";
		String [] temp=new String[2];
		String flag="false";
		String grpfld="";
		String fldlth="";
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try{
			String qrysql=HBUtil.getValueFromTab("qrysql", "grprcdsplmd", "ctci='"+ctci+"'  ");
			String col_code=HBUtil.getValueFromTab("col_code", "code_table_col", "ctci='"+ctci+"' ");
			String table_code=HBUtil.getValueFromTab("table_code", "code_table_col", "ctci='"+ctci+"' ");
		//	String col_name=HBUtil.getValueFromTab("col_name", "code_table_col", "ctci='"+ctci+"' ");
			//System.out.println("更新组合字段 "+col_name+" 开始：----------------");
			fldlth=HBUtil.getValueFromTab("fldlth", "code_table_col", "ctci='"+ctci+"' ");
			//System.out.println("插入新增人员信息开始："+df.format(new Date()));  
			String insertA000=" insert into "+table_code+" ("+table_code+"00,a0000) (select sys_guid(),a0000 from a01 where a01.a0000 not in (select a0000 from "+table_code+") )";
			HBSession session=HBUtil.getHBSession();
			session.createSQLQuery(insertA000).executeUpdate();
			//System.out.println("插入新增人员信息结束："+df.format(new Date()));  
			String cleanFld="update "+table_code+" set "+col_code+"='' ";
			//System.out.println("清空组合字段开始："+df.format(new Date()));  
			session.createSQLQuery(cleanFld).executeUpdate();
			//System.out.println("清空组合字段结束："+df.format(new Date()));  
			String qrya0000sql="";
			if(qrysql==null||"".equals(qrysql)){
				qrya0000sql="select a0000 from "+table_code+" ";
			}else{
				qrya0000sql="select a0000 from "+table_code+" where a0000 in ("+qrysql+")";
			}
			CommQuery cq=new CommQuery();
			//System.out.println("查询人员信息开始："+df.format(new Date()));  
			List<HashMap<String, Object>> list=cq.getListBySQL(qrya0000sql);
		//	System.out.println("查询人员信息结束："+df.format(new Date()));  
			String sqlord="select * from grpord where ctci='"+ctci+"' order by ordernum1 asc ";
			List<HashMap<String, Object>> listord=cq.getListBySQL(sqlord);
			String sqlrcd="select * from grprcdsplmd where ctci='"+ctci+"' ";
			List<HashMap<String, Object>> listrcd=cq.getListBySQL(sqlrcd);
			String sqlfld="select * from grpfld where ctci='"+ctci+"' order by ordernum1 asc ";
			List<HashMap<String, Object>> listfld=cq.getListBySQL(sqlfld);
			String sqlsyb="select code_type||code_value typevalue,code_name2 from code_value where code_type like '%GRP%'";
			List<HashMap<String, Object>> listsyb=cq.getListBySQL(sqlsyb);
			if(mapsyb.size()<=0){
				for(int i=0;i<listsyb.size();i++){
					mapsyb.put((String)listsyb.get(i).get("typevalue"), listsyb.get(i).get("code_name2"));
				}
			}
			String uuid=java.util.UUID.randomUUID().toString();//代替空值
			String slfcfg="";
			if(listrcd!=null&&listrcd.size()>0){
				HashMap<String, Object> map=listrcd.get(0);
				if(map!=null){
					Object o=map.get("slfcfg");
					if(o!=null){
						slfcfg=o.toString();
					}
				}
			}
			Map<String,Object> mapjson=new HashMap<String, Object>();
		
			if("1".equals(slfcfg)){
				String sql="select * from RESULTSPELL where ctci='"+ctci+"'";
				List<HashMap<String, Object>> listexp=cq.getListBySQL(sql);
				if(listexp!=null&&listexp.size()>0){
					List<HashMap<String, Object>> aFLdList=getAFld("",ctci,qrya0000sql,listord,listrcd,listfld,cq,uuid);
					Map<String,Object> maptemp =excuteList(aFLdList,listrcd);
					for (Map.Entry<String, Object> entry : maptemp.entrySet()) {
						
						Object o=entry.getValue();
						if(o!=null){
							grpfld=o.toString();
						}
						String a0000=entry.getKey();
						if(grpfld==null){grpfld="";}
						if(grpfld.length()>Integer.parseInt(fldlth)){
							grpfld=grpfld.substring(0,Integer.parseInt(fldlth));
							int oraclelength=getWordCountRegex(grpfld);
							int temp11=oraclelength-grpfld.length();
							grpfld=grpfld.substring(0,grpfld.length()-temp11);
							flag="true";
						}else if(getWordCountRegex(grpfld)>Integer.parseInt(fldlth)){
							int oraclelength=getWordCountRegex(grpfld);
							int temp11=oraclelength-grpfld.length();
							grpfld=grpfld.substring(0,grpfld.length()-temp11);
							flag="true";
						}
						mapjson.put(a0000, grpfld);
					}
//					for(int i=0;list!=null&&i<list.size();i++){//循环人员
//						HashMap<String, Object> map=list.get(i);
//						String a0000=(String)map.get("a0000");
//						grpfld=getResSpell("",ctci,a0000,listord,listrcd,listfld,cq,uuid);
//						if(grpfld==null||"null".equals(grpfld)){grpfld="";}
//						if(grpfld.length()>Integer.parseInt(fldlth)){
//							grpfld=grpfld.substring(0,Integer.parseInt(fldlth));
//							int oraclelength=getWordCountRegex(grpfld);
//							int temp11=oraclelength-grpfld.length();
//							grpfld=grpfld.substring(0,grpfld.length()-temp11);
//							flag="true";
//						}else if(getWordCountRegex(grpfld)>Integer.parseInt(fldlth)){
//							int oraclelength=getWordCountRegex(grpfld);
//							int temp11=oraclelength-grpfld.length();
//							grpfld=grpfld.substring(0,grpfld.length()-temp11);
//							flag="true";
//						}
////						if(getWordCountRegex(grpfld)>Integer.parseInt(fldlth)){
////							grpfld=grpfld.substring(0,Integer.parseInt(fldlth)*2-getWordCountRegex(grpfld));
////							flag="true";
////						}
//						mapjson.put(a0000, grpfld);
////						updatesql="update "+table_code+" set "+col_code+"='"+grpfld+"' where a0000 = '"+a0000+"'";
////						session.createSQLQuery(updatesql).executeUpdate();
//					}
				}
			}else{
				//System.out.println("循环人员信息开始："+df.format(new Date())); 
				List<HashMap<String, List<HashMap<String, Object>>>> aFLdList=getGrpFldList("",ctci,"",listord,listrcd,listfld,cq,uuid, qrysql);
				for(int i=0;list!=null&&i<list.size();i++){//循环人员
					HashMap<String, Object> map=list.get(i);
					String a0000=(String)map.get("a0000");
					grpfld=getGrpFld("",ctci,a0000,listord,listrcd,listfld,cq,uuid, qrysql,aFLdList);
					if(grpfld==null||"null".equals(grpfld)){grpfld="";}
					if("".equals(grpfld.trim())){
						grpfld="";
					}
					if(grpfld.length()>Integer.parseInt(fldlth)){
						grpfld=grpfld.substring(0,Integer.parseInt(fldlth));
						int oraclelength=getWordCountRegex(grpfld);
						int temp11=oraclelength-grpfld.length();
						grpfld=grpfld.substring(0,grpfld.length()-temp11);
						flag="true";
					}else if(getWordCountRegex(grpfld)>Integer.parseInt(fldlth)){
						int oraclelength=getWordCountRegex(grpfld);
						int temp11=oraclelength-grpfld.length();
						grpfld=grpfld.substring(0,grpfld.length()-temp11);
						flag="true";
					}
//					if(getWordCountRegex(grpfld)>Integer.parseInt(fldlth)){
//						grpfld=grpfld.substring(0,Integer.parseInt(fldlth)*2-getWordCountRegex(grpfld));
//						flag="true";
//					}
					mapjson.put(a0000, grpfld);
//					updatesql="update "+table_code+" set "+col_code+"='"+grpfld+"' where a0000 = '"+a0000+"'";
//					session.createSQLQuery(updatesql).executeUpdate();
				}
			}
			//System.out.println("循环人员信息结束："+df.format(new Date()));
			try{
				String username=" select user from dual";
				List<HashMap<String, Object>> lists=cq.getListBySQL(username);
				username =lists.get(0).get("user").toString();
				lists=cq.getListBySQL("select t.*,i.index_type from user_ind_columns t,user_indexes i "
						+ " where t.index_name = i.index_name "
						+ " and t.table_name='A93Q' "
						+ " and t.COLUMN_NAME='A0000'"
						+ " and TABLE_OWNER='"+username+"' ");
				//尝试建立索引
				if(lists==null||lists.size()==0||lists.get(0).isEmpty()){
					String tablespace="select default_tablespace from dba_users where username='"+username+"'";
					lists=cq.getListBySQL(tablespace);
					tablespace =lists.get(0).get("default_tablespace").toString();
					HBUtil.getHBSession().createSQLQuery("create unique index INDEX_"+table_code+"_A0000 on "+username+".A93Q (A0000) tablespace "+tablespace+" ").executeUpdate();
				}
			}catch(HibernateException e){
				
			}
			//System.out.println("更新人员信息开始："+df.format(new Date()));  
			excuteUpSql(mapjson,table_code,col_code);
			//System.out.println("更新人员信息结束："+df.format(new Date()));
			session.flush();
			temp[0]="true";
			temp[1]=flag;
			System.gc();
		}catch(Exception e){
			e.printStackTrace();
			temp[0]=e.getMessage();
			temp[1]=flag;
		}
		return temp;
	}
	public List<HashMap<String, List<HashMap<String, Object>>>> getGrpFldList(String rsm001,String ctci,String a0000,
			List<HashMap<String, Object>> listord,List<HashMap<String, Object>> listrcd,
			List<HashMap<String, Object>> listfld,CommQuery cq, String uuid,String qrysql) throws AppException{
		List<HashMap<String, List<HashMap<String, Object>>>> listdata=new ArrayList<HashMap<String,List<HashMap<String,Object>>>>();
		for(int i=0;listfld!=null&&i<listfld.size();i++){//循环指标项字段设置 
			String sqlgrpList= getSqlCompList(listfld, i, listord, listrcd, qrysql, a0000);
			List<HashMap<String, Object>> listgrpList=cq.getListBySQL(sqlgrpList);
			HashMap<String, List<HashMap<String, Object>>> map=excuteListToMap(listgrpList);
			listdata.add(map);
		}
		return listdata;
	}
	public HashMap<String, List<HashMap<String, Object>>> excuteListToMap(List<HashMap<String, Object>> listgrpList){
		HashMap<String, List<HashMap<String, Object>>> mapl=new HashMap<String, List<HashMap<String,Object>>>();
		for(HashMap<String, Object> map:listgrpList){
			String a0000=map.get("a0000").toString();
			if(mapl.containsKey(a0000)){
				List<HashMap<String, Object>>  templist=mapl.get(a0000);
				templist.add(map);
			}else{
				List<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
				list.add(map);
				mapl.put(a0000, list);
			}
		}
		return mapl;
	}
	public void excuteUpSql(Map<String,Object> mapjson,String table_code,String col_code) throws SQLException{
		HBSession session=HBUtil.getHBSession();
		java.sql.PreparedStatement pstmt= null;
		String updatesql="";
		Connection con=null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try{
			con=session.connection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement("update "+table_code+" set "+col_code+"=? where a0000 = ? ");
			int i=0;
			for (Map.Entry<String,Object> entry : mapjson.entrySet()) {
				String value=entry.getValue()+"";
				//value=value.replace("\'", "''");
//				updatesql="update "+table_code+" set "+col_code+"='"+value+"' where a0000 = '"+entry.getKey()+"'";
				pstmt.setString(1, value);
				pstmt.setString(2, entry.getKey());
				pstmt.addBatch();
				i++;
				if(i%5000==0){
					pstmt.executeBatch();
					pstmt.clearBatch();
					//System.out.println(df.format(new Date())+" 更新总人数："+i);
				}
			}
			if(i%5000!=0){
				pstmt.executeBatch();
				pstmt.clearBatch();
				//System.out.println(df.format(new Date())+" 更新总人数："+i);
			}
			pstmt.close();
			con.close();
		}catch(Exception e){
			try{
				if(pstmt!=null){
					pstmt.close();
				}
				if(con!=null){
					con.close();
				}
			}catch(Exception e1){ }
			System.out.println(updatesql);
			throw new SQLException(e.getMessage());
		}
	}
	/**
	 * 插入组合字段数据
	 * @return
	 * @throws AppException 
	 */
	@PageEvent("insertGrpData")
	public int insertGrpData() throws AppException{
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String updatesql="";
		try{
			String rsm001=request.getParameter("rsm001");
			String ctci=request.getParameter("ctci");
			String qrysql=HBUtil.getValueFromTab("qrysql", "grprcdsplmd", "rsm001='"+rsm001+"' ");
			String col_code=HBUtil.getValueFromTab("col_code", "code_table_col", "ctci='"+ctci+"' ");
			String fldlth=HBUtil.getValueFromTab("fldlth", "code_table_col", "ctci='"+ctci+"' ");
			String table_code=HBUtil.getValueFromTab("table_code", "code_table_col", "ctci='"+ctci+"' ");
			String insertA000=" insert into "+table_code+" ("+table_code+"00,a0000) (select sys_guid(),a0000 from a01 where a01.a0000 not in (select a0000 from "+table_code+") )";
//			String insertSql="update "+table_code+" set "+col_code+"= ("+qrysql+" where a01.a0000=a80.a0000)";
			HBSession session=HBUtil.getHBSession();
		//	System.out.println("插入新增人员信息开始："+df.format(new Date()));  
			session.createSQLQuery(insertA000).executeUpdate();
		//	System.out.println("插入新增人员信息结束："+df.format(new Date()));  
		//	System.out.println("清空组合字段信息开始："+df.format(new Date()));  
			String cleanFld="update "+table_code+" set "+col_code+"='' ";
			session.createSQLQuery(cleanFld).executeUpdate();
		//	System.out.println("清空组合字段信息结束："+df.format(new Date()));  
		//	System.out.println("查询人员信息开始："+df.format(new Date()));  
			String qrya0000sql="select a0000 from "+table_code+" where a0000 in ("+qrysql+")";
//		
			CommQuery cq=new CommQuery();
			List<HashMap<String, Object>> list=cq.getListBySQL(qrya0000sql);
		//	System.out.println("查询人员信息结束："+df.format(new Date()));  
//			System.out.println("排序查询开始："+df.format(new Date()));  
			String sqlord="select * from grpord where ctci='"+ctci+"' order by ordernum1 asc ";
			List<HashMap<String, Object>> listord=cq.getListBySQL(sqlord);
//			System.out.println("排序查询结束："+df.format(new Date()));  
//			System.out.println("查询sql开始："+df.format(new Date()));  
			String sqlrcd="select * from grprcdsplmd where ctci='"+ctci+"' ";
			List<HashMap<String, Object>> listrcd=cq.getListBySQL(sqlrcd);
			//System.out.println("查询sql结束："+df.format(new Date()));  
			//System.out.println("组合字段开始："+df.format(new Date()));  
			String sqlfld="select * from grpfld where ctci='"+ctci+"' order by ordernum1 asc ";
			List<HashMap<String, Object>> listfld=cq.getListBySQL(sqlfld);
			//System.out.println("组合字段结束："+df.format(new Date()));  
		//	System.out.println("二级代码开始："+df.format(new Date()));  
			String sqlsyb="select code_type||code_value typevalue,code_name2 from code_value where code_type like '%GRP%'";
			List<HashMap<String, Object>> listsyb=cq.getListBySQL(sqlsyb);
			if(mapsyb.size()<=0){
				for(int i=0;i<listsyb.size();i++){
					mapsyb.put((String)listsyb.get(i).get("typevalue"), listsyb.get(i).get("code_name2"));
				}
			}
		//	System.out.println("二级代码结束："+df.format(new Date()));  
			
			Map<String,Object> mapjson =new HashMap<String, Object>();
			String uuid=java.util.UUID.randomUUID().toString();//代替空值
			boolean flag=false;
			if("1".equals(listrcd.get(0).get("slfcfg").toString())){
				String sql="select * from RESULTSPELL where ctci='"+ctci+"'";
				List<HashMap<String, Object>> listexp=cq.getListBySQL(sql);
				if(listexp!=null&&listexp.size()>0){
				//	System.out.println("查询人员信息开始："+df.format(new Date())); 
					List<HashMap<String, Object>> aFLdList=getAFld(rsm001,ctci,qrya0000sql,listord,listrcd,listfld,cq,uuid);
				//	System.out.println("查询人员信息结束："+df.format(new Date())); 
				//	System.out.println("处理人员信息1开始："+df.format(new Date()));  
					Map<String,Object> maptemp =excuteList(aFLdList,listrcd);
					//System.out.println("处理人员信息1结束："+df.format(new Date()));
					//System.out.println("处理人员信息2开始："+df.format(new Date())); 
					for (Map.Entry<String, Object> entry : maptemp.entrySet()) {
						String grpfld="";
						Object o=entry.getValue();
						if(o!=null){
							grpfld=o.toString();
						}
						String a0000=entry.getKey();
						if(grpfld==null){grpfld="";}
						if(grpfld.length()>Integer.parseInt(fldlth)){
							grpfld=grpfld.substring(0,Integer.parseInt(fldlth));
							int oraclelength=getWordCountRegex(grpfld);
							int temp11=oraclelength-grpfld.length();
							grpfld=grpfld.substring(0,grpfld.length()-temp11);
							flag=true;
						}else if(getWordCountRegex(grpfld)>Integer.parseInt(fldlth)){
							int oraclelength=getWordCountRegex(grpfld);
							int temp11=oraclelength-grpfld.length();
							grpfld=grpfld.substring(0,grpfld.length()-temp11);
							flag=true;
						}
						mapjson.put(a0000, grpfld);
						//System.out.println(entry.getKey() + ":" + entry.getValue());
					}
				//	System.out.println("处理人员信息2结束："+df.format(new Date()));
//					System.out.println("循环人员信息开始："+df.format(new Date()));  
//					for(int i=0;list!=null&&i<list.size();i++){//循环人员
//						HashMap<String, Object> map=list.get(i);
//						String a0000=(String)map.get("a0000");
//						String grpfld=getResSpell(rsm001,ctci,a0000,listord,listrcd,listfld,cq,uuid);
//						if(grpfld==null){grpfld="";}
//						if(grpfld.length()>Integer.parseInt(fldlth)){
//							grpfld=grpfld.substring(0,Integer.parseInt(fldlth));
//							int oraclelength=getWordCountRegex(grpfld);
//							int temp11=oraclelength-grpfld.length();
//							grpfld=grpfld.substring(0,grpfld.length()-temp11);
//							flag=true;
//						}else if(getWordCountRegex(grpfld)>Integer.parseInt(fldlth)){
//							int oraclelength=getWordCountRegex(grpfld);
//							int temp11=oraclelength-grpfld.length();
//							grpfld=grpfld.substring(0,grpfld.length()-temp11);
//							flag=true;
//						}
////						if(getWordCountRegex(grpfld)>Integer.parseInt(fldlth)){
////							grpfld=grpfld.substring(0,Integer.parseInt(fldlth)*2-getWordCountRegex(grpfld));
////							flag=true;
////						}
//						mapjson.put(a0000, grpfld);
////						updatesql="update "+table_code+" set "+col_code+"='"+grpfld+"' where a0000 = '"+a0000+"'";
////						session.createSQLQuery(updatesql).executeUpdate();
//					}
				}else{
					this.setMainMessage("请输入结果项sql表达式");
				}
				//System.out.println("循环人员信息结束："+df.format(new Date()));
			}else{
				//System.out.println("循环人员信息开始："+df.format(new Date()));
				List<HashMap<String, List<HashMap<String, Object>>>> aFLdList=getGrpFldList("",ctci,"",listord,listrcd,listfld,cq,uuid, qrysql);
				for(int i=0;list!=null&&i<list.size();i++){//循环人员
					HashMap<String, Object> map=list.get(i);
					String a0000=(String)map.get("a0000");
					String grpfld=getGrpFld(rsm001,ctci,a0000,listord,listrcd,listfld,cq,uuid, qrysql,aFLdList);
					if(grpfld==null){grpfld="";}
					if("".equals(grpfld.trim())){
						grpfld="";
					}
					if(grpfld.length()>Integer.parseInt(fldlth)){
						grpfld=grpfld.substring(0,Integer.parseInt(fldlth));
						int oraclelength=getWordCountRegex(grpfld);
						int temp11=oraclelength-grpfld.length();
						grpfld=grpfld.substring(0,grpfld.length()-temp11);
						flag=true;
					}else if(getWordCountRegex(grpfld)>Integer.parseInt(fldlth)){
						int oraclelength=getWordCountRegex(grpfld);
						int temp11=oraclelength-grpfld.length();
						grpfld=grpfld.substring(0,grpfld.length()-temp11);
						flag=true;
					}
//					if(getWordCountRegex(grpfld)>Integer.parseInt(fldlth)){//判断oralce字段长度
//						grpfld=grpfld.substring(0,Integer.parseInt(fldlth)*2-getWordCountRegex(grpfld));
//						flag=true;
//					}
					mapjson.put(a0000, grpfld);
//					updatesql="update "+table_code+" set "+col_code+"='"+grpfld+"' where a0000 = '"+a0000+"'";
//					session.createSQLQuery(updatesql).executeUpdate();
				}
				//System.out.println("循环人员信息结束："+df.format(new Date()));
			}
		//	System.out.println("更新人员信息开始："+df.format(new Date()));
			try{
				String username=" select user from dual";
				List<HashMap<String, Object>> lists=cq.getListBySQL(username);
				username =lists.get(0).get("user").toString();
				lists=cq.getListBySQL("select t.*,i.index_type from user_ind_columns t,user_indexes i "
						+ " where t.index_name = i.index_name "
						+ " and t.table_name='A93Q' "
						+ " and t.COLUMN_NAME='A0000'"
						+ " and TABLE_OWNER='"+username+"' ");
				//尝试建立索引
				if(lists==null||lists.size()==0||lists.get(0).isEmpty()){
					String tablespace="select default_tablespace from dba_users where username='"+username+"'";
					lists=cq.getListBySQL(tablespace);
					tablespace =lists.get(0).get("default_tablespace").toString();
					HBUtil.getHBSession().createSQLQuery("create unique index INDEX_"+table_code+"_A0000 on "+username+".A93Q (A0000) tablespace "+tablespace+" ").executeUpdate();
				}
			}catch(HibernateException e){
				
			}
			excuteUpSql(mapjson,table_code,col_code);
		//	System.out.println("更新人员信息结束："+df.format(new Date()));
			session.flush();
			if(flag==true){
				this.setSelfDefResData("3@");
			}else{
				this.setSelfDefResData("2@");
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println( updatesql);
			this.setSelfDefResData("1@"+e.getMessage());
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	public Map<String ,Object> excuteList(List<HashMap<String, Object>> list, List<HashMap<String, Object>> listrcd){
		String connector=entryStr(listrcd.get(0).get("connector"));
		HashMap<String ,Object> map=new HashMap<String, Object>();
		for(HashMap<String, Object> mapfld:list){
			if(mapfld!=null&&!mapfld.isEmpty()){
				String codevalue="";
				Object o=mapfld.get("codevalue");
				if(o!=null){
					codevalue=o.toString();
				}
				
				String temp20180801="";
				o=mapfld.get("a0000");
				if(o!=null){
					temp20180801=o.toString();
				}
				if(map.containsKey(temp20180801)){
					map.put(temp20180801, codevalue+connector+map.get(temp20180801));
				}else{
					map.put(temp20180801, codevalue);
				}
			}
		}
		return map;
	}
	public List<HashMap<String ,Object>> getAFld(String rsm001,String ctci,String sqlA0000,
			List<HashMap<String, Object>> listord,List<HashMap<String, Object>> listrcd,
			List<HashMap<String, Object>> listfld,CommQuery cq, String uuid) throws AppException{
		sqlA0000="("+sqlA0000+") temp20180801";
		//String grpfld="";
		String startrow=(String)listrcd.get(0).get("startrow");
		if(startrow.equals("0")){//所有行
			startrow="";
		}else{
			startrow="i>="+startrow;
		}
		String endrow=(String)listrcd.get(0).get("endrow");
		if(endrow.equals("0")){//所有行
			endrow="";
		}else{
			endrow=" and i<="+endrow;
		}
	//	String connector=entryStr(listrcd.get(0).get("connector"));
		String orderby="";
		for(int j=0;listord!=null&&j<listord.size();j++){//循环排序
			String ordertbl=(String)listord.get(j).get("tblcode");
			String orderfld=(String)listord.get(j).get("fldcode");
			if("1".equals(listord.get(j).get("orderby"))){//升序
				orderby=orderby+"  "+ordertbl+"."+orderfld+",";
			}else{//降序
				orderby=orderby+" "+ordertbl+"."+orderfld+" desc ,";
			}
		}
		if(!"".equals(orderby)){
			orderby=" order by "+orderby.substring(0,orderby.length()-1);
		}
		String sql="select * from RESULTSPELL where ctci='"+ctci+"'";
		List<HashMap<String, Object>> listexp=cq.getListBySQL(sql);
		String fldname=listexp.get(0).get("resexpress").toString();
		String condition="";
		String tablename="";
		sql="select distinct(TBLNAME) tablename from grpfld where ctci='"+ctci+"'";
		List<HashMap<String, Object>> listtbl=cq.getListBySQL(sql);
		String flag="";
		for(HashMap<String, Object> map:listtbl){
			String tblname=map.get("tablename").toString();
			if(tblname.toLowerCase().indexOf("b")!=-1){//机构
				condition=condition+"  a02.a0201b="+tblname+".b0111 "
						+ " and "
						;
				flag="true";
			}else{
				condition=condition+"  "
						+ "temp20180801.a0000="+tblname+".a0000 "
						+ " and";
			}
			tablename=tablename+tblname+" ,";
		}
		if(flag.equals("true")){//机构
			if(tablename.toLowerCase().indexOf("a02")==-1){
				tablename=tablename+"a02,";
				condition=condition+" temp20180801.a0000=a02.a0000 and ";
			}
		}
		tablename=tablename+sqlA0000;
		
//		if(!"".equals(tablename)){
//			tablename=tablename.substring(0,tablename.length()-1);
//		}
		if(!"".equals(condition)){
			condition=condition.substring(0,condition.length()-3);
		}
		//sql="select "+fldname+" codevalue from ( select "+fldname+",rownum i from "+tablename+flag+" where "+condition+" ) where "+startrow+" "+endrow+" ";
		//6.21 lzl 修改
		sql="select codevalue,a0000 from ( select "+fldname+" codevalue,temp20180801.a0000,rownum i from "+tablename+" where "+condition+" ) where "+startrow+" "+endrow+" ";
		List<HashMap<String, Object>> list=cq.getListBySQL(sql);
		return list;
	}
	public static  int getWordCountRegex(String s) {  
		if(s!=null){
			s = s.replaceAll("[^\\x00-\\xff]", "**");  
			int length = s.length();  
			return length;  
		}else{
			return 0;
		}
    } 
	//选择is null 或者 is not null
		@PageEvent("setValue1And2Disable")
		public int setValue1And2Disable() throws RadowException{
			clearValue1And2();//清空值 一  值二
			this.createPageElement("conditionName611", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(true);
			return EventRtnType.NORMAL_SUCCESS;
		}
		//清空值 一  值二
		public void clearValue1And2() throws RadowException{
			this.getPageElement("conditionName6").setValue("");
			this.getPageElement("conditionName6_combotree").setValue("");
			this.getPageElement("conditionName61").setValue("");
			this.getPageElement("conditionName611").setValue("");
			this.getPageElement("conditionName6111").setValue("");
			this.getPageElement("conditionName7").setValue("");
			this.getPageElement("conditionName71").setValue("");
			this.getPageElement("conditionName71_combotree").setValue("");
			this.getPageElement("conditionName711").setValue("");
		}
	//结果指标项用sql表达式
	public String getResSpell(String rsm001,String ctci,String a0000,
			List<HashMap<String, Object>> listord,List<HashMap<String, Object>> listrcd,
			List<HashMap<String, Object>> listfld,CommQuery cq, String uuid) throws AppException, ParseException{
		String grpfld="";
		String startrow=(String)listrcd.get(0).get("startrow");
		if(startrow.equals("0")){//所有行
			startrow="";
		}else{
			startrow="i>="+startrow;
		}
		String endrow=(String)listrcd.get(0).get("endrow");
		if(endrow.equals("0")){//所有行
			endrow="";
		}else{
			endrow=" and i<="+endrow;
		}
		String connector=entryStr(listrcd.get(0).get("connector"));
		String orderby="";
		for(int j=0;listord!=null&&j<listord.size();j++){//循环排序
			String ordertbl=(String)listord.get(j).get("tblcode");
			String orderfld=(String)listord.get(j).get("fldcode");
			if("1".equals(listord.get(j).get("orderby"))){//升序
				orderby=orderby+"  "+ordertbl+"."+orderfld+",";
			}else{//降序
				orderby=orderby+" "+ordertbl+"."+orderfld+" desc ,";
			}
		}
		if(!"".equals(orderby)){
			orderby=" order by "+orderby.substring(0,orderby.length()-1);
		}
		String sql="select * from RESULTSPELL where ctci='"+ctci+"'";
		List<HashMap<String, Object>> listexp=cq.getListBySQL(sql);
		String fldname=listexp.get(0).get("resexpress").toString();
		String condition="";
		String tablename="";
		sql="select distinct(TBLNAME) tablename from grpfld where ctci='"+ctci+"'";
		List<HashMap<String, Object>> listtbl=cq.getListBySQL(sql);
		String flag="";
		for(HashMap<String, Object> map:listtbl){
			String tblname=map.get("tablename").toString();
			if(tblname.toLowerCase().indexOf("b")!=-1){//机构
				condition=condition+"  a02.a0201b="+tblname+".b0111 and a02.a0000='"+a0000+"'";
				flag=",a02";
			}else{
				condition=condition+"  a0000='"+a0000+"' and";
			}
			tablename=tablename+tblname+" ,";
		}
		if(!"".equals(tablename)){
			tablename=tablename.substring(0,tablename.length()-1);
		}
		if(!"".equals(condition)){
			condition=condition.substring(0,condition.length()-3);
		}
		//sql="select "+fldname+" codevalue from ( select "+fldname+",rownum i from "+tablename+flag+" where "+condition+" ) where "+startrow+" "+endrow+" ";
		//6.21 lzl 修改
		sql="select codevalue from ( select "+fldname+" codevalue,rownum i from "+tablename+flag+" where "+condition+" ) where "+startrow+" "+endrow+" ";
		List<HashMap<String, Object>> list=cq.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			for(HashMap<String, Object> map:list){
				grpfld=grpfld+map.get("codevalue")+connector;
			}
			grpfld=grpfld.substring(0,grpfld.length()-connector.length());
		}
		return grpfld;
	}
	
//	public String getGrpFld(String rsm001,String ctci,String a0000,
//			List<HashMap<String, Object>> listord,List<HashMap<String, Object>> listrcd,
//			List<HashMap<String, Object>> listfld,CommQuery cq, String uuid,String qrysql) throws AppException, ParseException{
//		String grpfld="";
//		//
//		String endsym="";
//		for(int i=0;listfld!=null&&i<listfld.size();i++){//循环指标项字段设置
//			String tblname=(String)listfld.get(i).get("tblname");//TBLNAME	信息集 表名
//			String fldname=(String)listfld.get(i).get("fldname");//FLDNAME	指标项 字段名
//			//GFID	主键
//			//CTCI	code_table_col表的主键
//			//FLDNAMENOTE	指标项注释 中文字段名
//			//ORDERNUM	序号
//			String codetocomm=(String)listfld.get(i).get("codetocomm");//CODETOCOMM	代码字段转换为描述标志
//			String connsymbol=(String)listfld.get(i).get("connsymbol");//CONNSYMBOL	字段后的连接字符
//			if("null".equals(connsymbol)||connsymbol==null)connsymbol="";
//			if(!"".equals(connsymbol)){
//				connsymbol=(String)mapsyb.get("GRPCNYB"+connsymbol);
//			}
//			String handnull=(String)listfld.get(i).get("handnull");//HANDNULL	值为空时的连接符处理方式
//			String fldnull=(String)listfld.get(i).get("fldnull");//FLDNULL	字段为空的代替符
//			if("null".equals(fldnull)||fldnull==null)fldnull="";
//			if(!"".equals(fldnull)){
//				fldnull=(String)mapsyb.get("GRPNLRP"+fldnull);
//			}
//			String fldfunc=(String)listfld.get(i).get("fldfunc");//FLDFUNC	字段合计函数
//			if("null".equals(fldfunc)||fldfunc==null)fldfunc="";
//			if(!"".equals(fldfunc)){
//				fldfunc=(String)mapsyb.get("GRPCBFC"+fldfunc);
//			}
//			String meregfld=(String)listfld.get(i).get("meregfld");//MEREGFLD	合计同类项字段标志
//			String replacefld=(String)listfld.get(i).get("replacefld");//REPLACEFLD	同类项代替连接符
//			if("null".equals(replacefld)||replacefld==null)replacefld="";
//			if(!"".equals(replacefld)){
//				replacefld=(String)mapsyb.get("GRPSMRP"+replacefld);
//			}
//			String datefmt=(String)listfld.get(i).get("datefmt");//DATEFMT	日期格式化
//			if("null".equals(datefmt)||datefmt==null)datefmt="";
//			if(!"".equals(datefmt)){
//				datefmt=mapsyb.get("GRPDTFM"+datefmt).toString().toLowerCase();
//			}
//			String codetype=(String)listfld.get(i).get("codetype");
//			
//			
//			String startrow=(String)listrcd.get(0).get("startrow");
//			if(startrow.equals("0")){//所有行
//				startrow="";
//			}else{
//				startrow="i>="+startrow;
//			}
//			String endrow=(String)listrcd.get(0).get("endrow");
//			if(endrow.equals("0")){//所有行
//				endrow="";
//			}else{
//				endrow=" and i<="+endrow;
//			}
//			String orderby="";
//			for(int j=0;listord!=null&&j<listord.size();j++){//循环排序
//				String ordertbl=(String)listord.get(j).get("tblcode");
//				if(tblname.toLowerCase().equals(ordertbl.toLowerCase())){
//					String orderfld=(String)listord.get(j).get("fldcode");
//					if("1".equals(listord.get(j).get("orderby"))){//升序
//						orderby=orderby+"  nvl("+ordertbl+"."+orderfld+",999999) asc ,";
//					}else{//降序
//						orderby=orderby+" nvl("+ordertbl+"."+orderfld+",0) desc ,";
//					}
//				}
//			}
//			
//			if(!"".equals(orderby)){
//				orderby=" order by "+orderby.substring(0,orderby.length()-1);
//			}
//			
//			String sqlgrp="";
//			//6.21 lzl修改
//			if("".equals(startrow)&&"".equals(endrow)){
//				if(tblname.toLowerCase().indexOf("b")!=-1){//机构
//					if(!"".equals(fldfunc)){//合计函数
//						sqlgrp="select "+fldfunc+"("+fldname+") "+fldname+" from ( select "+fldname+",a0000 from (select s.*,,rownum i from ( select "+tblname+"."+fldname+",a02.a0000 from "+tblname+",a02 where a02.a0201b="+tblname+".b0111 and a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+")s)) where "+fldname+" is not null group by a0000 ";
//					}else{
//						sqlgrp="select "+fldname+" from ( select "+tblname+"."+fldname+",rownum i from "+tblname+",a02 where a02.a0201b="+tblname+".b0111 and a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+")";
//					}
//				}else{
//					if(!"".equals(fldfunc)){//合计函数
//						sqlgrp="select "+fldfunc+"("+fldname+") "+fldname+" from ( select "+fldname+",a0000 from (select s.*,rownum i from (select "+tblname+"."+fldname+",a0000 from "+tblname+" where a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+" ) s)) where "+fldname+" is not null group by a0000 ";
//					}else{
//						sqlgrp="select "+fldname+" from (select s.*,rownum i from (select "+tblname+"."+fldname+" from "+tblname+" where a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+" ) s)";
//					}
//				}
//			}else{
//				if(tblname.toLowerCase().indexOf("b")!=-1){//机构
//					if(!"".equals(fldfunc)){//合计函数
//						sqlgrp="select "+fldfunc+"("+fldname+") "+fldname+" from ( select "+fldname+",a0000 from (select s.*,rownum i from ( select "+tblname+"."+fldname+",a02.a0000 from "+tblname+",a02 where a02.a0201b="+tblname+".b0111 and a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+")s) where "+startrow+" "+endrow+" ) where "+fldname+" is not null group by a0000 ";
//					}else{
//						sqlgrp="select "+fldname+" from (select s.*,rownum i from ( select "+tblname+"."+fldname+" from "+tblname+",a02 where a02.a0201b="+tblname+".b0111 and a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+")s) where "+startrow+" "+endrow+" ";
//					}
//				}else{
//					if(!"".equals(fldfunc)){//合计函数
//						sqlgrp="select "+fldfunc+"("+fldname+") "+fldname+" from ( select "+fldname+",a0000 from (select s.*,rownum i from (select "+tblname+"."+fldname+",a0000 from "+tblname+" where a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+" )s) where "+startrow+" "+endrow+" ) where "+fldname+" is not null group by a0000 ";
//					}else{
//						sqlgrp="select "+fldname+" from (select s.*,rownum i from (select "+tblname+"."+fldname+" from "+tblname+" where a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+" )s) where "+startrow+" "+endrow+" ";
//					}
//				}
//			}
//			List<HashMap<String, Object>> listgrp=cq.getListBySQL(sqlgrp);
//			if(!"".equals(fldfunc)){//合计函数  则除字段后的连接字符外  其他条件无效
//				String temp="";
//				if(listgrp==null||listgrp.size()==0){
//					temp="0";
//				}else{
//					temp=(String)listgrp.get(0).get(fldname.toLowerCase());
//				}
//				if(!"".equals(connsymbol)){//字段后的连接字符//connsymbol
//					grpfld=grpfld+" "+temp+" "+connsymbol;
//				}else{
//					grpfld=grpfld+" "+temp+" ";
//				}
//			}else{
//				//DATEFMT	日期格式化
//				if(!"".equals(datefmt)){
//					for(int j=0;listgrp!=null&&j<listgrp.size();j++){
//						String fldtemp=(String)listgrp.get(j).get(fldname.toLowerCase());
//						if(!"".equals(fldtemp)&&fldtemp!=null){
//							String tempdate=datefmt.replace("_", "").replace(".", "").replace("-", "");
//							SimpleDateFormat formatter = new SimpleDateFormat(tempdate);
//							Date date=formatter.parse(fldtemp);
//							formatter = new SimpleDateFormat(datefmt);
//						    listgrp.get(j).put(fldname.toLowerCase(), formatter.format(date));
//						}
//					}
//				}
//				if("1".equals(codetocomm)){//代码字段转换为描述标志
//					for(int j=0;listgrp!=null&&j<listgrp.size();j++){
//						String temp=(String)listgrp.get(j).get(fldname.toLowerCase());
//						if(!"".equals(temp)&&temp!=null){
//							String codename=HBUtil.getValueFromTab("code_name", "code_value", " code_type='"+codetype.toUpperCase()+"' and code_value='"+temp+"' ");
//							listgrp.get(j).put(fldname.toLowerCase(), codename);
//						}
//					}
//				}
//				
//				if("1".equals(meregfld)){//MEREGFLD	合计同类项字段标志 
//					//String uuid=java.util.UUID.randomUUID().toString();//代替空值
//					String comstr="";
//					String endtlx="";
//					String endvalue="";
//					//String endhandnull="";
//					for(int j=0;listgrp!=null&&j<listgrp.size();j++){
//						String fldtemp=(String)listgrp.get(j).get(fldname.toLowerCase());
//						if(fldtemp==null)fldtemp="";
//						
//						if("".equals(fldtemp)){//为空
//							fldtemp=uuid;
//							if("1".equals(handnull)){//前后连接符不要
//								if(!"".equals(endtlx)&&!uuid.equals(endvalue)){//上一个字段后有联结符
//									comstr=comstr.substring(0, comstr.length()-endtlx.length())+fldtemp;
//								}else{
//									comstr=comstr+" "+fldtemp;
//								}
//							}else if("2".equals(handnull)){//前连接符不要
//								if(!"".equals(endtlx)&&!uuid.equals(endvalue)){//上一个字段后有联结符
//									comstr=comstr.substring(0, comstr.length()-endtlx.length())+fldtemp+replacefld;
//								}else{
//									comstr=comstr+" "+fldtemp+replacefld;
//								}
//							}else if("3".equals(handnull)){//后连接符不要
//								if(!"".equals(endtlx)){//上一个字段后有联结符
//									comstr=comstr+fldtemp;
//								}else{
//									comstr=comstr+" "+fldtemp;
//								}
//							}
//						}else{
//							comstr=comstr+fldtemp+replacefld;
//						}
//						endvalue=fldtemp;
//						endtlx=replacefld;
//					}
//					
//					
//					if(!"".equals(replacefld)){//REPLACEFLD	同类项替代连接符
//						if("2".equals(handnull)||!uuid.equals(endvalue)){
//							comstr=comstr.substring(0, comstr.length()-replacefld.length());
//						}
//					}
//					if("".equals(comstr.replace(uuid, "").trim())){//HANDNULL	值为空时的连接符处理方式
//						if("1".equals(handnull)){//前后连接符不要
//							int n=grpfld.length();
//							int m=endsym.length();
//							if(!"".equals(endsym)&&n>=m
//									&&endsym.equals(grpfld.substring(n-m, n))){//上一个字段后有联结符
//								grpfld=grpfld.substring(0, grpfld.length()-endsym.length())+comstr;
//							}else{
//								grpfld=grpfld+" "+comstr;
//							}
//						}else if("2".equals(handnull)){//前连接符不要
//							int n=grpfld.length();
//							int m=endsym.length();
//							if(!"".equals(endsym)&&n>=m&&endsym.equals(grpfld.substring(n-m, n))){//上一个字段后有联结符
//								grpfld=grpfld.substring(0, grpfld.length()-endsym.length())+comstr+connsymbol;
//							}else{
//								grpfld=grpfld+" "+comstr+connsymbol;
//							}
//						}else if("3".equals(handnull)){//后连接符不要
//							if(!"".equals(endsym)){//上一个字段后有联结符
//								grpfld=grpfld+comstr;
//							}else{
//								grpfld=grpfld+" "+comstr;
//							}
//						}
//					}else{
//						grpfld=grpfld+comstr+connsymbol;
//					}
//					if(!"".equals(fldnull)){//字段为空的代替符
//						grpfld=grpfld.replace(uuid, fldnull);
//					}else{
//						grpfld=grpfld.replace(uuid, "");
//					}
//				}else{
//					String comstr="";
//					for(int j=0;listgrp!=null&&j<listgrp.size();){
//						String fldtemp=(String)listgrp.get(j).get(fldname.toLowerCase());
//						if(fldtemp==null)fldtemp="";
//						if("".equals(fldtemp)){//为空
//							fldtemp=uuid;
//						}else{
//							comstr=fldtemp;
//						}
//						break;
//					}
//					if("".equals(comstr)||uuid.equals(comstr)){//HANDNULL	值为空时的连接符处理方式
//						if("1".equals(handnull)){//前后连接符不要
//							int n=grpfld.length();
//							int m=endsym.length();
//							if(!"".equals(endsym)&&n>=m&&endsym.equals(grpfld.substring(n-m, n))){//上一个字段后有联结符
//								grpfld=grpfld.substring(0, grpfld.length()-endsym.length())+comstr;
//							}else{
//								grpfld=grpfld+" "+comstr;
//							}
//						}else if("2".equals(handnull)){//前连接符不要
//							int n=grpfld.length();
//							int m=endsym.length();
//							if(!"".equals(endsym)&&n>=m&&endsym.equals(grpfld.substring(n-m, n))){//上一个字段后有联结符
//								grpfld=grpfld.substring(0, grpfld.length()-endsym.length())+comstr+connsymbol;
//							}else{
//								grpfld=grpfld+" "+comstr+connsymbol;
//							}
//						}else if("3".equals(handnull)){//后连接符不要
//							if(!"".equals(endsym)){//上一个字段后有联结符
//								grpfld=grpfld+comstr;
//							}else{
//								grpfld=grpfld+" "+connsymbol+comstr;
//							}
//						}
//					}else{
//						grpfld=grpfld+comstr+connsymbol;
//					}
//					if(!"".equals(fldnull)){//字段为空的代替符
//						grpfld=grpfld.replace(uuid, fldnull);
//					}else{
//						grpfld=grpfld.replace(uuid, "");
//					}
//				}
//			}
//			endsym=connsymbol;
//		}
//		return grpfld;
//	}
	public List<HashMap<String, Object>>  getListFromList(String a0000,int num,
			List<HashMap<String, List<HashMap<String, Object>>>> aFLdList,List<HashMap<String, Object>> listrcd){
		HashMap<String, List<HashMap<String, Object>>> map=aFLdList.get(num);
		List<HashMap<String, Object>> list=map.get(a0000);
		String start=listrcd.get(0).get("startrow")+"";
		String end=listrcd.get(0).get("endrow")+"";
		int s=Integer.parseInt(start);
		int e=Integer.parseInt(end);
		List<HashMap<String, Object>> templist=new ArrayList<HashMap<String,Object>>();
		for(int i=0;list!=null&&i<list.size();i++){
			int n=i+1;
			if((n>=s&&n<=e&&e!=0)||(n>=s&&e==0)){
				templist.add(list.get(i));
			}
		}
		return templist;
	}
	public String getGrpFld(String rsm001,String ctci,String a0000,
			List<HashMap<String, Object>> listord,List<HashMap<String, Object>> listrcd,
			List<HashMap<String, Object>> listfld,CommQuery cq, String uuid,String qrysql,List<HashMap<String, List<HashMap<String, Object>>>> aFLdList) throws AppException, ParseException{
		String grpfld="";
		List<Object> lll=new ArrayList<Object>();
		for(int i=0;listfld!=null&&i<listfld.size();i++){//循环指标项字段设置 
			String fldname=(String)listfld.get(i).get("fldname");//FLDNAME	指标项 字段名
			String codetocomm=(String)listfld.get(i).get("codetocomm");//CODETOCOMM	代码字段转换为描述标志
			String connsymbol=(String)listfld.get(i).get("connsymbol");//CONNSYMBOL	字段后的连接字符
			if("null".equals(connsymbol)||connsymbol==null)connsymbol="";
			if(!"".equals(connsymbol)){
				connsymbol=(String)mapsyb.get("GRPCNYB"+connsymbol);
			}
			String handnull=(String)listfld.get(i).get("handnull");//HANDNULL	值为空时的连接符处理方式
			String fldnull=(String)listfld.get(i).get("fldnull");//FLDNULL	字段为空的代替符
			if("null".equals(fldnull)||fldnull==null)fldnull="";
			if(!"".equals(fldnull)){
				fldnull=(String)mapsyb.get("GRPNLRP"+fldnull);
			}
			String fldfunc=(String)listfld.get(i).get("fldfunc");//FLDFUNC	字段合计函数
			if("null".equals(fldfunc)||fldfunc==null)fldfunc="";
			if(!"".equals(fldfunc)){
				fldfunc=(String)mapsyb.get("GRPCBFC"+fldfunc);
			}
			String meregfld=(String)listfld.get(i).get("meregfld");//MEREGFLD	合计同类项字段标志
			String replacefld=(String)listfld.get(i).get("replacefld");//REPLACEFLD	同类项代替连接符
			if("null".equals(replacefld)||replacefld==null)replacefld="";
			if(!"".equals(replacefld)){
				replacefld=(String)mapsyb.get("GRPSMRP"+replacefld);
			}
			String datefmt=(String)listfld.get(i).get("datefmt");//DATEFMT	日期格式化
			if("null".equals(datefmt)||datefmt==null)datefmt="";
			if(!"".equals(datefmt)){
				datefmt=mapsyb.get("GRPDTFM"+datefmt).toString().toLowerCase();
			}
			String codetype=(String)listfld.get(i).get("codetype");
			
			
//			String sqlgrp= getSqlComp(listfld, i, listord, listrcd, qrysql, a0000);
//			List<HashMap<String, Object>> listgrp=cq.getListBySQL(sqlgrp);
			List<HashMap<String, Object>> listgrp=getListFromList(a0000,i,aFLdList,listrcd);
			List<Object> ll=new ArrayList<Object>();
			if(!"".equals(fldfunc)){//合计函数  则除字段后的连接字符外  其他条件无效
				List<Object> l=new ArrayList<Object>();
				String temp="";
				if(listgrp==null||listgrp.size()==0){
					temp="0";
				}else{
					temp=(String)listgrp.get(0).get(fldname.toLowerCase());
				}
				l.add("count");
				l.add(temp);
				l.add(connsymbol);
				if(!"".equals(connsymbol)){//字段后的连接字符//connsymbol
					grpfld=grpfld+" "+temp+" "+connsymbol;
				}else{
					grpfld=grpfld+" "+temp+" ";
				}
				ll.add(l);
			}else{
				//DATEFMT	日期格式化
				if(!"".equals(datefmt)){
					for(int j=0;listgrp!=null&&j<listgrp.size();j++){
						String fldtemp=(String)listgrp.get(j).get(fldname.toLowerCase());
						if(!"".equals(fldtemp)&&fldtemp!=null){
							String tempdate=datefmt.replace("_", "").replace(".", "").replace("-", "");
							SimpleDateFormat formatter = new SimpleDateFormat(tempdate);
							if(tempdate.length()==8&&fldtemp.length()==6){
								fldtemp=fldtemp+"01";
								Date date=formatter.parse(fldtemp);
								formatter = new SimpleDateFormat(datefmt);
							    listgrp.get(j).put(fldname.toLowerCase(), formatter.format(date));
							}else if(tempdate.length()==8&&fldtemp.length()==8){
								Date date=formatter.parse(fldtemp);
								formatter = new SimpleDateFormat(datefmt);
							    listgrp.get(j).put(fldname.toLowerCase(), formatter.format(date));
							}else if(tempdate.length()==6&&fldtemp.length()==8){
								fldtemp=fldtemp.substring(0, fldtemp.length()-2);
								Date date=formatter.parse(fldtemp);
								formatter = new SimpleDateFormat(datefmt);
							    listgrp.get(j).put(fldname.toLowerCase(), formatter.format(date));
							}else if(tempdate.length()==6&&fldtemp.length()==6){
								Date date=formatter.parse(fldtemp);
								formatter = new SimpleDateFormat(datefmt);
							    listgrp.get(j).put(fldname.toLowerCase(), formatter.format(date));
							}else{
								listgrp.get(j).put(fldname.toLowerCase(),fldtemp );
							}
						}
					}
				}
				if("1".equals(codetocomm)){//代码字段转换为描述标志
					for(int j=0;listgrp!=null&&j<listgrp.size();j++){
						String temp=(String)listgrp.get(j).get(fldname.toLowerCase());
						if(!"".equals(temp)&&temp!=null){
							String codename=HBUtil.getValueFromTab("code_name", "code_value", " code_type='"+codetype.toUpperCase()+"' and code_value='"+temp+"' ");
							listgrp.get(j).put(fldname.toLowerCase(), codename);
						}
					}
				}
				for(int j=0;listgrp!=null&&j<listgrp.size();j++){
					List<Object> l=new ArrayList<Object>();
					String fldtemp=(String)listgrp.get(j).get(fldname.toLowerCase());
					if(fldtemp==null)fldtemp="";
					l.add("fld");
					l.add(fldtemp);
					l.add(connsymbol);
					if("".equals(fldtemp)){
						if("1".equals(handnull)){//前后连接符不要
							l.set(2, "");
							if(lll.size()>0){
								List<Object>  lltemp=((List<Object> )lll.get(lll.size()-1));
								((List<Object> )lltemp.get(lltemp.size()-1)).set(2, "");
//								((List<Object>)ll.get(ll.size()-1)).set(2, "");
							}
						}else if("2".equals(handnull)){//前连接符不要
							if(lll.size()>0){
								List<Object>  lltemp=((List<Object> )lll.get(lll.size()-1));
								((List<Object> )lltemp.get(lltemp.size()-1)).set(2, "");
//								((List<Object>)ll.get(ll.size()-1)).set(2, "");
							}
						}else if("3".equals(handnull)){//后连接符不要
							l.set(2, "");
						}
					}
					ll.add(l);
				}
				
				if("1".equals(meregfld)){
					if(!"".equals(replacefld)){//REPLACEFLD	同类项替代连接符
						compareReplace( ll, replacefld);
					}
				}
				if(!"".equals(fldnull)){//字段为空的代替符
					nullReplace( ll,  fldnull);
				}
			}
			lll.add(ll);
		}
		grpfld=getGrpFld(lll);
		return grpfld;
	}
	public String getGrpFld2(String rsm001,String ctci,String a0000,
			List<HashMap<String, Object>> listord,List<HashMap<String, Object>> listrcd,
			List<HashMap<String, Object>> listfld,CommQuery cq, String uuid,String qrysql) throws AppException, ParseException{
		String grpfld="";
		List<Object> lll=new ArrayList<Object>();
		for(int i=0;listfld!=null&&i<listfld.size();i++){//循环指标项字段设置 
			String fldname=(String)listfld.get(i).get("fldname");//FLDNAME	指标项 字段名
			String codetocomm=(String)listfld.get(i).get("codetocomm");//CODETOCOMM	代码字段转换为描述标志
			String connsymbol=(String)listfld.get(i).get("connsymbol");//CONNSYMBOL	字段后的连接字符
			if("null".equals(connsymbol)||connsymbol==null)connsymbol="";
			if(!"".equals(connsymbol)){
				connsymbol=(String)mapsyb.get("GRPCNYB"+connsymbol);
			}
			String handnull=(String)listfld.get(i).get("handnull");//HANDNULL	值为空时的连接符处理方式
			String fldnull=(String)listfld.get(i).get("fldnull");//FLDNULL	字段为空的代替符
			if("null".equals(fldnull)||fldnull==null)fldnull="";
			if(!"".equals(fldnull)){
				fldnull=(String)mapsyb.get("GRPNLRP"+fldnull);
			}
			String fldfunc=(String)listfld.get(i).get("fldfunc");//FLDFUNC	字段合计函数
			if("null".equals(fldfunc)||fldfunc==null)fldfunc="";
			if(!"".equals(fldfunc)){
				fldfunc=(String)mapsyb.get("GRPCBFC"+fldfunc);
			}
			String meregfld=(String)listfld.get(i).get("meregfld");//MEREGFLD	合计同类项字段标志
			String replacefld=(String)listfld.get(i).get("replacefld");//REPLACEFLD	同类项代替连接符
			if("null".equals(replacefld)||replacefld==null)replacefld="";
			if(!"".equals(replacefld)){
				replacefld=(String)mapsyb.get("GRPSMRP"+replacefld);
			}
			String datefmt=(String)listfld.get(i).get("datefmt");//DATEFMT	日期格式化
			if("null".equals(datefmt)||datefmt==null)datefmt="";
			if(!"".equals(datefmt)){
				datefmt=mapsyb.get("GRPDTFM"+datefmt).toString().toLowerCase();
			}
			String codetype=(String)listfld.get(i).get("codetype");
			
			
			String sqlgrp= getSqlComp(listfld, i, listord, listrcd, qrysql, a0000);
			List<HashMap<String, Object>> listgrp=cq.getListBySQL(sqlgrp);
//			List<HashMap<String, Object>> listgrp=getListFromList(a0000,i,aFLdList,listrcd);
			List<Object> ll=new ArrayList<Object>();
			if(!"".equals(fldfunc)){//合计函数  则除字段后的连接字符外  其他条件无效
				List<Object> l=new ArrayList<Object>();
				String temp="";
				if(listgrp==null||listgrp.size()==0){
					temp="0";
				}else{
					temp=(String)listgrp.get(0).get(fldname.toLowerCase());
				}
				l.add("count");
				l.add(temp);
				l.add(connsymbol);
				if(!"".equals(connsymbol)){//字段后的连接字符//connsymbol
					grpfld=grpfld+" "+temp+" "+connsymbol;
				}else{
					grpfld=grpfld+" "+temp+" ";
				}
				ll.add(l);
			}else{
				//DATEFMT	日期格式化
				if(!"".equals(datefmt)){
					for(int j=0;listgrp!=null&&j<listgrp.size();j++){
						String fldtemp=(String)listgrp.get(j).get(fldname.toLowerCase());
						if(!"".equals(fldtemp)&&fldtemp!=null){
							String tempdate=datefmt.replace("_", "").replace(".", "").replace("-", "");
							SimpleDateFormat formatter = new SimpleDateFormat(tempdate);
							if(tempdate.length()==8&&fldtemp.length()==6){
								fldtemp=fldtemp+"01";
								Date date=formatter.parse(fldtemp);
								formatter = new SimpleDateFormat(datefmt);
							    listgrp.get(j).put(fldname.toLowerCase(), formatter.format(date));
							}else if(tempdate.length()==8&&fldtemp.length()==8){
								Date date=formatter.parse(fldtemp);
								formatter = new SimpleDateFormat(datefmt);
							    listgrp.get(j).put(fldname.toLowerCase(), formatter.format(date));
							}else if(tempdate.length()==6&&fldtemp.length()==8){
								fldtemp=fldtemp.substring(0, fldtemp.length()-2);
								Date date=formatter.parse(fldtemp);
								formatter = new SimpleDateFormat(datefmt);
							    listgrp.get(j).put(fldname.toLowerCase(), formatter.format(date));
							}else if(tempdate.length()==6&&fldtemp.length()==6){
								Date date=formatter.parse(fldtemp);
								formatter = new SimpleDateFormat(datefmt);
							    listgrp.get(j).put(fldname.toLowerCase(), formatter.format(date));
							}else{
								listgrp.get(j).put(fldname.toLowerCase(),fldtemp );
							}
						}
					}
				}
				if("1".equals(codetocomm)){//代码字段转换为描述标志
					for(int j=0;listgrp!=null&&j<listgrp.size();j++){
						String temp=(String)listgrp.get(j).get(fldname.toLowerCase());
						if(!"".equals(temp)&&temp!=null){
							String codename=HBUtil.getValueFromTab("code_name", "code_value", " code_type='"+codetype.toUpperCase()+"' and code_value='"+temp+"' ");
							listgrp.get(j).put(fldname.toLowerCase(), codename);
						}
					}
				}
				for(int j=0;listgrp!=null&&j<listgrp.size();j++){
					List<Object> l=new ArrayList<Object>();
					String fldtemp=(String)listgrp.get(j).get(fldname.toLowerCase());
					if(fldtemp==null)fldtemp="";
					l.add("fld");
					l.add(fldtemp);
					l.add(connsymbol);
					if("".equals(fldtemp)){
						if("1".equals(handnull)){//前后连接符不要
							l.set(2, "");
							if(lll.size()>0){
								List<Object>  lltemp=((List<Object> )lll.get(lll.size()-1));
								((List<Object> )lltemp.get(lltemp.size()-1)).set(2, "");
//								((List<Object>)ll.get(ll.size()-1)).set(2, "");
							}
						}else if("2".equals(handnull)){//前连接符不要
							if(lll.size()>0){
								List<Object>  lltemp=((List<Object> )lll.get(lll.size()-1));
								((List<Object> )lltemp.get(lltemp.size()-1)).set(2, "");
//								((List<Object>)ll.get(ll.size()-1)).set(2, "");
							}
						}else if("3".equals(handnull)){//后连接符不要
							l.set(2, "");
						}
					}
					ll.add(l);
				}
				
				if("1".equals(meregfld)){
					if(!"".equals(replacefld)){//REPLACEFLD	同类项替代连接符
						compareReplace( ll, replacefld);
					}
				}
				if(!"".equals(fldnull)){//字段为空的代替符
					nullReplace( ll,  fldnull);
				}
			}
			lll.add(ll);
		}
		grpfld=getGrpFld(lll);
		return grpfld;
	}
	public String getGrpFld(List<Object> lll){
		String grpfld="";
		int total=0;
		if(lll!=null){
			for(int i=0;i<lll.size();i++){
				List<Object> ll=(List<Object>)lll.get(i);
				int temp=0;
				if(ll!=null){
					temp=ll.size();
				}
				if(temp>total){
					total=temp;
				}
			}
			for(int i=0;i<total;i++){
				String tmpfld=getFld(lll, i+1);
				grpfld=grpfld+tmpfld;
			}
		}
		return grpfld;
	}
	
	public String getFld(List<Object> lll,int m){
		String fld="";
		for(int i=0;i<lll.size();i++){
			List<Object> ll=(List<Object>)lll.get(i);
			if(ll!=null&&ll.size()>=m){
				List<Object> l=(List<Object> )ll.get(m-1);
				fld=fld+l.get(1)+l.get(2);
			}
		}
		return fld;
	}
	public int nullReplace(List<Object> ll, String fldnull){
		for(int i=0;i<ll.size();i++){
			List<Object> l=(List<Object>)ll.get(i);
			if("fld".equals(l.get(0))){
				String fldtemp=(String)l.get(1);
				if(fldtemp==null||fldtemp.equals("")){
					((List<Object>)ll.get(i)).set(1, fldnull);
				}
			}
		}
		return 1;
	}
	public int compareReplace(List<Object> ll,String replacefld){
		for(int j=ll.size()-1;j>0;j--){
			List<Object> l=(List<Object>)ll.get(j);
			if("fld".equals(l.get(0))){
				String fldtemp=(String)l.get(1);
				for(int m=0;m<ll.size();m++){
					List<Object> lt=(List<Object>)ll.get(j);
					if("fld".equals(lt.get(0))){
						String ft=(String)lt.get(1);
						if(ft.equals(fldtemp)){
							((List<Object>)ll.get(j)).set(1, replacefld);
							break;
						}
					}
				}
			}
		}
		
		return 1;
	}
	public String getSqlCompList(List<HashMap<String, Object>> listfld,int i,
			List<HashMap<String, Object>> listord,
			List<HashMap<String, Object>> listrcd,String qrysql,String a0000) throws AppException{
		String tblname=(String)listfld.get(i).get("tblname");//TBLNAME	信息集 表名
		String fldname=(String)listfld.get(i).get("fldname");//FLDNAME	指标项 字段名
		String connsymbol=(String)listfld.get(i).get("connsymbol");//CONNSYMBOL	字段后的连接字符
		if("null".equals(connsymbol)||connsymbol==null)connsymbol="";
		if(!"".equals(connsymbol)){
			connsymbol=(String)mapsyb.get("GRPCNYB"+connsymbol);
		}
		String fldnull=(String)listfld.get(i).get("fldnull");//FLDNULL	字段为空的代替符
		if("null".equals(fldnull)||fldnull==null)fldnull="";
		if(!"".equals(fldnull)){
			fldnull=(String)mapsyb.get("GRPNLRP"+fldnull);
		}
		String fldfunc=(String)listfld.get(i).get("fldfunc");//FLDFUNC	字段合计函数
		if("null".equals(fldfunc)||fldfunc==null)fldfunc="";
		if(!"".equals(fldfunc)){
			fldfunc=(String)mapsyb.get("GRPCBFC"+fldfunc);
		}
		String replacefld=(String)listfld.get(i).get("replacefld");//REPLACEFLD	同类项代替连接符
		if("null".equals(replacefld)||replacefld==null)replacefld="";
		if(!"".equals(replacefld)){
			replacefld=(String)mapsyb.get("GRPSMRP"+replacefld);
		}
		String datefmt=(String)listfld.get(i).get("datefmt");//DATEFMT	日期格式化
		if("null".equals(datefmt)||datefmt==null)datefmt="";
		if(!"".equals(datefmt)){
			datefmt=mapsyb.get("GRPDTFM"+datefmt).toString().toLowerCase();
		}
		
		
		String startrow=(String)listrcd.get(0).get("startrow");
		if(startrow.equals("0")){//所有行
			startrow="";
		}else{
			startrow="i>="+startrow;
		}
		String endrow=(String)listrcd.get(0).get("endrow");
		if(endrow.equals("0")){//所有行
			endrow="";
		}else{
			endrow=" and i<="+endrow;
		}
		String orderby="";
		for(int j=0;listord!=null&&j<listord.size();j++){//循环排序
			String ordertbl=(String)listord.get(j).get("tblcode");
			if(tblname.toLowerCase().equals(ordertbl.toLowerCase())){
				String orderfld=(String)listord.get(j).get("fldcode");
				if("1".equals(listord.get(j).get("orderby"))){//升序
					orderby=orderby+"  nvl("+ordertbl+"."+orderfld+",999999) asc ,";
				}else{//降序
					orderby=orderby+" nvl("+ordertbl+"."+orderfld+",0) desc ,";
				}
			}
		}
		
		if(!"".equals(orderby)){
			orderby=" "+orderby.substring(0,orderby.length()-1);
			orderby=","+orderby;
		}
		
		String sqlgrp="";
		startrow="";
		endrow="";
		//6.21 lzl修改
//		if("".equals(startrow)&&"".equals(endrow)){
			if(tblname.toLowerCase().indexOf("b")!=-1){//机构
				if(!"".equals(fldfunc)){//合计函数
					sqlgrp="select "+fldfunc+"("+fldname+") "+fldname+",a0000 from ( select "+fldname+",a0000 from (select s.*,rownum i from ( select "+tblname+"."+fldname+",a02.a0000 from "+tblname+",a02,("+qrysql+") temps where a02.a0000=temps.a0000 and a02.a0201b="+tblname+".b0111 "+getCondition( tblname, qrysql)+" order by a02.a0000"+orderby+")s)) where "+fldname+" is not null group by a0000 ";
				}else{
					sqlgrp="select "+fldname+",a0000 from ( select "+tblname+"."+fldname+",rownum i,a02.a0000 from "+tblname+",a02,("+qrysql+") temps  where a02.a0000=temps.a0000 and a02.a0201b="+tblname+".b0111 "+getCondition( tblname, qrysql)+"  order by a02.a0000"+orderby+")";
				}
			}else{
				if(!"".equals(fldfunc)){//合计函数
					sqlgrp="select "+fldfunc+"("+fldname+") "+fldname+",a0000 from ( select "+fldname+",a0000 from (select s.*,rownum i from (select "+tblname+"."+fldname+","+tblname+".a0000 from "+tblname+",("+qrysql+") temps where "+tblname+".a0000=temps.a0000 "+getCondition( tblname, qrysql)+" order by "+tblname+".a0000"+orderby+" ) s)) where "+fldname+" is not null group by a0000 ";
				}else{
					sqlgrp="select "+fldname+",a0000 from (select s.*,rownum i from (select "+tblname+"."+fldname+","+tblname+".a0000 from "+tblname+",("+qrysql+") temps where  "+tblname+".a0000=temps.a0000 "+getCondition( tblname, qrysql)+" order by "+tblname+".a0000"+orderby+" ) s)";
				}
			}
//		}else{
//			if(tblname.toLowerCase().indexOf("b")!=-1){//机构
//				if(!"".equals(fldfunc)){//合计函数
//					sqlgrp="select "+fldfunc+"("+fldname+") "+fldname+",a0000 from ( select "+fldname+",a0000 from (select s.*,rownum i from ( select "+tblname+"."+fldname+",a02.a0000 from "+tblname+",a02,("+qrysql+") temps where a02.a0000=temps.a0000 and a02.a0201b="+tblname+".b0111  "+getCondition( tblname, qrysql)+" order by a02.a0000,"+orderby+")s) where "+startrow+" "+endrow+" ) where "+fldname+" is not null group by a0000 ";
//				}else{
//					sqlgrp="select "+fldname+",a0000 from (select s.*,rownum i from ( select "+tblname+"."+fldname+",a02.a0000 from "+tblname+",a02,("+qrysql+") temps  where a02.a0201b="+tblname+".b0111 and a02.a0000=temps.a0000 "+getCondition( tblname, qrysql)+" order by a02.a0000,"+orderby+")s) where "+startrow+" "+endrow+" ";
//				}
//			}else{
//				if(!"".equals(fldfunc)){//合计函数
//					sqlgrp="select "+fldfunc+"("+fldname+"),a0000 "+fldname+" from ( select "+fldname+",a0000 from (select s.*,rownum i from (select "+tblname+"."+fldname+","+tblname+".a0000 from "+tblname+",("+qrysql+") temps  where "+tblname+".a0000=temps.a0000 "+getCondition( tblname, qrysql)+" order by "+tblname+".a0000,"+orderby+" )s) where "+startrow+" "+endrow+" ) where "+fldname+" is not null group by a0000 ";
//				}else{
//					sqlgrp="select "+fldname+",a0000 from (select s.*,rownum i from (select "+tblname+"."+fldname+",temps.a0000 from "+tblname+",("+qrysql+") temps where temps.a0000="+tblname+".a0000 "+getCondition( tblname, qrysql)+" order by "+tblname+".a0000,"+orderby+" )s) where "+startrow+" "+endrow+" ";
//				}
//			}
//		}
		return sqlgrp;
	}
	public String getSqlComp(List<HashMap<String, Object>> listfld,int i,
			List<HashMap<String, Object>> listord,
			List<HashMap<String, Object>> listrcd,String qrysql,String a0000) throws AppException{
		String tblname=(String)listfld.get(i).get("tblname");//TBLNAME	信息集 表名
		String fldname=(String)listfld.get(i).get("fldname");//FLDNAME	指标项 字段名
		String connsymbol=(String)listfld.get(i).get("connsymbol");//CONNSYMBOL	字段后的连接字符
		if("null".equals(connsymbol)||connsymbol==null)connsymbol="";
		if(!"".equals(connsymbol)){
			connsymbol=(String)mapsyb.get("GRPCNYB"+connsymbol);
		}
		String fldnull=(String)listfld.get(i).get("fldnull");//FLDNULL	字段为空的代替符
		if("null".equals(fldnull)||fldnull==null)fldnull="";
		if(!"".equals(fldnull)){
			fldnull=(String)mapsyb.get("GRPNLRP"+fldnull);
		}
		String fldfunc=(String)listfld.get(i).get("fldfunc");//FLDFUNC	字段合计函数
		if("null".equals(fldfunc)||fldfunc==null)fldfunc="";
		if(!"".equals(fldfunc)){
			fldfunc=(String)mapsyb.get("GRPCBFC"+fldfunc);
		}
		String replacefld=(String)listfld.get(i).get("replacefld");//REPLACEFLD	同类项代替连接符
		if("null".equals(replacefld)||replacefld==null)replacefld="";
		if(!"".equals(replacefld)){
			replacefld=(String)mapsyb.get("GRPSMRP"+replacefld);
		}
		String datefmt=(String)listfld.get(i).get("datefmt");//DATEFMT	日期格式化
		if("null".equals(datefmt)||datefmt==null)datefmt="";
		if(!"".equals(datefmt)){
			datefmt=mapsyb.get("GRPDTFM"+datefmt).toString().toLowerCase();
		}
		
		
		String startrow=(String)listrcd.get(0).get("startrow");
		if(startrow.equals("0")){//所有行
			startrow="";
		}else{
			startrow="i>="+startrow;
		}
		String endrow=(String)listrcd.get(0).get("endrow");
		if(endrow.equals("0")){//所有行
			endrow="";
		}else{
			endrow=" and i<="+endrow;
		}
		String orderby="";
		for(int j=0;listord!=null&&j<listord.size();j++){//循环排序
			String ordertbl=(String)listord.get(j).get("tblcode");
			if(tblname.toLowerCase().equals(ordertbl.toLowerCase())){
				String orderfld=(String)listord.get(j).get("fldcode");
				if("1".equals(listord.get(j).get("orderby"))){//升序
					orderby=orderby+"  nvl("+ordertbl+"."+orderfld+",999999) asc ,";
				}else{//降序
					orderby=orderby+" nvl("+ordertbl+"."+orderfld+",0) desc ,";
				}
			}
		}
		
		if(!"".equals(orderby)){
			orderby=" order by "+orderby.substring(0,orderby.length()-1);
		}
		
		String sqlgrp="";
		//6.21 lzl修改
		if("".equals(startrow)&&"".equals(endrow)){
			if(tblname.toLowerCase().indexOf("b")!=-1){//机构
				if(!"".equals(fldfunc)){//合计函数
					sqlgrp="select "+fldfunc+"("+fldname+") "+fldname+" from ( select "+fldname+",a0000 from (select s.*,,rownum i from ( select "+tblname+"."+fldname+",a02.a0000 from "+tblname+",a02 where a02.a0201b="+tblname+".b0111 and a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+")s)) where "+fldname+" is not null group by a0000 ";
				}else{
					sqlgrp="select "+fldname+" from ( select "+tblname+"."+fldname+",rownum i from "+tblname+",a02 where a02.a0201b="+tblname+".b0111 and a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+")";
				}
			}else{
				if(!"".equals(fldfunc)){//合计函数
					sqlgrp="select "+fldfunc+"("+fldname+") "+fldname+" from ( select "+fldname+",a0000 from (select s.*,rownum i from (select "+tblname+"."+fldname+",a0000 from "+tblname+" where a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+" ) s)) where "+fldname+" is not null group by a0000 ";
				}else{
					sqlgrp="select "+fldname+" from (select s.*,rownum i from (select "+tblname+"."+fldname+" from "+tblname+" where a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+" ) s)";
				}
			}
		}else{
			if(tblname.toLowerCase().indexOf("b")!=-1){//机构
				if(!"".equals(fldfunc)){//合计函数
					sqlgrp="select "+fldfunc+"("+fldname+") "+fldname+" from ( select "+fldname+",a0000 from (select s.*,rownum i from ( select "+tblname+"."+fldname+",a02.a0000 from "+tblname+",a02 where a02.a0201b="+tblname+".b0111 and a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+")s) where "+startrow+" "+endrow+" ) where "+fldname+" is not null group by a0000 ";
				}else{
					sqlgrp="select "+fldname+" from (select s.*,rownum i from ( select "+tblname+"."+fldname+" from "+tblname+",a02 where a02.a0201b="+tblname+".b0111 and a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+")s) where "+startrow+" "+endrow+" ";
				}
			}else{
				if(!"".equals(fldfunc)){//合计函数
					sqlgrp="select "+fldfunc+"("+fldname+") "+fldname+" from ( select "+fldname+",a0000 from (select s.*,rownum i from (select "+tblname+"."+fldname+",a0000 from "+tblname+" where a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+" )s) where "+startrow+" "+endrow+" ) where "+fldname+" is not null group by a0000 ";
				}else{
					sqlgrp="select "+fldname+" from (select s.*,rownum i from (select "+tblname+"."+fldname+" from "+tblname+" where a0000='"+a0000+"' "+getCondition( tblname, qrysql)+" "+orderby+" )s) where "+startrow+" "+endrow+" ";
				}
			}
		}
		return sqlgrp;
	}
	public String [] replacTo(String []arrfh,String condition){
		condition=condition.replaceAll("\'", "");
		condition=condition.replaceAll("\\.", "");
		String [] arrtj=null;
		for(int i=0;i<arrfh.length;i++){
			String temp=arrfh[i].replaceAll("\'", "");
			temp=temp.replaceAll("\\.", "");
			condition=condition.replace(temp, "@@");
		}
		arrtj=condition.split("@@");
		return arrtj;
	}
	public String getCondition(String table,String sql) throws AppException{
		String temp="";
		int j=0;
		String ragexfh="[a][n][d]|[o][r]";
		//String ragextj="[^and]&[^or]";
		if(sql!=null&&!"".equals(sql)){
			int index=sql.indexOf("(");
			if(index!=-1){
				String condition=sql.substring(index, sql.length());
				String []arrfh=condition.split(ragexfh);
				String [] arrtj=replacTo(arrfh,condition);
				for(int i=0;arrfh!=null&&i<arrfh.length;i++){
					if(arrfh[i].indexOf(table.toLowerCase()+".")!=-1){
						if(j==0){
							temp=temp+" "+arrfh[i];
						}else{
							temp=temp+" "+arrtj[i]+" "+arrfh[i];
						}
						j++;
					}
				}
			}
		}
		int left=count(temp, "(");
		int right=count(temp, ")");
		if(left>right){
			for(int i=0;i<left-right;i++){
				temp=temp+")";
			}
		}else if(right>left){
			for(int i=0;i<right-left;i++){
				temp="("+temp;
			}
		}
		if(j>0){
			temp=" and "+temp;
		}
		return temp;
	}
	
	public static int count(String s, String key) {  
	  int count=0;  
	  int d=0;  
	  while((d=s.indexOf(key,d))!=-1){  
		  d=d+1;
	      //s=s.substring(d+key.length());  
	      count++;  
	  }      
	    return count;  
	}  
	
	@PageEvent("checkHighCon")
	public int checkHighCon() throws RadowException, AppException{
		String value=this.getPageElement("expressionid").getValue();
		if(value==null||value.trim().equals("")||value.trim().equals("null")){
			return EventRtnType.NORMAL_SUCCESS;
		}
		Grid grid=(Grid)this.getPageElement("content2");//选择信息集
		List<HashMap<String, Object>> list=grid.getValueList();
		if(list==null||list.size()<1){
			return EventRtnType.NORMAL_SUCCESS;
		}
		String table_codes="";
		for(int i=0;i<list.size();i++){
			String checked=list.get(i).get("change")+"";
			if("true".equals(checked)){
				table_codes=table_codes+""+list.get(i).get("table_code")+",";
			}
		}
		if("".equals(table_codes)){
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(table_codes.toLowerCase().indexOf("a01")==-1){//必须存在a01
			table_codes=table_codes+"a01,";
		}
		if(table_codes.toLowerCase().indexOf("b01")!=-1){//存在b01 则必须存在a02
			if(table_codes.toLowerCase().indexOf("a02")==-1){
				table_codes=table_codes+"a02,";
			}
		}
		table_codes=table_codes.substring(0, table_codes.length()-1);
		String sql="select count(1) from "+table_codes+"";
		String where=" where 1=1 ";
		for(int i=0;i<list.size();i++){
			String checked=list.get(i).get("change")+"";
			if("true".equals(checked)){
				String table_code=list.get(i).get("table_code")+"";
				if(!"b".equals(table_code.substring(0, 1).toLowerCase())){
					where=where+" and a01.a0000="+table_code+".a0000 ";
				}else{
					where=where+" and a02.a0201b="+table_code+".b0111 ";
				}
			}
		}
		sql=sql+where+" and "+value;
		try{
			new CommQuery().getListBySQL(sql);
		}catch(AppException e){
			this.setMainMessage("表达式语法出错!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		addtolistfunc();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 加载高级查询说明
	 * @return
	 * @throws AppException 
	 */
	@PageEvent("loadSm")
	public int loadSm(String str) throws AppException{
		String arr[]=str.split("@@");
		String sm=HBUtil.getValueFromTab("", "", "");
		return EventRtnType.NORMAL_SUCCESS;
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
	/*6.21 lzl修改
	 * 更新所有的组合字段值
	 */
	@PageEvent("insertAllGrpData")
	public int getAllGrpData() throws RadowException,AppException{
		List<String> list = new ArrayList<String>();
		String data = request.getParameter("params");
		String a0000 = request.getParameter("a0000");
		if(a0000.indexOf(",")!=-1){
			String[] str = a0000.split(",");
			for(int i=0;i<str.length;i++){
				list.add(str[i]);
			}
		}else{
			list.add(a0000);
		}
		if(data != null && !"".equals(data)){
			JSONArray jsonArr = JsonUtil.toJSONArray(data);
			for(int i=0;i<jsonArr.size();i++){
				JSONObject o = (JSONObject) jsonArr.get(i);
				String rsm001 = (String) o.get("rsm001");
				String ctci = (String) o.get("ctci");
				insertGrpData(rsm001,ctci,list);
			}
			
		}
		//System.out.println(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	/*6.21 lzl修改
	 * 复写生成组合字段保存方法，用来保存rmb是的组合字段
	 */
	public int insertGrpData(String rsm001,String ctci,List<String> a0000List) throws AppException{
		String updatesql="";
		try{
			//String rsm001=request.getParameter("rsm001");
			//String ctci=request.getParameter("ctci");
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<a0000List.size();i++){
				sb.append("'").append(a0000List.get(i)).append("'").append(",");
			}
			String a0000Id = sb.toString().substring(0, sb.toString().length()-1);
			String qrysql=HBUtil.getValueFromTab("qrysql", "grprcdsplmd", "rsm001='"+rsm001+"' ");
			String col_code=HBUtil.getValueFromTab("col_code", "code_table_col", "ctci='"+ctci+"' ");
			String col_name=HBUtil.getValueFromTab("col_name", "code_table_col", "ctci='"+ctci+"' ");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		//	System.out.println("保存任免表--更新组合字段开始："+col_code+"."+col_name+" "+df.format(new Date()));
			String fldlth=HBUtil.getValueFromTab("fldlth", "code_table_col", "ctci='"+ctci+"' ");
			String table_code=HBUtil.getValueFromTab("table_code", "code_table_col", "ctci='"+ctci+"' ");
			String insertA000=" insert into "+table_code+" ("+table_code+"00,a0000) (select sys_guid(),a0000 from a01 where a01.a0000 not in (select a0000 from "+table_code+") and a01.a0000 in ("+a0000Id+") )";
			HBSession session=HBUtil.getHBSession();
			session.createSQLQuery(insertA000).executeUpdate();
			String qrya0000sql="select a0000 from a01 where a0000 in ("+qrysql+") and a01.a0000 in ("+a0000Id+") ";
			CommQuery cq=new CommQuery();
			List<HashMap<String, Object>> list=cq.getListBySQL(qrya0000sql);
			String sqlord="select * from grpord where ctci='"+ctci+"' order by ordernum1 asc ";
			List<HashMap<String, Object>> listord=cq.getListBySQL(sqlord);
			String sqlrcd="select * from grprcdsplmd where ctci='"+ctci+"' ";
			List<HashMap<String, Object>> listrcd=cq.getListBySQL(sqlrcd);
			String sqlfld="select * from grpfld where ctci='"+ctci+"' order by ordernum1 asc ";
			List<HashMap<String, Object>> listfld=cq.getListBySQL(sqlfld);
			String sqlsyb="select code_type||code_value typevalue,code_name2 from code_value where code_type like '%GRP%'";
			List<HashMap<String, Object>> listsyb=cq.getListBySQL(sqlsyb);
			if(mapsyb.size()<=0){
				for(int i=0;i<listsyb.size();i++){
					mapsyb.put((String)listsyb.get(i).get("typevalue"), listsyb.get(i).get("code_name2"));
				}
			}
			String uuid=java.util.UUID.randomUUID().toString();//代替空值
			if("1".equals(listrcd.get(0).get("slfcfg").toString())){
				String sql="select * from RESULTSPELL where ctci='"+ctci+"'";
				List<HashMap<String, Object>> listexp=cq.getListBySQL(sql);
				if(listexp!=null&&listexp.size()>0){
					//System.out.println("循环人员信息开始"+df.format(new Date()));
					for(int i=0;list!=null&&i<list.size();i++){//循环人员
						HashMap<String, Object> map=list.get(i);
						String a0000=(String)map.get("a0000");
						if(a0000List.contains(a0000)){
							String grpfld=getResSpell(rsm001,ctci,a0000,listord,listrcd,listfld,cq,uuid);
							if(grpfld==null){grpfld="";}
							if(grpfld.length()>Integer.parseInt(fldlth)){
								grpfld=grpfld.substring(0,Integer.parseInt(fldlth));
								int oraclelength=getWordCountRegex(grpfld);
								int temp11=oraclelength-grpfld.length();
								grpfld=grpfld.substring(0,grpfld.length()-temp11);
							}else if(getWordCountRegex(grpfld)>Integer.parseInt(fldlth)){
								int oraclelength=getWordCountRegex(grpfld);
								int temp11=oraclelength-grpfld.length();
								grpfld=grpfld.substring(0,grpfld.length()-temp11);
							}
							updatesql="update "+table_code+" set "+col_code+"='"+grpfld+"' where a0000 = '"+a0000+"'";
							session.createSQLQuery(updatesql).executeUpdate();							
						}
					}
					//System.out.println("循环人员信息结束"+df.format(new Date()));
				}else{
					this.setMainMessage("请输入结果项sql表达式");
				}
			}else{
				//System.out.println("查询人员信息并转换开始"+df.format(new Date()));
				//List<HashMap<String, List<HashMap<String, Object>>>> aFLdList=getGrpFldList("",ctci,"",listord,listrcd,listfld,cq,uuid, qrysql);
				//System.out.println("查询人员信息并转换结束"+df.format(new Date()));
				//System.out.println("循环人员信息开始"+df.format(new Date()));
				for(int i=0;list!=null&&i<list.size();i++){//循环人员
					HashMap<String, Object> map=list.get(i);
					String a0000=(String)map.get("a0000");
					if(a0000List.contains(a0000)){
						String grpfld=getGrpFld2(rsm001,ctci,a0000,listord,listrcd,listfld,cq,uuid,qrysql);
						if(grpfld==null){grpfld="";}
						if("".equals(grpfld.trim())){
							grpfld="";
						}
						if(grpfld.length()>Integer.parseInt(fldlth)){
							grpfld=grpfld.substring(0,Integer.parseInt(fldlth));
							int oraclelength=getWordCountRegex(grpfld);
							int temp11=oraclelength-grpfld.length();
							grpfld=grpfld.substring(0,grpfld.length()-temp11);
						}else if(getWordCountRegex(grpfld)>Integer.parseInt(fldlth)){
							int oraclelength=getWordCountRegex(grpfld);
							int temp11=oraclelength-grpfld.length();
							grpfld=grpfld.substring(0,grpfld.length()-temp11);
						}
						updatesql="update "+table_code+" set "+col_code+"='"+grpfld+"' where a0000 = '"+a0000+"'";
						session.createSQLQuery(updatesql).executeUpdate();
													
					}
				}
				//System.out.println("循环人员信息结束"+df.format(new Date()));
			}
			//System.out.println("保存任免表--更新组合字段结束："+col_code+"."+col_name+" "+df.format(new Date()));
			session.flush();
			this.setSelfDefResData("2@");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println( updatesql);
			this.setSelfDefResData("1@"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	

}
