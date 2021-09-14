package com.insigma.siis.local.pagemodel.cadremgn.infmtionquery;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Qryview;
import com.utils.CommonQueryBS;

public class UserDefinedQueryPageModel extends PageModel {
	
	
	public static String jiaoyan = "0";//0是打开 1是点击
	
	@Override
	public int doInit() throws RadowException {
//		System.setProperty("sun.net.client.defaultConnectTimeout", "120000");
//		System.setProperty("sun.net.client.defaultReadTimeout", "120000");
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException{
		String qid = this.getPageElement("subWinIdBussessId").getValue();
		if(qid!=null&&!"".equals(qid)){
			savetoqv(qid);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 保存方案
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("saveschemename")
	@Transaction
	public int saveschemename() throws RadowException{
		try{
			HBSession session = HBUtil.getHBSession();
			//视图名
			String chinesename=this.getPageElement("queryName").getValue();
			//生成的sql
//			String qrysql=this.getPageElement("qrysql").getValue();
//			if(qrysql!=null){
//				qrysql=qrysql.replaceAll("@@", "\'");
//			}
			if(chinesename==null||chinesename.length()==0){
				this.setMainMessage("请输入查询名称!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			String userid=PrivilegeManager.getInstance().getCueLoginUser().getId();
//			String num=HBUtil.getValueFromTab("count(*)", "qryview", "userid='"+userid+"' and viewname='"+viewname.trim()+"' ");
//			if(Integer.parseInt(num)>0){
//				this.setMainMessage("查询名称已存在，请重新输入!");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			
			String qvid=this.getPageElement("qvid").getValue();//主键
			
			
			if("402881ee6af96a5d016af96e62cb0005".equals(qvid)){
				CommonQueryBS cq=new CommonQueryBS();
				HashMap<String, Object>  QVIDmap= cq.getMapBySQL("select QVID from qryview where QVID='402881ee6af96a5d016af96e62cb0005'");
				if(QVIDmap!=null){
					Qryview qryview=(Qryview)session.get(Qryview.class, qvid);
					//视图名
					qryview.setChinesename(chinesename);
					//生成的sql
//					qryview.setQrysql(qrysql);
					session.update(qryview);
					session.flush();
//					//清空设置条件tab页面的全局js变量
//					this.getExecuteSG().addExecuteCode("clearParameter();");
				}else{
					Qryview qryview=new Qryview();
					//视图名
					qryview.setChinesename(chinesename);
					//生成的sql
//					qryview.setQrysql(qrysql);
					//创建时间
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
					Date date=new Date();
					String createtime=sdf.format(date);
					qryview.setCreatetime(createtime);
					//视图类型1 自定义视图2 组合视图 3复杂视图 4自定义方案
					qryview.setType("4");
					//用户id
					qryview.setUserid(userid);
					session.save(qryview);
					qvid=qryview.getQvid();
					session.flush();
					//this.getPageElement("qvid").setValue(qryview.getQvid());
//					//清空设置条件tab页面的全局js变量
//					this.getExecuteSG().addExecuteCode("clearParameter();");
				}
			}else{
				if(qvid!=null&&qvid.length()>0){//更新
					Qryview qryview=(Qryview)session.get(Qryview.class, qvid);
					//视图名
					qryview.setChinesename(chinesename);
					//生成的sql
//					qryview.setQrysql(qrysql);
					session.update(qryview);
					session.flush();
//					//清空设置条件tab页面的全局js变量
//					this.getExecuteSG().addExecuteCode("clearParameter();");
				}else{//保存
					Qryview qryview=new Qryview();
					//视图名
					qryview.setChinesename(chinesename);
					//生成的sql
//					qryview.setQrysql(qrysql);
					//创建时间
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
					Date date=new Date();
					String createtime=sdf.format(date);
					qryview.setCreatetime(createtime);
					//视图类型1 自定义视图2 组合视图 3复杂视图 4自定义方案
					qryview.setType("4");
					//用户id
					qryview.setUserid(userid);
					session.save(qryview);
					qvid=qryview.getQvid();
					session.flush();
					//this.getPageElement("qvid").setValue(qryview.getQvid());
//					//清空设置条件tab页面的全局js变量
//					this.getExecuteSG().addExecuteCode("clearParameter();");
				}
			}
			
			this.getExecuteSG().addExecuteCode("savescheall('"+qvid+"');");
			//this.setMainMessage("保存成功!");
			//刷新视图列表
//			this.getExecuteSG().addExecuteCode("refreshList();");
			//跳转到条件设置
//			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab2');");
			//加载信息集列表
//			this.getExecuteSG().addExecuteCode("setTab2();");
			//设置保存预览可编辑
			//this.getExecuteSG().addExecuteCode("setDisalbe();");
			//传递视图主键到iframe页面
//			this.getExecuteSG().addExecuteCode("setQvidToIframe('iframeCondition','"+qvid+"');");
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("viewListGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException, AppException{
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username =SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			instr="";
		}else{
			//instr="  and ( userid='"+userid+"'  or qvid in (select t.viewid from COMPETENCE_USERQRYVIEW t where t.userid = '"+userid+"' and t.type = '1' ) ) ";
		}
		String sql="select"
				+ "  qvid, "       //主键
				+ " type, "        //视图类型1 自定义视图2 组合视图 3复杂视图
				+ " viewname, "    //视图名称
				+ " chinesename, " //中文名
				+ " q.uses, "         //用途
				+ " funcdesc "    //功能描述
		+ " from qryview q "
		+ " where  1=1 "
		+ instr
		+ " and type='4' "
		+ " order by createtime asc ";
		this.pageQuery(sql, "SQL", start,limit);
		return EventRtnType.SPE_SUCCESS;
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
			//中文名
			this.getPageElement("queryName").setValue(qryview.getChinesename());
			//主键
			this.getPageElement("qvid").setValue(qvid);
			this.getPageElement("qrysql").setValue(qryview.getQrysql());
			
			//清空设置条件tab页面的全局js变量
			this.getExecuteSG().addExecuteCode("document.getElementById('iframeCondition').contentWindow.clearConditionPar();");
			//传递视图主键到iframe页面
			this.getExecuteSG().addExecuteCode("document.getElementById('iframeCondition').contentWindow.setQvId('"+qvid+"');");
			//加载信息集列表
			this.getExecuteSG().addExecuteCode("document.getElementById('iframeCondition').contentWindow.initList();");
			//判断是否已经定义sql
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab1');");
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/*@PageEvent("initX")
	@NoRequiredValidate
	public void initX() throws RadowException{
		List<HashMap<String,Object>> arr=new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map=new HashMap<String,Object>();
    	map.put("table_name", "人员信息集");
    	arr.add(map);
    	this.getPageElement("tableListGrid").setValueList(arr);
    	
    	List<HashMap<String,Object>> arr2=new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map2=new HashMap<String,Object>();
    	map2.put("code_name", "姓名");
    	arr2.add(map2);
    	HashMap<String,Object> map3=new HashMap<String,Object>();
    	map3.put("code_name", "性别");
    	arr2.add(map3);
    	this.getPageElement("codeListGrid").setValueList(arr2);
	}*/
	
	@PageEvent("doDel")
	public int doDel(String value) {
		HBUtil.getHBSession().createSQLQuery("delete from qryview where qvid='"+value+"'").executeUpdate();
		this.getExecuteSG().addExecuteCode("radow.doEvent('viewListGrid.dogridquery')");
		this.setMainMessage("删除成功");
		return EventRtnType.NORMAL_SUCCESS;
	}

}