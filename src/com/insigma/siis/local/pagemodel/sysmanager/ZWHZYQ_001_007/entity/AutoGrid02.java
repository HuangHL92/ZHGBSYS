package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity;


import java.util.List;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 * ��̬���
 * @author gezh
 */
public class AutoGrid02{
	
	private final static String sm = "new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn})";
	private final static String rowNum = "new Ext.grid.RowNumberer({width:23})";
	private final static String EditNum = "'editor':new Ext.grid.GridEditor(new Ext.form.NumberField())";
	private final static String EditText = "'editor':new Ext.grid.GridEditor(new Ext.form.TextField())";
	private final static String EditDate = "'renderer':function(value){ if(value instanceof Date){ return new Date(value).format('Y-m-d'); }else{ return value; }} ,'editor':new Ext.form.DateField({format:'Y-m-d'})";
	
	/**
	 * ���ݶ������鷵��JSON����
	 * @param objs ��������
	 * @return JSON����
	 */
	public static JSONArray getGridDataFromObjList(List<Object> objs){
		JSONArray ja = JSONArray.fromObject(objs); 		return ja;
	}
	
	/**
	 * ���ݶ������鷵��JSON�ַ���
	 * @param objs ��������
	 * @return JSON�ַ���
	 */
	public static String getGridDataFromObjList2(List<Object> objs){
		JSONArray ja = JSONArray.fromObject(objs); 		return ja.toString();
	}
	
	/**
	 * ��JSON�ַ���ת��Ϊ��������
	 * @param jsonString  JSON�ַ���
	 * @param clazz ����.class
	 * @return
	 */
	public static Object[] getDTOArray(String jsonString, Class clazz){ 
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"})); 
		JSONArray array = JSONArray.fromObject(jsonString); 
		Object[] obj = new Object[array.size()]; 
		for(int i = 0; i < array.size(); i++){ 
			JSONObject jsonObject = array.getJSONObject(i); 
			obj[i] = JSONObject.toBean(jsonObject, clazz); 
		} 
		return obj; 
	} 
	
	/**
	 *  ��̬���������䷽��
	 * @param gts ����ж��󼯺�
	 * @param objs ���ݶ��󼯺�
	 * @param isCheckBox �Ƿ��ѡ
	 * @param isRowNumber �Ƿ����
	 * @return
	 */
	public static String getGridJsonData(List<GridTitle02> gts, List<Object> objs, boolean isCheckBox, boolean isRowNumber, String message){
		String  gridJson = "{'action':true,'message':'"+message+"',";
		gridJson += "'data':";
		gridJson += getGridDataFromObjList2(objs);
		gridJson += ",";
		gridJson += "'columnModel':[";
		//��ѡ��
		if(isCheckBox){
			gridJson += sm;						//sm = "new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn})";
			gridJson += ",";					//
		}
		//���
		if(isRowNumber){
			gridJson += rowNum;					//rowNum = "new Ext.grid.RowNumberer({width:23})";
			gridJson += ",";
		}
		for(int i = 0; i < gts.size(); i++){	//������ģ�͵�����ֵ�������ѭ��
			GridTitle02 gt = gts.get(i);			//
			gridJson += "{'header':'";			//
			gridJson += gt.getHeader();
			gridJson += "',";
			gridJson += "'dataIndex':'";
			gridJson += gt.getDataIndex();
			gridJson += "'";
		  			
			if(!"".equals(gt.getWidth()) && null != gt.getWidth()){
				gridJson += ",";
				gridJson += "'width':";	
				gridJson += gt.getWidth();
			}
		  			
			//������ʾjs���⺯��
			if(!"".equals(gt.getRenderer()) && null != gt.getRenderer()){
				gridJson += ",";
				gridJson += "'renderer':";
				gridJson += gt.getRenderer();
			}
			//����
			if(!"".equals(gt.isSortable())){
				gridJson += ",";
				gridJson += "'sortable':";	
				gridJson += gt.isSortable();
			}
			//����
			if(!"".equals(gt.isHidden())){
				gridJson += ",";
				gridJson += "'hidden':";	
				gridJson += gt.isHidden();
			}
			
			//�༭����
			if(!"".equals(gt.getAlign()) && null != gt.getAlign()){
				if("left".equalsIgnoreCase(gt.getAlign())){
					gridJson += ",";
					gridJson += "'align':";	
					gridJson += "'left'";
				}else if("right".equalsIgnoreCase(gt.getAlign())){
					gridJson += ",";
					gridJson += "'align':";	
					gridJson += "'right'";
				}else{
					gridJson += ",";
					gridJson += "'align':";	
					gridJson += "'center'";
				}
			}
		  			
			//�༭����
			if(!"".equals(gt.getEditType()) && null != gt.getEditType()){
				gridJson += ",";
				if("date".equalsIgnoreCase(gt.getEditType())){
					gridJson += EditDate;//EditDate = "'renderer':function(value){ if(value instanceof Date){ return new Date(value).format('Y-m-d'); }else{ return value; }} ,'editor':new Ext.form.DateField({format:'Y-m-d'})";
				}else if("Number".equalsIgnoreCase(gt.getEditType())){
					gridJson += EditNum;//EditNum = "'editor':new Ext.grid.GridEditor(new Ext.form.NumberField())";									
				}else{
					gridJson += EditText;//EditText = "'editor':new Ext.grid.GridEditor(new Ext.form.TextField())";
				}
			}
		  			
			gridJson += "}";
			if(i != gts.size() - 1){
				gridJson += ",";
			}
		}
		gridJson += "],";
		  		
		gridJson += "'fieldsNames':[";
		for(int i = 0; i < gts.size(); i++){
			GridTitle02 gt = gts.get(i);
			gridJson += "{name:'";				//'fieldsNames':[{name:' '},{name:' '}]}
			gridJson += gt.getDataIndex();
			gridJson += "'}";
			if(i != gts.size() - 1){
				gridJson += ",";
			}
		}
		gridJson += "]}";
		return gridJson;
	}
	
