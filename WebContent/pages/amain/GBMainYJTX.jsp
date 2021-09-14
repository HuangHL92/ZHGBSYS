<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<style>
body{
	font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","΢���ź�",Arial,sans-serif;
    font-size: 26px;
    line-height: 1.5;
    color: #515a6e;
    background-color: #fff;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    height: 100%;
    
}
.main-section{
	padding: 0px 3% 0px 3%;
    width: 94%;
    background-color: hsla(0,0%,100%,.8);
    border-radius: 8px;
    margin: 0px 0px 0px 0px;
    overflow: auto;
    position: relative;
    height: 100%;
    display: block;
   /*  background-color: rgb(244, 252, 253) !important; */
}
.main-section-content{
 	background-image: url(pages/amain/img/yj_background.png) ;
 	background-repeat: no-repeat;
 	position: relative;
 	background-attachment: fixed;
 	background-size: cover;
 	width:100%
}
p{
	display: block;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
}
.content-title{
	text-align:center;
	height: 33px;
    font-size: 24px;
    margin-top: 20px;
    font-weight: 600;
    color: #000;
    line-height: 33px;
    background: linear-gradient(180deg,#27bcff,#4a5ba8);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.content-title span {
	
	width: 24px;
    height: 24px;
    display: inline-block;
    margin-right: 10px;
    
}

.subcontent,td{
	line-height: 32px;
    font-size: 18px;
    font-weight: bolder;
    color: #3c3c3c;
    padding-left: 35px;
}
a{
    padding: 0 15px;
    color: #2d8cf0;
    background: transparent;
    text-decoration: none;
    outline: none;
    cursor: pointer;
    transition: color .2s ease;
    background-color: transparent;
    -webkit-text-decoration-skip: objects;
}

a:hover {
    color: #57a3f3;
    outline-width: 0;
    outline: 0;
    text-decoration: none;
}
#ext-gen9{
	width:120px;
	margin-left:25px	
}

#datetime{
	width:140px;
	border:none;
	font-size:16px
}
</style>

<link rel="stylesheet" type="text/css" href="pages/amain/js/yjxx.css"> 




<section class="main-section">
	<div class="main-section-content">
		<p class="content-title">
			<span></span>���乤������
		</p>
		<p class="subcontent" style="text-align:center;">
			1.�ɲ�����������Ա
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table id="coordTable" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 25%;">-</td>
					<td class="tableTile" style="width: 25%;" >���µ���</td>
					<td class="tableTile" style="width: 25%;" >���µ���</td>
					<td class="tableTile" style="width: 25%;" >�����</td>
				</tr>
				<tr>
					<td>������</td>
					<td><a href="javascript:void(0);" class="qxcount1" ></a></td>
					<td><a href="javascript:void(0);" class="qxcount2" ></a></td>
					<td><a href="javascript:void(0);" class="qxcount3" ></a></td>
				</tr>
				<tr>
					<td>��ֱ��λ</td>
					<td><a href="javascript:void(0);" class="szcount1" ></a></td>
					<td><a href="javascript:void(0);" class="szcount2" ></a></td>
					<td><a href="javascript:void(0);" class="szcount3" ></a></td>
				</tr>
				<tr>
					<td>�����У</td>
					<td><a href="javascript:void(0);" class="gqgxcount1" ></a></td>
					<td><a href="javascript:void(0);" class="gqgxcount2" ></a></td>
					<td><a href="javascript:void(0);" class="gqgxcount3" ></a></td>
				</tr>
				<tr>
					<td>�ܼ�</td>
					<td><a href="javascript:void(0);" class="count1" ></a></td>
					<td><a href="javascript:void(0);" class="count2" ></a></td>
					<td><a href="javascript:void(0);" class="count3" ></a></td>
				</tr>
			</table>
			
		</div>
		
		
