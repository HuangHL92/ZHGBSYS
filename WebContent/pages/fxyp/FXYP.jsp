<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<style>
.x-grid3-cell-inner, .x-grid3-hd-inner{
white-space:normal !important;
}

</style>

<script  type="text/javascript">
var T = Ext.Toolbar;
Ext.override(Ext.PagingToolbar, {
	initComponent : function(){
        var pagingItems = [this.first = new T.Button({
           // tooltip: this.firstText,
            overflowText: this.firstText,
            iconCls: 'x-tbar-page-first',
            disabled: true,
            handler: this.moveFirst,
            scope: this
        }), this.prev = new T.Button({
          //  tooltip: this.prevText,
            overflowText: this.prevText,
            iconCls: 'x-tbar-page-prev',
            disabled: true,
            handler: this.movePrevious,
            scope: this
        }), '-', this.beforePageText,
        this.inputItem = new Ext.form.NumberField({
            cls: 'x-tbar-page-number',
            allowDecimals: false,
            allowNegative: false,
            enableKeyEvents: true,
            selectOnFocus: true,
            listeners: {
                scope: this,
                keydown: this.onPagingKeyDown,
                blur: this.onPagingBlur
            }
        }), this.afterTextItem = new T.TextItem({
            text: String.format(this.afterPageText, 1)
        }), '-', this.next = new T.Button({
           // tooltip: this.nextText,
            overflowText: this.nextText,
            iconCls: 'x-tbar-page-next',
            disabled: true,
            handler: this.moveNext,
            scope: this
        }), this.last = new T.Button({
           // tooltip: this.lastText,
            overflowText: this.lastText,
            iconCls: 'x-tbar-page-last',
            disabled: true,
            handler: this.moveLast,
            scope: this
        }), '-', this.refresh = new T.Button({
          //  tooltip: this.refreshText,
            overflowText: this.refreshText,
            iconCls: 'x-tbar-loading',
            handler: this.refresh,
            scope: this
        })];


        var userItems = this.items || this.buttons || [];
        this.items = [];
        if(this.displayInfo){
            //this.items.push('->');
            this.items.push(this.displayItem = new T.TextItem({}));
        }
        this.items.push('->');
        if (this.prependButtons) {
            this.items.push(userItems.concat(pagingItems));
        }else{
            this.items.push(pagingItems.concat(userItems));
        }
        delete this.buttons;
        
        Ext.PagingToolbar.superclass.initComponent.call(this);
        this.addEvents(
            'change',
            'beforechange'
        );
        this.on('afterlayout', this.onFirstLayout, this, {single: true});
        this.cursor = 0;
        this.bindStore(this.store);
    }
});


var g_contextpath = '<%= request.getContextPath() %>';

function shareInfo(value, params, record, rowIndex, colIndex, ds) {
	return "<div align='center' width='100%' ><font color=blue>"
	+ "<a style='cursor:pointer;' onclick=\"loadadd(0,0,0,'"+record.data.fxyp00+"');\">编辑条件</a>"
	+ "</font></div>"
	
}
function fxyp03Renderer(value, params, record, rowIndex, colIndex, ds) {
	value = value||""
	value = value.replace(/\r\n|\r|\n/g,"<br/>");
	return  "<div align='left' width='100%' ><font color=blue>"
	+ "<a style='cursor:pointer;' onclick=\"loadadd(0,0,0,'"+record.data.fxyp00+"');\">编辑条件</a>"
	+ "</font></div>"
	+ "<a>"+value+"</a>";
	
}
function editorRenderer(value, params, record, rowIndex, colIndex, ds) {
	value = value||""
	value = value.replace(/\r\n|\r|\n/g,"");
	return "<div align='left' width='100%' ><font color=blue>"
	+ "<a style='cursor:pointer;' onclick=\"editorfxyp02('"+record.data.fxyp00+"','"+value+"');\">编辑岗位</a>"
	+ "</font></div>"
	+"<a>"+value+"</a>";
	
}

</script>
<table style="width: 100%">
  <tr>
    <td style="">
<odin:toolBar property="btnToolBar" >
	<odin:buttonForToolBar text="查询" icon="image/u45.png" handler="search" id="Search" />
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="增加" icon="image/icon021a2.gif" handler="addGW" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除" icon="image/icon021a3.gif" isLast="true" handler="deleteRow" id="infoDelete" />
	
