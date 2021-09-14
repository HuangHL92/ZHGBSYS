package com.insigma.siis.local.pagemodel.fxyp;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class MntpPageModel extends PageModel {
	
	
	
	
	@PageEvent("memberGrid.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogrid6AfterEdit() throws RadowException, SQLException {
		String mntp00 = this.getPageElement("mntp00").getValue();
		//获取页面数据
		Grid grid6 = (Grid) this.getPageElement("memberGrid");
		String mntp04 = grid6.getValue("mntp04") +"";
		String mntp01 = grid6.getValue("mntp01") +"";
		HBSession sess = HBUtil.getHBSession();
		
		sess.createSQLQuery("update hz_mntp set mntp04=?,mntp01=? where mntp00=?")
		.setString(0, mntp04)
		.setString(1, mntp01)
		.setString(2, mntp00).executeUpdate();
		sess.flush();
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		
		String sql="select * from (select y.mntp00,y.mntp01,to_char(y.mntp02,'yyyy-mm-dd') mntp02,y.mntp03,y.mntp04,y.mntp05,y.b0111,"
				+ "(select to_char(wm_concat(s.username))  from hz_mntpfa_userref u,smt_user s where s.userid=u.mnur01 and mnur02='"+SysManagerUtils.getUserId()+"' and y.mntp00=u.mntp00) mnur01,  "
				+ "(select  (select username from smt_user where userid=u.mnur02) from hz_mntpfa_userref u where mnur01='"+SysManagerUtils.getUserId()+"' and y.mntp00=u.mntp00) mnur02"
						+ " ,null fabd00,null fabd02  "
				+ "from hz_mntp y where y.mntp03='"+SysManagerUtils.getUserId()+"' "
				+ " or (exists (select 1 from hz_mntpfa_userref u where mnur01='"+SysManagerUtils.getUserId()+"' and u.mntp00=y.mntp00)) order by mntp02 desc)";
		String view = this.getPageElement("viewMode").getValue();
		if("1".equals(view)){
			sql += " union all "
					+ " select * from ( select null mntp00,null mntp01,to_char(fabd01,'yyyy-mm-dd') mntp02,null mntp03,fabd02 mntp04,null mntp05,null b0111,null mnur01,null mnur02,fabd00,fabd02 "
					+ " from HZ_MNTP_FABD  where fabd04='"+SysManagerUtils.getUserId()+"' order by fabd04,fabd01 ) ";
		}
		
		
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("cundang")
	@Transaction
	public int cundang(String mntp00) throws RadowException, AppException{
		try {
			HBSession sess = HBUtil.getHBSession();
			HBUtil.executeUpdate("update hz_mntp set mntp02=sysdate where mntp00=?",new Object[]{mntp00});
			sess.flush();
			this.setNextEventName("memberGrid.dogridquery");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("allDelete")
	@Transaction
	public int allDelete(String mntp00) throws RadowException, AppException{
		
		try {
			HBSession sess = HBUtil.getHBSession();
			/*delete from rxfxyp where fxyp00 in (select fxyp00 from fxyp where mntp00=?)
			delete from cxtj where fxyp00 in (select fxyp00 from fxyp where mntp00=?)
			delete from fxyp where mntp00=?
			delete from hz_mntp where mntp00=?*/
			HBUtil.executeUpdate("delete from rxfxyp where zwqc00 in (select zwqc00 from hz_mntp_zwqc where mntp00=?)",new Object[]{mntp00});
			
			HBUtil.executeUpdate("delete from cxtj where fxyp00 in (select fxyp00 from fxyp where mntp00=?)",new Object[]{mntp00});
			HBUtil.executeUpdate("delete from fxyp where mntp00=?",new Object[]{mntp00});
			
			HBUtil.executeUpdate("delete from HZ_MNTP_BZ where mntp00=?",new Object[]{mntp00});
			HBUtil.executeUpdate("delete from hz_mntpfa_userref where mntp00=?",new Object[]{mntp00});
			
			HBUtil.executeUpdate("delete from hz_mntp_zwqc where mntp00=?",new Object[]{mntp00});
			
			HBUtil.executeUpdate("delete from hz_mntp_userref where mntp00=?",new Object[]{mntp00});
			HBUtil.executeUpdate("delete from hz_mntp where mntp00=?",new Object[]{mntp00});
			HBUtil.executeUpdate("delete from gwsort where mntp00=?",new Object[]{mntp00});
			HBUtil.executeUpdate("delete from gwzwref where mntp00=?",new Object[]{mntp00});
			HBUtil.executeUpdate("delete from hz_mntp_pgridBuffer where mntp00=?",new Object[]{mntp00});
			HBUtil.executeUpdate("delete from hz_mntp_dwref where mntp00=?",new Object[]{mntp00});
			sess.flush();
			this.getExecuteSG().addExecuteCode("$('#mntp00').val('');");
			this.setNextEventName("memberGrid.dogridquery");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("saveMntp")
	@Transaction
	public int savesaveMntp(String yn_id) throws RadowException, AppException{
		String userid = this.getCurUserid();
		String username = this.getCurUsername();
		String mntp05 = "1";
		if("user1".equals(username)){
			mntp05 = "2";
		}else if("user2".equals(username)){
			mntp05 = "1";
		}else if("user3".equals(username)){
			mntp05 = "4";
		}
		
		String mntp01 = this.getPageElement("mntp01").getValue();
		String mntp04 = DateUtil.dateToString(new Date(), "yyyyMMdd");
		String mntp00 = UUID.randomUUID().toString();
		try {
			HBSession sess = HBUtil.getHBSession();
			sess.createSQLQuery("insert into hz_mntp(mntp00,mntp01,mntp03,mntp04,mntp05) values(?,?,?,?,'"+mntp05+"')")
			.setString(0, mntp00).setString(1, mntp01).setString(2, userid).setString(3, mntp04+"模拟调配").executeUpdate();
			sess.createSQLQuery("insert into fxyp(fxyp00,fxyp02) values(?,?)")
			.setString(0, mntp00).setString(1, "<span style='color:0033FF'>集中换届通用查询，不区分岗位</span>").executeUpdate();
			sess.flush();
			
			
			
			//添加空岗 001.001.004.001.001
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL("select qpid,gwname,gwnum,b01id,gwzf,"
					+ "(select b0111 from b01 b where b.b01id=t.b01id) b0111,"
					+ "(select b0131 from b01 b where b.b01id=t.b01id) b0131  from BGWQP t");
			for(HashMap<String, Object> m : nmzwList){
				String zwqc00 = UUID.randomUUID().toString();
				String b0111 = m.get("b0111")==null?"":m.get("b0111").toString();
				String qpid = m.get("qpid")==null?"":m.get("qpid").toString();
				String gwnum = m.get("gwnum")==null?"":m.get("gwnum").toString();
				String b01id = m.get("b01id")==null?"":m.get("b01id").toString();
				String gwname = m.get("gwname")==null?"":m.get("gwname").toString();
				String gwzf = m.get("gwzf")==null?"":m.get("gwzf").toString();
				String b0131 = m.get("b0131")==null?"":m.get("b0131").toString();
				String id=UUID.randomUUID().toString().substring(0,18);
				//区县
				if(b0111!=null&&b0111.length()>=15&&b0111.substring(0,11).equals("001.001.004")&&"1001,1003,1004,1005,1006,1007".contains(b0131)){
					b01id = HBUtil.getValueFromTab("b01id", "b01", "b0111='"+b0111.substring(0,15)+"'");
					HBUtil.executeUpdate("insert into hz_mntp_zwqc(mntp00,a0192a,zwqc00,fxyp07,zwqc01)"
							+ " values(?,?,?,?,?)", new Object[]{mntp00,gwname,zwqc00,1,gwnum});
					HBUtil.executeUpdate("insert into fxyp(fxyp00,fxyp02,fxyp05,fxyp06,b01id,mntp00,a0201e,zwqc00) values(?,?,?,?,?,?,?,?)",
							new Object[]{qpid.substring(0,18)+id,gwname,this.getCurUserid(),b0131,b01id,mntp00,gwzf,zwqc00});
					List list = HBUtil.getHBSession().createSQLQuery("select 1 from GWSORT where mntp00=? and b01id=?")
					.setString(0, mntp00).setString(1, b01id).list();
					if(list.size()==0){
						HBUtil.executeUpdate("insert into GWSORT(mntp00,sorttype,sortid,b01id,sortnum,zrrx) "
								+ " select '"+mntp00+"',type,id,'"+b01id+"',sortid,'"+b0131+"' from gwpxall where b0111='"+b0111+"' and type='1'  ");
					}
					
					HBUtil.executeUpdate("insert into GWSORT(mntp00,sorttype,sortid,b01id,sortnum,zrrx) "
							+ " select '"+mntp00+"',type,substr(id,0,18) || '"+id+"','"+b01id+"',sortid,'"+b0131+"' from gwpxall where b0111='"+b0111+"' and type='2'");
					
					
				}else{//市直
					String zrrx = "Z01";
					if("1".equals(gwzf)){
						zrrx = "101";
					}else if("3".equals(gwzf)){
						zrrx = "301";
					}
					HBUtil.executeUpdate("insert into hz_mntp_zwqc(mntp00,a0192a,zwqc00,fxyp07,zwqc01)"
							+ " values(?,?,?,?,?)", new Object[]{mntp00,gwname,zwqc00,1,gwnum});
					HBUtil.executeUpdate("insert into fxyp(fxyp00,fxyp02,fxyp05,fxyp06,b01id,mntp00,a0201e,zwqc00) values(?,?,?,?,?,?,?,?)",
							new Object[]{qpid.substring(0,18)+id,gwname,this.getCurUserid(),zrrx,b01id,mntp00,gwzf,zwqc00});
					
					List list = HBUtil.getHBSession().createSQLQuery("select 1 from GWSORT where mntp00=? and b01id=?")
							.setString(0, mntp00).setString(1, b01id).list();
					if(list.size()==0){
						HBUtil.executeUpdate("insert into GWSORT(mntp00,sorttype,sortid,b01id,sortnum,zrrx) "
								+ " select '"+mntp00+"',type,id,'"+b01id+"',sortid,'"+zrrx+"' from gwpxall where b0111='"+b0111+"' and type='1'");
					}
					
					HBUtil.executeUpdate("insert into GWSORT(mntp00,sorttype,sortid,b01id,sortnum,zrrx) "
							+ " select '"+mntp00+"',type,substr(id,0,18) || '"+id+"','"+b01id+"',sortid,'"+zrrx+"' from gwpxall where b0111='"+b0111+"' and type='2'");
				}
			}
			
			
			this.getPageElement("mntp00").setValue(mntp00);
			this.getPageElement("mntp05").setValue(mntp05);
			this.setNextEventName("memberGrid.dogridquery");
			this.getExecuteSG().addExecuteCode("Ext.getCmp('expFile').hide();");
			//this.getExecuteSG().addExecuteCode("openKqzsdw();");
			
			
			
			
			
		}catch (Exception e) {
			this.setMainMessage("保存失败！"+e.getMessage());
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("copymntp")
	@Transaction
	public int copymntp(String mntp00_old) throws RadowException, AppException{
		
		
		String mntp01 = this.getPageElement("mntp01").getValue();
		String mntp04_old = this.getPageElement("mntp04").getValue();
		String mntp04_new = mntp04_old+"-1";
		String mntp02 = DateUtil.dateToString(new Date(), "yyyyMMdd");
		String mntp00_new = UUID.randomUUID().toString();
		
		String username = this.getCurUsername();
		String mntp05 = "1";
		if("user1".equals(username)){
			mntp05 = "2";
		}else if("user2".equals(username)){
			mntp05 = "1";
		}else if("user3".equals(username)){
			mntp05 = "4";
		}
		
		String tp0100=UUID.randomUUID().toString().substring(0,18);
		String fxyp00=UUID.randomUUID().toString().substring(0,18);
		String zwqc00=UUID.randomUUID().toString().substring(0,18);
		HBSession sess = HBUtil.getHBSession();
		try {
			//hb_mntp
			HBUtil.executeUpdate("insert into hz_mntp(MNTP00,MNTP01,MNTP03,MNTP04,MNTP05,B0111) " + 
					"select '"+mntp00_new+"',MNTP01,MNTP03,MNTP04 || '-复制',MNTP05,B0111 from hz_mntp " + 
					"where mntp00='"+mntp00_old+"'");
			
			//fxyp
			HBUtil.executeUpdate("insert into FXYP(FXYP00,FXYP01,FXYP02,FXYP03,FXYP04,FXYP05,FXYP06,B01ID,MNTP00,FXYP07,A0200,GWCODE,ZJCODE,ZWCODE,A0219,A0201D,A0201E,ISCOUNT,STATUS,ZWQC00,FXYP00REF) " + 
					"select '"+fxyp00+"' ||substr(FXYP00,18),FXYP01,FXYP02,FXYP03,FXYP04,FXYP05,FXYP06,B01ID,'"+mntp00_new+"',FXYP07,A0200,GWCODE,ZJCODE,ZWCODE,A0219,A0201D,A0201E,ISCOUNT,STATUS,'"+zwqc00+"'||substr(zwqc00,18),FXYP00REF from FXYP " + 
					"where zwqc00 in (select zwqc00 from hz_mntp_zwqc where mntp00='"+mntp00_old+"')");
			
			//rxfxyp
			HBUtil.executeUpdate("insert into RXFXYP(TP0100,A0000,TYPE,TP0101,TP0102,TP0103,TP0104,TP0105,TP0106,TP0107,TP0108,TP0109,TP0110,TP0111,SORTNUM,TP0112,TP0113,TP0114,TP0115,FXYP00,TP0116,ZWQC00) " + 
					"select '"+tp0100+"'||substr(tp0100,18),A0000,TYPE,TP0101,TP0102,TP0103,TP0104,TP0105,TP0106,TP0107,TP0108,TP0109,TP0110,TP0111,SORTNUM,TP0112,TP0113,TP0114,TP0115,'"+fxyp00+"'||substr(fxyp00,18),TP0116,'"+zwqc00+"'||substr(zwqc00,18) from RXFXYP " + 
					"where zwqc00 in (select zwqc00 from hz_mntp_zwqc where mntp00='"+mntp00_old+"')");
			
			//hz_mntp_zwqc
			HBUtil.executeUpdate("insert into hz_mntp_zwqc(MNTP00,A0192A,ZWQC00,FXYP07,SORTNUM,CDATE,ZWQC01,A0501B) " + 
					"select '"+mntp00_new+"',A0192A,'"+zwqc00+"' || substr(ZWQC00,18),FXYP07,SORTNUM,CDATE,ZWQC01,A0501B from hz_mntp_zwqc " + 
					"where zwqc00 in (select zwqc00 from hz_mntp_zwqc where mntp00='"+mntp00_old+"')");
			
			//hz_mntp_bz
			HBUtil.executeUpdate("insert into hz_mntp_bz(MNTP00,A01BZDESC,A01BZTYPE,A01BZID,B01ID,B0101,RYBZ) " + 
					"select '"+mntp00_new+"',A01BZDESC,A01BZTYPE,A01BZID,B01ID,B0101,RYBZ from hz_mntp_bz " + 
					"where  mntp00='"+mntp00_old+"'");
			
			//hz_mntp_dwref
			HBUtil.executeUpdate("insert into hz_mntp_dwref(mntp00,b01id,mntp05) " + 
					"select '"+mntp00_new+"',b01id,mntp05 from hz_mntp_dwref " + 
					"where mntp00='"+mntp00_old+"'");
			
			//gwsort
			HBUtil.executeUpdate("insert into GWSORT(MNTP00,SORTTYPE,SORTID,B01ID,SORTNUM,ZRRX) " + 
					"select '"+mntp00_new+"',SORTTYPE,SORTID,B01ID,SORTNUM,ZRRX from GWSORT  " + 
					"where mntp00='"+mntp00_old+"' and sorttype='1'");
			
			HBUtil.executeUpdate("insert into GWSORT(MNTP00,SORTTYPE,SORTID,B01ID,SORTNUM,ZRRX) " + 
					"select '"+mntp00_new+"',SORTTYPE,'"+fxyp00+"' || substr(SORTID,18),B01ID,SORTNUM,ZRRX from GWSORT  " + 
					"where mntp00='"+mntp00_old+"' and sorttype='2'");
			
			//gwzwref
			HBUtil.executeUpdate("insert into gwzwref(MNTP00,ZWQC00,FXYP00,A0200,A0000) " + 
					"select '"+mntp00_new+"','"+zwqc00+"' || substr(ZWQC00,18),'"+fxyp00+"'||substr(fxyp00,18),A0200,A0000 from gwzwref " + 
					"where zwqc00 in (select zwqc00 from hz_mntp_zwqc where mntp00='"+mntp00_old+"')");
			
			//hz_mntp_pgridBuffer
			HBUtil.executeUpdate("insert into hz_mntp_pgridBuffer(MNTP00,A0000) " + 
					"select '"+mntp00_new+"',A0000 from hz_mntp_pgridBuffer " + 
					"where mntp00='"+mntp00_old+"'");
			this.getPageElement("mntp00").setValue(mntp00_new);
			this.getPageElement("mntp05").setValue(mntp05);
			this.setNextEventName("memberGrid.dogridquery");
			this.getExecuteSG().addExecuteCode("Ext.getCmp('expFile').hide();");
		}catch (Exception e) {
			this.setMainMessage("保存失败！"+e.getMessage());
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveTuiSongInfo.onclick")
    public int saveTuiSongInfo() throws RadowException {
		String curuserid = this.getCurUserid();
		String mntp00 = this.getPageElement("mntp00").getValue();
		String mnur01 = this.getPageElement("mnur01").getValue();
		String mnur01_combotree = this.getPageElement("mnur01_combotree").getValue();
		try {
			HBUtil.executeUpdate("delete from hz_mntpfa_userref where mntp00=? and mnur02=?",new Object[]{mntp00,curuserid});
			if(!StringUtils.isEmpty(mnur01)){
				
				String[] mnur01_combotrees = mnur01_combotree.split(",");
				String[] mnur01s = mnur01.split(",");
				for(int i=0;i<mnur01s.length;i++){
					HBUtil.executeUpdate("insert into hz_mntpfa_userref(mnur00,mntp00,mnur01,mnur02,mnur04) "
							+ "values(sys_guid(),?,?,?,?)",new Object[]{mntp00,mnur01s[i],curuserid,mnur01_combotrees[i]});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.getExecuteSG().addExecuteCode("Ext.getCmp('tuiSong').hide();");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("init0")
	public int init0() throws RadowException, AppException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		try {
			@SuppressWarnings("unchecked")
			List<String> tpdw= HBUtil.getHBSession().createSQLQuery("select distinct  b0111 from b01 c,fxyp " + 
					"where c.b01id=fxyp.b01id and fxyp.mntp00='"+mntp00+"' and fxyp.fxyp07 is not null " + 
					"order by c.b0111").list();
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			if(tpdw.size()>0) {
				for(int i=0;i<tpdw.size();i++) {
					@SuppressWarnings("unchecked")
					List<String> b0101= HBUtil.getHBSession().createSQLQuery("select b0101 from b01 where b0111='"+tpdw.get(i)+"'").list();
					map.put(tpdw.get(i), b0101.get(0));
				}
			}
			((Combo)this.getPageElement("b0101Org")).setValueListForSelect(map); 
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	
	}
	
	
	
}
