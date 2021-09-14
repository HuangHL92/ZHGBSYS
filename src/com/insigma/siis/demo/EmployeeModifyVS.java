package com.insigma.siis.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.VSSupport;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.sys.audit.AuditManager;
import com.insigma.odin.framework.sys.print.PrintManager;
import com.insigma.odin.framework.util.DateUtil;
import com.insigma.siis.demo.entity.Employee;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class EmployeeModifyVS extends VSSupport{
	public EmployeeDTO getEmployeeInfo(String aac001) throws AppException{
		if(aac001==null){
			throw new AppException("个人编码不能为空");
		}
		HBSession hbsess=HBUtil.getHBSession();
		Employee emp=(Employee)hbsess.get(Employee.class, aac001);
		if(emp==null){
			throw new AppException("个人编码不存在");
		}
		
		EmployeeDTO empDTO=new EmployeeDTO();
		empDTO.setAac001(emp.getAac001());
		empDTO.setAac002(emp.getAac002());
		empDTO.setAac003(emp.getAac003());
		empDTO.setAac004(emp.getAac004());
		empDTO.setAac006(emp.getAac006());
		List ac02Set=new ArrayList();
		Ac02DTO ac02DTO=new Ac02DTO();
		ac02DTO.setAae140("01");
		ac02Set.add(ac02DTO);
		ac02DTO=new Ac02DTO();
		ac02DTO.setAae140("04");
		ac02Set.add(ac02DTO);
		empDTO.setAc02Set(ac02Set);
		
		return empDTO;
	}
	
	public String modify(EmployeeForm empForm) throws AppException{
		HBSession hbsess=HBUtil.getHBSession();
		Date aac006=DateUtil.parseDate(empForm.getAac006());
		Date sysdate=HBUtil.getSysdate();
		if(aac006.getTime()>sysdate.getTime()){
			throw new AppException("出生日期不能晚于当前日期");
		}
		Employee emp=(Employee)hbsess.get(Employee.class, empForm.getAac001());
		emp.setAac002(empForm.getAac002());
		emp.setAac003(empForm.getAac003());
		emp.setAac004(empForm.getAac004());
		emp.setAac006(aac006);
		hbsess.update(emp);
		PrintManager.addBillPrint("1249270000","","HDYWLSH 1593528",true,1);
		AuditManager.addAudit(new Long(123));
		return "123";
	}
	
	public void audit(Long seno){
		CommonQueryBS.systemOut("业务审核成功");
	}
}
