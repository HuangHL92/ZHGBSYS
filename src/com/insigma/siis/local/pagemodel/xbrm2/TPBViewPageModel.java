package com.insigma.siis.local.pagemodel.xbrm2;


import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.Js2Yntp;
import com.insigma.siis.local.business.entity.TpbAtt;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.utils.SQLiteUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.utils.CommonQueryBS;

import net.sf.json.JSONArray;

public class TPBViewPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("memberGrid.dogridquery");
		//设置下拉框
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		String yn_gd_id = this.getPageElement("yn_gd_id").getValue();
		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
		if(rb==null){
			this.setMainMessage("无批次信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql = "select tp0100, a0000, type, tp0101, tp0102, tp0103, tp0104,"
				+ " tp0105, tp0106, tp0107,tp0108,tp0109,tp0110, tp0111,tp0112,tp0113,tp0114,tp0115,nvl2((select jsa00 from tpb_att where js0100=TPHJ1_GD.a0000 and rb_id=TPHJ1_GD.yn_id),'kcclclass','default_color') kcclclass from TPHJ1_GD where yn_gd_id='"+yn_gd_id+"' order by sortnum asc ";
		String js2_yntp_info = "select info0y, info0m, info0d from js2_yntp_info where yn_id='"+ynId+"' and info01='"+yn_type+"'";
		
		
		try {
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
			JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
			//System.out.println(updateunDataStoreObject.toString());
			this.getExecuteSG().addExecuteCode("TIME_INIT.changeTableType('"+yn_type+"');");
			this.getExecuteSG().addExecuteCode("doAddPerson.addPerson("+updateunDataStoreObject.toString()+");");
			//会议时间
			listCode=cqbs.getListBySQL(js2_yntp_info);
			if(listCode.size()>0){
				Map<String, Object> map = listCode.get(0);
				String y = map.get("info0y")==null?"":map.get("info0y").toString();
				String m = map.get("info0m")==null?"":map.get("info0m").toString();
				String d = map.get("info0d")==null?"":map.get("info0d").toString();
				this.getExecuteSG().addExecuteCode("TIME_INIT.setTime('"+y+"','"+m+"','"+d+"','"+yn_type+"');");
			}
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("readTPB")
	public int readTPB() throws RadowException, AppException{
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		String yn_gd_id = this.getPageElement("yn_gd_id").getValue();
		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
		if(rb==null){
			this.setMainMessage("无批次信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql = "select sys_guid() tp0100, a0000, type, tp0101, tp0102, tp0103, tp0104,"
				+ " tp0105, tp0106, tp0107,tp0108,tp0109,tp0110, tp0111,tp0112,tp0113,tp0114,tp0115,nvl2((select jsa00 from tpb_att where js0100=TPHJ1_GD.a0000 and rb_id=TPHJ1_GD.yn_id),'kcclclass','default_color') kcclclass from TPHJ1_GD where yn_gd_id='"+yn_gd_id+"' order by sortnum asc ";
		String js2_yntp_info = "select info0y, info0m, info0d from js2_yntp_info where yn_id='"+ynId+"' and info01='"+yn_type+"'";
		
		
		try {
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
			JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
			//System.out.println(updateunDataStoreObject.toString());
			//this.getExecuteSG().addExecuteCode("parent.TIME_INIT.clearData();;");
			//this.getExecuteSG().addExecuteCode("parent.TIME_INIT.changeTableType('"+yn_type+"');");
			this.getExecuteSG().addExecuteCode("parent.doAddPerson.addPerson("+updateunDataStoreObject.toString()+");");
			//会议时间
			listCode=cqbs.getListBySQL(js2_yntp_info);
			if(listCode.size()>0){
				Map<String, Object> map = listCode.get(0);
				String y = map.get("info0y")==null?"":map.get("info0y").toString();
				String m = map.get("info0m")==null?"":map.get("info0m").toString();
				String d = map.get("info0d")==null?"":map.get("info0d").toString();
				//this.getExecuteSG().addExecuteCode("parent.TIME_INIT.setTime('"+y+"','"+m+"','"+d+"','"+yn_type+"');");
			}
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("window.close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("deleteGD")
	public int deleteGD() throws RadowException, AppException{
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		String yn_gd_id = this.getPageElement("yn_gd_id").getValue();
		Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
		if(rb==null){
			this.setMainMessage("无批次信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		try {
			HBUtil.executeUpdate("delete from TPHJ1_GD where yn_gd_id=?",new Object[]{yn_gd_id});
			HBUtil.executeUpdate("delete from JS2_YNTP_GD where yn_gd_id=?",new Object[]{yn_gd_id});
			this.setNextEventName("memberGrid.dogridquery");
			this.getExecuteSG().addExecuteCode("TIME_INIT.clearData();");
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("memberGrid.dogridquery")
	@AutoNoMask
	public int doMemberQuery(int start,int limit) throws RadowException{
		try {
			String ynId = this.getPageElement("ynId").getValue();//批次
			String sql = "select yn_id,yn_type,to_char(yn_date,'yyyymmdd') yndate,yn_gd_desc,yn_gd_id"
					+ " from JS2_YNTP_GD where yn_id='"+ynId+"' order by yn_date desc ";
			this.pageQuery(sql, "SQL", start, limit);
        	return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.SPE_SUCCESS;
		}
	}
	
	//按姓名查询，传递人员IDs
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String listStr) throws Exception{
		//System.out.println(listStr);
		HBSession sess = HBUtil.getHBSession();
		
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		StringBuffer sql = new StringBuffer();
		if(listStr!=null && listStr.indexOf("TPHJ")>=0){//选择了其它会议类型
			String[] params = listStr.split("@@");
			String ortherYnid = "";
			if(params.length==2){
				ortherYnid = params[0];
				listStr = params[1];
			}else{
				ortherYnid = ynId;
			}
			sql.append("select sys_guid() tp0100, a0000, type, tp0101, tp0102, tp0103, tp0104,"
					+ " tp0105, tp0106, tp0107,tp0108,tp0109,tp0110, tp0111,tp0112,tp0113,tp0114,tp0115,nvl2((select jsa00 from tpb_att where js0100=t.a0000 and rb_id=t.yn_id),'kcclclass','default_color') kcclclass from TPHJ1 t where yn_id='"+ortherYnid+"' and tp0116='"+listStr+"' ");
			sql.append(" and not exists (select 1 from TPHJ1 p where p.a0000=t.a0000");
			sql.append(" and yn_id='"+ynId+"' and tp0116='"+yn_type+"')  order by sortnum asc ");
			try {
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
				JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
				//System.out.println(updateunDataStoreObject.toString());
				this.getExecuteSG().addExecuteCode("doAddPerson.addPerson("+updateunDataStoreObject.toString()+")");
				
				//提取考察材料
				if(params.length==2){
					
					String kcclsql = "select * from tpb_att where rb_id='"+ortherYnid+"' and js0100 in (select a0000 from TPHJ1 t "
							+ " where yn_id='"+ortherYnid+"' and tp0116='"+listStr+"' "
					+" and not exists (select 1 from TPHJ1 p where p.a0000=t.a0000"
					+" and yn_id='"+ynId+"' and tp0116='"+yn_type+"'))";
					List<TpbAtt> tpbattList = sess.createSQLQuery(kcclsql).addEntity(TpbAtt.class).list();
					String directory = "zhgbuploadfiles" + File.separator +ynId+ File.separator;
					String disk = YNTPFileExportBS.HZBPATH;
					File f = new File(disk + directory);
					if(!f.isDirectory()){
						f.mkdirs();
					}
					for(TpbAtt tpbAtt : tpbattList){
						TpbAtt tat = new TpbAtt();
						tat.setJs0100(tpbAtt.getJs0100());//人员信息
						tat.setJsa00(UUID.randomUUID().toString());//主键
						tat.setRbId(ynId);//批次id
						tat.setJsa05(SysManagerUtils.getUserId());//用户id
						tat.setJsa06(tpbAtt.getJsa06());//上传时间
						tat.setJsa04(tpbAtt.getJsa04());
						
						
						String targetPath = disk + directory  + tat.getJsa00();
						String sourcePath = disk+tpbAtt.getJsa07() + tpbAtt.getJsa00();
						SQLiteUtil.copyFileByPath(sourcePath, targetPath);
						
						tat.setJsa07(directory);
						tat.setJsa08(tpbAtt.getJsa08());
						sess.save(tat);
						
					}
				}
				
				sess.flush();
			} catch (Exception e) {
				this.setMainMessage("查询失败！");
				e.printStackTrace();
			}
		}else if(listStr!=null && listStr.length()>2){//基础库选择人员
			sql.append("select  sys_guid() tp0100, t.a0000 a0000,"
					+ " '3' type,GET_tpbXingming(t.a0101,t.a0104,t.a0117,t.a0141) tp0101,t.a0107 tp0102,t.a1701 tp0103, t.a0288 tp0104,"
					+ " GET_TPBXUELI2(t.qrzxl,t.zzxl,t.qrzxw,t.zzxw) tp0105,"
					+ " t.a0192a tp0106,'' tp0107,'' tp0108,'' tp0109,'' tp0110,"
					+ " '' tp0111,'' tp0112,'' tp0113,'' tp0114,'' tp0115,'default_color' kcclclass from a01 t");
		
			sql.append(" where t.a0000 in ('-1'");
			listStr = listStr.substring(1, listStr.length()-1);
			List<String> list = Arrays.asList(listStr.split(","));
			for(String id:list){
				sql.append(",'"+id.trim()+"'");
				
			}
			sql.append(") ");
			sql.append(" and not exists (select 1 from TPHJ1 p where p.a0000=t.a0000");
			sql.append(" and yn_id='"+ynId+"' and tp0116='"+yn_type+"')");
			try {
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
				for(HashMap m : listCode){
					//出生年月处理
					String text = this.getFTime(m.get("tp0102"));
					m.put("tp0102",text);
					//任现职时间处理
					text = this.getFTime(m.get("tp0104"));
					m.put("tp0104",text);
					//任现职时间处理
					String jianli = m.get("tp0103")==null?"":m.get("tp0103").toString();
					String[] jianliArray = jianli.split("\r\n|\r|\n");
					if(jianliArray.length>0){
						String jl = jianliArray[jianliArray.length-1].trim();
						//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
						Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");     
				        Matcher matcher = pattern.matcher(jl);     
				        if (matcher.find()) {
				        	String line1 = matcher.group(0);  
				        	m.put("tp0103",line1.substring(2,7));
				        }else{
				        	m.put("tp0103",null);
				        }
					}else{
						m.put("tp0103",null);
					}
					
					
				}
				JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
				//System.out.println(updateunDataStoreObject.toString());
				this.getExecuteSG().addExecuteCode("doAddPerson.addPerson("+updateunDataStoreObject.toString()+")");
				
			} catch (Exception e) {
				this.setMainMessage("查询失败！");
				e.printStackTrace();
			}
		}else{
			this.setMainMessage("无法查询到该人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
			
			
		return EventRtnType.NORMAL_SUCCESS;
		
	}

	
	private String getFTime(Object tex){
		String text = null;
		if(tex!=null){
			text = tex.toString();
			if(text.length()>=6){
				return text.substring(2, 4)+"."+text.substring(4, 6);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	
	
	
	
	/**
	 * 信息导出
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("ExpTPB")
	public int ExpTPB(String param) throws Exception{
		try {
			
		
			YNTPFileExportBS fileBS = new YNTPFileExportBS(null);
			String ynId = this.request.getParameter("ynId");//批次
			String yntype = this.request.getParameter("yntype");//环节
			String yn_gd_id = this.request.getParameter("yn_gd_id");
			if(yn_gd_id==null||"".equals(yn_gd_id)){
				this.setMainMessage("请双击调配环节");
			    return EventRtnType.NORMAL_SUCCESS;
			}
			String sql = "select tp0100, a0000, type, tp0101, tp0102, tp0103, tp0104,"
					+ " tp0105, tp0106, tp0107 tp0107 from TPHJ1_GD where yn_gd_id='"+yn_gd_id+"' order by sortnum asc ";
			fileBS.tpbSql = sql;
			fileBS.setOutputpath(YNTPFileExportBS.HZBPATH+"zhgboutputfiles/"+ynId+"/");
			File f = new File(fileBS.getOutputpath());
			
			if(f.isDirectory()){//先清空输出目录
				JSGLBS.deleteDirectory(fileBS.getOutputpath());
			}
			
			fileBS.exportGBTP(ynId,yntype);
			
			
			List<String> fns = fileBS.getOutputFileNameList();
			String downloadPath = "";
			String downloadName = "";
			if(fns.size()==1){//一个文件直接下载
				downloadPath = fileBS.getOutputpath()+fns.get(0);
				downloadName = fns.get(0);
			}else if(fns.size()>1){//压缩
				//downloadPath = fileBS.getOutputpath()+fileBS.getSheetName()+".zip";
				//downloadName = fileBS.getSheetName()+".zip";
				//Zip7z.zip7Z(fileBS.getOutputpath(), downloadPath, null);
				
			}else{
				return EventRtnType.NORMAL_SUCCESS;
			}
			String downloadUUID = UUID.randomUUID().toString();
			request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("ExpTPBExcel")
	public int ExpTPBExcel(String param) throws Exception{
//		try { 
//			YNTPFileExportBS fileBS = new YNTPFileExportBS(null);
//			String ynId = this.request.getParameter("ynId");//批次
//			String yntype = this.request.getParameter("yntype");//环节
//			String yn_gd_id = this.request.getParameter("yn_gd_id");
//			if(yn_gd_id==null||"".equals(yn_gd_id)){
//				this.setMainMessage("请双击调配环节");
//			    return EventRtnType.NORMAL_SUCCESS;
//			}
//			String sql = "select * where yn_gd_id='"+yn_gd_id+"' order by sortnum asc ";
//			 			 
//			List<HashMap<String, Object>> listValues = CommonQueryBS.getListBySQL(sql);
//			
//			com.insigma.siis.local.pagemodel.xbrm2.expexcel.ExpTPB objExpTPB = new com.insigma.siis.local.pagemodel.xbrm2.expexcel.ExpTPB();
//			
//			String downloadUUID = UUID.randomUUID().toString();
//			List<String> fns = fileBS.getOutputFileNameList();
//			String downloadPath = YNTPFileExportBS.HZBPATH+"zhgboutputfiles/"+ynId+"/";
//			String downloadName = "gbynrmb_"+downloadUUID+".xlsx";  
//			request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
//			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
//			this.getExecuteSG().addExecuteCode("downloadByUUID();");
//			
//			String filePath = "d:\\temp\\gbynrmb.xlsx";
//			String desFilepath = downloadPath+downloadName;
//			objExpTPB.copyFile(filePath, desFilepath);
//			List<String> titles = objExpTPB.getTitles();
//			List<HashMap<String, Object>> values = listValues;
//			objExpTPB.writeExcel(desFilepath, "Sheet1", titles, values);
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//			this.setMainMessage("导出失败"+e.getMessage());
//		}
//		return EventRtnType.NORMAL_SUCCESS;
		
		System.out.println("文件开始!"+(new Date()).toLocaleString());
		try { 
			YNTPFileExportBS fileBS = new YNTPFileExportBS(null);
			String ynId = this.request.getParameter("ynId");//批次
			String yntype = this.request.getParameter("yntype");//环节
			String yn_gd_id = this.request.getParameter("yn_gd_id");
			
			String sql = null;
			if(yn_gd_id==null||"".equals(yn_gd_id)){
				sql = "select 0 as xh,tphj1.* from tphj1 where yn_id='"+ynId+"' order by sortnum asc ";
			} else {
				sql = "select 0 as xh,tphj1_gd.* from tphj1_gd where yn_gd_id='"+yn_gd_id+"' order by sortnum asc ";
			}
			  
			List<HashMap<String, Object>> listValues = CommonQueryBS.getListBySQL(sql);
			
			com.insigma.siis.local.pagemodel.xbrm2.expexcel.ExpTPB objExpTPB = new com.insigma.siis.local.pagemodel.xbrm2.expexcel.ExpTPB();
			
			String downloadUUID = UUID.randomUUID().toString().replaceAll("\\-", "");
			List<String> fns = fileBS.getOutputFileNameList();
			String downloadName = "gbynrmb_"+downloadUUID+".xlsx"; 
			
			if ("TPHJ2".equals(yntype)) {
				downloadName = "gbcwhrmbgd_"+downloadUUID+".xlsx";  
			}else if ("TPHJ3".equals(yntype)) {
				downloadName = "gbswsjrmbgd_"+downloadUUID+".xlsx"; 
			} else {
				downloadName = "gbynrmbgd_"+downloadUUID+".xlsx";  
			}
			
			String downloadPath = YNTPFileExportBS.HZBPATH+"zhgboutputfiles/"+ynId+"/"+downloadName;
			 
			request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
			
			String path = this.getClass().getClassLoader().getResource("/").getPath();
			
			String filePath = path+"template/gbynrmb.xlsx";
			
			if ("TPHJ2".equals(yntype)) {
				filePath = path+"template/gbcwhrmb.xlsx";
			}else if ("TPHJ3".equals(yntype)) {
				filePath = path+"template/gbswsjrmb.xlsx";
			} else {
				filePath = path+"template/gbynrmb.xlsx";
			}
			String desFilepath = downloadPath;
			objExpTPB.copyFile(filePath, desFilepath);
			List<String> titles = objExpTPB.getTitles();
			List<HashMap<String, Object>> values = listValues;
			objExpTPB.writeExcel(desFilepath, "Sheet1", titles, values);
			this.setMainMessage("操作成功！");
		}catch(Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败"+e.getMessage());
		} 
		System.out.println("文件生成成功!"+(new Date()).toLocaleString());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	/**
	 * 导出任免表word
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("expWord")
	public int expWord(String param) throws Exception{
		try {
			
			
			YNTPFileExportBS fileBS = new YNTPFileExportBS(null);
			String ynId = this.getPageElement("ynId").getValue();
			String yntype = this.getPageElement("yntype").getValue();
			String yn_gd_id = this.getPageElement("yn_gd_id").getValue();
			Js2Yntp rb = (Js2Yntp) HBUtil.getHBSession().get(Js2Yntp.class, ynId);
			if(rb==null){
				this.setMainMessage("无批次信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			fileBS.setOutputpath(YNTPFileExportBS.HZBPATH+"zhgboutputfiles/"+ynId+"/");
			File f = new File(fileBS.getOutputpath());
			
			if(f.isDirectory()){//先清空输出目录
				JSGLBS.deleteDirectory(fileBS.getOutputpath());
			}
			fileBS.NORM = param;
			String sql = "select a0000,tp0106 from TPHJ1_GD where yn_gd_id='"+yn_gd_id+"' and a0000 is not null order by sortnum asc ";
			fileBS.RMsql = sql;
			fileBS.expWord(ynId,yntype);
			File fa[] = f.listFiles();
			
			for (int i = 0; i < fa.length; i++) {
				File fs = fa[i];
				if (fs.isFile()) {
					
					String filename = fs.getName();
					String downloadUUID = UUID.randomUUID().toString();
					request.getSession().setAttribute(downloadUUID, new String[]{fileBS.getOutputpath()+filename,filename});
					this.getExecuteSG().addExecuteCode("Print.startPrint('"+downloadUUID+"','"+request.getSession().getId()+"');");
				} 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败"+e.getMessage());
		}
		this.setMainMessage("打印完成");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
}