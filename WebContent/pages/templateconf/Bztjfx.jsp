<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-lang-zh_CN-GBK.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/jquery-ui/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%@page import="com.insigma.siis.local.pagemodel.templateconf.TemplateConfPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="<%=request.getContextPath()%>/basejs/odin.grid.menu.js"></script>
<odin:head></odin:head>
<style>
			.headerText {
				font-size: 24px;
				line-height: 26px;
				font-weight: bold;
				text-align: center;				
			}
			
			.noTop { 
				border-top: none;
			}
			
			.font1 {
				background-color: red;
			}
			
			.font2 {
				background-color: #FFCC00;
			}
			
			.font3 {
				background-color: green;
			}
			
			.structText {
				font-size: 20px;
				line-height: 20px;
				font-weight: bold;
				font-family: ����_GB2312;
			}
			
			.wholeLevelRemark {
				font-family: ����;
				text-align: left;
				font-size: 20px;
				line-height: 20px;				
			}
			
			.wholeLevel1 {
				font-family: ����;
				text-align: left;
				font-size: 20px;
				line-height: 20px;
				background-color: red;
			}
			
			.wholeLevel2 {
				font-family: ����;
				text-align: left;
				font-size: 20px;
				line-height: 20px;
				background-color: #FFCC00;
			}
			
			.wholeLevel3 {
				font-family: ����;
				text-align: left;
				font-size: 20px;
				line-height: 20px;
				background-color: green;
			}
			
			.structTable {
				border: 1px solid #000000;
				border-collapse:collapse;				
			}
			
			.structTable td {
				border: 1px solid #000000;	
				empty-cells: show;				
			}
			
			.nested {
				border: none;
				border-collapse:collapse;				
			}
			
			.structTable .boldText {
				font-weight: bold;
				text-align: center;
				word-break: keep-all;
			}
			
			.structTable .normalText {
				text-align: center;
			}
			
						
			.structTable .normalTextLevel1 {
				color: red;
				text-align: center;
			}
			
			.structTable .normalTextLevel1 a {
				color: red;
			}
			
			.structTable .normalTextLevel2 {
				color: #FFCC00;
				text-align: center;
			}
			
			.structTable .normalTextLevel2 a {
				color: #FFCC00;
			}
			
			.structTable .normalTextLevel3 {
				color: green;
				text-align: center;
			}
			
			.structTable .normalTextLevel3 a {
				color: green;
			}
			
			.structTable .level1 {
				background-color: red;
				text-align: center;
			}
			
			.structTable .level2 {
				background-color: #FFCC00;
				text-align: center;
			}
			
			.structTable .level3 {
				background-color: green;
				text-align: center;
			}
			
			.structTable .chart {
				vertical-align: bottom;
				text-align: center;
				border: none;
				padding-top: 2px;				
			}
			
			.structTable .chartCell {
				text-align: center;
				vertical-align:bottom;
				padding-bottom:2px;
			}
		</style>



