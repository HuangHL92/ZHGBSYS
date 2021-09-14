package com.insigma.siis.local.pagemodel.customquery;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GroupPageModelbak extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		
		return 0;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException{		
		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMMdd");
		//PageElement pe= this.getPageElement("isOnDuty");
		//pe.setValue("1");
        String datestr = myFmt1.format(new Date());
        this.getPageElement("jiezsj").setValue(datestr);
        this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4)+"."+datestr.substring(4, 6));
        return 0;
	}
	
	/**
	 * 查询（注：范围查询中代码的大小与字面逻辑的高低正好相反，所以判断逻辑也是相反的处理）
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("mQueryonclick")
	public int query() throws RadowException, AppException{
		
		//this.getPageElement("checkedgroupid").setValue(null);
		
		this.request.getSession().setAttribute("queryType", "1");
		//StringBuffer sb = new StringBuffer("");
		
		
		String userID = SysManagerUtils.getUserId();
		this.request.getSession().setAttribute("queryTypeEX", "新改查询方式");
		String b01String = (String)this.getPageElement("SysOrgTreeIds").getValue();
		/*b01String=b01String.replace("|", "'").replace("@", ",");//组织机构
		String [] arrB01=b01String.split(",");
		if(arrB01!=null && arrB01.length>200){
			throw new AppException("不能选择超过200个单位！");
		}*/
		StringBuffer a02_a0201b_sb = new StringBuffer("");
        StringBuffer cu_b0111_sb = new StringBuffer("");
        /*if(!"".equals(b01String)&&b01String!=null){
        	a02_a0201b_sb.append(" and a02.a0201b in ("+b01String+") ");
        	cu_b0111_sb.append(" and cu.b0111 in ("+b01String+") ");
            this.getPageElement("a0201b").setValue(b01String);					
        }*/
		//选择机构
        //String tree = this.getPageElement("SysOrgTree").getValue();
        if(b01String!=null && !"".equals(b01String)){//tree!=null && !"".equals(tree.trim()
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" and (1=2 ");
				cu_b0111_sb.append(" and (1=2 ");
			}
			Iterator<String> it = jsonObject.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {
				String nodeid = it.next(); 
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");//[机构名称，是否包含下级，是否本级选中]
				if("true".equals(types[1])&&"true".equals(types[2])){
					//a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+"%' ");
					a02_a0201b_sb.append(" or "+CommSQL.subString("a02.a0201b", 1, nodeid.length(), nodeid));
					//cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+"%' ");
					cu_b0111_sb.append(" or "+CommSQL.subString("cu.b0111", 1, nodeid.length(), nodeid));
				}else if("true".equals(types[1])&&"false".equals(types[2])){
					//a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+".%' ");
					a02_a0201b_sb.append(" or "+CommSQL.subString2("a02.a0201b", 1, nodeid.length(), nodeid));
					//cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+".%' ");
					cu_b0111_sb.append(" or "+CommSQL.subString2("cu.b0111", 1, nodeid.length(), nodeid));
				}else if("false".equals(types[1])&&"true".equals(types[2])){
					a02_a0201b_sb.append(" or a02.a0201b = '"+nodeid+"' ");
					cu_b0111_sb.append(" or cu.b0111 = '"+nodeid+"' ");
				}
			}
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" ) ");
				cu_b0111_sb.append(" ) ");
			}
        }
		//StringBuffer sb = new StringBuffer("");
		//sb.append(getNewSQLComQuery(userID));
		String a0101 = this.getPageElement("a0101A").getValue();//人员姓名
		String a0184 = this.getPageElement("a0184A").getValue().toUpperCase();//身份证号
		String a0160 = this.getPageElement("a0160").getValue();//人员类别
		String a0163 = this.getPageElement("a0163").getValue();//人员状态
		String ageS = this.getPageElement("ageA").getValue();//起始年龄
		String ageE = this.getPageElement("age1").getValue();//结束年龄
		String female = this.getPageElement("female").getValue();//性别是否女
		if(female.equals("1")){
			female = "2";
		}
		String minority = this.getPageElement("minority").getValue();//是否少数民族
		String nonparty = this.getPageElement("nonparty").getValue();//是否非中共党员	
		String duty = this.getPageElement("duty").getValue();//起始职务层次
		String duty1 = this.getPageElement("duty1").getValue();//结束职务层次
		String a0221aS = this.getPageElement("a0221aS").getValue();//职务等级
		String a0221aE = this.getPageElement("a0221aE").getValue();//职务等级			
		String a0192dS = this.getPageElement("a0192dS").getValue();//职务职级
		String a0192dE = this.getPageElement("a0192dE").getValue();//职务职级			
		String dutynow = this.getPageElement("dutynow").getValue();//职务层次起始日期
		String dutynow1 = this.getPageElement("dutynow1").getValue();//职务层次结束日期
		String a0219 = this.getPageElement("a0219").getValue();//职务类别
		String edu = this.getPageElement("edu").getValue();//起始学历
		String edu1 = this.getPageElement("edu1").getValue();//结束学历
		String jiezsj = this.getPageElement("jiezsj").getValue();//年龄年限计算截止
		String radioC = this.getPageElement("radioC").getValue();
		String allday = this.getPageElement("allday").getValue();//是否全日制
		String sql = this.getPageElement("sql").getValue();
		//年龄范围转换成日期范围
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMdd" );
		Calendar calendar=Calendar.getInstance();   
		
		if(jiezsj==null||"".equals(jiezsj)){
			jiezsj = sdf.format(new Date());
		}
		String dstrat = "";
		String dend = "";
		try {
			if(jiezsj.length()==6){
				jiezsj = jiezsj + "01";
			}
			Date djiezsj = sdf.parse(jiezsj);
			calendar.setTime(djiezsj); 
			int iages=0,iagee=200;
			if(ageS!=null&&!"".equals(ageS)){
				iages = Integer.parseInt(ageS);
				calendar.add(Calendar.YEAR, -iages);
				dend = sdf.format(calendar.getTime());
				calendar.setTime(djiezsj); 
			}
			
			if(ageE!=null&&!"".equals(ageE)){
				iagee = Integer.parseInt(ageE)+1;
				calendar.add(Calendar.YEAR, -iagee);
				dstrat = sdf.format(calendar.getTime());
			}
			if(iagee < iages){
				this.setMainMessage("年龄范围错误！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (NumberFormatException e2) {
			this.setMainMessage("年龄格式错误！");
			return EventRtnType.NORMAL_SUCCESS;
		}catch (Exception e1) {
			this.setMainMessage("截止日期格式错误！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!"1".equals(allday)){
//						allday="2";
			allday="";  //根据需求改为，未勾选则不添加  学历是否全日制条件 （bug编号：708）  
		}
		if(!"1".equals(radioC)){
			if("".equals(sql)||sql==null)
				throw new AppException("未进行过查询请先查询!");
		}
		//范围校验 start mengl 20160630
		/*if(!StringUtil.isEmpty(age) && !StringUtil.isEmpty(age1)){
			
			try {
				if(Integer.parseInt(age)>Integer.parseInt(age1)){
					throw new AppException("年龄范围不正确，请检查！");
				}
			} catch (NumberFormatException e) {
				throw new AppException("年龄数值不正确，请检查！");
			}
		}*/
		
		if(!StringUtil.isEmpty(duty) && !StringUtil.isEmpty(duty1)){
			CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", duty);
			CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", duty1);
			if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
				throw new AppException("职务层次范围不属于同一类别，请检查！");
			}
			//职务层次 值越小 字面意思越高级
			if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
				throw new AppException("职务层次范围不正确，请检查！");
			}
		}
		
		if(!StringUtil.isEmpty(dutynow) && !StringUtil.isEmpty(dutynow1)){
			if(dutynow.compareTo(dutynow1)>0){
				throw new AppException("任现职务层次时间范围不正确，请检查！");
			}
		}

		if(!StringUtil.isEmpty(a0221aS) && !StringUtil.isEmpty(a0221aE)){
			CodeValue a0221aSCodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aS);
			CodeValue a0221aECodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aE);
			
			//TODO 职务等级范围是否校验待定
			if(!a0221aSCodeValue.getSubCodeValue().equalsIgnoreCase(a0221aECodeValue.getSubCodeValue())){
				throw new AppException("职务等级范围不属于同一类别，请检查！");
			}
			//职务等级 值越小 字面意思越高级
			if(a0221aSCodeValue.getCodeValue().compareTo(a0221aECodeValue.getCodeValue())<0){
				throw new AppException("职务等级范围不正确，请检查！");
			}
		}
		
		if(!StringUtil.isEmpty(a0192dS) && !StringUtil.isEmpty(a0192dE)){
			CodeValue a0192dSSCodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dS);
			CodeValue a0192dECodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dE);
			if(!a0192dSSCodeValue.getSubCodeValue().equalsIgnoreCase(a0192dECodeValue.getSubCodeValue())){
				throw new AppException("职级范围不属于同一类别，请检查！");
			}
			//职级 值越小 字面意思越高级
			if(a0192dSSCodeValue.getCodeValue().compareTo(a0192dECodeValue.getCodeValue())>0){
				throw new AppException("职级范围不正确，请检查！");
			}
		}
		
		if(!StringUtil.isEmpty(edu) && !StringUtil.isEmpty(edu1)){
			CodeValue eduCodeValue =RuleSqlListBS.getCodeValue("ZB64", edu);
			CodeValue edu1CodeValue =RuleSqlListBS.getCodeValue("ZB64", edu1);
			//职级 值越小 字面意思越高级
			if(eduCodeValue.getCodeValue().compareTo(edu1CodeValue.getCodeValue())<0){
				throw new AppException("学历范围不正确，请检查！");
			}
		}
		
		
		//范围校验 end
		StringBuffer a01sb = new StringBuffer("");
		if (!a0101.equals("")){
			a01sb.append(" and ");
			a01sb.append("a01.a0101 like'"+a0101+"%'");
		}
		if (!a0184.equals("")){
			a01sb.append(" and ");
			a01sb.append("a01.a0184 like'"+a0184+"%'");
		}
		if (!a0160.equals("")){
			a01sb.append(" and ");
			a01sb.append("a01.a0160 ='"+a0160+"'");
		}
		/*if (!a0163.equals("")){
			a01sb.append(" and ");
			a01sb.append("a01.a0163='"+a0163+"'");
		}*/
		if(jiezsj.equals("")){
			jiezsj = DateUtil.getcurdate();
		}
		if (!dstrat.equals("")){
			a01sb.append(" and ");
			a01sb.append("a01.a0107>='"+dstrat+"'");
		}
		if (!dend.equals("")){
			a01sb.append(" and ");
			a01sb.append(" a01.a0107<='"+dend+"'");
		}
		if (!female.equals("0")){
			a01sb.append(" and ");
			a01sb.append("a01.a0104='"+female+"'");
		}
		if (!minority.equals("0")){
			a01sb.append(" and ");
			a01sb.append("a01.a0117!='01'");
		}
		if (nonparty.equals("1")){
			a01sb.append(" and ");
			a01sb.append("a01.a0141!='01'");			
		}
        if(!"".equals(duty1)){
        	a01sb.append(" and ");
        	a01sb.append("a01.a0148>='"+duty1+"'");		
        }
        if(!"".equals(duty)){
        	a01sb.append(" and ");
        	a01sb.append("a01.a0148<='"+duty+"'");		
        }
        if(!"".equals(a0192dE)){
        	a01sb.append(" and ");
        	a01sb.append("a01.a0192d>='"+a0192dE+"'");		
        }
        if(!"".equals(a0192dS)){
        	a01sb.append(" and ");
        	a01sb.append("a01.a0192d<='"+a0192dS+"'");		
        }       
        StringBuffer a02sb = new StringBuffer("");
        if(!"".equals(a0221aS)){
        	a02sb.append(" and ");
        	a02sb.append("a02.a0221a<='"+a0221aS+"'");
        }
        if(!"".equals(a0221aE)){
        	a02sb.append(" and ");
        	a02sb.append("a02.a0221a>='"+a0221aE+"'");
        }   
        if(!"".equals(dutynow)){
        	a02sb.append(" and ");
        	a02sb.append("a02.a0288>='"+dutynow+"'");
        }
        if(!"".equals(dutynow1)){
        	a02sb.append(" and ");
        	a02sb.append(" a02.a0288<='"+dutynow1+"'");
        }
        if(!"".equals(a0219)){
        	a02sb.append(" and ");
        	a02sb.append(" a02.a0219='"+a0219+"' ");
        }
        
        
        
        StringBuffer orther_sb = new StringBuffer("");
        if(!"".equals(edu)&&"".equals(edu1)){
        	orther_sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B <='"+edu+"'");
            if(!"".equals(allday)){
            	orther_sb.append(" and ");
            	orther_sb.append("a0837 = '"+allday+"'");		
            }
            orther_sb.append(")");
        }
        if(!"".equals(edu1)&&"".equals(edu)){
        	orther_sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B >='"+edu1+"'");	
            if(!"".equals(allday)){
            	orther_sb.append(" and ");
            	orther_sb.append("a0837 = '"+allday+"'");		
            }
            orther_sb.append(")");
        }
        if(!"".equals(edu1)&&!"".equals(edu)){
        	orther_sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B between '"+edu1+"' and '"+edu+"'");
            if(!"".equals(allday)){
            	orther_sb.append(" and ");
            	orther_sb.append("a0837 = '"+allday+"'");		
            }
            orther_sb.append(")");
        }
        
        String finalsql = CommSQL.getCondiQuerySQL(userID,a01sb,a02sb,a02_a0201b_sb,cu_b0111_sb,orther_sb,a0163,"0");
        
        finalsql = "select * from ("+finalsql+") a01 where 1=1 ";
        this.getPageElement("sql").setValue(finalsql);
        this.getExecuteSG().addExecuteCode("realParent.document.getElementById('sql').value=document.getElementById('sql').value");
        Map<String, Boolean> m = new HashMap<String, Boolean>();
        m.put("paixu", true);
        this.request.getSession().setAttribute("queryConditionsCQ",m);
        
        this.getExecuteSG().addExecuteCode("realParent.document.getElementById('checkedgroupid').value=''");
        this.getExecuteSG().addExecuteCode("realParent.document.getElementById('tabn').value='tab2'");
		//this.setNextEventName("peopleInfoGrid.dogridquery");
        this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('peopleInfoGrid.dogridquery');");
        this.getExecuteSG().addExecuteCode("collapseGroupWin();");
        
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	CustomQueryBS cbBs=new CustomQueryBS();
	/**
	 * 保存：（注：范围查询中代码的大小与字面逻辑的高低正好相反，所以判断逻辑也是相反的处理）
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveCon.onclick")
    @Transaction
	public int saveCon() throws RadowException, AppException{
		StringBuffer sb = new StringBuffer("");
		StringBuffer b01Ids = new StringBuffer("");
		String b01String = (String)this.getPageElement("SysOrgTreeIds").getValue();//机构信息
		b01String=b01String.replace("|", "'").replace("@", ",");
		//b01String=b01String.replace("{", "").replace("}", "");
			String a0101 = this.getPageElement("a0101A").getValue();//人员姓名
			String a0184 = this.getPageElement("a0184A").getValue().toUpperCase();//人员身份证
			String a0160 = this.getPageElement("a0160").getValue();//人员类别
			String a0163 = this.getPageElement("a0163").getValue();//人员状态
			String age = this.getPageElement("ageA").getValue();//起始年龄
			String age1 = this.getPageElement("age1").getValue();//结束年龄
			String female = this.getPageElement("female").getValue();//性别是否女
			if(female.equals("1")){
				female = "2";
			}
			String minority = this.getPageElement("minority").getValue();//是否少数民族
			String nonparty = this.getPageElement("nonparty").getValue();//是否非中共党员	
			String duty = this.getPageElement("duty").getValue();//起始职务层次
			String duty1 = this.getPageElement("duty1").getValue();//结束职务层次
			String dutynow = this.getPageElement("dutynow").getValue();//职务层次起始日期
			String dutynow1 = this.getPageElement("dutynow1").getValue();//职务层次结束日期
			String a0219 = this.getPageElement("a0219").getValue();//职务类别
			String a0221aS = this.getPageElement("a0221aS").getValue();//职务等级
			String a0221aE = this.getPageElement("a0221aE").getValue();//职务等级			
			String a0192dS = this.getPageElement("a0192dS").getValue();//职务职级
			String a0192dE = this.getPageElement("a0192dE").getValue();//职务职级
			String edu = this.getPageElement("edu").getValue();//起始学历
			String edu1 = this.getPageElement("edu1").getValue();//结束学历

			String allday = this.getPageElement("allday").getValue();//是否全日制
			if(!"1".equals(allday)){
//				allday="2";
				allday="";  //根据需求改为，未勾选则不添加  学历是否全日制条件 （bug编号：708）  
			}
			
			//范围校验 start mengl 20160630
			if(!StringUtil.isEmpty(age) && !StringUtil.isEmpty(age1)){
				if(Integer.parseInt(age)>Integer.parseInt(age1)){
					throw new AppException("年龄范围不正确，请检查！");
				}
			}
			
			if(!StringUtil.isEmpty(duty) && !StringUtil.isEmpty(duty1)){
				CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", duty);
				CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", duty1);
				if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
					throw new AppException("职务层次范围不属于同一类别，请检查！");
				}
				//职务层次 值越小 字面意思越高级
				if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
					throw new AppException("职务层次范围不正确，请检查！");
				}
			}
			
			if(!StringUtil.isEmpty(dutynow) && !StringUtil.isEmpty(dutynow1)){
				if(dutynow.compareTo(dutynow1)>0){
					throw new AppException("任现职务层次时间范围不正确，请检查！");
				}
			}

			if(!StringUtil.isEmpty(a0221aS) && !StringUtil.isEmpty(a0221aE)){
				CodeValue a0221aSCodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aS);
				CodeValue a0221aECodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aE);
				//TODO 职务等级范围是否校验 待定
				if(!a0221aSCodeValue.getSubCodeValue().equalsIgnoreCase(a0221aECodeValue.getSubCodeValue())){
					throw new AppException("职务等级范围不属于同一类别，请检查！");
				}
				//职务等级 值越小 字面意思越高级
				if(a0221aSCodeValue.getCodeValue().compareTo(a0221aECodeValue.getCodeValue())<0){
					throw new AppException("职务等级范围不正确，请检查！");
				}
			}
			
			if(!StringUtil.isEmpty(a0192dS) && !StringUtil.isEmpty(a0192dE)){
				CodeValue a0192dSSCodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dS);
				CodeValue a0192dECodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dE);
				if(!a0192dSSCodeValue.getSubCodeValue().equalsIgnoreCase(a0192dECodeValue.getSubCodeValue())){
					throw new AppException("职级范围不属于同一类别，请检查！");
				}
				//职级 值越小 字面意思越高级
				if(a0192dSSCodeValue.getCodeValue().compareTo(a0192dECodeValue.getCodeValue())>0){
					throw new AppException("职级范围不正确，请检查！");
				}
			}
			
			if(!StringUtil.isEmpty(edu) && !StringUtil.isEmpty(edu1)){
				CodeValue eduCodeValue =RuleSqlListBS.getCodeValue("ZB64", edu);
				CodeValue edu1CodeValue =RuleSqlListBS.getCodeValue("ZB64", edu1);
				/*if(!eduCodeValue.getSubCodeValue().equalsIgnoreCase(edu1CodeValue.getSubCodeValue())){
					throw new AppException("学历范围不属于同一类别，请检查！");
				}*/
				//职级 值越小 字面意思越高级
				if(eduCodeValue.getCodeValue().compareTo(edu1CodeValue.getCodeValue())<0){
					throw new AppException("学历范围不正确，请检查！");
				}
			}
			
			
			//范围校验 end
			
			
			StringBuffer customData=new StringBuffer("");//编辑器网格显示数据
			
