package com.insigma.siis.local.pagemodel.publicServantManage;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpRequest;
import org.apache.mina.core.buffer.BufferDataException;
import org.hibernate.SQLQuery;

import com.alibaba.fastjson.JSON;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.MediaEntity;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.xbrm2.YNTPFileExportBS;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.utils.CommonQueryBS;
/**
 * �����ý����ϢУ��Servlet
 * @author luol
 * @time 2020/02/07 17��26��
 *
 */
public class PlayInVideoServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MediaEntity media = new MediaEntity();
		HBSession sess = HBUtil.getHBSession();
		//��ȡ��Ӧ����Ա��Ϣ
		//��ȡ��ԱID
		String a0000=request.getParameter("a0000");
		//��ȡ��ƵID
		String jsa00=request.getParameter("jsa00");
		System.out.println("ID:"+a0000);
		 try {
			HashMap<String,Object> users= CommonQueryBS.getMapBySQL("select a0101,code_name ,age,A0192,A0198 from A01,code_value where code_type='GB3304' and code_value = a0117 and a0000='"+a0000+"'");
			//����Ա��Ϣ��ӵ�media��
			media.setName(users.get("a0101")+"");
			media.setAge(users.get("age")+"");
			media.setNation(users.get("code_name")+"");
			media.setPost(users.get("a0192")+"");
			media.setPhoto(users.get("a0198")+"");
			//�����Ƶ�б�media��
			media.setVideoList(getVideoList(jsa00, a0000, request,true));
			//���չʾ��Ƶ�б�
			media.setShowVideoList(getVideoList(jsa00, a0000, request,false));
			//������Ƶ����
			String jsa02=(String)sess.createSQLQuery("select jsa02 from tpb_media where jsa00='"+jsa00+"'").uniqueResult();
			media.setVideoTitle(jsa02);
			//���PDF�����б�
			media.setShowPdfList(getFileList(a0000, request));

			String mediastr=JSON.toJSONString(media);
			PrintWriter pw = response.getWriter();
			pw.write(mediastr);
			System.out.println("��װSession����"+mediastr);
			
		 } catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	/**
	 * ��ȡ��Ƶ�б��Ұѵ�ǰ���ŵ���Ƶ���ڵ�һ��
	 */
	private List<HashMap<String,String>> getVideoList(String jsa00,String a0000,HttpServletRequest request,boolean flage){
		HBSession sess = HBUtil.getHBSession();
		//������ƵID�����Ӧ�ļ���ID
		String a1700=(String)sess.createSQLQuery("select a1700 from tpb_media where jsa00='"+jsa00+"'").uniqueResult();
		List<HashMap<String,String>> videolist=new ArrayList<HashMap<String,String>>();
		//��ȡչʾ��Ƶ�б�
		List<HashMap<String,String>> showVideolist=new ArrayList<HashMap<String,String>>();
		System.out.println(a1700);
		//���ݼ���ID��ѯ����Ӧ����Ƶ�б��ѵ�ǰ���ŵ���Ƶ�ŵ���һλȥ��Ŀǰֻ����MP4��ʽ��
		List<Object[]> list=null;
		//�жϷ��������б��ǵ�ǰ������Ƶ�ڵ�һλ���б�
		if(flage) {
			list = sess.createSQLQuery("select  jsa02,jsa03,jsa06,jsa00 from  tpb_media tm where a1700='"+a1700+"' and jsa03 like '%.mp4' order by case when jsa00='"+jsa00+"' then 0 else 1 end").list();
		}else {
			list = sess.createSQLQuery("select  jsa02,jsa03,jsa06,jsa00 from  tpb_media tm where a1700='"+a1700+"' and jsa03 like '%.mp4'").list();

		}
		System.out.println(list.size());
		if(list!=null) {
			for(Object[] result:list) {
				String title = result[0]+"";//��Ƶ����
				String name  =result[1]+"";//��Ƶ����
				String path = result[2]+"";//��Ƶ·��
				jsa00=result[3]+"";//��̬����ID
				System.out.println("jsa00"+jsa00);
				HashMap<String,String> row = new HashMap<String, String>();
				String filePath=ProcessingPath(name,path,true);
				row.put(title,SplicFullPath(filePath, request, jsa00, a0000));
				videolist.add(row);
			}
		}
		
		
		return videolist;
	}
	/**
	 * ������Ƶ����·��
	 */
	/*
	 * private String ProcessingPath(String name,String path,HttpServletRequest
	 * request,String jsa00,String a0000) { //��ȡ��Ƶ���ĺ�׺ int extIndex =
	 * name.lastIndexOf("."); String extName = name.substring(extIndex,
	 * name.length()); //��ȡ�ļ��� int nameIndex = path.indexOf("\\")+1; String fileName
	 * = path.substring(nameIndex,path.length()-1); //������·�����ļ���ƴ�� String
	 * mediaPath=fileName+"/"+fileName+extName; //��ȡhttp String
	 * scheme=request.getScheme();//http String
	 * serverName=request.getServerName();//localhost int
	 * serverPort=request.getServerPort();//�˿� String
	 * contextPath=request.getContextPath(); //ƴ��Ϊ�����Ĳ���·�� String result=
	 * scheme+"://"+serverName+":"+serverPort+contextPath+
	 * "/pages/publicServantManage/PlayInVideo.jsp?path="+scheme+"://"+serverName+
	 * ":"+serverPort+"/playVideo/"+mediaPath+"?method=playFile&jsa00="+jsa00+
	 * "&a0000="+a0000; System.out.println("�õ�·��"+result); return result; }
	 */
	/**
	 * ��ȡ����·������������ͼһ�𷵻�
	 */
	private List<HashMap<String,String>> getFileList(String a0000,HttpServletRequest request){
		//��ȡ��Ա��Ӧý����Ϣ�������Ƶ������ļ�·��
		HBSession sess = HBUtil.getHBSession();
		/**
		 * ��ʱ�ȿ���PDF
		 */
		List<Object[]> filePaths=sess.createSQLQuery("select jsa03,jsa06 from tpb_media where a0000='"+a0000+"'and jsa03 like '%.pdf'").list();
		List<HashMap<String,String>> showPdflist=new ArrayList<HashMap<String,String>>();
		if(filePaths!=null) {
			for(Object[] obj:filePaths) {
				String name =obj[0]+"";
				String file = obj[1]+"";
				HashMap<String, String> map= new HashMap<String, String>();
				String thumbnail =SplicFullPath(ProcessingPath(name, file, false)+".jpg", request, null, null);
				System.out.println(thumbnail+"!!!!!!!");
				thumbnail=thumbnail.substring(thumbnail.indexOf("=")+1,thumbnail.lastIndexOf("?"));
				String pdfPath = SplicFullPath(ProcessingPath(name, file,true), request, null, null);
				pdfPath=pdfPath.substring(pdfPath.indexOf("=")+1,pdfPath.lastIndexOf("?"));
				map.put(thumbnail,pdfPath);
				showPdflist.add(map);
			}
		}
		return showPdflist;
	}

	/**
	 * �����ļ�����·��
	 */
	private String ProcessingPath(String name,String path,boolean flage) {
				//��ȡ�ļ����ĺ�׺
				int extIndex = name.lastIndexOf(".");
				String extName = name.substring(extIndex, name.length());
				//��ȡ�ļ���
				int nameIndex = path.indexOf("\\")+1;
				String fileName = path.substring(nameIndex,path.length()-1);
				//���ļ�·�����ļ���ƴ��
				//�ж�flage���true���ش���׺�ģ�false��Χ������׺��
				 String filePath=null;
				if(flage) {
					filePath=fileName+"/"+fileName+extName;
				}else {
					filePath=fileName+"/"+fileName;
				}
			    return filePath;
	}
	/**
	 * ƴ������·��
	 * @param filePath
	 * @param request
	 * @param jsa00
	 * @param a0000
	 * @return
	 */
	private String SplicFullPath(String filePath,HttpServletRequest request,String jsa00,String a0000) {
		  //��ȡhttp
	    String scheme=request.getScheme();//http
	    String serverName=request.getServerName();//localhost
	    int serverPort=request.getServerPort();//�˿�
	    String contextPath=request.getContextPath();
	    //ƴ��Ϊ�����Ĳ���·��
	    String result= scheme+"://"+serverName+":"+serverPort+contextPath+"/pages/publicServantManage/PlayInVideo.jsp?path="+scheme+"://"+serverName+":"+serverPort+"/playVideo/"+filePath+"?method=playFile&jsa00="+jsa00+"&a0000="+a0000;	    	
	    System.out.println("�õ�·��"+result);
	    return result;
	}

	 
      
}


