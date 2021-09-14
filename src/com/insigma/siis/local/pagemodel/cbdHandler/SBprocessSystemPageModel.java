package com.insigma.siis.local.pagemodel.cbdHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Transaction;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.AttachmentInfo;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CBDInfo;
import com.insigma.siis.local.business.entity.Cbdstatus;
import com.insigma.siis.local.business.entity.Ftpuser;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;

public class SBprocessSystemPageModel extends PageModel{
	public static String cbd_name = "";
	public static String cbd_id = "";
	public static String personname = "";
	public static String filePath = "";
	public static String status = "";

	@Override
	public int doInit() throws RadowException {
		String value = this.getRadow_parent_data();
		cbd_name = value.split("@")[0];
		cbd_id = value.split("@")[1];
		personname = value.split("@")[2];
		filePath = value.split("@")[3];
		status = value.split("@")[4];
		if(status.equals("3")){
			this.getExecuteSG().addExecuteCode("setButton('createCBD');");
			this.getExecuteSG().addExecuteCode("setButton('modifyCBD');");
			this.getExecuteSG().addExecuteCode("setButton('getCBD');");
			this.getExecuteSG().addExecuteCode("setButton('sureBtn');");
			this.getExecuteSG().addExecuteCode("setButton('sureBtn2');");
		}
		HBSession sess = HBUtil.getHBSession();
		StringBuffer stepValue=new StringBuffer();
		List list = sess.createSQLQuery("select cbd_status_step from cbd_status where cbd_id = '"+cbd_id+"' order by cbd_status_step").list();
		if(list.size()>0){
			//将所有的步骤组装，并放到页面中
			for(int i=0;i<list.size();i++){
				stepValue.append(list.get(i)).append(",");
			}
			stepValue.substring(0, stepValue.length()-1);
			this.getPageElement("step").setValue(stepValue.toString());
			this.getExecuteSG().addExecuteCode("controlStep('"+list.get(list.size()-1)+"');");
		}else{
			this.getExecuteSG().addExecuteCode("controlStep('0');");
			this.getPageElement("step").setValue("0");
		}
		this.getPageElement("cbd_id").setValue(cbd_id);
		this.getPageElement("cbd_name").setValue(cbd_name);
		this.getPageElement("filePath").setValue(filePath);
		this.getPageElement("status").setValue(status);
		this.getExecuteSG().addExecuteCode("setcbdname('"+cbd_name+"');");
		return 0;
	}
	
