package com.insigma.siis.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FieldMergingArgs;
import com.aspose.words.IFieldMergingCallback;
import com.aspose.words.ImageFieldMergingArgs;
import com.aspose.words.SaveFormat;
import com.insigma.siis.demo.entity.BbUtils;

public class EmployeesReportDemo {
	public Document execute(String templatePath) throws Exception {
		// 1 ��ȡģ��
		Document doc = new Document(templatePath + "/"
				+ "EmployeesReportDemo.doc");
		String imagePath = templatePath + "/" + "employees.jpg";
		// 2 �������Դ
		doc.getMailMerge().executeWithRegions(
				new MapMailMergeDataSource(getMapList(imagePath), "Employees"));
		return doc;
	}

	private List<Map<String, Object>> getMapList(String imagePath)
			throws IOException {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		// ��ȡһ��������ͼƬ
		FileInputStream fis = new FileInputStream(imagePath);
		byte[] image = new byte[fis.available()];
		fis.read(image);
		fis.close();

		for (int i = 0; i < 20; i++) {
			Map<String, Object> record = new HashMap<String, Object>();
			// �����keyҪ��ģ���е�<<xxxxx>>��Ӧ
			record.put("FirstName", "ŷ��");
			record.put("LastName", "�ĵ�");
			record.put("Title", "���˼�������Word PDF");
			record.put("Address", "�й� ������ ������");
			record.put("City", "����");
			record.put("Country", "��������");
			// ����������
			record.put("PhotoBLOB", image);
			dataList.add(record);
		}
		return dataList;
	}
	
	
	public static void main(String[] args) throws Exception {  
        //��ȡģ��·��  
        String doctpl = "C:/Users/wuhao/Desktop/ks/csaspose.doc";  
        //��ȡ����·���ǣ�file://E....������Ҫ��ȡ  
        //doctpl=doctpl.substring(6);  
        String imagePath = doctpl+"ani.jpg";  
        System.out.println(doctpl);  
        System.out.println(imagePath);  
          
        test1(doctpl);  
    }
	
	private static void test1(String doctpl) throws Exception{  
        //����Document���󣬶�ȡWordģ��  
        Document doc = new Document(doctpl);
        //DocumentBuilder builder = new DocumentBuilder(doc);
        //HandleMergeFieldFromBlob hb = new HandleMergeFieldFromBlob();
        String imagePath = doctpl+"ani.jpg";
        //���Ӵ����������
        doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());//setFieldMergingCallback(new HandleMergeFieldFromBlob());
        //��ģ���������Դ  
        doc.getMailMerge().executeWithRegions(new MapMailMergeDataSource(getMapList2(imagePath), "expert"));  
        //��䵥������  
        doc.getMailMerge().execute(new String[]{"judgeGroupName","projectCount"}, new String[]{"�ܺ�","10"});  
        //д�뵽Word�ĵ���  
          
        ByteArrayOutputStream os = new ByteArrayOutputStream();  
        //���浽�������  
        doc.save(os, SaveFormat.DOC);  
        OutputStream out = new FileOutputStream("C:/Users/wuhao/Desktop/ks/show.doc");  
        out.write(os.toByteArray());  
        out.close();  
    }
	
	
	
	private static List<Map<String, Object>> getMapList2(String imagePath) throws Exception{  
        List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();  
        //��ȡһ��������ͼƬ    
//        FileInputStream fis = new FileInputStream(imagePath);    
//        byte[] image = new byte[fis.available()];    
//        fis.read(image);    
//        fis.close();   
        for (int i = 0; i < 20; i++) {    
            Map<String, Object> record = new HashMap<String, Object>();    
            //�����keyҪ��ģ���е�<<xxxxx>>��Ӧ    
            record.put("lineNo", i);    
            record.put("personName", "�ĵ�");    
            record.put("sexName", "��");    
            record.put("age", "20");    
            record.put("companyName", "���׹�");   
//            //����������    
//            record.put("img", image);    
//            record.put("mobile", "13051292503");  
//            record.put("academician", "���Գɹ�");   
            dataList.add(record);    
        }    
        return dataList;
	}
	
	private static class HandleMergeFieldFromBlob implements IFieldMergingCallback {
	    public void fieldMerging(FieldMergingArgs args) throws Exception {
	    	 if (args.getDocumentFieldName().equals("companyName"))
	            {
	                // ʹ��DocumentBuilder�������
	                DocumentBuilder builder = new DocumentBuilder(args.getDocument());
	                builder.moveToMergeField(args.getFieldName());
	                BbUtils.formatArray(builder, args, true);
	                
	            }
	    }

	    public void imageFieldMerging(ImageFieldMergingArgs e) throws Exception {
	        /*// The field value is a byte array, just cast it and create a stream on it.
	        ByteArrayInputStream imageStream = new ByteArrayInputStream((byte[]) e.getFieldValue());
	        // Now the mail merge engine will retrieve the image from the stream.
	        e.setImageStream(imageStream);*/
	    }
	}

}


