package com.insigma.siis.local.pagemodel.publicServantManage;


import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.query.PageQueryData;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A01Oplog;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A99Z1;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.extra.ExtraTags;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.publicServantManage.QueryPersonListBS;
import com.insigma.siis.local.business.slabel.TagDazsbfj;
import com.insigma.siis.local.business.slabel.TagKcclfj2;
import com.insigma.siis.local.business.slabel.TagNdkhdjbfj;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.epsoft.config.SysCodeUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.DBUtils;

public class AddZHGBRmbPageModel extends PageModel {
	private LogUtil applog = new LogUtil();

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		if(DBUtils.isNoGbmc(SysManagerUtils.getUserId())) {
			this.getExecuteSG().addExecuteCode("$('#tabs23hid').show();");
		}
		if(!DBUtils.isNoGbmc(SysManagerUtils.getUserId())) {
			this.getExecuteSG().addExecuteCode("$('#tabs23hid').hide();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
//	@PageEvent("zylxhid")
//	public void zylxhid(){
//		if(DBUtils.isNoGbmc(SysManagerUtils.getUserId())) {
//			this.getExecuteSG().addExecuteCode("$('#tabs23hid').show();");
//		}
//		if(!DBUtils.isNoGbmc(SysManagerUtils.getUserId())) {
//			this.getExecuteSG().addExecuteCode("$('#tabs23hid').hide();");
//		}
//	}

	/*@PageEvent("printView")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int pdfView(String a0000AndFlag) throws RadowException, AppException{

		String[] params =this.request.getParameter("a0000AndFlag").split(",");
		String a0000 = params[0]; 										//人员ID
		Boolean flag = params[1].equalsIgnoreCase("true")?true:false;  	//是否打印拟任免信息
		String pdfPath = "";  											//pdf文件路径

		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		list.add(a0000);
		List<String> pdfPaths = null;
		try {
			pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"eebdefc2-4d67-4452-a973-5f7939530a11","pdf",a0000,list2);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String newPDFPath = ExpRar.expFile()+UUID.randomUUID().toString().replace("-", "")+".Pdf";
		QueryPersonListBS.mergePdfs(pdfPaths,newPDFPath);
		newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
		newPDFPath = "/hzb"+ newPDFPath;
		this.request.getSession().setAttribute("pdfFilePath", newPDFPath);
		//this.getPageElement("pdfPath").setValue(newPDFPath);
		String ctxPath = request.getContextPath();
		//this.getExecuteSG().addExecuteCode("alert(222);$h.openPageModeWin('pdfViewWin','pages.publicServantManage.PdfView','打印任免表',700,500,1,'"+ctxPath+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}*/


	/**
	 * 下一人
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("addnew")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int addnew(String confirm)throws RadowException, AppException{
		List<String> a0000list = (List<String>)this.request.getSession().getAttribute("personIdSet");
		String a0000Exists = this.request.getParameter("a0000");
		String a0101 = this.request.getParameter("a0101");
		A01 a01 = null;
		HBSession sess = HBUtil.getHBSession();
		String a0000 = UUID.randomUUID().toString();

		try {
			a01 = (A01)sess.get(A01.class, a0000Exists);
			if(a01==null){
				//this.setMainMessage("该人员在系统中不存在");
				//return EventRtnType.NORMAL_SUCCESS;
			}
			if("4".equals(a01.getStatus())){
				this.setMainMessage("当前页面信息未保存！请先保存");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//a01 = new A01();
		} catch (Exception e) {
			//this.setMainMessage("该人员在系统中不存在");
			//return EventRtnType.NORMAL_SUCCESS;
		}


		if(a0000list!=null&&a0000list.size()>0){
			int index = a0000list.indexOf(a0000Exists);
			if(index>=0){
				a0000list.add(index, a0000);
			}
		}
		a01 = new A01();
		a01.setA0000(a0000);
		a01.setA0163("1");//默认现职人员
		a01.setA14z101("无");//奖惩描述
		a01.setStatus("4");
		a01.setA0197("0");//基层工作经历时间两年以上
		a01.setTbr(SysManagerUtils.getUserId());
		a01.setTbsj(DateUtil.getTimestamp().getTime());
		a01.setA0155(DateUtil.getTimestamp().toString());
		a01.setA0128("健康");
		sess.save(a01);
		sess.flush();
		this.setRmb(a01);
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 下一人
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("showNextclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int showNextclick(String confirm)throws RadowException, AppException{

		String action = this.request.getParameter("action");
		String msg = "";
		if("1".equals(action)){
			msg = "无法获取下一位，请在查询列表中选中人员重新打开任免表后尝试！";
		}else{
			msg = "无法获取上一位，请在查询列表中选中人员重新打开任免表后尝试！";
		}
		String a0000 = this.request.getParameter("a0000");
		String a0000Exists = a0000;
		String start = this.request.getParameter("start");
		String a0101 = this.request.getParameter("a0101");
		String gridName = this.request.getParameter("gridName");
		String querySQL = "";
		if("peopleInfoGrid".equals(gridName)){
			querySQL = (String)this.request.getSession().getAttribute("allSelect");
		}else if("persongrid2".equals(gridName)){
			querySQL = (String)this.request.getSession().getAttribute("imgSelect");
		}else if("persongrid1".equals(gridName)){//事务提醒中使用
			querySQL = (String)this.request.getSession().getAttribute("sql_swtx");
		}else if("errorDetailGrid2".equals(gridName)){//人员校核结果和整库里的人员校核结果sql
			querySQL = (String)this.request.getSession().getAttribute("vsql");
			if(querySQL==null){
				querySQL = (String)this.request.getSession().getAttribute("v2sql");
			}
			querySQL = querySQL.replace("vel002,", "vel002 a0000,");
		}else if("IDerrorDetailGrid".equals(gridName)){//身份证校核结果sql
			querySQL = (String)this.request.getSession().getAttribute("v3sql");
			querySQL = querySQL.replace("vel002,", "vel002 a0000,");
		}else if("repeatInfogrid".equals(gridName)){//人员查重sql
			querySQL = (String)this.request.getSession().getAttribute("peopleQueryRepeat");
		}else if("ppsData".equals(gridName)){//pps抽数中使用
			querySQL = (String)this.request.getSession().getAttribute("sql_pps");
		}else{
			querySQL = (String)this.request.getSession().getAttribute("allSelect");
		}

		HBSession sess = HBUtil.getHBSession();
		try {
			A01 a01 = (A01)sess.get(A01.class, a0000Exists);
			if("4".equals(a01.getStatus())&&a0101!=null&&!"".equals(a0101)){
				this.setMainMessage("当前页面信息未保存。清空【姓名】输入项可继续操作！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//a01 = new A01();
		} catch (Exception e) {
		}


		List<String> a0000list = (List<String>)this.request.getSession().getAttribute("personIdSet");
		if(a0000list==null){
			int s = 0;
			try {
				s = Integer.valueOf(start);
				if(s<200){
					s=0;
				}else{
					s = s-200;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			int limit = 500;

			if(querySQL!=null){
				if(DBUtil.getDBType().equals(DBType.MYSQL))
					querySQL = "select a0000 from ("+querySQL+") t limit "+s+","+limit;
				else
					querySQL = "select a0000 from (select rownum as numrow,c.* from (" + querySQL + ") c  where rownum<=" + (s + limit)+") where numrow>=" + (s + 1) ;

				a0000list = sess.createSQLQuery(querySQL).list();
				this.request.getSession().setAttribute("personIdSet",a0000list);
				if(a0000list.size()>0){
					int index = a0000list.indexOf(a0000);
					int next = index + Integer.valueOf(action);
					if(index>=0){
						if(a0000list.size()<=next){
							this.setMainMessage("到底了！");
							return EventRtnType.NORMAL_SUCCESS;
						}else if(next<0){
							this.setMainMessage("到顶了！");
							return EventRtnType.NORMAL_SUCCESS;
						}else{
							a0000 = a0000list.get(next);
						}
					}else{
						this.setMainMessage(msg);
						return EventRtnType.NORMAL_SUCCESS;
					}
				}else{
						this.setMainMessage(msg);


					return EventRtnType.NORMAL_SUCCESS;
				}
			}else{
					this.setMainMessage(msg);


				return EventRtnType.NORMAL_SUCCESS;
			}
		}else{
			if(a0000list.size()>0){
				int index = a0000list.indexOf(a0000);
				int next = index + Integer.valueOf(action);
				if(index>=0){
					if(a0000list.size()<=next){
						this.setMainMessage("到底了！");
						return EventRtnType.NORMAL_SUCCESS;
					}else if(next<0){
						this.setMainMessage("到顶了！");
						return EventRtnType.NORMAL_SUCCESS;
					}else{
						a0000 = a0000list.get(next);
					}
				}else{
					this.setMainMessage(msg);
					return EventRtnType.NORMAL_SUCCESS;
				}
			}else{
				this.setMainMessage(msg);
				return EventRtnType.NORMAL_SUCCESS;
			}
		}

		A01 a01 = null;
		try {

			a01 = (A01)sess.get(A01.class, a0000);
			if(a01==null){
				this.setMainMessage("该人员在系统中不存在");
				return EventRtnType.NORMAL_SUCCESS;
			}

				//a01 = new A01();
			//a01 = new A01();
		} catch (Exception e) {
			this.setMainMessage("该人员在系统中不存在");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setRmb(a01);
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 *现职人员年龄问题
	 **/
	@PageEvent("validateAgeByA0163")
	public int validateAgeByA0163() throws RadowException, AppException{
		try {
			String a0163 = this.request.getParameter("a0163");
			String sql="select sub_code_value from code_value where code_type='ZB126' and code_value='"+a0163+"'";
			HBSession sess=HBUtil.getHBSession();
			String sub_code_value=null;
			if(sess.createSQLQuery(sql).uniqueResult()!=null) {
				sub_code_value=sess.createSQLQuery(sql).uniqueResult().toString();
			}else {
				sub_code_value="-1";
			}
			if((sub_code_value!=""||sub_code_value!=null)&&(sub_code_value=="1"||sub_code_value.equals("1"))) {
				//获取出生年月判断年龄
				String a0107 = this.request.getParameter("a0107");
				Date a0107Date=DateUtil.stringToDate(a0107,"yyyymmdd");
				String newDate=DateUtil.getcurdate();
				int age=DateUtil.getAgeByBirthday(a0107Date);
				if(age<18||age>63) {
					this.getExecuteSG().addExecuteCode("isSave()");
				}else {
					this.getExecuteSG().addExecuteCode("unlimitedSave()");
				}
			}else {
				this.getExecuteSG().addExecuteCode("unlimitedSave()");
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}


		return EventRtnType.NORMAL_SUCCESS;
	}


	/**
	 * 人员基本信息保存
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int savePerson(String confirm)throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();


		String a0000 = this.request.getParameter("a0000");
		A01 a01 = null;
		try {
			a01 = (A01)sess.get(A01.class, a0000);

			/* 无锡加 */
			if((Integer.valueOf(a01.getA0190()==null?"0":a01.getA0190()) & Integer.valueOf(a01.getA0189()==null?"0":a01.getA0189())) == 1){
				this.setMainMessage("该人员已被审核,无法修改信息!");
				return EventRtnType.FAILD;
			}

		} catch (Exception e) {
			this.setMainMessage("该人员在系统中不存在");
			return EventRtnType.NORMAL_SUCCESS;
		}



		String a0163 = this.request.getParameter("a0163");

		//对人员工作单位及职务信息进行判断，校验;（默认新增为现职人员）
		//现职人员：必须有一条在任主职务
		//非现职人员：有一条主职务
		//共性：必须要有一条输出职务
		String sqlOne = null;
		String msg = null;

		//注释的原因是不对主职务做校验
//		if(a01.getA0163().equals("1")){					//人员管理状态为1，现职人员
//			/*msg = "现职人员必须有一条在任主职务！";
//			sqlOne = "from A02 where a0000='"+a0000+"' and a0255='1' and a0279='1' order by a0223";//在任、主职务的职务
//*/
//			msg = "必须有一条主职务！";
//			sqlOne = "from A02 where a0000='"+a0000+"' and a0279='1' order by a0223";//在任、主职务的职务
//		}else{			//非现职人员
//			//msg = "非现职人员必须有一条主职务！";
//
//			msg = "必须有一条主职务！";
//			sqlOne = "from A02 where a0000='"+a0000+"' and a0279='1' order by a0223";//主职务的职务
//		}


		String sqlOut = "from A02 where a0000='"+a0000+"' and a0281='true'";   //输出职务
		List<A02> listOut = sess.createQuery(sqlOut).list();
		if(listOut.size() == 0){
			this.setMainMessage("必须要有一条输出职务");
			return EventRtnType.FAILD;
		}

		//不对主职务做校验
//		List<A02> list = sess.createQuery(sqlOne).list();
//		if(list.size() == 0){
//			this.setMainMessage(msg);
//			return EventRtnType.FAILD;
//		}


		//参加工作时间不能小于出生年月
		String a0134_1 = this.request.getParameter("a0134");//参加工作时间
		String a0107_1 = this.request.getParameter("a0107");//出生年月
		String a0134 = a0134_1;
		String a0107 = a0107_1;
		if(a0134_1!=null&&!"".equals(a0134_1)&&a0107_1!=null&&!"".equals(a0107_1)){
			if(a0134_1.length()==6){
				//a0134_1 += "01";
				a0134 += "01";
			}
			if(a0107_1.length()==6){
				//a0107_1 += "01";
				a0107 += "01";
			}
//			if(a0134_1.compareTo(a0107_1)<=0){
//				this.setMainMessage("参加工作时间不能小于等于出生年月");
//				return EventRtnType.FAILD;
//			}
			if(a0134.compareTo(a0107)<=0){
				this.setMainMessage("参加工作时间不能小于等于出生年月");
				return EventRtnType.FAILD;
			}
		}

		//String zcsj = this.request.getParameter("zcsj");        //正处时间
		//String fcsj = this.request.getParameter("fcsj");        //副处时间
		String a2949 = this.request.getParameter("a2949");		//公务员登记时间
		//公务员登记 时间不可早于200604
		/*if(a2949 != null && a2949.length() > 5){
			String year = a2949.substring(0, 6);
			if(200604 > Integer.parseInt(year)){
				this.setMainMessage("公务员登记时间不可早于200604！");
				return EventRtnType.FAILD;
			}
		}*/


		//专长a0187a
		String a0187a = this.request.getParameter("a0187a");
		if(a0187a != null || "".equals(a0187a)){
			if(a0187a.length() > 60){
				this.setMainMessage("专长不能超过60字！");
				return EventRtnType.FAILD;
			}
		}
		String a0141 = this.request.getParameter("a0141");
		String a3921 = this.request.getParameter("a3921");
		String a3927 = this.request.getParameter("a3927");
		String a0144 = this.request.getParameter("a0144");
		String a0192 = this.request.getParameter("a0192");
		String a0101 = this.request.getParameter("a0101");
		String a0117 = this.request.getParameter("a0117");
		String a0111 = this.request.getParameter("a0111");
		String comboxArea_a0111 = this.request.getParameter("comboxArea_a0111");
		String a0114 = this.request.getParameter("a0114");
		String comboxArea_a0114 = this.request.getParameter("comboxArea_a0114");
		String a0140 = this.request.getParameter("a0140");
		String a0196 = this.request.getParameter("a0196");
		String qrzxl = this.request.getParameter("qrzxl");
		String qrzxlxx = this.request.getParameter("qrzxlxx");
		String qrzxw = this.request.getParameter("qrzxw");

		String a0148 =  this.request.getParameter("a0148");		//最高职务层次
		String a0221 = this.request.getParameter("a0221");		//现职务层次
		String a0192e = this.request.getParameter("a0192e");	//现职级
		String a1701 = this.request.getParameter("a1701");		//简历
		String a0192f=this.request.getParameter("a0192f");		//任相当职务职级层次时间

		String a0115a = this.request.getParameter("a0115a");		//成长地
		String a0122 = this.request.getParameter("a0122");		//专业技术类公务员任职资格
		String a0120 = this.request.getParameter("a0120");		//级别

		/*//根据贵州客户要求，需详细记录所有信息日志。
		A01Oplog log = new A01Oplog();
		try {
			PropertyUtils.copyProperties(log, a01);
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InvocationTargetException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (NoSuchMethodException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/


		A01 a01_old = new A01();
		try {
			a01_old = a01.clone();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		a01.setA0141(a0141);//政治面貌
		a01.setA3921(a3921);//第二党派
		a01.setA3927(a3927);//第三党派
		a01.setA0144(a0144);//入党时间
		a01.setA0192(a0192);//现工作单位及职务简称
		a01.setA0163(a0163);//人员管理状态
		a01.setA0101(a0101);//姓名
		a01.setA0107(a0107);//出生年月
		a01.setA0117(a0117);//民族
		a01.setA0111(a0111);//籍贯
		a01.setComboxArea_a0111(comboxArea_a0111);//籍贯
		a01.setA0114(a0114);//出生地
		a01.setComboxArea_a0114(comboxArea_a0114);//出生地
		a01.setA0140(a0140);//入党时间
		a01.setA0134(a0134);//参加工作时间
		a01.setA0196(a0196);//专业技术职务
		a01.setA0187a(a0187a);//熟悉专业有何特长
		a01.setQrzxl(qrzxl);//全日制教育：学历
		a01.setQrzxlxx(qrzxlxx);//院校系及专业(学历)
		a01.setQrzxw(qrzxw);//全日制教育：学位
		String qrzxwxx = this.request.getParameter("qrzxwxx");
		String zzxl = this.request.getParameter("zzxl");
		String zzxlxx = this.request.getParameter("zzxlxx");
		String zzxw = this.request.getParameter("zzxw");
		String zzxwxx = this.request.getParameter("zzxwxx");
		String a0192a = this.request.getParameter("a0192a");
		String a14z101 = this.request.getParameter("a14z101");
		String a15z101 = this.request.getParameter("a15z101");
		String a0195 = this.request.getParameter("a0195");
		String a0184 = this.request.getParameter("a0184");
		String a0160 = this.request.getParameter("a0160");
		String a0192c = this.request.getParameter("a0192c");
		String a0288 = this.request.getParameter("a0288");

		a01.setQrzxwxx(qrzxwxx);//院校系及专业(学位)
		a01.setZzxl(zzxl);//在职教育：学历
		a01.setZzxlxx(zzxlxx);//院校系及专业(学历)
		a01.setZzxw(zzxw);//在职教育：学位
		a01.setZzxwxx(zzxwxx);//院校系及专业(学位)
		a01.setA0192a(a0192a);//工作单位及职务
		a01.setA14z101(a14z101);//奖惩情况
		a01.setA15z101(a15z101);//年度考核结果综述
		a01.setA0195(a0195);//统计关系所在单位
		a01.setA0184(a0184);//公民身份证号码
		a01.setA0160(a0160);//人员类别
		a01.setA0288(a0288);//任现职务层次时间

		/*
		 * //根据贵州客户要求，如果任现职级时间为空，则将人现职务层次时间填入a0192c中
		 * if(a0192c==null||"".equals(a0192c)){ a01.setA0192c(a0288);//任现职级时间 }else{
		 * a01.setA0192c(a0192c);//任现职级时间 }
		 */

		a01.setA0148(a0148); 		//最高职务层次
		a01.setA0221(a0221); 		//现职务层次
		a01.setA0192e(a0192e); 		//现职级
		a01.setA1701(a1701);        //简历
		a01.setA0192f(a0192f); 		//任相当层次职务职级时间

		a01.setA0115a(a0115a);	//成长地
		a01.setA0122(a0122); 	//专业技术类公务员任职资格
		a01.setA0120(a0120); 	//级别
		//a01.setZcsj(zcsj);      //正处时间
		//a01.setFcsj(fcsj);      //副处时间
		a01.setA2949(a2949); 	//公务员登记时间



		/*Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String time = sdf.format(d);  		*/	//当前系统时间

		Date date = new Date();//获得系统时间.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
        Timestamp dates =Timestamp.valueOf(nowTime);//把时间转换




		//a01.setXgsj(Long.parseLong(time));		    //修改时间

		String a0128 = this.request.getParameter("a0128");
		String a0104 = this.request.getParameter("a0104");
		String a0165 = this.request.getParameter("a0165");
		a01.setA0165(a0165);//管理类别
		a01.setA0128(a0128);//健康情况
		a01.setA0104(a0104);//性别
		String a0121 = this.request.getParameter("a0121");
		a01.setA0121(a0121);//编制类型

		String a0180 = this.request.getParameter("a0180");	//备注
		a01.setA0180(a0180);


		String fkbs = this.request.getParameter("fkbs");
//		a01.setFkbs(fkbs);

		JSONObject jsonbject = null;
		try {


			String idcard = a01.getA0184();//身份证号 重名校验//将身份证的最后一位x转换为大写字符 add by lizs 20161110
			if(idcard!=null){
				idcard = idcard.toUpperCase();
				a01.setA0184(idcard);
			}
			/*String sql = "select count(1) from A01 where  a0000!='"+a0000+"' and a0184='"+idcard+"'";//and a0101='"+a01.getA0101()+"'
			Object c = sess.createSQLQuery(sql).uniqueResult();*/

//			String sql = "select A0101,A0192A from A01 where  a0000!='"+a0000+"' and a0184='"+idcard+"'";
//			List<Object[]>  c = sess.createSQLQuery(sql).list();
//
//			if(c.size() > 0){		//有重复身份证
//
//
//				//拼接重复人员信息
//				String msgCard = "系统中已存在以下相同身份证号码人员,是否保存？<br/>";
//
//				for (int i = 0; i < c.size(); i++) {
//
//					Object[] info = c.get(i);
//					Object a0101msg = info[0];		//姓名
//					Object a0192amsg = info[1];		//工作单位及职务
//
//					msgCard = msgCard + "<br/>" + a0101msg +"      "+ a0192amsg;
//				}
//
//				this.setMainMessage(msgCard);
//				this.setMessageType(EventMessageType.CONFIRM);
////				this.setMainMessage(msgCard);
//
//				//this.getExecuteSG().addExecuteCode("$h.alert('系统提示','任免表信息\n保存成功!',null,220);");
//			}
			try{
				a01.setA0102(new ChineseSpelling().getPYString(a01.getA0101()));//拼音简称

			}catch(Exception e){
				e.printStackTrace();
			}

			//对可能存在空格数据的文本进行去空处理
			a01.setA0101(a0101.replaceAll("\\s*", ""));
			a01.setComboxArea_a0114(comboxArea_a0114.replaceAll("\\s*", ""));
			a01.setA0114(a0114.replaceAll("\\s*", ""));
			a01.setQrzxlxx(qrzxlxx.replaceAll("\\s*", ""));
			a01.setQrzxwxx(qrzxwxx.replaceAll("\\s*", ""));
			a01.setZzxlxx(zzxlxx.replaceAll("\\s*", ""));
			a01.setZzxwxx(zzxwxx.replaceAll("\\s*", ""));


			String logID = "";
			if("4".equals(a01.getStatus())){//如果是临时数据，保存时状态设为现职人员  日志记为新增
				a01.setStatus("1");
				String sql2 = "select t.a0201b from a02 t where t.a0000 = '"+a0000+"'";
				List<String> list2 = sess.createSQLQuery(sql2).list();
				for(int i=0;i<list2.size();i++){
					CreateSysOrgBS.updateB01UpdatedWithZero(list2.get(i));
				}
				//new LogUtil("31", "A01", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A01(), a01)).start();
				applog.createLog("31", "A01", a01.getA0000(), a01.getA0101(), "新增记录", Map2Temp.getLogInfo(new A01(), a01));
			}else{
				//new LogUtil("32", "A01", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a01_old, a01)).start();
				List list=Map2Temp.getLogInfo(a01_old, a01);
				if(list.size()>0) {
					applog.createLog("32", "A01", a01.getA0000(), a01.getA0101(), "修改记录", list);
				}
			}

			/*//根据贵州客户要求，需详细记录所有信息日志。
			if(logID!=null&&!"".equals(logID)){
				log.setOplogid(logID);
				sess.save(log);

				//同时保存A36_OPLOG和EXTRA_TAGS_OPLOG
				sess.createSQLQuery("insert into A36_OPLOG select a36.A0000,a36.A3600,a36.A3601,a36.A3604A,a36.A3607,a36.A3611,a36.A3627,a36.sortid,a36.updated,a36.A3684,a36.A3645,a36.A3617,a36.A0184GZ,a36.A0111GZ,A36.A0115GZ,A36.A0111GZB,A36.A3644,A36.A3604B,A36.A3614,A36.A3621,"
						+ "A36.A3631,A36.A3637,A36.A3641,A36.A3634,A36.A3627B,'"+logID+"',A36.MARK from a36 where a36.a0000 = '"+a0000+"'").executeUpdate();
				sess.createSQLQuery("insert into extra_Tags_OPLOG select e.*,'"+logID+"' from extra_Tags e where e.a0000 = '"+a0000+"'").executeUpdate();
			}*/

			a01.setXgr(user.getId());		//修改人(存id)
			a01.setXgsj(dates.getTime());		    //修改时间
			//简历格式化
			//StringBuffer originaljl = new StringBuffer("");
			//String jianli = formatJL(a01_old.getA1701(),originaljl);
			//jsonbject = objectToJson(a01_old);
			//this.getPageElement("a1701").setValue(jianli);
			sess.update(a01);

			this.getExecuteSG().addExecuteCode(AddZHGBRmbPageModel.setTitle(a01));


			//通过a0000查询出“补充信息集”A99Z1对象
			A99Z1 a99Z1 = new A99Z1();
			String a99z100 = this.request.getParameter("a99Z100");		//id
			String a99z101 = this.request.getParameter("a99z101");		//是否考录

			if((a99z101 != null && a99z101.equals("0")) || (a99z101 != null && a99z101.equals("on")) || (a99z101 != null && a99z101.equals(""))){
				a99z101 = "1";
			}

			String a99z102 = this.request.getParameter("a99z102");		//录用时间
			String a99z103 = this.request.getParameter("a99z103");		//是否选调生
			if((a99z103 != null && a99z103.equals("0")) || (a99z103 != null && a99z103.equals("on")) || (a99z103 != null && a99z103.equals(""))){
				a99z103 = "1";
			}

			String a99z104 = this.request.getParameter("a99z104");		//进入选调生时间
			String a99z191 = this.request.getParameter("a99z191");		//保留待遇
			String a99z195 = this.request.getParameter("a99z195");		//联系方式

			a99Z1.setA0000(a0000);
			a99Z1.setA99Z100(a99z100);
			a99Z1.setA99z101(a99z101);
			a99Z1.setA99z102(a99z102);
			a99Z1.setA99z103(a99z103);
			a99Z1.setA99z104(a99z104);
			a99Z1.setA99z191(a99z191);
			a99Z1.setA99z195(a99z195);


			if(a99Z1.getA99z101() == null || a99Z1.getA99z101().equals("")){		//"是否考录"如果为空，则设置为0：否
				a99Z1.setA99z101("0");
			}
			if(a99Z1.getA99z103() == null || a99Z1.getA99z103().equals("")){		//"是否选调生"如果为空，则设置为0：否
				a99Z1.setA99z103("0");
			}

			//判断录用时间：与出生日期进行比较，一般应大于18周岁。
			//String a0107 = a01.getA0107();//出生年月
			//String a99z102 = a99Z1.getA99z102();//录用时间
			if(a0107!=null&&!"".equals(a0107)&&a99z102!=null&&!"".equals(a99z102)){
				int age = getAgeNew(a99z102,a0107);
				if(age<18){
					this.setMainMessage("录用时间与出生日期进行比较，应大于18周岁！");
					return EventRtnType.FAILD;
				}
			}

			//判断进入选调生时间：与出生日期进行比较，一般应大于18周岁。

			//String a99z104 = a99Z1.getA99z104();//进入选调生时间
			if(a0107!=null&&!"".equals(a0107)&&a99z104!=null&&!"".equals(a99z104)){
				int age = getAgeNew(a99z104,a0107);
				if(age<18){
					this.setMainMessage("进入选调生时间与出生日期进行比较，应大于18周岁！");
					return EventRtnType.FAILD;
				}
			}

			//唐佳乐说要加的三个字段2021年2月26日20:48:58
			//String a99z1301 = this.request.getParameter("a99z1301");
			//String a99z1302 = this.request.getParameter("a99z1302");
			String a99z1303 = this.request.getParameter("a99z1303");
			String a99z1304 = this.request.getParameter("a99z1304");
			//a99Z1.setA99z1301(a99z1301);
			//a99Z1.setA99z1302(a99z1302);
			a99Z1.setA99z1303(a99z1303);
			a99Z1.setA99z1304(a99z1304);

			a99Z1.setA0000(a0000);
			A99Z1 a99Z1_old = null;
			if("".equals(a99Z1.getA99Z100())){
				a99Z1.setA99Z100(null);
				a99Z1_old = new A99Z1();
				//applog.createLog("3531", "A99Z1", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a99Z1_old,a99Z1));

				applog.createLogNew("3A99Z12","选调生、考录信息新增","选调生、考录信息集",a0000,a01.getA0101(),new Map2Temp().getLogInfo(new A99Z1(),a99Z1));
			}else{
				a99Z1_old = (A99Z1)sess.get(A99Z1.class, a99Z1.getA99Z100());
				//applog.createLog("3532", "A53", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a99Z1_old,a99Z1));

				applog.createLogNew("3A99Z12","选调生、考录信息修改","选调生、考录信息集",a0000,a01.getA0101(), new Map2Temp().getLogInfo(a99Z1_old,a99Z1));
			}
			PropertyUtils.copyProperties(a99Z1_old, a99Z1);

			sess.saveOrUpdate(a99Z1_old);
			//this.getPageElement("a99Z100").setValue(a99Z1_old.getA99Z100());
			this.getExecuteSG().addExecuteCode("document.getElementById('a99Z100').value='"+a99Z1_old.getA99Z100()+"'");



			
			/**
			 * ================== 保存标签信息保存 开始 ===========================
			 */
			com.insigma.siis.local.business.slabel.ExtraTags extratags = (com.insigma.siis.local.business.slabel.ExtraTags)sess.get(com.insigma.siis.local.business.slabel.ExtraTags.class, a0000);
		    String tagzybj = this.request.getParameter("tagzybj"); // 专业背景
		    String tagzc = this.request.getParameter("tagzc"); // 职称
		    String jqzy = this.request.getParameter("jqzy"); // 紧缺专业
		    String a0195z = this.request.getParameter("a0195z");//归国华侨
		    jqzy = jqzy != null && "1".equals(jqzy) ? "1" : "0";
		    a0195z = a0195z != null && "1".equals(a0195z) ? "1" : "0";
		  
			String sza0194c = this.request.getParameter("sza0194c");
			
			if( extratags != null ){
				extratags.setA0184(a0184);
				extratags.setTagzybj(tagzybj);
				extratags.setTagzc(tagzc);
				extratags.setJqzy(jqzy);
				extratags.setA0194c(sza0194c);
				extratags.setA0195z(a0195z);
				extratags.setUpdatedate(dates.getTime());
		    }else{
		    	extratags = new com.insigma.siis.local.business.slabel.ExtraTags();
		    	extratags.setA0000(a0000);
		    	extratags.setA0184(a0184);
				extratags.setTagzybj(tagzybj);
				extratags.setTagzc(tagzc);
				extratags.setJqzy(jqzy);
				extratags.setA0194c(sza0194c);
				extratags.setA0195z(a0195z);
		    	extratags.setCreatedate(dates.getTime());
		    	applog.createLogNew("3650","其它标签信息新增","标签信息集",a0000,a01.getA0101(),new Map2Temp().getLogInfo(new ExtraTags(),extratags));
		    }
			sess.saveOrUpdate(extratags);
		    sess.flush();
			
		    /**
			 * ================== 保存标签信息保存 结束 ===========================
			 */
			
			
			
			
			//新增修改保存时父页面设置人员内码参数。更新title
			//this.getExecuteSG().addExecuteCode("radow.doEvent('tabClick','"+a01.getA0000()+"');");

			for(int i=1;i<=10;i++){
				A36 a36 = new A36();
				char ichar = (char)(i+97);
				String a3600 = this.request.getParameter("a3600_"+ichar);				//id
				//String a3604a = this.request.getParameter("a3604a_"+ichar+"Text");		//称谓
				String a3604a = getCodeName("GB4761", this.request.getParameter("a3604a_"+ichar).replaceAll("\r\n", "").trim());		//称谓

				String a3601 = this.request.getParameter("a3601_"+ichar);				//
				String a3607 = this.request.getParameter("a3607_"+ichar);
				//String a3627 = this.request.getParameter("a3627_"+ichar+"Text");			//政治面貌
				String a3627 = this.request.getParameter("a3627_"+ichar);			//政治面貌

				String a3611 = this.request.getParameter("a3611_"+ichar);

				String a3684 = this.request.getParameter("a3684_"+ichar);
				String mark=this.request.getParameter("mark_"+ichar);
				String updated = getSubCodeValue("GB4761",a3604a);

				if((a3604a != null && !a3604a.equals("")) || (a3601 != null && !a3601.equals("")) || (a3607 != null && !a3607.equals(""))
						|| (a3627 != null && !a3627.equals("")) || (a3611 != null && !a3611.equals(""))){

					//信息验证
					if((a3604a == null || "".equals(a3604a)) && (a3601 != null && !"".equals(a3601))){
						this.setMainMessage("家庭成员【"+a3601+"】的称谓不能为空或与标准不符！");
						return EventRtnType.FAILD;
					}
					if((a3601 == null || "".equals(a3601)) && (a3604a != null && !"".equals(a3604a))){
						this.setMainMessage("家庭成员姓名不能为空！");
						return EventRtnType.FAILD;
					}


					/*if(a3627 == null || "".equals(a3627)){
						this.setMainMessage("家庭成员【"+a3601+"】的政治面貌不能为空！");
						return EventRtnType.FAILD;
					}*/
					if(a3611 == null || "".equals(a3611)){
						this.setMainMessage("家庭成员【"+a3601+"】的工作单位及职务不能为空！");
						return EventRtnType.FAILD;
					}

				}

				a36.setA0000(a0000);


				//全部信息去除，则删除
				if((a3604a==null||"".equals(a3604a))&&
						(a3601==null||"".equals(a3601))/* &&
						(a3607==null||"".equals(a3607))&&
						(a3627==null||"".equals(a3627))&&
										(a3611==null||"".equals(a3611)) */){
					//this.getPageElement("a3600_"+i).setValue("");

					if(a3600==null||"".equals(a3600)){

						continue;
					}else{
						this.setPageValue("a3600_"+ichar, "");
						a36.setA3600(a3600);
						//new LogUtil("3363", "A36", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A36(), new A36())).start();
						applog.createLog("3363", "A36", a01.getA0000(), a01.getA0101(), "删除记录", new ArrayList<String[]>());
						sess.delete(a36);
						continue;
					}

				}

				a36.setA3604a(a3604a);
				a36.setA3601(a3601);
				a36.setA3607(a3607);
				a36.setA3627(a3627);
				a36.setA3611(a3611);
				a36.setA0184gz(a3684);
				a36.setMark(mark);
//				a36.setA3684(a3684);
				a36.setUpdated(updated);
				a36.setSortid(BigDecimal.valueOf((long)i));
				if(a3600==null||"".equals(a3600)){
					a36.setA3600(null);
					sess.save(a36);
					sess.flush();
					//new LogUtil("3361", "A36", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A36(), a36)).start();
					applog.createLog("3361", "A36", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A36(), a36));
					this.setPageValue("a3600_"+ichar, a36.getA3600());

				}else{
					a36.setA3600(a3600);
					A36 a36_old = (A36)sess.get(A36.class, a3600);
					//new LogUtil("3362", "A36", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a36_old, a36)).start();
					applog.createLog("3362", "A36", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a36_old, a36));
					PropertyUtils.copyProperties(a36_old, a36);
					sess.update(a36_old);
				}
			}
			sess.flush();

			//标签集
			String a0194c = this.request.getParameter("a0194c");
			ExtraTags tags = (ExtraTags)sess.get(ExtraTags.class, a0000);
			if(tags!=null){
				tags.setA0194c(a0194c);
				sess.update(tags);
			}else{
				tags = new ExtraTags();
				tags.setA0000(a0000);
				tags.setA0194c(a0194c);
				sess.save(tags);
			}
//			String a0196c = this.request.getParameter("a0196c");
//			ExtraTags tags01 = (ExtraTags)sess.get(ExtraTags.class, a0000);
//			if(tags01!=null){
//				tags01.setA0196c(a0196c);
//				sess.update(tags);
//			}else{
//				tags01 = new ExtraTags();
//				tags01.setA0000(a0000);
//				tags01.setA0196c(a0196c);
//				sess.save(tags);
//			}
			
			
			sess.flush();
			//旧值 判断是否修改


			// 家庭成员
			/*String sqla36 = "from A36 where a0000='"+a0000+"' order by SORTID";
			List lista36 = sess.createQuery(sqla36).list();
			Integer rowLength2 = lista36.size();//行数

			//赋值到页面
			this.getPageElement("total").setValue(rowLength2.toString());

			
			
			String json = jsonbject.toString();
			this.getExecuteSG().addExecuteCode("A01value="+json+";A36value="+sb+";");*/
			
			
			
			
			//重新加载家庭成员
			String tabIndex = this.request.getParameter("tabIndex");
//			this.setPageValue("gznx", SV(gznx),"rmbNormalInput");
			//this.getExecuteSG().addExecuteCode("tabSwitch('tabs','page',2);");
			familyMem(a01);
			//this.getExecuteSG().addExecuteCode("tabSwitch('tabs','page',"+tabIndex+");");
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("任免表信息保存失败！");
			return EventRtnType.FAILD;
		}

		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 验证身份证号是否重复
	 **/
	@PageEvent("validateReIDCard")
	@Synchronous(true)
	public int validateReIDCard()throws AppException, RadowException, IOException{
		HBSession sess = HBUtil.getHBSession();
		String userID=this.request.getParameter("a0000");
		String idcard=this.request.getParameter("a0184");
		try {
			String sql="";
			if(userID==""||userID.equals("")||userID==null) {
				 sql = "select A0101,A0192A from A01 where  a0184='"+idcard+"'";
			}else {
				sql="select A0101,A0192A from A01 where a0000!='"+userID+"' and a0184='"+idcard+"'";
			}
		List<Object[]>  c = sess.createSQLQuery(sql).list();
		if(c.size() > 0){		//有重复身份证
			//拼接重复人员信息
			String msgCard = "提示：系统中已存在以下相同身份证号码人员<br/>";
			for (int i = 0; i < c.size(); i++) {
				Object[] info = c.get(i);
				Object a0101msg = info[0];		//姓名
				Object a0192amsg = info[1];		//工作单位及职务
				msgCard = msgCard  +" <br/> "+ a0101msg +"      "+ a0192amsg;
			}
			this.setMainMessage(msgCard);
			this.getExecuteSG().addExecuteCode("reIDCardInfo(false)");
			this.setMessageType(EventMessageType.CONFIRM);
		return EventRtnType.NORMAL_SUCCESS;
		}else {
			this.getExecuteSG().addExecuteCode("reIDCardInfo(true)");
			return EventRtnType.NORMAL_SUCCESS;
		}
		} catch (Exception e) {
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	private void setPageValue(String id, String value){
		value = value.replaceAll("'", "\\\\'");
		this.getExecuteSG().addExecuteCode("document.getElementById('"+id+"').value='"+value+"'");
	}

	private void setPageValue(String id, String value, String type){
		this.setPageValue(id, value);
		if("rmbNormalInput".equals(type)){
			this.getExecuteSG().addExecuteCode(id+"onblur();");
		}else if("rmbSelect".equals(type)){
			this.getExecuteSG().addExecuteCode(id+"setShowValue();");
		}else if("select".equals(type)){
			this.getExecuteSG().addExecuteCode("odin.setSelectValue('"+id+"','"+value+"')");
		}else if("rmbDateInput".equals(type)){
			this.getExecuteSG().addExecuteCode(id+"onSubstrblurEvent();");
		}else if("rmbPopWinInput".equals(type)){
			this.getExecuteSG().addExecuteCode(id+"onblurEvent();");
		}else if("rmbPopWinInput2".equals(type)){
			this.getExecuteSG().addExecuteCode(id+"ShowPopWinValue();");
		}else if("rmbDateInput2".equals(type)){
			this.setPageValue(id+"_1", value);
			this.getExecuteSG().addExecuteCode("rmbblurDate_bj('"+id+"',false,true);");
		}else if("checkbox".equals(type)) {
			if("1".equals(value)) {
				this.getExecuteSG().addExecuteCode("document.getElementById('"+id+"').checked="+true+"");
			}else {
				this.getExecuteSG().addExecuteCode("document.getElementById('"+id+"').checked="+false+"");
			}
		}
	}

	public static String setTitle(A01 a01,String parentScope) throws AppException{
		String setTitle = "";
		//姓名 性别 年龄
		String a0101 = a01.getA0101()==null?"":a01.getA0101();//姓名
		//String a0184 = a01.getA0184().toUpperCase();//身份证号//将身份证的最后一位x转换为大写字符 add by lizs 20161110
		String a0107 = a01.getA0107();//出生日期


		//String sex = "1".equals(a01.getA0104())?"男":"女";
		String sex = "";
		if(a01.getA0104() != null && "1".equals(a01.getA0104())){
			sex = "男";
		}
		if(a01.getA0104() != null && "2".equals(a01.getA0104())){
			sex = "女";
		}


		String age = "";
		int agei = 0;
		/*if(IdCardManageUtil.trueOrFalseIdCard(a0184)){
			age = IdCardManageUtil.getAge(a0184)+"";
		}*/
		if((agei = IdCardManageUtil.getAgefrombirth(a0107))!=-1){
			age = agei + "";
		}
		String title = a0101 + "，" + (sex==null?"":sex) + "，" + age+"岁";

		//将时间戳转为时间
		String date = "";

		if(a01.getXgsj() != null && !a01.getXgsj().equals("")){
			date = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(a01.getXgsj()));
		}


		String xgrName = "";
		String xgrId = a01.getXgr();
		HBSession sess = HBUtil.getHBSession();
		if(xgrId != null && !xgrId.equals("")){

			String sql = "select USERNAME from SMT_USER where  USERID ='"+xgrId+"'";
			Object userName = sess.createSQLQuery(sql).uniqueResult();

			if(userName != null && !userName.equals("")){
				xgrName = userName.toString();
			}
		}

		//拼接修改人，和修改时间
		String xg = "&nbsp;&nbsp;&nbsp;修改人："+(xgrName==null?"":xgrName)+"<br/>修改时间："+(date==null?"":date);
		//AddRmbPageModel.getExecuteSG().addExecuteCode("document.getElementById('a0195').value='';");

		title = title + xg;
		title = title.replaceAll("\r", "").replaceAll("\n", "");
		if(parentScope==null){
			setTitle = "var personInfoObj = document.getElementById('personInfo');if(personInfoObj){"
					+ "personInfoObj.innerHTML='"+title.replaceAll("'", "&acute;")+"';"
							+ "personInfoObj.title='"+title.replaceAll("&nbsp;", " ").replaceAll("<br/>", "")+"';"
					+ "}else{"
					+ "document.getElementById('personInfo').innerHTML='"+title.replaceAll("'", "&acute;")+"';"
							+ "document.getElementById('personInfo').title='"+title.replaceAll("&nbsp;", " ").replaceAll("<br/>", "")+"';"
					+ "}";
		}else{
			setTitle = parentScope+".document.getElementById('personInfo').innerHTML='"+title.replaceAll("<", "&lt;").replaceAll("'", "&acute;")+"';"
					+ parentScope+".document.getElementById('personInfo').title='"+title.replaceAll("&nbsp;", " ")+"';";
		}


		return setTitle;
	}

	public static String setTitle(A01 a01) throws AppException{
		return setTitle(a01, null);
	}

	@PageEvent("printFalse")
	@Synchronous(true)
	@NoRequiredValidate
	public int printFalse()throws AppException, RadowException, IOException{
		String a0000 = this.request.getParameter("a0000");
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null){
			return EventRtnType.FAILD;
		}
		pdfView2(a0000+",false");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 打开PDF预览界面
	 *
	 * @param a0000AndFlag a0000 （人员ID）与 flag（是否打印拟任免信息）拼接的参数，用逗号分隔
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @author mengl
	 * @throws IOException
	 * @date 2016-06-03
	 */
	public int pdfView2(String a0000AndFlag) throws RadowException, AppException, IOException{
		String[] params = a0000AndFlag.split(",");
		String a0000 = params[0]; 										//人员ID
		Boolean flag = params[1].equalsIgnoreCase("true")?true:false;  	//是否打印拟任免信息
		String pdfPath = "";  											//pdf文件路径

		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		list.add(a0000);
		List<String> pdfPaths = null;
		try {
			pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"eebdefc2-4d67-4452-a973-5f7939530a11","pdf",a0000,list2, params);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String newPDFPath = ExpRar.expFile()+UUID.randomUUID().toString().replace("-", "")+".Pdf";
		QueryPersonListBS.mergePdfs(pdfPaths,newPDFPath);
		newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
		newPDFPath = "/hzb"+ newPDFPath;
		this.request.getSession().setAttribute("pdfFilePath", newPDFPath);
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("static-access")
	@PageEvent("deleteFamily")
	@Synchronous(true)
	@Transaction
	@NoRequiredValidate
	public int deleteFamily()throws AppException, RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException{
		String a3600 = this.request.getParameter("a3600");
		A36 a36 = (A36)HBUtil.getHBSession().get(A36.class, a3600);
		if(StringUtil.isEmpty(a3600) || a36==null || StringUtil.isEmpty(a36.getA0000())){
			return EventRtnType.FAILD;
		}

		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a36.getA0000());

		//新增删除家庭成员
		applog.createLog("3363", "A36", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(a36, new A36()));

		String sql = "DELETE FROM A36 WHERE A3600 = '"+a3600+"'";
		HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
		return EventRtnType.NORMAL_SUCCESS;
	}

	private void setRmb(A01 a01){
		this.getExecuteSG().addExecuteCode("tabSwitch('tabs','page',1);");
		this.getExecuteSG().addExecuteCode(" ue.setContent('', false);");

		this.setPageValue("a0000", SV(a01.getA0000()));//id
		this.setPageValue("a0141", SV(a01.getA0141()));//政治面貌
		this.setPageValue("a3921", SV(a01.getA3921()));//第二党派
		this.setPageValue("a3927", SV(a01.getA3927()));//第三党派
		this.setPageValue("a0144", SV(a01.getA0144()));//入党时间
		this.setPageValue("a0192", SV(a01.getA0192()));//现工作单位及职务简称
		this.setPageValue("a0163", SV(a01.getA0163()));//人员管理状态
		//this.getExecuteSG().addExecuteCode("setA0163Text('"+ SV(a01.getA0163())+"')");

		this.setPageValue("a0101", SV(a01.getA0101()),"rmbNormalInput");//姓名
		this.setPageValue("a0107", SV(a01.getA0107()),"rmbDateInput");//出生年月
		this.setPageValue("a0117", SV(a01.getA0117()),"rmbSelect");//民族
		this.setPageValue("a0111", SV(a01.getA0111()));//籍贯
		this.setPageValue("comboxArea_a0111", SV(a01.getComboxArea_a0111()),"rmbPopWinInput");//籍贯
		this.setPageValue("a0114", SV(a01.getA0114()));//出生地
		this.setPageValue("comboxArea_a0114", SV(a01.getComboxArea_a0114()),"rmbPopWinInput");//出生地
		this.setPageValue("a0140", SV(a01.getA0140()),"rmbNormalInput");//入党时间
		this.setPageValue("a0134", SV(a01.getA0134()),"rmbDateInput");//参加工作时间
		this.setPageValue("a0196", SV(a01.getA0196()),"rmbNormalInput");//专业技术职务
		this.setPageValue("a0187a", SV(a01.getA0187a()),"rmbNormalInput");//熟悉专业有何特长
		this.setPageValue("qrzxl", SV(a01.getQrzxl()));//全日制教育：学历
		this.setPageValue("qrzxlxx", SV(a01.getQrzxlxx()));//院校系及专业(学历)
		this.setPageValue("qrzxw", SV(a01.getQrzxw()));//全日制教育：学位
		this.setPageValue("qrzxwxx", SV(a01.getQrzxwxx()));//院校系及专业(学位)
		this.setPageValue("zzxl", SV(a01.getZzxl()));//在职教育：学历
		this.setPageValue("zzxlxx", SV(a01.getZzxlxx()));//院校系及专业(学历)
		this.setPageValue("zzxw", SV(a01.getZzxw()));//在职教育：学位
		this.setPageValue("zzxwxx", SV(a01.getZzxwxx()));//院校系及专业(学位)
		this.setPageValue("a0192a", SV(a01.getA0192a()));//工作单位及职务
		this.setPageValue("a14z101", SV(a01.getA14z101()));//奖惩情况
		this.setPageValue("a15z101", SV(a01.getA15z101()));//年度考核结果综述
		this.setPageValue("a0195", SV(a01.getA0195()));//统计关系所在单位
		String Qrzxlxx = a01.getQrzxlxx();
		String Qrzxwxx = a01.getQrzxwxx();
		if(Qrzxwxx==null) {Qrzxwxx="";};
		if(Qrzxlxx==null) {Qrzxlxx=Qrzxwxx;};
		this.getExecuteSG().addExecuteCode("$('#qrzxlxx_p').html('"+Qrzxlxx+"')");
		this.getExecuteSG().addExecuteCode("$('#qrzxlxx_p').attr('title','"+Qrzxlxx+"')");
		
		
		this.getExecuteSG().addExecuteCode("$('#qrzxwxx_p').html('"+Qrzxwxx+"')");
		this.getExecuteSG().addExecuteCode("$('#qrzxwxx_p').attr('title','"+Qrzxwxx+"')");

		String Zzxlxx = a01.getZzxlxx();
		String Zzxwxx = a01.getZzxwxx();
		if(Zzxwxx==null) {Zzxwxx="";};
		if(Zzxlxx==null) {Zzxlxx=Zzxwxx;};
		this.getExecuteSG().addExecuteCode("$('#zzxlxx_p').html('"+Zzxlxx+"')");
		this.getExecuteSG().addExecuteCode("$('#zzxlxx_p').attr('title','"+Zzxlxx+"')");

		
		this.getExecuteSG().addExecuteCode("$('#zzxwxx_p').html('"+Zzxwxx+"')");
		this.getExecuteSG().addExecuteCode("$('#zzxwxx_p').attr('title','"+Zzxwxx+"')");

		String a0192a = a01.getA0192a();
		if(a0192a==null) {a0192a="";};
		this.getExecuteSG().addExecuteCode("$('#a0192a_p').html('"+a0192a+"')");
		this.getExecuteSG().addExecuteCode("$('#a0192a_p').attr('title','"+a0192a+"')");
		
		if("".equals(SV(a01.getQrzxlxx())) ||"".equals(SV(a01.getQrzxwxx())) || SV(a01.getQrzxlxx()).equals(SV(a01.getQrzxwxx()))) {
			this.getExecuteSG().addExecuteCode("$('#qrzxlxxTD').attr('rowspan','2')");
			this.getExecuteSG().addExecuteCode("$('#qrzxwxxTD').hide()");
		}else {
			this.getExecuteSG().addExecuteCode("$('#qrzxlxxTD').attr('rowspan','1')");
			this.getExecuteSG().addExecuteCode("$('#qrzxwxxTD').show()");
		}
		
		
		if ("".equals(SV(a01.getZzxlxx())) ||"".equals(SV(a01.getZzxwxx()))|| SV(a01.getZzxlxx()).equals(SV(a01.getZzxwxx()))) {
			this.getExecuteSG().addExecuteCode("$('#zzxlxxTD').attr('rowspan','2')");
			this.getExecuteSG().addExecuteCode("$('#zzxwxxTD').hide()");
		}else {
			this.getExecuteSG().addExecuteCode("$('#zzxlxxTD').attr('rowspan','1')");
			this.getExecuteSG().addExecuteCode("$('#zzxwxxTD').show()");
		}   
		
		
		
		try {
			String comboxArea_a0195 = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getA0195()+"'");

			if(comboxArea_a0195 == null || comboxArea_a0195.equals("")){
				comboxArea_a0195 = a01.getA0195();
			}

			this.setPageValue("comboxArea_a0195", SV(comboxArea_a0195));
		} catch (AppException e) {
			e.printStackTrace();
		}//统计关系所在单位
		this.setPageValue("a0184", SV(a01.getA0184()));//公民身份证号码
		this.setPageValue("a0160", SV(a01.getA0160()),"rmbPopWinInput2");//人员类别
		this.setPageValue("a0288", SV(a01.getA0288()),"rmbDateInput2");//任现职务层次时间
		this.setPageValue("a0192c", SV(a01.getA0192c()),"rmbDateInput2");//任现职级时间
		this.setPageValue("a0192f", SV(a01.getA0192f()), "rmbDateInput2");
		//this.setPageValue("a0165", SV(a01.getA0165()));//管理类别//,"rmbSelect"
		this.getExecuteSG().addExecuteCode("odin.setSelectValue('a0165','"+a01.getA0165()+"')");
		this.setPageValue("a0128", SV(a01.getA0128()),"rmbSelect");//健康情况
		this.setPageValue("a0104", SV(a01.getA0104()),"rmbSelect");//性别
		this.setPageValue("a0121", SV(a01.getA0121()),"rmbSelect");//编制类型

		this.setPageValue("a0148", SV(a01.getA0148()));//最高职务层次
		this.setPageValue("a0221", SV(a01.getA0221()));//现职务层次
		this.setPageValue("a0192e", SV(a01.getA0192e()));//现职级
		this.setPageValue("a0180", SV(a01.getA0180()));//备注

		//this.setPageValue("a0115a", SV(a01.getA0115a()),"rmbPopWinInput2");//成长地
		
		//this.setPageValue("a0122", SV(a01.getA0122()),"rmbPopWinInput2");//专业技术类公务员任职资格
		
		//this.setPageValue("a0120", SV(a01.getA0120()),"rmbPopWinInput2");//级别
		
		this.setPageValue("a2949", SV(a01.getA2949()),"rmbDateInput2");//公务员登记时间
		
		//this.setPageValue("zcsj", SV(a01.getZcsj()),"rmbDateInput2");//公务员登记时间
		//this.setPageValue("fcsj", SV(a01.getFcsj()),"rmbDateInput2");//公务员登记时间

		this.getExecuteSG().addExecuteCode("setShowValue('a0221',CodeTypeJson.ZB09);");
		this.getExecuteSG().addExecuteCode("setShowValue('a0192e',CodeTypeJson.ZB148);");
		//照片
		try {
			this.getExecuteSG().addExecuteCode("document.getElementById('personImg').src='"+this.request.getContextPath()+"/servlet/DownloadUserHeadImage?a0000="+URLEncoder.encode(URLEncoder.encode(a01.getA0000(),"UTF-8"),"UTF-8")+"'");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			this.getExecuteSG().addExecuteCode(AddZHGBRmbPageModel.setTitle(a01));
		} catch (AppException e) {
			e.printStackTrace();
		}

		//this.getExecuteSG().addExecuteCode("tabSwitch('tabs','page',2);");
		//家庭成员
		familyMem(a01);
		
		//简历
		this.setPageValue("a1701", SV(a01.getA1701()));//简历

		//String a1701Show = this.formatJL(a01.getA1701(), new StringBuffer());
		//this.setPageValue("a1701Show", SV(a1701Show));//简历

		//补充标签信息集
		String sqlet = "from ExtraTags where a0000='"+a01.getA0000()+"'";
		List listet = HBUtil.getHBSession().createQuery(sqlet).list();

		ExtraTags extraTags = null;
		if(listet==null||listet.size()==0){
			extraTags = new ExtraTags();
		}else{
			extraTags = (ExtraTags) listet.get(0);
		}
		this.setPageValue("a0193z", SV(extraTags.getA0193z()));
		this.setPageValue("a0194z", SV(extraTags.getA0194z()));
		this.setPageValue("a0194c", SV(extraTags.getA0194c()));
//		this.setPageValue("a0196z", SV(extraTags.getA0196z()));
//		this.setPageValue("a0196c", SV(extraTags.getA0196c()));
		this.setPageValue("tagsbjysjlzs", SV(extraTags.getTagsbjysjlzs()));
		this.setPageValue("tagrclxzs", SV(extraTags.getTagrclxzs()));
		this.setPageValue("tagcjlxzs", SV(extraTags.getTagcjlxzs()));


		//补充信息集（A99Z1）
		String sqlaA99Z1 = "from A99Z1 where a0000='"+a01.getA0000()+"'";
		List listA99Z1 = HBUtil.getHBSession().createQuery(sqlaA99Z1).list();

		A99Z1 a99Z1 = null;
		if(listA99Z1==null||listA99Z1.size()==0){
			//a0000 = UUID.randomUUID().toString();
			a99Z1 = new A99Z1();
		}else{
			a99Z1 = (A99Z1) listA99Z1.get(0);
		}

		this.setPageValue("a99Z100", SV(a99Z1.getA99Z100()));//id
		
		//this.setPageValue("a99z101", SV(a99Z1.getA99z101()));//是否考录
		
		//this.setPageValue("a99z102", SV(a99Z1.getA99z102()),"rmbDateInput2");//录用时间
		
		//this.setPageValue("a99z103", SV(a99Z1.getA99z103()));//是否选调生
		
		//this.setPageValue("a99z104", SV(a99Z1.getA99z104()),"rmbDateInput2");//进入选调生时间
		
		this.setPageValue("a99z101F", SV(a99Z1.getA99z101()));//是否考录
		
		this.setPageValue("a99z103F", SV(a99Z1.getA99z103()));//是否选调生
		
		//唐佳乐说要加的三个字段2021年2月26日20:48:58
		//this.setPageValue("a99z1301", SV(a99Z1.getA99z1301()),"select");
		
		//this.setPageValue("a99z1302", SV(a99Z1.getA99z1302()),"select");
		
		this.setPageValue("a99z1303", SV(a99Z1.getA99z1303()));
		this.setPageValue("a99z1304", SV(a99Z1.getA99z1304()));
		/**
		 * ================ 插入标签信息  开始 ===============
		 */
		this.setPageTagInfo(a01.getA0000());	// 加载人员标签信息页数据
		
		//String tabIndex = this.request.getParameter("tabIndex");
		//this.getExecuteSG().addExecuteCode("tabSwitch('tabs','page',"+tabIndex+");");
		//this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('personInfoOP').initialConfig.a0000='"+a01.getA0000()+"'");
		

		this.getExecuteSG().addExecuteCode("setContent();genResume();");

		this.getExecuteSG().addExecuteCode("setTDHeight();");
		
	}

