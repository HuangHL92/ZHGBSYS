package com.insigma.odin.framework.sys.auth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.IsSuperManagerUtil;
import com.insigma.odin.framework.util.MD5;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;



public class UserPassAuthExtend extends AuthAbstract {
	
	private PrivilegeManager privilegeManager = PrivilegeManager.getInstance();
	
	/**
	 * �û���������֤��ʽ����
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected boolean validate(AuthParamConfig config) throws Exception {
		HttpServletRequest httpServletRequest = config.getRequest();
		String s = httpServletRequest.getSession().getId();
		String password = config.getParamValueByName("password");
		String username = config.getParamValueByName("username");
		if(StringUtils.isEmpty(username)){
			username = httpServletRequest.getParameter("username");
			password = httpServletRequest.getParameter("password");
		}
		String username2 = IsSuperManagerUtil.MD5(username);
		boolean reslut = false;
		String[] names = GlobalNames.SUPER_USERS;
		for(int i=0;i<names.length;i++){
			String name = names[i];
			if(username2.equals(name)&&password.equals(GlobalNames.sysConfig.get("SUPER_PASSWORD"))){
				reslut = true;
				break;
			}
		}
		if(username2!=null && reslut) {
			return true;
		}
		List<Object> list =privilegeManager.getIUserControl().queryByName(username, true);
		if(list==null || list.size()==0){
			super.doError(httpServletRequest,"��¼��Ϊ��"+username+"�����û�������");
			return false;
		}
		UserVO user = (UserVO)list.get(0);
		if(user.getStatus().equals("0")){
			super.doError(httpServletRequest, "��¼��Ϊ��"+username+"�����û��Ѿ���ע���ˣ�");
			return false;
		}
		String sql = "select validity,pwdedtdate,logonfailtimes from smt_user where loginname='"+username+"'";
		HBSession hs = HBUtil.getHBSession();
		CommQuery query = new CommQuery();
		List<HashMap<String,Object>> li = query.getListBySQL(sql);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map = li.get(0);
		if(map.get("validity")!=null){
			String validity = map.get("validity").toString();
			Date createdate = user.getCreatedate();
			Date date = new Date();
			Calendar calendar =Calendar.getInstance();
			calendar.setTime(createdate);
						
			if(validity=="validity01"||"validity01".equals(validity)){
				calendar.add(Calendar.WEEK_OF_YEAR,1);         //����һ�����
				Date validitydate = calendar.getTime();
				if(validitydate.before(date)){
					super.doError(httpServletRequest, "��¼��Ϊ��"+username+"�����û������Ѿ������ˣ�");
					return false;
				}
				
			}else if(validity=="validity02"||"validity02".equals(validity)){			
				calendar.add(Calendar.MONTH, 1);//����һ����   
				Date validitydate = calendar.getTime();
				if(validitydate.before(date)){
					super.doError(httpServletRequest, "��¼��Ϊ��"+username+"�����û������Ѿ������ˣ�");
					return false;
				}
			}
		}
								
		
//		if(!privilegeManager.getIUserControl().validateHashCode(user)){
//			super.doError(httpServletRequest, "��¼��Ϊ��"+username+"�����û����ݲ��Ϸ�");
//			return false;
//		}
		Long lft = map.get("logonfailtimes")==null?0l:Long.valueOf(map.get("logonfailtimes").toString());
		if(user.getPasswd()==null || !user.getPasswd().equals(password)){
			if(lft>=100){
				super.doError(httpServletRequest, "��¼��Ϊ��"+username+"���ѱ�����������ϵ����Ա������");
			}else if(lft==4){
				lft++;
				HBUtil.executeUpdate("update smt_user set Logonfailtimes="+lft+" where userid='"+user.getId()+"'");
				new LogUtil(user).createLogNew(user.getId(),"��¼ʧ��5������","smt_user",user.getId(),user.getLoginname(), new ArrayList());
				super.doError(httpServletRequest, "��¼��Ϊ��"+username+"����¼ʧ�ܳ���5�Σ�����ϵ����Ա������");
			}else if(lft>4){
				super.doError(httpServletRequest, "��¼��Ϊ��"+username+"����¼ʧ�ܳ���5�Σ�����ϵ����Ա������");
			}else{
				lft++;
				HBUtil.executeUpdate("update smt_user set Logonfailtimes="+lft+" where userid='"+user.getId()+"'");
				super.doError(httpServletRequest, "��¼��Ϊ��"+username+"�����������");
			}
			return false;
		}
		if(lft==100){
			super.doError(httpServletRequest, "��¼��Ϊ��"+username+"���ѱ�����������ϵ����Ա������");
			return false;
		}else if(lft>=5){
			super.doError(httpServletRequest, "��¼��Ϊ��"+username+"����¼ʧ�ܳ���5�Σ�����ϵ����Ա������");
			return false;
		}
		HBUtil.executeUpdate("update smt_user set Logonfailtimes=0 where userid='"+user.getId()+"'");
		return true;
	}

}
