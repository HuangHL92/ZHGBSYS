  package com.insigma.siis.local.pagemodel.sysorg.org;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.util.FileUtil;

public class SysPreviewPageModel extends PageModel {

	
	
	public SysPreviewPageModel() {
		
	}

	@Override
	public int doInit() throws RadowException {	
		this.setNextEventName("initX");
		return 0;
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException{
		
		SysPreviewPageModel pm = new SysPreviewPageModel();
	    String subWinIdBussessIds = this.getPageElement("subWinIdBussessId").getValue();
	   // System.out.println(subWinIdBussessIds);
	    /*try {
			subWinIdBussessIds = new String(subWinIdBussessIds.getBytes("ISO8859-1"),"GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	    String[] str = subWinIdBussessIds.split("\\|");
	    //System.out.println(str.toString());
	    String num = str[0];
	    String groupid = str[1];
		String execlName = "";
		if(str.length==4){
			execlName = str[3];
			this.getPageElement("execlName").setValue(execlName);
		}
		if("1".equals(num)){
			this.getExecuteSG().addExecuteCode("sum1();change1()");
			this.getExecuteSG().addExecuteCode("change1()");
		}
		if("2".equals(num)){
			this.getExecuteSG().addExecuteCode("sum2();change2()");
		}
		if("3".equals(num)){
			this.getExecuteSG().addExecuteCode("sum1();change1()");
			this.getPageElement("biaozhi").setValue("1");
		}
		if("4".equals(num)){
			this.getExecuteSG().addExecuteCode("sum2();change2()");
		}
		this.getPageElement("num").setValue(num);
		this.getPageElement("checkedgroupid").setValue(groupid);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public List<Map<String, Object>> one(String groupid) throws RadowException, AppException{
		
		List<B01> list = SysOrgBS.selectListBySubId(groupid);
		int counts = Integer.valueOf(SysOrgBS.selectCountBySubId(groupid));//下级机构数
		if(counts==0){
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			Map<String, Object> map = null;
			map = new HashMap<String, Object>();
			map.put("B0101", "");//机构名称
			map.put("B0127", "");//机构级别 ZB03   等
			map.put("B0194", "");//机构 
			
			/*领导职数*/
			map.put("zzB0183", "");//应配人数
			map.put("zzB0111", "");//实配人数
			map.put("zzover", "");//超配
			map.put("zzlow", "");//缺配
			
			/*正职领导职数*/
			map.put("zlB0183", "");//应配人数
			map.put("zlB0111", "");//实配人数
			map.put("zlover", "");//超配
			map.put("zllow", "");//缺配
			
			/*副职领导职数*/
			map.put("flB0185", "");//应配人数
			map.put("flB0111", "");//实配人数
			map.put("flover", "");//超配
			map.put("fllow", "");//缺配
			
//			map.put("zflB0188", "");//应配人数
//			map.put("zflB0111", "");//实配人数
//			
//			map.put("fflB0189", "");//应配人数
//			map.put("fflB0111", "");//实配人数
			
			resultList.add(map);
		
			return resultList;
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		for(B01 b01:list){
			map = new HashMap<String, Object>();
			String B0101 = b01.getB0101();//机构名称
			
			String B0127 = (String)SysOrgBS.getCodeValue("ZB03").get(b01.getB0127());//机构级别
			String B0194=b01.getB0194();
			B0194=HBUtil.getValueFromTab("code_name", "code_value", " code_type='B0194' and code_value='"+b01.getB0194()+"' ");
			if(B0194==null){
				B0194="";
			}
			//String B0131 = (String)SysOrgBS.getCodeValue("ZB04").get(b01.getB0131());//机构性质类别 ZB04 
			
			Integer zlB0183 = Integer.parseInt((b01.getB0183()==null?"0":b01.getB0183().toString()));//应配人数（正职领导职数）	
			Integer zlB0111 = Integer.parseInt(CreateSysOrgBS.selectRightLeaderCount(b01.getB0111()));//实配人数（正职领导职数）
			Integer zlover = 0;
			Integer zllow = 0;
			if(zlB0111>zlB0183){
				zlover = zlB0111-zlB0183;//超配（正职领导职数）
			}else{
				zllow = zlB0183-zlB0111;//缺配（正职领导职数）
			}
			
			Integer flB0185 = Integer.parseInt(b01.getB0185()==null?"0":b01.getB0185().toString());//(副职领导职数)应配人数
			Integer flB0111 = Integer.parseInt(CreateSysOrgBS.selectViceLeaderCount(b01.getB0111()));//(副职领导职数)实配人数
			Integer flover = 0;
			Integer fllow = 0;
			if(flB0111>flB0185){
				flover = flB0111-flB0185;//超配（副职领导职数）
			}
			if(flB0111<=flB0185){
				fllow = flB0185-flB0111;////缺配（副职领导职数）

			}
			
			//Integer zflB0188 = 0;//Integer.parseInt(b01.getB0188().toString());//(同级正职非领导职数) 应配人数
			//Integer zflB0111 = Integer.parseInt(CreateSysOrgBS.selectRightNoLeaderCount(b01.getB0111()));//(同级正职非领导职数) 实配人数
			/*Integer zflover = 0;
			Integer zfllow = 0;*/
			/*if(zflB0111>zflB0188){
				zflover = zflB0111-zflB0188;//超配(同级正职非领导职数)
			}
			if(zflB0111<=zflB0188){
				zfllow = zflB0188-zflB0111;//缺配(同级正职非领导职数)

			}*/
			
			//Integer fflB0189 = 0;//Integer.parseInt(b01.getB0189().toString());//(同级副职非领导职数) 应配人数
			//Integer fflB0111 = Integer.parseInt(CreateSysOrgBS.selectViceNoLeaderCount(b01.getB0111()));//(同级副职非领导职数) 实配人数
			/*Integer fflover = 0;
			Integer ffllow = 0;*/
			/*if(fflB0111>fflB0189){
				fflover = fflB0111-fflB0189;//超配(同级副职非领导职数)
			}
			if(fflB0111<=fflB0189){
				ffllow = fflB0189-fflB0111;//缺配(同级副职非领导职数)
			}*/
			map.put("B0101", B0101);//机构名称        
			map.put("B0127", B0127);//机构级别 ZB03   
			map.put("B0194", B0194);//机构类型 1法人单位 2内设机构 3机构分组
			
			
			/*正职领导职数*/
			map.put("zlB0183", zlB0183); //应配人数   
			map.put("zlB0111", zlB0111); //实配人数   
			map.put("zlover", zlover);   //超配      
			map.put("zllow", zllow);     //缺配       
			
			/*副职领导职数*/
			map.put("flB0185", flB0185);//应配人数   
			map.put("flB0111", flB0111);//实配人数   
			map.put("flover", flover);  //超配      
			map.put("fllow", fllow);    //缺配       
			
			/*领导职数*/
			map.put("zzB0183", zlB0183+flB0185);//应配人数
			map.put("zzB0111", zlB0111+flB0111);//实配人数
			map.put("zzover", zlover+flover);//超配
			map.put("zzlow", zllow+fllow);//缺配
			
			//map.put("zflB0188", zflB0188);
			//map.put("zflB0111", zflB0111);
			//map.put("zflover", zflover);
			//map.put("zfllow", zfllow);
			
			//map.put("fflB0189", fflB0189);
			//map.put("fflB0111", fflB0111);
			//map.put("fflover", fflover);
			//map.put("ffllow", ffllow);
			
			resultList.add(map);
		}
		return resultList;
	}
	
	public List<Map<String, Object>> two(String groupid) throws RadowException {
		//List<B01> list = SysOrgBS.selectListByExecl(groupid);
		List<B01> list = SysOrgBS.selectListBySubIdBz(groupid);
		int counts = Integer.valueOf(SysOrgBS.selectCountBySubIdBz(groupid));//下级机构数
		if(counts==0){
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			Map<String, Object> map = null;
			map = new HashMap<String, Object>();
			map.put("B0101", "");
			map.put("B0127", "");
			
			map.put("xzB0227", "");
			map.put("xzB0111", "");
			map.put("xzover", "");
			map.put("xzlow", "");
			
			map.put("syB0232", "");
			map.put("syB0111", "");
			map.put("syover", "");
			map.put("sylow", "");
			
			map.put("qtB0233", "");
			map.put("qtB0111", "");
			map.put("qtover", "");
			map.put("qtlow", "");
			
//			map.put("zfzxB0235", "");
//			map.put("zfzxB0111", "");
//			map.put("zfzxover", "");
//			map.put("zfzxlow", "");
			
			map.put("gqB0236", "");
			map.put("gqB0111", "");
			map.put("gqover", "");
			map.put("gqlow", "");
			
			map.put("qt2B0234", "");
			map.put("qt2B0111", "");
			map.put("qt2over", "");
			map.put("qt2low", "");
			
			resultList.add(map);
		
			return resultList;
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		for(B01 b01:list){
			map = new HashMap<String, Object>();
			String B0101 = b01.getB0101();//机构名称
			
			String B0127 = (String)SysOrgBS.getCodeValue("ZB03").get(b01.getB0127());//机构级别
			
			Integer xzB0227 = Integer.parseInt((b01.getB0227()==null?"0":b01.getB0227().toString()));//应配人数（行政编制数）	
			Integer xzB0111 = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"1"));//实配人数（行政编制数）
			Integer xzover = 0;
			Integer xzlow = 0;
			if(xzB0111>xzB0227){
				xzover = xzB0111-xzB0227;//超配（行政编制数）
			}else{
				xzlow = xzB0227-xzB0111;//缺配（行政编制数）
			}
			
			
			Integer syB0232 = Integer.parseInt(b01.getB0232()==null?"0":b01.getB0232().toString());//(参照公务员法管理事业编制数)应配人数
			Integer syB0111 = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"2"));//(参照公务员法管理事业编制数)实配人数
			Integer syover = 0;
			Integer sylow = 0;
			if(syB0111>syB0232){
				syover = syB0111-syB0232;//超配（参照公务员法管理事业编制数）
			}else {
				sylow = syB0232-syB0111;////缺配（参照公务员法管理事业编制数）

			}
			
