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

	    //����û�
		
	    //HashMap<String,String> user = getUserByUserId("������");
 	    //System.out.println("result is "+user);
	    //HashMap<String,String> dept = getDeptById("250");
   	    //System.out.println("result is "+dept);
	    HashMap<String,String> user = getUserByToken("257509a8e9tzLIMpL0ddHYUitajmQr2VPSNh2Szc/n1/ZTcURQO2BlDilO4QkdIHLQCClYACtaYHgnt/TXdfR2eiCW5qgm2SHEhpAlqmnAbg");
   	    System.out.println("result is "+user);
    	

	    
    }

	/**
     * $authToken = time().md5(��time=��.time().��&authtoken��);
$data = $client->authtoken($token, $authToken);
print_r($data);
?>
������
����	˵��
token	���������û����ݵ�token����ǰ���ò��Բ�����257509a8e9tzLIMpL0ddHYUitajmQr2VPSNh2Szc/n1/ZTcURQO2BlDilO4QkdIHLQCClYACtaYHgnt/TXdfR2eiCW5qgm2SHEhpAlqmnAbg
authToken	�ӿ���֤����

     * ��ø���id����û�
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
     * ��ø���id����û�
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
     * ��ø���id����û�
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
     * ��������û�
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
     * ������л���
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
     * ����Id��û���
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
     * �ӿڻ�������
     * @param param
     * @return
     */
	private static Object actionOAService(String function,LinkedHashMap<String,String> param) {
		String authToken = String.valueOf(Long.toString((new Date().getTime()/1000)))+MD5.encodeString("time="+String.valueOf(Long.toString((new Date().getTime()/1000)))+"&"+function, null); 
		//ֱ������Զ�̵�wsdl�ļ�
		 Service service = new Service();
		 Object result = null;
		try {
			 Call call = (Call) service.createCall();
			 call.setTargetEndpointAddress(endpoint);
			 call.setOperationName(function);//WSDL���������Ľӿ�����
			 //�ӿڵĲ���
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
	
	/* �����û� 
	 * 
	 * ˼·
	 * 1�Ȳ�ѯSmtUser�Ƿ����û�
	 * 2�ж��Ƿ���ڣ������ӣ������SmtUser���ٸ��� loginname �ҵ�Userid���ٲ��� smtoauser
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
			smtu.setCheckip(null);   //�Ƿ���IP			
			smtu.setCreatedate(new Date());  //����ʱ��
			smtu.setDept(map.get("org_id"));    //����
			//smtu.setDesc(map.get("Desc"));    //����
			smtu.setEmail(map.get("email"));  //�ʼ�
			smtu.setEmpid(null);  //����ά�����
			//smtu.setHashcode(map.get("Hashcode")); //ɢ��ֵ
			smtu.setIplist(null);   //IPLIST
			smtu.setIsleader("0");  //�Ƿ��쵼
			smtu.setLoginname(map.get("user_id"));  //��¼��
			smtu.setMacaddr(map.get("bind_ip"));   //������ַ
			smtu.setMobile(map.get("mobile"));   //�ֻ�
			smtu.setName(map.get("User_Name"));   //�û�����
			smtu.setOtherinfo(null);   //������Ϣ
			smtu.setOwnerId(oWNER);   //������
			smtu.setPasswd("000000");     //����
			smtu.setRate(null);   //����������
			smtu.setRegionid(null);  //�������
			smtu.setSec(null);    //����
			smtu.setStatus("1");     //�û��Ƿ���Ч
			smtu.setTel(map.get("tel_dept"));    //�칫�绰
			smtu.setUsertype("2"); //�û����
			smtu.setValidity(null);  //��Ч��
			smtu.setWork(map.get("org_name"));    //������λ
			smtu.setSortid(Long.parseLong(map.get("order_no")));   //�û�����
			sess.save(smtu);
			
			SmtOauser smtoa= new SmtOauser();
			smtoa.setAddress_home(map.get("address_home"));  //��ͥ��ַ
			smtoa.setBind_ip(map.get("bind_ip"));  //��ip
			smtoa.setBirthday(map.get("birthday"));   //��������yyyy-MM-dd
			smtoa.setByname(map.get("byname"));  //�û�����
			smtoa.setEmail(map.get("email"));      //����
			smtoa.setFax_dept(map.get("fax_dept"));  //���Ŵ���
			smtoa.setGender(map.get("gender"));  //�Ա�1��0Ů��
			smtoa.setMobile(map.get("mobile"));   //�ƶ��绰
			smtoa.setNot_login(map.get("not_login"));  //�Ƿ��ֹ��¼��1��ֹ0������
			smtoa.setOrder_no(map.get("order_no")); //�û������
			smtoa.setOrg_id(map.get("org_id"));     //  ����id
			smtoa.setOrg_name(map.get("org_name"));    //��������
			smtoa.setPosition_name(map.get("position_name"));   //  ְλ��Ϣ�����ְλͨ��,����
			smtoa.setPostcode_home(map.get("postcode_home"));  //��ͥ�ʱ�
			smtoa.setQq(map.get("qq"));    //qq
			smtoa.setSignature(map.get("signature"));   //����ǩ��
			smtoa.setTel_dept(map.get("tel_dept"));  //���ŵ绰
			smtoa.setTel_home(map.get("tel_home"));  //��ͥ�绰
			smtoa.setUser_id(map.get("user_id"));   //�û���
			smtoa.setOrg_name(map.get("user_name"));  //�û����� 
			smtoa.setUser_name_index(map.get("user_name_index"));  //�û���������
			smtoa.setId(map.get("id"));
			
			
			/*
			 * {org_name=��Ϣ��, user_name_index=xj*t*g*l*y, birthday=0000-00-00, byname=,
			 * postcode_home=, not_login=0, fax_dept=, order_no=31, position_name=���������Σ�,
			 * org_id=23, id=1, user_name=ϵͳ����Ա, bind_ip=, email=, address_home=, tel_dept=,
			 * gender=0, user_id=admin, qq=, tel_home=, signature=, mobile=}
			 */
			String Self_Userid = (String) HBUtil.getHBSession().createSQLQuery("select USERID  from SMT_USER where LOGINNAME= '"+map.get("user_id")+"'").uniqueResult();
			smtoa.setSelf_userid(Self_Userid); //�����ɲ���Ϣϵͳuserid
			sess.save(smtoa);
			
			SmtUserGroup  sg=new SmtUserGroup();
			sg.setId(UUID.randomUUID().toString().replace("-", ""));
			sg.setName(map.get("byname"));
			sg.setSid("oa01");
			sg.setSortid(map.get("order_no"));
			sg.setUserid(map.get("id"));
			sess.save(sg);
			
			/* ���ú�����Ӹɲ��ල���͸ɲ��������˻� */
			 String hql = "select SaveUser(:systype,:uuid,:username,:loginname,:pwd,:ustatus,:roleid) from dual";
	    	 sess.createSQLQuery(hql)
	    	 .setString("systype", "gbjy")    
	    	 .setString("uuid", UUID.randomUUID().toString())
	    	 .setString("username", map.get("user_name"))
	    	 .setString("loginname",map.get("user_id")) 
	    	 .setString("pwd","000000")
	    	 .setString("ustatus","2")
	    	 .setString("roleid","logAdmin"); 
	    	 //�ɲ�����
	    	 
	    	 sess.createSQLQuery(hql).setString("systype", "gbjd")
	    	 .setString("uuid", UUID.randomUUID().toString())
	    	 .setString("username", map.get("user_name"))
	    	 .setString("loginname",map.get("user_id")) 
	    	 .setString("pwd","000000")
	    	 .setString("ustatus","2")
	    	 .setString("roleid","logAdmin"); 
	    	 ; //�ɲ��ල
	    	 
	    	 sess.createSQLQuery(hql).setString("systype", "gbgl")
	    	 .setString("uuid", UUID.randomUUID().toString())
	    	 .setString("username", map.get("user_name"))
	    	 .setString("loginname",map.get("user_id")) 
	    	 .setString("pwd","000000")
	    	 .setString("ustatus","2")
	    	 .setString("roleid","logAdmin"); 
	    	 ; //�ɲ��ල
	    	 
		}
	}

	/* ���OA��֯���� */
    public void addDept(HashMap<String, String> map) throws AppException {
    	HBUtil.executeUpdate("delete from SmtOaorg");   //ɾ��ԭ������
    	SmtOaorg smtoao  =new SmtOaorg();
    	smtoao.setFax_no(map.get("fax_no"));              //���Ŵ���
    	smtoao.setOrg_address(map.get("org_address"));    //���ŵ�ַ
    	smtoao.setOrg_func(map.get("org_func"));          //����ְ��
    	smtoao.setOrg_name(map.get("org_name"));		  //��������
    	smtoao.setOrg_no(map.get("org_no"));			 //���ű��
    	smtoao.setOrg_parent(map.get("org_parent"));     //�����ϼ�
    	smtoao.setTel_no(map.get("tel_no"));             //���ŵ绰
    	smtoao.setId(map.get("id"));     //id
    	
    	@SuppressWarnings("static-access")
		HBSession sess = new HBUtil().getHBSession();
    	sess.save(smtoao);
    	
	}
}