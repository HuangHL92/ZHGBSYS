package com.insigma.siis.local.pagemodel.fxyp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.fxyp.base.CommonSelSQL;

public class KqzsdwPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("noticeSetgrid.dogridquery");
		return 0;
	}

	
	
	/**
	 *  查询职数信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("noticeSetgrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		CommonSelSQL comsql = new CommonSelSQL(this);
		String sql = comsql.getNoticeSetgrid();
		this.pageQuery(sql, "SQL", start, 300);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 修改人员信息的双击事件
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("noticeSetgrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		//获得当前页面是浏览  还是  编辑  机构树
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();
		String mntp00 = this.getPageElement("mntp00").getValue();
		String b01id=this.getPageElement("noticeSetgrid").getValue("b01id",this.getPageElement("noticeSetgrid").getCueRowIndex()).toString();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	@PageEvent("addInfo")
	@Transaction
	public int addInfo(String txt) throws RadowException, AppException{
		
		try {
			String[] txtArray = txt.split("@@");
			String fxyp00 = UUID.randomUUID().toString();
			String userID = SysManagerUtils.getUserId();
			HBSession sess = HBUtil.getHBSession();
			HBUtil.executeUpdate("insert into fxyp(fxyp00,fxyp02,fxyp05,fxyp06,b01id,mntp00) values(?,?,?,?,?,?)",new Object[]{fxyp00,txtArray[0],userID,txtArray[1],txtArray[2],txtArray[3]});
			
			sess.flush();
			//this.getExecuteSG().addExecuteCode("menus.setAppendInfo('APPEND','"+fxyp00+"');openRenXuanTiaoJian('"+fxyp00+"');");
			this.getExecuteSG().addExecuteCode("openRenXuanTiaoJian('"+fxyp00+"');");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//按姓名查询，传递人员IDs
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String fxyp00) throws Exception{
		//System.out.println(listStr);
		HBSession sess = HBUtil.getHBSession();
		PreparedStatement ps = null;
		Connection conn = null;
		String a0000s = this.getPageElement("a0000s").getValue();
		a0000s = a0000s.replaceAll(",", "','");
		StringBuffer sql = new StringBuffer();
		if(a0000s!=null&&!"".equals(a0000s)){//插入岗位人员
			sql.append("select  sys_guid() tp0100, t.a0000 a0000,"
					+ " '3' type,GET_tpbXingming(t.a0101,t.a0104,t.a0117,t.a0141) tp0101,t.a0107 tp0102,t.a0192f tp0103, t.a0288 tp0104,"
					+ " GET_TPBXUELI2(t.qrzxl,t.zzxl,t.qrzxw,t.zzxw) tp0105,"
					+ " t.a0192a tp0106,'' tp0107,'' tp0108,'' tp0109,'' tp0110,"
					+ " '' tp0111,'' tp0112,'' tp0113,'' tp0114,'' tp0115,'"+fxyp00+"' fxyp00 from a01 t");
		
			sql.append(" where t.a0000 in ('"+a0000s+"') ");
			sql.append(" and not exists (select 1 from RXFXYP p where p.a0000=t.a0000");
			sql.append(" and fxyp00='"+fxyp00+"')");
			try {
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
				if(listCode.size()>0){
					String insertsql = "insert into RXFXYP(tp0100, a0000, type, tp0101, tp0102, tp0103, "
							+ "tp0104, tp0105, tp0106, tp0107, sortnum, fxyp00,tp0116,tp0111,tp0112,tp0115 )"
							+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					sess = HBUtil.getHBSession();
					conn = sess.connection();
					conn.setAutoCommit(false);
					String maxsortnum = HBUtil.getValueFromTab("max(sortnum)", "RXFXYP", "fxyp00='"+fxyp00+"'");
					int i=Integer.valueOf(maxsortnum==null?"0":maxsortnum);
					ps = conn.prepareStatement(insertsql);
					for(HashMap m : listCode){
						//出生年月处理
						String text = this.getFTime(m.get("tp0102"));
						m.put("tp0102",text);
						//任现职时间处理
						text = this.getFTime(m.get("tp0104"));
						m.put("tp0104",text);
						//任现职时间处理
						text = this.getFTime(m.get("tp0103"));
						m.put("tp0103",text);
						/*String jianli = m.get("tp0103")==null?"":m.get("tp0103").toString();
						String[] jianliArray = jianli.split("\r\n|\r|\n");
						if(jianliArray.length>0){
							String jl = jianliArray[jianliArray.length-1].trim();
							//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
							Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");     
					        Matcher matcher = pattern.matcher(jl);     
					        if (matcher.find()) {
					        	String line1 = matcher.group(0);  
					        	m.put("tp0103",line1.substring(0,7));
					        }else{
					        	m.put("tp0103",null);
					        }
						}else{
							m.put("tp0103",null);
						}*/
						
						
						ps.setString(1, m.get("tp0100").toString());
						ps.setString(2, textFormat(m.get("a0000")));
						ps.setString(3, m.get("type").toString());
						ps.setString(4, textFormat(m.get("tp0101")));
						ps.setString(5, textFormat(m.get("tp0102")));
						ps.setString(6, textFormat(m.get("tp0103")));
						ps.setString(7, textFormat(m.get("tp0104")));
						ps.setString(8, textFormat(m.get("tp0105")));
						ps.setString(9, textFormat(m.get("tp0106")));
						ps.setString(10, textFormat(m.get("tp0107")));
						ps.setInt(11, ++i);
						ps.setString(12, fxyp00);
						ps.setString(13, "");
						ps.setString(14, textFormat(m.get("tp0111")));
						ps.setString(15, textFormat(m.get("tp0112")));
						ps.setString(16, textFormat(m.get("tp0115")));
						ps.addBatch();
						
					}
					ps.executeBatch();
					conn.commit();
					ps.close();
					//JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
					//System.out.println(updateunDataStoreObject.toString());
					this.getExecuteSG().addExecuteCode("hideWin();");
					
				}
			} catch (Exception e) {
				try{
					if(conn!=null)
						conn.rollback();
					if(conn!=null)
						conn.close();
				}catch(Exception e1){
					this.setMainMessage("保存失败！");
					e.printStackTrace();
				}
				this.setMainMessage("查询失败！");
				e.printStackTrace();
			}
		}else{
			this.setMainMessage("无法查询到该人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
			
			
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
	
	
	
	@PageEvent("pgrid.dogridquery")
	public int pgrid(int start,int limit) throws RadowException{
	  String mntp00 = this.getPageElement("mntp00").getValue();
	  String b01id = this.getPageElement("b01id").getValue();
	  String sql = "select a.a0000,a.a0101,a.a0192a,m.fxyp02 yxgw,r.tp0100 from a01 a,hz_mntp_gw m,rxfxyp r "
	  		+ " where m.b01id='"+b01id+"' and m.fxyp00=r.fxyp00 and m.mntp00='"+mntp00+"' and a.a0000=r.a0000";
		
	  this.pageQuery(sql, "SQL", start, limit);
	  return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("DeleteP")
	@Transaction
	public int DeleteP(String tp0100) throws RadowException, AppException{
		
		try {
			HBSession sess = HBUtil.getHBSession();
			HBUtil.executeUpdate("update rxfxyp set sortnum=sortnum-1 where fxyp00=(select fxyp00 from rxfxyp where tp0100=?) and sortnum>(select sortnum from  rxfxyp where tp0100=?)",
					new Object[]{tp0100,tp0100});
			HBUtil.executeUpdate("delete from rxfxyp where tp0100=?",new Object[]{tp0100});
			sess.flush();
			this.setNextEventName("pgrid.dogridquery");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
