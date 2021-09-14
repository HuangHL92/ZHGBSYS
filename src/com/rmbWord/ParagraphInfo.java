package com.rmbWord;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ȡ������ı���ʽ�������Ϣ 
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
	 * @param str ������ı�
	 * @param oneline   ���еĺ�����
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
		int llength = line2.length();//�ܳ�
		//32����һ�С�
		//int oneline = 21;
    	if(llength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//���� 64���ֽ�����ƫ�ƣ�ֱ���㹻Ϊֹ��
    		while((end+offset)<llength){//32����һ�У����з��ָ�
    			end = oneline*(j+1);
    			if(end+offset>llength){
    				end = llength-offset;
    			}
    			String l = line2.substring(oneline*j+offset,end+offset);
    			int loffset=0;
    			while(l.getBytes().length<oneline<<1){//32����һ�е�����64���ֽ� ������
    				loffset++;
    				if((end+offset+loffset)>llength){//�����ܳ��� �˳�ѭ��
    					loffset--;
    					break;
    				}
    				l = line2.substring(oneline*j+offset,end+offset+loffset);
    				if(l.getBytes().length>oneline<<1){//���ܻ����һ��65���ֽڣ���ǰ��һ��
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