</odin:toolBar>

<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" topBarId="btnToolBar"
  bbarId="pageToolBar" forceNoScroll="true" url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="fxyp00" />  
		<odin:gridDataCol name="fxyp02"/>
		<odin:gridDataCol name="fxyp03" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="fxyp02" width="100" header="岗位名称" editor="text" edited="false" renderer="editorRenderer"  align="left"/>
		<odin:gridEditColumn2 dataIndex="fxyp03" width="250" header="条件" editor="text" edited="false"  renderer="fxyp03Renderer" isLast="true" align="left"/>
		<%-- <odin:gridEditColumn2 dataIndex="fxyp00" width="80" header="操作" editor="text" edited="false" align="center" renderer="shareInfo" /> --%>
	</odin:gridColumnModel>
</odin:editgrid2>
    </td>
    <td  style="vertical-align: top;">
<odin:editgrid2 property="personGrid" hasRightMenu="false" topBarId="pageToolBar"
  forceNoScroll="true" url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="a0000" />  
		<odin:gridDataCol name="a0101"/>
		<odin:gridDataCol name="a0104"/>
		<odin:gridDataCol name="a0107"/>
		<odin:gridDataCol name="a0288"/>
		<odin:gridDataCol name="a0192c"/>
		<odin:gridDataCol name="zgxl"/>
		<odin:gridDataCol name="zgxw"/>
		<odin:gridDataCol name="a0192a" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="a0101" width="60" header="姓名" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0104" width="30" header="性别" codeType="GB2261" editor="select" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0107" width="60" header="出生年月" renderer="getTime" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0288" width="60" header="现职务层次时间" renderer="getTime" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0192c" width="60" header="现职级时间" renderer="getTime" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="zgxl" width="60" header="学历" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="zgxw" width="60" header="学位" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0192a" width="160" header="单位及职务" editor="text" edited="false" align="left" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>
<table id="picdata" style=" width: 100%;margin-top:0px;display: none;text-align:center;" cellspacing="0px" cellspacing="1">
<tr style="background-color: #cedff5" height="20px" align='left'>
	<td colspan="3">
		<table>
			<tr>
				<td align="left" width="230px"><font style="font-size: 12">共</font><font id="l2" style="font-size: 12"></font><font style="font-size: 12">条记录</font></td>
				<td  width="370px"><odin:button text="列表模式" property="liebiaomoshi" handler="gridshow"/></td>
				<td><odin:button text="首页" property="fristpage2" handler="fristpage2"/></td>
				<td><odin:button text="上一页" property="lastpage2" handler="lastpage2"/></td>
				<td><odin:textEdit property="page3" label="当前页:" width="50" readonly="true" value="1"/></td>
				<td align="left" width="60px"><font style="font-size: 12">共</font><font id="page4" style="font-size: 12"></font><font style="font-size: 12">页</font></td>
				<td><odin:button text="下一页" property="nextpage2" handler="nextpage2"/><td>
				<td><odin:button text="尾页" property="endpage2" handler="endpage2"/><td>
				
			</tr>
		</table>
	</td>
</tr>
<tr><td colspan="3" style="height: 40px;">&nbsp;</td></tr>
<tr valign='top' style=''>
	<td>
		<table>
			<tr valign='top' >
				<td style="height: 170px;width: 136px;"><img id="datai0" height="170" width="136" alt="" src="" ondblclick="querypep(name)"></td>
				<td style="vertical-align:top;" align='left'>
					<span id="sp0" ><font id="dataf0" style="padding-left:18px;font-size:14px;"></font></span>
					<br>
					<br>
					<font id="resume0" style="width: 160px;font-size: 14px;height:170px;padding-left:20px"></font>
					
				</td>
			</tr>
		</table>
	</td>
	<td>
		<table>
			<tr valign='top'>
				<td style="height: 170px;width: 136px;"><img id="datai1" height="170" width="136" alt="" src="" ondblclick="querypep(name)"></td>
				<td style="vertical-align:top;" align='left'>
					<span id="sp1"><font id="dataf1" style="padding-left:18px;font-size:14px;"></font></span>
					<br>
					<br>
					<font id="resume1" style="width: 160px;font-size: 14px;height:170px;padding-left:20px"></font>
				</td>
			</tr>
		</table>
	</td>
	<td>
		<table>
			<tr valign='top'>
				<td style="height: 170px;width: 136px;"><img id="datai2" height="170" width="136" alt="" src="" ondblclick="querypep(name)"></td>
				<td style="vertical-align:top;" align='left'>
					<span id="sp2"><font id="dataf2" style="padding-left:18px;font-size:14px;"></font></span>
					<br>
					<br>
					<font id="resume2" style="width: 160px;font-size: 14px;height:170px;padding-left:20px"></font>
				</td>
			</tr>
		</table>
	</td>
