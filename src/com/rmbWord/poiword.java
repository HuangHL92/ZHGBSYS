package com.rmbWord;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright;


public class poiword   {
	
	public static void main(String[] args) throws Exception{
		//System.out.println(ParagraphAlignment.valueOf(2));simple9.doc
		//XWPFDocument doc1 = new XWPFDocument(new FileInputStream("H:\\itexttest\\simple9.docx")); 
		//for(int i=0;i<nametext.length;i++){
			CustomXWPFDocument doc = new CustomXWPFDocument(new FileInputStream("H:\\itexttest\\�����.docx")); 
		    
			List<XWPFTable> tables= doc.getTables();
			//�ڶ������
			XWPFTable tab = tables.get(1);
			//��������
			XWPFTableCell cell01 = tab.getRow(0).getCell(1);
			//XWPFStyles style = doc.createStyles();
			addNameText(cell01,nametext[9]);
			
			//tab.getRow(0).setHeight(1000);
			
			
			
			//�ϲ���Ԫ��
			XWPFTableCell cell71 = tab.getRow(4).getCell(2);
			//tab.getRow(8).setHeight(1000);//1.76cm
			CTTc cttc = cell71.getCTTc();
			CTTcPr cpr = cttc.addNewTcPr();
			cpr.addNewVMerge().setVal(STMerge.RESTART);//�ϲ���ʼ��Ԫ��
			
			XWPFTableCell cell51 = tab.getRow(5).getCell(2);
			CTTc cttc1 = cell51.getCTTc();
			CTTcPr cpr1 = cttc1.addNewTcPr();
			cpr1.addNewVMerge().setVal(STMerge.CONTINUE);//������Ԫ��
			cell71.getParagraphs().get(0).createRun().setText("fdsfds");
			
			
			
			XWPFTableCell cell111 = tab.getRow(11).getCell(1);
			addjlText(cell111,jianli);
			
			
			//��ͥ��Ա ģ���ƶ�10�У� ����ʵ�����ɾ����
			XWPFTable tab2 = tables.get(2);
			tab2.getRow(13).getCell(1).setText("33333");
			//tab2.removeRow(8);
			
			//ͼƬ
			XWPFTableCell cell06 = tab.getRow(0).getCell(6);
			List<XWPFParagraph> paragraphList = cell06.getParagraphs();
			XWPFParagraph paragraph = paragraphList.get(0);
			//doc.getAllPictures().
			String picId = doc.addPictureData(new FileInputStream( 
					"H:\\itexttest\\rmb.jpg"), XWPFDocument.PICTURE_TYPE_JPEG); 
			doc.createPicture(paragraph, 0, 136, 185, "");
			paragraph.removeRun(0);
			
			System.out.println(doc.getParagraphs().get(doc.getParagraphs().size()-1).getText());
			
			
			//�ϲ�
			CustomXWPFDocument doc2 = new CustomXWPFDocument(new FileInputStream("H:\\itexttest\\aaaa.docx")); 
			doc.createTable();
			doc.setTable(3, doc2.getTables().get(0));
			//��һ���ն��䣬����2�����ᱻ�ϲ�
			XWPFParagraph ph = doc.createParagraph();
			CTPPr pPPr = getParagraphCTPPr(ph);
	        CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing(): pPPr.addNewSpacing();
	        pSpacing.setLine(BigInteger.ZERO);//�м��
			//
	        doc.createTable();
	        doc.setTable(4, doc2.getTables().get(1));
	        
	        //�ϲ����ĵ�û�����ģ����¼���
	        ByteArrayOutputStream out1 = new ByteArrayOutputStream();  
	        doc.write(out1);  
	        doc = new CustomXWPFDocument(new ByteArrayInputStream(out1.toByteArray()));
	        
			//���ϲ����ĵ������Ƭ
			XWPFTableCell tcell06 = doc.getTables().get(4).getRow(0).getCell(6);
			
			XWPFParagraph tparagraph = tcell06.getParagraphs().get(0);
			System.out.println(tparagraph.getText());
			//doc.getAllPictures().
			doc.addPictureData(new FileInputStream( 
					"H:\\itexttest\\rmb2.png"), XWPFDocument.PICTURE_TYPE_PNG); 
			doc.createPicture(tparagraph, 1, 136, 185, "");
			tparagraph.removeRun(0);
			//doc2.getTables().get(4).getCTTbl().get
			
			
			
			
			
			
			
			
			doc2.close();
			
			
			
			
		    FileOutputStream out = new FileOutputStream("H:\\itexttest\\simple"+"jl"+".docx"); 
		    doc.write(out);  
		    doc.close();
		    out.close();
		//}
		
		
		
		
	}
	private static void addjlText(XWPFTableCell cell111, String string) {
		XWPFParagraph p = cell111.getParagraphs().get(0);
		XWPFRun r1 = p.createRun();
		CTPPr pPPr = getParagraphCTPPr(p);
		CTInd pInd = pPPr.getInd() != null ? pPPr.getInd() : pPPr.addNewInd();
		ParagraphInfo aaa = new ParagraphInfo(string, 20);
		System.out.println(aaa.getFormatStr().split("\r\n|\r|\n").length);
		String jl = JianLiCell.formatJL(string);
		
		String[] jianli = jl.split("\r\n|\r|\n");
		
		System.out.println(jianli.length);
		for(int i=0;i<jianli.length;i++){
			String jlp = jianli[i];
			if(i==0){
				r1.setText(jlp);
			}else{
				p = cell111.addParagraph();
				r1 = p.createRun();
				r1.setText(jlp);
			}
		}
		 //pInd.setFirstLine(BigInteger.valueOf(0));
		//pInd.setHanging(BigInteger.valueOf(2534));
		//pInd.setHangingChars(BigInteger.valueOf(1000-25));//10����
		//pInd.setLeftChars(BigInteger.valueOf(40));//0.4����
		
		
	}
	private static String jianli = "1973.07--1977.09  ĳĳʡĳĳ�㷺�Ĺ㷺�����ٸ���ٸ���ٸԶ�������ٸ���ĳĳ��Сѧ��ʦ"+"\r\n"+
"1977.09--1979.09  ĳĳʡĳĳ��ĳvcxvxdfdsfds�������Ƿ�淶 ��ٸԸ��ŵ��÷��Ĺ㷺���ĳ����н�ʦ"+"\r\n"+
"1979.09--1988.11  ĳĳʡĳĳ��ί��У��ʦ����䣺1985.10--1988.07��ĳĳʡί��У��̴�ר��ѧϰ��"+"\r\n"+
"1988.11--1993.07  ĳĳʡĳĳ��ί���������¡����Ƴ����Ƴ���1987.09--1992.07��ĳĳ��ѧĳĳϵĳĳרҵѧϰ��"+"\r\n"+
"1993.07--1995.11  ĳĳʡĳĳ��ĳ�㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸ�ĳ�ָ��ֳ�"+"\r\n"+
"1995.11--1998.05  ĳĳʡĳĳ�й㷺�Ĺ㷺�����ٸ���ٸ���ٸ�ĳĳ�־ֳ�"+"\r\n"+
"1998.05--2005.09  ĳĳʡĳĳ�й㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԸ��г�����䣺2001.08--2004.05 �����뵳Уĳĳרҵ�о�����ѧaϰ��"+"\r\n"+
"2005.09--2005.10  ĳĳʡĳĳ�㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸ���ί��ί�����г�"+"\r\n"+
"2005.10--2007.02  ĳĳʡĳ�㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸ�ĳ��ί����ǡ����г�"+"\r\n"+
"2007.02--         ĳĳʡĳ�㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸԹ㷺�Ĺ㷺�����ٸ���ٸ���ٸ�ĳ��ί����ǡ��г�"+"\r\n"+
 "�ڡ����������ίԱ";
	private static String[] nametext = {"����","���ٷŵ�aa��","���ٷŵ�aa����ٷ�","���ٷŵ���aa���ٷ�","��aa�ٷŵ�����ٷ�",
			"���ǿ������������ѯ","����aa��������bb��ѯ","�ڹѸ�����a�ڹѸ�����a�ڹѸ�����a","�ڹѸ������ٺڹѸ����ٺڹѸ�����a","�ڹѸ������ٺڹѸ����ٺڹѸ����ٰ�",
			"���ǿ������������ѯ���ǿ������������ѯ���ǿ������������ѯ"};
	/**
	 * ���� 2���� ���� �м��2���ո�      14������     1��
	 * ����9���ֽھ��� 		    14������     2��
	 * ��2��     				14������     ǰ9���ֽڸպ���5���� ��Ϊ1��  ����8���ֽ�Ϊһ��
	 * 
	 * ����2��    				12������  ���¼���
	 * 
	 * @param cell01
	 * @param name
	 */
	private static void addNameText(XWPFTableCell cell01,String name) {
		//cell01.removeParagraph(0);
		//��ȡ��Ԫ��Ŀն���
		XWPFParagraph p = cell01.getParagraphs().get(0);//cell01.addParagraph();
		//��Ԫ������
        //CTTcPr cellPr = cell01.getCTTc().addNewTcPr();
        //cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
		FontConfig fc = new FontConfig(name, "����");
		p.setAlignment(fc.getAlignment());
        XWPFRun r1 = p.createRun();
        r1.setFontSize(fc.getFontSize());//16 ����
        r1.setText(fc.getContents());
        CTPPr pPPr = getParagraphCTPPr(p);
        //�����м��
        CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing(): pPPr.addNewSpacing();
        pSpacing.setLine(fc.getLineSize());//�м��
	}
	
	
	
	/**
	 * @Description: �õ�����CTPPr
	 */
	public static CTPPr getParagraphCTPPr(XWPFParagraph p) {
		CTPPr pPPr = null;
		if (p.getCTP() != null) {
			if (p.getCTP().getPPr() != null) {
				pPPr = p.getCTP().getPPr();
			} else {
				pPPr = p.getCTP().addNewPPr();
			}
		}
		return pPPr;
	}
	
}