<!-- 		<p class="subcontent">
			1.<span> �ɲ���������</span>
			<a href="javascript:void(0);" class="count1" >���µ���(��)</a> 
			<a href="javascript:void(0);" class="count2" >���µ���(��)</a>
			<a href="javascript:void(0);" class="count3">����ȵ���(��)</a>
		</p> -->
		
		<p class="subcontent" style="text-align:center;">
			2.���˳��쵼��λת��ְ����Ա
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table id="coordTable" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 160;">-</td>
					<td class="tableTile" style="width: 160;" >6�¿ɰ���</td>
					<td class="tableTile" style="width: 160;" >12�¿ɰ���</td>	
					<td class="tableTile" style="width: 200;" >�������ʱ��</td>		
					<odin:dateEdit property="datetime" format="Ymd" onchange="yearchange()"  width="100" style="width:100%;margin-left:0px;" ></odin:dateEdit>	
					<odin:hidden property="searchdata"/>		
<%-- 					<odin:select2  property="searchdata"  onchange="yearchange()"  data="['1','1'],['2','2'],['3','3'],['4','4'],['5','5'],['6','6']
					,['7','7'],['8','8'],['9','9'],['10','10'],['11','11'],['12','12']"  width="100" style="background-color: rgb(230, 228, 228);"></odin:select2>					
 --%>			
 					
 				</tr>
				<tr>
					<td>��ְ</td>
					<td><a href="javascript:void(0);" class="zz6ytc" >-</a></td>
					<td><a href="javascript:void(0);" class="zz12ytc" ></a></td>
					<td colspan="2"><a href="javascript:void(0);" class="zzmytc" ></a></td>
				</tr>
				<tr>
					<td>��ְ</td>
					<td><a href="javascript:void(0);" class="fz6ytc" ></a></td>
					<td><a href="javascript:void(0);" class="fz12ytc" ></a></td>
					<td colspan="2"><a href="javascript:void(0);" class="fzmytc" ></a></td>
				</tr>
			</table>
			
		</div>
<!-- 		<p class="subcontent">
			2.<span> ���˳��쵼��λת��ְ����</span>
			<a href="javascript:void(0);" class="count7" >�й���ְ(��)</a>
			<a href="javascript:void(0);" class="count8" >�йܸ�ְ(��)</a>
		</p> -->
		<p class="subcontent" style="text-align:center;">
			3.<span> ���Ͻ�������Աְ��������Ա</span>
<!-- 			<a href="javascript:void(0);" class="count4" >��һѲ��(��)</a>
			<a href="javascript:void(0);" class="count5" >����Ѳ��(��)</a> -->
		</p>
			<div style="display: table; text-align: center;width: 100% " >
	
				<table id="coordTable" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 20%;">-</td>
					<td class="tableTile" style="width: 40%;" >һ��Ѳ��Ա</td>
					<td class="tableTile" style="width: 40%;" >����Ѳ��Ա</td>	
 					
 				</tr>
 				<tr>
					<td  style="width: 20%;">������57��</td>
					<td  style="width: 40%;" ><a href="javascript:void(0);" class="count4" ></a></td>
					<td  style="width: 40%;" ><a href="javascript:void(0);" class="count5" ></a></td>	
 					
 				</tr>
 				<tr>
					<td  style="width: 20%;">����δ��57��</td>
					<td  style="width: 40%;" ><a href="javascript:void(0);" class="count6" ></a></td>
					<td  style="width: 40%;" ><a href="javascript:void(0);" class="count7" ></a></td>	
 					
 				</tr>
 				<tr>
					<td  style="width: 20%;">��ʦ��ת�ɲ�</td>
					<td  style="width: 40%;" >-</td>
					<td  style="width: 40%;" ><a href="javascript:void(0);" class="count8" ></a></td>	
 					
 				</tr>
 				</table>

			
		</div>
		
		
		<p class="subcontent" style="text-align:center;">
			4.<span> ������ת�����</span>
