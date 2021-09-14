package com.insigma.siis.local.jftp;

import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

/**
 * FTP状态枚举类
 * @author gezh
 *
 */
public class JFtpStatusEnum {
	
	public enum ConnStatus {   
	    Connect_Fail,      //连接FTP失败   
	    Connect_Success,   //连接FTP成功  
	    Login_Fail,      //登录FTP失败   
	    Login_Success   //登录FTP成功   
	} 
	
	public enum UpStatus {   
	    Create_Directory_Fail,      //远程服务器相应目录创建失败   
	    Create_Directory_Success,   //远程服务器闯将目录成功   
	    Upload_New_File_Success,    //上传新文件成功   
	    Upload_New_File_Failed,     //上传新文件失败   
	    File_Exist,                 //文件已经存在   
	    Remote_Bigger_Local,        //远程文件大于本地文件   
	    Upload_From_Break_Success,  //断点续传成功   
	    Upload_From_Break_Failed,   //断点续传失败   
	    Delete_Remote_Faild;        //删除远程文件失败   
	} 
	public enum DownStatus {   
	    Remote_File_Noexist,    		//远程文件不存在   
	    Local_Bigger_Remote,    		//本地文件大于远程文件   
	    Download_From_Break_Success,    //断点下载文件成功   
	    Download_From_Break_Failed,     //断点下载文件失败   
	    Download_New_Success,           //全新下载文件成功   
	    Download_New_Failed,            //全新下载文件失败   
	    Local_Folder_Create_Faill;      //本地文件夹创建失败
	}  
	
	public static String getConnMean(ConnStatus cs){
		String result = "";
		switch(cs){
			case Connect_Fail:result = "连接FTP失败"; break;
			case Connect_Success:result = "连接FTP成功";break;
			case Login_Fail:result = "登录FTP失败,请核对用户名及密码"; break;
			case Login_Success:result = "登录FTP成功";break;
		}
		return result;
	}
	
	public static String getUsMean(UpStatus us){
		String result = "";
		switch(us){
			case Create_Directory_Fail:result = "远程服务器相应目录创建失败"; break;
			case Create_Directory_Success:result = "远程服务器闯将目录成功";break;
			case File_Exist:result = "文件已经存在";break;
			case Upload_New_File_Success:result = "上传新文件成功";break;
			case Upload_New_File_Failed:result = "上传新文件失败";break;
			case Remote_Bigger_Local:result = "远程文件大于本地文件";break;
			case Upload_From_Break_Success:result = "断点续传成功";break;
			case Upload_From_Break_Failed:result = "断点续传失败";break;
			case Delete_Remote_Faild:result = "删除远程文件失败";break;
		}
		return result;
	}
	
	public static String getDsMean(DownStatus ds){
		String result = "";
		switch(ds){
			case Remote_File_Noexist:result = "远程文件不存在";break;
			case Local_Bigger_Remote:result = "本地文件大于远程文件";break;
			case Download_From_Break_Success:result = "断点下载文件成功";break;
			case Download_From_Break_Failed:result = "断点下载文件失败";break;
			case Download_New_Success:result = "全新下载文件成功";break;
			case Download_New_Failed:result = "全新下载文件失败";break;
			case Local_Folder_Create_Faill:result = "本地文件夹创建失败";break;
		}
		return result;
	}
	
	public static void main(String[] args){
		CommonQueryBS.systemOut(JFtpStatusEnum.getUsMean(UpStatus.Create_Directory_Fail));
	}
}