<script>  
var contextPath='<%=request.getContextPath()%>';
//var subWinId = 'null';
    Ext.onReady(function(){
    	var man = document.getElementById('manager').value;
        var Tree = Ext.tree;
        tree = new Tree.TreePanel( {
   	  id:'group',
         el : 'div_1',//Ŀ��div����
         split:true,
         width: 238,
         minSize: 164,
         maxSize: 164,
         rootVisible: false,//�Ƿ���ʾ���ϼ��ڵ�
         autoScroll : false,
         animate : true,
         border:false,
         enableDD : true,
         containerScroll : true,
         loader : new Tree.TreeLoader( {
               dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople'
         })
     });
        tree.on('click',treeClick); //tree����¼�
		var root = new Ext.tree.AsyncTreeNode({
			/* checked : true,
			text : "������λ",
			iconCls : "picOrg",
			draggable : true,
			id : "-1" */
			checked : false,//��ǰ�ڵ��ѡ��״̬
			text : document.getElementById('ereaname').value,//�ڵ��ϵ��ı���Ϣ
			iconCls : document.getElementById('picType').value,//Ӧ�õ��ڵ�ͼ���ϵ���ʽ
			draggable : false,//�Ƿ�������ҷ
			id : document.getElementById('ereaid').value,//Ĭ�ϵ�nodeֵ��?node=-100
			href : "javascript:radow.doEvent('querybyid','"//�ڵ����������
					+ document.getElementById('ereaid').value + "')"
		});
		tree.setRootNode(root);
		tree.render();
		root.expand(false,true,callback);
		var callback = function (node){
			if(node.hasChildNodes()) {
				node.eachChild(function(child){
					child.expand();
				})
			}
		}
		/* tree.setRootNode(root);//���ø����
		//tree.addListener('click', BiaoZhunClick);
		tree.render();//��ʾ */
		root.expand(false,true, callback);//Ĭ��չ��
    	
    }); 
    
   
   var lvAll=0;
   var hongAll=0;
   var huangAll=0;
   var znzslv=0;
   var znzshuang=0;
   var znzshong=0;
    function treeClick(node,e){
    	//var aa=node.parentNode.id; 
    	 Ext.getCmp("tab1").show();//���������Ĭ����ʾ��һ��tabҳ
    	 var gid = node.id;
    	  lvAll = 0;
    	  hongAll=0;
    	  huangAll=0;
    	  znzslv=0;
    	  znzshuang=0;
    	  znzshong=0;
    	 document.getElementById("gid").value=gid;
    	 document.getElementById("unit").innerHTML="";
    	 document.getElementById("unit1").innerHTML="";
    	 document.getElementById("unit2").innerHTML="";
    	 document.getElementById("team_responsibility").innerHTML="";
    	 document.getElementById("general_evaluation").innerHTML="";
    	 document.getElementById("optimize_direction").innerHTML="";
    	  $("#gn1").empty();
	      $("#gn2").empty();
	      $("#gn3").empty();
	      $("#gn4").empty();
	      $("#gn5").empty();
	      $("#gn1").append("<td width='60' class='boldText' style='border-left:none;border-top:none;''>ְ��<br/>���</td>");
	      $("#gn2").append("<td class='boldText' style='border-left:none;'>����<br/>ְλ</td>");
	      $("#gn3").append("<td class='boldText' style='border-left:none;'>�ʸ�<br/>����</td>");
	      $("#gn4").append("<td class='boldText' style='border-left:none;'>�䱸<br/>״��</td>");
	      $("#gn5").append("<td class='boldText' style='border-left:none;border-bottom:none;'>�˸�<br/>����<br/>�ȷ�<br/>��</td>");
	      $("#zr1").empty();
	      $("#zr2").empty();
	      $("#zr3").empty();
	      $("#zr4").empty();
	      $("#zr5").empty();
	      $("#zr1").append("<td class='boldText' width='60' style='border-left:none;border-top:none;'>��Ŀ</td>");
	      $("#zr2").append("<td class='boldText' style='border-left:none;'>Ҫ��</td>");
	      $("#zr3").append("<td class='boldText' style='border-left:none;'>Ŀ��</td>");
	      $("#zr4").append("<td class='boldText' style='border-left:none;'>��״</td>");
	      $("#zr5").append("<td class='boldText' style='border-left:none;border-bottom:none;'>�Ա�</td>");
	      $("#ztzk").empty();
	      $("#cjsj").empty();
	      $("#sp").empty();
	      /* var boxes = document.getElementById("jgysmc");
	  	    for(i=0;i<boxes.rows.length;i++){
	  	        if(i>=2){
	  	            tr = boxes.rows[i];
	  	            tr.parentNode.removeChild(tr);
	  	        }
	  	    } */
	  	  $('#jgysmc tr:gt(1)').remove();
	      $("#jgmc2").empty();
      	  $("#jgmcww").empty();
      	  $("#jgmc2").append("<td class='boldtext'>&nbsp;</td>");
      	  $("#jgmcww").append("<td class='boldtext'>&nbsp;</td>");
    	 radow.doEvent('Znzs',gid);//ְ��ְ��
    	
    	 radow.doEvent('Zwsm',gid);//ְλ˵��
    	 radow.doEvent('zrjg',gid);//��Ȼ�ṹ������ʾ////alert(lvAll);
    	 radow.doEvent('showtype',gid);//�ۺ�����
    	 /* radow.doEvent('jgysmc',gid);//��Ȼ�ṹ��������
    	 $('#gid').val(gid); */
    	 
    	 radow.doEvent('TrainingInfoGrid.dogridquery');
    	 radow.doEvent('hmc',gid);
    	//grantTabChange(tabObj,item);
    	 //comprevalue();
    }
    function comprevalue(parem){//�����ۺ����ñ�
    	/* alert(lvAll);
	    alert(hongAll);
	    alert(huangAll); */
	   var gid = document.getElementById("gid").value;
	    var sumcol = lvAll*1+hongAll*1+huangAll*1;//��Ȼ�ṹȫ����ɫ
	    var znzssumcol = znzslv*1+znzshuang*1+znzshong*1;//ֻ��ְ��ȫ����ɫ
	    var sumAll = sumcol*1+znzssumcol*1;
	    var lvbAll=lvAll*1+znzslv*1;//��Ȼ�ṹ��ֻ��ְ��ȫ����ɫ
	    var lvbAll2= Math.round(lvbAll*1 / sumAll * 10000) / 100.00;
	    var paremarr = parem.split(",");
    	if(lvbAll2>=100){
    		document.getElementById("ztzk").innerText='��';//����״���ı�
    	    document.getElementById("ztzk").style.background = 'green';//����״����ɫ
    	    document.getElementById("namecol").innerText='��';
    	    document.getElementById("namecol").style.background = 'green';
    	    document.getElementById("hlqk").innerText='�����ṹ�ȽϺ�����쵼���ӡ�';
    	    var statustype = "3";
     	    var gs = gid+","+statustype;
     	    radow.doEvent("ztzk",gs);//����״��
    	}else if(lvbAll2>=50&&lvbAll2<100){
    		document.getElementById("ztzk").innerText='��';//����״���ı�
    	    document.getElementById("ztzk").style.background = 'Gold';//����״����ɫ
    	    document.getElementById("namecol").innerText='��';
    	    document.getElementById("namecol").style.background = 'Gold';
    	    document.getElementById("hlqk").innerText='�����ṹ����������쵼���ӡ�';
    	    var statustype = "2";
    	    var gs = gid+","+statustype;
    	    radow.doEvent("ztzk",gs);//����״��
    	}else if(lvbAll2<50){
    		document.getElementById("ztzk").innerText='��';//����״���ı�
    	    document.getElementById("ztzk").style.background = 'red';//����״����ɫ
    		document.getElementById("namecol").innerText='��';
    	    document.getElementById("namecol").style.background = 'red';
    		document.getElementById("hlqk").innerText='�����ṹ���ز�������쵼���ӡ�';
    	    var statustype = "1";
     	    var gs = gid+","+statustype;
     	    radow.doEvent("ztzk",gs);//����״��
    	}
    	document.getElementById("namejg").innerText=paremarr[0];//����״���ı�
	   // $("#ztzk").text="123";
	   if(parem!="error"){
	    	 document.getElementById("unit").innerHTML=paremarr[0];
	    	 document.getElementById("team_responsibility").innerHTML=paremarr[2];
	    	 document.getElementById("general_evaluation").innerHTML=paremarr[3];
	    	 document.getElementById("optimize_direction").innerHTML=paremarr[4];
	    	 /*document.getElementById("general_evaluation").value=paremarr[2];
	    	document.getElementById("optimize_direction").value=paremarr[3];
	    	document.getElementById("typeid").value=paremarr[4]; */ 
	   }
    	
    	
    }
    
   function znzsvalue(person,creattime){//����ְ��ְ��td
	   if(person=="û������"){
  		 document.getElementById("MainBody1").style.display="block";//��
  		 document.getElementById("MainBody").style.display="none";//��
  	 }else{
  		 /*  document.getElementById("MainBody").style.visibility="visible";//��
  		 document.getElementById("MainBody1").style.visibility="hidden";//�� */
  		  document.getElementById("MainBody").style.display="block";//��
  		 document.getElementById("MainBody1").style.display="none";//��
  		 //$("table").css('display','block');//��ʾ����table
  	 } 
   var creat = creattime.substring(0,4)+"��"+creattime.substring(4)+"��";
	   document.getElementById("cjsj").innerText=creat;//����ʱ��
	   var personAll = person.split("&");
	      var cosp= personAll.length;
	      $("#yp1").empty();
	      $("#yp2").empty();
	      $("#sp").empty();
	      var aaa="<td class='boldText' style='border-left:none;border-bottom:none;' width='60' rowspan='3'>ְλ<br/>ְ��</td>";
		var  aa="<td class='boldText' width='60' rowspan='2'>Ӧ��</td>";
			$("#yp1").append(aaa);
			$("#yp1").append(aa);
			$("#sp").append("<td class='boldText' style='border-bottom:none;' width='60'>ʵ��</td>");
	      $("#cosp").attr("colspan",cosp+1);
	      
	   for ( var i = 0; i <personAll.length; i++){
		    var aa=personAll[i];//1,5
		    var aaArr=aa.split("@@@");
		    var sql=aaArr[1]; 
		    var parem = aaArr[0].split(",");
		    var b =Math.ceil(parem[1]*1/2);
		    $("#yp1").append("<td class='normalText zsTableTd' style='word-break:break-all;'>"+parem[0]+"</td>");
		    $("#yp2").append("<td class='normalText' style='word-break:break-all;' >"+parem[1]+"</td>");
		    if(parem[6]*1>=parem[1]*1&&parem[5]*1=='1'){
		    	++znzslv
		    	  $("#sp").append("<td  class='normalText' style='border-bottom:none;word-break:break-all;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='green'>"+parem[6]+"</font></a></td>");	
		    } else if(parem[6]*1>=b&&parem[6]*1<parem[1]*1&&parem[5]*1=='1'){
		    	++znzshuang
		    	 $("#sp").append("<td  class='normalText' style='border-bottom:none;word-break:break-all;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='Gold'>"+parem[6]+"</font></a></td>");
		    }else if(parem[6]*1<b&&parem[5]*1=='1'){
		    	++znzshong
		    	$("#sp").append("<td  class='normalText' style='border-bottom:none;word-break:break-all;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='red'>"+parem[6]+"</font></a></td>");
		    }else if(parem[6]*1<parem[1]*1&&parem[5]*1=='0'){
		    	++znzshong
		    	$("#sp").append("<td  class='normalText' style='border-bottom:none;word-break:break-all;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='red'>"+parem[6]+"</font></a></td>");
		    }
		  
		    //alert(aa[0]);
		}
	 
   }
   
   
   function zwsmvalue(person,sls){//����ְλ˵��td
	   var personAll = person.split("$");
	      /* $("#gn1").empty();
	      $("#gn2").empty();
	      $("#gn3").empty();
	      $("#gn4").empty();
	      $("#gn5").empty(); */
	      /* $("#gn1").append("<td width='60' class='boldText' style='border-left:none;border-top:none;''>ְ��<br/>���</td>");
	      $("#gn2").append("<td class='boldText' style='border-left:none;'>����<br/>ְλ</td>");
	      $("#gn3").append("<td class='boldText' style='border-left:none;'>�ʸ�<br/>����</td>");
	      $("#gn4").append("<td class='boldText' style='border-left:none;'>�䱸<br/>״��</td>");
	      $("#gn5").append("<td class='boldText' style='border-left:none;border-bottom:none;'>�˸�<br/>����<br/>�ȷ�<br/>��</td>"); */
	   for ( var i = 0; i <personAll.length; i++){
		    //parem[0] parem[1] parem[4]
		    var aa=personAll[i];
		    var parem = aa.split("@");
		    $("#gn1").append("<td class='posTableTd' align='center' style='border-top:none;border-right:none;'>"+parem[1]+"</td>");
		    $("#gn2").append("<td align='center' style='border-right:none;'>"+parem[0]+"</td>");
		    $("#gn3").append("<td align='center' style='border-right:none;'>"+parem[2]+"</td>");
		    $("#gn4").append("<td align='center' style='border-right:none;'>"+parem[3]+"</td>");
		   
		}
	      var tjsls= sls.split("@");
	      for ( var i = 0; i <tjsls.length; i++){
	    	  var aa=tjsls[i];
			  var sls = aa.split(",");
	    	  $("#gn5").append("<td align='center' style='border-right:none;border-bottom:none;'>"+sls+"</td>");
	      }
       
   }
    function grantTabChange(tabObj,item){
    	var tab = item.getId();
    	var gid= document.getElementById("gid").value;
    	//tab="1";
    	if(tab=="tab2"){
    		 radow.doEvent('TrainingInfoGrid.dogridquery');
    		 radow.doEvent('hmc',gid);
    	}else if(tab=="tab3"){
    		$('#jgysmc tr:gt(1)').remove();
     		 $("#jgmc2").empty();
         	 $("#jgmcww").empty();
         	  $("#jgmc2").append("<td class='boldtext'>&nbsp;</td>");
         	  $("#jgmcww").append("<td class='boldtext'>&nbsp;</td>");
    		radow.doEvent('jgysmc',gid);//��Ȼ�ṹ������ʾ
    		radow.doEvent('hmc',gid);
    	}
    }
    function zrjg(prame){//��Ȼ�ṹ����td
    	var personAll = prame.split("$");
        var lv = 0;
        var huang = 0;
        var hong = 0;
        var flag = "";//�Ƿ�һƱ�����yesΪ��
        
    	 for ( var i = 0; i <personAll.length; i++){
 		    //parem[0] parem[1] parem[4]
 		     //nl,>=&50,51-55��,151$
 		    var aa=personAll[i];
 		     //alert(aa);
 		    var aaArr=aa.split("@&&&");
 		    var sql=aaArr[1]; 
 		    //alert(sql);
 		    var parem = aaArr[0].split(",");
 		    //var parem = aa.split(",");
 		    var pra = parem[1].replace("&","");
 		    var parll = pra.substring(2);//���õ�����
 		    var fuh = pra.substring(0,1);//>�Ż�<��
 		   var b =Math.ceil(parll/2);//����������һ��
 		   var aaa=parem[3];//һƱ���
 		  // var abcd = '"a01.a0000 = MODELCONFIG.id and a02.a0201b = 001.001.002.001.004) asd.tenuresystem = 01"';
 		 // var abcd ='"select * from (select distinct a01.a0000,a01.a0101,a01.a0104,a01.a0107,a01.a0114a,a01.a0111a,a01.a0192, modelconfig.tenuresystem from a01 inner join a02 on a01.a0000 = a02.a0000 left join MODELCONFIG on a01.a0000 = MODELCONFIG.id and a02.a0201b = 001.001.002.001.004) asd where asd.tenuresystem = 01"';
 		   //alert(aaa);
 		    $("#zr2").append("<td class='normalText jgTableTd' style='word-break: break-all;border-right:none;'>"+parem[2]+"</td>");
 		    $("#zr3").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;border-right:none;'>"+pra+"</td>");
 		    if(parem[4]*1>=parll*1&&fuh==">"){//> ������£�ʵ������ >= ��������  ��ɫ 
 		    	++lv;
 		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='green'>"+parem[4]+"</font></a></td>"); 
 		    }else if(parem[4]>=b&&parem[4]<parll*1&&fuh==">"){//> ������£�    ����������һ��  <= ʵ������   < ��������  ��ɫ
 		    	++huang;
 		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='Gold'>"+parem[4]+"</font></a></td>"); 
 		    }else if(parem[4]*1<=parll*1&&fuh=="<"){//< ������£�ʵ������   <= ��������  ��ɫ
 		    	++lv;
 		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='green'>"+parem[4]+"</font></a></td>");
 		    }/* else if(parem[4]>=b*2&&parem[4]>parll*1&&fuh=="<"){//< ������£�    ʵ������   > ��������      ʵ������ > ����������һ�� * 2      ��ɫ
 		    	alert("< ������£�    ����������һ�� * 2 > ʵ������   > ��������  ��ɫ");
 		    	++huang;
 		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='Gold'>"+parem[4]+"</font></a></td>");
 		    } */
 		    else if(Math.ceil(parem[4]/2)<=parll&&parem[4]>parll*1&&fuh=="<"){//< ������£�    ʵ������   > ��������   >=  ʵ������ ��һ��      ��ɫ
 		    	++huang;
 		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='Gold'>"+parem[4]+"</font></a></td>");
 		    }else if(parem[4]<b&&fuh==">"&&aaa=="0"){//> ������£�ʵ������  < ��������    ����  һƱ���    ��ɫ
 		    	flag="yes";
 		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='red'>"+parem[4]+"</font></a></td>");
 		    }else if(parem[4]>b&&fuh=="<"&&aaa=="0"){//< ������£�ʵ������  > ��������    ����  һƱ���    ��ɫ
 		    	flag="yes";
 		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='red'>"+parem[4]+"</font></a></td>");
 		    }else{//�����Ķ��Ǻ�ɫ
 		    	++hong;
 		    	$("#zr4").append("<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;'><a href='javascript:void(0)' onclick='fac("+sql+")'><font color='red'>"+parem[4]+"</font></a></td>");
 		    }
 		}   
    	 var type=personAll[0].split(",")[0];
    	 if(type=="nl"){
    		 type="����ṹ";
    	 }else if(type=="xl"){
    		 type="ѧ������";
    	 }else if(type=="zy"){
    		 type="רҵ�ṹ";
    	 }else if(type=="xb"){
    		type="�Ա�ṹ"; 
    	 }else if(type=="dp"){
    		 type="���ɽṹ";
    	 }else if(type=="mz"){
    		 type="����ṹ";
    	 }else if(type=="ly"){
    		 type="��Դ�ṹ";
    	 }else if(type=="zc"){
    		 type="ר���ṹ";
    	 }else if(type=="dy"){
    		 type="����ṹ";
    	 }else if(type=="nel"){
    		 type="�����ṹ";
    	 }else if(type=="jy"){
    		 type="����ṹ";
    	 }else if(type=="jl"){
    		 type="�����ṹ";
    	 }else if(type=="rqz"){
    		 type="������";
    	 }
    	 var len=personAll.length;
    	 var col = lv*1+hong*1+huang*1;
    	 
    	 //��  ��   ��   ��ӱȽ�
    	var lv1= Math.round(lv*1 / col * 10000) / 100.00;
    	var hong1 = Math.round(hong*1 / col * 10000) / 100.00;
    	var huang1 = Math.round(huang*1 / col * 10000) / 100.00;
    	 $("#zr1").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' colspan='"+len+"'>"+type+"</td>");
    	 if(flag=="yes"){
    		 ++hongAll;
    		 $("#zr5").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' bgcolor='red' colspan='"+len+"'></td>");
    	 }else if(lv1<50){
    		 ++hongAll
    		 $("#zr5").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' bgcolor='red' colspan='"+len+"'></td>");
    	 }else if((lv1>=50)&&(lv1<100)){
    		 ++huangAll;
    		 $("#zr5").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' bgcolor='Gold' colspan='"+len+"'></td>");
    	 }else if(lv1>=100){
    		 ++lvAll;
    		 $("#zr5").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' bgcolor='green' colspan='"+len+"'></td>");
    	 }else if(huang1>=100){
    		 ++huangAll;
    		 $("#zr5").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' bgcolor='Gold' colspan='"+len+"'></td>");
    	 }else if(hong1>=100){
    		 ++hongAll;
    		 $("#zr5").append("<td class='boldText' style='word-break:all;border-top:none;border-right:none;' bgcolor='red' colspan='"+len+"'></td>"); 
    	 }
     	 
    }
    
    function fac(sql){
    	var sql1 = sql.replace(/#/g,"'")
    	//alert(sql1);
    	document.getElementById("sql").value=sql1
    	var pa="";
    	//$h.openWin('BackCheck','pages.templateconf.BackCheck&initParams='+sql1,'��Ȼ�ṹ�������', 800, 500,'', contextPath);
		window.showModalDialog('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.templateconf.BackCheck',window,'dialogWidth:900px; dialogHeight:528px; status:no;directories:yes;scrollbars:no;resizable:no;help:no');
    }
    
    function zrjg1(prame){//�ṹҪ�طֲ�����
    	var personAll = prame.split("$");
    	 for ( var i = 0; i <personAll.length; i++){
 		    //parem[0] parem[1] parem[4]
 		    var aa=personAll[i];
 		    var parem = aa.split(",");
 		    var pra = parem[2].replace("&","");
 		    $("#jgmc2").append("<td class='normalText' style='word-break: keep-all;'>"+parem[1]+"</td>");
 		    
 		    
 		}   
    	 var type=personAll[0].split(",")[0];
    	 if(type=="nl"){
    		 type="����ṹ";
    	 }else if(type=="xl"){
    		 type="ѧ���ṹ";
    	 }else if(type=="zy"){
    		 type="רҵ�ṹ";
    	 }else if(type=="xb"){
    		type="�Ա�ṹ"; 
    	 }else if(type=="dp"){
    		 type="���ɽṹ";
    	 }else if(type=="mz"){
    		 type="����ṹ";
    	 }else if(type=="ly"){
    		 type="��Դ�ṹ";
    	 }else if(type=="zc"){
    		 type="ר���ṹ";
    	 }else if(type=="dy"){
    		 type="����ṹ";
    	 }else if(type=="nel"){
    		 type="�����ṹ";
    	 }else if(type=="jy"){
    		 type="����ṹ";
    	 }else if(type=="jl"){
    		 type="�����ṹ";
    	 }else if(type=="rqz"){
    		 type="������";
    	 }
    	 var len=personAll.length;
    	 var tr=document.getElementById("jgmcww");
    	 var td = tr.insertCell();
    	 td.setAttribute("colSpan",len+"");
    	 //td.setAttribute("class","boldText");
    	 td.className="boldText";
    	 td.innerText=type;
    	 //alert(ryname+"================="+count);
    	 var lengt = len+1;
    }
    
      function rynn(name,count){
    	    	var name = name.replace(/Y/g,"��").replace(/N/g," ");
    	    	//var name=name.replace("Y","1").replace("N"," ");
    	        var count=parseInt(count)+1;
    	    	var ryname = name.split("$");
    	    	if(ryname!="a" && count !=1){
    		    	for ( var j = 0; j <ryname.length; j++){
    		    		var dd=ryname[j];
    		    		var ddarr=dd.split(',');
    		    		var name=ddarr.shift();
    		    		 var table=document.getElementById("jgysmc");//��ȡtable
    		    		var tr= table.insertRow();
    		    		 for ( var i = 0; i <count; i++){
    		    			 var td=tr.insertCell();
    		    			 td.className="normalText";
    		    			 if(i==0){
    		    				 td.innerText=name;
    		    			 }else{
    		    				 td.innerText=ddarr[i-1]; 
    		    			 }
    		    			 
    		    			 
    		    		 }
    		    	}
    		    } 
    	  
      }
      function dogrid(jgname){
    	 // alert(jgname);
    	  document.getElementById("unit1").innerHTML=jgname;
    	  document.getElementById("unit2").innerHTML=jgname;
    	  
      }
      function expExcelFromGrid2(){
    		var excelName = null;
    		//excel�������Ƶ�ƴ�� 
    		var pgrid = Ext.getCmp('TrainingInfoGrid');
    		var dstore = pgrid.getStore();
    		excelName = "������Ϣ" + "_" + Ext.util.Format.date(new Date(), "YmdHis");
    		
    		expExcelFromGrid333('TrainingInfoGrid', excelName, null,null, false);
    		
    	} 
      
 function expExcelFromGrid333(gridId, fileName, sheetName, headNames,
			isFromInterface) {
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫ���������ݣ�");
			return;
		}
		var gridColumnModel = grid.getColumnModel();
		var colsArray = new Array();
		var dataIndex = new Array();
		var columnHeader = new Array();
		var index = 0;
		var noHeaderIndex = 0;
		var jsonType = {};  //������¼Ҫ�����е�����
		var gridCheckBoxDataIndex = window[gridId+"_checkbox_dataindex"];
		var gridExpDataIndex = window[gridId+"_exp_dataindex"];
		var gridExpDataIndexs = [];
		if(typeof gridExpDataIndex != 'undefined'){
			gridExpDataIndexs = gridExpDataIndex.split(",");
		}
		for (var i = 0; i < gridColumnModel.getColumnCount(); i++) {
			if (gridColumnModel.isHidden(i)
					|| gridColumnModel.getDataIndex(i) == "") {
				continue;
			}
			var col = odin.getGridColumn(gridId, gridColumnModel.getDataIndex(i));
			var head=col.header;
			if(head.indexOf('x-grid3-check-col-td')>0){
				continue;
			}
			if(typeof gridCheckBoxDataIndex != 'undefined'){
				if(gridCheckBoxDataIndex==gridColumnModel.getDataIndex(i)){
					continue;
				}
			}
			var isNeedExp = false;
			for(var dIndex in gridExpDataIndexs){
				var temp = gridExpDataIndexs[dIndex];
				if(temp == gridColumnModel.getDataIndex(i)){
					isNeedExp = true;
					break;
				}
			}
			if(gridExpDataIndexs.length>0 && isNeedExp==false){
				continue;
			}
			colsArray[index] = i;
			dataIndex[index] = gridColumnModel.getDataIndex(i);
			if(col.editor instanceof Ext.form.NumberField){
				jsonType[dataIndex[index]] = "number";
			}
			columnHeader[index] = gridColumnModel.getColumnHeader(i).replace(
					/<[^>]+>/g, "").replace(/,/g, "��");
			index++;
		}
		/*if(!MDParam){
			var MDParam={};
			MDParam.functionid=parent.tabs.activeTab.id;
			MDParam.t itle=parent.tabs.activeTab.title;
		}*/
		querySQLSim = "Radow$"+MDParam.functionid + "," + gridId;
		
		if (isFromInterface != null && isFromInterface != true
				&& isPageGridDiv(gridId)) {// ��ҳgrid����ȫ��ҳ
			// var querySQL = eval("pageGridSql." + gridId);
			
			if (headNames == null || headNames == "" || headNames == "null") { // ��ͷ
				headNames = {};
				for (var i = 0; i < dataIndex.length; i++) {
					eval("headNames." + dataIndex[i] + "="
							+ Ext.encode(columnHeader[i]));
				}
				headNames = Ext.encode(headNames);
			}
			var decodeJson; // decodeת��
			for (var i = 0; i < dataIndex.length; i++) {
				var col = odin.getGridColumn(gridId, dataIndex[i]);
				if (col.editor) {
					var type = "";
					try{
						type = col.editor.field.getXType();
					}catch(e){
						//
					}
					if (type == "combo") {// ֻ�����������ʽ����
						var decodeStrArray = eval(dataIndex[i] + "_select");
						var decodeStr = "";
						for (var j = 0; j < decodeStrArray.length; j++) {
							decodeStr += "," + decodeStrArray[j][0] + ","
									+ decodeStrArray[j][1];
						}
						decodeStr = decodeStr.substring(1);
						if (decodeStr != "") {// ��Ϊ�ղŲ���
							// decodeStr = decodeStr.substring(3,
							// decodeStr.length -
							// 3).replace(/\"\],\[\"/g, ',').replace(/\",\"/g,
							// ',');
							if (decodeJson == null) {
								decodeJson = {};
							}
							eval("decodeJson." + dataIndex[i] + "="
									+ Ext.encode(decodeStr));
						}
					}
				}
			}
			if (decodeJson != null) {
				decodeJsonSim = Ext.encode(decodeJson);
			}
		} else { // ����ҳgrid���������Ϣ
			if (headNames == null || headNames == "" || headNames == "null") { // ��ͷ
				headNames = columnHeader.join(",");
			}
			var jsonArray = new Array(store.getCount());
			for (i = 0; i < store.getCount(); i++) {
				recode = store.getAt(i);
					var checkValue = recode.get(gridCheckBoxDataIndex);
					if((typeof checkValue=='boolean' && true == checkValue)||typeof gridCheckBoxDataIndex == 'undefined'){
						json = {};
						for (j = 0; j < colsArray.length; j++) {
							var cell = grid.getView().getCell(i, colsArray[j]);
							var value = cell.outerText || cell.textContent;
							if (value == null) {
								value = "";
							} else {
								// ����renderAlt��ɵ������ַ�
								value = value.replace(/<[^>]+>/g, "");
							}
							eval("json." + dataIndex[j] + "=" + Ext.encode(value));
						}
						jsonArray[jsonArray.length] = json;
					}
			}
			jsonDataSim = Ext.encode(jsonArray);
		}
		if (fileName == null || fileName == "" || fileName == "null") {
			fileNameSim = MDParam.title /*+ "_" + gridId.replace("div_", "����")*/
					+ "_" + Ext.util.Format.date(new Date(), "Ymd");
		} else {
			fileNameSim = fileName;
		}
		if (sheetName == null || sheetName == "" || sheetName == "null") {
			sheetNameSim = MDParam.title /*+ "_" + gridId.replace("div_", "����");*/
		} else {
			sheetNameSim = sheetName;
		}
		window.jsonTypeSim = odin.encode(jsonType);
		headNamesSim = headNames;
		doOpenPupWin(contextPath + "/sys/excel/commSimpleExpExcelWindow.jsp",
				"�����ļ�", 500, 160);
		
		//$h.openWin('dow','sys.excel.commSimpleExpExcelWindow','ѡ��ģ��',500,160,'����',contextPath);
	}  

 function isPageGridDiv(divId) {
		if (Ext.getCmp(divId)
				&& ((Ext.getCmp(divId).getTopToolbar() && Ext.getCmp(divId)
						.getTopToolbar().pageSize) || (Ext.getCmp(divId)
						.getBottomToolbar() && Ext.getCmp(divId)
						.getBottomToolbar().pageSize))) {
			return true;
		} else {
			return false;
		}
	}
