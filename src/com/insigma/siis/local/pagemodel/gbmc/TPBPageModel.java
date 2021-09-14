package com.insigma.siis.local.pagemodel.gbmc;


import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;


import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.publicServantManage.LongSQL;

import com.insigma.siis.local.lrmx.ExpRar;



import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;






public class TPBPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	public int initX() throws RadowException, AppException {

		// openWindow
		String id = this.getPageElement("subWinIdBussessId").getValue();

		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		if (ynId == null || yn_type == null || "".equals(ynId) || "".equals(ynId)) {
			String[] idtype = id.split(",");
			ynId = idtype[0];
			yn_type = idtype[1];
		}
		this.querybyid(ynId, yn_type);
  

		this.getPageElement("ynId").setValue(ynId);


		return EventRtnType.NORMAL_SUCCESS;
	}

	private int querybyid(String id, String yn_type) throws RadowException, AppException {
		if ("gbmc2".equals(yn_type)) {
			return querybyid_gbmc2(id, yn_type);
		} else {
			return querybyid_gbmc1(id, yn_type);
		}
	}

	/****
	 * 邹志林 2019-10-20 上会名册的获取数据
	 * 
	 * @param id
	 * @param    yn_type:gbmc1
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	private int querybyid_gbmc2(String id, String yn_type) throws RadowException, AppException {
		
		String userID = SysManagerUtils.getUserId();
		String FIELD_B0111 = "b0111";
		String jsonValue = (String) this.request.getSession().getAttribute(id);
		String unitWhereSQL = getWhereSql(jsonValue, 1, FIELD_B0111); // 单位条件
		

		String sql = "select a0000 from a01 where ";
		sql = sql
				+ " a0163 like '1%' and a01.status!='4' and (a01.a0000  in (select a02.a0000 from a02 where a02.A0201B in "
				+ " (select b0111 from competence_userdept where userid = '" + userID + "' and (" + unitWhereSQL
				+ ") ) " + "  and a0281='true') ) ";
        sql = LongSQL.gbmc2SQL(sql);
		this.getPageElement("sql").setValue(sql);
		this.request.getSession().setAttribute("sql", sql);
		this.getExecuteSG().addExecuteCode("$('#coordTable_div').load('pages/gbmc/gbmc2_youhua.jsp')");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	


	/****
	 * 邹志林 2019-10-20 干部名册的获取数据
	 * 
	 * @param id
	 * @param    yn_type:gbmc1
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	private int querybyid_gbmc1(String id, String yn_type) throws RadowException, AppException {
		//判断id是否能在a01_gbmc中查询到，若能查询，干部名册；若不能，机构名册
		HBSession sess = HBUtil.getHBSession();
		Object[] objs = (Object[])sess.createSQLQuery("select id,sql,name,CHECKEDGROUPID from a01_gbmc a where a.id = '"+id+"'").uniqueResult();
		
		String type = "";
		String sql = "";
		String name = "";
		String checkedgroupid = "";
		if(objs!=null&&!"".equals(objs)) {
			type = "1";
			Clob clob = (Clob)objs[1];
			try {
				sql = ClobToString(clob);
			} catch (Exception e) {
				// TODO: handle exception
			}
			String replaceSql2 = ", a01.a0101, a01.a0104, a01.A0107, a01.A0192a";
			if(sql.contains(replaceSql2)) {
				sql = sql.replace(replaceSql2, " ");
			}
			String replaceSql = ", a01.a1701";
			if(sql.contains(replaceSql)) {
				sql = sql.replace(replaceSql, " ");
			}
			
			if(objs[3]!=null&&!"".equals(objs[3])) {
				checkedgroupid = objs[3].toString();
			}else {
				checkedgroupid = "001.001";
			}
			sql = LongSQL.gbmc1SQL_HZ(sql,checkedgroupid);
			
			name = objs[2].toString();
			this.request.getSession().setAttribute("gbmcName", name);

		}else {
			type = "2";
			String userID = SysManagerUtils.getUserId();
			String FIELD_B0111 = "b0111";
			String jsonValue = (String) this.request.getSession().getAttribute(id);
			String unitWhereSQL = getWhereSql(jsonValue, 1, FIELD_B0111); // 人员条件
			String b0111 = getWhereSql(jsonValue, 1, "a2.a0201b"); // 单位条件
			
			sql = "select a0000 from a01 where ";
			sql = sql
					+ " a0163 like '1%' and a01.status!='4' and (a01.a0000  in (select a02.a0000 from a02 where a02.A0201B in "
					+ " (select b0111 from competence_userdept where userid = '" + userID + "' and (" + unitWhereSQL
					+ ") ) ) ) ";
	        sql = LongSQL.gbmc1SQL(sql,b0111);
	        
	        this.request.getSession().setAttribute("gbmcName", "");
		}
		System.out.println("-------------------------------:"+sql);
		this.getPageElement("sql").setValue(sql);
		this.request.getSession().setAttribute("sql", sql);
		if("1".equals(type)) {
			this.getExecuteSG().addExecuteCode("$('#coordTable_div').load('pages/gbmc/gbmc.jsp')");
		}else {
			this.getExecuteSG().addExecuteCode("$('#coordTable_div').load('pages/gbmc/gbmc1_youhua.jsp')");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String ClobToString(Clob clob) throws SQLException, IOException { 
    	
        String reString = ""; 
        java.io.Reader is = clob.getCharacterStream();// 得到流 
        BufferedReader br = new BufferedReader(is); 
        String s = br.readLine(); 
        StringBuffer sb = new StringBuffer(); 
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING 
            sb.append(s); 
            s = br.readLine(); 
        } 
        reString = sb.toString(); 
        return reString; 
    }

	
	/****
	 * isUnit:是否单位
	 * 
	 * @param selectUnitId
	 * @param isUnit
	 * @return
	 */
	private String getWhereSql(String selectUnitId, int flag, String getCode) {
		boolean multiUnit = false;
		JSONObject objUnitId = JSONObject.fromObject(selectUnitId);
		StringBuffer sqlBuffer = new StringBuffer();

		multiUnit = objUnitId.keySet().size() > 1;

		StringBuffer removeUnitIds = new StringBuffer(",");
		if (flag == 1) {
			// 找到排除单位
			for (Object oKey : objUnitId.keySet()) {
				String unitId = (String) oKey;
				if (unitId.substring(unitId.lastIndexOf(".")).length() > 20) {
					removeUnitIds.append(unitId.substring(0, unitId.lastIndexOf(".")));
					removeUnitIds.append(",");
				}
			}
		}

		for (Object oKey : objUnitId.keySet()) {
			String unitId = (String) oKey;
			String array[] = objUnitId.get(unitId).toString().split(":");

			if (unitId.startsWith("P")) {
				// 是人员 Pa10dac70-544d-4347-bf7c-f192a7f7db2c
				if (flag == 2) {
					sqlBuffer.append(",'" + unitId.substring(1) + "'");
				} else if (flag == 3) {
					sqlBuffer.append(",'" + unitId.substring(1) + "'");
				}
			} else if (unitId.substring(unitId.lastIndexOf(".")).length() > 20) {
				/// 是单位人员 001.001.00B.a10dac70-544d-4347-bf7c-f192a7f7db2c
				if (flag == 2) {
					sqlBuffer.append(",'" + unitId.substring(unitId.lastIndexOf(".") + 1) + "'");
				} else if (flag == 3) {
					sqlBuffer.append(",'" + unitId + "'");
				}
			} else {
				if (flag == 1) {
					if (removeUnitIds.toString().contains("," + unitId + ",")) {
						continue;
					}
					if ("true".equals(array[1]) && "true".equals(array[2])) {
						sqlBuffer.append(" OR " + getCode + " like '" + unitId + "%' ");
						multiUnit = true;
					} else if (!"true".equals(array[1]) && "true".equals(array[2])) {
						sqlBuffer.append(" OR " + getCode + " = '" + unitId + "' ");
					}
				}
			}
		}
		if (flag == 1) {
			sqlBuffer.delete(0, 3);
		} else {
			sqlBuffer.delete(0, 1);
		}
		return sqlBuffer.toString();
	}





	// 导出excel
	@PageEvent("exportTPBExcel")
	public int exportTPBExcel(String param) throws Exception {
		String infile = null;
		infile = exportGBMCAction();
		if(!com.insigma.siis.local.util.StringUtil.isEmpty(this.getMainMessage())) {
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;
	}

	

	private String exportGBMCAction() throws RadowException, IOException, AppException {
		String infile;
		String id = this.getPageElement("subWinIdBussessId").getValue();
		String[] idtype = id.split(",");
		
		String yn_type = idtype[1];
		
		HBSession sess = HBUtil.getHBSession();
		Object[] objs = (Object[])sess.createSQLQuery("select id,sql,name from a01_gbmc a where a.id = '"+idtype[0]+"'").uniqueResult();
		
		ExportAsposeBS e = new ExportAsposeBS();
		String expFile = ExpRar.expFile();
		String sql = this.getPageElement("sql").getValue();
		if(objs!=null&&!"".equals(objs)) {
			String rootPath = e.getRootPath();
			String tempPath = rootPath + "gbhmc_hz.xls";
			String path = expFile;
			
			String expName = (String)this.request.getSession().getAttribute("gbmcName");
			infile = expFile + expName + ".xls";
			
			e.exportHYMC_HZ(tempPath, path, sql,expName);
		}else {
			if ("gbmc2".equals(yn_type)) {// 2
				String rootPath = e.getRootPath();
				String tempPath = rootPath + "gbhmc1.xls";
				String path = expFile;
				String expName = "干部花名册（一人一行）";
				infile = expFile + expName + ".xls";
				
				e.exportHYMC(tempPath, path, sql);
			} else {
				String rootPath = e.getRootPath();
				String expName = "干部花名册（按机构分组）";
				infile = expFile + expName + ".xls"; 
				
				e.expGBMC(sql, expFile);
			}
		}
		
		return infile;
	}
	


}