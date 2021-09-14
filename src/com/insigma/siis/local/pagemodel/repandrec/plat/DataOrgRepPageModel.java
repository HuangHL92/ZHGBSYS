package com.insigma.siis.local.pagemodel.repandrec.plat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
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
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
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
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Reportftp;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzFtpClient;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
public class DataOrgRepPageModel extends PageModel {

	@PageEvent("reppackagebtn.onclick")
	public int reppackagebtn(String name) throws RadowException, AppException {
		String id = UUID.randomUUID().toString().replace("-", "");	//刷新纪录关联 id 
		try {
			//-- 01 查找跟机构,获取页面信息条件 -------------------------------------------------------------------------------------------
			List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 where b0121 ='-1' ").list();
			B01 b01 = null;											//声明跟机构,获取根机构信息
			if (b01s != null && b01s.size() > 0) {
				b01 = b01s.get(0);
			} else {
				this.setMainMessage("系统出错，请联系管理员！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String searchDeptid = b01.getB0111();							//跟机构节点id
			String ftpid = this.getPageElement("ftpid").getValue();			//上报 fpt id
			String linkpsn = this.getPageElement("linkpsn").getValue();		//联系人
			String linktel = this.getPageElement("linktel").getValue();		//联系电话
			String remark = this.getPageElement("remark").getValue();		//备注
			
			String gzlbry = this.getPageElement("gzlbry").getValue();		//工作类别人员(全选)
			String gllbry = this.getPageElement("gllbry").getValue();		//管理类别人员(全选)
			String gz_lb = "";												//所选工作类别
			String gl_lb = "";												//所选管理类别
			//根据页面的统一规则 拼接 所选工作类别listZB125 和 所选管理类别listZB130
			List listZB125 = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
					" codeType='ZB125' order by codeValue").list();
			if(listZB125!=null && listZB125.size()>0){
				for(int i=0;i<listZB125.size();i++){
					CodeValue code = (CodeValue) listZB125.get(i);
					int k = i + 1;
					String gzlb="gz_"+k;	
					String gl_index = this.getPageElement(gzlb).getValue();
					gz_lb = gz_lb +(gl_index.equals("0")?"": "'" + code.getCodeValue()+"',");
				}
			}
			List listZB130 = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
					" codeType='ZB130' order by codeValue").list();
			if(listZB130!=null && listZB130.size()>0){
				for(int i=0;i<listZB130.size();i++){
					CodeValue code = (CodeValue) listZB130.get(i);
					int k = i + 1;
					String gllb="gl_"+k;	
					String gl_index = this.getPageElement(gllb).getValue();
					gl_lb = gl_lb +(gl_index.equals("0")?"": "'" +code.getCodeValue()+"',");
				}
			}
			if(gz_lb.equals("")){
				this.setMainMessage("至少设置一个工作类别!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(gl_lb.equals("")){
				this.setMainMessage("至少设置一个管理类别!");
				return EventRtnType.NORMAL_SUCCESS;
			}

			String lsry = this.getPageElement("lsry").getValue();
			String ltry = this.getPageElement("ltry").getValue();
			String gjgs = this.getPageElement("gjgs").getValue();
			if (gjgs != null && gjgs.equals("1")) {

			} else {
				this.setMainMessage("只有HZB格式支持分包导出。");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//-- 02 运行上报线程 -------------------------------------------------------------------------------------------
			CurrentUser user = SysUtil.getCacheCurrentUser();   	//获取当前执行导入的操作人员信息
			KingbsconfigBS.saveImpDetailInit3(id);					
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
//			DataOrgRepThread thr = new DataOrgRepThread(id, user,gjgs,ltry,lsry,gzlbry,gllbry,searchDeptid,
//					linkpsn,linktel,remark,gz_lb,gl_lb,userVo,ftpid);
			DataOrgRepNewThread thr = new DataOrgRepNewThread(id, ltry,lsry,gzlbry,gllbry,searchDeptid,
					linkpsn,linktel,remark,gz_lb,gl_lb,userVo,ftpid);
			new Thread(thr,"数据上报线程1").start();
//			this.getExecuteSG().addExecuteCode("parent.radow.doEvent('refresh','"+ id +"')");
			this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('refresh','"+ id +"')");
			this.closeCueWindow("dataorgwin");
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("上报异常！异常信息：" + e.getMessage());

		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("reppackagebtn1.onclick")
	public int reppackagebtn_old(String name) throws RadowException, AppException {
		// String tabimp = this.getPageElement("tabimp").getValue();
		//String cmd7z = AppConfig.CMD_7Z_EXE;
		Map<String, String> map = new HashMap<String, String>();
		String localf = AppConfig.LOCAL_FILE_BASEURL;
		HBSession sess = HBUtil.getHBSession();
		String infile = "";
		int packcount = 15000;
		try {
			List<B01> b01s = HBUtil.getHBSession()
					.createQuery(" from B01 where b0121 ='-1' ").list();
			B01 b01 = null;
			if (b01s != null && b01s.size() > 0) {
				b01 = b01s.get(0);
			} else {
				this.setMainMessage("系统出错，请联系管理员！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String searchDeptid = b01.getB0111();
			String ftpid = this.getPageElement("ftpid").getValue();
			String linkpsn = this.getPageElement("linkpsn").getValue();
			String linktel = this.getPageElement("linktel").getValue();
			String remark = this.getPageElement("remark").getValue();
			
			String gzlbry = this.getPageElement("gzlbry").getValue();
			String gllbry = this.getPageElement("gllbry").getValue();
			/*String gz_1 = this.getPageElement("gz_1").getValue();
			String gz_2 = this.getPageElement("gz_2").getValue();
			String gz_3 = this.getPageElement("gz_3").getValue();
			String gz_4 = this.getPageElement("gz_4").getValue();
			String gz_5 = this.getPageElement("gz_5").getValue();
			String gz_6 = this.getPageElement("gz_6").getValue();
			String gz_7 = this.getPageElement("gz_7").getValue();
			String gz_8 = this.getPageElement("gz_8").getValue();
			String gz_9 = this.getPageElement("gz_9").getValue();
			String gz_10 = this.getPageElement("gz_10").getValue();
			String gz_11 = this.getPageElement("gz_11").getValue();
			String gz_12 = this.getPageElement("gz_12").getValue();
			String gz_13 = this.getPageElement("gz_13").getValue();

			String gl_1 = this.getPageElement("gl_1").getValue();
			String gl_2 = this.getPageElement("gl_2").getValue();
			String gl_3 = this.getPageElement("gl_3").getValue();
			String gl_4 = this.getPageElement("gl_4").getValue();
			String gl_5 = this.getPageElement("gl_5").getValue();

			String gz_lb = "" + (gz_1.equals("0") ? "" : "'1'" + ",")
					+ (gz_2.equals("0") ? "" : "'2'" + ",")
					+ (gz_3.equals("0") ? "" : "'3'" + ",")
					+ (gz_4.equals("0") ? "" : "'5'" + ",")
					+ (gz_5.equals("0") ? "" : "'6'" + ",")
					+ (gz_6.equals("0") ? "" : "'7'" + ",")
					+ (gz_7.equals("0") ? "" : "'8'" + ",")
					+ (gz_8.equals("0") ? "" : "'A4'" + ",")
					+ (gz_9.equals("0") ? "" : "'A5'" + ",")
					+ (gz_10.equals("0") ? "" : "'A6'" + ",")
					+ (gz_11.equals("0") ? "" : "'A0'" + ",")
					+ (gz_12.equals("0") ? "" : "'A1'" + ",")
					+ (gz_13.equals("0") ? "" : "'A2'" + ",");
			String gl_lb = "" + (gl_1.equals("0") ? "" : "'01'" + ",")
					+ (gl_2.equals("0") ? "" : "'02'" + ",")
					+ (gl_3.equals("0") ? "" : "'03'" + ",")
					+ (gl_4.equals("0") ? "" : "'04'" + ",")
					+ (gl_5.equals("0") ? "" : "'09'" + ",");*/
			String gz_lb = "";
			String gl_lb = "";
			List listZB125 = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
					" codeType='ZB125' order by codeValue").list();
			if(listZB125!=null && listZB125.size()>0){
				for(int i=0;i<listZB125.size();i++){
					CodeValue code = (CodeValue) listZB125.get(i);
					int k = i + 1;
					String gzlb="gz_"+k;	
					String gl_index = this.getPageElement(gzlb).getValue();
					gz_lb = gz_lb +(gl_index.equals("0")?"": "'" + code.getCodeValue()+"',");
				}
			}
			List listZB130 = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
					" codeType='ZB130' order by codeValue").list();
			if(listZB130!=null && listZB130.size()>0){
				for(int i=0;i<listZB130.size();i++){
					CodeValue code = (CodeValue) listZB130.get(i);
					int k = i + 1;
					String gllb="gl_"+k;	
					String gl_index = this.getPageElement(gllb).getValue();
					gl_lb = gl_lb +(gl_index.equals("0")?"": "'" +code.getCodeValue()+"',");
				}
			}
			
			if(gz_lb.equals("")){
				this.setMainMessage("至少设置一个工作类别!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(gl_lb.equals("")){
				this.setMainMessage("至少设置一个管理类别!");
				return EventRtnType.NORMAL_SUCCESS;
			}

			String lsry = this.getPageElement("lsry").getValue();
			String ltry = this.getPageElement("ltry").getValue();
			String gjgs = this.getPageElement("gjgs").getValue();
			if (gjgs != null && gjgs.equals("1")) {

			} else {
				this.setMainMessage("只有HZB格式支持分包导出。");
				return EventRtnType.NORMAL_SUCCESS;
			}
			// B01 b01 = (B01) HBUtil.getHBSession().get(B01.class,
			// searchDeptid);
			// ------------ -------------//
			StringBuilder b = new StringBuilder();
			if (DBUtil.getDBType().equals(DBType.MYSQL)) {
				b.append("select distinct a1.a0000 a0000 from a01 a1 where 1=1");
				if (ltry.equals("0")) {
					b.append(" and a1.status<>'3'");
				}
				if (lsry.equals("0")) {
					b.append(" and a1.status<>'2'");
				}
				if (!gz_lb.equals("")) {
					b.append(" and a1.a0160 in ("
							+ gz_lb.substring(0, gz_lb.length() - 1) + ")");
				}
				if (!gl_lb.equals("")) {
					b.append(" and a1.a0165 in ("
							+ gl_lb.substring(0, gl_lb.length() - 1) + ")");
				}
				if(!gz_lb.equals("")){
					if(gzlbry.equals("0")){
						b.append(" and a1.a0160 in (" + gz_lb.substring(0, gz_lb.length()-1) + ")");
					}
				}
				if(!gl_lb.equals("")){
					if(gllbry.equals("0")){
						b.append(" and a1.a0165 in (" + gl_lb.substring(0, gl_lb.length()-1) + ")");
					}
				}
				b.append(" order by rand() ");
			} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
				b.append("select a0000,rownum rn from(select distinct a1.a0000 a0000 from a01 a1 where 1=1");
				if (ltry.equals("0")) {
					b.append(" and a1.status<>'3'");
				}
				if(!gz_lb.equals("")){
					if(gzlbry.equals("0")){
						b.append(" and a1.a0160 in (" + gz_lb.substring(0, gz_lb.length()-1) + ")");
					}
				}
				if(!gl_lb.equals("")){
					if(gllbry.equals("0")){
						b.append(" and a1.a0165 in (" + gl_lb.substring(0, gl_lb.length()-1) + ")");
					}
				}
				// b.append(" and a2.a0201b in (select b.b0111 from b01 b start with b0111='"
				// + searchDeptid +"' connect by prior b0111=b0121)");
				b.append(" order by a1.a0000 )");
			}

			Object obj = HBUtil.getHBSession().createSQLQuery(
							"select count(s.a0000) from (" + b.toString()
									+ ") s").uniqueResult();
			int count = 1;

			String path = getPath();
			java.sql.Timestamp now = DateUtil.getTimestamp();
			String time = DateUtil.timeToString(now);
			String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
			ZwhzPackDefine info = new ZwhzPackDefine();
			String sid = UUID.randomUUID().toString().replace("-", "");
			info.setId(sid);
			info.setB0101(b01.getB0101());
			info.setB0111(b01.getB0111());
			info.setB0114(b01.getB0114());
			info.setB0194(b01.getB0194());

			info.setDataversion(DateUtil.dateToString(DateUtil.getSysDate(),
					"yyyyMMdd"));
			info.setLinkpsn(linkpsn);
			info.setLinktel(linktel);
			if (obj != null) {
				if (DBUtil.getDBType().equals(DBType.MYSQL)) {
					count = ((BigInteger) obj).intValue()/ packcount
							+ (((BigInteger) obj).intValue() % packcount != 0 ? 1
									: 0);
					info.setPersoncount(Long.valueOf(((BigInteger) obj)
							.intValue()));
				} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
					count = ((BigDecimal) obj).intValue()/ packcount
							+ (((BigDecimal) obj).intValue() % packcount != 0 ? 1
									: 0);
					info.setPersoncount(Long.valueOf(((BigDecimal) obj)
							.intValue()));
				}
			}
			if(count == 0){
				count = 1;
			}
			info.setRemark(remark);
			info.setStype("2");
			info.setStypename("按机构导出");
			info.setTime(time);
			info.setTranstype("up");
			info.setErrortype("无");
			info.setErrorinfo("无");
			Map downf = new HashMap();
			List<SFileDefine> sfile = new ArrayList<SFileDefine>();
			String packageFile = "Pack_按机构导出文件_" + b01.getB0114() + "_"
					+ b01.getB0101() + "_" + time1 + ".xml";
			for (int i = 1; i <= count; i++) {
				SFileDefine sf = new SFileDefine();
				StringBuilder a01sql = new StringBuilder();
				if (DBUtil.getDBType().equals(DBType.MYSQL)) {
					a01sql.append(" select t.a0000 from(");
					a01sql.append(b);
					a01sql.append(" limit " + ((i - 1) * packcount) + ","
							+ packcount);
					a01sql.append(") as t ");
				} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
					a01sql.append(" select a0000 from(");
					a01sql.append(b);
					a01sql.append(") where rn >=" + ((i - 1) * packcount + 1));
					a01sql.append(" and rn <=" + (i * packcount));
				}
				List<B01> list16 = null;
				if (i == 1) {
					if (DBUtil.getDBType().equals(DBType.MYSQL)) {
						list16 = HBUtil
								.getHBSession()
								.createSQLQuery(
										"select * from b01 where b0111 like '"
												+ searchDeptid + "%'")
								.addEntity(B01.class).list();
					} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
						list16 = HBUtil
								.getHBSession()
								.createSQLQuery(
										"select * from b01 start with b0111='"
												+ searchDeptid
												+ "' connect by prior b0111=b0121")
								.addEntity(B01.class).list();
					}
					info.setOrgcount(Long.valueOf(list16.size()));
				}

				List<A01> list = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from a01 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A01.class).list();
				List<A02> list2 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A02 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A02.class).list();
				List<A06> list3 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A06 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A06.class).list();
				List<A08> list4 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A08 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A08.class).list();
				List<A11> list5 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A11 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A11.class).list();

				List<A14> list6 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A14 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A14.class).list();
				List<A15> list7 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A15 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A15.class).list();
				List<A29> list8 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A29 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A29.class).list();
				List<A30> list9 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A30 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A30.class).list();
				List<A31> list10 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A31 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A31.class).list();

				List<A36> list11 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A36 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A36.class).list();
				List<A37> list12 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A37 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A37.class).list();
				List<A41> list13 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A41 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A41.class).list();
				List<A53> list14 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A53 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A53.class).list();
				List<A57> list15 = HBUtil
						.getHBSession()
						.createSQLQuery(
								"select * from A57 where a0000 in("
										+ a01sql.toString() + ")")
						.addEntity(A57.class).list();
				List<Map> list17 = new ArrayList<Map>();
				map.put("type", "2");
				map.put("time", time);
				// ??
				map.put("dataversion", "20121221");
				map.put("psncount",
						(list != null && list.size() > 0) ? (list.size() + "")
								: "");
				//
				map.put("photodir", "Photos");
				map.put("B0101", b01.getB0101());
				map.put("B0111", b01.getB0111());
				map.put("B0114", b01.getB0114());
				map.put("B0194", b01.getB0194());
				map.put("linkpsn", linkpsn);
				map.put("linktel", linktel);
				map.put("remark", remark);
				sf.setTime(time);
				sf.setOrgrows((list16 != null && list16.size() > 0) ? (list16
						.size()) : 0L);
				sf.setPersonrows((list != null && list.size() > 0) ? (list
						.size()) : 0L);

				list17.add(map);
				// String path = getPath();
				String number = ("000" + i).substring(("000" + i).length() - 3);
				String zippath = path + "按机构导出文件_" + b01.getB0111() + "_"
						+ b01.getB0101() + "_" + time1 + "/" + number + "/";
				File file = new File(zippath);
				// 如果文件夹不存在则创建
				if (!file.exists() && !file.isDirectory()) {
					file.mkdirs();
				}
				String zippathtable = path + "按机构导出文件_" + b01.getB0111() + "_"
						+ b01.getB0101() + "_" + time1 + "/" + number
						+ "/Table/";
				File file1 = new File(zippathtable);
				// 如果文件夹不存在则创建
				if (!file1.exists() && !file1.isDirectory()) {
					file1.mkdirs();
				}
				String zipfile = localf + "/" + "按机构导出文件_" + b01.getB0114()
						+ "_" + b01.getB0101() + "_" + time1 + "_" + number
						+ ".7z";
				if (gjgs != null && gjgs.equals("1")) {
					zipfile = localf + "/" + "按机构导出文件_" + b01.getB0114() + "_"
							+ b01.getB0101() + "_" + time1 + "_" + number
							+ ".hzb";
					Xml4HZBUtil.List2Xml(list, "A01", zippath);
					Xml4HZBUtil.List2Xml(list2, "A02", zippath);
					Xml4HZBUtil.List2Xml(list3, "A06", zippath);
					Xml4HZBUtil.List2Xml(list4, "A08", zippath);
					Xml4HZBUtil.List2Xml(list5, "A11", zippath);

					Xml4HZBUtil.List2Xml(list6, "A14", zippath);
					Xml4HZBUtil.List2Xml(list7, "A15", zippath);
					Xml4HZBUtil.List2Xml(list8, "A29", zippath);
					Xml4HZBUtil.List2Xml(list9, "A30", zippath);
					Xml4HZBUtil.List2Xml(list10, "A31", zippath);

					Xml4HZBUtil.List2Xml(list11, "A36", zippath);
					Xml4HZBUtil.List2Xml(list12, "A37", zippath);
					Xml4HZBUtil.List2Xml(list13, "A41", zippath);
					Xml4HZBUtil.List2Xml(list14, "A53", zippath);
					Xml4HZBUtil.List2Xml(list15, "A57", zippath);

					Xml4HZBUtil.List2Xml(list16, "B01", zippath);
					Xml4HZBUtil.List2Xml(list17, "info", zippath);
				}
				if (list15 != null && list15.size() > 0) {
					String photopath = zippath + "Photos/";
					File file2 = new File(photopath);
					// 如果文件夹不存在则创建
					if (!file2.exists() && !file2.isDirectory()) {
						file2.mkdirs();
					}
					List<String> photolist = new ArrayList<String>();
					for (int j = 0; j < list15.size(); j++) {
						A57 a57 = list15.get(j);
//						if (a57.getPhotoname() != null
//								&& !a57.getPhotoname().equals("")
//								&& a57.getPhotodata() != null) {
//							File f = new File(photopath + a57.getA0000() + "."
//									+ (a57.getPhotoname().split("\\.")[1]));
//							FileOutputStream fos = new FileOutputStream(f);
//							InputStream is = a57.getPhotodata()
//									.getBinaryStream();// 读出数据后转换为二进制流
//							byte[] data = new byte[1024];
//							while (is.read(data) != -1) {
//								fos.write(data);
//							}
//							fos.close();
//							is.close();
//						}
						String photoname = (a57.getPhotoname()!=null&&!a57.getPhotoname().equals(""))?
								a57.getPhotoname():a57.getA0000() +"." + "jpg";
						photolist.add(a57.getPhotopath()+photoname);
					}
					PhotosUtil.copyCmd(photolist, photopath);
				}
				infile = zipfile;
