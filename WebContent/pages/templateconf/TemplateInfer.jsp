<!DOCTYPE script PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK" isELIgnored="false"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 


<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link  type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<link type="text/css" href="<%=request.getContextPath()%>/jslib/easyui1.5/themes/insdep/easyui.css" rel="stylesheet"/>
<link  type="text/css" href="<%=request.getContextPath()%>/jslib/easyui1.5/themes/insdep/easyui_plus.css" rel="stylesheet"/>
<link  type="text/css" href="<%=request.getContextPath()%>/jslib/easyui1.5/themes/insdep/insdep_theme_default.css" rel="stylesheet"/>
<link type="text/css" href="<%=request.getContextPath()%>/jslib/easyui1.5/themes/insdep/icon.css" rel="stylesheet"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>


<script type="text/javascript" src="<%=request.getContextPath()%>/pages/notice/third-party/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery-ui.min.js"></script>


<script src="<%=request.getContextPath()%>/basejs/ext/ext-lang-zh_CN-GBK.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jslib/echarts/themes/macarons.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%@page import="com.insigma.siis.local.pagemodel.templateconf.TemplateInferPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.ZjzzyPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>

<script type="text/javascript">
var contextPath='<%=request.getContextPath()%>';
/**
 * 左侧机构树    
 */
 var ht=document.body.clientHeight-5;
var tree;
	Ext.onReady(function() {
		var man = document.getElementById('manager').value;
	    var Tree = Ext.tree;
	       tree = new Tree.TreePanel( {
	  	  	id:'group',
	        el : 'tree-div',//目标div容器
	        split:true,
	        height:ht,
	         width: 238,
	         minSize: 164,
	         maxSize: 164,
	         rootVisible: false,//是否显示最上级节点
	         autoScroll : true,
	         animate : true,
	         border:false,
	         enableDD : true,
	         containerScroll : true,
	        loader : new Tree.TreeLoader( {
	        	 baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
	              dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople'
	        })
	      
       /* 监听点击事件
       listeners: { 
	      	   click: function(node){
	      		   var pid=node.attributes.id;
	      		   var text=node.attributes.text;
	      		  // alert(pid+'---'+text)
	      		   document.getElementById('text').value = text;
	      		   document.getElementById('treeid').value = pid;
	      		   radow.doEvent("selectduty",pid);
	      	   }    
	         } */
	       
	    });
	       tree.on('click',treeClick); //tree点击事件
	       function treeClick(node,e){
	       	//var aa=node.parentNode.id; 
	       	$("#PicDiv").empty();
	       	 var gid = node.id;
	       	 //alert(gid);
	       	 document.getElementById("gid").value=gid;
	       	 $('#gid').val(gid);
	       	 
	       	 document.getElementById("reset").value = "reset";
	         radow.doEvent("selectduty",gid);
	       	

	       } 

	       
		var root = new Tree.AsyncTreeNode({
			checked : false,
			text : document.getElementById('ereaname').value,//节点上的文本信息
			iconCls : document.getElementById('picType').value,
			//text : "",
			//iconCls : document.getElementById('picType').value,
			draggable : false,
			id : document.getElementById('ereaid').value,//默认的node值：?node=-100
			href : "javascript:radow.doEvent('querybyid','"//节点的连接属性
				+ document.getElementById('ereaid').value + "')"
			//id :"-1"//默认的node值：?node=-100
			//href : "javascript:radow.doEvent('querybyid','" + document.getElementById('ereaid').value + "')"
		});
		tree.setRootNode(root);
		tree.render();
		//tree.expandPath(root.getPath(),null,function(){addnode();});
		root.expand(false,true, callback);

	}); 

	var callback = function (node){
		if(node.hasChildNodes()) {
			node.eachChild(function(child){
				child.expand();
			})
		}
	}
	function addnode(){
		var nodeadd = tree.getRootNode(); 
		var newnode = new Ext.tree.TreeNode({ 
			  text: '无职务人员', 
	          expanded: false, 
	          icon: '<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png',
	  	      id:'X001',
	          leaf: false 
	          //,
	          //dblclick:"javascript:radow.doEvent('querybyid','X001')"
	      });

	     // nodeadd.appendChild(newnode);
	}  	
	function reloadTree() {
		var tree = Ext.getCmp("group");
		//获取选中的节点  
		var node = tree.getSelectionModel().getSelectedNode();  
		if(node == null) { //没有选中 重载树  
			tree.root.reload();
		} else {        //重载树 并默认选中上次选择的节点    
		    var path = node.getPath('id');  
		    tree.getLoader().load(tree.getRootNode(),  
		                function(treeNode) {  
		                    tree.expandPath(path, 'id', function(bSucess, oLastNode) {  
		                                tree.getSelectionModel().select(oLastNode);  
		                            });  
		                }, this);    
		}  
	} 