			Integer qtB0233 = Integer.parseInt(b01.getB0233()==null?"0":b01.getB0233().toString());//(其他事业编制数) 应配人数
			Integer qtB0111 = Integer.parseInt(CreateSysOrgBS.getServantCounts(b01.getB0111(),"3"));//(其他事业编制数) 实配人数
			Integer qtover = 0;
			Integer qtlow = 0;
			if(qtB0111>qtB0233){
				qtover = qtB0111-qtB0233;//超配(其他事业编制数)
			}else{
				qtlow = qtB0233-qtB0111;//缺配(其他事业编制数)

			}
			
			//Integer zfzxB0235 = Integer.parseInt(b01.getB0235()==null?"0":b01.getB0235().toString());//(政法专项编制数) 应配人数
			//Integer zfzxB0111 = Integer.parseInt(CreateSysOrgBS.getServantCounts(b01.getB0111(),"2"));//(政法专项编制数) 实配人数
//			Integer zfzxover = 0;
//			Integer zfzxlow = 0;
//			if(zfzxB0111>zfzxB0235){
//				zfzxover = zfzxB0111-zfzxB0235;//超配(政法专项编制数)
//			}else{
//				zfzxlow = zfzxB0235-zfzxB0111;//缺配(政法专项编制数)
//
//			}
			
