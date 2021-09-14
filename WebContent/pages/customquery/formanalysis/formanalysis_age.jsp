<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<script src="<%=request.getContextPath()%>/pages/customquery/formanalysis/formanalysis.js"> </script>


<%--�û�����·��������ֵ�������� --%>
<%@include file="/comOpenWinInit.jsp" %>

<div id="createtable" style="overflow-y:hidden;overflow-x:hidden;overflow: auto;margin-left:15px;display:block;">
	<table  style="width:100%;" border="0">
		<col width="5%">
		<col width="15%">
		<col width="3%">
		<col width="3%">
		<col width="7%">
		<col width="67%">
		<tr height="3px">
		</tr>
		<tr>
			<odin:select2 property="zwlb" label="ְ����" codeType="ZWCELB" multiSelectWithAll="true" onchange="zwlb_func()"></odin:select2>
			<td width="3px"></td>
			<td>
				<odin:checkbox property="xianyin" label="����ȫ����" value="1" onclick="xianyin_onclick(this)"></odin:checkbox>
			</td>
			<td width="3px"></td>
			<td>
				<table>
					<tr>
						<td>
							<odin:button text="����" property="imp_excel" handler="exportData"></odin:button>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table style="width:100%;" border="0">
		<tr>
			<td colspan="3">
				<div>
					<object id="DCellWeb1" style="left: 0px; width: 964px; top: 0px; height: 440px" 
				    classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" 
				    codebase="softs/cellweb5.cab#version=5,3,8,0429">
				    <!-- #version=5.3.9.16 -->
				    <param name="_Version" value="65536" />
				    <param name="_ExtentX" value="10266" />
				    <param name="_ExtentY" value="7011" />
				    <param name="_StockProps" value="0" />
				    </object>
				</div>
			</td>
		</tr>
	</table>
</div>
<div>
	<odin:hidden property="zwlb_l"/><!-- ְ�����ѡ����ֵ -->
	<odin:hidden property="xy_zwlb"/><!-- ����ѡ������ֵ -->
	<odin:hidden property="jsonString_str"/><!-- ����������json�ַ�������ֵ -->
</div>
<script for="DCellWeb1" event="MouseDClick(col, row)" language="JavaScript">
	myMouseDClick(col, row);
</script>
<script type="text/javascript" language="javascript">
	setAuth(document.getElementById("DCellWeb1"));
</script>
<script type="text/javascript">
////ͳ����Ŀ����
var tjxm_arr = new Array(
		"",
		"",
		"",
		"",
		"20�꼰����",
		"21��",
		"22��",
		"23��",
		"24��",
		"25��",
		"26��",
		"27��",
		"28��",
		"29��",
		"30��",
		"31��",
		"32��",
		"33��",
		"34��",
		"35��",
		"36��",
		"37��",
		"38��",
		"39��",
		"40��",
		"41��",
		"42��",
		"43��",
		"44��",
		"45��",
		"46��",
		"47��",
		"48��",
		"49��",
		"50��",
		"51��",
		"52��",
		"53��",
		"54��",
		"55��",
		"56��",
		"57��",
		"58��",
		"59��",
		"60��",
		"61��",
		"62��",
		"63��",
		"64��",
		"65�꼰����",
		"ƽ������"
		);
////���˫���¼� 
function myMouseDClick(col1, row1) {
	
	//�������� 
	if(col1==0||col1==""||col1==" "||col1==null||col1=="undefined"||col1<3||col1>49){
		return;
	}
	if(row1==0||row1==""||row1==" "||row1==null||row1=="undefined"||row1<4||row1>(parseInt(array_row[array_row.length-1].split(",")[1])+1)){
		return;
	}
	//��ֵΪ0 �� ��
	var value=DCellWeb_tj.GetCellString(col1,row1,0);
	if(value==0||value==""||value==" "||value==null||value=="undefined"){
		return;
	}
	
	var title1="";
	var title2="";
	title1=DCellWeb_tj.GetCellString(2,row1,0);
	for(var i=0;i<array_row.length;i++){//С��
		if(row1==(parseInt(array_row[i].split(",")[0])-1)){
			title1=DCellWeb_tj.GetCellString(1,row1,0);
		}
	}
	if(row1==4||row1==208){//�ܼ�
		title1=DCellWeb_tj.GetCellString(1,row1,0);
	}
	if(col1==3){
		title2=DCellWeb_tj.GetCellString(col1,2,0);
	}else{
		title2=DCellWeb_tj.GetCellString(col1,3,0);
	}
	var title=title1+"-"+title2;
	var dwid=document.getElementById("subWinIdBussessId2").value;
	var	cxtj=dwid+"$"+$('#zwlb_combo').attr("value")+"$"+col1+"$"+row1+"$"+title;
	//ϵͳʱ��
	var date_time = new Date();
	var time=date_time.getHours()+ date_time.getMinutes()+date_time.getSeconds();
	$h.openWin('formanalysiszge'+time,'pages.customquery.formanalysis.formanalysisagelist',title+'-��άͳ��',950,550,cxtj,'<%=request.getContextPath()%>');
}
var ctpath="<%=request.getContextPath()%>";