</script>  
<%
    SysOrgPageModel sys = new SysOrgPageModel();
	String picType = (String) (sys.areaInfo
			.get("picType"));	
	String ereaname = (String) (sys.areaInfo
			.get("areaname"));
	String ereaid = (String) (sys.areaInfo
			.get("areaid"));
	String manager = (String) (sys.areaInfo
			.get("manager"));
	%>
	<style type="text/css">
	#PageBody{width:101%;}
	#Sidebar{float:left;width:15%; border-right:5px solid #c3daf9;margin-top:-20px;}
	#MainBody{float:right;width:82%;margin-top:-20px;}
	.comments {  
 width:100%;/*�Զ���Ӧ�����ֿ��*/  
 height:100%;
 overflow:auto;  
 word-break:break-all;  
}  
	
	
	
</style> 

</head>
<body style="overflow: hidden;">

<odin:hidden property="gid"/>
<odin:hidden property="ryname"/>
<odin:hidden property="count"/>
<odin:hidden property="type"/>
<input type="hidden" name="typeid" id="typeid" value=""/>
<input type="hidden" name="jgtypeid" id="jgtypeid" value=""/>
<odin:hidden property="ereaname" value="<%=ereaname%>" />
<odin:hidden property="ereaid" value="<%=ereaid%>" />
<odin:hidden property="manager" value="<%=manager%>" />
<odin:hidden property="picType" value="<%=picType%>" />





