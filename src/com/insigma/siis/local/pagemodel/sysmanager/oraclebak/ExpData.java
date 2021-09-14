package com.insigma.siis.local.pagemodel.sysmanager.oraclebak;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.DateUtil;

public class ExpData {
	public PrintWriter out = null;
	public HttpServletRequest request = null;
	public HttpServletResponse response = null;
	public String logPath = null;
	public ExpData(HttpServletRequest request2, HttpServletResponse response2) throws IOException {
		this.request = request2;
		this.response = response2;
		this.out = initOutPut();
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
	public void outPrintlnErr(String message) {// COLOR=\"#FF0066\"
		out.println("<FONT style='font-size:18px;color:rgb(192,192,192);'>" + message + "</FONT><br/>");
		out.println("<script type=\"text/javascript\">scroll(0,1000000);</script>");
		out.flush();
	}
	public void outPrintln(String message) {
		out.println("<FONT style='font-size:18px;color:rgb(192,192,192);'>"+message + "</FONT><br/>");
		out.println("<script type=\"text/javascript\">scroll(0,1000000);</script>");
		out.flush();
	}
	public void outPrintlnTitle(String message) {
		out.println("<FONT style='font-size:18px;' COLOR=\"rgb(0,204,255)\">" + message + "</FONT>" + "<br/>");
		out.println("<script type=\"text/javascript\">scroll(0,1000000);</script>");
		out.flush();
	}
	public void outDownZip(String downloadUUID) {
		out.println("<script type=\"text/javascript\">function downloadFile(){"
				+ "window.location='PublishFileServlet?method=downloadFile&uuid="+downloadUUID+"';"
				+ "}</script>");
		out.println("<button onclick='downloadFile()'>点击下载数据包</button>");
		out.println("<script type=\"text/javascript\">scroll(0,1000000);</script>");
		out.flush();
	}
	public void outPrintlnSuc(String message) {
		out.println("<FONT style='font-size:18px;'  COLOR=\"#669900\">" + message + "</FONT>" + "<br/>");
		out.println("<script type=\"text/javascript\">scroll(0,1000000);</script>");
		out.flush();
	}


	public void expData(){
		String userName = this.request.getParameter("username");
		String password = this.request.getParameter("password");
		String SID = this.request.getParameter("sid");
		String savePath = this.request.getParameter("savepath");
		String fileName = "zwhzyq_"+DateUtil.timeToString(DateUtil.getTimestamp(),
				"yyyyMMddHHmmss");
		File saveFile = new File(savePath);
		if (!saveFile.exists()) {// 如果目录不存在
			saveFile.mkdirs();// 创建文件夹
		}
		/*String[] cmds = new String[3];
		cmds[0] = "cmd";
		cmds[1] = "/C";
		cmds[2] = " exp " + userName + "/" + password + "@" + SID
					+ " file=" + savePath + "/" + fileName + ".dmp" + " log="
					+ savePath + "/" + fileName + ".log";*/
		
		//cmd /c start 
		
		
		/*String cmd = "exp " + userName + "/" + password + "@" + SID
				+ " file=" + savePath + "/" + fileName + ".dmp" + " log="
				+ savePath + "/" + fileName + ".log";*/
		//dexp ZHGBSYS/ZHGBSYS123 FILE=dameng20210616.dmp LOG=dm20210616.log DIRECTORY=e:/dmdump/
		String cmd = "/opt/dmdbms/bin/bin/dexp " + userName + "/" + password + "@" + SID
				+ " file="  + fileName + ".dmp log=" +  fileName + ".log DIRECTORY="+savePath;
		
		//cmd = "cmd /c start D:/aaa/myexp.bat ";
		this.outPrintlnErr("开始执行导出：");
		this.outPrintlnErr(cmd);
		this.logPath = savePath + "/" + fileName + ".log";
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmd);
			process.getOutputStream().close();
			
			
			WriteLog wg = new WriteLog(this,process.getErrorStream());
			Thread t = new Thread(wg);
			t.start();
			WriteLog wg2 = new WriteLog(this,process.getInputStream());
			Thread t2 = new Thread(wg2);
			t2.start();
			process.waitFor(); 
		} catch (Exception e1) {
			e1.printStackTrace();
			this.outPrintlnErr(e1.getMessage());
		}
		

		
	}
	
	
	
}
