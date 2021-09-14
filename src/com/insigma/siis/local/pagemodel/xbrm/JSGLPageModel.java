package com.insigma.siis.local.pagemodel.xbrm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.utils.SQLiteUtil;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.xbrm.expword.ExpJSGLRMB;
import com.utils.CommonQueryBS;

import sun.misc.BASE64Encoder;

public class JSGLPageModel extends PageModel {
	
	JSGLBS bs = new JSGLBS();
	/**
	 * ������Ϣ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String rb_name1 = this.getPageElement("rb_name1").getValue();
		String rb_date1 = this.getPageElement("rb_date1").getValue();
		String where = "";
		if(rb_name1!=null&&!"".equals(rb_name1)){
			where += " and t.rb_name like '%"+rb_name1+"%'";
		}
		if(rb_date1!=null&&!"".equals(rb_date1)){
			where += " and t.rb_date like '%"+rb_date1+"%'";
		}
		String sql="select t.* from RECORD_BATCH t where t.rb_userid='"+SysManagerUtils.getUserId()+"' "+where+" order by rb_sysno desc";
		//String sql="select t.*,case when (select count(a0000) from js01 t1 where t1.rb_id=t.rb_id and nvl(t1.js0121,'2')!='1')>0 then 2 else 1 end status from RECORD_BATCH t where t.rb_userid='"+SysManagerUtils.getUserId()+"' "+where+" order by rb_sysno desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("allDelete")
	public int allDelete(String rb_id) throws RadowException, AppException{
		
		try {
			HBSession sess = HBUtil.getHBSession();
			bs.setPm(this);
			bs.deletePersonInfoByRB_ID(rb_id,sess);
			//ɾ������Ŀ¼
			//�����ļ���
			String directory_name = "zhgbuploadfiles/";
			//hzbĿ¼
			String disk_path = JSGLBS.HZBPATH;
			//�����ļ���
			String rb_path = rb_id+"/";
			//����Ŀ¼
			String directory_rb_path = disk_path + directory_name + rb_path;
			File file = new File(directory_rb_path);
			if(file.isDirectory()){
				JSGLBS.deleteDirectory(directory_rb_path);
			}
			sess.flush();
			this.getExecuteSG().addExecuteCode("$('#rb_id').val(''); $('#rb_name').val('');");
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
	@SuppressWarnings("unchecked")
	public static void downloadshanghuicailiao(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = JSGLBS.initOutPut(response);
		try {
			HBSession sess = HBUtil.getHBSession();
			String rb_id = request.getParameter("rb_id");
			String cur_hj_4 = request.getParameter("cur_hj_4");
			RecordBatch rb = (RecordBatch)sess.get(RecordBatch.class	, rb_id);
			if(rb==null){
				JSGLBS.outPrintlnErr(out, "���β����ڡ�");
				return;
			}
			String rb_name = rb.getRbName();
			
			
			
			JSGLBS.outPrintln(out, "��ʼִ��...");
			//�����ļ���
			String directory_name = rb_name+"("+rb_id+")/";
			//hzbĿ¼
			String disk_path = JSGLBS.HZBPATH + "zhgbshanghui/";
			//�����ļ���
			String rb_path = rb_id+"/";
			//����Ŀ¼
			String directory_rb_path = disk_path + rb_path;
			//����zipĿ¼
			String zip_directory_path = directory_rb_path + directory_name;
			
			File file_zip_directory = new File(directory_rb_path);
			//���zipĿ¼
			if(file_zip_directory.isDirectory()){
				JSGLBS.deleteDirectory(directory_rb_path);
			}
			//zipĿ¼�е�����Ŀ¼
			String zip_directory_rb_path = zip_directory_path + rb_path;
			File file_zip_directory_rb_path = new File(zip_directory_rb_path);
			//������Ŀ¼
			if(!file_zip_directory_rb_path.isDirectory()){
				file_zip_directory_rb_path.mkdirs();
			}
			File dbSourceFile=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/mdata.db").getPath());
			File photoSourceFile=new File(JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/xbrm/photo.db").getPath());
			
			//��Ա��Ϣdb
			String dbFilename = zip_directory_path+"/mdata.db";
			//��Ƭ��Ϣdb
			String photoFilename = zip_directory_path+"/photo.db";
			File dbTargetFile=new File(dbFilename);
			SQLiteUtil.copyFile(dbSourceFile, dbTargetFile);
			//��Ƭdb
			File photoTargetFile=new File(photoFilename);
			SQLiteUtil.copyFile(photoSourceFile, photoTargetFile);
			if(!dbTargetFile.isFile()){
				JSGLBS.outPrintlnErr(out, "db�ļ�����ʧ�ܣ�������ֹ��");
				return;
			}
			String wheresql = "where a0000 in (select a0000 from js01,js_hj where "
					+ " js01.js0100=js_hj.js0100 and js_type='"+cur_hj_4+"' and rb_id='"+rb_id+"')";
			OracleToSqlite o2s = new OracleToSqlite(dbFilename,out,wheresql);
			o2s.importTable("A01");
			o2s.importTable("A02");
			o2s.importTable("A05");
			o2s.importTable("A08");
			o2s.importTable("A11");
			o2s.importTable("A14");
			o2s.importTable("A15");
			o2s.importTable("A36");
			o2s.setWheresql(null);
			String b01sql = 
			"select distinct {*} "+
			"  from b01 t "+
			" start with t.b0111 in "+
			"            (select t.b0111 "+
			"               from b01 t "+
			"              where t.b0111 in "+
			"                    (select a0201b "+
			"                       from a02 "+
			"                      where a0000 in "+
			"                            (select a0000 "+
			"                               from js01 "+
			"                              where rb_id = "+
			"                                    '"+rb_id+"') "+
			"                     union all "+
			"                     select a0195 "+
			"                       from a01 "+
			"                      where a0000 in "+
			"                            (select a0000 "+
			"                               from js01 "+
			"                              where rb_id = "+
			"                                    '"+rb_id+"'))) "+
			"connect by prior t.b0121 = t.b0111 ";
			o2s.setSql(b01sql);
			
			o2s.importTable("B01");
			o2s.setSql(null);
			o2s.setWheresql("where js0100 in (select js01.js0100 from js01,js_hj where "
					+ " js01.js0100=js_hj.js0100 and js_type='"+cur_hj_4+"' and rb_id='"+rb_id+"')");
			o2s.importTable("JS01");
			o2s.importTable("JS_ATT");
			o2s.importTable("JS02");
			o2s.importTable("JS03");
			o2s.importTable("JS04");
			o2s.importTable("JS05");
			o2s.importTable("JS06");
			o2s.importTable("JS07");
			o2s.importTable("JS08");
			o2s.importTable("JS09");
			o2s.importTable("JS10");
			o2s.importTable("JS11");
			o2s.setWheresql("where js0100 in (select js01.js0100 from js01,js_hj where "
					+ " js01.js0100=js_hj.js0100 and js_type='"+cur_hj_4+"' and rb_id='"+rb_id+"') and js_type='"+cur_hj_4+"'");
			o2s.importTable("JS_HJ");
			o2s.setWheresql("");
			o2s.importTable("RECORD_BATCH");
			o2s.importTable("DEPLOY_CLASSIFY");
			o2s.importTable("JS_DW");
			o2s.update();
			JSGLBS.outPrintlnSuc(out, "��������ɡ�");
			JSGLBS.outPrintln(out, "��ʼǨ�Ƹ���...");
			
			List<JsAtt> jsattlist = sess.createSQLQuery("select * from Js_Att where js0100 in (select js01.js0100 from js01,js_hj where "
					+ " js01.js0100=js_hj.js0100 and js_type='"+cur_hj_4+"' and rb_id='"+rb_id+"')").addEntity(JsAtt.class).list();
			String attpath = JSGLBS.HZBPATH + "zhgbuploadfiles/"+rb_path;
			//Ǩ�Ƹ���
			if(jsattlist!=null){
				for(int i=0; i<jsattlist.size(); i++){
					JsAtt js = jsattlist.get(i);
					//����
					File attSourceFile = new File(attpath+js.getJsa00());
					if(attSourceFile.isFile()){
						//���Ƶ�zipĿ¼��
						String attFile_directory = zip_directory_rb_path+js.getJs0100();
						File attTarget_directory_file = new File(attFile_directory);
						if(!attTarget_directory_file.isDirectory()){
							attTarget_directory_file.mkdir();
						}
						File attTargetFile = new File(attTarget_directory_file+"/"+js.getJsa04());
						SQLiteUtil.copyFile(attSourceFile, attTargetFile);
						JSGLBS.outPrintlnSuc(out, "Ǩ�Ƹ�����"+js.getJsa04());
					}
				}
			}
			JSGLBS.outPrintlnSuc(out, "Ǩ�Ƹ�����ɡ�");
			JSGLBS.outPrintln(out, "��ʼǨ����Ƭ...");
			List<A57> a57list = sess.createSQLQuery("select * from A57 where a0000 in (select a0000 from js01,js_hj where "
					+ " js01.js0100=js_hj.js0100 and js_type='"+cur_hj_4+"' and rb_id='"+rb_id+"')").addEntity(A57.class).list();
			//�л���Ƭdb
			o2s.setPath(photoFilename);
			String insertSql = "insert into A57_BASE64S(a0000,PHOTONAME,PHOTOBASE64) values(?,?,?)";
			List<Object[]> listArgs = new ArrayList<Object[]>();
			if(a57list!=null){
				for(int i=0; i<a57list.size(); i++){
					A57 a57 = a57list.get(i);
					String photourl = a57.getPhotopath();
					File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
					if(fileF.isFile()){
						long nLen = fileF.length();
						int nSize = (int) nLen;
						byte[] data = new byte[nSize];
						FileInputStream inStream = new FileInputStream(fileF); 
						inStream.read(data);
						inStream.close();
						String base64Str=new BASE64Encoder().encodeBuffer(data);
						Object[] args = new Object[]{a57.getA0000(),a57.getPhotoname(),base64Str};
						listArgs.add(args);
					}
				}
				o2s.writeSqlite(insertSql, listArgs);
			}
			JSGLBS.outPrintlnSuc(out, "Ǩ����Ƭ��ɡ�");
			JSGLBS.outPrintln(out, "���������...");
			List<Js01> Js01list = sess.createSQLQuery("select * from Js01 where js0100 in (select js01.js0100 from js01,js_hj,a01 where "
					+ " js01.js0100=js_hj.js0100 and a01.a0000=js01.a0000 and js_type='"+cur_hj_4+"' and rb_id='"+rb_id+"')").addEntity(Js01.class).list();
			ExpJSGLRMB.getPdfsByA000s_aspose(Js01list, "eebdefc2-4d67-4452-a973-5f7939530a11","word",zip_directory_rb_path);
			JSGLBS.outPrintln(out, "�����������ɡ�");
			
			
			JSGLBS.outPrintln(out, "��ʼѹ��zip��...");
			String zip_output_name = rb.getRbName()+new SimpleDateFormat("yyyyMMdd_HH��mm��ss").format(new Date());
			Zip7z.zip7Z(directory_rb_path, directory_rb_path+zip_output_name, null);
			JSGLBS.outPrintlnSuc(out, "ѹ����ɡ�");
			//JSGLBS.outPrintlnErr(out, "���������");
			String downloadUUID = UUID.randomUUID().toString();
			String zipfilepath = directory_rb_path+zip_output_name+".zip";
			request.getSession().setAttribute(downloadUUID, new String[]{zipfilepath,zip_output_name+".zip"});
			JSGLBS.outDownZip(out,downloadUUID);
			JSGLBS.endOutPut(out);
		} catch (Exception e) {
			JSGLBS.outPrintlnErr(out, "��������"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	@PageEvent("leadsuggest")
	public void leadsugg(String text) throws RadowException{
		String rbid=this.getPageElement("rb_id").getValue();
		if(StringUtil.isEmpty(rbid)){
			this.setMainMessage("û��ѡ������!");
		}
		if("1".equals(text) || "2".equals(text)){
			try {
				HBUtil.executeUpdate("update record_batch set RB_LEADVIEW=? where RB_ID=?", new Object[]{text,rbid});
				this.setNextEventName("memberGrid.dogridquery");
				this.setMainMessage("����ɹ�!");
			} catch (Exception e) {
				e.printStackTrace();
				this.setMainMessage("����ʧ��!");
			}
		}else{
			this.setMainMessage("�������ݲ���ȷ!");
		}
		
	}
	
	@PageEvent("leadsuggWin")
	public int leadsuggWin(String text) throws RadowException{
		String rbid=this.getPageElement("rb_id").getValue();
		HBSession sess = HBUtil.getHBSession();
		try {
			RecordBatch rb = (RecordBatch) sess.get(RecordBatch.class, rbid);
			if(rb.getRbapprove()!=null && rb.getRbapprove().equals("1")) {
				this.getExecuteSG().addExecuteCode("spwin();");
			} else {
				this.setMainMessage("�����β�����ǰ���棬����Ҫ��ˣ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("��ȡ�����쳣��");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("statuss")
	public int statuss() throws RadowException{
		String rbId = this.request.getParameter("rbId");
		String sql="select a0000 from js01 where rb_id='"+rbId+"' and nvl(js0121,'2')!='1'";
		CommonQueryBS cq=new CommonQueryBS();
		try {
			List<HashMap<String, Object>> list = cq.getListBySQL(sql);
			if(list.size()>0){
	            this.setSelfDefResData("{'iswc':'false'}");
	            return EventRtnType.XML_SUCCESS;
			}else{
				this.setSelfDefResData("{'iswc':'true'}");
	            return EventRtnType.XML_SUCCESS;
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("rbmSent")
	public int rbmSent(String rb_id) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			rb_id =  this.getParameter("rb_id");
			String rbmDept = this.getParameter("rbmDept");
			RecordBatch rb = (RecordBatch) sess.get(RecordBatch.class, rb_id);
			if(rb.getRbmStatus()==null || rb.getRbmStatus().equals("") 
					|| rb.getRbmStatus().equals("0")) {
				rb.setRbmStatus("1");
				rb.setRbmDept(rbmDept);
				sess.update(rb);
				sess.flush();
				//this.setMainMessage("����ɹ���");
				this.getExecuteSG().addExecuteCode("realParent.odin.alert(\"����ɹ���\");realParent.radow.doEvent('memberGrid.dogridquery');window.close();");
			} else {
				this.setMainMessage("�˼�¼�Ѿ�������ϲ��������²�ѯ��");
				this.getExecuteSG().addExecuteCode("alert(\"�˼�¼�Ѿ�������ϲ��������²�ѯ��\");");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("������Ϣ�쳣��");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("rbmCancel")
	public int rbmCancel(String rb_id) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			RecordBatch rb = (RecordBatch) sess.get(RecordBatch.class, rb_id);
			if(rb.getRbmStatus()!=null && rb.getRbmStatus().equals("1")) {
				rb.setRbmStatus("0");
				rb.setRbmDept("");
				sess.update(rb);
				sess.flush();
				this.setMainMessage("�����ɹ���");
				this.getExecuteSG().addExecuteCode("radow.doEvent('memberGrid.dogridquery');");
			} else if(rb.getRbmStatus()!=null && rb.getRbmStatus().equals("2")) {
				this.setMainMessage("�˼�¼�Ѻϲ������ܳ�����");
			} else {
				this.setMainMessage("�˼�¼���Ƿ�����״̬�������²�ѯ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("������Ϣ�쳣��");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