function jcHeightWidth(){
	document.getElementById("DCellWeb1").style.height=document.body.offsetHeight-60;//�ײ����������+��ѯ�����߶� =60
	document.getElementById("DCellWeb1").style.width=document.body.offsetWidth;
}
Ext.onReady(function openfile(){
    try{
		document.getElementById("DCellWeb1").openfile(ctpath+"\\template\\gbjbqktjnlry.cll","");
    }catch(err){
    	//alert("��ʾ:�����ذ�װ������!��ʹ��IE�����(����ģʽ)��(����ϵϵͳ����ԱԶ��Э��)!");
    	//alert("�밲װ������!");
    }
    //���ø�ѡ�����¾���
  	checkboxvl("xianyin");
  	document.getElementById("createtable").parentNode.parentNode.style.overflow='hidden';
  	window.onresize=jcHeightWidth;
});
//���� odin:checkbox �� property���� ���ø�ѡ������¾�����ʽ
function checkboxvl(id){
	try{
		var node_66;
		var node_55;
		var node_5_height;
		var node_6_height;
		node_66=$("#"+id).parent();
		node_6_height=node_66[0].offsetHeight;
		node_55=node_66.children();
		node_5_height=node_55[0].offsetHeight;
		var mt=(node_6_height-node_5_height)/2;
		$("#"+id).parent().parent().parent().css({"top": mt+"px"});
	}catch(err){
	}
}
</script>
<script type="text/javascript">
//���json����
var json_obj="";
//��ʼ������ 
var DCellWeb_tj="";
var col_xho_start=3;
var col_xho_end=50;
//json�ַ���

