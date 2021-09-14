package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DataAccessKitUtil {

	/**
	 * 添加字段信息(资源库)
	 * @param columns
	 * @param columnName
	 * @param columnType
	 * @param columnValue
	 */
	public static void addColumn(List<Column> columns,String columnName,DataType columnType,String columnValue){
		Column column = new Column();
		column.setName(columnName);
		column.setType(columnType.getName());
		column.setValue(columnValue);
		columns.add(column); 
	}
	
	/**
	 * 根据生产库业务代码值，获取其对应的资源库代码值 
	 * @param codeValues 代码值键值对
	 * @param resourceOriginalCodeValue 资源库中该条记录该代码值的原始值
	 * @param workCodeValue 本地生产库代码值
	 * @return 返回最终的资源库代码值
	 * @throws Exception 
	 */
	public static String getResourceYWCode(HashMap<String, Code> codeValues,String resourceOriginalCodeValue,String workCodeValue) throws Exception{
		String retValue = "";
		String codeType = "";
		
		if(workCodeValue==null || "".equals(workCodeValue.trim()) || "null".equals(workCodeValue.trim())){
			return null;
		}
		
		List<String> mappList = new ArrayList<String>();
		for (Entry<String, Code> entry: codeValues.entrySet()) {
			Code code = entry.getValue();
			codeType = code.getYwCodeType();
			if(code.getWorkCodeValue().equals(workCodeValue)){
				mappList.add(code.getYwCodeValue());
			}
		}
		
		if(mappList.size()==1){
			return mappList.get(0);
		}
		
		if("".equalsIgnoreCase(resourceOriginalCodeValue) || resourceOriginalCodeValue==null){
			String maxValue = "";
			for (int i = 0; i < mappList.size(); i++) {
				String tempStr = mappList.get(i);
				if(tempStr.compareTo(maxValue)==0){
					maxValue = tempStr;
				}else if(tempStr.compareTo(maxValue)>=1){
					maxValue = tempStr;
				}
			}
			retValue = maxValue;
		}else{ 
			if(mappList.contains(resourceOriginalCodeValue)){
				retValue = resourceOriginalCodeValue;
			}else{
				String maxValue = "";
				for (int i = 0; i < mappList.size(); i++) {
					String tempStr = mappList.get(i);
					if(tempStr.compareTo(maxValue)==0){
						maxValue = tempStr;
					}else if(tempStr.compareTo(maxValue)>=1){
						maxValue = tempStr;
					}
				}
				retValue = maxValue;
			}
		}
		
		if(retValue==null || "".equals(retValue)){
			throw new Exception("资源库标准业务代码类型为:"+codeType+"的生产库代码为:"+workCodeValue+"转换关系存在缺失。");
		}
		return retValue;
	}
	
	/**
	 * 匹配资源库与镇江人员字段
	 * @return
	 */
	public static HashMap<String,String> Zyk_Ry(){
		HashMap<String,String> target = new HashMap<String, String>();
		target.put("BAZ805", "AAC161");
		target.put("AAA029", "AAC058");
		target.put("AAE135", "AAE135");
		target.put("AAC003", "AAC003");
		target.put("AAC004", "AAC004");
		target.put("AAC006", "AAC006");
		target.put("AAC161", "AAC161");
		target.put("AAC005", "AAC005");
		target.put("AAB401", "AAB401");
		target.put("AAB034", "BAB001");
		target.put("AAC010", "AAC010");
		target.put("AAC011", "AAC011");
		target.put("AAE406", "BAC238");
		target.put("AAC014", "AAC014");
		target.put("AAC015", "AAC015");
		target.put("AAC009", "AAC009");
		target.put("AAE402", "AAC024");
		target.put("AAC012", "AAC012");
		target.put("AAC020", "AAC020");
		target.put("AAB301", "AAB301");
		target.put("AAE005", "AAE005");
		target.put("AAE022", "AAE321");
		target.put("AAE159", "AAE159");
		target.put("AAE007", "AAE007");
		target.put("AAE006", "AAE006");
		target.put("AAE013", "AAE013");
		return target;
	} 
	/**
	 * 匹配生产库与镇江人员字段
	 * @return
	 */
	public static HashMap<String,String> Ry(){
		HashMap<String,String> Ry = new HashMap<String, String>();
		
		return Ry;
	}
	
	
	/**
	 * 根据调用数据访问接口服务返回的xml格式的数据集获取数据访问接口方案信息头
	 * @param XMLDATA        客户端传入的XML数据集字符串
	 * @return               List<Head>
	 * @throws Exception 
	 */
	public static List<Head> getFAHead(String XMLDATA) throws Exception{
		List<Head> heads = null; 
		SAXReader reader = null;
		Document  document = null;
		try {
			reader = new SAXReader();  
			document = reader.read(new ByteArrayInputStream(XMLDATA.getBytes("UTF-8")));
			Element rootElm = document.getRootElement();  
			List<Element> faHeadElms = rootElm.elements("FAHEAD");
			if(faHeadElms!=null && !faHeadElms.isEmpty()){
				heads = new ArrayList<Head>();
			}else{
				return null;
			}
			for(int i =0;i<faHeadElms.size();i++){
				Head head = new Head();
				Element faHeadElm = faHeadElms.get(i);
				String Flag = faHeadElm.attributeValue("Flag");
				String Mess = faHeadElm.attributeValue("Mess");
				head.setFlag(Flag);
				head.setMess(Mess);
				heads.add(head);
			}
		}catch (Exception e) {
				throw new Exception(e.getMessage());
		}finally{
				if(document!=null){
					document.clearContent();
				}
				if(reader!=null){
					reader=null;
				}
			}
		return heads;
	}
	
//	/**
//	 * 根据调用数据访问接口服务返回的xml格式的数据集以及数据访问接口方案脚本编码，获取数据访问接口方案脚本信息头
//	 * @param XMLDATA       客户端传入的XML数据集字符串
//	 * @param JbId          数据访问接口脚本编码
//	 * @return              List<Head>
//	 * @throws Exception 
//	 */
//	public static List<Head> getJBHead(String XMLDATA,String JbId) throws Exception{
//		List<Head> heads = null; 
//		SAXReader reader = null;
//		Document  document = null;
//		try {
//			reader = new SAXReader();
//			document = reader.read(new ByteArrayInputStream(XMLDATA.getBytes("UTF-8")));
//			Element rootElm = document.getRootElement();
//			Element DataElm = rootElm.element("Data");
//			List<Element> JbElms = DataElm.elements(JbId);
//			List<Element> faHeadElms = rootElm.elements("FAHEAD");
//			if(JbElms!=null ){
//				heads = new ArrayList<Head>();
//				Head head = new Head();
//				Element JbElm = JbElms.get(0);
//				Element JbHeadElms = JbElm.element("JBHEAD");
//				String Flag = JbHeadElms.attributeValue("Flag");
//				String Mess = JbHeadElms.attributeValue("Mess");
//				head.setFlag(Flag);
//				head.setMess(Mess);
//				heads.add(head);
//			}else{
//				return null;
//			}
//		}catch (Exception e) {
//				throw new Exception(e.getMessage());
//		}finally{
//				if(document!=null){
//					document.clearContent();
//				}
//				if(reader!=null){
//					reader=null;
//				}
//			}
//		return heads;
//	}
	
	
	/**
	 * 根据调用数据访问接口服务返回的xml格式的数据集获取数据访问接口方案脚本信息头
	 * @param XMLDATA        客户端传入的XML数据集字符串
	 * @return               List<Head>
	 * @throws Exception 
	 */
	public static HashMap<String,Head> getScriptHead(String XMLDATA) throws Exception{
		HashMap<String,Head> scriptHead = null;
		SAXReader reader = null;
		Document  document = null;
		try {
			reader = new SAXReader();  
			document = reader.read(new ByteArrayInputStream(XMLDATA.getBytes("UTF-8")));
			Element rootElm = document.getRootElement();  
			Element scriptHeadElms = rootElm.element("SCRIPTHEAD");
			List<Element> script= scriptHeadElms.elements();
			if(script!=null && !script.isEmpty()){
				scriptHead = new HashMap<String,Head>();
			}else{
				return null;
			}
			for(int i =0;i<script.size();i++){
				Head head = new Head();
				Element scriptHeadElm = script.get(i);
				String Flag = scriptHeadElm.attributeValue("Flag");
				String Mess = scriptHeadElm.attributeValue("Mess");
				head.setFlag(Flag);
				head.setMess(Mess);
				scriptHead.put(scriptHeadElm.getName(), head);
			}
		}catch (Exception e) {
				throw new Exception(e.getMessage());
		}finally{
				if(document!=null){
					document.clearContent();
				}
				if(reader!=null){
					reader=null;
				}
			}
		return scriptHead;
	}
	
	
	
	
//	/**
//	 * 根据客户端传入的xmlData和数据访问接口方案编码获取脚本数据集对象
//	 * @param xmlData           客户端传入的XML数据集字符串
//	 * @param JbId              数据访问接口脚本编码
//	 * @return                  JbData数据访问接口脚本数据集对象
//	 * @throws Exception
//	 */
//	public static JbData getJbData(String xmlData,String tabname) throws Exception{
//		SAXReader reader = null;
//		Document  document = null;
//		try {
//			reader = new SAXReader();  
//			document = reader.read(new ByteArrayInputStream(xmlData.getBytes("UTF-8")));
//			Element rootElm = document.getRootElement();
//			Element DataElm = rootElm.element("Data");
//			List<Element> JbIdElms = DataElm.elements(tabname);
//			JbData jbdata = null;
//			if(JbIdElms!=null && !JbIdElms.isEmpty()){
//				jbdata = new JbData();
//			}else{
//				return null;
//			}
//			
//			for (int i = 0; i < JbIdElms.size(); i++) {
//				Element JbIdElm = JbIdElms.get(i);
//				Element JBHEADElm = JbIdElm.element("JBHEAD");
//				Head head = new Head();
//				head.setFlag(JBHEADElm.attributeValue("Flag"));
//				head.setMess(JBHEADElm.attributeValue("Mess"));
//				jbdata.setHead(head);                             //设置脚本数据集信息头 
//				List<Element> tableElms = JbIdElm.elements("Table");
//				List<Table> tables = null;
//				if(tableElms!=null && !tableElms.isEmpty()){
//					tables = new ArrayList<Table>();
//				}else{
//					return null;
//				}
//				
//				for(int i0=0;i0<tableElms.size();i0++){
//					Table table=new Table();
//					Element tableElm = tableElms.get(i);
//					table.setName(tableElm.attributeValue("TabName"));
//					List<Element> colnodes = tableElm.elements("ColumnData");  
//					List<Column> columns=new ArrayList<Column>();
//					for (int j = 0; j < colnodes.size(); j++) {
//						Column column=new Column();
//						column.setName(colnodes.get(j).attributeValue("ColName"));
//						column.setType(colnodes.get(j).attributeValue("ColType"));
//						column.setValue(colnodes.get(j).attributeValue("ColValue"));
//						columns.add(column);
//					}
//					table.setColumns(columns);
//					
//					List<Element> keynodes = tableElm.elements("KeyData");  
//					List<Key> keys = null;
//					if(keynodes!=null && !keynodes.isEmpty()){
//						keys = new ArrayList<Key>();
//					}
//					for (int k = 0; k < keynodes.size(); k++) {
//						Key key=new Key();
//						key.setName(keynodes.get(k).attributeValue("KeyName"));
//						key.setValue(keynodes.get(k).attributeValue("KeyValue"));
//						key.setType(keynodes.get(k).attributeValue("KeyType"));
//						keys.add(key);
//					}
//					table.setKeys(keys);
//					
//					tables.add(table);
//				}
//				jbdata.setTables(tables);
//				
//				
//			}
//			return jbdata;
//		} catch (Exception e) {
//			throw new Exception(e.getMessage());
//		}finally{
//			if(document!=null){
//				document.clearContent();
//			}
//			if(reader!=null){
//				reader=null;
//			}
//		}
//		
//	}
	
	
	
	/**
	 * 解析WebServices客户端发过来的表信息XML字符串
	 * @param xmlData 客户端传入的XML字符串
	 * @param tabName 数据集名称
	 * @return 返回解析后表信息
	 * @throws Exception
	 */
	public static List<Table> praseXml(String xmlData,String tabName) throws Exception{
		
		SAXReader reader = null;
		Document  document = null;
		try {
			reader = new SAXReader();  
			document = reader.read(new ByteArrayInputStream(xmlData.getBytes("UTF-8")));
			Element rootElm = document.getRootElement();
			Element DataElm = rootElm.element("Data");
			List<Element> tableElms = DataElm.elements("Table");
			List<Table> tables = null;
			if(tableElms!=null && !tableElms.isEmpty()){
				tables = new ArrayList<Table>();
			}else{
				return null;
			}
			
			for (int i = 0; i < tableElms.size(); i++) {
				Element tableElm = tableElms.get(i);
				if(tabName.equalsIgnoreCase(tableElm.attributeValue("TabName"))){
					List<Element> rowElms = tableElm.elements("rowData");
					if(rowElms!=null && !rowElms.isEmpty()){
						for(int i1 =0;i1<rowElms.size();i1++){
							Table table=new Table();
							table.setName(tableElm.attributeValue("TabName"));
							Element rowElm = rowElms.get(i1);
							List<Element> colnodes = rowElm.elements("ColumnData");  
							List<Column> columns=new ArrayList<Column>();
							for (int j = 0; j < colnodes.size(); j++) {
								Column column=new Column();
								column.setName(colnodes.get(j).attributeValue("ColName"));
								column.setType(colnodes.get(j).attributeValue("ColType"));
								column.setValue(colnodes.get(j).attributeValue("ColValue"));
								columns.add(column);
							}
							table.setColumns(columns);
							
							List<Element> keynodes = rowElm.elements("KeyData");  
							List<Key> keys = null;
							if(keynodes!=null && !keynodes.isEmpty()){
								keys = new ArrayList<Key>();
							}
							for (int k = 0; k < keynodes.size(); k++) {
								Key key=new Key();
								key.setName(keynodes.get(k).attributeValue("KeyName"));
								key.setValue(keynodes.get(k).attributeValue("KeyValue"));
								key.setType(keynodes.get(k).attributeValue("KeyType"));
								keys.add(key);
							}
							table.setKeys(keys);
							tables.add(table);
						}
						
					}
					
				}
			}
			return tables;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			if(document!=null){
				document.clearContent();
			}
			if(reader!=null){
				reader=null;
			}
		}
	}

}