	/**
	 *  ��̬���������䷽��
	 * @param gts ����ж��󼯺�
	 * @param dataJsonStr ����JSON���� ���磺[{'number':'1','name': '����','age': '21','edu': '����״�ѧ'},{'number':'2','name': '����2','age': '212','edu': '����״�ѧ2'}]
	 * @param isCheckBox �Ƿ��ѡ
	 * @param isRowNumber �Ƿ����
	 * @return
	 */
	public static String getGridJsonData2(List<GridTitle02> gts, String dataJsonStr, boolean isCheckBox, boolean isRowNumber, String message, String tabName, String tableMessage){ //[{'����':'�ֶ�ֵ','����':'�ֶ�ֵ'}]������Դ
		String  gridJson = "{'action':true,'message':'"+message+"','tabName':'"+tabName+"','tableMessage':'"+tableMessage+"',";
		gridJson += "'data':";
		gridJson += dataJsonStr;
		gridJson += ",";
		gridJson += "'columnModel':[";
		//��ѡ��
//		if(isCheckBox){
//			gridJson += sm;//sm = "new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn})";
//			gridJson += ",";
//		}
		//���
		if(isRowNumber){
			gridJson += rowNum;//rowNum = "new Ext.grid.RowNumberer({width:23})";
			gridJson += ",";
		}
		//{'header':'QUOTE_TARGET_DATA_ISN','dataIndex':'QUOTE_TARGET_DATA_ISN','width':
		gridJson += "{'header':'QUOTE_TARGET_DATA_ISN','dataIndex':'QUOTE_TARGET_DATA_ISN','width':120,'sortable':true,'hidden':true,'align':'left','editor':new Ext.grid.GridEditor(new Ext.form.TextField())},";
		for(int i = 0; i < gts.size(); i++){
			GridTitle02 gt = gts.get(i);
			gridJson += "{'header':'";				//{'action':true,'message':'error!','data':[{'����':'�ֶ�ֵ','����':'�ֶ�ֵ'}],'columnModel':[ ,  ,{'header':'
			gridJson += gt.getHeaderTitle();				//'columnModel':[ ,  ,{'header':'����','dataIndex':'�ֶ�ֵ','width': ,'sortable': ,'hidden': ,'align': ,
			gridJson += "',";
			gridJson += "'dataIndex':'";
			gridJson += gt.getDataIndex();
			gridJson += "'";
		  			
			if(!"".equals(gt.getWidth()) && null != gt.getWidth()){
				gridJson += ",";
				gridJson += "'width':";	
				gridJson += gt.getWidth();
			}
		  			
			//����
			if(!"".equals(gt.isSortable())){
				gridJson += ",";
				gridJson += "'sortable':";	
				gridJson += gt.isSortable();
			}
			//����
			if(!"".equals(gt.isHidden())){
				gridJson += ",";
				gridJson += "'hidden':";	
				gridJson += gt.isHidden();
			}
			//�Ƿ�ɱ༭
			if(!"".equals(gt.getDisabled())){
				gridJson += ",";
				gridJson += "'editable':";	
				gridJson += gt.getDisabled();
			}
		  			
			//�༭����
			if(!"".equals(gt.getAlign()) && null != gt.getAlign()){
				if("left".equalsIgnoreCase(gt.getAlign())){
					gridJson += ",";
					gridJson += "'align':";	
					gridJson += "'left'";
				}else if("right".equalsIgnoreCase(gt.getAlign())){
					gridJson += ",";
					gridJson += "'align':";	
					gridJson += "'right'";
				}else{
					gridJson += ",";
					gridJson += "'align':";	
					gridJson += "'center'";
				}
			}
		  			
			//�༭����
			if(!"".equals(gt.getEditType()) && null != gt.getEditType()){
				gridJson += ",";
				if("date".equalsIgnoreCase(gt.getEditType())){
					gridJson += EditDate;//EditDate = "'renderer':function(value){ if(value instanceof Date){ return new Date(value).format('Y-m-d'); }else{ return value; }} ,'editor':new Ext.form.DateField({format:'Y-m-d'})";
				}else if("Number".equalsIgnoreCase(gt.getEditType())){
					gridJson += EditNum;//EditNum = "'editor':new Ext.grid.GridEditor(new Ext.form.NumberField())";	
				}else{
					gridJson += EditText;//EditText = "'editor':new Ext.grid.GridEditor(new Ext.form.TextField())";
				}
			}
		  			
			gridJson += "}";
			if(i != gts.size() - 1){
				gridJson += ",";
			}
		}
		gridJson += "],";
		  		
		gridJson += "'fieldsNames':[{name:'QUOTE_TARGET_DATA_ISN'},";
		for(int i = 0; i < gts.size(); i++){
			GridTitle02 gt = gts.get(i);
			gridJson += "{name:'";
			gridJson += gt.getDataIndex();
			gridJson += "'}";
			if(i != gts.size() - 1){
				gridJson += ",";
			}
		}
		gridJson += "]}";
		//System.out.println("+++++++++++++++++++++++++++++++++"+gridJson);
		return gridJson;
	}
	
}
