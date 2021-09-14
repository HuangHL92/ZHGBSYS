package com.insigma.siis.devtool;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.insigma.odin.framework.commform.hibernate.HList;
import com.insigma.odin.framework.util.commform.ObjectUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class ChangeDataBase {
	public static void writeProxoolConfig(HList hl) throws Exception {
		String dir = System.getProperty("user.dir");
		// 业务系统
		String fileName = dir + "/src_local/proxool.xml";
		SAXReader reader = new SAXReader();
		String encoding = "GBK";
		reader.setEncoding(encoding);
		Document document = reader.read(new InputStreamReader(new FileInputStream(fileName), encoding));
		Element element = document.getRootElement();
		Element proxoolElement = element.element("proxool");
		element = proxoolElement.element("driver-properties");
		List<?> elList = element.elements("property");
		((Element) elList.get(0)).attribute("value").setValue(hl.getString("user"));
		((Element) elList.get(1)).attribute("value").setValue(hl.getString("password"));
		element = proxoolElement.element("driver-url");
		element.setText(hl.getString("url"));
		// 写数据
		OutputFormat format = new OutputFormat();
		format.setEncoding(encoding);
		XMLWriter output = new XMLWriter(new OutputStreamWriter(new FileOutputStream(fileName), encoding), format);
		output.write(document);
		output.close();

		// 报表系统
		fileName = dir + "/WebContent/WEB-INF/resources/datasource.xml";
		encoding = "UTF-8";
		reader.setEncoding(encoding);
		document = reader.read(new InputStreamReader(new FileInputStream(fileName), encoding));
		element = document.getRootElement();
		element = element.element("ConnectionMap");
		elList = element.elements("Connection");
		for (int i = 0; i < elList.size(); i++) {
			element = (Element) elList.get(i);
			if (ObjectUtil.equals(element.attribute("name").getValue(), "siis")) {
				element = element.element("JDBCDatabaseAttr");
				element.attribute("user").setValue(hl.getString("user"));
				element.attribute("password").setValue(hl.getString("rep_password"));
				element.attribute("url").setValue(hl.getString("url"));
				break;
			}
		}
		// 写数据
		format = new OutputFormat();
		format.setEncoding(encoding);
		output = new XMLWriter(new OutputStreamWriter(new FileOutputStream(fileName), encoding), format);
		output.write(document);
		output.close();

	}

	public static HList getConfig(String dbname) {
		HList hl = new HList();
		if (ObjectUtil.equals(dbname, "宁波测试")) {
			hl.set("user", "sicp");
			hl.set("password", "sicp");
			hl.set("url", "jdbc:oracle:thin:@10.247.2.73:1521:nbsi");
			hl.set("rep_password", "___006000270069007f");
		} else if (ObjectUtil.equals(dbname, "宁波正式")) {
			hl.set("user", "sicp");
			hl.set("password", "sicp");
			hl.set("url", "jdbc:oracle:thin:@10.247.2.73:1521:nbsi");
			hl.set("rep_password", "___006000270069007f");
		}
		return hl;
	}

	public static void main(String arg[]) throws Exception {
		String[] db = { "宁波测试", "宁波正式" };
		// 在上面的数组中复制来替换执行
		String dbname = "宁波测试";
		if (arg != null && arg.length > 0) {
			dbname = arg[0];
		}
		CommonQueryBS.systemOut("修改数据库连接为【" + dbname + "】");
		writeProxoolConfig(getConfig(dbname));
	}
}