function json_func(){
   //jsonstr,lengthstr
   var jsonstr=document.getElementById("jsonString_str").value;
   DCellWeb_tj=document.getElementById("DCellWeb1");//��ȡ������
   var obj ="";
   try{
	   obj = eval('(' + jsonstr + ')');
   }catch(err){
   }
   json_obj=obj;
   DCellWeb_tj.SetFixedCol(1, 2); 
   DCellWeb_tj.SetFixedRow(1, 3);
   
	DCellWeb_tj.InsertCol (DCellWeb_tj.GetCols( 0 ), 1, 0);//׷��һ��
	DCellWeb_tj.SetColHidden(DCellWeb_tj.GetCols( 0 )-1,DCellWeb_tj.GetCols( 0 )-1);//�������һ��
   for(var i=0;i<obj.length;i++){////�������� ���� ѭ��
   	   var a0221=obj[i].a0221;//����
   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
   			//continue;//������Ϊ��
   		 	a0221='QT';//�����Ϊ�շ������һ��ͳ��
   	   }
   	   a0221=map[a0221];//����
   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
   		a0221=parseInt(array_row[array_row.length-1].split(",")[1])+1;//���һ��
   		var i_col=3;
   		var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
   		var value_last=obj[i].heji==0?temp:parseInt(obj[i].heji)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//�ϼ�
 	   i_col=i_col+1;	
 	   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 			temp=0;
 		}
 		value_last=obj[i].xydy20==0?temp:parseInt(obj[i].xydy20)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//20������
 	   	i_col=i_col+1;
 	   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
			temp=0;
		}
		value_last=obj[i].dy21==0?temp:parseInt(obj[i].dy21)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 		i_col=i_col+1;
		temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 			temp=0;
 		}
 		value_last=obj[i].dy22==0?temp:parseInt(obj[i].dy22)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy23==0?temp:parseInt(obj[i].dy23)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy24==0?temp:parseInt(obj[i].dy24)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy25==0?temp:parseInt(obj[i].dy25)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy26==0?temp:parseInt(obj[i].dy26)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy27==0?temp:parseInt(obj[i].dy27)+parseInt(temp);
 			DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy28==0?temp:parseInt(obj[i].dy28)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy29==0?temp:parseInt(obj[i].dy29)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy30==0?temp:parseInt(obj[i].dy30)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy31==0?temp:parseInt(obj[i].dy31)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy32==0?temp:parseInt(obj[i].dy32)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy33==0?temp:parseInt(obj[i].dy33)+parseInt(temp);
	   	DCellWeb_tj.SetCellString(
	   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy34==0?temp:parseInt(obj[i].dy34)+parseInt(temp);
	   	DCellWeb_tj.SetCellString(
	   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy35==0?temp:parseInt(obj[i].dy35)+parseInt(temp);
		DCellWeb_tj.SetCellString(
   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;	
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy36==0?temp:parseInt(obj[i].dy36)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy37==0?temp:parseInt(obj[i].dy37)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy38==0?temp:parseInt(obj[i].dy38)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy39==0?temp:parseInt(obj[i].dy39)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy40==0?temp:parseInt(obj[i].dy40)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy41==0?temp:parseInt(obj[i].dy41)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy42==0?temp:parseInt(obj[i].dy42)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		  	i_col=i_col+1;
 		  	temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy43==0?temp:parseInt(obj[i].dy43)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy44==0?temp:parseInt(obj[i].dy44)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy45==0?temp:parseInt(obj[i].dy45)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy46==0?temp:parseInt(obj[i].dy46)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   value_last= obj[i].dy47==0?temp:parseInt(obj[i].dy47)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy48==0?temp:parseInt(obj[i].dy48)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		 	i_col=i_col+1;
 		 	temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy49==0?temp:parseInt(obj[i].dy49)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy50==0?temp:parseInt(obj[i].dy50)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy51==0?temp:parseInt(obj[i].dy51)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy52==0?temp:parseInt(obj[i].dy52)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy53==0?temp:parseInt(obj[i].dy53)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy54==0?temp:parseInt(obj[i].dy54)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 	    	    		   	
    		i_col=i_col+1;
    		temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
     		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
     			temp=0;
     		}
     		value_last=obj[i].dy55==0?temp:parseInt(obj[i].dy55)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 	    	    		   	
    		i_col=i_col+1;
    		temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
     		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
     			temp=0;
     		}
     		value_last=obj[i].dy56==0?temp:parseInt(obj[i].dy56)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   		
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy57==0?temp:parseInt(obj[i].dy57)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy58==0?temp:parseInt(obj[i].dy58)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy59==0?temp:parseInt(obj[i].dy59)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy60==0?temp:parseInt(obj[i].dy60)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy61==0?temp:parseInt(obj[i].dy61)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy62==0?temp:parseInt(obj[i].dy62)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy63==0?temp:parseInt(obj[i].dy63)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy64==0?temp:parseInt(obj[i].dy64)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dydy65==0?temp:parseInt(obj[i].dydy65)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=(obj[i].pjnl==0||obj[i].pjnl=='null')?temp:returnFloat(parseInt(obj[i].pjnl)*parseInt(obj[i].heji_zs)+parseInt(temp));
 	 		
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].heji_zs==0?temp:parseInt(obj[i].heji_zs)+parseInt(temp);
 	 	
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//�����ֶβ�Ϊ�յ����� (ƽ��������ݴ����ݼ���)
   	   }else{
   	   var i_col=3;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].heji==0?"":obj[i].heji);//�ϼ�
	   i_col=i_col+1;		
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].xydy20==0?"":obj[i].xydy20);//20������
	   	i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy21==0?"":obj[i].dy21);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy22==0?"":obj[i].dy22);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy23==0?"":obj[i].dy23);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy24==0?"":obj[i].dy24);//
			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy25==0?"":obj[i].dy25);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy26==0?"":obj[i].dy26);//
		   	i_col=i_col+1;
			DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy27==0?"":obj[i].dy27);//
			i_col=i_col+1;	
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy28==0?"":obj[i].dy28);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy29==0?"":obj[i].dy29);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy30==0?"":obj[i].dy30);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy31==0?"":obj[i].dy31);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy32==0?"":obj[i].dy32);//
			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy33==0?"":obj[i].dy33);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy34==0?"":obj[i].dy34);//
		   	i_col=i_col+1;
			DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy35==0?"":obj[i].dy35);//
			i_col=i_col+1;		
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy36==0?"":obj[i].dy36);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy37==0?"":obj[i].dy37);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy38==0?"":obj[i].dy38);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy39==0?"":obj[i].dy39);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy40==0?"":obj[i].dy40);//
			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy41==0?"":obj[i].dy41);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy42==0?"":obj[i].dy42);//
		  	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy43==0?"":obj[i].dy43);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy44==0?"":obj[i].dy44);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy45==0?"":obj[i].dy45);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy46==0?"":obj[i].dy46);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy47==0?"":obj[i].dy47);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy48==0?"":obj[i].dy48);//
		 	i_col=i_col+1;
	   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy49==0?"":obj[i].dy49);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(i_col,a0221,0,obj[i].dy50==0?"":obj[i].dy50);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy51==0?"":obj[i].dy51);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy52==0?"":obj[i].dy52);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy53==0?"":obj[i].dy53);//
	    	  		   	
		   		
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy54==0?"":obj[i].dy54);//
	    	    		   	
   		i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy55==0?"":obj[i].dy55);//
	    	    		   	
   		i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy56==0?"":obj[i].dy56);//
		   		
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy57==0?"":obj[i].dy57);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy58==0?"":obj[i].dy58);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy59==0?"":obj[i].dy59);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy60==0?"":obj[i].dy60);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy61==0?"":obj[i].dy61);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy62==0?"":obj[i].dy62);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy63==0?"":obj[i].dy63);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy64==0?"":obj[i].dy64);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dydy65==0?"":obj[i].dydy65);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].pjnl==0?"":returnFloat(obj[i].pjnl));//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].heji_zs==0?"":obj[i].heji_zs);//
   	   }
   	
	}/// for end
	//���¼������һ�е�ƽ������ 
	//���һ�е�ƽ������֮�� 
	 var age_total=DCellWeb_tj.GetCellString(tjxm_arr.length-1,parseInt(array_row[array_row.length-1].split(",")[1])+1,0);
	if(age_total=='undefined' || age_total=="" || age_total==null||age_total==" "){
		age_total=0;
	} 
	//���һ�е�����
	 var num_per=DCellWeb_tj.GetCellString(3,parseInt(array_row[array_row.length-1].split(",")[1])+1,0);   
	if(num_per=='undefined' || num_per=="" || num_per==null||num_per==" "){
		num_per=1;
	}
	DCellWeb_tj.SetCellString(
	tjxm_arr.length-1,parseInt(array_row[array_row.length-1].split(",")[1])+1,0,returnFloat(age_total/num_per)); //
	//С��
   for(var i=0;i<array_row.length;i++){
		total_tj(parseInt(array_row[i].split(",")[0])-1,
				parseInt(array_row[i].split(",")[0]),
				parseInt(array_row[i].split(",")[1]));
	}
	//�ܼ� 
	total_zj();
	
	
	//���� ȫ��Ϊ0����
	displayzeroinit();
	//����ҳ�治�ɱ༭
	DCellWeb_tj.WorkbookReadonly=true;

	//��������grid�߶�
	
}