<input type="hidden" id="gid" name="gid" />
<input type="hidden" id="sql" name="sql" />
<input type="hidden" id="znzsvv" name="znzsvv" />
<div id="PageBody"> <!--ҳ������--> 
�������� <div id="Sidebar"> <!--�����-->
    <div id="div_1" style="overflow: auto;width: 15%; ">
    </div>
�������� </div> 
�������� <div id="MainBody" style="overflow-x:scroll; "> <!--��������-->
<!--      <table > -->
<!-- 		<tr> -->
<!-- 		  <td > -->
		   <odin:tab id="tab" width="100%"  height="document.body.clientHeight" tabchange="grantTabChange">
		   <odin:tabModel>
		    <odin:tabItem title="&nbsp�ṹģ��&nbsp" id="tab1"></odin:tabItem>
		    <odin:tabItem title="&nbsp���ӳ�Ա������&nbsp" id="tab2"></odin:tabItem>
			<odin:tabItem title="&nbsp�ṹҪ�طֲ�����&nbsp" id="tab3" isLast="true"></odin:tabItem>
		   </odin:tabModel>
		   
		   
		   <odin:tabCont itemIndex="tab1">
		   <div id="jgmx" style="width:100%;overflow: scroll;">
		   <table width="100%" border="0" >
				<tr height="60">
					<td class="headerText"><span class="headerText" id="unit" name="unit"></span>�쵼���ӽṹģ�ͼ��Աȷ�����</td>
				</tr>
			</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" >
				<tr height="32">
