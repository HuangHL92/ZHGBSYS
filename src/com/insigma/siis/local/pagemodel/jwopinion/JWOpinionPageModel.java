package com.insigma.siis.local.pagemodel.jwopinion;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fr.stable.core.UUID;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.JwBatch;
import com.insigma.siis.local.business.entity.JwOpinion;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.util.DateUtil;

import net.sf.json.JSONArray;

public class JWOpinionPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("JWBatchGrid.dogridquery");
		return 0;
	}

	
	@PageEvent("addBatch")
	public int addBatch(String batchno) {
		if(StringUtil.isEmpty(batchno)) {
			this.setMainMessage("请填写批次号");
			return EventRtnType.FAILD;
		}
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String time=DateUtil.formatDateStr(new Date(), DateUtil.PARTTEN_DATE);
		HBSession sess = HBUtil.getHBSession();
		List list2=sess.createSQLQuery("select * from jw_batch where batchno='"+batchno+"'").list();
		if(list2!=null&&list2.size()>0) {
			this.setMainMessage("批次号已存在");
			return EventRtnType.FAILD;
		}
		String batchid=UUID.randomUUID().toString();
		JwBatch jb=new JwBatch();
		jb.setBatchno(batchno);
		jb.setId(batchid);
		jb.setStatus("1");
		jb.setCreatetime(time);
		jb.setCreator(user.getId());
		jb.setDeleteflag("0");
		sess.save(jb);
		sess.flush();
		this.setMainMessage("新增完成");
		this.setNextEventName("JWBatchGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("JWBatchGrid.dogridquery")
	@NoRequiredValidate
	public int JWBatchGridQuery(int start, int limit) throws RadowException {
		String sql = " select * from jw_batch "
				+ " where deleteflag='0'";
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("JWOpinionGrid.dogridquery")
	@NoRequiredValidate
	public int JWOpinionGridQuery(int start, int limit) throws RadowException {
		String batchid=this.getPageElement("batchid").getValue();
		String sql = " select * from jw_checkopinion"
				+ " where batchid='"+batchid+"'";
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	@PageEvent("JWBatchGrid.rowdbclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	public int dbClickRowVerifyRuleGrid() throws RadowException{
		Grid grid = (Grid)this.getPageElement("JWBatchGrid");
		String batchid = grid.getValue("id").toString();
		String batchno = grid.getValue("batchno").toString();
		String status = grid.getValue("status").toString();
		this.getPageElement("batchid").setValue(batchid);
		this.getPageElement("status").setValue(status);
		this.getExecuteSG().addExecuteCode("changeh1('"+batchno+"')");
		this.setNextEventName("JWOpinionGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteBatch")
	public int deleteBatch(String id) throws AppException {
		HBUtil.executeUpdate("update jw_batch set deleteflag='1' where id='"+id+"'");
		this.setMainMessage("删除成功");
		this.setNextEventName("JWBatchGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteOpinion")
	public int deleteOpinion(String id) throws AppException {
		HBUtil.executeUpdate("delete from jw_checkopinion  where oid='"+id+"'");
		this.setMainMessage("删除成功");
		this.setNextEventName("JWOpinionGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("seekOpinion")
	public int seekOpinion(String id) {
		HBSession sess = HBUtil.getHBSession();
		JwBatch jb=(JwBatch) sess.get(JwBatch.class, id);
		List<JwOpinion> list=sess.createSQLQuery("select * from jw_checkopinion where batchid='"+jb.getId()+"'").addEntity(JwOpinion.class).list();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		if(list==null||list.size()<1) {
			this.setMainMessage("无人员信息");
			return EventRtnType.FAILD;
		}
		
		JSONArray jsonarr=JSONArray.fromObject(list);
		
		
		//请求地址需要根据实际情况修改
		//AppConfig.GBJD_ADDR="127.0.0.1:8081/gbdjd";
		String url="http://"+AppConfig.GBJD_ADDR+"/ProblemDownServlet?method=gbjwopinion";
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("person", jsonarr.toString());
		map.put("batchno", jb.getBatchno());
		map.put("batchid", jb.getId());
		map.put("username", user.getLoginname());
		try {
			
			String result=net(url, map);
			
			if("接收成功".equals(result.trim())) {
				jb.setStatus("2");
				sess.save(jb);
				sess.flush();
				this.setNextEventName("JWBatchGrid.dogridquery");
			}
			this.setMainMessage(result);
		} catch (Exception e) {
			this.setMainMessage("发送失败");
			e.printStackTrace();
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("setType")
	public int sendtojds(String type) throws AppException, RadowException {
		
		List<HashMap<String, Object>> list = this.getPageElement("JWOpinionGrid").getValueList();
		int countNum = 0;
		StringBuffer oids = new StringBuffer();
		
		for (int j = 0; j < list.size(); j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("personcheck");
			if (check1 != null && check1.equals(true)) {
				
					oids.append("'").append(map.get("oid") == null ? "" : map.get("oid").toString()).append("',");// 被勾选的人员编号组装，用“，”分隔
					
					countNum++;
				
				
			}
		}
		if (countNum == 0) {
			throw new AppException("请勾选人员！");
		}
		if (oids == null || oids.toString().trim().equals("")) {
			throw new AppException("数据获取异常！");
		}
		
		HBUtil.executeUpdate("update jw_checkopinion set type='"+type+"' where oid " + " in ("
				+ oids.toString().substring(0, oids.length() - 1) + ")");
		this.setNextEventName("JWOpinionGrid.dogridquery");
		this.setMainMessage("设置成功");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String listStr) throws AppException, RadowException {
		String batchid=this.getPageElement("batchid").getValue();
		HBSession sess = HBUtil.getHBSession();
		listStr = listStr.substring(1, listStr.length() - 1);
		listStr = "'"+listStr.replaceAll(", ", "','")+"'";
		String sql="select a0000,a0101,a0184,a0192 from A01_TPHJ where a0000 in ("+listStr+")";
		CommQuery cqbs = new CommQuery();
		List<HashMap<String, Object>> list = cqbs.getListBySQL(sql);
		for (HashMap m : list) {
			String psnkey=(String) m.get("a0000");
			String psnname=(String) m.get("a0101");
			String duty=(String) m.get("a0192");
			String idcard=(String) m.get("a0184");
			String sql2="select * from jw_checkopinion where batchid='"+batchid+"' and psnkey='"+psnkey+"'";
			List isExists=sess.createSQLQuery(sql2).list();
			if(isExists==null||isExists.size()==0) {
				JwOpinion jo=new JwOpinion();
				jo.setBatchid(batchid);
				jo.setDuty(duty);
				jo.setIdcard(idcard);
				jo.setPsnkey(psnkey);
				jo.setPsnname(psnname);
				jo.setOid(UUID.randomUUID().toString());
				sess.save(jo);
				sess.flush();
			}
		}
		
		this.setNextEventName("JWOpinionGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	public static String net(String strUrl, Map<String,String> params) throws Exception {
		String result=""; 
		// 创建默认的httpClient实例.  
		         CloseableHttpClient httpclient = HttpClients.createDefault();
		         // 创建httppost  
		         HttpPost httppost = new HttpPost(strUrl);
		        // 创建参数队列  
		         List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		        formparams.add(new BasicNameValuePair("person", params.get("person")));
		        formparams.add(new BasicNameValuePair("batchno", params.get("batchno")));
		        formparams.add(new BasicNameValuePair("batchid", params.get("batchid")));
		        formparams.add(new BasicNameValuePair("username", params.get("username")));
		         UrlEncodedFormEntity uefEntity;
		         try {
		             uefEntity = new UrlEncodedFormEntity(formparams, "GBK");
		             httppost.setEntity(uefEntity);
		            System.out.println("executing request " + httppost.getURI());
		            CloseableHttpResponse response = httpclient.execute(httppost);
		             try {
		                HttpEntity entity = response.getEntity();
		                 if (entity != null) {
					/*
					 * System.out.println("--------------------------------------");
					 * System.out.println("Response content: " +
					 * URLDecoder.decode(EntityUtils.toString(entity), "GBK"));
					 * System.out.println("--------------------------------------");
					 */
		                     result=URLDecoder.decode(EntityUtils.toString(entity), "GBK");
		                 }
		             } finally {
		                response.close();
		             }
		         } catch (ClientProtocolException e) {
		        	 result="请求出错";
		        	 e.printStackTrace();
		         } catch (UnsupportedEncodingException e1) {
		        	 result="请求出错";
		        	 e1.printStackTrace();
		         } catch (IOException e) {
		        	 result="请求出错";
		        	 e.printStackTrace();
		         } finally {
		             // 关闭连接,释放资源  
		             try {
		                httpclient.close();
		             } catch (IOException e) {
		                e.printStackTrace();
		             }
		         }
		
		
		return result;
	}
	
}
