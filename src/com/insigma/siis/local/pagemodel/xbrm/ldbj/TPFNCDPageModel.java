package com.insigma.siis.local.pagemodel.xbrm.ldbj;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.commform.hibernate.HUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.JsYjtj;
import com.insigma.siis.local.business.helperUtil.CommonSQLUtil;
import com.insigma.siis.local.pagemodel.cadremgn.util.SqlToMapUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class TPFNCDPageModel extends PageModel{

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
	
	public HashMap<String , String[]> mapBtn=new HashMap<String, String[]>();
	@Override
	public int doInit() throws RadowException {
		//设置条件设置中，保存按钮不可编辑
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(true);
		//设置条件设置中，预览按钮不可编辑
		this.createPageElement("btn5", ElementType.BUTTON, false).setDisabled(true);
		//值二
		this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(true);
		this.getExecuteSG().addExecuteCode("document.getElementById('conditionName71_combotree').disabled=true; ");
//		this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(true);
		this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(true);
		//组合条件
		this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//）
		this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);//并且
		this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);//或者
		
		this.setNextEventName("loadtable");
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
	
	@PageEvent("setValue111Disable")
	public int setValue111Disable(){
		this.createPageElement("conditionName611", ElementType.BUTTON, false).setDisabled(false);//
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
		//this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(false);//或者
		//((Combo)this.getPageElement("conditionName71")).setValueListForSelect(getMap(code_type)); 
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initPage")
	public int initPageInfo() throws RadowException, AppException{
		String qvid=this.getPageElement("qvid").getValue();
		if(qvid!=null&&!qvid.trim().equals("")&&!qvid.trim().equals("null")){
			CommQuery cq=new CommQuery();
			//String txtareaarr="";//存储所有添加条件 用于显示条件
			String sql=""
					+ "select "
					+ " 1 tyle,"//
					+ " quid, "//	主键
					+ " qvid, "//	视图id or 组合字段 ctci
					+ " sort, "//	条件顺序
					+ " fldname, "//	字段名
					+ " fldcode, "//	代码
					+ " tblname, "//	表名
					+ " valuename1, "//	值一中文名
					+ " valuecode1, "//	值一代码
					+ " lable_type code_type, "//	字段类型 T时间C字符串(文本)N数字S下拉选
					+ " valuecode2, "//	值二代码
					+ " valuename2, "//	值二中文名
					+ " q.sign, "//	符号
					+ " (select code_name from code_value where code_type='OPERATOR' and code_value=q.sign) signname "
					+ " from JS_YJTJ_use q where qvid='"+qvid+"' " + " order by sort asc " ;
			List<HashMap<String, Object>> list=cq.getListBySQL(sql);//3个条件具体信息
			
			String zhtj=HBUtil.getValueFromTab("JS_YJTJ.conditions", "JS_YJTJ", "qvid='"+qvid+"' ");// 1.并且. 2.并且. 3
			this.getExecuteSG().addExecuteCode("cleanInfo();");
			this.getPageElement("conditionName9").setValue(zhtj);
			this.getPageElement("zhtj").setValue(zhtj);
			
			if(list!=null&&list.size()>0){
				String jsonstr=JSONArray.fromObject(list).toString();
				//jsonstr=jsonstr.replace("\'", "$");
				this.getExecuteSG().addExecuteCode("initPageInfo('"+jsonstr+"');");
			}else{
				this.setNextEventName("initListAndButton");
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initListAndButton")
	public int initListAndButton() throws RadowException, AppException{
		String qvid=this.getPageElement("qvid").getValue();
		if(qvid!=null&&!qvid.trim().equals("")&&!qvid.trim().equals("null")){
			//初始化列表勾 -- 信息集
			String sql=" select tblname from ("
					+ " select tblname from js_yjtj_fld where qvid='"+qvid+"'"
					+ " union all"
					+ " select tblname from JS_YJTJ_use where qvid='"+qvid+"' "
					+ " ) tt group by tblname ";
			CommQuery cq=new CommQuery();
			List<HashMap<String, Object>> list=cq.getListBySQL(sql);
			String tableInfos="";
			for(int i=0;list!=null&&i<list.size();i++){
				tableInfos=tableInfos+list.get(i).get("tblname")+",";
			}
			//初始化列表勾 -- 信息项 【形式A01,A0101@A01,A0104@A01,A0107@A01,A0111@A01,A0114@】
			List<HashMap<String, Object>> listfld=cq.getListBySQL("select tblname,fldname from js_yjtj_fld where qvid='"+qvid+"'");
			String flds="";
			for(int i=0;listfld!=null&&i<listfld.size();i++){
				flds=flds+listfld.get(i).get("tblname")+","+listfld.get(i).get("fldname")+"@";
			}
			if(!"".equals(tableInfos)){
				tableInfos=tableInfos.substring(0, tableInfos.length()-1);
				flds=flds.substring(0, flds.length()-1);
				this.getExecuteSG().addExecuteCode("initGridChecked('"+tableInfos+"','"+flds+"');");
			}
			//初始化按钮是否可编辑
		}
		String conditionName9=this.getPageElement("conditionName9").getValue();
		if(conditionName9==null||conditionName9.trim().equals("")||conditionName9.trim().equals("null")){
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			this.createPageElement("btnm3", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("btnm4", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("editbtnm", ElementType.BUTTON, false).setDisabled(true);
			
			this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(false);
			this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(false);
		}
		String querysql=HBUtil.getValueFromTab("qrysql", "JS_YJTJ", "qvid='"+qvid+"'");
		if(querysql!=null&&!querysql.trim().equals("")&&!querysql.trim().equals("null")){
			this.getPageElement("querysql").setValue(querysql);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//选择is null 或者 is not null
	@PageEvent("setValue1And2Disable")
	public int setValue1And2Disable() throws RadowException{
		clearValue1And2();//清空值 一  值二
		//this.createPageElement("conditionName6", ElementType.SELECT, false).setDisabled(true);
		//this.createPageElement("conditionName61", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("conditionName611", ElementType.BUTTON, false).setDisabled(true);
		//this.createPageElement("conditionName6111", ElementType.BUTTON, false).setDisabled(true);
		//this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(true);
		//this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(true);
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
			setAllDis();//设置全部删除按钮，不可编辑
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
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
	 * 设置保存预览可编辑
	 * @return
	 */
	@PageEvent("setDisalbe")
	public int setDisalbe(){
		//设置条件设置中，保存按钮可编辑
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(false);
		//设置条件设置中，预览按钮可编辑
		this.createPageElement("btn5", ElementType.BUTTON, false).setDisabled(false);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 加载信息集列表
	 * @return
	 */
	@PageEvent("loadtable")
	public int loadtable() throws RadowException{
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			
		}else{
			//instr= " and table_code in (select t.table_code from competence_usertable t where t.userid = '"+userid+"')";
		}
		try{
			CommQuery cq=new CommQuery();
			String sql=""
					+ " select "
					+ " table_code tblcod,"//
					+ " concat(concat(table_code,' '),table_name) tblcpt "//
					+ " from code_table"
					+ " where 1=1 "
					+ instr
					+ " order by table_code asc ";
			List<HashMap<String, Object>> list=cq.getListBySQL(sql);
			this.getPageElement("tableList2Grid").setValueList(list);
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 值1 下拉选，赋值
	 * @returnreplaceSpecial
	 * @throws RadowException 
	 */
	@PageEvent("selectValue1List")
	public int selectValue1List(String code_type) throws RadowException{
		try{
			this.getExecuteSG().addExecuteCode("setTree('"+code_type+"');");
			//((Combo)this.getPageElement("conditionName6")).setValueListForSelect(getMap(code_type)); 
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public Map<String, Object> getMap(String code_type) throws AppException{
		String sql="";
		CommQuery commQuery = new CommQuery();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if(code_type!=null&&"b01".equals(code_type.toLowerCase())){//机构数据取，b01
			sql="select t.b0114,t.b0101,t.b0111 from b01 t "
					//+ " where  t.b0111='"+code_type.trim()+"' "
					+ " order by t.b0111 asc ";
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
			sql="select t.code_value,t.code_name from CODE_VALUE t "
					+ " where  t.code_type='"+code_type.trim()+"' "
					+ " order by t.code_value asc ";
			List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
			for(int i=0;i<list2.size(); i++){
				map.put(replaceSpecial(list2.get(i).get("code_value").toString()),replaceSpecial(list2.get(i).get("code_value").toString())+" "+replaceSpecial(list2.get(i).get("code_name").toString()));
			}
		}
		return map;
	}
	
	public static String replaceSpecial(String str){
		String temp=str
		.replace("\"", "")  
		.replace("\'", "")  
		.replace("\\", "")
		.replace("/", "")
		.replace("\n", "")  
		.replace("\r", "")
//		.replace(",", "")
//		.replace("，", "")
		;
		return temp;
	}
	
	@PageEvent("modifyTable")
	public int modifyTable(){
		//清空信息
		this.setNextEventName("clearValue");
		//加载信息
		this.setNextEventName("tabletofld");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 5行->num=4;
	 */
	//实现选择预选项
	@PageEvent("checkClickCode")
	public int checkClickCode(String num) throws RadowException{
		checkCode(num,false);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("checkClickCodeAll")
	public int checkClickCodeAll() throws RadowException{
		checkCode("",true);
		return 1;
	}
	public int checkCode(String num,boolean flag ) throws RadowException{
		Grid gridCode = (Grid) this.getPageElement("codeList2Grid");//未选项
		List<HashMap<String, Object>> listCode = gridCode.getValueList();
		Grid gridCode1 = (Grid) this.getPageElement("codeList2Grid1");//预选项
		List<HashMap<String, Object>> listCode1 = gridCode1.getValueList();
		if(flag==false){
				String n=num;
				if(null==n||"0".equals(n)){n="0";}//解决：grid 第一行返回null
				int rownum = Integer.parseInt(n);
				HashMap<String,Object> hashMap1 = listCode.get(rownum);
				hashMap1.put("weizhi", n);
				listCode.remove(Integer.parseInt(n));//未选项 -- 移除
				listCode1.add(hashMap1);//预选项 -- 增加
		}else{
			int size=listCode.size();
			for(int i=0;i<size;i++){
				HashMap<String,Object> hashMap1 = listCode.get(i);
				hashMap1.put("weizhi", i);
				listCode1.add(hashMap1);//预选项 -- 增加
			}
			for(int i=(size-1);i>=0;i--){
				listCode.remove(i);//未选项 -- 移除
			}
		}
		this.getPageElement("codeList2Grid").setValueList(listCode);
		this.getPageElement("codeList2Grid1").setValueList(listCode1);
		return 1;
	}
	
	//实现移除预选项
	@PageEvent("delThisOne")
	public int delThisOne(String num) throws RadowException{
		Grid gridCode = (Grid) this.getPageElement("codeList2Grid");//未选项
		List<HashMap<String, Object>> listCode = gridCode.getValueList();
		Grid gridCode1 = (Grid) this.getPageElement("codeList2Grid1");//预选项
		List<HashMap<String, Object>> listCode1 = gridCode1.getValueList();
		if(null==num||"0".equals(num)){num="0";}//解决：grid 第一行返回null
		int rownum = Integer.parseInt(num);
		HashMap<String,Object> hashMap1 = listCode1.get(rownum);
		String weizhi = (String)hashMap1.get("weizhi");
		listCode1.remove(Integer.parseInt(num));//预选项  -- 移除
		listCode.add(hashMap1);//未选项-- 增加至最后
		this.getPageElement("codeList2Grid").setValueList(listCode);
		this.getPageElement("codeList2Grid1").setValueList(listCode1);
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	
	/**
	 * 根据选中信息集，查询信息项
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("tabletofld")
	public int tabletofld() throws RadowException{
		String qvid=this.getPageElement("qvid").getValue();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			
		}else{
			//instr=" and col_code in ( select t.col_code from COMPETENCE_USERTABLECOL t where t.userid = '"+userid+"' ) ";
		}
		try{
			String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();//当前用户ID
			String parenttablename=this.getPageElement("parenttablename").getValue();
			String flds = this.getPageElement("flds").getValue();//获取已选信息集与信息项 【形式A01,A0101@A01,A0104@A01,A0107@A01,A0111@A01,A0114@】
			String[] split = flds.split("@");
			String[] splitnull = null;
			String col_code = "'1'";//以保存的信息项
			if(split.length==0||(split.length==1&&flds.length()==0)){
				
			}else{
				for (String s : split) {
					splitnull = s.split(",");
					col_code = col_code + "'" + splitnull[1] + "',";
				}
				col_code=col_code.substring(0, col_code.length()-1).replace("'1'", "");
			}
			CommQuery cq=new CommQuery();
			if(parenttablename==null||parenttablename.trim().equals("")||parenttablename.equals("null")){
				Grid grid = (Grid)this.getPageElement("tableList2Grid");
				List<HashMap<String, Object>> list=grid.getValueList();
				if(list==null||list.size()==0){
					this.setMainMessage("数据异常，检测不到信息集!");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String str="";
				for(int i=0;i<list.size();i++){
					if("true".equals(list.get(i).get("checked")+"")){
						str=str+"'"+list.get(i).get("tblcod").toString().trim()+"'"+",";
					}
				}
				if("".equals(str)){//无选中表时
					String sql=" select concat(concat(concat(concat(table_code,'.'),col_code),' '),col_name) col_name1,"//
							+ " col_name,"
							+ " code_type,"//指标项
							+ " col_data_type_should,"//
							+ " col_data_type,"//青岛对应列数据类型
							+ " col_code,"//指标项对应的表的对应列
							+ " table_code "//信息表编码,用来关联CODE_TABLE
							+ " from code_table_col "
							+ " where "
							/*+ " ISUSE=1 and "*/
							/*青岛特定数据库
							 * + " ISLOOK='1' and "*/
							+ " 1=2 order by table_code , col_code asc ";
					List<HashMap<String, Object>> listfld=cq.getListBySQL(sql);
					this.getPageElement("codeList2Grid").setValueList(listfld);
					sql=" select concat(concat(concat(concat(table_code,'.'),col_code),' '),col_name) col_name1,"//
							+ " col_name,"
							+ " code_type,"//指标项
							+ " col_data_type_should,"//
							+ " col_data_type,"//青岛对应列数据类型
							+ " col_code,"//指标项对应的表的对应列
							+ " table_code "//信息表编码,用来关联CODE_TABLE
							+ " from code_table_col "
							+ " where 1<>1";
					List<HashMap<String, Object>> listfld2=cq.getListBySQL(sql);
					this.getPageElement("codeList2Grid1").setValueList(listfld2);
					return EventRtnType.NORMAL_SUCCESS;
				}else{
					//这里处理两个部分，预选信息项与未选信息项
					str=str.substring(0, str.length()-1);
					
				
					if(!flds.isEmpty()){
						String sqlnew=" select concat(concat(concat(concat(c.table_code,'.'),c.col_code),' '),c.col_name) col_name1,"
								+ " c.col_name, c.code_type, c.col_data_type_should, c.col_code, c.col_data_type,"
								+ " c.table_code, q.FLDNUM from js_yjtj_fld q left join code_table_col c  on c.COL_CODE=q.FLDNAME"
								+ " where 1 = 1 and "
								/*+ " ISUSE = 1 and "*/
								/*青岛特定数据库
								 * + " ISLOOK='1' and "*/
								+ " c.table_code in ("+str+") and q.QVID='"+qvid+"' " + instr
								+ " order by q.fldnum asc";
						List<HashMap<String, Object>> listfldnew=cq.getListBySQL(sqlnew);
						this.getPageElement("codeList2Grid1").setValueList(listfldnew);
					}else{//防治添加/去除信息集时，去掉已选信息项
						Grid gridCode1 = (Grid) this.getPageElement("codeList2Grid1");//预选项
						List<HashMap<String, Object>> listCode1 = gridCode1.getValueList();
						if(str.length()>2){
							String splTables = str.replace("'", " ");//注意这里使用空格分隔
							for (int i = 0; i < listCode1.size(); i++) {
								if(splTables.indexOf(" "+(String)listCode1.get(i).get("table_code").toString()+" ")>-1){
									col_code = col_code + "'" + listCode1.get(i).get("col_code") + "',";
								}else{
									listCode1.remove(i);
									continue;
								}
							}
							this.getPageElement("codeList2Grid1").setValueList(listCode1);
						}
						if(col_code.length()>3){
							col_code=col_code.substring(0, col_code.length()-1).replace("'1'", "");
						}
					}
					
					
					/* 未选项 */
					String sqlold="select concat(concat(concat(concat(table_code,'.'),col_code),' '),col_name) col_name1,"
							+ " col_name,code_type, "
							+ " col_data_type_should,col_code,"
							+ " col_data_type,"//青岛对应列数据类型
							+ " table_code from code_table_col "
							+ " where 1=1 and "
							/*+ " ISUSE=1 and "*/
							/* 青岛特定数据库字段
							 * + " ISLOOK='1' and "*/
							+ " table_code in ("+str+") and "
							+ " col_code NOT IN  ("+col_code+") "
							+ instr + " order by table_code , col_code asc";
					List<HashMap<String, Object>> listfldold=cq.getListBySQL(sqlold);
					this.getPageElement("codeList2Grid").setValueList(listfldold);

				}
			}else{////人员信息批量修改 功能调用
				//BatchChange
				String arr[]=parenttablename.split("@");
				if(arr.length>1){
					String changeOrDel=arr[0];//控制删除或修改权限
					parenttablename=arr[1];//获取信息集
					String sql="";
					if(parenttablename==null||parenttablename.trim().equals("")||parenttablename.equals("null")){
						sql="select * from dual where 1=2 ";
					}else{
						//if(("40288103556cc97701556d629135000f").equals(cueUserid)){
							//系统管理员有全部权限
							sql=" select concat(concat(concat(concat(table_code,'.'),col_code),' '),col_name) col_name1,"
									+ " col_name,code_type, "
									+ " col_data_type_should,"
									+ " col_data_type,"//青岛对应列数据类型
									+ " col_code,table_code "
									+ " from code_table_col "
									+ " where "
									/*+ " ISUSE=1 and "*/
									/*青岛特定数据库
									 * + " ISLOOK='1' and "*/
									+ " table_code in ("+parenttablename+") order by table_code , col_code asc";
						/*}else{
							sql=" select concat(concat(concat(concat(ctc.table_code,'.'),ctc.col_code),' '),ctc.col_name) col_name1,"
									+ " ctc.col_name,ctc.code_type, "
									+ " ctc.col_data_type_should,"
									+ " ctc.col_data_type,"//青岛对应列数据类型
									+ " ctc.col_code,ctc.table_code "
									+" from code_table_col ctc"
									+ " left join competence_usertablecol cul on  ctc.table_code=cul.table_code and ctc.col_code=cul.col_code  "
									+ " where "
									+ " ctc.ISLOOK='1' and "
									+ " cul."+changeOrDel+"='1' and cul.userid = '"+cueUserid+"'"
									+ "and ctc.table_code  in ("+parenttablename+") order by ctc.table_code , ctc.col_code asc";
						}*/
					}	
					List<HashMap<String, Object>> listfld=cq.getListBySQL(sql);
					this.getPageElement("codeList2Grid").setValueList(listfld);
				}else{
					//tab1没有勾选时置空tab2的指标项
					this.getPageElement("codeList2Grid").setValueList(null);		
				}
			}
			
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
		CommQuery commQuery = new CommQuery();
		String sql="select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
				+ " where t.code_value not like '%between%' "
				+ " and t.sub_code_value!='11' "
				+ " and t.code_type='OPERATOR' ";
		List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<list2.size(); i++){
			map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
		}
		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
		
		this.getExecuteSG().addExecuteCode("setconditionName4();");//给条件指标项赋值
		return EventRtnType.NORMAL_SUCCESS;
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
			CommQuery commQuery = new CommQuery();
			String sql="select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
					+ " where t.code_value not like '%like%' "
					+ " and t.sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			for(int i=0;i<list2.size(); i++){
				map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
			}
			((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
			
			this.getExecuteSG().addExecuteCode("setconditionName4();");//给条件指标项赋值
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 条件符号下拉选，赋值
	 * 有between and  有like 
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("selectValueListLike")
	public int selectValueListLike() throws AppException, RadowException{
		try{
			conditionclear();//清除条件
			CommQuery commQuery = new CommQuery();
			String sql="select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
					+ " where t.sub_code_value!='11' "
					+ " and t.code_type='OPERATOR'";
			List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			for(int i=0;i<list2.size(); i++){
				map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
			}
			((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
			
			this.getExecuteSG().addExecuteCode("setconditionName4();");//给条件指标项赋值
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
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
		CommQuery commQuery = new CommQuery();
		String sql="select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
				+ " where t.sub_code_value!='11' "
				+ " and t.code_type='OPERATOR' ";
		List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<list2.size(); i++){
			map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
		}
		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
		
		selectValue1List(code_type);//值一
		this.getExecuteSG().addExecuteCode("setconditionName4();");//给条件指标项赋值
		return EventRtnType.NORMAL_SUCCESS;
	}
	
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
		CommQuery commQuery = new CommQuery();
		String sql="select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
				+ " where t.sub_code_value!='11' "
				+ " and t.code_type='OPERATOR' ";
		List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<list2.size(); i++){
			map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
		}
		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
		
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
		this.getPageElement("conditionName6").setValue("");//值一
		this.getPageElement("conditionName6_combotree").setValue("");//值一
		this.getPageElement("conditionName61").setValue("");//值一
		this.getPageElement("conditionName611").setValue("");//值一
		this.getPageElement("conditionName6111").setValue("");//值一
		this.getPageElement("conditionName7").setValue("");//值二
		this.getPageElement("conditionName71").setValue("");//值二
		this.getPageElement("conditionName71_combotree").setValue("");//值二
		this.getPageElement("conditionName711").setValue("");//值二
		this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(true);//或者
		this.getExecuteSG().addExecuteCode("document.getElementById('conditionName71_combotree').disabled=true; ");
		//this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(true);//或者
		this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(true);//或者
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
		
		//值一 conditionName6 select
		//String conditionName6=this.getPageElement("conditionName6").getValue();
		//值一 conditionName61 dateEdit
		//String conditionName61=this.getPageElement("conditionName61").getValue();
		//值一conditionName611 textEdit 
		//String conditionName611=this.getPageElement("conditionName611").getValue();
		//值一conditionName6111 numberEdit
		//String conditionName6111=this.getPageElement("conditionName6111").getValue();
		//值二conditionName7 dateEdit
		//String conditionName7=this.getPageElement("conditionName7").getValue();
		//值二conditionName71 textEdit
		//String conditionName71=this.getPageElement("conditionName71").getValue();
		//值二conditionName711 numberEdit
		//String conditionName711=this.getPageElement("conditionName711").getValue();
		String col_data_type_should=this.getPageElement("col_data_type_should").getValue();//存储的数据类型
		String code_type=this.getPageElement("code_type").getValue();//下拉参数
		String col_data_type=this.getPageElement("col_data_type").getValue();//显示的控件类型
		if(col_data_type_should==null||col_data_type_should.trim().length()==0){
			this.setMainMessage("请双击指标项！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		col_data_type_should=col_data_type_should.toLowerCase();
		String conditionName4=this.getPageElement("col_name").getValue();//隐藏的指标项 中文名称 
		//隐藏的指标项 代码
		String conditionName5=this.getPageElement("conditionName5").getValue();//条件符号
		if(conditionName5==null||conditionName5.trim().equals("")||"null".equals(conditionName5.trim())){
			this.setMainMessage("请选择条件符号!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//条件符号 中文
		String conditionName51=HUtil.getValueFromTab("code_name", "code_value", "code_type='OPERATOR' and code_value='"+conditionName5+"' ");
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
				qryusestr=conditionName4.replace(",", "")//字段名
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
				qryusestr=conditionName4.replace(",", "")//字段名
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
				qryusestr=conditionName4.replace(",", "")//字段名
						+","+col_code//代码
						+","+table_code//表名
						+","+value11//值一中文名
						+","+value1//值一代码
						+","+"S"//字段类型 T时间C字符串(文本)N数字S下拉选
						+","+""//值二代码
						+","+""//值二中文名
						+","+conditionName5//符号
						;
			}
			
		}else if("date".equals(col_data_type_should.toLowerCase())||"t".equals(col_data_type)){//值一 date
			value2=this.getPageElement("conditionName7").getValue();
			value1=this.getPageElement("conditionName61").getValue();
			if(conditionName5.indexOf("between")!=-1){
				qryusestr=conditionName4.replace(",", "")//字段名
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
				qryusestr=conditionName4.replace(",", "")//字段名
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
				qryusestr=conditionName4.replace(",", "")//字段名
						+","+col_code//代码
						+","+table_code//表名
						+","+" "//值一中文名
						+","+value1//值一代码
						+","+"T"//字段类型 T时间C字符串(文本)N数字S下拉选
						+","+" "//值二代码
						+","+" "//值二中文名
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
				qryusestr=conditionName4.replace(",", "")//字段名
						+","+col_code//代码
						+","+table_code//表名
						+","+""//值一中文名
						+","+""//值一代码
						+","+"C"//字段类型 T时间C字符串(文本)N数字S下拉选
						+","+""//值二代码
						+","+""//值二中文名
						+","+conditionName5//符号
						;
			}else{
				qryusestr=conditionName4.replace(",", "")//字段名
						+","+col_code//代码
						+","+table_code//表名
						+","+""//值一中文名
						+","+value1.replace(",", "").replace("@", "")//值一代码
						+","+"C"//字段类型 T时间C字符串(文本)N数字S下拉选
						+","+""//值二代码
						+","+""//值二中文名
						+","+conditionName5//符号
						;
			}
		}else if("number".equals(col_data_type_should)){//number
			value2=this.getPageElement("conditionName711").getValue();
			value1=this.getPageElement("conditionName6111").getValue();
			if(conditionName5.indexOf("between")!=-1){
				qryusestr=conditionName4.replace(",", "")//字段名
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
				qryusestr=conditionName4.replace(",", "")//字段名
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
				qryusestr=conditionName4.replace(",", "")//字段名
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
		this.getPageElement("qryusestr").setValue(qryusestr);
		if(code_type!=null&&!code_type.trim().equals("")&&!code_type.equals("null")){
			if(conditionName5.indexOf("between")!=-1){
				this.getPageElement("conditoionlist").setValue(conditionName4+"  "+conditionName51+"  "+value11+" , "+value21);
			}else if(conditionName5.indexOf("null")!=-1){
				this.getPageElement("conditoionlist").setValue(conditionName4+"  "+conditionName51+"");
			}else{
				this.getPageElement("conditoionlist").setValue(conditionName4+"  "+conditionName51+"  "+value11);
			}
		}else{
			if(conditionName5.indexOf("between")!=-1){
				this.getPageElement("conditoionlist").setValue(conditionName4+"  "+conditionName51+"  "+value1+" , "+value2);
			}else if(conditionName5.indexOf("null")!=-1){
				this.getPageElement("conditoionlist").setValue(conditionName4+"  "+conditionName51+"");
			}else{
				this.getPageElement("conditoionlist").setValue(conditionName4+"  "+conditionName51+"  "+value1);
			}
		}
		this.getExecuteSG().addExecuteCode("textareadd();");//添加到列表
		//conditionStr
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 设置全部删除按钮，不可编辑
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
	 * 条件设置保存
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("saveFunc")
	public int saveFunc(String v) throws RadowException{
		Statement  stmt =null;
		Connection connection =null;
		String qvid="";
		String qvsql="";
		String paramurl="";//自定义查询参数
		try{
			paramurl=this.getPageElement("paramurl").getValue();
			qvid=this.getPageElement("qvid").getValue();
			
			qvsql=HBUtil.getValueFromTab("qrysql", "JS_YJTJ", "qvid='"+qvid+"'");
			
			Grid gridCode = (Grid)this.getPageElement("codeList2Grid");//未选条件
			List<HashMap<String, Object>> listCode=gridCode.getValueList();
			Grid gridCode1 = (Grid)this.getPageElement("codeList2Grid1");//预选条件
			List<HashMap<String, Object>> listCode1=gridCode1.getValueList();
			if((listCode==null&&listCode1==null)||(listCode.size()==0&&listCode1.size()==0)){
				this.setMainMessage("请选择信息集!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(listCode1==null||listCode1.size()==0){
				this.setMainMessage("请选择信息项!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//组合条件
			String zhtj=this.getPageElement("zhtj").getValue();
		
			String qrysql=getSql();//生成sql
			//创建sql对应的视图
			HBSession hbsess = HBUtil.getHBSession();	
			connection = hbsess.connection();
			stmt =connection.createStatement();
			try{
				String tmepsql=qrysql+ " and 1=2 ";
				stmt.executeQuery(tmepsql);
				stmt.close();
			}catch(SQLException e){
				e.printStackTrace();
				this.setMainMessage("生成的语句语法出错，请检查添加的条件! "+e.getMessage());
				return EventRtnType.NORMAL_SUCCESS;
			}finally{
				try{
					if(stmt!=null){
						stmt.close();
					}
					if(connection!=null){
						connection.close();
					}
				}catch(Exception e2){
					e2.printStackTrace();
				}
			}
			
			//构建全程纪实选人时公务员库，事业库等使用的sql。
			String qrysql_2=getSql2();//生成sql
			//创建sql对应的视图
			connection = hbsess.connection();
			stmt =connection.createStatement();
			try{
				String tmepsql=qrysql_2+ " and 1=2 ";
				System.out.println(qrysql_2);
				stmt.executeQuery(tmepsql);
				stmt.close();
			}catch(SQLException e){
				e.printStackTrace();
				this.setMainMessage("生成的语句语法出错，请检查添加的条件! "+e.getMessage());
				return EventRtnType.NORMAL_SUCCESS;
			}finally{
				try{
					if(stmt!=null){
						stmt.close();
					}
					if(connection!=null){
						connection.close();
					}
				}catch(Exception e2){
					e2.printStackTrace();
				}
			}
			
			HBSession hbSession = HBUtil.getHBSession();
			JsYjtj jsYjtj=(JsYjtj)hbSession.get(JsYjtj.class, qvid);
			jsYjtj.setQrysql(qrysql);
			jsYjtj.setQrysql2(qrysql_2);
			jsYjtj.setConditions(zhtj);
			hbSession.update(jsYjtj);//保存sql到视图表中
			hbSession.flush();
			stmt =connection.createStatement();
			
			//保存信息项到自定义视图指标项表中 a0000,a0101 两个字段必须保存，不选中，则默认保存
			boolean a0000flag=false;
			boolean a0101flag=false;
			stmt.execute("delete from js_yjtj_fld where qvid='"+qvid+"' ");
			String col_code,table_code,col_name,sql,fldnum;
			for(int i=0;i<listCode1.size();i++){
				col_code=(String)listCode1.get(i).get("col_code");
				
				table_code=(String)listCode1.get(i).get("table_code");
				fldnum = (i+1) +"";
				if("a0000".equals(col_code.trim().toLowerCase())&&"a01".equals(table_code.trim().toLowerCase())){
					a0000flag=true;
					fldnum = "0";
				}
				if("a0101".equals(col_code.trim().toLowerCase())&&"a01".equals(table_code.trim().toLowerCase())){
					a0101flag=true;
					fldnum = "1";
				}
				col_name=(String)listCode1.get(i).get("col_name");
				//INSERT INTO employees (employee_id, name) VALUES (1, 'Zhangsan');  
				sql="insert into js_yjtj_fld (qvfid,qvid,tblname,fldname,fldnamenote,fldnum ) "
						+ " values ("+CommonSQLUtil.UUID()+",'"+qvid+"','"+table_code+"','"+col_code+"','"+col_name+"','"+fldnum+"')";
				stmt.execute(sql);
			}
			if(a0000flag==false){
				String sqlInsertA0000="insert into js_yjtj_fld (qvfid,qvid,tblname,fldname,fldnamenote,fldnum ) "
						+ " values ("+CommonSQLUtil.UUID()+",'"+qvid+"','"+"A01"+"','"+"A0000"+"','"+"人员统一标识符"+"','0')";
				stmt.execute(sqlInsertA0000);
			}
			if(a0101flag==false){
				String sqlInsertA0101="insert into js_yjtj_fld (qvfid,qvid,tblname,fldname,fldnamenote,fldnum ) "
						+ " values ("+CommonSQLUtil.UUID()+",'"+qvid+"','"+"A01"+"','"+"A0101"+"','"+"姓名"+"','0')";
				stmt.execute(sqlInsertA0101);
			}
			/*保存条件记录*/
			String allqryusestr=this.getPageElement("allqryusestr").getValue();
			String arrAt[]=null;//所有记录
			if(allqryusestr!=null&&!allqryusestr.trim().equals("")){
				arrAt=allqryusestr.split("@");
			}
			stmt.execute("delete from JS_YJTJ_use where qvid='"+qvid+"' ");
			String arrone[];
			String sqlInsertQryuse;
			for(int i=0;arrAt!=null&&i<arrAt.length;i++){
				arrone=arrAt[i].split(",");
//				String uuid=UUID.randomUUID().toString();
				sqlInsertQryuse="insert into JS_YJTJ_use (quid,qvid,sort,fldname,fldcode,"
						+ "tblname,valuename1,valuecode1,lable_type,valuecode2,valuename2,sign ) "
						+ " values ("+CommonSQLUtil.UUID()+",'"+qvid+"','"+(i+1)+"','"+arrone[0]+"','"+arrone[1]+"'"
								+ ",'"+arrone[2]+"','"+arrone[3]+"','"+arrone[4]+"',"
										+ "'"+arrone[5]+"','"+arrone[6]+"','"+arrone[7]+"','"+arrone[8]+"')";
				stmt.execute(sqlInsertQryuse);
			}
			stmt.close();
			//connection.commit();
			connection.close();
			if(paramurl!=null&&paramurl.trim().length()>0&&!paramurl.equals("null")){//自定义查询（人员信息查询tab）
				//保存成功则隐藏qvid到父页面，父页面根据qvid判断是否保存
				this.getExecuteSG().addExecuteCode("parent.document.getElementById('qvid').value='"+qvid+"';");
			}
			if(qvsql==null||qvid.trim().equals("qvsql")||qvid.trim().equals("")){//新增视图
					this.toastmessage("保存成功!");
					this.getExecuteSG().addExecuteCode("parent.refreshViewListGrid();");
				
				
			}else{
					this.toastmessage("修改成功!");
					this.getExecuteSG().addExecuteCode("parent.refreshViewListGrid();");
				
			}
		}catch(Exception e){
			e.printStackTrace();
			try{
	          if(stmt!=null){
	        	  stmt.close();
	          }
	          if(connection!=null){
	        	  connection.close();
	          }
			}catch(Exception e1){
				
			}
			
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * 父页面获取sql
	 * @parenthiddenid 父页面隐藏的id 
	 * @functionname父 页面的方法名称
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("getSqlParent")
	public int getSqlParent(String strarr) throws RadowException{
		String arr[]=strarr.split(",");
		String parentid=arr[0];
		String functionname=arr[1];
		String sql=getSql();
		sql=sql.replace("\'", "@@");
		this.getExecuteSG().addExecuteCode("parent.document.getElementById('"+parentid+"').value='"+sql+"';"
				+"parent."+functionname+ "();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 根据组合条件 生成sql 
	 * @return
	 * @throws RadowException
	 */
	public String getSql() throws RadowException{
		Grid gridCode = (Grid)this.getPageElement("codeList2Grid1");
		List<HashMap<String, Object>> listCode=gridCode.getValueList();
		Grid gridTable = (Grid)this.getPageElement("tableList2Grid");
		List<HashMap<String, Object>> listTable=gridTable.getValueList();
		
		
		/*生成查询sql保存到自定义查询视图表中*/
		String sqlSelect="select ",tempTable,tempCode;
		for(int i=0;i<listCode.size();i++){
//			String checked=listCode.get(i).get("checked")+"";
//			if("true".equals(checked)){
			tempTable=(String)listCode.get(i).get("table_code");
			tempCode=(String)listCode.get(i).get("col_code");
			sqlSelect=sqlSelect+" "+tempTable+"."+tempCode+" "+tempTable+tempCode+",";
//			}
		}
		sqlSelect=sqlSelect.toLowerCase();
		if(sqlSelect.indexOf("a01a0000")<0){//没有选择a0000信息项
		sqlSelect=sqlSelect+" "+"a01"+"."+"a0000"+" a01a0000,";
		}
		if(sqlSelect.indexOf("a01a0101")<0){//没有选择a0000信息项
		sqlSelect=sqlSelect+" "+"a01"+"."+"a0101"+" a01a0101,";
		}
		/* 修改统计关系所在单位 - 防止显示二级代码 */
		if(sqlSelect.toLowerCase().contains("a01.a0195")){
			sqlSelect = sqlSelect.toLowerCase().replace("a01.a0195", " (select b0101 from b01 where b0111=a01.A0195) ");
		}
		/* 修改统计关系所在单位 - 防止显示二级代码 */
		sqlSelect=sqlSelect.substring(0, sqlSelect.length()-1)+" from ";
		
		String tabletemp="";//表名
		for(int i=0;i<listTable.size();i++){
			String checked=listTable.get(i).get("checked")+"";
			if("true".equals(checked)){
				String temp=(String)listTable.get(i).get("tblcod");
				tabletemp=tabletemp+" "+temp.toLowerCase()+",";
			}
		}
		String arr[]=null;
		if(listTable==null||listTable.size()==0){//人员信息界调用，用没有查询信息集
			String parenttablename=this.getPageElement("parenttablename").getValue();//人员信息修改，隐藏到本页面的tab_code
	    	//BatchChange
			arr=parenttablename.split("@");
			parenttablename=arr[1];//获取信息集
			parenttablename=parenttablename.replace("\'", "");
			tabletemp=tabletemp+" "+parenttablename.toLowerCase()+"  ";
		}
		tabletemp=tabletemp.substring(0, tabletemp.length()-1);
		if(tabletemp.indexOf("a01")==-1){//a01 必须选择
			tabletemp=tabletemp+",a01";
		}
		if(tabletemp!=null&&tabletemp.indexOf("b01")!=-1){//选择b01则必须选择a02，前台不选则，则sql自动添加
//			if(tabletemp.indexOf("a01")==-1){
//				tabletemp=tabletemp+",a01";
//			}
			if(tabletemp.indexOf("a02")==-1){
				tabletemp=tabletemp+",a02";
			}
		}
		sqlSelect=sqlSelect+tabletemp;//拼接表名
		
		sqlSelect=sqlSelect+" where 1=1 ";
		String tableid="";//表关联
		if(tabletemp!=null&&!tabletemp.equals("null")&&tabletemp.trim().length()>0){
			//for(int i=0;i<listTable.size();i++){
				//String checked=listTable.get(i).get("checked")+"";
				//if("true".equals(checked)){
			String arrt[]=tabletemp.split(",");
			for(int i=0;i<arrt.length;i++){
				String temp=arrt[i];
				temp=temp.trim();
				if(!"a01".equals(temp.toLowerCase())&&!"b".equals(temp.substring(0, 1).toLowerCase())){
					tableid=tableid+" and a01.a0000="+temp.toLowerCase()+".a0000 ";
				}else if("b".equals(temp.substring(0, 1).toLowerCase())){//b01.b0111=a02.b0111 and a02.a0000=a01.a0000
					tableid=tableid+" and a02.a0201b="+temp.toLowerCase()+".b0111 ";
				}
			}
					
					
				//}
			//}
			String arr1[]=null;
			if(arr!=null){
				String temp=arr[1];
				temp=temp.replace("\'", "");
				arr1=temp.split(",");
				
			}
			for(int i=0;arr1!=null&&i<arr1.length;i++){
				String temp=arr1[i];
				if(!"a01".equals(temp.toLowerCase())&&!"b01".equals(temp.toLowerCase())){
					tableid=tableid+" and a01.a0000="+temp.toLowerCase()+".a0000 ";
				}else if("b01".equals(temp.toLowerCase())){//b01.b0111=a02.b0111 and a02.a0000=a01.a0000
					tableid=tableid+" and a02.a0201b="+temp.toLowerCase()+".b0111 ";
				}
			}
		}
//		if(tableid.indexOf("a01.a0000")==-1){//前台仅仅选择了b01信息集
//			tableid=tableid+" and a01.a0000=a02.a0000 ";
//		}
		sqlSelect=sqlSelect+tableid;//拼接表间关联
		sqlSelect=sqlSelect+" and a01.status!='4' "//垃圾数据
				+ " ";
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
		//qrysql=qrysql.toLowerCase();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			
		}else{
			//instr=" and a01.a0000 in (select t.a0000 from Competence_Subperson t where t.userid = '"+userid+"' and t.type = '1' ) ";
		}
		qrysql=qrysql+instr;
		this.getPageElement("querysql").setValue(qrysql);//隐藏sql到前台
		return qrysql;
	}
	
	/**
	 * 根据组合条件 生成sql 
	 * @return
	 * @throws RadowException
	 */
	public String getSql2() throws RadowException{
		Grid gridCode = (Grid)this.getPageElement("codeList2Grid1");
		List<HashMap<String, Object>> listCode=gridCode.getValueList();
		Grid gridTable = (Grid)this.getPageElement("tableList2Grid");
		List<HashMap<String, Object>> listTable=gridTable.getValueList();
		
		
		/*生成查询sql保存到自定义查询视图表中*/
		String sqlSelect="select ",tempTable,tempCode;
		for(int i=0;i<listCode.size();i++){
//			String checked=listCode.get(i).get("checked")+"";
//			if("true".equals(checked)){
			tempTable=(String)listCode.get(i).get("table_code");
			tempCode=(String)listCode.get(i).get("col_code");
			sqlSelect=sqlSelect+" "+tempTable+"."+tempCode+" "+tempTable+tempCode+",";
//			}
		}
		sqlSelect=sqlSelect.toLowerCase();
		if(sqlSelect.indexOf("a01a0000")<0){//没有选择a0000信息项
		sqlSelect=sqlSelect+" "+"a01"+"."+"a0000"+" a01a0000,";
		}
		if(sqlSelect.indexOf("a01a0101")<0){//没有选择a0000信息项
		sqlSelect=sqlSelect+" "+"a01"+"."+"a0101"+" a01a0101,";
		}
		sqlSelect=sqlSelect+" "+"a01"+"."+"v_xt"+" v_xt,";
		/* 修改统计关系所在单位 - 防止显示二级代码 */
		if(sqlSelect.toLowerCase().contains("a01.a0195")){
			sqlSelect = sqlSelect.toLowerCase().replace("a01.a0195", " (select b0101 from V_js_b01 b01 where b0111=a01.A0195 and b01.v_xt=a01.v_xt) ");
		}
		/* 修改统计关系所在单位 - 防止显示二级代码 */
		sqlSelect=sqlSelect.substring(0, sqlSelect.length()-1)+" from ";
		
		String tabletemp="";//表名
		for(int i=0;i<listTable.size();i++){
			String checked=listTable.get(i).get("checked")+"";
			if("true".equals(checked)){
				String temp=(String)listTable.get(i).get("tblcod");
				tabletemp = tabletemp+"V_js_"+temp.toLowerCase()+" "+temp.toLowerCase()+",";
			}
		}
		String arr[]=null;
		if(listTable==null||listTable.size()==0){//人员信息界调用，用没有查询信息集
			String parenttablename=this.getPageElement("parenttablename").getValue();//人员信息修改，隐藏到本页面的tab_code
	    	//BatchChange
			arr=parenttablename.split("@");
			parenttablename=arr[1];//获取信息集
			parenttablename=parenttablename.replace("\'", "");
			tabletemp = tabletemp+"V_js_"+parenttablename.toLowerCase()+" "+parenttablename.toLowerCase()+",";
		}
		tabletemp=tabletemp.substring(0, tabletemp.length()-1);
		if(tabletemp.indexOf("a01")==-1){//a01 必须选择
			tabletemp=tabletemp+","+"V_js_"+"a01 a01";
		}
		if(tabletemp!=null&&tabletemp.indexOf("b01")!=-1){//选择b01则必须选择a02，前台不选则，则sql自动添加
//			if(tabletemp.indexOf("a01")==-1){
//				tabletemp=tabletemp+",a01";
//			}
			if(tabletemp.indexOf("a02")==-1){
				tabletemp=tabletemp+","+"V_js_"+"a02 a02";
			}
		}
		sqlSelect=sqlSelect+tabletemp;//拼接表名
		
		sqlSelect=sqlSelect+" where 1=1 ";
		String tableid="";//表关联
		if(tabletemp!=null&&!tabletemp.equals("null")&&tabletemp.trim().length()>0){
			//for(int i=0;i<listTable.size();i++){
				//String checked=listTable.get(i).get("checked")+"";
				//if("true".equals(checked)){
			String arrt[]=tabletemp.split(",");
			for(int i=0;i<arrt.length;i++){
				String temp=arrt[i];
				temp=temp.trim().split(" ")[1].trim();
				if(!"a01".equals(temp.toLowerCase())&&!"b".equals(temp.substring(0, 1).toLowerCase())){
					tableid=tableid+" and a01.a0000="+temp.toLowerCase()+".a0000 ";
					tableid=tableid+" and a01.v_xt="+temp.toLowerCase()+".v_xt ";
				}else if("b".equals(temp.substring(0, 1).toLowerCase())){//b01.b0111=a02.b0111 and a02.a0000=a01.a0000
					tableid=tableid+" and a02.a0201b="+temp.toLowerCase()+".b0111 ";
					tableid=tableid+" and a02.v_xt="+temp.toLowerCase()+".v_xt ";
				}
				
			}
					
					
				//}
			//}
			String arr1[]=null;
			if(arr!=null){
				String temp=arr[1];
				temp=temp.replace("\'", "");
				arr1=temp.split(",");
				
			}
			for(int i=0;arr1!=null&&i<arr1.length;i++){
				String temp=arr1[i];
				if(!"a01".equals(temp.toLowerCase())&&!"b01".equals(temp.toLowerCase())){
					tableid=tableid+" and a01.a0000="+temp.toLowerCase()+".a0000 ";
					tableid=tableid+" and a01.v_xt="+temp.toLowerCase()+".v_xt ";
				}else if("b01".equals(temp.toLowerCase())){//b01.b0111=a02.b0111 and a02.a0000=a01.a0000
					tableid=tableid+" and a02.a0201b="+temp.toLowerCase()+".b0111 ";
					tableid=tableid+" and a02.v_xt="+temp.toLowerCase()+".v_xt ";
				}
			}
		}
//		if(tableid.indexOf("a01.a0000")==-1){//前台仅仅选择了b01信息集
//			tableid=tableid+" and a01.a0000=a02.a0000 ";
//		}
		sqlSelect=sqlSelect+tableid;//拼接表间关联
		sqlSelect=sqlSelect+" and a01.status!='4' "//垃圾数据
				+ " ";
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
		//qrysql=qrysql.toLowerCase();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			
		}else{
			//instr=" and a01.a0000 in (select t.a0000 from Competence_Subperson t where t.userid = '"+userid+"' and t.type = '1' ) ";
		}
		qrysql=qrysql+instr;
		//this.getPageElement("querysql").setValue(qrysql);//隐藏sql到前台
		return qrysql;
	}
	
	/**
	 * 设置条件，保存出错，则删除保存的数据（创建视图 不能回滚数据）
	 * @param qvid
	 * @param viewnametb
	 * paramurl 判断页面
	 * @return
	 */
	public int deleteErr(String qvid,String viewnametb,String paramurl){
		Connection connection=null;
		Statement stmt=null;
		try{
			//删除保存的sql
			HBSession session =HBUtil.getHBSession();
			connection = session.connection();
			if(paramurl!=null&&paramurl.trim().length()>0&&!paramurl.equals("null")){//自定义查询（人员信息查询tab），仅信息查询界面可以删除 
				stmt =connection.createStatement();
				stmt.execute("delete from JS_YJTJ where qvid='"+qvid+"' ");
				stmt.close();
			}
			if(qvid!=null&&qvid.trim().length()>0){
				stmt =connection.createStatement();
				stmt.execute("update JS_YJTJ set qrysql = '' where qvid='"+qvid+"' ");
				stmt.close();
			}
			
			//删除视图
			if(viewnametb!=null&&viewnametb.trim().length()>0){
				stmt =connection.createStatement();
				stmt.execute("drop "+viewnametb+" ");
				stmt.close();
			}
			//删除保存的信息项
			if(qvid!=null&&qvid.trim().length()>0){
				stmt =connection.createStatement();
				stmt.execute("delete from js_yjtj_fld where qvid='"+qvid+"' ");
				stmt.close();
			}
			if(qvid!=null&&qvid.trim().length()>0){//删除保存的条件项
				stmt =connection.createStatement();
				stmt.execute("delete from JS_YJTJ_use where qvid='"+qvid+"' ");
				stmt.close();
			}
			connection.close();
		}catch(Exception e1){
			try{
				System.out.println("自定义视图查询，设置条件保存出错，数据回滚失败!");
				if(stmt!=null){
					stmt.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(Exception e){
				
			}
		}
		if(paramurl!=null&&paramurl.trim().length()>0){
			this.getExecuteSG().addExecuteCode("parent.document.getElementById('qvid').value='';");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 隐藏sql到页面
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("previewFunc")
	public int previewFunc() throws RadowException{
		String sql=getSql();
		//刷新预览iframe子页面
		this.getExecuteSG().addExecuteCode("refreshPreview();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 清空页面的值
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
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
		//条件指标项
		this.getPageElement("conditionName4").setValue("");
		//清空信息项列表
		CommQuery commQuery=new CommQuery();
		List<HashMap<String, Object>> list = commQuery.getListBySQL(" select 1 from dual where 1=2 ");
		this.getPageElement("codeList2Grid").setValueList(list);
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
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(false);
		//预览
		this.createPageElement("btn5", ElementType.BUTTON, false).setDisabled(false);
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
		int num=0;
		String type=arr[num+5];
		String conditionName4=arr[num+2]+'.'+arr[num+1]+' '+arr[num];
		this.getPageElement("conditionName4").setValue(conditionName4);
		if("T".equals(type)){
			String sql="select t.code_value,concat(concat(t.code_value,' '),t.code_name) showname "
					+ " from code_value t "
					+ " where t.sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
			this.getPageElement("conditionName5").setValue(arr[num+8]);
			String name=HBUtil.getValueFromTab("concat(concat(code_value,' '),code_name)", "code_value", "code_type = 'OPERATOR' and code_value ='"+arr[num+8]+"' ");
			this.getPageElement("conditionName5_combo").setValue(name);
			if(arr[num+8].indexOf("null")==-1){
				this.getPageElement("conditionName61").setValue(arr[num+4]);
				if(arr.length>=10&&arr[num+8]!=null&&arr[num+8].indexOf("between")!=-1){
					this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(false);
					this.getPageElement("conditionName7").setValue(arr[num+6]);
				}
			}
		}else if("C".equals(type)){
			String sql="select t.code_value,concat(concat(t.code_value,' '),t.code_name) showname "
					+ " from CODE_VALUE t "
					+ " where t.code_value not like '%between%' "
					+ " and t.sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
			this.getPageElement("conditionName5").setValue(arr[num+8]);
			String name=HBUtil.getValueFromTab("concat(concat(code_value,' '),code_name)", "code_value", "code_type = 'OPERATOR' and code_value ='"+arr[num+8]+"' ");
			this.getPageElement("conditionName5_combo").setValue(name);
			if(arr[num+8].indexOf("null")==-1){
				this.getPageElement("conditionName611").setValue(arr[num+4]);
			}
		}else if("N".equals(type)){
			String sql="select t.code_value,concat(concat(t.code_value,' '),t.code_name) showname "
					+ " from CODE_VALUE t "
					+ " where t.sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
			this.getPageElement("conditionName5").setValue(arr[num+8]);
			String name=HBUtil.getValueFromTab("concat(concat(code_value,' '),code_name)", "code_value", "code_type = 'OPERATOR' and code_value ='"+arr[num+8]+"' ");
			this.getPageElement("conditionName5_combo").setValue(name);
			if(arr[num+8].indexOf("null")==-1){
				this.getPageElement("conditionName6111").setValue(arr[num+4]);
				if(arr.length>=8&&arr[num+8]!=null&&arr[num+8].indexOf("between")!=-1){
					this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(false);
					this.getPageElement("conditionName711").setValue(arr[num+6]);
				}
			}
		}else if("S".equals(type)){
			String sql="select t.code_value,concat(concat(t.code_value,' '),t.code_name) showname "
					+ " from code_value t "
					+ " where t.sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
			this.getPageElement("conditionName5").setValue(arr[num+8]);
			String name=HBUtil.getValueFromTab("concat(concat(code_value,' '),code_name)", "code_value", "code_type = 'OPERATOR' and code_value ='"+arr[num+8]+"' ");
			this.getPageElement("conditionName5_combo").setValue(name);
			String code_type=this.getPageElement("code_type").getValue();
			this.getExecuteSG().addExecuteCode("setTree('"+code_type+"');");
			if(arr[num+8].indexOf("null")==-1){
				this.getPageElement("conditionName6").setValue(arr[num+4]);
				this.getPageElement("conditionName6_combotree").setValue(arr[num+4]+"  "+arr[num+3]);
				if(arr.length>=10&&arr[num+8]!=null&&arr[num+8].indexOf("between")!=-1){
					this.getExecuteSG().addExecuteCode("document.getElementById('conditionName71_combotree').disabled=false; ");
					this.getPageElement("conditionName71").setValue(arr[num+6]);
					this.getPageElement("conditionName71_combotree").setValue(arr[num+6]+"  "+arr[num+7]);
				}
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}

}
