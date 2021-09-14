package com.insigma.siis.local.pagemodel.xbrm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.hibernate.SQLQuery;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

/**
 * 模拟干部任免
 * @author a
 *
 */
public class AllFileDownPageModel extends PageModel{
	private static final String separator =System.getProperty("file.separator");
	/**
	 * 页面初始化
	 */
	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 批量下载拟任免资料
	 * @param fileNames
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("download")
	public int showDetail(String fileNames) throws RadowException, AppException{
		CommQuery cq = new CommQuery();
		String js0100s = this.getPageElement("js0100s").getValue();
		String[] js0100sArr=js0100s.split("@#@");
		String[] fileNamesArr = fileNames.split(",");
		String classPath = AppConfig.HZB_PATH+"/zhgbdownload"+"/全程纪实_"+DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss");
		File tmpFile = new File(classPath);
	    if (!tmpFile.exists()) {
	    	 //创建临时目录
	    	 tmpFile.mkdirs();
	    }
	    
	    String fileNamesIn = fileNames.replace(",", "','");
	    
//	    classPath = classPath+"/全程纪实_"+DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss");
//	    File tempPath = new File(classPath);
//	    if(!tempPath.exists()){
//	    	tempPath.mkdir();
//	    }
	    
	    
	    
		String sql = "select c.a0101,a.jsa00,a.jsa07,a.jsa04,a.jsa02 from js_att a,JS01 b,"
				+ "(select a0000,a0101,'1' v_xt from a01 union select a0000,a0101,v_xt from v_Js_a01 a01 ) c"
				+ " where a.js0100=b.js0100 and b.a0000=c.a0000 and c.v_xt=js0122 and b.js0100 in (";
		for(int i=0;i<js0100sArr.length;i++){
			sql += "'"+js0100sArr[i]+"',";
		}
		sql = sql.substring(0, sql.length()-1);
		sql +=") and jsa02 in ('"+fileNamesIn+"')";
		List<HashMap<String,Object>> list = cq.getListBySQL(sql);
		if(list.isEmpty()||list==null||list.size()==0){
			this.setMainMessage("没有相应附件信息！");
			return EventRtnType.FAILD;
		}
		String savePath = "";
		
		for(HashMap<String,Object> map:list){
			String jsa02 = "";
			String a0101 = map.get("a0101").toString();
			if("JS02".equals(map.get("jsa02"))){
				jsa02 = "动议";
			}else if("JS99".equals(map.get("jsa02"))){
				jsa02 = "考核和听取意见";
			}else if("JS19".equals(map.get("jsa02"))){
				jsa02 = "民主推荐";
			}else if("JS14".equals(map.get("jsa02"))){
				jsa02 = "组织考察";
			}else if("JS07".equals(map.get("jsa02"))){
				jsa02 = "讨论决定";
			}else if("JS08".equals(map.get("jsa02"))){
				jsa02 = "任前公示";
			}
			String tabDir =makePath(jsa02, classPath);
			String nameDir = makePath(a0101, tabDir);
			String oldPath = AppConfig.HZB_PATH+"/"+map.get("jsa07").toString()
					+"/"+map.get("jsa00").toString();
			//将文件拷贝过去
			copyFile(oldPath,nameDir);
			//文件改名
			new File(nameDir+"/"+map.get("jsa00").toString()).renameTo(new File(nameDir+"/"+map.get("jsa04").toString()));
		}
		String infile = classPath+".zip";
		SevenZipUtil.zip7z(classPath, infile, null);
		this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		this.delFolder(classPath);
		return EventRtnType.NORMAL_SUCCESS;
	}
	/*
	 * 
	 */
	private String makePath(String filename,String savePath){
		//构造新的保存目录
		String dir = savePath  + "/" + filename;  
		//File既可以代表文件也可以代表目录
		File file = new File(dir);
		//如果目录不存在
		if(!file.exists()){
			//创建目录
			file.mkdirs();
		}
		return dir;
	}
	
	 /*
     * 实现文件的拷貝
     * @param srcPathStr
     *          源文件的地址信息
     * @param desPathStr
     *          目标文件的地址信息 
     */
    private static void copyFile(String srcPathStr, String desPathStr) {
        //1.获取源文件的名称
        String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("\\")+1); //目标文件地址
        System.out.println(newFileName);
        desPathStr = desPathStr + File.separator + newFileName; //源文件地址
        System.out.println(desPathStr);

        try{
            //2.创建输入输出流对象
            FileInputStream fis = new FileInputStream(srcPathStr);
            FileOutputStream fos = new FileOutputStream(desPathStr);                

            //创建搬运工具
            byte datas[] = new byte[1024*8];
            //创建长度
            int len = 0;
            //循环读取数据
            while((len = fis.read(datas))!=-1){
                fos.write(datas,0,len);
            }
            //3.释放资源
            fis.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        } 
    }
    
 // 删除文件夹
 	// param folderPath 文件夹完整绝对路径
 	public static void delFolder(String folderPath) {
 		try {
 			delAllFile(folderPath); // 删除完里面所有内容
 			String filePath = folderPath;
 			filePath = filePath.toString();
 			java.io.File myFilePath = new java.io.File(filePath);
 			myFilePath.delete(); // 删除空文件夹
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
 	
 	// 删除指定文件夹下所有文件
 	// param path 文件夹完整绝对路径
 	public static boolean delAllFile(String path) {
 		boolean flag = false;
 		File file = new File(path);
 		if (!file.exists()) {
 			return flag;
 		}
 		if (!file.isDirectory()) {
 			return flag;
 		}
 		String[] tempList = file.list();
 		File temp = null;
 		for (int i = 0; i < tempList.length; i++) {
 			if (path.endsWith(File.separator)) {
 				temp = new File(path + tempList[i]);
 			} else {
 				temp = new File(path + File.separator + tempList[i]);
 			}
 			if (temp.isFile()) {
 				temp.delete();
 			}
 			if (temp.isDirectory()) {
 				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
 				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
 				flag = true;
 			}
 		}
 		return flag;
 	}

}
