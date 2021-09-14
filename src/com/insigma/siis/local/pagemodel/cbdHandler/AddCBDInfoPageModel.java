package com.insigma.siis.local.pagemodel.cbdHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Transaction;
import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.CBDInfo;

public class AddCBDInfoPageModel extends PageModel {

	public static String uuid = "";
	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//设置页面属性
		this.getPageElement("cbd_userid").setValue(user.getId());
		this.getPageElement("cbd_username").setValue(user.getName());
		
		String value = this.getRadow_parent_data();
		//处理父页面传递过来的名字和编码
		if(value.contains("MZ")){
			String ids = value.split("@")[0];
			String names = value.split("@")[1];
			this.getPageElement("cbd_personname").setValue(names);
			this.getPageElement("cbd_personid").setValue(ids);
		}else if("New".equals(value)){
			this.getPageElement("flag").setValue(value);
		}else{
			//处理父页面传递过来的参数
			String value1 ="";
			String value2 ="";
			if(!"".equals(value) && value != null){
				uuid=value.split("@")[0];
				value1=value.split("@")[1];
				value2=value.split("@")[2];
				
			}
			if(!"".equals(value) && value != null && (("UU".equals(value1)) || ("GU".equals(value1)) ) ){
				HBSession sess = HBUtil.getHBSession();
				StringBuffer hql = new StringBuffer();
				hql.append("from CBDInfo t where 1=1 ");
				//当value1为 UU 时，操作是上报呈报单页面的修改呈报单功能，当value1为GU时，操作是接收页面的修改本级呈报单功能
				if("UU".equals(value1)){
					hql.append(" and t.cbd_id='"+uuid+"'");
				}else if("GU".equals(value1)){
					hql.append(" and t.objectno = '"+uuid+"' and t.cbd_path = '3'");
				}
				List<CBDInfo> cbdinfo =  sess.createQuery(hql.toString()).list();
				if(cbdinfo.size()>0){
					if("GU".equals(value1)){
						this.getExecuteSG().addExecuteCode("control()");
					}
					this.getPageElement("cdb_word_year_no").setValue(cbdinfo.get(0).getCdb_word_year_no());
//					this.getPageElement("cbd_word").setValue(cbdinfo.get(0).getCbd_word());
//					this.getPageElement("cbd_year").setValue(cbdinfo.get(0).getCbd_year());
//					this.getPageElement("cbd_no").setValue(cbdinfo.get(0).getCbd_no());
					this.getPageElement("cbd_leader").setValue(cbdinfo.get(0).getCbd_leader());
					this.getPageElement("cbd_organ").setValue(cbdinfo.get(0).getCbd_organ());
					this.getPageElement("cbd_text").setValue(cbdinfo.get(0).getCbd_text());
					this.getPageElement("cbd_date1").setValue(cbdinfo.get(0).getCbd_date1());
					this.getPageElement("cbd_personname").setValue(cbdinfo.get(0).getCbd_personname());
					this.getPageElement("cbd_personid").setValue(cbdinfo.get(0).getCbd_personid());
					this.getPageElement("cbd_id").setValue(cbdinfo.get(0).getCbd_id());
					this.getPageElement("cbd_cbr").setValue(cbdinfo.get(0).getCbd_cbr());
				}else if(cbdinfo.size()==0&&"GU".equals(value1)){
					this.getPageElement("cbd_personname").setValue(value2);
					this.getExecuteSG().addExecuteCode("control()");
				}
			}
		}

