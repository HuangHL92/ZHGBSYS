package com.insigma.siis.local.business.helperUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownUtilServlet
 */
public class DownUtilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownUtilServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath=request.getParameter("filePath");
		String filaName = filePath.substring(filePath.lastIndexOf("/"), filePath.length());
		response.setContentType(getServletContext().getMimeType(filaName));
		response.setHeader("Content-Disposition", "attachment;filename="+filaName);
		String fullFileName = getServletContext().getRealPath(filePath);
		InputStream in = new FileInputStream(fullFileName);
		OutputStream out = response.getOutputStream();
		int b;
		while((b=in.read())!= -1){
			 out.write(b);
		}
		in.close();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
