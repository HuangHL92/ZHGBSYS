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
		/*String aac = StringEscapeUtils.escapeJava("姓名");  
        System.out.println(aac);  
        String aa = StringEscapeUtils.unescapeHtml("&#208;&#213;&#195;&#251; &#181;&#200;");  
        System.out.println(aa);*/
	}


	public static String  fileRead(File file) throws Exception {
	    FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
	    BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
	    StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
	    String s = "";
	    while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
	        sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
	    }
	    bReader.close();
	    String str = sb.toString();
	    return str;
	}
}  