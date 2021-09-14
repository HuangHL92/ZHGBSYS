package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.kingbs.CommandImpUtil;
import com.insigma.siis.local.business.utils.kingbs.EncryptUtil;

public class Zip7z {

	public Zip7z() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static void zip7Z(String dirName, String zipFileName, String password) { 
		  String cmd7z = getRootPath() + "\\7-Zip\\7z.exe";
/* 487 */     CommandImpUtil util = new CommandImpUtil();
/*     */     try {
/* 489 */       System.out.println("newzip==============");
/* 490 */       if (password != null)
/*     */       {
/* 492 */         String cmd = "cmd.exe /c " + cmd7z.trim().replace(" ", "\" \"") + " a \"" + zipFileName + "\" -p" + password + " -m0=LZMA:d=20 -mhe -ms=off \"" + dirName + "\\*\"";
/* 493 */         util.executeCommand(cmd);
/* 494 */         CommandImpUtil.printList(util.getErroroutList());
/* 495 */         System.out.println("-----");
/* 496 */         CommandImpUtil.printList(util.getStdoutList());
/*     */       } else {
/* 498 */         String cmd = "cmd.exe /c " + cmd7z.trim().replace(" ", "\" \"") + " a -tzip \"" + zipFileName + "\" \"" + dirName + "\\*\"";
/* 499 */         System.out.println(cmd);
/* 500 */         util.executeCommand(cmd);
/* 501 */         CommandImpUtil.printList(util.getErroroutList());
/* 502 */         System.out.println("-----");
/* 503 */         CommandImpUtil.printList(util.getStdoutList());
/*     */       }
/*     */     } catch (Exception ie) {
/* 506 */       ie.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static String getRootPath() {
/* 511 */     String classPath = NewSevenZipUtil.class.getClassLoader().getResource("/").getPath();
/*     */     try {
/* 513 */       classPath = URLDecoder.decode(classPath, "GBK");
/*     */     } catch (UnsupportedEncodingException e) {
/* 515 */       e.printStackTrace();
/*     */     }
/* 517 */     String uuid = UUID.randomUUID().toString().replace("-", "");
/* 518 */     String rootPath = "";
/*     */     
/*     */ 
/* 521 */     if ("\\".equals(File.separator)) {
/* 522 */       rootPath = classPath.substring(1, classPath.indexOf("WEB-INF/classes"));
/* 523 */       rootPath = rootPath.replace("/", "\\");
/*     */     }
/*     */     
/* 526 */     if ("/".equals(File.separator)) {
/* 527 */       rootPath = classPath.substring(0, classPath.indexOf("WEB-INF/classes"));
/* 528 */       rootPath = rootPath.replace("\\", "/");
/*     */     }
/* 530 */     return rootPath + "softTools";
/*     */   }
		
		public static void unzip7zAll(String filepath, String destinationDir, String password) throws Exception
/*     */   {
/* 620 */     String cmd7z = getRootPath() + "\\7-Zip\\7z.exe";
/* 621 */     CommandImpUtil util = new CommandImpUtil();
/*     */     try {
/* 623 */       System.out.println("newzip==============");
/* 624 */       if (password != null) {
/* 625 */         String cmd = "cmd.exe /c " + cmd7z.trim().replace(" ", "\" \"") + " x  \"" + filepath + "\" -o\"" + destinationDir + 
/* 626 */           "\" " + " -y -p" + password;
/* 627 */         util.executeCommand(cmd);
/* 628 */         CommandImpUtil.printList(util.getErroroutList());
/* 629 */         CommandImpUtil.printList(util.getStdoutList());
/*     */       } else {
/* 631 */         String cmd = "cmd.exe /c " + cmd7z.trim().replace(" ", "\" \"") + " x  \"" + filepath + 
/* 632 */           "\" -o\"" + destinationDir + "\" " + " -y ";
/* 633 */         System.out.println(cmd);
/* 634 */         util.executeCommand(cmd);
/* 635 */         CommandImpUtil.printList(util.getErroroutList());
/* 636 */         System.out.println("-----");
/* 637 */         CommandImpUtil.printList(util.getStdoutList());
/*     */       }
/*     */     } catch (Exception ie) {
/* 640 */       ie.printStackTrace();
/*     */     }
/*     */   }

		public static void unzip7zOne(String filepath, String destinationDir, String password) throws Exception
/*     */   {
/* 646 */     
/* 648 */     String cmd7z = getRootPath() + "\\7-Zip\\7z.exe";
/* 649 */     CommandImpUtil util = new CommandImpUtil();
/*     */     try {
/* 651 */       System.out.println("newzip==============");
/* 652 */       if (password != null) {
/* 653 */         String cmd = "cmd.exe /c " + cmd7z.trim().replace(" ", "\" \"") + " x  \"" + filepath + "\" -o\"" + destinationDir + 
/* 654 */           "\" " + " gwyinfo.xml -y -p" + password;
/* 655 */         util.executeCommand(cmd);
/* 656 */         CommandImpUtil.printList(util.getErroroutList());
/* 657 */         CommandImpUtil.printList(util.getStdoutList());
/*     */       }else{
					String cmd = "cmd.exe /c " + cmd7z.trim().replace(" ", "\" \"") + " x  \"" + filepath + "\" -o\"" + destinationDir + 
							"\" " + " gwyinfo.xml ";
					util.executeCommand(cmd);
					CommandImpUtil.printList(util.getErroroutList());
					CommandImpUtil.printList(util.getStdoutList());
				}
/*     */     } catch (Exception ie) {
/* 660 */       ie.printStackTrace();
/*     */     }
/*     */   }
}
