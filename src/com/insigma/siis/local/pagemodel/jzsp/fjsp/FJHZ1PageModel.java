package com.insigma.siis.local.pagemodel.jzsp.fjsp;

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
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Sp01;
import com.insigma.siis.local.business.entity.Sp01_Pc;
import com.insigma.siis.local.business.entity.Sp_Att;
import com.insigma.siis.local.business.entity.Sp_Bus;
import com.insigma.siis.local.business.entity.Sp_Bus_Log;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.jzsp.SP2Util;

public class FJHZ1PageModel extends PageModel{
	
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
	
		
		String spp00 = this.getPageElement("spp00").getValue();
		String sql="select * from SP01 t where spp00 ='"+spp00+"' "
				+ " order by t.sp0116 asc";
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String groupid = SysManagerUtils.getUserGroupid();
		HBSession sess = HBUtil.getHBSession();
		
		try {
			sess.getTransaction().begin();
			
			
			
			//送审
			if("ss2".equals(ss)){
				String[] spInfo = SP2Util.getSPInfo(spp00);
				Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, spp00);
				int hj = Integer.valueOf(sb.getSpb06());
				if(hj>=4){
					throw new AppException("审批已完结！");
				}
				
				String curUser = SysManagerUtils.getUserId();
				String curGroup = SysManagerUtils.getUserGroupid();
				if("user".equals(spInfo[0])){//审批用户为当前用户
					if(!curUser.equals(spInfo[1])){
						throw new AppException("当前用户无审批权限！");
					}
					
				}
				if("group".equals(spInfo[0])){
					if(!curGroup.equals(spInfo[1])){
						throw new AppException("当前用户无审批权限！");
					}
				}
				
				String nexthj = "3";//下一个环节
				String spjg = "3";//当前审批结果
				String spzt = "1";//审批状态
				String desc = spp04;//描述
				
				
				spc = (Sp01_Pc)sess.get(Sp01_Pc.class, spp00);
				spc.setSpp08(spzt);//审批中
				spc.setSpp04(spp04);//处室意见
				spc.setSpp06(sdf.format(new Date()));
				sess.update(spc);
		
				if(spb04==null||"".equals(spb04)){
					throw new AppException("请选择送审单位或处理人！");
				}
			
					
				
				if(spp04==null||"".equals(spp04)){
					throw new AppException("处室意见不能为空！");
				}
				sb.setSpb02(spzt);//审批中
				sb.setSpb06(nexthj);//第二环节
				if("user".equals(usertype)){
					sb.setSpb03(spb04);//审批人
					sb.setSpb04(null);
				}else if("group".equals(usertype)){
					sb.setSpb03(null);//审批人
					sb.setSpb04(spb04);
				}
				sess.save(sb);
				//增加流程日志
				Sp_Bus_Log sbl = new Sp_Bus_Log();
				sbl.setSpbl00((long)Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004")));
				sbl.setSpb00(spp00);//流程主键
				sbl.setSpbl01(SysManagerUtils.getUserId());//操作人id
				sbl.setSpbl02(SysManagerUtils.getUserName());//操作人姓名
				sbl.setSpbl03(SysManagerUtils.getUserGroupid());//操作机构
				sbl.setSpbl04(spjg);//操作类型1登记 2送审 3审批通过 4审批不通过 5完结
				sbl.setSpbl05(new Date());//操作时间
				sbl.setSpbl06(desc);//描述
				sbl.setSpbl07(hj+"");//当前环节
				sbl.setSpbl08(spp04);
				sess.save(sbl);
				
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
			this.setMainMessage("查询失败");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			Sp01_Pc spc = (Sp01_Pc)sess.get(Sp01_Pc.class, spp00);
			if(spc==null){
				this.setMainMessage("查询失败");
				return EventRtnType.NORMAL_SUCCESS;
			}

			/*Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, spp00);
			String spb03 = sb.getSpb03();
			String spb04 = sb.getSpb04();
			if("2".equals(sb.getSpb06())){//送审状态下回显
				if(spb04!=null&&!"".equals(spb04)){
					this.getPageElement("usertype").setValue("group");
					this.getPageElement("spb04").setValue(spb04);
					this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getGroupName(spb04));
				}else if(spb03!=null&&!"".equals(spb03)){
					this.getPageElement("usertype").setValue("user");
					this.getPageElement("spb04").setValue(spb03);
					this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getUserName(spb03));
				}
			}*/
			String spp08 = spc.getSpp08();
			PMPropertyCopyUtil.copyObjValueToElement(spc, this);
			if("1".equals(spp08)){//审批中
				this.getExecuteSG().addExecuteCode("setDisabled2();");
			}
			
			
			//设置文件信息
			List<Sp_Att> spalist = sess.createQuery("from Sp_Att where spb00='"+spp00+"'").list();
			
			if(spalist!=null){
				List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
				for(Sp_Att spa : spalist){
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", spa.getSpa00());
					map.put("name", spa.getSpa02());
					map.put("fileSize", spa.getSpa06());
					//map2.put("readOnly", "true");
					
					listmap.add(map);
					this.setFilesInfo("file03",listmap,false);
				}
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
		String spp00 = this.getPageElement("spp00").getValue();
		String field = this.getPageElement("field").getValue();//字段名
		String name = this.getPageElement("name").getValue();//姓名
		String value = this.getPageElement("value").getValue();//修改后的值
		String sp01id = this.getPageElement("sp01id").getValue();//主键
		String fieldTitle = this.getPageElement("fieldTitle").getValue();//字段名注释
		try {
			Sp01 sp01 = (Sp01)HBUtil.getHBSession().get(Sp01.class, sp01id);
			if(sp01!=null){//更新
				String sql1 ="update sp01 set "+field+" = ? where  sp0100 = ?";
				HBUtil.executeUpdate(sql1,new Object[]{value,sp01id});
			}else{//新增
				String sql1 ="insert into sp01(sp0100,"+field+",spp00,sp0116) values(?,?,?,sysdate)";
				HBUtil.executeUpdate(sql1,new Object[]{sp01id,value,spp00});
			}
			
			this.toastmessage("【"+fieldTitle+"】信息已保存。");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败!");
		}
		return 0;
			
	}
	
}
