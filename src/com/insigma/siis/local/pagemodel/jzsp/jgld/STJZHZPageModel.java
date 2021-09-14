package com.insigma.siis.local.pagemodel.jzsp.jgld;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Sp01;
import com.insigma.siis.local.business.entity.Sp01_Pc;
import com.insigma.siis.local.business.entity.Sp_Bus;
import com.insigma.siis.local.business.entity.Sp_Bus_Log;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.jzsp.SP2Util;

public class STJZHZPageModel extends PageModel{
	
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String sp0100s = this.getPageElement("sp0100s").getValue();
		if(sp0100s==null||"".equals(sp0100s)){
			String spp00 = this.getPageElement("spp00").getValue();
			String sql="select * from SP01 t where spp00 ='"+spp00+"' "
					+ " order by t.sp0116 desc";
			this.pageQuery(sql, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		}
		sp0100s = sp0100s.substring(0,sp0100s.length()-1).replace(",", "','");
		String sql="select * from SP01 t where sp0100 in('"+sp0100s+"') "
				+ " order by t.sp0116 desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 批次信息修改保存
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("save.onclick")
	public int save(String ss) throws RadowException, AppException{
		String spp00 = this.getPageElement("spp00").getValue();
		Sp01_Pc spc = new Sp01_Pc();
		String spb04 = this.getPageElement("spb04").getValue();//送审单位 或领导
		String usertype = this.getPageElement("usertype").getValue();//送审单位 或领导
		String spp04 = this.getPageElement("spp04").getValue();//处室意见
		String spp05 = this.getPageElement("spp05").getValue();//部领导意见
		List<HashMap<String,String>> list = this.getPageElement("memberGrid").getStringValueList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String groupid = SysManagerUtils.getUserGroupid();
		Map<String, String> map = new HashMap<String, String>();
		HBSession sess = HBUtil.getHBSession();
		
		try {
			sess.getTransaction().begin();
			
			if(spp00==null||"".equals(spp00)){//新增
				//1新增批次记录
				spc.setSpp00(UUID.randomUUID().toString());
				spc.setSpp02("领导干部社团兼职审批表");//批次名称
				spc.setSpp04(spp04);
				spc.setSpp05(spp05);
				spc.setSpp08("0");//审批状态
				spc.setSpp03(new Date());//登记时间
				spc.setSpp11(SysManagerUtils.getUserId());//申请人
				if(spb04!=null&&!"".equals(spb04)){
					if("group".equals(usertype)){
						spc.setSpp10(spb04);//审批机构
					}else if("user".equals(usertype)){
						spc.setSpp09(spb04);//审批人
					}
				}
				sess.save(spc);
				spp00 = spc.getSpp00();
				//2记入个人日志 审批汇总
				
				for(HashMap<String,String> m : list){
					//增加流程日志
					Sp_Bus_Log sbl = new Sp_Bus_Log();
					sbl.setSpbl00((long)Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004")));
					sbl.setSpb00(m.get("sp0100"));//流程主键
					sbl.setSpbl01(SysManagerUtils.getUserId());//操作人id
					sbl.setSpbl02(SysManagerUtils.getUserName());//操作人姓名
					sbl.setSpbl03(groupid);//操作机构
					sbl.setSpbl04("1");//操作类型1登记 2送审 3审批通过 4审批不通过 5结束
					sbl.setSpbl05(new Date());//操作时间
					sbl.setSpbl06("审批汇总");//描述
					sbl.setSpbl07("2");//操作节点
					sbl.setSpbl08("处室汇总");
					sess.save(sbl);
					Sp01 sp01 = (Sp01)sess.get(Sp01.class, m.get("sp0100"));
					sp01.setSpp00(spp00);
					sess.save(sp01);
				}
				sess.flush();
			}else{//修改
				spc = (Sp01_Pc)sess.get(Sp01_Pc.class, spp00);
				if(spc==null){
					this.setMainMessage("汇总表信息不存在！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if("0".equals(spc.getSpp08())){//登记状态
					if(spb04!=null&&!"".equals(spb04)){
						if("group".equals(usertype)){
							spc.setSpp10(spb04);//审批机构
						}else if("user".equals(usertype)){
							spc.setSpp09(spb04);//审批人
						}
						
					}
					spc.setSpp04(spp04);
					spc.setSpp05(spp05);
				}else{
					spc.setSpp04(spp04);
					spc.setSpp05(spp05);
				}
				sess.update(spc);
				sess.flush();
			}
			
			
			
			//送审
			if("ss2".equals(ss)){
				if("0".equals(spc.getSpp08())){//登记状态
					String[] spInfo = SP2Util.getSPInfoPC1(spc.getSpp00());
					if(spInfo!=null){
						map.put(spInfo[0], spInfo[1]);
					}
					spc.setSpp08("1");
					map.put("spbl08", spp04);
					map.put("spp04", spp04);
					if(spb04==null||"".equals(spb04)){
						throw new AppException("请选择送审单位或处理人！");
					}
					if(spp04==null||"".equals(spp04)){
						throw new AppException("请填写处室意见！");
					}
					spc.setSpp06(sdf.format(new Date()));
					sess.update(spc);
					
					//记入个人日志 审批汇总
					for(HashMap<String,String> m : list){
						//增加流程日志
						SP2Util.apply02(m.get("sp0100"), sess, map, "1");
					}
					sess.flush();
				}else if("1".equals(spc.getSpp08())){//审批中  审批结束
					String[] spInfo = SP2Util.getSPInfoPC2(spc.getSpp00());
					if(spInfo!=null){
						map.put(spInfo[0], spInfo[1]);
					}
					spc.setSpp08("2");//通过
					map.put("spbl08", spp05);
					map.put("spp05", spp05);
					
					if(spp05==null||"".equals(spp05)){
						throw new AppException("请填写部领导意见！");
					}
					spc.setSpp07(sdf.format(new Date()));
					//记入个人日志 审批汇总
					for(HashMap<String,String> m : list){
						//增加流程日志
						SP2Util.apply02(m.get("sp0100"), sess, map, "1");
					}
					sess.flush();
				}
			}else if("ss3".equals(ss)){//未通过
				spc.setSpp08("3");//通过
				map.put("spbl08", spp05);
				map.put("spp05", spp05);
				
				if(spp05==null||"".equals(spp05)){
					throw new AppException("请填写部领导意见！");
				}
				spc.setSpp07(sdf.format(new Date()));
				//记入个人日志 审批汇总
				for(HashMap<String,String> m : list){
					//增加流程日志
					SP2Util.apply02(m.get("sp0100"), sess, map, "0");
				}
				sess.flush();
			}
			sess.getTransaction().commit();
			this.getExecuteSG().addExecuteCode("saveCallBack('保存成功','1');");
		} catch (AppException e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			sess.getTransaction().rollback();
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败");
			sess.getTransaction().rollback();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String spp00 = this.getPageElement("spp00").getValue();
		HBSession sess = HBUtil.getHBSession();
		if(spp00==null||"".equals(spp00)){
			this.getExecuteSG().addExecuteCode("setDisabled1();");
		}else{
			Sp01_Pc spc = (Sp01_Pc)sess.get(Sp01_Pc.class, spp00);
			if(spc==null){
				this.setMainMessage("查询失败");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String spp09 = spc.getSpp09();
			String spp10 = spc.getSpp10();
			if("0".equals(spc.getSpp08())){//送审状态下回显
				if(spp10!=null&&!"".equals(spp10)){
					this.getPageElement("usertype").setValue("group");
					this.getPageElement("spb04").setValue(spp10);
					this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getGroupName(spp10));
				}else if(spp09!=null&&!"".equals(spp09)){
					this.getPageElement("usertype").setValue("user");
					this.getPageElement("spb04").setValue(spp09);
					this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getUserName(spp09));
				}
			}
			String spp08 = spc.getSpp08();
			PMPropertyCopyUtil.copyObjValueToElement(spc, this);
			if("0".equals(spp08)){//汇总
				this.getExecuteSG().addExecuteCode("setDisabled1();");
			}else if("1".equals(spp08)){//审批中
				this.getExecuteSG().addExecuteCode("setDisabled2();");
			}else{
				this.getExecuteSG().addExecuteCode("setDisabled3();");
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * grid表格实时保存
	 * @param str
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveChange")
	public int saveChange(String str) throws RadowException, AppException{
		String field = this.getPageElement("field").getValue();//字段名
		String name = this.getPageElement("name").getValue();//姓名
		String value = this.getPageElement("value").getValue();//修改后的值
		String sp01id = this.getPageElement("sp01id").getValue();//主键
		String fieldTitle = this.getPageElement("fieldTitle").getValue();//字段名注释
		try {
			String sql1 ="update sp01 set "+field+" = ? where  sp0100 = ?";
			HBUtil.executeUpdate(sql1,new Object[]{value,sp01id});
			this.toastmessage("姓名为：" + name + "的【"+fieldTitle+"】信息已保存。");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败!");
		}
		return 0;
			
	}
	
}
