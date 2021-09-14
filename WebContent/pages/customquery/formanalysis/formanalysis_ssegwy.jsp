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
	for(var i=6;i<=29;i++){//��
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
var cxtj="sse$"+row1+"$"+col1+"$"+groupid+"$"+year;
$h.openWin('formanalysisssy','pages.customquery.formanalysis.hztjFormAnalysis','',950,550,cxtj,'<%=request.getContextPath()%>');
}
DCellWeb_tj="";
Ext.onReady(function openfile(){
    try{
		document.getElementById("DCellWeb1").openfile("<%=request.getContextPath()%>"+"\\template\\hztj\\J1703106.cll","");
    }catch(err){
    }
});

function json_func(param){
	var jsonstr=document.getElementById("jsonString_str"+param).value;
    DCellWeb_tj=document.getElementById("DCellWeb1");//��ȡ������
	DCellWeb_tj.SetRowhidden(2, 2);
    var obj ="";
    try{
	    obj = eval('(' + jsonstr + ')');
    }catch(err){
    }
    for(var i=0;i<obj.length;i++){////�������� ���� ѭ��
    	var a0221=obj[i].a0221;//����
   	    if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
   	    	if(param=='5'){
   	    		
   	    	}else{
   				continue;//������Ϊ��
   	    	}
   	    }
    	if(param=='1'){
    		a0221='6';//����
    	}else if(param=='2'){
    		a0221=map2[a0221];//����
    	}else if(param=='3'){
    		a0221='16';//����
    	}else if(param=='4'){
    		a0221=map4[a0221];//����
    		json1_func(a0221,obj,i);
    		continue;
    	}else if(param=='5'){
    		a0221='29';//����
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
    if(param=='5'){
   		sumHj1();
   		sumHj2();
   	}
}
function sum(temp1,temp2){
	if(temp1==""||temp1=='undefined' ||temp1==null||temp1==" "){
		temp1=0;
	}
	if(temp2==""||temp2=='undefined' ||temp2==null||temp2==" "){
		temp2=0;
	}
	var num=(parseInt(temp1)+parseInt(temp2));
	if(num==0){
		return '';
	}
	return num;
}
function json1_func(a0221,obj,i){
	var i_col=3;
	var temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	
	var temp2=(obj[i].heji==0||obj[i].heji=='null')?"":obj[i].heji;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//�ϼ�
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].nv==0||obj[i].nv=='null')?"":obj[i].nv;
		DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//Ů
		i_col++;
		temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
		temp2=(obj[i].sm==0||obj[i].sm=='null')?"":obj[i].sm;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//��������
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zd==0||obj[i].zd=='null')?"":obj[i].zd;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//�й���Ա
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].bs==0||obj[i].bs=='null')?"":obj[i].bs;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//��ʿ
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].ss==0||obj[i].ss=='null')?"":obj[i].ss;		
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//˶ʿ
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].xs==0||obj[i].xs=='null')?"":obj[i].xs;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//ѧʿ
	i_col++;
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].yjs==0||obj[i].yjs=='null')?"":obj[i].yjs;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//�о���
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].dxbk==0||obj[i].dxbk=='null')?"":obj[i].dxbk;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//��ѧ����
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].dxzz==0||obj[i].dxzz=='null')?"":obj[i].dxzz;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//��ѧר��
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zz==0||obj[i].zz=='null')?"":obj[i].zz;
	DCellWeb_tj.SetCellString(
				i_col,a0221,0,sum(temp1,temp2));//��ר������
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy2==0||obj[i].zhccxy2=='null')?"":obj[i].zhccxy2;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//����2��
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy3==0||obj[i].zhccxy3=='null')?"":obj[i].zhccxy3;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//2������3��
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy4==0||obj[i].zhccxy4=='null')?"":obj[i].zhccxy4;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//3������4��
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy5==0||obj[i].zhccxy5=='null')?"":obj[i].zhccxy5;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//4������5��
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy6==0||obj[i].zhccxy6=='null')?"":obj[i].zhccxy6;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//5������6��
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy7==0||obj[i].zhccxy7=='null')?"":obj[i].zhccxy7;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//6������7��
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy8==0||obj[i].zhccxy8=='null')?"":obj[i].zhccxy8;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//7������8��
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy12==0||obj[i].zhccxy12=='null')?"":obj[i].zhccxy12;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//8������12��
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy15==0||obj[i].zhccxy15=='null')?"":obj[i].zhccxy15;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//12������15��
	i_col++;
	temp1=DCellWeb_tj.GetCellString(i_col,a0221,0);
	temp2=(obj[i].zhccxy16==0||obj[i].zhccxy16=='null')?"":obj[i].zhccxy16;
	DCellWeb_tj.SetCellString(
  				i_col,a0221,0,sum(temp1,temp2));//15�꼰����
}
function sumHj2(){
	for(var i=3;i<=24;i++){
		if(i==10){
			continue;
		}
		var sum=colSum2(i);
		DCellWeb_tj.SetCellString( i,16,0,sum);
	}
}
function colSum2(col){
	var sum=0;
	for(var i=17;i<=28;i++){
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
function sumHj1(){
	for(var i=3;i<=24;i++){
		if(i==10){
			continue;
		}
		var sum=colSum1(i);
		DCellWeb_tj.SetCellString( i,6,0,sum);
	}
}
function colSum1(col){
	var sum=0;
	for(var i=7;i<=15;i++){
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
//���񾯲쾯Աְ������ 
var map2={	
		'20':'7',//һ������  
		'21':'8',//��������  
		'22':'9',//��������  
		'23':'10',//�ļ�����  
		'24':'11',//һ����Ա  
		'25':'12',//������Ա  
		'26':'13',//������Ա  
		'27':'14',//��������Ա 
		'28':'15'//���� 	  
			};
//���쵼ְ��
var map4={	
		'301':'17',//��ϯ�󷨹�
		'302':'18',//һ���󷨹�
		'303':'19',//�����󷨹�  
		'304':'20',//һ���߼�����
		'305':'21',//�����߼�����
		'306':'22',//�����߼�����
		'307':'23',//�ļ��߼�����
		'308':'24',//һ������
		'309':'25',//��������
		'310':'26',//��������
		'311':'27',//�ļ�����
		'312':'28',//�弶����
		'401':'17',//��ϯ�����
		'402':'18',//һ�������
		'403':'19',//���������
		'404':'20',//һ���߼�����
		'405':'21',//�����߼�����
		'406':'22',//�����߼�����
		'407':'23',//�ļ��߼�����
		'408':'24',//һ������
		'409':'25',//��������
		'410':'26',//��������
		'411':'27',//�ļ�����
		'412':'28'//�弶����
			};
</script>