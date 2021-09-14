<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>

<style>
   
#etcInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
    
    
</style>

<odin:toolBar property="toolBar" applyTo="toolbardiv">
    <odin:fill/>
    <odin:buttonForToolBar text="增加" id="AddBtn" icon="images/add.gif"></odin:buttonForToolBar>
    <odin:buttonForToolBar text="保存"  icon="images/save.gif" isLast="true" id="save"/>
</odin:toolBar>
<odin:toolBar property="ZWtoolBar" applyTo="ZWtoolbardiv">
    <odin:fill/>
    <odin:buttonForToolBar text="保存"  icon="images/save.gif" isLast="true" id="saveZWMX"/>
</odin:toolBar>

<div id="content">
    
<table>
  <tr>
    <td valign="top">
    	<odin:groupBox property="gb1" title="选择职务">
    	<table>
    		<tr>
    			<td colspan="4" style="padding-bottom: 20px"><div id="toolbardiv" ></div></td>
    		</tr>
    		<tr>
    			<tags:PublicTextIconEdit3 codetype="orgTreeJsonData" onchange="setZWInfo" label="选择任职机构" property="a0201bSeclect" defaultValue=""  required="true" readonly="true"/>
				<odin:select2 property="a0192aSeclect" width="180" label="工作单位及职务"  />
    		</tr>
    		<tr>
				<odin:NewDateEditTag property="a0243Seclect" labelSpanId="a0243SpanId" maxlength="8" label="任职时间" required="true" />
    		</tr>
    		<tr>
    			<odin:textEdit property="a0192aQT"  label="其他职务" width="430"  colspan="4"/>
    		</tr>
    		<tr>
    			<tags:PublicTextIconEdit property="a0192e" codetype="ZB148" width="160" label="现职级"  readonly="true"/>
				<odin:NewDateEditTag property="a0192c" isCheck="true" label="任现职级时间" maxlength="8" ></odin:NewDateEditTag>
			</tr>
        </table>
    	</odin:groupBox>
    	
    </td>
    <td style="width: 60%;padding-top: 9px;" >
	    <odin:editgrid property="memberGrid" hasRightMenu="true"  autoFill="true" load="selectRow2" forceNoScroll="true" height="160" pageSize="200" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="a0000" />
				<odin:gridDataCol name="a0200" />
				<odin:gridDataCol name="a0201a" />
				<odin:gridDataCol name="a0201b" />
				<odin:gridDataCol name="a0201d" />
				<odin:gridDataCol name="a0201e" />
				<odin:gridDataCol name="a0215a" />
				<odin:gridDataCol name="a0223" />
				<odin:gridDataCol name="a0225" />
				<odin:gridDataCol name="a0243" />
				<odin:gridDataCol name="a0245"/>
				<odin:gridDataCol name="a0251b"/>
				<odin:gridDataCol name="a0255"/>
				<odin:gridDataCol name="a0281"/>
				<odin:gridDataCol name="a0256a"/>
				<odin:gridDataCol name="a0256"/>
				<odin:gridDataCol name="a0229"/>
				<odin:gridDataCol name="a0201c"/>
				<odin:gridDataCol name="a0279"/>
				<odin:gridDataCol name="a0283g"/>
				<odin:gridDataCol name="a0248"/>
				<odin:gridDataCol name="a0247" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridEditColumn2 dataIndex="a0201a" width="90" header="任职机构名称" align="center"  edited="false"  editor="text" />
				<odin:gridEditColumn2 dataIndex="a0215a" width="140" header="职务名称" align="center" edited="false"  editor="text"/>
				<odin:gridEditColumn2 dataIndex="a0201d"  header="班子成员" selectData="['1','是'],['0','否']"  align="center"  editor="select"  edited="false"  editor="select"/>
				<odin:gridEditColumn2 dataIndex="a0201e"  header="成员类别" codeType="ZB129" editor="select" edited="false"  align="center"/>
				<odin:gridEditColumn2 dataIndex="a0279"  header="主职务标识" hidden="true" edited="false"  editor="text" />
				<odin:gridEditColumn2 dataIndex="a0248"  header="是否占职数" selectData="['1','不占职数'],['0','占职数'],['','占职数'],[null,'占职数']"  align="center"  edited="false"  editor="select"/>
				<odin:gridEditColumn2 dataIndex="zwcode"  header="职务层次代码" hidden="true" edited="false"  editor="text" isLast="true" />
				<%-- <odin:gridColumn dataIndex="ld00" width="80" header="操作" align="center" isLast="true" renderer="deleteRowRenderer" /> --%>
			</odin:gridColumnModel>
		</odin:editgrid>
		<odin:groupBox property="gb2" title="职务详细"  >
    	<table >
    		<tr>
    			<td colspan="4" style="padding-bottom: 20px"><div id="ZWtoolbardiv" ></div></td>
    		</tr>
    		<tr>
    			<odin:textarea property="a0192aText" cols="100" label="全称" rows="4" colspan="4"/>
    		</tr>
    		
    		
    		<tr>
   				<odin:textEdit property="a0201a" label="任职机构名称" readonly="true"></odin:textEdit>
   				
			    <td  colspan="2">
					<input type="radio" name="a0255" id="a02551" checked="checked" value="1" class="radioItem" />
					<label for="a0255" style="font-size: 12px;">在任</label>
					<odin:hidden property="a0200"/>
				</td>
			</tr>
			<tr>
				<odin:textEdit property="a0215a" label="职务名称"  readonly="true" maxlength="100"></odin:textEdit>
				
				<td colspan="2">
					<input type="checkbox" name="a0279" id="a0279"  onclick="return false;" />
					<label id="a0279SpanId" for="a0279" style="font-size: 12px;">优先排序</label>
				</td>
			</tr>
			<tr align="left">
			    <odin:select2 property="a0201e" label="成员类别" readonly="true" codeType="ZB129"></odin:select2>
			    <td  colspan="2">
					<input type="checkbox" name="a0201d" id="a0201d" onclick="return false;"/>
					<label id="a0201dSpanId" for="a0201d" style="font-size: 12px;">领导成员</label>
					<input type="checkbox" name="a0248" id="a0248" onclick="return false;"/>
					<label id="a0248SpanId" for="a0248" style="font-size: 12px;">不占职数</label>
				</td>
			</tr>
			<tr>
				<tags:PublicTextIconEdit3 property="a0247" label="选拔任用方式" codetype="ZB122" readonly="true" />
				<td colspan="2">
					<input type="checkbox" name="a0251b" id="a0251b"/>
					<label id="a0251bfSpanId" for="a0251b" style="font-size: 12px;">破格提拔</label>
				</td>
			</tr>
			<tr>
				<odin:NewDateEditTag property="a0243" labelSpanId="a0243SpanId" maxlength="8" label="任职时间" ></odin:NewDateEditTag>
				<odin:textEdit property="a0245" label="任职文号" ></odin:textEdit>
			</tr>
			<tr>
				<odin:textEdit property="a0283g" width="490" label="名册名称"  maxlength="1000"  colspan="4"/>
			</tr>
			<tr>
				<odin:textarea  property="a0229" label="分管工作" colspan='4' rows="3" ></odin:textarea>
			</tr>
			<tr>
				<odin:textEdit property="a0256" label="分管单位" colspan='2'></odin:textEdit>
				<odin:textEdit property="a0256a" label="备注" colspan='2'></odin:textEdit>
			</tr>
        </table>
    	</odin:groupBox>
    </td>
  </tr>
