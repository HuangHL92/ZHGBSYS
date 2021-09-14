package com.insigma.siis.local.jftp;

import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

/**
 * FTP״̬ö����
 * @author gezh
 *
 */
public class JFtpStatusEnum {
	
	public enum ConnStatus {   
	    Connect_Fail,      //����FTPʧ��   
	    Connect_Success,   //����FTP�ɹ�  
	    Login_Fail,      //��¼FTPʧ��   
	    Login_Success   //��¼FTP�ɹ�   
	} 
	
	public enum UpStatus {   
	    Create_Directory_Fail,      //Զ�̷�������ӦĿ¼����ʧ��   
	    Create_Directory_Success,   //Զ�̷���������Ŀ¼�ɹ�   
	    Upload_New_File_Success,    //�ϴ����ļ��ɹ�   
	    Upload_New_File_Failed,     //�ϴ����ļ�ʧ��   
	    File_Exist,                 //�ļ��Ѿ�����   
	    Remote_Bigger_Local,        //Զ���ļ����ڱ����ļ�   
	    Upload_From_Break_Success,  //�ϵ������ɹ�   
	    Upload_From_Break_Failed,   //�ϵ�����ʧ��   
	    Delete_Remote_Faild;        //ɾ��Զ���ļ�ʧ��   
	} 
	public enum DownStatus {   
	    Remote_File_Noexist,    		//Զ���ļ�������   
	    Local_Bigger_Remote,    		//�����ļ�����Զ���ļ�   
	    Download_From_Break_Success,    //�ϵ������ļ��ɹ�   
	    Download_From_Break_Failed,     //�ϵ������ļ�ʧ��   
	    Download_New_Success,           //ȫ�������ļ��ɹ�   
	    Download_New_Failed,            //ȫ�������ļ�ʧ��   
	    Local_Folder_Create_Faill;      //�����ļ��д���ʧ��
	}  
	
	public static String getConnMean(ConnStatus cs){
		String result = "";
		switch(cs){
			case Connect_Fail:result = "����FTPʧ��"; break;
			case Connect_Success:result = "����FTP�ɹ�";break;
			case Login_Fail:result = "��¼FTPʧ��,��˶��û���������"; break;
			case Login_Success:result = "��¼FTP�ɹ�";break;
		}
		return result;
	}
	
	public static String getUsMean(UpStatus us){
		String result = "";
		switch(us){
			case Create_Directory_Fail:result = "Զ�̷�������ӦĿ¼����ʧ��"; break;
			case Create_Directory_Success:result = "Զ�̷���������Ŀ¼�ɹ�";break;
			case File_Exist:result = "�ļ��Ѿ�����";break;
			case Upload_New_File_Success:result = "�ϴ����ļ��ɹ�";break;
			case Upload_New_File_Failed:result = "�ϴ����ļ�ʧ��";break;
			case Remote_Bigger_Local:result = "Զ���ļ����ڱ����ļ�";break;
			case Upload_From_Break_Success:result = "�ϵ������ɹ�";break;
			case Upload_From_Break_Failed:result = "�ϵ�����ʧ��";break;
			case Delete_Remote_Faild:result = "ɾ��Զ���ļ�ʧ��";break;
		}
		return result;
	}
	
	public static String getDsMean(DownStatus ds){
		String result = "";
		switch(ds){
			case Remote_File_Noexist:result = "Զ���ļ�������";break;
			case Local_Bigger_Remote:result = "�����ļ�����Զ���ļ�";break;
			case Download_From_Break_Success:result = "�ϵ������ļ��ɹ�";break;
			case Download_From_Break_Failed:result = "�ϵ������ļ�ʧ��";break;
			case Download_New_Success:result = "ȫ�������ļ��ɹ�";break;
			case Download_New_Failed:result = "ȫ�������ļ�ʧ��";break;
			case Local_Folder_Create_Faill:result = "�����ļ��д���ʧ��";break;
		}
		return result;
	}
	
	public static void main(String[] args){
		CommonQueryBS.systemOut(JFtpStatusEnum.getUsMean(UpStatus.Create_Directory_Fail));
	}
}
