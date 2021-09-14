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
    font-size: 18px;
    font-weight: bolder;
    color: #3c3c3c;
    padding-left: 35px;
}
a{
    /* padding: 0 15px; */
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
.coordTable{
	max-width: none!important;
	border: 3px solid #1890FF!important;
	width: 95%;
	border-color:#1890FF;
}
.coordTable th{
	background: #CAE8EA ; 
	text-align: center;
	height:40px;
	border-color:#1890FF;
}

.coordTable th,.coordTable td{
	font-family: ����,Times New Roman ;
	font-weight: bold;
	border:1px solid;
	border-color: rgb(162 162 162);
	padding: 2px;
}
.tableTile{
	font-size: 16px;
	background-color: #DAEAFD!important;
	font-family: ����!important ;
}
.coordTable td{
	text-align: center;
	height:40px;
	border-color:#1890FF;
	background-color: rgb(248,248,248);
}
.classBT{
	border-top: 3px solid rgb(152 152 152)!important;
}
.classBR{
	border-right: 3px solid rgb(152 152 152)!important;
}
.warning{
	color:red
}

tr[class^="dw-rowdata"] td{
	background-color: rgb(255,255,255)!important;
}
.toclick{
cursor: pointer;
}
</style>


<script type="text/javascript">
function openMate(p){
	$h.openWin('zbxqmateWin','pages.amain.GBMainZBXQMate','��Ա�б�',1410,900,'','<%=request.getContextPath()%>',null,p,true);
}
</script>


<section class="main-section">
	<div class="main-section-content">
		<p class="content-title">
			<span></span>ָ������
		</p>
		<p class="subcontent" style="text-align:center;">
			1.�ƽ���������չ�������
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 50%;">���</td>
					<td class="tableTile" style="width: 50%;" >����</td>
				</tr>
				<tr>
					<td>2021</td>
					<td><a href="javascript:void(0);" class="zb1" ></a></td>
				</tr>
				
			</table>
			
		</div>
		
		
		<p class="subcontent" style="text-align:center;">
			2.��ֱ��λ�쵼�ɲ��й�ָ�ָ꣨��2��3��
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb2" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 16%;">��λ</td>
					<td class="tableTile" colspan="2">��ֱ��λ�쵼���ӳ�Ա����</td>
					<td class="tableTile" colspan="2" >��ֱ��λ�쵼���ӳ�Ա��45�����Ҹɲ�</td>
					<td class="tableTile" colspan="2" >��ֱ��λ�쵼���ӳ�Ա��40�����Ҽ����¸ɲ�</td>
					<td class="tableTile" colspan="2">��ֱ��λ�����쵼�ɲ�����</td>
					<td class="tableTile" colspan="2" >��ֱ��λ�����쵼�ɲ���40�����¸ɲ�</td>
					<td class="tableTile" colspan="2" >��ֱ��λ�����쵼�ɲ���35�����Ҽ����¸ɲ�</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 7%;">ְ��</td>
					<td class="tableTile" style="width: 7%;">ʵ��</td>
					<td class="tableTile" style="width: 7%;">����</td>
					<td class="tableTile" style="width: 7%;">ռ��</td>
					<td class="tableTile" style="width: 7%;">����</td>
					<td class="tableTile" style="width: 7%;">ռ��</td>
					<td class="tableTile" style="width: 7%;">ְ��</td>
					<td class="tableTile" style="width: 7%;">ʵ��</td>
					<td class="tableTile" style="width: 7%;">����</td>
					<td class="tableTile" style="width: 7%;">ռ��</td>
					<td class="tableTile" style="width: 7%;">����</td>
					<td class="tableTile" style="width: 7%;">ռ��</td>
				</tr>
			</table>
			
		</div>
		
		
		
		
		
		<p class="subcontent" style="text-align:center;">
			4.�����أ��У������й�ָ�ָ꣨��4��5��16��
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb4" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 9%;">��λ</td>
					<td class="tableTile" colspan="3" >�������ӳ�Ա����</td>
					<td class="tableTile" colspan="2" >������ְ��40�����Ҽ����¸ɲ�</td>
					<td class="tableTile" colspan="2" >�����쵼������40�����Ҽ����¸ɲ�</td>
					<td class="tableTile" colspan="2" >�������򣨽ֵ���������ְ�����ĵ����쵼���ӳ�Ա</td>
					<td class="tableTile" colspan="2" >����Χ�ڣ��ؼ������쵼���ӳ�Ա�о������򣨽ֵ���������ְ�����ﵽ50%���ϵ�������</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 8%;">ְ��</td>
					<td class="tableTile" style="width: 8%;">ʵ��</td>
					<td class="tableTile" style="width: 8%;">������ְ</td>
					<td class="tableTile" style="width: 8%;">����</td>
					<td class="tableTile" style="width: 8%;">ռ��</td>
					<td class="tableTile" style="width: 8%;">����</td>
					<td class="tableTile" style="width: 8%;">ռ��</td>
					<td class="tableTile" style="width: 8%;">����</td>
					<td class="tableTile" style="width: 9%;">ռ��</td>
					<td class="tableTile" style="width: 9%;">����</td>
					<td class="tableTile" style="width: 9%;">ռ��</td>
				</tr>
				<tr>
					<td b0111="0" >ȫ��</td>
					<td><a href="javascript:void(0);" class="zb4a1" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a2" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a11" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a3" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a4" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a5" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a6" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a7" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a8" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a9" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a10" ></a></td>
				</tr>
				
			</table>
			
		</div>
		
		
		<p class="subcontent" style="text-align:center;">
			5.�����쵼�����й�ָ�ָ꣨��6��7��8��9��
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb5" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 10%;">��λ</td>
					<td class="tableTile" colspan="2" >���򣨽ֵ����쵼���ӳ�Ա����</td>
					<td class="tableTile" colspan="2" >���򣨽ֵ����쵼������35�����¸ɲ�</td>
					<td class="tableTile" colspan="4" >���򣨽ֵ���������ְ��35�����¸ɲ�</td>
					<td class="tableTile" rowspan="2" style="width: 10%;">���򣨽ֵ����쵼������30�����¸ɲ�����</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">������ְ</td>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">ռ��</td>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">ռ��</td>
					<td class="tableTile" style="width: 10%;">�������ռ��</td>
					<td class="tableTile" style="width: 10%;">����������ְռ��</td>
				</tr>
				
				
			</table>
			
		</div>
		
		
		<p class="subcontent" style="text-align:center;">
			6.�ֵ��쵼�����й�ָ�ָ꣨��6��7��8��9��
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb6" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 10%;">��λ</td>
					<td class="tableTile" colspan="2" >���򣨽ֵ����쵼���ӳ�Ա����</td>
					<td class="tableTile" colspan="2" >���򣨽ֵ����쵼������35�����¸ɲ�</td>
					<td class="tableTile" colspan="4" >���򣨽ֵ���������ְ��35�����¸ɲ�</td>
					<td class="tableTile" rowspan="2" style="width: 10%;">���򣨽ֵ����쵼������30�����¸ɲ�����</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">������ְ</td>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">ռ��</td>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">ռ��</td>
					<td class="tableTile" style="width: 10%;">�������ռ��</td>
					<td class="tableTile" style="width: 10%;">����������ְռ��</td>
				</tr>
				
				
			</table>
			
		</div>
		
		
		<p class="subcontent" style="text-align:center;">
			7.���򣨽ֵ����쵼�����й�ָ�ָ꣨��6��7��8��9��
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb7" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 10%;">��λ</td>
					<td class="tableTile" colspan="2" >���򣨽ֵ����쵼���ӳ�Ա����</td>
					<td class="tableTile" colspan="2" >���򣨽ֵ����쵼������35�����¸ɲ�</td>
					<td class="tableTile" colspan="4" >���򣨽ֵ���������ְ��35�����¸ɲ�</td>
					<td class="tableTile" rowspan="2" style="width: 10%;">���򣨽ֵ����쵼������30�����¸ɲ�����</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">������ְ</td>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">ռ��</td>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">ռ��</td>
					<td class="tableTile" style="width: 10%;">�������ռ��</td>
					<td class="tableTile" style="width: 10%;">����������ְռ��</td>
				</tr>
				
				
			</table>
			
		</div>
		
		<p class="subcontent" style="text-align:center;">
			8.�ṹ�Ըɲ���Ů�ɲ�������ɲ����й�ָ�ָ꣨��17��18��19��20��
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb8" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 10%;" >��λ</td>
					<td class="tableTile" colspan="3" >���ص���������Ů�ɲ�����</td>
					<td class="tableTile" rowspan="2" style="width: 10%;" >�м�����������������</td>
					<td class="tableTile" colspan="2" >�м��������������䱸Ů�ɲ����쵼����</td>
					<td class="tableTile" colspan="3">���ؼ���Э�쵼�����е���ɲ�</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">��ί</td>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">ռ��</td>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">����</td>
					<td class="tableTile" style="width: 10%;">ռ��</td>
				</tr>
				<tr>
					<td b0111='0'>ȫ��</td>
					<td><a href="javascript:void(0);" class="zb8a1" ></a></td>
					<td><a href="javascript:void(0);" class="zb8a2" ></a></td>
					<td><a href="javascript:void(0);" class="zb8a3" ></a></td>
					<td><a href="javascript:void(0);" class="zb8a4" ></a></td>
					<td><a href="javascript:void(0);" class="zb8a5" ></a></td>
					<td><a href="javascript:void(0);" class="zb8a6" ></a></td>
					<td>-</td>
					<td>-</td>
					<td>-</td>
				</tr>
				
			</table>
			
		</div>
		
		<p class="subcontent" style="text-align:center;">
			9.�ɲ��ճ������й�ָ�ָ꣨��10��12��13��21��22��27��28��
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb9" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 6%;">��λ</td>
					<td class="tableTile" colspan="2" >������40�������йܸɲ�ȫ���ƴ�ѧ����ѧ��</td>
					<td class="tableTile" colspan="2" >������40��������ֱ��λ�����쵼�ɲ�ȫ���ƴ�ѧ����ѧ��</td>
					<td class="tableTile" colspan="2" >������40���������أ��У����쵼�ɲ�ȫ���ƴ�ѧ����ѧ��</td>
					<td class="tableTile" colspan="2" >����������쵼�ɲ�ȫ���ƴ�ѧ����ѧ��</td>
					<td class="tableTile" rowspan="2" style="width: 6%;">��¼ѡ��������</td>
					<td class="tableTile" colspan="4">���������쵼����</td>
					<td class="tableTile" rowspan="2" style="width: 6%;">��ί����ĸɲ����µ������ֻ���</td>
					<td class="tableTile" rowspan="2" style="width: 6%;">ѡ�������ۺ�Ӧ��ʡ���ع�ͨ��</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 6%;">����</td>
					<td class="tableTile" style="width: 6%;">����ռ��</td>
					<td class="tableTile" style="width: 6%;">����</td>
					<td class="tableTile" style="width: 6%;">����ռ��</td>
					<td class="tableTile" style="width: 6%;">����</td>
					<td class="tableTile" style="width: 6%;">����ռ��</td>
					<td class="tableTile" style="width: 6%;">����</td>
					<td class="tableTile" style="width: 6%;">����ռ��</td>
					<td class="tableTile" style="width: 6%;">��ְ��</td>
					<td class="tableTile" style="width: 6%;">��ȱְ��</td>
					<td class="tableTile" style="width: 8%;">��ȱռ��</td>
					<td class="tableTile" style="width: 8%;">ȱ�����������Ϲؼ���λ�쵼�ɲ���</td>
				</tr>
				<tr b0111='0'>
					<td>ȫ��</td>
					<td><a href="javascript:void(0);" class="zb9r1a1" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a2" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a3" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a4" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a5" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a6" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a7" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a8" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a9" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a10" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a11" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a12" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a13" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a14" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a15" ></a></td>
				</tr>
				
				
			</table>
			
		</div>
		
		
		<p class="subcontent" style="text-align:center;">
			10.�ɲ��ල�й�ָ�ָ꣨��23��24��25��
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb10" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 25%;">��λ</td>
					<td class="tableTile" style="width: 25%;">���걻�鴦�ı�������ɲ���Ϊ��5�������εı���</td>
					<td class="tableTile" style="width: 25%;">��һ���������顱ѡ�����˹��������</td>
					<td class="tableTile" style="width: 25%;">�����й�����˲鲻һ����</td>
					
				</tr>
				<tr>
					<td>ȫ��</td>
					<td><a href="javascript:void(0);" class="zb10r1a1" ></a></td>
					<td><a href="javascript:void(0);" class="zb10r1a2" ></a></td>
					<td><a href="javascript:void(0);" class="zb10r1a3" ></a></td>
				</tr>
				
				
			</table>
			
		</div>
		
		
	</div>	
		
		
</section>


<script type="text/javascript">
function addZBXX4(datajson){
	//console.log(datajson);
	var html = "";
	var ri = 2;
	$.each(datajson,function(i,row){
		html += "<tr b0111='"+(row['b0111'])+"'>"+
		'<td>'+row['b0101']+'</td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a1" >'+row['zs']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a2" >'+row['max']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a9" >'+row['jcrz']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a3" >'+row['a0201eage']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a4" >'+
		(Math.round((row['a0201eage'])/row['a0201e']*10000)/100.00)+
		'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a5" >'+(row['age42'])+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a6" >'+(Math.round(row['age42']/row['max']*10000)/100.00)+'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a7" >'+(row['dzzz'])+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a8" >'+(Math.round(row['dzzz']/row['max']*10000)/100.00)+'%</a></td>'+
		'<td>-</td><td>-</td>'+
		"</tr>";
		ri++;
	});
	$('.tablezb4').append(html);
}

function addZBXX7(datajson,tableclass,b0101,ri,b0111){
	//console.log(datajson);
	var html = "";
	$.each(datajson,function(i,row){
		html += "<tr b0111='"+(row['b0111']||b0111)+"'>"+
		'<td>'+(b0101||row['b0101'])+'</td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a1" >'+row['max']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a2" >'+row['dzzz']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a3" >'+row['age35']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a4" >'+(Math.round(row['age35']/row['max']*10000)/100.00)+'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a5" >'+(row['age35dzzz'])+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a6" >'+(Math.round(row['age35dzzz']/row['dzzz']*10000)/100.00)+'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a7" >'+(Math.round(row['age35dw']/row['dzzz']*10000)/100.00)+'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a8" >'+(Math.round(row['age35zf']/row['dzzz']*10000)/100.00)+'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a9" >'+row['age30']+'</a></td>'+
		"</tr>";
		ri++;
	});
	$(tableclass).append(html);
}

function addZBXX8(datajson,tableclass){
	//console.log(datajson);
	var html = "";
	var ri = 1;
	$.each(datajson,function(i,row){
		html += "<tr b0111='"+(row['b0111'])+"'>"+
		'<td>'+row['b0101']+'</td>'+
		'<td><a href="javascript:void(0);" class="zb8r'+ri+'a1" >'+row['����Ů�ɲ�����']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb8r'+ri+'a2" >'+row['��ίŮ�ɲ�����']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb8r'+ri+'a3" >'+row['����Ů�ɲ�����']+'</a></td>'+
		'<td>-</td>'+
		'<td>-</td>'+
		'<td>-</td>'+
		'<td><a href="javascript:void(0);" class="zb8r'+ri+'a4" >'+row['��Э�ɲ�']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb8r'+ri+'a5" >'+(row['��Э����ɲ�'])+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb8r'+ri+'a6" >'+(Math.round(row['��Э����ɲ�']/row['��Э�ɲ�']*10000)/100.00)+'%</a></td>'+
		"</tr>";
		ri++;
	});
	$(tableclass).append(html);
}

function addZBXX9_szqb(datajson,tableclass,b0101,ri,b0111,async){
	var toclick = b0101?" class='toclick first-show' clsname='"+tableclass.replace('.','')+"' ":"";
	//չ���ı����ʽ
	var trclass = async?"class='dw-rowdata"+b0111+"'":""
	var html = "";
	$.each(datajson,function(i,row){
		row['��ְ��'] = row['��ְ��']==undefined?'--':row['��ְ��'];
		row['��ֱ��ȱ'] = row['��ֱ��ȱ']==undefined?'--':row['��ֱ��ȱ'];
		row['�й�������40ȫ����'] = row['�й�������40ȫ����']==undefined?'--':row['�й�������40ȫ����'];
		row['��40����ȫ����'] = row['��40����ȫ����']==undefined?'--':row['��40����ȫ����'];
		row['�й�ȫ����'] = row['�й�ȫ����']==undefined?'--':row['�й�ȫ����'];
		html += "<tr b0111='"+(row['b0111']||b0111)+"' "+trclass+">"+
		'<td '+toclick+' >'+(b0101||row['b0101'])+'</td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a1" >'+row['�й�������40ȫ����']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a2" >'+((row['�й�������40']==undefined||row['�й�������40']=='0')?'--':(Math.round(row['�й�������40ȫ����']/row['�й�������40']*10000)/100.00+"%"))+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a3" >'+row['��40����ȫ����']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a4" >'+((row['������40����']==undefined||row['������40����']=='0')?'--':(Math.round(row['��40����ȫ����']/row['������40����']*10000)/100.00+"%"))+'</a></td>'+
		'<td>-</td>'+
		'<td>-</td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a7" >'+row['�й�ȫ����']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a8" >'+((row['�йܸɲ�']==undefined||row['�йܸɲ�']=='0')?'--':Math.round(row['�й�ȫ����']/row['�йܸɲ�']*10000)/100.00+"%")+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a9" ></a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a10" >'+row['��ְ��']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a11" >'+row['��ֱ��ȱ']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a12" >'+(row['��ְ��']=='--'||row['��ְ��']=='0'?'--':(Math.round(row['��ֱ��ȱ']/row['��ְ��']*10000)/100.00+"%"))+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a13" ></a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a14" ></a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a15" ></a></td>'+
		"</tr>";
		ri++;
	});
	if(async){
		$(html).insertAfter($(tableclass+" tr[b0111="+b0111+"]"));
	}else{
		$(tableclass).append(html);
	}
}



function addZBXX9_qxqb(datajson,tableclass,b0101,ri,b0111,async){
	var toclick = b0101?" class='toclick first-show' clsname='"+tableclass.replace('.','')+"' ":"";
	//չ���ı����ʽ
	var trclass = async?"class='dw-rowdata"+b0111+"'":""
	var html = "";
	$.each(datajson,function(i,row){
		html += "<tr b0111='"+(row['b0111']||b0111)+"' "+trclass+">"+
		'<td '+toclick+' >'+(b0101||row['b0101'])+'</td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a1" >'+row['�й�������40ȫ����']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a2" >'+(row['�й�������40ȫ����']=='0'?'0':(Math.round(row['�й�������40ȫ����']/row['�й�������40']*10000)/100.00))+'%</a></td>'+
		'<td>-</td>'+
		'<td>-</td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a5" >'+(row['������40����ȫ����']||0)+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a6" >'+((row['���ش���������40']==undefined||row['���ش���������40']=='0')?'0':(Math.round(row['������40����ȫ����']/row['���ش���������40']*10000)/100.00))+'%</a></td>'+
		
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a7" >'+row['�й�ȫ����']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a8" >'+(Math.round(row['�й�ȫ����']/row['�йܸɲ�']*10000)/100.00)+'%</a></td>'+
		
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a9" ></a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a10" >'+row['��ְ��']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a11" >'+row['���ؿ�ȱ']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a12" >'+(row['��ְ��']=='0'?'0':(Math.round(row['���ؿ�ȱ']/row['��ְ��']*10000)/100.00))+'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a13" ></a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a14" ></a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a15" ></a></td>'+
		"</tr>";
		ri++;
	});
	if(async){
		$(html).insertAfter($(tableclass+" tr[b0111="+b0111+"]"));
	}else{
		$(tableclass).append(html);
	}
}

//(row['���ش���������40']==undefined||row['���ش���������40']=='0')?'0':
function addZBXX2(datajson,tableclass,b0101,ri,b0111,async){
	var toclick = b0101?" class='toclick first-show' clsname='"+tableclass.replace('.','')+"' ":"";
	//չ���ı����ʽ
	var trclass = async?"class='dw-rowdata"+b0111+"'":""
	var html = "";
	$.each(datajson,function(i,row){
		html += "<tr b0111='"+(row['b0111']||b0111)+"' "+trclass+">"+
		'<td '+toclick+' >'+(b0101||row['b0101'])+'</td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a1" >'+(row['��ְ��']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a2" >'+(row['��ֱ�йܸɲ�']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a3" >'+(row['��ֱ�йܸɲ�45']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a4" >'+((row['��ֱ�йܸɲ�']==undefined||row['��ֱ�йܸɲ�']=='0')?'--':Math.round(row['��ֱ�йܸɲ�45']/row['��ֱ�йܸɲ�']*10000)/100.00+'%')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a5" >'+(row['��ֱ�йܸɲ�40']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a6" >'+((row['��ֱ�йܸɲ�']==undefined||row['��ֱ�йܸɲ�']=='0')?'--':Math.round(row['��ֱ�йܸɲ�40']/row['��ֱ�йܸɲ�']*10000)/100.00+'%')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a7" >'+(row['����ְ��']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a8" >'+(row['��ֱ�����ɲ�']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a9" >'+(row['��ֱ�����ɲ�40']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a10" >'+((row['��ֱ�����ɲ�']==undefined||row['��ֱ�����ɲ�']=='0')?'--':Math.round(row['��ֱ�����ɲ�40']/row['��ֱ�����ɲ�']*10000)/100.00+'%')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a11" >'+(row['��ֱ�����ɲ�35']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a12" >'+((row['��ֱ�����ɲ�']==undefined||row['��ֱ�����ɲ�']=='0')?'--':Math.round(row['��ֱ�����ɲ�35']/row['��ֱ�����ɲ�']*10000)/100.00+'%')+'</a></td>'+
		"</tr>";
		ri++;
	});
	if(async){
		$(html).insertAfter($(tableclass+" tr[b0111="+b0111+"]"));
	}else{
		$(tableclass).append(html);
	}
	
}


//�󶨷���
function addZBXXClick(){
	$('td:nth-child(3),td:nth-child(4),td:nth-child(6),td:nth-child(9),td:nth-child(10),td:nth-child(12)',".tablezb2 tr:gt(1)").unbind('click').bind('click',function(){
    	var p = {maximizable:false,resizable:false};
    	p['colIndex'] = $(this).index();
    	//alert($(this).parent().index())
    	p['b0111'] = $(this).parent().attr('b0111');
    	p['query_type']="��ֱ��λ�쵼�ɲ��й�ָ�ָ꣨��2��3��";
    	//alert(p['colIndex'])
    	//alert(p['b0111'])
    	openMate(p);
    });
	
	
	$('td:nth-child(3),td:nth-child(5),td:nth-child(4),td:nth-child(7),td:nth-child(9)',".tablezb4 tr:gt(1)").unbind('click').bind('click',function(){
    	var p = {maximizable:false,resizable:false};
    	p['colIndex'] = $(this).index();
    	//alert($(this).parent().index())
    	p['b0111'] = $(this).parent().attr('b0111');
    	p['query_type']="�����أ��У������й�ָ�ָ꣨��4��5��16��";
    	//alert(p['colIndex'])
    	//alert(p['b0111'])
    	openMate(p);
    });
	
	
	$('td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(6),td:nth-child(8),td:nth-child(9),td:nth-child(10)',".tablezb7 tr:gt(1),.tablezb5 tr:gt(1),.tablezb6 tr:gt(1)").unbind('click').bind('click',function(){
    	var p = {maximizable:false,resizable:false};
    	p['colIndex'] = $(this).index();
    	//alert($(this).parent().index())
    	p['b0111'] = $(this).parent().attr('b0111');
    	var table = $(this).parent().parent().parent();
    	if(table.hasClass('tablezb7')){
    		p['query_type']="���򣨽ֵ����쵼�����й�ָ�ָ꣨��6��7��8��9��������ֵ���";
    	}else if(table.hasClass('tablezb5')){
    		p['query_type']="���򣨽ֵ����쵼�����й�ָ�ָ꣨��6��7��8��9��������";
    	}else if(table.hasClass('tablezb6')){
    		p['query_type']="���򣨽ֵ����쵼�����й�ָ�ָ꣨��6��7��8��9�����ֵ���";
    	}
    	
    	//alert(p['colIndex'])
    	//alert(p['b0111'])
    	openMate(p);
    });
	
	
	
	$('td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(9),td:nth-child(8)',".tablezb8 tr:gt(1)").unbind('click').bind('click',function(){
    	var p = {maximizable:false,resizable:false};
    	p['colIndex'] = $(this).index();
    	//alert($(this).parent().index())
    	p['b0111'] = $(this).parent().attr('b0111');
    	p['query_type']="�ṹ�Ըɲ���Ů�ɲ�������ɲ����й�ָ�ָ꣨��17��18��19��20��";
    	//alert(p['colIndex'])
    	//alert(p['b0111'])
    	openMate(p);
    });
	
	
	$('td:nth-child(2),td:nth-child(4),td:nth-child(6),td:nth-child(8)',".tablezb9 tr:gt(1)").unbind('click').bind('click',function(){
    	var p = {maximizable:false,resizable:false};
    	p['colIndex'] = $(this).index();
    	//alert($(this).parent().index())
    	p['b0111'] = $(this).parent().attr('b0111');
    	p['query_type']="�ɲ��ճ������й�ָ�ָ꣨��10��12��13��21��22��27��28��";
    	//alert(p['colIndex'])
    	//alert(p['b0111'])
    	openMate(p);
    });
}

//��λϸ���л�
function addZBXXDWClick(){
	$('.toclick').click(function(){
		
		if($(this).hasClass('data-show')){
			$(this).removeClass('data-show');
		}else{
			$(this).addClass('data-show');
		}
	
		//�����
		var tbtype = $(this).attr('clsname');
		//��λ
		var dwtype = $(this).parent().attr('b0111');
		//��һ�δӺ�̨����
		if($(this).hasClass('first-show')){
			radow.doEvent('showDWData',tbtype+"@@"+dwtype+"@@"+$("."+tbtype+" tr").length);
			$(this).removeClass('first-show');
		}else{
			if($(this).hasClass('data-show')){
				$('.dw-rowdata'+dwtype,"."+tbtype).show();
			}else{
				$('.dw-rowdata'+dwtype,"."+tbtype).hide();
			}
		}
	});
}


</script>