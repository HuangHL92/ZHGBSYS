package com.insigma.odin.framework.sys.code;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.transform.Transformers;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.entity.VAa10;
import com.insigma.odin.framework.commform.local.sys.LoginManager;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.sys.comm.CommQueryBS;
import com.insigma.odin.framework.util.GlobalNames;

public class DefaultSysCodeCm extends AbsSysCode {

	@SuppressWarnings("unchecked")
	public List getVAA10List(String codeType, String filter) {
		String aaa027="";
		if ("true".equals(com.insigma.odin.framework.util.GlobalNames.sysConfig.get("IS_USE_COMMFORM"))) { // 判断是否使用commform开发方法，如果不使用，则不进行下步处理
			aaa027=LoginManager.getCurrentAaa027();
		}
		
		if(codeType==null||codeType.equals("")) return null;	
		String hql="from VAa10 a where a.id.aaa100='"+codeType.toUpperCase()+"'";
		if(filter!=null&&!filter.equals("")){
			hql=hql+" and ("+filter+")";
		}
		if(aaa027!=null&&!aaa027.equals("")){
			hql=hql+" and aaa027 ='"+aaa027+"'";
		}
		hql=hql+" order by a.eaa101,a.id.aaa102";
		
		List codeList=new ArrayList();
		
		HBSession hbsess=HBUtil.getHBSession();
		codeList=hbsess.createQuery(hql).list();
		hbsess.flush();
		
		return codeList;
	}
	
	@SuppressWarnings("unchecked")
	public List getCodeValueList(String codeType, String filter) {
		String aaa027="";
		if ("true".equals(com.insigma.odin.framework.util.GlobalNames.sysConfig.get("IS_USE_COMMFORM"))) { // 判断是否使用commform开发方法，如果不使用，则不进行下步处理
			aaa027=LoginManager.getCurrentAaa027();
		}
		
		String sql = "SELECT b.COL_CODE AS aaa100, a.CODE_VALUE AS aaa102, a.CODE_NAME AS aaa103, '' AS aaa105, NULL AS aaa027, '' AS eaa101 FROM code_table_col b"
				+ " left join code_value a on  a.CODE_TYPE = b.code_type where b.col_code = '" + codeType.toUpperCase() + "' and b.CODE_TYPE IS NOT NULL order by a.inino,a.code_value";
		
		
		/*if(codeType==null||codeType.equals("")) return null;	
		String hql="from VAa10 a where a.id.aaa100='"+codeType.toUpperCase()+"'";
		if(filter!=null&&!filter.equals("")){
			hql=hql+" and ("+filter+")";
		}
		if(aaa027!=null&&!aaa027.equals("")){
			hql=hql+" and aaa027 ='"+aaa027+"'";
		}
		hql=hql+" order by a.eaa101,a.id.aaa102";*/
		
		/*List codeList=new ArrayList();
		
		
		codeList=hbsess.createSQLQuery(sql).list();
		hbsess.flush();
		return codeList;*/
		HBSession hbsess=HBUtil.getHBSession();
		Object localObject = new ArrayList();
	    localObject = hbsess.createSQLQuery(sql).addEntity(VAa10.class).list();
	    
	    hbsess.flush();
	    return (List)localObject;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCodeList(String codeType, String filter) {
		
		String DEV_MODE = GlobalNames.sysConfig.get("DEV_MODE");
		List codeList=null;
		if("0".equals(DEV_MODE)){
			codeList=getCodeValueList(codeType, filter);
		}else{
			codeList = getVAA10List(codeType, filter);
		}
		
		
		//List codeList=getVAA10List(codeType, filter);
		
		
		/*if(codeList==null||codeList.size()==0){
			codeList = getVAA10List(codeType, filter);
		}*/
		return codeList;
	}

	@Override
	public String getValueByCode(String codeType, String code) {
		String aaa027="";
		if ("true".equals(com.insigma.odin.framework.util.GlobalNames.sysConfig.get("IS_USE_COMMFORM"))) { // 判断是否使用commform开发方法，如果不使用，则不进行下步处理
			aaa027=LoginManager.getCurrentAaa027();
		}
		
		if(codeType==null||codeType.equals("")) return code;
        if(code==null||code.equals(""))return code;
		String hql="from VAa10 a where a.id.aaa100='"+codeType.toUpperCase()+"' and a.id.aaa102='"+code+"'";
		if(aaa027!=null&&!aaa027.equals("")){
			hql=hql+" and aaa027 ='"+aaa027+"'";
		}
		HBSession hbsess=HBUtil.getHBSession();
		List codeList=hbsess.createQuery(hql).list();
		hbsess.flush();
		if(codeList!=null&&codeList.size()>0){
			VAa10 codeObj = (VAa10)codeList.get(0);
			return codeObj.getId().getAaa103();
		}else{
			return code;
		}
	}

	public com.insigma.odin.framework.comm.query.PageQueryData getCodeList(String codeType, String filter, int start, int limit) throws AppException {
		
		String aaa027="";
		if ("true".equals(com.insigma.odin.framework.util.GlobalNames.sysConfig.get("IS_USE_COMMFORM"))) { // 判断是否使用commform开发方法，如果不使用，则不进行下步处理
			aaa027=LoginManager.getCurrentAaa027();
		}
		
		if(codeType==null||codeType.equals("")) return null;	
		String hql="from VAa10 a where a.id.aaa100='"+codeType.toUpperCase()+"'";
		if(filter!=null&&!filter.equals("")){
			hql=hql+" and ("+filter+")";
		}
		if(aaa027!=null&&!aaa027.equals("")){
			hql=hql+" and aaa027 ='"+aaa027+"'";
		}
		hql=hql+" order by a.eaa101,a.id.aaa102";
		CommQueryBS bs = new CommQueryBS();
		return bs.query(hql, "HQL", start, limit);
	}
	
	

}