<!-- 			<a href="javascript:void(0);" class="count11" >����ְ��5��(��)</a>
			<a href="javascript:void(0);" class="count12" >����ְ��8��(��)</a> 
			<a href="javascript:void(0);" class="count13" >����ְ��10��(��)</a> 
			<a href="javascript:void(0);" class="count14" >����ְ10�꼰����(��)</a> -->
		</p>
				<div style="display: table; text-align: center;width: 100% " >
	
				<table id="coordTable" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 25%;">-</td>
					<td class="tableTile" style="width: 25%;" >������������</td>
					<td class="tableTile" style="width: 25%;" >������������</td>	
 					<td class="tableTile" style="width: 25%;" >��������Ա</td>	

 				</tr>
 				<tr>
					<td  style="width: 25%;">����</td>
					<td  style="width: 25%;" ><a href="javascript:void(0);" class="bysyqjm" ></a></td>
					<td  style="width: 25%;" ><a href="javascript:void(0);" class="xysyqjm" ></a></td>
					<td  style="width: 25%;" ><a href="javascript:void(0);" class="syqry" ></a></td>
 					
 				</tr>
 				</table>

			
		</div>
		
		
		
		<p class="subcontent" style="text-align:center;">
			5.<span> ��ְʱ��ϳ��ɲ�</span>

		</p>
				<div style="display: table; text-align: center;width: 100% " >
	
				<table id="coordTable" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 20%;">-</td>
					<td class="tableTile" style="width: 20%;" >����ְ��5��</td>
					<td class="tableTile" style="width: 20%;" >����ְ��8��</td>	
 					<td class="tableTile" style="width: 20%;" >����ְ��10��</td>	
 					<td class="tableTile" style="width: 20%;" >����ְ10�꼰����</td>	
 				</tr>
 				<tr>
					<td  style="width: 20%;">����</td>
					<td  style="width: 20%;" ><a href="javascript:void(0);" class="count11" ></a></td>
					<td  style="width: 20%;" ><a href="javascript:void(0);" class="count12" ></a></td>
					<td  style="width: 20%;" ><a href="javascript:void(0);" class="count13" ></a></td>
					<td  style="width: 20%;" ><a href="javascript:void(0);" class="count14" ></a></td>	
 					
 				</tr>
 				</table>

			
			</div>
		
		<p class="content-title extrayj">
			<span></span>�ල������������
		</p>
		<p class="subcontent extrayj">
<!-- 			1.<span>�����й������</span><a href="javascript:void(0);"  >����5�׼����ϸɲ�����</a>
			<a href="javascript:void(0);"  >��ż����Ů���̰���ҵ�Ͻɳ���2000��Ԫ���ϸɲ�����</a>
 			<a href="javascript:void(0);"  >��ż����Ů�ƾӹ�(��)��ɲ�����</a>
 			<a href="javascript:void(0);"  >��ͥ����Ͷ��2000��Ԫ���ϸɲ�</a>
 			<br/> -->
 			<a href="javascript:void(0);"  onclick="openMateXF('�ŷ��йع���')" style="margin-left:120px">1.�й��ŷ����</a>
 			<br/>
 			<a href="javascript:void(0);"   onclick="openMate2('scfgb')" style="margin-left:120px">2.�ɲ��ܴ������</a>
 			<br/>
 			<a href="javascript:void(0);"  onclick="openMate2('gbstjk')" style="margin-left:120px">3.�ɲ����彡�����</a>
 			<br/>
 			<a href="javascript:void(0);"  onclick="openMateJD('��Ů�ƾӹ���')" style="margin-left:120px">4.��Ů�ƾӹ���������ĸɲ�����</a>
			<br/>
 			<a href="javascript:void(0);"  onclick="openMateGJ1('fc5t')" style="margin-left:120px">5.��������5�����ϸɲ�����</a>
			<br/>
 			<a href="javascript:void(0);"  onclick="openMateGJ1('jttz2000w')" style="margin-left:120px">6.��ͥͶ��2000�����ϸɲ�����</a>
			<br/>
 			<a href="javascript:void(0);"  onclick="openMateGJ1('jsb2000w')" style="margin-left:120px">7.���̰���ҵ�����ܶ�2000��Ԫ�����ϸɲ�����</a>
			
		</p>
		
		
		<p class="content-title">
			<span></span>�����������
		</p>
		<p class="subcontent">
