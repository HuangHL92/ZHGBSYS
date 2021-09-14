package com.insigma.siis.devtool;

import java.io.File;
import java.io.UnsupportedEncodingException;

import com.insigma.odin.framework.radow.util.ObjectUtil;
import com.insigma.odin.framework.util.commform.FileUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class CopyJsp {
	public static String WITHOUT_FILE_IDXNAME = "svn\\,classes\\,WEB-INF\\reportlets\\,images\\icon\\,WEB-INF\\resources\\,pages\\,odin-,odex-,sys_login_03.gif,topflash.swf,login_02.jpg,help.doc,Help,welcome.jpg,license.xml,web.xml,local.jsp,LogonDialog.jsp,Main.jsp,weblogic.xml,Config.xml";

	public static void copy(String to) throws Exception {
		String from = "insiis6";
		//String[] devModel = { "CommForm" };
		//ChangeDevModel.main(devModel);
//		String[] jobConfig = { "false" };
//		ChangeJobConfig.main(jobConfig);
		if (ObjectUtil.equals(FileUtil.WITHOUT_FILE_IDXNAME, "")) {
			FileUtil.WITHOUT_FILE_IDXNAME = WITHOUT_FILE_IDXNAME;
		}
		String s = System.getProperty("user.dir");
		s = s + "\\WebContent\\";
		CommonQueryBS.systemOut("开始同步");
		FileUtil.replaceDirFile(new File(s + "basejs"), from, to);
		FileUtil.replaceDirFile(new File(s + "commform"), from, to);
		FileUtil.replaceDirFile(new File(s + "css"), from, to);
		FileUtil.replaceDirFile(new File(s + "download"), from, to);
		FileUtil.replaceDirFile(new File(s + "error"), from, to);
		FileUtil.replaceDirFile(new File(s + "images"), from, to);
		FileUtil.replaceDirFile(new File(s + "images"), from, to);
		FileUtil.replaceDirFile(new File(s + "pages"), from, to);
		FileUtil.replaceDirFile(new File(s + "radow"), from, to);
		FileUtil.replaceDirFile(new File(s + "samples"), from, to);
		FileUtil.replaceDirFile(new File(s + "sys"), from, to);
		FileUtil.replaceDirFile(new File(s + "WEB-INF"), from, to);
		// 复制根目录下文件
		File[] fa = new File(s).listFiles();
		if (fa.length > 0) {
			for (int i = 0; i < fa.length; i++) {
				if (!fa[i].isDirectory()) {
					FileUtil.copyFile(fa[i].toString(), fa[i].toString().replace("\\" + from + "\\", "\\" + to + "\\")); // 调用
				}
			}
		}
		CommonQueryBS.systemOut("结束同步");
		// devModel[0] = "PageModel";
		// ChangeDevModel.main(devModel);
	}

	public static void main(String arg[]) throws UnsupportedEncodingException {
		// copy("sicp");
	}
}