	private void familyMem(A01 a01){
		//家庭成员
		if(a01.getA0000()!=null&&!"".equals(a01.getA0000())){
			String sqla36 = "from A36 where a0000='"+a01.getA0000()+"' order by sortid,"
					+ "case " +
					"        when A3604A='丈夫' or A3604A='妻子' then 1 " +
					"        when A3604A='儿子' or A3604A='女儿'or A3604A='长女'or A3604A='长子' or A3604A='次女' or A3604A='次子' or A3604A='三女' or A3604A='三子' or A3604A='四女' or A3604A='四子' or A3604A='五女' or A3604A='五子' or A3604A='其他女儿' or A3604A='其他子' then 2 " +
					"        when A3604A='父亲'  then 3 " +
					"        when A3604A='母亲'  then 4 " +
					"        when A3604A='继父'  then 5 " +
					"        when A3604A='继母'  then 6 " +
					"      end   , " +
		 			" case "+
		            "    when "+
		            "      A3604A='儿子' or A3604A='女儿' or A3604A='长女'  or "+
		            "      A3604A='长子' or A3604A='次女' or A3604A='次子'  or "+
		            "      A3604A='三女' or A3604A='三子' or A3604A='四女'  or "+
		            "      A3604A='四子' or A3604A='五女' or A3604A='五子'  or "+
		            "      A3604A='其他女儿' or A3604A='其他子' "+
			        "    then   "+
			        "      to_number(GETAGE(A3607)) "+
		            "    end"+
		            "        desc";
			List lista36 = HBUtil.getHBSession().createQuery(sqla36).list();
			int lista36Length = lista36.size();
			//家庭成员
			for(Integer i=1;i<=10;i++){
					A36 a36 = new A36();
					if(i<=lista36Length){
						a36 = (A36)lista36.get(i-1);
					}
					int ichar = i+97;
				String a3604a_i = "a3604a_"+(char)ichar;
				String a3601_i = "a3601_"+(char)ichar;
				String a3607_iF = "a3607_"+(char)ichar+"F";
				String a3607_i = "a3607_"+(char)ichar;
				String a3627_i = "a3627_"+(char)ichar;
				String a3611_i = "a3611_"+(char)ichar;
				String a3600_i = "a3600_"+(char)ichar;
				String a3684_i = "a3684_"+(char)ichar;
				String mark_i = "mark_"+(char)ichar;
				
				this.setPageValue(a3604a_i, SV(a36.getA3604a()),"rmbSelect");//称谓
				
				this.setPageValue(a3601_i, SV(a36.getA3601()),"rmbNormalInput");//姓名
				
				this.setPageValue(a3607_i, SV(a36.getA3607()),"rmbDateInput");//出生年月
				this.setPageValue(a3627_i, SV(a36.getA3627()),"rmbSelect");//政治面貌
				this.setPageValue(a3611_i, SV(a36.getA3611()),"rmbNormalInput");//工作单位及职务
				this.setPageValue(a3600_i, SV(a36.getA3600()));//id
				//this.setPageValue(a3684_i, SV(a36.getA0184gz()),"rmbNormalInput");//身份证号
				
				//this.setPageValue(mark_i, SV(a36.getMark()),"checkbox");//重要标识
			}

		}
	}



