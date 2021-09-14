package com.insigma.siis.local.jftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.log4j.Logger;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.jftp.JFtpStatusEnum.ConnStatus;
import com.insigma.siis.local.jtrans.ZwhzFtpPath;

/**
 * FTP客户端工具类
 * @author gezh
 *
 */
public class JFTPClient {
	
	private static Logger log = Logger.getLogger(JFTPClient.class);
	
	/**
	 * FTPClient
	 */
	private FTPClient ftpClient;
	
	/**
	 * Ftp传输文件类型
	 */
	private int fileType;
	
	/**
	 * Ftp连接超时 单位秒
	 */
	private int timeOut = 5;
	
	/**
	 * 客户端字符集
	 */
	private static final String C_CHARSET = "UTF-8";
	
	/**
	 * 服务器端字符集
	 */
	private static final String S_CHARSET = "ISO-8859-1";
	
	
	
	/**
	 * 构造函数
	 * @param ftpClient
	 * @param fileType
	 */
	public JFTPClient(FTPClient ftpClient, int fileType) {
		super();
		this.ftpClient = ftpClient;
		this.fileType = fileType;
	}
	
	/**
	 * 构造函数
	 * @param ftpClient
	 * @param fileType
	 * @param timeOut
	 */
	public JFTPClient(FTPClient ftpClient, int fileType, int timeOut) {
		super();
		this.ftpClient = ftpClient;
		this.fileType = fileType;
		this.timeOut = timeOut;
	}
	
	/**
	 * 获取客户端工具类实例
	 * @return
	 */
	public static JFTPClient getInstance(){
		return new JFTPClient(new FTPClient(),FTP.BINARY_FILE_TYPE);
	}
	
	/**
	 * 获取客户端工具类实例
	 * @param timeOut
	 * @return
	 */
	public static JFTPClient getInstance(int timeOut){
		return new JFTPClient(new FTPClient(),FTP.BINARY_FILE_TYPE,timeOut);
	}
	
	/**
	 * 获取客户端工具类实例
	 * @param fileType
	 * @param timeOut
	 * @return
	 */
	public static JFTPClient getInstance(int fileType,int timeOut){
		return new JFTPClient(new FTPClient(),fileType,timeOut);
	}
	
	/**
	 * 路径客户端字符集转为服务器端字符集
	 * @param pathname
	 * @return
	 */
	private String cdoWithPath(String pathName){
		try {
			pathName = new String(pathName.getBytes(C_CHARSET), S_CHARSET);
		} catch (Exception e) {
			log.error("路径处理失败",e);
		}
		return pathName;
	}
	
	/**
	 * 路径服务器端字符集转为客户端字符集
	 * @param pathname
	 * @return
	 */
	private String sdoWithPath(String pathName){
		try {
			pathName = new String(pathName.getBytes(S_CHARSET),C_CHARSET);
		} catch (Exception e) {
			log.error("路径处理失败",e);
		}
		return pathName;
	}
	
