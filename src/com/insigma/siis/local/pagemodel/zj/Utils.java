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

    // 过滤特殊字符 清除掉所有特殊字符，只保留数字和字母
    public static String StringFilter(String str) throws PatternSyntaxException {
	if (null == str) {
	    return STR_EMPTY;
	}
	String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	Pattern p = Pattern.compile(regEx);
	Matcher m = p.matcher(str);
	return m.replaceAll(STR_EMPTY).trim();
    }
    
    /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException  
     */
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
    
    /**
	 * MD5加密
	 * @param plainText  明文
	 * @return 32位密斿
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
			//获得该页面下，设置了哪些 按钮 权限
			FunctionVO func = SysfunctionManager.getCurrentSysfunction();
			String id = func.getFunctionid();
			List<Object[]> buttonList = sess.createSQLQuery("select s.functionid,s.buttonid from smt_function_button s where s.parent = '"+id+"'").list();
			
			if(buttonList!=null&&buttonList.size()>0){
				//获得当前用户的对应角色，所拥有的所有权限
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
				
				//遍历 按钮，判断所有权限中是否包含了该按钮权限，包含：有权限     不包含：没有权限
				for(int i=0;i<buttonList.size();i++){
					Object[] objs = buttonList.get(i);
					String functionid = ""+objs[0];
					String buttonid = ""+objs[1];
					//把不包含的放进去即可
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
	 * 根据userid获得用户对应的角色roleid
	 * return 字符串 
	 * eg: 4028c7cd252f4ccd01252f4e9df50005','4028b88157b190130157b190e74c0006
	 * 配合roleid in ('"+...+"')使用
	 */
    public static String getRoleId(String userid){
    	HBSession sess = HBUtil.getHBSession();
    	//获得当前用户的对应角色
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
	 * 根据userid获得用户对应的角色roleid
	 * return list
	 */
    public static List<String> getRoleIdList(String userid){
    	HBSession sess = HBUtil.getHBSession();
    	//获得当前用户的对应角色
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
    	//String regEx = "[~!@#$%^|~！@#￥%……|？?**=`]";
    	//Pattern p = Pattern.compile(regEx);
    	//Matcher m = p.matcher(str);
    	return str.trim();
   }
}