//			sb.append("select  a01.a0000, a0101, a0104, age, a0117, a0141, a0192, a0148,A0160,A0192D,A0120,a02.A0221A,QRZXL,ZZXL,a02.a0288,a02.A0243 from A01 a01,A02 a02 where a01.a0000=a02.a0000 ");
			sb.append(CustomQueryPageModel.getcommSQL() + " where 1=1 ");

			if (!a0101.equals("")){
				sb.append(" and ");
				sb.append("a01.a0101 like '"+a0101+"%'");
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'"+a0101+"','logicSymbols':' and ','colValues':'"+a0101+"','colNamesValue':'A0101','colNames':'人员姓名','leftBracket':'','rightBracket':''}");
			}
			if (!a0184.equals("")){
				sb.append(" and ");
				sb.append("a01.a0101 like '"+a0184+"%'");
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'"+a0184+"','logicSymbols':' and ','colValues':'"+a0184+"','colNamesValue':'A0101','colNames':'人员姓名','leftBracket':'','rightBracket':''}");
			}
			if (!a0160.equals("")){
				sb.append(" and ");
				sb.append("a01.a0160 ='"+a0160+"'");
				String colValueView=cbBs.getAaa103("A0160", a0160,cbBs.ctcList);
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0160+"','colNamesValue':'A0160','colNames':'人员类别','leftBracket':'','rightBracket':''}");
			}
			if (!a0163.equals("")){
				sb.append(" and ");
				sb.append("a01.a0163='"+a0163+"'");
				String colValueView=cbBs.getAaa103("A0163", a0163,cbBs.ctcList);
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0163+"','colNamesValue':'A0163','colNames':'人员状态','leftBracket':'','rightBracket':''}");				
			}
			if (!age.equals("")){
				sb.append(" and ");
				sb.append("a01.age>="+age);
				
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A01','colValuesView':'"+age+"','logicSymbols':' and ','colValues':'"+age+"','colNamesValue':'AGE','colNames':'年龄','leftBracket':'','rightBracket':''}");								
			}
			if (!age1.equals("")){
				sb.append(" and ");
				sb.append("a01.age<="+age1);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A01','colValuesView':'"+age1+"','logicSymbols':' and ','colValues':'"+age1+"','colNamesValue':'AGE','colNames':'年龄','leftBracket':'','rightBracket':''}");							
			}
			if (!female.equals("0")){
				sb.append(" and ");
				sb.append("a01.a0104='"+female+"'");
				String colValueView=cbBs.getAaa103("A0104", female,cbBs.ctcList);
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+female+"','colNamesValue':'A0104','colNames':'性别','leftBracket':'','rightBracket':''}");				
				
			}
			if (!minority.equals("0")){
				sb.append(" and ");
				sb.append("a01.a0117!='01'");
				String colValueView=cbBs.getAaa103("A0117","01",cbBs.ctcList);
				customData.append(",{'opeartors':'!={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'01','colNamesValue':'A0117','colNames':'民族','leftBracket':'','rightBracket':''}");								
			}
			if (nonparty.equals("1")){
				sb.append(" and ");
				sb.append("a01.a0141!='01'");
				String colValueView=cbBs.getAaa103("A0141","01",cbBs.ctcList);
				customData.append(",{'opeartors':'!={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'01','colNamesValue':'A0141','colNames':'政治面貌','leftBracket':'','rightBracket':''}");												
			}
            if(!"".equals(duty1)){
				sb.append(" and ");
//				sb.append("a01.a0148<='"+duty1+"'");
				sb.append("a01.a0148>='"+duty1+"'");
				String colValueView=cbBs.getAaa103("A0148", duty1,cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+duty1+"','colNamesValue':'A0148','colNames':'职务层次','leftBracket':'','rightBracket':''}");								
            }
            if(!"".equals(duty)){
				sb.append(" and ");
//				sb.append("a01.a0148>='"+duty+"'");	
				sb.append("a01.a0148<='"+duty+"'");	
				String colValueView=cbBs.getAaa103("A0148", duty,cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+duty+"','colNamesValue':'A0148','colNames':'职务层次','leftBracket':'','rightBracket':''}");												
            }
            if(!"".equals(a0192dE)){
				sb.append(" and ");
				sb.append("a01.a0192d>='"+a0192dE+"'");
				String colValueView=cbBs.getAaa103("A0192D", a0192dE,cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0192dE+"','colNamesValue':'A0192D','colNames':'职务职级','leftBracket':'','rightBracket':''}");				
            }
            if(!"".equals(a0192dS)){
				sb.append(" and ");
				sb.append("a01.a0192d<='"+a0192dS+"'");
				String colValueView=cbBs.getAaa103("A0192D", a0192dS,cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0192dS+"','colNamesValue':'A0192D','colNames':'职务职级','leftBracket':'','rightBracket':''}");								
            }            
            if(!"".equals(a0221aS)){
				sb.append(" and ");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0221a<='"+a0221aS+"')");	
				String colValueView=cbBs.getAaa103("A0221A", a0221aS,cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A02','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0221aS+"','colNamesValue':'A0221A','colNames':'职务等级','leftBracket':'','rightBracket':''}");												
            }
            if(!"".equals(a0221aE)){
				sb.append(" and ");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0221a>='"+a0221aE+"')");
				String colValueView=cbBs.getAaa103("A0221A", a0221aE,cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A02','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0221aE+"','colNamesValue':'A0221A','colNames':'职务等级','leftBracket':'','rightBracket':''}");																
            }            
            if(!"".equals(dutynow)){
				sb.append(" and ");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0288>='"+dutynow+"')");
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A02','colValuesView':'"+dutynow+"','logicSymbols':' and ','colValues':'"+dutynow+"','colNamesValue':'A0288','colNames':'职务层次起始日期','leftBracket':'','rightBracket':''}");																				
            }
            if(!"".equals(dutynow1)){
				sb.append(" and ");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0288<='"+dutynow1+"')");	
				String colValueView=cbBs.getAaa103("A0288", dutynow1,cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A02','colValuesView':'"+dutynow1+"','logicSymbols':' and ','colValues':'"+dutynow1+"','colNamesValue':'A0288','colNames':'职务层次结束日期','leftBracket':'','rightBracket':''}");																							
            }
            if(!"".equals(a0219)){
				sb.append(" and ");
//				sb.append("a02.a0219='"+a0219+"'");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0219='"+a0219+"')");
				String colValueView=cbBs.getAaa103("A0219", a0219,cbBs.ctcList);
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A02','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0219+"','colNamesValue':'A0219','colNames':'职务类别','leftBracket':'','rightBracket':''}");							
            }
            if(!"".equals(b01String)&&b01String!=null&&!"{}".equals(b01String)){
    			//选择机构
    			JSONObject jsonObject = JSONObject.fromObject(b01String);
    			sb.append("and exists (select 1 from a02 where a02.a0000=a01.a0000 ");
    			sb.append(" and (1=2 ");
    			Iterator<String> it = jsonObject.keys();
    			// 遍历jsonObject数据，添加到Map对象
    			while (it.hasNext()) {
    				String nodeid = it.next(); 
    				String operators = (String) jsonObject.get(nodeid);
    				String[] types = operators.split(":");
    				if("true".equals(types[1])&&"true".equals(types[2])){
    					sb.append(" or a02.A0201B like '"+nodeid+"%' ");
    					String leftBracket="";
						String rightBracket="";
						String logicSymbols=" or ";
						leftBracket="(";
						rightBracket=")";
						logicSymbols=" and ";
						String colValueView=cbBs.getAaa103("B0111",nodeid,cbBs.ctcList);
    					customData.append(",{'opeartors':'like {v%}','logchecked':'','tableName':'B01','colValuesView':'"+colValueView+"','logicSymbols':'"+logicSymbols+"','colValues':'"+nodeid+"','colNamesValue':'B0111','colNames':'单位','leftBracket':'"+leftBracket+"','rightBracket':'"+rightBracket+"'}");												
    					/*String sql = "select a02.A0201B from a02 a02 where a02.A0201B like '"+nodeid+"%'";
    					List<Object> b01S = HBUtil.getHBSession().createSQLQuery(sql).list();
    					int count = 0;
    					for(Object o:b01S){
    						String leftBracket="";
    						String rightBracket="";
    						String logicSymbols=" or ";
    						String o1 = "";
    						if(count==0){
    							leftBracket="(";
    						}
    						if(count==(b01S.size()-1)){
    							rightBracket=")";
    							logicSymbols=" and ";
    						}
    						if(o!=null&&!"".equals(o)){
    							o1=o.toString();
    						}
    						String colValueView=cbBs.getAaa103("B0111",o1,cbBs.ctcList);
        					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'B01','colValuesView':'"+colValueView+"','logicSymbols':'"+logicSymbols+"','colValues':'"+o1+"','colNamesValue':'B0111','colNames':'单位','leftBracket':'"+leftBracket+"','rightBracket':'"+rightBracket+"'}");												
    						count++;
    					}*/
    				}else if("true".equals(types[1])&&"false".equals(types[2])){
    					sb.append("or a02.A0201B like '"+nodeid+".%'");
    					String leftBracket="";
						String rightBracket="";
						String logicSymbols=" or ";
						leftBracket="(";
						rightBracket=")";
						logicSymbols=" and ";
						String colValueView=cbBs.getAaa103("B0111",nodeid+".",cbBs.ctcList);
    					customData.append(",{'opeartors':'like {v%}','logchecked':'','tableName':'B01','colValuesView':'"+colValueView+"','logicSymbols':'"+logicSymbols+"','colValues':'"+nodeid+".','colNamesValue':'B0111','colNames':'单位','leftBracket':'"+leftBracket+"','rightBracket':'"+rightBracket+"'}");												
    				}else if("false".equals(types[1])&&"true".equals(types[2])){
    					sb.append("or a02.A0201B = '"+nodeid+"'");
    					String leftBracket="";
						String rightBracket="";
						String logicSymbols=" or ";
						leftBracket="(";
						rightBracket=")";
						logicSymbols=" and ";
						String colValueView=cbBs.getAaa103("B0111",nodeid,cbBs.ctcList);
    					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'B01','colValuesView':'"+colValueView+"','logicSymbols':'"+logicSymbols+"','colValues':'"+nodeid+"','colNamesValue':'B0111','colNames':'单位','leftBracket':'"+leftBracket+"','rightBracket':'"+rightBracket+"'}");												
    				}
    			}
    			sb.append(" ) ");
    			sb.append(" ) ");
				//sb.append(" and ");
