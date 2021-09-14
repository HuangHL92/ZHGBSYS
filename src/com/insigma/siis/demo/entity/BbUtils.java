package com.insigma.siis.demo.entity;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aspose.words.DocumentBuilder;
import com.aspose.words.FieldMergingArgs;

public class BbUtils {
	
	private static int  PRE_BLANKS = 9;//每行前面有9个汉字的位置,前面空格长度为：18
	
	private static TreeMap<String, Double> fontSizeMap = new TreeMap<String,Double>(new Comparator<String>() {
		  public int compare(String obj1,String obj2){
			  double d1 = Double.valueOf(obj1);
			  double d2 = Double.valueOf(obj2);
			  
			  if(d2>d1) return 1;
			  else if (d2<d1) return -1;
			  else return 0;
		}
	});
	private static TreeMap<String, Double> fontSizeMapw = new TreeMap<String,Double>(new Comparator<String>() {
		public int compare(String obj1,String obj2){
			double d1 = Double.valueOf(obj1);
			double d2 = Double.valueOf(obj2);
			
			if(d2>d1) return 1;
			else if (d2<d1) return -1;
			else return 0;
		}
	});
	// 字的高
public static void initMap(){
	fontSizeMap.put("2", 2.04);
	fontSizeMap.put("3", 3.04);
	fontSizeMap.put("4", 4.04);
	fontSizeMap.put("5", 5.02);
	fontSizeMap.put("5.5", 5.57);
	fontSizeMap.put("6.5", 6.604);
	fontSizeMap.put("7.0", 7.133);
	fontSizeMap.put("7.5", 7.588);
	fontSizeMap.put("8", 8.105);
	fontSizeMap.put("8.5", 8.70);
	fontSizeMap.put("9", 9.145);
	fontSizeMap.put("9.5", 9.64);
	fontSizeMap.put("10", 10.19);
	fontSizeMap.put("10.5", 10.60);
	fontSizeMap.put("11", 11.145);
	fontSizeMap.put("11.5", 11.50);
	fontSizeMap.put("12", 12.30);
	fontSizeMap.put("12.5", 12.74);
	fontSizeMap.put("13", 13.20);
	fontSizeMap.put("13.5", 14.26);
	fontSizeMap.put("14", 14.26);
	
	
	/*fontSizeMap.put("2", 5.14);
	fontSizeMap.put("3", 6.14);
	fontSizeMap.put("4", 7.14);
	fontSizeMap.put("5", 8.11);
	fontSizeMap.put("5.5", 8.70);
	fontSizeMap.put("6.5", 9.64);
	fontSizeMap.put("7.0", 10.19);
	fontSizeMap.put("7.5", 10.81);
	fontSizeMap.put("8", 11.15);
	fontSizeMap.put("8.5", 11.51);
	fontSizeMap.put("9", 11.89);
	fontSizeMap.put("9.5", 12.30);
	fontSizeMap.put("10", 12.74);
	fontSizeMap.put("11", 14.3);
	fontSizeMap.put("12", 14.87);
	fontSizeMap.put("14", 17.50);*/
//	fontSizeMap.put("15", 20.0);
//	fontSizeMap.put("16", 21.0);
//	fontSizeMap.put("18", 24.0);
	}
//字的 宽
public static void initMapw(){
	fontSizeMapw.put("2", 0.95);
	fontSizeMapw.put("3", 1.53);
	fontSizeMapw.put("4", 2.06);
	fontSizeMapw.put("5", 2.57);
	fontSizeMapw.put("5.5", 2.84);
	fontSizeMapw.put("6.5", 3.30);
	fontSizeMapw.put("7.0", 3.59);
	fontSizeMapw.put("7.5", 3.93);
	fontSizeMapw.put("8", 4.12);
	fontSizeMapw.put("8.5", 4.34);
	fontSizeMapw.put("9", 4.58);
	fontSizeMapw.put("9.5", 4.85);
	fontSizeMapw.put("10", 5.16);
	fontSizeMapw.put("10.5", 5.36);
	fontSizeMapw.put("11", 5.90);
	fontSizeMapw.put("11.5", 6.10);
	fontSizeMapw.put("12", 6.35);
	fontSizeMapw.put("12.5", 6.55);
	fontSizeMapw.put("13", 6.8);
	fontSizeMapw.put("13.5", 7.0);
	fontSizeMapw.put("14", 7.50);
}
	
	private static Double getFontSize(String key){
		return fontSizeMap.get(key);
	}
	private static Double getFontSizew(String key){
		return fontSizeMapw.get(key);
	}
	

