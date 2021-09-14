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
 * 处理多媒体信息校的Servlet
 * @author luol
 * @time 2020/02/07 17点26分
 *
 */
public class PlayInVideoServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MediaEntity media = new MediaEntity();
		HBSession sess = HBUtil.getHBSession();
		//获取对应的人员信息
		//获取人员ID
		String a0000=request.getParameter("a0000");
		//获取视频ID
		String jsa00=request.getParameter("jsa00");
		System.out.println("ID:"+a0000);
		 try {
			HashMap<String,Object> users= CommonQueryBS.getMapBySQL("select a0101,code_name ,age,A0192,A0198 from A01,code_value where code_type='GB3304' and code_value = a0117 and a0000='"+a0000+"'");
			//将人员信息添加到media中
			media.setName(users.get("a0101")+"");
			media.setAge(users.get("age")+"");
			media.setNation(users.get("code_name")+"");
			media.setPost(users.get("a0192")+"");
			media.setPhoto(users.get("a0198")+"");
			//添加视频列表到media中
			media.setVideoList(getVideoList(jsa00, a0000, request,true));
			//添加展示视频列表
			media.setShowVideoList(getVideoList(jsa00, a0000, request,false));
			//设置视频标题
			String jsa02=(String)sess.createSQLQuery("select jsa02 from tpb_media where jsa00='"+jsa00+"'").uniqueResult();
			media.setVideoTitle(jsa02);
			//添加PDF播放列表
			media.setShowPdfList(getFileList(a0000, request));

			String mediastr=JSON.toJSONString(media);
			PrintWriter pw = response.getWriter();
			pw.write(mediastr);
			System.out.println("封装Session结束"+mediastr);
			
		 } catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	/**
	 * 获取视频列表并且把当前播放的视频放在第一个
	 */
	private List<HashMap<String,String>> getVideoList(String jsa00,String a0000,HttpServletRequest request,boolean flage){
		HBSession sess = HBUtil.getHBSession();
		//根据视频ID查出对应的简历ID
		String a1700=(String)sess.createSQLQuery("select a1700 from tpb_media where jsa00='"+jsa00+"'").uniqueResult();
		List<HashMap<String,String>> videolist=new ArrayList<HashMap<String,String>>();
		//获取展示视频列表
		List<HashMap<String,String>> showVideolist=new ArrayList<HashMap<String,String>>();
		System.out.println(a1700);
		//根据简历ID查询出对应的视频列表并把当前播放的视频排到第一位去（目前只考虑MP4格式）
		List<Object[]> list=null;
		//判断返回有序列表还是当前播放视频在第一位的列表
		if(flage) {
			list = sess.createSQLQuery("select  jsa02,jsa03,jsa06,jsa00 from  tpb_media tm where a1700='"+a1700+"' and jsa03 like '%.mp4' order by case when jsa00='"+jsa00+"' then 0 else 1 end").list();
		}else {
			list = sess.createSQLQuery("select  jsa02,jsa03,jsa06,jsa00 from  tpb_media tm where a1700='"+a1700+"' and jsa03 like '%.mp4'").list();

		}
		System.out.println(list.size());
		if(list!=null) {
			for(Object[] result:list) {
				String title = result[0]+"";//视频标题
				String name  =result[1]+"";//视频名称
				String path = result[2]+"";//视频路径
				jsa00=result[3]+"";//动态生成ID
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
	 * 处理视频播放路径
	 */
	/*
	 * private String ProcessingPath(String name,String path,HttpServletRequest
	 * request,String jsa00,String a0000) { //获取视频名的后缀 int extIndex =
	 * name.lastIndexOf("."); String extName = name.substring(extIndex,
	 * name.length()); //获取文件名 int nameIndex = path.indexOf("\\")+1; String fileName
	 * = path.substring(nameIndex,path.length()-1); //将播放路径与文件名拼接 String
	 * mediaPath=fileName+"/"+fileName+extName; //获取http String
	 * scheme=request.getScheme();//http String
	 * serverName=request.getServerName();//localhost int
	 * serverPort=request.getServerPort();//端口 String
	 * contextPath=request.getContextPath(); //拼接为完整的播放路径 String result=
	 * scheme+"://"+serverName+":"+serverPort+contextPath+
	 * "/pages/publicServantManage/PlayInVideo.jsp?path="+scheme+"://"+serverName+
	 * ":"+serverPort+"/playVideo/"+mediaPath+"?method=playFile&jsa00="+jsa00+
	 * "&a0000="+a0000; System.out.println("得到路径"+result); return result; }
	 */
	/**
	 * 获取资料路径并生成缩略图一起返回
	 */
	private List<HashMap<String,String>> getFileList(String a0000,HttpServletRequest request){
		//获取人员对应媒体信息里除开视频以外的文件路径
		HBSession sess = HBUtil.getHBSession();
		/**
		 * 暂时先考虑PDF
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
	 * 处理文件名与路径
	 */
	private String ProcessingPath(String name,String path,boolean flage) {
				//获取文件名的后缀
				int extIndex = name.lastIndexOf(".");
				String extName = name.substring(extIndex, name.length());
				//获取文件名
				int nameIndex = path.indexOf("\\")+1;
				String fileName = path.substring(nameIndex,path.length()-1);
				//将文件路径与文件名拼接
				//判断flage如果true返回带后缀的，false范围不带后缀的
				 String filePath=null;
				if(flage) {
					filePath=fileName+"/"+fileName+extName;
				}else {
					filePath=fileName+"/"+fileName;
				}
			    return filePath;
	}
	/**
	 * 拼接完整路径
	 * @param filePath
	 * @param request
	 * @param jsa00
	 * @param a0000
	 * @return
	 */
	private String SplicFullPath(String filePath,HttpServletRequest request,String jsa00,String a0000) {
		  //获取http
	    String scheme=request.getScheme();//http
	    String serverName=request.getServerName();//localhost
	    int serverPort=request.getServerPort();//端口
	    String contextPath=request.getContextPath();
	    //拼接为完整的播放路径
	    String result= scheme+"://"+serverName+":"+serverPort+contextPath+"/pages/publicServantManage/PlayInVideo.jsp?path="+scheme+"://"+serverName+":"+serverPort+"/playVideo/"+filePath+"?method=playFile&jsa00="+jsa00+"&a0000="+a0000;	    	
	    System.out.println("得到路径"+result);
	    return result;
	}

	 
      
}


