<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>

<%--�û�����·��������ֵ�������� --%>
<%@include file="/comOpenWinInit.jsp" %>
<div align="right">
	<table>
		<tr>
			<td>
				<table>
					<tr>
						<odin:dateEdit property="tjtime" label="ͳ�ƽ�ֹʱ��"></odin:dateEdit>
						<odin:hidden property="timetj"/>
					</tr>
				</table>
			</td>
			<td width="24px"></td>
			<td>
				<odin:button text="ͳ��" handler="tjfunc"></odin:button>
			</td>
			<td width="24px"></td>
			<td>
				<odin:button text="����" property="imp_excel" handler="exportData"></odin:button>
				<odin:hidden property="jsonString_str1"/>
				<odin:hidden property="jsonString_str2"/>
				<odin:hidden property="jsonString_str3"/>
				<odin:hidden property="jsonString_str4"/>
				<odin:hidden property="jsonString_str5"/>
				<odin:hidden property="jsonString_str6"/>
				<odin:hidden property="groupid"/>
			</td>
		</tr>
	</table>
</div>
<div>
	<object id="DCellWeb1" style="left: 0px; width: 1170px; top: 0px; height: 480px" 
    classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" 
    codebase="softs/cellweb5.cab#version=5,3,8,0429">
    <!-- #version=5.3.9.16 -->
    <param name="_Version" value="65536" />
    <param name="_ExtentX" value="10266" />
    <param name="_ExtentY" value="7011" />
    <param name="_StockProps" value="0" />
    </object>
</div>
<script for="DCellWeb1" event="MouseDClick(col, row)" language="JavaScript">
	myMouseDClick(col, row);
</script>
<script type="text/javascript" language="javascript">
	setAuth(document.getElementById("DCellWeb1"));
</script>
<script type="text/javascript">
function exportData(){
	DCellWeb_tj=document.getElementById("DCellWeb1");//��ȡ������
	DCellWeb_tj.ExportExcelDlg();
}
</script>
<script type="text/javascript">
var ctpath="<%=request.getContextPath()%>";
function tjfunc(){
	DCellWeb_tj=document.getElementById("DCellWeb1");//��ȡ������
	for(var i=6;i<=24;i++){//��
		for(var j=3;j<=24;j++){//��
			if(j==10){
				continue;
			}
			DCellWeb_tj.SetCellString(
	   				j,i,0,"");
		}
	}
	radow.doEvent("init");
}
////���˫���¼� 
function myMouseDClick(col1, row1) {
//�������� 
if(col1==0||col1==""||col1==" "||col1==null||col1=='10'||col1=="undefined"||col1<3||col1>24){
return;
}
if(row1==0||row1==""||row1==" "||row1==null||row1=="undefined"||row1<6||row1>29){
return;
}
//��ֵΪ0 �� ��
var value=DCellWeb_tj.GetCellString(col1,row1,0);
if(value==0||value==""||value==" "||value==null||value=="undefined"){
return;
}
var groupid=document.getElementById("groupid").value;
var year=document.getElementById("timetj").value;
var cxtj="sycz$"+row1+"$"+col1+"$"+groupid+"$"+year;
$h.openWin('formanalysisssy','pages.customquery.formanalysis.hztjFormAnalysis','',950,550,cxtj,'<%=request.getContextPath()%>');
}
DCellWeb_tj="";
Ext.onReady(function openfile(){
    try{
		document.getElementById("DCellWeb1").openfile("<%=request.getContextPath()%>"+"\\template\\hztj\\J1702054.cll","");
    }catch(err){
    }
});