<!-- 					<td width="50">&nbsp;</td> -->
					<td class="structText" width="150">����״����<span id="ztzk" class="css1" "></span></td>
					<td align="left" class="wholeLevelRemark">&nbsp;</td>
					<td width="250" class="structText" style="text-align:right;">����ʱ��:<span id="cjsj" class="css1" "></span></td>
<!-- 					<td width="50">&nbsp;2018��7��</td> -->
				</tr>
			</table>
							
		    <table align="center" width="100%" overflow-y: scroll; cellpadding="0" cellspacing="0" class="structTable" >
		<tr height="32">
			<td class="boldText" align="center">һ��ְ��ְ��</td>
		</tr>
		<tr>
			<td>
				<table id="zsTable" style="width:100%;height:60px;" cellpadding="0" cellspacing="0" class="nested" > 
					<tr height="60">
						<td class="boldText" style="border-left:none;border-top:none;" width="60">��Ҫ<br/>ְ��</td>
						<td  id="cosp"  style="word-break:break-all;text-align:left;border-top:none;border-right:none;">
						 <span  style="word-break:break-all;text-align:left;border-top:none;border-right:none;" id="team_responsibility" name="team_responsibility" ></span>
						</td>
					</tr>
					<tr id="yp1" height="32">
						<td class="boldText" style="border-left:none;border-bottom:none;" width="60" rowspan="3">ְλ<br/>ְ��</td>
						<td class="boldText" width="60" rowspan="2">Ӧ��</td>
