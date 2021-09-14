package com.insigma.siis.local.pagemodel.dataverify;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
//import com.insigma.siis.local.business.tbdeal.DataCheckBS;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

/**
 * Servlet implementation class InsjfxServlet
 */
public class InsjfxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method");
		if (method.equals("initX")) {

			try {
				initX(request, response);
			} catch (AppException e) {
				e.printStackTrace();
			}
		} else if (method.equals("changeZJ")) {
			try {
				changeZJ(request, response);
			} catch (AppException e) {
				e.printStackTrace();
			}
		} else if (method.equals("changeRadio")) {
			try {
				changeRadio(request, response);
			} catch (RadowException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void initX(HttpServletRequest request, HttpServletResponse response) throws AppException {
		/*
		 * CommQuery cq=new CommQuery(); HBSession sess =HBUtil. getHBSession (); String
		 * tjtype = request.getParameter("tjtype"); String ditype =
		 * request.getParameter("ditype"); String zjtype =
		 * request.getParameter("zjtype"); String cityid =
		 * request.getParameter("cityid"); String data = request.getParameter("year");
		 * String type = request.getParameter("type"); String values=""; String areaid =
		 * ""; String areasql = ""; String strmap2 = ""; String idsql = "";
		 * 
		 * try { if("zt".equals(type)){ SciBS.ifnull(cityid, tjtype, ditype,
		 * zjtype,data,type); String
		 * citysql=" select id from dt_area where area_name in (select b.b0101 from zwhzyq.b01 b where b.b0111 = '"
		 * +cityid+"')"; //List<HashMap<String, Object>> lista=
		 * sess.getListBySQL(citysql); List<HashMap<String, Object>> lista=
		 * cq.getListBySQL(citysql);
		 * 
		 * if(lista!=null&&lista.size()>0){ areaid =
		 * lista.get(0).get("id")==null?"":lista.get(0).get("id").toString();
		 * if(areaid.equals("100000")){
		 * areasql="select b.b0101,b.b0111 from zwhzyq.b01 b where b.b0101 in (select area_name from dt_area where id ='"
		 * +areaid+"')"; }else{
		 * areasql="select b.b0101,b.b0111 from zwhzyq.b01 b where b.b0101 in (select area_name from dt_area where area_parent_id='"
		 * +areaid+"')";
		 * 
		 * }
		 * 
		 * 
		 * List<HashMap<String, Object>> listb= cq.getListBySQL(areasql); //
		 * {name:"浙江省", value:102012}, strmap2="["; if(listb!=null&&listb.size()>0){
		 * for(int i=0;i<listb.size();i++){ HashMap<String, Object> map=listb.get(i);
		 * String name=(String) map.get("b0101"); String id=(String) map.get("b0111");
		 * 
		 * if(name.equals("全国")){ String
		 * bsql="select b.b0111,b.b0101 from  zwhzyq.b01 b where b.b0101 in (select area_name from dt_area where area_parent_id='"
		 * +areaid+"')"; List<HashMap<String, Object>> listqg= cq.getListBySQL(bsql);
		 * if(listqg!=null&&listqg.size()>0){ for(int j=0;j<listqg.size();j++){
		 * HashMap<String, Object> mapqg=listqg.get(j); String idqg=(String)
		 * mapqg.get("b0111"); String nameqg=(String) mapqg.get("b0101");
		 * idsql="select count(A0000) from zwhzyq.a01 a where a.A0163 = '1' and a.A0160 = '1'  and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * +idqg+"%')";
		 * idsql="select count(A0000) from zwhzyq.a01 where A0163 = '1' and A0160 = '1' and A0104 = '2' and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and a0201b like '"
		 * +idqg+"%')";
		 * idsql="select count(A0000) from zwhzyq.a01 where A0163 = '1' and A0160 = '1' and A0117 <> '01'  and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and a0201b like '"
		 * +idqg+"%')";
		 * idsql="select count(A0000) from zwhzyq.a01 where A0163 = '1' and A0160 = '1' and A0141 <> '01'  and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and a0201b like '"
		 * +idqg+"%')";
		 * idsql="select count(a.A0000) from zwhzyq.a01 a left join zwhzyq.a01 b on a.a0000=b.a0000 where a.A0163 = '1' and a.A0160 = '1' and b.A0834 = '1' and (substr(b.A0801B,1,1)='1' or  substr(b.A0801B,1,1)='2') and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and a0201b like '"
		 * +idqg+"%')";
		 * idsql="select count(A0000) from zwhzyq.a01 where A0163 = '1' and A0160 = '1' and GET_BIRTHDAY( a01.a0107,'"
		 * +datestr+"')<=30 and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and like '"
		 * +idqg+"%')";
		 * idsql="select count(A0000) from zwhzyq.a01 where A0163 = '1' and A0160 = '1' and GET_BIRTHDAY( a01.a0107,'"
		 * +datestr+"') <=35 and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and like '"
		 * +idqg+"%')";
		 * idsql="select count(a.A0000) from zwhzyq.a01 a left join zwhzyq.a05 b on a.a0000=b.a0000 where a.A0163 = '1' and a.A0160 = '1' and  a.A0104 = '2'  and b.A0524 = '1' and b.A0531 = '0' and substr(b.A0501B,1,1)='1'  and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and like '"
		 * +idqg+"%')";
		 * 
		 * 
		 * String value=SciBS.eqnull(sess.createSQLQuery(idsql).list()); strmap2 +=
		 * "{name:\""+nameqg+"\", value:"+value+"},"; }
		 * 
		 * }else{ String
		 * asql="select area_name from dt_area where area_parent_id='"+areaid+"'";
		 * List<HashMap<String, Object>> listc= cq.getListBySQL(asql);
		 * if(listc!=null&&listc.size()>0){ for(int z=0;z<listc.size();z++){
		 * HashMap<String, Object> mapz=listc.get(z); String namez=(String)
		 * mapz.get("area_name"); strmap2 += "{name:\""+namez+"\", value:\"0\"},"; } } }
		 * strmap2=strmap2.substring(0,strmap2.length()-1); strmap2 += "]"; }else{
		 * 
		 * idsql="select count(A0000) from zwhzyq.a01 a where a.A0163 = '1' and a.A0160 = '1'  and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * +id+"%')"; String value=SciBS.eqnull(sess.createSQLQuery(idsql).list());
		 * strmap2 += "{name:\""+name+"\", value:"+value+"},"; } }
		 * strmap2=strmap2.substring(0,strmap2.length()-1); strmap2 += "]";
		 * this.getExecuteSG().addExecuteCode("showMap("+strmap2+",'100000');");
		 * 
		 * 
		 * }else{ String
		 * asql="select area_name from dt_area where area_parent_id='"+areaid+"'";
		 * List<HashMap<String, Object>> listc= cq.getListBySQL(asql);
		 * if(listc!=null&&listc.size()>0){ for(int i=0;i<listc.size();i++){
		 * HashMap<String, Object> map=listc.get(i); String name=(String)
		 * map.get("name"); strmap2 += "{name:\""+name+"\", value:\"0\"},"; }
		 * strmap2=strmap2.substring(0,strmap2.length()-1); strmap2 += "]"; }
		 * 
		 * 
		 * } }else{ areaid = ""; }
		 * 
		 * }else{ if("2018".equals(data)){ if(tjtype.equals("gwy")){
		 * SciBS.ifnull(cityid, tjtype, ditype, zjtype,data,type); values = "J1801106";
		 * }else if(tjtype.equals("csqt")){ SciBS.ifnull(cityid, tjtype, ditype,
		 * zjtype,data,type); values = "J1802046"; }else{ SciBS.ifnull(cityid, tjtype,
		 * ditype, zjtype,data,type); values = "J1803102"; } }else
		 * if("2017".equals(data)){ if(tjtype.equals("gwy")){ SciBS.ifnull(cityid,
		 * tjtype, ditype, zjtype,data,type); values = "J1703105"; }else
		 * if(tjtype.equals("csqt")){ SciBS.ifnull(cityid, tjtype, ditype,
		 * zjtype,data,type); values = "J1701046"; }else{ SciBS.ifnull(cityid, tjtype,
		 * ditype, zjtype,data,type); values = "J1702054"; } }else
		 * if("2016".equals(data)){ if(tjtype.equals("gwy")){ SciBS.ifnull(cityid,
		 * tjtype, ditype, zjtype,data,type); values = "J1603091"; }else
		 * if(tjtype.equals("csqt")){ SciBS.ifnull(cityid, tjtype, ditype,
		 * zjtype,data,type); values = "J1601046"; }else{ SciBS.ifnull(cityid, tjtype,
		 * ditype, zjtype,data,type); values = "J1602054"; } }
		 * 
		 * String
		 * citysql=" select id from dt_area where area_name in (select name from tj_un where id = '"
		 * +cityid+"' and RPTYEAR = '"+data+"')"; //List<HashMap<String, Object>> lista=
		 * sess.getListBySQL(citysql); List<HashMap<String, Object>> lista=
		 * cq.getListBySQL(citysql);
		 * 
		 * if(lista!=null&&lista.size()>0){ areaid =
		 * lista.get(0).get("id")==null?"":lista.get(0).get("id").toString();
		 * if(areaid.equals("100000")){
		 * areasql="select name,id from tj_un where name in (select area_name from dt_area where id ='"
		 * +areaid+"')"; }else{
		 * areasql="select name,id from tj_un where name in (select area_name from dt_area where area_parent_id='"
		 * +areaid+"')";
		 * 
		 * }
		 * 
		 * 
		 * List<HashMap<String, Object>> listb= cq.getListBySQL(areasql); //
		 * {name:"浙江省", value:102012}, strmap2="["; if(listb!=null&&listb.size()>0){
		 * for(int i=0;i<listb.size();i++){ HashMap<String, Object> map=listb.get(i);
		 * String name=(String) map.get("name"); String id=(String) map.get("id");
		 * if(name.equals("全国")){ String
		 * bsql="select id,name from  tj_un where name in (select area_name from dt_area where area_parent_id='"
		 * +areaid+"')"; List<HashMap<String, Object>> listqg= cq.getListBySQL(bsql);
		 * if(listqg!=null&&listqg.size()>0){ for(int j=0;j<listqg.size();j++){
		 * HashMap<String, Object> mapqg=listqg.get(j); String idqg=(String)
		 * mapqg.get("id"); String nameqg=(String) mapqg.get("name");
		 * idsql="select j1 from "+values+" where ROWSID = '1' and  unitid = '"+idqg+
		 * "'";
		 * idsql="select j2 from "+values+" where ROWSID = '1' and  unitid = '"+idqg+
		 * "'";
		 * idsql="select j3 from "+values+" where ROWSID = '1' and  unitid = '"+idqg+
		 * "'"; idsql="select sum(j1)-sum(j4) from "
		 * +values+" where ROWSID = '1' and  unitid = '"+idqg+"'";
		 * idsql="select sum(j2+j3+j4+j5+j6+j7+j8+j9+j10+j11+j12) from "
		 * +values+" where ROWSID = '1' and  unitid = '"+idqg+"'";
		 * idsql="select sum(j2+j3+j4+j5+j6+j7+j8+j9+j10+j11+j12+j13+j14+j15+j16+j17) from "
		 * +values+" where ROWSID = '1' and  unitid = '"+idqg+"'";
		 * idsql="select sum(j2) from "
		 * +values+" where (ROWSID = '3'or ROWSID = '4' or ROWSID = '5' or ROWSID = '6' or ROWSID = '7' or ROWSID = '8'"
		 * +
		 * " or ROWSID = '9' or ROWSID = '10' or ROWSID = '11' or ROWSID = '12') and  unitid = '"
		 * +idqg+"'";
		 * 
		 * 
		 * String value=SciBS.eqnull(sess.createSQLQuery(idsql).list()); strmap2 +=
		 * "{name:\""+nameqg+"\", value:"+value+"},"; }
		 * 
		 * }else{ String
		 * asql="select area_name from dt_area where area_parent_id='"+areaid+"'";
		 * List<HashMap<String, Object>> listc= cq.getListBySQL(asql);
		 * if(listc!=null&&listc.size()>0){ for(int z=0;z<listc.size();z++){
		 * HashMap<String, Object> mapz=listc.get(z); String namez=(String)
		 * mapz.get("area_name"); strmap2 += "{name:\""+namez+"\", value:\"0\"},"; } } }
		 * strmap2=strmap2.substring(0,strmap2.length()-1); strmap2 += "]"; }else{
		 * 
		 * idsql="select j1 from "+values+" where ROWSID = '1' and  unitid = '"+id+"'";
		 * idsql="select j2 from "+values+" where ROWSID = '1' and  unitid = '"+idqg+
		 * "'";
		 * idsql="select j3 from "+values+" where ROWSID = '1' and  unitid = '"+idqg+
		 * "'"; idsql="select sum(j1)-sum(j4) from "
		 * +values+" where ROWSID = '1' and  unitid = '"+idqg+"'";
		 * idsql="select sum(j2+j3+j4+j5+j6+j7+j8+j9+j10+j11+j12) from "
		 * +values+" where ROWSID = '1' and  unitid = '"+idqg+"'";
		 * idsql="select sum(j2+j3+j4+j5+j6+j7+j8+j9+j10+j11+j12+j13+j14+j15+j16+j17) from "
		 * +values+" where ROWSID = '1' and  unitid = '"+idqg+"'";
		 * idsql="select sum(j2) from "
		 * +values+" where (ROWSID = '3'or ROWSID = '4' or ROWSID = '5' or ROWSID = '6' or ROWSID = '7' or ROWSID = '8'"
		 * +
		 * " or ROWSID = '9' or ROWSID = '10' or ROWSID = '11' or ROWSID = '12') and  unitid = '"
		 * +idqg+"'";
		 * 
		 * String value=SciBS.eqnull(sess.createSQLQuery(idsql).list()); strmap2 +=
		 * "{name:\""+name+"\", value:"+value+"},"; } }
		 * strmap2=strmap2.substring(0,strmap2.length()-1); strmap2 += "]";
		 * this.getExecuteSG().addExecuteCode("showMap("+strmap2+",'100000');");
		 * 
		 * 
		 * }else{ String
		 * asql="select area_name from dt_area where area_parent_id='"+areaid+"'";
		 * List<HashMap<String, Object>> listc= cq.getListBySQL(asql);
		 * if(listc!=null&&listc.size()>0){ for(int i=0;i<listc.size();i++){
		 * HashMap<String, Object> map=listc.get(i); String name=(String)
		 * map.get("name"); strmap2 += "{name:\""+name+"\", value:\"0\"},"; }
		 * strmap2=strmap2.substring(0,strmap2.length()-1); strmap2 += "]"; }
		 * 
		 * 
		 * } }else{ areaid = ""; }
		 * 
		 * } } catch (AppException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (RadowException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * //女少非..........--------------------------------------------------------------
		 * ----------------------------------------------- Map<String,Object> map = new
		 * HashMap<String,Object>(); String sql =
		 * "select names,zs,nv,shao,fei,bk,sss,sw,nvcj from eletemp where unitid='"
		 * +cityid+"'"; String nvli="";String shaoli="";String feili="";String
		 * bkli="";String ssli="";String swli="";String nvcjli=""; List<HashMap<String,
		 * Object>> list= cq.getListBySQL(sql); String names =
		 * list.get(0).get("names")==null?"":list.get(0).get("names").toString();
		 * if(names.equals("0")){ names="无机构"; } String zs =
		 * list.get(0).get("zs")==null?"":list.get(0).get("zs").toString(); String nvs =
		 * list.get(0).get("nv")==null?"":list.get(0).get("nv").toString();String
		 * nvb=eqxs(zs, nvs);int nvi=Integer.valueOf(nvs); String nv=eqw(nvs); String
		 * shaos =
		 * list.get(0).get("shao")==null?"":list.get(0).get("shao").toString();String
		 * shaob=eqxs(zs, shaos);int shaoi=Integer.valueOf(shaos); String
		 * shao=eqw(shaos); String feis =
		 * list.get(0).get("fei")==null?"":list.get(0).get("fei").toString();String
		 * feib=eqxs(zs, feis);int feii=Integer.valueOf(feis); String fei=eqw(feis);
		 * String bks =
		 * list.get(0).get("bk")==null?"":list.get(0).get("bk").toString();String
		 * bkb=eqxs(zs, bks);int bki=Integer.valueOf(bks); String bk=eqw(bks); String
		 * sss =
		 * list.get(0).get("sss")==null?"":list.get(0).get("sss").toString();String
		 * ssb=eqxs(zs, sss);int ssi=Integer.valueOf(sss); String ss=eqw(sss); String
		 * sws = list.get(0).get("sw")==null?"":list.get(0).get("sw").toString();String
		 * swb=eqxs(zs, sws);int swi=Integer.valueOf(sws); String sw=eqw(sws); String
		 * nvcjs =
		 * list.get(0).get("nvcj")==null?"":list.get(0).get("nvcj").toString();String
		 * nvcjb=eqxs(zs, nvcjs);int nvcji=Integer.valueOf(nvcjs); String
		 * nvcj=eqw(nvcjs);
		 * 
		 * 
		 * if(nvi<10000){ nvli="女性<span>"+nv+"</span>人,占<span>"+nvb+"</span>"; }else{
		 * nvli="女性<span>"+nv+"</span>万人,占<span>"+nvb+"</span>"; } if(shaoi<10000){
		 * shaoli="少数民族<span>"+shao+"</span>人,占<span>"+shaob+"</span>"; }else{
		 * shaoli="少数民族<span>"+shao+"</span>万人,占<span>"+shaob+"</span>"; }
		 * if(feii<10000){ feili="非中共党员<span>"+fei+"</span>人,占<span>"+feib+"</span>";
		 * }else{ feili="非中共党员<span>"+fei+"</span>万人,占<span>"+feib+"</span>"; }
		 * if(bki<10000){ bkli="本科以上<span>"+bk+"</span>人,占<span>"+bkb+"</span>"; }else{
		 * bkli="本科以上<span>"+bk+"</span>万人,占<span>"+bkb+"</span>"; } if(ssi<10000){
		 * ssli="30岁以下<span>"+ss+"</span>人,占<span>"+ssb+"</span>"; }else{
		 * ssli="30岁以下<span>"+ss+"</span>万人,占<span>"+ssb+"</span>"; } if(swi<10000){
		 * swli="35岁以下<span>"+sw+"</span>人,占<span>"+swb+"</span>"; }else{
		 * swli="35岁以下<span>"+sw+"</span>万人,占<span>"+swb+"</span>"; } if(nvcji<10000){
		 * nvcjli="处级以上女性<span>"+nvcj+"</span>人,占<span>"+nvcjb+"</span>"; }else{
		 * nvcjli="处级以上女性<span>"+nvcj+"</span>万人,占<span>"+nvcjb+"</span>"; }
		 * 
		 * 
		 * //女少非end---------------------------------------------------------------------
		 * ----------------------------------------------------
		 * 
		 * 
		 * 
		 * 
		 * //学历 String strxl=SciBS.getxl(cityid,tjtype,data,type);
		 * 
		 * //学历 String strxw=SciBS.getxw(cityid,tjtype,data,type);
		 * 
		 * 
		 * //各职务年龄层次 String strzw=SciBS.zjnlin(cityid,zjtype,type);
		 * 
		 * 
		 * 
		 * //人数变化 String strrs=SciBS.rsin(cityid); //任现职务层次年限 String
		 * strnx=SciBS.nxin(cityid,tjtype,data,type);
		 * 
		 * //年龄情况分析 String strnl=SciBS.nlin(cityid); String mc=names; String
		 * nsf=strmap2+"&"+nvli+"&"+shaoli+"&"+feili+"&"+bkli+"&"+ssli+"&"+swli+"&"+
		 * nvcjli+"&"+strzw+"&"+strnx+"&"+strnl+"&"+zs+"&"+mc+"&"+areaid+"&"+strxl+"&"+
		 * strxw+"&"+strrs;
		 * 
		 * 
		 * PrintWriter out = null; InputStream in = null; try { out =
		 * response.getWriter(); out.write(nsf);
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }finally{ out.flush();
		 * if(in!=null){ try { in.close(); } catch (IOException e) {
		 * e.printStackTrace(); } } if(out!=null){ out.close(); } }
		 */}

	public void findPerson(HttpServletRequest request, HttpServletResponse response) throws RadowException {
		/*
		 * String cityid = request.getParameter("cityid");// 机构编码 String type =
		 * request.getParameter("type");// 套表类型 公务员 参公 群团 String name = (String)
		 * request.getParameter("name");// 图标条件 Map data = new HashMap(); try { data =
		 * SciBS.getPageData(cityid, type, name); // StringBuffer sb = new
		 * StringBuffer(); //
		 * sb.append("{'totalCount':'").append(data.get("totalCount")).append("',"); //
		 * sb.append("'recordList':").append(data.get("recordList")).append("}"); //
		 * System.out.println(JSONObject.fromObject(data).toString());
		 * response.getWriter().print(JSONObject.fromObject(data).toString()); //
		 * this.doSuccess(request, "success", data); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */}

	public void changeRadio(HttpServletRequest request, HttpServletResponse response) throws RadowException {
		/*
		 * String tjtype = request.getParameter("tjtype"); String ditype =
		 * request.getParameter("ditype"); String zjtype =
		 * request.getParameter("zjtype"); String cityid =
		 * request.getParameter("cityid"); String data = request.getParameter("year");
		 * String type = request.getParameter("type"); String radiotype =
		 * request.getParameter("radiotype"); String values = ""; String valuenl = "";
		 * String areaid = ""; String areasql = ""; String strmap2 = ""; String idsql =
		 * ""; CommQuery cq = new CommQuery(); HBSession sess = HBUtil.getHBSession();
		 * SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式 String
		 * datestr = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳 try { if
		 * ("zt".equals(type)) {
		 * 
		 * String citysql =
		 * " select id from dt_area where area_name in (select b.b0101 from zwhzyq.b01 b where b.b0111 = '"
		 * + cityid + "')"; // List<HashMap<String, Object>> lista=
		 * sess.getListBySQL(citysql); List<HashMap<String, Object>> lista =
		 * cq.getListBySQL(citysql);
		 * 
		 * if (lista != null && lista.size() > 0) { areaid = lista.get(0).get("id") ==
		 * null ? "" : lista.get(0).get("id").toString(); if (areaid.equals("100000")) {
		 * areasql =
		 * "select b.b0101,b.b0111 from zwhzyq.b01 b where b.b0101 in (select area_name from dt_area where id ='"
		 * + areaid + "')"; } else { areasql =
		 * "select b.b0101,b.b0111 from zwhzyq.b01 b where b.b0101 in (select area_name from dt_area where area_parent_id='"
		 * + areaid + "')";
		 * 
		 * }
		 * 
		 * List<HashMap<String, Object>> listb = cq.getListBySQL(areasql); //
		 * {name:"浙江省", value:102012}, strmap2 = "["; if (listb != null && listb.size()
		 * > 0) { for (int i = 0; i < listb.size(); i++) {
		 * 
		 * HashMap<String, Object> map = listb.get(i); String name = (String)
		 * map.get("b0101"); String id = (String) map.get("b0111"); if
		 * (name.equals("全国")) { String bsql =
		 * "select b.b0111,b.b0101 from  zwhzyq.b01 b where b.b0101 in (select area_name from dt_area where area_parent_id='"
		 * + areaid + "')"; List<HashMap<String, Object>> listqg =
		 * cq.getListBySQL(bsql); if (listqg != null && listqg.size() > 0) { for (int j
		 * = 0; j < listqg.size(); j++) { HashMap<String, Object> mapqg = listqg.get(j);
		 * String idqg = (String) mapqg.get("b0111"); String nameqg = (String)
		 * mapqg.get("b0101"); if (radiotype.equals("gwyr")) { idsql =
		 * "select count(A0000) from zwhzyq.a01 a where a.A0163 = '1' and a.A0160 = '1'  and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')"; } else if (radiotype.equals("nvr")) { idsql =
		 * "select count(A0000) from zwhzyq.a01 a where a.A0163 = '1' and a.A0160 = '1' and a.A0104 = '2' and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } else if (radiotype.equals("shaor")) { idsql =
		 * "select count(A0000) from zwhzyq.a01  a where a.A0163 = '1' and a.A0160 = '1' and a.A0117 <> '01'  and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } else if (radiotype.equals("feir")) { idsql =
		 * "select count(A0000) from zwhzyq.a01 a where a.A0163 = '1' and a.A0160 = '1' and a.A0141 <> '01'  and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } else if (radiotype.equals("bkr")) { idsql =
		 * "select count(a.A0000) from zwhzyq.a01 a left join zwhzyq.a08 b on a.a0000=b.a0000 where a.A0163 = '1' and a.A0160 = '1' and b.A0834 = '1' and (substr(b.A0801B,1,1)='1' or  substr(b.A0801B,1,1)='2') and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } else if (radiotype.equals("ssr")) { idsql =
		 * "select count(A0000) from zwhzyq.a01 a where a.A0163 = '1' and a.A0160 = '1' and GET_BIRTHDAY( a.a0107,'"
		 * + datestr +
		 * "')<=30 and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } else if (radiotype.equals("swr")) { idsql =
		 * "select count(A0000) from zwhzyq.a01 a where a.A0163 = '1' and a.A0160 = '1' and GET_BIRTHDAY( a.a0107,'"
		 * + datestr +
		 * "') <=35 and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } else if (radiotype.equals("cjr")) { idsql =
		 * "select count(a.A0000) from zwhzyq.a01 a left join zwhzyq.a05 b on a.a0000=b.a0000 where a.A0163 = '1' and a.A0160 = '1' and  a.A0104 = '2'  and b.A0524 = '1' and b.A0531 = '0' and substr(b.A0501B,1,1)='1'  and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } String value = SciBS.eqnull(sess.createSQLQuery(idsql).list()); strmap2 +=
		 * "{name:\"" + nameqg + "\", value:" + value + "},"; }
		 * 
		 * } else { String asql = "select area_name from dt_area where area_parent_id='"
		 * + areaid + "'"; List<HashMap<String, Object>> listc = cq.getListBySQL(asql);
		 * if (listc != null && listc.size() > 0) { for (int z = 0; z < listc.size();
		 * z++) { HashMap<String, Object> mapz = listc.get(z); String namez = (String)
		 * mapz.get("area_name"); strmap2 += "{name:\"" + namez + "\", value:\"0\"},"; }
		 * } }
		 * 
		 * strmap2 = strmap2.substring(0, strmap2.length() - 1); strmap2 += "]"; } else
		 * { if (radiotype.equals("gwyr")) { idsql =
		 * "select count(A0000) from zwhzyq.a01 a where a.A0163 = '1' and a.A0160 = '1'  and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')"; } else if (radiotype.equals("nvr")) { idsql =
		 * "select count(A0000) from zwhzyq.a01 a where a.A0163 = '1' and a.A0160 = '1' and a.A0104 = '2' and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } else if (radiotype.equals("shaor")) { idsql =
		 * "select count(A0000) from zwhzyq.a01  a where a.A0163 = '1' and a.A0160 = '1' and a.A0117 <> '01'  and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } else if (radiotype.equals("feir")) { idsql =
		 * "select count(A0000) from zwhzyq.a01 a where a.A0163 = '1' and a.A0160 = '1' and a.A0141 <> '01'  and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } else if (radiotype.equals("bkr")) { idsql =
		 * "select count(a.A0000) from zwhzyq.a01 a left join zwhzyq.a08 b on a.a0000=b.a0000 where a.A0163 = '1' and a.A0160 = '1' and b.A0834 = '1' and (substr(b.A0801B,1,1)='1' or  substr(b.A0801B,1,1)='2') and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } else if (radiotype.equals("ssr")) { idsql =
		 * "select count(A0000) from zwhzyq.a01 a where a.A0163 = '1' and a.A0160 = '1' and GET_BIRTHDAY( a.a0107,'"
		 * + datestr +
		 * "')<=30 and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } else if (radiotype.equals("swr")) { idsql =
		 * "select count(A0000) from zwhzyq.a01 a where a.A0163 = '1' and a.A0160 = '1' and GET_BIRTHDAY( a.a0107,'"
		 * + datestr +
		 * "') <=35 and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } else if (radiotype.equals("cjr")) { idsql =
		 * "select count(a.A0000) from zwhzyq.a01 a left join zwhzyq.a05 b on a.a0000=b.a0000 where a.A0163 = '1' and a.A0160 = '1' and  a.A0104 = '2'  and b.A0524 = '1' and b.A0531 = '0' and substr(b.A0501B,1,1)='1'  and exists (select a0000 from zwhzyq.a02 c where c.a0000=a.a0000 and c.a0201b like '"
		 * + id + "%')";
		 * 
		 * } // idsql="select count(A0000) from zwhzyq.a01 a where a.A0163 = '1' and
		 * a.A0160 // = '1' and exists (select a0000 from zwhzyq.a02 c where
		 * c.a0000=a.a0000 and // c.a0201b like '"+id+"%')"; String value =
		 * SciBS.eqnull(sess.createSQLQuery(idsql).list()); strmap2 += "{name:\"" + name
		 * + "\", value:" + value + "},"; } } strmap2 = strmap2.substring(0,
		 * strmap2.length() - 1); strmap2 += "]";
		 * this.getExecuteSG().addExecuteCode("showMap("+strmap2+",'100000');");
		 * 
		 * } else { String asql = "select area_name from dt_area where area_parent_id='"
		 * + areaid + "'"; List<HashMap<String, Object>> listc = cq.getListBySQL(asql);
		 * if (listc != null && listc.size() > 0) { for (int i = 0; i < listc.size();
		 * i++) { HashMap<String, Object> map = listc.get(i); String name = (String)
		 * map.get("name"); strmap2 += "{name:\"" + name + "\", value:\"0\"},"; }
		 * strmap2 = strmap2.substring(0, strmap2.length() - 1); strmap2 += "]"; }
		 * 
		 * } } else { areaid = ""; }
		 * 
		 * } else { if ("2018".equals(data)) { if (tjtype.equals("gwy")) { values =
		 * "J1801106"; valuenl = "J1801108"; } else if (tjtype.equals("csqt")) { values
		 * = "J1802046"; valuenl = "j1802047"; } else { values = "J1803102"; valuenl =
		 * "j1803104"; } } else if ("2017".equals(data)) { if (tjtype.equals("gwy")) {
		 * values = "J1703105"; valuenl = "j1703107"; } else if (tjtype.equals("csqt"))
		 * { values = "J1701046"; valuenl = "j1701047"; } else { values = "J1702054";
		 * valuenl = "j1702055"; } } else if ("2016".equals(data)) { if
		 * (tjtype.equals("gwy")) { values = "J1603091"; valuenl = "j1603093"; } else if
		 * (tjtype.equals("csqt")) { values = "J1601046"; valuenl = "j1601047"; } else {
		 * values = "J1602054"; valuenl = "j1602055"; } }
		 * 
		 * String citysql =
		 * " select id from dt_area where area_name in (select name from tj_un where id = '"
		 * + cityid + "' and RPTYEAR = '" + data + "')"; // List<HashMap<String,
		 * Object>> lista= sess.getListBySQL(citysql); List<HashMap<String, Object>>
		 * lista = cq.getListBySQL(citysql);
		 * 
		 * if (lista != null && lista.size() > 0) { areaid = lista.get(0).get("id") ==
		 * null ? "" : lista.get(0).get("id").toString(); if (areaid.equals("100000")) {
		 * areasql =
		 * "select name,id from tj_un where name in (select area_name from dt_area where id ='"
		 * + areaid + "')"; } else { areasql =
		 * "select name,id from tj_un where name in (select area_name from dt_area where area_parent_id='"
		 * + areaid + "')";
		 * 
		 * }
		 * 
		 * List<HashMap<String, Object>> listb = cq.getListBySQL(areasql); //
		 * {name:"浙江省", value:102012}, strmap2 = "["; if (listb != null && listb.size()
		 * > 0) { for (int i = 0; i < listb.size(); i++) { HashMap<String, Object> map =
		 * listb.get(i); String name = (String) map.get("name"); String id = (String)
		 * map.get("id"); if (name.equals("全国")) { String bsql =
		 * "select id,name from  tj_un where name in (select area_name from dt_area where area_parent_id='"
		 * + areaid + "')"; List<HashMap<String, Object>> listqg =
		 * cq.getListBySQL(bsql); if (listqg != null && listqg.size() > 0) { for (int j
		 * = 0; j < listqg.size(); j++) { HashMap<String, Object> mapqg = listqg.get(j);
		 * String idqg = (String) mapqg.get("id"); String nameqg = (String)
		 * mapqg.get("name"); if (radiotype.equals("gwyr")) { idsql = "select j1 from "
		 * + values + " where ROWSID = '1' and  unitid = '" + idqg + "'"; } else if
		 * (radiotype.equals("nvr")) { idsql = "select j2 from " + values +
		 * " where ROWSID = '1' and  unitid = '" + idqg + "'";
		 * 
		 * } else if (radiotype.equals("shaor")) { idsql = "select j3 from " + values +
		 * " where ROWSID = '1' and  unitid = '" + idqg + "'";
		 * 
		 * } else if (radiotype.equals("feir")) { idsql = "select sum(j1)-sum(j4) from "
		 * + values + " where ROWSID = '1' and  unitid = '" + idqg + "'";
		 * 
		 * } else if (radiotype.equals("bkr")) { idsql = "select sum(j9+j10) from " +
		 * values + " where ROWSID = '1' and unitid = '" + idqg + "'";
		 * 
		 * } else if (radiotype.equals("ssr")) { idsql =
		 * "select sum(j2+j3+j4+j5+j6+j7+j8+j9+j10+j11+j12) from " + valuenl +
		 * " where ROWSID = '1' and  unitid = '" + idqg + "'";
		 * 
		 * } else if (radiotype.equals("swr")) { idsql =
		 * "select sum(j2+j3+j4+j5+j6+j7+j8+j9+j10+j11+j12+j13+j14+j15+j16+j17) from " +
		 * valuenl + " where ROWSID = '1' and  unitid = '" + idqg + "'";
		 * 
		 * } else if (radiotype.equals("cjr")) { idsql = "select sum(j2) from " + values
		 * +
		 * " where (ROWSID = '3'or ROWSID = '4' or ROWSID = '5' or ROWSID = '6' or ROWSID = '7' or ROWSID = '8'"
		 * +
		 * " or ROWSID = '9' or ROWSID = '10' or ROWSID = '11' or ROWSID = '12') and  unitid = '"
		 * + idqg + "'";
		 * 
		 * } //
		 * idsql="select j1 from "+values+" where ROWSID = '1' and unitid = '"+idqg+"'";
		 * 
		 * idsql="select j2 from "+values+" where ROWSID = '1' and  unitid = '"+idqg+
		 * "'";
		 * idsql="select j3 from "+values+" where ROWSID = '1' and  unitid = '"+idqg+
		 * "'"; idsql="select sum(j1)-sum(j4) from "
		 * +values+" where ROWSID = '1' and  unitid = '"+idqg+"'";
		 * idsql="select sum(j2+j3+j4+j5+j6+j7+j8+j9+j10+j11+j12) from "
		 * +values+" where ROWSID = '1' and  unitid = '"+idqg+"'";
		 * idsql="select sum(j2+j3+j4+j5+j6+j7+j8+j9+j10+j11+j12+j13+j14+j15+j16+j17) from "
		 * +values+" where ROWSID = '1' and  unitid = '"+idqg+"'";
		 * idsql="select sum(j2) from "
		 * +values+" where (ROWSID = '3'or ROWSID = '4' or ROWSID = '5' or ROWSID = '6' or ROWSID = '7' or ROWSID = '8'"
		 * +
		 * " or ROWSID = '9' or ROWSID = '10' or ROWSID = '11' or ROWSID = '12') and  unitid = '"
		 * +idqg+"'";
		 * 
		 * 
		 * String value = SciBS.eqnull(sess.createSQLQuery(idsql).list()); strmap2 +=
		 * "{name:\"" + nameqg + "\", value:" + value + "},"; }
		 * 
		 * } else { String asql = "select area_name from dt_area where area_parent_id='"
		 * + areaid + "'"; List<HashMap<String, Object>> listc = cq.getListBySQL(asql);
		 * if (listc != null && listc.size() > 0) { for (int z = 0; z < listc.size();
		 * z++) { HashMap<String, Object> mapz = listc.get(z); String namez = (String)
		 * mapz.get("area_name"); strmap2 += "{name:\"" + namez + "\", value:\"0\"},"; }
		 * } } strmap2 = strmap2.substring(0, strmap2.length() - 1); strmap2 += "]"; }
		 * else { if (radiotype.equals("gwyr")) { idsql = "select j1 from " + values +
		 * " where ROWSID = '1' and  unitid = '" + id + "'"; } else if
		 * (radiotype.equals("nvr")) { idsql = "select j2 from " + values +
		 * " where ROWSID = '1' and  unitid = '" + id + "'";
		 * 
		 * } else if (radiotype.equals("shaor")) { idsql = "select j3 from " + values +
		 * " where ROWSID = '1' and  unitid = '" + id + "'";
		 * 
		 * } else if (radiotype.equals("feir")) { idsql = "select sum(j1)-sum(j4) from "
		 * + values + " where ROWSID = '1' and  unitid = '" + id + "'";
		 * 
		 * } else if (radiotype.equals("bkr")) { idsql = "select sum(j9+j10) from " +
		 * values + " where ROWSID = '1' and unitid = '" + id + "'";
		 * 
		 * } else if (radiotype.equals("ssr")) { idsql =
		 * "select sum(j2+j3+j4+j5+j6+j7+j8+j9+j10+j11+j12) from " + valuenl +
		 * " where ROWSID = '1' and  unitid = '" + id + "'";
		 * 
		 * } else if (radiotype.equals("swr")) { idsql =
		 * "select sum(j2+j3+j4+j5+j6+j7+j8+j9+j10+j11+j12+j13+j14+j15+j16+j17) from " +
		 * valuenl + "  where ROWSID = '1' and  unitid = '" + id + "'";
		 * 
		 * } else if (radiotype.equals("cjr")) { idsql = "select sum(j2) from " + values
		 * +
		 * " where (ROWSID = '3'or ROWSID = '4' or ROWSID = '5' or ROWSID = '6' or ROWSID = '7' or ROWSID = '8'"
		 * +
		 * " or ROWSID = '9' or ROWSID = '10' or ROWSID = '11' or ROWSID = '12') and  unitid = '"
		 * + id + "'";
		 * 
		 * } //
		 * idsql="select j1 from "+values+" where ROWSID = '1' and unitid = '"+id+"'";
		 * 
		 * idsql="select j2 from "+values+" where ROWSID = '1' and  unitid = '"+idqg+
		 * "'";
		 * idsql="select j3 from "+values+" where ROWSID = '1' and  unitid = '"+idqg+
		 * "'"; idsql="select sum(j1)-sum(j4) from "
		 * +values+" where ROWSID = '1' and  unitid = '"+idqg+"'";
		 * idsql="select sum(j2+j3+j4+j5+j6+j7+j8+j9+j10+j11+j12) from "
		 * +values+" where ROWSID = '1' and  unitid = '"+idqg+"'";
		 * idsql="select sum(j2+j3+j4+j5+j6+j7+j8+j9+j10+j11+j12+j13+j14+j15+j16+j17) from "
		 * +values+" where ROWSID = '1' and  unitid = '"+idqg+"'";
		 * idsql="select sum(j2) from "
		 * +values+" where (ROWSID = '3'or ROWSID = '4' or ROWSID = '5' or ROWSID = '6' or ROWSID = '7' or ROWSID = '8'"
		 * +
		 * " or ROWSID = '9' or ROWSID = '10' or ROWSID = '11' or ROWSID = '12') and  unitid = '"
		 * +idqg+"'";
		 * 
		 * 
		 * String value = SciBS.eqnull(sess.createSQLQuery(idsql).list()); strmap2 +=
		 * "{name:\"" + name + "\", value:" + value + "},"; } } strmap2 =
		 * strmap2.substring(0, strmap2.length() - 1); strmap2 += "]";
		 * this.getExecuteSG().addExecuteCode("showMap("+strmap2+",'100000');");
		 * 
		 * } else { String asql = "select area_name from dt_area where area_parent_id='"
		 * + areaid + "'"; List<HashMap<String, Object>> listc = cq.getListBySQL(asql);
		 * if (listc != null && listc.size() > 0) { for (int i = 0; i < listc.size();
		 * i++) { HashMap<String, Object> map = listc.get(i); String name = (String)
		 * map.get("name"); strmap2 += "{name:\"" + name + "\", value:\"0\"},"; }
		 * strmap2 = strmap2.substring(0, strmap2.length() - 1); strmap2 += "]"; }
		 * 
		 * } } else { areaid = ""; }
		 * 
		 * } String sql = "select names from eletemp where unitid='" + cityid + "'";
		 * List<HashMap<String, Object>> list = cq.getListBySQL(sql); String names = "";
		 * if (list != null && list.size() > 0) { names = list.get(0).get("names") ==
		 * null ? "" : list.get(0).get("names").toString(); if (names.equals("0")) {
		 * names = "无机构"; } } else {
		 * 
		 * }
		 * 
		 * String mc = names; String nsf = strmap2 + "&" + areaid + "&" + mc;
		 * PrintWriter out = null; InputStream in = null; try { out =
		 * response.getWriter(); out.write(nsf);
		 * 
		 * } catch (IOException e) { e.printStackTrace(); } finally { out.flush(); if
		 * (in != null) { try { in.close(); } catch (IOException e) {
		 * e.printStackTrace(); } } if (out != null) { out.close(); } } } catch
		 * (AppException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */}

	public void changeZJ(HttpServletRequest request, HttpServletResponse response) throws AppException {
		/*
		 * CommQuery cq = new CommQuery(); HBSession sess = HBUtil.getHBSession();
		 * String tjtype = request.getParameter("tjtype"); String ditype =
		 * request.getParameter("ditype"); String zjtype =
		 * request.getParameter("zjtype"); String cityid =
		 * request.getParameter("cityid"); String data = request.getParameter("year");
		 * String type = request.getParameter("type"); try { SciBS.ifnull(cityid,
		 * tjtype, ditype, zjtype, data, type); } catch (AppException e) {
		 * e.printStackTrace(); } catch (RadowException e) { e.printStackTrace(); }
		 * 
		 * String str = ""; if (zjtype.equals("jc")) { str = SciBS.jcin(cityid, zjtype);
		 * } else if (zjtype.equals("fj")) { str = SciBS.fgin(cityid, zjtype); } else {
		 * str = SciBS.zjnlin(cityid, zjtype, type); }
		 * 
		 * PrintWriter out = null; InputStream in = null; try { out =
		 * response.getWriter(); out.write(str);
		 * 
		 * } catch (IOException e) { e.printStackTrace(); } finally { out.flush(); if
		 * (in != null) { try { in.close(); } catch (IOException e) {
		 * e.printStackTrace(); } } if (out != null) { out.close(); } }
		 * 
		 */}

	/**
	 * 处理百分比方法
	 * 
	 * @param list
	 * @return
	 */
	public String eqxs(String zs, String fs) {
		String percent = "";
		BigDecimal aBD = new BigDecimal(zs);
		BigDecimal bBD = new BigDecimal(fs);
		if (zs.equals("0")) {
			DecimalFormat df = new DecimalFormat("0%");
			BigDecimal d = new BigDecimal("0");
			percent = df.format(d);

		} else {
			BigDecimal resultBD = bBD.divide(aBD, 2, java.math.BigDecimal.ROUND_HALF_UP);
			String result = resultBD.toString();
			DecimalFormat df = new DecimalFormat("0%");
			BigDecimal d = new BigDecimal(result);
			percent = df.format(d);
		}

		return percent;
	}

	/**
	 * 处理小数方法
	 * 
	 * @param list
	 * @return
	 */
	public String eqxx(String zs, String fs) {
		BigDecimal aBD = new BigDecimal(zs);
		BigDecimal bBD = new BigDecimal(fs);
		BigDecimal resultBD = bBD.divide(aBD, 2, java.math.BigDecimal.ROUND_HALF_UP);
		String result = resultBD.toString();
		DecimalFormat df = new DecimalFormat("0%");
		BigDecimal d = new BigDecimal(result);
		String percent = df.format(d);
		String xs = percent.replace("%", "");
		return xs;
	}

	/**
	 * 换算成以万为单位的数
	 * 
	 * @param result
	 * @return
	 */
	public String eqw(String result) {
		int a = Integer.valueOf(getString(result));
		if (a >= 10000) {
			BigDecimal aBD = new BigDecimal(result);
			BigDecimal bBD = new BigDecimal("10000");
			BigDecimal resultBD = aBD.divide(bBD, 4, java.math.BigDecimal.ROUND_HALF_UP);
			String re = resultBD.toString();
			return re;
		} else {
			return result;
		}

	}

	/**
	 * 处理取数中含有小数点的值
	 * 
	 * @param str
	 * @return
	 */
	public String getString(String str) {
		if (str.indexOf(".") > -1) {
			str = str.substring(0, str.indexOf("."));
		}
		return str;
	}

}
