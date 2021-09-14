package com.insigma.siis.local.pagemodel.FamilyMember;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.fr.third.org.apache.poi.hssf.usermodel.HSSFFont;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A36New;
import com.insigma.siis.local.business.entity.A36Z1New;
import com.insigma.siis.local.business.entity.A36_FILE;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.util.AsposeExcelToPdf;
import com.insigma.siis.local.util.DateUtil;
import com.insigma.siis.local.util.StringUtil;
import com.picCut.servlet.SaveLrmFile;

import net.sf.jxls.util.Util;

public class addFamPageModel extends PageModel {

	private static Map<String,HSSFDataValidation> hm = new HashMap();
	private static final String cols = "a01.a0000,a01.a0101,a01.a0192,a01.a0107,a01.a0163 as rzzt,"+ 
	" decode((select count(1) from A36NEW b where (b.a0000 = a01.a0000 and b.a3645 = '0'))+(select count(1) from a36z1new c where (c.a0000 = a01.a0000 and c.a3645 = '0')), 0,1,0) shzt";
	private static String staticSql = "select a3600,a3604a,a3601,a3607,a3627,a3611,a0184gz,"
			+ " (select code_name2 from code_value b where b.code_type = 'ZB01' and b.code_value = a0111gz) a0111gz,"
			+ " (select code_name2 from code_value b where b.code_type = 'ZB01' and b.code_value = a0115gz) a0115gz,"
			+ " (select code_name from code_value b where b.code_type = 'GB2659' and b.code_value = a0111gzb) a0111gzb,"
			+ " (select code_name from code_value b where b.code_type = 'GB3304' and b.code_value = a3621) a3621,"
			+ " (select code_name from code_value b where b.code_type = 'ZB06' and b.code_value = a3631) a3631,"
			+ " (select code_name from code_value b where b.code_type = 'ZB56' and b.code_value = a3641) a3641,sortid from  ";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List famList() {
		List list = new ArrayList();
		list.add("父亲");
		list.add("母亲");
		list.add("继父");
		list.add("养父");
		list.add("继母");
		list.add("养母");
		list.add("丈夫");
		list.add("妻子");
		list.add("儿子");
		list.add("独生子");
		list.add("长子");
		list.add("次子");
		list.add("三子");
		list.add("四子");
		list.add("五子");
		list.add("继子");
		list.add("继女");
		list.add("养子");
		list.add("其他子");
		list.add("女儿");
		list.add("独生女");
		list.add("长女");
		list.add("次女");
		list.add("三女");
		list.add("四女");
		list.add("五女");
		list.add("其他女");
		return list;
	}

	private static Logger log = Logger.getLogger(SaveLrmFile.class);
	private LogUtil applog = new LogUtil();