<!-- 						<td class='normalText zsTableTd' style='word-break:break-all;' id="yp1"> </td> -->
					</tr>
															
					<tr id="yp2" height="32">
<!-- 						<td class='normalText' style='word-break:break-all;' ></td> -->
					</tr>
					<tr id="sp" height="32">
						<td class="boldText" style="border-bottom:none;" width="60">ʵ��</td>
<%-- 						<td id="sp" class='normalTextLevel<s:property value="#result.level"/>' style='border-bottom:none;<s:if test="#st.index==(summaryList[0].resultList.size-1)">border-right:none;</s:if>'>	</td> --%>
					</tr>			
				</table>
			</td>									
		</tr>
		<tr height="32">
			<td class="boldText" align="center">������Ȼ�ṹ</td>
		</tr>
		<tr>
			<td>
				<table id="jgTable" style="width:100%;" cellpadding="0" cellspacing="0" class="nested" >
					<tr height="32" id="zr1">
						<td class="boldText" width="60" style="border-left:none;border-top:none;">��Ŀ</td>
<!-- 						<td class='boldText' style='word-break:all;border-top:none;border-right:none;' colspan=''>1</td> -->
					</tr>
					<tr height="32" id="zr2">
						<td class="boldText" style="border-left:none;">Ҫ��</td>
