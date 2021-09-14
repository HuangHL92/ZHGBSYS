package com.insigma.siis.local.pagemodel.cadremgn.sysmanager;


import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Qryview;

public class ViewCreatePageModel extends PageModel {
	
	
	@Override
	public int doInit() throws RadowException {
		//System.setProperty("sun.net.client.defaultConnectTimeout", "120000");
		//System.setProperty("sun.net.client.defaultReadTimeout", "120000");
		//this.setNextEventName("initX");
		this.setNextEventName("viewListGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("viewListGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException, AppException{
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username =SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		/* 注释权限部分 - 青岛权限
		 * if("system".equals(username)){
			instr="";
		}else{
			instr="  and ( userid='"+userid+"'  or qvid in (select t.viewid from COMPETENCE_USERQRYVIEW t where t.userid = '"+userid+"' and t.type = '1' ) ) ";
		}*/
		String sql="select"
				+ "  qvid, "       //主键
				+ " type, "        //视图类型1 自定义视图2 组合视图 3复杂视图
				+ " viewname, "    //视图名称
				+ " chinesename, " //中文名
				+ " f.uses, "         //用途
				+ " funcdesc"    //功能描述
		+ " from qryview  f"
		+ " where  1=1 "
		+ instr
		+ " and type='1' ";
		this.pageQuery(sql, "SQL", start,limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 查询视图生成列表
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("initX")
	@NoRequiredValidate
	public void initX() throws RadowException, AppException{
//		CommQuery cq=new CommQuery();
//		List<HashMap<String, Object>> list=cq.getListBySQL("select"
//						+ "  qvid, "       //主键
//						+ " type, "        //视图类型1 自定义视图2 组合视图 3复杂视图
//						+ " viewname, "    //视图名称
//						+ " chinesename, " //中文名
//						+ " uses, "         //用途
//						+ " funcdesc "    //功能描述
//				+ " from qryview ");
//    	this.getPageElement("viewListGrid").setValueList(list);
//    	
//    	List<HashMap<String,Object>> arr2=new ArrayList<HashMap<String,Object>>();
//		HashMap<String,Object> map2=new HashMap<String,Object>();
//    	map2.put("code_name", "姓名");
//    	arr2.add(map2);
//    	HashMap<String,Object> map3=new HashMap<String,Object>();
//    	map3.put("code_name", "性别");
//    	arr2.add(map3);
//    	this.getPageElement("codeListGrid").setValueList(arr2);
	}
	
	/**
	 * 视图保存并设置
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("saveandset")
	public int saveandset() throws RadowException{
		try{
			HBSession session = HBUtil.getHBSession();
			//视图名
			String viewname=this.getPageElement("viewname").getValue();
			
			if(viewname==null||viewname.length()==0){
				this.setMainMessage("请输入视图名!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(!viewname.substring(0, 1).matches("^[a-zA-Z]$")){
				this.setMainMessage("视图名需要以字母开头!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//中文名
			String chinesename=this.getPageElement("chinesename").getValue();
//			if(chinesename==null||chinesename.trim().length()==0||chinesename.equals("null")){
//				this.setMainMessage("请输入视图中文名!");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			String userid=PrivilegeManager.getInstance().getCueLoginUser().getId();
			
			//用途
			String use=this.getPageElement("use").getValue();
			//功能描述
			String funcdesc=this.getPageElement("funcdesc").getValue();
			String qvid=this.getPageElement("qvid").getValue();//主键
			if(qvid!=null&&qvid.length()>0){//更新
				
				Qryview qryview=(Qryview)session.get(Qryview.class, qvid);
				//视图名
				qryview.setViewname(viewname);
				//中文名
				qryview.setChinesename(chinesename);
				//用途
				qryview.setUses(use);
				//功能描述
				qryview.setFuncdesc(funcdesc);
				session.update(qryview);
				session.flush();
				//清空设置条件tab页面的全局js变量
				this.getExecuteSG().addExecuteCode("clearParameter();");
			}else{//保存
				String num=HBUtil.getValueFromTab("count(*)", "qryview", "userid='"+userid+"' and viewname='"+viewname.trim()+"' ");
				if(Integer.parseInt(num)>0){
					this.setMainMessage("视图名已存在，请重新输入!");
					return EventRtnType.NORMAL_SUCCESS;
				}
				Qryview qryview=new Qryview();
				//视图名
				qryview.setViewname(viewname);
				//中文名
				qryview.setChinesename(chinesename);
				//用途
				qryview.setUses(use);
				//功能描述
				qryview.setFuncdesc(funcdesc);
				//创建时间
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
				Date date=new Date();
				String createtime=sdf.format(date);
				qryview.setCreatetime(createtime);
				//视图类型1 自定义视图2 组合视图 3复杂视图
				qryview.setType("1");
				//用户id
				qryview.setUserid(userid);
				session.save(qryview);
				qvid=qryview.getQvid();
				
				//新增视图，则为当前创建用户增加权限
				/* 注释权限部分 - 青岛权限
				 * session.createSQLQuery("insert into COMPETENCE_USERQRYVIEW values("
						+ "'"+UUID.randomUUID().toString().replaceAll("-", "")+"',"
						+ "'"+userid+"','"+qvid+"','1')").executeUpdate();*/
				
				session.flush();
				this.getPageElement("qvid").setValue(qryview.getQvid());
				//清空设置条件tab页面的全局js变量
				this.getExecuteSG().addExecuteCode("clearParameter();");
			}
			//刷新视图列表
			this.getExecuteSG().addExecuteCode("refreshList();");
			//跳转到条件设置
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab2');");
			//传递视图主键到iframe页面
			this.getExecuteSG().addExecuteCode("setQvidToIframe('iframeCondition','"+qvid+"');");
			//加载信息集列表
			this.getExecuteSG().addExecuteCode("setTab2();");
			//重置预览界面
//			this.getExecuteSG().addExecuteCode("var divUpdivUp=document.frames['iframePreview'].document.getElementById('divUp');if(divUpdivUp==null){}else{ .style.display='block'};"
//					+ "document.frames['iframePreview'].document.getElementById('divDown').style.display='none';");
			this.getExecuteSG().addExecuteCode("var divUpdivUp2=document.frames['iframePreview'].document.getElementById('divUp');if(divUpdivUp2==null){}else{divUpdivUp2.style.display='block'};"
					+ "var divDowndivDown2=document.frames['iframePreview'].document.getElementById('divDown');if(divDowndivDown2==null){}else{divDowndivDown2.style.display='none'};");
//			//设置保存预览可编辑
			//this.getExecuteSG().addExecuteCode("setDisalbe();");
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 双击已建视图行记录
	 * copy视图信息到页面
	 * @return
	 */
	@PageEvent("savetoqv")
	public int savetoqv(String qvid) throws RadowException{
		try{
			HBSession session = HBUtil.getHBSession();
			Qryview qryview = (Qryview)session.get(Qryview.class, qvid);
			//视图名
			this.getPageElement("viewname").setValue(qryview.getViewname());
			//中文名
			this.getPageElement("chinesename").setValue(qryview.getChinesename());
			//用途
			this.getPageElement("use").setValue(qryview.getUses());
			//功能描述
			this.getPageElement("funcdesc").setValue(qryview.getFuncdesc());
			//主键
			this.getPageElement("qvid").setValue(qryview.getQvid());
			
			//清空设置条件tab页面的全局js变量
			this.getExecuteSG().addExecuteCode("clearParameter();");
			//传递视图主键到iframe页面
			this.getExecuteSG().addExecuteCode("setQvidToIframe('iframeCondition','"+qvid+"');");
			//加载信息集列表
			this.getExecuteSG().addExecuteCode("setTab2();");
			//判断是否已经定义sql
			String id=HBUtil.getValueFromTab("qvid", "qryview", " qrysql is not null and qvid='"+qvid+"' ");
			//重置预览界面
			if(id!=null&&!"".equals(id)&&!"null".equals(id)){//已经生成sql
				//加载列表数据
				this.getExecuteSG().addExecuteCode("loadGridPre('"+qvid+"');");
			}else{//为生成sql
				this.getExecuteSG().addExecuteCode("var divUpdivUp2=document.frames['iframePreview'].document.getElementById('divUp');if(divUpdivUp2==null){}else{divUpdivUp2.style.display='block'};"
						+ "var divDowndivDown2=document.frames['iframePreview'].document.getElementById('divDown');if(divDowndivDown2==null){}else{divDowndivDown2.style.display='none'};");
//				this.getExecuteSG().addExecuteCode("window.setTimeout(\"aaasavetoqv()\",300);function aaasavetoqv(){document.frames['iframePreview'].document.getElementById('divUp').style.display='block';"
//						+ "document.frames['iframePreview'].document.getElementById('divDown').style.display='none';}");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 重置,清空视图信息
	 * @return
	 */
	@PageEvent("clearqv")
	public int clearqv(){
		this.clearDivData("area3");
		this.getExecuteSG().addExecuteCode("clearParameter();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	/**
	 * 删除提示
	 * @param
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("deleteCheck")
	public int deleteCheck(String v) throws RadowException, AppException {
		HBSession session = HBUtil.getHBSession();
		String qvid=this.getPageElement("qvid").getValue();//主键
		if(qvid==null||qvid.length()==0){
			this.setMainMessage("请双击已建视图列表，选中要删除的视图!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.addNextEvent(NextEventValue.YES, "deleteqv");
		//this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");
		this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
		this.setMainMessage("确定删除视图“"+v+"”吗？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	/**
	 * 删除视图
	 * @return
	 */
	@PageEvent("deleteqv")
	@Transaction
	public int deleteqv() throws RadowException{
		try{
			HBSession session = HBUtil.getHBSession();
			String qvid=this.getPageElement("qvid").getValue();//主键
			if(qvid==null||qvid.length()==0){
				this.setMainMessage("请双击已建视图列表，选中要删除的视图!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//信息集
			Qryview qryview = (Qryview)session.get(Qryview.class, qvid);
			session.delete(qryview);
			String viewnametb=qryview.getViewnametb();//视图名
			//删除信息项
			Connection connection = session.connection();
			Statement stmt=connection.createStatement();
			stmt.execute("delete from qryviewfld where qvid='"+qvid+"'");
			//删除条件
			stmt.execute("delete from qryuse where qvid='"+qvid+"'");
			if(viewnametb!=null&&!viewnametb.trim().equals("")&&!viewnametb.equals("null")){
				//删除视图
				String sql="drop view "+viewnametb;
				stmt.execute(sql);
			}
			stmt.execute(" delete from COMPETENCE_USERQRYVIEW  where viewid  ='"+qvid+"' ");//删除权限
			session.flush();
			//刷新视图列表
			this.getExecuteSG().addExecuteCode("refreshList();");
			//清空视图信息
			clearqv();
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}