	 public static int gbkCount(String text) {
         String Reg="^[\u4e00-\u9fa5]{1}|[（）]{1}$";//正则
         int result=0;
         float ansiCharNumber = 0;
       for(int i=0;i<text.length();i++){
         String b=Character.toString(text.charAt(i));
         if(b.matches(Reg))result++;
         else ansiCharNumber+=0.8;//modify zepeng 20171223 原计算字符个数的办法存在问题忽略了半角字符的宽度
         }
       return result+Math.round(ansiCharNumber)+1;
     }
	 

	 
 

 /**
  * 计算出串中汉字和非汉字按汉字计算的长度
  * @param value
  * @return
  */
 private static int getGbkWordLen(String value){
	 float defaultCount = 0.00f;
	 for (int i = 0; i < value.length(); i++) {
         float ff = getregex(value.substring(i, i + 1));
         defaultCount = defaultCount + ff;
     }
	 /*int allLength = value.length();
	 int gbkLength=gbkCount(value);
	 int len= allLength - gbkLength;
	 return  gbkLength + (len%2==0?len/2:(len/2+1));*/
	 return (int)(defaultCount*2);
 }
 public static float getregex(String charStr) {
     
     if(charStr==" ")
     {
         return 0.5f;
     }
     // 判断是否为字母或字符
     if (Pattern.compile("^[A-Za-z0-9]+$").matcher(charStr).matches()) {
         return 0.5f;
     }
     // 判断是否为全角

     if (Pattern.compile("[\u4e00-\u9fa5]+$").matcher(charStr).matches()) {
         return 1.00f;
     }
     //全角符号 及中文
     if (Pattern.compile("[^x00-xff]").matcher(charStr).matches()) {
         return 1.00f;
     }
     return 0.5f;

 }
 /**
  * 根据字号单元格式宽度，计算出占用行数
  * @param gbWordLength
  * @param cellWidth
  * @param wordLength
  * @return
  */
 private static int calcRows(int gbWordLength,double cellWidth,double wordLength,boolean isJl){
	 int rowWords = (int)(cellWidth/(wordLength));
	 if(gbWordLength>rowWords){
		 int tempWords = gbWordLength;
		 int cellWords = rowWords ;//实际每行只能再显示少了9个汉字位置
		//modify zepeng 20171223 是字符大小计算问题不需要判断是否简历			
//		 if(isJl){
//			 tempWords = gbWordLength - rowWords;//剩余有字数
//			 cellWords = rowWords - PRE_BLANKS;//实际每行只能再显示少了9个汉字位置
//		 }
		 
		 return tempWords%cellWords==0?tempWords/cellWords:tempWords/cellWords+1;
	 }else{
		 return 1;
	 }
 }
 /**
  * step 05
  * @param mBuilder
  * @param singleFieldValue
  * @return
  * @throws Exception
  */
 private static int formatWordsByEnter(DocumentBuilder mBuilder,String singleFieldValue,double wordLength,boolean isJl) throws Exception{
	 double cellWidth = mBuilder.getCellFormat().getWidth();//单元格长度：422.75
	 if(isJl){
		 cellWidth = cellWidth - 18*wordLength;
		//简历是日期开头
		 Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");     
	     Matcher matcher = pattern.matcher(singleFieldValue);     
	     if (matcher.find()) {
	    	String line1 = matcher.group(0);  
	     	int index = singleFieldValue.indexOf(line1);
	     	if(index==0){
	     		singleFieldValue = singleFieldValue.replaceFirst(line1+"  ", "");
	     	}
	     }
	 }
	 
	 int gbWordLength = getGbkWordLen(singleFieldValue);
	 //除了前面的空格的长度：422.75 - 18 = 404.75
	 int rows = calcRows(gbWordLength,cellWidth,wordLength,isJl);
	 return rows;
 }
 /**
  * step 04
  * @param mBuilder
  * @param args
 * @param wordHeight 
  * @return
 * @throws Exception 
  */
    private static double[] getCurrentRows(DocumentBuilder mBuilder,FieldMergingArgs args,double wordLength,boolean isJl, double wordHeight) throws Exception{
    	int currentRows = 0;
    	double currentHeight = 0;
    	String value= args.getFieldValue()==null?"":args.getFieldValue().toString();
    	if(args.getFieldName().equals("a1701")){//简历信息
    		//value="\n"+args.getFieldValue().toString();
    		//System.out.println(value);
    	}
    	String[] fieldValueArray = value.split("\r\n|\r|\n");
    	for(String jl:fieldValueArray){
    		currentRows+= formatWordsByEnter(mBuilder,jl,wordLength,isJl);
    		if(isJl){
    			currentHeight+= formatWordsByEnter(mBuilder,jl,wordLength,isJl)*(wordHeight+1)+3;
    		}else{
    			currentHeight+= formatWordsByEnter(mBuilder,jl,wordLength,isJl)*(wordHeight+1);
    		}
    		
    	}
    	
    	return new double[]{Double.valueOf(currentRows),currentHeight};
    }
 
    
    /**
     * step 03
     * @param mBuilder
     * @param args
     * @param fontSize
     * @return
     * @throws Exception 
     */
    private static boolean isFitCell(DocumentBuilder mBuilder,FieldMergingArgs args,String fontSize,boolean isJl) throws Exception{
		//简历单元格实际高度
		double cellHeight = mBuilder.getRowFormat().getHeight()+0.04;
		double wordHeight = Double.valueOf(fontSize)/2;//getFontSizew(fontSize);
		double wordWidth = getFontSizew(fontSize);//Double.valueOf(fontSize)/2;//
		if(isJl||args.getFieldName().equals("a14z101")||args.getFieldName().equals("a0192a")||args.getFieldName().equals("js0108")
				||args.getFieldName().equals("js0111")||args.getFieldName().equals("js0117")){
			wordHeight = Double.valueOf(fontSize)+0.04;//getFontSize(fontSize);
			String a="1";
		}
		if(isJl){
			//wordHeight = wordHeight;
		}
		/*if(args.getFieldName().equals("js0111")){
			double cellWidth = mBuilder.getCellFormat().getWidth();//单元格长度：422.75
		}*/
		
		//总行数
		double totalRows[] = getCurrentRows(mBuilder,args,wordWidth,isJl,wordHeight);//得到当前计算后的实际行数
		//int totalRows=getCurrentRows(mBuilder,args,wordWidth,isJl);//得到当前计算后的实际行数
		//int currentRows = (int)(cellHeight/(wordHeight));
		if("2".equals(fontSize)){
			return true;
		}
		if(cellHeight>=totalRows[1]){
			return true;
		}
		
    	return false;
    }
	 /**
	  * step 02
	  * @param mBuilder
	  * @param args
	  * @throws Exception
	  */
    private static void formatWords(DocumentBuilder mBuilder,FieldMergingArgs args,boolean isJl) throws Exception{
			/*
			 * 数据有回车
			 * 前面空格长度为：18
			 * 单元格长度：422.75
			 * 除了前面的空格的长度：422.75 - 18 = 404.75
			 * 处理思路：
			 * 1、读取一行
			 * 2、计算文本长度，是否超过单元格宽度，如果长度超过就计算折行能分的实际长度，返回行数
			 * 3、计算得到所有行数
			 * 4、根据字号、行数，计算出是否超过单元格式高度，如果超过换字号重新计算，重复上面的过程
			 * 5、直到找到合适的字号，行高
			 */
//			if(isJl){
//				//简历前边增加一行
//				mBuilder.getFont().setSize(fontSizeMap.get("4"));
//		       	mBuilder.getFont().setSpacing(0);
//		        mBuilder.getParagraphFormat().setLineSpacing(fontSizeMap.get("4")+2);
//				mBuilder.write("\n");
//			}
			//有字号
			 for(String fontSize:fontSizeMap.keySet()){
				// System.out.println("==================="+fontSize);
				if(isFitCell(mBuilder,args,fontSize,isJl)){
					String fs = fontSize;
					if(Float.valueOf(fontSize)<5f){
						fs = "5";
					}
//					if(4<=new Double(fontSize).doubleValue()){
//						mBuilder.getFont().setSize(12);
//					}else{
//						mBuilder.getFont().setSize(Double.valueOf(fontSize));
//					}
					//System.out.println("height:"+mBuilder.getRowFormat().getHeight());
					mBuilder.getFont().setSize(Double.valueOf(fs));
			       	
			       	mBuilder.getFont().setSpacing(0);
			       	if(isJl){
			       		//System.out.println("fontSize:"+fontSize);//14 +2
			       		mBuilder.getParagraphFormat().setLineSpacing(Double.valueOf(fontSize)+1);
			       		
			       	}else{
			       		mBuilder.getParagraphFormat().setLineSpacing(Double.valueOf(fontSize)+1);
			       	}
			        
			        /*System.out.println(args);
			        System.out.println(fontSize);*/
					System.out.println("fontSize:"+fontSize+"；args:"+args.getFieldName()+"；height2:"+mBuilder.getRowFormat().getHeight()+"；width2:"+mBuilder.getCellFormat().getWidth());
			        break;
				}
			 }
				
	 }

	 /**
	  * step 01
	  * @param mBuilder
	  * @param args
	  * @throws Exception
	  */
	 public static void formatArray(DocumentBuilder mBuilder,FieldMergingArgs args,boolean isJl) throws Exception{
		 if(fontSizeMap==null || (fontSizeMap!=null && fontSizeMap.isEmpty())){
			 initMap();
		 }   
		 if(fontSizeMapw==null || (fontSizeMapw!=null && fontSizeMapw.isEmpty())){
			 initMapw();
		 }   
		    formatWords(mBuilder,args,isJl);
		    mBuilder.write(args.getFieldValue()==null?"":args.getFieldValue().toString());
	 }

}