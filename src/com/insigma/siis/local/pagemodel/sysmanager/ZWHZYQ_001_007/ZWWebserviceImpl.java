package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Transaction;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.Column;
import com.insigma.siis.local.business.entity.InterfaceConfig;
import com.insigma.siis.local.business.entity.InterfaceLog;
import com.insigma.siis.local.business.entity.InterfaceParameter;
import com.insigma.siis.local.business.entity.InterfaceScript;
import com.insigma.siis.local.business.entity.Param;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_BS;
import com.insigma.siis.local.epsoft.fileList.FileListPathUtil;
import com.insigma.siis.local.epsoft.util.StringUtil;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.DeleteInfo;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.DeleteSingle;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.QueryPhoto;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA01;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA02;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA06;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA08;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA11;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA14;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA15;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA29;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA30;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA31;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA36;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA37;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA57;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA60;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA61;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA62;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA63;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveA64;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.SaveB01;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.saveTable.UpdateField;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ZWWebserviceImpl implements ZWWebservice {
	ZWHZYQ_001_007_BS bs7 = new ZWHZYQ_001_007_BS();
	
	//???????? 0:????????  1:????
	private String status = "0";
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIP(){
		MessageContext  mc  =  MessageContext.getCurrentContext();
	    HttpServletRequest  request  =  (HttpServletRequest)  mc.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
	    String callerIP = request.getRemoteAddr();
	    return callerIP;
	}
	
	public String getUserName() {
		MessageContext  mc  =  MessageContext.getCurrentContext();
		String userName = mc.getUsername();
		return userName;
	}

	public String getPath() throws RadowException {
		FileListPathUtil util = new FileListPathUtil();
		String path = util.getFileListPath();
		path += File.separator+"WEB-INF"+File.separator+"dateSource.properties";
		return path;
	}
	/**
	 * ????dateSource.properties????Connection
	 * @param path
	 * @return
	 */
	public Connection getConn() {
		String path = "";
		Connection conn = null;
		Properties prop = new Properties();
		try {
			path = getPath();
			InputStream in = new FileInputStream(new File(path));
			prop.load(in);
			String ip = prop.getProperty("ip");
			String port = prop.getProperty("port");
			String servername = prop.getProperty("servername");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			String dbType = prop.getProperty("dbType");
			String url = null;
			DriverManager.setLoginTimeout(1);
			if("oracle".equalsIgnoreCase(dbType)){
				try{
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
					} catch (InstantiationException e) {
						throw new Exception("ORACLE????????????????????:"+e.getMessage());
					} catch (IllegalAccessException e) {
						throw new Exception("ORACLE????????????:"+e.getMessage());
					} catch (ClassNotFoundException e) {
						throw new Exception("ORACLE????????????:"+e.getMessage());
					}
					url = "jdbc:oracle:thin:@" + ip + ":" + port + ":"+ servername;
					try {
						conn = DriverManager.getConnection(url, username, password);
					} catch (SQLException e) {
						throw new Exception("ORACLE????????????:"+e.getMessage());
					}
				}catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			} else  if("mysql".equalsIgnoreCase(dbType)) {
				try{
					try {
						Class.forName("com.mysql.jdbc.Driver");
					} catch (ClassNotFoundException e) {
						throw new Exception("MYSQL????????????:"+e.getMessage());
					}
					url = "jdbc:mysql://" + ip + ":" + port+"/"+servername;
					try {
						conn = DriverManager.getConnection(url, username, password);
					} catch (SQLException e) {
						throw new Exception("MYSQL????????????:"+e.getMessage());
					}
				}catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			}
		} catch (Exception e) {
			conn = HBUtil.getHBSession().connection();
		}
		return conn;
	}
	
	/**
	 * ??????????webservice????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
	 * @param INTERFACE_CONFIG_ID ????????????????????????
	 * @param INTERFACE_SCRIPT_ID ????????????????????????????
	 * @param QUERY_PARAM         ????????????????
	 * @return ????XML??????????????<br>
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String zwhzyqAccessit(String INTERFACE_CONFIG_ID, String INTERFACE_SCRIPT_ID, String QUERY_PARAM) {

		
		String username ="";
		if("1".equals(status)){
			//????????????????
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			username = user.getName();
		}
		HBSession session=HBUtil.getHBSession();              		    //??????????????????????
		InterfaceConfig interfaceConfig;
		InterfaceScript interfaceScript;
		try {
			interfaceConfig = bs7.getConfigById(INTERFACE_CONFIG_ID);
			interfaceScript = bs7.getScriptById(INTERFACE_CONFIG_ID, INTERFACE_SCRIPT_ID);
		} catch (RuntimeException e2) {
			throw new RuntimeException("????????????");
		}             
		//????INTERFACE_CONFIG_ID??INTERFACE_SCRIPT_ID????????????????
		String XMLData = "";
		Document document = DocumentHelper.createDocument();                  //????document???? 
		Element SERVICEINTERFACE = document.addElement("SERVICEINTERFACE");   //????XML????????
		Element FAHEAD = SERVICEINTERFACE.addElement("FAHEAD");               //????XML??????????????"FAHEAD",??????????????
		Element SCRIPTHEAD = SERVICEINTERFACE.addElement("SCRIPTHEAD");       //????XML??????????????"SCRIPTHEAD",??????????????
		Element DATA = SERVICEINTERFACE.addElement("Data");                   //????XML??????????????"DATA",??????????
		String tableName = interfaceScript.getTargetTableName();
		DATA.addAttribute("version","1.0");
		String scriptSQL = "";
		String IP = "localhost";
		if(username == "" || "".equals(username)){
			username = "????";
		}
		if("0".equals(status)){
			IP = getIP();
			username = getUserName();
		}
		
		//????    ????????INTERFACE_CONFIG_ID(????????????????????????)????????
		if(INTERFACE_CONFIG_ID==null || "".equals(INTERFACE_CONFIG_ID)){
			FAHEAD.addAttribute("Flag", "0101");                                 //??????????????Flag(??????????)
			FAHEAD.addAttribute("Mess", "0101.????????INTERFACE_CONFIG_ID(????????????????????????)??????");//??????????????Mess(????????????)
			DATA.setText("null");                                                //????????
			XMLData = document.asXML().replaceAll("-&gt;", "->");
			CreateInterfaceLog(INTERFACE_CONFIG_ID, interfaceConfig.getInterfaceConfigName(), INTERFACE_SCRIPT_ID, interfaceScript.getInterfaceScriptName(), interfaceScript.getInterfaceScriptSql(), interfaceScript.getInterfaceScriptSql(), IP, "0102", username, interfaceConfig.getInterfaceConfigDesc());
			return XMLData;								                         //??????????????????????????????
		}
		
		//????    ??????????????
		if("0".equals(interfaceConfig.getPublishStateId()) && "0".equals(status)){
			FAHEAD.addAttribute("Flag", "0202");                                          //??????????????Flag(??????????)
			FAHEAD.addAttribute("Mess", "0202.??????????????,??????????????????");//??????????????Mess(????????????)
			DATA.setText("null");                                                         //????????
			XMLData = document.asXML().replaceAll("-&gt;", "->");
			CreateInterfaceLog(INTERFACE_CONFIG_ID, interfaceConfig.getInterfaceConfigName(), INTERFACE_SCRIPT_ID, interfaceScript.getInterfaceScriptName(), interfaceScript.getInterfaceScriptSql(), interfaceScript.getInterfaceScriptSql(), IP, "0102", username, interfaceConfig.getInterfaceConfigDesc());
			return XMLData;
		}
		//**==========================??????????????========================================*//*
		//????INTERFACE_CONFIG_ID ????????????????????????????????????????????????????????
		if(interfaceConfig==null){
			FAHEAD.addAttribute("Flag", "0102");                                                                          //??????????????Flag(??????????)
			FAHEAD.addAttribute("Mess", "0102.????????INTERFACE_CONFIG_ID(????????????????????????)??INTERFACE_CONFIG????????????"); //??????????????Mess(????????????)
			DATA.setText("null");                                                                                        //????????
			XMLData = document.asXML().replaceAll("-&gt;", "->"); 
			CreateInterfaceLog(INTERFACE_CONFIG_ID, interfaceConfig.getInterfaceConfigName(), INTERFACE_SCRIPT_ID, interfaceScript.getInterfaceScriptName(), interfaceScript.getInterfaceScriptSql(), interfaceScript.getInterfaceScriptSql(), IP, "0102", username, interfaceConfig.getInterfaceConfigDesc());
			return XMLData;
		} else if ("0".equals(interfaceConfig.getAvailabilityStateId())) {
			FAHEAD.addAttribute("Flag", "0103");                               //??????????????Flag(??????????)
			FAHEAD.addAttribute("Mess", "0103.????????????????????");               //??????????????Mess(????????????)
			DATA.setText("null");                                              //????????
			XMLData = document.asXML().replaceAll("-&gt;", "->");
			CreateInterfaceLog(INTERFACE_CONFIG_ID, interfaceConfig.getInterfaceConfigName(), INTERFACE_SCRIPT_ID, interfaceScript.getInterfaceScriptName(), interfaceScript.getInterfaceScriptSql(), interfaceScript.getInterfaceScriptSql(), IP, "0102", username, interfaceConfig.getInterfaceConfigDesc());
			return XMLData;
		} else if ("1".equals(interfaceConfig.getAvailabilityStateId())) {     //????????????
			if(interfaceScript==null) {
				FAHEAD.addAttribute("Flag", "0102");                                   //??????????????Flag(??????????)
				FAHEAD.addAttribute("Mess", "0102.????????????????????????????????????????????"); //??????????????Mess(????????????)
				DATA.setText("null");                                                  //????????
				XMLData = document.asXML().replaceAll("-&gt;", "->");
				CreateInterfaceLog(INTERFACE_CONFIG_ID, interfaceConfig.getInterfaceConfigName(), INTERFACE_SCRIPT_ID, interfaceScript.getInterfaceScriptName(), interfaceScript.getInterfaceScriptSql(), interfaceScript.getInterfaceScriptSql(), IP, "0102", username, interfaceConfig.getInterfaceConfigDesc());
				return XMLData;
			}
			List<Param> params =null;
			try {
				params = jsonToParam(QUERY_PARAM);
			} catch (Exception e) {
				FAHEAD.addAttribute("Flag", "0302");                                   //??????????????Flag(??????????)
				FAHEAD.addAttribute("Mess", "0302.????????????????????????,"+e.getMessage());//??????????????Mess(????????????)
				DATA.setText("null");                                                  //????????
				XMLData = document.asXML().replaceAll("-&gt;", "->");
				CreateInterfaceLog(INTERFACE_CONFIG_ID, interfaceConfig.getInterfaceConfigName(), INTERFACE_SCRIPT_ID, interfaceScript.getInterfaceScriptName(), interfaceScript.getInterfaceScriptSql(), interfaceScript.getInterfaceScriptSql(), IP, "0102", username, interfaceConfig.getInterfaceConfigDesc());
				return XMLData;
			}
			
			List<InterfaceParameter> interfaceParameters =(List<InterfaceParameter>)session.createQuery
			("from InterfaceParameter r where r.id.interfaceConfigId=:INTERFACE_CONFIG_ID order by r.interfaceParameterSequence").setParameter("INTERFACE_CONFIG_ID", INTERFACE_CONFIG_ID).list();
			HashMap<String,String> lackParam = new HashMap<String, String>();//????????????????????????
			HashMap<String,String> errorParam = new HashMap<String, String>();//????????????????????????
			//????????????????????????????
			if(params != null && !params.isEmpty()) {
				if(interfaceParameters!=null && !interfaceParameters.isEmpty()) {
					for(int i=0; i<interfaceParameters.size(); i++) {
						int count = 0;
						for(int j=0;j<params.size();j++){
							if(interfaceParameters.get(i).getId().getInterfaceParameterName().equalsIgnoreCase(params.get(j).getName())
							&& getCodeValueName(interfaceParameters.get(i).getInterfaceParameterType()).equalsIgnoreCase(params.get(j).getType())){
								count++;//????????
                             }
						}
						//????????????????lackParam??
						if(count==0){
							try {
								lackParam.put(interfaceParameters.get(i).getId().getInterfaceParameterName(),getCodeValueName(interfaceParameters.get(i).getInterfaceParameterType()));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			//????????????????????????????????????????????????????????
			if(interfaceParameters!=null&&!interfaceParameters.isEmpty()){
				for(int i = 0;i<params.size();i++){
					int count=0;
					for(int j=0;j<interfaceParameters.size();j++){
						
						try {
							if(params.get(i).getName().equalsIgnoreCase(interfaceParameters.get(j).getId().getInterfaceParameterName())
									&&params.get(i).getType().equalsIgnoreCase(getCodeValueName(interfaceParameters.get(j).getInterfaceParameterType()))){
								count++;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						
					}
					if(count==0){
						errorParam.put(params.get(i).getName(),params.get(i).getType());
					}
				}
			}
			if((lackParam!=null && !lackParam.isEmpty())||(errorParam!=null && !errorParam.isEmpty())){//??????????????????
				StringBuffer sb = new StringBuffer();
				if(lackParam!=null && !lackParam.isEmpty()){
					sb.append("??????????????:");
					Set<Map.Entry<String, String>> set=lackParam.entrySet();
					for(Iterator<Map.Entry<String, String>> it=set.iterator();it.hasNext();){
						Map.Entry<String, String> mapEnter=it.next();
						sb.append("????:"+mapEnter.getKey()+",????:"+mapEnter.getValue()+";");
					}
				}
				if(errorParam!=null && !errorParam.isEmpty()){
					sb.append("??????????????:");
					Set<Map.Entry<String, String>> set=errorParam.entrySet();
					for(Iterator<Map.Entry<String, String>> it=set.iterator();it.hasNext();){
						Map.Entry<String, String> mapEnter=it.next();
						sb.append("????:"+mapEnter.getKey()+",????:"+mapEnter.getValue()+";");
					}
				}
				FAHEAD.addAttribute("Flag", "0201");                                                   //??????????????Flag(??????????)
				FAHEAD.addAttribute("Mess", "0201.????????????????????????"+sb.toString());                  //??????????????Mess(????????????)
				DATA.setText("null");                                                                  //????????
				XMLData = document.asXML().replaceAll("-&gt;", "->");
				CreateInterfaceLog(INTERFACE_CONFIG_ID, interfaceConfig.getInterfaceConfigName(), INTERFACE_SCRIPT_ID, interfaceScript.getInterfaceScriptName(), interfaceScript.getInterfaceScriptSql(), interfaceScript.getInterfaceScriptSql(), IP, "0102", username, interfaceConfig.getInterfaceConfigDesc());
				return XMLData;		
			}
			Connection conn = getConn();
			PreparedStatement pst = null;
			ResultSet rs = null;
			ResultSetMetaData rsmd = null;
			
			String Fa_Flag = "0001";                         //????????????????????????Flag(??????????)
			String Fa_Mess = "????????????????????????.";          //????????????????????????Mess(????????????)
			String errorMess = "";                           //????????
			FAHEAD.addAttribute("Flag", Fa_Flag);            //??????????????Flag(??????????)
			FAHEAD.addAttribute("Mess", Fa_Mess);            //??????????????Mess(????????????)
			scriptSQL = interfaceScript.getInterfaceScriptSql();                             
			 
			//??????scriptSQL
			for(Param param : params) {
				if(scriptSQL.contains(param.getName())){
					scriptSQL=scriptSQL.trim().replace("\r", " ").replace("\n"," ").replaceAll(":\\["+param.getName()+"\\]", param.getValue());
				}
			}
			if("1".equals(interfaceScript.getAvailabilityStateId())){
				try {
					if(scriptSQL.trim().toUpperCase().startsWith("SELECT")){//??????????????
						pst  =  conn.prepareStatement(scriptSQL);	
						rs   =  pst.executeQuery();//??????????????????????????,????????????????SQL????,Statement??????Connection??????PreparedStatement????
						rsmd =  rs.getMetaData();
						List<List<Column>> COLUMNS_ALL = new  ArrayList<List<Column>>();
						while(rs.next()){                                                             //??????????
							List<Column> COLUMNS = new ArrayList<Column>();
							for(int i = 1; i <= rsmd.getColumnCount(); i++){                       //????????????????????????
								Column Column = new Column();
								Column.setName(rsmd.getColumnName(i));
								Column.setType(rsmd.getColumnTypeName(i));
								if("VARCHAR2".equalsIgnoreCase(rsmd.getColumnTypeName(i)) || "CHAR".equalsIgnoreCase(rsmd.getColumnTypeName(i)) ||  "VARCHAR".equalsIgnoreCase(rsmd.getColumnTypeName(i)) ){
									String dataString = StringUtil.encodeXML(rs.getString(i));	                    //????????????
									Column.setValue(dataString);
								}else if("NUMBER".equals(rsmd.getColumnTypeName(i))){
									String dataString = StringUtil.encodeXML(Double.toString(rs.getDouble(i)));	//????????????
									Column.setValue(dataString);
								}else if("DATE".equalsIgnoreCase(rsmd.getColumnTypeName(i))){
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //????????????
									Date time = rs.getTimestamp(i);                                                //????????????
									if(time != null) {
										String dataString = StringUtil.encodeXML(sdf.format(time));	            //????????????
										Column.setValue(dataString);
									} else {
										Column.setValue(StringUtil.encodeXML(""));
									}
								}else if("CLOB".equals(rsmd.getColumnTypeName(i))){
									java.sql.Clob clob = rs.getClob(i);  
									Reader  inStream = clob.getCharacterStream();  
									char[] c = new char[(int) clob.length()];  
									inStream.read(c);                                                              //data??????????????????????????????
									String dataString = StringUtil.encodeXML(new String(c));	                    //????????????
									Column.setValue(dataString);
								}else if("BLOB".equals(rsmd.getColumnTypeName(i))){
									InputStream bais = rs.getBinaryStream(i);                                      //??????????
									String str3 = StringUtil.BASE64Encoder(bais);                                  //????????????????????BASE64????
									String dataString = str3;	                                                    //????????????
									Column.setValue(dataString);
								}
								COLUMNS.add(Column);
							}
							COLUMNS_ALL.add(COLUMNS);
						}
						//????xml????
						document=generateXML(tableName,interfaceScript.getInterfaceScriptId(),COLUMNS_ALL,document,"0002", "????????????"+interfaceScript.getInterfaceScriptId()+"."+interfaceScript.getInterfaceScriptName()+"??????");
						XMLData=document.asXML().replaceAll("-&gt;", "->");
					}else {//????????????
						List<List<Column>> columns = new  ArrayList<List<Column>>();
						//??????????????
						if(INTERFACE_CONFIG_ID.equals("insertUnitBasicInformation")&&INTERFACE_SCRIPT_ID.equals("JB_000001")){
							//??SQL
							scriptSQL = SaveB01.save(params);
						}else if("insertBasicInformation".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA01.save(params,conn);
						}else if("insertWorkUnits".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA02.save(params,conn);
						}else if("insertProfessionalTechnical".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA06.save(params,conn);
						}else if("insertDegreeInformation".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA08.save(params);
						}else if("insertTrainingInformation".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA11.save(params, conn);
						}else if("insertAwardInformation".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA14.save(params);
						}else if("insertCheckInformation".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA15.save(params);
						}else if("insertEnterManagementInformation".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA29.save(params,conn);
						}else if("insertExitManagementInformation".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA30.save(params,conn);
						}else if("insetResignInformation".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA31.save(params, conn);
						}else if("insertFamilyInformation".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA36.save(params);
						}else if("insertCorrespondInformation".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA37.save(params, conn);
						}else if("insertPhotoInfo".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA57.save(params, session);
						}else if("delete".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = DeleteInfo.delete(params, conn);
						}else if("delete2".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = DeleteSingle.delete(params, conn);
						}else if("changeField".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = UpdateField.update(params);
						}else if("insertExamPersonInfo".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA60.save(params, conn);
						}else if("insertAssignedGraduatesInfo".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA61.save(params, conn);
						}else if("insertSelectorsInfo".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA62.save(params, conn);
						}else if("insertPublicSelectorsInfo".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA63.save(params, conn);
						}else if("insertExamInfo".equals(INTERFACE_CONFIG_ID)){
							//??SQL
							scriptSQL = SaveA64.save(params, conn);
						}else if("selectPhoto".equals(INTERFACE_CONFIG_ID)){
							scriptSQL = QueryPhoto.save(params);
							if(scriptSQL.contains("??????")){
								//????????????
								throw new Exception(scriptSQL);
							}else{
							String[] photoInfo = scriptSQL.split(",");
							String a0000 = photoInfo[0];
							String photodata = photoInfo[1];
							List<Column> c = new ArrayList<Column>();
							List<A57> c1 = HBUtil.getHBSession().createSQLQuery("select * from a57 where a0000 = '"+a0000+"'").addEntity(A57.class).list();
							if(c1.size() !=0 ){
								Column column = new Column();
								column.setName("????????");
								column.setType("VARCHAR2");
								column.setValue(c1.get(0).getPhotoname());
								c.add(column);
								Column column1 = new Column();
								column1.setName("????????");
								column1.setType("BLOB");
								column1.setValue(photodata);				
								c.add(column1);
								Column column2 = new Column();
								column2.setName("????????");
								column2.setType("VARCHAR2");
								column2.setValue(c1.get(0).getPhotopath());				
								c.add(column2);
							}
							
							columns.add(c);
							scriptSQL = "select 1 from dual";
							}
						}
						if(scriptSQL.contains("??????")){
							//????????????
							throw new Exception(scriptSQL);
						}
						if(scriptSQL.toLowerCase().contains("select 1 from dual")){
//							scriptSQL = StringUtil.decodeXML(scriptSQL);
//							pst  =  conn.prepareStatement(scriptSQL);	
//							pst.execute();     //??????????????????????????,????????????????SQL????,Statement??????Connection??????PreparedStatement????
						//	conn.commit();           //????JDBC????
							conn.setAutoCommit(true);// ????JDBC??????????????????
							//????xml????
							document=generateXML(tableName,interfaceScript.getInterfaceScriptId(),columns,document,"0002", "????????"+interfaceScript.getInterfaceScriptId()+"."+interfaceScript.getInterfaceScriptName()+"??????");
							XMLData=document.asXML().replaceAll("-&gt;", "->");
						}
						else {
							scriptSQL = StringUtil.decodeXML(scriptSQL);
							pst  =  conn.prepareStatement(scriptSQL);	
							pst.executeUpdate();     //??????????????????????????,????????????????SQL????,Statement??????Connection??????PreparedStatement????
							conn.commit();           //????JDBC????
							conn.setAutoCommit(true);// ????JDBC??????????????????
							//????xml????
							document=generateXML(tableName,interfaceScript.getInterfaceScriptId(),null,document,"0002", "????????????"+interfaceScript.getInterfaceScriptId()+"."+interfaceScript.getInterfaceScriptName()+"??????");
							XMLData=document.asXML().replaceAll("-&gt;", "->");
						}

					}
					
				} catch (Exception e) {
					errorMess += errorMess+"[0404]????????"+interfaceScript.getInterfaceScriptId()+"."+interfaceScript.getInterfaceScriptName()+"????????;"+e.getMessage();
					try {
						document=generateXML(tableName,interfaceScript.getInterfaceScriptId(),null,document,"0402",  "[????DSM_GENERAL]->[NO.003 ????????????????????????????]->[0402]????????????"+interfaceScript.getInterfaceScriptId()+"."+interfaceScript.getInterfaceScriptName()+"??????"+e.getMessage());
						XMLData=document.asXML().replaceAll("-&gt;", "->");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						if(conn!=null)
							conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}//????JDBC????
				}finally{
					if(rs!=null){
						try {
							rs.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
					if(pst!=null){
						try {
							pst.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
					if(conn!=null){
						try {
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				FAHEAD.addAttribute("Flag", "0202");
				FAHEAD.addAttribute("Mess", "0202.??????????????????.");
				DATA.setText("null");                                                                  //????????
				XMLData = document.asXML().replaceAll("-&gt;", "->");
			}
			
		}
		CreateInterfaceLog(INTERFACE_CONFIG_ID, interfaceConfig.getInterfaceConfigName(), INTERFACE_SCRIPT_ID, interfaceScript.getInterfaceScriptName(), scriptSQL, interfaceScript.getInterfaceScriptSql(), IP, "0102", username, interfaceConfig.getInterfaceConfigDesc());
		try {
			//axis????session??
			MessageContext mc = MessageContext.getCurrentContext();
			HttpServletRequest request = (HttpServletRequest)mc.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
			request.getSession().invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return XMLData;
	}
	
	 /**
	  * ????????????????????JSON????????????List<Param>????
	  * @param paramsStr  ????????????JSON??
	  * @return
	  * @throws Exception 
	  */
	 public List<Param> jsonToParam(String paramsStr) throws Exception{
		 List<Param> Params = new ArrayList<Param>();
		 try{
			if(!"".equals(paramsStr) && null!=paramsStr){
				JSONArray json=JSONArray.fromObject(paramsStr);
				for (int i = 0; i < json.size(); i++) {
					 JSONObject jsonObj = JSONObject.fromObject(json.getString(i));
					 Param param = new Param();
					 param.setName(jsonObj.getString("paramBM"));
					 param.setType(jsonObj.getString("paramType"));
					 param.setValue(jsonObj.getString("paramValue").replaceAll("\\[????\\]", "\r\n"));
					 param.setDesc(jsonObj.getString("paramMC"));
					 Params.add(param);
				}
			}
		 }catch(Exception e){
			 throw new Exception("????????????????????????????:"+e.getMessage());
		 }
		 
		 return Params;
	 }
	 
	 /**
	  * ????codeTypeId????codeValueName['1','String'],['3','Date'],['2','Double']
	  * @param codeTypeId
	  * @return
	  * @throws Exception
	  */
	public String getCodeValueName(String codeValueId) {
		String codeValueName = null;
		if("1".equals(codeValueId)) {
			codeValueName = "String";
		} else if("2".equals(codeValueId)) {
			codeValueName = "Double";
		} else if("3".equals(codeValueId)) {
			codeValueName = "Date";
		}
		return codeValueName;
	}
	
	/**
	 * ????????????????????????XML??????
	 * @param INTERFACE_SCRIPT_ID   ???????????????????? 
	 * @param COLUMNS_ALL           ????????????????????
	 * @param doc                   document????
	 * @param FLAG                  ????????????
	 * @param MESS                  ????????????
	 * @return                      document????
	 * @throws Exception
	 */
	public Document generateXML(String tableName,String INTERFACE_SCRIPT_ID,List<List<Column>> COLUMNS_ALL,Document doc,String FLAG,String MESS) throws Exception{
		Document document = null;
		if(doc == null){
			throw new Exception("document??????????????");
		}
		if(tableName == null || "".equals(tableName)){
			throw new Exception("????????????????");
		}
		try{
			Element root = doc.getRootElement();
			Element script = root.element("SCRIPTHEAD");
			Element jbscript = script.addElement(tableName);
			jbscript.addAttribute("Flag", FLAG);
			jbscript.addAttribute("Mess", MESS);
			
			Element rootElem=root.element("Data");//??????????Data
			if(COLUMNS_ALL!=null && !COLUMNS_ALL.isEmpty()){
				Element objDataElem = rootElem.addElement("Table");
				objDataElem.addAttribute("TabName",tableName);
				for(int j =0;j<COLUMNS_ALL.size();j++){
					List<Column> columns=COLUMNS_ALL.get(j);
					Element rowElem = objDataElem.addElement("rowData");
					rowElem.addAttribute("RowName", j+"");
					if(columns!=null && !columns.isEmpty()){
						for (int i = 0; i < columns.size(); i++) {
							Column column=columns.get(i);
							Element dataElem = rowElem.addElement("ColumnData");
							dataElem.addAttribute("ColName", column.getName());
							if("BLOB".equals(column.getType()) || "CLOB".equals(column.getType())){
								dataElem.addAttribute("ColValue",StringUtil.newLineEncoder(column.getValue()));
							}else{
								dataElem.addAttribute("ColValue",column.getValue());
							}
							dataElem.addAttribute("ColType", column.getType()); 
						}
					}
				}
				
			}
			document = doc;
			return document;
		}catch (Exception e) {
			 throw new Exception("??????????????XML??????:"+e.getMessage());
		}
	}
	/**
	 * ????????????????
	 */
	public void CreateInterfaceLog(String interfaceConfigId, String interfaceConfigName, String interfaceScriptId,String interfaceScriptName,
			String interfaceExecSql,String interfaceOriginalSql, String interfaceAccessIp, String executeStateId,String operateUsername,String interfaceComments){
		InterfaceLog log = new InterfaceLog();
		UUID uuid = UUID.randomUUID();
		String logId = uuid.toString();
		Date date = new Date();
		log.setInterfaceLogId(logId);
		log.setInterfaceRequesttime(date);
		log.setInterfaceConfigId(interfaceConfigId);
		log.setInterfaceConfigName(interfaceConfigName);
		log.setInterfaceScriptId(interfaceScriptId);
		log.setInterfaceScriptName(interfaceScriptName);
		log.setInterfaceExecSql(interfaceExecSql);
		log.setInterfaceOriginalSql(interfaceOriginalSql);
		log.setInterfaceAccessIp(interfaceAccessIp);
		log.setExecuteStateId(executeStateId);
		log.setOperateUsername(operateUsername);
		log.setInterfaceComments(interfaceComments);
		Transaction t = bs7.getSession().getTransaction();
		t.begin();
		bs7.getSession().save(log);
		t.commit();
		log.setInterfaceLogId(logId);
		
	}
	
	
	//??SQL
	public String saveDemo(List<Param> params) throws Exception{
		StringBuffer column = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into b01 (");
		//????????
		for(Param param : params) {
			//1??????????????????
			//if(param.getName().equals("B0101")&&param.getValue().equals("")){
			//	return "??????????????????????";
			//}else{
				column.append(param.getName()+",");
				value.append("'"+param.getValue()+"',");
			//}
			//2??????????
			//ChineseSpelling chineseSpelling = new ChineseSpelling();
			//sql.append("'"+chineseSpelling.getPYString(param.getValue())+"',");
			//3?????????? (code_value??????????)
			/*if(param.getName().equals("b0117")&&!"".equals(param.getValue())){
				String str = HBUtil.getHBSession().createQuery("select code_value from Code_Value where code_type='ZB09' and code_status='1' and code_name='"+param.getValue()+"'").toString();
				if (str.length() == 0) {
					return "????????????????????????????";
				}else{
					column.append(param.getName()+",");
					value.append("'"+str+"',");
				}
			}
			//4??5 ????????????????????????
			if(param.getName().equals("B0121")&&param.getValue().equals("")){
				return "??????????????????????????";
			}else{
				Long sortid = CreateSysOrgBS.insertSortId(param.getValue());
				column.append(param.getName()+",");
				value.append("'"+param.getValue()+"',");
				column.append("sortid,");
				value.append(sortid+",");
			}*/
		}
		column.deleteCharAt(column.length()-1);
		value.deleteCharAt(value.length()-1);
		sql.append(column).append(") values (").append(value);
		sql.append(")");
		return sql.toString();
	}
}
