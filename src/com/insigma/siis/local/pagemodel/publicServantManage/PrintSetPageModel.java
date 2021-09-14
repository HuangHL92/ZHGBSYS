package com.insigma.siis.local.pagemodel.publicServantManage;


import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.servlet.http.HttpSession;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class PrintSetPageModel extends PageModel {
	/*//ҳ���ʼ��
	@Override
	public int doInit() throws RadowException {
		// ��ӡ����ɨ��
		List<String> list =  new ArrayList<String>();
		//���ѡһ��
		DocFlavor psInFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
		// ���ô�ӡ����
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		// aset.add(new Copies(3));
		// �������д�ӡ����
		PrintService[] services = PrintServiceLookup.lookupPrintServices(psInFormat, aset);
		// �����в��ҳ����Ĵ�ӡ�����Լ���Ҫ�Ĵ�ӡ������ƥ�䣬�ҳ��Լ���Ҫ�Ĵ�ӡ��
		for (int i = 0; i < services.length; i++) {
			String svcName = services[i].getName();
			list.add(svcName);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0;i<list.size(); i++){
			map.put(String.valueOf(i), list.get(i));
		}
		HttpSession session = request.getSession();
		((Combo)this.getPageElement("Printer")).setValueListForSelect(map);
		if(!session.getAttribute("Printer").toString().equals("")) {
			((Combo)this.getPageElement("Printer")).setValue(session.getAttribute("Printer").toString());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		String param = this.getPageElement("subWinIdBussessId2").getValue();
		
		
		String printers = param.split("&%")[1];
		if(printers.equals("")||printers==null) {
			this.setMainMessage("�õ���û�����Ӵ�ӡ����");
		}
		String[] printerss = printers.split("\\|@\\|");
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < printerss.length; i++) {
			map.put(String.valueOf(i), printerss[i]);
		}
		HttpSession session = request.getSession();
		session.setAttribute("Printer", param.split("&%")[0]);
		((Combo) this.getPageElement("Printer")).setValueListForSelect(map);
		((Combo) this.getPageElement("Printer")).setValue(param.split("&%")[0]);
		this.getPageElement("Number").setValue(session.getAttribute("PrintNum").toString());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("doSet")
	public int doSet(String param) throws RadowException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException {
		HttpSession session = request.getSession();
		String printer = param.split("\\|@\\|")[0];
		String printNum = param.split("\\|@\\|")[1];
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$"); 
		if(pattern.matcher(printNum).matches()&&(!printNum.substring(0, 1).equals("-"))) {
			session.setAttribute("PrintNum", printNum);
			session.setAttribute("Printer", printer);
			this.getExecuteSG().addExecuteCode("setPrinter('"+printer+"')");//�ı�Ĭ�ϴ�ӡ����ֵ
			this.setMainMessage("���óɹ���");
			this.closeCueWindowByYes("printSetWin");
			return EventRtnType.NORMAL_SUCCESS;
		}else {
			this.setMainMessage("��ӡ����������������������ȷ��д��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
	}
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        System.out.println(pattern.matcher("--2").matches());
	}
}
