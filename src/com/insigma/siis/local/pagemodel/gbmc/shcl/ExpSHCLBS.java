package com.insigma.siis.local.pagemodel.gbmc.shcl;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.*;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.lrmx.ItemXml;
import com.insigma.siis.local.lrmx.JiaTingChengYuanXml;
import com.insigma.siis.local.lrmx.PersonXml;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.gbmc.YNTPFileExportBS;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import org.apache.commons.io.output.FileWriterWithEncoding;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//import sun.misc.BASE64Encoder;

public class ExpSHCLBS {
	public PrintWriter out = null;
	public HttpServletRequest request = null;
	public HttpServletResponse response = null;

	public ExpSHCLBS(HttpServletRequest request2, HttpServletResponse response2) throws IOException {
		this.request = request2;
		this.response = response2;
		this.out = initOutPut();
	}
	/**
     * 删除目录及目录下的文件
     *
     * @param dir
     *            要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String dir) {
        /*// 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;*/
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            //System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }
    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                //System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
    public void endOutPut() throws IOException {
		out.println("</body></html>");
	}
	public PrintWriter initOutPut() throws IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>wu</title><style type=\"text/css\">\t.{\t\tfont-size: 12px;\t}\t.red{\t\tcolor: red;\t}</style></head><body>");
		out.println("<script type=\"text/javascript\">document.oncontextmenu=rightMouse;function rightMouse() {return false;} </script>");
		out.flush();
		return out;
	}
	public void outPrintlnErr(String message) {
		out.println("<FONT  COLOR=\"#FF0066\">" + message + "</FONT><br/>");
		out.println("<script type=\"text/javascript\">scroll(0,100000);</script>");
		out.flush();
	}
	public void outPrintln(String message) {
		out.println(message + "<br/>");
		out.println("<script type=\"text/javascript\">scroll(0,100000);</script>");
		out.flush();
	}
	public void outPrintlnTitle(String message) {
		out.println("<FONT  COLOR=\"rgb(0,204,255)\">" + message + "</FONT>" + "<br/>");
		out.println("<script type=\"text/javascript\">scroll(0,100000);</script>");
		out.flush();
	}
	public void outDownZip(String downloadUUID) {
		out.println("<script type=\"text/javascript\">function downloadFile(){"
				+ "window.location='PublishFileServlet?method=downloadFile&uuid="+downloadUUID+"';"
				+ "}</script>");
		out.println("<button onclick='downloadFile()'>点击下载数据包</button>");
		out.println("<script type=\"text/javascript\">scroll(0,100000);</script>");
		out.flush();
	}
	public void outPrintlnSuc(String message) {
		out.println("<FONT  COLOR=\"#669900\">" + message + "</FONT>" + "<br/>");
		out.println("<script type=\"text/javascript\">scroll(0,100000);</script>");
		out.flush();
	}
	
	public void downloadshanghuicailiao() throws IOException {
		
		try {
			HBSession sess = HBUtil.getHBSession();
			String ynId = request.getParameter("ynId");
			//String exptype = request.getParameter("exptype");
			Js2Yntp yntp = (Js2Yntp)sess.get(Js2Yntp.class	, ynId);
			if(yntp==null){
				this.outPrintlnErr("批次不存在。");
				return;
			}
			String BuHui = "部务会/"+yntp.getYnName();
			String ShuJiHui = "书记会常委会";
			
			
			this.outPrintlnTitle("开始执行...");
			String directory_name = "智慧党建/";
			//hzb目录
			String disk_path = JSGLBS.HZBPATH + "zhgbshanghui/";
			//批次文件夹
			String ynpc_der_name = ynId+"/";
			//批次目录
			String directory_ynpc_path = disk_path + ynpc_der_name;
			//批次zip目录
			String zip_directory_path = directory_ynpc_path + directory_name;
			
			//清空批次目录
			File file_directory_ynpc_path = new File(directory_ynpc_path);
			if(file_directory_ynpc_path.isDirectory()){
				deleteDirectory(directory_ynpc_path);
			}
			//导出部会
			String file_root = zip_directory_path+BuHui+"/";
			//批次zip目录 file导出部会
			/*File file_file_root = new File(file_root+"干部任免审批表/");
			//创建子目录
			if(!file_file_root.isDirectory()){
				file_file_root.mkdirs();
			}*/
			//导出调配表...
			YNTPFileExportBS fileBS = new YNTPFileExportBS(this);
			fileBS.setOutputpath(file_root);
			this.outPrintlnTitle("导出部会调配表和任免表lrmx...");
			fileBS.exportBuHui(ynId);
			
			//outPrintln(out, "导出任免表...");
			//outPrintln(out, "导出任免表完成。");
			
			
			//批次zip目录 file  导出书记会
			//
			String file_root2 = zip_directory_path+ShuJiHui+"/";
			/*File file_file_root2 = new File(file_root2+"干部任免审批表/");
			//创建子目录
			if(!file_file_root2.isDirectory()){
				file_file_root2.mkdirs();
			}*/
			//导出调配表...
			YNTPFileExportBS fileBS2 = new YNTPFileExportBS(this);
			fileBS2.setOutputpath(file_root2);
			this.outPrintlnTitle("导出书记会常委会调配表和任免表lrmx...");
			fileBS2.exportShuJiHui(ynId);
			
			//outPrintln(out, "导出任免表...");
			//outPrintln(out, "导出任免表完成。");
			
			
			
			
			this.outPrintlnTitle("开始压缩zip包...");
			String zip_output_name = yntp.getYnName()+new SimpleDateFormat("yyyyMMdd_HH：mm：ss").format(new Date());
			Zip7z.zip7Z(directory_ynpc_path, directory_ynpc_path+zip_output_name, null);
			this.outPrintlnSuc("压缩完成。");
			//JSGLBS.outPrintlnErr(out, "导出表完成");
			String downloadUUID = UUID.randomUUID().toString();
			String zipfilepath = directory_ynpc_path+zip_output_name+".zip";
			request.getSession().setAttribute(downloadUUID, new String[]{zipfilepath,zip_output_name+".zip"});
			this.outDownZip(downloadUUID);
			this.endOutPut();
		} catch (Exception e) {
			this.outPrintlnErr( "导出出错："+e.getMessage());
			e.printStackTrace();
		}
		
	}

	
	
	
	public PersonXml createLrmxStr(String ids,String time,String userid){
		String a0000 = ids;
		PersonXml a = new PersonXml();
		JiaTingChengYuanXml jiaTingChengYuanXml=new JiaTingChengYuanXml();
		List<JiaTingChengYuanXml> jtcyList = new ArrayList<JiaTingChengYuanXml>();
		List<ItemXml> itemlist = new ArrayList<ItemXml>();
		HBSession sess = HBUtil.getHBSession();
		String sqla36 = "from A36 where a0000='"+a0000+"' order by sortid";
		List lista36 = sess.createQuery(sqla36).list();
		
		A01 a01 = (A01)sess.get(A01.class, a0000);
		A57 a57 = (A57)sess.get(A57.class, a0000);
		String sqla53 = "from A53 where a0000='"+a0000+"' and a5399='"+userid+"'";
		List<A53> list = sess.createQuery(sqla53).list();
		Object xb = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB2261' and t.code_value = '"+a01.getA0104()+"'").uniqueResult();
		Object mz = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB3304' and t.code_value = '"+a01.getA0117()+"'").uniqueResult();
		if(a57!=null){
	    	byte[] data = PhotosUtil.getPhotoData(a57);
			if(data!=null){
				a.setZhaoPian(data);
			}
	    }
		a.setXingMing(a01.getA0101());
		a.setXingBie(xb!=null?xb.toString():"");                                    
		a.setChuShengNianYue(a01.getA0107());                            
		a.setMinZu(mz!=null?mz.toString():"");                                      
		a.setJiGuan(a01.getComboxArea_a0111());                                     
		a.setChuShengDi(a01.getComboxArea_a0114());
		a.setRuDangShiJian(a01.getA0140());
		a.setCanJiaGongZuoShiJian(a01.getA0134());
		a.setJianKangZhuangKuang(a01.getA0128());      
		a.setZhuanYeJiShuZhiWu(a01.getA0196());
		a.setShuXiZhuanYeYouHeZhuanChang(a01.getA0187a());
		a.setQuanRiZhiJiaoYu_XueLi(a01.getQrzxl());
		a.setQuanRiZhiJiaoYu_XueWei(a01.getQrzxw());
		a.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getQrzxlxx());
		a.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getQrzxwxx());
		a.setZaiZhiJiaoYu_XueLi(a01.getZzxl());
		a.setZaiZhiJiaoYu_XueWei(a01.getZzxw());
		a.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getZzxlxx());
		a.setZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getZzxwxx());
		a.setXianRenZhiWu(a01.getA0192a());                               
		a.setJianLi(a01.getA1701());                                     
		a.setJiangChengQingKuang(a01.getA14z101());                        
		a.setNianDuKaoHeJieGuo(a01.getA15z101());
		a.setShenFenZheng(a01.getA0184());
		if(list==null||list.size()==0){
			a.setNiRenZhiWu("");                                 
			a.setNiMianZhiWu("");
			a.setRenMianLiYou("");                               
			a.setChengBaoDanWei("");                             
			a.setJiSuanNianLingShiJian("");                      
			a.setTianBiaoShiJian(time);
			a.setTianBiaoRen("");
		}else{
			A53 a53 = list.get(0);
			a.setNiRenZhiWu(a53.getA5304());                                 
			a.setNiMianZhiWu(a53.getA5315());
			a.setRenMianLiYou(a53.getA5317());                               
			a.setChengBaoDanWei(a53.getA5319());                             
			a.setJiSuanNianLingShiJian(""); 
//			if(StringUtil.isEmpty(time)){
//				a.setTianBiaoShiJian(a53.getA5323());
//			}else{
			a.setTianBiaoShiJian(time);
//			}
			a.setTianBiaoRen("");
		}
		if(lista36!=null&&lista36.size()>0){
			for(int i=1;i<=lista36.size();i++){
				ItemXml b =new ItemXml();
				A36 a36 = (A36) lista36.get(i-1);
				//Object cw = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4761' and t.code_value = '"+a36.getA3604a()+"'").uniqueResult();
			    b.setChengWei(a36.getA3604a()!=null?a36.getA3604a().toString():"");
				b.setChuShengRiQi(a36.getA3607());
				b.setGongZuoDanWeiJiZhiWu(a36.getA3611());
				b.setXingMing(a36.getA3601());
				//Object zzmm = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4762' and t.code_value = '"+a36.getA3627()+"'").uniqueResult();
			    b.setZhengZhiMianMao(a36.getA3627()!=null?a36.getA3627().toString():"");
				itemlist.add(b);
			}
		}
		jiaTingChengYuanXml.setItem(itemlist);
		jtcyList.add(jiaTingChengYuanXml);
		a.setJiaTingChengYuan(jtcyList);
		return a;
	}
	
	/**
	 * 创建文件
	 * @param fullname
	 * @param fileContent
	 * @throws Exception
	 */
	public void createFile(String path,String fullname ,String fileContent,boolean append,String charset)  {
		
		
		try {
			File file = new File(path);
			if (!file.isDirectory()){
				file.mkdirs();
			}
			
			file = new File(path+fullname);
			if (!file.exists()){
				this.outPrintln("导出任免表："+fullname);
				file.createNewFile();
				FileWriterWithEncoding fw = new FileWriterWithEncoding(file, charset, append);
				fw.append(fileContent);
				fw.close();
			}
		} catch (Exception e) {
			this.outPrintlnErr("导出任免表失败："+fullname+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void copyFileByPath(String sourcePath, String targetPath,String name)
			throws IOException {
		FileInputStream input = null;
		BufferedInputStream bufInput = null;
		BufferedOutputStream bufOutput = null;
		FileOutputStream output = null;
		try {
			this.outPrintln("导出考察材料："+name);
			input = new FileInputStream(sourcePath);
			bufInput = new BufferedInputStream(input);
			File file = new File(targetPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			output = new FileOutputStream(file);
			bufOutput = new BufferedOutputStream(output);
			byte[] b = new byte[1025 * 5];
			int len;
			while ((len = input.read(b)) != -1) {
				bufOutput.write(b, 0, len);
			}
			bufOutput.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			bufOutput.close();
			bufInput.close();
			output.close();
			input.close();
		}
	}
	
	
	
	
	public PrintWriter getOut() {
		return out;
	}
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	
	
}
