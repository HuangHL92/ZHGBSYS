<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.OtherTemShowTowPageModel"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>


<script type="text/javascript">
	var ctpath = "<%=request.getContextPath() %>";
   Ext.onReady(function openfile(){
		document.getElementById("DCellWeb1").ShowSheetLabel(0,document.getElementById("DCellWeb1").GetCurSheet());
  		document.getElementById("DCellWeb1").ShowPageBreak(0);
        //���ò�֧�������϶�
        document.getElementById("DCellWeb1").AllowDragdrop = false;
   });

</script>
<script type="text/javascript" language="javascript">
var ctpath = "<%=request.getContextPath() %>";
        //��ӡԤ��
        function btn_PrePrint() 
        {	var aa = document.getElementById("DCellWeb1");
        	var countperson = 0; //ͳ������
        	var a = 0;//ѭ������
        	var b = 0;//�и�
        	var c = 0;//ѭ������
        	var d = 0;//��ȡ��Ŀ¼�˵�����
        	function start(){
        		for(;a<(�����еĲ���);a++){
        			//��A4��Ϊ210
        			b += get();//�и�
        			countperson++;
        			if(b>210){
        				countperson--;
        				acc (countperson);
        			}
        		}
        	} 
      
       
        
        	
        	setAuth(document.getElementById("DCellWeb1"));
            document.getElementById("DCellWeb1").PrintPreview(1, document.getElementById("DCellWeb1").GetCurSheet);
        }
     	//��ӡ
        function btn_Print() 
        {   
        	setAuth(document.getElementById("DCellWeb1"));
            document.getElementById("DCellWeb1").PrintSheet(true, document.getElementById("DCellWeb1").GetCurSheet);
        }
        // ����Excel
        function do_eportExcelDlg(){
        	var cell = document.getElementById("DCellWeb1");
        	setAuth(cell);
        	cell.ExportExcelDlg();
       		
        }
        //����·��ҳ���
        function openWin(){
        	radow.doEvent("openExpPathWin2");
        }
    	// ����Excel
    	function do_eportPDF(path,name){
      	    document.getElementById("DCellWeb1").ExportPdfFile( path+name+'.pdf',-1,0,document.getElementById("DCellWeb1").GetTotalSheets());
      	    if(document.getElementById("DCellWeb1").ExportPdfFile( path+name+'.pdf',-1,0,document.getElementById("DCellWeb1").GetTotalSheets())==true){
      	     			 alert("�����ɹ���");
      	     		 }
	  
    		
    	}
    	
</script>

<%
	OtherTemShowTowPageModel p = new OtherTemShowTowPageModel();
	String pre = p.aa(request);
	out.write(pre);
