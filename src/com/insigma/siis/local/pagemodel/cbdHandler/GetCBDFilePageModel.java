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
	 * ??????????????
	 */
	@PageEvent("processSystem")
	public int processSystem(String value) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String cbd_id = value.split("@")[0];//????????????????????id
		String cbd_name = value.split("@")[1];//????????????????????????
		String personname = value.split("@")[2];//??????????????????????
		String status = value.split("@")[4];
		String filePath = value.split("@")[5];
		if(status.equals("0")){
			this.setMainMessage("??????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String value2 = cbd_name+"@"+cbd_id+"@"+personname+"@"+filePath+"@"+status;
		this.setRadow_parent_data(value2);//??????id????????????????????id????????????????????
		this.openWindow("SBprocessSystemWindow", "pages.cbdHandler.SBprocessSystem");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ????????
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("lfsearch")
	public int lfsearch() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		//????????????????
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
				hm.put("transTypeCn", "up".equals(udpf.getTransType())?"????":"????");
				//??????????????????????????????????
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
				hm.put("transTypeCn", "up".equals(udpf_zip.getTransType())?"????":"????");
				//??????????????????????????????????
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
			throw new RadowException("??????????????????:"+e.getMessage());
		}
	}
	/**
	 * ????????????
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btnsx.onclick")
	public int btnsxOnClick()throws RadowException{
		this.setNextEventName("lfsearch");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ??????????????????
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
	 * ??????????????
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
	 *  ??????????????????
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
	 * ????????
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	@PageEvent("reply")
	public int reply(String value) throws Exception{
		
		//????????
		String[] values = value.split("@");
		String cbd_id = values[0];
		String cbd_name = values[1];
		String filePath = values[2];
		//??????????????
		HBSession sess = HBUtil.getHBSession();
		//??????????????
		CBDInfo cbdinfo = null;
		//????????????
		AttachmentInfo atta = null;
		//????????????????????????
		List list= sess.createQuery("from CBDInfo where objectno = '"+cbd_id+"'").list();
		if(list.size()>0){
			cbdinfo = (CBDInfo) list.get(0);
		}else {
			this.setMainMessage("????????????????????????????????????????");
			return EventRtnType.FAILD;
		}
		if(cbdinfo != null){
			
			List list_atta = sess.createQuery("from AttachmentInfo where objectid = '"+cbdinfo.getCbd_id()+"'").list();
			if(list_atta.size()>0 ){
				atta = (AttachmentInfo) list_atta.get(0);
			}else{
				this.setMainMessage("????????????????????????????????????????????");
				return EventRtnType.FAILD;
			}
		}
		//????ftp????
		StringBuffer ftpPath = new StringBuffer();
		List<Ftpuser> ftp_list = sess.createQuery("from Ftpuser t").list();
		if(ftp_list.size()>0){
			ftpPath.append(ftp_list.get(0).getHomedirectory().substring(1,ftp_list.get(0).getHomedirectory().length()));
			ftpPath.append("/cbd/down/");
		}else{
			//??????????????????
			//??????????up??????down
			//????????
			ftpPath.append(filePath.substring(0,filePath.lastIndexOf("Pack")));
			//String path = filePath.substring(0,filePath.lastIndexOf("Pack"));
			
		}
		
		//??????????
		CBDTools ct = new CBDTools();
		//??????????????
		String rootpath = ct.getPath();
		//??????????
		String filepath = rootpath + atta.getFilepath();
		
		//??????????????????????
		String fileDir = cbd_name+filePath.substring(filePath.lastIndexOf("_"), filePath.lastIndexOf(".")).concat("??????????");
		File file = new File(ftpPath.toString()+fileDir);
		//??????????????????????????????
		if(!file.exists() && !file.isDirectory() ){
			file.mkdirs();
		}
		
		File atta_file = new File(filepath);
		String atta_fileName = atta_file.getName();
		File targetFile = new File(ftpPath.toString()+fileDir+"/"+atta_fileName);
		ReportCBDFilePageModel.copyFile(atta_file, targetFile);
		
		//??????????????
		String zipFile = ftpPath.toString()+fileDir+".zip";
		//??????????????zip????????
		SevenZipUtil.zip7z(ftpPath.toString()+fileDir, zipFile, null);
		
		//????xml????
		List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 where b0121 ='-1' ").list();
		B01 b01 = null;
		if (b01s != null && b01s.size() > 0) {
			b01 = b01s.get(0);
		} else {
			this.setMainMessage("????????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//????????????
		java.sql.Timestamp now = DateUtil.getTimestamp();
		String time = DateUtil.timeToString(now);
		String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
		//????ftp????????xml????
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
		info.setStypename("??????????");
		info.setTime(time);
		info.setTranstype("down");
		info.setErrortype("??");
		info.setErrorinfo("??");
		info.setCbdStatus("3");//??????????????  0????????
		
		List<SFileDefine> sfile = new ArrayList<SFileDefine>();
		//????xml??????
		String packageFile = "Pack_" +fileDir + ".xml";
		
		SFileDefine sf = new SFileDefine();
		sf.setTime(time);
		File zipFile1 = new File(zipFile);
		sf.setName(zipFile1.getName());
		sf.setSize(ct.getFileSize(zipFile1));
		sfile.add(sf);
		
		//????????????
		info.setDatainfo("????????????????????1????");
		
		info.setSfile(sfile);
		
		//????xml????
		FileUtil.createFile(ftpPath.toString() + "/" + packageFile,
				JXUtil.Object2Xml(info, true), false, "UTF-8");
		
		//????????????????
		targetFile.delete();
		//??????????????????
		file.delete();
		
		ct.changeStatus(cbd_id+"@"+filePath+"@3");
		this.setMainMessage("??????????");
		this.setNextEventName("lfsearch");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ????/????????
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
	 * ??????????????????????
	 * @return
	 * @throws Exception
	 */
	public static List<UDPackFile> iteratorZipDir() throws Exception{
		List<UDPackFile> udpackFiles = new ArrayList<UDPackFile>();
		String packXmlStr = null;//??????????????????
		ZwhzPackDefine zpdefine = null;//????????????????
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    Calendar cal = null;
	    List<String> dirpaths = null;//??????????????
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
						packXmlStr = FileUtil.readFileByChars(file.getPath(),"UTF-8"); //????????????????
						if(packXmlStr!=null){
							zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //????????????
							udpf.setCreateTime(zpdefine.getTime());
							udpf.setOrgId(zpdefine.getB0111());
							udpf.setOrgName(zpdefine.getB0101());
							udpf.setTransType(zpdefine.getTranstype());
							udpf.setDataInfo(zpdefine.getDatainfo());
							udpf.setFilePublishType(zpdefine.getStype());
							udpf.setIsOther("0");//??other????????
							if("11".equals(zpdefine.getStype())){//????????????10??????????????????????????????
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
			throw new Exception("????????????????:"+e.getMessage());
		}
	}
	
	@PageEvent("checkCBD")
	public int checkCBD(String value){
		
		String[] values = value.split("@");
		//??????????????
		HBSession sess = HBUtil.getHBSession();
		List list = sess.createQuery("from CBDInfo where objectno = '"+values[0]+"'").list();
		if(list.size()>0){
			if("editFile".equals(values[1])){
				
				this.getExecuteSG().addExecuteCode("editFile()");
			}else if("modifyFile".equals(values[1])){
				this.getExecuteSG().addExecuteCode("modifyFile()");
			}
		}else{
			this.setMainMessage("????????????????????????????????????????????");
			return 0;
		}
		return 0;
	}
	
	/**
	 * ????????????????????????????
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("CBDGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException{  //??????????????
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
