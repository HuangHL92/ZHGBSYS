<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>

<style>
   
#etcInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
/* #content   input[type=radio]{
width:100px;
}  */
#text1 textarea{
	width:400px;
	height:40px
} 
.title{
	font-weight:700;
	color:#FF0000
}
</style>

<odin:toolBar property="toolBar">
    <odin:fill/>
    <odin:buttonForToolBar text="保存" id="save" isLast="true"/>
</odin:toolBar>

<div id="content">
    <odin:groupBox title="纪实明细信息" property="groupBox">
        <table 	>
	        <tr align="center">
		    	<td colspan='8'  align="center">
		    	<label><input type="radio" id='r1' name="radio_1" value="1" onclick="updateMC()" >选择人员</label>
		    	<label><input type="radio"  name="radio_1" value="2" onclick="updateMC()" >手动添加人员</label>
		    	<td>
			</tr>
            <tr id='tr1'>
                <odin:textEdit property="a0101_1" label="姓名" readonly="true" width="72"  onclick="openSelectPersonWin()"/>
                <odin:NewDateEditTag property="a0107_1" label="出生年月"  readonly="true" width="72"  ></odin:NewDateEditTag>
                <odin:textEdit property="a0192a_1" label="任现职务" readonly="true" width="210" />
                <odin:NewDateEditTag property="a0192f_1" label="任现职时间"  readonly="true" width="72" />
            </tr>
            <tr id='tr2'>
                <odin:textEdit property="a0101_2" label="姓名" width="72"/>
                <odin:NewDateEditTag property="a0107_2" label="出生年月" width="72"  />
                <odin:textEdit property="a0192a_2" label="任现职务" width="210"/>
                <odin:NewDateEditTag property="a0192f_2" label="任现职时间" width="72"/>
            </tr>
<%--             <tr id="text1">
            	<odin:textarea property="srzw" label="时任职务" colspan='4'/>
                <odin:textarea property="js01" label="破格提拔理由<br/>（如属破格提拔）" colspan='4'/>
            </tr>
            <tr>
                <td  class='title' colspan='8' align="center">民主推荐（会议推荐）：</td>
            </tr>
            <tr>
                <odin:textEdit property="js02a" label="得票数" width="72"/>
                <odin:textEdit property="js02b" label="总人数" width="72"/>
                <odin:textEdit property="js02c" label="名次" width="72"/>
            </tr>
            <tr>
                <td  class='title' colspan='8' align="center">民主推荐（谈话推荐）：</td>
            </tr>
            <tr>
                <odin:textEdit property="js03a" label="得票数" width="72"/>
                <odin:textEdit property="js03b" label="总人数" width="72"/>
                <odin:textEdit property="js03c" label="名次" width="72"/> 
            </tr>
            <tr>
                <td  class='title' colspan='8' align="center">考察情况：</td>
            </tr>
            <tr>
            	<td  colspan="2"  align="center"><label style="FONT-SIZE: 12px">是否进行考察</label>
				<input name="js04a" id="js04a" type="checkbox" value="1" /> </td>
				<td width="80"></td>
                <td  colspan="2"  align="center"><label style="FONT-SIZE: 12px;">有无不得列为考察对象的情形
				<input name="js04b" id="js04b" type="checkbox" value="1" /></label></td>
            </tr>
            <tr>
                <td  class='title' colspan='8' align="center">党组（党委）讨论</td>
            </tr>
            <tr>
                <odin:textEdit property="js05a" label="应参会人数" width="72"/>
                <odin:textEdit property="js05b" label="实际参会人数" width="72"/>
                <odin:textEdit property="js05c" label="参会同意人数" width="72"/> 
            </tr>
            <tr>
            	<td  colspan="4"  align="center"><label style="FONT-SIZE: 12px">任职公示没有反映</label>
				<input name="js06a" id="js04a" type="checkbox" value="1" /> </td>
				<td width="80"></td>
                <td  colspan="4"  align="center"><label style="FONT-SIZE: 12px">任职公示有反映但不影响使用</label>
				<input name="js06b" id="js04b" type="checkbox" value="1" /> </td>
            </tr>
            <tr>
            	<td  colspan="4"  align="center"><label style="FONT-SIZE: 12px">是否征求纪检监察机关意见</label>
				<input name="js07" id="js04a" type="checkbox" value="1" /> </td>
				<td width="80"></td>
                <td  colspan="4"  align="center"><label style="FONT-SIZE: 12px">个人有关事项报告查核是否一致</label>
				<input name="js08" id="js04b" type="checkbox" value="1" /> </td>
            </tr>
            <tr>
            	<td  colspan="4"  align="center"><label style="FONT-SIZE: 12px">干部档案是否进行审核</label>
				<input name="js09a" id="js04a" type="checkbox" value="1" /> </td>
				<td width="120"></td>
                <td  colspan="4"  align="center"><label style="FONT-SIZE: 12px">干部档案“三龄两历一身份”是否存在问题</label>
				<input name="js09b" id="js04b" type="checkbox" value="1" /> </td>
            </tr>
            <tr >
                <odin:NewDateEditTag property="js10" label="会议推荐时间" width="72"  />
                <odin:NewDateEditTag property="js11" label="谈话推荐时间" width="72"/>
                <odin:NewDateEditTag property="js12" label="考察谈话时间" width="72"  />
                <odin:NewDateEditTag property="js13" label="征求纪检监察意见时间" width="72"/>
            </tr>
            <tr >
                <odin:NewDateEditTag property="js14" label="纪检监察部门回复时间" width="72"  />
                <odin:NewDateEditTag property="js15" label="个人事项查询委托时间" width="72"/>
                <odin:NewDateEditTag property="js16" label="个人事项查核处理时间" width="72"  />
                <odin:NewDateEditTag property="js17" label="三龄二历一身份核定时间" width="72"/>
            </tr>
            <tr >
                <odin:NewDateEditTag property="js18" label="党组会讨论时间" width="72"  />
                <odin:NewDateEditTag property="js19a" label="任前公示时间发布公示通知时间" width="72"/>
                <odin:textEdit property="js19b" label="公示期限" width="144"  />
            </tr>
            <tr >
                <odin:NewDateEditTag property="js20" label="发文时间（落款时间）" width="72"  />
                <odin:textEdit property="js21" label="责任处室" width="72"/>
                <odin:select2  property="js22" label="提拔类型"  data="['1','提拔'],['2','转重'],['3','纪委副局长级转出'],['4','晋升巡视员、副巡视员'],['5','副局级组织员'],['6','晋升职级公务员']" ></odin:select2>
            </tr> --%>
