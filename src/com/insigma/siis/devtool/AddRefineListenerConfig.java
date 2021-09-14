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

import com.insigma.odin.framework.util.commform.ObjectUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class AddRefineListenerConfig {
	public static void writeWebXmlConfig() throws Exception {
		String dir = System.getProperty("user.dir");
		// ҵ��ϵͳ
		String fileName = dir + "/WebContent/WEB-INF/web.xml";
		String className = "com.insigma.siis.global.comm.webcontroller.SiFrameListener";
		SAXReader reader = new SAXReader();
		String encoding = "GBK";
		reader.setEncoding(encoding);
		Document document = reader.read(new InputStreamReader(new FileInputStream(fileName), encoding));
		Element element = document.getRootElement();
		List<Element> elList = element.elements("listener");
		for (int i = 0; i < elList.size(); i++) {
			element = (Element) elList.get(i);
			if (ObjectUtil.equals(element.element("listener-class").getText(), className)) {
				return;
			}
		}
		// û�У���Ҫ����
		element = (Element) elList.get(elList.size() - 1);
		Element el = (Element) element.clone();
		el.element("listener-class").setText(className);
		elList = element.getParent().elements();
		elList.add(elList.indexOf(element) + 1, el);
		// д����
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(encoding);
		XMLWriter output = new XMLWriter(new OutputStreamWriter(new FileOutputStream(fileName), encoding), format);
		output.write(document);
		output.close();

	}

	public static void main(String arg[]) throws Exception {
		// ��־��Ϊ��true����false�������û�ر�job����
		CommonQueryBS.systemOut("����ҵ���ܼ���");
		writeWebXmlConfig();
	}
}
