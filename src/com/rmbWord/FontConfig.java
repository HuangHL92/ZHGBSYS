package com.rmbWord;

import java.math.BigInteger;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

public class FontConfig {
	private int fontSize;
	private ParagraphAlignment alignment;
	private String contents;
	private BigInteger lineSize;

	public FontConfig() {
		
	}
	
	public FontConfig(String contents, String type) {
		if("姓名".equals(type)){
			XingMingCell.getXMConfig(contents,this);
		}else if("专业技术职务".equals(type)){
			//getXMConfig(contents);
		}
	}
	
		
		
	
	
	
	
	
	

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public ParagraphAlignment getAlignment() {
		return alignment;
	}

	public void setAlignment(ParagraphAlignment alignment) {
		this.alignment = alignment;
	}

	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public BigInteger getLineSize() {
		return lineSize;
	}

	public void setLineSize(BigInteger lineSize) {
		this.lineSize = lineSize;
	}
}
