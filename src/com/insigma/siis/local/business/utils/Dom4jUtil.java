package com.insigma.siis.local.business.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.fr.json.JSONArray;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class Dom4jUtil {
	
	@SuppressWarnings("unchecked")
	public static  List<Map<String, String>> gwyinfo(String path){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> nodeMap = new HashMap<String, String>();
		Map<String, String> tableMap = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
//		String upload_file=GlobalNames.sysConfig.get("UNZIP_FOLDER");
//		String file = upload_file + path + "/gwyinfo.xml";
		String file = path;
		Document doc;
		try {
			InputStream in = Dom4jUtil.class.getClassLoader().getResourceAsStream(file);  
			doc = reader.read(in);
			Element root = doc.getRootElement();
			List<Element> listnodes = root.elements("node");
	        for (Element element : listnodes) {
				nodeMap.put(element.attributeValue("name"), element.attributeValue("value"));
			}
	        Element tables = root.element("tablelist");
	        List<Element> listtables = tables.elements("table");
	        for (Element element2 : listtables) {
	        	tableMap.put(element2.attributeValue("name"), element2.attributeValue("name"));
			}
	        list.add(nodeMap);
	        list.add(tableMap);
//			System.out.println(net.sf.json.JSONArray.fromObject(list));;
		        
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> gwyA_B(String path){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		Map<String, String> tableMap = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
//		String upload_file=GlobalNames.sysConfig.get("UNZIP_FOLDER");
//		String file = upload_file + path + "/gwyinfo.xml";
		String file = path;
		Document doc;
		try {
			InputStream in = Dom4jUtil.class.getClassLoader().getResourceAsStream(file);  
			doc = reader.read(in);
			Element root = doc.getRootElement();
			
			List<Element> listnode = root.elements();
			List<Element> listrow = listnode.get(1).elements();
			for (Element element : listrow) {
				Map<String, String> nodeMap = new HashMap<String, String>();
				List<Attribute> attrs = element.attributes();  
				for (Attribute attr : attrs) {  
					nodeMap.put(attr.getName(), attr.getValue());
				
	            }
				list.add(nodeMap);
			}
			
//	        for (Element e : listnode) {
//				System.out.println(e.getName()+":" +e.getStringValue());
//			}
//			Element data = root.element("rowset");
//			List<Element> listtables = data.elements("z:row");
//			for (Element element : listtables) {
//				Map<String, String> nodeMap = new HashMap<String, String>();
//				List<Attribute> attrs = element.attributes();  
//				for (Attribute attr : attrs) {  
//					nodeMap.put(attr.getName(), attr.getValue());
//	            }
//				list.add(nodeMap);
//			}
//			System.out.println(net.sf.json.JSONArray.fromObject(list));;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> gwyA57(String path){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		Map<String, String> tableMap = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		String file = path;
		Document doc;
		try {
			InputStream in = Dom4jUtil.class.getClassLoader().getResourceAsStream(file);  
			doc = reader.read(in);
			Element root = doc.getRootElement();
			
			List<Element> listnode = root.elements();
			List<Element> listrow = listnode.get(1).elements();
			for (Element element1 : listrow) {
				Map<String, String> nodeMap = new HashMap<String, String>();
				List<Element> listrow2 = element1.elements();
				if(listrow2!=null && listrow2.size()>0){
					List<Element> listrow3 = listrow2.get(0).elements();
					Element element = listrow3.get(0);
					List<Attribute> attrs = element.attributes();  
					if(attrs!=null && attrs.size()>0){
						for (Attribute attr : attrs) {  
							nodeMap.put(attr.getName(), attr.getValue());
			            }
					}
				} else {
					List<Attribute> attrs = element1.attributes();
					if(attrs!=null && attrs.size()>0){
						for (Attribute attr : attrs) {  
							nodeMap.put(attr.getName(), attr.getValue());
			            }
					}
				}
				
				list.add(nodeMap);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}  
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static  List<Map<String, String>> gwyinfoF(String path){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> nodeMap = new HashMap<String, String>();
		Map<String, String> tableMap = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		Document doc;
		try {
			InputStream in = new FileInputStream(new File(path));  
			doc = reader.read(in);
			Element root = doc.getRootElement();
			List<Element> listnodes = root.elements("node");
	        for (Element element : listnodes) {
				nodeMap.put(element.attributeValue("name"), element.attributeValue("value"));
			}
	        Element tables = root.element("tablelist");
	        List<Element> listtables = tables.elements("table");
	        for (Element element2 : listtables) {
	        	tableMap.put(element2.attributeValue("name"), element2.attributeValue("name"));
			}
	        list.add(nodeMap);
	        list.add(tableMap);
		        
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> gwyA_BF(String path){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		SAXReader reader = new SAXReader();
		Document doc;
		try {
			InputStream in = new FileInputStream(new File(path));  
			doc = reader.read(in);
			Element root = doc.getRootElement();
			
			List<Element> listnode = root.elements();
			List<Element> listrow = listnode.get(1).elements();
			for (Element element : listrow) {
				Map<String, String> nodeMap = new HashMap<String, String>();
				List<Attribute> attrs = element.attributes();  
				for (Attribute attr : attrs) {  
					nodeMap.put(attr.getName(), attr.getValue());
				
	            }
				list.add(nodeMap);
			}
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> gwyA57F(String path){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		SAXReader reader = new SAXReader();
		Document doc;
		try {
			InputStream in = new FileInputStream(new File(path));  
			doc = reader.read(in);
			Element root = doc.getRootElement();
			
			List<Element> listnode = root.elements();
			List<Element> listrow = listnode.get(1).elements();
			for (Element element1 : listrow) {
				Map<String, String> nodeMap = new HashMap<String, String>();
				List<Element> listrow2 = element1.elements();
				if(listrow2!=null && listrow2.size()>0){
					List<Element> listrow3 = listrow2.get(0).elements();
					Element element = listrow3.get(0);
					List<Attribute> attrs = element.attributes();  
					if(attrs!=null && attrs.size()>0){
						for (Attribute attr : attrs) {  
							nodeMap.put(attr.getName(), attr.getValue());
			            }
					}
				} else {
					List<Attribute> attrs = element1.attributes();
					if(attrs!=null && attrs.size()>0){
						for (Attribute attr : attrs) {  
							nodeMap.put(attr.getName(), attr.getValue());
			            }
					}
				}
				
				list.add(nodeMap);
			}
			CommonQueryBS.systemOut(net.sf.json.JSONArray.fromObject(list).toString());;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
		return list;
	}
	
	public static void main(String[] args) {
//		String s = HBUtil.class.getClassLoader().getResource("/").getPath(); 
//		System.out.println(s);
		gwyA57F("C:\\Users\\ZDWX\\Desktop\\按机构导出文件_1111110111_国家税务总局20160604163856\\Table\\A57.xml");
	}
	
	@SuppressWarnings("unchecked")
	public static  List<Map<String, String>> gwyinfo2(String path){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> nodeMap = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		Document doc;
		try {
			InputStream in = new FileInputStream(new File(path));  
			doc = reader.read(in);
			Element root = doc.getRootElement();
			List<Element> listnodes = root.elements("node");
	        for (Element element : listnodes) {
				nodeMap.put(element.attributeValue("name"), element.attributeValue("value"));
			}
	        list.add(nodeMap);
//			System.out.println(net.sf.json.JSONArray.fromObject(list));;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
		return list;
	}
}
