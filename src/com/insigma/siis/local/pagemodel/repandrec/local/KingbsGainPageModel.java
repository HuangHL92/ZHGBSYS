package com.insigma.siis.local.pagemodel.repandrec.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.DataVerifyBS;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsInitBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4Zb3Util;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.ImpmodelThread;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;
public class KingbsGainPageModel extends PageModel {
	
	@PageEvent("initbtn.onclick")
	public int initbtn(String name) throws RadowException{
//		this.openWindow("win2", "pages.repandrec.local.Kingbsconfig");
		this.getExecuteSG().addExecuteCode("$h.openWin('initwin2','pages.repandrec.local.Kingbsconfig','配置窗口',650,230,'qqss','/hzb');");

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("imp3btn.onclick")
	public int imp3btn(String name) throws RadowException{
		CommonQueryBS.systemOut("导入程序开始：-----"+ DateUtil.getTime());
		Map<String, String> map = new HashMap<String, String>();
		HBSession sess = HBUtil.getHBSession();
		String path = "D:/KingbsData/unzip";   //生成文件路径
		File sfile = new File("D:/KingbsData");   //备份文件位置
		String filePath = "";
		String filename = "";
		int count = 0;//zzb3 文件数目
		int personcount = 0;
		int personsize = 5000;
		int time = 1;
		String imprecordid = "";
		try {
			sess.beginTransaction();
			if(sfile.exists()){
				File[] flist = sfile.listFiles(); //遍历文件
				for (int i = 0; i < flist.length; i++) {
					File file0 = flist[i];
					String houzui = file0.getName().substring(file0.getName().lastIndexOf(".") + 1,
							file0.getName().length());
					if(houzui.equalsIgnoreCase("zzb3")){
						filePath = file0.getAbsolutePath();
						filename = file0.getName();
						count ++;
					}
				}
			}
			if(count==0){
				this.setMainMessage("指定目录下不存在备份文件！");
				return EventRtnType.NORMAL_SUCCESS;
			} else if(count>1){
				this.setMainMessage("指定目录下存在多份备份文件！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String zippath = path + "/";
			//Photos 路径
			String photo_file = path + "/Photos/";
			File file =new File(zippath);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory()) {       
			    file .mkdirs();    
			} 
			CommonQueryBS.systemOut("zzb3解压缩开始开始：-----"+ DateUtil.getTime());
			SevenZipUtil.extractile(filePath, path+"/", "1234");
			CommonQueryBS.systemOut("zzb3解压缩结束：-----"+ DateUtil.getTime());
			File datfile = new File(path + "/KDataTmp/GWYDB.dat");   //备份文件位置
			if(datfile.exists() && datfile.isFile()){
				KingbsGainBS.cmdImpKingBs(datfile.getAbsoluteFile());
				CommonQueryBS.systemOut("导入金仓数据库成功！---"+ DateUtil.getTime());
			} else {
				this.setMainMessage("备份文件出错！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//获取机构
			B01 b01 = KingbsGainBS.getB01byParentid("");
			if(b01 == null){
				this.setMainMessage("系统出错，请联系管理员！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			B01 detp = null;
			String deptid = "";// 根节点上级机构id
			String impdeptid = "";//根节点机构id
			List<B01> detps = HBUtil.getHBSession().createQuery(
					"from B01 t where t.b0101='" + b01.getB0101()
							+ "' and t.b0114='" + b01.getB0114()
							+ "'").list();
			if (detps != null && detps.size() > 0) {
				detp = detps.get(0);
				impdeptid = detps.get(0).getB0111();
				deptid = detps.get(0).getB0121();
			} else {
				this.setMainMessage("为匹配到机构，请检测！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//获取当前人员机构
			CurrentUser user = SysUtil.getCacheCurrentUser();
			List<B01> grps = HBUtil.getHBSession().createQuery(
					"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
							+ user.getId() + "')").list();
			B01 gr = null;
			if (grps != null && grps.size() > 0) {
				gr = grps.get(0);
			}
			
			//获取机构
			personcount = KingbsGainBS.getA01Size();
			time = personcount/personsize + (personcount%personsize!=0? 1 : 0);
			
			//导入记录
			Imprecord imprecord = new Imprecord();
			imprecord.setImptime(DateUtil.getTimestamp());
			imprecord.setImpuserid(user.getId());
			if (gr != null) {
				imprecord.setImpgroupid(gr.getB0111());
				imprecord.setImpgroupname(gr.getB0101());
			}
			imprecord.setIsvirety("0");
			imprecord.setFilename(filename);
			imprecord.setFiletype("ZZB3");
			imprecord.setImptype("3");
			imprecord.setEmpdeptid(b01.getB0111());
			imprecord.setEmpdeptname(b01.getB0101());
			imprecord.setImpdeptid(impdeptid);
			imprecord.setImpstutas("1");
			imprecord.setB0114(b01.getB0114());
			imprecord.setB0194(b01.getB0194());
			imprecord.setWrongnumber("0");
			imprecord.setTotalnumber("0");
			sess.save(imprecord);
			imprecordid = imprecord.getImprecordid();
			CommonQueryBS.systemOut("导入数据开始！---"+ DateUtil.getTime());
			for (int i = 0; i < time; i++) {
//				KingbsGainBS.impTimeData(i, personsize, imprecord, photo_file, deptid);
			}
			CommonQueryBS.systemOut("导入数据结束！---"+ DateUtil.getTime());
			UploadHelpFileServlet.delFolder(path);
			CommonQueryBS.systemOut("删除临时文件！---"+ DateUtil.getTime());
		} catch (Exception e) {
			if(sess != null)
				sess.getTransaction().rollback();
			if(!imprecordid.equals(""))
				KingbsGainBS.dealRollback(imprecordid,null);
			this.setMainMessage(e.getMessage());
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public static Long getFileSize(File f) {
		FileChannel fc = null;
		try {
			if (f.exists() && f.isFile()) {
				FileInputStream fis = new FileInputStream(f);
				fc = fis.getChannel();
				return fc.size();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fc) {
				try {
					fc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return 0L;
	}

	private String getPath() {
		// TODO Auto-generated method stub
		String classPath = getClass().getClassLoader().getResource("/").getPath(); 
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath  = ""; 
		//windows下 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		//linux下 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		//上传路径
		String upload_file = rootPath + "zipload/";
		try {
			File file =new File(upload_file);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		//解压路径
		String zip = upload_file + uuid + "/";
		return zip;
	}
	
	private String getRootPath() {
		// TODO Auto-generated method stub
		String classPath = getClass().getClassLoader().getResource("/").getPath(); 
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath  = ""; 
		
		//windows下 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		//linux下 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		return rootPath;
	}
	/**
	 * 
	 * @param name
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("imp1btn.onclick")
	public int imp1btn() throws RadowException{
		this.openWindow("win1", "pages.repandrec.local.KingbsZZBFile");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 
	 * @param name
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("imp2btn.onclick")
	public int imp2btn() throws RadowException{
//		this.openWindow("win1", "pages.repandrec.local.KingbsZZBLoad");
		this.getExecuteSG().addExecuteCode("$h.openWin('win1123','pages.repandrec.local.KingbsZZBLoad','ZZB3数据导入',650,200,'qq','"+request.getContextPath()+"');");

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("imp4btn.onclick")
	public int imp4btn() throws RadowException{
//		this.openWindow("win4", "pages.repandrec.local.KingbsBZBLoad");
		this.getExecuteSG().addExecuteCode("$h.openWin('win1121','pages.repandrec.local.KingbsBZBLoad','抽取数据',600,270,'qq','"+request.getContextPath()+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("imp10btn.onclick")
	public int imp10btn() throws RadowException{
		this.openWindow("win10", "pages.repandrec.local.PhotoLoad");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("reset.onclick")
	@NoRequiredValidate
	public int resetbtn(String name) throws RadowException{
		this.getPageElement("createtimesta").setValue("");
		this.getPageElement("createtimeend").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 查询 -点击查询按钮，进行查询
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("query.onclick")
	@NoRequiredValidate           
	@OpLog
	public int queryonclick()throws RadowException, AppException {
		this.setNextEventName("MGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("MGrid.dogridquery")
	@NoRequiredValidate           ///??????
	public int dogridQuery(int start,int limit) throws RadowException{
		String to_format = DBUtil.getDBType().equals(DBType.MYSQL)? "DATE_FORMAT(imp_time,'%Y%m%d %T')": "to_char(imp_time,'yyyymmdd hh24:mi:ss')";
		StringBuffer sql = new StringBuffer("select file_name filename,"+to_format+" imptime,emp_dept_name empgroupname," +
				"is_virety isvirety, wrong_number wrongnumber,total_number totalnumber,imp_record_id imprecordid,imp_stutas impstutas " +
				"from Imp_record where 1=1 and imp_type='3' and PROCESS_STATUS = '2' ");
		String st = this.getPageElement("createtimesta").getValue();
		String et = this.getPageElement("createtimeend").getValue();
		String impstutas = this.getPageElement("impstutas1").getValue();
		
		if(impstutas != null && !impstutas.equals("0")){
			sql.append(" and imp_stutas='"+ impstutas +"' ");
		}
		if(st != null && !st.equals("")){
			sql.append(" and "+to_format+" >='" +st+ "'");
		}
		if(et != null && !et.equals("")){
			sql.append(" and "+to_format+" <='" +et+ "'");
		}
		sql.append(" order by imp_time desc,imp_stutas asc");
		this.pageQuery(sql.toString(),"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	/**
	 * 打开校验窗口
	 * @param name
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("openDataVerifyWin")
	public int openDataVerifyWin(String imprecordid) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			Imprecord imd = (Imprecord) sess.get(Imprecord.class, imprecordid);
			String type = imd.getImpstutas();
			if(type!=null && type.equals("3")){
				this.setMainMessage("数据已拒绝，不能校验！");
			} else if(type!=null && type.equals("4")){
				this.setMainMessage("数据接收中，不能校验，请点击‘详情’查看进度！");
			} else if(type!=null && type.equals("2")){
				this.setMainMessage("数据已接收，不能校验！");
			} else {
//				this.setRadow_parent_data("2@"+imprecordid);
//				this.openWindow("dataVerifyWin", "pages.sysorg.org.orgdataverify.OrgDataVerify");
//				System.out.println("this.setRadow_parent_data"+("2@"+imprecordid));
//				System.out.println("'校验窗口',1000,600, '2@+"+imprecordid+"','/hzb');");
				this.getExecuteSG().addExecuteCode("$h.openWin('dataVerifyWin','pages.sysorg.org.orgdataverify.OrgDataVerify','校验窗口',700,598, '2@"+imprecordid+"','/hzb',null,{maximizable:false,resizable:false});");

			}
		} catch (Exception e) {
			this.setMainMessage("数据异常。");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("MGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 打开对比窗口 张翔飞
	 * @param name
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("dataComp")
	public int dataCompWin(String imprecordid) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			Imprecord imd = (Imprecord) sess.get(Imprecord.class, imprecordid);
			
			//接收数据前的校验工作
			/*String imptemptable = imd.getImptemptable().toUpperCase();
			if(imptemptable!=null && !"".equals(imptemptable.toString())){
				this.getExecuteSG().addExecuteCode("ShowCellCover('start','系统提示','正在进行接收前数据校验,请稍等...');");
				//1、机构编码不能为空
				String sql1 = "SELECT COUNT (1) FROM B01"+imptemptable+" B WHERE 1 = 1 AND B.B0114 IS NULL";
				Object obj1 = sess.createSQLQuery(sql1).uniqueResult();
				if(obj1!=null){
					Integer one = Integer.parseInt(""+obj1);
					if(one > 0){
						this.getExecuteSG().addExecuteCode("ShowCellCover('failure','系统提示','无法接受数据（发现数据中含空的机构编码）！');");
						return EventRtnType.FAILD;
					}
				}
				
				//2、机构编码不能重复
				String sql2 = "";
				if(DBType.ORACLE == DBUtil.getDBType()){
					sql2 = "SELECT COUNT(1) FROM B01 WHERE 1 = 1 AND (EXISTS (SELECT 1 FROM B01 B WHERE B.B0114 = B01.B0114 AND B.B0111 != B01.B0111 AND B0111 LIKE '"+imptemptable+"%'))";
				}else{
					sql2 = "SELECT COUNT(1) FROM B01,B01 AS B WHERE B.B0114 = B01.B0114 AND B.B0111 != B01.B0111 AND B01.B0111 LIKE '"+imptemptable+"%'";
				}
				Object obj2 = sess.createSQLQuery(sql2).uniqueResult();
				if(obj2!=null){
					Integer one = Integer.parseInt(""+obj2);
					if(one > 0){
						this.getExecuteSG().addExecuteCode("ShowCellCover('failure','系统提示','无法接受数据（发现数据中含重复机构编码）！');");
						return EventRtnType.FAILD;
					}
				}
				
				//3、机构内人员身份证不能为空
				String sql3 = "SELECT COUNT(1) FROM A01"+imptemptable+" A WHERE 1 = 1 AND A.A0184 IS NULL";
				Object obj3 = sess.createSQLQuery(sql3).uniqueResult();
				if(obj3!=null){
					Integer one = Integer.parseInt(""+obj3);
					if(one > 0){
						this.getExecuteSG().addExecuteCode("ShowCellCover('failure','系统提示','无法接受数据（发现数据中含空的人员身份证）！');");
						return EventRtnType.FAILD;
					}
				}
				
				//4、机构内人员身份证不能重复  
				String sql4 = "";
				if(DBType.ORACLE == DBUtil.getDBType()){
					sql4 = "SELECT COUNT(1) FROM A01 WHERE 1 = 1 AND (EXISTS (SELECT 1 FROM A01 A,A02 WHERE A.A0184 = A01.A0184 AND A .a0000 != A01.a0000 AND A02.A0201B LIKE '"+imptemptable+"%' AND A.A0000 = A02.A0000))";
				}else{
					sql4 = "SELECT COUNT(1) FROM A01,A01 AS A,A02 WHERE A.A0184 = A01.A0184 AND A.a0000 != A01.a0000 AND A02.A0201B LIKE '"+imptemptable+"%' AND A.A0000 = A02.A0000";
				}
				Object obj4 = sess.createSQLQuery(sql4).uniqueResult();
				if(obj4!=null){
					Integer one = Integer.parseInt(""+obj4);
					if(one > 0){
						this.getExecuteSG().addExecuteCode("ShowCellCover('failure','系统提示','无法接受数据（发现数据中含重复人员身份证）！');");
						return EventRtnType.FAILD;
					}
				}
				
				this.getExecuteSG().addExecuteCode("Ext.MessageBox.hide();");
			}else{
				this.setMainMessage("临时表不存在，不能接收数据！");
				return EventRtnType.FAILD;
			}*/
			
			String type = imd.getImpstutas();
			if(type!=null && type.equals("3")){
				this.setMainMessage("数据已拒绝，不能接收数据！");
			} else if(type!=null && type.equals("4")){
				this.setMainMessage("数据接收中，不能接收数据！");
			} else if(type!=null && type.equals("2")){
				this.setMainMessage("数据已接收，不能接收数据！");
			} else {
				//this.getExecuteSG().addExecuteCode("$h.openWin('dataComparisonWin','pages.dataverify.DataComparison','校验窗口',900,550, '"+imprecordid+"','"+request.getContextPath()+"');");
				batchPrintBefore(imprecordid);
			}
		} catch (Exception e) {
			this.setMainMessage("数据异常。");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@NoRequiredValidate
    public int batchPrintBefore(String imprecordid) throws RadowException, AppException, SQLException{
        HBSession sess = HBUtil.getHBSession();
        Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
        String b0114 = imp.getB0114();
        Connection conn = sess.connection();
        Statement stmt = conn.createStatement();
        ResultSet rs =  null;
        String sql5 = "select count(b0111) from b01 where b0111 <> '-1'";
        rs = stmt.executeQuery(sql5);
        int size = 0;
        while(rs.next()){
            size = rs.getInt(1);
        }
//      String sql3 = "select b0114 from b01 where B0114 = '"+ b0114 +"'";
//      rs = stmt.executeQuery(sql3);
        if((size== 0 && imp.getImpdeptid().equals("001.001")) || size > 0 || imp.getImptype().equals("4")){
            String impdeptid = imp.getImpdeptid();
            String imptype = imp.getImptype();
            
            String ftype = imp.getFiletype();
//          if(imp.getIsvirety() == null || imp.getIsvirety().equals("") ||imp.getIsvirety().equals("0")){
//              this.setMainMessage("请先进行校验，再导入数据");
//              return EventRtnType.NORMAL_SUCCESS;
//          }
            if(imp.getImpstutas() != null && !imp.getImpstutas().equals("1")){
                if(imp.getImpstutas().equals("2")){
                    this.setMainMessage("数据已导入，不能重复导入。");
                } else if(imp.getImpstutas().equals("4")){
                    this.setMainMessage("数据接收中，不能重复接收。");
                } else {
                    this.setMainMessage("数据已拒绝，不能导入。");
                }
                return EventRtnType.NORMAL_SUCCESS;
            }
            //this.getExecuteSG().addExecuteCode("var grid = odin.ext.getCmp('errorGrid9');document.getElementById('grid9_totalcount').value=grid.getStore().getTotalCount();");
            if(imptype.equals("4")){
                this.getExecuteSG().addExecuteCode("radow.doEvent('impconfirmBtn2','1')");
            } else {
            	this.getExecuteSG().addExecuteCode("$h2.confirm('系统提示','是否更新导入的机构信息？',200,function(id){" +
                        "if(id=='ok'){" +
                        "           radow.doEvent('impconfirmBtn2','1');" +
                            "}else if(id=='cancel'){" +
                            "   " +
                            "}" +
                        "});");
            }
        }else{
            this.setMainMessage("没有对应机构,请新建该机构!");
        }
        return EventRtnType.NORMAL_SUCCESS;
    }
	
	/**
     * 导入正确数据前提示信息
     * @return
     * @throws RadowException
     */
    @PageEvent("impconfirmBtn2")
    @NoRequiredValidate
    public int impmodelOnclickBefore(String str) throws RadowException{
        //导入校验(业务类型为2) 
        //String bsType = this.getPageElement("bsType").getValue();
        String imprecordid = this.getPageElement("imprecordid").getValue();
        HBSession sess = HBUtil.getHBSession();
        try {
     	   Imprecord  impRecord = (Imprecord) sess.get(Imprecord.class, imprecordid);
     	  //数据包内含人数
     	   //Long psn = impRecord.getPsncount();
     	   String psn = impRecord.getTotalnumber();
     	   
     	   StringBuffer sqlbf = new StringBuffer();
     	   sqlbf.append("SELECT count(1) ")
	          		.append(" FROM A01"+impRecord.getImptemptable()+" A,")
	          		.append(" (SELECT DISTINCT A01.A0000, A01.A0101, A01.A0184")
	          		.append(" FROM A01, A02")
	          		.append(" WHERE A01.A0000 = A02.A0000")
	          		.append(" AND A02.A0201B LIKE '"+impRecord.getImpdeptid()+"%'")
	          		.append(" ) B")
	          		.append(" WHERE A.A0184 = B.A0184");
     	   Object obj = sess.createSQLQuery(sqlbf.toString()).uniqueResult();
     	   
     	   if(Integer.parseInt(obj.toString())>=Integer.parseInt(psn)){
  			  	obj = psn;
     	   }
     	   
     	   StringBuffer sqlbf2 = new StringBuffer();
     	   sqlbf2.append("select count(1) from b01 t, b01"+impRecord.getImptemptable()+" k where ")
	          		.append(" t.b0114=k.b0114 and t.b0111 not like '"+impRecord.getImpdeptid()+"%'");
     	   Object obj2 = sess.createSQLQuery(sqlbf2.toString()).uniqueResult();
     	   if(Integer.parseInt(obj.toString())>0 || Integer.parseInt(obj2.toString())>0){
                /*this.setMainMessage((Integer.parseInt(obj.toString())>0?"导入数据中的人员身份证号码与系统中重复 "+obj.toString()+" 人！":"")
             		   +(Integer.parseInt(obj2.toString())>0?"导入数据中的机构编码与系统中其他机构重复 "+obj2.toString()+" 个！":"")+ "是否继续接收信息？");
                this.setMessageType(EventMessageType.CONFIRM); 
                this.addNextEvent(NextEventValue.YES,"impmodel", str);*/
     		   String tip = (Integer.parseInt(obj.toString())>0?"导入数据中的人员身份证号码与系统中重复 "+obj.toString()+" 人！":"")
             		   +(Integer.parseInt(obj2.toString())>0?"导入数据中的机构编码与系统中其他机构重复 "+obj2.toString()+" 个！":"") + "是否继续接收信息？";
               this.getExecuteSG().addExecuteCode("$h1.confirm('系统提示','"+tip+"',300,function(id){" +
                       "if(id=='ok'){" +
                       "           radow.doEvent('impmodel','"+str+"');" +
                           "}else if(id=='cancel'){}"
                           + "});");
            }else{
                this.impmodelOnclick(str);
            }
			} catch (Exception e) {
				e.printStackTrace();
			}
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    /**
     * 导入正确数据
     * @return
     * @throws RadowException
     */
    @PageEvent("impmodel")
    @NoRequiredValidate
    public int impmodelOnclick(String str)throws RadowException{
        HBSession sess = HBUtil.getHBSession();
        try {
            CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
            String imprecordid = this.getPageElement("imprecordid").getValue();
            /**
             * 历史库代码暂无
             */
            Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
//          
            UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
            //-------------------------------------------------
            String sql1 = "delete from Datarecrejlog where imprecordid = '"+imprecordid+"' ";
            sess.createQuery(sql1).executeUpdate();
            //------------------------------------------------
            ImpmodelThread thr = new ImpmodelThread(imp,str, user,userVo); 
            new Thread(thr).start();
//          this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('dataComparisonWin').close();");
//          this.closeCueWindow();
//          this.setRadow_parent_data(imprecordid);
//          this.openWindow("refreshWin", "pages.dataverify.RefreshOrgRecRej");
//          this.getExecuteSG().addExecuteCode("realParent.$h.openWin('refreshWin1','pages.dataverify.RefreshOrgRecRej','接收窗口',700,445, '"+imprecordid+"','"+request.getContextPath()+"');");
            this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin1','pages.dataverify.RefreshOrgRecRej','接收窗口',700,445, '"+imprecordid+"','"+request.getContextPath()+"');");
        } catch (Exception e) {
            if(sess!=null)
                sess.getTransaction().rollback();
            e.printStackTrace();
        }
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    /**
     * 全部退回
     * @return
     * @throws RadowException
     */
    @PageEvent("rejectBtn")
    @NoRequiredValidate
    public int rejectBtnOnclick(String imprecordid)throws RadowException{
        String b0111 = imprecordid;
        String bsType = "2";//导入校验
        HBSession sess = HBUtil.getHBSession();
        try {
            sess.beginTransaction();
            Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
            String imptemptable = imp.getImptemptable();
            String ftype = imp.getFiletype();
            if(imp.getImpstutas().equals("2")){
                this.setMainMessage("数据已导入，不能拒绝。");
                return EventRtnType.NORMAL_SUCCESS;
            } else if(imp.getImpstutas().equals("4")){
                this.setMainMessage("数据接收中，不能拒绝。");
                return EventRtnType.NORMAL_SUCCESS;
            } else if(imp.getImpstutas().equals("3")){
                this.setMainMessage("数据已拒绝，不能重复拒绝。");
                return EventRtnType.NORMAL_SUCCESS;
            }
            String str = "";
//          String str = this.FkImpData(imprecordid, "");
            //打回后清除错误信息Verify_Error_List
            sess.createSQLQuery(" delete from Verify_Error_List where vel004 = '"+b0111+"' and vel005='"+bsType+"'").executeUpdate();
            String tables1[] = {"A01", "A02","A06","A08", "A11", "A14", "A15", "A29","A30", 
                    "A31","A36","A37","A41", "A53","A57","A60","A61","A62","A63","A64", "B01", "B_E", "I_E","A05", "A68", "A69", "A71", "A99Z1"};
            for (int i = 0; i < tables1.length; i++) {
         	   Object obj = null;
         	   if(DBUtil.getDBType().equals(DBType.ORACLE)){
         		   obj = sess.createSQLQuery("select count(*) from user_tables where table_name = '"+tables1[i]+""+imptemptable+"'").uniqueResult();
         	   }else{
         		   obj = sess.createSQLQuery("select count(*) from INFORMATION_SCHEMA.TABLES where TABLE_NAME = '"+tables1[i]+""+imptemptable+"' and TABLE_SCHEMA = 'ZWHZYQ'").uniqueResult();
         	   }
         	   Integer num = Integer.parseInt("" + obj);
     		   if(num != 0){
     			   sess.createSQLQuery(" drop table " + tables1[i] + ""+imptemptable+"").executeUpdate();
     		   }
            }
            imp.setImpstutas("3");
            sess.update(imp);
            sess.flush();
            sess.getTransaction().commit();
            PhotosUtil.removDirImpCmd(imprecordid,"");
            if(str != null && !str.equals(""))
                this.getExecuteSG().addExecuteCode("var w=window.open('ProblemDownServlet?method=downFile&prid="+ URLEncoder.encode(URLEncoder.encode(str,"UTF-8"),"UTF-8")+"');  setTimeout(cc,600); function cc(){w.close();}");
            if (ftype.equalsIgnoreCase("hzb") || ftype.equalsIgnoreCase("zb3")) {
                new LogUtil().createLog("462", "IMP_RECORD", "", "", "拒绝清除", new ArrayList());
            } else if (ftype.equalsIgnoreCase("zzb3")){
                new LogUtil().createLog("472", "IMP_RECORD", "", "", "拒绝清除", new ArrayList());
            }
            //this.getExecuteSG().addExecuteCode("realParent.odin.ext.getCmp('MGrid').store.reload();");
            this.getExecuteSG().addExecuteCode("odin.ext.getCmp('MGrid').store.reload();");
            this.setMainMessage("已拒绝！");
        } catch (UnsupportedEncodingException e) {
            sess.getTransaction().rollback();
            e.printStackTrace();
        } catch (AppException e) {
            sess.getTransaction().rollback();
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return EventRtnType.NORMAL_SUCCESS;
    }
}
