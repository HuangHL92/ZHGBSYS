<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit.jsp" %>

<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="mainPage/css/bootstrap-combined.min.css"> 
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="pages/mntpsj/resourse/mntpsjop.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
	
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<odin:hidden property="docpath" />
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
<link rel="stylesheet" type="text/css" href="pages/mntpsj/resourse/mntpsjop.css"> 
<style>

</style>


<div class="button-div">
	<button onclick="setLiuRen();return false;" class="button-tpyj button-xt">����</button>
	<button onclick="openQuXiangDanWei();return false;" class="button-tpyj button-xt">����</button>
	<button onclick="setMianZhi();return false;" style="" class="button-tpyj button-xt">��ְ</button>
</div>



<%-- <odin:hidden property="mntp05"/> --%>
<odin:hidden property="data"/>
<odin:hidden property="fabd00"/>
<odin:hidden property="fabd05"/>



<div style="width: 100%;height:40px;">

	<!-- <div style="width: 8%;height:40px;float: left;margin-bottom: 5px;margin-left: 30px;">
		<button type='button' class="btn  btn-primary" onclick="openMate()" style='margin-top:5px;'>��Ա�б�</button>
	</div> -->
	
	<div style="width: 25%;height:40px;float: left;padding-top: 5px;margin-left: 140px;">
		<table>
		  <tr>
			<odin:textEdit property="fabd02"  width="140"  />
			<td style="padding-left: 20px; ">&nbsp;</td>
			<odin:select2 property="mntp05" width="110" data="['2','������'],['1','��ֱ��λ'],['4','�����У']" />
			<td style="padding-left: 20px; "  >&nbsp;</td>
			<odin:select2 property="selorg"  width="110"  />
			<td style="padding-left: 20px; "  >&nbsp;</td>
			<odin:select2 property="selfamx00"  width="110"  />
		  </tr>
		</table>
	</div>
	
	<div style="width: 12%;height:40px;float: right;margin-bottom: 5px;">
		<button type='button' class="btn  btn-primary" onclick="infoSearch()" style='margin-top:5px;'>ˢ��ҳ��</button>
		<button type='button' class="btn   btn-danger" onclick="if(subWinId){parent.odin.ext.getCmp(subWinId).close();}else{window.close();}" style='margin-top:5px;margin-left: 5px;'>�ر�</button>
	</div>
	<div style="width: 8%;height:40px;float: right;margin-bottom: 5px;">
		<button type='button' class="btn  btn-primary DWSZ" onclick="openBDFAPage()" style='margin-top:5px;display: none;'>��λ����</button>
		<button type='button' class="btn  btn-primary XZGW" onclick="openXZGWPage()" style='margin-top:5px;display: none;'>ѡ���λ</button>
	</div>
	<div style="width: 8%;height:40px;float: right;margin-bottom: 5px;">
		<button type='button' class="btn  btn-primary" onclick="openBGWQP()" style='margin-top:5px;'>�˸�ά��</button>
	</div>
	
	<div style="width: 16%;height:40px;float: right;margin-bottom: 5px;">
		<button type='button' class="btn  btn-primary" onclick="rybd()" style='margin-top:5px;'>��Ա�ȶ�</button>
		<button type='button' class="btn  btn-primary" onclick="clearSelected()" style='margin-top:5px;'>���ѡ��</button>
		
	</div>
</div>
<div style="clear: both; text-align: center; width: 100%" ></div>
<div style="align:left top;overflow:auto;margin-left: 5px;"  id="selectable">
<div style="display: table; text-align: center;width: 100% " >

	<table id="coordTable" cellspacing="0" ondragover="event.preventDefault();" style="margin:auto"  >

		
	</table>
	
</div>
<div align="right">
					<div class="btn btn-primary" 
					 style="margin: 20px 20px 20px auto; " onclick="getTabArray()">����excel
					</div>
					</div>

</div>

<!-- ֱ���ڵ�λ�ϲ��� ��ť -->
<div  class="opt-btn-tpl" style="display: none;">
	<div class="opt-btn">
