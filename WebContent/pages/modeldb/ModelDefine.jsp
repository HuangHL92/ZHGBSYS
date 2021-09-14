<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js"></script>
<odin:hidden property="sub_libraries_model_id"/>
<odin:hidden property="editer"/>
<odin:toolBar property="btnToolBar" >
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave"  text="定义保存"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
</odin:toolBar>
<odin:panel contentEl="bar_div" property="panel1" topBarId="btnToolBar" collapsible="true"></odin:panel>
<div id="bar_div" >
<div id="one">
	<table  border="0">
	<tr>
		<odin:select2 property="information_set_" label="请选择信息集" codeType="VSL003" multiSelect="true" size="100"/>
		<td>
			<odin:button property="query" text="查询"/>
		</td>
	</tr>
  </table>
</div>
<div id="tow">
  <table>
  <tr>
  	<td>	
		<table  border="0">
		    <tr>
		       <odin:select2 property="brackets" label="括弧" />	
			   <odin:select2 property="information_set" label="表" required="true" codeType="VSL003"/>
			   <odin:select2 property="information_set_field" label="列" required="true" codeType="VSL004"/>
			</tr>
			<tr>
			   <odin:select2 property="condition_operator" label="关系" codeType="VSL006" required="true"/>
			   <td colspan="2"><table>
			   		<tr id="div_text">
			   			<odin:textEdit property="field_value_text" label="值" />
			   		</tr>
			   		<tr id="div_select">
			   			<odin:select2 property="field_value_select" label="值" />
			   		</tr>
			   		<tr id="div_date">
			   			<odin:dateEdit property="field_value_date" label="值" format="Ymd"/>
			   		</tr>
			   		<tr id="div_number">
			   			<odin:numberEdit property="field_value_number" label="值" />
			   		</tr>
				
			   </table>
			   </td>
			   <odin:select2 property="condition_connector" label="且或" data="['and','and'],['or','or']" />	
			</tr>
	    </table>
  	</td>
    <td colspan="1" width="20">
        <hr style="width: 1px; height: 50px;"></hr>
    </td>
    <td colspan="1">
        <table cellpadding="0" cellspacing="0"  border="0">
          <tr>
            <td colspan="1" align="right"><odin:button property="add" text="添加" /></td>
			<td colspan="1" align="left"><odin:button property="remove" text="移除"/></td>
          </tr>
        </table>
     </td>
   </tr>
  </table>
</div>


<odin:tab id="tab" width="100%" height='500' >
  <odin:tabModel >
    <odin:tabItem title="查询列定义" id="tab1" />
    <odin:tabItem title="查询条件定义" id="tab2"/>
    <odin:tabItem title="SQL检测" id="tab3" isLast="true" />
  </odin:tabModel>
  <odin:tabCont itemIndex="tab1">
    <table width=100% border="0">
		<tr>
			<td valign="top" width="100%">
			<odin:groupBoxNew
					collapsible="true" contentEl="one" title="" property="gbn1" /></td>
		</tr>
	</table>
    <table width="100%" border="0">
      <tr>
        <td valign="top">
          <div id="gridDiv_columnsdefine"></div>
        </td>
      </tr>
    </table>
  </odin:tabCont>
  <odin:tabCont itemIndex="tab2">
    <table width=100% border="0">
		<tr>
			<td valign="top" width="100%">
				<odin:groupBoxNew
						collapsible="true" contentEl="tow" title="" property="gbn2" />
			</td>
		</tr>
	</table> 
    <table width="100%" border="0">
      <tr>
        <td valign="top">
          <div id="gridDiv_conditionsdefine"></div>
        </td>
      </tr>
    </table>
  </odin:tabCont>
  <odin:tabCont itemIndex="tab3" >
    <table width=100% border="0">
		<tr>
			<td valign="top" width="100%">
				<div id="three">
				  <table  border="0">
				  	<tr>
					   <odin:textarea property="sqltext" label="SQL" colspan="4" cols="120" rows="10" ></odin:textarea>
					   <td>
				  	   		<odin:button text="检测" property="test"></odin:button>
				  	   </td>
				  	</tr>
				  	<tr>
				  	   <odin:textarea property="ora_err" label="SQLErr" colspan="3" cols="120" rows="5"></odin:textarea>
				  	</tr>
				  </table>
				</div>			
			</td>
		</tr>
	</table> 
  </odin:tabCont>
</odin:tab>

