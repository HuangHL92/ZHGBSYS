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

public class ChangeDevModel {
	public static void writeConfig(HList hl) throws Exception {
		String dir = System.getProperty("user.dir");
		// 业务系统
		String fileName = dir + "/WebContent/WEB-INF/conf/Config.xml";
		SAXReader reader = new SAXReader();
		String encoding = "GBK";
		reader.setEncoding(encoding);
		Document document = reader.read(new InputStreamReader(new FileInputStream(fileName), encoding));
		Element element = document.getRootElement();
		List<Element> elList = element.elements("config");
		for (int i = 0; i < elList.size(); i++) {
			Element config = elList.get(i);
			String name = config.attributeValue("name");
			if (hl.isColumn(name)) {
				config.attribute("value").setValue(hl.getString(name));
			}
		}
		// 写数据
		OutputFormat format = new OutputFormat();
		format.setEncoding(encoding);
		XMLWriter output = new XMLWriter(new OutputStreamWriter(new FileOutputStream(fileName), encoding), format);
		output.write(document);
		output.close();

		// 报表系统
		fileName = dir + "/WebContent/WEB-INF/web.xml";
		encoding = "GBK";
		reader.setEncoding(encoding);
		document = reader.read(new InputStreamReader(new FileInputStream(fileName), encoding));
		element = document.getRootElement();
		String name = "welcome-file";
		if (hl.isColumn(name)) {
			Element el = element.element("welcome-file-list");
			el.element(name).setText(hl.getString(name));
		}
		name = "session-timeout";
		if (hl.isColumn(name)) {
			Element el = element.element("session-config");
			el.element(name).setText(hl.getString(name));
		}
		// 写数据
		format = OutputFormat.createPrettyPrint();
		format.setEncoding(encoding);
		output = new XMLWriter(new OutputStreamWriter(new FileOutputStream(fileName), encoding), format);
		output.write(document);
		output.close();

	}

	public static HList getConfig(String devmodel) {
		HList hl = new HList();
		if (ObjectUtil.equals(devmodel, "CommForm")) {
			// Config.xml
			hl.set("INDEX_PAGE", "/commform/Index.jsp");
			hl.set("MAIN_PAGE", "/commform/Index.jsp");
			hl.set("LOGON_PAGE", "/commform/LogonDialog.jsp");
			hl.set("RELOGON_PAGE", "/commform/ReLogon.jsp");
			hl.set("IS_USE_COMMFORM", "true");
			hl.set("SELECT_LAZYLOAD", "0");
			// web.xml
			hl.set("welcome-file", "commform/LogonDialog.jsp");
			hl.set("session-timeout", "120");
		} else if (ObjectUtil.equals(devmodel, "PageModel")) {
			// Config.xml
			hl.set("INDEX_PAGE", "/Index.jsp");
			hl.set("MAIN_PAGE", "/Index.jsp");
			hl.set("LOGON_PAGE", "/LogonDialog.jsp");
			hl.set("RELOGON_PAGE", "/ReLogon.jsp");
			hl.set("IS_USE_COMMFORM", "false");
			hl.set("SELECT_LAZYLOAD", "1");
			// web.xml
			hl.set("welcome-file", "LogonDialog.jsp");
			hl.set("session-timeout", "30");
		}
		return hl;
	}

	public static void main(String arg[]) throws Exception {
		String[] dev = { "CommForm", "PageModel" };
		// 在上面的数组中复制来替换执行
		String devmodel = "CommForm";
		if (arg != null && arg.length > 0) {
			devmodel = arg[0];
		}
		CommonQueryBS.systemOut("修改开发模式为【" + devmodel + "】");
		writeConfig(getConfig(devmodel));
	}
}
