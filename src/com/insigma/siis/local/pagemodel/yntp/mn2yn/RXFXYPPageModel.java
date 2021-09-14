package com.insigma.siis.local.pagemodel.yntp.mn2yn;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.HzJs2Yntp;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONArray;
/**
 * 人选分析研判
 * @author epsoft
 *
 */
public class RXFXYPPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		
		//设置下拉框
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		String userid = SysManagerUtils.getUserId();
		String mntp00 = this.getPageElement("mntp00").getValue();
		mntp00 = mntp00.replace(",", "','");
		String sql = "select  (select count(1) from rxfxyp rx where  rx.fxyp00=f.fxyp00) countf,"+
"f.fxyp02,f.fxyp07,f.fxyp00,r.tp0100, r.a0000, r.type, r.tp0101, r.tp0102, r.tp0103, r.tp0104,"+
"r.tp0105, r.tp0106, r.tp0107,r.tp0108,r.tp0109,r.tp0110, r.tp0111,"+
"r.tp0112,r.tp0113,r.tp0114,r.tp0115 from fxyp f left join rxfxyp r on f.fxyp00=r.fxyp00 "
+ " where f.fxyp05='"+userid+"' and f.mntp00 in ('"+mntp00+"')"+
"order by f.fxyp07 desc, f.fxyp04 asc,r.sortnum";
		
		
		try {
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
			JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
			//System.out.println(updateunDataStoreObject.toString());
			this.getExecuteSG().addExecuteCode("doAddPerson.addPerson("+updateunDataStoreObject.toString()+");");
			
			
			
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
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
	
	
	@PageEvent("genTPB")
	public int genTPB() throws Exception{
		//String mntp00 = this.getPageElement("mntp00").getValue();
		//mntp00 = mntp00.replace(",", "','");
		String tp0100s = this.getPageElement("tp0100s").getValue();
		tp0100s = tp0100s.replace(",", "','");
		HBSession sess = HBUtil.getHBSession();
		PreparedStatement ps = null;
		PreparedStatement ps_zw = null;
		Connection conn = null;
		try {
			HzJs2Yntp yn = new HzJs2Yntp();
			String yn_id = UUID.randomUUID().toString();
			yn.setYnName(new SimpleDateFormat("yyyyMMdd").format(new Date())+"干部调配建议方案（模拟方案提取）");
			yn.setYnType("TPHJ1");
			yn.setYnId(yn_id);
			yn.setYnUserid(SysManagerUtils.getUserId());
			yn.setYnSysno(BigDecimal.valueOf(Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004"))));
			yn.setYnDate(new Timestamp( new Date().getTime()));
			sess.save(yn);
			Calendar cal = Calendar.getInstance();
	        int year = cal.get(Calendar.YEAR);//获取年份
	        int month=cal.get(Calendar.MONTH)+1;//获取月份
	        int day=cal.get(Calendar.DATE);//获取日
	        String uname = SysManagerUtils.getUserloginName();
			sess.createSQLQuery("insert into hz_js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ1','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
			sess.createSQLQuery("insert into hz_js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ2','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
			sess.createSQLQuery("insert into hz_js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ3','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
			sess.createSQLQuery("insert into hz_js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ4','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
			sess.createSQLQuery("insert into hz_js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ5','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
		
			sess.flush();
		
			
			String selsqlNR = "select sys_guid() tp0100, r.a0000, r.type, r.tp0101, r.tp0102, r.tp0103, r.tp0104, r.tp0105, r.tp0106, "
					+ " r.sortnum,'"+yn_id+"' yn_id,'TPHJ1' tp0116,f.fxyp02,f.b01id,"
							+ "f.gwcode,f.zjcode,f.zwcode,f.a0219,f.a0201d,f.a0201e,f.iscount,f.status from fxyp f inner join rxfxyp r on f.fxyp00=r.fxyp00 "
							+ " where tp0100 in('"+tp0100s+"') and  f.fxyp07='1'  order by f.mntp00, f.fxyp04 asc,r.sortnum asc";
			
			
			
			String selsqlNM = "select sys_guid() tp0100, r.a0000, r.type, r.tp0101, r.tp0102, r.tp0103, r.tp0104, r.tp0105, r.tp0106, "
					+ " r.sortnum,'"+yn_id+"' yn_id,'TPHJ1' tp0116,f.fxyp02,f.b01id,"
							+ "f.gwcode,f.zjcode,f.zwcode,f.a0219,f.a0201d,f.a0201e,f.iscount,f.status from fxyp f inner join rxfxyp r on f.fxyp00=r.fxyp00 "
					+ " where tp0100 in('"+tp0100s+"') and  f.fxyp07='-1'  order by f.mntp00, f.fxyp04 asc,r.sortnum asc";
			
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCodeNR =cqbs.getListBySQL(selsqlNR);
			List<HashMap<String, Object>> listCodeNM =cqbs.getListBySQL(selsqlNM);
			conn = sess.connection();
			conn.setAutoCommit(false);
			
			String insertSql = "insert into hz_TPHJ1("
					+ "tp0100, a0000, type, tp0101, tp0102, tp0103, "
					+ "tp0104, tp0105, tp0106, tp0107, sortnum, yn_id,"
					+ "tp0116,tp0111,tp0112,tp0115 ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			//职务详细信息
			String insertSql_zw = "insert into HZ_TPHJ_zw("
					+ "tpzw00,tp0100,tpzw01,fxyp07,b01id,tpzw02,tpzw04,yn_id,tp0116,"
					+ "gwcode,zjcode,zwcode,a0219,a0201d,a0201e,iscount,status ) values(?,?,?,?,?,SEQ_SORT.NEXTVAL,to_char(sysdate,'yyyymmdd'),'"+yn_id+"','TPHJ1',"
							+ "?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(insertSql);
			ps_zw = conn.prepareStatement(insertSql_zw);
			int i=0;
			List<String>  NRa0000List = new ArrayList<String>();
			List<String>  NMa0000List = new ArrayList<String>();
			for(HashMap m : listCodeNR){
				String a0000 = m.get("a0000")==null?"":m.get("a0000").toString();
				if(NRa0000List.contains(a0000)){
					continue;
				}
				//获取拟任职务
				String NRZWOrther = this.getNNZW(m,listCodeNR,ps_zw,NRa0000List);
				
				//获取拟免职务
				String NMZW = this.getNMZW(m,listCodeNM,ps_zw,NMa0000List);
				String tp0100 = m.get("tp0100")==null?"":m.get("tp0100").toString();
				
				String type = m.get("type")==null?"":m.get("type").toString();
				String tp0102 = m.get("tp0102")==null?"":m.get("tp0102").toString();
				String tp0101 = m.get("tp0101")==null?"":m.get("tp0101").toString();
				String tp0103 = m.get("tp0103")==null?"":m.get("tp0103").toString();
				String tp0104 = m.get("tp0104")==null?"":m.get("tp0104").toString();
				String tp0105 = m.get("tp0105")==null?"":m.get("tp0105").toString();
				String tp0106 = m.get("tp0106")==null?"":m.get("tp0106").toString();
				String sortnum = m.get("sortnum")==null?"":m.get("sortnum").toString();
				String tp0111 = m.get("fxyp02")==null?"":m.get("fxyp02").toString();
				String ynId = m.get("yn_id")==null?"":m.get("yn_id").toString();
				String tp0116 = m.get("tp0116")==null?"":m.get("tp0116").toString();
				String b01id = m.get("b01id")==null?"":m.get("b01id").toString();
				
				
				
				
				
				
				
				ps.setString(1, tp0100);
				ps.setString(2, a0000);
				ps.setString(3, type);
				ps.setString(4, tp0101);
				ps.setString(5, tp0102);
				ps.setString(6, tp0103);
				ps.setString(7, tp0104);
				ps.setString(8, tp0105);
				ps.setString(9, tp0106);
				String tp0107 = "";
				if("1".equals(sortnum)){
					if(!StringUtils.isEmpty(NRZWOrther)){
						tp0107 = "任" + NRZWOrther;
					}
					if(!StringUtils.isEmpty(NMZW)){
						if(StringUtils.isEmpty(tp0107)){
							tp0107 = "免" + NMZW;
						}else{
							tp0107 = tp0107 + "，免" + NMZW;
						}
					}
				}
				
				
				ps.setString(10, tp0107);
				ps.setString(11, i++ +"");
				ps.setString(12, ynId);
				ps.setString(13, tp0116);
				if("1".equals(sortnum)){
					ps.setString(14, NRZWOrther);
					ps.setString(15, NMZW);
				}else{
					ps.setString(14, null);
					ps.setString(15, null);
				}
				
				ps.setString(16, null);
				ps.addBatch();
				
			}
			
			
			// 只有拟免
			out:
			for(HashMap m : listCodeNM){
				
				String tp0100 = m.get("tp0100")==null?"":m.get("tp0100").toString();
				
				String type = m.get("type")==null?"":m.get("type").toString();
				String tp0102 = m.get("tp0102")==null?"":m.get("tp0102").toString();
				String tp0101 = m.get("tp0101")==null?"":m.get("tp0101").toString();
				String tp0103 = m.get("tp0103")==null?"":m.get("tp0103").toString();
				String tp0104 = m.get("tp0104")==null?"":m.get("tp0104").toString();
				String tp0105 = m.get("tp0105")==null?"":m.get("tp0105").toString();
				String tp0106 = m.get("tp0106")==null?"":m.get("tp0106").toString();
				String sortnum = m.get("sortnum")==null?"":m.get("sortnum").toString();
				String tp0111 = m.get("fxyp02")==null?"":m.get("fxyp02").toString();
				String ynId = m.get("yn_id")==null?"":m.get("yn_id").toString();
				String tp0116 = m.get("tp0116")==null?"":m.get("tp0116").toString();
				String b01id = m.get("b01id")==null?"":m.get("b01id").toString();
				
				
				
				String a0000 = m.get("a0000")==null?"":m.get("a0000").toString();
				if(NMa0000List.contains(a0000)){
					continue;
				}
				String NMZW = this.getNMZW(m,listCodeNM,ps_zw,NMa0000List);
				
				
				
				
				
				ps.setString(1, tp0100);
				ps.setString(2, a0000);
				ps.setString(3, type);
				ps.setString(4, tp0101);
				ps.setString(5, tp0102);
				ps.setString(6, tp0103);
				ps.setString(7, tp0104);
				ps.setString(8, tp0105);
				ps.setString(9, tp0106);
				String tp0107 = "";
				if(!StringUtils.isEmpty(NMZW)){
					tp0111 = NMZW;
				}
				if(!StringUtils.isEmpty(tp0111)){
					tp0107 = "免" + tp0111;
				}
				
				ps.setString(10, tp0107);
				ps.setString(11, i++ +"");
				ps.setString(12, ynId);
				ps.setString(13, tp0116);
				ps.setString(14, null);
				ps.setString(15, tp0111);
				
				ps.setString(16, null);
				ps.addBatch();
				
			}
			
			ps.executeBatch();
			ps_zw.executeBatch();
			conn.commit();
			ps.close();
			//sess.createSQLQuery(insertSql+"("+selectSql+")").executeUpdate();
			
			//this.setMainMessage("“"+yn.getYnName()+"”生成成功！");
			this.getExecuteSG().addExecuteCode("openGDview('"+yn_id+"')");
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
			e.printStackTrace();
			this.setMainMessage("保存失败");
		}
			
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	private String getNMZW(HashMap m, List<HashMap<String, Object>> listCodeNM, PreparedStatement ps_zw, List<String> nMa0000List) throws Exception {
		String a0000 = m.get("a0000").toString();
		String tp0100 = m.get("tp0100")==null?"":m.get("tp0100").toString();
		StringBuilder sb = new StringBuilder();
		for(HashMap nmMap : listCodeNM){
			if(nmMap.get("a0000").toString().equals(a0000)){
				nMa0000List.add(a0000);
				String tp0111 = nmMap.get("fxyp02")==null?"":nmMap.get("fxyp02").toString();
				sb.append(tp0111+"，");
				
				
				
				String b01id = nmMap.get("b01id")==null?"":nmMap.get("b01id").toString();
				//f.gwcode,f.zjcode,f.zwcode,f.a0219,f.a0201d,f.a0201e,f.iscount,f.status 
				String gwcode = nmMap.get("gwcode")==null?"":nmMap.get("gwcode").toString();
				String zjcode = nmMap.get("zjcode")==null?"":nmMap.get("zjcode").toString();
				String zwcode = nmMap.get("zwcode")==null?"":nmMap.get("zwcode").toString();
				String a0219 = nmMap.get("a0219")==null?"":nmMap.get("a0219").toString();
				String a0201d = nmMap.get("a0201d")==null?"":nmMap.get("a0201d").toString();
				String a0201e = nmMap.get("a0201e")==null?"":nmMap.get("a0201e").toString();
				String iscount = nmMap.get("iscount")==null?"":nmMap.get("iscount").toString();
				String status = nmMap.get("status")==null?"":nmMap.get("status").toString();
				ps_zw.setString(1, UUID.randomUUID().toString());
				ps_zw.setString(2, tp0100);
				ps_zw.setString(3, tp0111);
				ps_zw.setString(4, "-1");
				ps_zw.setString(5, b01id);
				ps_zw.setString(6, gwcode);
				ps_zw.setString(7, zjcode);
				ps_zw.setString(8, zwcode);
				ps_zw.setString(9, a0219);
				ps_zw.setString(10, a0201d);
				ps_zw.setString(11, a0201e);
				ps_zw.setString(12, iscount);
				ps_zw.setString(13, status);
				ps_zw.addBatch();
				
				
				
			}
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	private String getNNZW(HashMap m, List<HashMap<String, Object>> listCodeNR, PreparedStatement ps_zw, List<String> nRa0000) throws Exception {
		String a0000 = m.get("a0000").toString();
		String fxyp02 = m.get("fxyp02")==null?"":m.get("fxyp02").toString();
		String tp0100 = m.get("tp0100")==null?"":m.get("tp0100").toString();
		StringBuilder sb = new StringBuilder();
		for(HashMap nmMap : listCodeNR){
			if(nmMap.get("a0000").toString().equals(a0000)){
				nRa0000.add(a0000);
				String tp0111 = nmMap.get("fxyp02")==null?"":nmMap.get("fxyp02").toString();
				sb.append(tp0111+"，");
				
				
				
				
				String b01id = nmMap.get("b01id")==null?"":nmMap.get("b01id").toString();
				
				
				
				
				//f.gwcode,f.zjcode,f.zwcode,f.a0219,f.a0201d,f.a0201e,f.iscount,f.status 
				String gwcode = nmMap.get("gwcode")==null?"":nmMap.get("gwcode").toString();
				String zjcode = nmMap.get("zjcode")==null?"":nmMap.get("zjcode").toString();
				String zwcode = nmMap.get("zwcode")==null?"":nmMap.get("zwcode").toString();
				String a0219 = nmMap.get("a0219")==null?"":nmMap.get("a0219").toString();
				String a0201d = nmMap.get("a0201d")==null?"":nmMap.get("a0201d").toString();
				String a0201e = nmMap.get("a0201e")==null?"":nmMap.get("a0201e").toString();
				String iscount = nmMap.get("iscount")==null?"":nmMap.get("iscount").toString();
				String status = nmMap.get("status")==null?"":nmMap.get("status").toString();
				ps_zw.setString(1, UUID.randomUUID().toString());
				ps_zw.setString(2, tp0100);
				ps_zw.setString(3, tp0111);
				ps_zw.setString(4, "1");
				ps_zw.setString(5, b01id);
				ps_zw.setString(6, gwcode);
				ps_zw.setString(7, zjcode);
				ps_zw.setString(8, zwcode);
				ps_zw.setString(9, a0219);
				ps_zw.setString(10, a0201d);
				ps_zw.setString(11, a0201e);
				ps_zw.setString(12, iscount);
				ps_zw.setString(13, status);
				ps_zw.addBatch();
			}
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
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
	
	
	
	
	
	
	@PageEvent("tpbj.onclick")
    public int tpbj() throws RadowException {
        LinkedHashSet<String> selected = new LinkedHashSet<String>();
        // 从cookie中的获取之前选择的人员id
        Cookie[] cookies = this.request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("jggl.tpbj.ids".equals(cookie.getName())) {
                    String cookieValue = cookie.getValue();
                    String[] ids = cookieValue.split("#");
                    for (String id : ids) {
                    	if(!StringUtils.isEmpty(id)){
                        	selected.add(id);
                        }
                    }
                }
            }
        }
        // 从列表或者小资料中获取选择的人员
        String a0000s = this.getPageElement("a0000srybd").getValue();
        if(!StringUtils.isEmpty(a0000s)){
        	String[] a0000Array = a0000s.split(",");
    		for(int i=0;i<a0000Array.length;i++){
    			selected.add(a0000Array[i]);
    		}
        }
        
        
        if (selected.size() == 0) {
//			this.setMainMessage("请选择人员");
            this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
            return EventRtnType.FAILD;
        } else {
            String json = JSON.toJSONString(selected);
            this.getExecuteSG().addExecuteCode("$h.openWin('tpbjWindow','pages.fxyp.GbglTpbj','同屏比较',1500,731,null,'"+this.request.getContextPath()+"',null,{" + "maximizable:false,resizable:false,RMRY:'同屏比较',addPerson:true,data:" + json + "},true)");
            return EventRtnType.NORMAL_SUCCESS;
        }
    }
	
	
	
	
	
}