			Integer gqB0236 = Integer.parseInt(b01.getB0236()==null?"0":b01.getB0236().toString());//(工勤编制数) 应配人数
			Integer gqB0111 = Integer.parseInt(CreateSysOrgBS.getServantCounts(b01.getB0111(),"4"));//(工勤编制数) 实配人数
			Integer gqover = 0;
			Integer gqlow = 0;
			if(gqB0111>gqB0236){
				gqover = gqB0111-gqB0236;//超配(工勤编制数)
			}else{
				gqlow = gqB0236-gqB0111;//缺配(工勤编制数)

			}
			
			Integer qt2B0234 = Integer.parseInt(b01.getB0234()==null?"0":b01.getB0234().toString());//(其他编制数) 应配人数
			Integer qt2B0111 = Integer.parseInt(CreateSysOrgBS.getServantCounts(b01.getB0111(),"9"));//(其他编制数) 实配人数
			Integer qt2over = 0;
			Integer qt2low = 0;
			if(qt2B0111>qt2B0234){
				qt2over = qt2B0111-qt2B0234;//超配(其他事业编制数)
			}else{
				qt2low = qt2B0234-qt2B0111;//缺配(其他事业编制数)

			}
			
			map.put("B0101", B0101);
			map.put("B0127", B0127);
			
			map.put("xzB0227", xzB0227);
			map.put("xzB0111", xzB0111);
			map.put("xzover", xzover);
			map.put("xzlow", xzlow);
			
			map.put("syB0232", syB0232);
			map.put("syB0111", syB0111);
			map.put("syover", syover);
			map.put("sylow", sylow);
			
			map.put("qtB0233", qtB0233);
			map.put("qtB0111", qtB0111);
			map.put("qtover", qtover);
			map.put("qtlow", qtlow);
			
//			map.put("zfzxB0235", zfzxB0235);
//			map.put("zfzxB0111", zfzxB0111);
//			map.put("zfzxover", zfzxover);
//			map.put("zfzxlow", zfzxlow);
			
			map.put("gqB0236", gqB0236);
			map.put("gqB0111", gqB0111);
			map.put("gqover", gqover);
			map.put("gqlow", gqlow);
			
			map.put("qt2B0234", qt2B0234);
			map.put("qt2B0111", qt2B0111);
			map.put("qt2over", qt2over);
			map.put("qt2low", qt2low);
			