/**
 * 高度自适应页面
 */
$(function(){
	$('#Sidebar').height(ht);
	$('#MainBody').height(ht);
	$('#tree-div').height(ht);
	$('tableBG').height(ht);
	
	$('#ResDiv').height(ht-150);
});


 

	


//拼接主体div标签数据
function znzsvalue(jg,person){
		$("#PicDiv").remove(".personPanel") ;
     	//console.log(person);
	    //机构id,机构名称,应配, 实配 ,(缺配、超配)      人员id,人员姓名,人员工作单位及职务
	    //省水利厅@省水利厅@9@3
	    //{"020020A@厅长":["a10dac70-544d-4347-bf7c-f192a7f7db2c@王利州@省水利厅省水利厅厅长、党组书记"],
	    //"020020B@副厅长":["69fab45a-5b25-421e-ad3c-d4bf206c0689@张玉怀@省水利厅省水利厅副厅长、党组成员","9ece73d4-5645-4903-82ff-8998a4b323db@黎宏@省水利厅省水利厅副厅长、党组成员"],
	    //"020075@总工程师":[],
	    //"11@一级巡视员":[]}
	    //402876816cc6dcc5016cc6e8c0b5000f,测试项目,1,1,1A21,0,a10dac70-544d-4347-bf7c-f192a7f7db2c|王利州|省水利厅省水利厅厅长、党组书记
		//402876816cc6dcc5016cc6e8c0b5000f,测试项目,1,1,1A21,0,a10dac70-544d-4347-bf7c-f192a7f7db2c|王利州|省水利厅省水利厅厅长、党组书记&
		if(jg){
			var parem = jg.split("@");
			var str="";
		    var str1="<option >请选择干部人选</option>";
		    str+="<div class='personPanel'  id='personPanel"+parem[0]+"' pname='"+parem[1]+"'>";
	    	// str1+="<option value='"+parem[0]+"'>"+parem[1]+"</option>";parseInt(maxMoney) <= parseInt(minMoney)
	    	 if(parseInt(parem[2]) == parseInt(parem[3])){
	    		 str+="<div class='title equals' ><label>"+parem[1]+"，职位数量应配"+parem[2]+"人，实配"+parem[3]+"人";
	    	 }else if(parseInt(parem[2]) > parseInt(parem[3])){
	    		 str+="<div class='title less' ><label>"+parem[1]+"，职位数量应配"+parem[2]+"人，实配"+parem[3]+"人，缺配"+(parseInt(parem[2])-parseInt(parem[3]))+"人"; 
	    	 }else if(parseInt(parem[2]) < parseInt(parem[3])){
	    		 str+="<div class='title over' ><label>"+parem[1]+"，职位数量应配"+parem[2]+"人，实配"+parem[3]+"人，超配"+(parseInt(parem[3])-parseInt(parem[2]))+"人";  
	    	 }
	    	 str+="</label></div><table border='0' width='100%' ><tr><td>";
	    	 str+="<ul class='personList' id='"+parem[0]+"' expectedamount='' >";   
	    	 if(person){
	    		 var v = eval('(' + person + ')');
	    		 for (var key in v) {
                     //console.log(key + ":" + v[key])  
                     var keyCode = key;
                     var value = v[key];
                     var codes = key.split("@");
                     var code = codes[0];
                     var name = codes[1];
                     var gwnum = codes[2];//职位应配
                     
                     if(value){
                    	 for (var i=0;i<value.length;i++) {
     						 var person = value[i];
                        	 var vals = person.split("@");
                        	 var a0000 = vals[0];
                        	 var a0101 = vals[1];
                        	 var a0192a = vals[2];
                        	 
                        	 str+="<li class='deducePerson' pname='"+a0101+"' pid='"+a0000+"' code='"+code+"'>";
        	    			 str+="<img onclick='onRemovePerson(this);' title='点击移除该人员' class='close' src='<%=request.getContextPath()%>/image/close.png'/>"
        	    			 str+="<img class='photo' src='<%=request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000="+a0000+"&width=50&height=66' width='50' height='66' />";
        	    			 str+="<a title='"+a0192a+"'>"+a0101+"</a></li>";
                         }
                    	 //如果职位应配gwnum > value.length 说明该职位缺配，画上空白位
                    	 if(parseInt(gwnum) > parseInt(value.length)){
                    		 for (var i=0;i<(gwnum-value.length);i++) {
         						 str+="<li class='deducePerson' pname='"+name+"' pid='"+code+"'>";
            	    			 <%-- str+="<img onclick='onRemovePerson(this);' title='点击移除该人员' class='close' src='<%=request.getContextPath()%>/image/close.png'/>" --%>
            	    			 <%-- str+="<img class='photo' src='<%=request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000="+a0000+"&width=50&height=66' width='50' height='66' />"; --%>
            	    			 str+="<a style='width:50px;height:66px;border:1px black solid;' onclick='queryCode(this)'>"+name+"</a>";
            	    			 /* str+="<a title='"+a0192a+"'>"+a0101+"</a></li>"; */
            	    			 str+="<a title='"+name+"' style='color:red'>缺岗</a></li>";
                             }
                    	 }
                     }else{
                    	 str+="<li class='emptyPerson'></li>";
                     }
                 }
	    	 }
	    	 str+="</ul></td></tr></table>";
    	     str+="</div>";
    	     
    	     $("#PicDiv").html(str);
		}
	    
		hoverDeducePerson();
}

