package com.rmbWord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

public class XingMingCell {
	public static final int MAX_SIZE = 18;
	/**
	 * 一级字体的参数配置
	 * @author zoul
	 *
	 */
	static enum ONE_LEVEL_FONT{
		/**
		 * 16榜行间距
		 */
		LINE_SIZE(320),//16榜
		/**
		 * 14号字体
		 */
		SIZE(14),
		/**
		 * 一行最多9个字节
		 */
		ONE_LINE_BYTE_SIZE(9);
		private final int value;

		private ONE_LEVEL_FONT(int val) {
			this.value = val;
		}

		public int getValue() {
			return this.value;
		}
	}
	
	
	/**
	 * 二级字体的参数配置
	 * @author zoul
	 *
	 */
	static enum TWO_LEVEL_FONT{
		/**
		 * 14榜行间距
		 */
		LINE_SIZE(280),//14榜
		/**
		 * 12号字体
		 */
		SIZE(12),
		/**
		 * 一行最多10个字节
		 */
		ONE_LINE_BYTE_SIZE(10);
		private final int value;

		private TWO_LEVEL_FONT(int val) {
			this.value = val;
		}

		public int getValue() {
			return this.value;
		}
	}
	
	/**
	 * 三级字体的参数配置
	 * @author zoul
	 *
	 */
	static enum THREE_LEVEL_FONT{
		/**
		 * 11榜行间距
		 */
		LINE_SIZE(220),//行距11榜
		/**
		 * 11号字体
		 */
		SIZE(11),//字体大小
		/**
		 * 一行最多11个字节
		 */
		ONE_LINE_BYTE_SIZE(11);//每行最大字节数
		private final int value;

		private THREE_LEVEL_FONT(int val) {
			this.value = val;
		}

		public int getValue() {
			return this.value;
		}
	}
	
	/**
	 * 四级字体的参数配置
	 * @author zoul
	 *
	 */
	static enum FOUR_LEVEL_FONT{
		/**
		 * 11榜行间距
		 */
		LINE_SIZE(220),//11榜
		/**
		 * 10号字体
		 */
		SIZE(10),
		/**
		 * 一行最多12个字节
		 */
		ONE_LINE_BYTE_SIZE(12);
		private final int value;

		private FOUR_LEVEL_FONT(int val) {
			this.value = val;
		}