function returnFloat(value){
	/// var value=Math.round(parseFloat(value)*100)/100;
	 var xsd=value.toString().split(".");
	 if(xsd.length==1){
	 value=value.toString()+".00";
	 return value;
	 }
	 if(xsd.length>1){
	 if(xsd[1].length<2){
	 value=value.toString()+"0";
	 }
	 return value;
	 }
	}
//�ܼ�
function total_zj(){
	   //��3�е���ͳ�� 
	   var temp_zj_=parseInt(total_zj_ittt(3));
		DCellWeb_tj.SetCellString(
			3,4,0,temp_zj_==0?"":temp_zj_ );
		
	   var _zj_total=0;
	   for(var i=col_xho_start;i<=col_xho_end;i++){
			
			_zj_total=total_zj_ittt(i);
		    
			DCellWeb_tj.SetCellString(i,4,0,_zj_total==0?"":_zj_total );
		}
}

function total_zj_ittt(i){
	   var zj_total=0;
	   var zj_temp_total=0;
	   for(var j=0;j<array_row.length;j++){
		   zj_temp_total=DCellWeb_tj.GetCellString2(i, parseInt(array_row[j].split(",")[0])-1, 0);
			if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
				zj_temp_total=0;
			}
			zj_total=zj_total+parseInt(zj_temp_total);
	   }
	   //�������һ�е�ֵ
	   var lastvalue=DCellWeb_tj.GetCellString2(i, parseInt(array_row[array_row.length-1].split(",")[1])+1, 0);
	   if(lastvalue=='undefined' || lastvalue=="" || lastvalue==null||lastvalue==" "){
		   lastvalue=0;
	   }
	   zj_total=zj_total+parseInt(lastvalue);
	 	
		if(i==col_xho_end){
			zj_total=pj_38_zj(i);
			
		}
		return zj_total;
}