//				SevenZipUtil.zip7z(zippath, zipfile, "1234");
				NewSevenZipUtil.zip7zNew(zippath, zipfile, "1");
				File file0 = new File(zipfile);
				sf.setName(file0.getName());
				// InputStream f1 = new FileInputStream(zipfile);
				// int size = f1.available();
				sf.setSize(getFileSize(file0));
				sfile.add(sf);
				this.getPageElement("downfile").setValue(
						infile.replace("\\", "/"));
				downf.put("file" + i, infile.replace("\\", "/"));
			}
			info.setDatainfo("机构单位信息集数据" + info.getOrgcount() + "条，人员基本信息集"
					+ info.getPersoncount() + "条。");
			// this.getExecuteSG().addExecuteCode("window.reloadTree('"+(net.sf.json.JSONArray.fromObject(downf).toString())+"')");
			info.setSfile(sfile);
			CommonQueryBS.systemOut(JXUtil.Object2Xml(info, true));
			FileUtil.createFile(localf + "/" + packageFile,
					JXUtil.Object2Xml(info, true), false, "UTF-8");
			if (StringUtil.isEmpty(ftpid)) {
				throw new AppException("上报单位为空，请重新选择！");
			}
			TransConfig jfcc = (TransConfig) HBUtil.getHBSession().get(
					TransConfig.class, ftpid);
			// ZwhzFtpClient.uploadHzb(jfcc, localf + "/"+ packageFile);
			// UploadHelpFileServlet.delFolder(path);
			Reportftp rfpt = new Reportftp();
			rfpt.setFilename("");
			rfpt.setPackageindex(sid);
			rfpt.setPackagename(packageFile);
			// rfpt.setPackageret("");
			rfpt.setRecieveftpuserid(jfcc.getId());
			rfpt.setRecieveftpusername(jfcc.getName());
			// rfpt.setRecieveorg(recieveorg);
			// rfpt.setRecieveorgname(recieveorgname);
			rfpt.setReporttime(DateUtil.getTimestamp());
			rfpt.setB0111(b01.getB0111());
			rfpt.setReporttype("1");
			// String userid = SysUtil.getCacheCurrentUser().getId();
			rfpt.setReportuser(SysUtil.getCacheCurrentUser().getId());
			rfpt.setReportusername(SysUtil.getCacheCurrentUser().getName());
			try {
				sess.beginTransaction();
				sess.save(rfpt);
				ZwhzFtpClient.uploadHzb(jfcc, localf + "/" + packageFile);
				// UploadHelpFileServlet.delFolder(path);
				// Xml4HZBUtil.List2Xml(files, packageFile, path, dataMap);
				sess.getTransaction().commit();
				this.createPageElement("MGrid", "grid", true).reload();
				this.closeCueWindow("dataorgwin");
			} catch (Exception e) {
				e.printStackTrace();
				sess.getTransaction().rollback();
				throw new AppException(e.getMessage());

			}
			new LogUtil().createLog("431", "REPORT_FTP", "", "", "数据上报", new ArrayList());
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("上报异常！异常信息：" + e.getMessage());

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

	@PageEvent("reset.onclick")
	@NoRequiredValidate
	public int resetbtn(String name) throws RadowException {
		this.getPageElement("searchDeptBtn").setValue("");
		this.getPageElement("ftpid").setValue("");
		this.getPageElement("linkpsn").setValue("");
		this.getPageElement("linktel").setValue("");
		this.getPageElement("remark").setValue("");
		this.getPageElement("gz_1").setValue("0");
		this.getPageElement("gz_2").setValue("0");
		this.getPageElement("gz_3").setValue("0");
		this.getPageElement("gz_4").setValue("0");
		this.getPageElement("gz_5").setValue("0");
		this.getPageElement("gz_6").setValue("0");
		this.getPageElement("gz_7").setValue("0");
		this.getPageElement("gz_8").setValue("0");
		this.getPageElement("gz_9").setValue("0");
		this.getPageElement("gz_10").setValue("0");
		this.getPageElement("gz_11").setValue("0");
		this.getPageElement("gz_12").setValue("0");
		this.getPageElement("gz_13").setValue("0");

		this.getPageElement("gl_1").setValue("0");
		this.getPageElement("gl_2").setValue("0");
		this.getPageElement("gl_3").setValue("0");
		this.getPageElement("gl_4").setValue("0");
		this.getPageElement("gl_5").setValue("0");

		// this.getPageElement("lsry").getValue();
		this.getPageElement("ltry").setValue("0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	private String getPath() {
		// TODO Auto-generated method stub
		String classPath = getClass().getClassLoader().getResource("/")
				.getPath();
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath = "";

		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1,
					classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0,
					classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		// 上传路径
		String upload_file = rootPath + "zipload/";
		try {
			File file = new File(upload_file);
			// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 解压路径
		String zip = upload_file + uuid + "/";
		return zip;
	}

	private String getRootPath() {
		// TODO Auto-generated method stub
		String classPath = getClass().getClassLoader().getResource("/")
				.getPath();
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath = "";

		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1,
					classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0,
					classPath.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}

	@PageEvent("searchDeptBtn.ontriggerclick")
	@NoRequiredValidate
	public int searchDept(String name) throws RadowException {
		this.openWindow("deptWin", "pages.repandrec.plat.SearchFtpUp");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public void closeCueWindow(String arg0) {
		this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('"+arg0+"').close();");
	}

}
