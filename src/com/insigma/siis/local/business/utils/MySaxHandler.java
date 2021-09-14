package com.insigma.siis.local.business.utils;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class MySaxHandler implements ElementHandler {
	
	public String docname;
	public String lowerCase;
	public String table;
	public String imprecordid;
	public String t_n;
	public String uuid;
	public String from_file;
	public String B0111;
	public String impdeptid;

	public MySaxHandler() {
	}
	
	public MySaxHandler(String docname) {
		this.docname = docname;
	}

	public MySaxHandler(String docname, String lowerCase, String table,
			String imprecordid, int t_n, String uuid, String from_file,
			String B0111, String deptid, String impdeptid) {
		
	}
	
	
	@Override
	public void onEnd(ElementPath ep) {
		Element e = ep.getCurrent(); // 获得当前节点
		//e.addNamespace("s", "uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882");
		
		//System.out.println(e.asXML());
		CommonQueryBS.systemOut(e.asXML());
		e.detach(); // 记得从内存中移去
	}

	@Override
	public void onStart(ElementPath ep) {
	}

}