</tr>
<tr><td colspan="3" style="height: 15px;">&nbsp;</td></tr>
<tr valign='top' style=''>
	<td>
		<table>
			<tr valign='top'>
				<td style="height: 170px;width: 136px;"><img id="datai3" height="170" width="136" alt="" src="" ondblclick="querypep(name)"></td>
				<td style="vertical-align:top;" align='left'>
					<span id="sp3"><font id="dataf3" style="padding-left:18px;font-size:14px;"></font></span>
					<br>
					<br>
					<font id="resume3" style="width: 160px;font-size: 14px;height:170px;padding-left:20px"></font>
				</td>
			</tr>
		</table>
	</td>
	<td>
		<table>
			<tr valign='top'>
				<td style="height: 170px;width: 136px;"><img id="datai4" height="170" width="136" alt="" src="" ondblclick="querypep(name)"></td>
				<td style="vertical-align:top;" align='left'>
					<span id="sp4"><font id="dataf4" style="padding-left:18px;font-size:14px;"></font></span>
					<br>
					<br>
					<font id="resume4" style="width: 160px;font-size: 14px;height:170px;padding-left:20px"></font>
				</td>
			</tr>
		</table>
	</td>
	<td>
		<table>
			<tr valign='top'>
				<td style="height: 170px;width: 136px;"><img id="datai5" height="170" width="136" alt="" src="" ondblclick="querypep(name)"></td>
				<td style="vertical-align:top;" align='left'>
					<span id="sp5"><font id="dataf5" style="padding-left:18px;font-size:14px;"></font></span>
					<br>
					<br>
					<font id="resume5" style="width: 160px;font-size: 14px;height:170px;padding-left:20px"></font>
				</td>
			</tr>
		</table>
	</td>
</tr>


</table>
	</td>
  </tr>
</table>



		


<odin:hidden property="fxyp00"/>
<odin:hidden property="fxyp02"/>
<odin:hidden property="sql"/>
<odin:hidden property="picA0000s"/>
<!-- 1列表模式2照片模式 -->
<odin:hidden property="tableType" value="1"/>


