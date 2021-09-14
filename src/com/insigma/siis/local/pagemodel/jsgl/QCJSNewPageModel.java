package com.insigma.siis.local.pagemodel.jsgl;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.hdgf.streams.Stream;
import org.hibernate.Session;
import org.hsqldb.lib.StringUtil;

import com.JUpload.JUpload;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.SaveFormat;
import com.aspose.words.IFieldMergingCallback;
import com.aspose.words.License;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.bean.EventResBean;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.HzJs2Yntp;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.insigma.siis.local.pagemodel.xbrm.JSGLPageModel;
import com.insigma.siis.local.pagemodel.xbrm2.YNTPFileExportBS;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QCJSNewPageModel extends PageModel implements JUpload{
	
	JSGLBS bs = new JSGLBS();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		this.setNextEventName("gridcq.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX2")
	public int initX2() throws RadowException, AppException{
		String a0000 = this.getPageElement("a0000").getValue();
		String tp0100 = this.getPageElement("tp0100").getValue();
		String ynId = this.getPageElement("ynid").getValue();//����
		String yn_type = this.getPageElement("yn_type1").getValue();//����ʶ
		try {
			CommQuery cqbs=new CommQuery();
			String jcxxsql = "select tp0101,tp0106,tp0111 tp0107 from hz_TPHJ1 "
					+ " where a0000='"+a0000+"' and YN_ID='"+ynId+"' and TP0116='"+yn_type+"'";
			List<HashMap<String, Object>> listCode_=cqbs.getListBySQL(jcxxsql.toString());
			/*Object rm = listCode_.get(0).get("tp0107");
			String ren = "";
			if(rm!=null && !rm.toString().equals("")) {
				if(rm.toString().startsWith("��") && rm.toString().length()>1) {
					if(rm.toString().indexOf("����")>0) {
						ren = rm.toString().substring(1,rm.toString().indexOf("����"));
					} else {
						ren = rm.toString().substring(1);
					}
					listCode_.get(0).put("tp0107", ren);
				} else {
					listCode_.get(0).put("tp0107", ren);
				}
			} */
			this.getExecuteSG().addExecuteCode("setJCXX("+JSONArray.fromObject(listCode_).toString()+")");
			
			
			String sql = "select gzjsid,gzjs001,gzjs002,gzjs003,gzjs004,gzjs005,gzjs006,gzjs007,gzjs008,"
					+ "gzjs009,gzjs010,gzjs011,gzjs012,gzjs013,gzjs014,gzjs015,gzjs016,"
					+ "gzjs017,gzjs018 from GZJS where a0000='"+a0000+"' and YN_ID='"+ynId+"' ";
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
			JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
			this.getExecuteSG().addExecuteCode("initData("+updateunDataStoreObject.toString()+");");
			
			
			String spasql = "select jsf00 ,gzjsid,a0000 ,yn_id ,tp0100,tp0116,jsf01 ,jsf02 ,"
					+ "jsf03 ,jsf04 ,jsf05 ,jsf06  from GZJSFILE where "
					+ " A0000='" + a0000 + "' and YN_ID='"+ynId+"' ";
			List<HashMap<String, Object>> spalist=cqbs.getListBySQL(spasql.toString());
			Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
			map.put("sj01", new ArrayList<Map<String, String>>());
			map.put("sj02", new ArrayList<Map<String, String>>());
			map.put("sj03", new ArrayList<Map<String, String>>());
			map.put("sj04", new ArrayList<Map<String, String>>());
			map.put("sj05", new ArrayList<Map<String, String>>());
			map.put("sj06", new ArrayList<Map<String, String>>());
			map.put("sj07", new ArrayList<Map<String, String>>());
			map.put("sj08", new ArrayList<Map<String, String>>());
			map.put("sj09", new ArrayList<Map<String, String>>());
			map.put("sj10", new ArrayList<Map<String, String>>());
			map.put("sj11", new ArrayList<Map<String, String>>());
			map.put("sj12", new ArrayList<Map<String, String>>());
			if (spalist != null) {
				for (HashMap<String, Object> ta : spalist) {
					List<Map<String, String>> list_temp;
					Map<String, String> map_temp = new HashMap<String, String>();
					String type =ta.get("jsf06")!=null ?ta.get("jsf06").toString():"";
					if(map.containsKey(type)){
						list_temp = map.get(type);
					}else{
						list_temp = new ArrayList<Map<String, String>>();
					}
					map_temp.put("id", ta.get("jsf00")+"");
					map_temp.put("name", ta.get("jsf01")+"");
					map_temp.put("fileSize", ta.get("jsf05")+"");
					list_temp.add(map_temp);
					map.put(type, list_temp);
				}
				//����map
				for(Map.Entry<String, List<Map<String, String>>> entry : map.entrySet()){
					this.setFilesInfo(entry.getKey(), entry.getValue(), false);
				}
			}
			
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("gridcq.dogridquery")
	@AutoNoMask
	public int doMemberQuery(int start,int limit) throws RadowException{
		try {
			String ynId = this.getPageElement("ynid").getValue();//����
			String yn_type = this.getPageElement("yn_type1").getValue();//����ʶ
			String sql = "select tp0100, a0000, type, tp0101, tp0102, tp0103, tp0104,"
					+ " tp0105, tp0106, tp0107,tp0108,tp0109,tp0110, tp0111,tp0112,tp0113,tp0114,tp0115,tp0116"
					+ " from hz_TPHJ1 where yn_id='"+ynId+"' and tp0116='"+yn_type+"' and type='3' order by sortnum asc ";
			this.pageQuery(sql, "SQL", start, limit);
        	return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.SPE_SUCCESS;
		}
	}
	
	//����
	@SuppressWarnings("unchecked")
	@PageEvent("save")
	public int save(String a) throws Exception{
		String a0000 = this.getPageElement("a0000").getValue();
		String gzjsid = this.getPageElement("gzjsid").getValue();
		String UPDATE_FIELD = this.getPageElement("UPDATE_FIELD").getValue();
		String DATA = this.getPageElement("DATA").getValue();
		String tp0100 = this.getPageElement("tp0100").getValue();
		String ynId = this.getPageElement("ynid").getValue();//����
		String yn_type = this.getPageElement("yn_type1").getValue();//����ʶ
		
		DATA = DATA.replace("\n", "{/n}");
		JSONObject  ID_DATAINFOObject = JSONObject.fromObject(DATA);
		Map<String,String> DATAINFOMap = (Map<String,String>)ID_DATAINFOObject;
		
		JSONObject  ID_FIELDINFOObject = JSONObject.fromObject(UPDATE_FIELD);
		Map<String,String> FIELDINFOMap = (Map<String,String>)ID_FIELDINFOObject;
		
		
		HBSession sess = null;
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuilder sql = new StringBuilder("");
		
		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			
			
			//if(FIELDINFOMap.size()>0){}

			//����
			if(gzjsid==null||"".equals(gzjsid)){
				gzjsid = UUID.randomUUID().toString();
				sql.append("insert into GZJS( gzjsid,a0000,yn_id,tp0100,tp0116,");
				StringBuilder valuesql = new StringBuilder("values(?,?,?,?,?,");
				if(FIELDINFOMap.size()>0) {
					for(String f : FIELDINFOMap.keySet()){
						sql.append(f+",");
						valuesql.append("?,");
					}
				}
				
				sql.deleteCharAt(sql.length()-1);
				valuesql.deleteCharAt(valuesql.length()-1);
				sql.append(") ");
				sql.append(valuesql).append(")");
				System.out.println(sql);
				ps = conn.prepareStatement(sql.toString());
				
				int fidex = 1;
				ps.setString(fidex++, gzjsid);
				ps.setString(fidex++, a0000);
				ps.setString(fidex++, ynId);
				ps.setString(fidex++, tp0100);
				ps.setString(fidex++, yn_type);
				if(FIELDINFOMap.size()>0) {
					for(String f : FIELDINFOMap.keySet()){
						ps.setString(fidex++, textFormat(DATAINFOMap.get(f)));
					}
				}
				
				this.getExecuteSG().addExecuteCode("setGryp00('"+gzjsid+"');");
				if(FIELDINFOMap.size()>0) {
					ps.executeUpdate();
				}
			}else{//����
				sql.append("update GZJS set ");
				for(String f : FIELDINFOMap.keySet()){
					sql.append(f+"=?,");
				}
				sql.deleteCharAt(sql.length()-1);
				sql.append(" where gzjsid=? ");
				System.out.println(sql);
				ps = conn.prepareStatement(sql.toString());
				int fidex = 1;
				for(String f : FIELDINFOMap.keySet()){
					ps.setString(fidex, textFormat(DATAINFOMap.get(f)));
					fidex++;
				}
				ps.setString(fidex, gzjsid);
				if(FIELDINFOMap.size()>0) {
					ps.executeUpdate();
				}
			}
			
			ps.close();
			conn.commit();
		
			
			this.upLoadFile("sj01");
			this.upLoadFile("sj02");
			this.upLoadFile("sj03");
			this.upLoadFile("sj04");
			this.upLoadFile("sj05");
			this.upLoadFile("sj06");
			this.upLoadFile("sj07");
			this.upLoadFile("sj08");
			this.upLoadFile("sj09");
			this.upLoadFile("sj10");
			this.upLoadFile("sj11");
			this.upLoadFile("sj12");
			
			//this.getExecuteSG().addExecuteCode("radow.doEvent('initX2');");
		}catch (Exception e) {
			e.printStackTrace();
			try{
				if(conn!=null)
					conn.rollback();
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				//this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'����������Ϣ��','����ʧ��,"+e.getMessage()+"');");
				e.printStackTrace();
				throw new RadowException("����ʧ��:"+e.getMessage());
			}
			//this.getExecuteSG().addExecuteCode("parent.saveAlert(1,'����������Ϣ��','����ʧ��,"+e.getMessage()+"');");
			e.printStackTrace();
			throw new RadowException("����ʧ��:"+e.getMessage());
			//return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode("clearData();odin.alert('����ɹ���');");
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

	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// ����ļ�����
		String isfile = formDataMap.get("Filename");
		// �ж��Ƿ��ϴ��˸�����û���ϴ��򲻽����ļ�����
		if (isfile != null && !isfile.equals("")) {
			try {
				// ��ȡ����Ϣ
				FileItem fi = fileItem.get(0);
				DecimalFormat df = new DecimalFormat("#.00");
				String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
				// ����ļ�����1M����ʾM��С������ʾkb
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
	
	public static String  disk = JSGLBS.HZBPATH ;
	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// �����Ա��Ϣid
		String a0000 = formDataMap.get("a0000");
		String filename = formDataMap.get("Filename");
		String classAtt = formDataMap.get("fileid");
		
		String gzjsid = formDataMap.get("gzjsid");
		String tp0100 = formDataMap.get("tp0100");
		String ynId = formDataMap.get("ynid");//����
		String yn_type = formDataMap.get("yn_type1");//����ʶ
		
		String id = UUID.randomUUID().toString();
		String directory = "gzjsbfiles" + File.separator +a0000+ File.separator;
		String filePath = directory  + id;
		File f = new File(disk + directory);
		if(!f.isDirectory()){
			f.mkdirs();
		}
		HBSession sess = null;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			sess = HBUtil.getHBSession();
			conn = sess.connection();
			conn.setAutoCommit(false);
			
			String sql = "insert into GZJSFILE values(?,?,?,?,?, ?,?,?,?,?, ?,?)";
			System.out.println(sql);
			ps = conn.prepareStatement(sql.toString());
			int fidex = 1;
			ps.setString(fidex++, id);
			ps.setString(fidex++, gzjsid);
			ps.setString(fidex++, a0000);
			ps.setString(fidex++, ynId);
			ps.setString(fidex++, tp0100);
			ps.setString(fidex++, yn_type);
			
			ps.setString(fidex++, filename);
			ps.setString(fidex++, SysManagerUtils.getUserId());
			ps.setDate(fidex++, DateUtil.date2sqlDate(new Date()));
			ps.setString(fidex++, directory);
			ps.setString(fidex++, fileSize);
			ps.setString(fidex++, classAtt);
			
			fi.write(new File(disk + filePath));
			ps.executeUpdate();
			
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			try{
				if(conn!=null)
					conn.rollback();
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new RadowException("�ϴ�����ʧ�ܣ�");
		} finally {
			try{
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		return id;
	}

	@Override
	public String deleteFile(String id) {

		try {
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> list = sess.createSQLQuery("select JSF00,JSF04,JSF01 from GZJSFILE "
					+ " where JSF00 = '" +id + "'").list();
			if(list==null || list.size()==0){
				return null;//ɾ��ʧ��
			}
			Object[] arr = list.get(0);
			String directory = disk+arr[1];
			File f = new File(directory+id);
			if(f.isFile()){
				f.delete();
			}
			sess.createSQLQuery("delete from GZJSFILE "
					+ " where JSF00 = '" +id + "'").executeUpdate();
			sess.flush();
			
			return id;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	@PageEvent("expJISHIWord")
	public int expJISHIWord() throws Exception{
		try {
			if (!getLicense()) { // ��֤License ������֤�����ɵ�word�ĵ�����ˮӡ����
				return EventRtnType.FAILD;
			}
		
		//��ò���������
		String expDataArray=this.getPageElement("expdataarray").getValue();
		JSONArray arrayObject = JSONArray.fromObject(expDataArray);
		List<List<String>> dataList = (List<List<String>>)arrayObject; 
		String outputpath = YNTPFileExportBS.HZBPATH+"zhgboutputfiles/gbxbrygzjsb/";
		String doctpl = QCJSNewPageModel.class.getResource("./�ɲ�ѡ�����ù�����ʵ��.doc").getPath();
		//��ȡDocument����
        InputStream inps=new FileInputStream(new File(doctpl));
        Document doc = new Document(inps); 
		DocumentBuilder builder = new DocumentBuilder(doc);
		
		//��ѡ��
		List<String> checkeds=dataList.get(0);
		for(int tab1index=0;tab1index<checkeds.size();tab1index++) {
			String fldname="a"+0+"d"+tab1index;
			builder.moveToMergeField(fldname);
			if(checkeds.get(tab1index).equals("R")) {
				builder.getFont().setName("Wingdings 2");
				builder.write("R");
				builder.getFont().setSize(10);
			}
			else {
				builder.getFont().setName("Wingdings 2");
				builder.write("0");
				builder.getFont().setSize(10);
			}
		}
		//�����������������������
		StringBuffer mapkey = new StringBuffer();
		StringBuffer mapvalue = new StringBuffer();
		for (int i = 1; i < dataList.size(); i++) {
			List<String> tableData = dataList.get(i);
			for (int j = 0; j < tableData.size(); j++) {
				String value = tableData.get(j);
				String key = "a" + i + "d" + j;
				if (StringUtil.isEmpty(value + "")) {
					value = "@";
				}
				mapkey = mapkey.append(key + "&"); 
				// ȥ��&�����ַ�
				value = value.toString().replaceAll("&", "");
				mapvalue =mapvalue.append(value + "&");
			}
		} 
		//�ӱ�GZJSFILE��ȡ�ļ���
		String a0000 = this.getPageElement("a0000").getValue();
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		conn.setAutoCommit(false);
		String sql=null;
		String key=null;
		String value=null;
		for(int i=1;i<13;i++) {
			if(i<10) {
				key="sj0"+i;
				value=null;
				sql = "select JSF01 from GZJSFILE where JSF06='"+key+"'"+"and A0000='"+a0000+"'";
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ResultSet rs=ps.executeQuery();
				conn.commit();
				while(rs.next()){
			        value=rs.getString(1);
			    }
				if (StringUtil.isEmpty(value + "")||value==null) 
					value = "��";
				else
					value = "��";
				mapkey = mapkey.append(key + "&"); 
				// ȥ��&�����ַ�
				value = value.toString().replaceAll("&", "");
				mapvalue =mapvalue.append(value + "&");
			}
			else {
				key="sj"+i;
				value=null;
				sql = "select JSF01 from GZJSFILE where JSF06='"+key+"'"+"and A0000='"+a0000+"'";
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ResultSet rs=ps.executeQuery();
				conn.commit();
				while(rs.next()){
			        value=rs.getString(1);
			    }
				if (StringUtil.isEmpty(value + "")||value==null) 
					value = "��";
				else
					value = "��";
				mapkey = mapkey.append(key + "&"); 
				// ȥ��&�����ַ�
				value = value.toString().replaceAll("&", "");
				mapvalue =mapvalue.append(value + "&");
				}
		}
		
		// �ı���
		String[] flds =mapkey.toString().split("&"); 
		// ֵ
		String[] vals =mapvalue.toString().split("&");
		
		String name = vals[0];
		for (int j = 0; j < vals.length; j++) {
			if (vals[j].equals("@")) {
				vals[j] = "";
			}
		}
			// ��䵥������ 
			doc.getMailMerge().execute(flds, vals); 
			File outdf = new File(outputpath);
			if (!outdf.isDirectory()) {
				outdf.mkdirs();
			}
			String downloadName= "�ɲ�ѡ�����ù�����ʵ��_" + name + ".doc";
			String downloadPath=outputpath+downloadName;
			File outFile = new File(downloadPath); 
			if(outFile.exists()) {
				outFile.delete();
			}
			
			
			// д�뵽Word�ĵ���
			ByteArrayOutputStream os = new ByteArrayOutputStream(); 
			// ���浽�������
			doc.save(os, SaveFormat.DOC);
			OutputStream out = new FileOutputStream(outFile);
			out.write(os.toByteArray());
			out.close();
			String downloadUUID = UUID.randomUUID().toString();
			request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
			
		} catch (Exception e) { 
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�" + e.getMessage());
			return EventRtnType.FAILD;
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	public static boolean getLicense() {
		boolean result = false;
		try {
			InputStream is = TestAspose2Pdf.class.getClassLoader()
					.getResourceAsStream("Aspose.Words.lic"); // license.xmlӦ����..\WebRoot\WEB-INF\classes·����
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result = true;
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}