package com.insigma.siis.local.pagemodel.repandrec.local;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.kingbs.KingBSImpUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.DataOrgImpPageModel;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class KingbsZZBLoadPageModel extends PageModel {
	

	
	@Override
	public int doInit() throws RadowException {
		this.getPageElement("imprecordid").setValue(UUID.randomUUID().toString().replace("-", ""));
		this.setNextEventName("kingtest");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("kingtest")
	@NoRequiredValidate
	public int kingtest() throws RadowException {
		try {
			List<Dataexchangeconf> list = HBUtil.getHBSession().createQuery("from Dataexchangeconf").list();
			if (list ==null || list.size()==0) {
				this.setMainMessage("连接标准版数据库失败，请检查配置信息。");
				return EventRtnType.FAILD;
			}
			KingBSImpUtil.initKingbs(list.get(0));
		} catch (Exception e) {
			//e.printStackTrace();
			this.getExecuteSG().addExecuteCode("document.getElementById('s111').disabled=true;");
			this.setMainMessage("不能连接标准版数据库，请检查应用是否安装完好或数据库服务已开启。");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	////????????????????
	/**
	 * 查询文件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("zzbsearch")
	public int lfsearch() throws RadowException{
		String ZZB3_FILE = "D:/KingbsData";
		File file = new File(ZZB3_FILE);
		List<HashMap<String, Object>> gridList = new ArrayList<HashMap<String,Object>>();
		Grid grid = (Grid)this.createPageElement("Fgrid", ElementType.GRID, false);
		try {
			
			if(file.exists()){
				File[] subFiles = file.listFiles();
				if(subFiles!=null && subFiles.length>0){
					for (int i = 0; i < subFiles.length; i++) {
						File file0= subFiles[i];
						String name = file0.getName();
						if(name.substring(name.lastIndexOf(".") + 1).equalsIgnoreCase("zzb3")){
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("fname", name);
							map.put("fabsolutepath", file0.getAbsolutePath().replace("\\", "/"));
							gridList.add(map);
						}
					}
				}
			}
			if(gridList.size()>0){
				grid.setValueList(gridList);
			}else{
				grid.setValueList(new ArrayList<HashMap<String,Object>>());
			}
		} catch (Exception e) {
			throw new RadowException("检索目录失败:"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("fabsolutepath.ontriggerclick")
	@NoRequiredValidate
	public int searchDept(String name) throws RadowException{
//		this.openWindow("winFile", "pages.repandrec.local.KingbsFile");
		this.getExecuteSG().addExecuteCode("$h.openWin('winFile','pages.repandrec.local.KingbsFile','选择导入机构',650,250,'qq','"+request.getContextPath()+"');");

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("Fgrid.dogridquery")
	@NoRequiredValidate          
	public int dogridQuery(int start,int limit) throws RadowException{
		String id = this.getPageElement("imprecordid").getValue();
		StringBuffer sql = new StringBuffer("select PROCESS_NAME name," +
				"PROCESS_STATUS status,PROCESS_INFO info from IMP_PROCESS where ");
		sql.append(" IMPRECORDID='" + id +"'");
		sql.append(" order by PROCESS_TYPE asc");
		this.pageQuery(sql.toString(),"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 刷新
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btnsx")
	@NoRequiredValidate
	public int btnsxOnClick()throws RadowException{
		String id = this.getPageElement("imprecordid").getValue();
		Imprecord r = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, id);
		if(r != null){
			this.getPageElement("psncount").setValue(r.getPsncount()+"");
			CommonQueryBS.systemOut(r.getPsncount()+"");
			this.getPageElement("orgcount").setValue(r.getOrgcount()+"");
			CommonQueryBS.systemOut(r.getOrgcount()+"");
		}
		this.setNextEventName("Fgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	 public static void appendFileContent(String fileName, String content) {
         try {
             //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
             writer.write(content);
             writer.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
	 
	 private String getRootPath() {
			String classPath = this.getClass().getClassLoader().getResource("/").getPath(); 
			try {
				classPath = URLDecoder.decode(classPath, "GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String rootPath  = ""; 
			if("\\".equals(File.separator)){   
				rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
				rootPath = rootPath.replace("/", "\\"); 
			} 
			if("/".equals(File.separator)){   
				rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
				rootPath = rootPath.replace("\\", "/"); 
			}
			return rootPath;
	}
	 /**
		 * 刷新
		 * @return
		 * @throws RadowException
		 */
		@PageEvent("docheck2")
		@NoRequiredValidate
		public int docheck2()throws RadowException{
			String id = this.getPageElement("imprecordid").getValue();
			Imprecord r = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, id);
			if(r != null){
				this.getPageElement("psncount").setValue(r.getPsncount()+"");
				CommonQueryBS.systemOut(r.getPsncount()+"");
				this.getPageElement("orgcount").setValue(r.getOrgcount()+"");
				CommonQueryBS.systemOut(r.getOrgcount()+"");
			}
			this.setNextEventName("Fgrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
}
