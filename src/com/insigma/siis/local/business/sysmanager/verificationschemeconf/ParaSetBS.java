package com.insigma.siis.local.business.sysmanager.verificationschemeconf;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.BeanUtil;
import com.insigma.siis.local.business.entity.CodeTableCol;
import com.insigma.siis.local.business.entity.VVerifyColVsl006;
import com.insigma.siis.local.business.entity.VVerifyColVsl006Id;
import com.insigma.siis.local.business.entity.VerifyScheme;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;


public class ParaSetBS{
	
	public static void save(String tableCode,String colCode,List<HashMap<String,Object>> list) throws AppException{
		HBSession sess = HBUtil.getHBSession();
		CodeTableCol codeTableCol =  (CodeTableCol) sess.createQuery("from CodeTableCol where tableCode = '"+tableCode+"' and colCode = '"+colCode+"'").uniqueResult();
		if(codeTableCol==null){
			throw new AppException("��ȡ�������ñ�ʧ�ܣ��쳣��Ϣ����Ϣ��-"+tableCode+",��Ϣ��-"+colCode);
		}
		
		CodeTableColDTO codeTableColDTO = new CodeTableColDTO(); //��¼��־ʹ��
		BeanUtil.copy(codeTableCol, codeTableColDTO);
		
		StringBuffer vsl006sBf = new StringBuffer();
		if(list!=null && list.size()>0){
			for(HashMap<String,Object> map : list){
				Object checked =  map.get("checked");
				if(checked!=null && checked.toString().equalsIgnoreCase("true")){
					vsl006sBf.append(map.get("vsl006")).append(",");
				}
			}
			if(vsl006sBf.length()>0){
				codeTableCol.setVsl006s(vsl006sBf.substring(0, vsl006sBf.lastIndexOf(",")));
			}else{
				codeTableCol.setVsl006s("");
			}
		}else{
			codeTableCol.setVsl006s("");
		}
		sess.update(codeTableCol);
		sess.flush();
		//��¼��־
		try {
			new LogUtil().createLog("637", "CODE_TABLE_COL", codeTableCol.getCtci(), codeTableCol.getColName(), null,Map2Temp.getLogInfo(codeTableColDTO, codeTableCol) );
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		//��ն�������
		evictSecondLevelCache();
		
	}
	
	/**
	 * �����������
	 */
	public static void evictSecondLevelCache() {
		SessionFactory sf = HBUtil.getHBSessionFactory();
	    Map<String, CollectionMetadata> roleMap = sf.getAllCollectionMetadata();
	    for (String roleName : roleMap.keySet()) {
	      sf.evictCollection(roleName);
	    }
	    Map<String, ClassMetadata> entityMap = sf.getAllClassMetadata();
	    for (String entityName : entityMap.keySet()) {
	      sf.evictEntity(entityName);
	    }
	    sf.evictQueries();
	 }
}