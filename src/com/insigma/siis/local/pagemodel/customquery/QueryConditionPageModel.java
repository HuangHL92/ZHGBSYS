package com.insigma.siis.local.pagemodel.customquery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.sf.json.JSONArray;

import org.hibernate.Query;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.CodeManager;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.CodeTableCol;
import com.insigma.siis.local.business.entity.Customquery;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
/**
 * @author lixy
 *
 */
public class QueryConditionPageModel extends PageModel {
	
	private CustomQueryBS ctcBs=new CustomQueryBS();
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException{		
		/*List<Map<String, String>> list=ctcBs.collectionList;

		//������Ϣ��������
		if(list!=null&&list.size()>0){
			TreeMap<String, String> treeMap = new TreeMap<String, String>();
			for (Map<String, String> coll : list) {
				treeMap.put(coll.get("COL_LECTION_NAME".toLowerCase()), coll.get("COL_LECTION_NAME".toLowerCase()));
			}
			( (Combo) this.getPageElement("collection")).setValueListForSelect(treeMap);
		}*/
		
		//��ʼ�����趨��Ϣ����ѡ��  mengl 20160629
		//Combo collection =  (Combo)this.createPageElement("collection", ElementType.SELECT, false);
		Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		//collection.setValueListForSelect(map);
		
		//����г�ʼ�� 2017��6��5�� zoul
		//Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		StringBuffer dataarrayjs = getComboArray(map);
		//��Ϣ��
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:2,dataArray:"+dataarrayjs+"});changestore();");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:2,dataArray:"+dataarrayjs+"});");
		
		//����и������д��ݿɶ�����ID��������������Ϣ������
		//String queryId=this.getRadow_parent_data();
		String queryId=this.getPageElement("subWinIdBussessId2").getValue();
		if (!"".equals(queryId)) {
			HBSession sess = HBUtil.getHBSession();
			Customquery cq = (Customquery) sess.get(Customquery.class, queryId);
			String data = null;
			if (cq != null) {
				data = cq.getGridstring();
				if (data != null && !"".equals(data)) {
					this.getPageElement("grid").setValue(cq.getGridstring());
					this.getPageElement("conditionName").setValue(cq.getQueryname());
				}else{
					this.getExecuteSG().addExecuteCode("insertEmptyRow(Ext.getCmp('grid').getStore());");
				}
			}else{
				this.getExecuteSG().addExecuteCode("insertEmptyRow(Ext.getCmp('grid').getStore());");
			}
		}else{
			this.getExecuteSG().addExecuteCode("insertEmptyRow(Ext.getCmp('grid').getStore());");
		}
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
	}

	private StringBuffer getComboArray(Map<String, String> map){
		StringBuffer dataarrayjs = new StringBuffer("[");
		Set<String> tablecodes = map.keySet();
		//[ 'exists', '����' ]
		for(String tablecode : tablecodes){
			dataarrayjs.append("['"+tablecode+"','"+map.get(tablecode)+"'],");
		}
		if(map.size()>0){
			dataarrayjs.deleteCharAt(dataarrayjs.length()-1);
		}
		dataarrayjs.append("]");
		return dataarrayjs;
	}
	
	
	
	/**
	 * �趨ѡ���кŵ�ҳ����
	 * @author mengl
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("grid.rowclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@Synchronous(true)
	public int setCueRow() throws RadowException, AppException{
		int cueRowIndex = this.getPageElement("grid").getCueRowIndex();
		String cueRowIdexStr = this.getPageElement("cueRowIndex").getValue();
		int pRowIndex = -1;
		if(!"".equals(cueRowIdexStr)){
			pRowIndex = Integer.valueOf(cueRowIdexStr);
		}
		
		if(pRowIndex==cueRowIndex){//û����
			return EventRtnType.NORMAL_SUCCESS;
		}
		PageElement pe = this.getPageElement("grid");
		List<HashMap<String, Object>> glist = pe.getValueList();
		//PageElement colName = this.getPageElement("colName");
		HashMap<String, Object> m = glist.get(0);
		String tablenamecode = m.get("tableName").toString();
		String tablefieldcode =m.get("colNamesValue").toString();
		
		Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		//collection.setValueListForSelect(map);
		
		//����г�ʼ�� 2017��6��5�� zoul
		//Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		StringBuffer dataarrayjs = getComboArray(map);
		//��Ϣ��
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:2,dataArray:"+dataarrayjs+"});");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:2,dataArray:"+dataarrayjs+"});");
		
		//��Ϣ��
		if(tablenamecode!=null&&!"".equals(tablenamecode)){
			setColNames(tablenamecode);
		}
		
		
		//��Ϣ���Ӧ��codevalue
		//radow.doEvent('setCodeValue',dataarray[i][0]+"@"+e.record.data.tableName);
		if(tablenamecode!=null&&!"".equals(tablenamecode)&&tablefieldcode!=null&&!"".equals(tablefieldcode)){
			setCodeValue(tablefieldcode+"@"+tablenamecode);
		}
		
		
		
		//������
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:4,dataArray:[['(','(']],allowBlank:true});");
		//this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:4,dataArray:[['(','(']],allowBlank:true});");
		
		//������
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:13,dataArray:[[')',')']],allowBlank:true});");
		//this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:13,dataArray:[[')',')']],allowBlank:true});");
		
		//�߼���
		Map<String,String> treeMap = new TreeMap<String,String>();
		treeMap = RuleSqlListBS.getAllMapByCodeType("LOGICSYMBOLS");
		dataarrayjs = getComboArray(treeMap);
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:14,dataArray:"+dataarrayjs+",allowBlank:true});");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:14,dataArray:"+dataarrayjs+",allowBlank:true});");
		
		//��Ϣ��
		//this.getPageElement("collection").setValue(pe.getValue("tableName").toString());
		
		//��Ϣ��onchange
		//collectionChange();
		
		//��Ϣ��
		//colName.setValue((String)(pe.getValue("colNamesValue")));
		
		//��Ϣ��onchange
		//colNameChane();
		
		
		//this.getPageElement("opeartor").setValue((String)(pe.getValue("opeartors")));
		//this.getPageElement("colValue").setValue((String)(pe.getValue("colValues")));
		//this.getPageElement("logicSymbol").setValue((String)(pe.getValue("logicSymbols")));
		this.getPageElement("cueRowIndex").setValue(cueRowIndex+"");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * �޸�
	 * @return
	 * @author mengl
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("editcond.onclick")
	@AutoNoMask
	public int modify() throws RadowException, AppException{
		Grid pe = (Grid)this.getPageElement("grid");//��ȡgrid����
		String cueRowIdexStr = this.getPageElement("cueRowIndex").getValue();
		if(StringUtil.isEmpty(cueRowIdexStr)){
			throw new AppException("����ѡ��һ���޸ģ�");
		}
		int cueRowIndex = Integer.valueOf(cueRowIdexStr);
		//��ȡѡ�����и���ֵ
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
//		String tableName=RuleSqlListBS.getTableName(collection);  //���ڱ��溺�֣��������
		String colValueView = CodeManager.getValueByCode(!StringUtil.isEmpty(codeTableCol.getCodeType())?codeTableCol.getCodeType():colNameValue,colValue);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", collection);  //���ٱ��溺�֣��������
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
		
		pe.updateRowData(cueRowIndex, map);//���¸�������
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**��ѡ��������һ��
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
			throw new AppException("����ѡ��һ�У�");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==0){
				throw new AppException("�Ѿ��ǵ�һ�У��������ƣ�");
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
	
	
	/**��ѡ���н���һ��
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
			throw new AppException("����ѡ��һ�У�");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==(list.size()-1)){
				throw new AppException("�Ѿ�������У��������ƣ�");
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
	 * ��ѡ����Ϣ��ʱ��������ѯָ����
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("collection.onchange")
	@NoRequiredValidate
	public int collectionChange() throws RadowException, AppException {
		//����ѡ�����Ϣ�����ض�Ӧ�Ĳ�ѯ��������
		PageElement pe = this.getPageElement("colName");
		String code = this.getPageElement("collection").getValue();
		/*TreeMap<String, String> treeMap=ctcBs.getCtcListByCollectionCode(code,ctcBs.ctcList);
		( (Combo) this.getPageElement("colName")).setValueListForSelect(treeMap);*/
		
		TreeMap<String, String> treeMap = null;
		if(!"A01".equalsIgnoreCase(code)){
			treeMap = RuleSqlListBS.getVru005byVru004(code,false,"isZbx ='1'  ");//����ѯָ����Һ���vsl006�Ƿ����� mengl 20160629
		}else{
			treeMap = RuleSqlListBS.getVru005byVru004(code,false,"isZbx ='1' or  id.columnName = 'AGE' or id.columnName ='A0148' ");//����ѯָ����Һ���vsl006�Ƿ����� mengl 20160629
		}
		
		( (Combo) this.getPageElement("colName")).setValueListForSelect(treeMap);
		
		pe.setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("colName.onchange")
	@NoRequiredValidate
	public int colNameChane() throws RadowException, AppException {
		//������ѡ�Ĳ�ѯ�������ض�Ӧ��ֵ����
		Combo pe = (Combo)this.getPageElement("colValue");
		String collection = this.getPageElement("collection").getValue();
		String colNameValue = this.getPageElement("colName").getValue();
		
		if(StringUtil.isEmpty(colNameValue)){
			this.getPageElement("collection").setValue("");
			this.getPageElement("colName").setValue("");
			this.setMainMessage("����ѡ����Ϣ����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		
		// �趨��ѡֵ
		// mengl 20160629
		CodeTableCol cr = RuleSqlListBS.getCodeTableCol(collection, colNameValue);
		Map<String,String> map = new TreeMap<String,String>();
		if(cr!=null && !StringUtil.isEmpty(cr.getCodeType())){
			map = RuleSqlListBS.getAllMapByCodeType(cr.getCodeType().toUpperCase());
		}else if(!StringUtil.isEmpty(colNameValue)){
			map = RuleSqlListBS.getAllMapByCodeType(colNameValue.toUpperCase());
		}
		pe.setValueListForSelect(map);
		
		//�趨�����
		 Combo opeartorCombo = (Combo)this.getPageElement("opeartor");
		 Map<String,String> mapOperator = RuleSqlListBS.getCodeValuesMapByCodeType("OPERATOR");
		 //��ѡ����Ϊ�գ��趨������������ȥ��
		if(map.isEmpty()){
			//���жϴ���ֵΪ�յ�ʱ�������number��ʽ���ֶΣ����޳���Ӧ�������
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
			//�����DATE��ʽ���ֶΣ�������ʺ�date����ʹ�õ������
			}else if (cr.getColDataTypeShould().equals("DATE")){
				mapOperator.remove("like {v%}");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("like {%v}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("like {%v%}");
				mapOperator.remove("not like {%v%}");
			//�����VARCHAR2��ʽ���ֶΣ�������ʺ�VARCHAR2����ʹ�õ������
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
			//���������Ϣ��  �ſ�<��>��<=��>=���� //--lzy  updatee 
			if("A0221".equals(colNameValue)||"A0801B".equals(colNameValue)||"A0901B".equals(colNameValue)){
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
		this.closeCueWindowEX();
		return EventRtnType.NORMAL_SUCCESS;
	}
	public void closeCueWindowEX(){
		this.getExecuteSG().addExecuteCode("window.parent.Ext.WindowMgr.getActive().close();");
	}
	/**
	 * @$comment �����ѯ����
	 * @param 
	 * @return 
	 * @throws 
	 * @author lixy
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	@PageEvent("btnSave.onclick")
	@NoRequiredValidate
	@Transaction
	public int btnSaveOnclick() throws RadowException, AppException {
		PageElement pe = this.getPageElement("grid");
		String queryName=this.getPageElement("conditionName").getValue();
		List<HashMap<String, Object>> list= pe.getValueList();
		//�ǿ�У��
		int ret = -1;
		if((ret = valiEmpty(list))!=-1){
			return ret;
		}
		
		String querySql=ctcBs.createSQL(list);
		String queryDes=ctcBs.createSQLView(list);
		String loginName=SysUtil.getCacheCurrentUser() .getLoginname();
		String data=JSONArray.fromObject(list).toString();
		//String queryId=this.getRadow_parent_data();
		String queryId=this.getPageElement("subWinIdBussessId2").getValue();
		if(queryName==null||"".equals(queryName))
			throw new AppException("��ѯ�������Ʋ���Ϊ�գ�");
		
		if(queryId==null||"".equals(queryId)){
    	HBSession sess = HBUtil.getHBSession();
    	Query query=sess.createSQLQuery("select 1 from customquery where queryname='"+queryName+"' and  loginname='"+SysManagerUtils.getUserloginName()+"'");
    	List<Object> listc=query.list();
    	if(listc.size()>0)
    		throw new AppException("�̶����������Ѵ��ڣ�");
		}		

		ctcBs.saveOrUodateCq(queryId, queryName, querySql, queryDes, loginName,data);
		//this.closeCueWindowEX();
		this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('gridcq');");//ˢ�¸�����̶���������
		this.getExecuteSG().addExecuteCode("$h.alert('��ʾ','����ɹ�');setTimeout(function(){ Ext.MessageBox.hide();} , 300);");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * @$comment �������ɲ�ѯ������ѯ     ����˫����ѯ����   conditionRowdbclick
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
		String queryName="�ϴβ�ѯ";//����Ϊ�ϴβ�ѯ
		List<HashMap<String, Object>> list= pe.getValueList();
		
		//�ǿ�У��
		int ret = -1;
		if((ret = valiEmpty(list))!=-1){
			return ret;
		}
		
		
		String querySql=ctcBs.createSQL(list);
		String queryDes=ctcBs.createSQLView(list);
		String loginName=SysUtil.getCacheCurrentUser() .getLoginname();
//		String queryId=this.getRadow_parent_data();
		ctcBs.delLastTimeCq();//ɾ���ϴβ�ѯ����
		String data=JSONArray.fromObject(list).toString();
		ctcBs.saveOrUodateCq("", queryName, querySql, queryDes, loginName,data);
		
		//this.createPageElement("sql",ElementType.HIDDEN, true).setValue(querySql);//���ø�ҳ��sqlֵΪ��������sql
		
		this.request.getSession().setAttribute("groupBy", "1");
		this.request.getSession().setAttribute("queryType", "1");
		this.request.getSession().setAttribute("queryTypeEX", "�¸Ĳ�ѯ��ʽ");
		
		this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('gridcq');");	//ˢ�¸�����̶���������
		this.getExecuteSG().addExecuteCode("realParent.realParent.conditionRowdbclick('"+querySql.replace("'", "\\'")+"',realParent.doQuery());");
		//this.getExecuteSG().addExecuteCode("realParent.realParent.document.getElementById('sql').value='"+querySql.replace("'", "\\'")+"';");
		//this.getExecuteSG().addExecuteCode("realParent.realParent.document.getElementById('orgjson').value=Ext.util.JSON.encode(realParent.doQuery());");
		
		//this.getExecuteSG().addExecuteCode("realParent.realParent.radow.doEvent('peopleInfoGrid.dogridquery');");//������ִ�в�ѯ	
		
		this.closeCueWindowEX();
		return EventRtnType.NORMAL_SUCCESS;
	}

	private int valiEmpty(List<HashMap<String, Object>> list) {
		//�ǿ�У��
		int rowidex = 0;
		List<HashMap<String, Object>> listremove = new ArrayList<HashMap<String,Object>>();
    	for(HashMap<String, Object> m:list){
    		rowidex++;
    		String  tableNamed = m.get("tableNamed").toString();//sql������
    		String  colNamesInfo = m.get("colNamesInfo").toString();//sql������
    		String  opeartorsd = m.get("opeartorsd").toString();//sql������
    		String  colValuesView = m.get("colValuesView").toString();//sql������
    		String  opeartors = m.get("opeartors").toString();
    		if("".equals(tableNamed)&&"".equals(colNamesInfo)&&"".equals(opeartorsd)&&"".equals(colValuesView)){
    			listremove.add(m);
    			continue;
    		}
    		if("".equals(tableNamed)){
    			this.setMainMessage("��"+rowidex+"�� �������� ����Ϊ�գ�");
        		return EventRtnType.NORMAL_SUCCESS;
    		}
    		if("".equals(colNamesInfo)){
    			this.setMainMessage("��"+rowidex+"�� ����Ϣ� ����Ϊ�գ�");
        		return EventRtnType.NORMAL_SUCCESS;
    		}
    		if("".equals(opeartorsd)){
    			this.setMainMessage("��"+rowidex+"�� ��������� ����Ϊ�գ�");
        		return EventRtnType.NORMAL_SUCCESS;
    		}
    		if("".equals(colValuesView)&&!"({c} is null or {c}={v})".equals(opeartors)){
    			this.setMainMessage("��"+rowidex+"�� ��ֵ�� ����Ϊ�գ�");
        		return EventRtnType.NORMAL_SUCCESS;
    		}
    		
    	}
    	list.removeAll(listremove);
    	return -1;
	}
	
	
	
	/**
	 * ������Ϣ�������� zoul
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@SuppressWarnings("static-access")
	@PageEvent("setColNames")
	@AutoNoMask
	@Synchronous(true)
	public int setColNames(String code) throws RadowException, AppException{  
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		if(!"A01".equalsIgnoreCase(code)){
			treeMap = RuleSqlListBS.getVru005byVru004(code,false,"isZbx ='1'  ");//����ѯָ����Һ���vsl006�Ƿ����� mengl 20160629
		}else{
			treeMap = RuleSqlListBS.getVru005byVru004(code,false,"isZbx ='1' or  id.columnName = 'AGE' or id.columnName ='A0148' ");//����ѯָ����Һ���vsl006�Ƿ����� mengl 20160629
		}
		Set<String> keys = treeMap.keySet();
		String defaultValue = "";
		if(keys.size()>0){
			defaultValue = keys.iterator().next();
		}
		//����г�ʼ�� 2017��6��5�� zoul
		StringBuffer dataarrayjs = getComboArray(treeMap);
		//��Ϣ��
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:6,dataArray:"+dataarrayjs+"});");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:6,dataArray:"+dataarrayjs+"});");
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * �����༭��
	 * @author zoul
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("createCellEdit")
	@NoRequiredValidate
	//@Synchronous(true)
	public int createCellEdit(String row) throws RadowException, AppException{
		
		Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		StringBuffer dataarrayjs = getComboArray(map);
		//��Ϣ��
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:2,dataArray:"+dataarrayjs+"});");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:2,dataArray:"+dataarrayjs+"});");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����codevalue������ zoul
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@SuppressWarnings("static-access")
	@PageEvent("setCodeValue")
	@Synchronous(true)
	@AutoNoMask
	public int setCodeValue(String code) throws RadowException, AppException{  
		String[] p = code.split("@");
		String collection = p[1];
		String colNameValue = p[0];
		CodeTableCol cr = RuleSqlListBS.getCodeTableCol(collection, colNameValue);
		Map<String,String> treeMap = new TreeMap<String,String>();
		if(cr!=null && !StringUtil.isEmpty(cr.getCodeType())){
			treeMap = RuleSqlListBS.getAllMapByCodeType(cr.getCodeType().toUpperCase());
		}else if(!StringUtil.isEmpty(colNameValue)){
			treeMap = RuleSqlListBS.getAllMapByCodeType(colNameValue.toUpperCase());
		}
		
		
		Set<String> keys = treeMap.keySet();
		String defaultValue = "";
		String canOutSelectList = "true";
		if(keys.size()>0){
			defaultValue = keys.iterator().next();
			canOutSelectList = "false";
		}
		//����г�ʼ�� 2017��6��5�� zoul
		StringBuffer dataarrayjs = getComboArray(treeMap);
		//��Ϣ���Ӧ�Ĵ��뼯
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:11,dataArray:"+dataarrayjs+",canOutSelectList:'"+canOutSelectList+"'});");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:11,dataArray:"+dataarrayjs+",canOutSelectList:'"+canOutSelectList+"'});");
		
		Map<String,String> mapOperator = RuleSqlListBS.getCodeValuesMapByCodeType("OPERATOR");
		 //��ѡ����Ϊ�գ��趨������������ȥ��
		if(treeMap.isEmpty()){
			//���жϴ���ֵΪ�յ�ʱ�������number��ʽ���ֶΣ����޳���Ӧ�������
			if(cr.getColDataTypeShould().equals("NUMBER")){
				mapOperator.remove("like {v%}");
				
				mapOperator.remove("like {%v}");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("not like {%v%}");
				mapOperator.remove("like {%v%}");
				
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
			//�����DATE��ʽ���ֶΣ�������ʺ�date����ʹ�õ������
			}else if (cr.getColDataTypeShould().equals("DATE")){
				mapOperator.remove("like {v%}");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("like {%v}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("like {%v%}");
				mapOperator.remove("not like {%v%}");
			//�����VARCHAR2��ʽ���ֶΣ�������ʺ�VARCHAR2����ʹ�õ������
			}else if (cr.getColDataTypeShould().equals("VARCHAR2")){
				//mapOperator.remove(">{v}");
				//mapOperator.remove(">={v}");
				//mapOperator.remove("<{v}");
				//mapOperator.remove("<={v}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("not like {%v%}");
			}else{
				//mapOperator.remove(">{v}");
				//mapOperator.remove(">={v}");
				//mapOperator.remove("<{v}");
				//mapOperator.remove("<={v}");
				mapOperator.remove("!= trunc(sysdate + {v})");
				mapOperator.remove("< trunc(sysdate + {v})");
				mapOperator.remove("<= trunc(sysdate + {v})");
				mapOperator.remove("= trunc(sysdate + {v})");
				mapOperator.remove("> trunc(sysdate + {v}) ");
				mapOperator.remove(">= trunc(sysdate + {v})");
				mapOperator.remove("not like {v%}");
				mapOperator.remove("not like {%v}");
				mapOperator.remove("not like {%v%}");
			}
			
		}else{
			//���������Ϣ��  �ſ�<��>��<=��>=���� //--lzy  updatee 
			if("A0221".equals(colNameValue)||"A0801B".equals(colNameValue)||"A0901B".equals(colNameValue)){
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
			}else{
				//mapOperator.remove(">{v}");
				//mapOperator.remove(">={v}");
				//mapOperator.remove("<{v}");
				//mapOperator.remove("<={v}");
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
		}
		if(cr.getColCode().equals("AGE")){
			mapOperator.remove("!={v}");
			mapOperator.remove("({c} is null or {c}={v})");
			mapOperator.remove("like {v%}");
			mapOperator.remove("not like {v%}");
			mapOperator.remove("like {%v}");
			mapOperator.remove("not like {%v}");
			mapOperator.remove("like {%v%}");
			mapOperator.remove("not like {%v%}");
		}
		
		StringBuffer oparray = getComboArray(mapOperator);
		//�����
		//this.getExecuteSG().addExecuteCode("$h.changeGridEditor({gridid:'grid',colIndex:9,dataArray:"+oparray+"});");
		this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'grid',colIndex:9,dataArray:"+oparray+"});");
		
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * �����������б�
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
		String colNameValue = this.getPageElement("colName").getValue();
		String opeartor = this.getPageElement("opeartor").getValue();
		String colValue = this.getPageElement("colValue").getValue();
		//����ѯ�������֤ʱ������ѯֵ�е�Сд��ĸת��Ϊ��д��ĸ
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
	 * ��grid�еĹ�ϵ�б��ʱ����
	 * @author caiy
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	//@PageEvent("grid.afteredit")
	@GridDataRange(GridData.cuerow)
	@NoRequiredValidate
	@AutoNoMask
	public int gridonchange() throws RadowException, AppException{
		this.setNextEventName("PrintSQL");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��װ��ӡsql
	 * @author caiy
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	//@PageEvent("grid.afteredit")
	@PageEvent("PrintSQL")
	@GridDataRange(GridData.cuerow)
	@NoRequiredValidate
	@AutoNoMask
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
			sqlForm = sqlForm.replace("({c} is null or {c}={v})", "Ϊ��").replace("trunc(sysdate + {v}) ", "��ǰʱ��+"+value+"��").replace("not like {%v%}", "������'"+value+"'")
					.replace("not like {%v}", "����'"+value+"'��β").replace("not like {v%}", "����'"+value+"'��ͷ")
					.replace("{v}", value).replace("or", "����").replace("and", "����").replace("like {%v%}", "����("+value+")")
					.replace("like {%v}", "�Ұ���("+value+")").replace("like {v%}", "�����("+value+")").replace("���� 1=1", "");

		}
		this.getPageElement("aa").setValue(sqlForm);
		//this.getExecuteSG().addExecuteCode("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}