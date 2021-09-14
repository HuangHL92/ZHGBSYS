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
	 * һ������Ĳ�������
	 * @author zoul
	 *
	 */
	static enum ONE_LEVEL_FONT{
		/**
		 * 16���м��
		 */
		LINE_SIZE(320),//16��
		/**
		 * 14������
		 */
		SIZE(14),
		/**
		 * һ�����9���ֽ�
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
	 * ��������Ĳ�������
	 * @author zoul
	 *
	 */
	static enum TWO_LEVEL_FONT{
		/**
		 * 14���м��
		 */
		LINE_SIZE(280),//14��
		/**
		 * 12������
		 */
		SIZE(12),
		/**
		 * һ�����10���ֽ�
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
	 * ��������Ĳ�������
	 * @author zoul
	 *
	 */
	static enum THREE_LEVEL_FONT{
		/**
		 * 11���м��
		 */
		LINE_SIZE(220),//�о�11��
		/**
		 * 11������
		 */
		SIZE(11),//�����С
		/**
		 * һ�����11���ֽ�
		 */
		ONE_LINE_BYTE_SIZE(11);//ÿ������ֽ���
		private final int value;

		private THREE_LEVEL_FONT(int val) {
			this.value = val;
		}

		public int getValue() {
			return this.value;
		}
	}
	
	/**
	 * �ļ�����Ĳ�������
	 * @author zoul
	 *
	 */
	static enum FOUR_LEVEL_FONT{
		/**
		 * 11���м��
		 */
		LINE_SIZE(220),//11��
		/**
		 * 10������
		 */
		SIZE(10),
		/**
		 * һ�����12���ֽ�
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
	 * ���� 2���� ���� �м��2���ո�      14������     1��
	 * ����9���ֽھ��� 		    14������     2��
	 * ��2��     				14������     ǰ9���ֽڸպ���5���� ��Ϊ1��  ����8���ֽ�Ϊһ��
	 * 
	 * ����2��    				12������  ���¼���
	 *  
	 * @param contents
	 */
	public static void getXMConfig(String contents,FontConfig fc) {
		char[] cs = contents.toCharArray();
		if(cs.length>XingMingCell.MAX_SIZE){
			contents = contents.substring(0, XingMingCell.MAX_SIZE);
			cs = contents.toCharArray();//���18����
		}
		byte[] bs = contents.getBytes();
		if(cs.length==2){//2����
			fc.setContents(cs[0]+"  "+cs[1]);
			fc.setAlignment(ParagraphAlignment.CENTER);
			fc.setFontSize(XingMingCell.ONE_LEVEL_FONT.SIZE.getValue());
			fc.setLineSize(BigInteger.valueOf(XingMingCell.ONE_LEVEL_FONT.LINE_SIZE.getValue()));
		}else if(bs.length<=XingMingCell.ONE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()){//9����9���ֽ�����
			fc.setContents(contents);
			fc.setAlignment(ParagraphAlignment.CENTER);
			fc.setFontSize(XingMingCell.ONE_LEVEL_FONT.SIZE.getValue());
			fc.setLineSize(BigInteger.valueOf(XingMingCell.ONE_LEVEL_FONT.LINE_SIZE.getValue()));
		}else if(bs.length<=XingMingCell.ONE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()<<1){//9-18���ֽڣ�����18��������9
			fc.setContents(contents);
			fc.setAlignment(ParagraphAlignment.LEFT);
			int char_total__byte_length = 0;//�ֽڳ���
			for(int i=0; i<cs.length; i++){
				char c = cs[i];
				int char_byte_length = String.valueOf(c).getBytes().length;
				char_total__byte_length = char_total__byte_length + char_byte_length;
				//�����ͷ����9���ֽڣ���������ֻ��9���ֽڣ�2�п��Է���
				if(char_total__byte_length == XingMingCell.ONE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()){
					fc.setFontSize(XingMingCell.ONE_LEVEL_FONT.SIZE.getValue());
					fc.setLineSize(BigInteger.valueOf(XingMingCell.ONE_LEVEL_FONT.LINE_SIZE.getValue()));
					return;
				}
			}
			//�����ͷ������9���ֽڣ����൱�ڵ�һ��ֻ��4���֣��ٿ��ڶ��е����
			if((char_total__byte_length-XingMingCell.ONE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()/2*2)
					>XingMingCell.ONE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()){//�ڶ��д���9���ֽ�
				fc.setFontSize(XingMingCell.TWO_LEVEL_FONT.SIZE.getValue());
				fc.setLineSize(BigInteger.valueOf(XingMingCell.TWO_LEVEL_FONT.LINE_SIZE.getValue()));
			}else{
				fc.setFontSize(XingMingCell.ONE_LEVEL_FONT.SIZE.getValue());
				fc.setLineSize(BigInteger.valueOf(XingMingCell.ONE_LEVEL_FONT.LINE_SIZE.getValue()));
			}
			
		}else if(bs.length<=XingMingCell.TWO_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()<<1){//С�ڵ���20���ֽ� ������2��
			fc.setContents(contents);
			fc.setAlignment(ParagraphAlignment.LEFT);
			fc.setFontSize(XingMingCell.TWO_LEVEL_FONT.SIZE.getValue());
			fc.setLineSize(BigInteger.valueOf(XingMingCell.TWO_LEVEL_FONT.LINE_SIZE.getValue()));
		}else if(bs.length<=XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()*3){//С�ڵ���33���ֽ�  3��
			fc.setContents(contents);
			fc.setAlignment(ParagraphAlignment.LEFT);
			if(bs.length<=XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()/2*2*3){//С�ڵ���30���ֽ� �϶��ŵ���
				fc.setFontSize(XingMingCell.THREE_LEVEL_FONT.SIZE.getValue());
				fc.setLineSize(BigInteger.valueOf(XingMingCell.THREE_LEVEL_FONT.LINE_SIZE.getValue()));
			}else{//31-33�ֽ�
				int char_total__byte_length = 0;//�ֽڳ���
				int first_byte = 9999;
				int second_byte = 9999;
				for(int i=0; i<cs.length; i++){
					char c = cs[i];
					int char_byte_length = String.valueOf(c).getBytes().length;
					char_total__byte_length = char_total__byte_length + char_byte_length;
					//�����ͷ��11���ֽڣ�����Է���1��
					if(char_total__byte_length == XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()){
						first_byte = XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue();
					}else if(char_total__byte_length == XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()/2*2){
						first_byte = XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()/2*2;
					}
					//�ڶ����Ƿ�11���ֽ�
					if(char_total__byte_length>first_byte){
						if((char_total__byte_length-first_byte) == XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()){
							second_byte = XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue();
						}else if((char_total__byte_length-first_byte) == XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()/2*2){
							second_byte = XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()/2*2;
						}
					}
				}
				if(char_total__byte_length-first_byte-second_byte<XingMingCell.THREE_LEVEL_FONT.ONE_LINE_BYTE_SIZE.getValue()){//�������зŲ��ŵ���
					fc.setFontSize(XingMingCell.THREE_LEVEL_FONT.SIZE.getValue());
					fc.setLineSize(BigInteger.valueOf(XingMingCell.THREE_LEVEL_FONT.LINE_SIZE.getValue()));
				}else {
					fc.setFontSize(XingMingCell.FOUR_LEVEL_FONT.SIZE.getValue());
					fc.setLineSize(BigInteger.valueOf(XingMingCell.FOUR_LEVEL_FONT.LINE_SIZE.getValue()));
				}
			}
			
			
		}else{//����33���ֽ�
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
	 * 1word.doc 2text�ı�  6rtf 8html������  9mht��ʽ����������word���������� 10html������   11-16word��һ�ָ�ʽ    17pdf 18δ֪
	 * @param docfile
	 * @param htmlfile
	 */
	public static void wordToHtml(String docfile, String htmlfile) {
		ActiveXComponent app = new ActiveXComponent("Word.Application"); // ����word
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