			resultList.add(map);
		}
		return resultList;
	}
	
	public List<Map<String, Object>> three(List<B01> list) throws RadowException, AppException{
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		for(B01 b01:list){
			map = new HashMap<String, Object>();
			String B0101 = b01.getB0101();//机构名称
			
			String B0127 = (String)SysOrgBS.getCodeValue("ZB03").get(b01.getB0127());//机构级别
			String B0194=HBUtil.getValueFromTab("code_name", "code_value", " code_type='B0194' and code_value='"+b01.getB0194()+"' ");
			//String B0131 = (String)SysOrgBS.getCodeValue("ZB04").get(b01.getB0131());//机构性质类别 ZB04 
			
			Integer zlB0183 = Integer.parseInt((b01.getB0183()==null?"0":b01.getB0183().toString()));//应配人数（正职领导职数）	
			Integer zlB0111 = Integer.parseInt(CreateSysOrgBS.selectRightLeaderCount(b01.getB0111()));//实配人数（正职领导职数）
			Integer zlover = 0;
			Integer zllow = 0;
			if(zlB0111>zlB0183){
				zlover = zlB0111-zlB0183;//超配（正职领导职数）
			}else{
				zllow = zlB0183-zlB0111;//缺配（正职领导职数）
			}
			
			
			Integer flB0185 = Integer.parseInt(b01.getB0185()==null?"0":b01.getB0185().toString());//(副职领导职数)应配人数
			Integer flB0111 = Integer.parseInt(CreateSysOrgBS.selectViceLeaderCount(b01.getB0111()));//(副职领导职数)实配人数
			Integer flover = 0;
			Integer fllow = 0;
			if(flB0111>flB0185){
				flover = flB0111-flB0185;//超配（副职领导职数）
			}
			if(flB0111<=flB0185){
				fllow = flB0185-flB0111;////缺配（副职领导职数）

			}
			
			//Integer zflB0188 =0;//(同级正职非领导职数) 应配人数 Integer.parseInt(b01.getB0188().toString())
			//Integer zflB0111 = Integer.parseInt(CreateSysOrgBS.selectRightNoLeaderCount(b01.getB0111()));//(同级正职非领导职数) 实配人数
			/*Integer zflover = 0;
			Integer zfllow = 0;*/
			/*if(zflB0111>zflB0188){
				zflover = zflB0111-zflB0188;//超配(同级正职非领导职数)
			}
			if(zflB0111<=zflB0188){
				zfllow = zflB0188-zflB0111;//缺配(同级正职非领导职数)

			}*/
			
			//Integer fflB0189 = 0;//(同级副职非领导职数) 应配人数 Integer.parseInt(b01.getB0189().toString())
			//Integer fflB0111 = Integer.parseInt(CreateSysOrgBS.selectViceNoLeaderCount(b01.getB0111()));//(同级副职非领导职数) 实配人数
			/*Integer fflover = 0;
			Integer ffllow = 0;*/
			/*if(fflB0111>fflB0189){
				fflover = fflB0111-fflB0189;//超配(同级副职非领导职数)
			}
			if(fflB0111<=fflB0189){
				ffllow = fflB0189-fflB0111;//缺配(同级副职非领导职数)
			}*/
			map.put("B0101", B0101);
			map.put("B0127", B0127);
			map.put("B0194", B0194);
			
			map.put("zlB0183", zlB0183);
			map.put("zlB0111", zlB0111);
			map.put("zlover", zlover);
			map.put("zllow", zllow);
			
			map.put("flB0185", flB0185);
			map.put("flB0111", flB0111);
			map.put("flover", flover);
			map.put("fllow", fllow);
			
			/*领导职数*/
			map.put("zzB0183", zlB0183+flB0185);//应配人数
			map.put("zzB0111", zlB0111+flB0111);//实配人数
			map.put("zzover", zlover+flover);//超配
			map.put("zzlow", zllow+fllow);//缺配
			
//			map.put("zflB0188", zflB0188);
//			map.put("zflB0111", zflB0111);
			//map.put("zflover", zflover);
			//map.put("zfllow", zfllow);
			
//			map.put("fflB0189", fflB0189);
//			map.put("fflB0111", fflB0111);
			//map.put("fflover", fflover);
			//map.put("ffllow", ffllow);
			
			resultList.add(map);
		}
		return resultList;
	}
	
	public List<Map<String, Object>> four(List<B01> list) throws RadowException, AppException {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		for(B01 b01:list){
			map = new HashMap<String, Object>();
			String B0101 = b01.getB0101();//机构名称
			
			String B0127 = (String)SysOrgBS.getCodeValue("ZB03").get(b01.getB0127());//机构级别
			
			Integer xzB0227 = Integer.parseInt((b01.getB0227()==null?0:b01.getB0227())+"");//应配人数（行政编制数）	
			Integer xzB0111 = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"1"));//实配人数（行政编制数）
			Integer xzover = 0;
			Integer xzlow = 0;
			if(xzB0111>xzB0227){
				xzover = xzB0111-xzB0227;//超配（行政编制数）
			}else{
				xzlow = xzB0227-xzB0111;//缺配（行政编制数）
			}
			
			
			Integer syB0232 = Integer.parseInt((b01.getB0232()==null?0:b01.getB0232())+"");//(参照公务员法管理事业编制数)应配人数
			Integer syB0111 = Integer.valueOf(CreateSysOrgBS.getServantCounts(b01.getB0111(),"2"));//(参照公务员法管理事业编制数)实配人数
			Integer syover = 0;
			Integer sylow = 0;
			if(syB0111>syB0232){
				syover = syB0111-syB0232;//超配（参照公务员法管理事业编制数）
			}else {
				sylow = syB0232-syB0111;////缺配（参照公务员法管理事业编制数）

			}
			
			Integer qtB0233 = Integer.parseInt((b01.getB0233()==null?0:b01.getB0233())+"");//(其他事业编制数) 应配人数
			Integer qtB0111 = Integer.parseInt(CreateSysOrgBS.getServantCounts(b01.getB0111(),"3"));//(其他事业编制数) 实配人数
			Integer qtover = 0;
			Integer qtlow = 0;
			if(qtB0111>qtB0233){
				qtover = qtB0111-qtB0233;//超配(其他事业编制数)
			}else{
				qtlow = qtB0233-qtB0111;//缺配(其他事业编制数)

			}
			
			
			//Integer zfzxB0235 = Integer.parseInt(b01.getB0235()==null?"0":b01.getB0235().toString());//(政法专项编制数) 应配人数
			//Integer zfzxB0111 = Integer.parseInt(CreateSysOrgBS.getServantCounts(b01.getB0111(),"2"));//(政法专项编制数) 实配人数
			Integer zfzxover = 0;
			Integer zfzxlow = 0;