		public int getValue() {
			return this.value;
		}
	}
	
	
	
	
	/**
	 * 姓名 2个字 居中 中间加2个空格      14号字体     1行
	 * 大于9个字节居左 		    14号字体     2行
	 * 满2行     				14号字体     前9个字节刚好满5个字 则为1行  否则8个字节为一行
	 * 
	 * 超过2行    				12号字体  重新计算
	 *  
	 * @param contents
	 */
	public static void getXMConfig(String contents,FontConfig fc) {
		char[] cs = contents.toCharArray();
		if(cs.length>XingMingCell.MAX_SIZE){
			contents = contents.substring(0, XingMingCell.MAX_SIZE);
			cs = contents.toCharArray();//最多18个字
		}
		byte[] bs = contents.getBytes();
		if(cs.length==2){//2个字
			fc.setContents(cs[0]+"  "+cs[1]);
			fc.setAlignment(ParagraphAlignment.CENTER);
			fc.setFontSize(XingMingCell.ONE_LEVEL_FONT.SIZE.getValue());
			fc.setLineSize(BigInteger.valueOf(XingMingCell.ONE_LEVEL_FONT.LINE_SIZE.getValue()));
		}else if(bs.length<=XingMingCell.ONE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()){//9个及9个字节以下
			fc.setContents(contents);
			fc.setAlignment(ParagraphAlignment.CENTER);
			fc.setFontSize(XingMingCell.ONE_LEVEL_FONT.SIZE.getValue());
			fc.setLineSize(BigInteger.valueOf(XingMingCell.ONE_LEVEL_FONT.LINE_SIZE.getValue()));
		}else if(bs.length<=XingMingCell.ONE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()<<1){//9-18个字节，包括18，不包括9
			fc.setContents(contents);
			fc.setAlignment(ParagraphAlignment.LEFT);
			int char_total__byte_length = 0;//字节长度
			for(int i=0; i<cs.length; i++){
				char c = cs[i];
				int char_byte_length = String.valueOf(c).getBytes().length;
				char_total__byte_length = char_total__byte_length + char_byte_length;
				//如果开头存在9个字节，则后边最多就只有9个字节，2行可以放下
				if(char_total__byte_length == XingMingCell.ONE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()){
					fc.setFontSize(XingMingCell.ONE_LEVEL_FONT.SIZE.getValue());
					fc.setLineSize(BigInteger.valueOf(XingMingCell.ONE_LEVEL_FONT.LINE_SIZE.getValue()));
					return;
				}
			}
			//如果开头不存在9个字节，就相当于第一行只有4个字，再看第二行的情况
			if((char_total__byte_length-XingMingCell.ONE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()/2*2)
					>XingMingCell.ONE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()){//第二行大于9个字节
				fc.setFontSize(XingMingCell.TWO_LEVEL_FONT.SIZE.getValue());
				fc.setLineSize(BigInteger.valueOf(XingMingCell.TWO_LEVEL_FONT.LINE_SIZE.getValue()));
			}else{
				fc.setFontSize(XingMingCell.ONE_LEVEL_FONT.SIZE.getValue());
				fc.setLineSize(BigInteger.valueOf(XingMingCell.ONE_LEVEL_FONT.LINE_SIZE.getValue()));
			}
			
		}else if(bs.length<=XingMingCell.TWO_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()<<1){//小于等于20个字节 不超过2行
			fc.setContents(contents);
			fc.setAlignment(ParagraphAlignment.LEFT);
			fc.setFontSize(XingMingCell.TWO_LEVEL_FONT.SIZE.getValue());
			fc.setLineSize(BigInteger.valueOf(XingMingCell.TWO_LEVEL_FONT.LINE_SIZE.getValue()));
		}else if(bs.length<=XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()*3){//小于等于33个字节  3行
			fc.setContents(contents);
			fc.setAlignment(ParagraphAlignment.LEFT);
			if(bs.length<=XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()/2*2*3){//小于等于30个字节 肯定放得下
				fc.setFontSize(XingMingCell.THREE_LEVEL_FONT.SIZE.getValue());
				fc.setLineSize(BigInteger.valueOf(XingMingCell.THREE_LEVEL_FONT.LINE_SIZE.getValue()));
			}else{//31-33字节
				int char_total__byte_length = 0;//字节长度
				int first_byte = 9999;
				int second_byte = 9999;
				for(int i=0; i<cs.length; i++){
					char c = cs[i];
					int char_byte_length = String.valueOf(c).getBytes().length;
					char_total__byte_length = char_total__byte_length + char_byte_length;
					//如果开头是11个字节，则可以放下1行
					if(char_total__byte_length == XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()){
						first_byte = XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue();
					}else if(char_total__byte_length == XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()/2*2){
						first_byte = XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()/2*2;
					}
					//第二行是否11个字节
					if(char_total__byte_length>first_byte){
						if((char_total__byte_length-first_byte) == XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()){
							second_byte = XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue();
						}else if((char_total__byte_length-first_byte) == XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()/2*2){
							second_byte = XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()/2*2;
						}
					}
				}
				if(char_total__byte_length-first_byte-second_byte<XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()){//看第三行放不放的下
					fc.setFontSize(XingMingCell.THREE_LEVEL_FONT.SIZE.getValue());
					fc.setLineSize(BigInteger.valueOf(XingMingCell.THREE_LEVEL_FONT.LINE_SIZE.getValue()));
				}else {
					fc.setFontSize(XingMingCell.FOUR_LEVEL_FONT.SIZE.getValue());
					fc.setLineSize(BigInteger.valueOf(XingMingCell.FOUR_LEVEL_FONT.LINE_SIZE.getValue()));
				}
			}
			
			
		}else{//超过33个字节
			fc.setContents(contents);
			fc.setAlignment(ParagraphAlignment.LEFT);
			fc.setFontSize(XingMingCell.FOUR_LEVEL_FONT.SIZE.getValue());
			fc.setLineSize(BigInteger.valueOf(XingMingCell.FOUR_LEVEL_FONT.LINE_SIZE.getValue()));
		}
	}
	
	public static void main(String[] args) throws Exception {
		//System.out.println(XingMingCell.ONE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()<<1);
		//Document document = new Document();
		//PdfWriter.getInstance(document, new FileOutputStream("Helloworld.PDF"));
		//document.open();
		
		
		wordToHtml("H:\\itexttest\\simplejl.docx", "H:\\itexttest\\simple9.pdf");
	}
	
	/**
	 * 1word.doc 2text文本  6rtf 8html不正常  9mht格式，不正常，word可以正常打开 10html不正常   11-16word的一种格式    17pdf 18未知
	 * @param docfile
	 * @param htmlfile
	 */
	public static void wordToHtml(String docfile, String htmlfile) {
		ActiveXComponent app = new ActiveXComponent("Word.Application"); // 启动word
		try {
			app.setProperty("Visible", new Variant(false));
			Dispatch docs = app.getProperty("Documents").toDispatch();
			Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method,
					new Object[] { docfile, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
			Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { htmlfile, new Variant(17) },
					new int[1]);
			Variant f = new Variant(false);
			Dispatch.call(doc, "Close", f);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			app.invoke("Quit", new Variant[] {});
		}
		
	}
}
