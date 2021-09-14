package com.insigma.siis.local.pagemodel.xbrm.jcgl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.Checkreg;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.entity.Js21;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.sms.SMSSend;
import com.insigma.siis.local.business.utils.SQLiteUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.insigma.siis.local.pagemodel.xbrm.JSGLPageModel;
import com.insigma.siis.local.pagemodel.xbrm.OracleToSqlite;
import com.insigma.siis.local.pagemodel.xbrm.QCJSFileExportBS;
import com.insigma.siis.local.pagemodel.xbrm.expword.ExpJSGLRMB;

import sun.misc.BASE64Encoder;

public class CheckRegPageModel extends PageModel {
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String mainXdbid = this.getPageElement("mainXdbid").getValue();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String deptid = user.getDept();
		String regname1 = this.getPageElement("regname1").getValue();
		String regno = this.getPageElement("regno1").getValue();
		String xm = this.getPageElement("xm").getValue();
		String sfz = this.getPageElement("sfz").getValue();
		String where1 = "";
		if(!"c76c922860fe40c4972db05b0bc21be8".equals(deptid)) {
			where1 = " and userid = '"+user.getId()+"' ";
		}
		if(mainXdbid!=null && !mainXdbid.equals("")) {
			where1 = " and t.checkregid = '"+mainXdbid+"' ";
		}
		String where = "";
		if(regname1!=null&&!"".equals(regname1)){
			where += " and t.regname like '%"+regname1+"%'";
		}
		if(regno!=null&&!"".equals(regno)){
			where += " and t.regno like '%"+regno+"%'";
		}
		if((xm!=null&&!"".equals(xm)) || (sfz!=null&&!"".equals(sfz))){
			where += " and t.checkregid in (select k.checkregid from checkregperson k where 1=1 ";
			if(xm!=null&&!"".equals(xm)) {
				where += " and k.CRP001 like '%"+xm+"%'";
			}
			if(sfz!=null&&!"".equals(sfz)) {
				where += " and k.CRP006 like '%"+sfz+"%'";
			}
			where += ") ";
		}
		if(AppConfig.GBJDWLQH.equals("1")) {
			String rsql = "select checkregid,sum(decode(filetype,'crcrj',1,0)) crjxx,sum(decode(filetype,'crfc',1,0)) fcxx, " + 
					"sum(decode(filetype,'crbx',1,0)) sybxxx,sum(decode(filetype,'crzq',1,0)) gpjjxx, " + 
					"sum(decode(filetype,'crgscg',1,0)) gsxx1,sum(decode(filetype,'crgszw',1,0)) gsxx2 from checkregfile rf"
					+ " where filetype in ('crcrj','crfc','crbx','crzq','crgscg','crgszw') group by checkregid";
			String sql = "select t.checkregid,regno,regname,checkdate,regstatus,groupid,groupname,reguser,"
					+ "regtype,regotherid,r.crjxx,r.fcxx,r.sybxxx,r.gpjjxx,r.gsxx1,r.gsxx2,userid from CHECKREG t,("+rsql+") r"
					+ " where t.checkregid=r.checkregid(+) "+where1+" "+where+" order by createtime desc";
			System.out.println(sql);
			this.pageQuery(sql, "SQL", start, limit);
		} else {
			String sql="select * from CHECKREG t where 1=1 "+where1+" "+where+" order by createtime desc";
			System.out.println(sql);
			this.pageQuery(sql, "SQL", start, limit);
		}
		
