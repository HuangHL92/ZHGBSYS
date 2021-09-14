package com.rmbWord;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取任免表文本格式化后的信息 
 * @author zoul
 *
 */
public class ParagraphInfo {
	
	private int lineLength = 0;
	private String originalStr = "";
	private String formatStr = "";
	private List<String> listStr = new ArrayList<String>();
	/**
	 * 
	 * @param str 传入的文本
	 * @param oneline   单行的汉字数
	 */
	public ParagraphInfo(String str,  int oneline) {
		this.originalStr = new String(str);
		this.formatStr = formatStr(str,oneline);
		//this.lineLength = formatStr.length();
	}
	
	public String formatStr(String str, int oneline) {
		if(str!=null){
			String[] jianli = str.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				parseStr(jl, jlsb,oneline);
			}
			
			return jlsb.toString();
			
		}
		return str;
	}
	
	private void parseStr(String line2, StringBuffer jlsb, int oneline){
		int llength = line2.length();//总长
		//32个字一行。
		//int oneline = 21;
    	if(llength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//不足 64个字节往后偏移，直到足够为止。
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
    			jlsb.append(l).append("\r\n");
    			this.lineLength++;
    			j++;
    			this.listStr.add(l);
    		}
    	}else{
    		jlsb.append(line2).append("\r\n");
    		this.lineLength++;
    		this.listStr.add(line2);
    	}
	}
	
	
	public int getLineLength() {
		return lineLength;
	}

	public void setLineLength(int lineLength) {
		this.lineLength = lineLength;
	}

	public String getOriginalStr() {
		return originalStr;
	}

	public void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
	}

	public String getFormatStr() {
		return formatStr;
	}

	public void setFormatStr(String formatStr) {
		this.formatStr = formatStr;
	}
	
	public List<String> getListStr() {
		return listStr;
	}

	public void setListStr(List<String> listStr) {
		this.listStr = listStr;
	}
}
