package com.insigma.siis.local.pagemodel.customquery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class PeoplePortraitPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000Str").getValue();
		A01 dqA01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		this.getPageElement("legendData").setValue("[\""+dqA01.getA0101().toString()+"\"]");
		StringBuffer data = new StringBuffer(dqA01.getA0101().toString()+"��");//����
		if(dqA01.getA0104()!=null){
			data.append(initQuery("GB2261",dqA01.getA0104().toString()) + "��");//�Ա�
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		this.getPageElement("a0107StrMin").setValue(""+(Integer.parseInt(sdf.format(new Date()))-70)+"01");
		this.getPageElement("a0107StrMax").setValue(sdf.format(new Date())+"12");//6λ��ֵ
		if(dqA01.getA0107()!=null){
			String reg = "^[0-9]{6}$";
			String reg2 = "^[0-9]{8}$";
			if(dqA01.getA0107().toString().matches(reg) || dqA01.getA0107().toString().matches(reg2)){
				this.getPageElement("a0107StrMin").setValue(""+(Integer.parseInt(dqA01.getA0107().toString().substring(0,4))+18)+"01");
				data.append(initTime(dqA01.getA0107().toString(),1)+ "������");//��������
			}
		}
		if(dqA01.getA0117()!=null){
			data.append(initQuery("GB3304",dqA01.getA0117().toString()) + "��");//����
		}
		if(dqA01.getA0111()!=null){
			data.append(initQuery("ZB01",dqA01.getA0111().toString())+ "�ˣ�");//����
		}
		if(dqA01.getA0140()!=null){
			data.append(initTime(dqA01.getA0140().toString(),1)+ "�뵳��");//�뵳ʱ��
		}
		if(dqA01.getA0134()!=null){
			data.append(initTime(dqA01.getA0134().toString(),1)+ "�μӹ�����");//�μӹ���ʱ��
		}
		
		String zgxl = HBUtil.getValueFromTab("A0801A", "A08", "A0834 = '1' and a0000 = '"+a0000+"'");
		if(zgxl != null){
			data.append( zgxl.toString()+"ѧ��");//���ѧ��
		}
		
		String zgxw = HBUtil.getValueFromTab("A0901A", "A08", "A0835 = '1' and a0000 = '"+a0000+"'");
		if(zgxw != null){
			zgxw = zgxw.toString().replace("ѧλ", "");
			data.append(zgxw.toString() + "��");//���ѧλ
		}else{
			data.append("");
		}
		
		
		String zyjsStr = "";
		if(dqA01.getA0196() != null && !"".equals(dqA01.getA0196())){
			zyjsStr = dqA01.getA0196().toString();
		}
		zyjsStr = zyjsStr.replace(" ", "");
		if(zyjsStr != null && !zyjsStr.equals("")){
			data.append( zyjsStr.toString() + "��");//רҵ����ְ��
		}
		String dataJlStr = data.toString().substring(0, data.length()-1) + "��";         //������Ϣ
		
		String dataXzStr = "";
		String zw = "";
		if(dqA01.getA0192a() != null && !"".equals(dqA01.getA0192a())){
			zw = dqA01.getA0192a().toString();
		}
		zw = zw.replace(" ", "");
		if(zw!=null && !zw.equals("")){
			dataXzStr="����"+ zw.toString() + "��";//������λ��ְ��
		}else{
			dataXzStr="��ǰ��ְ��";
		}
		String dataJcStr = "";
		if(dqA01.getA14z101() != null && !"".equals(dqA01.getA14z101())&& !"��".equals(dqA01.getA14z101())){
			dataJcStr = dqA01.getA14z101().toString().replace("��", "<p></p>").replace("��", "<p></p>");//������Ϣ
		}
		
		String dataJyxlStr = "";     //��������
		String dataJyxwStr = "";     //��������
		if(dqA01.getQrzxl() != null && !"".equals(dqA01.getQrzxl())&& !"��".equals(dqA01.getQrzxl())){
			if(dqA01.getQrzxlxx() != null && !"".equals(dqA01.getQrzxlxx())&& !"��".equals(dqA01.getQrzxlxx())){
				if(dqA01.getQrzxw() != null && !"".equals(dqA01.getQrzxw())&& !"��".equals(dqA01.getQrzxw())){
					if(dqA01.getQrzxwxx() != null && !"".equals(dqA01.getQrzxwxx())&& !"��".equals(dqA01.getQrzxwxx())){
						String dataJyStr1 = dqA01.getQrzxl().toString().replace("��", "<p></p>").replace("��", "<p></p>");//��������
						String dataJyStr2 = dqA01.getQrzxlxx().toString().replace("��", "<p></p>").replace("��", "<p></p>");//��������
						String dataJyStr3 = dqA01.getQrzxw().toString().replace("��", "<p></p>").replace("��", "<p></p>");//��������
						String dataJyStr4 = dqA01.getQrzxwxx().toString().replace("��", "<p></p>").replace("��", "<p></p>");//��������
						
						//dataJyStr = "ѧ��:"+dataJyStr1+","+dataJyStr2+";<br>ѧλ:"+dataJyStr3+","+dataJyStr4;
						dataJyxlStr = "ѧ��:   "+dataJyStr1+","+dataJyStr2;
						dataJyxwStr = "ѧλ:   "+dataJyStr3+","+dataJyStr4;
					}
				}
				
			}
			
		}
		String dataChStr = "";
		if(dqA01.getA14z101() != null && !"".equals(dqA01.getA14z101())&& !"��".equals(dqA01.getA14z101())){
			dataChStr = dqA01.getA15z101().toString().replace("��", "<p></p>").replace("��", "<p></p>");//������Ϣ
		}
		// ��ȡƴ���ַ���
		String jsonData = getJsonData(a0000);
		System.out.println(jsonData);
		this.getPageElement("jsonData").setValue(jsonData);
		String jsonSeries = getJsonSeries(a0000,dqA01.getA0101().toString());
		System.out.println(jsonSeries);
		this.getPageElement("jsonSeries").setValue(jsonSeries);
		this.getExecuteSG().addExecuteCode("insertInfo('"+dataJlStr+"','"+dataXzStr+"','"+dataJcStr+"','"+dataChStr+"','"+dataJyxlStr+"','"+dataJyxwStr+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//��ѯ���ֶ�����Ϣ
	public static String initQuery(String code_type,String code_value){
		String str = "";
		try {
			str = HBUtil.getValueFromTab("code_name", "code_value", " code_type='"+code_type+"' and code_value='"+code_value+"'");
		} catch (AppException e) {
			e.printStackTrace();
		}
		return str;
	}
	//��ȡ��ɫ
	public static String getColor(){
		String r,g,b;  
		Random random = new Random();  
		r = Integer.toHexString(random.nextInt(256)).toUpperCase();  
		g = Integer.toHexString(random.nextInt(256)).toUpperCase();  
		b = Integer.toHexString(random.nextInt(256)).toUpperCase();  
		     
		r = r.length()==1 ? "0" + r : r ;  
		g = g.length()==1 ? "0" + g : g ;  
		b = b.length()==1 ? "0" + b : b ;  
		     
		return "#"+r+g+b;
	}
	//ƴ��ҳ��Json_data�ַ��� -- data
	public String getJsonData(String a0000){
		StringBuffer str = new StringBuffer();
		String string = "";
		HashMap<String,Object> map = new HashMap<String,Object>();
		/*�ж��Ƿ���ڵ�ǰ�ֶ���Ϣ*/
		/*{
			name: "�ش�����ְ",
			value: [4, 198801, 199002, "zj", "1988.01", "1990.02", "�ش�����ְ"],
			itemStyle: {
				normal: {
					color: "#97b552"
				}
			}
		}*/
		//�ж��Ƿ���ְ��
		String sqlZJ = "select c.code_name,a.a0501b,a.a0504,a.a0517 from a05 a,code_value c WHERE a.a0501b = c.code_value AND c.code_type = 'ZB148' AND a.a0000 = '"+a0000+"' ORDER BY a.a0504 ASC";
		List<HashMap<String,Object>> queryZJ = query(sqlZJ);
		if(queryZJ.size()==0){
			System.out.println("������Ϣ��ʾ����ְ����Ϣ��");
		}else{
			for (int i = 0; i < queryZJ.size(); i++) {
				map = queryZJ.get(i);
				if(map.get("a0504")==null||map.get("a0501b")==null){//ְ��������׼���ڱ���ͬ�´���
					continue;
				}
				str.append("{name:\""+map.get("code_name").toString()+"\",")
				.append("value:[4,")
				.append(getBJTime(map.get("a0504"),6)+",").append(getBJTime(map.get("a0517"),6)+",\"zj\",")
				.append("\""+initTime(getBJTime(map.get("a0504"),6),1)+"\",").append("\""+initTime(getBJTime(map.get("a0517"),6),1)+"\",")
				.append("\""+map.get("code_name").toString()+"\"],")
				.append("itemStyle:{normal:{color:\""+getColor()+"\"}}},");
			}
		}
		/*{
		name: "��ְ",
		value: [3, 200801, 200901, "ndkh", "2008", "��ְ"],
		itemStyle: {
			normal: {
				color: "#2ec7c9"
			}
		}
		}*/
		//�ж��Ƿ�����ȿ���
		String sqlNDKH = "select a.a1521,a.a1517,c.code_name from a15 a, code_value c where a.a1517 = c.code_value and c.code_type = 'ZB18' and a.a0000 = '"+a0000+"' order by a.a1521 ASC";
		List<HashMap<String,Object>> queryNDKH = query(sqlNDKH);
		if(queryNDKH.size()==0){
			System.out.println("������Ϣ��ʾ������ȿ�����Ϣ��");
		}else{
			for (int i = 0; i < queryNDKH.size(); i++) {
				map = queryNDKH.get(i);
				if(map.get("a1521")==null||map.get("a1517")==null){//���˽����뿼����ȱ���ͬ�´���
					continue;
				}
				str.append("{name:\""+map.get("code_name").toString()+"\",")
				.append("value:[3,")
				.append(getBJTime(map.get("a1521"),4)+"01,").append(addyear1(getBJTime(map.get("a1521"),4),1)+"01,\"ndkh\",")
				.append("\""+getBJTime(map.get("a1521"),4)+"\",")
				.append("\""+map.get("code_name").toString()+"\"],")
				.append("itemStyle:{normal:{color:\""+getColor()+"\"}}},");
			}
		}
		/*{
			name: "��ѧ",
			value: [2, 197809, 198206, "jy", "1978.09", "1982.06", "���人����ѧԺ�����л����� ѧϰ����ô�ѧѧλ"],
			itemStyle: {
				normal: {
					color: "#b6a2de"
				}
			}
		}*/
		//�ж��Ƿ��н�����Ϣ
		String sqlJY = "select a0801a,a0804,a0807,a0814,a0901a from a08 where a0000 = '"+a0000+"' order by a0804 ASC";
		List<HashMap<String,Object>> queryJY = query(sqlJY);
		if(queryJY.size()==0){
			System.out.println("������Ϣ��ʾ���޽�����Ϣ��");
		}else{
			for (int i = 0; i < queryJY.size(); i++) {
				map = queryJY.get(i);
				if(map.get("a0801a")==null||map.get("a0804")==null||map.get("a0814")==null){//ѧ����������ѧʱ����ѧУ���Ʊ���ͬʱ����
					continue;
				}
				String strxw;
				if(map.get("a0901a")==null){
					strxw="";
				}else{
					strxw="�����"+map.get("a0901a").toString()+"ѧλ";
				}
				str.append("{name:\""+map.get("a0801a").toString()+"\",")
				.append("value:[2,")
				.append(getBJTime(map.get("a0804"),6)+",").append(getBJTime(map.get("a0807"),6)+",\"jy\",")
				.append("\""+initTime(getBJTime(map.get("a0804"),6),1)+"\",").append("\""+initTime(getBJTime(map.get("a0807"),6),1)+"\",")
				.append("\"��"+map.get("a0814").toString()+"ѧϰ"+strxw+"\"],")
				.append("itemStyle:{normal:{color:\""+getColor()+"\"}}},");
			}
		}
		/*{
			name: "1",
			value: [0, 198601, 198602, "jc", "1986.01", "���������Ȼݶ�ũ��1985����Ƚ�������"],
			itemStyle: {
				normal: {
					color: "#2ec7c9"
				}
			}
		}*/
		//�ж��Ƿ��н�����Ϣ
		String sqlJC = "select a1407,a1404a from a14 where a0000 = '"+a0000+"' order by a1407 asc";
		List<HashMap<String,Object>> queryJC = query(sqlJC);
		if(queryJC.size()==0){
			System.out.println("������Ϣ��ʾ���޽�����Ϣ��");
		}else{
			for (int i = 0; i < queryJC.size(); i++) {
				map = queryJC.get(i);
				if(map.get("a1407")==null||map.get("a1404a")==null){//����ʱ���뽱�����Ʊ���ͬʱ����
					continue;
				}
				str.append("{name:\"1\",")
				.append("value:[0,")
				.append(getBJTime(map.get("a1407"),6)+",").append(addyear1(getBJTime(map.get("a1407"),6),2)+",\"jc\",")
				.append("\""+initTime(getBJTime(map.get("a1407"),6),1)+"\",")
				.append("\""+map.get("a1404a").toString()+"\"],")
				.append("itemStyle:{normal:{color:\""+getColor()+"\"}}},");
			}
		}
		/*{
			name: "������ѵ������",
			value: [1,201512,201512,"px","2015.12.01","2015.12.31","�μӲ������쵥λ������ѵ������"],
			itemStyle: {
				normal: {
					color: "#07a2a4"
				}
			}
		}*/
		//�ж��Ƿ�����ѵ��Ϣ
		String sqlPX = "select a1107,a1111,a1114,a1131 from a11 where a0000='"+a0000+"' order by a1107 asc";
		List<HashMap<String,Object>> queryPX = query(sqlPX);
		if(queryPX.size()==0){
			System.out.println("������Ϣ��ʾ������ѵ��Ϣ��");
		}else{
			for (int i = 0; i < queryPX.size(); i++) {
				map = queryPX.get(i);
				if(map.get("a1107")==null||map.get("a1111")==null||map.get("a1114")==null||map.get("a1131")==null){//��ѵ��ʼʱ�������ʱ������ѵ��������ѵ��λ����ͬʱ����
					continue;
				}
				str.append("{name:\""+map.get("a1114").toString()+"\",")
				.append("value:[1,")
				.append(getBJTime(map.get("a1107"),6)+",").append(getBJTime(map.get("a1111"),6)+",\"px\",")
				.append("\""+initTime(getBJTime(map.get("a1107"),8),2)+"\",").append("\""+initTime(getBJTime(map.get("a1111"),8),2)+"\",")
				.append("\""+map.get("a1131").toString()+"\"],")
				.append("itemStyle:{normal:{color:\""+getColor()+"\"}}},");
			}
		}
		string = str.toString();
		string = string.substring(0, string.length()-1);
		System.out.println("�������"+string);
		return "["+string+"]";
	}
	//ƴ��ҳ��Json_series�ַ��� -- series
	public String getJsonSeries(String a0000,String a0101) throws RadowException{
		StringBuffer str = new StringBuffer();
		/*[{
			name: "3882w",
			type: "line",
			data: [
				["1996-2", "�����쵼"],
				["2008-1", "�����쵼"],
				["2008-1", "�����쵼"],
				["2008-1", "�����쵼"],
				["2008-1", "�����쵼"],
				["2008-1", "�����쵼"],
				["2008-11", "�����쵼"]
			]
		}]*/
		String string = "";
		StringBuffer zj = new StringBuffer();
		String sqlPX = "select c.code_name,a.a0504 from a05 a,code_value c WHERE a.a0501b = c.code_value AND c.code_type = 'ZB148' and a0000='"+a0000+"' order by a0504 asc";
		List<HashMap<String,Object>> queryZWCC = query(sqlPX);
		if(queryZWCC.size()==0){
			System.out.println("������Ϣ��ʾ����ְ������Ϣ��");
		}else{
			str.append("{name:\""+a0101+"\",").append("type:\"line\",data: [");
			for (int i = 0; i < queryZWCC.size(); i++) {
				HashMap<String,Object> map = queryZWCC.get(i);
				if(map.get("code_name")==null||map.get("a0504")==null){//ְ������������׼ʱ��ͬʱ����
					continue;
				}
				str.append("[")
				.append("\""+initTime(getBJTime(map.get("a0504"),6),1).replace(".0", "-").replace(".", "-")+"\",")
				//.append("\""+map.get("code_name")+"�쵼\"")
				.append("\""+map.get("code_name")+"\"")
				.append("],");
				//zj=initTime(getBJTime(map.get("a0504"),6),1)+map.get("code_name")+"�쵼";
				
				zj.append(initTime(getBJTime(map.get("a0504"),6),1)+map.get("code_name"));
				zj.append("<br>");
			}
			String  zjjl = zj.toString();
			this.getPageElement("zj").setValue(zjjl);
			System.out.println(zjjl);
			string = str.toString();
			string = string.substring(0, string.length()-1)+"]}";
		}
		return "["+string+"]";
	}
	//��ѯ���ֶ�����Ϣ
	public static List<HashMap<String,Object>> query(String sql){
		CommQuery query = new CommQuery();
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		try {
			list = query.getListBySQL(sql);
			return list;
		} catch (AppException e) {
			e.printStackTrace();
		}
		return null;
	}
	//��ȡ��׼ʱ�� -4 ��ȡ��� -6��ȡ����ʱ�� -8��ȡ����ʱ��
	public String getBJTime(Object time,int n){
		if(time==null){
			return "";
		}
		String timecc = time.toString().trim();
		if(timecc.length()==8){
			if(n==4){
				return timecc.substring(0, 4);
			}else if(n==6){
				return timecc.substring(0, 6);
			}else {
				return timecc;
			}
		}else if(timecc.length()==6){
			if(n==4){
				return timecc.substring(0, 4);
			}else if(n==6){
				return timecc;
			}else {
				return timecc+"01";
			}
		}else if(timecc.length()==4){
			if(n==4){
				return timecc;
			}else if(n==6){
				return timecc+"01";
			}else {
				return timecc+"0101";
			}
		}else{
			return "";
		}
	}
	//��ȡʱ���� n=1���+1;n=2�·�+1;n=3�շ�+1;
	public String addyear1(Object time,int n){
		if(time==null){
			return "";
		}
		int timenum = 0;
		String timecc = time.toString().trim();
		if(timecc.length()==4){
			if(n==1){
				timenum = Integer.parseInt(timecc)+1;
				return timenum+"";
			}else{
				return "";
			}
		}else if(timecc.length()==6){
			if(n==1){
				timecc = timecc.substring(0, 4);
				timenum = Integer.parseInt(timecc)+1;
				return timenum+"";
			}else if(n==2){
				timecc = timecc.substring(0, 6);
				if(timecc.substring(4, 6).equals("11")){//�ж�12�³��֣����+1
					timecc = timecc.substring(0, 4);
					timenum = Integer.parseInt(timecc)+1;
					return timenum+"01";
				}else{
					timenum = Integer.parseInt(timecc)+1;
				}
				return timenum+"";
			}else{
				return "";
			}
		}else if(timecc.length()==8){
			if(n==1){
				timecc = timecc.substring(0, 4);
				timenum = Integer.parseInt(timecc)+1;
				return timenum+"";
			}else if(n==2){
				timecc = timecc.substring(0, 6);
				if(timecc.substring(4, 6).equals("11")){//�ж�12�³��֣����+1
					timecc = timecc.substring(0, 4);
					timenum = Integer.parseInt(timecc)+1;
					return timenum+"01";
				}else{
					timenum = Integer.parseInt(timecc)+1;
				}
				return timenum+"";
			}else if(n==3){//���ﲻ��Ҫ��ʡ�Բ�д
				return timenum+"";
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	//��ʱ���ַ���תΪn=1Ϊxxxx.xx ;n=2Ϊxxxx.xx  
	public String initTime(String timeStr,int n){
		if(timeStr==null){
			return "";
		}
		timeStr = timeStr.trim();
		if(timeStr.length()==4){
			if(n==1){
				return timeStr.substring(0,4)+".01";
			}else if(n==2){
				return timeStr.substring(0,4)+".01.01";
			}else{
				return "";
			}
		}else if(timeStr.length()==6){
			if(n==1){
				return timeStr.substring(0,4)+"."+timeStr.substring(4,6);
			}else if(n==2){
				return timeStr.substring(0,4)+"."+timeStr.substring(4,6)+".01";
			}else{
				return "";
			}
		}else if(timeStr.length()==8){
			if(n==1){
				return timeStr.substring(0,4)+"."+timeStr.substring(4,6);
			}else if(n==2){
				return timeStr.substring(0,4)+"."+timeStr.substring(4,6)+"."+timeStr.substring(6,8);
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
}
