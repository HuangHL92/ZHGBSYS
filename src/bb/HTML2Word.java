package bb;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.aspose.cells.License;


public class HTML2Word {
	private static InputStream license;

	/**
	 * 
	 * @return
	 */
	public static boolean getLicense()
	{
		boolean result = false;
		try
		{
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			license = new FileInputStream(loader.getResource("license.xml").getPath());// 鍑瘉鏂囦欢
			License aposeLic = new License();
			aposeLic.setLicense(license);
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public static void exportWord(String content) throws Exception {
		
		getLicense();
		 //创建 POIFSFileSystem 对象  
        POIFSFileSystem poifs = new POIFSFileSystem();    
        //获取DirectoryEntry  
        DirectoryEntry directory = poifs.getRoot();    
        //创建输出流  
        OutputStream out = new FileOutputStream("D:\\资料\\html_to_word.doc");  
        try {  
        	
            //创建文档,1.格式,2.HTML文件输入流  
        	directory.createDocument("WordDocument",new FileInputStream("D:\\资料\\sh1.html")); 
        	
            //写入  
        	ByteArrayOutputStream output = new ByteArrayOutputStream();
            poifs.writeFilesystem(output);  
            
            ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
            
            
//            Document document= new Document(input);
//
//
//
//            //获取指定节
//
//            Section section = document.getSections().get(0);
//
//            //设置页边距
//
//            section.getPageSetup().getMargins().setTop(30f);
//            section.getPageSetup().getMargins().setBottom(30f);
//            section.getPageSetup().getMargins().setLeft(30f);
//            section.getPageSetup().getMargins().setRight(30f);
//            document.saveToFile(out, FileFormat.Docx);
            
            
          /*  Document document = new Document(new FileInputStream("D:\\资料\\html_to_word.docx"));
            
            DocumentBuilder builder = new DocumentBuilder(document);
            PageSetup ps = builder.getPageSetup();
            ps.setBottomMargin(99.25);
            ps.setTopMargin(99.25);
            ps.setLeftMargin(99.25);
            ps.setRightMargin(99.25);
            document.save(out,SaveFormat.DOC);*/
            //释放资源  
           // output.close();  
            poifs.close();
           // document.close();
            System.out.println("success");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }    
	}
	public static void main(String[] args) throws Exception {
		exportWord("");

	}
	public static String readFileContent(String fileName) {
	    File file = new File(fileName);
	    BufferedReader reader = null;
	    StringBuffer sbf = new StringBuffer();
	    try {
	        reader = new BufferedReader(new FileReader(file));
	        String tempStr;
	        while ((tempStr = reader.readLine()) != null) {
	            sbf.append(tempStr);
	        }
	        reader.close();
	        return sbf.toString();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }
	    }
	    return sbf.toString();
	}
}