		return EventRtnType.SPE_SUCCESS;
	}
	
	public static String  disk = AppConfig.HZB_PATH + "/";
	@PageEvent("allDelete")
	public int allDelete(String checkregids) throws RadowException, AppException{
		try {
			HBSession sess = HBUtil.getHBSession();
			String ids = checkregids.replace(",", "','");
			String sql = "delete from checkreg where checkregid in ('"+ids+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sql = "delete from checkregperson where checkregid in ('"+ids+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sql = "delete from CHECKREGBX where checkregid in ('"+ids+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sql = "delete from CHECKREGCRJJL where checkregid in ('"+ids+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sql = "delete from CHECKREGCRJZJ where checkregid in ('"+ids+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sql = "delete from CHECKREGFC where checkregid in ('"+ids+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sql = "delete from CHECKREGFILE where checkregid in ('"+ids+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sql = "delete from CHECKREGGSCG where checkregid in ('"+ids+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sql = "delete from CHECKREGGSZW where checkregid in ('"+ids+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sql = "delete from CHECKREGZQ where checkregid in ('"+ids+"')";
			sess.createSQLQuery(sql).executeUpdate();
			
			//批次目录
			String idarr [] = checkregids.split(",");
			for (int i = 0; i < idarr.length; i++) {
				String id = idarr[i];
				String directory = "checkregfiles" + File.separator +id+ File.separator;
				String directory_rb_path = disk + directory;
				File file = new File(directory_rb_path);
				if(file.isDirectory()){
					deleteDirectory(directory_rb_path);
				}
			}
			
			sess.flush();
			this.getExecuteSG().addExecuteCode("odin.alert('删除成功！');$('#rb_id').val(''); $('#rb_name').val('');");
			this.setNextEventName("memberGrid.dogridquery");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RadowException("删除异常："+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException {
		
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("chaneStatus")
	public int chaneStatus(String chaneStatus) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String checkregid = this.getPageElement("checkregid").getValue();
		try {
			/*String sql = "update checkreg set regstatus='"+chaneStatus
					+"' where checkregid='"+checkregid+"'";
			sess.createSQLQuery(sql).executeUpdate();*/
			String roleids = "";
			Checkreg cr = (Checkreg) sess.get(Checkreg.class, checkregid);
			cr.setRegstatus(chaneStatus);
			if(cr.getGacltx()!=null && cr.getGacltx().equals("1")) {
				cr.setGacltx("0");
				roleids = roleids + "8294f18b6b93e083016bc01b8ed37e46,";
			}
			if(cr.getZjjcltx()!=null && cr.getZjjcltx().equals("1")) {
				cr.setZjjcltx("0");
				roleids = roleids + "8294f18b6b93e083016bc01efb4a7ecd,";
			}
			if(cr.getGscltx()!=null && cr.getGscltx().equals("1")) {
				cr.setGscltx("0");
				roleids = roleids + "8294f18b6b93e083016bc020dc647f47,";
			}
			sess.update(cr);
			sess.flush();
			if(chaneStatus.equals("0")) {
				String name = cr.getRegname();
				String r = roleids.length()>3?roleids.substring(0,roleids.length()-1):"";
				String sql = "select MOBILE from smt_user u,smt_act t where t.objectid=u.userid and t.roleid in ('"+(r.replace(",", "','"))+"')";
				List<Object> list = sess.createSQLQuery(sql).list();
				String phones = "";
				for (int i = 0; i < list.size(); i++) {
					Object obj = list.get(i);
					if(obj!=null && !obj.toString().trim().equals("")) {
						phones = phones + obj +",";
					}
				}
				if(phones.length()>1) {
					String mst = "无锡干部系统提醒您，您有任务【"+name+"】已取消。";
					try {
						SMSSend.send(phones, mst);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			this.setMainMessage("数据更新成功！");
			this.setNextEventName("memberGrid.dogridquery");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("操作数据异常!");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 删除文件夹下的文件及其子目录
	 * @param sourcePath
	 * @throws FileNotFoundException 
	 */
	public void deleteDirectory(String sourcePath) throws FileNotFoundException{
		try{
			//如果sPath不以文件分隔符结尾，自动添加文件分隔符  
			if (!sourcePath.endsWith(File.separator)) {  
				sourcePath = sourcePath + File.separator;  
			 } 
			File dirFile = new File(sourcePath);
			//如果dir对应是一个目录  
			 if (dirFile.isDirectory()) {  
					//删除文件夹下的所有文件(包括子目录)  
					File[] files = dirFile.listFiles();  
					for (int i = 0; i < files.length; i++) {  
					    //删除子文件  
					    if (files[i].isFile()) {  
					    	File file = new File(files[i].getAbsolutePath());  
					        // 路径为文件
					        if (file.isFile() && file.exists()) {  
					            file.delete();  
					        } 

					    } //删除子目录  
					    else {  
					    	//删除当前文件及其子目录
					    	deleteDirectorall(files[i].getAbsolutePath());
					       //deleteDirectory(files[i].getAbsolutePath());  
					    }  
					}  
			 }
		}catch(Exception e){
			 throw new FileNotFoundException(e.getMessage());
		}
	}
	
	/**
	 * 删除此文件夹，及其子文件与子目录
	 * @param sourcePath
	 */
	public void deleteDirectorall(String sourcePath){
		try{
			//如果sPath不以文件分隔符结尾，自动添加文件分隔符  
			if (!sourcePath.endsWith(File.separator)) {  
				sourcePath = sourcePath + File.separator;  
			 } 
			File dirFile = new File(sourcePath);
			//如果dir对应是一个目录  
			 if (dirFile.isDirectory()) {  
					//删除文件夹下的所有文件(包括子目录)  
					File[] files = dirFile.listFiles();  
					for (int i = 0; i < files.length; i++) {  
					    //删除子文件  
					    if (files[i].isFile()) {  
					    	File file = new File(files[i].getAbsolutePath());  
					        // 路径为文件且不为空则进行删除  
					        if (file.isFile() && file.exists()) {  
					            file.delete();  
					        } 

					    } //删除子目录  
					    else {  
					    	deleteDirectorall(files[i].getAbsolutePath());  
					    }  
					}  
					//删除当前目录  
					dirFile.delete();
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@PageEvent("importSheet")
    public int importSheet(String id) throws RadowException, AppException {
        QCJSFileExportBS fileBS = new QCJSFileExportBS();
        HBSession sess = HBUtil.getHBSession();
        Checkreg cr = (Checkreg) sess.get(Checkreg.class, id);
        String rtype = cr.getRegtype();
        String rotherid = cr.getRegotherid();
        if(!(rtype!=null && rtype.equals("2"))) {
        	this.setMainMessage("此批次为自建批次，不能下载。");
        	return EventRtnType.FAILD;
        }
        if(rotherid!=null && !rotherid.trim().equals("")) {
        	RecordBatch rb = (RecordBatch) sess.get(RecordBatch.class, rotherid);
        	if(rb == null) {
        		this.setMainMessage("没有选拔任用信息不存在或已删除，不能下载。");
            	return EventRtnType.FAILD;
        	}
        } else {
        	this.setMainMessage("没有选拔任用信息不存在或已删除，不能下载。");
        	return EventRtnType.FAILD;
        }
        
        String rbId = rotherid;//批次
        String dc005 = "1";//类别标识
        String cur_hj = "0";//环节
        String cur_hj_4 = "4-1";//讨论决定分环节
        
//        String js0100s=this.getPageElement("js0100s").getValue();
//        String js0100WhereSql="";
//        if(!StringUtil.isEmpty(js0100s)){
//        	js0100WhereSql = js0100s.substring(0, js0100s.length() - 1).replace(",", "','");
//        }
        //审签单
        String personsStr = fileBS.getSheetPersons(rbId, dc005, cur_hj, cur_hj_4, 2,"");
        String data = "";
        List<Js21> list = sess.createQuery(" from Js21 where rbid ='"+rbId+"'").list(); 
        if(list!=null && list.size()>0) {
        	Js21 js21 = list.get(0);
        	if(js21.getJs2101()!=null && !js21.getJs2101().equals("1")) {
        		data = data + "√,";
        	} else {
        		data = data + "□,";
        	}
        	if(js21.getJs2102()!=null && !js21.getJs2102().equals("1")) {
        		data = data + "√,";
        	} else {
        		data = data + "□,";
        	}
        	if(js21.getJs2103()!=null && !js21.getJs2103().equals("1")) {
        		data = data + "√,";
        	} else {
        		data = data + "□,";
        	}
        	if(js21.getJs2104()!=null && !js21.getJs2104().equals("1")) {
        		data = data + "√,";
        	} else {
        		data = data + "□,";
        	}
        	if(js21.getJs2105()!=null && !js21.getJs2105().equals("1")) {
        		data = data + "√,";
        	} else {
        		data = data + "□,";
        	}
        	if(js21.getJs2106()!=null && !js21.getJs2106().equals("1")) {
        		data = data + "√,";
        	} else {
        		data = data + "□,";
        	}
        	if(js21.getJs2107()!=null && !js21.getJs2107().equals("1")) {
        		data = data + "√,□,";
        	} else {
        		data = data + "□,√,";
        	}
        } else {
        	data = "√,√,√,√,√,√,√,□,";
        }
        
        data = data + "," + personsStr;
        String[] Flds = {"tqyj1", "tqyj2", "tqyj3", "tqyj4", "tqyj5", "tqyj6", "sxch1", "sxch2", "gzsy", "rydx"};
        String[] Vals = data.split(",");
        String path = new ExportAsposeBS().signatureSheet(Flds, Vals, "sqd.doc", "审签单.doc", null);
        //任职前听取公安部门意见word
        String[] gonganFlds = {"names","time"};
        String personsStrOne = fileBS.getSheetPersons(rbId, dc005, cur_hj, cur_hj_4, 1,"");
        		
        String time=DateUtil.dateToString(new Date(), "YYYY年MM月DD日");
        String[] gonganVals = {personsStrOne,time};
        new ExportAsposeBS().signatureSheet(gonganFlds, gonganVals, "gonganyj.doc", "任职前听取公安部门意见表.doc", path);
        //任职前听取检察机关意见表
        new ExportAsposeBS().signatureSheet(gonganFlds, gonganVals, "jianchajiguanyj.doc", "任职前听取检察机关意见表.doc", path);
        //听取纪检监察机关意见表
        new ExportAsposeBS().signatureSheet(gonganFlds, gonganVals, "jijianyj.doc", "听取纪检监察机关意见表.doc", path);
        //任职前听取审判机关意见表
        new ExportAsposeBS().signatureSheet(gonganFlds, gonganVals, "shenpanyj.doc", "任职前听取审判机关意见表.doc", path);
        //任职前听取卫生计生部门意见表
        new ExportAsposeBS().signatureSheet(gonganFlds, gonganVals, "weishengyj.doc", "任职前听取卫生计生部门意见表.doc", path);
        //听取意见人员名单,设置最多8个人
        StringBuffer personInfoS = new StringBuffer();
        for (int i = 1; i < 9; i++) {
            if (i == 1) {
                personInfoS.append("num1");
            } else {
                personInfoS.append("," + "num" + i);
            }
            personInfoS.append("," + "name" + i);
            personInfoS.append("," + "sex" + i);
            personInfoS.append("," + "idcard" + i);
            personInfoS.append("," + "zw" + i);
        }
        String[] personInfoFlds = personInfoS.toString().split(",");
        String personsinfo = fileBS.getPersonsInfo(rbId, dc005, cur_hj, cur_hj_4,"");
        String[] personInfoVals = personsinfo.split(",");
        new ExportAsposeBS().signatureSheet(personInfoFlds, personInfoVals, "tingqumd.doc", "听取意见人员名单.doc", path);
        //听取意见人员及配偶名单
        StringBuffer matepersonInfoS = new StringBuffer();
        for (int i = 1; i < 9; i++) {
            if (i == 1) {
                matepersonInfoS.append("n1");
            } else {
                matepersonInfoS.append("," + "n" + i);
            }
            matepersonInfoS.append("," + "na" + i);
            matepersonInfoS.append("," + "s" + i);
            matepersonInfoS.append("," + "i" + i);
            matepersonInfoS.append("," + "z" + i);
            matepersonInfoS.append("," + "p" + i);
            matepersonInfoS.append("," + "ps" + i);
            matepersonInfoS.append("," + "pi" + i);
            matepersonInfoS.append("," + "pz" + i);
        }

        String[] matepersonInfoFlds = matepersonInfoS.toString().split(",");
        String matepersonsinfo = fileBS.matePersonInfo(rbId, dc005, cur_hj, cur_hj_4,"");
        String[] matepersonInfoVals = matepersonsinfo.split(",");
        new ExportAsposeBS().signatureSheet(matepersonInfoFlds, matepersonInfoVals, "peiomd.doc", "听取意见人员及配偶名单.doc", path);

        if (path.length() == 0) {
            return EventRtnType.FAILD;
        }
        String infile = path.substring(0, path.length() - 1) + ".zip";
        SevenZipUtil.zip7z(path, infile, null);
        this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
        this.getExecuteSG().addExecuteCode("window.reloadTree()");
        return EventRtnType.NORMAL_SUCCESS;
    }
}
