package com.insigma.siis.local.pagemodel.statisticalanalysis.simpleanalysis;


import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;


public class TwoDStatisticsShowPageModel extends PageModel {
	public static String table_data_str = "";
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		//String param = this.getPageElement("subWinIdBussessId2").getValue();
		//if(param.contains("fixed_query")){
		//	this.setNextEventName("fixed_query");
		//}else{
		table_data_str = "";
		this.setNextEventName("query");
		//}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("query")
	public int doquery() throws RadowException{	
		table_data_str = "";
		String str = this.getPageElement("subWinIdBussessId2").getValue();
		table_data_str = str;
		String tran_name = "['";
		String vert_name = "[";
		String ld_tran_name = "[";
		String[] vert_name_arr = null;
		String[] tran_name_arr = null;
		String[] data_arr = null;
		Integer tran_num = null;
		Integer vert_num = null;
		String data_str = "";
		String[] data = null;
		String qxdata = "[";
		String zzdata = "[";
		String lddata = "[";
		Integer maxvalue = null;
        if(str.contains("$")){
        	//分割处理数据串
        	data = str.split("\\$");
        	String tran_name_str = data[0];
        	tran_num = Integer.parseInt(data[1]);
        	Integer tran_length = tran_num ;
        	this.getPageElement("tran_length").setValue(tran_length.toString());	//设置表单横轴长度
    		String vert_name_str = data[2];
    		vert_num =Integer.parseInt(data[3]);
    		Integer vert_length = vert_num ;
    		this.getPageElement("vert_length").setValue(vert_length.toString());	//设置表单竖轴长度
    		data_str = data[4];
    		maxvalue = Integer.parseInt(data[5]);
    		vert_name_arr = vert_name_str.split("@");
    		tran_name_arr = tran_name_str.split("@");
    		data_arr = data_str.split("#");
    		for(int i=0;i<vert_num;i++){
    			vert_name += "'"+vert_name_arr[i]+"',";
    			qxdata += "{name:'"+vert_name_arr[i]+"',type:'line',data:["+data_arr[i].replace("@", ",")+"]},";
    			zzdata += "{name:'"+vert_name_arr[i]+"',type:'bar',data:["+data_arr[i].replace("@", ",")+"],"
    					+ "itemStyle:{normal:{label:{show: true,'position': 'top'}}}},";
    			lddata += "{value:["+data_arr[i].replace("@", ",")+"],name:'"+vert_name_arr[i]+"'},";
    			this.getPageElement("vert"+i).setValue(vert_name_arr[i]);
    			for(int j=0;j<tran_num;j++){
    				String[] data_add = data_arr[i].split("@");
    				this.getPageElement("vert"+i+"_tran"+j).setValue(data_add[j]);
    			}
    		}  
    		for(int i=0;i<tran_num;i++){
    			ld_tran_name += "{name:'"+tran_name_arr[i]+"',max:"+maxvalue+"},";
    			this.getPageElement("tran"+i).setValue(tran_name_arr[i]);
    		}
    		tran_name += tran_name_str.replace("@", "','")+"']";
    		vert_name = vert_name.substring(0,vert_name.length()-1)+"]";
    		ld_tran_name = ld_tran_name.substring(0,ld_tran_name.length()-1)+"]";
    		qxdata = qxdata.substring(0,qxdata.length()-1)+"]";
    		zzdata = zzdata.substring(0,zzdata.length()-1)+"]";
    		lddata = lddata.substring(0,lddata.length()-1)+"]";
    		
        }
        this.getPageElement("tran_name").setValue(tran_name);
        this.getPageElement("vert_name").setValue(vert_name);
        this.getPageElement("qxdata").setValue(qxdata);
        this.getPageElement("zzdata").setValue(zzdata);
        this.getPageElement("lddata").setValue(lddata);
        this.getPageElement("ld_tran_name").setValue(ld_tran_name);
        
        this.getExecuteSG().addExecuteCode("def();");
        this.getExecuteSG().addExecuteCode("data_show();");
        this.getExecuteSG().addExecuteCode("createEwTable();");
		this.getExecuteSG().addExecuteCode("setQuxian();");
		this.getExecuteSG().addExecuteCode("setZhuzhuang();");
		this.getExecuteSG().addExecuteCode("setLeida();");	
		this.getExecuteSG().addExecuteCode("loding();");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
