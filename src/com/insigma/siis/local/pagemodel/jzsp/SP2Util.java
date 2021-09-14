package com.insigma.siis.local.pagemodel.jzsp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.Sp01;
import com.insigma.siis.local.business.entity.Sp01_Pc;
import com.insigma.siis.local.business.entity.Sp_Bus;
import com.insigma.siis.local.business.entity.Sp_Bus_Log;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class SP2Util {
	/**
	 * 0环节 : 1当前审批结果 ：2审批状态 : 3描述 ：4审批机构是否可为空  : 5备注是否可为空 : 
	 */
	public static String[][] nextJGLDconfig = new String[4][];
	/**
	 * 0环节 : 1当前审批结果 ：2审批状态 : 3描述 ：4审批机构是否可为空  : 5备注是否可为空 : 
	 */
	public static String[][] prevJGLDconfig = new String[4][];
	static{
		//0环节 : 1当前审批结果 ：2审批状态 : 3描述 ：4审批机构是否可为空  : 5备注是否可为空 : 6业务意见是否可为空 ：7业务意见字段
		nextJGLDconfig[1] = new String[]{"2","2","1","上报","0","1","1",""};//登记结束
		nextJGLDconfig[2] = new String[]{"3","6","1","送审","0","1","0","spp04"};//一级审批结束
		nextJGLDconfig[3] = new String[]{"4","3","2","审批通过(完结)","1","1","0","spp05"};//二级审批结束
		
		//0环节 : 1当前审批结果 ：2审批状态 : 3描述 ：4审批机构是否可为空  : 5备注是否可为空 : 6业务意见是否可为空
		prevJGLDconfig[2] = new String[]{"1","4","0","退回","1","0","1","spp04"};//一级审批结束
		prevJGLDconfig[3] = new String[]{"4","4","3","审批未通过(完结)","1","1","0","spp05"};//二级审批结束
	}
	/**
	 * 通过01
	 * @param sp0100
	 * @param sess
	 * @param map 
	 * @throws NumberFormatException
	 * @throws AppException
	 */
	public static void apply02(String sp0100, HBSession sess, Map<String, String> map,String type) throws NumberFormatException, AppException {
		String[][] JGLDconfig;
		if("1".equals(type)){//通过
			JGLDconfig = nextJGLDconfig;
		}else{//不通过
			JGLDconfig = prevJGLDconfig;
		}
		Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, sp0100);
		int hj = Integer.valueOf(sb.getSpb06());
		if(hj>=JGLDconfig.length){
			throw new AppException("审批已完结！");
		}
		if(1!=hj){
			String curUser = SysManagerUtils.getUserId();
			String curGroup = SysManagerUtils.getUserGroupid();
			if(map.get("user")!=null){//审批用户为当前用户
				if(!curUser.equals(map.get("user"))){
					throw new AppException("当前用户无审批权限！");
				}
				
			}
			if(map.get("group")!=null){
				if(!curGroup.equals(map.get("group"))){
					throw new AppException("当前用户无审批权限！");
				}
			}
		}
		String conf[] = JGLDconfig[hj];
		String nexthj = conf[0];//下一个环节
		String spjg = conf[1];//当前审批结果
		String spzt = conf[2];//审批状态
		String desc = conf[3];//描述
		
		
		Sp01 sp01 = (Sp01)sess.get(Sp01.class, sp0100);
		sp01.setSp0114(spzt);//审批中
		sess.update(sp01);
		
		String spb03 = sb.getSpb03();//审批机构
		String spb04 = sb.getSpb04();//审批人
		if((spb04==null||"".equals(spb04))&&(spb03==null||"".equals(spb03))&&"0".equals(conf[4])){
			throw new AppException("请选择送审单位或处理人！");
		}
		if(hj!=1){
			String temp = map.get(conf[7]);
			if("0".equals(conf[6])&&(temp==null||"".equals(temp))){
				throw new AppException("所在部门意见不能为空！");
			}
		}
		String spbl08 = map.get("spbl08");
		if((spbl08==null||"".equals(spbl08))&&"0".equals(conf[5])){
			throw new AppException("备注不能为空！");
		}
		sb.setSpb02(spzt);//审批中
		sb.setSpb06(nexthj);//第二环节
		sess.save(sb);
		//增加流程日志
		Sp_Bus_Log sbl = new Sp_Bus_Log();
		sbl.setSpbl00((long)Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004")));
		sbl.setSpb00(sp0100);//流程主键
		sbl.setSpbl01(SysManagerUtils.getUserId());//操作人id
		sbl.setSpbl02(SysManagerUtils.getUserName());//操作人姓名
		sbl.setSpbl03(SysManagerUtils.getUserGroupid());//操作机构
		sbl.setSpbl04(spjg);//操作类型1登记 2送审 3审批通过 4审批不通过 5完结
		sbl.setSpbl05(new Date());//操作时间
		sbl.setSpbl06(desc);//描述
		sbl.setSpbl07(hj+"");//当前环节
		sbl.setSpbl08(spbl08);
		sess.save(sbl);
		if("0".equals(type)&&!nexthj.equals("4")){
			//审批流程表退回上一级
			String sql = "select t.spbl01 from SP_BUS_LOG t where spb00='"+sp0100+"' and t.spbl04 in('2') order by t.spbl00 desc";
			List<String> list = sess.createSQLQuery(sql).list();
			if(list.size()==0){
				throw new AppException("无法退回！不存在上一个环节！");
			}
			String userid = list.get(0);
			sb.setSpb03(userid);//审批人
			sb.setSpb04(null);//审批机构
			sess.save(sb);
			
		}
		
		
		
		if(nexthj.equals("4")){//完结
			//删除审批信息， 将审批信息添加到历史记录。
			sess.createSQLQuery("insert into SP_BUS_his (select * from SP_BUS where spb00='"+sp0100+"')").executeUpdate();
			sess.delete(sb);
		}
		
		
		
		sess.flush();
	}
	
	

	/**
	 * 获取当前审批人 机构或用户 审批人
	 * @param id
	 * @return
	 */
	public static String[] getSPInfo(String id){
		String[] ret = new String[2];
		HBSession sess = HBUtil.getHBSession();
		Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, id);
		if(sb!=null){
			String sb03 = sb.getSpb03();
			if(sb03!=null&&!"".endsWith(sb03)){
				ret[0] = "user";
				ret[1] = sb03;
				return ret;
			}
			String sb04 = sb.getSpb04();
			if(sb04!=null&&!"".endsWith(sb04)){
				ret[0] = "group";
				ret[1] = sb04;
				return ret;
			}
		}
		return ret;
	}
	/**
	 * 获取当前审批人 机构或用户  提交人 处室流转
	 * @param id
	 * @return
	 */
	public static String[] getSPInfoPC1(String id){
		String[] ret = new String[2];
		HBSession sess = HBUtil.getHBSession();
		Sp01_Pc sb = (Sp01_Pc)sess.get(Sp01_Pc.class, id);
		if(sb!=null){
			String sb03 = sb.getSpp11();
			if(sb03!=null&&!"".endsWith(sb03)){
				ret[0] = "user";
				ret[1] = sb03;
				return ret;
			}
		}
		return null;
	}
	
	/**
	 * 获取当前审批人 机构或用户  批次
	 * @param id
	 * @return
	 */
	public static String[] getSPInfoPC2(String id){
		String[] ret = new String[2];
		HBSession sess = HBUtil.getHBSession();
		Sp01_Pc sb = (Sp01_Pc)sess.get(Sp01_Pc.class, id);
		if(sb!=null){
			String sb03 = sb.getSpp09();
			if(sb03!=null&&!"".endsWith(sb03)){
				ret[0] = "user";
				ret[1] = sb03;
				return ret;
			}
			String sb04 = sb.getSpp10();
			if(sb04!=null&&!"".endsWith(sb04)){
				ret[0] = "group";
				ret[1] = sb04;
				return ret;
			}
		}
		return null;
	}
	
}