<!--  			1.<span>�������ʿ���</span><a href="javascript:void(0);"  >�������</a>
 			<a href="javascript:void(0);"  >����ɲ�</a>
 			<a href="javascript:void(0);"  >һ�����</a>
 			<a href="javascript:void(0);"  >һ��ɲ�</a>
 			<br/> -->
			<span style="margin-left:130px">2020����ȿ���</span><a href="javascript:void(0);"  onclick="openMate3('��ȿ����������')">�������</a>
			<a href="javascript:void(0);"   onclick="openMate('ngkhyxgb')">����ɲ�</a>
 			<a href="javascript:void(0);"  onclick="openMate3('��ȿ���һ�����')">һ�����</a>
 			<a href="javascript:void(0);"   onclick="openMate('ngkhybgb')">һ��ɲ�</a>
			<br/>
<!-- 			3.<span>ר���</span><a href="javascript:void(0);"  >�������</a>
			<a href="javascript:void(0);"  >����ɲ�</a>
 			<a href="javascript:void(0);"  >һ�����</a>
 			<a href="javascript:void(0);"  >һ��ɲ�</a> -->
		</p>
		
		
		
		<p class="content-title">
			<span></span>�ɲ������������
		</p>
		<p class="subcontent">
		 	<a href="javascript:void(0);" style="margin-left:120px"  onclick="openMateGJ1('edugzmd')" >&nbsp;&nbsp;1.������й��쵼�ɲ�����ѧϰѧʱ��ע����</a>
 			<br/>
 			<a href="javascript:void(0);" style="margin-left:120px"  onclick="openMateGJ('edu330')" >&nbsp;&nbsp;2.�������й��쵼�ɲ��Ѳ���ѵ��ѧʱ������330ѧʱ��ע����</a>
 			<br/>
			<a href="javascript:void(0);" style="margin-left:120px" onclick="openMateGJ1('edu36')" >&nbsp;&nbsp;3.�����������е������ӳ�Ա���ۺ͵��Խ�������������36���ע����</a> 
<!-- 			<a href="javascript:void(0);" class="count13" >����ְ��10��(��)</a> 
			<a href="javascript:void(0);" class="count14" >����ְ10�꼰����(��)</a>  -->
		</p>
		

	</div>
	
	<!-- <div class="main-section-content">
		<p class="content-title">
			<span></span>�ල������������
		</p>
		<p class="subcontent">
			1.<span> �����й������</span><a href="javascript:void(0);">����5�׼����ϸɲ�����</a>
			<a href="javascript:void(0);">��ż����Ů���̰���ҵ�Ͻɳ���1000��Ԫ���ϸɲ�����</a> <a
				href="javascript:void(0);">��ż����Ů�ƾӹ�(��)��ɲ�����</a> <a
				href="javascript:void(0);">��ͥ����Ͷ��1000��Ԫ���ϸɲ�</a> <a
				href="javascript:void(0);">������˾����������׷�����</a>
		</p>
		<p class="subcontent">
			2.<span><a href="javascript:void(0);" onclick="openMate2('�ŷ��йع���')" >�ŷ��йع���</a></span>
		</p>
		<p class="subcontent">
			3.<span><a href="javascript:void(0);" onclick="openMate2('�ܴ��ָɲ����')" >�ܴ��ָɲ����</a></span>
		</p>
		<p class="subcontent">
			4.<span><a href="javascript:void(0);" onclick="openMate2('�ɲ����彡�����')" >�ɲ����彡�����</a></span>
		</p>
	</div>
	<div class="main-section-content">
		<p class="content-title">
			<span></span>�ṹ�Ըɲ��䱸����
		</p>
		<p class="subcontent">
			1.<span><a>����ɲ��䱸</a></span>
		</p>
		<p class="subcontent">
			2.<span><a>Ů�ɲ��䱸</a></span>
		</p>
		<p class="subcontent">
			3.<span><a>����ɲ��䱸</a></span>
		</p>
	</div>
	<div class="main-section-content">
		<p class="content-title">
			<span></span>�����������
		</p>
		<p class="subcontent">
			1.<span> �������ʿ���</span><a href="javascript:void(0);">�������</a> <a
				href="javascript:void(0);">����ɲ�</a><a href="javascript:void(0);">һ�����</a>
			<a href="javascript:void(0);">һ��ɲ�</a>
		</p>
		<p class="subcontent">
			2.<span> ��ȿ���</span><a href="javascript:void(0);">�������</a> <a
				href="javascript:void(0);">����ɲ�</a><a href="javascript:void(0);">һ�����</a>
			<a href="javascript:void(0);">һ��ɲ�</a>
		</p>
		<p class="subcontent">
			3.<span> ר���</span><a href="javascript:void(0);">�������</a> <a
				href="javascript:void(0);">����ɲ�</a><a href="javascript:void(0);">һ�����</a>
			<a href="javascript:void(0);">һ��ɲ�</a>
		</p>
	</div>
	<div class="main-section-content">
		<p class="content-title">
			<span></span>�ɲ������������
		</p>
		<p class="subcontent">
			1.<span><a>�������Ѳ���ѵ��ѧʱ������330ѧʱԤ����ע����</a></span>
		</p>
		<p class="subcontent">
			2.<span><a>2021��1-2���й��쵼�ɲ�ѧ�ֹ�ע����</a></span>
		</p>
	</div> -->
