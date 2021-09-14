package com.insigma.siis.local.pagemodel.dataverify;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class DataContrastResultWinPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		try {
		    String initParams = request.getParameter("initParams");
            String storeid = "";
            String fromUrl = "";//来源网址的地址
            String[] str = initParams.split("\\.");
            if(str.length>=1){
                storeid=str[0];
                if(str.length==2){
                }else if(str.length==3||str.length==4||str.length==5){
                    fromUrl=str.length==4?str[3].replace("@$", ".").replace("@@","&initParams="):"";
                }else if(str.length>=4){
                    throw new RadowException("参数传入有误");
                }
            }
            
            if(storeid == null || "".equals(storeid)){
                storeid = this.getRadow_parent_data();
            }
		    initDataContrastResult(storeid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//初始化对比结果
	public void initDataContrastResult(String storeid){
	    try {
            if(storeid != null && !"".equals(storeid)){
                String sql = "select t.storeid,t.b0101_n,t.b0101_w,t.b0114_n,t.b0114_w"
                        + ",t.comments,t.opptimetype,to_char(t.opptime, 'yyyy-mm-dd hh24:mi:ss') opptime  "
                        + "from TAB_DataContrastResult t where t.storeid ='" + storeid + "' ";
                
                Map<String, Object> paramMap = new HashMap<String, Object>();
                List<HashMap<String, Object>> list = new CommQuery().getListBySQL(sql);
                if(list != null && list.size() > 0){
                    paramMap = list.get(0);
                }else{
                    this.setMessageType(EventMessageType.ERROR);
                    this.setMainMessage("查询信息失败！");
                }
                this.getPageElement("storeid").setValue(paramMap.get("storeid") != null?paramMap.get("storeid").toString():"");//
                this.getPageElement("b0101_n").setValue(paramMap.get("b0101_n") != null?paramMap.get("b0101_n").toString():"");//
                this.getPageElement("b0101_w").setValue(paramMap.get("b0101_w") != null?paramMap.get("b0101_w").toString():"");//
                this.getPageElement("b0114_n").setValue(paramMap.get("b0114_n") != null?paramMap.get("b0114_n").toString():"");//
                this.getPageElement("b0114_w").setValue(paramMap.get("b0114_w") != null?paramMap.get("b0114_w").toString():"");//
                this.getPageElement("comments").setValue(paramMap.get("comments") != null?paramMap.get("comments").toString():"");//
                this.getPageElement("opptimetype").setValue(paramMap.get("opptimetype") != null?paramMap.get("opptimetype").toString():"");//
                this.getPageElement("opptime").setValue(paramMap.get("opptime") != null?paramMap.get("opptime").toString():"");//

            } 
        } catch (Exception e) {
               e.printStackTrace();
        }
	}
	
	
	
	
}
