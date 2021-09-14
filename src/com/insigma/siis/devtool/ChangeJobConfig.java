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

public class ChangeJobConfig {
	public static void writeWebXmlConfig(boolean flag) throws Exception {
		String dir = System.getProperty("user.dir");
		// 业务系统
		String fileName = dir + "/WebContent/WEB-INF/web.xml";
		SAXReader reader = new SAXReader();
		String encoding = "GBK";
		reader.setEncoding(encoding);
		Document document = reader.read(new InputStreamReader(new FileInputStream(fileName), encoding));
		Element element = document.getRootElement();
		List<?> elList = element.elements("servlet");
		for (int i = 0; i < elList.size(); i++) {
			element = (Element) elList.get(i);
			if (ObjectUtil.equals(element.element("servlet-name").getText(), "QuartzInitializer")) {
				List<?> elList2 = element.elements("init-param");
				for (int j = 0; j < elList2.size(); j++) {
					Element element2 = (Element) elList2.get(j);
					if (ObjectUtil.equals(element2.element("param-name").getText(), "start-scheduler-on-load")) {
						element2.element("param-value").setText(String.valueOf(flag));
					}
				}
				break;
			}
		}
		// 写数据
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(encoding);
		XMLWriter output = new XMLWriter(new OutputStreamWriter(new FileOutputStream(fileName), encoding), format);
		output.write(document);
		output.close();

	}

	public static void main(String arg[]) throws Exception {
		// 标志改为“true”或“false”来启用或关闭job服务
		boolean flag = false;
		if (arg != null && arg.length > 0) {
			flag = Boolean.valueOf(arg[0]);
		}
		CommonQueryBS.systemOut("修改JOB服务“自动启动标志”为【" + flag + "】");
		writeWebXmlConfig(flag);
	}
}
