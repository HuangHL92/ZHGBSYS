package com.insigma.siis.local.business.comm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import com.insigma.odin.framework.BSSupport;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class VerWindowBS extends BSSupport{
	
	public static String getVersionName(){
		String sql = " select t.v_name from Sversion t order by v_time desc ";
		String str =  HBUtil.getHBSession().createQuery(sql).list().get(0).toString();
		return str;
	}
	
	public static String getVersionTime(){
		String sql = " select t.v_time from Sversion t order by v_time desc ";
		String str =  HBUtil.getHBSession().createQuery(sql).list().get(0).toString();
		return str;
	}
	
	public static String getSoftwareVersionName(){
		 try {
            String encoding="GBK";
            String path = VerWindowBS.class.getClassLoader().getResource("../../programVersion.txt").getPath();
            path = path.replaceAll("%20", " ");
            File file=new File(path);
            String readLastLine = readLastLine(file, "UTF-8");
            /*if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    System.out.println(lineTxt);
                }
	                 read.close();
	             return  bufferedReader.toString();*/
            return readLastLine;
	     } catch (Exception e) {
	         System.out.println("读取文件内容出错");
	         e.printStackTrace();
	     }
		return "2";
        
	        
	         
	        
	}
	public static String getDBVersionName(){
		String sql = " select t.v_time from v_version t order by v_seq desc ";
		String str =  HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}
	 public static String readLastLine(File file, String charset) throws IOException { 
        if (!file.exists() || file.isDirectory() || !file.canRead()) {  
          return "";  
        }  
        RandomAccessFile raf = null;
        String line = "";
        try {  
          raf = new RandomAccessFile(file, "r");  
          long len = raf.length();  
          if (len == 0L) {  
            return "";  
          } else {  
            long pos = len - 1;  
            while (pos > 0) {  
              pos--;  
              raf.seek(pos);  
              Byte b =  raf.readByte();
              if (raf.readByte() == '\n') {
           	  line =  new String(raf.readLine().getBytes("ISO-8859-1"), "GBK"); 
           	  if(line != null && "".equals(line.trim())){
           		  continue;
           	  }else{
           		  break;  
           	  }
              }
             
            }  
            if("".equals(line)){
            	raf.seek(0);  
            	line =  new String(raf.readLine().getBytes("ISO-8859-1"), "GBK"); 
            }
            return line;
          }  
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } finally {  
          if (raf != null) {  
            try {  
              raf.close();  
            } catch (Exception e2) {  
            }  
          }  
        }  
        return "";  
      } 

}
