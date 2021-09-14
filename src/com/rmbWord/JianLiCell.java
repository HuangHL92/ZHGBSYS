package com.rmbWord;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JianLiCell {
	public static String formatJL(String a1701) { 
		if(a1701!=null){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");     
		        Matcher matcher = pattern.matcher(jl);     
		        if (matcher.find()) {     
		        	String line1 = matcher.group(0);  
		        	int index = jl.indexOf(line1);
		        	if(index==0){//以日期开头  (一段)
		        		jlsb.append(line1).append("  ");
		        		String line2 = jl.substring(line1.length()).trim();
			        	parseJL(line2, jlsb,true);
		        	}else{
		        		parseJL(jl, jlsb,false); 
		        	}
		        }else{
		        	parseJL(jl, jlsb,false);
		        }
			}
			
			return jlsb.toString();
			
		}
		return a1701;
	}
	
	private static void parseJL(String line2, StringBuffer jlsb, boolean isStart){
		int llength = line2.length();//总长
		//32个字一行。
		int oneline = 21;
    	if(llength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//不足 64个字节往后偏移，直到足够为止。
    		boolean hass = false;
    		while((end+offset)<llength){//32个字一行，换行符分割
    			end = oneline*(j+1);
    			if(end+offset>llength){
    				end = llength-offset;
    			}
    			String l = line2.substring(oneline*j+offset,end+offset);
    			int loffset=0;
    			while(l.getBytes().length<oneline<<1){//32个字一行但不足64个字节 往右移
    				loffset++;
    				if((end+offset+loffset)>llength){//超过总长度 退出循环
    					loffset--;
    					break;
    				}
    				l = line2.substring(oneline*j+offset,end+offset+loffset);
    				if(l.getBytes().length>oneline<<1){//可能会出现一行65个字节，往前退一格。
    					loffset--;
    					l = line2.substring(oneline*j+offset,end+offset+loffset);
    					break;
    				}
    			}
    			offset = offset+loffset;
    			if(isStart&&!hass){
    				jlsb.append(l).append("\r\n");
    				hass = true;
    			}else{
    				jlsb.append("                  ").append(l).append("\r\n");
    			}
    			
    			j++;
    		}
    	}else{
    		if(isStart){
    			jlsb.append(line2).append("\r\n");
    		}else{
    			jlsb.append("                  ").append(line2).append("\r\n");
    		}
    	}
	}
}