//�ܼ�  ƽ������
function pj_38_zj(i){
	   var zj_total=0;
	   var zj_temp_total=0;//��ǰ��ƽ������
	   var zj_temp_3=0;//��ǰ��������
	 
	   var zrs=0;//������
	   for(var j=0;j<array_row.length;j++){//ѭ��ְ�����
		   for(var n=parseInt(array_row[j].split(",")[0]);n<=parseInt(array_row[j].split(",")[1]);n++){//ѭ��ְ��ȼ�
				zj_temp_total=DCellWeb_tj.GetCellString2(i, n, 0);
				if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
					zj_temp_total=0;
				}
				//zj_temp_3=r_total_r(n);
				zj_temp_3=DCellWeb_tj.GetCellString2(tjxm_arr.length, n, 0);
				//zj_temp_3=DCellWeb_tj.GetCellString2((i+1), n, 0);
				if(zj_temp_3=='undefined' || zj_temp_3=="" || zj_temp_3==null||zj_temp_3==" "){
					zj_temp_3=0;
				}
				zrs=zrs+parseInt(zj_temp_3);
				zj_total=zj_total+parseInt(zj_temp_total)*parseInt(zj_temp_3);
		   }
	    }
		if(zrs=='undefined' || zrs=="" || zrs==null||zrs==" "||zrs==0){
			zrs=1;
		}

		return returnFloat(Math.round(parseInt(zj_total)/parseInt(zrs)*100)/100);
}

	//ͳ��С��
	function total_tj(row_xj,row_s,row_e){//row_xj С��ͳ���� row_s��ʼ�� row_e ������
		
		var temp_v="";
		for(var i=col_xho_start;i<=(col_xho_end+1);i++){//��ѭ��
			
			temp_v=col_total(i,row_s,row_e);
			DCellWeb_tj.SetCellString(
		   			i,row_xj,0,temp_v==0?"":temp_v );
		}
	}
	
	function col_total(col_xj,row_s,row_e){
	    var col_num=0;
	    var v=0;
	    for(var i=row_s;i<=row_e;i++){
			v=DCellWeb_tj.GetCellString2(col_xj, i, 0);
			if(v=='undefined' || v=="" || v==null||v==" "){
				v=0;
			}
			col_num=col_num+parseInt(v);
	    }
		if(col_xj==col_xho_end){//ƽ��ֵ
			var znl=0;
			var zrs=0; 
			var temp_pjnl=0;
			var dqhrs=0;
			for(var i=row_s;i<=row_e;i++){//��
				temp_pjnl=DCellWeb_tj.GetCellString2(col_xj, i, 0);//��ǰ��ƽ������
				if(temp_pjnl==""||temp_pjnl==" "||temp_pjnl=="undefined"||temp_pjnl==null){
					temp_pjnl=0;
	  			}
				dqhrs=DCellWeb_tj.GetCellString2((col_xho_end+1), i, 0);//����
				if(dqhrs==""||dqhrs==" "||dqhrs=="undefined"||dqhrs==null){
					dqhrs=0;
	  			}
				zrs=zrs+parseInt(dqhrs);
				znl=znl+parseInt(temp_pjnl)*parseInt(dqhrs);
			}
			if(zrs==""||zrs==" "||zrs=="undefined"||zrs==null||zrs==0){
				zrs=1;
			}
			col_num=returnFloat(Math.round(parseInt(znl)/parseInt(zrs)*100)/100);
		}
	    return col_num;
	}