function queryCode(obj, name){
	var li = $(obj).parent();
	var id = li.attr('pid');
	radow.doEvent("selcctPerson",id);
}

function personForWork(obj, name){
	var td = $(obj).parent();
	var a0000 = td.attr('pid');
	//alert(a0000);
	var b0111 = document.getElementById("gid").value;
	var value = a0000 + "$" + b0111;
	$h.openPageModeWin('selectWork','pages.templateconf.SelectWork','职位选择',600,500,value,'<%=request.getContextPath()%>',window);
}

/**
 * 拼接表格数据标签  hh
 */
 function ptable(pall){
	//console.log(pall);
	 var pAll = pall.split("&");
	 var cosp= pAll.length;
	 var str ="<tr><th width='60'>姓名</th><th >现任职务</th></tr>";
	 if(pall=="null"){
		str += "<tr class='targetPersonRow'><td align='center' pname='0' pid='0'></td>";
		str += "<td></td></tr>";
		$("#personListTable").html(str); 
	 }else{
	    for(var i = 0; i <pAll.length; i++){
			 var aa=pAll[i];
			 var parem = aa.split(",");
		     str+="<tr class='targetPersonRow'><td height='40px' align='center'  pname='"+parem[1]+"' pid='"+parem[0]+"' >"; 
		     str += "<img onclick='onRemovePerson(this);' title='点击移除该人员' class='close' src='<%=request.getContextPath()%>/image/close.png'/>";
		     str+="<img class='photo' src='<%=request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000="+parem[0]+"&width=50&height=66' width='50' height='66' />";
		     str+="<a title='"+parem[2]+"' onclick='personForWork(this);'>"+parem[1]+"</a></td><td>"+parem[2]+"</td></tr>";   
		    /*  str+="<tr class='targetPersonRow'><td align='center' pname='"+parem[1]+"' pid='"+parem[0]+"'>"+parem[1]+"</td>"; 
	         str+="<td title='"+parem[2]+"'>"+parem[2]+"</td></tr>";   */

		 } 
		 
		 $("#personListTable").html(str); 
	 }
	 
	
	 hoverDeducePerson();
	 
	 //图片拖动事件
	/*  $(".targetPersonRow").draggable({
 		 revert:true,  
 		 proxy:'clone' 
 	 }); */
	 
	 
	 //图片拖动到指定位置事件
	 /* $(".personList").droppable({
			accept: ".targetPersonRow",
			onDrop: function(e, source ) {
				var srcObj = $(source);
				var tds = srcObj.find('td');
				var person = {
					personcode: $(tds[0]).attr('pid'),
					xingm: $(tds[0]).attr('pname')
					
				};
				console.log(person);
				var key = $(this).attr('id');
				console.log(key);
				addPerson(person, key);
				addOrRemoveEmptyPerson(key);
				startProcess();
				
			}
	      
		}); */
	 
	  
}

