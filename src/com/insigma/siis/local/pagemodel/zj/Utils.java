package com.insigma.siis.local.pagemodel.zj;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.sys.SysfunctionManager;
import com.insigma.odin.framework.util.BASE64Encoder;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class Utils {
    private static String STR_EMPTY = "";

    // ���������ַ� ��������������ַ���ֻ�������ֺ���ĸ
    public static String StringFilter(String str) throws PatternSyntaxException {
	if (null == str) {
	    return STR_EMPTY;
	}
	String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����&*��������+|{}������������������������]";
	Pattern p = Pattern.compile(regEx);
	Matcher m = p.matcher(str);
	return m.replaceAll(STR_EMPTY).trim();
    }
    
    /**����MD5���м���
     * @param str  �����ܵ��ַ���
     * @return  ���ܺ���ַ���
     * @throws NoSuchAlgorithmException  û�����ֲ�����ϢժҪ���㷨
     * @throws UnsupportedEncodingException  
     */
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //ȷ�����㷽��
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //���ܺ���ַ���
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
    
    /**
	 * MD5����
	 * @param plainText  ����
	 * @return 32λ�ܔ�
	 */
	public static String encryption(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes("UTF-8"));
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re_md5;
	}
    
    public static List<String> getButtonList(){
    	List<String> buttons = new ArrayList<String>();
    	if("40288103556cc97701556d629135000f".equals(SysManagerUtils.getUserId())){
    		return buttons;
    	}
    	try {
			HBSession sess = HBUtil.getHBSession();
			//��ø�ҳ���£���������Щ ��ť Ȩ��
			FunctionVO func = SysfunctionManager.getCurrentSysfunction();
			String id = func.getFunctionid();
			List<Object[]> buttonList = sess.createSQLQuery("select s.functionid,s.buttonid from smt_function_button s where s.parent = '"+id+"'").list();
			
			if(buttonList!=null&&buttonList.size()>0){
				//��õ�ǰ�û��Ķ�Ӧ��ɫ����ӵ�е�����Ȩ��
				List<String> roleidList =  (List<String>)sess.createSQLQuery("select a.roleid from (select nvl2(sa.actid, 'true', 'false') rolecheck, sr.roleid "
						+ "from smt_role sr left join smt_act sa on sr.roleid = sa.roleid "
						+ "and sa.userid = '"+SysManagerUtils.getUserId()+"') a "
						+ "where a.rolecheck = 'true'").list();
				
				String roleidL = "";
				if(roleidList!=null&&roleidList.size()>1){
					roleidL = StringUtils.join(roleidList.toArray(), "','");
				}else if(roleidList!=null&&roleidList.size()==1){
					roleidL = roleidList.get(0);
				}
				List<String> resourceList = (List<String>)sess.createSQLQuery("select distinct s.resourceid from smt_acl s where s.roleid in ('"+roleidL+"')").list();
				
				//���� ��ť���ж�����Ȩ�����Ƿ�����˸ð�ťȨ�ޣ���������Ȩ��     ��������û��Ȩ��
				for(int i=0;i<buttonList.size();i++){
					Object[] objs = buttonList.get(i);
					String functionid = ""+objs[0];
					String buttonid = ""+objs[1];
					//�Ѳ������ķŽ�ȥ����
					if(!resourceList.contains(functionid)){
						buttons.add(buttonid);
					}
				}
				
			}

		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buttons;
    }
    
    /**
	 * ����userid����û���Ӧ�Ľ�ɫroleid
	 * return �ַ��� 
	 * eg: 4028c7cd252f4ccd01252f4e9df50005','4028b88157b190130157b190e74c0006
	 * ���roleid in ('"+...+"')ʹ��
	 */
    public static String getRoleId(String userid){
    	HBSession sess = HBUtil.getHBSession();
    	//��õ�ǰ�û��Ķ�Ӧ��ɫ
		List<String> roleidList =  (List<String>)sess.createSQLQuery("select a.roleid from (select nvl2(sa.actid, 'true', 'false') rolecheck, sr.roleid "
				+ "from smt_role sr left join smt_act sa on sr.roleid = sa.roleid "
				+ "and sa.userid = '"+userid+"') a "
				+ "where a.rolecheck = 'true'").list();
		String roleidL = "";
		if(roleidList!=null&&roleidList.size()>1){
			roleidL = StringUtils.join(roleidList.toArray(), "','");
		}else if(roleidList!=null&&roleidList.size()==1){
			roleidL = roleidList.get(0);
		}
    	return roleidL;
    }
   
    /**
	 * ����userid����û���Ӧ�Ľ�ɫroleid
	 * return list
	 */
    public static List<String> getRoleIdList(String userid){
    	HBSession sess = HBUtil.getHBSession();
    	//��õ�ǰ�û��Ķ�Ӧ��ɫ
		List<String> roleidList =  (List<String>)sess.createSQLQuery("select a.roleid from (select nvl2(sa.actid, 'true', 'false') rolecheck, sr.roleid "
				+ "from smt_role sr left join smt_act sa on sr.roleid = sa.roleid "
				+ "and sa.userid = '"+userid+"') a "
				+ "where a.rolecheck = 'true'").list();
    	return roleidList;
    }
    
    public static String clearA1701(String str) throws PatternSyntaxException {
    	if (null == str) {
    	    return STR_EMPTY;
    	}
    	str = str.replace(" .", ".");
    	str = str.replace(". ", ".");
    	str = str.replace("- -", "--");
    	//String regEx = "[~!@#$%^|~��@#��%����|��?**=`]";
    	//Pattern p = Pattern.compile(regEx);
    	//Matcher m = p.matcher(str);
    	return str.trim();
   }
}
