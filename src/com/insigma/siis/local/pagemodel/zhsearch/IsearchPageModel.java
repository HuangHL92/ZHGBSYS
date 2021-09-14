package com.insigma.siis.local.pagemodel.zhsearch;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.noggit.JSONUtil;
import org.hibernate.SQLQuery;

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
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.bean.SQLInfo;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.sys.SysfunctionManager;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Customquery;
import com.insigma.siis.local.business.helperUtil.CodeType2js;
import com.insigma.siis.local.business.helperUtil.CommonSQLUtil;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.lrmx.ItemXml;
import com.insigma.siis.local.lrmx.JiaTingChengYuanXml;
import com.insigma.siis.local.lrmx.PersonXml;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryByNamePageModel;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryPageModel;
import com.insigma.siis.local.pagemodel.customquery.DataPsnImpDBThread;
import com.insigma.siis.local.pagemodel.customquery.GroupPageBS;
import com.insigma.siis.local.pagemodel.dataverify.DataPsnImpThread;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.publicServantManage.betchModifyPageModel;
import com.utils.DBUtils;

import net.sf.json.JSONObject;

/**
 * @author lixy
 *
 */
public class IsearchPageModel extends PageModel {
	/**
	 * 系统区域信息
	 */
	private final static int ON_ONE_CHOOSE = -1;
	public static String queryType = "0";// 1点击机构树查询2点击按钮查询
	public static String LISTADDCCQLI = "-1";
	public static boolean QUERYLISTFLAG = false;
	public static String LISTADDNAME = "无";
	private final static int CHOOSE_OVER_TOW = -2;

