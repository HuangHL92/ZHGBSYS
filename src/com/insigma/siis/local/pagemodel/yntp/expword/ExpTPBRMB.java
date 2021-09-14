package com.insigma.siis.local.pagemodel.yntp.expword;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hsqldb.lib.StringUtil;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FieldMergingArgs;
import com.aspose.words.IFieldMergingCallback;
import com.aspose.words.ImageFieldMergingArgs;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.demo.entity.BbUtils;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.pagemodel.exportexcel.FiledownServlet;
import com.insigma.siis.local.pagemodel.xbrm.JSGLPageModel;

public class ExpTPBRMB {
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
	public static List getPdfsByA000s_aspose(List<Object[]> a0000s,String tpid,String type, String zip_directory_rb_path, String nORM) throws AppException {
		if (!getLicense()) { // ��֤License ������֤�����ɵ�word�ĵ�����ˮӡ����
			return null;
		}
		String userid = SysUtil.getCacheCurrentUser().getId();
		String doctpl = "";
		Map<String, Object> dataMap = null;
		List<String> paths = new ArrayList<String>();
			//�ɲ�����������
			for (int i = 0; i < a0000s.size(); i++) {
				A01 a01 = null;
				Object[] tphj1 = a0000s.get(i);
				String a0000 = tphj1[0]==null?"":tphj1[0].toString();
				if(!"".equals(a0000)){
					a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
					if(a01==null||"4".equals(a01.getStatus())){
						continue;
					}
				}else{
					continue;
				}
				
				// ����Document���󣬶�ȡWordģ��
				String sqla53 = "from A53 where a0000='"+a0000+"' and a5399='"+userid+"'";
				HBSession sess = HBUtil.getHBSession();
				List<A53> list = sess.createQuery(sqla53).list();
				
				try {
					dataMap = FiledownServlet.getMap(a0000,tpid);
					if(list.size()>0&&!"NORM".equals(nORM)){
						A53 a53 = list.get(0);
						
						dataMap.put("js0111", a53.getA5304()==null?"":a53.getA5304());
						dataMap.put("js0117", a53.getA5315()==null?"":a53.getA5315());
						dataMap.put("RMLY", a53.getA5317()==null?"":a53.getA5317());
					}else{
						dataMap.put("js0111", "");
						dataMap.put("js0117", "");
						dataMap.put("RMLY", "");
					}
					dataMap.put("js0108", dataMap.get("a0192a"));//tphj1[1]==null?"":tphj1[1].toString()
					String a0101 = (String) dataMap.get("a0101");
					doctpl = JSGLPageModel.class.getClassLoader().getResource("./com/insigma/siis/local/pagemodel/yntp/tpl/gbrmspb_1.doc").getPath();
					if(doctpl.startsWith("/")){
						doctpl = doctpl.replaceFirst("/", "");
					}
					Document doc = new Document(doctpl);
					// ���Ӵ����������Ƭ����
					doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
					// ��ģ���������Դ
					// doc.getMailMerge().executeWithRegions(new MapMailMergeDataSource(getMapList2(a0000), "Employees"));
					StringBuffer mapkey = new StringBuffer();
					StringBuffer mapvalue = new StringBuffer();
					for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
						String key = entry.getKey();
						Object value = entry.getValue();
						if (key.equals("image")) {
							A57 a57 = (A57) HBUtil.getHBSession().get(A57.class,
									a0000);
							if (a57 != null) {
								byte[] image = PhotosUtil.getPhotoData(a57);
								if (image != null) {
									value = PhotosUtil.PHOTO_PATH
											+ a57.getPhotopath()
											+ a57.getPhotoname();
								} else {
									value = "";
								}
							} else {
								value = "";
							}
						}
						if(StringUtil.isEmpty(value+"")){
	                        	value = "@";
						}
						mapkey = mapkey.append(key + "&");
						//ȥ��&�����ַ�
						value = value.toString().replaceAll("&", "");
						mapvalue = mapvalue.append(value + "&");
					}
					// �ı���
					String[] Flds = mapkey.toString().split("&");
					// ֵ
					String[] Vals = mapvalue.toString().split("&");
					for (int j = 0; j < Vals.length; j++) {
						if(Vals[j].equals("@")){
							Vals[j] = "";
						}
					}

					// ��䵥������
					doc.getMailMerge().execute(Flds, Vals);
					File outdf = new File(zip_directory_rb_path);
					if(!outdf.isDirectory()){
						outdf.mkdirs();
					}
					File outFile = new File(zip_directory_rb_path +String.format("%03d", i)+"�ɲ�����������_"+a0101 + ".doc");
					// д�뵽Word�ĵ���
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					// ���浽�������
					doc.save(os, SaveFormat.DOC);
					OutputStream out = new FileOutputStream(outFile);
					out.write(os.toByteArray());
					out.close();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		
		
		
		return paths;
	}
	
	public static void main(String[] args) {
		System.out.println(String.format("%03d", 3));
	}
	
	private static class HandleMergeFieldFromBlob implements IFieldMergingCallback {
	    public void fieldMerging(FieldMergingArgs args) throws Exception {
	    	 if (args.getDocumentFieldName().equals("a1701")){
	             // ʹ��DocumentBuilder�������
	             DocumentBuilder builder = new DocumentBuilder(args.getDocument());
	             builder.moveToMergeField(args.getFieldName());
	             BbUtils.formatArray(builder, args, true);
	         }else if(args.getDocumentFieldName().equals("image")){
	        	 String image = args.getFieldValue()==null?"":args.getFieldValue().toString();
	        	 if(!StringUtil.isEmpty(image)){
	        		 DocumentBuilder builder = new DocumentBuilder(args.getDocument());
		             builder.moveToMergeField(args.getFieldName());
		             builder.insertImage(args.getFieldValue().toString(),105,145); 
	        	 }
	         }else if (args.getDocumentFieldName().equals("zzxlxx")){//��ְѧ����Ϣ
	             // ʹ��DocumentBuilder�������
	             DocumentBuilder builder = new DocumentBuilder(args.getDocument());
	             builder.moveToMergeField(args.getFieldName());
	             BbUtils.formatArray(builder, args, false);
	         }else if (args.getDocumentFieldName().equals("a14z101")){//���������������
	             // ʹ��DocumentBuilder�������
	             DocumentBuilder builder = new DocumentBuilder(args.getDocument());
	             builder.moveToMergeField(args.getFieldName());
	             BbUtils.formatArray(builder, args, false);
	         }else if (args.getDocumentFieldName().equals("zzxwxx")){//��ְѧλ��Ϣ
	             // ʹ��DocumentBuilder�������
	             DocumentBuilder builder = new DocumentBuilder(args.getDocument());
	             builder.moveToMergeField(args.getFieldName());
	             BbUtils.formatArray(builder, args, false);
	         }else if (args.getDocumentFieldName().equals("qrzxlxx")){//ȫ����ѧ����Ϣ
	             // ʹ��DocumentBuilder�������
	             DocumentBuilder builder = new DocumentBuilder(args.getDocument());
	             builder.moveToMergeField(args.getFieldName());
	             BbUtils.formatArray(builder, args, false);
	         }else if (args.getDocumentFieldName().equals("qrzxwxx")){//ȫ����ѧλ��Ϣ
	             // ʹ��DocumentBuilder�������
	             DocumentBuilder builder = new DocumentBuilder(args.getDocument());
	             builder.moveToMergeField(args.getFieldName());
	             BbUtils.formatArray(builder, args, false);
	         }else if (args.getDocumentFieldName().equals("qrzxlxw")){
	             // ʹ��DocumentBuilder�������
	             DocumentBuilder builder = new DocumentBuilder(args.getDocument());
	             builder.moveToMergeField(args.getFieldName());
	             BbUtils.formatArray(builder, args, false);
	         }else if (args.getDocumentFieldName().equals("zzxlxw")){
	             // ʹ��DocumentBuilder�������
	             DocumentBuilder builder = new DocumentBuilder(args.getDocument());
	             builder.moveToMergeField(args.getFieldName());
	             BbUtils.formatArray(builder, args, false);
	         }
	    else if (args.getDocumentFieldName().equals("js0108")){
	    	// ʹ��DocumentBuilder�������
	    	DocumentBuilder builder = new DocumentBuilder(args.getDocument());
	    	builder.moveToMergeField(args.getFieldName());
	    	BbUtils.formatArray(builder, args, false);
	    }
	    	 
	    	 
	    	 
	    }

	    
	    
	    
	    public void imageFieldMerging(ImageFieldMergingArgs e) throws Exception {
	    	
	    }
	}
	
	
	public List<Map<String, Object>> groupListByCol(List<Map<String, Object>> list, String groupCol) {
		List<Map<String, Object>> groupList = new ArrayList<Map<String, Object>>();
		String tag = null;// �����ֶ�ֵ(ͬ���־�ֶ�ֵ)

		List<Map<String, Object>> groupsList = null;// ����list
		List<Map<String, Object>> nullList = new ArrayList<Map<String, Object>>();// �����ֶ�Ϊ�յ����ݷ���
		for (Map<String, Object> gm : list) {
			if (gm.get(groupCol) != null) {
				if (!gm.get(groupCol).toString().equals(tag)) {
					// ���ͬ���־�ֶ�ֵ��Ϊ�գ���������շ����б�
					if (tag != null) {
						Map<String, Object> fm = new HashMap<String, Object>();
						fm.put("tag", tag);
						fm.put("datas", groupsList);
						groupList.add(fm);
					}
					groupsList = new ArrayList<Map<String, Object>>();
				}

				groupsList.add(gm);
				// ���÷����־ֵ
				tag = gm.get(groupCol).toString();

			} else {
				// ��ӷ����ֶ�Ϊ�յ��б�
				nullList.add(gm);
			}

		}

		// �����һ����������б�
		Map<String, Object> fm = new HashMap<String, Object>();
		fm.put("tag", tag);
		fm.put("datas", groupsList);
		groupList.add(fm);

		if (nullList.size() > 0) {
			// ��ӷ����ֶ�Ϊ�յ��б�������б�
			Map<String, Object> um = new HashMap<String, Object>();
			um.put("tag", "�޷�����");
			um.put("datas", nullList);
			groupList.add(um);
		}
		
		return groupList;
	}
	
	
	
	
	/*public static void main(String[] args) throws AppException {
		//[8a5c554a-5287-4e2a-9bae-0a59a693ef1c, f75cf46c-5007-466d-91a9-1e8ca48b8f1c]
		//eebdefc2-4d67-4452-a973-5f7939530a11
		List<String> list2 = new ArrayList<String>();
		list2.add("8a5c554a-5287-4e2a-9bae-0a59a693ef1c");
		List<String> wordPaths = ExpJSGLRMB.getPdfsByA000s_aspose(list2,"eebdefc2-4d67-4452-a973-5f7939530a11","word");
		String zipPath = wordPaths.get(0);
		String infile = "";
		
		infile =zipPath.substring(0,zipPath.length()-1)+".zip" ;
	    SevenZipUtil.zip7z(zipPath, infile, null);
	}*/
	
	
}
