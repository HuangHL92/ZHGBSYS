package com.insigma.siis.local.pagemodel.dataverify;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import org.apache.commons.lang.StringUtils;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.DataComparisonBS;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.entity.TabDatacontrastresult;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.publicServantManage.DeletePersonTimer;


public class DataComparisonPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
	    //业务类型 （bsType）： 1-机构校验（包含下级机构）；2-导入校验 
	    this.getPageElement("bsType").setValue("2");
	    
		this.setNextEventName("initX");
		return 0;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
	   
		String param = this.getPageElement("subWinIdBussessId").getValue();
		try {
			String param1[] = param.split(",");
			this.getPageElement("imprecordid").setValue(param1[0]);
			
			if(param1.length == 2){
				this.getPageElement("impclose").setValue(param1[1]);
			} else {
				this.getPageElement("impclose").setValue("0");
			}
			this.getPageElement("errorSign").setValue("");//将errorSign复位
			HBSession sess = HBUtil.getHBSession();
			Imprecord impRecord = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, param1[0]);
			//接收数据前的校验工作
			String imptemptable = impRecord.getImptemptable().toUpperCase();
			if(imptemptable!=null && !"".equals(imptemptable.toString())){
				//1、机构编码不能为空
				String sql1 = "";
				if(DBType.ORACLE == DBUtil.getDBType()){
					sql1 = "SELECT COUNT(1) FROM B01"+imptemptable+" B WHERE 1 = 1 AND B.B0114 IS NULL";
				}else{
					sql1 = "SELECT COUNT(1) FROM B01"+imptemptable+" B WHERE 1 = 1 AND (B.B0114 IS NULL OR B.B0114 = '')";
				}
				Object obj1 = sess.createSQLQuery(sql1).uniqueResult();
				if(obj1!=null){
					Integer one = Integer.parseInt(""+obj1);
					if(one > 0){
						this.getPageElement("errorSign").setValue("0");
						this.setNextEventName("errorGrid9.dogridquery");
						this.setNextEventName("doublea0184.dogridquery");
						this.setNextEventName("list1.dogridquery");
						this.getExecuteSG().addExecuteCode("ShowCellCover('failure','系统提示','发现数据包内含有空机构编码');");
						return EventRtnType.FAILD;
					}
				}
				//2、机构编码不能重复
				String sql2 = "select count(1) from b01"+imptemptable+" group by b0114 having count(b0114)>1";
				Object obj2 = sess.createSQLQuery(sql2).uniqueResult();
				if(obj2!=null){
					Integer one = Integer.parseInt(""+obj2);
					if(one > 0){
						this.getPageElement("errorSign").setValue("1");
						this.setNextEventName("errorGrid9.dogridquery");
						this.setNextEventName("doublea0184.dogridquery");
						this.setNextEventName("list1.dogridquery");
						this.getExecuteSG().addExecuteCode("ShowCellCover('failure','系统提示','发现数据包内含有重复机构编码');");
						return EventRtnType.FAILD;
					}
				}
				
				//3、机构内人员身份证不能为空
				/*String sql3 = "";
				if(DBType.ORACLE == DBUtil.getDBType()){
					sql3 = "SELECT COUNT(1) FROM A01"+imptemptable+" A WHERE 1 = 1 AND A.A0184 IS NULL";
				}else{
					sql3 = "SELECT COUNT(1) FROM A01"+imptemptable+" A WHERE 1 = 1 AND (A.A0184 IS NULL OR A.A0184 <> '')";
				}
				Object obj3 = sess.createSQLQuery(sql3).uniqueResult();
				if(obj3!=null){
					Integer one = Integer.parseInt(""+obj3);
					if(one > 0){
						this.getExecuteSG().addExecuteCode("ShowCellCover('failure','系统提示','无法进行数据对比（发现数据包内含有空人员身份证）');");
						return EventRtnType.FAILD;
					}
				}*/
				
				//4、机构内人员身份证不能重复  
				
			}else{
				this.setMainMessage("临时表不存在，无法进行对比数据！");
				return EventRtnType.FAILD;
			}
			
			this.setNextEventName("errorGrid9.dogridquery");
			this.setNextEventName("doublea0184.dogridquery");
			this.setNextEventName("btnSave.onclick");//list1.dogridquery
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 初始化组织机构树
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException, AppException, RadowException {

		String userid = this.getParameter("userid");
		StringBuffer jsonStr = new StringBuffer();
		String cueUsername = PrivilegeManager.getInstance().getCueLoginUser()
				.getLoginname();
		jsonStr.append("[");
		try {
			jsonStr.append(getJeson(userid, cueUsername));
		} catch (RadowException e) {
			e.printStackTrace();
		}
		jsonStr.append("]");

		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}

	private String getJeson(String userid, String cueUsername)
			throws RadowException, AppException {
		String cb0 = "1";
		String cb1 = "1";
		try {
			cb0 = this.getParameter("hcb0");
			cb1 = this.getParameter("hcb1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String imprecordid = this.request.getParameter("initParams");
		StopWatch w = new StopWatch();
		w.start();
		int type = 1;	//查询类型， 1 全部 ,2 查询机构 ，3 整体信息
		if(cb1.equals("0")){
			if(cb0.equals("1")){
				type = 2;
			} else {
				type = 3;
			}
		}
		System.out.println(cb0 +"---"+cb1 +"---"+type);
		String node1 = this.getParameter("node");
		String returnValue = "";
		// 读取层次数据结果集列表
		List dataList = DataComparisonBS.getVirtualResult( node1, imprecordid, type);
//		List dataList = VirtualDataGenerator.getVirtualResult(cueUsername,
//				userid, node1);
		if (dataList.size() > 0) {
			returnValue = makeJSON(dataList);
		}
		w.stop();
		CommonQueryBS.systemOut("机构数组装结束:" + w.elapsedTime());
		return returnValue;
	}

	public String makeJSON(List dataList) {
		HashMap nodemap = new HashMap();
		String id = "";
		String text = "";
		String count1 = "";
		String bm = "";
		String comcount = "";
		String comcount2 = "";
		String b0194 = "";
		StringBuffer result = new StringBuffer();

		Iterator iter = dataList.iterator();
		while (iter.hasNext()) {
			nodemap = (HashMap) iter.next();
			count1 = (String) nodemap.get("count1");
			id = (String) nodemap.get("b0111");
			text = (String) nodemap.get("b0101");
			comcount = (String) nodemap.get("comcount");
			comcount2 = (String) nodemap.get("comcount2");
			bm = (String) nodemap.get("b0114");
			b0194 = (String)nodemap.get("b0194");
			String icon ="";
//			if(b0194.equals("1")){
//				icon="./main/images/icons/companyOrgImg2.png";
//			}else if(b0194.equals("2")){
//				icon="./main/images/icons/insideOrgImg1.png";
//			}else if(b0194.equals("3")){
//				icon="./main/images/icons/groupOrgImg1.png";
//			}
			if(b0194.equals("1")){
				icon="./main/images/icons/companyOrgImg2.png";
			}else if(b0194.equals("2")){
				icon="./main/images/tree/leaf.gif";
			}else if(b0194.equals("3")){
				icon="./main/images/tree/folder.gif";
			}
			if ("机构树".equals(text)) {
				continue;
			}
			result.append("{"
					+ "b0101 : '" + text
					+ "',id:'" + id + "'"
					+ ",b0101m:'" + text + "'");
			
			result.append(",icon:'"+icon+"'");
			result.append(", leaf : " + hasChildren(count1));
			result.append(",comcount:'" + comcount + "'");
			result.append(",comcount2:'" + (comcount2==null?"xxx":comcount2) + "'");
			result.append(",b0114:'" + bm + "'");
			result.append(",uiProvider:'col'}");
			result.append(",");
		}
		result.substring(0, result.length() - 1);
		return result.toString();
	}

	private String hasChildren(String id) {
		if (id != null && !"0".equals(id) && !"".equals(id)) {
			return "false";
		} else {
			return "true";
		}
	}
	
	
	@PageEvent("errorGrid9.dogridquery")
	@NoRequiredValidate
	public int doErrorGrid9Query(int start,int limit) throws RadowException{
		StopWatch w = new StopWatch();
    	w.start();
		String b0111OrimpID = this.getPageElement("imprecordid").getValue();
		if(StringUtil.isEmpty(b0111OrimpID) ){
			throw new RadowException("获取参数为空！");
		}
		
		Imprecord impRecord = null;
		String imptemptable = "_temp";
		impRecord = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, b0111OrimpID);
		imptemptable = impRecord.getImptemptable();
		
		StringBuffer sqlbf = new StringBuffer();
		sqlbf.append("SELECT A.A0101,B.A0184, B.A0101 A0101B,A.A0107 ,B.A0107 A0107B, A.A0144,B.A0144 A0144B, A.A0134,B.A0134 A0134B")
		.append(" FROM A01"+impRecord.getImptemptable()+" A,")
		.append(" A01 B ")
		.append(" WHERE A.A0184 = B.A0184 and (A.A0101<>B.A0101 or A.A0107<>B.A0107 or A.A0144<>B.A0144 or A.A0134<>B.A0134)");
		
		sqlbf.append(" order by A.A0101");
		CommonQueryBS.systemOut("--->三龄>>"+sqlbf.toString());
		this.pageQuery(sqlbf.toString(),"SQL", start, limit);
		w.stop();
		CommonQueryBS.systemOut("执行时间三龄：---"+w.elapsedTime());
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("doublea0184.dogridquery")
	@NoRequiredValidate
	public int doubleA0184Query(int start,int limit) throws RadowException{
		StopWatch w = new StopWatch();
    	w.start();
		String b0111OrimpID = this.getPageElement("imprecordid").getValue();
		if(StringUtil.isEmpty(b0111OrimpID) ){
			throw new RadowException("获取参数为空！");
		}
		
		Imprecord impRecord = null;
		impRecord = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, b0111OrimpID);
		String imptemptable = impRecord.getImptemptable();
		
		String sqlbf = "";
		if(DBType.MYSQL == DBUtil.getDBType()){
			sqlbf = "select t.a0101 a0101,t.a0184 a0184 from A01 a,a01"+imptemptable+" t WHERE t.a0101 = a.A0101 and t.a0184 = a.A0184 and t.A0184 is not null and t.A0184 <> ''";
		}else{
			sqlbf = "select t.a0101 a0101,t.a0184 a0184 from A01 a,a01"+imptemptable+" t WHERE t.a0101 = a.A0101 and t.a0184 = a.A0184 and t.A0184 is not null ";
		}
		/*StringBuffer sqlbf = new StringBuffer();
		sqlbf.append("SELECT A.A0101,B.A0184, B.A0101 A0101B,A.A0107 ,B.A0107 A0107B, A.A0144,B.A0144 A0144B, A.A0134,B.A0134 A0134B")
		.append(" FROM A01"+imptemptable+" A,")
		.append(" A01 B ")
		.append(" WHERE A.A0184 = B.A0184 and (A.A0101<>B.A0101 or A.A0107<>B.A0107 or A.A0144<>B.A0144 or A.A0134<>B.A0134)");*/
		CommonQueryBS.systemOut("--->身份证重复人员>>"+sqlbf);
		this.pageQuery(sqlbf,"SQL", start, limit);
		w.stop();
		CommonQueryBS.systemOut("执行时间身份证重复人员：---"+w.elapsedTime());
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("reloadJG")
	@NoRequiredValidate
	public int reloadJG(int start,int limit) throws RadowException{
		
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
     * 数据比对结果
     * @throws RadowException
     * @throws AppException 
     */
    @PageEvent("dataContrastResult")
    @NoRequiredValidate
    public int dataContrastResult(String imprecordid) throws RadowException{
        System.out.println("imprecordid=============================="+imprecordid);
        try {
            
            /*
             * pywu 20170514 判断数据是否已比对过
             * 
             * */
            if(imprecordid != null && !"".equals(imprecordid)){
                String sql = "select count(1) a from TAB_DataContrastResult t where t.imp_record_id ='" + imprecordid + "' ";
                List<HashMap<String, Object>> list;
                list = new CommQuery().getListBySQL(sql);
                if(list != null && list.size() > 0){
                    int count = Integer.parseInt(list.get(0).get("a")+"");
                    System.out.println("count=============================="+count);
                    if(count != 0){
                        this.getExecuteSG().addExecuteCode("openDataContrastResult();");
                    }else{
                        this.setMainMessage("暂无比对结果，请先比对数据！");
                    }
                }
            } 
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    
    /**
     * pywu  2017-06-12
     * 数据比对
     * 
     * @return
     * @throws RadowException
     * @throws AppException
     */
    @PageEvent("btnSave.onclick")
    public int saveBefore() throws RadowException{
        DeletePersonTimer.exec();
        try {
            String imprecordid = this.getPageElement("imprecordid").getValue();
            if(imprecordid != null && !"".equals(imprecordid)){
                String sql = "select count(1) a from TAB_DataContrastResult t where t.imp_record_id ='" + imprecordid + "' ";
                List<HashMap<String, Object>> list;
                list = new CommQuery().getListBySQL(sql);
                if(list != null && list.size() > 0){
                    int count = Integer.parseInt(list.get(0).get("a")+"");
                    if(count >= 1){
                        //this.setMainMessage("数据已比对，无需再次比对！");
                        //System.out.println("============数据已比对，无需再次比对！===================已比对个数："+count);
                    	//刷新grid
                        this.getExecuteSG().addExecuteCode("radow.doEvent('list1.dogridquery');");
                    }else{
                        dataPacket(imprecordid);
                        this.setMainMessage("数据比对完成！");
                    }
                }
            }else{
                this.setMessageType(EventMessageType.ERROR);
                this.setMainMessage("查询参数有误，请联系系统管理员！");
            }
        } catch (AppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return EventRtnType.NORMAL_SUCCESS;
    }
    

    /*
     * 根据当前导入数据记录表imp_record中的表名imp_temp_table，查询当前数据包中的所有机构信息
     * */
    public int dataPacket(String imprecordid){
        try {
            System.out.println("导出表记录id：=================>>>>>>>>>>>>>>>>>"+imprecordid);
            HBSession sess = HBUtil.getHBSession();
            Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
            if(imp == null){
                this.setMainMessage("数据加载失败！");
                return EventRtnType.FAILD; 
            }
            Connection conn = null;
            conn = sess.connection();
            getCompChild(conn,imprecordid,imp.getImptemptable(),imp.getImpdeptid(),imp.getImpdeptid(),imp.getEmpdeptname(),imp.getB0114());
            
            //刷新grid
            this.getExecuteSG().addExecuteCode("radow.doEvent('list1.dogridquery');");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return 1;
    }
    
    /*
     * 外部数据包 B01数据     需要和内部数据做比对
     * */
   private Map<String, List<String>> b01WB(String impTempTable) throws ServletException{
      /* b0111_parent_n 
       上级机构主键（内部） 
       b0111_parent_w 
                 上级机构主键（外部） 
       b0101_n 
                 机构名称（内部） 
       b0101_w 
                 机构名称（外部） 
       b0114_n 
       法人单位编码(机构分组编码)（内部） 
       b0114_w 
       法人单位编码(机构分组编码)（外部） */

       //需要比对的字段
       List<String> b0111_parent_ws = new ArrayList<String>();//上级机构主键（外部） 
       List<String> b0101_ws = new ArrayList<String>();// 机构名称（内部）
       List<String> b0114_ws = new ArrayList<String>();//法人单位编码(机构分组编码)（外部）
       
       try {
           String sql = "select * from " + impTempTable;
           
           List<HashMap<String, Object>> listB01Temp;
           listB01Temp = new CommQuery().getListBySQL(sql);
           
           if(listB01Temp != null && listB01Temp.size() > 0){
               for(Map<String, Object> map : listB01Temp){
                   String b0111_parent_w = (String) map.get("b0111");
                   String b0101_w = (String) map.get("b0101");
                   String b0114_w = (String) map.get("b0114");
                   
                   b0111_parent_ws.add(b0111_parent_w);
                   b0101_ws.add(b0101_w);
                   b0114_ws.add(b0114_w);
               }
           }
       } catch (AppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
       }
       
           
           Map<String, List<String>> map = new HashMap<String, List<String>>();
           map.put("b0111_parent_ws", b0111_parent_ws);
           map.put("b0101_ws", b0101_ws);
           map.put("b0114_ws", b0114_ws);
           
        return map;
        
    }
   
       //通过机构主键，查找内部机构信息
       private List<HashMap<String,Object>> b01NB(String b0111) throws AppException{
           String sql = "select * from b01 t ";
           List<HashMap<String, Object>> listB01 = new CommQuery().getListBySQL(sql);
           return listB01;
       }
       
       //数据比对
       private void dataContrast(String b0111) throws AppException{
           Map<String, List<String>> b01WB = new HashMap<String, List<String>>();
           
           try {
                b01WB = b01WB("");
            } catch (ServletException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           //需要比对的字段
           List<String> b0111_parent_ws = new ArrayList<String>();//上级机构主键（外部） 
           List<String> b0101_ws = new ArrayList<String>();// 机构名称（内部）
           List<String> b0114_ws = new ArrayList<String>();//法人单位编码(机构分组编码)（外部）
           
           b0111_parent_ws = b01WB.get("b0111_parent_ws");
           b0101_ws = b01WB.get("b0101_ws");
           b0114_ws = b01WB.get("b0114_ws");
           
           
           int zjxf_number = 0;
           List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
           
           System.out.println("外层循环次数："+zjxf_number);
           
           
           
       }
       

       private boolean getCompChild(Connection conn,String impRecordId,String imptemptable,String b0111NB, String b0111WB,String deptname,String b0114) {
           Statement stmt = null;
           ResultSet rs = null;
           Map<String,String> map1 = new HashMap<String,String>();//内部
           Map<String,String> map2 = new HashMap<String,String>();//外部
           Map<String,String> mapn = new HashMap<String,String>();//存储内部信息
           Map<String,String> mapw = new HashMap<String,String>();//存储外部信息
           
           int i = 0;
           int k = 0;
           try {
                   /*
                    * 机构全称：b0101
                    * 机构主键：b0111
                    * 机构编码：b0114
                    * 
                    * 机构编码和机构名称不允许重复
                    * 
                    * */
                   stmt = conn.createStatement();
                   rs = stmt.executeQuery("select b0101,b0114,b0111 from b01 where b0121='"+b0111NB+"'");//内部
                   while(rs.next()){
                	   String str = rs.getString(2)!=null&&!rs.getString(2).trim().equals("")?rs.getString(2):"";
                	   if(str==null || "".equals(str)){//机构编码为空 的内设机构、机构分组特殊处理
                		   str = rs.getString(1);
                	   }
                       map1.put(rs.getString(1)+","+ str, rs.getString(3));
                       mapn.put(str , rs.getString(1));//key=>b0114    value=>b0101
                       i++;
                   }
                   rs = stmt.executeQuery("select b0101,b0114,b0111 from b01"+imptemptable+" where b0121='"+b0111WB+"'");//外部
                   while(rs.next()){
                	   String str = rs.getString(2)!=null&&!rs.getString(2).trim().equals("")?rs.getString(2):"";
                	   if(str==null || "".equals(str)){//机构编码为空 的内设机构、机构分组特殊处理
                		   str = rs.getString(1);
                	   }
                       map2.put(rs.getString(1)+","+str, rs.getString(3));
                       mapw.put(str , rs.getString(1));
                       k++;
                   }
                   rs.close();
                   stmt.close();
                   
                   String b0111n = "";//机构主键（内部）
                   String b0111w = "";//机构主键（外部）
                   String b0111parentn = b0111NB;//上级机构主键（内部）
                   String b0111parentw = b0111WB;//上级机构主键（外部）
                   String b0101n = "";//机构名称（内部）
                   String b0101w = "";//机构名称（外部）
                   String b0114n = "";//法人单位编码(机构分组编码)（内部）
                   String b0114w = "";//法人单位编码(机构分组编码)（外部）
                   String impRecordId2 = impRecordId;//[0102]导入记录信息表imp_record主键
                   String opptimetype = "";//比对结果  对比结果类型   0：已删除 1：一致 2：新增 3：已修改
                   String comments = "";//比对结果描述
                   Date opptime = new Date();
                   SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   opptime = ft.parse(ft.format(opptime));
                   
                   HBSession sess = HBUtil.getHBSession();

                   Iterator<Map.Entry<String,String>> entries = map2.entrySet().iterator();
                   while(entries.hasNext()){//外部
                	   
                	   b0111n = "";//机构主键（内部）
                       b0111w = "";//机构主键（外部）
                       b0111parentn = b0111NB;//上级机构主键（内部）
                       b0111parentw = b0111WB;//上级机构主键（外部）
                       b0101n = "";//机构名称（内部）
                       b0101w = "";//机构名称（外部）
                       b0114n = "";//法人单位编码(机构分组编码)（内部）
                       b0114w = "";//法人单位编码(机构分组编码)（外部）
                       impRecordId2 = impRecordId;//[0102]导入记录信息表imp_record主键
                       opptimetype = "";//比对结果  对比结果类型   0：已删除 1：一致 2：新增 3：已修改
                       comments = "";//比对结果描述
                       ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                       opptime = ft.parse(ft.format(new Date()));
                	   
                       Map.Entry<String,String> entry = entries.next();
                       String key2 = entry.getKey();
                       String value2 = entry.getValue();
                       String value = map1.get(key2);//内部机构去外部相同节点下的机构比较
                       //外部机构根据机构名称+机构编码去内部相同节点下查找，
                       //若未找到，则机构编码或名称被修改，编码修改等同于该机构为新增，名称被修改，该机构视为被修改，或删除
                       
                       //外部
                       b0101w = key2.split(",", 2)[0];//全称
                       b0114w = key2.split(",", 2)[1];//编码
                       b0111w = value2;//主键
                       
                       if(value == null){
                           /*
                            * 若 同一节点下，外部有，内部没有，则内部机构删除或编码修改（视为新增）或名称被修改
                            * 具体判断：
                            * 分别在同级下用编码和名称分别判断
                            * 机构编码全库唯一
                            * 
                            * */
                           //外部
                    	   b0101n = mapn.get(b0114w);//全称
                           if(b0101n == null || "".equals(b0101n)){//机构编码不同，视为新增
                               opptimetype = "2";
                               comments = "该机构为新增，上级机构全称为："+deptname+"  编码为："+b0114;
                           }else{//机构编码有相同，比较名称
                        	   b0114n = b0114w;
                        	   b0111n = value;//主键
                        	   value = value2;
                        	   
                        	   opptimetype = "3";
                               comments = "该机构名称被修改";
                           }
                       } else { //若 同一节点下，外部有，内部有，则同节点下内部机构编码和全称完全一致
                           //外部
                           b0101n = key2.split(",", 2)[0];//全称
                           b0114n = key2.split(",", 2)[1];//编码
                           b0111n = value;//主键
                           
                           opptimetype = "1";
                           comments = "同节点下，机构编码和全称完全一致";
                       }
                       
                       //保存机构比对结果
                       TabDatacontrastresult dtr = new TabDatacontrastresult();
                       dtr.setB0111n(b0111n);//机构主键（内部）
                       dtr.setB0111w(b0111w);//机构主键（外部）
                       dtr.setB0111parentn(b0111parentn);//上级机构主键（内部）
                       dtr.setB0111parentw(b0111parentw);//上级机构主键（外部）
                       dtr.setB0101n(b0101n!=null?b0101n:"");//机构名称（内部）
                       dtr.setb0101w(b0101w!=null?b0101w:"");//机构名称（外部）
                       //dtr.setb0114n(b0114n!=null?b0114n:"");//法人单位编码(机构分组编码)（内部）
                       //dtr.setb0114w(b0114w!=null?b0114w:"");//法人单位编码(机构分组编码)（外部）
                       dtr.setb0114n((b0114n!=null&&b0114n!=b0101n)?b0114n:"");//法人单位编码(机构分组编码)（内部）
                       dtr.setb0114w((b0114w!=null&&b0114w!=b0101w)?b0114w:"");//法人单位编码(机构分组编码)（外部）
                       
                       dtr.setImprecordid(impRecordId2);//[0102]导入记录信息表imp_record主键
                       dtr.setComments(comments!=null?comments:"");//结果描述
                       dtr.setOpptimetype(opptimetype);//对比结果类型 0：已删除 1：一致 2：新增 3：已修改
                       dtr.setOpptime(opptime); //--操作时间
                       
                       sess.save(dtr);
                       sess.flush();
                       if(opptimetype.equals("2")){
                    	   boolean boo = getCompChild(conn,impRecordId2,imptemptable,value2,deptname,b0114,b0101w,b0114w);
                       } else {
                    	   boolean boo = getCompChild(conn,impRecordId2,imptemptable,value,value2,b0101n,b0114n);
                       }
                       
//                       if(!boo){
//                           return boo;
//                       }
                       
                   }
                   // 删除
                   Iterator<Map.Entry<String,String>> entries2 = map1.entrySet().iterator();
                   while(entries2.hasNext()){//内部
                       Map.Entry<String,String> entry = entries2.next();
                       String key2 = entry.getKey();
                       String value2 = entry.getValue();
                       String value = map2.get(key2);//内部机构去外部相同节点下的机构比较
                       //外部机构根据机构名称+机构编码去内部相同节点下查找，
                       //若未找到，则机构编码或名称被修改，编码修改等同于该机构为新增，名称被修改，该机构视为被修改，或删除
                       
                       if(value == null){
                           /*
                            * 若 同一节点下，外部有，内部没有，则内部机构删除或编码修改（视为新增）或名称被修改
                            * 具体判断：
                            * 分别在同级下用编码和名称分别判断
                            * 机构编码全库唯一
                            * 
                            * */
                    	   b0101n = key2.split(",", 2)[0];//全称
                           b0114n = key2.split(",", 2)[1];//编码
                    	   
                           b0101w = mapw.get(b0114n);
                           if(b0101w==null || "".equals(b0101w)){
                        	   //外部
                        	   String sql = "insert into TAB_DATACONTRASTRESULT select " + (DBUtil.getDBType().equals(DBType.MYSQL)?"uuid()":"sys_guid()")+",b0111,null,b0121"
                        			   + ",null,b0101,null,b0114,null,'"+impRecordId2+"','该机构在上报数据中从已有机构中删除。(删除起始节点，机构名称："+b0101n+"，机构编码:"+b0114n+")','0',"
                        			   + (DBUtil.getDBType().equals(DBType.MYSQL)?"now()":"sysdate")+" from b01 t where t.b0111 like '"+value2+"%'";
                        	   sess.createSQLQuery(sql).executeUpdate();
                        	   sess.flush();
                           }
                       }
                       
                   }
                   
                   map1.remove(map1);
                   map2.remove(map2);
                   return true;
           } catch (Exception e) {
               e.printStackTrace();
           }
           return false;
       }
       
       
       private boolean getCompChild(Connection conn,String impRecordId,String imptemptable, String b0111WB,String deptname,String b0114,String b0101wp,String b0114wp) {
           Statement stmt = null;
           ResultSet rs = null;
           Map<String,String> map2 = new HashMap<String,String>();//外部
           
           try {
                   /*
                    * 机构全称：b0101
                    * 机构主键：b0111
                    * 机构编码：b0114
                    * 
                    * 机构编码和机构名称不允许重复
                    * 
                    * */
        	   stmt = conn.createStatement();
                   rs = stmt.executeQuery("select b0101,b0114,b0111 from b01"+imptemptable+" where b0121='"+b0111WB+"'");//外部
                   while(rs.next()){
                	   String str = rs.getString(2)!=null&&!rs.getString(2).trim().equals("")?rs.getString(2):"";
                	   if(str==null || "".equals(str)){//机构编码为空 的内设机构、机构分组特殊处理
                		   str = rs.getString(1);
                	   }
                       map2.put(rs.getString(1)+","+str, rs.getString(3));
                       //mapw.put(rs.getString(3), rs.getString(3));
                   }
                   /*rs.close();
                   stmt.close();*/
                   
                   String b0111n = "";//机构主键（内部）
                   String b0111w = "";//机构主键（外部）
                   String b0111parentn = "";//上级机构主键（内部）
                   String b0111parentw = b0111WB;//上级机构主键（外部）
                   String b0101n = "";//机构名称（内部）
                   String b0101w = "";//机构名称（外部）
                   String b0114n = "";//法人单位编码(机构分组编码)（内部）
                   String b0114w = "";//法人单位编码(机构分组编码)（外部）
                   String impRecordId2 = impRecordId;//[0102]导入记录信息表imp_record主键
                   String opptimetype = "";//比对结果  对比结果类型   0：已删除 1：一致 2：新增 3：已修改
                   String comments = "";//比对结果描述
                   Date opptime = new Date();
                   SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   opptime = ft.parse(ft.format(opptime));
                   
                   HBSession sess = HBUtil.getHBSession();

                   Iterator<Map.Entry<String,String>> entries = map2.entrySet().iterator();
                   while(entries.hasNext()){//外部
                       Map.Entry<String,String> entry = entries.next();
                       String key2 = entry.getKey();
                       String value2 = entry.getValue();
                       //外部
                       b0101w = key2.split(",", 2)[0];//全称
                       b0114w = key2.split(",", 2)[1];//编码
                       b0111w = value2;//主键
                       
                       opptimetype = "2";
                       comments = "该机构为新增，上级机构全称为："+b0101wp+"  编码为："+b0114wp+",内部隶属上级机构全称为："+deptname+"  编码为："+b0114;
                       
                       //保存机构比对结果
                       TabDatacontrastresult dtr = new TabDatacontrastresult();
                       dtr.setB0111n(b0111n);//机构主键（内部）
                       dtr.setB0111w(b0111w);//机构主键（外部）
                       dtr.setB0111parentn(b0111parentn);//上级机构主键（内部）
                       dtr.setB0111parentw(b0111parentw);//上级机构主键（外部）
                       dtr.setB0101n(b0101n!=null?b0101n:"");//机构名称（内部）
                       dtr.setb0101w(b0101w!=null?b0101w:"");//机构名称（外部）
                       //dtr.setb0114n(b0114n!=null?b0114n:"");//法人单位编码(机构分组编码)（内部）
                       //dtr.setb0114w(b0114w!=null?b0114w:"");//法人单位编码(机构分组编码)（外部）
                       dtr.setb0114n((b0114n!=null&&b0114n!=b0101n)?b0114n:"");//法人单位编码(机构分组编码)（内部）
                       dtr.setb0114w((b0114w!=null&&b0114w!=b0101w)?b0114w:"");//法人单位编码(机构分组编码)（外部）
                       
                       dtr.setImprecordid(impRecordId2);//[0102]导入记录信息表imp_record主键
                       dtr.setComments(comments!=null?comments:"");//结果描述
                       dtr.setOpptimetype(opptimetype);//对比结果类型 0：已删除 1：一致 2：新增 3：已修改
                       dtr.setOpptime(opptime); //--操作时间
                       
                       sess.save(dtr);
                       sess.flush();
                       boolean boo = getCompChild(conn,impRecordId2,imptemptable,value2,deptname,b0114, b0101w, b0114w);
                   }
                   
                   map2.remove(map2);
                   return true;
           } catch (Exception e) {
               e.printStackTrace();
           }
           return false;
       }

       @PageEvent("impconfirmBtn.onclick")
       @NoRequiredValidate
       public int batchPrintBefore() throws RadowException, AppException, SQLException{
           String imprecordid = this.getPageElement("imprecordid").getValue();
           HBSession sess = HBUtil.getHBSession();
           Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
           String b0114 = imp.getB0114();
           Connection conn = sess.connection();
           Statement stmt = conn.createStatement();
           ResultSet rs =  null;
           String sql5 = "select count(b0111) from b01 where b0111 <> '-1'";
           rs = stmt.executeQuery(sql5);
           int size = 0;
           while(rs.next()){
               size = rs.getInt(1);
           }
//         String sql3 = "select b0114 from b01 where B0114 = '"+ b0114 +"'";
//         rs = stmt.executeQuery(sql3);
           if((size== 0 && imp.getImpdeptid().equals("001.001")) || size > 0 || imp.getImptype().equals("4")){
               String impdeptid = imp.getImpdeptid();
               String imptype = imp.getImptype();
               String ftype = imp.getFiletype();
//             if(imp.getIsvirety() == null || imp.getIsvirety().equals("") ||imp.getIsvirety().equals("0")){
//                 this.setMainMessage("请先进行校验，再导入数据");
//                 return EventRtnType.NORMAL_SUCCESS;
//             }
               if(imp.getImpstutas() != null && !imp.getImpstutas().equals("1")){
                   if(imp.getImpstutas().equals("2")){
                       this.setMainMessage("数据已导入，不能重复导入。");
                   } else if(imp.getImpstutas().equals("4")){
                       this.setMainMessage("数据接收中，不能重复接收。");
                   } else {
                       this.setMainMessage("数据已打回，不能导入。");
                   }
                   return EventRtnType.NORMAL_SUCCESS;
               }
               //this.getExecuteSG().addExecuteCode("var grid = odin.ext.getCmp('errorGrid9');document.getElementById('grid9_totalcount').value=grid.getStore().getTotalCount();");
               if(imptype.equals("4")){
                   this.getExecuteSG().addExecuteCode("radow.doEvent('impconfirmBtn2','1')");
               } else {
                   this.getExecuteSG().addExecuteCode("$h1.confirm4btn('系统提示','是否更新导入的机构信息？',200,function(id){" +
                           "if(id=='yes'){" +
                           "           radow.doEvent('impconfirmBtn2','1');" +
                               "}else if(id=='no'){" +
                               "           radow.doEvent('impconfirmBtn2','2');" +
                               "}else if(id=='cancel'){" +
                               "   " +
                               "}" +
                           "});");
               }
           }else{
               this.setMainMessage("没有对应机构,请新建该机构!");
           }
           return EventRtnType.NORMAL_SUCCESS;
       }
       
       /**
        * 全部退回
        * @return
        * @throws RadowException
        */
       @PageEvent("rejectBtn.onclick")
       @NoRequiredValidate
       public int rejectBtnOnclick()throws RadowException{
           String b0111 = this.getPageElement("imprecordid").getValue();
           String bsType = this.getPageElement("bsType").getValue();
           HBSession sess = HBUtil.getHBSession();
           try {
               sess.beginTransaction();
               String imprecordid = this.getPageElement("imprecordid").getValue();
               Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
               String imptemptable = imp.getImptemptable();
               String ftype = imp.getFiletype();
               if(imp.getImpstutas().equals("2")){
                   this.setMainMessage("数据已导入，不能打回。");
                   return EventRtnType.NORMAL_SUCCESS;
               } else if(imp.getImpstutas().equals("4")){
                   this.setMainMessage("数据接收中，不能打回。");
                   return EventRtnType.NORMAL_SUCCESS;
               } else if(imp.getImpstutas().equals("3")){
                   this.setMainMessage("数据已打回，不能重复打回。");
                   return EventRtnType.NORMAL_SUCCESS;
               }
               String str = "";
//             String str = this.FkImpData(imprecordid, "");
               //打回后清除错误信息Verify_Error_List
               sess.createSQLQuery(" delete from Verify_Error_List where vel004 = '"+b0111+"' and vel005='"+bsType+"'").executeUpdate();
               String tables1[] = {"A01", "A02","A06","A08", "A11", "A14", "A15", "A29","A30", 
                       "A31","A36","A37","A41", "A53","A57","A60","A61","A62","A63","A64", "B01", "B_E", "I_E","A05", "A68", "A69", "A71", "A99Z1"};
               for (int i = 0; i < tables1.length; i++) {
            	   Object obj = null;
            	   if(DBUtil.getDBType().equals(DBType.ORACLE)){
            		   obj = sess.createSQLQuery("select count(*) from user_tables where table_name = '"+tables1[i]+""+imptemptable+"'").uniqueResult();
            	   }else{
            		   obj = sess.createSQLQuery("select count(*) from INFORMATION_SCHEMA.TABLES where TABLE_NAME = '"+tables1[i]+""+imptemptable+"' and TABLE_SCHEMA = 'ZWHZYQ'").uniqueResult();
            	   }
            	   Integer num = Integer.parseInt("" + obj);
        		   if(num != 0){
        			   sess.createSQLQuery(" drop table " + tables1[i] + ""+imptemptable+"").executeUpdate();
        		   }
               }
               imp.setImpstutas("3");
               sess.update(imp);
               sess.flush();
               sess.getTransaction().commit();
               PhotosUtil.removDirImpCmd(imprecordid,"");
               if(str != null && !str.equals(""))
                   this.getExecuteSG().addExecuteCode("var w=window.open('ProblemDownServlet?method=downFile&prid="+ URLEncoder.encode(URLEncoder.encode(str,"UTF-8"),"UTF-8")+"');  setTimeout(cc,600); function cc(){w.close();}");
               if (ftype.equalsIgnoreCase("hzb") || ftype.equalsIgnoreCase("zzb")) {
                   new LogUtil().createLog("462", "IMP_RECORD", "", "", "打回清除", new ArrayList());
               } else if (ftype.equalsIgnoreCase("zzb3")){
                   new LogUtil().createLog("472", "IMP_RECORD", "", "", "打回清除", new ArrayList());
               }
               this.getExecuteSG().addExecuteCode("realParent.odin.ext.getCmp('MGrid').store.reload();");
               this.setMainMessage("已打回！");
           } catch (UnsupportedEncodingException e) {
               sess.getTransaction().rollback();
               e.printStackTrace();
           } catch (AppException e) {
               sess.getTransaction().rollback();
               e.printStackTrace();
           } catch (SQLException e) {
               e.printStackTrace();
           }
           return EventRtnType.NORMAL_SUCCESS;
       }
       
       
       /**************************************************翔飞代码**********************************************/
       /**
        * 导入正确数据前提示信息
        * @return
        * @throws RadowException
        */
       @PageEvent("impconfirmBtn2")
       @NoRequiredValidate
       public int impmodelOnclickBefore(String str) throws RadowException{
           //导入校验(业务类型为2) 
           String bsType = this.getPageElement("bsType").getValue();
           String imprecordid = this.getPageElement("imprecordid").getValue();
           HBSession sess = HBUtil.getHBSession();
           try {
        	   Imprecord  impRecord = (Imprecord) sess.get(Imprecord.class, imprecordid);
        	   StringBuffer sqlbf = new StringBuffer();
        	   sqlbf.append("SELECT count(1) ")
	          		.append(" FROM A01"+impRecord.getImptemptable()+" A,")
	          		.append(" (SELECT A01.A0000, A01.A0101, A01.A0184")
	          		.append(" FROM A01, A02")
	          		.append(" WHERE A01.A0000 = A02.A0000")
	          		.append(" AND A02.A0201B LIKE '"+impRecord.getImpdeptid()+"%'")
	          		.append(" ) B")
	          		.append(" WHERE A.A0184 = B.A0184");
        	   Object obj = sess.createSQLQuery(sqlbf.toString()).uniqueResult();
        	   StringBuffer sqlbf2 = new StringBuffer();
        	   sqlbf2.append("select count(1) from b01 t, b01"+impRecord.getImptemptable()+" k where ")
	          		.append(" t.b0114=k.b0114 and t.b0111 not like '"+impRecord.getImpdeptid()+"%'");
        	   Object obj2 = sess.createSQLQuery(sqlbf2.toString()).uniqueResult();
        	   if(Integer.parseInt(obj.toString())>0 || Integer.parseInt(obj2.toString())>0){
                   this.setMainMessage((Integer.parseInt(obj.toString())>0?"导入数据中的人员身份证号码与系统中重复 "+obj.toString()+" 人！":"")
                		   +(Integer.parseInt(obj2.toString())>0?"导入数据中的机构编码与系统中其他机构重复 "+obj2.toString()+" 个！":"")+ "是否继续接收信息？");
                   this.setMessageType(EventMessageType.CONFIRM); 
                   this.addNextEvent(NextEventValue.YES,"impmodel", str);
               }else{
                   this.impmodelOnclick(str);
               }
			} catch (Exception e) {
				e.printStackTrace();
			}
           return EventRtnType.NORMAL_SUCCESS;
       }
       
       /**
        * 导入正确数据
        * @return
        * @throws RadowException
        */
       @PageEvent("impmodel")
       @NoRequiredValidate
       public int impmodelOnclick(String str)throws RadowException{
           HBSession sess = HBUtil.getHBSession();
           try {
               CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
               String imprecordid = this.getPageElement("imprecordid").getValue();
               /**
                * 历史库代码暂无
                */
               Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
//             
               UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
               //-------------------------------------------------
               String sql1 = "delete from Datarecrejlog where imprecordid = '"+imprecordid+"' ";
               sess.createQuery(sql1).executeUpdate();
               //------------------------------------------------
               ImpmodelThread thr = new ImpmodelThread(imp,str, user,userVo); 
               new Thread(thr).start();
               this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('dataComparisonWin').close();");
//             this.closeCueWindow();
//             this.setRadow_parent_data(imprecordid);
//             this.openWindow("refreshWin", "pages.dataverify.RefreshOrgRecRej");
               this.getExecuteSG().addExecuteCode("realParent.$h.openWin('refreshWin1','pages.dataverify.RefreshOrgRecRej','接收窗口',700,445, '"+imprecordid+"','"+request.getContextPath()+"');");
           } catch (Exception e) {
               if(sess!=null)
                   sess.getTransaction().rollback();
               e.printStackTrace();
           }
           return EventRtnType.NORMAL_SUCCESS;
       }
       
       /**
   	 * 分页查询最好采取以下方法
   	 * 
   	 * @param start
   	 * @param limit
   	 * @return
   	 * @throws RadowException
   	 * @throws AppException
   	 */
   	@PageEvent("list1.dogridquery")
   	@NoRequiredValidate
   	@AutoNoMask
   	public int dogridQuery(int start, int limit) throws RadowException, AppException {
   		//String jgmc  = this.getPageElement("jgmc").getValue();//机构名称
   		String dataResultType  = this.getPageElement("dataResultType").getValue();//数据包比对结果
        //String imprecordid = request.getParameter("initParams");//imp_record导入记录表id   一个数据包
   		String imprecordid = this.getPageElement("imprecordid").getValue();
   		String errorSign = this.getPageElement("errorSign").getValue();
   		Imprecord imp = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, imprecordid);
   		
   		if(StringUtils.isNotBlank(imprecordid)){
   		    StringBuffer sql = new StringBuffer();
   		    if(errorSign!=null && !"".equals(errorSign)){
   		    	if("0".equals(errorSign)){//检测出机构编码为空
   					if(DBType.ORACLE == DBUtil.getDBType()){
   						sql.append("SELECT B.B0101 b0101_n,B.B0114 b0114_n,'机构编码为空' opptimetype FROM B01"+imp.getImptemptable()+" B WHERE 1 = 1 AND B.B0114 IS NULL");
   					}else{
   						sql.append("SELECT B.B0101 b0101_n,B.B0114 b0114_n,'机构编码为空' opptimetype FROM B01"+imp.getImptemptable()+" B WHERE 1 = 1 AND (B.B0114 IS NULL OR B.B0114 = '')");
   					}
   		    	}else if("1".equals(errorSign)){//机构编码重复
   		    		sql.append("SELECT B.B0101 b0101_n,B.B0114 b0114_n,'机构编码重复' opptimetype FROM B01"+imp.getImptemptable()+" B WHERE B.B0114 IN (SELECT B0114 FROM B01"+imp.getImptemptable()+" GROUP BY B0114 HAVING count(*) > 1)");
   		    	}
   		    }else{
   		    	sql.append("select t.storeid,t.b0111_n,t.b0111_w,t.b0111_parent_n,t.b0111_parent_w"
   			            + ",t.b0101_n,t.b0101_w,t.b0114_n,t.b0114_w"
   			            + ",t.imp_record_id,t.comments,t.opptimetype,");
   	   		    if(DBType.ORACLE == DBUtil.getDBType()){
   	   		    	sql.append("to_char(t.opptime, 'yyyy-mm-dd hh24:mi:ss') opptime "
   	   	   		            + "from TAB_DataContrastResult t where 1 = 1 ");
   	   		    }
   	   		    if(DBType.MYSQL == DBUtil.getDBType()){
   	   		    	sql.append("date_format(t.opptime, '%Y-%m-%d %H:%i:%s') opptime "
   	   	   		            + "from TAB_DataContrastResult t where 1 = 1 ");
   			    }
   	   		    /*if(StringUtils.isNotBlank(jgmc)){
   	   		        jgmc = getStrToLikeSelectStr(jgmc);
   	   		        sql.append(" and t.b0101_n like '"+jgmc+"'");//机构名称
   	   		    }*/
   	   		    if(!"5".equals(dataResultType)){
   	   		        if(!"4".equals(dataResultType)){//查询全部
   	   		            sql.append(" and t.opptimetype = '"+dataResultType+"'");//数据包比对结果
   	   		        }
   	   		    }else{//默认查询有变动的数据
   	   		        sql.append(" and t.opptimetype != '1' ");//数据包比对结果
   	   		    }
   	   		    sql.append(" and t.imp_record_id = '"+imprecordid+"' ");
   		    }
   		    this.pageQuery(sql.toString(), "SQL", start, limit); //处理分页查询
   		    return EventRtnType.SPE_SUCCESS;
   		}
   		return EventRtnType.NORMAL_SUCCESS;
   	}
}