%>
<script type="text/javascript">
var ctpath = "<%=request.getContextPath() %>";
	function showdata(){
		setAuth(document.getElementById("DCellWeb1"));
		a();
	}
	
	
	b = 0;//�ϲ���Ԫ����
	c = 1;//��������
	var cell ;
 	var data 
	function show(value){
		
		cell = document.getElementById("DCellWeb1");
		cell.setCurSheet(0);//��ȡ��һҳ�ķ���
		cell.PrintSetMargin(100,100,100,100);//����ҳ�߾�
		cell.PrintSetOrient(1);//��ֽ����
		cell.setCurSheet(1);//��ȡ�ڶ�ҳ�ķ���
		cell.PrintSetMargin(100,100,100,100);//����ҳ�߾�
		cell.PrintSetOrient(1);//��ֽ����
		cell.PrintSetFoot('','��&Pҳ','');
		cell.SetSheetLabel(0,"����");//����ָ����ҳ��ҳǩ���ơ�
		cell.SetSheetLabel(1,"Ŀ¼");//����ָ����ҳ��ҳǩ���ơ�
		cell.SetSheetLabel(2,"����");//����ָ����ҳ��ҳǩ���ơ�
		cell.MergeCells(3,9,9,9);
		var a = 0;
		try{
			data  = eval(value);
		}catch(err){
	
		}
		///�ϲ�Ŀ¼�����ߵ�Ԫ��
		for(a = 0; a < data.length;a++){
			cell.MergeCells(2,4+a,10,4+a);
		}
		var item ;
		var mess = '';//���ƴ�ӵ���Ϣ
		var rownum = 5;
		for (var i = 0; i < data.length; i++) {
		    item = data[i];
///		        console.log(item.B0101);
		        mess = getrowline(item.B0101,item.num); 
		        cell.SetCellString(2,4+b,'1',mess);
		        cell.SetCellString(11,4+b,'1','ѡ��' +' '+item.num+' '+'��');
		        cell.SetCellDouble(13,4+b,'1',rownum);
		        rownum = rownum + parseInt(item.num)+1;//��һ������ҳÿ������������һ������
		        b++;
		}
		getrowheight();
		cell.setCurSheet(1);//��ȡ�ڶ�ҳ�ķ���
		cell.SetColHidden(13,13);
		cell.DeleteRow(4+a,46-a,1);
		cell.setCurSheet(2);//��ȡ����ҳ�ķ���
		cell.PrintSetMargin(100,100,100,100);//����ҳ�߾�
		cell.PrintSetOrient(1);//��ֽ����
	}
	function getrowheight(){
		var countperson = 0;
		var clll = document.getElementById("DCellWeb1");
		var countwww = 0;
		var pagenum = 1;
		var mulu1 = 4; //mulu ��
		var mulu2 = 5; //mulu ֵ
//		var jjjj = 1000;//���� �� ��Ŀ
		clll.SetCurSheet(2);
		var rowscount = clll.GetRows(2);
		var Height = clll.PrintGetPaperHeight(2) - (100*2); 
		for(var a = 1; a <= rowscount; a++){
			var rowheight = clll.GetRowHeight(0,a,2);
			countwww = countwww + rowheight;
			if(countwww > Height){
				pagenum = pagenum + 1;
				countwww = rowheight;
			}
			if(mulu2 == a){
				cell.SetCellString(12, mulu1,'1',pagenum + 'ҳ');
				mulu1 ++;
				mulu2 = clll.getCellDouble(13, mulu1,1);
				
			}
		}
	}
	
	//a �ǻ������ƣ�b����
	function getrowline(a,b){
		var anum = a.length;
		var bnum = b.length;
		var cnum = anum+bnum
		var line = '';
		for(var r = 0; r < 100-cnum;r++){
			line += '-';
		}
		return a +' ' + line ;
	}
	
</script>
</head>
<body>
	<div style="background:#F0F0F0">
	<table>
		<tr>
			<TD NOWRAP><A class=tbButton id=cmdFilePrintPreview title=��ӡԤ�� onclick="btn_PrePrint()" ><IMG style="align:absMiddle;" src="images/general/printpreview.gif" width="20" height="20"></A></TD>
			<td width="15" ></td>
			<TD NOWRAP><A class=tbButton id=cmdFilePrint title=��ӡ onclick="btn_Print()" ><IMG style="align:absMiddle;" src="images/general/print.gif" width="20" height="20"></A></TD>
			<td width="15" ></td>
			<TD NOWRAP><A class=tbButton id=cmdFileExport title=����Excel onclick="do_eportExcelDlg()" ><IMG style="align:absMiddle;" src="main/images/f05.png" width="16" height="20"></A></TD>
			<td width="15" ></td>
			<TD NOWRAP><A class=tbButton id=cmdFileExport title=����PDF onclick="openWin()" ><IMG style="align:absMiddle;" src="main/images/zwzpdf5.gif" width="20" height="20"></A></TD>
	</table>
	<object id="DCellWeb1" style="left: 0px; width: 100%; top: 0px; height: 95%" 
    classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" 
    codebase="softTools/cellweb5.cab#version=5.3.9.16">
    <!-- #version=5.3.9.16 --><!-- #version=5,3,8,0429 -->
    <param name="_Version" value="65536" />
    <param name="_ExtentX" value="10266" />
    <param name="_ExtentY" value="7011" />
    <param name="_StockProps" value="0" />
    </object>
	</div>
</body>
<odin:hidden property="tpid"/>
<odin:hidden property="rownum"/>
<odin:window src="/blank.htm" id="ExpPathWin2" width="420" height="159"
	title="����PDF����·��" modal="true"></odin:window> 
<script type="text/javascript" language="javascript">
setAuth(document.getElementById("DCellWeb1"));
</script>
</html>







