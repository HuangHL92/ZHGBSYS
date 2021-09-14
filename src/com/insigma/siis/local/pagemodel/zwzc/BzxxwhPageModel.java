package com.insigma.siis.local.pagemodel.zwzc;

import java.sql.Statement;
import java.util.List;

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
import com.insigma.siis.local.business.entity.BZFX;
import com.insigma.siis.local.business.entity.DBWY;
import com.insigma.siis.local.business.entity.GQBZFX;
import com.insigma.siis.local.business.entity.QXSBZFX;

public class BzxxwhPageModel extends PageModel{
	@Override
	public int doInit() throws RadowException {
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//初始化单位名和核定职数
	@PageEvent("init1")
	@NoRequiredValidate
	public  int  init1() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		String type=checkedgroupid.substring(0,11);
		String bzlx="gqbzfx";
		if("001.001.002".equals(type)) {
			bzlx="bzfx";
		}else if ("001.001.003".equals(type)) {
			bzlx="gqbzfx";
		}else if ("001.001.004".equals(type)) {
			bzlx="qxsbzfx";
		}
		String dwm=null;
		StringBuffer jbgk=new StringBuffer();
		StringBuffer fzdw=new StringBuffer();
		try {
			@SuppressWarnings("unchecked")
			List<String> dwmList= HBUtil.getHBSession().createSQLQuery("select b0101 from b01 where b0111='"+checkedgroupid+"'").list();
			if(dwmList.size()>=1) {
				dwm= String.valueOf(dwmList.get(0));
			}else {
				dwm= "空";
			}
			
			//基本概况
			@SuppressWarnings("unchecked")
			List<String> jbgks1= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,0,2000)) from "+bzlx+" where B0111='"+checkedgroupid+"'").list();
			if(jbgks1.size()>0 && jbgks1.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks1.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> jbgks2= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,2001,2000)) from "+bzlx+" where B0111='"+checkedgroupid+"'").list();
			if(jbgks2.size()>0 && jbgks2.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks2.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> jbgks3= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,4001,2000)) from "+bzlx+" where B0111='"+checkedgroupid+"'").list();
			if(jbgks3.size()>0 && jbgks3.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks3.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> jbgks4= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,6001,2000)) from "+bzlx+" where B0111='"+checkedgroupid+"'").list();
			if(jbgks4.size()>0 && jbgks4.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks4.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> jbgks5= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,8001,2000)) from "+bzlx+" where B0111='"+checkedgroupid+"'").list();
			if(jbgks5.size()>0 && jbgks5.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks5.get(0)));
			}
			
			//发展定位
			@SuppressWarnings("unchecked")
			List<String> fzdws= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,0,2000)) fzdw from "+bzlx+" where B0111='"+checkedgroupid+"'").list();
			if(fzdws.size()>0 && fzdws.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> fzdws1= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,2001,2000)) fzdw from "+bzlx+" where B0111='"+checkedgroupid+"'").list();
			if(fzdws1.size()>0 && fzdws1.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws1.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> fzdws2= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,4001,2000)) fzdw from "+bzlx+" where B0111='"+checkedgroupid+"'").list();
			if(fzdws2.size()>0 && fzdws2.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws2.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> fzdws3= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,6001,2000)) fzdw from "+bzlx+" where B0111='"+checkedgroupid+"'").list();
			if(fzdws3.size()>0 && fzdws3.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws3.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> fzdws4= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,8001,2000)) fzdw from "+bzlx+" where B0111='"+checkedgroupid+"'").list();
			if(fzdws4.size()>0 && fzdws4.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws4.get(0)));
			}

		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("document.getElementById(\"dwm\").innerHTML='"+dwm+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"jbgk\").value='"+jbgk.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"fzdw\").value='"+fzdw.toString()+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//班子分析以及提任情况数据保存
	@PageEvent("saveFX")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveFX() throws RadowException, AppException {
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		String jbgk=this.getPageElement("jbgk").getValue();
		String fzdw=this.getPageElement("fzdw").getValue();
		String type=checkedgroupid.substring(0,11);
		String bz00="";
		HBSession session = HBUtil.getHBSession();
		try {

			String bzlx="gqbzfx";
			if("001.001.002".equals(type)) {
				bzlx="bzfx";
				@SuppressWarnings("unchecked")
				List<String> bz00s= HBUtil.getHBSession().createSQLQuery("select bz00 from "+bzlx+" where B0111='"+checkedgroupid+"'").list();
				if(bz00s.size()>0 && bz00s.get(0)!=null) {
					bz00=String.valueOf(bz00s.get(0));
					BZFX bzfx=(BZFX)session.get(BZFX.class, bz00);
					bzfx.setJbgk(jbgk);
					bzfx.setFzdw(fzdw);
					session.update(bzfx);
				}else {
					BZFX bzfx=new BZFX();
					bzfx.setB0111(checkedgroupid);
					bzfx.setJbgk(jbgk);
					bzfx.setFzdw(fzdw);
					session.save(bzfx);
				}
			}else if ("001.001.003".equals(type)) {
				bzlx="gqbzfx";
				@SuppressWarnings("unchecked")
				List<String> bz00s= HBUtil.getHBSession().createSQLQuery("select bz00 from "+bzlx+" where B0111='"+checkedgroupid+"'").list();
				if(bz00s.size()>0 && bz00s.get(0)!=null) {
					bz00=String.valueOf(bz00s.get(0));
					GQBZFX gqbzfx=(GQBZFX)session.get(GQBZFX.class, bz00);
					gqbzfx.setJbgk(jbgk);
					gqbzfx.setFzdw(fzdw);
					session.update(gqbzfx);
				}else {
					GQBZFX gqbzfx=new GQBZFX();
					gqbzfx.setB0111(checkedgroupid);
					gqbzfx.setJbgk(jbgk);
					gqbzfx.setFzdw(fzdw);
					session.save(gqbzfx);
				}
			}else if ("001.001.004".equals(type)) {
				bzlx="qxsbzfx";
				@SuppressWarnings("unchecked")
				List<String> bz00s= HBUtil.getHBSession().createSQLQuery("select bz00 from "+bzlx+" where B0111='"+checkedgroupid+"'").list();
				if(bz00s.size()>0 && bz00s.get(0)!=null) {
					bz00=String.valueOf(bz00s.get(0));
					QXSBZFX qxsbzfx=(QXSBZFX)session.get(QXSBZFX.class, bz00);
					qxsbzfx.setJbgk(jbgk);
					qxsbzfx.setFzdw(fzdw);
					session.update(qxsbzfx);
				}else {
					QXSBZFX qxsbzfx=new QXSBZFX();
					qxsbzfx.setB0111(checkedgroupid);
					qxsbzfx.setJbgk(jbgk);
					qxsbzfx.setFzdw(fzdw);
					session.save(qxsbzfx);
				}
			}
			session.flush();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','保存成功！',null,'220')");
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','保存失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