//				sb.append("a02.A0201B in ("+b01String+")");	
				//sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.A0201B in ("+b01String+"))");	
				
				//String [] arrB01=b01String.split(",");
				/*if(arrB01.length>200){
					throw new AppException("不能选择超过200个单位！");
				}
				
				for(int i=0;i<arrB01.length;i++){
					String b0111=arrB01[i];
					String leftBracket="";
					String rightBracket="";
					String logicSymbols=" or ";
					if(i==0){
						leftBracket="(";
					}
					if(i==(arrB01.length-1)){
						rightBracket=")";
						logicSymbols=" and ";
					}
					String colValueView=cbBs.getAaa103("B0111", b0111.replace("'", ""),cbBs.ctcList);
					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'B01','colValuesView':'"+colValueView+"','logicSymbols':'"+logicSymbols+"','colValues':'"+b0111.replace("'", "")+"','colNamesValue':'B0111','colNames':'单位','leftBracket':'"+leftBracket+"','rightBracket':'"+rightBracket+"'}");												
				}*/
            }
            if(!"".equals(edu)&&"".equals(edu1)){
				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B <='"+edu+"'");
	            if(!"".equals(allday)){
					sb.append(" and ");
					sb.append("a0837 = '"+allday+"'");		
	            }
	            sb.append(")");
	            
				String colValueView=cbBs.getAaa103("A0801B", edu,cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+edu+"','colNamesValue':'A0801B','colNames':'学历','leftBracket':'(','rightBracket':''}");
				if(!StringUtil.isEmpty(allday)){
					colValueView=cbBs.getAaa103("A0837", allday,cbBs.ctcList);
					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+allday+"','colNamesValue':'A0837','colNames':'是否全日制','leftBracket':'','rightBracket':')'}");													      				
				}
					
			}
            if(!"".equals(edu1)&&"".equals(edu)){
				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B >='"+edu1+"'");	
	            if(!"".equals(allday)){
					sb.append(" and ");
					sb.append("a0837 = '"+allday+"'");		
	            }
	            sb.append(")");
				String colValueView=cbBs.getAaa103("A0801B", edu1,cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+edu1+"','colNamesValue':'A0801B','colNames':'学历','leftBracket':'(','rightBracket':''}");
				if(!StringUtil.isEmpty(allday)){
					colValueView=cbBs.getAaa103("A0837", allday,cbBs.ctcList);
					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+allday+"','colNamesValue':'A0837','colNames':'是否全日制','leftBracket':'','rightBracket':')'}");													      					            
				}
			}
            if(!"".equals(edu1)&&!"".equals(edu)){
				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B between '"+edu1+"' and '"+edu+"'");
	            if(!"".equals(allday)){
					sb.append(" and ");
					sb.append("a0837 = '"+allday+"'");		
	            }
	            sb.append(")");
	            
				String colValueView=cbBs.getAaa103("A0801B", edu,cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+edu+"','colNamesValue':'A0801B','colNames':'学历','leftBracket':'(','rightBracket':''}");
				colValueView=cbBs.getAaa103("A0801B", edu1,cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+edu1+"','colNamesValue':'A0801B','colNames':'学历','leftBracket':'','rightBracket':''}");				
				if(!StringUtil.isEmpty(allday)){
					colValueView=cbBs.getAaa103("A0837", allday,cbBs.ctcList);
					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+allday+"','colNamesValue':'A0837','colNames':'是否全日制','leftBracket':'','rightBracket':')'}");													      				
				}
            }
            String data="";
            if(!StringUtil.isEmpty(customData.toString()) && customData.length()>0){
            	data =	"["+customData.toString().substring(1, customData.length()).replace("'", "\"")+"]";
            }else{
            	data = "[]";
            }
            
            //lzy--update--2017.05.24
            sb.append(" and a01.status = '1' and concat(a01.a0000, '') in (select a02.a0000 from a02 where a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+SysManagerUtils.getUserId()+"') and a02.a0255 = '1' )");
            cbBs.delComm();
            cbBs.saveOrUodateCq("", "常用条件", sb.toString(), "", SysUtil.getCacheCurrentUser() .getLoginname(), data); 
			//this.setNextEventName("gridcq");
            //String griddata= JSONArray.fromObject(cbBs.getCustomSqlList(SysUtil.getCacheCurrentUser().getLoginname())).toString();
    		//this.getPageElement("gridcq").setValue(griddata);
            this.getExecuteSG().addExecuteCode("var cwin = $h.getTopParent().document.getElementById('iframe_conditionwin');"
            		+ "if(cwin){cwin.contentWindow.radow.doEvent('gridcq');}");
            
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	/**
	 * 清除条件
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("clearCon.onclick")
	@NoRequiredValidate
	public int clearCon()throws RadowException, AppException{
		String a= this.getPageElement("female").getValue();
		this.getPageElement("a0160").setValue("");
		this.getPageElement("a0160_combo").setValue("");		//人员类别-fujun
		this.getPageElement("a0101A").setValue("");
		this.getPageElement("a0184A").setValue("");
		this.getPageElement("a0163").setValue("");
		this.getPageElement("a0163_combo").setValue("");		//人员状态-fujun
		this.getPageElement("ageA").setValue("");
		this.getPageElement("age1").setValue("");
		this.getPageElement("female").setValue("0");;
		this.getPageElement("minority").setValue("0");
		this.getPageElement("nonparty").setValue("0");
		this.getPageElement("duty").setValue("");
		this.getPageElement("duty_combo").setValue("");			//职务层次 ——fujun
		this.getPageElement("duty1").setValue("");
		this.getPageElement("duty1_combo").setValue("");		//职务层次 ——fujun
		this.getPageElement("dutynow").setValue("");
		this.getPageElement("dutynow_1").setValue("");			//任现职务层次时间 ——fujun
		this.getPageElement("dutynow1").setValue("");
		this.getPageElement("dutynow1_1").setValue("");			//任现职务层次时间 ——fujun
		this.getPageElement("a0219").setValue("");
		this.getPageElement("a0219_combo").setValue("");		//职务类别——fujun
		this.getPageElement("edu").setValue("");
		this.getPageElement("edu_combo").setValue("");		//学历——fujun
		this.getPageElement("edu1").setValue("");
		this.getPageElement("edu1_combo").setValue("");		//学历——fujun
		this.getPageElement("allday").setValue("0");
		/*this.getPageElement("SysOrgTree").setValue("");
		this.getPageElement("SysOrgTreeIds").setValue("");*/
	    this.getPageElement("a0221aS").setValue("");//职务等级
		this.getPageElement("a0221aS_combo").setValue("");		//职务等级——fujun
		this.getPageElement("a0221aE").setValue("");//职务等级
		this.getPageElement("a0221aE_combo").setValue("");		//职务等级——fujun
		
		this.getPageElement("a0192dS").setValue("");//职务职级
		this.getPageElement("a0192dS_combo").setValue("");		//职务职级——fujun
		this.getPageElement("a0192dE").setValue("");//职务职级	
		this.getPageElement("a0192dE_combo").setValue("");			//职务职级——fujun	
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}