<script type="text/javascript">
function querypep(name){
	 if(name==""){
		 return;
	 }
	 radow.doEvent("pic.dbclick",name);
}
//小资料
function datashow(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请先进行查询！',null,150);
		return;
	}
	document.getElementById("tableType").value="2";
	/* 隐藏列表 */
	Ext.getCmp('personGrid').hide();
	document.getElementById("picdata").style.display='block';
	for(var i=0;i<6;i++){
		document.getElementById("datai"+i).src="";
		document.getElementById("datai"+i).alt="";
		document.getElementById("dataf"+i).innerHTML="";
		document.getElementById("datai"+i).name="";
		document.getElementById("datai"+i).style.display="none";
		//document.getElementById("datac"+i).style.display="none";
		document.getElementById("resume"+i).innerHTML="";
	}
	document.getElementById("page3").value="1";
	radow.doEvent("ShowData");

}
function setlength2(size){
	document.getElementById("l2").innerHTML=size;
	var p = size/6;
	document.getElementById("page4").innerHTML = Math.ceil(p);
}
function showdata(i,a0000,a0101,data,src){
	//document.getElementById("datai"+i).src=path;
	if(!src){
		document.getElementById("datai"+i).style.display="";
	}
	document.getElementById("datai"+i).alt=a0101;
	document.getElementById("datai"+i).name=a0000;
	document.getElementById("dataf"+i).innerHTML=a0101;
	//document.getElementById("datac"+i).style.display="";
	//document.getElementById("resume"+i).innerHTML=data;
	var s=cutString(data,200);
	document.getElementById('resume'+i).innerHTML=s;
	document.getElementById("resume"+i).title=data;
	
}
function fristpage2(){
	 for(var i=0;i<6;i++){
			document.getElementById("datai"+i).src="";
			document.getElementById("datai"+i).alt="";
			document.getElementById("dataf"+i).innerHTML="";
			document.getElementById("datai"+i).name="";
			//document.getElementById("datac"+i).checked=false;
			document.getElementById("datai"+i).style.display="none";
			//document.getElementById("datac"+i).style.display="none";
			document.getElementById("resume"+i).innerHTML="";
		}
	 document.getElementById("picA0000s").value = "";
	 document.getElementById("page3").value=1;
	 radow.doEvent("datashow");
	 //checkAllPeople();
}
function lastpage2(){
	var page=document.getElementById("page3").value;
	if(page=="1"){
		//alert("已经是第一页");
		Ext.Msg.alert("系统提示","已经是第一页！");
		return;
	}
	for(var i=0;i<6;i++){
		document.getElementById("datai"+i).src="";
		document.getElementById("datai"+i).alt="";
		document.getElementById("dataf"+i).innerHTML="";
		document.getElementById("datai"+i).name="";
		//document.getElementById("datac"+i).checked=false;
		document.getElementById("datai"+i).style.display="none";
		//document.getElementById("datac"+i).style.display="none";
		document.getElementById("resume"+i).innerHTML="";
	}
	document.getElementById("picA0000s").value = "";
	var newpage=parseInt(page)-1;
	document.getElementById("page3").value=newpage;
	radow.doEvent("datashow");
	//checkAllPeople();
}
/* 小资料翻页方法 */
function nextpage2(){
	var page=document.getElementById("page3").value;
	var length=document.getElementById("l2").innerHTML;
	var maxpage=Math.ceil(length/6);
	var newpage=parseInt(page)+1;
	if(newpage>maxpage){
		//alert("已经是最后一页");
		Ext.Msg.alert("系统提示","已经是最后一页！");
		return;
	}
	for(var i=0;i<6;i++){
		document.getElementById("datai"+i).src="";
		document.getElementById("datai"+i).alt="";
		document.getElementById("dataf"+i).innerHTML="";
		document.getElementById("datai"+i).name="";
		//document.getElementById("datac"+i).checked=false;
		document.getElementById("datai"+i).style.display="none";
		//document.getElementById("datac"+i).style.display="none";
		document.getElementById("resume"+i).innerHTML="";
	}
	document.getElementById("picA0000s").value = "";
	document.getElementById("page3").value=newpage;

	radow.doEvent("datashow");
	//checkAllPeople();
}
function endpage2(){
	 var page=document.getElementById("page3").value;
		var length=document.getElementById("l2").innerHTML;
		var maxpage=Math.ceil(length/6);
		for(var i=0;i<6;i++){
			document.getElementById("datai"+i).src="";
			document.getElementById("datai"+i).alt="";
			document.getElementById("dataf"+i).innerHTML="";
			document.getElementById("datai"+i).name="";
			//document.getElementById("datac"+i).checked=false;
			document.getElementById("datai"+i).style.display="none";
			//document.getElementById("datac"+i).style.display="none";
			document.getElementById("resume"+i).innerHTML="";
		}
		document.getElementById("picA0000s").value = "";
		document.getElementById("page3").value=maxpage;
		radow.doEvent("datashow");
		//checkAllPeople();
}
/* 列表显示 */
function gridshow(){
	var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请先进行查询！',null,180);
		return;
	}
	document.getElementById("tableType").value="1";
	
	Ext.getCmp('personGrid').show();
	document.getElementById("picdata").style.display='none';
	
	var grid = Ext.getCmp('personGrid');    //通过grid的id取到grid
	radow.doEvent("personGrid.dogridquery");     //将数据reload()就可以刷新了
}
function cutString(str, len) {
	//alert(str.length);
	  //length属性读出来的汉字长度为1
	  if(str.length*2 <= len) {
		  return str;
	  }
	  var strlen = 0;
	  var s = "";
	  for(var i = 0;i < str.length; i++) {
		  s = s + str.charAt(i);
		  if (str.charCodeAt(i) > 128) {
			  strlen = strlen + 2;
			  if(strlen >= len){
				  return s.substring(0,s.length-1) + "...";
			  }
		  } else {
			  strlen = strlen + 1;
			  if(strlen >= len){
				  return s.substring(0,s.length-2) + "...";
			  }
		  }
	  }
	  return s;
 }
 
 
 
 
 
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-20);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width/4);
	
	var personGrid = Ext.getCmp('personGrid');
	personGrid.setHeight(viewSize.height-20);
	personGrid.setWidth(viewSize.width/4*3-10);

	memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		$("#fxyp00").val(rc.data.fxyp00);
		$("#fxyp02").val(rc.data.fxyp02);
	});
	
	memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		radow.doEvent('mQueryonclick');
	});
	
	
	
	var bbar = personGrid.getTopToolbar();
	 bbar.insertButton(1,[
						
						new Ext.menu.Separator({cls:'xtb-sep'}),
						new Ext.Spacer({width:100}),
						new Ext.Button({
							icon : 'images/keyedit.gif',
							id:'photoMode',
						    text:'照片模式',
						    handler:datashow
						})
						]); 
	
	
});
function search(){
	radow.doEvent('mQueryonclick');
}
function loadadd(a,b,c,fxyp00){
	var newWin_ = $h.getTopParent().Ext.getCmp('cxtj');
	if(!newWin_){
		$h.openWin('cxtj','pages.fxyp.CXTJ','查询条件 ',1250,720,null,contextPath,null,
				{maximizable:false,resizable:false,closeAction:'hide',fxyp00:fxyp00||""});
	}else{
		newWin_.show(); 
		var subwindow = $h.getTopParent().document.getElementById("iframe_cxtj").contentWindow;
		subwindow.parent.Ext.getCmp(subwindow.subWinId).initialConfig.fxyp00=(fxyp00||"");
		subwindow.clearConbtn(true);
		subwindow.reloadtree();
	}
}