<%-- 		<button type='button' class="btn btn-primary btn-mini" onclick="openBGWQP($(this).parent().parent())" style='margin-top:5px;'>�˸�ά��</button>
 --%>		<button type='button' class="btn btn-primary btn-mini " onclick="openXZGWPage($(this).parent().parent())" 
 			style='margin-top:2px;width: 40px;line-height: 14px;padding: 4px 5px;'>ѡ���λ</button>
			<button type='button' class="btn btn-primary btn-mini" onclick="setOpenGWWin($(this).parent().parent())" 
			style='margin-top:2px;width: 40px;line-height: 14px;padding: 4px 5px;'>���Ӹ�λ</button>
			<%-- <button type='button' class="btn btn-primary btn-mini" onclick="openMate($(this).parent().parent())" 
			style='margin-top:2px;width: 40px;line-height: 14px;padding: 4px 5px;'>��Ա�б�</button> --%>
	</div>
</div>
<div  class="opt-btn-tpl2" style="display: none;">
	<div class="opt-btn">
			<button type='button' class="btn btn-primary btn-mini" onclick="openXZDW($(this).parent().parent())" 
			style='margin-top:2px;width: 40px;line-height: 14px;padding: 4px 5px;'>ѡ��λ</button>
			<button type='button' class="btn btn-primary btn-mini" onclick="openMate($(this).parent().parent())" 
			style='margin-top:2px;width: 40px;line-height: 14px;padding: 4px 5px;'>��Ա�б�</button>
			<button type='button' class="btn btn-primary btn-mini GQGX" onclick="addGBSCTJ($(this).parent().parent());$('.JGFX').fadeIn(300);" 
			style='margin-top:2px;width: 40px;line-height: 14px;padding: 4px 5px;'>�ṹ����</button>
			
			<button type='button' class="btn btn-primary btn-mini QXS" onclick="radow.doEvent('genSH','1@@'+$(this).parent().parent().attr('famx00'));" 
			style='margin-top:2px;width: 40px;line-height: 14px;padding: 4px 5px;display: none;'>���ɶ���</button>
			<button type='button' class="btn btn-primary btn-mini QXS" onclick="radow.doEvent('genSH','2@@'+$(this).parent().parent().attr('famx00'));" 
			style='margin-top:2px;width: 40px;line-height: 14px;padding: 4px 5px;display: none;'>�����ϻ�</button>
	</div>
</div>
<!-- <div  class="opt-btn-tpl3" style="display: none;">
	<div class="opt-btn">
			<button type='button' class="btn btn-primary btn-mini" onclick="openXZDW($(this).parent().parent())" 
			style='margin-top:2px;width: 40px;line-height: 14px;padding: 4px 5px;'>ѡ��λ</button>
	</div>
</div> -->

<div id="tdMove"></div>	
<div id="tdTipMove">
   <div class="view">
     <div class="alert alert-info" >
       <button type="button" class="close" onclick="$('#tdTipMove').hide()">x</button>
       <h4>������λ��ʾ��</h4>
       <div class="RenGangXX">
       	<p><strong>11</strong> <span>��ע����ĸ�����˽��ȫ.��ע����ĸ�����˽��ȫ.��ע����ĸ�����˽��ȫ.��ע����ĸ�����˽��ȫ. </span></p>
       </div>
     </div>
   </div>
</div>	
<!-- //�ṹ���� -->
<div class="JGFX">
	<div class="alert alert-success">
		 <button type="button" class="close" onclick="$('.JGFX').fadeOut(300)">��</button>
		<h4>
			�ṹ������
		</h4> 
		<div class="JGFXInfo">
       		<p><strong>11</strong> <span>��ע����ĸ�����˽��ȫ.��ע����ĸ�����˽��ȫ.��ע����ĸ�����˽��ȫ.��ע����ĸ�����˽��ȫ. </span></p>
       </div>
	</div>
</div>