	/**
	 * 自定义查询，加载列表属性
	 *
	 * @throws AppException
	 */
	@PageEvent("query_config")
	public int query_config() throws RadowException, AppException {
		String qvid = this.request.getParameter("qvid");
		if(qvid==null||"".equals(qvid)){
			qvid = this.getPageElement("qvid").getValue();
		}
		String viewnametb = HBUtil.getValueFromTab("viewnametb", "qryview", "qvid='" + qvid + "'");
		String sessionid = this.request.getSession().getId();
		HBUtil.executeUpdate("delete from A01SEARCHTEMP where sessionid='" + sessionid + "' ");

		// (@i:=@i+1) as i from (select @i:=0) as it,
		if (DBUtil.getDBType() == DBType.ORACLE) {
			HBUtil.executeUpdate("insert into A01SEARCHTEMP (A0000,SESSIONID,SORT) select a01a0000,'" + sessionid
					+ "',rownum i from (select a01a0000 from  " + viewnametb + " group by a01a0000 )");

		} else { // mysql
			HBUtil.executeUpdate("insert into A01SEARCHTEMP (A0000,SESSIONID,SORT) select a01a0000,'" + sessionid
					+ "',(@i:=@i+1) as i from (select @i:=0) as it, (select a01a0000 from  " + viewnametb
					+ " group by a01a0000 ) tt");
		}

		StringBuilder cm = new StringBuilder("[");
		StringBuilder dm = new StringBuilder("[");
		cm.append("new Ext.grid.RowNumberer({locked:true,header:'',width:23}), "
				+ "{locked:true,header: \"<div class='x-grid3-check-col-td'><div class='x-grid3-check-col' alowCheck='true' id='selectall_peopleInfoGrid_personcheck' onclick='odin.selectAllFuncForE3(\\\"peopleInfoGrid\\\",this,\\\"personcheck\\\");getCheckList(\\\"peopleInfoGrid\\\",\\\"personcheck\\\",this);'></div></div>\","
				+ "hidden:false,align:'center', width: 40, sortable: false, enterAutoAddRow:false,"
				+ "renderer:function(value, params, record,rowIndex,colIndex,ds){var rtn = '';params.css=' x-grid3-check-col-td';if(value==true || value=='true'){rtn=\"<div class='x-grid3-check-col-on' alowCheck='true' onclick='odin.accCheckedForE3(this,\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");getCheckList2(\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");'></div>\";}else{rtn=\"<div class='x-grid3-check-col' alowCheck='true' onclick='odin.accCheckedForE3(this,\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");getCheckList2(\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");'></div>\";}return odin.renderEdit(rtn,params,record,rowIndex,colIndex,ds);}, "
				+ "editable:false,  hideable: false, dataIndex: 'personcheck', editor: new Ext.form.Checkbox({inputType:'',fireKey:odin.doAccSpecialkey})},");
		dm.append("{name: 'personcheck'},{name: 'a0000'},");
		int i = 0;
		HBSession sess = HBUtil.getHBSession();
		String sql = "select " + "concat(tblname,fldname) fldname1," + "fldnamenote,"
				+ "(select code_type from code_table_col where table_code=tblname and col_code=fldname) code_type  "
				+ " from qryviewfld t where qvid='" + qvid + "' and fldname!='a0000' " + " order by "
				+ CommonSQLUtil.to_number("fldnum") + " asc";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		for (Object[] o : list) {
			String name = o[0].toString().toLowerCase();
			String header = o[1].toString();
			// String width = o[3].toString();
			String width = "100";
			String codeType = o[2] == null || "null".equals(o[2]) || "".equals(o[2].toString().trim()) ? ""
					: o[2].toString().toUpperCase();
			// String editor = o[5].toString().toLowerCase();
			String editor = "text";
			if (codeType != null && codeType.trim().length() != 0) {
				editor = "select";
			} else {
				editor = "text";
			}
			// String desc = o[6].toString();
			// String renderer = o[7]==null?"":o[7].toString();
			String renderer = "";
			// String align = o[9].toString();
			String align = "left";
			if (!"".equals(renderer)) {
				// renderer = "var v="+renderer+"(a,b,c,d,e,f);";
				renderer = "function(a,b,c,d,e,f){var v=" + renderer
						+ "(a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f,\"\")}";
			} else {
				renderer = "function(v,b,c,d,e,f){odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f,\"\")}";
			}
			boolean locked = false;
			if ("a01a0101".equals(name)) {
				locked = true;
			}
			// i++;
			if (!"a01a0000".equals(name)) {
				if ("text".equals(editor)) {
					cm.append("{locked:" + locked + ",header: \"" + header + "\",hidden:false,align:'" + align
							+ "', width: " + width + ", sortable: true," + " enterAutoAddRow:false,renderer:" + renderer
							+ ", editable:false, " + " dataIndex: '" + name
							+ "', editor: new Ext.form.TextField({allowBlank:true ,fireKey:odin.doAccSpecialkey})},");

					dm.append("{name: '" + name + "'},");

				} else if ("select".equals(editor)) {
					cm.append("{locked:" + locked + ",header: \"" + header + "\",hidden:false,align:'" + align
							+ "', width: " + width + ", sortable: true, "
							+ "enterAutoAddRow:false,renderer:function(a,b,c,d,e,f){var v=odin.doGridSelectColRender('peopleInfoGrid','"
							+ name
							+ "',a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f,\"\")}, editable:false,  dataIndex: '"
							+ name + "', "
							+ "editor: new Ext.form.ComboBox({store: new Ext.data.SimpleStore({fields: ['key', 'value'],data:"
							+ CodeType2js.getCodeTypeJS(codeType) + ","
							+ "createFilterFn:odin.createFilterFn}),displayField:'value',typeAhead: false,mode: 'local',triggerAction: 'all',"
							+ "editable:true,selectOnFocus:true,hideTrigger:false,expand:function(){odin.setListWidth(this);Ext.form.ComboBox.prototype.expand.call(this);},allowBlank:true ,fireKey:odin.doAccSpecialkey})},");

					dm.append("{name: '" + name + "'},");
				}

			}

		}
		cm.deleteCharAt(cm.length() - 1);
		cm.append("]");
		dm.deleteCharAt(dm.length() - 1);
		dm.append("]");
		this.request.getSession().setAttribute("queryType", "define");// 设置自定义查询列表标志
		StringBuffer sb = new StringBuffer();
		sb.append(cm.toString() + "{split}" + dm.toString());
		// 将qvid值放到session中
		this.request.getSession().setAttribute("qvid", qvid);
		/*String sqls = "select a01.a0000, '"+sessionid+"' sessionid from A01 a01 where 1 = 1"
				+ " and concat(a01.a0000, '') in (select a02.a0000 from a02 where a02.A0201B in "
				+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+SysManagerUtils.getUserId()+"')"
				+ " and a02.a0281 = 'true') and a01.a0000 in (select a01a0000 from "+viewnametb+")";
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sqls);
		map.put("messageCode", 0);
		String json = JSONUtil.toJSON(map);
		this.setSelfDefResData(json);*/
		//this.getPageElement("sql").setValue(sqls);
		//this.getExecuteSG().addExecuteCode("document.getElementById('sql').value="+sqls);
		this.getExecuteSG().addExecuteCode(sb.toString());
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 修改人员信息的双击事件
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("peopleInfoGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException { // 打开窗口的实例
		String a0000 = this.getPageElement("peopleInfoGrid")
				.getValue("a0000", this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
		A01 ac01 = (A01) HBUtil.getHBSession().get(A01.class, a0000);
		if (ac01 != null) {
			this.request.getSession().setAttribute("personIdSet", null);
			/*this.getExecuteSG()
					.addExecuteCode("$h.showModalDialog('personInfoOP','" + request.getContextPath()
							+ "/rmb/ZHGBrmb.jsp?a0000=" + a0000 + "','人员信息修改',1009,630,null," + "{a0000:'" + a0000
							+ "',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
			this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
			// initA0000Map(a0000);
			// this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			// this.setRadow_parent_data(this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString());
			return EventRtnType.NORMAL_SUCCESS;
		} else {
			throw new AppException("该人员不在系统中！");
		}
	}

	@PageEvent("clear.onclick")
	@NoRequiredValidate
	@AutoNoMask
	public int clear() throws RadowException {
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("peopleInfoGrid.dogridquery")
	public int doMemberQuery(int start, int limit) throws RadowException, AppException {
		String queryType = "";
		queryType = (String) request.getSession().getAttribute("queryType");
		if (queryType == null)
			queryType = "1";

		if ("define".equals(queryType)) {// 自定义查询
			// String
			// qvid=this.getPageElement("qvid").getValue();当页面直接打开时，此方法取不到qvid值，采用session中取值
			String qvid = this.request.getSession().getAttribute("qvid").toString();
			String viewnametb = HBUtil.getValueFromTab("viewnametb", "qryview", "qvid='" + qvid + "'");
			String sessionid = this.request.getSession().getId();
			String username = SysManagerUtils.getUserloginName();
			String sql = "";
			// if("system".equals(username)){
			/*sql = " select * from " + viewnametb + ",A01SEARCHTEMP " + " where " + viewnametb
					+ ".a01a0000=A01SEARCHTEMP.a0000  " + " and A01SEARCHTEMP.sessionid='" + sessionid
					+ "' order by A01SEARCHTEMP.sort asc  ";*///

			  //}else{


			String b01String = (String)this.getPageElement("SysOrgTreeIds").getValue();

	        StringBuffer cu_b0111_sb = new StringBuffer("");

	        if(b01String!=null && !"".equals(b01String)){//tree!=null && !"".equals(tree.trim()
				JSONObject jsonObject = JSONObject.fromObject(b01String);
				if(jsonObject.size()>0){
					cu_b0111_sb.append(" and (1=2 ");
				}
				Iterator<String> it = jsonObject.keys();
				// 遍历jsonObject数据，添加到Map对象
				while (it.hasNext()) {
					String nodeid = it.next();
					String operators = (String) jsonObject.get(nodeid);
					String[] types = operators.split(":");//[机构名称，是否包含下级，是否本级选中]
					if("true".equals(types[1])&&"true".equals(types[2])){
						cu_b0111_sb.append(" or "+CommSQL.subString("cu.b0111", 1, nodeid.length(), nodeid));
					}else if("true".equals(types[1])&&"false".equals(types[2])){
						cu_b0111_sb.append(" or "+CommSQL.subString2("cu.b0111", 1, nodeid.length(), nodeid));
					}else if("false".equals(types[1])&&"true".equals(types[2])){
						cu_b0111_sb.append(" or cu.b0111 = '"+nodeid+"' ");
					}
				}
				if(jsonObject.size()>0){
					cu_b0111_sb.append(" ) ");
				}
	        }





		      String userid = SysManagerUtils.getUserId();
			  sql=" select * from "+viewnametb+",A01SEARCHTEMP " +
			  " where "+viewnametb+".a01a0000=A01SEARCHTEMP.a0000  " +
			  " and A01SEARCHTEMP.sessionid='"+sessionid+"' " + " and "
			  +viewnametb+".a01a0000 in (select a02.a0000 from a02 where a02.A0201B in "
					+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userid+"' "+cu_b0111_sb+") and a0281='true') " +
			  " order by A01SEARCHTEMP.sort asc  " + " ";


			  // }

			// String sql="select * from "+viewnametb+" ";//移除在导出，移除无效
			//this.request.getSession().setAttribute("allSelect", "");
			StopWatch w = new StopWatch();
			w.start();
			if (this.request.getSession().getAttribute("pageSize") != null
					&& !this.request.getSession().getAttribute("pageSize").equals("")) {
				int pageSize = Integer.parseInt(this.request.getSession().getAttribute("pageSize").toString()); // 判断是否设置了自定义每页数量，如果设置了使用自定义
				limit = pageSize;
			}
			this.pageQuery(sql, "SQL", start, limit);
			w.stop();

			PhotosUtil.saveLog("信息查询总耗时：" + w.elapsedTime() + "\r\n执行的sql:" + sql + "\r\n\r\n");
			return EventRtnType.SPE_SUCCESS;
		}

		Object attribute = this.request.getSession().getAttribute("listAddGroupSession");
		String sid = this.request.getSession().getId();
		if (attribute != null && !"".equals(attribute + "")) {
			String sql = this.getPageElement("sql").getValue();
			String startSql = "select temp.A0000 as a0000,A0101 as a0101,A0184 as a0184,A0192A as a0192a,A0192 as a0192,A0104 as a0104,A0107 as a0107,A0117 as a0117,A0111A as a0111,A0114A as a0114,A0134 as a0134,A0140 as a0140,QRZXL as qrzxl,QRZXLXX as qrzxlxx,QRZXW as qrzxw,QRZXWXX as qrzxwxx,ZZXL as zzxl,ZZXLXX as zzxlxx,ZZXW as zzxw,ZZXWXX as zzxwxx,A0192F as a0192f,A0221 as a0221,A0288 as a0288,A0192E as a0192e,A0192C as a0192c,A0120 as a0120,A0196 as a0196,A0122 as a0122,A0187A as a0187a,A0165 as a0165,A0160 as a0160,A0121 as a0121,A2949 as a2949,A0197 as a0197,A0128 as a0128,A0163 as a0163,ZGXL as zgxl,ZGXLXX as zgxlxx,ZGXW as zgxw,ZGXWXX as zgxwxx,(select b0101 from b01 where b0111=temp.A0195) as a0195 ";
			String allSql = startSql + ",'" + this.request.getSession().getId()
					+ "' sessionid from a01 temp where temp.a0000 in ( " + sql + ")";
			System.out.println("select count(temp1.a0000) from (" + allSql + ") temp1");
			Object count = HBUtil.getHBSession().createSQLQuery("select count(temp.a0000) from (" + allSql + ") temp")
					.uniqueResult();
			int totalcount = 0;
			if (count instanceof BigDecimal) {
				totalcount = ((BigDecimal) count).intValue();
			} else if (count instanceof BigInteger) {
				totalcount = ((BigInteger) count).intValue();
			}
			String resultOptSQL = "insert into A01SEARCHTEMP {sql} ";
			String aSql = "select temp.a0000,'" + this.request.getSession().getId()
					+ "' sessionid from A01 temp where temp.a0000 in ( " + sql + ")";
			this.deleteResult(sid);
			optResult(aSql, resultOptSQL);
			String userid = SysManagerUtils.getUserId();
			String ssql = "select temp.* ,(select b0101 from b01 where b0111=temp.A0195) from a01 temp where temp.a0000 in ( "
					+ sql + ")";
			String querySql = "select  a01.A0000 as a0000,A0101 as a0101,A0184 as a0184,A0192A as a0192a,A0192 as a0192,A0104 as a0104,A0107 as a0107,A0117 as a0117,A0111A as a0111,A0114A as a0114,A0134 as a0134,A0140 as a0140,QRZXL as qrzxl,QRZXLXX as qrzxlxx,QRZXW as qrzxw,QRZXWXX as qrzxwxx,ZZXL as zzxl,ZZXLXX as zzxlxx,ZZXW as zzxw,ZZXWXX as zzxwxx,A0192F as a0192f,A0221 as a0221,A0288 as a0288,A0192E as a0192e,A0192C as a0192c,A0120 as a0120,A0196 as a0196,A0122 as a0122,A0187A as a0187a,A0165 as a0165,A0160 as a0160,A0121 as a0121,A2949 as a2949,A0197 as a0197,A0128 as a0128,A0163 as a0163,ZGXL as zgxl,ZGXLXX as zgxlxx,ZGXW as zgxw,ZGXWXX as zgxwxx,(select b0101 from b01 where b0111=a01.A0195) as a0195  from ("
					+ ssql + ") a01 join (SELECT sort,a0000 from A01SEARCHTEMP where sessionid = '" + sid
					+ "') tp on a01.a0000 = tp.a0000 order by sort";
			// String querysql=allSql.replace(CommSQL.getComSQL(sid),
			// CommSQL.getComSQLQuery2(userid,sid));
			this.request.getSession().setAttribute("allSelect", querySql);
			this.request.getSession().setAttribute("ry_tj_zy", "conditionForCount@@" + querySql);// 统计专用
			this.request.getSession().setAttribute("listAdd", "listAdd");// 标识符
			StopWatch w = new StopWatch();
			w.start();
			this.pageQueryNoCount(allSql, "SQL", 0, 20, totalcount);
			w.stop();
			this.request.getSession().removeAttribute("listAddGroupSession");
			PhotosUtil.saveLog("信息查询总耗时：" + w.elapsedTime() + "\r\n执行的sql:" + allSql + "\r\n\r\n");
			return EventRtnType.SPE_SUCCESS;
		}
		if (!CustomQueryPageModel.QUERYLISTFLAG) {
			CustomQueryPageModel.LISTADDCCQLI = "-1";
			CustomQueryPageModel.LISTADDNAME = "无";
		} else {
			CustomQueryPageModel.QUERYLISTFLAG = false;
		}
		// 判断是否启用“集体内排序”，

		String isContain = this.getPageElement("isContain").getValue();
		String radioC = this.getPageElement("radioC").getValue();
		// 当queryType = 1，isContain = 0，并且，设置标志isA0225 = 1
		if (queryType.equals("1") && isContain != null && isContain.equals("0") && radioC.equals("1")) {
			this.request.getSession().setAttribute("isA0225", "1");
		}

		String temporarySort = "";
		Object ob = this.request.getSession().getAttribute("temporarySort");
		if (ob != null) {
			temporarySort = ob.toString();
		}

		String isA0225 = "";
		Object obisA0225 = this.request.getSession().getAttribute("isA0225");
		if (obisA0225 != null) {
			isA0225 = obisA0225.toString();
		}
		// 当前查询机构id
		String a0200 = this.getPageElement("checkedgroupid").getValue();

		String sort = request.getParameter("sort");// 要排序的列名--无需定义，ext自动后传
		String isPageTurning = this.request.getParameter("isPageTurning");
		String startCache = request.getParameter("startCache");
		if (!"true".equals(isPageTurning) && startCache != null) {// 不是翻页
																	// 且点击字段排序
			start = Integer.valueOf(startCache);
		}
		if (sort != null && sort.equals("a0101")) {
			sort = "a0102";
		}

		String dir = request.getParameter("dir");// 要排序的方式--无需定义，ext自动后传
		String orderby = "";
		if (sort != null && !"".equals(sort)) {
			orderby = "order by " + sort + " " + dir;
		}

		String userid = SysManagerUtils.getUserId();
		HttpSession session = request.getSession();
		if (session.getAttribute("pageSize") != null && !session.getAttribute("pageSize").equals("")) {
			int pageSize = Integer.parseInt(session.getAttribute("pageSize").toString()); // 判断是否设置了自定义每页数量，如果设置了使用自定义
			limit = pageSize;
		}
		// int maxRow = 50000;

		this.getPageElement("checkList").setValue("");
		queryType = (String) this.request.getSession().getAttribute("queryType");
		String sql = this.getPageElement("sql").getValue();
		// 替换当前session 查询方案中保存的sql还是以前的session
		sql = sql.replaceAll("(select  a01.a0000,')(.*)(' sessionid from A01 a01)", CommSQL.getComSQL(sid));
		// sql.replace(CommSQL.getComSQL(sid),"");

		// String radioC=this.getPageElement("radioC").getValue();
		String resultOptSQL = "";
		String tem_sql = "";
		String tem_sql2 = "";

		if ("true".equals(isPageTurning) && startCache == null) {// 说明是翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页
			String str6 = SysfunctionManager.getCurrentSysfunction().getFunctionid();
			String str7 = this.getCueEventElementName();
			SQLInfo localSQLInfo = (SQLInfo) this.request.getSession().getAttribute(str6 + "@" + str7);
			if (localSQLInfo != null) {
				StopWatch w = new StopWatch();
				w.start();
				// CommonQueryBS.systemOut("sql---:"+querysql);
				this.pageQuery(localSQLInfo.getSql(), "SQL", start, limit);
				w.stop();

				PhotosUtil.saveLog("信息查询总耗时：" + w.elapsedTime() + "\r\n执行的sql:" + localSQLInfo.getSql() + "\r\n\r\n");
				return EventRtnType.SPE_SUCCESS;
			}
		}

		// 结果中查询
		if ("2".equals(radioC)) {
			if (DBUtil.getDBType() == DBType.ORACLE) {
				tem_sql = "insert into cdttttt (select a0000,sessionid from  A01SEARCHTEMP where sessionid='" + sid
						+ "' minus {sql})";
				// tem_sql = "create or replace view ttttt as (select
				// a0000,sessionid from A01SEARCHTEMP where sessionid='"+sid+"'
				// minus {sql})";
				resultOptSQL = "delete from A01SEARCHTEMP where a0000 in (select a0000 from cdttttt ) and sessionid='"
						+ sid + "'";
				// resultOptSQL = "delete from A01SEARCHTEMP where a0000 in
				// (select a0000 from (select a0000 from A01SEARCHTEMP where
				// sessionid='"+sid+"' minus select a0000 from cdttttt )) and
				// sessionid='"+sid+"'";
			} else {
				resultOptSQL = "delete from A01SEARCHTEMP where a0000 not in (select a0000 from ({sql}) ax ) ";
			}

			// 追加查询
		} else if ("3".equals(radioC)) {
			tem_sql = "update A01SEARCHTEMP set sessionid='" + sid + "temp' where sessionid='" + sid + "'";
			resultOptSQL = "insert into A01SEARCHTEMP {sql} ";
			tem_sql2 = "delete from A01SEARCHTEMP  where sessionid='" + sid + "temp'";
		} else if ("4".equals(radioC)) {// 排查查询
			if (DBUtil.getDBType() == DBType.ORACLE) {
				tem_sql = "insert into cdttttt  {sql}";
				resultOptSQL = "delete from A01SEARCHTEMP where a0000 in (select a0000 from cdttttt ) and sessionid='"
						+ sid + "'";
				// resultOptSQL = "delete from A01SEARCHTEMP where exists ({sql}
				// and A01SEARCHTEMP.a0000=a01.a0000) and sessionid='"+sid+"'";
			} else {
				resultOptSQL = "delete from A01SEARCHTEMP where a0000 in (select a0000 from ({sql}) ax ) ";
			}

		} else {
			if (StringUtil.isEmpty(temporarySort)) {
				this.deleteResult(sid);
			}

			resultOptSQL = "insert into A01SEARCHTEMP {sql}";
		}
		// 管理类别 a0165
		String a0165 = PrivilegeManager.getInstance().getCueLoginUser().getRate();
		if (a0165 != null && !"".equals(a0165)) {
			sql = sql + " and a01.a0165 not in (" + a0165 + ")";
		}
		String iforder = (String) this.getPageElement("orderqueryhidden").getValue();
		/*
		 * String a0201bid = (String)this.getPageElement("a0201b").getValue();
		 * String[] jgid = a0201bid.split("\\."); //yinl a02条件增加分区 2017.08.02
		 * String v0201bs = (jgid.length >=
		 * AppConfig.PARTITION_FRAGMENT)?" and a02.V0201B='"+jgid[AppConfig.
		 * PARTITION_FRAGMENT-1]+"' ":"";
		 */
		// 修复两个排序都选时报错的bug
		// iforder = (iforder.equals("1") &&
		// this.getPageElement("paixu").getValue().equals("1")) ? "0" : iforder;
		String tabType = this.getPageElement("tabn").getValue().toString();
		if (!tabType.equals("tab3")) {
			String personViewSQL = " select 1 from COMPETENCE_USERPERSON cu ";
			// 人员查看权限

			// 某些情况下影响效率，且目前系统COMPETENCE_USERPERSON表已废弃，所以注释此段代码
			// sql=sql+ " and not exists ("+personViewSQL+" where
			// cu.a0000=a01.a0000 and
			// cu.userid='"+SysManagerUtils.getUserId()+"') ";

		} else {// 按机构查询
			String b01String = this.getPageElement("orgjson").getValue();
			StringBuffer sb = new StringBuffer();
			if (!"".equals(b01String) && b01String != null && !"{}".equals(b01String)) {
				// 选择机构
				JSONObject jsonObject = JSONObject.fromObject(b01String);
				sb.append(" and a01.a0000 in (select a0000 from a02, competence_userdept cu where  "
						+ " a02.A0201B = cu.b0111 AND cu.userid = '" + SysManagerUtils.getUserId() + "' ");

				Iterator<String> it = jsonObject.keys();
				if (it.hasNext()) {
					sb.append(" and a02.a0281='true' ");
				}
				sb.append(" and (1=2 ");
				// 遍历jsonObject数据，添加到Map对象
				while (it.hasNext()) {
					String nodeid = it.next();
					String operators = (String) jsonObject.get(nodeid);
					String[] types = operators.split(":");
					if ("true".equals(types[1]) && "true".equals(types[2])) {
						sb.append(" or cu.b0111 like '" + nodeid + "%' ");
					} else if ("true".equals(types[1]) && "false".equals(types[2])) {
						sb.append("or cu.b0111 like '" + nodeid + ".%'");
					} else if ("false".equals(types[1]) && "true".equals(types[2])) {
						sb.append("or cu.b0111 = '" + nodeid + "'");
					}
				}
				sb.append(" ) ");
				sb.append(" ) and 1=1 ");

			} else {
				// sb.append(" and 1=2 ");
				sb.append(" and a01.a0000 in (select a0000 from a02, competence_userdept cu where "
						+ " a02.A0201B = cu.b0111 and a02.a0281='true' AND cu.userid = '" + SysManagerUtils.getUserId()
						+ "' ");
				sb.append(" ) ");
			}
			sql = sql + sb.toString();
		}
		String querysql = "";
		int totalcount = 0;
		if (sql.equals("")) {
			return EventRtnType.NORMAL_SUCCESS;
		}
		String replace = null;
		String selectCount =  " select count(2) from A01 a01 ";
		String replaceSql = "select   a01.a0000, a01.a1701 from A01 a01";
		if(sql.contains(CommSQL.getComSQL(sid))) {
			replace = sql.replace(CommSQL.getComSQL(sid), selectCount);
		}else if (sql.contains(replaceSql)) {
			replace = sql.replace(replaceSql, selectCount);
		}else {
			replace = selectCount;
		}
		Object count = HBUtil.getHBSession().createSQLQuery(replace).uniqueResult();
//		Object count = HBUtil.getHBSession()
//				.createSQLQuery(sql.replace(CommSQL.getComSQL(sid), " select count(2) from A01 a01 ")).uniqueResult();

		if (count instanceof BigDecimal) {
			totalcount = ((BigDecimal) count).intValue();
		} else if (count instanceof BigInteger) {
			totalcount = ((BigInteger) count).intValue();
		} else if (count instanceof Long) {
			totalcount = ((Long) count).intValue();
		}
		if (totalcount > CommSQL.MAXROW) {
			if (!"1".equals(radioC)) {
				throw new AppException("单次查询结果超过" + CommSQL.MAXROW + "行限制，无法进行集合操作！");
			}

			querysql = sql.replace(CommSQL.getComSQL(sid), CommSQL.getComSQLQuery2(userid, sid));

			this.request.getSession().setAttribute("allSelect", querysql);
			this.request.getSession().setAttribute("ry_tj_zy", "conditionForCount@@" + querysql);// 统计专用
			StopWatch w = new StopWatch();
			w.start();
			// CommonQueryBS.systemOut("sql---:"+querysql);
			this.pageQueryNoCount(querysql, "SQL", start, limit, totalcount);
			w.stop();

			PhotosUtil.saveLog("信息查询总耗时：" + w.elapsedTime() + "\r\n执行的sql:" + querysql + "\r\n\r\n");
			return EventRtnType.SPE_SUCCESS;
		}

		if (StringUtil.isEmpty(temporarySort)) { // temporarySort为空，不是临时排序
			if (("2".equals(radioC) || "4".equals(radioC)) && DBUtil.getDBType() == DBType.ORACLE) {
				this.optResult(sql, tem_sql, resultOptSQL);
				// this.optResult(sql, resultOptSQL);
			} else if ("3".equals(radioC)) {
				String qsql = sql.replace(CommSQL.getComSQL(sid), " select a0000 from A01 a01 ")
						+ "union all select a0000 from A01SEARCHTEMP where sessionid = '" + sid + "temp'";
				qsql = CommSQL.getComSQL(sid) + " where a0000 in (" + qsql + ")";
				// sql = sql + " or a01.a0000 in (select a0000 from
				// A01SEARCHTEMP where sessionid='"+sid+"temp')";
				qsql = qsql + ("".equals(orderby) ? CommSQL.OrderByF(userid, isA0225, a0200,qsql) : orderby);
				this.optResult(qsql, tem_sql, tem_sql2, resultOptSQL);
			} else {
				// this.optResult(sql+("".equals(orderby)?CommSQL.OrderBy(userid):orderby),
				// resultOptSQL);
				this.optResult(sql + ("".equals(orderby) ? CommSQL.OrderByF(userid, isA0225, a0200,"") : orderby),
						resultOptSQL);
			}
		}
		querysql = CommSQL.getComSQLQuery(userid, sid) + " /*" + UUID.randomUUID() + "*/" + CommSQL.OrderBy(sort+" "+dir);

		// this.getExecuteSG().addExecuteCode("document.getElementById('temporarySort').value
		// = '';");
		// this.getPageElement("temporarySort").setValue("");

		// 清空是否临时排序标记
		this.request.getSession().setAttribute("temporarySort", "");
		// 清空启用单机构集体内排序顺序标记
		this.request.getSession().setAttribute("isA0225", "");

		// }else{

		// querysql = CommSQL.getComSQLQuery(userid,sid)+" where a01.a0000 in
		// (select a0000 from A01SEARCHTEMP where sessionid='"+sid+"')
		// /*"+UUID.randomUUID()+"*/"
		// +("".equals(orderby)?CommSQL.OrderBy(userid):orderby) ;

		/*
		 * int index = querysql.indexOf("from");
		 *
		 * StringBuffer sqlNew = new StringBuffer(querysql);
		 * sqlNew.insert(index-1,
		 * ",(select sort from A01SEARCHTEMP tmp where sessionid = '"
		 * +sid+"' and tmp.a0000 = a01.a0000) as sort "); querysql =
		 * sqlNew.toString();
		 *
		 * int indexOd = querysql.indexOf("order by"); sqlNew.insert(indexOd+8,
		 * " sort,");
		 *
		 * querysql = sqlNew.toString(); }
		 */

		// Object o =
		// this.request.getSession().getAttribute("queryConditionsCQ");
		// 不是固定条件查询并且排序标志未被取消,则添加排序
		// sql = sql + CommSQL.OrderBy("a01.a0148");

		// 调用同步查询交易，使分页查询及数据显示查询并行进行，用以减少查询时间。
		// this.request.getSession().setAttribute("allSelect", sql);

		if (!"1".equals(radioC)) {
			count = HBUtil.getHBSession()
					.createSQLQuery("select count(a0000) from A01SEARCHTEMP where sessionid='" + sid + "'")
					.uniqueResult();
			if (count instanceof BigDecimal) {
				totalcount = ((BigDecimal) count).intValue();
			} else if (count instanceof BigInteger) {
				totalcount = ((BigInteger) count).intValue();
			}

		}

		// if(StringUtil.isEmpty(temporarySort)){ //temporarySort为空，不是临时排序

		this.request.getSession().setAttribute("allSelect", querysql);
		this.request.getSession().setAttribute("ry_tj_zy", "tempForCount@@" + querysql);// 统计专用
		// }

		StopWatch w = new StopWatch();
		w.start();
		CommonQueryBS.systemOut("sql----CustomQueryPageModel-----:" + querysql);
		this.pageQueryNoCount(querysql, "SQL", start, limit, totalcount);
		w.stop();

		PhotosUtil.saveLog("信息查询总耗时：" + w.elapsedTime() + "\r\n执行的sql:" + querysql + "\r\n\r\n");
		return EventRtnType.SPE_SUCCESS;

	}

	@PageEvent("initListAddGroupFlag")
	public int doMemberQueryAdd() throws RadowException, AppException {
		Object attribute = this.request.getSession().getAttribute("listAddGroupSession");
		if (attribute != null || !"".equals(attribute + "")) {
			this.request.getSession().removeAttribute("listAddGroupSession");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	public void deleteResult(String sid) {
		try {
			HBUtil.executeUpdate("delete from A01SEARCHTEMP where sessionid='" + sid + "'");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// mySQL: 将查询的数据结果，存到A01SEARCHTEMP表
	private void optResult(String sql, String resultOptSQL) {
		try {
			String sid = this.request.getSession().getId();

			if (DBUtil.getDBType() == DBType.ORACLE) {
				sql = "select tmp.a0000,'" + sid + "' sessionid,rownum i from ( " + sql + " ) tmp";

			} else { // mysql
				sql = "select tmp.a0000,'" + sid + "' sessionid,(@i:=@i+1) as i from (select @i:=0) as it,( " + sql
						+ " ) tmp";
			}

			// sql = sql.replace("sort,", "");

			HBUtil.executeUpdate(resultOptSQL.replace("{sql}", sql));
		} catch (AppException e) {
			System.out.println(resultOptSQL.replace("{sql}", sql));
			e.printStackTrace();
		}
	}

	// oracle: 将查询的数据结果，存到A01SEARCHTEMP表
	private void optResult(String sql, String tmp_sql, String resultOptSQL) {
		Connection con = null;
		try {
			con = HBUtil.getHBSession().connection();
			con.setAutoCommit(false);
			Statement stat = con.createStatement();
			stat.executeUpdate(tmp_sql.replace("{sql}", sql));
			stat.executeUpdate(resultOptSQL);
			con.commit();

		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.out.println(resultOptSQL.replace("{sql}", sql));
			e.printStackTrace();
		}
	}

	private void optResult(String sql, String tmp_sql, String tem_sql2, String resultOptSQL) {
		String sid = this.request.getSession().getId();
		Connection con = null;
		try {
			con = HBUtil.getHBSession().connection();
			con.setAutoCommit(false);
			Statement stat = con.createStatement();
			stat.executeUpdate(tmp_sql);

			if (DBUtil.getDBType() == DBType.ORACLE) {
				sql = "select tmp.a0000,'" + sid + "' sessionid,rownum i from ( " + sql + ") tmp";
			} else { // mysql
				sql = "select tmp.a0000,'" + sid + "' sessionid,(@i:=@i+1) as i from (select @i:=0) as it,( " + sql
						+ " ) tmp";
			}
			stat.executeUpdate(resultOptSQL.replace("{sql}", sql));
			stat.executeUpdate(tem_sql2);
			con.commit();

		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.out.println(resultOptSQL.replace("{sql}", sql));
			e.printStackTrace();
		}
	}

	@Override
	public int doInit() throws RadowException {
		this.getExecuteSG().addExecuteCode("init()");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("open")
	@Synchronous(true)
	@NoRequiredValidate
	public int open() throws AppException, RadowException {
		String value = this.request.getParameter("type");
		request.getSession().setAttribute("Isearch", value);
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("queryByName")
	public int queryByName(String name) throws RadowException, AppException {

		String name2 = name;

		if (name != null) {
			name2 = name.toUpperCase();
		}
		/*
		 * String sql = this.getPageElement("sql").getValue();
		 *
		 * sql = sql + " and a01.A0101 like '%"+name+"%'";
		 */

		// 将name大写化
		// name = name.toUpperCase();

		// 改为全库查询
		String sid = this.request.getSession().getId();
		String userID = SysManagerUtils.getUserId();
		String sql = "select a0000 from a01 where a01.a0000  in "
				+ "(select a02.a0000 from a02 where a02.a0281='true' and " // 职务信息中要输出状态
				+ " a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '" + userID + "'))";
		sql = sql + " and (a01.a0184 = '" + name + "' or a01.a0101 like '%" + name + "%' or a01.A0102 like '%" + name2
				+ "%') ";

		// sql = sql + " and (a01.a0101 like '"+name+"%' or a01.A0102 like
		// '"+name+"%') ";

		sql = sql + " union all "
				+ "select a0000 from a01 where not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1') )   "
				+ " and a01.status!='4' " + " and (a01.a0184 = '" + name + "' or a01.a0101 like '%" + name
				+ "%' or a01.A0102 like '%" + name2 + "%') ";

		this.getPageElement("sql")
				.setValue(CommSQL.getComSQL(sid) + " where a01.a0000  in ( select a0000 from(" + sql + ") cc) ");
		// this.getPageElement("a0201b").setValue(id);
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 按姓名查询，传递人员IDs
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String listStr) throws RadowException {
		// System.out.println(listStr);
		String sid = this.request.getSession().getId();
		StringBuffer sql = new StringBuffer();
		sql.append(CommSQL.getComSQL(sid) + " where a0000 in ('-1'");
		if (listStr != null && listStr.length() > 2) {
			listStr = listStr.substring(1, listStr.length() - 1);
			List<String> list = Arrays.asList(listStr.split(","));
			for (String id : list) {
				sql.append(",'" + id.trim() + "'");
			}
			sql.append(")");
			this.getPageElement("sql").setValue(sql.toString());

			String queryType = (String) request.getSession().getAttribute("queryType");

			// 写死列表
			String tableType = "1";
			if ("1".equals(tableType)) {
				if ("define".equals(queryType)) {
					this.getExecuteSG().addExecuteCode("changeField()");
					request.getSession().removeAttribute("queryType");
				} else {
					this.setNextEventName("peopleInfoGrid.dogridquery");
				}
			}
			if ("2".equals(tableType)) {
				this.getExecuteSG().addExecuteCode("datashow()");
				this.setNextEventName("peopleInfoGrid.dogridquery");
			}
			if ("3".equals(tableType)) {
				this.getExecuteSG().addExecuteCode("picshow()");
				this.setNextEventName("peopleInfoGrid.dogridquery");
			}
			return EventRtnType.NORMAL_SUCCESS;
		} else {
			this.setMainMessage("无法查询到该人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}

	@PageEvent("saveA01_config")
	public int saveA01_config() throws RadowException {

		String jsonp = this.request.getParameter("jsonp");
		// System.out.println(jsonp);
		if (jsonp != null && !"".equals(jsonp)) {
			try {
				CommSQL.updateA01_config(jsonp, SysManagerUtils.getUserId());
			} catch (AppException e) {
				this.setMainMessage("操作失败：" + e.getDetailMessage());
				e.printStackTrace();
				return EventRtnType.FAILD;
			}
		}
		StringBuilder cm = new StringBuilder("[");
		StringBuilder dm = new StringBuilder("[");
		cm.append("new Ext.grid.RowNumberer({locked:true,header:'',width:23}), "
				+ "{locked:true,header: \"<div class='x-grid3-check-col-td'><div class='x-grid3-check-col' alowCheck='true' id='selectall_peopleInfoGrid_personcheck' onclick='odin.selectAllFuncForE3(\\\"peopleInfoGrid\\\",this,\\\"personcheck\\\");getCheckList(\\\"peopleInfoGrid\\\",\\\"personcheck\\\",this);'></div></div>\","
				+ "hidden:false,align:'center', width: 40, sortable: false, enterAutoAddRow:false,"
				+ "renderer:function(value, params, record,rowIndex,colIndex,ds){var rtn = '';params.css=' x-grid3-check-col-td';if(value==true || value=='true'){rtn=\"<div class='x-grid3-check-col-on' alowCheck='true' onclick='odin.accCheckedForE3(this,\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");getCheckList2(\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");'></div>\";}else{rtn=\"<div class='x-grid3-check-col' alowCheck='true' onclick='odin.accCheckedForE3(this,\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");getCheckList2(\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");'></div>\";}return odin.renderEdit(rtn,params,record,rowIndex,colIndex,ds);}, "
				+ "editable:false,  hideable: false, dataIndex: 'personcheck', editor: new Ext.form.Checkbox({inputType:'',fireKey:odin.doAccSpecialkey})},");
		dm.append("{name: 'personcheck'},{name: 'a0000'},");
		int i = 0;
		for (Object[] o : CommSQL.A01_CONFIG_LIST.get(SysManagerUtils.getUserId())) {
			String name = o[0].toString();
			String editor = o[5].toString().toLowerCase();
			String header = o[2].toString();
			String desc = o[6].toString();
			String width = o[3].toString();
			String codeType = o[4] == null ? "" : o[4].toString();
			String renderer = o[7] == null ? "" : o[7].toString();
			String align = o[9].toString();
			if (!"".equals(renderer)) {
				// renderer = "var v="+renderer+"(a,b,c,d,e,f);";
				renderer = "function(a,b,c,d,e,f){var v=" + renderer
						+ "(a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f,\"\")}";
			} else {
				renderer = "function(v,b,c,d,e,f){odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f,\"\")}";
			}
			boolean locked = false;
			if ("a0101".equals(name)) {
				locked = true;
			}
			// i++;
			if (!"a0000".equals(name)) {
				if ("text".equals(editor)) {
					cm.append("{locked:" + locked + ",header: \"" + header + "\",hidden:false,align:'" + align
							+ "', width: " + width + ", sortable: true," + " enterAutoAddRow:false,renderer:" + renderer
							+ ", editable:false, " + " dataIndex: '" + name
							+ "', editor: new Ext.form.TextField({allowBlank:true ,fireKey:odin.doAccSpecialkey})},");

					dm.append("{name: '" + name + "'},");

				} else if ("select".equals(editor)) {
					cm.append("{locked:" + locked + ",header: \"" + header + "\",hidden:false,align:'" + align
							+ "', width: " + width + ", sortable: true, "
							+ "enterAutoAddRow:false,renderer:function(a,b,c,d,e,f){var v=odin.doGridSelectColRender('peopleInfoGrid','"
							+ name
							+ "',a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f,\"\")}, editable:false,  dataIndex: '"
							+ name + "', "
							+ "editor: new Ext.form.ComboBox({store: new Ext.data.SimpleStore({fields: ['key', 'value'],data:"
							+ CodeType2js.getCodeTypeJS(codeType) + ","
							+ "createFilterFn:odin.createFilterFn}),displayField:'value',typeAhead: false,mode: 'local',triggerAction: 'all',"
							+ "editable:true,selectOnFocus:true,hideTrigger:false,expand:function(){odin.setListWidth(this);Ext.form.ComboBox.prototype.expand.call(this);},allowBlank:true ,fireKey:odin.doAccSpecialkey})},");

					dm.append("{name: '" + name + "'},");
				}

			}

		}
		cm.deleteCharAt(cm.length() - 1);
		cm.append("]");
		dm.deleteCharAt(dm.length() - 1);
		dm.append("]");
		this.getExecuteSG().addExecuteCode(cm.toString() + "{split}" + dm.toString());
		// this.reloadPage();
		this.setMainMessage("保存成功");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("getCheckList")
	public int getCheckList() throws RadowException, AppException {

		String listString = null;
		int cnt = 0;
		List<HashMap<String, Object>> gdlist = new ArrayList<HashMap<String, Object>>();
		PageElement pe = this.getPageElement("peopleInfoGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		String a0000 = "";
		String a0101 = "";
		for (HashMap<String, Object> hm : list) {

			if (!"".equals(hm.get("personcheck")) && (Boolean) hm.get("personcheck")) {
				listString = listString + "@|" + hm.get("a0000") + "|";
				++cnt;

				if (cnt == 1) {
					a0000 = hm.get("a0000").toString();
					a0101 = hm.get("a0101").toString();
				}
			}

		}
		if (!"".equals(listString) && listString != null)
			listString = listString.substring(listString.indexOf("@") + 1, listString.length());
		this.getPageElement("checkList").setValue(listString);
		if (true) {
			this.getPageElement("a0000s").setValue(a0000);
			String tabID = this.getPageElement("showTabID").getValue();
			if (tabID != null) {
				if ("".equals(tabID) || "showTab1".equals(tabID)) {
					this.setNextEventName("zwxx.dogridquery");
				}
				if ("showTab2".equals(tabID)) {
					this.setNextEventName("zhuanyexx.dogridquery");
				}
				if ("showTab3".equals(tabID)) {
					this.setNextEventName("xuelixx.dogridquery");
				}
				if ("showTab4".equals(tabID)) {
					this.setNextEventName("peixunxx.dogridquery");
				}
				if ("showTab5".equals(tabID)) {
					this.setNextEventName("jiangchengxx.dogridquery");
				}
				if ("showTab6".equals(tabID)) {
					this.setNextEventName("kaohexx.dogridquery");
				}
				if ("showTab7".equals(tabID)) {
					this.setNextEventName("jinruxx.dogridquery");
				}
				if ("showTab8".equals(tabID)) {
					this.setNextEventName("tuichuxx.dogridquery");
				}
				if ("showTab9".equals(tabID)) {
					this.setNextEventName("jiatingxx.dogridquery");
				}
				if ("showTab12".equals(tabID)) {
					this.setNextEventName("nirenxx.dogridquery");
				}
				if ("showTab20".equals(tabID)) {
					this.setNextEventName("beizhuxx.dogridquery");
				}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("zwxx.dogridquery")
	public int zwxxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();

		String sql = "SELECT a0201A a0201a,a0201B a0201b,CASE A0201D WHEN '1' THEN '是' WHEN '0' THEN '否' END a0201d,a0201E a0201e,a0215A a0215a,a0219,"
				+ "a0221,a0222,a0223,a0225,a0229,a0243,a0245,a0247,a0251,"
				+ "CASE A0251B WHEN '1' THEN '是' WHEN '0' THEN '否' END a0251b,a0255,a0265,a0267,a0272,a0281 "
				+ "FROM A02 WHERE A0000 = '";
		if (a0000s != null && !"".equals(a0000s)) {
			sql = sql + a0000s + "' ORDER BY A0000";
		} else {
			sql = sql + "1' ORDER BY A0000";
		}

		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	// 打印任免表
	@PageEvent("printRmb")
	@Synchronous
	public int printRmb() throws Exception {
		// 打印机的选择判断
		HttpSession session = request.getSession();
		String printer = (String) session.getAttribute("Printer");
		if (((String) session.getAttribute("PrintNum").toString()).equals("")) {
			this.setMainMessage("请设置打印份数！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		int printNum = Integer.parseInt((String) session.getAttribute("PrintNum"));
		if (printer.equals("")) {
			this.setMainMessage("请先去打印机设置中选择一种打印机！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (printNum <= 0) {
			this.setMainMessage("打印份数必须大于0并且不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}

		HBSession sess = HBUtil.getHBSession();
		String tpid = "";
		String type = "1";
		if (type.equals("1")) {
			// 干部任免审批表
			tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
		} else if (type.equals("2")) {
			// 公务员登记表
			tpid = "5d3cef0f0d8b430cb35b2ac2cb3bf927";
		} else if (type.equals("3")) {
			// 参照登记表
			tpid = "0f6e25ab-ee0a-4b23-b52d-7c6774dfc462";
		} else if (type.equals("4")) {
			// 公务员登记备案表
			tpid = "3de1c725-d71b-476a-b87c-6c8d2184";
		} else if (type.equals("5")) {
			// 参照登记备案表
			tpid = "40e9b81c-5a53-445f-a027-6e00a9f6";
		} else if (type.equals("6")) {
			// 年度考核登记表
			tpid = "a43d8c50-400d-42fe-9e0d-5665ed0b0508";
		} else if (type.equals("7")) {
			// 奖励审批表
			tpid = "04f59673-9c3a-4d9c-b016-a5b789d636e2";
		} else if (type.equals("8")) {
			// 干部名册（一人一行）
			tpid = "47b1011d70f34aefb89365bbfce";
		} else if (type.equals("9")) {
			// 干部名册（按机构分组）
			tpid = "eebdefc2-4d67-4452-a973";
		} else if (type.equals("10")) {
			// 公务员录用表
			tpid = "3de527c0-ea23-42c4-a66f";
		} else if (type.equals("11")) {
			// 公务员调任审批表
			tpid = "9e7e1226-6fa1-46a1-8270";
		}

		String ids = "";
		String idss = (String) request.getSession().getAttribute("personidy");
		if (idss != null && !"".equals(idss)) {
			// 勾选人员id
			ids = idss;
		} else {
			// 未勾选人员，全选
			ids = (String) request.getSession().getAttribute("personidall");
		}
		int length = ids.split("@").length;
		List<String> result = new ArrayList<String>();
		if (length > 200
				&& "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
						.contains(tpid)) {
			this.setMainMessage("请选择200人进行处理!");
			return EventRtnType.NORMAL_SUCCESS;
		} else {
			String[] personids = ids.split("@");
			List<String> list2 = new ArrayList<String>();
			for (int j = 0; j < personids.length; j++) {
				String a0000 = personids[j].replace("|", "");
				list2.add(a0000);
			}
			if (tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184") || tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
					|| tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")
					|| tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")) {
				// 判断所有人是否属于同一法人单位下，针对公务员登记备案表和参公登记备案表
				if (tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184") || tpid.equals("" + "")
						|| tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")
						|| tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")) {
					// 公务员
					for (int i = 0; i < list2.size(); i++) {
						List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '" + list2.get(i) + "'")
								.list();
						if (pertype.size() > 0) {
							String a0160 = pertype.get(0) + "";
							if (!a0160.equals("1")) {// !
								this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " + " '提示', "
										+ " '所选人员包含非公务员，是否继续导出？', " + " function (btn){ " + "	if(btn=='yes'){ "
										+ "		radow.doEvent('exp','" + type + "'); " + "	} " + "} " + ");");
								return EventRtnType.NORMAL_SUCCESS;
							}
						} else {
							this.setMainMessage("数据异常！");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				} else {
					// 非公务员
					for (int i = 0; i < list2.size(); i++) {
						List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '" + list2.get(i) + "'")
								.list();
						if (pertype.size() > 0) {
							String a0160 = pertype.get(0) + "";
							if (a0160.equals("1")) {
								this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " + " '提示', "
										+ " '所选人员包含公务员，是否继续导出？', " + " function (btn){ " + "	if(btn=='yes'){ "
										+ "		radow.doEvent('exp','" + type + "'); " + "	} " + "} " + ");");
								return EventRtnType.NORMAL_SUCCESS;
							}
						} else {
							this.setMainMessage("数据异常！");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}
				// 获取机构树结构
				List<Object[]> listres = sess.createSQLQuery("select b0111,b0194,b0121,B0101 from b01").list();

				Map<String, TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
				for (Object[] treedata : listres) {
					TreeNode rootnode = genNode(treedata);
					nodemap.put(rootnode.getId(), rootnode);
				}

				for (int i = 0; i < list2.size(); i++) {
					String sql = "select a0201b from a02 where a0000 = '" + list2.get(i) + "' and a0255 = '1'";
					List list3 = sess.createSQLQuery(sql).list();
					List<String> compare = new ArrayList<String>();
					for (int j = 0; j < list3.size(); j++) {
						String a0201b = list3.get(j).toString();
						while (true) {
							TreeNode cn = nodemap.get(a0201b);
							TreeNode n = nodemap.get(cn.getParentid());
							if (n == null) {
								a0201b = "-1";
								// throw new RadowException("机构读取异常");
							} else {
								a0201b = n.getId();
							}
							// if(n.getText() == null||"".equals(n.getText())){
							// throw new RadowException("机构读取异常");
							// }
							if (!"1".equals(cn.getText())) {// 不是法人单位,继续往上找机构
								continue;
							} else {// 法人单位
								compare.add(cn.getId());
								break;
							}
						}
					}
					if (i == 0) {
						result = compare;
					} else {
						result = receiveCollectionList(result, compare);
					}
					if (result.size() == 0) {
						// 如果所选人员没有相同法人单位则提示
						this.setMainMessage("所选人员不属于同一法人单位");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			List<String> wordPaths = null;
			try {
				wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2, tpid, "word", ids, result, personids);

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
							// printFile(wordPaths.get(0) + fs.getName(),
							// printer);
							String path = wordPaths.get(0) + fs.getName();
							String rootPath = PhotosUtil.getRootPath();
							path = path.replace(rootPath, "").replace("\\", "/");
							this.getExecuteSG().addExecuteCode("printStart('" + path + "');");
						}
					}
				}

			} catch (AppException e) {
				e.printStackTrace();
			}
			this.setMainMessage("打印完毕");
			return EventRtnType.NORMAL_SUCCESS;
		}

	}

	private static TreeNode genNode(Object[] treedata) {
		TreeNode node = new TreeNode();
		node.setId(treedata[0].toString());
		node.setText(treedata[1].toString());
		node.setLink(treedata[3].toString());
		node.setLeaf(true);
		if (treedata[2] != null)
			node.setParentid(treedata[2].toString());
		node.setOrderno((short) 1);

		return node;
	}

	/**
	 * @方法描述：获取两个ArrayList的交集
	 * @param firstArrayList
	 *            第一个ArrayList
	 * @param secondArrayList
	 *            第二个ArrayList
	 * @return resultList 交集ArrayList
	 */
	public static List<String> receiveCollectionList(List<String> firstArrayList, List<String> secondArrayList) {
		List<String> resultList = new ArrayList<String>();
		LinkedList<String> result = new LinkedList<String>(firstArrayList);// 大集合用linkedlist
		HashSet<String> othHash = new HashSet<String>(secondArrayList);// 小集合用hashset
		Iterator<String> iter = result.iterator();// 采用Iterator迭代器进行数据的操作
		while (iter.hasNext()) {
			if (!othHash.contains(iter.next())) {
				iter.remove();
			}
		}
		resultList = new ArrayList<String>(result);
		return resultList;
	}

	@PageEvent("chuidrusesson")
	public int chuidrusesson() throws AppException, RadowException, SQLException {
		/*
		 * //把人员id存到session中在表册展示的地方用
		 * request.getSession().setAttribute("personidy", a0000);
		 */
		HBSession sess = HBUtil.getHBSession();

		String tableType = this.getPageElement("tableType").getValue(); // 人员展现方式，1：列表；2：小资料；3：照片
		String a0000 = "";
		String idsp = "";
		String idall = "";
		PageElement pe = this.getPageElement("peopleInfoGrid");

		if (pe != null) {
			List<HashMap<String, Object>> list = pe.getValueList();
			String name = "";

			Process p = null;
			for (int j = 0; j < list.size(); j++) {
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("personcheck");
				if (usercheck.equals(true)) {
					a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000", j).toString();
					idsp += "|" + a0000 + "|@";
				}
			}
			if (idsp != null && idsp.length() > 0) {
				idsp = idsp.substring(0, idsp.length() - 1);
				// 把人员id存到session中在表册展示的地方用
				request.getSession().setAttribute("personidy", idsp);
				request.getSession().removeAttribute("personidall");
			}
		}
		if ("".equals(idsp)) {
			List<HashMap<String, Object>> list = pe.getValueList();
			String name = "";

			for (int j = 0; j < list.size(); j++) {
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("personcheck");
				a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000", j).toString();
				idall += "|" + a0000 + "|@";
			}
			idall = idall.substring(0, idall.length() - 1);
			request.getSession().setAttribute("personidall", idall);
			request.getSession().removeAttribute("personidy");
		}

		String a0000s = "";
		if ("2".equals(tableType) || "3".equals(tableType)) {

			String picA0000s = this.getPageElement("picA0000s").getValue();

			if (picA0000s == null || "".equals(picA0000s.trim())) { // 没有勾选
																	// 则导出全部

			} else {
				idsp = "";
				a0000s = picA0000s.substring(0, picA0000s.length() - 1);
				String[] a0000_arr = a0000s.replaceAll("\\'", "").split(",");
				if (a0000_arr != null && a0000_arr.length > 0) {
					for (int j = 0; j < a0000_arr.length; j++) {
						String a0000New = a0000_arr[j];

						idsp = "|" + a0000New + "|@" + idsp;
					}
				}

				if (idsp != null && idsp.length() > 0) {
					idsp = idsp.substring(0, idsp.length() - 1);
					request.getSession().setAttribute("personidy", idsp);
					request.getSession().removeAttribute("personidall");
				}

			}
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("expword")
	public int expWord(String type) throws AppException, RadowException, IOException {
		HBSession sess = HBUtil.getHBSession();
		String tpid = "";
		if (type.equals("1")) {
			// 干部任免审批表
			tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
		} else if (type.equals("2")) {
			// 公务员登记表
			tpid = "5d3cef0f0d8b430cb35b2ac2cb3bf927";
		} else if (type.equals("3")) {
			// 参照登记表
			tpid = "0f6e25ab-ee0a-4b23-b52d-7c6774dfc462";
		} else if (type.equals("4")) {
			// 公务员登记备案表
			tpid = "3de1c725-d71b-476a-b87c-6c8d2184";
		} else if (type.equals("5")) {
			// 参照登记备案表
			tpid = "40e9b81c-5a53-445f-a027-6e00a9f6";
		} else if (type.equals("6")) {
			// 年度考核登记表
			tpid = "a43d8c50-400d-42fe-9e0d-5665ed0b0508";
		} else if (type.equals("7")) {
			// 奖励审批表
			tpid = "04f59673-9c3a-4d9c-b016-a5b789d636e2";
		} else if (type.equals("8")) {
			// 干部名册（一人一行）
			tpid = "47b1011d70f34aefb89365bbfce";
		} else if (type.equals("9")) {
			// 干部名册（按机构分组）
			tpid = "eebdefc2-4d67-4452-a973";
		} else if (type.equals("10")) {
			// 公务员录用表
			tpid = "3de527c0-ea23-42c4-a66f";
		} else if (type.equals("11")) {
			// 公务员调任审批表
			tpid = "9e7e1226-6fa1-46a1-8270";
		}else if(type.equals("15")) {
			tpid = "excel";
		}else if(type.equals("16")) {
			tpid = "47b1011d70f34aefb89365bbfce";
		}

		String ids = "";
		String idss = (String) request.getSession().getAttribute("personidy");
		if (idss != null && !"".equals(idss)) {
			// 勾选人员id
			ids = idss;
		} else {
			// 未勾选人员，全选
			ids = (String) request.getSession().getAttribute("personidall");
		}
		int length = ids.split("@").length;
		List<String> result = new ArrayList<String>();
		if (length > 200
				&& "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
						.contains(tpid)) {
			this.setMainMessage("请选择200人进行处理!");
			return EventRtnType.NORMAL_SUCCESS;
		} else {
			String[] personids = ids.split("@");
			List<String> list2 = new ArrayList<String>();
			for (int j = 0; j < personids.length; j++) {
				String a0000 = personids[j].replace("|", "");
				list2.add(a0000);
			}
			if (tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184") || tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
					|| tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")
					|| tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")) {
				// 判断所有人是否属于同一法人单位下，针对公务员登记备案表和参公登记备案表
				if (tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184") || tpid.equals("" + "")
						|| tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")
						|| tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")) {
					// 公务员
					for (int i = 0; i < list2.size(); i++) {
						List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '" + list2.get(i) + "'")
								.list();
						if (pertype.size() > 0) {
							String a0160 = pertype.get(0) + "";
							if (!a0160.equals("1")) {// !
								this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " + " '提示', "
										+ " '所选人员包含非公务员，是否继续导出？', " + " function (btn){ " + "	if(btn=='yes'){ "
										+ "		radow.doEvent('exp','" + type + "'); " + "	} " + "} " + ");");
								return EventRtnType.NORMAL_SUCCESS;
							}
						} else {
							this.setMainMessage("数据异常！");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				} else {
					// 非公务员
					for (int i = 0; i < list2.size(); i++) {
						List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '" + list2.get(i) + "'")
								.list();
						if (pertype.size() > 0) {
							String a0160 = pertype.get(0) + "";
							if (a0160.equals("1")) {
								this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " + " '提示', "
										+ " '所选人员包含公务员，是否继续导出？', " + " function (btn){ " + "	if(btn=='yes'){ "
										+ "		radow.doEvent('exp','" + type + "'); " + "	} " + "} " + ");");
								return EventRtnType.NORMAL_SUCCESS;
							}
						} else {
							this.setMainMessage("数据异常！");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}
				// 获取机构树结构
				List<Object[]> listres = sess.createSQLQuery("select b0111,b0194,b0121,B0101 from b01").list();

				Map<String, TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
				for (Object[] treedata : listres) {
					TreeNode rootnode = genNode(treedata);
					nodemap.put(rootnode.getId(), rootnode);
				}

				for (int i = 0; i < list2.size(); i++) {
					String sql = "select a0201b from a02 where a0000 = '" + list2.get(i) + "' and a0255 = '1'";
					List list3 = sess.createSQLQuery(sql).list();
					List<String> compare = new ArrayList<String>();
					for (int j = 0; j < list3.size(); j++) {
						String a0201b = list3.get(j).toString();
						while (true) {
							TreeNode cn = nodemap.get(a0201b);
							TreeNode n = nodemap.get(cn.getParentid());
							if (n == null) {
								a0201b = "-1";
								// throw new RadowException("机构读取异常");
							} else {
								a0201b = n.getId();
							}
							// if(n.getText() == null||"".equals(n.getText())){
							// throw new RadowException("机构读取异常");
							// }
							if (!"1".equals(cn.getText())) {// 不是法人单位,继续往上找机构
								continue;
							} else {// 法人单位
								compare.add(cn.getId());
								break;
							}
						}
					}
					if (i == 0) {
						result = compare;
					} else {
						result = receiveCollectionList(result, compare);
					}
					if (result.size() == 0) {
						// 如果所选人员没有相同法人单位则提示
						this.setMainMessage("所选人员不属于同一法人单位");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			List<String> wordPaths = null;
			try {

				if("1_1".equals(type)){
					wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word1",ids,result,null);
				}
				else if(type.equals("15") || type.equals("16")) {
					wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,type,ids,result,null);

				}else{
					wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word",ids,result,null);
				}

				String zipPath = wordPaths.get(0);
				String infile = "";
				if (tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184") || tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
						|| tpid.equals("47b1011d70f34aefb89365bbfce") || tpid.equals("eebdefc2-4d67-4452-a973")) {
					if (tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")) {
						infile = zipPath + "公务员登记备案表.doc";
					} else if (tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")) {
						infile = zipPath + "参照登记备案表.doc";
					} else if (tpid.equals("47b1011d70f34aefb89365bbfce")) {
						infile = zipPath + "干部花名册（一人一行）.doc";
					} else {
						infile = zipPath + "干部花名册（按机构分组）.doc";
					}
				} else {
					infile = zipPath.substring(0, zipPath.length() - 1) + ".zip";
					SevenZipUtil.zip7z(zipPath, infile, null);
				}

				this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.reloadTree()");
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 任免表导出
	@PageEvent("exportLrmxBtn")
	public int expWin() throws RadowException {
		/*
		 * int i = choose("peopleInfoGrid","personcheck"); if (i ==
		 * ON_ONE_CHOOSE ) { this.setMainMessage("请选择要导出的人员"); return
		 * EventRtnType.FAILD; }
		 */
		String hasQueried = this.getPageElement("sql").getValue();
		Object queryType = this.request.getSession().getAttribute("queryType");
		if (("".equals(hasQueried) || hasQueried == null)&&!"define".equals(queryType)) {
			// this.setMainMessage("未进行过查询请先查询!");

			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}

		// 判断列表是否有数据
		List<HashMap<String, Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();

		if (list22.size() == 0) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要导出的数据！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}

		this.setNextEventName("export");
		// this.openWindow("expTimeWin",
		// "pages.publicServantManage.ExpTimeWindow");
		// this.getExecuteSG().addExecuteCode("$h.openWin('expTimeWin','pages.publicServantManage.ExpTimeWindow','任免表导出',450,300,document.getElementById('a0000').value,ctxPath)");
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 导出Lrmx
	@PageEvent("export")
	@GridDataRange
	public int exportLrmx(String time) throws RadowException {
		String hasQueried = this.getPageElement("sql").getValue();
		Object queryType =  this.request.getSession().getAttribute("queryType");
		if (("".equals(hasQueried) || hasQueried == null)&&!"define".equals(queryType)) {
			this.setMainMessage("未进行过查询请先查询!");
		}
		time = "";// 去除导出时间窗口后默认时间为空，以后要恢复时注释即可
		String tableType = this.getPageElement("tableType").getValue();
		if ("1".equals(tableType)) {
			int i = choose("peopleInfoGrid", "personcheck");
			if (i == ON_ONE_CHOOSE) { // 没有选中操作全部
				String a0000 = "";
				PageElement pe = this.getPageElement("peopleInfoGrid");
				if (pe != null) {
					List<HashMap<String, Object>> list = pe.getValueList();
					String zippath = ExpRar.expFile();
					String infile = "";
					String name = "";
					Runtime rt = Runtime.getRuntime();
					Process p = null;
					for (int j = 0; j < list.size(); j++) {
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");

						a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000", j).toString();
						PersonXml per = createLrmxStr(a0000, time);
						name = (String) HBUtil.getHBSession()
								.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult();

						try {
							FileUtil.createFile(zippath + name + ".lrmx", JXUtil.Object2Xml(per, true), false, "UTF-8");

							A01 a01log = new A01();
							new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出",
									new Map2Temp().getLogInfo(new A01(), a01log));
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}

					}
					try {
						infile = zippath.substring(0, zippath.length() - 1) + ".zip";

						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
					}
				}

				return EventRtnType.NORMAL_SUCCESS;

			}
			if (i == CHOOSE_OVER_TOW) {
				String a0000 = "";
				PageElement pe = this.getPageElement("peopleInfoGrid");
				if (pe != null) {
					List<HashMap<String, Object>> list = pe.getValueList();
					String zippath = ExpRar.expFile();
					String infile = "";
					String name = "";
					Runtime rt = Runtime.getRuntime();
					Process p = null;
					for (int j = 0; j < list.size(); j++) {
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if (usercheck.equals(true)) {
							a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000", j).toString();
							PersonXml per = createLrmxStr(a0000, time);
							name = (String) HBUtil.getHBSession()
									.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'")
									.uniqueResult();
							// System.out.println(zippath);
							try {
								FileUtil.createFile(zippath + name + ".lrmx", JXUtil.Object2Xml(per, true), false,
										"UTF-8");
								// String cmdd = "\"D:\\Program Files
								// (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe\"
								// "+zippath+name+".lrm"+" "+zippath;
								// p = rt.exec(cmdd);
								// p.waitFor();
								A01 a01log = new A01();
								new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出",
										new Map2Temp().getLogInfo(new A01(), a01log));
							} catch (Exception e) {
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}
						}
					}
					try {
						infile = zippath.substring(0, zippath.length() - 1) + ".zip";
						// String cmd="cmd /c start d:\\Program Files
						// (x86)\\7-Zip\\7z.exe a -tzip "+infile+ "
						// "+zippath+"\\*";
						// p = Runtime.getRuntime().exec(cmd);
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
					}
				}
				// System.out.println(ids.toString());
				return EventRtnType.NORMAL_SUCCESS;
			} else {
				String a0000 = "";
				PageElement pe = this.getPageElement("peopleInfoGrid");
				if (pe != null) {
					List<HashMap<String, Object>> list = pe.getValueList();
					String zippath = ExpRar.expFile();
					String infile = "";
					String name = "";
					for (int j = 0; j < list.size(); j++) {
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if (usercheck.equals(true)) {
							a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000", j).toString();
							PersonXml per = createLrmxStr(a0000, time);
							name = (String) HBUtil.getHBSession()
									.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'")
									.uniqueResult();
							// System.out.println(zippath);
							try {
								FileUtil.createFile(zippath + name + ".lrmx", JXUtil.Object2Xml(per, true), false,
										"UTF-8");
								A01 a01log = new A01();
								new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出",
										new Map2Temp().getLogInfo(new A01(), a01log));
							} catch (Exception e) {
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}
						}
					}
					try {
						infile = zippath + name + ".lrmx";
						// SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
					}
				}

			}
		} else if ("2".equals(tableType) || "3".equals(tableType)) {
			HBSession sess = HBUtil.getHBSession();
			String a0000s = this.getPageElement("picA0000s").getValue();

			if (a0000s == null || "".equals(a0000s.trim())) { // 没有勾选 则导出全部

				this.getPageElement("picA0000s").setValue("");
				String sql = "select a0000 from A01SEARCHTEMP where sessionid='" + this.request.getSession().getId()
						+ "'";
				List<String> list = sess.createSQLQuery(sql).list();
				String a0000 = "";
				if (list != null && list.size() > 0) {
					String zippath = ExpRar.expFile();
					String infile = "";
					String name = "";
					for (int j = 0; j < list.size(); j++) {
						a0000 = list.get(j);
						PersonXml per = createLrmxStr(a0000, time);
						name = (String) HBUtil.getHBSession()
								.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult();
						try {
							FileUtil.createFile(zippath + name + ".lrmx", JXUtil.Object2Xml(per, true), false, "UTF-8");
							A01 a01log = new A01();
							new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出",
									new Map2Temp().getLogInfo(new A01(), a01log));
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}
					}
					try {
						infile = zippath.substring(0, zippath.length() - 1) + ".zip";
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
					}
				}
				return EventRtnType.NORMAL_SUCCESS;
			} else { // 按照勾选人员导出
				a0000s = a0000s.substring(0, a0000s.length() - 1);
				a0000s = a0000s.replaceAll("\\'", "");
				String[] str = a0000s.split(",");
				if (str != null && str.length > 0) {
					String zippath = ExpRar.expFile();
					String infile = "";
					String name = "";
					for (int j = 0; j < str.length; j++) {
						String a0000 = str[j];
						PersonXml per = createLrmxStr(a0000, time);
						name = (String) HBUtil.getHBSession()
								.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult();
						try {
							FileUtil.createFile(zippath + name + ".lrmx", JXUtil.Object2Xml(per, true), false, "UTF-8");
							A01 a01log = new A01();
							new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出",
									new Map2Temp().getLogInfo(new A01(), a01log));
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}
					}
					try {
						infile = zippath.substring(0, zippath.length() - 1) + ".zip";
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
					}
				}
			}
		}
		// System.out.println(ids.toString());
		return EventRtnType.NORMAL_SUCCESS;
	}

	public PersonXml createLrmxStr(String ids, String time) {
		String a0000 = ids;
		// String content = "";
		// try {
		// content =
		// QueryPersonListBS.XmlContentBuilder(QueryPersonListBS.getObjXml(a0000));
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// return content;
		PersonXml a = new PersonXml();
		JiaTingChengYuanXml jiaTingChengYuanXml = new JiaTingChengYuanXml();
		List<JiaTingChengYuanXml> jtcyList = new ArrayList<JiaTingChengYuanXml>();
		List<ItemXml> itemlist = new ArrayList<ItemXml>();
		HBSession sess = HBUtil.getHBSession();
		String sqla36 = "from A36 where a0000='" + a0000 + "' order by sortid";
		List lista36 = sess.createQuery(sqla36).list();
		String userid = SysUtil.getCacheCurrentUser().getId();
		A01 a01 = (A01) sess.get(A01.class, a0000);
		A57 a57 = (A57) sess.get(A57.class, a0000);
		String sqla53 = "from A53 where a0000='" + a0000 + "' and a5399='" + userid + "'";
		List<A53> list = sess.createQuery(sqla53).list();
		Object xb = HBUtil.getHBSession()
				.createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB2261' and t.code_value = '"
						+ a01.getA0104() + "'")
				.uniqueResult();
		Object mz = HBUtil.getHBSession()
				.createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB3304' and t.code_value = '"
						+ a01.getA0117() + "'")
				.uniqueResult();
		if (a57 != null) {
			byte[] data = PhotosUtil.getPhotoData(a57);
			if (data != null) {
				a.setZhaoPian(data);
			}
		}
		a.setXingMing(a01.getA0101());
		a.setXingBie(xb != null ? xb.toString() : "");
		a.setChuShengNianYue(a01.getA0107());
		a.setMinZu(mz != null ? mz.toString() : "");
		a.setJiGuan(a01.getComboxArea_a0111());
		a.setChuShengDi(a01.getComboxArea_a0114());
		a.setRuDangShiJian(a01.getA0140());
		a.setCanJiaGongZuoShiJian(a01.getA0134());
		a.setJianKangZhuangKuang(a01.getA0128());
		a.setZhuanYeJiShuZhiWu(a01.getA0196());
		a.setShuXiZhuanYeYouHeZhuanChang(a01.getA0187a());
		a.setQuanRiZhiJiaoYu_XueLi(a01.getQrzxl());
		a.setQuanRiZhiJiaoYu_XueWei(a01.getQrzxw());
		a.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getQrzxlxx());
		a.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getQrzxwxx());
		a.setZaiZhiJiaoYu_XueLi(a01.getZzxl());
		a.setZaiZhiJiaoYu_XueWei(a01.getZzxw());
		a.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getZzxlxx());
		a.setZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getZzxwxx());
		a.setXianRenZhiWu(a01.getA0192a());
		a.setJianLi(a01.getA1701());
		a.setJiangChengQingKuang(a01.getA14z101());
		a.setNianDuKaoHeJieGuo(a01.getA15z101());
		a.setShenFenZheng(a01.getA0184());
		if (list == null || list.size() == 0) {
			a.setNiRenZhiWu("");
			a.setNiMianZhiWu("");
			a.setRenMianLiYou("");
			a.setChengBaoDanWei("");
			a.setJiSuanNianLingShiJian("");
			a.setTianBiaoShiJian(time);
			a.setTianBiaoRen("");
		} else {
			List lista53 = sess.createQuery(sqla53).list();
			A53 a53 = (A53) lista53.get(0);
			a.setNiRenZhiWu(a53.getA5304());
			a.setNiMianZhiWu(a53.getA5315());
			a.setRenMianLiYou(a53.getA5317());
			a.setChengBaoDanWei(a53.getA5319());
			a.setJiSuanNianLingShiJian("");
			// if(StringUtil.isEmpty(time)){
			// a.setTianBiaoShiJian(a53.getA5323());
			// }else{
			a.setTianBiaoShiJian(time);
			// }
			a.setTianBiaoRen("");
		}
		if (lista36 != null && lista36.size() > 0) {
			for (int i = 1; i <= lista36.size(); i++) {
				ItemXml b = new ItemXml();
				A36 a36 = (A36) lista36.get(i - 1);
				// Object cw = HBUtil.getHBSession().createSQLQuery("select
				// t.code_name from code_value t where t.code_type = 'GB4761'
				// and t.code_value = '"+a36.getA3604a()+"'").uniqueResult();
				b.setChengWei(a36.getA3604a() != null ? a36.getA3604a().toString() : "");
				b.setChuShengRiQi(a36.getA3607());
				b.setGongZuoDanWeiJiZhiWu(a36.getA3611());
				b.setXingMing(a36.getA3601());
				// Object zzmm = HBUtil.getHBSession().createSQLQuery("select
				// t.code_name from code_value t where t.code_type = 'GB4762'
				// and t.code_value = '"+a36.getA3627()+"'").uniqueResult();
				b.setZhengZhiMianMao(a36.getA3627() != null ? a36.getA3627().toString() : "");
				itemlist.add(b);
			}
		}
		jiaTingChengYuanXml.setItem(itemlist);
		jtcyList.add(jiaTingChengYuanXml);
		a.setJiaTingChengYuan(jtcyList);
		return a;
	}

	// 导出Lrm
	@PageEvent("exportLrmBtn")
	@GridDataRange
	public int exportLrm() throws RadowException {
		String tableType = this.getPageElement("tableType").getValue();
		String hasQueried = this.getPageElement("sql").getValue();
		Object queryType = this.request.getSession().getAttribute("queryType");
		if (("".equals(hasQueried) || hasQueried == null)&&!"define".equals(queryType)) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}

		// 判断列表是否有数据
		List<HashMap<String, Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();

		if (list22.size() == 0) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要导出的数据！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}

		if ("1".equals(tableType)) {
			int i = choose("peopleInfoGrid", "personcheck");
			if (i == ON_ONE_CHOOSE) { // 列表没有勾选，则操作全部
				String a0000 = "";
				PageElement pe = this.getPageElement("peopleInfoGrid");
				if (pe != null) {
					List<HashMap<String, Object>> list = pe.getValueList();
					String zippath = ExpRar.expFile();
					String infile = "";
					String name = "";
					Runtime rt = Runtime.getRuntime();
					Process p = null;
					for (int j = 0; j < list.size(); j++) {
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");

						a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000", j).toString();
						String lrm = createLrmStr(a0000);
						name = (String) HBUtil.getHBSession()
								.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult();

						try {
							FileUtil.createFile(zippath + name + ".lrm", lrm, false, "GBK");
							List<A57> list15 = HBUtil.getHBSession()
									.createSQLQuery("select * from A57 where a0000='" + a0000 + "'")
									.addEntity(A57.class).list();
							if (list15.size() > 0) {
								A57 a57 = list15.get(0);
								if (a57.getPhotopath() != null && !a57.getPhotopath().equals("")) {
									File f = new File(zippath + name + ".pic");
									FileOutputStream fos = new FileOutputStream(f);
									File f2 = new File(PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname());
									if (f2.exists() && f2.isFile()) {
										InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
										byte[] data = new byte[1024];
										while (is.read(data) != -1) {
											fos.write(data);
										}
										is.close();
									}
									fos.close();

								}
							}

						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}

					}
					try {
						infile = zippath.substring(0, zippath.length() - 1) + ".zip";
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
					}
				}
				return EventRtnType.NORMAL_SUCCESS;
			}
			if (i == CHOOSE_OVER_TOW) {
				String a0000 = "";
				PageElement pe = this.getPageElement("peopleInfoGrid");
				if (pe != null) {
					List<HashMap<String, Object>> list = pe.getValueList();
					String zippath = ExpRar.expFile();
					String infile = "";
					String name = "";
					Runtime rt = Runtime.getRuntime();
					Process p = null;
					for (int j = 0; j < list.size(); j++) {
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if (usercheck.equals(true)) {
							a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000", j).toString();
							String lrm = createLrmStr(a0000);
							name = (String) HBUtil.getHBSession()
									.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'")
									.uniqueResult();
							// System.out.println(zippath);
							try {
								FileUtil.createFile(zippath + name + ".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession()
										.createSQLQuery("select * from A57 where a0000='" + a0000 + "'")
										.addEntity(A57.class).list();
								if (list15.size() > 0) {
									A57 a57 = list15.get(0);
									if (a57.getPhotopath() != null && !a57.getPhotopath().equals("")) {
										File f = new File(zippath + name + ".pic");
										FileOutputStream fos = new FileOutputStream(f);
										File f2 = new File(
												PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname());
										if (f2.exists() && f2.isFile()) {
											InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
											byte[] data = new byte[1024];
											while (is.read(data) != -1) {
												fos.write(data);
											}
											is.close();
										}
										fos.close();

									}
								}
								// String cmdd = "\"D:\\Program Files
								// (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe\"
								// "+zippath+name+".lrm"+" "+zippath;
								// p = rt.exec(cmdd);
								// p.waitFor();
							} catch (Exception e) {
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}
						}
					}
					try {
						infile = zippath.substring(0, zippath.length() - 1) + ".zip";
						// String cmd="cmd /c start d:\\Program Files
						// (x86)\\7-Zip\\7z.exe a -tzip "+infile+ "
						// "+zippath+"\\*";
						// p = Runtime.getRuntime().exec(cmd);
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
					}
				}
				// System.out.println(ids.toString());
				return EventRtnType.NORMAL_SUCCESS;
			} else {
				PageElement pe = this.getPageElement("peopleInfoGrid");
				String a0000 = "";
				if (pe != null) {
					StringBuffer ids = new StringBuffer();
					List<HashMap<String, Object>> list = pe.getValueList();
					for (int j = 0; j < list.size(); j++) {
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if (usercheck.equals(true)) {
							ids.append(this.getPageElement("peopleInfoGrid").getValue("a0000", j).toString());
						}
					}
					a0000 = ids.toString();
				}

				String lrm = createLrmStr(a0000);
				String name = (String) HBUtil.getHBSession()
						.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult();
				String zippath = ExpRar.expFile();
				// System.out.println(zippath);
				String infile = "";
				try {
					FileUtil.createFile(zippath + name + ".lrm", lrm, false, "GBK");
					List<A57> list15 = HBUtil.getHBSession()
							.createSQLQuery("select * from A57 where a0000='" + a0000 + "'").addEntity(A57.class)
							.list();
					if (list15.size() > 0) {
						A57 a57 = list15.get(0);
						if (a57.getPhotopath() != null && !a57.getPhotopath().equals("")) {
							File f = new File(zippath + name + ".pic");
							FileOutputStream fos = new FileOutputStream(f);
							File f2 = new File(PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname());
							if (f2.exists() && f2.isFile()) {
								InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
								byte[] data = new byte[1024];
								while (is.read(data) != -1) {
									fos.write(data);
								}
								is.close();
							}
							fos.close();

						}
					}
					infile = zippath.substring(0, zippath.length() - 1) + ".zip";
					Runtime rt = Runtime.getRuntime();
					Process p = null;
					// String cmdd = "\"D:\\Program Files
					// (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe\"
					// "+zippath+name+".lrm"+" "+zippath;
					// p = rt.exec(cmdd);
					// p.waitFor();
					// String cmd="cmd /c start d:\\Program Files
					// (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
					// p = Runtime.getRuntime().exec(cmd);
					SevenZipUtil.zip7z(zippath, infile, null);
				} catch (Exception e) {
					e.printStackTrace();
					this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
				}
				this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.reloadTree()");
			}
		} else if ("2".equals(tableType) || "3".equals(tableType)) { // 小资料、照片
			HBSession sess = HBUtil.getHBSession();
			String a0000s = this.getPageElement("picA0000s").getValue();

			if (a0000s == null || "".equals(a0000s.trim())) { // 没有勾选 则导出全部

				this.getPageElement("picA0000s").setValue("");
				String sql = "select a0000 from A01SEARCHTEMP where sessionid='" + this.request.getSession().getId()
						+ "'";
				List<String> list = sess.createSQLQuery(sql).list();
				String a0000 = "";
				if (list != null && list.size() > 0) {
					String zippath = ExpRar.expFile();
					String infile = "";
					String name = "";
					for (int j = 0; j < list.size(); j++) {
						a0000 = list.get(j);
						String lrm = createLrmStr(a0000);
						name = (String) HBUtil.getHBSession()
								.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult();

						try {
							FileUtil.createFile(zippath + name + ".lrm", lrm, false, "GBK");
							List<A57> list15 = HBUtil.getHBSession()
									.createSQLQuery("select * from A57 where a0000='" + a0000 + "'")
									.addEntity(A57.class).list();
							if (list15.size() > 0) {
								A57 a57 = list15.get(0);
								if (a57.getPhotopath() != null && !a57.getPhotopath().equals("")) {
									File f = new File(zippath + name + ".pic");
									FileOutputStream fos = new FileOutputStream(f);
									File f2 = new File(PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname());
									if (f2.exists() && f2.isFile()) {
										InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
										byte[] data = new byte[1024];
										while (is.read(data) != -1) {
											fos.write(data);
										}
										is.close();
									}
									fos.close();

								}
							}

						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}

					}
					try {
						infile = zippath.substring(0, zippath.length() - 1) + ".zip";
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
					}
				}
				return EventRtnType.NORMAL_SUCCESS;
			} else { // 按照勾选人员导出
				a0000s = a0000s.substring(0, a0000s.length() - 1);
				a0000s = a0000s.replaceAll("\\'", "");
				String[] str = a0000s.split(",");
				if (str != null && str.length > 0) {
					String zippath = ExpRar.expFile();
					String infile = "";
					String name = "";
					for (int j = 0; j < str.length; j++) {
						String a0000 = str[j];
						String lrm = createLrmStr(a0000);
						name = (String) HBUtil.getHBSession()
								.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult();
						// System.out.println(zippath);
						try {
							FileUtil.createFile(zippath + name + ".lrm", lrm, false, "GBK");
							List<A57> list15 = HBUtil.getHBSession()
									.createSQLQuery("select * from A57 where a0000='" + a0000 + "'")
									.addEntity(A57.class).list();
							if (list15.size() > 0) {
								A57 a57 = list15.get(0);
								if (a57.getPhotopath() != null && !a57.getPhotopath().equals("")) {
									File f = new File(zippath + name + ".pic");
									FileOutputStream fos = new FileOutputStream(f);
									File f2 = new File(PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname());
									if (f2.exists() && f2.isFile()) {
										InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
										byte[] data = new byte[1024];
										while (is.read(data) != -1) {
											fos.write(data);
										}
										is.close();
									}
									fos.close();

								}
							}
							// String cmdd = "\"D:\\Program Files
							// (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe\"
							// "+zippath+name+".lrm"+" "+zippath;
							// p = rt.exec(cmdd);
							// p.waitFor();
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}
					}
					try {
						infile = zippath.substring(0, zippath.length() - 1) + ".zip";
						// String cmd="cmd /c start d:\\Program Files
						// (x86)\\7-Zip\\7z.exe a -tzip "+infile+ "
						// "+zippath+"\\*";
						// p = Runtime.getRuntime().exec(cmd);
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
					}
				}
				// System.out.println(ids.toString());
				return EventRtnType.NORMAL_SUCCESS;
			}

		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	public String createLrmStr(String ids) {
		return createLrmStr(ids, true);
	}

	public String createLrmStr(String ids, boolean falg) {
		return createLrmStr(ids, falg, null);
	}

	/**
	 * 生成Lrm文件
	 *
	 * @param ids
	 *            人员ID a0000
	 * @param falg
	 *            是否打印拟任免信息：true-打印拟任免信息
	 * @return
	 */
	public String createLrmStr(String ids, boolean falg, String printTime) {

		String useTime = printTime;
		String a0000 = ids;
		String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
		DBType cueDBType = DBUtil.getDBType();
		String str = "";
		String jiatingchengyuan = "";
		String laststr2 = "";
		String laststr1 = "";
		if (cueDBType == DBType.MYSQL) {
			ResultSet rs = null;
			try {
				rs = HBUtil.getHBSession().connection()
						.prepareStatement(
								"select CONCAT_WS('','\"',t.a0101,'\",\"',( select code_name from code_value where code_value = t.a0104 and code_type = 'GB2261' and code_status = '1'),'\",\"',T.A0107,'\",\"',( select code_name from code_value where code_value = t.A0117 and code_type = 'GB3304' and code_status = '1'),'\",\"',T.A0111A,'\",\"',replace(replace(t.a0140, '(', ''), ')', ''),'\",\"',t.a0128,'\",\"', T.a0114A,'\",\"', T.a0134,'\",\"',t.qrzxl,'#',t.qrzxw,'@',t.zzxl,'#',t.zzxw,'\",\"',t.qrzxlxx,'#',t.qrzxwxx,'@',t.zzxlxx,'#',t.zzxwxx,'\",\"',T.A0196,'\",\"\",\"\",\"\",\"\",\"',t.a0187a,'\",\"',t.A1701,'\",\"',t.A14Z101,'\",\"',t.A15Z101,'\"') "
										+ "from a01 t  where t.a0000='" + a0000 + "'")
						.executeQuery();
				if (rs != null && rs.next()) {
					str = rs.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// str = (String) HBUtil.getHBSession().createSQLQuery("select
			// CONCAT_WS('','\"',t.a0101,'\",\"',( select code_name from
			// code_value where code_value = t.a0104 and code_type = 'GB2261'
			// and code_status = '1'),'\",\"',T.A0107,'\",\"',( select code_name
			// from code_value where code_value = t.A0117 and code_type =
			// 'GB3304' and code_status =
			// '1'),'\",\"',T.A0111A,'\",\"',replace(replace(t.a0140, '(', ''),
			// ')', ''),'\",\"',t.a0128,'\",\"', T.a0114A,'\",\"',
			// T.a0134,'\",\"',t.qrzxl,'#',t.qrzxw,'@',t.zzxl,'#',t.zzxw,'\",\"',t.qrzxlxx,'#',t.qrzxwxx,'@',t.zzxlxx,'#',t.zzxwxx,'\",\"',T.A0196,'\",\"\",\"\",\"\",\"\",\"',t.a0187a,'\",\"',t.A1701,'\",\"',t.A14Z101,'\",\"',t.A15Z101,'\"')
			// "
			// +"from a01 t where t.a0000='"+a0000+"'").uniqueResult();
			jiatingchengyuan = (String) HBUtil.getHBSession()
					.createSQLQuery(
							"select CONCAT_WS('','\"' , replace(group_concat(t.a3604a order by sortid), ',', '@') , '|\",\"' , replace(group_concat(t.a3601 order by sortid), ',', '@') , '|\",\"' ,       replace(group_concat(t.a3607 order by sortid), ',', '@') , '|\",\"' ,       replace(group_concat(t.a3627 order by sortid), ',', '@') , '|\",\"' ,       replace(group_concat(t.a3611 order by sortid), ',', '@') , '|\"')  "
									+ "from (select a0000, ifnull(a3604a, '#') a3604a, ifnull(a3601, '#') a3601, ifnull(a3607, '#') a3607, ifnull(a3627, '#') a3627, ifnull(a3611, '#') a3611, sortid from a36 where a36.a0000 = '"
									+ a0000 + "' order by a36.sortid) t")
					.uniqueResult();
			if (StringUtil.isEmpty(useTime)) {
				laststr2 = (String) HBUtil.getHBSession()
						.createSQLQuery(
								"select CONCAT_WS('','\"' , max(ifnull(t.a5304, '')) , '\",\"' , max(ifnull(t.a5315, '')) ,'\",\"' , max(ifnull(t.a5317, '')) , '\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , date_format(now(), '%Y%m%d') , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max(ifnull(t.a5323, '')) , '\"')from a53 t where t.a0000 = '"
										+ a0000 + "' and t.a5399 = '" + userid + "'")
						.uniqueResult();
			} else {
				laststr2 = (String) HBUtil.getHBSession()
						.createSQLQuery(
								"select CONCAT_WS('','\"' , max(ifnull(t.a5304, '')) , '\",\"' , max(ifnull(t.a5315, '')) ,'\",\"' , max(ifnull(t.a5317, '')) , '\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , date_format(now(), '%Y%m%d') , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max("
										+ useTime + ") , '\"')from a53 t where t.a0000 = '" + a0000
										+ "' and t.a5399 = '" + userid + "'")
						.uniqueResult();
			}

			laststr1 = (String) HBUtil.getHBSession().createSQLQuery(
					"select CONCAT_WS('','\"',max(ifnull(t.a0192a,'')), '\"') from a01 t where t.a0000='" + a0000 + "'")
					.uniqueResult();
		} else {
			str = (String) HBUtil.getHBSession()
					.createSQLQuery(
							"select to_char('\"'||t.a0101||'\",\"'||( select code_name from code_value where code_value = t.a0104 and code_type = 'GB2261' and code_status = '1')||'\",\"'||T.A0107||'\",\"'||"
									+ "( select code_name from code_value where code_value = t.A0117 and code_type = 'GB3304' and code_status = '1')||'\",\"'||T.A0111A||'\",\"'||replace(replace(t.a0140, '(', ''), ')', '')||'\",\"'||"
									+ "t.a0128||'\",\"'|| T.a0114A||'\",\"'|| T.a0134||'\",\"'||"
									+ "t.qrzxl||'#'||t.qrzxw||'@'||t.zzxl||'#'||t.zzxw||"
									+ "'\",\"'||t.qrzxlxx||'#'||t.qrzxwxx||'@'||t.zzxlxx||'#'||t.zzxwxx||'\",\"'||"
									+ "T.A0196||'\",\"\",\"\",\"\",\"\",\"'||t.a0187a||'\",\"'||"
									+ "t.A1701||'\",\"'||t.A14Z101||'\",\"'||t.A15Z101||'\"')"
									+ "from a01 t  where t.a0000='" + a0000 + "'")
					.uniqueResult();
			jiatingchengyuan = (String) HBUtil.getHBSession()
					.createSQLQuery("select to_char('\"' || replace(wm_concat(t.a3604a), ',', '@') || '|\",\"' ||"
							+ "replace(wm_concat(t.a3601), ',', '@') || '|\",\"' ||"
							+ "replace(wm_concat(t.a3607), ',', '@') || '|\",\"' ||"
							+ "replace(wm_concat(t.a3627), ',', '@') || '|\",\"' ||"
							+ "replace(wm_concat(t.a3611), ',', '@') || '|\"')"
							+ " from (select a0000,  nvl( a3604a,'#') a3604a,  nvl(a3601,'#')a3601,  nvl(a3607,'#')a3607, nvl( a3627,'#') a3627,nvl(a3611,'#')a3611,sortid from a36 where a36.a0000 = '"
							+ a0000 + "' order by a36.sortid)  t")
					.uniqueResult();
			if (StringUtil.isEmpty(useTime)) {
				laststr2 = (String) HBUtil.getHBSession()
						.createSQLQuery(
								"select '\"'||max(nvl(t.a5304,''))||'\",\"'||max(nvl(t.a5315,''))||'\",\"'||max(nvl(t.a5317,''))||'\",\"'||max(nvl(t.a5319,''))||'\",\"'||to_char(sysdate ,'YYYYMMDD')||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max(nvl(t.a5323,''))||'\"' from a53 t where t.a0000 = '"
										+ a0000 + "' and t.a5399 = '" + userid + "'")
						.uniqueResult();
			} else {
				laststr2 = (String) HBUtil.getHBSession()
						.createSQLQuery(
								"select '\"'||max(nvl(t.a5304,''))||'\",\"'||max(nvl(t.a5315,''))||'\",\"'||max(nvl(t.a5317,''))||'\",\"'||max(nvl(t.a5319,''))||'\",\"'||to_char(sysdate ,'YYYYMMDD')||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max("
										+ useTime + ")||'\"' from a53 t where t.a0000 = '" + a0000 + "' and t.a5399 = '"
										+ userid + "'")
						.uniqueResult();
			}

			laststr1 = (String) HBUtil.getHBSession()
					.createSQLQuery(
							"select '\"'||max(nvl(t.a0192a,''))|| '\"' from a01 t where t.a0000='" + a0000 + "'")
					.uniqueResult();
		}
		// System.out.println(str);
		String count = HBUtil.getHBSession().createSQLQuery("Select Count(1) From a36 t where t.a0000='" + a0000 + "'")
				.list().get(0).toString();
		String append = "";
		if (Integer.valueOf(count) < 13) {
			for (int j = 6; j > Integer.valueOf(count) - 1; j--) {
				append += "@";
			}
		}
		jiatingchengyuan = jiatingchengyuan.replace("#", "");
		CommonQueryBS.systemOut(jiatingchengyuan.replace("|", append));
		// System.out.println(laststr1);
		// System.out.println(str+","+jiatingchengyuan.replace("|",append)+","+laststr);
		// 是否打印拟任免信息
		if (!falg) {
			if (cueDBType == DBType.MYSQL) {
				if (StringUtil.isEmpty(useTime)) {
					laststr2 = (String) HBUtil.getHBSession()
							.createSQLQuery(
									"select CONCAT_WS('','\"','\",\"','\",\"','\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , max(ifnull(t.a5321, '')) , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max(ifnull(t.a5323, '')) , '\"')from a53 t where t.a0000 = '"
											+ a0000 + "' and t.a5399 = '" + userid + "'")
							.uniqueResult();
				} else {
					laststr2 = (String) HBUtil.getHBSession()
							.createSQLQuery(
									"select CONCAT_WS('','\"','\",\"','\",\"','\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , max(ifnull(t.a5321, '')) , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max("
											+ useTime + ") , '\"')from a53 t where t.a0000 = '" + a0000
											+ "' and t.a5399 = '" + userid + "'")
							.uniqueResult();
				}
			} else {
				if (StringUtil.isEmpty(useTime)) {
					laststr2 = (String) HBUtil.getHBSession()
							.createSQLQuery(
									"select '\"\",\"\",\"\",\"'||max(nvl(t.a5319,''))||'\",\"'||max(nvl(t.a5321,''))||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max(nvl(t.a5323,''))||'\"' from a53 t where t.a0000 = '"
											+ a0000 + "' and t.a5399 = '" + userid + "'")
							.uniqueResult();
				} else {
					laststr2 = (String) HBUtil.getHBSession()
							.createSQLQuery(
									"select '\"\",\"\",\"\",\"'||max(nvl(t.a5319,''))||'\",\"'||max(nvl(t.a5321,''))||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max("
											+ useTime + ")||'\"' from a53 t where t.a0000 = '" + a0000
											+ "' and t.a5399 = '" + userid + "'")
							.uniqueResult();
				}
			}
		}

		String lrm = str + "," + jiatingchengyuan.replace("|", append) + "," + laststr1 + "," + laststr2;
		return lrm;
	}

	/**
	 * 私有方法，是否选中用户
	 *
	 * @throws RadowException
	 */
	private int choose(String gridid, String checkId) throws RadowException {
		int result = 1;
		int number = 0;
		PageElement pe = this.getPageElement(gridid);
		List<HashMap<String, Object>> list = pe.getValueList();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			Object check1 = map.get(checkId);
			if (check1 == null) {
				continue;
			}
			if (check1.equals(true)) {
				number = i;
				result++;
			}
		}
		if (result == 1) {
			return ON_ONE_CHOOSE;// 没有选中任何用户
		}
		if (result >= 2) {
			return CHOOSE_OVER_TOW;// 选中多于一个用户
		}
		return number;// 选中第几个用户
	}

	/**
	 * 对勾选的人员批量打印
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @author mengl
	 */
	@PageEvent("exportPdfForAspose.onclick")
	@NoRequiredValidate
	public int exportPdfForAspose() throws RadowException, AppException{

		String hasQueried = this.getPageElement("sql").getValue();

		if("".equals(hasQueried) || hasQueried==null){
			//this.setMainMessage("未进行过查询请先查询!");

			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}


		//判断列表是否有数据
		List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();

		if(list22.size() == 0){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
			return EventRtnType.NORMAL_SUCCESS;
		}

		boolean flagNrm =true;	//是否打印拟任免信息
		int i = choose("peopleInfoGrid","personcheck");
		if (i == ON_ONE_CHOOSE ) {
			String pdfPath = "";
			String a0000 = "";
			PageElement pe = this.getPageElement("peopleInfoGrid");
			if(pe!=null){
				List<HashMap<String, Object>> list = pe.getValueList();
				List<String> list2 = new ArrayList<String>();
				for(int j = 0; j < list.size(); j++){
					HashMap<String, Object> map = list.get(j);
					a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
					list2.add(a0000);
				}
				String pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2);
				String infile ="";
				try {
					infile =pdfPaths.substring(0,pdfPaths.length()-1)+".zip" ;
//						String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//						p = Runtime.getRuntime().exec(cmd);
					SevenZipUtil.zip7z(pdfPaths, infile, null);
					this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.reloadTree()");
				} catch (Exception e) {
					this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
				}

			}else{
				this.setMainMessage("请查询人员");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}else{
			String printTime = "";
			String newPDFPath = "";
			List<HashMap<String, Object>> list = this.getPageElement(
					"peopleInfoGrid").getValueList();
			// 勾选的人员ID
			List<String> a0000s = new ArrayList<String>();
			String infile ="";
			for (HashMap<String, Object> map : list) {
				if (map != null&& map.get("personcheck").toString().equalsIgnoreCase("true")) {
						a0000s.add((String) map.get("a0000"));
					}
			}
			String pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(a0000s);
			try {
				infile =pdfPaths.substring(0,pdfPaths.length()-1)+".zip" ;
//					String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//					p = Runtime.getRuntime().exec(cmd);
				SevenZipUtil.zip7z(pdfPaths, infile, null);
				this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.reloadTree()");
			} catch (Exception e) {
				this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
			}
		}

		return EventRtnType.NORMAL_SUCCESS;
	}
	//导出个人数据
		@PageEvent("importHzbBtn")
		public int expHzbWin(String gsType) throws RadowException {

			String tableType = this.getPageElement("tableType").getValue();
			StringBuffer ids = new StringBuffer();
			String hasQueried = this.getPageElement("sql").getValue();
			Object queryType =  this.request.getSession().getAttribute("queryType");
			//列表
			if(("".equals(hasQueried) || hasQueried==null)&&!"define".equals(queryType)){
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String sql = "";
			if("1".equals(tableType)){
				//Object obj = this.getPageElement("checkAll").getValue();
				int all = choose("peopleInfoGrid","personcheck");

				if(all == ON_ONE_CHOOSE){		//列表没有勾选，则操作全部
					//String allSelect = (String)this.request.getSession().getAttribute("allSelect");
					/*int i = choose("peopleInfoGrid","personcheck");
					if (i == ON_ONE_CHOOSE ) {
						this.setMainMessage("请先选中要导出的人员");
						return EventRtnType.FAILD;
					}*/
					/*if(StringUtil.isEmpty(allSelect)){
						this.setMainMessage("请先选中要导出的人员");
						return EventRtnType.FAILD;
					}*/
					//String newsql = allSelect.replace("*", "a0000");
					sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";

					/*List allPeople = HBUtil.getHBSession().createSQLQuery(sql).list();
					if (allPeople.size() > 0) {
						for(Object a0000 : allPeople){
							ids.append("'").append(a0000).append("',");
						}
					}*/
				}
				else{
					int i = choose("peopleInfoGrid","personcheck");
					if (i == ON_ONE_CHOOSE ) {
						/*this.setMainMessage("请先选中要导出的人员");
						return EventRtnType.FAILD;*/

						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					PageElement pe = this.getPageElement("peopleInfoGrid");
					if(pe!=null){
						List<HashMap<String, Object>> list = pe.getValueList();
						for(int j=0;j<list.size();j++){
							HashMap<String, Object> map = list.get(j);
							Object usercheck = map.get("personcheck");
							if(usercheck.equals(true)){
								ids.append("'"+map.get("a0000").toString()).append("',");
							}
						}
					}
				}
			}
			//小资料、照片
			if("2".equals(tableType) || "3".equals(tableType)){
				String a0000s = this.getPageElement("picA0000s").getValue();  //为空，则操作全部
				/*Object obj = null;
				if("2".equals(tableType)){
					obj = this.getPageElement("checkAll2").getValue();
				}
				if("3".equals(tableType)){
					obj = this.getPageElement("checkAll3").getValue();
				}*/
				if("".equals(a0000s)){

					/*String allSelect = (String)this.request.getSession().getAttribute("allSelect");
					String newsql = allSelect.replace("*", "a0000");
					*/
					sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
					/*List allPeople = HBUtil.getHBSession().createSQLQuery(sql).list();
					if (allPeople.size() > 0) {
						for(Object a0000 : allPeople){
							ids.append("'").append(a0000).append("',");
						}
					}*/
				}else{
					//String a0000s = this.getPageElement("picA0000s").getValue();
					if(a0000s==null || "".equals(a0000s.trim())){
						/*this.setMainMessage("请先选中要导出的人员");
						return EventRtnType.FAILD;*/

						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					ids = new StringBuffer(a0000s);
				}
			}

			//判断当前列表是否有人员
			if("".equals(sql) && ids.length() < 1){
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要导出的数据！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			String allids = "".equals(sql)?ids.substring(0,ids.length()-1):sql;
			String id = UUID.randomUUID().toString().replace("-", "");

			try {
				CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
				KingbsconfigBS.saveImpDetailInit3(id);
				UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
				Runnable thr = null;
				if("hzb".equals(gsType)){
					thr = new DataPsnImpThread(id, user,"","","","","","","","hzb","","","","",userVo,allids);
				}else if("7z".equals(gsType)){
					thr = new DataPsnImpThread(id, user,"","","","","","","","7z","","","","",userVo,allids);
				}else if("zip".equals(gsType)){
					String allSelect=(String)this.request.getSession().getAttribute("allSelect");
					allSelect="select a0000 from ("+allSelect+") tem11";
					thr = new DataPsnImpDBThread(id, user,"","","","","","","","zip","","","","",userVo,allids,allSelect,this.request.getSession().getId());
				}

				new Thread(thr,"Thread_psnexp").start();
				this.setRadow_parent_data(id);
			    this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','导出进度',500,300,'"+id+"',ctxPath)");

			} catch (Exception e) {
				e.printStackTrace();
			}
			return EventRtnType.NORMAL_SUCCESS;
		}
		//自定义导出弹框
		@PageEvent("expBtnwordonclick")
		@NoRequiredValidate
		public int word()throws RadowException{
			 this.getExecuteSG().addExecuteCode("view()");
			return EventRtnType.NORMAL_SUCCESS;
		}
	//自定义导出弹框
	@PageEvent("cQueryById")
	@NoRequiredValidate
	public int cQueryById(String qid)throws RadowException, AppException{
		String userID = SysManagerUtils.getUserId();
		StringBuffer a02_a0201b_sb = new StringBuffer("");
        StringBuffer cu_b0111_sb = new StringBuffer("");
        String sid = this.request.getSession().getId();
        //用来判断列表、小资料、照片
        String tableType = this.getPageElement("tableType").getValue();

		String finalsql = "";
		String sql_ = "select  a01.a0000,'"+sid+"' sessionid from A01 a01  where  1=1 ";
		String sql2_ = " and  concat(a01.a0000, '') in (select a02.a0000 from a02 where a02.A0201B in ("
				+ "select cu.b0111 from competence_userdept cu where cu.userid = '"+
				userID +"') and a02.a0281='true' and a02.a0279='1') ";

		String NSFsql = "";
		if(qid.equals("q01")) {
			finalsql =sql_ + sql2_ + " and GET_BIRTHDAY(a01.a0107, '"+DateUtil.getcurdate()+"')<=45 and a01.a0221='1A31'";
		} else if(qid.equals("q02")) {
			finalsql =sql_ + sql2_ + " and GET_BIRTHDAY(a01.a0107, '"+DateUtil.getcurdate()+"')<=40 and a01.a0221='1A31'";
		} else if(qid.equals("q03")) {
			finalsql =sql_ + sql2_ + " and GET_BIRTHDAY(a01.a0107, '"+DateUtil.getcurdate()+"')<=40 and a01.a0221='1A32'";
		} else if(qid.equals("q04")) {
			finalsql =sql_ + sql2_ + " and GET_BIRTHDAY(a01.a0107, '"+DateUtil.getcurdate()+"')<=35 and a01.a0221='1A32'";
		} else if(qid.equals("q05")) {
			finalsql =sql_ + sql2_ + " and a01.a0141 <> '01' and a01.a0165='03' and a01.a0221 >'1A22'";
		} else if(qid.equals("q06")) {
			finalsql =sql_ + sql2_ + " and a01.a0104='2' and a01.a0165='03' and a01.a0221 > '1A22'";
		} else if(qid.equals("q07")) {
			NSFsql =NSFsql + " and a0163='1' AND ((GET_BIRTHDAY(a01.a0107, '"+DateUtil.getcurdate()+"')>=60 AND a01.a0221 > '1A22' AND exists (SELECT a02.a0000 FROM b01,a02 WHERE b01.b0111 = a02.a0201b and a02.a0000=a01.a0000 AND A02.a0279 = '1' AND A02.a0219 = '1' and b01.B0131 NOT IN ('1003','1003')) ";
			NSFsql =NSFsql + "AND a01.a0221 = '1A31') or (GET_BIRTHDAY(a01.a0107, '"+DateUtil.getcurdate()+"')>=61 AND exists (SELECT a02.a0000 FROM b01,a02 WHERE b01.b0111 = a02.a0201b ";
			NSFsql =NSFsql + "AND A02.a0279 = '1' AND A02.a0219 = '1' and a02.a0000=a01.a0000 and b01.B0131 IN ('1003','1003')) AND A01.a0221 = '1A31'))";
			finalsql =sql_ + sql2_ + NSFsql;
		} else if(qid.equals("q08")) {
			finalsql =sql_ + sql2_ + " and exists (SELECT a02.a0000 FROM b01,a02 WHERE b01.b0111 = a02.a0201b AND A02.a0279 = '1' and a02.a0000=a01.a0000 AND A02.a0219 = '1' and (b01.B0124 like '7%' or b01.B0124 like '8%'))";
		} else {
			finalsql = getSQL(cu_b0111_sb, a02_a0201b_sb, userID, qid);
		}
        System.out.println(finalsql);
        this.getPageElement("sql").setValue(finalsql);
        Map<String, Boolean> m = new HashMap<String, Boolean>();
        m.put("paixu", true);
        this.request.getSession().setAttribute("queryConditionsCQ",m);

        this.getExecuteSG().addExecuteCode("document.getElementById('checkedgroupid').value=''");
        this.getExecuteSG().addExecuteCode("document.getElementById('tabn').value='tab2'");
		//this.setNextEventName("peopleInfoGrid.dogridquery");
        if("1".equals(tableType)){
        	String queryType = (String)request.getSession().getAttribute("queryType");
        	if("define".equals(queryType)){
				this.getExecuteSG().addExecuteCode("changeField()");
				request.getSession().removeAttribute("queryType");
			}else{
				this.getExecuteSG().addExecuteCode("radow.doEvent('peopleInfoGrid.dogridquery');");
			}

        }
        if("2".equals(tableType)){
        	this.getExecuteSG().addExecuteCode("radow.doEvent('peopleInfoGrid.dogridquery');");
        	this.getExecuteSG().addExecuteCode("datashow();");
        }
        if("3".equals(tableType)){
        	this.getExecuteSG().addExecuteCode("radow.doEvent('peopleInfoGrid.dogridquery');");
        	this.getExecuteSG().addExecuteCode("picshow();");
        }
        //this.getExecuteSG().addExecuteCode("collapseGroupWin();");
        this.request.getSession().setAttribute("queryType", "1");
		return EventRtnType.NORMAL_SUCCESS;
	}

	private String getSQL(StringBuffer cu_b0111_sb, StringBuffer a02_a0201b_sb, String userID, String qid) throws AppException, RadowException{
		StringBuffer a01sb = new StringBuffer("");
		StringBuffer a02sb = new StringBuffer("");
		StringBuffer orther_sb = new StringBuffer("");
		String sid = this.request.getSession().getId();

		Customquery query = (Customquery) HBUtil.getHBSession().get(Customquery.class, qid);
		String conds = query.getQuerycond();
		Map<String, String> condMap = JSONObject.fromObject(conds);

		String a0195 = condMap.get("a0195");//统计关系所在单位
		if (!"".equals(a0195)){
			a01sb.append(" and ");
			a01sb.append("a01.a0195 = '"+a0195+"'");
		}


		String a0101 = condMap.get("a0101");//人员姓名
		if (!"".equals(a0101)){
			a01sb.append(" and ");
			a01sb.append("a01.a0101 like '"+a0101+"%'");
		}

		String a0184 = condMap.get("a0184");//身份证号
		if (!"".equals(a0184)){
			a01sb.append(" and ");
			a01sb.append("a01.a0184 like '"+a0184+"%'");
		}

		String a0111 = condMap.get("a0111");//籍贯
		String a0111_combo = condMap.get("a0111_combo");//籍贯
		if (a0111!=null && !"".equals(a0111)){
			a0111 = a0111.replaceAll("(0){1,}$", "");
			a01sb.append(" and ");
			a01sb.append(" a01.a0111 like '"+a0111+"%' ");
		}else{
			if(a0111_combo!=null && !"".equals(a0111_combo)){
				a01sb.append(" and ");
				a01sb.append(" a01.a0111a like '%"+a0111_combo+"%' ");
			}
		}



		String a0104 = condMap.get("a0104");//性别
		if (!"".equals(a0104)&&!"0".equals(a0104)){
			a01sb.append(" and ");
			a01sb.append("a01.a0104 = '"+a0104+"'");
		}

		String ageA = condMap.get("ageA");//年龄
		String ageB = condMap.get("ageB");//年龄
		if(!"".equals(ageB) && StringUtils.isNumeric(ageB)){//是否数字
			ageB = Integer.valueOf(ageB)+1+"";
		}
		//String jiezsj = this.getPageElement("jiezsj").getValue();//截止时间
		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMM");
		String jiezsj = myFmt1.format(new Date());
		String dateEnd = GroupPageBS.getDateformY(ageA, jiezsj);
		String dateStart = GroupPageBS.getDateformY(ageB, jiezsj);
		if(!"".equals(dateEnd)&&!"".equals(dateStart)&&dateEnd.compareTo(dateStart)<0){
			throw new AppException("年龄范围错误！");
		}
		if(!"".equals(dateStart)){
			a01sb.append(" and a01.a0107>='"+dateStart+"'");
		}
		if(!"".equals(dateEnd)){
			a01sb.append(" and a01.a0107<'"+dateEnd+"'");
		}

		String a0160 = condMap.get("a0160");//人员类别
		if (!"".equals(a0160)){
			a01sb.append(" and ");
			a01sb.append("a01.a0160 = '"+a0160+"'");
		}




		String a0107A = condMap.get("a0107A");//出生年月
		String a0107B = condMap.get("a0107B");//出生年月
		if(!"".equals(a0107A)){
			a01sb.append(" and a01.a0107>='"+a0107A+"'");
		}
		if(!"".equals(a0107B)){
			a01sb.append(" and a01.a0107<='"+a0107B+"'");
		}



		/*String a0163 = this.getPageElement("a0163").getValue();//人员状态
		if(!"".equals(a0163)){
			a01sb.append(" and a01.a0163='"+a0163+"'");
		}*/




		String a0144A = condMap.get("a0144A");//参加中共时间
		String a0144B = condMap.get("a0144B");//参加中共时间
		if(!"".equals(a0144A)){
			a01sb.append(" and a01.a0144>='"+a0144A+"'");
		}
		if(!"".equals(a0144B)){
			a01sb.append(" and a01.a0144<='"+a0144B+"'");
		}



		String a0141 = condMap.get("a0141");//政治面貌
		if(!"".equals(a0141)){
			a01sb.append(" and a01.a0141='"+a0141+"'");
		}


		String a0192a = condMap.get("a0192a");//职务全称
		if(!"".equals(a0192a)){
			a01sb.append(" and a01.a0192a like '%"+a0192a+"%'");
		}



		String a0134A = condMap.get("a0134A");//参加工作时间
		String a0134B = condMap.get("a0134B");//参加工作时间
		if(!"".equals(a0134A)){
			a01sb.append(" and a01.a0134>='"+a0134A+"'");
		}
		if(!"".equals(a0134B)){
			a01sb.append(" and a01.a0134<='"+a0134B+"'");
		}



		String a0114 = condMap.get("a0114");//出生地
		if (a0114!=null && !"".equals(a0114)){
			a0114 = a0114.replaceAll("(0){1,}$", "");
			a01sb.append(" and a01.a0114 like '"+a0114+"%' ");
		}



		String a0221A = condMap.get("a0221A");//职务层次
		String a0221B = condMap.get("a0221B");//职务层次
		if(!StringUtil.isEmpty(a0221A) && !StringUtil.isEmpty(a0221B)){
			CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", a0221A);
			CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", a0221B);
			if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
				throw new AppException("职务层次范围不属于同一类别，请检查！");
			}
			//职务层次 值越小 字面意思越高级
			if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
				throw new AppException("职务层次范围不正确，请检查！");
			}
		}
		if(!"".equals(a0221A)){
			a01sb.append(" and a01.a0221<='"+a0221A+"'");
		}
		if(!"".equals(a0221B)){
			a01sb.append(" and a01.a0221>='"+a0221B+"'");
		}




		String a0288A = condMap.get("a0288A");//任现职务层次时间
		String a0288B = condMap.get("a0288B");//任现职务层次时间
		if(!"".equals(a0288A)){
			a01sb.append(" and a01.a0288>='"+a0288A+"'");
		}
		if(!"".equals(a0288B)){
			a01sb.append(" and a01.a0288<='"+a0288B+"'");
		}




		String xgsjA = condMap.get("xgsjA");//最后维护时间
		String xgsjB = condMap.get("xgsjB");//最后维护时间
		if(!"".equals(xgsjA)){
			a01sb.append(" and a01.xgsj>="+CommSQL.to_date(xgsjA));
		}
		if(!"".equals(a0288B)){
			a01sb.append(" and a01.xgsj<="+CommSQL.adddate(CommSQL.to_date(xgsjB)));
		}




		String a0117 = condMap.get("a0117");//人员管理类别
		if(!"".equals(a0117)){
			String[] a0117s = a0117.split(",");
			if(a0117s.length==1){
				if("01".equals(a0117s[0]))
					a01sb.append(" and a01.a0117 ='01'");
				else
					a01sb.append(" and a01.a0117 !='01'");
			}

		}




		String a1701 = condMap.get("a1701");//简历
		if(!"".equals(a1701)){
			a01sb.append(" and a01.a1701 like '%"+a1701+"%'");
		}




		String a0192e = condMap.get("a0192e");//现职级
		if(!"".equals(a0192e)){
			a01sb.append(" and a01.a0192e='"+a0192e+"'");
		}




		String a0192cA = condMap.get("a0192cA");//任职级时间
		String a0192cB = condMap.get("a0192cB");//任职级时间
		if(!"".equals(a0192cA)){
			a01sb.append(" and a01.a0192c>='"+a0192cA+"'");
		}
		if(!"".equals(a0192cB)){
			a01sb.append(" and a01.a0192c<='"+a0192cB+"'");
		}




		String a0165 = condMap.get("a0165");//人员管理类别
		if(!"".equals(a0165)){
			a0165 = a0165.replace(",", "','");
			a01sb.append(" and a01.a0165 in('"+a0165+"')");
		}



		//职务 StringBuffer a02sb = new StringBuffer("");

		String a0216a = condMap.get("a0216a");//职务名称
		if(!"".equals(a0216a)){
			a02sb.append(" and a02.a0216a like '%"+a0216a+"%'");
		}



		String a0201d = condMap.get("a0201d");//是否班子成员
		if(!"".equals(a0201d)){
			a02sb.append(" and a02.a0201d='"+a0201d+"'");
		}




		String a0219 = condMap.get("a0219");//是否领导职务
		if(!"".equals(a0219)){
			a02sb.append(" and a02.a0219='"+a0219+"'");
		}




		String a0201e = condMap.get("a0201e");//成员类别
		if(!"".equals(a0201e)){
			a0201e = a0201e.replace(",", "','");
			a02sb.append(" and a02.a0201e in('"+a0201e+"')");
		}


		//最高学历
		String xla0801b = condMap.get("xla0801b");//最高学历  学历代码
		String xla0814 = condMap.get("xla0814");//毕业院校
		String xla0824 = condMap.get("xla0824");//专业
		xuelixueweiSQL(xla0801b,xla0814,xla0824,orther_sb,"a0834","a0801b");


		//最高学位
		String xwa0901b = condMap.get("xwa0901b");//最高学位 学位代码
		String xwa0814 = condMap.get("xwa0814");//毕业院校
		String xwa0824 = condMap.get("xwa0824");//专业
		xuelixueweiSQL(xwa0901b,xwa0814,xwa0824,orther_sb,"a0835","a0901b");


		//全日制最高学历
		String qrzxla0801b = condMap.get("qrzxla0801b");//最高学历  学历代码
		String qrzxla0814 = condMap.get("qrzxla0814");//毕业院校
		String qrzxla0824 = condMap.get("qrzxla0824");//专业
		xuelixueweiSQL(qrzxla0801b,qrzxla0814,qrzxla0824,orther_sb,"a0831","a0801b");


		//全日制最高学位
		String qrzxwa0901b = condMap.get("qrzxwa0901b");//最高学位 学位代码
		String qrzxwa0814 = condMap.get("qrzxwa0814");//毕业院校
		String qrzxwa0824 = condMap.get("qrzxwa0824");//专业
		xuelixueweiSQL(qrzxwa0901b,qrzxwa0814,qrzxwa0824,orther_sb,"a0832","a0901b");



		//在职最高学历
		String zzxla0801b = condMap.get("zzxla0801b");//最高学历  学历代码
		String zzxla0814 = condMap.get("zzxla0814");//毕业院校
		String zzxla0824 = condMap.get("zzxla0824");//专业
		xuelixueweiSQL(zzxla0801b,zzxla0814,zzxla0824,orther_sb,"a0838","a0801b");


		//在职最高学位
		String zzxwa0901b = condMap.get("zzxwa0901b");//最高学位 学位代码
		String zzxwa0814 = condMap.get("zzxwa0814");//毕业院校
		String zzxwa0824 = condMap.get("zzxwa0824");//专业
		xuelixueweiSQL(zzxwa0901b,zzxwa0814,zzxwa0824,orther_sb,"a0839","a0901b");



		//奖惩
		String a14z101 = condMap.get("a14z101");//奖惩描述
		String lba1404b = condMap.get("lba1404b");//奖惩类别
		String a1404b = condMap.get("a1404b");//奖惩名称代码
		String a1415 = condMap.get("a1415");//受奖惩时职务层次
		String a1414 = condMap.get("a1414");//批准机关级别
		String a1428 = condMap.get("a1428");//批准机关性质
		if (!"".equals(a14z101) || !"".equals(lba1404b) || !"".equals(a1404b) || !"".equals(a1415) || !"".equals(a1414) || !"".equals(a1428)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a14 where 1=1 ");
			if(!"".equals(a14z101)){
				orther_sb.append(" and a14z101 like '%"+a14z101+"%'");
			}

			if (!"".equals(lba1404b)) {
				StringBuffer like_sb = new StringBuffer("");
				String[] fArray = lba1404b.split(",");
				for (int i = 0; i < fArray.length; i++) {
					like_sb.append(" a1404b like '" + fArray[i] + "%' or ");
				}
				like_sb.delete(like_sb.length() - 4, like_sb.length() - 1);
				orther_sb.append(" and (" + like_sb.toString() + ")");
			}
			if (!"".equals(a1404b)) {
				orther_sb.append(" and a1404b='" + a1404b + "'");
			}
			if (!"".equals(a1415)) {
				orther_sb.append(" and a1415='" + a1415 + "'");
			}
			if (!"".equals(a1414)) {
				orther_sb.append(" and a1414='" + a1414 + "'");
			}
			if (!"".equals(a1428)) {
				orther_sb.append(" and a1428='" + a1428 + "'");
			}
			orther_sb.append(")");
		}

		//职称
		String a0601 = condMap.get("a0601");//专业技术任职资格

		if (!"".equals(a0601)) {
			boolean is9 = false;
			orther_sb.append(" and (a01.a0000 in (select a0000 from a06 where a0699='true' ");
			StringBuffer like_sb = new StringBuffer("");
			String[] fArray = a0601.split(",");
			for (int i = 0; i < fArray.length; i++) {

				if("9".equals(fArray[i])){
					like_sb.append(" a0601 is null or a0601='999' or ");//无职称
					is9 = true;
				}else{
					like_sb.append(" a0601 like '%" + fArray[i] + "' or ");
				}
			}
			like_sb.delete(like_sb.length() - 4, like_sb.length() - 1);
			orther_sb.append(" and (" + like_sb.toString() + ")");
			orther_sb.append(")");
			if(is9){//无职称
				orther_sb.append(" or not exists (select 1 from a06 where a01.a0000=a06.a0000 and  a0699='true')");
			}
			orther_sb.append(")");
		}

		//年度考核
		String a15z101 = condMap.get("a15z101");//年度考核描述
		String a1521 = condMap.get("a1521");//考核年度
		String a1517 = condMap.get("a1517");//考核结论类别
		if (!"".equals(a15z101) || !"".equals(a1521) || !"".equals(a1517)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a15 where 1=1 ");
			if(!"".equals(a15z101)){
				orther_sb.append(" and a15z101 like '%"+a15z101+"%'");
			}

			if(!"".equals(a1521)){
				a1521 = a1521.replace(",", "','");
				orther_sb.append(" and a1521 in('"+a1521+"')");
			}

			if (!"".equals(a1517)) {
				orther_sb.append(" and a1517='" + a1517 + "'");
			}

			orther_sb.append(")");
		}




		//家庭成员
		String a3601 = condMap.get("a3601");//姓名
		String a3684 = condMap.get("a3684");//身份证号
		String a3611 = condMap.get("a3611");//工作单位及职务
		if (!"".equals(a3601) || !"".equals(a3684) || !"".equals(a3611)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a36 where 1=1 ");
			if(!"".equals(a3601)){
				orther_sb.append(" and a3601 like '"+a3601+"%'");
			}

			if(!"".equals(a3684)){
				orther_sb.append(" and a3684 like '"+a3684+"%'");
			}

			if(!"".equals(a3611)){
				orther_sb.append(" and a3611 like '%"+a3611+"%'");
			}

			orther_sb.append(")");
		}







		String radioC = condMap.get("radioC");
		/*String sql = condMap.get("sql");


		if(!"1".equals(radioC)){
			if("".equals(sql)||sql==null)
				throw new AppException("未进行过查询请先查询!");
		}*/



		String a0163 = condMap.get("a0163");//人员状态
		String qtxzry = condMap.get("qtxzry");

		String finalsql = CommSQL.getCondiQuerySQL(userID,a01sb,a02sb,a02_a0201b_sb,cu_b0111_sb,orther_sb,a0163,qtxzry,sid);
		return finalsql;
	}
	private void xuelixueweiSQL(String a0801b, String a0814, String a0824, StringBuffer orther_sb, String highField,
			String xueliORxuewei) {
		StringBuffer a0801b_sb = new StringBuffer("");

		if (!"".equals(a0801b)) {
			String[] a0801bArray = a0801b.split(",");
			for (int i = 0; i < a0801bArray.length; i++) {
				a0801b_sb.append(" " + xueliORxuewei + " like '" + a0801bArray[i] + "%' or ");
			}
			a0801b_sb.delete(a0801b_sb.length() - 4, a0801b_sb.length() - 1);
		}

		if (!"".equals(a0801b) || !"".equals(a0814) || !"".equals(a0824)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a08 where " + highField + "='1' ");
			if (!"".equals(a0801b)) {
				orther_sb.append(" and (" + a0801b_sb.toString() + ")");
			}

			if (!"".equals(a0814)) {
				orther_sb.append(" and a0814 like '%" + a0814 + "%'");
			}
			if (!"".equals(a0824)) {
				orther_sb.append(" and a0824 like '%" + a0824 + "%'");
			}
			orther_sb.append(")");
		}

	}

	//批量修改
		@PageEvent("betchModifyBtn.onclick")
		@GridDataRange
		public int betchModify() throws RadowException, AppException{  //打开窗口的实例
			HBSession sess = HBUtil.getHBSession();
			String sqlf = this.getPageElement("sql").getValue();
			//this.request.getSession().setAttribute("queryType", "define");// 设置自定义查询列表标志
			Object queryType =  this.request.getSession().getAttribute("queryType");
			if(sqlf.equals("")&&!"define".equals(queryType)){
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先查询！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			};

				//betchModifyPageModel.type=1;

				//CommonQueryBS.systemOut("value-------"+this.getPageElement("checkAll"));
				int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {
					String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"' order by sort";;
					List allSelect = sess.createSQLQuery(sql).list();
					if (!(allSelect.size() > 0)) {
						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
						return EventRtnType.NORMAL_SUCCESS;
					}else if(allSelect.size() > 500){
						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请选择少于500人批量修改！',null,220);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					String param = sql+"@1";
					param = param.replaceAll("\\'", "\\$");
					this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','批量维护',990,460,'"+param+"',ctxPath)");
					return EventRtnType.NORMAL_SUCCESS;
				}else{
					StringBuffer ids = new StringBuffer();
					PageElement pe = this.getPageElement("peopleInfoGrid");
					if(pe!=null){
						List<HashMap<String, Object>> list = pe.getValueList();
						if(list.size() > 500){
							this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请选择少于500人批量修改！',null,220);");
							return EventRtnType.NORMAL_SUCCESS;
						}
						for(int j=0;j<list.size();j++){
							HashMap<String, Object> map = list.get(j);
							Object usercheck = map.get("personcheck");
							if(usercheck.equals(true)){
								//判断是否有修改权限。
								String a0000 = map.get("a0000").toString();
								A01 a01 = (A01)sess.get(A01.class, a0000);
								String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
										" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
										" and c.type='0'";
								String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
								" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
								" and c.type='1' ";
								List elist = sess.createSQLQuery(editableSQL).list();
								List elist2 = sess.createSQLQuery(editableSQL2).list();
					/*			判断该人员的管理类别浏览权限------------------------------------------------------------------------------------------------------*/
								String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
								if(type == null || !type.contains("'")){
									type ="'zz'";//替换垃圾数据
								}
								List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
								if(elist3.size()>0){//无管理类别维护权限,即人员信息不可编辑
									continue;
								}
								if(elist2==null||elist2.size()==0){//维护权限

								}else {
									ids.append(map.get("a0000").toString()).append(",");
								}
							}
						}
					}
					if(ids.length()==0){
						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','所选人员不可操作！',null,180);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					String allids = ids.substring(0,ids.length()-1);
					this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','批量修改',990,380,'"+allids+"',ctxPath)");
					return EventRtnType.NORMAL_SUCCESS;
				}
		}

		/**
		 * 干部处审核
		 * @param a0000s
		 * @return
		 * @throws AppException
		 * @throws InvocationTargetException
		 * @throws IllegalAccessException
		 * @throws IntrospectionException
		 * @throws SQLException
		 */
		@PageEvent("cadresTTFAudit")
		public int cadresTTFAudit(String a0000s) throws SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException{
			//人员内码
			String[] a0000_array = a0000s.split("@#@");
			List<String> a0000_list = Arrays.asList(a0000_array);

			HBSession session = HBUtil.getHBSession();

			//校验人员是否已被审核
			StringBuffer sb = new StringBuffer();
			List<A01> a01_list = new ArrayList<A01>();
			A01 temp;
			for (String a0000 : a0000_list) {
				temp = (A01) session.get(A01.class, a0000);
				a01_list.add(temp);
			}
			for (A01 a01 : a01_list) {
				if((Integer.valueOf(a01.getA0190()==null?"0":a01.getA0190()) & Integer.valueOf(a01.getA0189()==null?"0":a01.getA0189())) == 1){
					sb.append(a01.getA0101() + ",");
				}
			}

			if(sb.length()>0){
				this.setMainMessage(sb.substring(0, sb.length()-1)+"等已审核!");
				return EventRtnType.FAILD;
			} else{
				//记录日志
				A01 a01_new;
				A01 a01_temp;
				for (A01 a01 : a01_list) {
					a01_new = new A01();
					a01_temp = new A01();
					a01_temp.setA0190(a01.getA0190());
					a01_new.setA0190("1");
					//applog.createLog("3801", "A01", a01.getA0000(), a01.getA0101(), "干部处审核", new Map2Temp().getLogInfo(a01_temp, a01_new));
				}

				//所选人员没被审核，则更新为干部处已审
				String sql;
				if(a0000_list.size()>=1000){
					sql = "update A01 set A0190='1' where A0000 in (:a00001) or A0000 in (:a00002) or A0000 in (:a00003)";
					List<String> a0000_list_1 = DBUtils.averageAssign(a0000_list, 3).get(0);
					List<String> a0000_list_2 = DBUtils.averageAssign(a0000_list, 3).get(1);
					List<String> a0000_list_3 = DBUtils.averageAssign(a0000_list, 3).get(2);
					SQLQuery query = session.createSQLQuery(sql);
					query.setParameterList("a00001", a0000_list_1);
					query.setParameterList("a00002", a0000_list_2);
					query.setParameterList("a00003", a0000_list_3);
					query.executeUpdate();
				} else {
					sql = "update A01 set A0190='1' where A0000 in (:a0000)";
					SQLQuery query = session.createSQLQuery(sql);
					query.setParameterList("a0000", a0000_list);
					query.executeUpdate();
				}
			}

			this.setMainMessage("审核成功!");
			this.setNextEventName("peopleInfoGrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}

		/**
		 * 干部一处审核
		 * @param a0000s
		 * @return
		 * @throws AppException
		 * @throws InvocationTargetException
		 * @throws IllegalAccessException
		 * @throws IntrospectionException
		 * @throws SQLException
		 */
		@PageEvent("cadresOAudit")
		public int cadresOAudit(String a0000s) throws SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException{
			//人员内码
			String[] a0000_array = a0000s.split("@#@");
			List<String> a0000_list = Arrays.asList(a0000_array);

			HBSession session = HBUtil.getHBSession();

			//校验人员是否已被审核
			StringBuffer sb = new StringBuffer();
			List<A01> a01_list = new ArrayList<A01>();
			A01 temp;
			for (String a0000 : a0000_list) {
				temp = (A01) session.get(A01.class, a0000);
				a01_list.add(temp);
			}
			for (A01 a01 : a01_list) {
				if((Integer.valueOf(a01.getA0190()==null?"0":a01.getA0190()) & Integer.valueOf(a01.getA0189()==null?"0":a01.getA0189())) == 1){
					sb.append(a01.getA0101() + ",");
				}
			}

			if(sb.length()>0){
				this.setMainMessage(sb.substring(0, sb.length()-1)+"等已审核!");
				return EventRtnType.FAILD;
			} else{
				//记录日志
				A01 a01_new;
				for (A01 a01 : a01_list) {
					a01_new = new A01();
					a01_new.setA0189("1");
					//applog.createLog("3802", "A01", a01.getA0000(), a01.getA0101(), "干部一处审核", new Map2Temp().getLogInfo(a01, a01_new));
				}

				//所选人员没被审核，则更新为干部处已审
				String sql;
				if(a0000_list.size()>=1000){
					sql = "update A01 set A0189='1' where A0000 in (:a00001) or A0000 in (:a00002) or A0000 in (:a00003)";
					List<String> a0000_list_1 = DBUtils.averageAssign(a0000_list, 3).get(0);
					List<String> a0000_list_2 = DBUtils.averageAssign(a0000_list, 3).get(1);
					List<String> a0000_list_3 = DBUtils.averageAssign(a0000_list, 3).get(2);
					SQLQuery query = session.createSQLQuery(sql);
					query.setParameterList("a00001", a0000_list_1);
					query.setParameterList("a00002", a0000_list_2);
					query.setParameterList("a00003", a0000_list_3);
					query.executeUpdate();
				} else {
					sql = "update A01 set A0189='1' where A0000 in (:a0000)";
					SQLQuery query = session.createSQLQuery(sql);
					query.setParameterList("a0000", a0000_list);
					query.executeUpdate();
				}
			}

			this.setMainMessage("审核成功!");
			this.setNextEventName("peopleInfoGrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}

		/**
		 * 干部一处解锁
		 * @param a0000s
		 * @return
		 * @throws AppException
		 * @throws InvocationTargetException
		 * @throws IllegalAccessException
		 * @throws IntrospectionException
		 * @throws SQLException
		 */
		@PageEvent("unLockAudit")
		public int unLockAudit(String a0000s) throws SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException{
			String flag=a0000s.split(",")[0];//1干部处取消审核，2干部一处取消审核，all取消所有审核

			String[] a0000_array = a0000s.split(",")[1].split("@#@");
			List<String> a0000_list = Arrays.asList(a0000_array);

			HBSession session = HBUtil.getHBSession();

			//获取所选人员
			List<A01> a01_list = new ArrayList<A01>();
			A01 temp;
			for (String a0000 : a0000_list) {
				temp = (A01) session.get(A01.class, a0000);
				a01_list.add(temp);
			}

			//记录日志
			A01 a01_new;
			for (A01 a01 : a01_list) {
				a01_new = new A01();
				String logtext="";
				if("1".equals(flag)){// A0190干部处
					a01_new.setA0190("0");
					logtext="干部处解锁审核";
				}else if("2".equals(flag)){
					a01_new.setA0189("0");
					logtext="干部一处解锁审核";
				}else{
					a01_new.setA0189("0");
					a01_new.setA0190("0");
					logtext="干部处、干部一处解锁审核";
				}
				//applog.createLog("3803", "A01", a01.getA0000(), a01.getA0101(), logtext, new Map2Temp().getLogInfo(a01, a01_new));
			}


			String sql;
			if(a0000_list.size()>=1000){
				if("1".equals(flag)){// A0190干部处
					sql = "update A01 set A0190='0' where A0000 in (:a00001) or A0000 in (:a00002) or A0000 in (:a00003)";
				}else if("2".equals(flag)){
					sql = "update A01 set A0189='0' where A0000 in (:a00001) or A0000 in (:a00002) or A0000 in (:a00003)";
				}else{
					sql = "update A01 set A0189='0',A0190='0' where A0000 in (:a00001) or A0000 in (:a00002) or A0000 in (:a00003)";
				}
				//sql = "update A01 set A0189='0',A0190='0' where A0000 in (:a00001) or A0000 in (:a00002) or A0000 in (:a00003)";
				List<String> a0000_list_1 = DBUtils.averageAssign(a0000_list, 3).get(0);
				List<String> a0000_list_2 = DBUtils.averageAssign(a0000_list, 3).get(1);
				List<String> a0000_list_3 = DBUtils.averageAssign(a0000_list, 3).get(2);
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameterList("a00001", a0000_list_1);
				query.setParameterList("a00002", a0000_list_2);
				query.setParameterList("a00003", a0000_list_3);
				query.executeUpdate();
			} else {
				if("1".equals(flag)){// A0190干部处
					sql = "update A01 set A0190='0' where A0000 in (:a0000)";
				}else if("2".equals(flag)){
					sql = "update A01 set A0189='0' where A0000 in (:a0000)";
				}else{
					sql = "update A01 set A0189='0',A0190='0' where A0000 in (:a0000)";
				}
				//sql = "update A01 set A0189='0',A0190='0' where A0000 in (:a0000)";
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameterList("a0000", a0000_list);
				query.executeUpdate();
			}

			this.setMainMessage("取消成功!");
			this.setNextEventName("peopleInfoGrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}



		/* poi导出excel
		 *
		 * gl
		 * */
		@PageEvent("expExcelFromGrid")
		public int expExcelFromGrid() throws RadowException, AppException {
			CustomQueryPageModel custom= new CustomQueryPageModel();
			String sql =	(String) this.request.getSession().getAttribute("allSelect");
			String field ="	select (" +
					"		SELECT" +
					"			CODE_NAME" +
					"		FROM" +
					"			code_value" +
					"		WHERE" +
					"			code_type = 'GB3304'" +
					"		AND code_value = A0117" +
					"	) AS a01171,zzxl as zzxl1,qrzxl as qrzxl1,";
			sql=sql.substring(6, sql.length());
			sql=field+sql;
			List<Map<String, Object>> list=custom.exportPoi(sql);
			if(list==null) {
				this.setMainMessage("查询失效！请重选机构树");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String infile=custom.insertList(list);
			infile=infile+"人员信息.xls";
			this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.reloadTree()");
			return EventRtnType.NORMAL_SUCCESS;
		}
}
