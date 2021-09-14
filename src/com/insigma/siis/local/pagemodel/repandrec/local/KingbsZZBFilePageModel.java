package com.insigma.siis.local.pagemodel.repandrec.local;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class KingbsZZBFilePageModel extends PageModel {
	
//	@PageEvent("imp3btn")
//	public int imp3btn(String name) throws RadowException{
//		System.out.println("导入程序开始：-----"+ DateUtil.getTime());
//		Map<String, String> map = new HashMap<String, String>();
//		HBSession sess = HBUtil.getHBSession();
//		String path = "D:/KingbsData/unzip";   //生成文件路径
//		File sfile = new File(name);   //备份文件位置
//		String filePath = "";
//		String filename = "";
//		int count = 0;//zzb3 文件数目
//		String imprecordid = "";
//		try {
//			String logfilename = getRootPath() + "upload/" +DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".txt";
//			File logfile = new File(logfilename);
//			if(!logfile.exists()){
//				logfile.createNewFile();
//			}
//			appendFileContent(logfilename, "开始导入:"+ DateUtil.getTime()+"\n");
////			sess.beginTransaction();
//			if(sfile.exists()){
//				filePath = sfile.getAbsolutePath();
//				filename = sfile.getName();
//				count ++;
//			}
//			if(count==0){
//				this.setMainMessage("指定目录下不存在备份文件！");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			String zippath = path + "/";
//			//Photos 路径
//			String photo_file = path + "/Photos/";
//			File file =new File(zippath);    
//			//如果文件夹不存在则创建    
//			if  (!file .exists()  && !file .isDirectory()) {       
//			    file .mkdirs();    
//			}
//			System.out.println("zzb3解压缩开始开始：-----"+ DateUtil.getTime());
//			appendFileContent(logfilename, "解压缩开始:"+ DateUtil.getTime()+"\n");
//			SevenZipUtil.extractile(filePath, path+"/", "1234");
//			appendFileContent(logfilename, "解压缩结束:"+ DateUtil.getTime()+"\n");
//			System.out.println("zzb3解压缩结束：-----"+ DateUtil.getTime());
//			File datfile = new File(path + "/KDataTmp/GWYDB.dat");   //备份文件位置
//			if(datfile.exists() && datfile.isFile()){
//				appendFileContent(logfilename, "导入金仓数据库:"+ DateUtil.getTime()+"\n");
//				KingbsGainBS.cmdImpKingBs(datfile.getAbsoluteFile());
//				appendFileContent(logfilename, "导入金仓数据库完成:"+ DateUtil.getTime()+"\n");
//				System.out.println("导入金仓数据库成功！---"+ DateUtil.getTime());
//			} else {
//				UploadHelpFileServlet.delFolder(path);
//				this.setMainMessage("备份文件出错！");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			//==========================================================================================================
//			//NO.003 获取本应用中和倒入包根节点机构对应的机构信息，主要是impdeptid（本应用 机构唯一编码）deptid（本应用 上级机构的唯一编码）
//			//2016年06月15日   北京 检查代码，添加注释 。
//			//获取机构 人大金仓数据库中获取根机构信息(b01) 
//			B01 b01 = KingbsGainBS.getB01byParentid("");
//			if(b01 == null){
//				UploadHelpFileServlet.delFolder(path);          //删除 人大金仓zzb3压缩包 在应用服务器上 解压后的文件目录。 
//				this.setMainMessage("系统出错，请联系管理员！");    //结束提示信息。 
//				return EventRtnType.NORMAL_SUCCESS;            //结束倒入
//			}
//			//获取机构 本应用数据库中获取根机构信息(b01) 
//			B01 detp = null;
//			String deptid = "";// 根节点上级机构id
//			String impdeptid = "";//根节点机构id
//			List<B01> detps = HBUtil.getHBSession().createQuery(
//					"from B01 t where t.b0101='" + b01.getB0101()  //机构民称
//							+ "' and t.b0114='" + b01.getB0114()   //组织机构编码
//							+ "'").list();
//			if (detps != null && detps.size() > 0) {
//				detp = detps.get(0);                   //获取本应用中和人大金仓数据的根机构同组织机构编码、同名称的机构信息
//				impdeptid = detps.get(0).getB0111();   //本应用 机构唯一编码
//				deptid = detps.get(0).getB0121();      //本应用 上级机构的唯一编码 
//				detps.removeAll(detps);                //释放 本应用机构信息列表
//				detps.clear();                         
//				detps = null;
//			} else {
//				UploadHelpFileServlet.delFolder(path);     //删除 人大金仓zzb3压缩包 在应用服务器上 解压后的文件目录。
//				this.setMainMessage("未匹配到机构，请检测！"); //结束提示信息。
//				return EventRtnType.NORMAL_SUCCESS;        //结束倒入
//			}
//			detp = null;
//			System.out.println("NO.003 检测到本应用中和倒入包根节点机构对应的机构信息impdeptid（本应用 机构唯一编码）："+impdeptid+"deptid（本应用 上级机构的唯一编码）:"+deptid+" 完成时间"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss"));
//			//==========================================================================================================
//			//NO.004 获取当前执行导入的操作人员所属机构信息 gr（机构信息）
//			CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
//			List<B01> grps = HBUtil.getHBSession().createQuery(
//					"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
//							+ user.getId() + "')").list();
//			B01 gr = null;
//			if (grps != null && grps.size() > 0) {
//				gr = grps.get(0);     //获取 当前执行导入的操作人员所属机构信息
//				grps.removeAll(grps); //释放 当前执行导入的操作人员所属机构信息列表
//				grps.clear();
//				grps = null;
//			}
//			System.out.println("NO.004 获取当前执行导入的操作人员所属机构信息 gr（机构信息）机构唯一编码："+gr.getB0111()+"机构名称:"+gr.getB0101()+" 完成时间"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss"));
//			//==========================================================================================================
//			//NO.005 本次倒入过程记录入导入查询记录表（imp_record），获取 imprecordid（导入查询记录的唯一主键）
//			Imprecord imprecord = new Imprecord();           //新建 导入查询记录表 记录
//			imprecord.setImptime(DateUtil.getTimestamp());     //设置 倒入开始时间 
//			imprecord.setImpuserid(user.getId());            //设置 倒入操作人员编码 
//			if (gr != null) {   
//				imprecord.setImpgroupid(gr.getB0111());      //设置 倒入操作员所属机构编码
//				imprecord.setImpgroupname(gr.getB0101());    //设置 倒入操作员所属机构名称
//			}
//			imprecord.setIsvirety("0");                      //设置 数据校验标志 初始化为0表示 没有校验 
//			imprecord.setFilename(filename);                 //设置 本次导入的文件的文件名称
//			imprecord.setFiletype("ZZB3");                   //设置 本次导入的文件类型 
//			imprecord.setImptype("3");                       //设置 导入类型 3 表示 ZZB3
//			imprecord.setEmpdeptid(b01.getB0111());          //设置 导入文件中根机构的唯一编码
//			imprecord.setEmpdeptname(b01.getB0101());        //设置 导入文件中根机构的名称
//			imprecord.setImpdeptid(impdeptid);               //设置 本应用 机构唯一编码
//			imprecord.setImpstutas("1");                     //设置 导入状态为1 （倒入状态 1：未导入、2：已导入、3：打回）
//			imprecord.setB0114(b01.getB0114());              //设置 导入文件中根机构的内部编码
//			imprecord.setB0194(b01.getB0194());              //设置 导入文件中根机构的法人单位标识
//			imprecord.setWrongnumber("0");                   //设置 校验错误的记录条数(未使用)
//			imprecord.setTotalnumber("0");                   //设置 导入的总记录条数
//			sess.save(imprecord);                            //保存导入查询记录表 
//			imprecordid = imprecord.getImprecordid();
//			b01  = null;
//			System.out.println("NO.005 本次倒入过程记录入导入查询记录表（imprecordid（导入查询记录的唯一主键）："+imprecordid+" 完成时间"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss"));
//			//==========================================================================================================
//			//NO.006 从人大金仓数据库分表、分批次导入本应用数据库，批次数据根据personsize设定
//			System.out.println("NO.006 总导入数据开始！---"+ DateUtil.getTime());
//			int personcount = 0;                                                     //批次数量声明
//			int personsize = 20000;                                                   //每次2000条记录
//			int time = 0;
//			int t_n = 0;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//声明导入次数变量 			
//			//NO.006.002 导入A02表
//			personcount = KingbsGainBS.getAllA02Size();                         //从人大金仓数据库获取A02记录数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0); //计算导入次数。
//			for (int i = 0; i < time; i++) {
//				appendFileContent(logfilename, "A02数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("NO.006.002 A02数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A02");
//				System.out.println("NO.006.002 A02数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A02数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllB01Size();                          //从人大金仓数据库获取b01记录数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);  //计算导入次数。
//			System.out.println("NO.006.001 B01数据导入 共需要插入"+personcount+"条记录");
//			for (int i = 0; i < time; i++) {//b01按次数分批次导入本应用数据库的临时表中
//				appendFileContent(logfilename, "B02数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("NO.006.001 B01数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "B01");
//				System.out.println("NO.006.001 B01数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "B02数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllA06Size();//获取a06倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a06循环插入
//				appendFileContent(logfilename, "A06数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a06数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A06");
//				System.out.println("a06数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A06数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllA08Size();//获取a08倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a06循环插入
//				appendFileContent(logfilename, "A08数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a08数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A08");
//				System.out.println("a08数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A08数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllA11Size();//获取a011倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a11循环插入
//				appendFileContent(logfilename, "A11数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a11数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A11");
//				System.out.println("a11数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A11数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllA14Size();//获取a14倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a14循环插入
//				appendFileContent(logfilename, "A14数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a14数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A14");
//				System.out.println("a14数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A14数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllA15Size();//获取a15倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a14循环插入
//				appendFileContent(logfilename, "A15数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a15数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A15");
//				System.out.println("a145数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A15数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllA29Size();//获取a29倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a14循环插入
//				appendFileContent(logfilename, "A29数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a29数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A29");
//				System.out.println("a29数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A29数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllA30Size();//获取a30倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a30循环插入
//				appendFileContent(logfilename, "A30数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a30数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A30");
//				System.out.println("a30数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A30数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllA31Size();//获取a31倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a31循环插入
//				appendFileContent(logfilename, "A31数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a31数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A31");
//				System.out.println("a31数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A31数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllA36Size();//获取a36倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a36循环插入
//				appendFileContent(logfilename, "A36数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a36数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A36");
//				System.out.println("a36数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A36数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllA37Size();//获取a37倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a37循环插入
//				appendFileContent(logfilename, "A37数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a37数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A37");
//				System.out.println("a37数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A37数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllA41Size();//获取a41倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a14循环插入
//				appendFileContent(logfilename, "A41数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a41数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A41");
//				System.out.println("a41数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A41数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllA53Size();//获取a53倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a53循环插入
//				appendFileContent(logfilename, "A53数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a53数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A53");
//				System.out.println("aa53数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A53数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getAllA57Size();//获取a57倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a57循环插入
//				appendFileContent(logfilename, "A57数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a57数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_A57(i, personsize, imprecord, photo_file, deptid, "A57");
//				System.out.println("a57数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A57数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			appendFileContent(logfilename, "==============================================="+"\n");
//			//NO.006.001 导入B01表
//			personcount = KingbsGainBS.getA01Size();//获取a01倒入次数
//			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
//			for (int i = 0; i < time; i++) {//a01循环插入
//				appendFileContent(logfilename, "A01数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始"+"\n");
//				System.out.println("a01数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次开始");
//				KingbsGainBS.impTimeData_Table(i, personsize, imprecord, photo_file, deptid, "A01");
//				System.out.println("a01数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束");
//				appendFileContent(logfilename, "A01数据导入"+ DateUtil.getTime()+"===>导入数据共需"+(time)+"次，第"+(i+1)+"次结束"+"\n");
//			}
//			t_n = t_n + personcount;
//			
//			imprecord.setTotalnumber(t_n+"");
//			sess.update(imprecord);
//			appendFileContent(logfilename, "提取完成"+"\n");
//			//系统日志
//			//new LogUtil().createLog("451", "IMP_RECORD", "", "", "导入临时库", new ArrayList());
//			System.out.println(DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>删除缓存文件 开始");
//			UploadHelpFileServlet.delFolder(path);
//			System.out.println(DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>删除缓存文件 结束");
////			sess.getTransaction().commit();
//			appendFileContent(logfilename, "删除缓存文件"+ DateUtil.getTime()+"\n");
//		} catch (Exception e) {
//			UploadHelpFileServlet.delFolder(path);
//			e.printStackTrace();
//			KingbsGainBS.dealRollback(imprecordid);
//			if(sess != null)
//				sess.getTransaction().rollback();
//			this.setMainMessage(e.getMessage());
//			e.printStackTrace();
//		}finally{
//			System.gc();
//		}
//		this.createPageElement("MGrid", ElementType.GRID, true).reload();
//		this.closeCueWindow("win1");
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("zzbsearch");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询文件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("zzbsearch")
	public int lfsearch() throws RadowException{
		String ZZB3_FILE = "D:/KingbsData";
		File file = new File(ZZB3_FILE);
		List<HashMap<String, Object>> gridList = new ArrayList<HashMap<String,Object>>();
		Grid grid = (Grid)this.createPageElement("Fgrid", ElementType.GRID, false);
		try {
			
			if(file.exists()){
				File[] subFiles = file.listFiles();
				if(subFiles!=null && subFiles.length>0){
					for (int i = 0; i < subFiles.length; i++) {
						File file0= subFiles[i];
						String name = file0.getName();
						if(name.substring(name.lastIndexOf(".") + 1).equalsIgnoreCase("zzb3")){
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("fname", name);
							map.put("fabsolutepath", file0.getAbsolutePath().replace("\\", "/"));
							gridList.add(map);
						}
					}
				}
			}
			if(gridList.size()>0){
				grid.setValueList(gridList);
			}else{
				grid.setValueList(new ArrayList<HashMap<String,Object>>());
			}
		} catch (Exception e) {
			throw new RadowException("检索目录失败:"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("btnsx.onclick")
	public int btnsxOnClick()throws RadowException{
		this.setNextEventName("zzbsearch");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public static void appendFileContent(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
           FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	 
	 private String getRootPath() {
			String classPath = this.getClass().getClassLoader().getResource("/").getPath(); 
			try {
				classPath = URLDecoder.decode(classPath, "GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String rootPath  = ""; 
			if("\\".equals(File.separator)){   
				rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
				rootPath = rootPath.replace("/", "\\"); 
			} 
			if("/".equals(File.separator)){   
				rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
				rootPath = rootPath.replace("\\", "/"); 
			}
			return rootPath;
	}
}