	/**
	 * 检测服务器是否存在该文件
	 * @param pathName
	 * @return
	 * @throws FtpException
	 */
	public boolean checkRemoteFileExists(String pathName) throws FtpException{
		try{
			pathName = FileUtil.resolveLeftPath(pathName);
			FTPFile[] files = ftpClient.listFiles(cdoWithPath(pathName));
			List<FTPFile> filesList = Arrays.asList(files);
			if(filesList.size()>0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			throw new FtpException("检测"+pathName+"文件是否存在失败:"+e.getMessage());
		}
	}
	
	/**
	 * 检测服务器是否存在该文件夹
	 * @param pathName
	 * @return
	 * @throws FtpException
	 */
	public boolean checkRemoteDirExists(String pathName) throws FtpException{
		pathName = FileUtil.resolveLeftPath(pathName);
		String curDir = null;
		try{
			curDir = ftpClient.printWorkingDirectory();
			return ftpClient.changeWorkingDirectory(pathName);
		}catch(Exception e){
			throw new FtpException("检测"+pathName+"文件夹是否存在失败:"+e.getMessage());
		}finally{
			try {
				ftpClient.changeWorkingDirectory(curDir);
			} catch (IOException e) {
			}
		}
		
	}
	
	/**
	 * 连接FTP服务器
	 * @param hostname 主机名
	 * @param port 端口
	 * @param username 用户名
	 * @param password 密码
	 * @throws FTPException 
	 */
	public void connect(String hostname, int port, String username, String password) throws FtpException {
		try {
			log.info("开始连接FTP服务器...");
			ftpClient.setConnectTimeout(this.timeOut*1000); //设置超时时间
			ftpClient.connect(hostname, port);//连接服务器
			//ftpClient.setControlEncoding("ISO-8859-1");   
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) { //判断连接服务器是否成功
				if (ftpClient.login(username, password)) {
					ftpClient.setFileType(this.fileType);//必须在FTP连接之后设置文件传输类型
					ftpClient.changeWorkingDirectory("/");
					log.info(JFtpStatusEnum.getConnMean(ConnStatus.Login_Success));
				}else{
					throw new FtpException(JFtpStatusEnum.getConnMean(ConnStatus.Login_Fail));
				}
			}else{
				throw new FtpException(JFtpStatusEnum.getConnMean(ConnStatus.Connect_Fail));
			}
		}catch (Exception ex) {
			log.error("连接【"+hostname+"】FTP服务器异常",ex);
			throw new FtpException("连接【"+hostname+"】FTP服务器异常:"+ex.getMessage());
		}
	}
	