<!-- ����λά�� -->
<div class="gwInfo" id="gwInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr>
			<odin:textEdit property="mntp_b01"  label="���䵥λ"  readonly="true"  />
		  </tr>
		  <tr>
		  	<odin:textEdit property="gwname" label="��λ����" ></odin:textEdit>
		  </tr>
		  <tr>
		  	<odin:select2 property="gwtype" label="��Ա���"  codeType="ZB129" />
		  <tr>
		  <tr>
		  	<tags:ComBoxWithTree property="bzgw" label="���Ӹ�λ"  codetype="KZ01" />
		  <tr>
		  <tr>
		  	<odin:select2 property="gwmc" label="�ص��λ"  />
		  <tr>
		</table>
		<odin:hidden property="famx00"/>
		<odin:hidden property="b0111gw"/>
		<div style="width: 100%;text-align: center;margin-top: 20px;">
			<button type='button' class="btn btn-primary" onclick="addGWInfo()"  >�����λ</button>
		</div>
	</div>
</div>


<script type="text/javascript">
var g_contextpath = '<%=request.getContextPath()%>';
function setTableWidth(w){
	if(w){
		$('#coordTable').width(w+"%");
	}
	$('.titleColor').css('background-color','rgb(230 228 228)');
	$('.name').unbind('click').bind('click',function(e){
		var tgt = e.target;
		var a0000=$(this).attr("a0000")
		if(a0000==null || a0000==''||tgt.tagName=='A'||tgt.tagName=='INPUT'){
			return;
		}
	  //$h.openPageModeWin('openTPRmb','pages.fxyp.TPRYXXZS','��Ա��Ϣ',1000,800,{a0000:a0000,location:'1'},contextPath); 
		openRMB(a0000)
	});
	
	//��λ��ע
	radow.doEvent('showOrgInfo');
	
	//�����λ
	$('.gwtj').unbind('click').bind('click',function(e){
		var fxyp00=$(this).attr("fxyp00");
		
		if(fxyp00==null || fxyp00==''){
			return;
		}
		//��ǰ�棬��û����ɾ����λҲ����ˢ��
		showDataSyn.clearParm();
	    var synparm = getDWTRs($(this).parent());
	    showDataSyn.putParm(synparm);
	    var zwqc00 = $(this).attr("zwqc00");
	    if(zwqc00){//������λ
	    	$.each($('td[zwqc00="'+zwqc00+'"]'), function (i2,rowsData2) {
				synparm = getDWTRs($(rowsData2).parent());
			    showDataSyn.putParm(synparm);
			});
	    }
	    
		var tgt = e.target;
		if(tgt.tagName=='A'||tgt.tagName=='INPUT'||tgt.tagName=='IMG'){
			return;
		}
		
		
	    //�󶨸�λ����id
	    if($(this).attr("gwmc")){
	    	radow.doEvent('setGWSQL',fxyp00);
	    	
	    }else{
	    	openRenXuanTiaoJian(fxyp00);
	    }
	    
	});


	  
	  
	  //�����λ
	  $('.unit').unbind('click').bind('click',function(e){
		  if(!$(this).attr('famx00')){
			  return
		  }
			 var b0111=$(this).attr("qx");
			 var b0101=$(this).text();
			 if(b0111==null || b0111==''){
				 return;
			 }
			 var tgt = e.target;
			if(tgt.tagName=='DIV'||tgt.tagName=='BUTTON'){
				return;
			}
			 <%-- var ip='<%=GlobalNames.sysConfig.get("XBDJ_IP")%>';
			 var port='<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>';
			 var url = "http://"+ip+":"+port+"/ngbdp/team/?code="+b0111+"&name="+b0101+"&hasback=false"; 
			$h.showWindowWithSrc("BZFX",url,"���ӷ���", 1500, 1200,null,{closeAction:'close'},true,true); --%>
			openMate($(this))
	}); 
	  
	var mntp05 = $('#mntp05').val();
	var fabd05 = $('#fabd05').val();  
	//������� ��ť
	//if("2"==fabd05){
		var optbtnhtml = $('.opt-btn-tpl').html();
		$('.unit[famx01="2"]').each(function(i,org){
			$(this).append($(optbtnhtml));
		});
		optbtnhtml = $('.opt-btn-tpl2').html();
		$('.tableTile[famx01="2"]').each(function(i,org){
			$(this).append($(optbtnhtml).show());
		});
		/* optbtnhtml = $('.opt-btn-tpl3').html();
		$('.tableTile[famx01="1"]').each(function(i,org){
			$(this).append($(optbtnhtml).show());
		}); */
		
		
		$('.unit[famx01="2"]').unbind('mouseenter').mouseenter(function() {
			$('.opt-btn',this).show();
			
		}).unbind('mouseleave').mouseleave(function() {
			$('.opt-btn',this).hide();
		});
	//}
	
	
	  
/************��λ����ɲ�����������ͳ��******************************************************************/
	
	if("4"==mntp05&&"1"==fabd05){
		$('.GQGX').show();
		//�ɲ���������ͳ��
		//addGBSCTJ();
	}else{
		$('.GQGX').hide();
	}
	
/************��ѡ����******************************************************************/
	renxuanpaixu();
	
}