	private String SV(String v){
		return v==null?"":v;
	}
	private String SVNEW(Object v){
		return v==null?"":(("0".equals(v.toString())) ||  ("0.0".equals(v.toString()) || "0.00".equals(v.toString())) ?"":v.toString());
	}


	/**
	 * 生成简历
	 */
	@PageEvent("genResume")
	@NoRequiredValidate
	public int genResume() throws RadowException{
		String a0000 = this.request.getParameter("a0000");

		//自动生成简历
		try {
			HBSession sess = HBUtil.getHBSession();
			String sqlA02 = "from A02 where a0000='"+a0000+"' order by a0223";
			List<A02> listA02 = sess.createQuery(sqlA02).list();
			Collections.sort(listA02, new Comparator<A02>(){
				@Override
				public int compare(A02 o1, A02 o2) {
					String o1sj = o1.getA0243()==null?"":o1.getA0243();//任职时间
					String o2sj = o2.getA0243()==null?"":o2.getA0243();//任职时间
					if("".equals(o1sj)){
						return -1;
					}else if("".equals(o2sj)){
						return 1;
					}else{
						if(o1sj.length()>=6){
							String d1 = o1sj.substring(0,6);
							String d2 = o2sj.substring(0,6);
							return d1.compareTo(d2);
						}

					}
					return 0;
				}
			});
			StringBuffer sb = new StringBuffer("");
			if(listA02!=null&&listA02.size()>0){

				for(int i=0;i<listA02.size();i++){
					A02 a02 = listA02.get(i);
					A02 a02Next = new A02();
					if(listA02.size()>i+1){
						a02Next = listA02.get(i+1);
					}
					String a0201a = a02.getA0201a()==null?"":a02.getA0201a();//任职机构
					//String a03015 = a02.getA0216a()==null?"":a02.getA0216a();//职务名称
					String a03015 = a02.getA0215a()==null?"":a02.getA0215a();	//职务名称

					String a0203 = a02.getA0243()==null?"":a02.getA0243();//任职时间
					String a0255 = a02.getA0255()==null?"":a02.getA0255();//任职状态
					String a0265 = a02.getA0265()==null?"":a02.getA0265();//免职时间

					B01 b01 = null;
					if(a02.getA0201b()!=null){
						b01 = (B01)sess.get(B01.class, a02.getA0201b());
					}

					if(b01 != null){//机构拼接规则 与 工作单位与职务全称 机构 拼接一致
						String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
						if("2".equals(b0194)){//2—内设机构
							while(true){
								b01 = (B01)sess.get(B01.class, b01.getB0121());
								if(b01==null){
									break;
								}else{
									b0194 = b01.getB0194();
									if("2".equals(b0194)){//2—内设机构
										a0201a = b01.getB0101()+a0201a;
									}else if("3".equals(b0194)){//3—机构分组
										continue;
									}else if("1".equals(b0194)){//1—法人单位
										a0201a = b01.getB0101()+a0201a;
										break;
									}else{
										break;
									}
								}
							}
						}
					}



					String a0203Next = null;//a02Next.getA0243()==null?"":a02Next.getA0243();//任职时间
					if("1".equals(a0255)){//在任
						a0203Next = "";
					}else{
						a0203Next = a0265;
					}
					if("".equals(a0203)){
						sb.append("       ");
					}else{
						if(a0203.length()>=6){
							String year = a0203.substring(0,4);
							String month = a0203.substring(4,6);
							sb.append(year+"."+month);
						}
					}
					sb.append("--");
					if("".equals(a0203Next)){
						sb.append("       ");
					}else{
						if(a0203Next.length()>=6){
							String year = a0203Next.substring(0,4);
							String month = a0203Next.substring(4,6);
							sb.append(year+"."+month);
						}
					}
					sb.append("  "+a0201a+a03015+"\n");
				}
			}
			//String a1701FirstValue = (String)this.request.getSession().getAttribute("a1701FirstValue");
			this.getExecuteSG().addExecuteCode("document.getElementById('contenttext2').value='"+sb.toString().replaceAll("'", "\\\\'")+"'");
			//this.getExecuteSG().addExecuteCode("document.getElementById('contenttext').value='"+a1701FirstValue+"'+'\r\n'+'"+sb.toString().replaceAll("'", "\\\\'")+"'");
			//this.getExecuteSG().addExecuteCode("document.getElementById('a1701').value ='"+a1701FirstValue+"'+'\r\n'+'"+sb.toString().replaceAll("'", "\\\\'")+"'");
			//this.getExecuteSG().addExecuteCode("setContent()");
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}



	/**
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("resume")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int resume(String confirm)throws RadowException, AppException{

		String a1701 = this.request.getParameter("a1701");

		//String a1701Show = this.formatJL(a1701,new StringBuffer());

		//this.getExecuteSG().addExecuteCode("document.getElementById('a1701Show').value='"+a1701Show+"'");
		//this.setPageValue("a1701Show", SV(a1701Show));//简历

		return EventRtnType.NORMAL_SUCCESS;
	}



	public static String formatJL(String a1701,StringBuffer originaljl) {


		if(a1701!=null&&!"".equals(a1701)){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");
		        Matcher matcher = pattern.matcher(jl);
		        if (matcher.find()) {
		        	String line1 = matcher.group(0);
		        	int index = jl.indexOf(line1);
		        	if(index==0){//以日期开头  (一段)
		        		jlsb.append(line1).append("  ");
		        		String line2 = jl.substring(line1.length()).trim();
			        	parseJL(line2, jlsb,true);
			        	//originaljl.append(jl);
			        	originaljl.append(jl).append("\r\n");//一段简历结束拼上回车
		        	}else{
		        		parseJL(jl, jlsb,false);
		        		if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
			        		originaljl.delete(originaljl.length()-2, originaljl.length());
			        	}
		        		originaljl.append(jl).append("\r\n");
		        	}
		        }else{
		        	parseJL(jl, jlsb,false);
		        	if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
		        		originaljl.delete(originaljl.length()-2, originaljl.length());
		        	}
		        	originaljl.append(jl).append("\r\n");
		        }
			}
			if(jlsb.lastIndexOf("\r\n")==jlsb.length()-2 ){
				jlsb.delete(jlsb.length()-2, jlsb.length());
        	}
			return jlsb.toString();

		}
		return a1701;
	}


	private static void parseJL(String line2, StringBuffer jlsb, boolean isStart){
		int llength = line2.length();//总长
		//25个字一行。
		int oneline = 21;
    	if(llength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//不足 50个字节往后偏移，直到足够为止。
    		boolean hass = false;
    		while((end+offset)<llength){//25个字一行，换行符分割
    			end = oneline*(j+1);
    			if(end+offset>llength){
    				end = llength-offset;
    			}
    			String l = line2.substring(oneline*j+offset,end+offset);
    			int loffset=0;
    			while(l.getBytes().length<oneline<<1){//25个字一行但不足50个字节 往右移
    				loffset++;
    				if((end+offset+loffset)>llength){//超过总长度 退出循环
    					loffset--;
    					break;
    				}
    				l = line2.substring(oneline*j+offset,end+offset+loffset);
    				if(l.getBytes().length>oneline<<1){//可能会出现一行51个字节，往前退一格。
    					loffset--;
    					l = line2.substring(oneline*j+offset,end+offset+loffset);
    					break;
    				}
    			}
    			offset = offset+loffset;
    			if(isStart&&!hass){
    				//jlsb.append(l);
    				jlsb.append(l).append("\r\n");
    				hass = true;
    			}else{
    				//jlsb.append("                  ").append(l);
    				jlsb.append("                  ").append(l).append("\r\n");
    			}

    			j++;
    		}
    	}else{
    		if(isStart){
    			//jlsb.append(line2);
    			jlsb.append(line2).append("\r\n");
    		}else{
    			//jlsb.append("                  ").append(line2);
    			jlsb.append("                  ").append(line2).append("\r\n");
    		}
    	}
	}



	public int getAgeNew(String entryDate,String birth) {
		int returnAge;

		String birthYear = birth.substring(0, 4);
		String birthMonth = birth.substring(4, 6);
		String birthDay = "";
		if(birth.length()==6){
			birthDay = "01";
		}
		if(birth.length()==8){
			birthDay = birth.substring(6, 8);
		}

		String nowYear = entryDate.substring(0, 4);
		String nowMonth = entryDate.substring(4, 6);
		String nowDay = "";
		if(entryDate.length()==6){
			nowDay = "01";
		}
		if(entryDate.length()==8){
			nowDay = entryDate.substring(6, 8);
		}
		if (Integer.parseInt(nowYear) == Integer.parseInt(birthYear)) {
			returnAge = 0; // 同年返回0岁
		} else {
			int ageDiff = Integer.parseInt(nowYear) - Integer.parseInt(birthYear); // 年只差
			if (ageDiff > 0) {
				if (Integer.parseInt(nowMonth) == Integer.parseInt(birthMonth)) {
					int dayDiff = Integer.parseInt(nowDay) - Integer.parseInt(birthDay);// 日之差
					if (dayDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				} else {
					int monthDiff = Integer.parseInt(nowMonth) - Integer.parseInt(birthMonth);// 月之差
					if (monthDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				}
			} else {
				returnAge = -1;// 出生日期错误 晚于今年
			}

		}
		//String msg = value.toString().substring(0, 6) + "(" + returnAge + "岁)";
		int msg = returnAge ;
		return msg;
	}


	//判断是否修改
	@PageEvent("isChange")
	@NoRequiredValidate
	public int isChange(String confirm) {

		HBSession sess = HBUtil.getHBSession();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();


		String a0000 = this.request.getParameter("a0000");
		A01 a01 = null;
		try {
			a01 = (A01)sess.get(A01.class, a0000);

		} catch (Exception e) {
			this.setMainMessage("该人员在系统中不存在");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String a0163 = this.request.getParameter("a0163");

		//参加工作时间不能小于出生年月
		String a0134_1 = this.request.getParameter("a0134");//参加工作时间
		String a0107_1 = this.request.getParameter("a0107");//出生年月
		String a0134 = a0134_1;
		String a0107 = a0107_1;


		//专长a0187a
		String a0187a = this.request.getParameter("a0187a");

		String a0141 = this.request.getParameter("a0141");
		String a3921 = this.request.getParameter("a3921");
		String a3927 = this.request.getParameter("a3927");
		String a0144 = this.request.getParameter("a0144");
		String a0192 = this.request.getParameter("a0192");
		String a0101 = this.request.getParameter("a0101");
		String a0117 = this.request.getParameter("a0117");
		String a0111 = this.request.getParameter("a0111");
		String comboxArea_a0111 = this.request.getParameter("comboxArea_a0111");
		String a0114 = this.request.getParameter("a0114");
		String comboxArea_a0114 = this.request.getParameter("comboxArea_a0114");
		String a0140 = this.request.getParameter("a0140");
		String a0196 = this.request.getParameter("a0196");
		String qrzxl = this.request.getParameter("qrzxl");
		String qrzxlxx = this.request.getParameter("qrzxlxx");
		String qrzxw = this.request.getParameter("qrzxw");

		String a0148 =  this.request.getParameter("a0148");		//最高职务层次
		String a0221 = this.request.getParameter("a0221");		//现职务层次
		String a0192e = this.request.getParameter("a0192e");	//现职级
		String a1701 = this.request.getParameter("a1701");		//简历

		String a0115a = this.request.getParameter("a0115a");		//成长地
		String a0122 = this.request.getParameter("a0122");		//专业技术类公务员任职资格
		String a0120 = this.request.getParameter("a0120");		//级别
		String a2949 = this.request.getParameter("a2949");		//公务员登记时间

		A01 a01_old = new A01();
		try {
			a01_old = a01.clone();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		a01.setA0141(a0141);//政治面貌
		a01.setA3921(a3921);//第二党派
		a01.setA3927(a3927);//第三党派
		a01.setA0144(a0144);//入党时间
		a01.setA0192(a0192);//现工作单位及职务简称
		a01.setA0163(a0163);//人员管理状态
		a01.setA0101(a0101);//姓名
		a01.setA0107(a0107);//出生年月
		a01.setA0117(a0117);//民族
		a01.setA0111(a0111);//籍贯
		a01.setComboxArea_a0111(comboxArea_a0111);//籍贯
		a01.setA0114(a0114);//出生地
		a01.setComboxArea_a0114(comboxArea_a0114);//出生地
		a01.setA0140(a0140);//入党时间
		a01.setA0134(a0134);//参加工作时间
		a01.setA0196(a0196);//专业技术职务
		a01.setA0187a(a0187a);//熟悉专业有何特长
		a01.setQrzxl(qrzxl);//全日制教育：学历
		a01.setQrzxlxx(qrzxlxx);//院校系及专业(学历)
		a01.setQrzxw(qrzxw);//全日制教育：学位
		String qrzxwxx = this.request.getParameter("qrzxwxx");
		String zzxl = this.request.getParameter("zzxl");
		String zzxlxx = this.request.getParameter("zzxlxx");
		String zzxw = this.request.getParameter("zzxw");
		String zzxwxx = this.request.getParameter("zzxwxx");
		String a0192a = this.request.getParameter("a0192a");
		String a14z101 = this.request.getParameter("a14z101");
		String a15z101 = this.request.getParameter("a15z101");
		String a0195 = this.request.getParameter("a0195");
		String a0184 = this.request.getParameter("a0184");
		String a0160 = this.request.getParameter("a0160");
		String a0192c = this.request.getParameter("a0192c");
		String a0288 = this.request.getParameter("a0288");

		a01.setQrzxwxx(qrzxwxx);//院校系及专业(学位)
		a01.setZzxl(zzxl);//在职教育：学历
		a01.setZzxlxx(zzxlxx);//院校系及专业(学历)
		a01.setZzxw(zzxw);//在职教育：学位
		a01.setZzxwxx(zzxwxx);//院校系及专业(学位)
		a01.setA0192a(a0192a);//工作单位及职务
		a01.setA14z101(a14z101);//奖惩情况
		a01.setA15z101(a15z101);//年度考核结果综述
		a01.setA0195(a0195);//统计关系所在单位
		a01.setA0184(a0184);//公民身份证号码
		a01.setA0160(a0160);//人员类别
		a01.setA0288(a0288);//任现职务层次时间
		a01.setA0192c(a0192c);//任现职级时间
		a01.setA0148(a0148); 		//最高职务层次
		a01.setA0221(a0221); 		//现职务层次
		a01.setA0192e(a0192e); 		//现职级
		//a01.setA1701(a1701);        //简历

		a01.setA0115a(a0115a);	//成长地
		a01.setA0122(a0122); 	//专业技术类公务员任职资格
		a01.setA0120(a0120); 	//级别
		a01.setA2949(a2949); 	//公务员登记时间
		//a01.setXgr(user.getId());		//修改人(存id)
		String a0128 = this.request.getParameter("a0128");
		String a0104 = this.request.getParameter("a0104");
		String a0165 = this.request.getParameter("a0165");
		a01.setA0165(a0165);//管理类别
		a01.setA0128(a0128);//健康情况
		a01.setA0104(a0104);//性别
		String a0121 = this.request.getParameter("a0121");
		a01.setA0121(a0121);//编制类型


		JSONObject jsonbject = null;
		try {


			String idcard = a01.getA0184();//身份证号 重名校验//将身份证的最后一位x转换为大写字符 add by lizs 20161110
			if(idcard!=null){
				idcard = idcard.toUpperCase();
				a01.setA0184(idcard);
			}


			a01.setA0102(new ChineseSpelling().getPYString(a01.getA0101()));//拼音简称

			//对可能存在空格数据的文本进行去空处理
			a01.setA0101(a0101.replaceAll("\\s*", ""));
			a01.setComboxArea_a0114(comboxArea_a0114.replaceAll("\\s*", ""));
			a01.setA0114(a0114.replaceAll("\\s*", ""));
			a01.setQrzxlxx(qrzxlxx.replaceAll("\\s*", ""));
			a01.setQrzxwxx(qrzxwxx.replaceAll("\\s*", ""));
			a01.setZzxlxx(zzxlxx.replaceAll("\\s*", ""));
			a01.setZzxwxx(zzxwxx.replaceAll("\\s*", ""));


			//判断是否有改变
			List<String[]> listww = Map2Temp.getLogInfo(a01,a01_old);

			if(listww.size() == 0){			//没有改变，直接关闭页面

				//简历做特殊处理，去除：/r
				String a1701Old = a01_old.getA1701();

				if(a1701Old == null){
					a1701Old = "";
				}
				if(a1701 == null){
					a1701 = "";
				}
				a1701Old = a1701Old.replaceAll("\r", "");
				a1701 = a1701.replaceAll("\r", "");

				if(a1701Old != null && a1701Old.equals(a1701)){
					this.getExecuteSG().addExecuteCode("gb();");
				}else{
					this.getExecuteSG().addExecuteCode("tishi();");
				}



			}else{				//改变了，弹窗提示

				this.getExecuteSG().addExecuteCode("tishi();");
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("检测失败！");
			return EventRtnType.FAILD;
		}


		return EventRtnType.NORMAL_SUCCESS;
	}



	//上一人，下一人判断是否修改
	@PageEvent("isChangeNext")
	@NoRequiredValidate
	public int isChangeNext(String confirm) {

		HBSession sess = HBUtil.getHBSession();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();


		String a0000 = this.request.getParameter("a0000");
		A01 a01 = null;
		try {
			a01 = (A01)sess.get(A01.class, a0000);

		} catch (Exception e) {
			this.setMainMessage("该人员在系统中不存在");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String a0163 = this.request.getParameter("a0163");

		//参加工作时间不能小于出生年月
		String a0134_1 = this.request.getParameter("a0134");//参加工作时间
		String a0107_1 = this.request.getParameter("a0107");//出生年月
		String a0134 = a0134_1;
		String a0107 = a0107_1;


		//专长a0187a
		String a0187a = this.request.getParameter("a0187a");

		String a0141 = this.request.getParameter("a0141");
		String a3921 = this.request.getParameter("a3921");
		String a3927 = this.request.getParameter("a3927");
		String a0144 = this.request.getParameter("a0144");
		String a0192 = this.request.getParameter("a0192");
		String a0101 = this.request.getParameter("a0101");
		String a0117 = this.request.getParameter("a0117");
		String a0111 = this.request.getParameter("a0111");
		String comboxArea_a0111 = this.request.getParameter("comboxArea_a0111");
		String a0114 = this.request.getParameter("a0114");
		String comboxArea_a0114 = this.request.getParameter("comboxArea_a0114");
		String a0140 = this.request.getParameter("a0140");
		String a0196 = this.request.getParameter("a0196");
		String qrzxl = this.request.getParameter("qrzxl");
		String qrzxlxx = this.request.getParameter("qrzxlxx");
		String qrzxw = this.request.getParameter("qrzxw");

		String a0148 =  this.request.getParameter("a0148");		//最高职务层次
		String a0221 = this.request.getParameter("a0221");		//现职务层次
		String a0192e = this.request.getParameter("a0192e");	//现职级
		String a1701 = this.request.getParameter("a1701");		//简历

		String a0115a = this.request.getParameter("a0115a");		//成长地
		String a0122 = this.request.getParameter("a0122");		//专业技术类公务员任职资格
		String a0120 = this.request.getParameter("a0120");		//级别
		String a2949 = this.request.getParameter("a2949");		//公务员登记时间

		A01 a01_old = new A01();
		try {
			a01_old = a01.clone();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		a01.setA0141(a0141);//政治面貌
		a01.setA3921(a3921);//第二党派
		a01.setA3927(a3927);//第三党派
		a01.setA0144(a0144);//入党时间
		a01.setA0192(a0192);//现工作单位及职务简称
		a01.setA0163(a0163);//人员管理状态
		a01.setA0101(a0101);//姓名
		a01.setA0107(a0107);//出生年月
		a01.setA0117(a0117);//民族
		a01.setA0111(a0111);//籍贯
		a01.setComboxArea_a0111(comboxArea_a0111);//籍贯
		a01.setA0114(a0114);//出生地
		a01.setComboxArea_a0114(comboxArea_a0114);//出生地
		a01.setA0140(a0140);//入党时间
		a01.setA0134(a0134);//参加工作时间
		a01.setA0196(a0196);//专业技术职务
		a01.setA0187a(a0187a);//熟悉专业有何特长
		a01.setQrzxl(qrzxl);//全日制教育：学历
		a01.setQrzxlxx(qrzxlxx);//院校系及专业(学历)
		a01.setQrzxw(qrzxw);//全日制教育：学位
		String qrzxwxx = this.request.getParameter("qrzxwxx");
		String zzxl = this.request.getParameter("zzxl");
		String zzxlxx = this.request.getParameter("zzxlxx");
		String zzxw = this.request.getParameter("zzxw");
		String zzxwxx = this.request.getParameter("zzxwxx");
		String a0192a = this.request.getParameter("a0192a");
		String a14z101 = this.request.getParameter("a14z101");
		String a15z101 = this.request.getParameter("a15z101");
		String a0195 = this.request.getParameter("a0195");
		String a0184 = this.request.getParameter("a0184");
		String a0160 = this.request.getParameter("a0160");
		String a0192c = this.request.getParameter("a0192c");
		String a0288 = this.request.getParameter("a0288");

		a01.setQrzxwxx(qrzxwxx);//院校系及专业(学位)
		a01.setZzxl(zzxl);//在职教育：学历
		a01.setZzxlxx(zzxlxx);//院校系及专业(学历)
		a01.setZzxw(zzxw);//在职教育：学位
		a01.setZzxwxx(zzxwxx);//院校系及专业(学位)
		a01.setA0192a(a0192a);//工作单位及职务
		a01.setA14z101(a14z101);//奖惩情况
		a01.setA15z101(a15z101);//年度考核结果综述
		a01.setA0195(a0195);//统计关系所在单位
		a01.setA0184(a0184);//公民身份证号码
		a01.setA0160(a0160);//人员类别
		a01.setA0288(a0288);//任现职务层次时间
		a01.setA0192c(a0192c);//任现职级时间
		a01.setA0148(a0148); 		//最高职务层次
		a01.setA0221(a0221); 		//现职务层次
		a01.setA0192e(a0192e); 		//现职级
		//a01.setA1701(a1701);        //简历

		a01.setA0115a(a0115a);	//成长地
		a01.setA0122(a0122); 	//专业技术类公务员任职资格
		a01.setA0120(a0120); 	//级别
		a01.setA2949(a2949); 	//公务员登记时间
		//a01.setXgr(user.getId());		//修改人(存id)
		String a0128 = this.request.getParameter("a0128");
		String a0104 = this.request.getParameter("a0104");
		String a0165 = this.request.getParameter("a0165");
		a01.setA0165(a0165);//管理类别
		a01.setA0128(a0128);//健康情况
		a01.setA0104(a0104);//性别
		String a0121 = this.request.getParameter("a0121");
		a01.setA0121(a0121);//编制类型


		JSONObject jsonbject = null;
		try {


			String idcard = a01.getA0184();//身份证号 重名校验//将身份证的最后一位x转换为大写字符 add by lizs 20161110
			if(idcard!=null){
				idcard = idcard.toUpperCase();
				a01.setA0184(idcard);
			}


			a01.setA0102(new ChineseSpelling().getPYString(a01.getA0101()));//拼音简称

			//对可能存在空格数据的文本进行去空处理
			a01.setA0101(a0101.replaceAll("\\s*", ""));
			a01.setComboxArea_a0114(comboxArea_a0114.replaceAll("\\s*", ""));
			a01.setA0114(a0114.replaceAll("\\s*", ""));
			a01.setQrzxlxx(qrzxlxx.replaceAll("\\s*", ""));
			a01.setQrzxwxx(qrzxwxx.replaceAll("\\s*", ""));
			a01.setZzxlxx(zzxlxx.replaceAll("\\s*", ""));
			a01.setZzxwxx(zzxwxx.replaceAll("\\s*", ""));


			//判断是否有改变
			List<String[]> listww = Map2Temp.getLogInfo(a01,a01_old);

			if(listww.size() == 0){			//没有改变，直接关闭页面

				//简历做特殊处理，去除：/r
				String a1701Old = a01_old.getA1701();

				if(a1701Old == null){
					a1701Old = "";
				}
				if(a1701 == null){
					a1701 = "";
				}
				a1701Old = a1701Old.replaceAll("\r", "");
				a1701 = a1701.replaceAll("\r", "");

				if(a1701Old != null && a1701Old.equals(a1701)){
					this.getExecuteSG().addExecuteCode("next();");
				}else{
					this.getExecuteSG().addExecuteCode("tishiNext();");
				}



			}else{				//改变了，弹窗提示

				this.getExecuteSG().addExecuteCode("tishiNext();");
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("检测失败！");
			return EventRtnType.FAILD;
		}


		return EventRtnType.NORMAL_SUCCESS;
	}


	//检查统计关系所在单位是否为“内设机构”
	@PageEvent("a0195Change")
	@NoRequiredValidate
	public int a0195Change(String a0195) throws RadowException, AppException, UnsupportedEncodingException{

		a0195 = this.request.getParameter("a0195");

		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+a0195+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();

		if(B0194 == null){
			B0194 = "";
		}

		if(B0194 != null && B0194.equals("2") || B0194.equals("3")){

			String msg = "不可选择内设机构单位！";
			if(B0194.equals("3")){
				msg = "不可选择机构分组单位！";
			}

			//((Combo)this.getPageElement("a0195")).setValue("");
			this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示','"+msg+"');document.getElementById('a0195').value='';"
					+ "document.getElementById('comboxArea_a0195').value='';");
		}

		return EventRtnType.NORMAL_SUCCESS;
	}
	public String getCodeName(String code_type,String code_value){
		String sql = "select code_name from code_value where code_type='"+code_type+"' and code_value='"+code_value+"'";
		PageQueryData pageQuery = null;
		try {
			pageQuery = this.pageQuery(sql,"SQL", -1, 999);
		} catch (RadowException e) {
			return "";
		}
		List listCode_name= (List) pageQuery.getData();
		if(listCode_name.size()>0) {
			Map map = (Map)listCode_name.get(0);
			return map.get("code_name").toString();
		}else {
			return "";
		}

	}
	public String getSubCodeValue(String code_type,String code_name){
		if(code_name!=null&&code_name!="") {
			String sql = "select sub_code_value from code_value where code_name='"+code_name+"' and code_type='"+code_type+"'";
			HBSession sess = HBUtil.getHBSession();
			String str = sess.createSQLQuery(sql).uniqueResult().toString();
			return str;
		}
		return "";
	}
	//打印任免表
	@PageEvent("prtRmb")
	@NoRequiredValidate
	public int prtRmb(String a0000) throws Exception {
		a0000 = this.request.getParameter("a0000");
		  //打印机的选择判断
        HttpSession session = request.getSession();
        String printer= (String) session.getAttribute("Printer");
        if(((String) session.getAttribute("PrintNum").toString()).equals("")) {
        	this.setMainMessage("请设置打印份数！");
        	return EventRtnType.NORMAL_SUCCESS;
        }
        int printNum =  Integer.parseInt((String) session.getAttribute("PrintNum"));
        if(printer.equals("")) {
        	this.setMainMessage("请先去打印机设置中选择一种打印机！");
        	return EventRtnType.NORMAL_SUCCESS;
        }
        if(printNum<=0) {
        	this.setMainMessage("打印份数必须大于0并且不能为空！");
        	return EventRtnType.NORMAL_SUCCESS;
        }

		String tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";

		List<String> list2 = new ArrayList<String>();
		String temp = a0000.replace("|", "");
		list2.add(temp);
		List<String> result = new ArrayList<String>();
		List<String> wordPaths = null;
		try {
			wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word",a0000,result, null);

			 File f = new File(wordPaths.get(0));
			    if (!f.exists()) {
			      this.setMainMessage("文件不存在！");
			      return EventRtnType.NORMAL_SUCCESS;
			    }
			    File fa[] = f.listFiles();
		for (int p = 1; p <= printNum; p++) {
			for (int i = 0; i < fa.length; i++) {
				File fs = fa[i];
				if (fs.isDirectory()) {
					System.out.println(fs.getName() + " [目录]");
				} else {
					//printFile(wordPaths.get(0) + fs.getName(), printer);
					String path = wordPaths.get(0) + fs.getName();
					String rootPath = PhotosUtil.getRootPath();
					path = path.replace(rootPath, "").replace("\\", "/");
					this.getExecuteSG().addExecuteCode("printStart('"+path+"');");
				}
			}
		}


			} catch (AppException e) {
				e.printStackTrace();
			}

			this.setMainMessage("打印完毕");
			return EventRtnType.NORMAL_SUCCESS;

	}
	//直接保存简历
	@PageEvent("saveJL")
	@NoRequiredValidate
	public int saveJL() throws Exception {
		String a0000 = this.request.getParameter("a0000");
		String a1701 = this.request.getParameter("a1701");		//简历
		if(!StringUtils.isEmpty(a1701)){
			a1701 = a1701.replace("\u2002", " ");
    	}
		HBSession hbSession = HBUtil.getHBSession();
		hbSession.createQuery("update A01 a set a.a1701 = '"+a1701+"' where a.a0000 ='"+a0000+"'").executeUpdate();
		hbSession.flush();
		return EventRtnType.NORMAL_SUCCESS;
	}

		private static TreeNode genNode(Object[] treedata) {
			TreeNode node = new TreeNode();
			node.setId(treedata[0].toString());
			node.setText(treedata[1].toString());
			node.setLink(treedata[3].toString());
			node.setLeaf(true);
			if(treedata[2]!=null)
				node.setParentid(treedata[2].toString());
			node.setOrderno((short)1);

			return node;
		}


	
	@PageEvent("saveB01")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveB01(String confirm)throws RadowException, AppException{
		String colName = this.request.getParameter("colName");
		String b0111 = this.request.getParameter("b0111");	
		String value = this.request.getParameter("value");	
		HBSession hbSession = HBUtil.getHBSession();
		hbSession.createQuery("update B01 set "+colName+" = '"+value+"' where b0111 ='"+b0111+"'").executeUpdate();
		hbSession.flush();
		return EventRtnType.NORMAL_SUCCESS;
	}
		   /*
	* 人员标签信息页数据加载
	*/
	private void setPageTagInfo(String a0000) {
		// 标签信息集（ExtraTags）
		com.insigma.siis.local.business.slabel.ExtraTags extratags = (com.insigma.siis.local.business.slabel.ExtraTags) HBUtil.getHBSession().get(com.insigma.siis.local.business.slabel.ExtraTags.class, a0000);
		if (extratags == null) {
		    extratags = new com.insigma.siis.local.business.slabel.ExtraTags();
		}
		this.setPageValue("sza0193z", SV(extratags.getA0193z())); // 历任重要职务重要经历标志
		this.setPageValue("sza0194z", SV(extratags.getA0194z())); // 熟悉领域
		this.setPageValue("sza0194c", SV(extratags.getA0194c())); // 熟悉领域备注
		
		this.setPageValue("sztagsbjysjlzs", SV(extratags.getTagsbjysjlzs())); // 省部级以上奖励
		this.setPageValue("sztagrclxzs", SV(extratags.getTagrclxzs())); // 人才类型
		this.setPageValue("sztagcjlxzs", SV(extratags.getTagcjlxzs())); // 惩戒类型
		
		this.setPageValue("tagzybj", SV(extratags.getTagzybj()));//专业背景 代码
		this.setPageValue("comboxArea_tagzybj", SVNEW(SysCodeUtil.getCodeName("TAGZYBJ", extratags.getTagzybj())),"rmbPopWinInput");//专业背景描述 描述
		
		this.setPageValue("tagzc", SV(extratags.getTagzc()));//职称 代码
		this.setPageValue("comboxArea_tagzc", SVNEW(SysCodeUtil.getCodeName("TAGZC", extratags.getTagzc())),"rmbPopWinInput");//职称描述 描述		
	
		this.setPageValue("a0195z", SV(extratags.getA0195z()), "rmbSelect");// 职称
		this.setPageValue("jqzy", SV(extratags.getJqzy()), "rmbSelect");// 

		// 考察材料附件
//		HBSession sess = HBUtil.getHBSession();
//		String sqlTagKcclfj2 = "from com.insigma.siis.local.business.slabel.TagKcclfj2 where a0000='" + a0000 + "' order by updatedate desc";
//		List tagKcclfj2List = sess.createQuery(sqlTagKcclfj2).list();
//	
//		TagKcclfj2 tagKcclfj20 = null;
//		TagKcclfj2 tagKcclfj21 = null;
//		if (null == tagKcclfj2List || 0 == tagKcclfj2List.size()) {
//		    tagKcclfj20 = new TagKcclfj2();
//		    tagKcclfj21 = new TagKcclfj2();
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj20_filename').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj20_filesize').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj21_filename').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj21_filesize').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj20id').style.display='none'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj21id').style.display='none'");
//		} else if (1 == tagKcclfj2List.size()) {
//		    tagKcclfj20 = (TagKcclfj2) tagKcclfj2List.get(0);
//		    tagKcclfj21 = new TagKcclfj2();
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj20id').style.display='block'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj20_filename').innerHTML='" + tagKcclfj20.getFilename() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj20_filesize').innerHTML='" + tagKcclfj20.getFilesize() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj21_filename').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj21_filesize').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj21id').style.display='none'");
//		} else if (2 <= tagKcclfj2List.size()) {
//		    tagKcclfj20 = (TagKcclfj2) tagKcclfj2List.get(0);
//		    tagKcclfj21 = (TagKcclfj2) tagKcclfj2List.get(1);
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj20id').style.display='block'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj21id').style.display='block'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj20_filename').innerHTML='" + tagKcclfj20.getFilename() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj20_filesize').innerHTML='" + tagKcclfj20.getFilesize() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj21_filename').innerHTML='" + tagKcclfj21.getFilename() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj21_filesize').innerHTML='" + tagKcclfj21.getFilesize() + "'");
//		} else {
//		    tagKcclfj20 = new TagKcclfj2();
//		    tagKcclfj21 = new TagKcclfj2();
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj20_filename').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj20_filesize').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj21_filename').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj21_filesize').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj20id').style.display='none'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagKcclfj21id').style.display='none'");
//		}
//	
//		// 年度考核登记附件
//		String sqlTagNdkhdjbfj = "from com.insigma.siis.local.business.slabel.TagNdkhdjbfj where a0000='" + a0000 + "' order by updatedate desc";
//		List tagNdkhdjbfjList = sess.createQuery(sqlTagNdkhdjbfj).list();
//	
//		TagNdkhdjbfj tagNdkhdjbfj0 = null;
//		TagNdkhdjbfj tagNdkhdjbfj1 = null;
//		if (null == tagNdkhdjbfjList || 0 == tagNdkhdjbfjList.size()) {
//		    tagNdkhdjbfj0 = new TagNdkhdjbfj();
//		    tagNdkhdjbfj1 = new TagNdkhdjbfj();
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj0_filename').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj0_filesize').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj1_filename').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj1_filesize').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj0id').style.display='none'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj1id').style.display='none'");
//		} else if (1 == tagNdkhdjbfjList.size()) {
//		    tagNdkhdjbfj0 = (TagNdkhdjbfj) tagNdkhdjbfjList.get(0);
//		    tagNdkhdjbfj1 = new TagNdkhdjbfj();
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj0id').style.display='block'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj0_filename').innerHTML='" + tagNdkhdjbfj0.getFilename() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj0_filesize').innerHTML='" + tagNdkhdjbfj0.getFilesize() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj1_filename').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj1_filesize').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj1id').style.display='none'");
//		} else if (2 <= tagNdkhdjbfjList.size()) {
//		    tagNdkhdjbfj0 = (TagNdkhdjbfj) tagNdkhdjbfjList.get(0);
//		    tagNdkhdjbfj1 = (TagNdkhdjbfj) tagNdkhdjbfjList.get(1);
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj0id').style.display='block'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj1id').style.display='block'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj0_filename').innerHTML='" + tagNdkhdjbfj0.getFilename() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj0_filesize').innerHTML='" + tagNdkhdjbfj0.getFilesize() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj1_filename').innerHTML='" + tagNdkhdjbfj1.getFilename() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj1_filesize').innerHTML='" + tagNdkhdjbfj1.getFilesize() + "'");
//		} else {
//		    tagNdkhdjbfj0 = new TagNdkhdjbfj();
//		    tagNdkhdjbfj1 = new TagNdkhdjbfj();
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj0_filename').innerHTML='" + tagNdkhdjbfj0.getFilename() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj0_filesize').innerHTML='" + tagNdkhdjbfj0.getFilesize() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj1_filename').innerHTML='" + tagNdkhdjbfj1.getFilename() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj1_filesize').innerHTML='" + tagNdkhdjbfj1.getFilesize() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj0id').style.display='none'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagNdkhdjbfj1id').style.display='none'");
//		}
//	
//		// 档案专审附件
//		String sqlTagDazsbfj = "from com.insigma.siis.local.business.slabel.TagDazsbfj where a0000='" + a0000 + "' order by updatedate desc";
//		List tagDazsbfjList = sess.createQuery(sqlTagDazsbfj).list();
//	
//		TagDazsbfj tagDazsbfj0 = null;
//		TagDazsbfj tagDazsbfj1 = null;
//		if (null == tagDazsbfjList || 0 == tagDazsbfjList.size()) {
//		    tagDazsbfj0 = new TagDazsbfj();
//		    tagDazsbfj1 = new TagDazsbfj();
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj0_filename').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj0_filesize').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj1_filename').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj1_filesize').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj0id').style.display='none'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj1id').style.display='none'");
//		} else if (1 == tagDazsbfjList.size()) {
//		    tagDazsbfj0 = (TagDazsbfj) tagDazsbfjList.get(0);
//		    tagDazsbfj1 = new TagDazsbfj();
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj0id').style.display='block'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj0_filename').innerHTML='" + tagDazsbfj0.getFilename() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj0_filesize').innerHTML='" + tagDazsbfj0.getFilesize() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj1_filename').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj1_filesize').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj1id').style.display='none'");
//		} else if (2 <= tagDazsbfjList.size()) {
//		    tagDazsbfj0 = (TagDazsbfj) tagDazsbfjList.get(0);
//		    tagDazsbfj1 = (TagDazsbfj) tagDazsbfjList.get(1);
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj0id').style.display='block'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj1id').style.display='block'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj0_filename').innerHTML='" + tagDazsbfj0.getFilename() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj0_filesize').innerHTML='" + tagDazsbfj0.getFilesize() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj1_filename').innerHTML='" + tagDazsbfj1.getFilename() + "'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj1_filesize').innerHTML='" + tagDazsbfj1.getFilesize() + "'");
//		} else {
//		    tagDazsbfj0 = new TagDazsbfj();
//		    tagDazsbfj1 = new TagDazsbfj();
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj0_filename').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj0_filesize').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj1_filename').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj1_filesize').innerHTML=''");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj0id').style.display='none'");
//		    this.getExecuteSG().addExecuteCode("document.getElementById('tagDazsbfj1id').style.display='none';");
//		}
//	    


	}
	
	
	
	//保存审核记录
	@PageEvent("saveAdt")
	@NoRequiredValidate
	public int saveAdt() throws Exception {
		String a0000 = this.request.getParameter("a0000");
		String sza0194z = this.request.getParameter("sza0194z");
		String userid = SysManagerUtils.getUserId();
		HBSession session = HBUtil.getHBSession();
		String sql = "insert into A01_AUDIT(adt00,a0000,adt01,userid,adt02,adt03) values(sys_guid(),?,sysdate,?,?,'熟悉领域')";
		HBUtil.executeUpdate(sql, new Object[]{a0000,userid,sza0194z});
		HBUtil.executeUpdate("update a01 set fsj='1' where a0000=?", new Object[]{a0000});
		this.setMainMessage("审核记录已保存！");
		//this.getExecuteSG().addExecuteCode("");
		return EventRtnType.NORMAL_SUCCESS;
	}
}