function hoverDeducePerson(){
	 //删除图片按钮事件
	 $(".deducePerson").hover(
				function() {
					$(this).find('.close').css('display', 'inline');
				},
				function() {
					$(this).find('.close').css('display', 'none');
				}
			);
}


//删除图片事件
	function onRemovePerson(obj, name) {
		var ul = $(obj).parent().parent();
		var b0111 = ul.attr('id');
		var a0000 = $(obj).parent().attr('pid');
		var code = $(obj).parent().attr('code');
		
		if(b0111&&a0000&&code){
			var val = b0111 + "$" + a0000 + "$" + code;
			radow.doEvent("deleteOne",val);
		}else{
			alert("删除人员失败");
		}
		
		//$(obj).parent().remove();
					
		//addOrRemoveEmptyPerson(id);
		
		//startProcess();
	}

//增加行，移除行事件
function addOrRemoveEmptyPerson(key) {
   // console.log(key);
	// 先移除所有的空行
	$("#" + key + " .emptyPerson").remove();
	
	var obj = $("#" + key);
	var panelObj = $("#personPanel" + key);
	var titleObj = panelObj.find('div');
	var labelObj = titleObj.find('label');
	var expectedAmount = obj.attr('expectedAmount');
	var actualAmount = $("#" + key + " .deducePerson").length;
	var less = expectedAmount - actualAmount;
	var over = actualAmount - expectedAmount;
	var name = panelObj.attr('pname');
	
	if (less > 0) {
		var html = '';
		for (var i = 0; i < less; i++) {
			html += '<li class="emptyPerson"></li>';
		}
		if (html != '') {
			obj.append(html);
		}
		
		titleObj.removeClass('less');
		titleObj.removeClass('over');
		titleObj.removeClass('equals');
		titleObj.addClass('less');
		labelObj.html(name+ ',' + expectedAmount + '人，缺配' + less + '人');
	}
	
	if (over > 0) {
		titleObj.removeClass('less');
		titleObj.removeClass('over');
		titleObj.removeClass('equals');
		titleObj.addClass('over');
		labelObj.html(name+ ',' +expectedAmount + '人，超配' + over + '人');
	}
	
	if (less == 0 && over == 0) {
		titleObj.removeClass('less');
		titleObj.removeClass('over');
		titleObj.removeClass('equals');
		titleObj.addClass('equals');
		labelObj.html(name + ',' +expectedAmount + '人');
	}
}

function resetper(){
	var gid = document.getElementById("gid").value;
	document.getElementById("reset").value = "reset";
	radow.doEvent("selectduty",gid);
}

