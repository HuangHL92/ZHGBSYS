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
		// 1 读取模板
		Document doc = new Document(templatePath + "/"
				+ "EmployeesReportDemo.doc");
		String imagePath = templatePath + "/" + "employees.jpg";
		// 2 填充数据源
		doc.getMailMerge().executeWithRegions(
				new MapMailMergeDataSource(getMapList(imagePath), "Employees"));
		return doc;
	}

	private List<Map<String, Object>> getMapList(String imagePath)
			throws IOException {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		// 读取一个二进制图片
		FileInputStream fis = new FileInputStream(imagePath);
		byte[] image = new byte[fis.available()];
		fis.read(image);
		fis.close();

		for (int i = 0; i < 20; i++) {
			Map<String, Object> record = new HashMap<String, Object>();
			// 这里的key要与模板中的<<xxxxx>>对应
			record.put("FirstName", "欧阳");
			record.put("LastName", "夏丹");
			record.put("Title", "个人简历导出Word PDF");
			record.put("Address", "中国 北京市 东城区");
			record.put("City", "北京");
			record.put("Country", "辽宁沈阳");
			// 二进制数据
			record.put("PhotoBLOB", image);
			dataList.add(record);
		}
		return dataList;
	}
	
	
	public static void main(String[] args) throws Exception {  
        //获取模板路径  
        String doctpl = "C:/Users/wuhao/Desktop/ks/csaspose.doc";  
        //获取到的路径是：file://E....所以需要截取  
        //doctpl=doctpl.substring(6);  
        String imagePath = doctpl+"ani.jpg";  
        System.out.println(doctpl);  
        System.out.println(imagePath);  
          
        test1(doctpl);  
    }
	
	private static void test1(String doctpl) throws Exception{  
        //创建Document对象，读取Word模板  
        Document doc = new Document(doctpl);
        //DocumentBuilder builder = new DocumentBuilder(doc);
        //HandleMergeFieldFromBlob hb = new HandleMergeFieldFromBlob();
        String imagePath = doctpl+"ani.jpg";
        //增加处理简历程序
        doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());//setFieldMergingCallback(new HandleMergeFieldFromBlob());
        //向模板填充数据源  
        doc.getMailMerge().executeWithRegions(new MapMailMergeDataSource(getMapList2(imagePath), "expert"));  
        //填充单个数据  
        doc.getMailMerge().execute(new String[]{"judgeGroupName","projectCount"}, new String[]{"很好","10"});  
        //写入到Word文档中  
          
        ByteArrayOutputStream os = new ByteArrayOutputStream();  
        //保存到输出流中  
        doc.save(os, SaveFormat.DOC);  
        OutputStream out = new FileOutputStream("C:/Users/wuhao/Desktop/ks/show.doc");  
        out.write(os.toByteArray());  
        out.close();  
    }
	
	
	
	private static List<Map<String, Object>> getMapList2(String imagePath) throws Exception{  
        List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();  
        //读取一个二进制图片    
//        FileInputStream fis = new FileInputStream(imagePath);    
//        byte[] image = new byte[fis.available()];    
//        fis.read(image);    
//        fis.close();   
        for (int i = 0; i < 20; i++) {    
            Map<String, Object> record = new HashMap<String, Object>();    
            //这里的key要与模板中的<<xxxxx>>对应    
            record.put("lineNo", i);    
            record.put("personName", "夏丹");    
            record.put("sexName", "男");    
            record.put("age", "20");    
            record.put("companyName", "美髯公");   
//            //二进制数据    
//            record.put("img", image);    
//            record.put("mobile", "13051292503");  
//            record.put("academician", "测试成功");   
            dataList.add(record);    
        }    
        return dataList;
	}
	
	private static class HandleMergeFieldFromBlob implements IFieldMergingCallback {
	    public void fieldMerging(FieldMergingArgs args) throws Exception {
	    	 if (args.getDocumentFieldName().equals("companyName"))
	            {
	                // 使用DocumentBuilder处理简历
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


