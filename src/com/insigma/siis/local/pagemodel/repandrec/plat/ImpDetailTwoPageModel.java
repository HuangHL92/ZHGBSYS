package com.insigma.siis.local.pagemodel.repandrec.plat;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.fr.third.org.apache.poi.hssf.usermodel.HSSFCell;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFCellStyle;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFRow;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.CodeManager;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.BeanUtil;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.DataVerifyBS;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.entity.VerifyProcess;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.sysorg.org.dto.B01DTO;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify.VerifyDataThread;

public class ImpDetailTwoPageModel extends PageModel {
	
	public static void main(String[] args) {
		CommonQueryBS.systemOut(20/69+"");
	}
	
	@Override
	public int doInit() throws RadowException {
		String imprecordid = request.getParameter("initParams");
		if(StringUtil.isEmpty(imprecordid)){
			throw new RadowException("获取参数为空！");
		}
		String bsType ="2";    //业务类型 （bsType）： 1-机构校验（包含下级机构）；2-导入校验 
		String b0111OrimpID;
		try {
			b0111OrimpID = imprecordid;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("获取校验业务类型异常！异常信息："+e.getMessage());
		}
		if (StringUtil.isEmpty(b0111OrimpID) ) {
			if("1".equals(bsType)){
				throw new RadowException("未获取到要校验的组织ID!");
			}else if("2".equals(bsType)){
				throw new RadowException("未获取到导入记录ID!");
			}else{
				throw new RadowException("不支持的业务类型!");
			}
		}
		
		if(StringUtil.isEmpty(bsType)){
			throw new RadowException("未获取校验业务类型！");
		}
		
		 if("2".equals(bsType)){
			//隐藏保存按钮
			this.getExecuteSG().addExecuteCode("Ext.getCmp('btnSaveUpdated').setVisible(false);");
			this.createPageElement("updatedDiv", ElementType.NORMAL, false).setDisplay(false); 
		}else if("1".equals(bsType)){
			//隐藏按钮 接收 打回
			this.getExecuteSG().addExecuteCode("Ext.getCmp('impBtn').setVisible(false);");
			this.getExecuteSG().addExecuteCode("Ext.getCmp('rejectBtn').setVisible(false);");
			HBSession sess = HBUtil.getHBSession();
			B01 b01 = (B01)sess.get(B01.class, b0111OrimpID);
			this.getPageElement("updated").setValue(b01.getUpdated());;
		}
		
		this.getPageElement("bsType").setValue(bsType);
		this.getPageElement("b0111OrimpID").setValue(b0111OrimpID);
		
		//设置默认校验方案
		String vsc001 = DataVerifyBS.getBaseVerifyScheme();
		if(!StringUtil.isEmpty(vsc001)){
			this.getPageElement("vsc001").setValue(vsc001);
		}
		this.setNextEventName("errorGrid.dogridquery");
		this.setNextEventName("errorDetailGrid.dogridquery");
		return 0;
	}


	

