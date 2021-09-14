package com.insigma.siis.local.business.axis.gzoa;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.hsqldb.lib.MD5;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.entity.SmtUserGroup;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtOaorg;
import com.insigma.odin.framework.privilege.entity.SmtOauser;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.rpc.ServiceException;

 

public class caClient2 {

   final static String endpoint = "http://222.88.130.104:8089/webservice/Async/approval/ws/1";//?wsdl
            
String[] s = {"","",""};
    public static void main(String[] args) throws ParseException, AppException {

	    //添加用户
		
	    //HashMap<String,String> user = getUserByUserId("王凤友");
 	    //System.out.println("result is "+user);
	    //HashMap<String,String> dept = getDeptById("250");
   	    //System.out.println("result is "+dept);
	    HashMap<String,String> user = getUserByToken("257509a8e9tzLIMpL0ddHYUitajmQr2VPSNh2Szc/n1/ZTcURQO2BlDilO4QkdIHLQCClYACtaYHgnt/TXdfR2eiCW5qgm2SHEhpAlqmnAbg");
   	    System.out.println("result is "+user);
    	

	    
    }

	/**
     * $authToken = time().md5(‘time=’.time().’&authtoken’);
$data = $client->authtoken($token, $authToken);
print_r($data);
?>
参数：
名称	说明
token	用于请求用户数据的token，当前可用测试参数：257509a8e9tzLIMpL0ddHYUitajmQr2VPSNh2Szc/n1/ZTcURQO2BlDilO4QkdIHLQCClYACtaYHgnt/TXdfR2eiCW5qgm2SHEhpAlqmnAbg
authToken	接口认证参数

     * 获得根据id获得用户
     * @return
     */
    public static HashMap<String,String> getUserByToken(String token) {
		HashMap<String,String> user = null ;
		LinkedHashMap<String,String> param = new LinkedHashMap<String,String>();
		try {
			param.put("token", token);
			user = (HashMap<String,String>) actionOAService("authtoken",param);
		  }catch (Exception e) {
			  e.printStackTrace();
		  }
		return user;
	}
    /**
     * 获得根据id获得用户
     * @return
     */
    public static HashMap<String,String> getUserById(String id) {
		HashMap<String,String> user = null ;
		LinkedHashMap<String,String> param = new LinkedHashMap<String,String>();
		try {
			param.put("single", "1");
			param.put("id", id);
			user = (HashMap<String,String>) actionOAService("getuser",param);
		  }catch (Exception e) {
			  e.printStackTrace();
		  }
		return user;
	}
    /**
     * 获得根据id获得用户
     * @return
     */
    public static HashMap<String,String> getUserByUserId(String userid) {
		HashMap<String,String> user = null ;
		LinkedHashMap<String,String> param = new LinkedHashMap<String,String>();
		try {
			param.put("single", "1");
			param.put("user_id", userid);
			user = (HashMap<String,String>) actionOAService("getuser",param);
		  }catch (Exception e) {
			  e.printStackTrace();
		  }
		return user;
	}
    /**
     * 获得所有用户
     * @return
     */
    public static HashMap<String,String>[] getAllUser() {
		HashMap<String,String>[] users = null ;
		LinkedHashMap<String,String> param = new LinkedHashMap<String,String>();
		try {
			param.put("single", "0");
			users = (HashMap[]) actionOAService("getuser",param);
		  }catch (Exception e) {
			  e.printStackTrace();
		  }
		return users;
	}
    /**
     * 获得所有机构
     * @return
     */
    public static HashMap<String,String>[] getAllDept() {
		HashMap<String,String>[] users = null ;
		LinkedHashMap<String,String> param = new LinkedHashMap<String,String>();
		try {
			param.put("single", "0");
			users = (HashMap<String,String>[]) actionOAService("getdept",param);
		  }catch (Exception e) {
			  e.printStackTrace();
		  }
		return users;
	}
    /**
     * 根据Id获得机构
     * @return
     */
    public static HashMap<String,String> getDeptById(String id) {
		HashMap<String,String> users = null ;
		LinkedHashMap<String,String> param = new LinkedHashMap<String,String>();
		try {
			param.put("single", "1");
			param.put("id", id);
			users = (HashMap<String,String>) actionOAService("getdept",param);
		  }catch (Exception e) {
			  e.printStackTrace();
		  }
		return users;
	}
    /**
     * 接口基本方法
     * @param param
     * @return
     */
	private static Object actionOAService(String function,LinkedHashMap<String,String> param) {
		String authToken = String.valueOf(Long.toString((new Date().getTime()/1000)))+MD5.encodeString("time="+String.valueOf(Long.toString((new Date().getTime()/1000)))+"&"+function, null); 
		//直接引用远程的wsdl文件
		 Service service = new Service();
		 Object result = null;
		try {
			 Call call = (Call) service.createCall();
			 call.setTargetEndpointAddress(endpoint);
			 call.setOperationName(function);//WSDL里面描述的接口名称
			 //接口的参数
		     String[] params = new String[param.size()+1];
		     params[0] = authToken;
			 call.addParameter("authToken", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			 int i=1;
			 for(Map.Entry entry:param.entrySet()) {
				 call.addParameter((String)entry.getKey(), org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
				 params[i] = param.get(entry.getKey());
				 i++;
			 }
			 result = call.invoke(params);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/* 插入用户 
	 * 
	 * 思路
	 * 1先查询SmtUser是否有用户
	 * 2判断是否存在，否就添加，先添加SmtUser，再根据 loginname 找到Userid，再插入 smtoauser
	 * */
	@SuppressWarnings("unchecked")
	public void addUser(HashMap<String, String> map, String oWNER) throws ParseException {
		@SuppressWarnings("static-access")
		HBSession sess = new HBUtil().getHBSession();
		String sql ="select *  from SMT_USER where USERID='"+map.get("id")+"'";
		List<SmtUser> list= sess.createSQLQuery(sql).addEntity(SmtUser.class).list();
		if(list.size()<=0) {
			SmtUser smtu=new SmtUser();
			smtu.setId(map.get("id"));
			smtu.setCheckip(null);   //是否检查IP			
			smtu.setCreatedate(new Date());  //创建时间
			smtu.setDept(map.get("org_id"));    //部门
			//smtu.setDesc(map.get("Desc"));    //描述
			smtu.setEmail(map.get("email"));  //邮件
			smtu.setEmpid(null);  //不可维护类别
			//smtu.setHashcode(map.get("Hashcode")); //散列值
			smtu.setIplist(null);   //IPLIST
			smtu.setIsleader("0");  //是否领导
			smtu.setLoginname(map.get("user_id"));  //登录名
			smtu.setMacaddr(map.get("bind_ip"));   //网卡地址
			smtu.setMobile(map.get("mobile"));   //手机
			smtu.setName(map.get("User_Name"));   //用户名称
			smtu.setOtherinfo(null);   //其他信息
			smtu.setOwnerId(oWNER);   //创建者
			smtu.setPasswd("000000");     //密码
			smtu.setRate(null);   //不可浏览类别
			smtu.setRegionid(null);  //区域代码
			smtu.setSec(null);    //密文
			smtu.setStatus("1");     //用户是否有效
			smtu.setTel(map.get("tel_dept"));    //办公电话
			smtu.setUsertype("2"); //用户类别
			smtu.setValidity(null);  //有效期
			smtu.setWork(map.get("org_name"));    //工作单位
			smtu.setSortid(Long.parseLong(map.get("order_no")));   //用户排序
			sess.save(smtu);
			
			SmtOauser smtoa= new SmtOauser();
			smtoa.setAddress_home(map.get("address_home"));  //家庭地址
			smtoa.setBind_ip(map.get("bind_ip"));  //绑定ip
			smtoa.setBirthday(map.get("birthday"));   //出生日期yyyy-MM-dd
			smtoa.setByname(map.get("byname"));  //用户别名
			smtoa.setEmail(map.get("email"));      //邮箱
			smtoa.setFax_dept(map.get("fax_dept"));  //部门传真
			smtoa.setGender(map.get("gender"));  //性别（1男0女）
			smtoa.setMobile(map.get("mobile"));   //移动电话
			smtoa.setNot_login(map.get("not_login"));  //是否禁止登录（1禁止0正常）
			smtoa.setOrder_no(map.get("order_no")); //用户排序号
			smtoa.setOrg_id(map.get("org_id"));     //  部门id
			smtoa.setOrg_name(map.get("org_name"));    //部门名称
			smtoa.setPosition_name(map.get("position_name"));   //  职位信息，多个职位通过,隔开
			smtoa.setPostcode_home(map.get("postcode_home"));  //家庭邮编
			smtoa.setQq(map.get("qq"));    //qq
			smtoa.setSignature(map.get("signature"));   //个性签名
			smtoa.setTel_dept(map.get("tel_dept"));  //部门电话
			smtoa.setTel_home(map.get("tel_home"));  //家庭电话
			smtoa.setUser_id(map.get("user_id"));   //用户名
			smtoa.setOrg_name(map.get("user_name"));  //用户姓名 
			smtoa.setUser_name_index(map.get("user_name_index"));  //用户姓名索引
			smtoa.setId(map.get("id"));
			
			
			/*
			 * {org_name=信息处, user_name_index=xj*t*g*l*y, birthday=0000-00-00, byname=,
			 * postcode_home=, not_login=0, fax_dept=, order_no=31, position_name=处长（主任）,
			 * org_id=23, id=1, user_name=系统管理员, bind_ip=, email=, address_home=, tel_dept=,
			 * gender=0, user_id=admin, qq=, tel_home=, signature=, mobile=}
			 */
			String Self_Userid = (String) HBUtil.getHBSession().createSQLQuery("select USERID  from SMT_USER where LOGINNAME= '"+map.get("user_id")+"'").uniqueResult();
			smtoa.setSelf_userid(Self_Userid); //关联干部信息系统userid
			sess.save(smtoa);
			
			SmtUserGroup  sg=new SmtUserGroup();
			sg.setId(UUID.randomUUID().toString().replace("-", ""));
			sg.setName(map.get("byname"));
			sg.setSid("oa01");
			sg.setSortid(map.get("order_no"));
			sg.setUserid(map.get("id"));
			sess.save(sg);
			
			/* 调用函数添加干部监督，和干部教育的账户 */
			 String hql = "select SaveUser(:systype,:uuid,:username,:loginname,:pwd,:ustatus,:roleid) from dual";
	    	 sess.createSQLQuery(hql)
	    	 .setString("systype", "gbjy")    
	    	 .setString("uuid", UUID.randomUUID().toString())
	    	 .setString("username", map.get("user_name"))
	    	 .setString("loginname",map.get("user_id")) 
	    	 .setString("pwd","000000")
	    	 .setString("ustatus","2")
	    	 .setString("roleid","logAdmin"); 
	    	 //干部教育
	    	 
	    	 sess.createSQLQuery(hql).setString("systype", "gbjd")
	    	 .setString("uuid", UUID.randomUUID().toString())
	    	 .setString("username", map.get("user_name"))
	    	 .setString("loginname",map.get("user_id")) 
	    	 .setString("pwd","000000")
	    	 .setString("ustatus","2")
	    	 .setString("roleid","logAdmin"); 
	    	 ; //干部监督
	    	 
	    	 sess.createSQLQuery(hql).setString("systype", "gbgl")
	    	 .setString("uuid", UUID.randomUUID().toString())
	    	 .setString("username", map.get("user_name"))
	    	 .setString("loginname",map.get("user_id")) 
	    	 .setString("pwd","000000")
	    	 .setString("ustatus","2")
	    	 .setString("roleid","logAdmin"); 
	    	 ; //干部监督
	    	 
		}
	}

	/* 添加OA组织机构 */
    public void addDept(HashMap<String, String> map) throws AppException {
    	HBUtil.executeUpdate("delete from SmtOaorg");   //删除原有数据
    	SmtOaorg smtoao  =new SmtOaorg();
    	smtoao.setFax_no(map.get("fax_no"));              //部门传真
    	smtoao.setOrg_address(map.get("org_address"));    //部门地址
    	smtoao.setOrg_func(map.get("org_func"));          //部门职能
    	smtoao.setOrg_name(map.get("org_name"));		  //部门名称
    	smtoao.setOrg_no(map.get("org_no"));			 //部门编号
    	smtoao.setOrg_parent(map.get("org_parent"));     //部门上级
    	smtoao.setTel_no(map.get("tel_no"));             //部门电话
    	smtoao.setId(map.get("id"));     //id
    	
    	@SuppressWarnings("static-access")
		HBSession sess = new HBUtil().getHBSession();
    	sess.save(smtoao);
    	
	}
}