</script>
<odin:hidden property="tpyjInfoJSON"/>

<odin:hidden property="a0000s"/>
<odin:hidden property="a0000rybd"/>
<odin:hidden property="a0200s"/>
<!-- ������ְ�� -->
<odin:hidden property="b0111"/>
<!-- ��Ա���� -->
<odin:hidden property="fxyp00"/>

<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<odin:hidden property="rmbs"/>

<script type="text/javascript">
var myMask;
<%
String fabd00 = request.getParameter("fabd00");
String famx00 = request.getParameter("famx00");
String b0111 = request.getParameter("b0111");
String type = request.getParameter("type");

%>
Ext.onReady(function() {
	
	myMask = new Ext.LoadMask(Ext.getBody(),{msg:"loading..."});
	
	
	closeWin();
	
	if(""!='<%=fabd00==null?"":fabd00%>'){
		$('#fabd00').val('<%=fabd00%>');
		$('#selfamx00').val('<%=famx00%>');
		$('#selorg').val('<%=b0111%>');
		if("<%=type==null?"":type%>"=="add"){
			openBDFAPage();
		}
	}else{
		if(parentParams.fabd00){
			$('#fabd00').val(parentParams.fabd00);
		}
		if(parentParams.famx00){
			$('#selfamx00').val(parentParams.famx00);
		}
		if(parentParams.b0111){
			$('#selorg').val(parentParams.b0111);
		}
	}
	
	
	
	var viewSize = Ext.getBody().getViewSize();
	var height=viewSize.height;
	$("#selectable").css('height',height-60);
	$("#selectable").css('width',viewSize.width-10);
	
	Ext.getCmp('fabd02').on('blur',function(){
		 if(this.getValue()==''){
			 return;
		 }
		radow.doEvent('saveFabd02')
	});
	Ext.getCmp('mntp05_combo').on('select',function(){
		if(this.getValue()==''){
			return;
		}
		radow.doEvent('saveFabd02');
		if("2"==$('#mntp05').val()&&"1"==$('#fabd05').val()){
			Ext.getCmp('selorg_combo').show();
			//$('.QXS').show();
		}else{
			odin.setSelectValue('selorg','');
			Ext.getCmp('selorg_combo').hide();
			//$('.QXS').hide();
		}
		
		infoSearch();
	});
	
	Ext.getCmp('selorg_combo').on('select',function(){
		if(this.getValue()==''){
			return;
		}
		infoSearch();
	});
	Ext.getCmp('selfamx00_combo').on('select',function(){
		if(this.getValue()==''){
			return;
		}
		infoSearch();
	});
	
	
	Ext.getCmp('mntp05_combo').hide();
	Ext.getCmp('selorg_combo').hide();
	Ext.getCmp('selfamx00_combo').hide();
	
	
	var win = openGWWin(false);
	
	win.hide();
	
});



</script>