<%--             <tr>
                <odin:select2  property="rank" label="级别"  data="['1', '中央'],['2', '省'],['3', '市'],['4', '区县市']" ></odin:select2>
               	<odin:select2  property="dbwy" label="代表委员"  data="['1', '市委委员'],['2', '人大常委'],['3', '人大代表'],['4', '政协委员'],['5', '党代表'],['6', '纪委委员']" ></odin:select2>
				
            </tr>
            <tr>
                <odin:NewDateEditTag property="rzsj" isCheck="true" label="任职时间"/>
                <odin:NewDateEditTag property="mzsj" isCheck="true" label="免职时间"/>
            </tr> --%>
        </table>
    </odin:groupBox>
		
</div>
<odin:panel property="panel" contentEl="content" topBarId="toolBar"/>

<odin:hidden property="type"/>
<odin:hidden property="js00"/>
<odin:hidden property="personId"/>
<odin:hidden property="ssid"/>
<script>
Ext.onReady(function () {
    //var data = parent.Ext.getCmp(subWinId).initialConfig.data
    
    if(parentParams.jgId){
        document.getElementById("js00").value=parentParams.js00;
    }
    
    document.getElementById("r1").checked='true';
	document.getElementById("tr2").style.display="none";
	document.getElementById("type").value='1';
    

})
function openSelectPersonWin() {
    $h.openWin('selectPerson', 'pages.gzgl.CjqkAddSelect', '选择人员 ', 1200, 610, null, contextPath, null, {
        maximizable: false,
        resizable: false,
        closeAction: 'close'
    })
}

function callback(param) {
    document.getElementById("personId").value = param
    // 关闭选择人员窗口
    $h.getTopParent().Ext.getCmp('selectPerson').close()
    // 更新人员信息
     radow.doEvent('freshInfo') 
}

function updateMC(){
	document.getElementById("personId").value="";
	document.getElementById("a0101_1").value="";
	document.getElementById("a0107_1").value="";
	document.getElementById("a0107_1_1").value="";
	document.getElementById("a0192a_1").value="";
	document.getElementById("a0192f_1").value="";
	document.getElementById("a0192f_1_1").value="";
	document.getElementById("a0101_2").value="";
	document.getElementById("a0107_2").value="";
	document.getElementById("a0107_2_1").value="";
	document.getElementById("a0192a_2").value="";
	document.getElementById("a0192f_2").value="";
	document.getElementById("a0192f_2_1").value="";
	var r1=$("input[name='radio_1']:checked").val();
	if(r1=='1'){
		document.getElementById("type").value="1";
		document.getElementById("tr1").style.display="";
		document.getElementById("tr2").style.display="none";
	}else if(r1=='2'){
		document.getElementById("type").value="2";
		document.getElementById("tr1").style.display="none";
		document.getElementById("tr2").style.display="";
	}
}


    


</script>