//			if(zfzxB0111>zfzxB0235){
//				zfzxover = zfzxB0111-zfzxB0235;//超配(政法专项编制数)
//			}else{
//				zfzxlow = zfzxB0235-zfzxB0111;//缺配(政法专项编制数)
//
//			}
//			
			Integer gqB0236 = Integer.parseInt(b01.getB0236()==null?"0":b01.getB0236().toString());//(工勤编制数) 应配人数
			Integer gqB0111 = Integer.parseInt(CreateSysOrgBS.getServantCounts(b01.getB0111(),"4"));//(工勤编制数) 实配人数
			Integer gqover = 0;
			Integer gqlow = 0;
			if(gqB0111>gqB0236){
				gqover = gqB0111-gqB0236;//超配(工勤编制数)
			}else{
				gqlow = gqB0236-gqB0111;//缺配(工勤编制数)

			}
			
			Integer qt2B0234 = Integer.parseInt(b01.getB0234()==null?"0":b01.getB0234().toString());//(其他编制数) 应配人数
			Integer qt2B0111 = Integer.parseInt(CreateSysOrgBS.getServantCounts(b01.getB0111(),"9"));//(其他编制数) 实配人数
			Integer qt2over = 0;
			Integer qt2low = 0;
			if(qt2B0111>qt2B0234){
				qt2over = qt2B0111-qt2B0234;//超配(其他事业编制数)
			}else{
				qt2low = qt2B0234-qt2B0111;//缺配(其他事业编制数)

			}
			
			
			map.put("B0101", B0101);
			map.put("B0127", B0127);
			
			map.put("xzB0227", xzB0227);
			map.put("xzB0111", xzB0111);
			map.put("xzover", xzover);
			map.put("xzlow", xzlow);
			
			map.put("syB0232", syB0232);
			map.put("syB0111", syB0111);
			map.put("syover", syover);
			map.put("sylow", sylow);
			
			map.put("qtB0233", qtB0233);
			map.put("qtB0111", qtB0111);
			map.put("qtover", qtover);
			map.put("qtlow", qtlow);
			
			//map.put("zfzxB0235", zfzxB0235);
			//map.put("zfzxB0111", zfzxB0111);
			map.put("zfzxover", zfzxover);
			map.put("zfzxlow", zfzxlow);
			
			map.put("gqB0236", gqB0236);
			map.put("gqB0111", gqB0111);
			map.put("gqover", gqover);
			map.put("gqlow", gqlow);
			
			map.put("qt2B0234", qt2B0234);
			map.put("qt2B0111", qt2B0111);
			map.put("qt2over", qt2over);
			map.put("qt2low", qt2low);
			
			resultList.add(map);
		}
		return resultList;
	}
	
	@PageEvent("exp.onclick")
	public int export() throws RadowException, AppException{
	
	    String subWinIdBussessIds = this.getPageElement("subWinIdBussessId").getValue();
	    String[] str = subWinIdBussessIds.split("\\|");
	    String sql = "";
	    if(str.length>2){
	    	sql = str[2];
	    }
		
		String num = this.getPageElement("num").getValue();
		if("1".equals(num)){
			/*String fileName ="各单位编制与人员配备表";
			String laststr=".xls";
			String loadFile= ExpRar.getExeclPath()+"\\"+fileName+laststr;
			String expFile=ExpRar.expFile()+fileName + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+laststr;
//			System.out.println(loadFile);
//			System.out.println(expFile);
			String groupid = this.getPageElement("checkedgroupid").getValue();
			if(groupid.trim().equals("")||groupid==null){
				throw new RadowException("请选择机构!");
			}
			if(!SysRuleBS.havaRule(groupid)){
				throw new RadowException("您无此权限!");
			}
			InputStream is;
			try {
				FileUtil.createFile(expFile);
				is = new FileInputStream(new File(loadFile));
				int counts = Integer.valueOf(SysOrgBS.selectCountBySubId(groupid));
	            List<B01> list = SysOrgBS.selectListByExecl(groupid);
				byte[] bytes =SysOrgBS.wirteExeclLowOrgPeople(is,counts,list);
			    FileOutputStream outf=new FileOutputStream(expFile);
			    BufferedOutputStream bufferout= new BufferedOutputStream(outf);
			    bufferout.write(bytes);
			    bufferout.flush();
			    bufferout.close();
			    this.getPageElement("downfile").setValue(expFile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.download()");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			String fileName ="各单位领导职数配备表";//
			String laststr=".xls";
			String loadFile= ExpRar.getExeclPath()+"\\"+fileName+laststr;
			String expFile=ExpRar.expFile()+fileName + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+laststr;
			CommonQueryBS.systemOut(loadFile);
			CommonQueryBS.systemOut(expFile);
			String groupid = this.getPageElement("checkedgroupid").getValue();
			if(groupid.trim().equals("")||groupid==null){
				throw new RadowException("请选择机构!");
			}
			if(!SysRuleBS.havaRule(groupid)){
				throw new RadowException("您无此权限!");
			}
			InputStream is;
			try {
				FileUtil.createFile(expFile);
				is = new FileInputStream(new File(loadFile));
	            //List<B01> list = SysOrgBS.selectListByExecl(groupid);
				List<B01> list = SysOrgBS.selectListBySubId(groupid);
				int counts = Integer.valueOf(SysOrgBS.selectCountBySubId(groupid));
				byte[] bytes =SysOrgBS.wirteExeclLowOrgLeader(is,list,counts);
			    FileOutputStream outf=new FileOutputStream(expFile);
			    BufferedOutputStream bufferout= new BufferedOutputStream(outf);
			    bufferout.write(bytes);
			    bufferout.flush();
			    bufferout.close();
			    this.getPageElement("downfile").setValue(expFile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.download()");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("2".equals(num)){
			String fileName ="各单位编制与人员配备表";//
			String laststr=".xls";
			String loadFile= ExpRar.getExeclPath()+"\\"+fileName+laststr;
			String expFile=ExpRar.expFile()+fileName + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+laststr;
			//System.out.println(loadFile);
			//System.out.println(expFile);
			String groupid = this.getPageElement("checkedgroupid").getValue();
			if(groupid.trim().equals("")||groupid==null){
				throw new RadowException("请选择机构!");
			}
			if(!SysRuleBS.havaRule(groupid)){
				throw new RadowException("您无此权限!");
			}
			InputStream is;
			try {
				FileUtil.createFile(expFile);
				is = new FileInputStream(new File(loadFile));
				int counts = Integer.valueOf(SysOrgBS.selectCountBySubIdBz(groupid));
	           // List<B01> list = SysOrgBS.selectListByExecl(groupid);
				List<B01> list = SysOrgBS.selectListBySubIdBz(groupid);
				byte[] bytes =SysOrgBS.wirteExeclLowOrgPeople(is,counts,list);
			    FileOutputStream outf=new FileOutputStream(expFile);
			    BufferedOutputStream bufferout= new BufferedOutputStream(outf);
			    bufferout.write(bytes);
			    bufferout.flush();
			    bufferout.close();
			    this.getPageElement("downfile").setValue(expFile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.download()");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if("3".equals(num) || "4".equals(num)){
			String execlName = this.getPageElement("execlName").getValue();
			CommonQueryBS.systemOut(execlName);
			
			String value = this.getPageElement("checkedgroupid").getValue();
			//String s = this.getPageElement("sql").getValue();//改
			List<B01> list = yuLanMany(value,sql,num);//改
			
			String expFile ="";
			if("people".equals(execlName)){
				expFile = peopleExp(list);
			}else if("leader".equals(execlName)){
				expFile = leaderExp(list);
			}else{
				return EventRtnType.FAILD;
			}
		    this.getPageElement("downfile").setValue(expFile.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.download()");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public List<B01> getTreeList(String value){
		String ret=null;
		String[] nodes = null;
		HashMap<String,String> nodemap = new HashMap<String,String>();
		nodes = value.split(",");
		for(int i=0;i<nodes.length;i++) {
			nodemap.put(nodes[i].split(":")[0], nodes[i].split(":")[1]);
		}
		StringBuffer addresourceIds = new StringBuffer();
		StringBuffer removeresourceIds = new StringBuffer();
		for(String node :nodemap.keySet()) {
			if(nodemap.get(node).equals("true")) {
				addresourceIds.append("'"+node+"',");
			}else if(nodemap.get(node).equals("false")) {
				removeresourceIds.append(node+",");
			}
		}
		ret=addresourceIds.toString();
		String b01String="";
		List<B01> list= null;
		if(ret!=null&&!"".equals(ret)){
			b01String=ret.substring(0,ret.lastIndexOf(","));
			list = selectListByIds(b01String);
		}
		return list;
	}
	
	public List<B01> selectListByIds(String ids){
		List<B01> list = new ArrayList<B01>();
		String userId = SysUtil.getCacheCurrentUser().getId();
		if(ids.length()>0){
			String sql="Select b0111 from B01 b where b.b0194 in ('1','2') and exists (select * from COMPETENCE_USERDEPT t where t.userid='"+userId+"' and t.b0111 in  ("+ids+") and t.type in ('0','1') and t.b0111=b.b0111)";
			List liststr = HBUtil.getHBSession().createSQLQuery(sql).list();
			String str="";
			if(liststr.size()>0){
				for(int i=0;i<liststr.size();i++){
					str = str+"'"+liststr.get(i)+"',";
				}
				str=str.substring(0,str.length()-1);
				String sql2="from B01 where b0111 in ("+str+") order by b0111";
				list = HBUtil.getHBSession().createQuery(sql2).list();	
			}
		}
		return list;
	}
	
	public String leaderExp(List<B01> list) throws RadowException, AppException  {
		String fileName ="各单位领导职数配备表";//
		String laststr=".xls";
		String loadFile= ExpRar.getExeclPath()+"\\"+fileName+laststr;
		String expFile=ExpRar.expFile()+fileName + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+laststr;
		CommonQueryBS.systemOut(loadFile);
		CommonQueryBS.systemOut(expFile);
		InputStream is;
		try {
			FileUtil.createFile(expFile);
			is = new FileInputStream(new File(loadFile));
			int counts=0;
			if(list !=null){
				counts = list.size();
			}
			byte[] bytes =SysOrgBS.wirteExeclLowOrgLeader(is,list,counts);
		    FileOutputStream outf=new FileOutputStream(expFile);
		    BufferedOutputStream bufferout= new BufferedOutputStream(outf);
		    bufferout.write(bytes);
		    bufferout.flush();
		    bufferout.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return expFile;
	}
	
	public String peopleExp(List<B01> list) throws RadowException  {
		String fileName ="各单位编制与人员配备表";//
		String laststr=".xls";
		String loadFile= ExpRar.getExeclPath()+"\\"+fileName+laststr;
		String expFile=ExpRar.expFile()+fileName + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+laststr;
		CommonQueryBS.systemOut(loadFile);
		CommonQueryBS.systemOut(expFile);
		InputStream is;
		try {
			FileUtil.createFile(expFile);
			is = new FileInputStream(new File(loadFile));
			int counts=0;
			if(list !=null){
				counts = list.size();
			}
			byte[] bytes =SysOrgBS.wirteExeclLowOrgPeople(is,counts,list);
		    FileOutputStream outf=new FileOutputStream(expFile);
		    BufferedOutputStream bufferout= new BufferedOutputStream(outf);
		    bufferout.write(bytes);
		    bufferout.flush();
		    bufferout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return expFile;
	}
	
	public String getSqlStrByList(List sqhList, int splitNum,String columnName) { //bm,1000,"b0111"
		if (splitNum > 1000) // 因为数据库的列表sql限制，不能超过1000.
			return null;
		StringBuffer ids = new StringBuffer("");
		if (sqhList != null) {
			ids.append(" ").append(columnName).append(" IN ( ");
			for (int i = 0; i < sqhList.size(); i++) {
				ids.append("'").append(sqhList.get(i) + "',");
				if ((i + 1) % splitNum == 0 && (i + 1) < sqhList.size()) {
					ids.deleteCharAt(ids.length() - 1);
					ids.append(" ) OR ").append(columnName).append(" IN (");
				}
			}
			ids.deleteCharAt(ids.length() - 1);
			ids.append(" )");
		}
		return ids.toString();
	}
		
	public List<B01> yuLanMany(String b01String,String sql,String num) throws RadowException {
		//String userID = SysManagerUtils.getUserId();
		//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+b01String);//{"001.001.00T":"浙江省人民政府(全部机构):true:true"}
        String userId = SysUtil.getCacheCurrentUser().getId();
        //List<B01> list = new ArrayList<B01>();
		String sql_="";
		sql_ = "select b0111,"//机构主键
        		+ " b0127,"//机构级别 ZB03   等
        		+ " b0183,"//正职领导职数
        		+ " b0185,"//副职领导职数
        		+ " b0188,"//同级正职非领导职数(无效)   少
        		+ " b0189,"//同级副职非领导职数(无效)   少
        		+ " b0227,"//行政编制数   等
        		+ " b0232,"//事业编制数(参公)
        		+ " b0233,"//事业编制数(其他)
        		+ " b0101,"//机构名称
        		+ " b0235,"//政法专项编制数-已经取消
        		+ " b0236,"//工勤编制数
        		+ " b0234, "//其他编制数
        		+ " B0194"//机构性质类别 ZB04 
        		+ " from B01 b ";
        		if("3".equals(num)){//职数统计
        			sql_=sql_+ " where b.b0194 in ('1','2') ";//1 法人单位，2 内设机构
				}else if("4".equals(num)){//编制统计
					sql_=sql_+ " where b.b0194 ='1' ";//1 法人单位
				}
        		sql_=sql_+ " and b.b0111 in (select cu.b0111 from COMPETENCE_USERDEPT cu "
        						+ " where cu.userid='"+userId+"' "
        						+ " "+sql+" "
        						+ " and cu.type in ('0','1') "
				        		//+ " and cu.b0111=b.b0111"
				        		+ " ) "
				        		+ " order by b0111";
        //System.out.println("sql~~~~~~~"+sql);
        List<Object[]> liststr = new ArrayList<Object[]>();
        liststr = HBUtil.getHBSession().createSQLQuery(sql_).list();
//      System.out.println(liststr.toString());
        List<B01> list = new ArrayList<B01>();
        for( Object[] obj:liststr){
        	B01 b = new B01();
        	//System.out.println(obj[0]);
        	if(obj[0]!=null&&obj[0]!=""){////机构主键 		
        		b.setB0111(obj[0].toString());
        	}
        	if(obj[1]!=null&&obj[1]!=""){//机构级别 ZB03   等
        		b.setB0127(obj[1].toString());
        	}
        	if(obj[2]!=null&&obj[2]!=""){//正职领导职数
        		b.setB0183(Long.valueOf(obj[2].toString()));
        	}
        	if(obj[3]!=null&&obj[3]!=""){//副职领导职数
        		b.setB0185(Long.valueOf(obj[3].toString()));
        	}
//        	if(obj[4]!=null&&obj[4]!=""){//同级正职非领导职数(无效)   
//        		b.setB0188(Long.valueOf(obj[4].toString()));
//        	}
//        	if(obj[5]!=null&&obj[5]!=""){//同级副职非领导职数(无效)
//        		b.setB0189(Long.valueOf(obj[5].toString()));
//        	}
        	if(obj[6]!=null&&obj[6]!=""){//行政编制数   等
        		b.setB0227(Long.valueOf(obj[6].toString()));
        	}
        	if(obj[7]!=null&&obj[7]!=""){//事业编制数(参公)
        		b.setB0232(Long.valueOf(obj[7].toString()));
        	}
        	if(obj[8]!=null&&obj[8]!=""){//事业编制数(其他)
        		b.setB0233(Long.valueOf(obj[8].toString()));
        	}
        	if(obj[9]!=null&&obj[9]!=""){//机构名称
        		b.setB0101(obj[9].toString());
        	}
        	
//        	if(obj[10]!=null&&obj[10]!=""){//政法专项编制数
//        		b.setB0235(Long.valueOf(obj[10].toString()));
//        	}
//        	if(obj[11]!=null&&obj[11]!=""){//工勤编制数
//        		b.setB0236(Long.valueOf(obj[11].toString()));
//        	}
//        	if(obj[12]!=null&&obj[12]!=""){//其他编制数
//        		b.setB0234(Long.valueOf(obj[12].toString()));
//        	}
        	if(obj[13]!=null&&obj[13]!=""){//机构类型
        		b.setB0194(obj[13].toString());
        	}
        	list.add(b);
        }
        
		/*
		List bm = new ArrayList();
		if(liststr.size()>0){
			for(int i=0;i<liststr.size();i++){
				bm.add(liststr.get(i));
			}
			String ping = getSqlStrByList(bm,1000,"b0111");
			String sql2="from B01 where "+ping+" order by b0111";
			
			System.out.println("sql2~~~~~~~"+sql2);
			
		}*/
        //list = HBUtil.getHBSession().createQuery(sql).list();	
		return list;
	}
	
}
