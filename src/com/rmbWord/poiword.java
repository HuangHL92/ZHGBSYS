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
			CustomXWPFDocument doc = new CustomXWPFDocument(new FileInputStream("H:\\itexttest\\任免表.docx")); 
		    
			List<XWPFTable> tables= doc.getTables();
			//第二个表格
			XWPFTable tab = tables.get(1);
			//姓名输入
			XWPFTableCell cell01 = tab.getRow(0).getCell(1);
			//XWPFStyles style = doc.createStyles();
			addNameText(cell01,nametext[9]);
			
			//tab.getRow(0).setHeight(1000);
			
			
			
			//合并单元格
			XWPFTableCell cell71 = tab.getRow(4).getCell(2);
			//tab.getRow(8).setHeight(1000);//1.76cm
			CTTc cttc = cell71.getCTTc();
			CTTcPr cpr = cttc.addNewTcPr();
			cpr.addNewVMerge().setVal(STMerge.RESTART);//合并起始单元格
			
			XWPFTableCell cell51 = tab.getRow(5).getCell(2);
			CTTc cttc1 = cell51.getCTTc();
			CTTcPr cpr1 = cttc1.addNewTcPr();
			cpr1.addNewVMerge().setVal(STMerge.CONTINUE);//结束单元格
			cell71.getParagraphs().get(0).createRun().setText("fdsfds");
			
			
			
			XWPFTableCell cell111 = tab.getRow(11).getCell(1);
			addjlText(cell111,jianli);
			
			
			//家庭成员 模版制定10行， 根据实际情况删除行
			XWPFTable tab2 = tables.get(2);
			tab2.getRow(13).getCell(1).setText("33333");
			//tab2.removeRow(8);
			
			//图片
			XWPFTableCell cell06 = tab.getRow(0).getCell(6);
			List<XWPFParagraph> paragraphList = cell06.getParagraphs();
			XWPFParagraph paragraph = paragraphList.get(0);
			//doc.getAllPictures().
			String picId = doc.addPictureData(new FileInputStream( 
					"H:\\itexttest\\rmb.jpg"), XWPFDocument.PICTURE_TYPE_JPEG); 
			doc.createPicture(paragraph, 0, 136, 185, "");
			paragraph.removeRun(0);
			
			System.out.println(doc.getParagraphs().get(doc.getParagraphs().size()-1).getText());
			
			
			//合并
			CustomXWPFDocument doc2 = new CustomXWPFDocument(new FileInputStream("H:\\itexttest\\aaaa.docx")); 
			doc.createTable();
			doc.setTable(3, doc2.getTables().get(0));
			//加一个空段落，否则2个表格会被合并
			XWPFParagraph ph = doc.createParagraph();
			CTPPr pPPr = getParagraphCTPPr(ph);
	        CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing(): pPPr.addNewSpacing();
	        pSpacing.setLine(BigInteger.ZERO);//行间距
			//
	        doc.createTable();
	        doc.setTable(4, doc2.getTables().get(1));
	        
	        //合并后文档没法更改，重新加载
	        ByteArrayOutputStream out1 = new ByteArrayOutputStream();  
	        doc.write(out1);  
	        doc = new CustomXWPFDocument(new ByteArrayInputStream(out1.toByteArray()));
	        
			//被合并的文档添加照片
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
		//pInd.setHangingChars(BigInteger.valueOf(1000-25));//10个字
		//pInd.setLeftChars(BigInteger.valueOf(40));//0.4个字
		
		
	}
	private static String jianli = "1973.07--1977.09  某某省某某广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈豆腐干梵蒂冈县某某镇小学教师"+"\r\n"+
"1977.09--1979.09  某某省某某县某vcxvxdfdsfds第三个是否规范 梵蒂冈个放到该罚的广泛大概某镇初中教师"+"\r\n"+
"1979.09--1988.11  某某省某某市委党校教师（其间：1985.10--1988.07在某某省委党校电教大专班学习）"+"\r\n"+
"1988.11--1993.07  某某省某某市委宣传部干事、副科长、科长（1987.09--1992.07在某某大学某某系某某专业学习）"+"\r\n"+
"1993.07--1995.11  某某省某某市某广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈某局副局长"+"\r\n"+
"1995.11--1998.05  某某省某某市广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈某某局局长"+"\r\n"+
"1998.05--2005.09  某某省某某市广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈副市长（其间：2001.08--2004.05 在中央党校某某专业研究生班学a习）"+"\r\n"+
"2005.09--2005.10  某某省某某广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈市委常委、副市长"+"\r\n"+
"2005.10--2007.02  某某省某广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈某市委副书记、代市长"+"\r\n"+
"2007.02--         某某省某广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈广泛的广泛大概梵蒂冈梵蒂冈梵蒂冈某市委副书记、市长"+"\r\n"+
 "第××届中央候补委员";
	private static String[] nametext = {"打算","多少放到aa额","多少放到aa额多少放","多少放到额aa多少放","多aa少放到额多少放",
			"但是看见啊巴拿马查询","但是aa见啊巴拿bb查询","黑寡妇多少a黑寡妇多少a黑寡妇多少a","黑寡妇啊多少黑寡妇多少黑寡妇多少a","黑寡妇啊多少黑寡妇多少黑寡妇多少啊",
			"但是看见啊巴拿马查询但是看见啊巴拿马查询但是看见啊巴拿马查询"};
	/**
	 * 姓名 2个字 居中 中间加2个空格      14号字体     1行
	 * 大于9个字节居左 		    14号字体     2行
	 * 满2行     				14号字体     前9个字节刚好满5个字 则为1行  否则8个字节为一行
	 * 
	 * 超过2行    				12号字体  重新计算
	 * 
	 * @param cell01
	 * @param name
	 */
	private static void addNameText(XWPFTableCell cell01,String name) {
		//cell01.removeParagraph(0);
		//获取单元格的空段落
		XWPFParagraph p = cell01.getParagraphs().get(0);//cell01.addParagraph();
		//单元格属性
        //CTTcPr cellPr = cell01.getCTTc().addNewTcPr();
        //cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
		FontConfig fc = new FontConfig(name, "姓名");
		p.setAlignment(fc.getAlignment());
        XWPFRun r1 = p.createRun();
        r1.setFontSize(fc.getFontSize());//16 三号
        r1.setText(fc.getContents());
        CTPPr pPPr = getParagraphCTPPr(p);
        //设置行间距
        CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing(): pPPr.addNewSpacing();
        pSpacing.setLine(fc.getLineSize());//行间距
	}
	
	
	
	/**
	 * @Description: 得到段落CTPPr
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


