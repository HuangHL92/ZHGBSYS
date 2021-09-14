package com.insigma.siis.local.pagemodel.mntpsj.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.entity.Publish;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.mntpsj.MNTPSJDWPageModel;
import com.insigma.siis.local.pagemodel.mntpsj.MNTPSJOPPageModel;

public class SHUtils {

	public static void shanghui(String param, MNTPSJOPPageModel page) throws Exception {
		String[] params = param.split("@@");
		String type = params[0];
		String selfamx00 = params[1];
		
		String fabd00 = page.getPageElement("fabd00").getValue();
		
		String famx03 = HBUtil.getValueFromTab("famx03", "HZ_MNTP_SJFA_famx", "famx00='"+selfamx00+"'");
		String fabd02 = HBUtil.getValueFromTab("fabd02", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
		List<List<Map<String, Object>>> b0111s = MNTPSJDWPageModel.returnFA(fabd00,null,selfamx00,true);
		List<Map<String, Object>> orgs = null;
		if(b0111s.size()==1){
			orgs = b0111s.get(0);
			if("1".equals(type)){
				new LogUtil().createLogNew("生成动议","生成动议","上会信息集",selfamx00,fabd02+"("+famx03+")", new ArrayList());
				dy(orgs,fabd00,selfamx00);
			}else if("2".equals(type)){
				new LogUtil().createLogNew("生成上会","生成上会","上会信息集",selfamx00,fabd02+"("+famx03+")", new ArrayList());
				sh(orgs);
			}
		}

		page.setMainMessage("生成会议成功！");
		
	}
	
	/**
	 * 动议
	 * @param orgs
	 * @throws Exception 
	 */
	private static void dy(List<Map<String, Object>> orgs,String fabd00,String selfamx00) throws Exception {
		/*MeetingTheme mt = new MeetingTheme();
		Publish p = new Publish();
		HBSession sess = HBUtil.getHBSession();
		String sql="";
	
		
		String userid = SysManagerUtils.getUserId();
		mt.setMeetingname("调配转上会动议");
		String meetingid=UUID.randomUUID().toString().replaceAll("-", "");
		mt.setMeetingid(meetingid);
		mt.setMeetingtype("2");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String Createdon =sdf.format(new Date());
		mt.setTime(Createdon);
		mt.setUserid(userid);
		
		String publishid=UUID.randomUUID().toString().replaceAll("-", "");
		String titleid=UUID.randomUUID().toString().replaceAll("-", "");
		String titleid1=UUID.randomUUID().toString().replaceAll("-", "");
		p.setMeetingid(meetingid);
		p.setAgendaname("提交市委讨论名单（干部一处）");
		p.setAgendatype("3");
		p.setPublishid(publishid);
		p.setUserid(userid);
		sess.save(p); 
		sess.save(mt);
		sess.flush();
		sess.createSQLQuery("insert into publishuser(publishid,userid,islook,ischange) values(?,?,?,?)")
		.setString(0, publishid).setString(1, userid).setString(2, "1").setString(3, "1")
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, titleid).setString(1, "拟提任市管正职").setString(2, "1").setString(3, "-1").setString(4, "1").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, titleid1).setString(1, "拟提任市管副职").setString(2, "1").setString(3, "-1").setString(4, "2").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, "sys_guid()").setString(1, "拟进一步使用").setString(2, "1").setString(3, "-1").setString(4, "3").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, "sys_guid()").setString(1, "拟新任党委（党组）成员").setString(2, "1").setString(3, "-1").setString(4, "4").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, "sys_guid()").setString(1, "区县市").setString(2, "2").setString(3, titleid).setString(4, "5").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, "sys_guid()").setString(1, "国企高校").setString(2, "2").setString(3, titleid).setString(4, "6").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, "sys_guid()").setString(1, "市直单位").setString(2, "2").setString(3, titleid).setString(4, "7").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();*/
		String sql="";
		String meetingid=UUID.randomUUID().toString().replace("-", "");
		String userid=SysManagerUtils.getUserId();
		sql="insert into meetingtheme(meetingid,time,meetingname,meetingtype,userid) values "
				+ "('"+meetingid+"',to_char(sysdate,'yyyyMMdd'),to_char(sysdate,'yyyyMMddHHmmss')||'书记会','2','"+userid+"') ";
		HBUtil.executeUpdate(sql);
		String publishid=UUID.randomUUID().toString().replace("-", "");
		sql="insert into publish (publishid,meetingid,agendatype,agendaname,sort,userid) values ('"+publishid+"','"+meetingid+"','3','提交市委讨论名单（干部一处）',1,'"+userid+"') ";
		HBUtil.executeUpdate(sql);
		
		String titleid1=UUID.randomUUID().toString().replace("-", "");
		sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid1+"','拟提任市管正职','1','-1','"+userid+"',1,'"+meetingid+"','"+publishid+"') ";
		HBUtil.executeUpdate(sql);
		
		String titleid11=UUID.randomUUID().toString().replace("-", "");
		sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid11+"','拟提任市管副职','1','-1','"+userid+"',2,'"+meetingid+"','"+publishid+"') ";
		HBUtil.executeUpdate(sql);
		
		String titleid111=UUID.randomUUID().toString().replace("-", "");
		sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid111+"','区、县（市）换届干部任免','1','"+titleid1+"','"+userid+"',1,'"+meetingid+"','"+publishid+"') ";
		HBUtil.executeUpdate(sql);
		
		String titleid112=UUID.randomUUID().toString().replace("-", "");
		sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid112+"','区、县（市）换届干部任免','1','"+titleid11+"','"+userid+"',1,'"+meetingid+"','"+publishid+"') ";
		HBUtil.executeUpdate(sql);
		
		CommQuery cqbs=new CommQuery();
						int sortid=0;
						String sqlg="";
						for(int k=0;k<orgs.size();k++){
							Map<String, Object> cfgMap = orgs.get(k);
								String b0111 = cfgMap.get("b0111")==null?"":cfgMap.get("b0111").toString();
								String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
								String b0101 = cfgMap.get("b0101")==null?"":cfgMap.get("b0101").toString();
								String famx00 = cfgMap.get("famx00")==null?"":cfgMap.get("famx00").toString();
								List<HashMap<String, Object>> dataMap = getTPXX(b0111,famx00);
								String titleid2=UUID.randomUUID().toString().replace("-", "");
								
								
								
								sql="SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY fxyp02 ORDER BY b0111_ry,fxyp01 ) RNO,fxyp02,fxyp01,b0111_ry"
								         + " FROM v_mntp_sj_gw_ry where famx00 ='"+selfamx00+"') "
								 			+"WHERE RNO = 1 ORDER BY  b0111_ry ,fxyp01";
								List<HashMap<String, Object>> list_t4=cqbs.getListBySQL(sql);
									
									
									for(HashMap<String, Object> tpdata : dataMap){
										if(tpdata.get("a0000")!=null&&!"".equals(tpdata.get("a0000"))) {
											String nirentype="1";
											String tp0111="";
											if(tpdata.get("gwa0200").equals(tpdata.get("rya0200"))) {
												nirentype="";
												tp0111="拟留任";
											}else {
												tp0111=tpdata.get("a0215a").toString();
											}
											
											if(!(tpdata.get("rya0201e").toString().equals(tpdata.get("a02a0201e").toString()))&&(tpdata.get("rya0201e").toString().equals("1"))) {
											sql="insert into hz_sh_a01(sh000,a0000,publishid,sh001,titleid,tp0111,a0101,a0104,a0107 ,a0141,zgxl,zgxw,a0192a,tp0121,tp0116,tp0117)"
												+ " values (sys_guid(),'"+tpdata.get("a0000")+"','"+publishid+"','"+(k+1)+"','"+titleid111+"','"+tp0111+"'"
												+ "	  ,'"+tpdata.get("xingming")+"','"+tpdata.get("a0104")+"','"+tpdata.get("a0107")+"','"+tpdata.get("a0141")+"'"
												+ "	  ,'"+tpdata.get("zgxl")+"','"+tpdata.get("zgxw")+"','"+tpdata.get("a0192a")+"','"+nirentype+"','1','1')";
												HBUtil.executeUpdate(sql);
											}else if(!(tpdata.get("rya0201e").toString().equals(tpdata.get("a02a0201e").toString()))&&(tpdata.get("rya0201e").toString().equals("3"))) {
												sql="insert into hz_sh_a01(sh000,a0000,publishid,sh001,titleid,tp0111,a0101,a0104,a0107 ,a0141,zgxl,zgxw,a0192a,tp0121,tp0116,tp0117)"
														+ " values (sys_guid(),'"+tpdata.get("a0000")+"','"+publishid+"','"+(k+1)+"','"+titleid112+"','"+tp0111+"'"
														+ "	  ,'"+tpdata.get("xingming")+"','"+tpdata.get("a0104")+"','"+tpdata.get("a0107")+"','"+tpdata.get("a0141")+"'"
														+ "	  ,'"+tpdata.get("zgxl")+"','"+tpdata.get("zgxw")+"','"+tpdata.get("a0192a")+"','"+nirentype+"','1','1')";
														HBUtil.executeUpdate(sql);
											}
										}
									}
									
			
		}
		/*for(int i=0;i<orgs.size();i++){
			Map<String, Object> cfgMap = orgs.get(i);
			String b0111 = cfgMap.get("b0111")==null?"":cfgMap.get("b0111").toString();
			String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
			String b0101 = cfgMap.get("b0101")==null?"":cfgMap.get("b0101").toString();
			String famx00 = cfgMap.get("famx00")==null?"":cfgMap.get("famx00").toString();
			List<HashMap<String, Object>> dataMap = getTPXX(b0111,famx00);
			//调配数据
			for(HashMap<String, Object> tpdata : dataMap){
				System.out.println(tpdata.toString());
			}
		}*/
		
		
	}
	
	/**
	 * 动议不用
	 * @param orgs
	 * @throws Exception 
	 */
	private static void dyy(List<Map<String, Object>> orgs,String fabd00,String selfamx00) throws Exception {
		/*MeetingTheme mt = new MeetingTheme();
		Publish p = new Publish();
		HBSession sess = HBUtil.getHBSession();
		String sql="";
	
		
		String userid = SysManagerUtils.getUserId();
		mt.setMeetingname("调配转上会动议");
		String meetingid=UUID.randomUUID().toString().replaceAll("-", "");
		mt.setMeetingid(meetingid);
		mt.setMeetingtype("2");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String Createdon =sdf.format(new Date());
		mt.setTime(Createdon);
		mt.setUserid(userid);
		
		String publishid=UUID.randomUUID().toString().replaceAll("-", "");
		String titleid=UUID.randomUUID().toString().replaceAll("-", "");
		String titleid1=UUID.randomUUID().toString().replaceAll("-", "");
		p.setMeetingid(meetingid);
		p.setAgendaname("提交市委讨论名单（干部一处）");
		p.setAgendatype("3");
		p.setPublishid(publishid);
		p.setUserid(userid);
		sess.save(p); 
		sess.save(mt);
		sess.flush();
		sess.createSQLQuery("insert into publishuser(publishid,userid,islook,ischange) values(?,?,?,?)")
		.setString(0, publishid).setString(1, userid).setString(2, "1").setString(3, "1")
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, titleid).setString(1, "拟提任市管正职").setString(2, "1").setString(3, "-1").setString(4, "1").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, titleid1).setString(1, "拟提任市管副职").setString(2, "1").setString(3, "-1").setString(4, "2").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, "sys_guid()").setString(1, "拟进一步使用").setString(2, "1").setString(3, "-1").setString(4, "3").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, "sys_guid()").setString(1, "拟新任党委（党组）成员").setString(2, "1").setString(3, "-1").setString(4, "4").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, "sys_guid()").setString(1, "区县市").setString(2, "2").setString(3, titleid).setString(4, "5").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, "sys_guid()").setString(1, "国企高校").setString(2, "2").setString(3, titleid).setString(4, "6").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();
		sess.createSQLQuery("insert into hz_sh_title(titleid,title01,title02,title04,sortid,meetingid,publishid) values(?,?,?,?,?,?,?)")
		.setString(0, "sys_guid()").setString(1, "市直单位").setString(2, "2").setString(3, titleid).setString(4, "7").setString(5, meetingid).setString(6, publishid)
		.executeUpdate();*/
		String sql="";
		String meetingid=UUID.randomUUID().toString().replace("-", "");
		String userid=SysManagerUtils.getUserId();
		sql="insert into meetingtheme(meetingid,time,meetingname,meetingtype,userid) values "
				+ "('"+meetingid+"',to_char(sysdate,'yyyyMMdd'),to_char(sysdate,'yyyyMMddHHmmss')||'书记会','2','"+userid+"') ";
		HBUtil.executeUpdate(sql);
		String publishid=UUID.randomUUID().toString().replace("-", "");
		sql="insert into publish (publishid,meetingid,agendatype,agendaname,sort,userid) values ('"+publishid+"','"+meetingid+"','3','提交市委讨论名单（干部一处）',1,'"+userid+"') ";
		HBUtil.executeUpdate(sql);
		
		String titleid1=UUID.randomUUID().toString().replace("-", "");
		sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid1+"','拟提任市管正职','1','-1','"+userid+"',1,'"+meetingid+"','"+publishid+"') ";
		HBUtil.executeUpdate(sql);
		
		String titleid11=UUID.randomUUID().toString().replace("-", "");
		sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid11+"','拟提任市管副职','1','-1','"+userid+"',2,'"+meetingid+"','"+publishid+"') ";
		HBUtil.executeUpdate(sql);
		
		String titleid111=UUID.randomUUID().toString().replace("-", "");
		sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid111+"','区、县（市）换届干部任免','1','"+titleid1+"','"+userid+"',1,'"+meetingid+"','"+publishid+"') ";
		HBUtil.executeUpdate(sql);
		
		String titleid112=UUID.randomUUID().toString().replace("-", "");
		sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid112+"','区、县（市）换届干部任免','1','"+titleid11+"','"+userid+"',1,'"+meetingid+"','"+publishid+"') ";
		HBUtil.executeUpdate(sql);
		
		CommQuery cqbs=new CommQuery();
		sql="SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY fxyp02 ORDER BY b0111_ry,fxyp01 ) RNO,fxyp02,fxyp01,b0111_ry"
		         + " FROM v_mntp_sj_gw_ry where famx00 ='"+selfamx00+"') "
		 			+"WHERE RNO = 1 ORDER BY  b0111_ry ,fxyp01";
		List<HashMap<String, Object>> list_t3=cqbs.getListBySQL(sql);
		String titleid3="";
		String titleid4="";	
		for(int j=0;j<list_t3.size();j++) {
			
			titleid3=UUID.randomUUID().toString().replace("-", "");
			sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid3+"','"+list_t3.get(j).get("fxyp02")+"','3','"+titleid111+"','"+userid+"',"+j+",'"+meetingid+"','"+publishid+"') ";
			HBUtil.executeUpdate(sql);
			
			titleid4=UUID.randomUUID().toString().replace("-", "");
			sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid4+"','"+list_t3.get(j).get("fxyp02")+"','3','"+titleid112+"','"+userid+"',"+j+",'"+meetingid+"','"+publishid+"') ";
			HBUtil.executeUpdate(sql);
			
		}
						int sortid=0;
						String sqlg="";
						for(int k=0;k<orgs.size();k++){
							Map<String, Object> cfgMap = orgs.get(k);
								String b0111 = cfgMap.get("b0111")==null?"":cfgMap.get("b0111").toString();
								String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
								String b0101 = cfgMap.get("b0101")==null?"":cfgMap.get("b0101").toString();
								String famx00 = cfgMap.get("famx00")==null?"":cfgMap.get("famx00").toString();
								List<HashMap<String, Object>> dataMap = getTPXX(b0111,famx00);
								String titleid2=UUID.randomUUID().toString().replace("-", "");
								
								
								
								sql="SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY fxyp02 ORDER BY b0111_ry,fxyp01 ) RNO,fxyp02,fxyp01,b0111_ry"
								         + " FROM v_mntp_sj_gw_ry where famx00 ='"+selfamx00+"') "
								 			+"WHERE RNO = 1 ORDER BY  b0111_ry ,fxyp01";
								List<HashMap<String, Object>> list_t4=cqbs.getListBySQL(sql);
								for(int j=0;j<list_t4.size();j++) {
									
									
									for(HashMap<String, Object> tpdata : dataMap){
										if(tpdata.get("a0000")!=null&&!"".equals(tpdata.get("a0000"))) {
											String nirentype="1";
											String tp0111="";
											if(tpdata.get("gwa0200").equals(tpdata.get("rya0200"))) {
												nirentype="";
												tp0111="拟留任";
											}else {
												tp0111=tpdata.get("a0215a").toString();
											}
											String gg=list_t4.get(j).get("fxyp02").toString();
											
											if((list_t4.get(j).get("fxyp02").equals(tpdata.get("a0215a").toString()))&&(tpdata.get("rya0201e").toString().equals("1"))) {
												sqlg="select titleid from hz_sh_title where title01='"+gg+"' and title04='"+titleid111+"'";
												List<HashMap<String, Object>> list_t5=cqbs.getListBySQL(sqlg);
												String summ=list_t5.get(0).get("titleid") == null ? "" : list_t5.get(0).get("titleid").toString();	
												
											sql="insert into hz_sh_a01(sh000,a0000,publishid,sh001,titleid,tp0111,a0101,a0104,a0107 ,a0141,zgxl,zgxw,a0192a,tp0121,tp0116,tp0117)"
												+ " values (sys_guid(),'"+tpdata.get("a0000")+"','"+publishid+"','"+(k+1)+"','"+summ+"','"+tp0111+"'"
												+ "	  ,'"+tpdata.get("xingming")+"','"+tpdata.get("a0104")+"','"+tpdata.get("a0107")+"','"+tpdata.get("a0141")+"'"
												+ "	  ,'"+tpdata.get("zgxl")+"','"+tpdata.get("zgxw")+"','"+tpdata.get("a0192a")+"','"+nirentype+"','1','1')";
												HBUtil.executeUpdate(sql);
											}else if((list_t4.get(j).get("fxyp02").equals(tpdata.get("a0215a").toString()))&&(tpdata.get("rya0201e").toString().equals("3"))) {
												sqlg="select titleid from hz_sh_title where title01='"+gg+"' and title04='"+titleid112+"'";
												List<HashMap<String, Object>> list_t5=cqbs.getListBySQL(sqlg);
												String summ=list_t5.get(0).get("titleid") == null ? "" : list_t5.get(0).get("titleid").toString();
												sql="insert into hz_sh_a01(sh000,a0000,publishid,sh001,titleid,tp0111,a0101,a0104,a0107 ,a0141,zgxl,zgxw,a0192a,tp0121,tp0116,tp0117)"
														+ " values (sys_guid(),'"+tpdata.get("a0000")+"','"+publishid+"','"+(k+1)+"','"+summ+"','"+tp0111+"'"
														+ "	  ,'"+tpdata.get("xingming")+"','"+tpdata.get("a0104")+"','"+tpdata.get("a0107")+"','"+tpdata.get("a0141")+"'"
														+ "	  ,'"+tpdata.get("zgxl")+"','"+tpdata.get("zgxw")+"','"+tpdata.get("a0192a")+"','"+nirentype+"','1','1')";
														HBUtil.executeUpdate(sql);
											}
										}
									}
									
						}
			
		}
		/*for(int i=0;i<orgs.size();i++){
			Map<String, Object> cfgMap = orgs.get(i);
			String b0111 = cfgMap.get("b0111")==null?"":cfgMap.get("b0111").toString();
			String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
			String b0101 = cfgMap.get("b0101")==null?"":cfgMap.get("b0101").toString();
			String famx00 = cfgMap.get("famx00")==null?"":cfgMap.get("famx00").toString();
			List<HashMap<String, Object>> dataMap = getTPXX(b0111,famx00);
			//调配数据
			for(HashMap<String, Object> tpdata : dataMap){
				System.out.println(tpdata.toString());
			}
		}*/
		
		
	}

	/**
	 * 上会
	 * @param orgs
	 * @throws AppException 
	 */
	private static void sh(List<Map<String, Object>> orgs) throws AppException {
		String sql="";
		String meetingid=UUID.randomUUID().toString().replace("-", "");
		String userid=SysManagerUtils.getUserId();
		sql="insert into meetingtheme(meetingid,time,meetingname,meetingtype,userid) values "
				+ "('"+meetingid+"',to_char(sysdate,'yyyyMMdd'),to_char(sysdate,'yyyyMMddHHmmss')||'书记会','2','"+userid+"') ";
		HBUtil.executeUpdate(sql);
		String publishid=UUID.randomUUID().toString().replace("-", "");
		sql="insert into publish (publishid,meetingid,agendatype,agendaname,sort,userid) values ('"+publishid+"','"+meetingid+"','1','提交市委讨论名单（干部一处）',1,'"+userid+"') ";
		HBUtil.executeUpdate(sql);
		
		String titleid1=UUID.randomUUID().toString().replace("-", "");
		sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid1+"','区、县（市）换届干部任免','1','-1','"+userid+"',1,'"+meetingid+"','"+publishid+"') ";
		HBUtil.executeUpdate(sql);
		String b0111_z="";
		for(int i=0;i<orgs.size();i++){
			Map<String, Object> cfgMap = orgs.get(i);
			if(cfgMap.get("b0111")==null||"".equals(cfgMap.get("b0111"))) {
				
			}else {
				b0111_z=b0111_z+"'"+cfgMap.get("b0111")+"',";
			}
		}
		HashMap map_t2=new HashMap();
		if(!"".equals(b0111_z)) {
			b0111_z=b0111_z.substring(0, b0111_z.length()-1);
			sql="select b.b0111, b.b0101 from b01 b where b.b0111 in (select substr(a.b0111, 1, 15) from b01 a where a.b0111 in ("+b0111_z+")) order by b.b0269 ";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list_t2=cqbs.getListBySQL(sql);
			if(list_t2!=null&&list_t2.size()>0) {
				for(int i=0;i<list_t2.size();i++) {
					String titleid2=UUID.randomUUID().toString().replace("-", "");
					sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid2+"','"+list_t2.get(i).get("b0101")+"','2','"+titleid1+"','"+userid+"',"+i+",'"+meetingid+"','"+publishid+"') ";
					HBUtil.executeUpdate(sql);
					sql="select substr(a.b0111,1,15) qxb0111,b0111,decode(b0131,'1001','党委班子','1004','政府班子','1003','人大班子','1005','政协班子','其他（法检、平台）') b0101 from b01 a where b0111 in ("+b0111_z+") and b0111 like '"+list_t2.get(i).get("b0111")+"%'"
							+ " order by decode(b0131,'1001',1,'1004',2,'1003',3,'1005',4,5),b0269 ";
					List<HashMap<String, Object>> list_t3=cqbs.getListBySQL(sql);
					boolean flag=false;
					String titleid3_pt="";
					for(int j=0;j<list_t3.size();j++) {
						String titleid3="";
						if("党委班子".equals(list_t3.get(j).get("b0101"))||"政府班子".equals(list_t3.get(j).get("b0101"))
								||"人大班子".equals(list_t3.get(j).get("b0101"))||"政协班子".equals(list_t3.get(j).get("b0101"))) {
							titleid3=UUID.randomUUID().toString().replace("-", "");
							sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid3+"','"+list_t3.get(j).get("b0101")+"','3','"+titleid2+"','"+userid+"',"+j+",'"+meetingid+"','"+publishid+"') ";
							HBUtil.executeUpdate(sql);
						}else {
							if(flag) {
								titleid3=titleid3_pt;
							}else {
								titleid3=UUID.randomUUID().toString().replace("-", "");
								sql="insert into hz_sh_title (titleid,title01,title02,title04,userid,sortid,meetingid,publishid) values ('"+titleid3+"','"+list_t3.get(j).get("b0101")+"','3','"+titleid2+"','"+userid+"',"+j+",'"+meetingid+"','"+publishid+"') ";
								HBUtil.executeUpdate(sql);
								titleid3_pt=titleid3;
								flag=true;
							}
						}
						int sortid=0;
						for(int k=0;k<orgs.size();k++){
							Map<String, Object> cfgMap = orgs.get(k);
							if(list_t3.get(j).get("b0111").equals(cfgMap.get("b0111"))) {
								String b0111 = cfgMap.get("b0111")==null?"":cfgMap.get("b0111").toString();
								String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
								String b0101 = cfgMap.get("b0101")==null?"":cfgMap.get("b0101").toString();
								String famx00 = cfgMap.get("famx00")==null?"":cfgMap.get("famx00").toString();
								List<HashMap<String, Object>> dataMap = getTPXX(b0111,famx00);
								if("党委班子".equals(list_t3.get(j).get("b0101"))||"政府班子".equals(list_t3.get(j).get("b0101"))
										||"人大班子".equals(list_t3.get(j).get("b0101"))||"政协班子".equals(list_t3.get(j).get("b0101"))) {
									int m=0;
									for(HashMap<String, Object> tpdata : dataMap){
										if(tpdata.get("a0000")!=null&&!"".equals(tpdata.get("a0000"))) {
											String nirentype="1";
											String tp0111="";
											if(tpdata.get("gwa0200").equals(tpdata.get("rya0200"))) {
												nirentype="";
												tp0111="拟留任";
											}else {
												tp0111=tpdata.get("a0215a").toString();
											}
											sql="insert into hz_sh_a01(sh000,a0000,publishid,sh001,titleid,tp0111,a0101,a0104,a0107 ,a0141,zgxl,zgxw,a0192a,tp0121,tp0116,tp0117)"
												+ " values (sys_guid(),'"+tpdata.get("a0000")+"','"+publishid+"','"+(m+1)+"','"+titleid3+"','"+tp0111+"'"
												+ "	  ,'"+tpdata.get("xingming")+"','"+tpdata.get("a0104")+"','"+tpdata.get("a0107")+"','"+tpdata.get("a0141")+"'"
												+ "	  ,'"+(tpdata.get("zgxl")==null?"":tpdata.get("zgxl"))+"','"+(tpdata.get("zgxw")==null?"":tpdata.get("zgxw"))+"','"+tpdata.get("a0192a")+"','"+nirentype+"','1','1')";
											HBUtil.executeUpdate(sql);
											m=m+1;
										}
									}
								}else {
									for(HashMap<String, Object> tpdata : dataMap){
										if(tpdata.get("a0000")!=null&&!"".equals(tpdata.get("a0000"))) {
											String nirentype="1";
											String tp0111="";
											if(tpdata.get("gwa0200").equals(tpdata.get("rya0200"))) {
												nirentype="";
												tp0111="拟留任";
											}else {
												tp0111=tpdata.get("a0215a").toString();
											}
											sortid=sortid+1;
											sql="insert into hz_sh_a01(sh000,a0000,publishid,sh001,titleid,tp0111,a0101,a0104,a0107 ,a0141,zgxl,zgxw,a0192a,tp0121,tp0116,tp0117)"
												+ " values (sys_guid(),'"+tpdata.get("a0000")+"','"+publishid+"','"+sortid+"','"+titleid3+"','"+tp0111+"'"
												+ "	  ,'"+tpdata.get("xingming")+"','"+tpdata.get("a0104")+"','"+tpdata.get("a0107")+"','"+tpdata.get("a0141")+"'"
												+ "	  ,'"+(tpdata.get("zgxl")==null?"":tpdata.get("zgxl"))+"','"+(tpdata.get("zgxw")==null?"":tpdata.get("zgxw"))+"','"+tpdata.get("a0192a")+"','"+nirentype+"','1','1')";
											HBUtil.executeUpdate(sql);
										}
									}
								}
								break;
							}
						}
					}
				}
				String table_id=publishid.substring(0, 15);
				sql="create table  hz_tptosh_"+table_id+"   as"
					+ "	select a0000,sh000,titleid,sortid,decode(tp0111,'拟留任，拟留任','拟留任',tp0111) tp0111,     "
					+ " row_number() over(partition by a.A0000 order by a.sortid ) row_num                                                                                    "
					+ "  from (select a0000,a.sh000,a.titleid,b.sortid,"
					+ "		 listagg (a.tp0111, '，') WITHIN GROUP (ORDER BY b.sortid)  over(PARTITION BY a.a0000) tp0111"
					+ "		  from hz_sh_a01 a,hz_sh_title b where a.titleid=b.titleid and a.publishid=b.publishid and a.publishid='"+publishid+"') a";
				HBUtil.executeUpdate(sql);
				sql="delete from hz_sh_a01 where publishid='"+publishid+"' and sh000 in (select sh000 from hz_tptosh_"+table_id+" where row_num>1)";
				HBUtil.executeUpdate(sql);
				sql="update hz_sh_a01 a set tp0111=(select b.tp0111 from hz_tptosh_"+table_id+" b where a.sh000=b.sh000) where publishid='"+publishid+"' and sh000 in (select sh000 from hz_tptosh_"+table_id+" where row_num=1)";
				HBUtil.executeUpdate(sql);
				sql="drop table hz_tptosh_"+table_id+" ";
				HBUtil.executeUpdate(sql);
			}
		}
		
		
	}
	
	
	
	public static List<HashMap<String, Object>> getTPXX(String b0111, String famx00) throws AppException {
		
		String liurenTable = ",(select tpdesc,tpdesc2,infoid tpyjid,a0200 from HZ_MNTP_SJFA_INFO where famx00f='"+famx00+"') info ";
		String liurenWhere = " and info.a0200(+)=t.fxyp00 ";
		
		String sql = "select b0111,b0131,t.zwqc00,famx00,fxyp02 a0215a,fxyp00,fxyp00_ry,info.tpyjid,info.tpdesc,info.tpdesc2,"
				+ " substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age,"
				+ " (select b0104 from b01 where b01.b0111 = a02.a0201b and b01.b0111!='"+b0111+"') laiyuan, "
				+ " (select b0104 from b01 where b01.b0111 = '"+b0111+"') jgjc, "
				+ " t.a0201e rya0201e,a02.a0201e a02a0201e,t.gwa0200,t.rya0200,t.gwmc,a02.a0201b,"
				+ "count(1) over(partition by fxyp00s)  gwcount,"
				+ "rank() over(partition by fxyp00s order by b0111_ry,  sortnum)  rk,"
				+ " GET_tpbXingming(a01.a0101, a01.a0104, a01.a0117, a01.a0141) a0101,a0101 xingming,"
 //+" decode(a01.a0107,null,null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107,"
 +" t.a0000,a01.a0192a,a01.zgxl,a01.qrzxl,a01.a0104,a01.a0141,a01.a0107,a01.zgxw,"
 + "decode(a01.a0288,null,null,substr(a01.a0288, 0, 4) || '.' || substr(a01.a0288, 5, 2)) a0288 "
 + " from v_mntp_sj_gw_ry t,a01,a02 " + liurenTable
 +" where t.a0000 = a01.a0000(+) and t.rya0200=a02.a0200(+) "+liurenWhere
 + " and t.famx00='"+famx00+"' and t.b0111='"+b0111+"'  "
 +" order by fxyp01,b0111_ry,sortnum";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		return nmzwList;
	}
	
}