</table>

               
	    <odin:editgrid property="memberGridMain" hasRightMenu="true" load="selectRow" autoFill="true" width="590" height="200" pageSize="200" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="zwzc00" />
				<odin:gridDataCol name="a0192a" />
				<odin:gridDataCol name="a0165"/>
				<odin:gridDataCol name="a0221"/>
				<odin:gridDataCol name="a0192e"/>
				<odin:gridDataCol name="a0192c"/>
				<odin:gridDataCol name="a0243seclect"/>
				<odin:gridDataCol name="jzaz"/>
				<odin:gridDataCol name="zwtype"/>
				<odin:gridDataCol name="b01id" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridColumn dataIndex="a0192a"  header="职务名称" align="center"   />
				<odin:gridColumn dataIndex="a0165"  header="管理类别" align="center"  renderer="gllbM" />
				<odin:gridColumn dataIndex="a0221"  header="职务层次" align="center" codeType="ZB09" editor="select"  align="center" />
				<odin:gridColumn dataIndex="jzaz"  header="军转安置" align="center" selectData="['1','是'],['0','否']" editor="select"/>
				<odin:gridColumn dataIndex="b01id"  header="所属机构id" isLast="true" align="center" />
				<%-- <odin:gridColumn dataIndex="zzs00"  header="操作" align="center" isLast="true" renderer="deleteRowRendererMain"/> --%>
			</odin:gridColumnModel>
		</odin:editgrid>
    
    
</div>



<odin:hidden property="a0000"/>
<odin:hidden property="zwzc00"/>
<odin:hidden property="zwtype"/>



<script>
<%
String RrmbCodeType = (String)session.getAttribute("RrmbCodeType");
//RrmbCodeType = CodeType2js.getRrmbCodeType();