</section>


<script type="text/javascript">
function setCountData(adata){
	$.each(adata,function(i,dataJSON){
		$('.count'+dataJSON['type']).text(dataJSON['dl']+"��")
		.attr('YJTXSQLType','YJTXSQLType'+dataJSON['type'])
		.bind('click',function(){
			openMate('YJTXSQLType'+dataJSON['type'])
		});
		if(dataJSON['type']=='1' ){
			if(parseInt(dataJSON['dl'])>0){
				$('.count'+dataJSON['type']).text('��'+dataJSON['dl']+"��").addClass('warning');					
			}
		};
	});
	
}

function setGBDLTXData(adata){
	$.each(adata,function(i,dataJSON){
		$('.'+dataJSON['type']).text(dataJSON['count(1)']+"��")
		.attr('YJTXSQLType',dataJSON['type'])
		.bind('click',function(){
			openMate(dataJSON['type'])
		});
		if(dataJSON['type']=='szcount1' || dataJSON['type']=='qxcount1' || dataJSON['type']=='gqgxcount1' || 
			dataJSON['type']=='bysyqjm' 	){
			if(parseInt(dataJSON['count(1)'])>0){
				$('.'+dataJSON['type']).text('��'+dataJSON['count(1)']+"��").addClass('warning');		
			}
		};
	});
	
}

function yearchange(){

	radow.doEvent("yearchange");
	
}
//��ͨ�б�
function openMate(p){
	$h.openWin('gbmainListWin','pages.amain.GBMainList','��Ա�б�',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
//�ܴ��� �ɲ����彡���б�
function openMate2(p){
	$h.openWin('gbmainListWin2','pages.amain.GBMainYJList','��Ա�б�',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
//�ŷ��б�
function openMateXF(p){
	$h.openWin('gbmainListWin2','pages.amain.GBMainYJList1','��Ա�б�',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
//�����б�
function openMate3(p){
	$h.openWin('gbmainListWin3','pages.amain.GBMainYJListBZ','�����б�',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
//�ɲ������б�1
function openMateGJ(p){
	$h.openWin('gbmainListWin3','pages.amain.GBMainYJListGJ','��Ա�б�',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
//�ɲ������б�2
function openMateGJ1(p){
	$h.openWin('gbmainListWin3','pages.amain.GBMainYJListGJ1','��Ա�б�',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
//�ɲ��ල�б�
function openMateJD(p){
	$h.openWin('gbmainListWin3','pages.amain.GBMainYJListJD','��Ա�б�',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
</script>