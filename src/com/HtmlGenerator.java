package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.insigma.odin.framework.util.MD5;


/** 
 * ��̬ҳ�����漼����ͻ������������UTF-8�� 
 * @author ������ 
 * 
 */  
public class HtmlGenerator {  
    HttpClient httpClient = new HttpClient(); //HttpClientʵ��  
    GetMethod getMethod =null; //GetMethodʵ��  
    BufferedWriter fw = null;  
    String page = null;  
    String webappname = null;  
    BufferedReader br = null;  
    InputStream in = null;  
    StringBuffer sb = null;  
    String line = null;   
    //���췽��  
    public HtmlGenerator(String webappname){  
        this.webappname = webappname;  
          
    }  
      
    /** ����ģ�漰����������̬ҳ�� */  
    public boolean createHtmlPage(String url,String htmlFileName,String sessionId){  
        boolean status = false;   
        int statusCode = 0;               
        try{  
            //����һ��HttpClientʵ���䵱ģ�������  
            //����httpclient��ȡ����ʱʹ�õ��ַ���  
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");           
            //����GET������ʵ��  
            getMethod = new GetMethod(url);  
            //ʹ��ϵͳ�ṩ��Ĭ�ϵĻָ����ԣ��ڷ����쳣ʱ���Զ�����3��  
            getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());  
            //����Get�����ύ����ʱʹ�õ��ַ���,��֧�����Ĳ�������������  
            getMethod.addRequestHeader("Content-Type","text/html;charset=GBK");  
            getMethod.setRequestHeader("SessionId", sessionId);
            getMethod.setRequestHeader("Cookie", "JSESSIONID=" + sessionId);
            //ִ��Get������ȡ�÷���״̬�룬200��ʾ��������������Ϊ�쳣  
            statusCode = httpClient.executeMethod(getMethod);             
            if (statusCode!=200) {  
                System.out.println("��̬ҳ�������ڽ���"+url+"������̬ҳ��"+htmlFileName+"ʱ����!");  
            }else{  
                //��ȡ�������  
                sb = new StringBuffer();  
                in = getMethod.getResponseBodyAsStream();  
                //br = new BufferedReader(new InputStreamReader(in));//�˷���Ĭ�ϻ����룬������ʱ�ڵ�����������ķ����ſ���  
                br = new BufferedReader(new InputStreamReader(in,"GBK"));  
                while((line=br.readLine())!=null){  
                    sb.append(line+"\n");  
                }  
                if(br!=null)br.close();  
                page = sb.toString();  
                //��ҳ���е����·���滻�ɾ���·������ȷ��ҳ����Դ��������  
                page = formatPage(page);  
                //���������д��ָ���ľ�̬HTML�ļ��У�ʵ�־�̬HTML����  
                writeHtml(htmlFileName,page);  
                status = true;  
            }             
        }catch(Exception ex){  
            System.out.println("��̬ҳ�������ڽ���"+url+"������̬ҳ��"+htmlFileName+"ʱ����:"+ex.getMessage());           
        }finally{  
            //�ͷ�http����  
            getMethod.releaseConnection();  
        }  
        return status;  
    }  
   
  
    
    
    //���������д��ָ���ľ�̬HTML�ļ���  
    private synchronized void writeHtml(String htmlFileName,String content) throws Exception{  
        fw = new BufferedWriter(new FileWriter(htmlFileName));  
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(htmlFileName),"GBK");  
        fw.write(page);   
        if(fw!=null)fw.close();       
    }  
      
    //��ҳ���е����·���滻�ɾ���·������ȷ��ҳ����Դ��������  
    private String formatPage(String page){       
        page = page.replaceAll("\\.\\./\\.\\./\\.\\./", webappname+"/");  
        page = page.replaceAll("\\.\\./\\.\\./", webappname+"/");  
        page = page.replaceAll("\\.\\./", webappname+"/");            
        return page;  
    }  
      
    //���Է���  
    public static void genHTML(String sessionId){  
        HtmlGenerator h = new HtmlGenerator("hzb");  
        h.createHtmlPage("http://localhost:8088/hzb/radowAction.do?method=doEvent&pageModel=pages.customquery.Group&subWinId=group","d:/a.html",sessionId);  
        System.out.println("��̬ҳ���Ѿ����ɵ�d:/a.html");  
          
    }  
  
}  