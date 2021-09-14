package com.insigma.siis.local.pagemodel.yntp;


import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.FileItem;

import com.JUpload.JUpload;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.HzJs2Yntp;
import com.insigma.siis.local.business.entity.TpbAtt;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.utils.SQLiteUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TPBPageModel extends PageModel implements JUpload {
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		
		//设置下拉框
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		HzJs2Yntp rb = (HzJs2Yntp) HBUtil.getHBSession().get(HzJs2Yntp.class, ynId);
		if(rb==null){
			this.setMainMessage("无批次信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql = "select tp0100, a0000, type, tp0101, tp0102, tp0103, tp0104,"
				+ " tp0105, tp0106, tp0107,tp0108,tp0109,tp0110, tp0111,tp0112,tp0113,tp0114,tp0115,nvl2((select jsa00 from tpb_att where js0100=hz_TPHJ1.a0000 and rb_id=hz_TPHJ1.yn_id),'kcclclass','default_color') kcclclass from hz_TPHJ1 where yn_id='"+ynId+"' and tp0116='"+yn_type+"' order by sortnum asc ";
		String js2_yntp_info = "select info0y, info0m, info0d from hz_js2_yntp_info where yn_id='"+ynId+"' and info01='"+yn_type+"'";
		
		
		try {
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
			JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
			//System.out.println(updateunDataStoreObject.toString());
			this.getExecuteSG().addExecuteCode("TIME_INIT.changeTableType('"+yn_type+"');");
			this.getExecuteSG().addExecuteCode("doAddPerson.addPerson("+updateunDataStoreObject.toString()+");");
			//会议时间
			listCode=cqbs.getListBySQL(js2_yntp_info);
			if(listCode.size()>0){
				Map<String, Object> map = listCode.get(0);
				String y = map.get("info0y")==null?"":map.get("info0y").toString();
				String m = map.get("info0m")==null?"":map.get("info0m").toString();
				String d = map.get("info0d")==null?"":map.get("info0d").toString();
				this.getExecuteSG().addExecuteCode("TIME_INIT.setTime('"+y+"','"+m+"','"+d+"','"+yn_type+"');");
			}
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("openRMB")
	public int openRMB(String a0000) throws RadowException, AppException{
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(a01==null||"4".equals(a01.getStatus())){
			this.setMainMessage("未查询到相关人员信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,730,null,"
		+"{a0000:'"+a0000+"',gridName:'',maximizable:false,resizable:false,draggable:false},true);");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("hasSaved")
	public int hasSaved() throws RadowException, AppException{
		String tp0100 = this.request.getParameter("tp0100");
		String sql = "select 1 from hz_TPHJ1 where tp0100='"+tp0100+"'";
		
		
		try {
			List list =HBUtil.getHBSession().createSQLQuery(sql).list();
			if(list.size()>0){
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				return EventRtnType.FAILD;
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
			return EventRtnType.FAILD;
		}
		
	}
	
	
	//按姓名查询，传递人员IDs
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String listStr) throws Exception{
		//System.out.println(listStr);
		HBSession sess = HBUtil.getHBSession();
		
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		StringBuffer sql = new StringBuffer();
		if(listStr!=null && listStr.indexOf("TPHJ")>=0){//选择了其它会议类型
			String[] params = listStr.split("@@");
			String ortherYnid = "";
			if(params.length==2){
				ortherYnid = params[0];
				listStr = params[1];
			}else{
				ortherYnid = ynId;
			}
			sql.append("select sys_guid() tp0100, a0000, type, tp0101, tp0102, tp0103, tp0104,"
					+ " tp0105, tp0106, tp0107,tp0108,tp0109,tp0110, tp0111,tp0112,tp0113,tp0114,tp0115,nvl2((select jsa00 from tpb_att where js0100=t.a0000 and rb_id=t.yn_id),'kcclclass','default_color') kcclclass from hz_TPHJ1 t where yn_id='"+ortherYnid+"' and tp0116='"+listStr+"' ");
			sql.append(" and not exists (select 1 from hz_TPHJ1 p where p.a0000=t.a0000");
			sql.append(" and yn_id='"+ynId+"' and tp0116='"+yn_type+"')  order by sortnum asc ");
			try {
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
				JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
				//System.out.println(updateunDataStoreObject.toString());
				this.getExecuteSG().addExecuteCode("doAddPerson.addPerson("+updateunDataStoreObject.toString()+")");
				
				//提取考察材料
				if(params.length==2){
					
					String kcclsql = "select * from tpb_att where rb_id='"+ortherYnid+"' and js0100 in (select a0000 from hz_TPHJ1 t "
							+ " where yn_id='"+ortherYnid+"' and tp0116='"+listStr+"' "
					+" and not exists (select 1 from hz_TPHJ1 p where p.a0000=t.a0000"
					+" and yn_id='"+ynId+"' and tp0116='"+yn_type+"'))";
					List<TpbAtt> tpbattList = sess.createSQLQuery(kcclsql).addEntity(TpbAtt.class).list();
					String directory = "zhgbuploadfiles" + File.separator +ynId+ File.separator;
					String disk = YNTPFileExportBS.HZBPATH;
					File f = new File(disk + directory);
					if(!f.isDirectory()){
						f.mkdirs();
					}
					for(TpbAtt tpbAtt : tpbattList){
						TpbAtt tat = new TpbAtt();
						tat.setJs0100(tpbAtt.getJs0100());//人员信息
						tat.setJsa00(UUID.randomUUID().toString());//主键
						tat.setRbId(ynId);//批次id
						tat.setJsa05(SysManagerUtils.getUserId());//用户id
						tat.setJsa06(tpbAtt.getJsa06());//上传时间
						tat.setJsa04(tpbAtt.getJsa04());
						
						
						String targetPath = disk + directory  + tat.getJsa00();
						String sourcePath = disk+tpbAtt.getJsa07() + tpbAtt.getJsa00();
						SQLiteUtil.copyFileByPath(sourcePath, targetPath);
						
						tat.setJsa07(directory);
						tat.setJsa08(tpbAtt.getJsa08());
						sess.save(tat);
						
					}
				}
				
				sess.flush();
			} catch (Exception e) {
				this.setMainMessage("查询失败！");
				e.printStackTrace();
			}
		}else if(listStr!=null && listStr.length()>2){//基础库选择人员
			sql.append("select  sys_guid() tp0100, t.a0000 a0000,"
					+ " '3' type,GET_tpbXingming(t.a0101,t.a0104,t.a0117,t.a0141) tp0101,t.a0107 tp0102,t.a1701 tp0103, t.a0288 tp0104,"
					+ " GET_TPBXUELI2(t.qrzxl,t.zzxl,t.qrzxw,t.zzxw) tp0105,"
					+ " t.a0192a tp0106,'' tp0107,'' tp0108,'' tp0109,'' tp0110,"
					+ " '' tp0111,'' tp0112,'' tp0113,'' tp0114,'' tp0115,'default_color' kcclclass from a01 t");
		
			sql.append(" where t.a0000 in ('-1'");
			listStr = listStr.substring(1, listStr.length()-1);
			List<String> list = Arrays.asList(listStr.split(","));
			final Map<String, Integer> mapSort = new HashMap<String, Integer>();
			int sort = 0;
			for(String id:list){
				mapSort.put(id.trim(), sort++);
				sql.append(",'"+id.trim()+"'");
				
			}
			sql.append(") ");
			sql.append(" and not exists (select 1 from hz_TPHJ1 p where p.a0000=t.a0000");
			sql.append(" and yn_id='"+ynId+"' and tp0116='"+yn_type+"')");
			try {
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
				Collections.sort(listCode, new Comparator<HashMap<String, Object>>() {
					@Override
					public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
						// TODO Auto-generated method stub
						/*Integer s1 = 0,s2=0;
						for(String a0000 : mapSort.keySet()){
							if(a0000.equals(o1.get("a0000"))){
								s1 = mapSort.get(a0000);
							}
							if(a0000.equals(o2.get("a0000"))){
								s2 = mapSort.get(a0000);
							}	
						}
						return s1-s2;*/
						return mapSort.get(o1.get("a0000"))-mapSort.get(o2.get("a0000"));
					}
				});
				for(HashMap m : listCode){
					//出生年月处理
					String text = this.getFTime(m.get("tp0102"));
					m.put("tp0102",text);
					//任现职时间处理
					text = this.getFTime(m.get("tp0104"));
					m.put("tp0104",text);
					//任现职时间处理
					String jianli = m.get("tp0103")==null?"":m.get("tp0103").toString();
					String[] jianliArray = jianli.split("\r\n|\r|\n");
					if(jianliArray.length>0){
						String jl = jianliArray[jianliArray.length-1].trim();
						//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
						Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");     
				        Matcher matcher = pattern.matcher(jl);     
				        if (matcher.find()) {
				        	String line1 = matcher.group(0);  
				        	m.put("tp0103",line1.substring(2,7));
				        }else{
				        	m.put("tp0103",null);
				        }
					}else{
						m.put("tp0103",null);
					}
					
					
				}
				JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
				//System.out.println(updateunDataStoreObject.toString());
				this.getExecuteSG().addExecuteCode("doAddPerson.addPerson("+updateunDataStoreObject.toString()+")");
				
			} catch (Exception e) {
				this.setMainMessage("查询失败！");
				e.printStackTrace();
			}
		}else{
			this.setMainMessage("无法查询到该人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
			
			
		return EventRtnType.NORMAL_SUCCESS;
		
	}

	
	private String getFTime(Object tex){
		String text = null;
		if(tex!=null){
			text = tex.toString();
			if(text.length()>=6){
				return text.substring(0, 4)+"."+text.substring(4, 6);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	//保存
	@SuppressWarnings("unchecked")
	@PageEvent("save")
	public int save(String a) throws Exception{
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		String tpy = this.getPageElement("tpy").getValue();
		String tpm = this.getPageElement("tpm").getValue();
		String tpd = this.getPageElement("tpd").getValue();
		
		HzJs2Yntp rb = (HzJs2Yntp) HBUtil.getHBSession().get(HzJs2Yntp.class, ynId);
		if(rb==null){
			this.setMainMessage("无批次信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String ID_ROWINFO = this.getPageElement("ID_ROWINFO").getValue().replace("\n", "{/n}");
		String ROWID = this.getPageElement("ROWID").getValue();
		JSONObject  ID_ROWINFOObject = JSONObject.fromObject(ID_ROWINFO);
		
		Map<String,Map<String,String>> ID_ROWINFOMap = (Map<String,Map<String,String>>)ID_ROWINFOObject;
		JSONArray  ROWIDObject = JSONArray.fromObject(ROWID);
		List<String> ROWIDList = (List<String>)ROWIDObject;
		
		
		HBSession sess = null;
		PreparedStatement ps = null;
		Statement stat = null;
		Connection conn = null;
		String sql = "insert into hz_TPHJ1(tp0100, a0000, type, tp0101, tp0102, tp0103, tp0104, tp0105, tp0106, tp0107, sortnum, yn_id,tp0116,tp0111,tp0112,tp0115 )"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		String updateJs2_yntp_info = "update hz_Js2_yntp_info set info0y=?, info0m=?, info0d=? where yn_id=? and info01=?";
		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			//更新批次信息
			ps = conn.prepareStatement(updateJs2_yntp_info);
			ps.setString(1, tpy);
			ps.setString(2, tpm);
			ps.setString(3, tpd);
			ps.setString(4, ynId);
			ps.setString(5, yn_type);
			ps.executeUpdate();
			//删除
			ps = conn.prepareStatement("delete from hz_TPHJ1 where yn_id=? and tp0116=?");
			ps.setString(1, ynId);
			ps.setString(2, yn_type);
			ps.executeUpdate();
			//更新当前环节
			ps = conn.prepareStatement("update hz_js2_yntp set yn_type=? where yn_id=?");
			ps.setString(1, yn_type);
			ps.setString(2, ynId);
			ps.executeUpdate();
			//插入
			ps = conn.prepareStatement(sql);
			for(int i=0;i<ROWIDList.size();i++){
				String id = ROWIDList.get(i);
				Map<String,String> rowData = ID_ROWINFOMap.get(id);
				ps.setString(1, rowData.get("tp0100"));
				ps.setString(2, textFormat(rowData.get("a0000")));
				ps.setString(3, rowData.get("type"));
				ps.setString(4, textFormat(rowData.get("tp0101")));
				ps.setString(5, textFormat(rowData.get("tp0102")));
				ps.setString(6, textFormat(rowData.get("tp0103")));
				ps.setString(7, textFormat(rowData.get("tp0104")));
				ps.setString(8, textFormat(rowData.get("tp0105")));
				ps.setString(9, textFormat(rowData.get("tp0106")));
				ps.setString(10, textFormat(rowData.get("tp0107")));
				ps.setInt(11, i);
				ps.setString(12, ynId);
				ps.setString(13, yn_type);
				ps.setString(14, textFormat(rowData.get("tp0111")));
				ps.setString(15, textFormat(rowData.get("tp0112")));
				ps.setString(16, textFormat(rowData.get("tp0115")));
				ps.addBatch();
			}
			ps.executeBatch();
			
			
			//删除附件
			String tp0100SQL = "select jsa00,jsa07 from TPB_ATT a where rb_id=? and"
					+ " not exists(select 1 from hz_TPHJ1 b where b.a0000=a.js0100 and yn_id=?) ";
			ps = conn.prepareStatement(tp0100SQL);
			ps.setString(1, ynId);
			ps.setString(2, ynId);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				String jsa00 = rst.getString(1);
				String jsa07 = rst.getString(2);
				String directory = YNTPFileExportBS.HZBPATH+jsa07;
				File f = new File(directory+jsa00);
				if(f.isFile()){
					f.delete();
				}
			}
			rst.close();
			ps = conn.prepareStatement("delete from TPB_ATT where rb_id=? and jsa00 in(select jsa00 from TPB_ATT a where "
					+ " not exists(select 1 from hz_TPHJ1 b where b.a0000=a.js0100 and yn_id=?))");
			ps.setString(1, ynId);
			ps.setString(2, ynId);
			ps.executeUpdate();
			
			//删除职务记录
			ps = conn.prepareStatement("delete from HZ_TPHJ_zw a where "
					+ " not exists(select 1 from hz_TPHJ1 b where b.tp0100=a.tp0100 and b.yn_id=? and b.tp0116=?)  and yn_id=? and tp0116=?");
			ps.setString(1, ynId);
			ps.setString(2, yn_type);
			ps.setString(3, ynId);
			ps.setString(4, yn_type);
			ps.executeUpdate();
			
			
			ps.close();
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			try{
				if(conn!=null)
					conn.rollback();
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				this.setMainMessage("保存失败！");
				e.printStackTrace();
			}
			if(e.getMessage()!=null&&e.getMessage().indexOf("ORA-00001")>=0){
				this.setMainMessage("保存失败！请确认读档后没有重复人员");
			}else{
				this.setMainMessage("保存失败！");
			}
			
			e.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("updateNRM".equals(a)){
			this.setNextEventName("updateNRM");
		}else if("savefirst".equals(a)){
			this.getExecuteSG().addExecuteCode("uploadKCCL.openUploadKcclWin()");
		}else{
			this.getExecuteSG().addExecuteCode("Ext.example.msg('','保存成功。',1);");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//保存
	@SuppressWarnings("unchecked")
	@PageEvent("saveGD")
	public int saveGD(String text) throws Exception{
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		
		HzJs2Yntp rb = (HzJs2Yntp) HBUtil.getHBSession().get(HzJs2Yntp.class, ynId);
		if(rb==null){
			this.setMainMessage("无批次信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String yn_gd_id = UUID.randomUUID().toString();
		String JS2_YNTP_gdsql = "insert into hz_JS2_YNTP_gd(yn_id,yn_name,yn_type,yn_userid,yn_org,yn_sysno,yn_date,"
				+ "yn_gd_id,yn_gd_desc )"
				+ " (select yn_id,yn_name,?,yn_userid,yn_org,yn_sysno,sysdate,"
				+ "?,? from hz_JS2_YNTP where yn_id=?)";
		
		HBSession sess = null;
		PreparedStatement ps = null;
		Connection conn = null;
		String sql = "insert into hz_TPHJ1_gd(tp0100,a0000,type,tp0101,tp0102,tp0103,tp0104,tp0105,tp0106,"
				+ "tp0107,tp0108,tp0109,tp0110,tp0111,sortnum,tp0112,tp0113,tp0114,tp0115,yn_id,"
				+ "tp0116,yn_gd_id )"
				+ " (select tp0100,a0000,type,tp0101,tp0102,tp0103,tp0104,tp0105,tp0106,"
				+ "tp0107,tp0108,tp0109,tp0110,tp0111,sortnum,tp0112,tp0113,tp0114,tp0115,yn_id,"
				+ "tp0116,? from hz_TPHJ1 where yn_id=? and tp0116=?)";
		
		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			//插入
			ps = conn.prepareStatement(JS2_YNTP_gdsql);
			ps.setString(1, yn_type);
			ps.setString(2, yn_gd_id);
			ps.setString(3, text);
			ps.setString(4, ynId);
			ps.executeUpdate();
			
			//插入
			ps = conn.prepareStatement(sql);
			ps.setString(1, yn_gd_id);
			ps.setString(2, ynId);
			ps.setString(3, yn_type);
			ps.executeUpdate();
			
			ps.close();
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			try{
				if(conn!=null)
					conn.rollback();
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				this.setMainMessage("存档失败！");
				e.printStackTrace();
			}
			this.setMainMessage("存档失败！");
			e.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		this.getExecuteSG().addExecuteCode("Ext.example.msg('','存档成功。',1);");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//更新拟任免
	@SuppressWarnings("unchecked")
	@PageEvent("updateNRM")
	public int updateNRM(String a0000p) throws Exception{
		String ynId = this.getPageElement("ynId").getValue();
		String yn_type = this.getPageElement("yntype").getValue();
		
		HzJs2Yntp rb = (HzJs2Yntp) HBUtil.getHBSession().get(HzJs2Yntp.class, ynId);
		if(rb==null){
			this.setMainMessage("无批次信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql = "";
		if(a0000p==null||"".equals(a0000p)){
			sql = "select a0000,tp0111,tp0112 from hz_TPHJ1 where yn_id='"+ynId+"' and tp0116='"+yn_type+"' and a0000 is not null";
		}else{
			sql = "select a0000,tp0111,tp0112 from hz_TPHJ1 where yn_id='"+ynId+"' and tp0116='"+yn_type+"' and  a0000='"+a0000p+"'";
		}
		HBSession sess = HBUtil.getHBSession();
		
		
		try {
			List<Object[]> list = sess.createSQLQuery(sql).list();
			String userid = SysUtil.getCacheCurrentUser().getId();
			
			for (int i = 0; i < list.size(); i++) {
				Object[] info = list.get(i);
				String a0000 = info[0]==null?"":info[0].toString();
				String sqla53 = "from A53 where a0000='"+a0000+"' and a5399='"+userid+"'";
				List<A53> a53list = sess.createQuery(sqla53).list();
				if(a53list.size()>0){
					A53 a53 = a53list.get(0);
					a53.setA5304(info[1]==null?"":info[1].toString());
					a53.setA5315(info[2]==null?"":info[2].toString());
					if(a53.getA5317()==null){
						a53.setA5317("工作需要");
						sess.save(a53);
					}
					sess.update(a53);
				}else{
					A53 a53 = new A53();
					a53.setA0000(a0000);
					a53.setA5399(userid);
					a53.setA5304(info[1]==null?"":info[1].toString());
					a53.setA5315(info[2]==null?"":info[2].toString());
					String date = DateUtil.getcurdate();
					a53.setA5321(date);
					a53.setA5323(date);
					a53.setA5327(SysManagerUtils.getUserName());
					a53.setA5317("工作需要");
					sess.save(a53);
				}
				
				sess.flush();
			}
		}catch (Exception e) {
			this.setMainMessage("更新拟任免信息失败！");
			e.printStackTrace();
		}
		
		this.setMainMessage("更新拟任免信息成功！");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private String textFormat(Object v){
		String value = null;
		if(v!=null){
			if("null".equals(v.toString())){
				return null;
			}
			value = v.toString().replace("{/n}", "\n");
		}
		return value;
	}
	
	/**
	 * 信息导出
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("ExpTPB")
	public int ExpTPB(String param) throws Exception{
		try {
			
		
			YNTPFileExportBS fileBS = new YNTPFileExportBS(null);
			//String rbId = this.getPageElement("rbId").getValue();			
			String ynId = this.request.getParameter("ynId");//批次
			String yntype = this.request.getParameter("yntype");//环节
			//String expType = this.request.getParameter("expType");//导出类型
			
			
			
			fileBS.setOutputpath(YNTPFileExportBS.HZBPATH+"zhgboutputfiles/"+ynId+"/");
			File f = new File(fileBS.getOutputpath());
			
			if(f.isDirectory()){//先清空输出目录
				JSGLBS.deleteDirectory(fileBS.getOutputpath());
			}
			
			fileBS.exportGBTP(ynId,yntype);
			
			
			List<String> fns = fileBS.getOutputFileNameList();
			String downloadPath = "";
			String downloadName = "";
			if(fns.size()==1){//一个文件直接下载
				downloadPath = fileBS.getOutputpath()+fns.get(0);
				downloadName = fns.get(0);
			}else if(fns.size()>1){//压缩
				//downloadPath = fileBS.getOutputpath()+fileBS.getSheetName()+".zip";
				//downloadName = fileBS.getSheetName()+".zip";
				//Zip7z.zip7Z(fileBS.getOutputpath(), downloadPath, null);
				
			}else{
				return EventRtnType.NORMAL_SUCCESS;
			}
			String downloadUUID = UUID.randomUUID().toString();
			request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	
	
	/**
	 * 导出任免表word
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("expWord")
	public int expWord(String param) throws Exception{
		try {
			
			
			YNTPFileExportBS fileBS = new YNTPFileExportBS(null);
			//String rbId = this.getPageElement("rbId").getValue();			
			String ynId = this.getPageElement("ynId").getValue();
			String yntype = this.getPageElement("yntype").getValue();
			//String expType = this.request.getParameter("expType");//导出类型
			
			HzJs2Yntp rb = (HzJs2Yntp) HBUtil.getHBSession().get(HzJs2Yntp.class, ynId);
			if(rb==null){
				this.setMainMessage("无批次信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			fileBS.setOutputpath(YNTPFileExportBS.HZBPATH+"zhgboutputfiles/"+ynId+"/");
			File f = new File(fileBS.getOutputpath());
			
			if(f.isDirectory()){//先清空输出目录
				JSGLBS.deleteDirectory(fileBS.getOutputpath());
			}
			fileBS.NORM = param;
			fileBS.expWord(ynId,yntype);
			File fa[] = f.listFiles();
			
			for (int i = 0; i < fa.length; i++) {
				File fs = fa[i];
				if (fs.isFile()) {
					
					String filename = fs.getName();
					String downloadUUID = UUID.randomUUID().toString();
					request.getSession().setAttribute(downloadUUID, new String[]{fileBS.getOutputpath()+filename,filename});
					this.getExecuteSG().addExecuteCode("Print.startPrint('"+downloadUUID+"','"+request.getSession().getId()+"');");
				} 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("导出失败"+e.getMessage());
		}
		this.setMainMessage("打印完成");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("impBtn.onclick")
	public int impBtn() throws RadowException, AppException{
		this.getExecuteSG().addExecuteCode("wait();");
		this.upLoadFile("file5");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) {
		Map<String, String> map = new HashMap<String, String>();
		// 获得文件名称
		String isfile = formDataMap.get("Filename");
		// 判断是否上传了附件，没有上传则不进行文件处理
		if (isfile != null && !isfile.equals("")) {
			try {
				// 获取表单信息
				FileItem fi = fileItem.get(0);
				DecimalFormat df = new DecimalFormat("#.00");
				String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
				// 如果文件大于1M则显示M，小于则显示kb
				if (fi.getSize() < 1048576) {
					fileSize = (int) fi.getSize() / 1024 + "KB";
				}
				if (fi.getSize() < 1024) {
					fileSize = (int) fi.getSize() / 1024 + "B";
				}
				YNTPFileExportBS fileBS = new YNTPFileExportBS(null);
				fileBS.setOutputpath(YNTPFileExportBS.HZBPATH+"zhgboutputfiles/"+SysManagerUtils.getUserloginName()+"/");
				File f = new File(fileBS.getOutputpath());
				
				if(f.isDirectory()){//先清空输出目录
					//JSGLBS.deleteDirectory(fileBS.getOutputpath());
				}
				//String yntype = formDataMap.get("imptype");
				fileBS.exportChangeTPB(fi,isfile);
				String id = UUID.randomUUID().toString();
				String outFileName = fileBS.getOutputFileNameList().get(0);
				request.getSession().setAttribute(id, new String[]{fileBS.getOutputpath()+outFileName,outFileName});
				map.put("file_pk", id);
				map.put("file_name", isfile);
			} catch (Exception e) {
				map.put("file_pk", "111");
				map.put("file_name", isfile);
				map.put("error", isfile+"转换失败");
				e.printStackTrace();
			}
		}

		return map;
	}



	@Override
	public String deleteFile(String id) {
		return id;
	}
}