<!-- 							<td class='normalText jgTableTd' style='word-break: break-all;border-right:none;'>2</td> -->
					</tr>
					<tr height="32" id="zr3">
						<td class="boldText" style="border-left:none;">Ŀ��</td>
<!-- 								<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;border-right:none;'> -->
<!-- 								>=31 -->
<!-- 								</td> -->
								
					</tr>
					<tr height="32" id="zr4">
						<td class="boldText" style="border-left:none;">��״</td>
<%-- 								<td class='normalTextLevel3' style='font-weight:bold;font-size:16px;<s:if test="#st.index==(summaryList.size-1)">border-right:none;</s:if>'> --%>
								
<!-- 								</td> -->
					</tr>
					<tr height="32" id="zr5">
						<td class="boldText" style="border-left:none;border-bottom:none;">�Ա�</td>
<%-- 						<td class='level<s:property value="#summary.summaryLevel"/>' style='border-bottom:none;<s:if test="#st.index==(summaryList.size-1)">border-right:none;</s:if>' colspan='<s:property value="#summary.resultList.size"/>'><s:property value="#summary.summaryLevelDesc"/></td> --%>
					</tr>
				</table>
			</td>									
		</tr>
		<tr height="32">
			<td class="boldText" align="center">�������ܽṹ</td>
		</tr>
		<tr>
			<td>
				<table id="posTable" style="width:100%;" cellpadding="0" cellspacing="0" class="nested" >
					<tr height="32" id="gn1">
						<td width="60" class="boldText" style="border-left:none;border-top:none;">ְ��<br/>���</td>
<!-- 						<td class='posTableTd' align="center" style='border-top:none;border-right:none;'>2</td> -->
					</tr>
					<tr height="60" id="gn2">
						<td class="boldText" style="border-left:none;">����<br/>ְλ</td>