	/**
	 * 断开与远程服务器的连接
	 */
	public void disconnect() {
		try {
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
				log.info("断开FTP服务器成功");
			}
		} catch (Exception ex) {
			log.info("断开FTP服务器出错：" + ex.getMessage());
		}
	}
	
	/**
	 * 远程下载，下载者单个文件
	 * 
	 * @param remotefile 远程文件完整路径
	 * @param localfile 本地文件完整路径
	 * @throws FtpException 
	 */
	public void downSingleFile(String remotefile, String localfilepath) throws FtpException {
		remotefile = FileUtil.resolveLeftPath(remotefile);
		if (!localfilepath.equals("") || localfilepath != null) {
			String localFolderPath = localfilepath.substring(0,localfilepath.lastIndexOf("/"));
			File fLocalFolderPath = new File(localFolderPath);
			if (!fLocalFolderPath.exists()) {
				fLocalFolderPath.mkdirs();
			}
		}
		String fileName = remotefile.substring(remotefile.lastIndexOf("/") + 1);
		try {
			// 去除变量remote后面的'/'
			if (remotefile.endsWith("/"))
				remotefile = remotefile.substring(0, remotefile.length() - 1);
			// 处理路径字符集，并切换FTP工作目录
			ftpClient.changeWorkingDirectory(cdoWithPath(remotefile.substring(0,remotefile.lastIndexOf("/"))));
			downLoadFile(fileName, localfilepath);
		} catch (Exception ex) {
			log.error("下载文件异常",ex);
			throw new FtpException("下载文件异常:"+ex.getMessage());
		}
	}
	
	/**
	 * 从FTP服务器上下载文件
	 * @param remoteFileName 远程文件名称
	 * @param localFilePath 本地文件路径
	 * @return 上传的状态
	 */
	public boolean downLoadFile(String remoteFileName, String localFilePath) throws FtpException{
		boolean result = false;
		OutputStream out = null;
		InputStream in = null;
		try {
			ftpClient.enterLocalPassiveMode();
			// 设置传输方式
			ftpClient.setFileType(fileType);
			File localFile = new File(localFilePath);
			log.info("开始下载"+remoteFileName+"文件...");
			out = new FileOutputStream(localFile);
			in = ftpClient.retrieveFileStream(cdoWithPath(remoteFileName));
			byte[] bytes = new byte[1024];
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
			}
			in.close();
			out.close();
			result = ftpClient.completePendingCommand();//判断下载是否完成
			log.info(remoteFileName+"文件下载结束！");
			return result;
		} catch (Exception ex) {
			log.error("下载文件出错:" + ex.getMessage() + "\n" + "FTP文件名:" + remoteFileName + "\n" + "本地路径:" + localFilePath,ex);
			throw new FtpException("下载文件出错:" + ex.getMessage() + "\n" + "FTP文件名:" + remoteFileName + "\n" + "本地路径:" + localFilePath);
		} finally {
			try{
				if(in!=null){in.close();}
				if(out!=null){out.close();}
			}catch(Exception e){}
		}

	}
	
	/**
	 * 递归创建远程服务器目录
	 * 
	 * @param remote  远程服务器文件绝对路径
	 * @param filetype 1-文件 2-文件夹
	 * @return 目录创建是否成功
	 */
	public void createDirecroty(String remote, int filetype) throws FtpException{
		String directory = "";
		if (filetype == 1)// 文件
			directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (filetype == 2) {// 文件夹
			directory = FileUtil.resolveRigthPath(remote);
		}
		try {
			
			if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(cdoWithPath(directory))) {
				// 如果远程目录不存在，则递归创建远程服务器目录
				int start = 0;
				int end = 0;
				if (directory.startsWith("/")) {
					start = 1;
				} else {
					start = 0;
				}
				end = directory.indexOf("/", start);
				while (true) {
					String subDirectory = cdoWithPath(remote.substring(start, end));
					if (!ftpClient.changeWorkingDirectory(subDirectory)) {
						if (ftpClient.makeDirectory(subDirectory)) {
							ftpClient.changeWorkingDirectory(subDirectory);
						} else {
							log.error(directory + ":创建目录失败");
							throw new FtpException(directory + ":创建目录失败");
						}
					}
					start = end + 1;
					end = directory.indexOf("/", start);

					// 检查所有目录是否创建完毕
					if (end <= start) {
						break;
					}
				}
				log.info(directory + ":创建目录成功!");
			} 
		} catch (Exception ex) {
			log.error("创建"+remote+"目录失败!",ex);
			throw new FtpException("创建"+remote+"目录失败:"+ex.getMessage());
		}
	}

	
	/**
	 * 上传文件到FTP服务器
	 * 
	 * @param local 本地文件名称
	 * @param remote 远程文件路径
	 *  按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
	 * @throws FtpException 
	 */
	public void upload(String local, String remote) throws FtpException {
		try {
			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			// 设置以传输方式
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			// 对远程目录的处理
			//String remoteFileName = remote;
			if (remote == null || remote.equals("")) {
				throw new FtpException("远程文件路径不能为空");
			}
			if (remote.contains("/")) {
				//remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
				// 创建服务器远程目录结构，创建失败直接返回
				createDirecroty(remote, 1);
			}
			ftpClient.changeWorkingDirectory(remote);

			File f = new File(local);
			InputStream input = new FileInputStream(f);
			ftpClient.storeFile(cdoWithPath(remote), input);
			input.close();
			log.info(local + "上传成功!");
		} catch (Exception ex) {
			throw new FtpException("上传"+local+"文件失败:"+ex.getMessage());
		}
	}
	
	/**
	 * 修改文件名
	 * 
	 * @param oldname
	 * @param newname
	 * @return
	 */
	public boolean reName(String oldname, String newname) {
		boolean result = false;
		try {
			result = ftpClient.rename(cdoWithPath(oldname), cdoWithPath(newname));  
			return result;
		} catch (Exception ex) {
			log.error("重命名文件出错：" + ex.getMessage(),ex);
			return false;
		}
	}

	
    /** 
     * 删除文件夹
     * @param pathname  文件夹的地址 
     * @return true 表似成功，false 失败 
     */  
    public boolean removeDirectory(String pathName){ 
		boolean rtnBln = false;
		try {
			pathName = FileUtil.resolveLeftPath(pathName);
			FTPFile[] files = ftpClient.listFiles(cdoWithPath(pathName));
			for (FTPFile f : files) {
				String path = pathName + File.separator + f.getName();
				if (f.isFile()) {// 是文件就删除文件
					ftpClient.deleteFile(cdoWithPath(path));
				} else if (f.isDirectory()) {//文件夹则继续递归
					removeDirectory(path);
				}
			}
			// 每次删除文件夹以后就去查看该文件夹下面是否还有文件，没有就删除该空文件夹
			FTPFile[] isExistsFiles = ftpClient.listFiles(cdoWithPath(pathName));
			if (isExistsFiles.length == 0) {
				rtnBln = ftpClient.removeDirectory(cdoWithPath(pathName));
			} else {
			    rtnBln = false;
			}
		} catch (Exception e) {
			log.error(pathName + ":删除失败:" + e.getMessage());
			return false;
		}
		return rtnBln;
    }  
	
    /** 
     * 删除文件
     * @param pathname  文件夹的地址 
     * @return true 表似成功，false 失败 
     */  
    public boolean removeFile(String pathName){ 
		boolean rtnBln = false;
		try {
			pathName = FileUtil.resolveLeftPath(pathName);
			rtnBln = ftpClient.deleteFile(cdoWithPath(pathName));
		} catch (Exception e) {
			log.error(pathName + ":删除失败:" + e.getMessage());
			return false;
		}
		return rtnBln;
    }  
    
    /**
	 * 判断Ftp连接是否成功
	 * @return
	 */
	public boolean getConBln() {
		boolean bln = ftpClient.isConnected();
		if (!bln) {
			log.error("连接FTP服务器失败!");
		}
		return bln;
	}
    
	/**
	 * 检索文件包
	 * @param pathName
	 * @return
	 * @throws FtpException
	 */
	public List<String> findPackFilesByPath(String pathName) throws FtpException{
		List<String> mFileList = new ArrayList<String>();
		pathName = FileUtil.resolveLeftPath(pathName);
		String ftpFileName = null;
		try{
			FTPFile[] files = ftpClient.listFiles(cdoWithPath(pathName));
			for (FTPFile ftpFile : files) {
				if(ftpFile.isFile()){
					ftpFileName = sdoWithPath(ftpFile.getName()).toLowerCase();
					if(ftpFileName.startsWith("pack_") && ftpFileName.endsWith(".xml")){
//						mFileList.add(pathName + "/" + sdoWithPath(ftpFile.getName()));
						mFileList.add(sdoWithPath(ftpFile.getName()));
					}
				}
			}
		}catch(Exception e){
			log.error("检索"+pathName+"文件目录失败",e);
		}
		return mFileList;
	}
	
	
	/**
	 * 检索文件列表
	 * @param pathName
	 * @return
	 * @throws FtpException
	 */
	public List<String> findFilesByPath(String pathName) throws FtpException{
		List<String> mFileList = new ArrayList<String>();
		pathName = FileUtil.resolveLeftPath(pathName);
		try{
			FTPFile[] files = ftpClient.listFiles(cdoWithPath(pathName));
			for (FTPFile ftpFile : files) {
				if(ftpFile.isFile()){
					mFileList.add(pathName + "/" + sdoWithPath(ftpFile.getName()));
				}
			}
		}catch(Exception e){
			log.error("检索"+pathName+"文件目录失败",e);
		}
		return mFileList;
	}
	
	
	/**
	 * 获取文件夹下面所有文件的大小
	 * 
	 * @param remoteFolder
	 * @return
	 * @throws FtpException 
	 */
	public int getFolderTotal(String remoteFolder) throws FtpException {
		if (!this.getConBln())
			return 0;
		int lRtn = 0;
		int tmpFileSize = 0;
		int tmpFolderSize = 0;
		remoteFolder = FileUtil.resolveLeftPath(remoteFolder);
		try {
			FTPFile[] files = ftpClient.listFiles(cdoWithPath(remoteFolder));
			if (files.length > 0) {
				FTPFile ftpFile = null;
				List<FTPFile> fileList = Arrays.asList(files);
				for (int i = 0; i < fileList.size(); i++) {
					ftpFile = fileList.get(i);
					if (!ftpFile.getName().equals("..") && !ftpFile.getName().equals(".")) {
						if (ftpFile.isFile()) {
							tmpFileSize = Integer.parseInt(String.valueOf(ftpFile.getSize()));
							log.info(remoteFolder + "/" + ftpFile.getName() + ":文件大小:" + ftpFile.getSize());
						}
						if (ftpFile.isDirectory()) {
							tmpFolderSize = getFolderTotal(remoteFolder + "/" + ftpFile.getName());
						}
						lRtn = lRtn + tmpFolderSize + tmpFileSize;
						tmpFolderSize = 0;
						tmpFileSize = 0;
					}

				}
			} else {
				log.error(remoteFolder + ":路径错误!");
				throw new FtpException(remoteFolder + ":路径错误!");
			}
			return lRtn;
		} catch (Exception e) {
			log.error("获取"+remoteFolder + "文件大小失败",e);
			throw new FtpException("获取"+remoteFolder + "文件大小失败:"+e.getMessage());
		}
	}

	
	/**
	 * 获取文件大小
	 * 
	 * @param path
	 * @return
	 */
	public long getFileSize(String pathName) {
		long rtnValue = 0;
		pathName = FileUtil.resolveLeftPath(pathName);
		try {
			if (ftpClient.changeWorkingDirectory(pathName)) {
				FTPFile[] files = ftpClient.listFiles(cdoWithPath(pathName));
				if (files.length == 1) {
					rtnValue = files[0].getSize();
					log.info("文件大小:" + rtnValue);
				} else {
					log.error("获取文件大小失败:" + files.length);
				}
			}
		} catch (IOException e) {
			log.error("获取文件大小失败" + e.getMessage());
		}
		return rtnValue;
	}
	
	/**
	 * 在FTP服务器上创建文件夹
	 * 
	 * @param remotepath
	 * @throws IOException 
	 */
	public boolean createFtpDirecroty(String remotepath) throws FtpException {
		boolean rtnBln = false;
		if (!this.getConBln() || remotepath == null || remotepath.equals(""))
			return rtnBln;
		if (this.getConBln()) {
			try{
				createDirecroty(remotepath, 2);
			}catch(Exception e){
				try{
					ftpClient.changeWorkingDirectory("/");
				}catch(Exception ex){}
				return false;
			}
		}
		return rtnBln;
	}
	
	/**
	 * 获取当前工作目录
	 * @return
	 */
	public String printWorkingDirectory(){
		String workDir = null;
		try{
			workDir = ftpClient.printWorkingDirectory();
		}catch(Exception e){
			log.error("获取当前工作目录失败",e);
		}
		return workDir;
	}
	
	/**
	 * 获取文件夹的状态 true 代表空，false代表不为空
	 * 
	 * @param remoteFolder
	 * @return
	 */
	public boolean getFolderStatus(String remoteFolder) {
		if (!this.getConBln()) {
			log.info("连接FTP服务器失败!");
			return false;
		}
		int totalSize = 0;
		int tmpFileSize = 0;
		int tmpFolderSize = 0;
		remoteFolder = FileUtil.resolveLeftPath(remoteFolder);
		try {
			FTPFile[] files = ftpClient.listFiles(cdoWithPath(remoteFolder));
			if (files.length > 0) {
				FTPFile ftpFile = null;
				List<FTPFile> fileList = Arrays.asList(files);
				for (int i = 0; i < fileList.size(); i++) {
					ftpFile = fileList.get(i);
					if (!ftpFile.getName().equals("..") && !ftpFile.getName().equals(".")) {
						if (ftpFile.isFile()) {
							tmpFileSize = Integer.parseInt(String.valueOf(ftpFile.getSize()));
							log.info(remoteFolder + "/" + ftpFile.getName()+ ":文件大小:" + ftpFile.getSize());
						}
						if (ftpFile.isDirectory()) {
							tmpFolderSize = getFolderTotal(remoteFolder + "/" + ftpFile.getName());
						}
						totalSize = totalSize + tmpFolderSize + tmpFileSize;
						if (totalSize > 0)
							return false;
						tmpFolderSize = 0;
						tmpFileSize = 0;
					}

				}
			}
		} catch (Exception e) {
			log.error("获取文件夹状态失败",e);
			return false;
		}
		return true;
	}
	
	
	public static void main(String[] args) throws FtpException {
		JFTPClient ft = JFTPClient.getInstance();
		ft.connect("127.0.0.1", 21, "test", "test");
		ft.findPackFilesByPath(ZwhzFtpPath.DM_DOWN);
		ft.disconnect();
	}
}
