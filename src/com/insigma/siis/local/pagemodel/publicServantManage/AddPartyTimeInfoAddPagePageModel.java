package com.insigma.siis.local.pagemodel.publicServantManage;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.helperUtil.DateUtil;

public class AddPartyTimeInfoAddPagePageModel extends PageModel{
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	public int saveDegrees()throws RadowException, AppException{
		
		String a0141 = this.getPageElement("a0141").getValue();
		String a0144 = this.getPageElement("a0144").getValue();
		String a3921 = this.getPageElement("a3921").getValue();
		String a3927 = this.getPageElement("a3927").getValue();
		
		String a0144All = a0144;
		String a0141_combo = this.getPageElement("a0141_combo").getValue();
		String a3921_combo = this.getPageElement("a3921_combo").getValue();
		String a3927_combo = this.getPageElement("a3927_combo").getValue();
		String A0140 = "";
//		String a0000 = this.getRadow_parent_data();
//		if(a0000==null||"".equals(a0000)){
//			this.setMainMessage("请先保存人员基本信息！");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		if(a0141_combo==null || "".equals(a0141_combo) || "请您选择...".equals(a0141_combo)){
			a0141="";
		}
		if(a3921_combo==null || "".equals(a3921_combo)|| "请您选择...".equals(a3921_combo)){
			a3921="";
		}
		if(a3927_combo==null || "".equals(a3927_combo) || "请您选择...".equals(a3927_combo)){
			a3927="";
		}
		if((a0141==null || "".equals(a0141))&& ((a0144!=null && !"".equals(a0144))|| (a3921!=null && !"".equals(a3921))|| (a3927!=null && !"".equals(a3927)))){
			this.setMainMessage("请先选择为第一党派！");
			return EventRtnType.NORMAL_SUCCESS;
		} else {
			if("02".equals(a0141) || "01".equals(a0141) || "03".equals(a0141)){
				if((a0144==null || "".equals(a0144))){
					this.setMainMessage("请添加参加时间！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String a0107 = this.getPageElement("a0107").getValue();//出生年月
				if(a0107!=null&&!"".equals(a0107)){
					if (a0107.length() == 6) {
						a0107 += "01";
					}
					if (a0144.length() == 6) {
						a0144 += "01";
					}
					if(a0107!=null&&!"".equals(a0107)&&a0144!=null&&!"".equals(a0144)){
						int start = Integer.valueOf(a0107);      	//出生日期
						int end = Integer.valueOf(a0144);			//入党时间
						if (start >= end) {
							this.setMainMessage("参加时间不能早于等于出生日期");
							return EventRtnType.NORMAL_SUCCESS;
						}
						
						a0107 = a0107.replace(".", "").substring(0, 6);
						a0144 = a0144.replace(".", "").substring(0, 6);
					}
				}
				String a0144_sj = a0144.substring(0,4)+"."+a0144.substring(4,6);
				if(a3921==null || "".equals(a3921)){
					if((a3927!=null && !"".equals(a3927))){
						this.setMainMessage("请添加第二党派！");
						return EventRtnType.NORMAL_SUCCESS;
					} else {
						A0140 = a0144_sj ;
					}
				} else {
					if(a3927!=null && !"".equals(a3927)){
						A0140 =  a3921_combo+ "、" + a3927_combo + "(" + a0144_sj +")";
					} else {
						A0140 =  a3921_combo+ "(" + a0144_sj +")";
					}
				}
			} else {
				if("02".equals(a3921) || "01".equals(a3921) || "03".equals(a3921)){
					if((a0144==null || "".equals(a0144))){
						this.setMainMessage("请添加参加时间！");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if("02".equals(a3927) || "01".equals(a3927) || "03".equals(a3927)){
					if((a0144==null || "".equals(a0144))){
						this.setMainMessage("请添加参加时间！");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if(a3921==null || "".equals(a3921)){
					if((a3927!=null && !"".equals(a3927))){
						this.setMainMessage("请添加第二党派！");
						return EventRtnType.NORMAL_SUCCESS;
					} else {
						A0140 = "(" + a0141_combo +")";
					}
				} else {
					if(a3927!=null && !"".equals(a3927)){
						A0140 =  "(" + a0141_combo+ "、" +a3921_combo+ "、" + a3927_combo +")";
					} else {
						A0140 =  "(" + a0141_combo+ "、" +a3921_combo+ ")";
					}
				}
			}
			
		}
		
		if("()".equals(A0140)){
			A0140="";
		}
		
		String a0144_time = "";
		if(a0144 != null && !a0144.equals("")){
			a0144_time = a0144.substring(0,4)+"."+a0144.substring(4,6);
		}
		
		
		//重新拼接入党时间
		String A0140New = "";
		String date = "";
		
		if(a0141.equals("01") || a0141.equals("02") || a0141.equals("03")){
			date = "("+a0144_time+")";
			a0141_combo = "";
		}
		if(a3921.equals("01") || a3921.equals("02") || a3921.equals("03")){
			date = "("+a0144_time+")";
			a3921_combo = "";
		}
		if(a3927.equals("01") || a3927.equals("02") || a3927.equals("03")){
			date = "("+a0144_time+")";
			a3927_combo = "";
		}
		
		
		
		if(a0141_combo != null && !a0141_combo.equals("")){
			A0140New = A0140New + a0141_combo+ "、";
		}
		if(a3921_combo != null && !a3921_combo.equals("")){
			A0140New = A0140New + a3921_combo+ "、";
		}
		if(a3927_combo != null && !a3927_combo.equals("")){
			A0140New = A0140New + a3927_combo+ "、";
		}
		
		if(A0140New != null && !A0140New.equals("")){
			A0140New = A0140New.substring(0, A0140New.length()-1);
			if(date != null && !date.equals("")){
				A0140New = A0140New + date;
			}
		}else{
			A0140New = A0140New + a0144_time;
		}
		
		
		if("()".equals(A0140New)){
			A0140New="";
		}
		
		
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0140').value='"+A0140New+"'");	//入党时间文字
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0141').value='"+a0141+"'");		//政治面貌
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a3921').value='"+a3921+"'");		//第二党派
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a3927').value='"+a3927+"'");		//第三党派
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0144').value='"+a0144All+"'");		//入党时间
		this.getExecuteSG().addExecuteCode("window.parent.Ext.WindowMgr.getActive().close();window.realParent.wrapdiv_a0140onclick()");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public int doInit() throws RadowException {
		
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		this.getExecuteSG().addExecuteCode("v_test('1');");
		this.getExecuteSG().addExecuteCode("v_test('2');");
		this.getExecuteSG().addExecuteCode("v_test('3');");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
