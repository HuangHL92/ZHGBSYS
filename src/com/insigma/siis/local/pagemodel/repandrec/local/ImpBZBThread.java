package com.insigma.siis.local.pagemodel.repandrec.local;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class ImpBZBThread implements Runnable {
	
	private String bzbDeptid;
	private String searchDeptid;
	private String imprecordid;
	private CurrentUser user;
	private UserVO userVo;
	private String fxz;
	private String adress;//照片路径
    public ImpBZBThread(String bzbDeptid, String searchDeptid, String imprecordid, CurrentUser user,UserVO userVo, String fxz, String adress) {
        this.bzbDeptid = bzbDeptid;
        this.searchDeptid = searchDeptid;
        this.imprecordid = imprecordid;
        this.user = user;
        this.userVo = userVo;
        this.fxz = fxz;
        this.adress = adress;
    }

	@Override
	public void run() {
		CommonQueryBS.systemOut("导入程序开始：-----"+ DateUtil.getTime());
		String bzbDeptid = this.bzbDeptid;
		String searchDeptid =this.searchDeptid;
		HBSession sess = HBUtil.getHBSession();
		String filename = "";
		String imprecordid =this.imprecordid;
		String process_run = "1";
		String tableExt = "";
		UploadHzbFileBS uploadbs = new UploadHzbFileBS();
		try {
			//--创建 zzb3导入日志文件
			String logfilename = AppConfig.HZB_PATH + "/temp/upload/zzb3" +DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".txt";
			File logfile = new File(logfilename);
			if(!logfile.exists()){
				logfile.createNewFile();
			}
			appendFileContent(logfilename, "开始导入:"+ DateUtil.getTime()+"\n");
			List<Dataexchangeconf> list = sess.createQuery("from Dataexchangeconf").list();
			//Photos 路径
			String photo_file = "D:/Photos/";
			if (list !=null && list.size()>0) {
				photo_file = list.get(0).getBzbpath() + "/Client/Photos/";
			} else {
				KingbsconfigBS.saveImpDetail(process_run ,"4","失败:导入前请先配置相应信息",imprecordid);
				return ;
			}
			
			//NO.004 获取当前执行导入的操作人员所属机构信息 gr（机构信息）
			CurrentUser user = this.user;   //获取当前执行导入的操作人员信息
			List<B01> grps = null;
			if(user!=null && user.getId()!=null && !user.getId().equals("")){
				grps = HBUtil.getHBSession().createQuery(
						"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
								+ user.getId() + "')").list();
			}
			
			B01 gr = null;
			if (grps != null && grps.size() > 0) {
				gr = grps.get(0);     //获取 当前执行导入的操作人员所属机构信息
				grps.removeAll(grps); //释放 当前执行导入的操作人员所属机构信息列表
				grps.clear();
				grps = null;
				CommonQueryBS.systemOut("NO.004 获取当前执行导入的操作人员所属机构信息 gr（机构信息）机构唯一编码："+gr.getB0111()+"机构名称:"+gr.getB0101()+" 完成时间"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss"));
			}
			//==========================================================================================================
			//NO.005 本次倒入过程记录入导入查询记录表（imp_record），获取 imprecordid（导入查询记录的唯一主键）
//			sess.createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ imprecordid +"')").executeUpdate();
			Imprecord imprecord = (Imprecord) sess.get(Imprecord.class, imprecordid);           //新建 导入查询记录表 记录
			imprecord.setImptime(DateUtil.getTimestamp());     //设置 倒入开始时间 
			imprecord.setImpuserid(user.getId());            //设置 倒入操作人员编码 
			if (gr != null) {   
				imprecord.setImpgroupid(gr.getB0111());      //设置 倒入操作员所属机构编码
				imprecord.setImpgroupname(gr.getB0101());    //设置 倒入操作员所属机构名称
			}
			imprecord.setIsvirety("0");                      //设置 数据校验标志 初始化为0表示 没有校验 
			imprecord.setFilename(filename);                 //设置 本次导入的文件的文件名称
			imprecord.setFiletype("ZZB3");                   //设置 本次导入的文件类型 
			imprecord.setImptype("3");                       //设置 导入类型 3 表示 ZZB3
			imprecord.setImpstutas("1");                     //设置 导入状态为1 （倒入状态 1：未导入、2：已导入、3：打回）
			imprecord.setWrongnumber("0");                   //设置 校验错误的记录条数(未使用)
			imprecord.setTotalnumber("");                   //设置 导入的总记录条数
			imprecord.setProcessstatus("1");
			sess.update(imprecord);                            //保存导入查询记录表 
			imprecordid = imprecord.getImprecordid();
			//============================================================================================================
			process_run = "2";
			imprecord.setPsncount(Long.parseLong(KingbsGainBS.getSizeByTable("A01", bzbDeptid)+""));
			imprecord.setOrgcount(Long.parseLong(KingbsGainBS.getAllB01Size()+""));
		
			sess.update(imprecord);
			KingbsconfigBS.saveImpDetail("1","2","完成",imprecordid);
			KingbsconfigBS.saveImpDetail("2","1","进行中",imprecordid);
			//CommonQueryBS.systemOut("zzb3解压缩结束：-----"+ DateUtil.getTime());
			
			//==========================================================================================================
			//NO.003 获取本应用中和倒入包根节点机构对应的机构信息，主要是impdeptid（本应用 机构唯一编码）deptid（本应用 上级机构的唯一编码）
			//2016年06月15日   北京 检查代码，添加注释 。
			//获取机构 人大金仓数据库中获取根机构信息(b01) 
			B01 b01 = KingbsGainBS.getB01byid(bzbDeptid);
			int tabsize = KingbsGainBS.getTableSize();
			if(b01 == null || (tabsize != 16 && tabsize != 21)){
				KingbsGainBS.dealRollback(imprecordid, tableExt);;
				KingbsconfigBS.saveImpDetail(process_run ,"4","失败:金仓数据格式不匹配，请联系管理员！",imprecordid);//结束提示信息。 
				return ;             //结束倒入
			}
			//获取机构 本应用数据库中获取根机构信息(b01) 
			B01 detp = null;
			String deptid = "";// 根节点上级机构id
			String impdeptid = "";//根节点机构id
			List<B01> detps = HBUtil.getHBSession().createQuery(
					"from B01 t where t.b0101='" + b01.getB0101()  //机构民称
							+ "' and t.b0114='" + b01.getB0114()   //组织机构编码
							+ "'").list();
			if (detps != null && detps.size() > 0) {
				detp = detps.get(0);                   //获取本应用中和人大金仓数据的根机构同组织机构编码、同名称的机构信息
				impdeptid = detps.get(0).getB0111();   //本应用 机构唯一编码
				deptid = detps.get(0).getB0121();      //本应用 上级机构的唯一编码 
				detps.removeAll(detps);                //释放 本应用机构信息列表
				detps.clear();                         
				detps = null;
			} else {
				detp = new B01();
				detp.setB0101(b01.getB0101());
				detp.setB0114(b01.getB0114());
				detp.setB0194(b01.getB0194());
				detp.setB0111(CreateSysOrgBS.selectB0111BySubId(searchDeptid));
				detp.setB0121(searchDeptid);
				detp.setSortid(0L);
				sess.save(detp);
				sess.flush();
				impdeptid = detp.getB0111();   //本应用 机构唯一编码
				deptid = detp.getB0121();      //本应用 上级机构的唯一编码 
			}
			CommonQueryBS.systemOut("NO.003 检测到本应用中和倒入包根节点机构对应的机构信息impdeptid（本应用 机构唯一编码）："+impdeptid+"deptid（本应用 上级机构的唯一编码）:"+deptid+" 完成时间"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss"));
			//==========================================================================================================
			//NO.005 本次倒入过程记录入导入查询记录表（imp_record），获取 imprecordid（导入查询记录的唯一主键）
			tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
			imprecord.setImptemptable(tableExt);
			imprecord.setEmpdeptid(b01.getB0111());          //设置 导入文件中根机构的唯一编码
			imprecord.setEmpdeptname(b01.getB0101());        //设置 导入文件中根机构的名称
			imprecord.setImpdeptid(impdeptid);               //设置 本应用 机构唯一编码
			imprecord.setB0114(b01.getB0114());              //设置 导入文件中根机构的内部编码
			imprecord.setB0194(b01.getB0194());              //设置 导入文件中根机构的法人单位标识
			imprecord.setTablenumber(Long.valueOf(tabsize));
			sess.update(imprecord);                            //保存导入查询记录表 
			uploadbs.createTempTable(tableExt);
			CommonQueryBS.systemOut("NO.005 本次倒入过程记录入导入查询记录表（imprecordid（导入查询记录的唯一主键）："+imprecordid+" 完成时间"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss"));
			//==========================================================================================================
			//NO.006 从人大金仓数据库分表、分批次导入本应用数据库，批次数据根据personsize设定
			CommonQueryBS.systemOut("NO.006 总导入数据开始！---"+ DateUtil.getTime());
			appendFileContent(logfilename, "==============================================="+"\n");
			//声明导入次数变量 
			KingbsconfigBS.saveImpDetail("2","2","完成",imprecordid);
			ImpZzb3Control control = new ImpZzb3Control();
			ImpBZBPartThread t1 = new ImpBZBPartThread(logfilename, imprecord, deptid,  bzbDeptid,userVo, control, tabsize, "1", fxz, adress);
			ImpBZBPartThread t2 = new ImpBZBPartThread(logfilename, imprecord, deptid,  bzbDeptid,userVo, control, tabsize, "2", fxz, adress);
			control.setTableExt(tableExt);
			control.setThd1(new Thread(t1, "zzb3_1_"+imprecordid));
			control.setThd2(new Thread(t2, "zzb3_2_"+imprecordid));
			control.setPhoto_file(photo_file);
			control.start();
		} catch (Exception e) {
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","失败:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//结束提示信息。 
			e.printStackTrace();
			KingbsGainBS.dealRollback(imprecordid, tableExt);;
			e.printStackTrace();
		}finally{
			System.gc();
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
	
	private static String getNo() {
		String no = "";
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int r = random.nextInt(35);
			no = no + key[r];
		}
		CommonQueryBS.systemOut(no);
		return no;
	}
}
