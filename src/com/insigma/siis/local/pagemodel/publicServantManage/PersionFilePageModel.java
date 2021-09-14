package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.transform.Transformers;

import com.JUpload.JUpload;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.PersionFile;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.entity.Supervision;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.insigma.siis.local.pagemodel.xbrm.pojo.JsAttPojo;

public class PersionFilePageModel extends PageModel implements JUpload{

	@Override
	public int doInit() throws RadowException {
		
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
    
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();				
		this.getPageElement("a0000").setValue(a0000);
		this.setNextEventName("grid1.dogridquery");
		this.setNextEventName("grid2.dogridquery");
		this.setNextEventName("grid3.dogridquery");
		this.setNextEventName("grid4.dogridquery");
		this.setNextEventName("grid5.dogridquery");
		this.setNextEventName("grid6.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
				
	@PageEvent("grid1.dogridquery")
	public int dogridquery(int start,int limit) throws Exception{
		String a0000 = this.getPageElement("a0000").getValue();				
		//this.getPageElement("a0000").setValue(a0000);
		//String sql = "select id,fileurl,filename,time from persionfile where a0000='"+a0000+"' and filesort='1' order by time desc";
		String sql="select t1.JSA00 JSA00,t1.JSA06 JSA06,t1.JSA04 JSA04,replace(t2.AAA005||'/'||t1.JSA07||t1.JSA00,'','') realurl from js_att t1,AA01 t2  where t2.AAA001='HZB_PATH' and  t1.js0100 in(select js0100 from js01 where a0000='"+a0000+"') and jsa02='JS02' ";
		
		this.pageQuery(sql, "SQL", start, limit);
        System.out.println("查询sql"+sql);
    	return EventRtnType.SPE_SUCCESS;
		
	}
	
	@PageEvent("grid2.dogridquery")
	public int dogrid2query(int start,int limit) throws Exception{
		String a0000 = this.getPageElement("a0000").getValue();				
			
		//String sql = "select id,fileurl,filename,time from persionfile where a0000='"+a0000+"' and filesort='2' order by time desc";
		String sql="select t1.JSA00 JSA00,t1.JSA06 JSA06,t1.JSA04 JSA04,replace(t2.AAA005||'/'||t1.JSA07||t1.JSA00,'','') realurl from js_att t1,AA01 t2  where t2.AAA001='HZB_PATH' and  t1.js0100 in(select js0100 from js01 where a0000='"+a0000+"') and jsa02='JS99' ";
		System.out.println("SQL"+sql);
		this.pageQuery(sql, "SQL", start, limit);
        System.out.println("查询sql"+sql);
    	return EventRtnType.SPE_SUCCESS;
		
	}
	
	@PageEvent("grid3.dogridquery")
	public int dogrid3query(int start,int limit) throws Exception{
		String a0000 = this.getPageElement("a0000").getValue();				
			
		//String sql = "select id,fileurl,filename,time from persionfile where a0000='"+a0000+"' and filesort='3' order by time desc";
		String sql="select t1.JSA00 JSA00,t1.JSA06 JSA06,t1.JSA04 JSA04,replace(t2.AAA005||'/'||t1.JSA07||t1.JSA00,'','') realurl from js_att t1,AA01 t2  where t2.AAA001='HZB_PATH' and  t1.js0100 in(select js0100 from js01 where a0000='"+a0000+"') and jsa02='JS19' ";
		System.out.println("SQL"+sql);
		this.pageQuery(sql, "SQL", start, limit);
        System.out.println("查询sql"+sql);
    	return EventRtnType.SPE_SUCCESS;
			
	}
	
	@PageEvent("grid4.dogridquery")
	public int dogrid4query(int start,int limit) throws Exception{
		String a0000 = this.getPageElement("a0000").getValue();				
			
		//String sql = "select id,fileurl,filename,time from persionfile where a0000='"+a0000+"' and filesort='4' order by time desc";
		String sql="select t1.JSA00 JSA00,t1.JSA06 JSA06,t1.JSA04 JSA04,replace(t2.AAA005||'/'||t1.JSA07||t1.JSA00,'','') realurl from js_att t1,AA01 t2  where t2.AAA001='HZB_PATH' and  t1.js0100 in(select js0100 from js01 where a0000='"+a0000+"') and jsa02='JS14' ";
		System.out.println("SQL"+sql);
		this.pageQuery(sql, "SQL", start, limit);
        System.out.println("查询sql"+sql);
    	return EventRtnType.SPE_SUCCESS;
		
	}
	
	
	@PageEvent("grid5.dogridquery")
	public int dogrid5query(int start,int limit) throws Exception{
		String a0000 = this.getPageElement("a0000").getValue();				
			
		//String sql = "select id,fileurl,filename,time from persionfile where a0000='"+a0000+"' and filesort='4' order by time desc";
		String sql="select t1.JSA00 JSA00,t1.JSA06 JSA06,t1.JSA04 JSA04,replace(t2.AAA005||'/'||t1.JSA07||t1.JSA00,'','') realurl from js_att t1,AA01 t2  where t2.AAA001='HZB_PATH' and  t1.js0100 in(select js0100 from js01 where a0000='"+a0000+"') and jsa02='JS07' ";
		System.out.println("SQL"+sql);
		this.pageQuery(sql, "SQL", start, limit);
        System.out.println("查询sql"+sql);
    	return EventRtnType.SPE_SUCCESS;
		
	}
	
	@PageEvent("grid6.dogridquery")
	public int dogrid6query(int start,int limit) throws Exception{
		String a0000 = this.getPageElement("a0000").getValue();				
			
		//String sql = "select id,fileurl,filename,time from persionfile where a0000='"+a0000+"' and filesort='4' order by time desc";
		String sql="select t1.JSA00 JSA00,t1.JSA06 JSA06,t1.JSA04 JSA04,replace(t2.AAA005||'/'||t1.JSA07||t1.JSA00,'','') realurl from js_att t1,AA01 t2  where t2.AAA001='HZB_PATH' and  t1.js0100 in(select js0100 from js01 where a0000='"+a0000+"') and jsa02='JS08' ";
		System.out.println("SQL"+sql);
		this.pageQuery(sql, "SQL", start, limit);
        System.out.println("查询sql"+sql);
    	return EventRtnType.SPE_SUCCESS;
		
	}
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String id)throws RadowException, AppException{
			
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			PersionFile persionfile = (PersionFile)sess.get(PersionFile.class, id);		
			
			String path = persionfile.getFileUrl();
			File file = new File(path);
			String parentPath = file.getParent();
			File parenfile = new File(parentPath);
			
			deleteFile(parenfile);
			sess.delete(persionfile);			
			this.getExecuteSG().addExecuteCode("restore()");
			sess.flush();
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}	
	
	
	public static String  disk = JSGLBS.HZBPATH ;
	@PageEvent("deleteRow3")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow3(String id)throws RadowException, AppException{
			
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
					
			JsAtt ja = (JsAtt)sess.get(JsAtt.class, id);
			if(ja==null){
				this.setMainMessage("删除失败！");
				return EventRtnType.FAILD;
			}
			String directory = disk+ja.getJsa07();
			File f = new File(directory+id);
			if(f.isFile()){
				f.delete();
			}
			sess.delete(ja);	
			this.getExecuteSG().addExecuteCode("restore()");
			sess.flush();
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("importZip")
	public int importZip() throws RadowException, IOException{
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("a0000").getValue();
		List<JsAttPojo> jsattList = sess.createSQLQuery("select *  from JS_ATT where JS0100 in (select JS0100 from JS01 where A0000 = :a0000 )").setParameter("a0000", a0000).setResultTransformer(Transformers.aliasToBean(JsAttPojo.class)).list();
		String path=new ExportAsposeBS().getexpPath();
		int num=1;
		for(JsAttPojo ja : jsattList){
			num=num+1;
			String directory = disk+ja.getJSA07()+ja.getJSA00();
			File file = new File(directory);
			
			if(file.isFile()){
				String newPath=path+num+ja.getJSA04().replace("\\", "/");
				String newPath1=path+ja.getJSA04().replace("\\", "/");
				File file2 = new File(newPath);
				if(file2.exists()){
					file2 = new File(newPath1);
					copyFile(file,file2);
				}else{
					copyFile(file,file2);
				}
			}
		}
		String infile =path.substring(0,path.length()-1)+".zip" ;
		SevenZipUtil.zip7z(path, infile, null);
		this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	public void copyFile(File sourceFile,File targetFile) throws IOException{  
        // 新建文件输入流并对它进行缓冲   
        FileInputStream input = new FileInputStream(sourceFile);  
        BufferedInputStream inBuff=new BufferedInputStream(input);  
  
        // 新建文件输出流并对它进行缓冲   
        FileOutputStream output = new FileOutputStream(targetFile);  
        BufferedOutputStream outBuff=new BufferedOutputStream(output);  
          
        // 缓冲数组   
        byte[] b = new byte[1024 * 5];  
        int len;  
        while ((len =inBuff.read(b)) != -1) {  
            outBuff.write(b, 0, len);  
        }  
        // 刷新此缓冲的输出流   
        outBuff.flush();  
          
        //关闭流   
        inBuff.close();  
        outBuff.close();  
        output.close();  
        input.close();  
	}  
	
	public void copyFile(String oldPath, String newPath) { 
		try { 
		int bytesum = 0; 
		int byteread = 0; 
		File oldfile = new File(oldPath); 
		if (oldfile.exists()) { //文件存在时 
		InputStream inStream = new FileInputStream(oldPath); //读入原文件 
		FileOutputStream fs = new FileOutputStream(newPath); 
		byte[] buffer = new byte[1444]; 
		int length; 
		while ( (byteread = inStream.read(buffer)) != -1) { 
		bytesum += byteread; //字节数 文件大小 
		System.out.println(bytesum); 
		fs.write(buffer, 0, byteread); 
		} 
		inStream.close(); 
		} 
		} 
		catch (Exception e) { 
		System.out.println("复制单个文件操作出错"); 
		e.printStackTrace(); 

		} 

		} 
	
	private void deleteFile(File file) {  
		if (file.exists()) {//判断文件是否存在  
			 if (file.isFile()) {//判断是否是文件  
				 file.delete();//删除文件  
			 } else if (file.isDirectory()) {//否则如果它是一个目录  
				 File[] files = file.listFiles();//声明目录下所有的文件 files[];  
				 for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件  
				       this.deleteFile(files[i]);//把每个文件用这个方法进行迭代  
				      } 
				 file.delete();//删除文件夹  
			 }
		} else {  
		     System.out.println("所删除的文件不存在");  
			
		}
	}



	/**
	 * 保存
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("save")
	public int save() throws RadowException, Exception {
		/*String ids = this.getPageElement("id").getValue();
		//String a0000 = this.getPageElement("a0000").getValue();
		String[] idarr = ids.split("@@@");
		String id = null;
		String a0000 = null;
		if(idarr.length==2){
			 id = idarr[0];
			 a0000 = idarr[1];
		}else{
			 a0000 = idarr[0];
		}*/
		String id = this.getPageElement("pid").getValue();
		String a0000 = this.getPageElement("a0000").getValue();
		Supervision supervision = new Supervision();
		HBSession sess = HBUtil.getHBSession();
		if(id==null||"".equals(id)){
			PMPropertyCopyUtil.copyElementsValueToObj(supervision, this);
			supervision.setId(UUID.randomUUID().toString());
			supervision.setA0000(a0000);
			sess.save(supervision);
			sess.flush();
			
			this.getExecuteSG().addExecuteCode("saveCallBack('新增成功');");
			//this.setMainMessage("保存成功");
		}else{
			supervision = (Supervision)sess.get(Supervision.class, id);
			PMPropertyCopyUtil.copyElementsValueToObj(supervision, this);
			sess.update(supervision);
			sess.flush();
			this.getExecuteSG().addExecuteCode("saveCallBack('修改成功');");
			//this.setMainMessage("修改成功");
		}		
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('grid1').store.reload();");
		return EventRtnType.NORMAL_SUCCESS;
	}


	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem,
			Map<String, String> formDataMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String deleteFile(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