		return 0;
	}
	
	@PageEvent("makeCBD.onclick")
	public int makeCBD() throws RadowException{
		
		//获取页面的值
		String cdb_word_year_no = this.getPageElement("cdb_word_year_no").getValue();
		String cbd_leader = this.getPageElement("cbd_leader").getValue();
		String cbd_organ = this.getPageElement("cbd_organ").getValue();
		String cbd_text = this.getPageElement("cbd_text").getValue();
		String cbd_date1 = this.getPageElement("cbd_date1").getValue();
		String cbd_personname = this.getPageElement("cbd_personname").getValue();
		String cbd_personid = this.getPageElement("cbd_personid").getValue();
		String cbd_cbr = this.getPageElement("cbd_cbr").getValue();
		if(StringUtil.isEmpty(cdb_word_year_no)){
			this.setMainMessage("请输入呈报单审批字_年_号");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtil.isEmpty(cbd_leader)){
			this.setMainMessage("请输入领导称谓");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtil.isEmpty(cbd_organ)){
			this.setMainMessage("请输入承办单位");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtil.isEmpty(cbd_text)){
			this.setMainMessage("请输入呈报单正文");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtil.isEmpty(cbd_date1)){
			this.setMainMessage("请输入呈报单日期");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		if(StringUtil.isEmpty(cbd_personname)){
//			this.setMainMessage("请选择呈报单人员");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		
		this.setNextEventName("saveCBDInfo");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveCBDInfo")
	public int saveCBDInfo() throws RadowException{

		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//获取时间
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		
		CBDInfo cbdinfo = new CBDInfo();
		String cbdid = this.getPageElement("cbd_id").getValue();
		if(cbdid != null && !"".equals(cbdid)){
			cbdinfo.setCbd_id(cbdid);
		}else{
			
			String uuid = UUID.randomUUID().toString();
			cbdinfo.setCbd_id(uuid);
		}
		cbdinfo.setCbd_date(createdate);
		cbdinfo.setCbd_date1(this.getPageElement("cbd_date1").getValue().trim());
		cbdinfo.setCdb_word_year_no(this.getPageElement("cdb_word_year_no").getValue().trim());
		cbdinfo.setCbd_leader(this.getPageElement("cbd_leader").getValue().trim());
		cbdinfo.setCbd_organ(this.getPageElement("cbd_organ").getValue().trim());
		cbdinfo.setCbd_text(this.getPageElement("cbd_text").getValue().trim());
		cbdinfo.setCbd_personname(this.getPageElement("cbd_personname").getValue().trim());
		cbdinfo.setCbd_personid(this.getPageElement("cbd_personid").getValue().trim());
		cbdinfo.setCbd_cbr(this.getPageElement("cbd_cbr").getValue().trim());
		cbdinfo.setCbd_userid(user.getId().trim());
		cbdinfo.setCbd_username(user.getName().trim());
		cbdinfo.setStatus("0");
		String objectno = this.getPageElement("objectno").getValue();
		if(objectno != null && !"".equals(objectno)){
			cbdinfo.setObjectno(objectno);
		}else{
			//cbdinfo.setObjectno(uuid);
		}
		//设置呈报单类型  1：上报页面的本级呈报单；3：接收呈报单页面的本级呈报单
		cbdinfo.setCbd_path("1");
		if(!"New".equals(this.getRadow_parent_data())){
			
			if(!"".equals(this.getRadow_parent_data()) && this.getRadow_parent_data()!= null){
				
				if("GU".equals(this.getRadow_parent_data().split("@")[1])){
					cbdinfo.setCbd_path("3");
					cbdinfo.setObjectno(uuid);
				}
			}
		}
		
		//处理申请人员信息，并设置呈报单名称的值
		String cbd_personname = this.getPageElement("cbd_personname").getValue();
		String[] cbd_personnames = cbd_personname.split(",");
		if(cbd_personnames.length>1){
			cbdinfo.setCbd_name("对"+cbd_personnames[0]+"等"+cbd_personnames.length+"名同志进行公务员登记的请示");
		}else{
			cbdinfo.setCbd_name("对"+cbd_personnames[0]+"进行公务员登记的请示");
		}
		
		HBSession sess = HBUtil.getHBSession();
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.saveOrUpdate(cbdinfo);
		ts.commit();
		if(!"New".equals(this.getRadow_parent_data())){
			
			if(!"".equals(this.getRadow_parent_data()) && this.getRadow_parent_data()!= null){
				if("GU".equals(this.getRadow_parent_data().split("@")[1])){
					this.setMainMessage("录入呈报单成功！");
					this.closeCueWindow("editCBD");
					this.getExecuteSG().addExecuteCode("parent.radow.doEvent('reload')");
				}
				
			}
		}
		this.setMainMessage("录入呈报单成功！");
		//this.createPageElement("CBDGrid", "grid", true).reload();
		this.closeCueWindow("editCBD");
		//this.getExecuteSG().addExecuteCode("parent.radow.doEvent('bjBtn.onclick')");
		String flag = this.getPageElement("flag").getValue();
		if("New".equals(flag)){
			//将选中的人员的值设置 到父页面中
			this.createPageElement("cbd_id", ElementType.HIDDEN, true).setValue(cbdinfo.getCbd_id());
			this.createPageElement("cbd_name", ElementType.HIDDEN, true).setValue(cbdinfo.getCbd_name());
			this.createPageElement("cbd_personname", ElementType.HIDDEN, true).setValue(cbdinfo.getCbd_personname());
			this.createPageElement("cbd_personid", ElementType.HIDDEN, true).setValue(cbdinfo.getCbd_personid());
			this.getExecuteSG().addExecuteCode("parent.radow.doEvent('reloadGrid')");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