<odin:editgrid property="columnsdefine" title=""  applyTo="gridDiv_columnsdefine" autoFill="false" pageSize="200" isFirstLoadData="false" url="/" height="400" width="800" collapsible="true" sm="cell">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="information_set" />
		<odin:gridDataCol name="information_set_comment" />
		<odin:gridDataCol name="information_set_field" />
		<odin:gridDataCol name="information_set_field_comment" />
		<odin:gridDataCol name="columns_no" type="int"/>
		<odin:gridDataCol name="queryitem" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn width="60"></odin:gridRowNumColumn >
		<odin:gridColumn dataIndex="information_set"  header="信息集ID"  width="150" align="left" editor="text"  edited="false" hidden="true" />
		<odin:gridColumn dataIndex="information_set_comment"  header="信息集名称"  width="150" align="left" editor="text"  edited="false"/>
		<odin:gridColumn dataIndex="information_set_field" header="信息ID" width="350" align="left" editor="text"  edited="false"  hidden="true"/>
		<odin:gridColumn dataIndex="information_set_field_comment" header="信息项名称" width="350" align="left" editor="text"  edited="false" />
		<odin:gridColumn dataIndex="columns_no" header="查询头序号"  	width="80" align="center" editor="number" edited="true"/>
	    <odin:gridColumn dataIndex="queryitem" header="selectall"  gridName="columnsdefine" align="center" width="80" editor="checkbox" edited="true" align="center"   isLast="true"/>
	</odin:gridColumnModel>
	<odin:gridJsonData>{data:[]}</odin:gridJsonData>
</odin:editgrid>


<odin:editgrid property="conditionsdefine" title=""   applyTo="gridDiv_conditionsdefine" topBarId="myToolBar" autoFill="false" pageSize="200" isFirstLoadData="false" url="/" height="400" width="800" collapsible="true" sm="cell">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="information_set" />
		<odin:gridDataCol name="information_set_comment" />
		<odin:gridDataCol name="information_set_field" />
		<odin:gridDataCol name="information_set_field_comment" />
		<odin:gridDataCol name="condition_no" type="int"/>
		<odin:gridDataCol name="condition_operator" />
		<odin:gridDataCol name="brackets" />
		<odin:gridDataCol name="field_value"/>
		<odin:gridDataCol name="condition_connector" />
		<odin:gridDataCol name="check" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="information_set"  header="表"  width="120" align="left" editor="text"  edited="false" hidden="true" />
		<odin:gridColumn dataIndex="information_set_comment"  header="信息集名称"  width="120" align="left" editor="text"  edited="false"/>
		<odin:gridColumn dataIndex="information_set_field" header="列" width="200" align="left" editor="text"  edited="false"  hidden="true" />
		<odin:gridColumn dataIndex="information_set_field_comment" header="信息项名称" width="200" align="left" editor="text"  edited="false" />
		<odin:gridEditColumn2 dataIndex="condition_operator" header="关系"  width="60" align="left"  editor="select" edited="false" codeType="VSL006"/>
		<odin:gridColumn dataIndex="field_value" header="值"  width="200" align="left" editor="text" edited="false"/>
		<odin:gridEditColumn2 dataIndex="brackets" header="括号" 	width="50" align="left" editor="select" edited="false" codeType="BRACKETS"/>
		<odin:gridEditColumn2 dataIndex="condition_connector" header="连接符" 	width="50" align="left" editor="select" edited="false" codeType="CONNECTOR"/>
		<odin:gridColumn dataIndex="condition_no" header="条件序号"  	width="80" align="right" editor="number" edited="true"/>
		<odin:gridColumn dataIndex="check" header="selectall"  gridName="conditionsdefine" align="center" width="50" editor="checkbox" edited="true" align="center"   isLast="true"/>
	</odin:gridColumnModel>
	<odin:gridJsonData>{data:[]}</odin:gridJsonData>
</odin:editgrid>
</div>


<script type="text/javascript">
function whenAlt(value, params, record, rowIndex, colIndex, ds, colorExp) {
	if (value == null) {
		return;
	}
	return '<a style="cursor:pointer;" title=\'' + value + '\'> ' + value + '</a>';
}
$(function() {
	$("#div_text").show();
	$("#div_select").hide();
	$("#div_date").hide();
	$("#div_number").hide();
	$("#sqltext").css('font-color','red');
})

function shift(editer){
	if(editer=='select'){
		$("#div_select").show();
		$("#div_text").hide();
		$("#div_date").hide();
		$("#div_number").hide();
		$("#editer").val('select');
	}else if(editer=='date'){
		$("#div_date").show();
		$("#div_select").hide();
		$("#div_text").hide();
		$("#div_number").hide();
		$("#editer").val('date');
	}else if(editer=='number'){
		$("#div_number").show();
		$("#div_select").hide();
		$("#div_date").hide();
		$("#div_text").hide();
		$("#editer").val('number');
	}else{
		$("#div_text").show();
		$("#div_select").hide();
		$("#div_date").hide();
		$("#div_number").hide();
		$("#editer").val('text');
	}
	
}

</script>