function selectRow(a,store){
	var peopleInfoGrid =Ext.getCmp('memberGrid');
	var len = peopleInfoGrid.getStore().data.length;
	if( len > 0 ){//默认选择第一条数据。
		var flag = true;
		for(var i=0;i<len;i++){
			var rc = peopleInfoGrid.getStore().getAt(i);
			if(rc.data.fxyp00==$('#fxyp00').val()){
				
				peopleInfoGrid.getSelectionModel().selectRow(i,true);
				peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
				flag= false;
				setTimeout(function(){peopleInfoGrid.getView().scroller.dom.scrollTop = peopleInfoGridscrollTop},100);
				return;
			}
		}
		peopleInfoGrid.getSelectionModel().selectRow(0,true);
		peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
	}
}
var peopleInfoGridscrollTop;
function infoSearch(fxyp00,refleshData){
	var peopleInfoGrid =Ext.getCmp('memberGrid');
	peopleInfoGridscrollTop = peopleInfoGrid.getView().scroller.dom.scrollTop;
	if(fxyp00){
		$('#fxyp00').val(fxyp00);
	}
	radow.doEvent('memberGrid.dogridquery');
	if(refleshData){
		radow.doEvent('mQueryonclick');
	}
}

function closeWinIfExists(a){
	 var newWin_ = $h.getTopParent().Ext.getCmp('cxtj');
	if(!newWin_){
	}else{
		
			newWin_.destroy();
	} 
	//window.close();
}

function deleteRow(){
	var fxyp00 = document.getElementById('fxyp00').value;
	var fxyp02 = document.getElementById('fxyp02').value;
	if(fxyp00==''){
		$h.alert('系统提示','请选择一行批次记录！');
		return;
	}
	$h.confirm("系统提示：",'确定删除岗位：'+fxyp02+"?",400,function(id) { 
		if("ok"==id){
			radow.doEvent('allDelete',fxyp00);
		}else{
			return false;
		}		
	});
}



function editorfxyp02(fxyp00,fxyp02){
	var msgTemplate =  "请输入岗位名称：";  
	Ext.MessageBox.prompt("输入框",msgTemplate,function(bu,txt){    
		 if(bu=="ok"&&txt!=''){
			 txt = txt.replace(/\r\n|\r|\n/g,"")
			 radow.doEvent("saveInfo",txt);
		 }
	        
	},this,true,fxyp02); 
	
	
}
function addGW(){
	var msgTemplate =  "请输入岗位名称：";  
	Ext.MessageBox.prompt("输入框",msgTemplate,function(bu,txt){    
		 if(bu=="ok"&&txt!=''){
			 txt = txt.replace(/\r\n|\r|\n/g,"")
			 radow.doEvent("addInfo",txt);
		 }
	        
	},this,true); 
	
	
}
</script>


