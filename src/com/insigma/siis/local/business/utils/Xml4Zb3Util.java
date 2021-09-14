package com.insigma.siis.local.business.utils;

import java.beans.IntrospectionException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class Xml4Zb3Util {
	
	public static void List2Xml(List list, String table, String uuid) {
		// TODO Auto-generated method stub
		if(table.equals("A01")){
			A01toXml(list, uuid);
		} else if(table.equals("A02")){
			A02toXml(list, uuid);
		} else if(table.equals("A06")){
			A06toXml(list, uuid);
		} else if(table.equals("A08")){
			A08toXml(list, uuid);
		} else if(table.equals("A11")){
			A11toXml(list, uuid);
		} else if(table.equals("A14")){
			A14toXml(list, uuid);
		} else if(table.equals("A15")){
			A15toXml(list, uuid);
		} else if(table.equals("A29")){
			A29toXml(list, uuid);
		} else if(table.equals("A30")){
			A30toXml(list, uuid);
		} else if(table.equals("A31")){
			A31toXml(list, uuid);
		} else if(table.equals("A36")){
			A36toXml(list, uuid);
		} else if(table.equals("A37")){
			A37toXml(list, uuid);
		} else if(table.equals("A41")){
			A41toXml(list, uuid);
		} else if(table.equals("A53")){
			A53toXml(list, uuid);
		} else if(table.equals("A57")){
			A57toXml(list, uuid);
		} else if(table.equals("B01")){
			B01toXml(list, uuid);
		} else if(table.equals("info")){
			infotoXml(list, uuid);
		}
		

	}

	private static void infotoXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Map map = (Map) list.get(0);
		Document document = DocumentHelper.createDocument();
        //添加元素 root
        Element root = document.addElement("root");
        //添加元素 root
        Element node1 = root.addElement("node");
        node1.addAttribute("name", "type");
        node1.addAttribute("value", emptoString(map.get("type")));
        Element node2 = root.addElement("node");
        node2.addAttribute("name", "time");
        node2.addAttribute("value", emptoString(map.get("time")));
        Element node3 = root.addElement("node");
        node3.addAttribute("name", "dataverion");
        node3.addAttribute("value", emptoString(map.get("dataverion")));
        Element node4 = root.addElement("node");
        node4.addAttribute("name", "psncount");
        node4.addAttribute("value", emptoString(map.get("psncount")));
        Element node5 = root.addElement("node");
        node5.addAttribute("name", "photodir");
        node5.addAttribute("value", emptoString(map.get("photodir")));
        Element node6 = root.addElement("node");
        node6.addAttribute("name", "B0101");
        node6.addAttribute("value", emptoString(map.get("B0101")));
        Element node7 = root.addElement("node");
        node7.addAttribute("name", "B0111");
        node7.addAttribute("value", emptoString(map.get("B0111")));
        Element node8 = root.addElement("node");
        node8.addAttribute("name", "B0114");
        node8.addAttribute("value", emptoString(map.get("B0114")));
        Element node9 = root.addElement("node");
        node9.addAttribute("name", "B0194");
        node9.addAttribute("value", emptoString(map.get("B0194")));
        Element node10 = root.addElement("node");
        node10.addAttribute("name", "linkpsn");
        node10.addAttribute("value", emptoString(map.get("linkpsn")));
        Element node11 = root.addElement("node");
        node11.addAttribute("name", "linktel");
        node11.addAttribute("value", emptoString(map.get("linktel")));
        Element node12 = root.addElement("node");
        node12.addAttribute("name", "remark");
        node12.addAttribute("value", emptoString(map.get("remark")));
        Element tablelist = root.addElement("tablelist");
        String tables1[] = {"B01","A02","A01","A08","A53","A36","A14", "A15","A29", "A31","A30","A11","A37","A57", "A06",     
				"WAGE_W00","WAGE_W01","WAGE_W02","WAGE_W03","WAGE_W04","WAGE_W05","WAGE_W06","A41","WAGE_W30","WAGE_W40", "WAGE_W50","A02_WAGE"};
		for (int i = 0; i < tables1.length; i++) {
			String string = tables1[i];
			Element table = tablelist.addElement("tablelist");
			table.addAttribute("name", string);
		}
		
		XMLWriter out = null;
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        try {
            File xmlFile = new File(uuid + "gwyinfo.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"GBK");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            out = new XMLWriter(bw, format);
            out.setEscapeText(false);
            out.write(document);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private static void B01toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema 
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType B0107
        Element B0107 = eleElement.addElement("s:AttributeType");
        B0107.addAttribute("name", "B0107");
        B0107.addAttribute("rs:number", "1");
        B0107.addAttribute("rs:nullable", "true");
        B0107.addAttribute("rs:write", "true");
        B0107.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0107.addAttribute("rs:baseschema", "DBO");
        B0107.addAttribute("rs:basetable", "B01");
        B0107.addAttribute("rs:basecolumn", "B0107");
        //s:datatype
        Element B0107datatype = B0107.addElement("s:datatype");
        B0107datatype.addAttribute("dt:type", "string");
        B0107datatype.addAttribute("rs:dbtype", "str");
        B0107datatype.addAttribute("dt:maxLength", "200");
     // 2----------------s:AttributeType B0101
        Element B0101 = eleElement.addElement("s:AttributeType");
        B0101.addAttribute("name", "B0101");
        B0101.addAttribute("rs:number", "2");
        B0101.addAttribute("rs:nullable", "true");
        B0101.addAttribute("rs:write", "true");
        B0101.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0101.addAttribute("rs:baseschema", "DBO");
        B0101.addAttribute("rs:basetable", "B01");
        B0101.addAttribute("rs:basecolumn", "B0101");
        //s:datatype
        Element B0101datatype = B0101.addElement("s:datatype");
        B0101datatype.addAttribute("dt:type", "string");
        B0101datatype.addAttribute("rs:dbtype", "str");
        B0101datatype.addAttribute("dt:maxLength", "200");
     // 3----------------s:AttributeType B0104
        Element B0104 = eleElement.addElement("s:AttributeType");
        B0104.addAttribute("name", "B0104");
        B0104.addAttribute("rs:number", "3");
        B0104.addAttribute("rs:nullable", "true");
        B0104.addAttribute("rs:write", "true");
        B0104.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0104.addAttribute("rs:baseschema", "DBO");
        B0104.addAttribute("rs:basetable", "B01");
        B0104.addAttribute("rs:basecolumn", "B0104");
        //s:datatype
        Element B0104datatype = B0104.addElement("s:datatype");
        B0104datatype.addAttribute("dt:type", "string");
        B0104datatype.addAttribute("rs:dbtype", "str");
        B0104datatype.addAttribute("dt:maxLength", "200");
      // 4----------------s:AttributeType A5304
        Element B0180 = eleElement.addElement("s:AttributeType");
        B0180.addAttribute("name", "B0180");
        B0180.addAttribute("rs:number", "4");
        B0180.addAttribute("rs:nullable", "true");
        B0180.addAttribute("rs:write", "true");
        B0180.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0180.addAttribute("rs:baseschema", "DBO");
        B0180.addAttribute("rs:basetable", "B01");
        B0180.addAttribute("rs:basecolumn", "B0180");
        //s:datatype
        Element B0180datatype = B0180.addElement("s:datatype");
        B0180datatype.addAttribute("dt:type", "string");
        B0180datatype.addAttribute("rs:dbtype", "str");
        B0180datatype.addAttribute("dt:maxLength", "500");
     // 5----------------s:AttributeType B0111
        Element B0111 = eleElement.addElement("s:AttributeType");
        B0111.addAttribute("name", "B0111");
        B0111.addAttribute("rs:number", "5");
        B0111.addAttribute("rs:nullable", "true");
        B0111.addAttribute("rs:write", "true");
        B0111.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0111.addAttribute("rs:baseschema", "DBO");
        B0111.addAttribute("rs:basetable", "B01");
        B0111.addAttribute("rs:basecolumn", "B0111");
        //s:datatype
        Element B0111datatype = B0111.addElement("s:datatype");
        B0111datatype.addAttribute("dt:type", "string");
        B0111datatype.addAttribute("rs:dbtype", "str");
        B0111datatype.addAttribute("dt:maxLength", "199");
        B0111datatype.addAttribute("rs:maybenull", "false");
     // 6----------------s:AttributeType A5317
        Element B0117 = eleElement.addElement("s:AttributeType");
        B0117.addAttribute("name", "B0117");
        B0117.addAttribute("rs:number", "6");
        B0117.addAttribute("rs:nullable", "true");
        B0117.addAttribute("rs:write", "true");
        B0117.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0117.addAttribute("rs:baseschema", "DBO");
        B0117.addAttribute("rs:basetable", "B01");
        B0117.addAttribute("rs:basecolumn", "B0117");
        //s:datatype
        Element B0117datatype = B0117.addElement("s:datatype");
        B0117datatype.addAttribute("dt:type", "string");
        B0117datatype.addAttribute("rs:dbtype", "str");
        B0117datatype.addAttribute("dt:maxLength", "8");
     // 7----------------s:AttributeType A5319
        Element B0121 = eleElement.addElement("s:AttributeType");
        B0121.addAttribute("name", "B0121");
        B0121.addAttribute("rs:number", "7");
        B0121.addAttribute("rs:nullable", "true");
        B0121.addAttribute("rs:write", "true");
        B0121.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0121.addAttribute("rs:baseschema", "DBO");
        B0121.addAttribute("rs:basetable", "B01");
        B0121.addAttribute("rs:basecolumn", "B0121");
        //s:datatype
        Element B0121datatype = B0121.addElement("s:datatype");
        B0121datatype.addAttribute("dt:type", "string");
        B0121datatype.addAttribute("rs:dbtype", "str");
        B0121datatype.addAttribute("dt:maxLength", "199");
     // 8----------------s:AttributeType A1424
        Element B0124 = eleElement.addElement("s:AttributeType");
        B0124.addAttribute("name", "B0124");
        B0124.addAttribute("rs:number", "8");
        B0124.addAttribute("rs:nullable", "true");
        B0124.addAttribute("rs:write", "true");
        B0124.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0124.addAttribute("rs:baseschema", "DBO");
        B0124.addAttribute("rs:basetable", "B01");
        B0124.addAttribute("rs:basecolumn", "B0124");
        //s:datatype
        Element B0124datatype = B0124.addElement("s:datatype");
        B0124datatype.addAttribute("dt:type", "string");
        B0124datatype.addAttribute("rs:dbtype", "str");
        B0124datatype.addAttribute("dt:maxLength", "8");
     // 9----------------s:AttributeType A1414
        Element B0127 = eleElement.addElement("s:AttributeType");
        B0127.addAttribute("name", "B0127");
        B0127.addAttribute("rs:number", "9");
        B0127.addAttribute("rs:nullable", "true");
        B0127.addAttribute("rs:write", "true");
        B0127.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0127.addAttribute("rs:baseschema", "DBO");
        B0127.addAttribute("rs:basetable", "B01");
        B0127.addAttribute("rs:basecolumn", "B0127");
        //s:datatype
        Element B0127datatype = B0127.addElement("s:datatype");
        B0127datatype.addAttribute("dt:type", "string");
        B0127datatype.addAttribute("rs:dbtype", "str");
        B0127datatype.addAttribute("dt:maxLength", "8");
      // 10----------------s:AttributeType B0124
        Element B0131 = eleElement.addElement("s:AttributeType");
        B0131.addAttribute("name", "B0131");
        B0131.addAttribute("rs:number", "10");
        B0131.addAttribute("rs:nullable", "true");
        B0131.addAttribute("rs:write", "true");
        B0131.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0131.addAttribute("rs:baseschema", "DBO");
        B0131.addAttribute("rs:basetable", "B01");
        B0131.addAttribute("rs:basecolumn", "B0131");
        //s:datatype
        Element B0131datatype = B0131.addElement("s:datatype");
        B0131datatype.addAttribute("dt:type", "string");
        B0131datatype.addAttribute("rs:dbtype", "str");
        B0131datatype.addAttribute("dt:maxLength", "8");
     // 11----------------s:AttributeType A1415
        Element B0183 = eleElement.addElement("s:AttributeType");
        B0183.addAttribute("name", "B0183");
        B0183.addAttribute("rs:number", "11");
        B0183.addAttribute("rs:nullable", "true");
        B0183.addAttribute("rs:write", "true");
        B0183.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0183.addAttribute("rs:baseschema", "DBO");
        B0183.addAttribute("rs:basetable", "B01");
        B0183.addAttribute("rs:basecolumn", "B0183");
        //s:datatype
        Element B0183datatype = B0183.addElement("s:datatype");
        B0183datatype.addAttribute("dt:type", "int");
        B0183datatype.addAttribute("dt:maxLength", "4");
        B0183datatype.addAttribute("rs:precision", "10");
        B0183datatype.addAttribute("rs:fixedlength", "true");
     // 12----------------s:AttributeType A1131
        Element B0185 = eleElement.addElement("s:AttributeType");
        B0185.addAttribute("name", "B0185");
        B0185.addAttribute("rs:number", "12");
        B0185.addAttribute("rs:nullable", "true");
        B0185.addAttribute("rs:write", "true");
        B0185.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0185.addAttribute("rs:baseschema", "DBO");
        B0185.addAttribute("rs:basetable", "B01");
        B0185.addAttribute("rs:basecolumn", "B0185");
        //s:datatype
        Element B0185datatype = B0185.addElement("s:datatype");
        B0185datatype.addAttribute("dt:type", "int");
        B0185datatype.addAttribute("dt:maxLength", "4");
        B0185datatype.addAttribute("rs:precision", "10");
        B0185datatype.addAttribute("rs:fixedlength", "true");
     // 13----------------s:AttributeType A1134
        Element B0188 = eleElement.addElement("s:AttributeType");
        B0188.addAttribute("name", "B0188");
        B0188.addAttribute("rs:number", "13");
        B0188.addAttribute("rs:nullable", "true");
        B0188.addAttribute("rs:write", "true");
        B0188.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0188.addAttribute("rs:baseschema", "DBO");
        B0188.addAttribute("rs:basetable", "B01");
        B0188.addAttribute("rs:basecolumn", "B0188");
        //s:datatype
        Element B0188datatype = B0188.addElement("s:datatype");
        B0188datatype.addAttribute("dt:type", "int");
        B0188datatype.addAttribute("dt:maxLength", "4");
        B0188datatype.addAttribute("rs:precision", "10");
        B0188datatype.addAttribute("rs:fixedlength", "true");
     // 14----------------s:AttributeType A1151
        Element B0150 = eleElement.addElement("s:AttributeType");
        B0150.addAttribute("name", "B0150");
        B0150.addAttribute("rs:number", "14");
        B0150.addAttribute("rs:nullable", "true");
        B0150.addAttribute("rs:write", "true");
        B0150.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0150.addAttribute("rs:baseschema", "DBO");
        B0150.addAttribute("rs:basetable", "B01");
        B0150.addAttribute("rs:basecolumn", "B0150");
        //s:datatype
        Element B0150datatype = B0150.addElement("s:datatype");
        B0150datatype.addAttribute("dt:type", "int");
        B0150datatype.addAttribute("dt:maxLength", "4");
        B0150datatype.addAttribute("rs:precision", "10");
        B0150datatype.addAttribute("rs:fixedlength", "true");
     // 15----------------s:AttributeType UPDATED
        Element B0190 = eleElement.addElement("s:AttributeType");
        B0190.addAttribute("name", "B0190");
        B0190.addAttribute("rs:number", "15");
        B0190.addAttribute("rs:nullable", "true");
        B0190.addAttribute("rs:write", "true");
        B0190.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0190.addAttribute("rs:baseschema", "DBO");
        B0190.addAttribute("rs:basetable", "B01");
        B0190.addAttribute("rs:basecolumn", "B0190");
        //s:datatype
        Element B0190datatype = B0190.addElement("s:datatype");
        B0190datatype.addAttribute("dt:type", "int");
        B0190datatype.addAttribute("dt:maxLength", "4");
        B0190datatype.addAttribute("rs:precision", "10");
        B0190datatype.addAttribute("rs:fixedlength", "true");
        
      // 16----------------s:AttributeType A0899
        Element B0191A = eleElement.addElement("s:AttributeType");
        B0191A.addAttribute("name", "B0191A");
        B0191A.addAttribute("rs:number", "16");
        B0191A.addAttribute("rs:nullable", "true");
        B0191A.addAttribute("rs:write", "true");
        B0191A.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0191A.addAttribute("rs:baseschema", "DBO");
        B0191A.addAttribute("rs:basetable", "B01");
        B0191A.addAttribute("rs:basecolumn", "B0191A");
        //s:datatype
        Element B0191Adatatype = B0191A.addElement("s:datatype");
        B0191Adatatype.addAttribute("dt:type", "int");
        B0191Adatatype.addAttribute("dt:maxLength", "4");
        B0191Adatatype.addAttribute("rs:precision", "10");
        B0191Adatatype.addAttribute("rs:fixedlength", "true");
     // 17----------------s:AttributeType A0898
        Element B0192 = eleElement.addElement("s:AttributeType");
        B0192.addAttribute("name", "B0192");
        B0192.addAttribute("rs:number", "17");
        B0192.addAttribute("rs:nullable", "true");
        B0192.addAttribute("rs:write", "true");
        B0192.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0192.addAttribute("rs:baseschema", "DBO");
        B0192.addAttribute("rs:basetable", "B01");
        B0192.addAttribute("rs:basecolumn", "B0192");
        //s:datatype
        Element B0192datatype = B0192.addElement("s:datatype");
        B0192datatype.addAttribute("dt:type", "int");
        B0192datatype.addAttribute("dt:maxLength", "4");
        B0192datatype.addAttribute("rs:precision", "10");
        B0192datatype.addAttribute("rs:fixedlength", "true");
        // 18----------------s:AttributeType A0831
        Element B0193 = eleElement.addElement("s:AttributeType");
        B0193.addAttribute("name", "B0193");
        B0193.addAttribute("rs:number", "18");
        B0193.addAttribute("rs:nullable", "true");
        B0193.addAttribute("rs:write", "true");
        B0193.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0193.addAttribute("rs:baseschema", "DBO");
        B0193.addAttribute("rs:basetable", "B01");
        B0193.addAttribute("rs:basecolumn", "B0193");
        //s:datatype
        Element B0193datatype = B0193.addElement("s:datatype");
        B0193datatype.addAttribute("dt:type", "int");
        B0193datatype.addAttribute("dt:maxLength", "4");
        B0193datatype.addAttribute("rs:precision", "10");
        B0193datatype.addAttribute("rs:fixedlength", "true");
     // 19----------------s:AttributeType A0838
        Element B0189 = eleElement.addElement("s:AttributeType");
        B0189.addAttribute("name", "B0189");
        B0189.addAttribute("rs:number", "19");
        B0189.addAttribute("rs:nullable", "true");
        B0189.addAttribute("rs:write", "true");
        B0189.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0189.addAttribute("rs:baseschema", "DBO");
        B0189.addAttribute("rs:basetable", "B01");
        B0189.addAttribute("rs:basecolumn", "B0189");
        //s:datatype
        Element B0189datatype = B0189.addElement("s:datatype");
        B0189datatype.addAttribute("dt:type", "int");
        B0189datatype.addAttribute("dt:maxLength", "4");
        B0189datatype.addAttribute("rs:precision", "10");
        B0189datatype.addAttribute("rs:fixedlength", "true");
     // 20----------------s:AttributeType A0832
        Element B0114 = eleElement.addElement("s:AttributeType");
        B0114.addAttribute("name", "B0114");
        B0114.addAttribute("rs:number", "20");
        B0114.addAttribute("rs:nullable", "true");
        B0114.addAttribute("rs:write", "true");
        B0114.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0114.addAttribute("rs:baseschema", "DBO");
        B0114.addAttribute("rs:basetable", "B01");
        B0114.addAttribute("rs:basecolumn", "B0114");
        //s:datatype
        Element B0114datatype = B0114.addElement("s:datatype");
        B0114datatype.addAttribute("dt:type", "string");
        B0114datatype.addAttribute("dt:maxLength", "68"); 
     // 21----------------s:AttributeType A0839
        Element B0191 = eleElement.addElement("s:AttributeType");
        B0191.addAttribute("name", "B0191");
        B0191.addAttribute("rs:number", "21");
        B0191.addAttribute("rs:nullable", "true");
        B0191.addAttribute("rs:write", "true");
        B0191.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0191.addAttribute("rs:baseschema", "DBO");
        B0191.addAttribute("rs:basetable", "B01");
        B0191.addAttribute("rs:basecolumn", "B0191");
        //s:datatype
        Element B0191datatype = B0191.addElement("s:datatype");
        B0114datatype.addAttribute("dt:type", "string");
        B0114datatype.addAttribute("rs:dbtype", "str");
        B0114datatype.addAttribute("dt:maxLength", "10"); 
        
     // 22----------------s:AttributeType A0811
        Element B0194 = eleElement.addElement("s:AttributeType");
        B0194.addAttribute("name", "B0194");
        B0194.addAttribute("rs:number", "22");
        B0194.addAttribute("rs:nullable", "true");
        B0194.addAttribute("rs:write", "true");
        B0194.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0194.addAttribute("rs:baseschema", "DBO");
        B0194.addAttribute("rs:basetable", "B01");
        B0194.addAttribute("rs:basecolumn", "B0194");
        //s:datatype
        Element B0194datatype = B0194.addElement("s:datatype");
        B0194datatype.addAttribute("dt:type", "string");
        B0194datatype.addAttribute("rs:dbtype", "str");
        B0194datatype.addAttribute("dt:maxLength", "1"); 
        // 23----------------s:AttributeType B0131
        Element B0227 = eleElement.addElement("s:AttributeType");
        B0227.addAttribute("name", "B0227");
        B0227.addAttribute("rs:number", "23");
        B0227.addAttribute("rs:nullable", "true");
        B0227.addAttribute("rs:write", "true");
        B0227.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0227.addAttribute("rs:baseschema", "DBO");
        B0227.addAttribute("rs:basetable", "B01");
        B0227.addAttribute("rs:basecolumn", "B0227");
        //s:datatype
        Element B0227datatype = B0227.addElement("s:datatype");
        B0227datatype.addAttribute("dt:type", "int");
        B0227datatype.addAttribute("dt:maxLength", "4");
        B0227datatype.addAttribute("rs:precision", "10"); 
        B0227datatype.addAttribute("rs:fixedlength", "true");   
     // 24----------------s:AttributeType A0141
        Element B0232 = eleElement.addElement("s:AttributeType");
        B0232.addAttribute("name", "B0232");
        B0232.addAttribute("rs:number", "24");
        B0232.addAttribute("rs:nullable", "true");
        B0232.addAttribute("rs:write", "true");
        B0232.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0232.addAttribute("rs:baseschema", "DBO");
        B0232.addAttribute("rs:basetable", "B01");
        B0232.addAttribute("rs:basecolumn", "B0232");
        //s:datatype
        Element B0232tatype = B0232.addElement("s:datatype");
        B0232tatype.addAttribute("dt:type", "int");
        B0232tatype.addAttribute("dt:maxLength", "4");
        B0232tatype.addAttribute("rs:precision", "10"); 
        B0232tatype.addAttribute("rs:fixedlength", "true"); 
        // 25----------------s:AttributeType A3921
        Element UPDATED = eleElement.addElement("s:AttributeType");
        UPDATED.addAttribute("name", "UPDATED");
        UPDATED.addAttribute("rs:number", "25");
        UPDATED.addAttribute("rs:nullable", "true");
        UPDATED.addAttribute("rs:write", "true");
        UPDATED.addAttribute("rs:basecatalog", "OFFICIALV2");
        UPDATED.addAttribute("rs:baseschema", "DBO");
        UPDATED.addAttribute("rs:basetable", "B01");
        UPDATED.addAttribute("rs:basecolumn", "UPDATED");
        //s:datatype
        Element UPDATEDdatatype = UPDATED.addElement("s:datatype");
        UPDATEDdatatype.addAttribute("dt:type", "string");
        UPDATEDdatatype.addAttribute("rs:dbtype", "str");
        UPDATEDdatatype.addAttribute("dt:maxLength", "1");
        UPDATEDdatatype.addAttribute("rs:fixedlength", "true");
        // 26----------------s:AttributeType A3927
        Element B0141 = eleElement.addElement("s:AttributeType");
        B0141.addAttribute("name", "B0141");
        B0141.addAttribute("rs:number", "26");
        B0141.addAttribute("rs:nullable", "true");
        B0141.addAttribute("rs:write", "true");
        B0141.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0141.addAttribute("rs:baseschema", "DBO");
        B0141.addAttribute("rs:basetable", "B01");
        B0141.addAttribute("rs:basecolumn", "B0141");
        //s:datatype
        Element B0141datatype = B0141.addElement("s:datatype");
        B0141datatype.addAttribute("dt:type", "string");
        B0141datatype.addAttribute("rs:dbtype", "str");
        B0141datatype.addAttribute("dt:maxLength", "68");
        // 27----------------s:AttributeType A0144
        Element B0140 = eleElement.addElement("s:AttributeType");
        B0140.addAttribute("name", "A0271");
        B0140.addAttribute("rs:number", "27");
        B0140.addAttribute("rs:nullable", "true");
        B0140.addAttribute("rs:write", "true");
        B0140.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0140.addAttribute("rs:baseschema", "DBO");
        B0140.addAttribute("rs:basetable", "B01");
        B0140.addAttribute("rs:basecolumn", "A0271");
        //s:datatype
        Element B0140datatype = B0140.addElement("s:datatype");
        B0140datatype.addAttribute("dt:type", "string");
        B0140datatype.addAttribute("rs:dbtype", "str");
        B0140datatype.addAttribute("dt:maxLength", "68");
        // 28----------------s:AttributeType B0140B
        Element B01IP = eleElement.addElement("s:AttributeType");
        B01IP.addAttribute("name", "B01IP");
        B01IP.addAttribute("rs:number", "28");
        B01IP.addAttribute("rs:nullable", "true");
        B01IP.addAttribute("rs:write", "true");
        B01IP.addAttribute("rs:basecatalog", "OFFICIALV2");
        B01IP.addAttribute("rs:baseschema", "DBO");
        B01IP.addAttribute("rs:basetable", "B01");
        B01IP.addAttribute("rs:basecolumn", "B01IP");
        //s:datatype
        Element B01IPdatatype = B01IP.addElement("s:datatype");
        B01IPdatatype.addAttribute("dt:type", "string");
        B01IPdatatype.addAttribute("rs:dbtype", "str");
        B01IPdatatype.addAttribute("dt:maxLength", "32");
        // 29----------------s:AttributeType B0140C
        Element B01TRANS = eleElement.addElement("s:AttributeType");
        B01TRANS.addAttribute("name", "B01TRANS");
        B01TRANS.addAttribute("rs:number", "29");
        B01TRANS.addAttribute("rs:nullable", "true");
        B01TRANS.addAttribute("rs:write", "true");
        B01TRANS.addAttribute("rs:basecatalog", "OFFICIALV2");
        B01TRANS.addAttribute("rs:baseschema", "DBO");
        B01TRANS.addAttribute("rs:basetable", "B01");
        B01TRANS.addAttribute("rs:basecolumn", "B01TRANS");
        //s:datatype
        Element B01TRANSdatatype = B01TRANS.addElement("s:datatype");
        B01TRANSdatatype.addAttribute("dt:type", "int");
        B01TRANSdatatype.addAttribute("dt:maxLength", "4");
        B01TRANSdatatype.addAttribute("rs:precision", "10"); 
        B01TRANSdatatype.addAttribute("rs:fixedlength", "true");  
     // 30----------------s:AttributeType A0151
        Element B0233 = eleElement.addElement("s:AttributeType");
        B0233.addAttribute("name", "B0233");
        B0233.addAttribute("rs:number", "30");
        B0233.addAttribute("rs:nullable", "true");
        B0233.addAttribute("rs:write", "true");
        B0233.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0233.addAttribute("rs:baseschema", "DBO");
        B0233.addAttribute("rs:basetable", "B01");
        B0233.addAttribute("rs:basecolumn", "B0233");
        //s:datatype
        Element B0233datatype = B0233.addElement("s:datatype");
        B0233datatype.addAttribute("dt:type", "int");
        B0233datatype.addAttribute("dt:maxLength", "4");
        B0233datatype.addAttribute("rs:precision", "10"); 
        B0233datatype.addAttribute("rs:fixedlength", "true");  
     // 31----------------s:AttributeType A0153
        Element SORTID = eleElement.addElement("s:AttributeType");
        SORTID.addAttribute("name", "SORTID");
        SORTID.addAttribute("rs:number", "31");
        SORTID.addAttribute("rs:nullable", "true");
        SORTID.addAttribute("rs:write", "true");
        SORTID.addAttribute("rs:basecatalog", "OFFICIALV2");
        SORTID.addAttribute("rs:baseschema", "DBO");
        SORTID.addAttribute("rs:basetable", "B01");
        SORTID.addAttribute("rs:basecolumn", "SORTID");
        //s:datatype
        Element SORTIDdatatype = SORTID.addElement("s:datatype");
        SORTIDdatatype.addAttribute("dt:type", "int");
        SORTIDdatatype.addAttribute("dt:maxLength", "4");
        SORTIDdatatype.addAttribute("rs:precision", "10"); 
        SORTIDdatatype.addAttribute("rs:fixedlength", "true");
     // 32----------------s:AttributeType A0155
        Element B0143 = eleElement.addElement("s:AttributeType");
        B0143.addAttribute("name", "B0143");
        B0143.addAttribute("rs:number", "32");
        B0143.addAttribute("rs:nullable", "true");
        B0143.addAttribute("rs:write", "true");
        B0143.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0143.addAttribute("rs:baseschema", "DBO");
        B0143.addAttribute("rs:basetable", "B01");
        B0143.addAttribute("rs:basecolumn", "B0143");
        //s:datatype
        Element B0143datatype = B0143.addElement("s:datatype");
        B0143datatype.addAttribute("dt:type", "string");
        B0143datatype.addAttribute("rs:dbtype", "str");
        B0143datatype.addAttribute("dt:maxLength", "8");
     // 33----------------s:AttributeType A0157
        Element B0142 = eleElement.addElement("s:AttributeType");
        B0142.addAttribute("name", "B0142");
        B0142.addAttribute("rs:number", "33");
        B0142.addAttribute("rs:nullable", "true");
        B0142.addAttribute("rs:write", "true");
        B0142.addAttribute("rs:basecatalog", "OFFICIALV2");
        B0142.addAttribute("rs:baseschema", "DBO");
        B0142.addAttribute("rs:basetable", "B01");
        B0142.addAttribute("rs:basecolumn", "B0142");
        //s:datatype
        Element B0142datatype = B0142.addElement("s:datatype");
        B0142datatype.addAttribute("dt:type", "int");
        B0142datatype.addAttribute("dt:maxLength", "4");
        B0142datatype.addAttribute("rs:precision", "10"); 
        B0142datatype.addAttribute("rs:fixedlength", "true");  
        
        /**
         * 加载数据 
         */
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			B01 a01 = (B01) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("B0107", emptoString(map.get("B0107")));
				zrow.addAttribute("B0101", emptoString(map.get("B0101")));
				zrow.addAttribute("B0104", emptoString(map.get("B0104")));
				zrow.addAttribute("B0180", emptoString(map.get("B0180")));
				zrow.addAttribute("B0111", emptoString(map.get("B0111")));
				zrow.addAttribute("B0117", emptoString(map.get("B0117")));
				zrow.addAttribute("B0121", emptoString(map.get("B0121")));
				zrow.addAttribute("B0124", emptoString(map.get("B0124")));
				zrow.addAttribute("B0127", emptoString(map.get("B0127")));
				zrow.addAttribute("B0131", emptoString(map.get("B0131")));
				
				zrow.addAttribute("B0183", emptoString(map.get("B0183")));
				zrow.addAttribute("B0185", emptoString(map.get("B0185")));
				zrow.addAttribute("B0188", emptoString(map.get("B0188")));
				zrow.addAttribute("B0150", emptoString(map.get("B0150")));
				zrow.addAttribute("B0190", emptoString(map.get("B0190")));
				zrow.addAttribute("B0191A", emptoString(map.get("B0191A")));
				zrow.addAttribute("B0192", emptoString(map.get("B0192")));
				zrow.addAttribute("B0193", emptoString(map.get("B0193")));
				zrow.addAttribute("B0189", emptoString(map.get("B0189")));
				zrow.addAttribute("B0114", emptoString(map.get("B0114")));
				
				zrow.addAttribute("B0194", emptoString(map.get("B0194")));
				zrow.addAttribute("B0227", emptoString(map.get("B0227")));
				zrow.addAttribute("B0232", emptoString(map.get("B0232")));
				zrow.addAttribute("UPDATED", emptoString(map.get("UPDATED")));
				zrow.addAttribute("B0141", emptoString(map.get("B0141")));
				zrow.addAttribute("B0140", emptoString(map.get("B0140")));
				zrow.addAttribute("B01TRANS", emptoString(map.get("B01TRANS")));
				zrow.addAttribute("B0233", emptoString(map.get("B0233")));
				zrow.addAttribute("SORTID", emptoString(map.get("SORTID")));
				zrow.addAttribute("B0143", emptoString(map.get("B0143")));
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        XMLWriter out = null;
        
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        
        try {
            File xmlFile = new File(uuid + "Table/" +"B01.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}

	private static void A57toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A0000
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "1");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A57");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "60");
     // 2----------------s:AttributeType A5300
        Element A5714 = eleElement.addElement("s:AttributeType");
        A5714.addAttribute("name", "A5714");
        A5714.addAttribute("rs:number", "2");
        A5714.addAttribute("rs:nullable", "true");
        A5714.addAttribute("rs:write", "true");
        A5714.addAttribute("rs:basecatalog", "OFFICIALV2");
        A5714.addAttribute("rs:baseschema", "DBO");
        A5714.addAttribute("rs:basetable", "A57");
        A5714.addAttribute("rs:basecolumn", "A5714");
        //s:datatype
        Element A5714datatype = A5714.addElement("s:datatype");
        A5714datatype.addAttribute("dt:type", "string");
        A5714datatype.addAttribute("rs:dbtype", "str");
        A5714datatype.addAttribute("dt:maxLength", "100");
		/**
         * 加载数据 
         */
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A57 a01 = (A57) object;
			Element zrow = rsdata.addElement("rs:update");
			Element rsoriginal = zrow.addElement("rs:original");
			try {
				Element zrow1 = rsoriginal.addElement("z:row");
				Map map = Map2Temp.convertBean(a01);
				zrow1.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow1.addAttribute("A5714", emptoString(map.get("A5714")));
				Element zrow2 = zrow.addElement("z:row");
				zrow2.addAttribute("A5714", emptoString(map.get("A5714")));
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        XMLWriter out = null;
        
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        
        try {
            File xmlFile = new File(uuid + "Table/" +"A57.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private static void A53toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A5399
        Element A5399 = eleElement.addElement("s:AttributeType");
        A5399.addAttribute("name", "A5399");
        A5399.addAttribute("rs:number", "1");
        A5399.addAttribute("rs:nullable", "true");
        A5399.addAttribute("rs:write", "true");
        A5399.addAttribute("rs:basecatalog", "OFFICIALV2");
        A5399.addAttribute("rs:baseschema", "DBO");
        A5399.addAttribute("rs:basetable", "A53");
        A5399.addAttribute("rs:basecolumn", "A5399");
        //s:datatype
        Element A5399datatype = A5399.addElement("s:datatype");
        A5399datatype.addAttribute("dt:type", "string");
        A5399datatype.addAttribute("rs:dbtype", "str");
        A5399datatype.addAttribute("dt:maxLength", "60");
     // 2----------------s:AttributeType A5300
        Element A5300 = eleElement.addElement("s:AttributeType");
        A5300.addAttribute("name", "A5300");
        A5300.addAttribute("rs:number", "2");
        A5300.addAttribute("rs:nullable", "true");
        A5300.addAttribute("rs:write", "true");
        A5300.addAttribute("rs:basecatalog", "OFFICIALV2");
        A5300.addAttribute("rs:baseschema", "DBO");
        A5300.addAttribute("rs:basetable", "A53");
        A5300.addAttribute("rs:basecolumn", "A5300");
        //s:datatype
        Element A5300datatype = A5300.addElement("s:datatype");
        A5300datatype.addAttribute("dt:type", "string");
        A5300datatype.addAttribute("rs:dbtype", "str");
        A5300datatype.addAttribute("dt:maxLength", "60");
     // 3----------------s:AttributeType A0000
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "3");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A53");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "60");
      // 4----------------s:AttributeType A5304
        Element A5304 = eleElement.addElement("s:AttributeType");
        A5304.addAttribute("name", "A5304");
        A5304.addAttribute("rs:number", "4");
        A5304.addAttribute("rs:nullable", "true");
        A5304.addAttribute("rs:write", "true");
        A5304.addAttribute("rs:basecatalog", "OFFICIALV2");
        A5304.addAttribute("rs:baseschema", "DBO");
        A5304.addAttribute("rs:basetable", "A53");
        A5304.addAttribute("rs:basecolumn", "A5304");
        //s:datatype
        Element A5304datatype = A5304.addElement("s:datatype");
        A5304datatype.addAttribute("dt:type", "string");
        A5304datatype.addAttribute("rs:dbtype", "str");
        A5304datatype.addAttribute("dt:maxLength", "400");
     // 5----------------s:AttributeType A5315
        Element A5315 = eleElement.addElement("s:AttributeType");
        A5315.addAttribute("name", "A5315");
        A5315.addAttribute("rs:number", "5");
        A5315.addAttribute("rs:nullable", "true");
        A5315.addAttribute("rs:write", "true");
        A5315.addAttribute("rs:basecatalog", "OFFICIALV2");
        A5315.addAttribute("rs:baseschema", "DBO");
        A5315.addAttribute("rs:basetable", "A53");
        A5315.addAttribute("rs:basecolumn", "A5315");
        //s:datatype
        Element A5315datatype = A5315.addElement("s:datatype");
        A5315datatype.addAttribute("dt:type", "string");
        A5315datatype.addAttribute("rs:dbtype", "str");
        A5315datatype.addAttribute("dt:maxLength", "400");
     // 6----------------s:AttributeType A5317
        Element A5317 = eleElement.addElement("s:AttributeType");
        A5317.addAttribute("name", "A5317");
        A5317.addAttribute("rs:number", "6");
        A5317.addAttribute("rs:nullable", "true");
        A5317.addAttribute("rs:write", "true");
        A5317.addAttribute("rs:basecatalog", "OFFICIALV2");
        A5317.addAttribute("rs:baseschema", "DBO");
        A5317.addAttribute("rs:basetable", "A53");
        A5317.addAttribute("rs:basecolumn", "A5317");
        //s:datatype
        Element A5317datatype = A5317.addElement("s:datatype");
        A5317datatype.addAttribute("dt:type", "string");
        A5317datatype.addAttribute("rs:dbtype", "str");
        A5317datatype.addAttribute("dt:maxLength", "400");
     // 7----------------s:AttributeType A5319
        Element A5319 = eleElement.addElement("s:AttributeType");
        A5319.addAttribute("name", "A5319");
        A5319.addAttribute("rs:number", "7");
        A5319.addAttribute("rs:nullable", "true");
        A5319.addAttribute("rs:write", "true");
        A5319.addAttribute("rs:basecatalog", "OFFICIALV2");
        A5319.addAttribute("rs:baseschema", "DBO");
        A5319.addAttribute("rs:basetable", "A53");
        A5319.addAttribute("rs:basecolumn", "A5319");
        //s:datatype
        Element A5319datatype = A5319.addElement("s:datatype");
        A5319datatype.addAttribute("dt:type", "string");
        A5319datatype.addAttribute("rs:dbtype", "str");
        A5319datatype.addAttribute("dt:maxLength", "400");
     // 8----------------s:AttributeType A1424
        Element A5321 = eleElement.addElement("s:AttributeType");
        A5321.addAttribute("name", "A5321");
        A5321.addAttribute("rs:number", "8");
        A5321.addAttribute("rs:nullable", "true");
        A5321.addAttribute("rs:write", "true");
        A5321.addAttribute("rs:basecatalog", "OFFICIALV2");
        A5321.addAttribute("rs:baseschema", "DBO");
        A5321.addAttribute("rs:basetable", "A53");
        A5321.addAttribute("rs:basecolumn", "A5321");
        //s:datatype
        Element A5321datatype = A5321.addElement("s:datatype");
        A5321datatype.addAttribute("dt:type", "string");
        A5321datatype.addAttribute("rs:dbtype", "str");
        A5321datatype.addAttribute("dt:maxLength", "8");
     // 9----------------s:AttributeType A1414
        Element A5323 = eleElement.addElement("s:AttributeType");
        A5323.addAttribute("name", "A5323");
        A5323.addAttribute("rs:number", "9");
        A5323.addAttribute("rs:nullable", "true");
        A5323.addAttribute("rs:write", "true");
        A5323.addAttribute("rs:basecatalog", "OFFICIALV2");
        A5323.addAttribute("rs:baseschema", "DBO");
        A5323.addAttribute("rs:basetable", "A53");
        A5323.addAttribute("rs:basecolumn", "A5323");
        //s:datatype
        Element A5323datatype = A5323.addElement("s:datatype");
        A5323datatype.addAttribute("dt:type", "string");
        A5323datatype.addAttribute("rs:dbtype", "str");
        A5323datatype.addAttribute("dt:maxLength", "8");
      // 10----------------s:AttributeType A5321
        Element A5327 = eleElement.addElement("s:AttributeType");
        A5327.addAttribute("name", "A5327");
        A5327.addAttribute("rs:number", "10");
        A5327.addAttribute("rs:nullable", "true");
        A5327.addAttribute("rs:write", "true");
        A5327.addAttribute("rs:basecatalog", "OFFICIALV2");
        A5327.addAttribute("rs:baseschema", "DBO");
        A5327.addAttribute("rs:basetable", "A41");
        A5327.addAttribute("rs:basecolumn", "A5327");
        //s:datatype
        Element A5327datatype = A5327.addElement("s:datatype");
        A5327datatype.addAttribute("dt:type", "string");
        A5327datatype.addAttribute("rs:dbtype", "str");
        A5327datatype.addAttribute("dt:maxLength", "36");
        /**
         * 加载数据 
         */
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A53 a01 = (A53) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A5399", emptoString(map.get("A5399")));
				zrow.addAttribute("A5300", emptoString(map.get("A5300")));
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A5304", emptoString(map.get("A5304")));
				zrow.addAttribute("A5315", emptoString(map.get("A5315")));
				zrow.addAttribute("A5317", emptoString(map.get("A5317")));
				zrow.addAttribute("A5319", emptoString(map.get("A5319")));
				zrow.addAttribute("A5321", emptoString(map.get("A5321")));
				zrow.addAttribute("A5323", emptoString(map.get("A5323")));
				zrow.addAttribute("A5327", emptoString(map.get("A5327")));
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        XMLWriter out = null;
        
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        
        try {
            File xmlFile = new File(uuid + "Table/" +"A53.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private static void A41toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A0000
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "1");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A41");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "50");
     // 2----------------s:AttributeType A1517
        Element A1100 = eleElement.addElement("s:AttributeType");
        A1100.addAttribute("name", "A1100");
        A1100.addAttribute("rs:number", "2");
        A1100.addAttribute("rs:nullable", "true");
        A1100.addAttribute("rs:write", "true");
        A1100.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1100.addAttribute("rs:baseschema", "DBO");
        A1100.addAttribute("rs:basetable", "A41");
        A1100.addAttribute("rs:basecolumn", "A1100");
        //s:datatype
        Element A1100datatype = A1100.addElement("s:datatype");
        A1100datatype.addAttribute("dt:type", "string");
        A1100datatype.addAttribute("rs:dbtype", "str");
        A1100datatype.addAttribute("dt:maxLength", "50");
     // 3----------------s:AttributeType A1100
        Element A4100 = eleElement.addElement("s:AttributeType");
        A4100.addAttribute("name", "A4100");
        A4100.addAttribute("rs:number", "3");
        A4100.addAttribute("rs:nullable", "true");
        A4100.addAttribute("rs:write", "true");
        A4100.addAttribute("rs:basecatalog", "OFFICIALV2");
        A4100.addAttribute("rs:baseschema", "DBO");
        A4100.addAttribute("rs:basetable", "A41");
        A4100.addAttribute("rs:basecolumn", "A4100");
        //s:datatype
        Element A4100datatype = A4100.addElement("s:datatype");
        A4100datatype.addAttribute("dt:type", "string");
        A4100datatype.addAttribute("rs:dbtype", "str");
        A4100datatype.addAttribute("dt:maxLength", "50");
      // 4----------------s:AttributeType A1521
        Element A4101 = eleElement.addElement("s:AttributeType");
        A4101.addAttribute("name", "A4101");
        A4101.addAttribute("rs:number", "4");
        A4101.addAttribute("rs:nullable", "true");
        A4101.addAttribute("rs:write", "true");
        A4101.addAttribute("rs:basecatalog", "OFFICIALV2");
        A4101.addAttribute("rs:baseschema", "DBO");
        A4101.addAttribute("rs:basetable", "A41");
        A4101.addAttribute("rs:basecolumn", "A4101");
        //s:datatype
        Element A4101datatype = A4101.addElement("s:datatype");
        A4101datatype.addAttribute("dt:type", "string");
        A4101datatype.addAttribute("rs:dbtype", "str");
        A4101datatype.addAttribute("dt:maxLength", "10");
     // 5----------------s:AttributeType A1527
        Element A4102 = eleElement.addElement("s:AttributeType");
        A4102.addAttribute("name", "A4102");
        A4102.addAttribute("rs:number", "5");
        A4102.addAttribute("rs:nullable", "true");
        A4102.addAttribute("rs:write", "true");
        A4102.addAttribute("rs:basecatalog", "OFFICIALV2");
        A4102.addAttribute("rs:baseschema", "DBO");
        A4102.addAttribute("rs:basetable", "A41");
        A4102.addAttribute("rs:basecolumn", "A4102");
        //s:datatype
        Element A4102datatype = A4102.addElement("s:datatype");
        A4102datatype.addAttribute("dt:type", "string");
        A4102datatype.addAttribute("rs:dbtype", "str");
        A4102datatype.addAttribute("dt:maxLength", "10");
     // 6----------------s:AttributeType A1407
        Element A4103 = eleElement.addElement("s:AttributeType");
        A4103.addAttribute("name", "A4103");
        A4103.addAttribute("rs:number", "6");
        A4103.addAttribute("rs:nullable", "true");
        A4103.addAttribute("rs:write", "true");
        A4103.addAttribute("rs:basecatalog", "OFFICIALV2");
        A4103.addAttribute("rs:baseschema", "DBO");
        A4103.addAttribute("rs:basetable", "A41");
        A4103.addAttribute("rs:basecolumn", "A4103");
        //s:datatype
        Element A4103datatype = A4103.addElement("s:datatype");
        A4103datatype.addAttribute("dt:type", "string");
        A4103datatype.addAttribute("rs:dbtype", "str");
        A4103datatype.addAttribute("dt:maxLength", "3");
     // 7----------------s:AttributeType A1411A
        Element A4104 = eleElement.addElement("s:AttributeType");
        A4104.addAttribute("name", "A4104");
        A4104.addAttribute("rs:number", "7");
        A4104.addAttribute("rs:nullable", "true");
        A4104.addAttribute("rs:write", "true");
        A4104.addAttribute("rs:basecatalog", "OFFICIALV2");
        A4104.addAttribute("rs:baseschema", "DBO");
        A4104.addAttribute("rs:basetable", "A41");
        A4104.addAttribute("rs:basecolumn", "A4104");
        //s:datatype
        Element A4104datatype = A4104.addElement("s:datatype");
        A4104datatype.addAttribute("dt:type", "string");
        A4104datatype.addAttribute("rs:dbtype", "str");
        A4104datatype.addAttribute("dt:maxLength", "10");
     // 8----------------s:AttributeType A1424
        Element A4105 = eleElement.addElement("s:AttributeType");
        A4105.addAttribute("name", "A4105");
        A4105.addAttribute("rs:number", "8");
        A4105.addAttribute("rs:nullable", "true");
        A4105.addAttribute("rs:write", "true");
        A4105.addAttribute("rs:basecatalog", "OFFICIALV2");
        A4105.addAttribute("rs:baseschema", "DBO");
        A4105.addAttribute("rs:basetable", "A41");
        A4105.addAttribute("rs:basecolumn", "A4105");
        //s:datatype
        Element A4105datatype = A4105.addElement("s:datatype");
        A4105datatype.addAttribute("dt:type", "string");
        A4105datatype.addAttribute("rs:dbtype", "str");
        A4105datatype.addAttribute("dt:maxLength", "200");
     // 9----------------s:AttributeType A1414
        Element A4199 = eleElement.addElement("s:AttributeType");
        A4199.addAttribute("name", "A4199");
        A4199.addAttribute("rs:number", "9");
        A4199.addAttribute("rs:nullable", "true");
        A4199.addAttribute("rs:write", "true");
        A4199.addAttribute("rs:basecatalog", "OFFICIALV2");
        A4199.addAttribute("rs:baseschema", "DBO");
        A4199.addAttribute("rs:basetable", "A41");
        A4199.addAttribute("rs:basecolumn", "A4199");
        //s:datatype
        Element A4199datatype = A4199.addElement("s:datatype");
        A4199datatype.addAttribute("dt:type", "string");
        A4199datatype.addAttribute("rs:dbtype", "str");
        A4199datatype.addAttribute("dt:maxLength", "200");
        
        /**
         * 加载数据 
         */
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A41 a01 = (A41) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A1100", emptoString(map.get("A1100")));
				zrow.addAttribute("A4100", emptoString(map.get("A4100")));
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        XMLWriter out = null;
        
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        
        try {
            File xmlFile = new File(uuid + "Table/" +"A41.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private static void A37toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A0000
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "1");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A37");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "60");
     // 2----------------s:AttributeType A1517
        Element A3711 = eleElement.addElement("s:AttributeType");
        A3711.addAttribute("name", "A3711");
        A3711.addAttribute("rs:number", "2");
        A3711.addAttribute("rs:nullable", "true");
        A3711.addAttribute("rs:write", "true");
        A3711.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3711.addAttribute("rs:baseschema", "DBO");
        A3711.addAttribute("rs:basetable", "A37");
        A3711.addAttribute("rs:basecolumn", "A3711");
        //s:datatype
        Element A3711datatype = A3711.addElement("s:datatype");
        A3711datatype.addAttribute("dt:type", "string");
        A3711datatype.addAttribute("rs:dbtype", "str");
        A3711datatype.addAttribute("dt:maxLength", "120");
     // 3----------------s:AttributeType A3711
        Element A3707A = eleElement.addElement("s:AttributeType");
        A3707A.addAttribute("name", "A3707A");
        A3707A.addAttribute("rs:number", "3");
        A3707A.addAttribute("rs:nullable", "true");
        A3707A.addAttribute("rs:write", "true");
        A3707A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3707A.addAttribute("rs:baseschema", "DBO");
        A3707A.addAttribute("rs:basetable", "A37");
        A3707A.addAttribute("rs:basecolumn", "A3707A");
        //s:datatype
        Element A3707Adatatype = A3707A.addElement("s:datatype");
        A3707Adatatype.addAttribute("dt:type", "string");
        A3707Adatatype.addAttribute("rs:dbtype", "str");
        A3707Adatatype.addAttribute("dt:maxLength", "20");
      // 4----------------s:AttributeType A1521
        Element A3707C = eleElement.addElement("s:AttributeType");
        A3707C.addAttribute("name", "A3707C");
        A3707C.addAttribute("rs:number", "4");
        A3707C.addAttribute("rs:nullable", "true");
        A3707C.addAttribute("rs:write", "true");
        A3707C.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3707C.addAttribute("rs:baseschema", "DBO");
        A3707C.addAttribute("rs:basetable", "A37");
        A3707C.addAttribute("rs:basecolumn", "A3707C");
        //s:datatype
        Element A3707Cdatatype = A3707C.addElement("s:datatype");
        A3707Cdatatype.addAttribute("dt:type", "string");
        A3707Cdatatype.addAttribute("rs:dbtype", "str");
        A3707Cdatatype.addAttribute("dt:maxLength", "20");
     // 5----------------s:AttributeType A1527
        Element A3707E = eleElement.addElement("s:AttributeType");
        A3707E.addAttribute("name", "A3707E");
        A3707E.addAttribute("rs:number", "5");
        A3707E.addAttribute("rs:nullable", "true");
        A3707E.addAttribute("rs:write", "true");
        A3707E.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3707E.addAttribute("rs:baseschema", "DBO");
        A3707E.addAttribute("rs:basetable", "A37");
        A3707E.addAttribute("rs:basecolumn", "A3707E");
        //s:datatype
        Element A3707Edatatype = A3707E.addElement("s:datatype");
        A3707Edatatype.addAttribute("dt:type", "string");
        A3707Edatatype.addAttribute("rs:dbtype", "str");
        A3707Edatatype.addAttribute("dt:maxLength", "20");
     // 6----------------s:AttributeType A1407
        Element A3707B = eleElement.addElement("s:AttributeType");
        A3707B.addAttribute("name", "A3707B");
        A3707B.addAttribute("rs:number", "6");
        A3707B.addAttribute("rs:nullable", "true");
        A3707B.addAttribute("rs:write", "true");
        A3707B.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3707B.addAttribute("rs:baseschema", "DBO");
        A3707B.addAttribute("rs:basetable", "A37");
        A3707B.addAttribute("rs:basecolumn", "A3707B");
        //s:datatype
        Element A3707Bdatatype = A3707B.addElement("s:datatype");
        A3707Bdatatype.addAttribute("dt:type", "string");
        A3707Bdatatype.addAttribute("rs:dbtype", "str");
        A3707Bdatatype.addAttribute("dt:maxLength", "20");
     // 7----------------s:AttributeType A1411A
        Element A3708 = eleElement.addElement("s:AttributeType");
        A3708.addAttribute("name", "A3708");
        A3708.addAttribute("rs:number", "7");
        A3708.addAttribute("rs:nullable", "true");
        A3708.addAttribute("rs:write", "true");
        A3708.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3708.addAttribute("rs:baseschema", "DBO");
        A3708.addAttribute("rs:basetable", "A37");
        A3708.addAttribute("rs:basecolumn", "A3708");
        //s:datatype
        Element A3708datatype = A3708.addElement("s:datatype");
        A3708datatype.addAttribute("dt:type", "string");
        A3708datatype.addAttribute("rs:dbtype", "str");
        A3708datatype.addAttribute("dt:maxLength", "60");
     // 8----------------s:AttributeType A1424
        Element A3714 = eleElement.addElement("s:AttributeType");
        A3714.addAttribute("name", "A3714");
        A3714.addAttribute("rs:number", "8");
        A3714.addAttribute("rs:nullable", "true");
        A3714.addAttribute("rs:write", "true");
        A3714.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3714.addAttribute("rs:baseschema", "DBO");
        A3714.addAttribute("rs:basetable", "A37");
        A3714.addAttribute("rs:basecolumn", "A3714");
        //s:datatype
        Element A3714datatype = A3714.addElement("s:datatype");
        A3714datatype.addAttribute("dt:type", "string");
        A3714datatype.addAttribute("rs:dbtype", "str");
        A3714datatype.addAttribute("dt:maxLength", "6");
     // 9----------------s:AttributeType A1414
        Element A3701 = eleElement.addElement("s:AttributeType");
        A3701.addAttribute("name", "A3701");
        A3701.addAttribute("rs:number", "9");
        A3701.addAttribute("rs:nullable", "true");
        A3701.addAttribute("rs:write", "true");
        A3701.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3701.addAttribute("rs:baseschema", "DBO");
        A3701.addAttribute("rs:basetable", "A37");
        A3701.addAttribute("rs:basecolumn", "A3701");
        //s:datatype
        Element A3701datatype = A3701.addElement("s:datatype");
        A3701datatype.addAttribute("dt:type", "string");
        A3701datatype.addAttribute("rs:dbtype", "str");
        A3701datatype.addAttribute("dt:maxLength", "120");
        /**
         * 加载数据 
         */
        //  extends
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A37 a01 = (A37) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A3101", emptoString(map.get("A3101")));
				zrow.addAttribute("A3104", emptoString(map.get("A3104")));
				zrow.addAttribute("A3107", emptoString(map.get("A3107")));
				zrow.addAttribute("A3117A", emptoString(map.get("A3117A")));
				zrow.addAttribute("A3118", emptoString(map.get("A3118")));
				zrow.addAttribute("A3137", emptoString(map.get("A3137")));
				zrow.addAttribute("A3138", emptoString(map.get("A3138")));
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        XMLWriter out = null;
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        try {
            File xmlFile = new File(uuid + "Table/" +"A37.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private static void A36toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A0000
        Element A3600 = eleElement.addElement("s:AttributeType");
        A3600.addAttribute("name", "A3600");
        A3600.addAttribute("rs:number", "1");
        A3600.addAttribute("rs:nullable", "true");
        A3600.addAttribute("rs:write", "true");
        A3600.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3600.addAttribute("rs:baseschema", "DBO");
        A3600.addAttribute("rs:basetable", "A36");
        A3600.addAttribute("rs:basecolumn", "A3600");
        //s:datatype
        Element A3600datatype = A3600.addElement("s:datatype");
        A3600datatype.addAttribute("dt:type", "string");
        A3600datatype.addAttribute("rs:dbtype", "str");
        A3600datatype.addAttribute("dt:maxLength", "60");
     // 2----------------s:AttributeType A1517
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "2");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A36");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "60");
     // 3----------------s:AttributeType A3600
        Element A3604A = eleElement.addElement("s:AttributeType");
        A3604A.addAttribute("name", "A3604A");
        A3604A.addAttribute("rs:number", "3");
        A3604A.addAttribute("rs:nullable", "true");
        A3604A.addAttribute("rs:write", "true");
        A3604A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3604A.addAttribute("rs:baseschema", "DBO");
        A3604A.addAttribute("rs:basetable", "A36");
        A3604A.addAttribute("rs:basecolumn", "A3604A");
        //s:datatype
        Element A3604Adatatype = A3604A.addElement("s:datatype");
        A3604Adatatype.addAttribute("dt:type", "string");
        A3604Adatatype.addAttribute("rs:dbtype", "str");
        A3604Adatatype.addAttribute("dt:maxLength", "10");
      // 4----------------s:AttributeType A1521
        Element A3601 = eleElement.addElement("s:AttributeType");
        A3601.addAttribute("name", "A3601");
        A3601.addAttribute("rs:number", "4");
        A3601.addAttribute("rs:nullable", "true");
        A3601.addAttribute("rs:write", "true");
        A3601.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3601.addAttribute("rs:baseschema", "DBO");
        A3601.addAttribute("rs:basetable", "A36");
        A3601.addAttribute("rs:basecolumn", "A3601");
        //s:datatype
        Element A3601datatype = A3601.addElement("s:datatype");
        A3601datatype.addAttribute("dt:type", "string");
        A3601datatype.addAttribute("rs:dbtype", "str");
        A3601datatype.addAttribute("dt:maxLength", "36");
     // 5----------------s:AttributeType A1527
        Element A3607 = eleElement.addElement("s:AttributeType");
        A3607.addAttribute("name", "A3607");
        A3607.addAttribute("rs:number", "5");
        A3607.addAttribute("rs:nullable", "true");
        A3607.addAttribute("rs:write", "true");
        A3607.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3607.addAttribute("rs:baseschema", "DBO");
        A3607.addAttribute("rs:basetable", "A36");
        A3607.addAttribute("rs:basecolumn", "A3607");
        //s:datatype
        Element A3607datatype = A3607.addElement("s:datatype");
        A3607datatype.addAttribute("dt:type", "string");
        A3607datatype.addAttribute("rs:dbtype", "str");
        A3607datatype.addAttribute("dt:maxLength", "8");
     // 6----------------s:AttributeType A1407
        Element A3627 = eleElement.addElement("s:AttributeType");
        A3627.addAttribute("name", "A3627");
        A3627.addAttribute("rs:number", "6");
        A3627.addAttribute("rs:nullable", "true");
        A3627.addAttribute("rs:write", "true");
        A3627.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3627.addAttribute("rs:baseschema", "DBO");
        A3627.addAttribute("rs:basetable", "A36");
        A3627.addAttribute("rs:basecolumn", "A3627");
        //s:datatype
        Element A3627datatype = A3627.addElement("s:datatype");
        A3627datatype.addAttribute("dt:type", "string");
        A3627datatype.addAttribute("rs:dbtype", "str");
        A3627datatype.addAttribute("dt:maxLength", "100");
     // 7----------------s:AttributeType A1411A
        Element A3611 = eleElement.addElement("s:AttributeType");
        A3611.addAttribute("name", "A3611");
        A3611.addAttribute("rs:number", "7");
        A3611.addAttribute("rs:nullable", "true");
        A3611.addAttribute("rs:write", "true");
        A3611.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3611.addAttribute("rs:baseschema", "DBO");
        A3611.addAttribute("rs:basetable", "A36");
        A3611.addAttribute("rs:basecolumn", "A3611");
        //s:datatype
        Element A3611datatype = A3611.addElement("s:datatype");
        A3611datatype.addAttribute("dt:type", "string");
        A3611datatype.addAttribute("rs:dbtype", "str");
        A3611datatype.addAttribute("dt:maxLength", "200");
     // 8----------------s:AttributeType A1424
        Element SORTID = eleElement.addElement("s:AttributeType");
        SORTID.addAttribute("name", "SORTID");
        SORTID.addAttribute("rs:number", "8");
        SORTID.addAttribute("rs:nullable", "true");
        SORTID.addAttribute("rs:write", "true");
        SORTID.addAttribute("rs:basecatalog", "OFFICIALV2");
        SORTID.addAttribute("rs:baseschema", "DBO");
        SORTID.addAttribute("rs:basetable", "A36");
        SORTID.addAttribute("rs:basecolumn", "SORTID");
        //s:datatype
        Element SORTIDdatatype = SORTID.addElement("s:datatype");
        SORTIDdatatype.addAttribute("dt:type", "int");
        SORTIDdatatype.addAttribute("dt:maxLength", "4");
        SORTIDdatatype.addAttribute("rs:precision", "10"); 
        SORTIDdatatype.addAttribute("rs:fixedlength", "true"); 
        /**
         * 
         */
    //  extends
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A36 a01 = (A36) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A3600", emptoString(map.get("A3600")));
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A3604A", emptoString(map.get("A3604A")));
				zrow.addAttribute("A3601", emptoString(map.get("A3601")));
				zrow.addAttribute("A3607", emptoString(map.get("A3607")));
				zrow.addAttribute("A3627", emptoString(map.get("A3627")));
				zrow.addAttribute("A3611", emptoString(map.get("A3611")));
				zrow.addAttribute("SORTID", emptoString(map.get("SORTID")));
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        XMLWriter out = null;
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        try {
            File xmlFile = new File(uuid + "Table/" +"A36.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private static void A31toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A0000
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "1");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A31");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "60");
     // 2----------------s:AttributeType A1517
        Element A3101 = eleElement.addElement("s:AttributeType");
        A3101.addAttribute("name", "A3101");
        A3101.addAttribute("rs:number", "2");
        A3101.addAttribute("rs:nullable", "true");
        A3101.addAttribute("rs:write", "true");
        A3101.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3101.addAttribute("rs:baseschema", "DBO");
        A3101.addAttribute("rs:basetable", "A31");
        A3101.addAttribute("rs:basecolumn", "A3101");
        //s:datatype
        Element A3101datatype = A3101.addElement("s:datatype");
        A3101datatype.addAttribute("dt:type", "string");
        A3101datatype.addAttribute("rs:dbtype", "str");
        A3101datatype.addAttribute("dt:maxLength", "8");
     // 3----------------s:AttributeType A0000
        Element A3104 = eleElement.addElement("s:AttributeType");
        A3104.addAttribute("name", "A3104");
        A3104.addAttribute("rs:number", "3");
        A3104.addAttribute("rs:nullable", "true");
        A3104.addAttribute("rs:write", "true");
        A3104.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3104.addAttribute("rs:baseschema", "DBO");
        A3104.addAttribute("rs:basetable", "A31");
        A3104.addAttribute("rs:basecolumn", "A3104");
        //s:datatype
        Element A3104datatype = A3104.addElement("s:datatype");
        A3104datatype.addAttribute("dt:type", "string");
        A3104datatype.addAttribute("rs:dbtype", "str");
        A3104datatype.addAttribute("dt:maxLength", "8");
      // 4----------------s:AttributeType A1521
        Element A3107 = eleElement.addElement("s:AttributeType");
        A3107.addAttribute("name", "A3107");
        A3107.addAttribute("rs:number", "4");
        A3107.addAttribute("rs:nullable", "true");
        A3107.addAttribute("rs:write", "true");
        A3107.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3107.addAttribute("rs:baseschema", "DBO");
        A3107.addAttribute("rs:basetable", "A31");
        A3107.addAttribute("rs:basecolumn", "A3107");
        //s:datatype
        Element A3107datatype = A3107.addElement("s:datatype");
        A3107datatype.addAttribute("dt:type", "string");
        A3107datatype.addAttribute("rs:dbtype", "str");
        A3107datatype.addAttribute("dt:maxLength", "8");
     // 5----------------s:AttributeType A1527
        Element A3117A = eleElement.addElement("s:AttributeType");
        A3117A.addAttribute("name", "A3117A");
        A3117A.addAttribute("rs:number", "5");
        A3117A.addAttribute("rs:nullable", "true");
        A3117A.addAttribute("rs:write", "true");
        A3117A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3117A.addAttribute("rs:baseschema", "DBO");
        A3117A.addAttribute("rs:basetable", "A31");
        A3117A.addAttribute("rs:basecolumn", "A3117A");
        //s:datatype
        Element A3117Adatatype = A3117A.addElement("s:datatype");
        A3117Adatatype.addAttribute("dt:type", "string");
        A3117Adatatype.addAttribute("rs:dbtype", "str");
        A3117Adatatype.addAttribute("dt:maxLength", "60");
     // 6----------------s:AttributeType A1407
        Element A3118 = eleElement.addElement("s:AttributeType");
        A3118.addAttribute("name", "A3118");
        A3118.addAttribute("rs:number", "6");
        A3118.addAttribute("rs:nullable", "true");
        A3118.addAttribute("rs:write", "true");
        A3118.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3118.addAttribute("rs:baseschema", "DBO");
        A3118.addAttribute("rs:basetable", "A31");
        A3118.addAttribute("rs:basecolumn", "A3118");
        //s:datatype
        Element A3118datatype = A3118.addElement("s:datatype");
        A3118datatype.addAttribute("dt:type", "string");
        A3118datatype.addAttribute("rs:dbtype", "str");
        A3118datatype.addAttribute("dt:maxLength", "40");
     // 7----------------s:AttributeType A1411A
        Element A3137 = eleElement.addElement("s:AttributeType");
        A3137.addAttribute("name", "A3137");
        A3137.addAttribute("rs:number", "7");
        A3137.addAttribute("rs:nullable", "true");
        A3137.addAttribute("rs:write", "true");
        A3137.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3137.addAttribute("rs:baseschema", "DBO");
        A3137.addAttribute("rs:basetable", "A31");
        A3137.addAttribute("rs:basecolumn", "A3137");
        //s:datatype
        Element A3137datatype = A3137.addElement("s:datatype");
        A3137datatype.addAttribute("dt:type", "string");
        A3137datatype.addAttribute("rs:dbtype", "str");
        A3137datatype.addAttribute("dt:maxLength", "24");
     // 8----------------s:AttributeType A1424
        Element A3138 = eleElement.addElement("s:AttributeType");
        A3138.addAttribute("name", "A3138");
        A3138.addAttribute("rs:number", "8");
        A3138.addAttribute("rs:nullable", "true");
        A3138.addAttribute("rs:write", "true");
        A3138.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3138.addAttribute("rs:baseschema", "DBO");
        A3138.addAttribute("rs:basetable", "A31");
        A3138.addAttribute("rs:basecolumn", "A3138");
        //s:datatype
        Element A3138datatype = A3138.addElement("s:datatype");
        A3138datatype.addAttribute("dt:type", "string");
        A3138datatype.addAttribute("rs:dbtype", "str");
        A3138datatype.addAttribute("dt:maxLength", "60");
        /**
         * 加载数据 
         */
        //  extends
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A31 a01 = (A31) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A3101", emptoString(map.get("A3101")));
				zrow.addAttribute("A3104", emptoString(map.get("A3104")));
				zrow.addAttribute("A3107", emptoString(map.get("A3107")));
				zrow.addAttribute("A3117A", emptoString(map.get("A3117A")));
				zrow.addAttribute("A3118", emptoString(map.get("A3118")));
				zrow.addAttribute("A3137", emptoString(map.get("A3137")));
				zrow.addAttribute("A3138", emptoString(map.get("A3138")));
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        XMLWriter out = null;
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        try {
            File xmlFile = new File(uuid + "Table/" +"A31.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private static void A30toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A0000
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "1");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A30");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "60");
     // 2----------------s:AttributeType A1517
        Element A3001 = eleElement.addElement("s:AttributeType");
        A3001.addAttribute("name", "A3001");
        A3001.addAttribute("rs:number", "2");
        A3001.addAttribute("rs:nullable", "true");
        A3001.addAttribute("rs:write", "true");
        A3001.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3001.addAttribute("rs:baseschema", "DBO");
        A3001.addAttribute("rs:basetable", "A30");
        A3001.addAttribute("rs:basecolumn", "A3001");
        //s:datatype
        Element A3001datatype = A3001.addElement("s:datatype");
        A3001datatype.addAttribute("dt:type", "string");
        A3001datatype.addAttribute("rs:dbtype", "str");
        A3001datatype.addAttribute("dt:maxLength", "8");
     // 3----------------s:AttributeType A0000
        Element A3007A = eleElement.addElement("s:AttributeType");
        A3007A.addAttribute("name", "A3007A");
        A3007A.addAttribute("rs:number", "3");
        A3007A.addAttribute("rs:nullable", "true");
        A3007A.addAttribute("rs:write", "true");
        A3007A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3007A.addAttribute("rs:baseschema", "DBO");
        A3007A.addAttribute("rs:basetable", "A30");
        A3007A.addAttribute("rs:basecolumn", "A3007A");
        //s:datatype
        Element A3007Adatatype = A3007A.addElement("s:datatype");
        A3007Adatatype.addAttribute("dt:type", "string");
        A3007Adatatype.addAttribute("rs:dbtype", "str");
        A3007Adatatype.addAttribute("dt:maxLength", "60");
      // 4----------------s:AttributeType A1521
        Element A3004 = eleElement.addElement("s:AttributeType");
        A3004.addAttribute("name", "A3004");
        A3004.addAttribute("rs:number", "4");
        A3004.addAttribute("rs:nullable", "true");
        A3004.addAttribute("rs:write", "true");
        A3004.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3004.addAttribute("rs:baseschema", "DBO");
        A3004.addAttribute("rs:basetable", "A30");
        A3004.addAttribute("rs:basecolumn", "A3004");
        //s:datatype
        Element A3004datatype = A3004.addElement("s:datatype");
        A3004datatype.addAttribute("dt:type", "string");
        A3004datatype.addAttribute("rs:dbtype", "str");
        A3004datatype.addAttribute("dt:maxLength", "8");
     // 5----------------s:AttributeType A1527
        Element A3034 = eleElement.addElement("s:AttributeType");
        A3034.addAttribute("name", "A3034");
        A3034.addAttribute("rs:number", "5");
        A3034.addAttribute("rs:nullable", "true");
        A3034.addAttribute("rs:write", "true");
        A3034.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3034.addAttribute("rs:baseschema", "DBO");
        A3034.addAttribute("rs:basetable", "A30");
        A3034.addAttribute("rs:basecolumn", "A3034");
        //s:datatype
        Element A3034datatype = A3034.addElement("s:datatype");
        A3034datatype.addAttribute("dt:type", "string");
        A3034datatype.addAttribute("rs:dbtype", "str");
        A3034datatype.addAttribute("dt:maxLength", "500");
        /**
         * 加载数据 
         */
        //  extends
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A30 a01 = (A30) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A3001", emptoString(map.get("A3001")));
				zrow.addAttribute("A3007A", emptoString(map.get("A3007A")));
				zrow.addAttribute("A3004", emptoString(map.get("A3004")));
				zrow.addAttribute("A3034", emptoString(map.get("A3034")));
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        XMLWriter out = null;
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        try {
            File xmlFile = new File(uuid + "Table/" +"A30.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private static void A29toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A0000
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "1");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A29");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "60");
     // 2----------------s:AttributeType A1517
        Element A2947 = eleElement.addElement("s:AttributeType");
        A2947.addAttribute("name", "A2947");
        A2947.addAttribute("rs:number", "2");
        A2947.addAttribute("rs:nullable", "true");
        A2947.addAttribute("rs:write", "true");
        A2947.addAttribute("rs:basecatalog", "OFFICIALV2");
        A2947.addAttribute("rs:baseschema", "DBO");
        A2947.addAttribute("rs:basetable", "A29");
        A2947.addAttribute("rs:basecolumn", "A2947");
        //s:datatype
        Element A2947datatype = A2947.addElement("s:datatype");
        A2947datatype.addAttribute("dt:type", "string");
        A2947datatype.addAttribute("rs:dbtype", "str");
        A2947datatype.addAttribute("dt:maxLength", "8");
     // 3----------------s:AttributeType A0000
        Element A2944 = eleElement.addElement("s:AttributeType");
        A2944.addAttribute("name", "A2944");
        A2944.addAttribute("rs:number", "3");
        A2944.addAttribute("rs:nullable", "true");
        A2944.addAttribute("rs:write", "true");
        A2944.addAttribute("rs:basecatalog", "OFFICIALV2");
        A2944.addAttribute("rs:baseschema", "DBO");
        A2944.addAttribute("rs:basetable", "A29");
        A2944.addAttribute("rs:basecolumn", "A2944");
        //s:datatype
        Element A2944datatype = A2944.addElement("s:datatype");
        A2944datatype.addAttribute("dt:type", "string");
        A2944datatype.addAttribute("rs:dbtype", "str");
        A2944datatype.addAttribute("dt:maxLength", "40");
      // 4----------------s:AttributeType A1521
        Element A2911 = eleElement.addElement("s:AttributeType");
        A2911.addAttribute("name", "A2911");
        A2911.addAttribute("rs:number", "4");
        A2911.addAttribute("rs:nullable", "true");
        A2911.addAttribute("rs:write", "true");
        A2911.addAttribute("rs:basecatalog", "OFFICIALV2");
        A2911.addAttribute("rs:baseschema", "DBO");
        A2911.addAttribute("rs:basetable", "A29");
        A2911.addAttribute("rs:basecolumn", "A2911");
        //s:datatype
        Element A2911datatype = A2911.addElement("s:datatype");
        A2911datatype.addAttribute("dt:type", "string");
        A2911datatype.addAttribute("rs:dbtype", "str");
        A2911datatype.addAttribute("dt:maxLength", "8");
     // 5----------------s:AttributeType A1527
        Element A2907 = eleElement.addElement("s:AttributeType");
        A2907.addAttribute("name", "A2907");
        A2907.addAttribute("rs:number", "5");
        A2907.addAttribute("rs:nullable", "true");
        A2907.addAttribute("rs:write", "true");
        A2907.addAttribute("rs:basecatalog", "OFFICIALV2");
        A2907.addAttribute("rs:baseschema", "DBO");
        A2907.addAttribute("rs:basetable", "A29");
        A2907.addAttribute("rs:basecolumn", "A2907");
        //s:datatype
        Element A2907datatype = A2907.addElement("s:datatype");
        A2907datatype.addAttribute("dt:type", "string");
        A2907datatype.addAttribute("rs:dbtype", "str");
        A2907datatype.addAttribute("dt:maxLength", "8");
     // 6----------------s:AttributeType A1407
        Element A2921A = eleElement.addElement("s:AttributeType");
        A2921A.addAttribute("name", "A2921A");
        A2921A.addAttribute("rs:number", "6");
        A2921A.addAttribute("rs:nullable", "true");
        A2921A.addAttribute("rs:write", "true");
        A2921A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A2921A.addAttribute("rs:baseschema", "DBO");
        A2921A.addAttribute("rs:basetable", "A29");
        A2921A.addAttribute("rs:basecolumn", "A2921A");
        //s:datatype
        Element A2921Adatatype = A2921A.addElement("s:datatype");
        A2921Adatatype.addAttribute("dt:type", "string");
        A2921Adatatype.addAttribute("rs:dbtype", "str");
        A2921Adatatype.addAttribute("dt:maxLength", "60");
     // 7----------------s:AttributeType A1411A
        Element A2941 = eleElement.addElement("s:AttributeType");
        A2941.addAttribute("name", "A2941");
        A2941.addAttribute("rs:number", "7");
        A2941.addAttribute("rs:nullable", "true");
        A2941.addAttribute("rs:write", "true");
        A2941.addAttribute("rs:basecatalog", "OFFICIALV2");
        A2941.addAttribute("rs:baseschema", "DBO");
        A2941.addAttribute("rs:basetable", "A29");
        A2941.addAttribute("rs:basecolumn", "A2941");
        //s:datatype
        Element A2941datatype = A2941.addElement("s:datatype");
        A2941datatype.addAttribute("dt:type", "string");
        A2941datatype.addAttribute("rs:dbtype", "str");
        A2941datatype.addAttribute("dt:maxLength", "40");
     // 8----------------s:AttributeType A1424
        Element A2949 = eleElement.addElement("s:AttributeType");
        A2949.addAttribute("name", "A2949");
        A2949.addAttribute("rs:number", "8");
        A2949.addAttribute("rs:nullable", "true");
        A2949.addAttribute("rs:write", "true");
        A2949.addAttribute("rs:basecatalog", "OFFICIALV2");
        A2949.addAttribute("rs:baseschema", "DBO");
        A2949.addAttribute("rs:basetable", "A29");
        A2949.addAttribute("rs:basecolumn", "A2949");
        //s:datatype
        Element A2949datatype = A2949.addElement("s:datatype");
        A2949datatype.addAttribute("dt:type", "string");
        A2949datatype.addAttribute("rs:dbtype", "str");
        A2949datatype.addAttribute("dt:maxLength", "8");
        
        /**
         * 加载数据 
         */
        //  extends
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A29 a01 = (A29) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A2947", emptoString(map.get("A2947")));
				zrow.addAttribute("A2944", emptoString(map.get("A2944")));
				zrow.addAttribute("A2911", emptoString(map.get("A2911")));
				zrow.addAttribute("A2907", emptoString(map.get("A2907")));
				zrow.addAttribute("A2921A", emptoString(map.get("A2921A")));
				zrow.addAttribute("A2941", emptoString(map.get("A2941")));
				zrow.addAttribute("A2949", emptoString(map.get("A2949")));
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        XMLWriter out = null;
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        try {
            File xmlFile = new File(uuid + "Table/" +"A29.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private static void A15toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A1500
        Element A1500 = eleElement.addElement("s:AttributeType");
        A1500.addAttribute("name", "A1500");
        A1500.addAttribute("rs:number", "1");
        A1500.addAttribute("rs:nullable", "true");
        A1500.addAttribute("rs:write", "true");
        A1500.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1500.addAttribute("rs:baseschema", "DBO");
        A1500.addAttribute("rs:basetable", "A15");
        A1500.addAttribute("rs:basecolumn", "A1500");
        //s:datatype
        Element A1500datatype = A1500.addElement("s:datatype");
        A1500datatype.addAttribute("dt:type", "string");
        A1500datatype.addAttribute("rs:dbtype", "str");
        A1500datatype.addAttribute("dt:maxLength", "60");
     // 2----------------s:AttributeType A1517
        Element A1517 = eleElement.addElement("s:AttributeType");
        A1517.addAttribute("name", "A1517");
        A1517.addAttribute("rs:number", "2");
        A1517.addAttribute("rs:nullable", "true");
        A1517.addAttribute("rs:write", "true");
        A1517.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1517.addAttribute("rs:baseschema", "DBO");
        A1517.addAttribute("rs:basetable", "A15");
        A1517.addAttribute("rs:basecolumn", "A1517");
        //s:datatype
        Element A1517datatype = A1517.addElement("s:datatype");
        A1517datatype.addAttribute("dt:type", "string");
        A1517datatype.addAttribute("rs:dbtype", "str");
        A1517datatype.addAttribute("dt:maxLength", "8");
     // 3----------------s:AttributeType A0000
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "3");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A15");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "60");
      // 4----------------s:AttributeType A1521
        Element A1521 = eleElement.addElement("s:AttributeType");
        A1521.addAttribute("name", "A1521");
        A1521.addAttribute("rs:number", "4");
        A1521.addAttribute("rs:nullable", "true");
        A1521.addAttribute("rs:write", "true");
        A1521.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1521.addAttribute("rs:baseschema", "DBO");
        A1521.addAttribute("rs:basetable", "A15");
        A1521.addAttribute("rs:basecolumn", "A1521");
        //s:datatype
        Element A1521datatype = A1521.addElement("s:datatype");
        A1521datatype.addAttribute("dt:type", "string");
        A1521datatype.addAttribute("rs:dbtype", "str");
        A1521datatype.addAttribute("dt:maxLength", "8");
     // 5----------------s:AttributeType A1527
        Element A1527 = eleElement.addElement("s:AttributeType");
        A1527.addAttribute("name", "A1527");
        A1527.addAttribute("rs:number", "5");
        A1527.addAttribute("rs:nullable", "true");
        A1527.addAttribute("rs:write", "true");
        A1527.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1527.addAttribute("rs:baseschema", "DBO");
        A1527.addAttribute("rs:basetable", "A15");
        A1527.addAttribute("rs:basecolumn", "A1527");
        //s:datatype
        Element A1527datatype = A1527.addElement("s:datatype");
        A1527datatype.addAttribute("dt:type", "string");
        A1527datatype.addAttribute("rs:dbtype", "str");
        A1527datatype.addAttribute("dt:maxLength", "8");
        /**
         * 加载数据 
         */
        //  extends
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A15 a01 = (A15) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A1500", emptoString(map.get("A1500")));
				zrow.addAttribute("A1517", emptoString(map.get("A1517")));
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A1521", emptoString(map.get("A1521")));
				zrow.addAttribute("A1527", emptoString(map.get("A1527")));
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
        
        XMLWriter out = null;
        
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        
        try {
            File xmlFile = new File(uuid + "Table/" +"A15.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private static void A14toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A1428
        Element A1428 = eleElement.addElement("s:AttributeType");
        A1428.addAttribute("name", "A1428");
        A1428.addAttribute("rs:number", "1");
        A1428.addAttribute("rs:nullable", "true");
        A1428.addAttribute("rs:write", "true");
        A1428.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1428.addAttribute("rs:baseschema", "DBO");
        A1428.addAttribute("rs:basetable", "A14");
        A1428.addAttribute("rs:basecolumn", "A1428");
        //s:datatype
        Element A1428datatype = A1428.addElement("s:datatype");
        A1428datatype.addAttribute("dt:type", "string");
        A1428datatype.addAttribute("rs:dbtype", "str");
        A1428datatype.addAttribute("dt:maxLength", "8");
     // 2----------------s:AttributeType A1400
        Element A1400 = eleElement.addElement("s:AttributeType");
        A1400.addAttribute("name", "A1400");
        A1400.addAttribute("rs:number", "2");
        A1400.addAttribute("rs:nullable", "true");
        A1400.addAttribute("rs:write", "true");
        A1400.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1400.addAttribute("rs:baseschema", "DBO");
        A1400.addAttribute("rs:basetable", "A14");
        A1400.addAttribute("rs:basecolumn", "A1400");
        //s:datatype
        Element A1400datatype = A1400.addElement("s:datatype");
        A1400datatype.addAttribute("dt:type", "string");
        A1400datatype.addAttribute("rs:dbtype", "str");
        A1400datatype.addAttribute("dt:maxLength", "60");
     // 3----------------s:AttributeType A0000
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "3");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A14");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "60");
      // 4----------------s:AttributeType A1404A
        Element A1404A = eleElement.addElement("s:AttributeType");
        A1404A.addAttribute("name", "A1404A");
        A1404A.addAttribute("rs:number", "4");
        A1404A.addAttribute("rs:nullable", "true");
        A1404A.addAttribute("rs:write", "true");
        A1404A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1404A.addAttribute("rs:baseschema", "DBO");
        A1404A.addAttribute("rs:basetable", "A14");
        A1404A.addAttribute("rs:basecolumn", "A1404A");
        //s:datatype
        Element A1404Adatatype = A1404A.addElement("s:datatype");
        A1404Adatatype.addAttribute("dt:type", "string");
        A1404Adatatype.addAttribute("rs:dbtype", "str");
        A1404Adatatype.addAttribute("dt:maxLength", "40");
     // 5----------------s:AttributeType A1404B
        Element A1404B = eleElement.addElement("s:AttributeType");
        A1404B.addAttribute("name", "A1404B");
        A1404B.addAttribute("rs:number", "5");
        A1404B.addAttribute("rs:nullable", "true");
        A1404B.addAttribute("rs:write", "true");
        A1404B.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1404B.addAttribute("rs:baseschema", "DBO");
        A1404B.addAttribute("rs:basetable", "A14");
        A1404B.addAttribute("rs:basecolumn", "A1404B");
        //s:datatype
        Element A1404Bdatatype = A1404B.addElement("s:datatype");
        A1404Bdatatype.addAttribute("dt:type", "string");
        A1404Bdatatype.addAttribute("rs:dbtype", "str");
        A1404Bdatatype.addAttribute("dt:maxLength", "8");
     // 6----------------s:AttributeType A1407
        Element A1407 = eleElement.addElement("s:AttributeType");
        A1407.addAttribute("name", "A1407");
        A1407.addAttribute("rs:number", "6");
        A1407.addAttribute("rs:nullable", "true");
        A1407.addAttribute("rs:write", "true");
        A1407.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1407.addAttribute("rs:baseschema", "DBO");
        A1407.addAttribute("rs:basetable", "A14");
        A1407.addAttribute("rs:basecolumn", "A1407");
        //s:datatype
        Element A1407datatype = A1407.addElement("s:datatype");
        A1407datatype.addAttribute("dt:type", "string");
        A1407datatype.addAttribute("rs:dbtype", "str");
        A1407datatype.addAttribute("dt:maxLength", "8");
     // 7----------------s:AttributeType A1411A
        Element A1411A = eleElement.addElement("s:AttributeType");
        A1411A.addAttribute("name", "A1411A");
        A1411A.addAttribute("rs:number", "7");
        A1411A.addAttribute("rs:nullable", "true");
        A1411A.addAttribute("rs:write", "true");
        A1411A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1411A.addAttribute("rs:baseschema", "DBO");
        A1411A.addAttribute("rs:basetable", "A14");
        A1411A.addAttribute("rs:basecolumn", "A1411A");
        //s:datatype
        Element A1411Adatatype = A1411A.addElement("s:datatype");
        A1411Adatatype.addAttribute("dt:type", "string");
        A1411Adatatype.addAttribute("rs:dbtype", "str");
        A1411Adatatype.addAttribute("dt:maxLength", "60");
     // 8----------------s:AttributeType A1424
        Element A1424 = eleElement.addElement("s:AttributeType");
        A1424.addAttribute("name", "A1424");
        A1424.addAttribute("rs:number", "8");
        A1424.addAttribute("rs:nullable", "true");
        A1424.addAttribute("rs:write", "true");
        A1424.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1424.addAttribute("rs:baseschema", "DBO");
        A1424.addAttribute("rs:basetable", "A14");
        A1424.addAttribute("rs:basecolumn", "A1424");
        //s:datatype
        Element A1424datatype = A1424.addElement("s:datatype");
        A1424datatype.addAttribute("dt:type", "string");
        A1424datatype.addAttribute("rs:dbtype", "str");
        A1424datatype.addAttribute("dt:maxLength", "8");
     // 9----------------s:AttributeType A1414
        Element A1414 = eleElement.addElement("s:AttributeType");
        A1414.addAttribute("name", "A1414");
        A1414.addAttribute("rs:number", "9");
        A1414.addAttribute("rs:nullable", "true");
        A1414.addAttribute("rs:write", "true");
        A1414.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1414.addAttribute("rs:baseschema", "DBO");
        A1414.addAttribute("rs:basetable", "A14");
        A1414.addAttribute("rs:basecolumn", "A1414");
        //s:datatype
        Element A1414datatype = A1414.addElement("s:datatype");
        A1414datatype.addAttribute("dt:type", "string");
        A1414datatype.addAttribute("rs:dbtype", "str");
        A1414datatype.addAttribute("dt:maxLength", "8");
      // 10----------------s:AttributeType SORTID
        Element SORTID = eleElement.addElement("s:AttributeType");
        SORTID.addAttribute("name", "SORTID");
        SORTID.addAttribute("rs:number", "10");
        SORTID.addAttribute("rs:nullable", "true");
        SORTID.addAttribute("rs:write", "true");
        SORTID.addAttribute("rs:basecatalog", "OFFICIALV2");
        SORTID.addAttribute("rs:baseschema", "DBO");
        SORTID.addAttribute("rs:basetable", "A14");
        SORTID.addAttribute("rs:basecolumn", "SORTID");
        //s:datatype
        Element SORTIDdatatype = SORTID.addElement("s:datatype");
        SORTIDdatatype.addAttribute("dt:type", "int");
        SORTIDdatatype.addAttribute("dt:maxLength", "4");
        SORTIDdatatype.addAttribute("rs:precision", "10");
        SORTIDdatatype.addAttribute("rs:fixedlength", "true");
     // 11----------------s:AttributeType A1415
        Element A1415 = eleElement.addElement("s:AttributeType");
        A1415.addAttribute("name", "A1415");
        A1415.addAttribute("rs:number", "11");
        A1415.addAttribute("rs:nullable", "true");
        A1415.addAttribute("rs:write", "true");
        A1415.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1415.addAttribute("rs:baseschema", "DBO");
        A1415.addAttribute("rs:basetable", "A14");
        A1415.addAttribute("rs:basecolumn", "A1415");
        //s:datatype
        Element A1415datatype = A1415.addElement("s:datatype");
        A1415datatype.addAttribute("dt:type", "string");
        A1415datatype.addAttribute("rs:dbtype", "str");
        A1415datatype.addAttribute("dt:maxLength", "8");
        
    //  extends
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A14 a01 = (A14) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A1428", emptoString(map.get("A1428")));
				zrow.addAttribute("A1400", emptoString(map.get("A1400")));
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A1404A", emptoString(map.get("A1404A")));
				zrow.addAttribute("A1404B", emptoString(map.get("A1404B")));
				zrow.addAttribute("A1407", emptoString(map.get("A1407")));
				zrow.addAttribute("A1411A", emptoString(map.get("A1411A")));
				zrow.addAttribute("A1424", emptoString(map.get("A1424")));
				zrow.addAttribute("A1414", emptoString(map.get("A1414")));
				zrow.addAttribute("SORTID", emptoString(map.get("SORTID")));
				
				zrow.addAttribute("A1415", emptoString(map.get("A1415")));
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
        
        XMLWriter out = null;
        
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        
        try {
            File xmlFile = new File(uuid + "Table/" +"A14.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private static void A11toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A0000
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "1");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A11");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "60");
     // 2----------------s:AttributeType A1100
        Element A1100 = eleElement.addElement("s:AttributeType");
        A1100.addAttribute("name", "A1100");
        A1100.addAttribute("rs:number", "2");
        A1100.addAttribute("rs:nullable", "true");
        A1100.addAttribute("rs:write", "true");
        A1100.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1100.addAttribute("rs:baseschema", "DBO");
        A1100.addAttribute("rs:basetable", "A11");
        A1100.addAttribute("rs:basecolumn", "A1100");
        //s:datatype
        Element A1100datatype = A1100.addElement("s:datatype");
        A1100datatype.addAttribute("dt:type", "string");
        A1100datatype.addAttribute("rs:dbtype", "str");
        A1100datatype.addAttribute("dt:maxLength", "60");
     // 3----------------s:AttributeType A1101
        Element A1101 = eleElement.addElement("s:AttributeType");
        A1101.addAttribute("name", "A1101");
        A1101.addAttribute("rs:number", "3");
        A1101.addAttribute("rs:nullable", "true");
        A1101.addAttribute("rs:write", "true");
        A1101.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1101.addAttribute("rs:baseschema", "DBO");
        A1101.addAttribute("rs:basetable", "A11");
        A1101.addAttribute("rs:basecolumn", "A1101");
        //s:datatype
        Element A1101datatype = A1101.addElement("s:datatype");
        A1101datatype.addAttribute("dt:type", "string");
        A1101datatype.addAttribute("rs:dbtype", "str");
        A1101datatype.addAttribute("dt:maxLength", "8");
      // 4----------------s:AttributeType A1104
        Element A1104 = eleElement.addElement("s:AttributeType");
        A1104.addAttribute("name", "A1104");
        A1104.addAttribute("rs:number", "4");
        A1104.addAttribute("rs:nullable", "true");
        A1104.addAttribute("rs:write", "true");
        A1104.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1104.addAttribute("rs:baseschema", "DBO");
        A1104.addAttribute("rs:basetable", "A11");
        A1104.addAttribute("rs:basecolumn", "A1104");
        //s:datatype
        Element A1104datatype = A1104.addElement("s:datatype");
        A1104datatype.addAttribute("dt:type", "string");
        A1104datatype.addAttribute("rs:dbtype", "str");
        A1104datatype.addAttribute("dt:maxLength", "8");
     // 5----------------s:AttributeType A1107
        Element A1107 = eleElement.addElement("s:AttributeType");
        A1107.addAttribute("name", "A1107");
        A1107.addAttribute("rs:number", "5");
        A1107.addAttribute("rs:nullable", "true");
        A1107.addAttribute("rs:write", "true");
        A1107.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1107.addAttribute("rs:baseschema", "DBO");
        A1107.addAttribute("rs:basetable", "A11");
        A1107.addAttribute("rs:basecolumn", "A1107");
        //s:datatype
        Element A1107datatype = A1107.addElement("s:datatype");
        A1107datatype.addAttribute("dt:type", "string");
        A1107datatype.addAttribute("rs:dbtype", "str");
        A1107datatype.addAttribute("dt:maxLength", "40");
     // 6----------------s:AttributeType A1107A
        Element A1107A = eleElement.addElement("s:AttributeType");
        A1107A.addAttribute("name", "A1107A");
        A1107A.addAttribute("rs:number", "6");
        A1107A.addAttribute("rs:nullable", "true");
        A1107A.addAttribute("rs:write", "true");
        A1107A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1107A.addAttribute("rs:baseschema", "DBO");
        A1107A.addAttribute("rs:basetable", "A11");
        A1107A.addAttribute("rs:basecolumn", "A1107A");
        //s:datatype
        Element A1107Adatatype = A1107A.addElement("s:datatype");
        A1107Adatatype.addAttribute("dt:type", "int");
        A1107Adatatype.addAttribute("dt:maxLength", "4");
        A1107Adatatype.addAttribute("rs:precision", "10");
        A1107Adatatype.addAttribute("rs:fixedlength", "true");
     // 7----------------s:AttributeType A1107B
        Element A1107B = eleElement.addElement("s:AttributeType");
        A1107B.addAttribute("name", "A1107B");
        A1107B.addAttribute("rs:number", "7");
        A1107B.addAttribute("rs:nullable", "true");
        A1107B.addAttribute("rs:write", "true");
        A1107B.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1107B.addAttribute("rs:baseschema", "DBO");
        A1107B.addAttribute("rs:basetable", "A11");
        A1107B.addAttribute("rs:basecolumn", "A1107B");
        //s:datatype
        Element A1107Bdatatype = A1107B.addElement("s:datatype");
        A1107Bdatatype.addAttribute("dt:type", "int");
        A1107Bdatatype.addAttribute("dt:maxLength", "4");
        A1107Bdatatype.addAttribute("rs:precision", "10");
        A1107Bdatatype.addAttribute("rs:fixedlength", "true");
     // 8----------------s:AttributeType A1111
        Element A1111 = eleElement.addElement("s:AttributeType");
        A1111.addAttribute("name", "A1111");
        A1111.addAttribute("rs:number", "8");
        A1111.addAttribute("rs:nullable", "true");
        A1111.addAttribute("rs:write", "true");
        A1111.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1111.addAttribute("rs:baseschema", "DBO");
        A1111.addAttribute("rs:basetable", "A11");
        A1111.addAttribute("rs:basecolumn", "A1111");
        //s:datatype
        Element A1111datatype = A1111.addElement("s:datatype");
        A1111datatype.addAttribute("dt:type", "string");
        A1111datatype.addAttribute("rs:dbtype", "str");
        A1111datatype.addAttribute("dt:maxLength", "8");
     // 9----------------s:AttributeType A1114
        Element A1114 = eleElement.addElement("s:AttributeType");
        A1114.addAttribute("name", "A1114");
        A1114.addAttribute("rs:number", "9");
        A1114.addAttribute("rs:nullable", "true");
        A1114.addAttribute("rs:write", "true");
        A1114.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1114.addAttribute("rs:baseschema", "DBO");
        A1114.addAttribute("rs:basetable", "A11");
        A1114.addAttribute("rs:basecolumn", "A1114");
        //s:datatype
        Element A1114datatype = A1114.addElement("s:datatype");
        A1114datatype.addAttribute("dt:type", "string");
        A1114datatype.addAttribute("rs:dbtype", "str");
        A1114datatype.addAttribute("dt:maxLength", "120");
      // 10----------------s:AttributeType A1121A
        Element A1121A = eleElement.addElement("s:AttributeType");
        A1121A.addAttribute("name", "A1121A");
        A1121A.addAttribute("rs:number", "10");
        A1121A.addAttribute("rs:nullable", "true");
        A1121A.addAttribute("rs:write", "true");
        A1121A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1121A.addAttribute("rs:baseschema", "DBO");
        A1121A.addAttribute("rs:basetable", "A11");
        A1121A.addAttribute("rs:basecolumn", "A1121A");
        //s:datatype
        Element A1121Adatatype = A1121A.addElement("s:datatype");
        A1121Adatatype.addAttribute("dt:type", "string");
        A1121Adatatype.addAttribute("rs:dbtype", "str");
        A1121Adatatype.addAttribute("dt:maxLength", "120");
     // 11----------------s:AttributeType A1127
        Element A1127 = eleElement.addElement("s:AttributeType");
        A1127.addAttribute("name", "A1127");
        A1127.addAttribute("rs:number", "11");
        A1127.addAttribute("rs:nullable", "true");
        A1127.addAttribute("rs:write", "true");
        A1127.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1127.addAttribute("rs:baseschema", "DBO");
        A1127.addAttribute("rs:basetable", "A11");
        A1127.addAttribute("rs:basecolumn", "A1127");
        //s:datatype
        Element A1127datatype = A1127.addElement("s:datatype");
        A1127datatype.addAttribute("dt:type", "string");
        A1127datatype.addAttribute("rs:dbtype", "str");
        A1127datatype.addAttribute("dt:maxLength", "8");
     // 12----------------s:AttributeType A1131
        Element A1131 = eleElement.addElement("s:AttributeType");
        A1131.addAttribute("name", "A1131");
        A1131.addAttribute("rs:number", "12");
        A1131.addAttribute("rs:nullable", "true");
        A1131.addAttribute("rs:write", "true");
        A1131.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1131.addAttribute("rs:baseschema", "DBO");
        A1131.addAttribute("rs:basetable", "A11");
        A1131.addAttribute("rs:basecolumn", "A1131");
        //s:datatype
        Element A1131datatype = A1131.addElement("s:datatype");
        A1131datatype.addAttribute("dt:type", "string");
        A1131datatype.addAttribute("rs:dbtype", "str");
        A1131datatype.addAttribute("dt:maxLength", "60");
     // 13----------------s:AttributeType A1134
        Element A1134 = eleElement.addElement("s:AttributeType");
        A1134.addAttribute("name", "A1134");
        A1134.addAttribute("rs:number", "13");
        A1134.addAttribute("rs:nullable", "true");
        A1134.addAttribute("rs:write", "true");
        A1134.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1134.addAttribute("rs:baseschema", "DBO");
        A1134.addAttribute("rs:basetable", "A11");
        A1134.addAttribute("rs:basecolumn", "A1134");
        //s:datatype
        Element A1134datatype = A1134.addElement("s:datatype");
        A1134datatype.addAttribute("dt:type", "string");
        A1134datatype.addAttribute("rs:dbtype", "str");
        A1134datatype.addAttribute("dt:maxLength", "1");
     // 14----------------s:AttributeType A1151
        Element A1151 = eleElement.addElement("s:AttributeType");
        A1151.addAttribute("name", "A1151");
        A1151.addAttribute("rs:number", "14");
        A1151.addAttribute("rs:nullable", "true");
        A1151.addAttribute("rs:write", "true");
        A1151.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1151.addAttribute("rs:baseschema", "DBO");
        A1151.addAttribute("rs:basetable", "A11");
        A1151.addAttribute("rs:basecolumn", "A1151");
        //s:datatype
        Element A1151datatype = A1151.addElement("s:datatype");
        A1151datatype.addAttribute("dt:type", "string");
        A1151datatype.addAttribute("rs:dbtype", "str");
        A1151datatype.addAttribute("dt:maxLength", "1");
     // 15----------------s:AttributeType UPDATED
        Element UPDATED = eleElement.addElement("s:AttributeType");
        UPDATED.addAttribute("name", "UPDATED");
        UPDATED.addAttribute("rs:number", "15");
        UPDATED.addAttribute("rs:nullable", "true");
        UPDATED.addAttribute("rs:write", "true");
        UPDATED.addAttribute("rs:basecatalog", "OFFICIALV2");
        UPDATED.addAttribute("rs:baseschema", "DBO");
        UPDATED.addAttribute("rs:basetable", "A11");
        UPDATED.addAttribute("rs:basecolumn", "UPDATED");
        //s:datatype
        Element UPDATEDdatatype = UPDATED.addElement("s:datatype");
        UPDATEDdatatype.addAttribute("dt:type", "string");
        UPDATEDdatatype.addAttribute("rs:dbtype", "str");
        UPDATEDdatatype.addAttribute("dt:maxLength", "1");
        UPDATEDdatatype.addAttribute("rs:fixedlength", "true");
        /**
         * 加载数据 
         */
        //  extends
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A11 a01 = (A11) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A1100", emptoString(map.get("A1100")));
				zrow.addAttribute("A1101", emptoString(map.get("A1101")));
				zrow.addAttribute("A1104", emptoString(map.get("A1104")));
				zrow.addAttribute("A1107", emptoString(map.get("A1107")));
				zrow.addAttribute("A1107A", emptoString(map.get("A1107A")));
				zrow.addAttribute("A1107B", emptoString(map.get("A1107B")));
				zrow.addAttribute("A1111", emptoString(map.get("A1111")));
				zrow.addAttribute("A1114", emptoString(map.get("A1114")));
				zrow.addAttribute("A1121A", emptoString(map.get("A1121A")));
				
				zrow.addAttribute("A1127", emptoString(map.get("A1127")));
				zrow.addAttribute("A1131", emptoString(map.get("A1131")));
				zrow.addAttribute("A1151", emptoString(map.get("A1151")));
				zrow.addAttribute("UPDATED", emptoString(map.get("UPDATED")));
				
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
        
        XMLWriter out = null;
        
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        
        try {
            File xmlFile = new File(uuid + "Table/" +"A11.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private static void A08toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A0801A
        Element A0801A = eleElement.addElement("s:AttributeType");
        A0801A.addAttribute("name", "A0801A");
        A0801A.addAttribute("rs:number", "1");
        A0801A.addAttribute("rs:nullable", "true");
        A0801A.addAttribute("rs:write", "true");
        A0801A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0801A.addAttribute("rs:baseschema", "DBO");
        A0801A.addAttribute("rs:basetable", "A08");
        A0801A.addAttribute("rs:basecolumn", "A0801A");
        //s:datatype
        Element A0801Adatatype = A0801A.addElement("s:datatype");
        A0801Adatatype.addAttribute("dt:type", "string");
        A0801Adatatype.addAttribute("rs:dbtype", "str");
        A0801Adatatype.addAttribute("dt:maxLength", "120");
     // 2----------------s:AttributeType A0800
        Element A0800 = eleElement.addElement("s:AttributeType");
        A0800.addAttribute("name", "A0800");
        A0800.addAttribute("rs:number", "2");
        A0800.addAttribute("rs:nullable", "true");
        A0800.addAttribute("rs:write", "true");
        A0800.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0800.addAttribute("rs:baseschema", "DBO");
        A0800.addAttribute("rs:basetable", "A08");
        A0800.addAttribute("rs:basecolumn", "A0800");
        A0800.addAttribute("rs:keycolumn", "true");
        //s:datatype
        Element A0800datatype = A0800.addElement("s:datatype");
        A0800datatype.addAttribute("dt:type", "string");
        A0800datatype.addAttribute("rs:dbtype", "str");
        A0800datatype.addAttribute("dt:maxLength", "60");
     // 3----------------s:AttributeType A0000
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "3");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A08");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "60");
      // 4----------------s:AttributeType A0827
        Element A0827 = eleElement.addElement("s:AttributeType");
        A0827.addAttribute("name", "A0827");
        A0827.addAttribute("rs:number", "4");
        A0827.addAttribute("rs:nullable", "true");
        A0827.addAttribute("rs:write", "true");
        A0827.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0827.addAttribute("rs:baseschema", "DBO");
        A0827.addAttribute("rs:basetable", "A08");
        A0827.addAttribute("rs:basecolumn", "A0827");
        //s:datatype
        Element A0827datatype = A0827.addElement("s:datatype");
        A0827datatype.addAttribute("dt:type", "string");
        A0827datatype.addAttribute("rs:dbtype", "str");
        A0827datatype.addAttribute("dt:maxLength", "8");
     // 5----------------s:AttributeType A0824
        Element A0824 = eleElement.addElement("s:AttributeType");
        A0824.addAttribute("name", "A0824");
        A0824.addAttribute("rs:number", "5");
        A0824.addAttribute("rs:nullable", "true");
        A0824.addAttribute("rs:write", "true");
        A0824.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0824.addAttribute("rs:baseschema", "DBO");
        A0824.addAttribute("rs:basetable", "A08");
        A0824.addAttribute("rs:basecolumn", "A0824");
        //s:datatype
        Element A0824datatype = A0824.addElement("s:datatype");
        A0824datatype.addAttribute("dt:type", "string");
        A0824datatype.addAttribute("rs:dbtype", "str");
        A0824datatype.addAttribute("dt:maxLength", "40");
     // 6----------------s:AttributeType A0801B
        Element A0801B = eleElement.addElement("s:AttributeType");
        A0801B.addAttribute("name", "A0801B");
        A0801B.addAttribute("rs:number", "6");
        A0801B.addAttribute("rs:nullable", "true");
        A0801B.addAttribute("rs:write", "true");
        A0801B.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0801B.addAttribute("rs:baseschema", "DBO");
        A0801B.addAttribute("rs:basetable", "A08");
        A0801B.addAttribute("rs:basecolumn", "A0801B");
        //s:datatype
        Element A0801Bdatatype = A0801B.addElement("s:datatype");
        A0801Bdatatype.addAttribute("dt:type", "string");
        A0801Bdatatype.addAttribute("rs:dbtype", "str");
        A0801Bdatatype.addAttribute("dt:maxLength", "8");
     // 7----------------s:AttributeType A0901B
        Element A0901B = eleElement.addElement("s:AttributeType");
        A0901B.addAttribute("name", "A0901B");
        A0901B.addAttribute("rs:number", "7");
        A0901B.addAttribute("rs:nullable", "true");
        A0901B.addAttribute("rs:write", "true");
        A0901B.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0901B.addAttribute("rs:baseschema", "DBO");
        A0901B.addAttribute("rs:basetable", "A08");
        A0901B.addAttribute("rs:basecolumn", "A0901B");
        //s:datatype
        Element A0901Bdatatype = A0901B.addElement("s:datatype");
        A0901Bdatatype.addAttribute("dt:type", "string");
        A0901Bdatatype.addAttribute("rs:dbtype", "str");
        A0901Bdatatype.addAttribute("dt:maxLength", "8");
     // 8----------------s:AttributeType A0901A
        Element A0901A = eleElement.addElement("s:AttributeType");
        A0901A.addAttribute("name", "A0901A");
        A0901A.addAttribute("rs:number", "8");
        A0901A.addAttribute("rs:nullable", "true");
        A0901A.addAttribute("rs:write", "true");
        A0901A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0901A.addAttribute("rs:baseschema", "DBO");
        A0901A.addAttribute("rs:basetable", "A08");
        A0901A.addAttribute("rs:basecolumn", "A0901A");
        //s:datatype
        Element A0901Adatatype = A0901A.addElement("s:datatype");
        A0901Adatatype.addAttribute("dt:type", "string");
        A0901Adatatype.addAttribute("rs:dbtype", "str");
        A0901Adatatype.addAttribute("dt:maxLength", "40");
     // 9----------------s:AttributeType A0804
        Element A0804 = eleElement.addElement("s:AttributeType");
        A0804.addAttribute("name", "A0804");
        A0804.addAttribute("rs:number", "9");
        A0804.addAttribute("rs:nullable", "true");
        A0804.addAttribute("rs:write", "true");
        A0804.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0804.addAttribute("rs:baseschema", "DBO");
        A0804.addAttribute("rs:basetable", "A08");
        A0804.addAttribute("rs:basecolumn", "A0804");
        //s:datatype
        Element A0804datatype = A0804.addElement("s:datatype");
        A0804datatype.addAttribute("dt:type", "string");
        A0804datatype.addAttribute("rs:dbtype", "str");
        A0804datatype.addAttribute("dt:maxLength", "8");
      // 10----------------s:AttributeType A0807
        Element A0807 = eleElement.addElement("s:AttributeType");
        A0807.addAttribute("name", "A0807");
        A0807.addAttribute("rs:number", "10");
        A0807.addAttribute("rs:nullable", "true");
        A0807.addAttribute("rs:write", "true");
        A0807.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0807.addAttribute("rs:baseschema", "DBO");
        A0807.addAttribute("rs:basetable", "A08");
        A0807.addAttribute("rs:basecolumn", "A0807");
        //s:datatype
        Element A0807datatype = A0807.addElement("s:datatype");
        A0807datatype.addAttribute("dt:type", "string");
        A0807datatype.addAttribute("rs:dbtype", "str");
        A0807datatype.addAttribute("dt:maxLength", "8");
     // 11----------------s:AttributeType A0904
        Element A0904 = eleElement.addElement("s:AttributeType");
        A0904.addAttribute("name", "A0904");
        A0904.addAttribute("rs:number", "11");
        A0904.addAttribute("rs:nullable", "true");
        A0904.addAttribute("rs:write", "true");
        A0904.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0904.addAttribute("rs:baseschema", "DBO");
        A0904.addAttribute("rs:basetable", "A08");
        A0904.addAttribute("rs:basecolumn", "A0904");
        //s:datatype
        Element A0904datatype = A0904.addElement("s:datatype");
        A0904datatype.addAttribute("dt:type", "string");
        A0904datatype.addAttribute("rs:dbtype", "str");
        A0904datatype.addAttribute("dt:maxLength", "8");
     // 12----------------s:AttributeType A0814
        Element A0814 = eleElement.addElement("s:AttributeType");
        A0814.addAttribute("name", "A0814");
        A0814.addAttribute("rs:number", "12");
        A0814.addAttribute("rs:nullable", "true");
        A0814.addAttribute("rs:write", "true");
        A0814.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0814.addAttribute("rs:baseschema", "DBO");
        A0814.addAttribute("rs:basetable", "A08");
        A0814.addAttribute("rs:basecolumn", "A0814");
        //s:datatype
        Element A0814datatype = A0814.addElement("s:datatype");
        A0814datatype.addAttribute("dt:type", "string");
        A0814datatype.addAttribute("rs:dbtype", "str");
        A0814datatype.addAttribute("dt:maxLength", "120");
     // 13----------------s:AttributeType A0834
        Element A0834 = eleElement.addElement("s:AttributeType");
        A0834.addAttribute("name", "A0834");
        A0834.addAttribute("rs:number", "13");
        A0834.addAttribute("rs:nullable", "true");
        A0834.addAttribute("rs:write", "true");
        A0834.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0834.addAttribute("rs:baseschema", "DBO");
        A0834.addAttribute("rs:basetable", "A08");
        A0834.addAttribute("rs:basecolumn", "A0834");
        //s:datatype
        Element A0834datatype = A0834.addElement("s:datatype");
        A0834datatype.addAttribute("dt:type", "string");
        A0834datatype.addAttribute("rs:dbtype", "str");
        A0834datatype.addAttribute("dt:maxLength", "1");
     // 14----------------s:AttributeType A0835
        Element A0835 = eleElement.addElement("s:AttributeType");
        A0835.addAttribute("name", "A0835");
        A0835.addAttribute("rs:number", "14");
        A0835.addAttribute("rs:nullable", "true");
        A0835.addAttribute("rs:write", "true");
        A0835.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0835.addAttribute("rs:baseschema", "DBO");
        A0835.addAttribute("rs:basetable", "A08");
        A0835.addAttribute("rs:basecolumn", "A0835");
        //s:datatype
        Element A0835datatype = A0835.addElement("s:datatype");
        A0835datatype.addAttribute("dt:type", "string");
        A0835datatype.addAttribute("rs:dbtype", "str");
        A0835datatype.addAttribute("dt:maxLength", "1");
     // 15----------------s:AttributeType A0837
        Element A0837 = eleElement.addElement("s:AttributeType");
        A0837.addAttribute("name", "A0837");
        A0837.addAttribute("rs:number", "15");
        A0837.addAttribute("rs:nullable", "true");
        A0837.addAttribute("rs:write", "true");
        A0837.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0837.addAttribute("rs:baseschema", "DBO");
        A0837.addAttribute("rs:basetable", "A08");
        A0837.addAttribute("rs:basecolumn", "A0837");
        //s:datatype
        Element A0837datatype = A0837.addElement("s:datatype");
        A0837datatype.addAttribute("dt:type", "string");
        A0837datatype.addAttribute("rs:dbtype", "str");
        A0837datatype.addAttribute("dt:maxLength", "1");
      // 16----------------s:AttributeType A0899
        Element A0899 = eleElement.addElement("s:AttributeType");
        A0899.addAttribute("name", "A0899");
        A0899.addAttribute("rs:number", "16");
        A0899.addAttribute("rs:nullable", "true");
        A0899.addAttribute("rs:write", "true");
        A0899.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0899.addAttribute("rs:baseschema", "DBO");
        A0899.addAttribute("rs:basetable", "A08");
        A0899.addAttribute("rs:basecolumn", "A0899");
        //s:datatype
        Element A0899datatype = A0899.addElement("s:datatype");
        A0899datatype.addAttribute("dt:type", "string");
        A0899datatype.addAttribute("rs:dbtype", "str");
        A0899datatype.addAttribute("dt:maxLength", "1"); 
     // 17----------------s:AttributeType A0898
        Element A0898 = eleElement.addElement("s:AttributeType");
        A0898.addAttribute("name", "A0898");
        A0898.addAttribute("rs:number", "17");
        A0898.addAttribute("rs:nullable", "true");
        A0898.addAttribute("rs:write", "true");
        A0898.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0898.addAttribute("rs:baseschema", "DBO");
        A0898.addAttribute("rs:basetable", "A08");
        A0898.addAttribute("rs:basecolumn", "A0898");
        //s:datatype
        Element A0898datatype = A0898.addElement("s:datatype");
        A0898datatype.addAttribute("dt:type", "string");
        A0898datatype.addAttribute("rs:dbtype", "str");
        A0898datatype.addAttribute("dt:maxLength", "1");
        // 18----------------s:AttributeType A0831
        Element A0831 = eleElement.addElement("s:AttributeType");
        A0831.addAttribute("name", "A0831");
        A0831.addAttribute("rs:number", "18");
        A0831.addAttribute("rs:nullable", "true");
        A0831.addAttribute("rs:write", "true");
        A0831.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0831.addAttribute("rs:baseschema", "DBO");
        A0831.addAttribute("rs:basetable", "A08");
        A0831.addAttribute("rs:basecolumn", "A0831");
        //s:datatype
        Element A0831datatype = A0831.addElement("s:datatype");
        A0831datatype.addAttribute("dt:type", "string");
        A0831datatype.addAttribute("rs:dbtype", "str");
        A0831datatype.addAttribute("dt:maxLength", "1"); 
        A0831datatype.addAttribute("rs:fixedlength", "true");
     // 19----------------s:AttributeType A0838
        Element A0838 = eleElement.addElement("s:AttributeType");
        A0838.addAttribute("name", "A0838");
        A0838.addAttribute("rs:number", "19");
        A0838.addAttribute("rs:nullable", "true");
        A0838.addAttribute("rs:write", "true");
        A0838.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0838.addAttribute("rs:baseschema", "DBO");
        A0838.addAttribute("rs:basetable", "A08");
        A0838.addAttribute("rs:basecolumn", "A0838");
        //s:datatype
        Element A0838datatype = A0838.addElement("s:datatype");
        A0838datatype.addAttribute("dt:type", "string");
        A0838datatype.addAttribute("rs:dbtype", "str");
        A0838datatype.addAttribute("dt:maxLength", "1");
        A0838datatype.addAttribute("rs:fixedlength", "true");
     // 20----------------s:AttributeType A0832
        Element A0832 = eleElement.addElement("s:AttributeType");
        A0832.addAttribute("name", "A0832");
        A0832.addAttribute("rs:number", "20");
        A0832.addAttribute("rs:nullable", "true");
        A0832.addAttribute("rs:write", "true");
        A0832.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0832.addAttribute("rs:baseschema", "DBO");
        A0832.addAttribute("rs:basetable", "A08");
        A0832.addAttribute("rs:basecolumn", "A0832");
        //s:datatype
        Element A0832datatype = A0832.addElement("s:datatype");
        A0832datatype.addAttribute("dt:type", "string");
        A0832datatype.addAttribute("rs:dbtype", "str");
        A0832datatype.addAttribute("dt:maxLength", "1"); 
        A0832datatype.addAttribute("rs:fixedlength", "true");
     // 21----------------s:AttributeType A0839
        Element A0839 = eleElement.addElement("s:AttributeType");
        A0839.addAttribute("name", "A0839");
        A0839.addAttribute("rs:number", "21");
        A0839.addAttribute("rs:nullable", "true");
        A0839.addAttribute("rs:write", "true");
        A0839.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0839.addAttribute("rs:baseschema", "DBO");
        A0839.addAttribute("rs:basetable", "A08");
        A0839.addAttribute("rs:basecolumn", "A0839");
        //s:datatype
        Element A0839datatype = A0839.addElement("s:datatype");
        A0839datatype.addAttribute("dt:type", "string");
        A0839datatype.addAttribute("rs:dbtype", "str");
        A0839datatype.addAttribute("dt:maxLength", "1"); 
        A0839datatype.addAttribute("rs:fixedlength", "true");
        
     // 22----------------s:AttributeType A0811
        Element A0811 = eleElement.addElement("s:AttributeType");
        A0811.addAttribute("name", "A0811");
        A0811.addAttribute("rs:number", "22");
        A0811.addAttribute("rs:nullable", "true");
        A0811.addAttribute("rs:write", "true");
        A0811.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0811.addAttribute("rs:baseschema", "DBO");
        A0811.addAttribute("rs:basetable", "A08");
        A0811.addAttribute("rs:basecolumn", "A0811");
        //s:datatype
        Element A0811datatype = A0811.addElement("s:datatype");
        A0811datatype.addAttribute("dt:type", "string");
//        A0811datatype.addAttribute("rs:dbtype", "str");
        A0811datatype.addAttribute("dt:maxLength", "8"); 
        // 23----------------s:AttributeType SORTID
        Element SORTID = eleElement.addElement("s:AttributeType");
        SORTID.addAttribute("name", "SORTID");
        SORTID.addAttribute("rs:number", "23");
        SORTID.addAttribute("rs:nullable", "true");
        SORTID.addAttribute("rs:write", "true");
        SORTID.addAttribute("rs:basecatalog", "OFFICIALV2");
        SORTID.addAttribute("rs:baseschema", "DBO");
        SORTID.addAttribute("rs:basetable", "A08");
        SORTID.addAttribute("rs:basecolumn", "SORTID");
        //s:datatype
        Element SORTIDdatatype = SORTID.addElement("s:datatype");
        SORTIDdatatype.addAttribute("dt:type", "int");
        SORTIDdatatype.addAttribute("dt:maxLength", "4");
        SORTIDdatatype.addAttribute("rs:precision", "10"); 
        SORTIDdatatype.addAttribute("rs:fixedlength", "true"); 
        /**
         * 加载数据 
         */
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A08 a01 = (A08) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A0801A", emptoString(map.get("A0801A")));
				zrow.addAttribute("A0800", emptoString(map.get("A0800")));
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A0827", emptoString(map.get("A0827")));
				zrow.addAttribute("A0824", emptoString(map.get("A0824")));
				zrow.addAttribute("A0801B", emptoString(map.get("A0801B")));
				zrow.addAttribute("A0901B", emptoString(map.get("A0901B")));
				zrow.addAttribute("A0901A", emptoString(map.get("A0901A")));
				zrow.addAttribute("A0804", emptoString(map.get("A0804")));
				zrow.addAttribute("A0807", emptoString(map.get("A0807")));
				
				zrow.addAttribute("A0904", emptoString(map.get("A0904")));
				zrow.addAttribute("A0814", emptoString(map.get("A0814")));
				zrow.addAttribute("A0834", emptoString(map.get("A0834")));
				zrow.addAttribute("A0835", emptoString(map.get("A0835")));
				zrow.addAttribute("A0837", emptoString(map.get("A0837")));
				zrow.addAttribute("A0899", emptoString(map.get("A0899")).equals("true")?"1":"0");
				zrow.addAttribute("A0898", emptoString(map.get("A0898")));
				zrow.addAttribute("A0831", emptoString(map.get("A0831")));
				zrow.addAttribute("A0838", emptoString(map.get("A0838")));
				zrow.addAttribute("A0832", emptoString(map.get("A0832")));
				
				zrow.addAttribute("A0839", emptoString(map.get("A0839")));
				zrow.addAttribute("A0811", emptoString(map.get("A0811")));
				zrow.addAttribute("SORTID", emptoString(map.get("SORTID")));
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
        
        XMLWriter out = null;
        
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        
        try {
            File xmlFile = new File(uuid + "Table/" +"A08.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}

	private static void A06toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A0611
        Element A0611 = eleElement.addElement("s:AttributeType");
        A0611.addAttribute("name", "A0611");
        A0611.addAttribute("rs:number", "1");
        A0611.addAttribute("rs:nullable", "true");
        A0611.addAttribute("rs:write", "true");
        A0611.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0611.addAttribute("rs:baseschema", "DBO");
        A0611.addAttribute("rs:basetable", "A06");
        A0611.addAttribute("rs:basecolumn", "A0611");
        //s:datatype
        Element A0611datatype = A0611.addElement("s:datatype");
        A0611datatype.addAttribute("dt:type", "string");
        A0611datatype.addAttribute("rs:dbtype", "str");
        A0611datatype.addAttribute("dt:maxLength", "100");
     // 2----------------s:AttributeType A0614
        Element A0614 = eleElement.addElement("s:AttributeType");
        A0614.addAttribute("name", "A0614");
        A0614.addAttribute("rs:number", "2");
        A0614.addAttribute("rs:nullable", "true");
        A0614.addAttribute("rs:write", "true");
        A0614.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0614.addAttribute("rs:baseschema", "DBO");
        A0614.addAttribute("rs:basetable", "A06");
        A0614.addAttribute("rs:basecolumn", "A0614");
        //s:datatype
        Element A0614datatype = A0614.addElement("s:datatype");
        A0614datatype.addAttribute("dt:type", "string");
        A0614datatype.addAttribute("rs:dbtype", "str");
        A0614datatype.addAttribute("dt:maxLength", "1");
        A0614datatype.addAttribute("rs:fixedlength", "true");
     // 3----------------s:AttributeType A0000
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "3");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A06");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "60");
      // 4----------------s:AttributeType A0600
        Element A0600 = eleElement.addElement("s:AttributeType");
        A0600.addAttribute("name", "A0600");
        A0600.addAttribute("rs:number", "4");
        A0600.addAttribute("rs:nullable", "true");
        A0600.addAttribute("rs:write", "true");
        A0600.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0600.addAttribute("rs:baseschema", "DBO");
        A0600.addAttribute("rs:basetable", "A06");
        A0600.addAttribute("rs:basecolumn", "A0600");
        A0600.addAttribute("rs:keycolumn", "true");
        //s:datatype
        Element A0600datatype = A0600.addElement("s:datatype");
        A0600datatype.addAttribute("dt:type", "string");
        A0600datatype.addAttribute("rs:dbtype", "str");
        A0600datatype.addAttribute("dt:maxLength", "60");
     // 5----------------s:AttributeType A0601
        Element A0601 = eleElement.addElement("s:AttributeType");
        A0601.addAttribute("name", "A0601");
        A0601.addAttribute("rs:number", "5");
        A0601.addAttribute("rs:nullable", "true");
        A0601.addAttribute("rs:write", "true");
        A0601.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0601.addAttribute("rs:baseschema", "DBO");
        A0601.addAttribute("rs:basetable", "A06");
        A0601.addAttribute("rs:basecolumn", "A0601");
        //s:datatype
        Element A0601datatype = A0601.addElement("s:datatype");
        A0601datatype.addAttribute("dt:type", "string");
        A0601datatype.addAttribute("rs:dbtype", "str");
        A0601datatype.addAttribute("dt:maxLength", "8");
     // 6----------------s:AttributeType A0602
        Element A0602 = eleElement.addElement("s:AttributeType");
        A0602.addAttribute("name", "A0602");
        A0602.addAttribute("rs:number", "6");
        A0602.addAttribute("rs:nullable", "true");
        A0602.addAttribute("rs:write", "true");
        A0602.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0602.addAttribute("rs:baseschema", "DBO");
        A0602.addAttribute("rs:basetable", "A06");
        A0602.addAttribute("rs:basecolumn", "A0602");
        //s:datatype
        Element A0602datatype = A0602.addElement("s:datatype");
        A0602datatype.addAttribute("dt:type", "string");
        A0602datatype.addAttribute("rs:dbtype", "str");
        A0602datatype.addAttribute("dt:maxLength", "60");
     // 7----------------s:AttributeType A0604
        Element A0604 = eleElement.addElement("s:AttributeType");
        A0604.addAttribute("name", "A0604");
        A0604.addAttribute("rs:number", "7");
        A0604.addAttribute("rs:nullable", "true");
        A0604.addAttribute("rs:write", "true");
        A0604.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0604.addAttribute("rs:baseschema", "DBO");
        A0604.addAttribute("rs:basetable", "A06");
        A0604.addAttribute("rs:basecolumn", "A0604");
        //s:datatype
        Element A0604datatype = A0604.addElement("s:datatype");
        A0604datatype.addAttribute("dt:type", "string");
        A0604datatype.addAttribute("rs:dbtype", "str");
        A0604datatype.addAttribute("dt:maxLength", "8");
     // 8----------------s:AttributeType A0607
        Element A0607 = eleElement.addElement("s:AttributeType");
        A0607.addAttribute("name", "A0607");
        A0607.addAttribute("rs:number", "8");
        A0607.addAttribute("rs:nullable", "true");
        A0607.addAttribute("rs:write", "true");
        A0607.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0607.addAttribute("rs:baseschema", "DBO");
        A0607.addAttribute("rs:basetable", "A06");
        A0607.addAttribute("rs:basecolumn", "A0607");
        //s:datatype
        Element A0607datatype = A0607.addElement("s:datatype");
        A0607datatype.addAttribute("dt:type", "string");
        A0607datatype.addAttribute("rs:dbtype", "str");
        A0607datatype.addAttribute("dt:maxLength", "8");
        
        /**
         * 加载数据 
         */
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A06 a01 = (A06) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A0611", emptoString(map.get("A0611")));
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A0600", emptoString(map.get("A0600")));
				zrow.addAttribute("A0601", emptoString(map.get("A0601")));
				zrow.addAttribute("A0602", emptoString(map.get("A0602")));
				zrow.addAttribute("A0604", emptoString(map.get("A0604")));
				zrow.addAttribute("A0607", emptoString(map.get("A0607")));
				
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
        
        XMLWriter out = null;
        
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        
        try {
            File xmlFile = new File(uuid + "Table/" +"A06.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}

	private static void A02toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A0191
        Element A0191 = eleElement.addElement("s:AttributeType");
        A0191.addAttribute("name", "A0223");
        A0191.addAttribute("rs:number", "1");
        A0191.addAttribute("rs:nullable", "true");
        A0191.addAttribute("rs:write", "true");
        A0191.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0191.addAttribute("rs:baseschema", "DBO");
        A0191.addAttribute("rs:basetable", "A02");
        A0191.addAttribute("rs:basecolumn", "A0223");
        //s:datatype
        Element A0191datatype = A0191.addElement("s:datatype");
        A0191datatype.addAttribute("dt:type", "string");
        A0191datatype.addAttribute("rs:dbtype", "str");
        A0191datatype.addAttribute("dt:maxLength", "8");
     // 2----------------s:AttributeType A0128
        Element A0128 = eleElement.addElement("s:AttributeType");
        A0128.addAttribute("name", "A0201C");
        A0128.addAttribute("rs:number", "2");
        A0128.addAttribute("rs:nullable", "true");
        A0128.addAttribute("rs:write", "true");
        A0128.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0128.addAttribute("rs:baseschema", "DBO");
        A0128.addAttribute("rs:basetable", "A02");
        A0128.addAttribute("rs:basecolumn", "A0201C");
        //s:datatype
        Element A0128datatype = A0128.addElement("s:datatype");
        A0128datatype.addAttribute("dt:type", "string");
        A0128datatype.addAttribute("rs:dbtype", "str");
        A0128datatype.addAttribute("dt:maxLength", "200");
     // 3----------------s:AttributeType ZZXWXX
        Element A0200 = eleElement.addElement("s:AttributeType");
        A0200.addAttribute("name", "A0200");
        A0200.addAttribute("rs:number", "3");
        A0200.addAttribute("rs:nullable", "true");
        A0200.addAttribute("rs:write", "true");
        A0200.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0200.addAttribute("rs:baseschema", "DBO");
        A0200.addAttribute("rs:basetable", "A02");
        A0200.addAttribute("rs:basecolumn", "A0200");
        A0200.addAttribute("rs:keycolumn", "true");
        //s:datatype
        Element A0200datatype = A0200.addElement("s:datatype");
        A0200datatype.addAttribute("dt:type", "string");
        A0200datatype.addAttribute("rs:dbtype", "str");
        A0200datatype.addAttribute("dt:maxLength", "60");
        
      // 4----------------s:AttributeType ZZXLXX
        Element ZZXLXX = eleElement.addElement("s:AttributeType");
        ZZXLXX.addAttribute("name", "A0216A");
        ZZXLXX.addAttribute("rs:number", "4");
        ZZXLXX.addAttribute("rs:nullable", "true");
        ZZXLXX.addAttribute("rs:write", "true");
        ZZXLXX.addAttribute("rs:basecatalog", "OFFICIALV2");
        ZZXLXX.addAttribute("rs:baseschema", "DBO");
        ZZXLXX.addAttribute("rs:basetable", "A02");
        ZZXLXX.addAttribute("rs:basecolumn", "A0216A");
        //s:datatype
        Element ZZXLXXdatatype = ZZXLXX.addElement("s:datatype");
        ZZXLXXdatatype.addAttribute("dt:type", "string");
        ZZXLXXdatatype.addAttribute("rs:dbtype", "str");
        ZZXLXXdatatype.addAttribute("dt:maxLength", "200");
     // 5----------------s:AttributeType QRZXWXX
        Element QRZXWXX = eleElement.addElement("s:AttributeType");
        QRZXWXX.addAttribute("name", "A0000");
        QRZXWXX.addAttribute("rs:number", "5");
        QRZXWXX.addAttribute("rs:nullable", "true");
        QRZXWXX.addAttribute("rs:write", "true");
        QRZXWXX.addAttribute("rs:basecatalog", "OFFICIALV2");
        QRZXWXX.addAttribute("rs:baseschema", "DBO");
        QRZXWXX.addAttribute("rs:basetable", "A02");
        QRZXWXX.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element QRZXWXXdatatype = QRZXWXX.addElement("s:datatype");
        QRZXWXXdatatype.addAttribute("dt:type", "string");
        QRZXWXXdatatype.addAttribute("rs:dbtype", "str");
        QRZXWXXdatatype.addAttribute("dt:maxLength", "60");
     // 6----------------s:AttributeType QRZXLXX
        Element QRZXLXX = eleElement.addElement("s:AttributeType");
        QRZXLXX.addAttribute("name", "A0201B");
        QRZXLXX.addAttribute("rs:number", "6");
        QRZXLXX.addAttribute("rs:nullable", "true");
        QRZXLXX.addAttribute("rs:write", "true");
        QRZXLXX.addAttribute("rs:basecatalog", "OFFICIALV2");
        QRZXLXX.addAttribute("rs:baseschema", "DBO");
        QRZXLXX.addAttribute("rs:basetable", "A02");
        QRZXLXX.addAttribute("rs:basecolumn", "A0201B");
        //s:datatype
        Element QRZXLXXdatatype = QRZXLXX.addElement("s:datatype");
        QRZXLXXdatatype.addAttribute("dt:type", "string");
        QRZXLXXdatatype.addAttribute("rs:dbtype", "str");
        QRZXLXXdatatype.addAttribute("dt:maxLength", "199");
     // 7----------------s:AttributeType A0192A
        Element A0192A = eleElement.addElement("s:AttributeType");
        A0192A.addAttribute("name", "A0201");
        A0192A.addAttribute("rs:number", "7");
        A0192A.addAttribute("rs:nullable", "true");
        A0192A.addAttribute("rs:write", "true");
        A0192A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0192A.addAttribute("rs:baseschema", "DBO");
        A0192A.addAttribute("rs:basetable", "A02");
        A0192A.addAttribute("rs:basecolumn", "A0201");
        //s:datatype
        Element A0192Adatatype = A0192A.addElement("s:datatype");
        A0192Adatatype.addAttribute("dt:type", "string");
        A0192Adatatype.addAttribute("rs:dbtype", "str");
        A0192Adatatype.addAttribute("dt:maxLength", "199");
     // 8----------------s:AttributeType A015A
        Element A015A = eleElement.addElement("s:AttributeType");
        A015A.addAttribute("name", "A0201A");
        A015A.addAttribute("rs:number", "8");
        A015A.addAttribute("rs:nullable", "true");
        A015A.addAttribute("rs:write", "true");
        A015A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A015A.addAttribute("rs:baseschema", "DBO");
        A015A.addAttribute("rs:basetable", "A02");
        A015A.addAttribute("rs:basecolumn", "A0201A");
        //s:datatype
        Element A015Adatatype = A015A.addElement("s:datatype");
        A015Adatatype.addAttribute("dt:type", "string");
        A015Adatatype.addAttribute("rs:dbtype", "str");
        A015Adatatype.addAttribute("dt:maxLength", "1000");
     // 9----------------s:AttributeType A0192B
        Element A0192B = eleElement.addElement("s:AttributeType");
        A0192B.addAttribute("name", "A0207");
        A0192B.addAttribute("rs:number", "9");
        A0192B.addAttribute("rs:nullable", "true");
        A0192B.addAttribute("rs:write", "true");
        A0192B.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0192B.addAttribute("rs:baseschema", "DBO");
        A0192B.addAttribute("rs:basetable", "A02");
        A0192B.addAttribute("rs:basecolumn", "A0207");
        //s:datatype
        Element A0192Bdatatype = A0192B.addElement("s:datatype");
        A0192Bdatatype.addAttribute("dt:type", "string");
        A0192Bdatatype.addAttribute("rs:dbtype", "str");
        A0192Bdatatype.addAttribute("dt:maxLength", "8");
      // 10----------------s:AttributeType A0192B
        Element A0180 = eleElement.addElement("s:AttributeType");
        A0180.addAttribute("name", "A0209");
        A0180.addAttribute("rs:number", "10");
        A0180.addAttribute("rs:nullable", "true");
        A0180.addAttribute("rs:write", "true");
        A0180.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0180.addAttribute("rs:baseschema", "DBO");
        A0180.addAttribute("rs:basetable", "A02");
        A0180.addAttribute("rs:basecolumn", "A0209");
        //s:datatype
        Element A0180datatype = A0180.addElement("s:datatype");
        A0180datatype.addAttribute("dt:type", "string");
        A0180datatype.addAttribute("rs:dbtype", "str");
        A0180datatype.addAttribute("dt:maxLength", "8");
     // 11----------------s:AttributeType A0000
        Element A0215A = eleElement.addElement("s:AttributeType");
        A0215A.addAttribute("name", "A0215A");
        A0215A.addAttribute("rs:number", "11");
        A0215A.addAttribute("rs:nullable", "true");
        A0215A.addAttribute("rs:write", "true");
        A0215A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0215A.addAttribute("rs:baseschema", "DBO");
        A0215A.addAttribute("rs:basetable", "A02");
        A0215A.addAttribute("rs:basecolumn", "A0215A");
        //s:datatype
        Element A0215Adatatype = A0215A.addElement("s:datatype");
        A0215Adatatype.addAttribute("dt:type", "string");
        A0215Adatatype.addAttribute("rs:dbtype", "str");
        A0215Adatatype.addAttribute("dt:maxLength", "8");
     // 12----------------s:AttributeType A0101
        Element A0101 = eleElement.addElement("s:AttributeType");
        A0101.addAttribute("name", "A0215B");
        A0101.addAttribute("rs:number", "12");
        A0101.addAttribute("rs:nullable", "true");
        A0101.addAttribute("rs:write", "true");
        A0101.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0101.addAttribute("rs:baseschema", "DBO");
        A0101.addAttribute("rs:basetable", "A02");
        A0101.addAttribute("rs:basecolumn", "A0215B");
        //s:datatype
        Element A0101datatype = A0101.addElement("s:datatype");
        A0101datatype.addAttribute("dt:type", "string");
        A0101datatype.addAttribute("rs:dbtype", "str");
        A0101datatype.addAttribute("dt:maxLength", "80");
     // 13----------------s:AttributeType A0102
        Element A0219 = eleElement.addElement("s:AttributeType");
        A0219.addAttribute("name", "A0219");
        A0219.addAttribute("rs:number", "13");
        A0219.addAttribute("rs:nullable", "true");
        A0219.addAttribute("rs:write", "true");
        A0219.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0219.addAttribute("rs:baseschema", "DBO");
        A0219.addAttribute("rs:basetable", "A02");
        A0219.addAttribute("rs:basecolumn", "A0219");
        //s:datatype
        Element A0219datatype = A0219.addElement("s:datatype");
        A0219datatype.addAttribute("dt:type", "string");
        A0219datatype.addAttribute("rs:dbtype", "str");
        A0219datatype.addAttribute("dt:maxLength", "8");
     // 14----------------s:AttributeType A0104
        Element A0104 = eleElement.addElement("s:AttributeType");
        A0104.addAttribute("name", "A0221");
        A0104.addAttribute("rs:number", "14");
        A0104.addAttribute("rs:nullable", "true");
        A0104.addAttribute("rs:write", "true");
        A0104.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0104.addAttribute("rs:baseschema", "DBO");
        A0104.addAttribute("rs:basetable", "A02");
        A0104.addAttribute("rs:basecolumn", "A0221");
        //s:datatype
        Element A0104datatype = A0104.addElement("s:datatype");
        A0104datatype.addAttribute("dt:type", "string");
        A0104datatype.addAttribute("rs:dbtype", "str");
        A0104datatype.addAttribute("dt:maxLength", "8");
     // 15----------------s:AttributeType A0107
        Element A0107 = eleElement.addElement("s:AttributeType");
        A0107.addAttribute("name", "A0229");
        A0107.addAttribute("rs:number", "15");
        A0107.addAttribute("rs:nullable", "true");
        A0107.addAttribute("rs:write", "true");
        A0107.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0107.addAttribute("rs:baseschema", "DBO");
        A0107.addAttribute("rs:basetable", "A02");
        A0107.addAttribute("rs:basecolumn", "A0229");
        //s:datatype
        Element A0107datatype = A0107.addElement("s:datatype");
        A0107datatype.addAttribute("dt:type", "string");
        A0107datatype.addAttribute("rs:dbtype", "str");
        A0107datatype.addAttribute("dt:maxLength", "120");
      // 16----------------s:AttributeType A0111
        Element A0111 = eleElement.addElement("s:AttributeType");
        A0111.addAttribute("name", "A0243");
        A0111.addAttribute("rs:number", "16");
        A0111.addAttribute("rs:nullable", "true");
        A0111.addAttribute("rs:write", "true");
        A0111.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0111.addAttribute("rs:baseschema", "DBO");
        A0111.addAttribute("rs:basetable", "A02");
        A0111.addAttribute("rs:basecolumn", "A0243");
        //s:datatype
        Element A0111datatype = A0111.addElement("s:datatype");
        A0111datatype.addAttribute("dt:type", "string");
        A0111datatype.addAttribute("rs:dbtype", "str");
        A0111datatype.addAttribute("dt:maxLength", "8"); 
     // 17----------------s:AttributeType A0111A
        Element A0111A = eleElement.addElement("s:AttributeType");
        A0111A.addAttribute("name", "A0247");
        A0111A.addAttribute("rs:number", "17");
        A0111A.addAttribute("rs:nullable", "true");
        A0111A.addAttribute("rs:write", "true");
        A0111A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0111A.addAttribute("rs:baseschema", "DBO");
        A0111A.addAttribute("rs:basetable", "A02");
        A0111A.addAttribute("rs:basecolumn", "A0247");
        //s:datatype
        Element A0111Adatatype = A0111A.addElement("s:datatype");
        A0111Adatatype.addAttribute("dt:type", "string");
        A0111Adatatype.addAttribute("rs:dbtype", "str");
        A0111Adatatype.addAttribute("dt:maxLength", "8");
        // 18----------------s:AttributeType A0114
        Element A0114 = eleElement.addElement("s:AttributeType");
        A0114.addAttribute("name", "A0245");
        A0114.addAttribute("rs:number", "18");
        A0114.addAttribute("rs:nullable", "true");
        A0114.addAttribute("rs:write", "true");
        A0114.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0114.addAttribute("rs:baseschema", "DBO");
        A0114.addAttribute("rs:basetable", "A02");
        A0114.addAttribute("rs:basecolumn", "A0245");
        //s:datatype
        Element A0114datatype = A0114.addElement("s:datatype");
        A0114datatype.addAttribute("dt:type", "string");
        A0114datatype.addAttribute("rs:dbtype", "str");
        A0114datatype.addAttribute("dt:maxLength", "260"); 
     // 19----------------s:AttributeType A0114A
        Element A0114A = eleElement.addElement("s:AttributeType");
        A0114A.addAttribute("name", "A0251");
        A0114A.addAttribute("rs:number", "19");
        A0114A.addAttribute("rs:nullable", "true");
        A0114A.addAttribute("rs:write", "true");
        A0114A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0114A.addAttribute("rs:baseschema", "DBO");
        A0114A.addAttribute("rs:basetable", "A02");
        A0114A.addAttribute("rs:basecolumn", "A0251");
        //s:datatype
        Element A0114Adatatype = A0114A.addElement("s:datatype");
        A0114Adatatype.addAttribute("dt:type", "string");
        A0114Adatatype.addAttribute("rs:dbtype", "str");
        A0114Adatatype.addAttribute("dt:maxLength", "8");
     // 20----------------s:AttributeType A0117
        Element A0117 = eleElement.addElement("s:AttributeType");
        A0117.addAttribute("name", "A0256A");
        A0117.addAttribute("rs:number", "20");
        A0117.addAttribute("rs:nullable", "true");
        A0117.addAttribute("rs:write", "true");
        A0117.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0117.addAttribute("rs:baseschema", "DBO");
        A0117.addAttribute("rs:basetable", "A02");
        A0117.addAttribute("rs:basecolumn", "A0256A");
        //s:datatype
        Element A0117datatype = A0117.addElement("s:datatype");
        A0117datatype.addAttribute("dt:type", "string");
        A0117datatype.addAttribute("rs:dbtype", "str");
        A0117datatype.addAttribute("dt:maxLength", "8"); 
     // 21----------------s:AttributeType A0128B
        Element A0128B = eleElement.addElement("s:AttributeType");
        A0128B.addAttribute("name", "A0256B");
        A0128B.addAttribute("rs:number", "21");
        A0128B.addAttribute("rs:nullable", "true");
        A0128B.addAttribute("rs:write", "true");
        A0128B.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0128B.addAttribute("rs:baseschema", "DBO");
        A0128B.addAttribute("rs:basetable", "A02");
        A0128B.addAttribute("rs:basecolumn", "A0256B");
        //s:datatype
        Element A0128Bdatatype = A0128B.addElement("s:datatype");
        A0128Bdatatype.addAttribute("dt:type", "int");
        A0128Bdatatype.addAttribute("dt:maxLength", "4");
        A0128Bdatatype.addAttribute("rs:precision", "10");
        A0128Bdatatype.addAttribute("rs:fixedlength", "true");
        
     // 22----------------s:AttributeType A0134
        Element A0134 = eleElement.addElement("s:AttributeType");
        A0134.addAttribute("name", "A0256C");
        A0134.addAttribute("rs:number", "22");
        A0134.addAttribute("rs:nullable", "true");
        A0134.addAttribute("rs:write", "true");
        A0134.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0134.addAttribute("rs:baseschema", "DBO");
        A0134.addAttribute("rs:basetable", "A02");
        A0134.addAttribute("rs:basecolumn", "A0256C");
        //s:datatype
        Element A0134datatype = A0134.addElement("s:datatype");
        A0134datatype.addAttribute("dt:type", "int");
        A0134datatype.addAttribute("dt:maxLength", "4");
        A0134datatype.addAttribute("rs:precision", "10");
        A0134datatype.addAttribute("rs:fixedlength", "true");
        // 23----------------s:AttributeType A0141D
        Element A0141D = eleElement.addElement("s:AttributeType");
        A0141D.addAttribute("name", "A0256");
        A0141D.addAttribute("rs:number", "23");
        A0141D.addAttribute("rs:nullable", "true");
        A0141D.addAttribute("rs:write", "true");
        A0141D.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0141D.addAttribute("rs:baseschema", "DBO");
        A0141D.addAttribute("rs:basetable", "A02");
        A0141D.addAttribute("rs:basecolumn", "A0256");
        //s:datatype
        Element A0141Ddatatype = A0141D.addElement("s:datatype");
        A0141Ddatatype.addAttribute("dt:type", "string");
        A0141Ddatatype.addAttribute("rs:dbtype", "str");
        A0141Ddatatype.addAttribute("dt:maxLength", "8");   
     // 24----------------s:AttributeType A0141
        Element A0141 = eleElement.addElement("s:AttributeType");
        A0141.addAttribute("name", "A0265");
        A0141.addAttribute("rs:number", "24");
        A0141.addAttribute("rs:nullable", "true");
        A0141.addAttribute("rs:write", "true");
        A0141.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0141.addAttribute("rs:baseschema", "DBO");
        A0141.addAttribute("rs:basetable", "A02");
        A0141.addAttribute("rs:basecolumn", "A0265");
        //s:datatype
        Element A0141datatype = A0141.addElement("s:datatype");
        A0141datatype.addAttribute("dt:type", "string");
        A0141datatype.addAttribute("rs:dbtype", "str");
        A0141datatype.addAttribute("dt:maxLength", "8");
        // 25----------------s:AttributeType A3921
        Element A3921 = eleElement.addElement("s:AttributeType");
        A3921.addAttribute("name", "A0267");
        A3921.addAttribute("rs:number", "25");
        A3921.addAttribute("rs:nullable", "true");
        A3921.addAttribute("rs:write", "true");
        A3921.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3921.addAttribute("rs:baseschema", "DBO");
        A3921.addAttribute("rs:basetable", "A02");
        A3921.addAttribute("rs:basecolumn", "A0267");
        //s:datatype
        Element A3921datatype = A3921.addElement("s:datatype");
        A3921datatype.addAttribute("dt:type", "string");
        A3921datatype.addAttribute("rs:dbtype", "str");
        A3921datatype.addAttribute("dt:maxLength", "24");
        // 26----------------s:AttributeType A3927
        Element A3927 = eleElement.addElement("s:AttributeType");
        A3927.addAttribute("name", "A0222");
        A3927.addAttribute("rs:number", "26");
        A3927.addAttribute("rs:nullable", "true");
        A3927.addAttribute("rs:write", "true");
        A3927.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3927.addAttribute("rs:baseschema", "DBO");
        A3927.addAttribute("rs:basetable", "A02");
        A3927.addAttribute("rs:basecolumn", "A0222");
        //s:datatype
        Element A3927datatype = A3927.addElement("s:datatype");
        A3927datatype.addAttribute("dt:type", "string");
        A3927datatype.addAttribute("rs:dbtype", "str");
        A3927datatype.addAttribute("dt:maxLength", "20");
        // 27----------------s:AttributeType A0144
        Element A0144 = eleElement.addElement("s:AttributeType");
        A0144.addAttribute("name", "A0271");
        A0144.addAttribute("rs:number", "27");
        A0144.addAttribute("rs:nullable", "true");
        A0144.addAttribute("rs:write", "true");
        A0144.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0144.addAttribute("rs:baseschema", "DBO");
        A0144.addAttribute("rs:basetable", "A02");
        A0144.addAttribute("rs:basecolumn", "A0271");
        //s:datatype
        Element A0144datatype = A0144.addElement("s:datatype");
        A0144datatype.addAttribute("dt:type", "string");
        A0144datatype.addAttribute("rs:dbtype", "str");
        A0144datatype.addAttribute("dt:maxLength", "8");
        // 28----------------s:AttributeType A0144B
        Element A0144B = eleElement.addElement("s:AttributeType");
        A0144B.addAttribute("name", "A0255");
        A0144B.addAttribute("rs:number", "28");
        A0144B.addAttribute("rs:nullable", "true");
        A0144B.addAttribute("rs:write", "true");
        A0144B.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0144B.addAttribute("rs:baseschema", "DBO");
        A0144B.addAttribute("rs:basetable", "A02");
        A0144B.addAttribute("rs:basecolumn", "A0255");
        //s:datatype
        Element A0144Bdatatype = A0144B.addElement("s:datatype");
        A0144Bdatatype.addAttribute("dt:type", "string");
        A0144Bdatatype.addAttribute("rs:dbtype", "str");
        A0144Bdatatype.addAttribute("dt:maxLength", "1");
        // 29----------------s:AttributeType A0144C
        Element A0144C = eleElement.addElement("s:AttributeType");
        A0144C.addAttribute("name", "A0284");
        A0144C.addAttribute("rs:number", "29");
        A0144C.addAttribute("rs:nullable", "true");
        A0144C.addAttribute("rs:write", "true");
        A0144C.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0144C.addAttribute("rs:baseschema", "DBO");
        A0144C.addAttribute("rs:basetable", "A02");
        A0144C.addAttribute("rs:basecolumn", "A0284");
        //s:datatype
        Element A0144Cdatatype = A0144C.addElement("s:datatype");
        A0144Cdatatype.addAttribute("dt:type", "string");
        A0144Cdatatype.addAttribute("rs:dbtype", "str");
        A0144Cdatatype.addAttribute("dt:maxLength", "8");
     // 30----------------s:AttributeType A0151
        Element A0151 = eleElement.addElement("s:AttributeType");
        A0151.addAttribute("name", "A0281");
        A0151.addAttribute("rs:number", "30");
        A0151.addAttribute("rs:nullable", "true");
        A0151.addAttribute("rs:write", "true");
        A0151.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0151.addAttribute("rs:baseschema", "DBO");
        A0151.addAttribute("rs:basetable", "A02");
        A0151.addAttribute("rs:basecolumn", "A0281");
        //s:datatype
        Element A0151datatype = A0151.addElement("s:datatype");
        A0151datatype.addAttribute("dt:type", "string");
        A0151datatype.addAttribute("rs:dbtype", "str");
        A0151datatype.addAttribute("dt:maxLength", "1");
     // 31----------------s:AttributeType A0153
        Element A0153 = eleElement.addElement("s:AttributeType");
        A0153.addAttribute("name", "A0277");
        A0153.addAttribute("rs:number", "31");
        A0153.addAttribute("rs:nullable", "true");
        A0153.addAttribute("rs:write", "true");
        A0153.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0153.addAttribute("rs:baseschema", "DBO");
        A0153.addAttribute("rs:basetable", "A02");
        A0153.addAttribute("rs:basecolumn", "A0277");
        //s:datatype
        Element A0153datatype = A0153.addElement("s:datatype");
        A0153datatype.addAttribute("dt:type", "string");
        A0153datatype.addAttribute("rs:dbtype", "str");
        A0153datatype.addAttribute("dt:maxLength", "1");
     // 32----------------s:AttributeType A0155
        Element A0155 = eleElement.addElement("s:AttributeType");
        A0155.addAttribute("name", "A0288");
        A0155.addAttribute("rs:number", "32");
        A0155.addAttribute("rs:nullable", "true");
        A0155.addAttribute("rs:write", "true");
        A0155.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0155.addAttribute("rs:baseschema", "DBO");
        A0155.addAttribute("rs:basetable", "A02");
        A0155.addAttribute("rs:basecolumn", "A0288");
        //s:datatype
        Element A0155datatype = A0155.addElement("s:datatype");
        A0155datatype.addAttribute("dt:type", "string");
        A0155datatype.addAttribute("rs:dbtype", "str");
        A0155datatype.addAttribute("dt:maxLength", "8");
     // 33----------------s:AttributeType A0157
        Element A0157 = eleElement.addElement("s:AttributeType");
        A0157.addAttribute("name", "A0289");
        A0157.addAttribute("rs:number", "33");
        A0157.addAttribute("rs:nullable", "true");
        A0157.addAttribute("rs:write", "true");
        A0157.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0157.addAttribute("rs:baseschema", "DBO");
        A0157.addAttribute("rs:basetable", "A02");
        A0157.addAttribute("rs:basecolumn", "A0289");
        //s:datatype
        Element A0157datatype = A0157.addElement("s:datatype");
        A0157datatype.addAttribute("dt:type", "string");
        A0157datatype.addAttribute("rs:dbtype", "str");
        A0157datatype.addAttribute("dt:maxLength", "8");
        // 34----------------s:AttributeType A0158
        Element A0158 = eleElement.addElement("s:AttributeType");
        A0158.addAttribute("name", "A4904");
        A0158.addAttribute("rs:number", "34");
        A0158.addAttribute("rs:nullable", "true");
        A0158.addAttribute("rs:write", "true");
        A0158.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0158.addAttribute("rs:baseschema", "DBO");
        A0158.addAttribute("rs:basetable", "A02");
        A0158.addAttribute("rs:basecolumn", "A4904");
        //s:datatype
        Element A0158datatype = A0158.addElement("s:datatype");
        A0158datatype.addAttribute("dt:type", "string");
        A0158datatype.addAttribute("rs:dbtype", "str");
        A0158datatype.addAttribute("dt:maxLength", "8");
     // 35----------------s:AttributeType A0160
        Element A0160 = eleElement.addElement("s:AttributeType");
        A0160.addAttribute("name", "A4901");
        A0160.addAttribute("rs:number", "35");
        A0160.addAttribute("rs:nullable", "true");
        A0160.addAttribute("rs:write", "true");
        A0160.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0160.addAttribute("rs:baseschema", "DBO");
        A0160.addAttribute("rs:basetable", "A02");
        A0160.addAttribute("rs:basecolumn", "A4901");
        //s:datatype
        Element A0160datatype = A0160.addElement("s:datatype");
        A0160datatype.addAttribute("dt:type", "string");
        A0160datatype.addAttribute("rs:dbtype", "str");
        A0160datatype.addAttribute("dt:maxLength", "8");
     // 36----------------s:AttributeType A0163
        Element A0163 = eleElement.addElement("s:AttributeType");
        A0163.addAttribute("name", "A4907");
        A0163.addAttribute("rs:number", "36");
        A0163.addAttribute("rs:nullable", "true");
        A0163.addAttribute("rs:write", "true");
        A0163.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0163.addAttribute("rs:baseschema", "DBO");
        A0163.addAttribute("rs:basetable", "A02");
        A0163.addAttribute("rs:basecolumn", "A4907");
        //s:datatype
        Element A0163datatype = A0163.addElement("s:datatype");
        A0163datatype.addAttribute("dt:type", "string");
        A0163datatype.addAttribute("rs:dbtype", "str");
        A0163datatype.addAttribute("dt:maxLength", "8");
     // 37----------------s:AttributeType A0184
        Element A0184 = eleElement.addElement("s:AttributeType");
        A0184.addAttribute("name", "A0259");
        A0184.addAttribute("rs:number", "37");
        A0184.addAttribute("rs:nullable", "true");
        A0184.addAttribute("rs:write", "true");
        A0184.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0184.addAttribute("rs:baseschema", "DBO");
        A0184.addAttribute("rs:basetable", "A02");
        A0184.addAttribute("rs:basecolumn", "A0259");
        //s:datatype
        Element A0184datatype = A0184.addElement("s:datatype");
        A0184datatype.addAttribute("dt:type", "string");
        A0184datatype.addAttribute("rs:dbtype", "str");
        A0184datatype.addAttribute("dt:maxLength", "8");
     // 38----------------s:AttributeType A0193
        Element A0193 = eleElement.addElement("s:AttributeType");
        A0193.addAttribute("name", "A0295");
        A0193.addAttribute("rs:number", "38");
        A0193.addAttribute("rs:nullable", "true");
        A0193.addAttribute("rs:write", "true");
        A0193.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0193.addAttribute("rs:baseschema", "DBO");
        A0193.addAttribute("rs:basetable", "A02");
        A0193.addAttribute("rs:basecolumn", "A0295");
        //s:datatype
        Element A0193datatype = A0193.addElement("s:datatype");
        A0193datatype.addAttribute("dt:type", "string");
        A0193datatype.addAttribute("rs:dbtype", "str");
        A0193datatype.addAttribute("dt:maxLength", "8");
     // 39----------------s:AttributeType A0148C
        Element A0148C = eleElement.addElement("s:AttributeType");
        A0148C.addAttribute("name", "A0201D");
        A0148C.addAttribute("rs:number", "39");
        A0148C.addAttribute("rs:nullable", "true");
        A0148C.addAttribute("rs:write", "true");
        A0148C.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0148C.addAttribute("rs:baseschema", "DBO");
        A0148C.addAttribute("rs:basetable", "A02");
        A0148C.addAttribute("rs:basecolumn", "A0201D");
        //s:datatype
        Element A0148Cdatatype = A0148C.addElement("s:datatype");
        A0148Cdatatype.addAttribute("dt:type", "string");
        A0148Cdatatype.addAttribute("rs:dbtype", "str");
        A0148Cdatatype.addAttribute("dt:maxLength", "1");
     // 40----------------s:AttributeType A0195
        Element A0195 = eleElement.addElement("s:AttributeType");
        A0195.addAttribute("name", "A0201E");
        A0195.addAttribute("rs:number", "40");
        A0195.addAttribute("rs:nullable", "true");
        A0195.addAttribute("rs:write", "true");
        A0195.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0195.addAttribute("rs:baseschema", "DBO");
        A0195.addAttribute("rs:basetable", "A02");
        A0195.addAttribute("rs:basecolumn", "A0201E");
        //s:datatype
        Element A0195datatype = A0195.addElement("s:datatype");
        A0195datatype.addAttribute("dt:type", "string");
        A0195datatype.addAttribute("rs:dbtype", "str");
        A0195datatype.addAttribute("dt:maxLength", "8");
     // 41----------------s:AttributeType A0192
        Element A0192 = eleElement.addElement("s:AttributeType");
        A0192.addAttribute("name", "A0251B");
        A0192.addAttribute("rs:number", "41");
        A0192.addAttribute("rs:nullable", "true");
        A0192.addAttribute("rs:write", "true");
        A0192.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0192.addAttribute("rs:baseschema", "DBO");
        A0192.addAttribute("rs:basetable", "A02");
        A0192.addAttribute("rs:basecolumn", "A0251B");
        //s:datatype
        Element A0192datatype = A0192.addElement("s:datatype");
        A0192datatype.addAttribute("dt:type", "string");
        A0192datatype.addAttribute("rs:dbtype", "str");
        A0192datatype.addAttribute("dt:maxLength", "1");
        // 42----------------s:AttributeType A01K01
        Element A01K01 = eleElement.addElement("s:AttributeType");
        A01K01.addAttribute("name", "A0204");
        A01K01.addAttribute("rs:number", "42");
        A01K01.addAttribute("rs:nullable", "true");
        A01K01.addAttribute("rs:write", "true");
        A01K01.addAttribute("rs:basecatalog", "OFFICIALV2");
        A01K01.addAttribute("rs:baseschema", "DBO");
        A01K01.addAttribute("rs:basetable", "A02");
        A01K01.addAttribute("rs:basecolumn", "A0204");
        //s:datatype
        Element A01K01datatype = A01K01.addElement("s:datatype");
        A01K01datatype.addAttribute("dt:type", "string");
        A01K01datatype.addAttribute("rs:dbtype", "str");
        A01K01datatype.addAttribute("dt:maxLength", "68");
     // 43----------------s:AttributeType A0196
        Element A0196 = eleElement.addElement("s:AttributeType");
        A0196.addAttribute("name", "A0219W");
        A0196.addAttribute("rs:number", "43");
        A0196.addAttribute("rs:nullable", "true");
        A0196.addAttribute("rs:write", "true");
        A0196.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0196.addAttribute("rs:baseschema", "DBO");
        A0196.addAttribute("rs:basetable", "A02");
        A0196.addAttribute("rs:basecolumn", "A0219W");
        //s:datatype
        Element A0196datatype = A0196.addElement("s:datatype");
        A0196datatype.addAttribute("dt:type", "string");
        A0196datatype.addAttribute("rs:dbtype", "str");
        A0196datatype.addAttribute("dt:maxLength", "8");
        // 44----------------s:AttributeType A0187A
        Element A0187A = eleElement.addElement("s:AttributeType");
        A0187A.addAttribute("name", "A0221W");
        A0187A.addAttribute("rs:number", "44");
        A0187A.addAttribute("rs:nullable", "true");
        A0187A.addAttribute("rs:write", "true");
        A0187A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0187A.addAttribute("rs:baseschema", "DBO");
        A0187A.addAttribute("rs:basetable", "A02");
        A0187A.addAttribute("rs:basecolumn", "A0221W");
        //s:datatype
        Element A0187Adatatype = A0187A.addElement("s:datatype");
        A0187Adatatype.addAttribute("dt:type", "string");
        A0187Adatatype.addAttribute("rs:dbtype", "str");
        A0187Adatatype.addAttribute("dt:maxLength", "8");
     // 45----------------s:AttributeType A0199
        Element A0199 = eleElement.addElement("s:AttributeType");
        A0199.addAttribute("name", "A0299");
        A0199.addAttribute("rs:number", "45");
        A0199.addAttribute("rs:nullable", "true");
        A0199.addAttribute("rs:write", "true");
        A0199.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0199.addAttribute("rs:baseschema", "DBO");
        A0199.addAttribute("rs:basetable", "A02");
        A0199.addAttribute("rs:basecolumn", "A0299");
        //s:datatype
        Element A0199datatype = A0199.addElement("s:datatype");
        A0199datatype.addAttribute("dt:type", "string");
        A0199datatype.addAttribute("rs:dbtype", "str");
        A0199datatype.addAttribute("dt:maxLength", "8");
     // 46----------------s:AttributeType A0161
        Element A0161 = eleElement.addElement("s:AttributeType");
        A0161.addAttribute("name", "A0225");
        A0161.addAttribute("rs:number", "46");
        A0161.addAttribute("rs:nullable", "true");
        A0161.addAttribute("rs:write", "true");
        A0161.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0161.addAttribute("rs:baseschema", "DBO");
        A0161.addAttribute("rs:basetable", "A02");
        A0161.addAttribute("rs:basecolumn", "A0225");
        //s:datatype
        Element A0161datatype = A0161.addElement("s:datatype");
        A0161datatype.addAttribute("dt:type", "int");
        A0161datatype.addAttribute("dt:maxLength", "4");
        A0161datatype.addAttribute("rs:precision", "10");
        A0161datatype.addAttribute("rs:fixedlength", "true");
     
        /**
         * 加载数据 
         */
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A02 a01 = (A02) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A0223", emptoString(map.get("A0223")));
				zrow.addAttribute("A0201C", emptoString(map.get("A0201C")));
				zrow.addAttribute("A0200", emptoString(map.get("A0200")));
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A0201B", emptoString(map.get("A0201B")));
				zrow.addAttribute("A0201A", emptoString(map.get("A0201A")));
				zrow.addAttribute("A0215A", emptoString(map.get("A0215A")));
				zrow.addAttribute("A0215B", emptoString(map.get("A0215B")));
				zrow.addAttribute("A0219", emptoString(map.get("A0219")));
				zrow.addAttribute("A0221", emptoString(map.get("A0221")));
				
				zrow.addAttribute("A0229", emptoString(map.get("A0229")));
				zrow.addAttribute("A0243", emptoString(map.get("A0243")));
				zrow.addAttribute("A0247", emptoString(map.get("A0247")));
				zrow.addAttribute("A0245", emptoString(map.get("A0245")));
				zrow.addAttribute("A0251", emptoString(map.get("A0251")));
				zrow.addAttribute("A0256A", emptoString(map.get("A0256A")));
				zrow.addAttribute("A0256", emptoString(map.get("A0256")));
				zrow.addAttribute("A0265", emptoString(map.get("A0265")));
				zrow.addAttribute("A0267", emptoString(map.get("A0267")));
				zrow.addAttribute("A0222", emptoString(map.get("A0222")));
				
				zrow.addAttribute("A0271", emptoString(map.get("A0271")));
				zrow.addAttribute("A0255", emptoString(map.get("A0255")));
				zrow.addAttribute("A0284", emptoString(map.get("A0284")));
				zrow.addAttribute("A0281", emptoString(map.get("A0281")).equals("true")?"1":"0");
				zrow.addAttribute("A0288", emptoString(map.get("A0288")));
				zrow.addAttribute("A4904", emptoString(map.get("A4904")));
				zrow.addAttribute("A4901", emptoString(map.get("A4901")));
				zrow.addAttribute("A4907", emptoString(map.get("A4907")));
				zrow.addAttribute("A0295", emptoString(map.get("A0295")));
				zrow.addAttribute("A0201D", emptoString(map.get("A0201D")));
				
				zrow.addAttribute("A0201E", emptoString(map.get("A0201E")));
				zrow.addAttribute("A0251B", emptoString(map.get("A0251B")));
				zrow.addAttribute("A0204", emptoString(map.get("A0204")));
				zrow.addAttribute("A0225", emptoString(map.get("A0225")));
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
        
        XMLWriter out = null;
        
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        
        try {
            File xmlFile = new File(uuid + "Table/" +"A02.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private static void A01toXml(List list, String uuid) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element xmlElement = document.addElement("xml");
        //给xml元素命名空间 
        xmlElement.addNamespace("s","uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882"); 
        xmlElement.addNamespace("dt","uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"); 
        xmlElement.addNamespace("rs","urn:schemas-microsoft-com:rowset"); 
        xmlElement.addNamespace("z","#RowsetSchema"); 
        //添加元素 s:Schema
        Element SchemaElement = xmlElement.addElement("s:Schema");
        SchemaElement.addAttribute("id", "RowsetSchema");
        //添加Us:ElementType
        Element eleElement = SchemaElement.addElement("s:ElementType");
        eleElement.addAttribute("name", "row");
        eleElement.addAttribute("content", "eltOnly");
        eleElement.addAttribute("rs:updatable", "true");
     //1-----------------s:AttributeType A0191
        Element A0191 = eleElement.addElement("s:AttributeType");
        A0191.addAttribute("name", "A0191");
        A0191.addAttribute("rs:number", "1");
        A0191.addAttribute("rs:nullable", "true");
        A0191.addAttribute("rs:write", "true");
        A0191.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0191.addAttribute("rs:baseschema", "DBO");
        A0191.addAttribute("rs:basetable", "A01");
        A0191.addAttribute("rs:basecolumn", "A0191");
        //s:datatype
        Element A0191datatype = A0191.addElement("s:datatype");
        A0191datatype.addAttribute("dt:type", "string");
        A0191datatype.addAttribute("rs:dbtype", "str");
        A0191datatype.addAttribute("dt:maxLength", "1");
     // 2----------------s:AttributeType A0128
        Element A0128 = eleElement.addElement("s:AttributeType");
        A0128.addAttribute("name", "A0128");
        A0128.addAttribute("rs:number", "2");
        A0128.addAttribute("rs:nullable", "true");
        A0128.addAttribute("rs:write", "true");
        A0128.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0128.addAttribute("rs:baseschema", "DBO");
        A0128.addAttribute("rs:basetable", "A01");
        A0128.addAttribute("rs:basecolumn", "A0128");
        //s:datatype
        Element A0128datatype = A0128.addElement("s:datatype");
        A0128datatype.addAttribute("dt:type", "string");
        A0128datatype.addAttribute("rs:dbtype", "str");
        A0128datatype.addAttribute("dt:maxLength", "120");
     // 3----------------s:AttributeType ZZXWXX
        Element ZZXWXX = eleElement.addElement("s:AttributeType");
        ZZXWXX.addAttribute("name", "ZZXWXX");
        ZZXWXX.addAttribute("rs:number", "3");
        ZZXWXX.addAttribute("rs:nullable", "true");
        ZZXWXX.addAttribute("rs:write", "true");
        ZZXWXX.addAttribute("rs:basecatalog", "OFFICIALV2");
        ZZXWXX.addAttribute("rs:baseschema", "DBO");
        ZZXWXX.addAttribute("rs:basetable", "A01");
        ZZXWXX.addAttribute("rs:basecolumn", "ZZXWXX");
        //s:datatype
        Element ZZXWXXdatatype = ZZXWXX.addElement("s:datatype");
        ZZXWXXdatatype.addAttribute("dt:type", "string");
        ZZXWXXdatatype.addAttribute("rs:dbtype", "str");
        ZZXWXXdatatype.addAttribute("dt:maxLength", "120");
      // 4----------------s:AttributeType ZZXLXX
        Element ZZXLXX = eleElement.addElement("s:AttributeType");
        ZZXLXX.addAttribute("name", "ZZXLXX");
        ZZXLXX.addAttribute("rs:number", "4");
        ZZXLXX.addAttribute("rs:nullable", "true");
        ZZXLXX.addAttribute("rs:write", "true");
        ZZXLXX.addAttribute("rs:basecatalog", "OFFICIALV2");
        ZZXLXX.addAttribute("rs:baseschema", "DBO");
        ZZXLXX.addAttribute("rs:basetable", "A01");
        ZZXLXX.addAttribute("rs:basecolumn", "ZZXLXX");
        //s:datatype
        Element ZZXLXXdatatype = ZZXLXX.addElement("s:datatype");
        ZZXLXXdatatype.addAttribute("dt:type", "string");
        ZZXLXXdatatype.addAttribute("rs:dbtype", "str");
        ZZXLXXdatatype.addAttribute("dt:maxLength", "120");
     // 5----------------s:AttributeType QRZXWXX
        Element QRZXWXX = eleElement.addElement("s:AttributeType");
        QRZXWXX.addAttribute("name", "QRZXWXX");
        QRZXWXX.addAttribute("rs:number", "5");
        QRZXWXX.addAttribute("rs:nullable", "true");
        QRZXWXX.addAttribute("rs:write", "true");
        QRZXWXX.addAttribute("rs:basecatalog", "OFFICIALV2");
        QRZXWXX.addAttribute("rs:baseschema", "DBO");
        QRZXWXX.addAttribute("rs:basetable", "A01");
        QRZXWXX.addAttribute("rs:basecolumn", "QRZXWXX");
        //s:datatype
        Element QRZXWXXdatatype = QRZXWXX.addElement("s:datatype");
        QRZXWXXdatatype.addAttribute("dt:type", "string");
        QRZXWXXdatatype.addAttribute("rs:dbtype", "str");
        QRZXWXXdatatype.addAttribute("dt:maxLength", "120");
     // 6----------------s:AttributeType QRZXLXX
        Element QRZXLXX = eleElement.addElement("s:AttributeType");
        QRZXLXX.addAttribute("name", "QRZXLXX");
        QRZXLXX.addAttribute("rs:number", "6");
        QRZXLXX.addAttribute("rs:nullable", "true");
        QRZXLXX.addAttribute("rs:write", "true");
        QRZXLXX.addAttribute("rs:basecatalog", "OFFICIALV2");
        QRZXLXX.addAttribute("rs:baseschema", "DBO");
        QRZXLXX.addAttribute("rs:basetable", "A01");
        QRZXLXX.addAttribute("rs:basecolumn", "QRZXLXX");
        //s:datatype
        Element QRZXLXXdatatype = QRZXLXX.addElement("s:datatype");
        QRZXLXXdatatype.addAttribute("dt:type", "string");
        QRZXLXXdatatype.addAttribute("rs:dbtype", "str");
        QRZXLXXdatatype.addAttribute("dt:maxLength", "120");
     // 7----------------s:AttributeType A0192A
        Element A0192A = eleElement.addElement("s:AttributeType");
        A0192A.addAttribute("name", "A0192A");
        A0192A.addAttribute("rs:number", "7");
        A0192A.addAttribute("rs:nullable", "true");
        A0192A.addAttribute("rs:write", "true");
        A0192A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0192A.addAttribute("rs:baseschema", "DBO");
        A0192A.addAttribute("rs:basetable", "A01");
        A0192A.addAttribute("rs:basecolumn", "A0192A");
        //s:datatype
        Element A0192Adatatype = A0192A.addElement("s:datatype");
        A0192Adatatype.addAttribute("dt:type", "string");
        A0192Adatatype.addAttribute("rs:dbtype", "str");
        A0192Adatatype.addAttribute("dt:maxLength", "2000");
     // 8----------------s:AttributeType A015A
        Element A015A = eleElement.addElement("s:AttributeType");
        A015A.addAttribute("name", "A015A");
        A015A.addAttribute("rs:number", "8");
        A015A.addAttribute("rs:nullable", "true");
        A015A.addAttribute("rs:write", "true");
        A015A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A015A.addAttribute("rs:baseschema", "DBO");
        A015A.addAttribute("rs:basetable", "A01");
        A015A.addAttribute("rs:basecolumn", "A015A");
        //s:datatype
        Element A015Adatatype = A015A.addElement("s:datatype");
        A015Adatatype.addAttribute("dt:type", "string");
        A015Adatatype.addAttribute("rs:dbtype", "str");
        A015Adatatype.addAttribute("dt:maxLength", "12");
     // 9----------------s:AttributeType A0192B
        Element A0192B = eleElement.addElement("s:AttributeType");
        A0192B.addAttribute("name", "A0192B");
        A0192B.addAttribute("rs:number", "9");
        A0192B.addAttribute("rs:nullable", "true");
        A0192B.addAttribute("rs:write", "true");
        A0192B.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0192B.addAttribute("rs:baseschema", "DBO");
        A0192B.addAttribute("rs:basetable", "A01");
        A0192B.addAttribute("rs:basecolumn", "A0192B");
        //s:datatype
        Element A0192Bdatatype = A0192B.addElement("s:datatype");
        A0192Bdatatype.addAttribute("dt:type", "string");
        A0192Bdatatype.addAttribute("rs:dbtype", "str");
        A0192Bdatatype.addAttribute("dt:maxLength", "2000");
      // 10----------------s:AttributeType A0192B
        Element A0180 = eleElement.addElement("s:AttributeType");
        A0180.addAttribute("name", "A0180");
        A0180.addAttribute("rs:number", "10");
        A0180.addAttribute("rs:nullable", "true");
        A0180.addAttribute("rs:write", "true");
        A0180.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0180.addAttribute("rs:baseschema", "DBO");
        A0180.addAttribute("rs:basetable", "A01");
        A0180.addAttribute("rs:basecolumn", "A0180");
        //s:datatype
        Element A0180datatype = A0180.addElement("s:datatype");
        A0180datatype.addAttribute("dt:type", "string");
        A0180datatype.addAttribute("rs:dbtype", "str");
        A0180datatype.addAttribute("dt:maxLength", "500");
     // 11----------------s:AttributeType A0000
        Element A0000 = eleElement.addElement("s:AttributeType");
        A0000.addAttribute("name", "A0000");
        A0000.addAttribute("rs:number", "11");
        A0000.addAttribute("rs:nullable", "true");
        A0000.addAttribute("rs:write", "true");
        A0000.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000.addAttribute("rs:baseschema", "DBO");
        A0000.addAttribute("rs:basetable", "A01");
        A0000.addAttribute("rs:basecolumn", "A0000");
        //s:datatype
        Element A0000datatype = A0000.addElement("s:datatype");
        A0000datatype.addAttribute("dt:type", "string");
        A0000datatype.addAttribute("rs:dbtype", "str");
        A0000datatype.addAttribute("dt:maxLength", "60");
     // 12----------------s:AttributeType A0101
        Element A0101 = eleElement.addElement("s:AttributeType");
        A0101.addAttribute("name", "A0101");
        A0101.addAttribute("rs:number", "12");
        A0101.addAttribute("rs:nullable", "true");
        A0101.addAttribute("rs:write", "true");
        A0101.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0101.addAttribute("rs:baseschema", "DBO");
        A0101.addAttribute("rs:basetable", "A01");
        A0101.addAttribute("rs:basecolumn", "A0101");
        //s:datatype
        Element A0101datatype = A0101.addElement("s:datatype");
        A0101datatype.addAttribute("dt:type", "string");
        A0101datatype.addAttribute("rs:dbtype", "str");
        A0101datatype.addAttribute("dt:maxLength", "36");
     // 13----------------s:AttributeType A0102
        Element A0102 = eleElement.addElement("s:AttributeType");
        A0102.addAttribute("name", "A0102");
        A0102.addAttribute("rs:number", "13");
        A0102.addAttribute("rs:nullable", "true");
        A0102.addAttribute("rs:write", "true");
        A0102.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0102.addAttribute("rs:baseschema", "DBO");
        A0102.addAttribute("rs:basetable", "A01");
        A0102.addAttribute("rs:basecolumn", "A0102");
        //s:datatype
        Element A0102datatype = A0102.addElement("s:datatype");
        A0102datatype.addAttribute("dt:type", "string");
        A0102datatype.addAttribute("rs:dbtype", "str");
        A0102datatype.addAttribute("dt:maxLength", "36");
     // 14----------------s:AttributeType A0104
        Element A0104 = eleElement.addElement("s:AttributeType");
        A0104.addAttribute("name", "A0104");
        A0104.addAttribute("rs:number", "14");
        A0104.addAttribute("rs:nullable", "true");
        A0104.addAttribute("rs:write", "true");
        A0104.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0104.addAttribute("rs:baseschema", "DBO");
        A0104.addAttribute("rs:basetable", "A01");
        A0104.addAttribute("rs:basecolumn", "A0104");
        //s:datatype
        Element A0104datatype = A0104.addElement("s:datatype");
        A0104datatype.addAttribute("dt:type", "string");
        A0104datatype.addAttribute("rs:dbtype", "str");
        A0104datatype.addAttribute("dt:maxLength", "8");
     // 15----------------s:AttributeType A0107
        Element A0107 = eleElement.addElement("s:AttributeType");
        A0107.addAttribute("name", "A0107");
        A0107.addAttribute("rs:number", "15");
        A0107.addAttribute("rs:nullable", "true");
        A0107.addAttribute("rs:write", "true");
        A0107.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0107.addAttribute("rs:baseschema", "DBO");
        A0107.addAttribute("rs:basetable", "A01");
        A0107.addAttribute("rs:basecolumn", "A0107");
        //s:datatype
        Element A0107datatype = A0107.addElement("s:datatype");
        A0107datatype.addAttribute("dt:type", "string");
        A0107datatype.addAttribute("rs:dbtype", "str");
        A0107datatype.addAttribute("dt:maxLength", "8");
      // 16----------------s:AttributeType A0111
        Element A0111 = eleElement.addElement("s:AttributeType");
        A0111.addAttribute("name", "A0111");
        A0111.addAttribute("rs:number", "16");
        A0111.addAttribute("rs:nullable", "true");
        A0111.addAttribute("rs:write", "true");
        A0111.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0111.addAttribute("rs:baseschema", "DBO");
        A0111.addAttribute("rs:basetable", "A01");
        A0111.addAttribute("rs:basecolumn", "A0111");
        //s:datatype
        Element A0111datatype = A0111.addElement("s:datatype");
        A0111datatype.addAttribute("dt:type", "string");
        A0111datatype.addAttribute("rs:dbtype", "str");
        A0111datatype.addAttribute("dt:maxLength", "8"); 
     // 17----------------s:AttributeType A0111A
        Element A0111A = eleElement.addElement("s:AttributeType");
        A0111A.addAttribute("name", "A0111A");
        A0111A.addAttribute("rs:number", "17");
        A0111A.addAttribute("rs:nullable", "true");
        A0111A.addAttribute("rs:write", "true");
        A0111A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0111A.addAttribute("rs:baseschema", "DBO");
        A0111A.addAttribute("rs:basetable", "A01");
        A0111A.addAttribute("rs:basecolumn", "A0111A");
        //s:datatype
        Element A0111Adatatype = A0111A.addElement("s:datatype");
        A0111Adatatype.addAttribute("dt:type", "string");
        A0111Adatatype.addAttribute("rs:dbtype", "str");
        A0111Adatatype.addAttribute("dt:maxLength", "200");
        // 18----------------s:AttributeType A0114
        Element A0114 = eleElement.addElement("s:AttributeType");
        A0114.addAttribute("name", "A0114");
        A0114.addAttribute("rs:number", "18");
        A0114.addAttribute("rs:nullable", "true");
        A0114.addAttribute("rs:write", "true");
        A0114.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0114.addAttribute("rs:baseschema", "DBO");
        A0114.addAttribute("rs:basetable", "A01");
        A0114.addAttribute("rs:basecolumn", "A0114");
        //s:datatype
        Element A0114datatype = A0114.addElement("s:datatype");
        A0114datatype.addAttribute("dt:type", "string");
        A0114datatype.addAttribute("rs:dbtype", "str");
        A0114datatype.addAttribute("dt:maxLength", "8"); 
     // 19----------------s:AttributeType A0114A
        Element A0114A = eleElement.addElement("s:AttributeType");
        A0114A.addAttribute("name", "A0114A");
        A0114A.addAttribute("rs:number", "19");
        A0114A.addAttribute("rs:nullable", "true");
        A0114A.addAttribute("rs:write", "true");
        A0114A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0114A.addAttribute("rs:baseschema", "DBO");
        A0114A.addAttribute("rs:basetable", "A01");
        A0114A.addAttribute("rs:basecolumn", "A0114A");
        //s:datatype
        Element A0114Adatatype = A0114A.addElement("s:datatype");
        A0114Adatatype.addAttribute("dt:type", "string");
        A0114Adatatype.addAttribute("rs:dbtype", "str");
        A0114Adatatype.addAttribute("dt:maxLength", "200");
     // 20----------------s:AttributeType A0117
        Element A0117 = eleElement.addElement("s:AttributeType");
        A0117.addAttribute("name", "A0117");
        A0117.addAttribute("rs:number", "20");
        A0117.addAttribute("rs:nullable", "true");
        A0117.addAttribute("rs:write", "true");
        A0117.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0117.addAttribute("rs:baseschema", "DBO");
        A0117.addAttribute("rs:basetable", "A01");
        A0117.addAttribute("rs:basecolumn", "A0117");
        //s:datatype
        Element A0117datatype = A0117.addElement("s:datatype");
        A0117datatype.addAttribute("dt:type", "string");
        A0117datatype.addAttribute("rs:dbtype", "str");
        A0117datatype.addAttribute("dt:maxLength", "8"); 
     // 21----------------s:AttributeType A0128B
        Element A0128B = eleElement.addElement("s:AttributeType");
        A0128B.addAttribute("name", "A0128B");
        A0128B.addAttribute("rs:number", "21");
        A0128B.addAttribute("rs:nullable", "true");
        A0128B.addAttribute("rs:write", "true");
        A0128B.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0128B.addAttribute("rs:baseschema", "DBO");
        A0128B.addAttribute("rs:basetable", "A01");
        A0128B.addAttribute("rs:basecolumn", "A0128B");
        //s:datatype
        Element A0128Bdatatype = A0128B.addElement("s:datatype");
        A0128Bdatatype.addAttribute("dt:type", "string");
        A0128Bdatatype.addAttribute("rs:dbtype", "str");
        A0128Bdatatype.addAttribute("dt:maxLength", "120");
     // 22----------------s:AttributeType A0134
        Element A0134 = eleElement.addElement("s:AttributeType");
        A0134.addAttribute("name", "A0134");
        A0134.addAttribute("rs:number", "22");
        A0134.addAttribute("rs:nullable", "true");
        A0134.addAttribute("rs:write", "true");
        A0134.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0134.addAttribute("rs:baseschema", "DBO");
        A0134.addAttribute("rs:basetable", "A01");
        A0134.addAttribute("rs:basecolumn", "A0134");
        //s:datatype
        Element A0134datatype = A0134.addElement("s:datatype");
        A0134datatype.addAttribute("dt:type", "string");
        A0134datatype.addAttribute("rs:dbtype", "str");
        A0134datatype.addAttribute("dt:maxLength", "8"); 
        // 23----------------s:AttributeType A0141D
        Element A0141D = eleElement.addElement("s:AttributeType");
        A0141D.addAttribute("name", "A0141D");
        A0141D.addAttribute("rs:number", "23");
        A0141D.addAttribute("rs:nullable", "true");
        A0141D.addAttribute("rs:write", "true");
        A0141D.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0141D.addAttribute("rs:baseschema", "DBO");
        A0141D.addAttribute("rs:basetable", "A01");
        A0141D.addAttribute("rs:basecolumn", "A0141D");
        //s:datatype
        Element A0141Ddatatype = A0141D.addElement("s:datatype");
        A0141Ddatatype.addAttribute("dt:type", "string");
        A0141Ddatatype.addAttribute("rs:dbtype", "str");
        A0141Ddatatype.addAttribute("dt:maxLength", "100");   
     // 24----------------s:AttributeType A0141
        Element A0141 = eleElement.addElement("s:AttributeType");
        A0141.addAttribute("name", "A0141");
        A0141.addAttribute("rs:number", "24");
        A0141.addAttribute("rs:nullable", "true");
        A0141.addAttribute("rs:write", "true");
        A0141.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0141.addAttribute("rs:baseschema", "DBO");
        A0141.addAttribute("rs:basetable", "A01");
        A0141.addAttribute("rs:basecolumn", "A0141");
        //s:datatype
        Element A0141datatype = A0141.addElement("s:datatype");
        A0141datatype.addAttribute("dt:type", "string");
        A0141datatype.addAttribute("rs:dbtype", "str");
        A0141datatype.addAttribute("dt:maxLength", "8");
        // 25----------------s:AttributeType A3921
        Element A3921 = eleElement.addElement("s:AttributeType");
        A3921.addAttribute("name", "A3921");
        A3921.addAttribute("rs:number", "25");
        A3921.addAttribute("rs:nullable", "true");
        A3921.addAttribute("rs:write", "true");
        A3921.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3921.addAttribute("rs:baseschema", "DBO");
        A3921.addAttribute("rs:basetable", "A01");
        A3921.addAttribute("rs:basecolumn", "A3921");
        //s:datatype
        Element A3921datatype = A3921.addElement("s:datatype");
        A3921datatype.addAttribute("dt:type", "string");
        A3921datatype.addAttribute("rs:dbtype", "str");
        A3921datatype.addAttribute("dt:maxLength", "8");
        // 26----------------s:AttributeType A3927
        Element A3927 = eleElement.addElement("s:AttributeType");
        A3927.addAttribute("name", "A3927");
        A3927.addAttribute("rs:number", "26");
        A3927.addAttribute("rs:nullable", "true");
        A3927.addAttribute("rs:write", "true");
        A3927.addAttribute("rs:basecatalog", "OFFICIALV2");
        A3927.addAttribute("rs:baseschema", "DBO");
        A3927.addAttribute("rs:basetable", "A01");
        A3927.addAttribute("rs:basecolumn", "A3927");
        //s:datatype
        Element A3927datatype = A3927.addElement("s:datatype");
        A3927datatype.addAttribute("dt:type", "string");
        A3927datatype.addAttribute("rs:dbtype", "str");
        A3927datatype.addAttribute("dt:maxLength", "8");
        // 27----------------s:AttributeType A0144
        Element A0144 = eleElement.addElement("s:AttributeType");
        A0144.addAttribute("name", "A0144");
        A0144.addAttribute("rs:number", "27");
        A0144.addAttribute("rs:nullable", "true");
        A0144.addAttribute("rs:write", "true");
        A0144.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0144.addAttribute("rs:baseschema", "DBO");
        A0144.addAttribute("rs:basetable", "A01");
        A0144.addAttribute("rs:basecolumn", "A0144");
        //s:datatype
        Element A0144datatype = A0144.addElement("s:datatype");
        A0144datatype.addAttribute("dt:type", "string");
        A0144datatype.addAttribute("rs:dbtype", "str");
        A0144datatype.addAttribute("dt:maxLength", "8");
        // 28----------------s:AttributeType A0144B
        Element A0144B = eleElement.addElement("s:AttributeType");
        A0144B.addAttribute("name", "A0144B");
        A0144B.addAttribute("rs:number", "28");
        A0144B.addAttribute("rs:nullable", "true");
        A0144B.addAttribute("rs:write", "true");
        A0144B.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0144B.addAttribute("rs:baseschema", "DBO");
        A0144B.addAttribute("rs:basetable", "A01");
        A0144B.addAttribute("rs:basecolumn", "A0144B");
        //s:datatype
        Element A0144Bdatatype = A0144B.addElement("s:datatype");
        A0144Bdatatype.addAttribute("dt:type", "string");
        A0144Bdatatype.addAttribute("rs:dbtype", "str");
        A0144Bdatatype.addAttribute("dt:maxLength", "8");
        // 29----------------s:AttributeType A0144C
        Element A0144C = eleElement.addElement("s:AttributeType");
        A0144C.addAttribute("name", "A0144C");
        A0144C.addAttribute("rs:number", "29");
        A0144C.addAttribute("rs:nullable", "true");
        A0144C.addAttribute("rs:write", "true");
        A0144C.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0144C.addAttribute("rs:baseschema", "DBO");
        A0144C.addAttribute("rs:basetable", "A01");
        A0144C.addAttribute("rs:basecolumn", "A0144C");
        //s:datatype
        Element A0144Cdatatype = A0144C.addElement("s:datatype");
        A0144Cdatatype.addAttribute("dt:type", "string");
        A0144Cdatatype.addAttribute("rs:dbtype", "str");
        A0144Cdatatype.addAttribute("dt:maxLength", "8");
     // 30----------------s:AttributeType A0151
        Element A0151 = eleElement.addElement("s:AttributeType");
        A0151.addAttribute("name", "A0151");
        A0151.addAttribute("rs:number", "30");
        A0151.addAttribute("rs:nullable", "true");
        A0151.addAttribute("rs:write", "true");
        A0151.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0151.addAttribute("rs:baseschema", "DBO");
        A0151.addAttribute("rs:basetable", "A01");
        A0151.addAttribute("rs:basecolumn", "A0151");
        //s:datatype
        Element A0151datatype = A0151.addElement("s:datatype");
        A0151datatype.addAttribute("dt:type", "string");
        A0151datatype.addAttribute("rs:dbtype", "str");
        A0151datatype.addAttribute("dt:maxLength", "1");
     // 31----------------s:AttributeType A0153
        Element A0153 = eleElement.addElement("s:AttributeType");
        A0153.addAttribute("name", "A0153");
        A0153.addAttribute("rs:number", "31");
        A0153.addAttribute("rs:nullable", "true");
        A0153.addAttribute("rs:write", "true");
        A0153.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0153.addAttribute("rs:baseschema", "DBO");
        A0153.addAttribute("rs:basetable", "A01");
        A0153.addAttribute("rs:basecolumn", "A0153");
        //s:datatype
        Element A0153datatype = A0153.addElement("s:datatype");
        A0153datatype.addAttribute("dt:type", "string");
        A0153datatype.addAttribute("rs:dbtype", "str");
        A0153datatype.addAttribute("dt:maxLength", "1");
     // 32----------------s:AttributeType A0155
        Element A0155 = eleElement.addElement("s:AttributeType");
        A0155.addAttribute("name", "A0155");
        A0155.addAttribute("rs:number", "32");
        A0155.addAttribute("rs:nullable", "true");
        A0155.addAttribute("rs:write", "true");
        A0155.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0155.addAttribute("rs:baseschema", "DBO");
        A0155.addAttribute("rs:basetable", "A01");
        A0155.addAttribute("rs:basecolumn", "A0155");
        //s:datatype
        Element A0155datatype = A0155.addElement("s:datatype");
        A0155datatype.addAttribute("dt:type", "string");
        A0155datatype.addAttribute("rs:dbtype", "str");
        A0155datatype.addAttribute("dt:maxLength", "1");
     // 33----------------s:AttributeType A0157
        Element A0157 = eleElement.addElement("s:AttributeType");
        A0157.addAttribute("name", "A0157");
        A0157.addAttribute("rs:number", "33");
        A0157.addAttribute("rs:nullable", "true");
        A0157.addAttribute("rs:write", "true");
        A0157.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0157.addAttribute("rs:baseschema", "DBO");
        A0157.addAttribute("rs:basetable", "A01");
        A0157.addAttribute("rs:basecolumn", "A0157");
        //s:datatype
        Element A0157datatype = A0157.addElement("s:datatype");
        A0157datatype.addAttribute("dt:type", "string");
        A0157datatype.addAttribute("rs:dbtype", "str");
        A0157datatype.addAttribute("dt:maxLength", "120");
        // 34----------------s:AttributeType A0158
        Element A0158 = eleElement.addElement("s:AttributeType");
        A0158.addAttribute("name", "A0158");
        A0158.addAttribute("rs:number", "34");
        A0158.addAttribute("rs:nullable", "true");
        A0158.addAttribute("rs:write", "true");
        A0158.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0158.addAttribute("rs:baseschema", "DBO");
        A0158.addAttribute("rs:basetable", "A01");
        A0158.addAttribute("rs:basecolumn", "A0158");
        //s:datatype
        Element A0158datatype = A0158.addElement("s:datatype");
        A0158datatype.addAttribute("dt:type", "string");
        A0158datatype.addAttribute("rs:dbtype", "str");
        A0158datatype.addAttribute("dt:maxLength", "8");
     // 35----------------s:AttributeType A0160
        Element A0160 = eleElement.addElement("s:AttributeType");
        A0160.addAttribute("name", "A0160");
        A0160.addAttribute("rs:number", "35");
        A0160.addAttribute("rs:nullable", "true");
        A0160.addAttribute("rs:write", "true");
        A0160.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0160.addAttribute("rs:baseschema", "DBO");
        A0160.addAttribute("rs:basetable", "A01");
        A0160.addAttribute("rs:basecolumn", "A0160");
        //s:datatype
        Element A0160datatype = A0160.addElement("s:datatype");
        A0160datatype.addAttribute("dt:type", "string");
        A0160datatype.addAttribute("rs:dbtype", "str");
        A0160datatype.addAttribute("dt:maxLength", "8");
     // 36----------------s:AttributeType A0163
        Element A0163 = eleElement.addElement("s:AttributeType");
        A0163.addAttribute("name", "A0163");
        A0163.addAttribute("rs:number", "36");
        A0163.addAttribute("rs:nullable", "true");
        A0163.addAttribute("rs:write", "true");
        A0163.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0163.addAttribute("rs:baseschema", "DBO");
        A0163.addAttribute("rs:basetable", "A01");
        A0163.addAttribute("rs:basecolumn", "A0163");
        //s:datatype
        Element A0163datatype = A0163.addElement("s:datatype");
        A0163datatype.addAttribute("dt:type", "string");
        A0163datatype.addAttribute("rs:dbtype", "str");
        A0163datatype.addAttribute("dt:maxLength", "8");
     // 37----------------s:AttributeType A0184
        Element A0184 = eleElement.addElement("s:AttributeType");
        A0184.addAttribute("name", "A0184");
        A0184.addAttribute("rs:number", "37");
        A0184.addAttribute("rs:nullable", "true");
        A0184.addAttribute("rs:write", "true");
        A0184.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0184.addAttribute("rs:baseschema", "DBO");
        A0184.addAttribute("rs:basetable", "A01");
        A0184.addAttribute("rs:basecolumn", "A0184");
        //s:datatype
        Element A0184datatype = A0184.addElement("s:datatype");
        A0184datatype.addAttribute("dt:type", "string");
        A0184datatype.addAttribute("rs:dbtype", "str");
        A0184datatype.addAttribute("dt:maxLength", "8");
     // 38----------------s:AttributeType A0193
        Element A0193 = eleElement.addElement("s:AttributeType");
        A0193.addAttribute("name", "A0193");
        A0193.addAttribute("rs:number", "38");
        A0193.addAttribute("rs:nullable", "true");
        A0193.addAttribute("rs:write", "true");
        A0193.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0193.addAttribute("rs:baseschema", "DBO");
        A0193.addAttribute("rs:basetable", "A01");
        A0193.addAttribute("rs:basecolumn", "A0193");
        //s:datatype
        Element A0193datatype = A0193.addElement("s:datatype");
        A0193datatype.addAttribute("dt:type", "string");
        A0193datatype.addAttribute("rs:dbtype", "str");
        A0193datatype.addAttribute("dt:maxLength", "8");
     // 39----------------s:AttributeType A0148C
        Element A0148C = eleElement.addElement("s:AttributeType");
        A0148C.addAttribute("name", "A0148C");
        A0148C.addAttribute("rs:number", "39");
        A0148C.addAttribute("rs:nullable", "true");
        A0148C.addAttribute("rs:write", "true");
        A0148C.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0148C.addAttribute("rs:baseschema", "DBO");
        A0148C.addAttribute("rs:basetable", "A01");
        A0148C.addAttribute("rs:basecolumn", "A0148C");
        //s:datatype
        Element A0148Cdatatype = A0148C.addElement("s:datatype");
        A0148Cdatatype.addAttribute("dt:type", "string");
        A0148Cdatatype.addAttribute("rs:dbtype", "str");
        A0148Cdatatype.addAttribute("dt:maxLength", "8");
     // 40----------------s:AttributeType A0195
        Element A0195 = eleElement.addElement("s:AttributeType");
        A0195.addAttribute("name", "A0195");
        A0195.addAttribute("rs:number", "40");
        A0195.addAttribute("rs:nullable", "true");
        A0195.addAttribute("rs:write", "true");
        A0195.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0195.addAttribute("rs:baseschema", "DBO");
        A0195.addAttribute("rs:basetable", "A01");
        A0195.addAttribute("rs:basecolumn", "A0195");
        //s:datatype
        Element A0195datatype = A0195.addElement("s:datatype");
        A0195datatype.addAttribute("dt:type", "string");
        A0195datatype.addAttribute("rs:dbtype", "str");
        A0195datatype.addAttribute("dt:maxLength", "199");
     // 41----------------s:AttributeType A0192
        Element A0192 = eleElement.addElement("s:AttributeType");
        A0192.addAttribute("name", "A0192");
        A0192.addAttribute("rs:number", "41");
        A0192.addAttribute("rs:nullable", "true");
        A0192.addAttribute("rs:write", "true");
        A0192.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0192.addAttribute("rs:baseschema", "DBO");
        A0192.addAttribute("rs:basetable", "A01");
        A0192.addAttribute("rs:basecolumn", "A0192");
        //s:datatype
        Element A0192datatype = A0192.addElement("s:datatype");
        A0192datatype.addAttribute("dt:type", "string");
        A0192datatype.addAttribute("rs:dbtype", "str");
        A0192datatype.addAttribute("dt:maxLength", "2000");
        // 42----------------s:AttributeType A01K01
        Element A01K01 = eleElement.addElement("s:AttributeType");
        A01K01.addAttribute("name", "A01K01");
        A01K01.addAttribute("rs:number", "42");
        A01K01.addAttribute("rs:nullable", "true");
        A01K01.addAttribute("rs:write", "true");
        A01K01.addAttribute("rs:basecatalog", "OFFICIALV2");
        A01K01.addAttribute("rs:baseschema", "DBO");
        A01K01.addAttribute("rs:basetable", "A01");
        A01K01.addAttribute("rs:basecolumn", "A01K01");
        //s:datatype
        Element A01K01datatype = A01K01.addElement("s:datatype");
        A01K01datatype.addAttribute("dt:type", "string");
        A01K01datatype.addAttribute("rs:dbtype", "str");
        A01K01datatype.addAttribute("dt:maxLength", "1");
     // 43----------------s:AttributeType A0196
        Element A0196 = eleElement.addElement("s:AttributeType");
        A0196.addAttribute("name", "A0196");
        A0196.addAttribute("rs:number", "43");
        A0196.addAttribute("rs:nullable", "true");
        A0196.addAttribute("rs:write", "true");
        A0196.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0196.addAttribute("rs:baseschema", "DBO");
        A0196.addAttribute("rs:basetable", "A01");
        A0196.addAttribute("rs:basecolumn", "A0196");
        //s:datatype
        Element A0196datatype = A0196.addElement("s:datatype");
        A0196datatype.addAttribute("dt:type", "string");
        A0196datatype.addAttribute("rs:dbtype", "str");
        A0196datatype.addAttribute("dt:maxLength", "120");
        // 44----------------s:AttributeType A0187A
        Element A0187A = eleElement.addElement("s:AttributeType");
        A0187A.addAttribute("name", "A0187A");
        A0187A.addAttribute("rs:number", "44");
        A0187A.addAttribute("rs:nullable", "true");
        A0187A.addAttribute("rs:write", "true");
        A0187A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0187A.addAttribute("rs:baseschema", "DBO");
        A0187A.addAttribute("rs:basetable", "A01");
        A0187A.addAttribute("rs:basecolumn", "A0187A");
        //s:datatype
        Element A0187Adatatype = A0187A.addElement("s:datatype");
        A0187Adatatype.addAttribute("dt:type", "string");
        A0187Adatatype.addAttribute("rs:dbtype", "str");
        A0187Adatatype.addAttribute("dt:maxLength", "100");
     // 45----------------s:AttributeType A0199
        Element A0199 = eleElement.addElement("s:AttributeType");
        A0199.addAttribute("name", "A0199");
        A0199.addAttribute("rs:number", "45");
        A0199.addAttribute("rs:nullable", "true");
        A0199.addAttribute("rs:write", "true");
        A0199.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0199.addAttribute("rs:baseschema", "DBO");
        A0199.addAttribute("rs:basetable", "A01");
        A0199.addAttribute("rs:basecolumn", "A0199");
        //s:datatype
        Element A0199datatype = A0199.addElement("s:datatype");
        A0199datatype.addAttribute("dt:type", "string");
        A0199datatype.addAttribute("rs:dbtype", "str");
        A0199datatype.addAttribute("dt:maxLength", "1");
     // 46----------------s:AttributeType A0161
        Element A0161 = eleElement.addElement("s:AttributeType");
        A0161.addAttribute("name", "A0161");
        A0161.addAttribute("rs:number", "46");
        A0161.addAttribute("rs:nullable", "true");
        A0161.addAttribute("rs:write", "true");
        A0161.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0161.addAttribute("rs:baseschema", "DBO");
        A0161.addAttribute("rs:basetable", "A01");
        A0161.addAttribute("rs:basecolumn", "A0161");
        //s:datatype
        Element A0161datatype = A0161.addElement("s:datatype");
        A0161datatype.addAttribute("dt:type", "string");
        A0161datatype.addAttribute("rs:dbtype", "str");
        A0161datatype.addAttribute("dt:maxLength", "500");
     // 47----------------s:AttributeType A0162
        Element A0162 = eleElement.addElement("s:AttributeType");
        A0162.addAttribute("name", "A0162");
        A0162.addAttribute("rs:number", "47");
        A0162.addAttribute("rs:nullable", "true");
        A0162.addAttribute("rs:write", "true");
        A0162.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0162.addAttribute("rs:baseschema", "DBO");
        A0162.addAttribute("rs:basetable", "A01");
        A0162.addAttribute("rs:basecolumn", "A0162");
        //s:datatype
        Element A0162datatype = A0162.addElement("s:datatype");
        A0162datatype.addAttribute("dt:type", "string");
        A0162datatype.addAttribute("rs:dbtype", "str");
        A0162datatype.addAttribute("dt:maxLength", "8");
     // 48----------------s:AttributeType A0148
        Element A0148 = eleElement.addElement("s:AttributeType");
        A0148.addAttribute("name", "A0148");
        A0148.addAttribute("rs:number", "48");
        A0148.addAttribute("rs:nullable", "true");
        A0148.addAttribute("rs:write", "true");
        A0148.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0148.addAttribute("rs:baseschema", "DBO");
        A0148.addAttribute("rs:basetable", "A01");
        A0148.addAttribute("rs:basecolumn", "A0148");
        //s:datatype
        Element A0148datatype = A0148.addElement("s:datatype");
        A0148datatype.addAttribute("dt:type", "string");
        A0148datatype.addAttribute("rs:dbtype", "str");
        A0148datatype.addAttribute("dt:maxLength", "20");
     // 49----------------s:AttributeType A0149
        Element A0149 = eleElement.addElement("s:AttributeType");
        A0149.addAttribute("name", "A0149");
        A0149.addAttribute("rs:number", "49");
        A0149.addAttribute("rs:nullable", "true");
        A0149.addAttribute("rs:write", "true");
        A0149.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0149.addAttribute("rs:baseschema", "DBO");
        A0149.addAttribute("rs:basetable", "A01");
        A0149.addAttribute("rs:basecolumn", "A0149");
        //s:datatype
        Element A0149datatype = A0149.addElement("s:datatype");
        A0149datatype.addAttribute("dt:type", "string");
        A0149datatype.addAttribute("rs:dbtype", "str");
        A0149datatype.addAttribute("dt:maxLength", "80");
     // 50----------------s:AttributeType A0198
        Element A0198 = eleElement.addElement("s:AttributeType");
        A0198.addAttribute("name", "A0198");
        A0198.addAttribute("rs:number", "50");
        A0198.addAttribute("rs:nullable", "true");
        A0198.addAttribute("rs:write", "true");
        A0198.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0198.addAttribute("rs:baseschema", "DBO");
        A0198.addAttribute("rs:basetable", "A01");
        A0198.addAttribute("rs:basecolumn", "A0198");
        //s:datatype
        Element A0198datatype = A0198.addElement("s:datatype");
        A0198datatype.addAttribute("dt:type", "string");
        A0198datatype.addAttribute("rs:dbtype", "str");
        A0198datatype.addAttribute("dt:maxLength", "200");
     // 51----------------s:AttributeType A0159
        Element A0159 = eleElement.addElement("s:AttributeType");
        A0159.addAttribute("name", "A0159");
        A0159.addAttribute("rs:number", "51");
        A0159.addAttribute("rs:nullable", "true");
        A0159.addAttribute("rs:write", "true");
        A0159.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0159.addAttribute("rs:baseschema", "DBO");
        A0159.addAttribute("rs:basetable", "A01");
        A0159.addAttribute("rs:basecolumn", "A0159");
        //s:datatype
        Element A0159datatype = A0159.addElement("s:datatype");
        A0159datatype.addAttribute("dt:type", "string");
        A0159datatype.addAttribute("rs:dbtype", "str");
        A0159datatype.addAttribute("dt:maxLength", "50");
        // 52----------------s:AttributeType A0165
        Element A0165 = eleElement.addElement("s:AttributeType");
        A0165.addAttribute("name", "A0165");
        A0165.addAttribute("rs:number", "52");
        A0165.addAttribute("rs:nullable", "true");
        A0165.addAttribute("rs:write", "true");
        A0165.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0165.addAttribute("rs:baseschema", "DBO");
        A0165.addAttribute("rs:basetable", "A01");
        A0165.addAttribute("rs:basecolumn", "A0165");
        //s:datatype
        Element A0165datatype = A0165.addElement("s:datatype");
        A0165datatype.addAttribute("dt:type", "string");
        A0165datatype.addAttribute("rs:dbtype", "str");
        A0165datatype.addAttribute("dt:maxLength", "8"); 
     // 53----------------s:AttributeType A0104A
        Element A0104A = eleElement.addElement("s:AttributeType");
        A0104A.addAttribute("name", "A0104A");
        A0104A.addAttribute("rs:number", "53");
        A0104A.addAttribute("rs:nullable", "true");
        A0104A.addAttribute("rs:write", "true");
        A0104A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0104A.addAttribute("rs:baseschema", "DBO");
        A0104A.addAttribute("rs:basetable", "A01");
        A0104A.addAttribute("rs:basecolumn", "A0104A");
        //s:datatype
        Element A0104Adatatype = A0104A.addElement("s:datatype");
        A0104Adatatype.addAttribute("dt:type", "string");
        A0104Adatatype.addAttribute("rs:dbtype", "str");
        A0104Adatatype.addAttribute("dt:maxLength", "60");
     // 54----------------s:AttributeType A0117A
        Element A0117A = eleElement.addElement("s:AttributeType");
        A0117A.addAttribute("name", "A0117A");
        A0117A.addAttribute("rs:number", "54");
        A0117A.addAttribute("rs:nullable", "true");
        A0117A.addAttribute("rs:write", "true");
        A0117A.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0117A.addAttribute("rs:baseschema", "DBO");
        A0117A.addAttribute("rs:basetable", "A01");
        A0117A.addAttribute("rs:basecolumn", "A0117A");
        //s:datatype
        Element A0117Adatatype = A0117A.addElement("s:datatype");
        A0117Adatatype.addAttribute("dt:type", "string");
        A0117Adatatype.addAttribute("rs:dbtype", "str");
        A0117Adatatype.addAttribute("dt:maxLength", "60");
     // 55----------------s:AttributeType XGR
        Element XGR = eleElement.addElement("s:AttributeType");
        XGR.addAttribute("name", "XGR");
        XGR.addAttribute("rs:number", "55");
        XGR.addAttribute("rs:nullable", "true");
        XGR.addAttribute("rs:write", "true");
        XGR.addAttribute("rs:basecatalog", "OFFICIALV2");
        XGR.addAttribute("rs:baseschema", "DBO");
        XGR.addAttribute("rs:basetable", "A01");
        XGR.addAttribute("rs:basecolumn", "XGR");
        //s:datatype
        Element XGRdatatype = XGR.addElement("s:datatype");
        XGRdatatype.addAttribute("dt:type", "string");
        XGRdatatype.addAttribute("rs:dbtype", "str");
        XGRdatatype.addAttribute("dt:maxLength", "36");
     // 56----------------s:AttributeType XGSJ
        Element XGSJ = eleElement.addElement("s:AttributeType");
        XGSJ.addAttribute("name", "XGSJ");
        XGSJ.addAttribute("rs:number", "56");
        XGSJ.addAttribute("rs:nullable", "true");
        XGSJ.addAttribute("rs:write", "true");
        XGSJ.addAttribute("rs:basecatalog", "OFFICIALV2");
        XGSJ.addAttribute("rs:baseschema", "DBO");
        XGSJ.addAttribute("rs:basetable", "A01");
        XGSJ.addAttribute("rs:basecolumn", "XGSJ");
        //s:datatype
        Element XGSJdatatype = XGSJ.addElement("s:datatype");
        XGSJdatatype.addAttribute("dt:type", "string");
        XGSJdatatype.addAttribute("rs:dbtype", "str");
        XGSJdatatype.addAttribute("dt:maxLength", "8");
        // 57----------------s:AttributeType A0140
        Element A0140 = eleElement.addElement("s:AttributeType");
        A0140.addAttribute("name", "A0140");
        A0140.addAttribute("rs:number", "57");
        A0140.addAttribute("rs:nullable", "true");
        A0140.addAttribute("rs:write", "true");
        A0140.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0140.addAttribute("rs:baseschema", "DBO");
        A0140.addAttribute("rs:basetable", "A01");
        A0140.addAttribute("rs:basecolumn", "A0140");
        //s:datatype
        Element A0140datatype = A0140.addElement("s:datatype");
        A0140datatype.addAttribute("dt:type", "string");
        A0140datatype.addAttribute("rs:dbtype", "str");
        A0140datatype.addAttribute("dt:maxLength", "100");
     // 58----------------s:AttributeType NL
        Element NL = eleElement.addElement("s:AttributeType");
        NL.addAttribute("name", "NL");
        NL.addAttribute("rs:number", "58");
        NL.addAttribute("rs:nullable", "true");
        NL.addAttribute("rs:write", "true");
        NL.addAttribute("rs:basecatalog", "OFFICIALV2");
        NL.addAttribute("rs:baseschema", "DBO");
        NL.addAttribute("rs:basetable", "A01");
        NL.addAttribute("rs:basecolumn", "NL");
        //s:datatype
        Element NLdatatype = NL.addElement("s:datatype");
        NLdatatype.addAttribute("dt:type", "string");
        NLdatatype.addAttribute("rs:dbtype", "str");
        NLdatatype.addAttribute("dt:maxLength", "8");
     // 59----------------s:AttributeType QRZXL
        Element QRZXL = eleElement.addElement("s:AttributeType");
        QRZXL.addAttribute("name", "QRZXL");
        QRZXL.addAttribute("rs:number", "59");
        QRZXL.addAttribute("rs:nullable", "true");
        QRZXL.addAttribute("rs:write", "true");
        QRZXL.addAttribute("rs:basecatalog", "OFFICIALV2");
        QRZXL.addAttribute("rs:baseschema", "DBO");
        QRZXL.addAttribute("rs:basetable", "A01");
        QRZXL.addAttribute("rs:basecolumn", "QRZXL");
        //s:datatype
        Element QRZXLdatatype = QRZXL.addElement("s:datatype");
        QRZXLdatatype.addAttribute("dt:type", "string");
        QRZXLdatatype.addAttribute("rs:dbtype", "str");
        QRZXLdatatype.addAttribute("dt:maxLength", "60");
     // 60----------------s:AttributeType QRZXW
        Element QRZXW = eleElement.addElement("s:AttributeType");
        QRZXW.addAttribute("name", "QRZXW");
        QRZXW.addAttribute("rs:number", "60");
        QRZXW.addAttribute("rs:nullable", "true");
        QRZXW.addAttribute("rs:write", "true");
        QRZXW.addAttribute("rs:basecatalog", "OFFICIALV2");
        QRZXW.addAttribute("rs:baseschema", "DBO");
        QRZXW.addAttribute("rs:basetable", "A01");
        QRZXW.addAttribute("rs:basecolumn", "QRZXW");
        //s:datatype
        Element QRZXWdatatype = QRZXW.addElement("s:datatype");
        QRZXWdatatype.addAttribute("dt:type", "string");
        QRZXWdatatype.addAttribute("rs:dbtype", "str");
        QRZXWdatatype.addAttribute("dt:maxLength", "60");
     // 61----------------s:AttributeType ZZXL
        Element ZZXL = eleElement.addElement("s:AttributeType");
        ZZXL.addAttribute("name", "ZZXL");
        ZZXL.addAttribute("rs:number", "61");
        ZZXL.addAttribute("rs:nullable", "true");
        ZZXL.addAttribute("rs:write", "true");
        ZZXL.addAttribute("rs:basecatalog", "OFFICIALV2");
        ZZXL.addAttribute("rs:baseschema", "DBO");
        ZZXL.addAttribute("rs:basetable", "A01");
        ZZXL.addAttribute("rs:basecolumn", "ZZXL");
        //s:datatype
        Element ZZXLdatatype = ZZXL.addElement("s:datatype");
        ZZXLdatatype.addAttribute("dt:type", "string");
        ZZXLdatatype.addAttribute("rs:dbtype", "str");
        ZZXLdatatype.addAttribute("dt:maxLength", "60");
     // 62----------------s:AttributeType ZZXW
        Element ZZXW = eleElement.addElement("s:AttributeType");
        ZZXW.addAttribute("name", "ZZXW");
        ZZXW.addAttribute("rs:number", "62");
        ZZXW.addAttribute("rs:nullable", "true");
        ZZXW.addAttribute("rs:write", "true");
        ZZXW.addAttribute("rs:basecatalog", "OFFICIALV2");
        ZZXW.addAttribute("rs:baseschema", "DBO");
        ZZXW.addAttribute("rs:basetable", "A01");
        ZZXW.addAttribute("rs:basecolumn", "ZZXW");
        //s:datatype
        Element ZZXWdatatype = ZZXW.addElement("s:datatype");
        ZZXWdatatype.addAttribute("dt:type", "string");
        ZZXWdatatype.addAttribute("rs:dbtype", "str");
        ZZXWdatatype.addAttribute("dt:maxLength", "60");
     // 63----------------s:AttributeType NMZW
        Element NMZW = eleElement.addElement("s:AttributeType");
        NMZW.addAttribute("name", "NMZW");
        NMZW.addAttribute("rs:number", "63");
        NMZW.addAttribute("rs:nullable", "true");
        NMZW.addAttribute("rs:write", "true");
        NMZW.addAttribute("rs:basecatalog", "OFFICIALV2");
        NMZW.addAttribute("rs:baseschema", "DBO");
        NMZW.addAttribute("rs:basetable", "A01");
        NMZW.addAttribute("rs:basecolumn", "NMZW");
        //s:datatype
        Element NMZWdatatype = NMZW.addElement("s:datatype");
        NMZWdatatype.addAttribute("dt:type", "string");
        NMZWdatatype.addAttribute("rs:dbtype", "str");
        NMZWdatatype.addAttribute("dt:maxLength", "100");
     // 64----------------s:AttributeType NRZW
        Element NRZW = eleElement.addElement("s:AttributeType");
        NRZW.addAttribute("name", "NRZW");
        NRZW.addAttribute("rs:number", "64");
        NRZW.addAttribute("rs:nullable", "true");
        NRZW.addAttribute("rs:write", "true");
        NRZW.addAttribute("rs:basecatalog", "OFFICIALV2");
        NRZW.addAttribute("rs:baseschema", "DBO");
        NRZW.addAttribute("rs:basetable", "A01");
        NRZW.addAttribute("rs:basecolumn", "NRZW");
        //s:datatype
        Element NRZWdatatype = NRZW.addElement("s:datatype");
        NRZWdatatype.addAttribute("dt:type", "string");
        NRZWdatatype.addAttribute("rs:dbtype", "str");
        NRZWdatatype.addAttribute("dt:maxLength", "100");
     // 65----------------s:AttributeType A1701
        Element A1701 = eleElement.addElement("s:AttributeType");
        A1701.addAttribute("name", "A1701");
        A1701.addAttribute("rs:number", "65");
        A1701.addAttribute("rs:nullable", "true");
        A1701.addAttribute("rs:write", "true");
        A1701.addAttribute("rs:basecatalog", "OFFICIALV2");
        A1701.addAttribute("rs:baseschema", "DBO");
        A1701.addAttribute("rs:basetable", "A01");
        A1701.addAttribute("rs:basecolumn", "A1701");
        //s:datatype
        Element A1701datatype = A1701.addElement("s:datatype");
        A1701datatype.addAttribute("dt:type", "string");
        A1701datatype.addAttribute("rs:dbtype", "str");
        A1701datatype.addAttribute("dt:maxLength", "8000");
        // 66----------------s:AttributeType A14Z101
        Element A14Z101 = eleElement.addElement("s:AttributeType");
        A14Z101.addAttribute("name", "A14Z101");
        A14Z101.addAttribute("rs:number", "66");
        A14Z101.addAttribute("rs:nullable", "true");
        A14Z101.addAttribute("rs:write", "true");
        A14Z101.addAttribute("rs:basecatalog", "OFFICIALV2");
        A14Z101.addAttribute("rs:baseschema", "DBO");
        A14Z101.addAttribute("rs:basetable", "A01");
        A14Z101.addAttribute("rs:basecolumn", "A14Z101");
        //s:datatype
        Element A14Z101datatype = A14Z101.addElement("s:datatype");
        A14Z101datatype.addAttribute("dt:type", "string");
        A14Z101datatype.addAttribute("rs:dbtype", "str");
        A14Z101datatype.addAttribute("dt:maxLength", "2000");
     // 67----------------s:AttributeType A15Z101
        Element A15Z101 = eleElement.addElement("s:AttributeType");
        A15Z101.addAttribute("name", "A15Z101");
        A15Z101.addAttribute("rs:number", "67");
        A15Z101.addAttribute("rs:nullable", "true");
        A15Z101.addAttribute("rs:write", "true");
        A15Z101.addAttribute("rs:basecatalog", "OFFICIALV2");
        A15Z101.addAttribute("rs:baseschema", "DBO");
        A15Z101.addAttribute("rs:basetable", "A01");
        A15Z101.addAttribute("rs:basecolumn", "A15Z101");
        //s:datatype
        Element A15Z101datatype = A15Z101.addElement("s:datatype");
        A15Z101datatype.addAttribute("dt:type", "string");
        A15Z101datatype.addAttribute("rs:dbtype", "str");
        A15Z101datatype.addAttribute("dt:maxLength", "2000");
     // 68----------------s:AttributeType RMLY
        Element RMLY = eleElement.addElement("s:AttributeType");
        RMLY.addAttribute("name", "RMLY");
        RMLY.addAttribute("rs:number", "68");
        RMLY.addAttribute("rs:nullable", "true");
        RMLY.addAttribute("rs:write", "true");
        RMLY.addAttribute("rs:basecatalog", "OFFICIALV2");
        RMLY.addAttribute("rs:baseschema", "DBO");
        RMLY.addAttribute("rs:basetable", "A01");
        RMLY.addAttribute("rs:basecolumn", "RMLY");
        //s:datatype
        Element RMLYdatatype = RMLY.addElement("s:datatype");
        RMLYdatatype.addAttribute("dt:type", "string");
        RMLYdatatype.addAttribute("rs:dbtype", "str");
        RMLYdatatype.addAttribute("dt:maxLength", "100");
     // 69----------------s:AttributeType CBDW
        Element CBDW = eleElement.addElement("s:AttributeType");
        CBDW.addAttribute("name", "CBDW");
        CBDW.addAttribute("rs:number", "69");
        CBDW.addAttribute("rs:nullable", "true");
        CBDW.addAttribute("rs:write", "true");
        CBDW.addAttribute("rs:basecatalog", "OFFICIALV2");
        CBDW.addAttribute("rs:baseschema", "DBO");
        CBDW.addAttribute("rs:basetable", "A01");
        CBDW.addAttribute("rs:basecolumn", "CBDW");
        //s:datatype
        Element CBDWdatatype = CBDW.addElement("s:datatype");
        CBDWdatatype.addAttribute("dt:type", "string");
        CBDWdatatype.addAttribute("rs:dbtype", "str");
        CBDWdatatype.addAttribute("dt:maxLength", "100");
     // 70----------------s:AttributeType TBSJ
        Element TBSJ = eleElement.addElement("s:AttributeType");
        TBSJ.addAttribute("name", "TBSJ");
        TBSJ.addAttribute("rs:number", "70");
        TBSJ.addAttribute("rs:nullable", "true");
        TBSJ.addAttribute("rs:write", "true");
        TBSJ.addAttribute("rs:basecatalog", "OFFICIALV2");
        TBSJ.addAttribute("rs:baseschema", "DBO");
        TBSJ.addAttribute("rs:basetable", "A01");
        TBSJ.addAttribute("rs:basecolumn", "TBSJ");
        //s:datatype
        Element TBSJdatatype = TBSJ.addElement("s:datatype");
        TBSJdatatype.addAttribute("dt:type", "string");
        TBSJdatatype.addAttribute("rs:dbtype", "str");
        TBSJdatatype.addAttribute("dt:maxLength", "8");
     // 71----------------s:AttributeType JSNLSJ
        Element JSNLSJ = eleElement.addElement("s:AttributeType");
        JSNLSJ.addAttribute("name", "JSNLSJ");
        JSNLSJ.addAttribute("rs:number", "71");
        JSNLSJ.addAttribute("rs:nullable", "true");
        JSNLSJ.addAttribute("rs:write", "true");
        JSNLSJ.addAttribute("rs:basecatalog", "OFFICIALV2");
        JSNLSJ.addAttribute("rs:baseschema", "DBO");
        JSNLSJ.addAttribute("rs:basetable", "A01");
        JSNLSJ.addAttribute("rs:basecolumn", "JSNLSJ");
        //s:datatype
        Element JSNLSJdatatype = JSNLSJ.addElement("s:datatype");
        JSNLSJdatatype.addAttribute("dt:type", "string");
        JSNLSJdatatype.addAttribute("rs:dbtype", "str");
        JSNLSJdatatype.addAttribute("dt:maxLength", "8");
        // 72----------------s:AttributeType TBR
        Element TBR = eleElement.addElement("s:AttributeType");
        TBR.addAttribute("name", "TBR");
        TBR.addAttribute("rs:number", "72");
        TBR.addAttribute("rs:nullable", "true");
        TBR.addAttribute("rs:write", "true");
        TBR.addAttribute("rs:basecatalog", "OFFICIALV2");
        TBR.addAttribute("rs:baseschema", "DBO");
        TBR.addAttribute("rs:basetable", "A01");
        TBR.addAttribute("rs:basecolumn", "TBR");
        //s:datatype
        Element TBRdatatype = TBR.addElement("s:datatype");
        TBRdatatype.addAttribute("dt:type", "string");
        TBRdatatype.addAttribute("rs:dbtype", "str");
        TBRdatatype.addAttribute("dt:maxLength", "36");
     // 73----------------s:AttributeType A01K02
        Element A01K02 = eleElement.addElement("s:AttributeType");
        A01K02.addAttribute("name", "A01K02");
        A01K02.addAttribute("rs:number", "73");
        A01K02.addAttribute("rs:nullable", "true");
        A01K02.addAttribute("rs:write", "true");
        A01K02.addAttribute("rs:basecatalog", "OFFICIALV2");
        A01K02.addAttribute("rs:baseschema", "DBO");
        A01K02.addAttribute("rs:basetable", "A01");
        A01K02.addAttribute("rs:basecolumn", "A01K02");
        //s:datatype
        Element A01K02datatype = A01K02.addElement("s:datatype");
        A01K02datatype.addAttribute("dt:type", "string");
        A01K02datatype.addAttribute("rs:dbtype", "str");
        A01K02datatype.addAttribute("dt:maxLength", "8");
     // 74----------------s:AttributeType A0194
        Element A0194 = eleElement.addElement("s:AttributeType");
        A0194.addAttribute("name", "A0194");
        A0194.addAttribute("rs:number", "74");
        A0194.addAttribute("rs:nullable", "true");
        A0194.addAttribute("rs:write", "true");
        A0194.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0194.addAttribute("rs:baseschema", "DBO");
        A0194.addAttribute("rs:basetable", "A01");
        A0194.addAttribute("rs:basecolumn", "A0194");
        //s:datatype
        Element A0194datatype = A0194.addElement("s:datatype");
        A0194datatype.addAttribute("dt:type", "string");
        A0194datatype.addAttribute("rs:dbtype", "str");
        A0194datatype.addAttribute("dt:maxLength", "8");
     // 75----------------s:AttributeType SORTID
        Element SORTID = eleElement.addElement("s:AttributeType");
        SORTID.addAttribute("name", "SORTID");
        SORTID.addAttribute("rs:number", "75");
        SORTID.addAttribute("rs:nullable", "true");
        SORTID.addAttribute("rs:write", "true");
        SORTID.addAttribute("rs:basecatalog", "OFFICIALV2");
        SORTID.addAttribute("rs:baseschema", "DBO");
        SORTID.addAttribute("rs:basetable", "A01");
        SORTID.addAttribute("rs:basecolumn", "SORTID");
        //s:datatype
        Element SORTIDdatatype = SORTID.addElement("s:datatype");
        SORTIDdatatype.addAttribute("dt:type", "int");
        SORTIDdatatype.addAttribute("dt:maxLength", "4");
        SORTIDdatatype.addAttribute("rs:precision", "10");
        SORTIDdatatype.addAttribute("rs:fixedlength", "true");
     // 76----------------s:AttributeType A0000NEW
        Element A0000NEW = eleElement.addElement("s:AttributeType");
        A0000NEW.addAttribute("name", "A0000NEW");
        A0000NEW.addAttribute("rs:number", "76");
        A0000NEW.addAttribute("rs:nullable", "true");
        A0000NEW.addAttribute("rs:write", "true");
        A0000NEW.addAttribute("rs:basecatalog", "OFFICIALV2");
        A0000NEW.addAttribute("rs:baseschema", "DBO");
        A0000NEW.addAttribute("rs:basetable", "A01");
        A0000NEW.addAttribute("rs:basecolumn", "A0000NEW");
        //s:datatype
        Element A0000NEWdatatype = A0000NEW.addElement("s:datatype");
        A0000NEWdatatype.addAttribute("dt:type", "string");
        A0000NEWdatatype.addAttribute("rs:dbtype", "str");
        A0000NEWdatatype.addAttribute("dt:maxLength", "60");
        /**
         * 加载数据 
         */
        Element sextends = eleElement.addElement("s:extends");
        sextends.addAttribute("type", "rs:rowbase");
        Element rsdata = xmlElement.addElement("rs:data");
        for (Object object : list) {
			A01 a01 = (A01) object;
			Element zrow = rsdata.addElement("z:row");
			try {
				Map map = Map2Temp.convertBean(a01);
				zrow.addAttribute("A0191", emptoString(map.get("A0191")));
				zrow.addAttribute("A0128", emptoString(map.get("A0128")));
				zrow.addAttribute("ZZXWXX", emptoString(map.get("ZZXWXX")));
				zrow.addAttribute("ZZXLXX", emptoString(map.get("ZZXLXX")));
				zrow.addAttribute("QRZXWXX", emptoString(map.get("QRZXWXX")));
				zrow.addAttribute("QRZXLXX", emptoString(map.get("QRZXLXX")));
				zrow.addAttribute("A0192A", emptoString(map.get("A0192A")));
				zrow.addAttribute("A015A", emptoString(map.get("A015A")));
				zrow.addAttribute("A0192B", emptoString(map.get("A0192B")));
				zrow.addAttribute("A0180", emptoString(map.get("A0180")));
				
				zrow.addAttribute("A0000", emptoString(map.get("A0000")));
				zrow.addAttribute("A0101", emptoString(map.get("A0101")));
				zrow.addAttribute("A0102", emptoString(map.get("A0102")));
				zrow.addAttribute("A0104", emptoString(map.get("A0104")));
				zrow.addAttribute("A0107", emptoString(map.get("A0107")));
				zrow.addAttribute("A0111", emptoString(map.get("A0111")));
				zrow.addAttribute("A0111A", emptoString(map.get("A0111A")));
				zrow.addAttribute("A0114", emptoString(map.get("A0114")));
				zrow.addAttribute("A0114A", emptoString(map.get("A0114A")));
				zrow.addAttribute("A0117", emptoString(map.get("A0117")));
				
				zrow.addAttribute("A0134", emptoString(map.get("A0134")));
				zrow.addAttribute("A0141", emptoString(map.get("A0141")));
				zrow.addAttribute("A3921", emptoString(map.get("A3921")));
				zrow.addAttribute("A3927", emptoString(map.get("A3927")));
				zrow.addAttribute("A0144", emptoString(map.get("A0144")));
				zrow.addAttribute("A0160", emptoString(map.get("A0160")));
				zrow.addAttribute("A0163", emptoString(map.get("A0163")));
				zrow.addAttribute("A0184", emptoString(map.get("A0184")));
				zrow.addAttribute("A0148C", emptoString(map.get("A0148C")));
				zrow.addAttribute("A0195", emptoString(map.get("A0195")));
				
				zrow.addAttribute("A0192", emptoString(map.get("A0192")));
				zrow.addAttribute("A01K01", emptoString(map.get("A01K01")));
				zrow.addAttribute("A0196", emptoString(map.get("A0196")));
				zrow.addAttribute("A0187A", emptoString(map.get("A0187A")));
				zrow.addAttribute("A0199", emptoString(map.get("A0199")));
				zrow.addAttribute("A0148", emptoString(map.get("A0148")));
				zrow.addAttribute("A0149", emptoString(map.get("A0149")));
				zrow.addAttribute("A0198", emptoString(map.get("A0198")));
				zrow.addAttribute("A0165", emptoString(map.get("A0165")));
				zrow.addAttribute("A0104A", emptoString(map.get("A0104A")));
				
				zrow.addAttribute("A0117A", emptoString(map.get("A0117A")));
				zrow.addAttribute("XGR", emptoString(map.get("XGR")));
				zrow.addAttribute("XGSJ", emptoString(map.get("XGSJ")));
				zrow.addAttribute("A0140", emptoString(map.get("A0140")));
				zrow.addAttribute("QRZXL", emptoString(map.get("QRZXL")));
				zrow.addAttribute("QRZXW", emptoString(map.get("QRZXW")));
				zrow.addAttribute("ZZXL", emptoString(map.get("ZZXL")));
				zrow.addAttribute("ZZXW", emptoString(map.get("ZZXW")));
				zrow.addAttribute("A1701", emptoString(map.get("A1701")));
				zrow.addAttribute("A14Z101", emptoString(map.get("A14Z101")));
				
				zrow.addAttribute("A15Z101", emptoString(map.get("A15Z101")));
				zrow.addAttribute("A0194",emptoString(map.get("A0194")));
				zrow.addAttribute("A0000NEW", emptoString(map.get("A0000")));
			
				
				
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
        
        XMLWriter out = null;
        
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        
        try {
            File xmlFile = new File(uuid + "Table/" +"A01.xml");//输出xml的路径
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            format.setSuppressDeclaration(true); //注意这句 
            out = new XMLWriter(bw, format); 
            out.setEscapeText(false);
            out.write(document);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	private static String emptoString(Object object) {
		// TODO Auto-generated method stub
		if (object!=null) {
			return object.toString();
		} else {
			return "";
		}
		
	}

	public static void main(String[] args) {
		List list = new ArrayList();
		A02 a = new A02();
		a.setA0000("111111111");
		a.setA0200("ddddddd");
		list.add(a);
		
	}
}
