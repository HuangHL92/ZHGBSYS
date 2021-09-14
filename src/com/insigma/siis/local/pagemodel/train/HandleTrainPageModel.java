package com.insigma.siis.local.pagemodel.train;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.entity.Train;
import com.insigma.siis.local.business.entity.TrainAtt;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;

public class HandleTrainPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String trainid = this.getPageElement("subWinIdBussessId2").getValue();
		try {
			if(trainid!=null&&!"".equals(trainid)){
				HBSession sess = HBUtil.getHBSession();
				Train t = (Train)sess.get(Train.class, trainid);
				PMPropertyCopyUtil.copyObjValueToElement(t, this);
				 this.getPageElement("a1107").setValue(formatLongToDate(t.getA1107())); //培训开始日期
	             this.getPageElement("a1111").setValue(formatLongToDate(t.getA1111())); //培训结束日期
				//List list_g11052 = sess.createSQLQuery("select g11052 from TRAIN_ATT where trainid='"+trainid+"'").list();
				//if(list_g11052.size()>0){
					//StringBuilder sb = new StringBuilder();
					//for(int i=0;i<list_g11052.size();i++){
						//sb.append(list_g11052.get(i).toString()+",");
					//}
					//sb.deleteCharAt(sb.length()-1);
					//this.getExecuteSG().addExecuteCode("loadPhoto('"+sb.toString()+"');");
				//}
			}
			//
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 将Long形式的yyyyMMdd转换为String的yyyy-MM-dd<br/>
	 * 或yyyyMM转换为yyyy-MM
	 * @param obj yyyyMMdd或yyyyMM
	 * @return yyyy-MM-dd或yyyy-MM
	 */
	public static String formatLongToDate(Object obj){
		String result;
		String str=String.valueOf(obj);
		int length=str.length();
		switch (length) {
		case 8:
			result=str.substring(0, 4)+"-"+str.substring(4, 6)+"-"+str.substring(6);
			break;
		case 6:
			result=str.substring(0, 4)+"-"+str.substring(4);
			break;
		default:
			result="";
			break;
		}
		return result;
	}
	@PageEvent("save")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int save() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String g11020 = this.getPageElement("g11020").getValue();
		String a1131 = this.getPageElement("a1131").getValue();
		if(StringUtils.isEmpty(a1131)){
			this.setMainMessage("请填写培训班次名称");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String a1101 = this.getPageElement("a1101").getValue();
		String a1114 = this.getPageElement("a1114").getValue();
		String a1107 = this.getPageElement("a1107").getValue();
		String a1111 = this.getPageElement("a1111").getValue();
		String g11021 = this.getPageElement("g11021").getValue();
		String g11022 = this.getPageElement("g11022").getValue();
		String g11023 = this.getPageElement("g11023").getValue();
		String g11024 = this.getPageElement("g11024").getValue();
		String a1121a = this.getPageElement("a1121a").getValue();
		String a1127 = this.getPageElement("a1127").getValue();
		String a1151 = this.getPageElement("a1151").getValue();
		//String g11025 = this.getPageElement("g11025").getValue();
		//String g11026 = this.getPageElement("g11026").getValue();
		//String g11030 = this.getPageElement("g11030").getValue();
		Train t = new Train();
		String trainid = this.getPageElement("trainid").getValue();
		if(StringUtils.isEmpty(trainid)){
			List list = sess.createSQLQuery("select * from TRAIN where a1131='"+a1131+"'").list();
			if(list.size()!=0){
				this.setMainMessage("该培训班次名称已存在");
				return EventRtnType.NORMAL_SUCCESS;
			}
			t.setTrainid(UUID.randomUUID().toString().replaceAll("-", ""));
			t.setG11020(new BigDecimal(g11020));
			t.setA1131(a1131);
			t.setA1101(a1101);
			t.setA1114(a1114);
			t.setA1151(a1151);
			t.setA1107(a1107.replace("-", ""));
			t.setA1111(a1111.replace("-", ""));
			t.setG11021(g11021);
			t.setG11022(g11022);
			t.setG11023(g11023);
			t.setG11024(g11024);
			t.setA1121a(a1121a);
			t.setA1127(a1127);
			//t.setG11025(g11025);
			//t.setG11026(g11026);
			String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
			String username = SysUtil.getCacheCurrentUser().getUserVO().getName();
			t.setUserid(userid);
			t.setUsername(username);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			t.setUpdatetime(sdf.format(new Date()));
			//t.setG11030(g11030);
			sess.save(t);
			sess.flush();
			this.getPageElement("trainid").setValue(t.getTrainid());
			this.getExecuteSG().addExecuteCode("saveCallBack();");
		}else{
			t.setTrainid(trainid);
			t.setG11020(new BigDecimal(g11020));
			t.setA1131(a1131);
			t.setA1101(a1101);
			t.setA1114(a1114);
			t.setA1151(a1151);
			t.setA1107(a1107.replace("-", ""));
			t.setA1111(a1111.replace("-", ""));
			t.setG11021(g11021);
			t.setG11022(g11022);
			t.setG11023(g11023);
			t.setG11024(g11024);
			t.setA1121a(a1121a);
			t.setA1127(a1127);
			//t.setG11025(g11025);
			//t.setG11026(g11026);
			String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
			String username = SysUtil.getCacheCurrentUser().getUserVO().getName();
			t.setUserid(userid);
			t.setUsername(username);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			t.setUpdatetime(sdf.format(new Date()));
			//t.setG11030(g11030);
			sess.update(t);
			sess.flush();
			this.getExecuteSG().addExecuteCode("saveCallBack();");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("downFile")
	public int outFile(String type) throws IOException, RadowException{
		HBSession session = HBUtil.getHBSession();
		String trainid = this.getPageElement("trainid").getValue();
		String disk = getTrainpath();
		String trainname = session.createSQLQuery("select A1131 from train where trainid='"+trainid+"'").uniqueResult().toString();
		String zip_name = "";
		String flagPath = "";
		if("1".equals(type)){
			List list = session.createSQLQuery("select g11025 from train where trainid='"+trainid+"'").list();
			if(list.size()==0||list.get(0)==null){
				this.setMainMessage("抱歉,这个班次没有上传教学计划信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//文件生成
			flagPath = disk + "out"+File.separator+trainname+System.currentTimeMillis();
			String flagPath2 = flagPath+File.separator+trainname+System.currentTimeMillis();
			File file = new File(flagPath2);
			if(!file.isDirectory()){
				file.mkdirs();
			}
			for(int i=0;i<list.size();i++){
				int one = list.get(0).toString().lastIndexOf("/");
				String filename = list.get(0).toString().substring((one+1),list.get(0).toString().length());
				//from_file
				File file_from = new File(disk+"in"+File.separator+list.get(0).toString());
				File file_to = new File(flagPath2+File.separator+filename);
				copyFile(file_from, file_to);
			}
			zip_name = trainname+"-教学计划信息";
		}else if("2".equals(type)){
			List list = session.createSQLQuery("select g11026 from train where trainid='"+trainid+"'").list();
			if(list.size()==0||list.get(0)==null){
				this.setMainMessage("抱歉,这个班次没有上传课程表信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//文件生成
			flagPath = disk + "out"+File.separator+trainname+System.currentTimeMillis();
			String flagPath2 = flagPath+File.separator+trainname+System.currentTimeMillis();
			File file = new File(flagPath2);
			if(!file.isDirectory()){
				file.mkdirs();
			}
			for(int i=0;i<list.size();i++){
				int one = list.get(0).toString().lastIndexOf("/");
				String filename = list.get(0).toString().substring((one+1),list.get(0).toString().length());
				//from_file
				File file_from = new File(disk+"in"+File.separator+list.get(0).toString());
				File file_to = new File(flagPath2+File.separator+filename);
				copyFile(file_from, file_to);
			}
			zip_name = trainname+"-课程表信息";
		}else{
			List list = session.createSQLQuery("select g11030 from train where trainid='"+trainid+"'").list();
			if(list.size()==0||list.get(0)==null){
				this.setMainMessage("抱歉,这个班次没有上传教学评估信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//文件生成
			flagPath = disk + "out"+File.separator+trainname+System.currentTimeMillis();
			String flagPath2 = flagPath+File.separator+trainname+System.currentTimeMillis();
			File file = new File(flagPath2);
			if(!file.isDirectory()){
				file.mkdirs();
			}
			for(int i=0;i<list.size();i++){
				int one = list.get(0).toString().lastIndexOf("/");
				String filename = list.get(0).toString().substring((one+1),list.get(0).toString().length());
				//from_file
				File file_from = new File(disk+"in"+File.separator+list.get(0).toString());
				File file_to = new File(flagPath2+File.separator+filename);
				copyFile(file_from, file_to);
			}
			zip_name = trainname+"-教学评估信息";
		}
		String path = disk+"out/"+zip_name;
		Zip7z.zip7Z(flagPath, path, null);
		request.getSession().setAttribute("train_path", path+".zip");
		this.getExecuteSG().addExecuteCode("outFileZip()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public static void downFile(HttpServletRequest request, HttpServletResponse response) {
    	String path_zip = request.getSession().getAttribute("train_path").toString();
		try {
			response.reset();
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(path_zip.split("/")[path_zip.split("/").length-1].getBytes("GBK"), "ISO8859-1"));
			// 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path_zip));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            /*创建输出流*/
            ServletOutputStream servletOS = response.getOutputStream();
            servletOS.write(buffer);
            servletOS.flush();
            servletOS.close();
            //清空meeting_out
            deleteDirectory(getTrainpath()+"out");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 删除单个文件
	 *
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile2(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}
	
	/**
	 * 删除目录及目录下的文件
	 *
	 * @param dir
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("删除目录失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile2(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}
	
	/**
     * 复制文件
     * @param fromFile
     * @param toFile
     * @throws IOException 
     */
    public static void copyFile(File fromFile,File toFile) throws IOException{
        FileInputStream ins = new FileInputStream(fromFile);
        FileOutputStream out = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n=0;
        while((n=ins.read(b))!=-1){
            out.write(b, 0, n);
        }
        
        ins.close();
        out.close();
    }
	
	public static String getTrainpath(){
		String upload_file = AppConfig.HZB_PATH + "/"+"train/";
		try {
			File file =new File(upload_file);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		//解压路径
		return upload_file;
	}
	
	
public static void main(String[] args) {
	String test="abc,efg,hij";
	int one = test.lastIndexOf(",");
	System.out.println(test.substring((one+1),test.length()));
}
}