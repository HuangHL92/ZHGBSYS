package com.insigma.siis.local.pagemodel.cbdHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.AttachmentInfo;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CBDInfo;
import com.insigma.siis.local.business.entity.Ftpuser;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.BeanCopyUtil;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.jtrans.IteratorUD;
import com.insigma.siis.local.jtrans.PackFilter;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.UDPackFile;
import com.insigma.siis.local.jtrans.ZwhzFtpClient;
import com.insigma.siis.local.jtrans.ZwhzFtpPath;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;

public class GetCBDFilePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		this.setNextEventName("lfsearch");
		return 0;
	}
	
	/**
	 * 查看呈报单流程
	 */
	@PageEvent("processSystem")
	public int processSystem(String value) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String cbd_id = value.split("@")[0];//获取前台传递的呈报单id
		String cbd_name = value.split("@")[1];//获取前台传递的呈报单名称
		String personname = value.split("@")[2];//获取前台传递的人员名字
		String status = value.split("@")[4];
		String filePath = value.split("@")[5];
		if(status.equals("0")){
			this.setMainMessage("请先接收呈报单");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String value2 = cbd_name+"@"+cbd_id+"@"+personname+"@"+filePath+"@"+status;
		this.setRadow_parent_data(value2);//将人员id、呈报单名称和呈报单id传递到呈报单流程页面
		this.openWindow("SBprocessSystemWindow", "pages.cbdHandler.SBprocessSystem");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 查询文件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("lfsearch")
	public int lfsearch() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		//获取登陆用户信息
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		List<TransConfig> jfccs = sess.createQuery("from TransConfig t where t.type='0' and t.status='1'").list();
		ZwhzFtpClient.hlPoling(jfccs);
		try {
			Grid grid = (Grid)this.createPageElement("CBDGrid", ElementType.GRID, false);
			List<String> param = new ArrayList<String>();
			param.add("CBD");
			List<UDPackFile> udpfs =  IteratorUD.iteratorDir(param);
			List<UDPackFile> udpfs_zip =  GetCBDFilePageModel.iteratorZipDir();
			List<HashMap<String, Object>> gridList = new ArrayList<HashMap<String,Object>>();
			for (UDPackFile udpf : udpfs) {
				HashMap<String, Object> hm = new HashMap<String, Object>();
				BeanCopyUtil.CopytoHashMap(udpf, hm);
				hm.put("transTypeCn", "up".equals(udpf.getTransType())?"上报":"下发");
				//查询呈报单记录对应的本级呈报单名称
//				String cbd_id = (String) hm.get("cbd_id");
//				String hql = "from CBDInfo where cbd_id = '"+cbd_id+"'";
//				List<CBDInfo> list = sess.createQuery(hql).list();
//				if(list.size()>0){
//					hm.put("cbd_name", list.get(0).getCbd_name());
//				}else{
//					hm.put("cbd_name", "1");
//				}
				List<SFileDefine> sfile = (List<SFileDefine>) hm.get("sfile");
				SFileDefine sfd = sfile.get(0);
				String name = sfd.getName().split("_")[0];
				hm.put("cbd_name", name);
				hm.put("fileName", name);
				hm.put("username", user.getName());
				gridList.add(hm);
			}
			for(UDPackFile udpf_zip : udpfs_zip){
				HashMap<String, Object> hm = new HashMap<String, Object>();
				BeanCopyUtil.CopytoHashMap(udpf_zip, hm);
				hm.put("transTypeCn", "up".equals(udpf_zip.getTransType())?"上报":"下发");
				//查询呈报单记录对应的本级呈报单名称
//				String cbd_id = (String) hm.get("cbd_id");
//				String hql = "from CBDInfo where cbd_id = '"+cbd_id+"'";
//				List<CBDInfo> list = sess.createQuery(hql).list();
//				if(list.size()>0){
//					hm.put("cbd_name", list.get(0).getCbd_name());
//				}else{
//					hm.put("cbd_name", "1");
//				}
				List<SFileDefine> sfile = (List<SFileDefine>) hm.get("sfile");
				SFileDefine sfd = sfile.get(0);
				String name = sfd.getName().split("_")[0];
				hm.put("cbd_name", name);
				hm.put("fileName", name);
				hm.put("username", user.getName());
				gridList.add(hm);
			}
			if(gridList.size()>0){
				grid.setValueList(gridList);
			}else{
				grid.setValueList(new ArrayList<HashMap<String,Object>>());
			}
			return EventRtnType.NORMAL_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("检索待接收目录失败:"+e.getMessage());
		}
	}
	/**
	 * 刷新按钮事件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btnsx.onclick")
	public int btnsxOnClick()throws RadowException{
		this.setNextEventName("lfsearch");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 录入本级呈报单页面
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("addCBDInfo")
	public int cbdAdd(String value) throws RadowException{
		
		this.setRadow_parent_data(value+"@GA");
		this.openWindow("editCBD", "pages.cbdHandler.AddCBDInfo");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 修改呈报单信息
	 * @param value
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("modifyCBD")
	public int modifyCBD(String value) throws RadowException{
		
		this.setRadow_parent_data(value+"@GU");
		this.openWindow("editCBD", "pages.cbdHandler.AddCBDInfo");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("getCBDBtn")
	public int getCBDBtn() throws RadowException{
		
		String value = chooseCBDs("CBDGrid");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private String chooseCBDs(String grid) throws RadowException{
		PageElement pe = this.getPageElement(grid);
		List<HashMap<String, Object>> list = pe.getValueList();
		String values = "";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("cbdchecked");
			if(logchecked.equals(true)){
				String cbd_id = (String) this.getPageElement(grid).getValue("cbd_id", i);
				String filePath = (String) this.getPageElement(grid).getValue("filePath", i);
				if(values.equals("")){
					values += cbd_id+"@"+filePath;
				}
				else{
					values += ","+cbd_id+"@"+filePath;
				}
			}
		}
		return values;
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
			//String path = filePath.substring(0,filePath.lastIndexOf("Pack"));
			
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
		info.setStype("10");
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
		
		//删除打回意见文件
		targetFile.delete();
		//删除打回意见文件夹
		file.delete();
		
		ct.changeStatus(cbd_id+"@"+filePath+"@3");
		this.setMainMessage("批复完成！");
		this.setNextEventName("lfsearch");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查看/删除附件
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("modifyAttach")
	public int modifyAttach(String value) throws RadowException{
		this.setRadow_parent_data(value);
		this.openWindow("modifyFileWindow", "pages.search.ModifyAttach");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 获取系统下的呈报单文件
	 * @return
	 * @throws Exception
	 */
	public static List<UDPackFile> iteratorZipDir() throws Exception{
		List<UDPackFile> udpackFiles = new ArrayList<UDPackFile>();
		String packXmlStr = null;//总包文件内容字符串
		ZwhzPackDefine zpdefine = null;//总包文件解析对象
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    Calendar cal = null;
	    List<String> dirpaths = null;//检索目录的信息
	    CBDTools ct = new CBDTools();
	    String rootPath = ct.getPath();
		try{
			File subDir = new File(rootPath+"GetZip/");
			if(subDir.exists()){
				
				File[] subFiles = subDir.listFiles(new PackFilter("pack_", ".xml"));
				for (File file : subFiles) {
					try{
						UDPackFile udpf = new UDPackFile();
						udpf.setFileName(file.getName());
						udpf.setFilePath(StringUtils.replace(file.getPath(), "\\", "/"));
						packXmlStr = FileUtil.readFileByChars(file.getPath(),"UTF-8"); //读取总包文件内容
						if(packXmlStr!=null){
							zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //解析总包文件
							udpf.setCreateTime(zpdefine.getTime());
							udpf.setOrgId(zpdefine.getB0111());
							udpf.setOrgName(zpdefine.getB0101());
							udpf.setTransType(zpdefine.getTranstype());
							udpf.setDataInfo(zpdefine.getDatainfo());
							udpf.setFilePublishType(zpdefine.getStype());
							udpf.setIsOther("0");//非other目录文件
							if("11".equals(zpdefine.getStype())){//当上报类型为10：呈报单时，获取呈报单状态信息
								udpf.setStatus(zpdefine.getCbdStatus());
								udpf.setCbd_id(zpdefine.getId());
								udpf.setSfile(zpdefine.getSfile());
								udpf.setPersonname(zpdefine.getPersonname());
							}
						}
						udpackFiles.add(udpf);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			return udpackFiles;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("检索文件目录失败:"+e.getMessage());
		}
	}
	
	@PageEvent("checkCBD")
	public int checkCBD(String value){
		
		String[] values = value.split("@");
		//创建数据库对象
		HBSession sess = HBUtil.getHBSession();
		List list = sess.createQuery("from CBDInfo where objectno = '"+values[0]+"'").list();
		if(list.size()>0){
			if("editFile".equals(values[1])){
				
				this.getExecuteSG().addExecuteCode("editFile()");
			}else if("modifyFile".equals(values[1])){
				this.getExecuteSG().addExecuteCode("modifyFile()");
			}
		}else{
			this.setMainMessage("该呈报单还没有录入本级呈报单，不能编辑附件！");
			return 0;
		}
		return 0;
	}
	
	/**
	 * 接收页面呈报单记录的双击事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("CBDGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException{  //打开窗口的实例
		String cbd_id = this.getPageElement("CBDGrid").getValue("cbd_id",this.getPageElement("CBDGrid").getCueRowIndex()).toString();
		String cbd_name = this.getPageElement("CBDGrid").getValue("cbd_name",this.getPageElement("CBDGrid").getCueRowIndex()).toString();
		String personname = this.getPageElement("CBDGrid").getValue("personname",this.getPageElement("CBDGrid").getCueRowIndex()).toString();
		String personid = this.getPageElement("CBDGrid").getValue("personid",this.getPageElement("CBDGrid").getCueRowIndex()).toString();
		String status = this.getPageElement("CBDGrid").getValue("status",this.getPageElement("CBDGrid").getCueRowIndex()).toString();
		String filePath = this.getPageElement("CBDGrid").getValue("filePath",this.getPageElement("CBDGrid").getCueRowIndex()).toString();
		this.getExecuteSG().addExecuteCode("SBprocessBtn('"+cbd_id+"@"+cbd_name+"@"+personname+"@"+personid+"@"+status+"@"+filePath+"')");
		//this.setRadow_parent_data(cbd_id+"@"+cbd_name+"@"+cbd_path);
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
}