	@Override
	public int doInit() throws RadowException {
		String userid = SysUtil.getCacheCurrentUser().getId();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		System.out.println(user.getName() + user.getLoginname());
		String sql = "select b0111 from ( select b0111 from competence_userdept where userid = '" + userid
				+ "' order by b0111) where rownum=1";
		List<String> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		String b0111 = list.get(0);
		Date date=new Date();
		String formatDateStr = DateUtil.formatDateStr(date, "yyyy-MM-dd");
		this.getPageElement("sysDate").setValue(formatDateStr);
		this.getPageElement("dept").setValue(b0111);
		this.getPageElement("dept_combo").setValue("");
		this.getPageElement("usertype").setValue(user.getLoginname());
		this.getPageElement("userid").setValue(userid);
		this.setNextEventName("queryByName");
		this.getPageElement("a0111gzb").setValue("156");//默认国籍为中国
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("dosave")
	public int doSave() throws RadowException, SQLException, IntrospectionException, IllegalAccessException,
			InvocationTargetException, AppException {
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("a0000").getValue();
		String a0101 = this.getPageElement("a0101").getValue();
		String a3600 = this.getPageElement("a3600").getValue();
		String udcheck = this.getPageElement("udcheck").getValue();

		A36New a36 = (A36New) sess.get(A36New.class, a3600);
		A36Z1New a36z1 = (A36Z1New) sess.get(A36Z1New.class, a3600);
		List list = famList();
		String a3604a = this.getPageElement("a3604a").getValue();
		List list1 = sess.createSQLQuery(
				"select sub_code_value from code_value where code_type='GB4761' and code_value='" + a3604a + "'")
				.list();
		String updated = (String) list1.get(0);
		A36New a36_old = a36;
		A36Z1New a36z1_old = a36z1;
		if (udcheck.equals("1")) {
			updated = "5";
		}
		if (a3600 == null || "".equals(a3600)) {// 新增
			a36 = new A36New();
			a36z1 = new A36Z1New();

			this.copyElementsValueToObj(a36, this);
			this.copyElementsValueToObj(a36z1, this);
			a36.setA0000(a0000);
			a36z1.setA0000(a0000);
			// a36.setA3600(UUID.randomUUID().toString());
			a36.setA3604b(a3604a);
			a36z1.setA3604b(a3604a);
			String a3627 = HBUtil.getCodeName("GB4762", a36.getA3627b());
			a36.setA3627(a3627);
			a36z1.setA3627(a3627);
			a36.setA3645("0");//0表示未审批,1表示已审批
			a36z1.setA3645("0");//0表示未审批,1表示已审批
			a36.setUpdated(updated);
			a36z1.setUpdated(updated);

			if (list.contains(a3604a)) {
				applog.createLog("3361", "A36New", a0000, a0101, "家庭成员新增",
						new Map2Temp().getLogInfo(new A36New(), a36));
				sess.save(a36);
			} else {
				applog.createLog("3361", "A36Z1New", a0000, a0101, "家庭成员新增",
						new Map2Temp().getLogInfo(new A36Z1New(), a36z1));
				sess.save(a36z1);
			}

		} else {// 修改

			if (a36z1 == null) {
				String a0111gzname = HBUtil.getCodeName("ZB01", a36.getA0111gz());
				String a0111gz = a36.getA0111gz();
				String a0115gzname = HBUtil.getCodeName("ZB01", a36.getA0115gz());
				String a0115gz = a36.getA0115gz();
				String a3631name = HBUtil.getCodeName("ZB06", a36.getA3631());
				String a3631 = a36.getA3631();

				this.copyElementsValueToObj(a36, this);
				String a3627 = HBUtil.getCodeName("GB4762", a36.getA3627b());
				a36.setA3627(a3627);
				a36.setUpdated(updated);// 修改时修改分组序号
				a36.setA3604b(a36.getA3604a());
				a36.setA3645("0");//0表示未审批,1表示已审批
				if (a36.getA0111gz().equals(a0111gzname)) {
					a36.setA0111gz(a0111gz);

				}
				if (a36.getA0115gz().equals(a0115gzname)) {
					a36.setA0115gz(a0115gz);

				}
				if (a36.getA3631().equals(a3631name)) {
					a36.setA3631(a3631);

				}
			} else {
				String a0111gzname = HBUtil.getCodeName("ZB01", a36z1.getA0111gz());
				String a0111gz = a36z1.getA0111gz();
				String a0115gzname = HBUtil.getCodeName("ZB01", a36z1.getA0115gz());
				String a0115gz = a36z1.getA0115gz();
				String a3631name = HBUtil.getCodeName("ZB06", a36z1.getA3631());
				String a3631 = a36z1.getA3631();

				this.copyElementsValueToObj(a36z1, this);
				String a3627 = HBUtil.getCodeName("GB4762", a36z1.getA3627b());
				a36z1.setA3627(a3627);
				a36z1.setA3604b(a36z1.getA3604a());
				a36z1.setUpdated(updated);// 修改时修改分组序号
				a36z1.setA3645("0");//0表示未审批,1表示已审批
				if (a36z1.getA0111gz().equals(a0111gzname)) {
					a36z1.setA0111gz(a0111gz);

				}
				if (a36z1.getA0115gz().equals(a0115gzname)) {
					a36z1.setA0115gz(a0115gz);

				}
				if (a36z1.getA3631().equals(a3631name)) {
					a36z1.setA3631(a3631);

				}

			}

			if (list.contains(a3604a)||a36z1==null) {
				if(a36==null)
				{
					a36 = new A36New();
					this.copyElementsValueToObj(a36,this);
					a36.setA0000(a0000);
				}
				if(a36_old==null)
				{
					a36_old = new A36New();
				}
				applog.createLog("3362", "A36", a0000, a0101, "家庭成员修改", new Map2Temp().getLogInfo(a36_old, a36));
				sess.update(a36);
			} else {
				if(a36z1==null)
				{
					a36z1 = new A36Z1New();
					this.copyElementsValueToObj(a36z1,this);
					a36z1.setA0000(a0000);
				}
				if(a36z1_old==null)
				{
					a36z1_old = new A36Z1New();
				}
				applog.createLog("3362", "A36Z1", a0000, a0101, "家庭成员修改", new Map2Temp().getLogInfo(a36z1_old, a36z1));
				sess.update(a36z1);
			}

		}
		sess.flush();
		this.getExecuteSG().addExecuteCode("radow.doEvent('MGrid.dogridquery')");
		this.getExecuteSG().addExecuteCode("radow.doEvent('worksort')");

		this.setMainMessage("保存成功");
		clearCondition();

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("MGrid.dogridquery") // 家庭成员列表
	public int mgridquery(int start, int limit) throws RadowException, SQLException {
		String a0000 = this.getPageElement("a0000").getValue();
		CallableStatement call = null;
		StringBuffer sql = new StringBuffer();
		HBSession sess = HBUtil.getHBSession();
		String sql1 = "select a3600 from a36new where a0000='" + a0000 + "' union all "
				+ " select a3600 from a36z1new where a0000='" + a0000 + "'";
		List list = sess.createSQLQuery(sql1).list();
		if (list.size() < 1) {
			call = sess.connection().prepareCall("{call PRO_A36_EXCHANGE(?)}");
			call.setString(1, a0000);
			call.execute();
		}

		sql.append(" select * from ");
		sql.append(" (select a3600 ,a3604a,a3601,a3607,a3627,a3645,");
		sql.append("a3611,a0184gz," + sql2("GB2659", "a0111gz") + sql2("ZB01", "a0115gz"));
		sql.append(sql2("GB2659", "a0111gzb") + sql2("GB3304", "a3621"));
		sql.append(sql2("ZB06", "a3631") + sql2("ZB56", "a3641"));
		sql.append(" updated,sortid from a36new where a0000 ='" + a0000 + "'");
		sql.append(" union ");
		sql.append("select a3600 ,a3604a,a3601,a3607,a3627,a3645,");
		sql.append("a3611,a0184gz," + sql2("GB2659", "a0111gz") + sql2("ZB01", "a0115gz"));
		sql.append(sql2("GB2659", "a0111gzb") + sql2("GB3304", "a3621"));
		sql.append(sql2("ZB06", "a3631") + sql2("ZB56", "a3641"));
		sql.append(" updated , sortid from a36z1new where a0000 ='" + a0000 + "' ) ");
		sql.append(" order by sortid asc ");

		this.pageQuery(sql.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	private String sql2(String code_type, String field) {
        if(code_type.equals("ZB01"))
		return " (select code_name2 from code_value b where b.code_type='" + code_type + "' and b.code_value=" + field
				+ " ) " + field + " ,";
        else
        return " (select code_name from code_value b where b.code_type='" + code_type + "' and b.code_value=" + field
    				+ " ) " + field + " ,";
	}

	@PageEvent("perGrid.dogridquery") // 人员信息列表
	public int perdquery(int start, int limit) throws RadowException {

        String sql = this.getPageElement("sql").getValue();
        System.out.println(sql);
		this.pageQuery(sql.toString(), "sql", start, limit);

		return EventRtnType.SPE_SUCCESS;
	}

	@PageEvent("perGrid.rowdbclick") // 人员信息双击事件
	public int perdbclick() throws RadowException, AppException, FileNotFoundException {
		Grid grid1 = (Grid) this.getPageElement("perGrid");
		String a0000 = (String) grid1.getValue("a0000");
		String a0101 = (String) grid1.getValue("a0101");

		if (a0000 != null) {
			this.getPageElement("a0000").setValue(a0000);
			this.getPageElement("a0000s").setValue(a0000);
			this.getPageElement("a0101").setValue(a0101);
			this.getPageElement("sbny").setValue("");
			this.getPageElement("txtarea").setValue("");
			this.getPageElement("filename").setValue("");
			this.getExecuteSG().addExecuteCode("radow.doEvent('MGrid.dogridquery');");
			this.getExecuteSG().addExecuteCode("clearFile()");
		} else {
			throw new AppException("人员信息不再系统中");
		}

		return EventRtnType.NORMAL_SUCCESS;

	}
	@PageEvent("queryFile") // 查询最近的一次的上传记录
    public int queryFile() throws RadowException {
		String a0000 = this.getPageElement("a0000").getValue();
		String sql="select * from a36_file where a0000='"+a0000+"' order by to_number(t_ime) desc";
		HBSession sess=HBUtil.getHBSession();
		List<A36_FILE> list= sess.createSQLQuery(sql).addEntity(A36_FILE.class).list();
		A36_FILE a36file=null;
		if(list.size()<1) {
			return EventRtnType.NORMAL_SUCCESS;
		}else {
			a36file= list.get(0);
		}
		this.getPageElement("sbny").setValue(a36file.getT_ime());
		this.getPageElement("txtarea").setValue(a36file.getFiletext());
		this.getPageElement("filename").setValue(a36file.getFilename());
    	return EventRtnType.NORMAL_SUCCESS;
    }
	/**
	 * 只保存曾任秘书情况
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("saveMS")
	public int saveMS() throws RadowException {
		String a0000 = this.getPageElement("a0000").getValue();
		String t_ime = this.getPageElement("sbny").getValue();
		String filetext = this.getPageElement("txtarea").getValue();
		String sql="select * from a36_file where a0000='"+a0000+"' order by to_number(t_ime) desc";
		HBSession sess=HBUtil.getHBSession();
		List<A36_FILE> list= sess.createSQLQuery(sql).addEntity(A36_FILE.class).list();
		A36_FILE a36file=null;
		if(list.size()<1) {
			a36file = new A36_FILE( a0000, t_ime, filetext);
			sess.save(a36file);
			sess.flush();
		}else {
			a36file= list.get(0);
			a36file.setT_ime(t_ime);
			a36file.setFiletext(filetext);
			sess.update(a36file);
			sess.flush();
		}
		this.setMainMessage("本人现任或曾任省部级及以上领导\r\n" +
				"干部（含已离退休）秘书的情况已保存成功");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("perInfo") // 人员信息
	public int perInfo(String value) throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		A36New a36 = (A36New) sess.get(A36New.class, value);
		A36Z1New a36z1 = (A36Z1New) sess.get(A36Z1New.class, value);

		if (a36 != null) {
			a36.setA3627b(HBUtil.getCodeValue("GB4762", a36.getA3627()));
			a36.setA0111gz(HBUtil.getCodeName("ZB01", a36.getA0111gz()));
			a36.setA0115gz(HBUtil.getCodeName("ZB01", a36.getA0115gz()));
			a36.setA3631(HBUtil.getCodeName("ZB06", a36.getA3631()));

			if (a36.getUpdated().equals("5") || a36.getUpdated() == "5") {
				this.getPageElement("udcheck").setValue("1");
			}else {
				this.getPageElement("udcheck").setValue("0");
			}

			PMPropertyCopyUtil.copyObjValueToElement(a36, this);
		} else {
			a36z1.setA3627b(HBUtil.getCodeValue("GB4762", a36z1.getA3627()));
			a36z1.setA0111gz(HBUtil.getCodeName("ZB01", a36z1.getA0111gz()));
			a36z1.setA0115gz(HBUtil.getCodeName("ZB01", a36z1.getA0115gz()));
			a36z1.setA3631(HBUtil.getCodeName("ZB06", a36z1.getA3631()));
			if (a36z1.getUpdated().equals("5") || a36z1.getUpdated() == "5") {
				this.getPageElement("udcheck").setValue("1");
			}else {
				this.getPageElement("udcheck").setValue("0");
			}
			PMPropertyCopyUtil.copyObjValueToElement(a36z1, this);
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("MGrid.rowdbclick")
	public int mgriddbclick() throws RadowException, AppException {
		Grid grid1 = (Grid) this.getPageElement("MGrid");
		String a3600 = (String) grid1.getValue("a3600");
		perInfo(a3600);

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("doDel") // 家庭成员删除
	public int doDel(String value) throws AppException, RadowException, SQLException, IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		String a0000 = this.getPageElement("a0000").getValue();
		String a0101 = this.getPageElement("a0101").getValue();
		HBUtil.getHBSession().createSQLQuery("delete from a36Z1New where a3600= '" + value + "'").executeUpdate();
		HBUtil.getHBSession().createSQLQuery("delete from a36New where a3600= '" + value + "'").executeUpdate();
		this.getExecuteSG().addExecuteCode("radow.doEvent('MGrid.dogridquery');");
		applog.createLog("3363", "A36", a0000, a0101, "家庭成员删除", new Map2Temp().getLogInfo(new A36(), new A36()));
		this.setMainMessage("删除成功");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("clearCondition")
	@NoRequiredValidate
	public void clearCondition() throws RadowException {// 页面信息清空
		String a0000 = this.getPageElement("a0000").getValue();
		A36 a36 = new A36();
		PMPropertyCopyUtil.copyObjValueToElement(a36, this);
		this.getPageElement("a0111gzb").setValue("156");//默认国籍为中国
		this.getPageElement("a0000").setValue(a0000);
	}

	/**
	 * 
	 * 导出数据前确认
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("exp.onclick")
	public int ExpConfirm() throws RadowException, AppException  {
		HBSession sess = HBUtil.getHBSession();

		List<String> oids = outputlist("perGrid");
		if (oids.size() < 1) {
			String sql = this.getPageElement("sql").getValue();
			sql =sql.replace(cols, "count(1)");
			BigDecimal result = (BigDecimal)sess.createSQLQuery(sql.toString()).uniqueResult();
			this.getExecuteSG().addExecuteCode("ExpConfirm("+result+")");
            
		}else {
			this.getExecuteSG().addExecuteCode("ExpConfirm("+oids.size()+")");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 导出数据
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @throws FileNotFoundException
	 */
	@PageEvent("expGrid")
    public int ExpGrid() throws RadowException, AppException, FileNotFoundException {
		HBSession sess = HBUtil.getHBSession();
		String sql = this.getPageElement("sql").getValue();
		sql =sql.replace(cols, "a01.a0000");
		System.out.println(sql);
		List<String> oids = outputlist("perGrid");
		if (oids.size() < 1) {
			List list1 = sess.createSQLQuery(sql.toString()).list();
			for (int i = 0; i < list1.size(); i++) {
				oids.add((String) list1.get(i));
			}
		}

		doexcel(oids, "export");
		this.getExecuteSG().addExecuteCode("Expexcel();");
		return EventRtnType.NORMAL_SUCCESS;
    }
	// 生成excel
	private void doexcel(List<String> oids, String type) throws FileNotFoundException, RadowException {
		List<File> files = new ArrayList<File>();
		HBSession sess = HBUtil.getHBSession();
		String sqlone = null;
		String sqltwo = null;
		String sqlthere = null;
		String sqlFour = null;
		String sqlfive = null;
		String zipPath = null;
		String path = getPath();
		String mbpath = null;
		for (String a0000 : oids) {

			sqlone = staticSql + " a36new where  a3604a in ('父亲','母亲','继父','养父','继母','养母') "
					+ " and a0000 ='" + a0000 + "'"
					+ " and  updated != 5 "
					+ " order by sortid asc";
			sqltwo = "select a3600,a3604a,a3601,a3607,a3627,a3611,a0184gz,a0111gz,a0115gz,a0111gzb,a3621,a3631,a3641,sortid from ("
					+ staticSql + " a36new where  a3604a in ('丈夫','妻子','公公','婆婆','岳父','岳母')  "
					+ " and a0000 ='" + a0000+ "' "
					+ " and  updated != 5 "
					+ " union " + staticSql + " a36z1new where  a3604a in ('丈夫','妻子','公公','婆婆','岳父','岳母')  "
					+ " and a0000 ='"+ a0000 + "' "
					+ " and  updated != 5 "
					+ ") order by sortid asc";
			sqlthere = staticSql
					+ " a36z1new where a3604a in ('哥哥','嫂子','弟弟','弟媳','姐姐','姐夫','妹妹','妹夫','夫兄','夫弟','夫姐','夫妹','妻兄','妻弟','妻姐','妻妹','表兄弟','表兄','表弟','表妹','表姐','堂妹','表妹夫') "
					+ " and a0000 ='"+ a0000 + "'"
					+ " and  updated != 5 "
					+ " order by sortid asc";

			sqlFour = " select a3600,a3604a,a3601,a3607,a3627,a3611,a0184gz,a0111gz,a0115gz,a0111gzb,a3621,a3631,a3641,sortid from ("
					+ staticSql
					+ " a36new where  a3604a in ('儿子','独生子','长子','次子','三子','四子','五子','女婿','其他子','长女婿','次女婿','三女婿','儿子岳父','长子岳父','次子岳父','三子岳父','儿子岳父' "
					+ " ,'长子岳母','次子岳母','三子岳母','养子','继子','女儿','独生女','长女','次女','三女','四女','五女','养女','儿媳','继女','长子媳','次子媳','三子媳','女儿公公','长女公公','次女公公','三女公公','女儿婆婆','长女婆婆' "
					+ " ,'次女婆婆','三女婆婆','其他女儿')  "
					+ " and a0000 ='" + a0000 + "'"
					+ " and  updated != 5 "
					+ " union " + staticSql
					+ " a36z1new where  a3604a in ('儿子','独生子','长子','次子','三子','四子','五子','女婿','其他子','长女婿','次女婿','三女婿','儿子岳父','儿子岳母','长子岳父','次子岳父','三子岳父','儿子岳父' "
					+ " ,'长子岳母','次子岳母','三子岳母','养子','继子','女儿','独生女','长女','次女','三女','四女','五女','养女','儿媳','继女','长子媳','次子媳','三子媳','女儿公公','长女公公','次女公公','三女公公','女儿婆婆','长女婆婆' "
					+ " ,'次女婆婆','三女婆婆','其他女儿')  "
					+ " and a0000 ='" + a0000 + "'"
					+ " and  updated != 5 "
					+ ") order by sortid asc";
			sqlfive = "select a3600,a3604a,a3601,a3607,a3627,a3611,a0184gz,a0111gz,a0115gz,a0111gzb,a3621,a3631,a3641,sortid from ("
					+ staticSql
					+ " a36z1new where  (updated = 5 or updated is null)   and a0000 ='" + a0000+ "'"
					+ " union "
					+ staticSql
					+ " a36new where  (updated = 5 or updated is null)   and a0000 ='" + a0000+ "'"
					+ ") order by sortid asc";

			FileInputStream fis = null;
			FileOutputStream fos = null;
			FileInputStream is = null;
			A01 a01 = (A01) HBUtil.getHBSession().get(A01.class, a0000);
			String sql_secretary="select * from a36_file where a0000='"+a0000+"' order by to_number(t_ime) desc";
			List<A36_FILE> list_secretary= sess.createSQLQuery(sql_secretary).addEntity(A36_FILE.class).list();
			String secretaryInfo = "";
			if(list_secretary.size()>0) {
				secretaryInfo = list_secretary.get(0).getFiletext();
			}
			
			A36_FILE a36file=null;
			try {
				String sql = "select b0101 from b01 where b0111='" + a01.getA0195() + "'";
				List list = sess.createSQLQuery(sql).list();
				String a = "无机构关联人员";
				if (list.size() != 0) {
					a = (String) list.get(0);
				}
					a = a.replace("/", "、");
					a = a.replace("\\", "、");
					a = replace(a);
					String path1 = path + "家庭成员/" + a + "/";
					File file1 = new File(path1);
					if (!file1.exists() && !file1.isDirectory()) {
						file1.mkdirs();
					}

					String pathdata = path1 + replace(a01.getA0101()) + replace(a01.getA0184()) + ".xls";
					String ss = File.separatorChar + "";
					pathdata = pathdata.replace("\\", ss);
					pathdata = pathdata.replace("/", ss);

					File file2 = new File(pathdata);
					if (!file2.exists() || !file2.isFile()) {
						file2.createNewFile();
					}
					if (type.equals("export")) {

						mbpath = this.request.getSession().getServletContext().getRealPath("/")
								+ "pages\\FamilyMember\\jtxxcjb.xls";
					} else {
						mbpath = this.request.getSession().getServletContext().getRealPath("/")
								+ "pages\\FamilyMember\\jtxxcjb1.xls";
					}

					mbpath = mbpath.replace("\\", ss);
					mbpath = mbpath.replace("/", ss);
					File file = new File(mbpath);
					fis = new FileInputStream(file);
					String file_copy = pathdata;
					fos = new FileOutputStream(file_copy);
					byte[] buf = new byte[1024 * 10];
					int len = -1;
					while ((len = fis.read(buf)) != -1) {
						fos.write(buf, 0, len);
					}
					fis.close();
					fos.close();

					is = new FileInputStream(file_copy);
					HSSFWorkbook workbook = new HSSFWorkbook(is); //
					Font font2 = workbook.createFont();
					font2.setFontName("方正小标宋简体");
					font2.setFontHeightInPoints((short) 12);
					Font font1 = workbook.createFont();
					font1.setFontName("方正小标宋简体");
					font1.setFontHeightInPoints((short) 11);
					Font font0 = workbook.createFont();
					font0.setFontName("楷体_GB2312");
					font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					font0.setFontHeightInPoints((short) 12);

					CellStyle cellstyle = workbook.createCellStyle();
					cellstyle.setAlignment(HorizontalAlignment.CENTER);
					cellstyle.setVerticalAlignment(VerticalAlignment.CENTER);
					cellstyle.setBorderLeft(BorderStyle.THIN);
					cellstyle.setBorderRight(BorderStyle.THIN);
					cellstyle.setBorderTop(BorderStyle.THIN);
					cellstyle.setBorderBottom(BorderStyle.THIN);
					cellstyle.setFont(font2);
					cellstyle.setWrapText(true);

					CellStyle cellstyle_secretary = workbook.createCellStyle();
					cellstyle_secretary.setAlignment(HorizontalAlignment.LEFT);
					cellstyle_secretary.setVerticalAlignment(VerticalAlignment.CENTER);
					cellstyle_secretary.setBorderLeft(BorderStyle.THIN);
					cellstyle_secretary.setBorderRight(BorderStyle.THIN);
					cellstyle_secretary.setBorderTop(BorderStyle.THIN);
					cellstyle_secretary.setBorderBottom(BorderStyle.THIN);
					cellstyle_secretary.setFont(font1);
					cellstyle_secretary.setWrapText(true);				
					 
					CellStyle cellstyleDept = workbook.createCellStyle();
					cellstyleDept.setAlignment(HorizontalAlignment.LEFT);
					cellstyleDept.setVerticalAlignment(VerticalAlignment.CENTER);
					cellstyleDept.setFont(font0);
					cellstyleDept.setBorderBottom(BorderStyle.THIN);
					cellstyleDept.setWrapText(true);
					
					
					HSSFSheet sheet = workbook.getSheetAt(0);
					sheet.setAutobreaks(true);
					int num = 0;
					num = exportData(sheet, workbook, cellstyle, sqlone, 3, a01, 2,"父母");
					
					num = exportData(sheet, workbook, cellstyle, sqltwo, num, a01, 3,"配偶及其父母");
					
					num = exportData(sheet, workbook, cellstyle, sqlthere, num, a01, 3,"本人的兄弟姐妹");
					
					num = exportData(sheet, workbook, cellstyle, sqlFour, num, a01, 4,"子女、子女的配偶及其父母");
					
					num = exportData(sheet, workbook, cellstyle, sqlfive, num, a01, 3,"其他直系和三代以内旁系亲属中现任或曾任厅局级及以上职务，以及移居国（境）外的人员");
					
					//if(num>18) {
						CellRangeAddress region = new CellRangeAddress(num, num+1, 0, 2);
				        sheet.addMergedRegion(region);
				        CellRangeAddress region2 = new CellRangeAddress(num, num+1, 3, 5);
				        sheet.addMergedRegion(region2);
				        CellRangeAddress region3 = new CellRangeAddress(num, num, 7, 12);
				        sheet.addMergedRegion(region3);
				        CellRangeAddress region4 = new CellRangeAddress(num+1, num+1, 7, 8);
				        sheet.addMergedRegion(region4);
				        CellRangeAddress region5 = new CellRangeAddress(num+1, num+1, 9, 10);
				        sheet.addMergedRegion(region5);
				        CellRangeAddress region6 = new CellRangeAddress(num+1, num+1, 11, 12);
				        sheet.addMergedRegion(region6);
				        CellRangeAddress region7 = new CellRangeAddress(num+2, num+2, 0, 12);
				        sheet.addMergedRegion(region7);
					//}
					int endRow=num+2;
					int startRow = num;

					try {
					    sheet.shiftRows(204, 207, -1*(205-endRow),true,true);
					}catch(Exception e) {
						e.printStackTrace();
					}
					endRow=endRow+1;
					for(;endRow<206;endRow++) {
						Row row=sheet.getRow(endRow);
						try {
							sheet.removeRow(row);
						}catch(Exception e) {
							e.printStackTrace();
						}	
					}

					Row row=sheet.getRow(0);
					Cell cell=row.getCell(1);
					cell.setCellType(CellType.STRING);
					cell.setCellValue(a01.getA0184());
					
					Cell cellMj=row.getCell(0);
					cellMj.setCellValue("没有权限");

					Row rowdept=sheet.getRow(1);
					Cell cell1=rowdept.getCell(0);
					cell1.setCellStyle(cellstyleDept);
					cell1.setCellValue("姓名："+a01.getA0101()+"     现工作单位及职务："+a01.getA0192a());
					/*
					 * List<HSSFDataValidation> dataValidations = sheet.getDataValidations(); int t
					 * = 0; for (HSSFDataValidation dataValidation : dataValidations) {
					 * CellRangeAddressList regions = dataValidation.getRegions();
					 * System.out.println(t++); for (int i = 0; i < regions.getSize(); i++) { try {
					 * if(t==16) { System.out.println(); CellRangeAddress address =
					 * regions.getCellRangeAddress(i);
					 * System.out.print(" 第一行："+address.getFirstRow());
					 * System.out.print(" 最後一行："+address.getLastRow());
					 * System.out.print(" 第一列："+address.getFirstColumn());
					 * System.out.print(" 最後一列："+address.getLastColumn());
					 * 
					 * System.out.println("設置成功"); address.setLastRow(num-1);
					 * System.out.print(" 最後一行："+address.getLastRow()); } } catch (Exception e) { //
					 * TODO: handle exception }
					 * 
					 * 
					 * 
					 * }
					 * 
					 * 
					 * }
					 */
					row=sheet.getRow(num);
					cell=row.getCell(0);
					cell.setCellValue("本人现任或曾任省部级及以上领导干部（含已离退休）秘书的情况");
					cell.setCellStyle(cellstyle);
				
					sheet.addValidationData(setValidate(num,num,3,14));
					//清除 该行的有效性 
					sheet.addValidationData(setValidate(num+1,num+1,3,14));
					//清除 该行的有效性 
					cell=row.getCell(3);
					cell.setCellFormula(null);
					cell.setCellValue(secretaryInfo);
					cell.setCellStyle(cellstyle_secretary);
					
					
					CellStyle cellstyle2 = workbook.createCellStyle();
					cellstyle2.setAlignment(HorizontalAlignment.LEFT);
					cellstyle2.setVerticalAlignment(VerticalAlignment.CENTER);
					cellstyle2.setBorderLeft(BorderStyle.THIN);
					cellstyle2.setBorderRight(BorderStyle.THIN);
					cellstyle2.setBorderTop(BorderStyle.THIN);
					cellstyle2.setBorderBottom(BorderStyle.THIN);
					cellstyle2.setFont(font2);
					cellstyle2.setWrapText(true);

					Cell cellIdCard=row.getCell(6);
					cellIdCard.setCellStyle(cellstyle2);
					cellIdCard.setCellValue("身份证号： ");
					Cell cellIdCardValue=row.getCell(7);
					cellIdCardValue.setCellType(CellType.STRING);
					cellIdCardValue.setCellStyle(cellstyle2);
					cellIdCardValue.setCellValue(a01.getA0184());

					Cell cell2=row.getCell(13);
					cell2.setCellFormula(null);
					row.setZeroHeight(false);
					
//					row=sheet.getRow(num+1);
//					cell.setCellStyle(cellstyle);
//					cell=row.getCell(7);
//					cell.setCellValue(a01.getA0184());
//					cell.setCellStyle(cellstyle);
					
					

					cell=row.getCell(6);
					cell.setCellStyle(cellstyle);
					row.setZeroHeight(false);
					
					row.setZeroHeight(false);
					

					row=sheet.getRow(num+1);
					row.setZeroHeight(false);
					cell=row.getCell(1);
					cell.setCellStyle(cellstyle);
					
					Row rowLast=sheet.getRow(num+2);
					rowLast.setZeroHeight(false);
					rowLast.setHeight((short)896.0);
					
					for(int i=num+3;i<4000;i++) {
						Row rowLastHidden=sheet.getRow(i);
						if(rowLastHidden==null) {
							rowLastHidden = sheet.createRow(i);
						}
						rowLastHidden.setZeroHeight(true);
					}
					

					
					fos = new FileOutputStream(file_copy);
					workbook.write(fos);
					fos.flush();
					fos.close();
					workbook.close();
					System.gc();
					// 压缩
					zipPath = path1 + a01.getA0101() + a01.getA0184() + ".xls";
				//}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RadowException(e.getMessage());
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
					if (fos != null) {
						fos.close();
					}
					if (is != null) {
						is.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (1 < oids.size()) {

			zipPath = path + "家庭成员.zip";

			createZip(path + "家庭成员/", zipPath);
		}
		this.getPageElement("zippath").setValue(zipPath);

	}
/**
 * add zepeng 20191031 判断该行前三格是否为空
 * @param row
 * @return
 */
	private boolean chkEmptyRow(Row row) {
		String testcell1 = row.getCell(0).getStringCellValue();
		String testcell2 = row.getCell(1).getStringCellValue();
		String testcell3 = row.getCell(2).getStringCellValue();
		String testcell4 = row.getCell(2).getStringCellValue();
		String testcell5 = row.getCell(2).getStringCellValue();
		String testcell6 = row.getCell(2).getStringCellValue();
		System.out.println(testcell1+testcell2+testcell3+testcell4+testcell5+testcell6);
		if(StringUtil.isEmpty(testcell1)&&StringUtil.isEmpty(testcell2)&&StringUtil.isEmpty(testcell3)&&StringUtil.isEmpty(testcell4)&&StringUtil.isEmpty(testcell5)&&StringUtil.isEmpty(testcell6)) {
			return true;
		}
		return false;
	}

	public String replace(String pathdata) {
		if(StringUtil.isEmpty(pathdata))return "";
		pathdata = pathdata.replace("\\", "");
		pathdata = pathdata.replace("/", "");
		pathdata = pathdata.replace("?", "");
		pathdata = pathdata.replace("\"", "");
		pathdata = pathdata.replace("*", "");
		pathdata = pathdata.replace(":", "");
		pathdata = pathdata.replace("<", "");
		pathdata = pathdata.replace(">", "");
		pathdata = pathdata.replace("|", "");
		pathdata = pathdata.replace(" ", "");
		pathdata = pathdata.replace("	", "");
		pathdata = pathdata.replaceAll("\n", "");
		pathdata = pathdata.replaceAll("\r", "");

		return pathdata;

	}

	/**
	 * 
	 * 写入数据
	 * 
	 * @param sheet
	 * @param workbook
	 * @param cellstyle
	 * @param abroadids
	 * @throws Exception
	 */
	public int exportData(HSSFSheet sheet, Workbook workbook, CellStyle cellstyle, String sqlone, int num, A01 a01,
			int rel,String title) throws Exception {
		try {
			CommQuery cq = new CommQuery();
			Cell cell = null;
			ExportAsposeBS eabs=new ExportAsposeBS();
			HashMap<String, Object> map = null;
			List<HashMap<String, Object>> list = cq.getListBySQL(sqlone.toString());
			CellRangeAddress region=null;
			Row row = null;
			
			

			Font font1 = workbook.createFont();
			font1.setFontName("方正小标宋简体");
			font1.setFontHeightInPoints((short) 10);

			CellStyle cellstyleTitleExt = workbook.createCellStyle();
			cellstyleTitleExt.setAlignment(HorizontalAlignment.CENTER);
			cellstyleTitleExt.setVerticalAlignment(VerticalAlignment.CENTER);
			cellstyleTitleExt.setBorderLeft(BorderStyle.THIN);
			cellstyleTitleExt.setBorderRight(BorderStyle.THIN);
			cellstyleTitleExt.setBorderTop(BorderStyle.THIN);
			cellstyleTitleExt.setBorderBottom(BorderStyle.THIN);
			cellstyleTitleExt.setFont(font1);
			cellstyleTitleExt.setWrapText(true);
			
			if (list != null) {
				int maxRow=Math.max(list.size(),rel);
				
				for (int i = 0; i < maxRow; i++) {
				System.out.println((num+i)+title);
					if(title.indexOf("三代以内")>-1) {
						addMenu(sheet, num,num+i ,"211:1");
					}else if(title.indexOf("子女")>-1) {
						addMenu(sheet, num,num+i ,"210:1");
					}else if(title.indexOf("兄弟")>-1) {
						addMenu(sheet, num,num+i ,"209:1");
					}else if(title.indexOf("配偶")>-1) {
						addMenu(sheet, num,num+i ,"208:1");
					}else if(title.indexOf("父母")>-1) {
						addMenu(sheet, num,num+i ,"207:1");
					}else {
						return 0;
					}
				}
				
				int j = list.size();
				for (int i = 0; i < maxRow; i++) {
					
					map = null;
					if(list==null||i>=list.size()) {
						map = new HashMap();
						map.put("a3604a","");
						map.put("a3601","");
						map.put("a3607","");
						map.put("a3627","");
						map.put("a3611","");
						map.put("a0184gz","");
						map.put("a0111gz","");
						map.put("a0115gz","");
						map.put("a0111gzb","");
						map.put("a3621","");
						map.put("a3631","");
						map.put("a3641","");
					}else {
						map = list.get(i);
					}
					
					if (rel < i+1) {
						//eabs.copyRows(i+num-2, i+num, i+num, sheet);
						//insertRow(sheet,num,1);
						row = sheet.getRow(i + num);

					} else {
						row = sheet.getRow(i + num);
					}
					row.setZeroHeight(false);
					// row.setHeight((short) (25 * 20));
					int m = 1;

					cell = row.getCell(m++);
					// cell.setCellStyle(cellstyle);
					cell.setCellValue((String) map.get("a3604a"));

					cell = row.getCell(m++);
					// cell.setCellStyle(cellstyle);
					cell.setCellValue((String) map.get("a3601"));

					cell = row.getCell(m++);
					// cell.setCellStyle(cellstyle);
					cell.setCellValue((String) map.get("a3607"));

					cell = row.getCell(m++);
					// cell.setCellStyle(cellstyle);
					cell.setCellValue((String) map.get("a3627"));

					cell = row.getCell(m++);
					// cell.setCellStyle(cellstyle);
					cell.setCellValue((String) map.get("a3611"));

					cell = row.getCell(m++);
					// cell.setCellStyle(cellstyle);
					cell.setCellType(CellType.STRING);
					cell.setCellValue((String) map.get("a0184gz"));

					cell = row.getCell(m++);
					// cell.setCellStyle(cellstyle);
					cell.setCellValue((String) map.get("a0111gz"));

					cell = row.getCell(m++);
					// cell.setCellStyle(cellstyle);
					cell.setCellValue((String) map.get("a0115gz"));

					cell = row.getCell(m++);
					// cell.setCellStyle(cellstyle);
					cell.setCellValue((String) map.get("a0111gzb"));

					cell = row.getCell(m++);
					// cell.setCellStyle(cellstyle);
					cell.setCellValue((String) map.get("a3621"));

					cell = row.getCell(m++);
					// cell.setCellStyle(cellstyle);
					cell.setCellValue((String) map.get("a3631"));

					cell = row.getCell(m++);
					// cell.setCellStyle(cellstyle);
					cell.setCellValue((String) map.get("a3641"));

				}
				row = sheet.getRow(num);
				cell=row.getCell(0);
				if(title.indexOf("三代以内")>-1) {
					cell.setCellStyle(cellstyleTitleExt);
				}else {
					cell.setCellStyle(cellstyle);
				}
				cell.setCellValue(title);
				if(j>rel) {
					region = new CellRangeAddress(num, num+j-1, 0, 0);
					sheet.addMergedRegion(region);
				}else {
					region = new CellRangeAddress(num, num+rel-1, 0, 0);
					sheet.addMergedRegion(region);
				}
				
				
				

				if (rel < list.size()) {
					rel = list.size();
				}

				rel = num + rel;

				

			}else {
				row = sheet.getRow(num);
				cell=row.getCell(0);
				cell.setCellStyle(cellstyle);
				cell.setCellValue(title);
				region = new CellRangeAddress(num, num+rel-1, 0, 0);
				sheet.addMergedRegion(region);
			}

			return rel;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public static HSSFDataValidation setValidate(int beginRow,  
			int beginCol, int endRow, int endCol) {  
	    DVConstraint constraint = DVConstraint.createNumericConstraint(  
	            DVConstraint.ValidationType.TEXT_LENGTH,  
	            DVConstraint.OperatorType.BETWEEN, "1", "20");  
	    // 设定在哪个单元格生效  
	    CellRangeAddressList regions = new CellRangeAddressList(beginRow,  
	            beginCol, endRow, endCol);  
	    // 创建规则对象  
	    HSSFDataValidation ret = new HSSFDataValidation(regions, constraint);  
	    return ret;  
	}  
	
	private void addMenu(HSSFSheet sheet, int num,int lastnum,String key) {
		if(hm.size()==0) {
			String keys = ",207:1,208:1,209:1,210:1,211:1,";
			for(HSSFDataValidation hd:sheet.getDataValidations()) {
				int rowIndex = hd.getRegions().getCellRangeAddress(0).getFirstRow();
				int colIndex = hd.getRegions().getCellRangeAddress(0).getFirstColumn();
				if(keys.indexOf(","+rowIndex+":"+colIndex+",")>-1){
					hm.put(rowIndex+":"+colIndex, hd);
				}
			}
		}		
		CellRangeAddressList regions = new CellRangeAddressList(lastnum, lastnum, 1, 1);
		DVConstraint constraint = hm.get(key).getConstraint();
		constraint.setFormula1(constraint.getFormula1().replaceAll("&[A-Z][0-9]+&", "&B"+(lastnum+1)+"&"));
		System.out.println(constraint.getFormula1()); 
		HSSFDataValidation data_validation_view= new HSSFDataValidation(regions, constraint);
		data_validation_view.setShowErrorBox(false);
		sheet.addValidationData(data_validation_view);
	}

	

	private String getPath() {
		String upload_file = AppConfig.HZB_PATH + "/template/";
		try {
			File file = new File(upload_file);
			//
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String excel = upload_file + UUID.randomUUID().toString() + "/";
		return excel;
	}

	private List<String> outputlist(String gridname) throws RadowException, AppException {

		PageElement pe = this.getPageElement(gridname);
		List<HashMap<String, Object>> list = pe.getValueList();
		List<String> oids = new ArrayList<String>();

		for (int j = 0; j < list.size(); j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("percheck");
			if (check1 != null && check1.equals(true)) {
				oids.add(map.get("a0000").toString());
			}
		}

		return oids;
	}

	/**
	 * 
	 * 
	 * @param srcFiles
	 * 
	 * @param out
	 * 
	 * @throws RuntimeException
	 * 
	 */
	private static void createZip(String sourcePath, String zipPath) {
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(zipPath);
			zos = new ZipOutputStream(fos);
			zos.setEncoding("gbk");// 此处修改字节码方式.
			writeZip(new File(sourcePath), "", zos);
		} catch (FileNotFoundException e) {
			log.error("创建zip文件失败", e);
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {
				log.error("创建zip文件失败", e);
			}

		}
	}

	private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
		if (file.exists()) {
			if (file.isDirectory()) {
				parentPath += file.getName() + File.separator;
				File[] files = file.listFiles();
				if (files.length != 0) {
					for (File f : files) {
						writeZip(f, parentPath, zos);
					}
				} else {
					try {
						zos.putNextEntry(new ZipEntry(parentPath));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);
					ZipEntry ze = new ZipEntry(parentPath + file.getName());
					zos.putNextEntry(ze);
					byte[] content = new byte[1024];
					int len;
					while ((len = fis.read(content)) != -1) {
						zos.write(content, 0, len);
						zos.flush();
					}
				} catch (FileNotFoundException e) {
					log.error("创建ZIP文件失败", e);
				} catch (IOException e) {
					log.error("创建ZIP文件失败", e);
				} finally {
					try {
						if (fis != null) {
							fis.close();
						}
					} catch (IOException e) {
						log.error("创建ZIP文件失败", e);
					}
				}
			}
		}
	}

	public static PrintService specifyPrinter(String printerName) {
		DocFlavor psInFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
		HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		PrintService printService[] = PrintServiceLookup.lookupPrintServices(psInFormat, pras);
		PrintService myPrinter = null;

		for (PrintService printService2 : printService) {
			String svcName = printService2.toString();
			if (svcName.contains(printerName)) {
				myPrinter = printService2;
				break;
			}
		}
		if (myPrinter == null) {
			myPrinter = PrintServiceLookup.lookupDefaultPrintService();
		}
		return myPrinter;
	}

	/**
	 * 打印
	 * 
	 * @param res
	 * @throws Exception
	 */
	@PageEvent("doPrint.onclick")
	public int doPrint() throws Exception {

		HBSession sess = HBUtil.getHBSession();
		String sql = this.getPageElement("sql").getValue();
		sql =sql.replace(cols, "a01.a0000");
		List<String> oids = outputlist("perGrid");
		if (oids.size() < 1) {
			List list1 = sess.createSQLQuery(sql.toString()).list();
			for (int i = 0; i < list1.size(); i++) {
				oids.add((String) list1.get(i));
			}
		}

		String sqlone = null;
		String sqltwo = null;
		String sqlthere = null;
		String sqlFour = null;
		String sqlfive = null;
		String zipPath = null;
		String path = getPath();
		for (String a0000 : oids) {

			sqlone = staticSql + " a36new where  a3604a in ('父亲','母亲','继父','养父','继母','养母')  and a0000 ='" + a0000
					+ "' order by sortid asc";
			sqltwo = "select * from (" + staticSql
					+ " a36new where  a3604a in ('丈夫','妻子','公公','婆婆','岳父','岳母')  and a0000 ='" + a0000 + "' union "
					+ staticSql + " a36z1new where  a3604a in ('丈夫','妻子','公公','婆婆','岳父','岳母')  and a0000 ='" + a0000
					+ "' ) order by sortid asc";
			sqlthere = staticSql
					+ " a36z1new where a3604a in ('哥哥','嫂子','弟弟','弟媳','姐姐','姐夫','妹妹','妹夫','夫兄','夫弟','夫姐','夫妹','妻兄','妻弟','妻姐','妻妹','表兄弟','表兄','表弟','表妹','表姐','堂妹','表妹夫') and a0000 ='"
					+ a0000 + "' order by sortid asc";

			sqlFour = " select * from (" + staticSql
					+ " a36new where  a3604a in ('儿子','独生子','长子','次子','三子','四子','五子','女婿','其他子','长女婿','次女婿','三女婿','儿子岳父','长子岳父','次子岳父','三子岳父','儿子岳父' "
					+ " ,'长子岳母','次子岳母','三子岳母','养子','继子','女儿','独生女','长女','次女','三女','四女','五女','养女','儿媳','继女','长子媳','次子媳','三子媳','女儿公公','长女公公','次女公公','三女公公','女儿婆婆','长女婆婆' "
					+ " ,'次女婆婆','三女婆婆','其他女儿')  and a0000 ='" + a0000 + "' union " + staticSql
					+ " a36z1new where  a3604a in ('儿子','独生子','长子','次子','三子','四子','五子','女婿','其他子','长女婿','次女婿','三女婿','儿子岳父','儿子岳母','长子岳父','次子岳父','三子岳父','儿子岳父' "
					+ " ,'长子岳母','次子岳母','三子岳母','养子','继子','女儿','独生女','长女','次女','三女','四女','五女','养女','儿媳','继女','长子媳','次子媳','三子媳','女儿公公','长女公公','次女公公','三女公公','女儿婆婆','长女婆婆' "
					+ " ,'次女婆婆','三女婆婆','其他女儿')  and a0000 ='" + a0000 + "') order by sortid asc";
			sqlfive = "select * from (" + staticSql
					+ " a36z1new where  a3604a in  ('孙子','孙女','外孙子','外孙女','祖父','祖母','外祖父','外祖母','曾祖父','曾祖母','其他','伯父','伯母','叔父','婶母','舅父','舅母' "
					+ " ,'姨夫','姨父','姨母','姑父','姑母','侄子','侄女','外甥','外甥女','其他亲属','保姆','非亲属')  and a0000 ='" + a0000
					+ "') order by sortid asc";

			FileInputStream fis = null;
			FileOutputStream fos = null;
			FileInputStream is = null;
			A01 a01 = (A01) HBUtil.getHBSession().get(A01.class, a0000);
			try {

				File file1 = new File(path);
				if (!file1.exists() && !file1.isDirectory()) {
					file1.mkdirs();
				}
				String pathdata = path + a01.getA0101() + "jtxxcjb.xls";
				String ss = File.separatorChar + "";
				pathdata = pathdata.replace("\\", ss);
				pathdata = pathdata.replace("/", ss);
				File file2 = new File(pathdata);
				if (!file2.exists() || !file2.isFile()) {
					file2.createNewFile();
				}

				String mbpath = this.request.getSession().getServletContext().getRealPath("/")
						+ "pages\\FamilyMember\\jtxxcjb.xls";
				mbpath = mbpath.replace("\\", ss);
				mbpath = mbpath.replace("/", ss);
				File file = new File(mbpath);
				fis = new FileInputStream(file);
				String file_copy = pathdata;
				fos = new FileOutputStream(file_copy);
				byte[] buf = new byte[1024 * 10];
				int len = -1;
				while ((len = fis.read(buf)) != -1) {
					fos.write(buf, 0, len);
				}
				fis.close();
				fos.close();

				is = new FileInputStream(file_copy);
				HSSFWorkbook workbook = new HSSFWorkbook(is); //
				Font font2 = workbook.createFont();
				font2.setFontName("方正小标宋简体");
				font2.setFontHeightInPoints((short) 8);

				CellStyle cellstyle = workbook.createCellStyle();
				cellstyle.setAlignment(HorizontalAlignment.CENTER);
				cellstyle.setVerticalAlignment(VerticalAlignment.CENTER);
				cellstyle.setBorderLeft(BorderStyle.THIN);
				cellstyle.setBorderRight(BorderStyle.THIN);
				cellstyle.setBorderTop(BorderStyle.THIN);
				cellstyle.setBorderBottom(BorderStyle.THIN);
				cellstyle.setFont(font2);
				cellstyle.setWrapText(true);
				HSSFSheet sheet = workbook.getSheetAt(0);
				int num = 0;
				num = exportData(sheet, workbook, cellstyle, sqlone, 3, a01, 2,"父母");
				
				num = exportData(sheet, workbook, cellstyle, sqltwo, num, a01, 3,"配偶及其父母");
				
				num = exportData(sheet, workbook, cellstyle, sqlthere, num, a01, 3,"本人的兄弟姐妹");
				
				num = exportData(sheet, workbook, cellstyle, sqlFour, num, a01, 4,"子女、子女的配偶及其父母");
				
				num = exportData(sheet, workbook, cellstyle, sqlfive, num, a01, 3,"其他直系和三代以内旁系亲属中现任或曾任厅局级及以上职务，以及移居国（境）外的人员");
				
				if(num>18) {
					CellRangeAddress region = new CellRangeAddress(num, num+1, 0, 2);
			        sheet.addMergedRegion(region);
			        region = new CellRangeAddress(num, num+1, 3, 5);
			        sheet.addMergedRegion(region);
			        region = new CellRangeAddress(num, num, 6, 12);
			        sheet.addMergedRegion(region);
			        region = new CellRangeAddress(num+1, num+1, 7, 8);
			        sheet.addMergedRegion(region);
			        region = new CellRangeAddress(num+1, num+1, 9, 12);
			        sheet.addMergedRegion(region);
					region = new CellRangeAddress(num+2, num+2, 0, 12);
			        sheet.addMergedRegion(region);
				}
				Row row=sheet.getRow(0);
				Cell cell=row.getCell(1);
				//cell.setCellValue(a01.getA0184());
				
				row=sheet.getRow(num);
				cell=row.getCell(6);
				cell.setCellValue("姓名: "+a01.getA0101()+"   工作单位及职务: "+a01.getA0192a());
				
				row=sheet.getRow(num+1);
				cell=row.getCell(7);
				cell.setCellValue(a01.getA0184());
				

				fos = new FileOutputStream(file_copy);
				workbook.write(fos);
				fos.flush();
				fos.close();
				workbook.close();
				System.gc();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RadowException(e.getMessage());
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
					if (fos != null) {
						fos.close();
					}
					if (is != null) {
						is.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		File file = new File(path);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file1 = files[i];
			if (file1.isFile()) {
				String path1 = file1.getAbsolutePath();
				if (path1.endsWith(".xls")) {
					AsposeExcelToPdf.pdfToPdf(path1);
				}
			}
		}

		File[] filess = file.listFiles();
		PDFMergerUtility mergePdf = new PDFMergerUtility();

		// PDDocument doc = null;
		for (int j = 0; j < filess.length; j++) {
			File file2 = filess[j];
			if (file2.isFile()) {
				String path2 = file2.getAbsolutePath();
				if (path2.endsWith(".pdf")) {

					mergePdf.addSource(file2);
				}
			}

		}
		mergePdf.setDestinationFileName(file.getAbsolutePath() + "/家庭成员.pdf");
		mergePdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

		this.getPageElement("zippath").setValue(path + "家庭成员.pdf");
		this.getExecuteSG().addExecuteCode("printExcel();");

		/*
		 * String
		 * ctxpath=this.request.getSession().getServletContext().getRealPath("/");
		 * String uuid=UUID.randomUUID().toString(); File file3=new
		 * File(ctxpath+"/upload/printPdf/"+uuid); if (!file3.exists() &&
		 * !file3.isDirectory()) { file3.mkdirs(); } String value="/jtcy.pdf"; String
		 * path3=file3.getAbsolutePath()+value; String path4=file3.getPath();
		 * mergePdf.setDestinationFileName(path3);
		 * mergePdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
		 * this.getPageElement("zippath").setValue(path3);
		 * //this.getPageElement("jtcy").setValue("/upload/printPdf/"+uuid+value);
		 * this.getExecuteSG().addExecuteCode("printExcel();");
		 */

		return EventRtnType.NORMAL_SUCCESS;

	}

	// 家庭成员排序
	@PageEvent("worksort")
	public int worksort() throws RadowException, AppException {
		PageElement pe = this.getPageElement("MGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		HBSession sess = HBUtil.getHBSession();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			A36 a36 = (A36) sess.get(A36.class, (Serializable) map.get("a3600"));

			BigDecimal b = new BigDecimal(i + 1);
			String sql = "";
			if (a36 == null) {
				sql = "update a36z1 set sortid=" + b + " where a3600='" + map.get("a3600") + "'";
				sess.createSQLQuery(sql).executeUpdate();

			} else {
				sql = "update a36 set sortid=" + b + " where a3600='" + map.get("a3600") + "'";
				sess.createSQLQuery(sql).executeUpdate();
			}

		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("dosearch")
	public int dosearch() throws RadowException {
		HBSession sess = new HBUtil().getHBSession();

		String a0000 = this.getPageElement("a0000s").getValue();
		String t_ime = this.getPageElement("sbny").getValue();
		String sql = "select t_ime||':'||nvl2(filetext,filetext,'无')  from a36_file where 1=1 and a0000='" + a0000
				+ "' and t_ime='" + t_ime + "'";
		List list = sess.createSQLQuery(sql).list();
		StringBuffer str = new StringBuffer();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				str.append(list.get(i) + "\n");

			}
			
			this.getPageElement("txtarea").setValue(str.toString());

		} else {
			this.setMainMessage("无数据");
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("queryByName")
	public int queryByName() throws RadowException, AppException {
		String b0111 = this.getPageElement("dept").getValue();
		String userid = this.getPageElement("userid").getValue();
		StringBuffer sql = new StringBuffer();
	
		if (b0111 == null || "".equals(b0111)) {
			b0111 = this.getPageElement("b0111").getValue();

		}
		String name = this.getPageElement("seachName").getValue();
		if (name == null || "".equals(name)||"输入姓名".equals(name)) {
			name = " and 1=1 ";
		}else {
			name = StringEscapeUtils.escapeSql(name.trim());
			name = name.replaceAll("\\s+"," ");
			name = name.replaceAll(" ", ",");
			name = name.replace(".", ",");
			name = name.replace("&", ",");
			name = name.replace("#", ",");
			name = name.replaceAll("[\\t\\n\\r]", ",");
			// 判断name是否包含","
			if (name.indexOf(",") > 0) {
				String[] names = name.split(",");
				String newName = "";
				for (String n : names) {
					if (n == null || "".equals(n)) {
						continue;
					}
					newName = newName + "'" + n + "',";
				}
				if (newName != null) {
					newName = newName.substring(0, newName.length() - 1);
					
					name = " and (a01.a0184 in (" + newName + ") or a01.a0101 in (" + newName
							+ ")  ) ";
				}
		}else {
			name = " and (a01.a0184 like '%" + name + "%' or a01.a0101 like '%" + name+ "%' or a01.a0102 = '"+name.toUpperCase()+"') ";
		}
		}

			sql.append("select "+cols+" from A01 a01 where 1=1 "
					+ " and (a01.a0221 not in ('1A01','1A02','1A11','1A12') OR a01.a0221 is null) ");
			sql.append(" and a01.status!='4' and a0163 like '1%' and a01.a0000 is not null  "+name+" ");
			sql.append(" and exists (select 1 from a02 a02 ,competence_userdept cu where a02.a0201b =cu.b0111 and cu.userid = '"+userid+"'"
					+ " and a0281='true' ");
			sql.append(" and a01.a0000=a02.a0000 and a02.a0201b like '" + b0111
					+ "%') order by a01.torgid,a01.torder asc ");
	    this.getPageElement("sql").setValue(sql.toString());
		this.setNextEventName("perGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	public static void insertRow(HSSFSheet sheet, int startRow,int rows) {
		 
		  sheet.shiftRows(startRow + 1, sheet.getLastRowNum(), rows,true,false);
		  //  Parameters:
		  //   startRow - the row to start shifting
		  //   endRow - the row to end shifting
		  //   n - the number of rows to shift
		  //   copyRowHeight - whether to copy the row height during the shift
		  //   resetOriginalRowHeight - whether to set the original row's height to the default

		  for (int i = 0; i < rows; i++) {
			  
			  	HSSFRow sourceRow = null;
			  	HSSFRow targetRow = null;
			  	
			  	sourceRow = sheet.getRow(startRow);
			  	targetRow = sheet.createRow(++startRow);
			  	
			  	Util.copyRow(sheet, sourceRow, targetRow);
		  }
		  
	}



	@PageEvent("dorecover")
	@Transaction
	public int dorecover() throws RadowException, SQLException {
		String a0000 = this.getPageElement("a0000").getValue();
		HBSession sess = HBUtil.getHBSession();
		A01 a01 = (A01) sess.get(A01.class, a0000);
		CallableStatement call = null;
		List list = sess.createSQLQuery("select a3600 from a36 where a0000='" + a0000 + "' union all"
				+ " select a3600 from a36new where a0000='" + a0000 + "' ").list();
		if (list.size() < 1) {
			this.setMainMessage(a01.getA0101() + "正式表没有数据 !!!");
		} else {
			call = sess.connection().prepareCall("{call PRO_A36_REC(?)}");
			call.setString(1, a0000);
			call.execute();
		}
		this.getExecuteSG().addExecuteCode("radow.doEvent('MGrid.dogridquery');");
		return EventRtnType.NORMAL_SUCCESS;
	}


}