//���������д�4�п�ʼ
function yx_all(){
	  DCellWeb_tj.SetRowHidden(4, parseInt(array_row[array_row.length-1].split(",")[1])+1);
}

function displayzeroinit(){
	  var v=0;
	  var i_v=parseInt(array_row[array_row.length-1].split(",")[1]+1);
		 for(var i=4;i<=i_v;i++){//��
			 v=DCellWeb_tj.GetCellString(3, i, 0);//�жϺϼ��Ƿ�Ϊ��  ��Ϊ�� ��Ϊ��ȫ��
			 if(v=='undefined' || v=="" || v==null||v==" "||v==0){
				 DCellWeb_tj.SetRowHidden(i, i);
			 }else{
				 DCellWeb_tj.SetRowUnHidden(i, i);
			 }
			 
		 }
}

//����ȫ����
function displayzero(xy){
	 
	  zwlb_xy();
	
}

//��ʾ������
function xsall(){
	DCellWeb_tj.SetRowUnhidden(4, parseInt(array_row[array_row.length-1].split(",")[1])+1);
}
function xs_zwlb_zero(){
	  zwlb_xy();
}
</script>
<script type="text/javascript">
//����ѡ��ְλ����ʾ ͳ�� 
var zwlb_data ="";
var xy_zwlb="1";
function zwlb_xy(){
	  xy_zwlb=document.getElementById("xy_zwlb").value;
	  zwlb_data=document.getElementById("zwlb_l").value;
	  clear4();
	  if(zwlb_data=='undefined' || zwlb_data=="" || zwlb_data==null || zwlb_data==" "){
		  if(xy_zwlb=='0'){
			  xsall();//��ʾ������
		  }else{
			  displayzeroinit();//����ȫ0��
		  }
		  total_zj_xy();//�ܼ�
		  
		  DCellWeb_tj.SetRowUnhidden(4, 4);//��ʾ
		  return;
	  }
	  
	  if(zwlb_data=='all'||zwlb_data=='ALL'){
		  if(xy_zwlb=='0'){
		  	xsall();//��ʾ������
		  }else{
			  displayzeroinit();//����ȫ0��
		  }
		  total_zj();//����ͳ��
		 
		  DCellWeb_tj.SetRowUnhidden(4, 4);//��ʾ
		 
		  return;
	  }
	  var strs= new Array(); //����һ���� �洢ְ�������� 
	  var strs_m= new Array(); //����һ����  �洢���� 
	  strs=zwlb_data.split(","); //�ַ��ָ�
	  DCellWeb_tj.SetRowhidden(4, parseInt(array_row[array_row.length-1].split(",")[1])+1);//����������
	  for (var i=0;i<strs.length ;i++ ) { 
		  strs_m=map_zwlb[strs[i]].split(",");
		  DCellWeb_tj.SetRowUnhidden(strs_m[0], strs_m[1]);//��ʾ
		  if(strs_m[1]!=(parseInt(array_row[array_row.length-1].split(",")[1])+1)){//�������һ�в���ʾС��
			  //С��
			  DCellWeb_tj.SetRowUnhidden(parseInt(strs_m[0])-1, parseInt(strs_m[0])-1);//��ʾ
		  }
		  //����ͳ��
		  DCellWeb_tj.SetRowUnhidden(4, 4);//��ʾ
		  total_zj_xy();//�ܼ�
		  
		  if(xy_zwlb=='1'){//������
			  displayzero_zwlb(strs_m[0], strs_m[1]);//���� 0��
		  }
	  } 
}
function displayzero_zwlb(start,end){
	  var v=0;
		 for(var i=parseInt(start);i<=parseInt(end);i++){//��
			 v=DCellWeb_tj.GetCellString(3, i, 0);//�жϺϼ��Ƿ�Ϊ��  ��Ϊ�� ��Ϊ��ȫ��
			 if(v=='undefined' || v=="" || v==null||v==" "||v==0){
				 DCellWeb_tj.SetRowHidden(i, i);
			 }
		 }
		 v=DCellWeb_tj.GetCellString(3, parseInt(start)-1, 0);//�жϺϼ��Ƿ�Ϊ��  ��Ϊ�� ��Ϊ��ȫ��
		 if(v=='undefined' || v=="" || v==null||v==" "||v==0){
			 DCellWeb_tj.SetRowHidden(parseInt(start)-1,parseInt(start)-1);
		 }
}
//��յ����е�����
function clear4(){
	  for(var i=col_xho_start;i<=col_xho_end;i++){
		  DCellWeb_tj.SetCellString(i,4,0,'');
	  }
}

