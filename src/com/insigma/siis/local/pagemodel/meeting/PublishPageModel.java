package com.insigma.siis.local.pagemodel.meeting;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.siis.local.pagemodel.cadremgn.util.ExtTreeNodeStr;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.hsqldb.lib.StringUtil;

import com.JUpload.JUpload;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FieldMergingArgs;
import com.aspose.words.IFieldMergingCallback;
import com.aspose.words.ImageFieldMergingArgs;
import com.aspose.words.SaveFormat;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.demo.entity.BbUtils;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.PublishAtt;
import com.insigma.siis.local.business.entity.WJGLAdd;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.lrmx.ItemXml;
import com.insigma.siis.local.lrmx.JiaTingChengYuanXml;
import com.insigma.siis.local.lrmx.PersonXml;
import com.insigma.siis.local.pagemodel.cadremgn.util.JsonUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryPageModel;
import com.insigma.siis.local.pagemodel.customquery.DataSHPadImpDBThread;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.exportexcel.FiledownServlet;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;


public class PublishPageModel extends PageModel implements JUpload {
	private final static int ON_ONE_CHOOSE=-1;
	private final static int CHOOSE_OVER_TOW=-2;
	CommQuery cqbs=new CommQuery();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		String meetingid=this.getPageElement("subWinIdBussessId2").getValue();
		//String sql = "select p.publishid,p.agendaname from publish p"
		//		+ " where p.meetingid = '"+meetingid+"'";
		String userid = SysManagerUtils.getUserId();
		this.getPageElement("new_userid").setValue(userid);
		String sql = "select meetingid,meetingname from "
				+ "(select meetingid,meetingname,meetingtype,time,userid from meetingtheme a where userid='"+userid+"'"
				+ " union "
				+ "  select meetingid,meetingname,meetingtype,time,userid from meetingtheme a where "
				+ "		meetingid in (select b.meetingid from publish b,publishuser c where b.publishid=c.publishid and c.userid='"+userid+"' and (c.islook='1' or c.ischange='1'))"
				+ "	) t order by time desc";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
		HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
		for(int i=0;i<listCode.size();i++){
			String tp0116 = listCode.get(i).get("meetingid").toString();
			mapCode.put(tp0116, listCode.get(i).get("meetingname").toString());
		}
//		Combo co = new Combo(this);
//	    co.setId("meetingname");
//	    co.setValueListForSelect(mapCode);

