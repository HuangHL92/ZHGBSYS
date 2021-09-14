package com.insigma.siis.local.pagemodel.xbrm;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;
import com.insigma.siis.local.pagemodel.xbrm.tpl.ExcelStyleUtil;

public class ZSYSPageModel extends PageModel{
	
	JSGLBS bs = new JSGLBS();

	@Override
	public int doInit() throws RadowException {
        this.setNextEventName("initX");
		
		//设置下拉框
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException, Exception{
		String rbid = this.getPageElement("rbId").getValue();
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		stmt = session.connection().createStatement();
        
		rs = stmt.executeQuery("select j.*,b.b0101 from js12 j, b01 b where j.js1201=b.b0111 and rb_id = '"+rbid+"'");
		int i=1;
		while(rs.next()){
			String js1201 = rs.getString("js1201");
			String b0101 = rs.getString("b0101");
			String js1202 = rs.getString("js1202");
			String js1203 = rs.getString("js1203");
			String js1204 = rs.getString("js1204");
			String js1205 = rs.getString("js1205");
			String js1206 = rs.getString("js1206");
			String js1207 = rs.getString("js1207");
			String js1208 = rs.getString("js1208");
			String js1209 = rs.getString("js1209");
			String js1210 = rs.getString("js1210");
			String js1211 = rs.getString("js1211");
			String js1212 = rs.getString("js1212");
			String js1213 = rs.getString("js1213");
			String js1214 = rs.getString("js1214");
			this.getPageElement("js1201_"+i).setValue(js1201);
			this.getPageElement("js1201_"+i).setValue(b0101);
			this.getPageElement("js1202_"+i).setValue(js1202);
			this.getPageElement("js1203_"+i).setValue(js1203);
			this.getPageElement("js1204_"+i).setValue(js1204);
			this.getPageElement("js1205_"+i).setValue(js1205);
			this.getPageElement("js1206_"+i).setValue(js1206);
			this.getPageElement("js1207_"+i).setValue(js1207);
			this.getPageElement("js1208_"+i).setValue(js1208);
			this.getPageElement("js1209_"+i).setValue(js1209);
			this.getPageElement("js1210_"+i).setValue(js1210);
			this.getPageElement("js1211_"+i).setValue(js1211);
			this.getPageElement("js1212_"+i).setValue(js1212);
			this.getPageElement("js1213_"+i).setValue(js1213);
			this.getPageElement("js1214").setValue(js1214);
			i++;
		}
		if(i>1){
			i=i-1;//有数据则显示总的数据条数
		}else{
			i=5;//没有数据则显示5条空的数据
		}
		this.getPageElement("block").setValue(i+"");
		this.getExecuteSG().addExecuteCode("showArow();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 信息导出
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("ExpGird")
	public int ExpGird(String param) throws RadowException{
		QCJSFileExportBS fileBS = new QCJSFileExportBS();
		//String rbId = this.getPageElement("rbId").getValue();			
		String rbId = this.request.getParameter("rbId");//批次
		String buttontext = this.request.getParameter("buttontext");
		
		this.request.setAttribute("rbId", rbId);
		
		
		
		
		fileBS.setPm(this);
		fileBS.setSheetName(buttontext);
		fileBS.setOutputpath(JSGLBS.HZBPATH+"zhgboutputfiles/"+rbId+"/");
		File f = new File(fileBS.getOutputpath());
		
		if(f.isDirectory()){//先清空输出目录
			JSGLBS.deleteDirectory(fileBS.getOutputpath());
		}
		try {
			fileBS.exportZSYS();
			//fileBS.exportJYRX();
		} catch (Exception e) {
			this.setMainMessage(e.getMessage());
			e.printStackTrace();
		}
		
		
		List<String> fns = fileBS.getOutputFileNameList();
		String downloadPath = "";
		String downloadName = "";
		if(fns.size()==1){//一个文件直接下载
			downloadPath = fileBS.getOutputpath()+fns.get(0);
			downloadName = fns.get(0);
		}else if(fns.size()>1){//压缩
			downloadPath = fileBS.getOutputpath()+fileBS.getSheetName()+".zip";
			downloadName = fileBS.getSheetName()+".zip";
			Zip7z.zip7Z(fileBS.getOutputpath(), downloadPath, null);
			
		}else{
			return EventRtnType.NORMAL_SUCCESS;
		}
		String downloadUUID = UUID.randomUUID().toString();
		request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
		this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
		this.getExecuteSG().addExecuteCode("downloadByUUID();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 职数预审修改保存
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("saveZS.onclick")
	public int saveZS(String id) throws RadowException{
		String rbId = this.getPageElement("rbId").getValue();//批次id
		String block = this.getPageElement("block").getValue();//职数预审展示行数
		String js1214 = this.getPageElement("js1214").getValue();//干部监督处意见
		try {
			HBSession sess = HBUtil.getHBSession();
			
			sess.createSQLQuery("update js12 set js1214 = '"+js1214+"'").executeUpdate();
			sess.flush();
			//this.setMainMessage("保存成功");
			this.setMainMessage("职数预审表审核完成。");
		}  catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败："+e.getCause().getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

}
