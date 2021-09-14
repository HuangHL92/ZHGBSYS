package com.insigma.siis.local.pagemodel.sysorg.org;





import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.sysorg.org.StatisticsWinBS;
import com.insigma.siis.local.pagemodel.exportexcel.ExportPhotoExcel;
import com.insigma.siis.local.pagemodel.exportexcel.FiledownServlet;
import com.insigma.siis.local.pagemodel.statisticalanalysis.simpleanalysis.GeneralStatisticsPageModel;


public class SimpleStatisticsPageModel extends PageModel {
	
	public static String strarr = "";

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException{
		//String groupid = this.getPageElement("subWinIdBussessId").getValue();
		this.setNextEventName("query");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//��ѯ������ʾ��ҳ����
	@PageEvent("query")
	public int doquery() throws RadowException{	
		HBSession sess = HBUtil.getHBSession();
		String Str = this.getPageElement("subWinIdBussessId2").getValue();
		this.getPageElement("Str").setValue(Str);
		strarr = Str;
		String[] data = null;
		String groupid = "";
		String queryid = "";
		String[] tjname = null;
		String[] number = null;
		String quxian = "";
		String bing = "";
		String zhuzhuang = "";
		String leida = "";
		String arr1 = "";
		String valuebing = "";
		String nametu = "";
    	String valuetu = "";
    	String indicator = "";
    	String valueleida = "";
        if(Str.contains("@")){
        	String allname = "";
        	for(int i=0;i<GeneralStatisticsPageModel.query_condition.size();i++){
        		allname += (String) GeneralStatisticsPageModel.query_condition.get(i).get("Queryname")+",";
        	}
        	data = Str.split("@");
        	
        	if (data !=null && data.length > 0) {
        	
        	number = data[0].split(",");//����
        	tjname = allname.split(",");//����
        	Integer max=Integer.valueOf(number[0]);
    		int temp;
    		for(int i=0;i<number.length;i++){
    			temp = Integer.valueOf(number[i]);
    			if(temp>max){
    				max = temp;
    			}	
    		}
    		String maxValue = max.toString();
        	arr1="{'tname':[";
        	int sum = 0;
        	int length = 0;
        	for(int i=0;i<tjname.length;i++){
        		nametu += "'"+tjname[i]+"',";
        		valuetu += number[i]+",";
        		valuebing += "{value:"+number[i]+",name:'"+tjname[i]+"'},";
        		indicator += "{name:'"+tjname[i]+"',max:"+maxValue+"},";
        		arr1 += "{'name':'"+tjname[i]+"','number':"+number[i]+"},";
        		sum += Integer.parseInt(number[i]);
        	}
        	nametu = "["+nametu.substring(0, nametu.length()-1)+"]";
        	valuetu = "["+valuetu.substring(0, valuetu.length()-1)+"]";
        	valuebing = "["+valuebing.substring(0, valuebing.length()-1)+"]";
        	indicator = "["+indicator.substring(0, indicator.length()-1)+"]";
        	valueleida = "[{value:"+valuetu+"}]";
        	length = tjname.length+1;
        	arr1 += "{'name':'�ϼ�','number':"+sum+"}],'length':"+length+"}";       	
        	}	
        }else{
        	data = Str.split("\\$");
        	if (data !=null && data.length > 0) {
        	groupid = data[0];
    		queryid = data[1];
    		//String queryid = "" ;		//this.getRadow_parent_data();
    		//String groupid = "001.001";         //this.getRadow_parent_data();		
    		Object[] sq = StatisticsWinBS.querySQ(queryid);
    		String sq002= sq[0].toString(); //ͳ������
    		String sq003= sq[1].toString(); //��ͳ����
    		String sq004= sq[2].toString(); //ͳ�����
    		int sum = 0;
    		int length = 0;
    		List<Object> b=new ArrayList<Object>();
    		String name=null;
    		String maxvalue=null;  
    		arr1="{'tname':[";
    		String[] n_arr = null;
    		if(!sq003.equals("3")){
    			if("1".equals(queryid)){
    				String[] arr = {"Ů��","��������","���й���Ա"};
    				n_arr = arr;
    			}else if("2".equals(queryid)){
    				String[] arr = {"��ʿ","˶ʿ","ѧʿ"};
    				n_arr = arr;
    			}else if("3".equals(queryid)){
    				String[] arr = {"�о���","��ѧ����","��ѧ��ר","��ר","���м�����","δ��д"};
    				n_arr = arr;
    			}else if("4".equals(queryid)){
    				String[] arr = {"30�꼰����","31����35��","36����40��","41����45��","46����50��","51����54��","55����59��","60�꼰����"};
    				n_arr = arr;
    			}else if("5".equals(queryid)){
    				String[] arr = {"����","�ɹ���","����","����","ά�����","׳��","����"};
    				n_arr = arr;
    			}else if("9".equals(queryid)){
    				String[] arr = {"���Ҽ���ְ","���Ҽ���ְ","ʡ������ְ","ʡ������ְ","���ּ���ְ","���ּ���ְ","�ش�����ְ","�ش�����ְ","��Ƽ���ְ","��Ƽ���ְ","��Ա","����Ա","��������Ա","����"};
    				n_arr = arr;
    			}else if("10".equals(queryid)){
    				String[] arr = {"����һ��","�������ڸ���","ʡ����һ��","ʡ�������ڸ���","��ʡ����һ��","��ʡ�������ڸ���","��(�ء��ݡ���)","��(�С�������)","��(��)"};
    				n_arr = arr;
    			}else if("11".equals(queryid)){
    				String[] arr = {"����","Ů��"}; 
    				n_arr = arr;
    			}
    			Object[] cyqc = StatisticsWinBS.queryCYQC(queryid);
    			String sql = cyqc[1].toString();
    			String v = "";
    			String n = "";
    			Object[] v_arr = null;
    			if("10".equals(queryid)){
    				v_arr = StatisticsWinBS.querySQL10(sql);
				}else{
					v_arr = StatisticsWinBS.querySQL(sql,groupid);
				}
    			for(int i = 0; i < v_arr.length; i++){
    				v = v_arr[i].toString();
    				n = n_arr[i];
    				b.add(v);
        			sum += Integer.parseInt(v);
        			//��ͼ����
        			valuebing += "{value:"+v+",name:'"+n+"'},";
        			//��״ͼ,����ͼ����
        			nametu += "'"+n+"',";
        			valuetu += v+",";
        			//�״�ͼ����	
        			indicator += "{name:'"+n+"',max:maxvalue},";
        			valueleida += v+",";
        			arr1 += "{'name':'"+n+"','number':"+v+"},";
    			}
    			length = v_arr.length+1;
    		}else{
    			List<Object[]> qc = StatisticsWinBS.queryQC(queryid);
        		for(int j=0;j<qc.size();j++){
        			Object[] oo = qc.get(j);
        			String n = oo[0].toString();
        			String sql = oo[1].toString();
        			String v = "";  			
    				sql = sql.replace("|", "'")+groupid.replace("|", "'");
    				if(DBUtil.getDBType() == DBType.ORACLE){
    					v = sess.createSQLQuery("select count(1) from ( "+sql+" )").uniqueResult().toString();
    				}else{
    					v = sess.createSQLQuery("select count(1) from ( "+sql+" ) as t").uniqueResult().toString();
    				}
        			b.add(v);
        			sum += Integer.parseInt(v);
        			//��ͼ����
        			valuebing += "{value:"+v+",name:'"+n+"'},";
        			//��״ͼ,����ͼ����
        			nametu += "'"+n+"',";
        			valuetu += v+",";
        			//�״�ͼ����	
        			indicator += "{name:'"+n+"',max:maxvalue},";
        			valueleida += v+",";
        			arr1 += "{'name':'"+n+"','number':"+v+"},";
        			length = qc.size()+1;
        		}
    		}
    		
    		maxvalue = StatisticsWinBS.max(b);
    		nametu = "["+nametu.substring(0, nametu.length()-1)+"]";
    		valuetu = "["+valuetu.substring(0, valuetu.length()-1)+"]";
    		valuebing = "["+valuebing.substring(0, valuebing.length()-1)+"]";		    		
    		indicator = indicator.replaceAll("maxvalue", maxvalue);
    		indicator = "["+indicator.substring(0, indicator.length()-1)+"]";
    		valueleida = "[{value:"+valuetu+"}]";
    		
    		//ͨ�ò���
    		name = sq002;		
//    		nametu = nametu.substring(0, nametu.length()-1)+"]";
//    		valuetu = valuetu.substring(0, valuetu.length()-1)+"]";
//    		valueleida = valueleida.substring(0, valueleida.length()-1)+"}]";
    		arr1 += "{'name':'�ϼ�','number':"+sum+"}],'length':"+length+"}";
    		
        	}
    		
        }
        this.getPageElement("nametu").setValue(nametu);        
        this.getPageElement("valuetu").setValue(valuetu);
        this.getPageElement("valuebing").setValue(valuebing);
        this.getPageElement("indicator").setValue(indicator);
        this.getPageElement("valueleida").setValue(valueleida);
        this.getPageElement("arr1").setValue(arr1);
        
        this.getExecuteSG().addExecuteCode("def();");
        this.getExecuteSG().addExecuteCode("setBing();");
		this.getExecuteSG().addExecuteCode("setQuxian();");
		this.getExecuteSG().addExecuteCode("setZhuzhuang();");
		this.getExecuteSG().addExecuteCode("setLeida();");	
		this.getExecuteSG().addExecuteCode("createTyTable();");
		this.getExecuteSG().addExecuteCode("loding();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("export")
	public int export() throws RadowException{
		String Str = this.getPageElement("Str").getValue();
		if(DBUtil.getDBType()==DBType.ORACLE){
		}else{
			Str = Str.replace("%", "%25"); //mysql���⴦��%�ַ�.
		}
		this.getExecuteSG().addExecuteCode("autosubmit('"+Str+"');");
		return EventRtnType.NORMAL_SUCCESS;
		
	}

}