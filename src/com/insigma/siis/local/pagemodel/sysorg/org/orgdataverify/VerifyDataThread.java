package com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.siis.local.business.datavaerify.DataVerifyBS;
import com.insigma.siis.local.business.entity.VerifyProcess;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.util.LargetaskLog;

public class VerifyDataThread { //implements Runnable  邹志林，这里不用线程
	private String vpid ;
	private String bsType ;
	private String b0111 ;
	private String ruleids ;
	private String a0163 ;
	private UserVO user;
	private String needverifyid;
	
	public VerifyDataThread(String vpid , String b0111,String ruleids,String bsType,UserVO user,String a0163,String needverifyid) {
		super();
		this.vpid = vpid;
		this.bsType = bsType;
		this.b0111 = b0111;
		this.ruleids = ruleids;
		this.user = user;
		this.a0163 = a0163;
		this.needverifyid = needverifyid;
	}
	public VerifyDataThread(String vpid , String b0111,String ruleids,String bsType,UserVO user) {
		super();
		this.vpid = vpid;
		this.bsType = bsType;
		this.b0111 = b0111;
		this.ruleids = ruleids;
		this.user = user;
	}
	//@Override
	public void run() {
		boolean flag = true;
		//CommonQueryBS.systemOut("     ------>批次-" + b0111 + ",校验规则-" + vsc001+",业务类型-"+bsType);
		CommonQueryBS.systemOut("校验开始------>"
				+ new Date().toString());
		String userloginName = SysManagerUtils.getUserloginName();
		Map<String, String> map=new HashMap<String, String>();
		map.put("LARGETASK_TYPE_CODE", "TASK_DATA_CHECK");
		map.put("LARGETASK_LOG_DETAIL", "整库数据校核");
		map.put("TASK_BEGIN_TIME", "");
		map.put("EXECUTE_STATE_ID", "working");
		map.put("START_MODEL_ID", "hand");
		map.put("ISREAD", "0");
		map.put("OPERATE_USERNAME", userloginName);
		map.put("EXEC_PERCENT", "100.00");
		LargetaskLog.MainLog(map);
		VerifyProcess vp= null;  
		HBSession sess =  HBUtil.getHBSession();
		try {
			flag = DataVerifyBS.dataVerifyByBSType(b0111, ruleids,bsType,vp,user,a0163,needverifyid);
			vp=(VerifyProcess) sess.get(VerifyProcess.class, vpid);
			vp.setResultFlag(flag?1L:0L);
			vp.setProcessMsg(flag?"校验结束,无错误信息！":"校验结束,请点击Tab页查看！");
			//vp.setProcessMsg(flag?"校验通过！":"校验未通过！");
			sess.saveOrUpdate(vp);
			sess.flush();
			Map<String, String> mapEnd=new HashMap<String, String>();
			mapEnd.put("EXECUTE_STATE_ID", "success");
			mapEnd.put("TASK_END_TIME", "");
			LargetaskLog.MainLog(mapEnd);
		} catch (Exception e) {
			e.printStackTrace();
			vp.setResultFlag(-1L);
			vp.setProcessMsg("校验失败！异常信息："+e.getMessage());
			Map<String, String> mapEnd=new HashMap<String, String>();
			mapEnd.put("EXECUTE_STATE_ID", "fail");
			mapEnd.put("TASK_END_TIME", "");
			LargetaskLog.MainLog(mapEnd);
//			sess.saveOrUpdate(vp);
//			sess.flush();
			e.printStackTrace();
			CommonQueryBS.systemOut("校验结束e------>"
					+ DateUtil.dateToString(new Date(), "yyyyMMdd HH:mm:ss"));
			return;
		}finally{
			//==============邹志林 2018-12-25========
			if (sess != null){
				sess.closeSession();
			}
			//====================================
		}

		CommonQueryBS.systemOut("校验结束------>"
				+ new Date().toString());
		System.gc();
		LargetaskLog.mainID="";
	}

	
	
}