		((Combo)this.getPageElement("meetingname")).setValueListForSelect(mapCode);
		this.getPageElement("meetingname").setValue(meetingid);
		this.getExecuteSG().addExecuteCode("updateTree();");
		//this.getExecuteSG().addExecuteCode("document.getElementById('publish_id').value='"+selValue+"'");
		//this.setNextEventName("publishGrid.dogridquery");
		//this.setNextEventName("peopleGrid.dogridquery");
		return 0;
	}
	//导出个人数据
		@PageEvent("exportLrmxBtn")
		public int exportLrmxBtn(String gsType) throws RadowException, UnsupportedEncodingException {
			StringBuffer checkIds = new StringBuffer();
			String  publish_id = this.getPageElement("publish_id").getValue();
			
				/*String sql="select a.* from hz_sh_a01 a where a.publishid='"+publish_id+"' ";
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> list;
				try {
					list = cqbs.getListBySQL(sql);
					for(int j=0;j<list.size();j++){
						HashMap<String, Object> map = list.get(j);
						checkIds.append("'").append(map.get("a0101").toString()).append("',");
					}
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			if(publish_id.length() == 0){
				this.setMainMessage("请至少选择一个议题！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		if(publish_id!=null){
			HBSession sess = HBUtil.getHBSession();
			String sql="select a.* from "
					+ "		(select a0000,a0101,sh000,titleid,sh001 from hz_sh_a01 a where a0000 is not null and publishid ='"+publish_id+"' "
					+ " 		union all "
					+ "  		select a.a0000,a.a0101,a.sh000,titleid_new,b.sh001 from hz_sh_a01 a,personcite b where a.a0000 is not null and a.sh000=b.sh000 and b.publishid_new='"+publish_id+"') a,"
					+ "		(select titleid,case when title02='1' then a.sortid+1000||'10001000' "
					+ " 		when title02='2' then  nvl((select b.sortid from hz_sh_title b where b.titleid=a.title04 ),0)+1000||''||lpad(a.sortid,4,0)+1000||'1000'"
					+ " 		when title02='3' then  nvl((select c.sortid from hz_sh_title b,hz_sh_title c where b.titleid=a.title04 and c.titleid=b.title04 ),0)+1000||''"
					+ "				||lpad(nvl((select b.sortid from hz_sh_title b where b.titleid=a.title04 ),0),4,0)+1000||lpad(a.sortid,4,0)+1000||'' end sortid"
					+ "			 from hz_sh_title a where publishid ='"+publish_id+"') b where a.titleid=b.titleid order by b.sortid,a.sh001";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list;
			try {
				list = cqbs.getListBySQL(sql);
				String a0000 = "";
				String a0101 = "";
				String sh000 = "";
				String path = getPath();
				String infile = "";
				String name = "";
				if(list !=null && list.size() > 0){
					String zippath=path+"议题Lrmx导出_"+DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+"/";
					//String zippath =path+"sh_lrmx/";
					File f = new File(zippath);
					if(!f.isDirectory()){
						f.mkdirs();
					}
					DataSHPadImpDBThread datash=new DataSHPadImpDBThread(zippath, null);
					for(int j = 0;j < list.size(); j++){
						a0101 = list.get(j).get("a0101").toString();
						a0000 = list.get(j).get("a0000").toString();
						sh000 = list.get(j).get("sh000").toString();
						PersonXml per = datash.Parenthesisprocessing(a0000,"",sh000);
						try {
							FileUtil.createFile(zippath+(j+1)+"_"+a0101+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
							A01 a01log = new A01();
							new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出", new Map2Temp().getLogInfo(new A01(), a01log));
						} catch (Exception e) {
							
						}
					}
					try {
						infile =zippath.substring(0,zippath.length()-1)+".zip" ;
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.downloadword()");
					} catch (Exception e) {
						this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
					}
				}
				
				
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//List<HashMap<String, Object>> list = sess.createSQLQuery(sql).list();
			
		}
		
			return EventRtnType.NORMAL_SUCCESS;
			
		}

	/**
	 * 标题树更新
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("updateTree")
	public int updateTree() throws RadowException, AppException{
		String node=request.getParameter("node");
		String title04=node;
		String meetingid=this.request.getParameter("meetingid");
		String userid = SysManagerUtils.getUserId();
		//String sql="select * from hz_sh_title where publishid='"+publishid+"' and title04='"+title04+"' order by to_number(sortid)";
		String sql ="select * from ( select titleid,title01,decode(title04,'-1',publishid,title04) title04,'2' type,sortid  from hz_sh_title a where  meetingid='"+meetingid+"' or publishid in (select publishid from publish where publishid in (select publishid from publishuser where (islook='1' or ischange='1') and userid='"+userid+"') or userid='"+userid+"')"
				+ " union all  "
				+ "   select publishid,agendaname,'-1','1',sort sortid    from publish where meetingid='"+meetingid+"' and (publishid in (select publishid from publishuser where (islook='1' or ischange='1') and userid='"+userid+"') or userid='"+userid+"')"
//				+ " union all  "
//				+ "   select b.publishid,a.agendaname,'-1','1',sort sortid     from publish a,publishcite b where b.meetingid_new='"+meetingid+"' and a.publishid=b.publishid and (b.publishid in (select publishid from publishuser where islook='1' and userid='"+userid+"') or userid='"+userid+"')"
				+ ") a where a.title04='"+title04+"' order by sortid";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		List<ExtTreeNodeStr> listTree=new ArrayList<ExtTreeNodeStr>();
		if(list!=null&&list.size()>0){
			for(HashMap<String, Object> a:list){
				ExtTreeNodeStr etn=new ExtTreeNodeStr();
				etn.setId(a.get("titleid").toString());
				sql="select * from hz_sh_title where  decode(title04,'-1',publishid,title04)='"+a.get("titleid").toString()+"' order by sortid";
				List<HashMap<String, Object>> listFalg=cqbs.getListBySQL(sql);
				if(listFalg.size()>0){
					etn.setLeaf(false);
				}else{
					etn.setLeaf(true);
				}
				etn.setText(a.get("title01").toString());
				listTree.add(etn);
			}
		}
		String json = JsonUtil.toJSONString(listTree);
		System.out.println(json);
		this.setSelfDefResData(json);
		return EventRtnType.XML_SUCCESS;
	}
	private String lengthToSix(String str) {
		if(str!=null&&!"".equals(str)) {
			if(str.length()==8) {
				String dateStr = str.substring(0, 6);
				return dateStr;
			}else {
				return str;
			}
		}
		return "";
	}
	
	@PageEvent("updateTitle")
	public int updateTitle(String id) throws RadowException, AppException{
		String userid = SysManagerUtils.getUserId();
		if(id.equals("-1")) {
			this.getPageElement("publishtype").setValue("");
			this.getExecuteSG().addExecuteCode("document.getElementById('publish_id').value='';document.getElementById('publishname').value='';document.getElementById('title_id').value='';document.getElementById('titlename').value='';");
			this.getPageElement("p_userid").setValue("");
			this.getPageElement("ischange").setValue("");
		}else {
			String sql="select a.publishid,b.agendaname,a.titleid,a.title01,b.agendatype,b.userid,(select ischange from publishuser c where c.publishid=a.publishid and c.userid='"+userid+"' ) ischange from hz_sh_title a,publish b where a.titleid='"+id+"' and a.publishid=b.publishid ";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			if(list!=null&&list.size()>0) {
				this.getPageElement("p_userid").setValue(list.get(0).get("userid").toString());
				if(list.get(0).get("ischange")!=null) {
					this.getPageElement("ischange").setValue(list.get(0).get("ischange").toString());
				}else {
					this.getPageElement("ischange").setValue("");
				}
				this.getPageElement("publishtype").setValue(list.get(0).get("agendatype").toString());
				this.getExecuteSG().addExecuteCode("document.getElementById('publish_id').value='"+list.get(0).get("publishid")+"';document.getElementById('publishname').value='"+list.get(0).get("agendaname")+"';document.getElementById('title_id').value='"+list.get(0).get("titleid")+"';document.getElementById('titlename').value='"+list.get(0).get("title01")+"';");
			}else {
				sql="select publishid,agendaname,agendatype,userid,(select ischange from publishuser c where c.publishid=a.publishid and c.userid='"+userid+"' ) ischange from publish a where publishid='"+id+"' ";
				List<HashMap<String, Object>> list2=cqbs.getListBySQL(sql);
				this.getPageElement("p_userid").setValue(list2.get(0).get("userid").toString());
				if(list2.get(0).get("ischange")!=null) {
					this.getPageElement("ischange").setValue(list2.get(0).get("ischange").toString());
				}else {
					this.getPageElement("ischange").setValue("");
				}
				this.getPageElement("publishtype").setValue(list2.get(0).get("agendatype").toString());
				this.getExecuteSG().addExecuteCode("document.getElementById('publish_id').value='"+list2.get(0).get("publishid")+"';document.getElementById('publishname').value='"+list2.get(0).get("agendaname")+"';document.getElementById('title_id').value='';document.getElementById('titlename').value='';btn_init();");
			}
		}
		this.setNextEventName("peopleGrid.dogridquery");
		this.setNextEventName("personClear");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("peopleGrid.dogridquery")
	@Transaction
	@Synchronous(true)
	public int peopleSelect(int start, int limit) throws RadowException, AppException, PrivilegeException {

		String titleid= this.getPageElement("title_id").getValue();
		
		String sql = "select a.a0000,a.a0101,a0192a,a.sh000,a.sh001,a.yy_flag from  "
				+ "		(select a.a0000,a.a0101,a0192a,a.sh000,a.sh001,'2' yy_flag from hz_sh_a01 a where titleid='"+titleid+"' "
				+ "			union select a.a0000,a.a0101,a0192a,a.sh000,b.sh001,'1' yy_flag from hz_sh_a01 a,personcite b where a.sh000=b.sh000 and b.titleid_new='"+titleid+"' "
				+ "		) a  order by sh001,a0101";
		this.pageQuery(sql, "SQL", start, 200);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("peopleGrid2.dogridquery")
	@Transaction
	public int queryByName(int start, int limit) throws RadowException, AppException, PrivilegeException {

		String name= this.getPageElement("queryName").getValue();
		String sqlName = "select a0101,a0192a,a0000 from a01 where 1=1 ";
		String name2 = "";
		
		if(name!=null&&!"输入姓名".equals(name)){
			name = name.replaceAll(" ", "").replaceAll("，", ",").replaceAll("[\\t\\n\\r]", ",");

			//判断name是否包含","
			if(name.indexOf(",")>0){
				String[] names = name.split(",");
				String newName = "";
				for(String n : names){
					if(n==null||"".equals(n)){
						continue;
					}
					newName = newName + "'" + n + "',";
				}
				if(newName!=null){
					newName = newName.substring(0, newName.length()-1);
					name2 = newName.toUpperCase();
					sqlName = sqlName + " and (a01.a0184 in ("+newName+") or a01.a0101 in ("+newName+") or a01.A0102 in ("+name2+")) ";
				}
			}else{
				name2 = name.toUpperCase();
				String whr = "a01.a0101 like '%"+name+"%' or a01.A0102 like '%"+name2+"%'";
				if(name.contains("?")||name.contains("%")||name.contains("？")||name.contains("_")||name.contains("*")){																			//若存在数字视为查询身份证号
					whr = "a01.a0101 like '" + name.replace("?", "_").replace("？", "_").replace("*", "%") + "' or a01.a0101 like '" + name2.replace("?", "_").replace("？", "_").replace("*", "%") + "'";																//将查询身份证号
				}
				sqlName = sqlName + " and (a01.a0184 = '"+name+"' or "+whr+") ";
			}
		}
		
		this.pageQuery(sqlName, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("delperson")
	@Transaction
	@Synchronous(true)
	public int delperson(String str) throws RadowException, AppException, PrivilegeException {
		String publish_id= this.getPageElement("publish_id").getValue();
		String title_id= this.getPageElement("title_id").getValue();
		String titlename = this.getPageElement("titlename").getValue();
		String[] arr=str.split(",");
		String sh000=arr[0];
		String a0000=arr[1];
		String sql = "delete from  hz_sh_a01  where sh000='"+sh000+"' and publishid='"+publish_id+"' and titleid='"+title_id+"' ";
		
		HBSession sess = HBUtil.getHBSession();
		try {
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			sql="delete from personcite where publishid_new='"+publish_id+"' and sh000='"+sh000+"'";
			stmt.executeUpdate(sql);
			stmt.close();
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			new LogUtil(user).createLogNew(user.getId(),"删除上会人员","hz_sh_a01",user.getId(),titlename, new ArrayList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setNextEventName("peopleGrid.dogridquery");
		this.setNextEventName("personClear");
		//this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('peopleGrid').getStore().reload();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("publishGrid.dogridquery")
	@Transaction
	@Synchronous(true)
	public int publishSelect(int start, int limit) throws RadowException, AppException, PrivilegeException {
		//String meetingid = this.getPageElement("meetingid").getValue();
		String meetingid=this.getPageElement("subWinIdBussessId2").getValue();
		/*
		 * String sql =
		 * "select p.publishid,p.agendatype,p.agendaname,wmsys.wm_concat(pa.pat04) pat04s,wmsys.wm_concat(pa.pat00) pat00s"
		 * + " from publish p, PUBLISH_ATT pa "+
		 * " where p.publishid = pa.publishid "+ " and p.meetingid = '"
		 * +meetingid+"' group by  p.publishid,p.agendatype,p.agendaname,p.sort order by p.sort"
		 * ;
		 */
		/*
		 * String sql
		 * ="select p.publishid,p.agendatype, p.agendaname, wmsys.wm_concat(t.pat04) pat04s,wmsys.wm_concat(t.pat00) pat00s from publish p, (select * from publish_att order by sort desc) t "
		 * + "where p.publishid = t.publishid and p.meetingid = '"
		 * +meetingid+"' and p.publishid = t.publishid group by p.publishid, p.agendatype, p.agendaname, p.sort order by p.sort"
		 * ;
		 */
		/*
		 * String sql
		 * =" select p.publishid,p.agendatype,p.agendaname, wmsys.wm_concat(pat04) pat04s,wmsys.wm_concat(pat00) pat00s "
		 * +
		 * "from publish p, (select  wmsys.wm_concat(pat04) pat04 ,wmsys.wm_concat(pat00) pat00 ,publishid from publish_att group by publishid,sort order by sort) t"
		 * + " where p.publishid = t.publishid  and p.meetingid = '"
		 * +meetingid+"' group by p.publishid, p.agendatype, p.agendaname, p.sort order by p.sort"
		 * ;
		 */
		//11.2.0.1===ok
		/*String sql = " select p.publishid, p.agendatype, p.agendaname, max(t.pat00s) pat00s,max(t.pat04s) pat04s from publish p,"
				+ "(select publishid,wm_concat(pat04) over(partition by publishid order by sort) pat04s,wm_concat(pat00) over(partition by publishid order by sort) pat00s from publish_att) t "
				+ "where p.publishid = t.publishid  and p.meetingid = '" + meetingid
				+ "' group by p.publishid, p.agendatype, p.agendaname, p.sort order by p.sort";*/
		
		/*String sql = "select p.publishid,p.agendatype,p.agendaname,t.pat00s pat00s, t.pat04s pat04s from publish p,"+
				 "(select publishid,"+
			               " LISTAGG( to_char(pat04), ',') WITHIN GROUP(ORDER BY sort) pat04s,"+
			                "LISTAGG( to_char(pat00), ',') WITHIN GROUP(ORDER BY sort) pat00s "+
			           "from publish_att group by publishid) t where p.publishid = t.publishid and p.meetingid = '"+meetingid+"' "+
			      "group by p.publishid, p.agendatype, p.agendaname, p.sort,t.pat00s,t.pat04s order by p.sort";*/
		
		String sql = "select p.publishid,p.agendatype, p.agendaname,t.pat00s pat00s,t.pat04s pat04s from publish p left join (select publishid,LISTAGG(to_char(pat04), ',') WITHIN GROUP(ORDER BY sort) pat04s,LISTAGG(to_char(pat00), ',') WITHIN GROUP(ORDER BY sort) pat00s"+
		" from publish_att  group by publishid) t on p.publishid = t.publishid where p.meetingid = '"+meetingid+"'  group by p.publishid,p.agendatype, p.agendaname,p.sort, t.pat00s,t.pat04s order by p.sort";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	@PageEvent("publishsort")
	public int publishsort() throws RadowException {
		List<HashMap<String, String>> list = this.getPageElement("publishGrid").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		Connection con = null;
		try {
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "update publish set sort = ? where publishid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			int i = 1;
			for (HashMap<String, String> m : list) {
				String publishid = m.get("publishid");
				ps.setInt(1, i);
				ps.setString(2, publishid);
				ps.addBatch();
				i++;
			}
			ps.executeBatch();
			con.commit();
			ps.close();
			con.close();
		} catch (Exception e) {
			try {
				con.rollback();
				if (con != null) {
					con.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		this.toastmessage("排序已保存！");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) throws Exception {
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
				String id = saveFile(formDataMap, fi,fileSize);
				map.put("file_pk", id);
				map.put("file_name", isfile);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return map;
	}

	@Override
	public String deleteFile(String id) {
		// TODO Auto-generated method stub
		//return null;
		try {
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> list = sess.createSQLQuery("select sh000,tp0115,tp0118 from hz_sh_a01 a where sh000='"+id+"' ").list();
			if(list==null || list.size()==0||(list.size()>0)&&list.get(0)[2]==null){
				return null;//删除失败
			}
			Object[] arr = list.get(0);
			String directory = diskx+arr[1];
			File f = new File(directory+arr[2]);
			if(f.isFile()){
				f.delete();
			}
			sess.createSQLQuery("update hz_sh_a01 a set tp0115='',tp0118='',tp0119=''  where sh000='"+id+"'  ").executeUpdate();
			sess.flush();
			return id;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// 获得人员信息id
				String sh000 = formDataMap.get("sh000");
				String publishid = formDataMap.get("publish_id");
				String titleid = formDataMap.get("title_id");
				String filename = formDataMap.get("Filename");
				//String id = UUID.randomUUID().toString();
				String id="";
				try {
					String sql="select a.* from hz_sh_a01 a where a.sh000='"+sh000+"' "
							+ " and a.publishid='"+publishid+"' and a.titleid='"+titleid+"' ";
					CommQuery cqbs=new CommQuery();
					List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
					if(list!=null&&list.size()>0){
						HashMap<String, Object> map=list.get(0);
						id=map.get("sh000").toString();
					}
					String directory ="shfiles/"+publishid+ File.separator +id+ File.separator;
					String filePath = directory  + filename;
					File f = new File(diskx + directory);
					if(!f.isDirectory()){
						f.mkdirs();
					}
					HBSession sess = null;
					sess = HBUtil.getHBSession();
					sql="update hz_sh_a01 set tp0115='"+directory+"',tp0118='"+filename+"',tp0119='"+fileSize+"' where sh000='"+sh000+"' and publishid='"+publishid+"' and titleid='"+titleid+"' ";
					System.out.println(sql);
					Statement stmt = sess.connection().createStatement();
					stmt.executeUpdate(sql);
					stmt.close();
					fi.write(new File(diskx + filePath));
					String path=diskx + filePath;
					path=path.replaceAll("\\\\", "/");;
					this.getPageElement("personPath").setValue(URLEncoder.encode(URLEncoder.encode(path, "utf-8"), "utf-8"));
				}catch (Exception e) {
					throw new RadowException("上传附件失败！");
				}
				return id;
	}

	/**
	 * 获取最大的排序号
	 * 
	 * @return
	 */
	public String getMax_sort(String publishid) {
		HBSession session = HBUtil.getHBSession();
		String sort = session
				.createSQLQuery("select nvl(max(sort),0) from publish_att where publishid='" + publishid + "'")
				.uniqueResult().toString();
		return sort;
	}
	
	/**
	 * 获取人员最大的排序号
	 * @return
	 */
	public String getTitMax_sort(String titleid,String sh000){
		HBSession session = HBUtil.getHBSession();
		String sort = session.createSQLQuery("select nvl(max(sh001),0) from (select sh001,sh000 from hz_sh_a01 where titleid='"+titleid+"' union select b.sh001,b.sh000 from personcite b where  b.titleid_new='"+titleid+"') where sh000<>'"+sh000+"'" ).uniqueResult().toString();
		return sort;
	}

	public static String disk = JSGLBS.HZBPATH;
	
	@PageEvent("titleDelete")
	@Transaction
	public int titleDelete() throws RadowException {
		String title_id = this.getPageElement("title_id").getValue();
		String titlename = this.getPageElement("titlename").getValue();
		if (title_id == null || "".equals(title_id)) {
			this.setMainMessage("请选择标题！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		CommQuery cqbs=new CommQuery();
		String sql="select 1 from hz_sh_title where title04='"+title_id+"' ";
		try {
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			if(list!=null&&list.size()>0) {
				this.setMainMessage("请先删除该标题下面的标题！");
			}else {
				HBSession session = HBUtil.getHBSession();
				session.createSQLQuery("delete from hz_sh_title where titleid = '" + title_id + "'").executeUpdate();
				session.createSQLQuery("delete from hz_sh_a01 where titleid = '" + title_id + "'").executeUpdate();
				session.createSQLQuery("delete from personcite where titleid_new = '" + title_id + "'").executeUpdate();
				this.setMainMessage("删除成功！");
				UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
				new LogUtil(user).createLogNew(user.getId(),"删除标题","hz_sh_title",user.getId(),titlename, new ArrayList());
				this.getExecuteSG().addExecuteCode("reloadSelData();");
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("confirmDelete")
	@Transaction
	public int confirmDeleteYT() throws RadowException {
		String publishid = this.getPageElement("publish_id").getValue();
		if (publishid == null || "".equals(publishid)) {
			this.setMainMessage("请选择议题！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		CommQuery cqbs=new CommQuery();
		String sql="select 1 from hz_sh_title where publishid='"+publishid+"' ";
		try {
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			if(list!=null&&list.size()>0) {
				this.setMainMessage("请先删除该议题下面的标题！");
			}else {
				HBSession session = HBUtil.getHBSession();
				List<String> address = session
						.createSQLQuery(
								"select pat07 from publish_att where publishid = '" + publishid + "' order by sort desc")
						.list();
				session.createSQLQuery("delete from publish where publishid = '" + publishid + "'").executeUpdate();
				session.createSQLQuery("delete from publish_att where publishid = '" + publishid + "'").executeUpdate();
				if (address.size()!= 0) {
					String directory = disk + address.get(0).toString();
					directory = directory.substring(0, directory.length() - 1);
					deleteDirectory(directory);
				}
				this.setMainMessage("删除成功！");
				UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
				String publishname = this.getPageElement("publishname").getValue();
				new LogUtil(user).createLogNew(user.getId(),"删除议题","publish",user.getId(),publishname, new ArrayList());
				this.getExecuteSG().addExecuteCode("reloadSelData();");
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("infoDelete")
	@Transaction
	public int delete() throws RadowException {
		String publishid = this.getPageElement("publish_id").getValue();
		if (publishid == null || "".equals(publishid)) {
			this.setMainMessage("请选择一行数据！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession session = HBUtil.getHBSession();
		List<String> address = session
				.createSQLQuery(
						"select pat07 from publish_att where publishid = '" + publishid + "' order by sort desc")
				.list();
		session.createSQLQuery("delete from publish where publishid = '" + publishid + "'").executeUpdate();
		session.createSQLQuery("delete from publish_att where publishid = '" + publishid + "'").executeUpdate();
		if (address.size()!= 0) {
			String directory = disk + address.get(0).toString();
			directory = directory.substring(0, directory.length() - 1);
			deleteDirectory(directory);
		}

		this.setMainMessage("删除成功！");
		this.getExecuteSG().addExecuteCode("reloadSelData();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 删除单个文件
	 *
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile2(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 *
	 * @param dir
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("删除目录失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile2(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}

	@PageEvent("sort")
	@Transaction
	public int changeSort(String param) {
		String pat00 = param.split("@")[0];
		String type = param.split("@")[1];
		HBSession session = HBUtil.getHBSession();
		if (type.equals("1")) {// 上移
			PublishAtt pa = (PublishAtt) session.get(PublishAtt.class, pat00);
			if (pa.getSort() == 1) {
				this.setMainMessage("已在最顶端！");
				return EventRtnType.NORMAL_SUCCESS;
			} else {
				String publishid = pa.getPublishid();
				int sort_before = pa.getSort() - 1;
				session.createSQLQuery("update publish_att set sort=" + pa.getSort() + " where publishid='" + publishid
						+ "' and sort=" + sort_before).executeUpdate();
				pa.setSort(sort_before);
				session.flush();
			}
		} else {// 下移
			PublishAtt pa = (PublishAtt) session.get(PublishAtt.class, pat00);
			String max_sort = getMax_sort(pa.getPublishid());
			if (pa.getSort() == Integer.valueOf(max_sort)) {
				this.setMainMessage("已在最底端！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String publishid = pa.getPublishid();
			int sort_next = pa.getSort() + 1;
			session.createSQLQuery("update publish_att set sort=" + pa.getSort() + " where publishid='" + publishid
					+ "' and sort=" + sort_next).executeUpdate();
			pa.setSort(sort_next);
			session.flush();
		}
		this.setNextEventName("publishGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	public static void downFile(HttpServletRequest request, HttpServletResponse response) {
		HBSession session = HBUtil.getHBSession();
		String pat00 = request.getParameter("pat00");
		PublishAtt pa = (PublishAtt) session.get(PublishAtt.class, pat00);
		String filename = pa.getPat04();
		String path = disk+pa.getPat07()+pa.getPat00();

		try {
			response.reset();
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(filename.getBytes("GBK"), "ISO8859-1"));
			// 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            /*创建输出流*/
            ServletOutputStream servletOS = response.getOutputStream();
            servletOS.write(buffer);
            servletOS.flush();
            servletOS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@PageEvent("deleteAtt")
	@Transaction
	public int deleteAtt(String pat00){
		HBSession session = HBUtil.getHBSession();
		PublishAtt pa = (PublishAtt) session.get(PublishAtt.class, pat00);
		session.createSQLQuery("delete from publish_att where pat00='"+pat00+"'").executeUpdate();
		//删除文件
		String path = disk+pa.getPat07()+pa.getPat00();
		deleteFile2(path);
		//this.setNextEventName("publishGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//公示
	@PageEvent("translation")
	@Transaction
	public int translation() throws RadowException{
		String publish_id= this.getPageElement("publish_id").getValue();
		String a0101="";
		HBSession sess=HBUtil.getHBSession();
		String filePath = (String) sess.createSQLQuery("select AAA005 from AA01 where AAA001 = 'PHOTO_PATH'").uniqueResult();
		String SOURCEFILEPATH = filePath;
		String path = getPath();
		String zippath = path + DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss") + "/";
		File file = new File(zippath);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		String photopath = zippath + "Photos/";
		File file_p =new File(photopath);   
		if (!file_p.exists() && !file_p.isDirectory()) {
			file_p.mkdirs();
		}
		CommQuery commQuery =new CommQuery();
		String zipfile = "";
		zipfile = "导出数据包_"+ DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss")+".zip";
		List<A57> photolist = sess.createSQLQuery("select * from A57 where a0000 in (select  a0000 from hz_sh_a01 where publishid='"+publish_id+"' or sh000 in (select sh000 from personcite where publishid_new='"+publish_id+"')) order by a0000 ").addEntity(A57.class).list();
		if(photolist != null && photolist.size()>0){
			for (int i = 0; i < photolist.size(); i++) {
				A57 a57 = photolist.get(i);
				if(a57.getA0000()!=null && !a57.getA0000().equals("") && a57.getPhotoname()!=null&& a57.getPhotoname()!=""&& a57.getPhotopath()!=null&& a57.getPhotopath()!=""){
					String sql_a0101="select a0101 from a01 where a0000='"+a57.getA0000()+"' ";
					try {
						a0101=commQuery.getListBySQL(sql_a0101).get(0).get("a0101").toString();
					} catch (AppException e1) {
						e1.printStackTrace();
					}
					File sourcefile = new File(SOURCEFILEPATH+"\\"+a57.getPhotopath()+a57.getPhotoname());
					File targetFile = new File(photopath +"{"+(i+1)+"_"+a0101+"}." + "jpg");
					if (sourcefile.exists() && sourcefile.isFile()) {
						try {
							UploadHelpFileServlet.copyFile(sourcefile, targetFile);
						} catch (IOException e) {
							
							e.printStackTrace();
						}
						
					}
				}
			}
			
		}
		String name = zippath+"个人简介_"+DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss")+".doc";
		try {
			String sql="select a0000,a0192a,a0102,"
					+ "case when qrzxldj>zzxldj then '在职'||zzxl else qrzxl end zgxl,"
					+ "case when qrzxldj>zzxldj then zzxldj else qrzxldj end zgxldj,"
					+ "case when qrzxwdj>zzxwdj then '在职'||zzxw else qrzxw end zgxw,"
					+ "case when qrzxwdj>zzxwdj then zzxwdj else qrzxwdj end zgxwdj from "
					+ "(select a.a0000,a.a0192a,"
					+ "a0101||'，'||decode(a0104,'1','男','2','女')||'，'||(select c.code_name from code_value c where code_type='GB3304' and c.code_value=a.a0117)"
					+ "||'，'||substr(a0107,1,4)||'年'||to_number(substr(a0107,5,2))||'月生，'||a0111a||'人，'||decode(a0141,'01',substr(a0144,1,4)||'年'||to_number(substr(a0144,5,2))||'月加入中国共产党','02',substr(a0144,1,4)||'年'||to_number(substr(a0144,5,2))||'月成为预备党员',a0140)"
					+ "||decode(a0140,'','','，')||substr(a0134,1,4)||'年'||to_number(substr(a0134,5,2))||'月参加工作，' a0102,"
					+ "case when qrzxl like '%研究生%' or qrzxl like '%硕士%' then '研究生' when qrzxl like '%大学%' or qrzxl like '%学士%'   then '大学' when qrzxl like '%大专%'  then '大专' when qrzxl='中专' then '中专'  when qrzxl='职业高中' then '职高' when qrzxl='技工学校' then '技校' else qrzxl end  qrzxl, "
					+ "case when qrzxw like '%博士%'  then '博士' when qrzxw like '%硕士%'  then '硕士' when qrzxw like '%学士%'  then '学士' else '' end  qrzxw, "
					+ "case when qrzxl like '%研究生%' or qrzxl like '%硕士%' then '2' when qrzxl like '%大学%' or qrzxl like '%学士%' then '3' when qrzxl like '%大专%'  then '4' when qrzxl='中专' then '5'  when qrzxl='职业高中' then '6' when qrzxl='技工学校' then '7' else '8' end  qrzxldj, "
					+ "case when qrzxw like '%博士%'  or qrzxw like '%硕士%'  then '2' when qrzxw like '%学士%'  then '3' else '9' end  qrzxwdj,"
					
					+ "case when zzxl like '%研究生%' or zzxl like '%硕士%' then '研究生' when zzxl like '%大学%' or zzxl like '%学士%'   then '大学' when zzxl like '%大专%'  then '大专' when zzxl='中专' then '中专'  when zzxl='职业高中' then '职高' when zzxl='技工学校' then '技校' else zzxl end  zzxl, "
					+ "case when zzxw like '%博士%'  then '博士' when zzxw like '%硕士%'  then '硕士' when zzxw like '%学士%'  then '学士' else '' end  zzxw, "
					+ "case when zzxl like '%研究生%' or zzxl like '%硕士%' then '2' when zzxl like '%大学%' or zzxl like '%学士%' then '3' when zzxl like '%大专%'  then '4' when zzxl='中专' then '5'  when zzxl='职业高中' then '6' when zzxl='技工学校' then '7' else '8' end  zzxldj, "
					+ "case when zzxw like '%博士%'  or zzxw like '%硕士%'  then '2' when zzxw like '%学士%'  then '3' else '9' end  zzxwdj"
					+ "    from (select a.a0000,a.a0101,a.a0104,b.a0117,a.a0107,b.a0111a,b.a0140,b.a0141,b.a0144,b.a0134,b.qrzxl,b.qrzxw,b.zzxl,b.zzxw,a.a0192a from hz_sh_a01 a,a01 b where a.a0000=b.a0000 and  publishid='"+publish_id+"' or sh000 in (select sh000 from personcite where publishid_new='"+publish_id+"')) a where deocde(a0000,null,'111')<>'111') a  order by a0000";
			List<HashMap<String, Object>>  list = commQuery.getListBySQL(sql); 

			FileOutputStream out = new FileOutputStream(name);
			PrintStream ps = new PrintStream(out);
			ps.print("");
			String str="";
			if(list!=null&&list.size()>0) {
				for(HashMap<String, Object> map:list) {
					str="    "+map.get("a0102").toString()+map.get("zgxl").toString()+"学历";
					if(Integer.valueOf(map.get("zgxldj").toString())>Integer.valueOf(map.get("zgxwdj").toString())) {
						str=str+"，"+map.get("zgxw").toString()+"学位";
					}
					str=str+"。现任"+map.get("a0192a").toString();
					ps.append(str);
					ps.append("\r\r");
				}
			}
			ps.close();
			out.close();
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		Zip7z.zip7Z(zippath, zipfile, null);
		this.delFolder(zippath);
		try {
			this.getPageElement("downfile").setValue(zipfile.replace("\\", "/"));
		} catch (RadowException e) {

			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private String getPath() {
		String upload_file = AppConfig.HZB_PATH + "/temp/zipload/";
		try {
			File file =new File(upload_file);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		//解压路径
		String zip = upload_file + UUID.randomUUID().toString().replaceAll("-", "") + "/";
		return zip;
	}
	private static String getPathx() {
        String upload_file = AppConfig.HZB_PATH + "/";
        try {
            File file = new File(upload_file);
            //如果文件夹不存在则创建
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //解压路径
        return upload_file;
    }
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	
	@PageEvent("rolesort")
	  @Transaction
	  public int rolesort() throws RadowException {
	    List<HashMap<String, String>> list = this.getPageElement("peopleGrid").getStringValueList();
	    String titleid=this.getPageElement("title_id").getValue();
	    HBSession sess = HBUtil.getHBSession();
	    Connection con = null;
	    try {
	      con = sess.connection();
	      con.setAutoCommit(false);
	      String sql = "update hz_sh_a01 set sh001 = ? where sh000=? and titleid=? ";
	      String sql2= "update personcite set sh001 = ? where sh000=? and titleid_new=? ";
	      PreparedStatement ps = con.prepareStatement(sql);
	      PreparedStatement ps2 = con.prepareStatement(sql2);
	      int i = 1;
	      String sql_cnt="";
	      CommQuery commQuery =new CommQuery();
	      for (HashMap<String, String> m : list) {
	        String sh000 = m.get("sh000");
	        sql_cnt="select sh000 from hz_sh_a01 where sh000='"+sh000+"' and titleid='"+titleid+"'";
	        List<HashMap<String, Object>>  list_cnt = commQuery.getListBySQL(sql_cnt); 
	        if(list_cnt!=null&&list_cnt.size()==1) {
		        ps.setInt(1, i);
		        ps.setString(2, sh000);
		        ps.setString(3, titleid);
		        ps.addBatch();
	        }else {
		        ps2.setInt(1, i);
		        ps2.setString(2, sh000);
		        ps2.setString(3, titleid);
		        ps2.addBatch();
	        }
	        i++;
	      }
	      ps.executeBatch();
	      ps2.executeBatch();
	      con.commit();
	      ps.close();
	      ps2.close();
	      con.close();
	    } catch (Exception e) {
	      try {
	        con.rollback();
	        if (con != null) {
	          con.close();
	        }
	      } catch (SQLException e1) {
	        e1.printStackTrace();
	      }
	      e.printStackTrace();
	      this.setMainMessage("排序失败！");
	      return EventRtnType.FAILD;
	    }
	    this.toastmessage("排序已保存！");
	    this.setNextEventName("peopleGrid.dogridquery");
	    return EventRtnType.NORMAL_SUCCESS;
	  }
	
	@PageEvent("queryPersonx")
	public int queryPersonx(String sh000) throws RadowException {
			String sql="select a.* from hz_sh_a01 a where a.sh000='"+sh000+"'  ";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list;
		try {
			list = cqbs.getListBySQL(sql);
			String path="";
			if(list!=null&&list.size()>0){
				HashMap<String, Object> map=list.get(0);
				this.getPageElement("a0101").setValue(map.get("a0101")==null?"":map.get("a0101").toString());
				this.getPageElement("a0104").setValue(map.get("a0104")==null?"":map.get("a0104").toString());
				String a0107=map.get("a0107")==null?"":map.get("a0107").toString();
				this.getPageElement("a0107").setValue(a0107);
				this.getPageElement("a0141").setValue(map.get("a0141")==null?"":map.get("a0141").toString());
				this.getPageElement("zgxl").setValue(map.get("zgxl")==null?"":map.get("zgxl").toString());
				this.getPageElement("zgxw").setValue(map.get("zgxw")==null?"":map.get("zgxw").toString());
				this.getPageElement("a0192a").setValue(map.get("a0192a")==null?"":map.get("a0192a").toString());
				this.getPageElement("tp0111").setValue(map.get("tp0111")==null?"":map.get("tp0111").toString());
				this.getPageElement("tp0112").setValue(map.get("tp0112")==null?"":map.get("tp0112").toString());
				this.getPageElement("tp0113").setValue(map.get("tp0113")==null?"":map.get("tp0113").toString());
				this.getPageElement("tp0114").setValue(map.get("tp0114")==null?"":map.get("tp0114").toString());
				this.getPageElement("tp0116").setValue(map.get("tp0116")==null?"1":map.get("tp0116").toString());
				this.getPageElement("tp0117").setValue(map.get("tp0117")==null?"1":map.get("tp0117").toString());
				this.getPageElement("tp0121").setValue(map.get("tp0121")==null?"":map.get("tp0121").toString());
				this.getPageElement("tp0122").setValue(map.get("tp0122")==null?"":map.get("tp0122").toString());
				this.getPageElement("tp0123").setValue(map.get("tp0123")==null?"":map.get("tp0123").toString());
				this.getPageElement("tp0124").setValue(map.get("tp0124")==null?"":map.get("tp0124").toString());
				this.getPageElement("tp0125").setValue(map.get("tp0125")==null?"":map.get("tp0125").toString());
				this.getPageElement("sh001").setValue(map.get("sh001")==null?"":map.get("sh001").toString());
				/*if(map.get("tp0118")!=null) {
					path=diskx +map.get("tp0115").toString()+map.get("tp0118").toString();
					path=path.replaceAll("\\\\", "/");
				}
				this.getPageElement("personPath").setValue(URLEncoder.encode(URLEncoder.encode(path, "utf-8"), "utf-8"));
				
				Map<String, List<Map<String, String>>> mapfile = new HashMap<String, List<Map<String, String>>>();
				mapfile.put("personfile", new ArrayList<Map<String, String>>());
				
				//遍历map
				for(Map.Entry<String, List<Map<String, String>>> entry : mapfile.entrySet()){
					this.setFilesInfo(entry.getKey(), entry.getValue(), false);
				}
				
				if (list != null&&list.get(0).get("tp0118")!=null) {
					for (HashMap<String, Object> ta : list) {
						List<Map<String, String>> list_temp;
						Map<String, String> map_temp = new HashMap<String, String>();
						String type ="personfile";
						if(mapfile.containsKey(type)){
							list_temp = mapfile.get(type);
						}else{
							list_temp = new ArrayList<Map<String, String>>();
						}
						map_temp.put("id", ta.get("sh000")+"");
						map_temp.put("name", ta.get("tp0118")+"");
						map_temp.put("fileSize", ta.get("tp0119")+"");
						list_temp.add(map_temp);
						mapfile.put(type, list_temp);
					}
					//遍历map
					for(Map.Entry<String, List<Map<String, String>>> entry : mapfile.entrySet()){
						this.setFilesInfo(entry.getKey(), entry.getValue(), false);
					}
				}
			}else {
				this.getPageElement("tp0117").setValue("0");
			}*/
			
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//附件信息
		
		 // CadresPlanningAllocationAppendixPageModel fujianObj = new  CadresPlanningAllocationAppendixPageModel(); 
		 // String fujianInfo;
		/*try {
			fujianInfo = fujianObj.fujianList( sh000 );
			//this.getExecuteSG().addExecuteCode("colseWin( '"+fujianInfo+"')");
			this.getExecuteSG().addExecuteCode("cin( '"+fujianInfo+"')");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		this.getExecuteSG().addExecuteCode("gg();");
		//this.setNextEventName("galeGrid.dogridquery");  
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("fileGrid.dogridquery")
	public int galeGridQuery(int start, int limit) throws RadowException {
		String sh000 = this.getPageElement("sh000").getValue();
		String sql = " select p.add00,p.filename,p.filesize,t.aaa005||'/'||p.fileurl as fileurl from WJGLADD p , aa01 t where  p.add00 = '" + sh000 + "' and t.aaa001 = 'HZB_PATH'";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	public final static String diskx = getPathx();
	@PageEvent("personSave")
	public int personSave() throws RadowException, AppException {
		String publishid=this.getPageElement("publish_id").getValue();
		String titleid=this.getPageElement("title_id").getValue();
		String titlename = this.getPageElement("titlename").getValue();
		String sh000=this.getPageElement("sh000").getValue();
		String tp0111=this.getPageElement("tp0111").getValue();
		String tp0112=this.getPageElement("tp0112").getValue();
		String tp0113=this.getPageElement("tp0113").getValue();
		String tp0114=this.getPageElement("tp0114").getValue();
		String tp0116=this.getPageElement("tp0116").getValue();
		String tp0117=this.getPageElement("tp0117").getValue();
		String tp0121=this.getPageElement("tp0121").getValue();
		String tp0122=this.getPageElement("tp0122").getValue();
		String tp0123=this.getPageElement("tp0123").getValue();
		String tp0124=this.getPageElement("tp0124").getValue();
		String tp0125=this.getPageElement("tp0125").getValue();
		String a0101=this.getPageElement("a0101").getValue();
		String a0104=this.getPageElement("a0104").getValue();
		String a0107=this.getPageElement("a0107").getValue();
		String a0141=this.getPageElement("a0141").getValue();
		String zgxl=this.getPageElement("zgxl").getValue();
		String zgxw=this.getPageElement("zgxw").getValue();
		String a0192a=this.getPageElement("a0192a").getValue();
		String sh001=this.getPageElement("sh001").getValue();
		
		int maxid=Integer.valueOf(getTitMax_sort(titleid,sh000));
		HBSession sess = HBUtil.getHBSession();
		
		
		
		try {
		if(sh000!=null||!"".equals(sh000)) {	
		String sqlg="select a.* from WJGLADD a where a.add00='"+sh000+"'  ";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sqlg);
		String path="";
		String logname="";
		if(list!=null&&list.size()>0) {
			HashMap<String, Object> map=list.get(0);
			String tp0115 = map.get("fileurl")==null?"":map.get("fileurl").toString();
			String tp0118 = map.get("filename")==null?"":map.get("filename").toString();
			String tp0119 = map.get("filesize")==null?"":map.get("filesize").toString();
		
			Statement stmt = sess.connection().createStatement();
			String sql="";
			
			if(sh001==null||"".equals(sh001)) {
				sh001=maxid+1+"";
			}else {
				List list_sort = sess.createSQLQuery("select * from hz_sh_a01 where sh000<>'"+sh000+"' and sh001='"+sh001+"' and titleid='"+titleid+"'"  ).list();
				List list_sort2 = sess.createSQLQuery("select * from personcite where sh000<>'"+sh000+"' and sh001='"+sh001+"' and titleid_new='"+titleid+"'"  ).list();
				if(list_sort.size()!=0||list_sort2.size()!=0){
					sql="update hz_sh_a01 set sh001=sh001+1 where to_number(sh001)>="+sh001+" and titleid='"+titleid+"' ";
					sess.createSQLQuery(sql).executeUpdate();
					
					sql="update personcite set sh001=sh001+1 where to_number(sh001)>="+sh001+" and titleid_new='"+titleid+"' ";
					sess.createSQLQuery(sql).executeUpdate();
				}
			}
			
			//手工新增录入
			if(sh000==null||"".equals(sh000)) {
				sh000=UUID.randomUUID().toString();
				this.getPageElement("sh000").setValue(sh000);
				sql="insert into hz_sh_a01 (sh000,publishid,titleid,tp0111,tp0112,tp0113,tp0114,tp0116,tp0117,tp0121,tp0122,tp0123,tp0124,tp0125"
						+ ",a0101,a0104,a0107,zgxl,zgxw,a0141,a0192a,sh001,tp0118,tp0119,tp0115) "
						+ "	  values "
						+ " ('"+sh000+"','"+publishid+"','"+titleid+"','"+tp0111+"','"+tp0112+"','"+tp0113+"','"+tp0114+"','"+tp0116+"'"
						+ "		,'"+tp0117+"','"+tp0121+"','"+tp0122+"','"+tp0123+"','"+tp0124+"','"+tp0125+"','"+a0101+"','"+a0104+"','"+a0107+"','"+a0141+"','"+zgxl+"','"+zgxw+"','"+a0192a+"','"+sh001+"','"+tp0118+"','"+tp0119+"','"+tp0115+"')";
				logname="新增上会人员（手工录入）";
			}else {//修改信息
				sql="update hz_sh_a01 set tp0111='"+tp0111+"',tp0112='"+tp0112+"',tp0113='"+tp0113+"',tp0114='"+tp0114+"'"
					+ ",tp0116='"+tp0116+"',tp0117='"+tp0117+"',tp0121='"+tp0121+"',tp0122='"+tp0122+"',tp0123='"+tp0123+"',tp0124='"+tp0124+"',tp0125='"+tp0125+"',a0101='"+a0101+"',a0104='"+a0104+"'"
					+ ",a0107='"+a0107+"',a0141='"+a0141+"',zgxl='"+zgxl+"',zgxw='"+zgxw+"',a0192a='"+a0192a+"',sh001='"+sh001+"' ,tp0118='"+tp0118+"' ,tp0119='"+tp0119+"' ,tp0115='"+tp0115+"' "
					+ "		where sh000='"+sh000+"' and publishid='"+publishid+"' and titleid='"+titleid+"' ";
				logname="修改上会人员";
			}
			sess.createSQLQuery(sql).executeUpdate();
			
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			new LogUtil(user).createLogNew(user.getId(),logname,"hz_sh_a01",user.getId(),titlename, new ArrayList());
	}else {
			Statement stmt = sess.connection().createStatement();
			String sql="";
			
			if(sh001==null||"".equals(sh001)) {
				sh001=maxid+1+"";
			}else {
				List list_sort = sess.createSQLQuery("select * from hz_sh_a01 where sh000<>'"+sh000+"' and sh001='"+sh001+"' and titleid='"+titleid+"'"  ).list();
				List list_sort2 = sess.createSQLQuery("select * from personcite where sh000<>'"+sh000+"' and sh001='"+sh001+"' and titleid_new='"+titleid+"'"  ).list();
				if(list_sort.size()!=0||list_sort2.size()!=0){
					sql="update hz_sh_a01 set sh001=sh001+1 where to_number(sh001)>="+sh001+" and titleid='"+titleid+"' ";
					sess.createSQLQuery(sql).executeUpdate();
					
					sql="update personcite set sh001=sh001+1 where to_number(sh001)>="+sh001+" and titleid_new='"+titleid+"' ";
					sess.createSQLQuery(sql).executeUpdate();
				}
			}
			
			//手工新增录入
			if(sh000==null||"".equals(sh000)) {
				sh000=UUID.randomUUID().toString();
				this.getPageElement("sh000").setValue(sh000);
				sql="insert into hz_sh_a01 (sh000,publishid,titleid,tp0111,tp0112,tp0113,tp0114,tp0116,tp0117,tp0121,tp0122,tp0123,tp0124,tp0125"
						+ ",a0101,a0104,a0107,zgxl,zgxw,a0141,a0192a,sh001) "
						+ "	  values "
						+ " ('"+sh000+"','"+publishid+"','"+titleid+"','"+tp0111+"','"+tp0112+"','"+tp0113+"','"+tp0114+"','"+tp0116+"'"
						+ "		,'"+tp0117+"','"+tp0121+"','"+tp0122+"','"+tp0123+"','"+tp0124+"','"+tp0125+"','"+a0101+"','"+a0104+"','"+a0107+"','"+a0141+"','"+zgxl+"','"+zgxw+"','"+a0192a+"','"+sh001+"')";
				logname="新增上会人员（手工录入）";
			}else {//修改信息
				sql="update hz_sh_a01 set tp0111='"+tp0111+"',tp0112='"+tp0112+"',tp0113='"+tp0113+"',tp0114='"+tp0114+"'"
					+ ",tp0116='"+tp0116+"',tp0117='"+tp0117+"',tp0121='"+tp0121+"',tp0122='"+tp0122+"',tp0123='"+tp0123+"',tp0124='"+tp0124+"',tp0125='"+tp0125+"',a0101='"+a0101+"',a0104='"+a0104+"'"
					+ ",a0107='"+a0107+"',a0141='"+a0141+"',zgxl='"+zgxl+"',zgxw='"+zgxw+"',a0192a='"+a0192a+"',sh001='"+sh001+"'  "
					+ "		where sh000='"+sh000+"' and publishid='"+publishid+"' and titleid='"+titleid+"' ";
				logname="修改上会人员";
			}
			sess.createSQLQuery(sql).executeUpdate();
			
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			new LogUtil(user).createLogNew(user.getId(),logname,"hz_sh_a01",user.getId(),titlename, new ArrayList());
	}
		}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.upLoadFile("personfile");
		
		
		//String fujianInfo = fujianList( sh000 );
	    //this.getExecuteSG().addExecuteCode("setHtml('div_fujian' , '"+fujianInfo+"' )");
		this.getExecuteSG().addExecuteCode("alert('保存成功');queryPerson();");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	// 获取附件信息
		public String fujianList(String sh000 ) throws AppException {
			String Info = "";
			String sql = "select filename,fileurl from WJGLADD t where add00 ='"+sh000+"'";
			List<HashMap<String, Object>> list = cqbs.getListBySQL(sql);
			int i =0;
			if(list.size()>0)
			{
			   for(HashMap<String, Object> map : list){
				   i++;
				   String filename = map.get("filename").toString();//附件ID
				   String fileurl = map.get("fileurl").toString();//附件名称
				   fileurl ="javascript:downloadFile(\"" + fileurl.replace(System.getProperty("file.separator"), "/") + "\")";
				   fileurl = String.valueOf(i)+"、<a href="+ fileurl +">"+ filename + "</a><br>";
				   Info += fileurl;
			   }
			}
			return Info;
		}
	
	private String saveFilex(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// 获得人员信息id
		String sh000 = formDataMap.get("sh000");
		String publishid = formDataMap.get("publish_id");
		String titleid = formDataMap.get("title_id");
		String filename = formDataMap.get("Filename");
		//String id = UUID.randomUUID().toString();
		String id="";
		try {
			String sql="select a.* from hz_sh_a01 a where a.sh000='"+sh000+"' "
					+ " and a.publishid='"+publishid+"' and a.titleid='"+titleid+"' ";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				HashMap<String, Object> map=list.get(0);
				id=map.get("sh000").toString();
			}
			String directory ="shfiles/"+publishid+ File.separator +id+ File.separator;
			String filePath = directory  + filename;
			File f = new File(diskx + directory);
			if(!f.isDirectory()){
				f.mkdirs();
			}
			HBSession sess = null;
			sess = HBUtil.getHBSession();
			sql="update hz_sh_a01 set tp0115='"+directory+"',tp0118='"+filename+"',tp0119='"+fileSize+"' where sh000='"+sh000+"' and publishid='"+publishid+"' and titleid='"+titleid+"' ";
			System.out.println(sql);
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			fi.write(new File(diskx + filePath));
			String path=diskx + filePath;
			path=path.replaceAll("\\\\", "/");;
			this.getPageElement("personPath").setValue(URLEncoder.encode(URLEncoder.encode(path, "utf-8"), "utf-8"));
		}catch (Exception e) {
			throw new RadowException("上传附件失败！");
		}
		return id;
	}
	
	
	public Map<String, String> getFilesx(List<FileItem> fileItem, Map<String, String> formDataMap) throws Exception {
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
				String id = saveFilex(formDataMap, fi,fileSize);
				map.put("file_pk", id);
				map.put("file_name", isfile);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return map;
	}
	
	@PageEvent("personClear")
	public int personClear() throws RadowException {
		this.getPageElement("sh000").setValue("");
		this.getPageElement("tp0111").setValue("");
		this.getPageElement("tp0112").setValue("");
		this.getPageElement("tp0113").setValue("");
		this.getPageElement("tp0114").setValue("");
		this.getPageElement("tp0116").setValue("");
		this.getPageElement("tp0117").setValue("");
		this.getPageElement("tp0121").setValue("");
		this.getPageElement("tp0122").setValue("");
		this.getPageElement("tp0123").setValue("");
		this.getPageElement("tp0124").setValue("");
		this.getPageElement("tp0125").setValue("");
		this.getPageElement("a0101").setValue("");
		this.getPageElement("a0104").setValue("");
		this.getPageElement("a0107").setValue("");
		this.getPageElement("a0141").setValue("");
		this.getPageElement("zgxl").setValue("");
		this.getPageElement("zgxw").setValue("");
		this.getPageElement("a0192a").setValue("");
		this.getPageElement("sh001").setValue("");
		
		Map<String, List<Map<String, String>>> mapfile = new HashMap<String, List<Map<String, String>>>();
		mapfile.put("personfile", new ArrayList<Map<String, String>>());
		//遍历map
		for(Map.Entry<String, List<Map<String, String>>> entry : mapfile.entrySet()){
			this.setFilesInfo(entry.getKey(), entry.getValue(), false);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("createRmbs")
	public int createRmbs(String str)  {
		String[] arr=str.split("@@");
		String str1=arr[0];
		String str2=arr[1];
		CommQuery cqbs=new CommQuery();
		String sql="";
		if("sh000".equals(str1)) {
			sql="select a.a0000,a.a0101,b.sh000 from a01 a,hz_sh_a01 b where a.a0000=b.a0000 and b.sh000='"+str2+"' ";
		}else if("title_id".equals(str1)) {
			sql="select a.a0000,a.a0101,b.sh000 from a01 a,hz_sh_a01 b where a.a0000=b.a0000 and titleid ='"+str2+"' or titleid in (select titleid  from hz_sh_title where title04 ='"+str2+"')"
					+ " or titleid in (select titleid  from hz_sh_title where title04 in (select titleid  from hz_sh_title where title04 ='"+str2+"'))";
		}else if("publish_id".equals(str1)) {
			sql="select a.a0000,a.a0101,b.sh000 from a01 a,hz_sh_a01 b where a.a0000=b.a0000 and b.publishid='"+str2+"' ";
		}
		List<HashMap<String, Object>> list;
		try {
			list = cqbs.getListBySQL(sql);
		
			String a0000="";
			String sh000="";
			if(list!=null&&list.size()>0){
				List<String> list2 = new ArrayList<String>();
				HashMap<String, String> map_sh000=new HashMap<String, String>();
				for (HashMap<String, Object> map : list) {
					a0000=map.get("a0000").toString();
					sh000=map.get("sh000").toString();
					list2.add(a0000);
					map_sh000.put(a0000, sh000);
				}
				List<String> result = new ArrayList<String>();
				String[] sub = null;
				List<String> wordPaths = null;
				wordPaths = getPdfsByA000s_aspose(list2,"eebdefc2-4d67-4452-a973-5f7939530a11","word",result,sub,map_sh000);
				String zipPath=wordPaths.get(0);
				String pngPath=getClass().getResource("/").getFile().toString().replace("WEB-INF/classes/", "yulan/");
							pngPath=pngPath.substring(1);
				for (HashMap<String, Object> map : list) {
					a0000=map.get("a0000").toString();
					sh000=map.get("sh000").toString();
					String pngname=sh000+"_rmb.jpg";
					
					MeetingPreviewPageModel mpp=new MeetingPreviewPageModel();
					mpp.queryImg(zipPath.substring(0,zipPath.length()-1)+"\\"+a0000+".doc",pngPath,pngPath+pngname);
				}
				this.getExecuteSG().addExecuteCode("ShowCellCover('','温馨提示','导出成功！');");
			}else {
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("expShrmb")
	public int expShrmb(String sh000) throws Exception {
		String sql="select a.a0000,a.a0101 from a01 a,hz_sh_a01 b where a.a0000=b.a0000 and b.sh000='"+sh000+"' ";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		String a0000="";
		if(list!=null&&list.size()>0){
			HashMap<String, Object> map=list.get(0);
			a0000=map.get("a0000").toString();
			List<String> list2 = new ArrayList<String>();
			list2.add(a0000);
			List<String> result = new ArrayList<String>();
			String[] sub = null;
			List<String> wordPaths = null;
			HashMap<String, String> map_sh000=new HashMap<String, String>();
			map_sh000.put(a0000, sh000);
			wordPaths = getPdfsByA000s_aspose(list2,"eebdefc2-4d67-4452-a973-5f7939530a11","word",result,sub,map_sh000);
			String zipPath=wordPaths.get(0);
			String pngPath=getClass().getResource("/").getFile().toString().replace("WEB-INF/classes/", "yulan/");
			pngPath=pngPath.substring(1);
			String pngname=sh000+"_rmb.jpg";
			MeetingPreviewPageModel mpp=new MeetingPreviewPageModel();
			mpp.queryImg(zipPath.substring(0,zipPath.length()-1)+"\\"+a0000+".doc",pngPath,pngPath+pngname);
			System.out.println(pngPath+pngname);
			this.getExecuteSG().addExecuteCode("yulan_rmb('"+sh000+"');");
		}else {
			
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getPdfsByA000s_aspose(List<String> a0000s, String tpid, String type, List<String> result,
									  String[] sub,HashMap<String, String> map_sh000) throws AppException, IOException {
		if (!ExportAsposeBS.getLicense()) { // 验证License 若不验证则生成的word文档会有水印产生
			return null;
		}
		String rootPath = ExportAsposeBS.getRootPath();
		List<String> paths = new ArrayList<String>();
		String expFile = ExpRar.expFile();
		type = gbrmAuditForm(a0000s, tpid, type, rootPath, expFile,map_sh000);
		File dec = new File(expFile);
		paths.add(expFile);

		return paths;
	}
	
	private String gbrmAuditForm(List<String> a0000s, String tpid, String type, String rootPath, String expFile,HashMap<String, String> map_sh000) {
		String doctpl;
		Map<String, Object> dataMap;
		// 干部任免审批表
		for (int i = 0; i < a0000s.size(); i++) {
			String a0000 = a0000s.get(i);
			String sh000=map_sh000.get(a0000);
			// 创建Document对象，读取Word模板
			try {
				dataMap = FiledownServlet.getMap(a0000, tpid);
				
				String sql="select tp0111,tp0112,tp0121,tp0122,a0192a from hz_sh_a01 where sh000='"+sh000+"' ";
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
				HashMap<String, Object> map=list.get(0);
				String tp0111=map.get("tp0111")==null?"":map.get("tp0111").toString();
				String tp0112=map.get("tp0112")==null?"":map.get("tp0112").toString();
				String tp0121=map.get("tp0121")==null?"":map.get("tp0121").toString();
				String tp0122=map.get("tp0122")==null?"":map.get("tp0122").toString();
				if(!"".equals(tp0111)) {
					if("2".equals(tp0121)) {
						dataMap.put("tp0111", tp0111+"（挂职）");
					}else {
						dataMap.put("tp0111", tp0111);
					}
				}else if("".equals(tp0111)&&"3".equals(tp0122)) {
					dataMap.put("tp0111", "到龄退休");
				}else {
					dataMap.put("tp0111", "");
				}
				if("2".equals(tp0122)||"3".equals(tp0122)) {
					dataMap.put("tp0112", map.get("a0192a").toString());
				}else {
					dataMap.put("tp0112", tp0112);
				}

				String a0101 = (String) dataMap.get("a0101");

				doctpl = rootPath + "gbrmbsh_1.doc";

				Document doc = new Document(doctpl);
				// 增加处理简历和照片程序
				doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
				// 向模板填充数据源
				// doc.getMailMerge().executeWithRegions(new
				// MapMailMergeDataSource(getMapList2(a0000), "Employees"));
				StringBuffer mapkey = new StringBuffer();
				StringBuffer mapvalue = new StringBuffer();
				for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					if (key.equals("image")) {
						A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
						if (a57 != null) {
							byte[] image = PhotosUtil.getPhotoData(a57);
							if (image != null) {
								value = PhotosUtil.PHOTO_PATH + a57.getPhotopath() + a57.getPhotoname();
							} else {
								value = "";
							}
						} else {
							value = "";
						}
					}
					if (StringUtil.isEmpty(value + "")) {
						value = "@";
					}
					mapkey = mapkey.append(key + "&");
					// 去掉&特殊字符
					value = value.toString().replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&nbsp;", " ")
							.replaceAll("&", "");
					mapvalue = mapvalue.append(value + "&");
				}
				// 文本域
				String[] Flds = mapkey.toString().split("&");
				// 值
				String[] Vals = mapvalue.toString().split("&");
				for (int j = 0; j < Vals.length; j++) {
					if (Vals[j].equals("@")) {
						Vals[j] = "";
					}
				}

				// 填充单个数据 //此处生成word路径 中文打印不了 转成了pinyin
				doc.getMailMerge().execute(Flds, Vals);
//				File outFile = new File(expFile + (i + 1) + "_" + getHanziInitials(a0101) + ".doc");
				File outFile = new File(expFile + a0000 + ".doc");
				// 写入到Word文档中
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				// 保存到输出流中
				doc.save(os, SaveFormat.DOC);
				OutputStream out = new FileOutputStream(outFile);
				out.write(os.toByteArray());
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return type;
	}
	
	private static class HandleMergeFieldFromBlob implements IFieldMergingCallback {
		public void fieldMerging(FieldMergingArgs args) throws Exception {
			if (args.getDocumentFieldName().equals("a1701")) {
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, true);
			} else if (args.getDocumentFieldName().equals("image")) {
				String image = args.getFieldValue().toString();
				if (!StringUtil.isEmpty(image)) {
					DocumentBuilder builder = new DocumentBuilder(args.getDocument());
					builder.moveToMergeField(args.getFieldName());
					builder.insertImage(args.getFieldValue().toString(), 105, 145);
				}
			} else if (args.getDocumentFieldName().equals("a0192a")) {
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("zzxlxx")) {// 在职学历信息
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("a14z101")) {// 奖惩情况字体缩放
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("zzxwxx")) {// 在职学位信息
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("qrzxlxx")) {// 全日制学历信息
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("qrzxwxx")) {// 全日制学位信息
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("qrzxlxw")) {
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("zzxlxw")) {
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("a0196")) {
				// 使用DocumentBuilder处理简历
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			} else if (args.getDocumentFieldName().equals("fwh")) {
				// 使用DocumentBuilder处理发文
				DocumentBuilder builder = new DocumentBuilder(args.getDocument());
				builder.moveToMergeField(args.getFieldName());
				BbUtils.formatArray(builder, args, false);
			}
		}

		public void imageFieldMerging(ImageFieldMergingArgs e) throws Exception {

		}
	}
	/**
	 * 附件上传之后保存附件信息
	 * @param params
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @throws SQLException 
	 */
	@PageEvent("file")
	public int fileGrid(String oid) throws RadowException, AppException, SQLException {
		//this.setMainMessage("附件上传成功！");
		String sql="";
	try {
		HBSession sess = HBUtil.getHBSession();
		if(oid!=null||!"".equals(oid)) {	
			String sqlg="select a.* from WJGLADD a where a.add00='"+oid+"'  ";
			List<HashMap<String, Object>> list;
				list = cqbs.getListBySQL(sqlg);
				HashMap<String, Object> map=list.get(0);
				String tp0115 = map.get("fileurl")==null?"":map.get("fileurl").toString();
				String tp0118 = map.get("filename")==null?"":map.get("filename").toString();
				String tp0119 = map.get("filesize")==null?"":map.get("filesize").toString();
			if(list.size()!=0||list.size()!=0){
				tp0115=tp0115.replaceAll("\\\\", "/");
				sql="update hz_sh_a01 set tp0115='"+tp0115+"',tp0118='"+tp0118+"',tp0119='"+tp0119+"' where sh000='"+oid+"'  ";
				//System.out.println(sql);
					//更新数据
					//st.execute(sql1);
				sess.createSQLQuery(sql).executeUpdate();
				
				}
				
		}
			} catch (Exception e) {
			} finally {
			}
			
				//this.setNextEventName("fileGrid.dogridquery");
				this.setMainMessage("附件上传成功！");
				//this.getExecuteSG().addExecuteCode("fg()");
				this.getExecuteSG().addExecuteCode("gg();");
				//this.setNextEventName("galeGrid.dogridquery");  
				//this.getExecuteSG().addExecuteCode("gg()");
				return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 删除数据并删除文件
	 * @param id
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("deleteFi")
	public int deleteFi(String index) throws RadowException, AppException {

	try {
		HBSession sess = HBUtil.getHBSession();
		Grid grid = (Grid)this.getPageElement("fileGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		int cueindex=Integer.parseInt(index);
		HashMap<String, Object> map = list.get(cueindex);
		String add00 = map.get("add00").toString();
		String filename = map.get("filename").toString();
		String allPath = getAllPath(filename);
		// 删除文件
		if (allPath != null && !"".equals(allPath)) {
			File file = new File(allPath); 
		    if(file.exists()){     //判断是否存在  
		    	file.delete();      //删除  
		    }
		}
		String sql1 = " delete from WJGLADD p where p.filename = '" + filename + "' ";
		String sql2 = " update hz_sh_a01 set tp0115='',tp0118='',tp0119='' where sh000='"+add00+"'    ";
		//this.setMainMessage("附件删除成功！");
		//更新数据
		//st.execute(sql1);
		sess.createSQLQuery(sql1).executeUpdate();
		sess.createSQLQuery(sql2).executeUpdate();
		sess.flush();
		
	} catch (Exception e) {
	} finally {
	}
		this.setMainMessage("附件删除成功！");
		//this.setNextEventName("galeGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("gg()");
		//String fujianInfo = fujianList( pid );
		//this.getExecuteSG().addExecuteCode("colseWin( '"+fujianInfo+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 获取文件全路径
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public String getAllPath(String filename) throws AppException {
		String allPath = "";
		// 数据是否存在
		// String sql = " select p.pid, p.p3002, p.p3001 from P30 p where p.pid = '" + pid + "' ";
		String sql = " select p.add00, p.fileurl, p.filename from WJGLADD p where p.filename = '" + filename + "' ";
		CommQuery cq = new CommQuery();
		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		if (list == null || list.size() == 0) {
			return allPath;
		}
		
		String filepath = list.get(0).get("fileurl") == null ? "" : list.get(0).get("fileurl").toString();
		//String filename = list.get(0).get("filename") == null ? "" : list.get(0).get("filename").toString();
		// 删除文件
		if (!"".equals(filepath) ) {
			allPath =  filepath;
			
		}
		return allPath;
	}
	/**
	 * 私有方法，是否选中用户
	 *
	 * @throws RadowException
	 */
	private int choose(String gridid,String checked) throws RadowException {
		int result = 1;
		int number = 0;
		PageElement pe = this.getPageElement(gridid);
		List<HashMap<String, Object>> list = pe.getValueList();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			Object check1 = map.get(checked);
			if(check1==null){
				continue;
			}
			if (check1.equals(true)) {
				number = i;
				result++;
			}
		}
		if (result == 1) {
			return ON_ONE_CHOOSE;// 没有选中任何用户
		}
		if (result > 2) {
			return CHOOSE_OVER_TOW;// 选中多于一个用户
		}
		if (result == 2) {
			return 1;// 选中多于一个用户
		}
		return number;// 选中第几个用户
	}
	
}
