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
			license = new FileInputStream(loader.getResource("license.xml").getPath());// 凭证文件
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
		 //���� POIFSFileSystem ����  
        POIFSFileSystem poifs = new POIFSFileSystem();    
        //��ȡDirectoryEntry  
        DirectoryEntry directory = poifs.getRoot();    
        //���������  
        OutputStream out = new FileOutputStream("D:\\����\\html_to_word.doc");  
        try {  
        	
            //�����ĵ�,1.��ʽ,2.HTML�ļ�������  
        	directory.createDocument("WordDocument",new FileInputStream("D:\\����\\sh1.html")); 
        	
            //д��  
        	ByteArrayOutputStream output = new ByteArrayOutputStream();
            poifs.writeFilesystem(output);  
            
            ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
            
            
//            Document document= new Document(input);
//
//
//
//            //��ȡָ����
//
//            Section section = document.getSections().get(0);
//
//            //����ҳ�߾�
//
//            section.getPageSetup().getMargins().setTop(30f);
//            section.getPageSetup().getMargins().setBottom(30f);
//            section.getPageSetup().getMargins().setLeft(30f);
//            section.getPageSetup().getMargins().setRight(30f);
//            document.saveToFile(out, FileFormat.Docx);
            
            
          /*  Document document = new Document(new FileInputStream("D:\\����\\html_to_word.docx"));
            
            DocumentBuilder builder = new DocumentBuilder(document);
            PageSetup ps = builder.getPageSetup();
            ps.setBottomMargin(99.25);
            ps.setTopMargin(99.25);
            ps.setLeftMargin(99.25);
            ps.setRightMargin(99.25);
            document.save(out,SaveFormat.DOC);*/
            //�ͷ���Դ  
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