	/**
	 * 生成本级呈报单
	 */
	@PageEvent("createCBD.onclick")
	public int createCBD() throws RadowException{
		String value = cbd_id+"@GU@"+personname;
		this.setRadow_parent_data(value);
		this.openWindow("editCBD", "pages.cbdHandler.AddCBDInfo");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 修改本级呈报单
	 */
	@PageEvent("modifyCBD.onclick")
	public int modifyCBD() throws RadowException{
		String value = cbd_id+"@GU@"+personname;
		this.setRadow_parent_data(value);
		this.openWindow("editCBD", "pages.cbdHandler.AddCBDInfo");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查看呈报单
	 */
	@PageEvent("getCBD.onclick")
	public int getCBD() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		List list = sess.createSQLQuery("select cbd_id from cbd_info where objectno ='"+cbd_id+"' and cbd_path='3'").list();
		String cbd_id2 = "";
		if(list.size()>0){
			cbd_id2 = list.get(0).toString();
		}else{
			this.setMainMessage("请先生成呈报单！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//this.getPageElement("cbd_id").setValue(cbd_id2);
		this.getExecuteSG().addExecuteCode("expExcelTemp('"+cbd_id2+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 提出申请
	 */
	@PageEvent("nextStep")
	public int startWork(String value) throws RadowException{
		
		HBSession sess = HBUtil.getHBSession();
		cbd_id = this.getPageElement("cbd_id").getValue();
		String uuid = UUID.randomUUID().toString();
		//获取时间
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String CBD_STATUS_TIME = sdf.format(new Date());
		Cbdstatus cbdstatus = new Cbdstatus();
		cbdstatus.setStatusid(uuid);
		cbdstatus.setCbdstatusstep(value);
		cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
		cbdstatus.setCbdid(cbd_id);
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.save(cbdstatus);
		ts.commit();
		
		this.setMainMessage("提出申请成功！");
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 刷新
	 */
	@PageEvent("reload")
	public int reload() throws RadowException {
	    this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 批复操作
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	@PageEvent("reply")
	public int reply(String value) throws Exception{
		
		//获取参数
		String[] values = value.split("@");
		String cbd_id = values[0];
		String cbd_name = values[1];
		String filePath = values[2];
		//创建数据库对象
		HBSession sess = HBUtil.getHBSession();
		//创建呈报单对象
		CBDInfo cbdinfo = null;
		//创建附件对象
		AttachmentInfo atta = null;
		//查询呈报单信息和附件信息
		List list= sess.createQuery("from CBDInfo where objectno = '"+cbd_id+"'").list();
		if(list.size()>0){
			cbdinfo = (CBDInfo) list.get(0);
		}else {
			this.setMainMessage("该呈报单还没有录入本级呈报单，不能批复！");
			return EventRtnType.FAILD;
		}
		if(cbdinfo != null){
			
			List list_atta = sess.createQuery("from AttachmentInfo where objectid = '"+cbdinfo.getCbd_id()+"'").list();
			if(list_atta.size()>0 ){
				atta = (AttachmentInfo) list_atta.get(0);
			}else{
				this.setMainMessage("该呈报单还没有上传本级呈报单附件，不能批复！");
				return EventRtnType.FAILD;
			}
		}
		//获取ftp路径
		StringBuffer ftpPath = new StringBuffer();
		List<Ftpuser> ftp_list = sess.createQuery("from Ftpuser t").list();
		if(ftp_list.size()>0){
			ftpPath.append(ftp_list.get(0).getHomedirectory().substring(1,ftp_list.get(0).getHomedirectory().length()));
			ftpPath.append("/cbd/down/");
		}else{
			//组装压缩包存放路径
			//将路径中的up替换为down
			//获取路径
			ftpPath.append(filePath.substring(0,filePath.lastIndexOf("Pack")));
			
		}
		
		//创建工具类
		CBDTools ct = new CBDTools();
		//获取系统根目录
		String rootpath = ct.getPath();
		//附件原路径
		String filepath = rootpath + atta.getFilepath();
		
		//组装批复文件存放文件夹
		String fileDir = cbd_name+filePath.substring(filePath.lastIndexOf("_"), filePath.lastIndexOf(".")).concat("的批复反馈");
		File file = new File(ftpPath.toString()+fileDir);
		//如果意见文件不存在，创建该文件
		if(!file.exists() && !file.isDirectory() ){
			file.mkdirs();
		}
		
		File atta_file = new File(filepath);
		String atta_fileName = atta_file.getName();
		File targetFile = new File(ftpPath.toString()+fileDir+"/"+atta_fileName);
		ReportCBDFilePageModel.copyFile(atta_file, targetFile);
		
		//定义压缩文件名
		String zipFile = ftpPath.toString()+fileDir+".zip";
		//将文件夹压缩（zip压缩包）
		SevenZipUtil.zip7z(ftpPath.toString()+fileDir, zipFile, null);
		
		//组装xml文件
		List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 where b0121 ='-1' ").list();
		B01 b01 = null;
		if (b01s != null && b01s.size() > 0) {
			b01 = b01s.get(0);
		} else {
			this.setMainMessage("系统出错，请联系管理员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//定义时间变量
		java.sql.Timestamp now = DateUtil.getTimestamp();
		String time = DateUtil.timeToString(now);
		String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
		//创建ftp上报信息xml对象
		ZwhzPackDefine info = new ZwhzPackDefine();
		info.setId(cbd_id);
		info.setB0101(b01.getB0101());
		info.setB0111(b01.getB0111());
		info.setB0114(b01.getB0114());
		info.setB0194(b01.getB0194());

		info.setDataversion(DateUtil.dateToString(DateUtil.getSysDate(),
				"yyyyMMdd"));
		info.setLinkpsn("");
		info.setLinktel("");
		info.setRemark("");
		info.setStype("11");
		info.setStypename("呈报单上报");
		info.setTime(time);
		info.setTranstype("down");
		info.setErrortype("无");
		info.setErrorinfo("无");
		info.setCbdStatus("3");//设置呈报单状态  0：未接收
		
		List<SFileDefine> sfile = new ArrayList<SFileDefine>();
		//定义xml文件名
		String packageFile = "Pack_" +fileDir + ".xml";
		
		SFileDefine sf = new SFileDefine();
		sf.setTime(time);
		File zipFile1 = new File(zipFile);
		sf.setName(zipFile1.getName());
		sf.setSize(ct.getFileSize(zipFile1));
		sfile.add(sf);
		
		//记录文件信息
		info.setDatainfo("本级呈报单扫描件文件1个。");
		
		info.setSfile(sfile);
		
		//创建xml文件
		FileUtil.createFile(ftpPath.toString() + "/" + packageFile,
				JXUtil.Object2Xml(info, true), false, "UTF-8");
		
		//删除文件
		targetFile.delete();
		File xmlfile = new File(ftpPath.toString() + "/" + packageFile);
		File zipfile = new File(zipFile);
		File txmlfile = new File(ftpPath.toString() + "/" +fileDir+"/"+ packageFile);
		File tzipfile = new File(ftpPath.toString()+fileDir+"/"+fileDir+".zip");
		ReportCBDFilePageModel.copyFile(xmlfile, txmlfile);
		ReportCBDFilePageModel.copyFile(zipfile, tzipfile);
		String zipFile2 = ftpPath.toString()+fileDir+"下载数据包"+".zip";
		SevenZipUtil.zip7z(ftpPath.toString()+fileDir, zipFile2, null);
		txmlfile.delete();
		tzipfile.delete();
		//删除文件夹
		file.delete();
		
		
		ct.changeStatus(cbd_id+"@"+filePath+"@3");
		
		String uuid = UUID.randomUUID().toString();
		//获取时间
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String CBD_STATUS_TIME = sdf.format(new Date());
		Cbdstatus cbdstatus = new Cbdstatus();
		cbdstatus.setStatusid(uuid);
		cbdstatus.setCbdstatusstep("3");
		cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
		cbdstatus.setCbdid(cbd_id);
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.save(cbdstatus);
		ts.commit();
		this.reloadPage();
		this.setMainMessage("批复完成！");
		this.getExecuteSG().addExecuteCode("parent.radow.doEvent('btnsx.onclick');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 *  跳转至打回意见页面
	 * @param value
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getBackWin")
	public int getBackWin(String value) throws RadowException{
		
		this.setRadow_parent_data(value);
		this.openWindow("backWin", "pages.cbdHandler.EditBackAdvice");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	/**
	 * 查看/删除附件
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("modifyAttach")
	public int editFile(String value) throws RadowException{
		this.setRadow_parent_data(value);
		this.openWindow("modifyFileWindow", "pages.search.ModifyAttach");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 点击下载呈报单数据包判断是否有附件
	 * @param value
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getCBDZip")
	public int getCBDZip(String value) throws RadowException {
		String [] values = value.split("@");
		String cbd_id = values[0];
		String cbd_name = values[1];
		//执行数据库操作
		HBSession sess = HBUtil.getHBSession();
		//通过objectno查询上报呈报单id
		String sb_cbd_id = sess.createSQLQuery("select cbd_id from cbd_info where objectno = '"+cbd_id+"'").uniqueResult().toString();
		if(sb_cbd_id.isEmpty()){
			this.setMainMessage("请先录入上报呈报单！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//查询呈报单对应的附件信息
		String sql = "select * from Attachment_Info ai where ai.objectid = '"+sb_cbd_id+"'";
		List attach_list =  sess.createSQLQuery(sql).list();
		if(attach_list.size()<=0){
			this.setMainMessage("还没有上传呈报单附件，不能下载呈报单数据包！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//获取呈报单对应人员编号
		sql = "select ci.CBD_PERSONID from CBD_Info ci where ci.cbd_id = '"+sb_cbd_id+"'";
		List list = sess.createSQLQuery(sql).list();
		String personid = list.get(0).toString();
		//截取人员编号
		String[] personids = personid.split(",");
						
		//循环查询人员的附件信息
		for(int i =0;i<personids.length;i++){
			sql = "select * from Attachment_Info ai where ai.personid = '"+personids[i]+"'";
			List atta = sess.createSQLQuery(sql).list();
			//判断是否有人员的附件信息，如果有人员没有上传过附件，不能上报
			if(atta.size()<=0){
				this.setMainMessage("还没有上传人员附件，不能下载呈报单数据包！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//获取呈报单对应人员名字
		sql = "select CBD_PERSONNAME from cbd_info where cbd_id = '"+sb_cbd_id+"'";
		List list2 = sess.createSQLQuery(sql).list();
		String personname = list2.get(0).toString();
		//将上报呈报单id、上报呈报单名称、本机呈报单id传到前台
		this.getExecuteSG().addExecuteCode("createCBDZip('"+sb_cbd_id+"','"+cbd_name+"','"+cbd_id+"','"+personname+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//通过光盘方式批复
	@PageEvent("reply1")
	public int reply1(String value){
		
		HBSession sess = HBUtil.getHBSession();
		
		String hql = "from CBDInfo where objectno = '"+value.split("@")[0]+"' and cbd_path = '3'";
		List<CBDInfo> cbdlist = sess.createQuery(hql).list();
		if(cbdlist.size()==0){
			this.setMainMessage("请先录入本级呈报单！");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			hql = "from AttachmentInfo where objectid = '"+cbdlist.get(0).getCbd_id()+"'";
			List attalist = sess.createQuery(hql).list();
			if(attalist.size() == 0){
				this.setMainMessage("没有上传本级呈报单附件！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		this.getExecuteSG().addExecuteCode("createCBDZip1('"+cbdlist.get(0).getCbd_id()+"','"+cbdlist.get(0).getCbd_name()+"','"+value+"','"+value.split("@")[1]+"');");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