	/**
	 * 校验前验证
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("btnSave.onclick")
	public int saveBefore() throws RadowException{
		String bsType = this.getPageElement("bsType").getValue();
		String b0111 = this.getPageElement("b0111OrimpID").getValue();
		HBSession sess = HBUtil.getHBSession();
		@SuppressWarnings("unchecked")
		List<VerifyProcess> list =  sess.createQuery("from VerifyProcess where batchNum ='"+b0111+"' and bsType = '"+bsType+"'").list();
		if(list!=null && !list.isEmpty() && list.get(0)!=null && "3".equals(list.get(0).getResultFlag())){//3-代表校验进行中
			this.setMainMessage("其他进程正在此数据进行校验，是否继续校验?");
			this.setMessageType(EventMessageType.CONFIRM);
			this.addNextEvent(NextEventValue.YES,"verifyData");
		}else{
			this.setNextEventName("verifyData");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 校验
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("verifyData")
	@Transaction
	public int save() throws RadowException, AppException {
		this.createPageElement("btnSave", ElementType.BUTTON, false).setDisabled(true);
		String vsc001 = this.getPageElement("vsc001").getValue();
		String bsType = this.getPageElement("bsType").getValue();
		String b0111 = this.getPageElement("b0111OrimpID").getValue();
		
		HBSession sess = HBUtil.getHBSession();
		sess.createSQLQuery("delete from  Verify_Process where batch_Num ='"+b0111+"' and bs_Type = '"+bsType+"' and Result_Flag=3").executeUpdate();
		sess.flush();
		String uuid = UUID.randomUUID().toString();
		VerifyProcess vp =  new VerifyProcess();
		vp.setProcessId(uuid);
		vp.setBsType(bsType);//业务类型
		vp.setResultFlag(3L);//进行中
		vp.setBatchNum(b0111);//批次号
		sess.save(vp);
		sess.flush();
		if (!StringUtil.isEmpty(vsc001) && !StringUtil.isEmpty(b0111) && !StringUtil.isEmpty(bsType)) {
//			try {
//				VerifyDataThread vdt = new VerifyDataThread(vp,b0111, vsc001,bsType,PrivilegeManager.getInstance().getCueLoginUser(),"0");
//				Thread t1=new Thread(vdt,"数据校验进程");
//				t1.start();
//			} catch (Exception e) {
//				vp.setResultFlag(-1L);
//				e.printStackTrace();
//				throw new AppException("校验过程中发生异常：" + e.getMessage());
//			}
			
		} else {
			throw new AppException("组织信息、校验方案或业务类型为空，不能开始校验！");
		}
		this.getPageElement("errorGrid").setValueList(new ArrayList());//清空页面异常信息
		this.getPageElement("errorDetailGrid").setValueList(new ArrayList());//清空页面异常信息
		this.getExecuteSG().addExecuteCode("radow.doEvent('queryBar','"+uuid+"@0')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询进度条
	 * @param processId
	 * @return
	 */
	@PageEvent("queryBar")
	@AutoNoMask
	public int queryBar(String processIdPageVal){
		if(StringUtil.isEmpty(processIdPageVal)){
			this.setMainMessage("查询进度获取参数异常！");
			this.createPageElement("btnSave", ElementType.BUTTON, false).setDisabled(false);
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] params = processIdPageVal.split("@");
		String processId = params[0];
		String pageValStr = StringUtil.isEmpty(params[1])?"0":params[1];
		long pageVal = Long.parseLong(pageValStr);
		HBSession sess = HBUtil.getHBSession();
		sess.getSession().clear();
		VerifyProcess vp = (VerifyProcess) sess.get(VerifyProcess.class, processId);
		if(vp!=null   ){
			if(-1L!=vp.getResultFlag()  ){
				long currentNum = vp.getCurrentNum()==null?1L:vp.getCurrentNum();
				long totalNum = vp.getTotalNum()==null?100L:vp.getTotalNum();
				float num= currentNum/(float)totalNum;   
				DecimalFormat df = new DecimalFormat("0");//格式化小数   
				String val = df.format(num*100);
				CommonQueryBS.systemOut("total-"+totalNum+",currentNum-"+currentNum+",num-"+num+",val-"+val);
				if(3L==vp.getResultFlag()||pageVal < Long.parseLong(val)){
					this.getExecuteSG().addExecuteCode("progress("+val+",'"+processId+"')");
				}else{
					this.setMainMessage(vp.getProcessMsg());
					this.createPageElement("btnSave", ElementType.BUTTON, false).setDisabled(false);
					this.setNextEventName("errorGrid.dogridquery");
					this.setNextEventName("errorDetailGrid.dogridquery");
				}
			}else{
				this.setMainMessage(vp.getProcessMsg());
				this.createPageElement("btnSave", ElementType.BUTTON, false).setDisabled(false);
				this.setNextEventName("errorGrid.dogridquery");
				this.setNextEventName("errorDetailGrid.dogridquery");
			}
		}/*else{
			//session 没有来得及提交，则重新调用该方法
			this.getExecuteSG().addExecuteCode("radow.doEvent('queryBar','"+processIdPageVal+"')");
		}*/
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 保存校验标记
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("btnSaveUpdated.onclick")
	@Transaction
	public int saveUpdated() throws RadowException, AppException {
		String bsType = this.getPageElement("bsType").getValue();
		String b0111 = this.getPageElement("b0111OrimpID").getValue();
		String updated = this.getPageElement("updated").getValue();
		if(StringUtil.isEmpty(updated)){
			throw new AppException("校验标记为空！");
		}
		if(!"1".equals(bsType)){
			return EventRtnType.NORMAL_SUCCESS;
		}
		if ( !StringUtil.isEmpty(b0111)) {
			HBSession sess = HBUtil.getHBSession();
			//sess.createSQLQuery("update b01 a set a.updated = '"+updated+"' where b0111 like '"+b0111+"%'").executeUpdate();
			@SuppressWarnings("unchecked")
			List<B01> list = sess.createQuery("from B01 a where b0111 like '"+b0111+"%'").list();
			
			for(B01 b01 :list){
				B01DTO b01Dto = new B01DTO();
				BeanUtil.copy(b01, b01Dto);
				b01.setUpdated(updated);
				sess.update(b01);
				//记录日志
				try {
						new LogUtil().createLog("643", "B01", b01.getB0111(), b01.getB0101(), null,Map2Temp.getLogInfo(b01Dto,b01) );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
				
		} else {
			this.setMainMessage("组织信息为空，不能保存校验标记！");
		}
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 双击错误信息，显示相关错误详细信息
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("errorGrid.rowdbclick")
	@GridDataRange(GridData.allrow)
	@AutoNoMask
	public int dbClickRowErrorGrid() throws RadowException{
		
		Grid grid = (Grid)this.getPageElement("errorGrid");
		int cueRowIndex = grid.getCueRowIndex();
		List<HashMap<String,Object>> gridList = grid.getValueList();
		String vel002 = "";
		String vel003 = "";
		//1.选该行
		for(int i=0;i<gridList.size();i++){
			HashMap<String,Object> map = gridList.get(i);
			if(cueRowIndex==i){
				vel002 = map.get("vel002").toString();
				vel003 = map.get("vel003").toString();
			}
		}
		grid.selectRow(cueRowIndex);
		
		//2.查询该行对应的校验规则
		this.getPageElement("dbClickvel002").setValue(vel002);//选中行
		this.getPageElement("dbClickvel003").setValue(vel003);//选中行
		this.setNextEventName("errorDetailGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查看全部错误详情
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("allErrorDetail.onclick")
	public int allErrorDetail() throws RadowException{
		this.getPageElement("dbClickvel002").setValue("");
		this.getPageElement("dbClickvel003").setValue("");
		this.setNextEventName("errorDetailGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询错误信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("errorGrid.dogridquery")
	@NoRequiredValidate
	public int doErrorGridQuery(int start,int limit) throws RadowException{
		String bsType = this.getPageElement("bsType").getValue();
		String b0111OrimpID = this.getPageElement("b0111OrimpID").getValue();
		if(StringUtil.isEmpty(bsType) || StringUtil.isEmpty(b0111OrimpID) ){
			throw new RadowException("获取参数为空！");
		}

		Imprecord impRecord = null;
		if("2".equals(bsType)){
			impRecord = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, b0111OrimpID);
		}
		
		String a01Name = "1".equals(bsType)?"A01":(impRecord!=null && "2".equals(impRecord.getImpstutas()))?"A01":"A01_temp";
		String b01Name = "1".equals(bsType)?"B01":(impRecord!=null && "2".equals(impRecord.getImpstutas()))?"B01":"B01_temp";
		
		
		String b0111 = this.getPageElement("b0111OrimpID").getValue();
		StringBuffer sqlbf = new StringBuffer();
		if(DBType.MYSQL ==DBUtil.getDBType()){
			sqlbf.append("Select  Vel003    ,           ")
			.append("       Case Vel003                 ")
			.append("         When '1' Then             ")
			.append("          (Select ifnull(A0101, '')")
			.append("             From "+a01Name+"      ")
			.append("            Where A0000 = Vel002   ")
			.append("              limit 0,1)   	    ")
			.append("         Else                      ")
			.append("          (Select ifnull(B0101, '')")
			.append("             From "+b01Name+"      ")
			.append("            Where B0111 = Vel002   ")
			.append("              limit 0,1)    	    ")
			.append("       End Vel002_name,            ")
			.append("           Vel002,                 ")
			.append("           count(1) count_nums       ")
			.append("  From Verify_Error_List a         ")
			.append(" Where 1 = 1                       ");
		}else if(DBType.ORACLE ==DBUtil.getDBType()){
			sqlbf.append("Select    Vel003 ,              ")
			.append("       Case Vel003                 ")
			.append("         When '1' Then             ")
			.append("          (Select Nvl(A0101, '')   ")
			.append("             From "+a01Name+"      ")
			.append("            Where A0000 = Vel002   ")
			.append("              And Rownum = 1)      ")
			.append("         Else                      ")
			.append("          (Select Nvl(B0101, '')   ")
			.append("             From "+b01Name+"      ")
			.append("            Where B0111 = Vel002   ")
			.append("              And Rownum = 1)      ")
			.append("       End Vel002_name,            ")
			.append("           Vel002,                 ")
			.append("           count(1) count_nums       ")
			.append("  From Verify_Error_List a         ")
			.append(" Where 1 = 1                       ");
		}
		sqlbf.append(" and   a.vel004 ='"+b0111+"'    AND a.vel005 = '"+bsType+"'");
		sqlbf.append(" group by Vel002,vel003");
		sqlbf.append(" order by vel003,Vel002_name");
		CommonQueryBS.systemOut("--->>>"+sqlbf.toString());
		this.pageQuery(sqlbf.toString(),"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 查询错误详细信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("errorDetailGrid.dogridquery")
	@NoRequiredValidate      
	public int doErrorDetailGridQuery(int start,int limit) throws RadowException{
		String bsType = this.getPageElement("bsType").getValue();
		String b0111OrimpID = this.getPageElement("b0111OrimpID").getValue();
		if(StringUtil.isEmpty(bsType) || StringUtil.isEmpty(b0111OrimpID) ){
			throw new RadowException("获取参数为空！");
		}
		
		Imprecord impRecord = null;
		if("2".equals(bsType)){
			impRecord = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, b0111OrimpID);
		}
		
		String a01Name = "1".equals(bsType)?"A01":(impRecord!=null && "2".equals(impRecord.getImpstutas()))?"A01":"A01_temp";
		String b01Name = "1".equals(bsType)?"B01":(impRecord!=null && "2".equals(impRecord.getImpstutas()))?"B01":"B01_temp";
		
		String dbClickvel002 = this.getPageElement("dbClickvel002").getValue();
		String dbClickvel003 = this.getPageElement("dbClickvel003").getValue();
		String b0111 = this.getPageElement("b0111OrimpID").getValue();
		StringBuffer sqlbf = new StringBuffer();
		if(DBType.MYSQL ==DBUtil.getDBType()){
			sqlbf.append("Select Vel001,                ")
			.append("       Case Vel003                 ")
			.append("         When '1' Then             ")
			.append("          (Select ifnull(A0101, '')")
			.append("             From "+a01Name+"      ")
			.append("            Where A0000 = Vel002   ")
			.append("              limit 0,1)   	    ")
			.append("         Else                      ")
			.append("          (Select ifnull(B0101, '')")
			.append("             From "+b01Name+"      ")
			.append("            Where B0111 = Vel002   ")
			.append("              limit 0,1)    	    ")
			.append("       End Vel002_name,            ")
			.append("           Vel002,                 ")
			.append("         Vru003,	")
			.append("       Case Vel003  When '1' Then  ")
			.append("          '人员'                   ")
			.append("         Else                      ")
			.append("          '机构'                   ")
			.append("       End Vel003_name,            ")
			.append("        Vel003,                    ")
			.append("       Vel004,                     ")
			.append("       Vel005,                     ")
			.append("       ifnull(Vel006, '') Vel006,     ")
			.append("       ifnull(c.table_name,'') Vel007,")
			.append("       ifnull(d.col_name,'') Vel008,  ")
			.append("       Vel009,                     ")
			.append("       Vel010                      ")
			.append("  From Verify_Error_List a         ")
			.append("  Left Join Code_Table c           ")
			.append("    On Vel007 = c.Table_Code       ")
			.append("  Left Join Code_Table_Col d       ")
			.append("    On Vel007 = d.Table_Code       ")
			.append("   And Vel008 = d.Col_Code         ")
			.append(" Where 1 = 1                       ");
		}else if(DBType.ORACLE ==DBUtil.getDBType()){
			sqlbf.append("Select Vel001,                ")
			.append("       Case Vel003                 ")
			.append("         When '1' Then             ")
			.append("          (Select Nvl(A0101, '')   ")
			.append("             From "+a01Name+"      ")
			.append("            Where A0000 = Vel002   ")
			.append("              And Rownum = 1)      ")
			.append("         Else                      ")
			.append("          (Select Nvl(B0101, '')   ")
			.append("             From "+b01Name+"      ")
			.append("            Where B0111 = Vel002   ")
			.append("              And Rownum = 1)      ")
			.append("       End Vel002_name,            ")
			.append("           Vel002,                 ")
			.append("         Vru003,		")
			.append("       Case Vel003  When '1' Then  ")
			.append("          '人员'                   ")
			.append("         Else                      ")
			.append("          '机构'                   ")
			.append("       End Vel003_name,                 ")
			.append("        Vel003,                 ")
			.append("       Vel004,                     ")
			.append("       Vel005,                     ")
			.append("       Nvl(Vel006, '') Vel006,     ")
			.append("       nvl(c.table_name,'') Vel007,")
			.append("       nvl(d.col_name,'') Vel008,  ")
			.append("       Vel009,                     ")
			.append("       Vel010                      ")
			.append("  From Verify_Error_List a         ")
			.append("  Left Join Code_Table c           ")
			.append("    On Vel007 = c.Table_Code       ")
			.append("  Left Join Code_Table_Col d       ")
			.append("    On Vel007 = d.Table_Code       ")
			.append("   And Vel008 = d.Col_Code         ")
			.append(" Where 1 = 1                       ");
		}
		sqlbf.append(" and   a.vel004 ='"+b0111+"'    AND a.vel005 = '"+bsType+"' ");
		if(!StringUtil.isEmpty(dbClickvel002)&&!StringUtil.isEmpty(dbClickvel003)){
			sqlbf.append(" and   a.vel002 ='"+dbClickvel002+"'    AND a.vel003 = '"+dbClickvel003+"'");
		}
		sqlbf.append(" order by Vel002,Vel007");
		CommonQueryBS.systemOut("------->"+sqlbf);
		this.pageQuery(sqlbf.toString(),"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	/**
	 * 双击错误详细信息，跳转到相关修改页面
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("errorDetailGrid.rowdbclick")
	@GridDataRange(GridData.allrow)
	@AutoNoMask
	public int dbClickRowErrorDetailGrid() throws RadowException, AppException{
		//导入校验(业务类型为2)且未导入正式库，则返回
		String bsType = this.getPageElement("bsType").getValue();
		String b0111OrimpID = this.getPageElement("b0111OrimpID").getValue();
		Imprecord impRecord = null;
		if("2".equals(bsType)){
			impRecord = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, b0111OrimpID);
		}
		
		String a01Name = "1".equals(bsType)?"A01":(impRecord!=null && "2".equals(impRecord.getImpstutas()))?"A01":"A01_temp";
		
		if(StringUtil.isEmpty(bsType) || !"A01".equals(a01Name)){
			this.setMainMessage("请接收数据之后，双击人员错误详情，进入人员信息修改界面！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		Grid grid = (Grid)this.getPageElement("errorDetailGrid");
		int cueRowIndex = grid.getCueRowIndex();
		List<HashMap<String,Object>> gridList = grid.getValueList();
		String vel002 = "";	//主体ID
		String vel003 = "";	//主体类型：1-人员；2-机构；
		String vel004 = "";	//批次ID
		String vel005 = "";	//业务类型
		for(int i=0;i<gridList.size();i++){
			HashMap<String,Object> map = gridList.get(i);
			if(cueRowIndex==i){
				vel002 = map.get("vel002").toString();
				vel003 = map.get("vel003").toString();
				vel004 = map.get("vel004").toString();
				vel005 = map.get("vel005").toString();
			}
		}
		grid.selectRow(cueRowIndex);
		
		if(StringUtil.isEmpty(vel002)){
			throw new AppException("无法获取错误主体ID！");
		}
		
		if(!StringUtil.isEmpty(vel003)){
			if("1".equals(vel003)){
				String errorInfoJsonStr = DataVerifyBS.getErrorInfo(vel002, vel004, vel005);
				String conp = this.request.getContextPath();
				this.getExecuteSG().addExecuteCode("addTab('','"+vel002+"','"+conp+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false,"+errorInfoJsonStr+")");
			}else if("2".equals(vel003)){
				//单位不处理
			}else{
				throw new AppException("错误主体的类型异常：不是【人员】，也不是【单位】！");
			}
		}else{
			throw new AppException("无法获取错误主体的类型！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**************************************************翔飞代码**********************************************/
	/**
	 * 导入正确数据
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("impBtn.onclick")
	@NoRequiredValidate
	public int impmodelOnclick()throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
			sess.beginTransaction();
			String imprecordid = this.getPageElement("b0111OrimpID").getValue();
			/**
			 * 历史库代码暂无
			 */
			Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
			String impdeptid = imp.getImpdeptid();
			String ftype = imp.getFiletype();
//			if(imp.getIsvirety() == null || imp.getIsvirety().equals("") ||imp.getIsvirety().equals("0")){
//				this.setMainMessage("请先进行校验，再导入数据");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
//			if(imp.getWrongnumber() != null && !imp.getWrongnumber().equals("") 
//					&& Long.parseLong(imp.getWrongnumber())>0){
//				this.setMainMessage("存在错误数据，请打回。");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			if(imp.getImpstutas() != null && !imp.getImpstutas().equals("1")){
				if(imp.getImpstutas().equals("2")){
					this.setMainMessage("数据已导入，不能重复导入。");
				} else {
					this.setMainMessage("数据已打回，不能导入。");
				}
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				String orgs = "select mt.a0000 from (select distinct a.a0000 a0000 from a02 a where a.a0201b in (select b0111 from b01 where b0111 like '"+ imp.getImpdeptid() +"%')) mt where mt.a0000=";
//				String orgs = "select  a.a0000 a0000 from a02 a where a.a0201b like '"+ imp.getImpdeptid() +"%' and a.a0000 = ";
				String borgs = "select mt.b0111 from (select b0111 from b01 where b0111 like '"+ imp.getImpdeptid() +"%') mt ";
				
				String houzhui = imp.getFilename().substring(imp.getFilename().lastIndexOf(".") + 1);

				//a06
				//delete 
//				sess.createSQLQuery(" delete from a06 where exists (select t.a0600 from(select k.a0600 from A06_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a0600=a06.a0600) or exists ("+orgs+"a06.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a06 where exists (select 1 from a02 a where a.a0000=a06.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a06 where exists (select 1 from A06_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0600=a06.a0600)").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a06 select A0600,A0000,A0601,A0602, A0604, A0607, A0611, A0614, t.SORTID, t.UPDATED"+
	       				" from a06_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a08
				//delete 
//				sess.createSQLQuery(" delete from a08 where exists (select t.a0800 from(select k.a0800 from A08_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a0800=a08.a0800) or exists ("+orgs+"a08.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a08 where exists (select 1 from a02 a where a.a0000=a08.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a08 where exists (select 1 from A08_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0800=a08.a0800)").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a08 select  A0000,A0800,A0801A,A0801B,A0804,A0807,A0811,A0814,A0824,A0827,"+
						" A0831,A0832,A0834,A0835,A0837,A0838,A0839,A0898,A0899,A0901A,"+
						" A0901B,A0904,SORTID,t.updated,t.wage_used"+
	       				" from a08_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a11
				//delete 
//				sess.createSQLQuery(" delete from a11 where exists (select t.a1100 from(select k.a1100 from A11_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a1100=a11.a1100) or exists ("+orgs+"a11.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a11 where exists (select 1 from a02 a where a.a0000=a11.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a11 where exists (select 1 from A11_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a1100=a11.a1100)").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a11 select  A0000,A1100,A1101,A1104,A1107,A1107A,a1107b ,a1111 ,a1114 ,a1121a ,"+
						"a1127 ,a1131 ,a1134 ,a1151 ,t.updated,t.A1108,t.A1107C"+
	       				" from a11_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a14
				//delete 
//				sess.createSQLQuery(" delete from a14 where exists (select t.a1400 from(select k.a1400 from A14_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a1400=a14.a1400) or exists ("+orgs+"a14.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a14 where exists (select 1 from a02 a where a.a0000=a14.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a14 where exists (select 1 from A14_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a1400=a14.a1400)").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a14 select a0000, a1400, a1404a, a1404b, a1407 ,a1411a ,a1414 ,a1415, a1424, a1428,"+ 
						"t.sortid ,t.updated "+
	       				" from a14_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a15
				//delete 
//				sess.createSQLQuery(" delete from a15 where exists (select t.a1500 from(select k.a1500 from A15_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a1500=a15.a1500) or exists ("+orgs+"a15.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a15 where exists (select 1 from a02 a where a.a0000=a15.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a15 where exists (select 1 from A15_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a1500=a15.a1500)").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a15 select a0000, a1500, a1517, a1521, t.updated, a1527  "+
	       				" from a15_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a29
				//delete 
//				sess.createSQLQuery(" delete from a29 where exists (select t.a0000 from(select k.a0000 from A29_temp k where k.imprecordid ='"+ imprecordid + 
//						"') t where t.a0000=a29.a0000) or exists ("+orgs+"a29.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a29 where exists (select 1 from a02 a where a.a0000=a29.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a29 where exists (select 1 from A29_temp k where k.imprecordid ='"+ imprecordid + 
						"' and k.a0000=a29.a0000)").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a29 select  a0000, a2907 ,a2911, a2921a ,a2941, a2944, a2947 ,a2949, t.updated," +
						"t.a2970,t.a2970a,t.a2970b,t.a2970c,t.a2947a,t.a2950,t.a2951,A2921B,A2947B,t.A2921C,t.A2921d "+
	       				" from a29_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a30
				//delete 
//				sess.createSQLQuery(" delete from a30 where exists (select t.a0000 from(select k.a0000 from A30_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a0000=a30.a0000) or exists ("+orgs+"a30.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a30 where exists (select 1 from a02 a where a.a0000=a30.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a30 where exists (select 1 from A30_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0000=a30.a0000)").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a30 select a0000,a3001, a3004, a3007a ,a3034 ,t.updated  "+
	       				" from a30_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a31
				//delete 
//				sess.createSQLQuery(" delete from a31 where exists (select t.a0000 from(select k.a0000 from A31_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a0000=a31.a0000) or exists ("+orgs+"a31.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a31 where exists (select 1 from a02 a where a.a0000=a31.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a31 where exists (select 1 from A31_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0000=a31.a0000)").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a31 select  a0000,a3101, a3104, a3107, a3117a,a3118, a3137, a3138 ,t.updated,t.a3140,t.a3141,t.a3142  "+
	       				" from a31_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a36
				//delete 
//				sess.createSQLQuery(" delete from a36 where exists (select t.a3600 from(select k.a3600 from A36_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a3600=a36.a3600) or exists ("+orgs+"a36.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a36 where exists (select 1 from a02 a where a.a0000=a36.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a36 where exists (select 1 from A36_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a3600=a36.a3600)").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a36 select  a0000,a3600, a3601, a3604a, a3607, a3611, a3627 ,sortid ,t.updated "+
	       				" from a36_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a37
				//delete 
//				sess.createSQLQuery(" delete from a37 where exists (select t.a0000 from(select k.a0000 from A37_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a0000=a37.a0000) or exists ("+orgs+"a37.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a37 where exists (select 1 from a02 a where a.a0000=a37.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a37 where exists (select 1 from A37_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0000=a37.a0000) ").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a37 select a0000,a3701,a3707a,a3707c,a3707e,a3707b,a3708,a3711,a3714,t.updated "+
	       				" from a37_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a41
				//delete 
//				sess.createSQLQuery(" delete from a41 where exists (select t.a4100 from(select k.a4100 from A41_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a4100=a41.a4100) or exists ("+orgs+"a41.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a41 where exists (select 1 from a02 a where a.a0000=a41.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a41 where exists (select 1 from A41_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a4100=a41.a4100)").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a41 select  a4100, a0000,a1100 ,a4101, a4102, a4103 ,a4104, a4105 ,a4199"+
	       				" from a41_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a53
				//delete 
//				sess.createSQLQuery(" delete from a53 where exists (select t.a5300 from(select k.a5300 from A53_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a5300=a53.a5300) or exists ("+orgs+"a53.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a53 where exists (select 1 from a02 a where a.a0000=a53.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a53 where exists (select 1 from A53_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a5300=a53.a5300)").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a53 select a0000,a5300,a5304,a5315,a5317,a5319,a5321,a5323,a5327,a5399,t.updated"+
	       				" from a53_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a57
				//delete 
//				sess.createSQLQuery(" delete from a57 where exists (select t.a0000 from(select k.a0000 from A57_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a0000=a57.a0000) or exists ("+orgs+"a57.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a57 where exists (select 1 from a02 a where a.a0000=a57.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a57 where exists (select 1 from A57_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0000=a57.a0000)").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a57 select a0000,a5714 ,t.updated,PHOTODATA,PHOTONAME,PHOTSTYPE,PHOTOPATH "+
	       				" from a57_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a01
				//update 统计关系所在单位
//				sess.createSQLQuery("update A01_temp t set t.a0195 = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.a0195 and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "' and exists (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.A0195 and d.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("update A01_temp t,B01TEMP_B01 d set t.a0195 = d.new_b0111 where t.imprecordid='"+ imprecordid + "' and d.temp_b0111=t.A0195").executeUpdate();
				sess.createSQLQuery("update A01_temp t set t.a0195 ='-1' where imprecordid='"+ imprecordid + "' and t.a0195='XXX'").executeUpdate();
				//delete 
//				sess.createSQLQuery(" delete from a01 where exists (select t.a0000 from(select k.a0000 from A01_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a0000=a01.a0000) or exists ("+orgs+"a01.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a01 where exists (select 1 from a02 a where a.a0000=a01.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a01 where exists (select 1 from A01_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0000=a01.a0000) ").executeUpdate();
//				String str =null;
//				Long.parseLong(str);
				//insert
				sess.createSQLQuery("insert into a01 select t.a0000,t.a0101,t.a0104,t.a0104a,t.a0107,t.a0111,t.a0111a,t.a0114,t.a0114a,t.a0117,"+
						"t.a0117a,t.a0134,t.a0144,t.a0144b,t.a0144c,t.a0148,t.a0149,t.a0151,t.a0153,t.a0155,t.a0157,"+
						"t.a0158,t.a0159,t.a015a,t.a0160,t.a0161,t.a0162,t.a0163,t.a0165,t.a0184,t.a0191,"+
						"t.a0192,t.a0192a,t.a0192b,t.a0193,t.a0195,t.a0196,t.a0198,t.a0199,t.a01k01,t.a01k02,"+
						"t.age,t.cbdw,t.isvalid,t.jsnlsj,t.nl,t.nmzw,t.nrzw,t.qrzxl,t.qrzxlxx,t.qrzxw,"+
						"t.qrzxwxx,t.resultsortid,t.rmly,t.tbr,t.tbsj,t.userlog,t.xgr,t.xgsj,t.zzxl,t.zzxlxx,"+
						"t.zzxw,t.zzxwxx,t.a3927,t.a0102,t.a0128b,t.a0128,t.a0140,t.a0187a,t.a0148c,t.a1701,"+
						"t.a14z101,t.a15z101,t.a0141d,t.a0141,t.a3921,t.sortid,t.a0180,t.a0194,t.a0192d,"+
						"t.STATUS,t.tbrjg,t.a0120,t.a0121,t.a0122,t.a0194u"+
						" from a01_temp t where  imprecordid='"+ imprecordid + "'").executeUpdate();
				
				//a02 先删除a02数据 ----- ‘现有数据’与‘来源数据’ 中已存在的同样人员（身份证相同）数据  ------ 之后插入临时表数据
				//update 更新temp任职机构id
//				sess.createSQLQuery("update A02_temp t set t.a0201b = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.a0201b  and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "'  and exists (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.a0201b and d.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("update A02_temp t,B01TEMP_B01 d set t.a0201b = d.new_b0111  where t.imprecordid='"+ imprecordid + "' and  d.temp_b0111=t.a0201b ").executeUpdate();
				//delete 
//				sess.createSQLQuery(" delete from a02 where exists (select t.a0200 from(select k.a0200 from A02_temp k where k.imprecordid='"+ imprecordid + 
//						"') t where t.a0200=a02.a0200) or exists ("+orgs+"a02.a0000)").executeUpdate();
				sess.createSQLQuery(" delete from a02 where a0201b like '"+impdeptid+"%'").executeUpdate();
				sess.createSQLQuery(" delete from a02 where exists (select 1 from A02_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0200=a02.a0200) ").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a02 select t.a0000,t.a0200,t.a0201,t.a0201a,t.a0201b,t.a0201c, t.a0201d,t.a0201e,t.a0204,t.a0207,"+
						"A0209,A0215A,A0215B,A0216A,A0219,A0219W,A0221,A0221W,A0222,A0223,"+
						" A0225,A0229,A0243,A0245,A0247,A0251,A0251B,A0255,A0256,A0256A,"+
						"A0256B,A0256C,A0259,A0265,A0267,A0271,A0277,A0281,A0284,A0288,"+
	       				"A0289,A0295,A0299,A4901,A4904,A4907,t.updated,t.wage_used,t.a0221a,t.b0238,t.b0239"+
	       				" from a02_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//b01
				//update 更新temp任职机构id 上级id 本级id
				
//				sess.createSQLQuery("update B01_temp t set t.b0121 = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0121 and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "' and t.b0111<>'"+imp.getEmpdeptid()+"' and exists (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0121 and d.imprecordid='"+ imprecordid + "')").executeUpdate();
//				sess.createSQLQuery("update B01_temp t set t.b0111 = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0111 and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "'  and exists (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0111 and d.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("update B01_temp t,B01TEMP_B01 d set t.b0121 = d.new_b0111 where t.imprecordid='"+ imprecordid + "' and t.b0111<>'"+imp.getEmpdeptid()+"' and d.temp_b0111=t.b0121").executeUpdate();
				sess.createSQLQuery("update B01_temp t,B01TEMP_B01 d set t.b0111 = d.new_b0111 where t.imprecordid='"+ imprecordid + "' and d.temp_b0111=t.b0111").executeUpdate();
				//delete 
//				sess.createSQLQuery(" delete from b01 where b0111 in (select a.b0111 from b01 a,b01_temp b where (a.b0111=b.b0114 and a.b0101=b.b0101) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from b01 where b0111 in ("+borgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into b01 select b0101,b0104,b0107,b0111,b0114,b0117,b0121,b0124,b0127,b0131,"+
						"b0140,b0141,b0142,b0143,b0150,b0180,b0183,b0185,b0188,b0189,"+
						"b0190,b0191,b0191a,b0192,b0193,b0194,b01trans,b01ip,b0227,b0232,"+
						"b0233,sortid,used,t.updated,create_user,create_date,update_user,update_date,t.status,b0238,b0239,b0234"+
	       				" from b01_temp t where t.imprecordid='"+ imprecordid + "'").executeUpdate();
				//导入机构授权
				sess.createSQLQuery(" delete from COMPETENCE_USERDEPT where b0111 like '"+impdeptid+"%' and userid='"+user.getId()+"'").executeUpdate();
				Connection conn = sess.connection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select b0111 from b01 where b0111 like '"+impdeptid+"%'");
				PreparedStatement pstmt1 = conn.prepareStatement("insert into COMPETENCE_USERDEPT values(?,?,?,?)");
				int i = 0;
				if (rs != null){
					while (rs.next()) {
						pstmt1.setString(1, UUID.randomUUID().toString().replace("-", ""));
						pstmt1.setString(2, user.getId());
						pstmt1.setString(3, rs.getString(1));
						pstmt1.setString(4, "0");
						pstmt1.addBatch();
						i++;
						if(i%10000 == 0){
							pstmt1.executeBatch();
							pstmt1.clearBatch();
						}
					}
					pstmt1.executeBatch();
					pstmt1.clearBatch();
				}
				rs.close();
				pstmt1.close();
				stmt.close();
				conn.close();
			} else {
				String orgs = "select 1 from a02 a where a.a0000=a0000 and a.a0201b like '"+impdeptid+"%'";
				String borgs = "select d.b0111 from b01 d where d.b0111 like '"+impdeptid+"%' ";
				//a06 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from a06 where exists (select 1 from a02 a where a.a0000=a06.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a06 where exists (select 1 from A06_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0600=a06.a0600)").executeUpdate();
				//insert
//				sess.createSQLQuery("insert into a06 select A0600,A0000,A0601,A0602, A0604, A0607, A0611, A0614, t.SORTID, t.UPDATED"+
//	       				" from a06_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery("insert into a06 select t.A0600,t.A0000,t.A0601,t.A0602, t.A0604, t.A0607, t.A0611, t.A0614, t.SORTID, t.UPDATED"+
	       				" from a06_temp t,a01_temp a where t.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//a08 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from a08 where exists (select 1 from a02 a where a.a0000=a08.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a08 where exists (select 1 from A08_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0800=a08.a0800)").executeUpdate();
				//insert
//				sess.createSQLQuery("insert into a08 select  A0000,A0800,A0801A,A0801B,A0804,A0807,A0811,A0814,A0824,A0827,"+
//						" A0831,A0832,A0834,A0835,A0837,A0838,A0839,A0898,A0899,A0901A,"+
//						" A0901B,A0904,SORTID,t.updated,t.wage_used"+
//	       				" from a08_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery("insert into a08 select  t.A0000,A0800,A0801A,A0801B,A0804,A0807,A0811,A0814,A0824,A0827,"+
						" A0831,A0832,A0834,A0835,A0837,A0838,A0839,A0898,A0899,A0901A,"+
						" A0901B,A0904,t.SORTID,t.updated,t.wage_used"+
	       				" from a08_temp t,a01_temp a where t.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//a11 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from a11 where exists (select 1 from a02 a where a.a0000=a11.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a11 where exists (select 1 from A11_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a1100=a11.a1100)").executeUpdate();
				//insert
//				sess.createSQLQuery("insert into a11 select  A0000,A1100,A1101,A1104,A1107,A1107A,a1107b ,a1111 ,a1114 ,a1121a ,"+
//						"a1127 ,a1131 ,a1134 ,a1151 ,t.updated,t.A1108,t.A1107C"+
//	       				" from a11_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery("insert into a11 select  t.A0000,A1100,A1101,A1104,A1107,A1107A,a1107b ,a1111 ,a1114 ,a1121a ,"+
						"a1127 ,a1131 ,a1134 ,a1151 ,t.updated,t.A1108,t.A1107C"+
	       				" from a11_temp t,a01_temp a where t.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//a14 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from a14 where exists (select 1 from a02 a where a.a0000=a14.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a14 where exists (select 1 from A14_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a1400=a14.a1400)").executeUpdate();
				//insert
//				sess.createSQLQuery("insert into a14 select a0000, a1400, a1404a, a1404b, a1407 ,a1411a ,a1414 ,a1415, a1424, a1428,"+ 
//						"t.sortid ,t.updated "+
//	       				" from a14_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery("insert into a14 select t.a0000, a1400, a1404a, a1404b, a1407 ,a1411a ,a1414 ,a1415, a1424, a1428,"+ 
						"t.sortid ,t.updated "+
	       				" from a14_temp t,a01_temp a where t.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//a15 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from a15 where exists (select 1 from a02 a where a.a0000=a15.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a15 where exists (select 1 from A15_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a1500=a15.a1500)").executeUpdate();
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
//				sess.createSQLQuery("insert into a15 select a0000, a1500, a1517, a1521, t.updated, a1527  "+
//	       				" from a15_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("insert into a15 select t.a0000, a1500, a1517, a1521, t.updated, a1527  "+
	       				" from a15_temp t,a01_temp a where t.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//a29 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from a29 where exists (select 1 from a02 a where a.a0000=a29.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a29 where exists (select 1 from A29_temp k where k.imprecordid ='"+ imprecordid + 
						"' and k.a0000=a29.a0000)").executeUpdate();
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
//				sess.createSQLQuery("insert into a29 select  a0000, a2907 ,a2911, a2921a ,a2941, a2944, a2947 ,a2949, t.updated," +
//						"t.a2970,t.a2970a,t.a2970b,t.a2970c,t.a2947a,t.a2950,t.a2951,A2921B,A2947B "+
//	       				" from a29_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("insert into a29 select  t.a0000, a2907 ,a2911, a2921a ,a2941, a2944, a2947 ,a2949, t.updated," +
						"t.a2970,t.a2970a,t.a2970b,t.a2970c,t.a2947a,t.a2950,t.a2951,A2921B,A2947B,t.A2921C,t.A2921d "+
	       				" from a29_temp t,a01_temp a where t.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//a30 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from a30 where exists (select 1 from a02 a where a.a0000=a30.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a30 where exists (select 1 from A30_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0000=a30.a0000)").executeUpdate();
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
//				sess.createSQLQuery("insert into a30 select a0000,a3001, a3004, a3007a ,a3034 ,t.updated  "+
//	       				" from a30_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("insert into a30 select t.a0000,a3001, a3004, a3007a ,a3034 ,t.updated  "+
	       				" from a30_temp t,a01_temp a where t.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//a31 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from a31 where exists (select 1 from a02 a where a.a0000=a31.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a31 where exists (select 1 from A31_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0000=a31.a0000)").executeUpdate();
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
//				sess.createSQLQuery("insert into a31 select  a0000,a3101, a3104, a3107, a3117a,a3118, a3137, a3138 ,t.updated,t.a3140,t.a3141,t.a3142  "+
//	       				" from a31_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("insert into a31 select  t.a0000,a3101, a3104, a3107, a3117a,a3118, a3137, a3138 ,t.updated,t.a3140,t.a3141,t.a3142  "+
	       				" from a31_temp t,a01_temp a where t.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//a36 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from a36 where exists (select 1 from a02 a where a.a0000=a36.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a36 where exists (select 1 from A36_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a3600=a36.a3600)").executeUpdate();
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
//				sess.createSQLQuery("insert into a36 select  a0000,a3600, a3601, a3604a, a3607, a3611, a3627 ,sortid ,t.updated "+
//	       				" from a36_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("insert into a36 select  t.a0000,a3600, a3601, a3604a, a3607, a3611, a3627 ,t.sortid ,t.updated "+
	       				" from a36_temp t,a01_temp a where t.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//a37 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from a37 where exists (select 1 from a02 a where a.a0000=a37.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a37 where exists (select 1 from A37_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0000=a37.a0000) ").executeUpdate();
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
//				sess.createSQLQuery("insert into a37 select a0000,a3701,a3707a,a3707c,a3707e,a3707b,a3708,a3711,a3714,t.updated "+
//	       				" from a37_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("insert into a37 select t.a0000,a3701,a3707a,a3707c,a3707e,a3707b,a3708,a3711,a3714,t.updated "+
	       				" from a37_temp t,a01_temp a where t.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//a41 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from a41 where exists (select 1 from a02 a where a.a0000=a41.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a41 where exists (select 1 from A41_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a4100=a41.a4100)").executeUpdate();
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
//				sess.createSQLQuery("insert into a41 select  a4100, a0000,a1100 ,a4101, a4102, a4103 ,a4104, a4105 ,a4199"+
//	       				" from a41_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("insert into a41 select  a4100, t.a0000,a1100 ,a4101, a4102, a4103 ,a4104, a4105 ,a4199"+
	       				" from a41_temp t,a01_temp a where t.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//a53 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from a53 where exists (select 1 from a02 a where a.a0000=a53.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a53 where exists (select 1 from A53_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a5300=a53.a5300)").executeUpdate();
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
//				sess.createSQLQuery("insert into a53 select a0000,a5300,a5304,a5315,a5317,a5319,a5321,a5323,a5327,a5399,t.updated"+
//	       				" from a53_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("insert into a53 select t.a0000,a5300,a5304,a5315,a5317,a5319,a5321,a5323,a5327,a5399,t.updated"+
	       				" from a53_temp t,a01_temp a where t.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//a57 //delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from a57 where exists (select 1 from a02 a where a.a0000=a57.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a57 where exists (select 1 from A57_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0000=a57.a0000)").executeUpdate();
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
//				sess.createSQLQuery("insert into a57 select a0000,a5714 ,t.updated,PHOTODATA,PHOTONAME,PHOTSTYPE "+
//	       				" from a57_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("insert into a57 select t.a0000,a5714 ,t.updated,PHOTODATA,PHOTONAME,PHOTSTYPE,PHOTOPATH "+
	       				" from a57_temp t,a01_temp a where t.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//a01
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				//update 统计关系所在单位
				sess.createSQLQuery("update A01_temp t set t.a0195 = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.a0195 and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "' ").executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery("update A01_temp t set t.a0195 ='-1' where imprecordid='"+ imprecordid + "' and t.a0195='XXX'").executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				//delete 
				sess.createSQLQuery(" delete from a01 where exists (select 1 from a02 a where a.a0000=a01.a0000 and a.a0201b like '"+impdeptid+"%')").executeUpdate();
				sess.createSQLQuery(" delete from a01 where exists (select 1 from A01_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0000=a01.a0000) ").executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				//insert
				sess.createSQLQuery("insert into a01 select t.a0000,t.a0101,t.a0104,t.a0104a,t.a0107,t.a0111,t.a0111a,t.a0114,t.a0114a,t.a0117,"+
						"t.a0117a,t.a0134,t.a0144,t.a0144b,t.a0144c,t.a0148,t.a0149,t.a0151,t.a0153,t.a0155,t.a0157,"+
						"t.a0158,t.a0159,t.a015a,t.a0160,t.a0161,t.a0162,t.a0163,t.a0165,t.a0184,t.a0191,"+
						"t.a0192,t.a0192a,t.a0192b,t.a0193,t.a0195,t.a0196,t.a0198,t.a0199,t.a01k01,t.a01k02,"+
						"t.age,t.cbdw,t.isvalid,t.jsnlsj,t.nl,t.nmzw,t.nrzw,t.qrzxl,t.qrzxlxx,t.qrzxw,"+
						"t.qrzxwxx,t.resultsortid,t.rmly,t.tbr,t.tbsj,t.userlog,t.xgr,t.xgsj,t.zzxl,t.zzxlxx,"+
						"t.zzxw,t.zzxwxx,t.a3927,t.a0102,t.a0128b,t.a0128,t.a0140,t.a0187a,t.a0148c,t.a1701,"+
						"t.a14z101,t.a15z101,t.a0141d,t.a0141,t.a3921,t.sortid,t.a0180,t.a0194,t.a0192d,"+
						"t.STATUS,t.tbrjg,t.a0120,t.a0121,t.a0122,t.a0194u"+
						" from a01_temp t where imprecordid='"+ imprecordid + "'").executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				//a02 先删除a02数据 ----- ‘现有数据’与‘来源数据’ 中已存在的同样人员（身份证相同）数据  ------ 之后插入临时表数据
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				//update 更新temp任职机构id
				sess.createSQLQuery("update A02_temp t set t.a0201b = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.a0201b  and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "' ").executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				//delete 
				sess.createSQLQuery(" delete from a02 where a0201b like '"+impdeptid+"%'").executeUpdate();
				sess.createSQLQuery(" delete from a02 where exists (select 1 from A02_temp k where k.imprecordid='"+ imprecordid + 
						"' and k.a0200=a02.a0200) ").executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				//insert
				sess.createSQLQuery("insert into a02 select t.a0000,t.a0200,t.a0201,t.a0201a,t.a0201b,t.a0201c, t.a0201d,t.a0201e,t.a0204,t.a0207,"+
						"t.A0209,t.A0215A,t.A0215B,t.A0216A,t.A0219,t.A0219W,t.A0221,t.A0221W,t.A0222,t.A0223,"+
						" t.A0225,t.A0229,t.A0243,t.A0245,t.A0247,t.A0251,t.A0251B,t.A0255,t.A0256,t.A0256A,"+
						"t.A0256B,t.A0256C,t.A0259,t.A0265,t.A0267,t.A0271,t.A0277,t.A0281,t.A0284,t.A0288,"+
	       				"t.A0289,t.A0295,t.A0299,t.A4901,t.A4904,t.A4907,t.updated,t.wage_used,t.a0221a,t.b0238,t.b0239"+
	       				" from a02_temp t,a01_temp a where a.imprecordid='"+ imprecordid + "' and t.imprecordid=a.imprecordid and t.a0000=a.a0000").executeUpdate();
				//b01    //update 更新temp任职机构id 上级id 本级id
				sess.createSQLQuery("update B01_temp t set t.b0121 = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0121 and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "' and t.b0111<>'"+imp.getEmpdeptid()+"' ").executeUpdate();
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery("update B01_temp t set t.b0111 = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0111 and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "' ").executeUpdate();
				//delete 
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery(" delete from b01 where b0111 in ("+borgs+")").executeUpdate();
				//insert
				CommonQueryBS.systemOut("time---------" + DateUtil.getTime());
				sess.createSQLQuery("insert into b01 select b0101,b0104,b0107,b0111,b0114,b0117,b0121,b0124,b0127,b0131,"+
						"b0140,b0141,b0142,b0143,b0150,b0180,b0183,b0185,b0188,b0189,"+
						"b0190,b0191,b0191a,b0192,b0193,b0194,b01trans,b01ip,b0227,b0232,"+
						"b0233,sortid,used,t.updated,create_user,create_date,update_user,update_date,t.status,b0238,b0239,b0234"+
	       				" from b01_temp t where t.imprecordid='"+ imprecordid + "'").executeUpdate();
			
				//导入机构授权
				sess.createSQLQuery(" delete from COMPETENCE_USERDEPT where b0111 like '"+impdeptid+"%' and userid='"+user.getId()+"'").executeUpdate();
				Connection conn = sess.connection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select b0111 from b01 where b0111 like '"+impdeptid+"%'");
				PreparedStatement pstmt1 = conn.prepareStatement("insert into COMPETENCE_USERDEPT values(?,?,?,?)");
				int i = 0;
				if (rs != null){
					while (rs.next()) {
						pstmt1.setString(1, UUID.randomUUID().toString().replace("-", ""));
						pstmt1.setString(2, user.getId());
						pstmt1.setString(3, rs.getString(1));
						pstmt1.setString(4, "0");
						pstmt1.addBatch();
						i++;
						if(i%10000 == 0){
							pstmt1.executeBatch();
							pstmt1.clearBatch();
						}
					}
					pstmt1.executeBatch();
					pstmt1.clearBatch();
				}
				rs.close();
				pstmt1.close();
				stmt.close();
				conn.close();
			}
			String tables1[] = {"A01", "A02","A06","A08", "A11", "A14", "A15", "A29","A30", 
					"A31","A36","A37","A41", "A53","A57", "B01"};
			for (int i = 0; i < tables1.length; i++) {
				sess.createSQLQuery(" delete from " + tables1[i] + "_temp where imprecordid='"
						+ imprecordid + "'").executeUpdate();
			}
			sess.createSQLQuery(" delete from B01TEMP_B01 where imprecordid='"
					+ imprecordid + "'").executeUpdate();
			imp.setImpstutas("2");
			sess.update(imp);
			sess.flush();
		//	PhotosUtil.moveIMPCmd(imprecordid);
	    //  PhotosUtil.moveIMPOtherCmd(imprecordid);
		//	PhotosUtil.removDirImpCmd(imprecordid);
			if (ftype.equalsIgnoreCase("hzb") || ftype.equalsIgnoreCase("zzb")) {
				new LogUtil().createLog("463", "IMP_RECORD", "", "", "导入应用库", new ArrayList());
			} else if (ftype.equalsIgnoreCase("zzb3")){
				new LogUtil().createLog("473", "IMP_RECORD", "", "", "导入应用库", new ArrayList());
			}
			sess.getTransaction().commit();
			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('MGrid').store.reload();");
		} catch (AppException e) {
			if(sess!=null)
				sess.getTransaction().rollback();
			e.printStackTrace();
		} catch (SQLException e) {
			if(sess!=null)
				sess.getTransaction().rollback();
			e.printStackTrace();
		}
		this.setMainMessage("接收成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("impBtn1.onclick")
	@NoRequiredValidate
	public int impmodelOnclick1()throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.beginTransaction();
			String imprecordid = this.getPageElement("b0111OrimpID").getValue();
			/**
			 * 历史库代码暂无
			 */
			Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
			String impdeptid = imp.getImpdeptid();
			String ftype = imp.getFiletype();
			String empdeptid = imp.getEmpdeptid();
			if(imp.getIsvirety() == null || imp.getIsvirety().equals("") ||imp.getIsvirety().equals("0")){
				this.setMainMessage("请先进行校验，再导入数据");
				return EventRtnType.NORMAL_SUCCESS;
			}
//			if(imp.getWrongnumber() != null && !imp.getWrongnumber().equals("") 
//					&& Long.parseLong(imp.getWrongnumber())>0){
//				this.setMainMessage("存在错误数据，请打回。");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			if(imp.getImpstutas() != null && !imp.getImpstutas().equals("1")){
				if(imp.getImpstutas().equals("2")){
					this.setMainMessage("数据已导入，不能重复导入。");
				} else {
					this.setMainMessage("数据已打回，不能导入。");
				}
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				String orgs = "select distinct a.a0000 from a02 a where a.a0000=a0000 and a.a0201b in (select b0111 from b01 start with b0111='"+ imp.getImpdeptid() +"' connect by prior b0111= b0121)";
				if(DBUtil.getDBType().equals(DBType.MYSQL)){
					orgs = "select mt.a0000 from (select distinct a.a0000 a0000 from a02 a where a.a0201b in (select b0111 from b01 where b0111 like '"+ imp.getImpdeptid() +"%')) mt where mt.a0000=a0000";
				}
				String borgs = "select b0111 from b01 start with b0111='"+ imp.getImpdeptid() +"' connect by prior b0111= b0121";
				if(DBUtil.getDBType().equals(DBType.MYSQL)){
					borgs = "select mt.b0111 from (select b0111 from b01 where b0111 like '"+ imp.getImpdeptid() +"%') mt ";
				}
				String houzhui = imp.getFilename().substring(imp.getFilename().lastIndexOf(".") + 1);

				//a02 先删除a02数据 ----- ‘现有数据’与‘来源数据’ 中已存在的同样人员（身份证相同）数据  ------ 之后插入临时表数据
				//update 更新temp任职机构id
//				sess.createSQLQuery("update A02_temp t set t.a0201b = (select a1 from (select a.b0111 a1,a.b0121 a2,b.b0111 b1 from b01 a,b01_temp b where a.b0101=b.b0101 and a.b0111=b.b0114) d where d.b1=t.a0201b ) where imprecordid='"+ imprecordid + "'  and exists (select a1 from (select a.b0111 a1, a.b0121 a2, b.b0111 b1 from b01 a, b01_temp b where a.b0101 = b.b0101 and a.b0111=b.b0114) d where d.b1 = t.a0201b)").executeUpdate();
				sess.createSQLQuery("update A02_temp t set t.a0201b = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.a0201b  and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "'  and exists (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.a0201b and d.imprecordid='"+ imprecordid + "')").executeUpdate();
				//delete 
				//sess.createSQLQuery(" delete from a02 where a0000 in (select a.a0000 from a01 a,a01_temp b where ( a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a02 where exists (select t.a0200 from(select k.a0200 from A02_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0200=a02.a0200) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a02 select t.a0000,t.a0200,t.a0201,t.a0201a,t.a0201b,t.a0201c, t.a0201d,t.a0201e,t.a0204,t.a0207,"+
						"A0209,A0215A,A0215B,A0216A,A0219,A0219W,A0221,A0221W,A0222,A0223,"+
						" A0225,A0229,A0243,A0245,A0247,A0251,A0251B,A0255,A0256,A0256A,"+
						"A0256B,A0256C,A0259,A0265,A0267,A0271,A0277,A0281,A0284,A0288,"+
	       				"A0289,A0295,A0299,A4901,A4904,A4907,t.updated,t.wage_used,t.a0221a,t.b0238,t.b0239"+
	       				" from a02_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where  a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a06
				//delete 
				//sess.createSQLQuery(" delete from a06 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a06 where exists (select t.a0600 from(select k.a0600 from A06_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0600=a06.a0600) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a06 select A0600,A0000,A0601,A0602, A0604, A0607, A0611, A0614, t.SORTID, t.UPDATED"+
	       				" from a06_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a08
				//delete 
				//sess.createSQLQuery(" delete from a08 where a0000 in (select a.a0000 from a01 a,a01_temp b where ( a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a08 where exists (select t.a0800 from(select k.a0800 from A08_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0800=a08.a0800) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a08 select  A0000,A0800,A0801A,A0801B,A0804,A0807,A0811,A0814,A0824,A0827,"+
						" A0831,A0832,A0834,A0835,A0837,A0838,A0839,A0898,A0899,A0901A,"+
						" A0901B,A0904,SORTID,t.updated,t.wage_used"+
	       				" from a08_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a11
				//delete 
				//sess.createSQLQuery(" delete from a11 where a0000 in (select a.a0000 from a01 a,a01_temp b where ( a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a11 where exists (select t.a1100 from(select k.a1100 from A11_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a1100=a11.a1100) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a11 select  A0000,A1100,A1101,A1104,A1107,A1107A,a1107b ,a1111 ,a1114 ,a1121a ,"+
						"a1127 ,a1131 ,a1134 ,a1151 ,t.updated,t.A1108,t.A1107C"+
	       				" from a11_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a14
				//delete 
				//sess.createSQLQuery(" delete from a14 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a14 where exists (select t.a1400 from(select k.a1400 from A14_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a1400=a14.a1400) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a14 select a0000, a1400, a1404a, a1404b, a1407 ,a1411a ,a1414 ,a1415, a1424, a1428,"+ 
						"t.sortid ,t.updated "+
	       				" from a14_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a15
				//delete 
//				sess.createSQLQuery(" delete from a15 where a0000 in (select a.a0000 from a01 a,a01_temp b where ( a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a15 where exists (select t.a1500 from(select k.a1500 from A15_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a1500=a15.a1500) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a15 select a0000, a1500, a1517, a1521, t.updated, a1527  "+
	       				" from a15_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a29
				//delete 
//				sess.createSQLQuery(" delete from a29 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a29 where exists (select t.a0000 from(select k.a0000 from A29_temp k where k.imprecordid ='"+ imprecordid + 
						"') t where t.a0000=a29.a0000) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a29 select  a0000, a2907 ,a2911, a2921a ,a2941, a2944, a2947 ,a2949, t.updated," +
						"t.a2970,t.a2970a,t.a2970b,t.a2970c,t.a2947a,t.a2950,t.a2951,A2921B,A2947B,t.A2921C,t.A2921d "+
	       				" from a29_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a30
				//delete 
//				sess.createSQLQuery(" delete from a30 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a30 where exists (select t.a0000 from(select k.a0000 from A30_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0000=a30.a0000) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a30 select a0000,a3001, a3004, a3007a ,a3034 ,t.updated  "+
	       				" from a30_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a31
				//delete 
//				sess.createSQLQuery(" delete from a31 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a31 where exists (select t.a0000 from(select k.a0000 from A31_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0000=a31.a0000) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a31 select  a0000,a3101, a3104, a3107, a3117a,a3118, a3137, a3138 ,t.updated,t.a3140,t.a3141,t.a3142  "+
	       				" from a31_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a36
				//delete 
//				sess.createSQLQuery(" delete from a36 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a36 where exists (select t.a3600 from(select k.a3600 from A36_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a3600=a36.a3600) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a36 select  a0000,a3600, a3601, a3604a, a3607, a3611, a3627 ,sortid ,t.updated "+
	       				" from a36_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a37
				//delete 
//				sess.createSQLQuery(" delete from a37 where a0000 in (select a.a0000 from a01 a,a01_temp b where ( a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a37 where exists (select t.a0000 from(select k.a0000 from A37_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0000=a37.a0000) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a37 select a0000,a3701,a3707a,a3707c,a3707e,a3707b,a3708,a3711,a3714,t.updated "+
	       				" from a37_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a41
				//delete 
//				sess.createSQLQuery(" delete from a41 where a0000 in (select a.a0000 from a01 a,a01_temp b where ( a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a41 where exists (select t.a4100 from(select k.a4100 from A41_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a4100=a41.a4100) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a41 select  a4100, a0000,a1100 ,a4101, a4102, a4103 ,a4104, a4105 ,a4199"+
	       				" from a41_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a53
				//delete 
//				sess.createSQLQuery(" delete from a53 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a53 where exists (select t.a5300 from(select k.a5300 from A53_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a5300=a53.a5300) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a53 select a0000,a5300,a5304,a5315,a5317,a5319,a5321,a5323,a5327,a5399,t.updated"+
	       				" from a53_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a57
				//delete 
//				sess.createSQLQuery(" delete from a57 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a57 where exists (select t.a0000 from(select k.a0000 from A57_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0000=a57.a0000) or exists ("+orgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into a57 select a0000,a5714 ,t.updated,PHOTODATA,PHOTONAME,PHOTSTYPE "+
	       				" from a57_temp t where t.imprecordid='"+ imprecordid + "' and t.a0000 in(select a0000 from a01_temp a where a.is_qualified='0' and a.imprecordid='"+ imprecordid + "')").executeUpdate();
				//a01
				//update 统计关系所在单位
				sess.createSQLQuery("update A01_temp t set t.a0195 = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.a0195 and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "' and exists (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.A0195 and d.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("update A01_temp t set t.a0195 ='-1' where imprecordid='"+ imprecordid + "' and t.a0195='XXX'").executeUpdate();
				//delete 
//				sess.createSQLQuery(" delete from a01 where a0000 in (select a.a0000 from a01 a,a01_temp b where (a.a0000=b.a0000) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from a01 where exists (select t.a0000 from(select k.a0000 from A01_temp k where k.imprecordid='"+ imprecordid + 
						"') t where t.a0000=a01.a0000) or exists ("+orgs+")").executeUpdate();
//				String str =null;
//				Long.parseLong(str);
				//insert
				sess.createSQLQuery("insert into a01 select t.a0000,t.a0101,t.a0104,t.a0104a,t.a0107,t.a0111,t.a0111a,t.a0114,t.a0114a,t.a0117,"+
						"t.a0117a,t.a0134,t.a0144,t.a0144b,t.a0144c,t.a0148,t.a0149,t.a0151,t.a0153,t.a0155,t.a0157,"+
						"t.a0158,t.a0159,t.a015a,t.a0160,t.a0161,t.a0162,t.a0163,t.a0165,t.a0184,t.a0191,"+
						"t.a0192,t.a0192a,t.a0192b,t.a0193,t.a0195,t.a0196,t.a0198,t.a0199,t.a01k01,t.a01k02,"+
						"t.age,t.cbdw,t.isvalid,t.jsnlsj,t.nl,t.nmzw,t.nrzw,t.qrzxl,t.qrzxlxx,t.qrzxw,"+
						"t.qrzxwxx,t.resultsortid,t.rmly,t.tbr,t.tbsj,t.userlog,t.xgr,t.xgsj,t.zzxl,t.zzxlxx,"+
						"t.zzxw,t.zzxwxx,t.a3927,t.a0102,t.a0128b,t.a0128,t.a0140,t.a0187a,t.a0148c,t.a1701,"+
						"t.a14z101,t.a15z101,t.a0141d,t.a0141,t.a3921,t.sortid,t.a0180,t.a0194,t.a0192d,"+
						"t.STATUS,t.tbrjg,t.a0120,t.a0121,t.a0122"+
						" from a01_temp t where t.is_qualified='0' and imprecordid='"+ imprecordid + "'").executeUpdate();
				
				//b01
				//update 更新temp任职机构id 上级id 本级id
				
				sess.createSQLQuery("update B01_temp t set t.b0121 = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0121 and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "' and t.b0111<>'"+imp.getEmpdeptid()+"' and exists (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0121 and d.imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery("update B01_temp t set t.b0111 = (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0111 and d.imprecordid='"+ imprecordid + "') where imprecordid='"+ imprecordid + "'  and exists (select new_b0111 from B01TEMP_B01 d where d.temp_b0111=t.b0111 and d.imprecordid='"+ imprecordid + "')").executeUpdate();
				//delete 
//				sess.createSQLQuery(" delete from b01 where b0111 in (select a.b0111 from b01 a,b01_temp b where (a.b0111=b.b0114 and a.b0101=b.b0101) and imprecordid='"+ imprecordid + "')").executeUpdate();
				sess.createSQLQuery(" delete from b01 where b0111 in ("+borgs+")").executeUpdate();
				//insert
				sess.createSQLQuery("insert into b01 select b0101,b0104,b0107,b0111,b0114,b0117,b0121,b0124,b0127,b0131,"+
						"b0140,b0141,b0142,b0143,b0150,b0180,b0183,b0185,b0188,b0189,"+
						"b0190,b0191,b0191a,b0192,b0193,b0194,b01trans,b01ip,b0227,b0232,"+
						"b0233,sortid,used,t.updated,create_user,create_date,update_user,update_date,t.status,b0238,b0239,b0234"+
	       				" from b01_temp t where t.imprecordid='"+ imprecordid + "' and is_qualified='0' and imprecordid='"+ imprecordid + "'").executeUpdate();
				
			} else {
				Connection conn=sess.connection();
				CallableStatement cstmt=conn.prepareCall("{call Imp_From_Temp(?,?,?,?)}");
				cstmt.setString(1,imprecordid);
				cstmt.setString(2,impdeptid);
				cstmt.setString(3,empdeptid);
				cstmt.registerOutParameter(4,Types.INTEGER );
				cstmt.execute();
				int imp_sta = cstmt.getInt(4);
				if (imp_sta!=1) {
					if(cstmt!=null) cstmt.close();	
					throw new RadowException("数据处理异常!");
				}
				if(cstmt!=null) cstmt.close();	
			}
			String tables1[] = {"A01", "A02","A06","A08", "A11", "A14", "A15", "A29","A30", 
					"A31","A36","A37","A41", "A53","A57", "B01"};
			for (int i = 0; i < tables1.length; i++) {
				sess.createSQLQuery(" delete from " + tables1[i] + "_temp where imprecordid='"
						+ imprecordid + "'").executeUpdate();
			}
			sess.createSQLQuery(" delete from B01TEMP_B01 where imprecordid='"
					+ imprecordid + "'").executeUpdate();
			imp.setImpstutas("2");
			sess.update(imp);
			sess.flush();
			if (ftype.equalsIgnoreCase("hzb") || ftype.equalsIgnoreCase("zzb")) {
				new LogUtil().createLog("463", "IMP_RECORD", "", "", "导入应用库", new ArrayList());
			} else if (ftype.equalsIgnoreCase("zzb3")){
				new LogUtil().createLog("473", "IMP_RECORD", "", "", "导入应用库", new ArrayList());
			}
			sess.getTransaction().commit();
			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('MGrid').store.reload();");
			this.setMainMessage("接收成功！");
		} catch (AppException e) {
			if(sess!=null)
				sess.getTransaction().rollback();
			e.printStackTrace();
			this.setMainMessage("接收失败！");
		} catch (SQLException e) {
			if(sess!=null)
				sess.getTransaction().rollback();
			e.printStackTrace();
			this.setMainMessage("接收失败！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 导入反馈包
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("empBtn.onclick")
	@NoRequiredValidate
	public int expmodelOnclick()throws RadowException{
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 全部退回
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("rejectBtn.onclick")
	@NoRequiredValidate
	public int rejectBtnOnclick()throws RadowException{
		
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.beginTransaction();
			String imprecordid = this.getPageElement("b0111OrimpID").getValue();
			Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
			String ftype = imp.getFiletype();
//			if(imp.getImpstutas().equals("2")){
//				this.setMainMessage("数据已导入，不能打回。");
//				return EventRtnType.NORMAL_SUCCESS;
//			} else if(imp.getImpstutas().equals("3")){
//				this.setMainMessage("数据已打回，不能重复打回。");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			String str = this.FkImpData(imprecordid, "");
			String tables1[] = {"A01", "A02","A06","A08", "A11", "A14", "A15", "A29","A30", 
					"A31","A36","A37","A41", "A53","A57", "B01"};
			for (int i = 0; i < tables1.length; i++) {
				sess.createSQLQuery(" delete from " + tables1[i] + "_temp where imprecordid='"
						+ imprecordid + "'").executeUpdate();
			}
			sess.createSQLQuery(" delete from B01TEMP_B01 where imprecordid='"
					+ imprecordid + "'").executeUpdate();
			imp.setImpstutas("3");
			sess.update(imp);
			sess.flush();
			sess.getTransaction().commit();
			if(str != null && !str.equals(""))
				this.getExecuteSG().addExecuteCode("var w=window.open('ProblemDownServlet?method=downFile&prid="+ URLEncoder.encode(URLEncoder.encode(str,"UTF-8"),"UTF-8")+"');  setTimeout(cc,600); function cc(){w.close();}");
			if (ftype.equalsIgnoreCase("hzb") || ftype.equalsIgnoreCase("zzb")) {
				new LogUtil().createLog("462", "IMP_RECORD", "", "", "打回清除", new ArrayList());
			} else if (ftype.equalsIgnoreCase("zzb3")){
				new LogUtil().createLog("472", "IMP_RECORD", "", "", "打回清除", new ArrayList());
			}
			this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('MGrid').store.reload();");
			this.setMainMessage("已打回！");
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
	private String FkImpData(String imprecordid, String string) {
		HBSession sess = HBUtil.getHBSession();
		ZwhzPackDefine zpdefine = new ZwhzPackDefine();
		List<SFileDefine> sfile = new ArrayList<SFileDefine>();
		String craeatePath = AppConfig.LOCAL_FILE_BASEURL + "/"; //生成文件路径
		try {
			List plist=sess.createSQLQuery("select distinct a.a0101,a.a0184,ve.ver005,ve.ver006,vd.ved002,vd.vru004,vd.vru005 from " +
					" a01_temp a,verify_error ve ,verify_error_detail vd where a.a0000=ve.ver002 and" +
					" ve.ver001=vd.ver001 and ve.ver003='1' and ve.ver004='"+imprecordid+"' order by a.a0101").list();
			List olist=sess.createSQLQuery("select distinct a.b0101,a.b0111,ve.ver005,ve.ver006,vd.ved002,vd.vru004,vd.vru005 from " +
					" b01_temp a,verify_error ve ,verify_error_detail vd where a.b0111=ve.ver002 and" +
					" ve.ver001=vd.ver001 and ve.ver003='2' and ve.ver004='"+imprecordid+"' order by a.b0101").list();
			//获取备份原文件
			Imprecord red = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, imprecordid);
			if(red.getIsvirety() == null || red.getIsvirety().equals("") ||red.getIsvirety().equals("0")){
				this.setMainMessage("数据未校验");
				return "";
			}
			//create excel file 
			String excelname = red.getFilename().substring(0,red.getFilename().lastIndexOf(".")) + ".xls";
			exportExcel(plist, olist, craeatePath + excelname, "");
			return craeatePath + excelname;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private void exportExcel(List plist, List olist, String excelname,
			String string2) {
		// 第一步，创建一个webbook，对应一个Excel文件  
        com.fr.third.org.apache.poi.hssf.usermodel.HSSFWorkbook wb = new com.fr.third.org.apache.poi.hssf.usermodel.HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        com.fr.third.org.apache.poi.hssf.usermodel.HSSFSheet sheet = wb.createSheet("人员错误信息");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
  
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("姓名");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 1);  
        cell.setCellValue("身份证号码");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 2);  
        cell.setCellValue("信息集");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 3);  
        cell.setCellValue("信息项");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 4);  
        cell.setCellValue("错误信息");  
        cell.setCellStyle(style);  
         
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
        if(plist!=null && plist.size()>0){
        	 for (int i = 0; i < plist.size(); i++)  
             {  
                 row = sheet.createRow((int) i + 1);  
                 // 第四步，创建单元格，并设置值  
                 row.createCell((short) 0).setCellValue((String) (((Object[]) plist.get(i))[0]));  
                 row.createCell((short) 1).setCellValue((String) (((Object[]) plist.get(i))[1])); 
                 row.createCell((short) 2).setCellValue(CodeManager.getValueByCode("VSL003", (String) (((Object[]) plist.get(i))[5]))); 
                 row.createCell((short) 3).setCellValue(CodeManager.getValueByCode("VSL004", (String) (((Object[]) plist.get(i))[6]))); 
                 row.createCell((short) 4).setCellValue((String) (((Object[]) plist.get(i))[4]));   
             }  
        }
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        com.fr.third.org.apache.poi.hssf.usermodel.HSSFSheet sheet2 = wb.createSheet("机构错误信息");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row2 = sheet2.createRow((int) 0);  
  
        HSSFCell cell2 = row2.createCell((short) 0);  
        cell2.setCellValue("机构名称");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell((short) 1);  
        cell2.setCellValue("机构编码");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell((short) 2);  
        cell2.setCellValue("信息集");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell((short) 3);  
        cell2.setCellValue("信息项");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell((short) 4);  
        cell2.setCellValue("错误信息");  
        cell2.setCellStyle(style);  
        
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
        if(olist!=null && olist.size()>0){
        	for (int i = 0; i < olist.size(); i++)  
            {  
                row = sheet2.createRow((int) i + 1);  
                // 第四步，创建单元格，并设置值  
                row.createCell((short) 0).setCellValue((String) (((Object[]) olist.get(i))[0]));  
                row.createCell((short) 1).setCellValue((String) (((Object[]) olist.get(i))[1])); 
                row.createCell((short) 2).setCellValue(CodeManager.getValueByCode("VSL003", (String) (((Object[]) olist.get(i))[5]))); 
                row.createCell((short) 3).setCellValue(CodeManager.getValueByCode("VSL004", (String) (((Object[]) olist.get(i))[6]))); 
                row.createCell((short) 4).setCellValue((String) (((Object[]) olist.get(i))[4]));   
            }  
       }
        // 第六步，将文件存到指定位置  
        try {  
            FileOutputStream fout = new FileOutputStream(excelname);  
            wb.write(fout);  
            fout.close();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
		
	}
	
	
	
	

}