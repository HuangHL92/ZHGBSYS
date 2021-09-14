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
 * ģ��ɲ�����
 * @author a
 *
 */
public class AllFileDownPageModel extends PageModel{
	private static final String separator =System.getProperty("file.separator");
	/**
	 * ҳ���ʼ��
	 */
	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ������������������
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
		String classPath = AppConfig.HZB_PATH+"/zhgbdownload"+"/ȫ�̼�ʵ_"+DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss");
		File tmpFile = new File(classPath);
	    if (!tmpFile.exists()) {
	    	 //������ʱĿ¼
	    	 tmpFile.mkdirs();
	    }
	    
	    String fileNamesIn = fileNames.replace(",", "','");
	    
//	    classPath = classPath+"/ȫ�̼�ʵ_"+DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss");
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
			this.setMainMessage("û����Ӧ������Ϣ��");
			return EventRtnType.FAILD;
		}
		String savePath = "";
		
		for(HashMap<String,Object> map:list){
			String jsa02 = "";
			String a0101 = map.get("a0101").toString();
			if("JS02".equals(map.get("jsa02"))){
				jsa02 = "����";
			}else if("JS99".equals(map.get("jsa02"))){
				jsa02 = "���˺���ȡ���";
			}else if("JS19".equals(map.get("jsa02"))){
				jsa02 = "�����Ƽ�";
			}else if("JS14".equals(map.get("jsa02"))){
				jsa02 = "��֯����";
			}else if("JS07".equals(map.get("jsa02"))){
				jsa02 = "���۾���";
			}else if("JS08".equals(map.get("jsa02"))){
				jsa02 = "��ǰ��ʾ";
			}
			String tabDir =makePath(jsa02, classPath);
			String nameDir = makePath(a0101, tabDir);
			String oldPath = AppConfig.HZB_PATH+"/"+map.get("jsa07").toString()
					+"/"+map.get("jsa00").toString();
			//���ļ�������ȥ
			copyFile(oldPath,nameDir);
			//�ļ�����
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
		//�����µı���Ŀ¼
		String dir = savePath  + "/" + filename;  
		//File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
		File file = new File(dir);
		//���Ŀ¼������
		if(!file.exists()){
			//����Ŀ¼
			file.mkdirs();
		}
		return dir;
	}
	
	 /*
     * ʵ���ļ��Ŀ�ؐ
     * @param srcPathStr
     *          Դ�ļ��ĵ�ַ��Ϣ
     * @param desPathStr
     *          Ŀ���ļ��ĵ�ַ��Ϣ 
     */
    private static void copyFile(String srcPathStr, String desPathStr) {
        //1.��ȡԴ�ļ�������
        String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("\\")+1); //Ŀ���ļ���ַ
        System.out.println(newFileName);
        desPathStr = desPathStr + File.separator + newFileName; //Դ�ļ���ַ
        System.out.println(desPathStr);

        try{
            //2.�����������������
            FileInputStream fis = new FileInputStream(srcPathStr);
            FileOutputStream fos = new FileOutputStream(desPathStr);                

            //�������˹���
            byte datas[] = new byte[1024*8];
            //��������
            int len = 0;
            //ѭ����ȡ����
            while((len = fis.read(datas))!=-1){
                fos.write(datas,0,len);
            }
            //3.�ͷ���Դ
            fis.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        } 
    }
    
 // ɾ���ļ���
 	// param folderPath �ļ�����������·��
 	public static void delFolder(String folderPath) {
 		try {
 			delAllFile(folderPath); // ɾ����������������
 			String filePath = folderPath;
 			filePath = filePath.toString();
 			java.io.File myFilePath = new java.io.File(filePath);
 			myFilePath.delete(); // ɾ�����ļ���
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
 	
 	// ɾ��ָ���ļ����������ļ�
 	// param path �ļ�����������·��
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
 				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
 				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
 				flag = true;
 			}
 		}
 		return flag;
 	}

}