//�ܼ�
function total_zj_xy(){
		
	   var _zj_total=0;
	   for(var i=col_xho_start;i<=col_xho_end;i++){

			_zj_total=total_zj_ittt_xy(i);
		    //ż���� С��
			DCellWeb_tj.SetCellString(i,4,0,_zj_total==0?"":_zj_total );
		}
}

function total_zj_ittt_xy(i){
	   var zj_total=0;
	   var zj_temp_total=0;
	   for(var j=0;j<array_row.length;j++){
		   if(DCellWeb_tj.IsRowHidden(parseInt(array_row[j].split(",")[0])-1,0)==false){//����ʾ
				zj_temp_total=DCellWeb_tj.GetCellString(i, parseInt(array_row[j].split(",")[0])-1,0);
				if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
						zj_temp_total=0;
				}
				zj_total=zj_total+parseInt(zj_temp_total);
		 	}
	   }
	   //�������һ�е�ֵ
	   var lastvalue=DCellWeb_tj.GetCellString2(i, parseInt(array_row[array_row.length-1].split(",")[1])+1, 0);
	   if(lastvalue=='undefined' || lastvalue=="" || lastvalue==null||lastvalue==" "){
		   lastvalue=0;
	   }
	   zj_total=zj_total+parseInt(lastvalue);
	 	if(i==col_xho_end){
	 		zj_total=total_zj_ittt_pjnl(i);
	 	}
		return zj_total;
}

function total_zj_ittt_pjnl(i){
	  var zj_total=0;
	   var zj_temp_total=0;//��ǰ��ƽ������
	   var zj_temp_3=0;//��ǰ��������
	 
	   var zrs=0;//������
	   for(var j=0;j<array_row.length;j++){
		   if(DCellWeb_tj.IsRowHidden(parseInt(array_row[j].split(",")[0])-1,0)==false){//����ʾ
			   for(var n=parseInt(array_row[j].split(",")[0]);n<=parseInt(array_row[j].split(",")[1]);n++){//ѭ��ְ��ȼ�
					
					zj_temp_total=DCellWeb_tj.GetCellString2(i, n, 0);//��ǰ��ƽ������
					if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
						zj_temp_total=0;
					}
					zj_temp_3=DCellWeb_tj.GetCellString2(tjxm_arr.length, n, 0);
				    //zj_temp_3=r_total_r(n);//��ǰ��������
					if(zj_temp_3=='undefined' || zj_temp_3=="" || zj_temp_3==null||zj_temp_3==" "){
						zj_temp_3=0;
					}
					zrs=zrs+parseInt(zj_temp_3);
					zj_total=zj_total+parseInt(zj_temp_total)*parseInt(zj_temp_3);
			   }
		 	}
	   }
	 	if(zrs=='undefined' || zrs=="" || zrs==null||zrs==" "||zrs==0){
			zrs=1;
		}

		return returnFloat(Math.round(parseInt(zj_total)/parseInt(zrs)*100)/100);
		
}
//ƽ�������Ӧ����
function r_total_r(row){
	   var zj_temp_3=0;
	   var zj_temp_cell=0;
	   for(var j=col_xho_start+1;j<=col_xho_end-1;j++){//��
			zj_temp_cell=DCellWeb_tj.GetCellString2(j, row, 0);
			if(zj_temp_cell=='undefined' || zj_temp_cell=="" || zj_temp_cell==null||zj_temp_cell==" "){
				zj_temp_cell=0;
			}
			zj_temp_3=zj_temp_3+parseInt(zj_temp_cell);
	 }
	   return zj_temp_3;
}
</script>
<script type="text/javascript">

function xianyin_onclick(obj){
	if(document.getElementById("xianyin").checked==true){//����ȫ����
		document.getElementById("xy_zwlb").value=1;
		zwlb_xy();
	}else if(document.getElementById("xianyin").checked==false){//��ʾȫ����
		document.getElementById("xy_zwlb").value=0;
		zwlb_xy();
	}
}
</script>
<script type="text/javascript">
function exportData(){
	DCellWeb_tj.ExportExcelDlg();
}
</script>