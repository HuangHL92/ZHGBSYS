package com.insigma.siis.local.pagemodel.weboffice;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.epsoft.config.AppConfig;

public class ExcelViewOfficePageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("TrainingInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
     /**
      * 历史记录
      * */
	@PageEvent("TrainingInfoGrid.dogridquery")
	@NoRequiredValidate
	public int TrainingInfoGridQuery(int start,int limit) throws RadowException{
		HBSession sess =HBUtil. getHBSession ();
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		//if("40288103556cc97701556d629135000f".equals(cueUserid)){
	        String sql = "from WebOffice  where type<>3 order by  ordernumber";
			 List list = sess.createQuery(sql).list();
		     this.setSelfDefResData( this .getPageQueryData(list, start, limit));
		/*}else{
			String sql = "select w.id,w.type,w.filename,w.creattime,w.updatetime from weboffice w left join competence_userweboffice c on w.id=c.officeid where c.userid='"+cueUserid+"' and w.type<>3 and w.selty=0 or c.userid='"+cueUserid+"' and w.type<>3 and w.selty=1";
			List<Object> list = sess.createSQLQuery(sql).list();
		    this.pageQuery(sql, "SQL", start, limit);
		}*/
	    return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("newWord.clck")
	@NoRequiredValidate
	public int newWord(){
		String filename = getRequest().getParameter("filename");
		HBSession sess =HBUtil. getHBSession ();
		this.request.getSession().setAttribute("filename", filename);
	  return EventRtnType.SPE_SUCCESS;
	}


	private PageModel getRequest() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	/**
	 * 删除模板
	 */	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String template ) throws RadowException, AppException {
		//String[] split = aaa.split(",");
	HBSession sess =HBUtil. getHBSession ();
	sess.createSQLQuery("delete from weboffice where id='"+template+"'").executeUpdate();
	sess.flush();
	//String Path = "webOffice/word"; 
	//String path = request.getSession().getServletContext().getRealPath("webOffice/word"); 
	String Path = AppConfig.HZB_PATH+"/WebOffice"; 
	File file = new java.io.File(Path+"\\"+template);
	//System.out.println("-------------------------------"+path+"\\"+split[1]);
	if(file.exists()){
		file.delete();
	}
	this.setNextEventName("TrainingInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 
	 * 模板下载
	 * */
	@PageEvent("expword")
	public int expword(String fileid)throws AppException, RadowException{
		String[] filed = fileid.split(",");
		String filename= filed[1];
		String id = filed[0];
		//String Path = request.getSession().getServletContext().getRealPath("webOffice/word") +"\\"+ id;//路径和模板名
		//Path=request.getSession().getServletContext().getRealPath(Path);
		String Path = AppConfig.HZB_PATH+"/WebOffice/"+id; 
		File file=new File(Path);
		if(file.exists()){
			this.getPageElement("downfile").setValue(Path.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.reloadTree()");
		}else{
			this.setMainMessage("没有找到相应模板");
			return EventRtnType.NORMAL_SUCCESS;
		}
	    
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * 
	 * 模板排序
	 * */
	@PageEvent("Save")
	@Transaction
	@Synchronous(true)
	public int savePersonsort()throws RadowException, AppException{
		List<HashMap<String,String>> list = this.getPageElement("TrainingInfoGrid").getStringValueList();
		//String a0201b = this.getPageElement("a0201b").getValue();	
		//String cura0000 = this.getPageElement("a0000").getValue();	
		//HBSession sess = null;
		try {
			//sess = HBUtil.getHBSession();
			//int i = list.size();
			int i = 1;
			for(HashMap<String,String> m : list){
				String id = m.get("id");
				
				HBUtil.executeUpdate("update weboffice set ordernumber="+i+" where id='"+id+"' ");
				//HBUtil.executeUpdate("update a02 set a0225="+i+" where a0000='"+a0000+"' and a0201b='"+a0201b+"'");
				/*if(a0000.equals(cura0000)){
					this.getPageElement("a0225").setValue(i+"");
					//this.getExecuteSG().addExecuteCode("parent.window.document.getElementById('iframe_workUnits').contentWindow.document.getElementById('a0225').value='"+i+"'");
					
					this.getExecuteSG().addExecuteCode("parent.document.getElementById('a0225').value='"+i+"'");
				}*/
				i++;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		
		//this.getExecuteSG().addExecuteCode("radow.doEvent('gridA01.dogridquery');");
	
		this.setMainMessage("排序成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
