package com.insigma.siis.local.pagemodel.xbrm.ldbj;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.helperUtil.CommonSQLUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class PreviewQueryPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("previewListGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("setColumnWidth")
	public int setColumnWidth(){
		this.getExecuteSG().addExecuteCode("setColumnWidth");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//@PageEvent("init")
	/*public int init(){
		this.setNextBackFunc("previewListGrid.dogridquery");
		//this.getExecuteSG().addExecuteCode("radow.doEvent('previewListGrid.dogridquery');");
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	
	@PageEvent("previewListGrid.dogridquery")
	public int Query(int start,int limit) throws RadowException, AppException{
		String qvid=this.getPageElement("qvid").getValue();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String sql="";
		if(qvid==null||qvid.trim().equals("")||qvid.trim().equals("null")){
			sql="select 1 from dual where 1=2 ";
		}else{
			sql=HBUtil.getValueFromTab("qrysql", "JS_YJTJ", " qvid='"+qvid+"' ");
			sql=sql.replace("{v}", "").replace("{%v}", "").replace("{v%}", "").replace("{%v%}", "");
			String instr=" and a01.a0000 in ( select t.a0000 from Competence_Subperson t where t.userid = '"+userid+"' and t.type = '1' ) ";
			if("system".equals(username)){
				
			}else{
				//sql=sql+instr;
			}
		}
		this.pageQuery(sql, "SQL", start,limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	
	/**
	 * 生成列表属性
	 * @param qvid
	 * @return
	 * @throws AppException
	 */
	public List<HashMap<String, Object>> returnlistPreview(String qvid) throws AppException{
		List<HashMap<String, Object>> list=null;
		try{
			CommQuery commQuery =new CommQuery();
			String sql="select "
					+ " CONCAT(tblname,fldname) fldname,"//指标项 字段名
					+ " fldnamenote,"//指标项注释 中文字段名
					+ " tblname, "//信息集 表名
					+ " (select code_type from code_table_col where table_code=tblname and col_code=fldname) code_type"
					+ " from JS_YJTJ_fld "
					+ " where qvid='"+qvid+"' "
							+ " order by "+CommonSQLUtil.to_number("fldnum")+" asc ";
			list = commQuery.getListBySQL(sql); 
			//替换重复的列名字段
//			for(int i=0;list!=null&&i<list.size();i++){
//				String table=list.get(i).get("tblname").toString().toLowerCase();
//				String code=list.get(i).get("fldname").toString().toLowerCase();
//				//a01.a0221 a02.a0221 视图 a01.a0221 a01a0221(别名)
//				//a01.a0288 a02.a0288 视图 a01.a0288 a01a0288(别名)
//				/*if("a01.a0221".equals(table+"."+code)){
//					list.get(i).put("fldname", table+code);
//				}
//				if("a01.a0288".equals(table+"."+code)){
//					list.get(i).put("fldname", table+code);
//				}*/
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

}
