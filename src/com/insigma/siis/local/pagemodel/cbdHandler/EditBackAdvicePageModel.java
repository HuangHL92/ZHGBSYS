package com.insigma.siis.local.pagemodel.cbdHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Cbdstatus;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;

public class EditBackAdvicePageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		String value = this.getRadow_parent_data();
		String cbd_id = value.split("@")[0];
		String cbd_name = value.split("@")[1];
		String filePath = value.split("@")[2];
		this.getPageElement("cbd_id").setValue(cbd_id);
		this.getPageElement("cbd_name").setValue(cbd_name);
		this.getPageElement("filePath").setValue(filePath);
		this.getPageElement("operateFlag").setValue(value.split("@")[3]);
		return 0;
	}
	
	/**
	 * 执行打回意见
	 * @return
	 * @throws Exception
	 */
	@PageEvent("save.onclick")
	public int saveBackAdvice() throws Exception{
		
		String cbd_text = this.getPageElement("cbd_text").getValue();
		String cbd_name = this.getPageElement("cbd_name").getValue();
		String filePath = this.getPageElement("filePath").getValue();
		String cbd_id = this.getPageElement("cbd_id").getValue();
		String linkpsn = this.getPageElement("linkpsn").getValue();
		String linktel = this.getPageElement("linktel").getValue();
		String remark = this.getPageElement("remark").getValue();
		if("gp".equals(this.getPageElement("operateFlag").getValue())){
			//将上报呈报单id、上报呈报单名称、本机呈报单id传到前台
			this.getExecuteSG().addExecuteCode("createCBDZip('"+cbd_text+"','"+cbd_name+"','"+cbd_id+"','"+filePath+"','"+linkpsn+"','"+linktel+"','"+remark+"','gpb');");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//将路径中的up替换为down
		String filename = filePath.replace("up", "down");
		//获取路径
		String path = filename.substring(0,filename.lastIndexOf("Pack"));
		//组装打回意见文件夹
		String fileDir = cbd_name+filename.substring(filename.lastIndexOf("_"), filename.lastIndexOf(".")).concat("的反馈");
		String fileName = cbd_name+filename.substring(filename.lastIndexOf("_"), filename.lastIndexOf(".")).concat("的打回意见.txt");
		File file = new File(path+fileDir);
		File file1 = new File(path+fileDir+"/"+fileName);
		//如果意见文件不存在，创建该文件
		if(!file.exists() && !file.isDirectory() ){
			file.mkdirs();
		}
		//将页面中的意见内容写到文件中
		OutputStreamWriter pw = null;//定义一个流
		pw = new OutputStreamWriter(new FileOutputStream(file1),"GBK");//确认流的输出文件和编码格式
		pw.write(cbd_text);//将要写入文件的内容，可以多次write
		pw.close();//关闭流
		
		//定义压缩文件名
		String zipFile = path+fileDir+".zip";
		//将文件夹压缩（zip压缩包）
		SevenZipUtil.zip7z(path+fileDir, zipFile, null);
		
		//组装xml文件
		HBSession sess = HBUtil.getHBSession();
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
		info.setLinkpsn(linkpsn);
		info.setLinktel(linktel);
		info.setRemark(remark);
		info.setStype("11");
		info.setStypename("呈报单上报");
		info.setTime(time);
		info.setTranstype("down");
		info.setErrortype("无");
		info.setErrorinfo("无");
		info.setCbdStatus("2");//设置呈报单状态  0：未接收
		
		List<SFileDefine> sfile = new ArrayList<SFileDefine>();
		//定义xml文件名
		String packageFile = "Pack_" +fileDir + ".xml";
		
		SFileDefine sf = new SFileDefine();
		sf.setTime(time);
		File zipFile1 = new File(zipFile);
		sf.setName(zipFile1.getName());
		//创建工具类
		CBDTools ct = new CBDTools();
		sf.setSize(ct.getFileSize(zipFile1));
		sfile.add(sf);
		
		//记录文件信息
		info.setDatainfo("呈报单打回意见文件1个。");
		
		info.setSfile(sfile);
		
		//创建xml文件
		FileUtil.createFile(path + "/" + packageFile,
				JXUtil.Object2Xml(info, true), false, "UTF-8");
		
		//删除打回意见文件
		file1.delete();
		//删除打回意见文件夹
		file.delete();
		
		ct.changeStatus(cbd_id+"@"+filePath+"@2");
		
		String uuid = UUID.randomUUID().toString();
		//获取时间
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String CBD_STATUS_TIME = sdf.format(new Date());
		Cbdstatus cbdstatus = new Cbdstatus();
		cbdstatus.setStatusid(uuid);
		cbdstatus.setCbdstatusstep("2");
		cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
		cbdstatus.setCbdid(cbd_id);
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.save(cbdstatus);
		ts.commit();
		this.getExecuteSG().addExecuteCode("parent.radow.doEvent('reload')");
		
		this.setMainMessage("呈报单打回成功！");
		this.closeCueWindowByYes("backWin");
		this.getExecuteSG().addExecuteCode("parent.parent.radow.doEvent('lfsearch')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("gbreload")
	public int reload(){
		
		return EventRtnType.NORMAL_SUCCESS;
	}

}
