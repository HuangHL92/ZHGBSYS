package com.insigma.siis.local.pagemodel.jzsp.fjsp;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;

import com.JUpload.JUpload;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.Sp01;
import com.insigma.siis.local.business.entity.Sp01_Pc;
import com.insigma.siis.local.business.entity.Sp_Att;
import com.insigma.siis.local.business.entity.Sp_Bus;
import com.insigma.siis.local.business.entity.Sp_Bus_Log;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.jzsp.SP2Util;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;

public class AddFJPageModel extends PageModel  implements JUpload{
	
	
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
		String spp13 = this.getPageElement("spp13").getValue();
		String spp02 = this.getPageElement("spp02").getValue();
		if(spp13==null||"".equals(spp13)){
			this.setMainMessage("标题不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(spp02==null||"".equals(spp02)){
			this.setMainMessage("类型不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		Sp01_Pc sp01_pc = new Sp01_Pc();
		String spb04 = this.getPageElement("spb04").getValue();//送审单位 或领导
		String usertype = this.getPageElement("usertype").getValue();//送审单位 或领导
		String groupid = SysManagerUtils.getUserGroupid();
		Map<String, String> map = new HashMap<String, String>();
		HBSession sess = HBUtil.getHBSession();
		boolean isUpload = false;
		boolean isAdd = false;
		try {
			sess.getTransaction().begin();
			
			if(spp00==null||"".equals(spp00)){//新增
				isAdd=true;
				sp01_pc.setSpp00(UUID.randomUUID().toString());
				sp01_pc.setSpp08("0");//审批状态 0未送审 1审批中 2审批通过 3审批不通过
				sp01_pc.setSpp11(SysManagerUtils.getUserId());//申请人
				sp01_pc.setSpp03(new Date());//登记时间
				sp01_pc.setSpp02(spp02);//审批类别
				sp01_pc.setSpp13(spp13);//标题
				sess.save(sp01_pc);
				spp00 = sp01_pc.getSpp00();
				
				
				//增加流程
				Sp_Bus sb = new Sp_Bus();
				sb.setSpb00(sp01_pc.getSpp00());//流程主键
				sb.setSpb01(sp01_pc.getSpp00());//业务主键
				sb.setSpb02("0");//审批状态 0未送审 1审批中 2审批通过 3审批不通过
				if(spb04!=null&&!"".equals(spb04)){
					if("group".equals(usertype)){
						sb.setSpb04(spb04);//审批机构
					}else if("user".equals(usertype)){
						sb.setSpb03(spb04);//审批人
					}
				}
				
				sb.setSpb05(spp02);//类型1机关领导
				sb.setSpb06("1");//当前节点
				sess.save(sb);
				//增加流程日志
				Sp_Bus_Log sbl = new Sp_Bus_Log();
				sbl.setSpbl00((long)Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004")));
				sbl.setSpb00(sp01_pc.getSpp00());//流程主键
				sbl.setSpbl01(SysManagerUtils.getUserId());//操作人id
				sbl.setSpbl02(SysManagerUtils.getUserName());//操作人姓名
				sbl.setSpbl03(groupid);//操作机构
				sbl.setSpbl04("1");//操作类型1登记 2送审 3审批通过 4审批不通过 5结束
				sbl.setSpbl05(new Date());//操作时间
				sbl.setSpbl06("新增登记表");//描述
				sbl.setSpbl07("1");//操作节点
				sess.save(sbl);
				sess.flush();
				
				isUpload = true;
				//this.setMainMessage("新增成功");
			}else{
				
				sp01_pc = (Sp01_Pc)sess.get(Sp01_Pc.class, spp00);
				if(sp01_pc==null){
					this.setMainMessage("审批表信息不存在！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if("0".equals(sp01_pc.getSpp08())){//登记状态
					sp01_pc.setSpp02(spp02);//审批类别
					sp01_pc.setSpp13(spp13);//标题
					isUpload = true;
				}
				
				Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, spp00);
				String[] spInfo = SP2Util.getSPInfo(spp00);
				if(spInfo!=null){
					map.put(spInfo[0], spInfo[1]);
				}
				if(spb04!=null&&!"".equals(spb04)){
					if("group".equals(usertype)){
						sb.setSpb04(spb04);//审批机构
						sb.setSpb03(null);//审批人
					}else if("user".equals(usertype)){
						sb.setSpb04(null);//审批机构
						sb.setSpb03(spb04);//审批人
					}
				}else{
					sb.setSpb04(null);//审批机构
					sb.setSpb03(null);//审批人
				}
				
				sess.update(sb);
				sess.flush();
			}
			//送审
			if("ss2".equals(ss)){
				
				String spbl08 = this.getPageElement("spbl08").getValue();
				Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, spp00);
				int hj = Integer.valueOf(sb.getSpb06());
				if(hj==4){
					throw new AppException("审批已完结！");
				}
				
				String nexthj = "2";//下一个环节
				String spjg = "2";//当前审批结果//操作类型1登记 2送审 3审批通过 4审批不通过 5结束
				String spzt = "1";//审批状态
				String desc = "送审";//描述
				
				
				
				sp01_pc.setSpp08(spzt);//审批中
				sess.update(sp01_pc);
				
				String spb03 = sb.getSpb03();//审批机构
				spb04 = sb.getSpb04();//审批人
				if((spb04==null||"".equals(spb04))&&(spb03==null||"".equals(spb03))){
					throw new AppException("请选择送审单位或处理人！");
				}
				sb.setSpb02(spzt);//审批中
				sb.setSpb06(nexthj);//第二环节
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
				sbl.setSpbl08(spbl08);
				sess.save(sbl);			
				
				sess.flush();
				this.getExecuteSG().addExecuteCode("saveCallBack('保存成功','1');");
			}
			sess.getTransaction().commit();
			if(isUpload){
				this.upLoadFile("file03");
				this.getPageElement("spp00").setValue(sp01_pc.getSpp00());
				if(isAdd){
					this.getExecuteSG().addExecuteCode("setFileLength('新增成功');");
				}else{
					this.getExecuteSG().addExecuteCode("setFileLength('保存成功');");
				}
			}
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
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String spp00 = this.getPageElement("spp00").getValue();
		try {
			HBSession sess = HBUtil.getHBSession();
			if(spp00!=null&&!"".equals(spp00)){
				Sp01_Pc sp01_pc = (Sp01_Pc)sess.get(Sp01_Pc.class, spp00);
				if(sp01_pc==null){
					this.setMainMessage("查询失败");
					return EventRtnType.NORMAL_SUCCESS;
				}
				PMPropertyCopyUtil.copyObjValueToElement(sp01_pc, this);
				Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, spp00);
				String spb04 = sb.getSpb04();//审批机构
				String spb03 = sb.getSpb03();//审批人
				if("0".equals(sb.getSpb02())){//送审状态下回显
					if(spb04!=null&&!"".equals(spb04)){
						this.getPageElement("usertype").setValue("group");
						this.getPageElement("spb04").setValue(spb04);
						this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getGroupName(spb04));
					}else if(spb03!=null&&!"".equals(spb03)){
						this.getPageElement("usertype").setValue("user");
						this.getPageElement("spb04").setValue(spb03);
						this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getUserName(spb03));
					}
				}
				
				String spb06 = sb.getSpb06();//当前节点
				if("1".equals(spb06)){//登记节点
					this.getExecuteSG().addExecuteCode("setDisabled1();");
				}else if("2".equals(spb06)){//一级审批
					this.getExecuteSG().addExecuteCode("setDisabled2();");
				}else if("3".equals(spb06)){//二级审批
					this.getExecuteSG().addExecuteCode("setDisabled3();");
				}else if("4".equals(spb06)){//三级审批
					this.getExecuteSG().addExecuteCode("setDisabled4();");
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
			}else{
				this.getExecuteSG().addExecuteCode("setDisabled1();");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("searchA01")
	public int searchA01(String name) throws RadowException, AppException{
		
		try {
			String sql = "select count(1) from a01 where status!='4' and a0101=?";
			HBSession sess = HBUtil.getHBSession();
			Object size = sess.createSQLQuery(sql).setString(0, name).uniqueResult();
			int s = Integer.valueOf(size.toString());
			if(s==1){
				List<A01> a01list = sess.createQuery("from A01 where status!='4' and a0101=?").setString(0, name).list();
				A01 a01 = a01list.get(0);
				this.getPageElement("a0000").setValue(a01.getA0000());
				this.getPageElement("sp0103").setValue(a01.getA0104());
				this.getPageElement("sp0104").setValue(a01.getA0107());
				this.getPageElement("sp0106").setValue(a01.getA0192a());
			}else if(s==0){
				this.setMainMessage("无该人员信息！");
			}else{
				this.getExecuteSG().addExecuteCode("openPwin();");
			}
		}catch (Exception e) {
			this.setMainMessage("查询失败");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("searchA01ByA0000")
	public int searchA01ByA0000(String a0000) throws RadowException, AppException{
		
		try {
			if(a0000==null||"".equals(a0000)){
				this.setMainMessage("请选择人员！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			HBSession sess = HBUtil.getHBSession();
				A01 a01 = (A01)sess.get(A01.class, a0000);
				if(a01==null){
					this.setMainMessage("无该人员信息！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				this.getPageElement("a0000").setValue(a01.getA0000());
				this.getPageElement("sp0103").setValue(a01.getA0104());
				this.getPageElement("sp0104").setValue(a01.getA0107());
				this.getPageElement("sp0106").setValue(a01.getA0192a());
				this.getPageElement("sp0102").focus();
		}catch (Exception e) {
			this.setMainMessage("查询失败");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 获得文件名称
		String isfile = formDataMap.get("Filename");
		// 判断是否上传了附件，没有上传则不进行文件处理
		if (isfile != null && !isfile.equals("")) {
			try {
				// 获取表单信息
				FileItem fi = fileItem.get(0);
				DecimalFormat df = new DecimalFormat("#.00");
				String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
				// 如果文件大于1M则显示M，小于则显示kb
				if (fi.getSize() < 1048576) {
					fileSize = (int) fi.getSize() / 1024 + "KB";
				}
				if (fi.getSize() < 1024) {
					fileSize = (int) fi.getSize() / 1024 + "B";
				}
				String id = saveFile(formDataMap, fi,fileSize);
				map.put("file_pk", id);
				map.put("file_name", isfile);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return map;
	}
	public static String  disk = JSGLBS.HZBPATH ;
	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// 获得人员信息id
		String spp00 = formDataMap.get("spp00");
		String filename = formDataMap.get("Filename");
		
		Sp_Att spa = new Sp_Att();
		spa.setSpb00(spp00);;//业务主键
		spa.setSpa00(UUID.randomUUID().toString());//主键
		spa.setSpa03(SysManagerUtils.getUserId());//用户id
		spa.setSpa04(new Date());//上传时间
		spa.setSpa06(fileSize);
		spa.setSpa02(filename);
		
		String directory = "zhgbuploadjzspfiles" + File.separator +spp00+ File.separator;
		String filePath = directory  + spa.getSpa00();
		File f = new File(disk + directory);
		
		if(!f.isDirectory()){
			f.mkdirs();
		}
		fi.write(new File(disk + filePath));
		spa.setSpa05(directory);
		HBUtil.getHBSession().save(spa);
		HBUtil.getHBSession().flush();
		
		return spa.getSpa00();
	}
	@Override
	public String deleteFile(String id) {
		try {
			HBSession sess = HBUtil.getHBSession();
			Sp_Att spa = (Sp_Att)sess.get(Sp_Att.class, id);
			if(spa==null){
				return null;//删除失败
			}
			String directory = disk+spa.getSpa05();
			File f = new File(directory+id);
			if(f.isFile()){
				f.delete();
			}
			sess.delete(spa);
			sess.flush();
			
			return id;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
