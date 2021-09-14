package com.insigma.siis.local.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/** 
 *  ��ѹZip�ļ������� 
 * 
 */  
public class ZipUtil
{
    private static final int buffer = 2048;  
    
  /** 
   * ��ѹZip�ļ� 
   * @param path �ļ�Ŀ¼ 
   * @throws Exception 
   */  
  public static void unZip(String path) throws Exception  
      {  
       int count = -1;  
       String savepath = "";  

       File file = null;  
       InputStream is = null;  
       FileOutputStream fos = null;  
       BufferedOutputStream bos = null;  

       savepath = path.substring(0, path.lastIndexOf(".")) + File.separator; //�����ѹ�ļ�Ŀ¼  
       new File(savepath).mkdir(); //��������Ŀ¼  
       ZipFile zipFile = null;  
       try  
       {  
           zipFile = new ZipFile(path,"gbk"); //���������������  
           Enumeration<?> entries = zipFile.getEntries();  

           while(entries.hasMoreElements())  
           {  
               byte buf[] = new byte[buffer];  

               ZipEntry entry = (ZipEntry)entries.nextElement();  

               String filename = entry.getName();  
               boolean ismkdir = false;  
               if(filename.lastIndexOf("/") != -1){ //�����ļ��Ƿ�����ļ���  
                  ismkdir = true;  
               }  
               filename = savepath + filename;  

               if(entry.isDirectory()){ //������ļ����ȴ���  
                  file = new File(filename);  
                  file.mkdirs();  
                   continue;  
               }  
               file = new File(filename);  
               if(!file.exists()){ //�����Ŀ¼�ȴ���  
                  if(ismkdir){  
                  new File(filename.substring(0, filename.lastIndexOf("/"))).mkdirs(); //Ŀ¼�ȴ���  
                  }  
               }  
               file.createNewFile(); //�����ļ�  

               is = zipFile.getInputStream(entry);  
               fos = new FileOutputStream(file);  
               bos = new BufferedOutputStream(fos, buffer);  

               while((count = is.read(buf)) > -1)  
               {  
                   bos.write(buf, 0, count);  
               }  
               bos.flush();  
               bos.close();  
               fos.close();  

               is.close();  
           }  

           zipFile.close();  

       }catch(Exception ioe){  
           ioe.printStackTrace();
           throw new Exception("Zip��ѹʧ��");
       }finally{  
              try{  
              if(bos != null){  
                  bos.close();  
              }  
              if(fos != null) {  
                  fos.close();  
              }  
              if(is != null){  
                  is.close();  
              }  
              if(zipFile != null){  
                  zipFile.close();  
              }  
              }catch(Exception e) {  
                  e.printStackTrace();  
                  throw new Exception("Zip��ѹʧ��");
              }  
          }  
      }  


/*public static void main(String[] args)  
    {  
        unZip("F:\\110000002.zip"); 
        String f = "F:\\110000002";
        File file = new File(f);
        String[] test=file.list();
        for(int i=0;i<test.length;i++){
            System.out.println(test[i]);
        }
        
        System.out.println("------------------");
        
        String fileName = "";
        
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                System.out.println("��     ����"+tempList[i]);
                
                fileName = tempList[i].getName();
                
                System.out.println("�ļ�����"+fileName);
            }
            if (tempList[i].isDirectory()) {
                System.out.println("�ļ��У�"+tempList[i]);
            }
        }
    }  */
}