function fac(sql){
	//console.log(sql);
	var sql1 = sql.replace(/#/g,"'")
	//alert(sql1);
	document.getElementById("sql").value=sql1
	var pa="";
	//$h.openWin('BackCheck','pages.templateconf.BackCheck&initParams='+sql1,'自然结构反查情况', 800, 500,'', contextPath);
	window.showModalDialog('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.templateconf.BackCheck',window,'dialogWidth:900px; dialogHeight:528px; status:no;directories:yes;scrollbars:no;resizable:no;help:no');
}

var myChart;
Ext.onReady(function(){
	myChart = echarts.init(document.getElementById('div2'));
	initEchart();
});

function initEchart(){
	$("#ztzk").html("");
	$("#backColor").css("background-color","");
	
	$("#bzf").text("0");
	$("#xbf").text("0");
	$("#dpf").text("0");
	$("#mzf").text("0");
	$("#nlf").text("0");
	$("#xlf").text("0");
	$("#zyf").text("0");
	$("#dyf").text("0");
	$("#knowf").text("0");
	$("#jlf").text("0");
	$("#total").text("0");
	$("#redf").val("0");
	$("#greenf").val("0");
	
	option = {
		    title: {
		        text: '班子结构',
		        left: 'center'
		    },
		    radar: [
		        {
		            indicator: [
		                { text: '人数', max:10  },
		                { text: '性别', max:10  },
		                { text: '党派', max:10  },
		                { text: '年龄', max:10  },
		                { text: '民族', max:10  },
		                { text: '学历', max:10  },
		                { text: '专业', max:10  },
		                { text: '地域', max:10  },
		                { text: '熟悉领域', max:10  },
		                { text: '经历', max:10  }
		            ],
		            center: ['50%', '50%'],
		            radius: 130
		        }
		    ],
		    series: [
		        {
		            name: '得分',
		            type: 'radar',
		            data: [
		                {
		                    value: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
		                    name: '得分'
		                }
		            ]
		        }
		    ]
		}; 
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

function appendTable(index,obj){
	$("tr").remove(".newTr") ;
	
	var val = eval('(' + obj + ')');
	//console.log(val.tr0);
	if(val){
		for(var i=0;i<index;i++){
			var tri = "val.tr"+i;
			var v = eval('(' + tri + ')');
			var aaa = "a"+i;
			//console.log(v);
			var htmlList = '<tr class=\"newTr\" id=\"'+v.id+'\">';
			//htmlList += '<td><input style=\"display: none\" type=\"text\" width=\"80\" value=\"'+v.a1700+'\"></td>';
			//htmlList += '<td><input style=\"display: none\" type=\"text\" width=\"80\" value=\"'+v.a0000+'\"></td>';
			htmlList += '<td height="50">'+v.category+'</td>';
			htmlList += '<td height="50">'+v.project+'</td>';
			htmlList += '<td height="50">'+v.quantity+'</td>';
			htmlList += '<td height="50"><a id="'+aaa+'" href="javaScript:void(0)">'+v.count+'</a></td>';
			htmlList += '<td height="50">'+v.one_ticket_veto+'</td>';
			htmlList += '</tr>';
			//在行最后添加数据
		    $("#tabNature tr:last").after(htmlList);
			
		    jueryArea(aaa,v.id);
		}
		
	}
}

function appendNullTable(){
	$("tr").remove(".newTr") ;
}

function jueryArea(a,obj){
	var v = eval('(' + a + ')');
	$(v).click(function() {
		$h.openPageModeWin('backCheck','pages.templateconf.BackCheck','人员反查详情',900,520,obj,'<%=request.getContextPath()%>',window);
	});
}

function appendScore(obj){
	var val = eval('(' + obj + ')');
	if("null"==val||!val){
		initEchart();
		return;
	}
	var statustype = val.statustype;
	if("0"==statustype){
		$("#ztzk").html("<font style='color: red'>红（一票否决）</font>");
		$("#backColor").css("background-color","red");
	}else if("1"==statustype){
		$("#ztzk").html("<font style='color: orangeRed'>红（非一票否决，总分未合格）</font>");
		$("#backColor").css("background-color","orangeRed");
	}else if("2"==statustype){
		$("#ztzk").html("<font style='color: yellow'>黄</font>");
		$("#backColor").css("background-color","yellow");
	}else if("3"==statustype){
		$("#ztzk").html("<font style='color: green'>绿</font>");
		$("#backColor").css("background-color","green");
	}
	
	var bzf = val.bzf;
	var xbf = val.xbf;
	var dpf = val.dpf;
	var mzf = val.mzf;
	var nlf = val.nlf;
	var xlf = val.xlf;
	var zyf = val.zyf;
	var dyf = val.dyf;
	var knowf = val.knowf;
	var jlf = val.jlf;
	var score = val.score;
	var redf = val.redf;
	var greenf = val.greenf;
	var m = val.max;//最大分值
	
	$("#bzf").text(bzf);
	$("#xbf").text(xbf);
	$("#dpf").text(dpf);
	$("#mzf").text(mzf);
	$("#nlf").text(nlf);
	$("#xlf").text(xlf);
	$("#zyf").text(zyf);
	$("#dyf").text(dyf);
	$("#knowf").text(knowf);
	$("#jlf").text(jlf);
	$("#total").text(score);
	$("#redf").val(redf);
	$("#greenf").val(greenf);
	
	option = {
		    title: {
		        text: '班子结构',
		        left: 'center'
		    },
		    radar: [
		        {
		            indicator: [
		                { text: '人数', max:m  },
		                { text: '性别', max:m  },
		                { text: '党派', max:m  },
		                { text: '年龄', max:m  },
		                { text: '民族', max:m  },
		                { text: '学历', max:m  },
		                { text: '专业', max:m  },
		                { text: '地域', max:m  },
		                { text: '熟悉领域', max:m  },
		                { text: '经历', max:m  }
		            ],
		            center: ['50%', '50%'],
		            radius: 130
		        }
		    ],
		    series: [
		        {
		            name: '得分',
		            type: 'radar',
		            data: [
		                {
		                    value: [bzf, xbf, dpf, nlf, mzf, xlf, zyf, dyf, knowf, jlf],//'人数','性别','党派'，'年龄'，'民族'，'学历'，'专业'，'地域'，'熟悉领域'，'经历'
		                    //value: [10, 10, 10, 10, 10, 10, 10, 10, 10, 10],
		                    name: '得分'
		                }
		            ]
		        }
		    ]
		}; 
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

<%
SysOrgPageModel sys = new SysOrgPageModel();
String picType = (String)(new ZjzzyPageModel().areaInfo.get("picType"));
String ereaid = (String) (sys.areaInfo.get("areaid"));
String ereaname = (String) (sys.areaInfo.get("areaname"));
String manager = (String) (sys.areaInfo.get("manager"));
%>


function reflesh(){
	document.getElementById("a0215a_c_sign").value="1";
	radow.doEvent("dataTb");
}
</script>





<style type="text/css">
	#PageBody{width:100%;}
	#Sidebar{float:left;width:15%; border-right:5px solid #c3daf9;margin-top:-15px;}
	#MainBody{width:65%;float:left;margin-top:-15px;}
	#PicDiv{height:120;border: 1px solid #bfbfbf;overflow:auto;}
	.personPanel{border: 1px solid #bfbfbf;float:left;width:100%;height: 100%}
	.personPanel .title {padding: 2px;height: 20px;text-align: center;}
	.personPanel .title label {color: #ffffff;font-weight: bold;text-align: center;}
    .personPanel .less {background-color: yellow;}
	.personPanel .less label{color: #000000;}
	.personPanel .over {background-color: red;}
	.personPanel .equals {background-color: #1da02b;} 
	.personList .highlight {border:1px dashed #fc0;background-color: yellow;}
	.personList {margin: 0;padding: 2px;font-size: 0;-webkit-text-size-adjust:none; }
	.personList .emptyPerson {border:1px dashed #cccccc;background-color: #f5f5f5;width: 50px;height: 82px;float:left;}
	.personList li {list-style-type:none;  text-align: center;color: #686f74;overflow: hidden;font-size: 12px;
	                margin-top:2px;display: inline-block;width: 50px;height: 84px;margin-left:5px;position: relative;}
	.personList .deducePerson {cursor: pointer;float:left;} 
	.personList li .photo {display: block;} 
	.personList li .close {position: absolute;top: 2px;right: 2px;cursor: pointer;display: none;z-index: 100;}  
	.inputtable{border-collapse:collapse;}
	.inputtable th{border-collapse:collapse;background:#EFEFEF;width:100px;border:1px dotted #CCCCCC;
	               font-weight:bold;text-align:center;padding:3px;}
    .inputtable tr td{border-collapse:collapse;border:1px dotted #CCCCCC;width:100px;padding:3px;}
     table td{font-size:12px;}
     table tr td img{display: none;}
		#suppertext{
			width: 99%;
			border: none;
			overflow:hidden;
			resize:none;
			white-space:pre-wrap;
			white-space:-moz-pre-wrap;
		 	white-space:-o-pre-wrap;
		 	word-wrap:break-word;
		}
		#tableTest{
			margin-top: 20px;
		}
		#tableTest tr td{
			font-size: 16px;
			font-family: "黑体";
		}
	
		.headerText {
				font-size: 24px;
				line-height: 26px;
				font-weight: bold;
				text-align: center;				
		}
		.structText {
			font-size: 20px;
			/* line-height: 20px; */
			font-weight: bold;
			font-family: 楷体_GB2312;
		}
		
		.nested {
			border: 1px solid #000000;
			border-collapse:collapse;				
		}
		
		.nested td {
			border: 1px solid #000000;	
			empty-cells: show;				
		}

		.fzxq{
			width: 9%;
			text-align: center;
			font-family: 宋体;
			font-size: 16px
		}

     
</style> 
<body>

<div id="PageBody"> <!--页面主体--> 
		<br/>
		   <div id="Sidebar" > <!--侧边栏--> 
		   
		        <div id="tree-div" style="overflow: auto;width: 15%; "></div>
		   </div> 
		<div id="MainBody">
			<!--主体内容-->
			<table>
				<tr>
					<td>
						<!--图片拖动div  -->
						<div id="PicDiv"></div>
						<div region="south" border="false"
							style="height: 20px; margin-top: 2px; border: 0px solid red; background-color: #cedff5;">
							<table border="0" align="right">
								<tr>
									<td><odin:button text="重置人员" handler="resetper" /><td>
								</tr>
							</table>
		                </div>
		                <div id="ResDiv" style="overflow: auto;">
		                
							<div id="jgmx" style="width:100%;padding-left: 30px;padding-right: 30px;overflow: auto;">
								   <table width="100%" border="0" >
										<tr height="60">
											<td class="headerText"><span class="headerText" id="unit" name="unit"></span>领导班子结构模型分析表</td>
										</tr>
									</table>
									<div>
									<table border="0" cellpadding="0" cellspacing="0" style="float: left;">
										<tr height="32">
											<td class="structText" style="width: 110px">整体状况：</td>
											<td  style="width: 240px"><span id="ztzk"></span></td>
										</tr>
										<tr height="32">
											<td class="structText" style="width: 110px">显色情况：</span></td>
											<td  style="width: 20px" id="backColor" ></td>
										</tr>
									</table>
									
									<table class="nested" style="text-align: center;float: right;" >
								    <tr style="width: 100%;height: 20px;">
								       <td style="width: 150px" rowspan="3"> <font class="font-tab">附：评分标准</font></td>
								       <td style="width: 150px;background-color: orangeRed"></td>
								       <td style="width: 150px"> 
											<font class="font-tab">&nbsp;&nbsp;&nbsp;小于</font>
											<input id="redf" type="text" value="" style="width: 60px"/>
								       </td>
								    </tr>
								    <tr style="width: 100%;height: 20px;">
								       <td style="width: 150px;background-color: yellow"></td>
								       <td> 
											<!-- <input id="" type="text" value="10" style="width: 60px"/> -->
											<font class="font-tab"></font><input id="redf" type="text" value="60" style="width: 60px;display: none"/>
								       </td>
								    </tr>
								    <tr style="width: 100%;height: 20px;">
								       <td style="width: 150px;background-color: green"></td>
								       <td> 
											<font class="font-tab">大于等于</font><input id="greenf" type="text" value="" style="width: 60px"/>
								       </td>
								    </tr>
								   </table>	
								    </div>
								    
									<table id="zsTable" style="width:100%;height:60px;margin-top: 30px" cellpadding="0" cellspacing="0" class="nested" > 
											<tr height="50">
												<td colspan="4" style="text-align: center;font-family: 宋体;font-weight: bolder;;font-size: 20px;">一、分值详情</td>
											</tr>
											<tr height="50">
												<td class="fzxq">领导班子<br/>人数</td>
												<td class="fzxq">性别</td>
												<td class="fzxq">党派</td>
												<td style="width: 30%" rowspan="8">
													<div id="div2" style="width:100%;height:100%;text-align:center;">
							   						</div>
												</td>
											</tr>
											<tr id="yp1" height="50">
												<td  style="text-align: center;"><font id="bzf"></font></td>
												<td style="text-align: center;"><font id="xbf"></font></td>
												<td style="text-align: center;"><font id="dpf"></font></td>
											</tr>	
											<tr height="50">
												<td class="fzxq">民族</td>
												<td class="fzxq">年龄</td>
												<td class="fzxq">学历</td>
											</tr>
											<tr id="yp1" height="50">
												<td style="text-align: center;"><font id="mzf"></font></td>
												<td style="text-align: center;"><font id="nlf"></font></td>
												<td style="text-align: center;"><font id="xlf"></font></td>
											</tr>	
											<tr height="50">
												<td class="fzxq">专业</td>
												<td class="fzxq">地域</td>
												<td class="fzxq">熟悉领域</td>
											</tr>	
											<tr id="yp1" height="50">
												<td style="text-align: center;"><font id="zyf"></font></td>
												<td style="text-align: center;"><font id="dyf"></font></td>
												<td style="text-align: center;"><font id="knowf"></font></td>
											</tr>
											
											<tr>
												<td class="fzxq" height="50">经历</td>
												<td class="fzxq">总得分</td>
												<td></td>
											</tr>
											<tr>
												<td style="text-align: center;" height="50"><font id="jlf"></font></td>
												<td style="text-align: center;"><font id="total"></font></td>
												<td></td>
											</tr>
												
									</table>
									
									<table id="tabNature" class="nested" style="margin-top: 30px;text-align: center;width:100%;height:50px;">
										<tr  height="50">
											<td align="center" colspan="5" style="text-align: center;font-family: 宋体;font-weight: bolder;;font-size: 20px;">二、自然结构</td>
										</tr>
										<tr  height="50">
											<td style="width: 20%">项目</td>
											<td style="width: 20%">要素</td>
											<td style="width: 20%">目标</td>
											<td style="width: 20%">现状</td>
											<td style="width: 20%">一票否决</td>
										</tr>
									</table>
							</div>
		                </div>
		             </td>
		           
		           <!--表格显示  -->  
		          <!-- <td style="border-left:5px solid #c3daf9;" width=260>
		                 <table id="tableBG">
							<tr>
								<td width="260" valign="top">
									<div style="width: 238; height: 100%; vertical-align: top; position: absolute; overflow: auto;">
										<table id="personListTable" class="inputtable"> 
										</table>
									</div>
								</td>
							</tr>
						</table>
					</td> -->
	           </tr>
	        </table>
	   </div>
	    <!--表格显示  -->  
	   <div tableBG style="width: 218; vertical-align: top; overflow: auto;margin-top:-15px;">
			<table id="personListTable" class="inputtable"> 
			</table>
	   </div>          
	                            
		    
		   
		   
		   
</div>
  <!-- 获取节点id -->
<odin:hidden property="treeid"/>
<!-- 获取节点内容 -->
<odin:hidden property="text"/>
<odin:hidden property="strs"/>
<odin:hidden property="ids"/>
<odin:hidden property="gid"/>
<odin:hidden property="sql"/>
<odin:hidden property="persons"/>
<odin:hidden property="quan"/>
<odin:hidden property="years"/>
<odin:hidden property="reset"/>
<odin:hidden property="a0215a_c_sign"/>
<odin:hidden property="picType" value="<%=picType%>" />
<odin:hidden property="ereaname" value="<%=ereaname%>" />
<odin:hidden property="ereaid" value="<%=ereaid%>" />
<odin:hidden property="manager" value="<%=manager%>" />
</body>
</html>