%>
<%=RrmbCodeType%>
function gllbM(value) {
	var returnV="";
	if(value){
		var v = value.split(",");
		for(i=0;i<v.length;i++){
			if(CodeTypeJson.ZB130[v[i]]){
				returnV += CodeTypeJson.ZB130[v[i]]+","
			}
		}
		returnV = returnV.substring(0,returnV.length-1);
	}
	
	return returnV;
	
}


    Ext.onReady(function () {
        if(window.dialogArguments.wincfg.a0000){
            document.getElementById("a0000").value=window.dialogArguments.wincfg.a0000;
           
        }
        
        
        Ext.getCmp('memberGrid').setWidth(650);
        Ext.getCmp('memberGrid').on('rowclick',function(gridobj,index,e){
    		var rc = gridobj.getStore().getAt(index);
    		$('#a0201a').val(rc.data.a0201a);
    		$('#a0215a').val(rc.data.a0215a);
    		if(rc.data.a0279=='1'){
    			$("#a0279").attr("checked", true);
    		}else{
    			$("#a0279").attr("checked", false);
    		}
    		
    		odin.setSelectValue('a0201e',rc.data.a0201e);
    		
    		if(rc.data.a0201d=='1'){
    			$("#a0201d").attr("checked", true);
    		}else{
    			$("#a0201d").attr("checked", false);
    		}
    		
    		if(rc.data.a0248=='1'){
    			$("#a0248").attr("checked", true);
    		}else{
    			$("#a0248").attr("checked", false);
    		}
    		
    		odin.setSelectValue('a0247',rc.data.a0247);
    		
    		if(rc.data.a0251b=='1'){
    			$("#a0251b").attr("checked", true);
    		}else{
    			$("#a0251b").attr("checked", false);
    		}
    		
    		
    		$('#a0243').val(rc.data.a0243);
    		$('#a0243_1').val(rc.data.a0243).focus().blur();
    		
    		$('#a0245').val(rc.data.a0245);
    		$('#a0283g').val(rc.data.a0283g);
    		$('#a0229').val(rc.data.a0229);
    		$('#a0256').val(rc.data.a0256);
    		$('#a0256a').val(rc.data.a0256a);
    		$('#a0200').val(rc.data.a0200);
    		
    	});
        
        
        Ext.getCmp('a0192aSeclect_combo').on('change',a0192aChange)

    })
   
    
    
    
    
    
    
    function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
    	var ld00 = record.data.ld00;
    	return "<a href=\"javascript:deleteRow2(&quot;"+ld00+"&quot;)\">删除</a>";
    	
    }
    function deleteRow2(ld00){ 
    	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
    		if("yes"==id){
    			radow.doEvent('deleteRow',ld00);
    		}else{

    		}		
    	});	
    }
    function deleteRowRendererMain(value, params, record,rowIndex,colIndex,ds){
    	var zzs00 = record.data.zzs00;
    	return "<a href=\"javascript:deleteRowMain(&quot;"+zzs00+"&quot;)\">删除</a>";
    	
    }
    function deleteRowMain(zzs00){ 
    	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
    		if("yes"==id){
    			radow.doEvent('deleteRowMain',zzs00);
    		}else{

    		}		
    	});	
    }
    
    
    
    
    
    function selectRow(a,store){
    	var peopleInfoGrid =Ext.getCmp('memberGridMain');
    	var len = peopleInfoGrid.getStore().data.length;
    	if( len > 0 ){//默认选择第一条数据。
    		
    		peopleInfoGrid.getSelectionModel().selectRow(0,true);
    		peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
    	}
    }
    function selectRow2(a,store){
    	var peopleInfoGrid =Ext.getCmp('memberGrid');
    	var len = peopleInfoGrid.getStore().data.length;
    	if( len > 0 ){//默认选择第一条数据。
    		
    		peopleInfoGrid.getSelectionModel().selectRow(0,true);
    		peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
    	}
    }
    
    
    
    
    function setZWInfo(record){
    	
    	//任职结构类别 和 职务名称代码对应关系
    	radow.doEvent('setZWInfo',record.data.key);

    }
    
    function a0192aChange(a){
    	var v = document.getElementById('a0192aSeclect_combo').value;
    	//alert(v)
    	if(v==''){
    		Ext.getCmp('a0192aQT').setDisabled(false);
    		$('#a0192aQT').attr('readOnly',false);
    		selecteWinEnable('a0192e');
    		
    		Ext.getCmp('a0192c_1').setDisabled(false);
    		$('#a0192c_1').attr('readOnly',false);
    	}else{
    		Ext.getCmp('a0192aQT').setValue('').setDisabled(true);
    		$('#a0192aQT').attr('readOnly',true);
    		selecteWinDisable('a0192e');
    		
    		
    		Ext.getCmp('a0192c_1').setDisabled(true);
    		$('#a0192c_1').val('').attr('readOnly',true);
    		$('#a0192c').val('')
    	}
    }
    
    
    function selecteWinDisable(selectId) {
    	var combo = Ext.getCmp(selectId+'_combo');//
   		combo.setValue('');
   		document.getElementById(selectId).value='';
   		combo.disable();
   		var img = Ext.query("#"+selectId+"_combo+img")[0];
   	    img.onclick=null;
    }
    
    function selecteWinEnable(selectId) {
    	var combo = Ext.getCmp(selectId+'_combo');//
    	if($('#'+selectId+'_combo.bgclor').length==0){
    		combo.enable();
    		var img = Ext.query("#"+selectId+"_combo+img")[0];
    		img.onclick=eval('openDiseaseInfoCommonQuery'+selectId);
    	}
    }
</script>