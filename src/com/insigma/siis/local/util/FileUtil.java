package com.insigma.siis.local.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.insigma.odin.framework.AppException;
import com.insigma.siis.local.business.modeldb.SaveFromFile;

public class FileUtil {
	private static Logger log = Logger.getLogger(FileUtil.class);
	/*****************
	 * @author lee
	 * @comment xml�ļ�����
	 * @param node_
	 * @param att_
	 * @return
	 * @throws DocumentException
	 */
	public static String getXmlAttr(String node_,String att_) throws DocumentException{
		String xml="<Context><Resource name='jdbc/insiis' type='javax.sql.DataSource' username='medtest' password='medtest' "+
			" driverClassName='oracle.jdbc.driver.OracleDriver' validationQuery='select 1 from dual' url='jdbc:oracle:thin:@localhost:1521:orcl' "+ 
			" maxIdle='2' maxWait='5000' maxActive='10'/></Context> ";
		Document doc=null; 
		Element root=null;
		Element node=null;
		Attribute attr=null;
		if(xml!=null){
		   doc = DocumentHelper.parseText(xml); 
		   root= doc.getRootElement();
		   node=root.element("Resource");  
		   attr=node.attribute("url");
		   log.info(attr.getText());
		}
		return attr.getText();
	}
    /*****************
     * @author lee
     * @comment �����ļ�
     * @param filefullname ȫ·��
     * @return
     * @throws IOException
     */
    public  static File createFile(String filefullname) throws IOException{
    	File file = new File(filefullname); 
		if (!file.exists()) {
			file.createNewFile();
		} else {
			file.delete();
			file.createNewFile();
		}
    	return file;
    } 
	/*****************
     * @author lee
     * @category �����ļ�
     * @param dirpath  Ŀ¼����
     * @param filename �ļ�����
     * @param endwith  �ļ���׺
     * @return
     * @throws IOException 
     */
    public  static File createFile(String dirpath,String filename,String endwith) throws IOException{
    	File dir = new File(dirpath); 
		dir.mkdirs();
		String filepath = dirpath + System.getProperty("file.separator")+ filename+ endwith;
		File file = new File(filepath);
		if (!file.exists()) {
			file.createNewFile();
		} else {
			file.delete();
			file.createNewFile();
		}
    	return file;
    } 
	/*********************
	 * @author lee
	 * @comment д���ļ�(����һ)
	 * @param file
	 * @param newStr
	 * @return
	 * @throws IOException
	 */
	public static boolean writeTxtFile (File file,String newStr) throws IOException { 
		boolean flag = false; 
		String filein = newStr + "\r\n"; 
		//String filein = newStr + System.getProperty("line.separator");
		String temp = ""; 
		FileInputStream fis = null; 
		InputStreamReader isr = null; 
		BufferedReader br = null; 

		FileOutputStream fos = null; 
		PrintWriter pw = null; 
		try { 
			//�ȶ�ȡԭ���ļ����ݣ�Ȼ�����д����� , ���ļ����������� 
			fis = new FileInputStream(file); 
			isr = new InputStreamReader(fis); 
			br = new BufferedReader(isr); 
			StringBuffer sb = new StringBuffer(); 
			// ������ļ�ԭ�е����� 
			for (int j = 1; (temp = br.readLine()) != null; j++) { 
				sb = sb.append(temp); 
				//sb = sb.append(System.getProperty("line.separator")); 
				sb = sb.append("\r\n"); 
			} 
			//׷��һ���µ�����
			sb.append(filein); 
			//�����
			fos = new FileOutputStream(file); 
			pw = new PrintWriter(fos); 
			pw.write(sb.toString().toCharArray()); 
			pw.flush(); 
			flag = true; 
		} catch (IOException e) { 
			throw e; 
		} finally { 
			if (pw != null) { 
				pw.close(); 
			} 
			if (fos != null) { 
				fos.close(); 
			} 
			if (br != null) { 
				br.close(); 
			} 
			if (isr != null) { 
				isr.close(); 
			} 
			if (fis != null) { 
				fis.close(); 
			} 
		} 
		return flag; 
	} 
	/*******************
	 * @author lee
	 * @comment д���ļ�����������
	 * @param file �ļ�����
	 * @param fileContent  д������
	 * @throws IOException
	 */
	public static void writeFile(File file, String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(file,true);//ʵ�ֲ�����׷�ӵ��ļ���
        //FileWriter fileWriter = new FileWriter(file);//���ǵ�ԭ��������
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(fileContent);
        printWriter.close();
        fileWriter.close();
    }
	/**********
	 * @commets clob����ת��ΪString
	 * @author lee
	 * @param clob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
    public static String ParseString(Clob clob) throws SQLException, IOException { 
        String reString = ""; 
        Reader is = clob.getCharacterStream();// �õ��� 
        BufferedReader br = new BufferedReader(is); 
        String s = br.readLine(); 
        StringBuffer sb = new StringBuffer(); 
        while (s != null) {
            sb.append(s); 
            s = br.readLine(); 
        } 
        reString = sb.toString(); 
        return reString; 
    } 
    /**********
	 * @commets clob����ת��ΪString��д���ļ�file
	 * @author lee
	 * @param clob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
    public static String ParseString(Clob clob,File file) throws SQLException, IOException { 
        String reString = ""; 
        Reader is = clob.getCharacterStream();// �õ��� 
        BufferedReader br = new BufferedReader(is); 
        String s = br.readLine(); 
        StringBuffer sb = new StringBuffer(); 
        while (s != null) {
        	writeFile(file,s);//׷��д��һ��
        	//writeTxtFile(file,s);//д��һ��
            sb.append(s); 
            s = br.readLine(); 
        } 
        reString = sb.toString(); 
        return reString; 
    } 
    public static String read4String(File fileName)throws Exception{  
    	  StringBuffer result=new StringBuffer();  
    	  FileReader fileReader=null;  
    	  BufferedReader bufferedReader=null;  
    	  try{  
    	   fileReader=new FileReader(fileName);  
    	   bufferedReader=new BufferedReader(fileReader);  
    	   try{  
    	    String read=null;  
    	    while((read=bufferedReader.readLine())!=null){ 
    	    	result.append(read+"\r\n");
    	    }  
    	   }catch(Exception e){  
    	    e.printStackTrace();  
    	   }  
    	  }catch(Exception e){  
    	   e.printStackTrace();  
    	  }finally{  
    	   if(bufferedReader!=null){  
    	    bufferedReader.close();  
    	   }  
    	   if(fileReader!=null){  
    	    fileReader.close();  
    	   }  
    	  }  
    	  //log.info("��ȡ�������ļ������ǣ�"+"\r\n"+result.toString()); 
    	  return result.toString();  
    	 }  
    public static void main(String args[]) throws DocumentException, ClassNotFoundException, SQLException, IOException, AppException{
		getXmlAttr("Resource","username");
		log.info("��ǰ����ϵͳ�汾��"+System.getProperty("os.name")); 
	}

    
    public static String read5String(File fileName)throws Exception{  
  	  StringBuffer result=new StringBuffer(); 
  	  FileInputStream fileInputStream =null;
  	  BufferedReader bufferedReader=null;  
  	  InputStreamReader inputStreamReader=null;
  	  try{  
  		  fileInputStream=new FileInputStream(fileName);
  		  inputStreamReader=new InputStreamReader(fileInputStream,"UTF-8");
  		  bufferedReader=new BufferedReader(inputStreamReader);
  		  try{  
  			  String read=null;  
  			  while((read=bufferedReader.readLine())!=null){ 
  				  result.append(read+"\r\n");
		    }  
	  	  }catch(Exception e){  
	  		  e.printStackTrace();  
	  	  }  
  	  }catch(Exception e){  
  		  e.printStackTrace();  
  	  }finally{  
  		  if(bufferedReader!=null){  
  			  bufferedReader.close();  
	  	  }  
  		  if(inputStreamReader!=null){  
  			  inputStreamReader.close();  
	  	  } 
  		  if(fileInputStream!=null){  
  			  fileInputStream.close();  
	  	  } 
//  	   if(fileReader!=null){  
//  	    fileReader.close();  
//  	   }  
  	  }  
  	  //log.info("��ȡ�������ļ������ǣ�"+"\r\n"+result.toString()); 
  	  return result.toString();  
  	 }  
    
    public static String read6String(File fileName)throws Exception{  
    	StringBuffer result=new StringBuffer(); 
      	FileInputStream fileInputStream =null;
      	BufferedReader bufferedReader=null;  
      	InputStreamReader inputStreamReader=null;
	  	try{  
	  		fileInputStream = new FileInputStream(fileName);  
			UnicodeReader ur = new UnicodeReader(fileInputStream, "utf-8");  
			bufferedReader = new BufferedReader(ur);  
	  		  try{  
	  			  String read=null;  
	  			  while((read=bufferedReader.readLine())!=null){ 
	  				  result.append(read+"\r\n");
			    }  
		  	  }catch(Exception e){  
		  		  e.printStackTrace();  
		  	  }  
	  	  }catch(Exception e){  
	  		  e.printStackTrace();  
	  	  }finally{  
	  		  if(bufferedReader!=null){  
	  			  bufferedReader.close();  
		  	  }  
	  		  if(inputStreamReader!=null){  
	  			  inputStreamReader.close();  
		  	  } 
	  		  if(fileInputStream!=null){  
	  			  fileInputStream.close();  
		  	  } 
//	  	   if(fileReader!=null){  
//	  	    fileReader.close();  
//	  	   }  
	  	  }  
	  	  //log.info("��ȡ�������ļ������ǣ�"+"\r\n"+result.toString()); 
	  	  char c = (char)Integer.parseInt("00a0", 16);
	  	  return result.toString().replace( c, ' '); 
    }  
}