function json_func(param){
	var jsonstr=document.getElementById("jsonString_str"+param).value;
    DCellWeb_tj=document.getElementById("DCellWeb1");//��ȡ������
   	DCellWeb_tj.SetRowhidden(2, 2);
   	DCellWeb_tj.SetCurSheet(0);
   	DCellWeb_tj.SetColUnhidden(0, 0);
   	DCellWeb_tj.SetRowUnhidden(0, 0);
   	DCellWeb_tj.CalcManaually(true);
	//DCellWeb_tj.SetSheetHideZero(1,1);
    var obj ="";
    try{
	    obj = eval('(' + jsonstr + ')');
    }catch(err){
    }
    for(var i=0;i<obj.length;i++){////�������� ���� ѭ��
    	var a0221=obj[i].a0221;//����
   	    if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
   			continue;//������Ϊ��
   	    }
    	if(param=='1'){
    		a0221='6';//����
    	}else if(param=='2'){
    		a0221=map2[a0221];//����
    	}else if(param=='3'){
    		a0221=map3[a0221];//����
    	}else if(param=='4'){
    		a0221=map4[a0221];;//����
    	}
    	var i_col=3;
    	DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].heji==0||obj[i].heji=='null')?"":obj[i].heji);//�ϼ�
    	i_col++;
   		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].nv==0||obj[i].nv=='null')?"":obj[i].nv);//Ů
   		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].sm==0||obj[i].sm=='null')?"":obj[i].sm);//��������
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].zd==0||obj[i].zd=='null')?"":obj[i].zd);//�й���Ա
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].bs==0||obj[i].bs=='null')?"":obj[i].bs);//��ʿ
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].ss==0||obj[i].ss=='null')?"":obj[i].ss);//˶ʿ
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].xs==0||obj[i].xs=='null')?"":obj[i].xs);//ѧʿ
		i_col++;
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].yjs==0||obj[i].yjs=='null')?"":obj[i].yjs);//�о���
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].dxbk==0||obj[i].dxbk=='null')?"":obj[i].dxbk);//��ѧ����
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].dxzz==0||obj[i].dxzz=='null')?"":obj[i].dxzz);//��ѧר��
		i_col++;
		DCellWeb_tj.SetCellString(
   				i_col,a0221,0,(obj[i].zz==0||obj[i].zz=='null')?"":obj[i].zz);//��ר������
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy2==0||obj[i].zhccxy2=='null')?"":obj[i].zhccxy2);//����2��
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy3==0||obj[i].zhccxy3=='null')?"":obj[i].zhccxy3);//2������3��
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy4==0||obj[i].zhccxy4=='null')?"":obj[i].zhccxy4);//3������4��
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy5==0||obj[i].zhccxy5=='null')?"":obj[i].zhccxy5);//4������5��
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy6==0||obj[i].zhccxy6=='null')?"":obj[i].zhccxy6);//5������6��
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy7==0||obj[i].zhccxy7=='null')?"":obj[i].zhccxy7);//6������7��
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy8==0||obj[i].zhccxy8=='null')?"":obj[i].zhccxy8);//7������8��
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy12==0||obj[i].zhccxy12=='null')?"":obj[i].zhccxy12);//8������12��
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy15==0||obj[i].zhccxy15=='null')?"":obj[i].zhccxy15);//12������15��
		i_col++;
		DCellWeb_tj.SetCellString(
	  				i_col,a0221,0,(obj[i].zhccxy16==0||obj[i].zhccxy16=='null')?"":obj[i].zhccxy16);//15�꼰����
    }
	if(param=='4'){
   		sumHj();
   	} 
}
function sumHj(){
	for(var i=3;i<=24;i++){
		if(i==10){
			continue;
		}
		var sum=colSum(i);
		DCellWeb_tj.SetCellString( i,6,0,sum);
	}
}
function colSum(col){
	var sum=0;
	for(var i=7;i<=24;i++){
		var temp=DCellWeb_tj.GetCellString(col,i,0);
		if(temp!=""&&temp!=" "&&temp!=null&&temp!='undefined'){
			sum=sum+parseInt(temp);
		}
	}
	if(sum==0){
		return '';
	}else{
		return sum;
	}
}
var map2={	
		'1A11':'7',//ʡ������ְ
		'1A12':'8',//ʡ������ְ
		'1A50':'21',//��Ա 
		'1A60':'22',//����Ա 
		'1A98':'23',//��������Ա
		'1A99':'24'//���� 
			};
//�쵼ְ��
var map3={	
		'1A21':'9',//���ּ���ְ
		'1A22':'11',//���ּ���ְ
		'1A31':'13',//�ش�����ְ 
		'1A32':'15',//�ش�����ְ 
		'1A41':'17',//��Ƽ���ְ 
		'1A42':'19'//����Ƽ���ְ
			};
//���쵼ְ��
var map4={	
		'1A21':'10',//���ּ���ְ
		'1A22':'12',//���ּ���ְ
		'1A31':'14',//�ش�����ְ 
		'1A32':'16',//�ش�����ְ 
		'1A41':'18',//��Ƽ���ְ 
		'1A42':'20'//����Ƽ���ְ
			};
</script>