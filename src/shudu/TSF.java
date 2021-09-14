package shudu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.commons.lang.StringEscapeUtils;

public class TSF {

	public static void main(String[] args) throws Exception {
		String path = TSF.class.getClass().getResource("/").getPath();
		path = path.replace("/WebContent/WEB-INF/classes/", "");
		File f = new File(path+"/src/shudu/com/insigma/siis/local/business/entity");
		File[] fs = f.listFiles();
		System.out.println(f.isDirectory());
		for(int i=0;i<fs.length;i++){
			f = fs[i];
			if(f.getName().contains(".hbm.xml")){
				
				String c = fileRead(f);
				c = StringEscapeUtils.unescapeHtml(c); 
				c = new String(c.getBytes("iso8859-1"),"utf8");
				//System.out.println(c);
				FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(c);
				bw.close();
			}
			
		}
		
		System.out.println(path);  
		/*String aac = StringEscapeUtils.escapeJava("����");  
        System.out.println(aac);  
        String aa = StringEscapeUtils.unescapeHtml("&#208;&#213;&#195;&#251; &#181;&#200;");  
        System.out.println(aa);*/
	}


	public static String  fileRead(File file) throws Exception {
	    FileReader reader = new FileReader(file);//����һ��fileReader����������ʼ��BufferedReader
	    BufferedReader bReader = new BufferedReader(reader);//newһ��BufferedReader���󣬽��ļ����ݶ�ȡ������
	    StringBuilder sb = new StringBuilder();//����һ���ַ������棬���ַ�����Ż�����
	    String s = "";
	    while ((s =bReader.readLine()) != null) {//���ж�ȡ�ļ����ݣ�����ȡ���з���ĩβ�Ŀո�
	        sb.append(s + "\n");//����ȡ���ַ�����ӻ��з����ۼӴ���ڻ�����
	    }
	    bReader.close();
	    String str = sb.toString();
	    return str;
	}
}  