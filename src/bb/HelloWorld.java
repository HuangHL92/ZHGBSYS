package bb;

import org.apache.commons.lang.StringEscapeUtils;

import com.insigma.odin.framework.util.MD5;

public class HelloWorld {
	

	public static void main(String[] args) {
		
		System.out.println("19680100".compareTo("19670201"));
		//System.out.println("erasfdabsdfacdabcdad".replaceFirst("a", ""));
		//System.out.println(StringEscapeUtils.escapeHtml("大师傅打算"));
		
		/*DecimalFormat df=new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		System.out.println(df.format(49.955));*///612
		
		
		//String a1 = "123456";
		
		//System.out.println(MD5.MD5(a1));
		
	//	File file = new File("C:\\Users\\admin\\Desktop\\新建文件夹\\hzb\\WEB-INF\\classes\\com\\insigma\\siis\\local\\pagemodel\\xbrm\\jcgl");
	//	file.mkdirs();
	/*	String regx = "[\\s\\S]*(硕士)[\\s\\S]*";
		String s = "农学硕士11 ";
		System.out.println(s.matches(regx));
		
		System.out.println(MD5.MD5("123456")); */
		//System.out.println("dsafads:das@@@@@1".split("@").length);
		//System.out.println(getNextUpEn("Z"));
	}
	
	
	public static String getNextUpEn(String en){  
		
        char lastE = 'a';  
        char st = en.toCharArray()[0];
        if(st<65 || st>90){
        	return "A";
        }
        if(Character.isUpperCase(st)){
            if(en.equals("Z")){
                return "A";
            }
            if(en==null || en.equals("")){ 
                return "A";  
            }
            lastE = 'Z';  
        }else{
            if(en.equals("z")){
                return "a";
            }
            if(en==null || en.equals("")){ 
                return "a";  
            }
            lastE = 'z';  
        }
        int lastEnglish = (int)lastE;      
        char[] c = en.toCharArray();  
        if(c.length>1){  
            return null;  
        }else{  
            int now = (int)c[0];  
            if(now >= lastEnglish)  
                return null;  
            char uppercase = (char)(now+1);  
            return String.valueOf(uppercase);  
        }  
    }
}