<!-- 						<td align="center" style='border-right:none;'>3</td> -->
					</tr>
					<tr height="60" id="gn3">
						<td class="boldText" style="border-left:none;">�ʸ�<br/>����</td>
					</tr>
					<tr height="60" id="gn4">
						<td class="boldText" style="border-left:none;">�䱸<br/>���</td>
					</tr>
					<tr height="60" id="gn5">
						<td class="boldText" style="border-left:none;border-bottom:none;">�˸�<br/>����<br/>�ȷ�<br/>��</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr height="32">
			<td class="boldText" align="center">�ġ��������ۼ��Ż�����</td>
		</tr>
		<tr>
			<td>
				<table style="width:100%;" cellpadding="0" cellspacing="0" class="nested" >
					<tr height="60">
						<td width="60" class="boldText" style="border-left:none;border-top:none;">����<br/>����</td>
						<td style="text-align:left;border-right:none;border-bottom:none;border-top:none;">
						<span id="" name="">���Աȣ�</span>
						<span id="namejg" name="namejg"></span>
						<span id="" name="">�쵼��������ṹΪ</span>
						<span id="namecol" name="namecol"></span>
						<span id="hlqk" name="hlqk"></span>
						<br/><br/><span id="general_evaluation" name="general_evaluation"></span>
						</td>
					</tr>
					<tr height="60">
						<td class="boldText" style="border-left:none;border-bottom:none;">�Ż�<br/>����<br/>����<br/>ʩ</td>
						<td style="text-align:left;border-right:none;border-bottom:none;" id="optimize_direction" name="optimize_direction"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
		  </div>  
		   </odin:tabCont>
		   
		   
		   
		   <odin:tabCont itemIndex="tab2">
		   
		      <div id="jgmx1" style="width:100%;overflow: scroll;">
		   <table width="100%" border="0" >
				<tr height="50">
					<td class="headerText"><span class="headerText" id="unit1" name="unit1"></span>�쵼���ӳ�Ա������</td>
				</tr>
			</table>
		   <odin:editgrid property="TrainingInfoGrid" height="document.body.clientHeight-113" title="" autoFill="true" bbarId="pageToolBar" sm="row" remoteSort="true" hasRightMenu="true" rightMenuId="updateM" isFirstLoadData="false" pageSize="20"  url="/"  >
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a0101" />
					<odin:gridDataCol name="a0192a" />
 			  		<odin:gridDataCol name="a0107"  /> 
 			  		<odin:gridDataCol name="a0111a"  /> 
 			  		<odin:gridDataCol name="qrzxl"  /> 
 			  		<odin:gridDataCol name="qrzxlxx"  /> 
 			  		<odin:gridDataCol name="zzxl"  /> 
 			  		<odin:gridDataCol name="zzxlxx"  /> 
 			  		<odin:gridDataCol name="a0196"  /> 
 			  		<odin:gridDataCol name="a0134"  /> 
 			  		<odin:gridDataCol name="a0288"  /> 
			  		<odin:gridDataCol name="a0192c" isLast="true"/> 
				</odin:gridJsonDataModel>
					<odin:gridColumnModel>
				    <odin:gridRowNumColumn></odin:gridRowNumColumn>
				  	<odin:gridEditColumn2 header="����" align="center" dataIndex="a0101" edited="false" editor="text" width="100"/>
				  	<odin:gridEditColumn2 header="����ְ��" align="center" dataIndex="a0192a" edited="false" editor="text" width="100"/>
				  	<odin:gridEditColumn2 header="��������" align="center" dataIndex="a0107" editor="text"  edited="false"  width="100" /> 
 				  	<odin:gridEditColumn2 header="����" align="center" dataIndex="a0111a" editor="text" edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="ȫ����ѧ��" align="center" dataIndex="qrzxl" editor="text" edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="ȫ����ѧλ" align="center" dataIndex="qrzxlxx" editor="text"  edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="��ְѧ��" align="center" dataIndex="zzxl" editor="text"  edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="��ְѧλ" align="center" dataIndex="zzxlxx" editor="text" edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="רҵ����ְ��" align="center" dataIndex="a0196" editor="text" edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="�μӹ���ʱ��" align="center" dataIndex="a0134" editor="text" edited="false" width="100"/> 
 				  	<odin:gridEditColumn2 header="����ְ��ʱ��" align="center" dataIndex="a0288" editor="text" edited="false" width="100"/> 
				  	<odin:gridEditColumn2 width="65" header="�����Ƽ�ʱ��" dataIndex="a0192c" editor="text" edited="false"  isLast="true"/>
					</odin:gridColumnModel>
			</odin:editgrid>
		   
		   </div>
		   </odin:tabCont>
		   
		   
		   <odin:tabCont itemIndex="tab3">
		    <div id="jgmx2" style="width:100%;overflow: scroll;">
		   <table width="100%" border="0">
				<tr height="50">
					<td class="headerText"><span class="headerText" id="unit2" name="unit2"></span>�쵼���ӳ�Ա�ṹҪ�طֲ�����</td>
				</tr>
			</table>
			<table width="100%" hidden="document.body.clientHeight-113" cellpadding="0" cellspacing="0" class="structTable" id="jgysmc" name="jgysmc">
				<tr height="32" id="jgmcww">
					<td class="boldText">&nbsp;</td>
	<!-- 									<td class="boldText" colspan="3"/>11</td>  -->
	<!-- 									<td class="boldText" colspan="4"/>11</td>  -->
	<!-- 									<td class="boldText" colspan="2"/>11</td>  -->
					
				</tr>
				
				<!-- ���� -->						
				<tr height="32" id="jgmc2">			
					<td class="boldText">&nbsp;</td>									
<!-- 									<td class="normalText" style="word-break: keep-all;">2</td> -->
				</tr>
				
<!-- 				<tr height="32" id="jgmc3"> -->
<!-- 					<td class="normalText"><a href="###" title="����鿴�������" onclick="viewRemoval();">3</a></td> -->
<!-- 					<td class="normalText"></td> -->
<!-- 				</tr> -->
			</table>
			</div>
		   </odin:tabCont>
		   </odin:tab>
		   </div>
		   
		   <div id=MainBody1 style="" >
 				<center>
			<p style="line-height: 50px;font-size: 18px;text-align: center;font-weight: bold;">
				�õ�λû�н��нṹģ�����ã��޷����з�����
			</p>
			</center>
           </div>
		   
		   
		   
		   
		   
		   </div>
		   
		   
<!-- 		  </td> -->
<!-- 		</tr> -->
<!-- 		</table> -->
		
������<script>  
Ext.onReady(function(){
	var pgrid = Ext.getCmp('TrainingInfoGrid');
	var bbar = pgrid.getBottomToolbar();
	bbar.insertButton(11,[
	                      new Ext.menu.Separator({cls:'xtb-sep'}),
							new Ext.Button({
								icon : 'images/icon/table.gif',
								id:'expExcelFromGrid',
							    text:'����Excel',
							    handler:expExcelFromGrid2
							    
							    
							})
							
							]); 
});

	var ht=document.body.clientHeight-5;
    Ext.onReady(function(){
    	document.getElementById("MainBody").style.display="none";//����
    	document.getElementById("MainBody1").style.display="none";//����
    	
    	//document.getElementById("MainBody").style.visibility="hidden";//����
    	//document.getElementById("MainBody1").style.visibility="hidden";//����
    	$('#Sidebar').height(ht);
    	$('#MainBody').height(ht);
    	$('#jgmx').height(document.body.clientHeight-30);
    	$('#jgmx1').height(document.body.clientHeight);
    	$('#jgmx2').height(document.body.clientHeight);
    	$('#div_1').height(ht);
    	
    });
    
</script> 

</body>
</html>